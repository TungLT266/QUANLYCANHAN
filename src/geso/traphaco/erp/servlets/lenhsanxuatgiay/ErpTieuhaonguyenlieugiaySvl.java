package geso.traphaco.erp.servlets.lenhsanxuatgiay;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.lenhsanxuatgiay.IErpLenhsanxuat;
import geso.traphaco.erp.beans.lenhsanxuatgiay.IErpTieuhaonguyenlieu;
import geso.traphaco.erp.beans.lenhsanxuatgiay.IErpTieuhaonguyenlieulist;
import geso.traphaco.erp.beans.lenhsanxuatgiay.imp.ErpLenhsanxuat;
import geso.traphaco.erp.beans.lenhsanxuatgiay.imp.ErpTieuhaonguyenlieu;
import geso.traphaco.erp.beans.lenhsanxuatgiay.imp.ErpTieuhaonguyenlieuList;
import geso.traphaco.erp.beans.nhanhangsanxuat.IErpNhanhang_Giay;
import geso.traphaco.erp.beans.nhanhangsanxuat.imp.ErpNhanhang_Giay;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpTieuhaonguyenlieugiaySvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpTieuhaonguyenlieugiaySvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpTieuhaonguyenlieulist  obj = new ErpTieuhaonguyenlieuList();
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    obj.SetCtyId((String)session.getAttribute("congtyId"));
	    obj.setNppId((String)session.getAttribute("nppId"));
	  
	    String action = util.getAction(querystring);
	    
	    String id = util.getId(querystring);
	    
	   
	   
	    
	    System.out.println("Action : "+action);
	    
	    String  nextJSP = "/TraphacoHYERP/pages/Erp/ErpTieuHaoNguyenLieuGiay.jsp";
	  
	    
	    if (action.equals("delete"))
	    {	
	    	obj.setIdTieuHao(id);
		    String msg=	obj.DeleteTieuHao();
		    System.out.println(msg);	
		    obj.setUserId(userId);
		    obj.Init("");
		    
			session.setAttribute("obj", obj);

			response.sendRedirect(nextJSP);
	    	
	    }  else if (action.equals("chot"))
	    {	
	    	obj.setIdTieuHao(id);
		    String msg=	obj.Chottieuhao(id);
		    System.out.println(msg);	
		    obj.setUserId(userId);
		    obj.Init("");
		    
			session.setAttribute("obj", obj);

			response.sendRedirect(nextJSP);
	    	
	    }
	    else if(action.equals("display")) {
	    	
	    	 
	    	 IErpTieuhaonguyenlieu lsxBean = new  ErpTieuhaonguyenlieu(id);
			    lsxBean.setCongtyId((String) session.getAttribute("congtyId"));
			    lsxBean.setNppId((String) session.getAttribute("nppId"));
			    
			    lsxBean.setTieuHaoId(id);
			    lsxBean.checkTieuHaoLsx();
			    
			    lsxBean.setUserId(userId);
			    lsxBean.createLoaisanphamRs();
				session.setAttribute("lsxBean", lsxBean);
				session.setAttribute("nhamayid", lsxBean.getNhamayId());
				lsxBean.CreateRs_tieuhao();
	    	     nextJSP ="/TraphacoHYERP/pages/Erp/ErpLenhSanXuatGiayTieuHaoDisplay.jsp";
	    	  
	    	   response.sendRedirect(nextJSP);
	    }else{

	   
		
		    obj.setUserId(userId);
		    obj.Init("");
		    
			session.setAttribute("obj", obj);
				
			
			
			response.sendRedirect(nextJSP);
	    }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    String action = request.getParameter("action");
	    if (action == null)
	    {
	    	action = "";
	    }
	    
	    IErpTieuhaonguyenlieulist obj = new ErpTieuhaonguyenlieuList();
	   
	    Utility util = new Utility();
	    HttpSession session = request.getSession();	 
	    obj.SetCtyId((String)session.getAttribute("congtyId"));
		obj.setNppId((String)session.getAttribute("nppId"));
		  
		  String tungay =  util.antiSQLInspection(request.getParameter("tungaySX"));
		  if(tungay==null)
			  tungay="";
		  obj.SetNgayBanDau(tungay);
		  
		  String denngay =  util.antiSQLInspection(request.getParameter("denngaySX"));
		  if(denngay==null)
			  denngay="";
		  obj.SetNgayKetThuc(denngay);
		  
		  
		  String trangthai =  util.antiSQLInspection(request.getParameter("trangthai"));
		  if(trangthai==null)
			  trangthai="";
		  obj.setTrangthai(trangthai);
		
		  String sochungtu =  util.antiSQLInspection(request.getParameter("sochungtu"));
		  if(sochungtu==null)
			  sochungtu="";
		  obj.setSochungtu(sochungtu);
		  
		  String solenhsx =  util.antiSQLInspection(request.getParameter("solenhsx"));
		  if(solenhsx==null)
			  solenhsx="";
		  obj.setLsxId(solenhsx);
	    
		  String xuongId =  util.antiSQLInspection(request.getParameter("xuongId"));
		  if(xuongId==null)
			  xuongId="";
		  obj.setXuongId(xuongId);
		  
		 
		  
		
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    
	    
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		System.out.println("toi day");
		    	
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setUserId(userId);
		    	obj.Init("");
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	
		    	
		    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpTieuHaoNguyenLieuGiay.jsp");	
		    	
		    }else if(action.equals("Taomoi")){
		    	IErpTieuhaonguyenlieu nhBean=new ErpTieuhaonguyenlieu();
		    
		    	nhBean.setUserId(userId);
		    	nhBean.setCongtyId((String)session.getAttribute("congtyId"));
		    	nhBean.setNppId((String)session.getAttribute("nppId"));
		    	nhBean.CreateRs_tieuhao();
		    	session.setAttribute("lsxBean", nhBean);
	    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLenhSanXuatGiayTieuHao.jsp";
	    		response.sendRedirect(nextJSP);
	    		
		    }
	    	else
	    	{
		    	
		    	obj.Init("");
				obj.setUserId(userId);
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpTieuHaoNguyenLieuGiay.jsp");	
		    	
	    	}
	    
	}
	
		
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
}
