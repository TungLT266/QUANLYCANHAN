package geso.dms.center.util;

import geso.traphaco.erp.db.sql.dbutils;
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
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class ImportPOChiphi extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
   
    public ImportPOChiphi() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		doPost(request, response);
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
//		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/xlsm");
		response.setHeader("Content-Disposition", "attachment; filename=PO_GR_IV_01.2014.xlsm");
				
		try{
			ImportData(request, response);
		
		}catch (Exception e) 
        {
			System.out.println(e.toString());
		}

	}
	

	
	/**
	 * @return
	 * @throws Exception
	 */
	private boolean ImportData(HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		dbutils db = new dbutils();
		String nccId = "";
		String query = 	"SELECT NCC.PK_SEQ, TK.PK_SEQ AS TKID " +
					   	"FROM ERP_NHACUNGCAP NCC " +
						"INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = NCC.TAIKHOAN_FK " +
						"WHERE NCC.MA = 'NCCCHIPHI' " ;

		ResultSet rs = db.get(query);
		if(rs != null){
			try{
				rs.next();
				nccId = rs.getString("PK_SEQ");
				rs.close();
			}catch(java.sql.SQLException e){
				System.out.println(e.toString());
			}
			
		}
		OutputStream out = response.getOutputStream();
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\PO_GR_IV_01.2014.xlsm");
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet("PO_DATA");

		Cells cells = worksheet.getCells();
		
		String userId = "100307"; // User geso
		String cpId ;
		String ncpTen;
		String tongtienAVAT;
		Cell cell;
		String Id;
		try{
		db.getConnection().setAutoCommit(false);
		for (int row = 6; row < 28; row ++){
			
			cell = cells.getCell("R" + row);
			tongtienAVAT = cell.getStringValue();
			
			cell = cells.getCell("L" + row);
			ncpTen = cell.getStringValue().trim();
			System.out.println("-----------Khoan muc chi phi: " + ncpTen);
			String ngaymuahang = "2014-01-31";
			String nam = ngaymuahang.substring(0, 4);
			String thang = ngaymuahang.substring(5, 7);

			query =  	"SELECT DISTINCT NCP.PK_SEQ " +
						"FROM ERP_NHOMCHIPHI NCP " +
						"INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = NCP.TAIKHOAN_FK " +			 
						"WHERE NCP.TEN = '" + ncpTen + "'";
			rs = db.get(query);
			if(rs != null){
				while(rs.next()){
					cpId = rs.getString("PK_SEQ");

					query = "select isnull( max(sotutang_theonam), 1) as maxSTT, (select PREFIX from ERP_DONVITHUCHIEN where PK_SEQ = '100000' ) as PREFIX " +
					"from ERP_MUAHANG where SUBSTRING('" + ngaymuahang + "', 0, 5) = '" + nam + "' ";
					
					String soPO = "";
					int sotutang_theonam = 0;
					ResultSet rsPO = db.get(query);  //MẤY NỮA BỔ SUNG THÊM, QUA NĂM MỚI SỐ TỰ TĂNG RESET VỀ BẰNG 1
					if(rsPO.next())
					{
						sotutang_theonam = rsPO.getInt("maxSTT") + 1 ;
						String prefix = rsPO.getString("PREFIX");
						String namPO = ngaymuahang.substring(2, 4);
				
						if(sotutang_theonam < 10)
							soPO = prefix + "-" + "000" + Integer.toString(sotutang_theonam) + "/" + thang + "/" + namPO;
						else
						{
							if(sotutang_theonam < 100)
								soPO = prefix + "-" + "00" + Integer.toString(sotutang_theonam) + "/" + thang + "/" + namPO;
							else
							{
								if(sotutang_theonam < 1000)
									soPO = prefix + "-" + "0" + Integer.toString(sotutang_theonam) + "/" + thang + "/" + namPO;
								else
									soPO = prefix + "-" + Integer.toString(sotutang_theonam) + "/" + thang + "/" + namPO;
							}
						}
					}
					rsPO.close();
					System.out.println("---SO PO: " + soPO);
					 		
					query = "Insert into Erp_MuaHang(NgayMua, DonViThucHien_FK, NhaCungCap_FK, LoaiHangHoa_FK, LoaiSanPham_FK, " +
							"TongTienAVAT, VAT, TongTienBVAT, DungSai, TrangThai, NgayTao, NgaySua, NguoiTao, NguoiSua, NguonGocHH, TienTe_FK, " + 
							"GhiChu, TyGiaQuyDoi, type, congty_fk, quanlycongno, SOTHAMCHIEU, ETD, ETA,HTTT, SOPO, SOTUTANG_THEONAM, DIADIEMGIAOHANG) " +
							"VALUES( '2014-01-31', 100000, " + nccId + ", '2', null, " +
							"		" + tongtienAVAT.replaceAll(",", "") + ", 0, " + tongtienAVAT.replaceAll(",", "") + ", '0', '0', '2014-01-31', '2014-01-31', '" + userId + "', '" + userId + "', 'TN', '100000', " +
							"'', '1', '', '100005', '1', '', '', '', '', '"+ soPO + "', " + sotutang_theonam + ", '')";
					System.out.println(query);
					db.update(query);
					
					query = "SELECT SCOPE_IDENTITY() AS Id";
					ResultSet rs2 = db.get(query);
					
					if(rs2 != null){
						rs2.next();
						Id = rs2.getString("Id");
						rs2.close();
					}else{
						Id = "";						
					}
					System.out.println("Id: " + Id);
					
					// CAP NHAT ID VAO EXCEL
					cell = cells.getCell("A" + row);
					cell.setValue(Id);
					
					if(Id.length() > 0){
						cell = cells.getCell("M" + row);
						String diengiai = cell.getStringValue();
						
						cell = cells.getCell("O" + row);
						String dvt = cell.getStringValue();

						cell = cells.getCell("P" + row);
						String soluong = cell.getStringValue();

						cell = cells.getCell("Q" + row);
						String dongia = cell.getStringValue();

						query = "insert into ERP_MUAHANG_SP(muahang_fk, sanpham_fk, taisan_fk, ccdc_fk, chiphi_fk, diengiai, " + 
								"donvi, soluong, dongia, thanhtien, dongiaviet, thanhtienviet, ngaynhan, khott_fk, dungsai, " +
								"PhanTramThue, ThueNhapKhau, TENHD) " +
								"VALUES(" + Id + ", null, null, null, " + cpId + ", N'" + diengiai + "', " +
										"N'" + dvt + "', " + soluong.replaceAll(",", "") + ", " + dongia.replaceAll(",", "") + ", " + tongtienAVAT.replaceAll(",", "") + ", " + dongia + ", " + tongtienAVAT.replaceAll(",", "") + ", '2014-01-31', null, '0', " +
										"'0', '0', '')";
						System.out.println(query);
						db.update(query);
					
						// insert nguoi duyet PO 
						query = "SELECT	DUYETCHIPHI.CHUCDANH_FK, DUYETCHIPHI.QUYETDINH, DUYETCHIPHI.THUTU " +
								"FROM ERP_MUAHANG MUAHANG " +
								"INNER JOIN ERP_CHINHSACHDUYETCHIPHI DUYETCHIPHI ON DUYETCHIPHI.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK " +
								"INNER JOIN ERP_KHOANGCHIPHI KHOANGCHIPHI ON KHOANGCHIPHI.PK_SEQ = DUYETCHIPHI.KHOANGCHIPHI_FK " +
								"INNER JOIN ERP_CHUCDANH CHUCDANH ON CHUCDANH.PK_SEQ = DUYETCHIPHI.CHUCDANH_FK " +
								"WHERE KHOANGCHIPHI.SOTIENTU <= MUAHANG.TONGTIENBVAT AND (KHOANGCHIPHI.SOTIENDEN > MUAHANG.TONGTIENBVAT OR KHOANGCHIPHI.SOTIENDEN IS NULL) " +
								"AND MUAHANG.PK_SEQ = '" + Id +"' ORDER BY DUYETCHIPHI.THUTU" ;

						System.out.println("3.Duyet PO:" + query);
						
						rs2 = db.get(query);
						int thutu = 0;
						
						while (rs2.next())
						{							
							thutu = Integer.parseInt(rs2.getString("THUTU"));
							
							query = "INSERT INTO ERP_DUYETMUAHANG(MUAHANG_FK, CHUCDANH_FK, TRANGTHAI, QUYETDINH, THUTU) " +
									"VALUES('"+ Id +"', '" + rs2.getString("CHUCDANH_FK") + "', '1','" + rs2.getString("QUYETDINH")+ "','" + rs2.getString("THUTU") + "') ";
							
							System.out.println("4. Insert Duyet PO:" + query);
							if (!db.update(query))
							{
								db.getConnection().rollback();
								return false;
							}
						}
				
						if (rs2 != null) 
							rs2.close();
						
					}

				}
			}
			
		}
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);

		}catch(Exception e){
			e.printStackTrace();
		}
		workbook.save(out);
		fstream.close();
		return true;	
	}
	
	

}
