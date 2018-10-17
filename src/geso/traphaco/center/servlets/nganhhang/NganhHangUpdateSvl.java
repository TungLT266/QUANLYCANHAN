package geso.traphaco.center.servlets.nganhhang;
import geso.traphaco.center.beans.nganhhang.*;
import geso.traphaco.center.beans.nganhhang.imp.*;
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

public class NganhHangUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private PrintWriter out;  

    public NganhHangUpdateSvl() 
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

	    INganhHang nhBean = new NganhHang(id);	    
	    
        nhBean.setUserId(userId);
        session.setAttribute("nhBean", nhBean);
        String nextJSP = "/TraphacoHYERP/pages/Center/NganhHangUpdate.jsp";
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		INganhHang nhBean;
		this.out = response.getWriter();
		
		Utility util = new Utility();
		
	    String id =  util.antiSQLInspection(request.getParameter("id"));
	    if(id == null){  	
	    	nhBean = new NganhHang("");
	    }else{
	    	nhBean = new NganhHang(id);
	    }
	    
	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		nhBean.setUserId(userId);
    	System.out.println("[NganhHangUpdateSvl.doPost] userId = " + nhBean.getUserId());
	    
    	String ten = util.antiSQLInspection(request.getParameter("ten"));
		if (ten == null)
			ten = "";				
    	nhBean.setTen(ten.trim());
    	System.out.println("[NganhHangUpdateSvl.doPost] ten = " + nhBean.getTen());
    	
    	String thuexuatStr = util.antiSQLInspection(request.getParameter("thuexuat"));
    	if (thuexuatStr == null)
    		thuexuatStr = "0";
    	nhBean.setThuexuat(Double.parseDouble(thuexuatStr));
    	
    	System.out.println("[NganhHangUpdateSvl.doPost] ten = " + nhBean.getTen());
    	
    	
    	String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		nhBean.setDiengiai(diengiai.trim());
    	System.out.println("[NganhHangUpdateSvl.doPost] diengiai = " + nhBean.getDiengiai());
		
		String dvkdId = util.antiSQLInspection(request.getParameter("dvkd"));
    	if ( dvkdId == null) { dvkdId = ""; }
    	nhBean.setDvkdId(dvkdId.trim());
    	System.out.println("[NganhHangUpdateSvl.doPost] dvkdId = " + nhBean.getDvkdId());

		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
    	if (trangthai == null)
    		trangthai = "0";
    	nhBean.setTrangthai(trangthai.trim());
    	System.out.println("[NganhHangUpdateSvl.doPost] trangthai = " + nhBean.getTrangthai());
    	
		String ngaysua = getDateTime();
    	nhBean.setNgaysua(ngaysua);
    	System.out.println("[NganhHangUpdateSvl.doPost] ngaysua = " + nhBean.getNgaysua());
		
		String nguoisua = userId;
		nhBean.setNguoisua(nguoisua);
    	System.out.println("[NganhHangUpdateSvl.doPost] nguoisua = " + nhBean.getNguoisua());
 		
 		String action = request.getParameter("action");
	    
		if(action.equals("save"))
		{
			if ( id == null) {
				if (!(nhBean.create())){				
					session.setAttribute("nhBean", nhBean);
					nhBean.setUserId(userId);
					
					String nextJSP = "/TraphacoHYERP/pages/Center/NganhHangNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					INganhHangList obj = new NganhHangList();
					obj.setUserId(userId);
					obj.init("");
					session.setAttribute("obj", obj);
						
					String nextJSP = "/TraphacoHYERP/pages/Center/NganhHang.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
				
			} else {
				if (!(nhBean.update())){			
					session.setAttribute("nhBean", nhBean);
					String nextJSP = "/TraphacoHYERP/pages/Center/NganhHangUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else {
					INganhHangList obj = new NganhHangList();
					obj.setUserId(userId);
					obj.init("");
					session.setAttribute("obj", obj);
					String nextJSP = "/TraphacoHYERP/pages/Center/NganhHang.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
			}
		} 
		else 
		{
			nhBean.setUserId(userId);
			session.setAttribute("nhBean", nhBean);
			
			String nextJSP;
			if (id == null){			
				nextJSP = "/TraphacoHYERP/pages/Center/NganhHangNew.jsp";
			}else{
				nextJSP = "/TraphacoHYERP/pages/Center/NganhHangUpdate.jsp";   						
			}
			response.sendRedirect(nextJSP);
			
		}
	}
	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
