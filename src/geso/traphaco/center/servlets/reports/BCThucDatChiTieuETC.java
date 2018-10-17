package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.chitieunhanvienetc.IChiTieuNhanvienETC;
import geso.traphaco.center.beans.chitieunhanvienetc.imp.ChiTieuNhanvienETC;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jxl.CellView;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class BCThucDatChiTieuETC extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public BCThucDatChiTieuETC()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IChiTieuNhanvienETC obj = new ChiTieuNhanvienETC();
		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		obj.setUserId(userId);
		//obj.init();

		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Center/BCThucDatChiTieuETC.jsp";
		
		   String view = request.getParameter("view");
		    if(view == null)
		    	view = "";
		response.sendRedirect(nextJSP);   
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		IChiTieuNhanvienETC obj = new ChiTieuNhanvienETC();
		Utility util = new Utility();
		
		obj.setUserId((String) session.getAttribute("userId") == null ? "" : (String) session.getAttribute("userId"));

		obj.setNam( util.antiSQLInspection(request.getParameter("nam")) );
		obj.setThang( util.antiSQLInspection(request.getParameter("thang")) );
		obj.setLoai( util.antiSQLInspection(request.getParameter("loaichitieu")) );
		
		String nppId = util.antiSQLInspection(request.getParameter("nppId"));
		if (nppId == null)
			nppId = "";
		obj.setNppId(nppId);
		
		String kbhId = util.antiSQLInspection(request.getParameter("kbhId"));
		if (kbhId == null)
			kbhId = "";
		obj.setKbhId(kbhId);
		
		String loainhanvien = util.antiSQLInspection(request.getParameter("loainhanvien"));
		if (loainhanvien == null)
			loainhanvien = "0";
		obj.setLoainhanvien(loainhanvien);
		
		String view = request.getParameter("view");
		if(view == null)
			view = "";
		
		String action = util.antiSQLInspection(request.getParameter("action"));
		if (action.equals("Taomoi"))
		{
			try
			{
		    	String query = "";
		    	
		    	if( obj.getLoai().equals("0") )  //OTC
		    	{
			    	if( loainhanvien.equals("0") )
			    	{
			    		query = "select * from ufn_chitieu_tdv_otc ( 106313, " + obj.getThang() + ", " + obj.getNam() + " ) A where 1 = 1 ";
			    		
			    		query += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "a", "DDKD_FK", session.getAttribute("loainhanvien").toString(), session.getAttribute("doituongId").toString() );
			    		
			    		System.out.println("::: LAY BC: " + query );
				    	this.XuatExcel( response, query, obj.getThang(), obj.getNam() );
			    	}
			    	else if( loainhanvien.equals("1") )
			    	{
			    		query = "select * from ufn_chitieu_gsbh_otc ( 106313, " + obj.getThang() + ", " + obj.getNam() + " ) ";
			    		
			    		System.out.println("::: LAY BC: " + query );
				    	this.XuatExcelGSBH( response, query, obj.getThang(), obj.getNam() );
			    	}
			    	else if( loainhanvien.equals("2") )
			    	{
			    		query = "select * from ufn_chitieu_asm_otc ( 106313, " + obj.getThang() + ", " + obj.getNam() + " ) ";
			    		
			    		System.out.println("::: LAY BC: " + query );
				    	this.XuatExcelASM( response, query, obj.getThang(), obj.getNam() );
			    	}
		    	}
		    	else //ETC
		    	{
		    		if( loainhanvien.equals("0") )
			    	{
			    		query = "select * from ufn_chitieu_tdv_etc ( 106313, " + obj.getThang() + ", " + obj.getNam() + " ) A where 1 = 1 ";
			    		
			    		query += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "a", "DDKD_FK", session.getAttribute("loainhanvien").toString(), session.getAttribute("doituongId").toString() );
			    		
			    		System.out.println("::: LAY BC: " + query );
				    	this.XuatExcel_ETC( response, query, obj.getThang(), obj.getNam() );
			    	}
			    	else if( loainhanvien.equals("1") )
			    	{
			    		query = "select * from ufn_chitieu_gsbh_etc ( 106313, " + obj.getThang() + ", " + obj.getNam() + " ) ";
			    		
			    		System.out.println("::: LAY BC: " + query );
				    	this.XuatExcelGSBH_ETC( response, query, obj.getThang(), obj.getNam() );
			    	}
			    	else if( loainhanvien.equals("2") )
			    	{
			    		query = "select * from ufn_chitieu_asm_etc ( 106313, " + obj.getThang() + ", " + obj.getNam() + " ) ";
			    		
			    		System.out.println("::: LAY BC: " + query );
				    	this.XuatExcelASM_ETC( response, query, obj.getThang(), obj.getNam() );
			    	}
		    	}
			} 
			catch (Exception ex)
			{
				ex.printStackTrace();
				obj.createRs();
				session.setAttribute("obj", obj);
				session.setAttribute("userId", obj.getUserId());
				obj.setMessage(ex.getMessage());
			}
		} 
		else
		{
			obj.createRs();
			session.setAttribute("obj", obj);
			session.setAttribute("userId", obj.getUserId());
		}
		
		String nextJSP = "/TraphacoHYERP/pages/Center/BCThucDatChiTieuETC.jsp";
		response.sendRedirect(nextJSP);

	}
	

	private void XuatExcel(HttpServletResponse response, String query, String thang, String nam) throws IOException
	{
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=ThucDatVaChiTieu.xls");
			
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());
			WritableSheet sheet = w.createSheet("Sheet1", 0);
			
			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 18);
			cellFont.setColour(Colour.BLACK);
			cellFont.setBoldStyle(jxl.write.WritableFont.BOLD);
			
			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

			sheet.addCell(new Label(0, 0, "Thực đạt chỉ tiêu OTC ", cellFormat));
			
			sheet.addCell(new Label(0, 1, "Tháng: "));
			sheet.addCell(new Label(1, 1, thang));

			sheet.addCell(new Label(0, 2, "Năm: "));
			sheet.addCell(new Label(1, 2, nam));

			cellFont = new WritableFont(WritableFont.TIMES, 12);
			cellFont.setColour(Colour.BLACK);
			
			cellFormat = new WritableCellFormat(cellFont);
			cellFormat.setBackground(jxl.format.Colour.GREY_25_PERCENT);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setAlignment(Alignment.CENTRE);

			sheet.addCell(new Label(0, 4, "STT", cellFormat));
			sheet.addCell(new Label(1, 4, "Khu vực", cellFormat));
			sheet.addCell(new Label(2, 4, "Tỉnh thành", cellFormat));
			sheet.addCell(new Label(3, 4, "Mã NV", cellFormat));
			sheet.addCell(new Label(4, 4, "Tên NV", cellFormat));
			
			sheet.addCell(new Label(5, 4, "Vị trí", cellFormat));
			sheet.addCell(new Label(6, 4, "Kênh bán hàng", cellFormat));
			
			sheet.mergeCells(7, 4, 11, 4);
			sheet.addCell(new Label(7, 4, "Chỉ tiêu", cellFormat));
			
			sheet.addCell(new Label(12, 4, "Thực hiện", cellFormat));
			sheet.mergeCells(12, 4, 17, 4);
			
			sheet.addCell(new Label(18, 4, "% Thực hiện / Kế hoạch", cellFormat));
			sheet.mergeCells(18, 4, 20, 4);
			
			sheet.addCell(new Label(21, 4, "Thưởng", cellFormat));
			sheet.mergeCells(21, 4, 22, 4);
			
			sheet.addCell(new Label(0, 5, " ", cellFormat));
			sheet.mergeCells(0, 5, 6, 5);
			
			//Chỉ tiêu
			sheet.addCell(new Label(7, 5, "Hàng chiến lược", cellFormat));
			sheet.addCell(new Label(8, 5, "Hàng đặc trị", cellFormat));
			sheet.addCell(new Label(9, 5, "Tổng DS khoán", cellFormat));
			sheet.addCell(new Label(10, 5, "KPI 1", cellFormat));
			sheet.addCell(new Label(11, 5, "KPI 2", cellFormat));
			
			//Thực đạt
			sheet.addCell(new Label(12, 5, "Hàng chiến lược", cellFormat));
			sheet.addCell(new Label(13, 5, "Hàng đặc trị", cellFormat));
			sheet.addCell(new Label(14, 5, "Tổng DS khoán", cellFormat));
			sheet.addCell(new Label(15, 5, "Đặc trị/ doanh số", cellFormat));
			sheet.addCell(new Label(16, 5, "KPI 1", cellFormat));
			sheet.addCell(new Label(17, 5, "KPI 2", cellFormat));
			
			//Tỉ lệ
			sheet.addCell(new Label(18, 5, "Hàng chiến lược", cellFormat));
			sheet.addCell(new Label(19, 5, "Hàng đặc trị", cellFormat));
			sheet.addCell(new Label(20, 5, "Tổng DS khoán", cellFormat));
			
			//Thưởng
			sheet.addCell(new Label(21, 5, "KPI 1", cellFormat));
			sheet.addCell(new Label(22, 5, "KPI 2", cellFormat));
			
			// set style to cell data
			WritableCellFormat cellFormat2 = new WritableCellFormat();

			cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			dbutils db = new dbutils();
			ResultSet rs = db.get(query);
			
			int j = 6;
			int stt = 1;
			NumberFormat formater = new DecimalFormat("#,###,###");
			while (rs.next())
			{
				Label label;
				
				String kbh = rs.getString("kbh");
				double hangchienluoc = rs.getDouble("hangchienluoc");
				double hangdactri = rs.getDouble("hangdactri");
				double tongdskhoan = rs.getDouble("tongdskhoan");
				double kpichienluoc = rs.getDouble("kpichienluoc");
				double kpidactri = rs.getDouble("kpidactri");
				
				//Thực đạt
				double td_chienluoc = rs.getDouble("td_hangchienluoc");
				double td_dactri = rs.getDouble("td_hangdactri");
				double td_tongdskhoan = td_chienluoc + td_dactri;
				double td_dactri_tren_ds = 0;
				if( td_tongdskhoan != 0 )
					td_dactri_tren_ds = td_dactri * 100 / td_tongdskhoan;
				double thuchien_kpi1 = rs.getDouble("thuchien_kpi1");
				double thuchien_kpi2 = rs.getDouble("thuchien_kpi2");
				
				//Phần trăm thực hiện
				double ptth_chienluoc = 0;
				if( hangchienluoc != 0 )
					ptth_chienluoc = td_chienluoc * 100 / hangchienluoc;
				
				double ptth_dactri = 0;
				if( hangdactri != 0 )
					ptth_dactri = td_dactri * 100 / hangdactri;
				
				double ptth_tongdskhoan = 0;
				if( tongdskhoan != 0 )
					ptth_tongdskhoan = td_tongdskhoan * 100 / tongdskhoan;
				
				double thuong_kpi1 = 0;
				double thuong_kpi2 = 0;
				
				if( kbh.equals("TPC") )
				{
					thuong_kpi1 = thuchien_kpi1 * 10000;  // 10k / 1 don hang
					
					double tile = 0;
					if( kpichienluoc != 0 )
						tile = thuchien_kpi2 * 100 / kpichienluoc;
					
					if( tile >= 100 )
						thuong_kpi2 = 1000000;  // 1 trieu / 1 tdv
				}
				else if( kbh.equals("SPC") ) //SPC
				{
					double tile = 0;
					if( kpidactri != 0 )
						tile = thuchien_kpi1 * 100 / kpidactri;
					
					if( tile >= 100 )
						thuong_kpi1 = 1000000;  // 1 trieu / 1 tdv
					
					//GIONG TPC
					tile = 0;
					if( kpichienluoc != 0 )
						tile = thuchien_kpi2 * 100 / kpichienluoc;
					
					if( tile >= 100 )
						thuong_kpi2 = 1000000;  // 1 trieu / 1 tdv
				}
				else
				{
					thuong_kpi1 = 0;
					thuong_kpi2 = 0;
				}
				
				label = new Label(0, j, Integer.toString(stt), cellFormat2);
				sheet.addCell(label);

				label = new Label(1, j, rs.getString("khuvuc"), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(2, j, rs.getString("tinhthanh"), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(3, j, rs.getString("maFAST"), cellFormat2);
				sheet.addCell(label);

				label = new Label(4, j, rs.getString("ten"), cellFormat2);
				sheet.addCell(label);

				label = new Label(5, j, rs.getString("vitri"), cellFormat2);
				sheet.addCell(label);

				label = new Label(6, j, rs.getString("kbh"), cellFormat2);
				sheet.addCell(label);
				
				//Chỉ tiêu
				label = new Label(7, j, formater.format(hangchienluoc), cellFormat2);
				sheet.addCell(label);

				label = new Label(8, j, formater.format(hangdactri), cellFormat2);
				sheet.addCell(label);

				label = new Label(9, j, formater.format(tongdskhoan), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(10, j, formater.format(kpichienluoc), cellFormat2);
				sheet.addCell(label);

				label = new Label(11, j, formater.format(kpidactri), cellFormat2);
				sheet.addCell(label);
				
				
				//Thực đạt
				label = new Label(12, j, formater.format(td_chienluoc), cellFormat2);
				sheet.addCell(label);

				label = new Label(13, j, formater.format(td_dactri), cellFormat2);
				sheet.addCell(label);

				label = new Label(14, j, formater.format(td_tongdskhoan), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(15, j, formater.format(td_dactri_tren_ds), cellFormat2);
				sheet.addCell(label);

				label = new Label(16, j, formater.format(thuchien_kpi1), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(17, j, formater.format(thuchien_kpi2), cellFormat2);
				sheet.addCell(label);

				
				//Tỉ lệ thực hiện
				label = new Label(18, j, formater.format(ptth_chienluoc), cellFormat2);
				sheet.addCell(label);

				label = new Label(19, j, formater.format(ptth_dactri), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(20, j, formater.format(ptth_tongdskhoan), cellFormat2);
				sheet.addCell(label);

				
				//Thưởng
				label = new Label(21, j, formater.format(thuong_kpi1), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(22, j, formater.format(thuong_kpi2), cellFormat2);
				sheet.addCell(label);

				j++;	
				stt++;
			}
			
			for(int x = 1; x <= 25; x++)
			{
			    CellView cell = sheet.getColumnView(x);
			    cell.setAutosize(true);
			    sheet.setColumnView(x, cell);
			}
			
			w.write();
			w.close();
			db.shutDown();
		} 
		catch (Exception e)
		{
			System.out.println("Error Cac Ban : " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void XuatExcelGSBH(HttpServletResponse response, String query, String thang, String nam) throws IOException
	{
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=ThucDatVaChiTieu.xls");
			
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());
			WritableSheet sheet = w.createSheet("Sheet1", 0);
			
			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 18);
			cellFont.setColour(Colour.BLACK);
			cellFont.setBoldStyle(jxl.write.WritableFont.BOLD);
			
			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

			sheet.addCell(new Label(0, 0, "Thực đạt chỉ tiêu OTC ", cellFormat));
			
			sheet.addCell(new Label(0, 1, "Tháng: "));
			sheet.addCell(new Label(1, 1, thang));

			sheet.addCell(new Label(0, 2, "Năm: "));
			sheet.addCell(new Label(1, 2, nam));

			cellFont = new WritableFont(WritableFont.TIMES, 12);
			cellFont.setColour(Colour.BLACK);
			
			cellFormat = new WritableCellFormat(cellFont);
			cellFormat.setBackground(jxl.format.Colour.GREY_25_PERCENT);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setAlignment(Alignment.CENTRE);

			sheet.addCell(new Label(0, 4, "STT", cellFormat));
			sheet.addCell(new Label(1, 4, "Khu vực", cellFormat));
			sheet.addCell(new Label(2, 4, "Tỉnh thành", cellFormat));
			sheet.addCell(new Label(3, 4, "Mã NV", cellFormat));
			sheet.addCell(new Label(4, 4, "Tên NV", cellFormat));
			
			sheet.addCell(new Label(5, 4, "Vị trí", cellFormat));
			sheet.addCell(new Label(6, 4, "Kênh bán hàng", cellFormat));
			
			sheet.addCell(new Label(7, 4, "Chỉ tiêu", cellFormat));
			sheet.mergeCells(7, 4, 11, 4);
			
			sheet.addCell(new Label(12, 4, "Thực hiện", cellFormat));
			sheet.mergeCells(12, 4, 17, 4);
			
			sheet.addCell(new Label(18, 4, "% Thực hiện / Kế hoạch", cellFormat));
			sheet.mergeCells(18, 4, 20, 4);
			
			sheet.addCell(new Label(21, 4, "Thưởng", cellFormat));
			sheet.mergeCells(21, 4, 23, 4);
			
			sheet.addCell(new Label(0, 5, " ", cellFormat));
			sheet.mergeCells(0, 5, 6, 5);
			
			//Chỉ tiêu
			sheet.addCell(new Label(7, 5, "Hàng chiến lược", cellFormat));
			sheet.addCell(new Label(8, 5, "Hàng đặc trị", cellFormat));
			sheet.addCell(new Label(9, 5, "Tổng DS khoán", cellFormat));
			sheet.addCell(new Label(10, 5, "KPI 1", cellFormat));
			sheet.addCell(new Label(11, 5, "KPI 2", cellFormat));
			
			//Thực đạt
			sheet.addCell(new Label(12, 5, "Hàng chiến lược", cellFormat));
			sheet.addCell(new Label(13, 5, "Hàng đặc trị", cellFormat));
			sheet.addCell(new Label(14, 5, "Tổng DS khoán", cellFormat));
			sheet.addCell(new Label(15, 5, "Đặc trị/ doanh số", cellFormat));
			sheet.addCell(new Label(16, 5, "KPI 1", cellFormat));
			sheet.addCell(new Label(17, 5, "KPI 2", cellFormat));
			
			//Tỉ lệ
			sheet.addCell(new Label(18, 5, "Hàng chiến lược", cellFormat));
			sheet.addCell(new Label(19, 5, "Hàng đặc trị", cellFormat));
			sheet.addCell(new Label(20, 5, "Tổng DS khoán", cellFormat));
			
			//Thưởng
			sheet.addCell(new Label(21, 5, "KPI 1", cellFormat));
			sheet.addCell(new Label(22, 5, "KPI 2", cellFormat));
			sheet.addCell(new Label(23, 5, "Lương hiệu quả", cellFormat));
			
			// set style to cell data
			WritableCellFormat cellFormat2 = new WritableCellFormat();

			cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			dbutils db = new dbutils();
			ResultSet rs = db.get(query);
			
			int j = 6;
			int stt = 1;
			NumberFormat formater = new DecimalFormat("#,###,###");
			while (rs.next())
			{
				Label label;
				
				//String kbh = rs.getString("kbh");
				double hangchienluoc = rs.getDouble("hangchienluoc");
				double hangdactri = rs.getDouble("hangdactri");
				double tongdskhoan = rs.getDouble("tongdskhoan");
				double kpichienluoc = rs.getDouble("kpichienluoc");
				double kpidactri = rs.getDouble("kpidactri");
				
				//Thực đạt
				double td_chienluoc = rs.getDouble("thuchien_kpi1"); //Bên GS và ASM 2 thông số này chính là thực hiện của KPI1, 2
				double td_dactri = rs.getDouble("thuchien_kpi2");
				double td_tongdskhoan = td_chienluoc + td_dactri;
				double td_dactri_tren_ds = 0;
				if( td_tongdskhoan != 0 )
					td_dactri_tren_ds = td_dactri * 100 / td_tongdskhoan;
				double thuchien_kpi1 = rs.getDouble("thuchien_kpi1");
				double thuchien_kpi2 = rs.getDouble("thuchien_kpi2");
				
				//Phần trăm thực hiện
				double ptth_chienluoc = 0;
				if( hangchienluoc != 0 )
					ptth_chienluoc = td_chienluoc * 100 / hangchienluoc;
				
				double ptth_dactri = 0;
				if( hangdactri != 0 )
					ptth_dactri = td_dactri * 100 / hangdactri;
				
				double ptth_tongdskhoan = 0;
				if( tongdskhoan != 0 )
					ptth_tongdskhoan = td_tongdskhoan * 100 / tongdskhoan;
				
				double thuong_kpi1 = 0;
				double thuong_kpi2 = 0;
				
				//LHQ= K1*T1+K2*T2
				double T1 = 0;
				double K1 = 0;
				double T2 = 0;
				double K2 = 0;
				double T = 0;
				
				if( hangchienluoc != 0 )
					T1 = thuchien_kpi1 * 100 / hangchienluoc;
				
				if ( hangdactri != 0 )
					T2 = thuchien_kpi2 * 100 / hangdactri;
				
				T = ( thuchien_kpi1 + thuchien_kpi2 ) * 100 / ( hangchienluoc + hangdactri );
				if( T >= 100 )
				{
					thuong_kpi1 = 8000000;
					thuong_kpi2 = 4000000;
				}
				else
				{
					thuong_kpi1 = 7000000;
					thuong_kpi2 = 3000000;
				}
				
				K1 = thuong_kpi1;
				K2 = thuong_kpi2;
				double lhq = ( K1 * T1 / 100.0 ) + ( K2 * T2 / 100.0 ) ;
				
				label = new Label(0, j, Integer.toString(stt), cellFormat2);
				sheet.addCell(label);

				label = new Label(1, j, rs.getString("khuvuc"), cellFormat2);
				sheet.addCell(label);

				label = new Label(2, j, rs.getString("tinhthanh"), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(3, j, rs.getString("maFAST"), cellFormat2);
				sheet.addCell(label);

				label = new Label(4, j, rs.getString("ten"), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(5, j, rs.getString("vitri"), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(6, j, rs.getString("kbh"), cellFormat2);
				sheet.addCell(label);

				//Chỉ tiêu
				label = new Label(7, j, formater.format(hangchienluoc), cellFormat2);
				sheet.addCell(label);

				label = new Label(8, j, formater.format(hangdactri), cellFormat2);
				sheet.addCell(label);

				label = new Label(9, j, formater.format(tongdskhoan), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(10, j, formater.format(kpichienluoc), cellFormat2);
				sheet.addCell(label);

				label = new Label(11, j, formater.format(kpidactri), cellFormat2);
				sheet.addCell(label);

				//Thực đạt
				label = new Label(12, j, formater.format(td_chienluoc), cellFormat2);
				sheet.addCell(label);

				label = new Label(13, j, formater.format(td_dactri), cellFormat2);
				sheet.addCell(label);

				label = new Label(14, j, formater.format(td_tongdskhoan), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(15, j, formater.format(td_dactri_tren_ds), cellFormat2);
				sheet.addCell(label);

				label = new Label(16, j, formater.format(thuchien_kpi1), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(17, j, formater.format(thuchien_kpi2), cellFormat2);
				sheet.addCell(label);

				
				//Tỉ lệ thực hiện
				label = new Label(18, j, formater.format(ptth_chienluoc), cellFormat2);
				sheet.addCell(label);

				label = new Label(19, j, formater.format(ptth_dactri), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(20, j, formater.format(ptth_tongdskhoan), cellFormat2);
				sheet.addCell(label);

				
				//Thưởng
				label = new Label(21, j, formater.format(thuong_kpi1), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(22, j, formater.format(thuong_kpi2), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(23, j, formater.format(lhq), cellFormat2);
				sheet.addCell(label);

				j++;	
				stt++;
			}
			
			for(int x = 1; x <= 25; x++)
			{
			    CellView cell = sheet.getColumnView(x);
			    cell.setAutosize(true);
			    sheet.setColumnView(x, cell);
			}
			
			w.write();
			w.close();
			db.shutDown();
		} 
		catch (Exception e)
		{
			System.out.println("Error Cac Ban : " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void XuatExcelASM(HttpServletResponse response, String query, String thang, String nam) throws IOException
	{
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=ThucDatVaChiTieu.xls");
			
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());
			WritableSheet sheet = w.createSheet("Sheet1", 0);
			
			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 18);
			cellFont.setColour(Colour.BLACK);
			cellFont.setBoldStyle(jxl.write.WritableFont.BOLD);
			
			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

			sheet.addCell(new Label(0, 0, "Thực đạt chỉ tiêu OTC ", cellFormat));
			
			sheet.addCell(new Label(0, 1, "Tháng: "));
			sheet.addCell(new Label(1, 1, thang));

			sheet.addCell(new Label(0, 2, "Năm: "));
			sheet.addCell(new Label(1, 2, nam));

			cellFont = new WritableFont(WritableFont.TIMES, 12);
			cellFont.setColour(Colour.BLACK);
			
			cellFormat = new WritableCellFormat(cellFont);
			cellFormat.setBackground(jxl.format.Colour.GREY_25_PERCENT);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setAlignment(Alignment.CENTRE);

			sheet.addCell(new Label(0, 4, "STT", cellFormat));
			sheet.addCell(new Label(1, 4, "Khu vực", cellFormat));
			sheet.addCell(new Label(2, 4, "Tỉnh thành", cellFormat));
			sheet.addCell(new Label(3, 4, "Mã NV", cellFormat));
			sheet.addCell(new Label(4, 4, "Tên NV", cellFormat));
			
			sheet.addCell(new Label(5, 4, "Vị trí", cellFormat));
			sheet.addCell(new Label(6, 4, "Kênh bán hàng", cellFormat));
			
			sheet.addCell(new Label(7, 4, "Chỉ tiêu", cellFormat));
			sheet.mergeCells(7, 4, 11, 4);
			
			sheet.addCell(new Label(12, 4, "Thực hiện", cellFormat));
			sheet.mergeCells(12, 4, 17, 4);
			
			sheet.addCell(new Label(18, 4, "% Thực hiện / Kế hoạch", cellFormat));
			sheet.mergeCells(18, 4, 20, 4);
			
			sheet.addCell(new Label(21, 4, "Thưởng", cellFormat));
			sheet.mergeCells(21, 4, 23, 4);
			
			sheet.addCell(new Label(0, 5, " ", cellFormat));
			sheet.mergeCells(0, 5, 6, 5);
			
			//Chỉ tiêu
			sheet.addCell(new Label(7, 5, "Hàng chiến lược", cellFormat));
			sheet.addCell(new Label(8, 5, "Hàng đặc trị", cellFormat));
			sheet.addCell(new Label(9, 5, "Tổng DS khoán", cellFormat));
			sheet.addCell(new Label(10, 5, "KPI 1", cellFormat));
			sheet.addCell(new Label(11, 5, "KPI 2", cellFormat));
			
			//Thực đạt
			sheet.addCell(new Label(12, 5, "Hàng chiến lược", cellFormat));
			sheet.addCell(new Label(13, 5, "Hàng đặc trị", cellFormat));
			sheet.addCell(new Label(14, 5, "Tổng DS khoán", cellFormat));
			sheet.addCell(new Label(15, 5, "Đặc trị/ doanh số", cellFormat));
			sheet.addCell(new Label(16, 5, "KPI 1", cellFormat));
			sheet.addCell(new Label(17, 5, "KPI 2", cellFormat));
			
			//Tỉ lệ
			sheet.addCell(new Label(18, 5, "Hàng chiến lược", cellFormat));
			sheet.addCell(new Label(19, 5, "Hàng đặc trị", cellFormat));
			sheet.addCell(new Label(20, 5, "Tổng DS khoán", cellFormat));
			
			//Thưởng
			sheet.addCell(new Label(21, 5, "KPI 1", cellFormat));
			sheet.addCell(new Label(22, 5, "KPI 2", cellFormat));
			sheet.addCell(new Label(23, 5, "Lương hiệu quả", cellFormat));
			
			// set style to cell data
			WritableCellFormat cellFormat2 = new WritableCellFormat();

			cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			dbutils db = new dbutils();
			ResultSet rs = db.get(query);
			
			int j = 6;
			int stt = 1;
			NumberFormat formater = new DecimalFormat("#,###,###");
			while (rs.next())
			{
				Label label;
				
				//String kbh = rs.getString("kbh");
				double hangchienluoc = rs.getDouble("hangchienluoc");
				double hangdactri = rs.getDouble("hangdactri");
				double tongdskhoan = rs.getDouble("tongdskhoan");
				double kpichienluoc = rs.getDouble("kpichienluoc");
				double kpidactri = rs.getDouble("kpidactri");
				
				//Thực đạt
				double td_chienluoc = rs.getDouble("thuchien_kpi1"); //Bên GS và ASM 2 thông số này chính là thực hiện của KPI1, 2
				double td_dactri = rs.getDouble("thuchien_kpi2");
				double td_tongdskhoan = td_chienluoc + td_dactri;
				double td_dactri_tren_ds = 0;
				if( td_tongdskhoan != 0 )
					td_dactri_tren_ds = td_dactri * 100 / td_tongdskhoan;
				double thuchien_kpi1 = rs.getDouble("thuchien_kpi1");
				double thuchien_kpi2 = rs.getDouble("thuchien_kpi2");
				
				//Phần trăm thực hiện
				double ptth_chienluoc = 0;
				if( hangchienluoc != 0 )
					ptth_chienluoc = td_chienluoc * 100 / hangchienluoc;
				
				double ptth_dactri = 0;
				if( hangdactri != 0 )
					ptth_dactri = td_dactri * 100 / hangdactri;
				
				double ptth_tongdskhoan = 0;
				if( tongdskhoan != 0 )
					ptth_tongdskhoan = td_tongdskhoan * 100 / tongdskhoan;
				
				double thuong_kpi1 = 0;
				double thuong_kpi2 = 0;
				
				//LHQ= K1*T1+K2*T2
				double T1 = 0;
				double K1 = 0;
				double T2 = 0;
				double K2 = 0;
				double T = 0;
				
				if( hangchienluoc != 0 )
					T1 = thuchien_kpi1 * 100 / hangchienluoc;
				
				if ( hangdactri != 0 )
					T2 = thuchien_kpi2 * 100 / hangdactri;
				
				T = ( thuchien_kpi1 + thuchien_kpi2 ) * 100 / ( hangchienluoc + hangdactri );
				if( T >= 100 )
				{
					thuong_kpi1 = 12000000;
					thuong_kpi2 = 6000000;
				}
				else
				{
					thuong_kpi1 = 10000000;
					thuong_kpi2 = 5000000;
				}
				
				K1 = thuong_kpi1;
				K2 = thuong_kpi2;
				double lhq = ( K1 * T1 / 100.0 ) + ( K2 * T2 / 100.0 ) ;
				
				label = new Label(0, j, Integer.toString(stt), cellFormat2);
				sheet.addCell(label);

				label = new Label(1, j, rs.getString("khuvuc"), cellFormat2);
				sheet.addCell(label);

				label = new Label(2, j, rs.getString("tinhthanh"), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(3, j, rs.getString("maFAST"), cellFormat2);
				sheet.addCell(label);

				label = new Label(4, j, rs.getString("ten"), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(5, j, rs.getString("vitri"), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(6, j, rs.getString("kbh"), cellFormat2);
				sheet.addCell(label);

				//Chỉ tiêu
				label = new Label(7, j, formater.format(hangchienluoc), cellFormat2);
				sheet.addCell(label);

				label = new Label(8, j, formater.format(hangdactri), cellFormat2);
				sheet.addCell(label);

				label = new Label(9, j, formater.format(tongdskhoan), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(10, j, formater.format(kpichienluoc), cellFormat2);
				sheet.addCell(label);

				label = new Label(11, j, formater.format(kpidactri), cellFormat2);
				sheet.addCell(label);

				//Thực đạt
				label = new Label(12, j, formater.format(td_chienluoc), cellFormat2);
				sheet.addCell(label);

				label = new Label(13, j, formater.format(td_dactri), cellFormat2);
				sheet.addCell(label);

				label = new Label(14, j, formater.format(td_tongdskhoan), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(15, j, formater.format(td_dactri_tren_ds), cellFormat2);
				sheet.addCell(label);

				label = new Label(16, j, formater.format(thuchien_kpi1), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(17, j, formater.format(thuchien_kpi2), cellFormat2);
				sheet.addCell(label);

				
				//Tỉ lệ thực hiện
				label = new Label(18, j, formater.format(ptth_chienluoc), cellFormat2);
				sheet.addCell(label);

				label = new Label(19, j, formater.format(ptth_dactri), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(20, j, formater.format(ptth_tongdskhoan), cellFormat2);
				sheet.addCell(label);

				
				//Thưởng
				label = new Label(21, j, formater.format(thuong_kpi1), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(22, j, formater.format(thuong_kpi2), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(23, j, formater.format(lhq), cellFormat2);
				sheet.addCell(label);

				j++;	
				stt++;
			}
			
			for(int x = 1; x <= 25; x++)
			{
			    CellView cell = sheet.getColumnView(x);
			    cell.setAutosize(true);
			    sheet.setColumnView(x, cell);
			}
			
			w.write();
			w.close();
			db.shutDown();
		} 
		catch (Exception e)
		{
			System.out.println("Error Cac Ban : " + e.getMessage());
			e.printStackTrace();
		}
	}

	
	/**************    CHỈ TIÊU ETC     *****************/
	private void XuatExcel_ETC(HttpServletResponse response, String query, String thang, String nam) throws IOException
	{
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=ThucDatVaChiTieu.xls");
			
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());
			WritableSheet sheet = w.createSheet("Sheet1", 0);
			
			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 18);
			cellFont.setColour(Colour.BLACK);
			cellFont.setBoldStyle(jxl.write.WritableFont.BOLD);
			
			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

			sheet.addCell(new Label(0, 0, "Thực đạt chỉ tiêu ETC ", cellFormat));
			
			sheet.addCell(new Label(0, 1, "Tháng: "));
			sheet.addCell(new Label(1, 1, thang));

			sheet.addCell(new Label(0, 2, "Năm: "));
			sheet.addCell(new Label(1, 2, nam));

			cellFont = new WritableFont(WritableFont.TIMES, 12);
			cellFont.setColour(Colour.BLACK);
			
			cellFormat = new WritableCellFormat(cellFont);
			cellFormat.setBackground(jxl.format.Colour.GREY_25_PERCENT);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setAlignment(Alignment.CENTRE);

			sheet.addCell(new Label(0, 4, "STT", cellFormat));
			sheet.addCell(new Label(1, 4, "Khu vực", cellFormat));
			sheet.addCell(new Label(2, 4, "Tỉnh /TP", cellFormat));
			sheet.addCell(new Label(3, 4, "Mã NV", cellFormat));
			sheet.addCell(new Label(4, 4, "Tên NV", cellFormat));
			
			sheet.addCell(new Label(5, 4, "Vị trí", cellFormat));
			sheet.addCell(new Label(6, 4, "DS PK tối thiểu", cellFormat));
			
			sheet.mergeCells(7, 4, 9, 4);
			sheet.addCell(new Label(7, 4, "Chỉ tiêu", cellFormat));
			
			sheet.mergeCells(10, 4, 12, 4);
			sheet.addCell(new Label(10, 4, "Thực hiện", cellFormat));
			
			sheet.mergeCells(13, 4, 15, 4);
			sheet.addCell(new Label(13, 4, "% Thực hiện / Kế hoạch", cellFormat));
			
			sheet.mergeCells(16, 4, 17, 4);
			sheet.addCell(new Label(16, 4, "Thưởng", cellFormat));
			
			sheet.mergeCells(0, 5, 6, 5);
			sheet.addCell(new Label(0, 5, " ", cellFormat));
			
			
			//Chỉ tiêu
			sheet.addCell(new Label(7, 5, "CLC", cellFormat));
			sheet.addCell(new Label(8, 5, "INS", cellFormat));
			sheet.addCell(new Label(9, 5, "Tổng DS khoán", cellFormat));
			
			//Thực đạt
			sheet.addCell(new Label(10, 5, "CLC", cellFormat));
			sheet.addCell(new Label(11, 5, "INS", cellFormat));
			sheet.addCell(new Label(12, 5, "Tổng DS khoán", cellFormat));
			
			//Tỉ lệ
			sheet.addCell(new Label(13, 5, "CLC", cellFormat));
			sheet.addCell(new Label(14, 5, "INS", cellFormat));
			sheet.addCell(new Label(15, 5, "Tổng DS khoán", cellFormat));
			
			//Thưởng
			sheet.addCell(new Label(16, 5, "LHQ 1", cellFormat));
			sheet.addCell(new Label(17, 5, "LHQ 2", cellFormat));
			
			// set style to cell data
			WritableCellFormat cellFormat2 = new WritableCellFormat();

			cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			dbutils db = new dbutils();
			ResultSet rs = db.get(query);
			
			int j = 6;
			int stt = 1;
			NumberFormat formater = new DecimalFormat("#,###,###");
			while (rs.next())
			{
				Label label;
				
				//Chỉ tiêu
				double dsPktoithieu = rs.getDouble("dsPktoithieu");
				double CLC = rs.getDouble("CLC");
				double INS = rs.getDouble("INS");
				double tongdskhoan = rs.getDouble("tongdskhoan");
				
				//Thực đạt
				double td_clc = rs.getDouble("td_clc");
				double td_ins = rs.getDouble("td_ins");
				double td_tongdskhoan = td_clc + td_ins;
				
				//Phần trăm thực hiện
				double ptth_clc = 0;
				if( CLC != 0 )
					ptth_clc = td_clc * 100 / CLC;
				
				double ptth_ins = 0;
				if( INS != 0 )
					ptth_ins = td_ins * 100 / INS;
				
				double ptth_tongdskhoan = 0;
				if( tongdskhoan != 0 )
					ptth_tongdskhoan = td_tongdskhoan * 100 / tongdskhoan;
				
				
				double LHQ1 = 0;
				double LHQ2 = 0;
				
				//B1. tính LHQ1
				//A=% DSTH/DSKH
				//T: DOANH SỐ THỰC HIỆN
				//T1: DOANH SỐ THỰC HIỆN KÊNH PHÒNG KHÁM
				//T2: DOANH SỐ THỰC HIỆN KÊNH BẢO HIỂM
				//DSTT PK: DOANH SỐ TỐI THIỂU

				if( td_clc < dsPktoithieu )
				{
					LHQ1 = td_clc * 2 / 100.0;
				}
				else
				{
					LHQ1 = td_clc * 4.5 / 100.0;
				}
				
				//B2 tính LHQ2
				if( ptth_tongdskhoan < 100 )
				{
					LHQ2 = ( td_ins * 1.2 / 100.0 ) * 80 / 100.0;
				}
				else
				{
					LHQ2 = td_ins * 1.2 / 100.0;
				}
				
				label = new Label(0, j, Integer.toString(stt), cellFormat2);
				sheet.addCell(label);

				label = new Label(1, j, rs.getString("khuvuc"), cellFormat2);
				sheet.addCell(label);

				label = new Label(2, j, rs.getString("tinhthanh"), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(3, j, rs.getString("maFAST"), cellFormat2);
				sheet.addCell(label);

				label = new Label(4, j, rs.getString("ten"), cellFormat2);
				sheet.addCell(label);

				label = new Label(5, j, rs.getString("vitri"), cellFormat2);
				sheet.addCell(label);

				
				//Chỉ tiêu
				label = new Label(6, j, formater.format(dsPktoithieu), cellFormat2);
				sheet.addCell(label);

				label = new Label(7, j, formater.format(CLC), cellFormat2);
				sheet.addCell(label);

				label = new Label(8, j, formater.format(INS), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(9, j, formater.format(tongdskhoan), cellFormat2);
				sheet.addCell(label);

				//Thực đạt
				label = new Label(10, j, formater.format(td_clc), cellFormat2);
				sheet.addCell(label);

				label = new Label(11, j, formater.format(td_ins), cellFormat2);
				sheet.addCell(label);

				label = new Label(12, j, formater.format(td_tongdskhoan), cellFormat2);
				sheet.addCell(label);
				

				//Tỉ lệ thực hiện
				label = new Label(13, j, formater.format(ptth_clc), cellFormat2);
				sheet.addCell(label);

				label = new Label(14, j, formater.format(ptth_ins), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(15, j, formater.format(ptth_tongdskhoan), cellFormat2);
				sheet.addCell(label);

				
				//Thưởng
				label = new Label(16, j, formater.format(LHQ1), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(17, j, formater.format(LHQ2), cellFormat2);
				sheet.addCell(label);

				j++;	
				stt++;
			}
			
			for(int x = 1; x <= 25; x++)
			{
			    CellView cell = sheet.getColumnView(x);
			    cell.setAutosize(true);
			    sheet.setColumnView(x, cell);
			}
			
			w.write();
			w.close();
			db.shutDown();
		} 
		catch (Exception e)
		{
			System.out.println("Error Cac Ban : " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void XuatExcelGSBH_ETC(HttpServletResponse response, String query, String thang, String nam) throws IOException
	{
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=ThucDatVaChiTieu.xls");
			
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());
			WritableSheet sheet = w.createSheet("Sheet1", 0);
			
			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 18);
			cellFont.setColour(Colour.BLACK);
			cellFont.setBoldStyle(jxl.write.WritableFont.BOLD);
			
			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

			sheet.addCell(new Label(0, 0, "Thực đạt chỉ tiêu ETC ", cellFormat));
			
			sheet.addCell(new Label(0, 1, "Tháng: "));
			sheet.addCell(new Label(1, 1, thang));

			sheet.addCell(new Label(0, 2, "Năm: "));
			sheet.addCell(new Label(1, 2, nam));

			cellFont = new WritableFont(WritableFont.TIMES, 12);
			cellFont.setColour(Colour.BLACK);
			
			cellFormat = new WritableCellFormat(cellFont);
			cellFormat.setBackground(jxl.format.Colour.GREY_25_PERCENT);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setAlignment(Alignment.CENTRE);

			sheet.addCell(new Label(0, 4, "STT", cellFormat));
			sheet.addCell(new Label(1, 4, "Khu vực", cellFormat));
			sheet.addCell(new Label(2, 4, "Tỉnh /TP", cellFormat));
			sheet.addCell(new Label(3, 4, "Mã NV", cellFormat));
			sheet.addCell(new Label(4, 4, "Tên NV", cellFormat));
			
			sheet.addCell(new Label(5, 4, "Vị trí", cellFormat));
			sheet.addCell(new Label(6, 4, "DS PK tối thiểu", cellFormat));
			
			sheet.mergeCells(7, 4, 9, 4);
			sheet.addCell(new Label(7, 4, "Chỉ tiêu", cellFormat));
			
			sheet.mergeCells(10, 4, 12, 4);
			sheet.addCell(new Label(10, 4, "Thực hiện", cellFormat));
			
			sheet.mergeCells(13, 4, 15, 4);
			sheet.addCell(new Label(13, 4, "% Thực hiện / Kế hoạch", cellFormat));
			
			sheet.mergeCells(16, 4, 17, 4);
			sheet.addCell(new Label(16, 4, "Thưởng", cellFormat));
			
			sheet.mergeCells(0, 5, 6, 5);
			sheet.addCell(new Label(0, 5, " ", cellFormat));
			
			
			//Chỉ tiêu
			sheet.addCell(new Label(7, 5, "CLC", cellFormat));
			sheet.addCell(new Label(8, 5, "INS", cellFormat));
			sheet.addCell(new Label(9, 5, "Tổng DS khoán", cellFormat));
			
			//Thực đạt
			sheet.addCell(new Label(10, 5, "CLC", cellFormat));
			sheet.addCell(new Label(11, 5, "INS", cellFormat));
			sheet.addCell(new Label(12, 5, "Tổng DS khoán", cellFormat));
			
			//Tỉ lệ
			sheet.addCell(new Label(13, 5, "CLC", cellFormat));
			sheet.addCell(new Label(14, 5, "INS", cellFormat));
			sheet.addCell(new Label(15, 5, "Tổng DS khoán", cellFormat));
			
			//Thưởng
			sheet.addCell(new Label(16, 5, "LHQ 1", cellFormat));
			sheet.addCell(new Label(17, 5, "LHQ 2", cellFormat));
			
			// set style to cell data
			WritableCellFormat cellFormat2 = new WritableCellFormat();

			cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			dbutils db = new dbutils();
			ResultSet rs = db.get(query);
			
			int j = 6;
			int stt = 1;
			NumberFormat formater = new DecimalFormat("#,###,###");
			while (rs.next())
			{
				Label label;
				
				//Chỉ tiêu
				double dsPktoithieu = rs.getDouble("dsPktoithieu");
				double CLC = rs.getDouble("CLC");
				double INS = rs.getDouble("INS");
				double tongdskhoan = rs.getDouble("tongdskhoan");
				
				//Thực đạt
				double td_clc = rs.getDouble("td_clc");
				double td_ins = rs.getDouble("td_ins");
				double td_tongdskhoan = td_clc + td_ins;
				
				//Phần trăm thực hiện
				double ptth_clc = 0;
				if( CLC != 0 )
					ptth_clc = td_clc * 100 / CLC;
				
				double ptth_ins = 0;
				if( INS != 0 )
					ptth_ins = td_ins * 100 / INS;
				
				double ptth_tongdskhoan = 0;
				if( tongdskhoan != 0 )
					ptth_tongdskhoan = td_tongdskhoan * 100 / tongdskhoan;
				
				
				double LHQ1 = 0;
				double LHQ2 = 0;
				
				//B1. tính LHQ1
				//A=% DSTH/DSKH
				//T: DOANH SỐ THỰC HIỆN
				//T1: DOANH SỐ THỰC HIỆN KÊNH PHÒNG KHÁM
				//T2: DOANH SỐ THỰC HIỆN KÊNH BẢO HIỂM
				//DSTT PK: DOANH SỐ TỐI THIỂU
				
				double T = td_clc + td_ins;
				if( T < 700000000 )
				{
					LHQ1 =  1000000;
				}
				else if( T >= 700000000 && T < 900000000 )
				{
					LHQ1 = 2000000;
				}
				else if( T >= 900000000 && T < 1100000000 )
				{
					LHQ1 = 3000000;
				}
				else if( T >= 1100000000 && T < 1400000000 )
				{
					LHQ1 = 4000000;
				}
				else if( T >= 1400000000  )
				{
					LHQ1 = 5000000;
				}
				
				//B2 tính LHQ2
				if( ptth_tongdskhoan < 100 )
				{
					LHQ2 = ( ( td_clc * 1.5 / 100.0 ) + ( td_ins * 0.5 / 100.0 ) ) * 80 / 100.0;
				}
				else
				{
					LHQ2 = ( td_clc * 1.5 / 100.0 ) + ( td_ins * 0.5 / 100.0 );
				}
				
				label = new Label(0, j, Integer.toString(stt), cellFormat2);
				sheet.addCell(label);

				label = new Label(1, j, rs.getString("khuvuc"), cellFormat2);
				sheet.addCell(label);

				label = new Label(2, j, rs.getString("tinhthanh"), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(3, j, rs.getString("maFAST"), cellFormat2);
				sheet.addCell(label);

				label = new Label(4, j, rs.getString("ten"), cellFormat2);
				sheet.addCell(label);

				label = new Label(5, j, rs.getString("vitri"), cellFormat2);
				sheet.addCell(label);

				
				//Chỉ tiêu
				label = new Label(6, j, formater.format(dsPktoithieu), cellFormat2);
				sheet.addCell(label);

				label = new Label(7, j, formater.format(CLC), cellFormat2);
				sheet.addCell(label);

				label = new Label(8, j, formater.format(INS), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(9, j, formater.format(tongdskhoan), cellFormat2);
				sheet.addCell(label);

				//Thực đạt
				label = new Label(10, j, formater.format(td_clc), cellFormat2);
				sheet.addCell(label);

				label = new Label(11, j, formater.format(td_ins), cellFormat2);
				sheet.addCell(label);

				label = new Label(12, j, formater.format(td_tongdskhoan), cellFormat2);
				sheet.addCell(label);
				

				//Tỉ lệ thực hiện
				label = new Label(13, j, formater.format(ptth_clc), cellFormat2);
				sheet.addCell(label);

				label = new Label(14, j, formater.format(ptth_ins), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(15, j, formater.format(ptth_tongdskhoan), cellFormat2);
				sheet.addCell(label);

				
				//Thưởng
				label = new Label(16, j, formater.format(LHQ1), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(17, j, formater.format(LHQ2), cellFormat2);
				sheet.addCell(label);

				j++;	
				stt++;
			}
			
			for(int x = 1; x <= 25; x++)
			{
			    CellView cell = sheet.getColumnView(x);
			    cell.setAutosize(true);
			    sheet.setColumnView(x, cell);
			}
			
			w.write();
			w.close();
			db.shutDown();
		} 
		catch (Exception e)
		{
			System.out.println("Error Cac Ban : " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void XuatExcelASM_ETC(HttpServletResponse response, String query, String thang, String nam) throws IOException
	{
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=ThucDatVaChiTieu.xls");
			
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());
			WritableSheet sheet = w.createSheet("Sheet1", 0);
			
			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 18);
			cellFont.setColour(Colour.BLACK);
			cellFont.setBoldStyle(jxl.write.WritableFont.BOLD);
			
			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

			sheet.addCell(new Label(0, 0, "Thực đạt chỉ tiêu ETC ", cellFormat));
			
			sheet.addCell(new Label(0, 1, "Tháng: "));
			sheet.addCell(new Label(1, 1, thang));

			sheet.addCell(new Label(0, 2, "Năm: "));
			sheet.addCell(new Label(1, 2, nam));

			cellFont = new WritableFont(WritableFont.TIMES, 12);
			cellFont.setColour(Colour.BLACK);
			
			cellFormat = new WritableCellFormat(cellFont);
			cellFormat.setBackground(jxl.format.Colour.GREY_25_PERCENT);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setAlignment(Alignment.CENTRE);

			sheet.addCell(new Label(0, 4, "STT", cellFormat));
			sheet.addCell(new Label(1, 4, "Khu vực", cellFormat));
			sheet.addCell(new Label(2, 4, "Tỉnh /TP", cellFormat));
			sheet.addCell(new Label(3, 4, "Mã NV", cellFormat));
			sheet.addCell(new Label(4, 4, "Tên NV", cellFormat));
			
			sheet.addCell(new Label(5, 4, "Vị trí", cellFormat));
			sheet.addCell(new Label(6, 4, "DS PK tối thiểu", cellFormat));
			
			sheet.mergeCells(7, 4, 9, 4);
			sheet.addCell(new Label(7, 4, "Chỉ tiêu", cellFormat));
			
			sheet.mergeCells(10, 4, 12, 4);
			sheet.addCell(new Label(10, 4, "Thực hiện", cellFormat));
			
			sheet.mergeCells(13, 4, 15, 4);
			sheet.addCell(new Label(13, 4, "% Thực hiện / Kế hoạch", cellFormat));
			
			sheet.mergeCells(16, 4, 17, 4);
			sheet.addCell(new Label(16, 4, "Thưởng", cellFormat));
			
			sheet.mergeCells(0, 5, 6, 5);
			sheet.addCell(new Label(0, 5, " ", cellFormat));
			
			
			//Chỉ tiêu
			sheet.addCell(new Label(7, 5, "CLC", cellFormat));
			sheet.addCell(new Label(8, 5, "INS", cellFormat));
			sheet.addCell(new Label(9, 5, "Tổng DS khoán", cellFormat));
			
			//Thực đạt
			sheet.addCell(new Label(10, 5, "CLC", cellFormat));
			sheet.addCell(new Label(11, 5, "INS", cellFormat));
			sheet.addCell(new Label(12, 5, "Tổng DS khoán", cellFormat));
			
			//Tỉ lệ
			sheet.addCell(new Label(13, 5, "CLC", cellFormat));
			sheet.addCell(new Label(14, 5, "INS", cellFormat));
			sheet.addCell(new Label(15, 5, "Tổng DS khoán", cellFormat));
			
			//Thưởng
			sheet.addCell(new Label(16, 5, "LHQ 1", cellFormat));
			sheet.addCell(new Label(17, 5, "LHQ 2", cellFormat));
			
			// set style to cell data
			WritableCellFormat cellFormat2 = new WritableCellFormat();

			cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			dbutils db = new dbutils();
			ResultSet rs = db.get(query);
			
			int j = 6;
			int stt = 1;
			NumberFormat formater = new DecimalFormat("#,###,###");
			while (rs.next())
			{
				Label label;
				
				//Chỉ tiêu
				double dsPktoithieu = rs.getDouble("dsPktoithieu");
				double CLC = rs.getDouble("CLC");
				double INS = rs.getDouble("INS");
				double tongdskhoan = rs.getDouble("tongdskhoan");
				
				//Thực đạt
				double td_clc = rs.getDouble("td_clc");
				double td_ins = rs.getDouble("td_ins");
				double td_tongdskhoan = td_clc + td_ins;
				
				//Phần trăm thực hiện
				double ptth_clc = 0;
				if( CLC != 0 )
					ptth_clc = td_clc * 100 / CLC;
				
				double ptth_ins = 0;
				if( INS != 0 )
					ptth_ins = td_ins * 100 / INS;
				
				double ptth_tongdskhoan = 0;
				if( tongdskhoan != 0 )
					ptth_tongdskhoan = td_tongdskhoan * 100 / tongdskhoan;
				
				
				double LHQ1 = 0;
				double LHQ2 = 0;
				
				//B1. tính LHQ1
				//A=% DSTH/DSKH
				//T: DOANH SỐ THỰC HIỆN
				//T1: DOANH SỐ THỰC HIỆN KÊNH PHÒNG KHÁM
				//T2: DOANH SỐ THỰC HIỆN KÊNH BẢO HIỂM
				//DSTT PK: DOANH SỐ TỐI THIỂU
				
				double T = td_clc + td_ins;
				if( T < 2500000000.0 )
				{
					LHQ1 =  10000000;
				}
				else if( T >= 2500000000.0 && T < 3500000000.0 )
				{
					LHQ1 = 12000000;
				}
				else if( T >= 3500000000.0 )
				{
					LHQ1 = 15000000;
				}
				
				//B2 tính LHQ2
				if( ptth_tongdskhoan < 100 )
				{
					LHQ2 = ( T * 0.5 / 100.0 ) * 90 / 100.0;
				}
				else
				{
					LHQ2 = T * 0.5 / 100.0;
				}
				
				label = new Label(0, j, Integer.toString(stt), cellFormat2);
				sheet.addCell(label);

				label = new Label(1, j, rs.getString("khuvuc"), cellFormat2);
				sheet.addCell(label);

				label = new Label(2, j, rs.getString("tinhthanh"), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(3, j, rs.getString("maFAST"), cellFormat2);
				sheet.addCell(label);

				label = new Label(4, j, rs.getString("ten"), cellFormat2);
				sheet.addCell(label);

				label = new Label(5, j, rs.getString("vitri"), cellFormat2);
				sheet.addCell(label);

				
				//Chỉ tiêu
				label = new Label(6, j, formater.format(dsPktoithieu), cellFormat2);
				sheet.addCell(label);

				label = new Label(7, j, formater.format(CLC), cellFormat2);
				sheet.addCell(label);

				label = new Label(8, j, formater.format(INS), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(9, j, formater.format(tongdskhoan), cellFormat2);
				sheet.addCell(label);

				//Thực đạt
				label = new Label(10, j, formater.format(td_clc), cellFormat2);
				sheet.addCell(label);

				label = new Label(11, j, formater.format(td_ins), cellFormat2);
				sheet.addCell(label);

				label = new Label(12, j, formater.format(td_tongdskhoan), cellFormat2);
				sheet.addCell(label);
				

				//Tỉ lệ thực hiện
				label = new Label(13, j, formater.format(ptth_clc), cellFormat2);
				sheet.addCell(label);

				label = new Label(14, j, formater.format(ptth_ins), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(15, j, formater.format(ptth_tongdskhoan), cellFormat2);
				sheet.addCell(label);

				
				//Thưởng
				label = new Label(16, j, formater.format(LHQ1), cellFormat2);
				sheet.addCell(label);
				
				label = new Label(17, j, formater.format(LHQ2), cellFormat2);
				sheet.addCell(label);

				j++;	
				stt++;
			}
			
			for(int x = 1; x <= 25; x++)
			{
			    CellView cell = sheet.getColumnView(x);
			    cell.setAutosize(true);
			    sheet.setColumnView(x, cell);
			}
			
			w.write();
			w.close();
			db.shutDown();
		} 
		catch (Exception e)
		{
			System.out.println("Error Cac Ban : " + e.getMessage());
			e.printStackTrace();
		}
	}

	
}
