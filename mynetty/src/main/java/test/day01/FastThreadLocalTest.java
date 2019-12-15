package test.day01;

import io.netty.util.concurrent.FastThreadLocal;

/**
 * @Author wangxi
 * @Time 2019/12/15 17:56
 */
public class FastThreadLocalTest {

    private static FastThreadLocal<String> threadLocal = new FastThreadLocal<>();

    public static void main(String[] args) {
        threadLocal.set("aaa");
        System.out.println(threadLocal.get());
        threadLocal.remove();
    }
}
