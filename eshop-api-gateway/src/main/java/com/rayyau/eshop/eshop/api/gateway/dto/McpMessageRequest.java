package com.rayyau.eshop.eshop.api.gateway.dto;

import java.io.Serial;
import java.io.Serializable;

public class McpMessageRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 4L;

    private String message;

    public McpMessageRequest() {
    }

    public McpMessageRequest(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
