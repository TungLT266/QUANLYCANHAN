package geso.traphaco.distributor.servlets.hoadonphelieu;

import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.distributor.beans.hoadonphelieu.*;
import geso.traphaco.distributor.beans.hoadonphelieu.imp.*;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.distributor.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpHoadonphelieuSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public ErpHoadonphelieuSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    IErpHoadonphelieuList obj = new ErpHoadonphelieuList();
	    String ctyId = (String)session.getAttribute("congtyId");
	    obj.setUserId(userId);
	    obj.setCongtyId(ctyId);
	    util.setSearchToHM(userId, session, this.getServletName(), "");
	    String action = util.getAction(querystring);
	    String khlId = util.getId(querystring);
	    String msg = "";
	    
	    if(action.trim().equals("delete"))
	    {
	    	dbutils db = new dbutils();
	    	if(!db.update("update erp_hoadonphelieu set trangthai = '2' where pk_seq = '" + khlId + "'"))
	    	{
	    		msg = "Không thể xóa erp_hoadonphelieu";
	    	}
	    	db.shutDown();
	    }
	    
	    if(action.trim().equals("chot"))
	    {
	    	msg = ChotHoaDon(khlId, ctyId);
	    	
	    }
	    
	    obj.init("");
	    obj.setMsg(msg);
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHoaDonPheLieu.jsp";
		response.sendRedirect(nextJSP);
	}

	private static String ChotHoaDon(String Id, String ctyId)
	{
		String msg = "";
		dbutils db = new dbutils();
    	
		Utility util = new Utility();
		
    	try 
    	{
			db.getConnection().setAutoCommit(false);
			
			String query = 
			
			" SELECT a.doanhthu_fk, ISNULL(a.diengiai,'') as diengiai, isnull(a.loaick,'')as loaick, a.ngayhoadon, a.vat, isnull(c.ma,'') as trungtamdt, b.PK_SEQ KHACHHANG_FK, \n" +

			"		 case a.doanhthu_fk \n" +
			"		 when 400004 then isnull(a.avat,0) \n" +
			"		 when 400005 then isnull(a.avat,0) \n" +
			"		 else ( select SUM( thanhtien ) from erp_hoadonphelieu_sanpham where hoadonphelieu_fk = a.pk_seq ) end as SOTIEN, isnull(a.diengiai, '') diengiai, isnull(a.isNPP, 0) isNPP, a.kbh_fk \n " +
			
			" FROM ERP_HoaDonPheLieu a \n" +
			" inner join khachhang b on a.khachhang_fk = b.PK_SEQ \n " +
			" left join ERP_TRUNGTAMDOANHTHU c on a.trungtamdoanhthu_fk = c.pk_seq \n " +
			
			" where a.pk_seq = '" + Id + "' \n ";
			System.out.println("Query: "+query);
			ResultSet rs = db.get(query);
			
			String taikhoanCO = "";
			String taikhoanCO_VAT = "";
			String taikhoanNO = "";
			String taikhoanNO_VAT = "";
			
			String doituong_NO = "";
			String madoituong_NO = "";
			
			String doituong_CO = "";
			String madoituong_CO = "";
			String loaiDT = "";
			
			while(rs.next())
			{

				double TONGSOTIEN = Math.round(rs.getDouble("SOTIEN"));
				double TONGVAT = Math.round(TONGSOTIEN * rs.getDouble("VAT") / 100 );
				doituong_NO = "Khách hàng";
				madoituong_NO = rs.getString("KHACHHANG_FK");
				String diengiai = rs.getString("diengiai");
				String isNPP = rs.getString("isNPP");
				String kbh_fk = rs.getString("kbh_fk");

				System.out.println("TONGSOTIEN:"+TONGSOTIEN);
				doituong_CO = "";
				madoituong_CO = "";
				
				if(rs.getString("doanhthu_fk").equals("400002")) //LOAI HD: HÓA ĐƠN ĐIỀU CHỈNH TĂNG
				{
					query = " SELECT KH.TAIKHOAN_FK AS TAIKHOAN_NO, HD.TAIKHOANDOANHTHU_FK AS TAIKHOAN_CO, \n" +
							" 		 KH.TAIKHOAN_FK AS TAIKHOAN_NO_VAT,  \n" +
							" 		(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33311000' AND CONGTY_FK = "+ctyId+") AS TAIKHOAN_CO_VAT \n" +
							
							" FROM ERP_HOADONPHELIEU HD \n" +
							" INNER JOIN KHACHHANG KH ON KH.PK_SEQ = HD.KHACHHANG_FK \n" +
							
							" WHERE HD.PK_SEQ =  '" + Id + "' \n ";
					
					System.out.println("DT!!!!:"+query);
					
					ResultSet rsTK = db.get(query);
					if(rsTK != null){
						rsTK.next();
						taikhoanNO =  rsTK.getString("TAIKHOAN_NO") == null ? "": rsTK.getString("TAIKHOAN_NO");
						taikhoanCO = rsTK.getString("TAIKHOAN_CO") == null ? "": rsTK.getString("TAIKHOAN_CO");
						
						taikhoanNO_VAT =  rsTK.getString("TAIKHOAN_NO_VAT")== null ? "": rsTK.getString("TAIKHOAN_NO_VAT");
						taikhoanCO_VAT = rsTK.getString("TAIKHOAN_CO_VAT")== null ? "": rsTK.getString("TAIKHOAN_CO_VAT");
						rsTK.close();
					}else{
						taikhoanNO = "";
						taikhoanCO = "";
						taikhoanNO_VAT = "";
						taikhoanCO_VAT = "";
					}
					loaiDT = "Hóa đơn điều chỉnh tăng";	
					
				}
				else if(rs.getString("doanhthu_fk").equals("400003")) //LOAI HD: HÓA ĐƠN ĐIỀU CHỈNH GIẢM
				{
					query = "SELECT HD.TAIKHOANDOANHTHU_FK AS TAIKHOAN_NO, KH.TAIKHOAN_FK AS TAIKHOAN_CO, \n " +							
							"		(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33311000' AND CONGTY_FK = "+ctyId+") AS TAIKHOAN_NO_VAT, \n " +
							"		KH.TAIKHOAN_FK AS TAIKHOAN_CO_VAT \n" +
							
							"FROM ERP_HOADONPHELIEU HD \n " +
							"INNER JOIN khachhang KH ON KH.PK_SEQ = HD.KHACHHANG_FK \n " +
							
							"WHERE HD.PK_SEQ =  '" + Id + "' \n ";
					
					System.out.println(query);
					ResultSet rsTK = db.get(query);
					if(rsTK != null){
						rsTK.next();
						taikhoanNO =  rsTK.getString("TAIKHOAN_NO") == null ? "": rsTK.getString("TAIKHOAN_NO");
						taikhoanCO = rsTK.getString("TAIKHOAN_CO") == null ? "": rsTK.getString("TAIKHOAN_CO");
				
						taikhoanNO_VAT =  rsTK.getString("TAIKHOAN_NO_VAT") == null ? "": rsTK.getString("TAIKHOAN_NO_VAT");
						taikhoanCO_VAT = rsTK.getString("TAIKHOAN_CO_VAT")== null ? "": rsTK.getString("TAIKHOAN_CO_VAT");
						rsTK.close();
					}else{
						taikhoanNO = "";
						taikhoanCO = "";
						taikhoanNO_VAT = "";
						taikhoanCO_VAT = "";
					}
			
					if(TONGSOTIEN < 0) TONGSOTIEN = TONGSOTIEN*(-1);
					if(TONGVAT < 0) TONGVAT = TONGVAT*(-1);
					loaiDT = "Hóa đơn điều chỉnh giảm";				
				}

				String ngayghinhan = rs.getString("ngayhoadon");
				String nam = ngayghinhan.substring(0, 4);
				String thang = ngayghinhan.substring(5, 7);
				
				String tiente_fk = "100000";
			
				if(TONGSOTIEN > 0)
				{
					if(taikhoanCO.trim().length() <= 0 || taikhoanNO.trim().length() <= 0 )
					{
						msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
						System.out.println(msg);
						db.getConnection().rollback();
						return msg;
					}
					
					msg = util.Update_TaiKhoan_Vat_DienGiai_KBH( db, thang, nam, ngayghinhan, ngayghinhan, "Hóa đơn khác", Id, taikhoanNO, taikhoanCO, "", 
					Double.toString(TONGSOTIEN), Double.toString(TONGSOTIEN), doituong_NO, madoituong_NO, doituong_CO, madoituong_CO, "0", "", "", tiente_fk, "", "1", Double.toString(TONGSOTIEN), Double.toString(TONGSOTIEN), loaiDT + " - Tiền hàng", "0" , diengiai , Id, isNPP, kbh_fk);
							
					/*
					msg = util.Update_TaiKhoan( db, thang, nam, ngayghinhan, ngayghinhan, "Hóa đơn khác", Id, taikhoanNO, taikhoanCO, "", 
									Double.toString(TONGSOTIEN), Double.toString(TONGSOTIEN), doituong_NO,madoituong_NO,doituong_CO, madoituong_CO, 
									"0", "", "", tiente_fk, "", "1", Double.toString(TONGSOTIEN), Double.toString(TONGSOTIEN), loaiDT + " - Tiền hàng");*/
					if(msg.trim().length() > 0)
					{
						db.getConnection().rollback();
						return msg;
					}
				}
				
				// DINH KHOAN VAT
				if(TONGVAT > 0)
				{
					if(taikhoanCO_VAT.trim().length() <= 0 || taikhoanNO_VAT.trim().length() <= 0 )
					{
						msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
						System.out.println(msg);
						db.getConnection().rollback();
						return msg;
					}						

					msg = util.Update_TaiKhoan_Vat_DienGiai_KBH( db, thang, nam, ngayghinhan, ngayghinhan, "Hóa đơn khác", Id, taikhoanNO_VAT, taikhoanCO_VAT, "", 
							Double.toString(TONGVAT), Double.toString(TONGVAT), doituong_NO, madoituong_NO, doituong_CO, madoituong_CO, "0", "", "", tiente_fk, "", "1", Double.toString(TONGVAT), Double.toString(TONGVAT), loaiDT + " - VAT", "0" , diengiai , Id, isNPP, kbh_fk);
/*								
					
					msg = util.Update_TaiKhoan( db, thang, nam, ngayghinhan, ngayghinhan, "Hóa đơn khác", Id, taikhoanNO_VAT, taikhoanCO_VAT, "", 
						Double.toString(TONGVAT), Double.toString(TONGVAT), doituong_NO,madoituong_NO,doituong_CO, madoituong_CO, "0", "", "", tiente_fk, "", "1", Double.toString(TONGVAT), Double.toString(TONGVAT), loaiDT + " - VAT" );*/
				
					if(msg.trim().length() > 0)
					{
						db.getConnection().rollback();
						return msg;
					}
				}
				
				
			}
			rs.close();
			
			if(!db.update("UPDATE ERP_HOADONPHELIEU SET TRANGTHAI = '1' WHERE pk_seq = '" + Id + "'"))
	    	{
				db.getConnection().rollback();
	    		msg = "Không thể cập nhật erp_hoadonphelieu";
	    		return msg;
	    	}
			
			db.getConnection().commit();
			db.shutDown();
		} 
    	catch (Exception e) 
    	{
    		db.update("rollback");
    		db.shutDown();
		}
    	
		return msg;
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    
	    out = response.getWriter();
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    String ctyId = (String)session.getAttribute("congtyId");	    
	    
	    IErpHoadonphelieuList obj;
	    
		String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    if(action.equals("new"))
	    {
    		IErpHoadonphelieu khl = new ErpHoadonphelieu();
    		
    		session.setAttribute("nppId", "");
    		khl.setUserId(userId);
    		khl.setCongtyId(ctyId);
    		khl.createRS();
    		
	    	session.setAttribute("csxBean", khl);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Distributor/ErpHoaDonPheLieuNew.jsp");
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpHoadonphelieuList();
			    obj.setUserId(userId);
			    obj.setCongtyId(ctyId);

			    session.setAttribute("nppId", "");
			    
	    		obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));

	    		//String search = getSearchQuery(request, obj);
	    		this.getSearchQuery(request, obj);
		    	
		    	//obj.init(search);
		    	obj.init("");
		    	String querySearch = GiuDieuKienLoc.createParams(obj);
				util.setSearchToHM(userId, session,this.getServletName(), querySearch);

		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	
		    	session.setAttribute("obj", obj);
		    	
		    	response.sendRedirect("/TraphacoHYERP/pages/Distributor/ErpHoaDonPheLieu.jsp");	
		    }
	    	else{
	    	
	    	obj = new ErpHoadonphelieuList();
		    obj.setUserId(userId);
		    obj.setCongtyId(ctyId);

	    	//String search = getSearchQuery(request, obj);
	    	this.getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	session.setAttribute("nppId", "");
	    	
	    	//obj.init(search);
	    	obj.init("");
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Distributor/ErpHoaDonPheLieu.jsp");
	    	}
	    }
	}
	
	//private String getSearchQuery(HttpServletRequest request, IErpHoadonphelieuList obj) 
	private void getSearchQuery(HttpServletRequest request, IErpHoadonphelieuList obj) 
	{
		Utility util = new Utility();
		
		String nppId = request.getParameter("nppId");
		if(nppId == null)
			nppId = "";
		obj.setNppId(nppId);
		
		String tennguoitao = request.getParameter("tennguoitao");
		if(tennguoitao == null)
			tennguoitao = "";
		tennguoitao=tennguoitao.trim();
		obj.setTennguoitao(tennguoitao);		
		
		String ma = request.getParameter("mahoadon");
		if(ma == null)
			ma = "";
		obj.setMa(ma);
		
		String diengiai = request.getParameter("diengiai");
		if(diengiai == null)
			diengiai = "";
		obj.setDiengiai(diengiai);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		
		String sohoadon = request.getParameter("sohoadon");
		if(sohoadon == null)
			sohoadon = "";
		obj.setSohoadon(sohoadon);
		
		String khachhang = request.getParameter("khachhang");
		if(khachhang == null)
			khachhang = "";
		obj.setKhachhang(khachhang);
//		
//		String sql =  " select a.pk_seq, d.ten as khTen, a.trangthai, b.ten as nguoitao,  " +
//					  "        a.ngaytao, c.ten as nguoisua, a.ngaysua ,a.sohoadon, a.ngayhoadon ,a.vat , a.avat as tongtien        " +
//					  "from ERP_HoaDonPheLieu a inner join NhanVien b on a.nguoitao = b.pk_seq      " +
//					  "		inner join nhanvien c on a.nguoisua = c.pk_seq inner join KhachHang d on a.khachhang_fk = d.pk_seq   " +
//					  " where a.pk_seq > 0 and a.npp_fk = "+ nppId +" ";
//		
//		if(tennguoitao.length() > 0)
//			sql += " and b.ten like N'%" + tennguoitao + "%' ";
//		if(diengiai.length() > 0)
//			sql += " and a.diengiai like N'%" + diengiai + "%' ";
//		
//		if(trangthai.length() > 0)
//			sql += " and a.trangthai = '" + trangthai + "' ";
//		
//		if(sohoadon.length() > 0)
//		{
//			sql += " and a.sohoadon like N'%" + sohoadon + "%' ";
//		}
//		if(ma.length() > 0)
//		{
//			sql += " and a.pk_seq like N'%" + ma + "%' ";
//		}
//		if(khachhang.length() > 0)
//		{
//			sql += " and (dbo.ftBoDau(d.ten)) like N'%" + util.replaceAEIOU(khachhang) + "%'  " +
//					"or  dbo.ftBoDau(d.mafast) like N'%"+ util.replaceAEIOU(khachhang) +"%' or dbo.ftBoDau(d.pk_seq) like N'%"+ util.replaceAEIOU(khachhang) +"%'";
//			
//		}
//		
//		return sql;
	}
	
	

}
