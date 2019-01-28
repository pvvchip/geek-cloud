package ru.pvvchip.cloud.server;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import ru.pvvchip.cloud.common.FileListSrv;
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
            if (msg instanceof FileListSrv) {
                ArrayList<String> arrayList = new ArrayList<>();
                Files.list(Paths.get("storage_server")).map(p -> p.getFileName().toString()).forEach(o ->
                        arrayList.add(o));
                FileListSrv fileList = new FileListSrv(arrayList);
                ctx.writeAndFlush(fileList);
            }
            if (msg instanceof FileSend) {

                if (((FileSend) msg).getData() != null) {
                    FileSend fs = (FileSend) msg;
                    Files.write(Paths.get("storage_server/" + fs.getFilename()),
                            fs.getData(), StandardOpenOption.CREATE);
                } else {
                    ((FileSend) msg).setData(Paths.get("storage_server/" + ((FileSend) msg).getFilename()));
                    ctx.writeAndFlush(msg);
                }
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
