package geso.traphaco.center.servlets.dondathang;

import geso.traphaco.center.beans.dondathang.IErpDondathang;
import geso.traphaco.center.beans.dondathang.IErpDondathangList;
import geso.traphaco.center.beans.dondathang.imp.ErpDondathang;
import geso.traphaco.center.beans.dondathang.imp.ErpDondathangList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.google.gson.Gson;

public class ErpDondathangSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	public ErpDondathangSvl() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpDondathangList obj;
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
		
		String type = util.antiSQLInspection(request.getParameter("type")==null?"":request.getParameter("type"));
		
		if(type.equals("GetDonGia"))
		{
			NumberFormat formatter = new DecimalFormat("#,###,###");
			Gson gson = new Gson();
			
			String spMa = "";
			String dvdlId ="";
			String nppId = util.antiSQLInspection(request.getParameter("nppId"));
			String kbhId = util.antiSQLInspection(request.getParameter("kbhId"));
			String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
			
			String query = (String)request.getQueryString();
			spMa = new String(query.substring(query.indexOf("&spMa=") + 6, query.indexOf("&dvdlId=")).getBytes("UTF-8"), "UTF-8");
			spMa = URLDecoder.decode(spMa, "UTF-8").replace("+", " ");
			
			dvdlId = new String(query.substring(query.indexOf("&dvdlId=") + 8, query.indexOf("&nppId=")).getBytes("UTF-8"), "UTF-8");
			dvdlId = URLDecoder.decode(dvdlId, "UTF-8").replace("+", " ");
			
			dbutils db = new dbutils();
			
			query = " select a.DVDL_FK as dvCHUAN, ( select PK_SEQ from DONVIDOLUONG where DONVI = N'" + dvdlId + "' ) as dvNEW, " + 
					"	case when a.DVDL_FK =( select PK_SEQ from DONVIDOLUONG where DONVI = N'" + dvdlId + "' ) then 1  "+
					"	else ( select soluong1 / soluong2 from QUYCACH where SANPHAM_FK=a.PK_SEQ and DVDL1_FK = a.DVDL_FK and DVDL2_FK =  "+
					"		( select PK_SEQ from DONVIDOLUONG where DONVI = N'" + dvdlId + "' ) ) end as TyLe,  "+
					"	(select soluong1/soluong2 from QUYCACH where SANPHAM_FK=a.PK_SEQ and DVDL1_FK=a.DVDL_FK and DVDL2_FK=  "+
					"	( select PK_SEQ from DONVIDOLUONG where DONVI =  N'" + dvdlId + "' ) ) as QuyCach_THG ,a.TRONGLUONG,a.THETICH, " +  
					" 	  isnull( ( select GIAMUANPP * ( 1 - isnull( ( select chietkhau from BANGGIAMUANPP_NPP where banggiamuaNPP_FK = bg_sp.bgmuaNPP_FK and NPP_FK = '" + nppId + "' ), 0) / 100 ) " +
					"				from BGMUANPP_SANPHAM bg_sp " +
					"			    where SANPHAM_FK = a.pk_seq " +
					"					and BGMUANPP_FK in ( select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = '" + nppId + "' and bg.DVKD_FK = '" + dvkdId + "' and bg.KENH_FK = '" + kbhId + "' order by bg.TUNGAY desc ) ), 0) as giamua " + 
					" from SANPHAM a where a.MA = '" + spMa + "'  ";
			
			System.out.println("[Sql]: " + query);
			
			ResultSet rs = db.get(query);
			double TheTich =0;		
			double TrongLuong =0;
			double DonGia =0;		
			
			double QuyCach=0;
			double TyLe = 0;
			
			
			if(rs != null)
			{
				try 
				{
					if(rs.next())
					{
						TheTich=rs.getDouble("thetich");
						TrongLuong= rs.getDouble("trongluong");
						DonGia =rs.getDouble("giamua");
						QuyCach = rs.getDouble("QuyCach_THG");
						TyLe = rs.getDouble("TyLe");
						
						System.out.println("[TyLe]"+TyLe+"[QuyCach]"+QuyCach+"[DonGia]"+DonGia+"[TrongLuong]"+TrongLuong+"[TheTich]"+TheTich);
						
						SanPham sp = new SanPham();
						sp.setDongia( formatter.format(DonGia*TyLe));
						sp.setTrongluong(formatter.format(TrongLuong*TyLe));
						sp.setThetich(formatter.format(TheTich*TyLe));
						sp.setQuycach(formatter.format(QuyCach) );
						response.setContentType("application/json");
						response.setCharacterEncoding("UTF-8");
						response.getWriter().write(gson.toJson(sp));
						
					}
					rs.close();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
				finally
				{
					if(db!=null)db.shutDown();
				}
			}
		}
		else
		{
			String lsxId = util.getId(querystring);
			obj = new ErpDondathangList();
			
			String ETC = util.antiSQLInspection(request.getParameter("ETC") == null? "0" : request.getParameter("ETC"));
			obj.setETC(ETC);
			
			String loaidonhang = request.getParameter("loaidonhang");
			if(loaidonhang == null)
				loaidonhang = "0";
			obj.setLoaidonhang(loaidonhang);
			
			System.out.println("---LOAI DON HANG: " + loaidonhang);
			
			
			 String print = request.getParameter("print");
			    if(print!=null)
			    {

					response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition", "attachment; filename=DonDuTru.xlsm");
					OutputStream out1 = response.getOutputStream();
		
						try {
								ExportToExcel(out1,lsxId);
							return;
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			    }
			
			
			if (action.equals("delete") )
			{	
				String msg = this.DeleteChuyenKho(lsxId);
				obj.setMsg(msg);
			}
			else
			{
				if(action.equals("chot"))
				{
					String msg = this.Chot(lsxId, loaidonhang, userId);
					obj.setMsg(msg);
				}
			}
			
			obj.setUserId(userId);
			obj.init("");
			
			session.setAttribute("obj", obj);
			
			String nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHang.jsp";
			if(loaidonhang.equals("4"))
				nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHangKhac.jsp";
			/*else if(loaidonhang.equals("3")) //Bán nguyên vật liệu
				nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangNVL.jsp";
			else if(loaidonhang.equals("2")) //Đon hàng XNK
				nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangXNK.jsp";
			else if(loaidonhang.equals("1"))
				nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangKMUngHang.jsp";*/
			else if(loaidonhang.equals("5"))
				nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangNoiBo.jsp";
			
			response.sendRedirect(nextJSP);
		}
	}
	
	private String Chot(String lsxId, String loaidh, String userId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			//Check phải có kho chi tiết, đơn nội bộ ko check kho
			String query = "";
			
			if( loaidh.equals("5") )
			{
				query = " SELECT b.loaidonhang, isnull(b.phanloai, '') as phanloai " + 
						" FROM ERP_DONDATHANG b  WHERE pk_seq = '" + lsxId + "'  ";
				
				System.out.println("::: CHECK : " + query);
				ResultSet rs = db.get(query);
				String loaidonhang = loaidh;
				String phanloai = "";
				if( rs != null )
				{
					if( rs.next() )
					{
						loaidonhang = rs.getString("loaidonhang");
						phanloai = rs.getString("phanloai");
					}
					rs.close();
				}
				
				if( phanloai.trim().length() <= 0 && loaidonhang.equals("5")  )
				{
					msg = "Đơn hàng nội bộ bạn phải chọn phân loại ( OTC / ETC / KM )";
					db.getConnection().rollback();
					return msg;
				}
			}
			else if( !loaidh.equals("5") && !loaidh.equals("4"))
			{
				query = " SELECT b.loaidonhang, isnull(b.phanloai, '') as phanloai, COUNT( a.DonDatHang_FK ) as sodong " + 
					    " FROM ERP_DONDATHANG_SANPHAM_CHITIET a inner join ERP_DONDATHANG b on a.DonDatHang_FK = b.pk_seq " + 
					    " WHERE DonDatHang_FK = '" + lsxId + "'  and b.kho_fk in ( select pk_seq from ERP_KHOTT ) " + 
					    " group by b.loaidonhang, isnull(b.phanloai, '') ";
				
				System.out.println("::: CHECK : " + query);
				ResultSet rs = db.get(query);
				int soDong = 0;
				if( rs != null )
				{
					if( rs.next() )
					{
						soDong = rs.getInt("sodong");
					}
					rs.close();
				}
				
				if( soDong <= 0  )
				{
					msg = "Vui lòng kiểm tra lại thông tin kho và sản phẩm chi tiết bán";
					db.getConnection().rollback();
					return msg;
				}
			}
			
			query = "update ERP_Dondathang set trangthai = '1', ngaygiochot = '" + getDateTimeFull() + "' where pk_seq = '" + lsxId + "' and TrangThai!=1  ";
			if(!db.update(query))
			{
				msg = "Khong the chot: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			db.getConnection().commit();
		}
		catch (Exception e) 
		{
			db.update("rollback");
			return "Exception: " + e.getMessage();
		}
		finally
		{
			db.shutDown();
		}
		return "";
	}
	
	private String DeleteChuyenKho(String lsxId)
	{
		dbutils db = new dbutils();
		Utility util = new Utility();
		String msg = "";
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_Dondathang set trangthai = '3', ngaysua = '" + this.getDateTime() + "' where pk_seq = '" + lsxId + "'";
			if(!db.update(query))
			{
				msg = "1.Khong the xoa: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			String loaichungtu = "Đơn đặt hàng - xóa"; 
			String chungtuId = lsxId; 
			String transactionId = util.createTransactionId();
			
			//Tăng kho ngược lại trước khi xóa
			query = "  select b.NgayDonHang, b.Kho_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, " + 
					" 		a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, a.phieudt, a.phieueo, SUM( a.SoLuongCHUAN ) as soluong  " + 
					"  from ERP_DONDATHANG_SANPHAM_CHITIET a inner join ERP_DONDATHANG b on a.DonDatHang_FK = b.PK_SEQ " + 
					"  where b.PK_SEQ = '" + lsxId + "' " + 
					"  group by b.NgayDonHang, b.Kho_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, a.bin_fk, a.phieudt, a.phieueo ";
			
			System.out.println("::: CAP NHAT KHO: " + query);
			ResultSet rs = db.get(query);
			if( rs != null )
			{
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
					
					msg = util.Update_KhoTT(rs.getString("NgayDonHang"), "Xóa đơn hàng - Tăng kho ngược lại trước khi xóa ", db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
							mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, 0, -1 * soluong, soluong, 
							loaichungtu, chungtuId, transactionId	);
					if( msg.trim().length() > 0 )
					{
						db.getConnection().rollback();
						return msg;
					}
				}
				rs.close();
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
		System.out.println(":: ACTION: " + action);
		
		Utility util = new Utility();
		String ETC = util.antiSQLInspection(request.getParameter("ETC") == null? "0" : request.getParameter("ETC"));
		
		String loaidonhang = request.getParameter("loaidonhang");
		if(loaidonhang == null)
			loaidonhang = "0";
		
		IErpDondathangList obj = new ErpDondathangList();
		obj.setLoaidonhang(loaidonhang);
		obj.setETC(ETC);
		
		HttpSession session = request.getSession();
		String userId = util.antiSQLInspection(request.getParameter("userId")); 
		obj.setUserId(userId);
		
		 if (action.equals("excell"))
		    {
		    	response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=Dondoitac.xlsm");
				OutputStream out = response.getOutputStream();

					try {
						
							ExportToExcel(request,out,obj);
						
						return;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return;
		    }
		
		
		if(action.equals("Tao moi"))
		{
			IErpDondathang lsxBean = new ErpDondathang();
			lsxBean.setETC(ETC);
			
			lsxBean.setUserId(userId);
			lsxBean.setLoaidonhang(loaidonhang);
			
			lsxBean.createRs();
			session.setAttribute("dvkdId", "");
			session.setAttribute("khoId", "");
			session.setAttribute("kbhId", "");
			//session.setAttribute("nppId", "");
			
			session.setAttribute("lsxBean", lsxBean);
			
			System.out.println("::: LOAI DON HANG: " + loaidonhang );
			
			String nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHangNew.jsp";
			if(loaidonhang.equals("4"))
				nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHangKhacNew.jsp";
			else if(loaidonhang.equals("2"))
				nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangXNKNew.jsp";
			else if(loaidonhang.equals("1"))
				nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangKMUngHangNew.jsp";
			else if(loaidonhang.equals("3"))
				nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangNVLNew.jsp";
			else if(loaidonhang.equals("5"))
				nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangNoiBoNew.jsp";
			
			response.sendRedirect(nextJSP);
		}
		else if( action.equals("chotdonhang") )
		{
			String dhId = request.getParameter("dhId");
			String msg = this.Chot(dhId, loaidonhang, userId);
			
			String search = getSearchQuery(request, obj);
			obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
			
			obj.init(search);
			obj.setMsg(msg);
			obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
			session.setAttribute("obj", obj);
			
			String nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHang.jsp";
			response.sendRedirect(nextJSP);
		}
		else
		{
			if(action.equals("view") || action.equals("next") || action.equals("prev"))
			{
				obj.setUserId(userId);
				obj.setETC(ETC);
				
				String search = getSearchQuery(request, obj);
				obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
				GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
				
				obj.init(search);
				obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
				session.setAttribute("obj", obj);
				
				String nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHang.jsp";
				if(loaidonhang.equals("4"))
					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHangKhac.jsp";
				/*else if(loaidonhang.equals("2"))
					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangXNK.jsp";
				else if(loaidonhang.equals("1"))
					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangKMUngHang.jsp";
				else if(loaidonhang.equals("3"))
					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangNVL.jsp";*/
				else if(loaidonhang.equals("5"))
					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangNoiBo.jsp";
				
				response.sendRedirect(nextJSP);
			}
			else
			{
				String search = getSearchQuery(request, obj);
				
				obj.setUserId(userId);
				obj.setETC(ETC);
				GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
				
				obj.init(search);
				
				session.setAttribute("obj", obj);  	
				session.setAttribute("userId", userId);
				
				String nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHang.jsp";
				if(loaidonhang.equals("4"))
					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHangKhac.jsp";
				/*else if(loaidonhang.equals("2"))
					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangXNK.jsp";
				else if(loaidonhang.equals("1"))
					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangKMUngHang.jsp";
				else if(loaidonhang.equals("3"))
					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangNVL.jsp";*/
				else if(loaidonhang.equals("5"))
					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangNoiBo.jsp";
				
				response.sendRedirect(nextJSP);
			}
		}
	}
	
	private void TaoBaoCao(com.aspose.cells.Workbook workbook,IErpDondathangList obj,String query,int sheetNum )throws Exception
	{
		try
		{
			com.aspose.cells.Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(sheetNum);
			com.aspose.cells.Cells cells = worksheet.getCells();
		
			com.aspose.cells.Cell cell = cells.getCell("B3");
			
			dbutils db = new dbutils();

			Hashtable<String,String> hashDienGiai = new Hashtable<String, String>();
			
			//ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			//cell = cells.getCell(4, 0 );cell.setValue("Ngày báo cáo: "+ getDateTime());
			
			//cell = cells.getCell(5, 0 );cell.setValue("Người tạo: "+ obj.getuserTen());
			
			
			System.out.println("query export bc "+query);
			ResultSet	rs = db.get(query);

			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();

			System.out.println("so cot la "+socottrongSql);
				
			int countRow = 4;
			int column = -1;
	/*		for( int i =1 ; i <=socottrongSql ; i ++  )
			{
				
				cell = cells.getCell(countRow, column + i );cell.setValue(rsmd.getColumnName(i));
				ReportAPI.setCellBackground(cell, new Color(219,229,241), BorderLineType.THIN, true, 0);
			

			}*/
			countRow ++;
				rs = db.get(query);
				int j=1;
			while(rs.next())
			{
				for(int i =1;i <=socottrongSql ; i ++)
				{
					Color c = Color.WHITE;
					cell = cells.getCell(countRow,column + i);
					System.out.println("column nama la "+rsmd.getColumnName(i));
					if(rsmd.getColumnName(i).equals("stt"))
					{
						cell.setValue(j);
						ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, true, 41);
					}
					else {
						if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i) == Types.INTEGER || rsmd.getColumnType(i) == Types.DECIMAL )
						{
							cell.setValue(rs.getDouble(i));
							ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, true, 41);
						}
						else
						{
							cell.setValue(rs.getString(i));
							ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, true, 0);
						}
					}
					
				}
				j++;
				++countRow;
			}
			
			if(rs!=null)rs.close();
			if(db!=null){
				db.shutDown();
			}


		}catch(Exception ex){

			ex.printStackTrace();
			throw new Exception("Qua trinh dien du lieu vao file Excel va tao PivotTable bi loi.!!!");
		}
	}
	
	private void ExportToExcel(HttpServletRequest request,OutputStream out,IErpDondathangList obj )throws Exception
	{
		try
		{ 		
			String strfstream = getServletContext().getInitParameter("path") + "\\Dondoitac.xlsm";
			
			/*com.aspose.cells.Workbook workbook = new com.aspose.cells.Workbook();
			workbook.setFileFormatType(FileFormatType.EXCEL2003);
			*/
			
			FileInputStream fstream = new FileInputStream(strfstream);
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			String tungay = request.getParameter("tungay");
			if(tungay == null)
				tungay = "";
			obj.setTungay(tungay);
			
			String denngay = request.getParameter("denngay");
			if(denngay == null)
				denngay = "";
			obj.setDenngay(denngay);
			
			
			String nppId = request.getParameter("nppId");
			if(nppId == null)
				nppId = "";
			obj.setNppTen(nppId);
		
			String condition ="";
			

			if(tungay.trim().length()>0)
			{
				condition+=" and a.NgayDonHang >='"+tungay+"' ";
			}
			if(denngay.trim().length()>0)
			{
				condition+=" and a.NgayDonHang >='"+tungay+"' ";
			}
			System.out.println("nhaphnan phoi id "+nppId);
			if(nppId.trim().length()==0)
			{
				return;
			}
			
			
			String query=" select '' stt,c.MA,c.TEN,d.DIENGIAI,sum(b.soluong) as soluong,'' ghichu from ERP_DONDATHANG a inner join ERP_DONDATHANG_SANPHAM b \n"+
						 "	on a.PK_SEQ=b.dondathang_fk \n"+
						 "	inner join SANPHAM c on c.PK_SEQ=b.sanpham_fk \n"+
						 "	inner join DONVIDOLUONG d on d.PK_SEQ=b.dvdl_fk \n"+
						 "	where a.trangthai=0 and  a.npp_fk= \n"+nppId+ condition +
						 "	group by c.MA,c.TEN,d.DIENGIAI\n";
					
			
		
			
			System.out.println("query chi tiet la "+query);
			TaoBaoCao(workbook, obj, query, 0);
			workbook.save(out);			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}

		
		
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpDondathangList obj)
	{
		Utility util = new Utility();
		
		String query = "select a.ngaygiochot, a.PK_SEQ, a.trangthai, a.ngaydonhang,TT.TEN as DIABAN ,c.ten as nppTEN, b.ten as khoTEN, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, isnull(a.NOTE, '') as NOTE,   " +
						" case a.KBH_FK when 100052 then N'ETC' when 100025 then N'OTC' end as KenhBanHang, isnull(a.iskm,0) as iskm"+
						" from ERP_Dondathang a inner join ERP_KHOTT b on a.kho_fk = b.pk_seq inner join NHAPHANPHOI c on a.NPP_FK = c.pk_seq  " +
						" inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
						" inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ "+
						" inner join TINHTHANh TT on c.TINHTHANH_FK = tt.PK_SEQ "+
						" where a.pk_seq > 0 and a.NPP_DACHOT = '1'    ";
		
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
		
		String ctId = request.getParameter("chungtu");
		if(ctId == null)
			ctId = "";
		obj.setctId(ctId);
		
		String nppId = request.getParameter("nppId");
		if(nppId == null)
			nppId = "";
		obj.setNppTen(nppId);
		
		String iskm = util.antiSQLInspection(request.getParameter("iskm")==null?"0":request.getParameter("iskm"));
		obj.setIsKm(iskm);
		
		if(iskm.length() > 0)
			query += " and a.iskm = '" + iskm + "' ";
		
		if(tungay.length() > 0)
			query += " and a.ngaydonhang >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngaydonhang <= '" + denngay + "'";
		
		if(trangthai.length() > 0)
			query += " and a.TrangThai = '" + trangthai + "'";
		
		if(ctId.length() > 0)
			query += " and a.PK_SEQ like '%" + ctId + "%'";
		
		if(nppId.length() > 0){
			query += " and a.NPP_FK= '" + nppId + "'";
		}
		
		System.out.print(query);
		return query;
	}
	
	
	private void ExportToExcel(OutputStream out,String id )throws Exception
	{
		try
		{ 		
			String strfstream = getServletContext().getInitParameter("path") + "\\DonDuTru.xlsm";
			
			/*com.aspose.cells.Workbook workbook = new com.aspose.cells.Workbook();
			workbook.setFileFormatType(FileFormatType.EXCEL2003);
			*/
			
			FileInputStream fstream = new FileInputStream(strfstream);
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

			String query=" select '' STT ,c.MA ,c.TEN,d.DONVI,b.soluong,a.GHICHU from ERP_DONDATHANG a inner join ERP_DONDATHANG_SANPHAM b \n"+
						 "	on a.PK_SEQ=b.dondathang_fk inner join SANPHAM c on c.PK_SEQ=b.sanpham_fk \n"+
						 "	inner join DONVIDOLUONG d on d.PK_SEQ=b.dvdl_fk \n"+
						 "	where a.PK_SEQ="+id;
		
			
			System.out.println("query chi tiet la "+query);
			TaoBaoCao(workbook, id, query, 0);
			workbook.save(out);			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}

	}
	
	private void TaoBaoCao(com.aspose.cells.Workbook workbook,String id,String query,int sheetNum )throws Exception
	{
		try
		{
			com.aspose.cells.Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(sheetNum);
			com.aspose.cells.Cells cells = worksheet.getCells();
		
			com.aspose.cells.Cell cell = cells.getCell("B3");
			
			dbutils db = new dbutils();

			String sql=" select SUBSTRING (a.NgayDonHang,9,2) as ngay,SUBSTRING (a.NgayDonHang,6,2) as thang,SUBSTRING (a.NgayDonHang,0,5) as nam,b.MaFAST,b.TEN from ERP_DONDATHANG a  "+
					   "	inner join nhaphanphoi b on a.npp_fk=b.PK_SEQ where a.PK_SEQ= "+id;
	
			ResultSet rssql=db.get(sql);
			rssql.next();
			
		
			cell = cells.getCell(2,0);	
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);
			System.out.println("Ngày "+rssql.getString("ngay")+" Tháng "+rssql.getString("thang")+" Năm "+rssql.getString("nam"));
			cell.setValue("Ngày "+rssql.getString("ngay")+" Tháng "+rssql.getString("thang")+" Năm "+rssql.getString("nam"));
			
			cell = cells.getCell(3,0);
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);
			cell.setValue("Đơn vị dự trù :"+rssql.getString("Mafast")+"-"+rssql.getString("ten"));
			rssql.close();
			
			ResultSet	rs = db.get(query);

			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();

			
				
			int countRow = 5;
			int column = -1;
	
				rs = db.get(query);
				int j=1;
			while(rs.next())
			{
				for(int i =1;i <=socottrongSql ; i ++)
				{
					Color c = Color.WHITE;
					cell = cells.getCell(countRow,column + i);
					
					if(rsmd.getColumnName(i).equals("STT"))
					{
						cell.setValue(j);
						ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, true, 41);
					}
					else
					{
						if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i) == Types.INTEGER || rsmd.getColumnType(i) == Types.DECIMAL )
						{
							cell.setValue(rs.getDouble(i));
							ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, true, 41);
						}
						else
						{
							cell.setValue(rs.getString(i));
							ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, true, 0);
						}
					}
					
				}
				j++;
				++countRow;
			}
			
			if(rs!=null)rs.close();
			if(db!=null){
				db.shutDown();
			}


		}catch(Exception ex){

			ex.printStackTrace();
			throw new Exception("Qua trinh dien du lieu vao file Excel va tao PivotTable bi loi.!!!");
		}
	}
	
	
	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}
	
	public String getDateTimeFull()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);	
	}
	
	class SanPham
	{
		String dongia;
		String quycach ;
		String trongluong;
		String thetich;
		
		public String getTrongluong()
		{
			return trongluong;
		}
		public void setTrongluong(String trongluong)
		{
			this.trongluong = trongluong;
		}
		public String getThetich()
		{
			return thetich;
		}
		public void setThetich(String thetich)
		{
			this.thetich = thetich;
		}
		public String getQuycach()
		{
			return quycach;
		}
		public void setQuycach(String quycach)
		{
			this.quycach = quycach;
		}
		public String getDongia() 
		{
			return dongia;
		}
		public void setDongia(String dongia) 
		{
			this.dongia = dongia;
		}
		
		
	}
	
}
