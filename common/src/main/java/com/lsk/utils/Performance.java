package com.lsk.utils;

/**
 * Created by LinShunkang on 7/4/16.
 */
public final class Performance {


    public static String getTimeCost2Seconds(long millis) {
        long cost = System.currentTimeMillis() - millis;
        long seconds = cost / 1000;
        return "Cost time=" + cost + "ms=" + seconds + "seconds";
    }

    public static String getTimeCost2Millis(long nanos) {
        long cost = System.nanoTime() - nanos;
        long uSeconds = cost / 1000;
        long milliSenconds = uSeconds / 100;
        return "Cost time=" + cost + "ns=" + uSeconds + "us=" + milliSenconds + "ms";
    }

}
