package fastexcel;

import java.io.File;

import edu.npu.fastexcel.ExcelException;
import edu.npu.fastexcel.FastExcel;
import edu.npu.fastexcel.Sheet;
import edu.npu.fastexcel.Workbook;

public class StreamWrite {
	public static void main(String[] args) throws ExcelException {
		File f = new File("write1.xls");
		Workbook wb = FastExcel.createWriteableWorkbook(f);
		wb.open();
		Sheet sheet = wb.addStreamSheet("SheetA");
		sheet.addRow(new String[] { "aaa", "bbb" });
		wb.close();
	}
}
