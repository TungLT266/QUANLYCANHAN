package geso.traphaco.erp.servlets.dulieutonghop;
import geso.traphaco.erp.beans.dulieutonghop.IDulieutonghop;
import geso.traphaco.erp.beans.dulieutonghop.imp.Dulieutonghop;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class Erp_DulieutonghopSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Erp_DulieutonghopSvl() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    Utility util=new Utility();
	    HttpSession session = request.getSession();
	    String ctyId = (String)session.getAttribute("congtyId");
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    //out.println(userId);
	    
	    if (userId.length()==0){
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    }
	    	
	    IDulieutonghop dlthBean = new Dulieutonghop();
	    dlthBean.SetUserId(userId);
	    dlthBean.setCtyId(ctyId);
	    dlthBean.init();
   	    
		// Data is saved into session
		session.setAttribute("dlthBean", dlthBean);
		String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_Dulieutonghop.jsp";
   		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");

	    Utility util = new Utility();
	    
	    HttpSession session = request.getSession();
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    String dlthId = util.antiSQLInspection(request.getParameter("dlthId"));
	    String ltkId = util.antiSQLInspection(request.getParameter("ltkId"));
	    String[] tkIds = request.getParameterValues("tkIds");
	    String action = request.getParameter("action"); 
	
	    String userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    IDulieutonghop dlthBean = new Dulieutonghop();
	    dlthBean.SetUserId(userId);
	    dlthBean.setCtyId(ctyId);
	    dlthBean.setDlthId(dlthId);
	    dlthBean.setLtkId(ltkId);
	    dlthBean.setTkIds(tkIds);
	    dlthBean.setRequest(request);
   	    
   	    if(action.equals("luulai")){
   	    	if(dlthId != null){    		
   	    		dlthBean.Save();
   	    	}
   	    }
   	    
   	    dlthBean.init();
		// Data is saved into session
		session.setAttribute("dlthBean", dlthBean);
//		session.setAttribute("userId", userId);

		String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_Dulieutonghop.jsp";
   		response.sendRedirect(nextJSP);

	}

}
