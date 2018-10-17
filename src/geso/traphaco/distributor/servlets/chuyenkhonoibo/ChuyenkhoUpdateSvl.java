package geso.traphaco.distributor.servlets.chuyenkho;

import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.chuyenkho.IChuyenkho;
import geso.traphaco.distributor.beans.chuyenkho.IChuyenkhoList;
import geso.traphaco.distributor.beans.chuyenkho.imp.Chuyenkho;
import geso.traphaco.distributor.beans.chuyenkho.imp.ChuyenkhoList;
import geso.traphaco.distributor.db.sql.dbutils;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChuyenkhoUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	IChuyenkho pxkBean;
	PrintWriter out; 
	dbutils db;
	
    public ChuyenkhoUpdateSvl() 
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
	    
	    if (userId.length() == 0)
	    	userId = request.getParameter("userId");
	    	    
	    String id = util.getId(querystring);  	

	    pxkBean = new Chuyenkho(id);
	    pxkBean.setUserId(userId);
	    pxkBean.init();
        
        String nextJSP = "";
        if(querystring.indexOf("display") > 0)
        {
        	nextJSP = "/TraphacoHYERP/pages/Distributor/ChuyenKhoDisplay.jsp";
        }
        else
        {
        	nextJSP = "/TraphacoHYERP/pages/Distributor/ChuyenKhoUpdate.jsp";
        }
        
        session.setAttribute("pxkBean", pxkBean);
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		this.out = response.getWriter();
		
	    String id = request.getParameter("id");	
	    if(id == null){  	
	    	pxkBean = new Chuyenkho("");
	    }else{
	    	pxkBean = new Chuyenkho(id);
	    }
	    	    
		String userId = request.getParameter("userId");
		pxkBean.setUserId(userId);
	        	
    	String nppId = request.getParameter("nppId");
		if (nppId == null)
			nppId = "";
		pxkBean.setNppId(nppId);
    	
		String ngaychuyen = request.getParameter("ngaychuyen");
		if (ngaychuyen == null || ngaychuyen.length() < 10)
			ngaychuyen = getDateTime();
		pxkBean.setNgaychuyen(ngaychuyen);
		
		String nvbhId = request.getParameter("nvbhId");
		if (nvbhId == null)
			nvbhId = "";
		pxkBean.setNvbhId(nvbhId);
		
		String khoId = request.getParameter("khoId");
		if (khoId == null)
			khoId = "";
		pxkBean.setKhoId(khoId);
		
		String[] spIds = request.getParameterValues("spIds");
		String[] soluong = request.getParameterValues("soluong");
		
		if(soluong != null)
		{
			Hashtable<String, Integer> sp_sl = new Hashtable<String, Integer>();
			for(int i = 0; i < soluong.length; i++)
			{
				if(soluong[i].trim().length() > 0)
				{
					String[] soluongDETAIL = request.getParameterValues(spIds[i] + "_spSOLUONG");
					String[] solo = request.getParameterValues(spIds[i] + "_spSOLO");
					
					for(int j = 0; j < solo.length; j++)
					{
						if(soluongDETAIL[j].trim().length() > 0)
						{
							sp_sl.put(spIds[i] + solo[j], Integer.parseInt( soluongDETAIL[j].trim().replaceAll(",", "") ));
						}
					}
				}
			}
			pxkBean.setSSp_Soluong(sp_sl);
		}
		
 		String action = request.getParameter("action");
	    
		if(action.equals("save"))
		{
			db = new dbutils();
			if (id == null)
			{
				if (!(pxkBean.CreateCk(request)))
				{	
					pxkBean.createRS();
					session.setAttribute("pxkBean", pxkBean);			
					String nextJSP = "/TraphacoHYERP/pages/Distributor/ChuyenKhoNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else{
					IChuyenkhoList obj = new ChuyenkhoList();
					obj.setUserId(userId);
					
					obj.init("");
					session.setAttribute("obj", obj);
						
					String nextJSP = "/TraphacoHYERP/pages/Distributor/ChuyenKho.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}				
			}	
			else
			{
				if (!(pxkBean.UpdateCk(request)))
				{	
					System.out.println("___Khong the cap nhat: " + pxkBean.getMessage());
					pxkBean.createRS();
					session.setAttribute("pxkBean", pxkBean);			
					String nextJSP = "/TraphacoHYERP/pages/Distributor/ChuyenKhoUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else{
					IChuyenkhoList obj = new ChuyenkhoList();
					obj.setUserId(userId);
					
					obj.init("");
					session.setAttribute("obj", obj);
						
					String nextJSP = "/TraphacoHYERP/pages/Distributor/ChuyenKho.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}	
			}
		}
		else
		{
			pxkBean.createRS();
			session.setAttribute("pxkBean", pxkBean);
			
			String nextJSP;
			if (id == null)
			{			
				nextJSP = "/TraphacoHYERP/pages/Distributor/ChuyenKhoNew.jsp";
			}
			else
			{
				nextJSP = "/TraphacoHYERP/pages/Distributor/ChuyenKhoUpdate.jsp";   						
			}
			response.sendRedirect(nextJSP);							
		}
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
}
