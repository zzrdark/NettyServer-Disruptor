package cn.iocoder.springboot.lab67.nettycommondemo.disruptor;

import cn.iocoder.springboot.lab67.nettycommondemo.codec.InvocationWrapper;
import cn.iocoder.springboot.lab67.nettycommondemo.dispatcher.Message;
import cn.iocoder.springboot.lab67.nettycommondemo.dispatcher.MessageHandler;
import cn.iocoder.springboot.lab67.nettycommondemo.dispatcher.MessageHandlerContainer;
import com.alibaba.fastjson.JSON;
import com.lmax.disruptor.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName MessageDispatcherEventHandler
 * @Author zzrdark
 * @Date 2020-07-15 19:30
 * @Description TODO
 **/
public class MessageDispatcherEventHandler implements EventHandler<InvocationWrapper> {

    @Autowired
    private MessageHandlerContainer messageHandlerContainer;

    @Override
    public void onEvent(InvocationWrapper event, long sequence, boolean endOfBatch) throws Exception {

        // 获得 type 对应的 MessageHandler 处理器
        MessageHandler messageHandler = messageHandlerContainer.getMessageHandler(event.getInvocation().getType());
        // 获得  MessageHandler 处理器 的消息类
        Class<? extends Message> messageClass = MessageHandlerContainer.getMessageClass(messageHandler);
        // 解析消息
        Message message = JSON.parseObject(event.getInvocation().getMessage(), messageClass);
        // 执行逻辑
        // noinspection unchecked
        messageHandler.execute(event.getChannelHandlerContext().channel(), message);
    }
}
