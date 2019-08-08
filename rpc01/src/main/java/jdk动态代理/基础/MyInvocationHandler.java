package jdk动态代理.基础;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by wxi.wang
 * <p>
 * 2019/8/8 16:56
 * Decription:
 */
public class MyInvocationHandler implements InvocationHandler {
    /**
     * 目标对象
     */
    private Object target;

    public MyInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(proxy.getClass().getName());
        System.out.println("------插入前置通知代码-------------");
        // 执行相应的目标方法
        Object rs = method.invoke(target, args);   // 执行目标方法时，需要传递target对象。
//        Object rs = method.invoke(args);   这个会报空指针
        System.out.println("------插入后置处理代码-------------");
        return null;
    }
}
