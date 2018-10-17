package geso.traphaco.erp.servlets.nganhangcongty;

import geso.traphaco.erp.beans.nganhangcongty.IErpNganHangCongTy;
import geso.traphaco.erp.beans.nganhangcongty.IErpNganHangCongTyList;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.nganhangcongty.imp.ErpNganHangCongTyList;
import geso.traphaco.erp.beans.nganhangcongty.imp.ErpNganHangCongTy;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ErpNganHangCongTySvl")
public class ErpNganHangCongTySvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	Utility util = new Utility();
	 
	public ErpNganHangCongTySvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("Do get ErpNganHangCongTySvl");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		Utility util = new Utility();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		String nextJSP = "";
		String action = util.getAction(querystring);
		String id = util.getId(querystring);
		String ctyId = (String)session.getAttribute("congtyId");
		IErpNganHangCongTyList nhct = new ErpNganHangCongTyList();
		nhct.setCtyId(ctyId);
		
		String chixem = request.getParameter("chixem");
		nhct.setChixem(chixem);
		
		if (action.equals("delete"))
		{
			IErpNganHangCongTy nhctBean = new ErpNganHangCongTy(id);
			nhctBean.Delete();
			System.out.println("Ngung hoat dong cong ty này");
		}
		nhct.init( ctyId );
		session.setAttribute("nhct", nhct);
		nextJSP = "/TraphacoHYERP/pages/Erp/ErpNganHangCongTy.jsp";
		response.sendRedirect(nextJSP);
	}

	@SuppressWarnings("unused")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("Do post ErpNganHangCongTySvl");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String ctyId = (String)session.getAttribute("congtyId");
		
		String nextJSP = "";
		String congty = request.getParameter("congty");
		String taikhoan = request.getParameter("taikhoan");
		String chinhanh = request.getParameter("chinhanh");
		String nganhang = request.getParameter("nganhang");
		String chutaikhoan = request.getParameter("chutaikhoan");
		String sotaikhoan = request.getParameter("sotaikhoan");
		String loaitien = request.getParameter("loaitien");
		String trangthai = request.getParameter("trangthai");
		String action = request.getParameter("action");
		
		IErpNganHangCongTyList nhct = new ErpNganHangCongTyList();
		nhct.setCtyId(ctyId);
		
		String chixem = request.getParameter("chixem");
		nhct.setChixem(chixem);
		
		if (action == null)
			action = "";

		nhct.setCongTy(congty);
		if (nganhang != null && nganhang.trim().length() > 0)
			nhct.setNganHang(nganhang);
		if (chinhanh != null && chinhanh.trim().length() > 0)
			nhct.setChiNhanh(chinhanh);
		if (loaitien != null && loaitien.trim().length() > 0)
			nhct.setLoaiTien(loaitien);

		if (action == null)
		{
			action = "";
		}
		if (action.equals("New"))
		{
			System.out.println("Vao trang tao moi");
			IErpNganHangCongTy nhctBean = new ErpNganHangCongTy();
			nhctBean.setCTyId(ctyId);
			nhctBean.createaRs();
			session.setAttribute("nhctBean", nhctBean);
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpNganHangCongTyNew.jsp";
		} else
		{
			System.out.println("Search ");
			nhct.init( ctyId );
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpNganHangCongTy.jsp";
		}
		session.setAttribute("nhct", nhct);
		response.sendRedirect(nextJSP);
	}
}
