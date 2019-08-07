package common;

import java.util.concurrent.ConcurrentHashMap;

/**
 * <Description>
 *服务端服务发布时使用的bean容器：
 * @author wangxi
 */
public class BeanContainer {
    private static ConcurrentHashMap<Class<?>, Object> container = new ConcurrentHashMap<>();

    public static boolean addBean(Class<?> clazz, Object object) {
        container.put(clazz, object);
        return true;
    }

    public static Object getBean(Class<?> clazz) {
        return container.get(clazz);
    }
}

