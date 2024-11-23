package com.luongnv.practice.reflection;

import com.basicsstrong.reflection.Entity;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ModifierInfo {
    public static void main(String[] args) throws NoSuchMethodException {
        Entity e = new Entity(10,"id");
        Class<? extends Entity> clss = e.getClass();
        int classModifier = clss.getModifiers(); // return modifier of class
        System.out.println(Modifier.isPublic(classModifier));
        System.out.println(Modifier.toString(classModifier));

        Method method = clss.getMethod("getVal");
        int methodModifier = method.getModifiers();
        System.out.println(Modifier.isPublic(methodModifier));
        System.out.println(Modifier.toString(methodModifier));

    }
}
