package geso.traphaco.erp.beans.baocaocongnoquay.imp;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.DKLocBaoCaoKeToan;
import geso.traphaco.center.util.PhatSinhKeToanItem;
import geso.traphaco.erp.beans.baocaocongno.IErp_BaoCaoCongNoList;
import geso.traphaco.erp.beans.baocaocongnoquay.IErp_CongNoKhachHangQuayList;
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

public class Erp_CongNoKhachHangQuayList  extends Phan_Trang implements IErp_CongNoKhachHangQuayList
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
	
	public Erp_CongNoKhachHangQuayList()
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
		if (this.dieuKienLoc.getQuay_fk().trim().length() > 0)
		{

			try {
			
			
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
			
			String query = "";
			String thangDauky = "0"+ thangdauky;
			thangDauky = thangDauky.substring(thangDauky.length() -2);
				
			System.out.println("query init: \n" + query + "\n------------------------------------------------------------");
			
//			System.out.println("asadasdsa"+this.dieuKienLoc.getNkh_fk());
//			REPORT_THEODOIKHACHHANGQUAY '2017-01-01','2017-06-30','','',100002
			query =
					"EXEC [REPORT_THEODOIKHACHHANGQUAY] " +
					"'" + this.dieuKienLoc.getTuNgay() + "'" +
					", '" + this.dieuKienLoc.getDenNgay()  + "'" +
					", '" + this.dieuKienLoc.getDoiTuongNoId() + "'" +
					", N'" + this.dieuKienLoc.getLoaiDoiTuongNoId() + "'" +
					", " + this.dieuKienLoc.getQuay_fk()+" ";
			System.out.println("--GET REPORT-- \n "+query);
				ResultSet rs = this.db.get(query);
				
				
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
			if (this.dieuKienLoc.getQuay_fk().trim().length() > 0)
			{
				//Lấy pk_seq của tài khoản thuộc chi nhánh
				String query = "select Ma,TEN from nhaphanphoi where pk_seq = '" + this.dieuKienLoc.getQuay_fk() + "' ";
				ResultSet rs = this.db.get(query);
				String ma = "";
				String tenquay="";
				if (rs != null)
				{
					if (rs.next())
					{
						ma = rs.getString("Ma");
						tenquay=rs.getString("TEN");
						
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
				query =
						"EXEC [REPORT_THEODOIKHACHHANGQUAY] " +
						"'" + this.dieuKienLoc.getTuNgay() + "'" +
						", '" + this.dieuKienLoc.getDenNgay()  + "'" +
						", '" + this.dieuKienLoc.getDoiTuongNoId() + "'" +
						", N'" + this.dieuKienLoc.getLoaiDoiTuongNoId() + "'" +
						", " + this.dieuKienLoc.getQuay_fk()+" ";
				
				System.out.println("query init: \n" + query + "\n------------------------------------------------------------");
				rs = this.db.get(query);
				
			    Cell cell = cells.getCell("A4");
			    
			    cell = cells.getCell("A4");
			    cell.setValue("Từ ngày: " + this.dieuKienLoc.getTuNgay() + " - " + this.dieuKienLoc.getDenNgay());
			    
			    
			    cell = cells.getCell("A5");
			    cell.setValue(" Quầy: " + ma + " - " + tenquay);
	             
			    
	             
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
						}
						cell = ReportAPI.CreateBorderSetting2(cell);
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
}