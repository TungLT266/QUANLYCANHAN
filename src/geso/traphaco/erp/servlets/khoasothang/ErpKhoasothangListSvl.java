package geso.traphaco.erp.servlets.khoasothang;

import geso.dms.center.util.Utility;
import geso.traphaco.erp.beans.khoasothang.IErpKhoasothang;
import geso.traphaco.erp.beans.khoasothang.IErpkhoasothanglist;
 
import geso.traphaco.erp.beans.khoasothang.imp.ErpKhoaSoThang;
import geso.traphaco.erp.beans.khoasothang.imp.ErpKhoasothanglist;
 

import java.io.IOException;
 

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpKhoasothangListSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
    public ErpKhoasothangListSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IErpkhoasothanglist obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    String Id=util.getId(querystring);
	    
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	   
	    obj = new ErpKhoasothanglist();
	    obj.setUserId(userId);
	    obj.setId(Id);
	    String task = request.getParameter("task");
	    if(task == null)
	    	task = "";
	    
	    String nextJSP = "";
	    
	    String action = request.getParameter("action");
	    if(action == null)
	    	action = "";
	    
	    if(action.equals("")){
	    	action=util.getAction(querystring);
	    }
	    if(action.equals("Unchot")){
	    	obj.setId(Id);
	    	boolean bien=	obj.MoKhoaSoKho();
	    
	    }
	     
	    obj.Init();
		nextJSP = "/TraphacoHYERP/pages/Erp/ErpKhoasothanglist.jsp";

	    session.setAttribute("obj", obj);
	    response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpkhoasothanglist obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	   
	    System.out.println("userId"+userId);
	    obj = new ErpKhoasothanglist();
	    obj.setUserId(userId);
	    
	    String thang = util.antiSQLInspection(request.getParameter("thang"));
	    if(thang == null)
	    	thang = "0";
	    obj.setThang(Integer.parseInt(thang));
	    
	    String nam = util.antiSQLInspection(request.getParameter("nam"));
	    if(nam == null)
	    	nam = "0";
	    obj.setNam(Integer.parseInt(nam));
	    
	    
	    String action = request.getParameter("action");
	    if(action == null)
	    	action = "";
	    
	    String msg = "";
	    String nextJSP = "";
	    
	    if(action.equals("kskt"))
	    {
 	    }
	    else if(action.equals("taomoi"))
	    {
			IErpKhoasothang Obj =new ErpKhoaSoThang();
			Obj.setUserId(userId);
			Obj.setCtyId((String)session.getAttribute("congtyId"));
			 
			Obj.init();
			Obj.Init_kiemtradulieu("");
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpKhoaSoThang.jsp";
			 session.setAttribute("obj", Obj);
			response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	 
	    }
	    
	   
	   
	}

}
