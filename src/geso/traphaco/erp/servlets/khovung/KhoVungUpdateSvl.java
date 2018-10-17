package geso.traphaco.erp.servlets.khovung;

import geso.traphaco.erp.beans.khovung.*;
import geso.traphaco.erp.beans.khovung.imp.*;
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

public class KhoVungUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	private PrintWriter out;  

    public KhoVungUpdateSvl() 
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
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String id = util.getId(querystring);  	

	    IKhoVung kvBean = (IKhoVung) new KhoVung(id);
	    kvBean.setCtyId(ctyId);
	    kvBean.init();

        kvBean.setUserId(userId);
        session.setAttribute("kvBean", kvBean);
        String nextJSP = "/TraphacoHYERP/pages/Erp/KhoVungUpdate.jsp";
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		IKhoVung kvBean;
		this.out = response.getWriter();
		Utility util = new Utility();
		
	    String id =  util.antiSQLInspection(request.getParameter("Id"));
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    if(id == null){  	
	    	kvBean = (IKhoVung) new KhoVung("");
	    }else{
	    	kvBean = (IKhoVung)new KhoVung(id);
	    }
	    
	    kvBean.setCtyId(ctyId);
	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		kvBean.setUserId(userId);
	    
    	String ma = util.antiSQLInspection(request.getParameter("ma"));
		if (ma == null)
			ma = "";				
    	kvBean.setMa(ma);
    	   	
    	String ten = util.antiSQLInspection(request.getParameter("ten"));
		if (ten == null)
			ten = "";
		kvBean.setTen(ten);

		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
    	if (trangthai == null)
    		trangthai = "0";
    	else
    		trangthai = "1";
    	kvBean.setTrangthai(trangthai);

		String ngaysua = getDateTime();
    	kvBean.setNgaysua(ngaysua);
		
		String nguoisua = userId;
		kvBean.setNguoisua(nguoisua);
    	
		
		boolean error = false;
				
		if (ma.trim().length()== 0){
			kvBean.setMessage("Vui lòng nhập Mã của Kho Vùng");
			error = true;
		}

		if (ten.trim().length()== 0){
			kvBean.setMessage("Vui lòng nhập Tên của Kho Vùng");
			error = true;
		}
 		
 		String action = request.getParameter("action");
	    
		if(action.equals("save"))
		{
			if (id == null){
				if (!(kvBean.CreateKhoVung())){				
					session.setAttribute("kvBean", kvBean);
					kvBean.setUserId(userId);
					String nextJSP = "/TraphacoHYERP/pages/Erp/KhoVungNew.jsp";
					response.sendRedirect(nextJSP);
				}else{
					IKhoVungList obj = (IKhoVungList) new KhoVungList();
					obj.setCtyId(ctyId);
					obj.setUserId(userId);
					obj.init("");	   
					session.setAttribute("obj", obj);
						
					String nextJSP = "/TraphacoHYERP/pages/Erp/KhoVung.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
				
			}else{
				if (!(kvBean.UpdateKhoVung())){			
					session.setAttribute("kvBean", kvBean);
					String nextJSP = "/TraphacoHYERP/pages/Erp/KhoVungUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else{
					IKhoVungList obj = (IKhoVungList) new KhoVungList();
					obj.setCtyId(ctyId);
					obj.setUserId(userId);
					obj.init("");	   
					session.setAttribute("obj", obj);
						
					String nextJSP = "/TraphacoHYERP/pages/Erp/KhoVung.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
			}
		}else{
			kvBean.setUserId(userId);
			session.setAttribute("kvBean", kvBean);
			
			String nextJSP;
			if (id == null){			
				nextJSP = "/TraphacoHYERP/pages/Erp/KhoVungNew.jsp";
			}else{
				nextJSP = "/TraphacoHYERP/pages/Erp/KhoVungUpdate.jsp";   						
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
