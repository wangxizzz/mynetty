package jdk动态代理.demo02;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by wxi.wang
 * <p>
 * 2019/8/8 19:31
 * Decription:
 */

/**
 * 1. 可以使用反射获取代理对象(Account表示原对象)的信息（也就是proxy.getClass().getName()）。
 *
 * 2. 可以将代理对象返回以进行连续调用，这就是proxy存在的目的，因为this并不是代理对象。
 */
public class ExampleInvocationHandler implements InvocationHandler {
    private double balance;

    /**
     *
     * @param proxy  代理对象(不是原始对象)
     * @param method 要执行的方法(原始对象需要执行的方法)
     * @param args 要执行方法的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke (Object proxy, Method method, Object[] args) throws Throwable {

        // simplified method checks, would need to check the parameter count and types too
        if ("deposit".equals(method.getName())) {
            Double value = (Double) args[0];
            System.out.println("deposit: " + value);
            method.invoke(args);
            balance += value;
            return proxy; // here we use the proxy to return 'this'
        }
        if ("getBalance".equals(method.getName())) {
            return balance;
        }
        return null;
    }
}
