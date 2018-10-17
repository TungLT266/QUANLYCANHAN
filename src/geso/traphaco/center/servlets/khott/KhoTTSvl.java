package geso.traphaco.center.servlets.khott;

import geso.traphaco.center.beans.khott.IKhoTT;
import geso.traphaco.center.beans.khott.imp.KhoTT;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class KhoTTSvl extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    public KhoTTSvl() {
    	
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Utility util=new Utility();
	       //HttpServletRequest request;
		
			request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    
		    PrintWriter out = response.getWriter();
		    HttpSession session = request.getSession();

		    String querystring = request.getQueryString();
				    
		    String userId = util.getUserId(querystring);
		    
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId"));
		    
		    String action = util.getAction(querystring);	    
		    String khoId = util.getId(querystring);
            
		    if (action.equals("delete")){	   		
			  //delete;
		    	 KhoTT obj = new KhoTT();
		    
		    	 obj.setId(khoId);
		    	 obj.Delete();
		    	 obj.setListkho("");
		    	 session.setAttribute("obj", obj);
		    	 session.setAttribute("userId", userId);
		    	 String nextJSP = "/TraphacoHYERP/pages/Center/KhoTT.jsp";
			     response.sendRedirect(nextJSP);
		    }
		    else if(action.equals("update")){
		    	 KhoTT obj = new KhoTT(khoId);
		    	 session.setAttribute("obj", obj);
		    	 session.setAttribute("userId", userId);
		    	 String nextJSP = "/TraphacoHYERP/pages/Center/KhoTTUpdate.jsp";
			    	response.sendRedirect(nextJSP);
		    }
		    else if(action.equals("display")){
		    	
		    }
		    else{
		    KhoTT obj = new KhoTT();
		    obj.setListkho("");
	    	//Data object is saved into session
	    	session.setAttribute("obj", obj);
			
	    	// userId is saved into session
	    	session.setAttribute("userId", userId);
			session.setAttribute("tungay", "");
			session.setAttribute("denngay", "");
	    	String nextJSP = "/TraphacoHYERP/pages/Center/KhoTT.jsp";
	    	response.sendRedirect(nextJSP);
		    }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Utility util=new Utility();
	       //HttpServletRequest request;
		
		request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
			HttpSession session = request.getSession();
		    //this.request = request;
		    KhoTT obj = new KhoTT();
		    String userId = util.antiSQLInspection(request.getParameter("userId"));
		    // Perform searching. Each Business Unit is saved into Donvikinhdoanh
		    if (request.getParameter("action").equals("search")){
		    	String search = getSearchQuery(obj, request);
//		    	out.println(search);
		    	obj.setListkho(search);
	    		// Saving data into session
	    		session.setAttribute("obj", obj);
	    		session.setAttribute("userId", userId);
	    		response.sendRedirect("/TraphacoHYERP/pages/Center/KhoTT.jsp");
		    }
		    // Create a new Business Unit
		    if (request.getParameter("action").equals("new")){
		    	// Empty Bean for distributor
		    	IKhoTT khoBean = new KhoTT();
		    
		    	// Save Data into session
	    		session.setAttribute("obj", khoBean);
	    		session.setAttribute("userId", userId);
		    	String nextJSP = "/TraphacoHYERP/pages/Center/KhoTTNew.jsp";
	    		response.sendRedirect(nextJSP);
		    }
	}
	private String getSearchQuery(KhoTT obj, ServletRequest request){
//	    PrintWriter out = response.getWriter();
		Utility util = new Utility();
		String ten = util.antiSQLInspection(request.getParameter("ten"));
    	if (ten == null)
    		ten = "";
    	obj.setTen(ten);
    	
    	String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));   	
    	if (trangthai == null)
    		trangthai = "";    	


    	obj.setTrangthai(trangthai);
    	
		String query=  "SELECT     K.PK_SEQ, K.TEN, K.NGAYTAO, K.NGAYSUA, K.TRANGTHAI, K.MA, NT.TEN AS NGUOITAO, NS.TEN AS NGUOISUA "+
					   " FROM         ERP_KHOTT K INNER JOIN  dbo.NHANVIEN NT ON K.NGUOITAO = NT.PK_SEQ INNER JOIN "+
			           " dbo.NHANVIEN AS NS ON K.NGUOISUA = NS.PK_SEQ WHERE  K.PK_SEQ > 0 ";
    	
    	if (ten.length()>0){
			query = query + " and upper(k.ten) like upper(N'%" + ten + "%')";
    	}
    		
    	if(trangthai.length() > 0){
    		query = query + " and k.trangthai = '" + trangthai + "'";
    	}
    	//System.out.print(query);
		return query;
	}
}
