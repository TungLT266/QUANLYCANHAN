package geso.traphaco.distributor.servlets.hopdong;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.hopdong.IErpDonhangNppETC;
import geso.traphaco.distributor.beans.hopdong.IErpDonhangNppETCList;
import geso.traphaco.distributor.beans.hopdong.imp.ErpDonhangNppETC;
import geso.traphaco.distributor.beans.hopdong.imp.ErpDonhangNppETCList;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

public class ErpDonhangNppETCSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpDonhangNppETCSvl() {
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
	    
	    String action = util.getAction(querystring);
	   
	    String type = util.antiSQLInspection(request.getParameter("type") == null ? "" : request.getParameter("type"));
	    if(type.equals("GetDonGia"))
    	{
    		NumberFormat formatter = new DecimalFormat("#,###,###.##");
    		Gson gson = new Gson();
    		
  			String spMa = "";
  			String dvdlId ="";

  			String nppId = util.antiSQLInspection(request.getParameter("nppId"));
  			String kbhId = util.antiSQLInspection(request.getParameter("kbhId"));
  			String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));

  			String mahd = request.getParameter("maHopDong");
  			if(mahd == null)
  				mahd = "";
  		
    		String query = (String)request.getQueryString();
    		spMa = new String(query.substring(query.indexOf("&spMa=") + 6, query.indexOf("&dvdlId=")).getBytes("UTF-8"), "UTF-8");
    		spMa = URLDecoder.decode(spMa, "UTF-8").replace("+", " ");
  			
    		dvdlId = new String(query.substring(query.indexOf("&dvdlId=") + 8, query.indexOf("&nppId=")).getBytes("UTF-8"), "UTF-8");
    		dvdlId = URLDecoder.decode(dvdlId, "UTF-8").replace("+", " ");

  			
  			dbutils db = new dbutils();
  			
  			if(mahd.trim().length() <= 0)
  			{
	  			 query = " select a.DVDL_FK as dvCHUAN, ( select PK_SEQ from DONVIDOLUONG where DONVI = N'" + dvdlId + "' ) as dvNEW, " + 
				    	 "	case when a.DVDL_FK =( select PK_SEQ from DONVIDOLUONG where DONVI = N'" + dvdlId + "' ) then 1  "+
				    	 "	else ( select soluong1 / soluong2 from QUYCACH where SANPHAM_FK=a.PK_SEQ and DVDL1_FK = a.DVDL_FK and DVDL2_FK =  "+
				    	 "		( select PK_SEQ from DONVIDOLUONG where DONVI = N'" + dvdlId + "' ) ) end as TyLe,  "+
				    	 "	(select soluong1/soluong2 from QUYCACH where SANPHAM_FK=a.PK_SEQ and DVDL1_FK=a.DVDL_FK and DVDL2_FK=  "+
				    	 "	( select PK_SEQ from DONVIDOLUONG where DONVI =  N'" + dvdlId + "' ) ) as QuyCach_THG ,a.TRONGLUONG,a.THETICH, " +  
						 " 	  isnull( ( select GIAMUANPP * ( 1 - isnull( ( select chietkhau from BANGGIAMUANPP_NPP where banggiamuaNPP_FK = bg_sp.bgmuaNPP_FK and NPP_FK = '" + nppId + "' ), 0) / 100 ) " +
						 "				from BGMUANPP_SANPHAM bg_sp " +
						 "			    where SANPHAM_FK = a.pk_seq " +
						 "					and BGMUANPP_FK in ( select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = '" + nppId + "' and bg.DVKD_FK = '" + dvkdId + "' order by bg.TUNGAY desc ) ), 0) as giamua " + 
						 " from SANPHAM a where a.MA = '" + spMa + "'  ";
  			}
  			else
  			{
  				String ddhId = request.getParameter("ddhId");
  				
  				query =  " select a.DVDL_FK as dvCHUAN, ( select PK_SEQ from DONVIDOLUONG where DONVI = N'" + dvdlId + "' ) as dvNEW, " + 
				    	 "	case when a.DVDL_FK =( select PK_SEQ from DONVIDOLUONG where DONVI = N'" + dvdlId + "' ) then 1  "+
				    	 "	else ( select soluong1 / soluong2 from QUYCACH where SANPHAM_FK=a.PK_SEQ and DVDL1_FK = a.DVDL_FK and DVDL2_FK =  "+
				    	 "		( select PK_SEQ from DONVIDOLUONG where DONVI = N'" + dvdlId + "' ) ) end as TyLe,  "+
				    	 "	(select soluong1/soluong2 from QUYCACH where SANPHAM_FK=a.PK_SEQ and DVDL1_FK=a.DVDL_FK and DVDL2_FK=  "+
				    	 "	( select PK_SEQ from DONVIDOLUONG where DONVI =  N'" + dvdlId + "' ) ) as QuyCach_THG ,a.TRONGLUONG,a.THETICH, " +  
						 " 	  isnull( ( select case when a.dvdl_fk = b.dvdl_fk then dongia " +
						 "						else dongia * ( select SOLUONG1 /SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and DVDL1_FK = b.DVDL_FK  ) end as dongia " +
						 "				from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.pk_seq  " +
						 "				where sanpham_fk = ( select pk_seq from SANPHAM where MA = '" + spMa + "' ) and dondathang_fk = '" + ddhId + "' ), 0 ) as giamua " + 
						 " from SANPHAM a where a.MA = '" + spMa + "'  ";
  			}
  			System.out.println("[Sql] - LAY GIA: " + query);

  			ResultSet rs = db.get(query);
  			double TheTich = 0;		
  			double TrongLuong = 0;
			double DonGia = 0;
			double soluongton = 0;

  			double QuyCach=0;
  			double TyLe = 0;
  			
  			if(rs != null)
  			{
  				try 
  				{
  					if(rs.next())
  					{
  						TheTich=rs.getDouble("thetich");
  						TrongLuong= rs.getDouble("trongluong");
  						DonGia =rs.getDouble("giamua");
						QuyCach = rs.getDouble("QuyCach_THG");
  						TyLe = rs.getDouble("TyLe");
  						
  						SanPham sp = new SanPham();
  						sp.setDongia( formatter.format( Math.round(  ( DonGia * TyLe - 0.005 ) * 100.0 ) / 100.0 ) );

  						sp.setTrongluong(formatter.format(TrongLuong*TyLe));
  						sp.setThetich(formatter.format(TheTich*TyLe));
  						sp.setQuycach(formatter.format(QuyCach) );

  						response.setContentType("application/json");
  						response.setCharacterEncoding("UTF-8");

  						response.getWriter().write(gson.toJson(sp));
  						
  					}
  					rs.close();
  				} 
  				catch (Exception e)
  				{
  					e.printStackTrace();
  				}
  				finally
  				{
  					if( db != null)
  						db.shutDown();
  				}
  			}
    	}
	    else
	    {
	    	String lsxId = util.getId(querystring);
		    obj = new ErpDonhangNppETCList();
		    
		    obj.setTdv_dangnhap_id(tdv_dangnhap_id);
		    obj.setNpp_duocchon_id(npp_duocchon_id);

		    obj.setLoainhanvien(session.getAttribute("loainhanvien"));
		    obj.setDoituongId(session.getAttribute("doituongId"));
		    
		    /*String loaidonhang = request.getParameter("loaidonhang");
		    if(loaidonhang == null)
		    	loaidonhang = "0";
		    obj.setLoaidonhang(loaidonhang);*/
		    
		    String nppId = request.getParameter("nppId");
		    if(nppId == null)
		    	nppId = "";
		    obj.setNppId(nppId);
		    
		    if (action.equals("delete") )
		    {	
		    	String msg = this.DeleteChuyenKho(lsxId);
				obj.setMsg(msg);
		    }
		    else if(action.equals("chot"))
	    	{
	    		String msg = this.Chot(lsxId);
				obj.setMsg(msg);
	    	}
		    else if(action.equals("unchot"))
	    	{
	    		String msg = this.UnChot(lsxId);
				obj.setMsg(msg);
	    	}
		    
		    System.out.println(":::: NPP DUOC CHON: " + npp_duocchon_id);
		    obj.setUserId(userId);
		    obj.init("");
		    
			session.setAttribute("obj", obj);
				
			String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETC.jsp";
			response.sendRedirect(nextJSP);
	    }
	    
	}

	private String UnChot(String lsxId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
		
			String query = "update ERP_DONDATHANGNPP set trangthai = '0' where pk_seq = '" + lsxId + "'";
			if(!db.update(query))
			{
				msg = "Khong the chot: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			db.getConnection().commit();
			db.shutDown();
		}
		catch (Exception e) 
		{
			db.update("rollback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		
		return "";
	}
	
	private String Chot(String lsxId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
		
			String query = "update ERP_DONDATHANGNPP set trangthai = '1' where pk_seq = '" + lsxId + "' and khachhang_fk is not null ";
			if(db.updateReturnInt(query) <= 0)
			{
				msg = "Vui lòng chọn khách hàng ETC cho đơn hàng";
				db.getConnection().rollback();
				return msg;
			}
			
			db.getConnection().commit();
			db.shutDown();
		}
		catch (Exception e) 
		{
			db.update("rollback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		
		return "";
	}

	private String DeleteChuyenKho(String lsxId)
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
		
			String query = "update ERP_DONDATHANGNPP set trangthai = '3' where pk_seq = '" + lsxId + "'";
			if(!db.update(query))
			{
				msg = "Khong the chot: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			//GIAM BOOK, TANG AVAI
			//TANG KHO NGUOC LAI
			/*query = "update kho   " +
					"set kho.available = kho.available + BOOK_KHO.soluong,  " +
					"	kho.booked = kho.booked - BOOK_KHO.soluong  " +
					"from " +
					"( " +
					"	select khoxuat_fk, npp_fk, nhomkenh_fk, sanpham_fk, sum(soluong) as soluong  " +
					"	from " +
					"	( " +
					"		select c.kho_fk as khoxuat_fk, c.npp_fk, '100000' as nhomkenh_fk, a.sanpham_fk,       " +
					"				case when a.dvdl_fk IS null then a.soluong       " +
					"					 when a.dvdl_fk = b.DVDL_FK then a.soluong      " +
					"					 else  a.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk )  end as soluong    " +
					"		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ   " +
					"				inner join ERP_DONDATHANGNPP c on a.dondathang_fk = c.pk_seq     " +
					"		where a.dondathang_fk in (  " + lsxId + "  ) and a.soluong > 0 " +
					"	) " +
					"	DATHANG  " +
					"	group by khoxuat_fk, npp_fk, nhomkenh_fk, sanpham_fk " +
					") " +
					"BOOK_KHO inner join NHAPP_KHO kho on BOOK_KHO.khoxuat_fk = kho.kho_fk and BOOK_KHO.npp_fk = kho.npp_fk and BOOK_KHO.nhomkenh_fk = kho.nhomkenh_fk and BOOK_KHO.sanpham_fk = kho.sanpham_fk ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật NHAPP_KHO " + query;
				db.getConnection().rollback();
				
				return msg;
			}*/
			
			db.getConnection().commit();
			db.shutDown();
		}
		catch (Exception e) 
		{
			db.update("rollback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		
		return "";
		
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
		
		obj.setLoaidonhang(loaidonhang);
	 
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    obj.setUserId(userId);
	    
	    String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
	    String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
	    
	    obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpDonhangNppETC lsxBean = new ErpDonhangNppETC();
	    	//lsxBean.setLoaidonhang(loaidonhang);
	    	lsxBean.setUserId(userId);
	    	lsxBean.setNpp_duocchon_id(npp_duocchon_id);

	    	lsxBean.setLoainhanvien(session.getAttribute("loainhanvien"));
	    	lsxBean.setDoituongId(session.getAttribute("doituongId"));
    		
	    	lsxBean.createRs( tdv_dangnhap_id );
	    	lsxBean.checkKSKD();
	    	
	    	session.setAttribute("dvkdId", "100001");
			session.setAttribute("kbhId", "");
			session.setAttribute("nppId", request.getParameter("nppId"));
			session.setAttribute("hopdongId", "");
			session.setAttribute("khoId", "");
    		
	    	session.setAttribute("lsxBean", lsxBean);
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETCNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj.setUserId(userId);
	    		
	    		obj.setNpp_duocchon_id(npp_duocchon_id);
	    		obj.setTdv_dangnhap_id(tdv_dangnhap_id);
	    		
		    	String search = getSearchQuery(request, obj);
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	String sql = getSumQuery(request, obj);
		    	obj.laytien(sql);
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	
		    	String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETC.jsp";
				response.sendRedirect(nextJSP);
		    }
	    	else
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.setUserId(userId);
		    	
		    	obj.setNpp_duocchon_id(npp_duocchon_id);
	    		obj.setTdv_dangnhap_id(tdv_dangnhap_id);
	    		
		    	String sql = getSumQuery(request, obj);
		    	obj.laytien(sql);
		    	
		    	obj.init(search);				
				System.out.println("Tim kiem "+search);
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
	    		
	    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETC.jsp";
	    		response.sendRedirect(nextJSP);
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpDonhangNppETCList obj)
	{
		Utility util = new Utility();
		
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
		
		String kbhid=request.getParameter("kbhid");
		if(kbhid==null)
			kbhid="";
		obj.setKbhId(kbhid);
		
		String kvid=request.getParameter("kvid");
		if(kvid==null)
			kvid="";
		obj.setKvId(kvid);
		
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
		
		String htbh=request.getParameter("htbhid");
		if(htbh==null)
			htbh="";
		obj.setHtbhId(htbh);
		System.out.println("HTBHID: "+htbh);
		
		String query =  "select distinct a.PK_SEQ, isnull(a.machungtu, '') as machungtu, a.trangthai, isnull(a.hopdong_fk, -1) as hopdong_fk, a.ngaydonhang, a.ngaydenghi, a.tooltip, a.tooltip_scheme, " + 
						" case a.loaidonhang when 0 then d.ten when 3 then e.ten else c.ten end as nppTEN, b.ten as khoTEN, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, isnull(a.NOTE, '') as NOTE, isnull(a.CAPDOGIAOHANG, 0) as CAPDOGIAOHANG,   " +
						"	a.CS_DUYET, a.SS_DUYET, isnull( lydokhongduyet, '' ) as lydokhongduyet, " + 
						" 		ISNULL ( ( select max( hd.trangthai ) from ERP_HOADONNPP hd inner join ERP_HOADONNPP_DDH hd_dh on hd.PK_SEQ = hd_dh.HOADONNPP_FK where hd.TRANGTHAI not in ( 3, 5 ) and hd_dh.DDH_FK = a.pk_seq ), 0 ) as daraHOADON, " +
						" 		ISNULL ( ( select COUNT( PK_SEQ ) from ERP_YCXUATKHONPP where TRANGTHAI != 2 and DDH_FK = a.pk_seq ), 0 ) as daraPGH, " + 
						" 		a.daraSXH, a.mohoadon, a.tongthanhtoan as doanhthu " +
						"from ERP_DonDatHangNPP a inner join KHO b on a.kho_fk = b.pk_seq " +
						"   inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk=a.PK_SEQ "+
						"   inner join sanpham sp on sp.pk_seq= dhsp.sanpham_fk "+
						" 	left join KHACHHANG c on a.khachhang_fk = c.pk_seq "+
						"   left join NVGN_KH nvgn on nvgn.khachhang_fk=a.khachhang_fk "+
						"   left join NHANVIENGIAONHAN nvgnn on nvgnn.PK_SEQ=nvgn.NVGN_FK  "+	
						" 	left join NHAPHANPHOI d on a.npp_dat_fk = d.pk_seq " +
						" 	left join ERP_NHANVIEN e on a.nhanvien_fk = e.pk_seq "+ 
						"	left join ERP_HopDongNPP f on a.HOPDONG_FK = f.PK_SEQ 	" +
						"	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
						"	left join KHACHHANG_KENHBANHANG kbh on c.pk_seq = kbh.khachhang_fk   " +
						"	left join diaban db on db.pk_seq = c.diaban   " +
						"	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ " +
						"where  a.pk_seq > 0 and a.npp_fk = '" + nppId + "'  and a.kho_fk in " + util.quyen_kho(obj.getUserId());
		
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
				query += " AND a.trangthai in ( 0, 1 ) and a.mohoadon = '0' ";
			else if( trangthai.equals("1") )
				query += " AND a.SS_DUYET in ( 1 ) AND a.CS_DUYET in ( 0 ) ";
			else if( trangthai.equals("2") )
			{
				query += " AND a.CS_DUYET in ( 1 ) ";
				query += " AND ( ISNULL ( ( select count( hd.pk_seq ) from ERP_HOADONNPP hd inner join ERP_HOADONNPP_DDH hd_dh on hd.PK_SEQ = hd_dh.HOADONNPP_FK where hd.TRANGTHAI not in ( 3, 5 ) and hd_dh.DDH_FK = a.pk_seq ), 0 ) < 1 ) ";
			}
			else if( trangthai.equals("3") ) //Chờ xử lý
				query += " AND a.trangthai in ( 0, 1 ) and a.mohoadon = '1' ";
			else if( trangthai.equals("4") ) //Đã ra phiếu giao hàng
			{
				//query += " AND ( ISNULL ( ( select COUNT( PK_SEQ ) from ERP_YCXUATKHONPP where TRANGTHAI != 2 and DDH_FK = a.pk_seq ), 0 ) >= 1 ) ";
				query += " AND a.daraSXH = '1' ";
			}
			else if( trangthai.equals("5") ) //Đã hủy đơn hàng
				query += " AND a.trangthai in ( 3 ) ";
			else if( trangthai.equals("7") ) //Chưa in hóa đơn
				query += " AND ( ISNULL ( ( select max( hd.trangthai ) from ERP_HOADONNPP hd inner join ERP_HOADONNPP_DDH hd_dh on hd.PK_SEQ = hd_dh.HOADONNPP_FK where hd.TRANGTHAI not in ( 3, 5 ) and hd_dh.DDH_FK = a.pk_seq ), 0 ) = 1 ) ";
			else if( trangthai.equals("8") ) //Đã in hóa đơn
			{
				query += " AND ( ISNULL ( ( select max( hd.trangthai ) from ERP_HOADONNPP hd inner join ERP_HOADONNPP_DDH hd_dh on hd.PK_SEQ = hd_dh.HOADONNPP_FK where hd.TRANGTHAI not in ( 3, 5 ) and hd_dh.DDH_FK = a.pk_seq ), 0 ) in ( 2, 4 ) ) ";
				query += " AND ( ISNULL ( ( select COUNT( PK_SEQ ) from ERP_YCXUATKHONPP where TRANGTHAI != 2 and DDH_FK = a.pk_seq ), 0 ) < 1 ) ";
			}
		}
		
		if(nvbanhang.length() > 0)
		{
			query+=" and a.DDKD_FK = " + nvbanhang;
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
		if(htbh.length()>0)
		{
			query += " and kbh.kbh_fk in (select kbh_fk from hethongbanhang_kenhbanhang where htbh_fk = '"+htbh+"')" ;
		}
		if(kbhid.length()>0)
		{
			query += " and kbh.kbh_fk = '"+kbhid+"'" ;
		}
		if(kvid.length()>0)
		{
			query += " and db.khuvuc_fk = '"+kvid+"'" ;
		}
		
		
		// bổ sung 22/12/2015
		if(ngayno.length() >0){
			query += " and c.THOIHANNO= "+ ngayno ;
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
	
	private String getSumQuery(HttpServletRequest request, IErpDonhangNppETCList obj)
	{
		Utility util = new Utility();
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
		{
			soHopDong = "";
		}
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
		
		/*String query = "select distinct dhsp.sanpham_fk, round( (dhsp.soluong * dhsp.dongiaGOC), 0 ) as doanhso, "+
						" 	( dhsp.soluong * round( ( isnull(dhsp.chietkhau_KM, 0) + isnull(dhsp.chietkhau_CSBH, 0) + isnull(dhsp.chietkhau_DLN, 0) ), 0 ) ) as chietkhau, "+
						"	round(  ( round( (dhsp.soluong * dhsp.dongiaGOC), 0 ) - ( dhsp.soluong * round( ( isnull(dhsp.chietkhau_KM, 0) + isnull(dhsp.chietkhau_CSBH, 0) + isnull(dhsp.chietkhau_DLN, 0) ), 0 ) ) ) * ( dhsp.thueVAT / 100.0 ), 0 ) as thueVAT " +
						"from ERP_DonDatHangNPP a inner join KHO b on a.kho_fk = b.pk_seq " +
						"  inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk=a.PK_SEQ "+
						"  inner join sanpham sp on sp.pk_seq= dhsp.sanpham_fk "+
						"  left join KHACHHANG c on a.khachhang_fk = c.pk_seq "+
						"  left join NVGN_KH nvgn on nvgn.khachhang_fk=a.khachhang_fk "+
						"  left join NHANVIENGIAONHAN nvgnn on nvgnn.PK_SEQ=nvgn.NVGN_FK  "+	
						"  left join NHAPHANPHOI d on a.npp_dat_fk = d.pk_seq " +
						"  left join ERP_NHANVIEN e on a.nhanvien_fk = e.pk_seq "+ 
						"  left join ERP_HopDongNPP f on a.HOPDONG_FK = f.PK_SEQ 	" +
						"  inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
						"  inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ " +
						"where  a.pk_seq > 0 and a.npp_fk = '" + nppId + "'  and a.kho_fk in " + util.quyen_kho(obj.getUserId());*/
		
		String query = "select distinct dhsp.sanpham_fk, round( ( dhsp.soluong * ( round( dhsp.dongiaGOC * ( 1 + dhsp.thueVAT / 100.0), 0 ) ) ), 0 ) as doanhso, "+
				" 	round ( ( dhsp.soluong * round( ( isnull(dhsp.chietkhau_KM, 0) + isnull(dhsp.chietkhau_CSBH, 0) + isnull(dhsp.chietkhau_DLN, 0) ) * ( 1 + dhsp.thueVAT / 100.0  ) , 0 ) )  , 0 ) as chietkhau, "+
				"	round(  ( round( (dhsp.soluong * dhsp.dongiaGOC), 0 ) - ( dhsp.soluong * round( ( isnull(dhsp.chietkhau_KM, 0) + isnull(dhsp.chietkhau_CSBH, 0) + isnull(dhsp.chietkhau_DLN, 0) ), 0 ) ) ) * ( dhsp.thueVAT / 100.0 ), 0 ) as thueVAT, " +
				"	a.tongthanhtoan as doanhthu	" +
				"from ERP_DonDatHangNPP a inner join KHO b on a.kho_fk = b.pk_seq " +
				"  inner join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk=a.PK_SEQ "+
				"  inner join sanpham sp on sp.pk_seq= dhsp.sanpham_fk "+
				"  left join KHACHHANG c on a.khachhang_fk = c.pk_seq "+
				"  left join NVGN_KH nvgn on nvgn.khachhang_fk=a.khachhang_fk "+
				"  left join NHANVIENGIAONHAN nvgnn on nvgnn.PK_SEQ=nvgn.NVGN_FK  "+	
				"  left join NHAPHANPHOI d on a.npp_dat_fk = d.pk_seq " +
				"  left join ERP_NHANVIEN e on a.nhanvien_fk = e.pk_seq "+ 
				"  left join ERP_HopDongNPP f on a.HOPDONG_FK = f.PK_SEQ 	" +
				"  inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
				"  inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ " +
				"where  a.pk_seq > 0 and a.npp_fk = '" + nppId + "'  and a.kho_fk in " + util.quyen_kho(obj.getUserId());
		
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
				query += " AND a.trangthai in ( 0, 1 ) and a.mohoadon = '0' ";
			else if( trangthai.equals("1") )
				query += " AND a.SS_DUYET in ( 1 ) AND a.CS_DUYET in ( 0 ) ";
			else if( trangthai.equals("2") )
			{
				query += " AND a.CS_DUYET in ( 1 ) ";
				query += " AND ( ISNULL ( ( select count( hd.pk_seq ) from ERP_HOADONNPP hd inner join ERP_HOADONNPP_DDH hd_dh on hd.PK_SEQ = hd_dh.HOADONNPP_FK where hd.TRANGTHAI not in ( 3, 5 ) and hd_dh.DDH_FK = a.pk_seq ), 0 ) < 1 ) ";
			}
			else if( trangthai.equals("3") ) //Chờ xử lý
				query += " AND a.trangthai in ( 0, 1 ) and a.mohoadon = '1' ";
			else if( trangthai.equals("4") ) //Đã ra phiếu giao hàng
			{
				//query += " AND ( ISNULL ( ( select COUNT( PK_SEQ ) from ERP_YCXUATKHONPP where TRANGTHAI != 2 and DDH_FK = a.pk_seq ), 0 ) >= 1 ) ";
				query += " AND a.daraSXH = '1' ";
			}
			else if( trangthai.equals("5") ) //Đã hủy đơn hàng
				query += " AND a.trangthai in ( 3 ) ";
			else if( trangthai.equals("7") ) //Chưa in hóa đơn
				query += " AND ( ISNULL ( ( select max( hd.trangthai ) from ERP_HOADONNPP hd inner join ERP_HOADONNPP_DDH hd_dh on hd.PK_SEQ = hd_dh.HOADONNPP_FK where hd.TRANGTHAI not in ( 3, 5 ) and hd_dh.DDH_FK = a.pk_seq ), 0 ) = 1 ) ";
			else if( trangthai.equals("8") ) //Đã in hóa đơn
			{
				query += " AND ( ISNULL ( ( select max( hd.trangthai ) from ERP_HOADONNPP hd inner join ERP_HOADONNPP_DDH hd_dh on hd.PK_SEQ = hd_dh.HOADONNPP_FK where hd.TRANGTHAI not in ( 3, 5 ) and hd_dh.DDH_FK = a.pk_seq ), 0 ) in ( 2, 4 ) ) ";
				query += " AND ( ISNULL ( ( select COUNT( PK_SEQ ) from ERP_YCXUATKHONPP where TRANGTHAI != 2 and DDH_FK = a.pk_seq ), 0 ) < 1 ) ";
			}
		}
		
		if(nvbanhang.length()>0)
		{
			query+=" and a.DDKD_FK = " + nvbanhang;
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
		
		String sql= " select SUM(isnull(data.doanhso,0)) as doanhso, " +
					" 	SUM(isnull(data.thueVAT, 0 )) as thuegtgt, SUM(isnull(data.chietkhau,0)) as chietkhau, SUM(isnull(data.doanhthu,0)) as doanhthu from ( "+ query +") as data";
		
		System.out.println("Câu query tinh tien: " + sql);
		return sql;
	}
	
	class SanPham
	{
		String dongia;
		String quycach ;
		String trongluong;
		String thetich;
		String soluongton;
		public String getSoluongton()
		{
			return soluongton;
		}
		public void setSoluongton(String soluongton)
		{
			this.soluongton = soluongton;
		}
		public String getTrongluong()
	    {
	    	return trongluong;
	    }
		public void setTrongluong(String trongluong)
	    {
	    	this.trongluong = trongluong;
	    }
		public String getThetich()
	    {
	    	return thetich;
	    }
		public void setThetich(String thetich)
	    {
	    	this.thetich = thetich;
	    }
		public String getQuycach()
	    {
	    	return quycach;
	    }
		public void setQuycach(String quycach)
	    {
	    	this.quycach = quycach;
	    }
		public String getDongia() 
		{
			return dongia;
		}
		public void setDongia(String dongia) 
		{
			this.dongia = dongia;
		}
		
		
	}
	
	
}
