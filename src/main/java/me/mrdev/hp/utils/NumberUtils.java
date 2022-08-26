package me.mrdev.hp.utils;

import java.io.IOException;

public class NumberUtils {

    public static boolean isLong(String string) {
        if(string == null) return false;
        try {
            Long.parseLong(string);
        }catch (NumberFormatException ignored) {
            return false;
        }
        return true;
    }

}
