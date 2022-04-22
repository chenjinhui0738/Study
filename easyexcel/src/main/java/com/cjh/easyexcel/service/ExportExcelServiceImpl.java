package com.cjh.easyexcel.service;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.cjh.easyexcel.ExcelConstant;
import com.cjh.easyexcel.dao.SysSystemMapper;
import com.cjh.easyexcel.vo.ResultVO;
import com.cjh.easyexcel.vo.SysSystemVO;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ExportExcelServiceImpl implements ExportExcelService {
    @Resource
    private SysSystemMapper sysSystemMapper;
    @Override
    public ResultVO<Void> exportLessThan20w(SysSystemVO sysSystemVO, HttpServletResponse response) throws Exception  {
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);

            // 设置EXCEL名称
            String fileName = new String(("SystemExcel").getBytes(), "UTF-8");

            // 设置SHEET名称
            Sheet sheet = new Sheet(1, 0);
            sheet.setSheetName("系统列表sheet1");

            // 设置标题
            Table table = new Table(1);
            List<List<String>> titles = new ArrayList<List<String>>();
            titles.add(Arrays.asList("系统名称"));
            titles.add(Arrays.asList("系统标识"));
            titles.add(Arrays.asList("描述"));
            titles.add(Arrays.asList("状态"));
            titles.add(Arrays.asList("创建人"));
            titles.add(Arrays.asList("创建时间"));
            table.setHead(titles);

            // 查数据写EXCEL
            List<List<String>> dataList = new ArrayList<>();
            List<SysSystemVO> sysSystemVOList = this.sysSystemMapper.selectSysSystemVOList(sysSystemVO);
            if (!CollectionUtils.isEmpty(sysSystemVOList)) {
                sysSystemVOList.forEach(eachSysSystemVO -> {
                    dataList.add(Arrays.asList(
                            eachSysSystemVO.getSystemName(),
                            eachSysSystemVO.getSystemKey(),
                            eachSysSystemVO.getDescription(),
                            eachSysSystemVO.getState().toString(),
                            eachSysSystemVO.getCreateUid(),
                            eachSysSystemVO.getCreateTime().toString()
                    ));
                });
            }
            writer.write0(dataList, sheet, table);

            // 下载EXCEL
            response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName).getBytes("gb2312"), "ISO-8859-1") + ".xls");
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding("utf-8");
            writer.finish();
            out.flush();

        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return ResultVO.getSuccess("导出系统列表EXCEL成功");
    }

    @Override
    public ResultVO<Void> exportLessThan100w(SysSystemVO sysSystemVO, HttpServletResponse response) throws Exception  {
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);

            // 设置EXCEL名称
            String fileName = new String(("SystemExcel").getBytes(), "UTF-8");

            // 设置SHEET名称
            Sheet sheet = new Sheet(1, 0);
            sheet.setSheetName("系统列表sheet1");

            // 设置标题
            Table table = new Table(1);
            List<List<String>> titles = new ArrayList<List<String>>();
            titles.add(Arrays.asList("系统名称"));
            titles.add(Arrays.asList("系统标识"));
            titles.add(Arrays.asList("描述"));
            titles.add(Arrays.asList("状态"));
            titles.add(Arrays.asList("创建人"));
            titles.add(Arrays.asList("创建时间"));
            table.setHead(titles);

            // 查询总数并 【封装相关变量 这块直接拷贝就行 不要改动】
            Integer totalRowCount = this.sysSystemMapper.selectCountSysSystemVOList(sysSystemVO);
            Integer pageSize = ExcelConstant.PER_WRITE_ROW_COUNT;
            Integer writeCount = totalRowCount % pageSize == 0 ? (totalRowCount / pageSize) : (totalRowCount / pageSize + 1);

            // 写数据 这个i的最大值直接拷贝就行了 不要改
            for (int i = 0; i < writeCount; i++) {
                List<List<String>> dataList = new ArrayList<>();

                // 此处查询并封装数据即可 currentPage, pageSize这个变量封装好的 不要改动
                PageHelper.startPage(i + 1, pageSize);
                List<SysSystemVO> sysSystemVOList = this.sysSystemMapper.selectSysSystemVOList(sysSystemVO);
                if (!CollectionUtils.isEmpty(sysSystemVOList)) {
                    sysSystemVOList.forEach(eachSysSystemVO -> {
                        dataList.add(Arrays.asList(
                                eachSysSystemVO.getSystemName(),
                                eachSysSystemVO.getSystemKey(),
                                eachSysSystemVO.getDescription(),
                                eachSysSystemVO.getState().toString(),
                                eachSysSystemVO.getCreateUid(),
                                eachSysSystemVO.getCreateTime().toString()
                        ));
                    });
                }
                writer.write0(dataList, sheet, table);
            }

            // 下载EXCEL
            response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName).getBytes("gb2312"), "ISO-8859-1") + ".xls");
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding("utf-8");
            writer.finish();
            out.flush();

        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return ResultVO.getSuccess("导出系统列表EXCEL成功");
    }

    @Override
    public ResultVO<Void> exportMoreThan100w(SysSystemVO sysSystemVO, HttpServletResponse response) throws Exception  {
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);

            // 设置EXCEL名称
            String fileName = new String(("SystemExcel").getBytes(), "UTF-8");

            // 设置SHEET名称
            String sheetName = "系统列表sheet";

            // 设置标题
            Table table = new Table(1);
            List<List<String>> titles = new ArrayList<List<String>>();
            titles.add(Arrays.asList("系统名称"));
            titles.add(Arrays.asList("系统标识"));
            titles.add(Arrays.asList("描述"));
            titles.add(Arrays.asList("状态"));
            titles.add(Arrays.asList("创建人"));
            titles.add(Arrays.asList("创建时间"));
            table.setHead(titles);

            // 查询总数并封装相关变量(这块直接拷贝就行了不要改)
            Integer totalRowCount = this.sysSystemMapper.selectCountSysSystemVOList(sysSystemVO);
            Integer perSheetRowCount = ExcelConstant.PER_SHEET_ROW_COUNT;
            Integer pageSize = ExcelConstant.PER_WRITE_ROW_COUNT;
            Integer sheetCount = totalRowCount % perSheetRowCount == 0 ? (totalRowCount / perSheetRowCount) : (totalRowCount / perSheetRowCount + 1);
            Integer previousSheetWriteCount = perSheetRowCount / pageSize;
            Integer lastSheetWriteCount = totalRowCount % perSheetRowCount == 0 ?
                    previousSheetWriteCount :
                    (totalRowCount % perSheetRowCount % pageSize == 0 ? totalRowCount % perSheetRowCount / pageSize : (totalRowCount % perSheetRowCount / pageSize + 1));


            for (int i = 0; i < sheetCount; i++) {

                // 创建SHEET
                Sheet sheet = new Sheet(i, 0);
                sheet.setSheetName(sheetName + i);

                // 写数据 这个j的最大值判断直接拷贝就行了，不要改动
                for (int j = 0; j < (i != sheetCount - 1 ? previousSheetWriteCount : lastSheetWriteCount); j++) {
                    List<List<String>> dataList = new ArrayList<>();

                    // 此处查询并封装数据即可 currentPage, pageSize这俩个变量封装好的 不要改动
                    PageHelper.startPage(j + 1 + previousSheetWriteCount * i, pageSize);
                    List<SysSystemVO> sysSystemVOList = this.sysSystemMapper.selectSysSystemVOList(sysSystemVO);
                    if (!CollectionUtils.isEmpty(sysSystemVOList)) {
                        sysSystemVOList.forEach(eachSysSystemVO -> {
                            dataList.add(Arrays.asList(
                                    eachSysSystemVO.getSystemName(),
                                    eachSysSystemVO.getSystemKey(),
                                    eachSysSystemVO.getDescription(),
                                    eachSysSystemVO.getState().toString(),
                                    eachSysSystemVO.getCreateUid(),
                                    eachSysSystemVO.getCreateTime().toString()
                            ));
                        });
                    }
                    writer.write0(dataList, sheet, table);
                }
            }

            // 下载EXCEL
            response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName).getBytes("gb2312"), "ISO-8859-1") + ".xls");
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding("utf-8");
            writer.finish();
            out.flush();

        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return ResultVO.getSuccess("导出系统列表EXCEL成功");
    }
}
