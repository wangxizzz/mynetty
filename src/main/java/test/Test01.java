package test;

import io.netty.util.NettyRuntime;
import io.netty.util.internal.SystemPropertyUtil;

/**
 * <Description>
 *
 * @author wangxi
 */
public class Test01 {
    public static void main(String[] args) {
        int result = Math.max(1, SystemPropertyUtil.getInt(
                "io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
        System.out.println(result);
    }
}

