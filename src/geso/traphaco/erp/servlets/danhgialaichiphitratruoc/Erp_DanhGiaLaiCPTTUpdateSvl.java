package geso.traphaco.erp.servlets.danhgialaichiphitratruoc;


import geso.traphaco.erp.beans.danhgialaichiphitratruoc.IErp_DanhGiaLaiCPTT;
import geso.traphaco.erp.beans.danhgialaichiphitratruoc.IErp_DanhGiaLaiCPTTList;
import geso.traphaco.erp.beans.danhgialaichiphitratruoc.imp.Erp_DanhGiaLaiCPTT;
import geso.traphaco.erp.beans.danhgialaichiphitratruoc.imp.Erp_DanhGiaLaiCPTTList;
import geso.traphaco.erp.beans.danhgialaitaisan.IErp_DanhGiaLaiTaiSan;
import geso.traphaco.erp.beans.danhgialaitaisan.IErp_DanhGiaLaiTaiSanList;
import geso.traphaco.erp.beans.danhgialaitaisan.imp.Erp_DanhGiaLaiTaiSan;
import geso.traphaco.erp.beans.danhgialaitaisan.imp.Erp_DanhGiaLaiTaiSanList;

import geso.traphaco.center.util.Utility;
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

public class Erp_DanhGiaLaiCPTTUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public Erp_DanhGiaLaiCPTTUpdateSvl( )
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
		String action = util.getAction(querystring);
		out.println(action);
		
		String Id = util.getId(querystring);
		String userId = util.getUserId(querystring);

		IErp_DanhGiaLaiCPTT tsBean = new Erp_DanhGiaLaiCPTT(Id);
	
		
		tsBean.setCtyId((String) session.getAttribute("congtyId"));
		tsBean.setUserid(userId);
		tsBean.setId(Id);
		tsBean.init("");
		tsBean.createRs();

		if (action.equals("update"))
		{
			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DanhGiaLaiCPTT_Update.jsp";
			session.setAttribute("dcBean", tsBean);
			response.sendRedirect(nextJSP);
		}else{
			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DanhGiaLaiCPTT_Display.jsp";
			session.setAttribute("dcBean", tsBean);
			response.sendRedirect(nextJSP);			
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		Utility util = new Utility();
		
		String ctyId = (String)session.getAttribute("congtyId");
		IErp_DanhGiaLaiCPTT cptt = new Erp_DanhGiaLaiCPTT();
		cptt.setCtyId(ctyId);
		
		String nextJSP = "";
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		String userTen = util.antiSQLInspection(request.getParameter("userTen"));
		
		String id = util.antiSQLInspection(request.getParameter("Id"));
		if(id == null)
			id = "";
		cptt.setId(id);
		
		String cpttId = util.antiSQLInspection(request.getParameter("chiphitratruoc"));
		if(cpttId == null) cpttId = "";	
		cptt.setCpttId(cpttId);
		
		
		String ngaychungtu = util.antiSQLInspection(request.getParameter("ngaychungtu"));
		if(ngaychungtu == null) ngaychungtu = "";	
		cptt.setNgaychungtu(ngaychungtu);
		
		
		String sochungtu = util.antiSQLInspection(request.getParameter("sochungtu"));
		if(sochungtu == null) sochungtu = "";	
		cptt.setSochungtu(sochungtu);
		String dienGiaiCT = util.antiSQLInspection(request.getParameter("dienGiaiCT"));
		if(dienGiaiCT == null) dienGiaiCT ="";
		cptt.setDienGiaiCT(dienGiaiCT);
		

		
		String sothangkh = util.antiSQLInspection(request.getParameter("sothangkhauhao"));
		if(sothangkh == null) sothangkh = "";	
		cptt.setSothangkh(sothangkh);
		
		
		String sothangkhmoi = util.antiSQLInspection(request.getParameter("sothangkhauhaomoi"));
		if(sothangkhmoi == null) sothangkhmoi = "";	
		cptt.setSothangkhmoi(sothangkhmoi);
		
		
		String dieuchinhsothangkh =request.getParameter("dieuchinhsothangkh");
		if(dieuchinhsothangkh == null) dieuchinhsothangkh = "";	
		cptt.setDieuchinhsothangkh(dieuchinhsothangkh);
		
		
		
		String nguyengia = util.antiSQLInspection(request.getParameter("nguyengia"));
		if(nguyengia == null) nguyengia = "";	
		cptt.setNguyengia(nguyengia);
		
		String nguyengiamoi = util.antiSQLInspection(request.getParameter("nguyengiamoi"));
		if(nguyengiamoi == null) nguyengiamoi = "";	
		cptt.setNguyengiamoi(nguyengiamoi);
		
		String dieuchinhnguyengia = util.antiSQLInspection(request.getParameter("dieuchinhnguyengia"));
		if(dieuchinhnguyengia == null) dieuchinhnguyengia = "";	
		cptt.setDieuchinhnguyengia(dieuchinhnguyengia);
		
		
		String loaichiphitratruoc = util.antiSQLInspection(request.getParameter("loaichiphitratruoc"));
		if(loaichiphitratruoc == null) loaichiphitratruoc = "";	
		cptt.setLoaiCPTTId(loaichiphitratruoc);
		
		
		String loaichiphitratruocmoi = util.antiSQLInspection(request.getParameter("loaichiphitratruocmoi"));
		if(loaichiphitratruocmoi == null) loaichiphitratruocmoi = "";	
		cptt.setLoaiCPTTId_New(loaichiphitratruocmoi);

		cptt.setUserid(userId);
		
		session.setAttribute("obj", cptt);
		session.setAttribute("userId", userId);
		session.setAttribute("userTen", userTen);
		String action = request.getParameter("action");
		
		System.out.println("Action la: " + action);
		
		if (action.equals("save"))
		{
			if(id.length() > 0 ){
				if (!cptt.UpdateMa(request))
				{
					cptt.createRs();
					cptt.setMsg("Update Khong Thanh Cong");
					session.setAttribute("dcBean", cptt);
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DanhGiaLaiCPTT_Update.jsp";
				}
				else
				{
					IErp_DanhGiaLaiCPTTList obj = new Erp_DanhGiaLaiCPTTList();
					obj.setCtyId((String) session.getAttribute("congtyId"));
					obj.init("");
					obj.createRs();
					session.setAttribute("obj", obj);
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DanhGiaLaiCPTT.jsp";
				}
				
			}
			else if(id.length() == 0){
				if (!cptt.themmoiMa(request))
				{
					cptt.createRs();
					session.setAttribute("dcBean", cptt);
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DanhGiaLaiCPTT_New.jsp";
				}
				else
				{
					IErp_DanhGiaLaiCPTTList obj = new Erp_DanhGiaLaiCPTTList();
					obj.setCtyId((String) session.getAttribute("congtyId"));
					obj.init("");
					obj.createRs();
					session.setAttribute("obj", obj);
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DanhGiaLaiCPTT.jsp";
				}
			}
		}
		
			else
			{
				cptt.setUserid(userId);
				cptt.createRs();
				
				if(id.trim().length() <= 0)
				{
					session.setAttribute("dcBean", cptt);
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DanhGiaLaiCPTT_New.jsp";
				}
				else
				{
					session.setAttribute("dcBean", cptt);
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DanhGiaLaiCPTT_Update.jsp";
				}

			}
		
		
		response.sendRedirect(nextJSP);
	}
	
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
