package geso.traphaco.erp.servlets.doituongkhac;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.doituongkhac.IErpDoiTuongKhac;
import geso.traphaco.erp.beans.doituongkhac.imp.ErpDoiTuongKhac;
import geso.traphaco.erp.beans.doituongkhac.imp.ErpDoiTuongKhacList;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpDoiTuongKhacUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErpDoiTuongKhacUpdateSvl()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		String ctyId = (String)session.getAttribute("congtyId");
		
		Utility util = new Utility();
		String querystring = request.getQueryString();
		
		String action = util.getAction(querystring);
		session.setAttribute("action", action);
		
		Utility cutil = new Utility();
		
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} 
		else
		{
			session.setMaxInactiveInterval(30000);
			String nextJSP = "";
			
			userId = cutil.getUserId(querystring);
			
			if (userId.length() == 0)
				userId = cutil.antiSQLInspection(request.getParameter("userId"));
			
			String id = cutil.getId(querystring);
			System.out.println("ID la" + id);
			
			IErpDoiTuongKhac obj = new ErpDoiTuongKhac(id);
			obj.setCongTyId(ctyId);
			
			obj.setUserId(userId);
			
			if (request.getQueryString().indexOf("display") >= 0)
			{
				obj.init();
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiTuongKhacUpdate.jsp";
			} 
			else if (request.getQueryString().indexOf("update") >= 0)
			{
				obj.init();
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiTuongKhacUpdate.jsp";
			}
			session.setAttribute("obj", obj);
			response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		
		Utility util = (Utility) session.getAttribute("util");
		String ctyId = (String)session.getAttribute("congtyId");
		
		System.out.println("congtyId:"+ctyId);
		String action = request.getParameter("action");
		session.setAttribute("action", action);
		
		if (action == null)
			action = "";
		if (!util.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} 
		else
		{
			String nextJSP = "";
			ErpDoiTuongKhac obj = new ErpDoiTuongKhac();
			getParameters(obj, request, session, util);
			
			obj.setCongTyId(ctyId);
			obj.setUserId(userId);
			
			session.setAttribute("userId", userId);
			session.setAttribute("userTen", userTen);
			
			System.out.println("action is: " + action);
			if (action.equals("save"))
			{
				if (obj.getId() == null || obj.getId().trim().length() == 0)
				{
					if (obj.create())
					{
						obj.DBClose();
			    		ErpDoiTuongKhacList ob = new ErpDoiTuongKhacList();
	//		    		ob.setCongTyId(ctyId);
			    		ob.init();
			    		
			    		session.setAttribute("userId", userId);
				    	session.setAttribute("obj", ob);
				    	
				    	nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiTuongKhac.jsp";
			    		response.sendRedirect(nextJSP);
			    		return;
					} else
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiTuongKhacUpdate.jsp";
					}
				}else
				{
					if (!obj.edit())
					{
						session.setAttribute("obj", obj);
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiTuongKhacUpdate.jsp";
					} else
					{
						obj.DBClose();
			    		ErpDoiTuongKhacList ob = new ErpDoiTuongKhacList();
//			    		ob.setCongTyId(ctyId);
			    		ob.init();
			    		
			    		session.setAttribute("userId", userId);
				    	session.setAttribute("obj", ob);
				    	
				    	nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiTuongKhac.jsp";
			    		response.sendRedirect(nextJSP);
			    		return;
					}
				}
			} 
			else
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiTuongKhacUpdate.jsp";
			}
			
			obj.init();
			session.setAttribute("obj", obj);
			response.sendRedirect(nextJSP);
		}
	}

	private void getParameters(ErpDoiTuongKhac obj, HttpServletRequest request, HttpSession session, Utility util) 
	{
		String id = util.antiSQLInspection(request.getParameter("id"));	
		String maDoiTuong = util.antiSQLInspection(request.getParameter("maDoiTuong"));
		String tenDoiTuong = util.antiSQLInspection(request.getParameter("tenDoiTuong"));
		String nppId = util.antiSQLInspection(request.getParameter("nppId"));
		String diaChi = util.antiSQLInspection(request.getParameter("diaChi"));
		String maSoThue = util.antiSQLInspection(request.getParameter("maSoThue"));
		String soTaiKhoan = util.antiSQLInspection(request.getParameter("soTaiKhoan"));
		String tenNganHang = util.antiSQLInspection(request.getParameter("tenNganHang"));
		String nganHangId = util.antiSQLInspection(request.getParameter("nganHangId"));
		String tenChiNhanh = util.antiSQLInspection(request.getParameter("tenChiNhanh"));
		String chiNhanhId = util.antiSQLInspection(request.getParameter("chiNhanhId"));
		String dienThoai = util.antiSQLInspection(request.getParameter("dienThoai"));
		String tenNguoiLienHe = util.antiSQLInspection(request.getParameter("tenNguoiLienHe"));
		String emailNguoiLienHe = util.antiSQLInspection(request.getParameter("emailNguoiLienHe"));
		String dtNguoiLienHe = util.antiSQLInspection(request.getParameter("dtNguoiLienHe"));
		String trangThai = util.antiSQLInspection(request.getParameter("trangThai"));
		System.out.println("trangThai: " + trangThai);
		String quanLyCongNo = util.antiSQLInspection(request.getParameter("quanLyCongNo"));
		String fax = util.antiSQLInspection(request.getParameter("fax"));
		
		obj.setId(id);
		
		if (diaChi != null)
			obj.setDiaChi(diaChi);
		
		if (dienThoai != null)
			obj.setDienThoai(dienThoai);
		
		if (dtNguoiLienHe != null)
			obj.setDtNguoiLienHe(dtNguoiLienHe);
		
		if (emailNguoiLienHe != null)
			obj.setEmailNguoiLienHe(emailNguoiLienHe);
		
		if (maSoThue != null)
			obj.setMaSoThue(maSoThue);
		
		if (fax != null)
			obj.setFax(fax);

		if (maDoiTuong != null)
			obj.setMaDoiTuong(maDoiTuong);

		if (tenDoiTuong != null)
			obj.setTenDoiTuong(tenDoiTuong);

		if (nppId != null)
			obj.setNppId(nppId);
		
		if (tenNguoiLienHe != null)
			obj.setTenNguoiLienHe(tenNguoiLienHe);

		if (tenNganHang != null && tenNganHang.trim().length() > 0)
			obj.setTenNganHang(tenNganHang);

		if (nganHangId != null && nganHangId.trim().length() > 0)
			obj.setNganHangId(nganHangId);
		
		if (tenChiNhanh != null && tenChiNhanh.trim().length() > 0)
			obj.setTenChiNhanh(tenChiNhanh);
		
		if (chiNhanhId != null && chiNhanhId.trim().length() > 0)
			obj.setChiNhanhId(chiNhanhId);
		
		if (soTaiKhoan != null && soTaiKhoan.trim().length() > 0)
			obj.setSoTaiKhoan(soTaiKhoan);
		
		if (trangThai != null)
			obj.setTrangThai(trangThai);
		
		if(quanLyCongNo != null)
			obj.setQuanLyCongNo("1");
		else
			obj.setQuanLyCongNo("0");		
	}
}