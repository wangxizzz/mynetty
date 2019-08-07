package common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <Description>
 *
 * @author wangxi
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestMultiObject {
    private static final long serialVersionUID = 3132836600205356306L;

    // 请求id
    private Long requestId;

    // 服务提供者接口
    private Class<?> calzz;

    // 服务的方法名称
    private String methodName;

    // 参数类型
    private Class<?>[] paramTypes;

    // 参数
    private Object[] args;

    public RequestMultiObject(Class<?> clazz, String methodName, Class<?>[] paramTypes, Object[] args) {
        this.calzz = clazz;
        this.methodName = methodName;
        this.paramTypes = paramTypes;
        this.args = args;
    }

}

