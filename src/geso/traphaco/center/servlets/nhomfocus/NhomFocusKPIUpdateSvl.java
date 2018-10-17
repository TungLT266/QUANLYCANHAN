package geso.traphaco.center.servlets.nhomfocus;

import geso.traphaco.center.beans.nhomfocus.INhomfocusKPI;
import geso.traphaco.center.beans.nhomfocus.INhomfocusKPIList;
import geso.traphaco.center.beans.nhomfocus.ISanpham;
import geso.traphaco.center.beans.nhomfocus.imp.NhomfocusKPI;
import geso.traphaco.center.beans.nhomfocus.imp.NhomfocusKPIList;
import geso.traphaco.center.beans.nhomfocus.imp.Sanpham;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class NhomFocusKPIUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public NhomFocusKPIUpdateSvl() {
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
			response.sendRedirect("/SalesUp/index.jsp");
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
		    INhomfocusKPI  ckBean = new NhomfocusKPI(id);
	        ckBean.setUserId(userId); //phai co UserId truoc khi Init
	        String nextJSP;
	        
	        String action = util.getAction(querystring);
	        
	        //if(request.getQueryString().indexOf("display") >= 0 )
	        if(action.equals("display"))
	        {
	        	ckBean.init();
	        	nextJSP = "/TraphacoHYERP/pages/Center/NhomSkufocusKPIDisplay.jsp";
	        }
	        else if(action.equals("copy"))//if(request.getQueryString().indexOf("copy") >= 0)
	        {
	        	 ckBean.init();
			     nextJSP ="/TraphacoHYERP/pages/Center/NhomfocusKPINew.jsp";
	        }
	        else
	        {
		        ckBean.init();
		        nextJSP ="/TraphacoHYERP/pages/Center/NhomfocusKPIUpdate.jsp";
	        }
	        session.setAttribute("ckBean", ckBean);
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
			response.sendRedirect("/SalesUp/index.jsp");
		}
		else
		{
			request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
			
			session.setMaxInactiveInterval(30000);
			
			INhomfocusKPI ckBean;
			
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
		    if(id == null)
		    {  	
		    	ckBean = new NhomfocusKPI("");
		    }
		    else
		    {
		    	ckBean = new NhomfocusKPI(id);
		    }		    		   
	
		    ckBean.setUserId(userId);
		    String diengiai = request.getParameter("diengiai");
			if(diengiai == null)
				diengiai = "";
			ckBean.setDiengiai(diengiai);
			
		    String thang= request.getParameter("thang");
			if(thang == null)
				thang = "";
			ckBean.setThang(thang);
			
			String nam = request.getParameter("nam");
			if(nam == null)
				nam = "";
			ckBean.setNam(nam);
			
			String donvikinhdoanh = request.getParameter("donvikinhdoanh");
			if(donvikinhdoanh == null)
				donvikinhdoanh = "";
			ckBean.setDvkd(donvikinhdoanh);
			
			String kenhbanhang = request.getParameter("kenhbanhang");
			if(kenhbanhang == null)
				kenhbanhang = "";
			ckBean.setKenhbanhang(kenhbanhang);
			
			String doituong = request.getParameter("doituong");
			if(doituong == null)
				doituong = "";
			ckBean.setDoituong(doituong);
			
			String strvungmien="";
			String[] checkvungmien = request.getParameterValues("checkvung");
			if(checkvungmien!=null){
				if(checkvungmien.length >0){
					for(int i=0;i<checkvungmien.length ;i++){
						if(i==0){
							strvungmien=checkvungmien[i];
						}else{
							strvungmien=strvungmien+","+checkvungmien[i];
						}
					}
				}
			}
			ckBean.setVungStr(strvungmien);

		    String action = request.getParameter("action");
		    if(action.equals("chonkho"))
		    {
		    	System.out.println("vao chon kho");
				session.setAttribute("dvkd", donvikinhdoanh);	

		    	session.setAttribute("ckBean", ckBean);
		    	if(id==null)
				{
			    	ckBean.createRs();
		    		String nextJSP = "/TraphacoHYERP/pages/Center/NhomfocusKPINew.jsp";
					 response.sendRedirect(nextJSP);
				}
		    	else
		    	{
			    	ckBean.createRsUpdate();
		    		String nextJSP ="/TraphacoHYERP/pages/Center/NhomfocusKPIUpdate.jsp";
					 response.sendRedirect(nextJSP);
		    	}
		    }
			if(action.equals("save")  )
			{	
				String[] masp = request.getParameterValues("masp");
	    		String[] tensp = request.getParameterValues("tensp");
	    		String[] phantramsp = request.getParameterValues("phantram");
			    List<ISanpham> spList = new ArrayList<ISanpham>();
			    
			    for(int i = 0; i < masp.length; i++)
			    {
			    	if(masp[i].length() !=0)
			    	{
				    	ISanpham cksp = null;
				    	cksp = new Sanpham();
				    	cksp.setMasp(masp[i]);
				    	cksp.setTensp(tensp[i]);
				    	if(phantramsp[i] == "")
				    		phantramsp[i] = "100";
				    	cksp.setPhantram(phantramsp[i]);
				    	spList.add(cksp);
			    	}
			    }
			    ckBean.setSanphamList(spList);
				if(id == null) //tao moi
				{
					if(!ckBean.createNhomfocus(request))
					{
						//Thêm thất 
	    		    	ckBean.createRs();
	    		    	session.setAttribute("ckBean", ckBean);
	    		    	String nextJSP = "/TraphacoHYERP/pages/Center/NhomfocusKPINew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						//Thêm thành công
						INhomfocusKPIList obj = new NhomfocusKPIList();
						obj.setUserId(userId);
						obj.init("");  
				    	session.setAttribute("obj", obj);  	
			    		session.setAttribute("userId", userId);
			    		response.sendRedirect("/TraphacoHYERP/pages/Center/NhomSkufocusKPI.jsp");
					}
				}
				else//Update
				{
					System.out.println("Vao cap nhat");
					if(!ckBean.updateNhomfocus(request ))
					{
						ckBean.createRs();
						session.setAttribute("ckBean", ckBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Center/NhomfocusKPIUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						INhomfocusKPIList obj = new NhomfocusKPIList();
					    obj.setUserId(userId);
					    obj.init("");
						session.setAttribute("obj", obj);							
						String nextJSP = "/TraphacoHYERP/pages/Center/NhomSkufocusKPI.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
		}
	
	}

}
