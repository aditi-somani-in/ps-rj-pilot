package com.puresoftware.raymondJames.pojo;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

public class TaskListVariableDetails {

    public static class TaskListVariableResponse{

        @Getter
        @Setter
        ResponseEntity<String> taskDetails;

        @Getter
        @Setter
        String processDefinitionKey;

        @Getter
        @Setter
        public String message;

        @Getter
        @Setter
        public JSONObject jsonResponse;

        @Getter
        @Setter
        public String formId;

        @Getter
        @Setter
        String assignee;

        @Getter
                @Setter
        String taskState;
    }

}
