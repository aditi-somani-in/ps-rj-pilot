package com.puresoftware.raymondJames.service;

import com.puresoftware.raymondJames.implementation.TasklistApiImpl;
import com.puresoftware.raymondJames.pojo.TaskListVariableDetails;
import com.puresoftware.raymondJames.pojo.ZeebeVariableDetails;
import org.springframework.http.ResponseEntity;

public interface ZeebeApiService {

    ResponseEntity<String> assignZeebeTask(String taskId, String variableJson);

    ZeebeVariableDetails.ZeebeVariablesResponse unAssignZeebeTask(String taskId, TaskListVariableDetails.TaskListVariableResponse taskListVariableResponse);

    ResponseEntity<String> updateZeebeTask(String taskId, String variableJson);

    ResponseEntity<String> completeZeebeTask(String taskId, String variableJson);

}
