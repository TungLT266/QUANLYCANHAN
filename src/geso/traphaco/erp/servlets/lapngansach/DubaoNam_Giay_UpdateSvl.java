package geso.traphaco.erp.servlets.lapngansach;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.lapngansach.IDubaokinhdoanhNam_Giay;

import geso.traphaco.erp.beans.lapngansach.imp.DubaokinhdoanhNam_Giay;
import geso.traphaco.erp.db.sql.dbutils;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.oreilly.servlet.MultipartRequest;
import com.aspose.cells.FileFormatType;



public class DubaoNam_Giay_UpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "C:\\upload\\excel\\";

	public DubaoNam_Giay_UpdateSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} 
		else
		{
			session.setMaxInactiveInterval(30000);

			Utility util = new Utility();

			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);
			String ctyId = (String)session.getAttribute("congtyId");
			
			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));
			String id = util.getId(querystring);
			
			DubaokinhdoanhNam_Giay dbkdBean = new DubaokinhdoanhNam_Giay(id);
			dbkdBean.setCtyId(ctyId);
			
			dbkdBean.setUserId(userId);
			String nextJSP;

			if (request.getQueryString().indexOf("display") >= 0)
			{
				dbkdBean.init();
				nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DuBao_Giay_Display.jsp";
			} 
			else
			{
				dbkdBean.init();
				nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DubaoNam_Giay_Update.jsp";
			}
			session.setAttribute("dbkdBean", dbkdBean);
			response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IDubaokinhdoanhNam_Giay dbkdBean;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html ; charset=UTF-8");
		String contentType = request.getContentType();
		HttpSession session = request.getSession();
		OutputStream out = response.getOutputStream();
		String userId = (String) session.getAttribute("userId");
		String ctyId = (String)session.getAttribute("congtyId");
		
		Utility util = new Utility();
		String nextJSP="";
		String action = "";
		String mohinh = "";
		
		//Khi upload file file phai dung MultipartRequest de lay parameter
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			MultipartRequest multi = new MultipartRequest(request, UPLOAD_DIRECTORY, 20000000, "UTF-8");
			action = util.antiSQLInspection(multi.getParameter("action"));
			if (action == null)
				action = "";
			
			System.out.println("Action Request encrypt: " + action);
			String id = util.antiSQLInspection(multi.getParameter("id"));
			System.out.println("________________ID LA: " + id);
			if (id == null)
			{
				dbkdBean = new DubaokinhdoanhNam_Giay();
			} 
			else
			{
				dbkdBean = new DubaokinhdoanhNam_Giay(id);
			}
			
			dbkdBean.setCtyId(ctyId);
			dbkdBean.setUserId(userId);
			dbkdBean.setId(id);
			getRequestEcrypt(util, dbkdBean, multi);
			
			Enumeration files = multi.getFileNames();
			String filename = "";
			while (files.hasMoreElements())
			{
				String name = (String) files.nextElement();
				filename = multi.getFilesystemName(name);
				//System.out.println("File:  " + UPLOAD_DIRECTORY + filename);
			}
			
			System.out.println("1____READ EXCEL TOI DAY, FILE NAME......" + filename);
			if (filename!=null&&filename.length() > 0)
			{
				System.out.println("___READ EXCEL FILE: ");
				
				readExcel(dbkdBean, UPLOAD_DIRECTORY + filename, response);
			}
			
			dbkdBean.init();
			//dbkdBean.createRs();
			nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DubaoNam_Giay_Update.jsp";
			session.setAttribute("dbkdBean", dbkdBean);
			response.sendRedirect(nextJSP);
		} 
		else
		{
			action = util.antiSQLInspection(request.getParameter("action"));
			if (action == null)
				action = "";
			System.out.println("Action " + action);
			String id = util.antiSQLInspection(request.getParameter("id"));
						
			
			if (id == null)
			{
				dbkdBean = new DubaokinhdoanhNam_Giay();				
			} 
			else
			{
				dbkdBean = new DubaokinhdoanhNam_Giay(id);
			}
			
			dbkdBean.setCtyId(ctyId);
			dbkdBean.setUserId(userId);
			
			//Lấy thông tin 
			getRequest(util, dbkdBean, request);
		
			if (action.equals("new"))
			{
				if (!dbkdBean.CreateDubao(request))
				{
					dbkdBean.setMsg("Tạo dự báo kinh doanh không thành công");
				} 
				else
				{
					dbkdBean.setMsg("Cập nhật dự báo kinh doanh thành công");
				}
			} 
			else
			{
				if(action.equals("exportExcel"))
				{

			    	try
				    {
				    	response.setContentType("application/vnd.ms-excel");
				        response.setHeader("Content-Disposition", "attachment; filename=Lapngansach_dubaokinhdoanh.xls");
		
				        dbkdBean.setCtyId(ctyId);
						
				        dbkdBean.setUserId(userId);
		
				        dbkdBean.init();
		
				        Workbook workbook = new Workbook();
				    	
					    CreateHeader(workbook, dbkdBean);
					    CreateData(workbook, dbkdBean);
					
					     //Saving the Excel file
					     workbook.save(out);
				    }
				    catch (Exception ex){ }
			    	
				    dbkdBean.setUserId(userId);
				    dbkdBean.setId(id);
				    dbkdBean.init();
					session.setAttribute("dbkdBean", dbkdBean);
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DubaoNam_Giay_Update.jsp";
					response.sendRedirect(nextJSP);		    		
					return;
			    
				}
				else
				{
					if (!dbkdBean.update(request))
					{
						dbkdBean.setMsg("Cập nhật dự báo kinh doanh không thành công");
					}
					else
					{
						dbkdBean.setMsg("Cập nhật dự báo kinh doanh thành công");
					}
				}
			}
			
			
			dbkdBean.init();
			//dbkdBean.createRs();
			nextJSP = "/TraphacoHYERP/pages/Erp/Erp_DubaoNam_Giay_Update.jsp";
			session.setAttribute("dbkdBean", dbkdBean);
			response.sendRedirect(nextJSP);
			
		}
		
	}

	void getRequest(Utility util, IDubaokinhdoanhNam_Giay dbkdBean, HttpServletRequest request)
	{
		String chungloai = util.antiSQLInspection(request.getParameter("chungloai"));
		if (chungloai == null)
			chungloai = "";
		dbkdBean.setChungloai(chungloai);

		String nhanhang = util.antiSQLInspection(request.getParameter("nhanhang"));
		if (nhanhang == null)
			nhanhang = "";
		dbkdBean.setNhanhang(nhanhang);

		String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
		if (dvkdId == null)
			dvkdId = "";
		dbkdBean.setDvkdId(dvkdId);

		String kbhId = util.antiSQLInspection(request.getParameter("kbhId"));
		if (kbhId == null)
			kbhId = "";
		dbkdBean.setKenhId(kbhId);

		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		dbkdBean.setDiengiai(diengiai);
		
		String nam = util.antiSQLInspection(request.getParameter("nam"));

		if (nam == null)
			nam = "";
		dbkdBean.setNam(Integer.parseInt(nam));

		dbkdBean.setNgaytonkho("0");

		String[] spIds = request.getParameterValues("spId");
		dbkdBean.setSanPhamIds(spIds);

		String nsId = util.antiSQLInspection(request.getParameter("nsId"));
		
		dbkdBean.setNsId(nsId) ;

	}

	void getRequestEcrypt(Utility util, IDubaokinhdoanhNam_Giay dbkdBean, MultipartRequest request)
	{

		String chungloai = util.antiSQLInspection(request.getParameter("chungloai"));
		if (chungloai == null)
			chungloai = "";
		dbkdBean.setChungloai(chungloai);

		String nhanhang = util.antiSQLInspection(request.getParameter("nhanhang"));
		if (nhanhang == null)
			nhanhang = "";
		dbkdBean.setNhanhang(nhanhang);

		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		dbkdBean.setDiengiai(diengiai);

		String filename = request.getParameter("filename");
		if (filename == null)
			filename = "";
	
		String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
		if (dvkdId == null)
			dvkdId = "";
		dbkdBean.setDvkdId(dvkdId);

		String kbhId = util.antiSQLInspection(request.getParameter("kbhId"));
		if (kbhId == null)
			kbhId = "";
		dbkdBean.setKenhId(kbhId);

		String nam = util.antiSQLInspection(request.getParameter("nam"));

		if (nam == null)
			nam = "";
		dbkdBean.setNam(Integer.parseInt(nam));


		String[] spIds = request.getParameterValues("spId");
		dbkdBean.setSanPhamIds(spIds);

		String nsId = util.antiSQLInspection(request.getParameter("nsId"));
		
		dbkdBean.setNsId(nsId) ;

	}
	
	private void CreateHeader(Workbook workbook, IDubaokinhdoanhNam_Giay dbdkBean) 
	{	
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	   	    
	    Cell cell = cells.getCell("A1"); 
	    cell.setValue("Lập ngân sách - Dự báo kinh doanh năm " + dbdkBean.getNam());
	   
	    cell = cells.getCell("A3");
	    cell.setValue("Ngày xuất dữ liệu: " + this.getDateTime());
	    //cell = cells.getCell("A4");
	    //cell.setValue("User:  " + UserName);
	    
	    //tieu de
	    cell = cells.getCell("A8");
	    cell.setValue("Mã sản phẩm");
	    
	    cell = cells.getCell("B8");
	    cell.setValue("Tên sản phẩm");
	    
	    cell = cells.getCell("C8");
	    cell.setValue("Đơn vị");
	
	    cell = cells.getCell("D8");
	    cell.setValue("T. 1");
	    cell = cells.getCell("E8");
	    cell.setValue("T. 2");
	    cell = cells.getCell("F8");
	    cell.setValue("T. 3");
	    cell = cells.getCell("G8");
	    cell.setValue("T. 4");
	    cell = cells.getCell("H8");
	    cell.setValue("T. 5");
	    cell = cells.getCell("I8");
	    cell.setValue("T. 6");
	    cell = cells.getCell("J8");
	    cell.setValue("T. 7");
	    cell = cells.getCell("K8");
	    cell.setValue("T. 8");
	    cell = cells.getCell("L8");
	    cell.setValue("T. 9");
	    cell = cells.getCell("M8");
	    cell.setValue("T. 10");
	    cell = cells.getCell("N8");
	    cell.setValue("T. 11");
	    cell = cells.getCell("O8");
	    cell.setValue("T. 12");
	
	    
	    worksheet.setName("Dự báo kinh doanh năm");
	}
	
	private void CreateData(Workbook workbook, IDubaokinhdoanhNam_Giay dbdkBean) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    
		dbutils db = new dbutils();
		int row = 9;
		String query = "";
		
		for(int i = 0; i < 12; i++)
		{
			if(dbdkBean.getId().length() > 0)
			{
				
				row = 9;
				query =	"SELECT	DISTINCT SP.PK_SEQ AS SPID, SP.TEN AS SPTEN, DVDL.DONVI, " +
						"DUBAOSP.NAM, DUBAOSP.THANG,  " +								
						"ISNULL(DUBAOSP.DUKIENBAN, '0') AS DUKIENBAN " +
						"FROM ERP_LAPNGANSACH_DUBAO DUBAO " +
						"INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM DUBAOSP on DUBAO.PK_SEQ = DUBAOSP.DUBAO_FK " +	
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = DUBAOSP.SANPHAM_FK " +	
						"INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = SP.DVDL_FK " +
						"WHERE DUBAO.PK_SEQ = '" + dbdkBean.getId() + "' AND DUBAOSP.THANG = '" + (i + 1) + "' AND DUBAOSP.NAM = '" + dbdkBean.getNam() + "' " +
						"AND DUBAO.CONGTY_FK = " + dbdkBean.getCtyId() + " ";
						 
				
				query = query + " ORDER BY SPTEN ";
				

				System.out.println("INIT SANPHAM THEO THANG: " + (i + 1) );
				System.out.println("INIT SANPHAM THEO THANG: " + query );
				ResultSet rs = db.get(query);
				
				if(rs != null)
				{
					cells.setColumnWidth(0, 14.0f);
					cells.setColumnWidth(1, 47.0f);
					cells.setColumnWidth(2, 12.0f);
					cells.setColumnWidth(3, 10.0f);
					cells.setColumnWidth(4, 10.0f);
					cells.setColumnWidth(5, 10.0f);
					cells.setColumnWidth(6, 10.0f);
					cells.setColumnWidth(7, 10.0f);
					cells.setColumnWidth(8, 10.0f);
					cells.setColumnWidth(9, 10.0f);
					cells.setColumnWidth(10, 10.0f);
					cells.setColumnWidth(11, 10.0f);
					cells.setColumnWidth(12, 10.0f);
					cells.setColumnWidth(13, 10.0f);
					cells.setColumnWidth(14, 10.0f);
					cells.setColumnWidth(15, 10.0f);
									
					Cell cell = null;

					try 
					{

						while(rs.next())
						{
							
							if(i == 0){
								String spId = "";
								if(rs.getString("SPID") != null)
									spId = rs.getString("SPID");
								
								String tensp = "";
								if(rs.getString("SPTEN") != null)
									tensp = rs.getString("SPTEN");
								
								String dv = "";
								if(rs.getString("DONVI") != null)
									dv = rs.getString("DONVI");

								double dukienban = 0;
								if(rs.getString("DUKIENBAN") != null)
									dukienban = rs.getDouble("DUKIENBAN");
								
								cell = cells.getCell("A" + Integer.toString(row));
								cell.setValue(spId);

								cell = cells.getCell("B" + Integer.toString(row));
								cell.setValue(tensp);
								cell = cells.getCell("C" + Integer.toString(row));
								cell.setValue(dv);
								
								cell = cells.getCell("D" + Integer.toString(row));
								cell.setValue(dukienban);
								row++;
							}
								
							if(i == 1){
								double dukienban = 0;
								if(rs.getString("DUKIENBAN") != null)
									dukienban = rs.getDouble("DUKIENBAN");

								cell = cells.getCell("E" + Integer.toString(row));
								cell.setValue(dukienban);		
								row++;
							}
							
							if(i == 2){
								double dukienban = 0;
								if(rs.getString("DUKIENBAN") != null)
									dukienban = rs.getDouble("DUKIENBAN");

								cell = cells.getCell("F" + Integer.toString(row));
								cell.setValue(dukienban);		
								row++;
							}

							if(i == 3){
								double dukienban = 0;
								if(rs.getString("DUKIENBAN") != null)
									dukienban = rs.getDouble("DUKIENBAN");

								cell = cells.getCell("G" + Integer.toString(row));
								cell.setValue(dukienban);		
								row++;
							}

							if(i == 4){
								double dukienban = 0;
								if(rs.getString("DUKIENBAN") != null)
									dukienban = rs.getDouble("DUKIENBAN");

								cell = cells.getCell("H" + Integer.toString(row));
								cell.setValue(dukienban);		
								row++;
							}

							if(i == 5){
								double DUKIENBAN = 0;
								if(rs.getString("DUKIENBAN") != null)
									DUKIENBAN = rs.getDouble("DUKIENBAN");

								cell = cells.getCell("I" + Integer.toString(row));
								cell.setValue(DUKIENBAN);		
								row++;
							}

							if(i == 6){
								double DUKIENBAN = 0;
								if(rs.getString("DUKIENBAN") != null)
									DUKIENBAN = rs.getDouble("DUKIENBAN");

								cell = cells.getCell("J" + Integer.toString(row));
								cell.setValue(DUKIENBAN);		
								row++;
							}

							if(i == 7){
								double DUKIENBAN = 0;
								if(rs.getString("DUKIENBAN") != null)
									DUKIENBAN = rs.getDouble("DUKIENBAN");

								cell = cells.getCell("K" + Integer.toString(row));
								cell.setValue(DUKIENBAN);		
								row++;
							}

							if(i == 8){
								double DUKIENBAN = 0;
								if(rs.getString("DUKIENBAN") != null)
									DUKIENBAN = rs.getDouble("DUKIENBAN");

								cell = cells.getCell("L" + Integer.toString(row));
								cell.setValue(DUKIENBAN);		
								row++;
							}

							if(i == 9){
								double DUKIENBAN = 0;
								if(rs.getString("DUKIENBAN") != null)
									DUKIENBAN = rs.getDouble("DUKIENBAN");

								cell = cells.getCell("M" + Integer.toString(row));
								cell.setValue(DUKIENBAN);		
								row++;
							}

							if(i == 10){
								double DUKIENBAN = 0;
								if(rs.getString("DUKIENBAN") != null)
									DUKIENBAN = rs.getDouble("DUKIENBAN");

								cell = cells.getCell("N" + Integer.toString(row));
								cell.setValue(DUKIENBAN);		
								row++;
							}

							if(i == 11){
								double DUKIENBAN = 0;
								if(rs.getString("DUKIENBAN") != null)
									DUKIENBAN = rs.getDouble("DUKIENBAN");

								cell = cells.getCell("O" + Integer.toString(row));
								cell.setValue(DUKIENBAN);		
								row++;
							}

						}
						rs.close();
					}catch(java.sql.SQLException e){}
				}		

			}
		}
	}
	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public void readExcel(IDubaokinhdoanhNam_Giay obj, String fileName, HttpServletResponse response) throws IOException
	{

		dbutils db = new dbutils();
		
		OutputStream out = response.getOutputStream();
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		
		fstream = new FileInputStream(fileName);
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007);
		
		Worksheets worksheets = workbook.getWorksheets();		

		String Id = obj.getId();
		
		System.out.println("BẮT ĐẦU ĐỌC FILE");
		
		//FILE BẮT ĐẦU CHỨA DỮ LIỆU TỪ DÒNG SỐ 9
		try
		{
			db.getConnection().setAutoCommit(false);
			String sql = "";
			
			Worksheet readSheet = worksheets.getSheet(0);
			sql = "SELECT NAM FROM ERP_LAPNGANSACH_DUBAO WHERE PK_SEQ = " + obj.getId() + " ";
			System.out.println(sql);
			ResultSet rs = db.get(sql);
			rs.next();
			String nam = rs.getString("NAM");
			rs.close();
			//LẤY SỐ DÒNG CỦA SHEET ĐANG ĐỌC. TÍNH TỪ 0.			
			int readSheetRow = readSheet.getLastRowIndex() + 1;
			
			for(int i = 8; i < readSheetRow; i++)
			{

				String spId =  readSheet.getCell(i, 0).getStringValue();
				sql = "";
				if(spId.length()> 0)
				{
					String dukienban = "0";
					
					for (int m = 1; m <= 12; m++){
					
						dukienban = readSheet.getCell(i, m + 2).getStringValue().replaceAll(",", "");
						
						sql +=	"UPDATE ERP_LAPNGANSACH_DUBAOSANPHAM set DUKIENBAN = " + dukienban + ", THANHTIEN = DONGIA*" + dukienban + " " +
								"WHERE DUBAO_FK = " + obj.getId() + " AND THANG = " + m + " AND NAM = " + nam + " " +
								"AND SANPHAM_FK = N'" + spId + "' ";
						
					}

					db.update(sql);
				}
	
			}
	
			System.out.println("KẾT THÚC ĐỌC SHEET: "+readSheet.getName());
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch(SQLException e)
		{
			
		}
			
		db.shutDown();
	
		System.out.println("KẾT THÚC ĐỌC FILE");
		
	
	}

}