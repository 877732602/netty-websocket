package rdlsmile.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;


public class WebsocketDanmuServerInitialize extends ChannelInitializer<SocketChannel>{
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("http_decodec", new HttpRequestDecoder());
        pipeline.addLast("http_aggregator", new HttpObjectAggregator(65535));
        pipeline.addLast("http_encodec", new HttpResponseEncoder());
        pipeline.addLast("http_chunked", new ChunkedWriteHandler());

        pipeline.addLast("http_request", new HttpRequestHandler("/ws"));
        pipeline.addLast("WebSocket_protocol", new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast("WebSocket_request", new TextWebSocketFrameHandler());
    }



}
