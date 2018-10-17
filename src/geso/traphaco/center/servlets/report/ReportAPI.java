package geso.traphaco.center.servlets.report;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class  ReportAPI {
	public static  void setHidden(Workbook workbook,int columCount){
		columCount += 26;
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);	   	   
	    Cells cells = worksheet.getCells();
	    for(int i=26;i<=columCount;++i){
	    	cells.hideColumn(i);
	    }
	}
	public static void setCellHeader(Cell cell){
		Style style = cell.getStyle();
		style.setColor(Color.GRAY);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		Font font = style.getFont();
		font.setBold(true);
		style.setFont(font);
		cell.setStyle(style);		
	}
	public static void getCellStyle_double(Cell cell, String fontName, Color color, Boolean bold, int size, double cellValue)
	 {     
	  Style style;
	  style = cell.getStyle();
	  //style.setTextWrapped(true);
	  
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
	  cell.setStyle(style);
	  cell.setValue(cellValue);
	 }
	
	public static void getCellStyle(Cell cell, String fontName, Color color, Boolean bold, int size, String cellValue)
	 {     
	  Style style;
	  style = cell.getStyle();
	  //style.setTextWrapped(true);
	  
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
	  cell.setStyle(style);
	  cell.setValue(cellValue);
	 }
	
	public static void setCellBackground(Cell cell,Color color,int borderLineType,boolean bold,int decimal){
		Style style = cell.getStyle();
		style.setColor(color);
		style.setBorderLine(BorderType.BOTTOM, borderLineType);
		style.setBorderLine(BorderType.LEFT, borderLineType);
		style.setBorderLine(BorderType.TOP, borderLineType);
		style.setBorderLine(BorderType.RIGHT, borderLineType);
		style.setNumber(decimal);
		
		
		
		Font font = new Font();
		font.setName("Times New Roman");
		font.setColor(Color.BLACK);
		font.setBold(bold);
		style.setFont(font);
		font.setSize(12);
		
		cell.setStyle(style);		
	}
	
	
	public static void setCellBackground_Font(Cell cell,Color color,int borderLineType,boolean bold,int decimal,Color font_Color){
		Style style = cell.getStyle();
		style.setColor(color);
		style.setBorderLine(BorderType.BOTTOM, borderLineType);
		style.setBorderLine(BorderType.LEFT, borderLineType);
		style.setBorderLine(BorderType.TOP, borderLineType);
		style.setBorderLine(BorderType.RIGHT, borderLineType);
		style.setNumber(decimal);
		
		style.setHAlignment(TextAlignmentType.CENTER);
		style.setVAlignment(TextAlignmentType.CENTER);
		
		
		
		Font font = new Font();
		font.setName("Times New Roman");
		font.setColor(font_Color);
		font.setBold(bold);
		style.setFont(font);
		font.setSize(9);
		
		cell.setStyle(style);		
	}
	
	
	
	public static void setCellBackground(Cell cell,Color color,int borderLineType,boolean bold,int decimal,Font font){
		Style style = cell.getStyle();
		style.setColor(color);
		style.setBorderLine(BorderType.BOTTOM, borderLineType);
		style.setBorderLine(BorderType.LEFT, borderLineType);
		style.setBorderLine(BorderType.TOP, borderLineType);
		style.setBorderLine(BorderType.RIGHT, borderLineType);
		style.setNumber(decimal);
		
		style.setFont(font);

		
		cell.setStyle(style);		
	}
	
	public static void mergeCells(Worksheet source,int beginRow, int endRow, int beginColumn, int endColumn) {
		Worksheet worksheet = source;		
		Cells cells = worksheet.getCells();
		cells.merge(beginRow, beginColumn, endRow,endColumn);	
	}
	
	
	public static void setCellHeader(Cell cell, Color bgColor, boolean bold) {
		Style style = cell.getStyle();
		style.setColor(bgColor);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		if(bold) {
			Font font = style.getFont();
			font.setBold(true);
			style.setFont(font);
		}
		cell.setStyle(style);		
	}
	public static void setCellHeader(Cell cell, Color bgColor, boolean bold, short horizontalAlignment) {
		Style style = cell.getStyle();
		style.setColor(bgColor);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setHAlignment(horizontalAlignment);//TextAlignmentType.CENTER
		if(bold) {
			Font font = style.getFont();
			font.setBold(true);
			style.setFont(font);
		}
		cell.setStyle(style);
	}
	public static void getCellStyle(Cell cell,Color color, Boolean bold, int size,String cellValue){		   
		Style style;
		style = cell.getStyle();		
		Font font = new Font();
		font.setColor(color);
		font.setBold(bold);
		font.setSize(size);
		font.setName("Times New Roman");
		style.setFont(font);
		
		cell.setStyle(style);
		cell.setValue(cellValue);
	}
	
	public static void setBorder_Style_MergerCell(Cells cells,int startRow, int endRow, int startColumn, int endColumn,int borderLineType,Color borderColor,Style style) 
	{
		cells.setRangeOutlineBorder(startRow,endRow, startColumn  , endColumn ,borderLineType,borderColor);
		cells.setRangeStyle(startRow,endRow, startColumn  , endColumn, style);
	}
	
	
	
	public static String NOW(String format){
		SimpleDateFormat df = new SimpleDateFormat(format);
		Date date = new Date();
		return df.format(date);
	}
	
	public static void setCellBackground(Cell cell,Color color,int borderLineType,boolean bold,int decimal,short align){
		Style style = cell.getStyle();
		style.setColor(color);
		style.setBorderLine(BorderType.BOTTOM, borderLineType);
		style.setBorderLine(BorderType.LEFT, borderLineType);
		style.setBorderLine(BorderType.TOP, borderLineType);
		style.setBorderLine(BorderType.RIGHT, borderLineType);
		style.setNumber(decimal);
		style.setVAlignment(align);
		style.setHAlignment(align);



		Font font = new Font();
		font.setName("Times New Roman");
		font.setColor(Color.BLACK);
		font.setBold(bold);
		style.setFont(font);
		font.setSize(9);

		cell.setStyle(style);		
	}
	public static String GetExcelColumnName(int columnNumber)
	{
		int dividend = columnNumber;
		String columnName = "";
		int modulo;

		while (dividend > 0)
		{
			modulo = (dividend - 1) % 26;
			columnName = (char)(65 + modulo) + columnName;
			dividend = (int)((dividend - modulo) / 26);
		} 

		return columnName;
	}
	public static void getCellStyle(Cell cell,Color color, Boolean bold, int size,String cellValue, int format){		   
		Style style;
		style = cell.getStyle();		
		Font font = new Font();
		font.setColor(color);
		font.setName("Times New Roman");
		if(format == 1) font.setBold(bold);
		font.setSize(size);
		style.setFont(font);
		cell.setStyle(style);
		cell.setValue(cellValue);
	}
	
	public static Cell CreateBorderSetting3(Cell cell) throws IOException
    {
	
        Style style;
        style = cell.getStyle();

        //Set border color
        style.setBorderColor(BorderType.TOP, Color.BLACK);
        style.setBorderColor(BorderType.BOTTOM, Color.BLACK);
        style.setBorderColor(BorderType.LEFT, Color.BLACK);
        style.setBorderColor(BorderType.RIGHT, Color.BLACK);

        //Set the cell border type
        style.setBorderLine(BorderType.TOP, BorderLineType.DOTTED);
        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);

        cell.setStyle(style);
        return cell;
    }
	
	public static Cell CreateBorderSetting2(Cell cell) throws IOException
    {
	
        Style style;
        style = cell.getStyle();
        Font font = style.getFont();
        style.setNumber(43);
        

        //Set border color
        style.setBorderColor(BorderType.TOP, Color.BLACK);
        style.setBorderColor(BorderType.BOTTOM, Color.BLACK);
        style.setBorderColor(BorderType.LEFT, Color.BLACK);
        style.setBorderColor(BorderType.RIGHT, Color.BLACK);

        //Set the cell border type
        style.setBorderLine(BorderType.TOP, BorderLineType.DOTTED);
        style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);

        cell.setStyle(style);
        return cell;
    }
	
	public static Cell CreateBorderSetting4(Cell cell) throws IOException
    {
	
        Style style;
        style = cell.getStyle();

        //Set border color
        style.setBorderColor(BorderType.TOP, Color.GRAY);
        style.setBorderColor(BorderType.BOTTOM, Color.GRAY);
        style.setBorderColor(BorderType.LEFT, Color.GRAY);
        style.setBorderColor(BorderType.RIGHT, Color.GRAY);

        //Set the cell border type
        style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);

        cell.setStyle(style);
        return cell;
    }
	public static Cell CreateBorderSetting2(Cell cell,int number) throws IOException
    {
	
        Style style;
        style = cell.getStyle();
        style.setNumber(number);
        //Set border color
        style.setBorderColor(BorderType.TOP, Color.BLACK);
        style.setBorderColor(BorderType.BOTTOM, Color.BLACK);
        style.setBorderColor(BorderType.LEFT, Color.BLACK);
        style.setBorderColor(BorderType.RIGHT, Color.BLACK);

        //Set the cell border type
        style.setBorderLine(BorderType.TOP, BorderLineType.DOTTED);
        style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);

        cell.setStyle(style);
        return cell;
    }
}
