package geso.erp.servlets.lapkehoach;

import geso.erp.beans.lapkehoach.IErpKehoachnguyenlieu;
import geso.erp.beans.lapkehoach.IErpKehoachnguyenlieuList;
import geso.erp.beans.lapkehoach.imp.ErpKehoachnguyenlieu;
import geso.erp.beans.lapkehoach.imp.ErpKehoachnguyenlieuList;
import geso.dms.center.util.Utility;
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


public class ErpKehoachnguyenlieuUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
       
    public ErpKehoachnguyenlieuUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String ctyId = (String)session.getAttribute("congtyId");
		
		IErpKehoachnguyenlieu khnlBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);
	   
	    khnlBean = new ErpKehoachnguyenlieu(id);
	    khnlBean.setCtyId(ctyId);
	    khnlBean.setId(id);
	    khnlBean.setUserId(userId);
	    
	    khnlBean.init();
        session.setAttribute("khnlBean", khnlBean);
        
        String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKeHoachNguyenLieuDisplay.jsp";
        
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String ctyId = (String)session.getAttribute("congtyId");
		
		IErpKehoachnguyenlieu khnlBean;

		Utility util = new Utility();
	    String id = util.antiSQLInspection(request.getParameter("id"));	
	    if(id == null){  	
	    	khnlBean = new ErpKehoachnguyenlieu("");
	    }else{
	    	khnlBean = new ErpKehoachnguyenlieu(id);
	    }
	    
	    khnlBean.setCtyId(ctyId);
	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		khnlBean.setUserId(userId);	       
    			
		String ngaylap = util.antiSQLInspection(request.getParameter("ngaylap"));
		
		if (ngaylap == null || ngaylap.trim().length() < 10)
			ngaylap = getDateTime();
		khnlBean.setNgaykehoach(ngaylap);
		System.out.println("___Ngay ke hoach: " + ngaylap);
		
		String loaiId = util.antiSQLInspection(request.getParameter("loaiId"));
		if (loaiId == null)
			loaiId = "";
		khnlBean.setLoaiId(loaiId);
		
			
		String[] thang = request.getParameterValues("thang");
		if(thang != null) khnlBean.setThang(thang);
				
    	String[] spIds = request.getParameterValues("spIds");
    	if(spIds != null)
    	{
    		String str = "";
    		for(int i = 0; i < spIds.length; i++ )
    			str += spIds[i] + ",";
    		if(str.length() > 0)
    			str = str.substring(0, str.length() - 1);
    		khnlBean.setSpIds(str);
    	}

    	
 		String action = request.getParameter("action");
 		if(action.equals("thuchien"))
 		{
 			if(thang == null){
 				khnlBean.setMsg("Vui lòng chọn tháng để chạy kế hoạch mua nguyên liệu");
 				khnlBean.setUserId(userId);
 		   		khnlBean.createRs();
 		   		session.setAttribute("userId", userId);
 		   		session.setAttribute("khnlBean", khnlBean);
 		    				
 		   		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKeHoachNguyenLieuNew.jsp";
 		   		response.sendRedirect(nextJSP);
 		   		return;
 			}
 			
 			if(spIds == null){
 				khnlBean.setMsg("Vui lòng chọn sản phẩm để chạy kế hoạch mua nguyên liệu");
 				khnlBean.setUserId(userId);
 		   		khnlBean.createRs();
 		   		session.setAttribute("userId", userId);
 		   		session.setAttribute("khnlBean", khnlBean);
 		    				
 		   		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKeHoachNguyenLieuNew.jsp";
 		   		response.sendRedirect(nextJSP);
 		   		return;
 			}

 			if (!khnlBean.createKehoach())
   			{
   		    	khnlBean.setUserId(userId);
   		    	khnlBean.createRs();
   		    	session.setAttribute("userId", userId);
   				session.setAttribute("khnlBean", khnlBean);
    				
   				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKeHoachNguyenLieuNew.jsp";
   				response.sendRedirect(nextJSP);
   			}
   			else
   			{
   				IErpKehoachnguyenlieuList obj = new ErpKehoachnguyenlieuList();
   				obj.setCtyId(ctyId);
   				obj.setUserId(userId);
   				obj.init("");
    				
   				session.setAttribute("obj", obj);
   				session.setAttribute("userId", userId);
		    		
   				response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpKeHoachNVL.jsp");	    	
    		}    		
	    }
		else
		{

				khnlBean.createRs();
				session.setAttribute("userId", userId);
				session.setAttribute("khnlBean", khnlBean);
				
				String nextJSP;
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpKeHoachNguyenLieuNew.jsp";
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
