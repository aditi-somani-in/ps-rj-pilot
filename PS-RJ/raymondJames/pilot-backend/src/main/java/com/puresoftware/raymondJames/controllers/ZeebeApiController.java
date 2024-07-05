package com.puresoftware.raymondJames.controllers;

import java.io.IOException;

import com.puresoftware.raymondJames.pojo.TaskListVariableDetails;
import com.puresoftware.raymondJames.pojo.ZeebeVariableDetails;
import io.camunda.zeebe.client.impl.http.ApiEntity;
import io.netty.handler.codec.http.HttpResponse;
import org.json.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.puresoftware.raymondJames.implementation.ZeebeApiImpl;

/**
 * Description: This Controller for all Zeebe User Task Apis
 * Assign, Unassign, Update, Complete
 */

@RestController
@ControllerAdvice
@CrossOrigin(origins = "*") //Need to remove this before deploying this service
@RequestMapping("/zeebe-api")
public class ZeebeApiController {

	@Autowired
	ZeebeApiImpl zeebeApiImpl;

	/* Api for assign zeebe task to user for mentioned taskId */
	@PostMapping("/assign/{taskId}")
	public ResponseEntity<String> assignZeebeTask(@PathVariable String taskId, @RequestBody String variableJson) throws Exception {
		return zeebeApiImpl.assignZeebeTask(taskId, variableJson);
	}

	/* Api for unassign zeebe task to user for mentioned taskId */
	@DeleteMapping("/un-assign/{taskId}")
	public ZeebeVariableDetails.ZeebeVariablesResponse unAssignZeebeTask(@PathVariable String taskId) throws IOException{
		ZeebeVariableDetails.ZeebeVariablesResponse response = zeebeApiImpl.unAssignZeebeTask(taskId);
		 return ResponseEntity.ok().body(response).getBody();
	}

	/* Api for update few details for mentioned taskId */
	@PatchMapping("/update/{taskId}")
	public ResponseEntity<String> updateZeebeTask(@PathVariable String taskId, @RequestBody String variableJson) throws IOException{
		return zeebeApiImpl.updateZeebeTask(taskId, variableJson);
	}

	/* Api for complete task to user for mentioned taskId */
	@PostMapping("/complete/{taskId}")
	public ResponseEntity<String> completeZeebeTask(@PathVariable String taskId, @RequestBody String variableJson) throws IOException{
		 return zeebeApiImpl.completeZeebeTask(taskId, variableJson);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
