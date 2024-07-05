package com.puresoftware.raymondJames.controllers;

import java.io.IOException;

import com.puresoftware.raymondJames.pojo.AssignZeebeDetails;
import com.puresoftware.raymondJames.pojo.ZeebeTaskDetails;
import org.springframework.beans.factory.annotation.Autowired;
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
	public ZeebeTaskDetails.ZeebeTaskResponse assignZeebeTask(@PathVariable String taskId, @RequestBody AssignZeebeDetails.AssignZeebeRequest assignZeebeRequest) throws Exception {
		ZeebeTaskDetails.ZeebeTaskResponse response = zeebeApiImpl.assignZeebeTask(taskId,assignZeebeRequest);
		return zeebeApiImpl.assignZeebeTask(taskId, assignZeebeRequest);
	}

	/* Api for unassign zeebe task to user for mentioned taskId */
	@DeleteMapping("/un-assign/{taskId}")
	public ZeebeTaskDetails.ZeebeTaskResponse unAssignZeebeTask(@PathVariable String taskId) throws IOException{
		 ZeebeTaskDetails.ZeebeTaskResponse response = zeebeApiImpl.unAssignZeebeTask(taskId);
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
