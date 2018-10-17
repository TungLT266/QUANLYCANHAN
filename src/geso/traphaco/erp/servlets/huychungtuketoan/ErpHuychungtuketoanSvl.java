package geso.traphaco.erp.servlets.huychungtuketoan;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.huychungtuketoan.IErpHuychungtuketoan;
import geso.traphaco.erp.beans.huychungtuketoan.IErpHuychungtuketoanList;
import geso.traphaco.erp.beans.huychungtuketoan.imp.ErpHuychungtuketoan;
import geso.traphaco.erp.beans.huychungtuketoan.imp.ErpHuychungtuketoanList;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpHuychungtuketoanSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpHuychungtuketoanSvl() {
        super();
    }

 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpHuychungtuketoanList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	   
	    
	    String querystring = request.getQueryString();
	    System.out.println(querystring);
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    
	    String hctmhId = util.getId(querystring);
	    
	    obj =  new ErpHuychungtuketoanList();
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    
	    if (action.equals("delete"))
	    {	
	    	String msg = Delete(hctmhId);
	    	if(msg.length() > 0)
	    		obj.setMsg(msg);
	    }
	    
	    if (action.equals("chot"))
	    {		    	
	    	//KIỂM TRA LOẠI CHỨNG TỪ
	    	
	    	String loaichungtu = util.getLoai(querystring);
	    	String id = util.getId(querystring);
	    	String sochungtu = util.getSoChungTu(querystring);
	    	String msg = ChotHPC(id,loaichungtu,sochungtu);
	    	if(msg.length() > 0)
	    		obj.setMsg(msg);
	    }
	    
	    
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuychungtuketoan.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpHuychungtuketoanList obj;
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
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpHuychungtuketoan hctmhBean = new ErpHuychungtuketoan();
	    	hctmhBean.setCongtyId((String)session.getAttribute("congtyId"));
	    	hctmhBean.createRs();
    		
	    	session.setAttribute("hctmhBean", hctmhBean);
	    
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHuychungtuketoanNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpHuychungtuketoanList();
	    		obj.setCongtyId((String)session.getAttribute("congtyId"));
	    		
		    	String search = getSearchQuery(request, obj);
		    	
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setUserId(userId);
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpHuychungtuketoan.jsp");	
		    }
	    	else
	    	{
		    	obj = new ErpHuychungtuketoanList();
		    	obj.setCongtyId((String)session.getAttribute("congtyId"));
		    	
		    	String search = getSearchQuery(request, obj);
		    	obj.init(search);
				obj.setUserId(userId);
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpHuychungtuketoan.jsp");
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpHuychungtuketoanList obj)
	{
		//LOAICHUNGTU = 2: HỦY PHIẾU CHI
		//LOAICHUNGTU = 3: HỦY HÓA ĐƠN NCC
		//LOAICHUNGTU = 1: HỦY PHIẾU THU
		
		String query = "	SELECT a.PK_SEQ as SOPHIEU,dc.sochungtu as SOCHUNGTU,a.SOCHUNGTU as SOPHIEUDC, case a.LOAICHUNGTU when 1 then N'Điều chuyển' else N'Giải ngân' end as LOAICHUNGTU ,a.TRANGTHAI, a.NGAYTAO, a.NGAYSUA, b.TEN as NGUOITAO, c.TEN as NGUOISUA, a.LOAICHUNGTU LOAI \n " +
   		"	FROM ERP_HUYCHUNGTUDIEUCHUYEN a inner join NHANVIEN b on a.nguoitao = b.pk_seq inner join NHANVIEN c on a.nguoisua = c.pk_seq \n " +
   		"   inner join ERP_DIEUCHUYENTIEN dc on dc.pk_seq= a.sochungtu" +
   		"	WHERE a.congty_fk = '" + obj.getCongtyId() + "' and a.LOAICHUNGTU ='1' ";

		
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
	
		
		if(tungay.length() > 0)
			query += " and a.ngaytao >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngaysua <= '" + denngay + "'";
		
		if(trangthai.length() > 0)
			query += " and a.trangthai = '" + trangthai + "'";
		
		if(nguoitao.length() > 0)
			query += " and b.TEN like N'%" + nguoitao + "%'";
		
		if(sochungtu.length() > 0)
			query += " and dc.SOCHUNGTU like '%" + sochungtu + "%'";
		
		
		return query;
	}
	
	private String Delete(String hctmhId)
	{
		dbutils db = new dbutils();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			db.update("update ERP_HUYCHUNGTUDIEUCHUYEN set trangthai = '2' where pk_seq = '" + hctmhId + "'");
			
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
			
			String trangthaiDCT="";
			String dcId="";
			query ="select DC.trangthai,DC.pk_seq from ERP_HUYCHUNGTUDIEUCHUYEN HDC left join  ERP_DIEUCHUYENTIEN DC on DC.PK_SEQ= HDC.SOCHUNGTU  where HDC.pk_seq ='"+Id+"'";
			ResultSet ttRs= db.get(query);
			if(ttRs!=null)
			{
				while (ttRs.next())
				{
					trangthaiDCT= ttRs.getString("trangthai") ;
					dcId=ttRs.getString("pk_seq");
				}
				ttRs.close();
			}
			if(trangthaiDCT!=null)
			{
				if(trangthaiDCT.equals("0"))
				{
					query= "DELETE ERP_DIEUCHUYENTIEN where pk_seq = " + dcId;
					if(!db.update(query))
					{
						db.getConnection().rollback();
						return  "Khong the huy dieu chuyển, " + query;
					}
				}
			}
			
			
			
			query ="UPDATE ERP_HUYCHUNGTUDIEUCHUYEN SET TRANGTHAI = 1 WHERE PK_SEQ ='"+Id+"'";
			
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return  "Khong the huy dieu chuyển, " + query;
			}
			
			if(loaict.equals("1")){ 
			
				
				String tb = Revert_KeToan_Huychungtuketoan(sochungtu);
				if(tb.length()>0) return tb;
				
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
	
	
	private String Revert_KeToan_Huychungtuketoan(String sochungtu)
	{	
		//sochungtu = sochungtu.substring(3, sochungtu.length());
		dbutils db= new dbutils();
		String msg = "";
		try{
			
			String query = "";					
			
			String	colNAME = "NGAYCHUNGTU";;
			String	tableNAME = "ERP_DIEUCHUYENTIEN";
					
			query = " SELECT " + colNAME + " as ngaynghiepvu from " + tableNAME + " where pk_seq = '" + sochungtu + "' ";
			System.out.println("____LAY NGAY NGHIEP VU: " + query);
			
			ResultSet ngayRS = db.get(query);
			String ngaynghiepvu = "";
					
			if(ngayRS.next())
			{
				ngaynghiepvu = ngayRS.getString("ngaynghiepvu");
			}
			ngayRS.close();
			System.out.println("ngay nghiep vu: " + ngaynghiepvu);
			String nam = ngaynghiepvu.substring(0, 4);
			String thang = ngaynghiepvu.substring(5, 7);
			
			//CHECK NGAY GHI NHAN REVERT CO HOP LE HAY KHONG (CHI DUOC THUC HIEN TRONG THANG KHOA SO CONG 1)
			query = "select THANGKS, NAM from ERP_KHOASOKETOAN order by NAM desc, THANGKS desc";
			System.out.println("1.CHECK NO-CO: " + query);
					
			String thangKS = "1";
//			String namKS = "2013";
			ResultSet rsCheck = db.get(query);
			if(rsCheck != null)
			{
				try 
				{
					if(rsCheck.next())
					{
						thangKS = rsCheck.getString("THANGKS");
//						namKS = rsCheck.getString("NAM");
					}
					rsCheck.close();
				} 
				catch (Exception e) {}
			}
					
//			String thangHopLe = "";
//			String namHopLe = "";
			if(Integer.parseInt(thangKS) == 12 )
			{
//				thangHopLe =  "1";
//				namHopLe = Integer.toString( Integer.parseInt(namKS)  + 1);
			}
			else
			{
//				thangHopLe =  Integer.toString(Integer.parseInt(thangKS) + 1);
//				namHopLe = namKS;
			}
					
			String	loaict = "Điều chuyển tiền";
			
			//GHI NHAN NGUOC LAI TAI KHOAN NO - CO
			query = "select SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NO, CO, TIENTEGOC_FK, TONGGIATRINT  " +
				    "from ERP_PHATSINHKETOAN " +
				    "where LOAICHUNGTU like N'"+loaict+"%' and SOCHUNGTU = '" + sochungtu + "' ";
			
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
					" 							 SELECT NGAYCHUNGTU, '" + ngaynghiepvu + "', N'REVERT_' + LOAICHUNGTU as LOAICHUNGTU, SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NOIDUNGNHAPXUAT_FK, NO*(-1), CO*(-1), DOITUONG, MADOITUONG, LOAIDOITUONG,  " +
					"							 SOLUONG, (-1)*isnull(DONGIA,0) DONGIA, TIENTEGOC_FK, isnull(DONGIANT,0)*(-1), TIGIA_FK, ISNULL(TONGGIATRI,0)*(-1), ISNULL(TONGGIATRINT,0)*(-1), KHOANMUC  " +
					" 							 FROM ERP_PHATSINHKETOAN  " +
					" 							 WHERE LOAICHUNGTU like N'"+loaict+"%' and SOCHUNGTU = '" + sochungtu + "' ";
												
			
			System.out.println("____LAY NGAY NGHIEP VU: " + query);
			
			if(!db.update(query))
			{
				msg =  "2.Lỗi REVERT: " + query;
				return msg;
			}
		
			
		  }				
		catch(Exception e )
		{
			e.printStackTrace();
		}
		db.shutDown();
		return msg;
	}
	
	
	private String Revert_KeToan_HuyHoaDonNCC(String sochungtu)
	{
		//sochungtu = sochungtu.substring(3, sochungtu.length());
		String msg = "";
		dbutils db = new dbutils();
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
//			String thangKS = "1";
//			String namKS = "2013";
//			ResultSet rsCheck = db.get(query);
//			if(rsCheck != null)
//			{
//				try 
//				{
//					if(rsCheck.next())
//					{
//						thangKS = rsCheck.getString("THANGKS");
//						namKS = rsCheck.getString("NAM");
//					}
//					rsCheck.close();
//				} 
//				catch (Exception e) {}
//			}
			
//			String thangHopLe = "";
//			String namHopLe = "";
//			if(Integer.parseInt(thangKS) == 12 )
//			{
//				thangHopLe =  "1";
//				namHopLe = Integer.toString( Integer.parseInt(namKS)  + 1);
//			}
//			else
//			{
//				thangHopLe =  Integer.toString(Integer.parseInt(thangKS) + 1);
//				namHopLe = namKS;
//			}
			
			//GHI NHAN NGUOC LAI TAI KHOAN NO - CO
			query = "select SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NO, CO, TIENTEGOC_FK, TONGGIATRINT  " +
				    "from ERP_PHATSINHKETOAN " +
				    "where LOAICHUNGTU = N'Duyệt hóa đơn' and SOCHUNGTU = '" + sochungtu + "' ";
			
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
					" 							 SELECT NGAYCHUNGTU, '" + ngaynghiepvu + "', N'REVERT_' + LOAICHUNGTU as LOAICHUNGTU, SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NOIDUNGNHAPXUAT_FK, NO*(-1), CO*(-1), DOITUONG, MADOITUONG, LOAIDOITUONG,  " +
					"							 SOLUONG, (-1)*isnull(DONGIA,0) DONGIA, TIENTEGOC_FK, isnull(DONGIANT,0)*(-1), TIGIA_FK, ISNULL(TONGGIATRI,0)*(-1), ISNULL(TONGGIATRINT,0)*(-1), KHOANMUC  " +
					" 							 FROM ERP_PHATSINHKETOAN  " +
					" 							 WHERE LOAICHUNGTU = N'Duyệt hóa đơn' and SOCHUNGTU = '" + sochungtu + "' ";
			
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
		db.shutDown();
		return msg;
	}

}
