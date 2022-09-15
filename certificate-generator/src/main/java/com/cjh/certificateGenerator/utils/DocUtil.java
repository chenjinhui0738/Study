package com.cjh.certificateGenerator.utils;

import com.cjh.certificateGenerator.domain.CertificateData;
import com.cjh.certificateGenerator.domain.CertificateField;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.IRunBody;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** 
 * 复制证书doc模板文件，并将内容修改为自定义数据
 */
public class DocUtil {
	/**
	 * 填充自定义模板数据
	 * @param templatePath  模板路径
	 * @param outputPath 输出路径
	 * @param certificateData 填充数据
	 */
	public static String toCumstomDoc(String templatePath, String outputPath, Map<String,String> certificateData)
			throws IOException, XmlException {
		return toCumstomDoc(new File(templatePath), outputPath, certificateData);
	}

	/**
	 * 填充自定义模板数据
	 * @param template 模板文件
	 * @param outputPath 输出路径
	 * @param certificateData 填充数据
	 */
	public static String toCumstomDoc(File template, String outputPath, Map<String,String> certificateData)
			throws IOException, XmlException {
		XWPFDocument document = new XWPFDocument(new FileInputStream(template));

		Set<String> keys = certificateData.keySet();

		for (XWPFParagraph paragraph : document.getParagraphs()) {
			XmlCursor cursor = paragraph.getCTP().newCursor();
			cursor.selectPath(
					"declare namespace w='http://schemas.openxmlformats.org/wordprocessingml/2006/main' .//*/w:txbxContent/w:p/w:r");

			List<XmlObject> xmlObjects = toXmlObjects(cursor);

			for (XmlObject xmlObject : xmlObjects) {
				CTR ctr = CTR.Factory.parse(xmlObject.xmlText());
				XWPFRun bufferrun = new XWPFRun(ctr, (IRunBody) paragraph);
				String text = bufferrun.getText(0);
				String conformingKey = containsKey(text, keys);
				if (conformingKey != null) {
					text = text.replace(toTemplateKey(conformingKey), certificateData.get(conformingKey));
					bufferrun.setText(text, 0);
				}

				xmlObject.set(bufferrun.getCTR());
			}
		}

		FileOutputStream out = new FileOutputStream(new File(outputPath));
		document.write(out);

		out.close();
		document.close();

		return outputPath;
	}

	public static List<XmlObject> toXmlObjects(XmlCursor docXmlCursor) {
		List<XmlObject> xmlObjects = Lists.newArrayList();

		while (docXmlCursor.hasNextSelection()) {
			docXmlCursor.toNextSelection();
			xmlObjects.add(docXmlCursor.getObject());
		}

		return xmlObjects;
	}

	public static String containsKey(String text, Set<String> keys) {
		String conforming = null;

		if (StringUtils.isEmpty(text)) {
			return conforming;
		}

		for (String key : keys) {
			if (text.contains(key)) {
				conforming = key;
				break;
			}
		}

		return conforming;
	}

	public static String toTemplateKey(String key) {
		if (StringUtils.isEmpty(key)) {
			return null;
		}

		return "${" + key + "}";
	}
}
