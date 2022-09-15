package com.cjh.certificateGenerator.domain;

import com.cjh.certificateGenerator.utils.PathUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

/** 
 * 证书临时文件路径
 * @author xuhaojin
 * @version [版本号, 2020年3月8日]
 */
public class CertificateTempPaths {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private TempFilePath tempDoc;

	private TempFilePath tempPdf;

	private TempFilePath tempImage;

	public static CertificateTempPaths newInstance(String docSuffix, String pdfSuffix, String imageSuffix) {
		List<TempFilePath> tempFilePaths = PathUtil.generateTempPaths(docSuffix, pdfSuffix, imageSuffix);
		createTempFiles(tempFilePaths);
		return new CertificateTempPaths(tempFilePaths);
	}

	public static void createTempFiles(List<TempFilePath> tempFilePaths) {
		for (TempFilePath tempFilePath : tempFilePaths) {
			createTempFile(tempFilePath);
		}
	}

	public static void createTempFile(TempFilePath tempFilePath) {
		try {
			File path = new File(tempFilePath.getPath());
			if (!path.exists()) {
				path.mkdirs();
			}

			File tempFile = new File(tempFilePath.getPathName());
			if (!tempFile.exists()) {
				tempFile.createNewFile();
			}
		} catch (IOException | SecurityException | NullPointerException e) {
			e.printStackTrace();
		}
	}

	public boolean deleteTempFiles() {
		boolean deleteStatus = FileUtils.deleteQuietly(new File(System.getProperty("user.dir") +PathUtil.tempFilePath));
		if(!deleteStatus){
			System.out.println("删除临时文件失败");
		}
		return deleteStatus;
	}

	public CertificateTempPaths(List<TempFilePath> tempFilePaths) {
		this(tempFilePaths.get(0), tempFilePaths.get(1), tempFilePaths.get(2));
	}

	public CertificateTempPaths(TempFilePath tempDoc, TempFilePath tempPdf, TempFilePath tempImage) {
		super();
		this.tempDoc = tempDoc;
		this.tempPdf = tempPdf;
		this.tempImage = tempImage;
	}

	public String getTempDocDirPath() {
		return tempDoc.getPath();
	}

	public String getTempDocPathName() {
		return tempDoc.getPathName();
	}

	public String getTempPdfDirPath() {
		return tempPdf.getPath();
	}

	public String getTempPdfPathName() {
		return tempPdf.getPathName();
	}

	public String getTempImagePath() {
		return tempImage.getPath();
	}

	public String getTempImagePathName() {
		return tempImage.getPathName();
	}

}
