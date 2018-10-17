package geso.traphaco.distributor.servlets.dondathang;

import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.dondathang.IErpDenghidathangNpp;
import geso.traphaco.distributor.beans.dondathang.IErpDenghidathangNppList;
import geso.traphaco.distributor.beans.dondathang.imp.ErpDenghidathangNpp;
import geso.traphaco.distributor.beans.dondathang.imp.ErpDenghidathangNppList;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpDenghidathangNppSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpDenghidathangNppSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpDenghidathangNppList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    
    	String lsxId = util.getId(querystring);
	    obj = new ErpDenghidathangNppList();
	    
	    String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "0";
	    obj.setLoaidonhang(loaidonhang);
	    System.out.println("---LOAI DON HANG: " + loaidonhang);
	    
	    if (action.equals("delete") )
	    {	
	    	String msg = this.DeleteChuyenKho(lsxId);
			obj.setMsg(msg);
	    }
	    else if(action.equals("chot"))
    	{
    		String msg = this.Chot(lsxId);
			obj.setMsg(msg);
    	}
	    else if(action.equals("convert"))
    	{
    		String msg = this.ConvertPO(lsxId);
			obj.setMsg(msg);
    	}
	    
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
			
		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDeNghiDatHangNpp.jsp";
		response.sendRedirect(nextJSP);
	    
	}

	private String ConvertPO(String lsxId)
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
		
			String query = "update ERP_Denghidathang set trangthai = '2' where pk_seq = '" + lsxId + "' and TrangThai!=2 ";
			if(db.updateReturnInt(query)!=1)
			{
				msg = "Đề nghị này đã chốt ,vui lòng kiểm tra lại. ";
				db.getConnection().rollback();
				return msg;
			}
			
			Utility util = new Utility();
			msg= util.Check_Huy_NghiepVu_KhoaSo("ERP_DeNghiDatHang", lsxId, "ngaydenghi", db);
			if(msg.length()>0)
			{
				db.getConnection().rollback();
				return msg;
			}
			
			//XAC DINH CẤP TRÊN CỦA NPP ĐỂ BIẾT LÀ ĐẶT TRỰC TIẾP HO hay đặt qua CN01, CN02, đối tác
			query = " insert ERP_DonDatHang(ngaydonhang, ngaydenghi, loaidonhang, ghichu, trangthai, dvkd_fk, kbh_fk, npp_fk, kho_fk, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua, NOTE, NPP_DACHOT, TRUCTHUOC_FK) " +
					" select ngaydenghi, ngaydenghi, 0, ghichu, 0 as trangthai, dvkd_fk, kbh_fk, npp_fk, kho_fk, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua, N'Convert từ đề nghị số  " + lsxId + "', '0', ( select tructhuoc_fk from NHAPHANPHOI where pk_seq = a.NPP_FK )  " +
					" from ERP_DeNghiDatHang a where pk_seq = '" + lsxId + "' ";
			
			System.out.println("1.Insert DDH: " + query);
			if(!db.update(query))
			{
				msg = "Không thể tạo mới ERP_DeNghiDatHang " + query;
				db.getConnection().rollback();
				return msg;
			} 
			
			query = "insert ERP_DONDATHANG_SANPHAM(dondathang_fk, sanpham_fk, soluong, dongia, dvdl_fk)  " +
					"select scope_identity(), sanpham_fk, denghi, dongia, dvdl_fk   " +
					"from ERP_DeNghiDatHang_SANPHAM  " +
					"where denghidathang_fk = '" + lsxId + "' and denghi != 0";
			System.out.println("1.Insert DDH - SP: " + query);
			if(!db.update(query))
			{
				msg = "Không thể tạo mới ERP_DeNghiDatHang " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			db.getConnection().commit();
			db.shutDown();
		}
		catch (Exception e) 
		{
			db.update("rollback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		
		return "Chuyển thành đơn đặt hàng thành công ";
	}

	private String Chot(String lsxId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			String query = "update ERP_Denghidathang set trangthai = '1' where pk_seq = '" + lsxId + "'";
			if(!db.update(query))
			{
				msg = "Khong the chot: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			Utility util = new Utility();
			msg= util.Check_Huy_NghiepVu_KhoaSo("ERP_DeNghiDatHang", lsxId, "ngaydenghi", db);
			if(msg.length()>0)
			{
				db.getConnection().rollback();
				return msg;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			db.update("rollback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		finally
		{
			if(db!=null)db.shutDown();
		}
		
		return "";
	}

	private String DeleteChuyenKho(String lsxId)
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);

			String query = "Delete ERP_Denghidathang where pk_seq = '" + lsxId + "'";
			if(!db.update(query))
			{
				msg = "1.Khong the xoa: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "Delete ERP_Denghidathang_SanPham where denghidathang_fk = '" + lsxId + "'";
			if(!db.update(query))
			{
				msg = "2.Khong the xoa: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			db.getConnection().commit();
			db.shutDown();
		}
		catch (Exception e) 
		{
			db.update("rollback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		
		return "";
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    String action = request.getParameter("action");
	    if (action == null)
	    {
	    	action = "";
	    }
	    
	    String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "0";
	    
	    
		IErpDenghidathangNppList obj = new ErpDenghidathangNppList();
		obj.setLoaidonhang(loaidonhang);
	 
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpDenghidathangNpp lsxBean = new ErpDenghidathangNpp();
	    	lsxBean.setLoaidonhang(loaidonhang);
	    	lsxBean.setUserId(userId);
	    	
	    	lsxBean.createRs();
	    	session.setAttribute("dvkdId", lsxBean.getDvkdId());
	    	session.setAttribute("kbhId", lsxBean.getKbhId());
	    	session.setAttribute("nppId", lsxBean.getNppId());
	    	session.setAttribute("lsxBean", lsxBean);
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDeNghiDatHangNppNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setUserId(userId);
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	
		    	String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDeNghiDatHangNpp.jsp";
				response.sendRedirect(nextJSP);
		    }
	    	else
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.init(search);
				obj.setUserId(userId);
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDeNghiDatHangNpp.jsp";
	    		response.sendRedirect(nextJSP);
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpDenghidathangNppList obj)
	{
		String query = "select a.PK_SEQ, a.trangthai, a.ngaydenghi as ngaydonhang, isnull(c.ten, d.ten) as nppTEN, b.ten as khoTEN, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua   " +
						"from ERP_Denghidathang a inner join NHAPHANPHOI c on a.NPP_FK = c.pk_seq " +
						"	left join ERP_KHOTT b on a.kho_fk = b.pk_seq  " +
						"	left join KHO d on a.kho_fk = d.pk_seq  " +
						"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
						"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.pk_seq > 0 ";
		
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
		
		String nppId = request.getParameter("nppId");
		if(nppId == null)
			nppId = "";
		obj.setNppTen(nppId);
		
		if(tungay.length() > 0)
			query += " and a.ngaydenghi >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngaydenghi <= '" + denngay + "'";
	
		if(trangthai.length() > 0)
			query += " and a.TrangThai = '" + trangthai + "'";
		
		if(nppId.length() > 0){
			query += " and a.NPP_FK= '" + nppId + "'";
		}
		
		System.out.print(query);
		return query;
	}
		
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
}
