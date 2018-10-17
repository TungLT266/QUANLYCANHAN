package geso.traphaco.erp.servlets.erp_capquanly;

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

import geso.traphaco.erp.beans.erp_capquanly.IErp_capquanly;
import geso.traphaco.erp.beans.erp_capquanly.IErp_capquanlyList;
import geso.traphaco.erp.beans.erp_capquanly.imp.*;


public class Erp_CapQuanLyUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;


	public Erp_CapQuanLyUpdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userid = util.getUserId(querystring);

		String id = util.getId(querystring);

		IErp_capquanly cnBean = new Erp_capquanly(id);
				
		String ctyId = session.getAttribute("congtyId").toString();
		if(ctyId == null)
			ctyId = "";
		cnBean.setCtyId(ctyId);
		
		cnBean.init();
		
		if(querystring.contains("display"))
		{
			cnBean.CreateNhanVien(null);
			
			session.setAttribute("cnBean", cnBean);
			
			response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_CapQuanLyDisplay.jsp");
		}else
		{
			
			cnBean.createRs();
			
			cnBean.CreateNhanVien(null);
			
			session.setAttribute("cnBean", cnBean);
			
			response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_CapQuanLyUpdate.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("Đã vào doPost");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();

		Utility util = new Utility();

		IErp_capquanly cnBean = new Erp_capquanly();
		String nextJSP ="";
		String id = util.antiSQLInspection(request.getParameter("id"));
		
		String ma = util.antiSQLInspection(request.getParameter("ma"));
		
		String ten = util.antiSQLInspection(request.getParameter("ten"));
		
		String trangthai = util.antiSQLInspection(request.getParameter("hoatdong"));

		String userId = util.antiSQLInspection(request.getParameter("userId"));
		
		String userTen = util.antiSQLInspection(request.getParameter("userTen"));
		
		String loaicapId = util.antiSQLInspection(request.getParameter("loaicapId"));
		
		String macap = util.antiSQLInspection(request.getParameter("macap"));
		
		String tencap = util.antiSQLInspection(request.getParameter("tencap"));
		
		String quanlycapId = util.antiSQLInspection(request.getParameter("quanlycapId"));
		
		String email = util.antiSQLInspection(request.getParameter("email"));
		
		String ctyId = session.getAttribute("congtyId").toString();
		if(ctyId == null)
			ctyId = "";
		cnBean.setCtyId(ctyId);
				
		String nv_chon[] = request.getParameterValues("nv_chon");
		cnBean.CreateNhanVien(nv_chon);
		
		String ngaytao = getDateTime();
		String ngaysua = ngaytao;

		if (trangthai == null)
			trangthai = "0";
		cnBean.setTRANGTHAI(trangthai);
		
		if(loaicapId == null)
			loaicapId = "";
		cnBean.setLoaicapId(loaicapId);
				
		if(macap == null)
			macap = "";
		cnBean.setMacap(macap);
		
		if(tencap == null)
			tencap = "";
		cnBean.setTencap(tencap);
		
		if(quanlycapId == null)
			quanlycapId = "";
		cnBean.setQuanlycapId(quanlycapId);
		
		if(email == null)
			email = "";
		cnBean.setEmail(email);

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

		System.out.println("action:"+action);
		
		if (action.equals("save"))
		{			
			if (id == null)
			{
				System.out.println("Đã vào tạo mới");
				if (!cnBean.ThemMoiMa())
				{
					session.setAttribute("userId", userId);
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_CapQuanLyNew.jsp";
				} 
				else
				{
					cnBean.DBClose();
					IErp_capquanlyList cnList = new Erp_capquanlyList();
					cnList.setUserid(userId);
					cnList.init("");
					session.setAttribute("cnList", cnList);
					nextJSP="/TraphacoHYERP/pages/Erp/Erp_CapQuanLy.jsp";

				}
			}
			else
			{
				if (!cnBean.UpdateMa())
				{
					session.setAttribute("userId", userId);
					nextJSP="/TraphacoHYERP/pages/Erp/Erp_CapQuanLyUpdate.jsp";
				} 
				else
				{
					cnBean.DBClose();
					IErp_capquanlyList cnList = new Erp_capquanlyList();
					cnList.setUserid(userId);
					cnList.init("");
					session.setAttribute("cnList", cnList);
					nextJSP="/TraphacoHYERP/pages/Erp/Erp_CapQuanLy.jsp";
				}
			}
		}
		else
		{
			try
			{
				cnBean.createRs();
				//obj.init();
				cnBean.CreateNhanVien(nv_chon);
				session.setAttribute("cnBean", cnBean);
				
				if ( id== null){
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_CapQuanLyNew.jsp";
				}
				else{
					nextJSP="/TraphacoHYERP/pages/Erp/Erp_CapQuanLyUpdate.jsp";			
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				System.out.println("loi !!!!!!!!!!!!!!!!");
			}
		}
		
		System.out.println("nextJSP "+nextJSP);
		response.sendRedirect(nextJSP);
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
