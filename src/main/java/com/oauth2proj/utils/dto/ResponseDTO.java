package com.oauth2proj.utils.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ResponseDTO<T> {

    public boolean success;
    public int code;
    public T data;
    public String message;
    
}
