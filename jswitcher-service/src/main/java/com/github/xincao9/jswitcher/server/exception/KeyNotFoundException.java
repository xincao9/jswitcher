package com.github.xincao9.jswitcher.server.exception;

/**
 *
 * @author xin.cao@asiainnovations.com
 */
public class KeyNotFoundException extends Error {

    public KeyNotFoundException(String message) {
        super(message);
    }

    public KeyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public KeyNotFoundException(Throwable cause) {
        super(cause);
    }

}
