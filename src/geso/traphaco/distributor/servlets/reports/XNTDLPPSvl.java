package geso.traphaco.distributor.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Types;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.HorizontalAlignmentType;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

import java.util.*;

public class XNTDLPPSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public XNTDLPPSvl()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		Utility util = new Utility();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		
		obj.setuserId(userId);
		obj.setdiscount("1");
		obj.setvat("0");
		obj.setdvdlId("");
		obj.settype("1");
		
		String isdlpp = request.getParameter("isdlpp");
		if( isdlpp == null )
			isdlpp = "1";
		obj.setIsDlpp(isdlpp);
		
		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
	    obj.setChiTietXNT_Lo("1");
		obj.init_dlpp_ctv();
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Distributor/BCXNT_DLPP_CTV.jsp";
		response.sendRedirect(nextJSP);
	}
	
	private String getPiVotName()
	{
		String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		String name = sdf.format(cal.getTime());
		name = name.replaceAll("-", "");
		name = name.replaceAll(" ", "_");
		name = name.replaceAll(":", "");
		return "_" + name;
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		
		Utility util = new Utility();
		if (userId == null)
			userId = "";
		obj.setuserId(userId);
		geso.traphaco.distributor.util.Utility Ult = new geso.traphaco.distributor.util.Utility();
		
		String nppId = request.getParameter("nppId");
		nppId = Ult.getIdNhapp(userId);
		obj.setnppId(nppId);
		
		obj.setuserTen(userTen);
		
		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
		
		String tungay = request.getParameter("Sdays"); // <!---
		if (tungay == null)
			tungay = "";
		obj.settungay(tungay);
		
		String denngay = request.getParameter("Edays");// <!---
		if (denngay == null)
			denngay = "";
		obj.setdenngay(denngay);
		
		String isdlpp = request.getParameter("isdlpp");// <!---
		if (isdlpp == null)
			isdlpp = "";
		obj.setIsDlpp(isdlpp);
		
		String[] dlppId = request.getParameterValues("nppId"); 
		String str = "";
		if (dlppId != null)
		{
			for(int i = 0; i < dlppId.length; i++)
				str += dlppId[i] + ",";
			if(str.length() > 0)
				str = str.substring(0, str.length() - 1);
		}
		obj.setDlppIds(str);
		
		obj.setChiTietXNT_Lo(util.antiSQLInspection(request.getParameter("layctnxsolo")) != null ? util.antiSQLInspection(request.getParameter("layctnxsolo")) : "");
		obj.setMonth( util.antiSQLInspection(request.getParameter("Sdays")) != null ? util.antiSQLInspection(request.getParameter("Sdays")) : "" );
		obj.setYear( util.antiSQLInspection(request.getParameter("Edays")) != null ? util.antiSQLInspection(request.getParameter("Edays")) : "" );
		obj.setkhuvucId( util.antiSQLInspection(request.getParameter("khuvucId")) != null ? util.antiSQLInspection(request.getParameter("khuvucId")) : "" );
		obj.setTinhthanhid( util.antiSQLInspection(request.getParameter("tinhthanhId")) != null ? util.antiSQLInspection(request.getParameter("tinhthanhId")) : "" );
		obj.setkhTen( util.antiSQLInspection(request.getParameter("khachang")) != null ? util.antiSQLInspection(request.getParameter("khachang")) : "" );
		obj.setSpId( util.antiSQLInspection(request.getParameter("tenSP")) != null ? util.antiSQLInspection(request.getParameter("tenSP")) : "" );
		
		if (!tungay.equals("") && !denngay.equals(""))
		{
			String action = request.getParameter("action");
			
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			if (action.equals("tao"))
			{
				if(obj.getIsDlpp().equals("1"))
				{
					response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition", "Attachment; filename=BaoCaoXuatNhapTon(DLPP)" + this.getPiVotName() + ".xlsm");
					
					OutputStream out = response.getOutputStream();
					try
					{
						CreatePivotTable(out, response, request, obj); 
					} 
					catch (Exception ex)
					{
						obj.init_dlpp_ctv();
						obj.setMsg(ex.getMessage());

						session.setAttribute("obj", obj);
						session.setAttribute("userId", userId);
						String nextJSP = "/TraphacoHYERP/pages/Distributor/BCXNT_DLPP_CTV.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				else if( obj.getIsDlpp().equals("0") ) //BÁO CÁO XNT CỘNG TÁC VIÊN
				{
					OutputStream out = response.getOutputStream();
					
					try
				    {
				        response.setContentType("application/xlsm");
						response.setHeader("Content-Disposition", "attachment; filename=BaoCaoXNTCongTacVien.xlsm");
				        
				        Workbook workbook = new Workbook();
				    	
						FileInputStream fstream = null;
						String chuoi = getServletContext().getInitParameter("path") + "\\BCXuatNhapTonCTV.xlsm";
						
						fstream = new FileInputStream(chuoi);		
						workbook.open(fstream);
						workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
				        
					    String query = getSearchQueryCTV(request, obj);
					    CreateStaticDataCTV(workbook, query, obj);
					
					    //Saving the Excel file
					    workbook.save(out);
					    fstream.close();
				    }
				    catch (Exception ex){ ex.printStackTrace(); }
				}
				else //BÁO CÁO XNT CỘNG TÁC VIÊN ( MAU KIEM SOAT NOI BO )
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
						String chuoi = getServletContext().getInitParameter("path") + "\\BCDonHangCTV.xlsm";
						
						fstream = new FileInputStream(chuoi);		
						workbook.open(fstream);
						workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
				        
					    String query = getSearchQueryCTV_KSNB(request, obj);
					    CreateStaticDataCTV_KSNB(workbook, query, obj);
					
					    //Saving the Excel file
					    workbook.save(out);
					    fstream.close();
				    }
				    catch (Exception ex){ ex.printStackTrace(); }
				}
			}
			else
			{
				obj.init_dlpp_ctv();
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
				
				String nextJSP = "/TraphacoHYERP/pages/Distributor/BCXNT_DLPP_CTV.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}
	
	private void CreatePivotTable(OutputStream out, HttpServletResponse response, HttpServletRequest request, IStockintransit obj) throws Exception
	{
		try
		{
			String strfstream = getServletContext().getInitParameter("path") + "\\BaoCaoXuatNhapTon(DLPP).xlsm";
			FileInputStream fstream = new FileInputStream(strfstream);
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

	  		if(obj.getIsDlpp().equals("1"))
	  		{
	  			//báo cáo đại lý phân phối
	  			this.TaoBaoCao(workbook, obj);
	  		}
	  		else
	  		{
	  			//báo cáo cộng tác viên
	  			this.TaoBaoCaoCTV(workbook, obj);
	  		}
			
			workbook.save(out);
			fstream.close();
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}
	
	private void TaoBaoCao(Workbook workbook, IStockintransit obj) 
	{
		dbutils db = new dbutils();
		try
		{
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");
			Cells cells = worksheet.getCells();
			   
			for(int i=0;i<30;i++){
				cells.setColumnWidth(i, 10.0f);   
			}
			cells.setColumnWidth(3, 18.0f);
			cells.setColumnWidth(5, 18.0f);
		   
			cells.setRowHeight(0, 50.0f);
			Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 14, "XUẤT NHẬP TỒN");
		   
			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 10, "Từ ngày : " + obj.gettungay() + "   Đến ngày: " + obj.getdenngay());
			cell = cells.getCell("A4");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày tạo: " + obj.getDateTime());
			
			cell = cells.getCell("B4");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Tạo bởi : " + obj.getuserTen());
			
			cell = cells.getCell("P1");
			Style style1=cell.getStyle();
			
			cell = cells.getCell("O1");
			Style style2=cell.getStyle();
			
			
			ResultSet rs;
	
			rs = this.Laydulieu(db, obj);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();
			
			int countRow = 5;

			for( int i = 1 ; i <=socottrongSql ; i ++  )
			{
				cell = cells.getCell(countRow,i-1 );
				cell.setStyle(style2);
				ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, true, 9, rsmd.getColumnName(i));
			}
			countRow ++;
	   
			while(rs.next())
			{
				for(int i = 1; i <= socottrongSql; i ++)
				{
					cell = cells.getCell(countRow,i-1);
					if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i)==Types.NUMERIC || rsmd.getColumnType(i)==Types.INTEGER )
					{
						cell.setStyle(style1);
						ReportAPI.getCellStyle_double(cell, "Arial", Color.BLACK, false, 9, rs.getDouble(i));
					}
					else
					{
						cell.setStyle(style2);
						ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString(i));
					}
				}
				++countRow;
			}
	   
			if(rs!=null)
				rs.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			db.shutDown();
		}
	}
	
	private ResultSet Laydulieu(dbutils db, IStockintransit obj)
	{
		Utility util = new Utility();
		ResultSet rs;
		String[] param = new String[4];
		
		param[0] = obj.getkhoId().equals("") ? null : obj.getkhoId();
		param[1] = obj.gettungay().equals("") ? null : obj.gettungay();
		param[2] = obj.getdenngay().equals("") ? null : obj.getdenngay();
		param[3] = obj.getnppId().equals("") ? null : obj.getnppId();
		
		String query = "delete kho_tmp";
		db.update(query);
		query = "delete npp_tmp ";
		db.update(query);
		if(obj.getkhoId().length() > 0)
			query = "insert into kho_tmp(kho_fk) select pk_seq from kho where pk_seq in (" + obj.getkhoId() + ")";
		else
			query = "insert into kho_tmp(kho_fk) select pk_seq from kho where pk_seq in (select pk_seq from kho where trangthai = 1)";
		db.update(query);
		if(obj.getDlppIds().length() > 0)
			query = "insert into npp_tmp(npp_fk) select pk_seq from nhaphanphoi where pk_seq in (" + obj.getDlppIds() + ")";
		else
			query = "insert into npp_tmp(npp_fk) select pk_seq from nhaphanphoi where loainpp = 1 and trangthai = 1 and pk_seq in "+util.quyen_npp(obj.getuserId());
		db.update(query);
		
		if(obj.getChiTietXNT_Lo().equals("1"))
			rs = db.getRsByPro("REPORT_XUATNHAPTON_DLPP_CHITIET", param);
		else
			rs = db.getRsByPro("REPORT_XUATNHAPTON_DLPP", param);
		
		return rs;
	}

	
	/************* XNT CONG TAC VIEN *****************************************************************************/
	
	private void TaoBaoCaoCTV(Workbook workbook, IStockintransit obj) 
	{
		dbutils db = new dbutils();
		try
		{
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");
			Cells cells = worksheet.getCells();
			   
			for(int i=0;i<30;i++){
				cells.setColumnWidth(i, 10.0f);   
			}
			cells.setColumnWidth(3, 18.0f);
			cells.setColumnWidth(5, 18.0f);
		   
			cells.setRowHeight(0, 50.0f);
			Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 14, "XUẤT NHẬP TỒN");
		   
			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 10, "Từ ngày : " + obj.gettungay() + "   Đến ngày: " + obj.getdenngay());
			cell = cells.getCell("A4");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày tạo: " + obj.getDateTime());
			
			cell = cells.getCell("B4");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Tạo bởi : " + obj.getuserTen());
			
			cell = cells.getCell("P1");
			Style style1=cell.getStyle();
			
			cell = cells.getCell("O1");
			Style style2=cell.getStyle();
			
			
			ResultSet rs;
	
			String query =  "\nselect kh.maFAST as [Mã KH], kh.ten as [Tên KH], ctv.MA as [Mã CTV], ctv.TEN as [Tên CTV], sp.MA_FAST as [Mã Sản Phẩm], sp.TEN as [Tên Sản Phẩm], DAUKY.DONVI as [Đơn Vị],  "+
							 "\n	DAUKY.soluong as [Tồn Đầu], ISNULL(xuat.soluong, 0) as [Xuất], DAUKY.soluong -  ISNULL(xuat.soluong, 0) as [Tồn Cuối] "+
							 "\nfrom "+
							 "\n( "+
							 "\n	select KHACHHANG_FK, CTV_FK, SANPHAM_FK, DONVI, SUM(soluong) as soluong   "+
							 "\n	from  "+
							 "\n	(  "+
							 "\n			select d.KHACHHANG_FK, e.ctv_fk, a.sanpham_fk, cast( SUM( a.SoLuong_Chuan ) as float ) as soluong, c.DONVI, round( a.DonGia_Chuan * ( 1 + a.VAT / 100 ), 0 )  as dongia      "+
							 "\n			from ERP_HOADONNPP_SP a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ  		inner join DONVIDOLUONG c on b.DVDL_FK = c.PK_SEQ  		   "+
							 "\n				inner join ERP_HOADONNPP d on a.HOADON_FK = d.PK_SEQ   "+
							 "\n				inner join CONGTACVIEN_KHACHHANG e on d.KHACHHANG_FK = e.KH_FK "+
							 "\n			where d.TRANGTHAI not in ( 0, 3, 5 )  		  "+
							 "\n				and SUBSTRING( NGAYXUATHD , 0, 8 ) = SUBSTRING( convert( varchar(10), DATEADD(mm, -1, '" + obj.gettungay() + "'), 120 ), 0, 8 ) 		   "+
							 "\n			group by d.KHACHHANG_FK, e.ctv_fk, a.sanpham_fk, c.DONVI, a.DonGia_Chuan, a.VAT    "+
							 "\n		union ALL  "+
							 "\n			select d.KHACHHANG_FK, e.ctv_fk, a.sanpham_fk, cast( SUM( a.SoLuong_Chuan ) as float ) as soluong, c.DONVI, round( a.DonGia_Chuan * ( 1 + a.VAT / 100 ), 0 )  as dongia      "+
							 "\n			from ERP_HOADONNPP_SP a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ  inner join DONVIDOLUONG c on b.DVDL_FK = c.PK_SEQ  		   "+
							 "\n				inner join ERP_HOADONNPP d on a.HOADON_FK = d.PK_SEQ   "+
							 "\n				inner join CONGTACVIEN_KHACHHANG e on d.KHACHHANG_FK = e.KH_FK "+
							 "\n				inner join KHACHHANG f on d.KHACHHANG_FK = f.benhvien_fk "+
							 "\n			where d.TRANGTHAI not in ( 0, 3, 5 )  		  "+
							 "\n				and SUBSTRING( NGAYXUATHD , 0, 8 ) = SUBSTRING( convert( varchar(10), DATEADD(mm, -1, '" + obj.gettungay() + "'), 120 ), 0, 8 ) 		   "+
							 "\n			group by d.KHACHHANG_FK, e.ctv_fk, a.sanpham_fk, c.DONVI, a.DonGia_Chuan, a.VAT   "+
							 "\n	)  "+
							 "\n	DT group by KHACHHANG_FK, CTV_FK, SANPHAM_FK, DONVI  "+
							 "\n) "+
							 "\nDAUKY left join "+
							 "\n( "+
							 "\n	select a.khachhang_fk, c.PK_SEQ as CTV_FK, d.PK_SEQ as sanpham_fk, b.DONVI, sum(b.SOLUONG) as soluong "+
							 "\n	from DONHANGCTV a inner join DONHANGCTV_SP b on a.PK_SEQ = b.DONHANGCTV_FK "+
							 "\n			inner join CONGTACVIEN c on b.CTV_FK = c.PK_SEQ "+
							 "\n			inner join SANPHAM d on b.SANPHAM_FK = d.PK_SEQ "+
							 "\n	where a.TRANGTHAI = '2' and a.NPP_FK = '" + obj.getnppId() + "'  "+
							 "\n				and SUBSTRING( NGAYNHAP , 0, 8 ) = SUBSTRING( '" + obj.gettungay() + "' , 0, 8 ) "+
							 "\n	group by a.khachhang_fk, c.PK_SEQ, d.PK_SEQ, b.DONVI "+
							 "\n) "+
							 "\nXUAT on DAUKY.CTV_FK = XUAT.CTV_FK and DAUKY.KHACHHANG_FK = XUAT.KHACHHANG_FK and DAUKY.SANPHAM_FK = XUAT.sanpham_fk  "+
							 "\ninner join CONGTACVIEN ctv on DAUKY.CTV_FK = ctv.PK_SEQ   "+
							 "\ninner join KHACHHANG kh on DAUKY.KHACHHANG_FK = kh.PK_SEQ   "+
							 "\ninner join SANPHAM sp on DAUKY.SANPHAM_FK = sp.PK_SEQ ";
			
			System.out.println("::: XUAT NHAP TON CTV: " + query );
			rs = db.get(query);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();
			
			int countRow = 5;

			for( int i =1 ; i <=socottrongSql ; i ++  )
			{
				cell = cells.getCell(countRow,i-1 );
				cell.setStyle(style2);
				ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, true, 9, rsmd.getColumnName(i));
			}
			countRow ++;
	   
			while(rs.next())
			{
				for(int i = 1; i <= socottrongSql; i ++)
				{
					cell = cells.getCell(countRow,i-1);
					if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i)==Types.NUMERIC || rsmd.getColumnType(i)==Types.INTEGER )
					{
						cell.setStyle(style1);
						ReportAPI.getCellStyle_double(cell, "Arial", Color.BLACK, false, 9, rs.getDouble(i));
					}
					else
					{
						cell.setStyle(style2);
						ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString(i));
					}
				}
				++countRow;
			}
	   
			if(rs!=null)
				rs.close();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			db.shutDown();
		}
	}

	private String getSearchQueryCTV(HttpServletRequest request, IStockintransit obj) 
	{
		String tungay = "";
		String denngay = "";
		
		if( obj.getMonth().trim().length() < 2 )
		{
			tungay = obj.getYear() + "-" + "0" + obj.getMonth() + "-01";
			denngay = obj.getYear() + "-" + "0" + obj.getMonth() + "-31";
		}
		else
		{
			tungay = obj.getYear() + "-" + obj.getMonth() + "-01";
			denngay = obj.getYear() + "-" + obj.getMonth() + "-31";
		}
		
		String conditon = "";
		if( obj.getkhuvucId().trim().length() > 0 )
			conditon += " and bc.khachhang_fk in ( select pk_seq from KHACHHANG where npp_fk = '" + obj.getnppId() + "' and diaban_fk in ( select pk_seq from DIABAN where khuvuc_fk = '" + obj.getkhuvucId() + "' ) ) ";
		if( obj.getTinhthanhid().trim().length() > 0 )
			conditon += " and bc.khachhang_fk in ( select pk_seq from KHACHHANG where npp_fk = '" + obj.getnppId() + "' and tinhthanh_fk = '" + obj.getTinhthanhid() + "' ) ";
		if( obj.getkhTen().trim().length() > 0 )
			conditon += " and dbo.ftBoDau( makhCAP2 + ' ' + tenkhCAP2 ) like N'%" + obj.getkhTen() + "%' ";
		if( obj.getsanphamId().trim().length() > 0 )
			conditon += " and dbo.ftBoDau( maSP + ' ' + tenSP ) like N'%" + obj.getsanphamId() + "%' ";
		
		String query = "select makhCAP1, tenkhCAP1, makhCAP2, tenkhCAP2, maSP, tenSP, donvi, dongia, tondau, nhaptrongky, nhaptrahang, xuattrongky " +
					   "	from ufn_xnt_ctv ( '" + tungay + "', '" + denngay + "', " + obj.getnppId() + " ) bc where 1 = 1 ";
					   
		//PHAN QUYEN
		Utility util = new Utility();
		query += util.getPhanQuyen_TheoNhanVien("KHACHHANG", "bc", "khachhang_fk", obj.getLoainhanvien(), obj.getDoituongId() );
		
		query += conditon;
		query += "	order by tenkhCAP1 asc, tenkhCAP2 asc, tenSP asc  ";
		
		System.out.print(":: BAO CAO XNT CTV: " + query);
		return query;

	}

	private void CreateStaticDataCTV(Workbook workbook, String query, IStockintransit obj) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    Cell cell = null;
	    Style style;
		Font font2 = new Font();
		//font2.setName("Calibri");				
		font2.setSize(11);
	    
	    cell = cells.getCell("A2"); cell.setValue( "Từ ngày: " + obj.gettungay() ); 
	    style = cell.getStyle(); style.setFont(font2); cell.setStyle(style);
	    
	    cell = cells.getCell("A3");	cell.setValue( "Đến ngày: " + obj.getdenngay() ); 
	    style = cell.getStyle(); style.setFont(font2); cell.setStyle(style);
	    
		dbutils db = new dbutils();
		
		ResultSet rs = db.get(query);

		int i = 7;
		if(rs != null)
		{
			try 
			{
				int stt = 1;
				while(rs.next())
				{
					double dongia = rs.getDouble("DONGIA");
					
					double tondau = rs.getDouble("tondau");
					double nhaptrongky = rs.getDouble("nhaptrongky");
					double nhaptrahang = rs.getDouble("nhaptrahang");
					double xuattrongky = rs.getDouble("xuattrongky");
					
					//NHAP TRA HANG KHONG DUOC AM KHO KHI THE HIEN TREN BC NAY
					if( tondau + nhaptrongky - nhaptrahang - xuattrongky < 0 )
						nhaptrahang = tondau + nhaptrongky - xuattrongky;
					
					double toncuoi = tondau + nhaptrongky - nhaptrahang - xuattrongky;
					double thanhtienTC = toncuoi * dongia;
					
					cell = cells.getCell("A" + Integer.toString(i));	cell.setValue( rs.getString("makhCAP1") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("B" + Integer.toString(i));	cell.setValue( rs.getString("tenkhCAP1") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue( rs.getString("makhCAP2") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue( rs.getString("tenkhCAP2") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					
					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue( rs.getString("maSP") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue( rs.getString("tenSP") ); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue( rs.getString("DONVI") ); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					
					cell = cells.getCell("H" + Integer.toString(i));	cell.setValue( rs.getDouble("DONGIA") ); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					
					cell = cells.getCell("I" + Integer.toString(i));	cell.setValue( tondau ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("J" + Integer.toString(i));	cell.setValue( nhaptrongky );	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);					
					cell = cells.getCell("K" + Integer.toString(i));	cell.setValue( nhaptrahang ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("L" + Integer.toString(i));	cell.setValue( xuattrongky ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					
					cell = cells.getCell("M" + Integer.toString(i));	cell.setValue( toncuoi ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("N" + Integer.toString(i));	cell.setValue( thanhtienTC ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					
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
	
	/************** END XNT CONG TAC VIEN *************************************************************************/
	
	
	/************* MAU CTV KIEM SOAT NOI BO ***********************************************************************/
	
	private String getSearchQueryCTV_KSNB(HttpServletRequest request, IStockintransit obj) 
	{
		HttpSession session = request.getSession();
		
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
		
		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));

		if (obj.getnppId().length() > 0)
			query += " and a.npp_fk = '" + obj.getnppId() + "'";
		
		if (obj.gettungay().length() > 0)
			query += " and month( a.ngaynhap ) = '" + obj.gettungay() + "'";

		if (obj.getdenngay().length() > 0)
			query += " and year( a.ngaynhap ) = '" + obj.getdenngay() + "'";
		
		//PHAN QUYEN
		Utility util = new Utility();
		query += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "a", "ddkd_fk", obj.getLoainhanvien(), obj.getDoituongId() );
		
		
		System.out.print(":: BAO CAO CTV MAU KSNB: " + query);
		return query;

	}
	
	private void CreateStaticDataCTV_KSNB(Workbook workbook, String query, IStockintransit obj) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    Cell cell = null;
	    Style style;
		Font font2 = new Font();
		//font2.setName("Calibri");				
		font2.setSize(11);
	    
	    cell = cells.getCell("A2"); cell.setValue( "Từ ngày: " + obj.gettungay() ); 
	    style = cell.getStyle(); style.setFont(font2); cell.setStyle(style);
	    
	    cell = cells.getCell("A3");	cell.setValue( "Đến ngày: " + obj.getdenngay() ); 
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
					cell = cells.getCell("A" + Integer.toString(i));	cell.setValue( rs.getString("asmTen") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("B" + Integer.toString(i));	cell.setValue( rs.getString("gsbhTen") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("C" + Integer.toString(i));	cell.setValue( rs.getString("ddkdTen") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("D" + Integer.toString(i));	cell.setValue( rs.getString("dbTen") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("E" + Integer.toString(i));	cell.setValue( rs.getString("tinhTen") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					
					cell = cells.getCell("F" + Integer.toString(i));	cell.setValue( rs.getString("makhCAP1") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("G" + Integer.toString(i));	cell.setValue( rs.getString("tenkhCAP1") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("H" + Integer.toString(i));	cell.setValue( rs.getString("makhCAP2") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("I" + Integer.toString(i));	cell.setValue( rs.getString("tenkhCAP2") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					
					cell = cells.getCell("J" + Integer.toString(i));	cell.setValue( rs.getString("maCTV") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("K" + Integer.toString(i));	cell.setValue( rs.getString("tenCTV") ); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("L" + Integer.toString(i));	cell.setValue( rs.getString("chucvu") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					
					cell = cells.getCell("M" + Integer.toString(i));	cell.setValue( rs.getString("htbh") ); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("N" + Integer.toString(i));	cell.setValue( rs.getString("kenh") ); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					
					cell = cells.getCell("O" + Integer.toString(i));	cell.setValue( rs.getString("maSP") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("P" + Integer.toString(i));	cell.setValue( rs.getString("tenSP") ); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					
					cell = cells.getCell("Q" + Integer.toString(i));	cell.setValue( rs.getDouble("DONGIA") ); 			style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("R" + Integer.toString(i));	cell.setValue( rs.getDouble("SOLUONG") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("S" + Integer.toString(i));	cell.setValue( rs.getDouble("doanhso") );	style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					
					cell = cells.getCell("T" + Integer.toString(i));	cell.setValue( rs.getString("sodonhang") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("U" + Integer.toString(i));	cell.setValue( rs.getString("ngaydonhang") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("V" + Integer.toString(i));	cell.setValue( rs.getString("sohoadon") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("W" + Integer.toString(i));	cell.setValue( rs.getString("ngayhoadon") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					
					cell = cells.getCell("X" + Integer.toString(i));	cell.setValue( rs.getString("ptCHI") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("Y" + Integer.toString(i));	cell.setValue( rs.getString("chi") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("Z" + Integer.toString(i));	cell.setValue( rs.getString("ptlythuyet") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("AA" + Integer.toString(i));	cell.setValue( rs.getString("ghichu") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					cell = cells.getCell("AB" + Integer.toString(i));	cell.setValue( rs.getString("tt_gt") ); 		style = cell.getStyle(); style.setFont(font2); cell.setStyle(style); setCellBorderStyle(cell);
					
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
	
	/************* END MAU CTV KIEM SOAT NOI BO *******************************************************************/
	
	
	
	
	
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


}

