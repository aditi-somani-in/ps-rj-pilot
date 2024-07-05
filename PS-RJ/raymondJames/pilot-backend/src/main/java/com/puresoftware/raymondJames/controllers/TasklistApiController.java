package com.puresoftware.raymondJames.controllers;

import com.puresoftware.raymondJames.implementation.TasklistApiImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

/**
 * Description: This task will fetch the details of the process assigned.
 *              Get a Task API used
 */

@RestController
@ControllerAdvice
@CrossOrigin(origins = "*") //Need to remove this before deploying this service
@RequestMapping("/tasklistApi")
public class TasklistApiController {

    @Autowired
    TasklistApiImpl taskListApiImpl;

    public TasklistApiController(TasklistApiImpl taskListApiImpl) {
        this.taskListApiImpl = taskListApiImpl;
    }

    /* Api for Get task details for provided taskid */
    @GetMapping("/getTaskDetails/{taskId}")
    public ResponseEntity<String> getTask(@PathVariable String taskId) throws IOException {
        return taskListApiImpl.getTask(taskId);
    }

    /* Api for Get form details for provided taskid */
    @GetMapping("/getFormDetails/{taskId}")
    public HashMap<String, Object> getForm(@PathVariable String taskId) throws IOException {
        return taskListApiImpl.getForm(taskId);
    }

    /* Api for Get all tasks from the tasklist */
    @PostMapping("/getAllTask")
    public ResponseEntity<String> getAllTask(@RequestBody String requestBody) throws  IOException{
        return taskListApiImpl.searchTask(requestBody);
    }

    /* Api for Get task details as per request body from the task list for ex: state:"CREATED" */
    @PostMapping("/SearchTask")
    public ResponseEntity<String> searchTask(@RequestBody String requestBody) throws  IOException{
        return taskListApiImpl.searchTask(requestBody);
    }

    /* Api for Search variable details from mentioned taskId and Variable */
    @PostMapping("/VariableSearch/{taskId}")
    public ResponseEntity<String> VariableSearch(@PathVariable String taskId, @RequestBody String requestBody) throws  IOException{
        return taskListApiImpl.variableSearch(taskId, requestBody);
    }

    /* Api for add draft variable in integer format form mentioned taskid */
    @PostMapping("/DraftVariable/{taskId}")
    public ResponseEntity<String> DraftVariable(@PathVariable String taskId, @RequestBody String requestBody) throws  IOException{
        return taskListApiImpl.draftVariable(taskId, requestBody);
    }
}


