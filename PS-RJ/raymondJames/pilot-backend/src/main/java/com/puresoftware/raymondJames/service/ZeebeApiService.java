package com.puresoftware.raymondJames.service;

import com.puresoftware.raymondJames.pojo.AssignZeebeDetails;
import com.puresoftware.raymondJames.pojo.ZeebeTaskDetails;
import org.springframework.http.ResponseEntity;

public interface ZeebeApiService {

    ZeebeTaskDetails.ZeebeTaskResponse assignZeebeTask(String taskId, AssignZeebeDetails.AssignZeebeRequest assignZeebeRequest);

    ZeebeTaskDetails.ZeebeTaskResponse unAssignZeebeTask(String taskId);

    ResponseEntity<String> updateZeebeTask(String taskId, String variableJson);

    ResponseEntity<String> completeZeebeTask(String taskId, String variableJson);

}
