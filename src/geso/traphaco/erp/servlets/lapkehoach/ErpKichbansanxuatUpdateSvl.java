package geso.erp.servlets.lapkehoach;


import geso.erp.beans.lapkehoach.IErpKichbansanxuat;
import geso.erp.beans.lapkehoach.IErpKichbansanxuatList;
import geso.erp.beans.lapkehoach.imp.ErpKichbansanxuat;
import geso.erp.beans.lapkehoach.imp.ErpKichbansanxuatList;
import geso.dms.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ErpKichbansanxuatUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
       
    public ErpKichbansanxuatUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IErpKichbansanxuat kbsxBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	   
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    String id = util.getId(querystring);
	   
	    kbsxBean = new ErpKichbansanxuat(id);
	    kbsxBean.setCtyId(ctyId);
	    kbsxBean.setId(id);
	    kbsxBean.setUserId(userId);
	    
	    kbsxBean.init();
        session.setAttribute("kbsxBean", kbsxBean);
        
        String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKichBanSanXuatUpdate.jsp";
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IErpKichbansanxuat kbsxBean;

		Utility util = new Utility();
	    String id = util.antiSQLInspection(request.getParameter("id"));
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    if(id == null){  	
	    	kbsxBean = new ErpKichbansanxuat();
	    }else{
	    	kbsxBean = new ErpKichbansanxuat(id);
	    }
	    	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		kbsxBean.setUserId(userId);	       
		
		kbsxBean.setCtyId(ctyId);
		
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null)
			trangthai = "0";
		kbsxBean.setr
		
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		kbsxBean.setDiengiai(diengiai);
		
		String spIds = util.antiSQLInspection(request.getParameter("spIds"));
		if (spIds == null)
			spIds = "";
		kbsxBean.setSpId(spIds);   
		
		String dcIds = util.antiSQLInspection(request.getParameter("dcIds"));
		if (dcIds == null)
			dcIds = "";
		kbsxBean.setDaychuyenId(dcIds);
		
		String bomIds = util.antiSQLInspection(request.getParameter("bomIds"));
		if (bomIds == null)
			bomIds = "";
		kbsxBean.setBomId(bomIds);
			
		
 		String action = request.getParameter("action");
 		if(action.equals("save"))
 		{
 			if(id == null)
 			{
	 			if (!(kbsxBean.createKichbansanxuat()))
				{
	 				kbsxBean.createRs();
	 				session.setAttribute("kbsxBean", kbsxBean);  	
	 	    		session.setAttribute("userId", userId);
	 			
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKichBanSanXuatNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IErpKichbansanxuatList obj = new ErpKichbansanxuatList();
					obj.setCtyId(ctyId);
					obj.init("");
	
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKichBanSanXuat.jsp";
					response.sendRedirect(nextJSP);
				}
 			}
 			else
 			{
 				if (!(kbsxBean.updateKichbansanxuat()))
				{
 					kbsxBean.createRs();
	 				session.setAttribute("kbsxBean", kbsxBean);  	
	 	    		session.setAttribute("userId", userId);
	 			
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKichBanSanXuatUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IErpKichbansanxuatList obj = new ErpKichbansanxuatList();
					obj.setCtyId(ctyId);
					obj.init("");
	
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKichBanSanXuat.jsp";
					response.sendRedirect(nextJSP);
				}
 			}
	    }
		else
		{
			kbsxBean.createRs();
			
			if(action.equals("changeBOM"))
			{
				kbsxBean.changeBom();
			}
			
			session.setAttribute("userId", userId);
			session.setAttribute("kbsxBean", kbsxBean);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKichBanSanXuatNew.jsp";
			
			if(id != null)
			{
				 nextJSP = "/TraphacoHYERP/pages/Erp/ErpKichBanSanXuatUpdate.jsp";
			}
			
			response.sendRedirect(nextJSP);
		}		
	}
	
	
}
