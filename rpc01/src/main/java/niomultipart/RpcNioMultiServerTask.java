package niomultipart;

import common.BeanContainer;
import common.RequestMultiObject;
import common.SerializeUtil;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * <Description>
 *
 * @author wangxi
 * 用于获取数据执行远端方法的线程池任务类
 */
public class RpcNioMultiServerTask implements Runnable{
    private byte[] bytes;

    private SocketChannel channel;

    public RpcNioMultiServerTask(byte[] bytes, SocketChannel channel) {
        this.bytes = bytes;
        this.channel = channel;
    }

    @Override
    public void run() {
        if (bytes != null && bytes.length > 0 && channel != null) {
            // 反序列化
            RequestMultiObject requestMultiObject = (RequestMultiObject) SerializeUtil.unSerialize(bytes);
            // 调用服务并序列化结果然后返回
            requestHandle(requestMultiObject, channel);
        }
    }

    public void requestHandle(RequestMultiObject requestObject, SocketChannel channel) {
        Long requestId = requestObject.getRequestId();
        Object obj = BeanContainer.getBean(requestObject.getCalzz());
        String methodName = requestObject.getMethodName();
        Class<?>[] parameterTypes = requestObject.getParamTypes();
        Object[] arguments = requestObject.getArgs();
        try {
            Method method = obj.getClass().getMethod(methodName, parameterTypes);
            String result = (String) method.invoke(obj, arguments);
            System.out.println("============provider端===执行客户端方法，结果为" +result);
            byte[] bytes = SerializeUtil.serialize(result);
            ByteBuffer buffer = ByteBuffer.allocate(bytes.length + 12);
            // 为了便于客户端获得请求ID，直接将id写在头部（这样客户端直接解析即可获得，不需要将所有消息反序列化才能得到）
            // 然后写入消息题的长度，最后写入返回内容
            buffer.putLong(requestId);  // long型id占8字节，内容长度int占4字节
            buffer.putInt(bytes.length);
            buffer.put(bytes);
            buffer.flip();
            channel.write(buffer);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | IOException e) {
            e.printStackTrace();
        }
    }

    public SocketChannel getChannel() {
        return channel;
    }

    public void setChannel(SocketChannel channel) {
        this.channel = channel;
    }

}

