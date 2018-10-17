package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.beans.baocao.IBaocao;
import geso.traphaco.center.beans.baocao.imp.Baocao;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class ErpBCHangnhapSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
    public ErpBCHangnhapSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IBaocao obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    obj = new Baocao();
	    obj.setUserId(userId);
	    obj.createRs();
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Center/ErpBCHangNhapKho.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		OutputStream out = response.getOutputStream(); 
		IBaocao obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    
	    
	    String userTen = (String) session.getAttribute("userTen");
	    String userId = request.getParameter("userId");

	    obj = new Baocao();
	    obj.setUserId(userId);
	    obj.setUserTen(userTen);
	    
	    String tungay = request.getParameter("tungay");
	    if(tungay == null)
	    	tungay = "";
	    obj.setTuNgay(tungay);
	    
	    String denngay = request.getParameter("denngay");
	    if(denngay == null)
	    	denngay = "";
	    obj.setDenNgay(denngay);
	    
	    String loaisp = request.getParameter("loaisanpham");
	    if(loaisp == null)
	    	loaisp = "";
	    obj.setLoaiSanPhamIds(loaisp);
	    
	    String khoId = request.getParameter("khoId");
	    if(khoId == null)
	    	khoId = "";
	    obj.setKhoIds(khoId);
	    
	    String khoTen = request.getParameter("khoTen");
	    if(khoTen == null)
	    	khoTen = "";
	    obj.setKhoTen(khoTen);
	    
	    String flag = request.getParameter("flag");
	    if(flag == null)
	    	flag = "0";
	    obj.setFlag(flag);
	    
	    String[] spIds = request.getParameterValues("spIds");
	    String spId = "";
	    if(spIds != null)
	    {
	    	for(int i = 0; i < spIds.length; i++)
	    		spId += spIds[i] + ",";
	    	if(spId.length() > 0)
	    		spId = spId.substring(0, spId.length() - 1);
	    	obj.setSanPhamIds(spId);
	    }
	    
	    String[] clIds = request.getParameterValues("clIds");
	    String clId = "";
	    if(clIds != null)
	    {
	    	for(int i = 0; i < clIds.length; i++)
	    		clId += clIds[i] + ",";
	    	if(clId.length() > 0)
	    		clId = clId.substring(0, clId.length() - 1);
	    	obj.setChungloaiIds(clId);
	    }
	    
	    String action = request.getParameter("action");
	    System.out.println("Action nhan duoc: " + action);
	    
	    if(action.equals("search"))
	    {
	    	obj.createRs();
	    	session.setAttribute("obj", obj);
			
			String nextJSP = "/TraphacoHYERP/pages/Center/ErpBCHangNhapKho.jsp";
			response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=HangNhapKho.xlsm");
			
	    	boolean isTrue = false;
			try 
			{
				isTrue = CreatePivotTable(out, obj, "");	
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				isTrue = false;
			}
			
			if(!isTrue)
			{
				obj.createRs();
				session.setAttribute("obj", obj);
				obj.setMsg("Không thể tạo báo cáo");
				
				String nextJSP = "/TraphacoHYERP/pages/Center/ErpBCHangNhapKho.jsp";
				response.sendRedirect(nextJSP);
			}
	    }
	}
	
	
	private boolean CreatePivotTable(OutputStream out, IBaocao obj, String condition) throws Exception
    {   
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();		
		
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ErpBCHangnhapkho.xlsm");

		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		
	     CreateStaticHeader(workbook, obj.getTuNgay(), obj.getDenNgay(), obj.getUserTen());	     
	     boolean isTrue = CreateStaticData(workbook, obj, condition);
	     if(!isTrue){
	    	 return false;
	     }
	     workbook.save(out);
			
		fstream.close();
	     return true;
   }
	
	private void CreateStaticHeader(Workbook workbook, String dateFrom, String dateTo, String UserName) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    worksheet.setName("Sheet1");
	    
	    Cells cells = worksheet.getCells();
		
	    Style style;
	    Font font = new Font();
	    font.setColor(Color.RED);//mau chu
	    font.setSize(16);// size chu
	   	font.setBold(true);
	   	
	    cells.setRowHeight(0, 20.0f);
	    Cell cell = cells.getCell("A1");
	    style = cell.getStyle();
	    style.setFont(font); 
	    style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu 	  
	    
	    String tieude = "BÁO CÁO HÀNG NHẬP KHO";
	    ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);
	           
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A2");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Từ ngày: " + dateFrom);
	    
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("B2");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Đến ngày: " + dateTo);
	    
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A3");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A4");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + UserName);
	    

	    //tieu de, hang dau tien cua bang du lieu, cell la toa do cua exel
	    cell = cells.getCell("AA1"); 	cell.setValue("Kho");   ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AB1"); 	cell.setValue("SoChungTu"); ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AC1"); 	cell.setValue("NgayChungTu"); ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AD1"); 	cell.setValue("TrangThai"); ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AE1"); 	cell.setValue("MaSanPham");	   ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AF1"); 	cell.setValue("TenSanPham");	   ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AG1"); 	cell.setValue("DonVi");	   ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AH1"); 	cell.setValue("SoLo");	   ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AI1"); 	cell.setValue("SoLuongKien");	   ReportAPI.setCellHeader(cell);
	}
	
	private boolean CreateStaticData(Workbook workbook, IBaocao obj, String condition) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
		
		String query =  "select (select TEN from ERP_KHOTT where pk_seq = a.khonhap_fk ) as khoTEN, a.pk_seq as sochungtu, a.ngayNHAP as ngaychungtu, " +
						"		case a.trangthai when 0 then N'Chưa chốt' else N'Đã chốt' end as trangthai, " +
						"		c.MA, c.TEN, d.DONVI, b.SOLO, b.SOLUONG " +
						"from ERP_NHAPKHO a inner join ERP_NHAPKHO_SANPHAM b on a.pk_seq = b.nhapkho_fk " +
						"	inner join SANPHAM c on b.sanpham_fk = c.pk_seq " +
						"	inner join DONVIDOLUONG d on c.DVDL_FK = d.pk_seq " +
						"where a.pk_seq >= '0' " ;
		
		if(obj.getTuNgay().length() > 0)
			query += " and a.ngayNHAP >= '" + obj.getTuNgay() + "' ";
		
		if(obj.getDenNgay().length() > 0)
			query += " and a.ngayNHAP <= '" + obj.getDenNgay() + "' ";
		
		if(obj.getKhoIds().length() > 0)
			query += " and a.khonhap_fk = '" + obj.getKhoIds() + "' ";
		
		
		Utility util =new Utility(); 
		query+=  " AND  a.khonhap_fk IN "+util.quyen_khoTT(obj.getUserId())+" ";
		
		
		query += " order by a.ngayNHAP asc";
	
		System.out.println("1.Bao cao hang nhap kho: " + query);
		ResultSet rs = db.get(query);
		
		int i = 2;
		if(rs!=null)
		{
			try 
			{
				cells.setColumnWidth(0, 15.0f);
				cells.setColumnWidth(1, 15.0f);
				cells.setColumnWidth(2, 15.0f);
				cells.setColumnWidth(3, 15.0f);
				cells.setColumnWidth(4, 15.0f);			
				cells.setColumnWidth(5, 15.0f);	
				cells.setColumnWidth(6, 15.0f);	
				cells.setColumnWidth(7, 15.0f);
				cells.setColumnWidth(8, 15.0f);
				cells.setColumnWidth(9, 15.0f);
				cells.setColumnWidth(10, 15.0f);
				
				Cell cell = null;
				while(rs.next())// lap den cuoi bang du lieu
				{
					String kho = rs.getString("khoTen");
					String sochungtu = rs.getString("sochungtu");
					String ngaychungtu = rs.getString("ngaychungtu");
					String trangthai = rs.getString("trangthai");
					String maSp = rs.getString("Ma");
					String tenSp = rs.getString("Ten");		
					String donvi = rs.getString("donvi");	
					String solo = rs.getString("SOLO");	
					float soluong = rs.getFloat("SoLuong");

					cell = cells.getCell("AA" + Integer.toString(i)); 	cell.setValue(kho);
					cell = cells.getCell("AB" + Integer.toString(i)); 	cell.setValue(sochungtu);
					cell = cells.getCell("AC" + Integer.toString(i)); 	cell.setValue(ngaychungtu);
					cell = cells.getCell("AD" + Integer.toString(i)); 	cell.setValue(trangthai);
					cell = cells.getCell("AE" + Integer.toString(i)); 	cell.setValue(maSp);
					cell = cells.getCell("AF" + Integer.toString(i)); 	cell.setValue(tenSp);
					cell = cells.getCell("AG" + Integer.toString(i)); 	cell.setValue(donvi);
					cell = cells.getCell("AH" + Integer.toString(i)); 	cell.setValue(solo);
					cell = cells.getCell("AI" + Integer.toString(i)); 	cell.setValue(soluong);
					i++;
				}
				if(rs!=null)
					rs.close();
				if(db != null) 
					db.shutDown();
				if(i==2){
					throw new Exception("Khong co bao cao trong thoi gian nay...");
				}
			
			} catch (Exception e) 
			{
				System.out.println("115.Exception: " + e.getMessage());
				throw new Exception(e.getMessage());
			}
		} else {
			if(db != null) db.shutDown();
			return false;
		}
		
		if(db != null) db.shutDown();
		return true;
		
	}
	

}
