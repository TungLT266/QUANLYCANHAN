package geso.traphaco.erp.beans.bangkechungtu.imp;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Erp_Item;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.FileInputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class Erp_BangKeChungTu  extends Phan_Trang {
	String userId;
	String ctyId;	
	String ctyTen;
	String year;
	String month;
	String diachi;
	String masothue;
	String tkId;
	private String nhomTaiKhoanId;
	private String loaiNghiepVu;
	private String soChungTu;
	private String loaiDoiTuong;
	private String doiTuong;
	private String taiKhoanNo;
	private String taiKhoanCo;
	private String congTy;
	String sohieu;
	String daukyno;
	String daukyco;
	String timkiem;
	
	String tungay;
	String denngay;
	
	ResultSet rs;
	ResultSet tk;
	
	private List<Erp_Item> nhomTaiKhoanList;
	private List<Erp_Item> loaiNghiepVuList;
	private List<Erp_Item> loaiDoiTuongList;
	private List<Erp_Item> doiTuongList;
	private List<Erp_Item> taiKhoanList;
	private List<Erp_Item> congTyList;
	private List<Erp_BangKeChungTuListItem> viewList;

	String tongNO;
	String tongCO;
	
	ResultSet congtyRs;
	String[] ctyIds;
	
	String ErpCongTyId;
	String view;
	
	String msg;
	dbutils db;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	
	public Erp_BangKeChungTu() {
		this.userId = "";
		this.ctyId = "";
		this.ctyTen = "";
		this.sohieu = "";
		this.daukyno = "0";
		this.daukyco = "0";
		this.tongCO = "0";
		this.tongNO = "0";
		this.tkId = "";
		this.nhomTaiKhoanId = "";
		this.loaiNghiepVu = "";
		this.soChungTu = "";
		this.loaiDoiTuong = "";
		this.doiTuong = "" ;
		this.taiKhoanNo = "" ;
		this.taiKhoanCo = "" ;
		this.congTy = "1";
		this.timkiem = "";
		this.tungay = "";
		this.denngay = Utility.getCurrentDate();
		this.tungay = denngay;
//		this.tungay = denngay.split("-")[0] + "-" + denngay.split("-")[1] + "-01";
		
		this.month = "";
		
		this.year = "";

		this.nhomTaiKhoanList = new ArrayList<Erp_Item>();
		this.loaiNghiepVuList = new ArrayList<Erp_Item>();
		this.loaiDoiTuongList = new ArrayList<Erp_Item>();
		this.doiTuongList = new ArrayList<Erp_Item>();
		this.taiKhoanList = new ArrayList<Erp_Item>();
		this.congTyList = new ArrayList<Erp_Item>();
		this.viewList = new ArrayList<Erp_BangKeChungTuListItem>();
		
		this.msg = "";
		this.db = new dbutils();
	}
	
	public void init_0(){
		if(this.ctyIds != null){
			String tmp = "";
			for(int i = 0; i < this.ctyIds.length; i++){
				tmp += this.ctyIds[i] + ",";
			}
			this.ErpCongTyId = tmp.substring(0, tmp.length() - 1);
			
			System.out.println("Công ty: " + this.ErpCongTyId);
		}else{
			
			String tmp = "";
			ResultSet rs = this.db.get("SELECT PK_SEQ, TEN FROM ERP_CONGTY WHERE isTongCongTy = 0 AND TRANGTHAI = 1");
			try{
				while(rs.next()){
					tmp += rs.getString("PK_SEQ") + ",";
				}
				
				this.ErpCongTyId = tmp.substring(0, tmp.length() - 1);
				
			}catch(java.sql.SQLException e){
				e.printStackTrace();
			}
		}
		
		String sql = "SELECT PK_SEQ, TEN FROM ERP_CONGTY WHERE isTongCongTy = 0 AND TRANGTHAI = 1";
		this.congtyRs = db.get(sql);	
	}

	public void init(){
		// tạm thời bỏ công ty ra. Khi đã đồng bộ bán hàng lên thì chỉnh lại
		initRs();
		
		try
		{
//			String[] param = {this.tungay, this.denngay, this.congTy, this.loaiNghiepVu
//					, this.soChungTu, this.loaiDoiTuong, this.doiTuong, this.taiKhoanNo
//					, this.taiKhoanCo};
			String query = "select * from REPORT_BANGKECHUNGTU_F( '" + 
			this.tungay+ "', '" + this.denngay+ "', " + (this.congTy.trim().length() > 0 ? this.congTy : "0") + ", N'" + this.loaiNghiepVu
			+ "', '" + this.soChungTu+ "', '" + this.loaiDoiTuong+ "', '" + this.doiTuong+ "', '" + this.taiKhoanNo
			+ "', '" + this.taiKhoanCo+ "')";
			
			System.out.println("bang ke chung tu: \n" + query  + "\n------------------------------------------------------------");
			
			ResultSet rs = createSplittingDataNew(db, 50, 10, "SOCHUNGTU DESC", query);
			Erp_BangKeChungTuListItem.getListFromRs(db, rs, this.viewList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean exportExcel(ServletOutputStream out, String fileName)
	{
		try{
			String[] param = {this.tungay, this.denngay, this.congTy, this.loaiNghiepVu
					, this.soChungTu, this.loaiDoiTuong, this.doiTuong, this.taiKhoanNo
					, this.taiKhoanCo};
			String query = "select * from REPORT_BANGKECHUNGTU_F( '" + 
			this.tungay+ "', '" + this.denngay+ "', " + (this.congTy.trim().length() > 0 ? this.congTy : "0") + ", N'" + this.loaiNghiepVu
			+ "', '" + this.soChungTu+ "', '" + this.loaiDoiTuong+ "', '" + this.doiTuong+ "', '" + this.taiKhoanNo
			+ "', '" + this.taiKhoanCo+ "')";
	
			query += " ORDER BY SOCHUNGTU DESC ";
			
			System.out.println("query" +query);
			ResultSet rs = db.get(query);
			
			FileInputStream fstream = null;
			Workbook workbook = new Workbook();
			
			fstream = new FileInputStream(fileName);
			
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");
			
			Cells cells = worksheet.getCells();
			
		    Cell cell = cells.getCell("A4");
		    cell = cells.getCell("A3");
		    cell.setValue("Báo Cáo Chi Tiết Nghiệp Vụ Kế Toán");
		    
		    
		    cell = cells.getCell("A4");
		    cell.setValue("Từ ngày: " + this.tungay + " - " + this.denngay);
		    
		    
		    
			    
	             
			ResultSetMetaData rsmd = rs.getMetaData();
			int rowNumber = rsmd.getColumnCount();
			int countRow = 10;
						
			int stt = 0;
			while(rs.next())
			{
				stt++;
				for(int i = 1;i <=rowNumber ; i ++)
				{
					cell = cells.getCell(countRow, i - 1);
					if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i) == Types.INTEGER || rsmd.getColumnType(i) == Types.DECIMAL)
					{
						cell.setValue(rs.getDouble(i));
						cell = ReportAPI.CreateBorderSetting2(cell);
					}
					else
					{
						cell.setValue(rs.getString(i));
						cell = ReportAPI.CreateBorderSetting2(cell);
					}
				}
				++countRow;
			}
			
			for(int i = 1; i <= rowNumber ; i++)
			{
				ReportAPI.CreateBorderSetting3(cells.getCell(countRow - 1,i - 1));
			}
			
			workbook.save(out);
			fstream.close();
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	public void initRs() {
		this.tk = 	this.db.get("SELECT PK_SEQ AS TKID, SOHIEUTAIKHOAN + ' - ' + TENTAIKHOAN AS TAIKHOAN FROM ERP_TAIKHOANKT WHERE CONGTY_FK = "+this.ctyId+" " +
		"ORDER BY SOHIEUTAIKHOAN");
		
		this.nhomTaiKhoanList.clear();
		String query = "select pk_seq, ma + ' - '  + diengiai as TEN from GROUP_TAIKHOAN_NHOM where trangthai = 1";
		Erp_Item.getListFromQuery(db, query, this.nhomTaiKhoanList);
		
		this.loaiNghiepVuList.clear();
		query = " SELECT distinct pk_seq,ten from ( select distinct LOAICHUNGTU as PK_SEQ, LOAICHUNGTU as TEN from ERP_PHATSINHKETOAN UNION ALL "
				+ "  select distinct LOAICHUNGTU as PK_SEQ, LOAICHUNGTU as TEN from "+Utility.prefixDMS+"ERP_PHATSINHKETOAN ) data";
		Erp_Item.getListFromQuery(db, query, this.loaiNghiepVuList);
		
		this.loaiDoiTuongList.clear();
		query = "select distinct doiTuong as PK_SEQ, doiTuong as TEN from ERP_PHATSINHKETOAN where doiTuong is not null and len(doiTuong) > 0";
		Erp_Item.getListFromQuery(db, query, this.loaiDoiTuongList);
		
		this.doiTuongList.clear();
		if (this.loaiDoiTuong.trim().length() > 0)
		{
			if (this.loaiDoiTuong.trim().equals("Công cụ dụng cụ"))
			{
				query = "select PK_SEQ, DIENGIAI as ten from ERP_CONGCUDUNGCU where TRANGTHAI <> 2";
				if (this.congTy.trim().length() > 0)
					query += "  and CONGTY_FK = " + this.congTy;
			}
			else if (this.loaiDoiTuong.trim().equals("Tài sản"))
			{
				query = "select PK_SEQ, DIENGIAI as ten from ERP_TAISANCODINH where TRANGTHAI <> 2";
				if (this.congTy.trim().length() > 0)
					query += "  and CONGTY_FK = " + this.congTy;
			}
			else if (this.loaiDoiTuong.trim().equals("Sản phẩm"))
			{
				query = "select PK_SEQ, ma + ' - ' + TEN as ten from erp_sanpham where TRANGTHAI <> 0";
			}
			else if (this.loaiDoiTuong.trim().equals("Ngân hàng"))
			{
				query = "select PK_SEQ, ma + ' - ' + TEN as ten from ERP_NGANHANG where trangthai = 1";
			}
			else if (this.loaiDoiTuong.trim().equals("Nhà cung cấp"))
			{
				query = "select PK_SEQ, ma + ' - ' + TEN as ten from ERP_NHACUNGCAP where trangthai = 1 and duyet = '1'";
				if (this.congTy.trim().length() > 0)
					query += "  and CONGTY_FK = " + this.congTy;
			}
			else if (this.loaiDoiTuong.trim().equals("Khách hàng"))
			{
				query = 
					"select PK_SEQ, ten from KHACHHANG where TRANGTHAI = 1\n" +
					"union \n" +
					"select PK_SEQ, ma + ' - ' + ten as ten from NHAPHANPHOI where isKHACHHANG = 1 and TRANGTHAI = 1\n";

				if (this.congTy.trim().length() > 0)
					query += "  and CONGTY_FK = " + this.congTy;
			}
			
			Erp_Item.getListFromQuery(db, query, this.doiTuongList);
		}
		
		this.taiKhoanList.clear();//Lấy hệ thống tài khoản của trung tâm làm chuẩn
		query = "select distinct soHieuTaiKhoan as PK_SEQ, soHieuTaiKhoan + ' - ' + TENTAIKHOAN as TEN from ERP_TAIKHOANKT where trangThai = 1 and NPP_FK = 1";
		Erp_Item.getListFromQuery(db, query, this.taiKhoanList);
		
		this.congTyList.clear();
		query = "select PK_SEQ, ma + ' - ' + TEN as ten from NHAPHANPHOI where isKHACHHANG = 0 and TRANGTHAI = 1";
		Erp_Item.getListFromQuery(db, query, this.congTyList);
	}

	public String getDate() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public void DBClose(){
		if(db != null) 
			db.shutDown();
	}
	
	public void setView(String view) {
		this.view = view;
	}

	public String getView() {
		return this.view;
	}
	
	public void setCtyRs(ResultSet ctyRs) {

		this.congtyRs = ctyRs;
	}

	public ResultSet getCtyRs() {

		return this.congtyRs;
	}
	
	public void setCtyIds(String[] ctyIds) {

		this.ctyIds = ctyIds;
	}

	public String[] getCtyIds() {
		return this.ctyIds;
	}
	
	public String getErpCongtyId() {
		return this.ErpCongTyId;
	}

	
	public void setErpCongtyId(String id) {
		this.ErpCongTyId=id;
	}

	public void setuserId(String userId) {
		this.userId = userId;
	}

	public String getuserId() {
		return this.userId;
	}

	public void setCtyId(String ctyId) {
		this.ctyId = ctyId;
	}

	public String getCtyId() {
		return this.ctyId;
	}

	public String getCtyTen() {
		return this.ctyTen;
	}

	public String getDiachi() {
		return this.diachi;
	}

	public String getMasothue() {
		return this.masothue;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getMonth() {
		if(this.month.length() > 0){
			return this.month;	
		}else{
			return this.getDate().substring(5, 7);
		}
		
	}
	
	public void setYear(String year) {
		this.year = year;
	}

	public String getYear() {
		if(this.year.length() > 0){
			return this.year;	
		}else{
			return this.getDate().substring(0, 4);
		}
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setTkId(String tkId) {
		this.tkId = tkId;
	}

	public String getTkId() {
		return this.tkId;
	}

	public void setTongNO(String tongNO) {
		this.tongNO = tongNO;
	}

	public String getTongNO() {
		return this.tongNO;
	}

	public void setTongCO(String tongCO) {
		this.tongCO = tongCO;
	}

	public String getTongCO() {
		return this.tongCO;
	}
	
	public String getSohieu() {
		return this.sohieu;
	}

	public String getDaukyno() {
		return this.daukyno;
	}

	public String getDaukyco() {
		return this.daukyco;
	}

	public ResultSet getData(){
		return this.rs;
	}
	
	public ResultSet getTaikhoan(){
		return this.tk;
	}
	
	public String getTimkiem() 
	{
		return timkiem;
	}

	public void setTimkiem(String timkiem) {
		this.timkiem = timkiem;
	}

	public String getTungay() {
		return this.tungay;
	}
	
	public void setTungay(String tungay) {
		this.tungay = tungay;
	}
	
	public String getDenngay() {
		return this.denngay;
	}
	
	public void setDenngay(String denngay) {
		this.denngay = denngay;
	}
	
	public String getNhomTaiKhoanId() {
		return nhomTaiKhoanId;
	}

	public void setNhomTaiKhoanId(String nhomTaiKhoanId) {
		this.nhomTaiKhoanId = nhomTaiKhoanId;
	}

	public String getLoaiNghiepVu() {
		return loaiNghiepVu;
	}

	public void setLoaiNghiepVu(String loaiNghiepVu) {
		this.loaiNghiepVu = loaiNghiepVu;
	}
	
	public List<Erp_Item> getNhomTaiKhoanList() {
		return nhomTaiKhoanList;
	}

	public void setNhomTaiKhoanList(List<Erp_Item> nhomTaiKhoanList) {
		this.nhomTaiKhoanList = nhomTaiKhoanList;
	}

	public List<Erp_Item> getLoaiNghiepVuList() {
		return loaiNghiepVuList;
	}

	public void setLoaiNghiepVuList(List<Erp_Item> loaiNghiepVuList) {
		this.loaiNghiepVuList = loaiNghiepVuList;
	}

	public void setSoChungTu(String soChungTu) {
		this.soChungTu = soChungTu;
	}

	public String getSoChungTu() {
		return soChungTu;
	}

	public void setLoaiDoiTuongList(List<Erp_Item> loaiDoiTuongList) {
		this.loaiDoiTuongList = loaiDoiTuongList;
	}

	public List<Erp_Item> getLoaiDoiTuongList() {
		return loaiDoiTuongList;
	}

	public void setLoaiDoiTuong(String loaiDoiTuong) {
		this.loaiDoiTuong = loaiDoiTuong;
	}

	public String getLoaiDoiTuong() {
		return loaiDoiTuong;
	}

	public void setDoiTuongList(List<Erp_Item> doiTuongList) {
		this.doiTuongList = doiTuongList;
	}

	public List<Erp_Item> getDoiTuongList() {
		return doiTuongList;
	}
	
	public String getDoiTuong() {
		return doiTuong;
	}

	public void setDoiTuong(String doiTuong) {
		this.doiTuong = doiTuong;
	}

	public String getTaiKhoanNo() {
		return taiKhoanNo;
	}

	public void setTaiKhoanNo(String taiKhoanNo) {
		this.taiKhoanNo = taiKhoanNo;
	}

	public String getTaiKhoanCo() {
		return taiKhoanCo;
	}

	public void setTaiKhoanCo(String taiKhoanCo) {
		this.taiKhoanCo = taiKhoanCo;
	}

	public String getCongTy() {
		return congTy;
	}

	public void setCongTy(String congTy) {
		this.congTy = congTy;
	}

	public List<Erp_Item> getTaiKhoanList() {
		return taiKhoanList;
	}

	public void setTaiKhoanList(List<Erp_Item> taiKhoanList) {
		this.taiKhoanList = taiKhoanList;
	}

	public void setCongTyList(List<Erp_Item> congTyList) {
		this.congTyList = congTyList;
	}

	public List<Erp_Item> getCongTyList() {
		return congTyList;
	}
	
	public List<Erp_BangKeChungTuListItem> getViewList() {
		return viewList;
	}

	public void setViewList(List<Erp_BangKeChungTuListItem> viewList) {
		this.viewList = viewList;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int[] getListPages() {
		return listPages;
	}

	public void setListPages(int[] listPages) {
		this.listPages = listPages;
	}

	public int getCurrentPages() {
		return currentPages;
	}

	public void setCurrentPages(int currentPages) {
		this.currentPages = currentPages;
	}
	

	public int getLastPage() {
		ResultSet rs = db.get("select count(*) as c from REPORT_BANGKECHUNGTU_F");
		return PhanTrang.getLastPage(rs);
	}

	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage) {
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}
}