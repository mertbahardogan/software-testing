package com.software.testing.core.constant;

import com.software.testing.core.exception.ControllerException;

public class ErrorConstant {

    private ErrorConstant() throws ControllerException {
        throw new ControllerException(E_ERROR_CONSTANT_UTILITY_CLASS);
    }

    public static final String E_GENERAL_SYSTEM = "The error has occurred in the system.";
    public static final String E_USER_EMAIL_MUST_NOT_BE_NULL = "User email must not be null.";
    public static final String E_USER_ALREADY_REGISTERED = "User is already registered.";
    public static final String E_ERROR_CONSTANT_UTILITY_CLASS = "ErrorConstant class cannot be instantiated";
    public static final String E_VALIDATE_UTILITY_CLASS = "ErrorConstant class cannot be instantiated";
}
