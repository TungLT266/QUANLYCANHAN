
package geso.traphaco.erp.servlets.dieuchuyentaisan;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.dieuchuyentaisan.IErp_DieuChuyenTaiSan;
import geso.traphaco.erp.beans.dieuchuyentaisan.IErp_DieuChuyenTaiSanList;
import geso.traphaco.erp.beans.dieuchuyentaisan.imp.Erp_DieuChuyenTaiSan;
import geso.traphaco.erp.beans.dieuchuyentaisan.imp.Erp_DieuChuyenTaiSanList;
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

public class Erp_DieuChuyenTaiSanUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public Erp_DieuChuyenTaiSanUpdateSvl( )
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

		IErp_DieuChuyenTaiSan tsBean = new Erp_DieuChuyenTaiSan(Id);
	
		
		tsBean.setCtyId((String) session.getAttribute("congtyId"));
		tsBean.setUserid(userId);
		tsBean.setId(Id);
		tsBean.init("");
		tsBean.createRs();

		if (action.equals("update"))
		{
			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DieuChuyenTaiSan_Update.jsp";
			session.setAttribute("dcBean", tsBean);
			response.sendRedirect(nextJSP);
		}else{
			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DieuChuyenTaiSan_Display.jsp";
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
		IErp_DieuChuyenTaiSan ts = new Erp_DieuChuyenTaiSan();
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
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DieuChuyenTaiSan_Update.jsp";
				}
				else
				{
					IErp_DieuChuyenTaiSanList obj = new Erp_DieuChuyenTaiSanList();
					obj.setCtyId((String) session.getAttribute("congtyId"));
					obj.init("");
					obj.createRs();
					session.setAttribute("obj", obj);
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DieuChuyenTaiSan.jsp";
				}
				
			}
			else if(id.length() == 0){
				if (!ts.themmoiMa(request))
				{
					ts.createRs();
					session.setAttribute("dcBean", ts);
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DieuChuyenTaiSan_New.jsp";
				}
				else
				{
					IErp_DieuChuyenTaiSanList obj = new Erp_DieuChuyenTaiSanList();
					obj.setCtyId((String) session.getAttribute("congtyId"));
					obj.init("");
					obj.createRs();
					session.setAttribute("obj", obj);
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DieuChuyenTaiSan.jsp";
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
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DieuChuyenTaiSan_New.jsp";
				}
				else
				{
					session.setAttribute("dcBean", ts);
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DieuChuyenTaiSan_Update.jsp";
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
