package com.luongnv.practice.reflection;

public class ConstructorInfo {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException {
        Class<?> clss = Class.forName("com.basicsstrong.reflection.Entity");
        clss.getConstructors();
        clss.getDeclaredConstructor();
    }
}
