package geso.traphaco.erp.servlets.lapkehoach;

import geso.traphaco.erp.beans.lapkehoach.IErpLenhsanxuatdk;
import geso.traphaco.erp.beans.lapkehoach.IErpLenhsanxuatdkList;
import geso.traphaco.erp.beans.lapkehoach.imp.ErpLenhsanxuatdk;
import geso.traphaco.erp.beans.lapkehoach.imp.ErpLenhsanxuatdkList;
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


public class ErpLenhsanxuatdukienUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
       
    public ErpLenhsanxuatdukienUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String ctyId = (String)session.getAttribute("congtyId");
		IErpLenhsanxuatdk lsxBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);
	    
	    System.out.println("Id is :" + id);
	    
	    String action = util.getAction(querystring);
	   
	    lsxBean = new ErpLenhsanxuatdk();
	    lsxBean.setCtyId(ctyId);
	    
	    lsxBean.setId(id);
	    lsxBean.setUserId(userId);
	    
	    lsxBean.init();
        session.setAttribute("lsxBean", lsxBean);
        
        String nextJSP;
        if(action.equals("display")){
        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpLenhSanXuatDuKienDisplay.jsp";
        }else{
        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpLenhSanXuatDuKienUpdate.jsp";
        }
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String ctyId = (String)session.getAttribute("congtyId");
		
		IErpLenhsanxuatdk lsxBean;

		Utility util = new Utility();
	    String Id = util.antiSQLInspection(request.getParameter("id"));	
	    
	    lsxBean = new ErpLenhsanxuatdk();
	    lsxBean.setCtyId(ctyId);
	    
	    lsxBean.setId(Id);
	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		lsxBean.setUserId(userId);	       
		
		String soluong = util.antiSQLInspection(request.getParameter("soluong"));
		lsxBean.setSoluong(soluong);
		
//		String ngay = util.antiSQLInspection(request.getParameter("ngay"));
//		lsxBean.setNgay(ngay);
		
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if(trangthai == null) trangthai = "2";
		lsxBean.setTrangthai(trangthai);
		
 		String action = request.getParameter("action");
 		if(action.equals("save"))
 		{	
 			if(!lsxBean.update()){
 			    lsxBean.init();
 		        session.setAttribute("lsxBean", lsxBean);
 		        
 		        String nextJSP;
	        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpLenhSanXuatDuKienUpdate.jsp";
 		        response.sendRedirect(nextJSP);
 				
 			}else{
 			    IErpLenhsanxuatdkList obj = new ErpLenhsanxuatdkList();
 			    obj.setCtyId(ctyId);
 			    obj.setUserId(userId);
 			
 			    obj.init();
 				session.setAttribute("obj", obj);
 			    
 			    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLenhSanXuatDuKien.jsp";
 				response.sendRedirect(nextJSP);
 			}
    		
	    }
		else
		{
			lsxBean.init();
			session.setAttribute("userId", userId);
			session.setAttribute("lsxBean", lsxBean);
			
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLenhSanXuatDuKienDisplay.jsp";
			
			response.sendRedirect(nextJSP);
		}		
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
