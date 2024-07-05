package com.puresoftware.raymondJames.pojo;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;
import lombok.Setter;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateZeebeDetails {

    public static class UpdateZeebeRequest {

        @JsonSetter
        JSONObject changeset;

        @Getter
                @Setter
        String dueDate;

        @Getter
                @Setter
        String followUpDate;

        @Getter
                @Setter
        List<String> candidateUsers;

        @Getter
                @Setter
        List<String> candidateGroups;

        @Getter
                @Setter
        String action;


    }
}
