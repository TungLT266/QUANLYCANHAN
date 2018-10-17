package geso.traphaco.center.servlets.tieuchithuong;

import geso.traphaco.center.beans.tieuchithuong.IDuyetbandunggia;
import geso.traphaco.center.beans.tieuchithuong.IDuyetbandunggiaList;
import geso.traphaco.center.beans.tieuchithuong.imp.Duyetbandunggia;
import geso.traphaco.center.beans.tieuchithuong.imp.DuyetbandunggiaList;
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

public class DuyetbandunggiaUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
	
    public DuyetbandunggiaUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		IDuyetbandunggia tctskuBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);
	   
	    tctskuBean = new Duyetbandunggia(id);
	    tctskuBean.setId(id);
	    tctskuBean.setUserId(userId);
	    
        tctskuBean.init();
        session.setAttribute("tctskuBean", tctskuBean);
        
        String nextJSP = "/TraphacoHYERP/pages/Center/DuyetBanDungGiaUpdate.jsp";
        if(querystring.indexOf("display") >= 0)
        	nextJSP = "/TraphacoHYERP/pages/Center/DuyetBanDungGiaDisplay.jsp";
        
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IDuyetbandunggia tctskuBean;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		this.out = response.getWriter();
		Utility util = new Utility();
		System.out.println("______________da vao day");
	   	String id = util.antiSQLInspection(request.getParameter("id"));
	    if(id == null){  	
	    	tctskuBean = new Duyetbandunggia("");
	    }else{
	    	tctskuBean = new Duyetbandunggia(id);
	    }
	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		tctskuBean.setUserId(userId);
		
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		tctskuBean.setDiengiai(diengiai);
		
		String thang = util.antiSQLInspection(request.getParameter("thang"));
		System.out.println("______________thang la +" + thang);
		if (thang == null)
			thang = Integer.toString(Integer.parseInt(getDateTime().split("-")[1]));
		tctskuBean.setThang(thang);
		
		String loaict = util.antiSQLInspection(request.getParameter("loaict"));
		if (loaict == null)
			loaict = "0";
		tctskuBean.setLoaict(loaict);
		
		String quy = util.antiSQLInspection(request.getParameter("quy"));
		if (quy == null)
			quy = getQuyHienTai();
		tctskuBean.setQuy(quy);
		
		System.out.println("______________quy la +" + quy);

		String nam = util.antiSQLInspection(request.getParameter("nam"));
		if (nam == null)
			nam = "";
		tctskuBean.setNam(nam);
		System.out.println("______________nam la +" + nam);
		String[] nppIds = request.getParameterValues("nppIds");
		if(nppIds != null)
		{
			String nppId = "";
			for(int i = 0; i < nppIds.length; i++)
			{
				nppId += nppIds[i] + ",";
			}
			
			if(nppId.trim().length() > 0)
			{
				nppId = nppId.substring(0, nppId.length() - 1);
				tctskuBean.setNppIds(nppId);
			}
		}
		
		String[] khIds = request.getParameterValues("khIds");
		if(khIds != null)
		{
			String khId = "";
			for(int i = 0; i < khIds.length; i++)
			{
				khId += khIds[i] + ",";
			}
			
			if(khId.trim().length() > 0)
			{
				khId = khId.substring(0, khId.length() - 1);
				tctskuBean.setKhIds(khId);
			}
		}
		
		//VUNG 
		String vungId = request.getParameter("vungId");
		if(vungId == null)
			vungId = "";
		tctskuBean.setVungIds(vungId);
		
		String kvId = request.getParameter("khuvucId");
		if(kvId == null)
			kvId = "";
		tctskuBean.setKhuvucIds(kvId);
		
 		String action = request.getParameter("action");
 		System.out.println("Action la: " + action);
 		
 		if(action.equals("save"))
 		{    
    		if (id == null )
    		{
    			if (!tctskuBean.createTctSKU())
    			{
    		    	tctskuBean.setUserId(userId);
    		    	
    		    	tctskuBean.createRs();
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("tctskuBean", tctskuBean);
    				
    				String nextJSP = "/TraphacoHYERP/pages/Center/DuyetBanDungGiaNew.jsp";
    				response.sendRedirect(nextJSP);
    			}
    			else
    			{
    				IDuyetbandunggiaList obj = new DuyetbandunggiaList();
				    obj.setUserId(userId);
				    
				    obj.init("");
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Center/DuyetBanDungGia.jsp";
					response.sendRedirect(nextJSP);
    			}	
    		}
    		else
    		{
    			if (!(tctskuBean.updateTctSKU()))
    			{			
    		    	tctskuBean.setUserId(userId);
    		    	
    		    	tctskuBean.createRs();
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("tctskuBean", tctskuBean);
    				
    				String nextJSP = "/TraphacoHYERP/pages/Center/DuyetBanDungGiaUpdate.jsp";
    				response.sendRedirect(nextJSP);
    			}
				else
				{
					IDuyetbandunggiaList obj = new DuyetbandunggiaList();
				    obj.setUserId(userId);
				    
				    obj.init("");
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Center/DuyetBanDungGia.jsp";
					response.sendRedirect(nextJSP);
				}
    		}
	    }
		else
		{		
			tctskuBean.createRs();
			session.setAttribute("userId", userId);
			session.setAttribute("tctskuBean", tctskuBean);
			
			String nextJSP;
			if (id == null){
				nextJSP = "/TraphacoHYERP/pages/Center/DuyetBanDungGiaNew.jsp";
			}
			else{
				nextJSP = "/TraphacoHYERP/pages/Center/DuyetBanDungGiaUpdate.jsp";   						
			}
			response.sendRedirect(nextJSP);
		}
	}
	
	public String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private String getQuyHienTai() 
	{
        int thang = Integer.parseInt(this.getDateTime().split("-")[1]);
        if(1 <= thang && thang <= 3)
        	return "1";
        else if(4 <= thang && thang <= 6)
        	return "2";
        else if(7 <= thang && thang <= 9)
        	return "3";
        else 
        	return "4";
	}

}
