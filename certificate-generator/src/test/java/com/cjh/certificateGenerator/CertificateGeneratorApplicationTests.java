package com.cjh.certificateGenerator;

import com.cjh.certificateGenerator.domain.CertificateData;
import com.cjh.certificateGenerator.domain.CertificateField;
import com.cjh.certificateGenerator.domain.CertificateTempPaths;
import com.cjh.certificateGenerator.utils.CertificateUtil;
import com.cjh.certificateGenerator.utils.DocUtil;
import com.cjh.certificateGenerator.utils.PDFUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CertificateGeneratorApplicationTests {
    public static final String LIBREOFFICE_PATH = "D:\\LibreOffice";
    public static final String IMAGE_SUFFIX = "jpg";
    public static final String IMAGE_SCALE = "2";
    @Test
    public void test() throws Exception {
        /*CertificateData data = new CertificateData();
        data.put(new CertificateField("持证人", "张 三"));
        data.put(new CertificateField("证书中文信息", "祝贺您完成\"直升机飞行员岗位资质\"培训课程。特发此证！"));
        data.put(new CertificateField("证书英文信息",
                "Congratulations on completion of training program of \"Helicopter Pilot Qualification\""));
        data.put(new CertificateField("证书编号", "100224512"));
        data.put(new CertificateField("证书签名", "李 四"));
        data.put(new CertificateField("证书日期", "2020年3月21日"));
        generate(data);*/
        Map data = new HashMap<String,String>();
        data.put("持证人", "张 三");
        data.put("证书中文信息", "祝贺您完成\"直升机飞行员岗位资质\"培训课程。特发此证！");
        data.put("证书英文信息",
                "Congratulations on completion of training program of \"Helicopter Pilot Qualification\"");
        data.put("证书编号", "100224512");
        data.put("证书签名", "李 四");
        data.put("证书日期", "2020年3月21日");
        CertificateUtil.createCertificateImage("C:\\Users\\CJH\\Desktop/image/tupian.jpg",data);
    }
}
