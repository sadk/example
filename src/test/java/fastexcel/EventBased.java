package fastexcel;

import java.io.File;

import edu.npu.fastexcel.ExcelException;
import edu.npu.fastexcel.FastExcel;
import edu.npu.fastexcel.SheetReadAdapter;
import edu.npu.fastexcel.Workbook;

public class EventBased {
	public static void main(String[] args) throws ExcelException {
		Workbook workBook;
		workBook = FastExcel.createReadableWorkbook(new File("test.xls"));
		workBook.open();
		workBook.getSheet(0, new SheetReadAdapter() {
			public void onCell(int row, int col, String content) {
				System.out.println(row + "," + col + "," + content);
			}
		});
		workBook.close();
	}
}
