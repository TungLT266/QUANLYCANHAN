package geso.traphaco.center.servlets.report;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;


public class StockInTransit extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	//ICenterReport obj;

	
	public String getQuery(HttpServletRequest request,String sql,String userId)
	{
     String   query = 
    		 		"select kbh.pk_seq as kenhid, kbh.ten as kenh,	vung.pk_seq as vungid, vung.ten as vung,nhaphang.ngaynhan as pridate, "+   
    				 "   	kv.pk_seq as kvid, kv.ten as khuvuc,isnull(qh.pk_seq,0) as qhid, isnull(qh.ten,'') as quanhuyen, "+   
    				 "   	npp.pk_seq as nppid, npp.ten as nppten, "+   
    				 "   	nhaphang.pk_seq as chungtu,dvkd.pk_seq as dvkdid, dvkd.donvikinhdoanh,nhan.pk_seq, "+   
    				 "   	nhan.ten as nhanten,chungloai.pk_seq, chungloai.ten as clten, "+   
    				 "   	sp.ma as spma, sp.ten as spten, nhaphang_sp.soluong as quantity, "+   
    				 "   	nhaphang_sp.soluong*nhaphang_sp.dongia as amount ,tt.ten as tinhthanh,nhaphang_sp.SOLO  "+   
    				 "    from nhaphang nhaphang	 "+   
    				 "   	 inner join nhaphang_sp nhaphang_sp on nhaphang_sp.nhaphang_fk = nhaphang.pk_seq  "+   
    				 "   	 inner join sanpham sp on nhaphang_sp.sanpham_fk = sp.pk_seq   "+   
    				 "   	 left join nhanhang nhan on nhan.pk_seq = sp.nhanhang_fk  "+   
    				 "   	 left join chungloai chungloai on chungloai.pk_seq = sp.chungloai_fk  "+   
    				 "   	 left join donvikinhdoanh dvkd on dvkd.pk_seq = sp.dvkd_fk  "+   
    				 "   	 inner join nhaphanphoi npp on nhaphang.npp_fk = npp.pk_seq  "+   
    				 "   	 inner join kenhbanhang kbh on kbh.pk_seq = nhaphang.kbh_fk  "+   
    				 "   	 left join khuvuc kv on kv.pk_seq = npp.khuvuc_fk	 "+   
    				 "   	 left join vung vung on vung.pk_seq = kv.vung_fk  "+   
    				 "   	 left join quanhuyen qh on qh.pk_seq = npp.quanhuyen_fk  "+   
    				 "   	 left join tinhthanh tt on tt.pk_seq = npp.tinhthanh_fk "+   
    				 "    where nhaphang.trangthai = '0' " ;
    	//phanquyen
		geso.traphaco.center.util.Utility ut = new geso.traphaco.center.util.Utility();
		query += " and npp.pk_seq in " + ut.quyen_npp(userId) + " and kbh.pk_seq in " + ut.quyen_kenh(userId) + " and sp.pk_seq in "+ ut.quyen_sanpham(userId);

        if(sql.length()>0)
        	query = query + sql;
        
       System.out.println("Hang dang nhap kho: " + query);
       return query;
	}
	

    public StockInTransit() {
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
//		    System.out.println(userId);
		    obj.setuserId(userId);
		    obj.init();	    
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			String nextJSP = "/TraphacoHYERP/pages/Center/StockInTransit.jsp";
			response.sendRedirect(nextJSP);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
	    IStockintransit obj = new Stockintransit();
	    String userId = (String) session.getAttribute("userId");  
	    String userTen = (String) session.getAttribute("userTen"); 

	    if(userId == null)
	    	userId = "";
	    obj.setuserId(userId);
	    
   	 	String nppId=request.getParameter("nppId");
   	 	if(nppId ==null) 
   	 		nppId ="";
   	 	obj.setnppId(nppId);
     	
   	 	obj.setuserTen(userTen);
   	 	
	   	 String kenhId=request.getParameter("kenhId");
	   	 if(kenhId == null)
	   		 kenhId ="";
	   	 obj.setkenhId(kenhId);
	   	 
	   	 String dvkdId=request.getParameter("dvkdId");
	   	 if(dvkdId == null)
	   		 dvkdId ="";
	   	 obj.setdvkdId(dvkdId);
	   	 
	   	 String nhanhangId=request.getParameter("nhanhangId");
	   	 if(nhanhangId ==null)
	   		 nhanhangId = "";
	   	 obj.setnhanhangId(nhanhangId);
	   	 
	   	 String chungloaiId=request.getParameter("chungloaiId");
	   	 if(chungloaiId==null)
	   		chungloaiId = "";
	   	 obj.setchungloaiId(chungloaiId);
	   	 
	   	 String tungay=request.getParameter("Sdays");
	   	 if(tungay ==null)
	   		 tungay ="";
	   	 obj.settungay(tungay);
	   	 
	   	 String denngay=request.getParameter("Edays");
	   	 if(denngay == null)
	   		 denngay ="";
	   	 obj.setdenngay(denngay);
	   	 
	 	   	 String vungId=request.getParameter("vungId");
	   	 if(vungId ==null)
	   		 vungId = "";
	   	 obj.setvungId(vungId);
	   	 
	   	 String khuvucId=request.getParameter("khuvucId");
	   	 if(khuvucId == null)
	   		 khuvucId ="";
	   	 obj.setkhuvucId(khuvucId);
	   	 
	   	 String gsbhId=request.getParameter("gsbhId");
	   	 if(gsbhId ==null)
	   		 gsbhId ="";
	   	 obj.setgsbhId(gsbhId);
	   	 
	   	 String sanphamId=request.getParameter("sanphamId");
	   	 if(sanphamId == null)
	   		 sanphamId ="";
	   	 obj.setsanphamId(sanphamId);
	   	 
	   	 String dvdlId=request.getParameter("dvdlId");
		 if(dvdlId == null)
			 dvdlId ="";
		 obj.setdvdlId(dvdlId);
		 
		 String []fieldsHien = request.getParameterValues("fieldsHien");
		 obj.setFieldShow(fieldsHien);
		 
		 String [] fieldsAn =request.getParameterValues("fieldsAn");
			obj.setFieldHidden(fieldsAn)  ;
		 
		 String sql ="";
		 if(tungay.length()>0)
			 sql = sql + " and NHAPHANG.NGAYCHUNGTU >='"+ tungay +"'";
		 if(denngay.length()>0) 
			 sql = sql + " and  NHAPHANG.NGAYCHUNGTU<='"+ denngay +"'";
		 if(kenhId.length()>0) 
			 sql = sql + " and kbh.pk_seq ='"+ kenhId +"'";
		 if(vungId.length()>0) 
			 sql = sql +" and vung.pk_seq ='"+ vungId +"'";
		 if(khuvucId.length() > 0)
			 sql = sql + " and kv.pk_seq ='"+ khuvucId +"'";
		 if(dvkdId.length()> 0) 
			 sql = sql +" and sp.dvkd_fk ='"+ dvkdId +"'";
		 if(nppId.length()>0) 
			 sql = sql +" and npp.pk_seq ='"+ nppId +"'";
		 if(gsbhId.length()>0) 
			 sql = sql +" and gsbh.pk_seq ='"+ gsbhId +"'";
		 if(nhanhangId.length()>0) 
			 sql = sql +" and nhan.pk_seq ='"+ nhanhangId +"'";
		 if(chungloaiId.length()>0)
			 sql = sql +" and chungloai.pk_seq ='"+ chungloaiId +"'";
		 if(dvdlId.length()>0) 
			 sql = sql + " and sp.dvdl_fk ='"+ dvdlId +"'";
		 if(sanphamId.length()>0) 
			 sql = sql + " and sp.pk_seq = '"+ sanphamId +"'";
		 
		 String action = request.getParameter("action");
		 if(tungay.length() > 0 && denngay.length() > 0)
		 {
				 if(action.equals("tao"))
				 {
					try
					{
						response.setContentType("application/xlsm");
						response.setHeader("Content-Disposition", "attachment; filename=HangChuaNhapKho.xlsm");
				        OutputStream out = response.getOutputStream();
				        System.out.println("chuoi noi them:" + sql);
				        String query=this.getQuery(request,sql,userId); 
				        
				        CreatePivotTable(out, response, request,fieldsHien,obj,query);	// Create PivotTable 
				        
					}catch(Exception ex){
						ex.printStackTrace();
						System.out.println("Loi Tai Day : "+ ex.toString());
						obj.setMsg("Khong The Tao Duoc Bao Cao. Loi : "+ ex.toString());
					}
				 }
		 }
		 else
			 obj.setMsg("Ban phai chon ngay ");
		
	 	obj.init();	    
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Center/StockInTransit.jsp";
		response.sendRedirect(nextJSP);
	}
	private void CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,String[] manghien,IStockintransit obj ,String query)throws Exception
	{
		try 
		{
			FileInputStream fstream = null;
			Workbook workbook = new Workbook();		
			

			String fstreamstr = getServletContext().getInitParameter("path") + "\\HangChuaNhapKhoTT.xlsm";
			fstream = new FileInputStream(fstreamstr);
			
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

			CreateHeader(workbook,obj); 
			FillData(workbook,manghien,query); 
			workbook.save(out);
			
			fstream.close();
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}
	private void CreateHeader(Workbook workbook,IStockintransit obj) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);	    
	    worksheet.setName("Sheet1");
	    
	    Cells cells = worksheet.getCells();
		
	    Style style;
	    Font font = new Font();
	    font.setColor(Color.RED);//mau chu
	    font.setSize(16);// size chu
	   	font.setBold(true);
	   	
	    cells.setRowHeight(0, 20.0f);
	    Cell cell = cells.getCell("A1");
	    style = cell.getStyle();
	    style.setFont(font); 
	    style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu 	  
	    
	    String tieude = "BÁO CÁO HÀNG CHƯA NHẬP KHO";
	    ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);
	           
	    String message = "có VAT";
		
		cells.setRowHeight(2, 18.0f);
		cell = cells.getCell("A2");
		ReportAPI.getCellStyle(cell, Color.RED, true, 9, message);   

		cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("A4");
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ ngày: " + obj.gettungay() + "" );
	   
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("C4"); 
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến ngày: " + obj.getdenngay() + "" );
	    
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A5");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A6");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());
	    
	    
	    cell = cells.getCell("AA1");		cell.setValue("KenhBanHang");			ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AB1");		cell.setValue("DonviKinhDoanh");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AC1");		cell.setValue("ChiNhanh");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AD1");		cell.setValue("KhuVuc");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AE1");		cell.setValue("MaCN/DT");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AF1");		cell.setValue("CN/DT");			ReportAPI.setCellHeader(cell);	
		cell = cells.getCell("AG1");		cell.setValue("NhanHang");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AH1");		cell.setValue("ChungLoai");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AI1");		cell.setValue("SoChungTu");		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AJ1");		cell.setValue("MaSanPham");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AK1");		cell.setValue("TenSanPham");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AL1");		cell.setValue("TinhThanh");					ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AM1");		cell.setValue("QuanHuyen");			ReportAPI.setCellHeader(cell);	
		cell = cells.getCell("AN1");		cell.setValue("NgayChungTu");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AO1");		cell.setValue("SoLuong");			ReportAPI.setCellHeader(cell);	    
		cell = cells.getCell("AP1");		cell.setValue("SoTien");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AQ1");		cell.setValue("SoLo");			ReportAPI.setCellHeader(cell);
	}
	private void FillData(Workbook workbook,String[] manghien,String query) throws Exception 
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		
		for(int i = 0; i < 5; i++)
		{
	    	cells.setColumnWidth(i, 15.0f);
	    	if(i == 4)
	    		cells.setColumnWidth(i, 30.0f);
	    }	
		
		dbutils db = new dbutils();

		ResultSet rs = db.get(query);
		int index = 2;
	    Cell cell = null;	    
	    while (rs.next()) {
	    	cell = cells.getCell("AA" + String.valueOf(index));		cell.setValue(rs.getString("KENH"));
	    	cell = cells.getCell("AB" + String.valueOf(index));		cell.setValue(rs.getString("DONVIKINHDOANH"));	
			cell = cells.getCell("AC" + String.valueOf(index));		cell.setValue(rs.getString("VUNG"));
			cell = cells.getCell("AD" + String.valueOf(index));		cell.setValue(rs.getString("KHUVUC"));
			cell = cells.getCell("AE" + String.valueOf(index));		cell.setValue(rs.getString("NPPID"));
			cell = cells.getCell("AF" + String.valueOf(index));		cell.setValue(rs.getString("NPPTEN"));
			cell = cells.getCell("AG" + String.valueOf(index));		cell.setValue(rs.getString("NHANTEN"));
			cell = cells.getCell("AH" + String.valueOf(index));		cell.setValue(rs.getString("CLTEN"));
			cell = cells.getCell("AI" + String.valueOf(index));		cell.setValue(rs.getString("CHUNGTU"));
			cell = cells.getCell("AJ" + String.valueOf(index));		cell.setValue(rs.getString("SPMA"));
			cell = cells.getCell("AK" + String.valueOf(index));		cell.setValue(rs.getString("SPTEN"));
			cell = cells.getCell("AL" + String.valueOf(index));		cell.setValue(rs.getString("TINHTHANH"));	
			cell = cells.getCell("AM" + String.valueOf(index));		cell.setValue(rs.getString("QUANHUYEN")); 
			cell = cells.getCell("AN" + String.valueOf(index));		cell.setValue(rs.getString("Pridate"));
			cell = cells.getCell("AO" + String.valueOf(index));		cell.setValue( rs.getDouble("QUANTITY")  ); //14
			
			cell = cells.getCell("AP" + String.valueOf(index));		cell.setValue(rs.getDouble("AMOUNT") );   //15	
			cell = cells.getCell("AQ" + String.valueOf(index));		cell.setValue(rs.getString("solo") );   //15	
			
			index++;
		} 
	    if(rs!=null) rs.close();
	    if(db!=null)
	    	db.shutDown();
	  
	}

}
