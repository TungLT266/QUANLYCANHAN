package geso.traphaco.erp.servlets.loaitaisan;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.loaitaisan.IErp_loaiCCDC;
import geso.traphaco.erp.beans.loaitaisan.IErp_loaiCCDCList;
import geso.traphaco.erp.beans.loaitaisan.imp.Erp_loaiCCDC;
import geso.traphaco.erp.beans.loaitaisan.imp.Erp_loaiCCDCList;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Erp_LoaiCCDCSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public Erp_LoaiCCDCSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		HttpSession session = request.getSession();

		Utility util = new Utility();
		out = response.getWriter();

		IErp_loaiCCDCList lccdcList = new Erp_loaiCCDCList();
		lccdcList.setCtyId((String)session.getAttribute("congtyId"));
		
		String userId;
		String querystring = request.getQueryString();
		userId = util.getUserId(querystring);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		out.println("userId la :" + userId);

		String action = util.getAction(querystring);
		if (action == null)
			action = "";

		String id = util.getId(querystring);
		if (id == null)
			id = "";

		if (action.equals("delete"))
		{
			if (id != null)
				lccdcList.setId(id);
			lccdcList.Delete();
		}
		lccdcList.setUserid(userId);
		lccdcList.setCtyId((String) session.getAttribute("congtyId"));
		lccdcList.init();
		session.setAttribute("lccdcList", lccdcList);
		response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_LoaiCCDC.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		IErp_loaiCCDCList lccdcList;
		String userId;
		Utility util = new Utility();

		lccdcList = new Erp_loaiCCDCList();
		HttpSession session = request.getSession();
		lccdcList.setCtyId((String)session.getAttribute("congtyId"));
		
		userId = util.antiSQLInspection(request.getParameter("userId"));
		String ma = util.antiSQLInspection(request.getParameter("mats"));
		if (ma == null)
			ma = "";
		lccdcList.setMa(ma);

		String ten = util.antiSQLInspection(request.getParameter("diengiai"));
		if (ten == null)
			ten = "";
		lccdcList.setTen(ten);

		String taikhoan = util.antiSQLInspection(request.getParameter("tk"));
		if (taikhoan == null)
			taikhoan = "";
		lccdcList.setTkId(taikhoan);

		String action = request.getParameter("action");
		if (action == null)
		{
			action = "";
		}

		out.println(action);

		if (action.equals("new"))
		{
			IErp_loaiCCDC lccdcBean = new Erp_loaiCCDC();
			lccdcBean.setCtyId((String)session.getAttribute("congtyId"));
			lccdcBean.createRs();
			session.setAttribute("lccdcBean", lccdcBean);
			String nextJSP = "pages/Erp/Erp_LoaiCCDCNew.jsp";
			response.sendRedirect(nextJSP);
		} else
		{

			lccdcList.setUserid(userId);
			lccdcList.init();
			session.setAttribute("lccdcList", lccdcList);
			String nextJSP = "pages/Erp/Erp_LoaiCCDC.jsp";
			response.sendRedirect(nextJSP);
		}
	}

}
