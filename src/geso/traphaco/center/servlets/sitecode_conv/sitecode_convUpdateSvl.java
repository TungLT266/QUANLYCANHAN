package geso.traphaco.center.servlets.sitecode_conv;

import geso.traphaco.center.beans.sitecode_conv.Isitecode_conv;
import geso.traphaco.center.beans.sitecode_conv.imp.sitcode_conv;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class sitecode_convUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public sitecode_convUpdateSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		Isitecode_conv obj;
		Utility util = new Utility();
		HttpSession session = request.getSession();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		System.out.println(userId);
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		String sitecode = util.getId(querystring);
		String action = util.getAction(querystring);
		if (action.equals("update"))
		{
			obj = new sitcode_conv(sitecode);
			obj.setRsKhuvuc();
			obj.setUserid(userId);
			obj.setRsNppTienNhiem();
			session.setAttribute("obj", obj);
			String nextJSP = "/TraphacoHYERP/pages/Center/sitecode_convupdate.jsp";
			response.sendRedirect(nextJSP);
		} else if (action.equals("chot"))
		{
			obj = new sitcode_conv(sitecode);
			obj.setUserid(userId);
			obj.setngaysua(getDateTime());

			if (!obj.chot())
			{
				obj.setMsg("Khong The Chot,Vui Long Thu Lai,Neu Khong Duoc Vui Long Lien He Voi Admin,De Duoc Giup Do ");
			} else
			{
				obj.setMsg("");
			}
			obj.Init("");
			obj.settrangthai("");
			obj.setten("");
			session.setAttribute("obj", obj);
			String nextJSP = "/TraphacoHYERP/pages/Center/sitecode_conv.jsp";
			response.sendRedirect(nextJSP);
		} else if (action.equals("display"))
		{
			obj = new sitcode_conv(sitecode);
			obj.setRsKhuvuc();
			obj.setUserid(userId);
			obj.setRsNppTienNhiem();
			session.setAttribute("obj", obj);
			String nextJSP = "/TraphacoHYERP/pages/Center/sitecode_convdisplay.jsp";
			response.sendRedirect(nextJSP);
		}
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		Isitecode_conv obj;
		obj = new sitcode_conv();
		Utility util = new Utility();
		String sitecode = util.antiSQLInspection(request.getParameter("sitecode"));
		obj.setsitecode(sitecode);

		obj = new sitcode_conv(sitecode);
		String userId = util.antiSQLInspection(request.getParameter("userId"));

		obj.setUserid(userId);
		obj.setngaytao(this.getDateTime());

		obj.setnguoitao(userId);
		String npptn = util.antiSQLInspection(request.getParameter("npptn"));

		String ngayks = util.antiSQLInspection(request.getParameter("ngayks"));
		obj.NgayKhoaSo(ngayks);

		obj.setIdNppTienNhiem(npptn);

		String tennpp = util.antiSQLInspection(request.getParameter("TenNCC_long"));
		obj.setten(tennpp);

		String khuvucid = util.antiSQLInspection(request.getParameter("khuvucid"));

		if (khuvucid == null)
		{
			khuvucid = "";
		}
		obj.setkhuvucId(khuvucid);
		System.out.println("khuvuc :" + khuvucid);
		String action = request.getParameter("action");
		String nextJSP = "/TraphacoHYERP/pages/Center/sitecode_conv.jsp";

		if (action.equals("save"))
		{
			// truong hop save chi thuc hien luu them convsitecode vao bang
			// sitecode_conv
			System.out.println("[SiteCode]");
			if (!obj.save())
			{
				obj.setRsKhuvuc();
				obj.setRsNppTienNhiem();
				nextJSP = "/TraphacoHYERP/pages/Center/sitecode_convupdate.jsp";
			} else
			{
				obj = new sitcode_conv();
				obj.Init("");
				nextJSP = "/TraphacoHYERP/pages/Center/sitecode_conv.jsp";
			}
		} else if (action.equals("chot"))
		{
			if (!obj.chot())
			{
				obj.setRsKhuvuc();
				obj.setRsNppTienNhiem();
				nextJSP = "/TraphacoHYERP/pages/Center/sitecode_convupdate.jsp";
			} else
			{
				obj = new sitcode_conv();
				obj.Init("");
				nextJSP = "/TraphacoHYERP/pages/Center/sitecode_conv.jsp";
			}

		} else if (action.equals("submit"))
		{
			obj.setRsKhuvuc();
			obj.setRsNppTienNhiem();
			nextJSP = "/TraphacoHYERP/pages/Center/sitecode_convupdate.jsp";

		} else if (action.equals("nppmoi"))
		{
			if (!obj.TaoNPPMoi())
			{
				obj.setRsKhuvuc();
				obj.setRsNppTienNhiem();
				nextJSP = "/TraphacoHYERP/pages/Center/sitecode_convupdate.jsp";
			} else
			{
				obj = new sitcode_conv();
				obj.Init("");
				nextJSP = "/TraphacoHYERP/pages/Center/sitecode_conv.jsp";
			}
		}
		session.setAttribute("obj", obj);
		response.sendRedirect(nextJSP);
	}

}
