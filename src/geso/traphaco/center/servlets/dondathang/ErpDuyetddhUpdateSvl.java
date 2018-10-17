package geso.traphaco.center.servlets.dondathang;

import geso.traphaco.center.beans.dondathang.IErpDuyetddh;
import geso.traphaco.center.beans.dondathang.IErpDuyetddhList;
import geso.traphaco.center.beans.dondathang.imp.ErpDuyetddh;
import geso.traphaco.center.beans.dondathang.imp.ErpDuyetddhList;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpDuyetddhUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
    public ErpDuyetddhUpdateSvl() 
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
			response.sendRedirect("/TraphacoHYERP/index.jsp");
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
		    IErpDuyetddh lsxBean = new ErpDuyetddh(id);
		    lsxBean.setUserId(userId); 
		    
		    String nextJSP = "";
		    
    		lsxBean.init();
    		
    		String hopdongId = request.getParameter("hopdongId");
		    if(hopdongId == null)
		    	hopdongId = "";
    			
			nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHangDuyetDisplay.jsp";
			if(lsxBean.getLoaidonhang().equals("4"))
				nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHangKhacDuyetDisplay.jsp";
			else if(lsxBean.getLoaidonhang().equals("5"))
				nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangNoiBoDuyetDisplay.jsp";
			else if(lsxBean.getLoaidonhang().equals("0") && hopdongId.trim().length() > 0 )
				nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangETCDuyetDisplay.jsp";
    		
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
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
			
			session.setMaxInactiveInterval(30000);
			
			this.out = response.getWriter();
			IErpDuyetddh lsxBean;
			
			Utility util = new Utility();	
			String id = util.antiSQLInspection(request.getParameter("id"));
		    if(id == null)
		    {  	
		    	lsxBean = new ErpDuyetddh("");
		    }
		    else
		    {
		    	lsxBean = new ErpDuyetddh(id);
		    }
	
		    lsxBean.setUserId(userId);
		    
		    String ngayyeucau = util.antiSQLInspection(request.getParameter("ngaychuyen"));
		    if(ngayyeucau == null)
		    	ngayyeucau = "";
		    lsxBean.setNgayyeucau(ngayyeucau);
		    
		    String ngaydenghi = util.antiSQLInspection(request.getParameter("ngaydenghi"));
		    if(ngaydenghi == null )
		    	ngaydenghi = "";
		    lsxBean.setNgaydenghi(ngaydenghi);
		    	    
		    String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
		    if(ghichu == null)
		    	ghichu = "";
		    lsxBean.setGhichu(ghichu);
		    
			String khonhapId = util.antiSQLInspection(request.getParameter("khonhapId"));
			if (khonhapId == null)
				khonhapId = "";				
			lsxBean.setKhoNhapId(khonhapId);
			
			String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
			if (dvkdId == null)
				dvkdId = "";				
			lsxBean.setDvkdId(dvkdId);
			
			String kbhId = util.antiSQLInspection(request.getParameter("kbhId"));
			if (kbhId == null)
				kbhId = "";				
			lsxBean.setKbhId(kbhId);
			
			String nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if (nppId == null)
				nppId = "";				
			lsxBean.setNppId(nppId);
		    
			String loaidonhang = util.antiSQLInspection(request.getParameter("loaidonhang"));
			if (loaidonhang == null)
				loaidonhang = "";				
			lsxBean.setLoaidonhang(loaidonhang);
			
			String mahopdong = util.antiSQLInspection(request.getParameter("mahopdong")==null?"":request.getParameter("mahopdong"));
			lsxBean.setMahopdong(mahopdong);
			String gsbhId = util.antiSQLInspection(request.getParameter("gsbhId") == null ? "" : request.getParameter("gsbhId"));
			lsxBean.setGsbhId(gsbhId);
			String ddkdId = util.antiSQLInspection(request.getParameter("ddkdId") == null? "" : request.getParameter("ddkdId"));
			lsxBean.setDdkdId(ddkdId);
			
			String[] schemeIds = request.getParameterValues("schemeIds");
			if (schemeIds != null)
			{
				String _scheme = "";
				for(int i = 0; i < schemeIds.length; i++)
				{
					_scheme += schemeIds[i] + ",";
				}
				
				if(_scheme.trim().length() > 0)
				{
					_scheme = _scheme.substring(0, _scheme.length() - 1);
					lsxBean.setSchemeId(_scheme);
				}
			}
			
			String[] spMa = request.getParameterValues("spMa");
			lsxBean.setSpMa(spMa);
			
			String[] spTen = request.getParameterValues("spTen");
			lsxBean.setSpTen(spTen);
			
			String[] dvt = request.getParameterValues("donvi");
			lsxBean.setSpDonvi(dvt);
			
			String[] soluong = request.getParameterValues("soluong");
			lsxBean.setSpSoluong(soluong);
			
			String[] dongia = request.getParameterValues("dongia");
			lsxBean.setSpGianhap(dongia);
			
			String[] spvat = request.getParameterValues("spvat");
			lsxBean.setSpVat(spvat);
			
			String[] spScheme = request.getParameterValues("scheme");
			lsxBean.setSpScheme(spScheme);
			String[] is_khongthue = request.getParameterValues("is_khongthue");
			if(spMa != null && is_khongthue ==null)
	    	{
	    		is_khongthue = new String[spMa.length];
	    		for(int i =0;i<is_khongthue.length;i++)
	    		{
	    			is_khongthue[i] = "0";
	    		}
	    	}
			lsxBean.setSpIskhongthue(is_khongthue);
			if(spMa != null && !loaidonhang.equals("5") )  //LUU LAI THONG TIN NGUOI DUNG NHAP
			{
				Hashtable<String, String> sanpham_soluong = new Hashtable<String, String>();
				for(int i = 0; i < spMa.length; i++ )
				{
					String temID = spMa[i] + "__" + spScheme[i];
					//System.out.println("---TEMP ID: " + temID);
					
					String[] spSOLO = request.getParameterValues(temID + "_spSOLO");
					String[] spNGAYHETHAN = request.getParameterValues(temID + "_spNGAYHETHAN");
					String[] spVITRI = request.getParameterValues(temID + "_spVITRI");
					String[] spMATHUNG = request.getParameterValues(temID + "_spMATHUNG");
					String[] spMAME = request.getParameterValues(temID + "_spMAME");
					String[] soLUONGXUAT = request.getParameterValues(temID + "_spSOLUONG");
					
					if(soLUONGXUAT != null)
					{
						for(int j = 0; j < soLUONGXUAT.length; j++ )
						{
							//1 số sp không có lô
							//if(soLUONGXUAT[j] != null && soLUONGXUAT[j].trim().length() > 0 && spSOLO[j].trim().length() > 0 )
							if(soLUONGXUAT[j] != null && soLUONGXUAT[j].trim().length() > 0  )
							{
								System.out.println("---KEY SVL: " + ( spMa[i] + "__" + spScheme[i] + "__" + spSOLO[j] + "__" + spNGAYHETHAN[j] + "__" + spVITRI[j]  + "__" + spMATHUNG[j] + "__" + spMAME[j] ) + "   --- GIA TRI: " + soLUONGXUAT[j].replaceAll(",", "") );
								sanpham_soluong.put(spMa[i] + "__" + spScheme[i] + "__" + spSOLO[j] + "__" + spNGAYHETHAN[j] + "__" + spVITRI[j] + "__" + spMATHUNG[j] + "__" + spMAME[j], soLUONGXUAT[j].replaceAll(",", "") );
							}
						}
					}
				}
				
				lsxBean.setSanpham_Soluong(sanpham_soluong);
			}
			
			String action = util.antiSQLInspection(request.getParameter("action"));
			if (action == null)
				action = "";				
			
			boolean kq = false;
			if(action.equals("save"))
				kq = lsxBean.saveDDH(request);
			else
				kq = lsxBean.duyetDDH(request);
			
			if(!kq)
			{
				lsxBean.init();
				session.setAttribute("lsxBean", lsxBean);
				String nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHangDuyetDisplay.jsp";
				if(lsxBean.getLoaidonhang().equals("4"))
					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHangKhacDuyetDisplay.jsp";
				/*else if(lsxBean.getLoaidonhang().equals("2"))
					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangKMTichLuyDuyetDisplay.jsp";
				else if(lsxBean.getLoaidonhang().equals("1"))
					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangKMUngHangDuyetDisplay.jsp";
				else if(lsxBean.getLoaidonhang().equals("3"))
					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangTrungBayDuyetDisplay.jsp";
				else if(lsxBean.getLoaidonhang().equals("5"))
					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangNoiBoDuyetDisplay.jsp";*/
				
				response.sendRedirect(nextJSP);
			}
			else
			{
				IErpDuyetddhList obj = new ErpDuyetddhList();
				obj.setLoaidonhang(loaidonhang);
				
			    obj.setUserId(userId);
			    obj.init("");
				session.setAttribute("obj", obj);							
				String nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHangDuyet.jsp";
				response.sendRedirect(nextJSP);
			}
		}	
	}
	
	
}
