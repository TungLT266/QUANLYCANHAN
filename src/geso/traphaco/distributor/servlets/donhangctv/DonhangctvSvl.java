package geso.traphaco.distributor.servlets.donhangctv;

import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.donhangctv.IDonhangctv;
import geso.traphaco.distributor.beans.donhangctv.IDonhangctvList;
import geso.traphaco.distributor.beans.donhangctv.imp.Donhangctv;
import geso.traphaco.distributor.beans.donhangctv.imp.DonhangctvList;
import geso.traphaco.distributor.db.sql.dbutils;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.HorizontalAlignmentType;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class DonhangctvSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	public DonhangctvSvl()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IDonhangctvList obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
		String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
		
		Utility util = new Utility();
		
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		
		String action = util.getAction(querystring);
		
		obj = new DonhangctvList();
		obj.setUserId(userId);
		obj.setTdv_dangnhap_id(tdv_dangnhap_id);
		obj.setNpp_duocchon_id(npp_duocchon_id);
		
		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
		
		String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "";
	    obj.setLoaidonhang(loaidonhang);
	    
		String duyetss = request.getParameter("duyet");
	    if(duyetss == null)
	    	duyetss = "";
	    obj.setDuyetSS(duyetss);
	    
		if (action.equals("delete"))
		{
			String lsxId = util.getId(querystring);
			String msg = this.DeleteChuyenKho(lsxId, userId);
			obj.setMsg(msg);	
		} 
		else if (action.equals("chot"))
		{
			String lsxId = util.getId(querystring);
			String msg = "";
			if(duyetss.length() > 0)
				msg = this.Chot(lsxId, userId, 1);
			else
				msg = this.Chot(lsxId, userId, 0);
			obj.setMsg(msg);
		} 
		else if (action.equals("UnChot"))
		{
			String lsxId = util.getId(querystring);
			String msg = this.MoChot(lsxId, userId, 1);
			obj.setMsg(msg);
		} 
	
		String view = request.getParameter("view");
	    if(view == null)
	    	view = "";
	
		obj.init("");
		session.setAttribute("obj", obj);
		String nextJSP="";
		if(view.equals("CTV"))
			 nextJSP = "/TraphacoHYERP/pages/Distributor/XemCTV.jsp";
		else
		 nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangCTV.jsp";
		response.sendRedirect(nextJSP);
	}
	
	private String Chot(String id, String userId, int loai)
	{
		dbutils db = new dbutils();
		String msg = "";

		try
		{
			db.getConnection().setAutoCommit(false);

			// Trạng thái bằng 1, đã chốt, SS chưa duyệt, Trạng thái bằng 2, SS đã duyệt
			String query = "";
			if(loai == 0)
				 query = "update donhangctv set trangthai = '1', nguoisua = '" + userId + "', Modified_Date=getdate() where pk_seq = '" + id + "' and trangthai = 0 ";
			else
				query = "update donhangctv set trangthai = '2', nguoisua = '" + userId + "', Modified_Date=getdate() where pk_seq = '" + id + "' and trangthai = 1 ";
			System.out.println("----CAP NHAT: " + query);
			if(db.updateReturnInt(query)!=1)
			{
				msg = "2.Khong thể cập nhật trạng thái: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			//PHAN BO SỐ LƯỢNG VÀO ĐƠN HÀNG CŨ HƠN
			if( loai == 0 )
			{
				query = " select a.ngaynhap, SUBSTRING(a.ngaynhap, 0, 8 ) + '-01' as dauthang, a.KHACHHANG_FK, a.ddkd_fk, b.ctv_fk, b.SANPHAM_FK, b.soluong, b.DONGIA, b.DONVI "+
						" from DONHANGCTV a inner join DONHANGCTV_SP b on a.PK_SEQ = b.DONHANGCTV_FK"+
						" where b.DONHANGCTV_FK = '" + id + "' " ;
						//" group by a.ngaynhap, a.KHACHHANG_FK, a.ddkd_fk, b.SANPHAM_FK, b.DONGIA, b.DONVI ";
				ResultSet rs = db.get(query);
				if( rs != null )
				{
					while( rs.next() )
					{
						String ngaynhap = rs.getString("dauthang");
						String khId = rs.getString("KHACHHANG_FK");
						String ddkdId = rs.getString("ddkd_fk");
						String ctvId = rs.getString("ctv_fk");
						String spId = rs.getString("SANPHAM_FK");
						String dongia = rs.getString("DONGIA");
						String donvi = rs.getString("DONVI");
						double soluongCAN = rs.getDouble("SOLUONG");
						
						//LAY CAC DON HANG CON CO THE PHAN BO
						/*query = " select DT.donhangId, DT.loaidonhang, DT.NgayDonHang, DT.soluong - ISNULL( DADUNG.SOLUONG, 0 ) as conlai "+
								 " from"+
								 " ("+
								 " 		select e.PK_SEQ as donhangId, 0 as loaidonhang, e.NgayDonHang, SUM( isnull( c.soluongCHUAN, c.soluong ) ) as soluong, d.DONVI, round( a.dongiaGOC * ( 1 + thueVAT / 100 ), 0 ) as dongia "+
								 " 		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ "+
								 " 			inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET c on a.dondathang_fk = c.dondathang_fk and a.sanpham_fk = c.SANPHAM_FK and a.dvdl_fk = c.DVDL_FK"+
								 " 			inner join DONVIDOLUONG d on c.DVDL_FK = d.PK_SEQ"+
								 " 			inner join ERP_DONDATHANGNPP e on a.dondathang_fk = e.PK_SEQ"+
								 " 		where a.ddkd_fk = '" + ddkdId + "' and e.TRANGTHAI = 4 and e.KHACHHANG_FK in ( select PK_SEQ from KHACHHANG where PK_SEQ = '" + khId + "' ) "+
								 " 			and a.sanpham_fk = '" + spId + "'	and round( a.dongiaGOC * ( 1 + thueVAT / 100 ), 0 ) = '" + dongia + "' " + 
								 " 			and SUBSTRING( e.NgayDonHang , 0, 8 ) = SUBSTRING( convert( varchar(10), DATEADD(mm, -1, '" + ngaynhap + "'), 120 ), 0, 8 ) "+
								 " 		group by d.DONVI, a.dongiaGOC, thueVAT, e.PK_SEQ, e.NgayDonHang"+
								 "	union " +
								 " 		select e.PK_SEQ as donhangId, 0 as loaidonhang, e.NgayDonHang, SUM( isnull( c.soluongCHUAN, c.soluong ) ) as soluong, d.DONVI, round( a.dongiaGOC * ( 1 + thueVAT / 100 ), 0 ) as dongia "+
								 " 		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ "+
								 " 			inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET c on a.dondathang_fk = c.dondathang_fk and a.sanpham_fk = c.SANPHAM_FK and a.dvdl_fk = c.DVDL_FK"+
								 " 			inner join DONVIDOLUONG d on c.DVDL_FK = d.PK_SEQ"+
								 " 			inner join ERP_DONDATHANGNPP e on a.dondathang_fk = e.PK_SEQ"+
								 " 		where e.TRANGTHAI = 4 and e.KHACHHANG_FK in ( select PK_SEQ from KHACHHANG where benhvien_fk = '" + khId + "'  ) "+
								 " 			and a.sanpham_fk = '" + spId + "'	and round( a.dongiaGOC * ( 1 + thueVAT / 100 ), 0 ) = '" + dongia + "' " + 
								 " 			and SUBSTRING( e.NgayDonHang , 0, 8 ) = SUBSTRING( convert( varchar(10), DATEADD(mm, -1, '" + ngaynhap + "'), 120 ), 0, 8 ) "+
								 "		    and a.sanpham_fk in ( select sanpham_fk from KHACHHANG_SANPHAM_XQBENHVIEN where khachhang_fk in ( select PK_SEQ from KHACHHANG where benhvien_fk = '" + khId + "' ) and tdv_fk = '" + ddkdId + "' )	" +
								 " 		group by d.DONVI, a.dongiaGOC, thueVAT, e.PK_SEQ, e.NgayDonHang"+
								 " 	union "+
								 " 		select e.PK_SEQ as donhangId, 1 as loaidonhang, e.NGAYNHAP, SUM( a.SOLUONG ) as soluong, d.DONVI, round( a.dongiaGOC * ( 1 + thueVAT / 100 ), 0 ) as dongia "+
								 " 		from DONHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ "+
								 " 				inner join DONVIDOLUONG d on b.DVDL_FK = d.PK_SEQ"+
								 " 				inner join DONHANG e on a.DONHANG_FK = e.PK_SEQ"+
								 " 		where e.ddkd_fk = '" + ddkdId + "' and e.CS_DUYET = 1 and e.KHACHHANG_FK in ( select PK_SEQ from KHACHHANG where benhvien_fk = '" + khId + "' or PK_SEQ = '" + khId + "' ) "+
								 " 				and a.sanpham_fk = '" + spId + "'	and round( a.dongiaGOC * ( 1 + thueVAT / 100 ), 0 ) = '" + dongia + "' "+
								 " 				and SUBSTRING( e.NGAYNHAP , 0, 8 ) = SUBSTRING( convert( varchar(10), DATEADD(mm, -1, '" + ngaynhap + "'), 120 ), 0, 8 ) "+
								 " 		group by d.DONVI, a.dongiaGOC, thueVAT, e.PK_SEQ, e.NGAYNHAP"+
								 " )"+
								 " DT left join"+
								 " ("+
								 " 	select b.DONHANG_FK, b.SOLUONG, b.DONVI, b.DONGIA, b.loaidonhang "+
								 " 	from DONHANGCTV a inner join DONHANGCTV_SP_CHITIET b on a.PK_SEQ = b.DONHANGCTV_FK"+
								 " 	where a.KHACHHANG_FK = '" + khId + "' and a.ddkd_fk = '" + ddkdId + "' and b.sanpham_fk = '" + spId + "' and b.dongia = '" + dongia + "' "+
								 " )"+
								 " DADUNG on DT.donhangId = DADUNG.DONHANG_FK and DT.loaidonhang = DADUNG.loaidonhang "+
								 " where  DT.soluong - ISNULL( DADUNG.SOLUONG, 0 ) > 0 " +
								 " order by DT.NgayDonHang asc, DT.loaidonhang asc ";*/
						
						query = " select DT.donhangId, DT.loaidonhang, DT.NgayDonHang, DT.soluong - ISNULL( DADUNG.SOLUONG, 0 ) as conlai "+
								 " from"+
								 " ("+
								 " 		select e.PK_SEQ as donhangId, 0 as loaidonhang, e.NgayDonHang, SUM( isnull( c.soluongCHUAN, c.soluong ) ) as soluong, d.DONVI, round( a.dongiaCHUAN * ( 1 + thueVAT / 100 ), 0 ) as dongia "+
								 " 		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ "+
								 " 			inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET c on a.dondathang_fk = c.dondathang_fk and a.sanpham_fk = c.SANPHAM_FK and a.dvdl_fk = c.DVDL_FK"+
								 " 			inner join DONVIDOLUONG d on c.DVDL_FK = d.PK_SEQ"+
								 " 			inner join ERP_DONDATHANGNPP e on a.dondathang_fk = e.PK_SEQ"+
								 " 		where e.chuyenSALES = '0' and a.ddkd_fk = '" + ddkdId + "' and e.TRANGTHAI = 4 and e.KHACHHANG_FK in ( select PK_SEQ from KHACHHANG where PK_SEQ = '" + khId + "' ) "+
								 " 			and a.sanpham_fk = '" + spId + "'	and round( a.dongiaGOC * ( 1 + thueVAT / 100 ), 0 ) = '" + dongia + "' " + 
								 " 			and e.NgayDonHang < '" + ngaynhap + "' "+
								 " 		group by d.DONVI, a.dongiaGOC, thueVAT, e.PK_SEQ, e.NgayDonHang"+
								 "	union " +
								 " 		select e.PK_SEQ as donhangId, 0 as loaidonhang, e.NgayDonHang, SUM( isnull( c.soluongCHUAN, c.soluong ) ) as soluong, d.DONVI, round( a.dongiaCHUAN * ( 1 + thueVAT / 100 ), 0 ) as dongia "+
								 " 		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ "+
								 " 			inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET c on a.dondathang_fk = c.dondathang_fk and a.sanpham_fk = c.SANPHAM_FK and a.dvdl_fk = c.DVDL_FK"+
								 " 			inner join DONVIDOLUONG d on c.DVDL_FK = d.PK_SEQ"+
								 " 			inner join ERP_DONDATHANGNPP e on a.dondathang_fk = e.PK_SEQ"+
								 " 		where e.chuyenSALES = '0' and e.TRANGTHAI = 4 and e.KHACHHANG_FK in ( select PK_SEQ from KHACHHANG where benhvien_fk = '" + khId + "'  ) "+
								 " 			and a.sanpham_fk = '" + spId + "'	and round( a.dongiaGOC * ( 1 + thueVAT / 100 ), 0 ) = '" + dongia + "' " + 
								 " 			and e.NgayDonHang < '" + ngaynhap + "' "+
								 "		    and a.sanpham_fk in ( select sanpham_fk from KHACHHANG_SANPHAM_XQBENHVIEN where khachhang_fk in ( select PK_SEQ from KHACHHANG where benhvien_fk = '" + khId + "' ) and tdv_fk = '" + ddkdId + "' )	" +
								 " 		group by d.DONVI, a.dongiaGOC, thueVAT, e.PK_SEQ, e.NgayDonHang"+
								 " 	union "+
								 " 		select e.PK_SEQ as donhangId, 1 as loaidonhang, e.NGAYNHAP, SUM( a.SOLUONG ) as soluong, d.DONVI, round( a.dongiaGOC * ( 1 + thueVAT / 100 ), 0 ) as dongia "+
								 " 		from DONHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ "+
								 " 				inner join DONVIDOLUONG d on b.DVDL_FK = d.PK_SEQ"+
								 " 				inner join DONHANG e on a.DONHANG_FK = e.PK_SEQ"+
								 " 		where e.ddkd_fk = '" + ddkdId + "' and e.CS_DUYET = 1 and e.KHACHHANG_FK in ( select PK_SEQ from KHACHHANG where benhvien_fk = '" + khId + "' or PK_SEQ = '" + khId + "' ) "+
								 " 				and a.sanpham_fk = '" + spId + "'	and round( a.dongiaGOC * ( 1 + thueVAT / 100 ), 0 ) = '" + dongia + "' "+
								 " 				and e.NgayNhap < '" + ngaynhap + "' "+
								 " 		group by d.DONVI, a.dongiaGOC, thueVAT, e.PK_SEQ, e.NGAYNHAP"+
								 "	union	" +
								 " 		select e.PK_SEQ as donhangId, 0 as loaidonhang, e.NgayDonHang, SUM( isnull( c.soluongCHUAN, c.soluong ) ) as soluong, d.DONVI, round( a.dongiaCHUAN * ( 1 + thueVAT / 100 ), 0 ) as dongia "+
								 " 		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ "+
								 " 			inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET c on a.dondathang_fk = c.dondathang_fk and a.sanpham_fk = c.SANPHAM_FK and a.dvdl_fk = c.DVDL_FK"+
								 " 			inner join DONVIDOLUONG d on c.DVDL_FK = d.PK_SEQ"+
								 " 			inner join ERP_DONDATHANGNPP e on a.dondathang_fk = e.PK_SEQ"+
								 " 		where e.chuyenSALES = '1' and a.ddkd_fk = '" + ddkdId + "' and e.TRANGTHAI = 4 and e.KHACHHANG_FK in ( select PK_SEQ from KHACHHANG where PK_SEQ = '" + khId + "' ) "+
								 " 			and a.sanpham_fk = '" + spId + "'	and round( a.dongiaGOC * ( 1 + thueVAT / 100 ), 0 ) = '" + dongia + "' " + 
								 " 			and e.NgayDonHang < '" + ngaynhap + "' "+
								 " 		group by d.DONVI, a.dongiaGOC, thueVAT, e.PK_SEQ, e.NgayDonHang"+
								 " )"+
								 " DT left join"+
								 " ("+
								 " 	select b.DONHANG_FK, sum(b.SOLUONG) as soluong, b.DONVI, b.DONGIA, b.loaidonhang "+
								 " 	from DONHANGCTV a inner join DONHANGCTV_SP_CHITIET b on a.PK_SEQ = b.DONHANGCTV_FK"+
								 " 	where a.KHACHHANG_FK = '" + khId + "' and a.ddkd_fk = '" + ddkdId + "' and b.sanpham_fk = '" + spId + "' and b.dongia = '" + dongia + "' "+
								 "	group by b.DONHANG_FK, b.DONVI, b.DONGIA, b.loaidonhang	" +
								 " )"+
								 " DADUNG on DT.donhangId = DADUNG.DONHANG_FK and DT.loaidonhang = DADUNG.loaidonhang "+
								 " where  DT.soluong - ISNULL( DADUNG.SOLUONG, 0 ) > 0 " +
								 " order by DT.NgayDonHang asc, DT.loaidonhang asc ";
						
						System.out.println("::: DE XUAT DON HANG: " + query );
						ResultSet rsDonhang = db.get(query);
						
						double totalSL = 0;
						boolean exit = false;
						if( rsDonhang != null )
						{
							while( rsDonhang.next() )
							{
								String donhangId = rsDonhang.getString("donhangId");
								String loaidonhang = rsDonhang.getString("loaidonhang");
								double conlai = rsDonhang.getDouble("conlai");
								double soluongDENGHI = 0;
								
								totalSL += conlai;
								
								if( totalSL < soluongCAN )
								{
									soluongDENGHI = conlai;
								}
								else
								{
									soluongDENGHI = soluongCAN - ( totalSL - conlai );
									exit = true;
								}
								
								System.out.println("::: SO LUONG CAN: " + soluongCAN + " HIEN HUU: " + conlai + " DE XUAT: " + soluongDENGHI + " TOTAL: " + totalSL );
								if( soluongDENGHI > 0 )
								{
									String sql = "insert DONHANGCTV_SP_CHITIET( donhangctv_fk, donhang_fk, sanpham_fk, soluong, donvi, dongia, CTV_FK, loaidonhang ) " + 
												 "values( '" + id + "', '" + donhangId + "', '" + spId + "', '" + soluongDENGHI + "', N'" + donvi + "', '" + dongia + "', '" + ctvId + "', '" + loaidonhang + "' ) ";
									
									System.out.println("::: CHEN SAN PHAM: " + sql );
									if(db.updateReturnInt(sql) != 1)
									{
										msg = "2.Lỗi khi duyệt đơn hàng CTV: " + query;
										rsDonhang.close();
										rs.close();
										db.getConnection().rollback();
										return msg;
									}
								}
								
								if( exit )
								{
									rsDonhang.close();
									break;
								}
							}
							rsDonhang.close();
						}
					}
					rs.close();
				}
			}
			
			if(loai == 0)
			{
				//query = "update donhangctv set GSBH_FK = ( select top(1) gsbh_fk from ERP_DONDATHANGNPP where pk_seq in ( select donhang_fk from DONHANGCTV_SP_CHITIET where donhangctv_fk = '" + id + "' ) ) where pk_seq = '" + id + "'  ";
				
				query = " update dh "+
						 " 	set dh.gsbh_fk = ( select top(1) gsbh_fk from DDKD_GSBH where DDKD_FK = dh.ddkd_fk ),"+
						 " 		dh.asm_fk = ( select ASM_FK from GIAMSATBANHANG where PK_SEQ in ( select top(1) gsbh_fk from DDKD_GSBH where DDKD_FK = dh.ddkd_fk ) )"+
						 " from DONHANGCTV dh where PK_SEQ = '" + id + "'";
				
				System.out.println("----CAP NHAT: " + query);
				if(db.updateReturnInt(query)!=1)
				{
					msg = "2.Khong thể cập nhật trạng thái: " + query;
					db.getConnection().rollback();
					return msg;
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			return "Exception: " + e.getMessage();
		}
		finally
		{
			if(db!=null)db.shutDown();
		}

		return "";
	}

	private String MoChot(String id, String userId, int loai)
	{
		dbutils db = new dbutils();
		String msg = "";

		try
		{
			db.getConnection().setAutoCommit(false);

			String	query = "update donhangctv set trangthai = '0', nguoimoduyet = '" + userId + "', ngaymoduyet = getdate() where pk_seq = '" + id + "'  ";
			System.out.println("----CAP NHAT: " + query);
			if( !db.update(query) )
			{
				msg = "1.Khong thể cập nhật trạng thái: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "delete DONHANGCTV_SP_CHITIET where donhangctv_fk = '" + id + "' ";
			System.out.println("----CAP NHAT: " + query);
			if( !db.update(query) )
			{
				msg = "2.Khong thể cập nhật trạng thái: " + query;
				db.getConnection().rollback();
				return msg;
			}
							
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
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

	
	//CHAY LAI CHOT CHI TIET
	public static void main(String[] arg)
	{
		DonhangctvSvl dh = new DonhangctvSvl();
		
		dbutils db = new dbutils();
		ResultSet rs = db.get("select PK_SEQ from DONHANGCTV where TRANGTHAI in ( 1, 2 ) order by PK_SEQ asc");
		if( rs != null )
		{
			try 
			{
				while ( rs.next() )
				{
					String dhId = rs.getString("PK_SEQ");
					dh.ChotLai(dhId);
				}
				rs.close();
			} 
			catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		db.shutDown();
		
	}
	
	
	private String ChotLai(String id)
	{
		dbutils db = new dbutils();
		String msg = "";

		try
		{
			db.getConnection().setAutoCommit(false);

			// Trạng thái bằng 1, đã chốt, SS chưa duyệt, Trạng thái bằng 2, SS đã duyệt
			String query = "";
			int loai = 0;
			
			//PHAN BO SỐ LƯỢNG VÀO ĐƠN HÀNG CŨ HƠN
			if( loai == 0 )
			{
				query = " select a.ngaynhap, SUBSTRING(a.ngaynhap, 0, 8 ) + '-01' as dauthang, a.KHACHHANG_FK, a.ddkd_fk, b.ctv_fk, b.SANPHAM_FK, b.soluong, b.DONGIA, b.DONVI "+
						" from DONHANGCTV a inner join DONHANGCTV_SP b on a.PK_SEQ = b.DONHANGCTV_FK"+
						" where b.DONHANGCTV_FK = '" + id + "' " ;
						//" group by a.ngaynhap, a.KHACHHANG_FK, a.ddkd_fk, b.SANPHAM_FK, b.DONGIA, b.DONVI ";
				System.out.println("::: THONG TIN DON HANG: " + query);
				ResultSet rs = db.get(query);
				if( rs != null )
				{
					while( rs.next() )
					{
						String ngaynhap = rs.getString("dauthang");
						String khId = rs.getString("KHACHHANG_FK");
						String ddkdId = rs.getString("ddkd_fk");
						String ctvId = rs.getString("ctv_fk");
						String spId = rs.getString("SANPHAM_FK");
						String dongia = rs.getString("DONGIA");
						String donvi = rs.getString("DONVI");
						double soluongCAN = rs.getDouble("SOLUONG");
						
						//LAY CAC DON HANG CON CO THE PHAN BO
						query = " select DT.donhangId, DT.loaidonhang, DT.NgayDonHang, DT.soluong - ISNULL( DADUNG.SOLUONG, 0 ) as conlai "+
								 " from"+
								 " ("+
								 " 		select e.PK_SEQ as donhangId, 0 as loaidonhang, e.NgayDonHang, SUM( isnull( c.soluongCHUAN, c.soluong ) ) as soluong, d.DONVI, round( a.dongiaCHUAN * ( 1 + thueVAT / 100 ), 0 ) as dongia "+
								 " 		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ "+
								 " 			inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET c on a.dondathang_fk = c.dondathang_fk and a.sanpham_fk = c.SANPHAM_FK and a.dvdl_fk = c.DVDL_FK"+
								 " 			inner join DONVIDOLUONG d on c.DVDL_FK = d.PK_SEQ"+
								 " 			inner join ERP_DONDATHANGNPP e on a.dondathang_fk = e.PK_SEQ"+
								 " 		where e.chuyenSALES = '0' and a.ddkd_fk = '" + ddkdId + "' and e.TRANGTHAI = 4 and e.KHACHHANG_FK in ( select PK_SEQ from KHACHHANG where PK_SEQ = '" + khId + "' ) "+
								 " 			and a.sanpham_fk = '" + spId + "'	and round( a.dongiaGOC * ( 1 + thueVAT / 100 ), 0 ) = '" + dongia + "' " + 
								 " 			and e.NgayDonHang < '" + ngaynhap + "' "+
								 " 		group by d.DONVI, a.dongiaCHUAN, thueVAT, e.PK_SEQ, e.NgayDonHang"+
								 "	union " +
								 " 		select e.PK_SEQ as donhangId, 0 as loaidonhang, e.NgayDonHang, SUM( isnull( c.soluongCHUAN, c.soluong ) ) as soluong, d.DONVI, round( a.dongiaCHUAN * ( 1 + thueVAT / 100 ), 0 ) as dongia "+
								 " 		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ "+
								 " 			inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET c on a.dondathang_fk = c.dondathang_fk and a.sanpham_fk = c.SANPHAM_FK and a.dvdl_fk = c.DVDL_FK"+
								 " 			inner join DONVIDOLUONG d on c.DVDL_FK = d.PK_SEQ"+
								 " 			inner join ERP_DONDATHANGNPP e on a.dondathang_fk = e.PK_SEQ"+
								 " 		where e.chuyenSALES = '0' and e.TRANGTHAI = 4 and e.KHACHHANG_FK in ( select PK_SEQ from KHACHHANG where benhvien_fk = '" + khId + "'  ) "+
								 " 			and a.sanpham_fk = '" + spId + "'	and round( a.dongiaGOC * ( 1 + thueVAT / 100 ), 0 ) = '" + dongia + "' " + 
								 " 			and e.NgayDonHang < '" + ngaynhap + "' "+
								 "		    and a.sanpham_fk in ( select sanpham_fk from KHACHHANG_SANPHAM_XQBENHVIEN where khachhang_fk in ( select PK_SEQ from KHACHHANG where benhvien_fk = '" + khId + "' ) and tdv_fk = '" + ddkdId + "' )	" +
								 " 		group by d.DONVI, a.dongiaCHUAN, thueVAT, e.PK_SEQ, e.NgayDonHang"+
								 " 	union "+
								 " 		select e.PK_SEQ as donhangId, 1 as loaidonhang, e.NGAYNHAP, SUM( a.SOLUONG ) as soluong, d.DONVI, round( a.dongiaGOC * ( 1 + thueVAT / 100 ), 0 ) as dongia "+
								 " 		from DONHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ "+
								 " 				inner join DONVIDOLUONG d on b.DVDL_FK = d.PK_SEQ"+
								 " 				inner join DONHANG e on a.DONHANG_FK = e.PK_SEQ"+
								 " 		where e.ddkd_fk = '" + ddkdId + "' and e.CS_DUYET = 1 and e.KHACHHANG_FK in ( select PK_SEQ from KHACHHANG where benhvien_fk = '" + khId + "' or PK_SEQ = '" + khId + "' ) "+
								 " 				and a.sanpham_fk = '" + spId + "'	and round( a.dongiaGOC * ( 1 + thueVAT / 100 ), 0 ) = '" + dongia + "' "+
								 " 				and e.NgayNhap < '" + ngaynhap + "' "+
								 " 		group by d.DONVI, a.dongiaGOC, thueVAT, e.PK_SEQ, e.NGAYNHAP"+
								 "	union	" +
								 " 		select e.PK_SEQ as donhangId, 0 as loaidonhang, e.NgayDonHang, SUM( isnull( c.soluongCHUAN, c.soluong ) ) as soluong, d.DONVI, round( a.dongiaCHUAN * ( 1 + thueVAT / 100 ), 0 ) as dongia "+
								 " 		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ "+
								 " 			inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET c on a.dondathang_fk = c.dondathang_fk and a.sanpham_fk = c.SANPHAM_FK and a.dvdl_fk = c.DVDL_FK"+
								 " 			inner join DONVIDOLUONG d on c.DVDL_FK = d.PK_SEQ"+
								 " 			inner join ERP_DONDATHANGNPP e on a.dondathang_fk = e.PK_SEQ"+
								 " 		where e.chuyenSALES = '1' and a.ddkd_fk = '" + ddkdId + "' and e.TRANGTHAI = 4 and e.KHACHHANG_FK in ( select PK_SEQ from KHACHHANG where PK_SEQ = '" + khId + "' ) "+
								 " 			and a.sanpham_fk = '" + spId + "'	and round( a.dongiaGOC * ( 1 + thueVAT / 100 ), 0 ) = '" + dongia + "' " + 
								 " 			and e.NgayDonHang < '" + ngaynhap + "' "+
								 " 		group by d.DONVI, a.dongiaCHUAN, thueVAT, e.PK_SEQ, e.NgayDonHang"+
								 " )"+
								 " DT left join"+
								 " ("+
								 " 	select b.DONHANG_FK, sum(b.SOLUONG) as soluong, b.DONVI, b.DONGIA, b.loaidonhang "+
								 " 	from DONHANGCTV a inner join DONHANGCTV_SP_CHITIET b on a.PK_SEQ = b.DONHANGCTV_FK"+
								 " 	where a.KHACHHANG_FK = '" + khId + "' and a.ddkd_fk = '" + ddkdId + "' and b.sanpham_fk = '" + spId + "' and b.dongia = '" + dongia + "' "+
								 "	group by b.DONHANG_FK, b.DONVI, b.DONGIA, b.loaidonhang	" +
								 " )"+
								 " DADUNG on DT.donhangId = DADUNG.DONHANG_FK and DT.loaidonhang = DADUNG.loaidonhang "+
								 " where  DT.soluong - ISNULL( DADUNG.SOLUONG, 0 ) > 0 " +
								 " order by DT.NgayDonHang asc, DT.loaidonhang asc ";
						
						System.out.println("::: DE XUAT DON HANG: " + query );
						ResultSet rsDonhang = db.get(query);
						
						double totalSL = 0;
						boolean exit = false;
						if( rsDonhang != null )
						{
							while( rsDonhang.next() )
							{
								String donhangId = rsDonhang.getString("donhangId");
								String loaidonhang = rsDonhang.getString("loaidonhang");
								double conlai = rsDonhang.getDouble("conlai");
								double soluongDENGHI = 0;
								
								totalSL += conlai;
								
								if( totalSL < soluongCAN )
								{
									soluongDENGHI = conlai;
								}
								else
								{
									soluongDENGHI = soluongCAN - ( totalSL - conlai );
									exit = true;
								}
								
								System.out.println("::: SO LUONG CAN: " + soluongCAN + " HIEN HUU: " + conlai + " DE XUAT: " + soluongDENGHI + " TOTAL: " + totalSL );
								if( soluongDENGHI > 0 )
								{
									String sql = "insert DONHANGCTV_SP_CHITIET( donhangctv_fk, donhang_fk, sanpham_fk, soluong, donvi, dongia, CTV_FK, loaidonhang ) " + 
												 "values( '" + id + "', '" + donhangId + "', '" + spId + "', '" + soluongDENGHI + "', N'" + donvi + "', '" + dongia + "', '" + ctvId + "', '" + loaidonhang + "' ) ";
									
									System.out.println("::: CHEN SAN PHAM: " + sql );
									if(db.updateReturnInt(sql) != 1)
									{
										msg = "2.Lỗi khi duyệt đơn hàng CTV: " + query;
										rsDonhang.close();
										rs.close();
										db.getConnection().rollback();
										return msg;
									}
								}
								
								if( exit )
								{
									rsDonhang.close();
									break;
								}
							}
							rsDonhang.close();
						}
					}
					rs.close();
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			return "Exception: " + e.getMessage();
		}
		finally
		{
			if(db!=null)db.shutDown();
		}

		return "";
	}
	

	private String DeleteChuyenKho(String lsxId, String userId)
	{
		dbutils db = new dbutils();
		String msg = "";

		try
		{
			db.getConnection().setAutoCommit(false);

			String query = "delete from donhangctv_sp where donhangctv_fk = '"+lsxId+"'";
			System.out.println("----Xoa donhangctv_sp " + query);
			if(db.updateReturnInt(query) <= 0)
			{
				msg = "2.Khong the xoa: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "delete from donhangctv_sp_chitiet where donhangctv_fk = '"+lsxId+"'";
			System.out.println("----Xoa donhangctv_sp_chitiet " + query);
			if(db.updateReturnInt(query) <= 0)
			{
				msg = "2.Khong the xoa: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "delete  from donhangctv where  pk_seq = '"+lsxId+"'";
			System.out.println("----Xoa donhangctv " + query);
			if(db.updateReturnInt(query) <= 0)
			{
				msg = "2.Khong the xoa: " + query;
				db.getConnection().rollback();
				return msg;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			return "Exception: " + e.getMessage();
		}
		finally
		{
			if(db!=null)db.shutDown();
		}

		return "";
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String action = request.getParameter("action");
		if (action == null) {
			action = "";
		}

		HttpSession session = request.getSession();
		String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
		String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
		
		IDonhangctvList obj = new DonhangctvList();
		
		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));

		Utility util = new Utility();
	
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		obj.setUserId(userId);
		obj.setTdv_dangnhap_id(tdv_dangnhap_id);
		obj.setNpp_duocchon_id(npp_duocchon_id);
		obj.setNppId(util.getIdNhapp(userId));

		String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "";
	    obj.setLoaidonhang(loaidonhang);
		
		if (action.equals("Tao moi")) {
			
			IDonhangctv lsxBean = new Donhangctv();
			
			lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
			lsxBean.setNpp_duocchon_id(npp_duocchon_id);
			lsxBean.setLoainhanvien(session.getAttribute("loainhanvien"));
			lsxBean.setDoituongId(session.getAttribute("doituongId"));
			
			lsxBean.setUserId(userId);
			lsxBean.setLoaidonhang(loaidonhang);
			lsxBean.createRs();

			session.setAttribute("lsxBean", lsxBean);

			String nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangCTVNew.jsp";
			response.sendRedirect(nextJSP);
		} 
		else if( action.equals("taobaocao") ) //MẪU TRÌNH DƯỢC VIÊN XEM
		{
			OutputStream out = response.getOutputStream();
			
			try
		    {
		    	/*response.setContentType("application/vnd.ms-excel");
		        response.setHeader("Content-Disposition", "attachment; filename=BCGuiHangTinh.xls");*/
		        
		        response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BaoCaoCongTacVien.xlsm");
		        
		        Workbook workbook = new Workbook();
		    	
				FileInputStream fstream = null;
				String chuoi = getServletContext().getInitParameter("path") + "\\BCDonHangCTV_TDV.xlsm";
				
				fstream = new FileInputStream(chuoi);		
				workbook.open(fstream);
				workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		        
			    String query = getSearchQuery_BaoCao(request, obj);
			   // String query =  getSearchQuery_trinhduocvien(request, obj);
			    CreateStaticData(workbook, query, obj);
			
			    //Saving the Excel file
			    workbook.save(out);
			    fstream.close();
		    }
		    catch (Exception ex){ ex.printStackTrace(); }
			
		}
		else 
		{
			if (  action.equals("view") || action.equals("next") || action.equals("prev")) {
				
				String nextJSP="";
				String view = request.getParameter("view");
			    if(view == null)
			    	view = "";
			    String search="";
			    if(view.equals("CTV"))
				{
					 search = getSearchQuery_trinhduocvien(request, obj);
					 obj.setRsxemCTV(search);
					 nextJSP = "/TraphacoHYERP/pages/Distributor/XemCTV.jsp";
				}
				else
				{
					search = getSearchQuery(request, obj);
				 nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangCTV.jsp";
				
				}
				obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
				obj.setTdv_dangnhap_id(tdv_dangnhap_id);
				obj.setNpp_duocchon_id(npp_duocchon_id);
				obj.setUserId(userId);
				obj.init(search);
				obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
				session.setAttribute("obj", obj);
				
				
				
				response.sendRedirect(nextJSP);
			} 
			else 
			{
				
				String view = request.getParameter("view");
				String nextJSP="";
				 if(view == null)
				    	view = "";
				 String search="";
					if(view.equals("CTV"))
					{
						 search = getSearchQuery_trinhduocvien(request, obj);
						 obj.setRsxemCTV(search);
						 nextJSP = "/TraphacoHYERP/pages/Distributor/XemCTV.jsp";
					}
					else
					{
						 search = getSearchQuery(request, obj);
					 nextJSP = "/TraphacoHYERP/pages/Distributor/DonHangCTV.jsp";
					}
				obj.setTdv_dangnhap_id(tdv_dangnhap_id);
				obj.setNpp_duocchon_id(npp_duocchon_id);
				obj.init(search);
				obj.setUserId(userId);

				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);

				
				response.sendRedirect(nextJSP);
			}
		}
	}

	private String getSearchQuery(HttpServletRequest request, IDonhangctvList obj) 
	{
		Utility util = new Utility();
		
		String query = " select a.pk_Seq,b.MA as nppMa,b.TEN as nppTen,a.ngaynhap, f.ten as tenkh,c.TEN as nguoiTao,d.TEN as nguoiSua,a.TRANGTHAI,a.SOTIENBVAT,a.Modified_Date,a.created_date "
					+ "	 from donhangctv a inner join NHAPHANPHOI b on b.PK_SEQ=a.NPP_FK "
					+ "		inner join NHANVIEN c on c.PK_SEQ=a.NGUOITAO  "
					+ "		inner join NHANVIEN d on d.PK_SEQ=a.NGUOISUA "
					+ "		inner join KHACHHANG f on f.pk_seq = a.khachhang_FK " 
					+ "		inner join DAIDIENKINHDOANH dd on a.ddkd_fk = dd.pk_seq " 
					+ "		inner join DONHANGCTV_SP dh_sp on a.pk_seq = dh_sp.donhangctv_fk " 
					+ " where a.npp_fk = '" + obj.getNppId() + "'";

		String tungaySX = request.getParameter("tungay");
		if (tungaySX == null)
			tungaySX = "";
		obj.setTungayTao(tungaySX);

		String denngaySX = request.getParameter("denngay");
		if (denngaySX == null)
			denngaySX = "";
		obj.setDenngayTao(denngaySX);

		String sochungtu = request.getParameter("sochungtu");
		if (sochungtu == null)
			sochungtu = "";
		obj.setSochungtu(sochungtu);

		String trangthai = request.getParameter("trangthai");
		if (trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String timkiem = request.getParameter("timkiem");
		if (timkiem == null)
			timkiem = "";
		obj.setTimkiem(timkiem);

		String nppId = request.getParameter("nppId");
		if (nppId == null)
			nppId = "";
		obj.setNppId(nppId);

		String khId = request.getParameter("khId");
		if (khId == null)
			khId = "";
		obj.setKhId(khId);
		
		String tenTDV = request.getParameter("tenTDV");
		if (tenTDV == null)
			tenTDV = "";
		obj.setTenTDV(tenTDV);
		
		String tenSP = request.getParameter("tenSP");
		if (tenSP == null)
			tenSP = "";
		obj.setTenSP(tenSP);

		if (tungaySX.length() > 0)
			query += " and a.ngaynhap >= '" + tungaySX + "'";

		if (denngaySX.length() > 0)
			query += " and a.ngaynhap <= '" + denngaySX + "'";

		if (trangthai.length() > 0)
			query += " and a.TrangThai = '" + trangthai + "'";

		if (sochungtu.length() > 0)
			query += " and a.sochungtu like '%" + sochungtu + "%'";
		
		if (timkiem.length() > 0)
			query += " and ( f.timkiem like N'%" + util.replaceAEIOU( timkiem ) + "%')";
		
		if (tenTDV.length() > 0)
			query += " and ( dd.timkiem like N'%" + util.replaceAEIOU( tenTDV ) + "%')";
		
		if (tenSP.length() > 0)
			query += " and ( dh_sp.sanpham_fk in ( select pk_seq from SANPHAM where timkiem like N'%" + util.replaceAEIOU( tenSP ) + "%' ) )";

		System.out.print(query);
		return query;

	}
	

	private String getSearchQuery_BaoCao(HttpServletRequest request, IDonhangctvList obj) 
	{
		HttpSession session = request.getSession();
		Utility util = new Utility();
		
		/*String query =  "select a.ngaynhap, e.maFAST as makhCAP1, e.TEN as tenkhCAP1, ' ' as makhCAP2, ' ' as tenkhCAP2, d.MA as maCTV, d.TEN as tenCTV, '' as chucvu, g.diengiai as kenh,  "+
						 "		c.MA_FAST as maSP, c.TEN as tenSP, b.DONGIA, b.SOLUONG, b.DONGIA * b.SOLUONG as doanhso, f.machungtu as sodonhang, f.NgayDonHang as ngaydonhang, "+
						 "		'' as ptCHI, '' as chi, '' as ptlythuyet, a.ghichu, '' as tt_gt "+
						 "from DONHANGCTV a inner join DONHANGCTV_SP_CHITIET b on a.PK_SEQ = b.DONHANGCTV_FK "+
						 "	inner join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ "+
						 "	inner join CONGTACVIEN d on b.ctv_fk = d.PK_SEQ "+
						 "	inner join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ "+
						 "	inner join ERP_DONDATHANGNPP f on b.donhang_fk = f.PK_SEQ "+
						 "	left join KENHBANHANG g on f.kbh_fk = g.PK_SEQ "+
						 "where a.TRANGTHAI in ( 1, 2 ) ";*/

		String query = " select asm.TEN as asmTen, gs.ten as gsbhTen, dd.TEN as ddkdTen, db.ten as dbTen, tt.TEN as tinhTen,"+
				 " 		 a.ngaynhap, "+
				 " 	  case b.loaidonhang when 1 then npp.MaFAST else e.maFAST end as makhCAP1, "+
				 " 	  case b.loaidonhang when 1 then npp.TEN else e.TEN end as tenkhCAP1, "+
				 " 	  case b.loaidonhang when 1 then e.MaFAST else ' ' end as makhCAP2, "+
				 " 	  case b.loaidonhang when 1 then e.TEN else ' ' end as tenkhCAP2, "+
				 " 		 d.MA as maCTV, d.TEN as tenCTV, '' as chucvu,"+
				 " 		( select ten from HETHONGBANHANG where PK_SEQ = ( select htbh_fk from hethongbanhang_kenhbanhang where kbh_fk = ISNULL( g.PK_SEQ, kbh.PK_SEQ ) ) ) as htbh, isnull( g.diengiai, kbh.DIENGIAI) as kenh,  "+
				 " 		c.MA_FAST as maSP, c.TEN as tenSP, b.DONGIA, b.SOLUONG, b.DONGIA * b.SOLUONG as doanhso, isnull(f.machungtu, dh.machungtu) as sodonhang, isnull(f.NgayDonHang, dh.NGAYNHAP) as ngaydonhang, "+
				 " 		'' as ptCHI, '' as chi, '' as ptlythuyet, a.ghichu, '' as tt_gt,"+
				 " 	  ISNULL( (	Select hdnpp.sohoadon + ',' AS [text()]  "+
				 " 				From ERP_HOADONNPP_DDH hd_dh inner join ERP_HOADONNPP hdnpp on hd_dh.HOADONNPP_FK = hdnpp.PK_SEQ   "+
				 " 				Where  hd_dh.DDH_FK  = f.PK_SEQ and hdnpp.TRANGTHAI not in ( 3, 5 )"+
				 " 				For XML PATH ('') ), '' )  as sohoadon,"+
				 " 	  ISNULL( (	Select hdnpp.NGAYGHINHAN + ',' AS [text()]  "+
				 " 				From ERP_HOADONNPP_DDH hd_dh inner join ERP_HOADONNPP hdnpp on hd_dh.HOADONNPP_FK = hdnpp.PK_SEQ   "+
				 " 				Where  hd_dh.DDH_FK  = f.PK_SEQ and hdnpp.TRANGTHAI not in ( 3, 5 )"+
				 " 				For XML PATH ('') ), '' )  as ngayhoadon      "+
				 " from DONHANGCTV a inner join DONHANGCTV_SP_CHITIET b on a.PK_SEQ = b.DONHANGCTV_FK "+
				 " 	inner join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ "+
				 " 	inner join CONGTACVIEN d on b.ctv_fk = d.PK_SEQ "+
				 " 	inner join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ "+
				 " 	"+
				 " 	left join ERP_DONDATHANGNPP f on b.donhang_fk = f.PK_SEQ and b.loaidonhang = 0 "+
				 " 	left join KENHBANHANG g on f.kbh_fk = g.PK_SEQ"+
				 " 	"+
				 " 	left join DONHANG dh on b.donhang_fk = dh.PK_SEQ and b.loaidonhang = 1"+
				 " 	left join KENHBANHANG kbh on dh.kbh_fk = kbh.PK_SEQ"+
				 " 	left join NHAPHANPHOI npp on dh.NPP_FK = npp.PK_SEQ"+
				 " 	"+
				 " 	inner join DAIDIENKINHDOANH dd on a.ddkd_fk = dd.PK_SEQ "+
				 " 	inner join GIAMSATBANHANG gs on a.gsbh_fk = gs.PK_SEQ"+
				 " 	inner join ASM asm on a.asm_fk = asm.PK_SEQ"+
				 " 	left join DIABAN db on e.diaban = db.PK_SEQ"+
				 " 	left join TINHTHANH tt on e.TINHTHANH_FK = tt.PK_SEQ"+
				 " where a.TRANGTHAI in ( 1, 2 ) ";
		
		String tungaySX = request.getParameter("tungay");
		if (tungaySX == null)
			tungaySX = "";
		obj.setTungayTao(tungaySX);

		String denngaySX = request.getParameter("denngay");
		if (denngaySX == null)
			denngaySX = "";
		obj.setDenngayTao(denngaySX);

		String sochungtu = request.getParameter("sochungtu");
		if (sochungtu == null)
			sochungtu = "";
		obj.setSochungtu(sochungtu);

		String trangthai = request.getParameter("trangthai");
		if (trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String timkiem = request.getParameter("timkiem");
		if (timkiem == null)
			timkiem = "";
		obj.setTimkiem(timkiem);

		String nppId = request.getParameter("nppId");
		if (nppId == null)
			nppId = "";
		obj.setNppId(nppId);

		String khId = request.getParameter("khId");
		if (khId == null)
			khId = "";
		obj.setKhId(khId);
		
		String tenTDV = request.getParameter("tenTDV");
		if (tenTDV == null)
			tenTDV = "";
		obj.setTenTDV(tenTDV);
		
		String tenSP = request.getParameter("tenSP");
		if (tenSP == null)
			tenSP = "";
		obj.setTenSP(tenSP);
		
		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));

		if (nppId.length() > 0)
			query += " and a.npp_fk = '" + nppId + "'";
		
		if (tungaySX.length() > 0)
			query += " and a.ngaynhap >= '" + tungaySX + "'";

		if (denngaySX.length() > 0)
			query += " and a.ngaynhap <= '" + denngaySX + "'";

		if (trangthai.length() > 0)
			query += " and a.TrangThai = '" + trangthai + "'";

		if (sochungtu.length() > 0)
			query += " and a.sochungtu like '%" + sochungtu + "%'";
		
		if (timkiem.length() > 0)
			query += " and ( e.timkiem like N'%" + util.replaceAEIOU( timkiem ) + "%')";

		if (tenTDV.length() > 0)
			query += " and ( dd.timkiem like N'%" + util.replaceAEIOU(tenTDV) + "%')";
		
		if (tenSP.length() > 0)
			query += " and ( c.timkiem like N'%" + util.replaceAEIOU(tenSP) + "%')";
		
		//PHAN QUYEN
		query += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "a", "ddkd_fk", obj.getLoainhanvien(), obj.getDoituongId() );
		
		
		query = " select asmTen, gsbhTen, ddkdTen, dbTen, tinhTen, makhCAP1, tenkhCAP1, makhCAP2, tenkhCAP2, maCTV, tenCTV, chucvu, htbh, kenh,   		"+
				 " 		maSP, tenSP, ghichu, DONGIA, sum(SOLUONG) as soluong, sum(doanhso) as doanhso	         "+
				 " from"+
				 " ("+
				 		query +
				 " )"+
				 " DT group by asmTen, gsbhTen, ddkdTen, dbTen, tinhTen, makhCAP1, tenkhCAP1, makhCAP2, tenkhCAP2, maCTV, tenCTV, chucvu, htbh, kenh,   		"+
				 " 			maSP, tenSP, DONGIA, ghichu";
		
		System.out.print(":: BAO CAO CTV MAU DDKD: " + query);
		return query;

	}

	private void CreateStaticData(Workbook workbook, String query, IDonhangctvList obj) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    Cell cell = null;
	    Style style;
		Font font2 = new Font();
		//font2.setName("Calibri");				
		font2.setSize(11);
	    
	    cell = cells.getCell("A2"); cell.setValue( "Từ ngày: " + obj.getTungayTao() ); 
	    style = cell.getStyle(); style.setFont(font2); cell.setStyle(style);
	    
	    cell = cells.getCell("A3");	cell.setValue( "Đến ngày: " + obj.getDenngayTao() ); 
	    style = cell.getStyle(); style.setFont(font2); cell.setStyle(style);
	    
		dbutils db = new dbutils();
		
		ResultSet rs = db.get(query);

		int i = 6;
		if(rs != null)
		{
			try 
			{
				int stt = 1;
				while(rs.next())
				{
					//cell = cells.getCell("A" + Integer.toString(i));	cell.setValue( rs.getString("asmTen") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					//cell = cells.getCell("B" + Integer.toString(i));	cell.setValue( rs.getString("gsbhTen") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("A" + Integer.toString(i));	cell.setValue( rs.getString("ddkdTen") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("B" + Integer.toString(i));	cell.setValue( rs.getString("dbTen") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue( rs.getString("tinhTen") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue( rs.getString("makhCAP1") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue( rs.getString("tenkhCAP1") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue( rs.getString("makhCAP2") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue( rs.getString("tenkhCAP2") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					
					cell = cells.getCell("H" + Integer.toString(i));	cell.setValue( rs.getString("maCTV") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("I" + Integer.toString(i));	cell.setValue( rs.getString("tenCTV") ); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("J" + Integer.toString(i));	cell.setValue( rs.getString("chucvu") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					
					cell = cells.getCell("K" + Integer.toString(i));	cell.setValue( rs.getString("htbh") ); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("L" + Integer.toString(i));	cell.setValue( rs.getString("kenh") ); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					
					cell = cells.getCell("M" + Integer.toString(i));	cell.setValue( rs.getString("maSP") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("N" + Integer.toString(i));	cell.setValue( rs.getString("tenSP") ); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					
					cell = cells.getCell("O" + Integer.toString(i));	cell.setValue( rs.getDouble("DONGIA") ); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("P" + Integer.toString(i));	cell.setValue( rs.getDouble("SOLUONG") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("Q" + Integer.toString(i));	cell.setValue( rs.getDouble("doanhso") );	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					
					cell = cells.getCell("R" + Integer.toString(i));	cell.setValue( rs.getString("ghichu") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					
					//cell = cells.getCell("T" + Integer.toString(i));	cell.setValue( rs.getString("sodonhang") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					//cell = cells.getCell("U" + Integer.toString(i));	cell.setValue( rs.getString("ngaydonhang") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					//cell = cells.getCell("V" + Integer.toString(i));	cell.setValue( rs.getString("sohoadon") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					//cell = cells.getCell("W" + Integer.toString(i));	cell.setValue( rs.getString("ngayhoadon") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					
					//cell = cells.getCell("R" + Integer.toString(i));	cell.setValue( rs.getString("ptCHI") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					//cell = cells.getCell("S" + Integer.toString(i));	cell.setValue( rs.getString("chi") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					//cell = cells.getCell("T" + Integer.toString(i));	cell.setValue( rs.getString("ptlythuyet") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					//cell = cells.getCell("U" + Integer.toString(i));	cell.setValue( rs.getString("ghichu") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					//cell = cells.getCell("V" + Integer.toString(i));	cell.setValue( rs.getString("tt_gt") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					
					i++;
					stt ++;
				}
				rs.close();
			}
			catch (Exception e){ 
				db.shutDown();
				e.printStackTrace(); }
		}
		
		db.shutDown();
	}
	
	private void setCellBorderStyle(Cell cell) 
	{
		Style style = cell.getStyle();
		style.setHAlignment(HorizontalAlignmentType.CENTRED);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		cell.setStyle(style);
	}
	
	
	public String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	
	private String getSearchQuery_trinhduocvien(HttpServletRequest request, IDonhangctvList obj) 
	{
		HttpSession session = request.getSession();
		
		/*String query =  "select a.ngaynhap, e.maFAST as makhCAP1, e.TEN as tenkhCAP1, ' ' as makhCAP2, ' ' as tenkhCAP2, d.MA as maCTV, d.TEN as tenCTV, '' as chucvu, g.diengiai as kenh,  "+
						 "		c.MA_FAST as maSP, c.TEN as tenSP, b.DONGIA, b.SOLUONG, b.DONGIA * b.SOLUONG as doanhso, f.machungtu as sodonhang, f.NgayDonHang as ngaydonhang, "+
						 "		'' as ptCHI, '' as chi, '' as ptlythuyet, a.ghichu, '' as tt_gt "+
						 "from DONHANGCTV a inner join DONHANGCTV_SP_CHITIET b on a.PK_SEQ = b.DONHANGCTV_FK "+
						 "	inner join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ "+
						 "	inner join CONGTACVIEN d on b.ctv_fk = d.PK_SEQ "+
						 "	inner join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ "+
						 "	inner join ERP_DONDATHANGNPP f on b.donhang_fk = f.PK_SEQ "+
						 "	left join KENHBANHANG g on f.kbh_fk = g.PK_SEQ "+
						 "where a.TRANGTHAI in ( 1, 2 ) ";*/

		String query = "\n select isnull((select dbo.getkhuvuc_ctv(e.PK_SEQ)),'') as tenkv,dd.maFAST,b.DONVI,asm.TEN as asmTen, gs.ten as gsbhTen, dd.TEN as ddkdTen, db.ten as dbTen, tt.TEN as tinhTen,"+
				"\n 		 a.ngaynhap, "+
				"\n 	  case b.loaidonhang when 1 then npp.MaFAST else e.maFAST end as makhCAP1, "+
				 "\n 	  case b.loaidonhang when 1 then npp.TEN else e.TEN end as tenkhCAP1, "+
				 " \n	  case b.loaidonhang when 1 then e.MaFAST else ' ' end as makhCAP2, "+
				 " 	\n  case b.loaidonhang when 1 then e.TEN else ' ' end as tenkhCAP2, "+
				 " \n		 d.MA as maCTV, d.TEN as tenCTV, '' as chucvu,"+
				 " \n		( select ten from HETHONGBANHANG where PK_SEQ = ( select htbh_fk from hethongbanhang_kenhbanhang where kbh_fk = ISNULL( g.PK_SEQ, kbh.PK_SEQ ) ) ) as htbh, isnull( g.diengiai, kbh.DIENGIAI) as kenh,  "+
				 " \n		c.MA_FAST as maSP, c.TEN as tenSP, b.DONGIA, b.SOLUONG, b.DONGIA * b.SOLUONG as doanhso, isnull(f.machungtu, dh.machungtu) as sodonhang, isnull(f.NgayDonHang, dh.NGAYNHAP) as ngaydonhang, "+
				 " \n		'' as ptCHI, '' as chi, '' as ptlythuyet, a.ghichu, '' as tt_gt,"+
				 " 	\n  ISNULL( (	Select hdnpp.sohoadon + ',' AS [text()]  "+
				 " 	\n			From ERP_HOADONNPP_DDH hd_dh inner join ERP_HOADONNPP hdnpp on hd_dh.HOADONNPP_FK = hdnpp.PK_SEQ   "+
				 " 	\n			Where  hd_dh.DDH_FK  = f.PK_SEQ and hdnpp.TRANGTHAI not in ( 3, 5 )"+
				 " 	\n			For XML PATH ('') ), '' )  as sohoadon,"+
				 " 	\n  ISNULL( (	Select hdnpp.NGAYGHINHAN + ',' AS [text()]  "+
				 " 	\n			From ERP_HOADONNPP_DDH hd_dh inner join ERP_HOADONNPP hdnpp on hd_dh.HOADONNPP_FK = hdnpp.PK_SEQ   "+
				 " 	\n			Where  hd_dh.DDH_FK  = f.PK_SEQ and hdnpp.TRANGTHAI not in ( 3, 5 )"+
				 " 	\n			For XML PATH ('') ), '' )  as ngayhoadon      "+
				 "\n from DONHANGCTV a inner join DONHANGCTV_SP_CHITIET b on a.PK_SEQ = b.DONHANGCTV_FK "+
				 "\n 	inner join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ "+
				 " \n	inner join CONGTACVIEN d on b.ctv_fk = d.PK_SEQ "+
				 "\n 	inner join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ "+
				 " \n	"+
				 " \n	left join ERP_DONDATHANGNPP f on b.donhang_fk = f.PK_SEQ and b.loaidonhang = 0 "+
				 " \n	left join KENHBANHANG g on f.kbh_fk = g.PK_SEQ"+
				 " \n	"+
				 " \n	left join DONHANG dh on b.donhang_fk = dh.PK_SEQ and b.loaidonhang = 1"+
				 "\n 	left join KENHBANHANG kbh on dh.kbh_fk = kbh.PK_SEQ"+
				 " \n	left join NHAPHANPHOI npp on dh.NPP_FK = npp.PK_SEQ"+
				 "\n 	"+
				 " \n	inner join DAIDIENKINHDOANH dd on a.ddkd_fk = dd.PK_SEQ "+
				 " \n	inner join GIAMSATBANHANG gs on a.gsbh_fk = gs.PK_SEQ"+
				 " \n	inner join ASM asm on a.asm_fk = asm.PK_SEQ"+
				 " \n	left join DIABAN db on e.diaban = db.PK_SEQ"+
				 " \n	left join TINHTHANH tt on e.TINHTHANH_FK = tt.PK_SEQ"+
				 "\n	left join VUNG v on v.QUOCGIA_FK=tt.QUOCGIA_FK "+
		         "\n	left join KHUVUC kv on kv.VUNG_FK=v.PK_SEQ "+
				 "\n where a.TRANGTHAI in ( 1, 2 ) ";
		
		String tungaySX = request.getParameter("tungay");
		if (tungaySX == null)
			tungaySX = "";
		obj.setTungayTao(tungaySX);

		String denngaySX = request.getParameter("denngay");
		if (denngaySX == null)
			denngaySX = "";
		obj.setDenngayTao(denngaySX);

		String sochungtu = request.getParameter("sochungtu");
		if (sochungtu == null)
			sochungtu = "";
		obj.setSochungtu(sochungtu);

		String trangthai = request.getParameter("trangthai");
		if (trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String timkiem = request.getParameter("timkiem");
		if (timkiem == null)
			timkiem = "";
		obj.setTimkiem(timkiem);

		String nppId = request.getParameter("nppId");
		if (nppId == null)
			nppId = "";
		obj.setNppId(nppId);

		String khId = request.getParameter("khId");
		if (khId == null)
			khId = "";
		obj.setKhId(khId);
		
		String tenTDV = request.getParameter("tenTDV");
		if (tenTDV == null)
			tenTDV = "";
		obj.setTenTDV(tenTDV);
		
		String tenSP = request.getParameter("tenSP");
		if (tenSP == null)
			tenSP = "";
		obj.setTenSP(tenSP);
		
		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));

	    Utility util = new Utility();
		if (nppId.length() > 0)
			query += " and a.npp_fk = '" + nppId + "'";
		
		if (tungaySX.length() > 0)
			query += " and a.ngaynhap >= '" + tungaySX + "'";

		if (denngaySX.length() > 0)
			query += " and a.ngaynhap <= '" + denngaySX + "'";

		if (trangthai.length() > 0)
			query += " and a.TrangThai = '" + trangthai + "'";

		if (sochungtu.length() > 0)
			query += " and a.sochungtu like '%" + sochungtu + "%'";
		
		if (timkiem.length() > 0)
			query += " and ( e.timkiem like N'%" + util.replaceAEIOU( timkiem ) + "%')";
		
		if (tenTDV.length() > 0)
			query += " and ( dd.timkiem like N'%" + util.replaceAEIOU(tenTDV) + "%')";
		
		if (tenSP.length() > 0)
			query += " and ( c.timkiem like N'%" + util.replaceAEIOU(tenSP) + "%')";

		//PHAN QUYEN
		query += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "a", "ddkd_fk", obj.getLoainhanvien(), obj.getDoituongId() );
		
		
		query = "\n select * from (select  ROW_NUMBER() OVER (PARTITION BY data.mafast order by data.mafast  desc)as stt,*,SUM(doanhso) OVER (PARTITION BY mafast) as tongdoanhso,'1' as type from ( select tenkv,mafast,DONVI,asmTen, gsbhTen, ddkdTen, dbTen, tinhTen, makhCAP1, tenkhCAP1, makhCAP2, tenkhCAP2, maCTV, tenCTV, chucvu, htbh, kenh,   		"+
				 " \n		maSP, tenSP, ghichu, DONGIA, sum(SOLUONG) as soluong, sum(doanhso) as doanhso	         "+
				 "\n from"+
				 "\n ("+
				 		query +
				 "\n )"+
				 "\n DT group by tenkv,mafast,donvi,asmTen, gsbhTen, ddkdTen, dbTen, tinhTen, makhCAP1, tenkhCAP1, makhCAP2, tenkhCAP2, maCTV, tenCTV, chucvu, htbh, kenh,   		"+
				 " \n			maSP, tenSP, DONGIA, ghichu,DONVI) as data"+
				 "\n union all    "+
				"\n select  ROW_NUMBER() OVER (PARTITION BY data.kenh,data.mafast order by data.kenh,data.mafast  desc)as stt,*,SUM(doanhso) OVER (PARTITION BY data.kenh,data.mafast) as tongdoanhso,'2' as type from ( select tenkv,mafast,DONVI,asmTen, gsbhTen, ddkdTen, dbTen, tinhTen, makhCAP1, tenkhCAP1, makhCAP2, tenkhCAP2, maCTV, tenCTV, chucvu, htbh, kenh,   		"+
				 " \n		maSP, tenSP, ghichu, DONGIA, sum(SOLUONG) as soluong, sum(doanhso) as doanhso	         "+
				 "\n from"+
				 "\n ("+
				 		query +
				 "\n )"+
				 "\n DT group by tenkv,mafast,donvi,asmTen, gsbhTen, ddkdTen, dbTen, tinhTen, makhCAP1, tenkhCAP1, makhCAP2, tenkhCAP2, maCTV, tenCTV, chucvu, htbh, kenh,   		"+
				 " \n			maSP, tenSP, DONGIA, ghichu,DONVI) as data"+
				"\n  union all	"+
				 "\n select  ROW_NUMBER() OVER (PARTITION BY data.makhcap2,data.mafast order by data.makhcap2,data.mafast  desc)as stt,*,SUM(doanhso) OVER (PARTITION BY data.makhcap2,data.mafast) as tongdoanhso,'3' as type from ( select tenkv,mafast,DONVI,asmTen, gsbhTen, ddkdTen, dbTen, tinhTen, makhCAP1, tenkhCAP1, makhCAP2, tenkhCAP2, maCTV, tenCTV, chucvu, htbh, kenh,   		"+
				 " \n		maSP, tenSP, ghichu, DONGIA, sum(SOLUONG) as soluong, sum(doanhso) as doanhso	         "+
				 "\n from"+
				 "\n ("+
				 		query +
				 "\n )"+
				 "\n DT group by tenkv,mafast,donvi,asmTen, gsbhTen, ddkdTen, dbTen, tinhTen, makhCAP1, tenkhCAP1, makhCAP2, tenkhCAP2, maCTV, tenCTV, chucvu, htbh, kenh,   		"+
				 " \n			maSP, tenSP, DONGIA, ghichu,DONVI) as data"+
				"\n  union all	"+
				 "\n select  ROW_NUMBER() OVER (PARTITION BY data.mafast order by data.mafast  desc)as stt,*,SUM(doanhso) OVER (PARTITION BY mafast) as tongdoanhso,'4' as type from ( select tenkv,mafast,DONVI,asmTen, gsbhTen, ddkdTen, dbTen, tinhTen, makhCAP1, tenkhCAP1, makhCAP2, tenkhCAP2, maCTV, tenCTV, chucvu, htbh, kenh,   		"+
				 " \n		maSP, tenSP, ghichu, DONGIA, sum(SOLUONG) as soluong, sum(doanhso) as doanhso         "+
				 "\n from"+
				 "\n ("+
				 		query +
				 "\n )"+
				 "\n DT group by tenkv,mafast,donvi,asmTen, gsbhTen, ddkdTen, dbTen, tinhTen, makhCAP1, tenkhCAP1, makhCAP2, tenkhCAP2, maCTV, tenCTV, chucvu, htbh, kenh,   		"+
				 " \n			maSP, tenSP, DONGIA, ghichu,DONVI) as data) as dd order by maFAST,type,stt ASC";
		
		System.out.println(":: BAO CAO CTV MAU DDKD1111: " + query);
		return query;

	}
	
	
}