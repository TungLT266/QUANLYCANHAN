package geso.traphaco.erp.servlets.giamgiahangmua;

import geso.traphaco.erp.beans.giamgiahangmua.*;
import geso.traphaco.erp.beans.giamgiahangmua.imp.*;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpGiamgiahangmuaSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public ErpGiamgiahangmuaSvl() {
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
	    
	    IErpGiamgiahangmuaList obj = new ErpGiamgiahangmuaList();
	    String ctyId = (String)session.getAttribute("congtyId");
	    obj.setUserId(userId);
	    obj.setCongtyId(ctyId);
	    
	    String action = util.getAction(querystring);
	    String Id = util.getId(querystring);
	    String msg = "";
	    
	    if(action.trim().equals("delete"))
	    {
	    	dbutils db = new dbutils();
	    	if(!db.update("update erp_giamgiahangmua set trangthai = '2' where pk_seq = '" + Id + "'"))
	    	{
	    		msg = "Không thể xóa erp_giamgiahangmua";
	    	}
	    	db.shutDown();
	    }
	    
	    if(action.trim().equals("chot"))
	    {
	    	if(action.trim().equals("chot"))
		    {
		    	msg = ChotGiamGiaHangMua(Id);
		    }
	    }
	    
	    obj.init("");
	    obj.setMsg(msg);
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpGiamGiaHangMua.jsp";
		response.sendRedirect(nextJSP);
	}

	private String ChotGiamGiaHangMua(String Id) 
	{
		String msg = "";
		dbutils db = new dbutils();
		Utility util = new Utility();
    	
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "update erp_giamgiahangmua set trangthai = '1' where pk_seq = '" + Id + "'";
	    	if(!db.update(query))
	    	{
	    		db.getConnection().rollback();
	    		msg = "Không thể xóa erp_giamgiahangmua";
	    		return msg;
	    	}
			
	    	query = "select ncc_fk, ngayhoadon, ngayghinhan from erp_giamgiahangmua where pk_seq = '" + Id + "'";
			
			String ngayhoadon = "";
			String ngayghinhan="";
			String ncc_fk = "";
			ResultSet rs = db.get(query);
			if(rs.next())
			{
				ngayhoadon = rs.getString("ngayhoadon");
				ngayghinhan= rs.getString("ngayghinhan");
				ncc_fk = rs.getString("ncc_fk");
			}
			
			String nam = ngayhoadon.substring(0, 4);
			String thang = ngayhoadon.substring(5, 7);
			
			query = " select GG.ncc_fk ,GGSP.sanpham_fk, GGSP.sotien ,GGSP.sotien * GG.vat / 100 as vat, \n"+
							"(select TAIKHOAN_FK from ERP_NHACUNGCAP where pk_seq=GG.ncc_fk) as taikhoanNO, \n"+
							"( select TAIKHOANKT_FK from ERP_LOAISANPHAM where PK_SEQ= sp.LOAISANPHAM_FK ) as taikhoanCO_DS," +
							"(select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '13311000') as taikhoanCO_VAT \n"+
	               "  from ERP_GiamGiaHangMua GG inner join erp_giamgiahangmua_sanpham GGSP on GG.pk_seq=GGSP.giamgiamua_fk \n"+
	 			   "			                 inner join ERP_SANPHAM  SP on GGSP.sanpham_fk= SP.PK_SEQ \n"+
	 			   " where GG.pk_seq= "+Id+" ";
			
			System.out.println("Ke toan "+query);
			ResultSet rsSp = db.get(query);
			if(rsSp != null)
			{
				String taikhoanktNo = "";
				String taikhoanktCo_DS = "";
				String taikhoanktCo_VAT = "";
				
				String doituong_no = "";
				String madoituong_no = "";
				String doituong_co = "";
				String madoituong_co = "";
				
				while(rsSp.next())
				{
					taikhoanktNo = rsSp.getString("taikhoanNO") == null ? "" : rsSp.getString("taikhoanNO") ;
					taikhoanktCo_DS = rsSp.getString("taikhoanCO_DS") == null ? "" : rsSp.getString("taikhoanCO_DS") ;
					taikhoanktCo_VAT = rsSp.getString("taikhoanCO_VAT") == null ? "" : rsSp.getString("taikhoanCO_VAT") ;
					
					double sotienGIAM = Math.round(rsSp.getDouble("sotien"));
					if(sotienGIAM > 0)
					{
						doituong_no = "Sản phẩm";
						madoituong_no = rsSp.getString("sanpham_fk");
						doituong_co = "Nhà cung cấp";
						madoituong_co = rsSp.getString("ncc_fk");
						
						if(taikhoanktNo.trim().length() <= 0 || taikhoanktCo_DS.trim().length() <= 0)
						{
							msg = "Loại sản phẩm và nội dung nhập tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
							db.getConnection().rollback();
							return msg;
						}
						
						String tiente_fk = "100000";
						msg = util.Update_TaiKhoan( db, thang, nam, ngayhoadon, ngayhoadon, "Giảm giá hàng mua", Id, taikhoanktNo, taikhoanktCo_DS, "", 
													Double.toString(sotienGIAM), Double.toString(sotienGIAM), doituong_no, madoituong_no, doituong_co, madoituong_co, "0", "", "", tiente_fk, "", "1", Double.toString(sotienGIAM), Double.toString(sotienGIAM), "Doanh số" );
						if(msg.trim().length() > 0)
						{
							rsSp.close();
							return msg;
						}
						
					}
					
					double sotienGIAM_VAT = Math.round(rsSp.getDouble("vat"));
					if(sotienGIAM_VAT > 0)
					{
						doituong_no = "Nhà cung cấp";
						madoituong_no = rsSp.getString("ncc_fk");
						doituong_co = "Sản phẩm";
						madoituong_co = rsSp.getString("ncc_fk");
						
						if(taikhoanktNo.trim().length() <= 0 || taikhoanktCo_VAT.trim().length() <= 0)
						{
							msg = "Loại sản phẩm và nội dung nhập tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
							db.getConnection().rollback();
							return msg;
						}
						
						String tiente_fk = "100000";
						msg = util.Update_TaiKhoan( db, thang, nam, ngayhoadon, ngayhoadon, "Giảm giá hàng mua", Id, taikhoanktNo, taikhoanktCo_VAT, "", 
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
			System.out.println("__Exception chot: " + e.getMessage());
		}
    	
    	db.shutDown();
    	
		return msg;
			
			/*query = "select b.LOAISANPHAM_FK, a.sotien, a.sotien * 0.1 as VAT, c.TAIKHOANKT_FK   " +
					"from erp_giamgiahangmua_sanpham a inner join ERP_SANPHAM b on a.sanpham_fk = b.PK_SEQ   " +
					"inner join ERP_LOAISANPHAM c on b.LOAISANPHAM_FK = b.PK_SEQ  " +
					"where a.giamgiamua_fk = '" + Id + "' ";
			ResultSet rsSp = db.get(query);
			if(rsSp != null)
			{
				while(rsSp.next())
				{
					
					
					//GHI NO TIEN GIAM
					query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + rsSp.getDouble("sotien") + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + rsSp.getDouble("sotien") + 
							" where taikhoankt_fk = ( select taikhoan_fk from ERP_NhaCungCap where pk_seq = '" + ncc_fk + "' ) " +
									"and nguyente_fk = '100000' and thang = '" + thang + "' and nam = '" + nam + "'";
					if(!db.update(query))
			    	{
			    		db.getConnection().rollback();
			    		msg = "Không thể cập nhật ERP_TAIKHOAN_NOCO " + query;
			    		return msg;
			    	}
					
					//GHI CO TIEN GIAM
					query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + rsSp.getDouble("sotien") + ", " +
														" GIATRINGUYENTE = GIATRINGUYENTE + "  + rsSp.getDouble("sotien") + 
							" where taikhoankt_fk = '" + rsSp.getString("TAIKHOANKT_FK") + "' and nguyente_fk = '100000' and thang = '" + thang + "' and nam = '" + nam + "'";
					if(!db.update(query))
					{
						db.getConnection().rollback();
						msg = "Không thể cập nhật ERP_TAIKHOAN_NOCO " + query;
						return msg;
					}
					
					//GHI NO VAT
					query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + rsSp.getDouble("VAT") + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + rsSp.getDouble("VAT") + 
							" where taikhoankt_fk = ( select taikhoan_fk from ERP_NhaCungCap where pk_seq = '" + ncc_fk + "' ) " +
									"and nguyente_fk = '100000' and thang = '" + thang + "' and nam = '" + nam + "'";
					if(!db.update(query))
					{
						db.getConnection().rollback();
						msg = "Không thể cập nhật ERP_TAIKHOAN_NOCO " + query;
						return msg;
					}
					
					//GHI CO VAT
					query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + rsSp.getDouble("VAT") + ", " +
												" GIATRINGUYENTE = GIATRINGUYENTE + "  + rsSp.getDouble("VAT") + 
							" where taikhoankt_fk = ( select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '133000' ) " +
									"and nguyente_fk = '100000' and thang = '" + thang + "' and nam = '" + nam + "'";
					if(!db.update(query))
					{
						db.getConnection().rollback();
						msg = "Không thể cập nhật ERP_TAIKHOAN_NOCO " + query;
						return msg;
					}
					
				}
				rsSp.close();
			}*/
	    	
			
	
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
	    
	    
	    IErpGiamgiahangmuaList obj;
	    
		String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    if(action.equals("new"))
	    {
    		IErpGiamgiahangmua khl = new ErpGiamgiahangmua();
    		khl.setCongtyId(ctyId);
    		khl.setUserId(userId);

	    	session.setAttribute("csxBean", khl);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpGiamGiaHangMuaNew.jsp");
	    }
	    else
	    {
	    	obj = new ErpGiamgiahangmuaList();
	    	obj.setCongtyId(ctyId);
		    obj.setUserId(userId);

	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpGiamGiaHangMua.jsp");	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpGiamgiahangmuaList obj) 
	{
		String ma = request.getParameter("magiamgiahangmua");
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
		
		String  sql = " select a.pk_seq, d.ten as nccTen, a.diengiai, " +
					  " a.trangthai, b.ten as nguoitao, a.ngaytao, c.ten as nguoisua, a.ngaysua      " +
					  " from ERP_GiamGiaHangMua a " +
					  " inner join ERP_GIAMGIAHANGMUA_HOADON HD ON HD.GIAMGIA_FK = a.PK_SEQ " + 
					  " inner join NhanVien b on a.nguoitao = b.pk_seq      " +
					  " inner join nhanvien c on a.nguoisua = c.pk_seq " +
					  " inner join ERP_NhaCungCap d on a.ncc_fk = d.pk_seq   " +
					  
					  "where 1 = 1  ";
		
		if(ma.length() > 0)
			sql += " and HD.HOADON_FK like N'%" + ma + "%' ";
		
		if(diengiai.length() > 0)
			sql += " and a.diengiai like N'%" + diengiai + "%' ";
		
		if(trangthai.length() > 0)
			sql += " and a.trangthai = '" + trangthai + "' ";
		
		sql += " order by a.pk_seq desc ";
		
		System.out.println(sql);
		return sql;
	}
	
	

}
