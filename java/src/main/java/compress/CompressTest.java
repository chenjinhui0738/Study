package compress;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class CompressTest {
    public static void main(String[] args) throws Exception {
        FileInputStream fis = new FileInputStream(new File("F:\\cjh_idea_workspace\\Study\\java\\src\\main\\resources\\test.txt"));
        FileChannel channel = fis.getChannel();
        ByteBuffer bb = ByteBuffer.allocate((int) channel.size());
        channel.read(bb);
        byte[] beforeBytes = bb.array();

        int times = 2000;
        System.out.println("压缩前大小：" + beforeBytes.length + " bytes");
        long startTime1 = System.currentTimeMillis();
        byte[] afterBytes = null;
        for (int i = 0; i < times; i++) {
            afterBytes = GZIPUtil.compress(beforeBytes);
        }
        long endTime1 = System.currentTimeMillis();
        System.out.println("压缩后大小：" + afterBytes.length + " bytes");
        System.out.println("压缩次数：" + times + "，时间：" + (endTime1 - startTime1)
                + "ms");

        byte[] resultBytes = null;
        long startTime2 = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            resultBytes = GZIPUtil.uncompress(afterBytes);
        }
        System.out.println("解压缩后大小：" + resultBytes.length + " bytes");
        long endTime2 = System.currentTimeMillis();
        System.out.println("解压缩次数：" + times + "，时间：" + (endTime2 - startTime2)
                + "ms");
    }
}
