package com.cjh.spring.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * 获取ip地址工具类
 */
public class IpUtils {
    private static Logger log = LoggerFactory.getLogger(IpUtils.class);

    /**
     * 根据ip获取城市信息
     * @param ip
     * @return
     */
    public static String getCityInfo(String ip) {
        String dbPath = createFtlFileByFtlArray() + "ip2region.db";
        File file = new File(dbPath);
        if (file.exists() == false) {
            System.out.println("Error: Invalid ip2region.db file");
        }

        //查询算法
        //B-tree, B树搜索（更快）
        int algorithm = DbSearcher.BTREE_ALGORITHM;

        //Binary,使用二分搜索
        //DbSearcher.BINARY_ALGORITHM

        //Memory,加载内存（最快）
        //DbSearcher.MEMORY_ALGORITYM
        try {
            DbConfig config = new DbConfig();
            DbSearcher searcher = new DbSearcher(config, dbPath);

            //define the method
            Method method = null;
            switch (algorithm) {
                case DbSearcher.BTREE_ALGORITHM:
                    method = searcher.getClass().getMethod("btreeSearch", String.class);
                    break;
                case DbSearcher.BINARY_ALGORITHM:
                    method = searcher.getClass().getMethod("binarySearch", String.class);
                    break;
                case DbSearcher.MEMORY_ALGORITYM:
                    method = searcher.getClass().getMethod("memorySearch", String.class);
                    break;
            }

            DataBlock dataBlock = null;
            if (Util.isIpAddress(ip) == false) {
                System.out.println("Error: Invalid ip address");
            }

            dataBlock = (DataBlock) method.invoke(searcher, ip);
            String ipInfo = dataBlock.getRegion();
            if (!StringUtils.isEmpty(ipInfo)) {
                ipInfo = ipInfo.replace("|0", "");
                ipInfo = ipInfo.replace("0|", "");
            }
            return ipInfo;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    /**
     * 创建ip2region文件
     * @return
     */
    public static String createFtlFileByFtlArray() {
        String ftlPath = "city/";
        return createFtlFile(ftlPath, "ip2region.db");
    }

    /**
     * 创建文件
     * @param ftlPath
     * @param ftlName
     * @return
     */
    private static String createFtlFile(String ftlPath, String ftlName) {
        InputStream certStream = null;
        try {
            //获取当前项目所在的绝对路径
            String proFilePath = System.getProperty("user.dir");

            //获取模板下的路径，然后存放在temp目录下　
            String newFilePath = proFilePath + File.separator + "temp" + File.separator + ftlPath;
            newFilePath = newFilePath.replace("/", File.separator);
            //检查项目运行时的src下的对应路径
            File newFile = new File(newFilePath + ftlName);
            if (newFile.isFile() && newFile.exists()) {
                return newFilePath;
            }
            //当项目打成jar包会运行下面的代码，并且复制一份到src路径下（具体结构看下面图片）
            certStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(ftlPath + ftlName);
            byte[] certData = IOUtils.toByteArray(certStream);
            FileUtils.writeByteArrayToFile(newFile, certData);
            return newFilePath;
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            try {
                certStream.close();
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return null;
    }
}
