package com.crgt.advanced.basic.raft.demo.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;

/**
 * @ClassName LeaderEndpoint
 * @Description 节点通信组件
 * @Author justin.sun@crgecent.com
 * @Date 2020/6/12 16:26
 * @Version 1.0
 **/
public class NodeEndpoint {


    private final int port;

    public NodeEndpoint(int port) {
        this.port = port;
    }

    public void start() throws InterruptedException {

        ServerBootstrap bootstrap = new ServerBootstrap();
        EventLoopGroup masterGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        // 构建NIO通信框架
        bootstrap
                .group(masterGroup, workerGroup)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .channel(NioServerSocketChannel.class)
                ;

        ChannelFuture future = bootstrap.bind(new InetSocketAddress(port)).sync();
        System.out.println("server started on port : " + port);
        future.channel().closeFuture().sync();

    }

    public static void main(String[] args) throws InterruptedException {
        NodeEndpoint serverEnd = new NodeEndpoint(10080);
        serverEnd.start();
    }

}
