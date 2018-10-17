package geso.traphaco.erp.servlets.danhgialaitaisan;


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

public class Erp_DanhGiaLaiTaiSanUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public Erp_DanhGiaLaiTaiSanUpdateSvl( )
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

		IErp_DanhGiaLaiTaiSan tsBean = new Erp_DanhGiaLaiTaiSan(Id);
	
		
		tsBean.setCtyId((String) session.getAttribute("congtyId"));
		tsBean.setUserid(userId);
		tsBean.setId(Id);
		tsBean.init("");
		tsBean.createRs();

		if (action.equals("update"))
		{
			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DanhGiaLaiTaiSan_Update.jsp";
			session.setAttribute("dcBean", tsBean);
			response.sendRedirect(nextJSP);
		}else{
			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DanhGiaLaiTaiSan_Display.jsp";
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
		IErp_DanhGiaLaiTaiSan ts = new Erp_DanhGiaLaiTaiSan();
		ts.setCtyId(ctyId);
		
		String nextJSP = "";
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		String userTen = util.antiSQLInspection(request.getParameter("userTen"));
		
		String id = util.antiSQLInspection(request.getParameter("Id"));
		if(id == null)
			id = "";
		ts.setId(id);
		
		String tscdId = util.antiSQLInspection(request.getParameter("taisancodinh"));
		if(tscdId == null) tscdId = "";	
		ts.setTscdId(tscdId);
		
		
		String ngaychungtu = util.antiSQLInspection(request.getParameter("ngaychungtu"));
		if(ngaychungtu == null) ngaychungtu = "";	
		ts.setNgaychungtu(ngaychungtu);
		
		
		String sochungtu = util.antiSQLInspection(request.getParameter("sochungtu"));
		if(sochungtu == null) sochungtu = "";	
		ts.setSochungtu(sochungtu);
		String dienGiaiCT = util.antiSQLInspection(request.getParameter("dienGiaiCT"));
		if(dienGiaiCT == null) dienGiaiCT ="";
		ts.setDienGiaiCT(dienGiaiCT);
		

		
		String sothangkh = util.antiSQLInspection(request.getParameter("sothangkhauhao"));
		if(sothangkh == null) sothangkh = "";	
		ts.setSothangkh(sothangkh);
		
		
		String sothangkhmoi = util.antiSQLInspection(request.getParameter("sothangkhauhaomoi"));
		if(sothangkhmoi == null) sothangkhmoi = "";	
		ts.setSothangkhmoi(sothangkhmoi);
		
		
		String dieuchinhsothangkh = util.antiSQLInspection(request.getParameter("dieuchinhsothangkh"));
		if(dieuchinhsothangkh == null) dieuchinhsothangkh = "";	
		ts.setDieuchinhsothangkh(dieuchinhsothangkh);
		
		
		
		String nguyengia = util.antiSQLInspection(request.getParameter("nguyengia"));
		if(nguyengia == null) nguyengia = "";	
		ts.setNguyengia(nguyengia);
		
		String nguyengiamoi = util.antiSQLInspection(request.getParameter("nguyengiamoi"));
		if(nguyengiamoi == null) nguyengiamoi = "";	
		ts.setNguyengiamoi(nguyengiamoi);
		
		String dieuchinhnguyengia = util.antiSQLInspection(request.getParameter("dieuchinhnguyengia"));
		if(dieuchinhnguyengia == null) dieuchinhnguyengia = "";	
		ts.setDieuchinhnguyengia(dieuchinhnguyengia);
		

		ts.setUserid(userId);
		
		session.setAttribute("obj", ts);
		session.setAttribute("userId", userId);
		session.setAttribute("userTen", userTen);
		String action = request.getParameter("action");
		
		System.out.println("Action la: " + action);
		
		if (action.equals("save"))
		{
			if(id.length() > 0 ){
				if (!ts.UpdateMa(request))
				{
					ts.createRs();
					ts.setMsg("Update Khong Thanh Cong");
					session.setAttribute("dcBean", ts);
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DanhGiaLaiTaiSan_Update.jsp";
				}
				else
				{
					IErp_DanhGiaLaiTaiSanList obj = new Erp_DanhGiaLaiTaiSanList();
					obj.setCtyId((String) session.getAttribute("congtyId"));
					obj.init("");
					obj.createRs();
					session.setAttribute("obj", obj);
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DanhGiaLaiTaiSan.jsp";
				}
				
			}
			else if(id.length() == 0){
				if (!ts.themmoiMa(request))
				{
					ts.createRs();
					session.setAttribute("dcBean", ts);
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DanhGiaLaiTaiSan_New.jsp";
				}
				else
				{
					IErp_DanhGiaLaiTaiSanList obj = new Erp_DanhGiaLaiTaiSanList();
					obj.setCtyId((String) session.getAttribute("congtyId"));
					obj.init("");
					obj.createRs();
					session.setAttribute("obj", obj);
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DanhGiaLaiTaiSan.jsp";
				}
			}
		}
		
			else
			{
				ts.setUserid(userId);
				ts.createRs();
				
				if(id.trim().length() <= 0)
				{
					session.setAttribute("dcBean", ts);
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DanhGiaLaiTaiSan_New.jsp";
				}
				else
				{
					session.setAttribute("dcBean", ts);
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DanhGiaLaiTaiSan_Update.jsp";
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
