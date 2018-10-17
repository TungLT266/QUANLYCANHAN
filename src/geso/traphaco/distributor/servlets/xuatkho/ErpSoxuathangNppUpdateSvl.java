package geso.traphaco.distributor.servlets.xuatkho;

import geso.traphaco.distributor.beans.xuatkho.IErpSoxuathangNpp;
import geso.traphaco.distributor.beans.xuatkho.IErpSoxuathangNppList;
import geso.traphaco.distributor.beans.xuatkho.imp.ErpSoxuathangNpp;
import geso.traphaco.distributor.beans.xuatkho.imp.ErpSoxuathangNppList;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpSoxuathangNppUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
    public ErpSoxuathangNppUpdateSvl() 
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
		
			 String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
			    String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
			    
			Utility util = new Utility();
			
	    	String querystring = request.getQueryString();
		    userId = util.getUserId(querystring);
		    
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId")); 
		    
		    String id = util.getId(querystring);  	
		    IErpSoxuathangNpp lsxBean = new ErpSoxuathangNpp(id);
		    lsxBean.setUserId(userId); 
		    lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
		    lsxBean.setNpp_duocchon_id(npp_duocchon_id);
		    
		    /*String phanloai = request.getParameter("phanloai");
			if(phanloai == null)
				phanloai = "";
			lsxBean.setXuatcho(phanloai);*/
		    
		    String nextJSP = "";
		    
		    lsxBean.setLoainhanvien(session.getAttribute("loainhanvien"));
		    lsxBean.setDoituongId(session.getAttribute("doituongId"));

    		if(!querystring.contains("display"))
    		{
    			lsxBean.init();
    			nextJSP = "/TraphacoHYERP/pages/Distributor/ErpSoXuatHangNppUpdate.jsp";	
    		}
    		else
    		{
    			lsxBean.initDisplay();
    			nextJSP = "/TraphacoHYERP/pages/Distributor/ErpSoXuatHangNppDisplay.jsp";
    		}
    		
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
			 String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
			    String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
			    
			this.out = response.getWriter();
			IErpSoxuathangNpp lsxBean;
			
			Utility util = new Utility();	
			String id = util.antiSQLInspection(request.getParameter("id"));
		    if(id == null)
		    {  	
		    	lsxBean = new ErpSoxuathangNpp("");
		    }
		    else
		    {
		    	lsxBean = new ErpSoxuathangNpp(id);
		    }
	
		    lsxBean.setUserId(userId);
		    
		    lsxBean.setLoainhanvien(session.getAttribute("loainhanvien"));
		    lsxBean.setDoituongId(session.getAttribute("doituongId"));
		    lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
		    lsxBean.setNpp_duocchon_id(npp_duocchon_id);
		    
		    String ngayyeucau = util.antiSQLInspection(request.getParameter("ngaychuyen"));
		    if(ngayyeucau == null || ngayyeucau.trim().length() <= 0)
		    	ngayyeucau = getDateTime();
		    lsxBean.setNgayyeucau(ngayyeucau);
		    
		    String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
		    if(ghichu == null)
		    	ghichu = "";
		    lsxBean.setGhichu(ghichu);
		    
		    String xuatchoId = util.antiSQLInspection(request.getParameter("xuatchoId"));
		    if(xuatchoId == null)
		    	xuatchoId = "";
		    lsxBean.setXuatcho(xuatchoId);
		    
			/*String khonhapId = util.antiSQLInspection(request.getParameter("khonhapId"));
			if (khonhapId == null)
				khonhapId = "";				
			lsxBean.setKhoNhapId(khonhapId);*/
			
			String[] khonhapId = request.getParameterValues("khonhapId");
			if (khonhapId != null)
			{
				String _scheme = "";
				for(int i = 0; i < khonhapId.length; i++)
				{
					_scheme += khonhapId[i] + ",";
				}
				
				if(_scheme.trim().length() > 0)
				{
					_scheme = _scheme.substring(0, _scheme.length() - 1);
					lsxBean.setKhoNhapId(_scheme);
				}
			}
			
			String nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if (nppId == null)
				nppId = "";				
			lsxBean.setNppId(nppId);
			
			String khId = util.antiSQLInspection(request.getParameter("khId"));
			if (khId == null)
				khId = "";				
			lsxBean.setKhId(khId);
			
			String tinhthanhId = util.antiSQLInspection(request.getParameter("tinhthanhId"));
			if (tinhthanhId == null)
				tinhthanhId = "";				
			lsxBean.setTinhthanhId(tinhthanhId);
			
			/*String quanhuyenId = util.antiSQLInspection(request.getParameter("quanhuyenId"));
			if (quanhuyenId == null)
				quanhuyenId = "";				
			lsxBean.setQuanhuyenId(quanhuyenId);*/
			
			String[] quanhuyenId = request.getParameterValues("quanhuyenId");
			if (quanhuyenId != null)
			{
				String _scheme = "";
				for(int i = 0; i < quanhuyenId.length; i++)
				{
					_scheme += quanhuyenId[i] + ",";
				}
				
				if(_scheme.trim().length() > 0)
				{
					_scheme = _scheme.substring(0, _scheme.length() - 1);
					lsxBean.setQuanhuyenId(_scheme);
				}
			}
			
			String nvbhId = util.antiSQLInspection(request.getParameter("nvbhId"));
			if (nvbhId == null)
				nvbhId = "";				
			lsxBean.setNVBHId(nvbhId);
			
			String nvgnId = util.antiSQLInspection(request.getParameter("nvgnId"));
			if (nvgnId == null)
				nvgnId = "";				
			lsxBean.setNVGNId(nvgnId);
			
			String tungay = util.antiSQLInspection(request.getParameter("tungay"));
			if (tungay == null)
				tungay = "";				
			lsxBean.setTungay(tungay);
			
			String denngay = util.antiSQLInspection(request.getParameter("denngay"));
			if (denngay == null)
				denngay = "";				
			lsxBean.setDenngay(denngay);
			
			String chkALL = util.antiSQLInspection(request.getParameter("chkALL"));
			if (chkALL == null)
				chkALL = "";				
			lsxBean.setCheckAll(chkALL);
			
			String[] ddhIds = request.getParameterValues("ddhIds");
			if (ddhIds != null)
			{
				String _scheme = "";
				for(int i = 0; i < ddhIds.length; i++)
				{
					_scheme += ddhIds[i] + ",";
				}
				
				if(_scheme.trim().length() > 0)
				{
					_scheme = _scheme.substring(0, _scheme.length() - 1);
					lsxBean.setDondathangId(_scheme);
				}
			}
			
			String[] spId = request.getParameterValues("spId");
			lsxBean.setSpId(spId);
			
			String[] spMa = request.getParameterValues("spMa");
			lsxBean.setSpMa(spMa);
			
			String[] spTen = request.getParameterValues("spTen");
			lsxBean.setSpTen(spTen);
			
			String[] dvt = request.getParameterValues("donvi");
			lsxBean.setSpDonvi(dvt);
			
			String[] soluongDAT = request.getParameterValues("soluongDAT");
			lsxBean.setSpSoluongDat(soluongDAT);
			
			String[] tonkho = request.getParameterValues("tonkho");
			lsxBean.setSpTonKho(tonkho);
			
			String[] daxuat = request.getParameterValues("daxuat");
			lsxBean.setSpDaXuat(daxuat);
			
			String[] soluong = request.getParameterValues("soluong");
			lsxBean.setSpSoluong(soluong);
			
			String[] spLoai = request.getParameterValues("spLoai");
			lsxBean.setSpLoai(spLoai);
			
			String[] spScheme = request.getParameterValues("scheme");
			lsxBean.setSpScheme(spScheme);
			
			if(spId != null)
			{
				Hashtable<String, String> sanpham_soluong = new Hashtable<String, String>();
				for(int i = 0; i < spId.length; i++ )
				{
					//System.out.println("---SP MA LA: " + spMa[i]);
					String temID = spId[i] + "__" + spLoai[i] + "__" + spScheme[i].trim();
					
					String[] spSOLO = request.getParameterValues(temID + "_spSOLO");
					String[] soLUONGXUAT = request.getParameterValues(temID + "_spSOLUONG");
					
					if(soLUONGXUAT != null)
					{
						for(int j = 0; j < soLUONGXUAT.length; j++ )
						{
							if(soLUONGXUAT[j] != null && soLUONGXUAT[j].trim().length() > 0)
							{
								System.out.println("---KEY SVL: " + ( spId[i] + "__" + spLoai[i] + "__" + spScheme[i].trim() + "__" + spSOLO[j] )  + "   --- GIA TRI: " + soLUONGXUAT[j].replaceAll(",", "") );
								sanpham_soluong.put(spId[i] + "__" + spLoai[i] + "__" + spScheme[i].trim() + "__" + spSOLO[j], soLUONGXUAT[j].replaceAll(",", "") );
							}
						}
					}
				}
				
				lsxBean.setSanpham_Soluong(sanpham_soluong);
			}
			
			
		    String action = request.getParameter("action");
		    
			if(action.equals("save"))
			{	
				if(id == null)
				{
					if(!lsxBean.create())
					{
						lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
					    lsxBean.setNpp_duocchon_id(npp_duocchon_id);
						lsxBean.setUserId(userId);
						lsxBean.createRs();
	    		    	session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpSoXuatHangNppNew.jsp";
	    				
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpSoxuathangNppList obj = new ErpSoxuathangNppList();
						
						obj.setUserId(userId);
						obj.setTdv_dangnhap_id(tdv_dangnhap_id);
						obj.setNpp_duocchon_id(npp_duocchon_id);
						//obj.setPhanloai(xuatchoId);
						obj.init("");  
				    	session.setAttribute("obj", obj);  	
			    		session.setAttribute("userId", userId);
			    		
			    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpSoXuatHangNpp.jsp";
			    		response.sendRedirect(nextJSP);
					}
				}
				else
				{
					if(!lsxBean.update())
					{
						lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
						lsxBean.setNpp_duocchon_id(npp_duocchon_id);
						lsxBean.setUserId(userId);
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpSoXuatHangNppUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpSoxuathangNppList obj = new ErpSoxuathangNppList();
						
						obj.setTdv_dangnhap_id(tdv_dangnhap_id);
						obj.setNpp_duocchon_id(npp_duocchon_id);
					    obj.setUserId(userId);
					    //obj.setPhanloai(xuatchoId);
					    obj.init("");
						session.setAttribute("obj", obj);							
						String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpSoXuatHangNpp.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
				if( action.equals("loclaiPO") )
				{
					lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
					lsxBean.setNpp_duocchon_id(npp_duocchon_id);
					lsxBean.setDondathangId("");
					lsxBean.setSanpham_Soluong(new Hashtable<String, String>());
					lsxBean.setSpId(null);
					lsxBean.setSpMa(null);
					lsxBean.setSpSoluong(null);
					lsxBean.setSpSoluongDat(null);
				}
				
				if( action.equals("changePO"))
				{
					lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
					lsxBean.setNpp_duocchon_id(npp_duocchon_id);
					lsxBean.setSanpham_Soluong(new Hashtable<String, String>());
					lsxBean.setSpId(null);
					lsxBean.setSpMa(null);
					lsxBean.setSpSoluong(null);
					lsxBean.setSpSoluongDat(null);
				}
				
				lsxBean.createRs();
				
				session.setAttribute("lsxBean", lsxBean);
				
				String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpSoXuatHangNppNew.jsp";
				if(id != null)
					nextJSP = "/TraphacoHYERP/pages/Distributor/ErpSoXuatHangNppUpdate.jsp";
				
				response.sendRedirect(nextJSP);
			}
		}
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
}
