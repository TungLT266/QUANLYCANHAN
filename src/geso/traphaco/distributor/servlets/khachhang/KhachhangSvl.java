package geso.traphaco.distributor.servlets.khachhang;

import geso.traphaco.distributor.beans.khachhang.*;
import geso.traphaco.distributor.beans.khachhang.imp.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Font;
import com.aspose.cells.HorizontalAlignmentType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class KhachhangSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private int items = 50;
	private int splittings = 20;
	
	
    public KhachhangSvl() 
    {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)) {
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else {
		
		IKhachhangList obj;
		PrintWriter out; 
		
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out  = response.getWriter();

	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String khId = util.getId(querystring);
	    obj = new KhachhangList();
	    String msg="";
	    if (action.equals("delete"))
	    {	   		  	    	
	    	try
        {
	    		msg= Delete(khId, userId, obj);
        } catch (SQLException e)
        {
	        e.printStackTrace();
        }
	    	obj.setMsg(msg);
	    }
	   	    
	    
	    
	    settingPage(obj);
	    	
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Distributor/KhachHang.jsp";
		response.sendRedirect(nextJSP);
		}
	}

	private void settingPage(IKhachhangList obj) {
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
		
		OutputStream out = response.getOutputStream();	
			
		IKhachhangList obj = new KhachhangList();
	
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	  
	    
	    Utility util = new Utility();
	    userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	   
	          
	    if (action.equals("new")){
	    	// Empty Bean for distributor
	    	IKhachhang khBean = (IKhachhang) new Khachhang("");
	    	khBean.setUserId(userId);
	    	khBean.createRS();
	    	
	    	 util = new Utility();
	    	util.getIdNhapp(userId);
	    	
	    	/*
	    	 * 0 :Chi Nhanh cap 1 
	    	 * 1 :Chi Nhanh cap 2
	    	 * 2 : Quầy bán buôn
	    	 * 3 : Quầy TraphacoERP
	   	   * 4 : Doi tac
	   	   * 5: Chi nhanh doi tac
	    	 */
	    	
	    	/*	Khi tạo mới khách hàng tại Chi nhánh cấp 1, chi nhánh cấp 2, quầy bán buôn, quầy traphco:
	    	mặc định ô thanh toán là Hóa đơn còn đối với Đối tác, chi nhánh đối tác -- mặc định ô thanh toán là tiền mặt
	    	*/
	    	
	    	if(util.getLoaiNpp().equals("0") ||util.getLoaiNpp().equals("1") ||util.getLoaiNpp().equals("2"))
  			{
  			 	khBean.setThanhtoan("1");
  			}
	    	else if(util.getLoaiNpp().equals("4") ||util.getLoaiNpp().equals("5"))
	    	{
	    		khBean.setThanhtoan("0");
	    	}
	    	
	   
	    	if(util.getLoaiNpp().equals("0") ||util.getLoaiNpp().equals("1") ||util.getLoaiNpp().equals("2")||util.getLoaiNpp().equals("3"))
  			{
  			 	khBean.setThanhtoanQuy("1");
  			}
	    	else if(util.getLoaiNpp().equals("4") ||util.getLoaiNpp().equals("5"))
	    	{
	    		khBean.setThanhtoanQuy("0");
	    	}
	    	
	    	// Save Data into session
	    	session.setAttribute("khBean", khBean);
    		String nextJSP = "/TraphacoHYERP/pages/Distributor/KhachHangNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    
	    settingPage(obj);
	    
	    if (action.equals("search"))
	    {	    
	    	
	    	String search = getSearchQuery(request, obj);
	    	//search = search + " and a.npp_fk='" + userId + "' order by a.ten";
	    	
	    	//obj = new KhachhangList(search);
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Distributor/KhachHang.jsp");	    		    	
	    }
	    else if (action.equals("excel"))
	    {
	    	obj.setQuery(getSearchQuery(request, (IKhachhangList) obj));
	    	
	    	try
		    {
		    	response.setContentType("application/vnd.ms-excel");
		        response.setHeader("Content-Disposition", "attachment; filename=DanhsachKhachhang_"+getDateTime()+".xls");
		        
		        Workbook workbook = new Workbook();
		    	
		        CreateStaticHeader(workbook, "Nguyen Duy Hai");
			    CreateStaticData(workbook, getQueryExcel(request, (IKhachhangList) obj));
			
			     //Saving the Excel file
			     workbook.save(out);
		    }
		    catch (Exception ex)
		    {
		        obj.setMsg("Khong the tao pivot.");
		    }
	    	
			obj.setUserId(userId);
			
			
	    	session.setAttribute("obj", obj);

    		session.setAttribute("userId", userId);
    		return;
	    		
    		//response.sendRedirect("/TraphacoHYERP/pages/Distributor/KhachHang.jsp");	    		
	    }
	    else if (action.equals("excel_hoadon"))  //THU NGHIEM
	    {
	    	System.out.println("--------BAT DAU IN................"); 
	    	try
		    {
		    	response.setContentType("application/vnd.ms-excel");
		        response.setHeader("Content-Disposition", "attachment; filename=HoaDonInTuGESO.xls");
		        
		        Workbook workbook = new Workbook();
		    	
		        CreateStaticHeader_HD(workbook, "HoaDon");
			    CreateStaticData_HD(workbook);
			
			     //Saving the Excel file
			     workbook.save(out);
		    }
		    catch (Exception ex)
		    {
		        obj.setMsg("Khong the tao pivot.");
		    }
	    	
			System.out.println("--------IN XONG................");   		
	    }
	    else  if(action.equals("view") || action.equals("next") || action.equals("prev")){
	    	
	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
	    	obj.setUserId(userId);

	    	obj.init(search);
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	session.setAttribute("obj", obj);
	    	response.sendRedirect("/TraphacoHYERP/pages/Distributor/KhachHang.jsp");
	    }
	    
	    
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IKhachhangList obj)
	{		
  		
		Utility util = new Utility();
		String ten = util.antiSQLInspection(request.getParameter("khTen"));
    	if ( ten == null)
    		ten = "";
    	obj.setTen(ten);
    	
    	String nppId = util.antiSQLInspection(request.getParameter("nppId"));
    	if ( nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
    	String hchId = util.antiSQLInspection(request.getParameter("hchTen"));
    	if (hchId == null)
    		hchId = "";    	
    	obj.setHchId(hchId);
    	
    	String kbhId = util.antiSQLInspection(request.getParameter("kbhTen"));
    	if (kbhId == null)
    		kbhId = "";    	
    	obj.setKbhId(kbhId);
    	
    	String vtchId = util.antiSQLInspection(request.getParameter("vtchTen"));
    	if (vtchId == null)
    		vtchId = "";    	
    	obj.setVtId(vtchId);
    	
    	String lchId = util.antiSQLInspection(request.getParameter("lchTen"));
    	if (lchId == null)
    		lchId = "";    	
    	obj.setLchId(lchId);
    	
    	
    	String diadiemId = util.antiSQLInspection(request.getParameter("diadiemId"));
    	if (diadiemId == null)
    		diadiemId = "";    	
    	obj.setDiadiemId(diadiemId);
    	
    	
    	String xuatkhau = util.antiSQLInspection(request.getParameter("xuatkhau"));
    	if (xuatkhau == null)
    		xuatkhau = "0";    	
    	obj.setXuatkhau(xuatkhau);
    	
    	String diachi = util.antiSQLInspection(request.getParameter("diachi"));
    	if (diachi == null)
    		diachi = "";    	
    	obj.setDiachi(diachi.trim());
    	
    	
    	String maFAST = util.antiSQLInspection(request.getParameter("maFAST"));
    	if (maFAST == null)
    		maFAST = "";    	
    	obj.setMaFAST(maFAST);
    	
    	
    	String ddkdId = util.antiSQLInspection(request.getParameter("ddkdId"));
    	if (ddkdId == null)
    		ddkdId = "";    	
    	obj.setDdkdId(ddkdId);
    	
    	
    	String tbhId = util.antiSQLInspection(request.getParameter("tbhId"));
    	if (tbhId == null)
    		tbhId = "";    	
    	obj.setTbhId(tbhId);
    	
    	String trangthai = util.antiSQLInspection(request.getParameter("trangthai")); 	
    	if (trangthai == null)
    		trangthai = "";    		
    	obj.setTrangthai(trangthai);
    	
    	String tungay = util.antiSQLInspection(request.getParameter("tungay")); 	
    	if (tungay == null)
    		tungay = "";    		
    	obj.setTungay(tungay);
    	
    	String denngay = util.antiSQLInspection(request.getParameter("denngay")); 	
    	if (denngay == null)
    		denngay = "";    		
    	obj.setDenngay(denngay);
    	System.out.println("[den ngay] : " + obj.getDenngay());
    	
    	String loaiKH = util.antiSQLInspection(request.getParameter("loaikh")); 	
    	if (loaiKH == null)
    		loaiKH = "";    		
    	obj.setloaiKH(loaiKH);
    	
    	String hopdong = util.antiSQLInspection(request.getParameter("hopdong")); 	
    	if (hopdong == null)
    		hopdong = "";    		
    	obj.setHopdong(hopdong);
    	
    	String hkhid = util.antiSQLInspection(request.getParameter("hkhid")); 	
    	if (hkhid == null)
    		hkhid = "";    		
    	obj.setHangkh(hkhid);
    	
   	String query = 
    			
    				"	select  f.pk_seq as hangkh,ROW_NUMBER() OVER(ORDER BY a.pk_seq DESC) AS 'stt', isnull(a.mafast,'') as mafast , a.pk_seq as khId, a.smartid, a.ten as khTen, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua, d.chietkhau, d.pk_seq as mckId,  "+ 
    					"		e.diengiai as kbhTen, e.pk_seq as kbhId, f.hang as hchTen, f.pk_seq as hchId, g.loai as lchTen, g.pk_seq as lchId, h.ten as nppTen, h.pk_seq as nppId,   "+
    					"		k.sotienno as ghcnTen, k.pk_seq as ghcnId, l.ten as bgstTen, l.pk_seq as bgstId, m.vitri as vtchTen,  "+
    					"		m.pk_seq as vtchId, a.dienthoai, a.diachi,  "+
    					"	STUFF      "+
    					"	(    "+
    					"		(   "+
    					"			select DISTINCT TOP 100 PERCENT ' , ' + tbh.DIENGIAI   "+
    					"			from KHACHHANG_TUYENBH khtbh inner join TUYENBANHANG tbh on tbh.PK_SEQ=khtbh.TBH_FK   "+ 
    					"			where khtbh.KHACHHANG_FK=a.PK_SEQ and tbh.NPP_FK=a.NPP_FK  "+ 
    					"			ORDER BY ' , ' + tbh.DIENGIAI      "+
    					"			FOR XML PATH('')      "+
    					"		 ), 1, 2, ''   "+
    					"	) + ' '  AS tbhTen,a.CHUCUAHIEU  ,a.MaHD,n.ten as LoaiCH  "+ 
    					"  from    "+
    					"  khachhang a inner join nhanvien b on a.nguoitao = b.pk_seq   "+ 
    					"  inner join nhanvien c on a.nguoisua = c.pk_seq    "+
    					"  left join mucchietkhau d on a.chietkhau_fk = d.pk_seq   "+
    					"  left join kenhbanhang e on a.kbh_fk = e.pk_seq    "+
    					"  left join hangcuahang f on a.hch_fk = f.pk_seq    "+
    					"  left join loaicuahang g on a.lch_fk = g.pk_seq    "+
    					"  inner join nhaphanphoi h on a.npp_fk = h.pk_seq    "+
    					"  left join gioihancongno k on a.ghcn_fk = k.pk_seq    "+
    					"  left join banggiasieuthi l on a.bgst_fk = l.pk_seq    "+
    					"  left join vitricuahang m on a.vtch_fk = m.pk_seq    "+
    					" left join LOAIKHACHHANG n on a.XuatKhau= n.pk_seq  ";
   	
 /*  	String	query="select  ROW_NUMBER() OVER(ORDER BY a.pk_seq DESC) AS 'stt',a.MASOTHUE,a.cmnd,case when dbo.parseNullDay(a.NgaySinh,'')='' then '' else day(a.NgaySinh) end as ngaysinh, \n"+
			  "		case when dbo.parseNullDay(a.NgaySinh,'')='' then '' else month(a.NgaySinh) end as thangsinh, \n"+
			  "		case when dbo.parseNullDay(a.NgaySinh,'')='' then '' else year(a.NgaySinh) end as namsinh, npp.TEN as tennpp,N'Tháng '+cast(month(a.NgayKyHD)as nvarchar(2)) thangkyhd,a.MaHD,isnull(a.mafast,'') as mafast , \n"+
			  "		a.pk_seq as khId, a.smartid, a.ten as khTen, isnull(a.MST_CaNhan,'') as MST_CaNhan,a.trangthai, \n"+
			  "		a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua, d.chietkhau, d.pk_seq as mckId,   \n"+		
			  "		e.diengiai as kbhTen, e.pk_seq as kbhId, f.hang as hchTen, f.pk_seq as hchId, \n"+
			  "		g.loai as lchTen, g.pk_seq as lchId, h.ten as nppTen, h.pk_seq as nppId,   		\n"+
			  "		k.sotienno as ghcnTen, k.pk_seq as ghcnId, l.ten as bgstTen, l.pk_seq as bgstId, m.vitri as vtchTen,  		 \n"+
			  "		m.pk_seq as vtchId, a.dienthoai, a.diachi,  isnull(a.MASOTHUE,'') as masothue, ISNULL(a.cmnd,'') as cmnd  \n"+
			  "		, ISNULL(a.MaHD,'') as maHD, n.ten as loaKH , 	\n"+
			  "		STUFF      	(    		(   			\n"+
			  "		select DISTINCT TOP 100 PERCENT ' , ' +'VT'+ cast(tbh.NGAYID as nvarchar(10))+''+tbh.DIENGIAI   \n"+			
			  "		from KHACHHANG_TUYENBH khtbh inner join TUYENBANHANG tbh on tbh.PK_SEQ=khtbh.TBH_FK   		 \n"+	
			  "		where khtbh.KHACHHANG_FK=a.PK_SEQ and tbh.NPP_FK=a.NPP_FK  			\n"+
			  "	 	ORDER BY ' , ' +'VT'+ cast(tbh.NGAYID as nvarchar(10))+''+tbh.DIENGIAI     			FOR XML PATH('')      		 ), 1, 6, ''   	) + ' '  AS tbhTen, \n"+
			  "		a.CHUCUAHIEU,a.NgaykyHd,(select hch.diengiai from HANGCUAHANG hch where hch.pk_seq=a.hch_fk  ) as hangkhachhang  ,ISNULL(loaihopdong,'') as loaihopdong    \n"+
			  "		from      khachhang a  \n"+
			  "		inner join nhanvien b on a.nguoitao = b.pk_seq      \n"+
			  "		inner join nhanvien c on a.nguoisua = c.pk_seq       \n"+
			  "		left join mucchietkhau d on a.chietkhau_fk = d.pk_seq     \n"+
			  "		left join kenhbanhang e on a.kbh_fk = e.pk_seq      \n"+
			  "		left join hangcuahang f on a.hch_fk = f.pk_seq      \n"+
			  "		left join loaicuahang g on a.lch_fk = g.pk_seq      \n"+
			  "		inner join nhaphanphoi h on a.npp_fk = h.pk_seq      \n"+
			  "		left join gioihancongno k on a.ghcn_fk = k.pk_seq     \n"+
			  "		left join banggiasieuthi l on a.bgst_fk = l.pk_seq     \n"+
			  "		left join vitricuahang m on a.vtch_fk = m.pk_seq     \n"+
			  "		left join LOAIKHACHHANG n on a.XuatKhau= n.pk_seq  \n"+
			  "		left join NHAPHANPHOI npp on npp.PK_SEQ=a.NPP_FK \n"+
			  "		where a.npp_fk='"+nppId+"' " ;
    			*/

     
    			
    	if (ten.length()>0)
    	{ 
    		
			//query = query + " and ( upper(a.pk_seq) like upper(N'%" + util.replaceAEIOU(ten) + "%') or upper(a.ten) like upper(N'%" + util.replaceAEIOU(ten) + "%')) ";	
			query = query + " and ( upper(dbo.ftBoDau(a.ten)) like upper(N'%" + util.replaceAEIOU(ten) + "%') or a.smartid like upper(N'%" + ten.trim()+ "%')) ";			
    	}
    	
    	if (kbhId.length()>0){
			query = query + " and a.kbh_fk ='" + kbhId + "'";	
    	}
    	
    	if (hchId.length()>0){
			query = query + " and a.hch_fk='" + hchId + "'";			
    	}
    	
    	if (vtchId.length()>0){
			query = query + " and a.vtch_fk='" + vtchId + "'";			
    	}
    	
    	if (lchId.length()>0){
			query = query + " and a.lch_fk='" + lchId + "'";			
    	}
    	
    	
    	if(diadiemId.length()>0)
    	{
    		query+=" and a.diadiem_fk="+diadiemId+" ";
    	}
    	
    	if(diachi.length()>0)
    	{
    		query+=" and (upper(dbo.ftBoDau(a.diachi)) like (N'%" + util.replaceAEIOU(diachi) + "%') )  ";
    	}
    	
    	if(maFAST.length()>0)
    	{
    		query+= " and upper(dbo.ftBoDau(a.MAFAST)) like  upper((N'%" + util.replaceAEIOU(maFAST) + "%')) ";
    	}
    	
    	if(ddkdId.length()>0)
    	{
    		query+= " and a.pk_Seq in (select khachhang_fk from KHACHHANG_TUYENBH where tbh_fk in (select PK_SEQ from tuyenbanhang where ddkd_Fk='"+ddkdId+"')) ";
    	}
    	
    	if(tbhId.length()>0)
    	{
    		query+= " and a.pk_Seq in (select KHACHHANG_FK from KHACHHANG_TUYENBH where tbh_fk ='"+tbhId+"' ) ";
    	}
    	
    	if (trangthai.length() > 0)
    	{
    		query = query + " and a.trangthai='" + trangthai + "'";
    	}
    	
    	if (tungay.length() > 0)
    	{
    		query = query + " and a.NGAYTAO>='" + tungay + "'";
    	}
    	
    	if (denngay.length() > 0)
    	{
    		query = query + " and a.NGAYTAO<='" + denngay + "'";
    	}
    	
    	if (loaiKH.length() > 0)
    	{
    		query = query + " and n.pk_seq='" + loaiKH + "'";
    	}
    
    	if (hopdong.length() > 0)
    	{
    		query = query + " and a.KhongKyHopDong='" + hopdong + "'";
    	}

    	if (hkhid.length() > 0)
    	{
    		query = query + " and f.pk_seq='" + hkhid + "'";
    	}
    	
    	System.out.println("Query tìm kiếm: " + query + "\n");

    	
    	return query;
	}	
	
	private String Delete(String id, String userId,IKhachhangList obj) throws SQLException
	{
		dbutils db = new dbutils();
		Utility util = new Utility();
		String nppId=util.getIdNhapp(userId);
		try 
		{
			String query="select count(*) as SoDong From DONHANG WHERE KHACHHANG_FK='"+id+"'";
			ResultSet rs= db.get(query);
			int SoDong=0;
			while(rs.next())
			{
				SoDong=rs.getInt("SoDong");
			}
			if(SoDong>0)
			{
				return "Khách Hàng đã phát sinh Số Liệu (DONHANG)!Không thể xóa !";
			}
			query="select count(*) as SoDong From DONHANGTRAVE WHERE KHACHHANG_FK='"+id+"'";
			rs= db.get(query);
			while(rs.next())
			{
				SoDong=rs.getInt("SoDong");
			}
			if(SoDong>0)
			{
				return "Khách Hàng đã phát sinh Số Liệu(DONHANGTRAVE)!Không thể xóa !";
			}			
		
			db.getConnection().setAutoCommit(false);

			query = "delete from khachhang_nkhachhang where khachhang_fk='" + id + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể xóa Khách Hàng "+query;
			}
			
			query = "delete from khachhang_tuyenBH where khachhang_fk='" + id + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể xóa Khách Hàng "+query;
			}
			
			query = "delete from nvgn_kh where khachhang_fk = '"+ id +"'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể xóa Khách Hàng "+query;
			}
			
			query = "delete from KHACHHANG_TUYENBH where khachhang_fk = '"+ id +"'";
			
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể xóa Khách Hàng "+query;
			}
			
			query = "delete from KHACHHANG_ANHCHUP where khachhang_fk = '"+ id +"'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể xóa Khách Hàng "+query;
			}
			
			query = "delete from KHACHHANG_CONGNO where khachhang_fk = '"+ id +"'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể xóa Khách Hàng "+query;
			}
			
			query = "delete from KhachHang_DaiDienKinhDoanh where khachhang_fk = '"+ id +"'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể xóa Khách Hàng "+query;
			}
			
			query = "delete from KHACHHANG_KHODOITHU where KH_FK = '"+ id +"'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể xóa Khách Hàng "+query;
			}
			
			query = "delete from KHACHHANG_MUCTIEUNGAY where khachhang_fk = '"+ id +"'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể xóa Khách Hàng "+query;
			}
			
			query = "delete from KHACHHANG_TOADO_LOG where khachhang_fk = '"+ id +"'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể xóa Khách Hàng "+query;
			}
			
			query = "delete from KHACHHANG_YKIEN where khachhang_fk = '"+ id +"'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể xóa Khách Hàng "+query;
			}
			
			
			query = "delete from ddkd_khachhang where khachhang_fk = '"+ id +"'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Không thể xóa Khách Hàng "+query;
			}
			
			
			query = "delete from khachhang where pk_seq = '" + id + "'  ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				System.out.println("::::"+query);
				return "Không thể xóa Khách Hàng "+query;
			}
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}
		catch(Exception e) 
		{
			db.getConnection().rollback();
			e.printStackTrace();
			return "Không thể xóa Khách Hàng,do đã phát sinh dữ liệu!Exception";
		}
		finally
		{
			db.shutDown();	
		}
		return "";
	}
	
	private String getQueryExcel(HttpServletRequest request, IKhachhangList obj)
	{
		Utility util = new Utility();
		String ten = util.antiSQLInspection(request.getParameter("khTen"));
    	if ( ten == null)
    		ten = "";
    	obj.setTen(ten);
    	
    	String nppId = util.antiSQLInspection(request.getParameter("nppId"));
    	if ( nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
    	String hchId = util.antiSQLInspection(request.getParameter("hchTen"));
    	if (hchId == null)
    		hchId = "";    	
    	obj.setHchId(hchId);
    	
    	String kbhId = util.antiSQLInspection(request.getParameter("kbhTen"));
    	if (kbhId == null)
    		kbhId = "";    	
    	obj.setKbhId(kbhId);
    	
    	String vtchId = util.antiSQLInspection(request.getParameter("vtchTen"));
    	if (vtchId == null)
    		vtchId = "";    	
    	obj.setVtId(vtchId);
    	
    	String lchId = util.antiSQLInspection(request.getParameter("lchTen"));
    	if (lchId == null)
    		lchId = "";    	
    	obj.setLchId(lchId);
    	
    	
    	String diadiemId = util.antiSQLInspection(request.getParameter("diadiemId"));
    	if (diadiemId == null)
    		diadiemId = "";    	
    	obj.setDiadiemId(diadiemId);
    	
    	
    	String xuatkhau = util.antiSQLInspection(request.getParameter("xuatkhau"));
    	if (xuatkhau == null)
    		xuatkhau = "0";    	
    	obj.setXuatkhau(xuatkhau);
    	
    	String diachi = util.antiSQLInspection(request.getParameter("diachi"));
    	if (diachi == null)
    		diachi = "";    	
    	obj.setDiachi(diachi.trim());
    	
    	
    	String maFAST = util.antiSQLInspection(request.getParameter("maFAST"));
    	if (maFAST == null)
    		maFAST = "";    	
    	obj.setMaFAST(maFAST.trim());
    	
    	
    	String ddkdId = util.antiSQLInspection(request.getParameter("ddkdId"));
    	if (ddkdId == null)
    		ddkdId = "";    	
    	obj.setDdkdId(ddkdId);
    	
    	
    	String tbhId = util.antiSQLInspection(request.getParameter("tbhId"));
    	if (tbhId == null)
    		tbhId = "";    	
    	obj.setTbhId(tbhId);
    	
    	String trangthai = util.antiSQLInspection(request.getParameter("trangthai")); 	
    	if (trangthai == null)
    		trangthai = "";    		
    	obj.setTrangthai(trangthai);
    	
    	String tungay = util.antiSQLInspection(request.getParameter("tungay")); 	
    	if (tungay == null)
    		tungay = "";    		
    	obj.setTungay(tungay);
    	
    	String denngay = util.antiSQLInspection(request.getParameter("denngay")); 	
    	if (denngay == null)
    		denngay = "";    		
    	obj.setDenngay(denngay);
    	
    	String loaiKH = util.antiSQLInspection(request.getParameter("loaikh")); 	
    	if (loaiKH == null)
    		loaiKH = "";    		
    	obj.setloaiKH(loaiKH);
    	
    	String hopdong = util.antiSQLInspection(request.getParameter("hopdong")); 	
    	if (hopdong == null)
    		hopdong = "";    		
    	obj.setHopdong(hopdong);
    	
   /*	String query =
    			
    				"	select  ROW_NUMBER() OVER(ORDER BY a.pk_seq DESC) AS 'stt', isnull(a.mafast,'') as mafast , a.pk_seq as khId, a.smartid, a.ten as khTen, isnull(a.MST_CaNhan,'') as MST_CaNhan,a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua, d.chietkhau, d.pk_seq as mckId,  "+ 
    					"		e.diengiai as kbhTen, e.pk_seq as kbhId, f.hang as hchTen, f.pk_seq as hchId, g.loai as lchTen, g.pk_seq as lchId, h.ten as nppTen, h.pk_seq as nppId,   "+
    					"		k.sotienno as ghcnTen, k.pk_seq as ghcnId, l.ten as bgstTen, l.pk_seq as bgstId, m.vitri as vtchTen,  "+
    					"		m.pk_seq as vtchId, a.dienthoai, a.diachi,  isnull(a.MASOTHUE,'') as masothue, ISNULL(a.cmnd,'') as cmnd ,ISNULL(a.ngaysinh,'') as ngaysinh, ISNULL(a.MaHD,'') as maHD, n.ten as loaKH , "+
    					"	STUFF      "+
    					"	(    "+
    					"		(   "+
    					"			select DISTINCT TOP 100 PERCENT ' , ' + tbh.DIENGIAI   "+
    					"			from KHACHHANG_TUYENBH khtbh inner join TUYENBANHANG tbh on tbh.PK_SEQ=khtbh.TBH_FK   "+ 
    					"			where khtbh.KHACHHANG_FK=a.PK_SEQ and tbh.NPP_FK=a.NPP_FK  "+ 
    					"			ORDER BY ' , ' + tbh.DIENGIAI      "+
    					"			FOR XML PATH('')      "+
    					"		 ), 1, 6, ''   "+
    					"	) + ' '  AS tbhTen,a.CHUCUAHIEU,a.NgaykyHd,(select hch.diengiai from HANGCUAHANG hch where hch.pk_seq=a.hch_fk  ) as hangkhachhang   "+ 
    					"  from    "+
    					"  khachhang a inner join nhanvien b on a.nguoitao = b.pk_seq   "+ 
    					"  inner join nhanvien c on a.nguoisua = c.pk_seq    "+
    					"  left join mucchietkhau d on a.chietkhau_fk = d.pk_seq   "+
    					"  left join kenhbanhang e on a.kbh_fk = e.pk_seq    "+
    					"  left join hangcuahang f on a.hch_fk = f.pk_seq    "+
    					"  left join loaicuahang g on a.lch_fk = g.pk_seq    "+
    					"  inner join nhaphanphoi h on a.npp_fk = h.pk_seq    "+
    					"  left join gioihancongno k on a.ghcn_fk = k.pk_seq    "+
    					"  left join banggiasieuthi l on a.bgst_fk = l.pk_seq    "+
    					"  left join vitricuahang m on a.vtch_fk = m.pk_seq    "+
    					" left join LOAIKHACHHANG n on a.XuatKhau= n.pk_seq  "+
    					" where a.npp_fk='"+nppId+"' " ;
    					*/
    			
     	String	query="select  ROW_NUMBER() OVER(ORDER BY a.pk_seq DESC) AS 'stt',a.MASOTHUE,a.cmnd,case when dbo.parseNullDay(a.NgaySinh,'')='' then '' else day(a.NgaySinh) end as ngaysinh, \n"+
  			  "		case when dbo.parseNullDay(a.NgaySinh,'')='' then '' else month(a.NgaySinh) end as thangsinh, \n"+
  			  "		case when dbo.parseNullDay(a.NgaySinh,'')='' then '' else year(a.NgaySinh) end as namsinh, npp.TEN as tennpp,N'Tháng '+cast(month(a.NgayKyHD)as nvarchar(2)) thangkyhd,a.MaHD,isnull(a.mafast,'') as mafast , \n"+
  			  "		a.pk_seq as khId, a.smartid, a.ten as khTen, isnull(a.MST_CaNhan,'') as MST_CaNhan,a.trangthai, \n"+
  			  "		a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua, d.chietkhau, d.pk_seq as mckId,   \n"+		
  			  "		e.diengiai as kbhTen, e.pk_seq as kbhId, f.hang as hchTen, f.pk_seq as hchId, \n"+
  			  "		g.loai as lchTen, g.pk_seq as lchId, h.ten as nppTen, h.pk_seq as nppId,   		\n"+
  			  "		k.sotienno as ghcnTen, k.pk_seq as ghcnId, l.ten as bgstTen, l.pk_seq as bgstId, m.vitri as vtchTen,  		 \n"+
  			  "		m.pk_seq as vtchId, a.dienthoai, a.diachi,  isnull(a.MASOTHUE,'') as masothue, ISNULL(a.cmnd,'') as cmnd  \n"+
  			  "		, ISNULL(a.MaHD,'') as maHD, n.ten as loaKH , 	\n"+
  			  "		STUFF      	(    		(   			\n"+
  			  "		select DISTINCT TOP 100 PERCENT ' , ' +'VT'+ cast(tbh.NGAYID as nvarchar(10))+''+tbh.DIENGIAI   \n"+			
  			  "		from KHACHHANG_TUYENBH khtbh inner join TUYENBANHANG tbh on tbh.PK_SEQ=khtbh.TBH_FK   		 \n"+	
  			  "		where khtbh.KHACHHANG_FK=a.PK_SEQ and tbh.NPP_FK=a.NPP_FK  			\n"+
  			  "	 	ORDER BY ' , ' +'VT'+ cast(tbh.NGAYID as nvarchar(10))+''+tbh.DIENGIAI     			FOR XML PATH('')      		 ), 1, 6, ''   	) + ' '  AS tbhTen, \n"+
  			  "		a.CHUCUAHIEU,a.NgaykyHd,(select hch.diengiai from HANGCUAHANG hch where hch.pk_seq=a.hch_fk  ) as hangkhachhang  ,ISNULL(loaihopdong,'') as loaihopdong    \n"+
  			  "		from      khachhang a  \n"+
  			  "		inner join nhanvien b on a.nguoitao = b.pk_seq      \n"+
  			  "		inner join nhanvien c on a.nguoisua = c.pk_seq       \n"+
  			  "		left join mucchietkhau d on a.chietkhau_fk = d.pk_seq     \n"+
  			  "		left join kenhbanhang e on a.kbh_fk = e.pk_seq      \n"+
  			  "		left join hangcuahang f on a.hch_fk = f.pk_seq      \n"+
  			  "		left join loaicuahang g on a.lch_fk = g.pk_seq      \n"+
  			  "		inner join nhaphanphoi h on a.npp_fk = h.pk_seq      \n"+
  			  "		left join gioihancongno k on a.ghcn_fk = k.pk_seq     \n"+
  			  "		left join banggiasieuthi l on a.bgst_fk = l.pk_seq     \n"+
  			  "		left join vitricuahang m on a.vtch_fk = m.pk_seq     \n"+
  			  "		left join LOAIKHACHHANG n on a.XuatKhau= n.pk_seq  \n"+
  			  "		left join NHAPHANPHOI npp on npp.PK_SEQ=a.NPP_FK \n"+
  			  "		where a.npp_fk='"+nppId+"' " ;
      			
    	
    	
    	if (ten.length()>0)
    	{ 
    		
			//query = query + " and ( upper(a.pk_seq) like upper(N'%" + util.replaceAEIOU(ten) + "%') or upper(a.ten) like upper(N'%" + util.replaceAEIOU(ten) + "%')) ";	
			query = query + " and ( upper(dbo.ftBoDau(a.ten)) like upper(N'%" + util.replaceAEIOU(ten) + "%') or a.smartid like upper(N'%" + ten.trim()+ "%')) ";			
    	}
    	
    	if (kbhId.length()>0){
			query = query + " and a.kbh_fk ='" + kbhId + "'";	
    	}
    	
    	if (hchId.length()>0){
			query = query + " and a.hch_fk='" + hchId + "'";			
    	}
    	
    	if (vtchId.length()>0){
			query = query + " and a.vtch_fk='" + vtchId + "'";			
    	}
    	
    	if (lchId.length()>0){
			query = query + " and a.lch_fk='" + lchId + "'";			
    	}
    	
    	
    	if(diadiemId.length()>0)
    	{
    		query+=" and a.diadiem_fk="+diadiemId+" ";
    	}
    	
    	if(diachi.length()>0)
    	{
    		query+=" and (upper(dbo.ftBoDau(a.diachi)) like (N'%" + util.replaceAEIOU(diachi) + "%') )  ";
    	}
    	
    	if(maFAST.length()>0)
    	{
    		query+= " and a.maFAST like N'%"+maFAST+"%' ";
    	}
    	
    	if(ddkdId.length()>0)
    	{
    		query+= " and a.pk_Seq in (select khachhang_fk from KHACHHANG_TUYENBH where tbh_fk in (select PK_SEQ from tuyenbanhang where ddkd_Fk='"+ddkdId+"')) ";
    	}
    	
    	if(tbhId.length()>0)
    	{
    		query+= " and a.pk_Seq in (select KHACHHANG_FK from KHACHHANG_TUYENBH where tbh_fk ='"+tbhId+"' ) ";
    	}
    	
    	if (trangthai.length() > 0)
    	{
    		query = query + " and a.trangthai='" + trangthai + "'";
    	}
    	
    	if (tungay.length() > 0)
    	{
    		query = query + " and a.NGAYTAO>='" + tungay + "'";
    	}
    	
    	if (denngay.length() > 0)
    	{
    		query = query + " and a.NGAYTAO<='" + denngay + "'";
    	}
    	
    	if (loaiKH.length() > 0)
    	{
    		query = query + " and n.pk_seq='" + loaiKH + "'";
    	}
    	
    	if (hopdong.length() > 0)
    	{
    		query = query + " and a.KhongKyHopDong='" + hopdong + "'";
    	}
    	
    	System.out.println("Query excel: " + query + "\n");

    	
    	return query;
		
	}
	
	private void CreateStaticHeader(Workbook workbook, String UserName) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	    Style style;
	   	    
	    Cell cell = cells.getCell("A1"); 
	    cells.merge(0,0,0,11);
	    cell.setValue("DANH SÁCH KHÁCH HÀNG");	
	    style = cell.getStyle();
		Font font2 = new Font();	
		font2.setName("Calibri");
		font2.setColor(Color.NAVY);
		font2.setSize(18);
		font2.setBold(true);
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.CENTER);					
		cell.setStyle(style);
		
		font2 = new Font();	
		font2.setName("Calibri");
		font2.setBold(true);
		font2.setSize(11);
	   
	    cell = cells.getCell("A3");
	    cell.setValue("Ngày tạo : " + this.getDateTime());
	    style = cell.getStyle();
	    style.setFont(font2);
		cell.setStyle(style);
	    
	    //tieu de
			cell = cells.getCell("A5");cell.setValue("STT");  		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);    
	    cell = cells.getCell("B5");cell.setValue("Chi nhánh / đối tác");  		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);    
	    cell = cells.getCell("C5");cell.setValue("Nhân viên bán hàng");  				style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	    cell = cells.getCell("D5");cell.setValue("Tháng ký hợp đồng"); 					style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	    cell = cells.getCell("E5");cell.setValue(" Mã hợp đồng");  					style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	    cell = cells.getCell("F5");cell.setValue(" Mã Fast "); 					style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	    cell = cells.getCell("G5");cell.setValue(" Phân loại KH ");  				style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	    cell = cells.getCell("H5");cell.setValue(" Loại Hợp đồng ");  				style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	    cell = cells.getCell("I5");cell.setValue(" Hạng khách hàng "); 					style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	    cell = cells.getCell("J5");cell.setValue(" Tên đơn vị"); 					style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	    cell = cells.getCell("K5");cell.setValue("  Họ và Tên khách hàng  "); 					style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	    cell = cells.getCell("L5");cell.setValue(" CMT"); 					style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	    cell = cells.getCell("M5");cell.setValue("  Mã số thuế"); 					style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	    cell = cells.getCell("N5");cell.setValue("  Số điện thoại "); 					style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	    cell = cells.getCell("O5");cell.setValue("  Ngày sinh "); 					style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	    cell = cells.getCell("P5");cell.setValue("  Tháng sinh "); 					style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	    cell = cells.getCell("Q5");cell.setValue("  Năm sinh "); 					style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	    worksheet.setName(" DSKH ");
	}

		private void CreateStaticData(Workbook workbook, String query) 
		{
			
			
			Worksheets worksheets = workbook.getWorksheets();
		    Worksheet worksheet = worksheets.getSheet(0);
		    Cells cells = worksheet.getCells();
		    
			dbutils db = new dbutils();
			ResultSet rs = db.get(query);
			//System.out.println("Get san pham :"+query);
			NumberFormat formatter = new DecimalFormat("#,###,###");
			int i = 6;
			if(rs != null)
			{
				try 
				{
					cells.setColumnWidth(0, 12.0f);
					cells.setColumnWidth(1, 30.0f);
					cells.setColumnWidth(2, 16.0f);
					cells.setColumnWidth(3, 16.0f);
					cells.setColumnWidth(4, 28.0f);
					cells.setColumnWidth(5, 40.0f);
					cells.setColumnWidth(6, 50.0f);
					cells.setColumnWidth(7, 15.0f);
					cells.setColumnWidth(8, 15.0f);
					cells.setColumnWidth(9, 15.0f);
					cells.setColumnWidth(10, 15.0f);
					
					for(int j = 0; j < 11; j++)
					{
						if(j==0)
							cells.setRowHeight(j, 23.0f);
						else
							cells.setRowHeight(j, 13.0f);
					}
					
					Cell cell = null;
					
					Style style;
					Font font2 = new Font();
					font2.setName("Calibri");				
					font2.setSize(11);
					int j=1;
					while(rs.next())
					{

						String ddkd = rs.getString("tbhTen");
						String MASOTHUE = rs.getString("MASOTHUE");
						String loaihopdong = rs.getString("loaihopdong");
						String thangkhd = rs.getString("thangkyhd");
						String tennpp = rs.getString("tennpp");
						String mafast = rs.getString("mafast");
						String loaiKH = rs.getString("loaKH");
						String chuCH = rs.getString("CHUCUAHIEU");
						String ten = rs.getString("khTen");
						String diachi = rs.getString("diachi");
						String masothue = rs.getString("masothue");
						String CMND = rs.getString("cmnd");
						String sodt = rs.getString("dienthoai");
						
						String maHD = rs.getString("maHD");
						String mast_tncn=rs.getString("MST_CaNhan");
						String ngaysinh=rs.getString("ngaysinh");
						String thangsinh=rs.getString("thangsinh");
						String namsinh=rs.getString("namsinh");
						String ngaykyHd = rs.getString("ngaykyHd")==null?"":rs.getString("ngaykyHd");
						String hangcuahang = rs.getString("hangkhachhang")==null?"":rs.getString("hangkhachhang");
						cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(j); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(tennpp); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(ddkd); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(thangkhd); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(maHD); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(mafast); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(loaiKH); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(loaihopdong); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(hangcuahang); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(ten); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("K" + Integer.toString(i));	cell.setValue(chuCH); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("L" + Integer.toString(i));	cell.setValue(CMND); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("M" + Integer.toString(i));	cell.setValue(MASOTHUE); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("N" + Integer.toString(i));	cell.setValue(sodt); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("O" + Integer.toString(i));	cell.setValue(ngaysinh); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("P" + Integer.toString(i));	cell.setValue(thangsinh); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("Q" + Integer.toString(i));	cell.setValue(namsinh); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
						i++;
						j++;
					
					}
					rs.close();
				}
				catch (Exception e){ e.printStackTrace(); }
			}
		}
				
		private void setCellBorderStyle(Cell cell, short align) {
			Style style = cell.getStyle();
			style.setHAlignment(align);
			style.setBorderLine(BorderType.TOP, 1);
			style.setBorderLine(BorderType.RIGHT, 1);
			style.setBorderLine(BorderType.BOTTOM, 1);
			style.setBorderLine(BorderType.LEFT, 1);
			cell.setStyle(style);
		}
		
		private String getDateTime()
		{
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			return dateFormat.format(date);
		}
		
		
		
		
	private void CreateStaticHeader_HD(Workbook workbook, String UserName) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	    Style style;
	   	    
	    Cell cell = cells.getCell("A1"); 
	    cell.setValue("DANH SÁCH HÓA ĐƠN");	
	    style = cell.getStyle();
		Font font2 = new Font();	
		font2.setName("Calibri");
		font2.setColor(Color.NAVY);
		font2.setSize(18);
		font2.setBold(true);
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.LEFT);					
		cell.setStyle(style);
		
		font2 = new Font();	
		font2.setName("Calibri");
		font2.setBold(true);
		font2.setSize(11);
	   
	    cell = cells.getCell("A3");
	    cell.setValue("Ngày tạo : " + this.getDateTime());
	    style = cell.getStyle();
	    style.setFont(font2);
		cell.setStyle(style);
	    
	    //tieu de
	    cell = cells.getCell("A5");cell.setValue("ID hóa đơn");  		style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);    
	    cell = cells.getCell("B5");cell.setValue("Số hóa đơn");  				style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	    cell = cells.getCell("C5");cell.setValue("Ký hiệu hóa đơn");  					style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	    cell = cells.getCell("D5");cell.setValue("Ngày hóa đơn"); 					style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	    
	    cell = cells.getCell("E5");cell.setValue("Mã hàng");  				style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	    cell = cells.getCell("F5");cell.setValue("Tên hàng hóa, dịch vụ");  				style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	    cell = cells.getCell("G5");cell.setValue("Số lô"); 					style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	    cell = cells.getCell("H5");cell.setValue("Hạn dùng"); 					style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	    cell = cells.getCell("I5");cell.setValue("ĐVT"); 					style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	    cell = cells.getCell("J5");cell.setValue("Số lượng"); 					style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	    cell = cells.getCell("K5");cell.setValue("Đơn giá"); 					style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	    cell = cells.getCell("L5");cell.setValue("Thành tiền"); 					style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	    cell = cells.getCell("M5");cell.setValue("TS %"); 					style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	    cell = cells.getCell("N5");cell.setValue("Thuế GTGT"); 					style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	    cell = cells.getCell("O5");cell.setValue("Số tiền TT"); 					style.setFont(font2); cell.setStyle(style);	setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
	    
	    worksheet.setName(" HOA DON ");
	}
	
	private void CreateStaticData_HD(Workbook workbook) 
	{
		
		System.out.println("Vào đây !");
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    
		dbutils db = new dbutils();
		
		String query = "";
		
		ResultSet rs = db.get(query);
		int i = 6;
		if(rs != null)
		{
			try 
			{
				cells.setColumnWidth(0, 40.0f);
				cells.setColumnWidth(1, 30.0f);
				cells.setColumnWidth(2, 16.0f);
				cells.setColumnWidth(3, 16.0f);
				cells.setColumnWidth(4, 28.0f);
				cells.setColumnWidth(5, 40.0f);
				cells.setColumnWidth(6, 50.0f);
				cells.setColumnWidth(7, 15.0f);
				cells.setColumnWidth(8, 15.0f);
				cells.setColumnWidth(9, 15.0f);
				cells.setColumnWidth(10, 15.0f);
				
				for(int j = 0; j < 11; j++)
				{
					if(j==0)
						cells.setRowHeight(j, 23.0f);
					else
						cells.setRowHeight(j, 13.0f);
				}
				
				Cell cell = null;
				
				Style style;
				Font font2 = new Font();
				font2.setName("Calibri");				
				font2.setSize(11);
				
				while(rs.next())
				{
					
					String ddkd = rs.getString("tbhTen");
					String maKH = rs.getString("mafast");
					String loaiKH = rs.getString("loaKH");
					String chuCH = rs.getString("CHUCUAHIEU");
					String ten = rs.getString("khTen");
					String diachi = rs.getString("diachi");
					String masothue = rs.getString("masothue");
					String CMND = "";
					String sodt = rs.getString("masothue");
					String ngaysinh = "";
					String maHD = rs.getString("maHD");
					
					cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(ddkd); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(maKH); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(loaiKH); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(chuCH); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(ten); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(diachi); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(masothue); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(CMND); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(sodt); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("J" + Integer.toString(i));	cell.setValue(ngaysinh); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("K" + Integer.toString(i));	cell.setValue(maHD); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					i++;
				}
				rs.close();
			}
			catch (Exception e){ e.printStackTrace(); }
		}
	}
	
		
}
