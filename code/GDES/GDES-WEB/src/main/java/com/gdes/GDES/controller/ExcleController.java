package com.gdes.GDES.controller;

import com.gdes.GDES.model.Latestabilityscore;
import com.gdes.GDES.service.LatestabilityscoreService;
import com.gdes.GDES.service.ScoredetailService;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * excle的下载
 */
@Controller
@RequestMapping("/excle/")
public class ExcleController {
    @Resource
    private LatestabilityscoreService latestabilityscoreService;
    @Resource
    private ScoredetailService scoredetailService;

    /**
     * 学生个人
     * @param id_s
     * @param response
     * @return
     */
    @RequestMapping("sdown")
    public String mydown(String id_s, HttpServletResponse response) {
        try {
            ServletOutputStream out = response.getOutputStream();
            try {
                //设置头文件，最后为设置下载文件名
                response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(id_s+".xls", "UTF-8"));
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }

            String[] titles = {"学号", "能力点编号", "能力点名称", "得分", "占比", "更新时间"};
            export(titles, out, id_s);
            return "success";

        } catch (Exception e) {
            e.printStackTrace();
            return "导出失败";
        }
    }

    public void export(String[] titles, ServletOutputStream out, String id_s) throws Exception {
        try {
            //第一步，创建一个workbook，对应一个excle文件
            HSSFWorkbook workbook = new HSSFWorkbook();
            //第二步，在workbook中添加一个sheet，对应excle文件中的sheet
            HSSFSheet hssfSheet = workbook.createSheet("sheet1");
            //第三步，在sheet中添加表头第0行（行数限制short）
            HSSFRow row = hssfSheet.createRow(0);
            //第四步，创建单元格，并设计表头 设置表居中
            HSSFCellStyle hssfCellStyle = workbook.createCellStyle();

            //居中样式
            //hssfCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

            hssfSheet.autoSizeColumn(1); //自适应

            HSSFCell hssfCell = null;
            for (int i = 0; i < titles.length; i++) {
                hssfCell = row.createCell(i); //列索引从0开始
                hssfCell.setCellValue(titles[i]); //列名1
                hssfCell.setCellStyle(hssfCellStyle);
            }

            //第五部找出数据
            List<Latestabilityscore> latestAbilityScores = latestabilityscoreService.queryByStudentId(id_s);
            int i = 1;
            for (Latestabilityscore las : latestAbilityScores) {
                row = hssfSheet.createRow(i);
                row.createCell(0).setCellValue(las.getIdS()); //学号
                row.createCell(1).setCellValue(las.getIdAp()); //能力点编号
                row.createCell(2).setCellValue(las.getAbilitypoint().getNameAp()); //能力点名称
                row.createCell(3).setCellValue(las.getScoreLas()); //得分
                row.createCell(4).setCellValue(las.getProportionLas()); //占比
                row.createCell(5).setCellValue(las.getTimeLas()); //更新时间
            }

            //第六步，将文件输出到客户端浏览器
            try {
                workbook.write(out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("导出失败！");
        }
    }

}
