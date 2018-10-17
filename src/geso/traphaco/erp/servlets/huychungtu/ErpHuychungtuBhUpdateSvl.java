package geso.traphaco.erp.servlets.huychungtu;

 
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.huychungtu.IErpHuychungtubanhang;
import geso.traphaco.erp.beans.huychungtu.IErpHuychungtubanhangList;
import geso.traphaco.erp.beans.huychungtu.imp.ErpHuychungtubanhang;
import geso.traphaco.erp.beans.huychungtu.imp.ErpHuychungtubanhangList;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpHuychungtuBhUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
  
    public ErpHuychungtuBhUpdateSvl() {
        super();
    }
    
    PrintWriter out;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		 Utility cutil = (Utility) session.getAttribute("util");
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
		    
		    String ctyId = (String)session.getAttribute("congtyId");
		    out.println(userId);
		    
		    String id = util.getId(querystring);  	
			IErpHuychungtubanhang hctbhBean = new ErpHuychungtubanhang(id);
	        hctbhBean.setUserId(userId); //phai co UserId truoc khi Init
	        hctbhBean.setCongtyId(ctyId);
	        hctbhBean.init();
	        
	        String nppId = util.getIdNhapp(userId);
		    if(nppId == null)
		    	nppId = "";
		    hctbhBean.setnppId(util.getIdNhapp(userId));
		    
	    	
	        String nextJSP;
	        if(request.getQueryString().indexOf("display") >= 0 ) 
	        {
	        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuyChungTuBanHangDisplay.jsp";
	        }
	        else
	        {
	        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuyChungTuBanHangUpdate.jsp";
	        }
	        
	        session.setAttribute("hctbhBean", hctbhBean);
	        response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen"); 
		
		String sum = (String) session.getAttribute("sum");
		Utility cutil = (Utility) session.getAttribute("util");
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
			IErpHuychungtubanhang hctbhBean;
			
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			String ctyId = (String)session.getAttribute("congtyId");
			
		    if(id == null)
		    {  	
		    	hctbhBean = new ErpHuychungtubanhang("");
		    }
		    else
		    {
		    	hctbhBean = new ErpHuychungtubanhang(id);
		    }
	
		    hctbhBean.setUserId(userId);
		    hctbhBean.setCongtyId(ctyId);
		    
		 	String loaichungtu1 = util.antiSQLInspection(request.getParameter("loaichungtu1"));
	    	if(loaichungtu1 == null)
	    		loaichungtu1 = "";
	    	hctbhBean.setloaichungtu(loaichungtu1);
			
		    String sochungtu = util.antiSQLInspection(request.getParameter("sochungtu"));
			if (sochungtu == null)
				sochungtu = "";		
	    	hctbhBean.setSochungtu(sochungtu);
	    	
	    	String sohoadon = util.antiSQLInspection(request.getParameter("sohoadon"));
	    	if(sohoadon == null)
	    		sohoadon = "";
	    	hctbhBean.setSoHoadon(sohoadon);
	    	
	    	String soxuatkho = util.antiSQLInspection(request.getParameter("soxuatkho"));
	    	if(soxuatkho == null)
	    		soxuatkho = "";
	    	hctbhBean.setSoPhieuxuatkho(soxuatkho);
	    	
	    	String sodathang = util.antiSQLInspection(request.getParameter("sodathang"));
	    	if(sodathang == null)
	    		sodathang = "";
	    	hctbhBean.setSoDondathang(sodathang);
	   
	    	String nppId = util.getIdNhapp(userId);
		    if(nppId == null)
		    	nppId = "";
		    hctbhBean.setnppId(util.getIdNhapp(userId));
		    
		    // FORM IN BIÊN BẢN
		    String sobienban = util.antiSQLInspection(request.getParameter("sobienban"));
		    if(sobienban == null)
		    	sobienban = "";
		    hctbhBean.setSobienban(sobienban);
		    
		    String ngaybienban = util.antiSQLInspection(request.getParameter("ngaybienban"));
		    if(ngaybienban == null)
		    	ngaybienban = "";
		    hctbhBean.setNgaybienban(ngaybienban);
		    
		    String ngaybb = util.antiSQLInspection(request.getParameter("ngaybb"));
		    if(ngaybb == null)
		    	ngaybb = "";
		    hctbhBean.setNgaybb(ngaybb);
		    
		    String benA_bb = util.antiSQLInspection(request.getParameter("benA_bb"));
		    if(benA_bb == null)
		    	benA_bb = "";
		    hctbhBean.setBenA_bb(benA_bb);
		    
		    String diachiA_bb = util.antiSQLInspection(request.getParameter("diachiA_bb"));
		    if(diachiA_bb == null)
		    	diachiA_bb = "";
		    hctbhBean.setDiachiA_bb(diachiA_bb);
		    
		    String dienthoaiA_bb = util.antiSQLInspection(request.getParameter("dienthoaiA_bb"));
		    if(dienthoaiA_bb == null)
		    	dienthoaiA_bb = "";
		    hctbhBean.setDienthoaiA_bb(dienthoaiA_bb);
		    
		    String MstA_bb = util.antiSQLInspection(request.getParameter("MstA_bb"));
		    if(MstA_bb == null)
		    	MstA_bb = "";
		    hctbhBean.setMstA_bb(MstA_bb);
		    
		    String OngbaA_bb = util.antiSQLInspection(request.getParameter("OngbaA_bb"));
		    if(OngbaA_bb == null)
		    	OngbaA_bb = "";
		    hctbhBean.setOngbaA_bb(OngbaA_bb);
		    
		    String chucvuA_bb = util.antiSQLInspection(request.getParameter("chucvuA_bb"));
		    if(chucvuA_bb == null)
		    	chucvuA_bb = "";
		    hctbhBean.setChucvuA_bb(chucvuA_bb);
		    
		    String benB_bb = util.antiSQLInspection(request.getParameter("benB_bb"));
		    if(benB_bb == null)
		    	benB_bb = "";
		    hctbhBean.setBenB_bb(benB_bb);
		    
		    String diachiB_bb = util.antiSQLInspection(request.getParameter("diachiB_bb"));
		    if(diachiB_bb == null)
		    	diachiB_bb = "";
		    hctbhBean.setDiachiB_bb(diachiB_bb);
		    
		    String dienthoaiB_bb = util.antiSQLInspection(request.getParameter("dienthoaiB_bb"));
		    if(dienthoaiB_bb == null)
		    	dienthoaiB_bb = "";
		    hctbhBean.setDienthoaiB_bb(dienthoaiB_bb);
		    
		    String MstB_bb = util.antiSQLInspection(request.getParameter("MstB_bb"));
		    if(MstB_bb == null)
		    	MstB_bb = "";
		    hctbhBean.setMstB_bb(MstB_bb);
		    
		    String OngbaB_bb = util.antiSQLInspection(request.getParameter("OngbaB_bb"));
		    if(OngbaB_bb == null)
		    	OngbaB_bb = "";
		    hctbhBean.setOngbaB_bb(OngbaB_bb);
		    
		    String chucvuB_bb = util.antiSQLInspection(request.getParameter("chucvuB_bb"));
		    if(chucvuB_bb == null)
		    	chucvuB_bb = "";
		    hctbhBean.setChucvuB_bb(chucvuB_bb);
		    
		    String sohoadon1_bb = util.antiSQLInspection(request.getParameter("sohoadon1_bb"));
		    if(sohoadon1_bb == null)
		    	sohoadon1_bb = "";
		    hctbhBean.setSohoadon1_bb(sohoadon1_bb);
		    
		    String kyhieu1_bb = util.antiSQLInspection(request.getParameter("kyhieu1_bb"));
		    if(kyhieu1_bb == null)
		    	kyhieu1_bb = "";
		    hctbhBean.setKyhieu1_bb(kyhieu1_bb);
		    
		    String sohoadon2_bb = util.antiSQLInspection(request.getParameter("sohoadon2_bb"));
		    if(sohoadon2_bb == null)
		    	sohoadon2_bb = "";
		    hctbhBean.setSohoadon2_bb(sohoadon2_bb);
		    
		    String ngayhoadon1_bb = util.antiSQLInspection(request.getParameter("ngayhoadon1_bb"));
		    if(ngayhoadon1_bb == null)
		    	ngayhoadon1_bb = "";
		    hctbhBean.setNgayhoadon1_bb(ngayhoadon1_bb);
		    
		    String sohoadon3_bb = util.antiSQLInspection(request.getParameter("sohoadon3_bb"));
		    if(sohoadon3_bb == null)
		    	sohoadon3_bb = "";
		    hctbhBean.setSohoadon3_bb(sohoadon3_bb);
		    
		    String kyhieu2_bb = util.antiSQLInspection(request.getParameter("kyhieu2_bb"));
		    if(kyhieu2_bb == null)
		    	kyhieu2_bb = "";
		    hctbhBean.setKyhieu2_bb(kyhieu2_bb);
		    
		    String sohoadon4_bb = util.antiSQLInspection(request.getParameter("sohoadon4_bb"));
		    if(sohoadon4_bb == null)
		    	sohoadon4_bb = "";
		    hctbhBean.setSohoadon4_bb(sohoadon4_bb);
		    
		    String ngayhoadon2_bb = util.antiSQLInspection(request.getParameter("ngayhoadon2_bb"));
		    if(ngayhoadon2_bb == null)
		    	ngayhoadon2_bb = "";
		    hctbhBean.setNgayhoadon2_bb(ngayhoadon2_bb);
		    
		    String Lydothuhoi_bb = util.antiSQLInspection(request.getParameter("Lydothuhoi_bb"));
		    if(Lydothuhoi_bb == null)
		    	Lydothuhoi_bb = "";
		    hctbhBean.setLydothuhoi_bb(Lydothuhoi_bb);
			    
	    	String[] soct = request.getParameterValues("sochungtuhuy");
	    	String[] thutu = request.getParameterValues("thutu");
	    	String[] ngaychungtu = request.getParameterValues("ngaychungtu");
	    	String[] trangthai = request.getParameterValues("trangthai");
	    	String[] loaichungtu = request.getParameterValues("loaichungtu");
	    	String[] ngaytao = request.getParameterValues("ngaytao");
	    	
	    	String[] sochungtuhuy = request.getParameterValues("sochungtuhuy");
	    	
			String action = request.getParameter("action");
			
			System.out.println("loaichungtu:"+loaichungtu1);
			if(action.equals("save"))
			{
				if(id == null) //tao moi
				{
					if(!hctbhBean.createHct(sochungtuhuy, soct, ngaychungtu, trangthai, loaichungtu, ngaytao, thutu))
					{
	    		    	hctbhBean.createRs();
	    		    	
	    		    	session.setAttribute("nppId", "");
	    		    	session.setAttribute("hctbhBean", hctbhBean);
	    		    	
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuyChungTuBanHangNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpHuychungtubanhangList obj = new ErpHuychungtubanhangList();
					    obj.setUserId(userId);
					    obj.init("");
						session.setAttribute("obj", obj);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuyChungTuBanHang.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				else
				{
					if(!hctbhBean.updateHct(sochungtuhuy, soct, ngaychungtu, trangthai, loaichungtu, ngaytao, thutu))
					{
	    		    	hctbhBean.createRs();
	    		    	
	    		    	session.setAttribute("nppId", "");
	    		    	session.setAttribute("hctbhBean", hctbhBean);
	    		    	
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuyChungTuBanHangUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpHuychungtubanhangList obj = new ErpHuychungtubanhangList();
					    obj.setUserId(userId);
					    obj.init("");
						session.setAttribute("obj", obj);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuyChungTuBanHang.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				
				
			}
			else
			{
				hctbhBean.createRs();		
				String nextJSP;
				if (id == null){
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuyChungTuBanHangNew.jsp";
				}
				else{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuyChungTuBanHangUpdate.jsp";   						
				}
				
				session.setAttribute("hctbhBean", hctbhBean);
				response.sendRedirect(nextJSP);
			}
		}
	}

}
