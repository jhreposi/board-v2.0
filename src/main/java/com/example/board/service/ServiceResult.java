package com.example.board.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ServiceResult {
    private String actionType;
    private String viewPath;
    private HttpServletRequest request;
    private HttpServletResponse response;

    public ServiceResult(String actionType, String viewPath, HttpServletRequest request, HttpServletResponse response) {
        this.actionType = actionType;
        this.viewPath = viewPath;
        this.request = request;
        this.response = response;
    }

    public String getActionType() {
        return actionType;
    }

    public String getViewPath() {
        return viewPath;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }
}
