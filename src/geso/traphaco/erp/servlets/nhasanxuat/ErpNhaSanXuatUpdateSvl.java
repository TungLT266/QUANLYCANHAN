package geso.traphaco.erp.servlets.nhasanxuat;

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
import geso.traphaco.erp.beans.nhasanxuat.IErpNhaSanXuat;
import geso.traphaco.erp.beans.nhasanxuat.IErpNhaSanXuatList;
import geso.traphaco.erp.beans.nhasanxuat.imp.ErpNhaSanXuat;
import geso.traphaco.erp.beans.nhasanxuat.imp.ErpNhaSanXuatList;

public class ErpNhaSanXuatUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

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

		String nsxId = util.getId(querystring);

		String userId = util.getUserId(querystring);

		if (action.equals("update"))
		{
			IErpNhaSanXuat nsxBean = new ErpNhaSanXuat(nsxId);
			nsxBean.init();
			nsxBean.setUserid(userId);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhaSanXuatUpdate.jsp";
			session.setAttribute("nsxBean", nsxBean);
			response.sendRedirect(nextJSP);
		}
		else if (action.equals("display"))
		{
			IErpNhaSanXuat nsxBean = new ErpNhaSanXuat(nsxId);
			nsxBean.init();
			nsxBean.setUserid(userId);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhaSanXuatDisplay.jsp";
			session.setAttribute("nsxBean", nsxBean);
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
			IErpNhaSanXuat nsxBean = new ErpNhaSanXuat();

			String nextJSP = "";

			String id = cutil.antiSQLInspection(request.getParameter("id"));
			String ma = cutil.antiSQLInspection(request.getParameter("Ma"));
			String ten = cutil.antiSQLInspection(request.getParameter("Ten"));
			String trangthai = cutil.antiSQLInspection(request.getParameter("hoatdong"));
			if (trangthai == null)
				trangthai = "0";
			else
				trangthai = "1";
			nsxBean.setTRANGTHAI(trangthai);

			String ngaytao = getDateTime();
			String nguoitao = request.getParameter("userId");
			String ngaysua = getDateTime();
			String nguoisua = request.getParameter("userId");

			if (id != null)
				nsxBean.setID(id);
			if (ma != null)
				nsxBean.setMA(ma);
			if (ten != null)
				nsxBean.setTEN(ten);

			nsxBean.setNGUOITAO(nguoitao);
			nsxBean.setNGAYTAO(ngaytao);

			nsxBean.setNGUOISUA(nguoisua);
			nsxBean.setNGAYSUA(ngaysua);

			session.setAttribute("userId", userId);
			session.setAttribute("userTen", userTen);

			String action = request.getParameter("action");

			if (action.equals("save"))
			{
				if (id == null)
				{
					if (!nsxBean.update())
					{
						nextJSP = "pages/Erp/ErpNhaSanXuatNew.jsp";
					}

					else
					{
						IErpNhaSanXuatList nsxList = new ErpNhaSanXuatList();
						nsxList.setUserid(userId);
						nsxList.init();
						session.setAttribute("nsxList", nsxList);
						nextJSP = "pages/Erp/ErpNhaSanXuat.jsp";
					}
				} else if (id != null)
				{
					if (!nsxBean.update())
					{
						nextJSP = "pages/Erp/ErpNhaSanXuatUpdate.jsp";
					}

					else
					{
						IErpNhaSanXuatList nsxList = new ErpNhaSanXuatList();
						nsxList.setUserid(userId);
						nsxList.init();
						session.setAttribute("nsxList", nsxList);
						nextJSP = "pages/Erp/ErpNhaSanXuat.jsp";
					}
				}
			}
			session.setAttribute("nsxBean", nsxBean);
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
