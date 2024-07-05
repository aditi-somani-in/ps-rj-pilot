package com.puresoftware.raymondJames.pojo;

import lombok.Getter;
import lombok.Setter;

public class ResponseDetails {

    @Getter
    @Setter
    public String TransactionId;

    @Getter
    @Setter
    public String message;

    @Getter
    @Setter
    public String errorCode;

    @Getter
    @Setter
    public String status;

    @Getter
    @Setter
    public String metadata;

}
