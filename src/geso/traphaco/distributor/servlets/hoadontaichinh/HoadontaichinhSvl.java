package geso.traphaco.distributor.servlets.hoadontaichinh;

import geso.traphaco.distributor.beans.hoadontaichinh.IHoadontaichinh;
import geso.traphaco.distributor.beans.hoadontaichinh.IHoadontaichinhList;
import geso.traphaco.distributor.beans.hoadontaichinh.imp.Hoadontaichinh;
import geso.traphaco.distributor.beans.hoadontaichinh.imp.HoadontaichinhList;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HoadontaichinhSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public HoadontaichinhSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IHoadontaichinhList obj;
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
	    obj = new HoadontaichinhList();
	    
	    String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "0";
	    obj.setLoaidonhang(loaidonhang);
	    System.out.println("---LOAI DON HANG: " + loaidonhang);
	    
	    String type = request.getParameter("type");
	    if(type == null)
	    	type = "";
	    obj.setType(type);
	    
	    System.out.println("---TYPE: HoadontaichinhSvl " +type);
	    
	    if(type.equals("deleteNEW"))
    	{
    		  GET_DATA_TO_CLIENT( request, response, action );
    	}
	    else
	    {
	    	if(action.equals("chot"))
	    	{
	    		IHoadontaichinh hd = new Hoadontaichinh();
	    		hd.setUserId(userId);
	    		String msg = "";
	    		if(!hd.chot(lsxId, false))
	    			msg = hd.getMsg();
	    		
				obj.setMsg(msg);
	    	}
	    	else if(action.equals("delete"))  //DELETE SE GIONG HUY, SE MO LAI TRANG THAI DON HANG
	    	{
					 String msg = this.Delete(lsxId, userId);
					 obj.setMsg(msg);
	    	}
	    	else if(action.equals("huy"))
	    	{
	    		 String msg = this.HuyHoaDon(lsxId, userId);
	    		 obj.setMsg(msg);
	    	}
	    	
		    
		    obj.setUserId(userId);
		    System.out.println("vao day ne------");
		    obj.init("");
		 
	
		    
			session.setAttribute("obj", obj);
			String nextJSP = "/TraphacoHYERP/pages/Distributor/HoaDonTaiChinh.jsp";
			response.sendRedirect(nextJSP);
	    }
	    
	}

	
	private void GET_DATA_TO_CLIENT(HttpServletRequest request, HttpServletResponse response, String action)  throws ServletException, IOException 
	{
			PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();
	    
	    String hoadonId =request.getParameter("hoadonId");
	    String userId =request.getParameter("userId");	    
	    Utility util = new Utility();
	    dbutils db = new dbutils();
	    String msg=util.Check_CK_DaHuong(hoadonId,userId,db);
	    
	    String myDATA="";
	    if(msg.length()>0)
	    {
	    	myDATA=" <p style='text-align:right;color:red;font-weight: bold' > " + msg+" </p> ";
	    }
	    
	    myDATA +=
	    "<table width='210' align='center' cellspacing='1' > " + 
		  "<tr class='tbheader' > " +
		  "	<td align='center' width='100px' style='font-size: 11px'>Xóa HĐ</td> " +
		  "	<td align='center' width='100px' style='font-size: 11px'>Hủy HĐ</td> " +
		  "</tr> </table> ";
	    
	    myDATA += "<div style='max-height:330px; overflow-x:hidden; overflow-y:auto; width: 100%;  ' > " +
				  "<table width='200px' align='center' cellspacing='1' > ";
	     myDATA +=
	      	"<tr style='background-color: #CCC;' > "+ 
	      " <td width='100px' > <A href ='../../HoadontaichinhSvl?userId="+userId+"&delete="+hoadonId+"' > <img src='../images/Delete_Icon.png' alt='Xóa hóa đơn' title='Xóa hóa đơn' width='20' height='20' border=0 onclick=\"if(!confirm('Bạn có xóa hóa đơn này?\')) return false;\"></A>" +
	      " </td>  " +
	      " &nbsp; | &nbsp;  "+
			  " <td width='100px' > <A href ='../../HoadontaichinhSvl?userId="+userId+"&huy="+hoadonId+"'><img src='../images/Delete20.png' alt='Hủy hóa đơn' title='Hủy hóa đơn' width='20' height='20' border=0 onclick=\"if(!confirm('Bạn có muốn hủy hóa đơn này?')) return false;\"></A>   " +
				" </td> "+  
	      " </tr>  ";
	     myDATA += " </table> </div> ";
	     
	    out.write(myDATA);
	}
	
	private String HuyHoaDon(String lsxId, String userId) 
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
			query = "update HOADON set trangthai = '5', nguoisua = '" + userId + "',NgayGioSua=getdate(),NgaySua='"+getDateTime()+"' where pk_seq = '" + lsxId + "' and TrangThai!=5 ";
			System.out.println("----HUY HOA DON: " + query);
			if(db.updateReturnInt(query)!=1)
			{
				msg = "Không thể cập nhật HOADON " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			//HUY HOA DON KHUYEN MAI HOAC BAN NEU CO
			query = 
				"update HOADON set trangthai = '5', nguoisua = '" + userId + "' " +
				"where  loaihoadon in (1,2) and pk_seq in (select hoadon_fk from HOADON_DDH where DDH_FK in (select DDH_FK from HOADON_DDH where HOADON_FK = '" + lsxId + "') and hoadon_fk != '" + lsxId + "' )  ";
			System.out.println("----2.HUY HOA DON: " + query);
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_HOADON " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			//TU DONG XOA PHIEU XUAT KHO
			query = "select DDH_FK as donhang_fk,  " +
						"ISNULL( ( select Import from DONHANG where pk_seq = a.DDH_FK ), 0) as Import, " +
						"	( select npp_fk from HOADON where pk_seq = '" + lsxId + "' ) as nppId,	" +
						"( select PXK_FK from PHIEUXUATKHO_DONHANG where donhang_fk = a.DDH_FK and PXK_FK in ( select pk_seq from PHIEUXUATKHO where trangthai != '2' and npp_fk = ( select npp_fk from HOADON where pk_seq = '" + lsxId + "' ) ) ) as soPXK  " +
					"from HOADON_DDH a where hoadon_fk = '" + lsxId + "'";
			System.out.println("---3.HUY HOA DON: " + query );
			
			String donhang_fk = "";
			String Import = "";
			String pxkId = "";
			String nppId = "";
			
			ResultSet rs = db.get(query);
			{
				while(rs.next())
				{
					donhang_fk = rs.getString("donhang_fk");
					Import = rs.getString("Import");
					nppId = rs.getString("nppId");
					
					if(Import.equals("0"))
					{
						pxkId = rs.getString("soPXK");
						
						//CHECK KHOA SO THANG
						
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
						
						query = "update kho set kho.SOLUONG = kho.SOLUONG + xuat.soluong, " +
								"			   kho.AVAILABLE = kho.AVAILABLE + xuat.soluong	 " +
								"from NHAPP_KHO_CHITIET kho inner join " +
								"( " +
								"	select kho_fk, kbh_fk, NPP_FK, SANPHAM_FK, SOLO, SUM(soluong) as soluong,NgayHetHan " +
								"   from (	" +
								"		select b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK, b.SOLO, SUM(b.soluong) as soluong,b.NgayHetHan " +
								"		from PHIEUXUATKHO a inner join PHIEUXUATKHO_SANPHAM_CHITIET b on a.PK_SEQ = b.PXK_FK " +
								"		where a.PK_SEQ = '" + pxkId + "' " +
								"		group by b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK, b.SOLO,b.NgayHetHan " +
								"	union ALL " +
								"		select b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK, b.SOLO, SUM(b.soluong) as soluong,b.NgayHetHan " +
								"		from PHIEUXUATKHO a inner join PHIEUXUATKHO_SPKM_CHITIET b on a.PK_SEQ = b.PXK_FK " +
								"		where a.PK_SEQ = '" + pxkId + "' " +
								"		group by b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK, b.SOLO,b.NgayHetHan " +
								"	)" +
								"	TX group by kho_fk, kbh_fk, NPP_FK, SANPHAM_FK, SOLO,NgayHetHan " +
								") " +
								"xuat on kho.SANPHAM_FK = xuat.SANPHAM_FK and kho.NPP_FK = xuat.NPP_FK and kho.KBH_FK = xuat.kbh_fk and kho.KHO_FK = xuat.kho_fk and kho.SOLO = xuat.SOLO and kho.NgayHetHan=xuat.NgayHetHan ";
						System.out.println("2.TANG KHO: " + query);
						if(!db.update(query))
						{
							rs.close();
							msg = "Không thể hủy " + query;
							db.getConnection().rollback();
							return msg;
						}
						

						
						query = "update phieuxuatkho set trangthai = '2' where pk_seq = '" + pxkId + "' and TrangThai=1 ";
						if( db.updateReturnInt(query) <= 0 )
						{
							rs.close();
							msg = "Không thể hủy " + query;
							db.getConnection().rollback();
							return msg;
						}
						
						//MO LAI TRANG THAI DON HANG
						query = "Update DONHANG set trangthai = '0', daxuathoadon = '0' where pk_seq = '" + donhang_fk + "' and TrangThai=1 ";
						if( db.updateReturnInt(query) <= 0 )
						{
							rs.close();
							msg = "Không thể hủy đơn hàng " + query;
							db.getConnection().rollback();
							return msg;
						}
						
						//XOA PHIEU CAN TRU NEU CO
						query = "UPDATE CANTRUCONGNO set trangthai = '2' where pk_seq in ( select CANTRUCONGNO_FK from CANTRUCONGNO_HOADON where hoadon_fk = '" + lsxId + "' ) ";
						if( !db.update(query) )
						{
							rs.close();
							msg = "Không thể hủy CANTRUCONGNO " + query;
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
			msg= util.Check_Kho_Tong_VS_KhoCT(nppId, db);
			if(msg.length()>0)
			{
				db.getConnection().rollback();
				return msg;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			msg=util.Check_CK_DaHuong(lsxId,userId,db);
			
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
		return msg;
	}

	private String Delete(String lsxId, String userId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		
		Utility util = new Utility();
		msg= util.Check_Huy_NghiepVu_KhoaSo("HoaDon", lsxId, "NgayXuatHD", db);
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
			query = "update HOADON set trangthai = '3', nguoisua = '" + userId + "',NgayGioSua=getdate(),NgaySua='"+getDateTime()+"' where pk_seq = '" + lsxId + "' and TrangThai!=3 ";
			System.out.println("---HUY HOA DON: 01 " + query );
			if(db.updateReturnInt(query)!=1)
			{
				msg = "Hóa đơn đã xóa rồi ";
				db.getConnection().rollback();
				return msg;
			}
			
			//HUY HOA DON KHUYEN MAI HOAC BAN NEU CO
			query = "update HOADON set trangthai = '3', nguoisua = '" + userId + "',NgayGioSua=getdate(),NgaySua='"+getDateTime()+"' " +
					"where  loaihoadon in ( 1,2) and pk_seq in (select hoadon_fk from HOADON_DDH where DDH_FK in (select DDH_FK from HOADON_DDH where HOADON_FK = '" + lsxId + "') and hoadon_fk != '" + lsxId + "' )  ";
			
			System.out.println("---HUY HOA DON: 02 " + query );
			if(!db.update(query))
			{
				msg = "Không thể cập nhật HOADON " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			//TU DONG XOA PHIEU XUAT KHO
			query = "select DDH_FK as donhang_fk,  " +
					"	( select npp_fk from HOADON where pk_seq = '" + lsxId + "' ) as nppId,	" +
					"	ISNULL( ( select Import from DONHANG where pk_seq = a.DDH_FK ), 0) as Import, " +
					"	( select PXK_FK from PHIEUXUATKHO_DONHANG where donhang_fk = a.DDH_FK and PXK_FK in ( select pk_seq from PHIEUXUATKHO where trangthai != '2' and npp_fk = ( select npp_fk from HOADON where pk_seq = '" + lsxId + "' ) ) ) as soPXK  " +
					"from HOADON_DDH a where hoadon_fk = '" + lsxId + "'";
			System.out.println("---HUY HOA DON: " + query );
			
			String donhang_fk = "";
			String Import = "";
			String pxkId = "";
			String nppId = "";
			
			ResultSet rs = db.get(query);
			{
				while(rs.next())
				{
					donhang_fk = rs.getString("donhang_fk");
					Import = rs.getString("Import");
					nppId = rs.getString("nppId");
					
					if(Import.equals("0"))
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
						
						query = "update kho set kho.SOLUONG = kho.SOLUONG + xuat.soluong, " +
								"			   kho.AVAILABLE = kho.AVAILABLE + xuat.soluong	 " +
								"from NHAPP_KHO_CHITIET kho inner join " +
								"( " +
								"	select kho_fk, kbh_fk, NPP_FK, SANPHAM_FK, SOLO, SUM(soluong) as soluong,NgayHetHan " +
								"   from (	" +
								"		select b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK, b.SOLO, SUM(b.soluong) as soluong,b.NgayHetHan " +
								"		from PHIEUXUATKHO a inner join PHIEUXUATKHO_SANPHAM_CHITIET b on a.PK_SEQ = b.PXK_FK " +
								"		where a.PK_SEQ = '" + pxkId + "' " +
								"		group by b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK, b.SOLO,b.NgayHetHan " +
								"	union ALL " +
								"		select b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK, b.SOLO, SUM(b.soluong) as soluong,b.NgayHetHan " +
								"		from PHIEUXUATKHO a inner join PHIEUXUATKHO_SPKM_CHITIET b on a.PK_SEQ = b.PXK_FK " +
								"		where a.PK_SEQ = '" + pxkId + "' " +
								"		group by b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK, b.SOLO,b.NgayHetHan " +
								"	)" +
								"	TX group by kho_fk, kbh_fk, NPP_FK, SANPHAM_FK, SOLO,NgayHetHan " +
								") " +
								"xuat on kho.SANPHAM_FK = xuat.SANPHAM_FK and kho.NPP_FK = xuat.NPP_FK and kho.KBH_FK = xuat.kbh_fk and kho.KHO_FK = xuat.kho_fk and kho.SOLO = xuat.SOLO  and kho.NgayHetHan=xuat.NgayHetHan ";
						System.out.println("2.TANG KHO: " + query);
						if(!db.update(query))
						{
							rs.close();
							msg = "Không thể hủy " + query;
							db.getConnection().rollback();
							return msg;
						}
						query = "update phieuxuatkho set trangthai = '2' where pk_seq = '" + pxkId + "' and TrangThai=1 ";
						if( db.updateReturnInt(query) <= 0 )
						{
							rs.close();
							msg = "Không thể hủy " + query;
							db.getConnection().rollback();
							return msg;
						}
						
						//MO LAI TRANG THAI DON HANG
						query = "Update DONHANG set trangthai = '0', daxuathoadon = '0' where pk_seq = '" + donhang_fk + "' and TrangThai=1 ";
						System.out.println("---CAP NHAT DON HANG: " + query);
						if( db.updateReturnInt(query) <= 0 )
						{
							rs.close();
							msg = "Không thể hủy đơn hàng " + query;
							db.getConnection().rollback();
							return msg;
						}
						
						//XOA PHIEU CAN TRU NEU CO
						query = "UPDATE CANTRUCONGNO set trangthai = '2' where pk_seq in ( select CANTRUCONGNO_FK from CANTRUCONGNO_HOADON where hoadon_fk = '" + lsxId + "' ) ";
						if( !db.update(query) )
						{
							rs.close();
							msg = "Không thể hủy CANTRUCONGNO " + query;
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
			
			//Đối vơi HD tháng 1,ckQúy và tháng lấy trong 1 HĐ
			/********************************************/
		query=			
		"	select ckThang.hoadon_fk as ckThangId,ckQuy.hoadon_fk as ckQuyId  "+ 
		"	from  "+
		"	(  "+
		"		select b.hoadon_fk,a.KHACHHANG_FK,a.NPP_FK  "+
		"		from HOADON a inner join HOADON_CHIETKHAU b on b.hoadon_fk=a.PK_SEQ  "+
		"		where b.tichluyQUY=0 and a.TRANGTHAI not in (1,3,5) and diengiai like 'CT%' and a.NGAYXUATHD like '2015-01%' and a.pk_seq='"+lsxId+"' "+
		"	)as ckThang left join  "+ 
		"	(  "+
		"		select b.hoadon_fk,a.KHACHHANG_FK,a.NPP_FK  "+
		"		from HOADON a inner join HOADON_CHIETKHAU b on b.hoadon_fk=a.PK_SEQ  "+
		"		where b.tichluyQUY=1 and a.TRANGTHAI not in (1,3,5) and b.diengiai like 'CQ%' and a.NGAYXUATHD like '2015-01%'  "+
		"	)as ckQuy on ckThang.KHACHHANG_FK=ckQuy.KHACHHANG_FK and ckThang.NPP_FK=ckQuy.NPP_FK and ckThang.hoadon_fk!=ckQuy.hoadon_fk  "+
		"	where ckQuy.hoadon_fk is not null  ";
		rs=db.get(query);
		while(rs.next())
		{
			msg +="Vui lòng xóa hóa đơn đã hưởng ck Qúy  mã số "+rs.getString("ckQuyId")+" trước khi xóa HĐ đã hưởng ck Tháng \n";
		}
		if(msg.length()>0)
		{
			db.getConnection().rollback();
			return msg;
		}
		/********************************************/
			
			msg= util.Check_Kho_Tong_VS_KhoCT(nppId, db);
			if(msg.length()>0)
			{
				db.getConnection().rollback();
				return msg;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			//Kiểm tra xem đã có hưởng ck tháng hay chưa.
			msg=util.Check_CK_DaHuong(lsxId,userId,db);
			
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
		
		return msg;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    Utility util = new Utility();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    
	    String action = request.getParameter("action");
	    if (action == null)
	    {
	    	action = "";
	    }
	    
	    String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "0";
	    
	    
		IHoadontaichinhList obj = new HoadontaichinhList();
		obj.setLoaidonhang(loaidonhang);
	 
		obj.setUserId(userId);
		
		String type = request.getParameter("type");
	    if(type == null)
	    	type = "";
	    obj.setType(type);
	    System.out.println("---TYPE GET: " + type);
		
	 
		HttpSession session = request.getSession();
	    
	    if(action.equals("Tao moi"))
	    {
	    	IHoadontaichinh lsxBean = new Hoadontaichinh();
	    	
	    	lsxBean.setUserId(userId);
	    	lsxBean.createRs();
	    	session.setAttribute("dvkdId", "");
			session.setAttribute("kbhId", "");
			session.setAttribute("nppId", "");
    		
	    	session.setAttribute("lsxBean", lsxBean);
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Distributor/HoaDonTaiChinhNew.jsp";
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
		    	
		    	String nextJSP = "/TraphacoHYERP/pages/Distributor/HoaDonTaiChinh.jsp";
				response.sendRedirect(nextJSP);
		    }
	    	else
	    	{
	    		obj.setUserId(userId);
		    	String search = getSearchQuery(request, obj);
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
	    		obj.setUserId(userId);
	    		
	    		
		    	obj.init(search);					
	    		String nextJSP = "/TraphacoHYERP/pages/Distributor/HoaDonTaiChinh.jsp";
	    		response.sendRedirect(nextJSP);
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IHoadontaichinhList obj)
	{
		String nppId = request.getParameter("nppId");
		geso.traphaco.center.util.Utility utilCenter = new  geso.traphaco.center.util.Utility();
		if(nppId == null)
			nppId = "";
		obj.setNppId(nppId);
		
		String query = "select distinct a.sohoadon, a.kyhieu ,isnull(Inhoadon,0) as inhoadon, a.PK_SEQ, a.trangthai, a.ngayxuatHD, NV.TEN as nguoitao, KH.TEN as nppTEN, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua ," +
					    "  a.tongtienavat as avat, ISNULL(b.hoadon_fk,0) ckt, " +
					    "	 case a.trangthai when 1 then 1 when 2 then 2 when 3 then 4 when 4 then 3 when 5 then 5 end as STT_SORT, " +
					    "	 case a.sohoadon when 'NA' then 9999999999 else cast(sohoadon as float) end as SORT_SOHOADON, isnull(DAINPGH, 0) as DAINPGH    " +
						" from HOADON a left join (select hoadon_fk from HOADON_CHIETKHAU where diengiai='CT5' and hienthi = '0' and round(chietkhau, 0) > 0) b on a.PK_SEQ = b.hoadon_fk  " +
						" inner join KHACHHANG KH on a.KHACHHANG_FK=KH.PK_SEQ  " +
						" inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
						" left join CANTRUCONGNO_HOADON ct on ct.hoadon_fk=a.pk_seq   "+
						" inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ "+
						" where a.pk_seq > 0 and a.NPP_FK ="+ nppId +" and isnull(loaihoadon,0) = 0 and a.kho_fk in  "+utilCenter.quyen_kho(obj.getUserId());

		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String madonhang = request.getParameter("madonhang");
		if(madonhang == null)
			madonhang = "";
		obj.setMadonhang(madonhang);
		
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
			query += " and kh.pk_seq like '%" + khten + "%' ";
		
		
		if(sohoadon.length() > 0)
			query += " and a.sohoadon like   '%" + sohoadon + "%' ";
		
		if(madonhang.length() > 0)
			query += " and a.pk_seq in ( select hoadon_fk from HOADON_DDH where ddh_fk = " + madonhang + ") ";
		
		
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
