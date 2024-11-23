package com.luongnv.practice.annotation;

public class Utility {
    @MostUsed("Python")
    public void show() {
        System.out.println("This is Utility Class");
    }
//    @MostImportant
    public void veryImportant() {
        System.out.println("This is Very Important");
    }
    public void important() {
        System.out.println("This is Important");
    }
    public void notImportant() {
        System.out.println("This is Not Important");
    }
    public void notImportantAtAll() {
        System.out.println("This is Not Important At All");
    }
//    @RunImmediately
    public void runImmediately() {
        System.out.println("This method should be run immediately");
    }

}
