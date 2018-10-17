package geso.traphaco.distributor.beans.reports.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.aspose.cells.BackgroundMode;
import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Font;
import com.aspose.cells.HorizontalAlignmentType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Erp_Item;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.reports.IBCChiTietCongNoHD;
import geso.traphaco.distributor.beans.reports.IBCChiTietTuoiNo;
import geso.traphaco.distributor.beans.reports.IBCCongNoTheoHD;
import geso.traphaco.distributor.db.sql.dbutils;

public class BCChiTietTuoiNo implements IBCChiTietTuoiNo {
	private String userId;
	private String userName;
	private String tuNgay;
	private String denNgay;
	private String nppId;
	private String ngayKS;
	private ResultSet rs, nhanhangRs;
	
	String msg;
	String dvkdId;
	String nhanhangId;
	String kBh;
	String nKh;
	ResultSet dvkdRs;
	ResultSet KbhRs;
	ResultSet NkhRs;
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
	String ctyId = "";
	private List<Erp_Item> loaiDoiTuongList;
	private List<Erp_Item> doiTuongList;
	private String loaiDoiTuong;
	private String doiTuong;
	private String loaiBC;
	private int buocNhay;
	private static final int NUMERIC_FORMAT = 43;
	private static final int NUMERIC_FORMAT_VND = 41;
	private static final int NUMERIC_FORMAT_TIGIA = 43;
	public BCChiTietTuoiNo(){
		this.userId = "";
		this.userName = "";
		this.tuNgay = "";
		this.denNgay = "";
		this.nppId = "";
		this.ngayKS = "";
		this.dvkdId="";
		this.msg= "";
		this.dtIds = "";
		this.khIds = "" ;
		this.nvbhIds = "";
		this.nvgnIds = "";
		this.kBh="";
		this.nKh="";
		this.rs = null;
		this.db = new dbutils();
		this.loaiDoiTuongList = new ArrayList<Erp_Item>();
		this.doiTuongList = new ArrayList<Erp_Item>();
		this.loaiDoiTuong = "";
		this.doiTuong = "";
		this.loaiBC = "0";// chi tiet
		this.buocNhay = 15;
	}

	
	

	public int getBuocNhay() {
		return buocNhay;
	}




	public String getNhanhangId() {
		return nhanhangId;
	}




	public void setNhanhangId(String nhanhangId) {
		this.nhanhangId = nhanhangId;
	}




	public ResultSet getNhanhangRs() {
		return nhanhangRs;
	}




	public void setNhanhangRs(ResultSet nhanhangRs) {
		this.nhanhangRs = nhanhangRs;
	}




	public void setBuocNhay(int buocNhay) {
		this.buocNhay = buocNhay;
	}




	public String getLoaiBC() {
		return loaiBC;
	}




	public void setLoaiBC(String loaiBC) {
		this.loaiBC = loaiBC;
	}




	public String getLoaiDoiTuong() {
		return loaiDoiTuong;
	}




	public void setLoaiDoiTuong(String loaiDoiTuong) {
		this.loaiDoiTuong = loaiDoiTuong;
	}




	public String getDoiTuong() {
		return doiTuong;
	}




	public void setDoiTuong(String doiTuong) {
		this.doiTuong = doiTuong;
	}




	public List<Erp_Item> getLoaiDoiTuongList() {
		return loaiDoiTuongList;
	}




	public void setLoaiDoiTuongList(List<Erp_Item> loaiDoiTuongList) {
		this.loaiDoiTuongList = loaiDoiTuongList;
	}




	public List<Erp_Item> getDoiTuongList() {
		return doiTuongList;
	}




	public void setDoiTuongList(List<Erp_Item> doiTuongList) {
		this.doiTuongList = doiTuongList;
	}




	public void setnppId(String nppId) 
	{
		this.nppId = nppId;
		
	}


	public String getnppId() 
	{

		return this.nppId;
	}

	public String getCtyId()
	{

		return this.ctyId;
	}

	public void setCtyId(String ctyId)
	{

		this.ctyId = ctyId;
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


	public String getNPPID() {
		// TODO Auto-generated method stub
		return this.nppId;
	}


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
	
	public void createStaticHeaderSumary(Workbook workbook, IBCChiTietTuoiNo obj){
		
	}
	
	@Override
	public void createStaticHeader(Workbook workbook, IBCChiTietTuoiNo obj, String loaiBC) 
	{		
		Worksheets worksheets = workbook.getWorksheets();
		
		String name_report = "";
    	if(loaiBC.equals("0"))
    		name_report = "REPORT_SOCHITIETCONGNO";
    	else if(loaiBC.equals("1")){
    		name_report = "REPORT_CHITIETTUOINO";
    	}else{
    		name_report = "REPORT_TONGHOPTUOINO";
    	}
    	Worksheet worksheet = worksheets.getSheet(Integer.parseInt(loaiBC));
		worksheet.setName(name_report);
		
		String query = " SELECT * FROM ERP_CONGTY WHERE PK_SEQ = " + this.ctyId;
		ResultSet ctyRs = db.get(query);
		String ctyName = "";
		String diachi = "";
		String masothue = "";
		String donvikd = "";
		String doituongten = "";
		
		if(dvkdId == null || dvkdId.trim().length() == 0) {
			donvikd = "Tất cả";
		}else{
			query = "SELECT * FROM DONVIKINHDOANH WHERE PK_SEQ =" + dvkdId;
			ResultSet rrr = db.get(query);
			if(rrr != null){
				try {
					if(rrr.next()){
						donvikd = rrr.getString("DONVIKINHDOANH");
					}
					rrr.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		if(this.doiTuong.trim().length() > 0) {
			query = "";
			System.out.println("loaidoituon " + this.loaiDoiTuong);
			if(this.loaiDoiTuong.equals("1")) {
				query = "select (MA + ' - ' + TEN)  ten  from KHACHHANG_TUOINO where PK_SEQ = " + this.doiTuong;
			}else {
				query = "select (MA + ' - ' + TEN)  ten  from ERP_NHACUNGCAP where PK_SEQ = " + this.doiTuong;
			}
			
			if(query.length() > 0) {
				ResultSet rsDT = db.get(query);
				System.out.println(query);
				if(rsDT != null) {
					try {
						if(rsDT.next()){
							doituongten = rsDT.getString("ten");
						}
						rsDT.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}else {
			doituongten = "Tất cả";
		}
		
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


		Cell cell = cells.getCell("A1");
		ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Công ty : " + ctyName);
		
	    cell = cells.getCell("A2");
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Địa chỉ : " +  diachi); 
	    
	   
	    cell = cells.getCell("A3");
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Mã số thuế : " +  masothue); 
	   
	    
	    cell = cells.getCell("C2");
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Đơn vị kinh doanh : " +  donvikd); 
	    
	    cell = cells.getCell("C3");
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Loại đối tượng : " + (this.loaiDoiTuong.equals("1")?"Khách hàng" : "Nhà cung cấp"));
	    
	    cell = cells.getCell("C4");
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Đối tượng : " + doituongten);
	    
	    if(loaiBC.equals("1")) {
	    	cell = cells.getCell("C5");
		    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Đến ngày : " + this.denNgay);
	    }else if(loaiBC.equals("0")){
		    cell = cells.getCell("C5");
		    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Từ ngày : " + this.tuNgay);
		    
		    cell = cells.getCell("E5");
		    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Đến ngày : " + this.denNgay);
	    }else{
	    	cell = cells.getCell("C5");
			ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Từ ngày : " + this.tuNgay);
			    
			cell = cells.getCell("F5");
			ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Đến ngày : " + this.denNgay);
	    }
	    //21
	    //8
	    cell = cells.getCell("U8")  ;
		Style style1 =cell.getStyle();
		int num_col = 7;
		System.out.println("LOAI BC" + this.loaiBC);
		System.out.println("BUOC NHAY" + this.buocNhay);
	    if(loaiBC.equals("1")){
	    	for(int i = 20, j = 1; i < 20 + num_col; i++, j++){
	    		 cell = cells.getCell(toName(i) + "8");
	    		 cell.setStyle(style1);
	    		 if(j == 1)
	    			 cell.setValue("1 -> " + this.buocNhay);
	    		 else if(j == num_col)
	    			 cell.setValue(" > " + this.buocNhay * (num_col-1)) ;
	    		 else{
	    			 cell.setValue((this.buocNhay *(j-1)  + 1) + " -> " + (buocNhay * j));
	    		 }
	    		 
	    	}
	    }if(loaiBC.equals("2")){
	    	for(int i = 17, j = 1; i <17+num_col; i++, j++){
	    		 cell = cells.getCell(toName(i) + "8");
	    		 cell.setStyle(style1);
	    		 if(j == 1)
	    			 cell.setValue("1 -> " + this.buocNhay);
	    		 else if(j == num_col)
	    			 cell.setValue(" > " + this.buocNhay * (num_col - 1)) ;
	    		 else{
	    			 cell.setValue((this.buocNhay *(j-1)  + 1) + " -> " + (buocNhay * j));
	    		 }
	    		 
	    	}
	    }

//	    cell = cells.getCell("A7"); 
//	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Từ ngày: " + this.tuNgay);
//	    
//	    ReportAPI.mergeCells(worksheet, 6, 6, 0, 3);
	    
//	    cell = cells.getCell("A7"); 
//	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Đến ngày: " + this.denNgay);
	    
	    

	}
	
	public static String toName(int number) {
        StringBuilder sb = new StringBuilder();
        while (number-- > 0) {
            sb.append((char)('A' + (number % 26)));
            number /= 26;
        }
        return sb.reverse().toString();
    }
	
	public static void main(String[] args) {
		System.out.println("hehe" + toName(2));
	}

	public void init() 
	{
		// TODO Auto-generated method stub
		Utility ut = new Utility();
		String dk = "";

		dbutils db = new dbutils();

		try{

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
	    
	
		
		for(int i=0;i<9;i++ )
		{
			worksheet.autoFitColumn(i);
		}
	}
	
	public void createStaticData2(Workbook workbook) 
	{
		this.initExcel("0");
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    int index = 9;
	    double dauki = 0;
	    DecimalFormat format = new DecimalFormat("###,###,###.##");
	    DecimalFormat formatVND = new DecimalFormat("###,###,###");
	    int colindex = 1;
	    if (this.rs != null) 
		{
			try 
			{
				Cell cell = null;
				while (this.rs.next())
				{		
					Color color = Color.WHITE;
					if(rs.getInt("STT") == 1) {
						dauki = rs.getDouble("NOCUOIKY");
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue("Công nợ đầu kỳ");	
						this.setStyleColorGray(cells, cell, "1",0,true, Color.BLACK,11, HorizontalAlignmentType.LEFT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT, color);
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT, color);
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT, color);
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT, color);
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT, color);
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.RIGHT, color);
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(dauki);
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_VND,true, Color.BLACK,11, HorizontalAlignmentType.LEFT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT, color);
						
						
						
	
					}else if(rs.getInt("STT") == 3) {
						
						
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getString("TENDOITUONG"));
						this.setStyleColorGray(cells, cell, "1",0,true, Color.BLACK,11, HorizontalAlignmentType.LEFT, color);
						
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,true, Color.BLACK,11, HorizontalAlignmentType.LEFT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT, color);
						
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT, color);
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT, color);
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("VNDTANG"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_VND,true, Color.BLACK,11, HorizontalAlignmentType.RIGHT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("VNDGIAM"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_VND,true, Color.BLACK,11, HorizontalAlignmentType.RIGHT, color);
						cell = cells.getCell(toName(colindex++)+ String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.RIGHT, color);

						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(dauki);
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_VND,true, Color.BLACK,11, HorizontalAlignmentType.LEFT, color);
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.RIGHT, color);
					}else {
						dauki = dauki + rs.getDouble("VNDTANG") - rs.getDouble("VNDGIAM");
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getString("MADOITUONG"));
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getString("TENDOITUONG"));
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT, color);
						
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getString("LOAICHUNGTU"));	
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getString("SOCHUNGTU"));
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getString("NGAYCHUNGTU"));
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.CENTRED, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getString("SOHOADON"));
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT, color);
						
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getString("NGAYHOADON"));
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.CENTRED, color);
						
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getString("TIENTE"));
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("TIGIA"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_TIGIA,false, Color.BLACK,11, HorizontalAlignmentType.RIGHT, color);
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getString("TAIKHOANDOIUNG"));
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("NGUYENTETANG"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT,false, Color.BLACK,11, HorizontalAlignmentType.RIGHT, color);
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("VNDTANG"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_VND,false, Color.BLACK,11, HorizontalAlignmentType.RIGHT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("NGUYENTEGIAM"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT,false, Color.BLACK,11, HorizontalAlignmentType.RIGHT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("VNDGIAM"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_VND,false, Color.BLACK,11, HorizontalAlignmentType.RIGHT, color);
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(dauki);
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_VND,false, Color.BLACK,11, HorizontalAlignmentType.RIGHT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("THOIHANNO"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_VND,false, Color.BLACK,11, HorizontalAlignmentType.RIGHT, color);
					}
					index++;	
					colindex=1;
					
					
				}

				if (this.rs != null){
					this.rs.close();
				}
				
				
			}

			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
	}
	
	public void createStaticData1(Workbook workbook) 
	{
		this.initExcel("2");
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(2);
	    Cells cells = worksheet.getCells();
	    int index = 9;
	    double dauki = 0;
	    DecimalFormat format = new DecimalFormat("###,###,###.##");
	    DecimalFormat formatVND = new DecimalFormat("###,###,###");
	    int colindex=1;
	    if (this.rs != null) 
		{
			try 
			{
				Cell cell = null;
				while (this.rs.next())
				{		
						boolean bold = rs.getInt("STT") == 1?false:true;
						Color color = rs.getInt("STT") == 1?Color.WHITE:Color.BLUE;
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getString("DONVIKINHDOANH"));
						this.setStyleColorGray(cells, cell, "1",0,bold, Color.BLACK,11, HorizontalAlignmentType.LEFT, color);
	
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getString("NHANHANG"));
						
						this.setStyleColorGray(cells, cell, "1",0,bold, Color.BLACK,11, HorizontalAlignmentType.LEFT, color );
	
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getString("NHOMKHACHHANG"));
						this.setStyleColorGray(cells, cell, "1",0,bold, Color.BLACK,11, HorizontalAlignmentType.LEFT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getString("MADOITUONG"));
						this.setStyleColorGray(cells, cell, "1",0,bold, Color.BLACK,11, HorizontalAlignmentType.LEFT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getString("TENDOITUONG"));
						this.setStyleColorGray(cells, cell, "1",0,bold, Color.BLACK,11, HorizontalAlignmentType.LEFT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getString("TIENTE"));
						this.setStyleColorGray(cells, cell, "1",0,bold, Color.BLACK,11, HorizontalAlignmentType.LEFT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("NODAUKY_NGTE"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT,bold, Color.BLACK,11, HorizontalAlignmentType.RIGHT,color);
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("NODAUKY"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_VND,bold, Color.BLACK,11, HorizontalAlignmentType.RIGHT,color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("TANGTRONGKY_NGTE"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT,bold, Color.BLACK,11, HorizontalAlignmentType.RIGHT,color);
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("TANGTRONGKY"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_VND,bold, Color.BLACK,11, HorizontalAlignmentType.RIGHT,color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("GIAMTRONGKY_NGTE"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT,bold, Color.BLACK,11, HorizontalAlignmentType.RIGHT,color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("GIAMTRONGKY"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_VND,bold, Color.BLACK,11, HorizontalAlignmentType.RIGHT,color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("NOCUOIKY_NGTE"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT,bold, Color.BLACK,11, HorizontalAlignmentType.RIGHT,color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("NOCUOIKY"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_VND,bold, Color.BLACK,11, HorizontalAlignmentType.RIGHT,color);
					
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("HANMUCNO"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_VND,bold, Color.BLACK,11, HorizontalAlignmentType.RIGHT,color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("VUOTHANMUC"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_VND,bold, Color.BLACK,11, HorizontalAlignmentType.RIGHT,color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("THOIHANNO"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_VND,bold, Color.BLACK,11, HorizontalAlignmentType.RIGHT,color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("NOTRONGHAN"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_VND,bold, Color.BLACK,11, HorizontalAlignmentType.RIGHT,color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("TONGNOQUAHAN"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_VND,bold, Color.BLACK,11, HorizontalAlignmentType.RIGHT,color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("OUTS_15_DAYS"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_VND,bold, Color.BLACK,11, HorizontalAlignmentType.RIGHT,color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("_15_OUTS_30DAYS"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_VND,bold, Color.BLACK,11, HorizontalAlignmentType.RIGHT,color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("_30_OUTS_45DAYS"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_VND,bold, Color.BLACK,11, HorizontalAlignmentType.RIGHT,color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("_45_OUTS_60DAYS"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_VND,bold, Color.BLACK,11, HorizontalAlignmentType.RIGHT,color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("_60_OUTS_90DAYS"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_VND,bold, Color.BLACK,11, HorizontalAlignmentType.RIGHT,color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("_90_OUTS_180DAYS"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_VND,bold, Color.BLACK,11, HorizontalAlignmentType.RIGHT,color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("OUTS_180DAYS"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_VND,bold, Color.BLACK,11, HorizontalAlignmentType.RIGHT,color);
					
					index++;	
					colindex=1;
					
					
				}

				if (this.rs != null){
					this.rs.close();
				}
				
				
			}

			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
	}


	public void createStaticData(Workbook workbook) 
	{
		this.initExcel("1");
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(1);
	    Cells cells = worksheet.getCells();
	    
	    int index = 9;
	    double dauki = 0;
	    DecimalFormat format = new DecimalFormat("###,###,###.##");
	    DecimalFormat formatVND = new DecimalFormat("###,###,###");
	    int colindex=1;
	    if (this.rs != null) 
		{
			try 
			{
				Cell cell = null;
				while (this.rs.next())
				{		
						boolean bold = rs.getInt("STT") == 1?false:true;
						
						Color color = Color.WHITE;

						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getString("MADOITUONG"));
						this.setStyleColorGray(cells, cell, "1",0,bold, Color.BLACK,11, HorizontalAlignmentType.LEFT, color );
						
						cell = cells.getCell(toName(colindex++)+ String.valueOf(index));		cell.setValue(rs.getString("TENDOITUONG"));
						this.setStyleColorGray(cells, cell, "1",0,bold, Color.BLACK,11, HorizontalAlignmentType.LEFT, color );
						
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getString("LOAICHUNGTU"));	
						this.setStyleColorGray(cells, cell, "1",0,bold, Color.BLACK,11, HorizontalAlignmentType.LEFT, color );
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getString("SOCHUNGTU"));
						this.setStyleColorGray(cells, cell, "1",0,bold, Color.BLACK,11, HorizontalAlignmentType.LEFT, color );
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getString("NGAYCHUNGTU"));
						this.setStyleColorGray(cells, cell, "1",0,bold, Color.BLACK,11, HorizontalAlignmentType.CENTRED, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getString("SOHOADON"));
						this.setStyleColorGray(cells, cell, "1",0,bold, Color.BLACK,11, HorizontalAlignmentType.LEFT, color );
						
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getString("NGAYHOADON"));
						this.setStyleColorGray(cells, cell, "1",0,bold, Color.BLACK,11, HorizontalAlignmentType.CENTRED, color);
						
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getString("TIENTE"));
						this.setStyleColorGray(cells, cell, "1",0,bold, Color.BLACK,11, HorizontalAlignmentType.LEFT, color );
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("TIGIA"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_TIGIA,bold, Color.BLACK,11, HorizontalAlignmentType.RIGHT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("NGUYENTETANG"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT,bold, Color.BLACK,11, HorizontalAlignmentType.RIGHT, color);
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("VNDTANG"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_VND,bold, Color.BLACK,11, HorizontalAlignmentType.RIGHT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("NGUYENTEGIAM"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT,bold, Color.BLACK,11, HorizontalAlignmentType.RIGHT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("VNDGIAM"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_VND,bold, Color.BLACK,11, HorizontalAlignmentType.RIGHT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("NGUYENTECONLAI"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT,bold, Color.BLACK,11, HorizontalAlignmentType.RIGHT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("SOTIENCONLAI_VND"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_VND,bold, Color.BLACK,11, HorizontalAlignmentType.RIGHT, color);
					
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("THOIHANNO"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_VND,bold, Color.BLACK,11, HorizontalAlignmentType.RIGHT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("SONGAYQUAHAN"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_VND,bold, Color.BLACK,11, HorizontalAlignmentType.RIGHT, color);
						
						cell = cells.getCell(toName(colindex++)+ String.valueOf(index));		cell.setValue(rs.getDouble("NOTRONGHAN"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_VND,bold, Color.BLACK,11, HorizontalAlignmentType.RIGHT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("TONGNOQUAHAN"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_VND,bold, Color.BLACK,11, HorizontalAlignmentType.RIGHT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("OUTS_15_DAYS"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_VND,bold, Color.BLACK,11, HorizontalAlignmentType.RIGHT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("_15_OUTS_30DAYS"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_VND,bold, Color.BLACK,11, HorizontalAlignmentType.RIGHT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("_30_OUTS_45DAYS"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_VND,bold, Color.BLACK,11, HorizontalAlignmentType.RIGHT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("_45_OUTS_60DAYS"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_VND,bold, Color.BLACK,11, HorizontalAlignmentType.RIGHT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("_60_OUTS_90DAYS"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_VND,bold, Color.BLACK,11, HorizontalAlignmentType.RIGHT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("_90_OUTS_180DAYS"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_VND,bold, Color.BLACK,11, HorizontalAlignmentType.RIGHT, color);
						
						cell = cells.getCell(toName(colindex++) + String.valueOf(index));		cell.setValue(rs.getDouble("OUTS_180DAYS"));
						this.setStyleColorGray(cells, cell, "1",NUMERIC_FORMAT_VND,bold, Color.BLACK,11, HorizontalAlignmentType.RIGHT, color);
					
					index++;	
					colindex=1;
					
					
				}

				if (this.rs != null){
					this.rs.close();
				}
				
				
			}

			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
	}

	private void setStyleColorGray(Cells cells, Cell cell, String leftright, int styleNumber, boolean bold, Color color, int fontSize, Short align, Color back) 
	{

		Cell cell1 = cells.getCell("Y1");
		Style style;	
		style = cell1.getStyle();
		style.setNumber(styleNumber);
		Font font = new Font();
		font.setColor(color);
		font.setBold(bold);
		font.setSize(fontSize);
		style.setPatternColor(back);
		style.setPatternStyle(BackgroundMode.AUTOMATIC);
		font.setName("Times New Roman");
		style.setFont(font);
		
		style.setHAlignment(align);
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
//		this.nppId = ut.getIdNhapp(userId);
		
		

		String      sql = " SELECT pk_seq, isnull(ma,'') + '-' + ten as khTen		\n" +
		      		" FROM 	 ERP_KHACHHANG	\n " ;
		   
		this.khRs = db.get(sql);
		
		
		sql = " SELECT pk_seq, donvikinhdoanh as ten		\n" +
  		" FROM 	 DONVIKINHDOANH	\n " +
  		" WHERE  trangthai = '1' and CONGTY_FK ='"+ this.ctyId +"' \n ";
		this.dvkdRs = db.get(sql);
		
		
		
		sql = " SELECT pk_seq, diengiai as ten		\n" +
  		" FROM 	 KENHBANHANG	\n " +
  		" WHERE  trangthai = '1'  \n ";
		this.KbhRs = db.get(sql);
		System.out.println("Kênh bán hàng là:"+sql);
		
		
		
		this.loaiDoiTuongList.clear();
		String query = 
//				"SELECT N'Nhân viên' AS PK_SEQ ,N'Nhân viên' AS ten \n " +
//				" UNION ALL \n " +
				" SELECT 1 AS PK_SEQ ,N'Khách hàng' AS ten \n " ;
//				" UNION ALL \n " +
//				" SELECT 0 AS PK_SEQ ,N'Chi nhánh/Đối tác' AS ten \n " ;
//				" UNION ALL \n " +
//				" SELECT N'Đối tượng khác' AS PK_SEQ ,N'Đối tượng khác' AS ten \n " +
//				" UNION ALL \n " +
//				" SELECT N'Chi nhánh/Đối tác' AS PK_SEQ ,N'Chi nhánh/Đối tác' AS ten \n " +
//				" UNION ALL \n " +
//				" SELECT N'Ngân hàng' AS PK_SEQ ,N'Ngân hàng' AS ten \n "+
//				" UNION ALL \n " +
//				" SELECT N'Quỹ tiền mặt' AS PK_SEQ ,N'Quỹ tiền mặt' AS ten \n ";
		System.out.println(query);
		Erp_Item.getListFromQuery(db, query, this.loaiDoiTuongList);
		
		query = "SELECT * FROM NHANHANG WHERE 1=1";
		if (this.ctyId.trim().length() > 0)
			query += "  and CONGTY_FK = " + this.ctyId;
		if(this.dvkdId.trim().length() > 0)
			query += " and Dvkd_fk = " + this.dvkdId;
		
		nhanhangRs = db.get(query);
		query ="";
		
		this.doiTuongList.clear();
		System.out.println("loai doi tuong :"+this.loaiDoiTuong );
		if (this.loaiDoiTuong.trim().length() > 0)
		{
			if (this.loaiDoiTuong.trim().equals("Công cụ dụng cụ"))
			{
				query = "select PK_SEQ, DIENGIAI as ten from ERP_CONGCUDUNGCU where TRANGTHAI <> 2";
				if (this.ctyId.trim().length() > 0)
					query += "  and CONGTY_FK = " + this.ctyId;
			}
			else if (this.loaiDoiTuong.trim().equals("Tài sản"))
			{
				query = "select PK_SEQ, DIENGIAI as ten from ERP_TAISANCODINH where TRANGTHAI <> 2";
				if (this.ctyId.trim().length() > 0)
					query += "  and CONGTY_FK = " + this.ctyId;
			}
			
			else if (this.loaiDoiTuong.trim().equals("Đối tượng khác"))
			{
				query = "select PK_SEQ, TENDOITUONG as ten from ERP_DOITUONGKHAC where TRANGTHAI <> 2";
				if (this.ctyId.trim().length() > 0)
					query += "  and CONGTY_FK = " + this.ctyId;
			}
			else if (this.loaiDoiTuong.trim().equals("Sản phẩm"))
			{
				query = "select PK_SEQ, ma + ' - ' + TEN as ten from erp_sanpham where TRANGTHAI <> 0";
			}
			else if (this.loaiDoiTuong.toLowerCase().equals("ngân hàng"))
			{
				query = "select NH_CT.PK_SEQ, NH_CT.SOTAIKHOAN + ' - ' + NH.TEN +'-' + CN.TEN as ten from ERP_NGANHANG_CONGTY NH_CT INNER JOIN ERP_NHANHANG NH ON NH.PK_SEQ = NH_CT.NGANHANG_FK" +
						" INNER JOIN ERP_CHINHANH CN ON CN.PK_SEQ = NH_CT.CHINHANH_FK where 1 = 1 and NH_CT.congty_fk="+this.ctyId+" and NH_CT.npp_fk="+this.nppId+"";
			}
			else if (this.loaiDoiTuong.toLowerCase().equals("2"))
			{
				query = "select PK_SEQ, ma + ' - ' + TEN as ten from ERP_NHACUNGCAP WHERE 1=1 ";
				if (this.ctyId.trim().length() > 0)
					query += "  and CONGTY_FK = " + this.ctyId;
				if(this.dvkdId.trim().length() > 0) {
					query += " and Dvkd_fk = " + this.dvkdId;
				}
				
			}
			else if (this.loaiDoiTuong.toLowerCase().equals("1"))
			{
				query = 
					"select PK_SEQ, ma + ' - ' + ten as ten from khachhang_tuoino where 1 = 1 and ISKHACHHANG = 1 \n" ;

				if (this.ctyId.trim().length() > 0)
					query += "  and CONGTY_FK = " + this.ctyId;
				
				if(this.dvkdId.trim().length() > 0) {
					query += " and Dvkd_fk = " + this.dvkdId;
				}
				if(this.nKh.trim().length() > 0){
					query += " and NHOMKH_FK = " + this.nKh;
				}
			}else if (this.loaiDoiTuong.toLowerCase().trim().equals("nhân viên"))
			{
				query = "select PK_SEQ, ma + ' - ' + TEN as ten from ERP_NHANVIEN WHERE 1=1 ";
				if (this.ctyId.trim().length() > 0)
					query += "  and CONGTY_FK = " + this.ctyId;
				
			}else if (this.loaiDoiTuong.trim().equals("0")){
				query = "SELECT PK_SEQ ,MA + ' - ' + TEN AS TEN FROM NHAPHANPHOI WHERE 1=1 ";
				if(this.ctyId.trim().length() >0)
					query += " AND CONGTY_FK = "+this.ctyId;
			}else if (this.loaiDoiTuong.toLowerCase().equals("quỹ tiền mặt"))
			{
				query = "select PK_SEQ, madtq + ' - ' + tendtq as ten from ERP_DOITUONGQUYTM WHERE 1=1 ";
				if (this.ctyId.trim().length() > 0)
					query += "  and CONGTY_FK = " + this.ctyId;
				
				
			}
			System.out.println("doi tuong" + query);
			Erp_Item.getListFromQuery(db, query, this.doiTuongList);
		}
		
		
		sql = " SELECT pk_seq, TEN ten		\n" +
  		" FROM 	 ERP_NHOMKHACHHANG	\n " +
  		" WHERE  trangthai = '1' \n ";
		this.NkhRs = db.get(sql);
		System.out.println("Nhóm khách hàng là:"+sql);
		
//		     sql = " SELECT pk_seq, CAST(pk_seq as nvarchar(20)) + '-' + ten as Ten" +
//		     	   " FROM 	NHANVIENGIAONHAN" +
//		     	   " WHERE 	trangthai = '1' and npp_fk ='"+ this.nppId +"' ";
//			this.nvgnRs = db.get(sql);
//		
//			sql = 	"select pk_seq, CAST(pk_seq as nvarchar(20)) + '-' + ten as Ten " +
//					"from DAIDIENKINHDOANH where trangthai = '1' and CONGTY_FK =  " + this.ctyId + "  ";
//			this.nvbhRs = db.get(sql);
//		
//			this.nvbhRs = db.get(sql);
		
	}





	public void initExcel(String loaiBC) 
	{
		Utility ut = new Utility();
		String query = "";
		
		dbutils db = new dbutils();
			try{
			if(this.dvkdId == null || this.dvkdId.trim().length() == 0) this.dvkdId = null;
			if(this.nhanhangId == null || this.nhanhangId.trim().length() == 0) this.nhanhangId = null;
			if(this.nKh == null || this.nKh.trim().length() == 0) this.nKh = null;
			if(this.doiTuong == null || this.doiTuong.trim().length() == 0) this.doiTuong = null;
			if(loaiBC.equals("0")){
				query=" EXEC [GETBCCHITIETHOADON_NHANHANG] "+this.ctyId+", "+this.nppId+", "+this.dvkdId+","+this.nhanhangId+","+this.loaiDoiTuong+","+this.nKh+", "+this.doiTuong+",'"+this.tuNgay+"', '"+this.denNgay+"' " ;
			}
			else if(loaiBC.equals("1")) {
				query=" EXEC [REPORT_CHITIETTUOINO_NHANHANG] "+this.ctyId+", "+this.nppId+", "+this.dvkdId+","+this.nhanhangId+","+this.loaiDoiTuong+","+this.nKh+", "+this.doiTuong+", '"+this.denNgay+"' ,"+ this.buocNhay;
			}else if(loaiBC.equals("2")){
				query=" EXEC [REPORT_TUOINOTONGHOP_NHANHANG] "+this.ctyId+", "+this.nppId+", "+this.dvkdId+","+this.nhanhangId+","+this.loaiDoiTuong+", "+this.nKh+","+this.doiTuong +",'"+this.tuNgay+"', '"+this.denNgay+"' ,"+ this.buocNhay;
			}
			System.out.println("Câu init báo cáo 1: " + query);
			this.rs = db.get(query);
		}catch(Exception e ){
			e.printStackTrace();
		}
		
	
		
	}


	public void setRs(ResultSet rs)
    {
		this.rs = rs;
    }

	public ResultSet getRsErpCongty() 
	{		
		return this.RsErpCongty;
	}


	public String getDvkdId() {
		return dvkdId;
	}


	public void setDvkdId(String dvkdId) {
		this.dvkdId = dvkdId;
	}


	public ResultSet getDvkdRs() {
		return dvkdRs;
	}


	public void setDvkdRs(ResultSet dvkdRs) {
		this.dvkdRs = dvkdRs;
	}


	public String getkBh() {
		return kBh;
	}


	public void setkBh(String kBh) {
		this.kBh = kBh;
	}


	public String getnKh() {
		return nKh;
	}


	public void setnKh(String nKh) {
		this.nKh = nKh;
	}


	public ResultSet getKbhRs() {
		return KbhRs;
	}


	public void setKbhRs(ResultSet kbhRs) {
		KbhRs = kbhRs;
	}


	public ResultSet getNkhRs() {
		return NkhRs;
	}


	public void setNkhRs(ResultSet nkhRs) {
		NkhRs = nkhRs;
	}
}
