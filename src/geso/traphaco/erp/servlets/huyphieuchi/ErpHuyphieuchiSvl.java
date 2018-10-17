package geso.traphaco.erp.servlets.huyphieuchi;

import geso.traphaco.center.db.sql.Idbutils;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.huyphieuchi.IErpHuyphieuchi;
import geso.traphaco.erp.beans.huyphieuchi.IErpHuyphieuchiList;
import geso.traphaco.erp.beans.huyphieuchi.imp.ErpHuyphieuchi;
import geso.traphaco.erp.beans.huyphieuchi.imp.ErpHuyphieuchiList;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpHuyphieuchiSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpHuyphieuchiSvl() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpHuyphieuchiList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    String ServerletName = this.getServletName();	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	   
	    String querystring = request.getQueryString();
	    System.out.println(querystring);
	    String userId = util.getUserId(querystring);
	 
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    
	    String hctmhId = util.getId(querystring);
	    
	    
	    
	    if (action.equals("delete"))
	    {	
	    	obj = new ErpHuyphieuchiList();
		    obj.setCongtyId((String)session.getAttribute("congtyId"));
		    obj.setUserId(userId);
		    obj.setnppdangnhap(util.getIdNhapp(userId));
		    
		    String searchQuery=util.getSearchFromHM(userId,ServerletName, session);
			GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
	    	String msg = Delete(hctmhId);
	    	if(msg.length() > 0)
	    		obj.setMsg(msg);
	    	
	    	  
		    obj.init("");
		    
			session.setAttribute("obj", obj);
					
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuyPhieuChi.jsp";
			response.sendRedirect(nextJSP);
	    } else if (action.equals("chot"))
	    {		    	
	    	//KIỂM TRA LOẠI CHỨNG TỪ
	    	obj = new ErpHuyphieuchiList();
	  	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	  	    obj.setUserId(userId);
	  	    obj.setnppdangnhap(util.getIdNhapp(userId));
	    	String loaichungtu = util.getLoai(querystring);
	    	String id = util.getId(querystring);
	    	String sochungtu = util.getSoChungTu(querystring);
	    	String msg = ChotHPC(id,loaichungtu,sochungtu);
	    	
	    	String searchQuery=util.getSearchFromHM(userId,ServerletName, session);
			GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
	    	if(msg.length() > 0)
	    		obj.setMsg(msg);
	    	
	    	obj.init("");
		    
			session.setAttribute("obj", obj);
					
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuyPhieuChi.jsp";
			response.sendRedirect(nextJSP);
	    }else{
	    
	    	obj = new ErpHuyphieuchiList();
		    obj.setCongtyId((String)session.getAttribute("congtyId"));
		    obj.setUserId(userId);
		    obj.setnppdangnhap(util.getIdNhapp(userId));
		    
		    obj.init("");
		    util.setSearchToHM(userId, session, ServerletName, "");
			session.setAttribute("obj", obj);
					
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuyPhieuChi.jsp";
			response.sendRedirect(nextJSP);
	    }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpHuyphieuchiList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");

	    String ServerletName = this.getServletName();
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    
	    
	    

		
		
	    if(action.equals("Tao moi"))
	    {
	    	IErpHuyphieuchi hctmhBean = new ErpHuyphieuchi();
	    	hctmhBean.setCongtyId((String)session.getAttribute("congtyId"));
	    	hctmhBean.setUserId(userId);
	    	hctmhBean.setnppdangnhap(util.getIdNhapp(userId));
	    	
	    	hctmhBean.createRs();
    		
	    	session.setAttribute("hctmhBean", hctmhBean);
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuyPhieuChiNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpHuyphieuchiList();
	    		obj.setCongtyId((String)session.getAttribute("congtyId"));
	    		obj.setUserId(userId);
	    		obj.setnppdangnhap(util.getIdNhapp(userId));
	    		
		    	this.getSearchQuery(request, obj);
		    	
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setUserId(userId);
		    	obj.init("");
		    	
		    	String querySearch = GiuDieuKienLoc.createParams(obj);
				util.setSearchToHM(userId, session,ServerletName, querySearch);
				
		    	
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpHuyPhieuChi.jsp");	
		    }
	    	else
	    	{
		    	obj = new ErpHuyphieuchiList();
		    	obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	
		    	this.getSearchQuery(request, obj);		    	
				obj.setCongtyId((String)session.getAttribute("congtyId"));
	    		obj.setUserId(userId);
	    		obj.setnppdangnhap(util.getIdNhapp(userId));
	    		
	    		obj.init("");
	    		
	    		String querySearch = GiuDieuKienLoc.createParams(obj);
				util.setSearchToHM(userId, session,ServerletName, querySearch);
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpHuyPhieuChi.jsp");
	    	}
	    }
	}
	
	private void getSearchQuery(HttpServletRequest request, IErpHuyphieuchiList obj)
	{
		//LOAICHUNGTU = 2: HỦY PHIẾU CHI
		//LOAICHUNGTU = 3: HỦY HÓA ĐƠN NCC
		//LOAICHUNGTU = 1: HỦY PHIẾU THU
		
//		String query = "SELECT a.PK_SEQ as SOPHIEU, a.SOCHUNGTU, case a.LOAICHUNGTU when 2 then N'PHIẾU CHI' else N'HÓA ĐƠN NCC' end as LOAICHUNGTU ,a.TRANGTHAI, a.NGAYTAO, a.NGAYSUA, b.TEN as NGUOITAO, c.TEN as NGUOISUA, a.LOAICHUNGTU LOAI \n " +
//					   "FROM ERP_HUYCHUNGTUKETOAN a inner join NHANVIEN b on a.nguoitao = b.pk_seq inner join NHANVIEN c on a.nguoisua = c.pk_seq \n " +
//					   "where a.congty_fk = '" + obj.getCongtyId() + "' and a.LOAICHUNGTU IN (2,3) and  a.NPP_FK = "+obj.getnppdangnhap()+" ";
//		
		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String nguoitao = request.getParameter("nguoitao");
		if(nguoitao == null)
			nguoitao = "";
		obj.setNguoitao(nguoitao);
		
		String sochungtu = request.getParameter("sochungtu");
		if(sochungtu == null)
			sochungtu = "";
		obj.setsochungtu(sochungtu);
	
		
//		if(tungay.length() > 0)
//			query += " and a.ngaytao >= '" + tungay + "'";
//		
//		if(denngay.length() > 0)
//			query += " and a.ngaysua <= '" + denngay + "'";
//		
//		if(trangthai.length() > 0)
//			query += " and a.trangthai = '" + trangthai + "'";
//		
//		if(nguoitao.length() > 0)
//			query += " and b.TEN like N'%" + nguoitao + "%'";
//		
//		if(sochungtu.length() > 0)
//			query += " and a.SOCHUNGTU like '%" + sochungtu + "%'";
//		
//		
//		return query;
	}
	
	private String Delete(String hctmhId)
	{
		dbutils db = new dbutils();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			db.update("update ERP_HUYCHUNGTUMUAHANG set trangthai = '2' where pk_seq = '" + hctmhId + "'");
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			return "";
		} 
		catch (SQLException e)
		{ 
			db.shutDown(); 
			return "Khong the xoa huy chung tu mua hang"; 
		}
		
	}
	
	
	private String ChotHPC(String Id, String loaict, String sochungtu)
	{
		dbutils db = new dbutils();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "";
			query ="UPDATE ERP_HUYCHUNGTUKETOAN SET TRANGTHAI = 1 WHERE PK_SEQ ='"+Id+"'";
			
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return  "Khong the huy phieu chi ERP_THANHTOANHOADON, " + query;
			}
			
			if(loaict.equals("2")){ 
				//CẬP NHẬT TRẠNG THÁI HỦY CHO PHIẾU CHI
				query ="UPDATE ERP_THANHTOANHOADON SET TRANGTHAI = 3 WHERE PK_SEQ ='"+sochungtu+"'";
				
				System.out.println(query);
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return  "Khong the huy phieu chi ERP_THANHTOANHOADON, " + query;					 
					
				}
				
				//NHẢ KẾ TOÁN LẠI KHI HỦY PHIẾU THU TIỀN
				
				String tb = Revert_KeToan_HuyPhieuChi(db,sochungtu);
				if(tb.length()>0) 
				{
					db.getConnection().rollback();
					return tb;
				}
				
			}				
			
			if(loaict.equals("3")){// CẬP NHẬT TRẠNG THÁI HỦY CHO HÓA ĐƠN NCC
				//HÓA ĐƠN NCC ĐƯỢC LƯU 2 BẢNG: ERP_PARK VÀ ERP_HOADONNCC
				
				query ="UPDATE ERP_PARK SET TRANGTHAI = 4 WHERE PK_SEQ ='"+sochungtu+"'";
				
				System.out.println(query);
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return  "Khong the huy phieu chi ERP_THANHTOANHOADON, " + query;					 
					
				}
												
				String tb = Revert_KeToan_HuyHoaDonNCC(db,sochungtu);
				if(tb.length()>0) 
				{
					db.getConnection().rollback();
					return tb;
					
				}
				
			}
						
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			return "";
		} 
		catch (SQLException e)
		{ 
			db.shutDown(); 
			return "Khong the xoa chot"; 
		}
		
	}
	
	
	private String Revert_KeToan_HuyPhieuChi(Idbutils db,String sochungtu)
	{	
		
		//sochungtu = sochungtu.substring(3, sochungtu.length());
		String msg = "";
		try{
			String query = "";					
			
			String	colNAME = "ngayghinhan";;
			String	tableNAME = "ERP_THANHTOANHOADON";
					
			query = " SELECT " + colNAME + " as ngaynghiepvu from " + tableNAME + " where pk_seq = '" + sochungtu + "' ";
			System.out.println("____LAY NGAY NGHIEP VU: " + query);
			
			ResultSet ngayRS = db.get(query);
			String ngaynghiepvu = "";
					
			if(ngayRS.next())
			{
				ngaynghiepvu = ngayRS.getString("ngaynghiepvu");
			}
			ngayRS.close();
			
			String nam = ngaynghiepvu.substring(0, 4);
			String thang = ngaynghiepvu.substring(5, 7);
			
			//CHECK NGAY GHI NHAN REVERT CO HOP LE HAY KHONG (CHI DUOC THUC HIEN TRONG THANG KHOA SO CONG 1)
			query = "select THANGKS, NAM from ERP_KHOASOKETOAN order by NAM desc, THANGKS desc";
			System.out.println("1.CHECK NO-CO: " + query);
					
			String thangKS = "1";
			String namKS = "2013";
			ResultSet rsCheck = db.get(query);
			if(rsCheck != null)
			{
				try 
				{
					if(rsCheck.next())
					{
						thangKS = rsCheck.getString("THANGKS");
						namKS = rsCheck.getString("NAM");
					}
					rsCheck.close();
				} 
				catch (Exception e) {}
			}
					
			String thangHopLe = "";
			String namHopLe = "";
			if(Integer.parseInt(thangKS) == 12 )
			{
				thangHopLe =  "1";
				namHopLe = Integer.toString( Integer.parseInt(namKS)  + 1);
			}
			else
			{
				thangHopLe =  Integer.toString(Integer.parseInt(thangKS) + 1);
				namHopLe = namKS;
			}
			// CẬP NHẬT HỦY TRẠNG THÁI THANH TOÁN CỦA ĐỀ NGHỊ TẠM ỨNG
			msg = capNhatHuyHoaDon(db, sochungtu, "ERP_TAMUNG", "1");
			if (msg.length() > 0) {
				return "Lỗi cập nhật HỦY trạng thái thanh toán của đề nghị tạm ứng : " + msg;
			}

			// CẬP NHẬT HỦY TRẠNG THÁI THANH TOÁN CỦA ĐỀ NGHỊ THANH TOÁN
			msg = capNhatHuyHoaDon(db, sochungtu, "ERP_MUAHANG", "6");
			if (msg.length() > 0) {
				return "Lỗi cập nhật HỦY trạng thái thanh toán của đề nghị thanh toán : " + msg;
			}
					
			String	loaict = "Thanh toán hóa đơn";
			
			//GHI NHAN NGUOC LAI TAI KHOAN NO - CO
			query = "select SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NO, CO, TIENTEGOC_FK, TONGGIATRINT  " +
				    "from ERP_PHATSINHKETOAN " +
				    "where LOAICHUNGTU = N'"+loaict+"' and SOCHUNGTU = '" + sochungtu + "' ";
			
			System.out.println("1.CHECK NO-CO: " + query);
					
					
			ResultSet rsPSKT = db.get(query);
			try 
			{
				while(rsPSKT.next())
				{
					String taikhoan_fk = rsPSKT.getString("TAIKHOAN_FK");
					String tiente_fk = rsPSKT.getString("TIENTEGOC_FK");
					double NO = rsPSKT.getDouble("NO");
					double CO = rsPSKT.getDouble("CO");
					double TONGGIATRINT = rsPSKT.getDouble("TONGGIATRINT");
					
					//NEU LA CO THI BAY GIO GHI GIAM CO LAI
					if( NO > 0 )
					{
						query = " update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND - " + NO + ", GIATRINONGUYENTE = GIATRINONGUYENTE - " + TONGGIATRINT + "  " +
								" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
					}
					else
					{
						query = " update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND - " + CO + ", GIATRICONGUYENTE = GIATRICONGUYENTE - " + TONGGIATRINT + "  " +
								" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
					}
					
					System.out.println("____LAY NGAY NGHIEP VU: " + query);
					
					if(!db.update(query))
					{
						msg =  "2.Lỗi REVERT: " + query;
						return msg;
					}
					
				}
				rsPSKT.close();
			} 
			catch (Exception e) { }
					
			//GHI NHAN LOG NGUOC LAI
			query = " INSERT ERP_PHATSINHKETOAN( NGAYCHUNGTU, NGAYGHINHAN, LOAICHUNGTU, SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NOIDUNGNHAPXUAT_FK, NO, CO, DOITUONG, MADOITUONG, LOAIDOITUONG, " +
					"							 SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC )  " +
					" 							 SELECT NGAYCHUNGTU, '" + ngaynghiepvu + "', N'Hủy_' + LOAICHUNGTU as LOAICHUNGTU, SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NOIDUNGNHAPXUAT_FK, NO*(-1), CO*(-1), DOITUONG, MADOITUONG, LOAIDOITUONG,  " +
					"							 SOLUONG, (-1)*isnull(DONGIA,0) DONGIA, TIENTEGOC_FK, isnull(DONGIANT,0)*(-1), TIGIA_FK, ISNULL(TONGGIATRI,0)*(-1), ISNULL(TONGGIATRINT,0)*(-1), KHOANMUC  " +
					" 							 FROM ERP_PHATSINHKETOAN  " +
					" 							 WHERE LOAICHUNGTU = N'"+loaict+"' and SOCHUNGTU = '" + sochungtu + "' ";
			
			System.out.println("____LAY NGAY NGHIEP VU: " + query);
			
			if(!db.update(query))
			{
				msg =  "2.Lỗi REVERT: " + query;
				return msg;
			}
		
			// NEU checkThanhtoantuTV <= 0 
			loaict = "Trả khác";
			
			//GHI NHAN NGUOC LAI TAI KHOAN NO - CO
			query = "select SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NO, CO, TIENTEGOC_FK, TONGGIATRINT  " +
				    "from ERP_PHATSINHKETOAN " +
				    "where LOAICHUNGTU = N'"+loaict+"' and SOCHUNGTU = '" + sochungtu + "' ";
			
			System.out.println("1.CHECK NO-CO: " + query);
							
							
			rsPSKT = db.get(query);
			try 
			{
				while(rsPSKT.next())
				{
					String taikhoan_fk = rsPSKT.getString("TAIKHOAN_FK");
					String tiente_fk = rsPSKT.getString("TIENTEGOC_FK");
					double NO = rsPSKT.getDouble("NO");
					double CO = rsPSKT.getDouble("CO");
					double TONGGIATRINT = rsPSKT.getDouble("TONGGIATRINT");
					
					//NEU LA CO THI BAY GIO GHI GIAM CO LAI
					if( NO > 0 )
					{
						query = " update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND - " + NO + ", GIATRINONGUYENTE = GIATRINONGUYENTE - " + TONGGIATRINT + "  " +
								" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
					}
					else
					{
						query = " update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND - " + CO + ", GIATRICONGUYENTE = GIATRICONGUYENTE - " + TONGGIATRINT + "  " +
								" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
					}
					
					System.out.println("____LAY NGAY NGHIEP VU: " + query);
					
					if(!db.update(query))
					{
						msg =  "2.Lỗi REVERT: " + query;
						return msg;
					}
					
				}
				rsPSKT.close();
			} 
			catch (Exception e) { }
							
			//GHI NHAN LOG NGUOC LAI
			query = " INSERT ERP_PHATSINHKETOAN ( NGAYCHUNGTU, NGAYGHINHAN, LOAICHUNGTU, SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NOIDUNGNHAPXUAT_FK, NO, CO, DOITUONG, MADOITUONG, LOAIDOITUONG, " +
					"							  SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC )  " +
					" 							  SELECT NGAYCHUNGTU, '" + ngaynghiepvu + "', N'Hủy_' + LOAICHUNGTU as LOAICHUNGTU, SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NOIDUNGNHAPXUAT_FK, NO*(-1), CO*(-1), DOITUONG, MADOITUONG, LOAIDOITUONG,  " +
					"							  SOLUONG, (-1)*isnull(DONGIA,0) DONGIA, TIENTEGOC_FK, isnull(DONGIANT,0)*(-1), TIGIA_FK, ISNULL(TONGGIATRI,0)*(-1), ISNULL(TONGGIATRINT,0)*(-1), KHOANMUC  " +
					" 							  FROM ERP_PHATSINHKETOAN  " +
					" 							  WHERE LOAICHUNGTU = N'"+loaict+"' and SOCHUNGTU = '" + sochungtu + "' ";
			
			System.out.println("____LAY NGAY NGHIEP VU: " + query);
			
			if(!db.update(query))
			{
				msg =  "2.Lỗi REVERT: " + query;
				return msg;
			}
					
					
			// NEU checkThanhtoantuTV <= 0 
			loaict = "Thanh toán Thuế nhập khẩu";
			
			//GHI NHAN NGUOC LAI TAI KHOAN NO - CO
			query = "select SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NO, CO, TIENTEGOC_FK, TONGGIATRINT  " +
				    "from ERP_PHATSINHKETOAN " +
				    "where LOAICHUNGTU = N'"+loaict+"' and SOCHUNGTU = '" + sochungtu + "' ";
			
			System.out.println("1.CHECK NO-CO: " + query);
					
					
			rsPSKT = db.get(query);
			try 
			{
				while(rsPSKT.next())
				{
					String taikhoan_fk = rsPSKT.getString("TAIKHOAN_FK");
					String tiente_fk = rsPSKT.getString("TIENTEGOC_FK");
					double NO = rsPSKT.getDouble("NO");
					double CO = rsPSKT.getDouble("CO");
					double TONGGIATRINT = rsPSKT.getDouble("TONGGIATRINT");
					
					//NEU LA CO THI BAY GIO GHI GIAM CO LAI
					if( NO > 0 )
					{
						query = " update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND - " + NO + ", GIATRINONGUYENTE = GIATRINONGUYENTE - " + TONGGIATRINT + "  " +
								" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
					}
					else
					{
						query = " update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND - " + CO + ", GIATRICONGUYENTE = GIATRICONGUYENTE - " + TONGGIATRINT + "  " +
								" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
					}
					
					System.out.println("____LAY NGAY NGHIEP VU: " + query);
					
					if(!db.update(query))
					{
						msg =  "2.Lỗi REVERT: " + query;
						return msg;
					}
					
				}
				rsPSKT.close();
			} 
			catch (Exception e) { }
					
			//GHI NHAN LOG NGUOC LAI
			query = " INSERT ERP_PHATSINHKETOAN ( NGAYCHUNGTU, NGAYGHINHAN, LOAICHUNGTU, SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NOIDUNGNHAPXUAT_FK, NO, CO, DOITUONG, MADOITUONG, LOAIDOITUONG, " +
					"							  SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC )  " +
					" 							  SELECT NGAYCHUNGTU, '" + ngaynghiepvu + "', N'Hủy_' + LOAICHUNGTU as LOAICHUNGTU, SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NOIDUNGNHAPXUAT_FK, NO*(-1), CO*(-1), DOITUONG, MADOITUONG, LOAIDOITUONG,  " +
					"							  SOLUONG, (-1)*isnull(DONGIA,0) DONGIA, TIENTEGOC_FK, isnull(DONGIANT,0)*(-1), TIGIA_FK, ISNULL(TONGGIATRI,0)*(-1), ISNULL(TONGGIATRINT,0)*(-1), KHOANMUC  " +
					" 							  FROM ERP_PHATSINHKETOAN  " +
					" 							  WHERE LOAICHUNGTU = N'"+loaict+"' and SOCHUNGTU = '" + sochungtu + "' ";
			
			System.out.println("____LAY NGAY NGHIEP VU: " + query);
			
			if(!db.update(query))
			{
				msg =  "2.Lỗi REVERT: " + query;
				return msg;
			}
			
			
			loaict = "Chi phí khác";

			// GHI NHAN NGUOC LAI TAI KHOAN NO - CO
			query = "select SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NO, CO, TIENTEGOC_FK, TONGGIATRINT  "
					+ "from ERP_PHATSINHKETOAN " + "where LOAICHUNGTU = N'" + loaict + "' and SOCHUNGTU = '" + sochungtu
					+ "' ";

			System.out.println("1.CHECK NO-CO: " + query);

			rsPSKT = db.get(query);
			try {
				while (rsPSKT.next()) {
					String taikhoan_fk = rsPSKT.getString("TAIKHOAN_FK");
					String tiente_fk = rsPSKT.getString("TIENTEGOC_FK");
					double NO = rsPSKT.getDouble("NO");
					double CO = rsPSKT.getDouble("CO");
					double TONGGIATRINT = rsPSKT.getDouble("TONGGIATRINT");

					// NEU LA CO THI BAY GIO GHI GIAM CO LAI
					if (NO > 0) {
						query = " update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND - " + NO
								+ ", GIATRINONGUYENTE = GIATRINONGUYENTE - " + TONGGIATRINT + "  "
								+ " where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang)
								+ "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
					} else {
						query = " update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND - " + CO
								+ ", GIATRICONGUYENTE = GIATRICONGUYENTE - " + TONGGIATRINT + "  "
								+ " where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang)
								+ "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
					}

					System.out.println("____LAY NGAY NGHIEP VU: " + query);

					if (!db.update(query)) {
						msg = "2.Lỗi REVERT: " + query;
						return msg;
					}

				}
				rsPSKT.close();
			} catch (Exception e) {
			}

			// GHI NHAN LOG NGUOC LAI
			query = " INSERT ERP_PHATSINHKETOAN ( NGAYCHUNGTU, NGAYGHINHAN, LOAICHUNGTU, SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NOIDUNGNHAPXUAT_FK, NO, CO, DOITUONG, MADOITUONG, LOAIDOITUONG, "
					+ "							  SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC )  "
					+ " 							  SELECT NGAYCHUNGTU, '" + ngaynghiepvu
					+ "', N'Hủy_' + LOAICHUNGTU as LOAICHUNGTU, SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NOIDUNGNHAPXUAT_FK, NO*(-1), CO*(-1), DOITUONG, MADOITUONG, LOAIDOITUONG,  "
					+ "							  SOLUONG, (-1)*isnull(DONGIA,0) DONGIA, TIENTEGOC_FK, isnull(DONGIANT,0)*(-1), TIGIA_FK, ISNULL(TONGGIATRI,0)*(-1), ISNULL(TONGGIATRINT,0)*(-1), KHOANMUC  "
					+ " 							  FROM ERP_PHATSINHKETOAN  "
					+ " 							  WHERE LOAICHUNGTU = N'" + loaict + "' and SOCHUNGTU = '"
					+ sochungtu + "' ";

			System.out.println("____LAY NGAY NGHIEP VU: " + query);

			if (!db.update(query)) {
				msg = "2.Lỗi REVERT: " + query;
				return msg;
			}
			
			
		  }				
		catch(Exception e )
		{
			e.printStackTrace();
		}

		return msg;
	}
	
	
	private String Revert_KeToan_HuyHoaDonNCC(Idbutils db,String sochungtu)
	{
		//sochungtu = sochungtu.substring(3, sochungtu.length());
		String msg = "";
		
		try{
			String colNAME = "";
			String tableNAME = "";
			
			colNAME = "ngayghinhan";
			tableNAME = "ERP_PARK";
			
			String query = " select " + colNAME + " as ngaynghiepvu from " + tableNAME + " where pk_seq = '" + sochungtu + "' ";
			System.out.println("____LAY NGAY NGHIEP VU: " + query);
			
			ResultSet ngayRS = db.get(query);
			String ngaynghiepvu = "";
			
			if(ngayRS.next())
			{
				ngaynghiepvu = ngayRS.getString("ngaynghiepvu");
			}
			ngayRS.close();
			
			String nam = ngaynghiepvu.substring(0, 4);
			String thang = ngaynghiepvu.substring(5, 7);
			
			//CHECK NGAY GHI NHAN REVERT CO HOP LE HAY KHONG (CHI DUOC THUC HIEN TRONG THANG KHOA SO CONG 1)
			query = "select THANGKS, NAM from ERP_KHOASOKETOAN order by NAM desc, THANGKS desc";
			System.out.println("1.CHECK NO-CO: " + query);
			String thangKS = "1";
			String namKS = "2013";
			ResultSet rsCheck = db.get(query);
			if(rsCheck != null)
			{
				try 
				{
					if(rsCheck.next())
					{
						thangKS = rsCheck.getString("THANGKS");
						namKS = rsCheck.getString("NAM");
					}
					rsCheck.close();
				} 
				catch (Exception e) {}
			}
			
			String thangHopLe = "";
			String namHopLe = "";
			if(Integer.parseInt(thangKS) == 12 )
			{
				thangHopLe =  "1";
				namHopLe = Integer.toString( Integer.parseInt(namKS)  + 1);
			}
			else
			{
				thangHopLe =  Integer.toString(Integer.parseInt(thangKS) + 1);
				namHopLe = namKS;
			}
			
			//GHI NHAN NGUOC LAI TAI KHOAN NO - CO
			query = "select SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NO, CO, TIENTEGOC_FK, TONGGIATRINT  " +
				    "from ERP_PHATSINHKETOAN " +
				    "where LOAICHUNGTU = N'Duyệt hóa đơn NCC' and SOCHUNGTU = '" + sochungtu + "' ";
			
			System.out.println("1.CHECK NO-CO: " + query);
						
			ResultSet rsPSKT = db.get(query);
			try 
			{
				while(rsPSKT.next())
				{
					String taikhoan_fk = rsPSKT.getString("TAIKHOAN_FK");
					String tiente_fk = rsPSKT.getString("TIENTEGOC_FK");
					double NO = rsPSKT.getDouble("NO");
					double CO = rsPSKT.getDouble("CO");
					double TONGGIATRINT = rsPSKT.getDouble("TONGGIATRINT");
					
					//NEU LA CO THI BAY GIO GHI GIAM CO LAI
					if( NO > 0 )
					{
						query = " update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND - " + NO + ", GIATRINONGUYENTE = GIATRINONGUYENTE - " + TONGGIATRINT + "  " +
								" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
					}
					else
					{
						query = " update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND - " + CO + ", GIATRICONGUYENTE = GIATRICONGUYENTE - " + TONGGIATRINT + "  " +
								" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
					}
					
					System.out.println("____LAY NGAY NGHIEP VU: " + query);
					
					if(!db.update(query))
					{
						msg = "1.Lỗi REVERT: " + query;
						return msg;
					}
					
				}
				rsPSKT.close();
			} 
			catch (Exception e) { }
			
			//GHI NHAN LOG NGUOC LAI
			query = " INSERT ERP_PHATSINHKETOAN( NGAYCHUNGTU, NGAYGHINHAN, LOAICHUNGTU, SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NOIDUNGNHAPXUAT_FK, NO, CO, DOITUONG, MADOITUONG, LOAIDOITUONG, " +
					"							 SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC )  " +
					" 							 SELECT NGAYCHUNGTU, '" + ngaynghiepvu + "', N'Hủy_' + LOAICHUNGTU as LOAICHUNGTU, SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NOIDUNGNHAPXUAT_FK, NO*(-1), CO*(-1), DOITUONG, MADOITUONG, LOAIDOITUONG,  " +
					"							 SOLUONG, (-1)*isnull(DONGIA,0) DONGIA, TIENTEGOC_FK, isnull(DONGIANT,0)*(-1), TIGIA_FK, ISNULL(TONGGIATRI,0)*(-1), ISNULL(TONGGIATRINT,0)*(-1), KHOANMUC  " +
					" 							 FROM ERP_PHATSINHKETOAN  " +
					" 							 WHERE LOAICHUNGTU = N'Duyệt hóa đơn NCC' and SOCHUNGTU = '" + sochungtu + "' ";
			
			System.out.println("____LAY NGAY NGHIEP VU: " + query);
			
			if(!db.update(query))
			{
				msg = "1.Lỗi REVERT: " + query;
				return msg;
			}
			
			
		}
		catch(Exception e )
		{
			e.printStackTrace();
		}

		return msg;
	}
	private String capNhatHuyHoaDon(Idbutils db,String id,String tableName, String loaiHoaDon) {
		String query = 
			"UPDATE " + tableName + " \n" +
			"SET ISTHANHTOAN = 0 \n" +
			"WHERE PK_SEQ IN (SELECT HOADON_FK FROM ERP_THANHTOANHOADON_HOADON WHERE THANHTOANHD_FK = " + id + " AND LOAIHD = " + loaiHoaDon + " ) ";
		
		if(!db.update(query))
		{
			return  "CTTHD1.3 Khong the chot ERP_TAMUNG: " + query;
		}
		
		return "";
	}

}
