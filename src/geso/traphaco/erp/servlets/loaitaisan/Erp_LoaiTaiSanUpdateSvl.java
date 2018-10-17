package geso.traphaco.erp.servlets.loaitaisan;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.loaitaisan.IErp_loaitaisan;
import geso.traphaco.erp.beans.loaitaisan.IErp_loaitaisanList;
import geso.traphaco.erp.beans.loaitaisan.imp.Erp_loaitaisan;
import geso.traphaco.erp.beans.loaitaisan.imp.Erp_loaitaisanList;

public class Erp_LoaiTaiSanUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public Erp_LoaiTaiSanUpdateSvl()
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

		String Id = util.getId(querystring);

		String userId = util.getUserId(querystring);
		System.out.println("user update : " + userId);
		System.out.println("Action : " + action);

		if (action.equals("update"))
		{
			IErp_loaitaisan tsBean = new Erp_loaitaisan(Id);
			tsBean.init();
			tsBean.createRs();
			tsBean.setUserid(userId);
			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_LoaiTaiSanUpdate.jsp";
			session.setAttribute("ltsBean", tsBean);
			response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else
		{
			IErp_loaitaisan ltsBean = new Erp_loaitaisan();
			String nextJSP = "";
			String id = cutil.antiSQLInspection(request.getParameter("id"));
			String ma = cutil.antiSQLInspection(request.getParameter("Ma"));
			String ten = cutil.antiSQLInspection(request.getParameter("Ten"));
			String tk = cutil.antiSQLInspection(request.getParameter("tk"));
			String tkkh = cutil.antiSQLInspection(request.getParameter("tkkh"));
			String trangthai = request.getParameter("hoatdong");
			if (trangthai == null)
				trangthai = "0";
			else
				trangthai = "1";
			ltsBean.setTrangthai(trangthai);

			String ngaytao = getDateTime();
			String nguoitao = request.getParameter("userId");
			String ngaysua = getDateTime();
			String nguoisua = request.getParameter("userId");

			if (id != null)
				ltsBean.setId(id);
			if (ma != null)
				ltsBean.setMa(ma);
			if (ten != null)
				ltsBean.setTen(ten);
			if (tk != null)
				ltsBean.setTkId(tk);

			if (tkkh != null)
				ltsBean.setTkkhId(tkkh);

			ltsBean.setNguoitao(nguoitao);
			ltsBean.setNgaytao(ngaytao);

			ltsBean.setNguoisua(nguoisua);
			ltsBean.setNgaysua(ngaysua);

			session.setAttribute("userId", userId);
			session.setAttribute("userTen", userTen);

			String action = request.getParameter("action");

			if (action.equals("save"))
			{
				if (id == null)
				{
					if (!ltsBean.ThemMoiMa())
					{
						nextJSP = "pages/Erp/Erp_LoaiTaiSanNew.jsp";
					} else
					{
						IErp_loaitaisanList ltsList = new Erp_loaitaisanList();
						ltsList.setUserid(userId);
						ltsList.init();
						ltsBean.DBClose();
						session.setAttribute("ltsList", ltsList);
						nextJSP = "pages/Erp/Erp_LoaiTaiSan.jsp";
					}
				} else if (id != null)
				{
					if (!ltsBean.UpdateMa())
					{
						nextJSP = "pages/Erp/Erp_LoaiTaiSanUpdate.jsp";
					}

					else
					{
						IErp_loaitaisanList ltsList = new Erp_loaitaisanList();
						ltsList.setUserid(userId);
						ltsList.init();
						ltsBean.DBClose();
						session.setAttribute("ltsList", ltsList);
						nextJSP = "pages/Erp/Erp_LoaiTaiSan.jsp";
					}
				}
				ltsBean.createRs();
				session.setAttribute("ltsBean", ltsBean);
				response.sendRedirect(nextJSP);
			}
		}
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

}
