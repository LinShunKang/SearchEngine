package com.lsk.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by LinShunkang on 7/3/16.
 */
public final class LoggerManager {

    private static final Logger logger = LoggerFactory.getLogger("");

    public static void info(String msg, Class<?> clazz) {
        logger.info("{}-" + msg, clazz.getSimpleName());
    }

    public static void info(String msg) {
        logger.info(msg);
    }

    public static void info(String msg, Object... params) {
        logger.info(msg, params);
    }

    public static void warn(String msg) {
        logger.warn(msg);
    }

    public static void warn(String msg, Object... params) {
        logger.warn(msg, params);
    }

    public static void error(String msg) {
        logger.error(msg);
    }

    public static void error(String msg, Object... params) {
        logger.error(msg, params);
    }

    public static void error(String msg, Throwable throwable) {
        logger.error(msg, throwable);
    }
}
