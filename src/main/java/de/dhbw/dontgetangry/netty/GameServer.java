package de.dhbw.dontgetangry.netty;

import de.dhbw.dontgetangry.GameUpdateHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.CharsetUtil;

import java.util.HashMap;
import java.util.Map;

public class GameServer {

    private final Map<String, String> gameUpdates;
    private final GameUpdateHandler gameUpdateHandler;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private Channel serverChannel;

    public GameServer(GameUpdateHandler gameUpdateHandler) {
        this.gameUpdateHandler = gameUpdateHandler;
        gameUpdates = new HashMap<>();
    }

    public void start(int port) throws Exception {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<>() {
                        @Override
                        protected void initChannel(Channel channel) {
                            ChannelPipeline pipeline = channel.pipeline();
                            pipeline.addLast(new GameServerHandler(gameUpdateHandler));
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture future = bootstrap.bind(port).sync();
            serverChannel = future.channel();
            System.out.println("Server started on port " + port);
        } catch (Exception e) {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            throw e;
        }
    }

    public void stop() throws InterruptedException {
        if (serverChannel != null) {
            serverChannel.close().sync();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }

        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        System.out.println("Server stopped");
    }

    private class GameServerHandler extends ChannelInboundHandlerAdapter {

        private GameUpdateHandler gameUpdateHandler;
        public GameServerHandler(GameUpdateHandler gameUpdateHandler) {
            this.gameUpdateHandler = gameUpdateHandler;
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            ByteBuf byteBuf = (ByteBuf) msg;
            String receivedData = byteBuf.toString(CharsetUtil.UTF_8);
            System.out.println(receivedData);
            String[] keyValue = receivedData.split(":");

            String player = keyValue[0];
            String update = keyValue[1];

            synchronized (gameUpdates) {
                gameUpdates.put(player, update);
            }
            this.gameUpdateHandler.handleUpdate(player, update);
            ctx.close();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
    }

}
