
package geso.traphaco.erp.servlets.dieuchuyencptt;


import geso.traphaco.erp.beans.dieuchuyencptt.IErp_DieuChuyenCPTTList;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.dieuchuyencptt.IErp_DieuChuyenCPTT;

import geso.traphaco.erp.beans.dieuchuyencptt.imp.Erp_DieuChuyenCPTT;
import geso.traphaco.erp.beans.dieuchuyencptt.imp.Erp_DieuChuyenCPTTList;

import geso.traphaco.erp.beans.taisancodinh.IPhanBo;
import geso.traphaco.erp.beans.taisancodinh.imp.PhanBo;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Erp_DieuChuyenCPTTUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public Erp_DieuChuyenCPTTUpdateSvl( )
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

		IErp_DieuChuyenCPTT tsBean = new Erp_DieuChuyenCPTT(Id);
	
		
		tsBean.setCtyId((String) session.getAttribute("congtyId"));
		tsBean.setUserid(userId);
		tsBean.setId(Id);
		tsBean.init("");
		tsBean.createRs();

		if (action.equals("update"))
		{
			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DieuChuyenCPTT_Update.jsp";
			session.setAttribute("dcBean", tsBean);
			response.sendRedirect(nextJSP);
		}else{
			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DieuChuyenCPTT_Display.jsp";
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
		IErp_DieuChuyenCPTT ts = new Erp_DieuChuyenCPTT();
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
		System.out.println("Tai san co dinh ID"+tscdId);
		
		String ngaychungtu = util.antiSQLInspection(request.getParameter("ngaychungtu"));
		if(ngaychungtu == null) ngaychungtu = "";	
		ts.setNgaychungtu(ngaychungtu);
		
		
		String sochungtu = util.antiSQLInspection(request.getParameter("sochungtu"));
		if(sochungtu == null) sochungtu = "";	
		ts.setSochungtu(sochungtu);
		
		String dienGiaiCT = util.antiSQLInspection(request.getParameter("dienGiaiCT"));
		if(dienGiaiCT == null) dienGiaiCT = "";
		ts.setDienGiaiCT(dienGiaiCT);
		
//		String phongban = util.antiSQLInspection(request.getParameter("phongban"));
//		if(phongban == null) phongban = "";	
//		ts.setPbId(phongban);
		
		String ttcpId = util.antiSQLInspection(request.getParameter("ttcpId"));
		if(ttcpId == null) ttcpId = "";	
		ts.setTtcpId(ttcpId);
		
		
		String ttcpCuId = util.antiSQLInspection(request.getParameter("ttcpCu"));
		if(ttcpCuId == null) ttcpCuId = "";	
		ts.setTtcpCuId(ttcpCuId);
		
		
		
//		String pbCuId = util.antiSQLInspection(request.getParameter("pbCu"));
//		if(pbCuId == null) pbCuId = "";	
//		ts.setPbCuId(pbCuId);
//	
//		
//	
		String[] cdtsIds = request.getParameterValues("cdtsIds");
		String[] kmcpIds = request.getParameterValues("kmcpIds");
		String[] khoanMucId = request.getParameterValues("khoanMucId");
		String[] phanTramKhauHao = request.getParameterValues("phanTramKhauHao");
		ts.setCdtsIds(cdtsIds);
		ts.setKhoanmucIds(kmcpIds);
		ts.setUserid(userId);
		ts.setPhanTramKhauHao(phanTramKhauHao);
		ArrayList<IPhanBo> phanBoList= new 	ArrayList<IPhanBo> ();
		
		if(khoanMucId != null){
			for (int i=0; i < khoanMucId.length; i++) {
				if (khoanMucId[i].trim().length() > 0 && !khoanMucId[i].trim().equals("")) {
					IPhanBo phanBo= new PhanBo();
					phanBo.setKhoanMucId(khoanMucId[i]);
					phanBo.setPhanTram(phanTramKhauHao[i]);
					phanBoList.add(phanBo);
				}
			
				
			}
		}
		ts.setPhanBoList(phanBoList);
		
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
					session.setAttribute("dcBean", ts);
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DieuChuyenCPTT_Update.jsp";
				}
				else
				{
					IErp_DieuChuyenCPTTList obj = new Erp_DieuChuyenCPTTList();
					obj.setCtyId((String) session.getAttribute("congtyId"));
					obj.init("");
					obj.createRs();
					session.setAttribute("obj", obj);
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DieuChuyenCPTT.jsp";
				}
				
			}
			else if(id.length() == 0){
				if (!ts.themmoiMa(request))
				{
					ts.createRs();
					session.setAttribute("dcBean", ts);
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DieuChuyenCPTT_New.jsp";
				}
				else
				{
					IErp_DieuChuyenCPTTList obj = new Erp_DieuChuyenCPTTList();
					obj.setCtyId((String) session.getAttribute("congtyId"));
					obj.init("");
					obj.createRs();
					session.setAttribute("obj", obj);
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DieuChuyenCPTT.jsp";
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
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DieuChuyenCPTT_New.jsp";
				}
				else
				{
					session.setAttribute("dcBean", ts);
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DieuChuyenCPTT_Update.jsp";
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
