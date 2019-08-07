package service;

/**
 * <Description>
 *
 * @author wangxi
 */
public class HelloServiceImpl implements HelloService{
    @Override
    public String sayHello(String name) {
        return "hello -------" + name;
    }
}

