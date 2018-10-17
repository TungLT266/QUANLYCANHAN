package geso.traphaco.distributor.servlets.hopdong;

import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.hopdong.IErpDonhangNppETCList;
import geso.traphaco.distributor.beans.hopdong.imp.ErpDonhangNppETCList;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpSuakhoasoSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpSuakhoasoSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpDonhangNppETCList obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
    
	    HttpSession session = request.getSession();	    

	    String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
	    String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
	    
	    Utility util = new Utility();
   
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));

	    obj = new ErpDonhangNppETCList();
	    
	    obj.setTdv_dangnhap_id(tdv_dangnhap_id);
	    obj.setNpp_duocchon_id(npp_duocchon_id);
	    
	    obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));

	    String nppId = request.getParameter("nppId");
	    if(nppId == null)
	    	nppId = "";
	    obj.setNppId(nppId);
	    
	    obj.setUserId(userId);
	    obj.initDCDH("");
	    
		session.setAttribute("obj", obj);
			
		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpSuaKhoaSo.jsp";
		response.sendRedirect(nextJSP);
	       
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    String action = request.getParameter("action");
	    if (action == null)
	    	action = "";
	    
	    String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "0";
	    
		IErpDonhangNppETCList obj = new ErpDonhangNppETCList();
	 
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    obj.setUserId(userId);
	    
	    String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
	    String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
	    
	    obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
	    
    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
    	{
    		obj.setUserId(userId);
    		
    		obj.setNpp_duocchon_id(npp_duocchon_id);
    		obj.setTdv_dangnhap_id(tdv_dangnhap_id);
    		
	    	String search = getSearchQuery(request, obj);
	    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
	    	obj.initDCDH(search);
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	session.setAttribute("obj", obj);
	    	
	    	String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpSuaKhoaSo.jsp";
			response.sendRedirect(nextJSP);
	    }
    	else if( action.equals("luuthongtin") )
    	{
    		String search = getSearchQuery(request, obj);
	    	obj.setUserId(userId);
	    	
	    	String[] iddonhangNEW = request.getParameterValues("iddonhangNEW");
	    	String[] loaidonhangNEW = request.getParameterValues("loaidonhangNEW");
	    	String[] ngaydonhangNEW = request.getParameterValues("ngaydonhangNEW");
	    	String[] giodonhangNEW = request.getParameterValues("giodonhangNEW");
	    	
	    	String[] ngaydonhangOLD = request.getParameterValues("ngaydonhangOLD");
	    	String[] giodonhangOLD = request.getParameterValues("giodonhangOLD");
	    	
	    	String msg = "";
	    	
	    	msg = obj.ChangeDonHang( iddonhangNEW, loaidonhangNEW, ngaydonhangNEW, giodonhangNEW, ngaydonhangOLD, giodonhangOLD );
	    	
	    	obj.setNpp_duocchon_id(npp_duocchon_id);
    		obj.setTdv_dangnhap_id(tdv_dangnhap_id);
	    	
	    	obj.initDCDH(search);
	    	obj.setMsg(msg);

	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
    		
    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpSuaKhoaSo.jsp";
    		response.sendRedirect(nextJSP);
    	}
    	else
    	{
	    	String search = getSearchQuery(request, obj);
	    	obj.setUserId(userId);
	    	
	    	obj.setNpp_duocchon_id(npp_duocchon_id);
    		obj.setTdv_dangnhap_id(tdv_dangnhap_id);
	    	
	    	obj.initDCDH(search);				
			System.out.println("Tim kiem "+search);
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
    		
    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpSuaKhoaSo.jsp";
    		response.sendRedirect(nextJSP);
    	}
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpDonhangNppETCList obj)
	{
		Utility util = new Utility();
		
		String hientimkiem = request.getParameter("hientimkiem");
		if(hientimkiem == null)
			hientimkiem = "0";
		obj.setHientiemkiem(hientimkiem);
		
		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String mafast = request.getParameter("mafast");
		if(mafast == null)
			mafast = "";
		obj.setMafast(mafast);
		
		String maHopDong = request.getParameter("maHopDong");
		if(maHopDong == null)
			maHopDong = "";
		obj.setMaHopDong(maHopDong);
		
		String soHopDong = request.getParameter("soHopDong");
		if(soHopDong == null)
			soHopDong = "";
		obj.setSoHopDong(soHopDong);
		
		String nppId = request.getParameter("nppId");
		if(nppId == null)
			nppId = "";
		obj.setNppId(nppId);
		
		String khId = request.getParameter("khId");
		if(khId == null)
			khId = "";
		obj.setKhTen(khId);
		
		String sodh = request.getParameter("sodh");
		if(sodh == null)
			sodh = "";
		obj.setSodh(sodh);
		
		String ngayno=request.getParameter("ngayno");
		if(ngayno==null)
			ngayno="";
		obj.setNgayno(ngayno);
		
		String ngaygiao=request.getParameter("ngaygiao");
		if(ngaygiao==null)
			ngaygiao="";
		obj.setNgaygiao(ngaygiao);
		
		String tensp=request.getParameter("tensp");
		if(tensp==null)
			tensp="";
		obj.setTensp(tensp);
		
		String nvbanhang=request.getParameter("nvbanhang");
		if(nvbanhang==null)
			nvbanhang="";
		obj.setNvbanhang(nvbanhang);
		
		String khohh=request.getParameter("khohhid");
		if(khohh==null)
			khohh="";
		obj.setKhohangid(khohh);
		
		String nguoigiao=request.getParameter("nguoigiao");
		if(nguoigiao==null)
			nguoigiao="";
		obj.setNguoigiao(nguoigiao);
		
		String ghichu=request.getParameter("ghichu");
		if(ghichu==null)
			ghichu="";
		obj.setGhichu(ghichu);
		
		String congty=request.getParameter("congtyid");
		if(congty==null)
			congty="";
		obj.setCongty(congty);
		
		String nguoitao=request.getParameter("nguoitao");
		if(nguoitao==null)
			nguoitao="";
		obj.setNguoitao(nguoitao);
		
		String loaidonhang = request.getParameter("loaidonhang");
		if(loaidonhang == null)
			loaidonhang = "";
		obj.setLoaidonhang(loaidonhang);
		
		String query =  "select distinct a.PK_SEQ, isnull(a.machungtu, '') as machungtu, a.trangthai, isnull(a.hopdong_fk, -1) as hopdong_fk, a.Created_Date, a.ngaydenghi, a.tooltip, a.tooltip_scheme, " + 
						"	isnull( c.maFAST, d.maFAST ) nppMA, isnull( c.ten, d.ten ) nppTEN, " + 
						//" ddkd.ten as ddkdTEN, " + 
						" 	b.ten as khoTEN, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, isnull(a.NOTE, '') as NOTE , isnull(a.CAPDOGIAOHANG, 0) as CAPDOGIAOHANG, " + 
						"	a.CS_DUYET, a.SS_DUYET, a.thoigianxuly, datepart(hh, a.Created_Date ) as giodonhang, a.NgayDonHang " + 
						"from ERP_DonDatHangNPP a inner join KHO b on a.kho_fk = b.pk_seq " + 
						"	left join KHACHHANG c on a.khachhang_fk = c.pk_seq " +
						" 	left join NHAPHANPHOI d on a.npp_dat_fk = d.pk_seq " +
						" 	left join ERP_NHANVIEN e on a.nhanvien_fk = e.pk_seq " +
						"	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
						"	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ "+
						"	inner join ERP_DONDATHANGNPP_SANPHAM dh_sp on a.pk_seq = dh_sp.dondathang_fk "+
						"where a.pk_seq > 0 and a.npp_fk = '" + nppId + "'  and a.kho_fk in " + util.quyen_kho(obj.getUserId());
		
		if( loaidonhang.trim().length() > 0 )
			query += " and a.loaidonhang = '" + loaidonhang + "' ";
		if(tungay.length() > 0)
			query += " and a.ngaydonhang >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngaydonhang <= '" + denngay + "'";
	
		if(khId.length() > 0){
			query += " and a.khachhang_FK = '" + khId + "'";
		}
		
		if(mafast.length() > 0){
			//query += " and case a.loaidonhang when 0  then e.TEN  when 3 then d.TimKiem else c.timkiem end LIKE   '%' + UPPER(dbo.ftBoDau(N'%" + mafast + "%')) + '%'";
			query += " and isnull(d.TimKiem, c.timkiem ) LIKE   N'%" + util.replaceAEIOU( mafast.trim() ) + "%'";
		}
		
		if(sodh.length()>0){
			query += " and a.machungtu LIKE '%" + sodh + "%'";
		}
				
		if(soHopDong.length() > 0)
		{
			query += " and a.hopdong_Fk like  '" + soHopDong + "' ";
		}
		
		if(maHopDong.length() > 0)
		{
			query += " and f.MaHopDong like '%" + maHopDong + "%'";
		}
		
		if(trangthai.length() > 0)
		{
			if( trangthai.equals("0") )
				query += " AND a.trangthai in ( 0, 1 ) ";
			else if( trangthai.equals("1") )
				query += " AND a.SS_DUYET in ( 1 ) ";
			else if( trangthai.equals("2") )
				query += " AND a.CS_DUYET in ( 1 ) ";
			else if( trangthai.equals("3") )
				query += " AND ( ISNULL ( ( select count( hd.PK_SEQ ) from ERP_HOADONNPP hd inner join ERP_HOADONNPP_DDH hd_dh on hd.PK_SEQ = hd_dh.HOADONNPP_FK where hd.TRANGTHAI not in ( 3, 5 ) and hd_dh.DDH_FK = a.pk_seq ), 0 ) >= 1 ) ";
			else if( trangthai.equals("4") )
				query += " AND ( ISNULL ( ( select COUNT( PK_SEQ ) from ERP_YCXUATKHONPP where TRANGTHAI != 2 and DDH_FK = a.pk_seq ), 0 ) >= 1 ) ";
			else if( trangthai.equals("5") )
				query += " AND a.trangthai in ( 3 ) ";
			else if( trangthai.equals("6") )
				query += " AND a.trangthai in ( 0, 1 ) and a.mohoadon = '1' ";
		}
		
		if(nvbanhang.length()>0)
		{
			query+=" and dh_sp.DDKD_FK = " + nvbanhang;
		}
		
		if(tensp.length()>0)
		{
			query += " and dbo.ftBoDau( sp.ten ) like N'%" + util.replaceAEIOU( tensp ) + "%' ";
		}
		
		if(khohh.length()>0)
		{
			query +=" and a.Kho_FK = " + khohh;
		}
		
		if(nguoigiao.length()>0)
		{
			query +=" and dbo.ftBoDau( nvgnn.ten ) like N'%" + util.replaceAEIOU( nguoigiao ) + "%'";
		}
		if(ngaygiao.length()>0)
		{
			query +="  and a.ngaydenghi = '" + ngaygiao + "'";
		}
		
		if(ghichu.length()>0)
		{
			query += " and dbo.ftBoDau( a.ghichu ) like N'%" + util.replaceAEIOU( ghichu ) + "%'";
		}
		/*if(congty.length()>0)
		{
			query += " and a.npp_fk = "+congty;
		}*/
		if(nguoitao.length()>0)
		{
			query += " and dbo.ftBoDau( nv.ten ) like N'%" + util.replaceAEIOU( nguoitao ) + "%' ";
		}
		
		System.out.println("Câu query hợp đồng: " + query);
		return query;
	}
	
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
}
