package com.puresoftware.raymondJames.pojo;


import lombok.Getter;
import lombok.Setter;

public class ZeebeTaskDetails {

    public static class ZeebeTaskResponse {

        @Getter
                @Setter
        public String assignee;

        @Getter
                @Setter
        public String taskState;

        @Getter
                @Setter
        public String message;

    }
}
