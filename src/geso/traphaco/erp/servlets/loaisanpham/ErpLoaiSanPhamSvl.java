package geso.traphaco.erp.servlets.loaisanpham;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.loaisanpham.IErpLoaiSanPham;
import geso.traphaco.erp.beans.loaisanpham.imp.ErpLoaiSanPham;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/ErpLoaiSanPhamSvl")
public class ErpLoaiSanPhamSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	Utility util = new Utility();
	IErpLoaiSanPham lspList;
	
	public ErpLoaiSanPhamSvl( )
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("Do get ErpLoaiSanPhamSvl");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		Utility util = new Utility();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		if (userId.length() == 0) userId = util.antiSQLInspection(request.getParameter("userId"));
		String action = util.getAction(querystring);
		String id = util.getId(querystring);
		String ctyId = (String)session.getAttribute("congtyId");
		
		String msg = "";
		lspList = new ErpLoaiSanPham();
		
		String chixem = request.getParameter("chixem");
		lspList.setChixem(chixem);
		
		if (action.equals("delete"))
		{
			IErpLoaiSanPham lspBean = new ErpLoaiSanPham(id);
			lspBean.Delete();
			msg = lspBean.getMsg();
		}
		this.lspList.setCtyId(ctyId);
		
		this.lspList.setMsg(msg);
		this.lspList.Search();
		this.lspList.CreateRs();
		session.setAttribute("lspList", lspList);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaiSanPham.jsp";
		response.sendRedirect(nextJSP);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("Do post ErpLoaiSanPhamSvl");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		// this.lspList = (IErpLoaiSanPham) session.getAttribute("lspList");
		this.lspList = new ErpLoaiSanPham();
		if (lspList == null)
		{
			System.out.println("Null mie roi");
		}
		String nextJSP = "";
		String action = request.getParameter("action");
		String ma = request.getParameter("ma");
		String ten = request.getParameter("ten");
		String taikhoan = request.getParameter("taikhoan");
		String ctyId = (String)session.getAttribute("congtyId");
		
		if (action == null) action = "";
		if (taikhoan == null) taikhoan = "";
		System.out.println("action " + action);
		System.out.println("taikhoan " + taikhoan == null ? "null" : "khong null");
		
		String chixem = request.getParameter("chixem");
		lspList.setChixem(chixem);
		
		lspList.setCtyId(ctyId);
		lspList.setTaiKhoan(taikhoan);
		lspList.setMa(ma);
		lspList.setTen(ten);
		if (action.equals("New"))
		{
			System.out.println("Vao trang tao moi");
			IErpLoaiSanPham lspBean = new ErpLoaiSanPham();
			lspBean.setCtyId(ctyId);
			lspBean.CreateRs();
			session.setAttribute("lspBean", lspBean);
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaiSanPhamNew.jsp";
		}
		else
		{
			System.out.println("Search------- ");
			lspList.Search();
			lspList.CreateRs();
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpLoaiSanPham.jsp";
		}
		session.setAttribute("lspList", lspList);
		response.sendRedirect(nextJSP);
	}
}
