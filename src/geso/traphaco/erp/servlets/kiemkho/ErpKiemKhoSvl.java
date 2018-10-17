package geso.traphaco.erp.servlets.kiemkho;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.kiemkho.IErpKiemKho;
import geso.traphaco.erp.beans.kiemkho.IErpKiemKhoList;
import geso.traphaco.erp.beans.kiemkho.imp.ErpKiemKho;
import geso.traphaco.erp.beans.kiemkho.imp.ErpKiemKhoList;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpKiemKhoSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	HttpServletRequest request;

	public ErpKiemKhoSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String querystring = request.getQueryString();
		Utility util = new Utility();
		String userId = util.getUserId(querystring);
		if (userId.length() == 0)
			userId = request.getParameter("userId");

		String id = util.getId(querystring);
		 
		String thongbao = "";
	 
		// xoas
		if (request.getQueryString().indexOf("delete") >= 0)
		{
			IErpKiemKhoList bean = new ErpKiemKhoList();
			bean.setUsedId(userId);
			bean.Delete(id);
		}// Chot
		else if (request.getQueryString().indexOf("approve") >= 0)
		{

			IErpKiemKho kiemkho = new ErpKiemKho(id);
			kiemkho.setNguoiSua(userId);
			kiemkho.setNguoiTao(userId);
			kiemkho.Approve();
			thongbao = kiemkho.getMessage();
		}
		IErpKiemKhoList obj = new ErpKiemKhoList();
		obj.setUsedId(userId);
		
		obj.init("");
		obj.setMSG(thongbao);
		session.setAttribute("obj", obj);

		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemKho.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Utility util = new Utility();
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemKho.jsp";
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		IErpKiemKhoList bean = new ErpKiemKhoList();
		
		String usedId = util.antiSQLInspection(request.getParameter("userId"));
		bean.setUsedId(usedId);
		
		if (action == null)
		{
			action = "";
		}
		System.out.println("Post + action " + action);
		if (action.equals("AddNew"))
		{
			IErpKiemKho obj = new ErpKiemKho();
			
			obj.setNguoiSua(usedId);
			obj.setNguoiTao(usedId);
			obj.CreateRsKho();
			obj.initThangNam();
			bean.DbClose();
			session.setAttribute("bean", obj);
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemKhoNew.jsp";
			response.sendRedirect(nextJSP);
		} else if (action.equals("Search"))
		{
			String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
			bean.setTrangThai(trangthai);

			String khott_fk = util.antiSQLInspection(request.getParameter("khott_fk"));
			bean.setKhoTT_FK(khott_fk);

			String tuthang = request.getParameter("tuthang").length() < 2 ? ("0" + request.getParameter("tuthang")) : request.getParameter("tuthang");

			String toithang = request.getParameter("denthang").length() < 2 ? ("0" + request.getParameter("denthang")) : request.getParameter("denthang");

			bean.setFromMonth(tuthang);

			bean.setToMonth(toithang);
			bean.setToYear(request.getParameter("dennam"));
			bean.setFromYear(request.getParameter("tunam"));

			bean.init("");
			session.setAttribute("obj", bean);
			response.sendRedirect(nextJSP);
		} else if (action.equals("view") || action.equals("next") || action.equals("prev"))
		{

			bean.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));

			bean.init("");
			bean.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
			session.setAttribute("obj", bean);
			response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpKiemKho.jsp");

		}

	}

}
