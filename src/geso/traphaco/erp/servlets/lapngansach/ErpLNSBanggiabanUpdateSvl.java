package geso.traphaco.erp.servlets.lapngansach;

import geso.traphaco.erp.beans.lapngansach.ILNSBanggiaban;
import geso.traphaco.erp.beans.lapngansach.ILNSBanggiabanList;
import geso.traphaco.erp.beans.lapngansach.imp.LNSBanggiaban;
import geso.traphaco.erp.beans.lapngansach.imp.LNSBanggiabanList;
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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.aspose.cells.FileFormatType;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import java.util.Enumeration;
import com.oreilly.servlet.MultipartRequest;

 public class ErpLNSBanggiabanUpdateSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   
	public ErpLNSBanggiabanUpdateSvl() {
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
	    
	    ILNSBanggiaban bgBean = new LNSBanggiaban();
	    String ctyId = (String)session.getAttribute("congtyId");
	    String ctyTen = (String)session.getAttribute("congtyTen");
	    bgBean.setCtyId(ctyId);
	    bgBean.setCtyTen(ctyTen);
	    
        bgBean.setUserId(userId);
        bgBean.setId(id);
        bgBean.init();
        
        session.setAttribute("bgbanBean", bgBean);
        String nextJSP;
        
        if(action.equals("update")){
        	nextJSP = "/TraphacoHYERP/pages/Erp/Erp_LNSBangGiaBanUpdate.jsp";
        }else{
        	nextJSP = "/TraphacoHYERP/pages/Erp/Erp_LNSBangGiaBanDisplay.jsp";
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

		ILNSBanggiaban bgBean = (ILNSBanggiaban) new LNSBanggiaban();
		ILNSBanggiaban bgbanBean;
	    String ctyId = (String)session.getAttribute("congtyId");
	    String ctyTen = (String)session.getAttribute("congtyTen");
		
		String userId = (String) session.getAttribute("userId");

	    bgBean.setCtyId(ctyId);
	    bgBean.setCtyTen(ctyTen);
	    
		Utility util = new Utility();
	    String action = "";
	    String nextJSP = "";
	    
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
				bgbanBean = new LNSBanggiaban();
			} 
			else
			{
				bgbanBean = new LNSBanggiaban(id);
			}
			
			bgbanBean.setCtyId(ctyId);
			bgbanBean.setUserId(userId);
			bgbanBean.setId(id);
			getRequestEcrypt(util, bgbanBean, multi);
			
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
				
				readExcel(bgbanBean, UPLOAD_DIRECTORY + filename, response);
			}
			
			bgbanBean.init();
			//bgbanBean.createRs();
			nextJSP = "/TraphacoHYERP/pages/Erp/Erp_LNSBangGiaBanUpdate.jsp";
			session.setAttribute("bgbanBean", bgbanBean);
			response.sendRedirect(nextJSP);
		} 
		else
			{
	
			String id =  util.antiSQLInspection(request.getParameter("id"));
		    
		    if(id == null){  	
		    	id = "";
		    }
		    bgBean.setId(id);
		    
			userId = util.antiSQLInspection(request.getParameter("userId"));
			bgBean.setUserId(userId);
		    System.out.println("userId :"+userId);
	    	String bgTen = util.antiSQLInspection(request.getParameter("bgTen"));
			if (bgTen == null)
				bgTen = "";				
	    	bgBean.setTen(bgTen);
	
			String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
			if(dvkdId == null)
				dvkdId = "";	
			bgBean.setDvkdId(dvkdId);
		    
			String kenhId = util.antiSQLInspection(request.getParameter("kenhId"));
			if(kenhId == null)
				kenhId = "";
	
			bgBean.setKenhId(kenhId);
	
			
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
	
			if (kenhId.trim().length()== 0){
				bgBean.setMessage("Vui long lua chon Kenh Ban Hang");
				error = true;
			}
	
			if (dvkdId.trim().length()== 0){
				bgBean.setMessage("Vui long lua chon Đơn Vị Kinh Doanh");
				error = true;
			}
			
			if (bgTen.trim().length()== 0){
				bgBean.setMessage("Vui Long Nhap Ten Bang Gia");
				error = true;
			}
	
			action = request.getParameter("action");
		    
			if (action.equals("excel"))
		    {
		    	try
			    {
			    	response.setContentType("application/vnd.ms-excel");
			        response.setHeader("Content-Disposition", "attachment; filename=BangGiaBan.xls");
	
			        bgBean.setCtyId(ctyId);
					
					bgBean.setUserId(userId);
	
					bgBean.init();
	
			        Workbook workbook = new Workbook();
			    	
				    CreateHeader(workbook, bgBean);
				    CreateData(workbook, bgBean);
				
				     //Saving the Excel file
				     workbook.save(out);
			    }
			    catch (Exception ex){ }
		    	
			    bgBean.setUserId(userId);
				bgBean.setId(id);
				bgBean.init();
				session.setAttribute("bgbanBean", bgBean);
				nextJSP = "/TraphacoHYERP/pages/Erp/Erp_LNSBangGiaBanUpdate.jsp";
				response.sendRedirect(nextJSP);		    		
				return;
		    }
			
			
			if(action.equals("save") & !error)
			{
				System.out.println("I am here 1!");
				if (id.length()==0){
					if (!(bgBean.CreateBgban(request)))
					{			
						bgBean.setCtyId(ctyId);
						bgBean.setCtyTen(ctyTen);
						
						bgBean.setUserId(userId);
						bgBean.init();
						session.setAttribute("bgbanBean", bgBean);
						nextJSP = "/TraphacoHYERP/pages/Erp/Erp_LNSBangGiaBanNew.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{
						
						ILNSBanggiabanList obj = new LNSBanggiabanList();
				    	obj.setCtyId(ctyId);
				    	obj.setCtyTen(ctyTen);
	//			    	bgBean.closeDB();
						obj.setUserId(userId);
						obj.init("");
						session.setAttribute("obj", obj);
							
						nextJSP = "/TraphacoHYERP/pages/Erp/Erp_LNSBangGiaBan.jsp";
						response.sendRedirect(nextJSP);			    			    									
					}
					
				}else{
					if (!(bgBean.UpdateBgban(request)))
					{								
						bgBean.setCtyId(ctyId);
						bgBean.setCtyTen(ctyTen);
	
						bgBean.setUserId(userId);
						bgBean.setId(id);
						bgBean.init();
						session.setAttribute("bgbanBean", bgBean);
						nextJSP = "/TraphacoHYERP/pages/Erp/Erp_LNSBangGiaBanUpdate.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{
						ILNSBanggiabanList obj = new LNSBanggiabanList();
						//bgBean.setUserId(userId);
				    	obj.setCtyId(ctyId);
				    	obj.setCtyTen(ctyTen);
//				    	bgBean.closeDB();
						obj.setUserId(userId);
						obj.init("");
						session.setAttribute("obj", obj);
						nextJSP = "/TraphacoHYERP/pages/Erp/Erp_LNSBangGiaBan.jsp";
						response.sendRedirect(nextJSP);			    			    									
					}
				}
			}else
				if(action.equals("display")){
					System.out.println("I am here 2!");
					if(bgBean.ChangeBgban(request)){
				    	ILNSBanggiabanList obj = new LNSBanggiabanList();
				    	obj.setCtyId(ctyId);
				    	obj.setCtyTen(ctyTen);
				    	obj.init("");
	//			    	bgBean.closeDB();
				    	obj.setUserId(userId);
				    	session.setAttribute("obj", obj);
							
				    	nextJSP = "/TraphacoHYERP/pages/Erp/Erp_LNSBangGiaBan.jsp";
				    	response.sendRedirect(nextJSP);					
					}else{
						bgBean.setCtyId(ctyId);
						bgBean.setCtyTen(ctyTen);
						
				        session.setAttribute("bgbanBean", bgBean);
				       				        
			        	nextJSP = "/TraphacoHYERP/pages/Erp/Erp_LNSBangGiaBanDisplay.jsp";
	
				        response.sendRedirect(nextJSP);
						
					}
				}else
				{	
					System.out.println("I am here 3!");
					bgBean.setCtyId(ctyId);
					bgBean.setCtyTen(ctyTen);
	
					bgBean.setUserId(userId);
					bgBean.createRS();
					bgBean.init();
					session.setAttribute("bgbanBean", bgBean);
	
					if (id.length()==0){			
						nextJSP = "/TraphacoHYERP/pages/Erp/Erp_LNSBangGiaBanNew.jsp";
					}else{
						nextJSP = "/TraphacoHYERP/pages/Erp/Erp_LNSBangGiaBanUpdate.jsp";   						
						}
					response.sendRedirect(nextJSP);
				
				}
		}
	}
	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	


	private void CreateHeader(Workbook workbook, ILNSBanggiaban bgbanBean) 
	{	
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	   	    
	    Cell cell = cells.getCell("A1"); 
	    cell.setValue("Bảng giá bán dự toán năm " + bgbanBean.getNam());
	   
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
	
	    
	    worksheet.setName("Bảng giá ngân sách");
	}
	
	private void CreateData(Workbook workbook, ILNSBanggiaban bgbanBean) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    
		dbutils db = new dbutils();
		int row = 9;
		String query = "";
		
		for(int i = 0; i < 12; i++)
		{
			if(bgbanBean.getId().length() > 0)
			{
				
				row = 9;
				query =	"SELECT	DISTINCT GIABANSP.SANPHAM_FK AS SPID, \n " +
						"GIABANSP.TEN AS SPTEN, ISNULL(DVDL.DONVI, '') AS DONVI, \n " +
						"GIABAN.NAM, GIABANSP.THANG, ISNULL(GIABANSP.GIABAN, 0) AS GIABAN \n " +								 
						"FROM ERP_LNSBANGGIABAN GIABAN  \n " +
						"INNER JOIN ERP_LNSBGBAN_SANPHAM GIABANSP on GIABAN.PK_SEQ = GIABANSP.BGBAN_FK  \n " +	
						"INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ = GIABAN.KENH_FK  \n " +		
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = GIABANSP.SANPHAM_FK  \n " +
						"INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = SP.DVDL_FK \n " +
						"WHERE GIABAN.PK_SEQ = '" + bgbanBean.getId() + "' AND GIABANSP.THANG = '" + (i + 1) + "' " +
						"AND GIABAN.NAM = '" + bgbanBean.getNam() + "'  \n " +
						"AND GIABAN.CONGTY_FK = " + bgbanBean.getCtyId() + " AND LEN( RTRIM(LTRIM(GIABANSP.TEN)) ) > 0  \n ";
						 
				
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

								double giaban = 0;
								if(rs.getString("GIABAN") != null)
									giaban = rs.getDouble("GIABAN");
								
								cell = cells.getCell("A" + Integer.toString(row));
								cell.setValue(spId);

								cell = cells.getCell("B" + Integer.toString(row));
								cell.setValue(tensp);
								cell = cells.getCell("C" + Integer.toString(row));
								cell.setValue(dv);
								
								cell = cells.getCell("D" + Integer.toString(row));
								cell.setValue(giaban);
								row++;
							}
								
							if(i == 1){
								double giaban = 0;
								if(rs.getString("GIABAN") != null)
									giaban = rs.getDouble("GIABAN");

								cell = cells.getCell("E" + Integer.toString(row));
								cell.setValue(giaban);		
								row++;
							}
							
							if(i == 2){
								double giaban = 0;
								if(rs.getString("GIABAN") != null)
									giaban = rs.getDouble("GIABAN");

								cell = cells.getCell("F" + Integer.toString(row));
								cell.setValue(giaban);		
								row++;
							}

							if(i == 3){
								double giaban = 0;
								if(rs.getString("GIABAN") != null)
									giaban = rs.getDouble("GIABAN");

								cell = cells.getCell("G" + Integer.toString(row));
								cell.setValue(giaban);		
								row++;
							}

							if(i == 4){
								double giaban = 0;
								if(rs.getString("GIABAN") != null)
									giaban = rs.getDouble("GIABAN");

								cell = cells.getCell("H" + Integer.toString(row));
								cell.setValue(giaban);		
								row++;
							}

							if(i == 5){
								double giaban = 0;
								if(rs.getString("GIABAN") != null)
									giaban = rs.getDouble("GIABAN");

								cell = cells.getCell("I" + Integer.toString(row));
								cell.setValue(giaban);		
								row++;
							}

							if(i == 6){
								double giaban = 0;
								if(rs.getString("GIABAN") != null)
									giaban = rs.getDouble("GIABAN");

								cell = cells.getCell("J" + Integer.toString(row));
								cell.setValue(giaban);		
								row++;
							}

							if(i == 7){
								double giaban = 0;
								if(rs.getString("GIABAN") != null)
									giaban = rs.getDouble("GIABAN");

								cell = cells.getCell("K" + Integer.toString(row));
								cell.setValue(giaban);		
								row++;
							}

							if(i == 8){
								double giaban = 0;
								if(rs.getString("GIABAN") != null)
									giaban = rs.getDouble("GIABAN");

								cell = cells.getCell("L" + Integer.toString(row));
								cell.setValue(giaban);		
								row++;
							}

							if(i == 9){
								double giaban = 0;
								if(rs.getString("GIABAN") != null)
									giaban = rs.getDouble("GIABAN");

								cell = cells.getCell("M" + Integer.toString(row));
								cell.setValue(giaban);		
								row++;
							}

							if(i == 10){
								double giaban = 0;
								if(rs.getString("GIABAN") != null)
									giaban = rs.getDouble("GIABAN");

								cell = cells.getCell("N" + Integer.toString(row));
								cell.setValue(giaban);		
								row++;
							}

							if(i == 11){
								double giaban = 0;
								if(rs.getString("GIABAN") != null)
									giaban = rs.getDouble("GIABAN");

								cell = cells.getCell("O" + Integer.toString(row));
								cell.setValue(giaban);		
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
	    {
	    	id = "";
	    	return "";
	    }
	    
		String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
    	if (dvkdId == null)
    		dvkdId = "";
    	
		String kenhId = util.antiSQLInspection(request.getParameter("kenhId"));
		if(kenhId == null)
			kenhId = "";
				
    	String query = 	"select distinct d.donvikinhdoanh as dvkd,  c.ma, c.ten, f.donvi, \n" +
    					"b.GIABAN as giablc, g.ten as kenhbanhang \n" +
    					"from ERP_LNSBANGGIABAN a \n" +
    					"inner join ERP_LNSBGBAN_SANPHAM b on a.pk_seq = b.BGBAN_FK \n" +
    					"inner join erp_sanpham c on b.sanpham_fk = c.pk_seq \n" +
    					"left join donvikinhdoanh d on c.dvkd_fk = d.pk_seq \n" +
    					
    					"inner join donvidoluong f on c.dvdl_fk = f.pk_seq inner join kenhbanhang g on a.kenh_fk = g.pk_seq \n" +
    					"where  a.pk_seq = '" + id + "' \n";
		     
    	System.out.print("\nQuery cua ban: " + query + "\n==================================");
    	
    	if(dvkdId.length() > 0)
    		query += "and d.pk_seq = '" + dvkdId + "' ";
 
    	if(kenhId.length() > 0)
    		query += "and a.kenh_fk = '" + kenhId + "'";
    	
    	return query;
    	
	}

	void getRequestEcrypt(Utility util, ILNSBanggiaban bgbanBean, MultipartRequest request)
	{

		String bgTen = util.antiSQLInspection(request.getParameter("bgTen"));
		if (bgTen == null)
			bgTen = "";
		bgbanBean.setTen(bgTen);

		String filename = request.getParameter("filename");
		if (filename == null)
			filename = "";
	
		String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
		if (dvkdId == null)
			dvkdId = "";
		bgbanBean.setDvkdId(dvkdId);

		String kenhId = util.antiSQLInspection(request.getParameter("kenhId"));
		if (kenhId == null)
			kenhId = "";
		bgbanBean.setKenhId(kenhId);

		String nam = util.antiSQLInspection(request.getParameter("nam"));

		if (nam == null)
			nam = "";
		bgbanBean.setNam(Integer.parseInt(nam));

		String Id = util.antiSQLInspection(request.getParameter("id"));

		if (Id == null)
			Id = "";
		bgbanBean.setId(Id);

	}

	public void readExcel(ILNSBanggiaban obj, String fileName, HttpServletResponse response) throws IOException
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
			sql = "SELECT NAM FROM ERP_LNSBANGGIABAN WHERE PK_SEQ = " + obj.getId() + " ";
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
					String giaban = "0";
					
					for (int m = 1; m <= 12; m++){
					
						
						giaban = readSheet.getCell(i, m + 2).getStringValue().replaceAll(",", "");
						
						sql += "UPDATE ERP_LNSBGBAN_SANPHAM SET GIABAN = " + giaban + " " +
							  "WHERE BGBAN_FK = " + obj.getId() + " AND SANPHAM_FK = '" + spId + "'  " +
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