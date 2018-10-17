package geso.traphaco.distributor.beans.reports.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import geso.traphaco.distributor.beans.reports.IBCCongNoTheoHD;
import geso.traphaco.distributor.db.sql.dbutils;

public class BCChiTietCongNoHD implements IBCChiTietCongNoHD {
	private String userId;
	private String userName;
	private String tuNgay;
	private String denNgay;
	private String nppId;
	private String ngayKS;
	private ResultSet rs;
	
	String msg;
	String dvkdId;
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
	
	public BCChiTietCongNoHD(){
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
	
	@Override
	public void createStaticHeader(Workbook workbook, IBCChiTietCongNoHD obj) 
	{		
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		if(this.doiTuong.trim().length() > 0) {
			query = "";
			System.out.println("loaidoituon " + this.loaiDoiTuong);
			if(this.loaiDoiTuong.equals("1")) {
				query = "select (MA + ' - ' + TEN)  ten  from ERP_KHACHHANG where PK_SEQ = " + this.doiTuong;
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
	   
	    
	    cell = cells.getCell("E2");
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Đơn vị kinh doanh : " +  donvikd); 
	    
	    cell = cells.getCell("E3");
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Loại đối tượng : " + (this.loaiDoiTuong.equals("1")?"Khách hàng" : "Nhà cung cấp"));
	    
	    cell = cells.getCell("E4");
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Đối tượng : " + doituongten);
	    
	    cell = cells.getCell("E5");
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Từ ngày : " + this.tuNgay);
	    
	    cell = cells.getCell("F5");
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Đến ngày : " + this.denNgay);
	    

//	    cell = cells.getCell("A7"); 
//	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Từ ngày: " + this.tuNgay);
//	    
//	    ReportAPI.mergeCells(worksheet, 6, 6, 0, 3);
	    
//	    cell = cells.getCell("A7"); 
//	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Đến ngày: " + this.denNgay);
	    
	    

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

	public void createStaticData(Workbook workbook) 
	{
		this.initExcel();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    int index = 9;
	    double dauki = 0;
	    DecimalFormat format = new DecimalFormat("###,###,###.##");
	    DecimalFormat formatVND = new DecimalFormat("###,###,###");
	    if (this.rs != null) 
		{
			try 
			{
				Cell cell = null;
				while (this.rs.next())
				{		
					if(rs.getInt("STT") == 1) {
						dauki = rs.getDouble("NOCUOIKY");
						cell = cells.getCell("A" + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT);
	
						cell = cells.getCell("B" + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT);
						
						cell = cells.getCell("C" + String.valueOf(index));		cell.setValue("Công nợ đầu kỳ");
						this.setStyleColorGray(cells, cell, "1",0,true, Color.BLACK,11, HorizontalAlignmentType.LEFT);
						
						
						cell = cells.getCell("D" + String.valueOf(index));		cell.setValue("");	
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT);
						
						cell = cells.getCell("E" + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT);
						
						cell = cells.getCell("F" + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT);
						
						cell = cells.getCell("G" + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT);
						
						cell = cells.getCell("H" + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT);
						
						
						cell = cells.getCell("I" + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT);
						
						cell = cells.getCell("J" + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("K" + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT);
						
						cell = cells.getCell("L" + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("M" + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT);
						
						cell = cells.getCell("N" + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("O" + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("P" + String.valueOf(index));		cell.setValue(dauki);
						this.setStyleColorGray(cells, cell, "1",41,true, Color.BLACK,11, HorizontalAlignmentType.RIGHT);
						
						cell = cells.getCell("Q" + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT);
					}else if(rs.getInt("STT") == 3) {
						
						cell = cells.getCell("A" + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT);
	
						cell = cells.getCell("B" + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT);
						
						cell = cells.getCell("C" + String.valueOf(index));		cell.setValue(rs.getString("TENDOITUONG"));
						this.setStyleColorGray(cells, cell, "1",0,true, Color.BLACK,11, HorizontalAlignmentType.LEFT);
						
						
						cell = cells.getCell("D" + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT);
						
						cell = cells.getCell("E" + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT);
						
						cell = cells.getCell("F" + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT);
						
						cell = cells.getCell("G" + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT);
						
						cell = cells.getCell("H" + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT);
						
						
						cell = cells.getCell("I" + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT);
						
						cell = cells.getCell("J" + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("K" + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT);
						
						cell = cells.getCell("L" + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT);
						cell = cells.getCell("M" + String.valueOf(index));		cell.setValue(rs.getDouble("VNDTANG"));
						this.setStyleColorGray(cells, cell, "1",41,true, Color.BLACK,11, HorizontalAlignmentType.RIGHT);
						
						cell = cells.getCell("N" + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT);
						
						cell = cells.getCell("O" + String.valueOf(index));		cell.setValue(rs.getDouble("VNDGIAM"));
						this.setStyleColorGray(cells, cell, "1",41,true, Color.BLACK,11, HorizontalAlignmentType.RIGHT);
						cell = cells.getCell("P" + String.valueOf(index));		cell.setValue(dauki);
						this.setStyleColorGray(cells, cell, "1",41,true, Color.BLACK,11, HorizontalAlignmentType.RIGHT);
						
						cell = cells.getCell("Q" + String.valueOf(index));		cell.setValue("");
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT);
					}else {
						dauki = dauki + rs.getDouble("VNDTANG") - rs.getDouble("VNDGIAM");
						cell = cells.getCell("A" + String.valueOf(index));		cell.setValue(rs.getString("DONVIKINHDOANH"));
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT);
	
						cell = cells.getCell("B" + String.valueOf(index));		cell.setValue(rs.getString("MADOITUONG"));
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT);
						
						cell = cells.getCell("C" + String.valueOf(index));		cell.setValue(rs.getString("TENDOITUONG"));
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT);
						
						
						cell = cells.getCell("D" + String.valueOf(index));		cell.setValue(rs.getString("LOAICHUNGTU"));	
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT);
						
						cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(rs.getString("SOCHUNGTU"));
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT);
						
						cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(rs.getString("NGAYCHUNGTU"));
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.CENTRED);
						
						cell = cells.getCell("G" + String.valueOf(index));		cell.setValue(rs.getString("SOHOADON"));
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT);
						
						
						cell = cells.getCell("H" + String.valueOf(index));		cell.setValue(rs.getString("NGAYHOADON"));
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.CENTRED);
						
						
						cell = cells.getCell("I" + String.valueOf(index));		cell.setValue(rs.getString("TIENTE"));
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT);
						
						cell = cells.getCell("J" + String.valueOf(index));		cell.setValue(rs.getDouble("TIGIA"));
						this.setStyleColorGray(cells, cell, "1",41,false, Color.BLACK,11, HorizontalAlignmentType.RIGHT);
						cell = cells.getCell("K" + String.valueOf(index));		cell.setValue(rs.getString("TAIKHOANDOIUNG"));
						this.setStyleColorGray(cells, cell, "1",0,false, Color.BLACK,11, HorizontalAlignmentType.LEFT);
						
						cell = cells.getCell("L" + String.valueOf(index));		cell.setValue(rs.getDouble("NGUYENTETANG"));
						this.setStyleColorGray(cells, cell, "1",41,false, Color.BLACK,11, HorizontalAlignmentType.RIGHT);
						cell = cells.getCell("M" + String.valueOf(index));		cell.setValue(rs.getDouble("VNDTANG"));
						this.setStyleColorGray(cells, cell, "1",41,false, Color.BLACK,11, HorizontalAlignmentType.RIGHT);
						
						cell = cells.getCell("N" + String.valueOf(index));		cell.setValue(rs.getDouble("NGUYENTEGIAM"));
						this.setStyleColorGray(cells, cell, "1",41,false, Color.BLACK,11, HorizontalAlignmentType.RIGHT);
						
						cell = cells.getCell("O" + String.valueOf(index));		cell.setValue(rs.getDouble("VNDGIAM"));
						this.setStyleColorGray(cells, cell, "1",41,false, Color.BLACK,11, HorizontalAlignmentType.RIGHT);
						cell = cells.getCell("P" + String.valueOf(index));		cell.setValue(dauki);
						this.setStyleColorGray(cells, cell, "1",41,false, Color.BLACK,11, HorizontalAlignmentType.RIGHT);
						
						cell = cells.getCell("Q" + String.valueOf(index));		cell.setValue(rs.getDouble("THOIHANNO"));
						this.setStyleColorGray(cells, cell, "1",41,false, Color.BLACK,11, HorizontalAlignmentType.RIGHT);
					}
					index++;	
					
					
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

	private void setStyleColorGray(Cells cells, Cell cell, String leftright, int styleNumber, boolean bold, Color color, int fontSize, Short align) 
	{

		Cell cell1 = cells.getCell("Y1");
		Style style;	
		style = cell1.getStyle();
		style.setNumber(styleNumber);
		Font font = new Font();
		font.setColor(color);
		font.setBold(bold);
		font.setSize(fontSize);
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
		
		

		String      sql = " SELECT pk_seq, isnull(maFAST,'') + '-' + ten as khTen		\n" +
		      		" FROM 	 ERP_KHACHHANG	\n " +
		      		" WHERE   npp_fk ='"+ this.nppId +"' \n ";
		this.khRs = db.get(sql);
		
		
		sql = " SELECT pk_seq, donvikinhdoanh as ten		\n" +
  		" FROM 	 DONVIKINHDOANH	\n " +
  		" WHERE  trangthai = '1' and CONGTY_FK ='"+ this.ctyId +"' \n ";
		this.dvkdRs = db.get(sql);
		
		
		
		sql = " SELECT pk_seq, diengiai as ten		\n" +
  		" FROM 	 KENHBANHANG	\n " +
  		" WHERE  trangthai = '1' and CONGTY_FK ='"+ this.ctyId +"' \n ";
		this.KbhRs = db.get(sql);
		System.out.println("Kênh bán hàng là:"+sql);
		
		this.loaiDoiTuongList.clear();
		String query = 
//				"SELECT N'Nhân viên' AS PK_SEQ ,N'Nhân viên' AS ten \n " +
//				" UNION ALL \n " +
				" SELECT 1 AS PK_SEQ ,N'Khách hàng' AS ten \n " +
				" UNION ALL \n " +
				" SELECT 2 AS PK_SEQ ,N'Nhà cung cấp' AS ten \n " ;
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
					"select PK_SEQ, ten from ERP_KHACHHANG where 1 = 1 \n" ;

				if (this.ctyId.trim().length() > 0)
					query += "  and CONGTY_FK = " + this.ctyId;
				if (this.nppId.trim().length() > 0)
					query += "  and NPP_FK = " + this.nppId;
				if(this.dvkdId.trim().length() > 0) {
					query += " and Dvkd_fk = " + this.dvkdId;
				}
			}else if (this.loaiDoiTuong.toLowerCase().trim().equals("nhân viên"))
			{
				query = "select PK_SEQ, ma + ' - ' + TEN as ten from ERP_NHANVIEN WHERE 1=1 ";
				if (this.ctyId.trim().length() > 0)
					query += "  and CONGTY_FK = " + this.ctyId;
				if (this.nppId.trim().length() > 0)
					query += "  and NPP_FK = " + this.nppId;
			}else if (this.loaiDoiTuong.trim().equals("Chi nhánh/Đối tác")){
				query = "SELECT PK_SEQ ,MA + ' - ' + TEN AS TEN FROM NHAPHANPHOI WHERE 1=1 ";
				if(this.ctyId.trim().length() >0)
					query += " AND CONGTY_FK = "+this.ctyId;
			}else if (this.loaiDoiTuong.toLowerCase().equals("quỹ tiền mặt"))
			{
				query = "select PK_SEQ, madtq + ' - ' + tendtq as ten from ERP_DOITUONGQUYTM WHERE 1=1 ";
				if (this.ctyId.trim().length() > 0)
					query += "  and CONGTY_FK = " + this.ctyId;
				if (this.nppId.trim().length() > 0)
					query += "  and NPP_FK = " + this.nppId;
				
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





	public void initExcel() 
	{
		Utility ut = new Utility();
		String sql = "";
		
		dbutils db = new dbutils();
			try{
			if(this.dvkdId.trim().length() == 0) this.dvkdId = null;
			if(this.doiTuong.trim().length() == 0) this.doiTuong = null;
			String query=" EXEC [GETBCCHITIETHOADON_v1] "+this.ctyId+", "+this.nppId+", "+this.dvkdId+","+this.loaiDoiTuong+", "+this.doiTuong+",'"+this.tuNgay+"', '"+this.denNgay+"' ";
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
