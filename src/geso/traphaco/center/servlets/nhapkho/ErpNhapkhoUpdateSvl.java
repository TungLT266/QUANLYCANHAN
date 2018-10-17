package geso.traphaco.center.servlets.nhapkho;

import geso.traphaco.center.util.Utility;
import geso.traphaco.center.beans.nhapkho.IErpNhapkho;
import geso.traphaco.center.beans.nhapkho.IErpNhapkhoList;
import geso.traphaco.center.beans.nhapkho.imp.ErpNhapkho;
import geso.traphaco.center.beans.nhapkho.imp.ErpNhapkhoList;
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


public class ErpNhapkhoUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
    public ErpNhapkhoUpdateSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/OneoOne/index.jsp");
		}
		else
		{
			session.setMaxInactiveInterval(30000);
		
			Utility util = new Utility();
			
	    	String querystring = request.getQueryString();
		    userId = util.getUserId(querystring);
		    
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId")); 
		    
		    String id = util.getId(querystring);  	
		    IErpNhapkho lsxBean = new ErpNhapkho(id);
		    lsxBean.setUserId(userId); 
		    
		    String nextJSP = "";
		    
    		lsxBean.init();
    		
    		if(!querystring.contains("display"))
    			nextJSP = "/TraphacoHYERP/pages/Center/ErpNhapKhoUpdate.jsp";
    		else
    			nextJSP = "/TraphacoHYERP/pages/Center/ErpNhapKhoDisplay.jsp";
    		
	        session.setAttribute("lsxBean", lsxBean);
	        response.sendRedirect(nextJSP);
		}		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen"); 
		
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/OneoOne/index.jsp");
		}
		else
		{
			request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
			
			session.setMaxInactiveInterval(30000);
			
			this.out = response.getWriter();
			IErpNhapkho lsxBean;
			
			Utility util = new Utility();	
			String id = util.antiSQLInspection(request.getParameter("id"));
		    if(id == null)
		    {  	
		    	lsxBean = new ErpNhapkho("");
		    }
		    else
		    {
		    	lsxBean = new ErpNhapkho(id);
		    }
	
		    lsxBean.setUserId(userId);
		    
		    String ngayyeucau = util.antiSQLInspection(request.getParameter("ngaychuyen"));
		    if(ngayyeucau == null || ngayyeucau.trim().length() <= 0)
		    	ngayyeucau = getDateTime();
		    lsxBean.setNgayyeucau(ngayyeucau);
		    	    
		    String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
		    if(ghichu == null)
		    	ghichu = "";
		    lsxBean.setGhichu(ghichu);
		    
			String khonhapId = util.antiSQLInspection(request.getParameter("khonhapId"));
			if (khonhapId == null)
				khonhapId = "";				
			lsxBean.setKhoNhapId(khonhapId);
			
			String[] spMa = request.getParameterValues("spMa");
			lsxBean.setSpMa(spMa);
			
			String[] spHsd = request.getParameterValues("spHansd");
			lsxBean.setSpHansudung(spHsd);
			
			String[] spTen = request.getParameterValues("spTen");
			lsxBean.setSpTen(spTen);
			
			String[] dvt = request.getParameterValues("donvi");
			lsxBean.setSpDonvi(dvt);
			
			String[] soluong = request.getParameterValues("soluong");
			lsxBean.setSpSoluong(soluong);
			
			if(spMa != null)
			{
				Hashtable<String, String> sp_chitiet = new Hashtable<String, String>();
				for(int i = 0; i < spMa.length; i++)
				{
					if(soluong[i].trim().length() > 0)
					{
						String[] soluongCT = request.getParameterValues("dong" + i + "_spSOLUONG");
						String[] solo = request.getParameterValues("dong" + i + "_spSOLO");	
						String[] ngaysanxuat = request.getParameterValues("dong" + i + "_spNGAYSANXUAT"); 	
						
						for(int j = 0; j < solo.length; j++)
						{
							if(ngaysanxuat[j].trim().length() <= 0)
								ngaysanxuat[j] = getDateTime();
							
							if(soluongCT[j].trim().length() > 0 && solo[j].trim().length() > 0  )
							{
								String ct = sp_chitiet.get(spMa[i].trim());
								if(ct == null)
									ct = "";
								
								if(ct.trim().length() <= 0)
									sp_chitiet.put(spMa[i], soluongCT[j].trim() + "__" + solo[j].trim() + "__" + ngaysanxuat[j].trim() + "___");
								else
								{
									ct += soluongCT[j].trim() + "__" + solo[j].trim() + "__" + ngaysanxuat[j].trim() + "___";
									sp_chitiet.put(spMa[i], ct);
								}
							}
						}
					}
				}
				lsxBean.setSp_Chitiet(sp_chitiet);
			}
			
			
		    String action = request.getParameter("action");
		    
			if(action.equals("save"))
			{	
				if(id == null)
				{
					if(!lsxBean.createNK())
					{
						lsxBean.createRs();
	    		    	session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Center/ErpNhapKhoNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpNhapkhoList obj = new ErpNhapkhoList();
						obj.setUserId(userId);
						obj.init("");  
				    	session.setAttribute("obj", obj);  	
			    		session.setAttribute("userId", userId);
				
			    		response.sendRedirect("/TraphacoHYERP/pages/Center/ErpNhapKho.jsp");
					}
				}
				else
				{
					if(!lsxBean.updateNK())
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Center/ErpNhapKhoUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpNhapkhoList obj = new ErpNhapkhoList();
					    obj.setUserId(userId);
					    obj.init("");
						session.setAttribute("obj", obj);							
						String nextJSP = "/TraphacoHYERP/pages/Center/ErpNhapKho.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
				lsxBean.createRs();
				
				session.setAttribute("lsxBean", lsxBean);
				
				String nextJSP = "";
				
				nextJSP = "/TraphacoHYERP/pages/Center/ErpNhapKhoNew.jsp";
				if(id != null)
					nextJSP = "/TraphacoHYERP/pages/Center/ErpNhapKhoUpdate.jsp";
				
				response.sendRedirect(nextJSP);
			}
		}
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
}
