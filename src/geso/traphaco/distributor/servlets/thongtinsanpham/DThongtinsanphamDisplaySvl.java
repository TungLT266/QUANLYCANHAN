package geso.traphaco.distributor.servlets.thongtinsanpham;

import geso.traphaco.distributor.beans.thongtinsanpham.IThongtinsanpham;
import geso.traphaco.distributor.beans.thongtinsanpham.imp.Thongtinsanpham;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class DThongtinsanphamDisplaySvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PrintWriter out;  
	
    public DThongtinsanphamDisplaySvl() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		System.out.println("userId la: " + userId);
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = request.getParameter("userId");
	    
	    String id = util.getId(querystring);  	

	    IThongtinsanpham spBean = new Thongtinsanpham(userId);
	    spBean.setId(id);
        spBean.setUserId(userId);
        spBean.init();
        session.setAttribute("spBean", spBean);
        String nextJSP = "/TraphacoHYERP/pages/Distributor/ThongTinSanPhamDisplay.jsp";
        response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
