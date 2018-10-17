package geso.traphaco.erp.beans.yclaymaukiemnghiem.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.traphaco.erp.beans.yclaymaukiemnghiem.IYCLayMauKiemNghiem;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;

public class YCLayMauKiemNghiem extends Phan_Trang implements IYCLayMauKiemNghiem{

	private static final long serialVersionUID = 1L;
	String userId; 
	String msg;
	dbutils db;
	private String ngayChungTu;
	private String soChungTuNhapMau;
	private String mauKiemNghiemId;
	private String soHoaDon;
	private String kyHieuHoaDon;
	private String ngayHoaDon;
	private String khoBietTruId;
	private String dienGiai;
	private String phongBanId;
	private String soPhieuKiemDinh;
	private String phieuNhanHangId;
	private double tongTienVND;
	private double chenhLechNT;
	private String khoTonTruId;
	private String id;
	private String ctyId;
	private String nppId;
	private String loaiKiemDinh;
	private String hosokemtheo;
	private String khoanMucId;
	private String sanphamId;
	private String khoXuatMauId; 

	/**
	 * Select
	 */

	private ResultSet rsMauKiemNghiem;
	private ResultSet rsPhongBan;
	private ResultSet rsPhieuNhanHang;
	private ResultSet rsKhoBietTru;
	private ResultSet rsKhoTonTru;
	private ResultSet rsKhoXuatMau;
	private ResultSet rsPhieuKiemDinh;
	private ResultSet rsLoaiKiemDinh;
	private ResultSet rsKhoanMuc;
	private List<PhieuKiemDinh> dsPhieuKiemDinhs;
	private ResultSet rsSanPham;
	ResultSet rsYCLPKN;


	public YCLayMauKiemNghiem(){
		this.userId="";
		this.msg="";
		this.id = "";
		this.db = new dbutils();
		this.ngayChungTu = "";
		this.soChungTuNhapMau = "";
		this.mauKiemNghiemId = "";
		this.soHoaDon = "";
		this.kyHieuHoaDon = "";
		this.ngayHoaDon = "";
		this.khoBietTruId = "";
		this.dienGiai = "";
		this.phongBanId = "";
		this.soPhieuKiemDinh = "";
		this.phieuNhanHangId = "";
		this.tongTienVND = 0;
		this.chenhLechNT = 0;
		this.khoTonTruId = "";
		this.ctyId = "";
		this.nppId = "";
		this.loaiKiemDinh = "";
		this.dsPhieuKiemDinhs = new ArrayList<PhieuKiemDinh>();
		this.hosokemtheo = "";
		this.khoanMucId = "";
		this.sanphamId = "";
		this.khoXuatMauId = "";
	}

	public YCLayMauKiemNghiem(String id){
		this.userId="";
		this.msg="";
		this.id = id;
		this.db = new dbutils();
		this.ngayChungTu = "";
		this.soChungTuNhapMau = "";
		this.mauKiemNghiemId = "";
		this.soHoaDon = "";
		this.kyHieuHoaDon = "";
		this.ngayHoaDon = "";
		this.khoBietTruId = "";
		this.dienGiai = "";
		this.phongBanId = "";
		this.soPhieuKiemDinh = "";
		this.phieuNhanHangId = "";
		this.tongTienVND = 0;
		this.chenhLechNT = 0;
		this.khoTonTruId = "";
		this.ctyId = "";
		this.nppId = "";
		this.loaiKiemDinh = "";
		this.hosokemtheo = "";
		this.khoanMucId = "";
		this.dsPhieuKiemDinhs = new ArrayList<PhieuKiemDinh>();
		this.sanphamId = "";
		this.khoXuatMauId = "";
	}

	public void createRs(){
		String query = "SELECT PK_SEQ, MA + ' - ' + TEN as TEN FROM ERP_KHOTT WHERE TRANGTHAI = 1";
		this.rsKhoBietTru = db.get(query);

		query = "SELECT PK_SEQ, MA + ' - ' + TEN as TEN FROM ERP_KHOTT WHERE TRANGTHAI = 1";
		this.rsKhoTonTru = db.get(query);

		query = "SELECT PK_SEQ, MA + ' - ' + TEN as TEN FROM ERP_KHOTT WHERE TRANGTHAI = 1";
		this.rsKhoXuatMau = db.get(query);

		query = "SELECT PK_SEQ , MA + ' - ' + TEN AS TEN FROM ERP_DONVITHUCHIEN WHERE TRANGTHAI=1";
		this.rsPhongBan = db.get(query);

		query = "select PK_SEQ, MA + ' - ' + TEN AS TEN from ERP_LOAIMAUKIEMNGHIEM WHERE TRANGTHAI=1";
		this.rsMauKiemNghiem = db.get(query);

		/*query = "select PK_SEQ, MA + ' - ' + TEN AS TEN from ERP_LOAIMAUKIEMNGHIEM WHERE TRANGTHAI=1";
		this.rsPhieuNhanHang = db.get(query);*/

		query  ="select PK_SEQ, TEN + ' - ' + DIENGIAI AS TEN from ERP_NHOMCHIPHI WHERE TRANGTHAI=1\r\n" + 
				"";
		this.rsKhoanMuc = db.get(query);

		query = " SELECT 1 AS PK_SEQ , N'TỰ TẠO (MẶC ĐỊNH)' AS TEN\r\n" + 
				"UNION ALL\r\n" + 
				"SELECT 2 AS PK_SEQ , N'KIỂM ĐỊNH MUA HÀNG' AS TEN\r\n" + 
				"UNION ALL\r\n" + 
				"SELECT 3 AS PK_SEQ , N'KIỂM ĐỊNH SẢN XUẤT' AS TEN\r\n" + 
				" ";
		this.rsLoaiKiemDinh = db.get(query);
		// Load phieu kiem dinh 

		System.out.println(this.loaiKiemDinh);
		if(this.loaiKiemDinh!=null && this.loaiKiemDinh.trim().length() > 0){
			if(loaiKiemDinh.equals("2")){
				query = "SELECT A.PK_SEQ, A.MAPHIEU + ' / ' + A.NGAYKIEM as TEN\r\n" + 
						" FROM 	 ERP_YEUCAUKIEMDINH A\r\n" + 
						" WHERE  A.NHANHANG_FK IS NOT NULL AND A.LOAIMUAHANG=1 \r\n" + 
						" AND A.LOAIMUAHANG='1'";
				System.out.println(query);
				this.rsPhieuKiemDinh = db.get(query);
			}else if(loaiKiemDinh.equals("3")){
				query = "SELECT A.PK_SEQ, A.MAPHIEU + ' / ' + A.NGAYKIEM as TEN\r\n" + 
						" FROM 	 ERP_YEUCAUKIEMDINH A\r\n" + 
						" WHERE  A.NHANHANG_FK IS NOT NULL AND A.LOAIMUAHANG=2 \r\n" + 
						" AND A.LOAIMUAHANG='2'";
				this.rsPhieuKiemDinh = db.get(query);
			}
		}


		if(this.soPhieuKiemDinh != null && this.soPhieuKiemDinh.trim().length() > 0){
			// Lấy sp ra
			if(this.sanphamId != null && this.sanphamId.trim().length() > 0 && this.dsPhieuKiemDinhs.size() == 0){
				layPhieuKiemDinh(this.soPhieuKiemDinh, this.sanphamId);
			}
		}

		query = "select PK_SEQ, MA + ' - ' + TEN AS TEN from ERP_SANPHAM SP WHERE TRANGTHAI=1";
		if(this.soPhieuKiemDinh != null && this.soPhieuKiemDinh.trim().length() > 0){
			// Lấy sp ra

			query += " AND EXISTS (\r\n" + 
					"	SELECT 1 FROM ERP_YEUCAUKIEMDINH_THUNG_CHITIET WHERE SANPHAM_FK = SP.PK_SEQ AND YEUCAUKIEMDINH_FK='"+this.soPhieuKiemDinh+"'\r\n" + 
					") ";
		}

		this.rsSanPham = db.get(query);

	}




	public String getKhoXuatMauId() {
		return khoXuatMauId;
	}

	public void setKhoXuatMauId(String khoXuatMauId) {
		this.khoXuatMauId = khoXuatMauId;
	}

	public ResultSet getRsKhoXuatMau() {
		return rsKhoXuatMau;
	}

	public void setRsKhoXuatMau(ResultSet rsKhoXuatMau) {
		this.rsKhoXuatMau = rsKhoXuatMau;
	}

	public String getSanphamId() {
		return sanphamId;
	}

	public void setSanphamId(String sanphamId) {
		this.sanphamId = sanphamId;
	}

	public ResultSet getRsSanPham() {
		return rsSanPham;
	}

	public void setRsSanPham(ResultSet rsSanPham) {
		this.rsSanPham = rsSanPham;
	}

	public ResultSet getRsYCLPKN() {
		return this.rsYCLPKN;
	}

	public void setRsYCLPKN(ResultSet rsYCLPKN) {
		this.rsYCLPKN = rsYCLPKN;
	}

	private void layPhieuKiemDinh(String soPhieuKiemDinh2, String sanphamId) {
		this.dsPhieuKiemDinhs.clear();
		String query =

				"SELECT SP.PK_SEQ, SP.TEN, SP.MA, DVT.DONVI DONVI,SUM( CT.SOLUONG ) SOLUONG "
				//+ ", CT.SOLO, ISNULL(CT.NGAYNHAPKHO,'') NGAYNHAPKHO, CT.NGAYHETHAN, \r\n" + 
				//" CT.MATHUNG, CT.MAME"
				+ "  FROM ERP_YEUCAUKIEMDINH_THUNG_CHITIET CT\r\n" + 
				" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ= CT.SANPHAM_FK\r\n" + 
				" INNER JOIN DONVIDOLUONG DVT ON DVT.PK_SEQ = SP.DVDL_FK\r\n" + 
				" WHERE CT.YEUCAUKIEMDINH_FK = '"+soPhieuKiemDinh2+"' \r\n" +
				" AND CT.SANPHAM_FK = " + sanphamId + " \r\n"+
				" GROUP BY SP.PK_SEQ, SP.TEN, SP.MA, DVT.DONVI  \r\n" ;
				//" CT.SOLO,CT.MATHUNG, CT.MAME, CT.NGAYHETHAN,ISNULL(CT.NGAYNHAPKHO,'')";
				
				/*"SELECT SP.PK_SEQ, SP.TEN, SP.MA, DVT.DONVI DONVI, CT.SOLO, ISNULL(CT.NGAYNHAPKHO,'') NGAYNHAPKHO, CT.NGAYHETHAN, \n "+ 
				" CT.MATHUNG, CT.MAME, SUM( CT.SOLUONG ) SOLUONG, isnull(vt.ma,'') as vitri ,  isnull(ct.maphieu,'') as maphieu,isnull(ct.phieudt,'') as phieudt, isnull(ct.phieueo,'') as phieueo, \n "+ 
				" ISNULL(CT.MARQ,'') AS MARQ,ISNULL(NSX.TEN,'') AS NSXTEN,ISNULL(NSX.pk_seq,0) AS nsxPk_seq,isnull(ct.hamam,'') as hamam,isnull(ct.hamluong,'') as hamluong \n "+ 
				" FROM ERP_YEUCAUKIEMDINH_THUNG_CHITIET CT\n "+ 
				" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ= CT.SANPHAM_FK\n "+ 
				" INNER JOIN DONVIDOLUONG DVT ON DVT.PK_SEQ = SP.DVDL_FK\n "+ 
				" LEFT JOIN ERP_BIN VT ON CT.BIN_FK=VT.PK_SEQ \n "+ 
				" left join ERP_NHASANXUAT nsx on CT.nsx_fk = nsx.pk_seq  WHERE CT.YEUCAUKIEMDINH_FK = '"+soPhieuKiemDinh2+"'  \n "+ 
				" AND CT.SANPHAM_FK =  '" + sanphamId + "' \n "+ 
				" GROUP BY SP.PK_SEQ, SP.TEN, SP.MA, DVT.DONVI, CT.SOLO, ISNULL(CT.NGAYNHAPKHO,''), CT.NGAYHETHAN, \n "+ 
				" CT.MATHUNG, CT.MAME,isnull(vt.ma,''),  isnull(ct.maphieu,'') ,isnull(ct.phieudt,'') , isnull(ct.phieueo,'') , \n "+ 
				" ISNULL(CT.MARQ,''),ISNULL(NSX.TEN,'') ,ISNULL(NSX.pk_seq,0) ,isnull(ct.hamam,'') ,isnull(ct.hamluong,'')";*/	
		System.out.println("layPhieuKiemDinh "+query);
		ResultSet rs = db.get(query);
		try {
			PhieuKiemDinh phieuKiemDinh = null;
			while(rs.next()){
				phieuKiemDinh = new PhieuKiemDinh();
				phieuKiemDinh.setMaSp(rs.getString("MA"));
				phieuKiemDinh.setTenSp(rs.getString("TEN"));
				phieuKiemDinh.setDvt(rs.getString("DONVI"));
				phieuKiemDinh.setSanPhamId(rs.getString("PK_SEQ"));
				
				phieuKiemDinh.setSoLuongPhieuNop(rs.getString("SOLUONG"));
				
		/*		
		 		phieuKiemDinh.setLoHang(rs.getString("SOLO"));
				
		 		phieuKiemDinh.setNgaySX(rs.getString("NGAYNHAPKHO"));
				phieuKiemDinh.setNgayHetHan(rs.getString("NGAYHETHAN"));
				phieuKiemDinh.setMaThung(rs.getString("MATHUNG"));
				phieuKiemDinh.setMaMe(rs.getString("MAME"));
				
		  		phieuKiemDinh.setVitri(rs.getString("vitri"));
				phieuKiemDinh.setMaphieu(rs.getString("maphieu"));
				phieuKiemDinh.setPhieudt(rs.getString("phieudt"));
				phieuKiemDinh.setPhieueo(rs.getString("phieueo"));
				phieuKiemDinh.setMarq(rs.getString("marq"));
				phieuKiemDinh.setNhasanxuat(rs.getString("nsxten"));
				phieuKiemDinh.setNhasx_Id(rs.getString("nsxpk_seq"));

				phieuKiemDinh.setHamam(rs.getString("hamam"));
				phieuKiemDinh.setHamluong(rs.getString("hamluong"));*/
				this.dsPhieuKiemDinhs.add(phieuKiemDinh);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public ResultSet getRsLayChiTiet(String soPhieuKiemDinh2, String masanpham ){
		try{
			String query="";
			if(this.id==null||this.id.length()==0){
			query="SELECT 0 as SOLUONGLAYMAU,0 as SOLUONGMAULUU,0 as SLOLUONGONDINH,SP.PK_SEQ, SP.TEN, SP.MA, DVT.DONVI DONVI, CT.SOLO, ISNULL(CT.NGAYNHAPKHO,'') NGAYNHAPKHO, CT.NGAYHETHAN, \n "+ 
					" CT.MATHUNG, CT.MAME, CT.SOLUONG as SOLUONG, isnull(vt.ma,'') as vitri ,  isnull(ct.maphieu,'') as maphieu,isnull(ct.phieudt,'') as phieudt, isnull(ct.phieueo,'') as phieueo, \n "+ 
					" ISNULL(CT.mamarquette,'') AS MARQ,ISNULL(NSX.TEN,'') AS NSXTEN,"
					+ " ISNULL(NSX.pk_seq,0) AS nsxPk_seq,isnull(ct.hamam,'') as hamam,isnull(ct.hamluong,'') as hamluong \n "+ 
					" FROM ERP_YEUCAUKIEMDINH_THUNG_CHITIET CT\n "+ 
					" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ= CT.SANPHAM_FK\n "+ 
					" INNER JOIN DONVIDOLUONG DVT ON DVT.PK_SEQ = SP.DVDL_FK\n "+ 
					" LEFT JOIN ERP_BIN VT ON CT.BIN_FK=VT.PK_SEQ \n "+ 
					" left join ERP_NHASANXUAT nsx on CT.nsx_fk = nsx.pk_seq  WHERE CT.YEUCAUKIEMDINH_FK = '"+soPhieuKiemDinh2+"'  \n "+ 
					" AND sp.ma =  '" + masanpham + "' \n ";
					/*" GROUP BY SP.PK_SEQ, SP.TEN, SP.MA, DVT.DONVI, CT.SOLO, ISNULL(CT.NGAYNHAPKHO,''), CT.NGAYHETHAN, \n "+ 
					" CT.MATHUNG, CT.MAME,isnull(vt.ma,''),  isnull(ct.maphieu,'') ,isnull(ct.phieudt,'') , isnull(ct.phieueo,'') , \n "+ 
					" ISNULL(CT.MARQ,''),ISNULL(NSX.TEN,'') ,ISNULL(NSX.pk_seq,0) ,isnull(ct.hamam,'') ,isnull(ct.hamluong,'')";	*/
			}
			else{
				query=  "SELECT [YCLAYMAUKIEMNGHIEM_FK]\r\n" + 
						"      ,[SANPHAM_FK] as PK_SEQ\r\n" + 
						"      ,[SOLUONG]\r\n" + 
						"      ,SOLUONGLAYMAU \r\n" + 
						"      ,SOLUONGMAULUU \r\n" + 
						"      ,SLOLUONGONDINH \r\n" + 
						"      ,[MASP]\r\n" + 
						"      ,[TENSP]\r\n" + 
						"      ,[DVT]\r\n" + 
						"      ,[LOHANG] as SOLO\r\n" + 
						"      ,[NGAYSX] AS NGAYNHAPKHO\r\n" + 
						"      ,[NGAYHH] AS NGAYHETHAN \r\n" + 
						"      ,[MATHUNG]\r\n" + 
						"      ,[MAME]\r\n" +
						"      ,[THOIGIANHUYMAU]\r\n" + 
						"      ,[STT]\r\n" + 
						
						"      ,ISNULL(VITRI,'') AS VITRI \r\n" + 
						"      ,ISNULL(maphieu,'') AS MAPHIEU \r\n" + 
						"      ,ISNULL(phieudt,'') AS PHIEUDT \r\n" + 
						"      ,ISNULL(phieueo,'') AS PHIEUEO \r\n" + 
						"      ,ISNULL(MARQ,'') AS MARQ \r\n" + 
						"      ,ISNULL(NSX.TEN,'') AS NSXTEN, ISNULL(CT.NSX_FK,0) AS nsxPk_seq \r\n" + 
						"      ,ISNULL(HAMLUONG,'') AS HAMLUONG \r\n" + 
						"      ,ISNULL(HAMAM,'') AS HAMAM \r\n" + 
					
						"  FROM [dbo].[ERP_YCLAYMAUKIEMNGHIEM_CHITIET] CT \r\n" +
						"  LEFT JOIN ERP_NHASANXUAT NSX ON CT.nsx_fk=NSX.pk_seq " + 
						"  WHERE 1=1 AND [YCLAYMAUKIEMNGHIEM_FK] = " + this.id + " "; 
					query += " ORDER BY STT ASC \r\n";
				System.out.println("________init sp YCLAYMAUKIEMNGHIEM_FK______" + query);
			}
			System.out.println("getRsLayChiTiet "+query);
			return db.get(query);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("EXCEPTION INIT SOLO: " + e.getMessage());
		}
		return null;

	}


	public String getHosokemtheo() {
		return hosokemtheo;
	}

	public void setHosokemtheo(String hosokemtheo) {
		this.hosokemtheo = hosokemtheo;
	}

	public String getKhoanMucId() {
		return khoanMucId;
	}

	public void setKhoanMucId(String khoanMucId) {
		this.khoanMucId = khoanMucId;
	}

	public ResultSet getRsKhoanMuc() {
		return rsKhoanMuc;
	}

	public void setRsKhoanMuc(ResultSet rsKhoanMuc) {
		this.rsKhoanMuc = rsKhoanMuc;
	}

	public List<PhieuKiemDinh> getDsPhieuKiemDinhs() {
		return dsPhieuKiemDinhs;
	}

	public void setDsPhieuKiemDinhs(List<PhieuKiemDinh> dsPhieuKiemDinhs) {
		this.dsPhieuKiemDinhs = dsPhieuKiemDinhs;
	}

	public String getLoaiKiemDinh() {
		return loaiKiemDinh;
	}

	public void setLoaiKiemDinh(String loaiKiemDinh) {
		this.loaiKiemDinh = loaiKiemDinh;
	}

	public ResultSet getRsLoaiKiemDinh() {
		return rsLoaiKiemDinh;
	}

	public void setRsLoaiKiemDinh(ResultSet rsLoaiKiemDinh) {
		this.rsLoaiKiemDinh = rsLoaiKiemDinh;
	}

	public ResultSet getRsPhieuKiemDinh() {
		return rsPhieuKiemDinh;
	}

	public void setRsPhieuKiemDinh(ResultSet rsPhieuKiemDinh) {
		this.rsPhieuKiemDinh = rsPhieuKiemDinh;
	}

	public String getCtyId() {
		return ctyId;
	}

	public void setCtyId(String ctyId) {
		this.ctyId = ctyId;
	}

	public String getNppId() {
		return nppId;
	}

	public void setNppId(String nppId) {
		this.nppId = nppId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public dbutils getDb() {
		return db;
	}

	public void setDb(dbutils db) {
		this.db = db;
	}

	public String getNgayChungTu() {
		return ngayChungTu;
	}

	public void setNgayChungTu(String ngayChungTu) {
		this.ngayChungTu = ngayChungTu;
	}

	public String getSoChungTuNhapMau() {
		return soChungTuNhapMau;
	}

	public void setSoChungTuNhapMau(String soChungTuNhapMau) {
		this.soChungTuNhapMau = soChungTuNhapMau;
	}

	public String getMauKiemNghiemId() {
		return mauKiemNghiemId;
	}

	public void setMauKiemNghiemId(String mauKiemNghiemId) {
		this.mauKiemNghiemId = mauKiemNghiemId;
	}

	public String getSoHoaDon() {
		return soHoaDon;
	}

	public void setSoHoaDon(String soHoaDon) {
		this.soHoaDon = soHoaDon;
	}

	public String getKyHieuHoaDon() {
		return kyHieuHoaDon;
	}

	public void setKyHieuHoaDon(String kyHieuHoaDon) {
		this.kyHieuHoaDon = kyHieuHoaDon;
	}

	public String getNgayHoaDon() {
		return ngayHoaDon;
	}

	public void setNgayHoaDon(String ngayHoaDon) {
		this.ngayHoaDon = ngayHoaDon;
	}

	public String getKhoBietTruId() {
		return khoBietTruId;
	}

	public void setKhoBietTruId(String khoBietTruId) {
		this.khoBietTruId = khoBietTruId;
	}

	public String getDienGiai() {
		return dienGiai;
	}

	public void setDienGiai(String dienGiai) {
		this.dienGiai = dienGiai;
	}

	public String getPhongBanId() {
		return phongBanId;
	}

	public void setPhongBanId(String phongBanId) {
		this.phongBanId = phongBanId;
	}

	public String getSoPhieuKiemDinh() {
		return soPhieuKiemDinh;
	}

	public void setSoPhieuKiemDinh(String soPhieuKiemDinh) {
		this.soPhieuKiemDinh = soPhieuKiemDinh;
	}

	public String getPhieuNhanHangId() {
		return phieuNhanHangId;
	}

	public void setPhieuNhanHangId(String phieuNhanHangId) {
		this.phieuNhanHangId = phieuNhanHangId;
	}

	public double getTongTienVND() {
		return tongTienVND;
	}

	public void setTongTienVND(double tongTienVND) {
		this.tongTienVND = tongTienVND;
	}

	public double getChenhLechNT() {
		return chenhLechNT;
	}

	public void setChenhLechNT(double chenhLechNT) {
		this.chenhLechNT = chenhLechNT;
	}

	public String getKhoTonTruId() {
		return khoTonTruId;
	}

	public void setKhoTonTruId(String khoTonTruId) {
		this.khoTonTruId = khoTonTruId;
	}

	public ResultSet getRsMauKiemNghiem() {
		return rsMauKiemNghiem;
	}

	public void setRsMauKiemNghiem(ResultSet rsMauKiemNghiem) {
		this.rsMauKiemNghiem = rsMauKiemNghiem;
	}

	public ResultSet getRsPhongBan() {
		return rsPhongBan;
	}

	public void setRsPhongBan(ResultSet rsPhongBan) {
		this.rsPhongBan = rsPhongBan;
	}

	public ResultSet getRsPhieuNhanHang() {
		return rsPhieuNhanHang;
	}

	public void setRsPhieuNhanHang(ResultSet rsPhieuNhanHang) {
		this.rsPhieuNhanHang = rsPhieuNhanHang;
	}

	public ResultSet getRsKhoBietTru() {
		return rsKhoBietTru;
	}

	public void setRsKhoBietTru(ResultSet rsKhoBietTru) {
		this.rsKhoBietTru = rsKhoBietTru;
	}

	public ResultSet getRsKhoTonTru() {
		return rsKhoTonTru;
	}

	public void setRsKhoTonTru(ResultSet rsKhoTonTru) {
		this.rsKhoTonTru = rsKhoTonTru;
	}

	public void DBclose() {

	}

	private String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}

	public void init() {
		String query = "SELECT [PK_SEQ]\r\n" + 
				"      ,[DVTH_FK]\r\n" + 
				"      ,[SANPHAM_FK]\r\n" + 
				"      ,[LOAIKIEMDINH]\r\n" + 
				"      ,[YCKIEMDINH_FK]\r\n" + 
				"      ,[MAUKIEMNGHIEM_FK]\r\n" + 
				"      ,ISNULL([HOSO],'') HOSO\r\n" + 
				"      ,[NGAYCHUNGTU]\r\n" + 
				"      ,[TRANGTHAI]\r\n" + 
				"      ,[KHOLUUMAU_FK]\r\n" + 
				"      ,[KHOONDINH_FK]\r\n" + 
				"      ,[QUYTRINHLAYMAU]\r\n" + 
				"      ,[NGUOITAO]\r\n" + 
				"      ,[NGAYTAO]\r\n" + 
				"      ,[NGUOISUA]\r\n" + 
				"      ,[NGAYSUA]\r\n" + 
				"      ,[CONGTY_FK]\r\n" + 
				"      ,[KHOANMUC_FK]\r\n" + 
				"      ,[KHOXUATMAU_FK]\r\n" +  
				"      ,isnull([SOCTNHAPMAU],'') SOCTNHAPMAU\r\n" +
				"      ,isnull([DIENGIAI],'') DIENGIAI\r\n" +
				"  FROM [dbo].[ERP_YCLAYMAUKIEMNGHIEM] WHERE 1=1";

		if(this.id != null && this.id.trim().length() > 0){
			query += " AND PK_SEQ = " + this.id;
		}

		System.out.println("________init sp______" + query);

		ResultSet rs = db.get(query);
		try {
			if(rs.next()){
				this.phongBanId = rs.getString("DVTH_FK");
				this.sanphamId = rs.getString("SANPHAM_FK");
				this.loaiKiemDinh = rs.getString("LOAIKIEMDINH");
				this.mauKiemNghiemId = rs.getString("MAUKIEMNGHIEM_FK");
				this.hosokemtheo = rs.getString("HOSO");
				this.ngayChungTu = rs.getString("NGAYCHUNGTU");
				this.khoBietTruId = rs.getString("KHOLUUMAU_FK");
				this.khoTonTruId = rs.getString("KHOONDINH_FK");
				this.dienGiai = rs.getString("QUYTRINHLAYMAU");
				this.khoanMucId = rs.getString("KHOANMUC_FK");
				this.khoXuatMauId = rs.getString("KHOXUATMAU_FK");
				this.soPhieuKiemDinh = rs.getString("YCKIEMDINH_FK");
				this.soChungTuNhapMau = rs.getString("SOCTNHAPMAU");
				this.dienGiai = rs.getString("DIENGIAI");

				initSanPhamKiemDinh(this.id);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initSanPhamKiemDinh(String id2) throws SQLException {
		String query = "SELECT [YCLAYMAUKIEMNGHIEM_FK]\r\n" + 
				"      ,[SANPHAM_FK]\r\n" + 
				"      ,sum([SOLUONG]) as soluong \r\n" + 
				"      ,sum(SOLUONGLAYMAU) as SOLUONGLAYMAU \r\n" + 
				"      ,sum(SOLUONGMAULUU) as SOLUONGMAULUU \r\n" + 
				"      ,sum(SLOLUONGONDINH) as SLOLUONGONDINH \r\n" + 
				"      ,[MASP]\r\n" + 
				"      ,[TENSP]\r\n" + 
				"      ,[DVT]\r\n" + 
				"      ,[THOIGIANHUYMAU]\r\n" + 
				
				"  FROM [dbo].[ERP_YCLAYMAUKIEMNGHIEM_CHITIET] CT \r\n" + 
				"  WHERE 1=1  "; 


		if(id2 !=null && id2.trim().length() > 0){
			query += " AND [YCLAYMAUKIEMNGHIEM_FK] = " + id2 + " \r\n";
		}

		//query += " ORDER BY STT ASC \r\n";
		query += " group by [YCLAYMAUKIEMNGHIEM_FK],[SANPHAM_FK],[MASP],[TENSP],[DVT],[THOIGIANHUYMAU] \r\n";
		System.out.println("________init sp YCLAYMAUKIEMNGHIEM_FK______" + query);

		ResultSet rs = db.get(query);
		PhieuKiemDinh phieuKiemDinh = null;
		while(rs.next()){
			phieuKiemDinh = new PhieuKiemDinh();
			phieuKiemDinh.setMaSp(rs.getString("MASP"));
			phieuKiemDinh.setTenSp(rs.getString("TENSP"));
			phieuKiemDinh.setDvt(rs.getString("DVT"));
/*			phieuKiemDinh.setLoHang(rs.getString("LOHANG"));
			phieuKiemDinh.setNgaySX(rs.getString("NGAYSX"));
			phieuKiemDinh.setNgayHetHan(rs.getString("NGAYHH"));
			phieuKiemDinh.setMaThung(rs.getString("MATHUNG"));
			phieuKiemDinh.setMaMe(rs.getString("MAME"));*/
			phieuKiemDinh.setSoLuongPhieuNop(rs.getString("SOLUONG"));
			phieuKiemDinh.setSoLuongLayMau(rs.getString("SOLUONGLAYMAU"));
			phieuKiemDinh.setSoLuongMauLuu(rs.getString("SOLUONGMAULUU"));
			phieuKiemDinh.setSoLuongTheoDoiDoOnDinh(rs.getString("SLOLUONGONDINH"));
			//phieuKiemDinh.setSoLuongConLai(rs.getString("CONLAI"));
			phieuKiemDinh.setThoiGianHuyMai(rs.getString("THOIGIANHUYMAU"));

			getDsPhieuKiemDinhs().add(phieuKiemDinh);
		}
	}

	public boolean create(){

		String ngaytao = this.getDateTime();

		try{
			db.getConnection().setAutoCommit(false);

			String query = "INSERT ERP_YCLAYMAUKIEMNGHIEM(DVTH_FK, SANPHAM_FK, LOAIKIEMDINH, YCKIEMDINH_FK, MAUKIEMNGHIEM_FK, HOSO, NGAYCHUNGTU,"
					+ " TRANGTHAI, KHOLUUMAU_FK, KHOONDINH_FK, QUYTRINHLAYMAU, NGUOITAO, NGAYTAO, NGUOISUA, NGAYSUA, CONGTY_FK, KHOANMUC_FK, KHOXUATMAU_FK, DIENGIAI, SOCTNHAPMAU) "
					+ " VALUES('"+this.phongBanId+"', '"+this.sanphamId+"', '"+this.loaiKiemDinh+"', '"+this.soPhieuKiemDinh+"', "+this.mauKiemNghiemId+","
					+ " '"+(this.hosokemtheo.trim().length() == 0 ? "" : this.hosokemtheo)+"', '"+this.ngayChungTu+"', '0', '"+this.khoBietTruId+"', "+(this.khoTonTruId.trim().length() == 0 ? null : this.khoTonTruId)+", "+(this.soChungTuNhapMau.trim().length() == 0 ? null : this.soChungTuNhapMau)+","
					+ " '"+this.userId+"', '"+ngaytao+"', '"+this.userId+"', '"+ngaytao+"', '"+this.ctyId+"', '"+this.khoanMucId+"', '"+this.khoXuatMauId+"', '"+this.dienGiai+"', "+(this.soChungTuNhapMau.trim().length() == 0 ? null : this.soChungTuNhapMau)+")";

			System.out.println("_____query_1____: " + query);


			if(!db.update(query))
			{
				this.msg = "Không thể tạo ERP_YCLAYMAUKIEMNGHIEM lỗi: " + query;
				db.getConnection().rollback();
				return false;
			} 

			String currentId = "";//Lay PK_SEQ vua insert
			query = "select IDENT_CURRENT('ERP_YCLAYMAUKIEMNGHIEM') as Id";

			ResultSet rsLmkn = db.get(query);						
			if(rsLmkn.next())
			{
				currentId = rsLmkn.getString("Id");
				rsLmkn.close();
			}

			for(PhieuKiemDinh pkd : getDsPhieuKiemDinhs()){
				query = "INSERT ERP_YCLAYMAUKIEMNGHIEM_CHITIET(YCLAYMAUKIEMNGHIEM_FK, SANPHAM_FK, SOLUONG, SOLUONGLAYMAU, SOLUONGMAULUU, SLOLUONGONDINH, CONLAI, MASP,"
						+ " TENSP, DVT, LOHANG, NGAYSX, NGAYHH, MATHUNG, MAME, THOIGIANHUYMAU, STT"
						+ " ,VITRI,MAPHIEU,PHIEUDT,PHIEUEO,NSX_FK,MARQ,hamluong,hamam)"
						+ " VALUES('"+currentId+"', '"+this.sanphamId+"', "+pkd.getSoLuongPhieuNop()+", "+pkd.getSoLuongLayMau()+", "+pkd.getSoLuongMauLuu()+", "
						+ " "+pkd.getSoLuongTheoDoiDoOnDinh()+", "+pkd.getSoLuongConLai()+", '"+pkd.getMaSp()+"', N'"+pkd.getTenSp()+"', N'"+pkd.getDvt()+"', "
						+ " '"+pkd.getLoHang()+"', '"+pkd.getNgaySX()+"', '"+pkd.getNgayHetHan()+"', '"+pkd.getMaThung()+"', '"+pkd.getMaMe()+"', '"+pkd.getThoiGianHuyMai()+"', "+pkd.getSott()+" "
						+ " , '"+pkd.getVitri()+"','"+pkd.getMaphieu()+"','"+pkd.getPhieudt()+"','"+pkd.getPhieueo()+"',"+pkd.getNhasx_Id()+",'"+pkd.getMarq()+"','"+pkd.getHamluong()+"','"+pkd.getHamam()+"')";

				System.out.println("_____query_2____: " + query);
				if(!db.update(query))
				{
					this.msg = "Không thể tạo ERP_YCLAYMAUKIEMNGHIEM_CHITIET lỗi: " + query;
					db.getConnection().rollback();
					return false;
				}
			}


			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;

		}catch(Exception e){
			e.printStackTrace();
			return false;
		}

	}

	public boolean update(){

		String ngaytao = this.getDateTime();
		String query = "";
		try{
			db.getConnection().setAutoCommit(false);

			/*query = "delete ERP_YCLAYMAUKIEMNGHIEM where PK_SEQ = '"+this.id+"'";

			if(!db.update(query))
			{
				this.msg = "Không thể xóa ERP_YCLAYMAUKIEMNGHIEM lỗi: " + query;
				db.getConnection().rollback();
				return false;
			} */

			query = "update ERP_YCLAYMAUKIEMNGHIEM set DVTH_FK='"+this.phongBanId+"', SANPHAM_FK='"+this.sanphamId+"', LOAIKIEMDINH='"+this.loaiKiemDinh+"',"
					+ " YCKIEMDINH_FK='"+this.soPhieuKiemDinh+"', MAUKIEMNGHIEM_FK="+this.mauKiemNghiemId+", HOSO='"+(this.hosokemtheo.trim().length() == 0 ? "" : this.hosokemtheo)+"',"
					+ " NGAYCHUNGTU='"+this.ngayChungTu+"', TRANGTHAI=0, KHOLUUMAU_FK='"+this.khoBietTruId+"', KHOONDINH_FK="+(this.khoTonTruId.trim().length() == 0 ? null : this.khoTonTruId)+", QUYTRINHLAYMAU='"+(this.soChungTuNhapMau.trim().length() == 0 ? "" : this.soChungTuNhapMau)+"',"
					+ " NGUOISUA='"+this.userId+"', NGAYSUA='"+ngaytao+"', CONGTY_FK='"+this.ctyId+"', KHOANMUC_FK='"+this.khoanMucId+"', KHOXUATMAU_FK='"+this.khoXuatMauId+"', DIENGIAI='"+this.dienGiai+"', SOCTNHAPMAU='"+this.soChungTuNhapMau+"' \r\n"
					+ " where PK_SEQ='"+this.id+"'";

			/*query = "INSERT ERP_YCLAYMAUKIEMNGHIEM(DVTH_FK, SANPHAM_FK, LOAIKIEMDINH, YCKIEMDINH_FK, MAUKIEMNGHIEM_FK, HOSO, NGAYCHUNGTU,"
					+ " TRANGTHAI, KHOLUUMAU_FK, KHOONDINH_FK, QUYTRINHLAYMAU, NGUOITAO, NGAYTAO, NGUOISUA, NGAYSUA, CONGTY_FK, KHOANMUC_FK, KHOXUATMAU_FK, DIENGIAI, SOCTNHAPMAU) "
					+ " VALUES('"+this.phongBanId+"', '"+this.sanphamId+"', '"+this.loaiKiemDinh+"', '"+this.soPhieuKiemDinh+"', '"+this.mauKiemNghiemId+"',"
					+ " "+(this.hosokemtheo.trim().length() == 0 ? null : this.hosokemtheo)+", '"+this.ngayChungTu+"', '0', '"+this.khoBietTruId+"', "+(this.khoTonTruId.trim().length() == 0 ? null : this.khoTonTruId)+", "+(this.soChungTuNhapMau.trim().length() == 0 ? null : this.soChungTuNhapMau)+","
					+ " '"+this.userId+"', '"+ngaytao+"', '"+this.userId+"', '"+ngaytao+"', '"+this.ctyId+"', '"+this.khoanMucId+"', '"+this.khoXuatMauId+"', '"+this.dienGiai+"', "+(this.soChungTuNhapMau.trim().length() == 0 ? null : this.soChungTuNhapMau)+")";*/

			System.out.println("_____query_1____: " + query);


			if(!db.update(query))
			{
				this.msg = "Không thể tạo ERP_YCLAYMAUKIEMNGHIEM lỗi: " + query;
				db.getConnection().rollback();
				return false;
			} 

/*			String stt = "";
			query = "select STT from ERP_YCLAYMAUKIEMNGHIEM_CHITIET where YCLAYMAUKIEMNGHIEM_FK='"+this.id+"'";

			ResultSet rsLmkn = db.get(query);						
			if(rsLmkn.next())
			{
				stt = rsLmkn.getString("STT");
				rsLmkn.close();
			}
*/
			/*query = "delete ERP_YCLAYMAUKIEMNGHIEM_CHITIET where YCLAYMAUKIEMNGHIEM_FK = '"+this.id+"'";

			if(!db.update(query))
			{
				this.msg = "Không thể xóa ERP_YCLAYMAUKIEMNGHIEM_CHITIET lỗi: " + query;
				db.getConnection().rollback();
				return false;
			} */

			for(PhieuKiemDinh pkd : getDsPhieuKiemDinhs()){

				query = "update ERP_YCLAYMAUKIEMNGHIEM_CHITIET set SANPHAM_FK='"+this.sanphamId+"', SOLUONG="+pkd.getSoLuongPhieuNop()+","
						+ "SOLUONGLAYMAU="+pkd.getSoLuongLayMau()+", SOLUONGMAULUU="+pkd.getSoLuongMauLuu()+", SLOLUONGONDINH="+pkd.getSoLuongTheoDoiDoOnDinh()+","
						+ " CONLAI="+pkd.getSoLuongConLai()+", MASP='"+pkd.getMaSp()+"', TENSP='"+pkd.getTenSp()+"', DVT='"+pkd.getDvt()+"', LOHANG='"+pkd.getLoHang()+"',"
						+ " NGAYSX='"+pkd.getNgaySX()+"', NGAYHH='"+pkd.getNgayHetHan()+"', MATHUNG='"+pkd.getMaThung()+"', MAME='"+pkd.getMaMe()+"', THOIGIANHUYMAU='"+pkd.getThoiGianHuyMai()+"' \r\n"
						+ " , VITRI='"+pkd.getVitri()+"',maphieu='"+pkd.getMaphieu()+"',phieudt='"+pkd.getPhieudt()+"',phieueo='"+pkd.getPhieueo()+"',nsx_fk='"+pkd.getNhasx_Id()+"',marq='"+pkd.getMarq()+"',hamluong='"+pkd.getHamluong()+"',hamam='"+pkd.getHamam()+"' "
						+ " where YCLAYMAUKIEMNGHIEM_FK='"+this.id+"' and STT="+pkd.getSott()+"";

				/*query = "INSERT ERP_YCLAYMAUKIEMNGHIEM_CHITIET(YCLAYMAUKIEMNGHIEM_FK, SANPHAM_FK, SOLUONG, SOLUONGLAYMAU, SOLUONGMAULUU, SLOLUONGONDINH, CONLAI, MASP,"
						+ " TENSP, DVT, LOHANG, NGAYSX, NGAYHH, MATHUNG, MAME, THOIGIANHUYMAU, STT)"
						+ " VALUES('"+this.id+"', '"+this.sanphamId+"', "+pkd.getSoLuongPhieuNop()+", "+pkd.getSoLuongLayMau()+", "+pkd.getSoLuongMauLuu()+", "
						+ " "+pkd.getSoLuongTheoDoiDoOnDinh()+", "+pkd.getSoLuongConLai()+", '"+pkd.getMaSp()+"', N'"+pkd.getTenSp()+"', N'"+pkd.getDvt()+"', "
						+ " '"+pkd.getLoHang()+"', '"+pkd.getNgaySX()+"', '"+pkd.getNgayHetHan()+"', '"+pkd.getMaThung()+"', '"+pkd.getMaMe()+"', '"+pkd.getThoiGianHuyMai()+"', "+pkd.getSott()+")";*/

				System.out.println("_____query_2____: " + query);
				if(!db.update(query))
				{
					this.msg = "Không thể tạo ERP_YCLAYMAUKIEMNGHIEM_CHITIET lỗi: " + query;
					db.getConnection().rollback();
					return false;
				}
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;

		}catch(Exception e){
			e.printStackTrace();
			return false;
		}

	}



}
