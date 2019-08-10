package niomultipart;

import common.RequestMultiObject;
import common.RpcContainer;
import common.RpcResponseFuture;
import common.SerializeUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * <Description>
 *
 * @author wangxi
 */
public class RpcNIoMultiHandler implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        // 获得请求id
        Long responseId = RpcContainer.getRequestId();
        // 封装请求对象
        RequestMultiObject requestMultiObject = new RequestMultiObject(method.getDeclaringClass(), method.getName(),
                method.getParameterTypes(), args);
        requestMultiObject.setRequestId(responseId);

        // 封装设置rpcResponseFuture，主要用于获取返回值
        RpcResponseFuture rpcResponseFuture = new RpcResponseFuture(responseId);
        RpcContainer.addRequestFuture(rpcResponseFuture);

        // 序列化
        byte[] requestBytes = SerializeUtil.serialize(requestMultiObject);
        // 发送请求信息
        RpcNioMultiClient rpcNioMultiClient = RpcNioMultiClient.getInstance();
        rpcNioMultiClient.sendMsg2Server(requestBytes);

        // 从ResponseContainer获取返回值
        byte[] responseBytes = rpcResponseFuture.get();
        if (requestBytes != null) {
            RpcContainer.removeResponseAndFuture(responseId);
        }

        // 反序列化获得结果
        Object result = SerializeUtil.unSerialize(responseBytes);
        System.out.println("请求id：" + responseId + " 结果：" + result);
        return result;
    }
}

