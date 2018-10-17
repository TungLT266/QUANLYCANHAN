package geso.traphaco.erp.servlets.erp_chinhanh;

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

import geso.traphaco.erp.beans.erp_chinhanh.IErp_chinhanh;
import geso.traphaco.erp.beans.erp_chinhanh.IErp_chinhanhList;
import geso.traphaco.erp.beans.erp_chinhanh.imp.*;


public class Erp_ChiNhanhUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;


	public Erp_ChiNhanhUpdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userid = util.getUserId(querystring);
		System.out.println("User id is " + userid);

		String id = util.getId(querystring);
		System.out.println("ID is " + id);

		IErp_chinhanh cnBean = new Erp_chinhanh(id);
		
		String cnId = util.getId(querystring);
		if (userid.length() == 0)
			userid = util.getUserId(querystring);
		
		String nppId = util.getIdNhapp(userid);
	    if(nppId == null)
	    	nppId = "";
	    cnBean.setnppId(nppId);
	    
		session.setAttribute("cnBean", cnBean);
		
		if(querystring.contains("display"))
		{
			response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_ChiNhanhDisplay.jsp");
		}else
		{
			response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_ChiNhanhUpdate.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();

		Utility util = new Utility();
//		session.setAttribute("util", util);

		IErp_chinhanh cnBean = new Erp_chinhanh();
		String nextJSP ="";
		String id = util.antiSQLInspection(request.getParameter("id"));
		String ma = util.antiSQLInspection(request.getParameter("ma"));
		String ten = util.antiSQLInspection(request.getParameter("ten"));
		String trangthai = util.antiSQLInspection(request.getParameter("hoatdong"));

		String userId = util.antiSQLInspection(request.getParameter("userId"));
		String userTen = util.antiSQLInspection(request.getParameter("userTen"));

//		session.setAttribute("userId", userId);
//		session.setAttribute("userTen", userTen);

		String ngaytao = getDateTime();
		String ngaysua = ngaytao;

		if (trangthai == null)
			trangthai = "0";
		cnBean.setTRANGTHAI(trangthai);

		if (id != null)
			cnBean.setID(id);

		if (ma != null)
			cnBean.setMA(ma);

		if (ten != null)
			cnBean.setTEN(ten);

		cnBean.setNGAYTAO(ngaytao);
		cnBean.setNGAYSUA(ngaysua);
		cnBean.setNGUOITAO(userId);
		cnBean.setNGUOISUA(userId);
		
		String action = request.getParameter("action");

		if (action.equals("save"))
		{
			if (id == null)
			{
				if (!cnBean.ThemMoiMa(session.getAttribute("congtyId").toString()))
				{
					session.setAttribute("userId", userId);
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_ChiNhanhNew.jsp";
				} 
				else
				{
					cnBean.DBClose();
					IErp_chinhanhList cnList = new Erp_chinhanhList();
					cnList.setUserid(userId);
				    
					cnList.init("");
					session.setAttribute("cnList", cnList);
					nextJSP="/TraphacoHYERP/pages/Erp/Erp_ChiNhanh.jsp";

				}
			}
			else
			{
				if (!cnBean.UpdateMa())
				{
					session.setAttribute("userId", userId);
					nextJSP="/TraphacoHYERP/pages/Erp/Erp_ChiNhanhUpdate.jsp";
				} 
				else
				{
					cnBean.DBClose();
					IErp_chinhanhList cnList = new Erp_chinhanhList();
					cnList.setUserid(userId);
					cnList.init("");
					session.setAttribute("cnList", cnList);
					nextJSP="/TraphacoHYERP/pages/Erp/Erp_ChiNhanh.jsp";
				}
			}
		}
		session.setAttribute("cnBean", cnBean);
		response.sendRedirect(nextJSP);
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
