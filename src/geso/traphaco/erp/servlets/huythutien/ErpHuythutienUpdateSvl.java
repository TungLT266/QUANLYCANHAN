package geso.traphaco.erp.servlets.huythutien;

import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.huythutien.IErpHuythutien;
import geso.traphaco.erp.beans.huythutien.IErpHuythutienList;
import geso.traphaco.erp.beans.huythutien.imp.ErpHuythutien;
import geso.traphaco.erp.beans.huythutien.imp.ErpHuythutienList;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpHuythutienUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpHuythutienUpdateSvl() {
        super();
    }

    PrintWriter out;
    
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
		
			this.out = response.getWriter();
			Utility util = new Utility();
			
	    	String querystring = request.getQueryString();
		    userId = util.getUserId(querystring);
		    		    
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId"));
		    
		    String id = util.getId(querystring);  	
			IErpHuythutien hctmhBean = new ErpHuythutien(id);
			
			hctmhBean.setCongtyId((String)session.getAttribute("congtyId"));
	        hctmhBean.setUserId(userId); 
	        hctmhBean.setnppdangnhap(util.getIdNhapp(userId));
	        
	        hctmhBean.init();
	    	
	        String nextJSP;
	        if(request.getQueryString().indexOf("display") >= 0 ) 
	        {
	        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuyThuTienDisplay.jsp";
	        }
	        else
	        {
	        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuyThuTienUpdate.jsp";
	        }
	        
	        session.setAttribute("hctmhBean", hctmhBean);
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
			IErpHuythutien hctmhBean;
			
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			
		    if(id == null)
		    {  	
		    	hctmhBean = new ErpHuythutien("");
		    }
		    else
		    {
		    	hctmhBean = new ErpHuythutien(id);
		    }
	
		    hctmhBean.setCongtyId((String)session.getAttribute("congtyId"));
		    hctmhBean.setUserId(userId);
		    hctmhBean.setnppdangnhap(util.getIdNhapp(userId));
		    
			
		    String sochungtu = util.antiSQLInspection(request.getParameter("sochungtu"));
			if (sochungtu == null)
				sochungtu = "";		
	    	hctmhBean.setSochungtu(sochungtu);
	    	
	    	String sochungtugoc = util.antiSQLInspection(request.getParameter("sochungtugoc"));
			if (sochungtugoc == null)
				sochungtugoc = "";		
	    	hctmhBean.setSochungtugoc(sochungtugoc);
		    	
	    	String sothanhtoan = util.antiSQLInspection(request.getParameter("sothanhtoan"));
	    	if(sothanhtoan == null)
	    		sothanhtoan = "";
	    	hctmhBean.setSoThanhtoan(sothanhtoan);
	    	
	    	String sohoadon = util.antiSQLInspection(request.getParameter("sohoadon"));
	    	if(sohoadon == null)
	    		sohoadon = "";
	    	hctmhBean.setSoHoadon(sohoadon);
	    	
	    	String sonhapkho = util.antiSQLInspection(request.getParameter("sonhapkho"));
	    	if(sonhapkho == null)
	    		sonhapkho = "";
	    	hctmhBean.setSoPhieunhapkho(sonhapkho);
	    	
	    	String sonhanhang = util.antiSQLInspection(request.getParameter("sonhanhang"));
	    	if(sonhanhang == null)
	    		sonhanhang = "";
	    	hctmhBean.setSoPhieunhanhang(sonhanhang);
	    	
	     	String ngayghinhan = util.antiSQLInspection(request.getParameter("ngayghinhan"));
	    	if(ngayghinhan == null)
	    		ngayghinhan = "";
	    	hctmhBean.setNgayghinhan(ngayghinhan);
	    	String dienGiaiCT = util.antiSQLInspection(request.getParameter("dienGiaiCT"));
	    	if(dienGiaiCT == null)
	    		dienGiaiCT = "";
	    	hctmhBean.setDienGiaiCT(dienGiaiCT);
	    	
	    	String somuahang = util.antiSQLInspection(request.getParameter("somuahang"));
	    	if(somuahang == null)
	    		somuahang = "";
	    	hctmhBean.setSoDonmuahang(somuahang);
	    	
	    	String[] soct = request.getParameterValues("sochungtuhuy");
	    	String[] thutu = request.getParameterValues("thutu");
	    	String[] ngaychungtu = request.getParameterValues("ngaychungtu");
	    	String[] trangthai = request.getParameterValues("trangthai");
	    	String[] ngaytao = request.getParameterValues("ngaytao");
	    	String[] loaichungtu = request.getParameterValues("loaichungtu");
	    	String[] sochungtuhuy = request.getParameterValues("sochungtuhuy");
;
	    	String[] sotienthanhtoan = request.getParameterValues("sotientt");	    	
	    	
			String action = request.getParameter("action");
			
			System.out.println("action:"+action);
			if(action.equals("save"))
			{
				if(id == null) //tao moi
				{	
					System.out.println("sochungtuhuy"+sochungtuhuy);
					
					if(!hctmhBean.createHct(sochungtuhuy, soct, ngaychungtu, trangthai, loaichungtu, ngaytao, thutu, sotienthanhtoan))
					{
						//System.out.println("Thong diep la: " + hctmhBean.getMsg() + "\n");
	    		    	hctmhBean.createRs();
	    		    
	    		    	session.setAttribute("hctmhBean", hctmhBean);
	    		    	
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuyThuTienNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpHuythutienList obj = new ErpHuythutienList();
						obj.setCongtyId((String)session.getAttribute("congtyId"));						
					    obj.setUserId(userId);
					    obj.setnppdangnhap(util.getIdNhapp(userId));
					    
						String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
				    	GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
					    obj.init("");
					    
						session.setAttribute("obj", obj);
								
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuyThuTien.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				else
				{
					if(!hctmhBean.updateHct(sochungtuhuy, soct, ngaychungtu, trangthai, loaichungtu, ngaytao, thutu, sotienthanhtoan))
					{
						hctmhBean.createRs();
		    		    
						session.setAttribute("hctmhBean", hctmhBean);
						
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuyThuTienUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpHuythutienList obj = new ErpHuythutienList();
						obj.setCongtyId((String)session.getAttribute("congtyId"));
					    obj.setUserId(userId);
					    obj.setnppdangnhap(util.getIdNhapp(userId));
						String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
				    	GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
					    obj.init("");
					    
						session.setAttribute("obj", obj);
								
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuyThuTien.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
				hctmhBean.createRs();		
				String nextJSP;
				if (id == null){
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuyThuTienNew.jsp";
				}
				else{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuyThuTienUpdate.jsp";   						
				}
				
				session.setAttribute("hctmhBean", hctmhBean);
				response.sendRedirect(nextJSP);
			}
		}
	}

}
