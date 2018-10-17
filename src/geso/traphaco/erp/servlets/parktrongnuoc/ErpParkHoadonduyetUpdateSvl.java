package geso.traphaco.erp.servlets.parktrongnuoc;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.parktrongnuoc.IErpHoadon;
import geso.traphaco.erp.beans.parktrongnuoc.IErpPark;
import geso.traphaco.erp.beans.parktrongnuoc.IErpParkList;
import geso.traphaco.erp.beans.parktrongnuoc.IErpHoadonSp;
import geso.traphaco.erp.beans.parktrongnuoc.imp.ErpHoadon;
import geso.traphaco.erp.beans.parktrongnuoc.imp.ErpPark;
import geso.traphaco.erp.beans.parktrongnuoc.imp.ErpParkList;
import geso.traphaco.erp.beans.parktrongnuoc.imp.ErpHoadonSp;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpParkHoadonduyetUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PrintWriter out;
    public ErpParkHoadonduyetUpdateSvl() {
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
		
			this.out = response.getWriter();
			Utility util = new Utility();
			
	    	String querystring = request.getQueryString();
		    userId = util.getUserId(querystring);
		    
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId")); 	
		    String id = util.getId(querystring);  	
			IErpPark pBean = new ErpPark(id);
			
	        pBean.setUserId(userId); //phai co UserId truoc khi Init
	        pBean.setCtyId((String)session.getAttribute("congtyId"));
	        pBean.setnppdangnhap(util.getIdNhapp(userId));
        	pBean.init();
	        String nextJSP;
	        
	        if(request.getQueryString().indexOf("display") >= 0 ) 
	        {
	        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDonDuyetDisplay.jsp";
	        }
	        else
	        {
	        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDonDuyetUpdate.jsp";
	        }
	        
	        session.setAttribute("pBean", pBean);
	        session.setAttribute("maduyet", id);
	        session.setAttribute("nccduyet", pBean.getNccId());
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
			IErpPark pBean;
			
			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("id"));
			
			System.out.println("so Id " + id);
			
		    if(id == null)
		    {  	
		    	pBean = new ErpPark("");
		    }
		    else
		    {
		    	pBean = new ErpPark(id);
		    }
	
		    pBean.setUserId(userId);
		    pBean.setCtyId((String)session.getAttribute("congtyId"));
	        pBean.setnppdangnhap(util.getIdNhapp(userId));
		    
	        String nccId = util.antiSQLInspection(request.getParameter("nccId"));
		    if(nccId == null)
		    	nccId = "";
		    pBean.setNccId(nccId);

		    String ngayghinhan = util.antiSQLInspection(request.getParameter("ngayghinhan"));
			if (ngayghinhan == null || ngayghinhan == "")
				ngayghinhan = getDateTime();		
	    	pBean.setNgayghinhan(ngayghinhan);
	    		    	
	    	String ttId = util.antiSQLInspection(request.getParameter("ttId"));
			if (ttId == null ){
				ttId = "";		
			}
	    	pBean.setTtId(ttId);
	    	
	    	String tongsoluong = util.antiSQLInspection(request.getParameter("tongsoluong"));
			if (tongsoluong == null || tongsoluong == "")
				tongsoluong = "0";		
	    	pBean.setTongsoluong(tongsoluong.replaceAll(",", ""));
	    	
	    	String tigia = util.antiSQLInspection(request.getParameter("tigia"));
			if (tigia == null)
				tigia = "";		
	    	pBean.setTigia(tigia.replaceAll(",", ""));
	    	
	    	// NCC THAY THÊ
	    	 String nccTTId = util.antiSQLInspection(request.getParameter("nccTTId"));
			    pBean.setNCCThayTheId(nccTTId);
			    
		    String nccTTTen = util.antiSQLInspection(request.getParameter("nccTTTen"));
		    if(nccTTTen == null)
		    	nccTTTen = "";
		    pBean.setNCCThayTheTen(nccTTTen);
		    
		    String nccTTMST = util.antiSQLInspection(request.getParameter("nccTTMST"));
		    if(nccTTMST == null)
		    	nccTTMST = "";
		    pBean.setNCCThayTheMST(nccTTMST);
		    
		    String nccTTDiachi = util.antiSQLInspection(request.getParameter("nccTTDiachi"));
		    if(nccTTDiachi == null)
		    	nccTTDiachi = "";
		    pBean.setNCCThayTheDiaChi(nccTTDiachi);
		    
		    String loaidonmuahangId = util.antiSQLInspection(request.getParameter("loaidonmuahangId"));
		    if(loaidonmuahangId == null)
		    	loaidonmuahangId = "";
		    pBean.setLoaidonmh(loaidonmuahangId);
	    	
	    	String[] poNKIDs = request.getParameterValues("poNKID");
	    	String poNKID = "";
			if (poNKIDs != null )
			{
				for(int i = 0; i < poNKIDs.length; i++)
					poNKID += poNKIDs[i] + ",";
				
				if(poNKID.trim().length() > 0)
				{
					poNKID = poNKID.substring(0, poNKID.length() - 1);
				}
			}	
			
			System.out.println("poNKID:"+poNKID);
			pBean.setPoNkIds(poNKID);
			
			String action = request.getParameter("action");
			
		    List<IErpHoadon> hdList = new ArrayList<IErpHoadon>();
		    IErpHoadon hoadon = null;	
		    		  
		    	for(int i = 0; i < 10; i++)
		    	{
		    		String mauhoadon = util.antiSQLInspection(request.getParameter("mauhoadon" + i));
		    		String mausohoadon = util.antiSQLInspection(request.getParameter("mausohoadon" + i));
		    		String sohoadon = util.antiSQLInspection(request.getParameter("sohoadon" + i));
		    		String hoadonpk_seq = util.antiSQLInspection(request.getParameter("pk_seq" + i));
		    		String kyhieuhoadon = util.antiSQLInspection(request.getParameter("kyhieuhoadon" + i));
		    		String ngayhoadon = util.antiSQLInspection(request.getParameter("ngayhoadon" + i));
		    		String sotienchuathue = util.antiSQLInspection(request.getParameter("sotienchuathue" + i));
		    		String chietkhau = util.antiSQLInspection(request.getParameter("chietkhau" + i));
		    		String thuesuat = util.antiSQLInspection(request.getParameter("thuesuat" + i));
		    		String vat = util.antiSQLInspection(request.getParameter("vat" + i));
		    		String tongcong = util.antiSQLInspection(request.getParameter("tongcong" + i));
		    		
		    		List<IErpHoadonSp> spList = new ArrayList<IErpHoadonSp>();
		    		
		    		if(sohoadon != null)
		    		{				    
		    			if(thuesuat == null)
		    				thuesuat = "";
			    	
		    			if(chietkhau == null)
		    				chietkhau = "";
			    	
		    			if(vat == null)
		    				vat="";
		    			vat = vat.replaceAll(",","");
		    			

	    				System.out.println("sohoadon:"+	sohoadon+"kyhieuhoadon: "+kyhieuhoadon);
			    	
		    			if(sohoadon != "" && kyhieuhoadon != "" && ngayhoadon != ""  && ngayghinhan != "")
		    			{
		    				hoadon = new ErpHoadon();
		    				System.out.println("vaoday");
		    				hoadon.setId(hoadonpk_seq);
		    				hoadon.setChieckhau(chietkhau.replaceAll(",", ""));
		    				hoadon.setMauhoadon(mauhoadon);
		    				hoadon.setMausohoadon(mausohoadon);
		    				hoadon.setKyhieu(kyhieuhoadon);
		    				hoadon.setNgayhoadon(ngayhoadon);
		    				hoadon.setSohoadon(sohoadon);
		    				hoadon.setVAT(vat);
		    				hoadon.setThuesuat(thuesuat);
		    				hoadon.setSotienbvat(sotienchuathue.replaceAll(",", ""));
		    				hoadon.setSotienavat(tongcong.replaceAll(",", ""));	
		    				hoadon.setChieckhau(chietkhau.replaceAll(",", ""));
		    				
			    		
		    				String[] poId = request.getParameterValues("sohoadon"+i+".poId");
		    				String[] poTen = request.getParameterValues("sohoadon"+i+".soPO");
		    				String[] loai = request.getParameterValues("sohoadon"+i+".loai");
		    				String[] spId = request.getParameterValues("sohoadon"+i+".mahang");
		    				String[] spma = request.getParameterValues("sohoadon"+i+".ma");
		    				String[] tenhang = request.getParameterValues("sohoadon"+i+".tenhang");
		    				String[] dungsai = request.getParameterValues("sohoadon"+i+".dungsai");
		    				String[] donvi = request.getParameterValues("sohoadon"+i+".donvi");
		    				String[] soluong = request.getParameterValues("sohoadon"+i+".soluongdat");
		    				String[] soluonghd = request.getParameterValues("sohoadon"+i+".soluong"); // SỐ LƯỢNG HÓA ĐƠN ĐẶT
		    				String[] soluongRaHD = request.getParameterValues("sohoadon"+i+".soluongRaHD");
		    				String[] dongia = request.getParameterValues("sohoadon"+i+".dongia");
		    				String[] thanhtien = request.getParameterValues("sohoadon"+i+".thanhtien");
		    				String[] ngaynhandk = request.getParameterValues("sohoadon"+i+".ngaynhandk");
		    				//String[] chon = request.getParameterValues("sohoadon"+i+".thanhtien");
		    				
		    				//String[] hoantat = request.getParameterValues("sohoadon"+i+".checkHT");
		    				
		    				
		        				for(int m= 0; m < poId.length; m++)
		        				{
		        					if(poId[m].trim().length() > 0)
		        					{		
		        						System.out.println("Size "+poId[m]);
		        						if(soluonghd[m]== null)
		        							soluonghd[m] = "0";		        						
		        							        						
		        						if(Double.parseDouble(soluonghd[m].replaceAll(",", ""))>0)
		        						{
				        					IErpHoadonSp sp = new ErpHoadonSp(poId[m], poTen[m], spId[m], tenhang[m], soluong[m].replaceAll(",", ""), dongia[m].replaceAll(",", ""),
			        						           thanhtien[m].replaceAll(",", ""), donvi[m], loai[m], "", "");
				        								        				
				        					sp.setSanphamMa(spma[m]);
				        					sp.setDungsai(dungsai[m]);
				        					sp.setSoluonghd(soluonghd[m].replaceAll(",", ""));
				        					sp.setSoluongRaHD(soluongRaHD[m].replaceAll(",", ""));
				        					sp.setNgaynhan(ngaynhandk[m]);
				        					spList.add(sp);
		        						}
		        					}
		        					
		        				}
		        				
		        				hoadon.setPnkList(spList);	
		        			hdList.add(hoadon);
		    			}
		    			
		    		}
		    	}
		    	pBean.setHdList(hdList);
		    
		    
			if(action.equals("save"))
			{	
				if(!pBean.updatePark())
				{
					//pBean.createRs();
					pBean.init();
					session.setAttribute("pBean", pBean);
    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDonDuyetUpdate.jsp";
    				response.sendRedirect(nextJSP);
				}
				else
				{
					IErpParkList obj = new ErpParkList();
				    obj.setUserId(userId);
				    obj.setDuyet(1);
				    obj.setCongtyId((String)session.getAttribute("congtyId"));
				    obj.setnppdangnhap(util.getIdNhapp(userId));
				    obj.init("");
					session.setAttribute("obj", obj);							
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpParkHoaDonDuyet.jsp";
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
