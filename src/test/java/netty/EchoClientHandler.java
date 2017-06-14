package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ByteProcessor;
import io.netty.util.CharsetUtil;

public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		String ss = "hello netty";
		
		ctx.writeAndFlush(Unpooled.copiedBuffer(ss, CharsetUtil.UTF_8));
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
		System.out.println("readerIndex="+msg.readerIndex()+",writerIndex="+msg.writerIndex()+",capacity="+msg.capacity());
//		int len = msg.readableBytes();
//		byte[] arr = new byte[len];
//		msg.getBytes(0, arr);
//		System.out.println("Client received: " + new String(arr));
		
		System.out.println("Client received: " + new String(ByteBufUtil.getBytes(msg.readBytes(msg.readableBytes()))));
		System.out.println("readerIndex="+msg.readerIndex()+",writerIndex="+msg.writerIndex()+",capacity="+msg.capacity());
		msg.discardReadBytes();
		System.out.println("readerIndex="+msg.readerIndex()+",writerIndex="+msg.writerIndex()+",capacity="+msg.capacity());
		
//		System.out.println("Client received: " + ByteBufUtil.hexDump(msg.readBytes(msg.readableBytes())));
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
