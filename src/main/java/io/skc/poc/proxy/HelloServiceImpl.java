package io.skc.poc.proxy;

public class HelloServiceImpl implements HelloService{

    public void sayHello() {
        System.out.println("HelloServiceImpl#sayHello : Says Hello from JVM");
    }

    public void sayHello(String name) {
        System.out.println("HelloServiceImpl#sayHello :  Hello"+name);
    }
}
