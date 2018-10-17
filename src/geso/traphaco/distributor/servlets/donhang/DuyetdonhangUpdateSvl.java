package geso.traphaco.distributor.servlets.donhang;

import geso.traphaco.distributor.beans.donhang.*;
import geso.traphaco.distributor.beans.donhang.imp.*;
import geso.traphaco.distributor.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DuyetdonhangUpdateSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
	
	private static final long serialVersionUID = 1L;

	PrintWriter out;

	public DuyetdonhangUpdateSvl() {
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
			String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
			String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();

			IDonhangList dhList = new DonhangList();

			dhList.setNpp_duocchon_id(npp_duocchon_id);
			dhList.setTdv_dangnhap_id(tdv_dangnhap_id);
		    
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
			
			IDuyetdonhang dhBean = new Duyetdonhang(id);
			dhBean.setUserId(userId); // phai co UserId truoc khi Init			
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
				nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangDisplayDaiLy.jsp";
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
			session.setAttribute("nppId", dhBean.getNppId());

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
		
		if (!cutil.check(userId, userTen, sum)) {
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			session.setMaxInactiveInterval(30000);
			String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
			String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();

			this.out = response.getWriter();
			//dbutils db = new dbutils();
			IDuyetdonhang dhBean;
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			if (id == null) {
				dhBean = new Duyetdonhang("");
			} else {
				dhBean = new Duyetdonhang(id);
			}
			
			dhBean.setNpp_duocchon_id(npp_duocchon_id);
			dhBean.setTdv_dangnhap_id(tdv_dangnhap_id);
			
			System.out.println("dhId = "+id);
			
			String[][] action_msg =	dhBean.initDATA( request, session, util );
			
			//XỬ LÝ ACTION
			String action = action_msg[0][0];		
			if(action.equals("duyetdonhang"))
			{
				if(action_msg[0][1].equals("3") || action_msg[0][1].equals("4") ) 
				{
					String nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangDisplayDuyet.jsp";
					response.sendRedirect(nextJSP);
				}
				else //Duyệt thành công, ra lại trang tổng đon hàng
				{
					IDuyetdonhangList obj = new DuyetdonhangList();
					obj.setNpp_duocchon_id(npp_duocchon_id);
					obj.setTdv_dangnhap_id(tdv_dangnhap_id);
					obj.setUserId(userId);
					obj.setCapduyet( request.getParameter("capduyet") );
					
					obj.setLoainhanvien(session.getAttribute("loainhanvien"));
				    obj.setDoituongId(session.getAttribute("doituongId"));
				    
					obj.init("", "1");
					session.setAttribute("obj", obj);
					session.setAttribute("ddkdId", "");

					String nextJSP = "/TraphacoHYERP/pages/Distributor/DuyetDonHang.jsp";
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

	
}
