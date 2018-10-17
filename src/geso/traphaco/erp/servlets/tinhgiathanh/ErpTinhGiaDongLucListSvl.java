package geso.traphaco.erp.servlets.tinhgiathanh;
import geso.traphaco.erp.beans.khoasothang.IErptinhgiadongluc;
import geso.traphaco.erp.beans.khoasothang.IErptinhgiadongluclist;
import geso.traphaco.erp.beans.khoasothang.imp.Erptinhgiadongluc;
import geso.traphaco.erp.beans.khoasothang.imp.Erptinhgiadongluclist;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 

public class ErpTinhGiaDongLucListSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public ErpTinhGiaDongLucListSvl() {
        super(); 
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    HttpSession session = request.getSession();	
	    Utility util = new Utility();
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    IErptinhgiadongluclist obj = new Erptinhgiadongluclist();
	    String Id = util.getId(querystring);
	    String msg="";
	    if(action.equals("duyet")){
	    	msg=obj.Duyet(Id);
	    }else if(action.equals("huybo")){
	    	msg=obj.huybo(Id);
	    }else if(action.equals("unduyet")){
	    	msg=obj.Boduyet(Id);
	    }
 
	    obj.setUserId(userId);
	    obj.setMsg(msg);
	    obj.Init();
	    
		session.setAttribute("obj", obj);
	    String nextJSP = "/TraphacoERP/pages/Erp/ErpTinhGiaDongLucList.jsp";
		response.sendRedirect(nextJSP);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	  	    
	    HttpSession session = request.getSession();	
	    	
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));     
		String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    String thang =  request.getParameter("thang");
	    if (thang == null || thang.equals("")){
	    	thang = "0";
	    }
	    
	    String nam = request.getParameter("nam");
	    if (nam == null  || nam.equals("")){
	    	nam = "0";
	    }
	 
	    IErptinhgiadongluclist obj = new Erptinhgiadongluclist();
	    obj.setThang( Integer.parseInt(thang));
	    obj.setNam( Integer.parseInt(nam));
	    obj.setUserId(userId);
	    obj.Init();
	    
	    
	    
	   if(action.equals("taomoi")){
		   	IErptinhgiadongluc bean=new Erptinhgiadongluc();
		   	bean.Init();
		   	bean.setUserId(userId);
		    session.setAttribute("obj", bean);
		    obj.DbClose();
		    String nextJSP = "/TraphacoERP/pages/Erp/ErpTinhgiadongluc.jsp";
			response.sendRedirect(nextJSP);
	   }else{
		   
		    
		    obj.setUserId(userId);
		    session.setAttribute("obj", obj);
		    
		    String nextJSP = "/TraphacoERP/pages/Erp/ErpTinhGiaDongLucList.jsp";
			response.sendRedirect(nextJSP);
	    
	    
	   }
	     
	   
	   
	}
 
}
