package geso.traphaco.center.servlets.quanhuyen;
import geso.traphaco.center.beans.quanhuyen.IQuanHuyen;
import geso.traphaco.center.beans.quanhuyen.IQuanHuyenList;
import geso.traphaco.center.beans.quanhuyen.imp.QuanHuyen;
import geso.traphaco.center.beans.quanhuyen.imp.QuanHuyenList;
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

public class QuanHuyenUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PrintWriter out;  

    public QuanHuyenUpdateSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String id = util.getId(querystring);  	

	    IQuanHuyen kvBean = new QuanHuyen(id);
	    
        kvBean.setUserId(userId);
        session.setAttribute("kvBean", kvBean);
        String nextJSP = "/TraphacoHYERP/pages/Center/QuanHuyenUpdate.jsp";
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		IQuanHuyen kvBean;
		this.out = response.getWriter();
		
		Utility util = new Utility();
		
	    String id =  util.antiSQLInspection(request.getParameter("id"));
	    if(id == null){  	
	    	kvBean = new QuanHuyen("");
	    }else{
	    	kvBean = new QuanHuyen(id);
	    }
	    
	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		kvBean.setUserId(userId);
	    
		String ttId = util.antiSQLInspection(request.getParameter("tinhthanh"));
		if (ttId == null)
			ttId = "";				
    	kvBean.setTinhthanhId(ttId);
    	
    	/*String qhId = util.antiSQLInspection(request.getParameter("quanhuyen"));
		if (qhId == null)
			qhId = "";				
    	kvBean.setQuanhuyenId(qhId);*/
    	
    	String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
    	System.out.println("Diengiai : "+diengiai);
		if (diengiai == null)
			diengiai = "";
		kvBean.setDiengiai(diengiai);

		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
    	if (trangthai == null)
    		trangthai = "0";
    	else
    		trangthai = "1";
    	kvBean.setTrangthai(trangthai);

		String ngaysua = getDateTime();
    	kvBean.setNgaysua(ngaysua);
		
		String nguoisua = userId;
		kvBean.setNguoisua(nguoisua);

 		String action = request.getParameter("action");
	    
	    	if(action.equals("save"))
	    	{
	    		if (!kiemtraNhap(ttId, diengiai, kvBean)){
		    		if ( id == null){
		    			if (!(kvBean.CreateQh())){				
		    				session.setAttribute("kvBean", kvBean);
		    				kvBean.setUserId(userId);
		    				String nextJSP = "/TraphacoHYERP/pages/Center/QuanHuyenNew.jsp";
		    				response.sendRedirect(nextJSP);
		    			}else{
		    				IQuanHuyenList obj = new QuanHuyenList();
		    				obj.setUserId(userId);
		    				session.setAttribute("obj", obj);
							
		    				String nextJSP = "/TraphacoHYERP/pages/Center/QuanHuyen.jsp";
		    				response.sendRedirect(nextJSP);			    			    									
		    			}
					
		    		}else{
		    			if (!(kvBean.UpdateQh())){			
		    				session.setAttribute("kvBean", kvBean);
		    				String nextJSP = "/TraphacoHYERP/pages/Center/QuanHuyenUpdate.jsp";
		    				response.sendRedirect(nextJSP);
		    			}
		    			else{
		    				IQuanHuyenList obj = new QuanHuyenList();
		    				obj.setUserId(userId);
		    				session.setAttribute("obj", obj);
							
		    				String nextJSP = "/TraphacoHYERP/pages/Center/QuanHuyen.jsp";
		    				response.sendRedirect(nextJSP);			    			    									
		    			}
		    		}
	    	}else{
	    		kvBean.setUserId(userId);
	    		session.setAttribute("kvBean", kvBean);
			
	    		String nextJSP;
	    		if (id == null){			
	    			nextJSP = "/TraphacoHYERP/pages/Center/QuanHuyenNew.jsp";
	    		}else{
	    			nextJSP = "/TraphacoHYERP/pages/Center/QuanHuyenUpdate.jsp";   						
	    		}
	    		response.sendRedirect(nextJSP);
			
	    	}
	    }else{
    		kvBean.setUserId(userId);
    		kvBean.createRS();
    		session.setAttribute("kvBean", kvBean);
		
    		String nextJSP;
    		if (id == null){			
    			nextJSP = "/TraphacoHYERP/pages/Center/QuanHuyenNew.jsp";
    		}else{
    			nextJSP = "/TraphacoHYERP/pages/Center/QuanHuyenUpdate.jsp";   						
    		}
    		response.sendRedirect(nextJSP);
	    	
	    }
	}
	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private boolean kiemtraNhap(String ttId, String diengiai, IQuanHuyen kvBean)
	{
		boolean error = false;
		
		if (ttId.trim().length()== 0){
			kvBean.setMessage("Vui lòng chọn thông tin tỉnh thành.");
			error = true;
		}
		
		/*if (qhId.trim().length()== 0){
			kvBean.setMessage("Vui lòng chọn thông tin quận huyện.");
			error = true;
		}*/

		if (diengiai.trim().length()== 0){
			kvBean.setMessage("Vui lòng nhập đầy đủ thông tin.");
			error = true;
		}
		
		return error;
	}

}
