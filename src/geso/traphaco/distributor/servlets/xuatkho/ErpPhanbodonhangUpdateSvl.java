package geso.traphaco.distributor.servlets.xuatkho;

import geso.traphaco.distributor.beans.xuatkho.IErpPhanbodonhang;
import geso.traphaco.distributor.beans.xuatkho.IErpPhanbodonhangList;
import geso.traphaco.distributor.beans.xuatkho.imp.ErpPhanbodonhang;
import geso.traphaco.distributor.beans.xuatkho.imp.ErpPhanbodonhangList;
import geso.traphaco.center.util.Utility;

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

public class ErpPhanbodonhangUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
    public ErpPhanbodonhangUpdateSvl() 
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
			response.sendRedirect("/TraphacoHYERP/index.jsp");
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
		    IErpPhanbodonhang lsxBean = new ErpPhanbodonhang(id);
		    lsxBean.setUserId(userId); 
		    
		    String nextJSP = "";
		    
    		lsxBean.init();
		    
    		if(!querystring.contains("display"))
    		{	
    			nextJSP = "/TraphacoHYERP/pages/Center/ErpPhanBoDonHangUpdate.jsp";	
    		}
    		else
    		{
    			nextJSP = "/TraphacoHYERP/pages/Center/ErpPhanBoDonHangDisplay.jsp";
    		}
    		
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
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
			
			session.setMaxInactiveInterval(30000);
			
			this.out = response.getWriter();
			IErpPhanbodonhang lsxBean;
			
			Utility util = new Utility();	
			String id = util.antiSQLInspection(request.getParameter("id"));
		    if(id == null)
		    {  	
		    	lsxBean = new ErpPhanbodonhang("");
		    }
		    else
		    {
		    	lsxBean = new ErpPhanbodonhang(id);
		    }
	
		    lsxBean.setUserId(userId);
		    
		    String tungay = util.antiSQLInspection(request.getParameter("tungay"));
		    if(tungay == null)
		    	tungay = "";
		    lsxBean.setTungay(tungay);
		    
		    String denngay = util.antiSQLInspection(request.getParameter("denngay"));
		    if(denngay == null)
		    	denngay = "";
		    lsxBean.setDenngay(denngay);
		    	    
		    String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
		    if(ghichu == null)
		    	ghichu = "";
		    lsxBean.setGhichu(ghichu);
		    
			String xuatchoId = util.antiSQLInspection(request.getParameter("xuatchoId"));
			if (xuatchoId == null)
				xuatchoId = "0";				
			lsxBean.setXuatcho(xuatchoId);
			
			String[] khIds = request.getParameterValues("khId");	
			String khId = "";
			if( khIds != null )
			{
				for( int i = 0; i < khIds.length; i++ )
					khId += khIds[i] + ",";
				
				if( khId.trim().length() > 0 )
					khId = khId.substring(0, khId.length() - 1);
			}
			lsxBean.setKhId(khId);
			
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
					String[] solo = request.getParameterValues(spMa[i] + "_spCHON");	
					if( solo != null )
					{
						for(int j = 0; j < solo.length; j++)
						{
							String key = spMa[i] + "__" + solo[j];
							//System.out.println("::: KEY SVL: " + key);
							sp_chitiet.put(key, "1");
						}
					}
					
					/*String[] soluongCT = request.getParameterValues("dong" + i + "_spSOLUONG");
					String[] solo = request.getParameterValues("dong" + i + "_spSOLO");	
					String[] tonkho = request.getParameterValues("dong" + i + "_spTONKHO");	
					
					for(int j = 0; j < solo.length; j++)
					{
						if( solo[j].trim().length() > 0  )
						{
							if( soluongCT[j].trim().length() <= 0 )
								soluongCT[j] = "0";
							
							String ct = sp_chitiet.get(spMa[i].trim());
							if(ct == null)
								ct = "";
							
							if(ct.trim().length() <= 0)
							{
								sp_chitiet.put(spMa[i], soluongCT[j].trim() + "__" + solo[j].trim() + "___" + tonkho[j].trim() + "___" );
							}
							else
							{
								ct += soluongCT[j].trim() + "__" + solo[j].trim() + "___" + tonkho[j].trim() + "___";
								sp_chitiet.put(spMa[i], ct);
							}
						}
					}*/
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
	    				String nextJSP = "/TraphacoHYERP/pages/Center/ErpPhanBoDonHangNew.jsp";
	    				
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpPhanbodonhangList obj = new ErpPhanbodonhangList();
						obj.setUserId(userId);
						obj.init("");  
				    	session.setAttribute("obj", obj);  	
			    		session.setAttribute("userId", userId);
			    		
			    		String nextJSP = "/TraphacoHYERP/pages/Center/ErpPhanBoDonHang.jsp";
			    		response.sendRedirect(nextJSP);
					}
				}
				else
				{
					if(!lsxBean.updateNK())
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Center/ErpPhanBoDonHangUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpPhanbodonhangList obj = new ErpPhanbodonhangList();
					    obj.setUserId(userId);
					    obj.init("");
						session.setAttribute("obj", obj);							
						String nextJSP = "/TraphacoHYERP/pages/Center/ErpPhanBoDonHang.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
				lsxBean.createRs();
				
				session.setAttribute("lsxBean", lsxBean);
				
				String nextJSP = "/TraphacoHYERP/pages/Center/ErpPhanBoDonHangNew.jsp";
				if(id != null)
					nextJSP = "/TraphacoHYERP/pages/Center/ErpPhanBoDonHangUpdate.jsp";
				
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
