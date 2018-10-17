package geso.traphaco.distributor.servlets.cantrucongno;


import geso.traphaco.distributor.beans.cantrucongno.ICantrucongnoList;

import geso.traphaco.distributor.beans.cantrucongno.imp.CantrucongnoList;
import geso.traphaco.distributor.beans.cantrucongno.ICantrucongno;
import geso.traphaco.distributor.beans.cantrucongno.imp.Cantrucongno;
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


public class CantrucongnoUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public CantrucongnoUpdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("vad");
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

			ICantrucongno nvgnBean;
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
			String nextJSP = "";
			System.out.println("ID nè:"+id);
			nvgnBean = new Cantrucongno(id);
			nvgnBean.setUserId(userId);
			nvgnBean.init();

			session.setAttribute("nvgnBean", nvgnBean);
			if(action.equals("update"))
			{
			   nextJSP = "/TraphacoHYERP/pages/Distributor/CanTruCongNoKHUpdate.jsp";
			}
			else
			{
				nextJSP = "/TraphacoHYERP/pages/Distributor/CanTruCongNoKHDisplay.jsp";
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
			
			ICantrucongno nvgnBean;
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
				nvgnBean = new Cantrucongno("");
			} else
			{
				nvgnBean = new Cantrucongno(id);
			}

			userId = util.antiSQLInspection(request.getParameter("userId"));
			nvgnBean.setUserId(userId);

			String ngay = util.antiSQLInspection(request.getParameter("ngay"));
			if (ngay == null)
				ngay = getDateTime();
			nvgnBean.setNgay(ngay);

			String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
			if (ghichu == null)
				ghichu = "";
			nvgnBean.setGhichu(ghichu);
			
			String nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if (nppId == null)
				nppId = "";
			nvgnBean.setNppId(nppId);

			String tungay = util.antiSQLInspection(request.getParameter("tungay"));
			if (tungay == null)
				tungay = "";
			nvgnBean.setTungay(tungay);

			String denngay = util.antiSQLInspection(request.getParameter("denngay"));
			if (denngay == null)
				denngay = "";
			nvgnBean.setDenngay(denngay);
			
			String sohoadontu = util.antiSQLInspection(request.getParameter("sohoadontu"));
			if (sohoadontu == null)
				sohoadontu = "";
			nvgnBean.setSohoadontu(sohoadontu);

			String sohoadonden = util.antiSQLInspection(request.getParameter("sohoadonden"));
			if (sohoadonden == null)
				sohoadonden = "";
			nvgnBean.setSohoadonden(sohoadonden);
			
			String sotientu = util.antiSQLInspection(request.getParameter("sotientu"));
			if (sotientu == null)
				sotientu = "0";
			nvgnBean.setSotientu(sotientu);			

			String sotienden = util.antiSQLInspection(request.getParameter("sotienden"));
			if (sotienden == null)
				sotienden = "0";
			nvgnBean.setSotienden(sotienden);

			String KHId = util.antiSQLInspection(request.getParameter("khIdLoc"));
			if (KHId == null)
				KHId = "";
			nvgnBean.setKhId(KHId);
			
			String ddkdId = util.antiSQLInspection(request.getParameter("ddkdId"));
			if (ddkdId == null)
				ddkdId = "";
			nvgnBean.setDdkdId(ddkdId);
						
			String ngaysua = getDateTime();
			nvgnBean.setNgaysua(ngaysua);
			
			String[] hdId = request.getParameterValues("hdId");			
			nvgnBean.setHdId(hdId);
			
			String[] khId = request.getParameterValues("khId");
			nvgnBean.setHdKhid(khId);
			
			String[] hdkhMa = request.getParameterValues("khMa");
			nvgnBean.setHdMakh(hdkhMa);
					
			String[] hdNgayhd = request.getParameterValues("hdNgay");
			nvgnBean.setHdNgayhd(hdNgayhd);
			
			String[] hdsohoadon = request.getParameterValues("hdsohoadon");
			nvgnBean.setHdSohd(hdsohoadon);
			
			String[] hdtienavat = request.getParameterValues("hdtienavat");
			nvgnBean.setHdSotien(hdtienavat);
			
			String[] hdtienck = request.getParameterValues("hdtienck");
			nvgnBean.setHdTienCK(hdtienck);
			
			if (hdId != null)
			{
				String[] hdids = new String[hdId.length];
				int i = 0;
				while (i < hdId.length)
				{
					String chon=request.getParameter(hdId[i]);
					if(chon==null)
					{
						hdId[i]="";
						hdids[i]="";
					}else
					{
						hdids[i] =hdId[i];
						System.out.println("HD"+hdids[i]);
					}
					i++;
				}
				nvgnBean.setHdIds(hdids);
			}							
			
			boolean error = false;


			String action = request.getParameter("action");
			
			if (!error)
			{
				if (action.equals("save"))
				{
					if (id.length() == 0)
					{
						if (!(nvgnBean.CreateNvgn()))//TAO MOI
						{
							nvgnBean.createRS();
							session.setAttribute("nvgnBean", nvgnBean);
							String nextJSP = "/TraphacoHYERP/pages/Distributor/CanTruCongNoKHNew.jsp";
							response.sendRedirect(nextJSP);
						} else
						{
							ICantrucongnoList obj = new CantrucongnoList();
							obj.setUserId(userId);
							obj.init("");
							System.out.println("vao day_________");
							session.setAttribute("obj", obj);
							
							String nextJSP = "/TraphacoHYERP/pages/Distributor/CanTruCongNoKH.jsp";
							response.sendRedirect(nextJSP);
						}

					} else
					{
						if (!(nvgnBean.UpdateNvgn()))//CAP NHAT
						{
							nvgnBean.init();
							session.setAttribute("nvgnBean", nvgnBean);
							String nextJSP = "/TraphacoHYERP/pages/Distributor/CanTruCongNoKHUpdate.jsp";
							response.sendRedirect(nextJSP);
						} else
						{
							ICantrucongnoList obj = new CantrucongnoList();
							obj.setUserId(userId);
							obj.init("");
							session.setAttribute("obj", obj);

							String nextJSP = "/TraphacoHYERP/pages/Distributor/CanTruCongNoKH.jsp";
							response.sendRedirect(nextJSP);
						}
					}
				} else
					//SEARCH 
				{
					//
					if (action.equals("search"))
					{
						nvgnBean.setHdId(null);
						System.out.println("ID______:"+ id);
						nvgnBean.createKHRS();
						
					}
					//
					if(action.equals("submitForm"))
					{
						if (id.length() > 0)
						{
							nvgnBean.init();
						} else
						{
							nvgnBean.setHdId(null);
							nvgnBean.createRS();
						}
					}
					session.setAttribute("nvgnBean", nvgnBean);

					String nextJSP;
					if (id.length() == 0)
					{
						nextJSP = "/TraphacoHYERP/pages/Distributor/CanTruCongNoKHNew.jsp";
					} else
					{
						nextJSP = "/TraphacoHYERP/pages/Distributor/CanTruCongNoKHUpdate.jsp";
					}
					response.sendRedirect(nextJSP);
				}
			} else
			{
				if (id.length() > 0)
				{
					nvgnBean.init();
				} else
				{
					nvgnBean.createRS();
				}
				session.setAttribute("nvgnBean", nvgnBean);
				String nextJSP;
				if (id.length() == 0)
				{
					nextJSP = "/TraphacoHYERP/pages/Distributor/CanTruCongNoKHNew.jsp";
				} else
				{
					nextJSP = "/TraphacoHYERP/pages/Distributor/CanTruCongNoKHUpdate.jsp";
				}
				response.sendRedirect(nextJSP);
			}
		}

	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}

}
