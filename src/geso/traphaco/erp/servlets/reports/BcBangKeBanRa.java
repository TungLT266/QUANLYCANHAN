package geso.traphaco.erp.servlets.reports;

import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.beans.stockintransit.IStockintransit;
import geso.traphaco.erp.beans.stockintransit.imp.Stockintransit;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.HorizontalAlignmentType;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class BcBangKeBanRa extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
   
    public BcBangKeBanRa() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();	
		String querystring = request.getQueryString();
		Utility util = new Utility();
		String ctyId = (String)session.getAttribute("congtyId");
		obj.setErpCongtyId(ctyId);

		obj.setuserId(util.getUserId(querystring));
		obj.setuserTen((String) session.getAttribute("userTen"));
		
		String view = util.antiSQLInspection(request.getParameter("view"));
		if(view == null) view = "";
		
		obj.setView(view);		
		obj.InitErp();
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBangKeBanRa.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		obj.setdiscount("1");
		obj.setvat("1");
		
		OutputStream out = response.getOutputStream();
		Utility util = new Utility();
		String ctyIds = util.antiSQLInspection(request.getParameter("ctyIds"));
		obj.setErpCongtyId(ctyIds);
		obj.setView(util.antiSQLInspection(request.getParameter("view")));

		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		
		String ctyId = (String)session.getAttribute("congtyId");
		ctyId = ctyId == null ? "" : ctyId;
		obj.setCongTy(ctyId);		
		
		String tuNgay = request.getParameter("tuNgay");
	    if (tuNgay == null)
	    	tuNgay = "";
	    obj.settungay(tuNgay);
	    
	    String denNgay = request.getParameter("denNgay");
	    if (denNgay == null)
	    	denNgay = "";
	    obj.setdenngay(denNgay);

	    String year = request.getParameter("year");
	    if (year == null)
	    	year = "";
	    obj.setYear(year);
	    
	    String thoiDiem = request.getParameter("thoiDiem");
	    if (thoiDiem == null)
	    	thoiDiem = "";
	    obj.setMonth(thoiDiem);
	    
	    
	    String nhomkhachhang = request.getParameter("nhomkhachhang");
	    if (nhomkhachhang == null)
	    	nhomkhachhang = "";
	    obj.setNhomKhId(nhomkhachhang);
	    
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");
		String action = (String) util.antiSQLInspection(request.getParameter("action"));
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBangKeBanRa.jsp";
		obj.InitErp();
		
		if (action.equals("Taomoi")) 
		{			
			response.setContentType("application/xls");
			response.setHeader("Content-Disposition", "attachment; filename=BcBangKeBanRa.xls");
	        try 
	        {
				if(!CreatePivotTable(out, response, request, obj, ""))
				{
					response.setContentType("text/html");
				    PrintWriter writer = new PrintWriter(out);
				    writer.print("Xin loi khong co bao cao trong thoi gian nay");
				    writer.close();
				}
				
			} 
	        catch (Exception e) 
	        {
				obj.setMsg("Khong the tao bao cao " + e.getMessage());
				e.printStackTrace();
			} finally {
				obj.DBclose();
			}
		}
		else {
			session.setAttribute("obj", obj);
			response.sendRedirect(nextJSP);
			return;
		}
	}
	
	private boolean CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj, String query) throws Exception 
	{
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BangKeBanRa.xls");
		
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL97TO2003);
		
		isFillData = FillData(workbook, obj, query);
   
		if (!isFillData)
		{
			if(fstream != null)
				fstream.close();
			return false;
		}
		
		workbook.save(out);
		fstream.close();
		return true;	
	}
	
	private void setStyleColorNormar(Cells cells ,Cell cell)
	{
		Cell cell1 = cells.getCell("X1");
		Style style;	
		 style = cell1.getStyle();
		 style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
                cell.setStyle(style);
	}
	
	private void setStyleColorLeft(Cells cells ,Cell cell)
	{
		Cell cell1 = cells.getCell("P1");
		Style style;	
		 style = cell1.getStyle();
		 style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
                cell.setStyle(style);
        
        
	}
	
	private void setStyleColorCenter(Cells cells ,Cell cell)
	{
		Cell cell1 = cells.getCell("Q1");
		Style style;	
		 style = cell1.getStyle();
		 style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
                cell.setStyle(style);
        
        
	}
	
	private boolean FillData(Workbook workbook, IStockintransit obj, String query) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		
		Cells cells = worksheet.getCells();	
		
		/*for(int i = 0;i < 7; ++i)
		{
	    	cells.setColumnWidth(i, 15.0f);
	    	if(i == 4)
	    		cells.setColumnWidth(i, 30.0f);
	    }	*/
		Cell cell = null;
		
		 int quy = 1;
		 if(Integer.parseInt(obj.getMonth()) >= 4 && Integer.parseInt(obj.getMonth()) <= 6) quy = 2;
		 else if(Integer.parseInt(obj.getMonth()) >= 7 && Integer.parseInt(obj.getMonth()) <= 9) quy = 3;
		 else if (Integer.parseInt(obj.getMonth()) >= 10 && Integer.parseInt(obj.getMonth()) <= 12) quy = 4;
		
		cell = cells.getCell("B7");		cell.setValue("Kỳ Tính Thuế : Tháng "+obj.getMonth()+" năm "+obj.getYear() +" /Quý " + quy + " Năm " +obj.getYear() );	
		
		String sql="select ten,masothue  from erp_congty where pk_seq="+obj.getErpCongtyId();
		 ResultSet rs_=db.get(sql);
		 
		if(rs_!=null){
			if(rs_.next()){
				cell = cells.getCell("B9");		cell.setValue("Người nộp thuế: "+rs_.getString("ten"));
				cell = cells.getCell("B10");	cell.setValue("Mã số thuế: "+rs_.getString("masothue"));
			}
			rs_.close();
		}
		 
		
//		int thang=Integer.parseInt(obj.getMonth());
//		int namdk=0;
//		int thangdk=0;
//		if(thang==1){
//			thangdk=12;
//			namdk=Integer.parseInt(obj.getYear())-1;
//		}else{
//			thangdk=thang-1;
//			namdk=Integer.parseInt(obj.getYear());
//		}		 
				 
		 int index = 17;
		 int i = index;
		 int stt = 1;
		 
		Style style;
		Font font2 = new Font();
		font2.setName("Arial");				
		font2.setSize(10);
		
		Font font3 = new Font();
		font3.setName("Arial");				
		font3.setSize(10);
		font3.setBold(true);
		
		Cell cell_mau01 = cells.getCell("B1");
		Cell cell_mau02 = cells.getCell("L1");
		
		Cell cell_mau = cells.getCell("C7");
//		Cell cell_mau2 = cells.getCell("L7");
						
		// IN TIÊU ĐỀ
		cells.merge(i-1, 1, i-1, 10);	
		cell = cells.getCell("B" + Integer.toString(i));	
		cell.setValue("1. Hàng hóa, dịch vụ không chịu thuế giá trị gia tăng (GTGT):"); 	style = cell_mau01.getStyle(); style.setFont(font2);  style.setBorderLine(BorderType.LEFT, BorderLineType.THIN); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		
		cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		
		cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);		
		cell = cells.getCell("F" + Integer.toString(i));	cell.setValue("");   	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT); 
		cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
		cell = cells.getCell("K" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
		cell = cells.getCell("L" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau02.getStyle(); style.setFont(font2) ; style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN) ;  cell.setStyle(style) ; setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
		cell = cells.getCell("M" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		cell = cells.getCell("N" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		cell = cells.getCell("O" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);

		i++;
		
		 double totaltienavat_0=0;
		 double totaltienbvat_0=0;
		 double totalvat_0=0;
		 
		 
		// TIÊU ĐỀ
		cells.merge(i-1, 1, i-1, 10);	
		cell = cells.getCell("B" + Integer.toString(i));	
		cell.setValue("2. Hàng hóa, dịch vụ chịu thuế suất thuế GTGT 0% "); 	style = cell_mau01.getStyle(); style.setFont(font2);  style.setBorderLine(BorderType.LEFT, BorderLineType.THIN); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		
		cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(""); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(""); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		
		cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(""); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);		
		cell = cells.getCell("F" + Integer.toString(i));	cell.setValue("");  style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(""); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(""); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(""); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT); 
		cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(""); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
		cell = cells.getCell("K" + Integer.toString(i));	cell.setValue(""); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
		cell = cells.getCell("L" + Integer.toString(i));	cell.setValue(""); 	style = cell_mau02.getStyle(); style.setFont(font2) ; style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN) ;  cell.setStyle(style) ; setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
		cell = cells.getCell("M" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		cell = cells.getCell("N" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		cell = cells.getCell("O" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		i++;
		db.getConnection().setAutoCommit(false);
		this.deleteBangTam(db);
		this.createBangTam(obj, db);
		ResultSet rs_0 = queryBangTam(db, 0);
		 
		 if (rs_0 != null) 
		 {
			try 
			{
				while (rs_0.next())
				{	
					
					double vat = rs_0.getDouble("vat");
					double bvat= Math.round(rs_0.getDouble("tongtienbvat"));
					double avat= Math.round(rs_0.getDouble("tongtienavat"));
					
					
					// Định dạng ngày xuất hóa đơn
						
					cell = cells.getCell("B"+String.valueOf(i));cell.setValue(stt );	
					this.setStyleColorLeft(cells, cell);
					cell = cells.getCell("C"+String.valueOf(i));cell.setValue("");	
					this.setStyleColorLeft(cells, cell);
					cell = cells.getCell("D"+String.valueOf(i));cell.setValue(rs_0.getString("kyhieu"));	
					this.setStyleColorLeft(cells, cell);
					cell = cells.getCell("E"+String.valueOf(i));cell.setValue(rs_0.getString("sohoadon"));
					this.setStyleColorLeft(cells, cell);
					cell = cells.getCell("F"+String.valueOf(i));cell.setValue(rs_0.getString("NGAY"));
					this.setStyleColorCenter(cells, cell);
					cell = cells.getCell("G"+String.valueOf(i));cell.setValue(rs_0.getString("tennguoimua"));
					this.setStyleColorLeft(cells, cell);
					cell = cells.getCell("H"+String.valueOf(i));cell.setValue(rs_0.getString("masothue"));
					this.setStyleColorLeft(cells, cell);
					cell = cells.getCell("I"+String.valueOf(i));cell.setValue(rs_0.getString("mathang"));
					this.setStyleColorLeft(cells, cell);
					
					cell = cells.getCell("J"+String.valueOf(i));
					this.setStyleColorNormar(cells, cell);
					style = cell.getStyle();
					style.setNumber(3);
					cell.setStyle(style);
					cell.setValue(bvat);
					
					totaltienbvat_0=totaltienbvat_0 + bvat;
					
					cell = cells.getCell("K"+String.valueOf(i));
					this.setStyleColorNormar(cells, cell);
					style = cell.getStyle();
					style.setNumber(3);
					cell.setStyle(style);
					cell.setValue(vat);
					
					totalvat_0 = totalvat_0 + vat;
					
					cell = cells.getCell("L"+String.valueOf(i));
					this.setStyleColorNormar(cells, cell);
					style = cell.getStyle();
					style.setNumber(3);
					cell.setStyle(style);
					cell.setValue(rs_0.getString("ghichu"));
					
					cell = cells.getCell("M"+String.valueOf(i));cell.setValue(rs_0.getString("LOAIHOADON"));
					this.setStyleColorLeft(cells, cell);
					cell = cells.getCell("N"+String.valueOf(i));cell.setValue(rs_0.getString("SOCHUNGTU"));
					this.setStyleColorLeft(cells, cell);
					
					cell = cells.getCell("O"+String.valueOf(i));cell.setValue(this.catChuoiDayChungTu(catChuoiDayChungTu(rs_0.getString("SOCHUNGTU"))));
					this.setStyleColorLeft(cells, cell);
					
					totaltienavat_0= totaltienavat_0 + avat;
					
					
					i++;
					stt++;
				}

				
				//THEM DONG TONG CONG		 
				
				cell = cells.getCell("B"+String.valueOf(i));cell.setValue("Tổng" ); this.setStyleColorLeft(cells, cell); 
				style = cell_mau.getStyle(); style.setFont(font3); 
	       		style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
	       		style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
	       		style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
	       		style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
	       		style.setNumber(3);
	       		cell.setStyle(style); 
	       		setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("C"+String.valueOf(i));cell.setValue(""); this.setStyleColorLeft(cells, cell);
				cell = cells.getCell("D"+String.valueOf(i));cell.setValue(""); this.setStyleColorLeft(cells, cell);
				cell = cells.getCell("E"+String.valueOf(i));cell.setValue(""); this.setStyleColorLeft(cells, cell);
				cell = cells.getCell("F"+String.valueOf(i));cell.setValue(""); this.setStyleColorCenter(cells, cell);
				cell = cells.getCell("G"+String.valueOf(i));cell.setValue(""); this.setStyleColorLeft(cells, cell);
				cell = cells.getCell("H"+String.valueOf(i));cell.setValue(""); this.setStyleColorLeft(cells, cell);
				cell = cells.getCell("I"+String.valueOf(i));cell.setValue(""); this.setStyleColorLeft(cells, cell);				
				cell = cells.getCell("J"+String.valueOf(i));cell.setValue(totaltienbvat_0); this.setStyleColorLeft(cells, cell);
				style = cell.getStyle(); style.setFont(font3);	
				style.setNumber(3);
	       		style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
	       		style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
	       		style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
	       		style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
	       		cell.setStyle(style); 
	       		setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				cell = cells.getCell("K"+String.valueOf(i)); cell.setValue(Double.parseDouble("0")); this.setStyleColorLeft(cells, cell);
				style = cell_mau.getStyle(); style.setFont(font3); 
	       		style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
	       		style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
	       		style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
	       		style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
	       		cell.setStyle(style); 
	       		setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				cell = cells.getCell("L"+String.valueOf(i)); cell.setValue(""); this.setStyleColorLeft(cells, cell);
				cell = cells.getCell("M"+String.valueOf(i)); cell.setValue(""); this.setStyleColorLeft(cells, cell);
				cell = cells.getCell("N"+String.valueOf(i)); cell.setValue(""); this.setStyleColorLeft(cells, cell);
				cell = cells.getCell("O"+String.valueOf(i)); cell.setValue(""); this.setStyleColorLeft(cells, cell);
										 
				i++;
				
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				throw new Exception(ex.getMessage());
			} finally {
				rs_0.close();
			}
		}
		else
		{
			return false;
		}		
		
		// THUẾ 5%
		cells.merge(i-1, 1, i-1, 10);	
		cell = cells.getCell("B" + Integer.toString(i));	
		cell.setValue("3. Hàng hóa, dịch vụ chịu thuế suất thuế GTGT 5%"); 	style = cell_mau01.getStyle(); style.setFont(font2);  style.setBorderLine(BorderType.LEFT, BorderLineType.THIN); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		
		cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		
		cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);		
		cell = cells.getCell("F" + Integer.toString(i));	cell.setValue("");   	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT); 
		cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
		cell = cells.getCell("K" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
		cell = cells.getCell("L" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau02.getStyle(); style.setFont(font2) ; style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN) ;  cell.setStyle(style) ; setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
		cell = cells.getCell("M" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		cell = cells.getCell("N" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		cell = cells.getCell("O" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);

		i++;		
		
			 
		
	 	 
		 double totaltienavat_5=0;
		 double totaltienbvat_5=0;
		 double totalvat_5=0;
		 ResultSet rs_5 = null;
		rs_5 = queryBangTam(db, 5);
				 
		 if (rs_5 != null) 
		 {
			try 
			{
				while (rs_5.next())
				{	
					
					double vat = rs_5.getDouble("vat");
					double bvat= Math.round(rs_5.getDouble("tongtienbvat"));
					double avat= Math.round(rs_5.getDouble("tongtienavat"));
					
					
					// Định dạng ngày xuất hóa đơn
						
					cell = cells.getCell("B"+String.valueOf(i));cell.setValue(stt );	
					this.setStyleColorLeft(cells, cell);
					cell = cells.getCell("C"+String.valueOf(i));cell.setValue("");	
					this.setStyleColorLeft(cells, cell);
					cell = cells.getCell("D"+String.valueOf(i));cell.setValue(rs_5.getString("kyhieu"));	
					this.setStyleColorLeft(cells, cell);
					cell = cells.getCell("E"+String.valueOf(i));cell.setValue(rs_5.getString("sohoadon"));
					this.setStyleColorLeft(cells, cell);
					cell = cells.getCell("F"+String.valueOf(i));cell.setValue(rs_5.getString("NGAY"));
					this.setStyleColorCenter(cells, cell);
					cell = cells.getCell("G"+String.valueOf(i));cell.setValue(rs_5.getString("tennguoimua"));
					this.setStyleColorLeft(cells, cell);
					cell = cells.getCell("H"+String.valueOf(i));cell.setValue(rs_5.getString("masothue"));
					this.setStyleColorLeft(cells, cell);
					cell = cells.getCell("I"+String.valueOf(i));cell.setValue(rs_5.getString("mathang"));
					this.setStyleColorLeft(cells, cell);
					
					cell = cells.getCell("J"+String.valueOf(i));
					this.setStyleColorNormar(cells, cell);
					style = cell.getStyle();
					style.setNumber(3);
					cell.setStyle(style);
					cell.setValue(bvat);
					
					totaltienbvat_5=totaltienbvat_5 + bvat;
					
					cell = cells.getCell("K"+String.valueOf(i));
					this.setStyleColorNormar(cells, cell);
					style = cell.getStyle();
					style.setNumber(3);
					cell.setStyle(style);
					cell.setValue(vat);
					
					totalvat_5 = totalvat_5 + vat;
					
					cell = cells.getCell("L"+String.valueOf(i));
					this.setStyleColorNormar(cells, cell);
					style = cell.getStyle();
					style.setNumber(3);
					cell.setStyle(style);
					cell.setValue(rs_5.getString("ghichu"));
					
					cell = cells.getCell("M"+String.valueOf(i));cell.setValue(rs_5.getString("LOAIHOADON"));
					this.setStyleColorLeft(cells, cell);
					cell = cells.getCell("N"+String.valueOf(i));cell.setValue(rs_5.getString("SOCHUNGTU"));
					this.setStyleColorLeft(cells, cell);
					cell = cells.getCell("O"+String.valueOf(i));cell.setValue(this.catChuoiDayChungTu(catChuoiDayChungTu(rs_5.getString("SOCHUNGTU"))));
					this.setStyleColorLeft(cells, cell);
					
					
					totaltienavat_5= totaltienavat_5 + avat;
					
					
					i++;
					stt++;
				}

				
				//THEM DONG TONG CONG		 
				
				cell = cells.getCell("B" + Integer.toString(i));	
        	    //MergeCellAndBorder(cells, i-1, 1, i-1, 3);
        		cell.setValue("Tổng"); 	
        		style = cell_mau.getStyle(); style.setFont(font3); 
        		style = cell_mau.getStyle(); style.setFont(font3); 
        		style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
        		style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        		style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        		style.setBorderLine(BorderType.TOP, BorderLineType.THIN);	        			
        		cell.setStyle(style); 
        		setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
        		
				cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(""); 		this.setStyleColorNormar(cells, cell);
				cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(""); 		this.setStyleColorNormar(cells, cell);
				
        		cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(""); 		this.setStyleColorNormar(cells, cell);		
				cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(""); 		this.setStyleColorNormar(cells, cell);
				cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(""); 		this.setStyleColorNormar(cells, cell);
				cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(""); 		this.setStyleColorNormar(cells, cell);
				cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(""); 		this.setStyleColorNormar(cells, cell); 
				
				cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(totaltienbvat_5); 
				style = cell.getStyle(); style.setFont(font3);	
				style.setNumber(3);
        		style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
        		style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        		style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        		style.setBorderLine(BorderType.TOP, BorderLineType.THIN);	        		
        		 cell.setStyle(style);
        		 setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				
        		 cell = cells.getCell("K" + Integer.toString(i));	cell.setValue(totalvat_5); 			this.setStyleColorNormar(cells, cell); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				style = cell.getStyle(); style.setFont(font3); 
				style.setNumber(3);
        		style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
        		style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        		style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        		style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        		cell.setStyle(style);
        		setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
        		
				cell = cells.getCell("L" + Integer.toString(i));	cell.setValue(""); 					this.setStyleColorNormar(cells, cell); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);

				cell = cells.getCell("M" + Integer.toString(i));	cell.setValue(""); 		this.setStyleColorNormar(cells, cell); 
				cell = cells.getCell("N" + Integer.toString(i));	cell.setValue(""); 		this.setStyleColorNormar(cells, cell); 
				cell = cells.getCell("O" + Integer.toString(i));	cell.setValue(""); 		this.setStyleColorNormar(cells, cell); 						 
				i++;
				
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				throw new Exception(ex.getMessage());
			}  finally {
				rs_5.close();
			}
		}
		else
		{
			return false;
		}
		
		
		//IN COT TIEU DE  --> 10%
	 	cells.merge(i-1, 1, i-1, 10);	
		//cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(""); 			style = cell_mau01.getStyle(); style.setFont(font2); cell.setStyle(style);
		
		cell = cells.getCell("B" + Integer.toString(i));	
		cell.setValue("4. Hàng hóa, dịch vụ chịu thuế suất thuế GTGT 10%"); 	style = cell_mau01.getStyle(); style.setBorderLine(BorderType.LEFT, BorderLineType.THIN) ;style.setFont(font2); style.setFont(font2) ;  cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		
		cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		
		cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);		
		cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT); 
		cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
		cell = cells.getCell("K" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
		cell = cells.getCell("L" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau02.getStyle(); style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
		cell = cells.getCell("M" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		cell = cells.getCell("N" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		cell = cells.getCell("O" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		
		 
		i++;
			
	
			double totaltienavat_10 =0;
			double totaltienbvat_10 =0;
			double totalvat_10=0;
			ResultSet rs_10 = null;
			rs_10 = queryBangTam(db, 10);
			if (rs_10 != null) 
			{
				try 
				{
					while (rs_10.next())
					{	
						
						double vat = rs_10.getDouble("vat");
						double bvat= Math.round(rs_10.getDouble("tongtienbvat"));
						double avat= Math.round(rs_10.getDouble("tongtienavat"));
						
						/*if(rs_10.getString("loaihd").equals("2"))
						{
							vat = rs_10.getDouble("vat")*(-1);
							bvat= rs_10.getDouble("tongtienbvat")*(-1);
							avat= rs_10.getDouble("tongtienavat")*(-1);
						}*/
						
						// Định dạng ngày xuất hóa đơn
							
						cell = cells.getCell("B"+String.valueOf(i));cell.setValue(stt );	
						this.setStyleColorLeft(cells, cell);
						cell = cells.getCell("C"+String.valueOf(i));cell.setValue("");	
						this.setStyleColorNormar(cells, cell);
						cell = cells.getCell("D"+String.valueOf(i));cell.setValue(rs_10.getString("kyhieu"));	
						this.setStyleColorLeft(cells, cell);
						cell = cells.getCell("E"+String.valueOf(i));cell.setValue(rs_10.getString("sohoadon"));
						this.setStyleColorLeft(cells, cell);
						cell = cells.getCell("F"+String.valueOf(i));cell.setValue(rs_10.getString("NGAY"));
						this.setStyleColorCenter(cells, cell);
						cell = cells.getCell("G"+String.valueOf(i));cell.setValue(rs_10.getString("tennguoimua"));
						this.setStyleColorLeft(cells, cell);
						cell = cells.getCell("H"+String.valueOf(i));cell.setValue(rs_10.getString("masothue"));
						this.setStyleColorLeft(cells, cell);
						cell = cells.getCell("I"+String.valueOf(i));cell.setValue(rs_10.getString("mathang"));
						this.setStyleColorLeft(cells, cell);
						
						cell = cells.getCell("J"+String.valueOf(i));
						this.setStyleColorNormar(cells, cell);
						style = cell.getStyle();
						style.setNumber(3);
						cell.setStyle(style);
						cell.setValue(bvat);
						
						totaltienbvat_10 =totaltienbvat_10 + bvat;
						
						cell = cells.getCell("K"+String.valueOf(i));
						this.setStyleColorNormar(cells, cell);
						style = cell.getStyle();
						style.setNumber(3);
						cell.setStyle(style);
						cell.setValue(vat);
						
						totalvat_10 =totalvat_10 + vat;
						
						cell = cells.getCell("L"+String.valueOf(i));
						this.setStyleColorNormar(cells, cell);
						cell.setValue(rs_10.getString("ghichu"));
						
						totaltienavat_10= totaltienavat_10 + avat;
						cell = cells.getCell("M"+String.valueOf(i));cell.setValue(rs_10.getString("LOAIHOADON"));
						this.setStyleColorLeft(cells, cell);
						cell = cells.getCell("N"+String.valueOf(i));cell.setValue(rs_10.getString("SOCHUNGTU"));
						this.setStyleColorLeft(cells, cell);
						cell = cells.getCell("O"+String.valueOf(i));cell.setValue(this.catChuoiDayChungTu(catChuoiDayChungTu(rs_10.getString("SOCHUNGTU"))));
						this.setStyleColorLeft(cells, cell);
						
						
						i++;
						stt++;
					}

					
					//THEM DONG TONG CONG					 
					
	        		cell = cells.getCell("B" + Integer.toString(i));	
	        	    //MergeCellAndBorder(cells, i-1, 1, i-1, 3);
	        		cell.setValue("Tổng"); 	
	        		style = cell_mau.getStyle(); style.setFont(font3); 
	        		style = cell_mau.getStyle(); style.setFont(font3); 
	        		style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
	        		style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
	        		style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
	        		style.setBorderLine(BorderType.TOP, BorderLineType.THIN);	        			
	        		cell.setStyle(style); 
	        		setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
	        		
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(""); 		this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(""); 		this.setStyleColorNormar(cells, cell);
					
	        		cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(""); 		this.setStyleColorNormar(cells, cell);		
					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(""); 		this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(""); 		this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(""); 		this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(""); 		this.setStyleColorNormar(cells, cell); 
					
					cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(totaltienbvat_10); 
					style = cell.getStyle(); style.setFont(font3);	
					style.setNumber(3);
	        		style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
	        		style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
	        		style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
	        		style.setBorderLine(BorderType.TOP, BorderLineType.THIN);	        		
	        		 cell.setStyle(style);
	        		 setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					
	        		 cell = cells.getCell("K" + Integer.toString(i));	cell.setValue(totalvat_10); 			this.setStyleColorNormar(cells, cell); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					style = cell.getStyle(); style.setFont(font3); 
					style.setNumber(3);
	        		style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
	        		style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
	        		style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
	        		style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
	        		cell.setStyle(style);
	        		 setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
	        		
					cell = cells.getCell("L" + Integer.toString(i));	cell.setValue(""); 					this.setStyleColorNormar(cells, cell); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);

					cell = cells.getCell("M" + Integer.toString(i));	cell.setValue(""); 		this.setStyleColorNormar(cells, cell); 
					cell = cells.getCell("N" + Integer.toString(i));	cell.setValue(""); 		this.setStyleColorNormar(cells, cell); 
					cell = cells.getCell("O" + Integer.toString(i));	cell.setValue(""); 		this.setStyleColorNormar(cells, cell); 
					i++;
					
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					throw new Exception(ex.getMessage());
				} finally {
					rs_10.close();
				}
			}
			else
			{
				return false;
			}	
	
			 // DÒNG TỔNG BẢNG
			
//			 double totaltienavat = totaltienavat_0 + totaltienavat_5 + totaltienavat_10 ;
			 double totaltienbvat = totaltienbvat_0 + totaltienbvat_5 + totaltienbvat_10;
			 double totalvat = totalvat_0 + totalvat_5 + totalvat_10;
			 
			 System.out.println("Tổng tiền1 "+Double.toString(totaltienbvat));
			 NumberFormat formatter1 = new DecimalFormat("###,###,###"); 
			 i=i+1; 
			 cell = cells.getCell("B"+String.valueOf(i)); style = cell.getStyle(); style.setFont(font2); cell.setStyle(style);  setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
			 cell.setValue("Tổng doanh thu hàng hoá, dịch vụ bán ra: "+ formatter1.format(totaltienbvat)); 
			 
			 i=i+1; 
			 cell = cells.getCell("B"+String.valueOf(i));style = cell.getStyle(); style.setFont(font2) ;  cell.setStyle(style);  setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
			 cell.setValue("Tổng thuế GTGT của hàng hóa, dịch vụ bán ra: " + formatter1.format(totalvat)); 
			 
			 i=i+2;
			 cell = cells.getCell("J"+String.valueOf(i));cell.setValue(",ngày "+obj.getDate().substring(8,10)+" tháng  "+obj.getDate().substring(5,7)+" năm "+obj.getDate().substring(0,4)); style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
			 i=i+1;
			 cell = cells.getCell("J"+String.valueOf(i));cell.setValue("NGƯỜI NỘP THUẾ hoặc"); style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
			 i=i+1;
			 cell = cells.getCell("J"+String.valueOf(i));cell.setValue("ĐẠI DIỆN HỢP PHÁP CỦA NGƯỜI NỘP THUẾ"); style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
			 i=i+1;
			 cell = cells.getCell("J"+String.valueOf(i));cell.setValue(" Ký tên, đóng dấu (ghi rõ họ tên và chức vụ)");	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style);	 setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);	

		this.deleteBangTam(db);	
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		if(db != null)
			db.shutDown();
		
		return true;
	}	
	
	public void createBangTam(IStockintransit obj, dbutils db) {
		String sql =
				//"-- 13/06/2017 CAC PHIEU CO THUE BANG 0 THI VAN LEN BAO CAO \r\n" + 
				"BEGIN  \n" + 
				"SELECT LOAIHOADON, SOCHUNGTU, PK_SEQ, UPPER(A.KYHIEU) KYHIEU, A.SOHOADON,  \n" + 
				"CASE WHEN LEN(NGAYHOADON) > 0 THEN SUBSTRING(NGAYHOADON, 9, 2) +'-'+ SUBSTRING(NGAYHOADON, 6, 2) +'-'+ SUBSTRING(NGAYHOADON, 1, 4) ELSE '' END AS NGAY,  \n" + 
				"		A.KH_FK, A.TENNGUOIMUA,   \n" + 
				"       LTRIM(A.MASOTHUE) AS MASOTHUE, ( A.TONGTIENBVAT ) TONGTIENBVAT , A.THUESUAT, ( A.VAT) VAT, (A.TONGTIENAVAT) AS TONGTIENAVAT, isnull(A.GHICHU,'') AS GHICHU, A.MATHANG   \n"
				+ "INTO Tb_ufn_bangkedauraERP \n" + 
				" FROM (  \n" + 
				/*"--BEGIN HOA DON TAI CHINH KHACH HANG CHI NHANH (DMS) \n" + 
				"		SELECT N'HÓA ĐƠN TÀI CHÍNH OTC CHI NHÁNH' LOAIHOADON, HD.NGAYXUATHD + ' - ' + CONVERT(VARCHAR, HD.PK_SEQ) AS SOCHUNGTU, ISNULL(HD.KYHIEU, '') KYHIEU, ISNULL(HD.SOHOADON, '') AS SOHOADON, HD.NGAYXUATHD AS NGAYHOADON,  \r\n" + 
				"					KH.PK_SEQ AS KH_FK, KH.TEN AS TENNGUOIMUA, LTRIM(KH.MASOTHUE) AS MASOTHUE, \r\n" + 
				"					SUM(ROUND(DONGIA*SOLUONG, 0)-HD_SP.CHIETKHAU) AS TONGTIENBVAT,  \r\n" + 
				"					HD_SP.VAT AS THUESUAT,  \r\n" + 
				"					SUM( ROUND((ROUND(DONGIA*SOLUONG, 0)-HD_SP.CHIETKHAU)*HD_SP.VAT/100, 0) ) AS VAT,  \r\n" + 
				"					SUM( ROUND(DONGIA*SOLUONG, 0)-HD_SP.CHIETKHAU + ROUND((ROUND(DONGIA*SOLUONG, 0)-HD_SP.CHIETKHAU)*HD_SP.VAT/100, 0)) AS TONGTIENAVAT,  \r\n" + 
				"					ISNULL(HD.GHICHU, '') AS GHICHU,  \r\n" + 
				"					MATHANG.TEN MATHANG   \r\n" + 
				"			FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "HOADON HD \r\n" + 
				"			INNER JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "HOADON_SP HD_SP ON HD.PK_SEQ = HD_SP.HOADON_FK  \r\n" + 
				"			LEFT JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "KHACHHANG KH ON KH.PK_SEQ = HD.KHACHHANG_FK    \r\n" + 
				"			INNER JOIN (\r\n" + 
				"				SELECT  HOADON_FK, VAT, MIN(SP.TEN) TEN\r\n" + 
				"				FROM HOADON_SP HDSP1\r\n" + 
				"				INNER JOIN SANPHAM SP ON HDSP1.SANPHAM_FK = SP.PK_SEQ\r\n" + 
				"				WHERE THANHTIEN = (SELECT MAX(THANHTIEN) FROM HOADON_SP HDSP2 WHERE HDSP2.HOADON_FK = HDSP1.HOADON_FK AND HDSP2.VAT = HDSP1.VAT)\r\n" + 
				"				GROUP BY HOADON_FK, VAT\r\n" + 
				"			)  MATHANG ON MATHANG.HOADON_FK = HD.PK_SEQ AND MATHANG.VAT = HD_SP.VAT \n"+
				"			WHERE HD.PK_SEQ IN (SELECT DISTINCT SOCHUNGTU FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_PHATSINHKETOAN WHERE MONTH(NGAYGHINHAN) = "+obj.getMonth()+" AND YEAR(NGAYGHINHAN) ="+obj.getYear()+" AND \r\n" + 
				"				LOAICHUNGTU = N'Hóa đơn tài chính' AND (LOAIHD = N'HDCN_OTC' OR LOAIHD = N'HDQ_OTC') AND CO > 0 \r\n" + 
				"				AND	TAIKHOAN_FK IN (SELECT PK_SEQ FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_TAIKHOANKT WHERE NPP_FK IN  (" + obj.getErpCongtyId() + ") AND SOHIEUTAIKHOAN LIKE '3331%')) AND HD.LOAIHOADON = 0  \r\n" + 
				"			GROUP BY HD_SP.VAT, HD.PK_SEQ, HD.KYHIEU, HD.SOHOADON, HD.NGAYXUATHD, KH.PK_SEQ, KH.TEN, KH.MASOTHUE, HD.GHICHU, MATHANG.TEN \r\n" + 
				"		--END HOA DON TAI CHINH KHACH HANG CHI NHANH (DMS) \n";
					sql+="	UNION ALL \n" + */
				"--BEGIN HOA DON TAI CHINH NHA PHAN PHOI (DMS) NHUNG DUOC HACH TOAN VAO HO \n" + 
				"		SELECT N'HÓA ĐƠN TÀI CHÍNH HO' LOAIHOADON, HD.NGAYXUATHD + ' - ' + CONVERT(VARCHAR, HD.PK_SEQ) AS SOCHUNGTU, ISNULL(HD.KYHIEU, '') KYHIEU, ISNULL(HD.SOHOADON, '') AS SOHOADON, HD.NGAYXUATHD AS NGAYHOADON,  \r\n" + 
				"				ISNULL(HD.KHACHHANG_FK, HD.NPP_FK) AS KH_FK, NPP.TEN AS TENNGUOIMUA, NPP.MASOTHUE AS MASOTHUE, \r\n" + 
				"				SUM(ROUND(HD_SP.THANHTIEN, 0)) AS TONGTIENBVAT,  \r\n" + 
				"				HD_SP.VAT AS THUESUAT,\r\n" + 
				"				SUM( ROUND(ROUND(HD_SP.THANHTIEN, 0)*HD_SP.VAT/100, 0) ) AS VAT,  \r\n" + 
				"				SUM(ROUND(HD_SP.THANHTIEN, 0)) + SUM( ROUND(ROUND(HD_SP.THANHTIEN, 0)*HD_SP.VAT/100, 0) ) AS TONGTIENAVAT,  \r\n" + 
				"				ISNULL(HD.GHICHU, '') AS GHICHU,  \r\n" + 
				"				(SELECT TOP 1 D.TEN FROM ERP_HOADON_SP HDMH \r\n" + 
				"					INNER JOIN  ERP_SANPHAM D ON HDMH.SANPHAM_FK = D.PK_SEQ  \r\n" + 
				"					WHERE HOADON_FK = HD.PK_SEQ AND HDMH.VAT = HD_SP.VAT AND THANHTIEN =  \r\n" + 
				"					(SELECT MAX( THANHTIEN) FROM  ERP_HOADON_SP HDMH WHERE HOADON_FK = HD.PK_SEQ AND HDMH.VAT = HD_SP.VAT) \r\n" + 
				"				) MATHANG   \r\n" + 
				"		FROM  ERP_HOADON HD \r\n" + 
				"		INNER JOIN  ERP_HOADON_SP HD_SP ON HD.PK_SEQ = HD_SP.HOADON_FK\r\n" + 
				"		INNER JOIN  NHAPHANPHOI NPP ON HD.NPP_FK = NPP.PK_SEQ  \n" + 
				"			WHERE HD.PK_SEQ IN (SELECT DISTINCT SOCHUNGTU FROM  ERP_PHATSINHKETOAN WHERE MONTH(NGAYGHINHAN) = "+obj.getMonth()+" AND YEAR(NGAYGHINHAN) ="+obj.getYear()+" AND \n" + 
				"				LOAICHUNGTU = N'Hóa đơn tài chính' AND LOAIHD = N'HDHO' \n" + 
				"				AND	TAIKHOAN_FK IN (SELECT PK_SEQ FROM  ERP_TAIKHOANKT WHERE NPP_FK IN  (" + obj.getErpCongtyId() + ")  AND SOHIEUTAIKHOAN LIKE '3331%')) AND HD.LOAIHOADON = 0 \n" + 
				"		GROUP BY HD_SP.VAT, HD.PK_SEQ, HD.KYHIEU, HD.SOHOADON, HD.NGAYXUATHD, HD.KHACHHANG_FK, HD.NPP_FK, NPP.TEN, NPP.MASOTHUE, HD.GHICHU \n"+
				"		--END HOA DON TAI CHINH NHA PHAN PHOI  CHI NHANH (DMS)  NHUNG DUOC HACH TOAN VAO HO \n"+
				/*
				"	UNION ALL \n" + 
				"		--BEGIN HANG TRA LAI HO \n" + 
				"		SELECT N'HÓA ĐƠN TRẢ HÀNG HO' LOAIHOADON, A.NGAYTRA + ' - ' + A.PK_SEQ AS SOCHUNGTU, A.PK_SEQ, ISNULL(KYHIEU, '') AS KYHIEU, ISNULL(SOHOADON, '') AS SOHOADON, A.NGAYTRA, B.PK_SEQ , \r\n" + 
				"		B.TEN AS TENNGNUOIMUA, B.MASOTHUE, \r\n" + 
				"		-SUM(ROUND(DONGIA*SOLUONG, 0)) AS TONGTIENBVAT,  \r\n" + 
				"		ISNULL(HD_SP.PTVAT, 0) AS THUESUAT, \r\n" + 
				"		-SUM(ROUND(DONGIA*SOLUONG*ISNULL(HD_SP.PTVAT, 0)/100, 0)) AS VAT, \r\n" + 
				"		-( SUM(ROUND(DONGIA*SOLUONG, 0)) + SUM(ROUND(DONGIA*SOLUONG*ISNULL(HD_SP.PTVAT, 0)/100, 0) )) AS TONGTIENAVAT, \r\n" + 
				"		ISNULL(A.GHICHU, '') GHICHU,  \r\n" + 
				"		(		SELECT TOP 1 SP.TEN FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "DONTRAHANG_SP HDMH \r\n" + 
				"					inner join  " + geso.traphaco.center.util.Utility.prefixDMS + "SANPHAM SP on SP.PK_SEQ= HDMH.Sanpham_fk   \r\n" + 
				"					WHERE DONTRAHANG_FK = A.PK_SEQ AND ISNULL(HDMH.PTVAT, 0) = ISNULL(HD_SP.PTVAT, 0) \r\n" + 
				"					GROUP BY  SP.TEN \r\n" + 
				"					HAVING SUM( DONGIA * SOLUONG )=  \r\n" + 
				"					(SELECT MAX( DONGIA*SOLUONG) FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "DONTRAHANG_SP HDMH WHERE DONTRAHANG_FK = A.PK_SEQ AND ISNULL(HDMH.PTVAT, 0) = ISNULL(HD_SP.PTVAT, 0)) \r\n" + 
				"				) MATHANG  \r\n" + 
				"		FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "DONTRAHANG A  \r\n" + 
				"		INNER JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "DONTRAHANG_SP HD_SP ON HD_SP.DONTRAHANG_FK = A.PK_SEQ \r\n" + 
				"		INNER JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "NHAPHANPHOI B ON B.PK_SEQ=A.NPP_FK    \n" + 
				"		WHERE A.TRUCTHUOC_FK IN ("+obj.getErpCongtyId()+") AND MONTH(A.NGAYTRA) = "+obj.getMonth()+" AND YEAR(A.NGAYTRA) ="+obj.getYear()+"  AND A.TRANGTHAI IN (1, 2) \n" + 
				"		GROUP BY A.KYHIEU, A.SOHOADON, A.NGAYTRA, B.PK_SEQ, B.TEN ,  B.MASOTHUE, HD_SP.PTVAT, A.SOTIENBVAT, A.GHICHU, A.PK_SEQ 	 \n"+
					"		--BEGIN HANG TRA LAI HO \n" ;
					
					sql+=  
							"	UNION ALL \n" + 
							"		--BEGIN HOA DON TAI CHINH NHA PHAN PHOI (DMS) NHUNG DUOC HACH TOAN VAO NHA PHAN PHOI  \n" + 
							"		SELECT N'HÓA ĐƠN TÀI CHÍNH ETC CHI NHÁNH' LOAIHOADON, HD.NGAYXUATHD + ' - ' + CONVERT(VARCHAR, HD.PK_SEQ) AS SOCHUNGTU, ISNULL(HD.KYHIEU, '') KYHIEU, ISNULL(HD.SOHOADON, '') AS SOHOADON, HD.NGAYXUATHD AS NGAYHOADON,  \r\n" + 
							"				ISNULL(HD.KHACHHANG_FK, HD.NPP_DAT_FK) AS KH_FK, \r\n" + 
							"				CASE WHEN NPP_DAT_FK IS NULL THEN KH.TEN ELSE NPP.TEN END AS TENNGUOIMUA,\r\n" + 
							"				CASE WHEN NPP_DAT_FK IS NULL THEN KH.MASOTHUE ELSE NPP.MASOTHUE END AS MASOTHUE,\r\n" + 
							"				SUM(ROUND(HD_SP.THANHTIEN, 0)) AS TONGTIENBVAT,  \r\n" + 
							"				HD_SP.VAT AS THUESUAT,\r\n" + 
							"				SUM( ROUND(ROUND(HD_SP.THANHTIEN, 0)*HD_SP.VAT/100, 0) ) AS VAT,  \r\n" + 
							"				SUM(ROUND(HD_SP.THANHTIEN, 0)) + SUM( ROUND(ROUND(HD_SP.THANHTIEN, 0)*HD_SP.VAT/100, 0) ) AS TONGTIENAVAT,\r\n" + 
							"				ISNULL(HD.GHICHU, '') AS GHICHU,  \r\n" + 
							"				(SELECT TOP 1 D.TEN FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_HOADONNPP_SP HDMH \r\n" + 
							"					INNER JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "SANPHAM D ON HDMH.SANPHAM_FK = D.PK_SEQ  \r\n" + 
							"					WHERE HOADON_FK = HD.PK_SEQ AND HDMH.VAT = HD_SP.VAT AND (THANHTIEN) =  \r\n" + 
							"					(SELECT MAX( THANHTIEN) FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_HOADONNPP_SP HDMH WHERE HOADON_FK = HD.PK_SEQ AND HDMH.VAT = HD_SP.VAT) \r\n" + 
							"				) MATHANG      \r\n" + 
							"		FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_HOADONNPP HD \r\n" + 
							"		INNER JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_HOADONNPP_SP HD_SP ON HD.PK_SEQ = HD_SP.HOADON_FK\r\n" + 
							"		LEFT JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "KHACHHANG KH ON KH.PK_SEQ = KHACHHANG_FK AND NPP_DAT_FK IS NULL   \r\n" + 
							"		LEFT JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "NHAPHANPHOI NPP ON NPP.PK_SEQ = NPP_DAT_FK AND KHACHHANG_FK IS NULL     \n" + 
							"			WHERE HD.PK_SEQ IN (SELECT DISTINCT SOCHUNGTU FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_PHATSINHKETOAN WHERE MONTH(NGAYGHINHAN) = "+obj.getMonth()+" AND YEAR(NGAYGHINHAN) ="+obj.getYear()+" AND \n" + 
							"				LOAICHUNGTU = N'Hóa đơn tài chính' AND (LOAIHD = N'HDCN_ETC' OR LOAIHD = N'HDQ_ETC') \n" + 
							"				AND	TAIKHOAN_FK IN (SELECT PK_SEQ FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_TAIKHOANKT WHERE NPP_FK IN  (" + obj.getErpCongtyId() + ") AND SOHIEUTAIKHOAN LIKE '3331%' )) AND HD.LOAIHOADON = 0 \n" + 
							"		GROUP BY HD_SP.VAT, HD.PK_SEQ, HD.KYHIEU, HD.SOHOADON, HD.NGAYXUATHD, \r\n" + 
							"		HD.KHACHHANG_FK, HD.NPP_DAT_FK, KH.TEN, NPP.TEN, KH.MASOTHUE, NPP.MASOTHUE, HD.GHICHU \n"+
							"		--END HOA DON TAI CHINH NHA PHAN PHOI  CHI NHANH (DMS) NHUNG DUOC HACH TOAN VAO NHA PHAN PHOI  \n";
				
				sql+=
				"	UNION ALL \n" + 
				"		--BEGIN HANG TRA LAI DMS \n" + 
				"		SELECT N'HÓA ĐƠN HÀNG TRẢ LẠI CHI NHÁNH' LOAIHOADON, HD.NGAYTRA + ' - ' + CONVERT(VARCHAR, HD.PK_SEQ) AS SOCHUNGTU, ISNULL(HD.KYHIEU, '') KYHIEU, ISNULL(HD.SOHOADON, '') AS SOHOADON, HD.NGAYTRA AS NGAYHOADON,  \r\n" + 
				"				CASE WHEN KHACHHANG_FK IS NOT NULL THEN KH.PK_SEQ ELSE NPP.PK_SEQ END AS KH_FK, \r\n" + 
				"				CASE WHEN KHACHHANG_FK IS NOT NULL THEN KH.TEN ELSE NPP.TEN END AS TENNGUOIMUA, \r\n" + 
				"				CASE WHEN KHACHHANG_FK IS NOT NULL THEN KH.MASOTHUE ELSE NPP.MASOTHUE END AS MASOTHUE, \r\n" + 
				"				-( ROUND(SUM(DONGIA*SOLUONG ), 0) ) AS TONGTIENBVAT,  \r\n" + 
				"				HD_SP.VAT THUESUAT,   \r\n" + 
				"				-   ROUND(SUM(DONGIA*SOLUONG*HD_SP.VAT/100), 0)   AS VAT,  \r\n" + 
				"				-( SUM(ROUND(DONGIA*SOLUONG, 0)) + ROUND(SUM(DONGIA*SOLUONG*HD_SP.VAT/100), 0) ) AS TONGTIENAVAT, \r\n" + 
				"				ISNULL(HD.GHICHU, '') AS GHICHU,  \r\n" + 
				"				(SELECT TOP 1 SP.TEN FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "Erp_HangTraLaiNpp_SanPham HDMH \r\n" + 
				"					inner join SANPHAM SP on SP.PK_SEQ= HDMH.Sanpham_fk   \r\n" + 
				"					WHERE HangTraLai_fk = HD.PK_SEQ AND VAT = HD_SP.VAT AND  (DONGIA)*(SOLUONG) =  \r\n" + 
				"					(SELECT MAX( DONGIA*SOLUONG) FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "Erp_HangTraLaiNpp_SanPham HDMH WHERE HangTraLai_fk = HD.PK_SEQ AND VAT = HD_SP.VAT) \r\n" + 
				"				) MATHANG   \r\n" + 
				"		FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "Erp_HangTraLaiNpp HD \r\n" + 
				"		INNER JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "Erp_HangTraLaiNpp_SanPham HD_SP ON HD.PK_SEQ = HD_SP.HANGTRALAI_FK\r\n" + 
				"		LEFT JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "KHACHHANG KH ON HD.KHACHHANG_FK = KH.PK_SEQ \r\n" + 
				"		LEFT JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "NHAPHANPHOI NPP ON HD.NPPTRA_FK = NPP.PK_SEQ    \n" + 
				"		WHERE HD.NPP_FK IN  (" + obj.getErpCongtyId() + ") AND MONTH(HD.NGAYTRA) = "+obj.getMonth()+" AND YEAR(HD.NGAYTRA) ="+obj.getYear()+" AND HD.TRANGTHAI = 1 \n" + 
				"		GROUP BY HD_SP.VAT, HD.PK_SEQ, HD.KYHIEU, HD.SOHOADON, HD.NGAYTRA, \r\n" + 
				"		HD.KHACHHANG_FK, HD.NPPTRA_FK, KH.PK_SEQ, NPP.PK_SEQ, KH.TEN, NPP.TEN, KH.MASOTHUE, NPP.MASOTHUE, HD.GHICHU \n"	+	
				"		--END HANG TRA LAI DMS \n" + 
				"	UNION ALL \n" + 
				"		--BEGIN BUT TOAN TONG HOP  \n" + 
				" 		SELECT DISTINCT N'BÚT TOÁN TỔNG HỢP ERP' LOAIHOADON, BTTH.NGAYBUTTOAN + ' - ' + CONVERT(VARCHAR, BTTH.PK_SEQ) AS SOCHUNGTU, BTTH.PK_SEQ, isnull(BTTH_HD.KYHIEU, '') KYHIEU, ISNULL(BTTH_HD.SOHOADON,'') SOHOADON, ISNULL(BTTH_HD.NGAYHOADON,'') AS NGAYXUATHD,   \n" + 
				"                  NULL AS KH_FK, ISNULL(BTTH_HD.TENNHACUNGCAP,'') AS TENNGUOIMUA, LTRIM(ISNULL(BTTH_HD.MASOTHUE,'')) MASOTHUE,   \n" + 
				"				   SUM(ISNULL(BTTH_HD.TIENHANG,0)*ISNULL(TIGIA, 1)) AS TONGTIENBVAT, ISNULL(BTTH_HD.THUESUAT,0) AS THUESUAT,   \n" + 
				"				   SUM(ISNULL(BTTH_HD.TIENTHUE,0)*ISNULL(TIGIA, 1)) AS VAT, SUM(ISNULL(BTTH_HD.TONGCONG,0)*ISNULL(TIGIA, 1)) AS TONGTIENAVAT,   \n" + 
				"				   ISNULL(BTTH_HD.GHICHU,'') GHICHU, ISNULL(BTTH_HD.GHICHU,'') MATHANG   \n" + 
				" 		FROM ERP_BUTTOANTONGHOP BTTH    \n" + 
				" 		INNER JOIN ERP_BUTTOANTONGHOP_CHITIET_HOADON BTTH_HD ON BTTH.PK_SEQ = BTTH_HD.BTTH_FK   \n" + 
				" 		WHERE BTTH.PK_SEQ IN \n" + 
				"			 (SELECT DISTINCT SOCHUNGTU FROM ERP_PHATSINHKETOAN WHERE  MONTH(NGAYGHINHAN) = "+obj.getMonth()+" AND YEAR(NGAYGHINHAN) ="+obj.getYear()+" AND \n" + 
				"				LOAICHUNGTU = N'BÚT TOÁN TỔNG HỢP' AND DOITUONG = N'KHÁCH HÀNG' AND CO > 0 \n" + 
				"			 	AND	TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE NPP_FK IN  (" + obj.getErpCongtyId() + ")) \n" + 
				"  		GROUP BY  isnull(BTTH_HD.KYHIEU, ''), ISNULL(BTTH_HD.SOHOADON,'') , ISNULL(BTTH_HD.NGAYHOADON,''), ISNULL(BTTH_HD.TENNHACUNGCAP,''),  LTRIM(ISNULL(BTTH_HD.MASOTHUE,'')),   \n" + 
				" 		ISNULL(BTTH_HD.THUESUAT,0),ISNULL(BTTH_HD.GHICHU,''), BTTH.PK_SEQ \n" + 
				"		--END BUT TOAN TONG HOP   \n" + 
				/*"	UNION ALL \n" + 
				"		--BEGIN BUT TOAN TONG HOP CHI NHANH (DMS) \n" + 
				"		SELECT DISTINCT N'BÚT TOÁN TỔNG HỢP DMS' LOAIHOADON, BTTH.NGAYBUTTOAN + ' - ' + CONVERT(VARCHAR, BTTH.PK_SEQ) AS SOCHUNGTU"+
				"       , STUFF((  SELECT DISTINCT ',' +DANHSACH.NGAYCHUNGTU + ' -- '+ CONVERT(VARCHAR, DANHSACH.SOCHUNGTU)  AS [text()]\r\n" + 
				"		FROM (\r\n" +
				"				SELECT DISTINCT MACHUNGTU as SOCHUNGTU, NGAYBUTTOAN AS NGAYCHUNGTU, \r\n" + 
				"				BTTH_HD.MAUSOHOADON, ISNULL(BTTH_HD.KYHIEU, '') KYHIEU, ISNULL(BTTH_HD.SOHOADON,'') SOHOADON, ISNULL(BTTH_HD.NGAYHOADON,'') AS NGAYXUATHD,    \r\n" + 
				"				NULL AS NCC_FK, ISNULL(BTTH_HD.TENNHACUNGCAP,'') AS TENNGUOIMUA, LTRIM(ISNULL(BTTH_HD.MASOTHUE,'')) MASOTHUE \r\n" + 
				"				FROM ERP_BUTTOANTONGHOP BTTH      \r\n" + 
				"				INNER JOIN ERP_BUTTOANTONGHOP_CHITIET_HOADON BTTH_HD ON BTTH.PK_SEQ = BTTH_HD.BTTH_FK  \r\n" + 
				" 		WHERE BTTH.PK_SEQ IN  \r\n" + 
				"			 (SELECT SOCHUNGTU FROM ERP_PHATSINHKETOAN WHERE MONTH(NGAYGHINHAN) = "+obj.getMonth()+" AND YEAR(NGAYGHINHAN) ="+obj.getYear()+" AND  \r\n" + 
				"				LOAICHUNGTU = N'BÚT TOÁN TỔNG HỢP' AND NO > 0  \r\n" + 
				"			 	AND	TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE NPP_FK IN  ("+obj.getErpCongtyId()+"))  \r\n" + 
				"			) DANHSACH\r\n" + 
				"			WHERE DANHSACH.MAUSOHOADON = BTTH_HD.MAUSOHOADON AND DANHSACH.SOHOADON = BTTH_HD.SOHOADON AND DANHSACH.KYHIEU = BTTH_HD.KYHIEU AND DANHSACH.NGAYXUATHD = BTTH_HD.NGAYHOADON AND DANHSACH.MASOTHUE = BTTH_HD.MASOTHUE\r\n" + 
				"			FOR XML PATH('') \r\n" + 
				"			), 1, 1, '' ) PK_SEQ, \r\n" + 
				"	isnull(BTTH_HD.KYHIEU, '') KYHIEU, ISNULL(BTTH_HD.SOHOADON,'') SOHOADON, ISNULL(BTTH_HD.NGAYHOADON,'') AS NGAYXUATHD,   \n" + 
				"                  NULL AS KH_FK, ISNULL(BTTH_HD.TENNHACUNGCAP,'') AS TENNGUOIMUA, LTRIM(ISNULL(BTTH_HD.MASOTHUE,'')) MASOTHUE,   \n" + 
				"				   SUM(ISNULL(BTTH_HD.TIENHANG,0)*ISNULL(TIGIA, 1)) AS TONGTIENBVAT, ISNULL(BTTH_HD.THUESUAT,0) AS THUESUAT,   \n" + 
				"				   SUM(ISNULL(BTTH_HD.TIENTHUE,0)*ISNULL(TIGIA, 1)) AS VAT, SUM(ISNULL(BTTH_HD.TONGCONG,0)*ISNULL(TIGIA, 1)) AS TONGTIENAVAT,   \n" + 
				"				   ISNULL(BTTH_HD.GHICHU,'') GHICHU, ISNULL(BTTH_HD.GHICHU,'') MATHANG   \n" + 
				" 		FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_BUTTOANTONGHOP BTTH    \n" + 
				" 		INNER JOIN  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_BUTTOANTONGHOP_CHITIET_HOADON BTTH_HD ON BTTH.PK_SEQ = BTTH_HD.BTTH_FK   \n" + 
				" 		WHERE BTTH.PK_SEQ IN \n" + 
				"			 (SELECT DISTINCT SOCHUNGTU FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_PHATSINHKETOAN WHERE  MONTH(NGAYGHINHAN) = "+obj.getMonth()+" AND YEAR(NGAYGHINHAN) ="+obj.getYear()+" AND \n" + 
				"				LOAICHUNGTU = N'BÚT TOÁN TỔNG HỢP' AND DOITUONG = N'KHÁCH HÀNG' AND CO > 0 \n" + 
				"			 	AND	TAIKHOAN_FK IN (SELECT PK_SEQ FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "ERP_TAIKHOANKT WHERE NPP_FK IN  (" + obj.getErpCongtyId() + "))) \n" + 
				"  		GROUP BY  isnull(BTTH_HD.KYHIEU, ''), ISNULL(BTTH_HD.SOHOADON,'') , ISNULL(BTTH_HD.NGAYHOADON,''), ISNULL(BTTH_HD.TENNHACUNGCAP,''),  LTRIM(ISNULL(BTTH_HD.MASOTHUE,'')),   \n" + 
				" 		ISNULL(BTTH_HD.THUESUAT,0),ISNULL(BTTH_HD.GHICHU,''), BTTH.PK_SEQ \n" + 
				"		--END BUT TOAN TONG HOP  CHI NHANH (DMS)  \n" +  
				"	UNION ALL  \n" + */
				"		--BEGIN HOA DON KHAC ERP \n" + 
				"		SELECT DISTINCT  N'HÓA ĐƠN KHÁC' LOAIHOADON, HD.NGAYHOADON + ' - ' + CONVERT(VARCHAR, HD.PK_SEQ) AS SOCHUNGTU, isnull(HD.KYHIEUHOADON, '') KYHIEU, ISNULL(HD.SOHOADON,'') SOHOADON,  \n" + 
				"		ISNULL(HD.NGAYHOADON,'') AS NGAYXUATHD,    \n" + 
				"                  HD.KHACHHANG_FK AS KH_FK, ISNULL(NPP.TEN,'') AS TENNGUOIMUA, LTRIM(ISNULL(NPP.MASOTHUE,'')) MASOTHUE,    \n" + 
				"				   BVAT AS TONGTIENBVAT, VAT AS THUESUAT,    \n" + 
				"				   BVAT*VAT/100 AS VAT, AVAT AS TONGTIENAVAT,    \n" + 
				"				   '' GHICHU, HD.DIENGIAI    \n" + 
				" 		FROM ERP_HOADONKHAC HD     \n" + 
				" 		INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = HD.KHACHHANG_FK   \n" + 
				" 		WHERE HD.PK_SEQ IN  \n" + 
				"			 (SELECT DISTINCT SOCHUNGTU FROM ERP_PHATSINHKETOAN WHERE  MONTH(NGAYGHINHAN) = "+obj.getMonth()+" AND YEAR(NGAYGHINHAN) ="+obj.getYear()+" AND  \n" + 
				"				LOAICHUNGTU = N'HÓA ĐƠN KHÁC'   \n" + 
				"			 	AND	TAIKHOAN_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE NPP_FK IN  (" + obj.getErpCongtyId() + ")))	  \n" + 
				"		--END HOA DON KHAC ERP \n"+
				") A \n" + 
				"ORDER BY  NGAYHOADON ASC \n"+
				"END  \n";
//			ResultSet rs = null;
			System.out.println("::: TAO BANG TAM:\n " + sql);
			
			String [] param = new String[4];
			param[0] = obj.getnppId();
			param[1] = obj.getMonth();
			param[2] = obj.getYear();
			param[3] = obj.getNhomKhId();
			db.get(" EXEC REPORT_BANGKEBANRA_V1 '"+obj.getErpCongtyId()+"','"+obj.gettungay()+"','"+obj.getdenngay()+"' ,'"+obj.getNhomKhId()+"'")			;
			/*try{
				sql = "select * from #Tb_ufn_bangkedauraERP";
				System.out.println("BC Thue: " + sql);
			  	rs =db.get(sql);
			}catch (Exception e) {
				e.printStackTrace();
				
			}
			return rs;*/
	}
	
	private void deleteBangTam(dbutils db) {
		String sql = 
				"DELETE Tb_ufn_bangkedauraERP ";
		db.update(sql);
		
		
	}
	private ResultSet queryBangTam(dbutils db, int giaTriThue) {
		String sql = "";
		ResultSet rs = null;
		try{
			sql = " select * from Tb_ufn_bangkedauraERP where THUESUAT = "+giaTriThue;
			System.out.println("BC Thue: " + sql);
		  	rs =db.get(sql);
		}catch (Exception e) {
			e.printStackTrace();
			
		}
		return rs;
	}
	
	public String catChuoiDayChungTu (String chuoi) {
		String daySoChungTu = "";
		if (chuoi != null) {
			String day = chuoi.trim();
			if (day.indexOf(",") > -1) {
				String[] thongTinChungTuArray = day.split(",");
				for (String thongTinChungTu : thongTinChungTuArray) {
					if (thongTinChungTu.indexOf(" -- ") > -1) {
						daySoChungTu += ( daySoChungTu.length() > 0 ) ? ", "+thongTinChungTu.split(" -- ")[1] : thongTinChungTu.split(" -- ")[1] ;
					} else {
						daySoChungTu += thongTinChungTu;
					}
				}
			} else {
				if (day.indexOf(" -- ") > -1) {
					daySoChungTu += ( daySoChungTu.length() > 0 ) ? ", "+day.split(" -- ")[1] : day.split(" -- ")[1] ;
				} else {
					daySoChungTu += day;
				}
			}
		}
		return daySoChungTu;
	}
	
	private void setCellBorderStyle(Cell cell, short alignment) 
	{
		Style style = cell.getStyle();
		//style.setHAlignment(HorizontalAlignmentType.CENTRED);
		style.setHAlignment(alignment);
		/*style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);*/
		cell.setStyle(style);
	}
}