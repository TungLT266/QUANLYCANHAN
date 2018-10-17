package geso.traphaco.distributor.servlets.dieuchinhtonkho;

import geso.traphaco.distributor.beans.dieuchinhtonkho.IDieuchinhtonkho;
import geso.traphaco.distributor.beans.dieuchinhtonkho.imp.Dieuchinhtonkho;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DieuchinhtonkhoDisplaySvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DieuchinhtonkhoDisplaySvl() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
//		PrintWriter out = response.getWriter();
		String nextJSP;
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	        
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String id = util.getId(querystring);  	
	    String action = util.getAction(querystring);

		IDieuchinhtonkho dctkBean = (IDieuchinhtonkho) new Dieuchinhtonkho();
		dctkBean.setUserId(userId);
		dctkBean.setId(id);
		dctkBean.setNppId(util.antiSQLInspection(request.getParameter("nppId")));
    	dctkBean.initDisplay();
    	session.setAttribute("dctkBean", dctkBean);
    	nextJSP = "/TraphacoHYERP/pages/Distributor/DieuChinhTonKhoDisplay.jsp";
    	response.sendRedirect(nextJSP);    
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
