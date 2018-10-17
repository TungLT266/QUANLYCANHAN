package geso.traphaco.erp.servlets.baocao;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.dms.distributor.util.Utility;
import geso.traphaco.erp.beans.baocao.IBaocao;
import geso.traphaco.erp.beans.baocao.imp.Baocao;
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

public class ErpBCHangluanchuyenSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
    public ErpBCHangluanchuyenSvl() {
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
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCHangChamLuanChuyen.jsp";
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
			
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCHangChamLuanChuyen.jsp";
			response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=HangChamLuanChuyen.xlsm");
			
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
				
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCHangChamLuanChuyen.jsp";
				response.sendRedirect(nextJSP);
			}
	    }
	}
	

	private boolean CreatePivotTable(OutputStream out, IBaocao obj, String condition) throws Exception
    {   
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();		
		
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ErpBCHangluanchuyen.xlsm");

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
	    
	    String tieude = "BÁO CÁO HÀNG CHẬM LUÂN CHUYỂN";
	    ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);
	           
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A3");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A4");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + UserName);
	    

	    //tieu de, hang dau tien cua bang du lieu, cell la toa do cua exel
	    cell = cells.getCell("DA1"); 	cell.setValue("Kho");   ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("DB1"); 	cell.setValue("ViTri");   ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("DC1"); 	cell.setValue("Bean");   ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("DD1"); 	cell.setValue("MaSanPham");	   ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("DE1"); 	cell.setValue("TenSanPham");	   ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("DF1"); 	cell.setValue("DonVi");	   ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("DG1"); 	cell.setValue("ChungLoai");	   ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("DH1"); 	cell.setValue("LoaiSanPham");	   ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("DI1"); 	cell.setValue("SoLo");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("DJ1"); 	cell.setValue("SoLuong");	   ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("DK1"); 	cell.setValue("NgayHetHan");	   ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("DL1"); 	cell.setValue("SoNgayKoLuanChuyen");	   ReportAPI.setCellHeader(cell);
	    
	}
	
	private boolean CreateStaticData(Workbook workbook, IBaocao obj, String condition) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
		
		String query = "select g.TEN as khoTen, i.TEN as khuTen, h.TEN as vitri, c.MA as spMa, c.TEN as spTen, d.DONVI, a.SOLO, b.SoLuong," +
							" b.NGAYHETHAN, a.SoNgayKoLuanChuyen,  e.TEN as clten, f.TEN as lspten " +
				"from " +
				"( " +
					"select DATEDIFF(dd, max(a.NGAYCHOT), '" + obj.getTuNgay() + "') as SoNgayKoLuanChuyen, a.KHO_FK as khoId, b.BEAN, b.SOLO, b.SANPHAM_FK  " +
					"from ERP_XUATKHO a inner join ERP_XUATKHO_SP_CHITIET b on a.PK_SEQ = b.XUATKHO_FK " +
					"where a.TRANGTHAI = '1' " +
					"group by a.KHO_FK,  b.BEAN, b.SOLO, b.NGAYHETHAN, b.SANPHAM_FK  " +
					"having DATEDIFF(dd, max(a.NGAYCHOT), '" + obj.getTuNgay() + "') >= '" + obj.getDenNgay() + "' " +
				") " +
				"a inner join ERP_KHOTT_SP_CHITIET b on  a.BEAN = b.BIN and a.khoId = b.KHOTT_FK and a.SOLO = b.SOLO  " +
				"inner join ERP_SANPHAM c on a.SANPHAM_FK = c.PK_SEQ  " +
				"inner join DONVIDOLUONG d on c.DVDL_FK = d.PK_SEQ  " +
				"left join CHUNGLOAI e on c.CHUNGLOAI_FK = e.PK_SEQ " +
				"inner join ERP_LOAISANPHAM f on c.LOAISANPHAM_FK = f.PK_SEQ " +
				"inner join ERP_KHOTT g on b.KHOTT_FK = g.PK_SEQ  " +
				"inner join ERP_VITRIKHO h on b.BIN = h.PK_SEQ " +
				"inner join ERP_KHUVUCKHO i on h.KHU_FK = i.PK_SEQ" ;
		
		
		if(obj.getKhoIds().length() > 0)
			query += " and g.pk_seq = '" + obj.getKhoIds() + "' ";
		
		if(obj.getChungloaiIds().length() > 0)
			query += " and e.pk_seq in (" + obj.getChungloaiIds() + ") ";
		
		if(obj.getLoaiSanPhamIds().length() > 0)
			query += " and c.loaisanpham_fk = '" + obj.getLoaiSanPhamIds() + "' ";
		
		if(obj.getSanPhamIds().length() > 0)
			query += " and c.pk_seq in (" + obj.getSanPhamIds() + ") ";
	
		System.out.println("1.Bao cao hang luan chuyen: " + query);
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
				
				Cell cell = null;
				while(rs.next())// lap den cuoi bang du lieu
				{
					String kho = rs.getString("khoTen");
					String vitri = rs.getString("khuTen");
					String bean = rs.getString("vitri");
					String maSp = rs.getString("spMa");
					String tenSp = rs.getString("spTen");		
					String donvi = rs.getString("donvi");	
					String chungloai = rs.getString("clTen");	
					String loaisp = rs.getString("lspten");
					String solo = rs.getString("SoLo");
					String ngayhethan = rs.getString("ngayhethan");
					
					float soluong = rs.getFloat("SoLuong");
					float ngaykoluanchuyen = rs.getFloat("SoNgayKoLuanChuyen");

					cell = cells.getCell("DA" + Integer.toString(i)); 	cell.setValue(kho);
					cell = cells.getCell("DB" + Integer.toString(i)); 	cell.setValue(vitri);
					cell = cells.getCell("DC" + Integer.toString(i)); 	cell.setValue(bean);
					cell = cells.getCell("DD" + Integer.toString(i)); 	cell.setValue(maSp);
					cell = cells.getCell("DE" + Integer.toString(i)); 	cell.setValue(tenSp);
					cell = cells.getCell("DF" + Integer.toString(i)); 	cell.setValue(donvi);
					cell = cells.getCell("DG" + Integer.toString(i)); 	cell.setValue(chungloai);				
					cell = cells.getCell("DH" + Integer.toString(i)); 	cell.setValue(loaisp);
					cell = cells.getCell("DI" + Integer.toString(i)); 	cell.setValue(solo);
					cell = cells.getCell("DJ" + Integer.toString(i)); 	cell.setValue(soluong); 
					cell = cells.getCell("DK" + Integer.toString(i)); 	cell.setValue(ngayhethan); 
					cell = cells.getCell("DL" + Integer.toString(i)); 	cell.setValue(ngaykoluanchuyen); 
					
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
