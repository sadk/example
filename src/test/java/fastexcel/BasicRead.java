package fastexcel;

import java.io.File;

import edu.npu.fastexcel.BIFFSetting;
import edu.npu.fastexcel.ExcelException;
import edu.npu.fastexcel.FastExcel;
import edu.npu.fastexcel.Sheet;
import edu.npu.fastexcel.Workbook;

/**
 * FastExcel是一个采用纯java开发的excel文件读写组件。支持Excel'97（-2003）（BIFF8）文件格式。FastExcel主要关注excel内容的处理，所以FastExcel只能读取单元格的字符 信息，而其它属性如颜色，字体等就不支持了。由于不读取，解析和存储这些额外信息，因此FastExcel只需很小的内存。
 * @author Administrator
 *
 */
public class BasicRead {
	public static void main(String[] args) throws ExcelException {
			Workbook workBook;
			workBook = FastExcel.createReadableWorkbook(new File("D:\\test.xls"));
			workBook.setSSTType(BIFFSetting.SST_TYPE_DEFAULT);//memory storage
			workBook.open();
			Sheet s;
			s = workBook.getSheet(0);
			System.out.println("SHEET:"+s);
			for (int i = s.getFirstRow(); i < s.getLastRow(); i++) {
			System.out.print(i+"#");
			for (int j = s.getFirstColumn(); j < s.getLastColumn(); j++) {
				System.out.print(","+s.getCell(i, j));
			}
			System.out.println();
			}
			workBook.close();
		}
}
