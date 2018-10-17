package geso.traphaco.erp.servlets.yeucauchuyenkho;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpDoiquycach;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpDoiquycachList;
import geso.traphaco.erp.beans.yeucauchuyenkho.IYeucau;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpDoiquycach;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpDoiquycachList;
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

public class ErpDoiquycachUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public ErpDoiquycachUpdateSvl() 
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
		    IErpDoiquycach lsxBean = new ErpDoiquycach(id);
		    lsxBean.setUserId(userId); 
		    
	        String nextJSP = "";
			if(request.getQueryString().indexOf("display") >= 0 ) 
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiQuyCachDisplay.jsp";
			else
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiQuyCachNew.jsp";
	       
			lsxBean.init(); 
			
			session.setAttribute("khochuyenIds", lsxBean.getKhoXuatId());
			session.setAttribute("khonhanIds", lsxBean.getKhoNhapId() );
	        session.setAttribute("ckBean", lsxBean);
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
			IErpDoiquycach lsxBean;
			
			Utility util = new Utility();	
			String id = util.antiSQLInspection(request.getParameter("id"));
		    if(id == null)
		    {  	
		    	lsxBean = new ErpDoiquycach("");
		    }
		    else
		    {
		    	lsxBean = new ErpDoiquycach(id);
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
			
			String khonhanId = util.antiSQLInspection(request.getParameter("khonhanId"));
			if (khonhanId == null)
				khonhanId = "";				
			lsxBean.setKhoNhapId(khonhanId);

			String kyhieu = util.antiSQLInspection(request.getParameter("kyhieu"));
			if (kyhieu == null)
				kyhieu = "";				
			lsxBean.setKyHieu(kyhieu);
			
			String sochungtu = util.antiSQLInspection(request.getParameter("sochungtu"));
			if (sochungtu == null)
				sochungtu = "";				
			lsxBean.setSochungtu(sochungtu);
			
			String[] sotaikhoan = request.getParameterValues("sotaikhoanCP");
			lsxBean.setSotaikhoanChiphi(sotaikhoan);
			
			String[] chiphi = request.getParameterValues("chiphi");
			lsxBean.setChiphi(chiphi);

			String[] idsp = request.getParameterValues("idsp");
			String[] masp = request.getParameterValues("masp");
			String[] tensp = request.getParameterValues("tensp");
			String[] dvt = request.getParameterValues("donvi");
			String[] dongia = request.getParameterValues("dongia");
			String[] soluong = request.getParameterValues("soluongyeucau"); 

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
						yeucau.setDonViTinh(dvt[m]);
						yeucau.setDongia(dongia[m]);
						yeucau.setSoluongYC(soluong[m].replaceAll(",",""));

						spList.add(yeucau);
					}				
				}
				
				lsxBean.setSpList(spList);
			}	
			
			if(masp != null  )  //LUU LAI THONG TIN NGUOI DUNG NHAP
			{
				Hashtable<String, String> sanpham_soluong = new Hashtable<String, String>();
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
					
					if(soLUONGXUAT != null)
					{
						for(int j = 0; j < soLUONGXUAT.length; j++ )
						{
							//if(soLUONGXUAT[j] != null && soLUONGXUAT[j].trim().length() > 0 && spSOLO[j].trim().length() > 0 )
							if(soLUONGXUAT[j] != null && soLUONGXUAT[j].trim().length() > 0  )
							{
								//System.out.println("---MA ME: " + spMAME[j]  + " -- MA THUNG: " + spMATHUNG[j] + " -- MA PHIEU: " +  spMAPHIEU[j] );
								//System.out.println("---VI TRI: " + spVITRI[j]  + " -- PHIEU DT: " + spPHIEUDT[j] );
								//System.out.println("---KEY SVL: " + ( masp[i] + "__ " + "__" + spSOLO[j] + "__" + spNGAYHETHAN[j] + "__" + spNGAYNHAPKHO[j] + "__" + spMERQ[j] + "__" + spHAMLUONG[j] + "__" + spHAMAM[j] + "__" + spVITRI[j] + "__" + spPHIEUDT[j] + "__" + spPHIEUEO[j] ) );
								
								sanpham_soluong.put(masp[i] + "__ " + "__" + spSOLO[j] + "__" + spNGAYHETHAN[j] + "__" + spNGAYNHAPKHO[j]
											+ "__" + spMAME[j] + "__" + spMATHUNG[j] + "__" + spVITRI[j] 
											+ "__" + spMAPHIEU[j] + "__" + spPHIEUDT[j] + "__" + spPHIEUEO[j]
											+ "__" + spMERQ[j] + "__" + spHAMLUONG[j] + "__" + spHAMAM[j], soLUONGXUAT[j].replaceAll(",", "") );
							}
						}
					}
				}
				
				lsxBean.setSanpham_Soluong(sanpham_soluong);
			}
			
			String[] idspNHAN = request.getParameterValues("idspNHAN");
			String[] maspNHAN = request.getParameterValues("maspNHAN");
			String[] tenspNHAN = request.getParameterValues("tenspNHAN");
			String[] dvtNHAN = request.getParameterValues("donviNHAN");
			String[] dongiaNHAN = request.getParameterValues("dongiaNHAN");
			String[] dongiaNHAN_saupb = request.getParameterValues("dongiaNHAN_saupb");
			String[] soluongNHAN = request.getParameterValues("soluongNHAN"); 

			List<IYeucau> spListNHAN = new ArrayList<IYeucau>();
			if(maspNHAN != null)
			{	
				IYeucau yeucau = null;
				for(int m = 0; m < maspNHAN.length; m++)
				{	
					if(maspNHAN[m] != "")
					{	
						yeucau = new Yeucau();
						yeucau.setId(idspNHAN[m]);
						yeucau.setMa(maspNHAN[m]);
						yeucau.setTen(tenspNHAN[m]);
						yeucau.setDonViTinh(dvtNHAN[m]);	
						yeucau.setDongia(dongiaNHAN[m]);	
						yeucau.setDongia2(dongiaNHAN_saupb[m]);	
						yeucau.setSoluongYC(soluongNHAN[m].replaceAll(",",""));
						
						spListNHAN.add(yeucau);
					}				
				}
				
				lsxBean.setSpNhanList(spListNHAN);
			}	
			
			if(maspNHAN != null  )
			{
				Hashtable<String, String> sanpham_soluongNHAN = new Hashtable<String, String>();
				for(int i = 0; i < maspNHAN.length; i++ )
				{
					String temID = "spNhan_" + i;

					String[] spSOLO = request.getParameterValues(temID + "_spSOLO");
					String[] spNGAYHETHAN = request.getParameterValues(temID + "_spNGAYHETHAN");
					
					String[] spMAME = request.getParameterValues(temID + "_spMAME");
					String[] spMATHUNG = request.getParameterValues(temID + "_spMATHUNG");
					String[] spMAPHIEU = request.getParameterValues(temID + "_spMAPHIEU");
					
					String[] spMERQ = request.getParameterValues(temID + "_spMarq");
					String[] spHAMLUONG = request.getParameterValues(temID + "_spHamluong");
					String[] spHAMAM = request.getParameterValues(temID + "_spHamam");
					
					String[] spVITRI = request.getParameterValues(temID + "_spVitri");
					String[] spPHIEUDT = request.getParameterValues(temID + "_spPhieudt");
					String[] spPHIEUEO = request.getParameterValues(temID + "_spPhieueo");
					
					String[] soLUONGNHAN = request.getParameterValues(temID + "_spSOLUONG");
					
					if(soLUONGNHAN != null )
					{
						for(int j = 0; j < soLUONGNHAN.length; j++ )
						{
							if(soLUONGNHAN[j] != null && soLUONGNHAN[j].trim().length() > 0 && spNGAYHETHAN[j].trim().length() > 0 )
							{
								String key = i + "__" + masp[i] + "__ " + spSOLO[j] + "__" + spNGAYHETHAN[j]
												+ "__" + spMAME[j] + "__" + spMATHUNG[j] + "__" + spVITRI[j] 
												+ "__" + spMAPHIEU[j] + "__" + spPHIEUDT[j] + "__" + spPHIEUEO[j]
												+ "__" + spMERQ[j] + "__" + spHAMLUONG[j] + "__" + spHAMAM[j];
								
								System.out.println("::: KEY NHAN SVL: " + key + " -- VALUE: " +  soLUONGNHAN[j].replaceAll(",", "") );
								sanpham_soluongNHAN.put( key, soLUONGNHAN[j].replaceAll(",", "") );
							}
						}
					}
				}
				
				lsxBean.setSanpham_SoluongNHAN(sanpham_soluongNHAN);
			}
			
 
		    String action = request.getParameter("action");
			if(action.equals("save"))
			{	
				if(id.length() ==0 )
				{
					if(!lsxBean.createCK())
					{
						lsxBean.createRs();
	    		    	session.setAttribute("ckBean", lsxBean);

	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiQuyCachNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpDoiquycachList obj = new ErpDoiquycachList();
						obj.setUserId(userId);

						obj.init("");  
				    	session.setAttribute("obj", obj);  	
			    		session.setAttribute("userId", userId);
			    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpDoiQuyCach.jsp");
					}
				}
				else
				{
					if(!lsxBean.updateCK())
					{
						lsxBean.createRs();
						session.setAttribute("ckBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiQuyCachNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpDoiquycachList obj = new ErpDoiquycachList();

						obj.setUserId(userId);
					    obj.init("");
						session.setAttribute("obj", obj);							
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiQuyCach.jsp";
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
				session.setAttribute("khonhanIds", khonhanId);

				lsxBean.createRs();
				
				session.setAttribute("ckBean", lsxBean);
				String nextJSP = "";
				
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiQuyCachNew.jsp";
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
