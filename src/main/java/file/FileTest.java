package file;

import java.io.*;

public class FileTest {
    private static int position = -1;
    public static void main(String[] args) {
        Test1();
    }

    /**
     * 断点续传
     */
    private static void Test1() {
        // 源文件与目标文件
        File sourceFile = new File("F:/", "test1.txt");
        File targetFile = new File("F:/", "test2.txt");
        // 输入输出流
        FileInputStream fis = null;
        FileOutputStream fos = null;
        // 数据缓冲区
        byte[] buf = new byte[1024];

        try {
            fis = new FileInputStream(sourceFile);
            fos = new FileOutputStream(targetFile);
            // 数据读写
            int len;
            while ((len = fis.read(buf) )!= -1) {
                fos.write(buf, 0, len);
                // 当已经上传了2000字节的文件内容时，网络中断了，抛出异常
                if (targetFile.length() >2000) {
                    position = 2000;
                    throw new FileAccessException();
                }
            }
        } catch (FileAccessException e) {
            keepGoing(sourceFile,targetFile, position);
        } catch (FileNotFoundException e) {
            System.out.println("指定文件不存在");
        } catch (IOException e) {
            // TODO: handle exception
        } finally {
            try {
                // 关闭输入输出流
                if (fis != null)
                    fis.close();

                if (fos != null)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 保持继续上传
     * @param source
     * @param target
     * @param position
     */
    private static void keepGoing(File source,File target, int position) {
        try {
            System.out.println("发生网络中断");
            //模拟中断事件，设置为10s
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            RandomAccessFile readFile = new RandomAccessFile(source, "rw");
            RandomAccessFile writeFile = new RandomAccessFile(target, "rw");
            readFile.seek(position);
            writeFile.seek(position);

            // 数据缓冲区
            byte[] buf = new byte[1024];
            // 数据读写
            int len;
            while ((len = readFile.read(buf) )!= -1) {
                writeFile.write(buf, 0, len);
            }
            System.out.println("下载完成");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
