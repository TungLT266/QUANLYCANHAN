package geso.traphaco.erp.servlets.nhaphangungtra;
 
 
 
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.nhaphangungtra.imp.*;
import geso.traphaco.erp.beans.nhaphangungtra.*;
 
 

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

 

public class ErpNhaphangungtraUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	private static float CM = 28.346457f;
       
	PrintWriter out;
    public ErpNhaphangungtraUpdateSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		 Utility cutil = ( Utility) session.getAttribute("util");
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
		    IErpNhaphangungtra lsxBean = new ErpNhaphangungtra(id);
		    lsxBean.setUserId(userId); 
		    lsxBean.setCongtyId((String)session.getAttribute("congtyId"));
		    String task = request.getParameter("task");
			if(task == null)
				task = "0";
			lsxBean.settask(task);
			
	        String nextJSP = "";
			
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhaphangungtraNew.jsp";
			if(request.getQueryString().indexOf("display") >= 0 ) 
	        {
				lsxBean.init(); 
        		nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhaphangungtraDisplay.jsp";
	        }else{
	        	lsxBean.init(); 
	        }
			session.setAttribute("khochuyenIds", lsxBean.getKhoXuatId());
			session.setAttribute("nccchuyenId", lsxBean.getNccChuyenIds());
			session.setAttribute("khid_xuat", lsxBean.getKhachHangId_xuat());
			session.setAttribute("nvid_xuat", lsxBean.getNvId_xuat());
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
			IErpNhaphangungtra lsxBean;
			
			Utility util = new Utility();	
			String id = util.antiSQLInspection(request.getParameter("id"));
		    if(id == null)
		    {  	
		    	lsxBean = new ErpNhaphangungtra("");
		    }
		    else
		    {
		    	lsxBean = new ErpNhaphangungtra(id);
		    }
	
		    lsxBean.setUserId(userId);
		    
		    
		    lsxBean.setCongtyId((String)session.getAttribute("congtyId"));
		    
		    
		    String task = request.getParameter("task");
			if(task == null)
				task = "0";
			lsxBean.setIsnhanHang(task);
			
			 lsxBean.setCongtyId((String)session.getAttribute("congtyId"));
			 
		    String ngayyeucau = util.antiSQLInspection(request.getParameter("ngaychuyen"));
		    if(ngayyeucau == null || ngayyeucau.trim().length() <= 0)
		    	ngayyeucau = getDateTime();
		    lsxBean.setNgayyeucau(ngayyeucau);
		    
		    String lydo = util.antiSQLInspection(request.getParameter("lydo"));
		    if(lydo == null)
		    	lydo = "";
		    lsxBean.setLydoyeucau(lydo);
		    
		    String doituongunghangid = util.antiSQLInspection(request.getParameter("doituongunghangid"));
		    if(doituongunghangid == null)
		    	doituongunghangid = "";
		    lsxBean.setDoiTuongUngHangId(doituongunghangid);
		    
		    
		    
		    String kenhid = util.antiSQLInspection(request.getParameter("kenhid"));
		    if(kenhid == null)
		    	kenhid = "";
		    lsxBean.setKenhId(kenhid);
		    
		    
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
			
			session.setAttribute("noidungxuat", lsxBean.getNdxId());
	    	
			System.out.println("NOI DUNG NHAP XUAT: " + noidungxuat);
			
		    String khoxuatId = util.antiSQLInspection(request.getParameter("khoxuatId"));
			if (khoxuatId == null)
				khoxuatId = "";				
			lsxBean.setKhoXuatId(khoxuatId);
 
			 session.setAttribute("loaikhoxuat", lsxBean.getLoaiKhoXuat());
			 
			 
			 String nccchuyenId = util.antiSQLInspection(request.getParameter("nccchuyenId"));
				if (nccchuyenId == null)
					nccchuyenId = "";				
				lsxBean.setNccChuyenIds(nccchuyenId);
				
				session.setAttribute("nccchuyenId", nccchuyenId);
				
				String khid_xuat = util.antiSQLInspection(request.getParameter("khid_xuat"));
				if (khid_xuat == null)
					khid_xuat = "";				
				lsxBean.setKhachHangid_xuat(khid_xuat);
				
				session.setAttribute("khid_xuat", khid_xuat);
				
				String khid_nhan = util.antiSQLInspection(request.getParameter("khid_nhan"));
				if (khid_nhan == null)
					khid_nhan = "";				
				lsxBean.setKhachHangId_nhan(khid_nhan);
				
				
				String nccnhanId = util.antiSQLInspection(request.getParameter("nccnhapId"));
				if (nccnhanId == null)
					nccnhanId = "";				
				lsxBean.setNccNhanIds(nccnhanId);
				
				
				   String nvid_xuat=request.getParameter("nvid_xuat");
					if(nvid_xuat == null)
						nvid_xuat = "";
					lsxBean.setNvid_xuat(nvid_xuat);
					
					session.setAttribute("nvid_xuat", nvid_xuat);
					
					
					
					String nvid_nhan=request.getParameter("nvid_nhan");
					if(nvid_nhan == null)
						nvid_nhan = "";
					lsxBean.setNvId_nhan(nvid_nhan);
					
					String khonhapId=request.getParameter("khonhapId");
					if(khonhapId == null)
						khonhapId = "";
					lsxBean.setKhoNhapId(khonhapId);
					
					session.setAttribute("khonhapId", lsxBean.getKhoNhapId());
					
					System.out.println("KHO NHAP ID: " + khonhapId);
					
					
			
			String nguoinhan = util.antiSQLInspection(request.getParameter("nguoinhan"));
			if (nguoinhan == null)
				nguoinhan = "";				
			lsxBean.setNguoinhan(nguoinhan);
			
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
			
			
			String nguoidenghi = util.antiSQLInspection(request.getParameter("nguoidenghi"));
			if (nguoidenghi == null)
				nguoidenghi = "";				
			lsxBean.setNguoidenghi(nguoidenghi);
			
			String donvithuchienid = util.antiSQLInspection(request.getParameter("donvithuchienid"));
			if (donvithuchienid == null)
				donvithuchienid = "";				
			lsxBean.setDonvithuchienId(donvithuchienid);
			
			String nhomchiphiid = util.antiSQLInspection(request.getParameter("nhomchiphiid"));
			if (nhomchiphiid == null)
				nhomchiphiid = "";				
			lsxBean.setNhomChiPhiId(nhomchiphiid);
			
			//session.setAttribute("nppId", lsxBean.getNppId());
			String loaisanphamcu = util.antiSQLInspection(request.getParameter("loaisanphamcu"));
			System.out.println("====================CU:"+loaisanphamcu);
			 String loaisanpham = util.antiSQLInspection(request.getParameter("loaisanpham"));
				if (loaisanpham == null)
					loaisanpham = "";
			
			
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
						yeucau.setSoluongTon(soluongton[m].replaceAll(",",""));
						yeucau.setTonhientai(soluongton[m].replaceAll(",",""));
						//System.out.println(soluongton[m].replaceAll(",",""));
						
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
				
				System.out.println("DANH SACH SAN PHAM: " + lsxBean.getSpList().size());
			}
			
			if(noidungxuat.equals("100051")){
				loaisanphamcu = null; 
			}
			
			
			if(!loaisanpham.equals(loaisanphamcu) && loaisanpham.trim().length() > 0){
				spList.clear();
			} 
			
			System.out.println("====================CU:"+loaisanphamcu);
			System.out.println("====================MOI:"+loaisanpham);
			
			lsxBean.setLspId(loaisanpham);
			session.setAttribute("loaisanpham", loaisanpham);
 
		    String action = request.getParameter("action");
		    System.out.println("Action la: " + action);
		    
			if(action.equals("save"))
			{	
				if(id.length() ==0 )
				{
					if(!lsxBean.createCK())
					{
						lsxBean.createRs();
	    		    	session.setAttribute("lsxBean", lsxBean);
	    		    	lsxBean.settask(task);
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhaphangungtraNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpNhaphangungtraList obj = new ErpNhaphangungtraList();
						obj.setUserId(userId);
						obj.setIsnhanHang(task);
						obj.setCongtyId((String)session.getAttribute("congtyId"));
						 
						obj.init("");  
				    	session.setAttribute("obj", obj); 
			    		session.setAttribute("userId", userId);
			    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpNhaphangungtra.jsp");
					}
				}
				else
				{
					if(!lsxBean.updateCK())
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
						lsxBean.setIsnhanHang(task);
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhaphangungtraNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpNhaphangungtraList obj = new ErpNhaphangungtraList();

						obj.setUserId(userId);
						obj.setIsnhanHang(task);
						obj.setCongtyId((String)session.getAttribute("congtyId"));
					    obj.init("");
					
						 
						session.setAttribute("obj", obj);							
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhaphangungtra.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else if(action.equals("savesohoadon"))
			{
				if(!lsxBean.updateSoHoaDon())
				{
					lsxBean.createRs();
					session.setAttribute("lsxBean", lsxBean);
    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhaphangungtraDisplay.jsp";   				
    				lsxBean.initView();   
    				response.sendRedirect(nextJSP);
				}
				else
				{
					IErpNhaphangungtraList obj = new ErpNhaphangungtraList();

					obj.setUserId(userId);
					obj.setIsnhanHang(task);
					obj.setCongtyId((String)session.getAttribute("congtyId"));
					
				    obj.init("");

					session.setAttribute("obj", obj);							
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhaphangungtra.jsp";
					response.sendRedirect(nextJSP);
				}
			}
			else{
				if(action.equals("changeKHO"))
				{
					lsxBean.setSpList(new ArrayList<IYeucau>());
				}
				
				session.setAttribute("khochuyenIds", khoxuatId);
				session.setAttribute("nccchuyenId", nccchuyenId);
				
				
				lsxBean.createRs();
				session.setAttribute("khonhanid",lsxBean.getKhoNhapId());
				session.setAttribute("lsxBean", lsxBean);
				lsxBean.settask(task);
				String nextJSP = "";
				
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhaphangungtraNew.jsp";
			 
				
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
