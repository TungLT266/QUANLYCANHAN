package geso.traphaco.center.servlets.nhapkho;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.beans.nhapkho.IErpNhapkho;
import geso.traphaco.center.beans.nhapkho.IErpNhapkhoList;
import geso.traphaco.center.beans.nhapkho.imp.ErpNhapkho;
import geso.traphaco.center.beans.nhapkho.imp.ErpNhapkhoList;
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

public class ErpNhapkhoSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpNhapkhoSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpNhapkhoList obj;
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
	    obj = new ErpNhapkhoList();
	    
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
			
		String nextJSP = "/TraphacoHYERP/pages/Center/ErpNhapKho.jsp";
		response.sendRedirect(nextJSP);
	}

	private String Chot(String lsxId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{	
			//CHECK NGAY KHOA SO
			//THANG CHOT PHIEU XUAT KHO BUOC PHAI SAU THANG KS + 1
			/*String query = "select top(1) NAM as namMax, THANGKS as thangMax, " +
							"	( select ngaynhap from ERP_NHAPKHO where pk_seq = '" + lsxId + "' ) as ngaylapphieu " +
							"from ERP_KHOASOTHANG  " +
							"order by NAM desc, THANGKS desc ";
			System.out.println("1.Khoi tao thang: " + query);
			ResultSet rs = db.get(query);
			
			try
			{
				if(rs != null)
				{
					while(rs.next())
					{
						String thangHL = "";
						String namHL = "";
						
						String thangKs = rs.getString("thangMax");
						String namKs = rs.getString("namMax"); 
				
						String nam = rs.getString("ngaylapphieu").substring(0, 4);
						String thang = rs.getString("ngaylapphieu").substring(5, 7);
						
						if(thangKs.equals("12"))
						{
							thangHL = "1";
							namHL = Integer.toString(Integer.parseInt(namKs) + 1);
						}
						else
						{
							thangHL = Integer.toString(Integer.parseInt(thangKs) + 1);
							namHL = namKs;
						}
						
						if(thangHL.trim().length() < 2)
							thangHL = "0" + thangHL;
						
						if(	!thangHL.equals(thang) || !namHL.equals(nam) )
						{
							msg = "Bạn chỉ được phép chốt nhập kho sau tháng khóa sổ ( " + thangKs + " ) 1 tháng";
							rs.close();
							return msg;
						}
						
					}
					rs.close();
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				return "Lỗi khi chốt nhập kho " + e.getMessage();
			}
			*/
			
			
			db.getConnection().setAutoCommit(false);
			
		String	query =
			"	select b.KhoNhap_FK, sanpham_fk, a.soluong, a.solo, a.gianhap, a.ngaysanxuat, a.ngayhethan,   "+
			"		isnull( ( select COUNT(*) from ERP_KHOTT_SANPHAM where KHOTT_FK = b.KhoNhap_FK and sanpham_fk = a.sanpham_fk ), 0) as exitKHOTT ,  "+  
			"		isnull( ( select COUNT(*) from ERP_KHOTT_SP_CHITIET where KHOTT_FK = b.KhoNhap_FK and sanpham_fk = a.sanpham_fk and solo = a.solo ), 0) as exitKHOTT_CT,  "+  
			"	isnull  "+
			"	(  "+
			"		case when sp.DVDL_FK=a.DVDL_FK then 1 else  "+ 
			"		( select SOLUONG1 / isnull(nullif( SOLUONG2, 0), 1) from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk  ) end, 0  "+
			"	) as quyCACH	   "+
			"	from ERP_NHAPKHO_SANPHAM a inner join ERP_NHAPKHO b on a.nhapkho_fk = b.PK_SEQ "+  
			"	inner join SANPHAM sp on sp.PK_SEQ=a.sanpham_fk "+
			"	where b.PK_SEQ = '"+lsxId+"' " ;
			System.out.println("---NHAP KHO: " + query );
			ResultSet rs; 
			rs = db.get(query);
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
					
					//CHECK KHO CHI TIET DA CO CHUA
					query = "select COUNT(*) as soDONG from ERP_KHOTT_SP_CHITIET where KHOTT_FK = '" + khoNHAP + "' and sanpham_fk = '" + spIds + "' and solo = '" + solo.trim() + "' ";
					ResultSet rsCHECK = db.get(query);
					if(rsCHECK.next())
					{
						exitKHOTT_CT = rsCHECK.getInt("soDONG");
					}
					rsCHECK.close();
					
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
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			db.update("rollback");
			return "Exception: " + e.getMessage();
		}
		finally
		{
			if(db!=null) db.shutDown();
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
			String query = "delete ERP_NHAPKHO_SANPHAM where nhapkho_fk = '" + lsxId + "' ";
			if(!db.update(query))
			{
				msg = "1.Khong the xoa: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "delete ERP_NHAPKHO where pk_seq = '" + lsxId + "'";
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
	    
		IErpNhapkhoList obj = new ErpNhapkhoList();
	 
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    obj.setUserId(userId);
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpNhapkho lsxBean = new ErpNhapkho();
	    	lsxBean.setUserId(userId);
	    	lsxBean.createRs();
    		
	    	session.setAttribute("lsxBean", lsxBean);
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Center/ErpNhapKhoNew.jsp";
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
		    	
		    	String nextJSP = "/TraphacoHYERP/pages/Center/ErpNhapKho.jsp";
				response.sendRedirect(nextJSP);
		    }
	    	else
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.init(search);
				
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		String nextJSP = "/TraphacoHYERP/pages/Center/ErpNhapKho.jsp";
	    		response.sendRedirect(nextJSP);
	    		
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpNhapkhoList obj)
	{
		
		Utility util =new Utility(); 
		
		String query =  "select a.PK_SEQ, a.trangthai, a.ngaynhap, a.lydo, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua   " +
						"from ERP_NHAPKHO a inner join ERP_KHOTT b on a.khonhap_fk = b.pk_seq  " +
						"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
						"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.pk_seq > 0   AND A.KHONHAP_FK IN  "+util.quyen_khoTT(obj.getUserId())+"  ";
		
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
			query += " and a.ngaynhap >= '" + tungaySX + "'";
		
		if(denngaySX.length() > 0)
			query += " and a.ngaynhap <= '" + denngaySX + "'";
	
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
