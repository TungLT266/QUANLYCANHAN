package geso.traphaco.erp.beans.baocaocongno.imp;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.DKLocBaoCaoKeToan;
import geso.traphaco.center.util.PhatSinhKeToanItem;
import geso.traphaco.erp.beans.baocaocongno.IErp_BaoCaoCongNoChiTietList;
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

public class Erp_BaoCaoCongNoChiTietList  extends Phan_Trang implements IErp_BaoCaoCongNoChiTietList
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String congTyId;
	private String msg;
	
	private DKLocBaoCaoKeToan dieuKienLoc;
	private List<String> colunmNameList;
	private List<PhatSinhKeToanItem> viewList;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	private dbutils db;
	ResultSet bcRS;
	
	public Erp_BaoCaoCongNoChiTietList()
	{
		this.congTyId = "";
		this.msg= ""; 
		
		this.dieuKienLoc = new DKLocBaoCaoKeToan();
		this.colunmNameList = new ArrayList<String>();
		this.viewList = new ArrayList<PhatSinhKeToanItem>();
		
		this.currentPages = 1;
		this.num = 1;
		this.db = new dbutils();
	}
	
	public void init()
	{
		this.dieuKienLoc.init(db);
		
//		String [] param = {"x...."};
		//Bắt buộc nhập chọn tài khoản công nợ
		if ( (this.dieuKienLoc.getSoHieuTaiKhoanNo().trim().length() > 0 || this.dieuKienLoc.getNhomTaiKhoanId().trim().length() >0 )
				 &&	( (this.dieuKienLoc.getLoaiDoiTuongNoId().trim().length() > 0 
					&& this.dieuKienLoc.getDoiTuongNoId().trim().length() > 0) || this.dieuKienLoc.getNhomDoiTuongId().trim().length() > 0 ) )
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
			
		
			
			query =
				"EXEC [REPORT_SOCHITIETCONGNO_NHOMDT] '" + taiKhoanId + "'" +
				", '" + this.dieuKienLoc.getTuNgay() + "'" +
				", '" + this.dieuKienLoc.getDenNgay()  + "'" +
				", " + this.dieuKienLoc.getCongTyId() +
				", '" + this.dieuKienLoc.getDoiTuongNoId() + "'" +
				", N'" + this.dieuKienLoc.getLoaiDoiTuongNoId() + "'"+
				", '"+this.dieuKienLoc.getNhomTaiKhoanId()+"'"+
				", '" + this.dieuKienLoc.getLoaiNhom() + "' "+
				", '" + this.dieuKienLoc.getNhomDoiTuongId() + "' ";
			
		/*	query = 
				"SELECT ISNULL(PSKT.MACHUNGTU, '') AS N'Số hóa đơn',PSKT.NGAYGHINHAN AS N'Ngày ghi nhận',PSKT.NGAYCHUNGTU AS N'Ngày chứng từ', \n " +
				"PSKT.DIENGIAI AS N'Diễn giải chứng từ', isNull(PSKT.SOHIEUTAIKHOAN, '') AS N'Tài khoản đối ứng', \n " +
				"PSKT.PS_NO AS N'Phát sinh nợ', PSKT.PS_CO AS N'Phát sinh có', \n " +
				"PSKT.LOAICHUNGTU AS N'Loại chứng từ',PSKT.SOCHUNGTU AS N'Mã chứng từ' \n " + 

				"FROM( \n " +
					"  	SELECT '' AS NGAYCHUNGTU,'' AS NGAYGHINHAN, N'Số dư đầu kỳ' AS LOAICHUNGTU,'' AS SOCHUNGTU, '' SOHIEUTAIKHOAN, \n " +
					"	CASE WHEN SUM(PS_NO-PS_CO)>0  THEN SUM(PS_NO-PS_CO) ELSE 0 END  AS PS_NO, \n " +
					"	CASE WHEN SUM(PS_CO-PS_NO)>0 THEN SUM(PS_CO-PS_NO) ELSE 0 END  AS PS_CO,  \n " +	
					"	'' AS DOITUONG, \n " + 
					"	'' MADOITUONG, '' DIENGIAI, '' MACHUNGTU  \n " +
					"	FROM( \n " +
					"		SELECT PS.NGAYCHUNGTU, PS.NGAYGHINHAN, PS.LOAICHUNGTU, PS.SOCHUNGTU, \n " + 
					"		TK1.SOHIEUTAIKHOAN,  \n " +
					"		SUM(ROUND(ISNULL(NO,0),0)) AS PS_NO, SUM(ROUND(ISNULL(CO,0),0)) AS PS_CO, \n " + 
					"		CASE WHEN (PS.DOITUONG = N'Khách hàng' AND ISNPP = 1) THEN N'Chi nhánh/Đối tác' ELSE DOITUONG END AS DOITUONG, \n " + 
					"		PS.MADOITUONG, PS.DIENGIAI, PS.MACHUNGTU \n " +
					"		FROM  dbo.ERP_PHATSINHKETOAN PS  \n " +
					"		INNER JOIN ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK AND TK1.NPP_FK = 1 \n " + 
					"		WHERE PS.NGAYGHINHAN < '" + this.dieuKienLoc.getTuNgay() + "'  \n " +
					"		AND PS.TAIKHOAN_FK = '" + taiKhoanId + "' AND PS.MADOITUONG = '" + this.dieuKienLoc.getDoiTuongNoId() + "'  \n " +
					"		AND PS.DOITUONG = N'"+dieuKienLoc.getLoaiDoiTuongNoId()+"' \n " +
					"		GROUP BY PS.MACHUNGTU ,PS.NGAYGHINHAN ,PS.NGAYCHUNGTU ,PS.DIENGIAI, TK1.SOHIEUTAIKHOAN,   \n " +
					"		PS.LOAICHUNGTU, PS.SOCHUNGTU, PS.DOITUONG, PS.ISNPP, PS.MADOITUONG  \n " +
							 
					"		UNION ALL \n " +
					"		SELECT PS.NGAYCHUNGTU, PS.NGAYGHINHAN, PS.LOAICHUNGTU, PS.SOCHUNGTU, \n " + 
					"		TK1.SOHIEUTAIKHOAN,  \n " +
					"		SUM(ROUND(ISNULL(NO,0),0)) AS PS_NO, SUM(ROUND(ISNULL(CO,0),0)) AS PS_CO, \n " + 
					"		CASE WHEN (PS.DOITUONG = N'Khách hàng' AND ISNPP = 1) THEN N'Chi nhánh/??i tác' ELSE DOITUONG END AS DOITUONG, \n " + 
					"		PS.MADOITUONG, PS.DIENGIAI, PS.MACHUNGTU \n " +
					"		FROM  LINK_DMS.DataCenter.dbo.ERP_PHATSINHKETOAN PS  \n " +
					"		INNER JOIN ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK AND TK1.NPP_FK = 1 \n " + 
					"		WHERE PS.NGAYGHINHAN < '" + this.dieuKienLoc.getTuNgay() + "'  \n " +
					"		AND PS.TAIKHOAN_FK = '" + taiKhoanId + "' AND PS.MADOITUONG = '" + this.dieuKienLoc.getDoiTuongNoId() + "'  \n " +
					"		AND PS.DOITUONG = N'"+dieuKienLoc.getLoaiDoiTuongNoId()+"' \n " +
					"		GROUP BY PS.MACHUNGTU ,PS.NGAYGHINHAN ,PS.NGAYCHUNGTU ,PS.DIENGIAI, TK1.SOHIEUTAIKHOAN,   \n " +
					"		PS.LOAICHUNGTU, PS.SOCHUNGTU, PS.DOITUONG, PS.ISNPP, PS.MADOITUONG  \n " +
					"	 )DAUKY \n " + 
					
					"UNION ALL \n " +
					"SELECT PS.NGAYCHUNGTU, PS.NGAYGHINHAN, PS.LOAICHUNGTU, CONVERT(VARCHAR, PS.SOCHUNGTU) AS SOCHUNGTU, \n " +
					"TK1.SOHIEUTAIKHOAN, \n " +
					"SUM(ROUND(ISNULL(NO,0),0)) AS PS_NO, SUM(ROUND(ISNULL(CO,0),0)) AS PS_CO, \n " +
					"CASE WHEN (PS.DOITUONG = N'Khách hàng' AND ISNPP = 1) THEN N'Chi nhánh/Đối tác' ELSE DOITUONG END AS DOITUONG, \n " +
					"PS.MADOITUONG, PS.DIENGIAI, PS.MACHUNGTU \n " +
					
					"FROM  dbo.ERP_PHATSINHKETOAN PS \n " +
					"INNER JOIN ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK AND TK1.NPP_FK = 1 \n " +	
					"WHERE PS.NGAYGHINHAN >= '" + this.dieuKienLoc.getTuNgay() + "' AND PS.NGAYGHINHAN <= '" + this.dieuKienLoc.getDenNgay() + "' \n " +
					"AND PS.TAIKHOAN_FK = '" + taiKhoanId + "' AND PS.MADOITUONG = '" + this.dieuKienLoc.getDoiTuongNoId() + "'  \n " + 
					" AND PS.DOITUONG = N'"+dieuKienLoc.getLoaiDoiTuongNoId()+"' \n " +
					"GROUP BY PS.MACHUNGTU ,PS.NGAYGHINHAN ,PS.NGAYCHUNGTU ,PS.DIENGIAI, TK1.SOHIEUTAIKHOAN,  \n " +
					"PS.LOAICHUNGTU, PS.SOCHUNGTU, PS.DOITUONG, PS.ISNPP, PS.MADOITUONG \n " +
					
					"UNION ALL \n " +
					"SELECT PS.NGAYCHUNGTU, PS.NGAYGHINHAN, PS.LOAICHUNGTU, CONVERT(VARCHAR, PS.SOCHUNGTU) AS SOCHUNGTU,  \n " + 
					"TK1.SOHIEUTAIKHOAN, \n " +
					"SUM(ROUND(ISNULL(NO,0),0)) AS PS_NO, SUM(ROUND(ISNULL(CO,0),0)) AS PS_CO, \n " +
					"CASE WHEN (PS.DOITUONG = N'Khách hàng' AND ISNPP = 1) THEN N'Chi nhánh/Đối tác' ELSE DOITUONG END AS DOITUONG, \n " +
					"PS.MADOITUONG, PS.DIENGIAI, PS.MACHUNGTU \n " +
					"FROM  LINK_DMS.DataCenter.dbo.ERP_PHATSINHKETOAN PS \n " +
					"INNER JOIN ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK AND TK1.NPP_FK = 1 \n " +
					"WHERE PS.NGAYGHINHAN >= '" + this.dieuKienLoc.getTuNgay() + "' AND PS.NGAYGHINHAN <= '" + this.dieuKienLoc.getDenNgay() + "' \n " +
					"AND PS.TAIKHOAN_FK = '" + taiKhoanId + "' AND PS.MADOITUONG = '" + this.dieuKienLoc.getDoiTuongNoId() + "'  \n " + 
					"AND PS.DOITUONG = N'"+dieuKienLoc.getLoaiDoiTuongNoId()+"' \n " +
					"GROUP BY PS.MACHUNGTU ,PS.NGAYGHINHAN ,PS.NGAYCHUNGTU ,PS.DIENGIAI, TK1.SOHIEUTAIKHOAN,  \n " +
					"PS.LOAICHUNGTU, PS.SOCHUNGTU, PS.DOITUONG, PS.ISNPP, PS.MADOITUONG \n " +
					
					"UNION ALL " +
					"  	SELECT '' AS NGAYCHUNGTU,'' AS NGAYGHINHAN, N'Số dư cuối kỳ' AS LOAICHUNGTU,'' AS SOCHUNGTU, '' SOHIEUTAIKHOAN, \n " +
					"	CASE WHEN SUM(PS_NO-PS_CO)>0  THEN SUM(PS_NO-PS_CO) ELSE 0 END  AS PS_NO, \n " +
					"	CASE WHEN SUM(PS_CO-PS_NO)>0 THEN SUM(PS_CO-PS_NO) ELSE 0 END  AS PS_CO,  \n " +	

					"	'' DOITUONG, \n " + 
					"	'' MADOITUONG, '' DIENGIAI, '' MACHUNGTU  \n " +
					"	FROM( \n " +
					"		SELECT PS.NGAYCHUNGTU, PS.NGAYGHINHAN, PS.LOAICHUNGTU, PS.SOCHUNGTU, \n " + 
					"		TK1.SOHIEUTAIKHOAN,  \n " +
					"		SUM(ROUND(ISNULL(NO,0),0)) AS PS_NO, SUM(ROUND(ISNULL(CO,0),0)) AS PS_CO, \n " + 
					"		CASE WHEN (PS.DOITUONG = N'Khách hàng' AND ISNPP = 1) THEN N'Chi nhánh/Đối tác' ELSE DOITUONG END AS DOITUONG, \n " + 
					"		PS.MADOITUONG, PS.DIENGIAI, PS.MACHUNGTU\n " +
					"		FROM  dbo.ERP_PHATSINHKETOAN PS  \n " +
					"		INNER JOIN ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK AND TK1.NPP_FK = 1 \n " + 
					"		WHERE PS.NGAYGHINHAN <= '" + this.dieuKienLoc.getDenNgay() + "'  \n " +
					"		AND PS.TAIKHOAN_FK = '" + taiKhoanId + "' AND PS.MADOITUONG = '" + this.dieuKienLoc.getDoiTuongNoId() + "'  \n " +
					"		AND PS.DOITUONG = N'"+dieuKienLoc.getLoaiDoiTuongNoId()+"' \n " +
					"		GROUP BY PS.MACHUNGTU ,PS.NGAYGHINHAN ,PS.NGAYCHUNGTU ,PS.DIENGIAI, TK1.SOHIEUTAIKHOAN,   \n " +
					"		PS.LOAICHUNGTU, PS.SOCHUNGTU, PS.DOITUONG, PS.ISNPP, PS.MADOITUONG  \n " +
							 
					"		UNION ALL \n " +
					"		SELECT PS.NGAYCHUNGTU, PS.NGAYGHINHAN, PS.LOAICHUNGTU, PS.SOCHUNGTU, \n " + 
					"		TK1.SOHIEUTAIKHOAN,  \n " +
					"		SUM(ROUND(ISNULL(NO,0),0)) AS PS_NO, SUM(ROUND(ISNULL(CO,0),0)) AS PS_CO, \n " + 
					"		CASE WHEN (PS.DOITUONG = N'Khách hàng' AND ISNPP = 1) THEN N'Chi nhánh/??i tác' ELSE DOITUONG END AS DOITUONG, \n " + 
					"		PS.MADOITUONG, PS.DIENGIAI, PS.MACHUNGTU \n " +
					"		FROM  LINK_DMS.DataCenter.dbo.ERP_PHATSINHKETOAN PS  \n " +
					"		INNER JOIN ERP_TAIKHOANKT TK1 ON TK1.PK_SEQ = PS.TAIKHOANDOIUNG_FK AND TK1.NPP_FK = 1 \n " + 
					"		WHERE PS.NGAYGHINHAN <= '" + this.dieuKienLoc.getDenNgay() + "'  \n " +
					"		AND PS.TAIKHOAN_FK = '" + taiKhoanId + "' AND PS.MADOITUONG = '" + this.dieuKienLoc.getDoiTuongNoId() + "'  \n " +
					"		AND PS.DOITUONG = N'"+dieuKienLoc.getLoaiDoiTuongNoId()+"' \n " +
					"		GROUP BY PS.MACHUNGTU ,PS.NGAYGHINHAN ,PS.NGAYCHUNGTU ,PS.DIENGIAI, TK1.SOHIEUTAIKHOAN,   \n " +
					"		PS.LOAICHUNGTU, PS.SOCHUNGTU, PS.DOITUONG, PS.ISNPP, PS.MADOITUONG  \n " +
					"	 )CUOIKY  \n " + 
					
				")PSKT ";*/
			
			
			System.out.println("query init: \n" + query + "\n------------------------------------------------------------");
			
			
	//			rs = this.db.getRsByPro("MyPro...", param);
	//			rs = createSplittingData(50, 10, "NGAYTAO desc, NGAYSUA desc, PK_SEQ desc", query);

				this.bcRS = this.db.get(query);
/*				ResultSetMetaData rsmd = bcRS.getMetaData();
				int rowNumber = rsmd.getColumnCount();
				for (int i = 1; i <= rowNumber; i++)
					this.colunmNameList.add(rsmd.getColumnLabel(i));
*/				
/*				if (rs != null)
				{
					int stt = 1;
					while (rs.next())
					{
						//N'Số chứng từ',a.NGAYGHINHAN N'Ngày ghi nhận',a.NGAYCHUNGTU N'Ngày chứng từ',a.DIENGIAI N'Diễn giải'
						//, b.sohieutaikhoan as N'Tài khoản đối ứng', a.PS_NO N'Phát sinh nợ', a.PS_CO N'Phát sinh có'
						String soChungTu = rs.getString(1);
						String ngayGhiNhan = rs.getString(2);
						String ngayChungTu = rs.getString(3);
						String dienGiai = rs.getString(4);
						String taiKhoanDoiUng = rs.getString(5);
						double no = rs.getDouble(6);
						double co = rs.getDouble(7);
						String loaiChungTu = rs.getString(8);
						String maChungTu = rs.getString(9);
						
						PhatSinhKeToanItem item = new PhatSinhKeToanItem();
						item.setStt(stt);
						item.setSoChungTu(soChungTu);
						item.setNgayGhiNhan(ngayGhiNhan);
						item.setNgayChungTu(ngayChungTu);
						item.setDienGiai(dienGiai);
						item.setTaiKhoanDoiUng(taiKhoanDoiUng);
						item.setNo(no);
						item.setCo(co);
						item.setLoaiChungTu(loaiChungTu);
						item.setMaChungTu(maChungTu);
						
						this.viewList.add(item);
						stt++;
					}
					rs.close();
				}*/
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean exportExcel(ServletOutputStream out, String fileName)
	{
		try{
			//Bắt buộc nhập chọn tài khoản công nợ
			if ( (this.dieuKienLoc.getSoHieuTaiKhoanNo().trim().length() > 0 || this.dieuKienLoc.getNhomTaiKhoanId().trim().length() >0 )
					 &&	( (this.dieuKienLoc.getLoaiDoiTuongNoId().trim().length() > 0 
						&& this.dieuKienLoc.getDoiTuongNoId().trim().length() > 0) || this.dieuKienLoc.getNhomDoiTuongId().trim().length() > 0 ) )
			{
				//Lấy pk_seq của tài khoản thuộc chi nhánh
				String query = 	"select PK_SEQ from ERP_TAIKHOANKT " +
								"where SOHIEUTAIKHOAN = '" + this.dieuKienLoc.getSoHieuTaiKhoanNo() + "' " +
								"and TRANGTHAI = 1 and npp_fk = " + this.dieuKienLoc.getCongTyId();
				
				String query1="select TEN from dmdoituong where loai = N'" + this.dieuKienLoc.getLoaiDoiTuongNoId() +  "'and PK_SEQ= "+this.dieuKienLoc.getDoiTuongNoId()  ;

				
				System.out.println("Lấy pk_seq của tài khoản: " + query);
				System.out.println("lấy tên của đối tượng:"+query1);
				
				ResultSet rs = this.db.get(query);
				ResultSet rs1=this.db.get(query1);

				String taiKhoanId = "";
				String TEN="";

				if (rs != null)
				{
					if (rs.next())
					{
						taiKhoanId = rs.getString(1);
					}
					rs.close();
				}
				if(rs1 !=null)
				{
					if(rs1.next())
					{
						TEN=rs1.getString("TEN");
						
					}
					
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
//					"EXEC [REPORT_SOCHITIETCONGNO] '" + taiKhoanId + "'" +
//					", '" + this.dieuKienLoc.getTuNgay() + "'" +
//					", '" + this.dieuKienLoc.getDenNgay()  + "'" +
//					", " + this.dieuKienLoc.getCongTyId() +
//					", '" + this.dieuKienLoc.getDoiTuongNoId() + "'" +
//					", N'" + this.dieuKienLoc.getLoaiDoiTuongNoId() + "'"+
//					", '"+this.dieuKienLoc.getNhomTaiKhoanId()+"'"+
//					", '" + this.dieuKienLoc.getLoaiNhom() + "' "+
//					", '" + this.dieuKienLoc.getNhomDoiTuongId() + "' ";
				query =
						"EXEC [REPORT_SOCHITIETCONGNO_NHOMDT] '" + taiKhoanId + "'" +
						", '" + this.dieuKienLoc.getTuNgay() + "'" +
						", '" + this.dieuKienLoc.getDenNgay()  + "'" +
						", " + this.dieuKienLoc.getCongTyId() +
						", '" + this.dieuKienLoc.getDoiTuongNoId() + "'" +
						", N'" + this.dieuKienLoc.getLoaiDoiTuongNoId() + "'"+
						", '"+this.dieuKienLoc.getNhomTaiKhoanId()+"'"+
						", '" + this.dieuKienLoc.getLoaiNhom() + "' "+
						", '" + this.dieuKienLoc.getNhomDoiTuongId() + "' ";
				
				System.out.println("query init: \n" + query + "\n------------------------------------------------------------");
				rs = this.db.get(query);
				
			    Cell cell = cells.getCell("A4");
			    
			    cell = cells.getCell("A4");
			    cell.setValue("Từ ngày: " + this.dieuKienLoc.getTuNgay() + " - " + this.dieuKienLoc.getDenNgay());
			    
			    cell = cells.getCell("A5");
			    cell.setValue("Tài khoản: " + this.dieuKienLoc.getTenTaiKhoanNo(db));
			    
			    
			    cell = cells.getCell("A6");
			    cell.setValue(" Tên Đối tượng: " + TEN );
			    
				ResultSetMetaData rsmd = rs.getMetaData();
				int rowNumber = rsmd.getColumnCount();
				int countRow = 11;
							
				int stt = 0;
				while(rs.next())
				{
					stt++;
					for(int i = 1;i <=rowNumber ; i ++)
					{
						{
							cell = cells.getCell(countRow, i - 1);
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
				
				for(int i = 1; i <= rowNumber ; i++)
				{
					ReportAPI.CreateBorderSetting3(cells.getCell(countRow - 1,i - 1));
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
		try{
			if(this.bcRS != null) this.bcRS.close();
			if (this.db != null)
			{
				this.db.shutDown();
			}
		}catch(java.sql.SQLException e){}
	}

	public ResultSet getBcRS() {
		return this.bcRS;
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

	public void setColunmNameList(List<String> colunmNameList) {
		this.colunmNameList = colunmNameList;
	}

	public List<String> getColunmNameList() {
		return colunmNameList;
	}
	
	@Override
	public void getLoaiTaiKhoan() {
		if(this.getDieuKienLoc().getSoHieuTaiKhoanNo().trim().length() == 0){
			this.getDieuKienLoc().setLoaiNhom("");
			return;
		}
			
		String query = "SELECT TOP 1 ISNULL(DUNGCHONCC,0) DUNGCHONCC, ISNULL(DUNGCHOKHACHHANG, 0) DUNGCHOKHACHHANG , " +
				" ISNULL(DUNGCHONHANVIEN,0) DUNGCHONHANVIEN,  ISNULL(DUNGCHODOITUONGKHAC, 0) AS DUNGCHODOITUONGKHAC, SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '" + this.getDieuKienLoc().getSoHieuTaiKhoanNo() + "'";
		System.out.println("lay tai khoan " + query);
		ResultSet rs = db.get(query);
		try {
			
			rs.next();
			if(rs.getString("DUNGCHONCC").equals("1")){
				this.getDieuKienLoc().setLoaiNhom("2");
			}
			
			else if(rs.getString("DUNGCHOKHACHHANG").equals("1")){
				this.getDieuKienLoc().setLoaiNhom("0");
			}
			
			else if(rs.getString("DUNGCHONHANVIEN").equals("1")){
				this.getDieuKienLoc().setLoaiNhom("1");
			}
		
			else if(rs.getString("DUNGCHODOITUONGKHAC").equals("1")){
				this.getDieuKienLoc().setLoaiNhom("3");
			}
			else {
				this.getDieuKienLoc().setLoaiNhom("");
			}
			this.getDieuKienLoc().setNhomDoiTuongId("");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}