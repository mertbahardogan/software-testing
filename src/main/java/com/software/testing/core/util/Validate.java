package com.software.testing.core.util;

import com.software.testing.core.exception.ControllerException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Validate {

    /**
     * The expression must be TRUE, otherwise throws {@link ControllerException}
     */
    public void state(boolean isExpressionTrue, String errorMessage)
            throws ControllerException {
        if (!isExpressionTrue) {
            throw new ControllerException(errorMessage);
        }
    }

    /**
     * The expression must be FALSE, otherwise throws {@link ControllerException}
     */
    public void stateNot(boolean isExpressionFalse, String errorMessage)
            throws ControllerException {
        if (isExpressionFalse) {
            throw new ControllerException(errorMessage);
        }
    }

}
