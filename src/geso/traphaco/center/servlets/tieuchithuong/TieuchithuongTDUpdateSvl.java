package geso.traphaco.center.servlets.tieuchithuong;

import geso.traphaco.center.beans.tieuchithuong.ITieuchithuongTD;
import geso.traphaco.center.beans.tieuchithuong.ITieuchithuongTDList;
import geso.traphaco.center.beans.tieuchithuong.imp.TieuchithuongTD;
import geso.traphaco.center.beans.tieuchithuong.imp.TieuchithuongTDList;
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

public class TieuchithuongTDUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
	
    public TieuchithuongTDUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		ITieuchithuongTD tctskuBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);
	   
	    tctskuBean = new TieuchithuongTD(id);
	    tctskuBean.setId(id);
	    tctskuBean.setUserId(userId);
	    
        tctskuBean.init();
        session.setAttribute("tctskuBean", tctskuBean);
        
        String nextJSP = "/TraphacoHYERP/pages/Center/TieuChiThuongTDUpdate.jsp";
        if(querystring.indexOf("display") >= 0)
        	nextJSP = "/TraphacoHYERP/pages/Center/TieuChiThuongTDDisplay.jsp";
        
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		ITieuchithuongTD tctskuBean;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		this.out = response.getWriter();
		Utility util = new Utility();
		
	   	String id = util.antiSQLInspection(request.getParameter("id"));
	    if(id == null){  	
	    	tctskuBean = new TieuchithuongTD("");
	    }else{
	    	tctskuBean = new TieuchithuongTD(id);
	    }
	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		tctskuBean.setUserId(userId);
		
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		tctskuBean.setDiengiai(diengiai);
		
		String scheme = util.antiSQLInspection(request.getParameter("scheme"));
		if (scheme == null)
			scheme = "";
		tctskuBean.setScheme(scheme);
		
		String thang = util.antiSQLInspection(request.getParameter("thang"));
		if (thang == null)
			thang = "";
		tctskuBean.setThang(thang);
		
		String nam = util.antiSQLInspection(request.getParameter("nam"));
		if (nam == null)
			nam = "";
		tctskuBean.setNam(nam);
		
		String mucVuot = util.antiSQLInspection(request.getParameter("mucVuot"));
		if (mucVuot == null)
			mucVuot = "";
		tctskuBean.setMucvuot(mucVuot);
		
		String chietkhauMucVuot = util.antiSQLInspection(request.getParameter("chietkhauMucVuot"));
		if (chietkhauMucVuot == null)
			chietkhauMucVuot = "";
		tctskuBean.setChietkhauMucvuot(chietkhauMucVuot);
		
		String donviMucVuot = util.antiSQLInspection(request.getParameter("donviMucVuot"));
		if (donviMucVuot == null)
			donviMucVuot = "";
		tctskuBean.setDonviMucvuot(donviMucVuot);
		
		String apdungchodaily = util.antiSQLInspection(request.getParameter("apdungchodaily"));
		if (apdungchodaily == null)
			apdungchodaily = "0";
		tctskuBean.setApdungchodaily(apdungchodaily);
		
		String apdungchohopdong = util.antiSQLInspection(request.getParameter("apdungchohopdong"));
		if (apdungchohopdong == null)
			apdungchohopdong = "0";
		tctskuBean.setApdungchohopdong(apdungchohopdong);
		
		String tichluyId = util.antiSQLInspection(request.getParameter("tichluyId"));
		if (tichluyId == null)
			tichluyId = "0";
		tctskuBean.setTichluyIds(tichluyId);
		
		String kmcpId = util.antiSQLInspection(request.getParameter("kmcpId"));
		if(kmcpId == null)
			kmcpId = "";
		tctskuBean.setKhoanmuccpId(kmcpId);
		
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
		
		String[] nhomkhIds = request.getParameterValues("nhomkhIds");
		if(nhomkhIds != null)
		{
			String nhomkhId = "";
			for(int i = 0; i < nhomkhIds.length; i++)
			{
				nhomkhId += nhomkhIds[i] + ",";
			}
			
			if(nhomkhId.trim().length() > 0)
			{
				nhomkhId = nhomkhId.substring(0, nhomkhId.length() - 1);
				tctskuBean.setSpIds(nhomkhId);
			}
		}
		
		String[] spIds = request.getParameterValues("spIds");
		if(spIds != null)
		{
			String nhomkhId = "";
			for(int i = 0; i < spIds.length; i++)
			{
				nhomkhId += spIds[i] + ",";
			}
			
			if(nhomkhId.trim().length() > 0)
			{
				nhomkhId = nhomkhId.substring(0, nhomkhId.length() - 1);
				tctskuBean.setSpIds(nhomkhId);
			}
		}
		
		String[] kbhIds = request.getParameterValues("kbhIds");
		if(kbhIds != null)
		{
			String nhomkhId = "";
			for(int i = 0; i < kbhIds.length; i++)
			{
				nhomkhId += kbhIds[i] + ",";
			}
			
			if(nhomkhId.trim().length() > 0)
			{
				nhomkhId = nhomkhId.substring(0, nhomkhId.length() - 1);
				tctskuBean.setKbhIds(nhomkhId);
			}
		}
		
		String[] loaiIds = request.getParameterValues("loaiIds");
		if(loaiIds != null)
		{
			String nhomkhId = "";
			for(int i = 0; i < loaiIds.length; i++)
			{
				nhomkhId += loaiIds[i] + ",";
			}
			
			if(nhomkhId.trim().length() > 0)
			{
				nhomkhId = nhomkhId.substring(0, nhomkhId.length() - 1);
				tctskuBean.setLoaikhIds(nhomkhId);
			}
		}
		
		//Muc 1 thang
    	String[] diengiaiMuc = request.getParameterValues("diengiaiMuc");
    	tctskuBean.setDiengiaiMuc(diengiaiMuc);
    	
		String[] tumuc = request.getParameterValues("tumuc");
		tctskuBean.setTumuc(tumuc);
		
		String[] denmuc = request.getParameterValues("denmuc");
		tctskuBean.setDenmuc(denmuc);
		
		String[] thuongSR = request.getParameterValues("chietkhau");
		tctskuBean.setThuongSR(thuongSR);
		
		String[] thuongTDSR = request.getParameterValues("donvi");
		tctskuBean.setThuongTDSR(thuongTDSR);
		
		/*String[] thuongSS = request.getParameterValues("thuongSS");
		tctskuBean.setThuongSS(thuongSS);
		
		String[] thuongTDSS = request.getParameterValues("thuongTDSS");
		tctskuBean.setThuongTDSS(thuongTDSS);
		
		String[] thuongASM = request.getParameterValues("thuongASM");
		tctskuBean.setThuongASM(thuongASM);
		
		String[] thuongTDASM = request.getParameterValues("thuongTDASM");
		tctskuBean.setThuongTDASM(thuongTDASM);*/
		
		String chiavaodongia = request.getParameter("chiavaodongia");
		if(chiavaodongia == null)
			chiavaodongia = "0";
		tctskuBean.setChiavaodongia(chiavaodongia);
		
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
    				
    				String nextJSP = "/TraphacoHYERP/pages/Center/TieuChiThuongTDNew.jsp";
    				response.sendRedirect(nextJSP);
    			}
    			else
    			{
    				ITieuchithuongTDList obj = new TieuchithuongTDList();
				    obj.setUserId(userId);
				    
				    obj.init("");
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Center/TieuChiThuongTD.jsp";
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
    				
    				String nextJSP = "/TraphacoHYERP/pages/Center/TieuChiThuongTDUpdate.jsp";
    				response.sendRedirect(nextJSP);
    			}
				else
				{
					ITieuchithuongTDList obj = new TieuchithuongTDList();
				    obj.setUserId(userId);
				    
				    obj.init("");
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Center/TieuChiThuongTD.jsp";
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
				nextJSP = "/TraphacoHYERP/pages/Center/TieuChiThuongTDNew.jsp";
			}
			else{
				nextJSP = "/TraphacoHYERP/pages/Center/TieuChiThuongTDUpdate.jsp";   						
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

}
