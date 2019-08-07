package common;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <Description>
 *RPC容器 用来存储发送RPC请求时的请求对象，以及存储返回值
 * @author wangxi
 */
public class RpcContainer {
    //返回值容器
    private static ConcurrentHashMap<Long, byte[]> responseContainer = new ConcurrentHashMap<>();
    //请求对象容器
    private static ConcurrentHashMap<Long, RpcResponseFuture> requestFuture = new ConcurrentHashMap<>();
    //请求id
    private volatile static AtomicLong requestId = new AtomicLong(0);

    public static Long getRequestId() {
        return requestId.getAndIncrement();
    }

    public static void addResponse(Long requestId, byte[] responseBytes) {
        responseContainer.put(requestId, responseBytes);
        RpcResponseFuture responseFuture = requestFuture.get(requestId);
        responseFuture.rpcIsDone();
    }

    public static byte[] getResponse(Long requestId) {
        return responseContainer.get(requestId);
    }

    public static void addRequestFuture(RpcResponseFuture rpcResponseFuture) {
        requestFuture.put(rpcResponseFuture.getRequstId(), rpcResponseFuture);
    }

    public static RpcResponseFuture getRpcRequestFuture(Long requestId) {
        return requestFuture.get(requestId);
    }

    public static void removeResponseAndFuture(Long requestId) {
        responseContainer.remove(requestId);
        requestFuture.remove(requestId);
    }
}

