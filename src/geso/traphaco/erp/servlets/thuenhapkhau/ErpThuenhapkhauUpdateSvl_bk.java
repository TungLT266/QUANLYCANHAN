package geso.traphaco.erp.servlets.thuenhapkhau;

import geso.traphaco.erp.beans.thuenhapkhau.IErpThuenhapkhau;
import geso.traphaco.erp.beans.thuenhapkhau.IErpThuenhapkhauList;
import geso.traphaco.erp.beans.thuenhapkhau.imp.ErpThuenhapkhau;
import geso.traphaco.erp.beans.thuenhapkhau.imp.ErpThuenhapkhauList;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpThuenhapkhauUpdateSvl_bk extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
       
    public ErpThuenhapkhauUpdateSvl_bk() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IErpThuenhapkhau tnkBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);
	   
	    tnkBean = new ErpThuenhapkhau(id);
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    tnkBean.setCongtyId(ctyId);
	    tnkBean.setId(id);
	    tnkBean.setUserId(userId);
	    
	    tnkBean.init();
        session.setAttribute("tnkBean", tnkBean);
        
        String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThueNhapKhauUpdate.jsp";
        if(querystring.indexOf("display") >= 0)
        {
        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpThueNhapKhauDisplay.jsp";
        }else if(querystring.indexOf("updateVAT") >= 0){
        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpThueNhapKhauUpdateVAT.jsp";
        }
        
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IErpThuenhapkhau tnkBean;

		Utility util = new Utility();
	    String id = util.antiSQLInspection(request.getParameter("Id"));	
	    System.out.println("Id: " + id);
	    
	    if(id == null){  	
	    	tnkBean = new ErpThuenhapkhau();
	    }else{
	    	tnkBean = new ErpThuenhapkhau(id);
	    }
	    	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		tnkBean.setUserId(userId);	       
		
		String ctyId = (String)session.getAttribute("congtyId");
		tnkBean.setCongtyId(ctyId);
				
		String sochungtu = util.antiSQLInspection(request.getParameter("sochungtu"));
		if (sochungtu == null)
			sochungtu = "";
		tnkBean.setSochungtu(sochungtu);
		
		String ngaychungtu = util.antiSQLInspection(request.getParameter("ngaychungtu"));
		if (ngaychungtu == null)
			ngaychungtu = "";
		tnkBean.setNgaychungtu(ngaychungtu);
		
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		tnkBean.setDiengiai(diengiai);
		
		String ngaynhap = util.antiSQLInspection(request.getParameter("ngaynhap"));
		if (ngaynhap == null)
			ngaynhap = "";
		tnkBean.setNgaynhap(ngaynhap);
		
		String cqt = util.antiSQLInspection(request.getParameter("cqt"));
		if (cqt == null)
			cqt = "";
		tnkBean.setCqt(cqt);

		String cqtId = util.antiSQLInspection(request.getParameter("cqtId"));
		if (cqtId == null)
			cqtId = "";
		tnkBean.setCqtId(cqtId);

		String ncc = util.antiSQLInspection(request.getParameter("ncc"));
		if (ncc == null)
			ncc = "";
		tnkBean.setNcc(ncc);

		String nccId = util.antiSQLInspection(request.getParameter("nccId"));
		if (nccId == null)
			nccId = "";
		tnkBean.setNccId(nccId);

		System.out.println(nccId);
		
		String pnkId = util.antiSQLInspection(request.getParameter("pnkId"));
		if (pnkId == null)
			pnkId = "";
		tnkBean.setPnkId(pnkId);

		String ptVAT = util.antiSQLInspection(request.getParameter("ptVAT"));
		if (ptVAT == null)
			ptVAT = "";
		tnkBean.setPtVAT(ptVAT);

		String VAT = util.antiSQLInspection(request.getParameter("VAT").replace(",", ""));
		if (VAT == null)
			VAT = "";
		tnkBean.setVAT(VAT);
		
		String[] spId = request.getParameterValues("spId");
		tnkBean.setSpId(spId);
		
		String[] soluong = request.getParameterValues("soluong");
		tnkBean.setSoluong(soluong);
		
		String[] dongia = request.getParameterValues("dongia");
		tnkBean.setDongia(dongia);
		
		String[] thuesuat = request.getParameterValues("thuesuat");
		tnkBean.setThuesuat(thuesuat);
		
		String[] thue = request.getParameterValues("thue");
		tnkBean.setThue(thue);
		
 		String action = request.getParameter("action");
 		if(action.equals("save"))
 		{
 			if(id == null)
 			{
	 			if (!(tnkBean.Create()))
				{
	 				tnkBean.createRs();
	 				session.setAttribute("tnkBean", tnkBean);  	
	 	    		session.setAttribute("userId", userId);
	 			
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThueNhapKhauNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IErpThuenhapkhauList obj = new ErpThuenhapkhauList();
					obj.setCongtyId(ctyId);
					obj.setUserId(userId);
					obj.init("");
	
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThueNhapKhau.jsp";
					response.sendRedirect(nextJSP);
				}
 			}
 			else
 			{
 				if (!(tnkBean.Update()))
				{
 					tnkBean.createRs();
	 				session.setAttribute("tnkBean", tnkBean);  	
	 	    		session.setAttribute("userId", userId);
	 			
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThueNhapKhauUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IErpThuenhapkhauList obj = new ErpThuenhapkhauList();
					obj.setCongtyId(ctyId);
					obj.setUserId(userId);
					obj.init("");
	
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThueNhapKhau.jsp";
					response.sendRedirect(nextJSP);
				}
 			}
	    }else if(action.equals("saveVAT")){
			if (!(tnkBean.UpdateVAT()))
			{
					tnkBean.createRs();
 				session.setAttribute("tnkBean", tnkBean);  	
 	    		session.setAttribute("userId", userId);
 			
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThueNhapKhauUpdateVAT.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				IErpThuenhapkhauList obj = new ErpThuenhapkhauList();
				obj.setCongtyId(ctyId);
				obj.setUserId(userId);
				obj.init("");

				session.setAttribute("obj", obj);
			    
			    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThueNhapKhau.jsp";
				response.sendRedirect(nextJSP);
			}	    	
	    }
		else
		{
			tnkBean.createRs();
			session.setAttribute("userId", userId);
			session.setAttribute("tnkBean", tnkBean);
			
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThueNhapKhauNew.jsp";
			
			if( id != null )
			{
				 nextJSP = "/TraphacoHYERP/pages/Erp/ErpThueNhapKhauUpdate.jsp";
			}
			
			response.sendRedirect(nextJSP);
		}		
	}
	
	
}
