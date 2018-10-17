package geso.traphaco.center.servlets.banggiabanlechuan;

import geso.traphaco.center.beans.banggiablc.*;
import geso.traphaco.center.beans.banggiablc.imp.*;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BanggiabanlechuanSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
{
	static final long serialVersionUID = 1L;

	public BanggiabanlechuanSvl()
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
		String userId = util.getUserId(querystring);
		out.println(userId);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		String action = util.getAction(querystring);
		out.println(action);

		String bgId = util.getId(querystring);

		if (action.equals("delete"))
		{
			Delete(bgId);
			out.print(bgId);
		}

		IBanggiablcList obj;

		obj = new BanggiablcList();
		obj.setUserId(userId);
		session.setAttribute("obj", obj);

		String nextJSP = "/TraphacoHYERP/pages/Center/BangGiaBanLeChuan.jsp";
		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		IBanggiablcList obj;
		obj = new BanggiablcList();
		HttpSession session = request.getSession();
		Utility util = new Utility();
		String userId = util.antiSQLInspection(request.getParameter("userId"));

		String action = request.getParameter("action");
		if (action == null)
		{
			action = "";
		}
		out.println(action);

		if (action.equals("new"))
		{

			IBanggiablc bgBean = (IBanggiablc) new Banggiablc();
			bgBean.setUserId(userId);

			session.setAttribute("bgblcBean", bgBean);

			String nextJSP = "/TraphacoHYERP/pages/Center/BangGiaBanLeChuanNew.jsp";
			response.sendRedirect(nextJSP);

		}
		if (action.equals("search"))
		{
			String search = getSearchQuery(request, obj);

			obj.init(search);

			obj.setUserId(userId);

			session.setAttribute("obj", obj);

			response.sendRedirect("/TraphacoHYERP/pages/Center/BangGiaBanLeChuan.jsp");

		}
	}

	private String getSearchQuery(HttpServletRequest request, IBanggiablcList obj)
	{
		// PrintWriter out = response.getWriter();

		// IBanggiablcList obj = new BanggiablcList();

		Utility util = new Utility();

		String bgTen = util.antiSQLInspection(request.getParameter("bgTen"));
		if (bgTen == null)
			bgTen = "";
		obj.setTen(bgTen);

		String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
		if (dvkdId == null)
			dvkdId = "";
		obj.setDvkdId(dvkdId);

		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null)
			trangthai = "";

		if (trangthai.equals("2"))
			trangthai = "";

		obj.setTrangthai(trangthai);

		String query = "select a.pk_seq as id, a.ten as ten, a.trangthai as trangthai, c.ten as nguoitao, a.ngaytao as ngaytao, d.ten as nguoisua, a.ngaysua as ngaysua, b.donvikinhdoanh as dvkd, b.pk_seq as dvkdId from banggiabanlechuan a, donvikinhdoanh b, nhanvien c, nhanvien d where a.dvkd_fk=b.pk_seq and a.nguoitao = c.pk_seq and a.nguoisua = d.pk_seq ";

		if (bgTen.length() > 0)
		{
			query = query + " and upper(dbo.ftBoDau(a.ten)) like upper(N'%" + util.replaceAEIOU(bgTen) + "%')";
		}

		if (dvkdId.length() > 0)
		{
			query = query + " and b.pk_seq = '" + dvkdId + "'";

		}

		if (trangthai.length() > 0)
		{
			query = query + " and a.trangthai = '" + trangthai + "'";

		}
		query = query + "  order by ten";
		return query;
	}

	private void Delete(String id)
	{
		dbutils db = new dbutils();
		db.update("delete from banggiablc_sanpham where bgblc_fk='" + id + "'");
		db.update("delete from banggiabanlechuan where pk_seq = '" + id + "'");
		db.shutDown();

	}

}
