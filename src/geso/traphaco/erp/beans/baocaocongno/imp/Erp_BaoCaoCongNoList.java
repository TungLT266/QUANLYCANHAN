package geso.traphaco.erp.beans.baocaocongno.imp;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.DKLocBaoCaoKeToan;
import geso.traphaco.center.util.PhatSinhKeToanItem;
import geso.traphaco.erp.beans.baocaocongno.IErp_BaoCaoCongNoList;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.FileInputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class Erp_BaoCaoCongNoList  extends Phan_Trang implements IErp_BaoCaoCongNoList
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String congTyId;
	private String msg;
	
	private DKLocBaoCaoKeToan dieuKienLoc;
	static private List<String> colunmNameList = initColunmNameList();
	private List<PhatSinhKeToanItem> viewList;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	private dbutils db;
	
	public Erp_BaoCaoCongNoList()
	{
		this.congTyId = "";
		this.msg= ""; 
		
		this.dieuKienLoc = new DKLocBaoCaoKeToan();
		this.viewList = new ArrayList<PhatSinhKeToanItem>();
		
		this.currentPages = 1;
		this.num = 1;
		this.db = new dbutils();
	}
	
	private static List<String> initColunmNameList() {
		List<String> list = new ArrayList<String>();
		
		list.add("STT");
		list.add("Mã đối tượng");
		list.add("Tên đối tượng");
		list.add("Nợ đầu kỳ");
		list.add("Có đầu kỳ");
		list.add("Nợ");
		list.add("Có");
		list.add("Nợ cuối kỳ");
		list.add("Có cuối kỳ");
		list.add("Tác vụ");
		return list;
	}

	public void init()
	{
		this.dieuKienLoc.init(db);
		
//		String [] param = {"x...."};
		//Bắt buộc nhập chọn tài khoản công nợ
		if (this.dieuKienLoc.getSoHieuTaiKhoanNo().trim().length() > 0 || this.dieuKienLoc.getNhomTaiKhoanId().trim().length() >0)
		{
			//Lấy pk_seq của tài khoản thuộc chi nhánh
			String query = "select PK_SEQ from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '" + this.dieuKienLoc.getSoHieuTaiKhoanNo() + "' and TRANGTHAI = 1 and npp_fk = " + this.dieuKienLoc.getCongTyId();
			try {
			ResultSet rs = this.db.get(query);
			String taiKhoanId = "";
			if (rs != null)
			{
				if (rs.next())
				{
					taiKhoanId = rs.getString(1);
				}
				rs.close();
			}
			
			/*query =
				"EXEC [REPORT_CANDOIPHATSINHPHATSINHCONGNO] " +
				"'" + this.dieuKienLoc.getTuNgay() + "'" +
				", '" + this.dieuKienLoc.getDenNgay()  + "'" +
				", '" + taiKhoanId + "'" +
				", '" + this.dieuKienLoc.getDoiTuongNoId() + "'" +
				", N'" + this.dieuKienLoc.getLoaiDoiTuongNoId() + "'" +
				", " + this.dieuKienLoc.getCongTyId();*/
			
			int thangdauky ;
			int namdauky ;
			int lastmonth ;
			int lastyear ;
			lastyear = Integer.parseInt(this.dieuKienLoc.getTuNgay().substring(0,3)) - 1;
			if(Integer.parseInt(this.dieuKienLoc.getTuNgay().substring(5, 6)) >1 )
				lastmonth = Integer.parseInt(this.dieuKienLoc.getTuNgay().substring(5, 6)) - 1;
			else
				lastmonth = 12;
			if(lastmonth != 12){
				thangdauky = lastmonth;
				namdauky = Integer.parseInt(this.dieuKienLoc.getTuNgay().substring(0,3));
			}else{
				thangdauky = lastmonth;
				namdauky = lastyear;
			}
			int thangks ;
			int namks ;
			//Chọn tháng khóa sổ gần nhất
			String sqlKhoaSo= "SELECT DISTINCT TOP 1 NAM,THANG FROM ERP_TAIKHOAN_NOCO_KHOASO KS \n" +
					 "INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = KS.TAIKHOANKT_FK \n"+
					 "WHERE TK.NPP_FK =  " + this.dieuKienLoc.getCongTyId() + 
					 "ORDER BY NAM DESC,THANG DESC";
			ResultSet rsKhoaSo = db.get(sqlKhoaSo);
			int thangKS = 0;
			int namKS = 0;
			if(rsKhoaSo!= null){
				try {
					while(rsKhoaSo.next()){
						thangKS = rsKhoaSo.getInt("THANG");
						namKS = rsKhoaSo.getInt("NAM");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					String error = "[ERROR 1.1] Không thể lấy tháng KS gần nhất : "+ e.toString();
					System.out.println(error);
					this.msg = error;
				}
			}
			if(thangKS >= thangdauky && namKS >= namdauky){
				
			}else{
				thangdauky = thangKS;
				namdauky = namKS;
			}
			
			query = "";
			String thangDauky = "0"+ thangdauky;
			thangDauky = thangDauky.substring(thangDauky.length() -2);
				
			System.out.println("query init: \n" + query + "\n------------------------------------------------------------");
			
//			System.out.println("asadasdsa"+this.dieuKienLoc.getNkh_fk());
			
//			query =
//					"EXEC [REPORT_CANDOIPHATSINHPHATSINHCONGNO_NHOMDT] " +
//					"'" + this.dieuKienLoc.getTuNgay() + "'" +
//					", '" + this.dieuKienLoc.getDenNgay()  + "'" +
//					", '" + taiKhoanId + "'" +
//					", '" + this.dieuKienLoc.getDoiTuongNoId() + "'" +
//					", N'" + this.dieuKienLoc.getLoaiDoiTuongNoId() + "'" +
//					", " + this.dieuKienLoc.getCongTyId() +",'"+this.dieuKienLoc.getNhomKhachHangId()+"'"+
//					", '" + this.dieuKienLoc.getLoaiNhom() + "' "+
//					", '" + this.dieuKienLoc.getNhomDoiTuongId() + "' ";
			query =
					"EXEC [REPORT_CANDOIPHATSINHPHATSINHCONGNO_NHOMDT] " +
					"'" + this.dieuKienLoc.getTuNgay() + "'" +
					", '" + this.dieuKienLoc.getDenNgay()  + "'" +
					", '" + taiKhoanId + "'" +
					", '" + this.dieuKienLoc.getDoiTuongNoId() + "'" +
					", N'" + this.dieuKienLoc.getLoaiDoiTuongNoId() + "'" +
					", " + this.dieuKienLoc.getCongTyId()+",'"+this.dieuKienLoc.getNhomKhachHangId()+"', '"+this.dieuKienLoc.getNhomTaiKhoanId()+"'" +
					", '" + this.dieuKienLoc.getLoaiNhom() + "' "+
					", '" + this.dieuKienLoc.getNhomDoiTuongId() + "' ";
			System.out.println("--GET REPORT-- \n "+query);
				rs = this.db.get(query);
				
				
				if (rs != null)
				{
					int stt = 1;
					while (rs.next())
					{
						//b.ma N'Mã đối tượng', b.ten N'Tên đối tượng',a.NO_DK N'Nợ đầu kỳ',a.CO_DK N'Có đầu kỲ',a.PS_NO N'Nợ',a.PS_CO N'Có',a.NO_CK N'Nợ cuối kỳ',a.CO_CK  N'Có cuối kỳ'
						String maDoiTuong = rs.getString(1);
						String tenDoiTuong = rs.getString(2);
						double noDauKy = rs.getDouble(3);
						double coDauKy = rs.getDouble(4);
						double no = rs.getDouble(5);
						double co = rs.getDouble(6);
						double noCuoiKy = rs.getDouble(7);
						double coCuoiKy = rs.getDouble(8);
						String loaiDoiTuong = rs.getString(9);
						String doiTuong = rs.getString(10);
						
						PhatSinhKeToanItem item = new PhatSinhKeToanItem();
						item.setLoaiDoiTuong(loaiDoiTuong);
						item.setDoiTuong(doiTuong);
						item.setStt(stt);
						item.setMaDoiTuong(maDoiTuong);
						item.setTenDoiTuong(tenDoiTuong);
						item.setNoDauKy(noDauKy);
						item.setCoDauKy(coDauKy);
						item.setNo(no);
						item.setCo(co);
						item.setNoCuoiKy(noCuoiKy);
						item.setCoCuoiKy(coCuoiKy);
						
						this.viewList.add(item);
						stt++;
					}
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public void createRs()
	{
		this.dieuKienLoc.init(db);
		

	}
	
	public boolean exportExcel(ServletOutputStream out, String fileName)
	{
		try{
			if (this.dieuKienLoc.getSoHieuTaiKhoanNo().trim().length() > 0 || this.dieuKienLoc.getNhomTaiKhoanId().trim().length() >0)
			{
				//Lấy pk_seq của tài khoản thuộc chi nhánh
				String query = "select PK_SEQ,SOHIEUTAIKHOAN,TENTAIKHOAN from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '" + this.dieuKienLoc.getSoHieuTaiKhoanNo() + "' and TRANGTHAI = 1 and npp_fk = " + this.dieuKienLoc.getCongTyId();
				ResultSet rs = this.db.get(query);
				String taiKhoanId = "";
				String TENTAIKHOAN="";
				String SOHIEUTAIKHOAN="";
				if (rs != null)
				{
					if (rs.next())
					{
						taiKhoanId = rs.getString(1);
						TENTAIKHOAN=rs.getString("TENTAIKHOAN");
						SOHIEUTAIKHOAN=rs.getString("SOHIEUTAIKHOAN");
					}
					rs.close();
				}
				FileInputStream fstream = null;
				Workbook workbook = new Workbook();
				
				fstream = new FileInputStream(fileName);
				
				workbook.open(fstream);
				workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
				
				Worksheets worksheets = workbook.getWorksheets();
				Worksheet worksheet = worksheets.getSheet(0);
				worksheet.setName("Sheet1");
				
				Cells cells = worksheet.getCells();
//				query =
//						"EXEC [REPORT_CANDOIPHATSINHPHATSINHCONGNO_NHOMDT] " +
//						"'" + this.dieuKienLoc.getTuNgay() + "'" +
//						", '" + this.dieuKienLoc.getDenNgay()  + "'" +
//						", '" + taiKhoanId + "'" +
//						", '" + this.dieuKienLoc.getDoiTuongNoId() + "'" +
//						", N'" + this.dieuKienLoc.getLoaiDoiTuongNoId() + "'" +
//						", " + this.dieuKienLoc.getCongTyId() +",'"+this.dieuKienLoc.getNhomKhachHangId()+"'"+
//						", '" + this.dieuKienLoc.getLoaiNhom() + "' "+
//						", '" + this.dieuKienLoc.getNhomDoiTuongId() + "' ";
				query =
						"EXEC [REPORT_CANDOIPHATSINHPHATSINHCONGNO_NHOMDT] " +
						"'" + this.dieuKienLoc.getTuNgay() + "'" +
						", '" + this.dieuKienLoc.getDenNgay()  + "'" +
						", '" + taiKhoanId + "'" +
						", '" + this.dieuKienLoc.getDoiTuongNoId() + "'" +
						", N'" + this.dieuKienLoc.getLoaiDoiTuongNoId() + "'" +
						", " + this.dieuKienLoc.getCongTyId()+",'"+this.dieuKienLoc.getNhomKhachHangId()+"', '"+this.dieuKienLoc.getNhomTaiKhoanId()+"'" +
						", '" + this.dieuKienLoc.getLoaiNhom() + "' "+
						", '" + this.dieuKienLoc.getNhomDoiTuongId() + "' ";
				
				
				System.out.println("query init: \n" + query + "\n------------------------------------------------------------");
				rs = this.db.get(query);
				
			    Cell cell = cells.getCell("A4");
			    
			    cell = cells.getCell("A4");
			    cell.setValue("Từ ngày: " + this.dieuKienLoc.getTuNgay() + " - " + this.dieuKienLoc.getDenNgay());
			    
			    
			    cell = cells.getCell("A5");
			    cell.setValue(" Tài khoản: " + SOHIEUTAIKHOAN + " - " + TENTAIKHOAN);
	             
			    
	             
				ResultSetMetaData rsmd = rs.getMetaData();
				int rowNumber = rsmd.getColumnCount();
				int countRow = 8;
							
				int stt = 0;
				while(rs.next())
				{
					stt++;
					for(int i = 0;i <= rowNumber - 2 ; i ++)
					{
						if (i == 0)
						{
							cell = cells.getCell(countRow,0);
							cell.setValue(stt);
							cell = ReportAPI.CreateBorderSetting2(cell,1);
						}
						else
						{
							cell = cells.getCell(countRow, i);
							if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i) == Types.INTEGER || rsmd.getColumnType(i) == Types.DECIMAL)
							{
								cell.setValue(rs.getDouble(i));
							}
							else
							{
								cell.setValue(rs.getString(i));
							}
							cell = ReportAPI.CreateBorderSetting2(cell);
						}
						
					}
					++countRow;
				}
				
				for(int i = 0; i <= rowNumber - 2; i++)
				{
					ReportAPI.CreateBorderSetting3(cells.getCell(countRow - 1,i));
				}
				
				workbook.save(out);
				fstream.close();
			}
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	public void DBClose()
	{
		if (this.db != null)
		{
			this.db.shutDown();
		}
	}
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
		listPages = PhanTrang.getListPages(num);
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
	public dbutils getDb() {
		return db;
	}
	public void setDb(dbutils db) {
		this.db = db;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setCongTyId(String congTyId) {
		this.congTyId = congTyId;
	}

	public String getCongTyId() {
		return congTyId;
	}

	public void setDieuKienLoc(DKLocBaoCaoKeToan dieuKienLoc) {
		this.dieuKienLoc = dieuKienLoc;
	}

	public DKLocBaoCaoKeToan getDieuKienLoc() {
		return dieuKienLoc;
	}

	public void setViewList(List<PhatSinhKeToanItem> viewList) {
		this.viewList = viewList;
	}

	public List<PhatSinhKeToanItem> getViewList() {
		return viewList;
	}

	public List<String> getColunmNameList() {
		return colunmNameList;
	}
	
	@Override
	public void getLoaiTaiKhoan() {
		if(this.getDieuKienLoc().getSoHieuTaiKhoanNo().trim().length() == 0)
			return;
		String query = "SELECT TOP 1 ISNULL(DUNGCHONCC,0) DUNGCHONCC, ISNULL(DUNGCHOKHACHHANG, 0) DUNGCHOKHACHHANG , " +
				" ISNULL(DUNGCHONHANVIEN,0) DUNGCHONHANVIEN,  ISNULL(DUNGCHODOITUONGKHAC, 0) AS DUNGCHODOITUONGKHAC, SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '" + this.getDieuKienLoc().getSoHieuTaiKhoanNo() + "'";
		System.out.println("lay tai khoan " + query);
		ResultSet rs = db.get(query);
		try {
			
			rs.next();
			if(rs.getString("DUNGCHONCC").equals("1")){
				this.getDieuKienLoc().setLoaiNhom("2");
			}
			
			if(rs.getString("DUNGCHOKHACHHANG").equals("1")){
				this.getDieuKienLoc().setLoaiNhom("0");
			}
			
			if(rs.getString("DUNGCHONHANVIEN").equals("1")){
				this.getDieuKienLoc().setLoaiNhom("1");
			}
		
			if(rs.getString("DUNGCHODOITUONGKHAC").equals("1")){
				this.getDieuKienLoc().setLoaiNhom("3");
			}
			
			this.getDieuKienLoc().setNhomDoiTuongId("");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}