package poi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.lsqt.components.db.procedure.DataSet;
 

public class Read {
	static DataFormatter formatter = new DataFormatter();
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		int headRowNum = 3 ; //列头所在的行
		int dataRowNumStart = 6; // 数据起始行
		
		InputStream is = new FileInputStream("d:/test.xlsx");
		// 得到Excel工作簿对象
		XSSFWorkbook wb = new XSSFWorkbook(is);
		
		DataFormatter formatter = new DataFormatter();
		
		DataSet.Table table= new DataSet.Table.TableModel();
		
		Map<String,Object> head = new LinkedHashMap<>();
	    Sheet sheet1 = wb.getSheetAt(0);
		for (Row row : sheet1) {
			if (row == null) {
				continue;
			}
			int rowNum = row.getRowNum() + 1;
			if (rowNum == headRowNum) {
				for (Cell cell : row) {
					if (headRowNum == rowNum) {
						CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
						// System.out.print("获取单元格定位(比如 A6):
						// "+cellRef.formatAsString());
						head.put(CellReference.convertNumToColString(cell.getColumnIndex()),getPrepareCellValue(cell));
						//getPrepareCellValue(cell);

					}

					/*
					 * String text = formatter.formatCellValue(cell);
					 * System.out.print("显示格式化后的文本字样值："+text+"（不是java数据类型值!!）");
					 */

				}
				break;
			}

			System.out.println();
		}
		
	    is.close();
	    System.out.println(head);
	}

	/**
	 * 获取数据块列头
	 * @param headRowNum 列头所在的行，（从1开始)
	 * @param fileInputStream excel流文件句柄
	 * @return 返回头的列字母 ，和单元格值
	 * @throws IOException
	 */
	public static Map<String,Object> getHeadLabels(int headRowNum ,InputStream fileInputStream) throws IOException{
		Map<String,Object> head = new LinkedHashMap<>();
		XSSFWorkbook wb = new XSSFWorkbook(fileInputStream);
		
		Sheet sheet1 = wb.getSheetAt(0);
		for (Row row : sheet1) {
			if (row == null) {
				continue;
			}
			int rowNum = row.getRowNum() + 1;
			if (rowNum == headRowNum) {
				for (Cell cell : row) {
					if (headRowNum == rowNum) {
						/*
						 CellReference cellRef = new CellReference(row.getRowNum(), cell.getColumnIndex());
						 System.out.print("获取单元格定位(比如 A6)=> cellRef.formatAsString());
						 */
						head.put(CellReference.convertNumToColString(cell.getColumnIndex()),getPrepareCellValue(cell));
						
					}
					/*
					 * String text = formatter.formatCellValue(cell);
					 * System.out.print("显示格式化后的文本字样值："+text+"（不是java数据类型值!!）");
					 */
				}
				break;
			}
		}
		return head;
	}
	
	/**
	 * 获取“假的”的单元格值(excel格式化的字样值)
	 * @param cell
	 * @return
	 */
	public static String getPrepareCellTextFormated(Cell cell) {

		return formatter.formatCellValue(cell);
	}
	
	/**
	 * 获取真实的单元格值(java数据类型值)
	 * @param cell
	 * @return
	 */
	public static Object getPrepareCellValue(Cell cell) {
		switch (cell.getCellType()) {
		    case Cell.CELL_TYPE_STRING:
		        return cell.getRichStringCellValue().getString();
		        
		        
		    case Cell.CELL_TYPE_NUMERIC:
		        if (DateUtil.isCellDateFormatted(cell)) {
		           return cell.getDateCellValue();
		        } else {
		            return cell.getNumericCellValue();
		        }
		        
		        
		    case Cell.CELL_TYPE_BOOLEAN:
		      	return cell.getBooleanCellValue();
		      	
		      	
		    case Cell.CELL_TYPE_FORMULA:
				try {
					return cell.getNumericCellValue();
				} catch (Exception ex) {
					return String.valueOf(cell.getRichStringCellValue().getString());
				}
		    	

		    case Cell.CELL_TYPE_BLANK:
		       	return null;
		       	
		       	
		    default:
		       return null;
		}
	}
}
