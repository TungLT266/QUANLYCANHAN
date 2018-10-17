package geso.traphaco.center.servlets.daidienkinhdoanh;

import geso.traphaco.center.beans.daidienkinhdoanh.*;
import geso.traphaco.center.beans.daidienkinhdoanh.imp.*;
import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DaidienkinhdoanhSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet 
{
	private static final long serialVersionUID = 1L;
	Utility bodau=new Utility();
   
    public DaidienkinhdoanhSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IDaidienkinhdoanhList obj = new DaidienkinhdoanhList();
	    
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	       	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    PrintWriter out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);

	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    
	    
	    String ddkdId = util.getId(querystring);
	    
	   // System.out.println("Action nek :"+action);
	    if (action.equals("delete"))
	    { 
	    	Delete(ddkdId,obj);
	    	//out.print(ddkdId);
	    }
	    obj.setRequestObj(request);
	    obj.setUserId(userId);
	    //System.out.println("user iad 1: "+userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Center/DaiDienKinhDoanh.jsp";
		response.sendRedirect(nextJSP);
	}

	String query1="";
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IDaidienkinhdoanhList obj = new DaidienkinhdoanhList();
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	   
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId"));
	    String action = request.getParameter("action");
	      //----
	    obj.setUserId(userId);
	    obj.setRequestObj(request);
	    String search = "";
	    String nextJSP = "";
	    if(action.equals("excel"))
	    {
	    	try{
	    	IStockintransit obj1 = new Stockintransit();
    		response.setContentType("application/xlsm");
    		response.setHeader("Content-Disposition", "attachment; filename=DanhSachNVBH(tt).xlsm");
    		OutputStream out1 = response.getOutputStream();
			setQuery(obj1,request);
			ExportToExcel(out1,obj1);
			nextJSP = "/TraphacoHYERP/pages/Center/DaiDienKinhDoanh.jsp";
			response.sendRedirect(nextJSP);
	    	}
	    	catch (Exception e) {
				System.out.println("Không thể xuất excel " + e.getMessage());
			}
	    }
	    if (action.equals("new"))
	    {
	    	// Empty Bean for distributor
	    	IDaidienkinhdoanh ddkdBean = (IDaidienkinhdoanh) new Daidienkinhdoanh("");
	    	ddkdBean.setUserId(userId);
	    	// Save Data into session
	    	session.setAttribute("ddkdBean", ddkdBean);
    		
    		nextJSP = "/TraphacoHYERP/pages/Center/DaiDienKinhDoanhNew.jsp";  		
	    }
	    else if (action.equals("search")){	    
	    	search = getSearchQuery(request,obj);
			obj.setUserId(userId);
			
    		session.setAttribute("abc", search);
	    		
    		nextJSP = "/TraphacoHYERP/pages/Center/DaiDienKinhDoanh.jsp";
	    }
	    else{
		    
		    //phantrang
		    
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");

	    	//------------------------
	    	
	    	search = getSearchQuery(request, obj);

	    	nextJSP = "/TraphacoHYERP/pages/Center/DaiDienKinhDoanh.jsp";
	    }
	    
	    obj.init(search);
	   
    	session.setAttribute("obj", obj);  	
		session.setAttribute("userId", userId);
    		
		response.sendRedirect(nextJSP); 
	}
	
	private String getSearchQuery(HttpServletRequest request,IDaidienkinhdoanhList obj)
	{	
		
		Utility util = new Utility();
		
		String maFAST = util.antiSQLInspection(request.getParameter("maFAST"));
    	if (maFAST == null)
    		maFAST = "";    	
    	obj.setMaFAST(maFAST);
		
		String ten = util.antiSQLInspection(request.getParameter("ddkdTen"));
    	if ( ten == null)
    		ten = "";
    	obj.setTen(ten);
    	
    	String sodienthoai = util.antiSQLInspection(request.getParameter("DienThoai"));
    	if (sodienthoai == null)
    		sodienthoai = "";    	
    	obj.setSodienthoai(sodienthoai);
    	
    	String kbhId = util.antiSQLInspection(request.getParameter("KenhBanHang"));
    	if (kbhId == null)
    		kbhId = "";    	
    	obj.setKbhId(kbhId);
    	
    	String gsbhId = util.antiSQLInspection(request.getParameter("GSBanHang"));
    	if (gsbhId == null)
    		gsbhId = "";    	
    	obj.setGsbhId(gsbhId);
    	
    	String nppId = util.antiSQLInspection(request.getParameter("NhaPhanPhoi"));
    	if (nppId == null)
    		nppId = "";    	
    	obj.setNppId(nppId);
    	
    	String trangthai = util.antiSQLInspection(request.getParameter("TrangThai"));   	
    	if (trangthai == null)
    		trangthai = "";    	
	
    	if (trangthai.equals("2"))
    		trangthai = "";
    	
    	obj.setTrangthai(trangthai);
    	
    	String query =  "select distinct isnull(a.mafast,'') as mafast, a.pk_seq  as id, a.ten , a.dienthoai, a.diachi, a.trangthai, a.ngaytao,   " +
						"	b.ten as nguoitao, a.ngaysua, c.ten as nguoisua,  f.ten as gsbhTen,   " +
						"	g.ten as kbhTen, a.manhanvien AS manhanvien ,isnull(tn.TEN,'') as TienNhiem,  " +
						"	isnull((select REPLACE(  " +
						"				(	SELECT npp.TEN AS [data()]  " +
						"					FROM  NHAPHANPHOI npp inner join DAIDIENKINHDOANH_NPP ddkd_npp on npp.pk_seq = ddkd_npp.npp_fk and  ddkd_npp.DDKD_FK = a.PK_SEQ  " +
						"					FOR XML PATH('p')   " +
						"			),' ',' ')  ), 'Traphaco') as nppTEN " +
						"from daidienkinhdoanh a inner join nhanvien b on a.nguoitao = b.pk_seq   " +
						"	 left join DAIDIENKINHDOANH tn on tn.PK_SEQ=a.DDKDTIENNHIEM  " +
					  	 " left join DAIDIENKINHDOANH_NPP ddkd_npp on a.PK_SEQ=ddkd_npp.ddkd_fk "+
						"	 inner join  nhanvien c on   a.nguoisua = c.pk_seq  " +
						"	 left join ddkd_gsbh e on a.pk_seq = e.ddkd_fk   " +
						"	 left join giamsatbanhang f on e.gsbh_fk = f.pk_seq   " +
						"	 left join  kenhbanhang g on f.kbh_fk=g.pk_seq   " +
						"where 1=1 and f.pk_seq in (select gsbh_fk from nhapp_giamsatbh where NPP_FK in (ddkd_npp.NPP_FK ))   ";
						
    	if (ten.length()>0)
    	{
			query = query + " and a.TIMKIEM like  UPPER( '%"+bodau.replaceAEIOU(ten)+"%')";
    	}
    	
    	if (sodienthoai.length()>0)
    	{
			query = query + " and upper(a.dienthoai) like upper('%" + sodienthoai + "%')";
    	}
    	
   	
    	if (gsbhId.length()>0){
			query = query + " and e.gsbh_fk='" + gsbhId + "'";
			
    	}
    	
    	if (kbhId.length()>0){
			query = query + " and g.pk_seq = '" + kbhId + "'";
			
    	}
    	
    	if (nppId.length()>0){
			query = query + " and a.pk_seq in  ( select ddkd_fk from DAIDIENKINHDOANH_NPP where NPP_FK = '" + nppId + "' )   ";
			
    	}
    	
    	if(trangthai.length() > 0){
    		query = query + " and a.trangthai = '" + trangthai + "'";
    		
    	}
    	
    	if(maFAST.length()>0)
    	{
    		query+= " and a.maFAST like '%"+maFAST+"%' ";
    	}
    	//query = query + "  order by a.ten";
    	System.out.println("Serch   "+query);
    	return query;
	}
	
	private void Delete(String id,IDaidienkinhdoanhList obj)
	{
		dbutils db = new dbutils();
		
			try{
			db.getConnection().setAutoCommit(false);
			String sql="delete from ddkd_gsbh where ddkd_fk = '" + id + "'";
			System.out.println("1." +sql);
			if(!db.update(sql))
			{
				db.update("rollback");
				obj.setMsg("Không thể xóa khi có giám sát");
				db.shutDown();
				return;
			}
			 sql="delete from daidienkinhdoanh where pk_Seq = '" + id + "'";
			 System.out.println("2." +sql);
			
			if(!db.update(sql))
			{
				db.update("rollback");
				System.out.println("Vao rollback");
				obj.setMsg("Không thể xóa khi đã có tuyến");
				db.shutDown();
				return;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
		}catch(Exception  e)
		{
			System.out.println("Loi "+e.toString());
			db.update("rollback");
			obj.setMsg("Lỗi " + e.toString());
			db.shutDown();
			return;
		}
		
	}
	
	private void ExportToExcel(OutputStream out,IStockintransit obj)throws Exception
	 {
		try{ 
			String chuoi=getServletContext().getInitParameter("path") + "\\DanhSachDDKD(tt).xlsm";
			
			FileInputStream fstream = new FileInputStream(chuoi);
			
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			
			CreateHeader(workbook,obj);
			FillData(workbook,obj);
			
			workbook.save(out);	
			fstream.close();
		}catch(Exception ex){
			throw new Exception(ex.getMessage());
		}
		
	}
	 
	private void CreateHeader(Workbook workbook,IStockintransit obj) throws Exception
	{
		try {
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");

			Cells cells = worksheet.getCells();
			cells.setRowHeight(0, 50.0f);
			Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 16,
					"DANH SÁCH NHÂN VIÊN BÁN HÀNG");
			
			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Ngày tạo : "
					+ obj.getDateTime());
			cell = cells.getCell("A4");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10,
					"Người tạo : " + obj.getuserTen());
			
			cell = cells.getCell("EA1");		cell.setValue("MaRSM");
			cell = cells.getCell("EB1");		cell.setValue("TenRSM");
			cell = cells.getCell("EC1");		cell.setValue("MaASM");
			cell = cells.getCell("ED1");		cell.setValue("TenASM");
			cell = cells.getCell("EE1");		cell.setValue("MaGSBH");
			cell = cells.getCell("EF1");		cell.setValue("TenGSBH");
			cell = cells.getCell("EG1");		cell.setValue("MaNPP");
			cell = cells.getCell("EH1");		cell.setValue("TenNPP");
			cell = cells.getCell("EI1");		cell.setValue("MaNhanVien");
			cell = cells.getCell("EJ1");		cell.setValue("TenNhanVien");
			cell = cells.getCell("EK1");		cell.setValue("DiaChi");
			cell = cells.getCell("EL1");		cell.setValue("PhanTramChuyen");
			cell = cells.getCell("EM1");		cell.setValue("SoNamLamViec");
			cell = cells.getCell("EN1");		cell.setValue("DienThoai");
			cell = cells.getCell("EO1");		cell.setValue("NgayVaoCongTy");
			cell = cells.getCell("EP1");		cell.setValue("HDLD");
			cell = cells.getCell("EQ1");		cell.setValue("LoaiHD");
			cell = cells.getCell("ER1");		cell.setValue("NgayKyHD");
			cell = cells.getCell("ES1");		cell.setValue("NgayKetThucHD");
			cell = cells.getCell("ET1");		cell.setValue("TeamLeader");
			cell = cells.getCell("EU1");		cell.setValue("SoTaiKhoan");
			cell = cells.getCell("EV1");		cell.setValue("Email");
			cell = cells.getCell("EW1");		cell.setValue("GhiChu");
			cell = cells.getCell("EX1");		cell.setValue("Data");
			cell = cells.getCell("EY1");		cell.setValue("TrangThai");
			cell = cells.getCell("EZ1");		cell.setValue("Vung");
			cell = cells.getCell("FA1");		cell.setValue("KhuVuc");

		} catch (Exception ex) 
		{
			System.out.println(ex.toString());
			throw new Exception("Khong the tao duoc Header cho bao cao.!!!");
		}
	}
	private void FillData(Workbook workbook,IStockintransit obj)throws Exception
	{
		try{
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setGridlinesVisible(false);
			Cells cells = worksheet.getCells();
			dbutils db = new dbutils();
			
			ResultSet rs = db.get(getQuery());
			Cell cell = null;
			int countRow = 2;
			while(rs.next()){
				cell = cells.getCell("EA" + String.valueOf(countRow));		cell.setValue(rs.getString("BMMA"));
				cell = cells.getCell("EB" + String.valueOf(countRow));		cell.setValue(rs.getString("BMTEN"));
				cell = cells.getCell("EC" + String.valueOf(countRow));		cell.setValue(rs.getString("ASMMA"));
				cell = cells.getCell("ED" + String.valueOf(countRow));		cell.setValue(rs.getString("ASMTEN"));
				cell = cells.getCell("EE" + String.valueOf(countRow));		cell.setValue(rs.getString("GSBHMA"));
				cell = cells.getCell("EF" + String.valueOf(countRow));		cell.setValue(rs.getString("GSBHTEN"));
				cell = cells.getCell("EG" + String.valueOf(countRow));		cell.setValue(rs.getString("NPPMA"));
				cell = cells.getCell("EH" + String.valueOf(countRow));		cell.setValue(rs.getString("NPPTEN"));
				cell = cells.getCell("EI" + String.valueOf(countRow));		cell.setValue(rs.getString("DDKDMA"));
				cell = cells.getCell("EJ" + String.valueOf(countRow));		cell.setValue(rs.getString("DDKDTEN"));
				cell = cells.getCell("EK" + String.valueOf(countRow));		cell.setValue(rs.getString("DIACHI"));
				cell = cells.getCell("EL" + String.valueOf(countRow));		cell.setValue(rs.getString("PHANTRAMCHUYEN"));
				cell = cells.getCell("EM" + String.valueOf(countRow));		cell.setValue(rs.getString("SONAMLAMVIEC"));
				cell = cells.getCell("EN" + String.valueOf(countRow));		cell.setValue(rs.getString("DienThoai"));
				cell = cells.getCell("EO" + String.valueOf(countRow));		cell.setValue(rs.getString("NgayVAOCONGTY"));
				cell = cells.getCell("EP" + String.valueOf(countRow));		cell.setValue(rs.getString("HDLD"));
				cell = cells.getCell("EQ" + String.valueOf(countRow));		cell.setValue(rs.getString("LOAIHD"));
				cell = cells.getCell("ER" + String.valueOf(countRow));		cell.setValue(rs.getString("NGAYKYHD"));
				cell = cells.getCell("ES" + String.valueOf(countRow));		cell.setValue(rs.getString("NGAYKETTHUCHD"));
				cell = cells.getCell("ET" + String.valueOf(countRow));		cell.setValue(rs.getString("TEAMLEADER"));
				cell = cells.getCell("EU" + String.valueOf(countRow));		cell.setValue(rs.getString("SOTAIKHOAN"));
				cell = cells.getCell("EV" + String.valueOf(countRow));		cell.setValue(rs.getString("EMAIL"));
				cell = cells.getCell("EW" + String.valueOf(countRow));		cell.setValue(rs.getString("GHICHU"));
				cell = cells.getCell("EX" + String.valueOf(countRow));		cell.setValue(rs.getString("DATA"));
				
				String trangthai = "Hoạt động ";
				if(rs.getString("trangthai").equals("0"))
					trangthai = "Ngưng hoạt động ";
					 
						
					
					
				cell = cells.getCell("EY" + String.valueOf(countRow));		cell.setValue(trangthai);
				cell = cells.getCell("EZ" + String.valueOf(countRow));		cell.setValue(rs.getString("VUNG"));
				cell = cells.getCell("FA" + String.valueOf(countRow));		cell.setValue(rs.getString("KHUVUC"));
				++countRow;
			}
			if(rs!=null)rs.close();
			if(db!=null){
				db.shutDown();
			}
			//ReportAPI.setHidden(workbook,28);
	
		}catch(Exception ex){
			
			System.out.println("Errrorr : "+ex.toString());
			throw new Exception("Qua trinh dien du lieu vao file Excel va tao PivotTable bi loi.!!!");
		}
	}
	public void setQuery(IStockintransit obj,HttpServletRequest request)
	{
		Utility util = new Utility();
		obj.setuserTen(util.antiSQLInspection(request.getParameter("userTen")));

		query1 =
		"	SELECT 	ISNULL(V.TEN,'') AS VUNG , BM.TEN AS BMTEN,BM.MANHANVIEN AS BMMA,ISNULL(KV.TEN,'') AS KHUVUC,ASM.MANHANVIEN AS ASMMA,ASM.TEN AS ASMTEN, " +  
		"	F.MANHANVIEN AS GSBHMA,F.TEN AS GSBHTEN,D.MA AS NPPMA,D.TEN AS NPPTEN,A.MANHANVIEN AS DDKDMA,A.TEN AS DDKDTEN,1 AS DATA, " +  
		"	A.DIENTHOAI,A.DIACHI,A.EMAIL,A.PHANTRAMCHUYEN,TN.MANHANVIEN AS DDTIENNHIEMMA,TN.TEN AS DDTIENNHIEMTEN,A.SONAMLAMVIEC,A.NGAYVAOCONGTY,A.LOAIHD,A.NGAYKYHD,A.TEAMLEADER,A.SOTAIKHOAN,A.GHICHU, " +  
		"	A.HDLD,A.NGAYKETTHUCHD,A.TRANGTHAI " +  
		"FROM DAIDIENKINHDOANH A " +  
		"	INNER JOIN NHANVIEN B ON A.NGUOITAO = B.PK_SEQ " +  
		"	LEFT JOIN DAIDIENKINHDOANH TN ON TN.PK_SEQ=A.DDKDTIENNHIEM" +  
		"	INNER JOIN  NHANVIEN C ON   A.NGUOISUA = C.PK_SEQ " +  
		"	LEFT JOIN  NHAPHANPHOI D ON A.NPP_FK = D.PK_SEQ " +  
		"	LEFT JOIN KHUVUC KV ON KV.PK_SEQ=D.KHUVUC_FK " +  
		"	LEFT JOIN VUNG V ON V.PK_SEQ=KV.VUNG_FK	" +  
		"	LEFT JOIN DDKD_GSBH E ON A.PK_SEQ = E.DDKD_FK  " +  
		"	LEFT JOIN GIAMSATBANHANG F ON E.GSBH_FK = F.PK_SEQ  " +  
		"	LEFT JOIN ASM_KHUVUC ON ASM_KHUVUC.KHUVUC_FK=KV.PK_SEQ" +  
		"	LEFT JOIN ASM ON ASM.PK_SEQ=ASM_KHUVUC.ASM_FK	" +  
		"	LEFT JOIN BM_CHINHANH ON BM_CHINHANH.VUNG_FK=V.PK_SEQ	" +  
		"	LEFT JOIN BM ON BM.PK_SEQ=BM_CHINHANH.BM_FK	" +  
		"	LEFT JOIN  KENHBANHANG G ON F.KBH_FK=G.PK_SEQ " +  
		" WHERE  1= 1 ";
		//" ASM.TRANGTHAI=1  AND F.TRANGTHAI=1  AND ASM_KHUVUC.NGAYBATDAU<='"+getDateTime()+"' "+
		//"		AND ASM_KHUVUC.NGAYKETTHUC>='"+getDateTime()+"' AND BM_CHINHANH.NGAYBATDAU<='"+getDateTime()+"' "+
		//"		AND BM_CHINHANH.NGAYKETTHUC>='"+getDateTime()+"' ";

		
		String tenNVBH = util.antiSQLInspection(request.getParameter("ddkdTen"));
		if(tenNVBH.length() > 0 ) query1 += " and A.TIMKIEM like upper(dbo.ftBoDau(N'%" +tenNVBH.trim() + "%')) ";
		
		String soDT = util.antiSQLInspection(request.getParameter("DienThoai"));
		if(soDT.length() > 0) query1 += " AND A.DIENTHOAI = '" + soDT.trim() + "'";
		
		String kenhBH = util.antiSQLInspection(request.getParameter("KenhBanHang"));
		if(kenhBH.length() > 0 ) query1 += " AND G.PK_SEQ = '" + kenhBH + "'";
		
		String GSBH = util.antiSQLInspection(request.getParameter("GSBanHang"));
		if(GSBH.length() > 0 ) query1 += " AND F.PK_SEQ = '" + GSBH + "'";
		
		String trangthai = util.antiSQLInspection(request.getParameter("TrangThai"));
		if(!trangthai.equals("2")) query1 += " AND A.TRANGTHAI = '" + trangthai + "'";
		
		String npp = util.antiSQLInspection(request.getParameter("NhaPhanPhoi"));
		if(npp.length() > 0 ) query1 += " AND A.NPP_FK = '" + npp + "'";
		
		System.out.println("Xuất Trình dược viên : " + query1);
	}
	public String getQuery()
	{
		return this.query1;
	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
      
        return dateFormat.format(date);	
	}

}




