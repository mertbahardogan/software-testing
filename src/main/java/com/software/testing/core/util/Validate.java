package com.software.testing.core.util;

import com.software.testing.core.exception.ControllerException;

public class Validate {

    private Validate() {
    }

    /**
     * The expression must be TRUE, otherwise throws {@link ControllerException}
     */
    public static void state(boolean isExpressionTrue, String errorMessage)
            throws ControllerException {
        if (!isExpressionTrue) {
            throw new ControllerException(errorMessage);
        }
    }

    /**
     * The expression must be FALSE, otherwise throws {@link ControllerException}
     */
    public static void stateNot(boolean isExpressionFalse, String errorMessage)
            throws ControllerException {
        if (isExpressionFalse) {
            throw new ControllerException(errorMessage);
        }
    }

}
