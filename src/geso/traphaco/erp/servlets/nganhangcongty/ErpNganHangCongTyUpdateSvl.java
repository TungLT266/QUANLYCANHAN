package geso.traphaco.erp.servlets.nganhangcongty;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.nganhangcongty.IErpNganHangCongTy;
import geso.traphaco.erp.beans.nganhangcongty.IErpNganHangCongTyList;
import geso.traphaco.erp.beans.nganhangcongty.imp.ErpNganHangCongTy;
import geso.traphaco.erp.beans.nganhangcongty.imp.ErpNganHangCongTyList;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/ErpNganHangCongTyUpdateSvl") public class ErpNganHangCongTyUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Utility util;
	IErpNganHangCongTy nhctBean;
	public ErpNganHangCongTyUpdateSvl()
	{
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		System.out.println("Do get! ErpNganHangCongTyUpdateSvl");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		String ctyId = (String)session.getAttribute("congtyId");
		
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum)) {
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else {
			session.setMaxInactiveInterval(30000);
			String nextJSP = "";
			util = new Utility();
			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);
			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));
			String id = util.getId(querystring);
			System.out.println("ID la" + id);
			
			nhctBean = new ErpNganHangCongTy(id);
			nhctBean.setCTyId(ctyId);
			
			System.out.println("ID : " + nhctBean.getId());
			nhctBean.setUserId(userId);
			
			if (request.getQueryString().indexOf("display") >= 0) {
				nhctBean.init();
				nhctBean.createaRs();
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpNganHangCongTyDisplay.jsp";
			}
			else
				if (request.getQueryString().indexOf("update") >= 0) {
					nhctBean.init();
					nhctBean.createaRs();
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpNganHangCongTyUpdate.jsp";
				}
			System.out.println("Ban dang o trang" + nextJSP);
			session.setAttribute("nhctBean", nhctBean);
			response.sendRedirect(nextJSP);
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		System.out.println("Do Post ErpNganHangCongTyUpdateSvl!");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		String ctyId = (String)session.getAttribute("congtyId");

		util = (Utility) session.getAttribute("util");
		if (!util.check(userId, userTen, sum)) {
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else {
			String nextJSP = "";
			IErpNganHangCongTy nhctBean = (IErpNganHangCongTy) new ErpNganHangCongTy();
			
			String action =request.getParameter("action");
			String id = util.antiSQLInspection(request.getParameter("id"));
			String taikhoan = util.antiSQLInspection(request.getParameter("taikhoan"));
			String chinhanh = util.antiSQLInspection(request.getParameter("chinhanh"));
			String nganhang = util.antiSQLInspection(request.getParameter("nganhang"));
			String chutaikhoan = util.antiSQLInspection(request.getParameter("chutaikhoan"));
			String sotaikhoan = util.antiSQLInspection(request.getParameter("sotaikhoan"));
			String loaitien = util.antiSQLInspection(request.getParameter("loaitien"));
			String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
			String ctyId_gd = util.antiSQLInspection(request.getParameter("ctyId"));
			String masothue = util.antiSQLInspection(request.getParameter("masothue"));
			if(masothue==null){
				masothue="";
			}
			
			String nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if(nppId != null)
			{
				nhctBean.setNppId(nppId);
			}
			if (action == null)
				action = "";
			if (nganhang == null || nganhang.trim().length() <= 0)
				nganhang = "NULL";
			if (taikhoan == null || taikhoan.trim().length() <= 0)
				taikhoan = "NULL";
			if (chinhanh == null || chinhanh.trim().length() <= 0)
				chinhanh = "NULL";
			if (chutaikhoan == null)
				chutaikhoan = "NULL";
			if (sotaikhoan == null)
				sotaikhoan = "NULL";
			if (loaitien == null || loaitien.length() == 0)
				loaitien = "NULL";
			if (trangthai == null)
				trangthai = "0";
			else
				trangthai = "1";
			
			if(ctyId_gd == null)
				ctyId_gd = "";
			
			System.out.println("ctyId_gd:"+ctyId_gd);
			nhctBean.setCongTy(ctyId_gd);
			nhctBean.setCTyId(ctyId);
			nhctBean.setId(id);
			nhctBean.setChiNhanh(chinhanh);
			nhctBean.setNganHang(nganhang);
			nhctBean.setTaiKhoanKeToan(taikhoan);
			nhctBean.setLoaiTien(loaitien);
			nhctBean.setChuTaiKhoan(chutaikhoan);
			nhctBean.setSoTaiKhoan(sotaikhoan);
			nhctBean.setTrangThai(trangthai);
			nhctBean.setUserId(userId);
			session.setAttribute("userId", userId);
			session.setAttribute("userTen", userTen);
			System.out.println("LoaiTien" + loaitien + "Leng" + loaitien.length());
			
			if (action.equals("Create")) {
				System.out.println("Tao moi");
				if (nhctBean.Create()) {
					System.out.println("Tao duoc");
					IErpNganHangCongTyList nhct = new ErpNganHangCongTyList();
					nhct.setCtyId(ctyId);
					nhct.init(ctyId);
					session.setAttribute("nhct", nhct);
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpNganHangCongTy.jsp";
				}
				else {
					System.out.println("Khong tao  duoc");
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpNganHangCongTyNew.jsp";
				}
			}
			else
				if (action.equals("Update")) {
					System.out.println("Update ErpNganHangCongTyUpdateSvl ");
					if (!nhctBean.Update()) {
						System.out.println("Khong Update  duoc");
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNganHangCongTyUpdate.jsp";
					}
					else {
						System.out.println("Update duoc");
						IErpNganHangCongTyList nhct = new ErpNganHangCongTyList();
						nhct.setCtyId(ctyId);
						nhct.init(ctyId);
						session.setAttribute("nhct", nhct);
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNganHangCongTy.jsp";
					}
				}
				else
					if (id == null)
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNganHangCongTyNew.jsp";
					else
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpNganHangCongTyUpdate.jsp";
			nhctBean.createaRs();
			System.out.print("nextJSP " + nextJSP);
			session.setAttribute("nhctBean", nhctBean);
			response.sendRedirect(nextJSP);
		}
	}
}
