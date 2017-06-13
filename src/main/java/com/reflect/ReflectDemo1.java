package com.reflect;

public class ReflectDemo1 {

    public static void main(String[] args){
        //Foo类实例对象表示
        Foo foo = new Foo();
        //三种表达方式
        Class c1 = Foo.class;
        Class c2 = foo.getClass();
        System.out.println(c1 == c2);
        try {
            Class c3 = Class.forName("com.reflect.Foo");
            System.out.println(c2 == c3);

            Foo foo1 = (Foo) c1.newInstance();
            foo1.print();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

class Foo{

    void print(){
        System.out.println("===========");
    }

}