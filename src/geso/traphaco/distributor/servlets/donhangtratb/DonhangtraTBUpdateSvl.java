package geso.traphaco.distributor.servlets.donhangtratb;

import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.donhangtratb.IDonhangtraTB;
import geso.traphaco.distributor.beans.donhangtratb.IDonhangtraTBList;
import geso.traphaco.distributor.beans.donhangtratb.imp.DonhangtraTB;
import geso.traphaco.distributor.beans.donhangtratb.imp.DonhangtraTBList;

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

public class DonhangtraTBUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
	
    public DonhangtraTBUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		IDonhangtraTB tctskuBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);
	   
	    tctskuBean = new DonhangtraTB(id);
	    tctskuBean.setId(id);
	    tctskuBean.setUserId(userId);
	    
        tctskuBean.init();
        session.setAttribute("tctskuBean", tctskuBean);
        
        String nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangTraTBUpdate.jsp";
        if(querystring.indexOf("display") >= 0)
        	nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangTraTBDisplay.jsp";
        
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IDonhangtraTB tctskuBean;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		this.out = response.getWriter();
		Utility util = new Utility();
		
	   	String id = util.antiSQLInspection(request.getParameter("id"));
	    if(id == null){  	
	    	tctskuBean = new DonhangtraTB("");
	    }else{
	    	tctskuBean = new DonhangtraTB(id);
	    }
	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		tctskuBean.setUserId(userId); 
		
		String ngaynhap = util.antiSQLInspection(request.getParameter("ngaynhap"));
		if (ngaynhap == null)
			ngaynhap = "";
		tctskuBean.setNgaynhap(ngaynhap);
		
		String cttbId = util.antiSQLInspection(request.getParameter("cttbId"));
		if (cttbId == null)
			cttbId = "";
		tctskuBean.setCttbId(cttbId);
		
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		tctskuBean.setDiengiai(diengiai);
		
		String lanthanhtoan = util.antiSQLInspection(request.getParameter("lanthanhtoan"));
		if (lanthanhtoan == null)
			lanthanhtoan = "0";
		tctskuBean.setLanthanhtoan(Integer.parseInt(lanthanhtoan));
		
		String[] khId = request.getParameterValues("khId");
		tctskuBean.setKhId(khId);
		
		String[] khTen = request.getParameterValues("khTen");
		tctskuBean.setKhTen(khTen);
		
		String[] spId = request.getParameterValues("spId");
		tctskuBean.setSpId(spId);
		
		String[] spTen = request.getParameterValues("spTen");
		tctskuBean.setSpTen(spTen);
		
		String[] soxuat = request.getParameterValues("soxuat");
		tctskuBean.setSoxuat(soxuat);
		
		String[] total = request.getParameterValues("total");
		tctskuBean.setTotal(total);
		
		String[] thanhtoan = request.getParameterValues("thanhtoan");
		tctskuBean.setThanhtoan(thanhtoan);
		
		//Hashtable<String, Double> khachhang_thanhtoan = new Hashtable<String, Double>();
		String msg = "";
		if(khId != null)
		{
			for(int i = 0; i < khId.length; i++)
			{
				if(thanhtoan[i] != "" )
				{
					//khachhang_thanhtoan.put(khId[i], Double.parseDouble(thanhtoan[i].replaceAll(",", "")) );
					
					if( Double.parseDouble(total[i].trim().replaceAll(",", "")) < Double.parseDouble(thanhtoan[i].replaceAll(",", ""))  )
					{
						msg = "Số thanh toán của khách hàng ( " + khTen[i] + " ) không được phép vượt quá tổng số lượng / số tiền";
					}
				}
			}
		}
		//tctskuBean.setKhachhang_Thanhtoan(khachhang_thanhtoan);
		
 		String action = request.getParameter("action");
 		System.out.println("Action la: " + action);
 		
 		if(action.equals("save") && msg.trim().length() <= 0 )
 		{    
    		if (id == null )
    		{
    			if (!tctskuBean.create())
    			{
    		    	tctskuBean.setUserId(userId);
    		    	
    		    	tctskuBean.createRs();
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("tctskuBean", tctskuBean);
    				
    				String nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangTraTBNew.jsp";
    				response.sendRedirect(nextJSP);
    			}
    			else
    			{
    				IDonhangtraTBList obj = new DonhangtraTBList();
				    obj.setUserId(userId);
				    
				    obj.init("");
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangTraTB.jsp";
					response.sendRedirect(nextJSP);
    			}	
    		}
    		else
    		{
    			if (!(tctskuBean.update()))
    			{			
    		    	tctskuBean.setUserId(userId);
    		    	
    		    	tctskuBean.createRs();
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("tctskuBean", tctskuBean);
    				
    				String nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangTraTBUpdate.jsp";
    				response.sendRedirect(nextJSP);
    			}
				else
				{
					IDonhangtraTBList obj = new DonhangtraTBList();
				    obj.setUserId(userId);
				    
				    obj.init("");
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangTraTB.jsp";
					response.sendRedirect(nextJSP);
				}
    		}
	    }
		else
		{		
			tctskuBean.createRs();
			tctskuBean.setMsg(msg);
			session.setAttribute("userId", userId);
			session.setAttribute("tctskuBean", tctskuBean);
			
			String nextJSP;
			if (id == null){
				nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangTraTBNew.jsp";
			}
			else{
				nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangTraTBUpdate.jsp";   						
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
