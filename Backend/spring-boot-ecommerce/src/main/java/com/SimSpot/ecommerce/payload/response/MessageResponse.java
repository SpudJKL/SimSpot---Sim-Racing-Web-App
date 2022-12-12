package com.SimSpot.ecommerce.payload.response;

public class MessageResponse {
    // Variables
    private String message;

    // Constructor
    public MessageResponse(String message) {
        this.message = message;
    }

    // Methods
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}