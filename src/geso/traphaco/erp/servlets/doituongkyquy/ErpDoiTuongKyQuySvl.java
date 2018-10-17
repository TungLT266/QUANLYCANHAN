package geso.traphaco.erp.servlets.doituongkyquy;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.doituongkyquy.IErpDoiTuongKyQuy;
import geso.traphaco.erp.beans.doituongkyquy.imp.ErpDoiTuongKyQuy;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@WebServlet("/ErpDoiTuongKyQuySvl")
public class ErpDoiTuongKyQuySvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	Utility util = new Utility();
	IErpDoiTuongKyQuy lspList;
	
	public ErpDoiTuongKyQuySvl( )
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("Do get ErpDoiTuongKyQuySvl");
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
		lspList = new ErpDoiTuongKyQuy();
		
		String chixem = request.getParameter("chixem");
		if(chixem==null){chixem="";}
		lspList.setChixem(chixem);
		
		if (action.equals("delete"))
		{
			IErpDoiTuongKyQuy lspBean = new ErpDoiTuongKyQuy(id);
			lspBean.Delete();
			msg = lspBean.getMsg();
		}
		this.lspList.setCtyId(ctyId);
		
		this.lspList.setMsg(msg);
		this.lspList.Search( session.getAttribute("congtyId").toString() );
		this.lspList.CreateRs();
		session.setAttribute("lspList", lspList);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiTuongKyQuy.jsp";
		response.sendRedirect(nextJSP);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("Do post ErpDoiTuongKyQuySvl");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		// this.lspList = (IErpDoiTuongKyQuy) session.getAttribute("lspList");
		this.lspList = new ErpDoiTuongKyQuy();
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
		System.out.println("cont ty " + ctyId);
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
			IErpDoiTuongKyQuy lspBean = new ErpDoiTuongKyQuy();
			lspBean.setCtyId(ctyId);
			lspBean.CreateRs();
			session.setAttribute("lspBean", lspBean);
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiTuongKyQuyNew.jsp";
		}
		else
		{
			System.out.println("Search------- ");
			lspList.Search(session.getAttribute("congtyId").toString());
			lspList.CreateRs();
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiTuongKyQuy.jsp";
		}
		session.setAttribute("lspList", lspList);
		response.sendRedirect(nextJSP);
	}
}
