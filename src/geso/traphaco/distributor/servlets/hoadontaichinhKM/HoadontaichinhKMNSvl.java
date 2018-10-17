package geso.traphaco.distributor.servlets.hoadontaichinhKM;

import geso.traphaco.distributor.beans.hoadontaichinhKM.IHoadontaichinhKM;
import geso.traphaco.distributor.beans.hoadontaichinhKM.IHoadontaichinhKMList;
import geso.traphaco.distributor.beans.hoadontaichinhKM.imp.HoadontaichinhKM;
import geso.traphaco.distributor.beans.hoadontaichinhKM.imp.HoadontaichinhKMList;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HoadontaichinhKMNSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public HoadontaichinhKMNSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IHoadontaichinhKMList obj;
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
	    obj = new HoadontaichinhKMList();
	    
	    String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "0";
	    obj.setLoaidonhang(loaidonhang);
	    
    	if(action.equals("chot"))
    	{
    		String msg = this.Chot(lsxId,userId);
			obj.setMsg(msg);
    	}
    	else if(action.equals("delete"))
    	{
    		String msg = this.Delete(lsxId,userId);
			obj.setMsg(msg);
    	}
    	else if(action.equals("huy"))
    	{
    		String msg = this.Huy(lsxId,userId);
    		obj.setMsg(msg);	
    	}
	    
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Distributor/HoaDonTaiChinhKMN.jsp";
		response.sendRedirect(nextJSP);
	    
	}

	private String Delete(String lsxId, String userId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		
		Utility util = new Utility();
		msg= util.Check_Huy_NghiepVu_KhoaSo("HOADON", lsxId, "NgayXuatHD", db);
		if(msg.length()>0)
		{
			db.shutDown();
			return msg;
		}
		
		try
		{
			db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
			db.update("SET LOCK_TIMEOUT 60000;");
			db.getConnection().setAutoCommit(false);
			
			String query = "";
			
			query = "update HOADON set trangthai = '3',NgaySua='"+getDateTime()+"',NgayGioSua=getdate() where pk_seq = '" + lsxId + "' and TrangThai!=3 ";
			if(db.updateReturnInt(query)!=1)
			{
				msg = "Hóa đơn đã xóa rồi ";
				db.getConnection().rollback();
				return msg;
			}
			
			//NEU LA DON HANG KHAC THI CHO HUY
			//TU DONG XOA PHIEU XUAT KHO
			query = "select DDH_FK as donhang_fk,  " +
						"	ISNULL( ( select Import from DONHANG where pk_seq = a.DDH_FK ), 0) as Import, " +
						"	ISNULL( ( select donquatang from DONHANG where pk_seq = a.DDH_FK ), 0) as donquatang, " +
						"	( select npp_fk from HOADON where pk_seq = '" + lsxId + "' ) as nppId,	" +
						"ISNULL( ( select DonHangKhac from DONHANG where pk_seq = a.DDH_FK ), 0) as DonHangKhac, " +
						"( select PXK_FK from PHIEUXUATKHO_DONHANG where donhang_fk = a.DDH_FK and PXK_FK in ( select pk_seq from PHIEUXUATKHO where trangthai != '2' and npp_fk = ( select npp_fk from HOADON where pk_seq = '" + lsxId + "' ) ) ) as soPXK  " +
					"from HOADON_DDH a where hoadon_fk = '" + lsxId + "'";
			System.out.println("---HUY HOA DON: " + query );
			
			String donhang_fk = "";
			String Import = "";
			String pxkId = "";
			String DonHangKhac = "";
			String nppId = "";
			String donquatang = "";
			
			ResultSet rs = db.get(query);
			{
				while(rs.next())
				{
					donhang_fk = rs.getString("donhang_fk");
					Import = rs.getString("Import");
					DonHangKhac = rs.getString("DonHangKhac");
					nppId = rs.getString("nppId");
					donquatang = rs.getString("donquatang");
					
					
					if(Import.equals("0"))
					{
						if( DonHangKhac.equals("1") || donquatang.equals("1") )
						{
							pxkId = rs.getString("soPXK");
							//TANG KHO NGUOC LAI
							query = "update kho set kho.SOLUONG = kho.SOLUONG + xuat.soluong, " +
									"			   kho.BOOKED = kho.BOOKED + xuat.soluong	 " +
									"from NHAPP_KHO kho inner join " +
									"( " +
									"   select kho_fk, kbh_fk, NPP_FK, SANPHAM_FK, SUM(soluong) as soluong " +
									" 	from (  " +
									"		select b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK, SUM(b.soluong) as soluong " +
									"		from PHIEUXUATKHO a inner join PHIEUXUATKHO_SANPHAM b on a.PK_SEQ = b.PXK_FK " +
									"		where a.PK_SEQ = '" + pxkId + "' " +
									"		group by b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK " +
									"	union ALL " +
									"		select b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK, SUM(b.soluong) as soluong " +
									"		from PHIEUXUATKHO a inner join PHIEUXUATKHO_SPKM b on a.PK_SEQ = b.PXK_FK " +
									"		where a.PK_SEQ = '" + pxkId + "' " +
									"		group by b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK " +
									"	)" +
									"	TX group by kho_fk, kbh_fk, NPP_FK, SANPHAM_FK " +
									") " +
									"xuat on kho.SANPHAM_FK = xuat.SANPHAM_FK and kho.NPP_FK = xuat.NPP_FK and kho.KBH_FK = xuat.kbh_fk and kho.KHO_FK = xuat.kho_fk ";
							System.out.println("1.TANG KHO: " + query);
							if(!db.update(query))
							{
								rs.close();
								msg = "Không thể xóa " + query;
								db.getConnection().rollback();
								return msg;
							}
							
							query = 
								"update kho set kho.SOLUONG = kho.SOLUONG + xuat.soluong, " +
								"			   kho.AVAILABLE = kho.AVAILABLE + xuat.soluong	 " +
								"from NHAPP_KHO_CHITIET kho inner join " +
								"( " +
								"	select kho_fk, kbh_fk, NPP_FK, SANPHAM_FK, SOLO, SUM(soluong) as soluong ,NgayHetHan" +
								"   from (	" +
								"		select b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK, b.SOLO, SUM(b.soluong) as soluong,b.NgayHetHan " +
								"		from PHIEUXUATKHO a inner join PHIEUXUATKHO_SANPHAM_CHITIET b on a.PK_SEQ = b.PXK_FK " +
								"		where a.PK_SEQ = '" + pxkId + "' " +
								"		group by b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK, b.SOLO,b.NgayHetHan " +
								"	union ALL " +
								"		select b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK, b.SOLO, SUM(b.soluong) as soluong , b.NgayHetHan" +
								"		from PHIEUXUATKHO a inner join PHIEUXUATKHO_SPKM_CHITIET b on a.PK_SEQ = b.PXK_FK " +
								"		where a.PK_SEQ = '" + pxkId + "' " +
								"		group by b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK, b.SOLO,b.NgayHetHan " +
								"	)" +
								"	TX group by kho_fk, kbh_fk, NPP_FK, SANPHAM_FK, SOLO,NgayHetHan " +
								") " +
								"xuat on kho.SANPHAM_FK = xuat.SANPHAM_FK and kho.NPP_FK = xuat.NPP_FK and kho.KBH_FK = xuat.kbh_fk and kho.KHO_FK = xuat.kho_fk and kho.SOLO = xuat.SOLO and kho.NgayHetHan=xuat.NgayHetHan";
							System.out.println("2.TANG KHO: " + query);
							if(!db.update(query))
							{
								rs.close();
								msg = "Không thể xóa " + query;
								db.getConnection().rollback();
								return msg;
							}
							
							query = "update phieuxuatkho set trangthai = '2' where pk_seq = '" + pxkId + "'";
							if( db.updateReturnInt(query) <= 0 )
							{
								rs.close();
								msg = "Không thể xóa " + query;
								db.getConnection().rollback();
								return msg;
							}
							
							//MO LAI TRANG THAI DON HANG
							query = "Update DONHANG set trangthai = '0', daxuathoadon = '0' where pk_seq = '" + donhang_fk + "' and TrangThai!=0 ";
							System.out.println("---CAP NHAT DON HANG: " + query);
							if( db.updateReturnInt(query) <= 0 )
							{
								rs.close();
								msg = "Không thể xóa đơn hàng " + query;
								db.getConnection().rollback();
								return msg;
							}
							
							msg= util.Check_Kho_Tong_VS_KhoCT(nppId, db);
							if(msg.length()>0)
							{
								db.getConnection().rollback();
								return msg;
							}							
						}
						else
						{
							rs.close();
							msg = "Không thể xóa hóa đơn khuyến mại tự động ";
							db.getConnection().rollback();
							return msg;
						}
					}
					else
					{
						rs.close();
						msg = "Không thể hủy xóa đơn tự IMPORT vào hệ thống ";
						db.getConnection().rollback();
						return msg;
					}
				}
				rs.close();
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
	
	private String Huy(String lsxId, String userId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		
		Utility util = new Utility();
		msg= util.Check_Huy_NghiepVu_KhoaSo("HOADON", lsxId, "NgayXuatHD", db);
		if(msg.length()>0)
		{
			db.shutDown();
			return msg;
		}
		
		try
		{
			db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
			db.update("SET LOCK_TIMEOUT 60000;");
			db.getConnection().setAutoCommit(false);
			
			String query = "";
			
			query = "update HOADON set NguoiSua='"+userId+"',trangthai = '5',NgaySua='"+getDateTime()+"',NgayGioSua=getdate() where pk_seq = '" + lsxId + "'and TrangThai !=5 ";
			if(db.updateReturnInt(query)!=1)
			{
				msg = "Không thể cập nhật HOADON " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			//NEU LA DON HANG KHAC THI CHO HUY
			//TU DONG XOA PHIEU XUAT KHO
			query = "select DDH_FK as donhang_fk,  " +
						"ISNULL( ( select Import from DONHANG where pk_seq = a.DDH_FK ), 0) as Import, " +
						"	( select npp_fk from HOADON where pk_seq = '" + lsxId + "' ) as nppId,	" +
						"ISNULL( ( select DonHangKhac from DONHANG where pk_seq = a.DDH_FK ), 0) as DonHangKhac, " +
						"ISNULL( ( select donquatang from DONHANG where pk_seq = a.DDH_FK ), 0) as donquatang, " +
						"( select PXK_FK from PHIEUXUATKHO_DONHANG where donhang_fk = a.DDH_FK and PXK_FK in ( select pk_seq from PHIEUXUATKHO where trangthai != '2' and npp_fk = ( select npp_fk from HOADON where pk_seq = '" + lsxId + "' ) ) ) as soPXK  " +
					"from HOADON_DDH a where hoadon_fk = '" + lsxId + "'";
			System.out.println("---HUY HOA DON: " + query );
			
			String donhang_fk = "";
			String Import = "";
			String pxkId = "";
			String DonHangKhac = "";
			String nppId = "";
			String donquatang="";
			ResultSet rs = db.get(query);
			{
				while(rs.next())
				{
					donhang_fk = rs.getString("donhang_fk");
					Import = rs.getString("Import");
					DonHangKhac = rs.getString("DonHangKhac");
					nppId = rs.getString("nppId");
					donquatang=rs.getString("donquatang");
					if(Import.equals("0"))
					{
						if(DonHangKhac.equals("1") || donquatang.equals("1") )
						{
							pxkId = rs.getString("soPXK");
							
							//TANG KHO NGUOC LAI
							query = "update kho set kho.SOLUONG = kho.SOLUONG + xuat.soluong, " +
									"			   kho.BOOKED = kho.BOOKED + xuat.soluong	 " +
									"from NHAPP_KHO kho inner join " +
									"( " +
									"   select kho_fk, kbh_fk, NPP_FK, SANPHAM_FK, SUM(soluong) as soluong " +
									" 	from (  " +
									"		select b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK, SUM(b.soluong) as soluong " +
									"		from PHIEUXUATKHO a inner join PHIEUXUATKHO_SANPHAM b on a.PK_SEQ = b.PXK_FK " +
									"		where a.PK_SEQ = '" + pxkId + "' " +
									"		group by b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK " +
									"	union ALL " +
									"		select b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK, SUM(b.soluong) as soluong " +
									"		from PHIEUXUATKHO a inner join PHIEUXUATKHO_SPKM b on a.PK_SEQ = b.PXK_FK " +
									"		where a.PK_SEQ = '" + pxkId + "' " +
									"		group by b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK " +
									"	)" +
									"	TX group by kho_fk, kbh_fk, NPP_FK, SANPHAM_FK " +
									") " +
									"xuat on kho.SANPHAM_FK = xuat.SANPHAM_FK and kho.NPP_FK = xuat.NPP_FK and kho.KBH_FK = xuat.kbh_fk and kho.KHO_FK = xuat.kho_fk ";
							System.out.println("1.TANG KHO: " + query);
							if(!db.update(query))
							{
								rs.close();
								msg = "Không thể hủy " + query;
								db.getConnection().rollback();
								return msg;
							}
							query=
							"update kho set kho.SOLUONG = kho.SOLUONG + xuat.soluong, " +
							"			   kho.AVAILABLE = kho.AVAILABLE + xuat.soluong	 " +
							"from NHAPP_KHO_CHITIET kho inner join " +
							"( " +
							"	select kho_fk, kbh_fk, NPP_FK, SANPHAM_FK, SOLO, SUM(soluong) as soluong ,NgayHetHan" +
							"   from (	" +
							"		select b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK, b.SOLO, SUM(b.soluong) as soluong,b.NgayHetHan " +
							"		from PHIEUXUATKHO a inner join PHIEUXUATKHO_SANPHAM_CHITIET b on a.PK_SEQ = b.PXK_FK " +
							"		where a.PK_SEQ = '" + pxkId + "' " +
							"		group by b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK, b.SOLO,b.NgayHetHan " +
							"	union ALL " +
							"		select b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK, b.SOLO, SUM(b.soluong) as soluong , b.NgayHetHan" +
							"		from PHIEUXUATKHO a inner join PHIEUXUATKHO_SPKM_CHITIET b on a.PK_SEQ = b.PXK_FK " +
							"		where a.PK_SEQ = '" + pxkId + "' " +
							"		group by b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK, b.SOLO,b.NgayHetHan " +
							"	)" +
							"	TX group by kho_fk, kbh_fk, NPP_FK, SANPHAM_FK, SOLO,NgayHetHan " +
							") " +
							"xuat on kho.SANPHAM_FK = xuat.SANPHAM_FK and kho.NPP_FK = xuat.NPP_FK and kho.KBH_FK = xuat.kbh_fk and kho.KHO_FK = xuat.kho_fk and kho.SOLO = xuat.SOLO and kho.NgayHetHan=xuat.NgayHetHan";
							System.out.println("2.TANG KHO: " + query);
							if(!db.update(query))
							{
								rs.close();
								msg = "Không thể hủy " + query;
								db.getConnection().rollback();
								return msg;
							}
							
							query = "update phieuxuatkho set trangthai = '2' where pk_seq = '" + pxkId + "'";
							if( db.updateReturnInt(query) <= 0 )
							{
								rs.close();
								msg = "Không thể hủy " + query;
								db.getConnection().rollback();
								return msg;
							}
							
							//MO LAI TRANG THAI DON HANG
							query = "Update DONHANG set trangthai = '0', daxuathoadon = '0' where pk_seq = '" + donhang_fk + "' and TrangThai!=0 ";
							System.out.println("---CAP NHAT DON HANG: " + query);
							if( db.updateReturnInt(query) <= 0 )
							{
								rs.close();
								msg = "Không thể hủy đơn hàng " + query;
								db.getConnection().rollback();
								return msg;
							}
							
							msg= util.Check_Kho_Tong_VS_KhoCT(nppId, db);
							if(msg.length()>0)
							{
								db.getConnection().rollback();
								return msg;
							}

						}
						else
						{
							rs.close();
							msg = "Không thể hủy hóa đơn khuyến mại tự động ";
							db.getConnection().rollback();
							return msg;
						}
					}
					else
					{
						rs.close();
						msg = "Không thể hủy hóa đơn tự IMPORT vào hệ thống ";
						db.getConnection().rollback();
						return msg;
					}
				}
				rs.close();
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			db.update("rollback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}finally
		{
			if(db!=null)db.shutDown();
		}
		
		return "";
	}
	
	private String Chot(String lsxId, String userId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		Utility util = new Utility();
		msg= util.Check_Huy_NghiepVu_KhoaSo("HOADON", lsxId, "NgayXuatHD", db);
		if(msg.length()>0)
		{
			db.shutDown();
			return msg;
		}
		try
		{
			db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
			db.update("SET LOCK_TIMEOUT 60000;");
			db.getConnection().setAutoCommit(false);
			
			String query = "";

			query = "update HOADON set trangthai = '2',NgaySua='"+getDateTime()+"'  where pk_seq = '" + lsxId + "' and TrangThai=1 ";
			if(db.updateReturnInt(query)!=1)
			{
				msg = "Hóa đơn đã chốt!";
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
			db.shutDown();
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
	    
	    
			IHoadontaichinhKMList obj = new HoadontaichinhKMList();
			obj.setLoaidonhang(loaidonhang);
	 
		
	    Utility util = new Utility();
	    
	    HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    
	    if(action.equals("Tao moi"))
	    {
	    	IHoadontaichinhKM lsxBean = new HoadontaichinhKM();
	    	
	    	lsxBean.setUserId(userId);
	    	lsxBean.createRs();
	    	session.setAttribute("dvkdId", "");
	    	session.setAttribute("kbhId", "");
	    	session.setAttribute("nppId", "");
    		
	    	session.setAttribute("lsxBean", lsxBean);
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Distributor/HoaDonTaiChinhKMNew.jsp";
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
		    	
		    	String nextJSP = "/TraphacoHYERP/pages/Distributor/HoaDonTaiChinhKMN.jsp";
				response.sendRedirect(nextJSP);
		    }
	    	else
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.setUserId(userId);
		    	obj.init(search);
			
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		String nextJSP = "/TraphacoHYERP/pages/Distributor/HoaDonTaiChinhKMN.jsp";
	    		response.sendRedirect(nextJSP);
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IHoadontaichinhKMList obj)
	{
		String nppId = request.getParameter("nppId");
    	if ( nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
		
    	String query = 
    	"select    a.sohoadon, a.kyhieu ,a.PK_SEQ, a.trangthai, a.ngayxuatHD, NV.TEN as nguoitao, KH.TEN as nppTEN, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua   " +
		"from HOADON a  " +
		"inner join KHACHHANG KH on a.KHACHHANG_FK=KH.PK_SEQ  " +
		"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
		"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ " +
		"where a.pk_seq > 0 and a.NPP_FK ="+ nppId +"  and a.LOAIHOADON in ( '1') ";

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
		
		String khten = request.getParameter("khTen");		
		if(khten == null)
			khten = "";
		obj.setKhTen(khten);
		
		
		String sohoadon = request.getParameter("sohoadon");		
		if(sohoadon == null)
			sohoadon = "";
		obj.setSoHoaDon(sohoadon.trim());
		
		String madonhang = request.getParameter("madonhang");		
		if(madonhang == null)
			madonhang = "";
		obj.setMadonhang(madonhang);
		
		String hoadonId = request.getParameter("hoadonId");		
		if(hoadonId == null)
			hoadonId = "";
		obj.setHoadonId(hoadonId.trim());
		
		
		if(tungay.length() > 0)
			query += " and a.ngayxuatHD >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngayxuatHD <= '" + denngay + "'";
	
		if(trangthai.length() > 0)
			query += " and a.TrangThai = '" + trangthai + "'";
		
		if(khten.length() > 0)
			query += " and kh.pk_seq = '" + khten + "' ";
		
		
		if(sohoadon.length() > 0)
			query += " and a.sohoadon like   '%" + sohoadon + "%' ";
		

		if(madonhang.length() > 0)
			query += " and a.pk_seq in ( select hoadon_fk from HOADON_DDH where DDH_FK = " + madonhang + " ) ";
		
		

		if(hoadonId.length() > 0)
			query += " and  cast(a.pk_seq as varchar(20)) like '%"+hoadonId+"%' ";
		
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
