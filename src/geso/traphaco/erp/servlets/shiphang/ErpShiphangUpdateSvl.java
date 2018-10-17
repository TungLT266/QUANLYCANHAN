package geso.traphaco.erp.servlets.shiphang;

import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.beans.shiphang.*;
import geso.traphaco.erp.beans.shiphang.imp.*;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility_Kho;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpShiphangUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
	
	public ErpShiphangUpdateSvl()
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
			session.setMaxInactiveInterval(30000);
			
			this.out = response.getWriter();
			Utility util = new Utility();
			
			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);
			
			out.println(userId);	
			
			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));
			
			String id = util.getId(querystring);
			IErpShiphang nhBean = new ErpShiphang(id);
			nhBean.setCongtyId((String)session.getAttribute("congtyId"));
			nhBean.setUserId(userId); // phai co UserId truoc khi Init
			nhBean.init();
			String nextJSP;
			
			if (request.getQueryString().indexOf("display") >= 0)
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpShipHangDisplay.jsp";
			}
			else
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpShipHangUpdate.jsp";	
			}
			
			session.setAttribute("nhBean", nhBean);			
			response.sendRedirect(nextJSP);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		
		String sum = (String) session.getAttribute("sum");
		Utility_Kho util_kho=new Utility_Kho();
		dbutils db=new dbutils();
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			
			session.setMaxInactiveInterval(30000);
			
			this.out = response.getWriter();
			IErpShiphang nhBean;
			
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			
			if (id == null)
			{
				nhBean = new ErpShiphang("");
			}
			else
			{
				nhBean = new ErpShiphang(id);
			}
			
			nhBean.setCongtyId((String)session.getAttribute("congtyId"));
			nhBean.setUserId(userId);
			
			String ngaygd = util.antiSQLInspection(request.getParameter("ngaychungtu"));
			if (ngaygd == null || ngaygd == "")
				ngaygd = this.getDateTime();			
			nhBean.setNgaychungtu(ngaygd);
			
			
			String ngaychot = util.antiSQLInspection(request.getParameter("ngaychot"));
			if (ngaychot == null || ngaychot == "")
				ngaychot = this.getDateTime();
			
			nhBean.setNgaychot(ngaychot);
			
			
			String soPO = request.getParameter("poId");
			if (soPO == null)
				soPO = "";
			nhBean.setDonmuahangId(soPO);
			
			
			String nccId = request.getParameter("nccId");
			if (nccId == null)
				nccId = "";
			nhBean.setNccId(nccId);

			
			String ghichu = request.getParameter("ghichu");
			if(ghichu==null){
				ghichu="";
			}
			nhBean.setGhichu(ghichu);
			
			String dvthId  = request.getParameter("dvthId");
			if(dvthId==null){
				dvthId="";
			}
			nhBean.setDvthId(dvthId);
						
			// Luu lai san pham
			String[] spId = request.getParameterValues("idhangmua");
			String[] spMa = request.getParameterValues("mahangmua");
			String[] spTen = request.getParameterValues("tenhangmua");
			String[] spDonvi = request.getParameterValues("dvdl");
			String[] soluongdat = request.getParameterValues("soluongdat");
			String[] dungsai = request.getParameterValues("dungsai");
			String[] soluongdaship = request.getParameterValues("soluongdaship");
			String[] soluongship = request.getParameterValues("soluongship");
			String[] ngaynhandukien = request.getParameterValues("ngaynhandukien");
			String[] dongia = request.getParameterValues("dongiaMua");
			
			String action = request.getParameter("action");
			if (action.equals("save"))
			{
				
				List<ISanpham> spList = new ArrayList<ISanpham>();
				
				if (spMa != null)
				{
					ISanpham sanpham = null;
					int m = 0;
					while (m < spMa.length)
					{
						sanpham = new Sanpham(spId[m], spMa[m], spTen[m],"", "", ngaynhandukien[m], soluongdat[m].replaceAll("", ""), spDonvi[m]);
						
						sanpham.setDungsai(dungsai[m]);
						sanpham.setDongia(dongia[m]);
						sanpham.setSoluongdaship(soluongdaship[m]);
						sanpham.setSoluongship(soluongship[m]);
						
						spList.add(sanpham);
						
						m++; 
					}
				}
				//System.out.println("So sp trong List: " + spList.size());
				nhBean.setSpList(spList);
				
				if (id == null) // tao moi
				{
					if (!nhBean.createShipHang())
					{
						nhBean.createRs();
						
						session.setAttribute("nhBean", nhBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpShipHangNew.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{ 
						
						IErpShiphangList obj = new ErpShiphangList();
						
						obj.setCongtyId((String)session.getAttribute("congtyId"));
						obj.setUserId(userId);
						obj.setLoaidh("0");
						obj.init("");
						
						session.setAttribute("obj", obj);
						
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpShipHang.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				else
				{
					if (!nhBean.updateShipHang())
					{
						nhBean.createRs();
						
						session.setAttribute("nhBean", nhBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpShipHangUpdate.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{
						
						IErpShiphangList obj = new ErpShiphangList();
						obj.setCongtyId((String)session.getAttribute("congtyId"));
						obj.setUserId(userId);
						obj.setLoaidh("0");
						obj.init("");
						
						session.setAttribute("obj", obj);
						
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpShipHang.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
					String nextJSP;
					nhBean.createRs();
					
					if (id == null)
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpShipHangNew.jsp";
					}
					else
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpShipHangUpdate.jsp";
					}
					
					session.setAttribute("nhBean", nhBean);
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
