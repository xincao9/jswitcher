package com.github.xincao9.jswitcher.server.exception;

/**
 *
 * @author xin.cao@asiainnovations.com
 */
public class SwitcherServerException extends Error {

    public SwitcherServerException(String message) {
        super(message);
    }

    public SwitcherServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public SwitcherServerException(Throwable cause) {
        super(cause);
    }

}
