package com.puresoftware.raymondJames.pojo;

import lombok.Getter;
import lombok.Setter;

public class AssignZeebeDetails {

    public static class AssignZeebeRequest {

        @Getter
                @Setter
        String assignee;

        @Getter
                @Setter
        String allowOverride;

        @Getter
                @Setter
        String action;

    }

    public static class AssignZeebeResponse {

    }
}
