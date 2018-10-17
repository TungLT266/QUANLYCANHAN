package geso.traphaco.erp.servlets.nganhang;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.nganhang.IErpNganHang;
import geso.traphaco.erp.beans.nganhang.IErpNganHangList;
import geso.traphaco.erp.beans.nganhang.imp.*;

public class ErpNganHangUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErpNganHangUpdateSvl()
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

		String querystring = request.getQueryString();
		String action = util.getAction(querystring);
		out.println(action);

		String nhId = util.getId(querystring);

		String userId = util.getUserId(querystring);

		if (action.equals("update"))
		{
			IErpNganHang nhBean = new ErpNganHang(nhId);
			nhBean.init();
			nhBean.setUserid(userId);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNganHangUpdate.jsp";
			session.setAttribute("nhBean", nhBean);
			response.sendRedirect(nextJSP);
		}
		else if (action.equals("display"))
		{
			IErpNganHang nhBean = new ErpNganHang(nhId);
			nhBean.init();
			nhBean.setUserid(userId);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNganHangDisplay.jsp";
			session.setAttribute("nhBean", nhBean);
			response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String userId = request.getParameter("userId");
		String userTen = request.getParameter("userTen");
		String sum = (String) session.getAttribute("sum");
		Utility cutil = (Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else
		{
			IErpNganHang nhBean = new ErpNganHang();

			String nextJSP = "";

			String id = cutil.antiSQLInspection(request.getParameter("id"));
			String ma = cutil.antiSQLInspection(request.getParameter("Ma"));
			String diachi = cutil.antiSQLInspection(request.getParameter("diachi"));
			String ten = cutil.antiSQLInspection(request.getParameter("Ten"));
			String masothue = cutil.antiSQLInspection(request.getParameter("masothue"));
			String trangthai = cutil.antiSQLInspection(request.getParameter("hoatdong"));
			if (trangthai == null)
				trangthai = "0";
			else
				trangthai = "1";
			nhBean.setTrangthai(trangthai);

			String ngaytao = getDateTime();
			String nguoitao = request.getParameter("userId");
			String ngaysua = getDateTime();
			String nguoisua = request.getParameter("userId");

			if (id != null)
				nhBean.setID(id);
			if (ma != null)
				nhBean.setMA(ma);
			if (ten != null)
				nhBean.setTEN(ten);
			if (masothue != null)
				nhBean.setMasothue(masothue);
			if (diachi == null )
				diachi = "";
			nhBean.setDiachi(diachi);

			nhBean.setNGUOITAO(nguoitao);
			nhBean.setNGAYTAO(ngaytao);

			nhBean.setNGUOISUA(nguoisua);
			nhBean.setNGAYSUA(ngaysua);

			session.setAttribute("userId", userId);
			session.setAttribute("userTen", userTen);

			String action = request.getParameter("action");

			if (action.equals("save"))
			{
				if (id == null)
				{
					if (!nhBean.themMoiMa())
					{
						nextJSP = "pages/Erp/ErpNganHangNew.jsp";
					}

					else
					{
						IErpNganHangList nhList = new ErpNganHangList();
						nhList.setUserid(userId);
						nhList.init();
						session.setAttribute("nhList", nhList);
						nextJSP = "pages/Erp/ErpNganHang.jsp";
					}
				} else if (id != null)
				{
					if (!nhBean.UpdateMa())
					{
						nextJSP = "pages/Erp/ErpNganHangUpdate.jsp";
					}

					else
					{
						IErpNganHangList nhList = new ErpNganHangList();
						nhList.setUserid(userId);
						nhList.init();
						session.setAttribute("nhList", nhList);
						nextJSP = "pages/Erp/ErpNganHang.jsp";
					}
				}
			}
			session.setAttribute("nhBean", nhBean);
			response.sendRedirect(nextJSP);

		}
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

}
