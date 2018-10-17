package geso.traphaco.distributor.servlets.donhang;

import geso.traphaco.distributor.beans.donhang.*;
import geso.traphaco.distributor.beans.donhang.imp.*;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DonhangUpdateSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	private static final long serialVersionUID = 1L;

	PrintWriter out;

	public DonhangUpdateSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum)) {
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else {
			session.setMaxInactiveInterval(30000);

			this.out = response.getWriter();
			Utility util = new Utility();

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);
			String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
			String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
			
			IDonhangList dhList = new DonhangList();

			String trangthai = request.getParameter("trangthai");
			if (trangthai == null)
				trangthai = "";
			dhList.setTrangthai(trangthai);
					
			String tungay = request.getParameter("tungay");
			if (tungay == null)
				tungay = "";
			dhList.setTungay(tungay);

			String denngay = request.getParameter("denngay");
			if (denngay == null)
				denngay = "";
			dhList.setDenngay(denngay);

			String sohoadon = request.getParameter("sohoadon");
			if (sohoadon == null)
				sohoadon = "";
			dhList.setSohoadon(sohoadon);

			String khachhang = request.getParameter("khachhang");
			if (khachhang == null)
				khachhang = "";
			dhList.setKhachhang(khachhang);

			String mafast = request.getParameter("mafast");
			if (mafast == null)
				mafast = "";
			dhList.setMafast(mafast);

			String tdvId = request.getParameter("tdvId");
			if (tdvId == null)
				tdvId = "";
			dhList.setDdkdId(tdvId);

			String nvgnId = request.getParameter("nvgnId");
			if (nvgnId == null)
				nvgnId = "";
			dhList.setnvgnId(nvgnId);
			
			session.setAttribute("dhList", dhList);

			out.println(userId);

			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));

			String copy = request.getParameter("copy");
			if (copy == null)
				copy = "";

			String id = util.getId(querystring);
			String msg = "";
			/*if (copy.trim().length() > 0)
			{
				msg = copyDONHANG(copy, userId);
				System.out.println("KET QUA COPY DON HANG: " + id);

				if (msg.trim().length() > 10) 
				{
					id = "-1"; // Copy đơn hàng không thành công
					msg = "Có lõi trong quá trình copy đơn hàng. Vui lòng thử copy lại, hoặc bấm nút BACK để tạo mới đơn hàng";
				} 
				else 
				{
					id = msg;
					msg = "";
				}
			} 
			else 
			{
				id = util.getId(querystring);
			}*/
						
			IDonhang dhBean = new Donhang(id);
			dhBean.setUserId(userId); // phai co UserId truoc khi Init	
			
			dhBean.setNpp_duocchon_id(npp_duocchon_id);
			dhBean.setTdv_dangnhap_id(tdv_dangnhap_id);
			
			dhBean.setLoainhanvien(session.getAttribute("loainhanvien"));
	    	dhBean.setDoituongId(session.getAttribute("doituongId"));
	    	
			dhBean.init();
			dhBean.setMessage(msg);

			String capduyet = request.getParameter("capduyet");
		    if(capduyet == null)
		    	capduyet = "";
		    dhBean.setCapduyet(capduyet);
			
			dhBean.setKhId(dhBean.getKhId());
			session.setAttribute("bgstId", dhBean.getBgstId());
			session.setAttribute("khoId", dhBean.getKhoId());

			String nextJSP;

			if (request.getQueryString().indexOf("display") >= 0) {
				// dhBean.CreateSpDisplay();
				nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangDisplay.jsp";
			} else if (request.getQueryString().indexOf("duyet") >= 0) {
				nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangDisplayDuyet.jsp";
			}
			else
				nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangUpdate.jsp";

			session.setAttribute("dhBean", dhBean);
			session.setAttribute("donhangKhac", dhBean.getDonhangKhac());
			session.setAttribute("khId", dhBean.getKhId());
			session.setAttribute("nvgnId", dhBean.getnvgnId());
			session.setAttribute("ddkdId", dhBean.getDdkdId());
			session.setAttribute("ngaydonhang", dhBean.getNgaygiaodich());

			// bo sung them de do phai truy xuat csdl khi nhan sanpham
			session.setAttribute("nppId", dhBean.getTructhuocId() );

			response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		
		String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
		String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();

		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		
		if (!cutil.check(userId, userTen, sum)) {
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			session.setMaxInactiveInterval(30000);

			this.out = response.getWriter();
			//dbutils db = new dbutils();
			IDonhang dhBean;
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			if (id == null) {
				dhBean = new Donhang("");
			} else {
				dhBean = new Donhang(id);
			}

			dhBean.setNpp_duocchon_id(npp_duocchon_id);
			dhBean.setTdv_dangnhap_id(tdv_dangnhap_id);
			
			dhBean.setLoainhanvien(session.getAttribute("loainhanvien"));
	    	dhBean.setDoituongId(session.getAttribute("doituongId"));
			
			String[][] action_msg =	dhBean.initDATA( request, session, util );
			
			//XỬ LÝ ACTION
			String action = action_msg[0][0];		
			if (action.equals("submitKh"))
			{
				if (id == null) 
				{
					String nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangNew.jsp";
					response.sendRedirect(nextJSP);
				} 
				else 
				{
					String nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangUpdate.jsp";
					response.sendRedirect(nextJSP);
				}	
			} 
			else if (action.equals("save")) 
			{
				if (id == null) 
				{
					if (action_msg[0][1].equals("0")) //Tạo mới không thành công
					{
						String nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangNew.jsp";
						response.sendRedirect(nextJSP);
					} 
					else 
					{
						String nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangNew.jsp";
						response.sendRedirect(nextJSP);
					}
				} 
				else 
				{
					if(action_msg[0][1].equals("1"))  //Cập nhật không thành công
					{
						String nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangUpdate.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{
						String nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangNew.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			} 
			else if(action.equals("duyetdonhang"))
			{
				if(action_msg[0][1].equals("3") || action_msg[0][1].equals("4") )  //Chốt không thành công
				{
					String nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangDisplayDuyet.jsp";
					response.sendRedirect(nextJSP);
				}
				else //Duyệt thành công, ra lại trang tổng đon hàng
				{
					IDonhangList obj = new DonhangList();
					obj.setNpp_duocchon_id(npp_duocchon_id);
					obj.setTdv_dangnhap_id(tdv_dangnhap_id);
					obj.setUserId(userId);
					String search = getSearchQuery(request, obj);
					obj.setCapduyet(dhBean.getCapduyet());
					obj.init(search, "1");
					session.setAttribute("obj", obj);
					session.setAttribute("ddkdId", "");

					String nextJSP = "/TraphacoHYERP/pages/Distributor/DuyetDonHang.jsp";
					response.sendRedirect(nextJSP);
				}
			}
			else if (action.equals("taomoi"))
			{
				// Empty Bean for distributor
		    	dhBean = (IDonhang) new Donhang("");
		    	dhBean.setUserId(userId);
		    	
		    	dhBean.setNpp_duocchon_id(npp_duocchon_id);
		    	dhBean.setTdv_dangnhap_id(tdv_dangnhap_id);
		    	
		    	dhBean.createRS();
		    	
		    	// Save Data into session
		    	session.setAttribute("dhBean", dhBean);//truyen vao session mot doi tuong donhang co gia tri rong khi khoi tao de ben form don hang nhan dc
		    	session.setAttribute("khId", "");
		    	session.setAttribute("ddkdId", "");
		    	session.setAttribute("ngaydonhang", "" );
		    	session.setAttribute("nppId", "" );
	    		
	    		String nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangNew.jsp";
	    		response.sendRedirect(nextJSP);
			} 
			else if (action.equals("chotdonhang")) 
			{
				if(action_msg[0][1].equals("3") || action_msg[0][1].equals("4") )  //Chốt không thành công
				{
					String nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else //Chốt thành công, ra lại trang tổng đon hàng
				{
					IDonhangList obj = new DonhangList();
					obj.setNpp_duocchon_id(npp_duocchon_id);
					obj.setTdv_dangnhap_id(tdv_dangnhap_id);
					obj.setUserId(userId);
					String search = getSearchQuery(request, obj);
					obj.init(search, "0");
					session.setAttribute("obj", obj);
					session.setAttribute("ddkdId", "");

					String nextJSP = "/TraphacoHYERP/pages/Distributor/DonHang.jsp";
					response.sendRedirect(nextJSP);
				}
			} 
			else if (action.equals("apkhuyenmai")) 
			{
				if(action_msg[0][1].equals("6.1"))  //Qua trang tạo mới
				{
					String nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else if(action_msg[0][1].equals("6.2")) //Qua trang cập nhật
				{
					String nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else if(action_msg[0][1].equals("6.3")) //Qua trang chọn khuyến mại
				{
					String nextJSP = "/TraphacoHYERP/pages/Distributor/KhuyenMai.jsp";
					response.sendRedirect(nextJSP);
				}
				else if(action_msg[0][1].equals("6.4"))  //Qua trang chọn khuyến mại ĐXK
				{
					String nextJSP = "/TraphacoHYERP/pages/Distributor/KhuyenMaiDxk.jsp";
					response.sendRedirect(nextJSP);
				}
			} 
			else  
			{
				String nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangNew.jsp";
				if (id != null) 
					nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangUpdate.jsp";
				
				response.sendRedirect(nextJSP);
			}
		}
	}

	private String getSearchQuery(HttpServletRequest request,IDonhangList obj) 
	{
    	String nppId = request.getParameter("nppId");
    	if ( nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
    	/*String ddkdId = request.getParameter("ddkdTen");
    	if ( ddkdId == null)
    		ddkdId = "";
    	obj.setDdkdId(ddkdId);
    	
    	String trangthai = request.getParameter("trangthai");
    	if (trangthai == null)
    		trangthai = "";    	
    	obj.setTrangthai(trangthai);*/
    	
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
    	
    	String query = "select a.pk_seq as dhId, a.ngaynhap, a.trangthai, a.ngaytao, a.ngaysua, d.maFAST, isnull(a.DAXUATHOADON,0) as DAXUATHOADON , isnull(DAIN, '0') as DAINDH,     " +
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
				") AS SoHoaDon, d.CAPDOGIAOHANG, CS_DUYET, SS_DUYET  "+
				" from donhang a   " +
				"		inner  join nhanvien c on a.nguoisua = c.pk_seq inner join khachhang d on a.khachhang_fk = d.pk_seq        " +
				"		left join daidienkinhdoanh e on a.ddkd_fk = e.pk_seq  " +
				"where a.npp_fk = '" + nppId + "'  ";//and a.kho_fk in "+util.quyen_kho(this.userId) ;	
    	
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
    		query = query + " and (d.smartid like '%"+ util.replaceAEIOU(khachhang) +"%' or d.pk_seq like (N'%" + util.replaceAEIOU(khachhang) + "%') or [dbo].[fuConvertToUnsign1](lower(d.ten)) like lower(N'%" + util.replaceAEIOU(khachhang) + "%') )";	
    		//System.out.println("1/ bỏ dấu: " + util.replaceAEIOU(khachhang));
    	}
    	if (mafast.length() > 0)
    	{
    		query = query + " and d.maFAST like '%" + mafast + "%'";	
    	}
    	/*System.out.println("\nQuery cua ban: " + query);*/
    	return query;
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	/******************** CHUYEN XUONG BEAN CAC HAM XU LY KHUYEN MAI *************/

}
