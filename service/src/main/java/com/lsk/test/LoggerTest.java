package com.lsk.test;

import com.lsk.log.LoggerManager;

/**
 * Created by LinShunkang on 7/3/16.
 */
public class LoggerTest {

    public static void main(String[] args) {
        try {
            throw new RuntimeException(new NullPointerException("aaa"));
//            throw new RuntimeException("23456");
        } catch (Exception e) {
            LoggerManager.error("DocumentServiceImpl.getById({})", 1, e);
            LoggerManager.error("DocumentServiceImpl.getById()", e);
        }
    }
}
