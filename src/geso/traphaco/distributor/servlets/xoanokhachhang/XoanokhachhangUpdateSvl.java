package geso.traphaco.distributor.servlets.xoanokhachhang;

import geso.traphaco.distributor.beans.xoanokhachhang.IXoanokhachhang;
import geso.traphaco.distributor.beans.xoanokhachhang.IXoanokhachhangList;
import geso.traphaco.distributor.beans.xoanokhachhang.imp.Xoanokhachhang;
import geso.traphaco.distributor.beans.xoanokhachhang.imp.XoanokhachhangList;
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


public class XoanokhachhangUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public XoanokhachhangUpdateSvl()
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

			IXoanokhachhang nvgnBean;
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
			
			
			nvgnBean = new Xoanokhachhang(id);
			nvgnBean.setUserId(userId);
			nvgnBean.init();

			session.setAttribute("nvgnBean", nvgnBean);
			if(action.equals("update"))
			{
			   nextJSP = "/TraphacoHYERP/pages/Distributor/XoaNoKhachHangUpdate.jsp";
			}
			else
			{
				nextJSP = "/TraphacoHYERP/pages/Distributor/XoaNoKhachHangDisplay.jsp";
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

			IXoanokhachhang nvgnBean;
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
				nvgnBean = new Xoanokhachhang("");
			} else
			{
				nvgnBean = new Xoanokhachhang(id);
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
			
			String ngaysua = getDateTime();
			nvgnBean.setNgaysua(ngaysua);
			
			String[] hdId = request.getParameterValues("hdId");
			//System.out.println("hdId"+ hdId.length);
			nvgnBean.setHdId(hdId);
			
			String[] khId = request.getParameterValues("khId");
			nvgnBean.setHdKhid(khId);
			
			String[] hdsotien = request.getParameterValues("hdsotien");
			nvgnBean.setHdSotien(hdsotien);
			
			String[] hdloaihd = request.getParameterValues("loaihd");
			nvgnBean.setHdLoaiHd(hdloaihd);

			String[] hdids = request.getParameterValues("hdids");

			if (hdids != null)
			{
				String[] hdSelected = new String[hdids.length];
				int i = 0;
				while (i < hdids.length)
				{
					hdSelected[i] = hdids[i].substring(hdids[i].indexOf("-") + 1, hdids[i].length());		
					System.out.println("hdSelected"+ hdSelected[i]);
					i++;
				}
				nvgnBean.setHdIds(hdSelected);
				
				String[] Khids = new String[hdids.length];
				String[] TienCKids = new String[hdids.length];
				String[] LoaiHds = new String[hdids.length];
				
				for(int j=0; j<hdids.length;j++){
					 for(int m = 0 ; m< hdId.length;m++){
						 if(hdids[j].equals(hdId[m])){
							 Khids[j]=khId[m];
							 TienCKids[j]=hdsotien[m];
							 LoaiHds[j]=hdloaihd[m];
							 System.out.println(LoaiHds[j]+",");
						 }
					 }
				}
				nvgnBean.setKhIdss(Khids);
				nvgnBean.setHdTienCKs(TienCKids);		
				nvgnBean.setHdLoaiHds(LoaiHds);	
			}
			
			
			boolean error = false;


			String action = request.getParameter("action");
			if (!error)
			{
				if (action.equals("save"))
				{
					if (id.length() == 0)
					{
						if (!(nvgnBean.CreateNvgn()))
						{
							nvgnBean.createRS();
							session.setAttribute("nvgnBean", nvgnBean);
							String nextJSP = "/TraphacoHYERP/pages/Distributor/XoaNoKhachHangNew.jsp";
							System.out.println("vào đây1");
							response.sendRedirect(nextJSP);
						} else
						{
							IXoanokhachhangList obj = new XoanokhachhangList();
							obj.setUserId(userId);
							obj.init("");
							session.setAttribute("obj", obj);
							System.out.println("vào đây2");
							String nextJSP = "/TraphacoHYERP/pages/Distributor/XoaNoKhachHang.jsp";
							response.sendRedirect(nextJSP);
						}

					} else
					{
						if (!(nvgnBean.UpdateNvgn()))
						{
							nvgnBean.init();
							session.setAttribute("nvgnBean", nvgnBean);
							String nextJSP = "/TraphacoHYERP/pages/Distributor/XoaNoKhachHangUpdate.jsp";
							response.sendRedirect(nextJSP);
						} else
						{
							IXoanokhachhangList obj = new XoanokhachhangList();
							obj.setUserId(userId);
							obj.init("");
							session.setAttribute("obj", obj);

							String nextJSP = "/TraphacoHYERP/pages/Distributor/XoaNoKhachHang.jsp";
							response.sendRedirect(nextJSP);
						}
					}
				} else
				{
					if (id.length() > 0)
					{
						nvgnBean.init();
					} else
					{
						nvgnBean.setHdId(null);
						nvgnBean.createRS();
					}

					session.setAttribute("nvgnBean", nvgnBean);

					String nextJSP;
					if (id.length() == 0)
					{
						nextJSP = "/TraphacoHYERP/pages/Distributor/XoaNoKhachHangNew.jsp";
					} else
					{
						nextJSP = "/TraphacoHYERP/pages/Distributor/XoaNoKhachHangUpdate.jsp";
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
					nextJSP = "/TraphacoHYERP/pages/Distributor/XoaNoKhachHangNew.jsp";
				} else
				{
					nextJSP = "/TraphacoHYERP/pages/Distributor/XoaNoKhachHangUpdate.jsp";
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
