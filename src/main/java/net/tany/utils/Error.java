package net.tany.utils;

import java.util.List;

public class Error {
    private String message;

    private String code;

    private List<String> subMessage;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<String> getSubMessage() {
        return subMessage;
    }

    public void setSubMessage(List<String> subMessage) {
        this.subMessage = subMessage;
    }
}
