package geso.traphaco.distributor.servlets.chuyenkho;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpChuyenkhoSX;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpChuyenkhoSXList;
import geso.traphaco.erp.beans.yeucauchuyenkho.IYeucau;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpChuyenkhoSX;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpChuyenkhoSXList;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.Yeucau;
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

public class ErpChuyenKhoUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public ErpChuyenKhoUpdateSvl() 
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
		    IErpChuyenkhoSX lsxBean = new ErpChuyenkhoSX(id);
		    lsxBean.setUserId(userId); 
		    
		    String task = request.getParameter("task");
			if(task == null)
				task = "0";
			lsxBean.settask(task);
			
	        String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKhoNew.jsp";
			if(request.getQueryString().indexOf("display") >= 0 ) 
	        {
        		lsxBean.initView();
        		nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKhoDisplay.jsp";
	        }
			else
	        	lsxBean.init(); 
	        
			session.setAttribute("khochuyenIds", lsxBean.getKhoXuatId());
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
			IErpChuyenkhoSX lsxBean;
			
			Utility util = new Utility();	
			String id = util.antiSQLInspection(request.getParameter("id"));
		    if(id == null)
		    {  	
		    	lsxBean = new ErpChuyenkhoSX("");
		    }
		    else
		    {
		    	lsxBean = new ErpChuyenkhoSX(id);
		    }
	
		    lsxBean.setUserId(userId);
		    
		    String task = request.getParameter("task");
			if(task == null)
				task = "0";
			lsxBean.setIsnhanHang(task);
		    
		    String ngayyeucau = util.antiSQLInspection(request.getParameter("ngaychuyen"));
		    if(ngayyeucau == null || ngayyeucau.trim().length() <= 0)
		    	ngayyeucau = getDateTime();
		    lsxBean.setNgayyeucau(ngayyeucau);
		    
		    String lydo = util.antiSQLInspection(request.getParameter("lydo"));
		    if(lydo == null)
		    	lydo = "";
		    lsxBean.setLydoyeucau(lydo);
		    
		    String IsChuyenHangSX = util.antiSQLInspection(request.getParameter("IsChuyenHangSX"));
		    if(IsChuyenHangSX == null)
		    	IsChuyenHangSX = "0";
		    lsxBean.setIsChuyenHangSX(IsChuyenHangSX);
		    
		    String IsChuyenkhongdat= util.antiSQLInspection(request.getParameter("chuyenhangkhongdat"));
		    if(IsChuyenkhongdat == null)
		    	IsChuyenkhongdat = "0";
		    lsxBean.setIsChuyenhangkhongdat(IsChuyenkhongdat);
		    
		    String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
		    if(ghichu == null)
		    	ghichu = "";
		    lsxBean.setGhichu(ghichu);
		    
		    String noidungxuat = util.antiSQLInspection(request.getParameter("noidungxuat"));
			if (noidungxuat == null)
				noidungxuat = "";	
			lsxBean.setNdxId(noidungxuat);
	    	
		    String khoxuatId = util.antiSQLInspection(request.getParameter("khoxuatId"));
			if (khoxuatId == null)
				khoxuatId = "";				
			lsxBean.setKhoXuatId(khoxuatId);
 
			String khonhapId = util.antiSQLInspection(request.getParameter("khonhapId"));
			if (khonhapId == null)
				khonhapId = "";				
			lsxBean.setKhoNhapId(khonhapId);
			
			String nguoinhan = util.antiSQLInspection(request.getParameter("nguoinhan"));
			if (nguoinhan == null)
				nguoinhan = "";				
			lsxBean.setNguoinhan(nguoinhan);
			
			String tsddId = util.antiSQLInspection(request.getParameter("tsddId"));
			if (tsddId == null)
				tsddId = "";				
			lsxBean.setTsddId(tsddId);
			
			String kyhieu = util.antiSQLInspection(request.getParameter("kyhieu"));
			if (kyhieu == null)
				kyhieu = "";				
			lsxBean.setKyHieu(kyhieu);
			
			String sochungtu = util.antiSQLInspection(request.getParameter("sochungtu"));
			if (sochungtu == null)
				sochungtu = "";				
			lsxBean.setSochungtu(sochungtu);
			
			String lenhdieudong = util.antiSQLInspection(request.getParameter("lenhdieudong"));
			if (lenhdieudong == null)
				lenhdieudong = "";				
			lsxBean.setLenhdieudong(lenhdieudong);
			
			String ngaydieudong = util.antiSQLInspection(request.getParameter("ngaydieudong"));
			if (ngaydieudong == null)
				ngaydieudong = "";				
			lsxBean.setNgaydieudong(ngaydieudong);
			
			String nguoidieudong = util.antiSQLInspection(request.getParameter("nguoidieudong"));
			if (nguoidieudong == null)
				nguoidieudong = "";				
			lsxBean.setNguoidieudong(nguoidieudong);
			
			String veviec = util.antiSQLInspection(request.getParameter("veviec"));
			if (veviec == null)
				veviec = "";				
			lsxBean.setVeviec(veviec);
			
			String nguoivanchuyen = util.antiSQLInspection(request.getParameter("nguoivanchuyen"));
			if (nguoivanchuyen == null)
				nguoivanchuyen = "";				
			lsxBean.setNguoivanchuyen(nguoivanchuyen);
			
			String phuongtien = util.antiSQLInspection(request.getParameter("phuongtien"));
			if (phuongtien == null)
				phuongtien = "";				
			lsxBean.setPhuongtien(phuongtien);
			
			String hopdong = util.antiSQLInspection(request.getParameter("hopdong"));
			if (hopdong == null)
				hopdong = "";				
			lsxBean.setHopdong(hopdong);
			
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
			
			//BÊN NHẬN
			String cokhoNhan = util.antiSQLInspection(request.getParameter("cokhoNhan"));
			if (cokhoNhan == null)
				cokhoNhan = "";				
			lsxBean.setCoKhonhan(cokhoNhan);
			
			String codoituongNhan = util.antiSQLInspection(request.getParameter("codoituongNhan"));
			if (codoituongNhan == null)
				codoituongNhan = "";				
			lsxBean.setCoDoituongNhan(codoituongNhan);
			
			String loaidoituongNhanId = util.antiSQLInspection(request.getParameter("loaidoituongNhanId"));
			if (loaidoituongNhanId == null)
				loaidoituongNhanId = "";				
			lsxBean.setLoaidoituongNhanId(loaidoituongNhanId);
			
			String doituongNhanId = util.antiSQLInspection(request.getParameter("doituongNhanId"));
			if (doituongNhanId == null)
				doituongNhanId = "";				
			lsxBean.setDoituongNhanId(doituongNhanId);
			
			String[] idsp = request.getParameterValues("idsp");
			String[] masp = request.getParameterValues("masp");
			String[] tensp = request.getParameterValues("tensp");
			String[] dvt = request.getParameterValues("donvi");
		 
			String[] soluong = request.getParameterValues("soluongyeucau"); 
			String[] soluongton = request.getParameterValues("soluongtonkho"); 
			String[] ghichu_ck = request.getParameterValues("ghichu_ck"); 
			
			String[] lsxId = request.getParameterValues("lsxId"); 
			
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
						
						if(lsxId != null)
						{
							yeucau.setLsxId(lsxId[m]);
						}else
						{
							yeucau.setLsxId("");
						}
						
						if(dvt != null)
							yeucau.setDonViTinh(dvt[m]);
				 
						
						spList.add(yeucau);
					}				
				}
				
				lsxBean.setSpList(spList);
			}	
 
		    String action = request.getParameter("action");
			if(action.equals("save"))
			{	
				if(id.length() ==0 )
				{
					if(!lsxBean.createCK())
					{
						lsxBean.createRs();
	    		    	session.setAttribute("lsxBean", lsxBean);
	    		    	lsxBean.settask(task);
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKhoNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpChuyenkhoSXList obj = new ErpChuyenkhoSXList();
						obj.setUserId(userId);
						obj.setIsnhanHang(task);
						obj.init("");  
						lsxBean.DBclose();
				    	session.setAttribute("obj", obj);  	
			    		session.setAttribute("userId", userId);
			    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpChuyenKho.jsp");
					}
				}
				else
				{
					if(!lsxBean.updateCK())
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
						lsxBean.setIsnhanHang(task);
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKhoNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpChuyenkhoSXList obj = new ErpChuyenkhoSXList();

						obj.setUserId(userId);
						obj.setIsnhanHang(task);
					    obj.init("");
					    lsxBean.DBclose();
						session.setAttribute("obj", obj);							
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKho.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else if(action.equals("savesohoadon"))
			{
				System.out.println("Vào đây: "+action);
				if(!lsxBean.updateSoHoaDon())
				{
					lsxBean.createRs();
					session.setAttribute("lsxBean", lsxBean);
    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKhoDisplay.jsp";   				
    				lsxBean.initView();   
    				response.sendRedirect(nextJSP);
				}
				else
				{
					IErpChuyenkhoSXList obj = new ErpChuyenkhoSXList();
					obj.setUserId(userId);
					obj.setIsnhanHang(task);
				    obj.init("");
				    lsxBean.DBclose();
					session.setAttribute("obj", obj);							
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKho.jsp";
					response.sendRedirect(nextJSP);
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
				
				session.setAttribute("lsxBean", lsxBean);
				lsxBean.settask(task);
				String nextJSP = "";
				
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKhoNew.jsp";
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
