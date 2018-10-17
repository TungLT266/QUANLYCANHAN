package geso.traphaco.center.servlets.donmuahang;

import geso.traphaco.center.beans.donmuahang.ITaodonhangKm;
import geso.traphaco.center.beans.donmuahang.imp.TaodonhangKm;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/TaoDonHangTraKhuyenMaiSvl")
public class TaoDonHangTraKhuyenMaiSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public TaoDonHangTraKhuyenMaiSvl() {
        super();
    
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Utility util = new Utility();

		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");

	    HttpSession session = request.getSession();

	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);

	
	    System.out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = request.getParameter("userId");
	    
	    String action = util.getAction(querystring);
	    System.out.println(action);
	    ITaodonhangKm dhkm=new TaodonhangKm();
	    dhkm.Init();
	    session.setAttribute("dhkm", dhkm);

		String nextJSP = "/TraphacoHYERP/pages/Center/Taodonhangtrakhuyenmai.jsp";
		response.sendRedirect(nextJSP);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Utility util = new Utility();
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");

	    HttpSession session = request.getSession();
	    ITaodonhangKm dhkm=new TaodonhangKm();
	   String msg="";
	   		String userId = (String) session.getAttribute("userId");
	   		dhkm.setUserId(userId);
	   		
	   		String action = util.antiSQLInspection(request.getParameter("action"));
			    String  thang= util.antiSQLInspection(request.getParameter("thang"));
			    String nam = util.antiSQLInspection(request.getParameter("nam"));
			    
			  
			     String[] ctkmchon = request.getParameterValues("ctkmchon");
			     
			     String strchon="0";
				   if(ctkmchon!=null){
					   for(int i=0;i<ctkmchon.length;i++){
						   strchon=strchon+","+ctkmchon[i];
					   }
				   }
			    dhkm.setCTKMChon(strchon);
			    
			    
			
			   String[] nhappchon = request.getParameterValues("nhappchon");
			     strchon="0";
			   if(nhappchon!=null){
				   for(int i=0;i<nhappchon.length;i++){
					   strchon=strchon+","+nhappchon[i];
				   }
			   }
			    dhkm.setNppChon(strchon);
			
			    String[] nhappchon_ttt = request.getParameterValues("nhappchon_ttt");
			    String strchon_ttt="0";
			   if(nhappchon_ttt!=null){
				   for(int i=0;i<nhappchon_ttt.length;i++){
					   strchon_ttt=strchon_ttt+","+nhappchon_ttt[i];
				   }
			   }
			    dhkm.setNppChon_ThanhToanTien(strchon_ttt);
			    
			    if(thang == null){
			    	thang="0";
			    }
			    dhkm.setNam(nam);
			    dhkm.setThang(thang);
			   
			    try{
				    if( Double.parseDouble(thang)>0 && Double.parseDouble(nam) >0 && msg.length()==0){
				    	
				      if(action.equals("thuchien"))	{
				    	 if(dhkm.ThucHien()){
				    		 msg="Thực hiện thành công";
				    	 }
				    	  
				      }else{
				    	  dhkm.LoadSpKm();
				    	  dhkm.setMsg(msg);
				      }
				    }

			    }catch(Exception err){
			    	msg=err.toString();
			    	err.printStackTrace();
			    }
			   if(!msg.equals("")){
				   dhkm.setMsg(msg);
			   }
			    dhkm.Init();
	    	    session.setAttribute("dhkm", dhkm);
	    	    String nextJSP = "/TraphacoHYERP/pages/Center/Taodonhangtrakhuyenmai.jsp";
	    		response.sendRedirect(nextJSP);	    		
	    
	    
	}
	

	
	
}
