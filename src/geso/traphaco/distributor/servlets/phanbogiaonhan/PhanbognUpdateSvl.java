package geso.traphaco.distributor.servlets.phanbogiaonhan;

import geso.traphaco.distributor.beans.phanbogiaonhan.IPhanbogiaonhan;
import geso.traphaco.distributor.beans.phanbogiaonhan.IPhanbogiaonhanList;
import geso.traphaco.distributor.beans.phanbogiaonhan.imp.Phanbogiaonhan;
import geso.traphaco.distributor.beans.phanbogiaonhan.imp.PhanbogiaonhanList;
import geso.traphaco.distributor.util.Utility;

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

public class PhanbognUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public PhanbognUpdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} 
		else
		{
			IPhanbogiaonhan nvgnBean;
			PrintWriter out;

			out = response.getWriter();
			Utility util = new Utility();

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);

			out.println(userId);

			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));

			String id = util.getId(querystring);

			nvgnBean = new Phanbogiaonhan(id);
			nvgnBean.setCongtyId((String)session.getAttribute("congtyId"));
			nvgnBean.setUserId(userId);
			
			nvgnBean.setLoainhanvien(session.getAttribute("loainhanvien"));
	    	nvgnBean.setDoituongId(session.getAttribute("doituongId"));
	    	
			nvgnBean.init();

			session.setAttribute("nvgnBean", nvgnBean);
			String nextJSP = "/TraphacoHYERP/pages/Distributor/PhanBoGiaoNhanUpdate.jsp";
			response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

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

			IPhanbogiaonhan nvgnBean;
			PrintWriter out;

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			out = response.getWriter();
			Utility util = new Utility();

			String id = util.antiSQLInspection(request.getParameter("id"));
			if (id == null)
			{
				id = "";
				nvgnBean = new Phanbogiaonhan("");
			} else
			{
				nvgnBean = new Phanbogiaonhan(id);
			}

			userId = util.antiSQLInspection(request.getParameter("userId"));
			nvgnBean.setCongtyId((String)session.getAttribute("congtyId"));
			nvgnBean.setUserId(userId);
			
			nvgnBean.setLoainhanvien(session.getAttribute("loainhanvien"));
	    	nvgnBean.setDoituongId(session.getAttribute("doituongId"));

			String nvgnId = util.antiSQLInspection(request.getParameter("nvgnId"));
			if (nvgnId == null)
				nvgnId = "";
			nvgnBean.setNvgnId(nvgnId);

			String nvgn_newId = util.antiSQLInspection(request.getParameter("nvgn_newId"));
			if (nvgn_newId == null)
				nvgn_newId = "";
			nvgnBean.setNvgn_newId(nvgn_newId);
			
			String nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if (nppId == null)
				nppId = "";
			nvgnBean.setNppId(nppId);

			String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
			if (trangthai == null || trangthai.equals("2"))
				trangthai = "";
			nvgnBean.setTrangthai(trangthai);

			String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
			if (diengiai == null)
				diengiai = "";
			nvgnBean.setDiengiai(diengiai);

			String ngay = util.antiSQLInspection(request.getParameter("ngay"));
			if (ngay == null)
				ngay = "";
			nvgnBean.setNgay(ngay);
			
			String denngay = util.antiSQLInspection(request.getParameter("denngay"));
			if (denngay == null)
				denngay = "";
			nvgnBean.setDenngay(denngay);
			
			String tinhthanhId = util.antiSQLInspection(request.getParameter("tinhthanhId"));
			if (tinhthanhId == null)
				tinhthanhId = "";
			nvgnBean.setTinhthanhId(tinhthanhId);
			
			String quanhuyenId = util.antiSQLInspection(request.getParameter("quanhuyenId"));
			if (quanhuyenId == null)
				quanhuyenId = "";
			nvgnBean.setQuanhuyenId(quanhuyenId);
			
			String khId = util.antiSQLInspection(Doisangchuoi(request.getParameterValues("khId")));
			if (khId == null)
				khId = "";
			nvgnBean.setKhId(khId);
			
			String action = request.getParameter("action");

				if (action.equals("save"))
				{
					if (id.length() == 0)
					{
						if (!(nvgnBean.CreatePbgn()))
						{
							nvgnBean.createRS();
							session.setAttribute("nvgnBean", nvgnBean);
							String nextJSP = "/TraphacoHYERP/pages/Distributor/PhanBoGiaoNhanNew.jsp";
							response.sendRedirect(nextJSP);
						} 
						else
						{
							IPhanbogiaonhanList obj = new PhanbogiaonhanList();
							obj.setUserId(userId);
							obj.init("");
							session.setAttribute("obj", obj);

							String nextJSP = "/TraphacoHYERP/pages/Distributor/PhanBoGiaoNhan.jsp";
							response.sendRedirect(nextJSP);
						}
					} 
					else
					{
						if (!(nvgnBean.UpdatePbgn()))
						{
							nvgnBean.init();
							session.setAttribute("nvgnBean", nvgnBean);
							String nextJSP = "/TraphacoHYERP/pages/Distributor/PhanBoGiaoNhanUpdate.jsp";
							response.sendRedirect(nextJSP);
						} else
						{
							IPhanbogiaonhanList obj = new PhanbogiaonhanList();
							obj.setUserId(userId);
							obj.init("");
							session.setAttribute("obj", obj);

							String nextJSP = "/TraphacoHYERP/pages/Distributor/PhanBoGiaoNhan.jsp";
							response.sendRedirect(nextJSP);
						}
					}
				} 
				else
				{
					if (id.length() > 0)
					{
						nvgnBean.init();
					} 
					else
					{
						nvgnBean.createRS();
					}

					session.setAttribute("nvgnBean", nvgnBean);

					String nextJSP;
					if (id.length() == 0)
					{
						nextJSP = "/TraphacoHYERP/pages/Distributor/PhanBoGiaoNhanNew.jsp";
					} else
					{
						nextJSP = "/TraphacoHYERP/pages/Distributor/PhanBoGiaoNhanUpdate.jsp";
					}
					response.sendRedirect(nextJSP);
				}
		}

	}

	private String Doisangchuoi(String[] checkkv)
	{
		// TODO Auto-generated method stub
		String str = "";
		if (checkkv != null)
		{
			for (int i = 0; i < checkkv.length; i++)
			{
				if (i == 0)
				{
					str = checkkv[i];
				} else
				{
					str = str + "," + checkkv[i];
				}
			}
		}
		return str;
	}
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}

}
