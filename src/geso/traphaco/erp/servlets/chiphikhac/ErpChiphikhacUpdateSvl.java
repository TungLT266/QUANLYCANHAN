package geso.traphaco.erp.servlets.chiphikhac;

import geso.traphaco.erp.beans.chiphikhac.IErpChiphikhac;
import geso.traphaco.erp.beans.chiphikhac.IErpChiphikhacList;
import geso.traphaco.erp.beans.chiphikhac.imp.ErpChiphikhac;
import geso.traphaco.erp.beans.chiphikhac.imp.ErpChiphikhacList;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpChiphikhacUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
       
    public ErpChiphikhacUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IErpChiphikhac cpkBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);
	    if(id == null) id = "";
	    
	    cpkBean = new ErpChiphikhac(id);
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    cpkBean.setCongtyId(ctyId);
	    cpkBean.setId(id);
	    cpkBean.setUserId(userId);
	    cpkBean.setnppdangnhap(util.getIdNhapp(userId));
	    
	    cpkBean.init();
        session.setAttribute("cpkBean", cpkBean);
        
        String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChiPhiKhacUpdate.jsp";
        if(querystring.indexOf("display") >= 0)
        {
        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpChiPhiKhacDisplay.jsp";
        }
        
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IErpChiphikhac cpkBean;

		Utility util = new Utility();
	    String id = util.antiSQLInspection(request.getParameter("Id"));	
	    if(id == null){  	
	    	cpkBean = new ErpChiphikhac();
	    }else{
	    	cpkBean = new ErpChiphikhac(id);
	    }
	    System.out.println("Id: " + id);
	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		cpkBean.setUserId(userId);	       
		
		String ctyId = (String)session.getAttribute("congtyId");
		cpkBean.setCongtyId(ctyId);

		cpkBean.setnppdangnhap(util.getIdNhapp(userId));
		
		String ngaynhap = util.antiSQLInspection(request.getParameter("ngaynhap"));
		if (ngaynhap == null)
			ngaynhap = "";
		cpkBean.setNgaynhap(ngaynhap);			
				
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		cpkBean.setDiengiai(diengiai);
		
		String loai = util.antiSQLInspection(request.getParameter("loai"));
		cpkBean.setLoai(loai);

		String ttId = util.antiSQLInspection(request.getParameter("tienteId"));
		if(ttId == null) ttId = "";
		cpkBean.setTienteId(ttId);
		
		if(loai.equals("0")){
			String nccId = util.antiSQLInspection(request.getParameter("nccId"));
			if (nccId == null)
				nccId = "";
			cpkBean.setNccId(nccId);
			cpkBean.setNvId("");
			
		}else{
			String nvId = util.antiSQLInspection(request.getParameter("nvId"));
			if (nvId == null)
				nvId = "";
			cpkBean.setNvId(nvId);
			cpkBean.setNccId("");
		}
		
		String[] chiphi = request.getParameterValues("chiphi");
		cpkBean.setChiphi(chiphi);
			
		String[] diengiaicp = request.getParameterValues("diengiaicp");
		cpkBean.setDiengiaicp(diengiaicp);

		String[] tongtien = request.getParameterValues("tienchuathue");
		cpkBean.setTongtien(tongtien);

		String[] tongthue = request.getParameterValues("thue");
		cpkBean.setTongthue(tongthue);

		String action = request.getParameter("action");
 		if(action.equals("save"))
 		{
 			if(id == null)
 			{
	 			if (!(cpkBean.Create(request)))
				{
	 				session.setAttribute("cpkBean", cpkBean);  	
	 	    		session.setAttribute("userId", userId);
	 			
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChiPhiKhacNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IErpChiphikhacList obj = new ErpChiphikhacList();
					obj.setCongtyId(ctyId);
					obj.setUserId(userId);
					obj.setnppdangnhap(util.getIdNhapp(userId));
					
					obj.init("");
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChiPhiKhac.jsp";
					response.sendRedirect(nextJSP);
				}
 			}
 			else
 			{
 				if (!(cpkBean.Update(request)))
				{
	 				session.setAttribute("cpkBean", cpkBean);  	
	 	    		session.setAttribute("userId", userId);
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChiPhiKhacUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IErpChiphikhacList obj = new ErpChiphikhacList();
					obj.setCongtyId(ctyId);
					obj.setUserId(userId);
					obj.setnppdangnhap(util.getIdNhapp(userId));
					obj.init("");
	
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChiPhiKhac.jsp";
					response.sendRedirect(nextJSP);
				}
 			}
	    }
		else			
		{
			System.out.println("I am here!");
			cpkBean.init();
			session.setAttribute("userId", userId);
			session.setAttribute("cpkBean", cpkBean);
			
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChiPhiKhacNew.jsp";
			
			if( id != null )
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpChiPhiKhacUpdate.jsp";
			}
			
			response.sendRedirect(nextJSP);
			return;
		}		
	}
	
	
}
