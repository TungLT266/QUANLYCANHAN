package geso.traphaco.erp.servlets.lapngansach;

import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.lapngansach.ILapngansach;
import geso.traphaco.erp.beans.lapngansach.imp.Lapngansach;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.PatternType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.aspose.cells.Font;
import java.text.DecimalFormat; 
import java.text.NumberFormat;

@WebServlet("/Erp_PLExportSvl") 
public class Erp_PLExportSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	Utility util = new Utility();
	
	public Erp_PLExportSvl()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String ctyId = (String)session.getAttribute("congtyId");

		ILapngansach bean = (ILapngansach) new Lapngansach();
		bean.setCtyId(ctyId);
	    String Id = util.antiSQLInspection(request.getParameter("Id"));
		bean.setId(Id) ;
		
		String type = request.getParameter("type");
		if(type == null)
			type = "";
			
		try{
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename = PLExport.xlsm");

			FileInputStream fstream = null;
			Workbook workbook = new Workbook();		
			
			fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\PLNewToyo.xlsm");		
	
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

			OutputStream out = response.getOutputStream();		   
			
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet0 = worksheets.addSheet();
			worksheet0.setName("P&L Company");
			worksheet0.setZoom(70);
		   		
			if(!type.equals("theodoi"))
				this.XuatFileExcel_PLCompany(bean, worksheet0);
			else
				this.XuatFileExcel_PLCompany_2(bean, worksheet0);
			
			Worksheet worksheet1 = worksheets.addSheet();

			worksheet1.setName("P&L Lamination");
			worksheet1.setZoom(70);

			if(!type.equals("theodoi"))
				this.XuatFileExcel_PLLamination(bean, worksheet1);
			else
				this.XuatFileExcel_PLLamination2(bean, worksheet1);
					
			Worksheet worksheet2 = worksheets.addSheet();

			worksheet2.setName("P&L Paper Core");
			worksheet2.setZoom(70);

			if(!type.equals("theodoi"))
				this.XuatFileExcel_PLPaperCore(bean, worksheet2);
			else
				this.XuatFileExcel_PLPaperCore2(bean, worksheet2);

			workbook.save(out);			    
			fstream.close();
			
		} catch (Exception e) {System.out.println(e.toString());}
		
	}
	
	private void XuatFileExcel_PLCompany(ILapngansach bean, Worksheet worksheet) throws Exception{
		try {
			NumberFormat formatter = new DecimalFormat("#,###,###.##");
			String[] COGS = new String[13];
			String[] SALES = new String[13];
			String[] NETPROFITBTAX = new String[13];
			for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
				SALES[i] = "0";
				COGS[i]  = "0";
				NETPROFITBTAX[i] = "0";
			}			
			
			Cell cell;
			Style style;
			Font font;


			Cells cells = worksheet.getCells();	    
			cells.setRowHeight(0, 15.75f);
			cells.setColumnWidth(0, 51.63f);
			cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "NEW TOYO ALUMINIUM PAPER PACKAGING CO., LTD");
		   
			cell = cells.getCell("A2");
			ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, this.getDate().substring(0, 4) + " Profit and Loss Account" );
		   
			cell = cells.getCell("A4");		   
			ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PARTICULAR");		   		   
			cells.merge(3, 0 , 4, 0);
			
			style = cell.getStyle();
			style.setHAlignment(TextAlignmentType.CENTER);
			style.setPatternStyle(PatternType.SOLID);
			style.setBorderColor(1, Color.BLACK);
			style.setBorderLine(BorderType.TOP, BorderLineType.DOUBLE);
			cell.setStyle(style);
			
			cell = cells.getCell("A5");
			style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			cell.setStyle(style);
		   
			for (int i = 1; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++ ){
			   cells.setColumnWidth(i, 22.38f);
			   cell = cells.getCell(3, i);
			   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Actual" );
			   style = cell.getStyle();
			   style.setHAlignment(TextAlignmentType.CENTER);		   
			   style.setColor(new Color(204, 255, 204));
			   style.setBorderColor(1, Color.BLACK);
			   style.setPatternStyle(PatternType.SOLID);
			   style.setBorderLine(BorderType.TOP, BorderLineType.DOUBLE);
			   style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
			   style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
			   cell.setStyle(style);
			   
			   cell = cells.getCell(4, i);
			   if(i < Integer.parseInt(this.getDate().substring(5, 7)) + 1){
				   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, this.getDate(("" + i), ("" + 2014)) );
			   }else{
				   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Total" );
			   }
			   style = cell.getStyle();
			   style.setHAlignment(TextAlignmentType.CENTER);
			   style.setColor(new Color(204, 255, 204));
			   style.setPatternStyle(PatternType.SOLID);
			   style.setBorderLine(BorderType.TOP, BorderLineType.DOUBLE);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);			   
			   style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
			   cell.setStyle(style);
			   
		   }

		   String[] data = bean.PL_SalesVolumn();
		   double total = 0;

		   cell = cells.getCell("A8");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "SALES AMOUNT (NET OF TAX)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(3);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_SalesAmount();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(13, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
		   }

		   cell = cells.getCell("A9");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Lamination");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_SalesAmount_LA();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(14, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   SALES[i] = data[i];
			   }else{			   
				   cell.setValue(total);
				   SALES[i] = "" + total;
			   }
			   
		   }

		   cell = cells.getCell("A10");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Sales return/allowance");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
//		   data = bean.;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(15, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");
		   }

		   cell = cells.getCell("A11");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Paper core/cone");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_SalesAmount_PA();
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(16, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");
		   }

		   cell = cells.getCell("A12");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Sales return/allowance");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
//		   data = bean.;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(15, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");
		   }
		   
		   cell = cells.getCell("A13");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Others");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
//		   data = bean.;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(15, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");
		   }		   
		   
		   cell = cells.getCell("A16");		   
		   ReportAPI.getCellStyle(cell,Color.MAGENTA, true, 12, "MATERIALS CONSUMED  02 division ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(18, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   COGS[i] = data[i];
			   }else{			   
				   cell.setValue(total);
				   COGS[i] = "" + total;
			   }
			   
		   }

		   cell = cells.getCell("A17");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PAPER CONSUMED");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume_Paper();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7))); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(19, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);			   
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)))){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A18");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, "FOIL CONSUMED");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume_Foil();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(20, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A19");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, "GLUE CONSUMED");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume_Glue();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(21, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A20");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, "LACQUER CONSUMED");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume_Lacquer();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(22, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A21");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "SUB-MATERIAL CONSUMED");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume_Submaterial();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(23, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A23");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "DIRECT LABOUR(LUONG TRUC TIEP)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_DirectLabour();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(25, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   COGS[i] = "" + (Double.parseDouble(COGS[i]) + Double.parseDouble(data[i]));				   
			   }else{			   
				   cell.setValue(total);
				   COGS[i] = "" + (Double.parseDouble(COGS[i]) + total);
			   }
			   
		   }

		   cell = cells.getCell("A24");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "WORKERS' SALARY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_WorkerSalary();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(26, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }
		   
		   cell = cells.getCell("A25");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OVERTIME FOR WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_OvertimeWorkers();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(27, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)))){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A26");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "AMORTIZED 13 TH SALARY OF WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_AmortizedWorkers();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(28, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A27");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "SOCIAL INSURANCE (WORKERS)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_SocialInsurance();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(29, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A28");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "HEALTH INSURANCE (WORKERS)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_HealthInsurance();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(30, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A30");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "PRODUCTION OVERHEAD(CPHI SAN XUAT)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_ProductionOverhead();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(32, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   COGS[i] = "" + (Double.parseDouble(COGS[i]) + Double.parseDouble(data[i]));
			   }else{			   
				   cell.setValue(total);
				   COGS[i] = "" + (Double.parseDouble(COGS[i]) + total);
			   }
			   
		   }

		   cell = cells.getCell("A31");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "ALLOWANCES FOR WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_WorkerAllowances();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(33, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }
		   
		   cell = cells.getCell("A32");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "BONUS FOR WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_WorkerBonus();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(34, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A33");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION OF BUILDING");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_DepreciationBuilding_Production();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(35, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A34");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION MACHINE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_DepreciationMachine();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(36, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A35");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION MOTOR VEHICLE FOR FACTORY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_DepreciationMotorVehicle_Production();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(37, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A36");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "RENTAL CHARGE OF MACHINERY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_RentalCharge_Machinery();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(38, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A37");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "ELECTRICITY+WATER(FACT.)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_ElectricityWater();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(39, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A38");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "FACTORY EXPENSES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_FactoryExpenses();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(40, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A39");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PRINTING & STATIONERY (FACTORY)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Printing_Stationery();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(41, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A40");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "MEALS FOR WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Workers_Meals();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(42, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }


		   }

		   cell = cells.getCell("A41");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UPKEEP OF MOTORVEHICLE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Upkeep_Motorvehicle();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(43, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A42");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UPKEEP OF MACHINERY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Upkeep_Machinery();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(44, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A43");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TRAINING COST");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_TrainingCost();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(45, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A44");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "WELFARE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Welfare();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(46, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A45");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INSURANCE A");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_InsuranceA();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(47, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A46");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UPKEEP OF FACTORY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_UpkeepFactory();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(48, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A47");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UNIFORM FOR WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Workers_Uniform() ;
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(49, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A48");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "LAND RENTAL (DIVIDED BY 2)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_LandRental();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(50, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A49");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TOOL & ACCESSORIES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Tool_Accessories();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(51, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A50");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OIL FOR MACHINE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Oil_Machine();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(52, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A51");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INDIRECT LABOUR SALARY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_IndirectLabourSalary();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(53, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }


		   }

		   cell = cells.getCell("A52");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INDIRECT LABOUR 13TH SALARY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Indirect_13th_LabourSalary();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(54, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A53");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INDIRECT LABOUR BONUS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Indirect_LabourBonus();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(55, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }
		   
		   cell = cells.getCell("A56");		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "Finished Goods purchased/ (Transferred)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(58, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");

		   }

		   cell = cells.getCell("A57");		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "W.I.P Opening");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(59, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");

		   }

		   cell = cells.getCell("A58");		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "W.I.P Closing");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(60, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");

		   }

		   cell = cells.getCell("A59");		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "Finished goods Opening");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(61, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");

		   }

		   cell = cells.getCell("A60");		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "Finished goods Closing");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(62, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");

		   }

		   cell = cells.getCell("A61");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "% RM/Sales");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(64, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue(Double.parseDouble(COGS[i]));
		   }
		   
		   cell = cells.getCell("A62");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "Cost of Raw Materials");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(64, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue(Double.parseDouble(COGS[i]));
		   }

		   
		   cell = cells.getCell("A63");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "Manufacturing Cost");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(64, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue(Double.parseDouble(COGS[i]));
		   }

		   
		   cell = cells.getCell("A64");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "COST OF GOODS SOLD(02 Division)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(64, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue(Double.parseDouble(COGS[i]));
		   }

		   cell = cells.getCell("A65");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, "Gross profit");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(65, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   NETPROFITBTAX[i] = "" + (Double.parseDouble(SALES[i]) - Double.parseDouble(COGS[i]));
			   
			   if(Double.parseDouble(NETPROFITBTAX[i]) < 0){ 		
				   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(NETPROFITBTAX[i])*(-1))) + ")");
			   }else{ 							
				   cell.setValue(formatter.format(Math.round(Double.parseDouble(NETPROFITBTAX[i]))));
			   } 
			   
		   }

		   cell = cells.getCell("A66");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, false, 12, " ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
//		   data = bean.PL_MaterialConsume();
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(66, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   
			   cell.setStyle(style);			   			   
			   if( Double.parseDouble(SALES[i]) != 0)
				   cell.setValue(formatter.format(100*(Double.parseDouble(SALES[i]) - Double.parseDouble(COGS[i]))/ Double.parseDouble(SALES[i])) + "%");
			   else
				   cell.setValue("-");
		   }
		   
		   cell = cells.getCell("A68");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "SELLING EXPENSES(CHI PHI BAN HANG)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_SellingExpenses();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(68, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(data[i]));
			   }else{			   
				   cell.setValue(total);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - total);
			   }
			   			   
		   }

		   cell = cells.getCell("A69");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TRANSPORTATION");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Transportation();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(69, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A70");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "COMMISSION");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Commission();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(70, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A71");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PROMOTION EXPENSE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_PromotionExpenses();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(71, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A73");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "ADMINISTRATIVE OVERHEAD (CHI PHI QUAN LY)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_AdminOverhead();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(73, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(data[i]));
			   }else{			   
				   cell.setValue(total);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - total);
			   }
			   
		   }

		   cell = cells.getCell("A74");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "ALLOWANCES FOR STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Staff_Allowances();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(74, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A75");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "BANK CHARGES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_BankCharges();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(75, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A76");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "BONUS FOR STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_StaffsBonus();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(76, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A77");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION OF BUILDING");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Depreciation_Building_Office();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(77, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A78");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION OF OFFICE EQUIPMENT");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Depreciation_Equipment_Office();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(78, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A79");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION MOTOR VEHICLE FOR OFFICE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Depreciation_Motor_Office();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(79, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A80");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PHONE+FAX +ELECTRICITY CHARGE FOR OFFICE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Phone_Fax_Electricity_Office();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(80, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A81");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PUBLIC RELATION EXPENSES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_PublicRelations();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(81, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }
		   
		   cell = cells.getCell("A82");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "ENTERTAINMENT");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Entertainments();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(82, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A83");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OFFICE EXPENSES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_OfficeExpenses();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(83, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A84");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "HANDLING CHARGE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_HandlingCharges();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(84, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A85");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PRINTING & STATIONERY (OFFICE)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Printing_Stationery_Office();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(85, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A86");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TRAINING FEE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_TrainingFees();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(86, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A87");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TRAVELLING");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Trevelling();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(87, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A88");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UPKEEP OF MOTOR VEHICLE (OFFICE)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Upkeep_Motor_Vehicle_Office();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(88, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A89");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "AUDITING FEE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_AuditingFees();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(89, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A90");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INSURANCE (office)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Insurance_Office();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(90, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A91");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PROVISION EXPENSES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_StaffUniform();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(91, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A92");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "LAND RENTAL");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_LandRental();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(92, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }
		   
		   cell = cells.getCell("A93");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "R & D EXPENSES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_RD_Expenses();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(93, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A94");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "MANAGEMENT FEE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_ManagementFees();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(94, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A96");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "ADMIN. SALARY(LUONG QUAN LY)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_AdminSalary();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(96, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(data[i]));				   
			   }else{			   
				   cell.setValue(total);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - total);
			   }

		   }

		   cell = cells.getCell("A97");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "STAFFS SALARY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Staff_Allowances();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(74, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }


		   cell = cells.getCell("A98");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OVERTIME FOR STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_StaffOvertime();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(98, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A99");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "AMORTIZED 13th SALARY OF STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Staff_Amortized_13th_Salary();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(99, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }
		   
		   cell = cells.getCell("A100");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "SOCIAL INSURANCE OF STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Staff_SocialInsurance();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(100, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A101");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "HEALTH INSURANCE (STAFFS)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Staff_HealthInsurance();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(101, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A103");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "FINANCIAL ACTIVITY(HOAT DONG TAI CHINH)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Financial_Activity();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(103, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   if(Double.parseDouble(data[i]) < 0){ 		
					   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(data[i])*(-1))) + ")");
				   }else{ 							
					   cell.setValue(formatter.format(Math.round(Double.parseDouble(data[i]))));
				   } 
				   
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(data[i]));

				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   if(total < 0){ 
					   cell.setValue("(" + formatter.format(Math.round(total)*(-1)) + ")");
				   }else{ 							
					   cell.setValue(formatter.format(Math.round(total)));
				   } 
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - total);
			   }
			   
			   
		   }

		   cell = cells.getCell("A104");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INTEREST INCOME (51501)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Interest_Income();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(104, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(data[i]))) + ")");

				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue("(" + formatter.format(Math.round(total)) + ")");
			   }

		   }

		   cell = cells.getCell("A105");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "GAIN ON EXCHANGE RESERVE (51502)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Gain_Exchange_Reserve();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(105, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(data[i])*(-1))) + ")");

				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue("(" + formatter.format(Math.round(total)) + ")");
			   }

		   }

		   cell = cells.getCell("A106");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "LOAN INTEREST (63501)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_LoanInterest();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(106, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A107");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "LOSS ON EXCHANGE RESERVE (63502)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Loss_Exchange_Reserve();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(107, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A109");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "OTHER ACTIVITY (HOAT DONG KHAC)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_OtherActivity();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(109, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   if(Double.parseDouble(data[i]) < 0){ 		
					   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(data[i])*(-1))) + ")");
				   }else{ 							
					   cell.setValue(formatter.format(Math.round(Double.parseDouble(data[i]))));
				   } 
				   
				   total = total + Double.parseDouble(data[i]);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(data[i]));
			   }else{			   
				   if(total < 0){ 		
					   cell.setValue("(" + formatter.format(Math.round(total)*(-1)) + ")");
				   }else{ 							
					   cell.setValue(formatter.format(Math.round(total)));
				   } 
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - total);
			   }
		   }

		   cell = cells.getCell("A110");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OTHER INCOME(711)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Other_Income();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(110, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(data[i])*(-1))) + ")");

				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue("(" + formatter.format(Math.round(total)*(-1)) + ")");
			   }

		   }
		   
		   cell = cells.getCell("A111");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OTHER EXPENSE (811)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_OtherExpenses();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(111, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A115");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, " Net Profit before tax");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(65, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(Double.parseDouble(NETPROFITBTAX[i]) < 0){ 		
				   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(NETPROFITBTAX[i])*(-1))) + ")");
			   }else{ 							
				   cell.setValue(formatter.format(Math.round(Double.parseDouble(NETPROFITBTAX[i]))));
			   } 
			   
		   }
		   
		   cell = cells.getCell("A117");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, "Additional CIT (Adjust)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(116, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   
			   if(Double.parseDouble(SALES[i]) > 0){
				   cell.setValue(Math.round(100*Double.parseDouble(NETPROFITBTAX[i])/Double.parseDouble(SALES[i])) + "%");
			   }else{
				   cell.setValue("-");
			   }
		   }

		   cell = cells.getCell("A119");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, "CURRENT INCOME TAX EXPENSE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(119, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue(7.5*Double.parseDouble(NETPROFITBTAX[i])/100);
		   }

		   cell = cells.getCell("A121");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, "Net profit after tax");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(121, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue(Math.round((100 - 7.5)*Double.parseDouble(NETPROFITBTAX[i])/100));
		   }

		   cell = cells.getCell("A123");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, " ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(122, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(Double.parseDouble(SALES[i]) > 0){
				   cell.setValue(Math.round((100-7.5)*Double.parseDouble(NETPROFITBTAX[i])/(100*Double.parseDouble(SALES[i]))) + "%");
			   }else{
				   cell.setValue("-");
			   }
		   }
		   
		   cell = cells.getCell("A124");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, "Year-End Adjustment");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(123, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");
		   }

		   cell = cells.getCell("A125");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, " OTHER ADJUSTMENT");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(125, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");
		   }

		   cell = cells.getCell("A126");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, "DEFERRED TAX");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(127, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue((100 - 7.5)*Double.parseDouble(NETPROFITBTAX[i])/100);
		   }
			   
		   cell = cells.getCell("A136");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, "Unrealised exhange Gain/(Loss)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(127, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue(0);
		   }
		   
		  }catch(Exception ex){
				throw new Exception("Khong the tao duoc tua de cho bao cao");
			}
	}


	private void XuatFileExcel_PLCompany_2(ILapngansach bean, Worksheet worksheet) throws Exception{
		try {
			NumberFormat formatter = new DecimalFormat("#,###,###.##");
			String[] COGS = new String[13];
			String[] SALES = new String[13];
			String[] NETPROFITBTAX = new String[13];
			for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
				SALES[i] = "0";
				COGS[i]  = "0";
				NETPROFITBTAX[i] = "0";
			}			
			
			Cell cell;
			Style style;
			Font font;


			Cells cells = worksheet.getCells();	    
			cells.setRowHeight(0, 18.0f);
			cells.setColumnWidth(0, 51.63f);
			cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell,Color.BLACK, true, 14, "NEW TOYO ALUMINIUM PAPER PACKAGING CO., LTD");
		   
			cell = cells.getCell("A2");
			ReportAPI.getCellStyle(cell,Color.BLACK, true, 16, this.getDate().substring(0, 4) + " Profit and Loss Account" );
		   
			cell = cells.getCell("A4");		   
			ReportAPI.getCellStyle(cell,Color.BLACK, true, 16, "PARTICULAR");		   		   
			cells.merge(3, 0 , 4, 0);
			
			style = cell.getStyle();
			style.setHAlignment(TextAlignmentType.CENTER);
			style.setPatternStyle(PatternType.SOLID);
			style.setBorderColor(1, Color.BLACK);
			style.setBorderLine(BorderType.TOP, BorderLineType.DOUBLE);
			cell.setStyle(style);
			
			cell = cells.getCell("A5");
			style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			cell.setStyle(style);
		   
			for (int i = 1; i <= (Integer.parseInt(this.getDate().substring(5, 7)) + 1); i++ ){
			   cells.setColumnWidth(i, 22.38f);
			   cell = cells.getCell(3, i);
			   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Actual" );
			   style = cell.getStyle();
			   style.setHAlignment(TextAlignmentType.CENTER);		   
			   style.setColor(new Color(204, 255, 204));
			   style.setBorderColor(1, Color.BLACK);
			   style.setPatternStyle(PatternType.SOLID);
			   style.setBorderLine(BorderType.TOP, BorderLineType.DOUBLE);
			   style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
			   style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
			   cell.setStyle(style);
			   
			   cell = cells.getCell(4, i);
			   if(i < Integer.parseInt(this.getDate().substring(5, 7)) + 1){
				   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, this.getDate(("" + i), ("" + 2014)) );
			   }else{
				   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Total" );
			   }
			   style = cell.getStyle();
			   style.setHAlignment(TextAlignmentType.CENTER);
			   style.setColor(new Color(204, 255, 204));
			   style.setPatternStyle(PatternType.SOLID);
			   style.setBorderLine(BorderType.TOP, BorderLineType.DOUBLE);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);			   
			   style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
			   cell.setStyle(style);
			   
		   }

		   String[] data = bean.PL_SalesVolumn();
		   double total = 0;

		   cells.setRowHeight(0, 22.0f);
		   cell = cells.getCell("A8");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "SALES AMOUNT (NET OF TAX)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(3);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_SalesAmount_Actual();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(13, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
		   }

		   cells.setRowHeight(0, 18.0f);
		   cell = cells.getCell("A9");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Lamination");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_SalesAmount_LA();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(14, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   SALES[i] = data[i];
			   }else{			   
				   cell.setValue(total);
				   SALES[i] = "" + total;
			   }
			   
		   }

		   cell = cells.getCell("A10");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Sales return/allowance");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
//		   data = bean.;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(15, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");
		   }

		   cell = cells.getCell("A11");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Paper core/cone");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_SalesAmount_PA();
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(16, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");
		   }

		   cell = cells.getCell("A12");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Sales return/allowance");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
//		   data = bean.;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(15, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");
		   }
		   
		   cell = cells.getCell("A13");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Others");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
//		   data = bean.;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(15, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");
		   }		   
		   
		   cell = cells.getCell("A16");		   
		   ReportAPI.getCellStyle(cell,Color.MAGENTA, true, 12, "MATERIALS CONSUMED  02 division ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(18, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   COGS[i] = data[i];
			   }else{			   
				   cell.setValue(total);
				   COGS[i] = "" + total;
			   }
			   
		   }

		   cell = cells.getCell("A17");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PAPER CONSUMED");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume_Paper();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7))); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(19, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);			   
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)))){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A18");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, "FOIL CONSUMED");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume_Foil();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(20, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A19");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, "GLUE CONSUMED");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume_Glue();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(21, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A20");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, "LACQUER CONSUMED");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume_Lacquer();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(22, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A21");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "SUB-MATERIAL CONSUMED");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume_Submaterial();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(23, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A23");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "DIRECT LABOUR(LUONG TRUC TIEP)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_DirectLabour();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(25, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   COGS[i] = "" + (Double.parseDouble(COGS[i]) + Double.parseDouble(data[i]));				   
			   }else{			   
				   cell.setValue(total);
				   COGS[i] = "" + (Double.parseDouble(COGS[i]) + total);
			   }
			   
		   }

		   cell = cells.getCell("A24");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "WORKERS' SALARY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_WorkerSalary();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(26, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }
		   
		   cell = cells.getCell("A25");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OVERTIME FOR WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_OvertimeWorkers();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(27, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)))){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A26");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "AMORTIZED 13 TH SALARY OF WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_AmortizedWorkers();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(28, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A27");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "SOCIAL INSURANCE (WORKERS)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_SocialInsurance();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(29, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A28");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "HEALTH INSURANCE (WORKERS)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_HealthInsurance();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(30, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A30");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "PRODUCTION OVERHEAD(CPHI SAN XUAT)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_ProductionOverhead();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(32, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   COGS[i] = "" + (Double.parseDouble(COGS[i]) + Double.parseDouble(data[i]));
			   }else{			   
				   cell.setValue(total);
				   COGS[i] = "" + (Double.parseDouble(COGS[i]) + total);
			   }
			   
		   }

		   cell = cells.getCell("A31");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "ALLOWANCES FOR WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_WorkerAllowances();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(33, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }
		   
		   cell = cells.getCell("A32");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "BONUS FOR WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_WorkerBonus();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(34, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A33");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION OF BUILDING");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_DepreciationBuilding_Production();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(35, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A34");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION MACHINE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_DepreciationMachine();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(36, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A35");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION MOTOR VEHICLE FOR FACTORY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_DepreciationMotorVehicle_Production();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(37, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A36");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "RENTAL CHARGE OF MACHINERY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_RentalCharge_Machinery();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(38, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A37");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "ELECTRICITY+WATER(FACT.)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_ElectricityWater();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(39, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A38");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "FACTORY EXPENSES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_FactoryExpenses();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(40, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A39");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PRINTING & STATIONERY (FACTORY)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Printing_Stationery();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(41, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A40");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "MEALS FOR WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Workers_Meals();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(42, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }


		   }

		   cell = cells.getCell("A41");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UPKEEP OF MOTORVEHICLE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Upkeep_Motorvehicle();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(43, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A42");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UPKEEP OF MACHINERY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Upkeep_Machinery();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(44, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A43");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TRAINING COST");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_TrainingCost();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(45, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A44");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "WELFARE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Welfare();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(46, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A45");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INSURANCE A");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_InsuranceA();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(47, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A46");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UPKEEP OF FACTORY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_UpkeepFactory();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(48, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A47");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UNIFORM FOR WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Workers_Uniform() ;
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(49, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A48");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "LAND RENTAL (DIVIDED BY 2)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_LandRental();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(50, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A49");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TOOL & ACCESSORIES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Tool_Accessories();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(51, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A50");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OIL FOR MACHINE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Oil_Machine();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(52, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A51");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INDIRECT LABOUR SALARY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_IndirectLabourSalary();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(53, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }


		   }

		   cell = cells.getCell("A52");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INDIRECT LABOUR 13TH SALARY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Indirect_13th_LabourSalary();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(54, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A53");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INDIRECT LABOUR BONUS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Indirect_LabourBonus();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(55, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }
		   
		   cell = cells.getCell("A56");		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "Finished Goods purchased/ (Transferred)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(58, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");

		   }

		   cell = cells.getCell("A57");		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "W.I.P Opening");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(59, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");

		   }

		   cell = cells.getCell("A58");		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "W.I.P Closing");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(60, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");

		   }

		   cell = cells.getCell("A59");		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "Finished goods Opening");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(61, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");

		   }

		   cell = cells.getCell("A60");		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "Finished goods Closing");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(62, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");

		   }

		   cell = cells.getCell("A61");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "% RM/Sales");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(64, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue(Double.parseDouble(COGS[i]));
		   }
		   
		   cell = cells.getCell("A62");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "Cost of Raw Materials");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(64, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue(Double.parseDouble(COGS[i]));
		   }

		   
		   cell = cells.getCell("A63");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "Manufacturing Cost");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(64, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue(Double.parseDouble(COGS[i]));
		   }

		   
		   cell = cells.getCell("A64");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "COST OF GOODS SOLD(02 Division)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(64, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue(Double.parseDouble(COGS[i]));
		   }

		   cell = cells.getCell("A65");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, "Gross profit");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(65, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   NETPROFITBTAX[i] = "" + (Double.parseDouble(SALES[i]) - Double.parseDouble(COGS[i]));
			   
			   if(Double.parseDouble(NETPROFITBTAX[i]) < 0){ 		
				   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(NETPROFITBTAX[i])*(-1))) + ")");
			   }else{ 							
				   cell.setValue(formatter.format(Math.round(Double.parseDouble(NETPROFITBTAX[i]))));
			   } 
			   
		   }

		   cell = cells.getCell("A66");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, false, 12, " ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
//		   data = bean.PL_MaterialConsume();
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(66, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   
			   cell.setStyle(style);			   			   
			   if( Double.parseDouble(SALES[i]) != 0)
				   cell.setValue(formatter.format(100*(Double.parseDouble(SALES[i]) - Double.parseDouble(COGS[i]))/ Double.parseDouble(SALES[i])) + "%");
			   else
				   cell.setValue("-");
		   }
		   
		   cell = cells.getCell("A68");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "SELLING EXPENSES(CHI PHI BAN HANG)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_SellingExpenses();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(68, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(data[i]));
			   }else{			   
				   cell.setValue(total);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - total);
			   }
			   			   
		   }

		   cell = cells.getCell("A69");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TRANSPORTATION");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Transportation();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(69, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A70");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "COMMISSION");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Commission();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(70, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A71");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PROMOTION EXPENSE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_PromotionExpenses();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(71, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A73");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "ADMINISTRATIVE OVERHEAD (CHI PHI QUAN LY)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_AdminOverhead();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(73, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(data[i]));
			   }else{			   
				   cell.setValue(total);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - total);
			   }
			   
		   }

		   cell = cells.getCell("A74");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "ALLOWANCES FOR STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Staff_Allowances();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(74, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A75");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "BANK CHARGES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_BankCharges();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(75, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A76");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "BONUS FOR STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_StaffsBonus();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(76, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A77");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION OF BUILDING");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Depreciation_Building_Office();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(77, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A78");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION OF OFFICE EQUIPMENT");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Depreciation_Equipment_Office();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(78, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A79");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION MOTOR VEHICLE FOR OFFICE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Depreciation_Motor_Office();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(79, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A80");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PHONE+FAX +ELECTRICITY CHARGE FOR OFFICE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Phone_Fax_Electricity_Office();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(80, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A81");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PUBLIC RELATION EXPENSES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_PublicRelations();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(81, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }
		   
		   cell = cells.getCell("A82");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "ENTERTAINMENT");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Entertainments();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(82, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A83");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OFFICE EXPENSES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_OfficeExpenses();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(83, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A84");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "HANDLING CHARGE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_HandlingCharges();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(84, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A85");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PRINTING & STATIONERY (OFFICE)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Printing_Stationery_Office();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(85, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A86");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TRAINING FEE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_TrainingFees();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(86, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A87");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TRAVELLING");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Trevelling();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(87, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A88");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UPKEEP OF MOTOR VEHICLE (OFFICE)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Upkeep_Motor_Vehicle_Office();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(88, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A89");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "AUDITING FEE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_AuditingFees();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(89, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A90");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INSURANCE (office)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Insurance_Office();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(90, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A91");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PROVISION EXPENSES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_StaffUniform();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(91, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A92");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "LAND RENTAL");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_LandRental();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(92, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }
		   
		   cell = cells.getCell("A93");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "R & D EXPENSES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_RD_Expenses();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(93, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A94");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "MANAGEMENT FEE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_ManagementFees();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(94, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A96");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "ADMIN. SALARY(LUONG QUAN LY)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_AdminSalary();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(96, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(data[i]));				   
			   }else{			   
				   cell.setValue(total);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - total);
			   }

		   }

		   cell = cells.getCell("A97");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "STAFFS SALARY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Staff_Allowances();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(74, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }


		   cell = cells.getCell("A98");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OVERTIME FOR STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_StaffOvertime();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(98, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A99");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "AMORTIZED 13th SALARY OF STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Staff_Amortized_13th_Salary();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(99, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }
		   
		   cell = cells.getCell("A100");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "SOCIAL INSURANCE OF STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Staff_SocialInsurance();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(100, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A101");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "HEALTH INSURANCE (STAFFS)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Staff_HealthInsurance();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(101, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A103");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "FINANCIAL ACTIVITY(HOAT DONG TAI CHINH)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Financial_Activity();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(103, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   if(Double.parseDouble(data[i]) < 0){ 		
					   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(data[i])*(-1))) + ")");
				   }else{ 							
					   cell.setValue(formatter.format(Math.round(Double.parseDouble(data[i]))));
				   } 
				   
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(data[i]));

				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   if(total < 0){ 
					   cell.setValue("(" + formatter.format(Math.round(total)*(-1)) + ")");
				   }else{ 							
					   cell.setValue(formatter.format(Math.round(total)));
				   } 
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - total);
			   }
			   
			   
		   }

		   cell = cells.getCell("A104");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INTEREST INCOME (51501)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Interest_Income();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(104, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(data[i]))) + ")");

				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue("(" + formatter.format(Math.round(total)) + ")");
			   }

		   }

		   cell = cells.getCell("A105");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "GAIN ON EXCHANGE RESERVE (51502)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Gain_Exchange_Reserve();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(105, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(data[i])*(-1))) + ")");

				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue("(" + formatter.format(Math.round(total)) + ")");
			   }

		   }

		   cell = cells.getCell("A106");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "LOAN INTEREST (63501)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_LoanInterest();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(106, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A107");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "LOSS ON EXCHANGE RESERVE (63502)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Loss_Exchange_Reserve();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(107, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A109");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "OTHER ACTIVITY (HOAT DONG KHAC)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_OtherActivity();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(109, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   if(Double.parseDouble(data[i]) < 0){ 		
					   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(data[i])*(-1))) + ")");
				   }else{ 							
					   cell.setValue(formatter.format(Math.round(Double.parseDouble(data[i]))));
				   } 
				   
				   total = total + Double.parseDouble(data[i]);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(data[i]));
			   }else{			   
				   if(total < 0){ 		
					   cell.setValue("(" + formatter.format(Math.round(total)*(-1)) + ")");
				   }else{ 							
					   cell.setValue(formatter.format(Math.round(total)));
				   } 
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - total);
			   }
		   }

		   cell = cells.getCell("A110");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OTHER INCOME(711)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Other_Income();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(110, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(data[i])*(-1))) + ")");

				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue("(" + formatter.format(Math.round(total)*(-1)) + ")");
			   }

		   }
		   
		   cell = cells.getCell("A111");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OTHER EXPENSE (811)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_OtherExpenses();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(111, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A115");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, " Net Profit before tax");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(65, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(Double.parseDouble(NETPROFITBTAX[i]) < 0){ 		
				   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(NETPROFITBTAX[i])*(-1))) + ")");
			   }else{ 							
				   cell.setValue(formatter.format(Math.round(Double.parseDouble(NETPROFITBTAX[i]))));
			   } 
			   
		   }
		   
		   cell = cells.getCell("A117");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, "Additional CIT (Adjust)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(116, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   
			   if(Double.parseDouble(SALES[i]) > 0){
				   cell.setValue(Math.round(100*Double.parseDouble(NETPROFITBTAX[i])/Double.parseDouble(SALES[i])) + "%");
			   }else{
				   cell.setValue("-");
			   }
		   }

		   cell = cells.getCell("A119");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, "CURRENT INCOME TAX EXPENSE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(119, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue(7.5*Double.parseDouble(NETPROFITBTAX[i])/100);
		   }

		   cell = cells.getCell("A121");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, "Net profit after tax");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(121, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue(Math.round((100 - 7.5)*Double.parseDouble(NETPROFITBTAX[i])/100));
		   }

		   cell = cells.getCell("A123");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, " ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(122, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(Double.parseDouble(SALES[i]) > 0){
				   cell.setValue(Math.round((100-7.5)*Double.parseDouble(NETPROFITBTAX[i])/(100*Double.parseDouble(SALES[i]))) + "%");
			   }else{
				   cell.setValue("-");
			   }
		   }
		   
		   cell = cells.getCell("A124");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, "Year-End Adjustment");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(123, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");
		   }

		   cell = cells.getCell("A125");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, " OTHER ADJUSTMENT");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(125, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");
		   }

		   cell = cells.getCell("A126");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, "DEFERRED TAX");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(127, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue((100 - 7.5)*Double.parseDouble(NETPROFITBTAX[i])/100);
		   }
			   
		   cell = cells.getCell("A136");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, "Unrealised exhange Gain/(Loss)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(127, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue(0);
		   }
		   
		  }catch(Exception ex){
				throw new Exception("Khong the tao duoc tua de cho bao cao");
			}
	}
	
/*	private void XuatFileExcel_PLCompany2(ILapngansach bean, Worksheet worksheet) throws Exception{
		try {
			NumberFormat formatter = new DecimalFormat("#,###,###.##");
			String[] COGS = new String[13];
			String[] SALES = new String[13];
			String[] NETPROFITBTAX = new String[13];
			for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
				SALES[i] = "0";
				COGS[i]  = "0";
				NETPROFITBTAX[i] = "0";
			}			
			
			Cell cell;
			Style style;
			Font font;


			Cells cells = worksheet.getCells();	    
			cells.setRowHeight(0, 15.75f);
			cells.setColumnWidth(0, 51.63f);
			cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "NEW TOYO ALUMINIUM PAPER PACKAGING CO., LTD");
		   
			cell = cells.getCell("A2");
			ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Profit and Loss Account" );
		   
			cell = cells.getCell("A4");		   
			ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PARTICULAR");		   		   
			cells.merge(3, 0 , 4, 0);
			
			style = cell.getStyle();
			style.setHAlignment(TextAlignmentType.CENTER);
			style.setPatternStyle(PatternType.SOLID);
			style.setBorderColor(1, Color.BLACK);
			style.setBorderLine(BorderType.TOP, BorderLineType.DOUBLE);
			cell.setStyle(style);
			
			cell = cells.getCell("A5");
			style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			cell.setStyle(style);
		   
			for (int i = 1; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++ ){
			   cells.setColumnWidth(i, 22.38f);
			   cell = cells.getCell(3, i);
			   if(i <= (Integer.parseInt(this.getDate().substring(5, 7)) ))
				   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, Integer.toString(i) + "-2014" );
			   else
				   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TOTAL" );
			   style = cell.getStyle();
			   style.setHAlignment(TextAlignmentType.CENTER);		   
			   style.setColor(new Color(204, 255, 204));
			   style.setBorderColor(1, Color.BLACK);
			   style.setPatternStyle(PatternType.SOLID);
			   style.setBorderLine(BorderType.TOP, BorderLineType.DOUBLE);
			   style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
			   style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
			   cell.setStyle(style);
			   
			   cell = cells.getCell(4, i);
			   if(i == 13)
				   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, this.getDate(("" + i), ("" + 2014)) );
			   else
				   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Total") ;
			   
			   style = cell.getStyle();
			   style.setHAlignment(TextAlignmentType.CENTER);
			   style.setColor(new Color(204, 255, 204));
			   style.setPatternStyle(PatternType.SOLID);
			   style.setBorderLine(BorderType.TOP, BorderLineType.DOUBLE);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);			   
			   style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
			   cell.setStyle(style);
			   
		   }

		   cell = cells.getCell("A7");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Sales Volume (Kg)");		   		   
		   cells.merge(3, 0 , 4, 0);
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   String[] data = bean.PL_SalesVolumn();
		   double total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(6, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);
			   cell.setStyle(style);
			   
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }
		   
		   cell = cells.getCell("A8");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, false, 12, "Lamination");		   		   
		   		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(3);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_SalesVolumn_LA();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(7, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
		   }

		   cell = cells.getCell("A9");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, false, 12, "Paper Core");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(3);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_SalesVolumn_PA();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(8, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A14");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "SALES AMOUNT (NET OF TAX)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(3);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_SalesAmount();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(13, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
		   }

		   cell = cells.getCell("A15");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Sales");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_SalesAmount();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(14, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   SALES[i] = data[i];
			   }else{			   
				   cell.setValue(total);
				   SALES[i] = "" + total;
			   }
			   
		   }

		   cell = cells.getCell("A16");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Sales return/allowance");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
//		   data = bean.PL_SalesAmount();
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(15, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");
		   }

		   cell = cells.getCell("A17");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Others");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
//		   data = bean.PL_SalesAmount();
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(16, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");
		   }

		   cell = cells.getCell("A19");		   
		   ReportAPI.getCellStyle(cell,Color.MAGENTA, true, 12, "MATERIALS CONSUMED ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(18, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   COGS[i] = data[i];
			   }else{			   
				   cell.setValue(total);
				   COGS[i] = "" + total;
			   }
			   
		   }

		   cell = cells.getCell("A18");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, false, 12, " ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
//		   data = bean.PL_MaterialConsume();
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(17, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   
			   cell.setStyle(style);	
			   if(Double.parseDouble(SALES[i]) != 0)
				   cell.setValue(formatter.format(100*Double.parseDouble(COGS[i])/ Double.parseDouble(SALES[i])) + "%");
			   else
				   cell.setValue("-");
		   }

		   cell = cells.getCell("A20");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PAPER CONSUMED");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume_Paper();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(19, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);			   
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A21");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, "FOIL CONSUMED");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume_Foil();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(20, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A22");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, "GLUE CONSUMED");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume_Glue();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(21, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A23");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, "LACQUER CONSUMED");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume_Lacquer();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(22, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A24");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "SUB-MATERIAL CONSUMED");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume_Submaterial();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(23, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A26");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "DIRECT LABOUR(LUONG TRUC TIEP)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_DirectLabour();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(25, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   COGS[i] = "" + (Double.parseDouble(COGS[i]) + Double.parseDouble(data[i]));				   
			   }else{			   
				   cell.setValue(total);
				   COGS[i] = "" + (Double.parseDouble(COGS[i]) + total);
			   }
			   
		   }

		   cell = cells.getCell("A27");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "WORKERS' SALARY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_WorkerSalary();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(26, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }
		   
		   cell = cells.getCell("A28");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OVERTIME FOR WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_OvertimeWorkers();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(27, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A29");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "AMORTIZED 13 TH SALARY OF WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_AmortizedWorkers();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(28, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A30");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "SOCIAL INSURANCE+UNION FEE (WORKERS)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_SocialInsurance();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(29, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A31");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "HEALTH INSURANCE (WORKERS)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_HealthInsurance();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(30, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A33");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "PRODUCTION OVERHEAD(CPHI SAN XUAT)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_ProductionOverhead();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(32, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   COGS[i] = "" + (Double.parseDouble(COGS[i]) + Double.parseDouble(data[i]));
			   }else{			   
				   cell.setValue(total);
				   COGS[i] = "" + (Double.parseDouble(COGS[i]) + total);
			   }
			   
		   }

		   cell = cells.getCell("A34");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "ALLOWANCES FOR WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_WorkerAllowances();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(33, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }
		   
		   cell = cells.getCell("A35");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "BONUS FOR WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_WorkerBonus();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(34, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A36");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION OF BUILDING");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_DepreciationBuilding_Production();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(35, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A37");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION MACHINE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_DepreciationMachine();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(36, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A38");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION MOTOR VEHICLE FOR FACTORY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_DepreciationMotorVehicle_Production();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(37, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A39");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "RENTAL CHARGE OF MACHINERY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_RentalCharge_Machinery();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(38, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A40");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "ELECTRICITY+WATER(FACT.)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_ElectricityWater();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(39, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A41");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "FACTORY EXPENSES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_FactoryExpenses();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(40, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A42");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PRINTING & STATIONERY (FACTORY)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Printing_Stationery();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(41, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A43");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "MEALS FOR WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Workers_Meals();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(42, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }


		   }

		   cell = cells.getCell("A44");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UPKEEP OF MOTORVEHICLE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Upkeep_Motorvehicle();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(43, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A45");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UPKEEP OF MACHINERY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Upkeep_Machinery();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(44, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A46");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TRAINING COST");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_TrainingCost();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(45, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A47");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "WELFARE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Welfare();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(46, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A48");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INSURANCE A");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_InsuranceA();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(47, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A49");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UPKEEP OF FACTORY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_UpkeepFactory();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(48, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A50");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UNIFORM FOR WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Workers_Uniform() ;
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(49, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A51");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "LAND RENTAL (DIVIDED BY 2)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_LandRental();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(50, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A52");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TOOL & ACCESSORIES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Tool_Accessories();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(51, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A53");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OIL FOR MACHINE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Oil_Machine();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(52, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A54");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INDIRECT LABOUR SALARY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_IndirectLabourSalary();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(53, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }


		   }

		   cell = cells.getCell("A55");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INDIRECT LABOUR 13TH SALARY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Indirect_13th_LabourSalary();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(54, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A56");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INDIRECT LABOUR BONUS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Indirect_LabourBonus();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(55, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }
		   
		   cell = cells.getCell("A59");		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "Goods purchased");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(58, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");

		   }

		   cell = cells.getCell("A60");		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "W.I.P Opening");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(59, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");

		   }

		   cell = cells.getCell("A61");		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "W.I.P Closing");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(60, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");

		   }

		   cell = cells.getCell("A62");		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "Finished goods Opening");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(61, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");

		   }

		   cell = cells.getCell("A63");		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "Finished goods Closing");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(62, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");

		   }
		   
		   cell = cells.getCell("A65");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "Cost of goods sold");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(64, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue(Double.parseDouble(COGS[i]));
		   }

		   cell = cells.getCell("A66");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, "Gross profit");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(65, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   NETPROFITBTAX[i] = "" + (Double.parseDouble(SALES[i]) - Double.parseDouble(COGS[i]));
			   
			   if(Double.parseDouble(NETPROFITBTAX[i]) < 0){ 		
				   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(NETPROFITBTAX[i])*(-1))) + ")");
			   }else{ 							
				   cell.setValue(formatter.format(Math.round(Double.parseDouble(NETPROFITBTAX[i]))));
			   } 
			   
		   }

		   cell = cells.getCell("A67");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, false, 12, " ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
//		   data = bean.PL_MaterialConsume();
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(66, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   
			   cell.setStyle(style);			   			   
			   if( Double.parseDouble(SALES[i]) != 0)
				   cell.setValue(formatter.format(100*(Double.parseDouble(SALES[i]) - Double.parseDouble(COGS[i]))/ Double.parseDouble(SALES[i])) + "%");
			   else
				   cell.setValue("-");
		   }
		   
		   cell = cells.getCell("A69");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "SELLING EXPENSES(CHI PHI BAN HANG)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_SellingExpenses();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(68, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(data[i]));
			   }else{			   
				   cell.setValue(total);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - total);
			   }
			   			   
		   }

		   cell = cells.getCell("A70");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TRANSPORTATION");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Transportation();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(69, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A71");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "COMMISSION /HADNLING CHARGES FOR EXPORT SALES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Commission();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(70, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A72");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PROMOTION EXPENSE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_PromotionExpenses();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(71, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A74");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "ADMINISTRATIVE OVERHEAD (CHI PHI QUAN LY)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_AdminOverhead();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(73, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(data[i]));
			   }else{			   
				   cell.setValue(total);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - total);
			   }
			   
		   }

		   cell = cells.getCell("A75");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "ALLOWANCES FOR STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Staff_Allowances();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(74, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A76");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "BANK CHARGES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_BankCharges();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(75, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A77");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "BONUS FOR STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_StaffsBonus();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(76, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A78");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION OF BUILDING");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Depreciation_Building_Office();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(77, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A79");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION OF OFFICE EQUIPMENT");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Depreciation_Equipment_Office();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(78, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A80");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION MOTOR VEHICLE FOR OFFICE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Depreciation_Motor_Office();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(79, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A81");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PHONE+FAX +ELECTRICITY CHARGE FOR OFFICE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Phone_Fax_Electricity_Office();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(80, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A82");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PUBLIC RELATION EXPENSES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_PublicRelations();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(81, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }
		   
		   cell = cells.getCell("A83");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "ENTERTAINMENT");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Entertainments();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(82, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A84");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OFFICE EXPENSES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_OfficeExpenses();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(83, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A85");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "HANDLING CHARGE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_HandlingCharges();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(84, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A86");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PRINTING & STATIONERY (OFFICE)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Printing_Stationery_Office();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(85, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A87");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TRAINING FEE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_TrainingFees();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(86, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A88");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TRAVELLING");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Trevelling();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(87, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A89");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UPKEEP OF MOTOR VEHICLE (OFFICE)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Upkeep_Motor_Vehicle_Office();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(88, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A90");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "AUDITING FEE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_AuditingFees();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(89, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A91");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INSURANCE (office)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Insurance_Office();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(90, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A92");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UNIFORM FOR STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_StaffUniform();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(91, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A93");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "LAND RENTAL");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_LandRental();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(92, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }
		   
		   cell = cells.getCell("A94");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "R & D EXPENSES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_RD_Expenses();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(93, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A95");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "MANAGEMENT FEE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_ManagementFees();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(94, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A97");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "ADMIN. SALARY(LUONG QUAN LY)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_AdminSalary();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(96, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(data[i]));				   
			   }else{			   
				   cell.setValue(total);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - total);
			   }

		   }

		   cell = cells.getCell("A98");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "STAFFS SALARY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Staff_Allowances();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(74, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }


		   cell = cells.getCell("A99");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OVERTIME FOR STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_StaffOvertime();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(98, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A100");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "AMORTIZED 13th SALARY OF STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Staff_Amortized_13th_Salary();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(99, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }
		   
		   cell = cells.getCell("A101");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "SOCIAL INSURANCE+UNION FEE OF STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Staff_SocialInsurance();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(100, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A102");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "HEALTH INSURANCE (STAFFS)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Staff_HealthInsurance();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(101, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A104");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "FINANCIAL ACTIVITY(HOAT DONG TAI CHINH)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Financial_Activity();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(103, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   if(Double.parseDouble(data[i]) < 0){ 		
					   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(data[i])*(-1))) + ")");
				   }else{ 							
					   cell.setValue(formatter.format(Math.round(Double.parseDouble(data[i]))));
				   } 
				   
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(data[i]));

				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   if(total < 0){ 
					   cell.setValue("(" + formatter.format(Math.round(total)*(-1)) + ")");
				   }else{ 							
					   cell.setValue(formatter.format(Math.round(total)));
				   } 
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - total);
			   }
			   
			   
		   }

		   cell = cells.getCell("A105");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INTEREST INCOME (51501)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Interest_Income();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(104, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(data[i]))) + ")");

				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue("(" + formatter.format(Math.round(total)) + ")");
			   }

		   }

		   cell = cells.getCell("A106");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "GAIN ON EXCHANGE RESERVE (51502)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Gain_Exchange_Reserve();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(105, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(data[i])*(-1))) + ")");

				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue("(" + formatter.format(Math.round(total)) + ")");
			   }

		   }

		   cell = cells.getCell("A107");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "LOAN INTEREST (63501)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_LoanInterest();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(106, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A108");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "LOSS ON EXCHANGE RESERVE (63502)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Loss_Exchange_Reserve();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(107, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A110");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "OTHER ACTIVITY (HOAT DONG KHAC)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_OtherActivity();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(109, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   if(Double.parseDouble(data[i]) < 0){ 		
					   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(data[i])*(-1))) + ")");
				   }else{ 							
					   cell.setValue(formatter.format(Math.round(Double.parseDouble(data[i]))));
				   } 
				   
				   total = total + Double.parseDouble(data[i]);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(data[i]));
			   }else{			   
				   if(total < 0){ 		
					   cell.setValue("(" + formatter.format(Math.round(total)*(-1)) + ")");
				   }else{ 							
					   cell.setValue(formatter.format(Math.round(total)));
				   } 
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - total);
			   }
		   }

		   cell = cells.getCell("A111");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OTHER INCOME(711)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Other_Income();
		   total = 0;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(110, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(data[i])*(-1))) + ")");

				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue("(" + formatter.format(Math.round(total)*(-1)) + ")");
			   }

		   }
		   
		   cell = cells.getCell("A112");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OTHER EXPENSE (811)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_OtherExpenses();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(111, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A116");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, " Net Profit before tax");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(65, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(Double.parseDouble(NETPROFITBTAX[i]) < 0){ 		
				   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(NETPROFITBTAX[i])*(-1))) + ")");
			   }else{ 							
				   cell.setValue(formatter.format(Math.round(Double.parseDouble(NETPROFITBTAX[i]))));
			   } 
			   
		   }
		   
		   cell = cells.getCell("A117");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, " ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(116, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   
			   if(Double.parseDouble(SALES[i]) > 0){
				   cell.setValue(Math.round(100*Double.parseDouble(NETPROFITBTAX[i])/Double.parseDouble(SALES[i])) + "%");
			   }else{
				   cell.setValue("-");
			   }
		   }

		   cell = cells.getCell("A120");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, "CURRENT INCOME TAX EXPENSE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(119, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue(7.5*Double.parseDouble(NETPROFITBTAX[i])/100);
		   }

		   cell = cells.getCell("A122");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, " Net profit after tax");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(121, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue(Math.round((100 - 7.5)*Double.parseDouble(NETPROFITBTAX[i])/100));
		   }

		   cell = cells.getCell("A123");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, " ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(122, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(Double.parseDouble(SALES[i]) > 0){
				   cell.setValue(Math.round((100-7.5)*Double.parseDouble(NETPROFITBTAX[i])/(100*Double.parseDouble(SALES[i]))) + "%");
			   }else{
				   cell.setValue("-");
			   }
		   }
		   
		   cell = cells.getCell("A124");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, "Unrealised exchange");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(123, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");
		   }

		   cell = cells.getCell("A126");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, " OTHER ADJUSTMENT");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(125, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");
		   }

		   cell = cells.getCell("A128");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, "Y-T-D Net profit after adj.");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(127, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue((100 - 7.5)*Double.parseDouble(NETPROFITBTAX[i])/100);
		   }
			   
		  }catch(Exception ex){
				throw new Exception("Khong the tao duoc tua de cho bao cao");
			}
	}
*/
	
	private void XuatFileExcel_PLLamination(ILapngansach bean, Worksheet worksheet) throws Exception{
		try {
			NumberFormat formatter = new DecimalFormat("#,###,###.##");
			String[] COGS = new String[13];
			String[] SALES = new String[13];
			String[] NETPROFITBTAX = new String[13];
			for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
				SALES[i] = "0";
				COGS[i]  = "0";
				NETPROFITBTAX[i] = "0";
			}			
			
			Cell cell;
			Style style;
			Font font;

			Cells cells = worksheet.getCells();	    
			cells.setRowHeight(0, 15.75f);
			cells.setColumnWidth(0, 51.63f);
			cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "NEW TOYO ALUMINIUM PAPER PACKAGING CO., LTD");
		   
			cell = cells.getCell("A2");
			ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Profit and Loss Account" );
		   
			cell = cells.getCell("A4");		   
			ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PARTICULAR");		   		   
			cells.merge(3, 0 , 4, 0);
			
			style = cell.getStyle();
			style.setHAlignment(TextAlignmentType.CENTER);
			style.setPatternStyle(PatternType.SOLID);
			style.setBorderColor(1, Color.BLACK);
			style.setBorderLine(BorderType.TOP, BorderLineType.DOUBLE);
			cell.setStyle(style);
			
			cell = cells.getCell("A5");
			style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			cell.setStyle(style);
		   
			for (int i = 1; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++ ){
			   cells.setColumnWidth(i, 22.38f);
			   cell = cells.getCell(3, i);
			   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Budget" );
			   style = cell.getStyle();
			   style.setHAlignment(TextAlignmentType.CENTER);		   
			   style.setColor(new Color(204, 255, 204));
			   style.setBorderColor(1, Color.BLACK);
			   style.setPatternStyle(PatternType.SOLID);
			   style.setBorderLine(BorderType.TOP, BorderLineType.DOUBLE);
			   style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
			   style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
			   cell.setStyle(style);
			   
			   cell = cells.getCell(4, i);
			   if(i == (Integer.parseInt(this.getDate().substring(5, 7)) ))
				   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, this.getDate(("" + i), ("" + 2014)) );
			   else
				   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Total") ;
			   
			   style = cell.getStyle();
			   style.setHAlignment(TextAlignmentType.CENTER);
			   style.setColor(new Color(204, 255, 204));
			   style.setPatternStyle(PatternType.SOLID);
			   style.setBorderLine(BorderType.TOP, BorderLineType.DOUBLE);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);			   
			   style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
			   cell.setStyle(style);
			   
		   }

		   cell = cells.getCell("A7");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Lamination Sales Volume (Kg)");		   		   
		   cells.merge(3, 0 , 4, 0);
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   String[] data = bean.PL_SalesVolumn_LA();
		   double total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(6, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);
			   cell.setStyle(style);
			   
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }
		   
		   String[][] tmp2 = bean.PL_SalesVolumn_LA_Details();
		   int num = 7;
		   
		   for(int j = 0; j < tmp2.length; j++){				
			   	total = 0;
			   
				cells.setColumnWidth(1, 22.38f);
				cell = cells.getCell(num, 0);
				style = cell.getStyle();
				   
				style.setHAlignment(TextAlignmentType.LEFT);
//				style.setNumber(3);
				font = style.getFont();
				font.setBold(false);
				font.setSize(12);
				font.setColor(Color.BLACK);
				style.setFont(font);
//				style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);
				cell.setStyle(style);

				cell.setValue(tmp2[j][0]);

				for(int i = 1; i <= 13; i++){														
					cells.setColumnWidth(i + 1, 22.38f);
					cell = cells.getCell(num, i );
					style = cell.getStyle();
					   
					style.setHAlignment(TextAlignmentType.RIGHT);
					style.setNumber(3);
					font = style.getFont();
					font.setBold(false);
					font.setSize(12);
					font.setColor(Color.BLACK);
					style.setFont(font);
					style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);
					cell.setStyle(style);

					if(i < 13){
					   cell.setValue(Double.parseDouble(tmp2[j][i]));
					   total = total + Double.parseDouble(tmp2[j][i]);
					}else{			   
					   cell.setValue(total);
					}			
				}							
				num++;
		   }


		   cell = cells.getCell("A117");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Lamination Sales Amount (Net of tax)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(3);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);

		   data = bean.PL_SalesAmount_LA();
		   total = 0;
		   for(int i = 0; i <= (Integer.parseInt(this.getDate().substring(5, 7)) ); i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(116, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);
			   cell.setStyle(style);
			   
			   if(i < (Integer.parseInt(this.getDate().substring(5, 7)) )){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }
		   
		   tmp2 = bean.PL_SalesAmount_LA_Details();
		   num = 117;
		   
		   for(int j = 0; j < tmp2.length; j++){				
			   total = 0;			
			   cells.setColumnWidth(1, 22.38f);
			   cell = cells.getCell(num, 0);
			   style = cell.getStyle();
				   
			   style.setHAlignment(TextAlignmentType.LEFT);
//				style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//				style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);
			   cell.setStyle(style);

			   cell.setValue(tmp2[j][0]);

			   for(int i = 1; i <= 13; i++){														
					cells.setColumnWidth(i + 1, 22.38f);
					cell = cells.getCell(num, i );
					style = cell.getStyle();
					   
					style.setHAlignment(TextAlignmentType.RIGHT);
					style.setNumber(3);
					font = style.getFont();
					font.setBold(false);
					font.setSize(12);
					font.setColor(Color.BLACK);
					style.setFont(font);
					style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);
					cell.setStyle(style);

					if(i < 13){
					   cell.setValue(Double.parseDouble(tmp2[j][i]));
					   total = total + Double.parseDouble(tmp2[j][i]);
					}else{			   
					   cell.setValue(total);
					}			
				}							
				num++;
		   }

		   cell = cells.getCell("A225");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "LAMINATION SALES AMOUNT (NET OF TAX)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(3);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);

		   data = bean.PL_SalesAmount_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(224, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   SALES[i] = data[i];
			   }else{			   
				   cell.setValue(total);
				   SALES[i] = "" + total;
			   }
			   
		   }
		   
		   cell = cells.getCell("A226");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Lamination");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_SalesAmount_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(225, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   
			   }else{			   
				   cell.setValue(total);
				   
			   }
			   
		   }

		   cell = cells.getCell("A227");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Sales return/allowance");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
//		   data = bean.PL_SalesAmount();
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(226, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");
		   }

		   cell = cells.getCell("A228");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Others");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
//		   data = bean.PL_SalesAmount();
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(227, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");
		   }

		   cell = cells.getCell("A230");		   
		   ReportAPI.getCellStyle(cell,Color.MAGENTA, true, 12, "MATERIALS CONSUMED ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(229, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   COGS[i] = data[i];
			   }else{			   
				   cell.setValue(total);
				   COGS[i] = "" + total;
			   }
			   
		   }

		   cell = cells.getCell("A229");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, false, 12, " ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
//		   data = bean.PL_MaterialConsume();
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(228, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   
			   cell.setStyle(style);	
			   if(Double.parseDouble(SALES[i]) != 0)
				   cell.setValue(formatter.format(100*Double.parseDouble(COGS[i])/ Double.parseDouble(SALES[i])) + "%");
			   else
				   cell.setValue("-");
		   }

		   cell = cells.getCell("A231");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PAPER CONSUMED");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume_Paper_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(230, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);			   
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A232");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, "FOIL CONSUMED");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume_Foil_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(231, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A233");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, "GLUE CONSUMED");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume_Glue_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(232, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A234");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, "LACQUER CONSUMED");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume_Lacquer_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(233, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A235");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "SUB-MATERIAL CONSUMED");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume_Submaterial_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(234, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A237");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "DIRECT LABOUR(LUONG TRUC TIEP)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_DirectLabour_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(236, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   COGS[i] = "" + (Double.parseDouble(COGS[i]) + Double.parseDouble(data[i]));				   
			   }else{			   
				   cell.setValue(total);
				   COGS[i] = "" + (Double.parseDouble(COGS[i]) + total);
			   }
			   
		   }

		   cell = cells.getCell("A238");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "WORKERS' SALARY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_WorkerSalary_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(237, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }
		   
		   cell = cells.getCell("A239");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OVERTIME FOR WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_OvertimeWorkers_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(238, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A240");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "AMORTIZED 13 TH SALARY OF WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_AmortizedWorkers_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(239, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A241");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "SOCIAL INSURANCE+UNION FEE (WORKERS)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_SocialInsurance_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(240, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A242");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "HEALTH INSURANCE (WORKERS)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_HealthInsurance_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(241, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A244");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "PRODUCTION OVERHEAD(CPHI SAN XUAT)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_ProductionOverhead_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(243, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   COGS[i] = "" + (Double.parseDouble(COGS[i]) + Double.parseDouble(data[i]));
			   }else{			   
				   cell.setValue(total);
				   COGS[i] = "" + (Double.parseDouble(COGS[i]) + total);
			   }
			   
		   }

		   cell = cells.getCell("A245");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "ALLOWANCES FOR WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_WorkerAllowances_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(244, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }
		   
		   cell = cells.getCell("A246");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "BONUS FOR WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_WorkerBonus_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(245, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A247");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION OF BUILDING");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_DepreciationBuilding_Production_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(246, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A248");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION MACHINE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_DepreciationMachine_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(247, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A249");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION MOTOR VEHICLE FOR FACTORY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_DepreciationMotorVehicle_Production_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(248, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A250");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "RENTAL CHARGE OF MACHINERY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_RentalCharge_Machinery_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(249, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A251");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "ELECTRICITY+WATER(FACT.)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_ElectricityWater_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(250, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A252");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "FACTORY EXPENSES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_FactoryExpenses_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(251, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A253");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PRINTING & STATIONERY (FACTORY)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Printing_Stationery_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(252, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A254");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "MEALS FOR WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Workers_Meals_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(253, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }


		   }

		   cell = cells.getCell("A255");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UPKEEP OF MOTORVEHICLE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Upkeep_Motorvehicle_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(254, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A256");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UPKEEP OF MACHINERY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Upkeep_Machinery_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(255, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A257");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TRAINING COST");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_TrainingCost_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(256, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A258");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "WELFARE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Welfare_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(257, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A259");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INSURANCE A");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_InsuranceA_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(258, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A260");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UPKEEP OF FACTORY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_UpkeepFactory_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(259, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A261");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UNIFORM FOR WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Workers_Uniform_LA() ;
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(260, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A262");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "LAND RENTAL");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_LandRental_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(261, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A263");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TOOL & ACCESSORIES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Tool_Accessories_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(262, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A264");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OIL FOR MACHINE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Oil_Machine_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(263, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A265");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INDIRECT LABOUR SALARY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_IndirectLabourSalary_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(264, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }


		   }

		   cell = cells.getCell("A266");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INDIRECT LABOUR 13TH SALARY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Indirect_13th_LabourSalary_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(265, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A267");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INDIRECT LABOUR BONUS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Indirect_LabourBonus_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(266, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }
		   
		   cell = cells.getCell("A270");		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "Goods purchased");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(269, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");

		   }

		   cell = cells.getCell("A271");		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "W.I.P Opening");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(270, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");

		   }

		   cell = cells.getCell("A272");		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "W.I.P Closing");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(271, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");

		   }

		   cell = cells.getCell("A273");		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "Finished goods Opening");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(272, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");

		   }

		   cell = cells.getCell("A274");		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "Finished goods Closing");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(273, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");

		   }
		   
		   cell = cells.getCell("A276");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "Cost of goods sold");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(275, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue(Double.parseDouble(COGS[i]));
		   }

		   cell = cells.getCell("A277");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, "Gross profit");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(276, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   NETPROFITBTAX[i] = "" + (Double.parseDouble(SALES[i]) - Double.parseDouble(COGS[i]));
			   
			   if(Double.parseDouble(NETPROFITBTAX[i]) < 0){ 		
				   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(NETPROFITBTAX[i])*(-1))) + ")");
			   }else{ 							
				   cell.setValue(formatter.format(Math.round(Double.parseDouble(NETPROFITBTAX[i]))));
			   } 
			   
		   }

		   cell = cells.getCell("A278");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, false, 12, " ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
//		   data = bean.PL_MaterialConsume();
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(277, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   
			   cell.setStyle(style);			   			   
			   if( Double.parseDouble(SALES[i]) != 0)
				   cell.setValue(formatter.format(100*(Double.parseDouble(SALES[i]) - Double.parseDouble(COGS[i]))/ Double.parseDouble(SALES[i])) + "%");
			   else
				   cell.setValue("-");
		   }
		   
		   cell = cells.getCell("A280");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "SELLING EXPENSES(CHI PHI BAN HANG)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_SellingExpenses_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(279, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(data[i]));
			   }else{			   
				   cell.setValue(total);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - total);
			   }
			   			   
		   }

		   cell = cells.getCell("A281");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TRANSPORTATION");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Transportation_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(280, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A282");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "COMMISSION /HADNLING CHARGES FOR EXPORT SALES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Commission_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(281, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A283");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PROMOTION EXPENSE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_PromotionExpenses_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(282, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A285");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "ADMINISTRATIVE OVERHEAD (CHI PHI QUAN LY)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_AdminOverhead_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(284, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(data[i]));
			   }else{			   
				   cell.setValue(total);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - total);
			   }
			   
		   }

		   cell = cells.getCell("A286");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "ALLOWANCES FOR STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Staff_Allowances_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(285, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A287");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "BANK CHARGES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_BankCharges_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(286, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A288");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "BONUS FOR STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_StaffsBonus_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(287, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A289");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION OF BUILDING");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Depreciation_Building_Office_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(288, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A290");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION OF OFFICE EQUIPMENT");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Depreciation_Equipment_Office_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(289, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A291");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION MOTOR VEHICLE FOR OFFICE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Depreciation_Motor_Office_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(290, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A292");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PHONE+FAX +ELECTRICITY CHARGE FOR OFFICE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Phone_Fax_Electricity_Office_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(291, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A293");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PUBLIC RELATION EXPENSES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_PublicRelations_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(292, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }
		   
		   cell = cells.getCell("A294");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "ENTERTAINMENT");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Entertainments_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(293, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A295");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OFFICE EXPENSES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_OfficeExpenses_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(294, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A296");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "HANDLING CHARGE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_HandlingCharges_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(295, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A297");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PRINTING & STATIONERY (OFFICE)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Printing_Stationery_Office_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(296, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A298");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TRAINING FEE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_TrainingFees_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(297, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A299");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TRAVELLING");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Trevelling_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(298, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A300");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UPKEEP OF MOTOR VEHICLE (OFFICE)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Upkeep_Motor_Vehicle_Office_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(299, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A301");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "AUDITING FEE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_AuditingFees_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(300, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A302");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INSURANCE (office)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Insurance_Office_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(301, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A303");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UNIFORM FOR STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_StaffUniform_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(302, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A304");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "LAND RENTAL");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_LandRental_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(303, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }
		   
		   cell = cells.getCell("A305");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "R & D EXPENSES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_RD_Expenses_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(304, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A306");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "MANAGEMENT FEE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_ManagementFees_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(305, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A308");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "ADMIN. SALARY(LUONG QUAN LY)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_AdminSalary_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(307, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(data[i]));				   
			   }else{			   
				   cell.setValue(total);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - total);
			   }

		   }

		   cell = cells.getCell("A309");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "STAFFS SALARY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Staff_Allowances_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(308, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }


		   cell = cells.getCell("A310");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OVERTIME FOR STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_StaffOvertime_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(309, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A311");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "AMORTIZED 13th SALARY OF STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Staff_Amortized_13th_Salary_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(310, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }
		   
		   cell = cells.getCell("A312");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "SOCIAL INSURANCE+UNION FEE OF STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Staff_SocialInsurance_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(311, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A313");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "HEALTH INSURANCE (STAFFS)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Staff_HealthInsurance_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(312, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A315");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "FINANCIAL ACTIVITY(HOAT DONG TAI CHINH)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Financial_Activity_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(314, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   if(Double.parseDouble(data[i]) < 0){ 		
					   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(data[i])*(-1))) + ")");
				   }else{ 							
					   cell.setValue(formatter.format(Math.round(Double.parseDouble(data[i]))));
				   } 
				   
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(data[i]));

				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   if(total < 0){ 
					   cell.setValue("(" + formatter.format(Math.round(total)*(-1)) + ")");
				   }else{ 							
					   cell.setValue(formatter.format(Math.round(total)));
				   } 
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - total);
			   }
			   
			   
		   }

		   cell = cells.getCell("A316");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INTEREST INCOME (51501)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Interest_Income_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(315, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(data[i]))) + ")");

				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue("(" + formatter.format(Math.round(total)) + ")");
			   }

		   }

		   cell = cells.getCell("A317");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "GAIN ON EXCHANGE RESERVE (51502)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Gain_Exchange_Reserve_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(316, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(data[i])*(-1))) + ")");

				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue("(" + formatter.format(Math.round(total)) + ")");
			   }

		   }

		   cell = cells.getCell("A318");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "LOAN INTEREST (63501)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_LoanInterest_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(317, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A319");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "LOSS ON EXCHANGE RESERVE (63502)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Loss_Exchange_Reserve_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(318, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A321");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "OTHER ACTIVITY (HOAT DONG KHAC)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_OtherActivity_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(320, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   if(Double.parseDouble(data[i]) < 0){ 		
					   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(data[i])*(-1))) + ")");
				   }else{ 							
					   cell.setValue(formatter.format(Math.round(Double.parseDouble(data[i]))));
				   } 
				   
				   total = total + Double.parseDouble(data[i]);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(data[i]));
			   }else{			   
				   if(total < 0){ 		
					   cell.setValue("(" + formatter.format(Math.round(total)*(-1)) + ")");
				   }else{ 							
					   cell.setValue(formatter.format(Math.round(total)));
				   } 
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - total);
			   }
		   }

		   cell = cells.getCell("A322");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OTHER INCOME(711)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Other_Income_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(321, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(data[i]))) + ")");

				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue("(" + formatter.format(Math.round(total)) + ")");
			   }

		   }
		   
		   cell = cells.getCell("A323");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OTHER EXPENSE (811)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_OtherExpenses_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(322, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }


		   cell = cells.getCell("A327");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, " Net Profit before tax");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(326, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(Double.parseDouble(NETPROFITBTAX[i]) < 0){ 		
				   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(NETPROFITBTAX[i])*(-1))) + ")");
			   }else{ 							
				   cell.setValue(formatter.format(Math.round(Double.parseDouble(NETPROFITBTAX[i]))));
			   } 
			   
		   }
		   
		   cell = cells.getCell("A328");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, " ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(327, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   
			   if(Double.parseDouble(SALES[i]) > 0){
				   cell.setValue(Math.round(100*Double.parseDouble(NETPROFITBTAX[i])/Double.parseDouble(SALES[i])) + "%");
			   }else{
				   cell.setValue("-");
			   }
		   }

		   cell = cells.getCell("A331");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, "CURRENT INCOME TAX EXPENSE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(330, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue(7.5*Double.parseDouble(NETPROFITBTAX[i])/100);
		   }

		   cell = cells.getCell("A333");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, " Net profit after tax");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(332, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue((100 - 7.5)*Double.parseDouble(NETPROFITBTAX[i])/100);
		   }

		   cell = cells.getCell("A334");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, " ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(333, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(Double.parseDouble(SALES[i]) > 0){
				   cell.setValue(Math.round((100-7.5)*Double.parseDouble(NETPROFITBTAX[i])/(100*Double.parseDouble(SALES[i]))) + "%");
			   }else{
				   cell.setValue("-");
			   }
		   }
		   
		   cell = cells.getCell("A336");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, "Unrealised exchange");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(335, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");
		   }

		   cell = cells.getCell("A337");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, " OTHER ADJUSTMENT");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(336, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");
		   }

		   cell = cells.getCell("A338");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, "Y-T-D Net profit after adj.");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(337, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue((100 - 7.5)*Double.parseDouble(NETPROFITBTAX[i])/100);
		   }
			   
		  }catch(Exception ex){
				throw new Exception("Khong the tao duoc tua de cho bao cao");
			}
	}

	private void XuatFileExcel_PLPaperCore(ILapngansach bean, Worksheet worksheet) throws Exception{
		try {
			NumberFormat formatter = new DecimalFormat("#,###,###.##");
			String[] COGS = new String[13];
			String[] SALES = new String[13];
			String[] NETPROFITBTAX = new String[13];
			for(int i = 0; i <= 12; i++){
				SALES[i] = "0";
				COGS[i]  = "0";
				NETPROFITBTAX[i] = "0";
			}			
			
			Cell cell;
			Style style;
			Font font;


			Cells cells = worksheet.getCells();	    
			cells.setRowHeight(0, 15.75f);
			cells.setColumnWidth(0, 51.63f);
			cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "NEW TOYO ALUMINIUM PAPER PACKAGING CO., LTD");
		   
			cell = cells.getCell("A2");
			ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Profit and Loss Account" );
		   
			cell = cells.getCell("A4");		   
			ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PARTICULAR");		   		   
			cells.merge(3, 0 , 4, 0);
			
			style = cell.getStyle();
			style.setHAlignment(TextAlignmentType.CENTER);
			style.setPatternStyle(PatternType.SOLID);
			style.setBorderColor(1, Color.BLACK);
			style.setBorderLine(BorderType.TOP, BorderLineType.DOUBLE);
			cell.setStyle(style);
			
			cell = cells.getCell("A5");
			style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			cell.setStyle(style);
		   
			for (int i = 1; i <= 13; i++ ){
			   cells.setColumnWidth(i, 22.38f);
			   cell = cells.getCell(3, i);
			   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Budget" );
			   style = cell.getStyle();
			   style.setHAlignment(TextAlignmentType.CENTER);		   
			   style.setColor(new Color(204, 255, 204));
			   style.setBorderColor(1, Color.BLACK);
			   style.setPatternStyle(PatternType.SOLID);
			   style.setBorderLine(BorderType.TOP, BorderLineType.DOUBLE);
			   style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
			   style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
			   cell.setStyle(style);
			   
			   cell = cells.getCell(4, i);
			   if(i == 13)
				   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, this.getDate(("" + i), ("" + 2014)) );
			   else
				   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Total") ;
			   
			   style = cell.getStyle();
			   style.setHAlignment(TextAlignmentType.CENTER);
			   style.setColor(new Color(204, 255, 204));
			   style.setPatternStyle(PatternType.SOLID);
			   style.setBorderLine(BorderType.TOP, BorderLineType.DOUBLE);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);			   
			   style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
			   cell.setStyle(style);
			   
		   }

		   cell = cells.getCell("A8");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Paper Core/Cone Sales Volume (Kg)");		   		   
		   cells.merge(3, 0 , 4, 0);
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   String[] data = bean.PL_SalesVolumn_PA();
		   double total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(7, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);
			   cell.setStyle(style);
			   
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }
		   
		   String[][] tmp2 = bean.PL_SalesVolumn_PA_Details();
		   int num = 8;
		   for(int j = 0; j < tmp2.length; j++){				
			   	total = 0;
				   
				cells.setColumnWidth(1, 22.38f);
				cell = cells.getCell(num, 0);
				style = cell.getStyle();
				   
				style.setHAlignment(TextAlignmentType.LEFT);
//				style.setNumber(3);
				font = style.getFont();
				font.setBold(false);
				font.setSize(12);
				font.setColor(Color.BLACK);
				style.setFont(font);
//				style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);
				cell.setStyle(style);

				cell.setValue(tmp2[j][0]);

				for(int i = 1; i <= 13; i++){														
					cells.setColumnWidth(i + 1, 22.38f);
					cell = cells.getCell(num, i );
					style = cell.getStyle();
					   
					style.setHAlignment(TextAlignmentType.RIGHT);
					style.setNumber(3);
					font = style.getFont();
					font.setBold(false);
					font.setSize(12);
					font.setColor(Color.BLACK);
					style.setFont(font);
					style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);
					cell.setStyle(style);

					if(i < 13){
					   cell.setValue(Double.parseDouble(tmp2[j][i]));
					   total = total + Double.parseDouble(tmp2[j][i]);
					}else{			   
					   cell.setValue(total);
					}			
				}							
				num++;

			   
		   }


		   cell = cells.getCell("A16");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TOTAL SALES AMOUNT (VND)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(3);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);

		   data = bean.PL_SalesAmount_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(15, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);
			   cell.setStyle(style);
			   
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }
		   
		   tmp2 = bean.PL_SalesAmount_PA_Details();
		   num = 16;
		   for(int j = 0; j < tmp2.length; j++){				
			   total = 0;			
			   cells.setColumnWidth(1, 22.38f);
			   cell = cells.getCell(num, 0);
			   style = cell.getStyle();
				   
			   style.setHAlignment(TextAlignmentType.LEFT);
//				style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//				style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);
			   cell.setStyle(style);

			   cell.setValue(tmp2[j][0]);

			   for(int i = 1; i <= 13; i++){														
					cells.setColumnWidth(i + 1, 22.38f);
					cell = cells.getCell(num, i );
					style = cell.getStyle();
					   
					style.setHAlignment(TextAlignmentType.RIGHT);
					style.setNumber(3);
					font = style.getFont();
					font.setBold(false);
					font.setSize(12);
					font.setColor(Color.BLACK);
					style.setFont(font);
					style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);
					cell.setStyle(style);

					if(i < 13){
					   cell.setValue(Double.parseDouble(tmp2[j][i]));
					   total = total + Double.parseDouble(tmp2[j][i]);
					}else{			   
					   cell.setValue(total);
					}			
				}							
				num++;
		   }

		   cell = cells.getCell("A24");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "SALES AMOUNT (NET OF TAX)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(3);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);

		   data = bean.PL_SalesAmount_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(23, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   SALES[i] = data[i];
			   }else{			   
				   cell.setValue(total);
				   SALES[i] = "" + total;
			   }
			   
		   }
		   
		   cell = cells.getCell("A25");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Paper core/cone");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_SalesAmount_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(24, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   
			   }else{			   
				   cell.setValue(total);
				   
			   }
			   
		   }

		   cell = cells.getCell("A26");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Sales return/allowance");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
//		   data = bean.PL_SalesAmount();
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(25, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");
		   }

		   cell = cells.getCell("A27");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Others");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
//		   data = bean.PL_SalesAmount();
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(26, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");
		   }

		   cell = cells.getCell("A29");		   
		   ReportAPI.getCellStyle(cell,Color.MAGENTA, true, 12, "MATERIALS CONSUMED (Paper core)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(28, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   COGS[i] = data[i];
			   }else{			   
				   cell.setValue(total);
				   COGS[i] = "" + total;
			   }
			   
		   }

		   cell = cells.getCell("A28");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, false, 12, " ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
//		   data = bean.PL_MaterialConsume();
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(27, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   
			   cell.setStyle(style);	
			   if(Double.parseDouble(SALES[i]) != 0)
				   cell.setValue(formatter.format(100*Double.parseDouble(COGS[i])/ Double.parseDouble(SALES[i])) + "%");
			   else
				   cell.setValue("-");
		   }

		   cell = cells.getCell("A30");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PAPER CONSUMED");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume_Paper_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(29, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);			   
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A31");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, "FOIL CONSUMED");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume_Foil_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(30	, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A32");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, "GLUE CONSUMED");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume_Glue_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(31, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A33");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, "LACQUER CONSUMED");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume_Lacquer_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   style = cell.getStyle();
			   cell = cells.getCell(32, i + 1);
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A34");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "SUB-MATERIAL CONSUMED");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume_Submaterial_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(33, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A36");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "DIRECT LABOUR(LUONG TRUC TIEP)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_DirectLabour_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(35, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   COGS[i] = "" + (Double.parseDouble(COGS[i]) + Double.parseDouble(data[i]));				   
			   }else{			   
				   cell.setValue(total);
				   COGS[i] = "" + (Double.parseDouble(COGS[i]) + total);
			   }
			   
		   }

		   cell = cells.getCell("A37");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "WORKERS' SALARY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_WorkerSalary_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(36, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }
		   
		   cell = cells.getCell("A38");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OVERTIME FOR WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_OvertimeWorkers_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(37, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A39");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "AMORTIZED 13 TH SALARY OF WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_AmortizedWorkers_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(38, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A40");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "SOCIAL INSURANCE+UNION FEE (WORKERS)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_SocialInsurance_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(39, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A41");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "HEALTH INSURANCE (WORKERS)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_HealthInsurance_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(40, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A43");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "PRODUCTION OVERHEAD(CPHI SAN XUAT)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_ProductionOverhead_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(42, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   COGS[i] = "" + (Double.parseDouble(COGS[i]) + Double.parseDouble(data[i]));
			   }else{			   
				   cell.setValue(total);
				   COGS[i] = "" + (Double.parseDouble(COGS[i]) + total);
			   }
			   
		   }

		   cell = cells.getCell("A44");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "ALLOWANCES FOR WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_WorkerAllowances_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(43, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }
		   
		   cell = cells.getCell("A45");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "BONUS FOR WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_WorkerBonus_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(44, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A46");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION OF BUILDING");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_DepreciationBuilding_Production_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(45, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A47");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION MACHINE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_DepreciationMachine_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(46, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A48");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION MOTOR VEHICLE FOR FACTORY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_DepreciationMotorVehicle_Production_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(47, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A49");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "RENTAL CHARGE OF MACHINERY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_RentalCharge_Machinery_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(48, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A50");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "ELECTRICITY+WATER(FACT.)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_ElectricityWater_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(49, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A51");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "FACTORY EXPENSES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_FactoryExpenses_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(50, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A52");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PRINTING & STATIONERY (FACTORY)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Printing_Stationery_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(51, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A53");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "MEALS FOR WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Workers_Meals_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(52, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }


		   }

		   cell = cells.getCell("A54");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UPKEEP OF MOTORVEHICLE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Upkeep_Motorvehicle_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(53, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A55");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UPKEEP OF MACHINERY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Upkeep_Machinery_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(54, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A56");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TRAINING COST");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_TrainingCost_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(55, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A57");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "WELFARE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Welfare_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(56, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A58");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INSURANCE A");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_InsuranceA_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(57, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A59");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UPKEEP OF FACTORY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_UpkeepFactory_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(58, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A60");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UNIFORM FOR WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Workers_Uniform_PA() ;
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(59, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A61");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "LAND RENTAL");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_LandRental_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(60, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A62");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TOOL & ACCESSORIES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Tool_Accessories_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(61, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A63");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OIL FOR MACHINE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Oil_Machine_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(62, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A64");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INDIRECT LABOUR SALARY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_IndirectLabourSalary_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(63, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }


		   }

		   cell = cells.getCell("A65");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INDIRECT LABOUR 13TH SALARY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Indirect_13th_LabourSalary_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(64, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A66");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INDIRECT LABOUR BONUS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Indirect_LabourBonus_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(65, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }
		   
		   cell = cells.getCell("A69");		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "Finished Goods purchased from other companies");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(68, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");

		   }

		   cell = cells.getCell("A70");		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "W.I.P Opening");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(69, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");

		   }

		   cell = cells.getCell("A71");		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "W.I.P Closing");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(70, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");

		   }

		   cell = cells.getCell("A72");		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "Finished goods Opening");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(71, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");

		   }

		   cell = cells.getCell("A73");		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "Finished goods Closing");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(72, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");

		   }
		   
		   cell = cells.getCell("A75");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "COST OF GOODS SOLD(Paper core)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(74, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue(Double.parseDouble(COGS[i]));
		   }

		   cell = cells.getCell("A76");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, "Gross profit");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(75, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   NETPROFITBTAX[i] = "" + (Double.parseDouble(SALES[i]) - Double.parseDouble(COGS[i]));
			   
			   if(Double.parseDouble(NETPROFITBTAX[i]) < 0){ 		
				   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(NETPROFITBTAX[i])*(-1))) + ")");
			   }else{ 							
				   cell.setValue(formatter.format(Math.round(Double.parseDouble(NETPROFITBTAX[i]))));
			   } 
			   
		   }

		   cell = cells.getCell("A77");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, false, 12, " ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
//		   data = bean.PL_MaterialConsume();
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(76, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   
			   cell.setStyle(style);			   			   
			   if( Double.parseDouble(SALES[i]) != 0)
				   cell.setValue(formatter.format(100*(Double.parseDouble(SALES[i]) - Double.parseDouble(COGS[i]))/ Double.parseDouble(SALES[i])) + "%");
			   else
				   cell.setValue("-");
		   }
		   
		   cell = cells.getCell("A79");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "SELLING EXPENSES(CHI PHI BAN HANG)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_SellingExpenses_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(78, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(data[i]));
			   }else{			   
				   cell.setValue(total);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - total);
			   }
			   			   
		   }

		   cell = cells.getCell("A80");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TRANSPORTATION");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Transportation_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(79, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A81");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "COMMISSION");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Commission_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(80, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A82");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PROMOTION EXPENSE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_PromotionExpenses_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(81, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A84");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "ADMINISTRATIVE OVERHEAD (CHI PHI QUAN LY)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_AdminOverhead_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(83, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(data[i]));
			   }else{			   
				   cell.setValue(total);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - total);
			   }
			   
		   }

		   cell = cells.getCell("A85");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "ALLOWANCES FOR STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Staff_Allowances_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(84, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A86");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "BANK CHARGES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_BankCharges_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(85, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A87");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "BONUS FOR STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_StaffsBonus_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(86, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A88");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION OF BUILDING");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Depreciation_Building_Office_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(87, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A89");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION OF OFFICE EQUIPMENT");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Depreciation_Equipment_Office_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(88, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A90");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION MOTOR VEHICLE FOR OFFICE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Depreciation_Motor_Office_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(89, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A91");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PHONE+FAX +ELECTRICITY CHARGE FOR OFFICE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Phone_Fax_Electricity_Office_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(90, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A92");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PUBLIC RELATION EXPENSES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_PublicRelations_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(91, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }
		   
		   cell = cells.getCell("A93");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "ENTERTAINMENT");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Entertainments_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(92, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A94");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OFFICE EXPENSES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_OfficeExpenses_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(93, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A95");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "HANDLING CHARGE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_HandlingCharges_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(94, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A96");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PRINTING & STATIONERY (OFFICE)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Printing_Stationery_Office_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(95, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A97");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TRAINING FEE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_TrainingFees_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(96, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A98");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TRAVELLING");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Trevelling_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(97, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A99");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UPKEEP OF MOTOR VEHICLE (OFFICE)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Upkeep_Motor_Vehicle_Office_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(98, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A100");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "AUDITING FEE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_AuditingFees_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(99, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A101");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INSURANCE (office)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Insurance_Office_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(100, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A102");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UNIFORM FOR STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_StaffUniform_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(101, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A103");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "LAND RENTAL");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_LandRental_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(102, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }
		   
		   cell = cells.getCell("A104");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "R & D EXPENSES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_RD_Expenses_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(103, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A105");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "MANAGEMENT FEE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_ManagementFees_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(104, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A107");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "ADMIN. SALARY(LUONG QUAN LY)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_AdminSalary_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(106, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(data[i]));				   
			   }else{			   
				   cell.setValue(total);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - total);
			   }

		   }

		   cell = cells.getCell("A108");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "STAFFS SALARY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Staff_Allowances_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(107, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }


		   cell = cells.getCell("A109");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OVERTIME FOR STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_StaffOvertime_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(108, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A110");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "AMORTIZED 13th SALARY OF STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Staff_Amortized_13th_Salary_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(109, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }
		   
		   cell = cells.getCell("A111");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "SOCIAL INSURANCE OF STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Staff_SocialInsurance_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(110, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A112");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "HEALTH INSURANCE (STAFFS)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Staff_HealthInsurance_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(111, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A114");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "FINANCIAL ACTIVITY(HOAT DONG TAI CHINH)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Financial_Activity_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(113, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   if(Double.parseDouble(data[i]) < 0){ 		
					   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(data[i])*(-1))) + ")");
				   }else{ 							
					   cell.setValue(formatter.format(Math.round(Double.parseDouble(data[i]))));
				   } 
				   
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(data[i]));

				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   if(total < 0){ 
					   cell.setValue("(" + formatter.format(Math.round(total)*(-1)) + ")");
				   }else{ 							
					   cell.setValue(formatter.format(Math.round(total)));
				   } 
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - total);
			   }
			   
			   
		   }

		   cell = cells.getCell("A115");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INTEREST INCOME (51501)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Interest_Income_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(114, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(data[i]))) + ")");

				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue("(" + formatter.format(Math.round(total)) + ")");
			   }

		   }

		   cell = cells.getCell("A116");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "GAIN ON EXCHANGE RESERVE (51502)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Gain_Exchange_Reserve_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(115, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(data[i])*(-1))) + ")");

				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue("(" + formatter.format(Math.round(total)) + ")");
			   }

		   }

		   cell = cells.getCell("A117");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "LOAN INTEREST (63501)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_LoanInterest_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(116, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A118");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "LOSS ON EXCHANGE RESERVE (63502)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Loss_Exchange_Reserve_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(117, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A120");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "OTHER ACTIVITY (HOAT DONG KHAC)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_OtherActivity_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(119, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   if(Double.parseDouble(data[i]) < 0){ 		
					   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(data[i])*(-1))) + ")");
				   }else{ 							
					   cell.setValue(formatter.format(Math.round(Double.parseDouble(data[i]))));
				   } 
				   
				   total = total + Double.parseDouble(data[i]);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(data[i]));
			   }else{			   
				   if(total < 0){ 		
					   cell.setValue("(" + formatter.format(Math.round(total)*(-1)) + ")");
				   }else{ 							
					   cell.setValue(formatter.format(Math.round(total)));
				   } 
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - total);
			   }
		   }

		   cell = cells.getCell("A121");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OTHER INCOME(711)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Other_Income_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(120, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(data[i])*(-1))) + ")");

				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue("(" + formatter.format(Math.round(total)*(-1)) + ")");
			   }

		   }
		   
		   cell = cells.getCell("A122");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OTHER EXPENSE (811)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_OtherExpenses_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(121, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }


		   cell = cells.getCell("A126");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, " Net Profit before tax");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(125, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(Double.parseDouble(NETPROFITBTAX[i]) < 0){ 		
				   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(NETPROFITBTAX[i])*(-1))) + ")");
			   }else{ 							
				   cell.setValue(formatter.format(Math.round(Double.parseDouble(NETPROFITBTAX[i]))));
			   } 
			   
		   }
		   
		   cell = cells.getCell("A127");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, " ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(126, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   
			   if(Double.parseDouble(SALES[i]) > 0){
				   cell.setValue(Math.round(100*Double.parseDouble(NETPROFITBTAX[i])/Double.parseDouble(SALES[i])) + "%");
			   }else{
				   cell.setValue("-");
			   }
		   }

		   cell = cells.getCell("A130");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, "CURRENT INCOME TAX EXPENSE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(129, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue(7.5*Double.parseDouble(NETPROFITBTAX[i])/100);
		   }

		   cell = cells.getCell("A132");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, " Net profit after tax");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(131, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue((100 - 7.5)*Double.parseDouble(NETPROFITBTAX[i])/100);
		   }

		   cell = cells.getCell("A133");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, " ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(132, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(Double.parseDouble(SALES[i]) > 0){
				   cell.setValue(Math.round((100-7.5)*Double.parseDouble(NETPROFITBTAX[i])/(100*Double.parseDouble(SALES[i]))) + "%");
			   }else{
				   cell.setValue("-");
			   }
		   }
		   

		   cell = cells.getCell("A136");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, " OTHER ADJUSTMENT");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(135, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");
		   }
			   
		  }catch(Exception ex){
				throw new Exception("Khong the tao duoc tua de cho bao cao");
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
//		HttpSession session = request.getSession();
//		String userId = (String) session.getAttribute("userId");
//		String ctyId = (String)session.getAttribute("congtyId");
	}

	private String getDate(String month, String year){
		String y =  year.substring(2, year.length());
		int m = Integer.parseInt(month);
		if(m == 1) return "Jan-" + y;
		if(m == 2) return "Feb-" + y;
		if(m == 3) return "Mar-" + y;
		if(m == 4) return "Apr-" + y;
		if(m == 5) return "May-" + y;
		if(m == 6) return "Jun-" + y;
		if(m == 7) return "Jul-" + y;
		if(m == 8) return "Aug-" + y;
		if(m == 9) return "Sep-" + y;
		if(m == 10) return "Oct-" + y;
		if(m == 11) return "Nov-" + y;
		if(m == 12) return "Dec-" + y;
		return "";
	}
	public String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	

	private void XuatFileExcel_PLLamination2(ILapngansach bean, Worksheet worksheet) throws Exception{
		try {
			NumberFormat formatter = new DecimalFormat("#,###,###.##");
			String[] COGS = new String[13];
			String[] SALES = new String[13];
			String[] NETPROFITBTAX = new String[13];
			for(int i = 0; i <= 12; i++){
				SALES[i] = "0";
				COGS[i]  = "0";
				NETPROFITBTAX[i] = "0";
			}			
			
			Cell cell;
			Style style;
			Font font;

			Cells cells = worksheet.getCells();	    
			cells.setRowHeight(0, 15.75f);
			cells.setColumnWidth(0, 51.63f);
			cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "NEW TOYO ALUMINIUM PAPER PACKAGING CO., LTD");
		   
			cell = cells.getCell("A2");
			ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Profit and Loss Account" );
		   
			cell = cells.getCell("A4");		   
			ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PARTICULAR");		   		   
			cells.merge(3, 0 , 4, 0);
			
			style = cell.getStyle();
			style.setHAlignment(TextAlignmentType.CENTER);
			style.setPatternStyle(PatternType.SOLID);
			style.setBorderColor(1, Color.BLACK);
			style.setBorderLine(BorderType.TOP, BorderLineType.DOUBLE);
			cell.setStyle(style);
			
			cell = cells.getCell("A5");
			style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			cell.setStyle(style);
		   
			for (int i = 1; i <= 13; i++ ){
			   cells.setColumnWidth(i, 22.38f);
			   cell = cells.getCell(3, i);
			   if(i <= 12)
				   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, Integer.toString(i) + "-2014" );
			   else
				   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TOTAL" );
			   style = cell.getStyle();
			   style.setHAlignment(TextAlignmentType.CENTER);		   
			   style.setColor(new Color(204, 255, 204));
			   style.setBorderColor(1, Color.BLACK);
			   style.setPatternStyle(PatternType.SOLID);
			   style.setBorderLine(BorderType.TOP, BorderLineType.DOUBLE);
			   style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
			   style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
			   cell.setStyle(style);
			   
			   cell = cells.getCell(4, i);
			   if(i == 13)
				   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, this.getDate(("" + i), ("" + 2014)) );
			   else
				   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Total") ;
			   
			   style = cell.getStyle();
			   style.setHAlignment(TextAlignmentType.CENTER);
			   style.setColor(new Color(204, 255, 204));
			   style.setPatternStyle(PatternType.SOLID);
			   style.setBorderLine(BorderType.TOP, BorderLineType.DOUBLE);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);			   
			   style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
			   cell.setStyle(style);
			   
		   }

		   cell = cells.getCell("A7");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Lamination Sales Volume (Kg)");		   		   
		   cells.merge(3, 0 , 4, 0);
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   String[] data = bean.PL_SalesVolumn_LA();
		   double total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(6, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);
			   cell.setStyle(style);
			   
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }
		   
		   String[][] tmp2 = bean.PL_SalesVolumn_LA_Details();
		   int num = 7;
		   
		   for(int j = 0; j < tmp2.length; j++){				
			   	total = 0;
			   
				cells.setColumnWidth(1, 22.38f);
				cell = cells.getCell(num, 0);
				style = cell.getStyle();
				   
				style.setHAlignment(TextAlignmentType.LEFT);
//				style.setNumber(3);
				font = style.getFont();
				font.setBold(false);
				font.setSize(12);
				font.setColor(Color.BLACK);
				style.setFont(font);
//				style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);
				cell.setStyle(style);

				cell.setValue(tmp2[j][0]);

				for(int i = 1; i <= 13; i++){														
					cells.setColumnWidth(i + 1, 22.38f);
					cell = cells.getCell(num, i );
					style = cell.getStyle();
					   
					style.setHAlignment(TextAlignmentType.RIGHT);
					style.setNumber(3);
					font = style.getFont();
					font.setBold(false);
					font.setSize(12);
					font.setColor(Color.BLACK);
					style.setFont(font);
					style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);
					cell.setStyle(style);

					if(i < 13){
					   cell.setValue(Double.parseDouble(tmp2[j][i]));
					   total = total + Double.parseDouble(tmp2[j][i]);
					}else{			   
					   cell.setValue(total);
					}			
				}							
				num++;
		   }


		   cell = cells.getCell("A117");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Lamination Sales Amount (Net of tax)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(3);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);

		   data = bean.PL_SalesAmount_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(116, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);
			   cell.setStyle(style);
			   
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }
		   
		   tmp2 = bean.PL_SalesAmount_LA_Details();
		   num = 117;
		   
		   for(int j = 0; j < tmp2.length; j++){				
			   total = 0;			
			   cells.setColumnWidth(1, 22.38f);
			   cell = cells.getCell(num, 0);
			   style = cell.getStyle();
				   
			   style.setHAlignment(TextAlignmentType.LEFT);
//				style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//				style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);
			   cell.setStyle(style);

			   cell.setValue(tmp2[j][0]);

			   for(int i = 1; i <= 13; i++){														
					cells.setColumnWidth(i + 1, 22.38f);
					cell = cells.getCell(num, i );
					style = cell.getStyle();
					   
					style.setHAlignment(TextAlignmentType.RIGHT);
					style.setNumber(3);
					font = style.getFont();
					font.setBold(false);
					font.setSize(12);
					font.setColor(Color.BLACK);
					style.setFont(font);
					style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);
					cell.setStyle(style);

					if(i < 13){
					   cell.setValue(Double.parseDouble(tmp2[j][i]));
					   total = total + Double.parseDouble(tmp2[j][i]);
					}else{			   
					   cell.setValue(total);
					}			
				}							
				num++;
		   }

		   cell = cells.getCell("A225");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "LAMINATION SALES AMOUNT (NET OF TAX)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(3);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);

		   data = bean.PL_SalesAmount_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(224, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   SALES[i] = data[i];
			   }else{			   
				   cell.setValue(total);
				   SALES[i] = "" + total;
			   }
			   
		   }
		   
		   cell = cells.getCell("A226");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Lamination");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_SalesAmount_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(225, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   
			   }else{			   
				   cell.setValue(total);
				   
			   }
			   
		   }

		   cell = cells.getCell("A227");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Sales return/allowance");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
//		   data = bean.PL_SalesAmount();
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(226, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");
		   }

		   cell = cells.getCell("A228");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Others");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
//		   data = bean.PL_SalesAmount();
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(227, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");
		   }

		   cell = cells.getCell("A230");		   
		   ReportAPI.getCellStyle(cell,Color.MAGENTA, true, 12, "MATERIALS CONSUMED ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(229, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   COGS[i] = data[i];
			   }else{			   
				   cell.setValue(total);
				   COGS[i] = "" + total;
			   }
			   
		   }

		   cell = cells.getCell("A229");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, false, 12, " ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
//		   data = bean.PL_MaterialConsume();
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(228, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   
			   cell.setStyle(style);	
			   if(Double.parseDouble(SALES[i]) != 0)
				   cell.setValue(formatter.format(100*Double.parseDouble(COGS[i])/ Double.parseDouble(SALES[i])) + "%");
			   else
				   cell.setValue("-");
		   }

		   cell = cells.getCell("A231");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PAPER CONSUMED");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume_Paper_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(230, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);			   
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A232");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, "FOIL CONSUMED");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume_Foil_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(231, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A233");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, "GLUE CONSUMED");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume_Glue_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(232, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A234");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, "LACQUER CONSUMED");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume_Lacquer_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(233, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A235");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "SUB-MATERIAL CONSUMED");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume_Submaterial_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(234, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A237");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "DIRECT LABOUR(LUONG TRUC TIEP)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_DirectLabour_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(236, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   COGS[i] = "" + (Double.parseDouble(COGS[i]) + Double.parseDouble(data[i]));				   
			   }else{			   
				   cell.setValue(total);
				   COGS[i] = "" + (Double.parseDouble(COGS[i]) + total);
			   }
			   
		   }

		   cell = cells.getCell("A238");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "WORKERS' SALARY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_WorkerSalary_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(237, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }
		   
		   cell = cells.getCell("A239");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OVERTIME FOR WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_OvertimeWorkers_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(238, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A240");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "AMORTIZED 13 TH SALARY OF WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_AmortizedWorkers_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(239, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A241");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "SOCIAL INSURANCE+UNION FEE (WORKERS)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_SocialInsurance_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(240, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A242");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "HEALTH INSURANCE (WORKERS)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_HealthInsurance_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(241, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A244");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "PRODUCTION OVERHEAD(CPHI SAN XUAT)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_ProductionOverhead_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(243, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   COGS[i] = "" + (Double.parseDouble(COGS[i]) + Double.parseDouble(data[i]));
			   }else{			   
				   cell.setValue(total);
				   COGS[i] = "" + (Double.parseDouble(COGS[i]) + total);
			   }
			   
		   }

		   cell = cells.getCell("A245");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "ALLOWANCES FOR WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_WorkerAllowances_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(244, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }
		   
		   cell = cells.getCell("A246");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "BONUS FOR WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_WorkerBonus_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(245, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A247");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION OF BUILDING");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_DepreciationBuilding_Production_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(246, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A248");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION MACHINE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_DepreciationMachine_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(247, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A249");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION MOTOR VEHICLE FOR FACTORY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_DepreciationMotorVehicle_Production_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(248, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A250");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "RENTAL CHARGE OF MACHINERY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_RentalCharge_Machinery_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(249, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A251");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "ELECTRICITY+WATER(FACT.)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_ElectricityWater_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(250, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A252");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "FACTORY EXPENSES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_FactoryExpenses_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(251, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A253");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PRINTING & STATIONERY (FACTORY)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Printing_Stationery_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(252, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A254");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "MEALS FOR WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Workers_Meals_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(253, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }


		   }

		   cell = cells.getCell("A255");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UPKEEP OF MOTORVEHICLE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Upkeep_Motorvehicle_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(254, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A256");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UPKEEP OF MACHINERY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Upkeep_Machinery_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(255, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A257");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TRAINING COST");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_TrainingCost_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(256, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A258");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "WELFARE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Welfare_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(257, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A259");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INSURANCE A");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_InsuranceA_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(258, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A260");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UPKEEP OF FACTORY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_UpkeepFactory_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(259, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A261");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UNIFORM FOR WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Workers_Uniform_LA() ;
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(260, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A262");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "LAND RENTAL");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_LandRental_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(261, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A263");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TOOL & ACCESSORIES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Tool_Accessories_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(262, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A264");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OIL FOR MACHINE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Oil_Machine_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(263, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A265");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INDIRECT LABOUR SALARY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_IndirectLabourSalary_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(264, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }


		   }

		   cell = cells.getCell("A266");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INDIRECT LABOUR 13TH SALARY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Indirect_13th_LabourSalary_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(265, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A267");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INDIRECT LABOUR BONUS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Indirect_LabourBonus_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(266, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }
		   
		   cell = cells.getCell("A270");		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "Goods purchased");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(269, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");

		   }

		   cell = cells.getCell("A271");		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "W.I.P Opening");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(270, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");

		   }

		   cell = cells.getCell("A272");		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "W.I.P Closing");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(271, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");

		   }

		   cell = cells.getCell("A273");		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "Finished goods Opening");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(272, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");

		   }

		   cell = cells.getCell("A274");		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "Finished goods Closing");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(273, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");

		   }
		   
		   cell = cells.getCell("A276");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "Cost of goods sold");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(275, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue(Double.parseDouble(COGS[i]));
		   }

		   cell = cells.getCell("A277");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, "Gross profit");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(276, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   NETPROFITBTAX[i] = "" + (Double.parseDouble(SALES[i]) - Double.parseDouble(COGS[i]));
			   
			   if(Double.parseDouble(NETPROFITBTAX[i]) < 0){ 		
				   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(NETPROFITBTAX[i])*(-1))) + ")");
			   }else{ 							
				   cell.setValue(formatter.format(Math.round(Double.parseDouble(NETPROFITBTAX[i]))));
			   } 
			   
		   }

		   cell = cells.getCell("A278");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, false, 12, " ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
//		   data = bean.PL_MaterialConsume();
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(277, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   
			   cell.setStyle(style);			   			   
			   if( Double.parseDouble(SALES[i]) != 0)
				   cell.setValue(formatter.format(100*(Double.parseDouble(SALES[i]) - Double.parseDouble(COGS[i]))/ Double.parseDouble(SALES[i])) + "%");
			   else
				   cell.setValue("-");
		   }
		   
		   cell = cells.getCell("A280");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "SELLING EXPENSES(CHI PHI BAN HANG)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_SellingExpenses_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(279, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(data[i]));
			   }else{			   
				   cell.setValue(total);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - total);
			   }
			   			   
		   }

		   cell = cells.getCell("A281");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TRANSPORTATION");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Transportation_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(280, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A282");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "COMMISSION /HADNLING CHARGES FOR EXPORT SALES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Commission_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(281, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A283");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PROMOTION EXPENSE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_PromotionExpenses_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(282, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A285");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "ADMINISTRATIVE OVERHEAD (CHI PHI QUAN LY)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_AdminOverhead_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(284, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(data[i]));
			   }else{			   
				   cell.setValue(total);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - total);
			   }
			   
		   }

		   cell = cells.getCell("A286");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "ALLOWANCES FOR STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Staff_Allowances_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(285, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A287");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "BANK CHARGES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_BankCharges_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(286, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A288");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "BONUS FOR STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_StaffsBonus_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(287, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A289");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION OF BUILDING");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Depreciation_Building_Office_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(288, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A290");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION OF OFFICE EQUIPMENT");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Depreciation_Equipment_Office_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(289, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A291");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION MOTOR VEHICLE FOR OFFICE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Depreciation_Motor_Office_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(290, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A292");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PHONE+FAX +ELECTRICITY CHARGE FOR OFFICE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Phone_Fax_Electricity_Office_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(291, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A293");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PUBLIC RELATION EXPENSES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_PublicRelations_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(292, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }
		   
		   cell = cells.getCell("A294");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "ENTERTAINMENT");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Entertainments_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(293, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A295");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OFFICE EXPENSES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_OfficeExpenses_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(294, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A296");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "HANDLING CHARGE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_HandlingCharges_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(295, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A297");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PRINTING & STATIONERY (OFFICE)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Printing_Stationery_Office_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(296, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A298");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TRAINING FEE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_TrainingFees_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(297, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A299");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TRAVELLING");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Trevelling_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(298, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A300");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UPKEEP OF MOTOR VEHICLE (OFFICE)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Upkeep_Motor_Vehicle_Office_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(299, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A301");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "AUDITING FEE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_AuditingFees_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(300, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A302");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INSURANCE (office)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Insurance_Office_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(301, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A303");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UNIFORM FOR STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_StaffUniform_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(302, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A304");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "LAND RENTAL");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_LandRental_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(303, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }
		   
		   cell = cells.getCell("A305");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "R & D EXPENSES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_RD_Expenses_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(304, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A306");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "MANAGEMENT FEE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_ManagementFees_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(305, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A308");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "ADMIN. SALARY(LUONG QUAN LY)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_AdminSalary_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(307, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(data[i]));				   
			   }else{			   
				   cell.setValue(total);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - total);
			   }

		   }

		   cell = cells.getCell("A309");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "STAFFS SALARY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Staff_Allowances_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(308, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }


		   cell = cells.getCell("A310");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OVERTIME FOR STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_StaffOvertime_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(309, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A311");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "AMORTIZED 13th SALARY OF STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Staff_Amortized_13th_Salary_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(310, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }
		   
		   cell = cells.getCell("A312");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "SOCIAL INSURANCE+UNION FEE OF STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Staff_SocialInsurance_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(311, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A313");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "HEALTH INSURANCE (STAFFS)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Staff_HealthInsurance_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(312, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A315");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "FINANCIAL ACTIVITY(HOAT DONG TAI CHINH)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Financial_Activity_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(314, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   if(Double.parseDouble(data[i]) < 0){ 		
					   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(data[i])*(-1))) + ")");
				   }else{ 							
					   cell.setValue(formatter.format(Math.round(Double.parseDouble(data[i]))));
				   } 
				   
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(data[i]));

				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   if(total < 0){ 
					   cell.setValue("(" + formatter.format(Math.round(total)*(-1)) + ")");
				   }else{ 							
					   cell.setValue(formatter.format(Math.round(total)));
				   } 
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - total);
			   }
			   
			   
		   }

		   cell = cells.getCell("A316");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INTEREST INCOME (51501)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Interest_Income_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(315, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(data[i]))) + ")");

				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue("(" + formatter.format(Math.round(total)) + ")");
			   }

		   }

		   cell = cells.getCell("A317");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "GAIN ON EXCHANGE RESERVE (51502)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Gain_Exchange_Reserve_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(316, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(data[i])*(-1))) + ")");

				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue("(" + formatter.format(Math.round(total)) + ")");
			   }

		   }

		   cell = cells.getCell("A318");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "LOAN INTEREST (63501)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_LoanInterest_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(317, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A319");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "LOSS ON EXCHANGE RESERVE (63502)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Loss_Exchange_Reserve_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(318, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A321");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "OTHER ACTIVITY (HOAT DONG KHAC)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_OtherActivity_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(320, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   if(Double.parseDouble(data[i]) < 0){ 		
					   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(data[i])*(-1))) + ")");
				   }else{ 							
					   cell.setValue(formatter.format(Math.round(Double.parseDouble(data[i]))));
				   } 
				   
				   total = total + Double.parseDouble(data[i]);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(data[i]));
			   }else{			   
				   if(total < 0){ 		
					   cell.setValue("(" + formatter.format(Math.round(total)*(-1)) + ")");
				   }else{ 							
					   cell.setValue(formatter.format(Math.round(total)));
				   } 
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - total);
			   }
		   }

		   cell = cells.getCell("A322");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OTHER INCOME(711)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Other_Income_LA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(321, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(data[i]))) + ")");

				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue("(" + formatter.format(Math.round(total)) + ")");
			   }

		   }
		   
		   cell = cells.getCell("A323");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OTHER EXPENSE (811)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_OtherExpenses_LA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(322, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }


		   cell = cells.getCell("A327");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, " Net Profit before tax");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(326, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(Double.parseDouble(NETPROFITBTAX[i]) < 0){ 		
				   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(NETPROFITBTAX[i])*(-1))) + ")");
			   }else{ 							
				   cell.setValue(formatter.format(Math.round(Double.parseDouble(NETPROFITBTAX[i]))));
			   } 
			   
		   }
		   
		   cell = cells.getCell("A328");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, " ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(327, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   
			   if(Double.parseDouble(SALES[i]) > 0){
				   cell.setValue(Math.round(100*Double.parseDouble(NETPROFITBTAX[i])/Double.parseDouble(SALES[i])) + "%");
			   }else{
				   cell.setValue("-");
			   }
		   }

		   cell = cells.getCell("A331");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, "CURRENT INCOME TAX EXPENSE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(330, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue(7.5*Double.parseDouble(NETPROFITBTAX[i])/100);
		   }

		   cell = cells.getCell("A333");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, " Net profit after tax");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(332, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue((100 - 7.5)*Double.parseDouble(NETPROFITBTAX[i])/100);
		   }

		   cell = cells.getCell("A334");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, " ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(333, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(Double.parseDouble(SALES[i]) > 0){
				   cell.setValue(Math.round((100-7.5)*Double.parseDouble(NETPROFITBTAX[i])/(100*Double.parseDouble(SALES[i]))) + "%");
			   }else{
				   cell.setValue("-");
			   }
		   }
		   
		   cell = cells.getCell("A336");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, "Unrealised exchange");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(335, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");
		   }

		   cell = cells.getCell("A337");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, " OTHER ADJUSTMENT");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(336, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");
		   }

		   cell = cells.getCell("A338");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, "Y-T-D Net profit after adj.");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(337, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue((100 - 7.5)*Double.parseDouble(NETPROFITBTAX[i])/100);
		   }
			   
		  }catch(Exception ex){
				throw new Exception("Khong the tao duoc tua de cho bao cao");
			}
	}


	private void XuatFileExcel_PLPaperCore2(ILapngansach bean, Worksheet worksheet) throws Exception{
		try {
			NumberFormat formatter = new DecimalFormat("#,###,###.##");
			String[] COGS = new String[13];
			String[] SALES = new String[13];
			String[] NETPROFITBTAX = new String[13];
			for(int i = 0; i <= 12; i++){
				SALES[i] = "0";
				COGS[i]  = "0";
				NETPROFITBTAX[i] = "0";
			}			
			
			Cell cell;
			Style style;
			Font font;


			Cells cells = worksheet.getCells();	    
			cells.setRowHeight(0, 15.75f);
			cells.setColumnWidth(0, 51.63f);
			cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "NEW TOYO ALUMINIUM PAPER PACKAGING CO., LTD");
		   
			cell = cells.getCell("A2");
			ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Profit and Loss Account" );
		   
			cell = cells.getCell("A4");		   
			ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PARTICULAR");		   		   
			cells.merge(3, 0 , 4, 0);
			
			style = cell.getStyle();
			style.setHAlignment(TextAlignmentType.CENTER);
			style.setPatternStyle(PatternType.SOLID);
			style.setBorderColor(1, Color.BLACK);
			style.setBorderLine(BorderType.TOP, BorderLineType.DOUBLE);
			cell.setStyle(style);
			
			cell = cells.getCell("A5");
			style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			cell.setStyle(style);
		   
			for (int i = 1; i <= 13; i++ ){
			   cells.setColumnWidth(i, 22.38f);
			   cell = cells.getCell(3, i);
			   if(i <= 12)
				   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, Integer.toString(i) + "-2014" );
			   else
				   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TOTAL" );
			   style = cell.getStyle();
			   style.setHAlignment(TextAlignmentType.CENTER);		   
			   style.setColor(new Color(204, 255, 204));
			   style.setBorderColor(1, Color.BLACK);
			   style.setPatternStyle(PatternType.SOLID);
			   style.setBorderLine(BorderType.TOP, BorderLineType.DOUBLE);
			   style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
			   style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
			   cell.setStyle(style);
			   
			   cell = cells.getCell(4, i);
			   if(i == 13)
				   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, this.getDate(("" + i), ("" + 2014)) );
			   else
				   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Total") ;
			   
			   style = cell.getStyle();
			   style.setHAlignment(TextAlignmentType.CENTER);
			   style.setColor(new Color(204, 255, 204));
			   style.setPatternStyle(PatternType.SOLID);
			   style.setBorderLine(BorderType.TOP, BorderLineType.DOUBLE);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);			   
			   style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
			   cell.setStyle(style);
			   
		   }

		   cell = cells.getCell("A8");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Paper Core/Cone Sales Volume (Kg)");		   		   
		   cells.merge(3, 0 , 4, 0);
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   String[] data = bean.PL_SalesVolumn_PA();
		   double total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(7, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);
			   cell.setStyle(style);
			   
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }
		   
		   String[][] tmp2 = bean.PL_SalesVolumn_PA_Details();
		   int num = 8;
		   for(int j = 0; j < tmp2.length; j++){				
			   	total = 0;
				   
				cells.setColumnWidth(1, 22.38f);
				cell = cells.getCell(num, 0);
				style = cell.getStyle();
				   
				style.setHAlignment(TextAlignmentType.LEFT);
//				style.setNumber(3);
				font = style.getFont();
				font.setBold(false);
				font.setSize(12);
				font.setColor(Color.BLACK);
				style.setFont(font);
//				style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);
				cell.setStyle(style);

				cell.setValue(tmp2[j][0]);

				for(int i = 1; i <= 13; i++){														
					cells.setColumnWidth(i + 1, 22.38f);
					cell = cells.getCell(num, i );
					style = cell.getStyle();
					   
					style.setHAlignment(TextAlignmentType.RIGHT);
					style.setNumber(3);
					font = style.getFont();
					font.setBold(false);
					font.setSize(12);
					font.setColor(Color.BLACK);
					style.setFont(font);
					style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);
					cell.setStyle(style);

					if(i < 13){
					   cell.setValue(Double.parseDouble(tmp2[j][i]));
					   total = total + Double.parseDouble(tmp2[j][i]);
					}else{			   
					   cell.setValue(total);
					}			
				}							
				num++;

			   
		   }


		   cell = cells.getCell("A16");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TOTAL SALES AMOUNT (VND)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(3);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);

		   data = bean.PL_SalesAmount_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(15, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);
			   cell.setStyle(style);
			   
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }
		   
		   tmp2 = bean.PL_SalesAmount_PA_Details();
		   num = 16;
		   for(int j = 0; j < tmp2.length; j++){				
			   total = 0;			
			   cells.setColumnWidth(1, 22.38f);
			   cell = cells.getCell(num, 0);
			   style = cell.getStyle();
				   
			   style.setHAlignment(TextAlignmentType.LEFT);
//				style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//				style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);
			   cell.setStyle(style);

			   cell.setValue(tmp2[j][0]);

			   for(int i = 1; i <= 13; i++){														
					cells.setColumnWidth(i + 1, 22.38f);
					cell = cells.getCell(num, i );
					style = cell.getStyle();
					   
					style.setHAlignment(TextAlignmentType.RIGHT);
					style.setNumber(3);
					font = style.getFont();
					font.setBold(false);
					font.setSize(12);
					font.setColor(Color.BLACK);
					style.setFont(font);
					style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);
					cell.setStyle(style);

					if(i < 13){
					   cell.setValue(Double.parseDouble(tmp2[j][i]));
					   total = total + Double.parseDouble(tmp2[j][i]);
					}else{			   
					   cell.setValue(total);
					}			
				}							
				num++;
		   }

		   cell = cells.getCell("A24");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "SALES AMOUNT (NET OF TAX)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(3);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);

		   data = bean.PL_SalesAmount_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(23, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   SALES[i] = data[i];
			   }else{			   
				   cell.setValue(total);
				   SALES[i] = "" + total;
			   }
			   
		   }
		   
		   cell = cells.getCell("A25");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Paper core/cone");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_SalesAmount_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(24, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   
			   }else{			   
				   cell.setValue(total);
				   
			   }
			   
		   }

		   cell = cells.getCell("A26");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Sales return/allowance");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
//		   data = bean.PL_SalesAmount();
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(25, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");
		   }

		   cell = cells.getCell("A27");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Others");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
//		   data = bean.PL_SalesAmount();
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(26, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");
		   }

		   cell = cells.getCell("A29");		   
		   ReportAPI.getCellStyle(cell,Color.MAGENTA, true, 12, "MATERIALS CONSUMED (Paper core)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(28, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   COGS[i] = data[i];
			   }else{			   
				   cell.setValue(total);
				   COGS[i] = "" + total;
			   }
			   
		   }

		   cell = cells.getCell("A28");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, false, 12, " ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
//		   data = bean.PL_MaterialConsume();
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(27, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   
			   cell.setStyle(style);	
			   if(Double.parseDouble(SALES[i]) != 0)
				   cell.setValue(formatter.format(100*Double.parseDouble(COGS[i])/ Double.parseDouble(SALES[i])) + "%");
			   else
				   cell.setValue("-");
		   }

		   cell = cells.getCell("A30");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PAPER CONSUMED");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume_Paper_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(29, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);			   
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A31");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, "FOIL CONSUMED");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume_Foil_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(30	, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A32");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, "GLUE CONSUMED");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume_Glue_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(31, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A33");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, "LACQUER CONSUMED");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume_Lacquer_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   style = cell.getStyle();
			   cell = cells.getCell(32, i + 1);
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A34");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "SUB-MATERIAL CONSUMED");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_MaterialConsume_Submaterial_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(33, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A36");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "DIRECT LABOUR(LUONG TRUC TIEP)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_DirectLabour_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(35, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   COGS[i] = "" + (Double.parseDouble(COGS[i]) + Double.parseDouble(data[i]));				   
			   }else{			   
				   cell.setValue(total);
				   COGS[i] = "" + (Double.parseDouble(COGS[i]) + total);
			   }
			   
		   }

		   cell = cells.getCell("A37");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "WORKERS' SALARY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_WorkerSalary_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(36, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }
		   
		   cell = cells.getCell("A38");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OVERTIME FOR WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_OvertimeWorkers_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(37, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A39");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "AMORTIZED 13 TH SALARY OF WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_AmortizedWorkers_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(38, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A40");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "SOCIAL INSURANCE+UNION FEE (WORKERS)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_SocialInsurance_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(39, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A41");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "HEALTH INSURANCE (WORKERS)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_HealthInsurance_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(40, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A43");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "PRODUCTION OVERHEAD(CPHI SAN XUAT)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_ProductionOverhead_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(42, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   COGS[i] = "" + (Double.parseDouble(COGS[i]) + Double.parseDouble(data[i]));
			   }else{			   
				   cell.setValue(total);
				   COGS[i] = "" + (Double.parseDouble(COGS[i]) + total);
			   }
			   
		   }

		   cell = cells.getCell("A44");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "ALLOWANCES FOR WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_WorkerAllowances_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(43, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }
		   
		   cell = cells.getCell("A45");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "BONUS FOR WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_WorkerBonus_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(44, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A46");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION OF BUILDING");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_DepreciationBuilding_Production_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(45, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A47");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION MACHINE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_DepreciationMachine_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(46, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A48");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION MOTOR VEHICLE FOR FACTORY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_DepreciationMotorVehicle_Production_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(47, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A49");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "RENTAL CHARGE OF MACHINERY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_RentalCharge_Machinery_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(48, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A50");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "ELECTRICITY+WATER(FACT.)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_ElectricityWater_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(49, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A51");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "FACTORY EXPENSES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_FactoryExpenses_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(50, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A52");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PRINTING & STATIONERY (FACTORY)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Printing_Stationery_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(51, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A53");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "MEALS FOR WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Workers_Meals_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(52, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }


		   }

		   cell = cells.getCell("A54");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UPKEEP OF MOTORVEHICLE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Upkeep_Motorvehicle_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(53, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A55");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UPKEEP OF MACHINERY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Upkeep_Machinery_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(54, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A56");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TRAINING COST");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_TrainingCost_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(55, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A57");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "WELFARE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Welfare_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(56, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A58");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INSURANCE A");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_InsuranceA_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(57, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A59");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UPKEEP OF FACTORY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_UpkeepFactory_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(58, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A60");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UNIFORM FOR WORKERS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Workers_Uniform_PA() ;
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(59, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A61");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "LAND RENTAL");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_LandRental_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(60, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A62");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TOOL & ACCESSORIES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Tool_Accessories_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(61, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A63");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OIL FOR MACHINE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Oil_Machine_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(62, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A64");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INDIRECT LABOUR SALARY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_IndirectLabourSalary_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(63, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }


		   }

		   cell = cells.getCell("A65");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INDIRECT LABOUR 13TH SALARY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Indirect_13th_LabourSalary_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(64, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A66");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INDIRECT LABOUR BONUS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Indirect_LabourBonus_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(65, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }
		   
		   cell = cells.getCell("A69");		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "Finished Goods purchased from other companies");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(68, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");

		   }

		   cell = cells.getCell("A70");		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "W.I.P Opening");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(69, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");

		   }

		   cell = cells.getCell("A71");		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "W.I.P Closing");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(70, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");

		   }

		   cell = cells.getCell("A72");		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "Finished goods Opening");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(71, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");

		   }

		   cell = cells.getCell("A73");		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "Finished goods Closing");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(72, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");

		   }
		   
		   cell = cells.getCell("A75");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "COST OF GOODS SOLD(Paper core)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(74, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue(Double.parseDouble(COGS[i]));
		   }

		   cell = cells.getCell("A76");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, "Gross profit");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(75, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   NETPROFITBTAX[i] = "" + (Double.parseDouble(SALES[i]) - Double.parseDouble(COGS[i]));
			   
			   if(Double.parseDouble(NETPROFITBTAX[i]) < 0){ 		
				   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(NETPROFITBTAX[i])*(-1))) + ")");
			   }else{ 							
				   cell.setValue(formatter.format(Math.round(Double.parseDouble(NETPROFITBTAX[i]))));
			   } 
			   
		   }

		   cell = cells.getCell("A77");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, false, 12, " ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
//		   data = bean.PL_MaterialConsume();
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(76, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   
			   cell.setStyle(style);			   			   
			   if( Double.parseDouble(SALES[i]) != 0)
				   cell.setValue(formatter.format(100*(Double.parseDouble(SALES[i]) - Double.parseDouble(COGS[i]))/ Double.parseDouble(SALES[i])) + "%");
			   else
				   cell.setValue("-");
		   }
		   
		   cell = cells.getCell("A79");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "SELLING EXPENSES(CHI PHI BAN HANG)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_SellingExpenses_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(78, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(data[i]));
			   }else{			   
				   cell.setValue(total);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - total);
			   }
			   			   
		   }

		   cell = cells.getCell("A80");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TRANSPORTATION");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Transportation_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(79, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A81");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "COMMISSION");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Commission_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(80, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A82");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PROMOTION EXPENSE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_PromotionExpenses_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(81, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A84");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "ADMINISTRATIVE OVERHEAD (CHI PHI QUAN LY)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_AdminOverhead_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(83, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(data[i]));
			   }else{			   
				   cell.setValue(total);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - total);
			   }
			   
		   }

		   cell = cells.getCell("A85");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "ALLOWANCES FOR STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Staff_Allowances_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(84, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }
			   
		   }

		   cell = cells.getCell("A86");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "BANK CHARGES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_BankCharges_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(85, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A87");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "BONUS FOR STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_StaffsBonus_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(86, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A88");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION OF BUILDING");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Depreciation_Building_Office_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(87, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A89");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION OF OFFICE EQUIPMENT");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Depreciation_Equipment_Office_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(88, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A90");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "DEPRECIATION MOTOR VEHICLE FOR OFFICE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Depreciation_Motor_Office_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(89, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A91");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PHONE+FAX +ELECTRICITY CHARGE FOR OFFICE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Phone_Fax_Electricity_Office_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(90, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A92");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PUBLIC RELATION EXPENSES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_PublicRelations_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(91, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }
		   
		   cell = cells.getCell("A93");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "ENTERTAINMENT");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Entertainments_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(92, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A94");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OFFICE EXPENSES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_OfficeExpenses_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(93, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A95");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "HANDLING CHARGE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_HandlingCharges_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(94, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A96");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "PRINTING & STATIONERY (OFFICE)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Printing_Stationery_Office_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(95, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A97");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TRAINING FEE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_TrainingFees_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(96, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A98");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "TRAVELLING");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Trevelling_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(97, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A99");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UPKEEP OF MOTOR VEHICLE (OFFICE)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Upkeep_Motor_Vehicle_Office_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(98, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A100");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "AUDITING FEE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_AuditingFees_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(99, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A101");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INSURANCE (office)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Insurance_Office_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(100, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A102");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "UNIFORM FOR STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_StaffUniform_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(101, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A103");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "LAND RENTAL");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_LandRental_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(102, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }
		   
		   cell = cells.getCell("A104");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "R & D EXPENSES");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_RD_Expenses_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(103, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A105");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "MANAGEMENT FEE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_ManagementFees_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(104, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A107");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "ADMIN. SALARY(LUONG QUAN LY)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_AdminSalary_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(106, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(data[i]));				   
			   }else{			   
				   cell.setValue(total);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - total);
			   }

		   }

		   cell = cells.getCell("A108");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "STAFFS SALARY");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Staff_Allowances_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(107, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }


		   cell = cells.getCell("A109");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OVERTIME FOR STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_StaffOvertime_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(108, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A110");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "AMORTIZED 13th SALARY OF STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Staff_Amortized_13th_Salary_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(109, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }
		   
		   cell = cells.getCell("A111");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "SOCIAL INSURANCE OF STAFFS");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Staff_SocialInsurance_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(110, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A112");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "HEALTH INSURANCE (STAFFS)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Staff_HealthInsurance_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(111, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A114");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "FINANCIAL ACTIVITY(HOAT DONG TAI CHINH)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Financial_Activity_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(113, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   if(Double.parseDouble(data[i]) < 0){ 		
					   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(data[i])*(-1))) + ")");
				   }else{ 							
					   cell.setValue(formatter.format(Math.round(Double.parseDouble(data[i]))));
				   } 
				   
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(data[i]));

				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   if(total < 0){ 
					   cell.setValue("(" + formatter.format(Math.round(total)*(-1)) + ")");
				   }else{ 							
					   cell.setValue(formatter.format(Math.round(total)));
				   } 
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - total);
			   }
			   
			   
		   }

		   cell = cells.getCell("A115");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "INTEREST INCOME (51501)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Interest_Income_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(114, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(data[i]))) + ")");

				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue("(" + formatter.format(Math.round(total)) + ")");
			   }

		   }

		   cell = cells.getCell("A116");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "GAIN ON EXCHANGE RESERVE (51502)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Gain_Exchange_Reserve_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(115, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(data[i])*(-1))) + ")");

				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue("(" + formatter.format(Math.round(total)) + ")");
			   }

		   }

		   cell = cells.getCell("A117");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "LOAN INTEREST (63501)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_LoanInterest_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(116, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A118");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "LOSS ON EXCHANGE RESERVE (63502)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Loss_Exchange_Reserve_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(117, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }

		   cell = cells.getCell("A120");		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "OTHER ACTIVITY (HOAT DONG KHAC)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_OtherActivity_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(119, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(i < 12){
				   if(Double.parseDouble(data[i]) < 0){ 		
					   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(data[i])*(-1))) + ")");
				   }else{ 							
					   cell.setValue(formatter.format(Math.round(Double.parseDouble(data[i]))));
				   } 
				   
				   total = total + Double.parseDouble(data[i]);
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - Double.parseDouble(data[i]));
			   }else{			   
				   if(total < 0){ 		
					   cell.setValue("(" + formatter.format(Math.round(total)*(-1)) + ")");
				   }else{ 							
					   cell.setValue(formatter.format(Math.round(total)));
				   } 
				   NETPROFITBTAX[i] = "" + (Double.parseDouble(NETPROFITBTAX[i]) - total);
			   }
		   }

		   cell = cells.getCell("A121");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OTHER INCOME(711)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Other_Income_PA();
		   total = 0;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(120, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(data[i])*(-1))) + ")");

				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue("(" + formatter.format(Math.round(total)*(-1)) + ")");
			   }

		   }
		   
		   cell = cells.getCell("A122");		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "OTHER EXPENSE (811)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = bean.PL_OtherExpenses_PA();
		   total = 0;
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(121, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
				   total = total + Double.parseDouble(data[i]);
			   }else{			   
				   cell.setValue(total);
			   }

		   }


		   cell = cells.getCell("A126");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, " Net Profit before tax");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(125, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);

			   if(Double.parseDouble(NETPROFITBTAX[i]) < 0){ 		
				   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(NETPROFITBTAX[i])*(-1))) + ")");
			   }else{ 							
				   cell.setValue(formatter.format(Math.round(Double.parseDouble(NETPROFITBTAX[i]))));
			   } 
			   
		   }
		   
		   cell = cells.getCell("A127");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, " ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(126, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   
			   if(Double.parseDouble(SALES[i]) > 0){
				   cell.setValue(Math.round(100*Double.parseDouble(NETPROFITBTAX[i])/Double.parseDouble(SALES[i])) + "%");
			   }else{
				   cell.setValue("-");
			   }
		   }

		   cell = cells.getCell("A130");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, "CURRENT INCOME TAX EXPENSE");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(129, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue(7.5*Double.parseDouble(NETPROFITBTAX[i])/100);
		   }

		   cell = cells.getCell("A132");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, " Net profit after tax");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(131, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue((100 - 7.5)*Double.parseDouble(NETPROFITBTAX[i])/100);
		   }

		   cell = cells.getCell("A133");		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 12, " ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(132, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   if(Double.parseDouble(SALES[i]) > 0){
				   cell.setValue(Math.round((100-7.5)*Double.parseDouble(NETPROFITBTAX[i])/(100*Double.parseDouble(SALES[i]))) + "%");
			   }else{
				   cell.setValue("-");
			   }
		   }
		   

		   cell = cells.getCell("A136");		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, " OTHER ADJUSTMENT");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
//		   style.setIndent(1);
		   font = style.getFont();
//		   font.setUnderline(1);
		   style.setFont(font);
//		   style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   cells.setColumnWidth(i + 1, 22.38f);
			   cell = cells.getCell(135, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
//			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   cell.setValue("-");
		   }
			   
		  }catch(Exception ex){
				throw new Exception("Khong the tao duoc tua de cho bao cao");
		}
	}
	
	public String getDate() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	
}
