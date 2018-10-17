package geso.traphaco.erp.servlets.yeucauchuyenkho;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpNhapkhotrave;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpNhapkhotraveList;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpNhapkhotrave;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpNhapkhotraveList;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpNhapkhotraveUpdateSvl extends HttpServlet 
{
private static final long serialVersionUID = 1L;
	
	PrintWriter out;
    public ErpNhapkhotraveUpdateSvl() 
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
			response.sendRedirect("/OneoOne/index.jsp");
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
		    IErpNhapkhotrave lsxBean = new ErpNhapkhotrave(id);
		    lsxBean.setUserId(userId); 
		    
		    String nextJSP = "";
		    
    		lsxBean.init();
    		session.setAttribute("khonhanIds", lsxBean.getKhoNhapId());
    		
    		if(!querystring.contains("display"))
    			nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoTraVeUpdate.jsp";
    		else
    			nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoTraVeDisplay.jsp";
    		
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
			response.sendRedirect("/OneoOne/index.jsp");
		}
		else
		{
			request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
			
			session.setMaxInactiveInterval(30000);
			
			this.out = response.getWriter();
			IErpNhapkhotrave lsxBean;
			
			Utility util = new Utility();	
			String id = util.antiSQLInspection(request.getParameter("id"));
		    if(id == null)
		    {  	
		    	lsxBean = new ErpNhapkhotrave("");
		    }
		    else
		    {
		    	lsxBean = new ErpNhapkhotrave(id);
		    }
	
		    lsxBean.setUserId(userId);
		    
		    String tungay = util.antiSQLInspection(request.getParameter("tungay"));
		    if(tungay == null)
		    	tungay = "";
		    lsxBean.setTungay(tungay);
		    
		    String denngay = util.antiSQLInspection(request.getParameter("denngay"));
		    if(denngay == null)
		    	denngay = "";
		    lsxBean.setDenngay(denngay);
		    	    
		    String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
		    if(ghichu == null)
		    	ghichu = "";
		    lsxBean.setGhichu(ghichu);
		    
		    String kyhieu = util.antiSQLInspection(request.getParameter("kyhieu"));
		    if(kyhieu == null)
		    	kyhieu = "";
		    lsxBean.setKyhieuHd(kyhieu);
		    
		    String sohoadon = util.antiSQLInspection(request.getParameter("sohoadon"));
		    if(sohoadon == null)
		    	sohoadon = "";
		    lsxBean.setSOhoadon(sohoadon);
		    
			String xuatchoId = util.antiSQLInspection(request.getParameter("xuatchoId"));
			if (xuatchoId == null)
				xuatchoId = "0";				
			lsxBean.setXuatcho(xuatchoId);
			
			String khonhapId = util.antiSQLInspection(request.getParameter("khonhapId"));
			if (khonhapId == null)
				khonhapId = "";				
			lsxBean.setKhoNhapId(khonhapId);
			session.setAttribute("khonhanIds", khonhapId);
			
			String[] khIds = request.getParameterValues("khId");	
			String khId = "";
			if( khIds != null )
			{
				for( int i = 0; i < khIds.length; i++ )
					khId += khIds[i] + ",";
				
				if( khId.trim().length() > 0 )
					khId = khId.substring(0, khId.length() - 1);
			}
			lsxBean.setKhId(khId);
			
			String[] spMa = request.getParameterValues("spMa");
			lsxBean.setSpMa(spMa);
			
			String[] spHsd = request.getParameterValues("spHansd");
			lsxBean.setSpHansudung(spHsd);
			
			String[] spTen = request.getParameterValues("spTen");
			lsxBean.setSpTen(spTen);
			
			String[] dvt = request.getParameterValues("donvi");
			lsxBean.setSpDonvi(dvt);
			
			String[] soluong = request.getParameterValues("soluong");
			lsxBean.setSpSoluong(soluong);
			
			String[] dongia = request.getParameterValues("dongia");
			lsxBean.setSpGianhap(dongia);
			
			String[] vat = request.getParameterValues("vat");
			lsxBean.setSpVat(vat);
			
			String[] tienVat = request.getParameterValues("tienVat");
			lsxBean.setSpTienVat(tienVat);
			
			if(spMa != null)
			{
				Hashtable<String, String> sp_chitiet = new Hashtable<String, String>();
				for(int i = 0; i < spMa.length; i++)
				{
					String temID = spMa[i];
					
					String[] solo = request.getParameterValues(spMa[i] + "_spSOLO");
					String[] ngayhethan = request.getParameterValues(spMa[i] + "_spNGAYHETHAN");
					
					String[] spMAME = request.getParameterValues(temID + "_spMAME");
					String[] spMATHUNG = request.getParameterValues(temID + "_spMATHUNG");
					String[] spMAPHIEU = request.getParameterValues(temID + "_spMAPHIEU");
					
					String[] spMERQ = request.getParameterValues(temID + "_spMarq");
					String[] spHAMLUONG = request.getParameterValues(temID + "_spHamluong");
					String[] spHAMAM = request.getParameterValues(temID + "_spHamam");
					
					String[] spVITRI = request.getParameterValues(temID + "_spVitri");
					String[] spPHIEUDT = request.getParameterValues(temID + "_spPhieudt");
					String[] spPHIEUEO = request.getParameterValues(temID + "_spPhieueo");
					
					String[] spSOLUONG = request.getParameterValues(spMa[i] + "_spSOLUONG");
					
					if( solo != null )
					{
						for(int j = 0; j < solo.length; j++)
						{
							if( spSOLUONG[j].trim().length() > 0 && !spSOLUONG[j].equals("0") )
							{
								//Trường hợp nhận hàng, kho có thể không quản lý vị trí nhận
								String key = "";
								if( spVITRI == null )
								{
									key = spMa[i] + "__" + solo[j] + "__" + ngayhethan[j] 
											+ "__" + spMAME[j] + "__" + spMATHUNG[j] + "__0"
											+ "__" + spMAPHIEU[j] + "__" + spPHIEUDT[j] + "__" + spPHIEUEO[j]
											+ "__" + spMERQ[j] + "__" + spHAMLUONG[j] + "__" + spHAMAM[j];
								}
								else
								{
									key = spMa[i] + "__" + solo[j] + "__" + ngayhethan[j] 
											+ "__" + spMAME[j] + "__" + spMATHUNG[j] + "__" + spVITRI[j] 
											+ "__" + spMAPHIEU[j] + "__" + spPHIEUDT[j] + "__" + spPHIEUEO[j]
											+ "__" + spMERQ[j] + "__" + spHAMLUONG[j] + "__" + spHAMAM[j];
								}
								
								//String key = spMa[i] + "__" + solo[j] + "__" + ngayhethan[j];
								System.out.println("::: KEY SVL: " + key);
								sp_chitiet.put(key, spSOLUONG[j]);
							}
						}
					}
				}
				lsxBean.setSp_Chitiet(sp_chitiet);
			}
			
		    String action = request.getParameter("action");
			if(action.equals("save"))
			{	
				if(id == null)
				{
					if(!lsxBean.createNK())
					{
						lsxBean.createRs();
	    		    	session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoTraVeNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpNhapkhotraveList obj = new ErpNhapkhotraveList();
						obj.setUserId(userId);
						obj.init("");  
				    	session.setAttribute("obj", obj);  	
			    		session.setAttribute("userId", userId);
				
			    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpNhapKhoTraVe.jsp");
					}
				}
				else
				{
					if(!lsxBean.updateNK())
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoTraVeUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpNhapkhotraveList obj = new ErpNhapkhotraveList();
					    obj.setUserId(userId);
					    obj.init("");
						session.setAttribute("obj", obj);							
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoTraVe.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
				lsxBean.createRs();
				
				session.setAttribute("lsxBean", lsxBean);
				
				String nextJSP = "";
				
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoTraVeNew.jsp";
				if(id != null)
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoTraVeUpdate.jsp";
				
				response.sendRedirect(nextJSP);
			}
		}
	}
}
