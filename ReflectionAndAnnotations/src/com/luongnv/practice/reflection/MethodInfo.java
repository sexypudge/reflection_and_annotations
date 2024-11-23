package com.luongnv.practice.reflection;

import com.basicsstrong.reflection.Entity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodInfo {
    public static void main(String[] args) throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Entity entity = new Entity(10, "id");
        Class<? extends Entity> clss = entity.getClass();
        Class<?> aClass = Class.forName("com.basicsstrong.reflection.Entity");

        Constructor<?> constructor = aClass.getDeclaredConstructor();
        constructor.setAccessible(true);
        Object o = constructor.newInstance();
        Method setVal = clss.getDeclaredMethod("setVal", int.class);
        setVal.invoke(o, 15);

        System.out.println(o);
    }
}
