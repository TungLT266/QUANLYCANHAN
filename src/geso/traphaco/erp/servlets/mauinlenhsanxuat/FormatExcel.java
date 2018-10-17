package geso.traphaco.erp.servlets.mauinlenhsanxuat;
import geso.traphaco.center.servlets.report.ReportAPI;

import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Font;
import com.aspose.cells.Shape;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Worksheet;

public class FormatExcel {

	public void Merge(Cells cells,Cell cell,int startrow,int startcolumn,int endrow, int endcolumn,Color color_,Double Value ){
		for (int j =startcolumn ; j <=endcolumn ; j++) {
			for(int k=startrow; k<=endrow;k++){
				cell = cells.getCell(k,j);
				this.getCellStyle(cell, "Times New Roman", color_, true, 12,TextAlignmentType.CENTER,Value);
				this.setCellBorderStyle(cell);	
			}
		}
	}
	
	public void Merge(Cells cells,Cell cell,int startrow,int startcolumn,int endrow, int endcolumn,Color color_,Boolean bold, short align, Double Value ){
		for (int j =startcolumn ; j <=endcolumn ; j++) {
			for(int k=startrow; k<=endrow;k++){
				cell = cells.getCell(k,j);
				this.getCellStyle(cell, "Times New Roman", color_, bold, 12,align,Value);
				this.setCellBorderStyle(cell);	
			}
		}
	}
	public static void getCellStyle(Cell cell, String fontName, Color color, Boolean bold, int size,short setHAlignment, Double cellValue)
	{     
		Style style;
		style = cell.getStyle();
		style.setTextWrapped(true);

		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		//style.setNumber(40);
		style.setCustom("#,##0.000");
		Font font = new Font();
		font.setName(fontName);
		font.setColor(color);
		font.setBold(bold);
		font.setSize(size);
		style.setFont(font);
		style.setHAlignment(setHAlignment);
		style.setVAlignment(TextAlignmentType.TOP);

		cell.setStyle(style);
		cell.setValue(cellValue);
	}

	private void setCellBorderStyle(Cell cell){
		Style style;
		style = cell.getStyle();


		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);


		cell.setStyle(style);
	}

	public static void getCellStyle_ha(Cell cell,Color color, Boolean bold, int size,short setHAlignment,String cellValue){		   
		Style style;
		style = cell.getStyle();		
		Font font = new Font();
		font.setColor(color);
		font.setBold(bold);
		font.setSize(size);
		font.setName("Times New Roman");
		style.setFont(font);
		style.setHAlignment(setHAlignment);
		style.setVAlignment(TextAlignmentType.TOP);

		cell.setStyle(style);
		cell.setValue(cellValue);
	}

	//8 9 0 0 // 8 8 4 6
	public void Merge(Cells cells,Cell cell,int startrow,int startcolumn,int endrow, int endcolumn,Color color_,String Value ){
		for (int j =startcolumn ; j <=endcolumn ; j++) {
			for(int k=startrow; k<=endrow;k++){
				cell = cells.getCell(k,j);
				this.getCellStyle(cell, "Times New Roman", color_, true, 12,TextAlignmentType.CENTER,Value);
				this.setCellBorderStyle(cell);	
			}
		}
	}
	
	//8 9 0 0 // 8 8 4 6
		public void Merge_noborder(Cells cells,Cell cell,int startrow,int startcolumn,int endrow, int endcolumn,Color color_,String Value ){
			for (int j =startcolumn ; j <=endcolumn ; j++) {
				for(int k=startrow; k<=endrow;k++){
					cell = cells.getCell(k,j);
					this.getCellStyle(cell, "Times New Roman", color_, true, 12,TextAlignmentType.CENTER,Value);
					Style style = cell.getStyle();
					style.setBorderLine(BorderType.BOTTOM, 0);
					style.setBorderLine(BorderType.LEFT, 0);
					style.setBorderLine(BorderType.TOP, 0);
					style.setBorderLine(BorderType.RIGHT, 0);
					cell.setStyle(style);
				}
			}
		}

	public void Merge(Cells cells,Cell cell,int startrow,int startcolumn,int endrow, int endcolumn,Color color_,Boolean bold, short align, String Value ){
		for (int j =startcolumn ; j <=endcolumn ; j++) {
			for(int k=startrow; k<=endrow;k++){
				cell = cells.getCell(k,j);
				this.getCellStyle(cell, "Times New Roman", color_, bold, 12,align,Value);
				this.setCellBorderStyle(cell);	
			}
		}
	}
	
	public static void getCellStyle(Cell cell, String fontName, Color color, Boolean bold, int size,short setHAlignment, String cellValue)
	{     
		Style style;
		style = cell.getStyle();
		style.setTextWrapped(true);

		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);

		Font font = new Font();
		font.setName(fontName);
		font.setColor(color);
		font.setBold(bold);
		font.setSize(size);
		style.setFont(font);
		style.setHAlignment(setHAlignment);
		style.setVAlignment(TextAlignmentType.TOP);

		cell.setStyle(style);
		cell.setValue(cellValue);
	}
	
	
	public static void setborder_shadow(Cells cells, int startrow, int startcolumn, int endrow, int endcolumn)
	{   
		for (int j =startcolumn ; j <=endcolumn ; j++) {
			for(int k=startrow; k<=endrow;k++){
				
				Cell cell = cells.getCell(k,j);
				
				Style style;
				style = cell.getStyle();
				style.setBorderLine(BorderType.BOTTOM, 1);
				style.setBorderLine(BorderType.LEFT, 1);
				style.setBorderLine(BorderType.TOP, 1);
				style.setBorderLine(BorderType.RIGHT, 1);
				cell.setStyle(style);
			}
		}
		

	}
	
	public static void getStyle(Cell cell, String fontName, Color color, Boolean bold, Boolean Wrapped,  int size, String cellValue){ 
		Style style;
		style = cell.getStyle();
		style.setTextWrapped(Wrapped);
		Font font = new Font();
		font.setColor(color);
		font.setBold(bold);
		font.setSize(size);
		font.setName(fontName);
		style.setFont(font);

		cell.setStyle(style);
		cell.setValue(cellValue);
	}
	
	public static void getCellStyle_war(Cell cell, String fontName, Color color, Boolean bold, int size,short setHAlignment, String cellValue)
	{     
		Style style;
		style = cell.getStyle();
		style.setTextWrapped(true);

		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);

		Font font = new Font();
		font.setName(fontName);
		font.setColor(color);
		font.setBold(bold);
		font.setSize(size);
		style.setFont(font);
		style.setHAlignment(setHAlignment);
		style.setVAlignment(TextAlignmentType.TOP);

		cell.setStyle(style);
		cell.setValue(cellValue);
	}

	public static void mergeCells_Style12(Worksheet source,int beginRow, int endRow, int beginColumn, int endColumn,String nameRow,
			String value,Boolean setTextWrapped,Boolean setBold,int RowHeightPixel,short setHAlignment) {
		Worksheet worksheet = source;		
		Cells cells = worksheet.getCells();
		cells.merge(beginRow, beginColumn, endRow,endColumn);
		
		Cell cell = cells.getCell(nameRow + Integer.toString(beginRow + 1));
		
		Style style;
		style = cell.getStyle();
		style.setTextWrapped(setTextWrapped);

		Font font = new Font();
		font.setName("Times New Roman");
		font.setSize(12);
		font.setBold(setBold);
		
		style.setFont(font);
		style.setHAlignment(setHAlignment);
		style.setVAlignment(TextAlignmentType.JUSTIFY);
		
		cell.setStyle(style);
		cell.setValue(value);
		
		cell.setStyle(style);
		cells.setRowHeightPixel(beginRow, RowHeightPixel);
	}
	public static void mergeCells_Style12(Worksheet source,int beginRow, int endRow, int beginColumn, int endColumn,String nameRow,
			String value,Boolean setTextWrapped,Boolean setBold,int RowHeightPixel,short setHAlignment,int size) {
		Worksheet worksheet = source;		
		Cells cells = worksheet.getCells();
		cells.merge(beginRow, beginColumn, endRow,endColumn);
		
		Cell cell = cells.getCell(nameRow + Integer.toString(beginRow + 1));
		
		Style style;
		style = cell.getStyle();
		style.setTextWrapped(setTextWrapped);

		Font font = new Font();
		font.setName("Times New Roman");
		font.setSize(size);
		font.setBold(setBold);
		
		style.setFont(font);
		style.setHAlignment(setHAlignment);
		style.setVAlignment(TextAlignmentType.JUSTIFY);
		
		cell.setStyle(style);
		cell.setValue(value);
		
		cell.setStyle(style);
		cells.setRowHeightPixel(beginRow, RowHeightPixel);
	}
	
	public static void mergeCells_Style12(Worksheet source,int beginRow, int endRow, int beginColumn, int endColumn,String nameRow,
			String value,Boolean setTextWrapped,Boolean setBold,Boolean setItalic,int RowHeightPixel,short setHAlignment) {
		Worksheet worksheet = source;		
		Cells cells = worksheet.getCells();
		cells.merge(beginRow, beginColumn, endRow,endColumn);
		
		Cell cell = cells.getCell(nameRow + Integer.toString(beginRow + 1));
		
		Style style;
		style = cell.getStyle();
		style.setTextWrapped(setTextWrapped);

		Font font = new Font();
		font.setName("Times New Roman");
		font.setSize(12);
		font.setBold(setBold);
		font.setItalic(setItalic);
		
		style.setFont(font);
		style.setHAlignment(setHAlignment);
		style.setVAlignment(TextAlignmentType.JUSTIFY);
		
		cell.setStyle(style);
		cell.setValue(value);
		
		cell.setStyle(style);
		cells.setRowHeightPixel(beginRow, RowHeightPixel);
	}
	
	public static void mergeCells_Style12(Worksheet source,int beginRow, int endRow, int beginColumn, int endColumn,String nameRow,
			Shape value,Boolean setTextWrapped,Boolean setBold,int RowHeightPixel,short setHAlignment) {
		Worksheet worksheet = source;		
		Cells cells = worksheet.getCells();
		cells.merge(beginRow, beginColumn, endRow,endColumn);
		
		Cell cell = cells.getCell(nameRow + Integer.toString(beginRow + 1));
		
		Style style;
		style = cell.getStyle();
		style.setTextWrapped(setTextWrapped);

		Font font = new Font();
		font.setName("Times New Roman");
		font.setSize(12);
		font.setBold(setBold);
		
		style.setFont(font);
		style.setHAlignment(setHAlignment);
		style.setVAlignment(TextAlignmentType.JUSTIFY);
		
		cell.setStyle(style);
		cell.setValue(value);
		
		cell.setStyle(style);
		cells.setRowHeightPixel(beginRow, RowHeightPixel);
	}
	
	///---- double
	public static void mergeCells_Style12(Worksheet source,int beginRow, int endRow, int beginColumn, int endColumn,String nameRow,
			Double value,Boolean setTextWrapped,Boolean setBold,int RowHeightPixel,short setHAlignment) {
		Worksheet worksheet = source;		
		Cells cells = worksheet.getCells();
		cells.merge(beginRow, beginColumn, endRow,endColumn);
		
		Cell cell = cells.getCell(nameRow + Integer.toString(beginRow + 1));
		
		Style style;
		style = cell.getStyle();
		style.setTextWrapped(setTextWrapped);

		Font font = new Font();
		font.setName("Times New Roman");
		font.setSize(12);
		font.setBold(setBold);
		
		style.setFont(font);
		style.setHAlignment(setHAlignment);
		style.setVAlignment(TextAlignmentType.JUSTIFY);
		
		cell.setStyle(style);
		cell.setValue(value);
		
		cell.setStyle(style);
		cells.setRowHeightPixel(beginRow, RowHeightPixel);
	}
	
	public static void mergeCells_Style12_new(Worksheet source,int beginRow, int endRow, int beginColumn, int endColumn,String nameRow,
			String value,Boolean setTextWrapped,Boolean setBold,int RowHeightPixel,short setHAlignment, short setVAlignment) {
		Worksheet worksheet = source;		
		Cells cells = worksheet.getCells();
		cells.merge(beginRow, beginColumn, endRow,endColumn);
		
		Cell cell = cells.getCell(nameRow + Integer.toString(beginRow + 1));
		
		Style style;
		style = cell.getStyle();
		style.setTextWrapped(setTextWrapped);

		Font font = new Font();
		font.setName("Times New Roman");
		font.setSize(12);
		font.setBold(setBold);
		
		style.setFont(font);
		style.setHAlignment(setHAlignment);
		style.setVAlignment(setVAlignment);
		
		cell.setStyle(style);
		cell.setValue(value);
		
		cell.setStyle(style);
		cells.setRowHeightPixel(beginRow, RowHeightPixel);
	}
	
	
	public static void mergeCells_Style1_laygetCell(Cell cell,Worksheet source,int beginRow, int endRow, int beginColumn, int endColumn,String nameRow,
			String value,Boolean setTextWrapped,Boolean setBold,int RowHeightPixel,short setHAlignment) {
		Worksheet worksheet = source;		
		Cells cells = worksheet.getCells();
		cells.merge(beginRow, beginColumn, endRow,endColumn);
		
		//Cell cell = cells.getCell(nameRow + Integer.toString(beginRow + 1));
		
		Style style;
		style = cell.getStyle();
		style.setTextWrapped(setTextWrapped);

		Font font = new Font();
		font.setName("Times New Roman");
		font.setSize(12);
		font.setBold(setBold);
		
		style.setFont(font);
		style.setHAlignment(setHAlignment);
		style.setVAlignment(TextAlignmentType.JUSTIFY);
		
		cell.setStyle(style);
		cell.setValue(value);
		
		cell.setStyle(style);
		cells.setRowHeightPixel(beginRow, RowHeightPixel);
	}
	
	public static void mergeCells_Style12(Worksheet source,int beginRow, int endRow, int beginColumn, int endColumn,int cotbdct,
			String value,Boolean setTextWrapped,Boolean setBold,int RowHeightPixel,short setHAlignment) {
		Worksheet worksheet = source;		
		Cells cells = worksheet.getCells();
		cells.merge(beginRow, beginColumn, endRow,endColumn);
		Cell cell = cells.getCell(beginRow,cotbdct);
		
		Style style;
		style = cell.getStyle();
		style.setTextWrapped(setTextWrapped);

		Font font = new Font();
		font.setName("Times New Roman");
		font.setSize(12);
		font.setBold(setBold);
		
		style.setFont(font);
		style.setHAlignment(setHAlignment);
		style.setVAlignment(TextAlignmentType.JUSTIFY);
		
		cell.setStyle(style);
		cell.setValue(value);
		
		cell.setStyle(style);
		cells.setRowHeightPixel(beginRow, RowHeightPixel);
	} 

	public static void mergeCells_Style12(Worksheet source,int beginRow, int endRow, int beginColumn, int endColumn,int cotbdct,
			String value,Boolean setTextWrapped,Boolean setBold,int RowHeightPixel,short setHAlignment,short setVAlignment) {
		Worksheet worksheet = source;		
		Cells cells = worksheet.getCells();
		cells.merge(beginRow, beginColumn, endRow,endColumn);
		Cell cell = cells.getCell(beginRow,cotbdct);
		
		Style style;
		style = cell.getStyle();
		style.setTextWrapped(setTextWrapped);

		Font font = new Font();
		font.setName("Times New Roman");
		font.setSize(12);
		font.setBold(setBold);
		
		style.setFont(font);
		style.setVAlignment(setVAlignment);
		style.setHAlignment(setHAlignment);
		
		
		cell.setStyle(style);
		cell.setValue(value);
		
		cell.setStyle(style);
		cells.setRowHeightPixel(beginRow, RowHeightPixel);
	} 
	public static void mergeCells_Style12(Worksheet source,int beginRow, int endRow, int beginColumn, int endColumn,String nameRow,
			String value,Boolean setTextWrapped,Boolean setBold,int RowHeightPixel,short setHAlignment,short setVAlignment) {
		Worksheet worksheet = source;		
		Cells cells = worksheet.getCells();
		cells.merge(beginRow, beginColumn, endRow,endColumn);
		Cell cell = cells.getCell(nameRow + Integer.toString(beginRow + 1));
		
		Style style;
		style = cell.getStyle();
		style.setTextWrapped(setTextWrapped);

		Font font = new Font();
		font.setName("Times New Roman");
		font.setSize(12);
		font.setBold(setBold);
		
		style.setFont(font);
		style.setVAlignment(setVAlignment);
		style.setHAlignment(setHAlignment);
		
		
		cell.setStyle(style);
		cell.setValue(value);
		
		cell.setStyle(style);
		cells.setRowHeightPixel(beginRow, RowHeightPixel);
	} 
}
