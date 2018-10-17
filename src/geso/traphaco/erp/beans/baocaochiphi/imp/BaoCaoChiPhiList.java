package geso.traphaco.erp.beans.baocaochiphi.imp;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Erp_Item;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.baocaochiphi.IBaoCaoChiPhiList;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.FileInputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class BaoCaoChiPhiList  extends Phan_Trang implements IBaoCaoChiPhiList
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String congTyId;
	private String userId;
	private String phongBanId;
	private String chiNhanhId;
	private String nam;
	private String thangBatDau;
	private String thangKeThuc;
	private String nhomChiPhiId;
	private ResultSet rsNhomChiPhi;
	private String[] phongBanIds;
	private String msg;
	
	private List<Erp_Item> phongBanList;
	private List<ChiPhiItem> list;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	private dbutils db;
	
	public BaoCaoChiPhiList()
	{
		this.congTyId = "";
		this.userId = "";
		this.nhomChiPhiId ="";
		this.phongBanId = "";
		this.chiNhanhId = "";
		this.nam = "";
		this.thangBatDau = "1";
		this.thangKeThuc = "12";
		this.msg = ""; 
		
		this.phongBanList = new ArrayList<Erp_Item>();
		this.list = new ArrayList<ChiPhiItem>();
		
		this.currentPages = 1;
		this.num = 1;
		this.db = new dbutils();
	}
	
	public void init()
	{
		initList();
	}
	
	private void initList() {
		String query =
			" SELECT 1 AS PK_SEQ,'All' AS TEN UNION ALL "+
			"select pk_seq, ISNULL(ma, '') + ' - ' + ISNULL(ten, '') as ten from erp_donViThucHien \n" +
			"where trangThai = 1 order by pk_seq asc";
		if (this.phongBanIds != null) {
			if (this.phongBanIds.length > 0) {
				this.phongBanId = "";
				for (int i = 0; i < this.phongBanIds.length; i++) {
					this.phongBanId = this.phongBanId + this.phongBanIds[i] + ",";
				}
			}
		}
		Erp_Item.getListFromQuery(db, query, this.phongBanList);
		query = "SELECT PK_SEQ AS PKSEQ, ISNULL(MA,'') + ' - ' + ISNULL(TEN,'') AS TEN FROM ERP_NHOMKHOANMUC AS TEN WHERE CONGTY_FK = " +this.congTyId;
		this.rsNhomChiPhi = this.db.get(query);
	}

	public boolean exportExcel(ServletOutputStream out, String fileName)
	{
		try{
//			if (this.dieuKienLoc.getSoHieuTaiKhoanNo().trim().length() > 0)
//			{
				//Lấy pk_seq của tài khoản thuộc chi nhánh
				FileInputStream fstream = null;
				Workbook workbook = new Workbook();
				
				fstream = new FileInputStream(fileName);
				
				workbook.open(fstream);
				workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
				
				Worksheets worksheets = workbook.getWorksheets();
				Worksheet worksheet = worksheets.getSheet(0);
				worksheet.setName("BaoCaoChiPhiTongHop");
				
				Cells cells = worksheet.getCells();
				
				if(this.phongBanIds != null){
					String tmp = "";
					for(int i = 0; i < this.phongBanIds.length; i++){
						tmp += this.phongBanIds[i] + ",";
					}
					this.phongBanId = tmp.substring(0, tmp.length() - 1);
						
					System.out.println("Phòng ban ID : " + this.phongBanId);
				}
				String phongBanTen = "Phòng Ban : ";
				String queryTenPB =
						"SELECT TEN FROM ("+
						" SELECT 1 AS PK_SEQ,'All' AS TEN " +
						" UNION ALL "+
						"select pk_seq, ISNULL(ma, '') + ' - ' + ISNULL(ten, '') as ten from erp_donViThucHien \n" +
						"where trangThai = 1)DATA WHERE PK_SEQ IN ("+this.phongBanId+") ";
				System.out.println("Query Phong ban ten:"+queryTenPB);
				ResultSet rs = db.get(queryTenPB);
				if(rs!= null){
					while(rs.next()){
						phongBanTen += rs.getString("TEN") + ",";
					}
				}
				phongBanTen= phongBanTen.substring(0, phongBanTen.length() - 1);
				
				String query = this.getQueryBCTongHopChiPhi();
				ChiPhiItem.getListFromQuery(db, query, list,"tonghop");
				
			    Cell cell = cells.getCell("A8");
			    Style style;
				style = cell.getStyle();	
			    cell = cells.getCell("A3");
			    cell.setValue("Từ tháng: " + this.thangBatDau + " / " + this.nam + " Đến tháng " + this.thangKeThuc + " / " + this.nam);
			    cell = cells.getCell("A4");
			    cell.setValue(phongBanTen);
			    int startColumn = 6;
			    int tmp = startColumn;
			    
			    for (int i = Integer.parseInt(this.thangBatDau); i <= Integer.parseInt(this.thangKeThuc); i++)
			    {
			    	cell = cells.getCell(7, tmp++);
				    cell.setValue("Tháng " + i);
				    cell.setStyle(style);
				    cell = ReportAPI.CreateBorderSetting4(cell);
			    }
			    cell = cells.getCell("A1");
			    style = cell.getStyle();
			    style.setNumber(0);
//				int rowNumber = this.list.size();
//				int countRow = 10;
							
				int curRow = 8;
				for(ChiPhiItem item : this.list)
				{
					tmp = 0;
					cells.setRowHeight(curRow, 30.0F);
					cell = cells.getCell(curRow, tmp++);
					cell.setValue(item.getTaiKhoanChiPhi());
					style.setNumber(0);
					cell.setStyle(style);
					cell = ReportAPI.CreateBorderSetting4(cell);
					
					cell = cells.getCell(curRow, tmp++);
					cell.setValue(item.getPhongBan());
					style.setNumber(0);
					cell.setStyle(style);
					cell = ReportAPI.CreateBorderSetting4(cell);
					
					cell = cells.getCell(curRow, tmp++);
					cell.setValue(item.getNhomKhoanMuc());
					style.setNumber(0);
					cell.setStyle(style);
					cell = ReportAPI.CreateBorderSetting4(cell);
					
					cell = cells.getCell(curRow, tmp++);
					cell.setValue(item.getKhoanMuc());
					style.setNumber(0);
					cell.setStyle(style);
					cell = ReportAPI.CreateBorderSetting4(cell);
					
					cell = cells.getCell(curRow, tmp++);
					cell.setValue(item.getTongCong());
					style.setNumber(3);
					cell.setStyle(style);
					cell = ReportAPI.CreateBorderSetting4(cell);
					
					cell = cells.getCell(curRow, tmp++);
					cell.setValue(item.getBinhQuan());
					style.setNumber(3);
					cell.setStyle(style);
					cell = ReportAPI.CreateBorderSetting4(cell);
					
					tmp = startColumn;
				    for (int i = Integer.parseInt(this.thangBatDau); i <= Integer.parseInt(this.thangKeThuc); i++)
				    {
				    	cell = cells.getCell(curRow, tmp++);
					    cell.setValue(item.getThang("thang" + i));
					    style.setNumber(3);
						cell.setStyle(style);
					    cell = ReportAPI.CreateBorderSetting4(cell);
				    }
				    
					++curRow;
				}
				
				for(int i = 8; i <= this.list.size() + 8 ; i++)
				{
					cell = cells.getCell(i, 0);
					if (cell.getStringValue().trim().length() == 0)
					{
						cells.setRowHeight(i, 50.0F);
						for (int j = 0; j < 5 + (1 + Integer.parseInt(this.thangKeThuc) - Integer.parseInt(this.thangBatDau)); j++)
						{
							cell = cells.getCell(i, j);
							style = cell.getStyle();		
							Font font = style.getFont();
							font.setSize(12);
							font.setBold(true);
							style.setFont(font);
							cell.setStyle(style);
						}
					}
				}
				
				//Sheet 2
				worksheet = worksheets.getSheet(1);
				worksheet.setName("BaoCaoChiPhiChiTiet");
				cells = worksheet.getCells();
				cell = cells.getCell("A3");
			    cell.setValue("Từ tháng: " + this.thangBatDau + " / " + this.nam + " Đến tháng " + this.thangKeThuc + " / " + this.nam);
			    cell = cells.getCell("A4");
			    cell.setValue(phongBanTen);
			    cell = cells.getCell("A1");
			    style = cell.getStyle();
				style.setNumber(0);
				String query1 = this.getQueryBCChiTietChiPhi();
				ChiPhiItem.getListFromQuery(db, query1, list,"chitiet");
				curRow = 8;
				for(ChiPhiItem item : this.list)
				{
					tmp = 0;
					cells.setRowHeight(curRow, 30.0F);
					cell = cells.getCell(curRow, tmp++);
					cell.setValue(item.getTaiKhoanChiPhi());
					style.setNumber(0);
					cell.setStyle(style);
					cell = ReportAPI.CreateBorderSetting4(cell);
					
					cell = cells.getCell(curRow, tmp++);
					cell.setValue(item.getPhongBan());
					style.setNumber(0);
					cell.setStyle(style);
					cell = ReportAPI.CreateBorderSetting4(cell);
					
					cell = cells.getCell(curRow, tmp++);
					cell.setValue(item.getNhomKhoanMuc());
					style.setNumber(0);
					cell.setStyle(style);
					cell = ReportAPI.CreateBorderSetting4(cell);
					
					cell = cells.getCell(curRow, tmp++);
					cell.setValue(item.getKhoanMuc());
					style.setNumber(0);
					cell.setStyle(style);
					cell = ReportAPI.CreateBorderSetting4(cell);
					
					cell = cells.getCell(curRow, tmp++);
					cell.setValue(item.getLoaiChungTu());
					style.setNumber(0);
					cell.setStyle(style);
					cell = ReportAPI.CreateBorderSetting4(cell);
					
					cell = cells.getCell(curRow, tmp++);
					cell.setValue(item.getSoChungTu());
					style.setNumber(0);
					cell.setStyle(style);
					cell = ReportAPI.CreateBorderSetting4(cell);
					
					cell = cells.getCell(curRow, tmp++);
					cell.setValue(item.getNgayChungTu());
					style.setNumber(0);
					cell.setStyle(style);
					cell = ReportAPI.CreateBorderSetting4(cell);
					
					cell = cells.getCell(curRow, tmp++);
					cell.setValue(item.getSoTienTang());
					style.setNumber(3);
					cell.setStyle(style);
					cell = ReportAPI.CreateBorderSetting4(cell);
					
					cell = cells.getCell(curRow, tmp++);
					cell.setValue(item.getSoTienGiam());
					style.setNumber(3);
					cell.setStyle(style);
					cell = ReportAPI.CreateBorderSetting4(cell);
					
					cell = cells.getCell(curRow, tmp++);
					cell.setValue(item.getTaiKhoanDoiUng().trim());
					style.setNumber(0);
					cell.setStyle(style);
					cell = ReportAPI.CreateBorderSetting4(cell);
					
					++curRow;
				}
				for(int i = 8; i <= this.list.size() + 8 ; i++)
				{
					cell = cells.getCell(i, 0);
					if (cell.getStringValue().trim().length() == 0)
					{
						cells.setRowHeight(i, 50.0F);
						for (int j = 0; j < 5 + (1 + Integer.parseInt(this.thangKeThuc) - Integer.parseInt(this.thangBatDau)); j++)
						{
							cell = cells.getCell(i, j);
							style = cell.getStyle();
							
							Font font = style.getFont();
							font.setSize(12);
							font.setBold(true);
							style.setFont(font);
							cell.setStyle(style);
						}
					}
				}
				
				workbook.save(out);
			
				fstream.close();
//			}
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	public void DBClose()
	{
		try {
		if(this.rsNhomChiPhi != null) this.rsNhomChiPhi.close();
		if(this.phongBanList !=null) this.phongBanList.clear();
		if(this.list != null) this.list.clear();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally {
			if(this.db != null) this.db.shutDown();
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


	public void setPhongBanList(List<Erp_Item> phongBanList) {
		this.phongBanList = phongBanList;
	}

	public List<Erp_Item> getPhongBanList() {
		return phongBanList;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setPhongBanId(String phongBanId) {
		this.phongBanId = phongBanId;
	}

	public String getPhongBanId() {
		return phongBanId;
	}

	public void setPhongBanIds(String[] phongBanIds){
		this.phongBanIds = phongBanIds;
	}
	
	public String[] getPhongBanIds(){
		return this.phongBanIds;
	}
	public void setChiNhanhId(String chiNhanhId) {
		this.chiNhanhId = chiNhanhId;
	}

	public String getChiNhanhId() {
		return chiNhanhId;
	}

	public void setNam(String nam) {
		this.nam = nam;
	}

	public String getNam() {
		return nam;
	}

	public void setThangKeThuc(String thangKeThuc) {
		this.thangKeThuc = thangKeThuc;
	}

	public String getThangKeThuc() {
		return thangKeThuc;
	}

	public void setThangBatDau(String thangBatDau) {
		this.thangBatDau = thangBatDau;
	}

	public String getThangBatDau() {
		return thangBatDau;
	}
	public String getNhomChiPhiId() {
		return nhomChiPhiId;
	}

	public void setNhomChiPhiId(String nhomChiPhiId) {
		this.nhomChiPhiId = nhomChiPhiId;
	}

	public ResultSet getRsNhomChiPhi() {
		return rsNhomChiPhi;
	}

	public void setRsNhomChiPhi(ResultSet rsNhomChiPhi) {
		this.rsNhomChiPhi = rsNhomChiPhi;
	}
	public String getQueryBCTongHopChiPhi(){
		String query = "";
		//query63x lấy dữ liệu của các tài khoản 63x
		String queryTK63x ="";
		//query64x lấy dữ liệu của các tài khoản 64x
		String queryTK64x = "";
		//nếu chọn phòng ban All thì mới lấy dữ liệu TK : 63x
		if(this.phongBanId.trim().length() == 1 && this.phongBanId.trim().equals("1") && this.nhomChiPhiId.trim().length() == 0 ){
			queryTK63x = " SELECT CASE WHEN DATA.SOHIEUTAIKHOAN LIKE '632%' THEN 1 ELSE 2 END AS LOAI, \n " + 
					" DATA.*,'' AS DVTHTEN, '' AS NCPID,''AS NCPTEN,'' AS NKMTEN, \n " ;
			for(int i = Integer.parseInt(this.thangBatDau);i<=Integer.parseInt(this.thangKeThuc);i++){
				queryTK63x += "  ISNULL(THANG"+i+".PHATSINH,0) + ";
			}
			queryTK63x += " 0 AS TONGCONG , ( ";
			for(int i = Integer.parseInt(this.thangBatDau);i<=Integer.parseInt(this.thangKeThuc);i++){
				queryTK63x += "  ISNULL(THANG"+i+".PHATSINH,0) + ";
			}
			queryTK63x += " 0 )/"+ (Integer.parseInt(this.thangKeThuc) - Integer.parseInt(this.thangBatDau) +1) +" AS BINHQUAN";
			for(int i = Integer.parseInt(this.thangBatDau);i<=Integer.parseInt(this.thangKeThuc);i++){
				queryTK63x += " , ISNULL(THANG"+i+".PHATSINH,0) AS THANG"+i;
			}
			for(int i = 1;i<=12; i++){
				int kiemTra = 0;
				for(int j=Integer.parseInt(this.thangBatDau);j<=Integer.parseInt(this.thangKeThuc);j++)
					if(i == j)
						kiemTra ++;
				if(kiemTra ==0)
					queryTK63x +=", 0 AS THANG"+i;
			}
			queryTK63x +="\n FROM \n " +
					" ( \n " + 
					" SELECT DISTINCT TAIKHOAN.TKID, TAIKHOAN.SOHIEUTAIKHOAN \n " +
					" FROM ( \n" +
					" SELECT DISTINCT TK.PK_SEQ AS TKID,TK.SOHIEUTAIKHOAN \n " + 
					" FROM ERP_PHATSINHKETOAN PS \n " + 
					" INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK \n " + 
					" WHERE MONTH(PS.NGAYGHINHAN) >="+Integer.parseInt(this.thangBatDau) +
					" AND MONTH(PS.NGAYGHINHAN) <="+Integer.parseInt(this.thangKeThuc)+
					" AND YEAR(PS.NGAYGHINHAN) = "+this.nam+" \n " +
					" AND (TK.SOHIEUTAIKHOAN LIKE '63%') AND TK.CONGTY_FK = "+this.congTyId+" \n " + 
					/*" AND PS.DOITUONG= N'Khoản mục chi phí' \n"+*/
					" UNION \n " +
					" SELECT * FROM OPENQUERY(["+Utility.prefixLinkDMS+"],' \n " +
					" SELECT DISTINCT TK.PK_SEQ AS TKID,TK.SOHIEUTAIKHOAN \n " + 
					" FROM "+Utility.prefixReportDMS+"ERP_PHATSINHKETOAN PS \n " + 
					" INNER JOIN "+Utility.prefixReportDMS+"ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK \n " + 
					" WHERE MONTH(PS.NGAYGHINHAN) >="+Integer.parseInt(this.thangBatDau) +
					" AND MONTH(PS.NGAYGHINHAN) <="+Integer.parseInt(this.thangKeThuc)+
					" AND YEAR(PS.NGAYGHINHAN) = "+this.nam+" \n " +
					" AND (TK.SOHIEUTAIKHOAN LIKE ''63%'') AND TK.CONGTY_FK = "+this.congTyId+" ')\n " + 
					/*" AND PS.DOITUONG= N'Khoản mục chi phí' \n"+*/
					" )TAIKHOAN \n" +
					" )DATA \n " ;
			for(int i = Integer.parseInt(this.thangBatDau);i<=Integer.parseInt(this.thangKeThuc);i++){
				queryTK63x += " LEFT JOIN \n " + 
						" ( \n " + 
						" SELECT PHATSINH.TKID, SUM(PHATSINH.PHATSINH) AS PHATSINH \n "+
						" FROM ( \n "+
						" SELECT PS.TAIKHOAN_FK AS TKID,SUM(PS.NO) - SUM(PS.CO) AS PHATSINH \n " + 
						" FROM ERP_PHATSINHKETOAN PS \n " + 
						" INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ =PS.TAIKHOAN_FK \n " + 
						" WHERE MONTH(PS.NGAYGHINHAN) ="+i+" AND YEAR(PS.NGAYGHINHAN) = "+this.nam+" \n " + 
						" AND (TK.SOHIEUTAIKHOAN LIKE '63%') AND TK.CONGTY_FK = "+this.congTyId+" \n " + 
						/*" AND PS.DOITUONG= N'Khoản mục chi phí' \n"+*/
						" GROUP BY PS.TAIKHOAN_FK \n " + 
						" UNION ALL \n " +
						" SELECT * FROM OPENQUERY(["+Utility.prefixLinkDMS+"],' \n " +
						" SELECT PS.TAIKHOAN_FK AS TKID,SUM(PS.NO) - SUM(PS.CO) AS PHATSINH \n " + 
						" FROM "+Utility.prefixReportDMS+"ERP_PHATSINHKETOAN PS \n " + 
						" INNER JOIN "+Utility.prefixReportDMS+"ERP_TAIKHOANKT TK ON TK.PK_SEQ =PS.TAIKHOAN_FK \n " + 
						" WHERE MONTH(PS.NGAYGHINHAN) ="+i+" AND YEAR(PS.NGAYGHINHAN) = "+this.nam+" \n " + 
						" AND (TK.SOHIEUTAIKHOAN LIKE ''63%'') AND TK.CONGTY_FK = "+this.congTyId+" \n " + 
						/*" AND PS.DOITUONG= N'Khoản mục chi phí' \n"+*/
						" GROUP BY PS.TAIKHOAN_FK ' )\n " +
						" )PHATSINH \n"+
						" GROUP BY PHATSINH.TKID \n " +
						" )THANG"+i+" ON THANG"+i+".TKID =DATA.TKID \n " ;
			}
			queryTK63x +=" UNION ALL \n " ;
		}
		queryTK64x = " SELECT  \n " + 
				" CASE WHEN DATA.SOHIEUTAIKHOAN LIKE '641%' THEN 3 ELSE 4 END AS LOAI, \n " + 
				" DATA.TKID, DATA.SOHIEUTAIKHOAN,DATA.DVTHTEN,DATA.NCPID,DATA.NCPTEN,DATA.NKMTEN, \n " ;
		for(int i = Integer.parseInt(this.thangBatDau);i<=Integer.parseInt(this.thangKeThuc);i++){
			queryTK64x += "  ISNULL(THANG"+i+".PHATSINH,0) + ";
		}
		queryTK64x += " 0 AS TONGCONG , ( ";
		for(int i = Integer.parseInt(this.thangBatDau);i<=Integer.parseInt(this.thangKeThuc);i++){
			queryTK64x += "  ISNULL(THANG"+i+".PHATSINH,0) + ";
		}
		queryTK64x += " 0 )/"+ (Integer.parseInt(this.thangKeThuc) - Integer.parseInt(this.thangBatDau) +1) +" AS TONGCONG";
		for(int i = Integer.parseInt(this.thangBatDau);i<=Integer.parseInt(this.thangKeThuc);i++){
			queryTK64x += " , ISNULL(THANG"+i+".PHATSINH,0) AS THANG"+i;
		}
		for(int i = 1;i<=12; i++){
			int kiemTra = 0;
			for(int j=Integer.parseInt(this.thangBatDau);j<=Integer.parseInt(this.thangKeThuc);j++)
				if(i == j)
					kiemTra ++;
			if(kiemTra ==0)
				queryTK64x +=", 0 AS THANG"+i;
		}
		queryTK64x +="  FROM \n " + 
				" ( \n " + 
				" SELECT DISTINCT TKID,SOHIEUTAIKHOAN,DVTHTEN,DOITUONG, \n " + 
				" NCPID, \n" +
				" NCPTEN,NKMTEN \n " + 
				" FROM (" +
				" SELECT DISTINCT TK.PK_SEQ AS TKID,TK.SOHIEUTAIKHOAN,ISNULL(DVTH.TEN,'') AS DVTHTEN,ISNULL(PS.DOITUONG,'') AS DOITUONG, \n " + 
				" PS.MADOITUONG AS NCPID, \n" +
				" ISNULL(ISNULL(NCP.TEN,TTCP.TEN),'') AS NCPTEN,ISNULL(NKM.MA,'') + '-' +ISNULL(NKM.TEN,'') AS NKMTEN \n " + 
				" FROM ERP_PHATSINHKETOAN PS \n " + 
				" INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK \n " + 
				" LEFT JOIN ERP_NHOMCHIPHI NCP ON CONVERT(VARCHAR,NCP.PK_SEQ) = PS.MADOITUONG AND PS.DOITUONG= N'Khoản mục chi phí' \n " + 
				" LEFT JOIN ERP_TRUNGTAMCHIPHI TTCP ON CONVERT(VARCHAR,NCP.PK_SEQ) = PS.MADOITUONG AND PS.DOITUONG= N'Trung tâm chi phí' \n " +
				" LEFT JOIN ERP_NHOMKHOANMUC_KHOANMUCCHIPHI NKM_KMCP ON NCP.PK_SEQ = NKM_KMCP.KHOANMUCCHIPHI_FK \n " +
				" LEFT JOIN ERP_NHOMKHOANMUC NKM ON NKM.PK_SEQ = NKM_KMCP.NHOMKHOANMUC_FK \n " +
				" LEFT JOIN ERP_DONVITHUCHIEN DVTH ON NCP.DONVITHUCHIEN_FK =DVTH.PK_SEQ  \n " + 
				" WHERE MONTH(PS.NGAYGHINHAN) >="+Integer.parseInt(this.thangBatDau) +
				" AND MONTH(PS.NGAYGHINHAN) <="+Integer.parseInt(this.thangKeThuc)+
				" AND YEAR(PS.NGAYGHINHAN) = "+this.nam+" \n " +
				" AND (TK.SOHIEUTAIKHOAN LIKE '64%') AND TK.CONGTY_FK = "+this.congTyId+" \n "  
				/*+" AND PS.DOITUONG= N'Khoản mục chi phí' \n"*/;
		if(this.phongBanId.trim().length() >1)
			queryTK64x +=" AND NCP.DONVITHUCHIEN_FK IN ("+this.phongBanId+") \n "; 
		if(this.nhomChiPhiId.trim().length() >1)
			queryTK64x +=" AND NKM.PK_SEQ IN ("+this.nhomChiPhiId+") \n "; 
		queryTK64x += " UNION ALL \n " +
				"SELECT * FROM OPENQUERY(["+Utility.prefixLinkDMS+"],'" +
				" SELECT DISTINCT TK.PK_SEQ AS TKID,TK.SOHIEUTAIKHOAN,ISNULL(DVTH.TEN,'''') AS DVTHTEN,PS.DOITUONG, \n " + 
				" ISNULL(PS.MADOITUONG,'''') AS NCPID, \n" +
				" ISNULL(ISNULL(NCP.TEN,TTCP.TEN),'''') AS NCPTEN,ISNULL(NKM.MA,'''') + ''-'' +ISNULL(NKM.TEN,'''') AS NKMTEN \n " + 
				" FROM "+Utility.prefixReportDMS+"ERP_PHATSINHKETOAN PS \n " + 
				" INNER JOIN "+Utility.prefixReportDMS+"ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK \n " + 
				" LEFT JOIN "+Utility.prefixReportDMS+"ERP_NHOMCHIPHI NCP ON CONVERT(VARCHAR,NCP.PK_SEQ) = PS.MADOITUONG AND PS.DOITUONG= N''Khoản mục chi phí'' \n " + 
				" LEFT JOIN "+Utility.prefixReportDMS+"ERP_TRUNGTAMCHIPHI TTCP ON CONVERT(VARCHAR,NCP.PK_SEQ) = PS.MADOITUONG AND PS.DOITUONG= N''Trung tâm chi phí'' \n " + 
				" LEFT JOIN "+Utility.prefixReportDMS+"ERP_NHOMKHOANMUC_KHOANMUCCHIPHI NKM_KMCP ON NCP.PK_SEQ = NKM_KMCP.KHOANMUCCHIPHI_FK \n " +
				" LEFT JOIN "+Utility.prefixReportDMS+"ERP_NHOMKHOANMUC NKM ON NKM.PK_SEQ = NKM_KMCP.NHOMKHOANMUC_FK \n " +
				" LEFT JOIN "+Utility.prefixReportDMS+"ERP_DONVITHUCHIEN DVTH ON NCP.DONVITHUCHIEN_FK =DVTH.PK_SEQ  \n " + 
				" WHERE MONTH(PS.NGAYGHINHAN) >="+Integer.parseInt(this.thangBatDau) +
				" AND MONTH(PS.NGAYGHINHAN) <="+Integer.parseInt(this.thangKeThuc)+
				" AND YEAR(PS.NGAYGHINHAN) = "+this.nam+" \n " +
				" AND (TK.SOHIEUTAIKHOAN LIKE ''64%'') AND TK.CONGTY_FK = "+this.congTyId+" \n "  
				/*+" AND PS.DOITUONG= N'Khoản mục chi phí' \n"*/;
		if(this.phongBanId.trim().length() >1)
			queryTK64x +=" AND NCP.DONVITHUCHIEN_FK IN ("+this.phongBanId+") \n ";
		if(this.nhomChiPhiId.trim().length() >1)
			queryTK64x +=" AND NKM.PK_SEQ IN ("+this.nhomChiPhiId+") \n ";
		queryTK64x += " ')";
		queryTK64x +=")TAIKHOAN";
		queryTK64x +=" )DATA \n " ; 
		for(int i = Integer.parseInt(this.thangBatDau);i<=Integer.parseInt(this.thangKeThuc);i++){
			queryTK64x += " LEFT JOIN \n " + 
					" ( \n " + 
					" SELECT TKID,DOITUONG,MADOITUONG,SUM(PHATSINH) AS PHATSINH \n " +
					" FROM ( "+
					" SELECT PS.TAIKHOAN_FK AS TKID,ISNULL(PS.DOITUONG,'') AS DOITUONG,ISNULL(PS.MADOITUONG,'') AS MADOITUONG, \n " + 
					" (SUM(NO) - SUM(CO)) AS PHATSINH \n " + 
					" FROM ERP_PHATSINHKETOAN PS \n " + 
					" WHERE MONTH(PS.NGAYGHINHAN) ="+i+" AND YEAR(PS.NGAYGHINHAN) = "+nam+" \n " + 
					" AND PS.TAIKHOAN_FK IN  \n " + 
					" (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE (SOHIEUTAIKHOAN LIKE '64%') AND CONGTY_FK = "+this.congTyId+") \n "
					/*+" AND PS.DOITUONG= N'Khoản mục chi phí' \n"*/;
			if(this.phongBanId.trim().length() >1)
				queryTK64x +=" AND PS.MADOITUONG IN (SELECT CONVERT(VARCHAR,PK_SEQ) FROM ERP_NHOMCHIPHI WHERE DONVITHUCHIEN_FK IN ("+this.phongBanId+")) \n " ; 
			queryTK64x +=" GROUP BY PS.TAIKHOAN_FK,PS.DOITUONG,PS.MADOITUONG  \n " ;
			queryTK64x +=" UNION ALL \n " ;
			queryTK64x +=" SELECT * FROM OPENQUERY(["+Utility.prefixLinkDMS+"],'";
			queryTK64x +=" SELECT PS.TAIKHOAN_FK AS TKID,ISNULL(PS.DOITUONG,'''') AS DOITUONG,ISNULL(PS.MADOITUONG,'''') AS MADOITUONG, \n " + 
					" (SUM(NO) - SUM(CO)) AS PHATSINH \n " + 
					" FROM "+Utility.prefixReportDMS+"ERP_PHATSINHKETOAN PS \n " + 
					" WHERE MONTH(PS.NGAYGHINHAN) ="+i+" AND YEAR(PS.NGAYGHINHAN) = "+nam+" \n " + 
					" AND PS.TAIKHOAN_FK IN  \n " + 
					" (SELECT PK_SEQ FROM "+Utility.prefixReportDMS+"ERP_TAIKHOANKT WHERE (SOHIEUTAIKHOAN LIKE ''64%'') AND CONGTY_FK = "+this.congTyId+")  \n "
					/*+" AND PS.DOITUONG= N'Khoản mục chi phí' \n"*/;
			if(this.phongBanId.trim().length() >1)
				queryTK64x +=" AND PS.MADOITUONG IN (SELECT CONVERT(VARCHAR,PK_SEQ) FROM ERP_NHOMCHIPHI WHERE DONVITHUCHIEN_FK IN ("+this.phongBanId+")) \n " ;
			if(this.nhomChiPhiId.trim().length() >1)
				queryTK64x +=" AND PS.MADOITUONG IN (SELECT CONVERT(VARCHAR,KHOANMUCCHIPHI_FK) FROM ERP_NHOMKHOANMUC_KHOANMUCCHIPHI WHERE NHOMKHOANMUC_FK IN ("+this.nhomChiPhiId+")) \n " ; 
			queryTK64x +=" GROUP BY PS.TAIKHOAN_FK,PS.DOITUONG,PS.MADOITUONG  ') \n " ;
			queryTK64x +=")PHATSINH \n "+
					" GROUP BY TKID,DOITUONG,MADOITUONG \n";
			queryTK64x +=" )THANG"+i+" ON THANG"+i+".TKID =DATA.TKID AND THANG"+i+".MADOITUONG = DATA.NCPID AND THANG"+i+".DOITUONG = DATA.DOITUONG \n ";
		}
		query = queryTK63x + queryTK64x  + "WHERE 1=2  ";
		for(int i = Integer.parseInt(this.thangBatDau);i<=Integer.parseInt(this.thangKeThuc);i++){
			query += " OR ISNULL(THANG"+i+".PHATSINH,0) <> 0 ";
		}
		query += "\n ORDER BY LOAI,SOHIEUTAIKHOAN,DVTHTEN,NCPID " ;
		
		System.out.println("Query BC chi phí tổng hợp: \n" + query + "\n------------------------------------------------------------");
		return query;
	}
	public String getQueryBCChiTietChiPhi(){
		String query = "";
		//query63x lấy dữ liệu của các tài khoản 63x
		String queryTK63x ="";
		//query64x lấy dữ liệu của các tài khoản 64x
		String queryTK64x = "";
		//nếu chọn phòng ban All thì mới lấy dữ liệu TK : 63x
		if(this.phongBanId.trim().length() == 1 && this.phongBanId.trim().equals("1") && this.nhomChiPhiId.trim().length() == 0){
			queryTK63x = "  SELECT CASE WHEN TK.SOHIEUTAIKHOAN LIKE '631%' THEN 1 ELSE 2 END AS LOAI, \n " +
					"   TK.PK_SEQ AS TKID,TK.SOHIEUTAIKHOAN,ISNULL(DVTH.TEN,'') AS DVTHTEN, \n " + 
					"   ISNULL(CONVERT(VARCHAR,NCP.PK_SEQ),'') AS NCPID,ISNULL(NCP.TEN,'') AS NCPTEN,  \n " + 
					"   ISNULL(NKM.MA,'') + '-' + ISNULL(NKM.TEN,'') AS NKMTEN, \n " + 
					"   PS.NGAYGHINHAN AS NGAYCHUNGTU,PS.LOAICHUNGTU,ISNULL(MACHUNGTU,SOCHUNGTU) AS SOCHUNGTU,  \n " +
					"   PS.NO AS SOTIENTANG,PS.CO AS SOTIENGIAM,TKDU.SOHIEUTAIKHOAN AS TAIKHOANDOIUNG \n " +
					"   FROM ERP_PHATSINHKETOAN PS  \n " + 
					"   INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ =PS.TAIKHOAN_FK  \n " + 
					"   INNER JOIN ERP_TAIKHOANKT TKDU ON TKDU.PK_SEQ = PS.TAIKHOANDOIUNG_FK \n " +
					"   LEFT JOIN ERP_NHOMCHIPHI NCP ON PS.KHOANMUCCHIPHI_FK = NCP.PK_SEQ \n " +
					"   LEFT JOIN ERP_NHOMKHOANMUC_KHOANMUCCHIPHI NKM_NCP ON NKM_NCP.KHOANMUCCHIPHI_FK = NCP.PK_SEQ \n " + 
					"   LEFT JOIN ERP_NHOMKHOANMUC NKM ON NKM.PK_SEQ = NKM_NCP.NHOMKHOANMUC_FK \n " + 
					"   LEFT JOIN ERP_DONVITHUCHIEN DVTH ON NCP.DONVITHUCHIEN_FK =DVTH.PK_SEQ  \n " + 
					"   WHERE MONTH(PS.NGAYGHINHAN) >="+Integer.parseInt(this.thangBatDau)+
					"   AND MONTH(PS.NGAYGHINHAN) <="+Integer.parseInt(this.thangKeThuc)+
					"   AND YEAR(PS.NGAYGHINHAN) = "+this.nam+" \n " +
					" AND (PS.LOAICHUNGTU NOT IN (N'Duyệt hóa đơn NCC',N'Xuất tiêu hao gia công',N'Xuất tiêu hao lệnh sản xuất',N'Kết chuyển CPSX') OR \n" +
					"(PS.LOAICHUNGTU IN (N'Duyệt hóa đơn NCC',N'Xuất tiêu hao gia công',N'Xuất tiêu hao lệnh sản xuất') \n "+
					" AND TAIKHOANDOIUNG_FK NOT IN(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '154%') )) \n" + 
					" AND (TK.SOHIEUTAIKHOAN LIKE '63%') AND TK.CONGTY_FK = "+this.congTyId+" \n " ;
			if(this.nhomChiPhiId.length() >0)
				queryTK63x += " AND NKM.PK_SEQ = " +this.nhomChiPhiId + " \n" ;
			queryTK63x +="   AND (PS.NO >0 OR PS.CO >0) \n " ;
			queryTK63x +=" UNION ALL \n " ;
			
			//dms
			queryTK63x += "SELECT * FROM OPENQUERY(["+Utility.prefixLinkDMS+"],'" +	
					"  SELECT CASE WHEN TK.SOHIEUTAIKHOAN LIKE ''631%'' THEN 1 ELSE 2 END AS LOAI, \n " +
					"   TK.PK_SEQ AS TKID,TK.SOHIEUTAIKHOAN,ISNULL(DVTH.TEN,'''') AS DVTHTEN, \n " + 
					"   ISNULL(CONVERT(VARCHAR,NCP.PK_SEQ),'''') AS NCPID,ISNULL(NCP.TEN,'''') AS NCPTEN,  \n " + 
					"   ISNULL(NKM.MA,'''') + ''-'' + ISNULL(NKM.TEN,'''') AS NKMTEN, \n " + 
					"   PS.NGAYGHINHAN AS NGAYCHUNGTU,PS.LOAICHUNGTU,ISNULL(MACHUNGTU,SOCHUNGTU) AS SOCHUNGTU,  \n " +
					"   PS.NO AS SOTIENTANG,PS.CO AS SOTIENGIAM,TKDU.SOHIEUTAIKHOAN AS TAIKHOANDOIUNG \n " +
					"   FROM ERP_PHATSINHKETOAN PS  \n " + 
					"   INNER JOIN "+Utility.prefixReportDMS+"ERP_TAIKHOANKT TK ON TK.PK_SEQ =PS.TAIKHOAN_FK  \n " + 
					"   INNER JOIN "+Utility.prefixReportDMS+"ERP_TAIKHOANKT TKDU ON TKDU.PK_SEQ = PS.TAIKHOANDOIUNG_FK \n " +
					"   LEFT JOIN "+Utility.prefixReportDMS+"ERP_NHOMCHIPHI NCP ON PS.KHOANMUCCHIPHI_FK = NCP.PK_SEQ \n " +
					"   LEFT JOIN "+Utility.prefixReportDMS+"ERP_NHOMKHOANMUC_KHOANMUCCHIPHI NKM_NCP ON NKM_NCP.KHOANMUCCHIPHI_FK = NCP.PK_SEQ \n " + 
					"   LEFT JOIN "+Utility.prefixReportDMS+"ERP_NHOMKHOANMUC NKM ON NKM.PK_SEQ = NKM_NCP.NHOMKHOANMUC_FK \n " + 
					"   LEFT JOIN "+Utility.prefixReportDMS+"ERP_DONVITHUCHIEN DVTH ON NCP.DONVITHUCHIEN_FK =DVTH.PK_SEQ  \n " + 
					"   WHERE MONTH(PS.NGAYGHINHAN) >="+Integer.parseInt(this.thangBatDau)+
					"   AND MONTH(PS.NGAYGHINHAN) <="+Integer.parseInt(this.thangKeThuc)+
					"   AND YEAR(PS.NGAYGHINHAN) = "+this.nam+" \n " +
					" AND (PS.LOAICHUNGTU NOT IN (N''Duyệt hóa đơn NCC'',N''Xuất tiêu hao gia công'',N''Xuất tiêu hao lệnh sản xuất'',N''Kết chuyển CPSX'') OR \n" +
					"(PS.LOAICHUNGTU IN (N''Duyệt hóa đơn NCC'',N''Xuất tiêu hao gia công'',N''Xuất tiêu hao lệnh sản xuất'') \n "+
					" AND TAIKHOANDOIUNG_FK NOT IN(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE ''154%'') )) \n" + 
					" AND (TK.SOHIEUTAIKHOAN LIKE ''63%'') AND TK.CONGTY_FK = "+this.congTyId+" \n " ;
			if(this.nhomChiPhiId.length() >0)
				queryTK63x += " AND NKM.PK_SEQ = " +this.nhomChiPhiId + " \n" ;
			queryTK63x +="   AND (PS.NO >0 OR PS.CO >0) ') \n " ;
			queryTK63x +=" UNION ALL \n " ;
			
		} 
		queryTK64x = " SELECT CASE WHEN TK.SOHIEUTAIKHOAN LIKE '621%' THEN 3 WHEN TK.SOHIEUTAIKHOAN LIKE '622%' THEN 4 WHEN TK.SOHIEUTAIKHOAN LIKE '627%' THEN 5\n" +
				"	 WHEN TK.SOHIEUTAIKHOAN LIKE '641%' THEN 6 ELSE 7 END AS LOAI, \n" +
				"   TK.PK_SEQ AS TKID,TK.SOHIEUTAIKHOAN,ISNULL(DVTH.TEN,'') AS DVTHTEN, \n " + 
				"   ISNULL(CONVERT(VARCHAR,NCP.PK_SEQ),'') AS NCPID,ISNULL(NCP.TEN,'') AS NCPTEN,  \n " + 
				"   ISNULL(NKM.MA,'') + '-' + ISNULL(NKM.TEN,'') AS NKMTEN, \n " + 
				"   PS.NGAYGHINHAN AS NGAYCHUNGTU,PS.LOAICHUNGTU,ISNULL(MACHUNGTU,SOCHUNGTU) AS SOCHUNGTU,  \n " +
				"   PS.NO AS SOTIENTANG,PS.CO AS SOTIENGIAM,TKDU.SOHIEUTAIKHOAN AS TAIKHOANDOIUNG \n " +
				"   FROM ERP_PHATSINHKETOAN PS  \n " + 
				"   INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ =PS.TAIKHOAN_FK  \n " + 
				"   INNER JOIN ERP_TAIKHOANKT TKDU ON TKDU.PK_SEQ = PS.TAIKHOANDOIUNG_FK \n " +
				"   LEFT JOIN ERP_NHOMCHIPHI NCP ON PS.KHOANMUCCHIPHI_FK = NCP.PK_SEQ \n " +
				"   LEFT JOIN ERP_NHOMKHOANMUC_KHOANMUCCHIPHI NKM_NCP ON NKM_NCP.KHOANMUCCHIPHI_FK = NCP.PK_SEQ \n " + 
				"   LEFT JOIN ERP_NHOMKHOANMUC NKM ON NKM.PK_SEQ = NKM_NCP.NHOMKHOANMUC_FK \n " + 
				"   LEFT JOIN ERP_DONVITHUCHIEN DVTH ON NCP.DONVITHUCHIEN_FK =DVTH.PK_SEQ  \n " + 
				"   WHERE MONTH(PS.NGAYGHINHAN) >="+Integer.parseInt(this.thangBatDau)+
				"   AND MONTH(PS.NGAYGHINHAN) <="+Integer.parseInt(this.thangKeThuc)+
				"   AND YEAR(PS.NGAYGHINHAN) = "+this.nam+" \n " + 
				" AND (PS.LOAICHUNGTU NOT IN (N'Duyệt hóa đơn NCC',N'Xuất tiêu hao gia công',N'Xuất tiêu hao lệnh sản xuất',N'Kết chuyển CPSX') OR \n" +
				"(PS.LOAICHUNGTU IN (N'Duyệt hóa đơn NCC',N'Xuất tiêu hao gia công',N'Xuất tiêu hao lệnh sản xuất') \n "+
				" AND TAIKHOANDOIUNG_FK NOT IN(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '154%') )) \n" + 
				"   AND (TK.SOHIEUTAIKHOAN LIKE '64%' OR TK.SOHIEUTAIKHOAN LIKE '621%' OR TK.SOHIEUTAIKHOAN LIKE '622%' OR TK.SOHIEUTAIKHOAN LIKE '627%') AND TK.CONGTY_FK = "+this.congTyId+" \n " ;
		if(this.nhomChiPhiId.length() >0)
			queryTK64x += " AND NKM.PK_SEQ = " +this.nhomChiPhiId + " \n" ;
		if(this.phongBanId.trim().length() >1)
			queryTK64x +=" AND PS.KHOANMUCCHIPHI_FK IN (SELECT CONVERT(VARCHAR,PK_SEQ) FROM ERP_NHOMCHIPHI WHERE DONVITHUCHIEN_FK IN ("+this.phongBanId+")) \n " ; 
		queryTK64x +="   AND (PS.NO <>0 OR PS.CO <>0) \n " ;
		
		queryTK64x +=" UNION ALL \n " ;
		//DMS
		queryTK64x += "SELECT * FROM OPENQUERY(["+Utility.prefixLinkDMS+"],'" +
				" SELECT CASE WHEN TK.SOHIEUTAIKHOAN LIKE ''621%'' THEN 3 WHEN TK.SOHIEUTAIKHOAN LIKE ''622%'' THEN 4 WHEN TK.SOHIEUTAIKHOAN LIKE ''627%'' THEN 5\n" +
				"	 WHEN TK.SOHIEUTAIKHOAN LIKE ''641%'' THEN 6 ELSE 7 END AS LOAI, \n" +
				"   TK.PK_SEQ AS TKID,TK.SOHIEUTAIKHOAN,ISNULL(DVTH.TEN,'''') AS DVTHTEN, \n " + 
				"   ISNULL(CONVERT(VARCHAR,NCP.PK_SEQ),'''') AS NCPID,ISNULL(NCP.TEN,'''') AS NCPTEN,  \n " + 
				"   ISNULL(NKM.MA,'''') + ''-'' + ISNULL(NKM.TEN,'''') AS NKMTEN, \n " + 
				"   PS.NGAYGHINHAN AS NGAYCHUNGTU,PS.LOAICHUNGTU,ISNULL(MACHUNGTU,SOCHUNGTU) AS SOCHUNGTU,  \n " +
				"   PS.NO AS SOTIENTANG,PS.CO AS SOTIENGIAM,TKDU.SOHIEUTAIKHOAN AS TAIKHOANDOIUNG \n " +
				"   FROM "+Utility.prefixReportDMS+"ERP_PHATSINHKETOAN PS  \n " + 
				"   INNER JOIN "+Utility.prefixReportDMS+"ERP_TAIKHOANKT TK ON TK.PK_SEQ =PS.TAIKHOAN_FK  \n " + 
				"   INNER JOIN "+Utility.prefixReportDMS+"ERP_TAIKHOANKT TKDU ON TKDU.PK_SEQ = PS.TAIKHOANDOIUNG_FK \n " +
				"   LEFT JOIN "+Utility.prefixReportDMS+"ERP_NHOMCHIPHI NCP ON PS.KHOANMUCCHIPHI_FK = NCP.PK_SEQ \n " +
				"   LEFT JOIN "+Utility.prefixReportDMS+"ERP_NHOMKHOANMUC_KHOANMUCCHIPHI NKM_NCP ON NKM_NCP.KHOANMUCCHIPHI_FK = NCP.PK_SEQ \n " + 
				"   LEFT JOIN "+Utility.prefixReportDMS+"ERP_NHOMKHOANMUC NKM ON NKM.PK_SEQ = NKM_NCP.NHOMKHOANMUC_FK \n " + 
				"   LEFT JOIN "+Utility.prefixReportDMS+"ERP_DONVITHUCHIEN DVTH ON NCP.DONVITHUCHIEN_FK =DVTH.PK_SEQ  \n " + 
				"   WHERE MONTH(PS.NGAYGHINHAN) >="+Integer.parseInt(this.thangBatDau)+
				"   AND MONTH(PS.NGAYGHINHAN) <="+Integer.parseInt(this.thangKeThuc)+
				"   AND YEAR(PS.NGAYGHINHAN) = "+this.nam+" \n " + 
				" AND (PS.LOAICHUNGTU NOT IN (N''Duyệt hóa đơn NCC'',N''Xuất tiêu hao gia công'',N''Xuất tiêu hao lệnh sản xuất'',N''Kết chuyển CPSX'') OR \n" +
				"(PS.LOAICHUNGTU IN (N''Duyệt hóa đơn NCC'',N''Xuất tiêu hao gia công'',N''Xuất tiêu hao lệnh sản xuất'') \n "+
				" AND TAIKHOANDOIUNG_FK NOT IN(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE ''154%'') )) \n" + 
				"   AND (TK.SOHIEUTAIKHOAN LIKE ''64%'' OR TK.SOHIEUTAIKHOAN LIKE ''621%'' OR TK.SOHIEUTAIKHOAN LIKE ''622%'' OR TK.SOHIEUTAIKHOAN LIKE ''627%'') AND TK.CONGTY_FK = "+this.congTyId+" \n " ;
		if(this.nhomChiPhiId.length() >0)
			queryTK64x += " AND NKM.PK_SEQ = " +this.nhomChiPhiId + " \n" ;
		if(this.phongBanId.trim().length() >1)
			queryTK64x +=" AND PS.KHOANMUCCHIPHI_FK IN (SELECT CONVERT(VARCHAR,PK_SEQ) FROM ERP_NHOMCHIPHI WHERE DONVITHUCHIEN_FK IN ("+this.phongBanId+")) \n " ; 
		queryTK64x +="   AND (PS.NO <>0 OR PS.CO <>0) ') \n " ;
		
		query = queryTK63x + queryTK64x +
				"\n ORDER BY LOAI,SOHIEUTAIKHOAN,DVTHTEN,NCPID,NGAYCHUNGTU,"
				+ "SOCHUNGTU,SOTIENTANG,SOTIENGIAM,TAIKHOANDOIUNG  " ;
		System.out.println("Query BC chi phí chi tiết: \n" + query + "\n------------------------------------------------------------");
		return query;
	}
}