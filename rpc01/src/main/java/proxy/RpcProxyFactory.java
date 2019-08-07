package proxy;

import niomultipart.RpcNIoMultiHandler;

import java.lang.reflect.Proxy;

/**
 * <Description>
 *
 * @author wangxi
 */
public class RpcProxyFactory {
    /**
     * 多线程环境代理对象
     *
     * @param interfaceClass
     * @return T
     * @createTime：2018/7/1
     */
    public static <T> T getMultiService(Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[] { interfaceClass },
                new RpcNIoMultiHandler());
    }
}

