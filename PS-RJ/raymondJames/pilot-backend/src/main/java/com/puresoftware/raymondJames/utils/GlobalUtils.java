package com.puresoftware.raymondJames.utils;

public class GlobalUtils {
    //Bearer Token, Authorization Variables
    public static class GlobalUtilHeaders {
        public static final String CONTENT_TYPE="Content-Type";
        public static final String ACCEPT="Accept";
        public static final String AUTHORIZATION="Authorization";
        public static final String ACCESSTOKEN="access_token";
    }

    public static class GlobalZeebeUtils {
        public static final String ASSIGNEDSUCCESS ="Task Assigned successfully";
        public static final String ALREADY_ASSIGNED ="Task is Already Assigned to";
        public static final String COMPLETED_1 ="Task is Already Assigned or Completed";
        public static final String SUCCESS ="Success";
        public static final String UPDATED ="Task Updated successfully";
        public static final String COMPLETED ="COMPLETED";
        public static final String ASSIGNED ="ASSIGNED";
        public static final String COMPLETED_SUCCESSFULLY ="Task Completed successfully";
        public static final String ERROR ="Please check task is already completed or mentioned taskId is not correct";
    }

    public static class GlobalTasklistUtils {
        public static final String FORMID="formId";
        public static final String PROCESSDEFINITIONKEY="processDefinitionKey";
        public static final String DRAFTVARIABLE="Draft variable successfully done";
        public static final String ERRORINDRAFTVARIABLE="Error in draft variables";
        public static final String ERRORINVARIABLESEARCH="Error in variable search";
        public static final String VARIABLESEARCHDONE="Variable search is Done";
        public static final String PROCESSNAME="diagram_1.bpmn";
    }
    }

