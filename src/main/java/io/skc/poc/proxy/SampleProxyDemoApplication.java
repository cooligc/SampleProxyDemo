package io.skc.poc.proxy;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SpringBootApplication
public class SampleProxyDemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SampleProxyDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println("============ ENTRY =================");
//		dynamicInvocation();
//		beanCreationDynamic();
		beanCreationStatic();

		System.out.println("============ EXIT =================");
	}

	public static void beanCreationDynamic() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		BeanGenerator beanGenerator = new BeanGenerator();

		beanGenerator.addProperty("name", String.class);
		Object myBean = beanGenerator.create();
		Method setter = myBean.getClass().getMethod("setName", String.class);
		setter.invoke(myBean, "some string value set by a cglib");

		Method getter = myBean.getClass().getMethod("getName");
		System.out.println(getter.invoke(myBean));
	}

	public static void beanCreationStatic() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		BeanGenerator beanGenerator = new BeanGenerator();
		beanGenerator.setSuperclass(HelloServiceImpl.class);
		Object myBean = beanGenerator.create();
		Method method1 = myBean.getClass().getMethod("sayHello");
		method1.invoke(myBean);

		Method method2 = myBean.getClass().getMethod("sayHello", String.class);
		System.out.println(method2.invoke(myBean,"sitakant"));
	}

	public static void dynamicInvocation(){
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(HelloServiceImpl.class);
		enhancer.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
			return proxy.invokeSuper(obj, args);
		});
		HelloService proxy = (HelloService) enhancer.create();

		proxy.sayHello("sitakant");
	}

}
