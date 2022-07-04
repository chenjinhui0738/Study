package FreeMark;

import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class TestFreeMarker {
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(TestFreeMarker.class);
    private static String document = "document.xml";

    //outputStream 输出流可以自己定义 浏览器或者文件输出流
    public static void createDocx(Map dataMap, OutputStream outputStream) {
        ZipOutputStream zipout = null;
        try {
            /*//图片配置文件模板
            ByteArrayInputStream documentXmlRelsInput = FreeMarkUtils.getFreemarkerContentInputStream(dataMap, documentXmlRels);*/

            //内容模板
            ByteArrayInputStream documentInput = FreeMarkUtils.getFreemarkerContentInputStream(dataMap, document);
            //内容模板
            ByteArrayInputStream settingInput = FreeMarkUtils.getFreemarkerContentInputStream(dataMap, "custom.xml");
            //最初设计的模板
            //File docxFile = new File(WordUtils.class.getClassLoader().getResource(template).getPath());
            File docxFile = new File("F:\\test\\test.zip");//换成自己的zip路径
            if (!docxFile.exists()) {
                docxFile.createNewFile();
            }
            ZipFile zipFile = new ZipFile(docxFile);
            Enumeration<? extends ZipEntry> zipEntrys = zipFile.entries();
            zipout = new ZipOutputStream(outputStream);
            //开始覆盖文档------------------
            int len = -1;
            byte[] buffer = new byte[1024];
            while (zipEntrys.hasMoreElements()) {
                ZipEntry next = zipEntrys.nextElement();
                InputStream is = zipFile.getInputStream(next);
                if (next.toString().indexOf("media") < 0) {
                    zipout.putNextEntry(new ZipEntry(next.getName()));
                    if ("word/document.xml".equals(next.getName())) {//如果是word/document.xml由我们输入
                        if (documentInput != null) {
                            while ((len = documentInput.read(buffer)) != -1) {
                                zipout.write(buffer, 0, len);
                            }
                            documentInput.close();
                        }
                    } else if ("docProps/custom.xml".equals(next.getName())) {
                        if (settingInput != null) {
                            while ((len = settingInput.read(buffer)) != -1) {
                                zipout.write(buffer, 0, len);
                            }
                            settingInput.close();
                        }
                    } else {
                        while ((len = is.read(buffer)) != -1) {
                            zipout.write(buffer, 0, len);
                        }
                        is.close();
                    }
                }
            }

        } catch (Exception e) {
            logger.info(String.valueOf(e));
            //logger.error();
        } finally {
            if (zipout != null) {
                try {
                    zipout.close();
                } catch (IOException e) {
                    System.out.println("io异常");

                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    System.out.println("io异常");
                }
            }
        }
    }

    public static void main(String arg[]) {
        Map dataMap = new HashMap();
        dataMap.put("name", "zhangsan");
        dataMap.put("fileMark", "123456");


        //指定输出docx路径
        File outFile = new File("F:\\test\\test.docx");
        try {
            createDocx(dataMap, new FileOutputStream(outFile));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

}
