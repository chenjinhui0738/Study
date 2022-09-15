package com.cjh.certificateGenerator.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.jodconverter.LocalConverter;
import org.jodconverter.office.LocalOfficeManager;
import org.jodconverter.office.OfficeManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * pdf工具类
 */
public class PDFUtil {
	private static final float DEFAULT_IMAGE_SCALE = 2F;
	/**
	 * 需要安装第三方工具 libreOffice安装路径
	 * @param docxPath  要转化的doc文件地址
	 * @param pdfPath  输出的pdf文件地址
	 * @param libreOfficePath  libreOffice安装路径
	 * @return
	 * @throws IOException
	 */
	public static File toPdf(String docxPath, String pdfPath, String libreOfficePath)
			throws IOException {
		File docx = new File(docxPath);
		File pdf = new File(pdfPath);
		try {
			LocalOfficeManager.Builder builder = LocalOfficeManager.builder();
			builder.officeHome(libreOfficePath);
			OfficeManager build = builder.build();
			build.start();
			LocalConverter make = LocalConverter.make(build);
			make.convert(docx).to(pdf).execute();
			build.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pdf;
	}

	/**
	 * 使用pdfbox工具，将pdf转成图片
	 * @param pdfPath pdf地址
	 * @param imagePath 图片输出地址
	 * @param imageExt 图片格式
	 * @param imageScale 图片规模，默认设置为2
	 */
	public static File toImage(String pdfPath, String imagePath, String imageExt, Float imageScale) {
		File out = null;

		try (PDDocument document = PDDocument.load(new File(pdfPath));) {
			File imagePathFile = new File(getPath(imagePath));
			if (!imagePathFile.exists()) {
				imagePathFile.mkdirs();
			}
			PDFRenderer pdfRender = new PDFRenderer(document);

			if (imageScale == null) {
				imageScale = DEFAULT_IMAGE_SCALE;
			}

			BufferedImage image = pdfRender.renderImage(0, imageScale);
			out = new File(imagePath);
			ImageIO.write(image, imageExt, out);
			document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return out;
	}

	public static String getPath(String pathName) {
		if (pathName.endsWith("\\") || pathName.endsWith("/")) {
			return pathName;
		}

		if (pathName.contains("\\")) {
			return pathName.substring(0, pathName.lastIndexOf('\\') + 1);
		}

		return pathName.substring(0, pathName.lastIndexOf('/') + 1);
	}
}
