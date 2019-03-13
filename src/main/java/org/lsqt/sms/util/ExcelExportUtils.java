package org.lsqt.sms.util;

import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.*;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * excel导出工具类
 */
public class ExcelExportUtils<T> {


    /**
     * 百万级别数据excel导出（处理上万条数据就用这个方法）
     * @throws IOException
     * 
     * @date 2017年12月27日 下午2:06:02
     */
    @SuppressWarnings({ "unchecked", "rawtypes" }) //去掉感叹号
    public void ExcelLargeExprort(String title, String[] headers,Collection<T> dataset,OutputStream out) throws IOException {
        long beginTime = System.currentTimeMillis();
        XSSFWorkbook workbook1 = new XSSFWorkbook();
        List<T> list;
        if (dataset instanceof List){
            list = (List)dataset;
        }
        else{
            list = new ArrayList<T>(dataset);
        }
        generateExcel(workbook1,0,title,headers,list);
        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(workbook1, 500);
        Sheet first = sxssfWorkbook.getSheetAt(0);
        sxssfWorkbook.write(out);
        out.close();
        long endTime = System.currentTimeMillis();
        System.out.println("excel文件导出耗时 : " + (endTime - beginTime)/1000+" 秒");
    }

    /**
     *
     * @Description: 导出excel表的时候对数据分析，如果数据量太大就截取生成多sheet导出
     * @param  title excel文件名
     * @param  headers excel 列头名
     * @param  dataset 要生成的数据源
     * 
     * @date 2017年12月19日 下午6:11:24
     */
    public void excelDowmForData(String title, String[] headers,Collection<T> dataset,OutputStream out,int sheetNum) throws Exception{
        //new一个表单
        XSSFWorkbook wbook = new XSSFWorkbook();
        List<T> list;
        if (dataset instanceof List){
            list = (List)dataset;
        }
        else{
            list = new ArrayList<T>(dataset);
        }
        //定义为每个sheet中的数据为50000条
        if (list.size() > 50000) {
            int k = (dataset.size() + 50000) / 50000;
            for (int i = 1; i <= k; i++) {
                if (i < k) {
                    generateExcel(wbook,i+sheetNum,title,headers,list.subList((i - 1) * 50000, i * 50000));
                } else {
                    generateExcel(wbook,i+sheetNum,title,headers,list.subList((i - 1) * 50000, list.size()));
                }
            }
        } else {
            generateExcel(wbook,sheetNum,title,headers,list);
        }
        //写出excel
        wbook.write(out);
        out.close();
    }
    /**
     *
     * @param title
     * @param headers
     * @param dataset
     * @param out
     * @throws Exception
     */
    public void excelDowmForDataTo(String title, String[] headers,Collection<T> dataset,OutputStream out) throws Exception{
        //new一个表单
        XSSFWorkbook wbook = new XSSFWorkbook();
        List<T> list;
        if (dataset instanceof List){
            list = (List)dataset;
        }
        else{
            list = new ArrayList<T>(dataset);
        }
        //定义为每个sheet中的数据为50000条
        if (list.size() > 50000) {
            int k = (dataset.size() + 50000) / 50000;
            for (int i = 1; i <= k; i++) {
                if (i < k) {
                    generateExcel(wbook,i,title,headers,list.subList((i - 1) * 50000, i * 50000));
                } else {
                    generateExcel(wbook,i,title,headers,list.subList((i - 1) * 50000, list.size()));
                }
            }
        } else {
            generateExcel(wbook,0,title,headers,list);
        }
        //写出excel

        //wbook.write(out);
        //out.close();
    }
    /**
     * @Description: 生成excel报表
     * @param   num sheet 标号
     * @param  excelName excel文件名
     * @param  excelHeader excel 列头名
     * @param  list  要生成的数据源
     * 
     * @date 2017年12月19日 下午6:11:24
     */
    public void generateExcel(XSSFWorkbook wbook, int num, String excelName, String[] excelHeader, List<T> list){
        //if (list.size()!=0) {
        //定义一个excel表单
        XSSFSheet sheet = null;
        //设置sheet名
        if(num != 0){
            sheet  = wbook.createSheet(excelName+num);
        }else{
            sheet  = wbook.createSheet(excelName);
        }
        // 给工作表列定义列宽(实际应用自己更改列数)
        for (int i = 0; i < excelHeader.length; i++) {
            sheet.setColumnWidth(i, 6000);
        }
        //创建列头样式
        XSSFCellStyle headerStyle = wbook.createCellStyle();
        //指定单元格居中对齐
        headerStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        //指定单元格垂直居中对齐
        headerStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        //设置背景色
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
        headerStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        //设置边框
        headerStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        //设置列头字体
        XSSFFont headerfont = wbook.createFont();
        headerfont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);  //加粗
        headerfont.setFontName("宋体");  //字体
        headerfont.setFontHeight((short) 200); //字体高度
        headerStyle.setFont(headerfont);
        // 创建数据列样式  ----------------------------------------------------------------
        XSSFCellStyle bodyStyle = wbook.createCellStyle();
        // 指定单元格居中对齐
        bodyStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        // 指定单元格垂直居中对齐
        bodyStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        //设置边框
        bodyStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        bodyStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        bodyStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        bodyStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        // 设置列头字体
        XSSFFont bodyFont = wbook.createFont();
        //bodyFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        bodyFont.setFontName("宋体");
        bodyFont.setFontHeight((short) 200);
        bodyStyle.setFont(bodyFont);
        // 指定当单元格内容显示不下时自动换行
        //cellStyle.setWrapText(true);
        // 设置列头
        XSSFRow rowHeader = sheet.createRow(0);//table第一列
        for(int i = 0; i < excelHeader.length; i++){
            XSSFCell cell = rowHeader.createCell(i);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(excelHeader[i]);
        }
        // 声明一个画图的顶级管理器
        XSSFDrawing patriarch = sheet.createDrawingPatriarch();
        // 遍历集合数据，产生数据行
        Iterator<T> it = list.iterator();
        int index = 0;
        while (it.hasNext()){
            index++;
            //产生数据的行编号
            XSSFRow rowBody = sheet.createRow(index);//第一列是列头，加1从第二列开始打印数据
            T t = (T) it.next();
            //利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            Field[] fields = t.getClass().getDeclaredFields();
            for (short i = 0; i < fields.length; i++){
                XSSFCell cell = rowBody.createCell(i);
                cell.setCellStyle(bodyStyle);
                Field field = fields[i];
                String fieldName = field.getName();
                String getMethodName = "get"+ fieldName.substring(0, 1).toUpperCase()+ fieldName.substring(1);
                try {
                    Class tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName,new Class[]{});
                    Object value = getMethod.invoke(t,new Object[]{});
                    // 判断值的类型后进行强制类型转换
                    String textValue = null;
                    if(value==null||value.equals("")){
                        textValue = "";
                    }else{
                        //如果属性数据是布尔型
                        if(value instanceof Boolean) {
                            if((boolean) value){
                                textValue = "是";
                            }else{
                                textValue = "否";
                            }
                            //如果属性数据是时间格式
                        } else if (value instanceof Date) {
                            Date date = (Date) value;
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            textValue = sdf.format(date);
                            //如果属性数据是byte
                        } else if (value instanceof byte[]) {
                            // 有图片时，设置行高为60px;
                            rowBody.setHeightInPoints(60);
                            // 设置图片所在列宽度为80px,注意这里单位的一个换算
                            sheet.setColumnWidth(i, (short) (35.7 * 80));
                            // sheet.autoSizeColumn(i);
                            byte[] bsValue = (byte[]) value;
                            XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0,
                                    1023, 255, (short) 6, index, (short) 6, index);
                            //anchor.setAnchorType(2);
                            patriarch.createPicture(anchor, wbook.addPicture(bsValue, XSSFWorkbook.PICTURE_TYPE_JPEG));
                        } else {
                            // 其它数据类型都当作字符串简单处理
                            textValue = value.toString();
                        }
                    }
                    // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                    if (textValue != null) {
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            XSSFRichTextString richString = new XSSFRichTextString(textValue);
                            cell.setCellValue(richString);
                        }
                    }
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } finally {
                    // 清理资源
                }
            }
        }
    }
    // }
}
