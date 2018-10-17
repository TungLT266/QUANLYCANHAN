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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Types;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

import java.util.*;

public class SecondarySalesPIR extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public SecondarySalesPIR()
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
		String ctyId = (String)session.getAttribute("congtyId");
		
		obj.setuserId(userId);
		obj.setdiscount("1");
		obj.setvat("0");
		obj.setdvdlId("");
		obj.settype("1");
		obj.setChiTietXNT_Lo_Tong("1");
		obj.init();
		
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		session.setAttribute("congtyId", ctyId);
		String nextJSP = "/TraphacoHYERP/pages/Distributor/SecondarySalesPurchaseInventoryReport.jsp";
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
		
		String nppTen = Ult.getTenNhaPP();
		obj.setuserTen(userTen);
		
		String khId = request.getParameter("khId"); // <!---
		if (khId == null)
			khId = "";
		obj.setkhId(khId);
		
		String nccId = request.getParameter("nccId"); // <!---
		if (nccId == null)
			nccId = "";
		obj.setNccId(nccId);
		
		String kenhId = request.getParameter("kenhId"); // <!---
		if (kenhId == null)
			kenhId = "";
		obj.setkenhId(kenhId);
		
		String dvkdId = request.getParameter("dvkdId"); // <!---
		if (dvkdId == null)
			dvkdId = "";
		obj.setdvkdId(dvkdId);
		
		String nhanhangId = request.getParameter("nhanhangId"); // <!---
		if (nhanhangId == null)
			nhanhangId = "";
		obj.setnhanhangId(nhanhangId);
		
		String chungloaiId = request.getParameter("chungloaiId");// <!---
		if (chungloaiId == null)
			chungloaiId = "";
		obj.setchungloaiId(chungloaiId);
		
		String tungay = request.getParameter("Sdays"); // <!---
		if (tungay == null)
			tungay = "";
		obj.settungay(tungay);
		
		String denngay = request.getParameter("Edays");// <!---
		if (denngay == null)
			denngay = "";
		obj.setdenngay(denngay);
		
		String ddkd = request.getParameter("ddkdId");// <!---
		if (ddkd == null)
			ddkd = "";
		obj.setDdkd(ddkd);
		System.out.println("ddkd id "+obj.getDdkd());
		
		String[] khoId = request.getParameterValues("khoId"); 
		String str = "";
		if (khoId != null)
		{
			for(int i = 0; i < khoId.length; i++)
				str += khoId[i] + ",";
			if(str.length() > 0)
				str = str.substring(0, str.length() - 1);
		}
		System.out.println("khoID " + str);
		obj.setkhoId(str);
		
		String[] spId = request.getParameterValues("spId"); 
		str = "";
		if (spId != null)
		{
			for(int i = 0; i < spId.length; i++)
				str += spId[i] + ",";
			if(str.length() > 0)
				str = str.substring(0, str.length() - 1);
		}
		obj.setsanphamId(str);
		
		String vat = request.getParameter("vat");
		obj.setvat(vat);
		
		String discount = request.getParameter("discount");
		obj.setdiscount(discount);
		
		String instransit = request.getParameter("instransit");
		obj.setHangDiDuong(instransit);
		
		obj.setnhanhangId(util.antiSQLInspection(request.getParameter("nganhhangId")) != null ? util.antiSQLInspection(request.getParameter("nganhhangId")) : "");
		obj.setHoaDonKmDb(util.antiSQLInspection(request.getParameter("hdkmdb")) != null ? util.antiSQLInspection(request.getParameter("hdkmdb")) : "");
		obj.setHangDiDuong(util.antiSQLInspection(request.getParameter("instransit")) != null ? util.antiSQLInspection(request.getParameter("instransit")) : "");
		
		obj.settype(util.antiSQLInspection(request.getParameter("type")) != null ? util.antiSQLInspection(request.getParameter("type")) : "");	
		obj.setLaytheo(util.antiSQLInspection(request.getParameter("laysolo")) != null ? util.antiSQLInspection(request.getParameter("laysolo")) : "");
		
		obj.setChiTietXNT_Lo(util.antiSQLInspection(request.getParameter("layctnxsolo")) != null ? util.antiSQLInspection(request.getParameter("layctnxsolo")) : "");
		
		obj.setChiTietXNT_Lo_Tong(util.antiSQLInspection(request.getParameter("layctnxsolo_tong")) != null ? util.antiSQLInspection(request.getParameter("layctnxsolo_tong")) : "");
		
		
		obj.setTrangthai(util.antiSQLInspection(request.getParameter("laytien")) != null ? util.antiSQLInspection(request.getParameter("laytien")) : "");
		
		obj.setHangdiduong(util.antiSQLInspection(request.getParameter("hangdiduong")) != null ? util.antiSQLInspection(request.getParameter("hangdiduong")) : "");
		
		String[] fieldsHien = request.getParameterValues("fieldsHien");
		obj.setFieldShow(fieldsHien);
		
		String[] fieldsAn = request.getParameterValues("fieldsAn");
		obj.setFieldHidden(fieldsAn);

		String ctyId = (String)session.getAttribute("congtyId");
		dbutils db = new dbutils();
		String query = "";
		
		if (!tungay.equals("") && !denngay.equals(""))
		{
			
			String action = request.getParameter("action");
			
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			if (action.equals("tao"))
			{
				query = "select pk_seq from NHAPHANPHOI where congty_fk = "+ctyId+" and TRANGTHAI = 1 ";
				System.out.println("[congty]" + query);
				ResultSet rs = db.get(query);
				try
				{
					if(rs.next())
					{
						obj.setnppId(rs.getString("pk_seq"));
					}
				}catch(Exception e)
				{
					e.printStackTrace();
				}
				
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "Attachment; filename=BaoCaoXuatNhapTon(NPP)" + this.getPiVotName() + ".xlsm");
				OutputStream out = response.getOutputStream();
				try
				{
					CreatePivotTable(out, response, request, fieldsHien, obj); 
					
				} catch (Exception ex)
				{
					obj.init();
					obj.setMsg(ex.getMessage());
						
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);
					session.setAttribute("congtyId", ctyId);
					String nextJSP = "/TraphacoHYERP/pages/Distributor/SecondarySalesPurchaseInventoryReport.jsp";
					response.sendRedirect(nextJSP);
					
				}
			}else if(action.equals("xem"))
			{
				try
				{
					obj.init();
					query = "select pk_seq from NHAPHANPHOI where congty_fk = "+ctyId+" and TRANGTHAI = 1 ";
					System.out.println("[congty]" + query);
					ResultSet rs = db.get(query);
					try
					{
						if(rs.next())
						{
							obj.setnppId(rs.getString("pk_seq"));
						}
					}catch(Exception e)
					{
						e.printStackTrace();
					}
					query = "delete sanpham_tmp";
					db.update(query);
					if(obj.getsanphamId().length() > 0)
						query = "insert into sanpham_tmp(sanpham_fk) select pk_seq from sanpham where pk_seq in (" + obj.getsanphamId() + ")";
					else
						query = "insert into sanpham_tmp(sanpham_fk) select pk_seq from sanpham where trangthai = 1";
					db.update(query);
					String[] param = new String[5];
					param[0] = obj.getkhoId().equals("") ? null : obj.getkhoId();
					param[1] = obj.gettungay().equals("") ? null : obj.gettungay();
					param[2] = obj.getdenngay().equals("") ? null : obj.getdenngay();
					param[3] = obj.getnppId().equals("") ? null : obj.getnppId();
					if(obj.getkhId().trim().length() > 0)
					{
						param[4] = obj.getkhId().equals("") ? null : obj.getkhId();
						 
						query = "REPORT_XUATNHAPTON_KYGUI_CHITIET"; 
					}
					else if(obj.getNccId().trim().length() > 0)
					{
						param[4] = obj.getNccId().equals("") ? null : obj.getNccId();
						 
						query = "REPORT_XUATNHAPTON_NCC_CHITIET";
					}
					else if(obj.getDdkd().trim().length() > 0)
					{
						if(obj.getDdkd().length() > 0)
							query = "insert into ddkd_tmp(ddkd_fk) select pk_seq from daidienkinhdoanh where pk_seq in (" + obj.getDdkd() + ")";
						else
							query = "insert into ddkd_tmp(ddkd_fk) select pk_seq from daidienkinhdoanh where trangthai = 1 and npp_fk = "+obj.getnppId();
						System.out.println("[ddkd_tmp] "+query);
						db.update(query);
						query = "REPORT_XUATNHAPTON_DDKD_CHITIET"; 
					}
					else
					{
						param = new String[4];
						param[0] = null;
						param[1] = obj.gettungay().equals("") ? null : obj.gettungay();
						param[2] = obj.getdenngay().equals("") ? null : obj.getdenngay();
						param[3] = obj.getnppId().equals("") ? null : obj.getnppId();
						 
						query = "delete kho_tmp";
						db.update(query);
						if(obj.getkhoId().length() > 0)
							query = "insert into kho_tmp(kho_fk) select pk_seq from kho where pk_seq in (" + obj.getkhoId() + ")";
						else
							query = "insert into kho_tmp(kho_fk) select pk_seq from kho where trangthai = 1 and pk_seq in " + util.quyen_kho( obj.getuserId() );
						db.update(query);
						
						query = "REPORT_XUATNHAPTON_CHITIET";
					}
					obj.createXNTRs(query, param);
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);
					session.setAttribute("congtyId", ctyId);
					String nextJSP = "/TraphacoHYERP/pages/Distributor/SecondarySalesPurchaseInventoryReport.jsp";
					response.sendRedirect(nextJSP);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}else{
				obj.init();
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
				session.setAttribute("congtyId", ctyId);
				String nextJSP = "/TraphacoHYERP/pages/Distributor/SecondarySalesPurchaseInventoryReport.jsp";
				response.sendRedirect(nextJSP);
			}
		}	
	}
	
	private void CreatePivotTable(OutputStream out, HttpServletResponse response, HttpServletRequest request, String[] fieldsHien, IStockintransit obj) throws Exception
	{
		try
		{
			String strfstream = getServletContext().getInitParameter("path") + "\\BaoCaoXuatNhapTon(NPP).xlsm";
			if(obj.getLaytheo().equals("1"))
			{
				strfstream = getServletContext().getInitParameter("path") + "\\BaoCaoXuatNhapTon_ChiTiet.xlsm";
			}
			FileInputStream fstream = new FileInputStream(strfstream);
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			Worksheets worksheets = workbook.getWorksheets();
	  		Worksheet worksheet_2 = worksheets.getSheet("sheet1");
	  		
	  		Cells cells = worksheet_2.getCells();
			   
	  		Cell cell = cells.getCell("P1");
			Style style1=cell.getStyle();
				
			if(obj.getChiTietXNT_Lo().equals("1")){
				dbutils db = new dbutils(); 
		  		worksheets = workbook.getWorksheets();
		  		//Worksheet worksheet_1 = worksheets.addSheet("XUAT_NHAP_TON_CHITIET");
		  		System.out.println("[1]");
		  		this.TaoBaoCao(workbook, fieldsHien, obj);
				
			}else{
				
				if(obj.getChiTietXNT_Lo_Tong().equals("1")){
					// chi tiêt xuât nhap ton theo lo tong
					System.out.println("[2]");
					FillData_Lo_Tong(workbook, fieldsHien, obj);
				}else{
					System.out.println("[3]");
					FillData(workbook, fieldsHien, obj);
				}
			}
			
			workbook.save(out);
			fstream.close();
		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}
	
	private void TaoBaoCao(Workbook workbook, String[] fieldsHien, IStockintransit obj) 
	{
		try{
			Utility Ult = new Utility();
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
			ReportAPI.getCellStyle(cell, Color.RED, true, 14, "XUẤT NHẬP TỒN CHI TIẾT");
		   
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
			
			dbutils db = new dbutils();
			ResultSet rs;
			String query = "";
			query = "delete sanpham_tmp";
			db.update(query);
			if(obj.getsanphamId().length() > 0)
				query = "insert into sanpham_tmp(sanpham_fk) select pk_seq from sanpham where pk_seq in (" + obj.getsanphamId() + ")";
			else
				query = "insert into sanpham_tmp(sanpham_fk) select pk_seq from sanpham where trangthai = 1";
			db.update(query);
			String[] param = new String[5];
			param[0] = obj.getkhoId().equals("") ? null : obj.getkhoId();
			param[1] = obj.gettungay().equals("") ? null : obj.gettungay();
			param[2] = obj.getdenngay().equals("") ? null : obj.getdenngay();
			param[3] = obj.getnppId().equals("") ? null : obj.getnppId();
			if(obj.getkhId().trim().length() > 0)
			{
				param[4] = obj.getkhId().equals("") ? null : obj.getkhId();
				rs = db.getRsByPro("REPORT_XUATNHAPTON_KYGUI_CHITIET_COLUMN", param);
			}
			else if(obj.getNccId().trim().length() > 0)
			{
				param[4] = obj.getNccId().equals("") ? null : obj.getNccId();
				rs = db.getRsByPro("REPORT_XUATNHAPTON_NCC_CHITIET_COLUMN", param);
			}
			else if(obj.getDdkd().trim().length() > 0)
			{
				if(obj.getDdkd().length() > 0)
					query = "insert into ddkd_tmp(ddkd_fk) select pk_seq from daidienkinhdoanh where pk_seq in (" + obj.getDdkd() + ")";
				else
					query = "insert into ddkd_tmp(ddkd_fk) select pk_seq from daidienkinhdoanh where trangthai = 1 and npp_fk = "+obj.getnppId();
				System.out.println("[ddkd_tmp] "+query);
				db.update(query);
				rs = db.getRsByPro("REPORT_XUATNHAPTON_DDKD_CHITIET_COLUMN", param);
			}
			else
			{
				param = new String[4];
		
				param[0] = obj.getkhoId().equals("") ? null : obj.getkhoId();
				param[1] = obj.gettungay().equals("") ? null : obj.gettungay();
				param[2] = obj.getdenngay().equals("") ? null : obj.getdenngay();
				param[3] = obj.getnppId().equals("") ? null : obj.getnppId();
		
				//rs = db.getRsByPro("REPORT_XUATNHAPTON_CHITIET_COLUMN", param);
				
				query = "delete kho_tmp";
				db.update(query);
				if(obj.getkhoId().length() > 0)
					query = "insert into kho_tmp(kho_fk) select pk_seq from kho where pk_seq in (" + obj.getkhoId() + ")";
				else
					query = "insert into kho_tmp(kho_fk) select pk_seq from kho where trangthai = 1 and pk_seq in " + Ult.quyen_kho( obj.getuserId() );
				db.update(query);
				
				if(obj.getkhoId().length() > 7 || obj.getkhoId().length() <= 0)
				{
					param[0] = param[0].substring(0, 6);
					rs = db.getRsByPro("REPORT_XUATNHAPTON_CHITIET_COLUMN_NHIEUKHO", param);
				}
				else
					rs = db.getRsByPro("REPORT_XUATNHAPTON_CHITIET_COLUMN", param);
			}
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();
				String[] HideColumnArray= new String[]{
						"Tiền đầu kỳ",
						"Tiền nhập mua hàng",
						"Tiền nhập hàng trả về",
						"Tiền nhập chuyển nội bộ",
						"Tiền nhập chuyển kho bên ngoài",
						"Tiền nhập chuyển kho TDV",
						"Tiền nhập điều chỉnh tồn kho",
						"Tiền nhập hàng khác",
						"Tiền nhập hàng NPP",
						"Tiền nhập hàng nhà máy",
						"Tiền nhập hàng nhà nhập khẩu",
						"Tiền nhập hàng ký gửi KH",
						"Tiền nhập kiểm kho TDV",
						"Tiền xuất sử dụng nội bộ",
						"Tiền xuất chuyển kho ký gửi KH",
						"Tiền xuất hàng mẫu",
						"Tiền xuất hủy",
						"Tiền xuất chuyển bên ngoài",
						"Tiền xuất ứng hàng",
						"Tiền xuất đổi hàng",
						"Tiền xuất chuyển kho TDV",
						"Tiền xuất điều chỉnh tồn kho",
						"Tiền xuất giao hàng (hàng bán)",
						"Tiền xuất giao hàng (hàng KM)",
						"Tiền xuất báo cáo đại lý",
						"Tiền xuất báo cáo đại lý TDV",
						"Tiền xuất kiểm kho TDV",
						"Tiền xuất trả hàng NCC",
						"Tiền cuối kỳ",
						"Tiền đầu kỳ",
						"Tiền nhập",
						"Tiền xuất",
						"Tiền nhập chuyển kho",
						"Tiền xuất chuyển kho",
						"Hàng đi đường",
						"SPID", "KHO_FK", "NHOMKENH_FK", "KHACHHANG_FK", "KHOID"};
				
			String[] mang_columndisplay = new String[0];
			System.out.println(obj.getTrangthai());
			if(obj.getTrangthai().equals("1")){
				System.out.println("Vào đây null 1 cục : ");
				for(int i = 0; i <= 33; i++)
					HideColumnArray[i] = "";
			}
			System.out.println("hangdiduong "+obj.getHangdiduong());
			if(obj.getHangdiduong().equals("1")){
				System.out.println("Vào đây null 1 cục : ");
				HideColumnArray[34] = "";
			}
			mang_columndisplay   = this.getColumnExportExcel(rsmd,HideColumnArray,socottrongSql);
				 
			
			
			
			int countRow = 6, pd = 0, pn = 0, px = 0, pc = 0, dauky = 0, nhap = 0, xuat = 0, cuoiky = 0;
				
			for(int i = 0; i < mang_columndisplay.length; i++)
			{
				int j = Integer.parseInt(mang_columndisplay[i]);
				
				if(rsmd.getColumnName(j).indexOf("đầu kỳ") >= 0) 
				{
					dauky++;
					if(dauky == 1) pd = i;
				}
				if(rsmd.getColumnName(j).indexOf("nhập") >= 0) 
				{
					nhap++;
					if(nhap == 1) pn = i;
				}
				if(rsmd.getColumnName(j).indexOf("xuất") >= 0)
				{
					xuat++;
					if(xuat == 1) px = i;
				}
				if(rsmd.getColumnName(j).indexOf("cuối kỳ") >= 0)
				{
					cuoiky++;
					if(cuoiky == 1) pc = i;
				}
				
				if(dauky < 1 && nhap < 1 && xuat < 1 && cuoiky < 1)
				{
					cells.merge(countRow-1, i, countRow, i);
					cell = cells.getCell(countRow-1, i);
					
					Cell cell1 = cells.getCell(countRow, i);
					cell1.setStyle(style2);
				}
				else
					cell = cells.getCell(countRow, i);
				
				cell.setStyle(style2);
				//ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
    	
				ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, true, 9, rsmd.getColumnName(j));
				
			}
		    System.out.println("d "+pd+", n "+pn+", x "+px+", c "+pc);
			if(dauky > 0)
			{
				cells.merge(5, pd, 5, pn-1);
				for(int i = pd; i <= pn-1; i++)
				{
					cell = cells.getCell(5, i);
					cell.setStyle(style2);
					if(i == pd)
						ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, true, 9, "Tồn đầu kỳ");
				}
			}
		    if(nhap > 0)
			{
				cells.merge(5, pn, 5, px-1);
				for(int i = pn; i <= px-1; i++)
				{
					cell = cells.getCell(5, i);
					cell.setStyle(style2);
					if(i == pn)
						ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, true, 9, "Nhập trong kỳ");
				}
				
			}
			if(xuat > 0)
			{
				cells.merge(5, px, 5, pc-1);
				for(int i = px; i <= pc-1; i++)
				{
					cell = cells.getCell(5, i);
					cell.setStyle(style2);
					if(i == px)
						ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, true, 9, "Xuất trong kỳ");		
				}
			}
			if(cuoiky > 0)
			{
				cells.merge(5, pc, 5, pc+cuoiky-1);
				for(int i = pc; i <= pc+cuoiky-1; i++)
				{
					cell = cells.getCell(5, i);
					cell.setStyle(style2);
					if(i == pc)
						ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, true, 9, "Cuối kỳ");
				}
			}
			countRow ++;
	   
			NumberFormat formatter = new DecimalFormat("#,###,###");
			while(rs.next())
			{
				for( int i =0 ; i < mang_columndisplay.length ; i ++  )
				{
					int j= Integer.parseInt(mang_columndisplay[i]);
					
					cell = cells.getCell(countRow,i);
					
					if(rsmd.getColumnType(j) == Types.DOUBLE || rsmd.getColumnType(j)==Types.INTEGER )
					{
						cell.setStyle(style1);
						ReportAPI.getCellStyle_double(cell, "Times New Roman", Color.BLACK, false, 9,  rs.getDouble(j));
						// ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
					}
					else
					{
						cell.setStyle(style2);
						ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, false, 9, rs.getString(j));
						// ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					}
				}
				++countRow;
			}
	   
			if(rs!=null)rs.close();

		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private String[] getColumnExportExcel(ResultSetMetaData rsmd,
			String[] hideColumnArray, int socottrongSql) {
		
		// TODO Auto-generated method stub
		String[] mangtrave ;
		String strmang="";
		try{
			
		for(int i = 1 ; i <= socottrongSql; i++)
		{
			 
			String tencot=rsmd.getColumnName(i);
			// System.out.println("ten cot: "+tencot);
			 
			 boolean bien=false;
			 for(int j=0;j<hideColumnArray.length;j++ ){
					 if(tencot.equals(hideColumnArray[j])){
						 //System.out.println("ten cot: "+tencot);
						 //System.out.println("hideColumnArray["+j+"] : "+hideColumnArray[j]);
						 bien=true;
					 }
			 }
			 if(!bien){
				 if(strmang.length()==0){
					 strmang= i+"";
				 }else{
					 strmang=strmang+ ","+i;
				 }
			 }
		 
		}
		}catch(Exception er){
			er.printStackTrace();
		}
		
		//System.out.println("str mang: "+strmang);
		mangtrave=strmang.split(",");
		
		return mangtrave;
	}

	private void FillData(Workbook workbook, String[] fieldsHien, IStockintransit obj) throws Exception
	{
		Utility Ult = new Utility();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		Cells cells = worksheet.getCells();
		for(int i=0;i<30;i++){
			   cells.setColumnWidth(i, 20.0f);   
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
		
		dbutils db = new dbutils();
		String[] param = new String[5];
		param[0] = obj.getkhoId().equals("") ? null : obj.getkhoId();
		param[1] = obj.gettungay().equals("") ? null : obj.gettungay();
		param[2] = obj.getdenngay().equals("") ? null : obj.getdenngay();
		param[3] = obj.getnppId().equals("") ? null : obj.getnppId();
		ResultSet rs;
		String query = "";
		query = "delete sanpham_tmp";
		db.update(query);
		if(obj.getsanphamId().length() > 0)
			query = "insert into sanpham_tmp(sanpham_fk) select pk_seq from sanpham where pk_seq in (" + obj.getsanphamId() + ")";
		else
			query = "insert into sanpham_tmp(sanpham_fk) select pk_seq from sanpham where trangthai = 1";
		db.update(query);
		if(obj.getkhId().trim().length() > 0)
		{
			param[4] = obj.getkhId().equals("") ? null : obj.getkhId();
			if(obj.getLaytheo().equals("1"))
			{
				rs = db.getRsByPro("REPORT_XUATNHAPTON_KYGUI_CHITIET", param);
			}
			else
			{
				rs = db.getRsByPro("REPORT_XUATNHAPTON_KYGUI", param);
			}
		}
		else if(obj.getNccId().trim().length() > 0)
		{
			param[4] = obj.getNccId().equals("") ? null : obj.getNccId();
			if(obj.getLaytheo().equals("1"))
			{
				rs = db.getRsByPro("REPORT_XUATNHAPTON_NCC_CHITIET", param);
			}
			else
			{
				rs = db.getRsByPro("REPORT_XUATNHAPTON_NCC", param);
			}
		}
		else if(obj.getDdkd().trim().length() > 0)
		{
			if(obj.getDdkd().length() > 0)
				query = "insert into ddkd_tmp(ddkd_fk) select pk_seq from daidienkinhdoanh where pk_seq in (" + obj.getDdkd() + ")";
			else
				query = "insert into ddkd_tmp(ddkd_fk) select pk_seq from daidienkinhdoanh where trangthai = 1 and npp_fk = "+obj.getnppId();
			System.out.println("[ddkd_tmp] "+query);
			db.update(query);
			if(obj.getLaytheo().equals("1"))
			{
				rs = db.getRsByPro("REPORT_XUATNHAPTON_DDKD_CHITIET", param);
			}
			else
			{
				rs = db.getRsByPro("REPORT_XUATNHAPTON_DDKD", param);
			}
		}
		else
		{
			param = new String[4];
			param[0] = obj.getkhoId().equals("") ? null : obj.getkhoId();
			param[1] = obj.gettungay().equals("") ? null : obj.gettungay();
			param[2] = obj.getdenngay().equals("") ? null : obj.getdenngay();
			param[3] = obj.getnppId().equals("") ? null : obj.getnppId();
			if(obj.getLaytheo().equals("1"))
			{
				rs = db.getRsByPro("REPORT_XUATNHAPTON_CHITIET", param);
			}
			else
			{
				System.out.println("vao day:::::::::::::::::::::::::::::");
				query = "delete kho_tmp";
				db.update(query);
				if(obj.getkhoId().length() > 0)
					query = "insert into kho_tmp(kho_fk) select pk_seq from kho where pk_seq in (" + obj.getkhoId() + ")";
				else
					query = "insert into kho_tmp(kho_fk) select pk_seq from kho where trangthai = 1 and pk_seq in " + Ult.quyen_kho( obj.getuserId() );
				db.update(query);
				
				if(obj.getkhoId().length() > 7 || obj.getkhoId().length() <= 0)
				{
					param[0] = param[0].substring(0, 6);
					rs = db.getRsByPro("REPORT_XUATNHAPTON_NHIEUKHO", param);
				}
				else
					rs = db.getRsByPro("REPORT_XUATNHAPTON", param);
			}
		}
		System.out.println("vao detail:::::::::::::::::::::::::::::");
		
		ResultSetMetaData rsmd = rs.getMetaData();
	    int socottrongSql = rsmd.getColumnCount();
	    String[] HideColumnArray= new String[]{
				"Tiền đầu kỳ",
				"Tiền nhập mua hàng",
				"Tiền nhập hàng trả về",
				"Tiền nhập chuyển nội bộ",
				"Tiền nhập chuyển kho bên ngoài",
				"Tiền nhập chuyển kho TDV",
				"Tiền nhập điều chỉnh tồn kho",
				"Tiền nhập hàng khác",
				"Tiền nhập hàng NPP",
				"Tiền nhập hàng nhà máy",
				"Tiền nhập hàng nhà nhập khẩu",
				"Tiền nhập hàng ký gửi KH",
				"Tiền nhập kiểm kho TDV",
				"Tiền xuất sử dụng nội bộ",
				"Tiền xuất chuyển kho ký gửi KH",
				"Tiền xuất hàng mẫu",
				"Tiền xuất hủy",
				"Tiền xuất chuyển bên ngoài",
				"Tiền xuất ứng hàng",
				"Tiền xuất đổi hàng",
				"Tiền xuất chuyển kho TDV",
				"Tiền xuất điều chỉnh tồn kho",
				"Tiền xuất giao hàng (hàng bán)",
				"Tiền xuất giao hàng (hàng KM)",
				"Tiền xuất báo cáo đại lý",
				"Tiền xuất báo cáo đại lý TDV",
				"Tiền xuất kiểm kho TDV",
				"Tiền xuất trả hàng NCC",
				"Tiền cuối kỳ",
				"Tiền đầu kỳ",
				"Tiền nhập",
				"Tiền xuất",
				"Tiền nhập chuyển kho",
				"Tiền xuất chuyển kho",
				"Hàng đi đường",
				"SPID", "KHO_FK", "NHOMKENH_FK", "KHACHHANG_FK", "KHOID"};
		
		String[] mang_columndisplay = new String[0];
		System.out.println(obj.getTrangthai());
		if(obj.getTrangthai().equals("1")){
			System.out.println("Vào đây null 1 cục : ");
			for(int i = 0; i <= 33; i++)
				HideColumnArray[i] = "";
		}
		System.out.println("hangdiduong "+obj.getHangdiduong());
		if(obj.getHangdiduong().equals("1")){
			System.out.println("Vào đây null 1 cục : ");
			HideColumnArray[34] = "";
		}
		mang_columndisplay   = this.getColumnExportExcel(rsmd,HideColumnArray,socottrongSql);
			  
	    int countRow = 6, pd = 0, pn = 0, px = 0, pc = 0, dauky = 0, nhap = 0, xuat = 0, cuoiky = 0;
	    
	    for( int i = 0; i < mang_columndisplay.length; i++)
		{
			int j = Integer.parseInt(mang_columndisplay[i]);
			String colname = rsmd.getColumnName(j);
			if(rsmd.getColumnName(j).indexOf("đầu kỳ") >= 0) 
			{
				dauky++;
				if(dauky == 1) pd = i;
				colname = colname.replaceAll("đầu kỳ", "");
			}
			if(rsmd.getColumnName(j).indexOf("nhập") >= 0) 
			{
				nhap++;
				if(nhap == 1) pn = i;
				colname = colname.replaceAll("nhập", "");
			}
			if(rsmd.getColumnName(j).indexOf("xuất") >= 0)
			{
				xuat++;
				if(xuat == 1) px = i;
				colname = colname.replaceAll("xuất", "");
			}
			if(rsmd.getColumnName(j).indexOf("cuối kỳ") >= 0)
			{
				cuoiky++;
				if(cuoiky == 1) pc = i;
				colname = colname.replaceAll("cuối kỳ", "");
			}
			
			if(dauky < 1 && nhap < 1 && xuat < 1 && cuoiky < 1)
			{
				cells.merge(countRow-1, i, countRow, i);
				cell = cells.getCell(countRow-1, i);
				
				Cell cell1 = cells.getCell(countRow, i);
				cell1.setStyle(style2);
			}
			else
				cell = cells.getCell(countRow, i);
			cell.setStyle(style2);
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
	
			ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, true, 9, colname);
			
		}
	    System.out.println("d "+pd+", n "+pn+", x "+px+", c "+pc);
	    if(dauky > 0)
		{
			cells.merge(5, pd, 5, pn-1);
			for(int i = pd; i <= pn-1; i++)
			{
				cell = cells.getCell(5, i);
				cell.setStyle(style2);
				if(i == pd)
					ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, true, 9, "Tồn đầu kỳ");
			}
		}
	    if(nhap > 0)
		{
			cells.merge(5, pn, 5, px-1);
			for(int i = pn; i <= px-1; i++)
			{
				cell = cells.getCell(5, i);
				cell.setStyle(style2);
				if(i == pn)
					ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, true, 9, "Nhập trong kỳ");
			}
			
		}
		if(xuat > 0)
		{
			cells.merge(5, px, 5, pc-1);
			for(int i = px; i <= pc-1; i++)
			{
				cell = cells.getCell(5, i);
				cell.setStyle(style2);
				if(i == px)
					ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, true, 9, "Xuất trong kỳ");		
			}
		}
		if(cuoiky > 0)
		{
			cells.merge(5, pc, 5, pc+cuoiky-1);
			for(int i = pc; i <= pc+cuoiky-1; i++)
			{
				cell = cells.getCell(5, i);
				cell.setStyle(style2);
				if(i == pc)
					ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, true, 9, "Cuối kỳ");
			}
		}
	    /*for( int i = 1 ; i <=socottrongSql ; i ++  )
	    {
	    	cell = cells.getCell(countRow,i-1 );
	    	Style s = cell.getStyle();
		    s.setTextWrapped(true);
		    s.setHAlignment(TextAlignmentType.CENTER);
		    s.setVAlignment(TextAlignmentType.JUSTIFY);
		    cell.setStyle(s);
	    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
	    	ReportAPI.getCellStyle(cell, "Times New Roman", Color.WHITE, true, 9, rsmd.getColumnName(i));
	    }*/
	    countRow ++;
	    NumberFormat formatter = new DecimalFormat("#,###,###"); 
	    
	    /*while(rs.next())
	    {
		    for(int i =1;i <=socottrongSql ; i ++)
		    {
		    	cell = cells.getCell(countRow,i-1 );
		    	if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i)==Types.INTEGER )
		    	{
		    		cell.setStyle(style1);
		    		ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, false, 9, formatter.format(rs.getDouble(i)));
		    	}
		    	else
		    	{
		    		ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, false, 9, rs.getString(i));
		    	}
		    }
	    	++countRow;
	    }*/
	    
	    while(rs.next())
		{
			for( int i =0 ; i < mang_columndisplay.length ; i ++  )
			{
				int j= Integer.parseInt(mang_columndisplay[i]);
				
				cell = cells.getCell(countRow,i);
				
				if(rsmd.getColumnType(j) == Types.DOUBLE || rsmd.getColumnType(j)==Types.INTEGER )
				{
					cell.setStyle(style1);
					ReportAPI.getCellStyle_double(cell, "Times New Roman", Color.BLACK, false, 9,  rs.getDouble(j));
					// ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
				}
				else
				{
					cell.setStyle(style2);
					ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, false, 9, rs.getString(j));
					// ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				}
			}
			++countRow;
		}
	   
	    if(rs!=null)
	    	rs.close();
	    if (db != null)
	    	db.shutDown();
	}
	private void FillData_Lo_Tong(Workbook workbook, String[] fieldsHien, IStockintransit obj) throws Exception
	{
		Utility Ult = new Utility();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		Cells cells = worksheet.getCells();
		for(int i=0;i<30;i++){
			   cells.setColumnWidth(i, 20.0f);   
		}
		cells.setColumnWidth(3, 18.0f);
		cells.setColumnWidth(5, 18.0f);
		   
		cells.setRowHeight(0, 50.0f);
		
		Cell cell = cells.getCell("A1");
		ReportAPI.getCellStyle(cell, Color.RED, true, 14, "XUẤT NHẬP TỒN THEO LÔ");
		
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
		
		dbutils db = new dbutils();
		String[] param = new String[5];
		String query = "";
		param[0] = obj.getkhoId().equals("") ? null : obj.getkhoId();
		param[1] = obj.gettungay().equals("") ? null : obj.gettungay();
		param[2] = obj.getdenngay().equals("") ? null : obj.getdenngay();
		param[3] = obj.getnppId().equals("") ? null : obj.getnppId();
		ResultSet rs;
		query = "delete sanpham_tmp";
		db.update(query);
		if(obj.getsanphamId().length() > 0)
			query = "insert into sanpham_tmp(sanpham_fk) select pk_seq from sanpham where pk_seq in (" + obj.getsanphamId() + ")";
		else
			query = "insert into sanpham_tmp(sanpham_fk) select pk_seq from sanpham where trangthai = 1";
		db.update(query);
		if(obj.getkhId().trim().length() > 0)
		{
			param[4] = obj.getkhId().equals("") ? null : obj.getkhId();
			 
				rs = db.getRsByPro("REPORT_XUATNHAPTON_KYGUI_CHITIET", param);
			 
			 
		}
		else if(obj.getNccId().trim().length() > 0)
		{
			param[4] = obj.getNccId().equals("") ? null : obj.getNccId();
			 
				rs = db.getRsByPro("REPORT_XUATNHAPTON_NCC_CHITIET", param);
			 
		}
		else if(obj.getDdkd().trim().length() > 0)
		{
			if(obj.getDdkd().length() > 0)
				query = "insert into ddkd_tmp(ddkd_fk) select pk_seq from daidienkinhdoanh where pk_seq in (" + obj.getDdkd() + ")";
			else
				query = "insert into ddkd_tmp(ddkd_fk) select pk_seq from daidienkinhdoanh where trangthai = 1 and npp_fk = "+obj.getnppId();
			System.out.println("[ddkd_tmp] "+query);
			db.update(query);
				rs = db.getRsByPro("REPORT_XUATNHAPTON_DDKD_CHITIET", param);
			 
		}
		else
		{
			param = new String[4];
			param[0] = null;
			param[1] = obj.gettungay().equals("") ? null : obj.gettungay();
			param[2] = obj.getdenngay().equals("") ? null : obj.getdenngay();
			param[3] = obj.getnppId().equals("") ? null : obj.getnppId();
			 
			query = "delete kho_tmp";
			db.update(query);
			if(obj.getkhoId().length() > 0)
				query = "insert into kho_tmp(kho_fk) select pk_seq from kho where pk_seq in (" + obj.getkhoId() + ")";
			else
				query = "insert into kho_tmp(kho_fk) select pk_seq from kho where trangthai = 1 and pk_seq in " + Ult.quyen_kho( obj.getuserId() );
			db.update(query);
			
			rs = db.getRsByPro("REPORT_XUATNHAPTON_CHITIET", param);
			 
		}
		 
		
		ResultSetMetaData rsmd = rs.getMetaData();
	    int socottrongSql = rsmd.getColumnCount();
	    String[] HideColumnArray= new String[]{
	    		"SPID", "KHO_FK", "NHOMKENH_FK", "KHACHHANG_FK", "KHOID"
				};
		
		String[] mang_columndisplay = new String[0];
		 
		/*if(obj.getTrangthai().equals("1")){
			System.out.println("Vào đây null 1 cục : ");
			HideColumnArray=new String[0];
		}
		System.out.println("hangdiduong "+obj.getHangdiduong());
		if(obj.getHangdiduong().equals("1")){
			System.out.println("Vào đây null 1 cục : ");
			HideColumnArray[27] = "";
		}*/
		mang_columndisplay   = this.getColumnExportExcel(rsmd,HideColumnArray,socottrongSql);
			  
	    int countRow = 6, pd = 0, pn = 0, px = 0, pc = 0, dauky = 0, nhap = 0, xuat = 0, cuoiky = 0;
	    
	    for( int i = 0; i < mang_columndisplay.length; i++)
		{
			int j = Integer.parseInt(mang_columndisplay[i]);
			String colname = rsmd.getColumnName(j);
			if(rsmd.getColumnName(j).indexOf("đầu kỳ") >= 0) 
			{
				dauky++;
				if(dauky == 1) pd = i;
				colname = colname.replaceAll("đầu kỳ", "");
			}
			if(rsmd.getColumnName(j).indexOf("nhập") >= 0) 
			{
				nhap++;
				if(nhap == 1) pn = i;
				colname = colname.replaceAll("nhập", "");
			}
			if(rsmd.getColumnName(j).indexOf("xuất") >= 0)
			{
				xuat++;
				if(xuat == 1) px = i;
				colname = colname.replaceAll("xuất", "");
			}
			if(rsmd.getColumnName(j).indexOf("cuối kỳ") >= 0)
			{
				cuoiky++;
				if(cuoiky == 1) pc = i;
				colname = colname.replaceAll("cuối kỳ", "");
			}
			
			if(dauky < 1 && nhap < 1 && xuat < 1 && cuoiky < 1)
			{
				cells.merge(countRow-1, i, countRow, i);
				cell = cells.getCell(countRow-1, i);
				
				Cell cell1 = cells.getCell(countRow, i);
				cell1.setStyle(style2);
			}
			else
				cell = cells.getCell(countRow, i);
			cell.setStyle(style2);
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
	
			ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, true, 9, colname);
			
		}
	    System.out.println("d "+pd+", n "+pn+", x "+px+", c "+pc);
	    if(dauky > 0)
		{
			cells.merge(5, pd, 5, pn-1);
			for(int i = pd; i <= pn-1; i++)
			{
				cell = cells.getCell(5, i);
				cell.setStyle(style2);
				if(i == pd)
					ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, true, 9, "Tồn đầu kỳ");
			}
		}
	    if(nhap > 0)
		{
			cells.merge(5, pn, 5, px-1);
			for(int i = pn; i <= px-1; i++)
			{
				cell = cells.getCell(5, i);
				cell.setStyle(style2);
				if(i == pn)
					ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, true, 9, "Nhập trong kỳ");
			}
			
		}
		if(xuat > 0)
		{
			cells.merge(5, px, 5, pc-1);
			for(int i = px; i <= pc-1; i++)
			{
				cell = cells.getCell(5, i);
				cell.setStyle(style2);
				if(i == px)
					ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, true, 9, "Xuất trong kỳ");		
			}
		}
		if(cuoiky > 0)
		{
			cells.merge(5, pc, 5, pc+cuoiky-1);
			for(int i = pc; i <= pc+cuoiky-1; i++)
			{
				cell = cells.getCell(5, i);
				cell.setStyle(style2);
				if(i == pc)
					ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, true, 9, "Cuối kỳ");
			}
		}
	   
	    countRow ++;
	    NumberFormat formatter = new DecimalFormat("#,###,###"); 
	     
	    while(rs.next())
		{
			for( int i =0 ; i < mang_columndisplay.length ; i ++  )
			{
				int j= Integer.parseInt(mang_columndisplay[i]);
				
				cell = cells.getCell(countRow,i);
				
				if(rsmd.getColumnType(j) == Types.DOUBLE || rsmd.getColumnType(j)==Types.INTEGER )
				{
					cell.setStyle(style1);
					ReportAPI.getCellStyle_double(cell, "Times New Roman", Color.BLACK, false, 9,  rs.getDouble(j));
					// ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
				}
				else
				{
					cell.setStyle(style2);
					ReportAPI.getCellStyle(cell, "Times New Roman", Color.BLACK, false, 9, rs.getString(j));
					// ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
				}
			}
			++countRow;
		}
	   
	    if(rs!=null)
	    	rs.close();
	    if (db != null)
	    	db.shutDown();
	}


}

