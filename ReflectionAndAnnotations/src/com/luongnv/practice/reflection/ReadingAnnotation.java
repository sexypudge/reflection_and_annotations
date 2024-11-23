package com.luongnv.practice.reflection;

import com.basicsstrong.annotation.MostUsed;
import com.basicsstrong.annotation.Utility;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReadingAnnotation {

    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {

        Class<?> aClass = Class.forName("com.basicsstrong.annotation.Utility");
        Constructor<?> constructors = aClass.getConstructor();
        Utility u = (Utility) constructors.newInstance();

//        Method[] methods = aClass.getMethods();
        Method[] methods = aClass.getDeclaredMethods(); // get all method

        for (Method method : methods) {
            if (method.isAnnotationPresent(MostUsed.class)) {
                MostUsed annotation = method.getAnnotation(MostUsed.class);
                ;
                method.invoke(u, annotation.value());
            }
        }

    }
}
