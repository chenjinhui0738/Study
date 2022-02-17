package FreeMark;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public class FreeMarkUtils {
    private static Logger logger = LoggerFactory.getLogger(FreeMarkUtils.class);

    public static Configuration getConfiguration() throws IOException {
        //创建配置实例
        Configuration configuration = new Configuration(Configuration.getVersion());
        //设置编码
        configuration.setDefaultEncoding("utf-8");
        configuration.setDirectoryForTemplateLoading(new File("F:\\ruida_idea_workspace\\cqes\\IQWebService\\cqes_web_manager\\src\\main\\resources\\template\\"));
        return configuration;
    }

    /**
     * 获取模板字符串输入流
     * @param dataMap   参数
     * @param templateName  模板名称
     * @return
     */
    public static ByteArrayInputStream getFreemarkerContentInputStream(Map dataMap, String templateName) {
        ByteArrayInputStream in = null;
        try {
            //获取模板
            Template template = getConfiguration().getTemplate(templateName);
            StringWriter swriter = new StringWriter();
            template.process(dataMap, swriter);
            //template.setCustomAttribute("fileMark","123456");
            in = new ByteArrayInputStream(swriter.toString().getBytes("utf-8"));//这里一定要设置utf-8编码 否则导出的word中中文会是乱码
        } catch (Exception e) {
            logger.error("模板生成错误！");
        }
        return in;
    }
}
