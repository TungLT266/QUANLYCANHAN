package geso.traphaco.erp.servlets.yeucauchuyenkho;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpDoisolo;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpDoisoloList;
import geso.traphaco.erp.beans.yeucauchuyenkho.IYeucau;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpDoisolo;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpDoisoloList;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.Yeucau;

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

public class ErpDoisoloUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public ErpDoisoloUpdateSvl() 
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
		    IErpDoisolo lsxBean = new ErpDoisolo(id);
		    lsxBean.setUserId(userId); 
		    
	        String nextJSP = "";
			if(request.getQueryString().indexOf("display") >= 0 ) 
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiSoLoDisplay.jsp";
			else
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiSoLoNew.jsp";
	       
			lsxBean.init(); 
			session.setAttribute("khochuyenIds", lsxBean.getKhoXuatId());
			session.setAttribute("isDctk", "1");
	        session.setAttribute("ckBean", lsxBean);
	        response.sendRedirect(nextJSP);
		}		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
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
			IErpDoisolo lsxBean;
			
			Utility util = new Utility();	
			String id = util.antiSQLInspection(request.getParameter("id"));
		    if(id == null)
		    {  	
		    	lsxBean = new ErpDoisolo("");
		    }
		    else
		    {
		    	lsxBean = new ErpDoisolo(id);
		    }
	
		    lsxBean.setUserId(userId);
		    
		    String ngayyeucau = util.antiSQLInspection(request.getParameter("ngaychuyen"));
		    if(ngayyeucau == null || ngayyeucau.trim().length() <= 0)
		    	ngayyeucau = getDateTime();
		    lsxBean.setNgayyeucau(ngayyeucau);
		    
		    String lydo = util.antiSQLInspection(request.getParameter("lydo"));
		    if(lydo == null)
		    	lydo = "";
		    lsxBean.setLydoyeucau(lydo);
		    
		    String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
		    if(ghichu == null)
		    	ghichu = "";
		    lsxBean.setGhichu(ghichu);
		    
		    String khoxuatId = util.antiSQLInspection(request.getParameter("khoxuatId"));
			if (khoxuatId == null)
				khoxuatId = "";				
			lsxBean.setKhoXuatId(khoxuatId);
			session.setAttribute("isDctk", "1");
 
			String vitriId = util.antiSQLInspection(request.getParameter("vitriId"));
			if (vitriId == null)
				vitriId = "";				
			lsxBean.setVitrichuyenId(vitriId);
			
			String kyhieu = util.antiSQLInspection(request.getParameter("kyhieu"));
			if (kyhieu == null)
				kyhieu = "";				
			lsxBean.setKyHieu(kyhieu);
			
			String sochungtu = util.antiSQLInspection(request.getParameter("sochungtu"));
			if (sochungtu == null)
				sochungtu = "";				
			lsxBean.setSochungtu(sochungtu);
			
			String loaidieuchinh = util.antiSQLInspection(request.getParameter("loaidieuchinh"));
			if (loaidieuchinh == null)
				loaidieuchinh = "";				
			lsxBean.setLoaidieuchinh(loaidieuchinh);
			
			//BÊN CHUYỂN
			String codoituong = util.antiSQLInspection(request.getParameter("codoituong"));
			if (codoituong == null)
				codoituong = "";				
			lsxBean.setCoDoituong(codoituong);
			
			String loaidoituongId = util.antiSQLInspection(request.getParameter("loaidoituongId"));
			if (loaidoituongId == null)
				loaidoituongId = "";				
			lsxBean.setLoaidoituongId(loaidoituongId);
			
			String doituongId = util.antiSQLInspection(request.getParameter("doituongId"));
			if (doituongId == null)
				doituongId = "";				
			lsxBean.setDoituongId(doituongId);
			
			//CHI PHI
			String coChiphi = util.antiSQLInspection(request.getParameter("coChiphi"));
			if (coChiphi == null)
				coChiphi = "";				
			lsxBean.setCochiphi(coChiphi);
			
			String chiphiId = util.antiSQLInspection(request.getParameter("chiphiId"));
			if (chiphiId == null)
				chiphiId = "";				
			lsxBean.setChiphiId(chiphiId);
			
			String action = request.getParameter("action");
			System.out.println(":: ACTION: " + action);
			
			String[] idsp = request.getParameterValues("idsp");
			String[] masp = request.getParameterValues("masp");
			String[] tensp = request.getParameterValues("tensp");
			String[] dvt = request.getParameterValues("donvi");
		 
			String[] soluong = request.getParameterValues("soluongyeucau"); 
			String[] soluongton = request.getParameterValues("soluongtonkho"); 
			String[] ghichu_ck = request.getParameterValues("ghichu_ck"); 
			
			//String[] lsxId = request.getParameterValues("lsxId"); 
			
			List<IYeucau> spList = new ArrayList<IYeucau>();
			if(masp != null)
			{	
				IYeucau yeucau = null;
				for(int m = 0; m < masp.length; m++)
				{	
					if(masp[m] != "")
					{	
						yeucau = new Yeucau();
						yeucau.setId(idsp[m]);
						yeucau.setMa(masp[m]);
						yeucau.setTen(tensp[m]);
						yeucau.setTonhientai(soluongton[m].replaceAll(",",""));
						yeucau.setSoluongYC(soluong[m].replaceAll(",",""));
						yeucau.setghichu_CK(ghichu_ck[m]);

						if(dvt != null)
							yeucau.setDonViTinh(dvt[m]);
						
						spList.add(yeucau);
					}				
				}
				
				lsxBean.setSpList(spList);
			}	
			
			if(masp != null  )  //LUU LAI THONG TIN NGUOI DUNG NHAP
			{
				Hashtable<String, String> sanpham_soluong = new Hashtable<String, String>();
				Hashtable<String, String> sanpham_thongtindoi = new Hashtable<String, String>();
				Hashtable<String, String> sanpham_ngayhethandoi = new Hashtable<String, String>();
				for(int i = 0; i < masp.length; i++ )
				{
					String temID = masp[i] + "__ ";
					//System.out.println("---TEMP ID: " + temID);
					
					String[] spSOLO = request.getParameterValues(temID + "_spSOLO");
					String[] spNGAYHETHAN = request.getParameterValues(temID + "_spNGAYHETHAN");
					String[] spNGAYNHAPKHO = request.getParameterValues(temID + "_spNGAYNHAPKHO");
					
					String[] spMAME = request.getParameterValues(temID + "_spMAME");
					String[] spMATHUNG = request.getParameterValues(temID + "_spMATHUNG");
					String[] spMAPHIEU = request.getParameterValues(temID + "_spMAPHIEU");
					
					String[] spMERQ = request.getParameterValues(temID + "_spMarq");
					String[] spHAMLUONG = request.getParameterValues(temID + "_spHamluong");
					String[] spHAMAM = request.getParameterValues(temID + "_spHamam");
					
					String[] spVITRI = request.getParameterValues(temID + "_spVitri");
					String[] spPHIEUDT = request.getParameterValues(temID + "_spPhieudt");
					String[] spPHIEUEO = request.getParameterValues(temID + "_spPhieueo");
					
					String[] soLUONGXUAT = request.getParameterValues(temID + "_spSOLUONG");
					String[] spTHONGTINDOI = request.getParameterValues(temID + "_spTHONGTINDOI");
					String[] spNGAYHHDOI = request.getParameterValues(temID + "_spNGAYHHDOI");
					
					String[] nsxId = request.getParameterValues(temID + "_spNSXID");
					
					if(soLUONGXUAT != null)
					{
						for(int j = 0; j < soLUONGXUAT.length; j++ )
						{
							if(soLUONGXUAT[j] != null && soLUONGXUAT[j].trim().length() > 0  )
							{
								String key = masp[i] + "__ " + "__" + spSOLO[j] + "__" + spNGAYHETHAN[j] + "__" + spNGAYNHAPKHO[j]
												+ "__" + spMAME[j] + "__" + spMATHUNG[j] + "__" + spVITRI[j] 
												+ "__" + spMAPHIEU[j] + "__" + spPHIEUDT[j] + "__" + spPHIEUEO[j]
												+ "__" + nsxId[j]
												+ "__" + spMERQ[j] + "__" + spHAMLUONG[j] + "__" + spHAMAM[j];
								
								sanpham_soluong.put(key, soLUONGXUAT[j].replaceAll(",", "") );
								sanpham_thongtindoi.put(key, spTHONGTINDOI[j]);
								
								if( loaidieuchinh.equals("0") || loaidieuchinh.equals("6") )
									sanpham_ngayhethandoi.put(key, spNGAYHHDOI[j]);
								
							}
						}
					}
				}
				
				lsxBean.setSanpham_Soluong(sanpham_soluong);
				lsxBean.setSanpham_Thongtindoi(sanpham_thongtindoi);
				lsxBean.setSanpham_Ngayhethandoi(sanpham_ngayhethandoi);
			}
			
		    
			if(action.equals("save"))
			{	
				if(id.length() ==0 )
				{
					if(!lsxBean.createCK())
					{
						lsxBean.createRs();
	    		    	session.setAttribute("ckBean", lsxBean);

	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiSoLoNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpDoisoloList obj = new ErpDoisoloList();
						obj.setUserId(userId);

						obj.init("");  
				    	session.setAttribute("obj", obj);  	
			    		session.setAttribute("userId", userId);
			    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpDoiSoLo.jsp");
					}
				}
				else
				{
					if(!lsxBean.updateCK())
					{
						lsxBean.createRs();
						session.setAttribute("ckBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiSoLoNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpDoisoloList obj = new ErpDoisoloList();

						obj.setUserId(userId);
					    obj.init("");
						session.setAttribute("obj", obj);							
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiSoLo.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
				if(action.equals("changeKHO"))
				{
					lsxBean.setSpList(new ArrayList<IYeucau>());
				}
				
				session.setAttribute("khochuyenIds", khoxuatId);
				/*session.setAttribute("nccchuyenId", nccchuyenId);*/
 
				lsxBean.createRs();
				
				session.setAttribute("ckBean", lsxBean);
				String nextJSP = "";
				
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiSoLoNew.jsp";
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
