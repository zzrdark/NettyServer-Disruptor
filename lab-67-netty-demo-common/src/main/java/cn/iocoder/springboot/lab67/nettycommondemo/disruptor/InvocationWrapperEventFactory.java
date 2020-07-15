package cn.iocoder.springboot.lab67.nettycommondemo.disruptor;

import cn.iocoder.springboot.lab67.nettycommondemo.codec.InvocationWrapper;
import com.lmax.disruptor.EventFactory;

/**
 * @ClassName InvocationWrapperEventFactory
 * @Author zzrdark
 * @Date 2020-07-15 19:43
 * @Description 工厂类
 * @See {@link InvocationWrapper}
 **/
public class InvocationWrapperEventFactory implements EventFactory<InvocationWrapper> {
    @Override
    public InvocationWrapper newInstance() {
        return new InvocationWrapper();
    }
}
