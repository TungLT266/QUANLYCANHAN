package geso.traphaco.distributor.servlets.cttongketnapchai;

import geso.traphaco.distributor.beans.cttongketnapchai.ICttongketnapchai;
import geso.traphaco.distributor.beans.cttongketnapchai.ICttongketnapchaiList;
import geso.traphaco.distributor.beans.cttongketnapchai.imp.Cttongketnapchai;
import geso.traphaco.distributor.beans.cttongketnapchai.imp.CttongketnapchaiList;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CttongketnapchaiUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	    
    public CttongketnapchaiUpdateSvl() {
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
		}else{
	    
	    PrintWriter out; 
		
		ICttongketnapchai obj;
		
		out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = request.getParameter("userId");
	    String action = util.getAction(querystring);
	    String id = util.getId(querystring);  	
	    obj = new Cttongketnapchai();
	    obj.setUserId(userId);
	    obj.setId(id);    
	    obj.init();
	    
	    session.setAttribute("obj", obj);  	
	    	if(action.equals("update"))
	    		response.sendRedirect("/TraphacoHYERP/pages/Distributor/CTTongKetNapChaiNew.jsp");
	    	else if(action.equals("dislay")){
	    		response.sendRedirect("/TraphacoHYERP/pages/Distributor/CTTongKetNapChaiUpdate.jsp");
//	    	}else if(action.equals("chot")){
//	    		 obj.Chot();
//	    		 CttongketnapchaiList   obj1  = new CttongketnapchaiList();
//	    	     obj1.setUserId(userId);
//	    	     obj1.init();
//	             session.setAttribute("obj",obj1);
//	             String nextJSP = "/TraphacoHYERP/pages/Distributor/CTTongKetNapChai.jsp";
//	             response.sendRedirect(nextJSP);
//	            
	    	}else{
	    		if(obj.Delete()){
	    			CttongketnapchaiList   obj1  = new CttongketnapchaiList();
		    	    obj1.setUserId(userId);
		    	    obj1.init();
		            session.setAttribute("obj",obj1);
		            String nextJSP = "/TraphacoHYERP/pages/Distributor/CTTongKetNapChai.jsp";
		            response.sendRedirect(nextJSP);
	    		}
	    	}
	    }
	    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
		PrintWriter out; 
		
		ICttongketnapchai obj;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    out = response.getWriter();
	    obj = new Cttongketnapchai();
	    
	    userId = request.getParameter("userId");
	    obj.setUserId(userId);
	    
	    String nppId = request.getParameter("nppId");
	    obj.setNppId(nppId);
	    
	    String id = request.getParameter("id");
	    obj.setId(id);
	    
	    String nppTen = request.getParameter("nppTen");
	    obj.setNppTen(nppTen);
	    
	    String nvbhId = request.getParameter("nvbhId");
	    if(nvbhId == null)
	    	nvbhId = "";
	    System.out.println("nvbhId " + nvbhId);
	    obj.setNvbhIds(nvbhId);
	    
	    String spId = request.getParameter("spId");
	    if(spId == null)
	    	spId = "";
	    System.out.println("spId " + spId);
	    obj.setSpId(spId);
	    
	    String ma = request.getParameter("ma");
	    if(ma == null)
	    	ma = "";
	    obj.setMa(ma);
	    
	    String diengiai = request.getParameter("diengiai");
	    if(diengiai == null)
	    	diengiai = "";
	    obj.setDiengiai(diengiai);
	    
	    String tungay = request.getParameter("tungay");
	    if(tungay == null)
	    	tungay = "";
	    obj.setTungay(tungay);
	    
	    String denngay = request.getParameter("denngay");
	    if(denngay == null)
	    	denngay = "";
	    obj.setDenngay(denngay);
	    
	    String soluong = request.getParameter("soluong");
	    if(soluong == null)
	    	soluong = "";
	    obj.setSoluong(soluong);
	    
	    String khachhang[] = request.getParameterValues("khIds");
//	    if(khachhang != null)
//	    {
//	    	String kh = "";
//	    	for(int i = 0; i < khachhang.length; i++ )
//	    	{
//	    		kh += khachhang[i] + ",";
//	    	}
//	    	if(kh.trim().length() > 0)
//	    	{
//	    		kh = kh.substring(0, kh.length() - 1);
//	    		obj.setKhId(kh);
//	    	}
//	    }
	    
	    String[] Soluong = request.getParameterValues("Soluong");
	    Hashtable<String, String> khsoluonghtb = new Hashtable<String, String>();
	    String str = "";
	    if(khachhang != null)
	    {
			for (int i = 0; i < khachhang.length; i++)
			{
				if(Soluong[i].length()>0)
					khsoluonghtb.put(khachhang[i], Soluong[i].replaceAll(",", ""));
				str += khachhang[i] + ",";
				if(str.length() > 0)
					str = str.substring(0, str.length() - 1);
			}
	    }
		obj.setKhSoluonHtb(khsoluonghtb);
	    
	    String action = request.getParameter("action");
	    
	    if(action.equals("submit"))
	    {   
	    	obj.createRs();
	    	session.setAttribute("obj", obj);  	 		
	    	response.sendRedirect("/TraphacoHYERP/pages/Distributor/CTTongKetNapChaiNew.jsp");	
	    }
	    else
	    {
	    	if(obj.getId().length()>0)
	    	{
	    		if(obj.edit()){
		    		ICttongketnapchaiList objl  = new CttongketnapchaiList();
		    		objl.setUserId(userId);
		    		objl.init();
		    		session.setAttribute("obj",objl);
		    		String nextJSP = "/TraphacoHYERP/pages/Distributor/CTTongKetNapChai.jsp";
		    		response.sendRedirect(nextJSP);
		    	}else{
		    		obj.init();
			    	obj.createRs();
			    	session.setAttribute("obj", obj);  	 		
		    		response.sendRedirect("/TraphacoHYERP/pages/Distributor/CTTongKetNapChaiNew.jsp");	
		    	}	
	    	}
	    	else
	    	{
	    		if(obj.save()){
		    		ICttongketnapchaiList objl  = new CttongketnapchaiList();
		    		objl.setUserId(userId);
		    		objl.init();
		    		session.setAttribute("obj",objl);
		    		String nextJSP = "/TraphacoHYERP/pages/Distributor/CTTongKetNapChai.jsp";
		    		response.sendRedirect(nextJSP);
		    	}else{
		    		obj.init();
			    	obj.createRs();
			    	session.setAttribute("obj", obj);  	 		
		    		response.sendRedirect("/TraphacoHYERP/pages/Distributor/CTTongKetNapChaiNew.jsp");	
		    	}
	    	}
	    	
	    }
	    out.println(action); 

	}}

}
