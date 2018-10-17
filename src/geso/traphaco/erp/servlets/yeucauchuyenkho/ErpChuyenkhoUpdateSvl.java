package geso.traphaco.erp.servlets.yeucauchuyenkho;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpChuyenkho;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpChuyenkhoList;
import geso.traphaco.erp.beans.yeucauchuyenkho.IYeucau;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpChuyenkho;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpChuyenkhoList;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.Yeucau;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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

public class ErpChuyenkhoUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public ErpChuyenkhoUpdateSvl() 
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
		    IErpChuyenkho lsxBean = new ErpChuyenkho(id);
		    lsxBean.setUserId(userId); 
		    
		    String isnhanhang = request.getParameter("isnhanHang");
			if(isnhanhang == null)
				isnhanhang = "0";
			lsxBean.setIsnhanHang(isnhanhang);
			
	        String nextJSP = "";
	        if(request.getQueryString().indexOf("nhanhang") >= 0 )
	        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKhoNhanHang.jsp";
	        else
	        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKhoNew.jsp";
	        	
			if(request.getQueryString().indexOf("display") >= 0 ) 
			{
				if( isnhanhang.equals("0") )
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKhoDisplay.jsp";
				else
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKhoNhanHangDisplay.jsp";
			}

	        System.out.println("DIS");
			lsxBean.init(); 
			session.setAttribute("khochuyenIds", lsxBean.getKhoXuatId());
			session.setAttribute("khonhanIds", lsxBean.getKhoNhapId());
			session.setAttribute("vitriId", "");
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
			IErpChuyenkho lsxBean;
			
			Utility util = new Utility();	
			String id = util.antiSQLInspection(request.getParameter("id"));
		    if(id == null)
		    {  	
		    	lsxBean = new ErpChuyenkho("");
		    }
		    else
		    {
		    	lsxBean = new ErpChuyenkho(id);
		    }
	
		    lsxBean.setUserId(userId);
		    
		    String isnhanhang = request.getParameter("isnhanHang");
			if(isnhanhang == null)
				isnhanhang = "0";
			lsxBean.setIsnhanHang(isnhanhang);
		    
		    String ngayyeucau = util.antiSQLInspection(request.getParameter("ngaychuyen"));
		    if(ngayyeucau == null || ngayyeucau.trim().length() <= 0)
		    	ngayyeucau = getDateTime();
		    lsxBean.setNgayyeucau(ngayyeucau);
		    
		    
		    
		    // lay ngay xuat thuc te
		    String ngayxuatthucte = util.antiSQLInspection(request.getParameter("ngayxuatthucte"));
		    if(ngayxuatthucte == null || ngayxuatthucte.trim().length() <= 0)
		    	ngayxuatthucte ="";
		    lsxBean.setNgayxuatthucte(ngayxuatthucte);
		    
		    
		    
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
			
			String ycckId = util.antiSQLInspection(request.getParameter("ycckId"));
			if (ycckId == null)
				ycckId = "";				
			lsxBean.setYcckId(ycckId);
			
			String lsxId = util.antiSQLInspection(request.getParameter("lsxId"));
			if (lsxId == null)
				lsxId = "";				
			lsxBean.setLsxIds(lsxId);
		    String CdsxId = util.antiSQLInspection(request.getParameter("CdsxId"));
		    if(CdsxId == null || CdsxId.trim().length() <= 0)
		    	CdsxId = "";
		    lsxBean.setCDSXId(CdsxId);
		    
			//CHI PHI
			String coChiphi = util.antiSQLInspection(request.getParameter("coChiphi"));
			if (coChiphi == null)
				coChiphi = "";				
			lsxBean.setCochiphi(coChiphi);
			
			String chiphiId = util.antiSQLInspection(request.getParameter("chiphiId"));
			if (chiphiId == null)
				chiphiId = "";				
			lsxBean.setChiphiId(chiphiId);
			
			String sokien = util.antiSQLInspection(request.getParameter("sokien"));
			if (sokien == null)
				sokien = "";				
			lsxBean.setSokien(sokien);
			
			//TEN NGUOI NHAN
			
			String tennguoinhan = util.antiSQLInspection(request.getParameter("tennguoinhan"));
			if (tennguoinhan == null)
				tennguoinhan = "";				
			lsxBean.setNguoinhan(tennguoinhan);
			
			String muahang_fk = util.antiSQLInspection(request.getParameter("muahang_fk"));
			if (muahang_fk == null)
				muahang_fk = "";				
			lsxBean.setMuahang_fk(muahang_fk);
			
			
			String[] idsp = request.getParameterValues("idsp");
			String[] masp = request.getParameterValues("masp");
			String[] tensp = request.getParameterValues("tensp");
			String[] dvt = request.getParameterValues("donvi");
		 
			String[] soluong = request.getParameterValues("soluongyeucau"); 
			String[] soluongton = request.getParameterValues("soluongtonkho"); 
			String[] ghichu_ck = request.getParameterValues("ghichu_ck"); 
		 
			
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

						yeucau.setLsxId(lsxId);
						if(dvt != null)
							yeucau.setDonViTinh(dvt[m]);
						
						spList.add(yeucau);
					}				
				}
				
				lsxBean.setSpList(spList);
			}	
			List<String> listSp=new ArrayList<String>();
			
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
					
					String[] spPK_SEQ = request.getParameterValues(temID + "_spPK_SEQ");
					String[] spId_ = request.getParameterValues(temID + "_spId");
					
					String[] spNhasanxuat = request.getParameterValues(temID + "_spNhasanxuat");
					String[] spNhasanxuatID = request.getParameterValues(temID + "_spNhasanxuatID");
					
					if(soLUONGXUAT != null)
					{
						for(int j = 0; j < soLUONGXUAT.length; j++ )
						{
							//if(soLUONGXUAT[j] != null && soLUONGXUAT[j].trim().length() > 0 && !soLUONGXUAT[j].trim().equals("0") && spSOLO[j].trim().length() >= 0 )
							if(soLUONGXUAT[j] != null && soLUONGXUAT[j].trim().length() > 0  && spSOLO[j].trim().length() >= 0 )
							{
								//System.out.println("---MA ME: " + spMAME[j]  + " -- MA THUNG: " + spMATHUNG[j] + " -- MA PHIEU: " +  spMAPHIEU[j] );
								//System.out.println("---VI TRI: " + spVITRI[j]  + " -- PHIEU DT: " + spPHIEUDT[j] );
								//System.out.println("---KEY SVL: " + ( masp[i] + "__ " + "__" + spSOLO[j] + "__" + spNGAYHETHAN[j] + "__" + spNGAYNHAPKHO[j] + "__" + spMERQ[j] + "__" + spHAMLUONG[j] + "__" + spHAMAM[j] + "__" + spVITRI[j] + "__" + spPHIEUDT[j] + "__" + spPHIEUEO[j] ) );
								
								//Trường hợp nhận hàng, kho có thể không quản lý vị trí nhận
								String key = "";
								if( spVITRI == null )
								{
									key = masp[i] + "__ " + "__" + spSOLO[j] + "__" + spNGAYHETHAN[j] + "__" + spNGAYNHAPKHO[j]
											+ "__" + spMAME[j] + "__" + spMATHUNG[j] + "__0"
											+ "__" + spMAPHIEU[j] + "__" + spPHIEUDT[j] + "__" + spPHIEUEO[j]
											+ "__" + spMERQ[j] + "__" + spHAMLUONG[j] + "__" + spHAMAM[j];
								}
								else
								{
									key = masp[i] + "__ " + "__" + spSOLO[j] + "__" + spNGAYHETHAN[j] + "__" + spNGAYNHAPKHO[j]
											+ "__" + spMAME[j] + "__" + spMATHUNG[j] + "__" + spVITRI[j] 
											+ "__" + spMAPHIEU[j] + "__" + spPHIEUDT[j] + "__" + spPHIEUEO[j]
											+ "__" + spMERQ[j] + "__" + spHAMLUONG[j] + "__" + spHAMAM[j] ;
								}
								
								if( isnhanhang.equals("1") ){
									key += "__" + spPK_SEQ[j];
								}else{
									key += "__" + spId_[j];
								}
								
								if( spVITRI == null ) {
									key += "__0";
								} else {
									key += "__" + spVITRI[j];
								}
								
								key += "__" + spNhasanxuat[j] + "__" + spNhasanxuatID[j];
								
								sanpham_soluong.put(key, soLUONGXUAT[j].replaceAll(",", "") );
								listSp.add(key);
								
							}
						}
					}
				}
				
				lsxBean.setSanpham_Soluong(sanpham_soluong);
				lsxBean.setKey(listSp);
			}
			
			System.out.println("DIS1");
		    String action = request.getParameter("action");
		    
		    if(action.equals("savesokien"))
		    {
		    	dbutils db=new dbutils();
		    	try {
					db.getConnection().setAutoCommit(false);
				
		    	String query="update erp_chuyenkho set sokien=N'"+sokien+"' where pk_seq='"+id+"'";
		    	if(!db.update(query))
				{
		    		lsxBean.setMsg( "Không thể cập nhật ERP_CHUYENKHO_SANPHAM_CHITIET " + query);
					db.getConnection().rollback();
				}
		    	
		    	db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
		    	} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	IErpChuyenkhoList obj = new ErpChuyenkhoList();

				obj.setUserId(userId);
				obj.setIsnhanHang(isnhanhang);
				
				String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
    			geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
    			
			    obj.init("");
				session.setAttribute("obj", obj);							
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKho.jsp";
				response.sendRedirect(nextJSP);
		    }
		    else if(action.equals("save"))
			{	
				if(id.length() ==0 )
				{
					if(!lsxBean.createCK())
					{
						lsxBean.setIsnhanHang(isnhanhang);
						lsxBean.createRs();
	    		    	session.setAttribute("ckBean", lsxBean);
	    		    	lsxBean.settask(isnhanhang);
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKhoNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpChuyenkhoList obj = new ErpChuyenkhoList();
						obj.setUserId(userId);
						obj.setIsnhanHang(isnhanhang);
						String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
		    			geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
						obj.init("");  
				    	session.setAttribute("obj", obj);  	
			    		session.setAttribute("userId", userId);
			    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpChuyenKho.jsp");
					}
				}
				else
				{
					if(!lsxBean.updateCK())
					{
						lsxBean.setIsnhanHang(isnhanhang);
						lsxBean.createRs();
						session.setAttribute("ckBean", lsxBean);
						lsxBean.setIsnhanHang(isnhanhang);
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKhoNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpChuyenkhoList obj = new ErpChuyenkhoList();

						obj.setUserId(userId);
						obj.setIsnhanHang(isnhanhang);
						
						String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
		    			geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
		    			
					    obj.init("");
						session.setAttribute("obj", obj);							
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKho.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else if( action.equals("chotnhanhang") )
			{
				if( !lsxBean.nhanHang() )
				{
					lsxBean.setIsnhanHang(isnhanhang);
					lsxBean.createRs();
					session.setAttribute("ckBean", lsxBean);
					lsxBean.setIsnhanHang(isnhanhang);
    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKhoNhanHang.jsp";
    				response.sendRedirect(nextJSP);
				}
				else
				{
					IErpChuyenkhoList obj = new ErpChuyenkhoList();

					obj.setUserId(userId);
					obj.setIsnhanHang(isnhanhang);
					
					String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
	    			geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
	    			
				    obj.init("");
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
				if(action.equals("ChangeNgaychuyen") && lsxId.length()> 0)
				{
					//chuyển kho của lệnh sản xuất
					lsxBean.init_sp_soluong(ngayyeucau);
				}
				
				session.setAttribute("khochuyenIds", khoxuatId);
				session.setAttribute("khonhanIds", khonhapId);
 
				lsxBean.createRs();
				
				session.setAttribute("ckBean", lsxBean);
				lsxBean.settask(isnhanhang);
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
