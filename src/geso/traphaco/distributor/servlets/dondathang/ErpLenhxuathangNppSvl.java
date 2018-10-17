package geso.traphaco.distributor.servlets.dondathang;

import geso.traphaco.distributor.beans.dondathang.IErpDuyetddhNppList;
import geso.traphaco.distributor.beans.dondathang.imp.ErpDuyetddhNppList;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpLenhxuathangNppSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	public ErpLenhxuathangNppSvl() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpDuyetddhNppList obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();	 
		
	    String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
	    String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
		    
		Utility util = new Utility();
		
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		
		String phanloai = request.getParameter("loai");
		if(phanloai == null)
			phanloai = "";
		
		if (userId.length()==0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		
		obj = new ErpDuyetddhNppList();
		obj.setUserId(userId);
		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
	    
	    obj.setTdv_dangnhap_id(tdv_dangnhap_id);
	    obj.setNpp_duocchon_id(npp_duocchon_id);
	    
		//obj.setPhanloai(phanloai);
		//obj.init("");
		obj.initLENHXUATHANG("");
		
		session.setAttribute("obj", obj);
		
		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpLenhXuatHang.jsp";
		response.sendRedirect(nextJSP);
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		String action = request.getParameter("action");
		if (action == null)
		{
			action = "";
		}	    
		
		IErpDuyetddhNppList obj = new ErpDuyetddhNppList();
		
		Utility util = new Utility();
		
		HttpSession session = request.getSession();
		String userId = util.antiSQLInspection(request.getParameter("userId")); 
		String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
		String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
		    
		obj.setUserId(userId);
		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));	 
		
		if(action.equals("view") || action.equals("next") || action.equals("prev"))
		{
			String search = getSearchQuery(request, obj);
			obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
			obj.setUserId(userId);
			obj.setNpp_duocchon_id(npp_duocchon_id);
    		obj.setTdv_dangnhap_id(tdv_dangnhap_id);
			String sql=getsumQuery(request, obj);
	    	obj.laytien(sql);
			obj.init(search);
			obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
			session.setAttribute("obj", obj);
			
			String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpLenhXuatHang.jsp";
			response.sendRedirect(nextJSP);
		}
		else
		{
			String search = getSearchQuery(request, obj);
			obj.setUserId(userId);
			obj.setLoainhanvien(session.getAttribute("loainhanvien"));
		    obj.setDoituongId(session.getAttribute("doituongId"));
			obj.setNpp_duocchon_id(npp_duocchon_id);
    		obj.setTdv_dangnhap_id(tdv_dangnhap_id);
		    
			String sql=getsumQuery(request, obj);
	    	obj.laytien(sql);
			obj.initLENHXUATHANG(search);
			obj.setNpp_duocchon_id(npp_duocchon_id);
    		obj.setTdv_dangnhap_id(tdv_dangnhap_id);
    		
			session.setAttribute("obj", obj);  	
			session.setAttribute("userId", userId);
			
			String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpLenhXuatHang.jsp";
			response.sendRedirect(nextJSP);
		}
		
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpDuyetddhNppList obj)
	{
		Utility util = new Utility();
		System.out.println("vao serach rui ne");
		String nppId = request.getParameter("nppId");
		if(nppId == null)
			nppId = "";
		obj.setNppTen(nppId);
		
		String loaidonhang = request.getParameter("loaidonhang");
		if(loaidonhang == null)
			loaidonhang = "";
		obj.setLoaidonhang(loaidonhang);
		System.out.println(":::: LOAI DON HANG: " + loaidonhang );
		
		String doituong = request.getParameter("doituong");
		if(doituong == null)
			doituong = "";
		obj.setDoiTuong(doituong);
		
		String nvbanhang=request.getParameter("nvbanhang");
		if(nvbanhang==null)
			nvbanhang="";
		obj.setNvbanhang(nvbanhang);
		
		
		String ngaygiao=request.getParameter("ngaygiao");
		if(ngaygiao==null)
			ngaygiao="";
		obj.setNgaygiao(ngaygiao);
		
		String khohhid=request.getParameter("khohhid");
		if(khohhid==null)
			khohhid="";
		obj.setKhohh(khohhid);
		
		String nguoigiao=request.getParameter("nguoigiao");
		if(nguoigiao==null)
			nguoigiao="";
		obj.setNguoigiao(nguoigiao);
		
		String tensp=request.getParameter("tensp");
		if(tensp==null)
			tensp="";
		obj.setTensp(tensp);
		
		String thuegtgt=request.getParameter("thuegtgt");
		if(thuegtgt==null)
			thuegtgt="";
		obj.setThuegtgt(thuegtgt);
		
		
		String cksp=request.getParameter("cksp");
		if(cksp==null)
			cksp="";
		obj.setChietkhau(cksp);
		
		String kbhid=request.getParameter("kbhid");
		if(kbhid==null)
			kbhid="";
		obj.setKbhId(kbhid);
		
		String kvid=request.getParameter("kvid");
		if(kvid==null)
			kvid="";
		obj.setKvId(kvid);
		
		String solo=request.getParameter("solo");
		if(solo==null)
			solo="";
		obj.setSolo(solo);
		
		String nguoitao=request.getParameter("nguoitao");
		System.out.println("nguoi tao la "+nguoitao);
		if(nguoitao==null)
			nguoitao="";
		obj.setNguoitao(nguoitao);
		
		String ghichu=request.getParameter("ghichu");
		if(ghichu==null)
			ghichu="";
		obj.setGhichu(ghichu);
		
		// 22/12/2015 thêm trường ngày nợ
		String ngayno = request.getParameter("ngayno");
		if(ngayno == null){
			ngayno = "";
		}
		obj.setNgayno(ngayno);
		
		String query = "select distinct a.PK_SEQ, a.machungtu, a.trangthai, a.ngaydonhang, case a.loaidonhang when 0 then d.ten when 3 then e.ten else c.ten end as nppTEN, a.loaidonhang, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, isnull(a.NOTE, '') as NOTE, isnull(SOTIENTHU, 0) as SOTIENTHU, a.hopdong_fk ,isnull(a.iskm,0) as isKm, a.CS_DUYET, a.SS_DUYET, a.tooltip, a.tooltip_scheme, a.GHICHU, isnull(a.CAPDOGIAOHANG, 0) as CAPDOGIAOHANG " +
							"\n from ERP_DondathangNPP a inner join KHO b on a.kho_fk = b.pk_seq " +
							" \n inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk=a.PK_SEQ "+
							" \n  inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET dhspct on dhspct.dondathang_fk=a.PK_SEQ "+
							"  \n left join KHACHHANG c on a.khachhang_fk = c.pk_seq "+
							"  \n left join   ddkd_khachhang ddkd on ddkd.khachhang_fk=a.KHACHHANG_FK "+ 
							"  \n left join SANPHAM sp on sp.PK_SEQ=dhsp.sanpham_fk	 "+
							" \n left join NVGN_KH nvgn on nvgn.KHACHHANG_FK=a.KHACHHANG_FK "+	
							"\n left join NHANVIENGIAONHAN nvgnn on nvgnn.PK_SEQ=nvgn.NVGN_FK  "+	
							"\n 	left join NHAPHANPHOI d on a.NPP_DAT_FK = d.pk_seq  " +
							" \n 	left join ERP_NHANVIEN e on a.nhanvien_fk = e.pk_seq " +
							"	left join KHACHHANG_KENHBANHANG kbh on c.pk_seq = kbh.khachhang_fk   " +
							"	left join diaban db on db.pk_seq = c.diaban   " +
							"\n 	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
							"\n	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.npp_fk = '" + nppId + "' and a.cs_duyet = '1'  ";
						/*" union all	select a.PK_SEQ, a.trangthai, a.ngaydonhang,  "+
							"	 isnull(c.ten,'') as nppTEN, a.loaidonhang, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO,  "+ 
							"	NV2.TEN as nguoisua, isnull(a.NOTE, '') as NOTE, isnull(SOTIENTHU, 0) as SOTIENTHU, a.hopdong_fk,a.iskm    "+
							"	from ERP_Dondathang a inner join KHO b on a.kho_fk = b.pk_seq 	  "+
							"		left join NHAPHANPHOI c on a.NPP_FK = c.pk_seq  	  "+
							"		inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ     "+
							"		inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ  "+
							"	where a.tructhuoc_fk = '" + nppId + "'  and a.trangthai in ( 0, 4 ) and a.NPP_DACHOT=1 and a.LoaiDonHang=4  and a.kho_fk in "+util.quyen_kho(obj.getUserId());*/
		
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
		
		String sodh = request.getParameter("sodh");
		if(sodh == null)
			sodh = "";
		obj.setSodh(sodh);
		
		String khId = request.getParameter("khId");
		if(khId == null)
			khId = "";
		obj.setKhTen(khId);
		
		String iskm = util.antiSQLInspection(request.getParameter("iskm")==null?"0":request.getParameter("iskm"));
		obj.setIsKm(iskm);
		
		if(doituong.length() > 0){
			//query += " and case a.loaidonhang when 0  then e.TEN  when 3 then d.TimKiem else c.timkiem end LIKE   '%' +UPPER(dbo.ftBoDau(N'%" + doituong + "%'))+'%'";
			query += " and isnull(d.TimKiem, c.timkiem ) LIKE   N'%" + util.replaceAEIOU( doituong.trim() ) + "%'";
		}
		
		if(iskm.length() > 0)
			query += " and a.iskm = '" + iskm + "' ";
		
		if(tungay.length() > 0)
			query += " and a.ngaydonhang >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngaydonhang <= '" + denngay + "'";
		
		if(nppId.length() > 0){
			query += " and a.NPP_FK= '" + nppId + "'";
		}
		
		if(sodh.length()>0){
			query+= " and a.machungtu LIKE '%" + sodh + "%'";
		}
		
		if(khId.length() > 0 ){
			//query += " and isnull(d.TimKiem, c.timkiem ) LIKE   N'%" + util.replaceAEIOU( khId.trim() ) + "%'";
			query += " and ((a.KHACHHANG_FK ='"+ khId +"') or (a.NPP_DAT_FK="+khId+") or(a.nhanvien_fk="+khId+"))";
		}
		if(nguoitao.length()>0)
		{
			query += " and nv.ten like N'%" +nguoitao + "%'";
		}
		if(ngaygiao.length()>0)
		{
			query += "  and a.NgayDeNghi = '" + ngaygiao + "'";
		}
		
		if(khohhid.length()>0)
		{
			query += "  and a.kho_fk = '" + khohhid + "'";
		}
		
		if(tensp.length()>0)
		{
			query += " and dbo.ftBoDau( sp.ten ) like '%" + util.replaceAEIOU(tensp) + "%' ";
		}
	
		if(ghichu.length()>0)
		{
			query+= " and dbo.ftBoDau( a.ghichu ) like N'%" + util.replaceAEIOU( ghichu ) + "%'";
		}
		
		if(nvbanhang.length()>0)
		{
			query+=" and a.DDKD_FK="+nvbanhang;
		}
		
		if(nguoigiao.length()>0)
		{
			query+=" and dbo.ftBoDau( nvgnn.ten ) like N'%" + util.replaceAEIOU( nguoigiao ) + "%'";
		}
		if(trangthai.length()>0)
		{
			if(trangthai.equals("4"))
			{
				query+=" and a.trangthai=4";
			}
			else
				query+=" and a.trangthai<>4";
		}
		
		if(solo.length()>0)
		{
			query+=" and dhspct.solo like '"+solo+"'";
		}
		
		if(kbhid.length()>0)
		{
			query += " and kbh.kbh_fk = '"+kbhid+"'" ;
		}
		if(kvid.length()>0)
		{
			query += " and db.khuvuc_fk = '"+kvid+"'" ;
		}
		
		// 22-12-2015, thêm trường tìm kiếm ngày nợ
		if(ngayno.length() > 0) {
			query += " and c.THOIHANNO =" + ngayno;
		}
		
		
		System.out.print("caau tim kiem " + query);
		return query;
	}
	
	
	private String getsumQuery(HttpServletRequest request, IErpDuyetddhNppList obj)
	{
		Utility util = new Utility();
		System.out.println("vao serach rui ne");
		String nppId = request.getParameter("nppId");
		if(nppId == null)
			nppId = "";
		obj.setNppTen(nppId);
		
		String loaidonhang = request.getParameter("loaidonhang");
		if(loaidonhang == null)
			loaidonhang = "";
		obj.setLoaidonhang(loaidonhang);
		System.out.println(":::: LOAI DON HANG: " + loaidonhang );
		
		String doituong = request.getParameter("doituong");
		if(doituong == null)
			doituong = "";
		obj.setDoiTuong(doituong);
		
		String nvbanhang=request.getParameter("nvbanhang");
		if(nvbanhang==null)
			nvbanhang="";
		obj.setNvbanhang(nvbanhang);
		
		
		String ngaygiao=request.getParameter("ngaygiao");
		if(ngaygiao==null)
			ngaygiao="";
		obj.setNgaygiao(ngaygiao);
		
		String khohhid=request.getParameter("khohhid");
		if(khohhid==null)
			khohhid="";
		obj.setKhohh(khohhid);
		
		String nguoigiao=request.getParameter("nguoigiao");
		if(nguoigiao==null)
			nguoigiao="";
		obj.setNguoigiao(nguoigiao);
		
		String tensp=request.getParameter("tensp");
		if(tensp==null)
			tensp="";
		obj.setTensp(tensp);
		
		String thuegtgt=request.getParameter("thuegtgt");
		if(thuegtgt==null)
			thuegtgt="";
		obj.setThuegtgt(thuegtgt);
		
		
		String cksp=request.getParameter("cksp");
		if(cksp==null)
			cksp="";
		obj.setChietkhau(cksp);
		
		String solo=request.getParameter("solo");
		if(solo==null)
			solo="";
		obj.setSolo(solo);
		
		String nguoitao=request.getParameter("nguoitao");
		System.out.println("nguoi tao la "+nguoitao);
		if(nguoitao==null)
			nguoitao="";
		obj.setNguoitao(nguoitao);
		
		String ghichu=request.getParameter("ghichu");
		if(ghichu==null)
			ghichu="";
		obj.setGhichu(ghichu);
		
		
		String query = "select distinct dhsp.sanpham_fk, round( ( dhsp.soluong * ( round( dhsp.dongiaGOC * ( 1 + dhsp.thueVAT / 100.0), 0 ) ) ), 0 ) as doanhso, "+
						" 	round ( ( dhsp.soluong * round( ( isnull(dhsp.chietkhau_KM, 0) + isnull(dhsp.chietkhau_CSBH, 0) + isnull(dhsp.chietkhau_DLN, 0) ) * ( 1 + dhsp.thueVAT / 100.0  ) , 0 ) )  , 0 ) as chietkhau, "+
						"	round(  ( round( (dhsp.soluong * dhsp.dongiaGOC), 0 ) - ( dhsp.soluong * round( ( isnull(dhsp.chietkhau_KM, 0) + isnull(dhsp.chietkhau_CSBH, 0) + isnull(dhsp.chietkhau_DLN, 0) ), 0 ) ) ) * ( dhsp.thueVAT / 100.0 ), 0 ) as thueVAT, " +
						"	round( (dhsp.soluong * ( round( dhsp.dongia * ( 1 + dhsp.thueVAT / 100.0 ), 0 ) ) ), 0 ) as doanhthu, isnull(a.CAPDOGIAOHANG, 0) as CAPDOGIAOHANG	" +
						"\n from ERP_DondathangNPP a inner join KHO b on a.kho_fk = b.pk_seq " +
							" \n inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk=a.PK_SEQ "+
							" \n  inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET dhspct on dhspct.dondathang_fk=a.PK_SEQ "+
							"  \n left join KHACHHANG c on a.khachhang_fk = c.pk_seq "+
							"  \n left join   ddkd_khachhang ddkd on ddkd.khachhang_fk=a.KHACHHANG_FK "+ 
							"  \n left join SANPHAM sp on sp.PK_SEQ=dhsp.sanpham_fk	 "+
							" \n left join NVGN_KH nvgn on nvgn.KHACHHANG_FK=a.KHACHHANG_FK "+	
							"\n left join NHANVIENGIAONHAN nvgnn on nvgnn.PK_SEQ=nvgn.NVGN_FK  "+	
							"\n 	left join NHAPHANPHOI d on a.NPP_DAT_FK = d.pk_seq  " +
							" \n 	left join ERP_NHANVIEN e on a.nhanvien_fk = e.pk_seq " +
							"\n 	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
							"\n	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.npp_fk = '" + nppId + "' and a.trangthai in ( 1, 4 ) ";
						
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
		
		String sodh = request.getParameter("sodh");
		if(sodh == null)
			sodh = "";
		obj.setSodh(sodh);
		
		String khId = request.getParameter("khId");
		if(khId == null)
			khId = "";
		obj.setKhTen(khId);
		
		String iskm = util.antiSQLInspection(request.getParameter("iskm")==null?"0":request.getParameter("iskm"));
		obj.setIsKm(iskm);
		
		if(doituong.length() > 0){
			//query += " and case a.loaidonhang when 0  then e.TEN  when 3 then d.TimKiem else c.timkiem end LIKE   '%' +UPPER(dbo.ftBoDau(N'%" + doituong + "%'))+'%'";
			query += " and isnull(d.TimKiem, c.timkiem ) LIKE   N'%" + util.replaceAEIOU( doituong.trim() ) + "%'";
		}
		
		if(iskm.length() > 0)
			query += " and a.iskm = '" + iskm + "' ";
		
		if(tungay.length() > 0)
			query += " and a.ngaydonhang >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngaydonhang <= '" + denngay + "'";
		
		if(nppId.length() > 0){
			query += " and a.NPP_FK= '" + nppId + "'";
		}
		
		if(sodh.length()>0){
			query+= " and a.machungtu LIKE '%" + sodh + "%'";
		}
		
		if(khId.length() > 0 ){
			//query += " and case a.loaidonhang when 0 then d.TimKiem when 3 then e.ten else c.timkiem end LIKE N'%" + khId + "%'";
			query += " and isnull(d.TimKiem, c.timkiem ) LIKE   N'%" + util.replaceAEIOU( khId.trim() ) + "%'";
		}
		if(nguoitao.length()>0)
		{
			query += " and dbo.ftBoDau( nv.ten ) like N'%" + util.replaceAEIOU(nguoitao) + "%'";
		}
		if(ngaygiao.length()>0)
		{
			query += "  and a.NgayDeNghi = '" + ngaygiao + "'";
		}
		
		if(khohhid.length()>0)
		{
			query += "  and a.kho_fk = '" + khohhid + "'";
		}
		
		if(tensp.length()>0)
		{
			query += " and dbo.ftBoDau( sp.ten ) like '%" + util.replaceAEIOU(tensp) + "%' ";
		}
	
		if(ghichu.length()>0)
		{
			query+= " and dbo.ftBoDau( a.ghichu ) like N'%" + util.replaceAEIOU( ghichu ) + "%'";
		}
		
		if(nvbanhang.length()>0)
		{
			query+=" and a.DDKD_FK="+nvbanhang;
		}
		
		if(nguoigiao.length()>0)
		{
			query+=" and dbo.ftBoDau( nvgnn.ten ) like N'%" + util.replaceAEIOU( nguoigiao ) + "%'";
		}
		if(trangthai.length()>0)
		{
			if(trangthai.equals("4"))
			{
				query+=" and a.trangthai=4";
			}
			else
				query+=" and a.trangthai<>4";
		}
		
		if(solo.length()>0)
		{
			query+=" and dhspct.solo like '"+solo+"'";
		}
		
		
		String sql= " select SUM(isnull(data.doanhso,0)) as doanhso, " +
				" 	SUM(isnull(data.thueVAT, 0 )) as thuegtgt, SUM(isnull(data.chietkhau,0)) as chietkhau, SUM(isnull(data.doanhthu,0)) as doanhthu from ( "+ query +") as data";
	
		
		System.out.println("Câu query tim kiem: " + sql);
		return sql;
	}
	
	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}
	
	
}
