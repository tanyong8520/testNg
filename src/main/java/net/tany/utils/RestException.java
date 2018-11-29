package net.tany.utils;

public class RestException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private Error error;

    public RestException(Error error){
        super(error.getMessage());
        this.error = error;
    }

    public Error getError() {
        return error;
    }

    public String getCode() {
        return error.getCode();
    }
}
