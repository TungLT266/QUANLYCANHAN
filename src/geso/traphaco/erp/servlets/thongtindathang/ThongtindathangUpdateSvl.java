package geso.traphaco.erp.servlets.thongtindathang;

import geso.traphaco.erp.beans.thongtindathang.IThongtindathang;
import geso.traphaco.erp.beans.thongtindathang.IThongtindathangList;
import geso.traphaco.erp.beans.thongtindathang.imp.Thongtindathang;
import geso.traphaco.erp.beans.thongtindathang.imp.ThongtindathangList;
import geso.traphaco.center.util.Utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ThongtindathangUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;

	public ThongtindathangUpdateSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String ctyId = (String)session.getAttribute("congtyId");

		Utility util = new Utility();
		String querystring = request.getQueryString();
		
		String userId = util.getUserId(querystring);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		
		IThongtindathang obj = new Thongtindathang();
		
		obj.setUserId(userId);
		obj.setCtyId(ctyId);
		String id = getId(querystring);
		obj.setId(id);
		obj.init();
		obj.setCtyId(ctyId);
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ThongtindathangUpdate.jsp";
		
		if (querystring.indexOf("display") > 0)
			nextJSP = "/TraphacoHYERP/pages/Erp/ThongtindathangDisplay.jsp";

		response.sendRedirect(nextJSP);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();
	    String ctyId = (String)session.getAttribute("congtyId"); 
		Utility util = new Utility();
		
		IThongtindathang obj = new Thongtindathang();
		String id = util.antiSQLInspection(request.getParameter("id"));
		if (id != null) obj.setId(id);
		
		obj.setCtyId(ctyId);
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		obj.setUserId(userId);
			
		id = util.antiSQLInspection(request.getParameter("id"));
		if (id!=null) obj.setId(id);
			
		String isMua = util.antiSQLInspection(request.getParameter("isMua"));
		if(isMua == null) isMua = "0";
		obj.setIsMua(isMua);
		
		String spId = util.antiSQLInspection(request.getParameter("spId"));
		if (spId == null)
			spId = "";
		if(spId.length() == 0){
			obj.setMsg("Vui lòng chọn sản phẩm") ;
			obj.createRs();
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			String nextJSP = "";
			
			if(id != null){
				nextJSP = "/TraphacoHYERP/pages/Erp/ThongtindathangUpdate.jsp";
			}else{
				nextJSP = "/TraphacoHYERP/pages/Erp/ThongtindathangNew.jsp";
			}
			response.sendRedirect(nextJSP);
			return;
		}
		obj.setSpId(spId);

		if(isMua.equals("1")){
			String nccId = util.antiSQLInspection(request.getParameter("nccId"));
			if(nccId.length() == 0) nccId = null;
			obj.setNccId(nccId);
		
			String mou = util.antiSQLInspection(request.getParameter("mou"));
			if (mou == null)
				mou = "";
			obj.setMOU(mou);
		
			String leadtime = util.antiSQLInspection(request.getParameter("leadtime"));
			if (leadtime == null)
				leadtime = "";
			obj.setLeadtime(leadtime);
			
		}else{
			String lotsize = util.antiSQLInspection(request.getParameter("lotsize"));
			if(lotsize == null) lotsize = "0";
			obj.setLotsize(lotsize);
		
			String thoigiansx = util.antiSQLInspection(request.getParameter("thoigiansx"));
			if (thoigiansx == null)
				thoigiansx = "0";
			obj.setThoigianSX(thoigiansx);
		
			String cleanup = util.antiSQLInspection(request.getParameter("cleanup"));
			if (cleanup == null)
				cleanup = "0";
			obj.setCleanUp(cleanup);

			String nmId = util.antiSQLInspection(request.getParameter("nmId"));
			if (nmId == null)
				nmId = "";
			obj.setNhamayId(nmId);
			
			String[] dtsxIds = request.getParameterValues("DtsxIds");
			String[] dtsxIds_ss = request.getParameterValues("DtsxIds_ss");
			String tmp = "";
			String tgsx = "";
			String tgsx_ss = "";
			
			if(dtsxIds != null){
				for(int i = 0; i < dtsxIds.length; i++){
					if(util.antiSQLInspection(request.getParameter("dt_" + dtsxIds[i])) != null){
						if(tmp.length() == 0){
							tmp = dtsxIds[i];
						}else{
							tmp = tmp + "," + dtsxIds[i];
						}

						if(util.antiSQLInspection(request.getParameter("tgsx_" + dtsxIds[i])) != null){
							if(tgsx.length() == 0){
								tgsx = (util.antiSQLInspection(request.getParameter("tgsx_" + dtsxIds[i])).length() == 0?"0":util.antiSQLInspection(request.getParameter("tgsx_" + dtsxIds[i])));
							}else{
								tgsx = tgsx + "," + (util.antiSQLInspection(request.getParameter("tgsx_" + dtsxIds[i])).length() == 0?"0":util.antiSQLInspection(request.getParameter("tgsx_" + dtsxIds[i])));
							}
						}

					}
					

				}
			}
			obj.setDtsxIds_Selected(tmp.split(","));
			obj.setTgsx_Selected(tgsx.split(","));
			
			tmp = "";
			if(dtsxIds_ss != null){
				for(int i = 0; i < dtsxIds_ss.length; i++){
					if(util.antiSQLInspection(request.getParameter("dt_ss_" + dtsxIds_ss[i])) != null){
						if(tmp.length() == 0){
							tmp = dtsxIds[i];
						}else{
							tmp = tmp + "," + dtsxIds[i];
						}

						if(util.antiSQLInspection(request.getParameter("tgsx_ss_" + dtsxIds_ss[i])) != null){
							if(tgsx_ss.length() == 0){
								tgsx_ss = (util.antiSQLInspection(request.getParameter("tgsx_ss_" + dtsxIds_ss[i])).length() == 0?"0":util.antiSQLInspection(request.getParameter("tgsx_ss_" + dtsxIds_ss[i])));
							}else{
								tgsx_ss = tgsx_ss + "," + (util.antiSQLInspection(request.getParameter("tgsx_ss_" + dtsxIds_ss[i])).length() == 0?"0":util.antiSQLInspection(request.getParameter("tgsx_ss_" + dtsxIds_ss[i])));
							}
						}

					}
					

				}
			}
			obj.setDtsxIds_SS_Selected(tmp.split(","));
			obj.setTgsx_SS_Selected(tgsx_ss.split(","));
		}
		
		String action = request.getParameter("action");
		if(action == null) action = "";
		
		if(action.equals("save"))
		{
			if(id == null)
			{
				if (!(obj.createThongtindathang()))
				{
					obj.createRs();
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);

					String nextJSP = "/TraphacoHYERP/pages/Erp/ThongtindathangNew.jsp";
					response.sendRedirect(nextJSP);
					return;
				}
				else
				{
					IThongtindathangList obj1 = new ThongtindathangList();
					obj1.setUserId(userId);
					obj1.setCtyId(ctyId);
					obj1.init("");
					
					session.setAttribute("obj", obj1);

					String nextJSP = "/TraphacoHYERP/pages/Erp/Thongtindathang.jsp";
					response.sendRedirect(nextJSP);
					return;
				}
			}
			else
			{
				if (!(obj.updateThongtindathang()))
				{
					obj.createRs();
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);

					String nextJSP = "/TraphacoHYERP/pages/Erp/ThongtindathangUpdate.jsp";
					response.sendRedirect(nextJSP);
					return;
				}
				else
				{
					IThongtindathangList obj1 = new ThongtindathangList();
					obj1.setUserId(userId);
					obj1.setCtyId(ctyId);
					obj1.init("");

					session.setAttribute("obj", obj1);

					String nextJSP = "/TraphacoHYERP/pages/Erp/Thongtindathang.jsp";
					response.sendRedirect(nextJSP);
					return;
				}
			}
		}else{

			obj.createRs();
			session.setAttribute("userId", userId);
			session.setAttribute("obj", obj);
	
			String nextJSP = "/TraphacoHYERP/pages/Erp/ThongtindathangNew.jsp";
	
			if( id != null )
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ThongtindathangUpdate.jsp";
			}
	
			response.sendRedirect(nextJSP);
			
			return;
		}
	}
	
	private String getId(String str) 
	{
		String id = "";
		String tmp = "";
		if (str!=null) if (str.length()>0 && str.contains("&")) tmp = str.split("&")[1];
		if (tmp!=null) if (tmp.length()>0 && tmp.contains("=")) id = tmp.split("=")[1];
		return id;
	}
	
	private String getFilename(String str) 
	{
		String fn = "";
		String tmp = "";
		if (str!=null) if (str.length()>0 && str.contains("&")) tmp = str.split("&")[1];
		if (tmp!=null) if (tmp.length()>0 && tmp.contains("=")) fn = tmp.split("=")[1];
		return fn;
	}
}
