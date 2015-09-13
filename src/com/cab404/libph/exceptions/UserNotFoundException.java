package com.cab404.libph.exceptions;

/**
 * Sorry for no comments!
 * Created at 16:00 on 09/09/15
 *
 * @author cab404
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Exception e) {
        super(e);
    }
}
