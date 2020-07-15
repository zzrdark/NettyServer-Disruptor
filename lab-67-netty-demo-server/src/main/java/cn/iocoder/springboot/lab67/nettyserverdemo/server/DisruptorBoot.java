package cn.iocoder.springboot.lab67.nettyserverdemo.server;

import cn.iocoder.springboot.lab67.nettycommondemo.dispatcher.MessageHandlerContainer;
import cn.iocoder.springboot.lab67.nettycommondemo.disruptor.InvocationWrapperEventFactory;
import cn.iocoder.springboot.lab67.nettycommondemo.disruptor.MessageDispatcherEventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName DisruptorBoot
 * @Author zzrdark
 * @Date 2020-07-15 19:38
 * @Description 这里使用Disruptor进行消费
 * 因为没有耗时操作 则使用 多生产-单消费模式
 **/
@Component
public class DisruptorBoot {

    private RingBuffer ringBuffer;

    private Disruptor disruptor;

    @Autowired
    private MessageHandlerContainer messageHandlerContainer;
    /**
     * 由于单消费者模式，单线程便就好
      */
    private ExecutorService es = Executors.newSingleThreadExecutor();

    @PostConstruct
    public void start(){
        disruptor = new Disruptor(
                new InvocationWrapperEventFactory(),
                1024,
                es,
                // 这里的选择会影响到Sequencer的类型
                ProducerType.MULTI,
                new YieldingWaitStrategy()
        );

        disruptor.handleEventsWith(new MessageDispatcherEventHandler(messageHandlerContainer));
        ringBuffer = disruptor.start();

    }

    @PreDestroy
    public void shutdown() {
        if (!es.isShutdown()){
            es.shutdown();
        }
        disruptor.shutdown();
    }

    public RingBuffer getRingBuffer() {
        return ringBuffer;
    }
}
