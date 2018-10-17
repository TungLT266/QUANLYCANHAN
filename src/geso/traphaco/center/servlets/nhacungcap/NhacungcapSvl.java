package geso.traphaco.center.servlets.nhacungcap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.*;
import java.sql.ResultSet;

import geso.traphaco.center.db.sql.*;
import geso.traphaco.center.beans.nhacungcap.imp.Nhacungcap;
import geso.traphaco.center.beans.nhacungcap.imp.NhacungcapList;
import geso.traphaco.center.beans.nhacungcap.INhacungcap;
import geso.traphaco.center.beans.nhacungcap.INhacungcapList;
import geso.traphaco.distributor.util.Utility;

public class NhacungcapSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
{
	static final long serialVersionUID = 1L;

	public NhacungcapSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		dbutils db;

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String command = "";

		String querystring = request.getQueryString();

		Utility util = new Utility();
		String userId = util.getUserId(querystring);
		if (userId == null)
			userId = "";

		String action = util.getAction(querystring);
		if (action == null)
			action = "";

		String id = util.getId(querystring);
		if (id == null)
			id = "";
		INhacungcapList obj = new NhacungcapList();
		if (action.equals("delete"))
		{
			db = new dbutils();
			command = "select count (pk_seq) as num from nhacungcap_dvkd where ncc_fk='" + id + "' and checked ='1' ";
			System.out.println(command);
			ResultSet rs = db.get(command);
			if (rs != null)
				try
				{
					if (rs.next())
						if (rs.getString("num").equals("0"))
						{
							command = "delete from nhacungcap_dvkd where ncc_fk ='" + id + "'";
							db.update(command);
							command = "delete from nhacungcap where pk_seq ='" + id + "'";
							db.update(command);
						} else
							obj.setMsg("nha cung cap da ton tai trong giam sat ban hang roi, khong the xoa duoc");
					if (rs != null)
						rs.close();
					if (db != null)
						db.shutDown();
				} catch (Exception e)
				{

					e.printStackTrace();
				}
		}

		if (action.equals("update"))
		{

			INhacungcap ncc = new Nhacungcap();
			ncc.setId(id);
			ncc.init();
			HttpSession session = request.getSession();

			session.setAttribute("ncc", ncc);
			session.setAttribute("userId", userId);
			String nextJSP = "/TraphacoHYERP/pages/Center/NhaCungCapUpdate.jsp";
			response.sendRedirect(nextJSP);
		} else
		{
			obj.init();
			HttpSession session = request.getSession();
			// Data object is saved into session
			session.setAttribute("obj", obj);
			// userId is saved into session
			session.setAttribute("userId", userId);
			String nextJSP = "/TraphacoHYERP/pages/Center/NhaCungCap.jsp";
			response.sendRedirect(nextJSP);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		Utility util = new Utility();
		HttpSession session = request.getSession();
		String userId = util.antiSQLInspection(request.getParameter("userId"));

		// Perform searching. Each distributor is saved into Nhacungcap
		if (request.getParameter("action").equals("search"))
		{
			INhacungcapList obj = new NhacungcapList();
			String tenncc = util.antiSQLInspection(request.getParameter("ncc"));
			String tenviettat = util.antiSQLInspection(request.getParameter("tenviettat"));
			String tungay = util.antiSQLInspection(request.getParameter("tungay"));
			String denngay = util.antiSQLInspection(request.getParameter("denngay"));
			String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));

			String query = "";

			query = "select a.pk_seq, a.ten, a.diachi, a.masothue, a.tenviettat, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from nhacungcap a, nhanvien b, nhanvien c where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq ";

			if (tenncc.length() > 0)
			{
				query = query + " and upper(dbo.ftBoDau(a.ten)) like upper(N'%" + util.replaceAEIOU(tenncc) + "%')";
				obj.setNhacungcap(tenncc);
			}

			if (tenviettat.length() > 0)
			{
				query = query + " and upper(a.tenviettat) like upper(N'%" + util.replaceAEIOU(tenviettat) + "%')";
				obj.setTenviettat(tenviettat);
			}

			out.println(query);
			if (tungay.length() > 0)
			{
				query = query + " and a.ngaytao >= '" + tungay + "'";
				obj.setTungay(tungay);
			}

			if (denngay.length() > 0)
			{
				query = query + " and a.ngaytao <= '" + denngay + "'";
				obj.setDenngay(tungay);
			}

			if (!trangthai.trim().equals("2"))
			{
				query = query + " and a.trangthai ='" + trangthai + "'";
				obj.setTrangthai(trangthai);
			}

			// System.out.print(query);
			obj.setQuery(query);
			obj.init();

			// Data object is saved into session
			session.setAttribute("obj", obj);

			// userId is saved into session
			session.setAttribute("userId", userId);

			String nextJSP = "/TraphacoHYERP/pages/Center/NhaCungCap.jsp";
			response.sendRedirect(nextJSP);
		}
		// Create a new distributor
		if (request.getParameter("action").equals("new"))
		{

			// Empty Bean for distributor
			Nhacungcap nccBean = new Nhacungcap();

			// Save Distributor into session
			session.setAttribute("nccBean", nccBean);
			session.setAttribute("userId", userId);
			String nextJSP = "/TraphacoHYERP/pages/Center/NhaCungCapNew.jsp";
			response.sendRedirect(nextJSP);

		}

	}
}