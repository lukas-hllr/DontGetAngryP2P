package de.dhbw.dontgetangry.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

public class GameClient {

    private final String player;
    private EventLoopGroup group;
    private Channel clientChannel;

    public String getPlayer() {
        return player;
    }

    public GameClient(String player) {
        this.player = player;
    }

    public void sendUpdate(String update, String serverHost, int serverPort) throws Exception {

        group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel channel) {
                            ChannelPipeline pipeline = channel.pipeline();
                            pipeline.addLast(new GameClientHandler(player, update));
                        }
                    });

            ChannelFuture future = bootstrap.connect(serverHost, serverPort).sync();
            clientChannel = future.channel();
            clientChannel.closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    public void stop() throws InterruptedException {
        if (clientChannel != null) {
            clientChannel.close().sync();
        }
        if (group != null) {
            group.shutdownGracefully();
        }
        System.out.println("Client stopped");
    }

    private class GameClientHandler extends ChannelInboundHandlerAdapter {

        private String player;
        private String update;

        public GameClientHandler(String player, String update) {
            this.player = player;
            this.update = update;
        }

        @Override
        public void channelActive(ChannelHandlerContext ctx) {
            String data = player + ":" + update;
            ByteBuf byteBuf = Unpooled.copiedBuffer(data, CharsetUtil.UTF_8);
            ctx.writeAndFlush(byteBuf);
            ctx.close();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
    }

    public static void main(String[] args) throws Exception {
        String serverHost = "localhost"; // Specify the server host
        int serverPort = 5000; // Specify the server port

        GameClient client = new GameClient("Player1");
        client.sendUpdate("Game update message", serverHost, serverPort);
    }
}
