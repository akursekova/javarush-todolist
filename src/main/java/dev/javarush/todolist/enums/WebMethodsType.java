package dev.javarush.todolist.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum WebMethodsType {
    GET("get"),
    POST("post"),
    PUT("put"),
    EDIT("edit"),
    DELETE("delete");


    private final String method;

    public String getMethod() {
        return method;
    }
}
