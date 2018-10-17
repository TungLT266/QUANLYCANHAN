package geso.traphaco.erp.servlets.cauhinhchiphi;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.cauhinhchiphi.IErpCauhinhchiphi;
import geso.traphaco.erp.beans.cauhinhchiphi.imp.ErpCauhinhchiphi;
import geso.traphaco.erp.beans.cauhinhchiphi.IErpCauhinhchiphiList;
import geso.traphaco.erp.beans.cauhinhchiphi.imp.ErpCauhinhchiphiList;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpCauhinhchiphiUpdateSvl extends HttpServlet
{
	
	private static final long serialVersionUID = 1L;
	
	public ErpCauhinhchiphiUpdateSvl() {
	    super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	
	    Utility util = new Utility();
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();
	
	    String querystring = request.getQueryString();
	    
	    String userId = util.getUserId(querystring);
	    System.out.println("UserId: " + userId);
	    
	    if (userId.length()==0)
	    	userId = request.getParameter("userId");
	    
	    String Id = util.getId(querystring);
	    System.out.println(Id);
	    
	    String ctyId = (String)session.getAttribute("congtyId");
	    IErpCauhinhchiphi TTDTBean = new ErpCauhinhchiphi();
	    TTDTBean.setUserId(userId);
	    TTDTBean.setCtyId(ctyId);
	    TTDTBean.setId(Id);   	    
		TTDTBean.init();
		    
		// Data is saved into session
		session.setAttribute("TTDTBean", TTDTBean);
		session.setAttribute("userId", userId);
	
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCauHinhChiPhiUpdate.jsp";
		if(querystring.contains("display"))
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpCauHinhChiPhiDisplay.jsp";
		response.sendRedirect(nextJSP);
	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    boolean err = false;
	    
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();
	    Utility util = new Utility();
	    
	    String ctyId = (String)session.getAttribute("congtyId");
	    IErpCauhinhchiphi TTDTBean = new ErpCauhinhchiphi();
	    TTDTBean.setCtyId(ctyId);
	    
	    String Id = util.antiSQLInspection(request.getParameter("TTDTId"));
	    TTDTBean.setId(Id);
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));
	    TTDTBean.setUserId(userId);
	        
	    
	    String ma = util.antiSQLInspection(request.getParameter("ma"));
	    if(ma.length()==0)
	    {
	    	err = true;
	    	if(TTDTBean.getMsg().length() == 0) 
	    		TTDTBean.setMsg("Vui lòng nhập Mã của nhóm chi phí" );	    	
	    }else{
	    	TTDTBean.setMa(ma);
	    }
	    
	    String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
	    if(diengiai == null) diengiai = ""; 	 
	
	    if(diengiai.length()==0){
	    	err = true;
	    	if(TTDTBean.getMsg().length() == 0) 
	    		TTDTBean.setMsg("Vui lòng nhập Diễn giải của nhóm chi phí" );	    	
	    }else{
	    	TTDTBean.setDiengiai(diengiai);
	    }
	
	    String trangthai = util.antiSQLInspection(request.getParameter("TrangThai"));
	    if(trangthai == null) trangthai = "0";
    	TTDTBean.setTrangthai(trangthai);
    	    	
    	String timkiem = util.antiSQLInspection(request.getParameter("timkiem"));
 	    if(timkiem == null) timkiem = "";
     	TTDTBean.setTimkiem(timkiem);

    	String groupCP = request.getParameter("groupCP");
	    if(groupCP == null)
	    	groupCP = "";
	    TTDTBean.setGroupCpIds(groupCP);
	    
	    String[] khoanmucIds = request.getParameterValues("khoanmucIds");
	    if(khoanmucIds != null)
	    {
	    	String kmIds = "";
	    	for(int  i = 0; i < khoanmucIds.length; i++)
	    	{
	    		kmIds += khoanmucIds[i] + ",";
	    	}
	    	
	    	if(kmIds.trim().length() > 0)
	    	{
	    		kmIds = kmIds.substring(0, kmIds.length() - 1);
	    		TTDTBean.setKhoanmucCpIds(kmIds);
	    	}
	    }
	    
	    String action = request.getParameter("action");
	   
	    if(Id.length()>0) {
	    	if(err){
	    		TTDTBean.createRs();
	       	    
				// Data is saved into session
				session.setAttribute("TTDTBean", TTDTBean);
				session.setAttribute("userId", userId);
	
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCauHinhChiPhiUpdate.jsp";
				response.sendRedirect(nextJSP);
	    		
	    	}else{
	    		if(action.equals("save")){
	    			if(!TTDTBean.Update()){
	    				
	    				TTDTBean.createRs();
	    				
	    				// Data is saved into session
	    				session.setAttribute("TTDTBean", TTDTBean);
	    				session.setAttribute("userId", userId);
	
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCauHinhChiPhiUpdate.jsp";
	    				response.sendRedirect(nextJSP);
	    		
	    			}else{
	    				IErpCauhinhchiphiList obj = (IErpCauhinhchiphiList) new ErpCauhinhchiphiList();
	    				obj.setCtyId(ctyId);
	    				obj.setUserId(userId);
	    				obj.init();
	    				session.setAttribute("obj", obj);
	    				session.setAttribute("userId", userId);
	    			    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCauHinhChiPhi.jsp";
	    				
	    				response.sendRedirect(nextJSP);	    		
	    			}
	    		}
	    		else{
	    			TTDTBean.createRs();
	    			session.setAttribute("TTDTBean", TTDTBean);
	    			session.setAttribute("userId", userId);
	
	    			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCauHinhChiPhiUpdate.jsp";
	    			response.sendRedirect(nextJSP);
	    			
	    		}
	    	}
	    }else{
	    	if(err)
	    	{
	    		TTDTBean.createRs();
	    		
	    		session.setAttribute("TTDTBean", TTDTBean);
				session.setAttribute("userId", userId);
	
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCauHinhChiPhiNew.jsp";
				response.sendRedirect(nextJSP);
	    		
	    	}else{
	    		if(action.equals("save"))
	    		{
	    			if(!TTDTBean.New())
	    			{
	    				
	    				TTDTBean.createRs();
	    				session.setAttribute("TTDTBean", TTDTBean);
	    				session.setAttribute("userId", userId);
	
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCauHinhChiPhiNew.jsp";
	    				response.sendRedirect(nextJSP);
	    		
	    			}else{
	    				IErpCauhinhchiphiList obj = (IErpCauhinhchiphiList) new ErpCauhinhchiphiList();
	    				obj.setCtyId(ctyId);
	    				obj.setUserId(userId);
	    				obj.init();
	    				session.setAttribute("obj", obj);
	    				session.setAttribute("userId", userId);
	    			    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCauHinhChiPhi.jsp";
	    				
	    				response.sendRedirect(nextJSP);	    		
	    			}
	    		}	    		
	    		else{
	    			
	    			TTDTBean.createRs();
	    			
	    			session.setAttribute("TTDTBean", TTDTBean);
	    			session.setAttribute("userId", userId);
	
	    			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpCauHinhChiPhiNew.jsp";
	    			response.sendRedirect(nextJSP);
	    			
	    		}
	
	    	}
	    }
	    
	}

}
