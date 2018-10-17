package geso.traphaco.center.servlets.dieuchinhtonkho;

import geso.traphaco.center.beans.dieuchinhtonkho.IErpDctkTT;
import geso.traphaco.center.beans.dieuchinhtonkho.IErpDctkTTList;
import geso.traphaco.center.beans.dieuchinhtonkho.imp.ErpDctkTT;
import geso.traphaco.center.beans.dieuchinhtonkho.imp.ErpDctkTTList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpDctkTTSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpDctkTTSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpDctkTTList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    
	    String lsxId = util.getId(querystring);
	    obj = new ErpDctkTTList();
	    
	    if (action.equals("delete"))
	    {	
	    	String msg = this.DeleteChuyenKho(lsxId);
			obj.setMsg(msg);
	    }
	    else
	    {
	    	if(action.equals("chot"))
	    	{
	    		String msg = this.Chot(lsxId);
    			obj.setMsg(msg);
	    	}
	    }
	    
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
			
		String nextJSP = "/TraphacoHYERP/pages/Center/ErpDieuChinhTonKhoTT.jsp";
		response.sendRedirect(nextJSP);
	}

	private String Chot(String lsxId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
		
			String query =  "select b.KhoNhap_FK, sanpham_fk, a.soluong, a.solo, a.gianhap, a.ngaysanxuat, a.ngayhethan, " +
							"	isnull( ( select COUNT(*) from ERP_KHOTT_SANPHAM where KHOTT_FK = b.KhoNhap_FK and sanpham_fk = a.sanpham_fk ), 0) as exitKHOTT, " +
							"	isnull( ( select COUNT(*) from ERP_KHOTT_SP_CHITIET where KHOTT_FK = b.KhoNhap_FK and sanpham_fk = a.sanpham_fk and solo = a.solo ), 0) as exitKHOTT_CT, " +
							"	isnull(	( select SOLUONG1 / isnull(nullif( SOLUONG2, 0), 1) from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk ), 0) as quyCACH, " +
							"	( select TEN from SANPHAM where pk_seq = a.sanpham_fk ) as spTEN	 " +
							"from ERP_NHAPKHO_SANPHAM a inner join ERP_NHAPKHO b on a.nhapkho_fk = b.PK_SEQ " +
							"where b.PK_SEQ = '" + lsxId + "' ";
			System.out.println("---NHAP KHO: " + query );
			
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				while(rs.next())
				{
					String khoNHAP = rs.getString("KhoNhap_FK");
					String spIds = rs.getString("sanpham_fk");
					double soluong = rs.getDouble("soluong");
					String solo = rs.getString("solo");
					String gianhap = rs.getString("gianhap");
					String ngaysanxuat = rs.getString("ngaysanxuat");
					String ngayhethan = rs.getString("ngayhethan");
					int exitKHOTT = rs.getInt("exitKHOTT");
					int exitKHOTT_CT = rs.getInt("exitKHOTT_CT");
					double quyCACH = rs.getDouble("quyCACH");
					
					if(quyCACH <= 0)
					{
						msg = "Sản phẩm ( " + rs.getString("spTEN") + " ) chưa thiết lập quy đổi ra thùng. Vui lòng kiểm tra lại. ";
						db.getConnection().rollback();
						return msg;
					}
					
					soluong = soluong * quyCACH;
					
					if(exitKHOTT > 0)
					{
						query = "UPDATE ERP_KHOTT_SANPHAM set soluong = soluong + '" + soluong + "', available = available + '" + soluong + "', " +
								"		giaton = ( ( ( giaton * soluong ) + ( " + gianhap + " * " + soluong + " ) ) / ( soluong + " + soluong + " ) ) " +
								"where khott_fk = '" + khoNHAP + "' and sanpham_fk = '" + spIds + "' ";
					}
					else
					{
						query = "insert ERP_KHOTT_SANPHAM(KHOTT_FK, SANPHAM_FK, GIATON, SOLUONG, BOOKED, AVAILABLE, THANHTIEN) " +
								"values( '" + khoNHAP + "', '" + spIds + "', '" + gianhap + "', '" + soluong + "', '0', '" + soluong + "', '0' )  ";
					}
					
					System.out.println("---KHO : " + query );
					if(!db.update(query))
					{
						msg = "Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;
						db.getConnection().rollback();
						return msg;
					}
					
					
					if(exitKHOTT_CT > 0)
					{
						query = "UPDATE ERP_KHOTT_SP_CHITIET set soluong = soluong + '" + soluong + "', available = available + '" + soluong + "', " +
								"		ngaysanxuat = '" + ngaysanxuat + "', ngayhethan = '" + ngayhethan + "' " +
								"where khott_fk = '" + khoNHAP + "' and sanpham_fk = '" + spIds + "' and SOLO = N'" + solo.trim() + "' ";
					}
					else
					{
						query = "insert ERP_KHOTT_SP_CHITIET(KHOTT_FK, SANPHAM_FK, SOLO, SOLUONG, BOOKED, AVAILABLE, NGAYSANXUAT, NGAYHETHAN) " +
								"values( '" + khoNHAP + "', '" + spIds + "', N'" + solo.trim() + "', '" + soluong + "', '0', '" + soluong + "', '" + ngaysanxuat + "', '" + ngayhethan + "' )  ";
					}
					
					System.out.println("---KHO CHI TIET: " + query );
					if(!db.update(query))
					{
						msg = "Khong the cap nhat ERP_KHOTT_SP_CHITIET: " + query;
						db.getConnection().rollback();
						return msg;
					}
					
				}
				rs.close();
			}
			
			query = "update ERP_NHAPKHO set trangthai = '1' where pk_seq = '" + lsxId + "'";
			if(!db.update(query))
			{
				msg = "Khong the chot: " + query;
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

	private String DeleteChuyenKho(String lsxId)
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
		
			//
			String query = "delete ERP_DCTK_SANPHAM where dctk_fk = '" + lsxId + "' ";
			if(!db.update(query))
			{
				msg = "1.Khong the xoa: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "delete ERP_DIEUCHINHTONKHO where pk_seq = '" + lsxId + "'";
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
	    
		IErpDctkTTList obj = new ErpDctkTTList();
	 
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpDctkTT lsxBean = new ErpDctkTT();
	    	lsxBean.createRs();
    		
	    	session.setAttribute("lsxBean", lsxBean);
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Center/ErpDieuChinhTonKhoTTNew.jsp";
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
		    	
		    	String nextJSP = "/TraphacoHYERP/pages/Center/ErpDieuChinhTonKhoTT.jsp";
				response.sendRedirect(nextJSP);
		    }
	    	else
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.init(search);
				obj.setUserId(userId);
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		String nextJSP = "/TraphacoHYERP/pages/Center/ErpDieuChinhTonKhoTT.jsp";
	    		response.sendRedirect(nextJSP);
	    		
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpDctkTTList obj)
	{
		String query =  "select a.PK_SEQ, a.trangthai, a.ngaydieuchinh, a.lydo, NV.TEN as nguoitao, b.ten as khodieuchinh, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua   " +
						"from ERP_DIEUCHINHTONKHO a inner join ERP_KHOTT b on a.khott_fk = b.pk_seq  " +
						"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
						"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.pk_seq > 0 ";
		
		String tungaySX = request.getParameter("tungay");
		if(tungaySX == null)
			tungaySX = "";
		obj.setTungayTao(tungaySX);
		
		String denngaySX = request.getParameter("denngay");
		if(denngaySX == null)
			denngaySX = "";
		obj.setDenngayTao(denngaySX);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		

		if(tungaySX.length() > 0)
			query += " and a.ngaydieuchinh >= '" + tungaySX + "'";
		
		if(denngaySX.length() > 0)
			query += " and a.ngaydieuchinh <= '" + denngaySX + "'";
	
		if(trangthai.length() > 0)
			query += " and a.TrangThai = '" + trangthai + "'";
		
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
