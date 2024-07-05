package com.puresoftware.raymondJames.implementation;

import com.puresoftware.raymondJames.config.BearerTokenGeneratorConfig;
import com.puresoftware.raymondJames.pojo.AssignZeebeDetails;
import com.puresoftware.raymondJames.pojo.TaskListVariableDetails;
import com.puresoftware.raymondJames.pojo.ZeebeTaskDetails;
import com.puresoftware.raymondJames.service.ZeebeApiService;
import com.puresoftware.raymondJames.config.HeaderConfig;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;


import static com.puresoftware.raymondJames.utils.GlobalUtils.GlobalZeebeUtils.*;

@Service
@Slf4j
public class ZeebeApiImpl implements ZeebeApiService {

	private static Logger logger = LoggerFactory.getLogger(ZeebeApiImpl.class);

	@Value("${zeebe.api.url}")
	private String zeebeApiUrl;

	@Value("${application.json}")
	private String applicationJson;

	@Value("${zeebe.version}")
	private String zeebeVersion;

	@Autowired
	BearerTokenGeneratorConfig bearerTokenGeneratorConfig;

	@Autowired
	public RestTemplate restTemplate;

	@Autowired
	private HeaderConfig headerConfig;

	@Autowired
	TasklistApiImpl tasklistApiImpl;


	@Override
	@SneakyThrows
	public ZeebeTaskDetails.ZeebeTaskResponse assignZeebeTask(String taskId, AssignZeebeDetails.AssignZeebeRequest assignZeebeRequest) {

		logger.debug("Service for Assign Zeebe User Task..!!");

		ZeebeTaskDetails.ZeebeTaskResponse zeebeTaskResponse = new ZeebeTaskDetails.ZeebeTaskResponse();

		String assignZeebeTaskUrl = zeebeApiUrl + zeebeVersion + taskId + "/assignment";

		HttpHeaders headers = headerConfig.addHeadersValue();
		HttpEntity<String> entity = new HttpEntity(assignZeebeRequest, headers);

		for (int i = 0; i < assignZeebeRequest.getAssignee().length(); i++){

			zeebeTaskResponse.setAssignee(assignZeebeRequest.getAssignee());
		}

		try {

			if (zeebeTaskResponse.getAssignee() != null) {

				ResponseEntity<String> response = restTemplate.exchange(assignZeebeTaskUrl, HttpMethod.POST, entity, String.class);
			} else {

				/*TODO: Need to include http status as bad request */
				zeebeTaskResponse.setMessage("Task id: " + taskId + " is already assigned..!!");
			}

		} catch (Exception ex) {
			logger.error(ex.toString());
			zeebeTaskResponse.setMessage(ex.getMessage());
		}

		zeebeTaskResponse.setMessage(SUCCESS);
		return zeebeTaskResponse;
	}



	@Override
	@SneakyThrows
	public ZeebeTaskDetails.ZeebeTaskResponse unAssignZeebeTask(String taskId) {

		logger.debug("Service for UnAssign Zeebe User Task..!!");

		ZeebeTaskDetails.ZeebeTaskResponse zeebeTaskResponse = new ZeebeTaskDetails.ZeebeTaskResponse();
		String unAssignZeebeTaskUrl = zeebeApiUrl + zeebeVersion + taskId + "/assignee";

		TaskListVariableDetails.TaskListVariableResponse getTaskJson = tasklistApiImpl.getTask(taskId);

		for(int i = 0; i < getTaskJson.toString().length(); i++){

			zeebeTaskResponse.setAssignee(getTaskJson.getAssignee());
			zeebeTaskResponse.setTaskState(getTaskJson.getTaskState());
		}

		HttpHeaders headers = headerConfig.addHeadersValue();
		HttpEntity<String> entity = new HttpEntity(headers);
		try {

			if (zeebeTaskResponse.getAssignee() != null) {
				ResponseEntity<String> response  = restTemplate.exchange(unAssignZeebeTaskUrl, HttpMethod.DELETE, entity, String.class);
			}

			else if (getTaskJson.getTaskState().equals(COMPLETED) || getTaskJson.getTaskState().equals(ASSIGNED)) {
				/*TODO: Need to include http status as bad request */
				zeebeTaskResponse.setMessage("Task id: "+taskId+ " has status " + getTaskJson.getTaskState());
			}

		} catch (Exception ex) {
			logger.error(ex.toString());
			zeebeTaskResponse.setMessage(ex.getMessage());
		}

		zeebeTaskResponse.setMessage(SUCCESS);
		return zeebeTaskResponse;
	}



	@Override
	@SneakyThrows
	public ResponseEntity<String> updateZeebeTask(String taskId, String variableJson) {
		logger.debug("Service for Update Zeebe User Task..!!");
		JSONObject responseObj = new JSONObject();
		ZeebeTaskDetails.ZeebeTaskResponse zeebeTaskResponse = new ZeebeTaskDetails.ZeebeTaskResponse();
		String updateZeebeTaskUrl = zeebeApiUrl + zeebeVersion + taskId;
		HttpHeaders headers = headerConfig.addHeadersValue();
		HttpEntity<String> entity = new HttpEntity(variableJson, headers);
		ResponseEntity<String> response = null;
		try {
			response = restTemplate.exchange(updateZeebeTaskUrl, HttpMethod.PATCH, entity, String.class);
		} catch (Exception ex) {
			logger.error(ex.toString());
			new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
		}
		zeebeTaskResponse.setMessage(UPDATED);
	//	responseObj = responseConfig.ResponseOutput(zeebeVariablesResponse.jsonObject, response, "TransactionId", zeebeVariablesResponse.getMessage());
		return new ResponseEntity<>(responseObj.toString(), HttpStatus.OK);
	}

	// Zeebe Api for Complete User Task
	@Override
	@SneakyThrows
	public ResponseEntity<String> completeZeebeTask(String taskId, String variableJson) {
		logger.debug("Service for Complete Zeebe User Task..!!");
		JSONObject responseObj = new JSONObject();
		ZeebeTaskDetails.ZeebeTaskResponse zeebeTaskResponse = new ZeebeTaskDetails.ZeebeTaskResponse();
		String completeZeebeTaskUrl = zeebeApiUrl + zeebeVersion + taskId + "/completion";
		HttpHeaders headers = headerConfig.addHeadersValue();
		HttpEntity<String> entity = new HttpEntity(variableJson, headers);
		ResponseEntity<String> response = null;
		try {
			response = restTemplate.exchange(completeZeebeTaskUrl, HttpMethod.POST, entity, String.class);
		} catch (Exception ex) {
			logger.error(ex.toString());
			return new ResponseEntity<>(ERROR, HttpStatus.BAD_REQUEST);
		}
		zeebeTaskResponse.setMessage(COMPLETED_SUCCESSFULLY);
		//responseObj = responseConfig.ResponseOutput(zeebeVariablesResponse.jsonObject, response, "TransactionId", zeebeVariablesResponse.getMessage());
		return new ResponseEntity<>(responseObj.toString(), HttpStatus.OK);
	}
}
