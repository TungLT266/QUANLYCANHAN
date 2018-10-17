package geso.traphaco.center.servlets.Packsize;
import geso.traphaco.center.beans.packsize.imp.*;
import geso.traphaco.center.beans.packsize.*;
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

public class PacksizeUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private PrintWriter out;  

    public PacksizeUpdateSvl() 
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

	    IPacksize kbhBean = new Packsize(id);	    
	    
        kbhBean.setUserId(userId);
        session.setAttribute("kbhBean", kbhBean);
        String nextJSP = "/TraphacoHYERP/pages/Center/PacksizeUpdate.jsp";
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		IPacksize kbhBean;
		this.out = response.getWriter();
		
		Utility util = new Utility();
		
	    String id =  util.antiSQLInspection(request.getParameter("id"));
	    if(id == null){  	
	    	kbhBean = new Packsize("");
	    }else{
	    	kbhBean = new Packsize(id);
	    }
	    
	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		kbhBean.setUserId(userId);
	    
    	String kenhbanhang = util.antiSQLInspection(request.getParameter("kenhbanhang"));
		if (kenhbanhang == null)
			kenhbanhang = "";				
    	kbhBean.setKenhbanhang(kenhbanhang);
    	
    	String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		kbhBean.setDiengiai(diengiai);

		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
    	if (trangthai == null)
    		trangthai = "0";
    	else
    		trangthai = "1";
    	kbhBean.setTrangthai(trangthai);
    	
		String ngaysua = getDateTime();
    	kbhBean.setNgaysua(ngaysua);
		
		String nguoisua = userId;
		kbhBean.setNguoisua(nguoisua);
    		
		boolean error = false;		
		if (kenhbanhang.trim().length()== 0){
			kbhBean.setMessage("Vui lòng nhập tên Packsize");
			error = true;
		}

		
 		
 		String action = request.getParameter("action");
	    
		if(action.equals("save"))
		{
			if ( id == null){
				if (!(kbhBean.CreateKbh())){				
					session.setAttribute("kbhBean", kbhBean);
					kbhBean.setUserId(userId);
					
					String nextJSP = "/TraphacoHYERP/pages/Center/PacksizeNew.jsp";
					response.sendRedirect(nextJSP);
				}else{
					IPacksizeList obj = new PacksizeList();
					obj.setUserId(userId);
					obj.init("");
					session.setAttribute("obj", obj);
						
					String nextJSP = "/TraphacoHYERP/pages/Center/Packsize.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
				
			}else{
				if (!(kbhBean.UpdateKbh())){			
					session.setAttribute("kbhBean", kbhBean);
					String nextJSP = "/TraphacoHYERP/pages/Center/PacksizeUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else{
					IPacksizeList obj = new PacksizeList();
					obj.setUserId(userId);
					obj.init("");
					session.setAttribute("obj", obj);
					String nextJSP = "/TraphacoHYERP/pages/Center/Packsize.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
			}
		}else{
			kbhBean.setUserId(userId);
			session.setAttribute("kbhBean", kbhBean);
			
			String nextJSP;
			if (id == null){			
				nextJSP = "/TraphacoHYERP/pages/Center/PacksizeNew.jsp";
			}else{
				nextJSP = "/TraphacoHYERP/pages/Center/PacksizeUpdate.jsp";   						
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
