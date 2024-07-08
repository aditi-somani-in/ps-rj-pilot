package com.puresoftware.raymondJames.implementation;

import com.puresoftware.raymondJames.pojo.TaskListVariableDetails;
import com.puresoftware.raymondJames.service.TasklistApiService;
import com.puresoftware.raymondJames.config.HeaderConfig;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

import static com.puresoftware.raymondJames.utils.GlobalUtils.GlobalTasklistUtils.*;

@Service
@Slf4j
public class TasklistApiImpl implements TasklistApiService {
    private static Logger logger = LoggerFactory.getLogger(TasklistApiImpl.class);

    @Value("${camunda.api.url}")
    private String camundaApiUrl;

    @Value("${localhost.formapi.url}")
    private String formapiUrl;

    @Value("${tasklist.TaskSearchUrl}")
    private String taskSearchUrl;

    @Value("${task.version}")
    private String taskVersion;

    @Value("${application.json}")
    private String applicationJson;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HeaderConfig headerConfig;

    /*TODO: Need to check if rest template are required*/

    //For get task details using taskId
    @Override
    @SneakyThrows
    public TaskListVariableDetails.TaskListVariableResponse getTask(String taskId) {

        logger.debug("Service for GET A TASK FROM TASKLIST invoked..!!");

        TaskListVariableDetails.TaskListVariableResponse taskListVariableResponse =
                new TaskListVariableDetails.TaskListVariableResponse();

        String url = camundaApiUrl + taskVersion + taskId;
        HttpHeaders headers = headerConfig.addHeadersValue();
        HttpEntity<String> httpEntity = new HttpEntity(null, headers);
        try {
            ResponseEntity<TaskListVariableDetails.TaskListVariableResponse> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, TaskListVariableDetails.TaskListVariableResponse.class);
            taskListVariableResponse.setAssignee(response.getBody().getAssignee());
            taskListVariableResponse.setTaskState(response.getBody().getTaskState());
        } catch (Exception ex) {
            logger.error(ex.toString());
            taskListVariableResponse.setMessage(ex.getMessage());
        }
        return taskListVariableResponse;
    }

    //For get form details using taskId
    @Override
    @SneakyThrows
    public HashMap<String, Object> getForm(String taskId) {
        logger.debug("Service for GET A FORM FROM TASKLIST invoked..!!");
        TaskListVariableDetails.TaskListVariableResponse taskListVariableResponse = new TaskListVariableDetails.TaskListVariableResponse();
        //taskListVariableResponse.setTaskDetails(getTask(taskId));
        taskListVariableResponse.jsonResponse = new JSONObject(taskListVariableResponse.getTaskDetails().getBody());
        taskListVariableResponse.setProcessDefinitionKey(taskListVariableResponse.jsonResponse.getString(PROCESSDEFINITIONKEY));
        taskListVariableResponse.setFormId(taskListVariableResponse.jsonResponse.getString(FORMID));
        String url = formapiUrl+taskListVariableResponse.getFormId()+"?processDefinitionKey="+taskListVariableResponse.getProcessDefinitionKey();
        HttpHeaders headers = headerConfig.addHeadersValue();
        HttpEntity<String> httpEntity = new HttpEntity(null, headers);
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        } catch (Exception ex) {
            logger.error(ex.toString());
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("taskDetails", taskListVariableResponse.getTaskDetails().getBody());
        map.put("formDetails", response.getBody());
        System.out.println(map);
        return map;
    }

    //For get task Search from tasklist
    @Override
    @SneakyThrows
    public ResponseEntity<String> searchTask(String requestBody) {
        logger.debug("Service for Search A TASK FROM TASKLIST invoked..!!");
        HttpHeaders headers = headerConfig.addHeadersValue();
        HttpEntity<String> httpEntity = new HttpEntity(requestBody, headers);
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(taskSearchUrl, HttpMethod.POST, httpEntity, String.class);
        }catch(Exception ex){
            logger.error(ex.toString());
        }
        return response;
    }

    //For get variable Search from tasklist
    @Override
    @SneakyThrows
    public ResponseEntity<String> variableSearch(String taskId, String requestBody) {
        logger.debug("Service for Search A Variable FROM TASKLIST invoked..!!");
        TaskListVariableDetails.TaskListVariableResponse taskListVariableResponse = new TaskListVariableDetails.TaskListVariableResponse();
        JSONObject responseObj =  new JSONObject();
        String url = camundaApiUrl + taskVersion+ taskId+"/variables/search";
        HttpHeaders headers = headerConfig.addHeadersValue();
        HttpEntity<String> httpEntity = new HttpEntity(requestBody, headers);
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        }catch(Exception ex){
            logger.error(ex.toString());
            taskListVariableResponse.setMessage(ERRORINVARIABLESEARCH);
            //responseObj = responseConfig.ResponseOutput(taskListVariableResponse.jsonResponse, response, "TransactionId", taskListVariableResponse.getMessage());
        }
        taskListVariableResponse.setMessage(VARIABLESEARCHDONE);
        //responseObj = responseConfig.ResponseOutput(taskListVariableResponse.jsonResponse, response, "TransactionId", taskListVariableResponse.getMessage());
        return response;
    }

    //For get variable Search from tasklist
    @Override
    @SneakyThrows
    public ResponseEntity<String> draftVariable(String taskId, String requestBody) {
        logger.debug("Service for Draft Variables FROM TASKLIST invoked..!!");
        TaskListVariableDetails.TaskListVariableResponse taskListVariableResponse = new TaskListVariableDetails.TaskListVariableResponse();
        JSONObject responseObj =  new JSONObject();
        String url = camundaApiUrl + taskVersion+ taskId+"/variables";
        HttpHeaders headers = headerConfig.addHeadersValue();
        HttpEntity<String> httpEntity = new HttpEntity(requestBody, headers);
        ResponseEntity<String> response = null;
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        }catch(Exception ex){
            logger.error(ex.toString());
            taskListVariableResponse.setMessage(ERRORINDRAFTVARIABLE);
            //responseObj = responseConfig.ResponseOutput(taskListVariableResponse.jsonResponse, response, "TransactionId", taskListVariableResponse.getMessage());
        }
        taskListVariableResponse.setMessage(DRAFTVARIABLE);
        //responseObj = responseConfig.ResponseOutput(taskListVariableResponse.jsonResponse, response, "TransactionId", taskListVariableResponse.getMessage());
        return response;
    }
}
