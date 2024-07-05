package com.puresoftware.raymondJames.pojo;


import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

public class ZeebeVariableDetails {

    public static class ZeebeVariablesResponse{

        @Getter
                @Setter
        public String assignee;

        @Getter
        @Setter
        public String taskState;

        /*TODO: not sure if we need below two*/

        @Getter
        @Setter
        public JSONObject jsonObject;

        @Getter
        @Setter
        public ResponseEntity<String> taskDetails;

        @Getter
        @Setter
        public String message;

    }

}
