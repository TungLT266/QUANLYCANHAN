package geso.traphaco.distributor.beans.reports.imp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.cete.dynamicpdf.Align;
import com.extentech.formats.OOXML.Alignment;

import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.reports.IBCHoaDonChuaThanhToan;
import geso.traphaco.distributor.db.sql.dbutils;

public class BCHoaDonChuaThanhToan implements IBCHoaDonChuaThanhToan, Serializable {

	private String userId;
	private String userName;
	private String tuNgay;
	private String denNgay;
	private String nppId;
	private String ngayKS;
	private ResultSet rs;
	
	String msg;
	
	String dtIds;
	String nvbhIds;
	String nvgnIds;
	String khIds;
	
	ResultSet nvgnRs;
	ResultSet nvbhRs;
	ResultSet khRs;
	ResultSet dtRs;
	
	ResultSet RsErpCongty;
	dbutils db;
	
	public BCHoaDonChuaThanhToan(){
		this.userId = "";
		this.userName = "";
		this.tuNgay = "";
		this.denNgay = "";
		this.nppId = "";
		this.ngayKS = "";
		
		this.msg= "";
		this.dtIds = "";
		this.khIds = "" ;
		this.nvbhIds = "";
		this.nvgnIds = "";
		this.rs = null;
		this.db = new dbutils();
	}





	public void setnppId(String nppId) 
	{
		this.nppId = nppId;
		
	}


	public String getnppId() 
	{

		return this.nppId;
	}




	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public String getMsg() 
	{
		return this.msg;
	}


	public void setNvbhIds(String nvbhIds)
	{
		this.nvbhIds = nvbhIds;
	}


	public String getNvbhIds() 
	{
		return this.nvbhIds;
	}


	public ResultSet getNvbhRs() 
	{
		return this.nvbhRs;
	}


	public ResultSet setNvbhRs(ResultSet nvbhRs)
	{
		return this.nvbhRs;
	}


	public void setNvgnIds(String nvgnIds) 
	{
		this.nvgnIds = nvgnIds;
	}


	public String getNvgnIds() 
	{
		return this.nvbhIds;
	}


	public ResultSet getNvgnRs() 
	{
		return this.nvgnRs;
	}


	public ResultSet setNvgnRs(ResultSet nvgnRs) 
	{
		return this.nvgnRs;
	}


	public void setKhIds(String khIds) 
	{
		this.khIds= khIds;
	}


	public String getKhIds() 
	{
		return this.khIds;
	}


	public ResultSet getKhRs() 
	{
		return this.khRs;
	}


	public ResultSet setKhRs(ResultSet khRs) 
	{
		return this.khRs;
	}


	public void setDtIds(String dtIds) 
	{
		this.dtIds = dtIds;
	}


	public String getDtIds() 
	{
		return this.dtIds;
	}


	public ResultSet getDtRs()
	{
		return this.dtRs;
	}


	public ResultSet setDtRs(ResultSet dtRs) 
	{
		return this.dtRs;
	}

	@Override
	public String getNPPID() {
		// TODO Auto-generated method stub
		return this.nppId;
	}

	@Override
	public String getNgayKS() {
		// TODO Auto-generated method stub
		Utility ut = new Utility();
		this.nppId = ut.getIdNhapp(userId);
		dbutils db = new dbutils();
		
		String sql = "SELECT TOP(1) NGAYKS FROM KHOASONGAY WHERE NPP_FK = '"+ this.nppId +"' ORDER BY NGAYKS DESC";
		
		//ResultSet rs = db.get("select * from khoasongay where npp_fk='"+this.nppId+"'");
		ResultSet rs = db.get(sql);
		
		if(rs != null)
		{
			try {
				rs.next();
				this.ngayKS = rs.getString("ngayks");
			} catch(Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{try {
				rs.close();
			} catch(Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
			
			
		}
		if(db != null)
			db.shutDown();
		return this.ngayKS;

	}

	@Override
	public ResultSet getRS() {
		// TODO Auto-generated method stub
		return this.rs;
	}
	
	@Override
	public void createStaticHeader(Workbook workbook, IBCHoaDonChuaThanhToan obj) 
	{		
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		ResultSet ctyRs = obj.getRsErpCongty();
		String ctyName = "";
		String diachi = "";
		String masothue = "";
		
		try{
			if(ctyRs != null){
				ctyRs.next();
			
				ctyName = ctyRs.getString("TEN");
				diachi =  ctyRs.getString("DIACHI");
				masothue =  ctyRs.getString("MASOTHUE");
				
				ctyRs.close();
			}
			
		}catch(java.sql.SQLException e){
			System.out.println(e.toString());
		}
		
		Cells cells = worksheet.getCells();

		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
		ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, ctyName);
	    
	    cells.setRowHeight(1, 20.0f);
	    cell = cells.getCell("A2");
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Địa chỉ: " + diachi); 
	    
	    cells.setRowHeight(2, 20.0f);
	    cell = cells.getCell("A3");
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Mã số thuế: " + masothue); 
	    
	    cell = cells.getCell("D5"); 
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 15, "BÁO CÁO CÔNG NỢ ");

	    cell = cells.getCell("A7"); 
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Từ ngày: " + this.tuNgay);
	    
	    cell = cells.getCell("A8"); 
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Đến ngày: " + this.denNgay);
	    

	}

	public void init() 
	{
		// TODO Auto-generated method stub
		Utility ut = new Utility();
		this.nppId = ut.getIdNhapp(userId);
		String dk = "";
		if(!this.tuNgay.equals(""))
			dk += 	" and dh.ngaynhap >= '"+this.tuNgay+"' ";
		if(!this.tuNgay.equals(""))
			dk += " and dh.ngaynhap <= '"+this.denNgay+"' ";
		
		String sql = "SELECT TOP(1) NGAYKS FROM KHOASONGAY WHERE NPP_FK = '"+ this.nppId +"' ORDER BY NGAYKS DESC";
		System.out.println("Khoa so : "+sql);
		dbutils db = new dbutils();
		ResultSet ks = db.get(sql);
		try{
			ks.next();
			this.ngayKS = ks.getString("ngayks");
			
			sql=" SELECT kh.smartid as smartid, KH.PK_SEQ AS KHID, KH.TEN AS TENKH,	"+    	 
				" DH.PK_SEQ AS DHID, DH.NGAYNHAP AS NGAYDH,    	 	"+
				" KH_CN.SOTIENNO AS TIENDH ,0 as SOCHUNGTU, 0 AS TIENTHANHTOAN	"+  	 
				" FROM DONHANG DH    	"+
				" INNER JOIN KHACHHANG_CONGNO KH_CN ON KH_CN.DONHANG_FK = DH.PK_SEQ 	"+
				" INNER JOIN KHACHHANG KH ON KH.PK_SEQ = DH.KHACHHANG_FK 	"+ 	 
				" WHERE DH.NPP_FK = '"+this.nppId+"'  AND  	 	"+
				" KH_CN.DONHANG_FK NOT IN (SELECT DONHANG_FK FROM PHIEUTHANHTOAN WHERE NGAY <= '"+this.ngayKS+"')	"+  	
				" AND KH_CN.NGAYNO <='"+this.ngayKS+"'	"+
				" UNION   		"+
				" SELECT substring(kh.smartid ,11,len(kh.smartid)-10) as smartid,KH.PK_SEQ,KH.TEN AS TENKH,	"+
				" CN.DONHANG_FK AS DHID,DH.NGAYNHAP AS NGAYDH,CN.SOTIENNO AS TIENDH,	"+  	
				" PTT.PK_SEQ AS SOCHUNGTU,	"+
				" TT.TT AS TIENTHANHTOAN  	 	"+
				" from KHACHHANG_CONGNO CN  		"+
				" INNER JOIN KHACHHANG KH ON KH.PK_SEQ=CN.KHACHHANG_FK 	"+ 	
				" INNER JOIN DONHANG DH ON DH.PK_SEQ=CN.DONHANG_FK	"+  		 
				" INNER JOIN	"+
				" (	"+
				" SELECT DH.PK_SEQ AS DHID, SUM(PTT.SOTIEN) AS TT	"+ 
				" from PHIEUTHANHTOAN PTT  	"+
				" INNER JOIN DONHANG DH ON DH.PK_SEQ = PTT.DONHANG_FK	"+
				" WHERE PTT.NGAY <='"+this.ngayKS+"'	"+ 
				" GROUP BY DH.PK_SEQ	"+
				" )TT ON TT.DHID = CN.DONHANG_FK 	"+
				" LEFT JOIN PHIEUTHANHTOAN PTT ON PTT.DONHANG_FK=CN.DONHANG_FK	"+  
				" where CN.SOTIENNO- TT.TT >10 	"+
				" AND DH.NPP_FK= '"+this.nppId+"' AND CN.NGAYNO <='"+this.ngayKS+"'	"+
				" ORDER BY DH.PK_SEQ ,KH.TEN";
				//" 						 ORDER BY KH.TEN, SOCHUNGTU	";
		System.out.println("sql jkf: " + sql);
		this.rs = db.get(sql);
		}catch(Exception e ){}
	}

	@Override
	public void tieuDe(Workbook workbook, int rowIndex) {
		// TODO Auto-generated method stub
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   
	    
   	   
	    Cells cells = worksheet.getCells();
	    Cell cell = cells.getCell("A" + rowIndex);  	    
	    cell.setValue("Số hóa đơn"); 		//createBorderSetting(workbook,"A" + rowIndex); 
	    getCellStyle(workbook,"A" + rowIndex,Color.BLACK,true,9);
	    
	    cell = cells.getCell("C"  + rowIndex); 	    
	    cell.setValue("Chứng từ thành toán");		//createBorderSetting(workbook,"B" + rowIndex);	
	    getCellStyle(workbook,"C" + rowIndex,Color.BLACK,true,9);
	    
	    cell = cells.getCell("E"  + rowIndex); cell.setValue("Ngày hóa đơn");			//createBorderSetting(workbook,"C" + rowIndex);	
	    getCellStyle(workbook,"E" + rowIndex,Color.BLACK,true,9);
	    
	    cell = cells.getCell("G"  + rowIndex); cell.setValue("Tiền hóa đơn");	//createBorderSetting(workbook,"D" + rowIndex);	
	    getCellStyle(workbook,"G" + rowIndex,Color.BLACK,true,9);
	    
	    cell = cells.getCell("I"  + rowIndex); cell.setValue("Thanh toán");		//createBorderSetting(workbook,"E" + rowIndex);	
	    getCellStyle(workbook,"I" + rowIndex,Color.BLACK,true,9);
	    
	    cells.setColumnWidth(0, 15.0f);
	    cells.setColumnWidth(1, 15.0f);
		cells.setColumnWidth(2, 20.0f);
		cells.setColumnWidth(4, 15.0f);
		cells.setColumnWidth(6, 15.0f);
		cells.setColumnWidth(8, 15.0f);	
	}

	public void createStaticData(Workbook workbook) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
		 
	    float tongtienavat= 0;
	    float tongtientt= 0;
	    
	    NumberFormat formatter = new DecimalFormat("#,###,###"); 
	    
	    int index = 12;
	    for(int i = 0;i < 10; ++i)
		{
	    	cells.setColumnWidth(i, 15.0f);
	    	if(i == 0)
	    		cells.setColumnWidth(i, 10.0f);
	    		
	    		
	    	
	    }
	    
	    if (rs != null) 
		{
			try 
			{
				Cell cell = null;
				while (rs.next())
				{		
					tongtienavat  += rs.getDouble("SOTIENAVAT");
					tongtientt  += rs.getDouble("SOTIENTT");
					
					
					Style style ;
					
					cell = cells.getCell("A" + String.valueOf(index));		cell.setValue(index - 11);						
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.CENTER);
					cell.setStyle(style);
					
					cell = cells.getCell("B" + String.valueOf(index));		cell.setValue(rs.getString("MAFAST"));	
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					
					cell = cells.getCell("C" + String.valueOf(index));		cell.setValue(rs.getString("TENKH"));
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					
					cell = cells.getCell("D" + String.valueOf(index));		cell.setValue(rs.getString("SOHOADON"));	
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.CENTER);
					cell.setStyle(style);
					
					
					cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(rs.getString("NGAYHOADON"));
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.CENTER);
					cell.setStyle(style);
					
					cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(rs.getString("TTID"));
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.CENTER);
					cell.setStyle(style);
					
					cell = cells.getCell("G" + String.valueOf(index));		cell.setValue(formatter.format(rs.getDouble("SOTIENAVAT")));
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.RIGHT);
					cell.setStyle(style);
					
					cell = cells.getCell("H" + String.valueOf(index));		cell.setValue(formatter.format(rs.getDouble("SOTIENTT")));
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.RIGHT);
					cell.setStyle(style);
					
					cell = cells.getCell("I" + String.valueOf(index));		cell.setValue("");
					this.setStyleColorNormar(cells, cell);
					

					
					index++;					
				}

				if (rs != null){
					rs.close();
				}
				
				
				Style style1 ;
				
				cell = cells.getCell("A" + String.valueOf(index));		cell.setValue(" ");
				this.setStyleColorGray(cells, cell, "0");
				
				cell = cells.getCell("B" + String.valueOf(index));		cell.setValue("Tổng cộng");
				this.setStyleColorGray(cells, cell, "0");
				style1 = cell.getStyle();
				style1.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style1);
				
				cell = cells.getCell("C" + String.valueOf(index));		cell.setValue(" ");
				this.setStyleColorGray(cells, cell, "0");
				
				cell = cells.getCell("D" + String.valueOf(index));		cell.setValue(" ");	
				this.setStyleColorGray(cells, cell, "1");
				
				cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(" ");	
				this.setStyleColorGray(cells, cell, "1");
				
				cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(" ");
				this.setStyleColorGray(cells, cell, "1");
				
				cell = cells.getCell("G" + String.valueOf(index));		cell.setValue(formatter.format(tongtientt));
				this.setStyleColorGray(cells, cell, "1");
				style1 = cell.getStyle();
				style1.setHAlignment(TextAlignmentType.RIGHT);
				cell.setStyle(style1);
				
				cell = cells.getCell("H" + String.valueOf(index));		cell.setValue(formatter.format(tongtientt));
				this.setStyleColorGray(cells, cell, "1");
				style1 = cell.getStyle();
				style1.setHAlignment(TextAlignmentType.RIGHT);
				cell.setStyle(style1);
				
				cell = cells.getCell("I" + String.valueOf(index));		cell.setValue(" ");
				this.setStyleColorGray(cells, cell, "1");
				

				index=index+3;
				this.setStyleColorGray(cells, cell, "1");
				
				cell = cells.getCell("B" + String.valueOf(index));		
				ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Lập biểu");
				
				cell = cells.getCell("H" + String.valueOf(index));		
				ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Giám đốc");
				
				
				cells.setColumnWidth(0, 10.0f);
				cells.setColumnWidth(1, 20.0f);
				cells.setColumnWidth(2, 40.0f);
				cells.setColumnWidth(3, 25.0f);
				cells.setColumnWidth(4, 25.0f);
				cells.setColumnWidth(5, 25.0f);
				cells.setColumnWidth(6, 25.0f);
				cells.setColumnWidth(7, 25.0f);
				cells.setColumnWidth(8, 25.0f);
				cells.setColumnWidth(9, 25.0f);
				
				
			}

			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
	}

	private void setStyleColorGray(Cells cells, Cell cell, String leftright) 
	{

		Cell cell1 = cells.getCell("Y1");
		Style style;	
		style = cell1.getStyle();
        if(leftright.equals("1")){
        	style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        	style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        	style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        	style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
            cell.setStyle(style);
        }else{
        	style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        	style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        	style.setBorderLine(BorderType.LEFT, BorderLineType.NONE);
        	style.setBorderLine(BorderType.RIGHT, BorderLineType.NONE);
            cell.setStyle(style);        	
        }
        
	
		
	}





	private void setStyleColorNormar(Cells cells, Cell cell) 
	{

		Cell cell1 = cells.getCell("X1");
		Style style;	
		style = cell1.getStyle();
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);

        cell.setStyle(style);
        
		
	}





	@Override
	public void getCellStyle(Workbook workbook, String cellName, Color color,
			Boolean bold, int size) {
		// TODO Auto-generated method stub
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
		Style style;
		Cell cell = cells.getCell(cellName); 
		 style = cell.getStyle();
		 style.setHAlignment(TextAlignmentType.CENTER);
	        Font font1 = new Font();
	        font1.setColor(color);
	        font1.setBold(bold);
	        font1.setSize(size);
	        style.setFont(font1);
	        cell.setStyle(style);
		
	}

	@Override
	public void createBorderSetting(Workbook workbook, String fileName) {
		// TODO Auto-generated method stub
		
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
        Cells cells = worksheet.getCells();
        Cell cell;
        Style style;

        cell = cells.getCell(fileName);
        style = cell.getStyle();

        //Set border color
        style.setBorderColor(BorderType.TOP, Color.BLACK);
        style.setBorderColor(BorderType.BOTTOM, Color.BLACK);
        style.setBorderColor(BorderType.LEFT, Color.BLACK);
        style.setBorderColor(BorderType.RIGHT, Color.BLACK);
        //style.setBorderColor(BorderType.DIAGONAL_DOWN, Color.BLACK);
        //style.setBorderColor(BorderType.DIAGONAL_UP, Color.BLACK);

        //Set the cell border type
        style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
        //style.setBorderLine(BorderType.DIAGONAL_DOWN, BorderLineType.DASHED);
        //style.setBorderLine(BorderType.DIAGONAL_UP, BorderLineType.DASHED);

        cell.setStyle(style);
		
	}

	@Override
	public String getDateTime() {
		// TODO Auto-generated method stub
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	@Override
	public void dbClose() {
		// TODO Auto-generated method stub
		try {
			if(rs !=null)
				rs.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}

	@Override
	public void setUserId(String userId) {
		this.userId = userId;
		
	}

	@Override
	public String getUserId() {
		return this.userId;
	}

	@Override
	public String getUserName() {
		return this.userName;
	}

	@Override
	public void setUserName(String userName) {
		this.userName = userName;
		
	}

	@Override
	public void setTuNgay(String tuNgay) {
		this.tuNgay = tuNgay;
		
	}

	@Override
	public String getTuNgay() {
		return this.tuNgay ;
	}

	@Override
	public void setDenNgay(String denNgay) {
		this.denNgay = denNgay;
		
	}

	@Override
	public String getDenNgay() {
		return this.denNgay;
	}





	@Override
	public void createRs() 
	{
		Utility ut = new Utility();
		
		this.nppId = ut.getIdNhapp(userId);
		
		if(this.nppId==null)
		{
			
			String sql = "select pk_seq, isnull(maFAST,'') + '-' + ten as nppTen from NHAPHANPHOI where trangthai = '1' and loainpp='4'";
			System.out.println("jdfgdjfg...."+sql);
			this.dtRs = db.get(sql);
			
			
			sql = "select pk_seq, isnull(maFAST,'') + '-' + ten as nppTen from NHAPHANPHOI where trangthai = '1' and loainpp='4'  ";
			System.out.println("jdfgdjfg...."+sql);
			this.dtRs = db.get(sql);
			
			 sql = "select pk_seq, isnull(maFAST,'') + '-' + ten as khTen from KHACHHANG where trangthai = '1' ";
				this.khRs = db.get(sql);
				
				 sql = "select pk_seq, CAST(pk_seq as nvarchar(20)) + '-' + ten as Ten from NHANVIENGIAONHAN where trangthai = '1' ";
					this.nvgnRs = db.get(sql);
					
				
				sql = " select B.PK_SEQ , CAST(B.pk_seq as nvarchar(20)) + ' - ' + B.ten as Ten " +
					  " from DAIDIENKINHDOANH_NPP A INNER JOIN DAIDIENKINHDOANH B ON A.DDKD_FK= B.PK_SEQ " +
					  " where B.TRANGTHAI = '1' ";
					
					this.nvbhRs = db.get(sql);	
		}
		else
		{
			String sql = "select pk_seq, isnull(maFAST,'') + '-' + ten as nppTen from NHAPHANPHOI where trangthai = '1' and loainpp='4'";
			System.out.println("jdfgdjfg...."+sql);
			this.dtRs = db.get(sql);
			
			
			sql = "select pk_seq, isnull(maFAST,'') + '-' + ten as nppTen from NHAPHANPHOI where trangthai = '1' and loainpp='4' AND TRUCTHUOC_FK ='"+ this.nppId +"' ";
			System.out.println("jdfgdjfg...."+sql);
			this.dtRs = db.get(sql);
			
			 sql = "select pk_seq, isnull(maFAST,'') + '-' + ten as khTen from KHACHHANG where trangthai = '1' and npp_fk ='"+ this.nppId +"' ";
				this.khRs = db.get(sql);
				
				 sql = "select pk_seq, CAST(pk_seq as nvarchar(20)) + '-' + ten as Ten from NHANVIENGIAONHAN where trangthai = '1' and npp_fk ='"+ this.nppId +"' ";
					this.nvgnRs = db.get(sql);
					
				
				sql = " select B.PK_SEQ , CAST(B.pk_seq as nvarchar(20)) + ' - ' + B.ten as Ten " +
					  " from DAIDIENKINHDOANH_NPP A INNER JOIN DAIDIENKINHDOANH B ON A.DDKD_FK= B.PK_SEQ " +
					  " where  A.npp_fk ='"+ this.nppId +"' AND B.TRANGTHAI = '1' ";
					
					this.nvbhRs = db.get(sql);
		}	
		
	}

	public void initExcel() 
	{
		Utility ut = new Utility();
		this.nppId = ut.getIdNhapp(userId);
		String sql = "";
		
		dbutils db = new dbutils();
		
		sql =  "Select TEN, DIACHI, MASOTHUE " +
			   "From NHAPHANPHOI " +
			   "Where PK_SEQ = '"+ this.nppId +"'  ";
		this.RsErpCongty = db.get(sql);
		
		
		try{
			
			String conditionOTC = "";
			String conditionETC = "";
			String conditionTT = "";
			String conditionHDKHAC = "";
			
			if(this.tuNgay.trim().length() > 0)
			{
				conditionOTC += " and HOADON.NGAYHOADON >='"+ this.tuNgay +"' ";
				conditionETC += " and HOADON.NGAYHOADON >='"+ this.tuNgay +"' ";
				conditionTT +=  " and HOADON.NGAYHOADON >='"+ this.tuNgay +"' ";
			}
			if(this.denNgay.trim().length() > 0)
			{
				conditionOTC += " and HOADON.NGAYHOADON <='"+ this.denNgay +"' \n ";
				conditionETC += " and HOADON.NGAYHOADON <='"+ this.denNgay +"' \n ";
				conditionTT +=  " and HOADON.NGAYHOADON <='"+ this.denNgay +"' \n ";
			}
			if (this.khIds.trim().length() > 0)
			{
				conditionOTC += " and HOADON.KHID in ('"+ this.khIds +"')  ";
				conditionETC += " and HOADON.KHID in ('"+ this.khIds +"')  ";
				conditionTT += " and HOADON.KHID in ('"+ this.khIds +"')  ";
			}
			if (this.nvbhIds.trim().length() > 0)
			{
				conditionETC +=
					"    AND ( HOADON.KHID   in (\n"+
					"                                     	SELECT c.KHACHHANG_FK \n"+
					"                                     	FROM   DAIDIENKINHDOANH a inner join TUYENBANHANG b on b.DDKD_FK=a.PK_SEQ \n"+
					"                                            	inner join KHACHHANG_TUYENBH c on c.TBH_FK=b.PK_SEQ \n"+
					"                                     	WHERE  a.PK_SEQ in ("+this.nvbhIds+") \n"+
					"                               ) \n" +
					"		 ) \n";
				 
				conditionTT +=
					"    AND ( HOADON.KHID   in (\n"+
					"                                     	SELECT c.KHACHHANG_FK \n"+
					"                                     	FROM   DAIDIENKINHDOANH a inner join TUYENBANHANG b on b.DDKD_FK=a.PK_SEQ \n"+
					"                                            	inner join KHACHHANG_TUYENBH c on c.TBH_FK=b.PK_SEQ \n"+
					"                                     	WHERE  a.PK_SEQ in ("+this.nvbhIds+") \n"+
					"                               ) \n" +
					"		 ) \n";
				
					conditionOTC +=
/*					"    AND ( HD.PK_SEQ  in \n" +
					"                    ( SELECT C.PK_SEQ " +
					"                      FROM DONHANG A INNER JOIN HOADON_DDH B ON A.PK_SEQ = B.DDH_FK \n" +
					"                                     INNER JOIN HOADON C ON B.HOADON_FK= C.PK_SEQ \n" +
					"                      WHERE C.TRANGTHAI in (2,4) AND A.DDKD_FK IN (" + this.nvbhIds + ") ) ) \n" ;*/
					"    AND ( HOADON.KHID  in (\n"+
					"                                     	SELECT c.KHACHHANG_FK \n"+
					"                                     	FROM   DAIDIENKINHDOANH a inner join TUYENBANHANG b on b.DDKD_FK=a.PK_SEQ \n"+
					"                                            	inner join KHACHHANG_TUYENBH c on c.TBH_FK=b.PK_SEQ \n"+
					"                                     	WHERE  a.PK_SEQ in ("+this.nvbhIds+") \n"+
					"                               ) \n" +
					"		  )\n";
					
			}
			if (this.nvgnIds.trim().length() > 0)
			{
				conditionOTC += 
					" AND HOADON.KHID  in ( SELECT KHACHHANG_FK FROM NVGN_KH WHERE NVGN_FK IN ( "+ this.nvgnIds +" )) ";
				conditionETC += " AND HOADON.KHID  in ( SELECT KHACHHANG_FK FROM NVGN_KH WHERE NVGN_FK IN ( "+ this.nvgnIds +" )) ";
			}
			if(this.dtIds.trim().length() > 0)
			{
				conditionETC += " AND NPP.PK_SEQ in (" + this.dtIds + ") ";
				//conditionOTC += " AND HD.PK_SEQ < 0 ";
				
			}
			
			if(this.nppId!=null)
			{/*
				sql= " SELECT hd.PK_SEQ hoadonId, hd.KYHIEU, hd.NGAYXUATHD, hd.SOHOADON as sohoadon, hd.NGAYXUATHD as ngayhoadon," +
					 "        hd.tongtienavat - ISNULL(THUTIENHD.SOTIENDATHU,0) as TongTien, kh.maFAST, kh.TEN as tenkh \n "+
					 " FROM   HOADON hd left join KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ \n"+
					 "                left join NHAPHANPHOI npp on hd.NPP_FK=npp.PK_SEQ \n"+
					 "                left join" +
					 "		  ( SELECT 	tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as SOTIENDATHU \n"+
					 " 			FROM 	ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK \n"+ 
					 " 			WHERE 	tt.TRANGTHAI = 1 \n"+  
					 " 			GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK \n"+ 
					 " 		  ) \n"+ 
					 " 			THUTIENHD  on hd.PK_SEQ=THUTIENHD.HOADONNPP_FK and hd.KHACHHANG_FK =THUTIENHD.KHACHHANG_FK \n"+  
					 " WHERE hd.LOAIHOADON = '0'  and hd.TRANGTHAI in (2,4)  \n " +
					 " 		  and hd.NPP_FK = '"+this.nppId+ "' "+ conditionOTC +" " +
					 " 		  and  (hd.tongtienavat - ISNULL(THUTIENHD.SOTIENDATHU,0)) > 0 \n ";
				 
				
			sql += 	 " UNION ALL \n "+
			 		 " SELECT hd.PK_SEQ hoadonId,hd.KYHIEU,hd.NGAYXUATHD, hd.SOHOADON as sohoadon, hd.NGAYXUATHD as ngayhoadon," +
			 		 "        (hd.tongtienavat - ISNULL(THUTIENHD.SOTIENDATHU,0)) as TongTien, " +
			 		 "        CASE WHEN hd.KHACHHANG_FK is not null THEN kh.maFAST ELSE cast(npp.PK_SEQ as nvarchar(50) ) END mafast, " +
			 		 "        CASE WHEN hd.KHACHHANG_FK is not null THEN kh.TEN    ELSE npp.TEN  END tenkh \n"+
			 		 " FROM (" +
			 		 " 		  SELECT hd.TRANGTHAI,hd.NGAYTAO,hd.KYHIEU,hdETC.NPP_FK,hdETC.KHACHHANG_FK, hdETC.PK_SEQ HOADON_FK,hdETC.SOHOADON,hdETC.NGAYXUATHD,kh.PK_SEQ, \n"+  
			 		 "				 round(SUM(hdETC.AVAT - hdETC.AVAT_CK),0) as tongtienavat \n"+  
			 		 " 		  FROM \n"+ 
			 		 "			  (	SELECT  ETC.PK_SEQ,ETC.NGAYXUATHD, ETC.SOHOADON,ETC.KHACHHANG_FK,ETC.ddkd_fk,ETC.PK_SEQ as HOADONNPP_FK,npp_fk, \n"+ 
			 		 "						sum(soluong) as soluong, ( sum( soluong * dongia ) / sum(soluong) ) as dongia, \n"+  
			 		 "						sum( soluong * dongia )  as BVAT,( sum( soluong * dongia*thuexuat/100 ) ) as VAT, \n"+ 
			 		 "						sum( soluong * dongia*(1+thuexuat/100 ) ) as AVAT, \n"+ 
			 		 "						sum(isnull(chietkhau,0)*(1+thuexuat/100)) as AVAT_CK, \n"+ 
			 		 "						sum(isnull(thuexuat,0)) as BVAT_CK \n"+ 
			 		 "				FROM ( \n"+
			 		 "						SELECT  c.HOADON_FK as PK_SEQ,a.NGAYXUATHD,a.SOHOADON,a.KHACHHANG_fk,c.HOADON_FK as HOADONNPP_FK, a.NPP_FK,c.chietkhau,c.vat, \n"+  
			 		 "								( \n"+
			 		 "								  SELECT  top(1) bb.DDKD_FK  \n"+
			 		 "								  FROM 	  ERP_HOADONNPP_DDH aa inner join ERP_DONDATHANGNPP bb on bb.PK_SEQ=aa.DDH_FK \n"+  
			 		 "								  WHERE   aa.HOADONNPP_FK=c.HOADON_FK \n"+ 
			 		 "								) as ddkd_fk , \n"+
			 		 "								case when c.donvitinh = e.donvi then c.soluong else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong,  \n"+
			 		 "								case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia, c.vat as thuexuat \n"+  
			 		 "						FROM 	ERP_HOADONNPP a \n"+
			 		 "								inner join ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk \n"+ 
			 		 "								inner join SANPHAM d on c.sanpham_fk = d.pk_seq \n"+
			 		 "								inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq \n"+
			 		 "						WHERE 	1=1 and c.SOLUONG > 0 and a.trangthai in ( 2 , 4 ) \n"+
			 		 "								and a.NgayXuatHD>='"+tuNgay+"' \n"+
			 		 "								and a.NgayXuatHD<='"+denNgay+"' \n"+ 
			 		 "					)ETC \n"+
				  	 "	    		GROUP BY ETC.PK_SEQ,ETC.NGAYXUATHD, ETC.SOHOADON,ETC.KHACHHANG_FK,ETC.ddkd_fk,ETC.PK_SEQ,npp_fk \n"+  
				  	 " 			   )as hdETC inner join ERP_HOADONNPP hd on hd.PK_SEQ=hdETC.HOADONNPP_FK \n"+
				  	 "	   	  		left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ=hdETC.DDKD_FK \n"+
				  	 "	   	  		inner join KHACHHANG kh on kh.PK_SEQ=hdETC.KHACHHANG_FK \n"+
				  	 "	   	  		inner join NHAPHANPHOI npp on npp.PK_SEQ=hdETC.NPP_FK \n"+ 
				  	 " 			WHERE 1=1 and hd.NPP_FK ='"+nppId+"' \n"+conditionETC+					
				  	 " 			GROUP BY hdETC.NPP_FK,hdETC.KHACHHANG_FK, hdETC.SOHOADON,hdETC.NGAYXUATHD,kh.PK_SEQ,hdETC.PK_SEQ,hd.TRANGTHAI,hd.NGAYTAO,hd.KYHIEU \n"+
				  	 " 		)  \n"+			 		
				  	 " 		hd left join KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ \n" +
				  	 "         left join NHAPHANPHOI npp on hd.NPP_FK=npp.PK_SEQ \n" +
				  	 "         left join" +
				  	 "			( SELECT tthd.KHACHHANG_FK,tthd.HOADONNPP_FK, SUM(tthd.SOTIENTT) as SOTIENDATHU \n"+ 
				  	 " 		  	  FROM 	 ERP_THUTIENNPP_HOADON tthd inner join ERP_THUTIENNPP tt on  tthd.THUTIENNPP_FK = tt.PK_SEQ \n"+ 
				  	 " 		  	  WHERE  tt.TRANGTHAI = 1 \n"+ 
				  	 "  		  GROUP BY tthd.HOADONNPP_FK,tthd.KHACHHANG_FK \n"+ 
				  	 "			) THUTIENHD on hd.PK_SEQ = THUTIENHD.HOADONNPP_FK and hd.KHACHHANG_FK = THUTIENHD.KHACHHANG_FK \n"+
				  	 " WHERE  hd.TRANGTHAI in (2,4) "+
				  	 "        and hd.NPP_FK='"+ this.nppId+"'  "+ conditionETC +" " +
				  	 "        and  (hd.tongtienavat - ISNULL(THUTIENHD.SOTIENDATHU,0)) > 0 \n ";	 			 
				 
			 sql+=	 " ORDER BY hd.NGAYXUATHD";
			*/
				
			sql =
			// HOA DON ETC
			" SELECT HOADON.PK_SEQ HOADONID, HOADON.KYHIEU, HOADON.NGAYHOADON , HOADON.SOHOADON, HOADON.TONGTIENAVAT, \n"+
			"	   ( CAST(ISNULL(HOADON.TONGTIENAVAT, 0) as numeric(18,0)) + CAST(ISNULL(bthd.GHINO,0) as numeric(18,0))-CAST(ISNULL(bthd.GHICO,0) as numeric(18,0)) - CAST(ISNULL(DATHANHTOAN.DATHANHTOAN, 0)as numeric(18,0)  ) - CAST(ISNULL(XOANO.SOTIENXOA,0) as numeric(18,0) ) ) AS TONGTIEN, \n"+ 
			"	   HOADON.maFAST, HOADON.TEN as TENKH \n"+
			" FROM ( \n"+	
			"		SELECT	KH.PK_SEQ AS KHID,KH.MAFAST, KH.TEN, NPP.PK_SEQ as NPPID,NPP.MAFAST + '-' + NPP.TEN as MANPP, HD.PK_SEQ, HD.KYHIEU, HD.SOHOADON, HD.NGAYXUATHD AS NGAYHOADON, HD.TRANGTHAI , HD.TONGTIENAVAT   AS TONGTIENAVAT \n"+
			"		FROM	ERP_HOADONNPP HD  LEFT join NHAPHANPHOI NPP on HD.NPP_DAT_FK= NPP.PK_SEQ AND NPP.LOAINPP = 4 and NPP.TRUCTHUOC_FK = '"+this.nppId+"'  \n"+
			"				LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = HD.KHACHHANG_FK  \n"+
			"		WHERE   HD.TRANGTHAI in (2,4) and HD.NPP_FK = '"+this.nppId+"'   \n"+
			"	 ) HOADON \n"+
			"	 LEFT JOIN ( \n"+
			"		SELECT HOADONNPP_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN \n"+
			"		FROM  \n"+
			"			( 	\n"+
			"			SELECT TTHD.HOADONNPP_FK , SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n"+
			"			FROM ERP_THUTIENNPP_HOADON TTHD \n"+
			"			INNER JOIN ERP_THUTIENNPP TT ON TTHD.THUTIENNPP_FK = TT.PK_SEQ \n"+ 
			"			WHERE TT.NPP_FK= '"+this.nppId+"' AND  TT.TRANGTHAI NOT IN (2) \n"+
			" 			GROUP BY HOADONNPP_FK \n"+
			"			) HOADONDATT  \n"+
			"		GROUP BY HOADONNPP_FK \n"+
			"	 )DATHANHTOAN ON HOADON.PK_SEQ = DATHANHTOAN.HOADONNPP_FK 	 \n"+
			"	 LEFT JOIN ( \n"+
			"		 SELECT HOADON_FK, SOTIENXOA \n"+ 
			"		 FROM XOANOKHACHHANG XN INNER JOIN XOANOKHACHHANG_HOADON XNHD ON XN.PK_SEQ = XNHD.XNKH_FK \n"+ 
			"		 WHERE  XN.TRANGTHAI = 1 AND XNHD.KHACHHANG_FK IN (SELECT PK_SEQ FROM KHACHHANG WHERE KBH_FK = '100052') \n"+     
			"	) XOANO ON HOADON.PK_SEQ = XOANO.HOADON_FK \n"+
			"	 LEFT JOIN ( \n"+
			"		  select bthd.HOADON_FK,bthd.KHACHHANG_FK, SUM(ISNULL(bthd.GHINO,0)) as GHINO, SUM(ISNULL(bthd.GHICO,0)) as GHICO \n"+
			"		  from BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK \n"+
			"		  where bt.TRANGTHAI = 1 \n"+
			"		  group by bthd.HOADON_FK, bthd.KHACHHANG_FK \n"+
			"	)bthd on HOADON.PK_SEQ = bthd.HOADON_FK and bthd.KHACHHANG_FK = HOADON.KHID \n"+
			"    LEFT JOIN NHAPHANPHOI npp on npp.PK_SEQ = HOADON.NPPID \n"+	
			"	 WHERE CAST(ISNULL(HOADON.TONGTIENAVAT, 0) as numeric(18,0)) + CAST(ISNULL(bthd.GHINO,0) as numeric(18,0))-CAST(ISNULL(bthd.GHICO,0) as numeric(18,0)) - CAST(ISNULL(DATHANHTOAN.DATHANHTOAN, 0)as numeric(18,0)  ) - CAST(ISNULL(XOANO.SOTIENXOA,0) as numeric(18,0) )  > 0 \n"+conditionETC+
			
					
			" UNION ALL \n"+
			
			//HOA DON OTC
			" SELECT HOADON.PK_SEQ HOADONID, HOADON.KYHIEU, HOADON.NGAYHOADON , HOADON.SOHOADON, HOADON.TONGTIENAVAT, \n"+
			"	   ( CAST(ISNULL(HOADON.TONGTIENAVAT, 0) as numeric(18,0)) - CAST(ISNULL(DATHANHTOAN.DATHANHTOAN, 0)as numeric(18,0)) - CAST(ISNULL(XOANO.SOTIENXOA,0) as numeric(18,0)) - CAST(ISNULL(CTCN.SOTIENCANTRU, 0) as numeric(18,0))) AS TongTien, \n"+ 
			"	   HOADON.maFAST, HOADON.TEN as TENKH \n"+
			" FROM ( \n"+ 
			"		SELECT KH.PK_SEQ AS KHID,KH.MAFAST,HD.NPP_FK NPPID, KH.TEN,HD.PK_SEQ, HD.KYHIEU, HD.SOHOADON, HD.NGAYXUATHD AS NGAYHOADON,HD.TRANGTHAI, \n"+  
			"			  (HD.TONGTIENAVAT  ) as TONGTIENAVAT \n"+   
			"        FROM HOADON HD LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = HD.KHACHHANG_FK \n"+ 
			"        WHERE  ISNULL(HD.LOAIHOADON,0) = 0 AND HD.TRANGTHAI in (2,4) and HD.NPP_FK = '"+this.nppId+"' \n"+    
			"	) HOADON \n"+ 
			"	LEFT JOIN \n"+ 
			"	( \n"+ 
			"		SELECT HOADONNPP_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN \n"+ 
			"		FROM  \n"+
			"		( 	\n" +			
			"			SELECT TTHD.HOADONNPP_FK , SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n"+
			"			FROM ERP_THUTIENNPP_HOADON TTHD \n"+
			"			INNER JOIN ERP_THUTIENNPP TT ON TTHD.THUTIENNPP_FK = TT.PK_SEQ \n"+
			"			WHERE TT.NPP_FK= '"+this.nppId+"' AND  TT.TRANGTHAI NOT IN (2) \n"+
			"			GROUP BY HOADONNPP_FK \n"+
			"		) HOADONDATT  \n"+
			"		GROUP BY HOADONNPP_FK \n"+ 
			"	)DATHANHTOAN ON HOADON.PK_SEQ = DATHANHTOAN.HOADONNPP_FK \n"+
			"	LEFT JOIN \n"+
			"	( \n"+
			"		 SELECT HOADON_FK, SOTIENXOA \n"+
			"		 FROM XOANOKHACHHANG XN INNER JOIN XOANOKHACHHANG_HOADON XNHD ON XN.PK_SEQ = XNHD.XNKH_FK \n"+
			"		 WHERE XN.NPP_FK = '"+this.nppId+"' and XN.TRANGTHAI = 1 AND XNHD.KHACHHANG_FK IN (SELECT PK_SEQ FROM KHACHHANG WHERE KBH_FK = '100025')   \n"+  
			"	) XOANO ON HOADON.PK_SEQ = XOANO.HOADON_FK \n"+
			"	LEFT JOIN \n"+
			"	( \n"+
			"     SELECT CT_HD.HOADON_FK, SUM(SOTIENCANTRU) as SOTIENCANTRU \n"+
			"     FROM CANTRUCONGNO CT INNER JOIN CANTRUCONGNO_HOADON CT_HD ON CT.PK_SEQ = CT_HD.CANTRUCONGNO_FK  \n"+
			"     WHERE  CT.TRANGTHAI = 1 AND CT.NPP_FK = '"+this.nppId+"' group by  CT_HD.HOADON_FK     \n"+
			"	) CTCN ON HOADON.PK_SEQ = CTCN.HOADON_FK \n"+
			"	LEFT JOIN ( \n"+
			"		  SELECT bthd.HOADON_FK,bthd.KHACHHANG_FK, SUM(ISNULL(bthd.GHINO,0)) as GHINO, SUM(ISNULL(bthd.GHICO,0)) as GHICO \n"+
			"		  FROM BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK \n"+
			"		  WHERE bt.NPP_FK = '"+this.nppId+"' and bt.TRANGTHAI = 1 \n"+
			"		  GROUP BY bthd.HOADON_FK, bthd.KHACHHANG_FK \n"+
			"	)bthd on HOADON.PK_SEQ = bthd.HOADON_FK and bthd.KHACHHANG_FK = HOADON.KHID \n"+
			"    LEFT JOIN NHAPHANPHOI npp on npp.PK_SEQ = HOADON.NPPID \n"+	
			"	WHERE CAST(ISNULL(HOADON.TONGTIENAVAT, 0) as numeric(18,0)) + CAST(ISNULL(bthd.GHINO,0) as numeric(18,0))-CAST(ISNULL(bthd.GHICO,0) as numeric(18,0)) - CAST(ISNULL(DATHANHTOAN.DATHANHTOAN, 0)as numeric(18,0)  ) - CAST(ISNULL(XOANO.SOTIENXOA,0) as numeric(18,0)) - CAST(ISNULL(CTCN.SOTIENCANTRU, 0) as numeric(18,0))  > 0  \n"+conditionOTC+ 
			
			" UNION ALL \n"+
			
			//HOA DON KHAC
			" SELECT HOADON.PK_SEQ HOADONID, HOADON.KYHIEU, HOADON.NGAYHOADON , HOADON.SOHOADON, HOADON.TONGTIENAVAT, \n"+
			"	   ( CAST(ISNULL(HOADON.TONGTIENAVAT, 0) as numeric(18,0)) - CAST(ISNULL(DATHANHTOAN.DATHANHTOAN, 0)as numeric(18,0)) - CAST(ISNULL(XOANO.SOTIENXOA,0) as numeric(18,0))) AS TongTien, \n"+ 
			"	   HOADON.maFAST, HOADON.TEN as TENKH \n"+
			" FROM ( \n"+ 
			"		 SELECT KH.PK_SEQ AS KHID,KH.MAFAST,HD.NPP_FK NPPID, KH.TEN,HD.PK_SEQ, HD.KYHIEUHOADON KYHIEU, HD.SOHOADON, HD.NGAYHOADON AS NGAYHOADON,HD.TRANGTHAI, \n"+  
			"			  (HD.AVAT  ) as TONGTIENAVAT \n"+   
			"        FROM ERP_HOADONPHELIEU HD LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = HD.KHACHHANG_FK \n"+ 
			"        WHERE  HD.TRANGTHAI  =  1 \n"+    
			"		) HOADON \n"+ 
			"		LEFT JOIN \n"+ 
			"		( \n"+ 
			"			SELECT HOADONNPP_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN \n"+ 
			"			FROM  \n"+
			"				( 	\n" +			
			"					SELECT 	TTHD.HOADONNPP_FK , SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n"+
			"					FROM 	ERP_THUTIENNPP_HOADON TTHD \n"+
			"							INNER JOIN ERP_THUTIENNPP TT ON TTHD.THUTIENNPP_FK = TT.PK_SEQ \n"+
			"					WHERE 	TT.TRANGTHAI NOT IN (2) AND TTHD.LOAIHD = 1  \n"+
			"					GROUP BY HOADONNPP_FK \n"+
			"				) HOADONDATT  \n"+
			"			GROUP BY HOADONNPP_FK \n"+ 
			"		)DATHANHTOAN ON HOADON.PK_SEQ = DATHANHTOAN.HOADONNPP_FK \n"+
			"		LEFT JOIN \n"+
			"		( \n"+
			"		 	SELECT 	HOADON_FK, SOTIENXOA \n"+
			"		 	FROM 	XOANOKHACHHANG XN INNER JOIN XOANOKHACHHANG_HOADON XNHD ON XN.PK_SEQ = XNHD.XNKH_FK \n"+
			"		 	WHERE  	XN.TRANGTHAI = 1 AND XNHD.LOAIHD = 1  \n"+  
			"		) XOANO ON HOADON.PK_SEQ = XOANO.HOADON_FK \n"+			
			"    	LEFT JOIN NHAPHANPHOI npp on npp.PK_SEQ = HOADON.NPPID \n"+	
			"		WHERE CAST(ISNULL(HOADON.TONGTIENAVAT, 0) as numeric(18,0)) - CAST(ISNULL(DATHANHTOAN.DATHANHTOAN, 0)as numeric(18,0)  ) - CAST(ISNULL(XOANO.SOTIENXOA,0) as numeric(18,0))  > 0  \n"+conditionTT+ 
			
			" UNION ALL \n"+
			
			//DƯ NỢ
			" SELECT HOADON.PK_SEQ HOADONID, HOADON.KYHIEU, HOADON.NGAYHOADON , HOADON.SOHOADON, HOADON.TONGTIENAVAT, \n"+
			"	   ( CAST(ISNULL(HOADON.TONGTIENAVAT, 0) as numeric(18,0)) - CAST(ISNULL(DATHANHTOAN.DATHANHTOAN, 0)as numeric(18,0)) - CAST(ISNULL(XOANO.SOTIENXOA,0) as numeric(18,0))) AS TongTien, \n"+ 
			"	   HOADON.maFAST, HOADON.TEN as TENKH \n"+
			" FROM ( \n"+ 
			"		 SELECT KH.PK_SEQ AS KHID,KH.MAFAST,HD.NPP_FK NPPID, KH.TEN,HD.PK_SEQ, '' KYHIEU, '' SOHOADON, HD.NGAYDUNO AS NGAYHOADON, \n"+  
			"			   (HD.SONO) as TONGTIENAVAT \n"+   
			"        FROM   DUNO_KHACHHANG HD LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = HD.KHACHHANG_FK \n"+ 
			"		) HOADON \n"+ 
			"		LEFT JOIN \n"+ 
			"		( \n"+ 
			"			SELECT HOADONNPP_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN \n"+ 
			"			FROM  \n"+
			"				( 	\n" +			
			"					SELECT 	TTHD.HOADONNPP_FK , SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n"+
			"					FROM 	ERP_THUTIENNPP_HOADON TTHD \n"+
			"							INNER JOIN ERP_THUTIENNPP TT ON TTHD.THUTIENNPP_FK = TT.PK_SEQ \n"+
			"					WHERE 	TT.TRANGTHAI NOT IN (2) AND TTHD.LOAIHD = 1  \n"+
			"					GROUP BY HOADONNPP_FK \n"+
			"				) HOADONDATT  \n"+
			"			GROUP BY HOADONNPP_FK \n"+ 
			"		)DATHANHTOAN ON HOADON.PK_SEQ = DATHANHTOAN.HOADONNPP_FK \n"+
			"		LEFT JOIN \n"+
			"		( \n"+
			"		 	SELECT 	HOADON_FK, SOTIENXOA \n"+
			"		 	FROM 	XOANOKHACHHANG XN INNER JOIN XOANOKHACHHANG_HOADON XNHD ON XN.PK_SEQ = XNHD.XNKH_FK \n"+
			"		 	WHERE  	XN.TRANGTHAI = 1 AND XNHD.LOAIHD = 1  \n"+  
			"		) XOANO ON HOADON.PK_SEQ = XOANO.HOADON_FK \n"+			
			"    	LEFT JOIN NHAPHANPHOI npp on npp.PK_SEQ = HOADON.NPPID \n"+	
			"		WHERE CAST(ISNULL(HOADON.TONGTIENAVAT, 0) as numeric(18,0)) - CAST(ISNULL(DATHANHTOAN.DATHANHTOAN, 0)as numeric(18,0)  ) - CAST(ISNULL(XOANO.SOTIENXOA,0) as numeric(18,0))  > 0  \n"+conditionTT+ 
			  
			" ORDER BY HOADON.NGAYHOADON DESC "; 
			
			}
			
			if(this.nppId==null)
			{			 
				
				/*sql += 
					" SELECT hd.PK_SEQ hoadonId,hd.KYHIEU,hd.NGAYXUATHD, hd.SOHOADON as sohoadon, hd.NGAYXUATHD as ngayhoadon," +
			 		"        (hd.tongtienavat - ISNULL(THUTIENHD.SOTIENDATHU,0)) as TongTien, cast(npp.PK_SEQ as nvarchar(50))as maFAST, npp.TEN as tenkh \n"+
			 		" FROM 	 ERP_HOADON  hd left join NHAPHANPHOI npp on hd.NPP_FK=npp.PK_SEQ \n" +
			 		"               	    left join" +
					"                		( SELECT tthd.HOADON_FK, SUM(tthd.SOTIENTT) as SOTIENDATHU \n"+
					"                 		  FROM 	 ERP_THUTIEN_HOADON tthd inner join ERP_THUTIEN tt on  tthd.THUTIEN_FK = tt.PK_SEQ \n"+
					"                 		  WHERE  tt.TRANGTHAI != 2  \n"+
					"                  		  GROUP BY tthd.HOADON_FK \n"+
					"                		) THUTIENHD on hd.PK_SEQ = THUTIENHD.HOADON_FK  \n"+
			 		" WHERE  hd.TRANGTHAI in (2,4) "+
			 		"        and  (hd.tongtienavat - ISNULL(THUTIENHD.SOTIENDATHU,0)) > 0  "+conditionTT+" \n ";	 	 			 
				 
				sql+= " ORDER BY hd.NGAYXUATHD \n";*/
				
				sql+=
				// HOA DON ETC
				" SELECT HOADON.PK_SEQ HOADONID, HOADON.KYHIEU, HOADON.NGAYHOADON , HOADON.SOHOADON, HOADON.TONGTIENAVAT, \n"+
				"	   ( CAST(ISNULL(HOADON.TONGTIENAVAT, 0) as numeric(18,0)) + CAST(ISNULL(bthd.GHINO,0) as numeric(18,0))-CAST(ISNULL(bthd.GHICO,0) as numeric(18,0)) - CAST(ISNULL(DATHANHTOAN.DATHANHTOAN, 0)as numeric(18,0)  ) - CAST(ISNULL(XOANO.SOTIENXOA,0) as numeric(18,0) ) ) AS TONGTIEN, \n"+ 
				"	   HOADON.maFAST, HOADON.TEN as TENKH \n"+
				" FROM ( \n"+	
				"			SELECT	KH.PK_SEQ AS KHID,KH.MAFAST, KH.TEN, NPP.PK_SEQ as NPPID,NPP.MAFAST + '-' + NPP.TEN as MANPP, HD.PK_SEQ, HD.KYHIEU, HD.SOHOADON, HD.NGAYXUATHD AS NGAYHOADON, HD.TRANGTHAI , HD.TONGTIENAVAT   AS TONGTIENAVAT \n"+
				"			FROM	ERP_HOADONNPP HD  LEFT join NHAPHANPHOI NPP on HD.NPP_DAT_FK= NPP.PK_SEQ \n"+
				"					LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = HD.KHACHHANG_FK  \n"+
				"			WHERE   HD.TRANGTHAI in (2,4)  \n"+
				"	 	) HOADON \n"+
				"	 	LEFT JOIN ( \n"+
				"					SELECT HOADONNPP_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN \n"+
				"					FROM  \n"+
				"						( 	\n"+
				"							SELECT 	TTHD.HOADONNPP_FK , SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n"+
				"							FROM 	ERP_THUTIENNPP_HOADON TTHD \n"+
				"									INNER JOIN ERP_THUTIENNPP TT ON TTHD.THUTIENNPP_FK = TT.PK_SEQ \n"+ 
				"							WHERE  TT.TRANGTHAI NOT IN (2) AND TTHD.LOAIHD = 0 \n"+
				" 							GROUP BY HOADONNPP_FK \n"+
				"						) HOADONDATT  \n"+
				"					GROUP BY HOADONNPP_FK \n"+
				"	 			   )DATHANHTOAN ON HOADON.PK_SEQ = DATHANHTOAN.HOADONNPP_FK 	 \n"+
				"	 	LEFT JOIN ( \n"+
				"		 			SELECT 	HOADON_FK, SOTIENXOA \n"+ 
				"		 			FROM 	XOANOKHACHHANG XN INNER JOIN XOANOKHACHHANG_HOADON XNHD ON XN.PK_SEQ = XNHD.XNKH_FK \n"+ 
				"		 			WHERE  	XN.TRANGTHAI = 1 AND XNHD.KHACHHANG_FK IN (SELECT PK_SEQ FROM KHACHHANG WHERE KBH_FK = '100052') AND XNHD.LOAIHD = 0 \n"+     
				"				  ) XOANO ON HOADON.PK_SEQ = XOANO.HOADON_FK \n"+
				"	 	LEFT JOIN ( \n"+
				"		  			SELECT 	bthd.HOADON_FK,bthd.KHACHHANG_FK, SUM(ISNULL(bthd.GHINO,0)) as GHINO, SUM(ISNULL(bthd.GHICO,0)) as GHICO \n"+
				"		  			FROM 	BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK \n"+
				"		  			WHERE 	bt.TRANGTHAI = 1 \n"+
				"		  			GROUP BY bthd.HOADON_FK, bthd.KHACHHANG_FK \n"+
				"				  )bthd on HOADON.PK_SEQ = bthd.HOADON_FK and bthd.KHACHHANG_FK = HOADON.KHID \n"+
				"    	LEFT JOIN NHAPHANPHOI npp on npp.PK_SEQ = HOADON.NPPID \n"+	
				" WHERE CAST(ISNULL(HOADON.TONGTIENAVAT, 0) as numeric(18,0)) + CAST(ISNULL(bthd.GHINO,0) as numeric(18,0))-CAST(ISNULL(bthd.GHICO,0) as numeric(18,0)) - CAST(ISNULL(DATHANHTOAN.DATHANHTOAN, 0)as numeric(18,0)  ) - CAST(ISNULL(XOANO.SOTIENXOA,0) as numeric(18,0) )  > 0 \n"+conditionTT+
										
			" UNION ALL \n"+
				
				//HOA DON OTC
				" SELECT HOADON.PK_SEQ HOADONID, HOADON.KYHIEU, HOADON.NGAYHOADON , HOADON.SOHOADON, HOADON.TONGTIENAVAT, \n"+
				"	   ( CAST(ISNULL(HOADON.TONGTIENAVAT, 0) as numeric(18,0)) - CAST(ISNULL(DATHANHTOAN.DATHANHTOAN, 0)as numeric(18,0)) - CAST(ISNULL(XOANO.SOTIENXOA,0) as numeric(18,0)) - CAST(ISNULL(CTCN.SOTIENCANTRU, 0) as numeric(18,0))) AS TongTien, \n"+ 
				"	   HOADON.maFAST, HOADON.TEN as TENKH \n"+
				" FROM ( \n"+ 
				"		SELECT KH.PK_SEQ AS KHID,KH.MAFAST,HD.NPP_FK NPPID, KH.TEN,HD.PK_SEQ, HD.KYHIEU, HD.SOHOADON, HD.NGAYXUATHD AS NGAYHOADON,HD.TRANGTHAI, \n"+  
				"			  (HD.TONGTIENAVAT  ) as TONGTIENAVAT \n"+   
				"       FROM HOADON HD LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = HD.KHACHHANG_FK \n"+ 
				"       WHERE  ISNULL(HD.LOAIHOADON,0) = 0 AND HD.TRANGTHAI in (2,4)  \n"+    
				"	   ) HOADON \n"+ 
				"	   LEFT JOIN \n"+ 
				"		( \n"+ 
				"			SELECT HOADONNPP_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN \n"+ 
				"			FROM  \n"+
				"				( 	\n" +			
				"					SELECT 	TTHD.HOADONNPP_FK , SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n"+
				"					FROM 	ERP_THUTIENNPP_HOADON TTHD \n"+
				"							INNER JOIN ERP_THUTIENNPP TT ON TTHD.THUTIENNPP_FK = TT.PK_SEQ \n"+
				"					WHERE 	TT.TRANGTHAI NOT IN (2) AND TTHD.LOAIHD = 0  \n"+
				"					GROUP BY HOADONNPP_FK \n"+
				"				) HOADONDATT  \n"+
				"			GROUP BY HOADONNPP_FK \n"+ 
				"		)DATHANHTOAN ON HOADON.PK_SEQ = DATHANHTOAN.HOADONNPP_FK \n"+
				"		LEFT JOIN \n"+
				"		( \n"+
				"		 SELECT HOADON_FK, SOTIENXOA \n"+
				"		 FROM XOANOKHACHHANG XN INNER JOIN XOANOKHACHHANG_HOADON XNHD ON XN.PK_SEQ = XNHD.XNKH_FK \n"+
				"		 WHERE  XN.TRANGTHAI = 1 AND XNHD.KHACHHANG_FK IN (SELECT PK_SEQ FROM KHACHHANG WHERE KBH_FK = '100025') AND  XNHD.LOAIHD = 0  \n"+  
				"		) XOANO ON HOADON.PK_SEQ = XOANO.HOADON_FK \n"+
				"		LEFT JOIN \n"+
				"		( \n"+
				"     		SELECT CT_HD.HOADON_FK, SUM(SOTIENCANTRU) as SOTIENCANTRU \n"+
				"     		FROM 	CANTRUCONGNO CT INNER JOIN CANTRUCONGNO_HOADON CT_HD ON CT.PK_SEQ = CT_HD.CANTRUCONGNO_FK  \n"+
				"     		WHERE   CT.TRANGTHAI = 1  " +
				"	  		GROUP BY  CT_HD.HOADON_FK     \n"+
				"		) CTCN ON HOADON.PK_SEQ = CTCN.HOADON_FK \n"+
				"		LEFT JOIN ( \n"+
				"		  	SELECT 	bthd.HOADON_FK,bthd.KHACHHANG_FK, SUM(ISNULL(bthd.GHINO,0)) as GHINO, SUM(ISNULL(bthd.GHICO,0)) as GHICO \n"+
				"		  	FROM 	BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK \n"+
				"		  	WHERE 	bt.TRANGTHAI = 1 \n"+
				"		  	GROUP BY bthd.HOADON_FK, bthd.KHACHHANG_FK \n"+
				"		)bthd on HOADON.PK_SEQ = bthd.HOADON_FK and bthd.KHACHHANG_FK = HOADON.KHID \n"+
				"    	LEFT JOIN NHAPHANPHOI npp on npp.PK_SEQ = HOADON.NPPID \n"+	
				" WHERE CAST(ISNULL(HOADON.TONGTIENAVAT, 0) as numeric(18,0)) + CAST(ISNULL(bthd.GHINO,0) as numeric(18,0))-CAST(ISNULL(bthd.GHICO,0) as numeric(18,0)) - CAST(ISNULL(DATHANHTOAN.DATHANHTOAN, 0)as numeric(18,0)  ) - CAST(ISNULL(XOANO.SOTIENXOA,0) as numeric(18,0)) - CAST(ISNULL(CTCN.SOTIENCANTRU, 0) as numeric(18,0))  > 0  \n"+conditionTT+ 
							
			" UNION ALL \n"+
				
				//HOA DON KHAC
				" SELECT HOADON.PK_SEQ HOADONID, HOADON.KYHIEU, HOADON.NGAYHOADON , HOADON.SOHOADON, HOADON.TONGTIENAVAT, \n"+
				"	   ( CAST(ISNULL(HOADON.TONGTIENAVAT, 0) as numeric(18,0)) - CAST(ISNULL(DATHANHTOAN.DATHANHTOAN, 0)as numeric(18,0)) - CAST(ISNULL(XOANO.SOTIENXOA,0) as numeric(18,0))) AS TongTien, \n"+ 
				"	   HOADON.maFAST, HOADON.TEN as TENKH \n"+
				" FROM ( \n"+ 
				"		 SELECT KH.PK_SEQ AS KHID,KH.MAFAST,HD.NPP_FK NPPID, KH.TEN,HD.PK_SEQ, HD.KYHIEUHOADON KYHIEU, HD.SOHOADON, HD.NGAYHOADON AS NGAYHOADON,HD.TRANGTHAI, \n"+  
				"			  (HD.AVAT  ) as TONGTIENAVAT \n"+   
				"        FROM ERP_HOADONPHELIEU HD LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = HD.KHACHHANG_FK \n"+ 
				"        WHERE  HD.TRANGTHAI  =  1 \n"+    
				"		) HOADON \n"+ 
				"		LEFT JOIN \n"+ 
				"		( \n"+ 
				"			SELECT HOADONNPP_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN \n"+ 
				"			FROM  \n"+
				"				( 	\n" +			
				"					SELECT 	TTHD.HOADONNPP_FK , SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n"+
				"					FROM 	ERP_THUTIENNPP_HOADON TTHD \n"+
				"							INNER JOIN ERP_THUTIENNPP TT ON TTHD.THUTIENNPP_FK = TT.PK_SEQ \n"+
				"					WHERE 	TT.TRANGTHAI NOT IN (2) AND TTHD.LOAIHD = 1  \n"+
				"					GROUP BY HOADONNPP_FK \n"+
				"				) HOADONDATT  \n"+
				"			GROUP BY HOADONNPP_FK \n"+ 
				"		)DATHANHTOAN ON HOADON.PK_SEQ = DATHANHTOAN.HOADONNPP_FK \n"+
				"		LEFT JOIN \n"+
				"		( \n"+
				"		 	SELECT 	HOADON_FK, SOTIENXOA \n"+
				"		 	FROM 	XOANOKHACHHANG XN INNER JOIN XOANOKHACHHANG_HOADON XNHD ON XN.PK_SEQ = XNHD.XNKH_FK \n"+
				"		 	WHERE  	XN.TRANGTHAI = 1 AND XNHD.LOAIHD = 1  \n"+  
				"		) XOANO ON HOADON.PK_SEQ = XOANO.HOADON_FK \n"+			
				"    	LEFT JOIN NHAPHANPHOI npp on npp.PK_SEQ = HOADON.NPPID \n"+	
				"		WHERE CAST(ISNULL(HOADON.TONGTIENAVAT, 0) as numeric(18,0)) - CAST(ISNULL(DATHANHTOAN.DATHANHTOAN, 0)as numeric(18,0)  ) - CAST(ISNULL(XOANO.SOTIENXOA,0) as numeric(18,0))  > 0  \n"+conditionTT+ 
				
				" UNION ALL \n"+
				
				//DƯ NỢ
				" SELECT HOADON.PK_SEQ HOADONID, HOADON.KYHIEU, HOADON.NGAYHOADON , HOADON.SOHOADON, HOADON.TONGTIENAVAT, \n"+
				"	   ( CAST(ISNULL(HOADON.TONGTIENAVAT, 0) as numeric(18,0)) - CAST(ISNULL(DATHANHTOAN.DATHANHTOAN, 0)as numeric(18,0)) - CAST(ISNULL(XOANO.SOTIENXOA,0) as numeric(18,0))) AS TongTien, \n"+ 
				"	   HOADON.maFAST, HOADON.TEN as TENKH \n"+
				" FROM ( \n"+ 
				"		 SELECT KH.PK_SEQ AS KHID,KH.MAFAST,HD.NPP_FK NPPID, KH.TEN,HD.PK_SEQ, '' KYHIEU, '' SOHOADON, HD.NGAYDUNO AS NGAYHOADON, \n"+  
				"			   (HD.SONO) as TONGTIENAVAT \n"+   
				"        FROM   DUNO_KHACHHANG HD LEFT JOIN KHACHHANG KH ON KH.PK_SEQ = HD.KHACHHANG_FK \n"+  
				"		) HOADON \n"+ 
				"		LEFT JOIN \n"+ 
				"		( \n"+ 
				"			SELECT HOADONNPP_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN \n"+ 
				"			FROM  \n"+
				"				( 	\n" +			
				"					SELECT 	TTHD.HOADONNPP_FK , SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n"+
				"					FROM 	ERP_THUTIENNPP_HOADON TTHD \n"+
				"							INNER JOIN ERP_THUTIENNPP TT ON TTHD.THUTIENNPP_FK = TT.PK_SEQ \n"+
				"					WHERE 	TT.TRANGTHAI NOT IN (2) AND TTHD.LOAIHD = 1  \n"+
				"					GROUP BY HOADONNPP_FK \n"+
				"				) HOADONDATT  \n"+
				"			GROUP BY HOADONNPP_FK \n"+ 
				"		)DATHANHTOAN ON HOADON.PK_SEQ = DATHANHTOAN.HOADONNPP_FK \n"+
				"		LEFT JOIN \n"+
				"		( \n"+
				"		 	SELECT 	HOADON_FK, SOTIENXOA \n"+
				"		 	FROM 	XOANOKHACHHANG XN INNER JOIN XOANOKHACHHANG_HOADON XNHD ON XN.PK_SEQ = XNHD.XNKH_FK \n"+
				"		 	WHERE  	XN.TRANGTHAI = 1 AND XNHD.LOAIHD = 1  \n"+  
				"		) XOANO ON HOADON.PK_SEQ = XOANO.HOADON_FK \n"+			
				"    	LEFT JOIN NHAPHANPHOI npp on npp.PK_SEQ = HOADON.NPPID \n"+	
				"		WHERE CAST(ISNULL(HOADON.TONGTIENAVAT, 0) as numeric(18,0)) - CAST(ISNULL(DATHANHTOAN.DATHANHTOAN, 0)as numeric(18,0)  ) - CAST(ISNULL(XOANO.SOTIENXOA,0) as numeric(18,0))  > 0  \n"+conditionTT+ 
				  
				" ORDER BY HOADON.NGAYHOADON DESC "; 
			}
				 
		System.out.println("CAU BAO CAO: " + sql);
		this.rs = db.get(sql);
		}catch(Exception e ){}		
	}


	public void setRs(ResultSet rs)
    {
		this.rs = rs;
    }

	public ResultSet getRsErpCongty() 
	{		
		return this.RsErpCongty;
	}




}
