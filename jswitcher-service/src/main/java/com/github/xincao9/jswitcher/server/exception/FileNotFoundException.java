package com.github.xincao9.jswitcher.server.exception;

/**
 *
 * @author xin.cao@asiainnovations.com
 */
public class FileNotFoundException extends Error {

    public FileNotFoundException(String message) {
        super(message);
    }

    public FileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileNotFoundException(Throwable cause) {
        super(cause);
    }

}
