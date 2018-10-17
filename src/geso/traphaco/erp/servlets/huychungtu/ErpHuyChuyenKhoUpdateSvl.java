package geso.traphaco.erp.servlets.huychungtu;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.huychungtu.IErpHuyChuyenKho;
import geso.traphaco.erp.beans.huychungtu.IErpHuyChuyenKhoList;
import geso.traphaco.erp.beans.huychungtu.imp.ErpHuyChuyenKho;
import geso.traphaco.erp.beans.huychungtu.imp.ErpHuyChuyenKhoList;
 
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpHuyChuyenKhoUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpHuyChuyenKhoUpdateSvl() {
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
		    out.println(userId);
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId"));
		    
		    String id = util.getId(querystring);  	
			IErpHuyChuyenKho hctmhBean = new ErpHuyChuyenKho(id);
			hctmhBean.setCongtyId((String)session.getAttribute("congtyId"));
	        hctmhBean.setUserId(userId); //phai co UserId truoc khi Init
	        hctmhBean.init();
	        String nextJSP;
	        nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuyChuyenKhoDisplay.jsp";
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
			IErpHuyChuyenKho hctmhBean;
			
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			
		    if(id == null)
		    {  	
		    	hctmhBean = new ErpHuyChuyenKho("");
		    }
		    else
		    {
		    	hctmhBean = new ErpHuyChuyenKho(id);
		    }
	
		    hctmhBean.setCongtyId((String)session.getAttribute("congtyId"));
		    hctmhBean.setUserId(userId);
			
		    String sochungtu = util.antiSQLInspection(request.getParameter("sochungtu"));
			if (sochungtu == null)
				sochungtu = "";		
	    	hctmhBean.setSochungtu(sochungtu);
	    	
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
	    	
	    	String loaichungtu1 = util.antiSQLInspection(request.getParameter("loaichungtu1"));
	    	if(loaichungtu1 == null)
	    		loaichungtu1 = "";
	    	hctmhBean.setloaichungtu(loaichungtu1);
	    	
	     	String ngayghinhan = util.antiSQLInspection(request.getParameter("ngayghinhan"));
	    	if(ngayghinhan == null)
	    		ngayghinhan = "";
	    	hctmhBean.setNgayghinhan(ngayghinhan);
	    	
	    	String somuahang = util.antiSQLInspection(request.getParameter("somuahang"));
	    	if(somuahang == null)
	    		somuahang = "";
	    	hctmhBean.setSoDonmuahang(somuahang);
	    	
	    	String[] thutu = request.getParameterValues("thutu");
	    	String[] ngaychungtu = request.getParameterValues("ngaychungtu");
	    	String[] trangthai = request.getParameterValues("trangthai");
	    	String[] loaichungtu = request.getParameterValues("loaichungtu");
	    	String[] soct = request.getParameterValues("sochungtuhuy");
	    	String[] ngaytao = request.getParameterValues("ngaytao");
	    	String[] sochungtuhuy = request.getParameterValues("sochungtuhuy");
	    	String[] type = request.getParameterValues("type");
	    	String sochungtu_chonhuy="";
	    	if(sochungtuhuy!=null){
	    		for(int i=0;i<sochungtuhuy.length;i++){
	    			sochungtu_chonhuy=sochungtu_chonhuy+","+sochungtuhuy[i];
	    		}
	    	}
	    	
	    	hctmhBean.setSochungtu_chonhuy(sochungtu_chonhuy);
	    	
			String action = request.getParameter("action");
			if(action.equals("save"))
			{
				if(id == null) //tao moi
				{
					if(!hctmhBean.createHct(sochungtuhuy, soct, ngaychungtu, trangthai, loaichungtu, ngaytao, thutu,type))
					{
	    		    	hctmhBean.createRs();
	    		    	session.setAttribute("hctmhBean", hctmhBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuyChuyenKhoNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpHuyChuyenKhoList obj = new ErpHuyChuyenKhoList();
						obj.setCongtyId((String)session.getAttribute("congtyId"));
					    obj.setUserId(userId);
					    obj.init("");
					    hctmhBean.DbClose();
						session.setAttribute("obj", obj);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuyChuyenKho.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				  
			}
			else
			{
				hctmhBean.createRs();		
				String nextJSP;
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuyChuyenKhoNew.jsp";
				session.setAttribute("hctmhBean", hctmhBean);
				response.sendRedirect(nextJSP);
			}
		}
	}

}
