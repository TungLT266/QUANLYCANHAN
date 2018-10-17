package geso.traphaco.erp.servlets.baocao;

import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Font;
import com.aspose.cells.HorizontalAlignmentType;
import com.aspose.cells.Style;
import com.aspose.cells.VerticalAlignmentType;
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
	
	public static void getCellStyle_wraptext(Cell cell, String fontName, Color color, Boolean bold, int size, String cellValue)
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
	  cell.setStyle(style);
	  cell.setValue(cellValue);
	 }
	
	public static void getCellStyle_wraptext2(Cell cell, String fontName, Color color, Boolean bold, int size, String cellValue)
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
	  style.setHAlignment(HorizontalAlignmentType.CENTRED);
		style.setVAlignment(VerticalAlignmentType.CENTRED);
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
		cell.setStyle(style);		
	}
	
	public static void mergeCells(Worksheet source,int beginRow, int endRow, int beginColumn, int endColumn) {
		Worksheet worksheet = source;		
		Cells cells = worksheet.getCells();
		cells.merge(beginRow, beginColumn, endRow,endColumn);	
	}
	
	
	public static void setCellHeader(Cell cell){
		Style style = cell.getStyle();
//		style.setColor(Color.YELLOW);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		Font font = style.getFont();
		font.setBold(true);
		style.setFont(font);
		cell.setStyle(style);		
	}
	public static void setCellHasBorder(Cell cell){
		Style style = cell.getStyle();
		
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		Font font = style.getFont();
		
		style.setFont(font);
		cell.setStyle(style);		
	}
	public static void getCellStyle(Cell cell,Color color, Boolean bold, int size,String cellValue){		   
		Style style;
		style = cell.getStyle();		
		Font font = new Font();
		font.setColor(color);
		
		font.setSize(size);
		style.setFont(font);
		cell.setStyle(style);
		cell.setValue(cellValue);
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
	public static String NOW(String format){
		SimpleDateFormat df = new SimpleDateFormat(format);
		Date date = new Date();
		return df.format(date);
	}

	public static String getQuycachSp(dbutils db, String spma) {
		// TODO Auto-generated method stub
		String quycach="";
		try{
			String query=" SELECT CAST(QC.SOLUONG1 AS NVARCHAR(10))+' '+ " +
					" DVCHUAN.DONVI +'/' + CAST(QC.SOLUONG2 AS NVARCHAR(10))+ ' '+ DVQD.DONVI AS TEN FROM ERP_SANPHAM SP "+  
				" INNER JOIN QUYCACH QC ON QC.SANPHAM_FK= SP.PK_SEQ "+
				" INNER JOIN DONVIDOLUONG DVCHUAN ON DVCHUAN.PK_SEQ=SP.DVDL_FK "+
				" INNER JOIN DONVIDOLUONG DVQD ON DVQD.PK_SEQ=QC.DVDL2_FK "+
				" WHERE SP.ma='"+spma+"'";
			ResultSet rs=db.get(query);
			
			while(rs.next()){
				quycach+= (quycach.length()>0?" - ":"")+rs.getString("TEN");
			}
			rs.close();
		}catch(Exception err){
			
		}
		return quycach;
	}
}
