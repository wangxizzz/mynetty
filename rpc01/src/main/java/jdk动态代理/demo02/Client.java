package jdk动态代理.demo02;

import java.io.Serializable;
import java.lang.reflect.Proxy;

/**
 * Created by wxi.wang
 * <p>
 * 2019/8/8 19:33
 * Decription:
 */
public class Client {
    public static void main(String[] args) {

        Account account = (Account) Proxy.newProxyInstance(Account.class.getClassLoader(), new Class[] {Account.class, Serializable.class},
                new ExampleInvocationHandler());    // 真正的接口实现类可以不用传进去
        // method chaining for the win!
        account.deposit(5000).deposit(4000).deposit(-2500);
        System.out.println("Balance: " + account.getBalance());
    }
}
