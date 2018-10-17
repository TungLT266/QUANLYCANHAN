package geso.traphaco.distributor.servlets.hoadontaichinhKM;

import geso.traphaco.distributor.beans.donhang.ISanpham;
import geso.traphaco.distributor.beans.donhang.imp.Sanpham;
import geso.traphaco.distributor.beans.hoadontaichinhKM.IHoadontaichinhKM;
import geso.traphaco.distributor.beans.hoadontaichinhKM.IHoadontaichinhKMList;
import geso.traphaco.distributor.beans.hoadontaichinhKM.imp.HoadontaichinhKM;
import geso.traphaco.distributor.beans.hoadontaichinhKM.imp.HoadontaichinhKMList;
import geso.traphaco.distributor.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HoadontaichinhKMUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
    public HoadontaichinhKMUpdateSvl() 
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
		    IHoadontaichinhKM lsxBean = new HoadontaichinhKM(id);
		    lsxBean.setUserId(userId); 
		    
		    String nextJSP = "";
		    
    		lsxBean.init();
    		
    		if(!querystring.contains("display"))
    		{
    			nextJSP = "/TraphacoHYERP/pages/Distributor/HoaDonTaiChinhKMUpdate.jsp";	
    		}
    		else
    		{
    			nextJSP = "/TraphacoHYERP/pages/Distributor/HoaDonTaiChinhKMDisplay.jsp";
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
			
			this.out = response.getWriter();
			IHoadontaichinhKM lsxBean;
			
			Utility util = new Utility();	
			String id = util.antiSQLInspection(request.getParameter("id"));
		    if(id == null)
		    {  	
		    	lsxBean = new HoadontaichinhKM("");
		    }
		    else
		    {
		    	lsxBean = new HoadontaichinhKM(id);
		    }
	
		    lsxBean.setUserId(userId);
		    
		    String ngayxuatHD = util.antiSQLInspection(request.getParameter("ngayxuat"));
		    if(ngayxuatHD == null || ngayxuatHD.trim().length() <= 0)
		    	ngayxuatHD = getDateTime();
		    lsxBean.setNgayxuatHD(ngayxuatHD);
		    
		    
		    String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
		    if(ghichu == null)
		    	ghichu = "";
		    lsxBean.setGhichu(ghichu);
		    
		    String khId = util.antiSQLInspection(request.getParameter("khId"));
		    if(khId == null)
		    	khId = "";
		    lsxBean.setKhId(khId);
		    
		    String kyhieuhoadon = util.antiSQLInspection(request.getParameter("kyhieuhoadon"));
		    if(kyhieuhoadon == null)
		    	kyhieuhoadon = "";
		    lsxBean.setKyhieuhoadon(kyhieuhoadon);
		    
		    String sohoadon = util.antiSQLInspection(request.getParameter("sohoadon"));
		    if(sohoadon == null)
		    	sohoadon = "";
		    lsxBean.setSohoadon(sohoadon);
		    
		    String hinhthuctt = util.antiSQLInspection(request.getParameter("hinhthuctt"));
		    if(hinhthuctt == null)
		    	hinhthuctt = "";
		    lsxBean.setHinhthucTT(hinhthuctt);
		    
		    String nguoimua = util.antiSQLInspection(request.getParameter("nguoimuahang"));
		    if(nguoimua == null)
		    	nguoimua = "";
		    lsxBean.setNguoimua(nguoimua);
		    
		    String innguoimua = util.antiSQLInspection(request.getParameter("innguoimuahang"));
		    if(innguoimua == null || innguoimua.trim().length() <= 0)
		    {
		    	innguoimua = "0";
		    }else
		    {
		    	innguoimua = "1";
		    }
		    lsxBean.setInNguoimua(innguoimua);
		    
		    String tienck = util.antiSQLInspection(request.getParameter("tienck"));
		    if(tienck == null)
		    	tienck = "0";
		    lsxBean.setTienCK(Double.parseDouble(tienck.replace(",", "")));
		    
		    String tiensauCK = util.antiSQLInspection(request.getParameter("tiensauCK"));
		    if(tiensauCK == null)
		    	tiensauCK = "0";
		    lsxBean.setTienSauCK(Double.parseDouble(tiensauCK.replace(",", "")));
		    
		    String tienvat = util.antiSQLInspection(request.getParameter("tienvat"));
		    if(tienvat == null)
		    	tienvat = "0";
		    lsxBean.setTienVAT(Double.parseDouble(tienvat.replace(",", "")));
		    
		    
				String[] ddhid = request.getParameterValues("ddhid");
				
				String ddh = "";
				if (ddhid != null) 
				{
					for(int i = 0; i < ddhid.length; i++)
					{
						ddh += ddhid[i] + ",";
					}
					
					if(ddh.trim().length() > 0)
					{
						ddh = ddh.substring(0, ddh.length() - 1);
						lsxBean.setDondathangId(ddh);
					}
				}
			
			String[] idspTrakm = request.getParameterValues("idspTrakm");
			String[] maspTrakm = request.getParameterValues("maspTrakm");
			String[] tenspTraKm = request.getParameterValues("tenspTraKm");
			String[] dvtTrakm = request.getParameterValues("dvtTrakm");
			String[] slgTraKm = request.getParameterValues("slgTraKm");
			String[] dgTrakm = request.getParameterValues("dgTrakm");
			String[] trakmSpScheme = request.getParameterValues("trakmSpScheme");
			String[] vatTrakm = request.getParameterValues("vatTrakm");

			List<ISanpham>	spList = new ArrayList<ISanpham>();
			
			if(maspTrakm != null)
			{
				ISanpham sanpham = null;
				String[] param = new String[8];
				int m = 0;
				while(m < maspTrakm.length)
				{
				  if(maspTrakm[m] != "" & slgTraKm[m].length() > 0)
				  {
					  param[0]= idspTrakm[m];
					  param[1]= maspTrakm[m];
					  param[2]= tenspTraKm [m];
					  param[3]= slgTraKm[m];
					  param[4]= dvtTrakm[m];
					  param[5]= dgTrakm [m];
					  param[6]= trakmSpScheme[m];
					  param[7]= "0";
					  
					  sanpham = new Sanpham(param);
					  sanpham.setVat(vatTrakm[m]);
					  spList.add(sanpham);	
					  
					  
				  }
					
					m++ ;
				}
			}
			lsxBean.setScheme_SpList(spList);
			
			String[] spId = request.getParameterValues("idspTrakm");
			lsxBean.setSpId(spId);
			
			String[] spMa = request.getParameterValues("maspTrakm");
			lsxBean.setSpMa(spMa);
			
			String[] spTen = request.getParameterValues("tenspTraKm");
			lsxBean.setSpTen(spTen);
			
			String[] dvt = request.getParameterValues("dvtTrakm");
			lsxBean.setSpDonvi(dvt);
			
			String[] spDongia = request.getParameterValues("dgTrakm");
			lsxBean.setSpDongia(spDongia);
						
			String[] spChietkhau = request.getParameterValues("spChietkhau");
			lsxBean.setSpChietkhau(spChietkhau);
			
			String[] soluong = request.getParameterValues("slgTraKm");
			lsxBean.setSpSoluong(soluong);
			
			String[] spLoai = request.getParameterValues("spLoai");
			lsxBean.setSpLoai(spLoai);
			
			String[] spScheme = request.getParameterValues("trakmSpScheme");
			lsxBean.setSpScheme(spScheme);	
			
			String[] spVat = request.getParameterValues("vatTrakm");
			lsxBean.setSpVat(spVat);	
			
			
			//Lưu lại trường hợp đổi số lô
			if(spMa != null)
			{
				Hashtable<String, String> sanpham_soluong = new Hashtable<String, String>();
				for(int i = 0; i < spMa.length; i++ )
				{
					//System.out.println("---SP MA LA: " + spMa[i]);
					String temID = spId[i];
					
					String[] spSOLO = request.getParameterValues(temID + "_spSOLO");
					String[] spNGAYHETHAN = request.getParameterValues(temID + "_spNGAYHETHAN");
					String[] soLUONGXUAT = request.getParameterValues(temID + "_spSOLUONG");
					
					if(soLUONGXUAT != null)
					{
						for(int j = 0; j < soLUONGXUAT.length; j++ )
						{
							if(soLUONGXUAT[j] != null && soLUONGXUAT[j].trim().length() > 0)
							{
								//System.out.println("---KEY SVL: " + ( spId[i] + "__" + spLoai[i] + "__" + spScheme[i].trim() + "__" + spSOLO[j] )  + "   --- GIA TRI: " + soLUONGXUAT[j].replaceAll(",", "") );
								sanpham_soluong.put(spId[i] + "__" + spSOLO[j] + "__" + spNGAYHETHAN[j], soLUONGXUAT[j].replaceAll(",", "") );
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
						lsxBean.createRs();
						lsxBean.getMsg();
	    		    	session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Distributor/HoaDonTaiChinhKMNew.jsp";
	    				
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IHoadontaichinhKMList obj = new HoadontaichinhKMList();
						
						obj.setUserId(userId);
						obj.init("");  
				    	session.setAttribute("obj", obj);  	
			    		session.setAttribute("userId", userId);
			    		
			    		String nextJSP = "/TraphacoHYERP/pages/Distributor/HoaDonTaiChinhKM.jsp";
			    		response.sendRedirect(nextJSP);
					}
				}
				else
				{
					if(!lsxBean.update())
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Distributor/HoaDonTaiChinhKMUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IHoadontaichinhKMList obj = new HoadontaichinhKMList();
						
					    obj.setUserId(userId);
					    obj.init("");
						session.setAttribute("obj", obj);							
						String nextJSP = "/TraphacoHYERP/pages/Distributor/HoaDonTaiChinhKM.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
				if(action.equals("chot"))
				{
					if(!lsxBean.chot())
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Distributor/HoaDonTaiChinhKMUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IHoadontaichinhKMList obj = new HoadontaichinhKMList();
						
					    obj.setUserId(userId);
					    obj.init("");
						session.setAttribute("obj", obj);							
						String nextJSP = "/TraphacoHYERP/pages/Distributor/HoaDonTaiChinhKM.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				else
				{
					String nextJSP ="";
					if(id == null )
					{
						lsxBean.createRs();	
						session.setAttribute("lsxBean", lsxBean);					
					    nextJSP = "/TraphacoHYERP/pages/Distributor/HoaDonTaiChinhKMNew.jsp";
					}
					if(id != null)
					{
						lsxBean.loadContents();	
						session.setAttribute("lsxBean", lsxBean);
						nextJSP = "/TraphacoHYERP/pages/Distributor/HoaDonTaiChinhKMUpdate.jsp";
					}
					response.sendRedirect(nextJSP);
				}
				
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
