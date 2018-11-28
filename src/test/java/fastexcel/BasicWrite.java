package fastexcel;

import java.io.File;

import edu.npu.fastexcel.ExcelException;
import edu.npu.fastexcel.FastExcel;
import edu.npu.fastexcel.Sheet;
import edu.npu.fastexcel.Workbook;

public class BasicWrite {
	public static void main(String[] args) throws ExcelException {
		File f = new File("write.xls");
		Workbook wb = FastExcel.createWriteableWorkbook(f);
		wb.open();
		Sheet sheet = wb.addSheet("sheetA");
		sheet.setCell(1, 2, "some string");
		wb.close();
	}
}
