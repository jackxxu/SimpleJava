package com.learning.concurrency;

/**
 * Created by Jack.Xu on 7/18/15.
 */
public class Util {

    public static int transmogrify(int v) {
        return Character.isLetter(v) ? v ^ ' ' : v;
    }
}
