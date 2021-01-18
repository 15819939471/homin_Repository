package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.service.MemberService;
import com.itheima.health.service.ReportService;
import com.itheima.health.service.SetMealService;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author homin
 * 日期2021-01-15 16:28
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private MemberService memberService;

    @Reference
    private SetMealService setMealService;

    @Reference
    private ReportService reportService;

    /**
     * 根据月份集合查询每月会员数量
     *
     * @return
     */
    @GetMapping("/getMemberReport")
    public Result getMemberReport() {
        // 获取过去一年的月份
        List<String> months = new ArrayList<>();
        // 获取日历对象
        Calendar calender = Calendar.getInstance();
        // 获取去年的这个月份
        calender.add(Calendar.MONTH, -12);
        // 设置月份格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        // 循环获取过去12个月的月份
        for (int i = 0; i < 12; i++) {
            calender.add(Calendar.MONTH, 1);
            Date date = calender.getTime();
            months.add(sdf.format(date));
            i++;
        }
        // 通过月份集合查询对应的会员数量
        List<Integer> memberCount = memberService.getMemberCount(months);
        // 将结果封装到map集合返回给客户端
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("months", months);
        dataMap.put("memberCount", memberCount);
        return new Result(true, "查询会员数量成功", dataMap);
    }

    /**
     * 查询套餐预约人数占比
     * 返回数据setmealNames  setmealCount:value,name
     *
     * @return
     */
    @RequestMapping("/getSetmealReport")
    public Result getSetMealReport() {
        List<Map<String, String>> setMealCount = setMealService.getSetMealCount();
        Map<String, Object> dataMap = new HashMap<>();
        ArrayList<String> setmealNames = new ArrayList<String>();
        setMealCount.forEach(map -> {
            String name = map.get("name");
            setmealNames.add(name);
        });
        dataMap.put("setmealNames", setmealNames);
        dataMap.put("setmealCount", setMealCount);
        return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, dataMap);
    }

    /**
     * reportDate，日期
     * todayNewMember，本日新增会员数
     * totalMember，总会员数
     * thisWeekNewMember，本周新增会员数
     * thisMonthNewMember，本月新增会员数
     * todayOrderNumber，今日预约数
     * todayVisitsNumber，今日到诊数
     * thisWeekOrderNumber，本周预约数
     * thisWeekVisitsNumber，本周到诊数
     * thisMonthOrderNumber，本月预约数
     * thisMonthVisitsNumber，本月到诊数
     * hotSetmeal，热门套餐  name: "",setmeal_count: "",proportion: ""
     * 查询商业报告
     *
     * @return
     */
    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReport() {
        Map<String, Object> map = reportService.getBuinessReport();
        return new Result(true, "查询商业报表成功", map);
    }

    /**
     * 导出运营数据到excell表格
     * @param request
     * @param response
     */
    @RequestMapping("/exportBusinessReport")
    public void exportBusinessReport(HttpServletRequest request, HttpServletResponse response){
        // - 调用ReportService获取运营的数据
        Map<String, Object> buinessReport = reportService.getBuinessReport();
        //- 获取模板的存放路径 .xlsx
        String template = request.getSession().getServletContext().getRealPath("/report_template.xlsx");
        //- 创建XSSFWorkbook
        try (XSSFWorkbook wk = new XSSFWorkbook(template);){
            //- 获取工作表
            XSSFSheet sheet = wk.getSheetAt(0);
            //- 获取行对象
            //- 获取单格
            XSSFCell cell = sheet.getRow(2).getCell(5);
            //- 填值
            //  - 报表日期
            cell.setCellValue((String) buinessReport.get("reportDate"));
            //  - 会员数量
            sheet.getRow(4).getCell(5).setCellValue((Integer)buinessReport.get("todayNewMember"));
            sheet.getRow(4).getCell(7).setCellValue((Integer)buinessReport.get("totalMember"));
            sheet.getRow(5).getCell(5).setCellValue((Integer)buinessReport.get("thisWeekNewMember"));
            sheet.getRow(7).getCell(7).setCellValue((Integer)buinessReport.get("thisMonthNewMember"));
            //  - 预约到诊
            sheet.getRow(7).getCell(5).setCellValue((Integer)buinessReport.get("todayOrderNumber"));
            sheet.getRow(7).getCell(7).setCellValue((Integer)buinessReport.get("todayVisitsNumber"));
            sheet.getRow(8).getCell(5).setCellValue((Integer)buinessReport.get("thisWeekOrderNumber"));
            sheet.getRow(8).getCell(7).setCellValue((Integer)buinessReport.get("thisWeekVisitsNumber"));
            sheet.getRow(9).getCell(5).setCellValue((Integer)buinessReport.get("thisMonthOrderNumber"));
            sheet.getRow(9).getCell(7).setCellValue((Integer)buinessReport.get("thisMonthVisitsNumber"));
            //  - 热门套餐
            List<Map<String,Object>> hotSetmeal = (List<Map<String, Object>>) buinessReport.get("hotSetmeal");
            int workIndex = 12;
            for (Map<String, Object> map : hotSetmeal) {
                String name = (String) map.get("name");
                long setmeal_count = (long) map.get("setmeal_count");
                BigDecimal proportion = (BigDecimal) map.get("proportion");
                String remark = (String) buinessReport.get("remark");
                sheet.getRow(workIndex).getCell(4).setCellValue(name);
                sheet.getRow(workIndex).getCell(5).setCellValue(setmeal_count);
                sheet.getRow(workIndex).getCell(6).setCellValue(proportion.doubleValue());
                sheet.getRow(workIndex).getCell(7).setCellValue(remark);
            }
            //- 实现下载
            //  - 设置响应内容体格式, 告诉浏览器，文件的类型为excel
            response.setContentType("application/vnd.ms-excel");
            //  - 调协响应头信息 告诉浏览器，响应的内容体是一个文件，文件名叫business.xlsx
            String filename="运营数据统计.xlsx";
            filename = new String(filename.getBytes(),"ISO-8859-1");// 数据没有丢失
            response.setHeader("Content-Disposition","attachment;filename=" + filename);
            //- workbook.write(response.getOutputStream)
            wk.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出运营数据统计pdf
     * @param req
     * @param res
     * @return
     */
    @GetMapping("/exportBusinessReportPDF")
    public void exportBusinessReportPDF(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // 1. 查询运营数据统计
        Map<String, Object> reportData = reportService.getBuinessReport();
        // 2. 定义模板所在
        String path = req.getSession().getServletContext().getRealPath("/template");
        String jrxml = path+"/" + "business.jrxml";
        // 3. 定义编译后的jasper
        String jasper = path+"/" + "business.jasper";
        JasperCompileManager.compileReportToFile(jrxml, jasper);
        // 4. 填充数据
        List<Map<String,Object>> hotSetmeal = (List<Map<String,Object>>)reportData.get("hotSetmeal");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, reportData, new JRBeanCollectionDataSource(hotSetmeal));
        // 5. 设置响应的内容体格式，设置响应头信息
        res.setContentType("application/pdf");
        res.setHeader("Content-Disposition","attachment;filename=business.pdf");
        // 6. 导出到输出流
        JasperExportManager.exportReportToPdfStream(jasperPrint, res.getOutputStream());
    }
}
