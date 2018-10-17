package geso.traphaco.center.servlets.thongtinsanpham;

import geso.traphaco.center.beans.thongtinsanpham.IThongtinsanpham;
import geso.traphaco.center.beans.thongtinsanpham.IThongtinsanphamList;
import geso.traphaco.center.beans.thongtinsanpham.imp.Thongtinsanpham;
import geso.traphaco.center.beans.thongtinsanpham.imp.ThongtinsanphamList;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.db.sql.dbutils;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.BorderType;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.HorizontalAlignmentType;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

 public class ThongtinsanphamSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   PrintWriter out;
   IThongtinsanphamList obj;
private int items = 50;
private int splittings = 10;
   
   public ThongtinsanphamSvl() {
		super();
	}   
   
   private void settingPage(IThongtinsanphamList obj) {
		Utility util = new Utility();
		if(getServletContext().getInitParameter("items") != null){
	    	String i = getServletContext().getInitParameter("items").trim();
	    	if(util.isNumeric(i))
	    		items = Integer.parseInt(i);
	    }
	    
	    if(getServletContext().getInitParameter("splittings") != null){
	    	String i = getServletContext().getInitParameter("splittings").trim();
	    	if(util.isNumeric(i))
	    		splittings = Integer.parseInt(i);
	    }
	    
   	obj.setItems(items);
   	obj.setSplittings(splittings);
	}
	
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
      
	    Utility util = new Utility();
	    out = response.getWriter();
	    obj = new ThongtinsanphamList();
   		
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = request.getParameter("userId");
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String spId = util.getId(querystring);

	    String msg = "";
	    if (action.equals("delete")){	   		  	    	
	    	Delete(spId);
	    }
	    
    	obj.setUserId(userId);
    	settingPage(obj);
		obj.init();
		obj.setMsg(msg);
		
    	session.setAttribute("obj", obj);

		session.setAttribute("userId", userId);
    		
		response.sendRedirect("/TraphacoHYERP/pages/Center/ThongTinSanPham.jsp");
	}  	
	
	private String ThayDoiTrangThai(String trangthaiCAPNHAT, String userId) 
	{
		String[] ttARR = trangthaiCAPNHAT.split(",");
		String strTRANGTHAI = "";
		for( int i = 0; i < ttARR.length; i++ )
		{
			String[] ttARR2 = ttARR[i].split("-");
			strTRANGTHAI += "select " + ttARR2[0] + " as spId, " + ttARR2[1] + " as trangthai ";
			strTRANGTHAI += " union ALL ";
		}
		
		if( strTRANGTHAI.trim().length() > 0 )
		{
			strTRANGTHAI = strTRANGTHAI.substring(0, strTRANGTHAI.length() - 10);
			
			dbutils db = new dbutils();
			String query = " Update a set a.trangthai = b.trangthai, a.nguoisua = '" + userId + "', ngaysua = '" + getDateTime() + "' " + 
						   " from SANPHAM a inner join ( " + strTRANGTHAI + " ) b on a.pk_seq = b.spId ";
			
			System.out.println(":::: CAP NHAT TRANG THAI: " + query );
			if( !db.update(query) )
			{
				db.shutDown();
				return "Lỗi khi cập nhật trạng thái: " + query;
			}
		}
		
		return "";
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    OutputStream out = response.getOutputStream();
	    
		HttpSession session = request.getSession();
	    String userId = request.getParameter("userId");
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	        
	    String msg = "";
	    if (action.equals("new"))
	    {
	    	// Empty Bean for distributor
	    	IThongtinsanpham spBean = (IThongtinsanpham) new Thongtinsanpham();
	    	spBean.setUserId(userId);
	    	spBean.CreateRS();
	    	// Save Data into session
	    	session.setAttribute("userId", userId);
	    	session.setAttribute("spBean", spBean);
    		
			String nextJSP = "/TraphacoHYERP/pages/Center/ThongTinSanPhamNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    else if( action.equals("changeTRANGTHAI") )
	    {
	    	String trangthaiCAPNHAT = request.getParameter("trangthaiCAPNHAT");
	    	if( trangthaiCAPNHAT.trim().length() > 0 )
	    	{
	    		trangthaiCAPNHAT = trangthaiCAPNHAT.substring(0, trangthaiCAPNHAT.length() - 1);		
	    		msg = ThayDoiTrangThai(trangthaiCAPNHAT, userId);
	    	}
	    }
	    
	    obj = new ThongtinsanphamList();
	    settingPage(obj);
	    if (action.equals("search") || action.equals("changeTRANGTHAI") )
	    {
	    	obj.setQuery(getSearchQuery(request, obj));
			obj.setUserId(userId);
			obj.init();
			
	    	session.setAttribute("obj", obj);
    		session.setAttribute("userId", userId);
    		
    		response.sendRedirect("/TraphacoHYERP/pages/Center/ThongTinSanPham.jsp");	    	
	    }
	    else if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    {
	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
	    	obj.setUserId(userId);
	    	obj.setQuery(search);
	    	obj.init();
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	session.setAttribute("obj", obj);
	    	response.sendRedirect("/TraphacoHYERP/pages/Center/ThongTinSanPham.jsp");
		}
	    
	    if (action.equals("excel"))
	    {
	    	obj = new ThongtinsanphamList();
	    	
	    	obj.setQuery(getSearchQuery(request, obj));
	    	
	    	try
		    {
	    		response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=DanhSachSanPham.xlsm");
		        
		        Workbook workbook = new Workbook();
		    	
				FileInputStream fstream = null;
				String chuoi = getServletContext().getInitParameter("path") + "\\BCDanhSachSanPham.xlsm";
				
				fstream = new FileInputStream(chuoi);		
				workbook.open(fstream);
				workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
				
				String query =   
						 "select sp.MA, sp.TEN, dv.DIENGIAI as donvi, dvkd.DIENGIAI as dvkd, nh.TEN as nganhhang, nhan.TEN as nhanhang, " + 
								 "cl.TEN as chungloai, " + 
								 "sp.dangtrinhbay, sp.quycachdonggoi, sp.tieuchuanchatluong, sp.nguongocnguyenlieu, sp.tieuchuannsx, sp.nuocsx, " + 
								 "sp.TENXUATHOADON, sp.NGUONGOCNHAPKHAU, " + 
								 "sp.tenviettat, lsp.TEN as loaisp, sp.HAMLUONG, sp.tenhoatchat, sp.nguongoc as nguongoc, sp.thuexuat,sp.giabancothue," + 
								 "sp.giatheodvtnhonhat,sp.stttheott40,sp.nhomsphtotc,isnull(sp.SPMOI,'') as SPMOI ,isnull(sp.SPCHULUC,'') as  SPCHULUC," + 
								 "case " + 
								 "when sp.TRANGTHAI = 1 then N'Hoạt động'" + 
								 "else" + 
								 "	N'Ngưng hoạt động'" + 
								 "end as trangthai,sp.VISA,sp.NGAYCAP,sp.hethanvisa,sp.KKG,sp.NSX,sp.NKK,sp.NXB," + 
								 "[dbo].[LayNhomHang_SanPham](sp.PK_SEQ) as NhomHang, [dbo].[LayNhomSanPham_SanPham](sp.PK_SEQ) as NhomSP " + 
								 "from SANPHAM sp left join DONVIDOLUONG dv on sp.DVDL_FK = dv.PK_SEQ  " + 
								 "left join DONVIKINHDOANH dvkd on sp.DVKD_FK = dvkd.PK_SEQ " + 
								 "left join NGANHHANG nh on sp.NGANHHANG_FK = nh.PK_SEQ " + 
								 "left join NHANHANG nhan on sp.NHANHANG_FK = nhan.PK_SEQ " + 
								 "left join CHUNGLOAI cl on sp.CHUNGLOAI_FK = cl.PK_SEQ " + 
								 "left join ERP_LOAISANPHAM lsp on sp.LOAISANPHAM_FK = lsp.PK_SEQ "
								 + "where sp.trangthai = 1" + 
								 "order by sp.TEN asc " ;
				
				CreateStaticData(workbook, query);

				//Saving the Excel file
				workbook.save(out);
				
				fstream.close();
		    }
		    catch (Exception ex)
		    {
		        obj.setMsg("Khong the tao pivot.");
		        ex.printStackTrace();
		    }
	    	
			obj.setUserId(userId);
			obj.init();
			
	    	session.setAttribute("obj", obj);

    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Center/ThongTinSanPham.jsp");	    		
	    }
	    
	
	}   
		
	private String getSearchQuery(HttpServletRequest request, IThongtinsanphamList obj)
	{
		String masp = request.getParameter("masp");
    	if (masp == null)
    		masp = "";
    	obj.setMasp(masp);

		String tensp = request.getParameter("tensp");
    	if (tensp == null)
    		tensp = "";
    	obj.setTensp(tensp);
    	
    	String dvkdId = request.getParameter("dvkdId");
    	if (dvkdId == null)
    		dvkdId = "";    	
    	obj.setDvkdId(dvkdId);
    	
    	String nhId = request.getParameter("nhId");
    	if (nhId == null)
    		nhId = "";    	
    	obj.setNhId(nhId);
    	
    	String clId = request.getParameter("clId");
    	if (clId == null)
    		clId = "";    	
    	obj.setClId(clId);
    	
    	String trangthai = request.getParameter("trangthai");   	
    	if (trangthai == null)
    		trangthai = "";    	
	
    	if (trangthai.equals("2"))
    		trangthai = "";
    	
    	obj.setTrangthai(trangthai);
    	
    	String soItem = request.getParameter("soItems");
		if(soItem == null)
			soItem = "25";
		int soItems = Integer.parseInt(soItem);
		obj.setSoItems(soItems);
		
		String loaispId = request.getParameter("loaispId");
    	if (loaispId == null)
    		loaispId = "";    	
    	obj.setLoaispId(loaispId);    	
		
    	String   query ="select ROW_NUMBER() OVER(ORDER BY a.ma DESC) AS 'stt', a.pk_seq, a.ma, a.ten, a.trongluong, a.thetich, b.donvikinhdoanh as dvkd,b.pk_seq as dvkdId, c.ten as chungloai, e.pk_seq as nhId, d.donvi, e.ten as nhanhang, d.pk_seq as clId, a.trangthai, isnull(f.giabanlechuan,'0') as giablc,a.MA_FAST as ma_fast, isnull(k.stt, 9) as sapxep ";
		query = query + " from sanpham a left join donvikinhdoanh b on a.dvkd_fk = b.pk_seq left join chungloai c on a.chungloai_fk = c.pk_seq left join donvidoluong d on a.dvdl_fk = d.pk_seq left join nhanhang e on a.nhanhang_fk = e.pk_seq ";
		query = query  + " left join banggiablc_sanpham f on a.pk_seq = f.sanpham_fk left join banggiabanlechuan g on f.bgblc_fk = g.pk_seq ";
		query =  query  +  " left join erp_loaisanpham k on k.pk_seq = a.loaisanpham_fk  where 1=1  ";
				
		    
    	if (masp.length()>0){
			query = query + " and upper(a.timkiem) like upper('%" + masp + "%')";	
    	}

	    if (tensp.length()>0){
	    	   geso.traphaco.distributor.util.Utility util = new geso.traphaco.distributor.util.Utility();
			query = query + " and upper(dbo.ftBoDau(a.ten)) like upper(N'%"+ util.replaceAEIOU(tensp.trim()).trim()+"%')";	
    	}
    	
    	if (dvkdId.length()>0){
			query = query + " and b.pk_seq = '" + dvkdId + "'";	
    	}

    	if (nhId.length()>0){
			query = query + " and e.pk_seq = '" + nhId + "'";   		
    	}
    	
    	if (clId.length()>0){
			query = query + " and c.pk_seq = '" + clId + "'";    		
    	}

    	if(trangthai.length() > 0){
    		query = query + " and a.trangthai = '" + trangthai + "'";   		
    	}
    
    	if(loaispId.length()>0)
    	{
    		query = query + " and k.pk_seq = '" + loaispId + "'";   
    	}
    	
    	// Giá sản phẩm từ
    	
    	// Giá sản phẩm đến
    	// Thuế GTGT từ
    	// Thuế GTGT đến
    	// hoạt chất
    	//Đơn vị tính
    	// Nhà Sản xuất
    	// Quy cách
    	/*
    	query = query + "union select a.pk_seq, a.ma, a.ten, '' as donvi, '' as dvkd, null as dvkdId, null as nhId, null as nhanhang, null as clId, null as chungloai, a.trangthai, '0' as giablc from sanpham a  where a.pk_seq not in (select a.pk_seq from sanpham a, donvidoluong b, nhanhang c, chungloai d,  donvikinhdoanh g, banggiabanlechuan h, banggiablc_sanpham i  where a.dvdl_fk=b.pk_seq and a.nhanhang_fk = c.pk_seq and a.chungloai_fk = d.pk_seq and c.dvkd_fk = g.pk_seq and g.pk_seq = h.dvkd_fk and h.pk_seq= i.bgblc_fk and a.pk_seq = i.sanpham_fk) ";
    	if (masp.length()>0){
			query = query + " and upper(a.ma) like upper('%" + masp + "%')";
			
    	}

	    if (tensp.length()>0){
			query = query + " and upper(a.ten) like upper('%" + tensp + "%')";
			
    	}
    	
    	if(trangthai.length() > 0){
    		query = query + " and a.trangthai = '" + trangthai + "'";
    		
    	}
    	
    	query = query + " order by trangthai, ma";
    	 */
    	
 
    	

    	//System.out.println("Chuoi Tim Kiem "+query);
    	return query;
    	
	}	

	private String getQueryExcel(HttpServletRequest request, IThongtinsanphamList obj)
	{
		String masp = request.getParameter("masp");
    	if (masp == null)
    		masp = "";
    	obj.setMasp(masp);

		String tensp = request.getParameter("tensp");
    	if (tensp == null)
    		tensp = "";
    	obj.setTensp(tensp);
    	
    	String dvkdId = request.getParameter("dvkdId");
    	if (dvkdId == null)
    		dvkdId = "";    	
    	obj.setDvkdId(dvkdId);
    	
    	String nhId = request.getParameter("nhId");
    	if (nhId == null)
    		nhId = "";    	
    	obj.setNhId(nhId);
    	
    	String clId = request.getParameter("clId");
    	if (clId == null)
    		clId = "";    	
    	obj.setClId(clId);
    	
    	String trangthai = request.getParameter("trangthai");   	
    	if (trangthai == null)
    		trangthai = "";    	
	
    	if (trangthai.equals("2"))
    		trangthai = "";
    	
    	obj.setTrangthai(trangthai);
    	
    	String   query =
    			"select  b.pk_seq as dvkdId,b.donvikinhdoanh as dvkd,e.ten as nhanhang, d.pk_seq as clId,\n  "+ 
    			"	n.TEN as NganhHang,p.TEN as PackSize,qc.SOLUONG1 as QuyCach,qvc.SOLUONG2 as GoiVanChuyen\n  "+ 
    			"	,a.pk_seq, a.ma,a.MACHUAN ,a.ten, a.tenchuan , a.trongluong,\n  "+ 
    			"	a.thetich, c.ten as chungloai, e.pk_seq as nhId, d.donvi, a.trangthai, isnull(f.giabanlechuan,'0') as giablc  \n  "+ 
    			"	from sanpham a \n  "+ 
    			"left join donvikinhdoanh b on a.dvkd_fk = b.pk_seq \n  "+ 
    			"left join chungloai c on a.chungloai_fk = c.pk_seq \n  "+ 
    			"left join donvidoluong d on a.dvdl_fk = d.pk_seq\n  "+ 
    			"left join nhanhang e on a.nhanhang_fk = e.pk_seq  \n  "+ 
    			"left join banggiablc_sanpham f on a.pk_seq = f.sanpham_fk\n  "+ 
    			"left join banggiabanlechuan g on f.bgblc_fk = g.pk_seq \n  "+ 
    			"left join NGANHHANG n on n.PK_SEQ=a.NGANHHANG_FK\n  "+ 
    			"left join Packsize p  on p.PK_SEQ=a.PACKSIZE_FK\n  "+ 
    			"left join QUYCACH qc on qc.SANPHAM_FK=a.PK_SEQ and qc.DVDL2_FK=100018\n  "+ 
    			"left join QUYCACH qvc on qvc.SANPHAM_FK=a.PK_SEQ and qvc.DVDL2_FK=100052 " +
    			"where 1=1 and trangthai = 1";
    	if (masp.length()>0){
			query = query + " and upper(a.ma) like upper('%" + masp + "%')";	
    	}

	    if (tensp.length()>0){
	    	   geso.traphaco.distributor.util.Utility util = new geso.traphaco.distributor.util.Utility();
			query = query + " and upper(dbo.ftBoDau(a.ten)) like upper(N'%"+ util.replaceAEIOU(tensp.trim()).trim()+"%')";	
    	}
    	
    	if (dvkdId.length()>0){
			query = query + " and b.pk_seq = '" + dvkdId + "'";	
    	}

    	if (nhId.length()>0){
			query = query + " and e.pk_seq = '" + nhId + "'";   		
    	}
    	
    	if (clId.length()>0){
			query = query + " and c.pk_seq = '" + clId + "'";    		
    	}

    	if(trangthai.length() > 0){
    		query = query + " and a.trangthai = '" + trangthai + "'";   		
    	}
    	System.out.println("Excel "+query);
    	return query;
    	
	}	
	
	
	boolean kiemtra(String sql,String id)
	{
		dbutils db =new dbutils();
		String query = "select count(*) as num from " + sql + " where sanpham_fk ='"+ id +"'";
	
    	ResultSet rs = db.get(query);
		try 
		{	
			//kiem tra ben san pham
			while(rs.next())
			{ 
				if(rs.getString("num").equals("0"))
					return false;
			}
		} 
		catch (SQLException e) {}
		return true;
	}
	private void Delete(String id){
		dbutils db = new dbutils();
	
		if(kiemtra("nhaphang_sp",id)||kiemtra("donhangtrave_sanpham",id) || kiemtra("dieukienkm_sanpham",id) || kiemtra("phieuxuatkho_spkm",id) || kiemtra("bosanpham_sanpham",id) || kiemtra("trakhuyenmai_sanpham",id)||kiemtra("donhangthuhoi_sanpham",id)||kiemtra("phieuxuatkho_sanpham",id) || kiemtra("denghidathang_sp",id) ||kiemtra("phieuthuhoi_sanpham",id) || kiemtra("donhang_sanpham",id) || kiemtra("dieuchinhtonkho_sp",id) || kiemtra("dontrahang_sp",id) || kiemtra("bosanpham_sanpham",id) ||kiemtra("phieuxuatkho_spkm",id) )
		{
         obj.setMsg("San pham nay da ton tai trong cac don hang roi, nen khong the xoa duoc"); 			
		}
		else
		{	
			
			String sql = "delete from nhomsanpham_sanpham where sp_fk='" + id + "'";
			if(!db.update(sql))
			{
				db.update("rollback");
				obj.setMsg("Khong the xoa :"+sql);
				return;
			}
			
			sql = "delete from quycach where sanpham_fk='" + id + "'";
			if(!db.update(sql))
			{
				db.update("rollback");
				obj.setMsg("Khong the xoa :"+sql);
				return;
			}
			
			sql = "DELETE FROM BANGGIABLC_SANPHAM WHERE SANPHAM_FK='" + id + "'";
			if(!db.update(sql))
			{
				db.update("rollback");
				obj.setMsg("Khong the xoa :"+sql);
				return;
			}
			
			
//			sql = "delete from bgbanlenpp_sanpham where sanpham_fk='" + id + "'";
//			if(!db.update(sql))
//			{
//				db.update("rollback");
//				obj.setMsg("Khong the xoa :"+sql);
//				return;
//			}
			
			sql = "delete from bgmuanpp_sanpham where sanpham_fk='" + id + "'";
			if(!db.update(sql))
			{
				db.update("rollback");
				obj.setMsg("Khong the xoa :"+sql);
				return;
			}
			
			sql = "delete from banggiast_sanpham where sanpham_fk='" + id + "'";
			if(!db.update(sql))
			{
				db.update("rollback");
				obj.setMsg("Khong the xoa :"+sql);
				return;
			}
			
			sql = "delete from nhapp_kho where sanpham_fk='" + id + "'";
			if(!db.update(sql))
			{
				db.update("rollback");
				obj.setMsg("Khong the xoa :"+sql);
				return;
			}
			
			sql = "delete from nhanvien_sanpham where sanpham_fk = '" + id + "'";
			if(!db.update(sql))
			{
				db.update("rollback");
				obj.setMsg("Khong the xoa :"+sql);
				return;
			}
			
			sql = "delete from BANGGIAMUA_SANPHAM where sanpham_fk =" + id + "";
			if(!db.update(sql))
			{
				db.update("rollback");
				obj.setMsg("Khong the xoa :"+sql);
				return;
			}
			
			sql = "delete from sanpham_chitiet where sp_fk =" + id + "";
			if(!db.update(sql))
			{
				db.update("rollback");
				obj.setMsg("Khong the xoa :"+sql);
				return;
			}
			
			sql = "delete from sanpham where pk_seq=" + id + "";
			if(!db.update(sql))
			{
				db.update("rollback");
				obj.setMsg("Khong the xoa :"+sql);
				return;
			}
			
			db.shutDown();
		}		
	}
	
	private void CreateStaticData(Workbook workbook, String query) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    
		dbutils db = new dbutils();
		
		ResultSet rs = db.get(query);

		int i = 3;
		if(rs != null)
		{
			try 
			{
				Cell cell = null;
				
				Style style;
				Font font2 = new Font();			
				font2.setSize(11);
				
				int stt = 1;
				while(rs.next())
				{
					cell = cells.getCell("A" + Integer.toString(i));	cell.setValue( stt ); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("B" + Integer.toString(i));	cell.setValue( rs.getString("MA") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue( rs.getString("TEN") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue( rs.getString("donvi") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue( rs.getString("dvkd") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue( rs.getString("nganhhang") ); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue( rs.getString("nhanhang") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("H" + Integer.toString(i));	cell.setValue( rs.getString("chungloai") ); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("I" + Integer.toString(i));	cell.setValue( rs.getString("dangtrinhbay") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("J" + Integer.toString(i));	cell.setValue( rs.getString("quycachdonggoi") ); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("K" + Integer.toString(i));	cell.setValue( rs.getString("tieuchuanchatluong") ); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					
					cell = cells.getCell("L" + Integer.toString(i));	cell.setValue( rs.getString("nguongocnguyenlieu") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("M" + Integer.toString(i));	cell.setValue( rs.getString("tieuchuannsx") );	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					
					cell = cells.getCell("N" + Integer.toString(i));	cell.setValue( rs.getString("nuocsx") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("O" + Integer.toString(i));	cell.setValue( rs.getString("TENXUATHOADON") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("P" + Integer.toString(i));	cell.setValue( rs.getString("NGUONGOCNHAPKHAU") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("Q" + Integer.toString(i));	cell.setValue( rs.getString("tenviettat") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("R" + Integer.toString(i));	cell.setValue( rs.getString("loaisp") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("S" + Integer.toString(i));	cell.setValue( rs.getString("HAMLUONG") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("T" + Integer.toString(i));	cell.setValue( rs.getString("tenhoatchat") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("U" + Integer.toString(i));	cell.setValue( rs.getString("nguongoc") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("V" + Integer.toString(i));	cell.setValue( rs.getString("thuexuat") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("W" + Integer.toString(i));	cell.setValue( rs.getString("giabancothue") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("X" + Integer.toString(i));	cell.setValue( rs.getString("giatheodvtnhonhat") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("Y" + Integer.toString(i));	cell.setValue( rs.getString("stttheott40") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("Z" + Integer.toString(i));	cell.setValue( rs.getString("nhomsphtotc") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("AA" + Integer.toString(i));	cell.setValue( rs.getString("SPMOI") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("AB" + Integer.toString(i));	cell.setValue( rs.getString("SPCHULUC") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("AC" + Integer.toString(i));	cell.setValue( rs.getString("trangthai") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("AD" + Integer.toString(i));	cell.setValue( rs.getString("VISA") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("AE" + Integer.toString(i));	cell.setValue( rs.getString("NGAYCAP") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("AF" + Integer.toString(i));	cell.setValue( rs.getString("hethanvisa") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("AG" + Integer.toString(i));	cell.setValue( rs.getString("KKG") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("AH" + Integer.toString(i));	cell.setValue( rs.getString("NSX") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("AI" + Integer.toString(i));	cell.setValue( rs.getString("NKK") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("AJ" + Integer.toString(i));	cell.setValue( rs.getString("NXB") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("AK" + Integer.toString(i));	cell.setValue( rs.getString("NhomHang") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("AL" + Integer.toString(i));	cell.setValue( rs.getString("NhomSP") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					
					
					
					i++;
					stt ++;
				}
				rs.close();
			}
			catch (Exception e){ e.printStackTrace(); }
		}
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private void setCellBorderStyle(Cell cell) {
		Style style = cell.getStyle();
		style.setHAlignment(HorizontalAlignmentType.CENTRED);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		cell.setStyle(style);
	}
}