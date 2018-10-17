package geso.traphaco.erp.servlets.thuenhapkhau;

import geso.traphaco.erp.beans.thuenhapkhau.*;
import geso.traphaco.erp.beans.thuenhapkhau.imp.*;
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

public class ErpThuenhapkhauSvl_bk extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public ErpThuenhapkhauSvl_bk() {
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
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    IErpThuenhapkhauList obj = new ErpThuenhapkhauList();
	    String ctyId = (String)session.getAttribute("congtyId");
	    obj.setUserId(userId);
	    obj.setCongtyId(ctyId);
	    
	    String action = util.getAction(querystring);
	    String Id = util.getId(querystring);
	    String msg = "";
	    
	    if(action.trim().equals("delete"))
	    {
	    	dbutils db = new dbutils();
	    	if(!db.update("update ERP_THUENHAPKHAU set TRANGTHAI = '2' where PK_SEQ = '" + Id + "'"))
	    	{
	    		msg = "Không thể xóa ERP_THUENHAPKHAU";
	    	}
	    	db.shutDown();
	    }
	    
	    if(action.trim().equals("chotthue"))
	    {
	    	msg = ChotThue_NhapKhau(Id);
	    }
	    
	    if(action.trim().equals("chotVAT"))
	    {
	    	msg = ChotThue_VAT(Id);
	    }
	    
	    obj.init("");
	    obj.setMsg(msg);
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpThueNhapKhau.jsp";
		response.sendRedirect(nextJSP);
	}

	private String ChotThue_NhapKhau(String Id) 
	{
		String msg = "";
		
		dbutils db = new dbutils();
		
		Utility util = new Utility();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query =   "select  case when c.LOAIHANGHOA_FK = 0 then e.TAIKHOANKT_FK else g.taikhoan_fk end as sohieutaikhoanNO_DS,  " + 
							 "		( select taikhoan_fk from ERP_NHACUNGCAP where pk_seq = a.Coquanthue_FK ) as sohieutaikhanCO_DS, " + 
							 "		( select pk_seq from ERP_TAIKHOANKT  " + 
							 "				where SOHIEUTAIKHOAN = ( case when c.LOAIHANGHOA_FK = 0 then '133000' else '133000' end ) ) as sohieutaikhoanNO_VAT,   " + 
							 "		( select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '333120' ) as sohieutaikhanCO_VAT,  " + 
							 "		a.ngay as ngayhoadon, b.THUENHAPKHAU, a.VAT  " + 
							 "from ERP_THUENHAPKHAU a inner join ERP_THUENHAPKHAU_CHITIET b on a.PK_SEQ = b.THUENHAPKHAU_FK  " + 
							 "		inner join ERP_NHANHANG c on a.PHIEUNHAPKHO_FK = c.PK_SEQ " + 
							 "		left join ERP_SANPHAM d on b.SANPHAM_FK = d.PK_SEQ " + 
							 "		left join ERP_LOAISANPHAM e on d.LOAISANPHAM_FK = e.PK_SEQ " + 
							 "		left join ERP_TAISANCODINH f on b.SANPHAM_FK = f.pk_seq " + 
							 "		left join ERP_LOAITAISAN g on f.loaitaisan_fk = g.pk_seq " + 
							 "where a.PK_SEQ = '" + Id + "' "  ;
			
			System.out.println("____KE TOAN: " + query);
			String taikhoanCO_DS = "";
			String taikhoanNO_DS = "";
			
			String taikhoanCO_VAT = "";
			String taikhoanNO_VAT = "";
			
			String loaidoituongCO_DS = "";
			String madoituongCO_DS = "";
			
			String loaidoituongNO = "";
			String madoituongNO = "";
			
			ResultSet rsTk = db.get(query);
			while(rsTk.next())
			{
				double totalDS = Math.round(rsTk.getDouble("THUENHAPKHAU"));
				double totalVAT = Math.round( totalDS * rsTk.getDouble("VAT") / 100 );
				
				taikhoanCO_DS = rsTk.getString("sohieutaikhanCO_DS");
				taikhoanNO_DS = rsTk.getString("sohieutaikhoanNO_DS");
				
				taikhoanCO_VAT = rsTk.getString("sohieutaikhoanNO_VAT");
				taikhoanNO_VAT = rsTk.getString("sohieutaikhanCO_VAT");
				
				/*loaidoituongCO_DS = rsTk.getString("loaidoituongCO_DS");
				madoituongCO_DS = rsTk.getString("madoituongCO_DS");
				loaidoituongNO = rsTk.getString("loaidoituongNO");
				madoituongNO = rsTk.getString("madoituongNO");*/
				
				String ngayghinhan = rsTk.getString("ngayhoadon");
				String nam = ngayghinhan.substring(0, 4);
				String thang = ngayghinhan.substring(5, 7);
				
				String tiente_fk = "100000";
				
				if(totalDS > 0)
				{
					if(taikhoanCO_DS.trim().length() <= 0 || taikhoanNO_DS.trim().length() <= 0 )
					{
						msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
						db.getConnection().rollback();
						return msg;
					}
					
					msg = util.Update_TaiKhoan( db, thang, nam, ngayghinhan, ngayghinhan, "Thuế nhập khẩu", Id, taikhoanNO_DS, taikhoanCO_DS, "", 
									Double.toString(totalDS), Double.toString(totalDS), loaidoituongNO, madoituongNO, loaidoituongCO_DS, madoituongCO_DS, "0", "", "", tiente_fk, "", "1", Double.toString(totalDS), Double.toString(totalDS), "Doanh số" );
					if(msg.trim().length() > 0)
					{
						db.getConnection().rollback();
						return msg;
					}
				}
				
				/*if(totalVAT > 0)
				{
					if(taikhoanCO_VAT.trim().length() <= 0 || taikhoanNO_VAT.trim().length() <= 0 )
					{
						msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
						db.getConnection().rollback();
						return msg;
					}
					
					msg = util.Update_TaiKhoan( db, thang, nam, ngayghinhan, ngayghinhan, "Thuế nhập khẩu", Id, taikhoanNO_VAT, taikhoanCO_VAT, "", 
									Double.toString(totalVAT), Double.toString(totalVAT), loaidoituongNO, madoituongNO, "", "", "0", "", "", tiente_fk, "", "1", Double.toString(totalVAT), Double.toString(totalVAT), "VAT" );
					if(msg.trim().length() > 0)
					{
						db.getConnection().rollback();
						return msg;
					}
				}*/
			
			}
			rsTk.close();
			
			
			query = "update ERP_THUENHAPKHAU set TRANGTHAI = '1' where PK_SEQ = '" + Id + "'";
			if(!db.update(query))
	    	{
				db.getConnection().rollback();
	    		msg = "Không thể chot ERP_THUENHAPKHAU " + query;
	    		return msg;
	    	}
			
			db.getConnection().commit();
			db.shutDown();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			return "Lỗi chốt thuế nhập khẩu " + e.getMessage();
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
	    
	    
	    IErpThuenhapkhauList obj;
	    
		String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    if(action.equals("new"))
	    {
    		IErpThuenhapkhau tnk = new ErpThuenhapkhau();
    		tnk.setCongtyId(ctyId);
    		tnk.setUserId(userId);
    		tnk.createRs();

	    	session.setAttribute("tnkBean", tnk);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpThueNhapKhauNew.jsp");
	    }
	    else
	    {
	    	obj = new ErpThuenhapkhauList();
	    	obj.setCongtyId(ctyId);
		    obj.setUserId(userId);

	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpThueNhapKhau.jsp");	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpThuenhapkhauList obj) 
	{
		String poId = request.getParameter("poId");
		if(poId == null)
			poId = "";
		obj.setPoId(poId);

		String ncc = request.getParameter("ncc");
		if(ncc == null)
			ncc = "";
		obj.setNcc(ncc);
		
		String nccId = request.getParameter("nccId");
		if(nccId == null)
			nccId = "";
		obj.setNccId(nccId);

		String diengiai = request.getParameter("diengiai");
		if(diengiai == null)
			diengiai = "";
		obj.setDiengiai(diengiai);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String  sql = 	"SELECT TNK.PK_SEQ AS TNKID, TNK.DIENGIAI, TNK.NGAY, NH.PK_SEQ AS PHIEUNHAP, TNK.TRANGTHAI, " +
						"NV1.TEN AS NGUOITAO, TNK.NGAYTAO, NV2.TEN AS NGUOISUA, TNK.NGAYSUA " +
						"FROM ERP_THUENHAPKHAU TNK " +
						"INNER JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = TNK.NCC_FK " +
						"INNER JOIN ERP_NHANHANG NH ON NH.PK_SEQ = TNK.PHIEUNHAPKHO_FK " +
						"INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ = NH.MUAHANG_FK " +
						"INNER JOIN NHANVIEN NV1 ON NV1.PK_SEQ = TNK.NGUOITAO " +
						"INNER JOIN NHANVIEN NV2 ON NV2.PK_SEQ = TNK.NGUOISUA " +
						"WHERE 1 = 1 ";
		
		
		if(nccId.length() > 0)
			sql += " and NCC.PK_SEQ = '" + nccId + "' ";
		
		if(poId.length() > 0)
			sql += " and MH.PK_SEQ = '" + poId + "' ";
		
		if(diengiai.length() > 0)
			sql += " and TNK.DIENGIAI like N'%" + diengiai + "%' ";
		
		if(trangthai.length() > 0)
			sql += " and TNK.TRANGTHAI = '" + trangthai + "' ";
		
		sql += " ORDER BY TNK.PK_SEQ DESC ";
		
		return sql;
	}
	
	private String ChotThue_VAT(String Id) 
	{
		String msg = "";
		
		dbutils db = new dbutils();
		
		Utility util = new Utility();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query =   "select  case when c.LOAIHANGHOA_FK = 0 then e.TAIKHOANKT_FK else g.taikhoan_fk end as sohieutaikhoanNO_DS,  " + 
							 "		( select taikhoan_fk from ERP_NHACUNGCAP where pk_seq = a.Coquanthue_FK ) as sohieutaikhanCO_DS, " + 
							 "		( select pk_seq from ERP_TAIKHOANKT  " + 
							 "				where SOHIEUTAIKHOAN = ( case when c.LOAIHANGHOA_FK = 0 then '133000' else '133000' end ) ) as sohieutaikhoanNO_VAT,   " + 
							 "		( select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '333120' ) as sohieutaikhanCO_VAT,  " + 
							 "		a.ngay as ngayhoadon, b.THUENHAPKHAU, a.VAT  " + 
							 "from ERP_THUENHAPKHAU a inner join ERP_THUENHAPKHAU_CHITIET b on a.PK_SEQ = b.THUENHAPKHAU_FK  " + 
							 "		inner join ERP_NHANHANG c on a.PHIEUNHAPKHO_FK = c.PK_SEQ " + 
							 "		left join ERP_SANPHAM d on b.SANPHAM_FK = d.PK_SEQ " + 
							 "		left join ERP_LOAISANPHAM e on d.LOAISANPHAM_FK = e.PK_SEQ " + 
							 "		left join ERP_TAISANCODINH f on b.SANPHAM_FK = f.pk_seq " + 
							 "		left join ERP_LOAITAISAN g on f.loaitaisan_fk = g.pk_seq " + 
							 "where a.PK_SEQ = '" + Id + "' "  ;
			
			System.out.println("____KE TOAN: " + query);
			
			String taikhoanCO_VAT = "";
			String taikhoanNO_VAT = "";
			
			String loaidoituongNO = "";
			String madoituongNO = "";
			
			ResultSet rsTk = db.get(query);
			while(rsTk.next())
			{
				double totalDS = Math.round(rsTk.getDouble("THUENHAPKHAU"));
				double totalVAT = Math.round( totalDS * rsTk.getDouble("VAT") / 100 );
								
				taikhoanCO_VAT = rsTk.getString("sohieutaikhoanNO_VAT");
				taikhoanNO_VAT = rsTk.getString("sohieutaikhanCO_VAT");
				
				/*loaidoituongCO_DS = rsTk.getString("loaidoituongCO_DS");
				madoituongCO_DS = rsTk.getString("madoituongCO_DS");
				loaidoituongNO = rsTk.getString("loaidoituongNO");
				madoituongNO = rsTk.getString("madoituongNO");*/
				
				String ngayghinhan = rsTk.getString("ngayhoadon");
				String nam = ngayghinhan.substring(0, 4);
				String thang = ngayghinhan.substring(5, 7);
				
				String tiente_fk = "100000";
								
				if(totalVAT > 0)
				{
					if(taikhoanCO_VAT.trim().length() <= 0 || taikhoanNO_VAT.trim().length() <= 0 )
					{
						msg = "Lá»—i xÃ¡c Ä‘á»‹nh tÃ i khoáº£n káº¿ toÃ¡n. Vui lÃ²ng kiá»ƒm tra láº¡i thÃ´ng tin dá»¯ liá»‡u ná»�n trÆ°á»›c khi chá»‘t.";
						db.getConnection().rollback();
						return msg;
					}
					
					msg = util.Update_TaiKhoan( db, thang, nam, ngayghinhan, ngayghinhan, "Thuáº¿ nháº­p kháº©u", Id, taikhoanNO_VAT, taikhoanCO_VAT, "", 
									Double.toString(totalVAT), Double.toString(totalVAT), loaidoituongNO, madoituongNO, "", "", "0", "", "", tiente_fk, "", "1", Double.toString(totalVAT), Double.toString(totalVAT), "VAT" );
					if(msg.trim().length() > 0)
					{
						db.getConnection().rollback();
						return msg;
					}
				}
			
			}
			rsTk.close();
			
			
			query = "update ERP_THUENHAPKHAU set TRANGTHAI = '2' where PK_SEQ = '" + Id + "'";
			if(!db.update(query))
	    	{
				db.getConnection().rollback();
	    		msg = "Khong the chot ERP_THUENHAPKHAU " + query;
	    		return msg;
	    	}
			
			db.getConnection().commit();
			db.shutDown();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			return "Lá»—i chá»‘t thuáº¿ nháº­p kháº©u " + e.getMessage();
		}

    	return msg;
    	
	}
	

}
