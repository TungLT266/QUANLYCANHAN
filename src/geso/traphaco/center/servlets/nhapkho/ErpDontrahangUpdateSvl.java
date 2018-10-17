package geso.traphaco.center.servlets.nhapkho;

import geso.traphaco.center.util.Utility;
import geso.traphaco.center.beans.nhapkho.IErpDontrahang;
import geso.traphaco.center.beans.nhapkho.IErpDontrahangList;
import geso.traphaco.center.beans.nhapkho.imp.ErpDontrahang;
import geso.traphaco.center.beans.nhapkho.imp.ErpDontrahangList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpDontrahangUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
    public ErpDontrahangUpdateSvl() 
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
		    IErpDontrahang lsxBean = new ErpDontrahang(id);
		    lsxBean.setUserId(userId); 
		    
		    String nextJSP = "";
		    
    		lsxBean.init();
    		
    		if(!querystring.contains("display"))
    			nextJSP = "/TraphacoHYERP/pages/Center/ErpDonTraHangUpdate.jsp";
    		else
    			nextJSP = "/TraphacoHYERP/pages/Center/ErpDonTraHangDisplay.jsp";
    		
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
			IErpDontrahang lsxBean;
			
			Utility util = new Utility();	
			String id = util.antiSQLInspection(request.getParameter("id"));
		    if(id == null)
		    {  	
		    	lsxBean = new ErpDontrahang("");
		    }
		    else
		    {
		    	lsxBean = new ErpDontrahang(id);
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
		    
			String xuatchoId = util.antiSQLInspection(request.getParameter("xuatchoId"));
			if (xuatchoId == null)
				xuatchoId = "0";				
			lsxBean.setXuatcho(xuatchoId);
			
			String sochungtu = util.antiSQLInspection(request.getParameter("sochungtu"));
			if (sochungtu == null)
				sochungtu = "0";				
			lsxBean.setSOhoadon(sochungtu);
			
			
			String kyhieu = util.antiSQLInspection(request.getParameter("kyhieu"));
			if (kyhieu == null)
				kyhieu = "0";				
			lsxBean.setKyhieuHd(kyhieu);
			
			String tongtienBVAT = util.antiSQLInspection(request.getParameter("tongtienBVAT"));
			if (tongtienBVAT == null)
				tongtienBVAT = "0";				
			lsxBean.setTongtienbvat(tongtienBVAT.replaceAll(",",""));
			
			String tongtienVAT = util.antiSQLInspection(request.getParameter("tongtienVAT"));
			if (tongtienVAT == null)
				tongtienVAT = "0";				
			lsxBean.setTongtienvat(tongtienVAT.replaceAll(",",""));
			
			
			String tongtienAVAT = util.antiSQLInspection(request.getParameter("tongtienAVAT"));
			if (tongtienAVAT == null)
				tongtienAVAT = "0";				
			lsxBean.setTongtienavat(tongtienAVAT.replaceAll(",",""));
			
			
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
			
			String[] tienvat = request.getParameterValues("tienvat");
			lsxBean.setTienvat(tienvat);
			
			if(spMa != null)
			{
				Hashtable<String, String> sp_chitiet = new Hashtable<String, String>();
				for(int i = 0; i < spMa.length; i++)
				{
					String[] spSOLUONG = request.getParameterValues(spMa[i] + "_spSOLUONG");
					String[] solo = request.getParameterValues(spMa[i] + "_spSOLO");
					String[] ngayhethan = request.getParameterValues(spMa[i] + "_spNGAYHETHAN");
					if( solo != null )
					{
						for(int j = 0; j < solo.length; j++)
						{
							if( spSOLUONG[j].trim().length() > 0 && !spSOLUONG[j].equals("0") )
							{
								String key = spMa[i] + "__" + solo[j] + "__" + ngayhethan[j] + "__" + tienvat[i];
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
	    				String nextJSP = "/TraphacoHYERP/pages/Center/ErpDonTraHangNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpDontrahangList obj = new ErpDontrahangList();
						obj.setUserId(userId);
						obj.init("");  
				    	session.setAttribute("obj", obj);  	
			    		session.setAttribute("userId", userId);
				
			    		response.sendRedirect("/TraphacoHYERP/pages/Center/ErpDonTraHang.jsp");
					}
				}
				else
				{
					if(!lsxBean.updateNK())
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Center/ErpDonTraHangUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpDontrahangList obj = new ErpDontrahangList();
					    obj.setUserId(userId);
					    obj.init("");
						session.setAttribute("obj", obj);							
						String nextJSP = "/TraphacoHYERP/pages/Center/ErpDonTraHang.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
				lsxBean.createRs();
				
				session.setAttribute("lsxBean", lsxBean);
				
				String nextJSP = "";
				
				nextJSP = "/TraphacoHYERP/pages/Center/ErpDonTraHangNew.jsp";
				if(id != null)
					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonTraHangUpdate.jsp";
				
				response.sendRedirect(nextJSP);
			}
		}
	}
	
	
}
