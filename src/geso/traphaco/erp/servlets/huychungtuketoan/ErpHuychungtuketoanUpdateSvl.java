package geso.traphaco.erp.servlets.huychungtuketoan;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.huychungtuketoan.IErpHuychungtuketoan;
import geso.traphaco.erp.beans.huychungtuketoan.IErpHuychungtuketoanList;
import geso.traphaco.erp.beans.huychungtuketoan.imp.ErpHuychungtuketoan;
import geso.traphaco.erp.beans.huychungtuketoan.imp.ErpHuychungtuketoanList;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpHuychungtuketoanUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpHuychungtuketoanUpdateSvl() {
        super();
    }

    PrintWriter out;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			session.setMaxInactiveInterval(30000);
		
			this.out = response.getWriter();
			Utility util = new Utility();
			
	    	String querystring = request.getQueryString();
		    userId = util.getUserId(querystring);
		    
		    out.println(userId);
		    
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId"));
		    
		    String id = util.getId(querystring);  	
			IErpHuychungtuketoan hctmhBean = new ErpHuychungtuketoan(id);
			hctmhBean.setCongtyId((String)session.getAttribute("congtyId"));
	        hctmhBean.setUserId(userId); //phai co UserId truoc khi Init
	        hctmhBean.init();
	    	
	        String nextJSP;
	        if(request.getQueryString().indexOf("display") >= 0 ) 
	        {
	        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuychungtuketoanDisplay.jsp";
	        }
	        else
	        {
	        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuychungtuketoanUpdate.jsp";
	        }
	        
	        session.setAttribute("hctmhBean", hctmhBean);
	        response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen"); 
		
		session.setAttribute("userId", userId);
		session.setAttribute("userTen", userTen);
		
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
			
			session.setMaxInactiveInterval(30000);
			
			this.out = response.getWriter();
			IErpHuychungtuketoan hctmhBean;
			
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			
		    if(id == null)
		    {  	
		    	hctmhBean = new ErpHuychungtuketoan("");
		    }
		    else
		    {
		    	hctmhBean = new ErpHuychungtuketoan(id);
		    }
	
		    hctmhBean.setCongtyId((String)session.getAttribute("congtyId"));
		    hctmhBean.setUserId(userId);
			
		    String sochungtu = util.antiSQLInspection(request.getParameter("sochungtu"));
			if (sochungtu == null)
				sochungtu = "";		
	    	hctmhBean.setSochungtu(sochungtu);
	    	
	    	String sochungtugoc = util.antiSQLInspection(request.getParameter("sochungtugoc"));
			if (sochungtugoc == null)
				sochungtugoc = "";		
	    	hctmhBean.setSochungtugoc(sochungtugoc);
		    	
	    	String sothanhtoan = util.antiSQLInspection(request.getParameter("sothanhtoan"));
	    	if(sothanhtoan == null)
	    		sothanhtoan = "";
	    	hctmhBean.setSoThanhtoan(sothanhtoan);
	    	
	    	String dienGiaiCT = util.antiSQLInspection(request.getParameter("dienGiaiCT"));
	    	if(dienGiaiCT == null)
	    		dienGiaiCT = "";
	    	hctmhBean.setDienGiaiCT(dienGiaiCT);
	    	
	    	String sohoadon = util.antiSQLInspection(request.getParameter("sohoadon"));
	    	if(sohoadon == null)
	    		sohoadon = "";
	    	hctmhBean.setSoHoadon(sohoadon);
	    	
	    	String sonhapkho = util.antiSQLInspection(request.getParameter("sonhapkho"));
	    	if(sonhapkho == null)
	    		sonhapkho = "";
	    	hctmhBean.setSoPhieunhapkho(sonhapkho);
	    	
	    	String sonhanhang = util.antiSQLInspection(request.getParameter("sonhanhang"));
	    	if(sonhanhang == null)
	    		sonhanhang = "";
	    	hctmhBean.setSoPhieunhanhang(sonhanhang);
	    	
	     	String ngayghinhan = util.antiSQLInspection(request.getParameter("ngayghinhan"));
	    	if(ngayghinhan == null)
	    		ngayghinhan = "";
	    	hctmhBean.setNgayghinhan(ngayghinhan);
	    	
	    	String somuahang = util.antiSQLInspection(request.getParameter("somuahang"));
	    	if(somuahang == null)
	    		somuahang = "";
	    	hctmhBean.setSoDonmuahang(somuahang);
	    		    	
	    	String loaichungtuId = util.antiSQLInspection(request.getParameter("loaict"));
	    	if(loaichungtuId == null)
	    		loaichungtuId = "";
	    	hctmhBean.setLoaiCTId(loaichungtuId);
	    	
	    	String[] soct = request.getParameterValues("sochungtuhuy");
	    	String[] thutu = request.getParameterValues("thutu");
	    	String[] ngaychungtu = request.getParameterValues("ngaychungtu");
	    	String[] trangthai = request.getParameterValues("trangthai");
	    	String[] ngaytao = request.getParameterValues("ngaytao");
	    	String[] loaichungtu = request.getParameterValues("loaichungtu");
	    	String[] sochungtuhuy = request.getParameterValues("sochungtuhuy");
	    	String[] sotienthanhtoan = request.getParameterValues("sotientt");	    	
	    	
			String action = request.getParameter("action");
			if(action.equals("save"))
			{
				if(id == null) //tao moi
				{
					if(!hctmhBean.createHct(sochungtuhuy, soct, ngaychungtu, trangthai, loaichungtu, ngaytao, thutu, sotienthanhtoan))
					{
						
	    		    	hctmhBean.createRs();
	    		    
	    		    	session.setAttribute("hctmhBean", hctmhBean);
	    		    	
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuychungtuketoanNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpHuychungtuketoanList obj = new ErpHuychungtuketoanList();
						obj.setCongtyId((String)session.getAttribute("congtyId"));
					    obj.setUserId(userId);
					    obj.init("");
					    hctmhBean.DbClose();
						session.setAttribute("obj", obj);
								
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuychungtuketoan.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				else
				{
					if(!hctmhBean.updateHct(sochungtuhuy, soct, ngaychungtu, trangthai, loaichungtu, ngaytao, thutu, sotienthanhtoan))
					{
						hctmhBean.createRs();
		    		    
						session.setAttribute("hctmhBean", hctmhBean);
						
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuychungtuketoanUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpHuychungtuketoanList obj = new ErpHuychungtuketoanList();
						obj.setCongtyId((String)session.getAttribute("congtyId"));
					    obj.setUserId(userId);
					    obj.init("");
					    hctmhBean.DbClose();
						session.setAttribute("obj", obj);
								
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuychungtuketoan.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
				hctmhBean.createRs();		
				String nextJSP;
				if (id == null){
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuychungtuketoanNew.jsp";
				}
				else{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuychungtuketoanUpdate.jsp";   						
				}
				
				session.setAttribute("hctmhBean", hctmhBean);
				response.sendRedirect(nextJSP);
			}
		}
	}

}
