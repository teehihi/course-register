package com.course.model;

public class ServiceResult {
    private final boolean success;
    private final String message;

    public ServiceResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static ServiceResult ok(String message) {
        return new ServiceResult(true, message);
    }

    public static ServiceResult fail(String message) {
        return new ServiceResult(false, message);
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
}
