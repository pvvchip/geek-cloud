package ru.pvvchip.cloud.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import ru.pvvchip.cloud.common.FileListSrv;
import ru.pvvchip.cloud.common.FileMessage;
import ru.pvvchip.cloud.common.FileRequest;
import ru.pvvchip.cloud.common.FileSend;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class MainHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            if (msg == null) {
                return;
            }
            if (msg instanceof FileRequest) {
                FileRequest fr = (FileRequest) msg;
                if (Files.exists(Paths.get("storage_server/" + fr.getFilename()))) {
                    FileMessage fm = new FileMessage(Paths.get("storage_server/" + fr.getFilename()));
                    ctx.writeAndFlush(fm);
                }
            }
            if (msg instanceof FileListSrv) {
                ArrayList<String> arrayList = new ArrayList<>();
                Files.list(Paths.get("storage_server")).map(p -> p.getFileName().toString()).forEach(o ->
                        arrayList.add(o));
                FileListSrv fileList = new FileListSrv(arrayList);
                ctx.writeAndFlush(fileList);
            }
            if (msg instanceof FileSend) {

                FileSend fs = (FileSend) msg;
                Files.write(Paths.get("storage_server/" + fs.getFilename()),
                        fs.getData(), StandardOpenOption.CREATE);
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
