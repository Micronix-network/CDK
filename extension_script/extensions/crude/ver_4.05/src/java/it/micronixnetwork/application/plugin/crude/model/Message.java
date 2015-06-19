package it.micronixnetwork.application.plugin.crude.model;

public  class Message {

    public static final int OK = 0;

    private String message;

    private int status;
    
    public Message() {
	this.status=OK;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}