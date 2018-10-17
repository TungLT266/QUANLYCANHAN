package geso.traphaco.center.servlets.hopdong;

import geso.traphaco.center.beans.hopdong.IErpDonhangETC;
import geso.traphaco.center.beans.hopdong.IErpDonhangETCList;
import geso.traphaco.center.beans.hopdong.imp.ErpDonhangETC;
import geso.traphaco.center.beans.hopdong.imp.ErpDonhangETCList;
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

import org.apache.catalina.connector.Request;

public class ErpDonhangETCUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
    public ErpDonhangETCUpdateSvl() 
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
			String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
			String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
		
			Utility util = new Utility();
			
	    	String querystring = request.getQueryString();
		    userId = util.getUserId(querystring);
		    
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId")); 
		    
		    String id = util.getId(querystring);  	
		    IErpDonhangETC lsxBean = new ErpDonhangETC(id);
		    lsxBean.setUserId(userId); 
		    lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
		    lsxBean.setNpp_duocchon_id(npp_duocchon_id);
		    
		    String nextJSP = "";
		    
    		lsxBean.init();
    		
    		session.setAttribute("dvkdId", lsxBean.getDvkdId());
			session.setAttribute("kbhId", lsxBean.getKbhId());
			session.setAttribute("nppId", lsxBean.getNppId());
    		
    		if(!querystring.contains("display"))
    		{
    			nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangETCUpdate.jsp";
    		}
    		else
    		{
    			nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangETCDisplay.jsp";
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
			
			IErpDonhangETC lsxBean;
			
			Utility util = new Utility();	
			String id = util.antiSQLInspection(request.getParameter("id"));
		    if(id == null)
		    {  	
		    	lsxBean = new ErpDonhangETC("");
		    }
		    else
		    {
		    	lsxBean = new ErpDonhangETC(id);
		    }
	
		    lsxBean.setUserId(userId);
		    
		    String tungay = util.antiSQLInspection(request.getParameter("hopdong_tungay"));
		    if(tungay == null || tungay.trim().length() <= 0 )
		    	tungay = getDateTime();
		    lsxBean.setTungay(tungay);
		    
		    String denngay = util.antiSQLInspection(request.getParameter("hopdong_denngay"));
		    if(denngay == null || denngay.trim().length() <= 0)
		    	denngay = getDateTime();
		    lsxBean.setDenngay(denngay);
		    	    
		    String mahopdong = util.antiSQLInspection(request.getParameter("mahopdong"));
		    if(mahopdong == null)
		    	mahopdong = "";
		    lsxBean.setMahopdong(mahopdong);
		    
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
			
			String gsbhId = util.antiSQLInspection(request.getParameter("gsbhId"));
			if (gsbhId == null)
				gsbhId = "";				
			lsxBean.setGsbhId(gsbhId);
			
			String ddkdId = util.antiSQLInspection(request.getParameter("ddkdId"));
			if (ddkdId == null)
				ddkdId = "";				
			lsxBean.setDdkdId(ddkdId);
			
			String nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if (nppId == null)
				nppId = "";				
			lsxBean.setNppId(nppId);
			
			String vat = util.antiSQLInspection(request.getParameter("ptVat"));
			if (vat == null)
				vat = "";				
			lsxBean.setVat(vat);
			
			String loaidonhang = util.antiSQLInspection(request.getParameter("loaidonhang"));
			if (loaidonhang == null)
				loaidonhang = "0";				
			lsxBean.setLoaidonhang(loaidonhang);
			
			
			String[] spMa = request.getParameterValues("spMa");
			lsxBean.setSpMa(spMa);
			
			String[] spTen = request.getParameterValues("spTen");
			lsxBean.setSpTen(spTen);
			
			String[] dvt = request.getParameterValues("donvi");
			lsxBean.setSpDonvi(dvt);
			
			String[] soluong = request.getParameterValues("soluong");
			lsxBean.setSpSoluong(soluong);
			
			String[] dongia = request.getParameterValues("dongia");
			lsxBean.setSpGianhap(dongia);
			
			String[] spQuyDoi = request.getParameterValues("spQuyDoi");
			lsxBean.setSpQuyDoi(spQuyDoi);
			
			String[] spChietkhau = request.getParameterValues("chietkhau");
			lsxBean.setSpChietkhau(spChietkhau);
			
			String[] spVat = request.getParameterValues("spvat");
			lsxBean.setSpVat(spVat);
			
			String[] trongluong = request.getParameterValues("spTrongLuong");
			lsxBean.setSpTrongluong(trongluong);
			
			String[] thetich = request.getParameterValues("spTheTich");
			lsxBean.setSpThetich(thetich);
			
			String[] spTungay = request.getParameterValues("tungay");
			lsxBean.setSpTungay(spTungay);
			
			String[] spDenngay = request.getParameterValues("denngay");
			lsxBean.setSpDenngay(spDenngay);
			
			//THEM CAC LOAI CHIET KHAU
			String[] dhCK_diengiai = request.getParameterValues("dhCK_diengiai");
			lsxBean.setDhck_Diengiai(dhCK_diengiai);
			String[] dhCK_giatri = request.getParameterValues("dhCK_giatri");
			lsxBean.setDhck_giatri(dhCK_giatri);
			String[] dhCK_loai = request.getParameterValues("dhCK_loai");
			lsxBean.setDhck_loai(dhCK_loai);
				
		    String action = request.getParameter("action");
			if(action.equals("save"))
			{	
				if(id == null)
				{
					boolean kq = lsxBean.createNK();
					if(!kq)
					{
						lsxBean.createRs();
	    		    	session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangETCNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpDonhangETCList obj = new ErpDonhangETCList();
						obj.setLoaidonhang(loaidonhang);
						
						obj.setUserId(userId);
						obj.init("");  
				    	session.setAttribute("obj", obj);  	
			    		session.setAttribute("userId", userId);
			    		
			    		String nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangETC.jsp";	
			    		response.sendRedirect(nextJSP);
					}
				}
				else
				{
					boolean kq = lsxBean.updateNK("1");
					if(!kq)
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangETCUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpDonhangETCList obj = new ErpDonhangETCList();
						obj.setLoaidonhang(loaidonhang);
						
					    obj.setUserId(userId);
					    obj.init("");
						session.setAttribute("obj", obj);							
						String nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangETC.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
				lsxBean.createRs();
				
				session.setAttribute("dvkdId", lsxBean.getDvkdId());
				session.setAttribute("kbhId", lsxBean.getKbhId());
				session.setAttribute("nppId", lsxBean.getNppId());
				
				session.setAttribute("lsxBean", lsxBean);
				
				String nextJSP = "";
				
				nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangETCNew.jsp";
				if(id != null)
					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangETCUpdate.jsp";
				
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
