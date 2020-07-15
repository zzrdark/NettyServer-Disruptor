package cn.iocoder.springboot.lab67.nettycommondemo.codec;

import io.netty.channel.ChannelHandlerContext;

/**
 * @ClassName InvocationWrapp
 * @Author zzrdark
 * @Date 2020-07-15 19:26
 * @Description TODO
 **/
public class InvocationWrapper  {

    private Invocation invocation;

    private ChannelHandlerContext channelHandlerContext;

    public InvocationWrapper(Invocation invocation, ChannelHandlerContext channelHandlerContext) {
        this.invocation = invocation;
        this.channelHandlerContext = channelHandlerContext;
    }

    public InvocationWrapper() {
    }

    public Invocation getInvocation() {
        return invocation;
    }

    public void setInvocation(Invocation invocation) {
        this.invocation = invocation;
    }

    public ChannelHandlerContext getChannelHandlerContext() {
        return channelHandlerContext;
    }

    public void setChannelHandlerContext(ChannelHandlerContext channelHandlerContext) {
        this.channelHandlerContext = channelHandlerContext;
    }
}
