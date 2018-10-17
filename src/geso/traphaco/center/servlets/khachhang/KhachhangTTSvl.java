package geso.traphaco.center.servlets.khachhang;

import geso.traphaco.center.beans.khachhang.IKhachhangList;
import geso.traphaco.center.beans.khachhang.imp.KhachhangList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.DateFormat;
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
import com.aspose.cells.Font;
import com.aspose.cells.HorizontalAlignmentType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class KhachhangTTSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private int items = 50;
	private int splittings = 20;
	
	
    public KhachhangTTSvl() 
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

	    obj = new KhachhangList();
	    settingPage(obj);
	    	
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Center/KhachHang.jsp";
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
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
		
		OutputStream out = response.getOutputStream();	
			
		IKhachhangList obj = new KhachhangList();
	
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
		obj.setUserId(userId);
		
	    
	    Utility util = new Utility();
	    userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = request.getParameter("action");
	    System.out.println("action "+action==null?"null":action);
	    if (action == null){
	    	action = "";
	    	
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
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Center/KhachHang.jsp");	    		    	
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
	    		
    		//response.sendRedirect("/TraphacoHYERP/pages/Center/KhachHang.jsp");
    		return;
	    }
	    else  if(action.equals("view") || action.equals("next") || action.equals("prev")){
	    	
	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
	    	obj.setUserId(userId);

	    	obj.init(search);
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	session.setAttribute("obj", obj);
	    	response.sendRedirect("/TraphacoHYERP/pages/Center/KhachHang.jsp");
	    }
	    else  if(action.equals("new")){
	    	System.out.println("action is new");
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
    				"	select  f.pk_seq as hangkh,ROW_NUMBER() OVER(ORDER BY a.pk_seq DESC) AS 'stt', isnull(a.mafast,'') as mafast , a.pk_seq as khId, a.smartid, a.ten as khTen, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua, d.chietkhau, d.pk_seq as mckId,  \n"+ 
					"		e.diengiai as kbhTen, e.pk_seq as kbhId, f.hang as hchTen, f.pk_seq as hchId, g.loai as lchTen, g.pk_seq as lchId, h.ten as nppTen, h.pk_seq as nppId,   \n"+
					"		k.sotienno as ghcnTen, k.pk_seq as ghcnId, l.ten as bgstTen, l.pk_seq as bgstId, m.vitri as vtchTen,  \n"+
					"		m.pk_seq as vtchId, a.dienthoai, a.diachi,  \n"+
					"	STUFF      \n"+
					"	(    \n"+
					"		(   \n"+
					"			select DISTINCT TOP 100 PERCENT ' , ' + tbh.DIENGIAI   \n"+
					"			from KHACHHANG_TUYENBH khtbh inner join TUYENBANHANG tbh on tbh.PK_SEQ=khtbh.TBH_FK   \n"+ 
					"			where khtbh.KHACHHANG_FK=a.PK_SEQ and tbh.NPP_FK=a.NPP_FK  \n"+ 
					"			ORDER BY ' , ' + tbh.DIENGIAI      \n"+
					"			FOR XML PATH('')      \n"+
					"		 ), 1, 2, ''   \n"+
					"	) + ' '  AS tbhTen,a.CHUCUAHIEU  ,a.MaHD,n.ten as LoaiCH  \n"+ 
					"  from    \n"+
					"  khachhang a left join nhanvien b on a.nguoitao = b.pk_seq   \n"+ 
					"  left join nhanvien c on a.nguoisua = c.pk_seq    \n"+
					"  left join mucchietkhau d on a.chietkhau_fk = d.pk_seq   \n"+
					"  left join kenhbanhang e on a.kbh_fk = e.pk_seq    \n"+
					"  left join hangcuahang f on a.hch_fk = f.pk_seq    \n"+
					"  left join loaicuahang g on a.lch_fk = g.pk_seq    \n"+
					"  inner join nhaphanphoi h on a.npp_fk = h.pk_seq    \n"+
					"  left join gioihancongno k on a.ghcn_fk = k.pk_seq    \n"+
					"  left join banggiasieuthi l on a.bgst_fk = l.pk_seq    \n"+
					"  left join vitricuahang m on a.vtch_fk = m.pk_seq    \n"+
					" left join LOAIKHACHHANG n on a.XuatKhau= n.pk_seq  \n"+
					" where 1 = 1";
     
    			
    	if (ten.length()>0)
    	{ 
			//query = query + " and ( upper(a.pk_seq) like upper(N'%" + util.replaceAEIOU(ten) + "%') or upper(a.ten) like upper(N'%" + util.replaceAEIOU(ten) + "%')) ";	
			query = query + " and ( upper(dbo.ftBoDau(a.ten)) like upper(N'%" + util.replaceAEIOU(ten) + "%') or a.smartid like upper(N'%" + ten.trim()+ "%') or a.maFAST like '%"+ten.trim()+"%' ) \n";			
    	}
    	
    	if (kbhId.length()>0){
			query = query + " and a.kbh_fk ='" + kbhId + "' \n";	
    	}
    	
    	if (hchId.length()>0){
			query = query + " and a.hch_fk='" + hchId + "' \n";			
    	}
    	
    	if (vtchId.length()>0){
			query = query + " and a.vtch_fk='" + vtchId + "' \n";			
    	}
    	
    	if (lchId.length()>0){
			query = query + " and a.lch_fk='" + lchId + "' \n";			
    	}
    	
    	
    	if(diadiemId.length()>0)
    	{
    		query+=" and a.diadiem_fk="+diadiemId+" \n";
    	}
    	
    	if(diachi.length()>0)
    	{
    		query+=" and (upper(dbo.ftBoDau(a.diachi)) like (N'%" + util.replaceAEIOU(diachi) + "%') )  \n";
    	}
    	
    	if(maFAST.length()>0)
    	{
    		query+= " and upper(dbo.ftBoDau(a.MAFAST)) like  upper((N'%" + util.replaceAEIOU(maFAST) + "%')) \n";
    	}
    	
    	if(ddkdId.length()>0)
    	{
    		query+= " and a.pk_Seq in (select khachhang_fk from KHACHHANG_TUYENBH where tbh_fk in (select PK_SEQ from tuyenbanhang where ddkd_Fk='"+ddkdId+"')) \n";
    	}
    	
    	if(tbhId.length()>0)
    	{
    		query+= " and a.pk_Seq in (select KHACHHANG_FK from KHACHHANG_TUYENBH where tbh_fk ='"+tbhId+"' ) \n";
    	}
    	
    	if (trangthai.length() > 0)
    	{
    		query = query + " and a.trangthai='" + trangthai + "' \n";
    	}
    	
    	if (tungay.length() > 0)
    	{
    		query = query + " and a.NGAYTAO>='" + tungay + "' \n";
    	}
    	
    	if (denngay.length() > 0)
    	{
    		query = query + " and a.NGAYTAO<='" + denngay + "' \n";
    	}
    	
    	if (loaiKH.length() > 0)
    	{
    		query = query + " and n.pk_seq='" + loaiKH + "' \n";
    	}
    
    	if (hopdong.length() > 0)
    	{
    		query = query + " and a.KhongKyHopDong='" + hopdong + "' \n";
    	}

    	if (hkhid.length() > 0)
    	{
    		query = query + " and f.pk_seq='" + hkhid + "' \n";
    	}
    	
    	System.out.println("Query tìm kiếm: " + query + "\n");

    	return query;
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
    	
    	String loaiKH = util.antiSQLInspection(request.getParameter("loaikh")); 	
    	if (loaiKH == null)
    		loaiKH = "";    		
    	obj.setloaiKH(loaiKH);
    	
    	String hopdong = util.antiSQLInspection(request.getParameter("hopdong")); 	
    	if (hopdong == null)
    		hopdong = "";    		
    	obj.setHopdong(hopdong);
    	
    	String query = 
    			
       			
    			" select distinct '' as STT,   isnull(a.mafast,'') as maCSMkh ,isnull(a.ma,'') as Mamoikh, isnull(a.ten,'') as TenKH, case when a.trangthai = 1 then N'Hoạt động' else N'Ngưng hoạt động' end as trangthai, isnull(a.ngaytao,'') as ngaytao, isnull(a.ngaysua,'') as ngaysua, isnull(b.ten,'') as nguoitao, isnull(c.ten,'') as nguoisua, "
    	   				 +"\n isnull([dbo].[GetKenhBanHang_KhachHang](a.pk_seq),'') as kenhbanhang, isnull(f.HANG,'') as HangCuaHang, isnull(g.loai,'') as LoaiKhachHang,      "
    	   				 +"\n isnull(a.dienthoai,'') as dienthoai, isnull(a.diachi,'') as diachi,  isnull(a.MASOTHUE,'') as masothue, isnull(a.cmnd,'') as cmnd , ISNULL(h.ten,'') as maHD ,    "
    	   				 +"\n isnull(a.CHUCUAHIEU,'') as chucuahieu,       "
    	   				 +"\n isnull(o.TEN,'') as tinhthanh, isnull(p.TEN,'') as quanhuyen, isnull(q.Ten,'') as phuongxa, isnull(a.chanhxe,'') as chanhxe, isnull(a.phuongthucgiaohang,'') as phuongthucgiaohang , isnull(a.TENXUATHD,'') TENXUATHD,  "
    	   				 +"\n isnull(a.CAPDOGIAOHANG,'') as CAPDOGIAOHANG , isnull(a.GhiChu,'') as ghichu, isnull(a.TENNGUOITT,'') as TENNGUOITT , isnull(a.DIENTHOAINGUOITT,'') as DIENTHOAINGUOITT,   "
    	   				 +"\n isnull(r.TEN ,'')as nganhang, isnull(s.TEN,'') as chinhanh,isnull([dbo].[GetKHACHHANG_MA_DDKD](a.pk_seq),'') as MaTDV,isnull([dbo].[GetKHACHHANG_DDKD](a.pk_seq),'') as TenTDV, isnull(a.Ngaysinh,'') as ngaysinh,isnull(a.nguoidaidien,'') as nguoidaidien,db.ten,h2.ten as NppXuatHoaDon,a.TenKyHd,h3.ten as NPPXK,  		      "
    	   				 +"\n [dbo].[GetCONGTACVIEN_KHACHHANG](a.PK_SEQ)  as ctv,isnull(a.phuongthucgiaohang,'') as phuongthucgiaohang,isnull(a.THOIHANNO,'') as THOIHANNO,isnull(a.ghinhancongno,'') as ghinhancongno,isnull(a.HANMUCNO,'') as HANMUCNO,		      "
    	   				 +"\n [dbo].[GetKHACHHANG_TUYENBH](a.PK_SEQ) AS tbhTen,isnull(qg.TENQG,'') as quocgia,case when a.insoVISA = '1' then N'Có' else N'Không' end as insovisa,"
    	   				 +"\n case when a.insoHOPDONG = '1' then N'Có' else N'Không' end as insoHOPDONG,case when a.innhaNK = '1' then N'Có' else N'Không' end as innhank,"
    	   				 +"\n case when a.innhaSX = '1' then N'Có' else N'Không' end as innhaSX, case when a.inbhDT = '1' then N'Có' else N'Không' end as inbhDT,"
    	   				 +"\n isnull(a.sotaikhoan,'') as sotaikhoan,isnull(t.SOHIEUTAIKHOAN,'') as SOHIEUTAIKHOAN , ISNULL(a.nguoidaidien,'') as nguoidaidien,"
    	   				 +"\n isnull(a.chucvu,'') as chucvu,isnull(h4.TEN,'') as tructhuoc,ISNULL(a.MST_CaNhan,'') mstcn, case when a.ISDACBIET = '1' then N'Có' else N'Không' end as khdacbiet,"
    	   				 +"\n case when a.ISXQBENHVIEN = '1' then N'Có' else N'Không' end as xqbenhvien,ISNULL(bv.ten,'') as benhvien,"
    	   				 +"\n isnull(t.TENTAIKHOAN,'') as tkkq,isnull(kh.TEN,'') as khodh, case when a.KhongKyHopDong = '1' then N'Có' else N'Không' end as khongkyHD,"
    	   				 +"\n isnull(a.NGUOI_LIENHE_DH,'') as nguoilienhe,"
    	   				 +"\n [dbo].[GetKHACHHANG_THONGTINHOADON](a.PK_SEQ) AS nguoimuahang,isnull(a.NgayKyHD,'') as ngaykyhd,isnull(httt.TEN,'') as httt,ISNULL(a.songayvanchuyen,'') as songayvc,"
    	   				 +"\n ISNULL(a.PT_CHIETKHAU,'') as ptchietkhau,	[dbo].[GetKHACHHANG_NHANVIENGIAONHAN](a.PK_SEQ)  AS nvgn, "
    	   				 +"\n isnull(a.diachi_chanhxe,'') as diachichanhxe,isnull(a.sdt_chanhxe,'') as sdt_chanhxe,"
    	   				 +"\n isnull(a.CKTHANHTOAN,0) as ckthanhtoan,[dbo].[GetNHOMKHACHHANG_KHACHHANG](a.PK_SEQ)  AS nhomkh"
    	   				 +"\n from       "
    	   				 +"\n khachhang a inner join nhanvien b on a.nguoitao = b.pk_seq       "
    	   				 +"\n inner join nhanvien c on a.nguoisua = c.pk_seq       "
    	   				 +"\n left join hangcuahang f on a.hch_fk = f.pk_seq       "
    	   				 +"\n left join loaicuahang g on a.lch_fk = g.pk_seq       "
    	   				 +"\n LEFT join nhaphanphoi h on a.npp_fk = h.pk_seq  "
    	   				 +"\n left join KHACHHANG bv on a.benhvien_fk = bv.pk_seq     "
    	   				 +"\n left join TINHTHANH o on a.TINHTHANH_FK= o.pk_seq"
    	   				 +"\n left join QUOCGIA qg on qg.PK_SEQ = o.QUOCGIA_FK     "
    	   				 +"\n left join QUANHUYEN p on a.QUANHUYEN_FK= p.pk_seq     "
    	   				 +"\n left join PhuongXa q on a.PHUONGXA_FK= q.pk_seq     "
    	   				 +"\n left join ERP_NGANHANG r on a.NganHang_FK= r.pk_seq     "
    	   				 +"\n left join ERP_CHINHANH s on a.ChiNhanh_FK= s.pk_seq     "
    	   				 +"\n left join ERP_TAIKHOANKT t on a.TaiKhoan_FK= t.pk_seq     "
    	   				 +"\n left join nhaphanphoi h2 on a.NPPXHD_FK = h2.pk_seq    "
    	   				 +"\n left join nhaphanphoi h4 on a.TrucThuoc_FK = h4.pk_seq    "
    	   				 +"\n left join nhaphanphoi h3 on a.NPPXK_FK = h3.pk_seq    "
    	   				 +"\n left join diaban db on db.pk_seq = a.diaban  "
    	   				 +"\n left join KHO kh on kh.pk_seq = a.KHO_FK"
    	   				 +"\n left join HINHTHUCTHANHTOAN httt on httt.pk_seq = a.HINHTHUCTT_FK"
    	   				 +"\n where a.pk_seq > 0    ";
   	
    	if(nppId.trim().length() > 0)
    		query += " and a.npp_fk = '" + nppId + "' ";
   		
    	if (ten.length()>0)
    	{ 
    		
			//query = query + " and ( upper(a.pk_seq) like upper(N'%" + util.replaceAEIOU(ten) + "%') or upper(a.ten) like upper(N'%" + util.replaceAEIOU(ten) + "%')) ";	
			query = query + " and ( a.ten like upper(N'%" + ten + "%') or a.smartid like upper(N'%" + ten.trim()+ "%')) ";			
    	}
    	
    	if (kbhId.length()>0){
			query = query + " and exists (select top(1) KBH_FK from KHACHHANG_KENHBANHANG where khachhang_fk = a.PK_SEQ and kbh_fk = '" + kbhId + "') \n";	
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
    		query+=" and (a.diachi like (N'%" + diachi + "%') )  ";
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
    	
//    	query += " order by a.npp_fk asc, a.ten asc ";
    
    	
    	System.out.println("Query excel: " + query + "\n");

    	
    	return query;
		
	}
	
		private void CreateStaticHeader(Workbook workbook, String UserName) 
		{
			Worksheets worksheets = workbook.getWorksheets();
		    Worksheet worksheet = worksheets.getSheet(0);
		   	   
		    Cells cells = worksheet.getCells();
		    Style style;
		   	    
		    Cell cell = cells.getCell("B1"); 
		    cell.setValue("DANH SÁCH KHÁCH HÀNG");	
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
			 cell = cells.getCell("A5");cell.setValue("STT");    style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);    
		      cell = cells.getCell("B5");cell.setValue("Mã CSM KH");    style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);    
		      cell = cells.getCell("C5");cell.setValue("Mã mới KH");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("D5");cell.setValue("Tên KH");       style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("E5");cell.setValue("Trạng thái");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("F5");cell.setValue("Ngày tạo");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("G5");cell.setValue("Ngày sửa");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("H5");cell.setValue("Người tạo");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("I5");cell.setValue("Người sửa");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("J5");cell.setValue("Kênh bán hàng");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("K5");cell.setValue("Hạng cửa hàng");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("L5");cell.setValue("Loại KH");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("M5");cell.setValue("Điện thoại");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("N5");cell.setValue("Địa chỉ");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("O5");cell.setValue("Mã số thuế");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("P5");cell.setValue("CMND");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("Q5");cell.setValue("Nhà phân phối");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("R5");cell.setValue("Chủ cửa hiệu");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("S5");cell.setValue("Tỉnh thành");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("T5");cell.setValue("Quận huyện");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("U5");cell.setValue("Phường xã");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("V5");cell.setValue("Chành xe");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("W5");cell.setValue("Phương thức giao hàng");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("X5");cell.setValue("Tên xuất HĐ");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("Y5");cell.setValue("Cấp độ giao hàng");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("Z5");cell.setValue("Ghi chú");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("AA5");cell.setValue("Tên người TT");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("AB5");cell.setValue("Điện thoại người TT");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("AC5");cell.setValue("Ngân hàng");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("AD5");cell.setValue("Chi nhánh");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("AE5");cell.setValue("Mã TDV");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("AF5");cell.setValue("Tên TDV");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("AG5");cell.setValue("Ngày sinh");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("AH5");cell.setValue("Người đại diện");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("AI5");cell.setValue("Địa bàn");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("AJ5");cell.setValue("NPP Xuất hóa đơn");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("AK5");cell.setValue("Tên ký hợp đồng");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("AL5");cell.setValue("NPP Xuất kho");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("AM5");cell.setValue("Cộng tác viên");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("AN5");cell.setValue("PT giao hàng");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("AO5");cell.setValue("Thời hạn nợ");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("AP5");cell.setValue("Ghi nhận công nợ");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("AQ5");cell.setValue("Hạn mức nợ");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("AR5");cell.setValue("Nhân viên bán hàng");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		     
		      cell = cells.getCell("AS5");cell.setValue("Quốc gia");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("AT5");cell.setValue("In số visa");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("AU5");cell.setValue("In số hợp đồng");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("AV5");cell.setValue("In nhà nhập khẩu");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("AW5");cell.setValue("In nhà sản xuất");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("AW5");cell.setValue("In nhà sản xuất");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("AX5");cell.setValue("Bán hàng qua điện thoại");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("AY5");cell.setValue("Số tài khoản ngân hàng");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("AZ5");cell.setValue("Tài khoản kế toán");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("BA5");cell.setValue("Người đại diện");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("BB5");cell.setValue("Chức vụ");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("BC5");cell.setValue("Trực thuộc");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("BD5");cell.setValue("Mã số thuế cá nhân");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("BE5");cell.setValue("Khách hàng đặc biệt");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("BF5");cell.setValue("Xung quanh bệnh viện");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("BG5");cell.setValue("Bệnh viện");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("BH5");cell.setValue("Tài khoản ký quỹ");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("BI5");cell.setValue("Kho đặt hàng");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("BJ5");cell.setValue("Hợp đồng");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("BK5");cell.setValue("Người liên hệ đặt hàng");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("BL5");cell.setValue("Người mua hàng");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("BM5");cell.setValue("Ngày ký hợp đồng");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("BN5");cell.setValue("Phương thức thanh toán");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("BO5");cell.setValue("Số ngày vận chuyển");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("BP5");cell.setValue("Phần trăm chiết khấu");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("BQ5");cell.setValue("Nhân viên giao nhận");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("BR5");cell.setValue("Địa chỉ chành xe");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("BS5");cell.setValue("SĐT chành xe");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("BT5");cell.setValue("Chiết khấu thanh toán");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("BU5");cell.setValue("Nhóm khách hàng");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      cell = cells.getCell("BV5");cell.setValue("Địa chỉ XHĐ");      style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
		      worksheet.setName(" DSKH ");
		}

		private void CreateStaticData(Workbook workbook, String query) 
		{
			
			System.out.println("Vào đây !");
			Worksheets worksheets = workbook.getWorksheets();
		    Worksheet worksheet = worksheets.getSheet(0);
		    Cells cells = worksheet.getCells();
		    
			dbutils db = new dbutils();
			ResultSet rs = db.get(query);
			//System.out.println("Get san pham :"+query);
//			NumberFormat formatter = new DecimalFormat("#,###,###");
			int i = 6;
			if(rs != null)
			{
				try 
				{
					cells.setColumnWidth(0, 5.0f);
					cells.setColumnWidth(1, 20.0f);
					cells.setColumnWidth(2, 26.0f);
					cells.setColumnWidth(3, 40.0f);
					cells.setColumnWidth(4, 28.0f);
					cells.setColumnWidth(5, 40.0f);
					cells.setColumnWidth(6, 50.0f);
					cells.setColumnWidth(7, 15.0f);
					cells.setColumnWidth(8, 15.0f);
					cells.setColumnWidth(9, 15.0f);
					cells.setColumnWidth(10, 15.0f);
					cells.setColumnWidth(11, 20.0f);
					cells.setColumnWidth(12, 20.0f);
					cells.setColumnWidth(13, 20.0f);
					cells.setColumnWidth(14, 20.0f);
					cells.setColumnWidth(15, 20.0f);
					cells.setColumnWidth(16, 20.0f);
					cells.setColumnWidth(17, 20.0f);
					cells.setColumnWidth(18, 20.0f);
					cells.setColumnWidth(19, 20.0f);
					cells.setColumnWidth(20, 20.0f);
					cells.setColumnWidth(21, 20.0f);
					cells.setColumnWidth(22, 20.0f);
					cells.setColumnWidth(23, 20.0f);
					cells.setColumnWidth(24, 20.0f);
					cells.setColumnWidth(25, 20.0f);
					cells.setColumnWidth(26, 20.0f);
					cells.setColumnWidth(27, 20.0f);
					cells.setColumnWidth(28, 40.0f);
					cells.setColumnWidth(29, 20.0f);
					cells.setColumnWidth(30, 20.0f);
					cells.setColumnWidth(31, 40.0f);
					
					for(int j = 0; j < 11; j++)
					{
						if(j==0)
							cells.setRowHeight(j, 23.0f);
						else
							cells.setRowHeight(j, 13.0f);
					}
					
					Cell cell = null;
					
//					Style style;
					Font font2 = new Font();
					font2.setName("Calibri");				
					font2.setSize(11);
					int stt = 0;
					ResultSetMetaData rsmd = rs.getMetaData();
					int socottrongSql = rsmd.getColumnCount();
					
					int countRow = 5;
					
					while(rs.next())
					{
						stt++;
						for( i =1;i <=socottrongSql ; i ++)
						{
							cell = cells.getCell(countRow,i-1 );
							if(rsmd.getColumnName(i).equals("STT"))
							{
								
								cell.setValue(stt);
								ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
								
							}
							else

								if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i) == Types.INTEGER || rsmd.getColumnType(i) == Types.DECIMAL)
								{
									cell.setValue(rs.getDouble(i));
									ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
								}
								else
								{
									cell.setValue(rs.getString(i));
										ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
								}
						}
						++countRow;
					}
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

	

}
