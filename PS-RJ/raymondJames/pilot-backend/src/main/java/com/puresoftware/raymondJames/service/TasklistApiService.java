package com.puresoftware.raymondJames.service;

import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.HashMap;

public interface TasklistApiService {

    ResponseEntity<String> getTask(String taskId) throws IOException;

    HashMap<String, Object> getForm(String taskId);

    ResponseEntity<String> searchTask(String requestBody);

    ResponseEntity<String> variableSearch(String taskId, String requestBody);

    ResponseEntity<String> draftVariable(String taskId, String requestBody);

}
