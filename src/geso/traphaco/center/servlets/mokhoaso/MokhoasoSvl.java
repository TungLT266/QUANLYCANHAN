package geso.traphaco.center.servlets.mokhoaso;

import geso.traphaco.center.beans.mokhoaso.IMokhoaso;
import geso.traphaco.center.beans.mokhoaso.imp.Mokhoaso;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class MokhoasoSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MokhoasoSvl() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    Utility util = new Utility();
	    
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();
	    String querystring = request.getQueryString();
	    
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = request.getParameter("userId");
	    
		session.setAttribute("userId", userId);
	    IMokhoaso mksBean = new Mokhoaso();
	    mksBean.setVungId("");
	    mksBean.setKhuvucId("");
	    mksBean.setNppId("");
	    mksBean.init();
	    
	    session.setAttribute("mksBean", mksBean);
		String nextJSP = "/TraphacoHYERP/pages/Center/Mokhoaso.jsp";
		response.sendRedirect(nextJSP);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    Utility util = new Utility();
	    
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();
	    
	    String userId = request.getParameter("userId");	  
	    String vungId = request.getParameter("vungId");
	    String kvId = request.getParameter("kvId");
	    String nppId = request.getParameter("nppId");
	    
	    
	    
	    String action = request.getParameter("action");
	    
	    if (vungId == null) vungId = "";
	    if (kvId == null) kvId = "";
	    if (nppId == null) nppId = "";
	    
	    IMokhoaso mksBean = new Mokhoaso();
	    mksBean.setVungId(vungId);
	    mksBean.setKhuvucId(kvId);
	    mksBean.setNppId(nppId);
	    mksBean.setUserId(userId);
	    mksBean.init();
	    
	    if(action.equals("open"))
	    {
	    	if(nppId.length()> 0)
	    	{
	    		mksBean.setMsg(mksBean.MoKhoaSoNgay());
	    	}
	    	else
	    	{
	    		mksBean.setMsg("Vui lòng chọn Nhà phân phối");
	    	}
	    }

	    session.setAttribute("mksBean", mksBean);
    	String nextJSP = "/TraphacoHYERP/pages/Center/Mokhoaso.jsp";
		response.sendRedirect(nextJSP);

	}

}
