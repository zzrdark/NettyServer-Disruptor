package cn.iocoder.springboot.lab67.nettyserverdemo.server.handler;

import cn.iocoder.springboot.lab67.nettycommondemo.codec.Invocation;
import cn.iocoder.springboot.lab67.nettycommondemo.codec.InvocationWrapper;
import cn.iocoder.springboot.lab67.nettyserverdemo.server.DisruptorBoot;
import com.lmax.disruptor.EventTranslator;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName MessageDispatcherHandler
 * @Author zzrdark
 * @Date 2020-07-15 19:56
 * @Description TODO
 **/
@ChannelHandler.Sharable
@Component
public class MessageDispatcherHandler extends SimpleChannelInboundHandler<Invocation> {


    @Autowired
    private DisruptorBoot disruptorBoot;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Invocation msg) throws Exception {


        disruptorBoot.getRingBuffer().publishEvent(new InvocationEventTranslator(ctx,msg));
    }


    static class InvocationEventTranslator implements EventTranslator<InvocationWrapper>{

        private ChannelHandlerContext ctx;

        private Invocation invocation;

        public InvocationEventTranslator(ChannelHandlerContext ctx, Invocation invocation) {
            this.ctx = ctx;
            this.invocation = invocation;
        }

        @Override
        public void translateTo(InvocationWrapper event, long sequence) {
            event.setChannelHandlerContext(ctx);
            event.setInvocation(invocation);
        }
    }
}
