package com.premsinghdaksha.download_manager;

/**
 * Created by Er.prem singh daksha on 28/12/2020.
 */
public interface RetryPolicy {

    /**
     * Returns the current timeout (used for logging).
     */
    public int getCurrentTimeout();

    /**
     * Returns the current retry count (used for logging).
     */
    public int getCurrentRetryCount();

    /**
     * Return back off multiplier
     */
    public float getBackOffMultiplier();


    public void retry() throws RetryError;


}
