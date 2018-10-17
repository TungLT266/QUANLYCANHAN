package geso.traphaco.erp.servlets.cauhinhchiphi;

import geso.traphaco.center.util.Utility;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.cauhinhchiphi.IErpCauhinhchiphi;
import geso.traphaco.erp.beans.cauhinhchiphi.IErpCauhinhchiphiList;
import geso.traphaco.erp.beans.cauhinhchiphi.imp.ErpCauhinhchiphi;
import geso.traphaco.erp.beans.cauhinhchiphi.imp.ErpCauhinhchiphiList;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpCauhinhchiphiSvl extends HttpServlet {
	static final long serialVersionUID = 1L;
		
	
	public ErpCauhinhchiphiSvl() {
		super();
	}   	
	 int tranghientai=1;
	 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();
	    
	    Utility util = new Utility();    
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	  
	    if (userId.length()==0)
	    	userId = request.getParameter("userId");
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    	    
	    String id = util.getId(querystring).split(";")[0];
	      
	    String ctyId = (String)session.getAttribute("congtyId"); 
	    
	    IErpCauhinhchiphiList obj = (IErpCauhinhchiphiList) new ErpCauhinhchiphiList();
	    obj.setUserId(userId);
	    obj.setCtyId(ctyId);
	    
	    String chixem = request.getParameter("chixem");
	    obj.setChixem(chixem);
	    
	    if (action.equals("delete")){	   		
		 	Delete(id, obj);
	    }
	  
	    obj.init();
	    session.setAttribute("obj", obj);
	    session.setAttribute("userId", userId);
			
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCauHinhChiPhi.jsp";
	    response.sendRedirect(nextJSP);
		
	}  	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    HttpSession session = request.getSession();
	    
	    IErpCauhinhchiphiList obj = (IErpCauhinhchiphiList) new ErpCauhinhchiphiList();
	    String userId = request.getParameter("userId");
	    obj.setUserId(userId);
	    String ctyId = (String)session.getAttribute("congtyId");
	    String action = request.getParameter("action");
	    
	    String chixem = request.getParameter("chixem");
	    obj.setChixem(chixem);
	    
	    // Perform searching. Each Business Unit is saved into Donvikinhdoanh
	    if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    {
	    	//obj = new ErpHoaDon();
	    	
	    	obj.getReqParam(request); 
	    	obj.setUserId(userId);
	    	obj.setCtyId(ctyId);
	     	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
	     	tranghientai = Integer.parseInt(request.getParameter("nxtApprSplitting"));
        	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	
	    	// Saving data into session
			session.setAttribute("obj", obj);
	 		session.setAttribute("userId", userId);
	 		
	 		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpCauHinhChiPhi.jsp");
	    }
	    else if ("search".equals(action)){
	    	obj.setUserId(userId);
	    	obj.setCtyId(ctyId);
	    	obj.getReqParam(request);
	    	// Saving data into session
			session.setAttribute("obj", obj);
	 		session.setAttribute("userId", userId);
	 		
	 		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpCauHinhChiPhi.jsp");
	    	
	    }
	    else
	    // Create a new Business Unit
	    if ("new".equals(action)){

	    	IErpCauhinhchiphi TTDTBean = new ErpCauhinhchiphi();
	    	TTDTBean.setUserId(userId);
	    	TTDTBean.setCtyId(ctyId);
	    	TTDTBean.setTrangthai("1");
	 
	    	TTDTBean.createRs();
	    	
	    	session.setAttribute("TTDTBean", TTDTBean);
	    	session.setAttribute("userId", userId);
 		
	    	String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCauHinhChiPhiNew.jsp";
	    	response.sendRedirect(nextJSP);
	    
	    }

	}

	private void Delete(String id, IErpCauhinhchiphiList obj)
	{
		dbutils db = new dbutils();			    
	    // Cap nhat lai trạng thái cho 
		String	sql = 	"UPDATE GROUP_TAIKHOAN_NHOM SET TRANGTHAI = 0 WHERE PK_SEQ = "+ id +" " ;				
		if(!db.update(sql))
		{
			obj.setMsg("Không thể xóa nhóm tài khoản này ");
		}
				
				   	
	}
	    
	
}
