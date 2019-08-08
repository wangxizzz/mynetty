package jdk动态代理.基础;

/**
 * Created by wxi.wang
 * <p>
 * 2019/8/8 16:56
 * Decription:
 */
public class HelloImpl implements IHello{
    @Override
    public void sayHello() {
        System.out.println("Hello world!");
    }

    @Override
    public void eat() {
        System.out.println("吃饭.....");
    }
}
