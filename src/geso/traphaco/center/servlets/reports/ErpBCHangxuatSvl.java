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

public class ErpBCHangxuatSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
    public ErpBCHangxuatSvl() {
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
				
		String nextJSP = "/TraphacoHYERP/pages/Center/ErpBCHangXuatKho.jsp";
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
	    	
			
			String nextJSP = "/TraphacoHYERP/pages/Center/ErpBCHangXuatKho.jsp";
			response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=HangXuatKho.xlsm");
			
	    	boolean isTrue = false;
			try 
			{
				isTrue = CreatePivotTable(out, obj, "");	
			} 
			catch (Exception e) 
			{
				isTrue = false;
			}
			
			if(!isTrue)
			{
				obj.createRs();
				session.setAttribute("obj", obj);
				obj.setMsg("Không thể tạo báo cáo");
				
				String nextJSP = "/TraphacoHYERP/pages/Center/ErpBCHangXuatKho.jsp";
				response.sendRedirect(nextJSP);
			}
	    }
	}
	

	private boolean CreatePivotTable(OutputStream out, IBaocao obj, String condition) throws Exception
    {   
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();		
		
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ErpBCHangxuatKho.xlsm");

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
	    
	    String tieude = "BÁO CÁO HÀNG XUẤT KHO";
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
	    cell = cells.getCell("AB1"); 	cell.setValue("ChiNhanh/DoiTac"); ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AC1"); 	cell.setValue("SoChungTu"); ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AD1"); 	cell.setValue("NgayChungTu"); ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AE1"); 	cell.setValue("LoaiXuatChuyen"); ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AF1"); 	cell.setValue("TrangThai"); ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AG1"); 	cell.setValue("MaSanPham");	   ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AH1"); 	cell.setValue("TenSanPham");	   ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AI1"); 	cell.setValue("DonVi");	   ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AJ1"); 	cell.setValue("SCHEME");	   ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AK1"); 	cell.setValue("SoLo");	   ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AL1"); 	cell.setValue("SoLuongThung");	   ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AM1"); 	cell.setValue("SoLuongLe");	   ReportAPI.setCellHeader(cell);
	}
	
	private boolean CreateStaticData(Workbook workbook, IBaocao obj, String condition) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
		
		String query =  "select e.ten as khoTEN,npp.TEN as tennpp, a.pk_seq as sochungtu, a.ngayyeucau as ngaychungtu, N'xuất bán' as LoaiXuatChuyen,  " +
						"	case a.trangthai when 1 then N'Chưa chốt' when 2 then N'Đã xuất kho' else '' end as trangthai, c.MA, c.TEN, d.DONVI, b.solo,  " +
						"	ISNULL( ( select SOLUONG1 from QUYCACH where sanpham_fk = c.PK_SEQ and DVDL2_FK = '100018' ), 1 ) as quycach, b.soluong, b.SCHEME,  " +
						"	isnull(c.TRONGLUONG, 0) as TRONGLUONG, isnull(c.THETICH, 0) as THETICH  " +
						" from ERP_YCXUATKHO a inner join ERP_YCXUATKHO_SANPHAM_CHITIET b on a.PK_SEQ = b.ycxk_fk  " +
						"	inner join SANPHAM c on b.sanpham_fk = c.PK_SEQ  " +
						"	left join DONVIDOLUONG d on c.DVDL_FK = d.PK_SEQ  " +
						"	inner join ERP_KHOTT e on a.kho_fk = e.pk_seq " +
						" inner join NHAPHANPHOI npp on a.NPP_FK=npp.PK_SEQ " +
						"where a.trangthai >= '1' " ;

		if(obj.getTuNgay().length() > 0)
			query += " and a.ngayyeucau >= '" + obj.getTuNgay() + "' ";
		
		if(obj.getDenNgay().length() > 0)
			query += " and a.ngayyeucau <= '" + obj.getDenNgay() + "' ";
		
		if(obj.getKhoIds().length() > 0)
			query += " and a.kho_fk = '" + obj.getKhoIds() + "' ";
		
		Utility util =new Utility(); 
		query+=  " AND  a.kho_fk IN "+util.quyen_khoTT(obj.getUserId())+" ";
		
		query+=  " AND  a.npp_fk IN "+util.quyen_npp(obj.getUserId())+" ";
		
		
		query += " order by a.ngayyeucau asc";
	
		System.out.println("1.Bao cao hang nhap xuat: " + query);
		ResultSet rs = db.get(query);
		
		int i = 2;
		if(rs!=null)
		{
			try 
			{
				Cell cell = null;
				while(rs.next())// lap den cuoi bang du lieu
				{
					String kho = rs.getString("khoTen");
					String tenNpp= rs.getString("tennpp");
					String sochungtu = rs.getString("sochungtu");
					String ngaychungtu = rs.getString("ngaychungtu");
					String loaixuatchuyen = rs.getString("LoaiXuatChuyen");
					String trangthai = rs.getString("trangthai");
					String maSp = rs.getString("Ma");
					String tenSp = rs.getString("Ten");		
					String donvi = rs.getString("donvi");	
					String scheme = rs.getString("SCHEME");	
					String solo = rs.getString("SOLO");	
					
					double soLUONG = rs.getDouble("soluong");
					double quyCACH = rs.getDouble("quycach");
					
					double thung = Math.round( (soLUONG / quyCACH) - 0.5 );
					double le = Math.round( (soLUONG % quyCACH) - 0.5 );

					cell = cells.getCell("AA" + Integer.toString(i)); 	cell.setValue(kho);
					cell = cells.getCell("AB" + Integer.toString(i)); 	cell.setValue(tenNpp);
					cell = cells.getCell("AC" + Integer.toString(i)); 	cell.setValue(sochungtu);
					cell = cells.getCell("AD" + Integer.toString(i)); 	cell.setValue(ngaychungtu);
					cell = cells.getCell("AE" + Integer.toString(i)); 	cell.setValue(loaixuatchuyen);
					cell = cells.getCell("AF" + Integer.toString(i)); 	cell.setValue(trangthai);
					cell = cells.getCell("AG" + Integer.toString(i)); 	cell.setValue(maSp);
					cell = cells.getCell("AH" + Integer.toString(i)); 	cell.setValue(tenSp);
					cell = cells.getCell("AI" + Integer.toString(i)); 	cell.setValue(donvi);
					cell = cells.getCell("AJ" + Integer.toString(i)); 	cell.setValue(scheme);
					cell = cells.getCell("AK" + Integer.toString(i)); 	cell.setValue(solo);
					cell = cells.getCell("AL" + Integer.toString(i)); 	cell.setValue(thung);
					cell = cells.getCell("AM" + Integer.toString(i)); 	cell.setValue(le);
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

