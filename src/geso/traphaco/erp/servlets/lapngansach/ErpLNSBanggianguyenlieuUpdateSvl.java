package geso.traphaco.erp.servlets.lapngansach;


import geso.traphaco.erp.beans.lapngansach.ILNSBanggianguyenlieu;
import geso.traphaco.erp.beans.lapngansach.ILNSBanggianguyenlieuList;
import geso.traphaco.erp.beans.lapngansach.imp.LNSBanggianguyenlieu;
import geso.traphaco.erp.beans.lapngansach.imp.LNSBanggianguyenlieuList;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.oreilly.servlet.MultipartRequest;

 public class ErpLNSBanggianguyenlieuUpdateSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
	public ErpLNSBanggianguyenlieuUpdateSvl() {
		super();
	}   	

	private static final String UPLOAD_DIRECTORY = "C:\\upload\\excel\\";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		PrintWriter out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    String action = util.getAction(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String id = util.getId(querystring);  	
	    out.println(id);
	    
	    ILNSBanggianguyenlieu bgBean = new LNSBanggianguyenlieu();
	    String ctyId = (String)session.getAttribute("congtyId");
	    String ctyTen = (String)session.getAttribute("congtyTen");
	    bgBean.setCtyId(ctyId);
	    bgBean.setCtyTen(ctyTen);

        bgBean.setUserId(userId);
        bgBean.setId(id);
        bgBean.init();
        
        session.setAttribute("bgnlBean", bgBean);
        String nextJSP;
        
        if(action.equals("update")){
        	nextJSP = "/TraphacoHYERP/pages/Erp/Erp_LNSBangGiaNguyenLieuUpdate.jsp";
        }else{
        	nextJSP = "/TraphacoHYERP/pages/Erp/Erp_LNSBangGiaNguyenLieuDisplay.jsp";
        }
        response.sendRedirect(nextJSP);

	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String contentType = request.getContentType();
		
		OutputStream out = response.getOutputStream();

		ILNSBanggianguyenlieu bgBean = (ILNSBanggianguyenlieu) new LNSBanggianguyenlieu();
		
	    String ctyId = (String)session.getAttribute("congtyId");
	    String ctyTen = (String)session.getAttribute("congtyTen");
	    
	    bgBean.setCtyId(ctyId);
	    bgBean.setCtyTen(ctyTen);
	    
		Utility util = new Utility();
	    
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			MultipartRequest multi = new MultipartRequest(request, UPLOAD_DIRECTORY, 20000000, "UTF-8");
			String action = util.antiSQLInspection(multi.getParameter("action"));
			if (action == null)
				action = "";
			
			System.out.println("Action Request encrypt: " + action);
			String id = util.antiSQLInspection(multi.getParameter("id"));
			System.out.println("________________ID LA: " + id);
			
			
			getRequestEcrypt(util, bgBean, multi);
			
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
				
				readExcel(bgBean, UPLOAD_DIRECTORY + filename, response);
			}
			
			bgBean.init();
			//bgbanBean.createRs();
			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_LNSBangGiaNguyenLieuUpdate.jsp";
			session.setAttribute("bgnlBean", bgBean);
			response.sendRedirect(nextJSP);
			return;
		}else{ 

			String id =  util.antiSQLInspection(request.getParameter("id"));
		    
		    if(id == null){  	
		    	id = "";
		    }
		    bgBean.setId(id);
		    
			String userId = util.antiSQLInspection(request.getParameter("userId"));
			bgBean.setUserId(userId);
		    System.out.println("userId :"+userId);
	    	String bgTen = util.antiSQLInspection(request.getParameter("bgTen"));
			if (bgTen == null)
				bgTen = "";				
	    	bgBean.setTen(bgTen);

			String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
	    	if (trangthai == null)
	    		trangthai = "0";
	    	else
	    		trangthai = "1";
	    	bgBean.setTrangthai(trangthai);
			   	
			String ngaysua = getDateTime();
	    	bgBean.setNgaysua(ngaysua);
	    	bgBean.setNgaytao(ngaysua);
	    	
			bgBean.setNguoitao(userId);
			bgBean.setNguoisua(userId);
	    			
			boolean error = false;


			if (bgTen.trim().length()== 0){
				bgBean.setMessage("Vui Long Nhap Ten Bang Gia");
				error = true;
			}

			String action = request.getParameter("action");
			
			if (action.equals("excel"))
		    {
		    	try
			    {
			    	response.setContentType("application/vnd.ms-excel");
			        response.setHeader("Content-Disposition", "attachment; filename=BangGiaNguyenLieu.xls");
			        
			        Workbook workbook = new Workbook();
			        getSearchQuery2(request, "", "");
				    CreateHeader(workbook, bgBean);
				    CreateData(workbook, bgBean);
				    workbook.save(out);
			    }
			    catch (Exception ex){ }
		    	
			    bgBean.setUserId(userId);
				bgBean.setId(id);
				bgBean.init();
				session.setAttribute("bgnlBean", bgBean);
				String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_LNSBangGiaNguyenLieuUpdate.jsp";
				response.sendRedirect(nextJSP);		    		
				return;
		    }
			
			
			if(action.equals("save") & !error)
			{
				if (id.length()==0){
					if (!(bgBean.CreateBgnguyenlieu(request)))
					{			
						bgBean.setCtyId(ctyId);
						bgBean.setCtyTen(ctyTen);
						
						bgBean.setUserId(userId);
						bgBean.init();
						session.setAttribute("bgnlBean", bgBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_LNSBangGiaNguyenLieuNew.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{
						
						ILNSBanggianguyenlieuList obj = new LNSBanggianguyenlieuList();
				    	obj.setCtyId(ctyId);
				    	obj.setCtyTen(ctyTen);
						
						obj.setUserId(userId);
						obj.init("");
						session.setAttribute("obj", obj);
							
						String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_LNSBangGiaNguyenLieu.jsp";
						response.sendRedirect(nextJSP);			    			    									
					}
					
				}else{
					if (!(bgBean.UpdateBgnguyenlieu(request)))
					{								
						bgBean.setCtyId(ctyId);
						bgBean.setCtyTen(ctyTen);
	
						bgBean.setUserId(userId);
						bgBean.setId(id);
						bgBean.init();
						session.setAttribute("bgnlBean", bgBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_LNSBangGiaNguyenLieuUpdate.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{
						ILNSBanggianguyenlieuList obj = new LNSBanggianguyenlieuList();
						//bgBean.setUserId(userId);
				    	obj.setCtyId(ctyId);
				    	obj.setCtyTen(ctyTen);
	
						obj.setUserId(userId);
						obj.init("");
						session.setAttribute("obj", obj);
						String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_LNSBangGiaNguyenLieu.jsp";
						response.sendRedirect(nextJSP);			    			    									
					}
				}
			}else
				if(action.equals("display")){
	
						bgBean.setCtyId(ctyId);
						bgBean.setCtyTen(ctyTen);
						
				        session.setAttribute("bgnlBean", bgBean);
				        String nextJSP;
				        
			        	nextJSP = "/TraphacoHYERP/pages/Erp/Erp_LNSBangGiaNguyenLieuDisplay.jsp";
	
				        response.sendRedirect(nextJSP);
						
					
				}else
				{	
				
				String nextJSP;
				if (id.length()==0){			
					bgBean.setCtyId(ctyId);
			    	bgBean.setCtyTen(ctyTen);
	
					bgBean.setUserId(userId);
					bgBean.init();
					session.setAttribute("bgnlBean", bgBean);
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_LNSBangGiaNguyenLieuNew.jsp";
				}else{
					bgBean.setCtyId(ctyId);
			    	bgBean.setCtyTen(ctyTen);
	
					bgBean.setUserId(userId);
					bgBean.setId(id);
					bgBean.init();
					session.setAttribute("bgnlBean", bgBean);
	
					nextJSP = "/TraphacoHYERP/pages/Erp/Erp_LNSBangGiaNguyenLieuUpdate.jsp";   						
				}
				response.sendRedirect(nextJSP);
				
			}
		}
	}
	
	void getRequestEcrypt(Utility util, ILNSBanggianguyenlieu bgBean, MultipartRequest request)
	{
    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		bgBean.setUserId(userId);
		
		String bgTen = util.antiSQLInspection(request.getParameter("bgTen"));
		if (bgTen == null)
			bgTen = "";
		bgBean.setTen(bgTen);

		String filename = request.getParameter("filename");
		if (filename == null)
			filename = "";
	
		String nam = util.antiSQLInspection(request.getParameter("nam"));

		if (nam == null)
			nam = "";
		bgBean.setNam(nam);

		String Id = util.antiSQLInspection(request.getParameter("id"));

		if (Id == null)
			Id = "";
		bgBean.setId(Id);

		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));

		if (trangthai == null)
			trangthai = "";

		bgBean.setTrangthai(trangthai);
	}

	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private void CreateHeader(Workbook workbook, ILNSBanggianguyenlieu bgBean) 
	{
		String sql = "SELECT NAM FROM ERP_LNSBANGGIANGUYENLIEU WHERE PK_SEQ = " + bgBean.getId() + " ";
		dbutils db = new dbutils();
		String nam = "";
		System.out.println(sql);

		ResultSet rs = db.get(sql);
		try{
			rs.next();
			nam = rs.getString("NAM");
			bgBean.setNam(nam);
			rs.close();
		}catch(java.sql.SQLException e){}
		
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	   	    
	    Cell cell = cells.getCell("A1"); 
	    cell.setValue("Lập ngân sách - Bảng giá nguyên liệu năm " + bgBean.getNam());
	   
	    cell = cells.getCell("A3");
	    cell.setValue("Ngày xuất dữ liệu: " + this.getDateTime());

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
	    
	    worksheet.setName("Bang gia nguyen lieu");
	}
	
	private void CreateData(Workbook workbook, ILNSBanggianguyenlieu bgBean) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    
		dbutils db = new dbutils();
		int row = 9;
		String query = "";
		
		for(int i = 0; i < 12; i++)
		{
			if(bgBean.getId().length() > 0)
			{
				
				row = 9;
				query =	"SELECT	DISTINCT BGSP.SANPHAM_FK AS SPID, BGSP.TEN AS SPTEN, DVDL.DONVI, BGSP.GIAMUA  " +
						"FROM ERP_LNSBANGGIANGUYENLIEU BG  " +
						"INNER JOIN ERP_LNSBGNGUYENLIEU_NGUYENLIEU BGSP on BG.PK_SEQ = BGSP.BGNGUYENLIEU_FK 	 " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = BGSP.SANPHAM_FK " +
						"INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = SP.DVDL_FK " + 
						"WHERE BG.PK_SEQ = '" + bgBean.getId() + "' AND BGSP.THANG = '" + (i + 1) + "'   " +
						"AND BG.CONGTY_FK = '" + bgBean.getCtyId() + "' " +
						" ORDER BY SPTEN ";
				

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

								double GIAMUA = 0;
								if(rs.getString("GIAMUA") != null)
									GIAMUA = rs.getDouble("GIAMUA");
								
								cell = cells.getCell("A" + Integer.toString(row));
								cell.setValue(spId);

								cell = cells.getCell("B" + Integer.toString(row));
								cell.setValue(tensp);
								cell = cells.getCell("C" + Integer.toString(row));
								cell.setValue(dv);
								
								cell = cells.getCell("D" + Integer.toString(row));
								cell.setValue(GIAMUA);
								row++;
							}
								
							if(i == 1){
								double GIAMUA = 0;
								if(rs.getString("GIAMUA") != null)
									GIAMUA = rs.getDouble("GIAMUA");

								cell = cells.getCell("E" + Integer.toString(row));
								cell.setValue(GIAMUA);		
								row++;
							}
							
							if(i == 2){
								double GIAMUA = 0;
								if(rs.getString("GIAMUA") != null)
									GIAMUA = rs.getDouble("GIAMUA");

								cell = cells.getCell("F" + Integer.toString(row));
								cell.setValue(GIAMUA);		
								row++;
							}

							if(i == 3){
								double GIAMUA = 0;
								if(rs.getString("GIAMUA") != null)
									GIAMUA = rs.getDouble("GIAMUA");

								cell = cells.getCell("G" + Integer.toString(row));
								cell.setValue(GIAMUA);		
								row++;
							}

							if(i == 4){
								double GIAMUA = 0;
								if(rs.getString("GIAMUA") != null)
									GIAMUA = rs.getDouble("GIAMUA");

								cell = cells.getCell("H" + Integer.toString(row));
								cell.setValue(GIAMUA);		
								row++;
							}

							if(i == 5){
								double GIAMUA = 0;
								if(rs.getString("GIAMUA") != null)
									GIAMUA = rs.getDouble("GIAMUA");

								cell = cells.getCell("I" + Integer.toString(row));
								cell.setValue(GIAMUA);		
								row++;
							}

							if(i == 6){
								double GIAMUA = 0;
								if(rs.getString("GIAMUA") != null)
									GIAMUA = rs.getDouble("GIAMUA");

								cell = cells.getCell("J" + Integer.toString(row));
								cell.setValue(GIAMUA);		
								row++;
							}

							if(i == 7){
								double GIAMUA = 0;
								if(rs.getString("GIAMUA") != null)
									GIAMUA = rs.getDouble("GIAMUA");

								cell = cells.getCell("K" + Integer.toString(row));
								cell.setValue(GIAMUA);		
								row++;
							}

							if(i == 8){
								double GIAMUA = 0;
								if(rs.getString("GIAMUA") != null)
									GIAMUA = rs.getDouble("GIAMUA");

								cell = cells.getCell("L" + Integer.toString(row));
								cell.setValue(GIAMUA);		
								row++;
							}

							if(i == 9){
								double GIAMUA = 0;
								if(rs.getString("GIAMUA") != null)
									GIAMUA = rs.getDouble("GIAMUA");

								cell = cells.getCell("M" + Integer.toString(row));
								cell.setValue(GIAMUA);		
								row++;
							}

							if(i == 10){
								double GIAMUA = 0;
								if(rs.getString("GIAMUA") != null)
									GIAMUA = rs.getDouble("GIAMUA");

								cell = cells.getCell("N" + Integer.toString(row));
								cell.setValue(GIAMUA);		
								row++;
							}

							if(i == 11){
								double GIAMUA = 0;
								if(rs.getString("GIAMUA") != null)
									GIAMUA = rs.getDouble("GIAMUA");

								cell = cells.getCell("O" + Integer.toString(row));
								cell.setValue(GIAMUA);		
								row++;
							}

						}
						rs.close();
					}catch(java.sql.SQLException e){}
				}		

			}
		}
	}
	
	private String getSearchQuery2(HttpServletRequest request, String pages, String soDong)
	{
		Utility util = new Utility();
		String id =  util.antiSQLInspection(request.getParameter("id"));
	    if(id == null)  	
	    	id = "";
	    
    	String query = 	"select distinct c.ma, c.ten, f.donvi, b.GIAMUA as giamua " +
    					"from ERP_LNSBANGGIANGUYENLIEU a " +
    					"inner join ERP_LNSBGNGUYENLIEU_NGUYENLIEU b on a.pk_seq = b.BGNGUYENLIEU_FK " +
    					"inner join erp_sanpham c on b.sanpham_fk = c.pk_seq " +
    					
    					"inner join donvidoluong f on c.dvdl_fk = f.pk_seq " +
    					"where  a.pk_seq = '" + id + "' ";
		     
    	System.out.print("\nQuery cua ban: " + query + "\n");
    	
    	return query;
    	
	}
	
	public void readExcel(ILNSBanggianguyenlieu obj, String fileName, HttpServletResponse response) throws IOException
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
			sql = "SELECT NAM FROM ERP_LNSBANGGIANGUYENLIEU WHERE PK_SEQ = " + obj.getId() + " ";
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
					String giamua = "0";
					
					for (int m = 1; m <= 12; m++){
					
						
						giamua = readSheet.getCell(i, m + 2).getStringValue().replaceAll(",", "");
						
						sql += "UPDATE ERP_LNSBGNGUYENLIEU_NGUYENLIEU SET GIAMUA = " + giamua + " " +
							  "WHERE BGNGUYENLIEU_FK = " + obj.getId() + " AND SANPHAM_FK = '" + spId + "'  " +
							  "AND THANG = " + m + " " ;
						
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