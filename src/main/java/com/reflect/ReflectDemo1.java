package com.reflect;

import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

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

        System.out.println("=================================");

        Method[] methods = Foo.class.getDeclaredMethods();
        for (Method method : methods){
            System.out.println(method.getName());
        }

        System.out.println("=================================");
        try {
            foo.setId(1L);
            foo.setName("1");
            foo.setPhone("1");
            foo.setSex(1);
            Field[] fields = foo.getClass().getDeclaredFields();
            for (Field field : fields){
                field.setAccessible(true);
                System.out.println("fieldName:"+field.getName());
                System.out.println("fieldType:"+field.getType());
                System.out.println("fieldValue:"+field.get(foo));
                System.out.println(field.getType().toString().contains("String"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}

class Foo{

    private Long id;
    private String name;
    private int sex;
    private String phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    void print(){
        System.out.println("@@@@@@@@@@@@@@");
    }

}