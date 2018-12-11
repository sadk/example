package org.lsqt.report.controller;

import java.io.FileOutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.collection.MapUtil;

public class Util4ExcelCreate {
	
	/**
	 * 跟据数据创建文件
	 * @param data
	 * @param destFilePath
	 * @throws Exception
	 */
	public static void create(List<Map<String, Object>> data,String destFilePath) throws Exception{
		Workbook wb = new SXSSFWorkbook(100); // keep 100 rows in memory,  exceeding rows will be flushed to disk 
		Sheet sh = wb.createSheet();
		for (int rownum = 0; rownum < data.size(); rownum++) {
			Row row = sh.createRow(rownum);
			
			Map<String, Object> rowMap = data.get(rownum);
			List<String> keyList = MapUtil.toKeyList(rowMap);
			
			for(int i=0;i<keyList.size();i++) {
				Cell cell = row.createCell(i);
				Object value = rowMap.get(keyList.get(i));
				cell.setCellValue(value == null ? "":value.toString());
			}
		}
		
		FileOutputStream out = new FileOutputStream(destFilePath); 
		wb.write(out); 
		out.close();
	}
	

}
