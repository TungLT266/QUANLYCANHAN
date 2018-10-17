package geso.traphaco.erp.servlets.lapngansach;

import geso.traphaco.erp.beans.lapngansach.imp.DuyetPLngansach;
import geso.traphaco.erp.beans.lapngansach.imp.Lapngansach;
import geso.traphaco.erp.beans.lapngansach.imp.LapngansachList;
import geso.traphaco.erp.beans.lapngansach.IDuyetPLngansach;
import geso.traphaco.erp.beans.lapngansach.ILapngansach;
import geso.traphaco.erp.beans.lapngansach.ILapngansachList;
import geso.traphaco.center.util.*;
import geso.traphaco.center.servlets.report.ReportAPI;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.PatternType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class DuyetngansachSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DuyetngansachSvl() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();
		String ctyId = (String)session.getAttribute("congtyId");
		
		String URL;
		Utility util = new Utility();
		
	    String userId;
		String querystring = request.getQueryString();
		userId = util.getUserId(querystring);

	    String Id = util.getId(querystring);
    	if (Id == null)
    		Id = "";
	    
    	String action = util.getAction(querystring);
    	if (action == null)
    		action = "";

    	ILapngansachList nsList = new LapngansachList();
		nsList.setCtyId(ctyId);
		
		nsList.setUserId(userId);

		if(action.equals("capnhat")){
			
			ILapngansach lnsBean = (ILapngansach) new Lapngansach();
			lnsBean.setCtyId(ctyId);
			lnsBean.setId(Id);
			lnsBean.init_duyetngansach();
			
			session.setAttribute("lnsBean", lnsBean);
			URL="../TraphacoHYERP/pages/Erp/Erp_DuyetNganSach_Hienthi.jsp";
			response.sendRedirect(URL);
			return;
			
		}else if(action.equals("duyet")){			
			nsList.Duyet(Id);
		}
    	
		
		nsList.init_duyetngansach();			
		
		session.setAttribute("nsList", nsList);
		URL="../TraphacoHYERP/pages/Erp/Erp_DuyetNganSach.jsp";
		response.sendRedirect(URL);


	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session=request.getSession();
		
		Utility util = new Utility();
		String URL;
		String ctyId = (String)session.getAttribute("congtyId");
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

	    String Id = util.antiSQLInspection(request.getParameter("Id"));
    	if (Id == null)
    		Id = "";
	    
    	String action = util.antiSQLInspection(request.getParameter("action"));
    	if (action == null)
    		action = "";
    	System.out.println("action: " + action);
    	
		ILapngansachList nsList = (ILapngansachList) new LapngansachList();
		
		nsList.setCtyId(ctyId);
		
		userId = util.antiSQLInspection(request.getParameter("userId"));
		nsList.setUserId(userId);

		String nam = util.antiSQLInspection(request.getParameter("year"));
		nsList.setNam(nam);
		
		String hieuluc = util.antiSQLInspection(request.getParameter("hieuluc"));
		if(hieuluc == null) hieuluc = "0";
		nsList.setHieuluc(hieuluc);
			
		if(action.equals("duyet")){
			nsList.Duyet(Id);
		}
		
		if(action.equals("save")){
			nsList.Hieuluc(Id);
		}
		
		if(action.equals("excel")){
	    	try
		    {
	    		
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename = BudgetApproval.xlsm");
				IDuyetPLngansach bean = (IDuyetPLngansach) new DuyetPLngansach();
				bean.setCtyId(ctyId);
				
				bean.setId(Id) ;
				
				String type = request.getParameter("type");
				if(type == null)
					type = "";

				bean.setMonth("12");
				bean.setYear(nam);
				bean.setUserId(userId);

				FileInputStream fstream = null;
				Workbook workbook = new Workbook();		
				
				fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\Duyetngansach.xlsm");		
		
				workbook.open(fstream);
				workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

				OutputStream out = response.getOutputStream();		   
				
				Worksheets worksheets = workbook.getWorksheets();
			   		
				Worksheet worksheet1 = worksheets.getSheet(0);

				worksheet1.setName("Duyet ngan sach");
				worksheet1.setZoom(70);

				this.XuatFileExcel(bean, worksheet1);
										
				workbook.save(out);			    
				fstream.close();

		    }
		    catch (Exception ex){ }
	    	
		    nsList.setUserId(userId);
		    nsList.setId(Id);
		    nsList.init();
	
			nsList.init_duyetngansach();			
			
			session.setAttribute("nsList", nsList);
			URL="../TraphacoHYERP/pages/Erp/Erp_DuyetNganSach.jsp";
			response.sendRedirect(URL);   		
			return;
		}
		
		nsList.init_duyetngansach(); 
					
		session.setAttribute("nsList", nsList);
		URL="../TraphacoHYERP/pages/Erp/Erp_DuyetNganSach.jsp";
		response.sendRedirect(URL);
	}
	
	private void XuatFileExcel(IDuyetPLngansach bean, Worksheet worksheet) throws Exception{
		// TODO Auto-generated method stub

		try {
			NumberFormat formatter = new DecimalFormat("#,###,###.##");
			
			Cell cell;
			Style style;
			Font font;

			Cells cells = worksheet.getCells();	    
			cells.setRowHeight(0, 25.0f);
			cells.setColumnWidth(0, 51.63f);
			cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell,Color.BLACK, true, 14, "CÔNG TY CỔ PHẦN TRAPHACO");
		   
			cell = cells.getCell(0, 12);
			ReportAPI.getCellStyle(cell,Color.BLACK, true, 10, "Tháng");
			style = cell.getStyle();
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			
			cell = cells.getCell("A2");
			ReportAPI.getCellStyle(cell,Color.BLACK, true, 16, "DUYỆT NGÂN SÁCH" );
		   
			cell = cells.getCell(1, 12);
			ReportAPI.getCellStyle(cell,Color.BLACK, true, 10, bean.getMonth() );
			style = cell.getStyle();
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);

			cell = cells.getCell("A4");		   
			ReportAPI.getCellStyle(cell,Color.BLACK, true, 16, "HẠNG MỤC");		   		   
			cells.merge(3, 0 , 4, 0);
			System.out.println("Year: " + bean.getYear());
			
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
			   
			   cells.setColumnWidth(i, 22.5f);
			   cell = cells.getCell(3, i);
			   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Ngân sách" );
		    
				   
			   
			   style = cell.getStyle();
			   style.setHAlignment(TextAlignmentType.CENTER);		   
				   //style.setColor(new Color(204, 255, 204));
			   style.setBorderColor(1, Color.BLACK);
			   style.setPatternStyle(PatternType.SOLID);
			   style.setBorderLine(BorderType.TOP, BorderLineType.DOUBLE);
			   style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
			   style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
	
				   
			   if(i < 13){
				   cell = cells.getCell(4, i);
				   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, this.getDate("" + i, bean.getYear()) );
			   }else{
				   cell = cells.getCell(4, i);
				   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Tổng cộng" );
			   }

			   style = cell.getStyle();
			   style.setHAlignment(TextAlignmentType.CENTER);		   
			   //style.setColor(new Color(204, 255, 204));
			   style.setBorderColor(1, Color.BLACK);
			   style.setPatternStyle(PatternType.SOLID);
			   style.setBorderLine(BorderType.TOP, BorderLineType.DOUBLE);
			   style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
			   style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
			   style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
			   cell.setStyle(style);
			   
		   }

		   int m = 7;
		   String[] data = null; 
// Vì hàm này bị vượt quá 65K dòng, nên phần 1 (trước "Goods purchased") được gom vào hàm setValue_Part_1(cells, cell, m, style)
		   
		   bean.setValue_Part_1_Budget(cells, cell, m, style);
		   
		   int row_Lamination_Sales_Volume = bean.getRow_SALES_VOLUME();
		   int row_TOTAL_AMOUNT = bean.getRow_TOTAL_AMOUNT();
		   int row_MATERIALS_CONSUMED_LA = bean.getRow_MATERIALS_CONSUMED_LA();
		   int row_DIRECT_LABOUR_LA = bean.getRow_DIRECT_LABOUR_LA();
		   int row_PRODUCTION_OVERHEAD_LA = bean.getRow_PRODUCTION_OVERHEAD_LA();
		   int row_SALES_AMOUNT_LA = bean.getRow_TOTAL_AMOUNT();

		   m = bean.getRow_LA();
		   m = m + 2;
		   int row_Goods_Purchased_LA = m;
		   
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "Hàng hóa mua");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);

			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   cell.setStyle(style);
			   cell.setValue("-");

		   }

		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "BTP Đầu kỳ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);

		   font = style.getFont();

		   style.setFont(font);
		   
		   cell.setStyle(style);
		   
		   data = bean.PL_WIP_Opening_LA();
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);

			   cell.setStyle(style);
			   cell.setValue(data[i]);
			   
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
			   }
		   }

		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "BTP Cuối kỳ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);

		   font = style.getFont();

		   style.setFont(font);
		   
		   cell.setStyle(style);
		   
		   data = bean.PL_WIP_Closing_LA();
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   cell.setStyle(style);
	
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }

		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "Thành phẩm đầu kỳ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);

		   font = style.getFont();

		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data = bean.PL_FinishGoods_Opening_LA();
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);

			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);

			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }

		   m++;
		   int row_Finish_Goods_Closing_LA = m;
		   
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLUE, true, 12, "Thành phẩm cuối kỳ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);

		   font = style.getFont();

		   style.setFont(font);
		   
		   cell.setStyle(style);
		   
		   data = bean.PL_FinishGoods_Closing_LA();
		   
		   for(int i = 0; i <= 12 ; i++){
   			   cell = cells.getCell(m-1, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);

			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }

		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "% Nguyên liệu/Doanh số");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   int row_RM_per_Sales_LA = m;
		   
		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "Chi phí nguyên vật liệu");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data = bean.PL_CostOfMaterial_LA();
		   
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);

			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }

// Điền dữ liệu vào dòng %RM/Sales		   
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(row_RM_per_Sales_LA-1, i + 1);

			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   cell.setStyle(style);

			   if(i < 12){
				   if (i == 0) cell.setFormula("=IF(B" + row_TOTAL_AMOUNT + " > 0, B" + m + "/B" + row_TOTAL_AMOUNT + ", 0)");
				   if (i == 1) cell.setFormula("=IF(C" + row_TOTAL_AMOUNT + " > 0, C" + m + "/C" + row_TOTAL_AMOUNT + ", 0)");
				   if (i == 2) cell.setFormula("=IF(D" + row_TOTAL_AMOUNT + " > 0, D" + m + "/D" + row_TOTAL_AMOUNT + ", 0)");
				   if (i == 3) cell.setFormula("=IF(E" + row_TOTAL_AMOUNT + " > 0, E" + m + "/E" + row_TOTAL_AMOUNT + ", 0)");
				   if (i == 4) cell.setFormula("=IF(F" + row_TOTAL_AMOUNT + " > 0, F" + m + "/F" + row_TOTAL_AMOUNT + ", 0)");
				   if (i == 5) cell.setFormula("=IF(G" + row_TOTAL_AMOUNT + " > 0, G" + m + "/G" + row_TOTAL_AMOUNT + ", 0)");
				   if (i == 6) cell.setFormula("=IF(H" + row_TOTAL_AMOUNT + " > 0, H" + m + "/H" + row_TOTAL_AMOUNT + ", 0)");
				   if (i == 7) cell.setFormula("=IF(I" + row_TOTAL_AMOUNT + " > 0, I" + m + "/I" + row_TOTAL_AMOUNT + ", 0)");
				   if (i == 8) cell.setFormula("=IF(J" + row_TOTAL_AMOUNT + " > 0, J" + m + "/J" + row_TOTAL_AMOUNT + ", 0)");
				   if (i == 9) cell.setFormula("=IF(K" + row_TOTAL_AMOUNT + " > 0, K" + m + "/K" + row_TOTAL_AMOUNT + ", 0)");
				   if (i == 10) cell.setFormula("=IF(L" + row_TOTAL_AMOUNT + " > 0, L" + m + "/L" + row_TOTAL_AMOUNT + ", 0)");
				   if (i == 11) cell.setFormula("=IF(M" + row_TOTAL_AMOUNT + " > 0, M" + m + "/M" + row_TOTAL_AMOUNT + ", 0)");
			   }else{
				   cell.setFormula("=IF(M" + row_TOTAL_AMOUNT + " > 0, M" + m + "/M" + row_TOTAL_AMOUNT + ", 0)");
			   }
		   }

		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "Chi phí sản xuất");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data = bean.PL_ManufacturingCost_LA();
		   
		   for(int i = 0; i <= 12; i++){
			   
   			   cell = cells.getCell(m-1, i + 1);

			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   cell.setStyle(style);
			   
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }
		   }

		   m++;
		   int row_Cost_of_goods_sold_LA = m;
		   
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "Giá vốn hàng bán");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);
			   style = cell.getStyle();
			   
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   cell.setStyle(style);
			   
			   if(i < 12){
				   if (i == 0) cell.setFormula("=SUM(B" + row_Goods_Purchased_LA + ":B" + row_Finish_Goods_Closing_LA + ")+B" + row_PRODUCTION_OVERHEAD_LA +  "+B" + row_MATERIALS_CONSUMED_LA);
				   if (i == 1) cell.setFormula("=SUM(C" + row_Goods_Purchased_LA + ":C" + row_Finish_Goods_Closing_LA + ")+C" + row_PRODUCTION_OVERHEAD_LA +  "+C" + row_MATERIALS_CONSUMED_LA);
				   if (i == 2) cell.setFormula("=SUM(D" + row_Goods_Purchased_LA + ":D" + row_Finish_Goods_Closing_LA + ")+D" + row_PRODUCTION_OVERHEAD_LA +  "+D" + row_MATERIALS_CONSUMED_LA);
				   if (i == 3) cell.setFormula("=SUM(E" + row_Goods_Purchased_LA + ":E" + row_Finish_Goods_Closing_LA + ")+E" + row_PRODUCTION_OVERHEAD_LA +  "+E" + row_MATERIALS_CONSUMED_LA);
				   if (i == 4) cell.setFormula("=SUM(F" + row_Goods_Purchased_LA + ":F" + row_Finish_Goods_Closing_LA + ")+F" + row_PRODUCTION_OVERHEAD_LA +  "+F" + row_MATERIALS_CONSUMED_LA);
				   if (i == 5) cell.setFormula("=SUM(G" + row_Goods_Purchased_LA + ":G" + row_Finish_Goods_Closing_LA + ")+G" + row_PRODUCTION_OVERHEAD_LA +  "+G" + row_MATERIALS_CONSUMED_LA);
				   if (i == 6) cell.setFormula("=SUM(H" + row_Goods_Purchased_LA + ":H" + row_Finish_Goods_Closing_LA + ")+H" + row_PRODUCTION_OVERHEAD_LA +  "+H" + row_MATERIALS_CONSUMED_LA);
				   if (i == 7) cell.setFormula("=SUM(I" + row_Goods_Purchased_LA + ":I" + row_Finish_Goods_Closing_LA + ")+I" + row_PRODUCTION_OVERHEAD_LA +  "+I" + row_MATERIALS_CONSUMED_LA);
				   if (i == 8) cell.setFormula("=SUM(J" + row_Goods_Purchased_LA + ":J" + row_Finish_Goods_Closing_LA + ")+J" + row_PRODUCTION_OVERHEAD_LA +  "+J" + row_MATERIALS_CONSUMED_LA);
				   if (i == 9) cell.setFormula("=SUM(K" + row_Goods_Purchased_LA + ":K" + row_Finish_Goods_Closing_LA + ")+K" + row_PRODUCTION_OVERHEAD_LA +  "+K" + row_MATERIALS_CONSUMED_LA);
				   if (i == 10) cell.setFormula("=SUM(L" + row_Goods_Purchased_LA + ":L" + row_Finish_Goods_Closing_LA + ")+L" + row_PRODUCTION_OVERHEAD_LA + "+L" + row_MATERIALS_CONSUMED_LA);
				   if (i == 11) cell.setFormula("=SUM(M" + row_Goods_Purchased_LA + ":M" + row_Finish_Goods_Closing_LA + ")+M" + row_PRODUCTION_OVERHEAD_LA +  "+M" + row_MATERIALS_CONSUMED_LA);
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

			
		   }
		  
		   m++;
		   int row_Gross_profit_LA = m;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 12, "Lợi nhuận gộp");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);

			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);

			   cell.setStyle(style);
			   if(i < 12){
				   if (i == 0) cell.setFormula("=B" + row_TOTAL_AMOUNT + "-B" + row_Cost_of_goods_sold_LA );
				   if (i == 1) cell.setFormula("=C" + row_TOTAL_AMOUNT + "-C" + row_Cost_of_goods_sold_LA);
				   if (i == 2) cell.setFormula("=D" + row_TOTAL_AMOUNT + "-D" + row_Cost_of_goods_sold_LA);
				   if (i == 3) cell.setFormula("=E" + row_TOTAL_AMOUNT + "-E" + row_Cost_of_goods_sold_LA);
				   if (i == 4) cell.setFormula("=F" + row_TOTAL_AMOUNT + "-F" + row_Cost_of_goods_sold_LA);
				   if (i == 5) cell.setFormula("=G" + row_TOTAL_AMOUNT + "-G" + row_Cost_of_goods_sold_LA);
				   if (i == 6) cell.setFormula("=H" + row_TOTAL_AMOUNT + "-H" + row_Cost_of_goods_sold_LA);
				   if (i == 7) cell.setFormula("=I" + row_TOTAL_AMOUNT + "-I" + row_Cost_of_goods_sold_LA);
				   if (i == 8) cell.setFormula("=J" + row_TOTAL_AMOUNT + "-J" + row_Cost_of_goods_sold_LA);
				   if (i == 9) cell.setFormula("=K" + row_TOTAL_AMOUNT + "-K" + row_Cost_of_goods_sold_LA);
				   if (i == 10) cell.setFormula("=L" + row_TOTAL_AMOUNT + "-L" + row_Cost_of_goods_sold_LA);
				   if (i == 11) cell.setFormula("=M" + row_TOTAL_AMOUNT + "-M" + row_Cost_of_goods_sold_LA);
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }
		   }

		   m = m + 3;
		   int row_SELLING_EXPENSES_LA = m;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "CHI PHÍ BÁN HÀNG");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);

		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);

		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Phòng Kinh doanh");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();

		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data = bean.PL_VPMB_Budget();
		   
		   for(int i = 0; i < 12; i++){
   			   cell = cells.getCell(m-1, i + 1);

			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);

			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
			   }

		   }

		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Các tỉnh miền bắc");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();

		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data = bean.PL_CTMB_Budget();
		   
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);

			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);

			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
			   }

		   }

		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "KD khu vực Hà Nội 1");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data = bean.PL_HN_Budget();
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);

			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }

		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Chi nhánh Nam Định");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data = bean.PL_ND_Budget();
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);

			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }

		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Chi nhánh Hải Phòng");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data = bean.PL_HP_Budget();
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);

			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }

		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Chi nhánh Thanh Hoá");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data = bean.PL_HN_Budget();
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);

			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }
		   
		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Chi nhánh Quảng Ninh");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data = bean.PL_HN_Budget();
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);

			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }		   
		   
		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Chi nhánh Hải Dương");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data = bean.PL_HN_Budget();
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);

			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }	
		   
		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Chi nhánh Phú Thọ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data = bean.PL_HN_Budget();
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);

			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }			   

		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Chi nhánh Bắc Giang");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data = bean.PL_HN_Budget();
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);

			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }			   
		   
		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Chi nhánh Hưng Yên");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data = bean.PL_HN_Budget();
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);

			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }			   

		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Chi nhánh Bắc Miền Trung");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data = bean.PL_HN_Budget();
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);

			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }			   

		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "VP chi nhánh MT & các tỉnh");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data = bean.PL_HN_Budget();
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);

			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }			
		   
		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Chi nhánh Khánh Hoà");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data = bean.PL_HN_Budget();
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);

			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }			
		   
		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Chi nhánh Quảng Ngãi");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data = bean.PL_HN_Budget();
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);

			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }			
		   
		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Chi nhánh Gia Lai");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data = bean.PL_HN_Budget();
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);

			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }		
		   
		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "VP chi nhánh HCM & các tỉnh");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data = bean.PL_HN_Budget();
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);

			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }		

		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Chi nhánh Đồng Nai");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data = bean.PL_HN_Budget();
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);

			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }		

		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Chi nhánh Bình Thuận");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data = bean.PL_HN_Budget();
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);

			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }		

		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Chi nhánh Vĩnh Long");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data = bean.PL_HN_Budget();
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);

			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }		
		   
		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Chi nhánh Cần Thơ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data = bean.PL_HN_Budget();
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);

			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }		

		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Chi nhánh Tiền Giang");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data = bean.PL_HN_Budget();
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);

			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }		

		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Chi nhánh Bình Dương");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data = bean.PL_HN_Budget();
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);

			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }		

		   for(int i = 0; i <= 12; i++){ 					
	   			cell = cells.getCell(row_SELLING_EXPENSES_LA-1, i + 1);
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
		   
		   
		   		if(i < 12 ){
		   			if (i == 0) cell.setFormula("=SUM(B" + (row_SELLING_EXPENSES_LA + 1) + ":B" + m + ")");
		   			if (i == 1) cell.setFormula("=SUM(C" + (row_SELLING_EXPENSES_LA + 1) + ":C" + m + ")");
		   			if (i == 2) cell.setFormula("=SUM(D" + (row_SELLING_EXPENSES_LA + 1) + ":D" + m + ")");
		   			if (i == 3) cell.setFormula("=SUM(E" + (row_SELLING_EXPENSES_LA + 1) + ":E" + m + ")");
		   			if (i == 4) cell.setFormula("=SUM(F" + (row_SELLING_EXPENSES_LA + 1) + ":F" + m + ")");
		   			if (i == 5) cell.setFormula("=SUM(G" + (row_SELLING_EXPENSES_LA + 1) + ":G" + m + ")");
		   			if (i == 6) cell.setFormula("=SUM(H" + (row_SELLING_EXPENSES_LA + 1) + ":H" + m + ")");
		   			if (i == 7) cell.setFormula("=SUM(I" + (row_SELLING_EXPENSES_LA + 1) + ":I" + m + ")");
		   			if (i == 8) cell.setFormula("=SUM(J" + (row_SELLING_EXPENSES_LA + 1) + ":J" + m + ")");
		   			if (i == 9) cell.setFormula("=SUM(K" + (row_SELLING_EXPENSES_LA + 1) + ":K" + m + ")");
		   			if (i == 10) cell.setFormula("=SUM(L" + (row_SELLING_EXPENSES_LA + 1) + ":L" + m + ")");
		   			if (i == 11) cell.setFormula("=SUM(M" + (row_SELLING_EXPENSES_LA + 1) + ":M" + m + ")");
			   }else{
				   cell.setFormula("=SUM(B" + row_SELLING_EXPENSES_LA + ":M" + row_SELLING_EXPENSES_LA + ")");
					   
			   }
		   
	   		}

		   m = m + 2;
		   int row_ADMINISTRATIVE_OVERHEAD_LA = m;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "CHI PHÍ QUẢN LÝ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);

		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);

		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Ban Tổng Giám Đốc");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data = bean.PL_BTGD_Budget();
		   
		   for(int i = 0; i < 12; i++){
   			   cell = cells.getCell(m-1, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }
			   
		   }

		   m++;
		   cell = cells.getCell("A" + m);
		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Phòng Tổ chức cán bộ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();

		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data = bean.PL_TCCB_Budget();
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);

			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);

			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }

		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Phòng hành chính quản trị");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data = bean.PL_HCQT_Budget();
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);

			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }

		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Phòng tài chính kế toán");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data = bean.PL_TCKT_Budget();
		   
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);

			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
			   }

		   }
		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Ban quản trị rủi ro");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();

		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data = bean.PL_QTRR_Budget();
		   
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }

		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Phòng Marketing");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data = bean.PL_MKT_Budget();
		   
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }

		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Phòng nghiên cứu & phát triển");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();

		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data = bean.PL_NCPT_Budget();
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);

			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }
		   
		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Phòng XNK - Cung ứng vật tư");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data = bean.PL_XNK_Budget();
		   
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }
		   
		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Phòng kế hoạch");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();

		   style.setFont(font);
		   
		   cell.setStyle(style);
		   
		   data = bean.PL_KH_Budget();
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);

			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);

			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
			   }

		   }

		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Ban dự án");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();

		   style.setFont(font);
		   
		   cell.setStyle(style);
		   
		   data = bean.PL_DA_Budget();
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);

			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);

			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }

		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Chung các phòng ban");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();

		   style.setFont(font);
		   
		   cell.setStyle(style);
		   
		   data = bean.PL_PBC_Budget();
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);

			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);

			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }


	   		for(int i = 0; i <= 12; i++){ 					
	   			cell = cells.getCell(row_ADMINISTRATIVE_OVERHEAD_LA-1, i + 1);
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
		   
		   
		   		if(i < 12 ){
		   			if (i == 0) cell.setFormula("=SUM(B" + (row_ADMINISTRATIVE_OVERHEAD_LA + 1) + ":B" + m + ")");
		   			if (i == 1) cell.setFormula("=SUM(C" + (row_ADMINISTRATIVE_OVERHEAD_LA + 1) + ":C" + m + ")");
		   			if (i == 2) cell.setFormula("=SUM(D" + (row_ADMINISTRATIVE_OVERHEAD_LA + 1) + ":D" + m + ")");
		   			if (i == 3) cell.setFormula("=SUM(E" + (row_ADMINISTRATIVE_OVERHEAD_LA + 1) + ":E" + m + ")");
		   			if (i == 4) cell.setFormula("=SUM(F" + (row_ADMINISTRATIVE_OVERHEAD_LA + 1) + ":F" + m + ")");
		   			if (i == 5) cell.setFormula("=SUM(G" + (row_ADMINISTRATIVE_OVERHEAD_LA + 1) + ":G" + m + ")");
		   			if (i == 6) cell.setFormula("=SUM(H" + (row_ADMINISTRATIVE_OVERHEAD_LA + 1) + ":H" + m + ")");
		   			if (i == 7) cell.setFormula("=SUM(I" + (row_ADMINISTRATIVE_OVERHEAD_LA + 1) + ":I" + m + ")");
		   			if (i == 8) cell.setFormula("=SUM(J" + (row_ADMINISTRATIVE_OVERHEAD_LA + 1) + ":J" + m + ")");
		   			if (i == 9) cell.setFormula("=SUM(K" + (row_ADMINISTRATIVE_OVERHEAD_LA + 1) + ":K" + m + ")");
		   			if (i == 10) cell.setFormula("=SUM(L" + (row_ADMINISTRATIVE_OVERHEAD_LA + 1) + ":L" + m + ")");
		   			if (i == 11) cell.setFormula("=SUM(M" + (row_ADMINISTRATIVE_OVERHEAD_LA + 1) + ":M" + m + ")");
			   }else{

				   cell.setFormula("=SUM(B" + row_ADMINISTRATIVE_OVERHEAD_LA + ":M" + row_ADMINISTRATIVE_OVERHEAD_LA + ")");
			   }
		   
	   		}
	
		   m = m + 2;
		   int row_OTHER_ACTIVITY_LA = m;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell, Color.MAGENTA, true, 12, "HOẠT ĐỘNG KHÁC");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);

		   font = style.getFont();

		   style.setFont(font);
		   
		   cell.setStyle(style);
		   
		   data = bean.PL_OtherActivity_LA_Budget();
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);

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
				   
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }
				 
		   }

		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Doanh thu khác(711)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();

		   style.setFont(font);
		   
		   cell.setStyle(style);
		   
		   data = bean.PL_Other_Income_LA_Budget();
		   
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);

			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue("(" + formatter.format(Math.round(Double.parseDouble(data[i]))) + ")");

			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }
		   
		   m++;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell,Color.BLACK, true, 12, "Chi phí khác (811)");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);
		   style.setIndent(1);
		   font = style.getFont();

		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data = bean.PL_OtherExpenses_LA_Budget();
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);

			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(false);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);

			   cell.setStyle(style);
			   if(i < 12){
				   cell.setValue(Double.parseDouble(data[i]));
			   }else{			   
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");
				   
			   }

		   }


		   m =  m + 4;
		   int row_Net_Profit_before_tax_LA = m;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 18, "LỢI NHUẬN RÒNG TRƯỚC THUẾ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);

		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);

			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);
			   cell.setStyle(style);

			   if(i < 12){
				   if (i == 0) cell.setFormula("=B" + row_Gross_profit_LA + "-B" + row_SELLING_EXPENSES_LA + "-B" + row_ADMINISTRATIVE_OVERHEAD_LA + "-B" + row_OTHER_ACTIVITY_LA + "");
				   if (i == 1) cell.setFormula("=C" + row_Gross_profit_LA + "-C" + row_SELLING_EXPENSES_LA + "-C" + row_ADMINISTRATIVE_OVERHEAD_LA + "-C" + row_OTHER_ACTIVITY_LA + "");
				   if (i == 2) cell.setFormula("=D" + row_Gross_profit_LA + "-D" + row_SELLING_EXPENSES_LA + "-D" + row_ADMINISTRATIVE_OVERHEAD_LA + "-D" + row_OTHER_ACTIVITY_LA + "");
				   if (i == 3) cell.setFormula("=E" + row_Gross_profit_LA + "-E" + row_SELLING_EXPENSES_LA + "-E" + row_ADMINISTRATIVE_OVERHEAD_LA + "-E" + row_OTHER_ACTIVITY_LA + "");
				   if (i == 4) cell.setFormula("=F" + row_Gross_profit_LA + "-F" + row_SELLING_EXPENSES_LA + "-F" + row_ADMINISTRATIVE_OVERHEAD_LA + "-F" + row_OTHER_ACTIVITY_LA + "");
				   if (i == 5) cell.setFormula("=G" + row_Gross_profit_LA + "-G" + row_SELLING_EXPENSES_LA + "-G" + row_ADMINISTRATIVE_OVERHEAD_LA + "-G" + row_OTHER_ACTIVITY_LA + "");
				   if (i == 6) cell.setFormula("=H" + row_Gross_profit_LA + "-H" + row_SELLING_EXPENSES_LA + "-H" + row_ADMINISTRATIVE_OVERHEAD_LA + "-H" + row_OTHER_ACTIVITY_LA + "");
				   if (i == 7) cell.setFormula("=I" + row_Gross_profit_LA + "-I" + row_SELLING_EXPENSES_LA + "-I" + row_ADMINISTRATIVE_OVERHEAD_LA + "-I" + row_OTHER_ACTIVITY_LA + "");
				   if (i == 8) cell.setFormula("=J" + row_Gross_profit_LA + "-J" + row_SELLING_EXPENSES_LA + "-J" + row_ADMINISTRATIVE_OVERHEAD_LA + "-J" + row_OTHER_ACTIVITY_LA + "");
				   if (i == 9) cell.setFormula("=K" + row_Gross_profit_LA + "-K" + row_SELLING_EXPENSES_LA + "-K" + row_ADMINISTRATIVE_OVERHEAD_LA + "-K" + row_OTHER_ACTIVITY_LA + "");
				   if (i == 10) cell.setFormula("=L" + row_Gross_profit_LA + "-L" + row_SELLING_EXPENSES_LA + "-L" + row_ADMINISTRATIVE_OVERHEAD_LA + "-L" + row_OTHER_ACTIVITY_LA + "");
				   if (i == 11) cell.setFormula("=M" + row_Gross_profit_LA + "-M" + row_SELLING_EXPENSES_LA + "-M" + row_ADMINISTRATIVE_OVERHEAD_LA + "-M" + row_OTHER_ACTIVITY_LA + "");
			   }else{
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");

			   }
			   
		   }
		   
		   m++;
		   int row_CURRENT_INCOME_TAX_EXPENSE_LA = m;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell, Color.BLACK, true, 16, "THUẾ THU NHẬP DOANH NGHIỆP");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);

		   font = style.getFont();

		   style.setFont(font);
		   
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
			   
   			   cell = cells.getCell(m-1, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);

			   cell.setStyle(style);

			   if(i < 12){
				   if (i == 0) cell.setFormula("=-ROUND(B" + row_Net_Profit_before_tax_LA + "*15%, 0)");
				   if (i == 1) cell.setFormula("=-ROUND(C" + row_Net_Profit_before_tax_LA + "*15%, 0)");
				   if (i == 2) cell.setFormula("=-ROUND(D" + row_Net_Profit_before_tax_LA + "*15%, 0)");
				   if (i == 3) cell.setFormula("=-ROUND(E" + row_Net_Profit_before_tax_LA + "*15%, 0)");
				   if (i == 4) cell.setFormula("=-ROUND(F" + row_Net_Profit_before_tax_LA + "*15%, 0)");
				   if (i == 5) cell.setFormula("=-ROUND(G" + row_Net_Profit_before_tax_LA + "*15%, 0)");
				   if (i == 6) cell.setFormula("=-ROUND(H" + row_Net_Profit_before_tax_LA + "*15%, 0)");
				   if (i == 7) cell.setFormula("=-ROUND(I" + row_Net_Profit_before_tax_LA + "*15%, 0)");
				   if (i == 8) cell.setFormula("=-ROUND(J" + row_Net_Profit_before_tax_LA + "*15%, 0)");
				   if (i == 9) cell.setFormula("=-ROUND(K" + row_Net_Profit_before_tax_LA + "*15%, 0)");
				   if (i == 10) cell.setFormula("=-ROUND(L" + row_Net_Profit_before_tax_LA + "*15%, 0)");
				   if (i == 11) cell.setFormula("=-ROUND(M" + row_Net_Profit_before_tax_LA + "*15%, 0)");
			   }else{
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");

			   }
			   
		   }

		   m = m + 2;
		   cell = cells.getCell("A" + m);		   
		   ReportAPI.getCellStyle(cell, Color.BLUE, true, 18, "LỢI NHUẬN RÒNG SAU THUẾ");		   		   
		   
		   style = cell.getStyle();
		   style.setHAlignment(TextAlignmentType.LEFT);

		   font = style.getFont();
		   style.setFont(font);
		   cell.setStyle(style);
		   
		   data = null;
		   
		   for(int i = 0; i <= 12; i++){
   			   cell = cells.getCell(m-1, i + 1);
			   style = cell.getStyle();
			   
			   style.setHAlignment(TextAlignmentType.RIGHT);
			   style.setNumber(3);
			   font = style.getFont();
			   font.setBold(true);
			   font.setSize(12);
			   font.setColor(Color.BLACK);
			   style.setFont(font);

			   cell.setStyle(style);

			   if(i < 12){
				   if (i == 0) cell.setFormula("=B" + row_Net_Profit_before_tax_LA + "+B" + row_CURRENT_INCOME_TAX_EXPENSE_LA);
				   if (i == 1) cell.setFormula("=C" + row_Net_Profit_before_tax_LA + "+C" + row_CURRENT_INCOME_TAX_EXPENSE_LA);
				   if (i == 2) cell.setFormula("=D" + row_Net_Profit_before_tax_LA + "+D" + row_CURRENT_INCOME_TAX_EXPENSE_LA);
				   if (i == 3) cell.setFormula("=E" + row_Net_Profit_before_tax_LA + "+E" + row_CURRENT_INCOME_TAX_EXPENSE_LA);
				   if (i == 4) cell.setFormula("=F" + row_Net_Profit_before_tax_LA + "+F" + row_CURRENT_INCOME_TAX_EXPENSE_LA);
				   if (i == 5) cell.setFormula("=G" + row_Net_Profit_before_tax_LA + "+G" + row_CURRENT_INCOME_TAX_EXPENSE_LA);
				   if (i == 6) cell.setFormula("=H" + row_Net_Profit_before_tax_LA + "+H" + row_CURRENT_INCOME_TAX_EXPENSE_LA);
				   if (i == 7) cell.setFormula("=I" + row_Net_Profit_before_tax_LA + "+I" + row_CURRENT_INCOME_TAX_EXPENSE_LA);
				   if (i == 8) cell.setFormula("=J" + row_Net_Profit_before_tax_LA + "+J" + row_CURRENT_INCOME_TAX_EXPENSE_LA);
				   if (i == 9) cell.setFormula("=K" + row_Net_Profit_before_tax_LA + "+K" + row_CURRENT_INCOME_TAX_EXPENSE_LA);
				   if (i == 10) cell.setFormula("=L" + row_Net_Profit_before_tax_LA + "+L" + row_CURRENT_INCOME_TAX_EXPENSE_LA);
				   if (i == 11) cell.setFormula("=M" + row_Net_Profit_before_tax_LA + "+M" + row_CURRENT_INCOME_TAX_EXPENSE_LA);
			   }else{
				   cell.setFormula("=SUM(B" + m + ":M" + m + ")");

			   }

		   }

		  }catch(Exception ex){
				System.out.println(ex.toString());
			}
	}

	
	public String getDate() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
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
}
