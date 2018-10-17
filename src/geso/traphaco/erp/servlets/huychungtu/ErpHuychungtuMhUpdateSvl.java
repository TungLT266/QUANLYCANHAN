package geso.traphaco.erp.servlets.huychungtu;

import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.beans.huychungtu.IErpHuychungtumuahang;
import geso.traphaco.erp.beans.huychungtu.IErpHuychungtumuahangList;
import geso.traphaco.erp.beans.huychungtu.imp.ErpHuychungtumuahang;
import geso.traphaco.erp.beans.huychungtu.imp.ErpHuychungtumuahangList;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpHuychungtuMhUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpHuychungtuMhUpdateSvl() {
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
			IErpHuychungtumuahang hctmhBean = new ErpHuychungtumuahang(id);
			hctmhBean.setCongtyId((String)session.getAttribute("congtyId"));
	        hctmhBean.setUserId(userId); //phai co UserId truoc khi Init
	        hctmhBean.init();
	    	
	        String nextJSP;
	        if(request.getQueryString().indexOf("display") >= 0 ) 
	        {
	        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuyChungTuMuaHangDisplay.jsp";
	        }
	        else
	        {
	        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuyChungTuMuaHangUpdate.jsp";
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
			IErpHuychungtumuahang hctmhBean;
			
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			
		    if(id == null)
		    {  	
		    	hctmhBean = new ErpHuychungtumuahang("");
		    }
		    else
		    {
		    	hctmhBean = new ErpHuychungtumuahang(id);
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
	    	
	     	String ngayghinhan = util.antiSQLInspection(request.getParameter("ngayghinhan"));
	    	if(ngayghinhan == null)
	    		ngayghinhan = "";
	    	hctmhBean.setNgayghinhan(ngayghinhan);
	    	
	    	String somuahang = util.antiSQLInspection(request.getParameter("somuahang"));
	    	if(somuahang == null)
	    		somuahang = "";
	    	hctmhBean.setSoDonmuahang(somuahang);
	    	
	    	String sodntt = util.antiSQLInspection(request.getParameter("sodntt"));
	    	if(sodntt == null)
	    		sodntt = "";
	    	hctmhBean.setSoDeNghiThanhToan(sodntt);


	    	String sothuenhapkhau = util.antiSQLInspection(request.getParameter("sothuenhapkhau"));
	    	if(sothuenhapkhau == null)
	    		sothuenhapkhau = "";
	    	hctmhBean.setSoThuenhapkhau(sothuenhapkhau);
	    	
	    	String soxuatkhotrave = util.antiSQLInspection(request.getParameter("soxuatkhotrave"));
	    	if(soxuatkhotrave == null)
	    		soxuatkhotrave = "";
	    	hctmhBean.setSoxuatkhotrave(soxuatkhotrave);
	    	
	    	
	    	
	    	String loaict = util.antiSQLInspection(request.getParameter("loaict"));
	    	if(loaict == null)
	    		loaict = "";
	    	hctmhBean.setLoaictId(loaict);
	    	System.out.println(" loai chung tu: "+ loaict);
	    	
	    	String sopkd = util.antiSQLInspection(request.getParameter("sopkd"));
	    	if(sopkd == null)
	    		sopkd = "";
	    	hctmhBean.setSoPhieukiemdinh(sopkd);
	    	
	    	String sohoadontrahang = util.antiSQLInspection(request.getParameter("sohoadontrahang"));
	    	if(sohoadontrahang == null)
	    		sohoadontrahang = "";
	    	hctmhBean.setSohoadontrahang(sohoadontrahang);
	    	
	    	
	    	String sodontrahang = util.antiSQLInspection(request.getParameter("sodontrahang"));
	    	if(sodontrahang == null)
	    		sodontrahang = "";
	    	hctmhBean.setSoDontrahang(sodontrahang);
	    	
	    	
	    	String sohoadondieuchinhncc = util.antiSQLInspection(request.getParameter("sohoadondieuchinhncc"));
	    	if(sohoadondieuchinhncc == null)
	    		sohoadondieuchinhncc = "";
	    	hctmhBean.setSohoadondieuchinhncc(sohoadondieuchinhncc);
	    	
	    	String sotieuhaogiacong = util.antiSQLInspection(request.getParameter("sotieuhaogiacong"));
	    	if(sotieuhaogiacong == null)
	    		sotieuhaogiacong = "";
	    	hctmhBean.setSotieuhaogiacong(sotieuhaogiacong);
	    	
	    	String sophieuchuyenkho = util.antiSQLInspection(request.getParameter("sophieuchuyenkho"));
	    	if(sophieuchuyenkho == null)
	    		sophieuchuyenkho = "";
	    	hctmhBean.setSophieuchuyenkho(sophieuchuyenkho);
	    	
	    	String sochiphinhanhang = util.antiSQLInspection(request.getParameter("sochiphinhanhang"));
	    	if(sochiphinhanhang == null)
	    		sochiphinhanhang = "";
	    	hctmhBean.setSochiphinhanhang(sochiphinhanhang);
	    	
	    	
	    	String[] soct = request.getParameterValues("sochungtu1");
	    	String[] thutu = request.getParameterValues("thutu");
	    	String[] ngaychungtu = request.getParameterValues("ngaychungtu");
	    	String[] trangthai = request.getParameterValues("trangthai");
	    	String[] loaichungtu = request.getParameterValues("loaichungtu");
	    	String[] ngaytao = request.getParameterValues("ngaytao");
	    	String[] chon = request.getParameterValues("chon");
	    	String[] type = request.getParameterValues("type");
	    	String[] sochungtuhuy = request.getParameterValues("sochungtuhuy");
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
					if(!hctmhBean.createHct(sochungtuhuy, soct, ngaychungtu, trangthai, loaichungtu, ngaytao, thutu,type,chon))
					{
	    		    	hctmhBean.createRs();
	    		    	session.setAttribute("hctmhBean", hctmhBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuyChungTuMuaHangNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpHuychungtumuahangList obj = new ErpHuychungtumuahangList();
						obj.setCongtyId((String)session.getAttribute("congtyId"));
					    obj.setUserId(userId);
					    obj.init("");
					    
						session.setAttribute("obj", obj);
								
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuyChungTuMuaHang.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			 
			}
			else
			{
				hctmhBean.createRs();		
				String nextJSP;
				if (id == null){
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuyChungTuMuaHangNew.jsp";
				}
				else{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuyChungTuMuaHangUpdate.jsp";   						
				}
				
				session.setAttribute("hctmhBean", hctmhBean);
				response.sendRedirect(nextJSP);
			}
		}
	}

}
