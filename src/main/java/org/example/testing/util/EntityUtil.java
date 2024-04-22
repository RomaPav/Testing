package org.example.testing.util;

public class EntityUtil {
    public static boolean isValidCode(String code){
        return code.length() >= 6;
    }
    public static boolean isValidName(String name){
        return name != null && !name.isEmpty() && name.length() > 3 && !name.contains("test");
    }
    public static boolean isValidDescription(String description){
        return !description.contains("test");
    }
}
