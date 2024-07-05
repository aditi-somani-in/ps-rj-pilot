package com.puresoftware.raymondJames.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.puresoftware.raymondJames.config.BearerTokenGeneratorConfig;
import com.puresoftware.raymondJames.pojo.TaskListVariableDetails;
import com.puresoftware.raymondJames.pojo.ZeebeVariableDetails;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

	// Zeebe Api for Assign User Task
	@Override
	@SneakyThrows
	public ResponseEntity<String> assignZeebeTask(String taskId, String variableJson)  {
		logger.debug("Service for Assign Zeebe User Task..!!");
		JSONObject responseObj =  new JSONObject();
		ObjectMapper mapper = new ObjectMapper();
		ZeebeVariableDetails.ZeebeVariablesResponse zeebeVariablesResponse = new ZeebeVariableDetails.ZeebeVariablesResponse();
		String assignZeebeTaskUrl = zeebeApiUrl + zeebeVersion + taskId + "/assignment";
		//zeebeVariablesResponse.setTaskDetails(tasklistApiImpl.getTask(taskId));
		zeebeVariablesResponse.jsonObject = new JSONObject(zeebeVariablesResponse.taskDetails.getBody());
		zeebeVariablesResponse.setAssignee(zeebeVariablesResponse.jsonObject.get("assignee").toString());
		zeebeVariablesResponse.setTaskState(zeebeVariablesResponse.jsonObject.get("taskState").toString());
		HttpHeaders headers = headerConfig.addHeadersValue();
		HttpEntity<String> entity = new HttpEntity(variableJson, headers);
		ResponseEntity<String> response= null;
		HashMap<String, String> mapResponse = new HashMap<>();
		try {
			if(zeebeVariablesResponse.getAssignee().equals("null")) {
				response = restTemplate.exchange(assignZeebeTaskUrl, HttpMethod.POST, entity, String.class);
			}
			else{
				zeebeVariablesResponse.setMessage(ALREADY_ASSIGNED + zeebeVariablesResponse.getAssignee());
				return new ResponseEntity<>(zeebeVariablesResponse.getMessage(), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception ex) {
			logger.error(ex.toString());
			//responseObj = responseConfig.ResponseOutput(zeebeVariablesResponse.jsonObject, response, "TransactionId", ex.getMessage().toString());
			return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
		}
		zeebeVariablesResponse.setMessage(ASSIGNEDSUCCESS);
		//responseObj =  zeebeVariablesResponse.jsonObject, response, "TransactionId", zeebeVariablesResponse.getMessage());
		return new ResponseEntity<>(responseObj.toString(), HttpStatus.OK);
	}



	// Zeebe Api for UnAssign User Task
	@Override
	@SneakyThrows
	public ZeebeVariableDetails.ZeebeVariablesResponse unAssignZeebeTask(String taskId) {

		logger.debug("Service for UnAssign Zeebe User Task..!!");

		ZeebeVariableDetails.ZeebeVariablesResponse zeebeVariablesResponse = new ZeebeVariableDetails.ZeebeVariablesResponse();
		String unAssignZeebeTaskUrl = zeebeApiUrl + zeebeVersion + taskId + "/assignee";

		TaskListVariableDetails.TaskListVariableResponse getTaskJson = tasklistApiImpl.getTask(taskId);

		for(int i = 0; i < getTaskJson.toString().length(); i++){

			zeebeVariablesResponse.setAssignee(getTaskJson.getAssignee());
			zeebeVariablesResponse.setTaskState(getTaskJson.getTaskState());
		}

		HttpHeaders headers = headerConfig.addHeadersValue();
		HttpEntity<String> entity = new HttpEntity(headers);
		try {

			if (zeebeVariablesResponse.getAssignee() != null) {
				ResponseEntity<String> response = restTemplate.exchange(unAssignZeebeTaskUrl, HttpMethod.DELETE, entity, String.class);
			}
			/*TODO: Re-iterate the logic*/
			else if (zeebeVariablesResponse.getTaskState() != CREATED ) {
				/*TODO: Need to include http status as bad request */
				zeebeVariablesResponse.setMessage("Task id: "+taskId+ " has status " + zeebeVariablesResponse.getTaskState());
			}

		} catch (Exception ex) {
			logger.error(ex.toString());
			zeebeVariablesResponse.setMessage(ex.getMessage());
		}
		zeebeVariablesResponse.setMessage(UN_ASSIGNED_SUCCESSFULLY);
		return zeebeVariablesResponse;
	}

	// Zeebe Api for Update User Task
	@Override
	@SneakyThrows
	public ResponseEntity<String> updateZeebeTask(String taskId, String variableJson) {
		logger.debug("Service for Update Zeebe User Task..!!");
		JSONObject responseObj = new JSONObject();
		ZeebeVariableDetails.ZeebeVariablesResponse zeebeVariablesResponse = new ZeebeVariableDetails.ZeebeVariablesResponse();
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
		zeebeVariablesResponse.setMessage(UPDATED);
	//	responseObj = responseConfig.ResponseOutput(zeebeVariablesResponse.jsonObject, response, "TransactionId", zeebeVariablesResponse.getMessage());
		return new ResponseEntity<>(responseObj.toString(), HttpStatus.OK);
	}

	// Zeebe Api for Complete User Task
	@Override
	@SneakyThrows
	public ResponseEntity<String> completeZeebeTask(String taskId, String variableJson) {
		logger.debug("Service for Complete Zeebe User Task..!!");
		JSONObject responseObj = new JSONObject();
		ZeebeVariableDetails.ZeebeVariablesResponse zeebeVariablesResponse = new ZeebeVariableDetails.ZeebeVariablesResponse();
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
		zeebeVariablesResponse.setMessage(COMPLETED_SUCCESSFULLY);
		//responseObj = responseConfig.ResponseOutput(zeebeVariablesResponse.jsonObject, response, "TransactionId", zeebeVariablesResponse.getMessage());
		return new ResponseEntity<>(responseObj.toString(), HttpStatus.OK);
	}
}
