package com.luo.dubbo.sniff.exception;

/**
 * Created by xiangnan on 2018/9/25.
 */
public class DubboSniffException extends RuntimeException {

    public DubboSniffException(String message) {
        super(message);
    }

    public DubboSniffException(String message, Throwable cause) {
        super(message, cause);
    }
}
