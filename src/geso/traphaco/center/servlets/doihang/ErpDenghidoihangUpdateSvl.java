package geso.traphaco.center.servlets.doihang;

import geso.traphaco.center.beans.doihang.IErpDenghidoihang;
import geso.traphaco.center.beans.doihang.IErpDenghidoihangList;
import geso.traphaco.center.beans.doihang.imp.ErpDenghidoihang;
import geso.traphaco.center.beans.doihang.imp.ErpDenghidoihangList;
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

public class ErpDenghidoihangUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
    public ErpDenghidoihangUpdateSvl() 
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
		    IErpDenghidoihang lsxBean = new ErpDenghidoihang(id);
		    lsxBean.setUserId(userId); 
		    
		    String nextJSP = "";
		    
    		lsxBean.init();
    		
    		if(!querystring.contains("display"))
    		{
    			nextJSP = "/TraphacoHYERP/pages/Center/ErpDeNghiDoiHangUpdate.jsp";
    		}
    		else
    		{
    			nextJSP = "/TraphacoHYERP/pages/Center/ErpDeNghiDoiHangDisplay.jsp";
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
			IErpDenghidoihang lsxBean;
			
			Utility util = new Utility();	
			String id = util.antiSQLInspection(request.getParameter("id"));
		    if(id == null)
		    {  	
		    	lsxBean = new ErpDenghidoihang("");
		    }
		    else
		    {
		    	lsxBean = new ErpDenghidoihang(id);
		    }
	
		    lsxBean.setUserId(userId);
		    
		    String ngayyeucau = util.antiSQLInspection(request.getParameter("ngaychuyen"));
		    if(ngayyeucau == null || ngayyeucau.trim().length() <= 0)
		    	ngayyeucau = getDateTime();
		    lsxBean.setNgayyeucau(ngayyeucau);
		    
		    String ngaydenghi = util.antiSQLInspection(request.getParameter("ngaydenghi"));
		    if(ngaydenghi == null || ngaydenghi.trim().length() <= 0)
		    	ngaydenghi = "";
		    lsxBean.setNgaydenghi(ngaydenghi);
		    	    
		    String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
		    if(ghichu == null)
		    	ghichu = "";
		    lsxBean.setGhichu(ghichu);
		    
			String khonhapId = util.antiSQLInspection(request.getParameter("khonhapId"));
			if (khonhapId == null)
				khonhapId = "";				
			lsxBean.setKhoNhapId(khonhapId);
			
			String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
			if (dvkdId == null)
				dvkdId = "";				
			lsxBean.setDvkdId(dvkdId);
			
			String kbhId = util.antiSQLInspection(request.getParameter("kbhId"));
			if (kbhId == null)
				kbhId = "";				
			lsxBean.setKbhId(kbhId);
			
			String nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if (nppId == null)
				nppId = "";				
			lsxBean.setNppId(nppId);
			
			String chietkhau = util.antiSQLInspection(request.getParameter("ptChietkhau"));
			if (chietkhau == null)
				chietkhau = "";				
			lsxBean.setChietkhau(chietkhau);
			
			String vat = util.antiSQLInspection(request.getParameter("ptVat"));
			if (vat == null)
				vat = "";				
			lsxBean.setVat(vat);
			
			String loaidonhang = util.antiSQLInspection(request.getParameter("loaidonhang"));
			if (loaidonhang == null)
				loaidonhang = "";				
			lsxBean.setLoaidonhang(loaidonhang); 
			
		    String action = request.getParameter("action");
			if(action.equals("save"))
			{	
				if(id == null)
				{
					boolean kq = lsxBean.createNK(request);
					
					if(!kq)
					{
						lsxBean.createRs();
	    		    	session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Center/ErpDeNghiDoiHangNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpDenghidoihangList obj = new ErpDenghidoihangList();
						obj.setLoaidonhang(loaidonhang);
						
						obj.setUserId(userId);
						obj.init("");  
				    	session.setAttribute("obj", obj);  	
			    		session.setAttribute("userId", userId);
			    		
			    		String nextJSP = "/TraphacoHYERP/pages/Center/ErpDeNghiDoiHang.jsp";
			    		response.sendRedirect(nextJSP);
					}
				}
				else
				{
					boolean kq = lsxBean.updateNK(request);
					
					if(!kq)
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Center/ErpDeNghiDoiHangUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpDenghidoihangList obj = new ErpDenghidoihangList();
						obj.setLoaidonhang(loaidonhang);
						
					    obj.setUserId(userId);
					    obj.init("");
						session.setAttribute("obj", obj);							
						String nextJSP = "/TraphacoHYERP/pages/Center/ErpDeNghiDoiHang.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else if(action.equals("duyet"))
			{
				boolean kq = lsxBean.duyetDH(request);
				
				if(!kq)
				{
					lsxBean.createRs();
					session.setAttribute("lsxBean", lsxBean);
    				String nextJSP = "/TraphacoHYERP/pages/Center/ErpDeNghiDoiHangUpdate.jsp";
    				response.sendRedirect(nextJSP);
				}
				else
				{
					IErpDenghidoihangList obj = new ErpDenghidoihangList();
					obj.setLoaidonhang(loaidonhang);
					
				    obj.setUserId(userId);
				    obj.init("");
					session.setAttribute("obj", obj);							
					String nextJSP = "/TraphacoHYERP/pages/Center/ErpDeNghiDoiHang.jsp";
					response.sendRedirect(nextJSP);
				}
			}
			else
			{
				lsxBean.createRs();
				
				session.setAttribute("lsxBean", lsxBean);
				
				String nextJSP = "";
				
				nextJSP = "/TraphacoHYERP/pages/Center/ErpDeNghiDoiHangNew.jsp";
				if(id != null)
				{
					nextJSP = "/TraphacoHYERP/pages/Center/ErpDeNghiDoiHangUpdate.jsp";
				}
				
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
