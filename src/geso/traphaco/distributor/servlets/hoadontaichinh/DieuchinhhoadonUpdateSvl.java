package geso.traphaco.distributor.servlets.hoadontaichinh;

import geso.traphaco.distributor.beans.hoadontaichinh.IHoadontaichinh;
import geso.traphaco.distributor.beans.hoadontaichinh.IHoadontaichinhList;
import geso.traphaco.distributor.beans.hoadontaichinh.imp.Hoadontaichinh;
import geso.traphaco.distributor.beans.hoadontaichinh.imp.HoadontaichinhList;
import geso.traphaco.distributor.util.Utility;
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

public class DieuchinhhoadonUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
    public DieuchinhhoadonUpdateSvl() 
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
		    IHoadontaichinh lsxBean = new Hoadontaichinh(id);
		    lsxBean.setUserId(userId); 
		    
		    String nextJSP = "";
		    
    		lsxBean.init();
    		
    		if(!querystring.contains("display"))
    		{
    			nextJSP = "/TraphacoHYERP/pages/Distributor/DieuChinhHoaDonUpdate.jsp";	
    		}
    		else
    		{
    			nextJSP = "/TraphacoHYERP/pages/Distributor/HoaDonTaiChinhDisplay.jsp";
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
			IHoadontaichinh lsxBean;
			
			Utility util = new Utility();	
			String id = util.antiSQLInspection(request.getParameter("id"));
		    if(id == null)
		    {  	
		    	lsxBean = new Hoadontaichinh("");
		    }
		    else
		    {
		    	lsxBean = new Hoadontaichinh(id);
		    }
	
		    lsxBean.setUserId(userId);
		    
		    String ngayxuatHD = util.antiSQLInspection(request.getParameter("ngayxuat"));
		    if(ngayxuatHD == null || ngayxuatHD.trim().length() <= 0)
		    	ngayxuatHD = getDateTime();
		    lsxBean.setNgayxuatHD(ngayxuatHD);
		    
		    String ngayghinhan = util.antiSQLInspection(request.getParameter("ngayghinhan"));
		    if(ngayghinhan == null || ngayghinhan.trim().length() <= 0)
		    	ngayghinhan = getDateTime();
		    lsxBean.setNgayghinhanCN(ngayghinhan);
		    
		    String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
		    if(ghichu == null)
		    	ghichu = "";
		    lsxBean.setGhichu(ghichu);
		    
		    String khId = util.antiSQLInspection(request.getParameter("khId"));
		    if(khId == null)
		    	khId = "";
		    lsxBean.setKhId(khId);
		    
		    String kyhieuhoadon = util.antiSQLInspection(request.getParameter("kyhieuhoadon"));
		    if(kyhieuhoadon == null)
		    	kyhieuhoadon = "";
		    lsxBean.setKyhieuhoadon(kyhieuhoadon);
		    
		    String sohoadon = util.antiSQLInspection(request.getParameter("sohoadon"));
		    if(sohoadon == null)
		    	sohoadon = "";
		    lsxBean.setSohoadon(sohoadon);
		    
		    String hinhthuctt = util.antiSQLInspection(request.getParameter("hinhthuctt"));
		    if(hinhthuctt == null)
		    	hinhthuctt = "";
		    lsxBean.setHinhthucTT(hinhthuctt);
		    
		    
		    String tienck = util.antiSQLInspection(request.getParameter("tienKM"));
		    if(tienck == null)
		    	tienck = "0";
		    lsxBean.setTienCK(Double.parseDouble(tienck.replace(",", "")));
		    
		    String tiensauCK = util.antiSQLInspection(request.getParameter("tiensauCK"));
		    if(tiensauCK == null)
		    	tiensauCK = "0";
		    lsxBean.setTienSauCK(Double.parseDouble(tiensauCK.replace(",", "")));
		    
		    String tienvat = util.antiSQLInspection(request.getParameter("tienvat"));
		    if(tienvat == null)
		    	tienvat = "0";
		    lsxBean.setTienVAT(Double.parseDouble(tienvat.replace(",", "")));
		    
			String[] ddhid = request.getParameterValues("ddhid");
			if (ddhid != null) {
				lsxBean.setDonDatHang(ddhid);
			}
			
			String[] spId = request.getParameterValues("spId");
			lsxBean.setSpId(spId);
			
			String[] spMa = request.getParameterValues("spMa");
			lsxBean.setSpMa(spMa);
			
			String[] spTen = request.getParameterValues("spTen");
			lsxBean.setSpTen(spTen);
			
			String[] dvt = request.getParameterValues("donvi");
			lsxBean.setSpDonvi(dvt);
			
			String[] spDongia = request.getParameterValues("spDongia");
			lsxBean.setSpDongia(spDongia);
						
			String[] spChietkhau = request.getParameterValues("spChietkhau");
			lsxBean.setSpChietkhau(spChietkhau);
			
			String[] soluong = request.getParameterValues("soluong");
			lsxBean.setSpSoluong(soluong);
			
			String[] spLoai = request.getParameterValues("spLoai");
			lsxBean.setSpLoai(spLoai);
			
			String[] spScheme = request.getParameterValues("scheme");
			lsxBean.setSpScheme(spScheme);	
			
			String[] spVat = request.getParameterValues("spVat");
			lsxBean.setSpVat(spVat);	
			
			
		    String action = request.getParameter("action");
		    
			if(action.equals("save"))
			{	
				if(id == null)
				{
					if(!lsxBean.create())
					{
						lsxBean.createRs();
	    		    	session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Distributor/DieuChinhHoaDonNew.jsp";
	    				
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IHoadontaichinhList obj = new HoadontaichinhList();
						
						obj.setUserId(userId);
						obj.init("");  
				    	session.setAttribute("obj", obj);  	
			    		session.setAttribute("userId", userId);
			    		
			    		String nextJSP = "/TraphacoHYERP/pages/Distributor/DieuChinhHoaDon.jsp";
			    		response.sendRedirect(nextJSP);
					}
				}
				else
				{
					if(!lsxBean.update())
					{
						lsxBean.init();
						session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Distributor/DieuChinhHoaDonUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IHoadontaichinhList obj = new HoadontaichinhList();
						
					    obj.setUserId(userId);
					    obj.init("");
						session.setAttribute("obj", obj);							
						String nextJSP = "/TraphacoHYERP/pages/Distributor/DieuChinhHoaDon.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
				if(action.equals("chot"))
				{
					if(!lsxBean.chot())
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Distributor/DieuChinhHoaDonUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IHoadontaichinhList obj = new HoadontaichinhList();
						
					    obj.setUserId(userId);
					    obj.init("");
						session.setAttribute("obj", obj);							
						String nextJSP = "/TraphacoHYERP/pages/Distributor/DieuChinhHoaDon.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				else
				{
					String nextJSP ="";
					if(id == null )
					{
						lsxBean.createRs();	
						session.setAttribute("lsxBean", lsxBean);					
					    nextJSP = "/TraphacoHYERP/pages/Distributor/DieuChinhHoaDonNew.jsp";
					}
					if(id != null)
					{
						lsxBean.loadContents();	
						session.setAttribute("lsxBean", lsxBean);
						nextJSP = "/TraphacoHYERP/pages/Distributor/DieuChinhHoaDonUpdate.jsp";
					}
					response.sendRedirect(nextJSP);
				}
				
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
