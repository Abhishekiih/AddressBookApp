package com.bridgelabz.addressBookApp.dto;

public class MessageDTO {
    String message;

    Long id;

    public MessageDTO(String message) {
        this.message = message;
        this.id = null;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
