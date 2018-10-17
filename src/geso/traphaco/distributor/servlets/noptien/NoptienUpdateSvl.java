package geso.traphaco.distributor.servlets.noptien;

import geso.traphaco.distributor.beans.noptien.*;
import geso.traphaco.distributor.beans.noptien.imp.*;

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

public class NoptienUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public NoptienUpdateSvl()
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
		} else
		{
			INoptien khBean;
			PrintWriter out;

			out = response.getWriter();
			Utility util = new Utility();

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);
			
			String action = util.getAction(querystring);
			
			out.println(userId);

			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));

			String id = util.getId(querystring);

			khBean = new Noptien(id);
			khBean.setUserId(userId);
			khBean.init();
			session.setAttribute("khBean", khBean);
			
			String nextJSP ="";
		   if (action.equals("update"))
		   {
			   nextJSP = "/TraphacoHYERP/pages/Distributor/NopTienUpdate.jsp";
		   }
		   else if(action.equals("display"))
		   {
			   nextJSP = "/TraphacoHYERP/pages/Distributor/NopTienDisplay.jsp"; 
		   }
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

			INoptien khBean;
			PrintWriter out;

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			out = response.getWriter();
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			if (id == null)
			{
				khBean = new Noptien("");
			} else
			{
				khBean = new Noptien(id);
			}

			userId = util.antiSQLInspection(request.getParameter("userId"));
			khBean.setUserId(userId);

			String ngaynop = util.antiSQLInspection(request.getParameter("ngaynop"));
			if (ngaynop == null)
				ngaynop = "";
			khBean.setNgaynop(ngaynop);
			
			String doituongId = util.antiSQLInspection(request.getParameter("doituongId"));
			if (doituongId == null)
				doituongId = "";
			khBean.setDoituongId(doituongId);
			
			String khId = util.antiSQLInspection(request.getParameter("khId"));
			if (khId == null)
				khId = "";
			khBean.setKhId(khId);
		
			
			String nvbhId = util.antiSQLInspection(request.getParameter("nvbhId"));
			if (nvbhId == null)
				nvbhId = "";
			khBean.setNvbhId(nvbhId);
			
			String nvgnId = util.antiSQLInspection(request.getParameter("nvgnId"));
			if (nvgnId == null)
				nvgnId = "";
			khBean.setNvgnId(nvgnId);
			
			String dtId = util.antiSQLInspection(request.getParameter("dtId"));
			if (dtId == null)
				dtId = "";
			khBean.setDtId(dtId);

			String vekhoan = util.antiSQLInspection(request.getParameter("vekhoan"));
			if (vekhoan == null)
				vekhoan = "";
			khBean.setVekhoan(vekhoan);
			
			String soin = util.antiSQLInspection(request.getParameter("soin"));
			if (soin == null)
				soin = "";
			khBean.setSoin(soin);
			
			String soCTgoc = util.antiSQLInspection(request.getParameter("soCTgoc"));
			if (soCTgoc == null)
				soCTgoc = "";
			khBean.setSoCTgoc(soCTgoc);
			
			
			String sotien = util.antiSQLInspection(request.getParameter("sotien"));
			if (sotien == null)
				sotien = "0";
			khBean.setSotien(Double.parseDouble(sotien.replaceAll(",", "")));
			

			String ngaysua = getDateTime();
			khBean.setNgaysua(ngaysua);

			boolean error = false;
		
		

			String action = request.getParameter("action");
			if (!error)
			{
				if (action.equals("save"))
				{
					if (id == null)
					{
						if (!(khBean.CreateKh()))
						{
							khBean.createRS();
							session.setAttribute("khBean", khBean);
							String nextJSP = "/TraphacoHYERP/pages/Distributor/NopTienNew.jsp";
							response.sendRedirect(nextJSP);
						} else
						{
							INoptienList obj = new NoptienList();
							obj.setUserId(userId);
							obj.init("");
							session.setAttribute("obj", obj);
							String nextJSP = "/TraphacoHYERP/pages/Distributor/NopTien.jsp";
							response.sendRedirect(nextJSP);
						}

					} 
					else
					{
						if (!(khBean.UpdateKh()))
						{
							khBean.createRS();
							session.setAttribute("khBean", khBean);
							String nextJSP = "/TraphacoHYERP/pages/Distributor/NopTienUpdate.jsp";
							response.sendRedirect(nextJSP);
						} 
						else
						{
							INoptienList obj = new NoptienList();
							obj.setUserId(userId);
							obj.init("");
							session.setAttribute("obj", obj);
							String nextJSP = "/TraphacoHYERP/pages/Distributor/NopTien.jsp";
							response.sendRedirect(nextJSP);
						}
					}
				} 
				else
				{
					khBean.setUserId(userId);
					
					khBean.createRS();

					session.setAttribute("khBean", khBean);

					String nextJSP;
					if (id == null)
					{
						nextJSP = "/TraphacoHYERP/pages/Distributor/NopTienNew.jsp";
					} else
					{
						nextJSP = "/TraphacoHYERP/pages/Distributor/NopTienUpdate.jsp";
					}
					response.sendRedirect(nextJSP);
				}
			} 
			else
			{
				khBean.setUserId(userId);
				khBean.createRS();

				session.setAttribute("khBean", khBean);

				String nextJSP;
				if (id == null)
				{
					nextJSP = "/TraphacoHYERP/pages/Distributor/NopTienNew.jsp";
				} else
				{
					nextJSP = "/TraphacoHYERP/pages/Distributor/NopTienUpdate.jsp";
				}
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
