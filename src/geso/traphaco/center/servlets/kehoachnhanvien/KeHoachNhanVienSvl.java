package geso.traphaco.center.servlets.kehoachnhanvien;

import geso.traphaco.center.beans.kehoachnhanvien.*;
import geso.traphaco.center.beans.kehoachnhanvien.imp.*;
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


public class KeHoachNhanVienSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
	
    public KeHoachNhanVienSvl()
    {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IKeHoachNhanVienList obj;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
	    obj = new KeHoachNhanVienList();
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = request.getParameter("userId");
	    obj.setUserId(userId);
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String khnvId = util.getId(querystring);

	    if (action.equals("delete")) {
	    	obj.delete(khnvId);
	    } else if(action.equals("duyet")) {
	    	obj.duyet(khnvId);
	    } else if(action.equals("moduyet")) {
	    	obj.moduyet(khnvId);
	    }
	    
	    obj.init("");
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Center/KeHoachNhanVien.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IKeHoachNhanVienList obj;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out = response.getWriter();
	    obj = new KeHoachNhanVienList();
		HttpSession session = request.getSession();
	    String userId = request.getParameter("userId");
	    obj.setUserId(userId);
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    if (action.equals("new")) {
	    	obj.closeDB();
	    	// Empty Bean for distributor
	    	IKeHoachNhanVien khnvBean = (IKeHoachNhanVien) new KeHoachNhanVien(userId, "");
	    	khnvBean.init();
	    	khnvBean.createRs();
	    	// Save Data into session
	    	session.setAttribute("khnvBean", khnvBean);
    		
    		String nextJSP = "/TraphacoHYERP/pages/Center/KeHoachNhanVienNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else if (action.equals("search"))
	    {
	    	String loai = request.getParameter("Loai");
	    	if (loai == null)
	    		loai = "";
	    	obj.setLoai(loai);
	    	
	    	String thang = request.getParameter("Thang");
	    	if (thang == null)
	    		thang = "";    	
	    	obj.setThang(thang);
	    	
	    	String nam = request.getParameter("Nam");
	    	if (nam == null)
	    		nam = "";
	    	obj.setNam(nam);
	    	
	    	
	    	String search = obj.getSearchQuery();
	    		
	    	obj.init(search);
			obj.setUserId(userId);
			
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Center/KeHoachNhanVien.jsp");	    	
	    }
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
      
        return dateFormat.format(date);
	}
	
	private String getDateTime(String pattern) 
	{
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
      
        return dateFormat.format(date);
	}

}
