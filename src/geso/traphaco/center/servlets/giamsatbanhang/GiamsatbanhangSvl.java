
package geso.traphaco.center.servlets.giamsatbanhang;

import geso.traphaco.center.beans.giamsatbanhang.*;
import geso.traphaco.center.beans.giamsatbanhang.imp.*;
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

 public class GiamsatbanhangSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet 
 {
	static final long serialVersionUID = 1L;
	Utility bodau=new Utility();
   	
	public GiamsatbanhangSvl() 
	{
		super();
	}   	
	String query1="";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		

		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
	    IGiamsatbanhangList obj = new GiamsatbanhangList();
	    Utility util = new Utility();
	    out = response.getWriter();
	    

	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	  
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String bgId = util.getId(querystring);

	    if (action.equals("delete"))
	    {	   		  	    	
	    	Delete(bgId, obj);
	    	System.out.println(obj.getMsg());
	    	out.print(bgId);
	    }
	    obj.setUserId(userId);
	    obj.setRequestObj(request);
	   	obj.init("");
	   
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Center/GiamSatBanHang.jsp";
		response.sendRedirect(nextJSP);

	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    IGiamsatbanhangList obj = new GiamsatbanhangList();
		HttpSession session = request.getSession();
		
		Utility util = new Utility();
		
	    String userId = util.antiSQLInspection(request.getParameter("userId"));
	    String userTen = util.antiSQLInspection(request.getParameter("userTen"));
	    
	  
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    } 
	        
	    String nextJSP = "";
	    obj.setUserId(userId);
	    
	    if (action.equals("new"))
	    {
	    	IGiamsatbanhang gsbhBean = (IGiamsatbanhang) new Giamsatbanhang("");
	    	gsbhBean.setUserId(userId);
	    	session.setAttribute("gsbhBean", gsbhBean);
    		nextJSP = "/TraphacoHYERP/pages/Center/GiamSatBanHangNew.jsp";
	    }
	    else if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    {
	    	obj.setNxtApprSplitting(Integer.parseInt(util.antiSQLInspection(request.getParameter("nxtApprSplitting"))));	    	
	    	String search = getSearchQuery(request, obj);
	    	obj.init(search);
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	nextJSP = "/TraphacoHYERP/pages/Center/GiamSatBanHang.jsp";
	    }	    
	    if (action.equals("search"))
	    {
	    	String search = getSearchQuery(request,obj);	    		    	
	    	obj.init(search);
	    	nextJSP = "/TraphacoHYERP/pages/Center/GiamSatBanHang.jsp";
	    }
	    
	    if(action.equals("excel"))
	    {
	    	try
	    	{
		    	response.setContentType("application/xlsm");
		        response.setHeader("Content-Disposition", "attachment; filename=DanhSachGiamSatBanHang.xlsm");
		        OutputStream out = response.getOutputStream();
		        IStockintransit obj1 = new Stockintransit();
		        obj1.setuserId(userId);
				obj1.setuserTen(userTen);
		        setQuery(obj1,request);
				ExportToExcel(out,obj1);
		    	nextJSP = "/TraphacoHYERP/pages/Center/GiamSatBanHang.jsp";
	    	}
	    	catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}
	    }
	    session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);    		
		response.sendRedirect(nextJSP);		   
	}   
	private void ExportToExcel(OutputStream out,IStockintransit obj)throws Exception{
		try{ 
			String chuoi=getServletContext().getInitParameter("path") + "\\DANHSACHGSBH.xlsm";			
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
	private void CreateHeader(Workbook workbook,IStockintransit obj) throws Exception {
		try {
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");
	
			Cells cells = worksheet.getCells();
			cells.setRowHeight(0, 50.0f);
			Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 16,
					"DANH SÁCH PTT / GĐ CN 2");
			cell = cells.getCell("A2");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Ngày tạo : "
					+ obj.getDateTime());
			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10,
					"Người tạo : " + obj.getuserTen());
			
			cell = cells.getCell("AA1");		cell.setValue("MaNhanVien");
			cell = cells.getCell("AB1");		cell.setValue("TEN");
			cell = cells.getCell("AC1");		cell.setValue("DIENTHOAI");
			cell = cells.getCell("AD1");		cell.setValue("EMAIL");
			cell = cells.getCell("AE1");		cell.setValue("TRANGTHAI");
			cell = cells.getCell("AF1");		cell.setValue("NGAYTAO");
			cell = cells.getCell("AG1");		cell.setValue("NGUOITAO");
			cell = cells.getCell("AH1");		cell.setValue("NGAYSUA");
			cell = cells.getCell("AI1");		cell.setValue("NGUOISUA");
			cell = cells.getCell("AJ1");		cell.setValue("NCC");
			cell = cells.getCell("AK1");		cell.setValue("KBH");
			cell = cells.getCell("AL1");		cell.setValue("DIACHI");
			cell = cells.getCell("AM1");		cell.setValue("KHUVUC");
			cell = cells.getCell("AN1");		cell.setValue("DVKD");
			cell = cells.getCell("AO1");		cell.setValue("SMARTID");
			
			cell = cells.getCell("AP1");		cell.setValue("MaNhanVien");
			cell = cells.getCell("AQ1");		cell.setValue("VITRI");
			cell = cells.getCell("AR1");		cell.setValue("VUNGTT");
			cell = cells.getCell("AS1");		cell.setValue("SOTAIKHOAN");
			cell = cells.getCell("AT1");		cell.setValue("SONAMLAMVIEC");
			cell = cells.getCell("AU1");		cell.setValue("SODIENTHOAICONGTY");
			cell = cells.getCell("AV1");		cell.setValue("DAKYHOPDONG");
			cell = cells.getCell("AW1");		cell.setValue("LOAIHOPDONG");
			cell = cells.getCell("AX1");		cell.setValue("NGAYKYHOPDONG");
			cell = cells.getCell("AY1");		cell.setValue("NGAYVAOCONGTY");
			cell = cells.getCell("AZ1");		cell.setValue("NGAYKETTHUCHOPDONG");
			cell = cells.getCell("BA1");		cell.setValue("GHICHU");
			
		}
		catch(Exception ex)
		{
			System.out.println(ex.toString());
			throw new Exception("Khong the tao duoc Header cho bao cao.!!!");
		}
		
	}
	
	private void FillData(Workbook workbook,IStockintransit obj)throws Exception{
		try
		{
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setGridlinesVisible(false);
			Cells cells = worksheet.getCells();
			dbutils db = new dbutils();
			ResultSet rs = db.get(getQuery());
			Cell cell = null;
			int countRow = 2;
			while(rs.next()){
				cell = cells.getCell("AA" + String.valueOf(countRow));		cell.setValue(rs.getString("MaNhanVien"));
				cell = cells.getCell("AB" + String.valueOf(countRow));		cell.setValue(rs.getString("tenGSBH"));
				cell = cells.getCell("AC" + String.valueOf(countRow));		cell.setValue(rs.getString("DIENTHOAI"));
				cell = cells.getCell("AD" + String.valueOf(countRow));		cell.setValue(rs.getString("EMAIL"));
				cell = cells.getCell("AE" + String.valueOf(countRow));		cell.setValue(rs.getString("TRANGTHAI"));
				cell = cells.getCell("AF" + String.valueOf(countRow));		cell.setValue(rs.getString("NGAYTAO"));
				cell = cells.getCell("AG" + String.valueOf(countRow));		cell.setValue(rs.getString("NGUOITAO"));
				cell = cells.getCell("AH" + String.valueOf(countRow));		cell.setValue(rs.getString("NGAYSUA"));
				cell = cells.getCell("AI" + String.valueOf(countRow));		cell.setValue(rs.getString("NGUOISUA"));
				cell = cells.getCell("AJ" + String.valueOf(countRow));		cell.setValue(rs.getString("TENNCC"));
				cell = cells.getCell("AK" + String.valueOf(countRow));		cell.setValue(rs.getString("TENKBH"));
				cell = cells.getCell("AL" + String.valueOf(countRow));		cell.setValue(rs.getString("DIACHI"));
				cell = cells.getCell("AM" + String.valueOf(countRow));		cell.setValue(rs.getString("TENKHUVUC"));
				cell = cells.getCell("AN" + String.valueOf(countRow));		cell.setValue(rs.getString("DONVIKINHDOANH"));
				cell = cells.getCell("AO" + String.valueOf(countRow));		cell.setValue(rs.getString("SMARTID"));
				
				cell = cells.getCell("AP" + String.valueOf(countRow));		cell.setValue(rs.getString("MaNhanVien"));
				cell = cells.getCell("AQ" + String.valueOf(countRow));		cell.setValue(rs.getString("VITRI"));
				cell = cells.getCell("AR" + String.valueOf(countRow));		cell.setValue(rs.getString("VUNGTT"));
				cell = cells.getCell("AS" + String.valueOf(countRow));		cell.setValue(rs.getString("SOTAIKHOAN"));
				cell = cells.getCell("AT" + String.valueOf(countRow));		cell.setValue(rs.getString("SONAMLAMVIEC"));
				cell = cells.getCell("AU" + String.valueOf(countRow));		cell.setValue(rs.getString("SODTCONGTY"));
				cell = cells.getCell("AV" + String.valueOf(countRow));		cell.setValue(rs.getString("DAKYHD"));
				cell = cells.getCell("AW" + String.valueOf(countRow));		cell.setValue(rs.getString("LOAIHD"));
				cell = cells.getCell("AX" + String.valueOf(countRow));		cell.setValue(rs.getString("NGAYKYHD"));
				cell = cells.getCell("AY" + String.valueOf(countRow));		cell.setValue(rs.getString("NGAYVAOCONGTY"));
				cell = cells.getCell("AZ" + String.valueOf(countRow));		cell.setValue(rs.getString("NGAYKETTHUCHD"));
				cell = cells.getCell("BA" + String.valueOf(countRow));		cell.setValue(rs.getString("GHICHU"));
				++countRow;
			}
			if(rs!=null)rs.close();
			if(db!=null){
				db.shutDown();
			}
			ReportAPI.setHidden(workbook,27);

		}
		catch(Exception ex)
		{
			
			System.out.println("Errrorr : "+ex.toString());
			throw new Exception("Qua trinh dien du lieu vao file Excel va tao PivotTable bi loi.!!!");
		}
	}
	private void setQuery(IStockintransit obj,HttpServletRequest request)
	{
		Utility util = new Utility();
		
		String TenGSBH = util.antiSQLInspection(request.getParameter("TenGSBH"));
	    if(TenGSBH==null) TenGSBH="";
	    String SoDienThoai = util.antiSQLInspection(request.getParameter("SoDienThoai"));
	    if(SoDienThoai==null) SoDienThoai="";
	    String KenhBanHang = util.antiSQLInspection(request.getParameter("KenhBanHang"));
	    if(KenhBanHang==null) KenhBanHang="";
	    String TrangThai = util.antiSQLInspection(request.getParameter("TrangThai"));
	    if(TrangThai==null) TrangThai="";
	    
	    String tungay =util.antiSQLInspection(request.getParameter("tungay")==null?"":request.getParameter("tungay"));
	    
	    String denngay =util.antiSQLInspection(request.getParameter("denngay")==null?"":request.getParameter("denngay"));
	    
		query1= "SELECT gsbh.manhanvien, gsbh.vitri, gsbh.vungtt, gsbh.sotaikhoan, gsbh.sonamlamviec, gsbh.sodtcongty, gsbh.dakyhd, gsbh.loaihd, gsbh.ngaykyhd, gsbh.ngayvaocongty, gsbh.ngayketthuchd, gsbh.ghichu," +
				"gsbh.PK_SEQ AS gsbhId, gsbh.TEN AS tenGSBH, ISNULL(gsbh.DIENTHOAI,'N/A')AS DIENTHOAI, ISNULL(gsbh.EMAIL,'N/A')AS EMAIL,  "+
			    "	gsbh.NGAYTAO AS NGAYTAO, gsbh.NGAYSUA AS NGAYSUA, nt.TEN AS NGUOITAO, ns.TEN AS NGUOISUA, ncc.TEN AS TENNCC,ISNULL(gsbh.DIACHI,'N/A') AS DIACHI, "+
			    "	kv.TEN AS TENKHUVUC , dvkd.DIENGIAI AS DONVIKINHDOANH, cast(gsbh.pk_seq as nvarchar(10)) AS SMARTID, " +
				"	case " +
				"	when gsbh.TRANGTHAI = 1 then N'Hoạt động' " +
				"	else  N'Ngưng hoạt động' " +
				"	end AS TRANGTHAI " +
				"	FROM GIAMSATBANHANG gsbh " +
				"	INNER JOIN NHANVIEN nt ON gsbh.NGUOITAO = nt.PK_SEQ " +
				"	INNER JOIN NHANVIEN ns ON gsbh.NGUOISUA = ns.PK_SEQ " +
				"	INNER JOIN NHACUNGCAP ncc ON gsbh.NCC_FK = ncc.PK_SEQ " +
				" 	INNER JOIN GSBH_KHUVUC ON GSBH_KHUVUC.GSBH_FK=GSBH.PK_SEQ" +
				"	INNER JOIN KHUVUC kv ON GSBH_KHUVUC.KHUVUC_FK = kv.PK_SEQ " +
				"   INNER JOIN DONVIKINHDOANH dvkd ON gsbh.DVKD_FK = dvkd.PK_SEQ " +
				"	WHERE 1=1 " ;
		if(TenGSBH.length()>0) query1 += " AND gsbh.TIMKIEM LIKE  UPPER(DBO.ftBoDau( N'%"+TenGSBH+"%' )) ";
		if(SoDienThoai.length()>0) query1 += " AND gsbh.DIENTHOAI = '" + SoDienThoai + "' ";
		if(KenhBanHang.length()>0) query1 += " AND gsbh.KBH_FK = '" + KenhBanHang + "' ";
		if(!TrangThai.equals("2")) query1 += " AND gsbh.trangthai = '" + TrangThai + "' ";
		
		if(tungay.length()>0)
			query1+=" and exists "+        
				    "   (select *  From NHAPP_GIAMSATBH where gsbh_fk=gsbh.pk_seq and NGAYBATDAU >='"+tungay+"'  ) ";  
		
		if(denngay.length()>0)
			query1+=" and exists "+        
				    "   (select *  From NHAPP_GIAMSATBH where gsbh_fk=gsbh.pk_seq and NGAYKETTHUC <='"+denngay+"'  ) ";
		
	System.out.println("1/Câu lệnh excel: " +query1);
	}
	private String getQuery()
	{
		return query1;
	}

	private String getSearchQuery(HttpServletRequest request,IGiamsatbanhangList obj){
	   // PrintWriter out = response.getWriter();
		
		Utility util = new Utility();
		
		String Tengsbh = util.antiSQLInspection(request.getParameter("TenGSBH"));
    	if ( Tengsbh== null)
    		Tengsbh = "";
    	obj.setTen(Tengsbh);
    	
    	String SoDienThoai = util.antiSQLInspection(request.getParameter("SoDienThoai"));
    	if (SoDienThoai == null)
    		SoDienThoai = "";    	
    	obj.setSodienthoai(SoDienThoai);
    	
    	String kbhId = util.antiSQLInspection(request.getParameter("KenhBanHang"));
    	if (kbhId == null)
    		kbhId = "";    	
    	obj.setKbhId(kbhId);
    	   	
    	String trangthai = util.antiSQLInspection(request.getParameter("TrangThai"));   	
    	if (trangthai == null)
    		trangthai = "";    	
	
    	if (trangthai.equals("2"))
    		trangthai = "";
    	
    	obj.setTrangthai(trangthai);
    	
    	String maFAST = util.antiSQLInspection(request.getParameter("maFAST"));
    	if (maFAST == null)
    		maFAST = "";    	
    	obj.setMaFAST(maFAST);
    	
    	String	query = " select isnull(a.maFast,'') as maFast , a.pk_seq as id, a.ten as ten, a.diachi as diachi,  " +
		"  a.dienthoai as sodienthoai, a.email as email, b.tenviettat as nccTen " +
		" , b.pk_seq as nccId, a.trangthai as trangthai, d.ten as nguoitao," +
		"   a.ngaytao as ngaytao, e.ten as nguoisua, a.ngaysua as ngaysua,  " +
		" a.pk_seq as manhanvien from giamsatbanhang a, nhacungcap b,  " +
		"  nhanvien d, nhanvien e where a.ncc_fk=b.pk_seq  and a.nguoitao = d.pk_seq and a.nguoisua = e.pk_seq ";
    	
    	
    	if (Tengsbh.length()>0)
    	{
			query = query + " and upper("+bodau.replaceAEIOU("(a.timkiem)")+") like UPPER( '%"+bodau.replaceAEIOU(Tengsbh)+"%')";		
    	}
    	
    	if (SoDienThoai.length()>0){
			query = query + " and upper(a.dienthoai) like upper('%" + SoDienThoai + "%')";
			
    	}
    	
    	if (kbhId.length()>0)
    	{
    		obj.setKbhId(kbhId);
			query = query + " and c.pk_seq = '" + kbhId + "'";	
    	}

    	if(trangthai.length() > 0)
    	{
    		query = query + " and a.trangthai = '" + trangthai + "'";
    	}

    	String tungay =util.antiSQLInspection(request.getParameter("tungay")==null?"":request.getParameter("tungay"));
    	obj.setTungay(tungay);
	    
	    String denngay =util.antiSQLInspection(request.getParameter("denngay")==null?"":request.getParameter("denngay"));
	    obj.setDenngay(denngay);
	    
	    
	    if(tungay.length()>0)
	    	query+=" and exists "+        
				    "   (select *  From NHAPP_GIAMSATBH where gsbh_fk=a.pk_seq and NGAYBATDAU >='"+tungay+"'  ) ";  
		
		if(denngay.length()>0)
			query+=" and exists "+        
				    "   (select *  From NHAPP_GIAMSATBH where gsbh_fk=a.pk_seq and NGAYKETTHUC <='"+denngay+"'  ) ";
		
		if(maFAST.length()>0)
    	{
    		query+= " and a.maFAST like '%"+maFAST+"%' ";
    	}
    	
    	System.out.println("gsbh: "+query);

    	return query;
	}	
	boolean kiemtra(String sql)
	{
		dbutils db =new dbutils();
    	ResultSet rs = db.get(sql);
		try {//kiem tra ben san pham
			while(rs.next())
			{ if(rs.getString("num").equals("0"))
			   return true;
			}
		} catch(Exception e) {
		
			e.printStackTrace();
		} 
		return false;
	}
	private void Delete(String id, IGiamsatbanhangList obj)
	{
		
		dbutils db = new dbutils();
		String sql ="select count(gsbh_fk) as num from donhang where gsbh_fk='"+ id + "'";
		System.out.println("kt: "+sql);
		if(kiemtra(sql))
		{  	
			System.out.println("kiem tra" + kiemtra(sql) );
			sql="delete from ddkd_gsbh where gsbh_fk='" + id + "'";
			db.get(sql); 
			sql = "select count(gsbh_fk) as num from ddkd_gsbh where gsbh_fk='"+ id + "'";
		 	System.out.println("kt 1: "+sql);
		 	
			if(kiemtra(sql))
			{
				System.out.println("kiem tra" + kiemtra(sql) );
				
				sql="delete from nhapp_giamsatbh where gsbh_fk='" + id + "'";
				db.get(sql);
				
				sql = "select count(gsbh_fk) as num from nhapp_giamsatbh where gsbh_fk='"+ id + "'";
				System.out.println("kt2: "+ sql);
				if(kiemtra(sql))
				{
					System.out.println("kiem tra" + kiemtra(sql) );
					db.update("delete from giamsatbanhang where pk_seq = '" + id + "'");
					System.out.println("xóa thành công");
				}
				else{
					obj.setMsg("Giám sát bán hàng này đã có nhà phân phối rồi nên không xóa được");}	
			}
			else{
				obj.setMsg("Giám sát bán hàng này đã có trong đơn hàng rồi nên không xóa được");}	
		}
		else{
			obj.setMsg("Giám sát bán hàng này đã có đại diện kinh doanh rồi nên không xóa được");}
	}
	
	
 }

