package geso.traphaco.erp.servlets.nhanhang;

import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility_Kho;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.nhanhang.IErpNhanhangList_Giay;
import geso.traphaco.erp.beans.nhanhang.IErpNhanhang_Giay;
import geso.traphaco.erp.beans.nhanhang.imp.ErpNhanhangList_Giay;
import geso.traphaco.erp.beans.nhanhang.imp.ErpNhanhang_Giay;
import geso.traphaco.erp.beans.nhanhang.imp.HoaChatKiemDinh; 
import geso.traphaco.erp.util.Library;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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

public class ErpNhanhang_GiaySvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpNhanhang_GiaySvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpNhanhangList_Giay obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    String loaidh = util.antiSQLInspection(request.getParameter("loai"));
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
 
	    String action = util.getAction(querystring);
	    
	    String nhId = util.getId(querystring);
	    
	    String msg = "";
	    String ctyId = "";
	    
	    obj = new ErpNhanhangList_Giay();
	    ctyId = (String)session.getAttribute("congtyId") ;
	    obj.setCongtyId(ctyId);
	    obj.setTrangthai("0");
	    obj.setUserId(userId);
	    obj.setLoaimh(loaidh);
	    
	    if (action.equals("delete"))
	    {	
	    	try
	    	{
		    	dbutils db = new dbutils();
		    	db.getConnection().setAutoCommit(false);
		    	msg = Delete(nhId, db);
		    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
		    	if(msg.length() > 0)
		    	{
		    		obj.setmsg(msg);
		    		db.getConnection().rollback();
		    	}
		    	else //xoa thanh cong
		    	{
		    		System.out.println("xoa thanh cong........");
		    		String poId = request.getParameter("poId");
		    		IErpNhanhang_Giay nhanhang = new ErpNhanhang_Giay(nhId);
		    		
		    		//nhanhang.init();
		    		if(!poId.equals("null")){
		    			nhanhang.updateDonmuahang(poId.substring(5, poId.length()));
		    		}
		    		
		    	}
		    	db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
				obj.setCongtyId((String)session.getAttribute("congtyId"));
	    	    obj.setUserId(userId);
	    	   
	    	    obj.init("");
	    		session.setAttribute("obj", obj);
	    		session.setAttribute("congtyId", obj.getCongtyId());
	    				
	    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHang_Giay.jsp";
	    		response.sendRedirect(nextJSP);
	    		return;
			
	    	}catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}
	    }
	    else if(action.equals("chot"))
	    {
						
			String lhhId = request.getParameter("lhhId");
    		if(lhhId == null)
    			lhhId = "";
    		String loai = request.getParameter("loai");
    		if(loai == null)
    			loai = "";
    		
    		msg = ChotNhanHang(nhId, userId, ctyId, lhhId, loai);
    		session.setAttribute("userId", userId);
    		obj.setTrangthai("0");	
    		obj.setLoaimh(loai);

    		GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);	
    		obj.setCongtyId((String)session.getAttribute("congtyId"));
    	    obj.setUserId(userId);
    		obj.setmsg(msg);
			obj.init("");
	    	session.setAttribute("obj", obj);  	
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpNhanHang_Giay.jsp");
			
    		return;
		     
	    }
	    else if(action.equals("convert"))
	    {
	    	IErpNhanhang_Giay nhBean = new ErpNhanhang_Giay();
	    	nhBean.setUserId(userId);
	    	nhBean.setCongtyId((String)session.getAttribute("congtyId"));
	    		    	
	    	String nccId = util.antiSQLInspection(request.getParameter("NCCId")); 	    	
	    	nhBean.setNccId(nccId); 
	    	
	    	String hoadonnccId = util.antiSQLInspection(request.getParameter("hoadonnccId"));
	    	nhBean.setHdNccId(hoadonnccId);
	    	
	    	String loaihd = util.antiSQLInspection(request.getParameter("loaihd"));
	    	nhBean.setLoaimh(loaihd);
	    	
	    	String loaihh = util.antiSQLInspection(request.getParameter("loaihh"));
	    	nhBean.setLoaihanghoa(loaihh);
	    	
	    	nhBean.setNdnId("100000");
	    	nhBean.setLdnId("100046");
	    			    
	    	nhBean.init_convert(hoadonnccId, loaihd);
	    	nhBean.createRs();
    		
	    	session.setAttribute("nhBean", nhBean);
	    	session.setAttribute("spList", "");
	    	
	    	System.out.println("NCCId gét:"+nhBean.getNccId());
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangNew_Giay.jsp";
    		response.sendRedirect(nextJSP);
    		
    		return;
	    }
	    else{
     
	    obj.setTrangthai("0");
	    obj.setmsg(msg);
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    obj.setUserId(userId);
	    obj.init("");
	    GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);	
		session.setAttribute("obj", obj);
		session.setAttribute("congtyId", obj.getCongtyId());
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHang_Giay.jsp";
		response.sendRedirect(nextJSP);
	    }
	}
	
	

	Hashtable<String, Hashtable<String, Long> > nhan_form_choSelected = null; 
	
	private String ChotNhanHang (String nhId, String userId, String ctyId, String lhhId, String loaimh) 
	{
		dbutils db = new dbutils();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			Utility util = new Utility();
			
			String query1= " select ngaynhan ,ISNULL(ISTUDONG,'0') AS ISTUDONG, loaihanghoa_fk, hdncc_fk from erp_nhanhang where pk_seq="+nhId+" ";
			
			ResultSet rs1 = db.get(query1);
			String ngaychotNV = "";
		 	String istudong = "0";
		 	String loaihh = "";
		 	String hdId = "";
			if(rs1.next()){
				ngaychotNV = rs1.getString("ngaynhan");
				istudong = rs1.getString("ISTUDONG");
				loaihh = rs1.getString("loaihanghoa_fk");
				hdId = rs1.getString("hdncc_fk");
			}
			rs1.close();
			
			String msg1=util.checkNgayHopLe(db, ngaychotNV);
			if(msg1.length() >0){
				db.update("rollback");
				return msg1;
			}
			
			 String query=" UPDATE nhsp SET nhsp.NGAYNHAPKHO=nh.NGAYNHAN FROM ERP_NHANHANG nh "+  
			   	   		  " inner join ERP_NHANHANG_SP_CHITIET nhsp on nh.PK_SEQ= nhsp.NHANHANG_FK "+
			   	   		  " where nh.pk_seq="+nhId;
			 
			 if(!db.update(query)){
				 	db.getConnection().rollback();
					return "1. Không thể chôt nhận hàng, vui lòng báo Admin: Không thể cập nhật  "+query;
			 }
			 
			 
			// Lay tai khoan No trong bang config
			// sua them cac truong de insert xuong bang erp_yeucaukiemdinh
			  query = 	  " SELECT nh1.NCC_KH_FK as NCC , nh1.KHONHAN_FK KHONHAN," 
							+ " NH.MUAHANG_FK,nh.SANPHAM_FK," 
							+ " DONGIA, TIENTE_FK, TYGIAQUYDOI, DONGIAVIET, ISNULL(NH.DONVI, '') AS DONVI, "
							+ " SP.MA AS MASP  ,SP.TEN AS SPTEN  "
							+ " FROM ERP_NHANHANG nh1 inner join "
							+ " ERP_NHANHANG_SANPHAM NH on nh1.PK_SEQ = NH.NHANHANG_FK "
							+ " INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=NH.SANPHAM_FK   "
							+ " INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ=SP.DVDL_FK "
							+ " WHERE NH.NHANHANG_FK ='" + nhId + "' and  nh.SOLUONGNHAN > 0";

			//System.out.println("_____LAY TK: " + query);
			ResultSet rsSp = db.get(query);
			double total_soluongNhan = 0;

			String NCC = "";
			while (rsSp.next()) {
				// bổ sung NCC
				NCC = rsSp.getString("NCC");
				String sanphamId = rsSp.getString("SANPHAM_FK");
				String khoNhanId = rsSp.getString("KHONHAN");
				String muaHangId = rsSp.getString("MUAHANG_FK");
				
				// tăng kho nhận
				// đang tạo cho từng sản phẩm
				String khoId = khoNhanId;
				boolean check  = TangKho(db, khoId ,nhId, sanphamId, muaHangId);
				if(check == false){
					db.getConnection().rollback();
					db.shutDown();
					return "1. Không thể chôt nhận hàng, vui lòng báo Admin: Không thể cập nhật kho";
				}
			}
			rsSp.close();
			
			if(loaihh.equals("0"))
			{
				query =  "select p.pk_seq park, a.SANPHAM_FK, a.SOLUONG, sum(c.SOLUONGNHAN) soluongnhan \n"+ 
						 "from ERP_HOADONNCC hd inner join ERP_HOADONNCC_DONMUAHANG a on hd.pk_seq = a.HOADONNCC_FK \n"+ 
						 "inner join ERP_NHANHANG b on a.HOADONNCC_FK = b.hdNCC_fk \n"+ 
						 "inner join ERP_NHANHANG_SANPHAM c on b.PK_SEQ = c.NHANHANG_FK and a.SANPHAM_FK = c.SANPHAM_FK and a.ngaynhandk = c.NGAYNHANDUKIEN \n"+
						 "inner join ERP_PARK p on hd.park_fk = p.pk_seq \n"+
						 "where HOADONNCC_FK = "+hdId+" group by p.pk_seq, a.SANPHAM_FK, a.soluong \n";
				System.out.println("::: Kiem tra hoan tat: "+query);
				ResultSet rs = db.get(query);
				int num = 0, hoantat = 0;
				String park = "";
				if(rs.next())
				{
					park = rs.getString("park");
					num++;
					if(rs.getDouble("SOLUONG") == rs.getDouble("soluongnhan"))
					{
						hoantat++;
					}
				}rs.close();
				if(num == hoantat)
				{
					query = "Update ERP_PARK set HOANTAT_NHANHANG = '1' where pk_seq = '" + park + "'";
					if(!db.update(query))
					{
						db.getConnection().rollback();
						db.shutDown();
						return "2. Không thể chôt nhận hàng: " + query;
					}
				}
			}
			else
			{
				query = "update p set p.HOANTAT_NHANHANG = '1' from ERP_PARK p inner join ERP_HOADONNCC hd on p.pk_seq = hd.park_fk where hd.pk_seq = "+hdId;
				if(!db.update(query))
				{
					db.getConnection().rollback();
					db.shutDown();
					return "2. Không thể chôt nhận hàng: " + query;
				}
			}
			
			//Sau nay phai them buoc check xem taikhoan do da co khoasothang chua
			String ngaychot = "";
			String ngaychungtu = "";
			String ttId = "";
			String sql= " SELECT nh.PK_SEQ, mh.PK_SEQ MUAHANG_FK ,mh.NGUONGOCHH, nh.NGAYCHOT , \n"+
						" (SELECT distinct C.PK_SEQ from ERP_SANPHAM A INNER JOIN ERP_LOAISANPHAM B ON A.LOAISANPHAM_FK = B.pk_seq \n"+
						"  INNER JOIN ERP_TAIKHOANKT C ON B.TAIKHOANKT_FK = C.SOHIEUTAIKHOAN \n"+
						"  WHERE A.PK_SEQ = nhsp.SANPHAM_FK AND C.CONGTY_FK = 100000) TAIKHOANNO_SANPHAM, \n"+
						" (SELECT PK_SEQ FROM ERP_TAIKHOANKT KT WHERE SOHIEUTAIKHOAN = '15120000' AND CONGTY_FK = 100000) TAIKHOAN_15120000, \n"+
						" (SELECT PK_SEQ FROM ERP_TAIKHOANKT KT WHERE SOHIEUTAIKHOAN = '15110000' AND CONGTY_FK = 100000) TAIKHOAN_15110000, \n"+
						"  nhsp.TYGIAQUYDOI tigia, nhsp.TIENTE_FK, mh.LOAI, nhsp.SOLUONGNHAN SOLUONG , nhsp.DONGIA,  \n"+
						" nhsp.TAISAN_FK, nhsp.CCDC_FK, nhsp.CHIPHI_FK, nhsp.SANPHAM_FK, nh.NCC_KH_FK NCC_FK, NCC.TAIKHOAN_FK TAIKHOAN_NCC, nh.hdNCC_FK \n"+
						" FROM erp_nhanhang nh INNER JOIN ERP_NHANHANG_SANPHAM nhsp on nh.PK_SEQ = nhsp.NHANHANG_FK \n"+
						" inner join ERP_MUAHANG mh on  nh.muahang_fk= mh.pk_seq  \n"+
						" INNER JOIN ERP_NHACUNGCAP NCC ON nh.NCC_KH_FK = NCC.PK_SEQ  \n"+
						" WHERE 1 = 1 AND nh.PK_SEQ = " + nhId;
			
			System.out.println(sql);
			
			ResultSet rs = db.get(sql);
			 
				while(rs.next())
				{
					String nguongocHH = "";					
					ngaychot = rs.getString("ngaychot");
					String namNV = ngaychot.substring(0, 4);
        			String thangNV = ngaychot.substring(5, 7);
        			
					nguongocHH = rs.getString("nguongocHH");
					loaimh = rs.getString("LOAI");
					String TAIKHOAN_NO = "";
					String TAIKHOAN_CO = "";
					String tiente_fk = rs.getString("tiente_fk");
					String hdNCC_fk = rs.getString("hdNCC_FK") == null?"":rs.getString("hdNCC_FK") ;
					
					double tygia = rs.getDouble("tigia");
					double sotienBVAT_VND = 0;
        		    double sotienBVAT_NT = 0;
        		    
        		    double soluong  =  rs.getDouble("soluong");
        		    double dongia = rs.getDouble("DONGIA");
        		    
        		    double dongia_VND = 0;
        		    double dongia_NT = 0;
        		    
        		    String doituongno = "";
        		    String madoituongno = "";
        		    
        		    String doituongco = "";
        		    String madoituongco = "";
        		    
        		    String muahang_fk = rs.getString("MUAHANG_FK") == null?"":rs.getString("MUAHANG_FK") ;
					
					String TAIKHOANNO_SANPHAM = rs.getString("TAIKHOANNO_SANPHAM") == null?"":rs.getString("TAIKHOANNO_SANPHAM") ;
					String TAIKHOAN_15120000 = rs.getString("TAIKHOAN_15120000") == null?"":rs.getString("TAIKHOAN_15120000") ;
					String TAIKHOAN_15110000 = rs.getString("TAIKHOAN_15110000") == null?"":rs.getString("TAIKHOAN_15110000") ;
					String TAIKHOAN_NCC = rs.getString("TAIKHOAN_NCC") == null?"":rs.getString("TAIKHOAN_NCC") ;
					
					String sanpham_fk =  rs.getString("SANPHAM_FK") == null ? "":rs.getString("sanpham_fk")  ;
        			String taisan_fk = rs.getString("TAISAN_FK") == null ? "":rs.getString("TAISAN_FK")  ;
        			String chiphi_fk = rs.getString("CHIPHI_FK") == null ? "":rs.getString("CHIPHI_FK")  ;
        			
        			String ncc_fk = rs.getString("ncc_fk") == null?"":rs.getString("ncc_fk") ;
					
					if(tiente_fk.equals("100000")) // TIỀN VIỆT
        			{
        				tygia = 1;
        				dongia_VND = dongia;
        				dongia_NT  = 0;
        				
        				sotienBVAT_VND = soluong*dongia_VND;
        				sotienBVAT_NT = soluong*dongia_VND;
        				
        			}
        			else
        			{
        				dongia_VND = dongia*tygia;
        				dongia_NT = dongia;
        				
        				sotienBVAT_VND = Math.round(soluong*dongia_VND);
        				sotienBVAT_NT = soluong*dongia_NT;
        				
        			}
					
					if(loaimh.equals("2")) // MUA CP
					{
						if(nguongocHH.equals("TN"))
						{
							if(sanpham_fk.trim().length() > 0)
							{
								doituongno = "Sản phẩm";
		                		madoituongno = sanpham_fk;
		                		
		                		doituongco = "Sản phẩm";
		                		madoituongco = sanpham_fk;
		                		
		                		TAIKHOAN_NO = TAIKHOANNO_SANPHAM;
		                		TAIKHOAN_CO = TAIKHOAN_15110000;
		                		
		                		if(sotienBVAT_VND > 0)
		                		{
		                			if(TAIKHOAN_NO.trim().length() <= 0 || TAIKHOAN_CO.trim().length() <= 0)
			    					{
		                				rs.close();
			    						db.getConnection().rollback();
			    						return "Loại sản phẩm, chi phí hoặc nhà cung cấp tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";

			    					}
			        				
			            		    String msg = util.Update_TaiKhoan_Vat_DienGiai_SP_KBH( db, thangNV, namNV, ngaychot, ngaychot, "Nhận hàng", nhId, TAIKHOAN_NO, TAIKHOAN_CO, "", 
			  							Double.toString(sotienBVAT_VND), Double.toString(sotienBVAT_VND), doituongno, madoituongno, doituongco, madoituongco, "0", Double.toString(soluong), Double.toString(dongia_VND), tiente_fk, Double.toString(dongia_NT), Double.toString(tygia), Double.toString(sotienBVAT_VND), 
			  							Double.toString(sotienBVAT_NT), "Nhận hàng - Tiền hàng", "0" , "" , "NULL","NULL" ,"NULL" , "NULL", "NULL", "NULL");
			  						            					
									if(msg.trim().length() > 0)
									{
										rs.close();
			    						db.getConnection().rollback();
										return msg;
									}
		                		}
							}
							
						}
						else if (nguongocHH.equals("NN"))
						{
							if(sanpham_fk.trim().length() > 0)
							{								
								// CHÊNH LÊCH TỈ GIÁ
		                		query =  " SELECT distinct a.PK_SEQ , A.TIGIA " +
				                				" FROM ERP_THUENHAPKHAU A INNER JOIN ERP_THUENHAPKHAU_HOADONNCC B ON A.PK_SEQ = B.THUENHAPKHAU_FK \n"+
				                				" INNER JOIN ERP_HOADONNCC C ON B.HOADONNCC_FK = C.PK_SEQ \n"+
				                				" WHERE C.PARK_FK IN (SELECT PK_SEQ FROM ERP_PARK WHERE TRANGTHAI IN (1, 2) ) AND C.PK_SEQ = "+hdNCC_fk+" AND A.TRANGTHAI IN (1,2) ";
		                		System.out.println(query);
		                		ResultSet rschenhlech = db.get(query);
		                		
		                		int tokhai_fk = 0;
		                		
		                		double tygia_tokhai = 0;
		                		
		                		double sotienBVAT_VND_TOKHAI = 0;
		                		double sotienBVAT_VND_HOADON = 0;
		                				                		
		                		if(rschenhlech!=null)
		                		{
		                			while(rschenhlech.next())
		                			{
		                				tokhai_fk = rschenhlech.getInt("PK_SEQ");
		                				tygia_tokhai = rschenhlech.getInt("TIGIA");
		                			}
		                			rschenhlech.close();
		                		}
		                		
		                		if(tokhai_fk<=0)
		                		{
		                			rs.close();
		    						db.getConnection().rollback();
									return "Vui lòng khai báo thuế nhập khẩu cho nhận hàng này!";
		                		}
		                		else
		                		{		                			            				
		            				sotienBVAT_VND_TOKHAI = Math.round(soluong*dongia*tygia_tokhai);
		            				sotienBVAT_VND_HOADON = Math.round(soluong*dongia*tygia);
		            						            	
		            				sotienBVAT_NT = Math.round(soluong*dongia);
			            		    
		                			if(tygia > tygia_tokhai)
		                			{
		                				doituongno = "Sản phẩm";
				                		madoituongno = sanpham_fk;
				                		
				                		doituongco = "Sản phẩm";
				                		madoituongco = sanpham_fk;
				                		
				                		TAIKHOAN_NO = TAIKHOANNO_SANPHAM;
				                		TAIKHOAN_CO = TAIKHOAN_15120000;
				                		
				            		    
		                			}		                			
		                			else if(tygia < tygia_tokhai)
		                			{
		                				doituongno = "Sản phẩm";
				                		madoituongno = sanpham_fk;
				                		
				                		doituongco = "Sản phẩm";
				                		madoituongco = sanpham_fk;
				                		
				                		TAIKHOAN_NO = TAIKHOAN_15120000 ;
				                		TAIKHOAN_CO = TAIKHOANNO_SANPHAM;
				                						            		    
		                			}			            			
			            		    
			            		    double chenhlech_VND =  Math.abs( sotienBVAT_VND_TOKHAI - sotienBVAT_VND_HOADON);
			            		    
			            		    if(chenhlech_VND > 0)
			            		    {
			            		    	 String msg = util.Update_TaiKhoan_Vat_DienGiai_SP_KBH( db, thangNV, namNV, ngaychot, ngaychot, "Nhận hàng", nhId, TAIKHOAN_NO, TAIKHOAN_CO, "", 
						  							Double.toString(chenhlech_VND), Double.toString(chenhlech_VND), doituongno, madoituongno, doituongco, madoituongco, "0", Double.toString(soluong), Double.toString(dongia_VND), tiente_fk, Double.toString(dongia_NT), Double.toString(tygia_tokhai), Double.toString(chenhlech_VND), 
						  							"0", "Nhận hàng - Chênh lệch", "0" , "" , "NULL","NULL" ,"NULL" , "NULL", "NULL", "NULL");
						  			
												if(msg.trim().length() > 0)
												{
													rs.close();
						    						db.getConnection().rollback();
													return msg;
												}				            		    	
			            		    }
		                		
			                		if(sotienBVAT_VND_TOKHAI > 0)
			                		{
			                			doituongno = "Sản phẩm";
				                		madoituongno = sanpham_fk;
				                		
				                		doituongco = "Sản phẩm";
				                		madoituongco = sanpham_fk;
				                		
				                		TAIKHOAN_NO = TAIKHOANNO_SANPHAM;
				                		TAIKHOAN_CO = TAIKHOAN_15120000;
				                		
			                			if(TAIKHOAN_NO.trim().length() <= 0 || TAIKHOAN_CO.trim().length() <= 0)
				    					{
			                				rs.close();
				    						db.getConnection().rollback();
				    						return "Loại sản phẩm, chi phí hoặc nhà cung cấp tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
	
				    					}
				        				
				            		    String msg = util.Update_TaiKhoan_Vat_DienGiai_SP_KBH( db, thangNV, namNV, ngaychot, ngaychot, "Nhận hàng", nhId, TAIKHOAN_NO, TAIKHOAN_CO, "", 
				  							Double.toString(sotienBVAT_VND_TOKHAI), Double.toString(sotienBVAT_VND_TOKHAI), doituongno, madoituongno, doituongco, madoituongco, "0", Double.toString(soluong), Double.toString(dongia_VND), tiente_fk, Double.toString(dongia_NT), Double.toString(tygia), Double.toString(sotienBVAT_VND_TOKHAI), 
				  							Double.toString(sotienBVAT_NT), "Nhận hàng - Tiền hàng", "0" , "" , "NULL","NULL" ,"NULL" , "NULL", "NULL", "NULL");
				  						            					
										if(msg.trim().length() > 0)
										{
											rs.close();
				    						db.getConnection().rollback();
											return msg;
										}
			                		}
		                					                				
		                		}
		                		
							}
						}
						
					}
					else if(loaimh.equals("1")) // TRONG NUOC
					{
						if(sanpham_fk.trim().length() > 0)
						{
							doituongno = "Sản phẩm";
	                		madoituongno = sanpham_fk;
	                		
	                		doituongco = "Nhà cung cấp";
	                		madoituongco = ncc_fk;
	                		
	                		TAIKHOAN_NO = TAIKHOANNO_SANPHAM;
	                		TAIKHOAN_CO = TAIKHOAN_15110000;
	                		
	                		if(sotienBVAT_VND > 0)
	                		{
	                			if(TAIKHOAN_NO.trim().length() <= 0 || TAIKHOAN_CO.trim().length() <= 0)
		    					{
	                				rs.close();
		    						db.getConnection().rollback();
		    						return "Loại sản phẩm, chi phí hoặc nhà cung cấp tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";

		    					}
		        				
		            		    String msg = util.Update_TaiKhoan_Vat_DienGiai_SP_KBH( db, thangNV, namNV, ngaychot, ngaychot, "Nhận hàng", nhId, TAIKHOAN_NO, TAIKHOAN_CO, "", 
		  							Double.toString(sotienBVAT_VND), Double.toString(sotienBVAT_VND), doituongno, madoituongno, doituongco, madoituongco, "0", Double.toString(soluong), Double.toString(dongia_VND), tiente_fk, Double.toString(dongia_NT), Double.toString(tygia), Double.toString(sotienBVAT_VND), 
		  							Double.toString(sotienBVAT_NT), "Nhận hàng - Tiền hàng", "0" , "" , "NULL","NULL" ,"NULL" , "NULL", "NULL", "NULL");
		  						            					
								if(msg.trim().length() > 0)
								{
									rs.close();
		    						db.getConnection().rollback();
									return msg;
								}
	                		}
						}
					}
					else if(loaimh.equals("0")) // NHẬP KHẨU
					{
						if(sanpham_fk.trim().length() > 0)
						{
							double sotienBVAT_VND_TOKHAI = 0;
	                		double sotienBVAT_VND_HOADON = 0;
							// CHÊNH LÊCH TỈ GIÁ
	                		query =  " SELECT distinct a.PK_SEQ , A.TIGIA " +
				            				" FROM ERP_THUENHAPKHAU A INNER JOIN ERP_THUENHAPKHAU_HOADONNCC B ON A.PK_SEQ = B.THUENHAPKHAU_FK \n"+
				            				" INNER JOIN ERP_HOADONNCC C ON B.HOADONNCC_FK = C.PK_SEQ \n"+
				            				" WHERE C.PARK_FK IN (SELECT PK_SEQ FROM ERP_PARK WHERE TRANGTHAI IN (1, 2) ) AND C.PK_SEQ = "+hdNCC_fk+" AND A.TRANGTHAI IN (1,2) ";
	                		
	                		ResultSet rschenhlech = db.get(query);
	                		
	                		int tokhai_fk = 0;
	                		
	                		double tygia_tokhai = 0;
	                		
	                		if(rschenhlech!=null)
	                		{
	                			while(rschenhlech.next())
	                			{
	                				tokhai_fk = rschenhlech.getInt("PK_SEQ");
	                				tygia_tokhai = rschenhlech.getInt("TIGIA");
	                			}
	                			rschenhlech.close();
	                		}
	                		
	                		if(tokhai_fk<=0)
	                		{
	                			rs.close();
	    						db.getConnection().rollback();
								return "Vui lòng khai báo thuế nhập khẩu cho nhận hàng này!";
	                		}
	                		else
	                		{		            
	                			sotienBVAT_VND_TOKHAI = Math.round(soluong*dongia*tygia_tokhai);
	            				sotienBVAT_VND_HOADON = Math.round(soluong*dongia*tygia);
	            						            	
	            				sotienBVAT_NT = Math.round(soluong*dongia);
	            				
	                			if(tygia > tygia_tokhai)
	                			{
	                				doituongno = "Sản phẩm";
			                		madoituongno = sanpham_fk;
			                		
			                		doituongco = "Sản phẩm";
			                		madoituongco = sanpham_fk;
			                		
			                		TAIKHOAN_NO = TAIKHOANNO_SANPHAM;
			                		TAIKHOAN_CO = TAIKHOAN_15120000;
			                		
			            		    
	                			}		                			
	                			else if(tygia < tygia_tokhai)
	                			{
	                				doituongno = "Sản phẩm";
			                		madoituongno = sanpham_fk;
			                		
			                		doituongco = "Sản phẩm";
			                		madoituongco = sanpham_fk;
			                		
			                		TAIKHOAN_NO = TAIKHOAN_15120000 ;
			                		TAIKHOAN_CO = TAIKHOANNO_SANPHAM;
			                						            		    
	                			}
		                		
	                			double chenhlech_VND =  Math.abs( sotienBVAT_VND_TOKHAI - sotienBVAT_VND_HOADON);
	                			
		            		    if(chenhlech_VND > 0)
		            		    {
		            		    	 String msg = util.Update_TaiKhoan_Vat_DienGiai_SP_KBH( db, thangNV, namNV, ngaychot, ngaychot, "Nhận hàng", nhId, TAIKHOAN_NO, TAIKHOAN_CO, "", 
					  							Double.toString(chenhlech_VND), Double.toString(chenhlech_VND), doituongno, madoituongno, doituongco, madoituongco, "0", Double.toString(soluong), Double.toString(dongia_VND), tiente_fk, Double.toString(dongia_NT), Double.toString(tygia_tokhai), Double.toString(chenhlech_VND), 
					  							"0", "Nhận hàng - Chênh lệch", "0" , "" , "NULL","NULL" ,"NULL" , "NULL", "NULL", "NULL");
					  			
											if(msg.trim().length() > 0)
											{
												rs.close();
					    						db.getConnection().rollback();
												return msg;
											}						            		    	
		            		    }
		            		    
		            		    if(sotienBVAT_VND_TOKHAI > 0)
		                		{
		                			doituongno = "Sản phẩm";
			                		madoituongno = sanpham_fk;
			                		
			                		doituongco = "Sản phẩm";
			                		madoituongco = sanpham_fk;
			                		
			                		TAIKHOAN_NO = TAIKHOANNO_SANPHAM;
			                		TAIKHOAN_CO = TAIKHOAN_15120000;
			                		
		                			if(TAIKHOAN_NO.trim().length() <= 0 || TAIKHOAN_CO.trim().length() <= 0)
			    					{
		                				rs.close();
			    						db.getConnection().rollback();
			    						return "Loại sản phẩm, chi phí hoặc nhà cung cấp tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";

			    					}
			        				
			            		    String msg = util.Update_TaiKhoan_Vat_DienGiai_SP_KBH( db, thangNV, namNV, ngaychot, ngaychot, "Nhận hàng", nhId, TAIKHOAN_NO, TAIKHOAN_CO, "", 
			  							Double.toString(sotienBVAT_VND_TOKHAI), Double.toString(sotienBVAT_VND_TOKHAI), doituongno, madoituongno, doituongco, madoituongco, "0", Double.toString(soluong), Double.toString(dongia_VND), tiente_fk, Double.toString(dongia_NT), Double.toString(tygia), Double.toString(sotienBVAT_VND_TOKHAI), 
			  							Double.toString(sotienBVAT_NT), "Nhận hàng - Tiền hàng", "0" , "" , "NULL","NULL" ,"NULL" , "NULL", "NULL", "NULL");
			  						            					
									if(msg.trim().length() > 0)
									{
										rs.close();
			    						db.getConnection().rollback();
										return msg;
									}
		                		}   
	                		}
						}
					}
				}
				rs.close();
				
				Library lib=new Library();
				
				msg1=lib.Capnhat_Ngaynhapkho_Nhanhang(userId, db, nhId);
				if(msg1.length()>0){
					db.getConnection().rollback();
					return msg1;
				}	
			 
			query = "Update ERP_NhanHang set trangthai = '1', giochot = '" + getTime() + "' where pk_seq = '" + nhId + "' and trangthai=0";
			if(db.updateReturnInt(query)!=1)
			{
				db.getConnection().rollback();
				db.shutDown();
				return "88.Không thể chôt nhận hàng: " + query;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			return "";
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (Exception e1) {
				e1.printStackTrace();
			}
			
			db.shutDown();
			
			return "Không thể chôt nhận hàng: " + e.getMessage();
		}
	}

	private boolean TangKho(dbutils db, String khoId, String nhanhang_fk, String sanpham, String muahang_fk){
		try{
			// tăng kho chi tiết
			String sql =  " select NH.NGAYNHAN ,a.SOLO, isnull(a.MATHUNG, '') MATHUNG, isnull(a.MAME, '') MAME, a.BIN_FK, a.SOLUONG, a.NGAYHETHAN, a.NGAYSANXUAT," +
						  " a.SANPHAM_FK, b.DONGIA, b.idmarquette, isnull((select MA from MARQUETTE m where m.PK_SEQ = b.idmarquette),'') as mamarquette " +
						  " from ERP_NHANHANG NH INNER JOIN " +
						  " ERP_NHANHANG_SP_CHITIET a  ON A.NHANHANG_FK= NH.PK_SEQ   inner join ERP_NHANHANG_SANPHAM b " +
						  " on a.NHANHANG_FK = b.NHANHANG_FK and a.SANPHAM_FK = b.SANPHAM_FK and isnull(a.NGAYNHANDUKIEN, '') = isnull(b.NGAYNHANDUKIEN, '')" +
						  " where a.SANPHAM_FK = "+sanpham+" and a.NHANHANG_FK = "+nhanhang_fk+" and a.MUAHANG_FK = "+ muahang_fk;
			 
			ResultSet rs = db.get(sql);
			 
				while(rs.next()){
					String sanpham_fk = rs.getString("SANPHAM_FK");
					String solo = rs.getString("SOLO");
					String khu_fk = rs.getString("BIN_FK")==null?"":rs.getString("BIN_FK");
					double soluong= rs.getDouble("SOLUONG");
					String ngayhethan= rs.getString("NGAYHETHAN");
					String ngaysanxuat = rs.getString("NGAYSANXUAT");
					double dongia = rs.getDouble("DONGIA");
					String idmarquette = rs.getString("idmarquette");
					String mamarquette = rs.getString("mamarquette");
					String mathung = rs.getString("MATHUNG");
					String mame = rs.getString("MAME");
					String date=rs.getString("NGAYNHAN");
					
					String kq = Update_Kho_Sp_Chitiet(db, khoId, sanpham_fk, soluong, 0.0, soluong, dongia, solo, null, khu_fk,
										date, date, ngaysanxuat, ngayhethan, idmarquette, mamarquette, mame, mathung);
					if( kq.trim().length() >0){
						return false;
					}
				}
				rs.close();
			 
			
			// tăng kho tổng
			sql = " select a.SANPHAM_FK, SUM(a.SOLUONG) as soluong " +
				  " from ERP_NHANHANG_SP_CHITIET a where SANPHAM_FK = "+sanpham+" and NHANHANG_FK = "+nhanhang_fk+" and MUAHANG_FK = "+muahang_fk+" " +
				  " group by SANPHAM_FK";
			
			rs = db.get(sql);
		 
				while(rs.next()){
					String sanpham_fk = rs.getString("SANPHAM_FK");
					double soluong= rs.getDouble("SOLUONG");
					String kq = Update_Kho_Sp(db, khoId, sanpham_fk,soluong,0.0,soluong,0.0);
					if( kq.trim().length() >0){
						return false;
					}
				}
				rs.close();
			 
			return true;
		} catch(Exception ex){
			ex.printStackTrace();
			return false;
		}	
	}
	
	public String Update_Kho_Sp(dbutils db, String khott_fk, String spId,
			double soluong ,double booked,double available , double dongia) {
		// TODO Phương thức đưa số lượng nhập vào kho,bảng ERP_KHOTT_SANPHAM
		try{
			String query="  select sanpham_fk ,available,soluong , sp.ma+ ' '+ sp.ten as ten , ISNULL(KHO.GIATON,0) AS GIATON   from erp_khott_sanpham kho " +
						 "  inner join erp_sanpham sp  on kho.sanpham_fk=sp.pk_seq  where khott_fk="+khott_fk+" and sanpham_fk= "+spId;
			 
			double available_ton=0;
			double giaton=0;
			 double soluongton=0;
			
			ResultSet rsCheck = db.get(query);
			if(rsCheck.next()){
				     soluongton=rsCheck.getDouble("soluong");
				     available_ton=rsCheck.getDouble("available");
				     giaton=rsCheck.getDouble("GIATON");
				    
				    if(available < 0 && available_ton < (-1)*available ){
				    	return "Số lượng tồn hiện tại trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				    }
				    
				    if(soluong < 0 && soluongton <(-1)*soluong ){
				    	return "Số lượng tồn  trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				    }
				    
				    if(giaton >0){
				    	if( giaton- dongia !=0) {
				    		
				    		query=" insert into log_sql_khott(ngay,khott_fk,sanpham_fk ,ghichu ) " +
				    			  " values( GETDATE(),"+khott_fk+","+spId+",N'đơn giá khác nhau giữa 2 kho :Giá cũ :"+giaton+". Giá mới :"+dongia+"' )";
				    		
				    		db.update(query);
				    		
				    	}
				    } 
					
					query = " Update ERP_KHOTT_SANPHAM set booked=isnull(booked,0)+"+booked+" , soluong =ISNULL(soluong,0) + " + soluong + ", " +
							" AVAILABLE = ISNULL(AVAILABLE,0) + " + available + ",giaton="+(giaton >0?giaton:dongia)+"  "+
							" where khott_fk =" + khott_fk + " and sanpham_fk = " +   spId + "";
			}else{
					query=  " INSERT INTO ERP_KHOTT_SANPHAM ( KHOTT_FK,SANPHAM_FK,GIATON,SOLUONG,BOOKED,AVAILABLE,THANHTIEN ) VALUES  " +
						    " ("+khott_fk+","+ spId+","+dongia+","+soluong+","+booked+","+available+","+soluong+"*"+dongia+")";
					
					if(available < 0 && available_ton < (-1)*available ){
				    	return "Số lượng tồn hiện tại trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				    }
				    
				    if(soluong < 0 && soluongton <(-1)*soluong ){
				    	return "Số lượng tồn  trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				    }
					
			}
			rsCheck.close();
			int resultInt = db.updateReturnInt(query);
			
			if(resultInt != 1)
			{
				return  " Không thể cập nhật ERP_KHOTT_SANPHAM " + query;
			}
		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp : "+er.getMessage();
		}
		return "";
	}
	public String Update_Kho_Sp_Chitiet(geso.traphaco.distributor.db.sql.dbutils db, String khott_fk,
			String SANPHAM_FK, double soluongnhap,double booked,double available , double DONGIAMUA,
			String solo, String vitri,String KHUVUCKHO_FK, String NgayBatDau,String NgayNhapKho ,
			String NgaySanXuat,String NGAYHETHAN, String idmarquette, String mamarquette, String mame, String mathung) {
		/*
		 * Phương thức này là cập nhật số lượng nhập kho vào bảng ERP_KHOTT_SP_CHITIET ,cả đạt chất lượng và không đạt chất lượng.
		 * Hàng không đạt chất lượng chỉ có trong kho chờ xử lý
		 * 
		 * Mặc định trong nhận hàng hàm ẩm và hàm lượng chỗ nhận hàng cứ mặc định theo nguyên tắc sau:
		 * Hàm ẩm là 0%
		 * Hàm lượng là 100%
		 * 
		 */
		try{

			vitri="100000";
			String	query = " select count(*) as sodong from ERP_KHOTT_SP_CHITIET " +
					" where   KHOTT_FK = '" + khott_fk + "' and SANPHAM_FK = " + SANPHAM_FK + " " +
					" and  ltrim(rtrim(SOLO)) = '" + solo.trim() + "' "+ (KHUVUCKHO_FK.length() >0?" and  " +
					"bin_fk ="+KHUVUCKHO_FK:"") +" and NGAYBATDAU='"+NgayBatDau+"'" +"and MAME='"+mame+"' and MATHUNG='"+mathung+"'"; 
			
			if(idmarquette == null){
				query += " and IDMARQUETTE is null";
			} else {
				query += " and IDMARQUETTE = "+ idmarquette;
			}
			
			System.out.println(query);

			ResultSet	rsCheck = db.get(query);
			boolean flag = false;
			if(rsCheck.next())
			{
				if(rsCheck.getInt("sodong") > 0) {
					flag = true;
				}
			}
			rsCheck.close();

			if(flag)
			{
				query = " update ERP_KHOTT_SP_CHITIET set booked = isnull(booked,0) + "+booked+", soluong = soluong + " + soluongnhap + ", "+
						" AVAILABLE = AVAILABLE + " + available + ", HAMAM = 0, HAMLUONG = 100, MARQ = N'"+ mamarquette+ "' " + 
						" where KHOTT_FK = '" + khott_fk + "' and SANPHAM_FK = " +SANPHAM_FK + 
						" and ltrim(rtrim(SOLO)) = '" + solo.trim() + "' " 
						+(KHUVUCKHO_FK.length() >0?"and  bin_fk ="+KHUVUCKHO_FK:"")+" and NGAYBATDAU='"+NgayBatDau+"'"
						+" and MAME='"+mame+"' and MATHUNG='"+mathung+"'";
				
				if(idmarquette == null){
					query += " and IDMARQUETTE is null";
				} else {
					query += " and IDMARQUETTE = "+ idmarquette;
				}
			}
			else
			{
				query = "  insert ERP_KHOTT_SP_CHITIET(KHOTT_FK, SANPHAM_FK, SOLUONG, BOOKED, AVAILABLE, SOLO, NGAYSANXUAT, NGAYHETHAN, NGAYNHAPKHO, NGAYBATDAU, " +
						"  BIN_FK, DONGIAMUA, IDMARQUETTE, HAMAM, HAMLUONG, MARQ, MAME, MATHUNG) " +
						"  VALUES ( " + khott_fk + " ,  " + SANPHAM_FK + " , " +soluongnhap+ ", "+booked+", " +available + ", '" + solo.trim() + "', '"+NgaySanXuat+"', '"+NGAYHETHAN+"'," +
						"  '"  + NgayNhapKho + "','"+NgayBatDau+"',"+(KHUVUCKHO_FK.length() >0?KHUVUCKHO_FK:"NULL")+", " +
						+DONGIAMUA+","+idmarquette+",0,100,'"+ mamarquette +"','"+mame+"','"+mathung+"') " ;
			}	

			if(db.updateReturnInt(query)!=1)
			{
				return "Không thể cập nhật ERP_KHOTT_SP_CHITIET " + query;
			}
			
			return "";
		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp_Chitiet : "+er.getMessage();
		}
	}
	
	private int  CheckTonTaiList(List<String> list, String hoachat){
		for(int i=0; i< list.size(); i++){
			if(hoachat.equals(list.get(i))){
				return i;
			}
		}
		return -1;
	}
	
	private boolean TinhToanHoaChatCanDung(String SanPhamId, String NCC, List<HoaChatKiemDinh> list){
		
		dbutils db = new dbutils();
		try{
		
		String sql = " select * from (select  count(*) as m,SANPHAM_FK, HOACHAT,SOCHATDUOCKIEMDINH, " +
				     " SOCHATKIEMDINH, NCC_FK from HOACHAT_SANPHAM where SANPHAM_FK = "+SanPhamId+" " +
				     " and NCC_FK = "+NCC+" group by SANPHAM_FK, HOACHAT,SOCHATDUOCKIEMDINH, " +
				     " SOCHATKIEMDINH, NCC_FK " +
				     " union " +
				     " select count(*) as m, SANPHAM_FK, HOACHAT, SOCHATDUOCKIEMDINH, SOCHATKIEMDINH, " +
				     " isnull(NCC_FK,0) from HOACHAT_SANPHAM where SANPHAM_FK = "+SanPhamId+
				     " and NCC_FK is null " +
				     " group by SANPHAM_FK, HOACHAT,SOCHATDUOCKIEMDINH, SOCHATKIEMDINH ,NCC_FK ) " +
				     " as a order by a.NCC_FK desc";
		
		ResultSet rs = db.get(sql);
		int sodong =0;
		int dem =0;
		if(rs!=null){
			while(rs.next()){
				sodong = rs.getInt("m");
				String hoachat = rs.getString("SANPHAM_FK");
				float sochatduockiemdinh = rs.getFloat("SOCHATDUOCKIEMDINH");
				float sochatkiemdinh = rs.getFloat("SOCHATKIEMDINH");
				HoaChatKiemDinh obj = new HoaChatKiemDinh();
				obj.setHoaChat(hoachat);
				obj.setSoChatDuocKiemDinh(sochatduockiemdinh);
				obj.setSoChatKiemDinh(sochatkiemdinh);
				
				int check = CheckTonTaiTrongList(list,hoachat);
				if(check >=0 ){
					HoaChatKiemDinh temp = list.get(check);
					
				} else {
					list.add(obj);
				}
				dem++;
				if(dem == sodong){
					break;
				}
			}
			rs.close();
		}
		} catch( Exception ex){
			ex.printStackTrace();
		} finally{
			db.shutDown();
		}
		return false;
	}
	private int  CheckTonTaiTrongList(List<HoaChatKiemDinh> list, String hoachat){
		for(int i=0; i< list.size(); i++){
			if(hoachat.equals(list.get(i))){
				return i;
			}
		}
		return -1;
	}
	
	private String PhanBoGiaTri(dbutils db, String nhId, String ngaychot, String thang, String nam) 
	{
		Utility util = new Utility();
		
		String query = "select CHIPHIKHAC, isnull( (select THUENK from  ERP_THUENHAPKHAU where DONMUAHANG_FK = mh.PK_SEQ ), 0) as THUENK  " +
					   "from ERP_MUAHANG mh  " +
					   "where PK_SEQ = ( select muahang_fk from ERP_NHANHANG where PK_SEQ = '" + nhId + "' )";
		ResultSet rsDATA = db.get(query);
		if(rsDATA != null)
		{
			try 
			{
				while(rsDATA.next())
				{
					double chiphiKHAC = Double.parseDouble(rsDATA.getString("CHIPHIKHAC"));
					double thueNK = Double.parseDouble(rsDATA.getString("THUENK"));
					String taikhoanNO_SoHieu = "";
					String taikhoanCO_SoHieu = "";
					
					//1.Phân bổ chi phí khác
					if(chiphiKHAC > 0)
					{
						query = " INSERT INTO ERP_NHANHANG_PHANBO_CHIPHIKHAC(NHANHANG_FK, SANPHAM_FK, TAISAN_FK, TIENHANG, TIENTHUE, TIENHANG_GOC, TIENTHUE_GOC, PHANTRAM)  " +
								" select hoadonDETAIL.ID, SANPHAM_FK, TAISAN_FK, ABS(SOTIEN * 100 *  " + chiphiKHAC + "  / TONGTIEN) as tienhang_phanbo, 0, SOTIEN as TIENHANG_GOC, 0,  " +
								" 		ABS(SOTIEN * 100 / TONGTIEN) as PHANTRAM  " +
								" from " +
								" ( " +
								" 	select NH.PK_SEQ as ID, NHSP.SANPHAM_FK, NHSP.TAISAN_FK, SOLUONGNHAN * DONGIAVIET as sotien " +
								" 	FROM ERP_NHANHANG NH INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NHSP.NHANHANG_FK = NH.PK_SEQ    " +
								" 	where NHSP.NHANHANG_FK = '" + nhId + "'  " +
								" )  " +
								" hoadonDETAIL inner join  " +
								" (  " +
								" 	select NH.NHANHANG_FK as ID, SUM( SOLUONGNHAN * DONGIAVIET ) as TONGTIEN  " +
								" 	FROM ERP_NHANHANG_SANPHAM NH  " +
								" 	where NH.NHANHANG_FK = '" + nhId + "' " +
								" 	group by NH.NHANHANG_FK " +
								" )  " +
								" hoadonTOTAL on hoadonDETAIL.ID = hoadonTOTAL.ID ";
						
						if(!db.update(query))
						{
							rsDATA.close();
							return "1111.Khong the cap nhat ERP_NHANHANG_PHANBO_CHIPHIKHAC: " + query;
						}
						
						//GHI NHAN BOOKTOAN PHAN BO
            			query = " select a.NGAYCHOT as ngayghinhan, a.pk_seq,  a.PK_SEQ, c.NHACUNGCAP_FK as ncc_fk, b.tienhang,   " +
		            			"  	( select PK_SEQ from ERP_TAIKHOANKT where sohieutaikhoan = '15100000' )  as TAIKHOANKT_NCC,  " +
		            			"  	case when d.PK_SEQ IS NULL then e.pk_seq else d.pk_seq end as doituong,  " +
		            			"  	case when d.PK_SEQ IS NULL then N'Tài sản' else N'Sản phẩm' end as loai,  " +
		            			"  	case when d.PK_SEQ IS NULL then ( select taikhoan_fk from Erp_LOAITAISAN where pk_seq = e.pk_seq  )   " +
		            			"  		 else ( select TAIKHOANKT_FK from ERP_LOAISANPHAM where pk_seq = d.LOAISANPHAM_FK  ) end as TAIKHOAN_DUOI_SP_TS  " +
		            			"  from ERP_NHANHANG a inner join ERP_NHANHANG_PHANBO_CHIPHIKHAC b on a.pk_seq = b.nhanhang_fk  " +
		            			" 	    inner join ERP_MUAHANG c on a.MUAHANG_FK = c.PK_SEQ " +
		            			"  		left join ERP_SANPHAM d on b.sanpham_fk = d.PK_SEQ  " +
		            			"  		left join ERP_TAISANCODINH e on b.taisan_fk = e.pk_seq  " +
		            			"  where a.pk_seq = '" + nhId + "' " ;
            			
            			System.out.println("2.LAY TIEN CHI PHI KHAC PHAN BO: " + query);
						ResultSet rsTaikhoan = db.get(query);
						if(rsTaikhoan != null)
						{
							String doituong_no = "";
							String madoituong_no = "";
							String doituong_co = "";
							String madoituong_co = "";
							
							while(rsTaikhoan.next())
							{
								doituong_no = rsTaikhoan.getString("loai");
								madoituong_no = rsTaikhoan.getString("doituong");
								
								doituong_co = "";
								madoituong_co = "";
								
								taikhoanNO_SoHieu = rsTaikhoan.getString("TAIKHOAN_DUOI_SP_TS");
								taikhoanCO_SoHieu = rsTaikhoan.getString("TAIKHOANKT_NCC");

								double tienGHINHAN = rsTaikhoan.getDouble("tienhang");
								
								if(taikhoanNO_SoHieu.trim().length() <= 0 || taikhoanCO_SoHieu.trim().length() <= 0 )
								{
									String msg = "1115.Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
									return msg;
								}
								
								String msg = util.Update_TaiKhoan( db, thang, nam, ngaychot, ngaychot, "Phân bổ chi phí khác", nhId, taikhoanNO_SoHieu, taikhoanCO_SoHieu, "", 
											Double.toString(tienGHINHAN), Double.toString(tienGHINHAN), doituong_no, madoituong_no, doituong_co, madoituong_co, "0", "0", "0", "100000", "0", "1", Double.toString(tienGHINHAN), Double.toString(tienGHINHAN), "Phân bổ chi phí khác" );
								if(msg.trim().length() > 0)
								{
									rsTaikhoan.close();
									return msg;
								}
							}
							rsTaikhoan.close();
						}
					}
					
					//2.Phân bổ thuế nhập khẩu
					if(thueNK > 0)
					{
						query = " INSERT INTO ERP_NHANHANG_PHANBO_THUENHAPKHAU(NHANHANG_FK, SANPHAM_FK, TAISAN_FK, TIENHANG, TIENTHUE, TIENHANG_GOC, TIENTHUE_GOC, PHANTRAM)  " +
								" select hoadonDETAIL.ID, SANPHAM_FK, TAISAN_FK, ABS(SOTIEN * 100 *  " + thueNK + "  / TONGTIEN) as tienhang_phanbo, 0, SOTIEN as TIENHANG_GOC, 0,  " +
								" 		ABS(SOTIEN * 100 / TONGTIEN) as PHANTRAM  " +
								" from " +
								" ( " +
								" 	select NH.PK_SEQ as ID, NHSP.SANPHAM_FK, NHSP.TAISAN_FK, SOLUONGNHAN * DONGIAVIET as sotien " +
								" 	FROM ERP_NHANHANG NH INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NHSP.NHANHANG_FK = NH.PK_SEQ    " +
								" 	where NHSP.NHANHANG_FK = '" + nhId + "'  " +
								" )  " +
								" hoadonDETAIL inner join  " +
								" (  " +
								" 	select NH.NHANHANG_FK as ID, SUM( SOLUONGNHAN * DONGIAVIET ) as TONGTIEN  " +
								" 	FROM ERP_NHANHANG_SANPHAM NH  " +
								" 	where NH.NHANHANG_FK = '" + nhId + "' " +
								" 	group by NH.NHANHANG_FK " +
								" )  " +
								" hoadonTOTAL on hoadonDETAIL.ID = hoadonTOTAL.ID ";
						
						if(!db.update(query))
						{
							rsDATA.close();
							return "1111.Khong the cap nhat ERP_NHANHANG_PHANBO_CHIPHIKHAC: " + query;
						}
						
						//GHI NHAN BOOKTOAN PHAN BO
            			query = " select a.NGAYCHOT as ngayghinhan, a.pk_seq,  a.PK_SEQ, c.NHACUNGCAP_FK as ncc_fk, b.tienhang,   " +
		            			"  	( select PK_SEQ from ERP_TAIKHOANKT where sohieutaikhoan = '15100000' )  as TAIKHOANKT_NCC,  " +
		            			"  	case when d.PK_SEQ IS NULL then e.pk_seq else d.pk_seq end as doituong,  " +
		            			"  	case when d.PK_SEQ IS NULL then N'Tài sản' else N'Sản phẩm' end as loai,  " +
		            			"  	case when d.PK_SEQ IS NULL then ( select taikhoan_fk from Erp_LOAITAISAN where pk_seq = e.pk_seq  )   " +
		            			"  		 else ( select TAIKHOANKT_FK from ERP_LOAISANPHAM where pk_seq = d.LOAISANPHAM_FK  ) end as TAIKHOAN_DUOI_SP_TS  " +
		            			"  from ERP_NHANHANG a inner join ERP_NHANHANG_PHANBO_THUENHAPKHAU b on a.pk_seq = b.nhanhang_fk  " +
		            			" 	    inner join ERP_MUAHANG c on a.MUAHANG_FK = c.PK_SEQ " +
		            			"  		left join ERP_SANPHAM d on b.sanpham_fk = d.PK_SEQ  " +
		            			"  		left join ERP_TAISANCODINH e on b.taisan_fk = e.pk_seq  " +
		            			"  where a.pk_seq = '" + nhId + "' " ;
            			
            			System.out.println("2.LAY TIEN CHI PHI KHAC PHAN BO: " + query);
						ResultSet rsTaikhoan = db.get(query);
						if(rsTaikhoan != null)
						{
							String doituong_no = "";
							String madoituong_no = "";
							String doituong_co = "";
							String madoituong_co = "";
							
							while(rsTaikhoan.next())
							{
								doituong_no = rsTaikhoan.getString("loai");
								madoituong_no = rsTaikhoan.getString("doituong");
								
								doituong_co = "";
								madoituong_co = "";
								
								taikhoanNO_SoHieu = rsTaikhoan.getString("TAIKHOAN_DUOI_SP_TS");
								taikhoanCO_SoHieu = rsTaikhoan.getString("TAIKHOANKT_NCC");

								double tienGHINHAN = rsTaikhoan.getDouble("tienhang");
								
								if(taikhoanNO_SoHieu.trim().length() <= 0 || taikhoanCO_SoHieu.trim().length() <= 0 )
								{
									String msg = "1116.Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
									return msg;
								}
								
								String msg = util.Update_TaiKhoan( db, thang, nam, ngaychot, ngaychot, "Phân bổ thuế nhập khẩu", nhId, taikhoanNO_SoHieu, taikhoanCO_SoHieu, "", 
											Double.toString(tienGHINHAN), Double.toString(tienGHINHAN), doituong_no, madoituong_no, doituong_co, madoituong_co, "0", "0", "0", "100000", "0", "1", Double.toString(tienGHINHAN), Double.toString(tienGHINHAN), "Phân bổ thuế nhập khẩu" );
								if(msg.trim().length() > 0)
								{
									rsTaikhoan.close();
									return msg;
								}
							}
							rsTaikhoan.close();
						}
						
					}
					
				}
				rsDATA.close();
			} 
			catch (Exception e) { }
		}

		return "";
	}

	private boolean UpdatePhanBo(dbutils db, String ttchiphi_fk, String ctyId, double tonggiatri, String thang, String nam) 
	{
		/*String query =  "select TTCPNHAN_FK, PHANTRAM * ( " + tonggiatri + " ) / 100 as PhanBo from ERP_TRUNGTAMCHIPHI_PHANBO " +
						"where TTCPCHO_FK = '" + ttchiphi_fk + "' ";*/
		
		/*String query = " select TTCPNHAN_FK, case PHANTRAM when 1002 then '" + tonggiatri + "' * ( (	select SUM(THANHTIEN) as totalNhom  " +
													"from ERP_LAPNGANSACH_DUBAOSANPHAM  " +
													"where DUBAO_FK in ( select PK_SEQ from ERP_LAPNGANSACH_DUBAO where THANG = '" + thang + "' and NAM = '" + nam + "' and LAPNGANSACH_FK = ( select PK_SEQ from ERP_LAPNGANSACH where NAM = '" + nam + "' and HIEULUC = '1' ) and DVKD_FK = '100000' ) )  " +
													" / " +
												"(	select SUM(THANHTIEN) as total  " +
													"from ERP_LAPNGANSACH_DUBAOSANPHAM  " +
													"where DUBAO_FK in ( select PK_SEQ from ERP_LAPNGANSACH_DUBAO where THANG = '" + thang + "' and NAM = '" + nam + "' and LAPNGANSACH_FK = ( select PK_SEQ from ERP_LAPNGANSACH where NAM = '" + nam + "' and HIEULUC = '1' ) ) ) 	 )  " +
											"when 1003 then '" + tonggiatri + "' * ( (	select SUM(THANHTIEN) as totalLoi  " +
													"from ERP_LAPNGANSACH_DUBAOSANPHAM  " +
													"where DUBAO_FK in ( select PK_SEQ from ERP_LAPNGANSACH_DUBAO where THANG = '" + thang + "' and NAM = '" + nam + "' and LAPNGANSACH_FK = ( select PK_SEQ from ERP_LAPNGANSACH where NAM = '" + nam + "' and HIEULUC = '1' ) and DVKD_FK = '100004' ) )  " +
													" / " +
												"(	select SUM(THANHTIEN) as total  " +
													"from ERP_LAPNGANSACH_DUBAOSANPHAM  " +
													"where DUBAO_FK in ( select PK_SEQ from ERP_LAPNGANSACH_DUBAO where THANG = '" + thang + "' and NAM = '" + nam + "' and LAPNGANSACH_FK = ( select PK_SEQ from ERP_LAPNGANSACH where NAM = '" + nam + "' and HIEULUC = '1' ) ) ) 	 )  " +
											"else PHANTRAM * '" + tonggiatri + "' / 100 end as PhanBo   " +
						"from ERP_TRUNGTAMCHIPHI_PHANBO  " +
						"where TTCPCHO_FK = '" + ttchiphi_fk + "' ";*/
		
		String query = " select TTCPNHAN_FK, PHANTRAM,  case PHANTRAM when 1002 then " + tonggiatri + " * ( (	SELECT SUM(ISNULL(LNS_DBSP.DUKIENBAN,0) * ISNULL(BG_SP.GIABAN,0)) AS NUM " +
		"														FROM ERP_LAPNGANSACH_DUBAO LNS_DB " +
		"															INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM LNS_DBSP ON LNS_DBSP.DUBAO_FK = LNS_DB.PK_SEQ  " +
		"															INNER JOIN SANPHAM SP ON SP.PK_SEQ = LNS_DBSP.SANPHAM_FK AND SP.DVKD_FK = LNS_DB.DVKD_FK  " +
		"															LEFT JOIN ERP_LNSBANGGIABAN_KH BG_KH ON BG_KH.KH_FK =  LNS_DBSP.KHACHHANG_FK  " +
		"															LEFT JOIN ERP_LNSBANGGIABAN BG ON BG.PK_SEQ = BG_KH.BANGGIABAN_FK  " +
		"															LEFT JOIN ERP_LNSBGBAN_SANPHAM BG_SP ON BG_SP.BGBAN_FK = BG.PK_SEQ AND BG_SP.SANPHAM_FK = LNS_DBSP.SANPHAM_FK AND BG_SP.TRANGTHAI = 1  " +
		"														WHERE	LNS_DB.LAPNGANSACH_FK = ( select PK_SEQ from ERP_LAPNGANSACH where NAM = '" + nam + "' and HIEULUC = '1' ) AND LNS_DB.CONGTY_FK = '" + ctyId + "' AND SP.LOAISANPHAM_FK = '100005' AND LNS_DBSP.THANG = '" + thang + "' and LNS_DBSP.NAM = '" + nam + "' AND SP.DVKD_FK = '100000' " +
		"													)  " +
		"												/  " +
		"													(	 SELECT SUM(ISNULL(LNS_DBSP.DUKIENBAN,0) * ISNULL(BG_SP.GIABAN,0)) AS NUM  " +
		"														FROM ERP_LAPNGANSACH_DUBAO LNS_DB  " +
		"															INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM LNS_DBSP ON LNS_DBSP.DUBAO_FK = LNS_DB.PK_SEQ  " +
		"															INNER JOIN SANPHAM SP ON SP.PK_SEQ = LNS_DBSP.SANPHAM_FK AND SP.DVKD_FK = LNS_DB.DVKD_FK  " +
		"															LEFT JOIN ERP_LNSBANGGIABAN_KH BG_KH ON BG_KH.KH_FK =  LNS_DBSP.KHACHHANG_FK  " +
		"															LEFT JOIN ERP_LNSBANGGIABAN BG ON BG.PK_SEQ = BG_KH.BANGGIABAN_FK  " +
		"															LEFT JOIN ERP_LNSBGBAN_SANPHAM BG_SP ON BG_SP.BGBAN_FK = BG.PK_SEQ AND BG_SP.SANPHAM_FK = LNS_DBSP.SANPHAM_FK AND BG_SP.TRANGTHAI = 1  " +
		"														WHERE	LNS_DB.LAPNGANSACH_FK = ( select PK_SEQ from ERP_LAPNGANSACH where NAM = '" + nam + "' and HIEULUC = '1' ) AND LNS_DB.CONGTY_FK = '" + ctyId + "' AND SP.LOAISANPHAM_FK = '100005' AND LNS_DBSP.THANG = '" + thang + "' and LNS_DBSP.NAM = '" + nam + "' " +
			"													) )  " +
		"		when 1003 then " + tonggiatri + " * ( (	SELECT SUM(ISNULL(LNS_DBSP.DUKIENBAN,0) * ISNULL(BG_SP.GIABAN,0)) AS NUM  " +
		"														FROM ERP_LAPNGANSACH_DUBAO LNS_DB  " +
		"															INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM LNS_DBSP ON LNS_DBSP.DUBAO_FK = LNS_DB.PK_SEQ  " +
		"															INNER JOIN SANPHAM SP ON SP.PK_SEQ = LNS_DBSP.SANPHAM_FK AND SP.DVKD_FK = LNS_DB.DVKD_FK " +
		"															LEFT JOIN ERP_LNSBANGGIABAN_KH BG_KH ON BG_KH.KH_FK =  LNS_DBSP.KHACHHANG_FK  " +
		"															LEFT JOIN ERP_LNSBANGGIABAN BG ON BG.PK_SEQ = BG_KH.BANGGIABAN_FK  " +
		"															LEFT JOIN ERP_LNSBGBAN_SANPHAM BG_SP ON BG_SP.BGBAN_FK = BG.PK_SEQ AND BG_SP.SANPHAM_FK = LNS_DBSP.SANPHAM_FK AND BG_SP.TRANGTHAI = 1 " +
		"														WHERE	LNS_DB.LAPNGANSACH_FK = ( select PK_SEQ from ERP_LAPNGANSACH where NAM = '" + nam + "' and HIEULUC = '1' ) AND LNS_DB.CONGTY_FK = '" + ctyId + "' AND SP.LOAISANPHAM_FK = '100005' AND LNS_DBSP.THANG = '" + thang + "' and LNS_DBSP.NAM = '" + nam + "' AND SP.DVKD_FK = '100004' " +
		"													)   " +
		"												/  " +
		"													(	 SELECT SUM(ISNULL(LNS_DBSP.DUKIENBAN,0) * ISNULL(BG_SP.GIABAN,0)) AS NUM  " +
		"														FROM ERP_LAPNGANSACH_DUBAO LNS_DB  " +
		"															INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM LNS_DBSP ON LNS_DBSP.DUBAO_FK = LNS_DB.PK_SEQ  " +
		"															INNER JOIN SANPHAM SP ON SP.PK_SEQ = LNS_DBSP.SANPHAM_FK AND SP.DVKD_FK = LNS_DB.DVKD_FK  " +
		"															LEFT JOIN ERP_LNSBANGGIABAN_KH BG_KH ON BG_KH.KH_FK =  LNS_DBSP.KHACHHANG_FK  " +
		"															LEFT JOIN ERP_LNSBANGGIABAN BG ON BG.PK_SEQ = BG_KH.BANGGIABAN_FK  " +
		"															LEFT JOIN ERP_LNSBGBAN_SANPHAM BG_SP ON BG_SP.BGBAN_FK = BG.PK_SEQ AND BG_SP.SANPHAM_FK = LNS_DBSP.SANPHAM_FK AND BG_SP.TRANGTHAI = 1 " +
		"														WHERE	LNS_DB.LAPNGANSACH_FK = ( select PK_SEQ from ERP_LAPNGANSACH where NAM = '" + nam + "' and HIEULUC = '1' ) AND LNS_DB.CONGTY_FK = '" + ctyId + "' AND SP.LOAISANPHAM_FK = '100005' AND LNS_DBSP.THANG = '" + thang + "' and LNS_DBSP.NAM = '" + nam + "' " +
			"													) )  " +
		"		  else PHANTRAM * " + tonggiatri + " / 100 end as PhanBo    " +
		"	from ERP_TRUNGTAMCHIPHI_PHANBO  " +
		"	where TTCPCHO_FK = '" + ttchiphi_fk + "'  ";

		System.out.println("9.Lay chi phi: " + query);
		
		ResultSet rsPhanbo = db.get(query);
		if(rsPhanbo != null)
		{
			try
			{
				while(rsPhanbo.next())
				{
					String ttcpNhan_fk = rsPhanbo.getString("TTCPNHAN_FK");
					long tongphanbo = Math.round(rsPhanbo.getDouble("PhanBo"));
					
					if(tongphanbo > 0)
					{
						Hashtable<String, Long> ttcp_from_dacho = null;
						
						if(nhan_form_choSelected.get(ttcpNhan_fk) == null )
							ttcp_from_dacho = new Hashtable<String, Long>();
						else
							ttcp_from_dacho = nhan_form_choSelected.get(ttcpNhan_fk);
						
						nhan_form_choSelected.put(ttcpNhan_fk, ttcp_from_dacho);
						
						long giatriNhanDuoc = tongphanbo  - ( ttcp_from_dacho.get(ttcpNhan_fk) == null ? 0 : ttcp_from_dacho.get(ttcpNhan_fk) ) ;
						
						//Neu chua co thi INSERT
						query = " select count(*) as sodong from ERP_TRUNGTAMCHIPHI_THUCCHI " +
								" where TTCP_FK = '" + ttcpNhan_fk + "' and thang = '" + thang + "' and nam = '" + nam + "'  ";
						ResultSet rsCheck = db.get(query);
						if(rsCheck.next())
						{
							if(rsCheck.getInt("sodong") <= 0 )
							{
								query = "Insert ERP_TRUNGTAMCHIPHI_THUCCHI(TTCP_FK, Thang, Nam, ThucChi) " +
										"values('" + ttcpNhan_fk + "', '" + thang + "', '" + nam + "', '" + giatriNhanDuoc + "') ";
							}
							else
							{
								query = "update ERP_TRUNGTAMCHIPHI_THUCCHI set THUCCHI = THUCCHI +  " + giatriNhanDuoc + "  " +
										" where TTCP_FK = '" + ttcpNhan_fk + "' and thang = '" + thang + "' and nam = '" + nam + "'  ";
							}
							
							ttcp_from_dacho.put( ttchiphi_fk, ( ttcp_from_dacho.get(ttchiphi_fk) == null ? 0 : ttcp_from_dacho.get(ttchiphi_fk) ) + Math.round(tongphanbo) );
							
							if(!db.update(query))
							{
								rsPhanbo.close();
								
								System.out.println("1.Loi: " + query);
								return false;
							}
							
						}
						rsCheck.close();
						
						
						//Tu trung tam nhan nay, xet truong hop trung tam nhan co trung tam dc phan bo
						//////////Phai them cho kiem tra phan bo, ----> coi chung lap vo han
						if(!UpdatePhanBo(db, ttcpNhan_fk, ctyId, giatriNhanDuoc, thang, nam))
						{
							rsPhanbo.close();
							
							System.out.println("2222.Khong the cap nhat thuc chi, vui long kiem tra lai: " + query);
							return false;
						}
						
					}
					
				}
				rsPhanbo.close();
			}
			catch (Exception e) 
			{
				System.out.println("9.Exception phan bo: " + e.getMessage());
				return false;
			}
		}
		
		return true;
	}
	
	/*private boolean UpdatePhanBo(dbutils db, String nhId, String chiphi_fk, String ttchiphi_fk, double tonggiatri, String thang, String nam) 
	{
		String query =  "select TTCPNHAN_FK, PHANTRAM * ( " + tonggiatri + " ) / 100 as PhanBo from ERP_TRUNGTAMCHIPHI_PHANBO " +
						"where TTCPCHO_FK = '" + ttchiphi_fk + "'";

		ResultSet rsPhanbo = db.get(query);
		if(rsPhanbo != null)
		{
			try
			{
				while(rsPhanbo.next())
				{
					String ttcpNhan_fk = rsPhanbo.getString("TTCPNHAN_FK");
					double tongphanbo = rsPhanbo.getDouble("PhanBo");
					
					if(tongphanbo > 0)
					{
						query = "update ERP_TRUNGTAMCHIPHI_NGANSACH set NGANSACH = NGANSACH + ( " + tongphanbo + " ) " +
								" where TTCP_FK = '" + ttcpNhan_fk + "' and thang = '" + thang + "' and nam = '" + nam + "'";
						System.out.println("Cap nhat TTCP: " + query);
						
						if(!db.update(query))
						{
							rsPhanbo.close();
							db.getConnection().rollback();
							
							System.out.println("1.Loi: " + query);
							return false;
						}
						
						//Tu trung tam nhan nay, xet truong hop trung tam nhan co trung tam dc phan bo
						//////////Phai them cho kiem tra phan bo, ----> coi chung lap vo han
						if(!UpdatePhanBo(db, nhId, chiphi_fk, ttcpNhan_fk, tongphanbo, thang, nam))
						{
							rsPhanbo.close();
							db.getConnection().rollback();
							
							System.out.println("2222.Khong the cap nhat ngan sach, vui long kiem tra lai: " + query);
							return false;
						}
						
					}
					
				}
				rsPhanbo.close();
			}
			catch (Exception e) 
			{
				System.out.println("9.Exception phan bo: " + e.getMessage());
				return false;
			}
		}
		
		return true;
	}*/

	
	private String chotNhanHang_SanPham(String nhId, String userId, String ctyId, dbutils db, String loaimh)
	{
		String msg = "";
		Utility util=new Utility(); 
		Utility_Kho util_kho=new Utility_Kho();
		
		//Check khoa so thang
		//String sql = "select ngaychot from erp_nhanhang where pk_seq = '" + nhId + "'";
		String sql="  SELECT A.KHACHHANGKYGUI_FK, ISNULL(A.ISTUDONG,0) AS ISTUDONG, '100000' as NHOMKENH_FK, A.NHAPHANPHOI_FK, A.NGAYNHAN AS NGAYCHUNGTU, A.NGAYCHOT, C.LOAINHACUNGCAP_FK, KHONL_NHO_GC, D.NCC_FK,   " +  
				   "  ISNULL(B.NGUONGOCHH, 'TN') AS NGUONGOC, B.LOAI, (select pk_seq from KHO where LOAI = 7) KHONCC, (select pk_seq from KHO where LOAI = 2) KHOKYGUINCC,	 " + 
				   "  A.MUAHANG_FK "+
				   "  FROM ERP_NHANHANG A  " +  
				   "  left JOIN ERP_MUAHANG B ON A.MUAHANG_FK = B.PK_SEQ   " +  
				   "  left JOIN ERP_HOADONNCC E ON A.HDNCC_FK = E.PK_SEQ   " +
				   "  left JOIN ERP_PARK D ON E.PARK_FK = D.PK_SEQ  " +  
				   "  left JOIN ERP_NHACUNGCAP C ON B.NHACUNGCAP_FK = C.PK_SEQ  " +   
				   "  WHERE A.PK_SEQ ="+nhId;
		if(loaimh.trim().equals("0")) //NHẬP KHẨU
		{
			sql =  "  SELECT A.KHACHHANGKYGUI_FK, ISNULL(A.ISTUDONG,0) AS ISTUDONG, '100000' as NHOMKENH_FK, A.NHAPHANPHOI_FK, A.NGAYNHAN AS NGAYCHUNGTU, A.NGAYCHOT, C.LOAINHACUNGCAP_FK, KHONL_NHO_GC, D.NCC_FK,   " +  
				   "  ISNULL(B.NGUONGOCHH, 'TN') AS NGUONGOC, B.LOAI, (select pk_seq from KHO where LOAI = 7) KHONCC, (select pk_seq from KHO where LOAI = 2) KHOKYGUINCC,	 " + 
				   "  A.MUAHANG_FK "+
				   "  FROM ERP_NHANHANG A  " +
				   "  left JOIN ERP_MUAHANG B ON A.MUAHANG_FK = B.PK_SEQ   " +
				   "  left JOIN ERP_HOADONNCC E ON A.HDNCC_FK = E.PK_SEQ   " +
				   "  left JOIN ERP_PARK D ON E.PARK_FK = D.PK_SEQ  " +  
				   "  left JOIN ERP_NHACUNGCAP C ON B.NHACUNGCAP_FK = C.PK_SEQ  " + 
				   "  WHERE A.PK_SEQ ="+nhId;
		}
		System.out.println("Câu lấy TT "+sql);
		String ngaychotNV = "";
		String ngaychungtu = "";
		String nccId = "";
		int istudong = 0;
		String khoNCC = "";
		String khokyguiNCC = "";
		String NHAPHANPHOI_FK="";
		String NHOMKENH_FK="";
		String muahang_fk= "";
		String khachhang_fk = ""; // Kho nhận ký gửi KH
		boolean isSLconlai = false;
		
		 String khoxuat = "NULL";
		 String khonhan = "NULL";
		 
		 String nccXuatId= "NULL";
		 String nccNhanId= "NULL";
		 String khXuatId= "NULL";
			 String khNhanId= "NULL";
		
		ResultSet rs = db.get(sql);
		try 
		{
			if(rs.next())
			{
				khachhang_fk= rs.getString("KHACHHANGKYGUI_FK")== null?"":rs.getString("KHACHHANGKYGUI_FK");
				muahang_fk= rs.getString("MUAHANG_FK");
				ngaychotNV = rs.getString("ngaychot");
				ngaychungtu = rs.getString("ngaychungtu");
				nccId = rs.getString("NCC_FK") == null ? "" : rs.getString("NCC_FK");
				khoNCC = rs.getString("KHONCC");
				khokyguiNCC = rs.getString("KHOKYGUINCC");
				NHAPHANPHOI_FK= rs.getString("NHAPHANPHOI_FK");
				NHOMKENH_FK= rs.getString("NHOMKENH_FK");
				istudong = rs.getInt("ISTUDONG");
				
				rs.close();
			}else{
				return "10.Vui lòng kiểm tra ngày chốt của nhận hàng : " +sql;
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return "10.Vui lòng kiểm tra ngày chốt của nhận hàng " + e.getMessage();
		}
		
		//String nam = ngaychotNV.substring(0, 4);
		//String thang = ngaychotNV.substring(5, 7);
 
		
  		String query =  " SELECT isnull( B.MUAHANG_FK ,0 ) AS MUAHANG_FK , isnull( nh.loaihanghoa_fk,'1') as loaihanghoa_fk    " +
  						"      , c.ma as masp , SANPHAM_FK, b.KHONHAN, DONGIA, DONGIAVIET, b.TIENTE_FK, b.ngaynhandukien, b.SOLUONGDAT, b.SOLUONGNHAN, " +
		  				"      isnull((select SUM(ab.SOLUONGNHAN)       \n"+
		 			    "               from ERP_NHANHANG a inner join ERP_NHANHANG_SANPHAM ab on a.PK_SEQ=ab.NHANHANG_FK \n"+
		 			    "               where a.PK_SEQ != "+ nhId +" and a.hdNCC_fk = nh.hdNCC_fk and a.TRANGTHAI not in (3,4)  and b.SANPHAM_FK=ab.sanpham_fk  \n"+
		 			    "      ),0) SOLUONGDANHAN, "+
						"      c.ten  as spTen, ISNULL(c.MA, c.ma) as masanpham , ( select loai from KHO where PK_SEQ = b.KHONHAN) loaikho "+
						" FROM ERP_NHANHANG nh inner join ERP_NHANHANG_SANPHAM b on nh.PK_SEQ = b.NHANHANG_FK  " +
						"      inner join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ " +
						" WHERE b.NHANHANG_FK = '" + nhId + "'  ";
	  		
	  	System.out.println("[ErpNhanhang_GiaySvl] INIT NHAN HANG: " + query );
	  	rs = db.get(query);
	
		try 
		{
			while(rs.next())
			{
				String masanpham = rs.getString("SANPHAM_FK");
				String khonhap = rs.getString("KHONHAN");
				String loaikho = rs.getString("loaikho");
				double dongia = rs.getDouble("DONGIA");
				String masp=rs.getString("masp");
				String ngaynhandukien=rs.getString("ngaynhandukien");
				muahang_fk = rs.getString("muahang_fk");
				if(rs.getDouble("SOLUONGDAT")- rs.getDouble("SOLUONGDANHAN") - rs.getDouble("SOLUONGNHAN") > 0)
				{
					isSLconlai = true;
				}
												
				// Lấy số lô luc nhap kho NCC
				String solo_NK = "";
				String ngaynhap_NK = "";
				String ngaysx = "";
				String ngayhh = "";
					
				query=" update ERP_NHANHANG_SANPHAM set danhan='1' where SANPHAM_FK = '" + masanpham + "'  and nhanhang_fk="+nhId+" and khonhan="+khonhap+" and ngaynhandukien='"+ngaynhandukien+"'";
				if(!db.update(query))
				{
					rs.close();
					return "14.Không thể cập nhật ERP_NHANHANG_SANPHAM: " + query;
				}
				
					// Cập nhật kho
					
					query =" select a.muahang_fk, a.KHONHAN ,ISNULL(b.GIATHEOLO,0) DONGIA ,b.NGAYSANXUAT,B.NGAYHETHAN,B.SOLO,B.KHU_FK,B.SOLUONG "+
					 	   " from ERP_NHANHANG_SANPHAM a inner join ERP_NHANHANG_SP_CHITIET b on a.NHANHANG_FK = b.NHANHANG_FK  " +
					 	   " and a.SANPHAM_FK = b.SANPHAM_FK and b.ngaynhandukien=a.ngaynhandukien and a.muahang_fk = b.muahang_fk " +
					 	   " inner join ERP_NHANHANG c on a.NHANHANG_FK = c.PK_SEQ "+
					 	   " where  b.NHANHANG_FK = '" + nhId + "' and a.muahang_fk = "+muahang_fk+" " +
					 	   " and b.SANPHAM_FK = '" +masanpham+ "' and a.ngaynhandukien='"+ngaynhandukien+"'";					
					System.out.println("Câu lấy CT "+query);
					ResultSet rsKho=db.get(query);
					while (rsKho.next()){
						
						 String ngaysanxuat=rsKho.getString("NGAYSANXUAT");
						 String NGAYHETHAN=rsKho.getString("NGAYHETHAN");
						 dongia=rsKho.getDouble("DONGIA");
						 String solo= rsKho.getString("solo");						
						 muahang_fk = rsKho.getString("muahang_fk");
						 
						 double soluongct=rsKho.getDouble("soluong");
						 double booked=0;
						 double available =rsKho.getDouble("soluong");
						 	
						// NHẬN HÀNG BÌNH THƯỜNG :Mua nhập khẩu, trong nước: tăng kho nhận, giảm kho của nhà cung cấp
						//                        Mua vật tư: tăng kho nhận (Không kiểm định chất lượng)
						//                        Tạo ra 1 phiếu Chuyển kho tự động  
						//                        Tạo ra nhận hàng tự động nếu SLHD - SLNhan > 0
						 // NHẬN HÀNG TỰ ĐỘNG : tắng kho hàng bán, giảm kho ký gửi
						 

						 if(istudong <= 0) // NHẬN HÀNG BÌNH THƯỜNG
						 {
							 
							// 1.Tăng kho nhận
							 if(loaikho.equals("8")) // Kho ký gửi KH
							 {
								 khonhan = khonhap;
								 khNhanId = khachhang_fk; 
										 
								 msg= util_kho.Update_NPP_Kho_Sp(ngaychotNV,"Nhận hàng",db,khonhap, masanpham,NHAPHANPHOI_FK,NHOMKENH_FK, soluongct,booked,available,dongia);
								 if(msg.length() >0){								   
									return msg;
								 }
								 
								 msg= util_kho.Update_NPP_Kho_Sp_Kygui(ngaychotNV,"Nhận hàng",db,khonhap, masanpham, NHAPHANPHOI_FK, NHOMKENH_FK, soluongct, booked, available, dongia, khachhang_fk);
								 if(msg.length() >0){									   
										return msg;
								 }
								 
								 msg= util_kho.Update_NPP_Kho_Sp_Kygui_Chitiet(ngaychotNV,"Nhận hàng",db,khonhap, masanpham, NHAPHANPHOI_FK, NHOMKENH_FK, khachhang_fk, solo, ngaysanxuat, NGAYHETHAN, ngaychungtu, soluongct, booked, available, dongia );
								 if(msg.length() >0){									   
										return msg;
								 }
								 
							 }else{
								 khonhan = khonhap;
								 
								 msg= util_kho.Update_NPP_Kho_Sp(ngaychotNV,"Nhận hàng",db,khonhap, masanpham,NHAPHANPHOI_FK,NHOMKENH_FK, soluongct,booked,available,dongia);
								 if(msg.length() >0){
								   
									return msg;
								 }
							 
								 msg= util_kho.Update_NPP_Kho_Sp_Chitiet(ngaychotNV,"Nhận hàng",db,khonhap, masanpham,NHAPHANPHOI_FK,NHOMKENH_FK, solo, ngaysanxuat, NGAYHETHAN, ngaychungtu, soluongct,booked,available,dongia);
								 if(msg.length() >0){
								   
									return msg;
								 }
							 }
							
							 
							 // 2.Giảm kho của nhà cung cấp
							 if(!loaimh.equals("2"))  // PO nhập khẩu && PO trong nước mới dùng nhập kho của nhà cung cấp
							 {								
								 khoxuat = khoNCC;
								 nccXuatId = nccId;
								 
								 msg= util_kho.Update_NPP_Kho_Sp(ngaychotNV,"Nhận hàng",db,khoNCC, masanpham,NHAPHANPHOI_FK,NHOMKENH_FK, soluongct*(-1),booked,available*(-1),dongia);
								 if(msg.length() >0){
								   
									return msg;
								 }
							 
								 msg= util_kho.Update_NPP_Kho_Sp_NCC(ngaychotNV,"Nhận hàng",db,khoNCC, masanpham,NHAPHANPHOI_FK,NHOMKENH_FK, soluongct*(-1),booked,available*(-1),dongia, nccId);
								 if(msg.length() >0){
								   
									return msg;
								 }
								 if(loaimh.equals("1"))
								 {
									 query = "SELECT ct.SOLO, nk.NGAYNHAP, ct.NGAYSANXUAT, ct.NGAYHETHAN "
										   + "FROM ERP_NHAPKHONHAMAY nk inner join ERP_NHAPKHONHAMAY_SP_CHITIET ct on nk.PK_SEQ = ct.NHAMAY_FK "
										   + "WHERE nk.MUAHANG_FK in (select distinct b.MUAHANG_FK from ERP_PHANBOMUAHANG_PO a "
										   + "inner join ERP_PHANBOMUAHANG b on a.PHANBO_FK = b.PK_SEQ where a.PODUOCPB =  "+ muahang_fk +")  "
										   + "and ct.SANPHAM_FK =  "+ masanpham +" and ct.solo = '"+solo+"'";  
								 }
								 else if(loaimh.equals("0"))
								 {
									 query = "SELECT ct.SOLO, nk.NGAYNHAP, ct.NGAYSX as ngaysanxuat, ct.NGAYHETHAN "
										   + "FROM ERP_NHAPKHONHAPKHAU nk inner join ERP_NHAPKHONHAPKHAU_SP_CHITIET ct on nk.PK_SEQ = ct.NHAPKHO_FK "
										   /*+ "inner join ERP_PARK p on nk.PK_SEQ = p.PO_NHAPKHAU "
										   + "inner join ERP_HOADONNCC h on h.park_fk = p.pk_seq "
										   + "inner join ERP_NHANHANG n on h.pk_seq = n.hdNCC_fk "*/
										   + "WHERE nk.pk_seq =  "+ muahang_fk +"  "
										   + "and ct.SANPHAM_FK =  "+ masanpham +" and ct.solo = '"+solo+"' --and n.PK_SEQ = "+nhId;  
								 }
								 System.out.println("---1 Lay so lo, ngay nhap " + query);
								 ResultSet rsSL = db.get(query);					
								 if(rsSL!=null)
								 {
									 while(rsSL.next())
									 {
										 solo_NK = rsSL.getString("SOLO");
										 ngaynhap_NK = rsSL.getString("NGAYNHAP");
										 ngaysx = rsSL.getString("NGAYSANXUAT");
										 ngayhh = rsSL.getString("NGAYHETHAN");
										 System.out.println("solo nha may " + solo_NK + ", soluong " + soluongct + ", kho " + khoNCC);
										 msg= util_kho.Update_NPP_Kho_Sp_Chitiet_NCC(ngaychotNV,"Nhận hàng",db,khoNCC, masanpham,NHAPHANPHOI_FK,NHOMKENH_FK, nccId, solo_NK, ngaysx, ngayhh, ngaynhap_NK, soluongct*(-1),booked,available*(-1),dongia);
										 if(msg.length() >0){
										   
											return msg;
										 }
									 }rsSL.next();
								 }
							 }
						 }
						 else // NHẬN HÀNG TỰ ĐỘNG
						 {
							// 1.Tăng kho nhận
							 if(loaikho.equals("5") || loaikho.equals("8")) // Kho ký gửi KH
							 {
								 khonhan = khonhap;
								 khNhanId = khachhang_fk;
								 
								 msg= util_kho.Update_NPP_Kho_Sp(ngaychotNV,"Nhận hàng",db,khonhap, masanpham,NHAPHANPHOI_FK,NHOMKENH_FK, soluongct,booked,available,dongia);
								 if(msg.length() >0){								   
									return msg;
								 }
								 
								 msg= util_kho.Update_NPP_Kho_Sp_Kygui(ngaychotNV,"Nhận hàng",db,khonhap, masanpham, NHAPHANPHOI_FK, NHOMKENH_FK, soluongct, booked, available, dongia, khachhang_fk);
								 if(msg.length() >0){									   
										return msg;
								 }
								 
								 msg= util_kho.Update_NPP_Kho_Sp_Kygui_Chitiet(ngaychotNV,"Nhận hàng",db,khonhap, masanpham, NHAPHANPHOI_FK, NHOMKENH_FK, khachhang_fk, solo, ngaysanxuat, NGAYHETHAN, ngaychungtu, soluongct, booked, available, dongia );
								 if(msg.length() >0){									   
										return msg;
								 }
								 
							 }else{
								 khonhan = khonhap;
								 
								 msg= util_kho.Update_NPP_Kho_Sp(ngaychotNV,"Nhận hàng",db,khonhap, masanpham,NHAPHANPHOI_FK,NHOMKENH_FK, soluongct,booked,available,dongia);
								 if(msg.length() >0){							   
									return msg;
								 }
							 
								 msg= util_kho.Update_NPP_Kho_Sp_Chitiet(ngaychotNV,"Nhận hàng",db,khonhap, masanpham,NHAPHANPHOI_FK,NHOMKENH_FK, solo, ngaysanxuat, NGAYHETHAN, ngaychungtu, soluongct,booked,available,dongia);
								 if(msg.length() >0){								   
									return msg;
								 }
							 }
							 
							 // 2.Giảm kho ký gửi nhà cung cấp
							 // Tính chi phí lưu kho : gài sau
							 if(!loaimh.equals("2"))  
							 {
								 khoxuat = khokyguiNCC;
								 nccXuatId = nccId;
								 
								 msg= util_kho.Update_NPP_Kho_Sp(ngaychotNV,"Nhận hàng",db,khokyguiNCC, masanpham,NHAPHANPHOI_FK,NHOMKENH_FK, soluongct*(-1),booked,available*(-1),dongia);
								 if(msg.length() >0){
								   
									return msg;
								 }
							 
								 msg= util_kho.Update_NPP_Kho_Sp_NCC(ngaychotNV,"Nhận hàng",db,khokyguiNCC, masanpham,NHAPHANPHOI_FK,NHOMKENH_FK, soluongct*(-1),booked,available*(-1),dongia, nccId);
								 if(msg.length() >0){
								   
									return msg;
								 }
								 
								 if(loaimh.equals("1"))
								 {
									 query = "SELECT ct.SOLO, nk.NGAYNHAP, ct.NGAYSANXUAT, ct.NGAYHETHAN "
										   + "FROM ERP_NHAPKHONHAMAY nk inner join ERP_NHAPKHONHAMAY_SP_CHITIET ct on nk.PK_SEQ = ct.NHAMAY_FK "
										   + "WHERE nk.MUAHANG_FK in (select distinct b.MUAHANG_FK from ERP_PHANBOMUAHANG_PO a "
										   + "inner join ERP_PHANBOMUAHANG b on a.PHANBO_FK = b.PK_SEQ where a.PODUOCPB =  "+ muahang_fk +")  "
										   + "and ct.SANPHAM_FK =  "+ masanpham +" and ct.solo = '"+solo+"'";  
								 }
								 else if(loaimh.equals("0"))
								 {
									 query = "SELECT ct.SOLO, nk.NGAYNHAP, ct.NGAYSX as ngaysanxuat, ct.NGAYHETHAN "
										   + "FROM ERP_NHAPKHONHAPKHAU nk inner join ERP_NHAPKHONHAPKHAU_SP_CHITIET ct on nk.PK_SEQ = ct.NHAPKHO_FK "
										   /*+ "inner join ERP_PARK p on nk.PK_SEQ = p.PO_NHAPKHAU "
										   + "inner join ERP_HOADONNCC h on h.park_fk = p.pk_seq "
										   + "inner join ERP_NHANHANG n on h.pk_seq = n.hdNCC_fk "*/
										   + "WHERE nk.pk_seq =  "+ muahang_fk +"  "
										   + "and ct.SANPHAM_FK =  "+ masanpham +" and ct.solo = '"+solo+"' --and n.PK_SEQ = "+nhId;  
								 }
								 System.out.println("---1 Lay so lo, ngay nhap " + query);
								 ResultSet rsSL = db.get(query);					
								 if(rsSL!=null)
								 {
									 while(rsSL.next())
									 {
										 solo_NK = rsSL.getString("SOLO");
										 ngaynhap_NK = rsSL.getString("NGAYNHAP");
										 ngaysx = rsSL.getString("NGAYSANXUAT");
										 ngayhh = rsSL.getString("NGAYHETHAN");
										 System.out.println("--------solo nha may " + solo_NK);
										 msg= util_kho.Update_NPP_Kho_Sp_Chitiet_NCC(ngaychotNV,"Nhận hàng",db,khokyguiNCC, masanpham,NHAPHANPHOI_FK,NHOMKENH_FK, nccId, solo_NK, ngaysx,ngayhh,ngaychotNV, soluongct*(-1),booked,available*(-1),dongia);
										 if(msg.length() >0){
										   
											return msg;
										 }
									 }rsSL.next();
								 }	
							 }
						 }
						 					
						// Cập nhật lại giá nhập kho về giá nhập
							
						query="UPDATE NHAPP_KHO_CHITIET SET GIAMUA="+dongia+" WHERE KHO_FK = "+khonhap+" and NPP_FK = "+ NHAPHANPHOI_FK +" AND  NHOMKENH_FK = "+ NHOMKENH_FK +" AND SANPHAM_FK ="+masanpham+" AND SOLO = '"+ solo +"' ";
						if(!db.update(query))
						{
							rs.close();
							return "17.KHông thể cập nhật NHAPP_KHO_CHITIET : " + query;
						}
					
					}
										
			}
			rs.close();
			
			// 3. Tạo phiếu chuyển kho tự động
			/*if(istudong <= 0) // NHẬN HÀNG BÌNH THƯỜNG
			{*/
				msg = TaoChuyenKhoTuDong(db, userId, ctyId, NHAPHANPHOI_FK, khoxuat, khonhan, nhId, nccXuatId, nccNhanId, khXuatId, khNhanId, "0", loaimh);
				if(msg.length() >0){								   
					return msg;
				}
			/*}
			else
			{
				msg = TaoChuyenKhoTuDong(db, userId, ctyId, NHAPHANPHOI_FK, khoxuat, khonhan, nhId, nccXuatId, nccNhanId, khXuatId, khNhanId, "1", loaimh);
				if(msg.length() >0){								   
					return msg;
				}
			}*/
			
			// Cập nhật trạng thái Phiếu nhận hàng
			query = "update ERP_NHANHANG set trangthai = '1', ngaychot = '"+ getDateTime() +"' ,giochot = '" + getTime() + "',  ngaysua = '" + getDateTime() + "', nguoisua = '" + userId + "' where pk_seq = '" + nhId + "'";
			if(!db.update(query))
			{
				return "25.Khong the cap nhat ERP_NHANHANG: " + query;
			}
						
			
			// Chuyen trang thai cua mua hang
			if(CheckDahoantat(db,nhId)){
				query="UPDATE  ERP_MUAHANG  SET TRANGTHAI=2  WHERE  PK_SEQ= (SELECT MUAHANG_FK FROM ERP_NHANHANG WHERE PK_SEQ='"+nhId+"' ) ";
				if(!db.update(query))
				{
					return "27. Chưa hoàn tất đơn mua hàng: " + query;
				}
			}			


			 // Nếu số lượng còn lại > 0 >> Tự động tạo 1 phiếu nhận hàng nhận SL còn lại			
			 if(isSLconlai == true)
			 {
				 String msg1 = TaoNhanHangTuDong(db, ctyId, userId, loaimh, nhId, istudong);
				 if(msg1.trim().length() > 0)
				 {
					 return msg1;
				 }
			 }			
			
		}catch (Exception e) 
		{
			e.printStackTrace();
			//db.update("rollback");
			return "Không thể chốt nhận hàng: " + e.getMessage();
		}
			
		return msg;
	}

	private String TaoNhanHangTuDong(dbutils db,String ctyId, String userId, String loaimh, String nhId, int istudong) 
	{
		Utility_Kho util_kho=new Utility_Kho();
		System.out.println("vao day");
		 String msg ="";
		 String query = "";
		 String NHOMKENH_FK = "";
		 String khokyguiNCC = "";
		 String khoNCC = "";
		 String NHAPHANPHOI_FK = "";
		 String nccId = "";
		 String hdNcc = "";
		 
		 String ngayHT = this.getDateTime();
		 String khoxuat = "NULL";
		 String khonhan = "NULL";
		 String nccXuatId = "NULL";
		 String nccNhanId = "NULL";
		 String khXuatId = "NULL";
		 String khNhanId = "NULL";
		 String muahang_fk = "NULL";
		 
		 try
		 {
			 query = "SELECT LOAIHANGHOA_FK, DONVITHUCHIEN_FK, NOIDUNGNHAN_FK, NCC_KH_FK,  NHAPHANPHOI_FK, hdNCC_fk, NHOMKENH_FK, "
				 	   + "       N'Nhận hàng tự động từ phiếu ' + CAST(PK_SEQ as nvarchar(50)) DIENGIAI, NOIDUNGNHAP_FK, NCC_KH_FK, MUAHANG_FK, "
				 	   + "       (select pk_seq from KHO where LOAI = 7) KHONCC, (select pk_seq from KHO where LOAI = 2) KHOKYGUINCC, KHONHAN_FK "
				 	   + "FROM ERP_NHANHANG  "
				 	   + "WHERE PK_SEQ = "+ nhId +" ";
			 ResultSet rsNH = db.get(query);
			 while(rsNH.next())
			 {
				 nccId = rsNH.getString("NCC_KH_FK");
				 khoNCC = rsNH.getString("KHONCC");
				 khokyguiNCC = rsNH.getString("KHOKYGUINCC");
				 NHAPHANPHOI_FK = rsNH.getString("NHAPHANPHOI_FK");
				 NHOMKENH_FK  = rsNH.getString("NHOMKENH_FK");
				 hdNcc =  rsNH.getString("hdNCC_fk");
				 muahang_fk = rsNH.getString("MUAHANG_FK");
				 
				 query = "insert ERP_NHANHANG(KHONHAN_FK, KHOKYGUI_FK, ISTUDONG, NGAYNHAN, NGAYCHOT , MUAHANG_FK, LOAIHANGHOA_FK, NOIDUNGNHAN_FK, DIENGIAI, DONVITHUCHIEN_FK,  NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, TRANGTHAI, CONGTY_FK, NoiDungNhap_fk, NCC_KH_FK, hdNCC_fk, tigia , NHAPHANPHOI_FK) " +
						 "values( "+ rsNH.getString("KHONHAN_FK") +", "+ rsNH.getString("KHOKYGUINCC") +", '1','" +ngayHT +"', '" +ngayHT +"',"+ rsNH.getString("MUAHANG_FK") +", '" + rsNH.getString("LOAIHANGHOA_FK") + "', '" +  rsNH.getString("NOIDUNGNHAN_FK") + "', N'"+ rsNH.getString("DIENGIAI") +"', '" +rsNH.getString("DONVITHUCHIEN_FK") +"',  " + 
						  " '" + this.getDateTime() +"', '" +this.getDateTime() +"', '" + userId + "', '" + userId +"', '0', '" + ctyId + "', " + rsNH.getString("NOIDUNGNHAP_FK") + ", " + rsNH.getString("NCC_KH_FK") + ", " + hdNcc + ", '1', "+
						  " "+ rsNH.getString("NHAPHANPHOI_FK") +" )";
				 System.out.println("100. Câu lấy insert nhận hàng "+query);	
				 if (!db.update(query))
				 {
					 rsNH.close();
					 return "KHông thể tạo Nhận hàng tự động : " + query;
				 }
			 }
			 
			 String nhCurrent = "";
				query = "select IDENT_CURRENT('Erp_NHANHANG') as nhId";
				
				ResultSet rs1=  db.get(query);
				
				if(rs1!=null){
					try{
						while(rs1.next()){
							nhCurrent= rs1.getString("nhId");
						}
					}catch(Exception e){
						e.printStackTrace();
					}	
				}
			
			 query = "SELECT DISTINCT MUAHANG_FK FROM ERP_NHANHANG_SANPHAM WHERE NHANHANG_FK = "+ nhId +"";
			 ResultSet rsMH = db.get(query);
			 while(rsMH.next())
			 {
				 muahang_fk = rsMH.getString("muahang_fk");
				 query = "SELECT DISTINCT sp.SANPHAM_FK, sp.TAISAN_FK, sp.CCDC_FK, sp.CHIPHI_FK, sp.DIENGIAI, sp.NGAYNHANDUKIEN, sp.DUNGSAI,  "
					   + "       sp.SOLUONGDAT, sp.SOLUONGNHAN, sp.DONVI, sp.TIENTE_FK, sp.TYGIAQUYDOI, sp.DONGIA, sp.DONGIAVIET, sp.KHONHAN, sp.MUAHANG_FK, "
					   + "       isnull((select SUM(ab.SOLUONGNHAN)       "
					   + "               from ERP_NHANHANG a inner join ERP_NHANHANG_SANPHAM ab on a.PK_SEQ=ab.NHANHANG_FK  "
		               + "               where a.PK_SEQ != "+ nhId +" and a.TRANGTHAI not in (3,4)  and sp.SANPHAM_FK = ab.sanpham_fk  "
		               + "				 and a.hdNCC_fk = nh.hdNCC_fk and ab.MUAHANG_FK = sp.MUAHANG_FK "
		               + "              ),0) SOLUONGDANHAN  "	 
					   + "FROM ERP_NHANHANG_SANPHAM sp inner join ERP_NHANHANG nh on sp.NHANHANG_FK = nh.PK_SEQ "
					   + "WHERE sp.NHANHANG_FK = "+ nhId +" and sp.MUAHANG_FK = "+muahang_fk;
				 System.out.println("101. lay chi tiet nhan hang " +query );
				 ResultSet rsNHCT = db.get(query);
				 while(rsNHCT.next())
				 {					 
					 double sl = rsNHCT.getDouble("SOLUONGDAT")- rsNHCT.getDouble("SOLUONGDANHAN") - rsNHCT.getDouble("SOLUONGNHAN") ;
					 double booked_ = 0;
					 double available_ = sl;
					 
					 //muahang_fk = rsNHCT.getString("MUAHANG_FK");
					 
					 query = "insert ERP_NHANHANG_SANPHAM(NHANHANG_FK, MUAHANG_FK ,SANPHAM_FK, TAISAN_FK, CCDC_FK, CHIPHI_FK, DIENGIAI, DONVI, NGAYNHANDUKIEN, KHONHAN, SOLUONGDAT, SOLUONGNHAN, DUNGSAI, DONGIA, TIENTE_FK, TYGIAQUYDOI, DONGIAVIET, SOLOKYGUI, NGAYSANXUAT, NGAYHETHAN) " +
							 "values('" + nhCurrent + "', "+ muahang_fk +", " + rsNHCT.getString("SANPHAM_FK") + ", " + rsNHCT.getString("TAISAN_FK") + ", " +  rsNHCT.getString("CCDC_FK") + ", " + rsNHCT.getString("CHIPHI_FK") + ","+
							 " N'" + rsNHCT.getString("DIENGIAI") + "', N'" + rsNHCT.getString("DONVI")  + "', '" + rsNHCT.getString("NGAYNHANDUKIEN")  + "', " + rsNHCT.getString("KHONHAN") + ", " +
							 " '" + rsNHCT.getString("SOLUONGDAT") + "',  '" + sl + "', '" + rsNHCT.getString("DUNGSAI") + "', " + rsNHCT.getString("DONGIA") + ", '" + 
							 rsNHCT.getString("TIENTE_FK") + "', '" + rsNHCT.getString("TYGIAQUYDOI") + "', '" +  rsNHCT.getString("DONGIAVIET") + "', '"+ ngayHT.replaceAll("-", "") +"', '"+ ngayHT +"', '"+ ngayHT +"' )";
					 System.out.println("102. insert nhanhang_sanpham: "+query);
					 if (!db.update(query))
					 {
						    rsNHCT.close();
							msg= "Không thể tạo Nhận hàng- sản phẩm tự động : " + query;
							return msg;
					 }
					 
					 if(!loaimh.trim().equals("2"))
					 {
						 /*if(loaimh.trim().equals("0"))
						 {*/
							 query = "select a.SANPHAM_FK, a.SOLO, a.giatheolo as DONGIA, a.NGAYSANXUAT, a.NGAYHETHAN, a.NGAYNHANDUKIEN, d.SOLUONG - a.soluong "
									+ " - isnull((select SUM(ab.SOLUONG)       "
									+ " from ERP_NHANHANG nh inner join ERP_NHANHANG_SP_CHITIET ab on nh.PK_SEQ=ab.NHANHANG_FK  "
						            + " where nh.PK_SEQ != "+ nhId +" and nh.TRANGTHAI not in (3,4)  and a.SANPHAM_FK = ab.sanpham_fk and nh.hdNCC_fk = b.hdNCC_fk "
						            + " and ab.solo = a.solo and a.MUAHANG_FK = ab.MUAHANG_FK "
						            + "	),0) as soluong "
							 		+ "from ERP_NHANHANG_SP_CHITIET a inner join ERP_NHANHANG b on a.nhanhang_fk = b.pk_Seq "
							 		+ "inner join ERP_HOADONNCC_DONMUAHANG d on d.HOADONNCC_FK = b.hdNCC_fk "
							 		+ "and a.SANPHAM_FK = d.SANPHAM_FK and a.SOLO = d.SOLO and d.MUAHANG_FK = a.MUAHANG_FK "
							 		+ "where a.NHANHANG_FK = "+ nhId +" and a.sanpham_fk = "+rsNHCT.getString("SANPHAM_FK")+"  and a.MUAHANG_FK = "+muahang_fk+" "
							 		+ "and d.SOLUONG - a.SOLUONG > 0 ";
							 System.out.println("104. lay chi tiet nhan hang NHAP KHAU: "+query);
						 /*}
						 else
						 {*/
							 /*query = "select a.SANPHAM_FK, a.SOLO, c.DONGIA, a.NGAYSANXUAT, a.NGAYHETHAN, a.NGAYNHANDUKIEN, c.SOLUONG - a.soluong "
									+ " - isnull((select SUM(ab.SOLUONG)       "
									+ "           from ERP_NHANHANG nh inner join ERP_NHANHANG_SP_CHITIET ab on nh.PK_SEQ=ab.NHANHANG_FK  "
						            + "           where nh.PK_SEQ != "+ nhId +" and nh.TRANGTHAI not in (3,4)  and a.SANPHAM_FK = ab.sanpham_fk  and nh.hdNCC_fk = b.hdNCC_fk and ab.solo = a.solo "
						            + "           ),0) as soluong "
							 		+ "from ERP_NHANHANG_SP_CHITIET a inner join ERP_NHANHANG b on a.nhanhang_fk = b.pk_Seq "
							 		+ "inner join erp_phanbomuahang_po d on d.poduocpb = b.muahang_fk inner join erp_phanbomuahang_sp_chitiet c on c.PHANBO_FK = d.PHANBO_FK "
							 		+ "and a.SANPHAM_FK = c.SANPHAM_FK and a.SOLO = c.SOLO and b.congty_fk = c.CONGTY_FK "
							 		+ "where a.NHANHANG_FK = "+ nhId +" and a.sanpham_fk = "+rsNHCT.getString("SANPHAM_FK")+" and c.SOLUONG - a.SOLUONG > 0 ";*/
							 /*query = "select a.SANPHAM_FK, a.SOLO, a.NGAYSANXUAT, a.NGAYHETHAN, a.NGAYNHANDUKIEN, b.soluongmax as soluong from \n"+
									"( \n"+
									"	select a.SANPHAM_FK, a.SOLO, a.NGAYSANXUAT, a.NGAYHETHAN, a.NGAYNHANDUKIEN \n"+
									"	from ERP_NHANHANG_SP_CHITIET a inner join ERP_NHANHANG b on a.nhanhang_fk = b.pk_Seq \n"+ 
									"	where a.NHANHANG_FK = "+ nhId +" and a.sanpham_fk = "+rsNHCT.getString("SANPHAM_FK")+" \n"+
									") a inner join  \n"+
									"( \n"+
									"	select a.SANPHAM_FK, a.SOLO, a.soluong - isnull((select SUM(ct.soluong) from ERP_NHANHANG_SP_CHITIET ct \n"+
									"	inner join ERP_NHANHANG nh on ct.NHANHANG_FK = nh.PK_SEQ \n"+ 
									"	inner join ERP_HOADONNCC_DONMUAHANG hd_ddh on nh.hdNCC_fk = hd_ddh.HOADONNCC_FK and ct.SANPHAM_FK = hd_ddh.SANPHAM_FK  \n"+
									"	where nh.trangthai <> 3 and hd_ddh.MUAHANG_FK = c.pk_seq and ct.SANPHAM_FK = a.SANPHAM_FK and ct.SOLO = a.SOLO  \n"+
									"	group by ct.SANPHAM_FK, ct.SOLO), 0) as soluongmax \n"+
									"	from erp_phanbomuahang_sp_chitiet a inner join erp_phanbomuahang_po b on a.phanbo_fk = b.phanbo_fk \n"+ 
									"	inner join ERP_MUAHANG c on b.poduocpb = c.PK_SEQ and c.congty_fk = a.congty_fk  \n"+
									"	inner join ERP_HOADONNCC_DONMUAHANG d on c.PK_SEQ = d.muahang_fk  and a.SANPHAM_FK = d.SANPHAM_FK \n"+ 
									"	inner join ERP_PHANBOMUAHANG e on b.PHANBO_FK = e.pk_seq  \n"+
									"	inner join ERP_NHAPKHONHAMAY f on f.muahang_fk = e.MUAHANG_FK inner join ERP_NHAPKHONHAMAY_SP_CHITIET g on g.nhamay_fk = f.PK_SEQ \n"+ 
									"	and g.SOLO = a.SOLO and g.SANPHAM_FK = a.SANPHAM_FK  \n"+
									"	where d.hoadonncc_fk = "+hdNcc+"  and a.sanpham_fk = "+rsNHCT.getString("SANPHAM_FK")+" \n"+
									") b on a.SANPHAM_FK = b.SANPHAM_FK and a.SOLO = b.SOLO \n"+
									"where b.soluongmax > 0";
							 System.out.println("104. lay chi tiet nhan hang trong nuoc: "+query);
						 }*/
						 System.out.println("104. lay chi tiet nhan hang: "+query);
						 ResultSet rsNHTN = db.getScrol(query);
						 while(rsNHTN.next())
						 {
							 query = "insert ERP_NHANHANG_SP_CHITIET(NHANHANG_FK, MUAHANG_FK, SANPHAM_FK, LANNHAN, SOLO, SOLUONG, GIATHEOLO, NGAYSANXUAT, NGAYHETHAN, NGAYNHANDUKIEN ,KHU_FK, solokygui) " +
									 "values('" + nhCurrent + "', "+muahang_fk+", '" + rsNHTN.getString("SANPHAM_FK") + "', '1', '" + rsNHTN.getString("Solo") + "', " +
									 "'" + rsNHTN.getString("soluong") + "', "+ rsNHTN.getString("DONGIA") +", '" + rsNHTN.getString("ngaysanxuat") +"', '"+rsNHTN.getString("ngayhethan")+"','"+ rsNHTN.getString("NGAYNHANDUKIEN") +"', NULL, '"+rsNHTN.getString("Solo")+"')";
							 System.out.println("105. insert chi tiet nhan hang trong nuoc: "+query);
							 if (!db.update(query))
							 {
							    rsNHCT.close();
								msg = "Không thể tạo Nhận hàng- sản phẩm - chi tiết tự động : " + query;
								return msg;
							 }
						 }
						 
						 // Lấy số lô luc nhap kho NCC 
						 if(istudong <= 0)
						 {
							 String solo_NK = "";
							 String ngaynhap_NK = "";
							 String ngaysx = "";
							 String ngayhh = "";
							
							 msg= util_kho.Update_NPP_Kho_Sp(ngayHT,"Nhập kho ký gửi - Nhận hàng",db,khokyguiNCC, rsNHCT.getString("SANPHAM_FK"),NHAPHANPHOI_FK,NHOMKENH_FK, sl,booked_,available_,rsNHCT.getDouble("DONGIA"));
							 if(msg.length() >0){
							   
								return msg;
							 }
						 
							 msg= util_kho.Update_NPP_Kho_Sp_NCC(ngayHT,"Nhập kho ký gửi - Nhận hàng",db,khokyguiNCC, rsNHCT.getString("SANPHAM_FK"),NHAPHANPHOI_FK,NHOMKENH_FK, sl,booked_,available_,rsNHCT.getDouble("DONGIA"), nccId);
							 if(msg.length() >0){
							   
								return msg;
							 }
							 
							 if(!loaimh.equals("2"))
							 {
								 khoxuat = khoNCC;
								 nccXuatId = nccId;
								 
								 msg= util_kho.Update_NPP_Kho_Sp(ngayHT,"Nhận hàng",db,khoNCC, rsNHCT.getString("SANPHAM_FK"),NHAPHANPHOI_FK,NHOMKENH_FK, sl*(-1),booked_,available_*(-1),rsNHCT.getDouble("DONGIA"));
								 if(msg.length() >0){
								   
									return msg;
								 }
							 
								 msg= util_kho.Update_NPP_Kho_Sp_NCC(ngayHT,"Nhận hàng",db,khoNCC, rsNHCT.getString("SANPHAM_FK"),NHAPHANPHOI_FK,NHOMKENH_FK, sl*(-1),booked_,available_*(-1),rsNHCT.getDouble("DONGIA"), nccId);
								 if(msg.length() >0){
								   
									return msg;
								 }
							 }
										 
							 // 1.Tăng kho ký gửi NCC (nhập ngày nhập), solo lấy ngày hiện tại
							 nccNhanId = nccId;
							 khonhan = khokyguiNCC;
							 rsNHTN.beforeFirst();
							 while(rsNHTN.next())
							 {
								 sl = rsNHTN.getDouble("soluong");
								 available_ = sl;
								 System.out.println("[aaaaaaaaaaaaaaaa] "+ khokyguiNCC+" [soluong] "+ sl);
								 msg= util_kho.Update_NPP_Kho_Sp_Chitiet_NCC(ngayHT,"Nhập kho ký gửi - Nhận hàng",db,khokyguiNCC, rsNHTN.getString("SANPHAM_FK"),NHAPHANPHOI_FK,NHOMKENH_FK,
										 nccId, rsNHTN.getString("solo"), rsNHTN.getString("ngaysanxuat"), rsNHTN.getString("ngayhethan"),getDateTime(), sl, 
										 booked_,available_,rsNHTN.getDouble("DONGIA"));
								 if(msg.length() >0){
									return msg;
								 }
								 //2.Giảm kho nhà cung cấp
								 if(!loaimh.equals("2")) // PO nhập khẩu  & trong nước có chức năng nhập kho của NCC
								 {
									 khoxuat = khoNCC;
									 nccXuatId = nccId;
				
									 System.out.println("---------toi day: kho "+khoxuat);
									 msg= util_kho.Update_NPP_Kho_Sp_Chitiet_NCC(ngayHT,"Nhận hàng",db,khoNCC, rsNHTN.getString("SANPHAM_FK"),NHAPHANPHOI_FK,NHOMKENH_FK, nccId, rsNHTN.getString("solo"), rsNHTN.getString("ngaysanxuat"), rsNHTN.getString("ngayhethan"), ngaynhap_NK, sl*(-1),booked_,available_*(-1),rsNHTN.getDouble("DONGIA"));
									 if(msg.length() >0){
										return msg;
									 }
								 }
							 } 
						 }
					 }
				 }
			 }	 
			 //CẬP NHẬT TOOLTIP
			 db.execProceduce2("CapNhatTooltip_NhanHang", new String[] { nhCurrent } );
			 //3. Tạo phiếu chuyển kho tự động
			 System.out.println("[istudong] "+istudong);
			 if(!loaimh.equals("2") && istudong <= 0)
			 {
				 System.out.println("[vao chuyen kho] "+istudong);
				 msg = TaoChuyenKhoTuDong(db, userId, ctyId, NHAPHANPHOI_FK, khoxuat, khonhan, nhCurrent, nccXuatId, nccNhanId, khXuatId, khNhanId, "2", loaimh);
				 if(msg.trim().length() > 0){
					 return msg;
				 }
				 
				 // chương trình sẽ gài kế toán nhận hàng vào kho ký gửi tại nhà cung cấp , 
				 //những nhận hàng sau thì không gài kế toán nữa mà chỉ là chuyển kho nội bộ trong kho
				 
				 
			 }
			 	 
			 //----------------- End Nhận hàng----------------------
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
			 return "";
		 }
		 
		 		 		 
		return msg;
	}

	// loaiNH: 0 chốt nhận hàng bình thường, 1 chốt nhận hàng tự động, 2 tạo nhận hàng tự động
	private String TaoChuyenKhoTuDong(dbutils db, String userId, String ctyId, String nppId, String khoxuat, String khonhan, String nhId, String nccXuatId, String nccNhanId, String khXuatId, String khNhanId, String loaiNH, String loaimh) 
	{
		String msg = "";
		
		// Nội dung xuất: Chuyển kho bên ngoài 100024
		// Đã trừ cập nhật kho ở phía trên
		
		try 
		{
			
			String query = " insert ERP_CHUYENKHO(CONGTY_FK, NGUOIDENGHI,NHOMKENH_FK,NPP_FK,NGUOINHAN ,noidungxuat_fk, ngaychuyen, ngaynhan, ngaychot, lydo, ghichu, trangthai, \n"+
					  " khoxuat_fk, khonhan_fk, trangthaisp, ngaytao, nguoitao, ngaysua, nguoisua, NCC_CHUYEN_FK, NCC_NHAN_FK ,KYHIEU, SOCHUNGTU, LENHDIEUDONG, NGAYDIEUDONG, \n"+
					  " NGUOIDIEUDONG, VEVIEC, NGUOIVANCHUYEN, PHUONGTIEN, HOPDONG,KH_XUAT_FK,KH_NHAN_FK , NHANHANG_FK) \n" +
					  " values("+ ctyId +",N'','100000',"+nppId+", N'','100024', '" + this.getDateTime() + "', '" + this.getDateTime() + "', '" + this.getDateTime() + "', N'', N'Chuyển kho tự động từ nhận hàng' + '"+ nhId +"' , '3',  \n" +
					  "        " + khoxuat + ", " + khonhan + ", '1', '" + this.getDateTime() + "', '" + userId + "', '" + this.getDateTime() + "', '" + userId + "'," + nccXuatId + ", " + nccNhanId + ", \n"+
					  "        '','','','',N'',N'',N'',N'',N'',"+khXuatId+","+khNhanId+", "+ nhId +")";
			System.out.println("---1 chuyen kho tu dong " + query);
			if(!db.update(query))
			{
				msg = "Không thể tạo mới ERP_CHUYENKHO " + query;
				return msg;
			}
			
			String ycnlCurrent = "";
			query = " select IDENT_CURRENT('ERP_CHUYENKHO') as ckId";
			
			ResultSet rsPxk = db.get(query);						
			if(rsPxk.next())
			{
				ycnlCurrent = rsPxk.getString("ckId");
				rsPxk.close();
			}
			
			query = " update ERP_CHUYENKHO set machungtu = 'PX' + SUBSTRING(NGAYCHOT, 6, 2) + SUBSTRING(NGAYCHOT, 0, 5) + '-' + dbo.LaySoChungTu( " + ycnlCurrent + " ) " + 
					" where pk_seq = '" + ycnlCurrent + "' ";
			if(!db.update(query))
			{
				msg = "Không thể tạo mới ERP_CHUYENKHO " + query;
				return msg;
			}
			
			query = " SELECT DISTINCT c.ma as masp , SANPHAM_FK, DONGIA, b.ngaynhandukien, b.SOLUONGDAT, b.SOLUONGNHAN, b.SOLOKYGUI, b.NGAYSANXUAT, b.NGAYHETHAN, " +  
					"        (select distinct LOAI from ERP_MUAHANG where nh.MUAHANG_FK = PK_SEQ) loaimh, b.MUAHANG_FK \n"+
					" FROM ERP_NHANHANG nh inner join ERP_NHANHANG_SANPHAM b on nh.PK_SEQ = b.NHANHANG_FK  " +
					"      inner join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ " +
					" WHERE b.NHANHANG_FK = '" + nhId + "'  ";
			
			
			System.out.println("---2 lay sp nhan hang "+ query);
			ResultSet rs = db.get(query);
			//String loaimh = "";
			String muahang_fk = "";
			if(rs!=null)
			{
				while(rs.next())
				{
					//loaimh = rs.getString("loaimh") ;
					muahang_fk = rs.getString("MUAHANG_FK") ;
					
					String spId =  rs.getString("SANPHAM_FK") ;
					double soluong = rs.getDouble("SOLUONGNHAN");
					double dongia = rs.getDouble("DONGIA");		
					
					String soloNM_NK = "";
					String ngaynhapNM_NK = "";
					
							
					query = //" if not exists (select sanpham_fk from ERP_CHUYENKHO_SANPHAM where chuyenkho_fk = " + ycnlCurrent + " and sanpham_fk = " + spId + " and muahang_fk = "+muahang_fk+") " +
							" INSERT ERP_CHUYENKHO_SANPHAM( CHUYENKHO_FK, SANPHAM_FK, SOLUONGYEUCAU, SOLUONGXUAT, SOLUONGNHAN, DONGIA, MUAHANG_FK ) " +
							" values( '" + ycnlCurrent + "', '" + spId + "', "+soluong+", " + soluong + ", " + soluong + ", "+dongia+", "+muahang_fk+" ) ";
					System.out.println("---3 insert erp_chuyenkho_sanpham " +query);
					if(!db.update(query))
					{
						msg = "Không thể tạo mới ERP_CHUYENKHO_SANPHAM : " + query;
						return msg;
					}
					
					
					// Lấy lo kho NM/NK
					if(!loaimh.equals("2"))
					{
						if(loaimh.equals("0")){
							 query = "SELECT ct.SOLO, ct.NGAYNHAP "
								   + "FROM ERP_NHAPKHONHAPKHAU nk inner join ERP_NHAPKHONHAPKHAU_SP_CHITIET ct on nk.PK_SEQ = ct.NHAPKHO_FK "
								   + "WHERE nk.pk_seq = "+ muahang_fk +" and ct.SANPHAM_FK =  "+ spId +" "; 
						}
						else if(loaimh.equals("1")){
							 query = "SELECT ct.SOLO, nk.NGAYNHAP, ct.NGAYSANXUAT, ct.NGAYHETHAN "
								   + "FROM ERP_NHAPKHONHAMAY nk inner join ERP_NHAPKHONHAMAY_SP_CHITIET ct on nk.PK_SEQ = ct.NHAMAY_FK "
								   + "WHERE nk.MUAHANG_FK in (select distinct b.MUAHANG_FK "
								   + "                        from ERP_PHANBOMUAHANG_PO a inner join ERP_PHANBOMUAHANG b on a.PHANBO_FK = b.PK_SEQ "
								   + "                        where a.PODUOCPB =  "+ muahang_fk +")  and ct.SANPHAM_FK =  "+ spId +" ";  
						}					
						System.out.println("---4 lay solo kho nm/nk " + query);
						ResultSet rsCT = db.get(query);
						if(rsCT!= null)
						{
							while(rsCT.next())
							{																
								soloNM_NK = rsCT.getString("SOLO")	;
								ngaynhapNM_NK = rsCT.getString("NGAYNHAP")	;
								
								// 0 Nhận hàng BT: loxuat : Nhập kho NM/ NK; lô nhận : Nhận hàng - chi tiết (solo)
								// 1 Nhận hàng tự động - chốt : loxuat : Nhận hàng - chi tiết (lokygui), lô nhận : Nhận hàng - chi tiết (solo)
								// 2 Tạo Nhận hàng tự động : loxuat : Nhập kho NM/ NK; lô nhận kho ký gửi
								
								/*if(loaiNH.equals("0"))
								{
									if(loaimh.equals("0") && khoxuat != "NULL")
									{																	
										query=" INSERT INTO ERP_CHUYENKHO_SP_XUATHANG (CHUYENKHO_FK,SANPHAM_FK,SOLO,KHU,NGAYBATDAU,SOLUONG,DONGIA,NGAYHETHAN ) " +
											  " VALUES ("+ycnlCurrent+","+ spId+",'"+ soloNM_NK +"' , NULL ,'"+ ngaynhapNM_NK +"',"+soluong+","+dongia+",'')";
										System.out.println("---5 tao chuyenkho xuat hang " + query);	
										if(!db.update(query))
										{
											msg = "0.Không thể tạo mới ERP_CHUYENKHO_SP_XUATHANG : " + query;
											return msg;
										}
									
									}*/
									
									query = " SELECT DISTINCT c.ma as masp , b.SANPHAM_FK, ISNULL(b.GIATHEOLO,0) DONGIA, b.SOLUONG, b.SOLO, b.NGAYSANXUAT, b.NGAYHETHAN " +  				
											" FROM ERP_NHANHANG_SP_CHITIET b inner join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ " +
											" WHERE b.NHANHANG_FK = '" + nhId + "' and b.SANPHAM_FK = "+ spId +" "+
											" and b.solo = '"+soloNM_NK+"' and b.muahang_fk = "+muahang_fk+" ";
									System.out.println("---5 lay chi tiet nhan hang "+query);
									ResultSet rsCKNhan = db.get(query);
									if(rsCKNhan!= null)
									{
										while(rsCKNhan.next())
										{
											if(khoxuat != "NULL")
											{																	
												query=" INSERT INTO ERP_CHUYENKHO_SP_XUATHANG (CHUYENKHO_FK, SANPHAM_FK, SOLO, KHU, NGAYBATDAU, SOLUONG, DONGIA, NGAYHETHAN, MUAHANG_FK ) " +
													  " VALUES ("+ycnlCurrent+", "+ spId+", '"+ rsCKNhan.getString("SOLO") +"', NULL, '"+ ngaynhapNM_NK +"', "+rsCKNhan.getString("SOLUONG")+", "+
													  rsCKNhan.getString("DONGIA")+", '"+rsCKNhan.getString("NGAYHETHAN")+"', "+muahang_fk+")";
												System.out.println("---5 tao chuyenkho xuat hang " + query);	
												if(!db.update(query))
												{
													msg = "0.Không thể tạo mới ERP_CHUYENKHO_SP_XUATHANG : " + query;
													return msg;
												}
											
											}
											
											query=" INSERT INTO ERP_CHUYENKHO_SP_NHANHANG (CHUYENKHO_FK, CK_SP_XH_FK, SANPHAM_FK, SOLO, KHU, NGAYBATDAU, SOLUONG, DONGIA, NGAYHETHAN, MUAHANG_FK ) " +
												  " VALUES ("+ycnlCurrent+", NULL, "+ rsCKNhan.getString("SANPHAM_FK")+",'"+rsCKNhan.getString("SOLO")+"' , NULL, "+
												  " '"+rsCKNhan.getString("NGAYSANXUAT")+"',"+rsCKNhan.getString("SOLUONG")+","+rsCKNhan.getString("DONGIA")+",'"+rsCKNhan.getString("NGAYHETHAN")+"', "+muahang_fk+")";
											System.out.println("---5 tao chuyenkho nhan hang " + query);		
											if(!db.update(query))
											{
												msg = "1.Không thể tạo mới ERP_CHUYENKHO_SP_XUATHANG : " + query;
												return msg;
											}
											
										}rsCKNhan.close();
									}
									
								/*}
								else if(loaiNH.equals("1"))
								{
									String lokygui = rs.getString("SOLOKYGUI");
									String ngaysx = rs.getString("NGAYSANXUAT");
									String ngayhh = rs.getString("NGAYHETHAN");
									
									if(loaimh.equals("1")){
										query = "select c.* from ERP_NHANHANG nh inner join ERP_NHANHANG_SANPHAM b on nh.PK_SEQ = b.NHANHANG_FK "+
												"inner join ERP_NHANHANG_SP_CHITIET c on c.NHANHANG_FK = b.NHANHANG_FK and b.SANPHAM_FK = c.SANPHAM_FK "+
												"where nh.PK_SEQ = "+ nhId + " and c.solo = '"+soloNM_NK+"'";
										System.out.println("---6 lay chi tiet xuat hang "+query);
										ResultSet rsKygui = db.get(query);
										if(rsKygui!= null)
										{
											while(rsKygui.next())
											{	
												lokygui = rsKygui.getString("SOLOKYGUI");
												ngaysx = rsKygui.getString("NGAYSANXUAT");
												ngayhh = rsKygui.getString("NGAYHETHAN");
												query=" INSERT INTO ERP_CHUYENKHO_SP_XUATHANG (CHUYENKHO_FK,SANPHAM_FK,SOLO,KHU,NGAYBATDAU,SOLUONG,DONGIA,NGAYHETHAN ) " +
														  " VALUES ("+ycnlCurrent+","+ spId +",'"+ lokygui +"' , NULL ,'"+ ngaysx +"',"+rsKygui.getDouble("soluong")+","+dongia+", '"+ ngayhh +"')";
												System.out.println("---6 tao chuyenkho xuat hang " + query);
												if(!db.update(query))
												{
													msg = "2.Không thể tạo mới ERP_CHUYENKHO_SP_XUATHANG : " + query;
													return msg;
												}
											}
										}
									}
									else
									{
										query=" INSERT INTO ERP_CHUYENKHO_SP_XUATHANG (CHUYENKHO_FK,SANPHAM_FK,SOLO,KHU,NGAYBATDAU,SOLUONG,DONGIA,NGAYHETHAN ) " +
												  " VALUES ("+ycnlCurrent+","+ spId +",'"+ lokygui +"' , NULL ,'"+ ngaysx +"',"+soluong+","+dongia+", '"+ ngayhh +"')";
										System.out.println("---6 tao chuyenkho xuat hang " + query);
										if(!db.update(query))
										{
											msg = "2.Không thể tạo mới ERP_CHUYENKHO_SP_XUATHANG : " + query;
											return msg;
										}
									}
									
									query = " SELECT DISTINCT c.ma as masp , b.SANPHAM_FK, ISNULL(b.GIATHEOLO,0) DONGIA, b.SOLUONG, b.SOLO, b.NGAYSANXUAT, b.NGAYHETHAN " +  				
											" FROM ERP_NHANHANG_SP_CHITIET b inner join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ " +
											" WHERE b.NHANHANG_FK = '" + nhId + "' and b.SANPHAM_FK = "+ spId +" ";
									if(loaimh.equals("1")) query += "and b.solo = '"+soloNM_NK+"' ";
									System.out.println("---6 lay chi tiet nhan hang "+query);
									ResultSet rsCKNhan = db.get(query);
									if(rsCKNhan!= null)
									{
										while(rsCKNhan.next())
										{								
											query=" INSERT INTO ERP_CHUYENKHO_SP_NHANHANG (CHUYENKHO_FK, CK_SP_XH_FK, SANPHAM_FK,SOLO,KHU,NGAYBATDAU,SOLUONG,DONGIA,NGAYHETHAN ) " +
													  " VALUES ("+ycnlCurrent+", NULL, "+ rsCKNhan.getString("SANPHAM_FK")+",'"+rsCKNhan.getString("SOLO")+"' , NULL " +
													  " ,'"+rsCKNhan.getString("NGAYSANXUAT")+"',"+rsCKNhan.getString("SOLUONG")+","+rsCKNhan.getString("DONGIA")+",'"+rsCKNhan.getString("NGAYHETHAN")+"')";
											System.out.println("---6 tao chuyenkho nhan hang " + query);		
											if(!db.update(query))
											{
												msg = "3.Không thể tạo mới ERP_CHUYENKHO_SP_XUATHANG : " + query;
												return msg;
											}
											
										}rsCKNhan.close();
									}
								}
								else
								{
									if(loaimh.equals("1"))
									{
										query = " SELECT DISTINCT c.ma as masp , b.SANPHAM_FK, ISNULL(b.GIATHEOLO,0) DONGIA, b.SOLUONG, b.SOLO, b.NGAYSANXUAT, b.NGAYHETHAN " +  				
												" FROM ERP_NHANHANG_SP_CHITIET b inner join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ " +
												" WHERE b.NHANHANG_FK = '" + nhId + "' and b.SANPHAM_FK = "+ spId +" and b.solo = '"+soloNM_NK+"' ";
										System.out.println("---7 lay chi tiet nhan hang "+query);
										ResultSet rsCKNhan = db.get(query);
										if(rsCKNhan!= null)
										{
											while(rsCKNhan.next())
											{						
												if(khoxuat != "NULL")
												{
													query=" INSERT INTO ERP_CHUYENKHO_SP_XUATHANG (CHUYENKHO_FK,SANPHAM_FK,SOLO,KHU,NGAYBATDAU,SOLUONG,DONGIA,NGAYHETHAN ) " +
															  " VALUES ("+ycnlCurrent+","+ spId+",'"+ soloNM_NK +"' , NULL ,'"+ ngaynhapNM_NK +"',"+soluong+","+dongia+",'')";
													System.out.println("---7 tao chuyenkho xuat hang " + query);
													if(!db.update(query))
													{
														msg = "4.Không thể tạo mới ERP_CHUYENKHO_SP_XUATHANG : " + query;
														return msg;
													}
												}
												query=" INSERT INTO ERP_CHUYENKHO_SP_NHANHANG (CHUYENKHO_FK, CK_SP_XH_FK, SANPHAM_FK,SOLO,KHU,NGAYBATDAU,SOLUONG,DONGIA,NGAYHETHAN ) " +
														  " VALUES ("+ycnlCurrent+", NULL, "+ spId +",'"+ soloNM_NK +"' , NULL " +
														  " ,'"+ rs.getString("NGAYSANXUAT")+"',"+ rsCKNhan.getString("soluong") +","+ dongia +",'"+ rs.getString("NGAYHETHAN") +"')";
												System.out.println("---7 tao chuyenkho nhan hang " + query);
												if(!db.update(query))
												{
													msg = "5.Không thể tạo mới ERP_CHUYENKHO_SP_XUATHANG : " + query;
													return msg;
												}
											}
										}
									}
									else
									{
										if(khoxuat != "NULL")
										{
											query=" INSERT INTO ERP_CHUYENKHO_SP_XUATHANG (CHUYENKHO_FK,SANPHAM_FK,SOLO,KHU,NGAYBATDAU,SOLUONG,DONGIA,NGAYHETHAN ) " +
													  " VALUES ("+ycnlCurrent+","+ spId+",'"+ soloNM_NK +"' , NULL ,'"+ ngaynhapNM_NK +"',"+soluong+","+dongia+",'')";
											System.out.println("---7 tao chuyenkho xuat hang " + query);
											if(!db.update(query))
											{
												msg = "4.Không thể tạo mới ERP_CHUYENKHO_SP_XUATHANG : " + query;
												return msg;
											}
										}
										
										query=" INSERT INTO ERP_CHUYENKHO_SP_NHANHANG (CHUYENKHO_FK, CK_SP_XH_FK, SANPHAM_FK,SOLO,KHU,NGAYBATDAU,SOLUONG,DONGIA,NGAYHETHAN ) " +
												  " VALUES ("+ycnlCurrent+", NULL, "+ spId +",'"+ getDateTime().replaceAll("-", "") +"' , NULL " +
												  " ,'"+ rs.getString("NGAYSANXUAT")+"',"+ soluong +","+ dongia +",'"+ rs.getString("NGAYHETHAN") +"')";
										System.out.println("---7 tao chuyenkho nhan hang " + query);
										if(!db.update(query))
										{
											msg = "5.Không thể tạo mới ERP_CHUYENKHO_SP_XUATHANG : " + query;
											return msg;
										}
									}
								}*/
								
							}rsCT.close();
						}
					}
					else
					{
						query = " SELECT DISTINCT c.ma as masp , b.SANPHAM_FK, ISNULL(b.GIATHEOLO,0) DONGIA, b.SOLUONG, b.SOLO, b.NGAYSANXUAT, b.NGAYHETHAN " +  				
								" FROM ERP_NHANHANG_SP_CHITIET b inner join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ " +
								" WHERE b.NHANHANG_FK = '" + nhId + "' and b.SANPHAM_FK = "+ spId +" and b.muahang_fk = "+muahang_fk+" ";
						System.out.println("---5 lay chi tiet nhan hang "+query);
						ResultSet rsCKNhan = db.get(query);
						if(rsCKNhan!= null)
						{
							while(rsCKNhan.next())
							{
								if(khoxuat != "NULL")
								{																	
									query=" INSERT INTO ERP_CHUYENKHO_SP_XUATHANG (CHUYENKHO_FK, SANPHAM_FK, SOLO, KHU, NGAYBATDAU, SOLUONG, DONGIA, NGAYHETHAN, MUAHANG_FK) " +
										  " VALUES ("+ycnlCurrent+", "+ spId+", '"+ rsCKNhan.getString("SOLO") +"', NULL, '"+ ngaynhapNM_NK +"', "+rsCKNhan.getString("SOLUONG")+", "+
										  rsCKNhan.getString("DONGIA")+", '"+rsCKNhan.getString("NGAYHETHAN")+"', "+muahang_fk+")";
									System.out.println("---5 tao chuyenkho xuat hang " + query);	
									if(!db.update(query))
									{
										msg = "0.Không thể tạo mới ERP_CHUYENKHO_SP_XUATHANG : " + query;
										return msg;
									}
								
								}
								
								query=" INSERT INTO ERP_CHUYENKHO_SP_NHANHANG (CHUYENKHO_FK, CK_SP_XH_FK, SANPHAM_FK, SOLO, KHU, NGAYBATDAU, SOLUONG, DONGIA, NGAYHETHAN, MUAHANG_FK) " +
									  " VALUES ("+ycnlCurrent+", NULL, "+ rsCKNhan.getString("SANPHAM_FK")+", '"+rsCKNhan.getString("SOLO")+"', NULL " +
									  " , '"+rsCKNhan.getString("NGAYSANXUAT")+"', "+rsCKNhan.getString("SOLUONG")+", "+rsCKNhan.getString("DONGIA")+", "+
									  " '"+rsCKNhan.getString("NGAYHETHAN")+"', "+muahang_fk+")";
								System.out.println("---5 tao chuyenkho nhan hang " + query);		
								if(!db.update(query))
								{
									msg = "1.Không thể tạo mới ERP_CHUYENKHO_SP_XUATHANG : " + query;
									return msg;
								}
								
							}rsCKNhan.close();
						}
					}
				}rs.close();
			}
					// giá trong 	
			if(khonhan.equals("100004") && khoxuat.equals("100009")){
				// cập nhật kế toán giống nhận hàng vào chỗ này

				
				//Lay tai khoan No trong bang config
				 query = 	" SELECT CK.KHONHAN_FK,CK.KHOXUAT_FK,NH.SOLO,NH.NGAYHETHAN, NHAN.NOIDUNGNHAN_FK, CK.NGAYCHUYEN, NH.SANPHAM_FK, SOLUONG as SOLUONGNHAN, nhsp.dongiaviet, \n"+
						 	" NH.DONGIA ,NHSP.TIENTE_FK AS TIENTE_FK, NHSP.TYGIAQUYDOI, NH.DONGIA, DV.DIENGIAI AS DONVI, \n"+  
							" SP.MA AS MASP  ,SP.TEN AS SPTEN \n"+
							" FROM ERP_CHUYENKHO_SP_NHANHANG NH  \n"+
							" INNER JOIN ERP_CHUYENKHO CK ON CK.PK_SEQ=NH.CHUYENKHO_FK \n"+
							" INNER JOIN ERP_NHANHANG NHAN ON NHAN.PK_SEQ=CK.NHANHANG_FK \n"+
							" INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NHSP.NHANHANG_FK=NHAN.PK_SEQ \n"+
							"	AND NH.SANPHAM_FK=NHSP.SANPHAM_FK and nh.muahang_fk = nhsp.muahang_fk \n"+
							" INNER JOIN SANPHAM SP ON SP.PK_SEQ=NH.SANPHAM_FK   \n"+
							" INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ=SP.DVDL_FK \n"+
							" WHERE NH.CHUYENKHO_FK = "+ycnlCurrent;
				
				//System.out.println("_____LAY TK: " + query);
				 ResultSet rsSp = db.get(query);
				 Utility util = new Utility();
				 while(rsSp.next())
				 {
					String taisan_fk = rsSp.getString("SANPHAM_FK");
					String masp = rsSp.getString("masp");
					String tensp= rsSp.getString("SPTEN");
					String donvi = rsSp.getString("donvi");
					String soluongNhan = rsSp.getString("SOLUONGNHAN");
					String dongia = rsSp.getString("DONGIA");
					String tiente = rsSp.getString("TIENTE_FK");
					String dongiaViet = rsSp.getString("DONGIAVIET");
					String tygia = rsSp.getString("TYGIAQUYDOI");
					String ngaychungtu=rsSp.getString("NGAYCHUYEN");
					String ngaychot=rsSp.getString("NGAYCHUYEN");
					String NGAYHETHAN=rsSp.getString("NGAYHETHAN");
					String solo=rsSp.getString("SOLO");
					String khonhan_fk_=rsSp.getString("khonhan_fk");
					String khoxuat_fk_=rsSp.getString("khoxuat_fk");
					
					String noidungnhan_fk=rsSp.getString("NOIDUNGNHAN_FK");
					
					query = " select ( select pk_seq from ERP_TaiKhoanKT where sohieutaikhoan = b.taikhoankt_fk and CONGTY_FK="+ctyId+") as  taikhoankt_fk  ,  " +
							" ( select pk_seq from ERP_TaiKhoanKT where sohieutaikhoan = '15100000' AND CONGTY_FK = "+ctyId+" ) as taikhoanCO_FK " +
					        " from SANPHAM a inner join ERP_LOAISANPHAM b on a.LOAISANPHAM_FK = b.pk_seq " +
					        " where a.pk_seq = '" + taisan_fk + "'";
					
					
					System.out.println("__Lay TK Ke toan: " + query);
					
					ResultSet rsTKNo = db.get(query);
					String taikhoanNo = "";
					String taikhoanco_fk="";
					if(rsTKNo.next())
					{
						taikhoanNo = rsTKNo.getString("taikhoankt_fk");
						taikhoanco_fk = rsTKNo.getString("taikhoanCO_FK");
					}
					rsTKNo.close();
					
					query=  " update ERP_CHUYENKHO_SP_NHANHANG SET GIACHAYKT="+ rsSp.getDouble("DONGIA")+" , taikhoanktCO= "+taikhoanco_fk+" ,taikhoanktNo ="+taikhoanNo+" " +
							" where SANPHAM_FK="+ taisan_fk+" AND chuyenkho_fk="+ycnlCurrent+" AND SOLO='"+solo+"' ";
					if(db.updateReturnInt(query)!=1){
						db.getConnection().rollback();
						return "Lỗi dòng lệnh :"+query;
					}	
					// 

					if(taikhoanNo.trim().length() <= 0 )
					{
						rsSp.close();
						db.getConnection().rollback();
						db.shutDown();
						return "Sản phẩm trong nhận hàng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nến.";
					}
					
					String nam = ngaychot.substring(0, 4);
					String thang = ngaychot.substring(5, 7);
					double thanhtien = Double.parseDouble(dongia) * Double.parseDouble(soluongNhan);
					msg = util.Update_TaiKhoan_DienGiai_SP_TheoLo( db, thang, nam, ngaychungtu, ngaychot, "Chuyển kho ký gửi nhà cung cấp", ycnlCurrent, taikhoanNo, taikhoanco_fk, noidungnhan_fk, 
							Double.toString(thanhtien), Double.toString(thanhtien), "Sản phẩm", taisan_fk, "Sản phẩm", taisan_fk, "0", soluongNhan, dongiaViet, tiente, dongia, tygia, dongiaViet + "*" + soluongNhan, dongia + "*" + soluongNhan, "" ,"","",masp,tensp,donvi,solo,NGAYHETHAN,"NULL",khoxuat_fk_,khonhan_fk_);
					if(msg.trim().length() > 0)
					{
						rsSp.close();
						db.getConnection().rollback();
						db.shutDown();
						return msg;
					}		
				}
				rsSp.close();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "Lỗi trong quá trình tạo phiếu Chuyển kho tự động";
			
		}
		
		
		return msg;
	}

	public boolean CheckDahoantat(dbutils db, String idnhanhang )
	{		
		String query=
			 " SELECT DATA.SANPHAM_FK,DATA.SOLUONG,DATA.SOLUONGDANHAN FROM ( "+ 
			 " SELECT MHSP.SANPHAM_FK,MHSP.SOLUONG ,(SELECT SUM( NHSP.SOLUONGNHAN ) FROM ERP_NHANHANG NH  "+ 
			 " INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NH.PK_SEQ=NHSP.NHANHANG_FK "+ 
			 " WHERE NH.MUAHANG_FK=MH.PK_SEQ AND NHSP.SANPHAM_FK=MHSP.SANPHAM_FK AND NH.TRANGTHAI  IN (1,2) )  AS SOLUONGDANHAN "+ 
			 " FROM ERP_MUAHANG MH  "+ 
			 " INNER JOIN ERP_MUAHANG_SP MHSP ON MH.PK_SEQ=MHSP.MUAHANG_FK "+ 
			 " WHERE MH.PK_SEQ= (select MUAHANG_FK from ERP_NHANHANG where PK_SEQ= "+idnhanhang+")  "+ 
			 " ) DATA WHERE DATA.SOLUONG-DATA.SOLUONGDANHAN >0";
		
		
		ResultSet  kt = db.get(query);
		try{
			 
			if(kt.next()){
				//dang con du lieu co the nhan
				kt.close();
				return false;
			}
			kt.close();
			
		}catch(Exception e){
			e.printStackTrace();
			return false;
			
		}
		return true;
	}
			
		
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IErpNhanhangList_Giay obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    String loaimh = util.antiSQLInspection(request.getParameter("loaimh")); 
	    System.out.println("Loai "+loaimh);
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpNhanhang_Giay nhBean = new ErpNhanhang_Giay();
	    	nhBean.setUserId(userId);
	    	nhBean.setCongtyId((String)session.getAttribute("congtyId"));
	    	nhBean.setLoaimh(loaimh);
	    	nhBean.setLdnId("100000");
	    	nhBean.createRs();
    		
	    	
	    	
	    	session.setAttribute("nhBean", nhBean);
	    	session.setAttribute("spList", "");
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhanHangNew_Giay.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpNhanhangList_Giay();
	    		obj.setUserId(userId);
	    		obj.setCongtyId((String)session.getAttribute("congtyId"));
	    		obj.setLoaimh(loaimh);
		    	this.getSearchQuery(request, obj);
		    	
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	
		    	obj.init("");
		    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpNhanHang_Giay.jsp");	
		    }
	    	else
	    	{
		    	obj = new ErpNhanhangList_Giay();
		    	obj.setUserId(userId);
		    	obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	obj.setLoaimh(loaimh);
		    	this.getSearchQuery(request, obj);
		    	obj.init("");
		    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpNhanHang_Giay.jsp");
	    	}
	    }
	}
	
	private void getSearchQuery(HttpServletRequest request, IErpNhanhangList_Giay obj)
	{
		Utility util=new Utility();
//		String query = " SELECT distinct a.PK_SEQ as nhId, ncc.Ten as nccTen, \n"+
//				" isnull(case when a.hdNCC_fk is null then isnull(a.SOHOADON,'') else ( select isnull(kyhieu,'') + ' ' + CAST(sohoadon as varchar(50)) as ten  from ERP_HOADONNCC where pk_seq = a.hdNCC_fk ) end, '') as SOHOADON, a.NGAYNHAN, b.TEN as dvthTen, \n"+
//				" c.sopo as PoId, \n"+
//				" b.PREFIX + a.PREFIX + CAST(a.PK_SEQ as varchar(20)) as SOCHUNGTU, \n"+
//				" a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO, d.TEN as nguoitao, e.TEN as nguoisua, a.loaihanghoa_fk, \n"+
//				" case when a.hdNCC_fk IS null then 0  when a.hdNCC_fk IS NOt null then 1  else -1 end DaRaHd, a.tooltip \n"+
//				" FROM erp_nhanhang a "+
//				" inner join ERP_DONVITHUCHIEN b on a.DONVITHUCHIEN_FK = b.PK_SEQ \n"+
//				" left join DonTraHang th on a.TRAHANG_FK = th.PK_SEQ \n"+
//				" inner join NHANVIEN d on a.NGUOITAO = d.PK_SEQ  \n"+
//				" inner join NHANVIEN e on a.NGUOISUA = e.PK_SEQ  \n"+
//				" inner join ERP_MUAHANG c on  a.muahang_fk= c.pk_seq \n"+  
//				" INNER JOIN ERP_HOADONNCC f ON A.HDNCC_FK = f.PK_SEQ \n" +
//				" INNER JOIN ERP_PARK g ON f.PARK_FK = g.PK_SEQ  \n" +  
//				" left join ERP_NHACUNGCAP ncc on g.NCC_FK = ncc.pk_seq   \n"+
//		 " where c.LOAI = "+ obj.getLoaimh() +" and a.congty_fk = '" + obj.getCongtyId() + "' and c.SOPO is not null  ";
//		
		 		//" and b.pk_seq in  "+ util.quyen_donvithuchien(obj.getUserId()) ; 		
		
		String khonhanId = request.getParameter("khonhanId");
		if( khonhanId == null){
			khonhanId = "";
		}
		obj.setKhonhanId(khonhanId);
		
		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String dvthId = request.getParameter("dvth");
		if(dvthId == null)
			dvthId = "";
		obj.setDvthId(dvthId);
		
		String soPo = request.getParameter("sopo");
		if(soPo == null)
			soPo = "";
		obj.setSoPO(soPo);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String sonhanhang = request.getParameter("sonhanhang");
		if(sonhanhang == null)
			sonhanhang = "";
		obj.setSoNhanhang(sonhanhang);
		
		String sohoadon = request.getParameter("sohoadon");
		if(sohoadon == null)
			sohoadon = "";
		obj.setSoHoadon(sohoadon);
		
		String ncc = request.getParameter("ncc");
		if(ncc == null)
			ncc = "";
		obj.setNCC(ncc);
		
		String nguoitao = request.getParameter("nguoitao");
		if(nguoitao == null)
			nguoitao = "";
		obj.setNguoitaoIds(nguoitao);
		
		String mactsp = request.getParameter("mactsp");
		if(mactsp == null)
			mactsp = "";
		obj.setMaCtSp(mactsp);
		String soItem = request.getParameter("soItems");
		if(soItem == null)
			soItem = "25";
		int soItems = Integer.parseInt(soItem);
		obj.setSoItems(soItems);
		
//		if( khonhanId.length() >0){
//			query +=" and a.khonhan_fk ='"+ khonhanId+"'";
//		}
//		if(tungay.length() > 0)
//			query += " and a.ngaynhan >= '" + tungay + "'";
//		
//		if(denngay.length() > 0)
//			query += " and a.ngaynhan <= '" + denngay + "'";
//		
//		if(dvthId.length() > 0)
//			query += " and b.pk_seq = '" + dvthId + "'";
//		
//		if(nguoitao.trim().length() > 0)
//			query += " AND a.nguoitao = '" + nguoitao + "' ";
//		
//		
//		if(soPo.length() > 0)
//		{
//			//query += " and ( CAST(c.PK_SEQ as varchar(10)) + '/' + SUBSTRING(c.NGAYMUA, 6, 2) + '/' + SUBSTRING(c.NGAYMUA, 0, 5) like '%" + soPo + "%' ) ";
//		
//			query += "  AND ( c.sopo like '%" + soPo.trim() + "%' ) ";
//		}
//		
//		if(trangthai.length() > 0)
//			query += " and a.trangthai = '" + trangthai + "'";
//		
//		if(sonhanhang.trim().length() > 0)
//		{
//			query += " and b.PREFIX + a.PREFIX + CAST(a.PK_SEQ as varchar(20)) like N'%" + sonhanhang + "%'  ";
//		}
//		
//		if(sohoadon.trim().length() > 0)
//		{
//			query += " and ( select isnull(kyhieu,'') + ' ' + CAST(sohoadon as varchar(50)) as ten  from ERP_HOADONNCC where pk_seq = a.hdNCC_fk ) like N'%" + sohoadon + "%' ";
//		}
//		
//		if(ncc.trim().length() > 0)
//		{
//			query += " and (  ( ( c.pk_seq is not null ) and ( c.NHACUNGCAP_FK in ( select pk_seq from ERP_NHACUNGCAP where ma like N'%" + ncc + "%' or ten like N'%" + ncc + "%' ) ) )              " +
//					 "     or ( ( th.pk_seq is not null ) and ( th.KHACHHANG_FK in  ( select pk_seq from ERP_KHACHHANG where ma like N'%" + ncc + "%' and ten like N'%" + ncc + "%'  )  ) ) )   ";
//		}
//		
//		if(mactsp.trim().length() > 0)
//		{
//			query +=" and a.pk_seq in (" +
//					"     select distinct nhanhang_fk from erp_nhanhang_sanpham where sanpham_fk in ( select distinct pk_seq from SANPHAM where pk_seq =" + mactsp + ") " +
//					" ) ";
//		}
//		
//		//query += " order by a.NGAYNHAN desc, a.trangthai asc, a.pk_seq desc;";
//
//		System.out.println(query);
//		return query;
	}
	
	private String Delete(String nhId, dbutils db)
	{
		try 
		{
			String msg = "";
			String query = "";
			ResultSet rs;
			
			/*//Xóa nhừng nhận hàng tự động được sinh ra từ nhận hàng đang xóa
			query = "select pk_seq from ERP_NHANHANG where nhanhanggoc_fk = '" + nhId + "'";
			System.out.println("0. " + query);
			rs = db.get(query);
			if(rs.next())
			{
				msg = Delete(rs.getString("pk_seq"), db);
				if(msg.length() > 0)
					return msg;
			}
			*/
			// Lấy thông tin nhận hàng cần xóa
			query = "select isnull(istudong,0) istudong, trangthai, nhanhanggoc_fk, khonhan_fk, khachhangkygui_fk, NHAPHANPHOI_FK, NCC_KH_FK, NHOMKENH_FK "+
					"from ERP_NHANHANG where pk_seq = '" + nhId + "'";
			System.out.println("1. " + query);
			rs = db.get(query);
			int istudong = 0;
			String trangthai = "", nhgoc = "", khonhan = "", khachhangkygui = "", npp = "", ncc = "", nhomkenh = "";
			
			if(rs!= null){
				while(rs.next()){
					istudong = rs.getInt("istudong");
					trangthai = rs.getString("trangthai");
					nhgoc = rs.getString("nhanhanggoc_fk");
					khonhan = rs.getString("nhanhanggoc_fk");
					khachhangkygui = rs.getString("khachhangkygui_fk")==null?"":rs.getString("khachhangkygui_fk");
					npp = rs.getString("NHAPHANPHOI_FK")==null?"":rs.getString("NHAPHANPHOI_FK");
					ncc = rs.getString("NCC_KH_FK")==null?"":rs.getString("NCC_KH_FK");
					nhomkenh = rs.getString("NHOMKENH_FK")==null?"100000":rs.getString("NHOMKENH_FK");
				}rs.close();
					
			}
			
			// Nhận hàng tự động khi xóa phải tăng kho Ký gửi ncc, trừ kho nhận
			if(istudong > 0)
			{
				if(trangthai.equals("1"))
				{
					String spid = "", solo = "", soluong = "", ngayhethan = ""; 
					query = "select SANPHAM_FK, SOLO, SOLUONG, NGAYHETHAN from ERP_NHANHANG_SP_CHITIET where NHANHANG_FK = "+nhId;
					System.out.println("2. " + query);
					rs = db.get(query);
					if(rs!= null){
						while(rs.next()){
							spid = rs.getString("sanpham_fk");
							solo = rs.getString("solo");
							soluong = rs.getString("soluong");
							ngayhethan = rs.getString("ngayhethan");
							
							//kiểm tra kho nhận xem còn hàng để trả lại k?
							if(khachhangkygui.trim().length() > 0)
							{
								query = 
								"select soluong, booked, available from nhapp_kho_kygui_chitiet "+
								"where kho_fk = "+khonhan+" and npp_fk = "+npp+" and nhomkenh_fk = "+nhomkenh+" "+
								"and sanpham_fk = "+spid+" and solo = '"+solo+"' and ngayhethan = '"+ngayhethan+"' and khachhang_fk = "+khachhangkygui;
							}
							else
							{
								query = 
								"select soluong, booked, available from nhapp_kho_chitiet "+
								"where kho_fk = "+khonhan+" and npp_fk = "+npp+" and nhomkenh_fk = "+nhomkenh+" "+
								"and sanpham_fk = "+spid+" and solo = '"+solo+"' and ngayhethan = '"+ngayhethan+"'";
							}
							
						}rs.close();
							
					}
				}
				
				return "Phiếu nhận hàng này tự động phát sinh. Bạn không được xóa ";
			}
				
			query = "update ERP_NHANHANG set trangthai = '3' where pk_seq = '" + nhId + "' and trangthai=0";
			if(!db.update(query))
			{
				return "Không thể cập nhật ERP_NHANHANG: " + query;
			}
			
			
			query="update ERP_PARK set HOANTAT_NHANHANG='0' where pk_seq=(select park_fk from erp_hoadonncc where pk_Seq= (select hdncc_fk from erp_nhanhang where pk_Seq="+nhId+")) ";
			if(!db.update(query)){
				db.getConnection().rollback();
				return "không thể cập nhật HOANTAT_NHANHANG "+query;
			}
			
			
			
			return "";
		} 
		catch (Exception e)
		{ 
			return "Khong the xoa don mua hang"; 
		}
		
	}
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	private String getTime()
	{
		Date date = new Date();
	    SimpleDateFormat simpDate;

	    //format 24h
	    simpDate = new SimpleDateFormat("kk:mm:ss");
	    
	    return simpDate.format(date);
	}
	
	private String Tinh_ChiPhiLuuKho(String nhId, String userId, String ctyId, dbutils db)
	{
		String msg = "";
		try 
		{
			// KIỂM TRA XEM NHẬN HÀNG LÀ NHẬP KHẨU HAY TRONG NƯỚC
			
			String query = "SELECT distinct HD.LOAIHD "
						+  "FROM ERP_NHANHANG NH INNER JOIN ERP_HOADONNCC HD ON NH.hdNCC_fk = HD.pk_seq "
						+  "WHERE NH.PK_SEQ = "+nhId;
			
			System.out.println(query);
			ResultSet rs = db.get(query);
			
			String loaid = "";
			try 
			{
				if(rs.next())
				{
					loaid = rs.getString("LOAIHD");
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();			
			}
			
			String sql ="";
			if(loaid.equals("0"))// NHẬP KHẨU
			{
				sql = "select distinct NH.PK_SEQ NHANHANG_FK,NH.NGAYNHAN NGAYNHANHANG \n"+
					  "		, NK.NGAYCHOT NGAYNHAPKHO, NHSP.SANPHAM_FK, \n"+ 
					  "	   NHSP.SOLO, NHSP.SOLUONG, NKSP.SONGAYLUUKHO, (DATEDIFF (day, NK.NGAYNHAP, NH.NGAYNHAN) - NKSP.SONGAYLUUKHO) SONGAYLUUKHOTINH, 1 DONGIA, NHSP.MUAHANG_FK \n"+
					  "	from ERP_NHANHANG NH INNER JOIN ERP_HOADONNCC HD ON NH.hdNCC_fk = HD.pk_seq \n"+
					  "	INNER JOIN ( SELECT distinct A.MUAHANG_FK, A.HOADONNCC_FK FROM ERP_HOADONNCC_DONMUAHANG A ) HD_NK ON HD.pk_seq = HD_NK.HOADONNCC_FK \n"+
					  "	INNER JOIN ERP_NHAPKHONHAPKHAU NK ON HD_NK.MUAHANG_FK = NK.PK_SEQ \n"+
					  " INNER JOIN ERP_NHAPKHONHAPKHAU_SANPHAM NKSP ON NK.PK_SEQ = NKSP.NHAPKHO_FK \n"+
					  "	INNER JOIN ERP_NHANHANG_SP_CHITIET NHSP ON NH.PK_SEQ = NHSP.NHANHANG_FK AND NKSP.SANPHAM_FK = NHSP.SANPHAM_FK \n"+
					  "	WHERE NH.PK_SEQ = "+nhId+" ";
				
				System.out.println(sql);
				
				ResultSet rs1 = db.get(sql);
				
				String ngaynhanhang = "";
				String ngaynhapkho = "";
				String sanphamId = "";
				String solo = "";
				String muahangId = "";
				
				double soluong = 0;
				double dongia = 0;
				String songayluukho = "";
				double songayluukhotinh = 0;
				
				double thanhtien = 0;
				try 
				{
					if(rs1.next())
					{
						ngaynhanhang = rs1.getString("NGAYNHANHANG");
						ngaynhapkho = rs1.getString("NGAYNHAPKHO");
						sanphamId = rs1.getString("SANPHAM_FK");
						solo = rs1.getString("SOLO");
						soluong = rs1.getDouble("SOLUONG");
						songayluukho = rs1.getString("SONGAYLUUKHO");
						songayluukhotinh = rs1.getDouble("SONGAYLUUKHOTINH");
						dongia = rs1.getDouble("DONGIA");
						muahangId = rs1.getString("MUAHANG_FK");
						thanhtien = soluong*songayluukhotinh*dongia;
						
						if(thanhtien>0)
						{
							String add = " INSERT ERP_CHIPHILUUKHO_SP_CHITIET (NHANHANG_FK, SANPHAM_FK, SOLO, SOLUONG, DONGIA, NGAYNHANHANG, NGAYNHAPKHO, SONGAYLUUKHO, THANHTIEN, MUAHANG_FK)  "	
										+" values ("+nhId+", "+sanphamId+",'"+solo+"', "+soluong+" , "+dongia+", '"+ngaynhanhang+"', '"+ngaynhapkho+"', '"+songayluukho+"', '"+thanhtien+"', "+muahangId+")";
							
							System.out.println("" + add);
	
							if (!db.update(add)) {
								msg = "Khong the tao moi Nhan hang: " + query;
								db.getConnection().rollback();
								return msg;
							}
							
						}
					}
					rs1.close();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();			
				}
				
				
			}
			
			if(loaid.equals("1"))// TRONG NƯỚC
			{
				sql =     "select A.PK_SEQ NHANHANG_FK,A.NGAYNHAN NGAYNHANHANG,D.NGAYNHAP NGAYNHAPKHO, \n"+
						  " NHSP.SANPHAM_FK, NHSP.SOLO, NHSP.SOLUONG, 0 SONGAYLUUKHO, (DATEDIFF (day,D.NGAYNHAP, A.NGAYNHAN)) SONGAYLUUKHOTINH, 1 DONGIA, NHSP.MUAHANG_FK \n"+
						  " from erp_nhanhang A INNER JOIN erp_phanbomuahang_po B ON A.MUAHANG_FK = B.poduocpb \n"+
						  " INNER JOIN erp_phanbomuahang C ON B.PHANBO_FK = C.PK_SEQ INNER JOIN ERP_NHAPKHONHAMAY D ON C.MUAHANG_FK = D.MUAHANG_FK \n"+
						  " INNER JOIN ERP_NHANHANG_SP_CHITIET NHSP ON A.PK_SEQ = NHSP.NHANHANG_FK \n"+ 
						  " WHERE A.PK_SEQ = "+nhId+" \n";
				System.out.println(sql);
				
				ResultSet rs2 = db.get(sql);	
				
				String ngaynhanhang = "";
				String ngaynhapkho = "";
				String sanphamId = "";
				String solo = "";
				String muahangId = "";
				double soluong = 0;
				double dongia = 0;
				String songayluukho = "";
				double songayluukhotinh = 0;
				
				double thanhtien = 0;
				try 
				{
					if(rs2.next())
					{
						ngaynhanhang = rs2.getString("NGAYNHANHANG");
						ngaynhapkho = rs2.getString("NGAYNHAPKHO");
						sanphamId = rs2.getString("SANPHAM_FK");
						solo = rs2.getString("SOLO");
						soluong = rs2.getDouble("SOLUONG");
						songayluukho = rs2.getString("SONGAYLUUKHO");
						songayluukhotinh = rs2.getDouble("SONGAYLUUKHOTINH");
						dongia = rs2.getDouble("DONGIA");
						muahangId = rs2.getString("MUAHANG_FK");
						thanhtien = soluong*songayluukhotinh*dongia;
						
						if(thanhtien>0)
						{
							String add = " INSERT ERP_CHIPHILUUKHO_SP_CHITIET (NHANHANG_FK, SANPHAM_FK, SOLO, SOLUONG, DONGIA, NGAYNHANHANG, NGAYNHAPKHO, SONGAYLUUKHO, THANHTIEN, MUAHANG_FK)  "	
										+" values ("+nhId+", "+sanphamId+",'"+solo+"', "+soluong+" , "+dongia+", '"+ngaynhanhang+"', '"+ngaynhapkho+"', '"+songayluukho+"', "+thanhtien+", "+muahangId+")";
							
							System.out.println("" + add);
	
							if (!db.update(add)) {
								msg = "Khong the tao moi Nhan hang: " + add;
								db.getConnection().rollback();
								return msg;
							}
							
						}
					}
					rs2.close();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();			
				}
				
			}
						
			return "";
				
		} 
		catch (Exception e)
		{ 
			db.shutDown(); 
			return "Khong the insert "; 
		}
		
	}
	/*
	private void taophieuyeucaukiemdinh(int sothung1,dbutils db,String manhanhang,String sanpham_fk,String khuvuc,String solo,String ngayhethan,String userId,String slthung,String ngaysx,String ngaynhandk,String khonhan_fk,String dongiaViet)
	{
		int i;
		String query="";
		for(i=1;i<=sothung1;i++)
		{
			System.out.println("muo luu loo ");
			query="insert into ERP_YeuCauKiemDinh(nhanhang_fk,sanpham_fk,khuvuckho_fk,solo,ngayhethan,trangthai,nguoitao,"
					+ "ngaytao,nguoisua,ngaysua,soluong,ngaysanxuat,"
					+ "ngaynhan,KHONHAN_FK,NGAYHETHONG,"
					+ "DonGiaViet)"
					+ "values('"+manhanhang+"','"+sanpham_fk+"','"+khuvuc+"','"+solo+"/"+i+"','"+ngayhethan+"','"+userId+"','"+s+"',"
					+ "'"+userId+"','"+s+"','"+slthung+"','"+ngaysx+"','"+ngaynhandukien+"','"+khonhan_fk_+"','"+s+"','"+dongiaViet+"',"
					+ ")";

			if(db.updateReturnInt(query)!=1){
				db.getConnection().rollback();
				return "Lỗi dòng lệnh :"+query;
			}	
		}
	}
	*/
}
