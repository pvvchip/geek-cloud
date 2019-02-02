package ru.pvvchip.cloud.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import ru.pvvchip.cloud.common.AbstractMessage;
import ru.pvvchip.cloud.common.FileDel;
import ru.pvvchip.cloud.common.FileListSrv;
import ru.pvvchip.cloud.common.FileSend;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class MainHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String dir;
        try {
            if (msg == null) {
                return;
            }
            if (msg instanceof FileListSrv) {
                dir = "storage_server/" + ((AbstractMessage) msg).getLg();
                isDir(dir);

                ArrayList<String> arrayList = new ArrayList<>();

                Files.list(Paths.get(dir)).map(p -> p.getFileName().toString()).forEach(o ->
                        arrayList.add(o));
                FileListSrv fileList = new FileListSrv(arrayList, "0", "0");
                ctx.writeAndFlush(fileList);

                System.out.println(((FileListSrv) msg).getLg());
                System.out.println(((FileListSrv) msg).getPw());
            }
            if (msg instanceof FileSend) {

                dir = "storage_server/" + ((AbstractMessage) msg).getLg();
                isDir(dir);

                if (((FileSend) msg).getData() != null) {
                    FileSend fs = (FileSend) msg;
                    Files.write(Paths.get(dir + "/" + fs.getFilename()), fs.getData(), StandardOpenOption.CREATE);
                } else {
                    ((FileSend) msg).setData(Paths.get(dir + "/" + ((FileSend) msg).getFilename()));
                    ctx.writeAndFlush(msg);
                }
            }
            if (msg instanceof FileDel) {

                dir = "storage_server/" + ((AbstractMessage) msg).getLg();

                String nameFile = ((FileDel) msg).getName();
                Files.delete(Paths.get(dir + "/" + nameFile));
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    private void isDir(String dir) {
        Path paths = Paths.get(dir);
        boolean is = Files.isExecutable(paths);
        if (!is) {
            try {
                Files.createDirectory(paths);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
