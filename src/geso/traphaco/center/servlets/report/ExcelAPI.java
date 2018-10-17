package geso.traphaco.center.servlets.report;

import jxl.Cell;
import jxl.CellView;
import jxl.Sheet;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WriteException;

public class ExcelAPI {

	public static WritableCellFormat format(WritableFont font, Colour color,
			Colour background, Border boder, BorderLineStyle lineStyle) {
		WritableFont cellFont = new WritableFont(font);
		WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
		try {
			cellFont.setColour(color);
			cellFormat = new WritableCellFormat(cellFont);
			cellFormat.setBackground(background);
			cellFormat.setBorder(boder, lineStyle);
		} catch (WriteException e) {
			e.printStackTrace();
		}
		return cellFormat;

	}

	public static void sheetAutoFitColumns(WritableSheet sheet) {
		for (int i = 0; i < sheet.getColumns(); i++) {
			Cell[] cells = sheet.getColumn(i);
			int longestStrLen = -1;

			if (cells.length == 0)
				continue;

			/* Find the widest cell in the column. */
			for (int j = 0; j < cells.length; j++) {
				if (cells[j].getContents().length() > longestStrLen) {
					String str = cells[j].getContents();
					if (str == null || str.isEmpty())
						continue;
					longestStrLen = str.trim().length();
				}
			}

			/* If not found, skip the column. */
			if (longestStrLen == -1)
				continue;

			/* If wider than the max width, crop width */
			if (longestStrLen > 255)
				longestStrLen = 255;

			CellView cv = sheet.getColumnView(i);
			cv.setSize(longestStrLen * 256 + 100); /*
													 * Every character is 256
													 * units wide, so scale it.
													 */
			sheet.setColumnView(i, cv);
		}
	}

	public static String getValue(Sheet sheet, int column, int row) {
		Cell cell;
		cell = sheet.getCell(column, row);
		return cell.getContents();
	}

}
