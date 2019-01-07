package com.github.xincao9.jswitcher.server.exception;

/**
 *
 * @author xin.cao@asiainnovations.com
 */
public class ParameterInvalidException extends Error {

    public ParameterInvalidException(String message) {
        super(message);
    }

    public ParameterInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParameterInvalidException(Throwable cause) {
        super(cause);
    }

}
