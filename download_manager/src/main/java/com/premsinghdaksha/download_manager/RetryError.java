package com.premsinghdaksha.download_manager;
/**
 * Created by Er.prem singh daksha on 28/12/2020.
 */
public class RetryError extends Exception {

    public RetryError() {
        super("Maximum retry exceeded");
    }

    public RetryError(Throwable cause) {
        super(cause);
    }
}
