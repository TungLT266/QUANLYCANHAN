package geso.traphaco.distributor.servlets.donhang;

import geso.traphaco.distributor.beans.donhang.IDuyetdonhangList;
import geso.traphaco.distributor.beans.donhang.imp.Donhang;
import geso.traphaco.distributor.beans.donhang.imp.DuyetdonhangList;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class DuyetdonhangSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;

	private int items = 50; 
	//String userId;
	//String nppId;

	private int splittings = 20;
	
    public DuyetdonhangSvl() 
    {
        super();
    }
    
	private void settingPage(IDuyetdonhangList obj) {
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
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    	    
	   
	    session.setMaxInactiveInterval(30000);
	    String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
		String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();

	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    //out.println(userId);
	    System.out.println();

	    System.out.println("DonhangSvl user :" + userId + "  ,sessionId: " + session.getId() );
		   
	    if (userId.length()==0)
	    	userId = request.getParameter("userId");
	    String nppId;
	    if(request.getParameter("nppId") != null)
	    	 nppId = request.getParameter("nppId");
	  
			
	    //Lay Nha PP Theo Dang Nhap Moi
	    nppId = util.getIdNhapp(userId);
	    
	    String action = util.getAction(querystring);
	    if(action == null)
	    	action = request.getParameter("action");
	    System.out.println("ACTION LA: " + action);
	    
	    String msg = "";
	    
	    IDuyetdonhangList obj = new DuyetdonhangList();	
	    obj.setUserId(userId);
	    obj.setNpp_duocchon_id(npp_duocchon_id);
	    obj.setTdv_dangnhap_id(tdv_dangnhap_id);
	    obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
	    
	    String capduyet = request.getParameter("capduyet");
	    if(capduyet == null)
	    	capduyet = "";
	    obj.setCapduyet(capduyet);
	    
	    settingPage(obj);
		obj.init("", "1");
	    obj.setMsg(msg);
		session.setAttribute("obj", obj);
		session.setAttribute("khId", "");
				
		String nextJSP = "/TraphacoHYERP/pages/Distributor/DuyetDonHang.jsp";
		response.sendRedirect(nextJSP);
		}
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
		
			request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    
			session.setMaxInactiveInterval(30000);
			String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
			String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
			
			userId = request.getParameter("userId");
		    System.out.println();
		    System.out.println("DonhangSvl user :" + userId + "  ,sessionId: " + session.getId()) ;
		    String action = request.getParameter("action");
		    if (action == null){
		    	action = "";
		    }
		   
		    
		    IDuyetdonhangList obj = new DuyetdonhangList();
	    	settingPage(obj);
		   
	    	obj.setUserId(userId);
	    	obj.setLoainhanvien(session.getAttribute("loainhanvien"));
		    obj.setDoituongId(session.getAttribute("doituongId"));
		    obj.setNpp_duocchon_id(npp_duocchon_id);
		    obj.setTdv_dangnhap_id(tdv_dangnhap_id);
		    
	    	String capduyet = request.getParameter("capduyet");
		    if(capduyet == null)
		    	capduyet = "";
		    obj.setCapduyet(capduyet);
	    	
		    if(action.equals("search"))
		    {
		    	obj.setUserId(userId);
		    	String search = getSearchQuery(request, obj);

		    	obj.init(search, "1");
	    		session.setAttribute("userId", userId);
	    		session.setAttribute("obj", obj);
	    			    		
	    		response.sendRedirect("/TraphacoHYERP/pages/Distributor/DuyetDonHang.jsp");	    		    	
		    }
		    else if(action.equals("view") || action.equals("next") || action.equals("prev"))
		    {
		    	obj.setUserId(userId);
		    	System.out.println("____"+request.getParameter("nxtApprSplitting")+"____");
		    	String search = getSearchQuery(request, obj);
		    	
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    			
		    	obj.init(search, "1");
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Distributor/DuyetDonHang.jsp");
		    }
		    else if(action.equals("duyetALL"))
			{
				String msg = this.DuyetALL(request, userId, capduyet);
				obj.setMsg(msg);
				
				obj.setUserId(userId);
		    	String search = getSearchQuery(request, obj);

		    	obj.init(search, "1");
	    		session.setAttribute("userId", userId);
	    		session.setAttribute("obj", obj);
	    			    		
	    		response.sendRedirect("/TraphacoHYERP/pages/Distributor/DuyetDonHang.jsp");	  
			}
		    else if(action.equals("cs_moduyet"))
			{
				String msg = this.CS_MoDuyet( cutil.antiSQLInspection(request.getParameter("dhId")) , userId, capduyet);
				obj.setMsg(msg);
				
				obj.setUserId(userId);
		    	String search = getSearchQuery(request, obj);

		    	obj.init(search, "1");
	    		session.setAttribute("userId", userId);
	    		session.setAttribute("obj", obj);
	    			    		
	    		response.sendRedirect("/TraphacoHYERP/pages/Distributor/DuyetDonHang.jsp");	  
			}
	    	else if (action.equals("toExcel")) 
	    	{
				ToExcel(response, obj,getSearchQuery_Excel(request,obj));
			} 
		    
		}
	}
	
	private String CS_MoDuyet(String id, String userId, String capduyet) 
	{
		dbutils db = new dbutils();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "";
			
			/*if( capduyet.equals("SS") )
			{*/
				query = " update kho "+
						 " 	set kho.AVAILABLE = kho.AVAILABLE + DH.SOLUONG,"+
						 " 		kho.SOLUONG = kho.SOLUONG + DH.SOLUONG"+
						 " from NHAPP_KHO kho inner join"+
						 " ("+
						 " 	select a.NPP_FK, a.KHO_FK, 100000 as nhomkenh_fk, b.SANPHAM_FK, b.SOLUONG"+
						 " 	from DONHANG a inner join DONHANG_SANPHAM b on a.PK_SEQ = b.DONHANG_FK "+
						 " 	where DONHANG_FK = '" + id + "' "+
						 " )"+
						 " DH on kho.NPP_FK = DH.NPP_FK and kho.KHO_FK = DH.KHO_FK and kho.nhomkenh_fk = DH.nhomkenh_fk and kho.SANPHAM_FK = DH.SANPHAM_FK";
				System.out.println("1. HANG BAN: " + query);
				if( db.updateReturnInt(query) < 1 )
				{
					db.getConnection().rollback();
					return "1. Lỗi khi mở duyệt: " + query;
				}
				
				query =  " update kho "+
						 " 	set kho.AVAILABLE = kho.AVAILABLE + DH.SOLUONG,"+
						 " 		kho.SOLUONG = kho.SOLUONG + DH.SOLUONG"+
						 " from NHAPP_KHO_CHITIET kho inner join"+
						 " ("+
						 " 	select a.NPP_FK, a.KHO_FK, 100000 as nhomkenh_fk, b.SANPHAM_FK, b.SOLO, b.NGAYHETHAN, sum(b.SOLUONG) as soluong"+
						 " 	from DONHANG a inner join DONHANG_SANPHAM_CHITIET b on a.PK_SEQ = b.DONHANG_FK "+
						 " 	where DONHANG_FK = '" + id + "' and dbo.trim( ISNULL( scheme, '' ) ) = ''"+
						 " 	group by a.NPP_FK, a.KHO_FK, b.SANPHAM_FK, b.SOLO, b.NGAYHETHAN"+
						 " )"+
						 " DH on kho.NPP_FK = DH.NPP_FK and kho.KHO_FK = DH.KHO_FK and kho.nhomkenh_fk = DH.nhomkenh_fk and kho.SANPHAM_FK = DH.SANPHAM_FK"+
						 " 		and kho.SOLO = DH.SOLO and kho.NGAYHETHAN = DH.NGAYHETHAN";
				System.out.println("2. HANG BAN: " + query);
				if( db.updateReturnInt(query) < 1 )
				{
					db.getConnection().rollback();
					return "2. Lỗi khi mở duyệt: " + query;
				}
			/*}
			else if( capduyet.equals("CS") ) //Mở duyệt hàng KM
			{*/
				query =  " update kho "+
						 " 	set kho.AVAILABLE = kho.AVAILABLE + DH.SOLUONG,"+
						 " 		kho.SOLUONG = kho.SOLUONG + DH.SOLUONG"+
						 " from NHAPP_KHO_DDKD kho inner join"+
						 " ("+
						 " 	select a.NPP_FK, a.KHO_FK, 100000 as nhomkenh_fk, b.SANPHAM_FK, a.DDKD_FK, sum(b.SOLUONG) as soluong"+
						 " 	from DONHANG a inner join DONHANG_SANPHAM_CHITIET b on a.PK_SEQ = b.DONHANG_FK "+
						 " 	where DONHANG_FK = '" + id + "' and dbo.trim( ISNULL( scheme, '' ) ) != ''"+
						 " 	group by a.NPP_FK, a.KHO_FK, b.SANPHAM_FK, a.DDKD_FK"+
						 " )"+
						 " DH on kho.NPP_FK = DH.NPP_FK and kho.KHO_FK = DH.KHO_FK and kho.nhomkenh_fk = DH.nhomkenh_fk "+
						 " 		and kho.SANPHAM_FK = DH.SANPHAM_FK and kho.ddkd_fk = DH.DDKD_FK";
				System.out.println("3. HANG KM: " + query);
				if( db.updateReturnInt(query) < 0 )
				{
					db.getConnection().rollback();
					return "3. Lỗi khi mở duyệt: " + query;
				}
				
				query =  " update kho "+
						 " 	set kho.AVAILABLE = kho.AVAILABLE + DH.SOLUONG,"+
						 " 		kho.SOLUONG = kho.SOLUONG + DH.SOLUONG"+
						 " from NHAPP_KHO_DDKD_CHITIET kho inner join"+
						 " ("+
						 " 	select a.NPP_FK, a.KHO_FK, 100000 as nhomkenh_fk, b.SANPHAM_FK, a.DDKD_FK, b.SOLO, b.NGAYHETHAN, sum(b.SOLUONG) as soluong"+
						 " 	from DONHANG a inner join DONHANG_SANPHAM_CHITIET b on a.PK_SEQ = b.DONHANG_FK "+
						 " 	where DONHANG_FK = '" + id + "' and dbo.trim( ISNULL( scheme, '' ) ) != ''"+
						 " 	group by a.NPP_FK, a.KHO_FK, b.SANPHAM_FK, a.DDKD_FK, b.SOLO, b.NGAYHETHAN"+
						 " )"+
						 " DH on kho.NPP_FK = DH.NPP_FK and kho.KHO_FK = DH.KHO_FK and kho.nhomkenh_fk = DH.nhomkenh_fk "+
						 " 		and kho.SANPHAM_FK = DH.SANPHAM_FK and kho.ddkd_fk = DH.DDKD_FK"+
						 " 		and kho.SOLO = DH.SOLO and kho.NGAYHETHAN = DH.NGAYHETHAN";
				System.out.println("4. HANG BAN: " + query);
				if( db.updateReturnInt(query) < 0 )
				{
					db.getConnection().rollback();
					return "4. Lỗi khi mở duyệt: " + query;
				}
			//}
			
			query = "update DONHANG set CS_DUYET = 0, SS_DUYET = 0, trangthai = 0, thoidiem_moduyet = '" + this.getDateTime() + "', user_moduyet = '" + userId + "' " + 
					" where pk_seq = '" + id + "' ";
			if( db.updateReturnInt(query) < 1 )
			{
				db.getConnection().rollback();
				return "5. Lỗi khi mở duyệt: " + query;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			try {
				db.getConnection().rollback();
			} 
			catch (Exception e1) { }
			
			db.shutDown();
		}
		
		db.shutDown();
		return "";
	}

	private String DuyetALL(HttpServletRequest request, String userId, String capduyet) 
	{
		String[] dhIds = request.getParameterValues("donhangIds");
		String msg = "";
		if(dhIds != null)
		{
			for(int i = 0; i < dhIds.length; i++)
			{
				Donhang donhang = new Donhang( dhIds[i] );
				donhang.setCapduyet(capduyet);
				donhang.setUserId(userId);
				String msg2 = donhang.CS_SS_DuyetDonHang(dhIds[i], userId );
				if( msg2.trim().length() > 0 )
				{
					System.out.println("--- Đơn hàng: " + dhIds[i] + " -- Lỗi: " + msg2 );
					msg += "Lỗi khi duyệt đơn hàng: " + dhIds[i] + " \n";
					donhang.DBclose();
				}
			}
		}
		
		return msg;
	}
	
	
	private String getSearchQuery_Excel(HttpServletRequest request,IDuyetdonhangList obj) 
	{
		String nppId = request.getParameter("nppId");
		if ( nppId == null)
			nppId = "";
		obj.setNppId(nppId);

		String ddkdId = request.getParameter("ddkdTen");
		if ( ddkdId == null)
			ddkdId = "";
		obj.setDdkdId(ddkdId);

		String trangthai = request.getParameter("trangthai");
		if (trangthai == null)
			trangthai = "";    	
		obj.setTrangthai(trangthai);
		System.out.println(" ---- TRANG THAI LA: " + trangthai);

		String tungay = request.getParameter("tungay");
		if (tungay == null)
			tungay = "";    	
		obj.setTungay(tungay);

		String denngay = request.getParameter("denngay");
		if (denngay == null)
			denngay = "";    	
		obj.setDenngay(denngay);

		String sodonhang = request.getParameter("sodonhang");
		if (sodonhang == null)
			sodonhang = "";    	
		obj.setSohoadon(sodonhang.trim());

		String khachhang = request.getParameter("khachhang");
		if (khachhang == null)
			khachhang = "";    	
		obj.setKhachhang(khachhang.trim());

		String mafast = request.getParameter("mafast");
		if(mafast==null)
			mafast="";
		obj.setMafast(mafast);

		String query =  "	select b.DONHANG_FK,a.NGAYNHAP,c.maFAST,c.TEN,c.DIACHI,d.MA as spMa,b.SOLUONG  "+
						"			from donhang a inner join donhang_sanpham b on b.donhang_fk=a.pk_Seq   "+
						"			inner join khachhang c on c.PK_SEQ=a.KHACHHANG_FK  "+
						"			inner join SANPHAM d on d.PK_SEQ=b.SANPHAM_FK  "+
						"		where a.npp_Fk = '"+nppId+"'  ";

		if (ddkdId.length() > 0)
		{
			//query = query + " and a.ddkd_Fk = '" + ddkdId + "'";		
			query+=" and a.KHACHHANG_FK in (select KHACHHANG_FK from KHACHHANG_TUYENBH a inner join TUYENBANHANG b on b.PK_SEQ=a.TBH_FK  where b.DDKD_FK="+ddkdId+")";

		}    

		if (trangthai.length() > 0)
		{
			if(trangthai.equals("4"))
			{
				query += " and a.trangthai in (1,3) and a.DAXUATHOADON  = 1 ";
			}
			else if (trangthai.equals("7"))
			{
				query += " and  a.DAIN  = 1 ";
			}
			else
			{
				if(trangthai.equals("1"))
				{
					query += " and a.trangthai ='1' and a.DAXUATHOADON  = 0 ";
				}
				else
				{
					query += " and a.trangthai = '" + trangthai + "'";
				}
			}
		}
		else
			query += " and a.trangthai != '2' ";

		if (tungay.length() > 0)
		{
			query = query + " and a.ngaynhap >= '" + tungay + "'";			
		}    	
		if (denngay.length() > 0)
		{
			query = query + " and a.ngaynhap <= '" + denngay + "'";	
		}
		if (sodonhang.length() > 0)
		{
			query = query + " and a.pk_seq like '%" + sodonhang + "%'";	
		}
		if (khachhang.length() > 0)
		{
			Utility util = new Utility();
			query = query + " and (c.smartid like '%"+ util.replaceAEIOU(khachhang) +"%' or c.pk_seq like (N'%" + util.replaceAEIOU(khachhang) + "%') or [dbo].[fuConvertToUnsign1](lower(c.ten)) like lower(N'%" + util.replaceAEIOU(khachhang) + "%') )";	
			// System.out.println("1/ bỏ dấu: " + util.replaceAEIOU(khachhang));
		}
		if (mafast.length() > 0)
		{
			query = query + " and c.maFAST like '%" + mafast + "%'";	
		}
		System.out.println("Query cua ban: " + query);
		return query;

	}
	
	private String getSearchQuery(HttpServletRequest request,IDuyetdonhangList obj) 
	{
		Utility util = new Utility();
    	String nppId = request.getParameter("nppId");
    	if ( nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
    	String ddkdId = request.getParameter("ddkdTen");
    	if ( ddkdId == null)
    		ddkdId = "";
    	obj.setDdkdId(ddkdId);
    	
    	String trangthai = request.getParameter("trangthai");
    	if (trangthai == null)
    		trangthai = "";    	
    	obj.setTrangthai(trangthai);
    	System.out.println(" ---- TRANG THAI LA: " + trangthai);
    	
    	String tungay = request.getParameter("tungay");
    	if (tungay == null)
    		tungay = "";    	
    	obj.setTungay(tungay);
    	
    	String denngay = request.getParameter("denngay");
    	if (denngay == null)
    		denngay = "";    	
    	obj.setDenngay(denngay);
    	
    	String sodonhang = request.getParameter("sodonhang");
    	if (sodonhang == null)
    		sodonhang = "";    	
    	obj.setSohoadon(sodonhang.trim());
    	
    	String khachhang = request.getParameter("khachhang");
    	if (khachhang == null)
    		khachhang = "";    	
    	obj.setKhachhang(khachhang.trim());
    	
    	String mafast = request.getParameter("mafast");
    	if(mafast==null)
    		mafast="";
    	obj.setMafast(mafast);
    	
    	String tructhuocId = request.getParameter("tructhuocId");
    	if ( tructhuocId == null)
    		tructhuocId = "";
    	obj.setNppTructhuocIds(tructhuocId);
    	
    	/*if ( ddkdId.trim().length() <= 0 && trangthai.trim().length() <= 0 && tungay.trim().length() <= 0 && denngay.trim().length() <= 0 
    			&& sodonhang.trim().length() <= 0 && khachhang.trim().length() <= 0 && mafast.trim().length() <= 0 )
    		obj.setIsSearch(false);
    	else
    		obj.setIsSearch(true);*/
    	
    	obj.setTtId(util.antiSQLInspection(request.getParameter("ttId")) == null ? "" : util.antiSQLInspection(request.getParameter("ttId")));
    	
    	obj.setQhId(util.antiSQLInspection(request.getParameter("qhId")) == null ? "" : util.antiSQLInspection(request.getParameter("qhId")));
    	
    	String query = "select a.machungtu, a.pk_seq as dhId, a.ngaynhap, a.trangthai, a.ngaytao, a.ngaysua, d.maFAST, isnull(a.DAXUATHOADON,0) as DAXUATHOADON , isnull(DAIN, '0') as DAINDH,     " +
						"			c.ten as nguoisua, d.ten as khTen, d.pk_seq as khId, e.pk_seq as ddkdId, e.ten as ddkdTen,        " +
						"			'' as nppTen, a.tonggiatri, d.THANHTOAN, a.VAT " +
						", ' ' as nguoitao, 0 as exitPXK ,     " +
						"STUFF   "+     
							"(  "+      
								"(  "+     
								"	select DISTINCT TOP 100 PERCENT ' , ' + RTRIM(ltrim(isnull(aa.pk_seq,''))) +' '+isnull(cast(aa.LOAIHOADON as nvarchar),'') "+ 
								"	from HOADON aa inner join HOADON_DDH bb on bb.HOADON_FK=aa.PK_SEQ    "+ 
								"	where aa.TRANGTHAI in (2,4) and bb.DDH_FK=A.PK_SEQ    "+ 
								"	ORDER BY ' , ' +  RTRIM(ltrim(isnull(aa.pk_seq,''))) +' '+isnull(cast(aa.LOAIHOADON as nvarchar),'')  "+  
								"	FOR XML PATH('')         "+ 
								" ), 1, 2, ''      "+ 
							") AS SoHoaDon, d.CAPDOGIAOHANG, a.CS_DUYET, a.SS_DUYET, a.tooltip  "+
						" from donhang a   " +
						"		inner  join nhanvien c on a.nguoisua = c.pk_seq inner join khachhang d on a.khachhang_fk = d.pk_seq        " +
						"		left join daidienkinhdoanh e on a.ddkd_fk = e.pk_seq  " +
						"where a.npp_fk in ( select pk_seq from NHAPHANPHOI where tructhuoc_fk = '" + nppId + "' )  ";
    	
    	if (ddkdId.length() > 0)
    	{
			query = query + " and e.pk_seq = '" + ddkdId + "'";		
    	}    
    	
    	if (trangthai.length() > 0)
    	{
    		if(trangthai.equals("4"))
    		{
    			query += " and a.trangthai in (1,3) and a.DAXUATHOADON  = 1 ";
    		}
    		else if (trangthai.equals("7"))
    		{
    			query += " and  a.DAIN  = 1 ";
    		}
    		else
    		{
	    		if(trangthai.equals("1"))
	    		{
	    			query += " and a.trangthai ='1' and a.DAXUATHOADON  = 0 ";
	    		}
	    		else
	    		{
	    			query += " and a.trangthai = '" + trangthai + "'";
	    		}
    		}
    	}
    	else
    		query += " and a.trangthai != '2' ";
    	
    	if (tungay.length() > 0)
    	{
			query = query + " and a.ngaynhap >= '" + tungay + "'";			
    	}    	
    	if (denngay.length() > 0)
    	{
			query = query + " and a.ngaynhap <= '" + denngay + "'";	
    	}
    	if (sodonhang.length() > 0)
    	{
    		query = query + " and a.pk_seq like '%" + sodonhang + "%'";	
    	}
    	if (khachhang.length() > 0)
    	{
    		//query = query + " and (d.smartid like '%"+ util.replaceAEIOU(khachhang) +"%' or d.pk_seq like (N'%" + util.replaceAEIOU(khachhang) + "%') or [dbo].[fuConvertToUnsign1](lower(d.ten)) like lower(N'%" + util.replaceAEIOU(khachhang) + "%') )";	
    		
    		query = query + " and d.timkiem like '%" + util.replaceAEIOU(khachhang) + "%' ";	
    	}
    	if (mafast.length() > 0)
    	{
    		query = query + " and d.maFAST like '%" + mafast + "%'";	
    	}
    	
    	if(obj.getTtId().length()>0)
    	{
    		query+= " and a.khachhang_fk in (select pk_Seq from khachhang where tinhthanh_Fk='"+obj.getTtId()+"')";
    	}
    	
    	if(obj.getQhId().length()>0)
    	{
    		query+= " and a.khachhang_fk in (select pk_Seq from khachhang where quanhuyen_fk='"+obj.getQhId()+"')";
    	}
    	
    	if(tructhuocId.trim().length() > 0)
    		query += " and a.npp_fk = '" + tructhuocId + "' ";
    	
    	System.out.println("Query cua ban: " + query);
    	return query;
	}

	private void ToExcel(HttpServletResponse response, IDuyetdonhangList obj,String query) throws IOException
	{
		OutputStream out = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=DonHang.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			int k = 0;
			int j = 1;

			WritableSheet sheet = null;

			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 15);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);
			
			WritableCellFormat celltieude = new WritableCellFormat(cellTitle);
			celltieude.setAlignment(Alignment.CENTRE);
			
			WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 13);
			cellFont.setColour(Colour.BLACK);
			
			NumberFormat dp3 = new NumberFormat("#,###,###,##");
			WritableCellFormat inFormat = new WritableCellFormat(dp3);
			inFormat.setFont(cellFont);
		
			inFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			WritableFont cellFontWhite = new WritableFont(WritableFont.TIMES, 13);
			cellFontWhite.setColour(Colour.WHITE);
			
			WritableCellFormat cellFormat = new WritableCellFormat(cellFontWhite);

			cellFormat.setBackground(jxl.format.Colour.GRAY_80);
			cellFormat.setWrap(true);
			cellFormat.setAlignment(Alignment.CENTRE);
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.WHITE);

			
			WritableCellFormat cformat3 = new WritableCellFormat(cellFont);
			cformat3.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cformat3.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cformat3.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cformat3.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			sheet = w.createSheet("Đơn hàng", k);// ten sheet
					

			sheet.addCell(new Label(0, 0, "Ngày", cellFormat));
			sheet.addCell(new Label(1, 0, "Mã Khách Hàng", cellFormat));
			sheet.addCell(new Label(2, 0, "Mã Hàng", cellFormat));
			sheet.addCell(new Label(3, 0, "Số Lượng", cellFormat));
			
		
		
			inFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			sheet.setRowView(100, 4);

			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 20);
			dbutils db = new dbutils();

			ResultSet rs = db.get(query);

			Label label;
			Number number;
			
			while (rs.next())
			{
				label = new Label(0, j,getFormatDate( rs.getString("NgayNhap")), cformat3);sheet.addCell(label);
				label = new Label(1, j, rs.getString("MaFast"), cformat3);sheet.addCell(label);
				label = new Label(2, j, rs.getString("spMa"), cformat3);sheet.addCell(label);
				number = new Number(3, j, rs.getDouble("SoLuong"), inFormat);sheet.addCell(number);
				j++;
			}
			w.write();
			w.close();
			rs.close();
			db.shutDown();
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			if (out != null)
				out.close();

		}
		
	}
	
	public String getFormatDate(String date) 
	{
		String[] arr = date.split("-");
		
		return arr[2] + "/" + arr[1] + "/" + arr[0];
	}
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
}

