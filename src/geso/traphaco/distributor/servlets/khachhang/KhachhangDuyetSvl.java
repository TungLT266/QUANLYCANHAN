package geso.traphaco.distributor.servlets.khachhang;

import geso.traphaco.distributor.beans.khachhang.*;
import geso.traphaco.distributor.beans.khachhang.imp.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
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

public class KhachhangDuyetSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private int items = 50;
	private int splittings = 20;
	
    public KhachhangDuyetSvl() 
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
			
			obj.setLoainhanvien(session.getAttribute("loainhanvien"));
		    obj.setDoituongId(session.getAttribute("doituongId"));
			
			String capduyet = request.getParameter("capduyet");
			obj.setCapduyet(capduyet);
			
			String msg="";
			if (action.equals("delete"))
			{	   		  	    	
				try
				{
					msg= Delete(khId, userId, obj);
				} 
				catch (Exception e)
				{
					e.printStackTrace();
				}
				obj.setMsg(msg);
			}

			settingPage(obj);

			String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
			obj.setNpp_duocchon_id(npp_duocchon_id);

			obj.setUserId(userId);
			obj.init("");

			session.setAttribute("obj", obj);

			String nextJSP = "/TraphacoHYERP/pages/Distributor/KhachHangDuyet.jsp";
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

			String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
			obj.setNpp_duocchon_id(npp_duocchon_id);
			
			obj.setLoainhanvien(session.getAttribute("loainhanvien"));
		    obj.setDoituongId(session.getAttribute("doituongId"));

			String capduyet = request.getParameter("capduyet");
			obj.setCapduyet(capduyet);
			
			Utility util = new Utility();
			userId = util.antiSQLInspection(request.getParameter("userId"));

			String action = request.getParameter("action");
			if (action == null){
				action = "";
			}

			settingPage(obj);
			if (action.equals("search") || action.equals("duyetALL") )
			{	    
				String msg = "";
				if(action.equals("duyetALL"))
				{
					msg = this.DuyetALL(request, session.getAttribute("congtyId").toString(), userId, capduyet);
				}

				String search = getSearchQuery(request, obj);

				obj.setUserId(userId);
				obj.init(search);
				obj.setMsg(msg);

				session.setAttribute("obj", obj);  	
				session.setAttribute("userId", userId);

				response.sendRedirect("/TraphacoHYERP/pages/Distributor/KhachHangDuyet.jsp");	    		    	
			}
			else  if(action.equals("view") || action.equals("next") || action.equals("prev")){

				String search = getSearchQuery(request, obj);

				obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
				obj.setUserId(userId);

				obj.init(search);
				obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
				session.setAttribute("obj", obj);
				response.sendRedirect("/TraphacoHYERP/pages/Distributor/KhachHangDuyet.jsp");
			}
	    }
	}
	
	private String DuyetALL(HttpServletRequest request, String congtyId, String userId, String capduyet) 
	{
		String[] dhIds = request.getParameterValues("khIds");
		String msg = "";
		if(dhIds != null)
		{
			for(int i = 0; i < dhIds.length; i++)
			{
				IKhachhang donhang = new Khachhang( dhIds[i] );
				donhang.setCapduyet(capduyet);
				donhang.setUserId(userId);
				
				if(!donhang.duyetKH(congtyId, "benngoai", null))
				{
					System.out.println("--- Khách hàng: " + dhIds[i] + " -- Lỗi: " + donhang.getMessage() );
					msg += "Lỗi khi duyệt khách hàng: " + dhIds[i] + ": " + donhang.getMessage() + " \n";
					donhang.DBclose();
				}
			}
		}
		
		return msg;
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
    	
    	String soItem = request.getParameter("soItems");
		if(soItem == null)
			soItem = "25";
		int soItems = Integer.parseInt(soItem);
		obj.setSoItems(soItems);
    	
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
    	
    	String mst = util.antiSQLInspection(request.getParameter("mst")); 	
    	if (mst == null)
    		mst = "";    		
    	obj.setMst(mst);
    	
    	String tructhuocId = util.antiSQLInspection(request.getParameter("tructhuocId")); 	
    	if (tructhuocId == null)
    		tructhuocId = "";    		
    	obj.setTructhuocId(tructhuocId);
    	
   	String query = "	select  ROW_NUMBER() OVER(ORDER BY a.pk_seq DESC) AS 'stt', ISNULL(a.MA,'') AS MA, isnull(a.mafast,'') as mafast , a.pk_seq as khId, a.smartid, a.ten as khTen, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua, d.chietkhau, d.pk_seq as mckId,  \n"+ 
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
					"	) + ' '  AS tbhTen,a.CHUCUAHIEU  ,a.MaHD,n.ten as LoaiCH, a.TL_DUYET, a.ASM_DUYET, a.CS_DUYET  \n"+ 
					"  from    \n"+
					"  khachhang a inner join nhanvien b on a.nguoitao = b.pk_seq   \n"+ 
					"  inner join nhanvien c on a.nguoisua = c.pk_seq    \n"+
					"  left join mucchietkhau d on a.chietkhau_fk = d.pk_seq   \n"+
					"  left join kenhbanhang e on a.kbh_fk = e.pk_seq    \n"+
					"  left join hangcuahang f on a.hch_fk = f.pk_seq    \n"+
					"  left join loaicuahang g on a.lch_fk = g.pk_seq    \n"+
					"  inner join nhaphanphoi h on a.npp_fk = h.pk_seq    \n"+
					"  left join gioihancongno k on a.ghcn_fk = k.pk_seq    \n"+
					"  left join banggiasieuthi l on a.bgst_fk = l.pk_seq    \n"+
					"  left join vitricuahang m on a.vtch_fk = m.pk_seq    \n"+
					" left join LOAIKHACHHANG n on a.XuatKhau= n.pk_seq  \n"+
					" where 1 = 1 " ;
	   	if( tructhuocId.trim().length() <= 0 )
			query += " a.npp_fk = '" + nppId + "' ";
		else
			query += " and a.npp_fk = '" + tructhuocId + "' ";
    				
    	if (ten.length()>0)
    	{     		
			//query = query + " and ( upper(a.pk_seq) like upper(N'%" + util.replaceAEIOU(ten) + "%') or upper(a.ten) like upper(N'%" + util.replaceAEIOU(ten) + "%')) ";	
			query = query + " and ( upper( a.timkiem ) like upper(N'%" + util.replaceAEIOU(ten) + "%') ) \n";			
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
    		query+=" and a.diadiem_fk="+diadiemId+" ";
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
    	if (mst.length() > 0)
    	{
    		query = query + " and a.MASOTHUE like '%" + mst + "%'";
    	}

    	System.out.println("Query tìm kiếm: " + query + "\n");

    	
    	return query;
	}	
	
	private String Delete(String id, String userId, IKhachhangList obj) throws SQLException
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
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}	
		
}
