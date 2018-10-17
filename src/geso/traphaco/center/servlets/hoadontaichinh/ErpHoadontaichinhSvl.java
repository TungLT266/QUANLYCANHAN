package geso.traphaco.center.servlets.hoadontaichinh;

import geso.traphaco.center.beans.hoadontaichinh.IErpHoadontaichinh;
import geso.traphaco.center.beans.hoadontaichinh.IErpHoadontaichinhList;
import geso.traphaco.center.beans.hoadontaichinh.imp.ErpHoadontaichinh;
import geso.traphaco.center.beans.hoadontaichinh.imp.ErpHoadontaichinhList;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.db.sql.dbutils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.HorizontalAlignmentType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class ErpHoadontaichinhSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpHoadontaichinhSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpHoadontaichinhList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    OutputStream out = response.getOutputStream();
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    	    
    	String lsxId = util.getId(querystring);
	    obj = new ErpHoadontaichinhList();
	   
	    String nppId = util.getIdNhapp(userId);
	    
	    String noibo = request.getParameter("NOIBO") == null ? "0" : request.getParameter("NOIBO");
	    obj.setNOIBO(noibo);
	    
	    String km = request.getParameter("KM");
	    if( km == null )
	    	km = "0";
	    obj.setLoaikm(km);

	    String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "";
	    obj.setLoaidonhang(loaidonhang);
 
    	if(action.equals("chot"))
    	{
    		GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
    		IErpHoadontaichinh hd = new ErpHoadontaichinh(lsxId);
    		hd.setUserId(userId);
    		boolean flag = hd.chot(lsxId, userId);
    		
    		if( flag == false )
			{
    			obj.setMsg( hd.getMsg() );
			}
    		/*else
    		{
    			//Tạo chuyển kho tự động
    			hd.setUserId(userId);
    			String error = hd.TaoXuatKhoTuDong();
    			System.out.println("::: LOI KHI TAO CHUYEN KHO: " + error);
    		}*/
    		hd.DBclose();
    		

			obj.setUserId(userId);
			
			obj.init("");
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			String nextJSP = "/TraphacoHYERP/pages/Center/ErpHoaDonTaiChinh.jsp";
			response.sendRedirect(nextJSP); 
    	}
    	else if(action.equals("delete"))
    	{
    		String msg = this.Delete(lsxId, userId, nppId,obj);
			obj.setMsg(msg);
			obj.setUserId(userId);
			GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
			obj.init("");
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			String nextJSP = "/TraphacoHYERP/pages/Center/ErpHoaDonTaiChinh.jsp";
			response.sendRedirect(nextJSP); 
    	}
    	else if(action.equals("huy"))
    	{
    		String msg = this.Huy(lsxId, userId, nppId,obj);
			obj.setMsg(msg);
			obj.setUserId(userId);	
			GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
			obj.init("");
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			String nextJSP = "/TraphacoHYERP/pages/Center/ErpHoaDonTaiChinh.jsp";
			response.sendRedirect(nextJSP); 
    	}
    	else if(action.equals("printBANGKE"))
    	{
    		String query = "select b.MA, b.TEN, a.DONVITINH as DONVI, a.SOLUONG, a.DONGIA, a.SOLUONG * a.DONGIA as doanhthu, " + 
    					   " a.SOLUONG * a.DONGIA * a.VAT / 100.0 as VAT, a.SOLUONG * a.DONGIA * ( 1 + ( a.VAT / 100.0 ) ) as AVAT " +
						   "from ERP_HOADON_SP a inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ " +
						   "where a.HOADON_FK = '" + lsxId + "' " +
						   "order by b.TEN asc"; 
    		
    		System.out.println("::: QUERY BANG KE: " + query);
    		try
			{ 
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BangKeHoaDonNoiBo.xlsm");
				FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BCBangKeNoiBo.xlsm");
				
				Workbook workbook = new Workbook();
				workbook.open(fstream);
				workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

				CreateStaticHeader(workbook, obj);

				CreateStaticData(workbook, obj, query);
						
				workbook.save(out);
				fstream.close();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				obj.setMsg("Khong the tao pivot.");
			}
    	}
			else
			{

			obj.setUserId(userId);
			obj.init("");
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			String nextJSP = "/TraphacoHYERP/pages/Center/ErpHoaDonTaiChinh.jsp";
			response.sendRedirect(nextJSP); 
    		return;
			}
    	}
    	
/*    	// CHẠY LẠI ĐỊNH KHOẢN

    	/*dbutils db = new dbutils();
		IErpHoadontaichinh hd = new ErpHoadontaichinh(lsxId);
		String query =  "SELECT PK_SEQ AS ID \n " +
						"FROM ERP_HOADON  \n " +
						"WHERE TRANGTHAI IN (2, 4) AND \n " + 
						"CONVERT(VARCHAR, PK_SEQ) NOT IN ( \n " +
						"SELECT SOCHUNGTU FROM ERP_PHATSINHKETOAN \n " + 
						"WHERE LOAICHUNGTU = N'Hóa đơn tài chính' AND (LOAIHD = 'HDHO' OR loaihd = 'HDHO_NB')) ";
		ResultSet rs = db.get(query);
		
		try{
			while(rs.next()){
				boolean flag = hd.Chaydinhkhoan(rs.getString("ID"));		
			}
			rs.close();
			db.shutDown();
		}catch(java.sql.SQLException e){}*/
		
		
    	// KẾT THÚC CHẠY ĐỊNH KHOẢN

  

	private String Huy(String lsxId, String userId, String nppId,IErpHoadontaichinhList obj) 
	{
		dbutils db = new dbutils();
		String msg = "";

		Utility util = new Utility();
		/*msg= util.Check_Huy_NghiepVu_KhoaSo("ERP_HOADONNPP", lsxId, "ngayxuatHD", db);
		if(msg.length()>0)
		{
			db.shutDown();
			return msg;
		}*/

		try
		{	
			db.getConnection().setAutoCommit(false);
			String query = "";

			// Kiểm tra import =1 thì k cho hủy
			query = "select COUNT(*) as sodong from ERP_YCXUATKHO_DDH where hoadon_fk = '" + lsxId + "' ";
			System.out.println(query);

			ResultSet rsKT = db.get(query);
			int dacoXK = 0;

			if(rsKT!= null)
			{
				while(rsKT.next())
				{
					dacoXK =  rsKT.getInt("sodong");
				}
				rsKT.close();
			}

			if( dacoXK > 0 )
			{
				msg = "Hóa đơn đã có xuất kho. Bạn không thể xóa / hủy. ";
				db.getConnection().rollback();
				return msg;
			}

			String loaichungtu = "Hóa đơn HO - hủy"; 
			String chungtuId = lsxId; 
			String transactionId = util.createTransactionId();
			
			//NHẢ LẠI TỒN FKHO
			//Tăng kho ngược lại trước khi xóa
			query = "  select b.NgayDonHang, b.Kho_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, " + 
					" 		a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, a.phieudt, a.phieueo, SUM( a.SoLuongCHUAN ) as soluong  " + 
					"  from ERP_DONDATHANG_SANPHAM_CHITIET a inner join ERP_DONDATHANG b on a.DonDatHang_FK = b.PK_SEQ " + 
					"  where b.trangthai != 3 and  b.PK_SEQ = ( select DonDatHang_FK from ERP_HOADON where PK_SEQ = '" + lsxId + "' ) " + 
					"  group by b.NgayDonHang, b.Kho_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, a.bin_fk, a.phieudt, a.phieueo ";
			
			System.out.println("::: CAP NHAT KHO: " + query);
			ResultSet rs = db.get(query);
			/*if( rs != null )
			{*/
				while( rs.next() )
				{
					String khoId = rs.getString("Kho_FK");
					String spId = rs.getString("SanPham_fk");
					String solo = rs.getString("SoLo");
					String ngayhethan = rs.getString("NgayHetHan");
					String ngaynhapkho = rs.getString("ngaynhapkho");
					
					String loaidoituong = "";
					String doituongId = "";
					
					String mame = rs.getString("mame");
					String mathung = rs.getString("mathung");
					String bin_fk = rs.getString("bin_fk");
					
					String maphieu = rs.getString("maphieu");
					String phieudt = rs.getString("phieudt");
					String phieueo = rs.getString("phieueo");
					
					String marq = rs.getString("marq");
					String hamluong = rs.getString("hamluong");
					String hamam = rs.getString("hamam");

					double soluong = rs.getDouble("soluong");
					
					/*msg = util.Update_KhoTT(rs.getString("NgayDonHang"), "HO / xóa hóa đơn ", db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
							mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, 0, -1 * soluong, soluong, 
							loaichungtu, chungtuId, transactionId );
					if( msg.trim().length() > 0 )
					{
						//Revert
						util.Revert_KhoTT(db, loaichungtu, chungtuId, transactionId);
						
						db.getConnection().rollback();
						return msg;
					}*/
				}
				rs.close();
			//}

			query = "Update ERP_DONDATHANG set trangthai = '0' where pk_seq = ( select DonDatHang_FK from ERP_HOADON where PK_SEQ = '" + lsxId + "' ) ";
			if( db.updateReturnInt(query) < 1 )
			{
				msg = "Lỗi khi hủy: " + query;
				db.getConnection().rollback();
				return msg;
			}

			//XÓA TẤT CẢ CÁC HÓA ĐƠN CỦA ĐƠN HÀNG NÀY
			query = "update  ERP_HOADON set trangthai = '5' where DonDatHang_FK = ( select DonDatHang_FK from ERP_HOADON where pk_seq = '" + lsxId + "' ) ";
			if( db.updateReturnInt(query) < 1 )
			{
				msg = "Lỗi khi hủy: " + query;
				db.getConnection().rollback();
				return msg;
			}

			/*msg= util.Check_Kho_Tong_VS_KhoCT(nppId, db);
			if(msg.length()>0)
			{
				db.getConnection().rollback();
				return msg;
			}*/

			
			//Tạo chứng từ hủy hóa đơn tài chính
			msg=util.HuyUpdate_TaiKhoan_NgayChungTu_LoaiHD(db, lsxId, "Hóa đơn tài chính","HDHO", "NGAYXUATHD", "ERP_HOADON");
			if(msg.length()>0)
			{
				msg = "Lỗi hủy kế toán " + query;
				db.getConnection().rollback();
				return msg;
			}
			msg=util.HuyUpdate_TaiKhoan_NgayChungTu_LoaiHD(db, lsxId, "Hóa đơn nội bộ", "HDHO_NB", "NGAYXUATHD", "ERP_HOADON");
			if(msg.length()>0)
			{
				msg = "Lỗi hủy kế toán " + query;
				db.getConnection().rollback();
				return msg;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			try
			{
				db.getConnection().rollback();
			} catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			return "Exception: " + e.getMessage();
		}
		finally
		{
			db.shutDown();
		}
		return "";
	}
	
	private String Delete(String lsxId, String userId, String nppId, IErpHoadontaichinhList obj) 
	{
		dbutils db = new dbutils();
		String msg = "";

		Utility util = new Utility();
		/*msg= util.Check_Huy_NghiepVu_KhoaSo("ERP_HOADONNPP", lsxId, "ngayxuatHD", db);
		if(msg.length()>0)
		{
			db.shutDown();
			return msg;
		}*/

		try
		{	
			db.getConnection().setAutoCommit(false);
			String query = "";
			
			// Kiểm tra import =1 thì k cho hủy
			query = "select COUNT(*) as sodong from ERP_YCXUATKHO_DDH where hoadon_fk = '" + lsxId + "' ";
			System.out.println(query);

			ResultSet rsKT = db.get(query);
			int dacoXK = 0;

			if(rsKT!= null)
			{
				while(rsKT.next())
				{
					dacoXK =  rsKT.getInt("sodong");
				}
				rsKT.close();
			}

			if( dacoXK > 0 )
			{
				msg = "Hóa đơn đã có xuất kho. Bạn không thể xóa / hủy. ";
				db.getConnection().rollback();
				return msg;
			}

			//NHẢ LẠI TỒN FKHO
			//Tăng kho ngược lại trước khi xóa
			/*query = "  select b.NgayDonHang, b.Kho_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, SUM( a.SoLuong ) as soluong  " + 
					"  from ERP_DONDATHANG_SANPHAM_CHITIET a inner join ERP_DONDATHANG b on a.DonDatHang_FK = b.PK_SEQ " + 
					"  where b.PK_SEQ = ( select DonDatHang_FK from ERP_HOADON where PK_SEQ = '" + lsxId + "' ) " + 
					"  group by b.NgayDonHang, b.Kho_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho ";*/
			
			String loaichungtu = "Hóa đơn HO - xóa"; 
			String chungtuId = lsxId; 
			String transactionId = util.createTransactionId();
			
			query = "  select b.NgayDonHang, b.Kho_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, " + 
					" 		a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, a.phieudt, a.phieueo, SUM( a.SoLuongCHUAN ) as soluong  " + 
					"  from ERP_DONDATHANG_SANPHAM_CHITIET a inner join ERP_DONDATHANG b on a.DonDatHang_FK = b.PK_SEQ " + 
					"  where b.trangthai != 3 and b.PK_SEQ = ( select DonDatHang_FK from ERP_HOADON where PK_SEQ = '" + lsxId + "' ) " + 
					"  group by b.NgayDonHang, b.Kho_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, a.bin_fk, a.phieudt, a.phieueo ";
			
			System.out.println("::: CAP NHAT KHO: " + query);
			ResultSet rs = db.get(query);
			/*if( rs != null )
			{*/
				while( rs.next() )
				{
					String khoId = rs.getString("Kho_FK");
					String spId = rs.getString("SanPham_fk");
					String solo = rs.getString("SoLo");
					String ngayhethan = rs.getString("NgayHetHan");
					String ngaynhapkho = rs.getString("ngaynhapkho");
					
					String loaidoituong = "";
					String doituongId = "";
					
					String mame = rs.getString("mame");
					String mathung = rs.getString("mathung");
					String bin_fk = rs.getString("bin_fk");
					
					String maphieu = rs.getString("maphieu");
					String phieudt = rs.getString("phieudt");
					String phieueo = rs.getString("phieueo");
					
					String marq = rs.getString("marq");
					String hamluong = rs.getString("hamluong");
					String hamam = rs.getString("hamam");

					double soluong = rs.getDouble("soluong");
					// nha trang thai dh ve chua chot khong anh huong gi den kho
					/*msg = util.Update_KhoTT(rs.getString("NgayDonHang"), "HO / xóa hóa đơn ", db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
							mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, 0, -1 * soluong, soluong, 
							loaichungtu, chungtuId, transactionId	);
					if( msg.trim().length() > 0 )
					{
						//Revert
						util.Revert_KhoTT(db, loaichungtu, chungtuId, transactionId);
						
						db.getConnection().rollback();
						return msg;
					}*/
				}
				rs.close();
			//}

			query = "Update ERP_DONDATHANG set trangthai = '0' where pk_seq = ( select DonDatHang_FK from ERP_HOADON where PK_SEQ = '" + lsxId + "' ) ";
			if( db.updateReturnInt(query) < 1 )
			{
				msg = "Lỗi khi hủy: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			if(obj.getNOIBO().equals("1"))
			{
				query = "delete  ERP_DONDATHANG_CTKM_TRAKM where dondathangID = ( select DonDatHang_FK from ERP_HOADON where PK_SEQ = '" + lsxId + "' ) ";
				if( !db.update(query)  )
				{
					msg = "Lỗi khi hủy: " + query;
					db.getConnection().rollback();
					return msg;
				}
			}

			//XÓA TẤT CẢ CÁC HÓA ĐƠN CỦA ĐƠN HÀNG NÀY
			query = "update  ERP_HOADON set trangthai = '3' where DonDatHang_FK = ( select DonDatHang_FK from ERP_HOADON where pk_seq = '" + lsxId + "' ) ";
			if( db.updateReturnInt(query) < 1 )
			{
				msg = "Lỗi khi hủy: " + query;
				db.getConnection().rollback();
				return msg;
			}

			/*msg= util.Check_Kho_Tong_VS_KhoCT(nppId, db);
			if(msg.length()>0)
			{
				db.getConnection().rollback();
				return msg;
			}*/
			
			
			//Xóa phát sinh
//			util.HuyUpdate_TaiKhoan(db, lsxId, "Hóa đơn tài chính", "HDHO");
//			util.HuyUpdate_TaiKhoan(db, lsxId, "Hóa đơn nội bộ", "HDHO_NB");
			msg=util.HuyUpdate_TaiKhoan_NgayChungTu_LoaiHD(db, lsxId, "Hóa đơn tài chính","HDHO", "NGAYXUATHD", "ERP_HOADON");
			if(msg.length()>0)
			{
				msg = "Lỗi hủy kế toán " + query;
				db.getConnection().rollback();
				return msg;
			}
			msg=util.HuyUpdate_TaiKhoan_NgayChungTu_LoaiHD(db, lsxId, "Hóa đơn nội bộ", "HDHO_NB", "NGAYXUATHD", "ERP_HOADON");
			if(msg.length()>0)
			{
				msg = "Lỗi hủy kế toán " + query;
				db.getConnection().rollback();
				return msg;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			try
			{
				db.getConnection().rollback();
			} catch (SQLException e1)
			{
				e1.printStackTrace();
			}
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
	    
		IErpHoadontaichinhList obj = new ErpHoadontaichinhList();
		obj.setLoaidonhang(loaidonhang);
		
		String noibo = request.getParameter("NOIBO") == null ? "0" : request.getParameter("NOIBO");
	    obj.setNOIBO(noibo);
	    
		String km = request.getParameter("km");
	    if(km!=null)
	    {
	    	obj.setLoaikm(km);
	    }
		
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
    	obj.setUserId(userId);

	    if(action.equals("Tao moi"))
	    {
	    	IErpHoadontaichinh lsxBean = new ErpHoadontaichinh();
	    	lsxBean.setUserId(userId);
	    	lsxBean.createRs();
	    	session.setAttribute("dvkdId", "");
			session.setAttribute("kbhId", "");
			session.setAttribute("nppId", "");
    		
	    	session.setAttribute("lsxBean", lsxBean);
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Center/ErpHoaDonTaiChinhNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    { 
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
		    	this.getSearchQuery(request, obj);
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setUserId(userId);
		    	obj.init("");
		    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	
		    	String nextJSP = "/TraphacoHYERP/pages/Center/ErpHoaDonTaiChinh.jsp";
				response.sendRedirect(nextJSP);
		    }
	    	else
	    	{
		    	this.getSearchQuery(request, obj);
		    	obj.setUserId(userId);
		    	//String sumqr = getSumQuery(request, obj);
		    	//obj.getSumBySearch(sumqr);
		    	obj.init("");	
		    	GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);		
	    		String nextJSP = "/TraphacoHYERP/pages/Center/ErpHoaDonTaiChinh.jsp";
	    		response.sendRedirect(nextJSP);
	    	}
	    }
	}
	
	private void getSearchQuery(HttpServletRequest request, IErpHoadontaichinhList obj)
	{
		String sanphamId = request.getParameter("sanphamId");
		if(sanphamId == null)
			sanphamId = "";
		obj.setSanphamId(sanphamId);
		
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
//		String[] loai = khten.split("--");   	
    	obj.setKhTen(khten);
		
		String sohoadon = request.getParameter("sohoadon");
		if(sohoadon == null)
			sohoadon = "";
		obj.setSohoadon(sohoadon);
		System.out.println("sohoadon la "+sohoadon);
		
		String sodonhang = request.getParameter("sodonhang");
		if(sodonhang == null)
			sodonhang = "";
		obj.setSodonhang(sodonhang);
		

		String loaihoadon = request.getParameter("loaidonhang")==null?"0":request.getParameter("loaidonhang");
		obj.setLoaidonhang(loaihoadon);

	}
		
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private String getSumQuery(HttpServletRequest request, IErpHoadontaichinhList obj) 
	{
		String nppId = request.getParameter("nppId");
    	if ( nppId == null)
    		nppId = "";
    	obj.setnppId(nppId);
    	
    	String trangthai = request.getParameter("trangthai");
    	if (trangthai == null)
    		trangthai = "";    	
    	obj.setTrangthai(trangthai);
    	
    	String tungay = request.getParameter("tungay");
    	if (tungay == null)
    		tungay = "";    	
    	obj.setTungay(tungay);
    	
    	String denngay = request.getParameter("denngay");
    	if (denngay == null)
    		denngay = "";    	
    	obj.setDenngay(denngay);
    	
    	String sohoadon = request.getParameter("sohoadon");
		if(sohoadon == null)
			sohoadon = "";
		obj.setSohoadon(sohoadon);
		
		String sodonhang = request.getParameter("sodonhang");
		if(sodonhang == null)
			sodonhang = "";
		obj.setSodonhang(sodonhang);
    	
    	String khachhang = request.getParameter("khTen");
    	System.out.println("aaaaaaaaaaaa"+khachhang);
    	String[] loai = khachhang.split("--");   	
    	obj.setKhTen(khachhang);
    	    	
    	if ( nppId.length() == 0 && trangthai.length() == 0 && tungay.length() == 0 && denngay.length() == 0 && sodonhang.length() == 0 && khachhang.length()==0)
    		obj.setIsSearch(false);
    	else
    		obj.setIsSearch(true);
    	
    	String query =
    		" select " +
    		" sum(hd_npp.tongtienbvat) as doanhso,  sum(hd_npp.vat) as thue, sum(tongtienbvat + vat - chietkhau) as doanhthu "+
    		" from	ERP_HOADON hd_npp inner join ERP_HOADON_DDH hd_ddh on hd_npp.PK_SEQ = hd_ddh.HOADON_FK "+
    		" where	1=1 ";     	
    	if (nppId.length() > 0)
    	{
			query = query + " and hd_npp.NPP_FK = '" + nppId + "'";		
    	} 
    	    	
    	if (trangthai.length() > 0)
    	{    		
    		if(trangthai.equals("3")||trangthai.equals("5") )
				query += " and hd_npp.TrangThai in (3,5) ";
			else
				query += " and hd_npp.TrangThai = '" + trangthai + "'";
    		
    	}
    	else
    		query += " and hd_npp.TRANGTHAI not in (3,5) ";
    	
    	if (tungay.length() > 0)
    	{
			query = query + " and hd_npp.NGAYXUATHD >= '" + tungay + "'";			
    	}    	
    	if (denngay.length() > 0)
    	{
			query = query + " and hd_npp.NGAYXUATHD <= '" + denngay + "'";	
    	}
    	if (sodonhang.length() > 0)
    	{
    		query = query + " and hd_ddh.DDH_FK like '%" + sodonhang + "%'";	
    	}
    	
    	if (khachhang.length() > 0)
    	{
    		if(loai[0].equals("ETC")){
    			query = query + " and hd_npp.KHACHHANG_FK='"+loai[0]+"' ";
    		}
    		else{
    			query = query + " and hd_npp.NPP_DAT_FK='"+loai[0]+"' ";
    		}
    	}
    	
    	if(sohoadon.length()>0)
    	{
    		query= query+ " and hd_npp.SOHOADON like '%"+sohoadon+"%' ";
    	}
    	    	
    	System.out.println("\nQuery cua ban: " + query);
    	return query;
	}
	
	private void CreateStaticHeader(Workbook workbook, IErpHoadontaichinhList obj) 
	{
		Worksheets worksheets = workbook.getWorksheets();

	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	    Style style;
	   	    
	    //LẤY THÔNG TIN HO
	    dbutils db = new dbutils();
	    String ten = "";
	    String diachi = "";
	    String dienthoai = "";
	    String fax = "";
	    
	    String query = "select ten, diachi, dienthoai, fax from NHAPHANPHOI where pk_seq = '1'";
	    ResultSet rs = db.get(query);
	    if(rs != null)
	    {
	    	try 
	    	{
	    		if(rs.next())
	    		{
		    		ten = rs.getString("ten");
		    		diachi = rs.getString("diachi");
		    		dienthoai = rs.getString("dienthoai");
		    		fax = rs.getString("fax");
	    		}
	    		
				rs.close();
			} 
	    	catch (Exception e) { e.printStackTrace(); }
	    }
	    
	    Cell cell = cells.getCell("D1"); 
	    cell.setValue(ten.toUpperCase());	    
	    style = cell.getStyle();
		Font font2 = new Font();	
		font2.setName("Times New Roman");
		font2.setSize(11);
		font2.setBold(false);
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.CENTER);					
		cell.setStyle(style);
		
		font2 = new Font();	
		font2.setName("Times New Roman");
		font2.setSize(12);
		font2.setBold(false);
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.CENTER);					
		cell.setStyle(style);
		
		cell = cells.getCell("D2");
	    cell.setValue("Địa chỉ: " + diachi);
	    style = cell.getStyle();
	    style.setFont(font2);
		cell.setStyle(style);
		
		font2 = new Font();	
		font2.setName("Times New Roman");
		font2.setSize(12);
		font2.setBold(false);
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.CENTER);					
		cell.setStyle(style);
		
		cell = cells.getCell("D3");
	    cell.setValue("Tel: " + dienthoai + " - Fax: " + fax );
	    style = cell.getStyle();
	    style.setFont(font2);
		cell.setStyle(style);
		
		font2 = new Font();	
		font2.setName("Times New Roman");
		font2.setBold(false);
		font2.setSize(12);
	   
	    cell = cells.getCell("A8");
	    cell.setValue("Từ ngày: " + getFormatDate(obj.getTungay()) + " đến ngày: " + getFormatDate(obj.getDenngay()) );
	    style = cell.getStyle();
	    style.setFont(font2);
		cell.setStyle(style);
	    
	    worksheet.setName("BangKe");
	}
	
	private void CreateStaticData(Workbook workbook, IErpHoadontaichinhList obj, String query ) 
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();

		dbutils db = new dbutils();
		ResultSet hdRs = db.get(query);

		Cell cell_mau = cells.getCell("A9");
		Cell cell = null;

		Style style;
		Font font2 = new Font();
		font2.setName("Times New Roman");				
		font2.setSize(8);

		Font font3 = new Font();
		font3.setName("Times New Roman");				
		font3.setSize(8);
		font3.setBold(true);

		int i = 10;
		if(hdRs != null)
		{
			try 
			{
				int stt = 1;
				double totalTRUOCVAT = 0;
				double totalVAT = 0;

				while(hdRs.next())
				{
					totalTRUOCVAT += Math.round(hdRs.getDouble("DOANHTHU"));
					totalVAT += hdRs.getDouble("VAT");

					cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(stt); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
					cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(hdRs.getString("MA")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(hdRs.getString("TEN")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(hdRs.getString("DONVI")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);		
					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(hdRs.getDouble("SOLUONG")); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);


					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(hdRs.getDouble("DONGIA")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					style.setCustom("#,##0.0000");
					cell.setStyle(style);

					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(Math.round(hdRs.getDouble("DOANHTHU"))); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);

					cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(hdRs.getDouble("VAT")); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);

					cell = cells.getCell("I" + Integer.toString(i));	cell.setValue( hdRs.getDouble("AVAT")  ); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
					ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);


					i++;
					stt ++;
				}
				hdRs.close();

				/*query = "	select loai.ma as MaLoai,ck.diengiai,ck.thuevat, sum( ROUND( ck.ChietKhau , 0 ) ) as ChietKhau ,   "+
						"		sum( ROUND(  ck.ChietKhau * ( ck.thueVAT / 100.0 ) ,0 ) )   	as VAT,  "+
						"		sum( ROUND( ck.ChietKhau * ( 1 + ck.thueVAT / 100.0 ), 0 ) )   	as AVAT "+
						"	from hoadon_chietkhau ck left join loaick loai on loai.ma=ck.diengiai " +
						"   where isnull(ck.HienThi,0)=1 and hoadon_fk in  (select pk_seq from hoadon  a where   trangthai not in ( 1 , 3, 5 ) and isnull(loaihoadon, '0') = '0'   "+condition+" )   "+
						"	group by loai.ma,ck.diengiai,ck.thuevat  ";

				System.out.println("-----LAY CHIET KHAU: " + query);
				ResultSet rsCK = db.get(query);
				if(rsCK != null)
				{
					while(rsCK.next())
					{
						double ck_truocVAT = Math.round( -1 * rsCK.getDouble("chietkhau"));
						double ck_VAT = -1 * rsCK.getDouble("VAT");
						double ck_sauVAT =-1 * rsCK.getDouble("AVAT");

						totalTRUOCVAT += ck_truocVAT;
						totalVAT += ck_VAT;

						cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(stt); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
						cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(rsCK.getString("maloai")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(rsCK.getString("diengiai")); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);		
						cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(" "); 	style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
						cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(0); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
						cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(0); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
						cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(ck_truocVAT); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);

						cell = cells.getCell("H" + Integer.toString(i));	cell.setValue(ck_VAT); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);

						cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(ck_sauVAT); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);

						stt++;
						i++;
					}
					rsCK.close();
				}*/

				//THEM DONG TONG CONG
				cell = cells.getCell("A" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("B" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("C" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);		
				cell = cells.getCell("D" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
				cell = cells.getCell("E" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
				cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(""); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED);
				cell = cells.getCell("G" + Integer.toString(i));	cell.setValue(Math.round(totalTRUOCVAT)); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);

				cell = cells.getCell("H" + Integer.toString(i));	cell.setValue( Math.round( totalVAT)); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);

				cell = cells.getCell("I" + Integer.toString(i));	cell.setValue(Math.round( totalTRUOCVAT + totalVAT )); 			style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell, HorizontalAlignmentType.RIGHT);
				ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 3);

				i++;
				
				cell = cells.getCell("B" + Integer.toString(i)); 	cell.setValue(" "); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell);
				ReportAPI.mergeCells(worksheet, i, i, 1, 2);
				
				cell = cells.getCell("F" + Integer.toString(i));	cell.setValue(" "); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell);
				ReportAPI.mergeCells(worksheet, i, i, 5, 7);
				
				i++;
				ReportAPI.mergeCells(worksheet, i, i, 1, 2);
				cell = cells.getCell("B" + Integer.toString(i)); 	cell.setValue("Bên bán"); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell);
				
				ReportAPI.mergeCells(worksheet, i, i, 5, 7);
				cell = cells.getCell("F" + Integer.toString(i));	cell.setValue("Bên mua"); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell);
				
				i++;
				ReportAPI.mergeCells(worksheet, i, i, 1, 2);
				cell = cells.getCell("B" + Integer.toString(i));	cell.setValue("Ký tên, đóng dấu"); 		style = cell_mau.getStyle();  style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell);
				
				ReportAPI.mergeCells(worksheet, i, i, 5, 7);
				cell = cells.getCell("F" + Integer.toString(i));	cell.setValue("Ký tên, đóng dấu"); 		style = cell_mau.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle2(cell);
			}
			catch (Exception e)
			{ 
				e.printStackTrace(); 
			}
		}
	}
	
	private void setCellBorderStyle(Cell cell, short alignment) 
	{
		Style style = cell.getStyle();
		//style.setHAlignment(HorizontalAlignmentType.CENTRED);
		style.setHAlignment(alignment);
		/*style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);*/
		cell.setStyle(style);
	}
	
	private void setCellBorderStyle2(Cell cell) 
	{
		Style style = cell.getStyle();
		style.setHAlignment(HorizontalAlignmentType.CENTRED);
		style.setBorderLine(BorderType.TOP, 0);
		style.setBorderLine(BorderType.RIGHT, 0);
		style.setBorderLine(BorderType.BOTTOM, 0);
		style.setBorderLine(BorderType.LEFT, 0);
		cell.setStyle(style);
	}
	
	private String getFormatDate(String date) 
	{
		try
		{
			String[] arr = date.split("-");
	
	        return arr[2] + "/" + arr[1] + "/" + arr[0];	
		}
		catch(Exception ex)
		{
			return date;
		}
	}
	
	
}
