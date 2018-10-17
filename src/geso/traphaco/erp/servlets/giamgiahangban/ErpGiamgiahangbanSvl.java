package geso.traphaco.erp.servlets.giamgiahangban;

import geso.traphaco.erp.beans.giamgiahangban.*;
import geso.traphaco.erp.beans.giamgiahangban.imp.*;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpGiamgiahangbanSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public ErpGiamgiahangbanSvl() {
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
	    
	    IErpGiamgiahangbanList obj = new ErpGiamgiahangbanList();
	    String ctyId = (String)session.getAttribute("congtyId");
	    obj.setUserId(userId);
	    obj.setCongtyId(ctyId);
	    
	    String action = util.getAction(querystring);
	    String Id = util.getId(querystring);
	    String msg = "";
	    
	    if(action.trim().equals("delete"))
	    {
	    	dbutils db = new dbutils();
	    	
	    	/*if(!db.update("delete erp_giamgiahangban_hoadon where giamgia_fk = '" + Id + "'"))
	    	{
	    		msg = "Không thể xóa erp_giamgiahangban_hoadon";
	    	}

	    	if(!db.update("delete erp_giamgiahangban_sanpham where giamgiaban_fk = '" + Id + "'"))
	    	{
	    		msg = "Không thể xóa erp_giamgiahangban_sanpham";
	    	}*/

	    	if(!db.update("update erp_giamgiahangban set trangthai='2' where pk_seq = '" + Id + "'"))
	    	{
	    		msg = "Không thể xóa erp_giamgiahangban";
	    	}
	    	db.shutDown();
	    }
	    
	    if(action.trim().equals("chot"))
	    {
	    	msg = ChotGiamGiaHangBan(Id);
	    }
	    
	    obj.init("");
	    obj.setMsg(msg);
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpGiamGiaHangBan.jsp";
		response.sendRedirect(nextJSP);
	}

	private String ChotGiamGiaHangBan(String khlId)
	{
		 		
		String msg = "";
		dbutils db = new dbutils();
		Utility util = new Utility();
    	
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "update erp_giamgiahangban set trangthai = '1' where pk_seq = '" + khlId + "'";
	    	if(!db.update(query))
	    	{
	    		db.getConnection().rollback();
	    		msg = "Không thể xóa erp_giamgiahangban";
	    		return msg;
	    	}
			
	    	query = "select khachhang_fk, ngayghinhan, ngayhoadon from erp_giamgiahangban where pk_seq = '" + khlId + "'";
			
			String ngayhoadon = "";
			String ngayghinhan = "";

			ResultSet rs = db.get(query);
			if(rs.next())
			{
				ngayhoadon = rs.getString("ngayhoadon");
				ngayghinhan = rs.getString("ngayghinhan");
			}
			rs.close();
			
			String nam = ngayhoadon.substring(0, 4);
			String thang = ngayhoadon.substring(5, 7);
			
			query = " select a.khachhang_fk, b.sanpham_fk, " +
					"	    ( select pk_seq from ERP_TAIKHOANKT where PK_SEQ =  d.TAIKHOAN_FK ) as taikhoanCO_SoHieu, " +
					" 		( select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = (	" +
					"			 case when c.DVKD_FK = '100000' then '532100' " +
					" 			 	  when c.DVKD_FK = '100004' then '532200' " +
					" 			 end ) ) as taikhoanNO_DS_SoHieu, b.sotien, " +
					"		b.sotien * a.vat / 100 as tienVAT, ( select pk_seq from ERP_TAIKHOANKT where sohieutaikhoan = '333110' ) as taikhoanNO_VAT_SoHieu " +
					" from erp_giamgiahangban a inner join erp_giamgiahangban_sanpham b on a.pk_seq = b.giamgiaban_fk " +
					" 	 inner join ERP_SANPHAM c on b.sanpham_fk = c.PK_SEQ " +
					" 	 inner join ERP_KHACHHANG d on a.khachhang_fk = d.PK_SEQ " +
					" where a.pk_seq = '" + khlId + "' and sotien != 0 ";
			
			System.out.println("--XAC DINH KE TOAN: " + query);
			ResultSet rsSp = db.get(query);
			if(rsSp != null)
			{
				String taikhoanktCo = "";
				String taikhoanktNo_DS = "";
				String taikhoanktNo_VAT = "";
				
				String doituong_no = "";
				String madoituong_no = "";
				String doituong_co = "";
				String madoituong_co = "";
				
				while(rsSp.next())
				{
					taikhoanktCo = rsSp.getString("taikhoanCO_SoHieu") == null ? "" : rsSp.getString("taikhoanCO_SoHieu") ;
					taikhoanktNo_DS = rsSp.getString("taikhoanNO_DS_SoHieu") == null ? "" : rsSp.getString("taikhoanNO_DS_SoHieu") ;
					taikhoanktNo_VAT = rsSp.getString("taikhoanNO_VAT_SoHieu") == null ? "" : rsSp.getString("taikhoanNO_VAT_SoHieu") ;
					
					double sotienGIAM = Math.round(rsSp.getDouble("sotien"));
					if(sotienGIAM > 0)
					{
						doituong_no = "Sản phẩm";
						madoituong_no = rsSp.getString("sanpham_fk");
						doituong_co = "Khách hàng";
						madoituong_co = rsSp.getString("khachhang_fk");
						
						if(taikhoanktCo.trim().length() <= 0 || taikhoanktNo_DS.trim().length() <= 0)
						{
							msg = "Loại sản phẩm và nội dung nhập tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
							db.getConnection().rollback();
							return msg;
						}
						
						String tiente_fk = "100000";
						msg = util.Update_TaiKhoan( db, thang, nam, ngayghinhan, ngayhoadon, "Giảm/Tăng giá hàng bán", khlId, taikhoanktNo_DS, taikhoanktCo, "", 
													Double.toString(sotienGIAM), Double.toString(sotienGIAM), doituong_no, madoituong_no, doituong_co, madoituong_co, "0", "", "", tiente_fk, "", "1", Double.toString(sotienGIAM), Double.toString(sotienGIAM), "Doanh số" );
						if(msg.trim().length() > 0)
						{
							rsSp.close();
							return msg;
						}
						
					}
					
					double sotienGIAM_VAT = Math.round(rsSp.getDouble("tienVAT"));
					if(sotienGIAM_VAT > 0)
					{
						doituong_no = "Khách hàng";
						madoituong_no = rsSp.getString("khachhang_fk");
						doituong_co = "Khách hàng";
						madoituong_co = rsSp.getString("khachhang_fk");
						
						if(taikhoanktCo.trim().length() <= 0 || taikhoanktNo_DS.trim().length() <= 0)
						{
							msg = "Loại sản phẩm và nội dung nhập tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
							db.getConnection().rollback();
							return msg;
						}
						
						String tiente_fk = "100000";
						msg = util.Update_TaiKhoan( db, thang, nam, ngayghinhan, ngayhoadon, "Giảm/Tăng giá hàng bán", khlId, taikhoanktNo_VAT, taikhoanktCo, "", 
													Double.toString(sotienGIAM_VAT), Double.toString(sotienGIAM_VAT), doituong_no, madoituong_no, doituong_co, madoituong_co, "0", "", "", tiente_fk, "", "1", Double.toString(sotienGIAM_VAT), Double.toString(sotienGIAM_VAT), "VAT" );
						if(msg.trim().length() > 0)
						{
							rsSp.close();
							return msg;
						}
						
					}
					
				}
				rsSp.close();
			}
	    	
			db.getConnection().commit();
			db.shutDown();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("__Exception chot: " + e.getMessage());
		}
    	
    	db.shutDown();
    	
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
	    
	    
	    IErpGiamgiahangbanList obj;
	    
		String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    if(action.equals("new"))
	    {
    		IErpGiamgiahangban khl = new ErpGiamgiahangban();
    		khl.setCongtyId(ctyId);
    		khl.setUserId(userId);

	    	session.setAttribute("csxBean", khl);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpGiamGiaHangBanNew.jsp");
	    }
	    else
	    {
	    	obj = new ErpGiamgiahangbanList();
	    	obj.setCongtyId(ctyId);
		    obj.setUserId(userId);

	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpGiamGiaHangBan.jsp");	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpGiamgiahangbanList obj) 
	{
		String ma = request.getParameter("magiamgiahangmua");
		if(ma == null)
			ma = "";
		obj.setMa(ma);
		
		String khachhang = request.getParameter("khachhang");
		if(khachhang == null)
			khachhang = "";
		obj.setKhachhang(khachhang);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String 
		query = " select A.SOHOADON ,a.pk_seq, d.ten as nccTen, '' as poTen,  " +
				" a.trangthai, b.ten as nguoitao, a.ngaytao, c.ten as nguoisua, a.ngaysua      " +
				" from ERP_GiamGiaHangBan a inner join NhanVien b on a.nguoitao = b.pk_seq      " +
				" inner join nhanvien c on a.nguoisua = c.pk_seq inner join ERP_KhachHang d on a.khachhang_fk = d.pk_seq   " +
				" where a.pk_seq > 0 ";
		
		if(ma.length() > 0)
			query += " and HD.HOADON_FK like N'%" + ma + "%' ";
		
		if(khachhang.length() > 0)
			query += " and d.ten like N'%" + khachhang + "%' ";
		
		if(trangthai.length() > 0)
			query += " and a.trangthai = '" + trangthai + "' ";
		
		query += " order by a.pk_seq desc ";
		
		
		return query;
	}
	
	

}
