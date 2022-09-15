package com.cjh.certificateGenerator.utils;

import com.cjh.certificateGenerator.domain.CertificateTempPaths;
import org.apache.commons.io.FileUtils;
import org.apache.xmlbeans.XmlException;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

public class CertificateUtil {
    public static final String LIBREOFFICE_PATH = "D:\\LibreOffice";
    public static final String IMAGE_SUFFIX = "jpg";
    public static final String IMAGE_SCALE = "2";
    public static final String CERTIFICATE_TEMPLATE_PATH = "template\\template.docx";

    /**
     * 生成证书图片
     * @param imageOutPath 输出图片路径
     * @param data 需要填充的数据
     */
    public static File createCertificateImage(String imageOutPath,Map<String,String> data) throws IOException, XmlException {
        File file = new File(imageOutPath);
        FileUtils.writeByteArrayToFile(file,getCertificateImageBytes(data));
        return file;
    }
    /**
     * 获取生成的证书图片字节流
     * @param data 需要填充的数据
     */
    public static byte[] getCertificateImageBytes(Map<String,String> data) throws IOException, XmlException {
        // 获取生成证书预设参数
        Float imageScale = null;
        try {
            imageScale = Float.valueOf(IMAGE_SCALE);
        } catch (NumberFormatException e) {
            // image scale转换失败
        }

        // 获取证书模板
        File docxTemplate = new ClassPathResource(CERTIFICATE_TEMPLATE_PATH).getFile();

        // 生成临时文件路径
        CertificateTempPaths tempPaths = CertificateTempPaths.newInstance("docx", "pdf", IMAGE_SUFFIX);

        // 复制证书模板，将字段替换为自定义数据
        String tempDocxPath = tempPaths.getTempDocPathName();
        DocUtil.toCumstomDoc(docxTemplate, tempDocxPath, data);
        // 将doc模板转成pdf
        String tempPdfPath = tempPaths.getTempPdfPathName();
        PDFUtil.toPdf(tempDocxPath, tempPdfPath,LIBREOFFICE_PATH);
        // 将pdf转成证书图片
        File image = PDFUtil.toImage(tempPdfPath, tempPaths.getTempImagePathName(), IMAGE_SUFFIX, imageScale);
        byte[] bytes = FileUtils.readFileToByteArray(image);
        //tempPaths.deleteTempFiles();
        return bytes;
    }
}
