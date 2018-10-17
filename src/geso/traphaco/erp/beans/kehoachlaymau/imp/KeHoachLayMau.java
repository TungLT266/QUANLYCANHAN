package geso.traphaco.erp.beans.kehoachlaymau.imp;

import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.erp.beans.kehoachlaymau.IKeHoachLayMau;
import geso.traphaco.erp.beans.kehoachlaymau.imp.PhieuKiemDinh;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KeHoachLayMau extends Phan_Trang implements IKeHoachLayMau {
	private static final long serialVersionUID = 1L;
	String userId;
	String msg;
	dbutils db;
	private String ngayKeHoach;
	private String soTT;
	private String phongBanId;
	private String dienGiai;
	private String ngayDanhGia;
	private String ngayDanhGiaLai;
	private String ngayThucTe;
	private String ghiChu;
	private String id;
	private String ctyId;
	private String nppId; 
	private String sanphamId;
	private String trangthai;
	
	/**
	 * Select
	 */
	
 
	private ResultSet rsPhongBan;
 
	private List<PhieuKiemDinh> dsPhieuKiemDinhs;
	private ResultSet rsSanPham; 
	
	
	public KeHoachLayMau(){
		this.userId="";
		this.msg="";
		this.id = "";
		this.db = new dbutils();
		this.ngayKeHoach = "";
		this.soTT = " ";
		this.dienGiai = "";
		this.phongBanId = "";
		this.ngayDanhGia = "";
		this.ngayDanhGiaLai = "";
		this.ngayThucTe = "";
		this.ctyId = "";
		this.nppId = "";
		this.ghiChu = "";
		this.dsPhieuKiemDinhs = new ArrayList<PhieuKiemDinh>();
 
		this.sanphamId = "";
		this.trangthai = "";
 
	}
	
	public KeHoachLayMau(String id){
		this.userId="";
		this.msg="";
		this.id = id;
		this.db = new dbutils();
		this.ngayKeHoach = "";
		this.soTT = " ";
		this.dienGiai = "";
		this.phongBanId = "";
		this.ngayDanhGia = "";
		this.ngayDanhGiaLai = "";
		this.ngayThucTe = "";
		this.ctyId = "";
		this.nppId = "";
		this.ghiChu = "";
		this.dsPhieuKiemDinhs = new ArrayList<PhieuKiemDinh>();
		this.sanphamId = "";
		this.trangthai = "";
 
	}
	
	public void createRs(){
		String query = "";
		query = "SELECT PK_SEQ , MA + ' - ' + TEN AS TEN FROM ERP_DONVITHUCHIEN WHERE TRANGTHAI=1";
		this.rsPhongBan = db.get(query);
		
		 
		/*query = "select PK_SEQ, MA + ' - ' + TEN AS TEN from ERP_LOAIMAUKIEMNGHIEM WHERE TRANGTHAI=1";
		this.rsPhieuNhanHang = db.get(query);*/
 
	 
		query = "select PK_SEQ, MA + ' - ' + TEN AS TEN from ERP_SANPHAM SP WHERE TRANGTHAI=1";
 
		this.rsSanPham = db.get(query);
		
	}
 
	public String getNgayDanhGia() {
		return ngayDanhGia;
	}

	public void setNgayDanhGia(String ngayDanhGia) {
		this.ngayDanhGia = ngayDanhGia;
	}

	public String getNgayDanhGiaLai() {
		return ngayDanhGiaLai;
	}

	public void setNgayDanhGiaLai(String ngayDanhGiaLai) {
		this.ngayDanhGiaLai = ngayDanhGiaLai;
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
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
	
	
	
	/*private void layPhieuKiemDinh(String soPhieuKiemDinh2, String sanphamId) {
		this.dsPhieuKiemDinhs.clear();
		String query =
		
				"SELECT SP.PK_SEQ, SP.TEN, SP.MA, DVT.DONVI DONVI, CT.SOLO, ISNULL(CT.NGAYNHAPKHO,'') NGAYNHAPKHO, CT.NGAYHETHAN, \r\n" + 
				" CT.MATHUNG, CT.MAME, SUM( CT.SOLUONG ) SOLUONG  FROM ERP_YEUCAUKIEMDINH_THUNG_CHITIET CT\r\n" + 
				" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ= CT.SANPHAM_FK\r\n" + 
				" INNER JOIN DONVIDOLUONG DVT ON DVT.PK_SEQ = SP.DVDL_FK\r\n" + 
				" WHERE CT.YEUCAUKIEMDINH_FK = '"+soPhieuKiemDinh2+"' \r\n" +
				" AND CT.SANPHAM_FK = " + sanphamId + " \r\n"+
				" GROUP BY SP.PK_SEQ, SP.TEN, SP.MA, DVT.DONVI, CT.SOLO, ISNULL(CT.NGAYNHAPKHO,''), CT.NGAYHETHAN, \r\n" + 
				" CT.MATHUNG, CT.MAME";
		System.out.println(query);
		ResultSet rs = db.get(query);
		try {
			PhieuKiemDinh phieuKiemDinh = null;
			while(rs.next()){
				phieuKiemDinh = new PhieuKiemDinh();
				phieuKiemDinh.setMaSp(rs.getString("MA"));
				phieuKiemDinh.setTenSp(rs.getString("TEN"));
				phieuKiemDinh.setDvt(rs.getString("DONVI"));
				phieuKiemDinh.setSanPhamId(rs.getString("PK_SEQ"));
				phieuKiemDinh.setLoHang(rs.getString("SOLO"));
				phieuKiemDinh.setNgaySX(rs.getString("NGAYNHAPKHO"));
				phieuKiemDinh.setNgayHetHan(rs.getString("NGAYHETHAN"));
				phieuKiemDinh.setMaThung(rs.getString("MATHUNG"));
				phieuKiemDinh.setMaMe(rs.getString("MAME"));
				phieuKiemDinh.setSoLuongPhieuNop(rs.getString("SOLUONG"));
				
				this.dsPhieuKiemDinhs.add(phieuKiemDinh);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/
	
 
	public String getNgayThucTe() {
		return ngayThucTe;
	}

	public void setNgayThucTe(String ngayThucTe) {
		this.ngayThucTe = ngayThucTe;
	}

	public String getTrangThai() {
		return trangthai;
	}

	public void setTrangThai(String trangthai) {
		this.trangthai = trangthai;
	}

	public String getNgayKeHoach() {
		return ngayKeHoach;
	}

	public void setNgayKeHoach(String ngayKeHoach) {
		this.ngayKeHoach = ngayKeHoach;
	}

	public List<PhieuKiemDinh> getDsPhieuKiemDinhs() {
		return dsPhieuKiemDinhs;
	}

	public void setDsPhieuKiemDinhs(List<PhieuKiemDinh> dsPhieuKiemDinhs) {
		this.dsPhieuKiemDinhs = dsPhieuKiemDinhs;
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

	 
	public String getDienGiai() {
		return dienGiai;
	}

	public void setDienGiai(String dienGiai) {
		this.dienGiai = dienGiai;
	}
 
	public ResultSet getRsPhongBan() {
		return rsPhongBan;
	}

	public void setRsPhongBan(ResultSet rsPhongBan) {
		this.rsPhongBan = rsPhongBan;
	}
 
	public void DBclose() {
 
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
 
	public String getSoTT() {
		return this.soTT;
	}

	public void setSoTT(String soTT) {
		this.soTT = soTT;
	}

	/*public void init() {
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
				"      ,[STT]\r\n" + 
				"  FROM [dbo].[ERP_YCLAYMAUKIEMNGHIEM] WHERE PK_SEQ = " + this.id;
		
		System.out.println("________init sp______" + query);
		
		ResultSet rs = db.get(query);
		try {
			if(rs.next()){
				 
				this.sanphamId = rs.getString("SANPHAM_FK");
			 
				this.ngayKeHoach = rs.getString("NGAYCHUNGTU");
				 
		 
				this.dienGiai = rs.getString("QUYTRINHLAYMAU");
 
				initSanPhamKiemDinh(this.id);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	
	/*private void initSanPhamKiemDinh(String id2) throws SQLException {
		String query = "SELECT [YCLAYMAUKIEMNGHIEM_FK]\r\n" + 
				"      ,[SANPHAM_FK]\r\n" + 
				"      ,[SOLUONG]\r\n" + 
				"      ,[SOLUONGLAYMAU]\r\n" + 
				"      ,[SOLUONGMAULUU]\r\n" + 
				"      ,[SLOLUONGONDINH]\r\n" + 
				"      ,[CONLAI]\r\n" + 
				"      ,[MASP]\r\n" + 
				"      ,[TENSP]\r\n" + 
				"      ,[DVT]\r\n" + 
				"      ,[LOHANG]\r\n" + 
				"      ,[NGAYSX]\r\n" + 
				"      ,[NGAYHH]\r\n" + 
				"      ,[MATHUNG]\r\n" + 
				"      ,[MAME]\r\n" + 
				"      ,[THOIGIANHUYMAU]\r\n" + 
				"      ,[STT]\r\n" + 
				"  FROM [dbo].[ERP_YCLAYMAUKIEMNGHIEM_CHITIET]\r\n" + 
				"  WHERE [YCLAYMAUKIEMNGHIEM_FK] = " + id2 + " \r\n" + 
				"  ORDER BY STT ASC \r\n";
		
		System.out.println("________init sp______" + query);
		
		ResultSet rs = db.get(query);
		PhieuKiemDinh phieuKiemDinh = null;
		while(rs.next()){
			phieuKiemDinh = new PhieuKiemDinh();
			phieuKiemDinh.setMaSp(rs.getString("MASP"));
			phieuKiemDinh.setTenSp(rs.getString("TENSP"));
			phieuKiemDinh.setDvt(rs.getString("DVT"));
			phieuKiemDinh.setLoHang(rs.getString("LOHANG"));
			phieuKiemDinh.setNgaySX(rs.getString("NGAYSX"));
			phieuKiemDinh.setNgayHetHan(rs.getString("NGAYHH"));
			phieuKiemDinh.setMaThung(rs.getString("MATHUNG"));
			phieuKiemDinh.setMaMe(rs.getString("MAME"));
			phieuKiemDinh.setSoLuongPhieuNop(rs.getString("SOLUONG"));
			phieuKiemDinh.setSoLuongLayMau(rs.getString("SOLUONGLAYMAU"));
			phieuKiemDinh.setSoLuongMauLuu(rs.getString("SOLUONGMAULUU"));
			phieuKiemDinh.setSoLuongTheoDoiDoOnDinh(rs.getString("SLOLUONGONDINH"));
			phieuKiemDinh.setSoLuongConLai(rs.getString("CONLAI"));
			phieuKiemDinh.setThoiGianHuyMai(rs.getString("THOIGIANHUYMAU"));
			phieuKiemDinh.setSott(rs.getInt("STT"));
			getDsPhieuKiemDinhs().add(phieuKiemDinh);
		}
	}*/

	public boolean create(){
		
		
		String ngaytao = this.getDateTime();
		
		
		
		try{
			db.getConnection().setAutoCommit(false);
  
			String query = "INSERT ERP_KEHOACHLAYMAU(NGAYKEHOACH, DVTH_FK, SANPHAM_FK, DIENGIAI, NGUOITAO, NGAYTAO, NGUOISUA, NGAYSUA, CONGTY_FK, TRANGTHAI)"
					+ " VALUES('"+this.ngayKeHoach+"', '"+this.phongBanId+"', '"+this.sanphamId+"', N'"+this.dienGiai+"', '"+this.userId+"',"
					+ " '"+ngaytao+"', '"+this.userId+"',"
					+ " '"+ngaytao+"', '"+this.ctyId+"', 0)";
			
			System.out.println("_____query_1____: " + query);
			
			
			if(!db.update(query))
			{
				this.msg = "Không thể tạo ERP_KEHOACHLAYMAU lỗi: " + query;
				db.getConnection().rollback();
				return false;
			} 
			
			String currentId = "";//Lay PK_SEQ vua insert
			query = "select IDENT_CURRENT('ERP_KEHOACHLAYMAU') as Id";
		
			ResultSet rsLmkn = db.get(query);						
			if(rsLmkn.next())
			{
				currentId = rsLmkn.getString("Id");
				rsLmkn.close();
			}
 
			 for(PhieuKiemDinh pkd : getDsPhieuKiemDinhs()){
				if(pkd.getNgayDanhGia()!=null && pkd.getNgayDanhGia().length() > 0){
					query = "INSERT ERP_KEHOACHLAYMAU_CHITIET(SOTT, KEHOACHLAYMAU_FK, NGAYLAYMAU, NGAYDANHGIALAI, GHICHU, NGAYTHUCTE)"
							+ " VALUES("+pkd.getSott()+", '"+currentId+"', '"+pkd.getNgayDanhGia()+"', '"+pkd.getNgayDanhGiaLai()+"', "
							+ "'"+pkd.getGhiChu()+"', '"+pkd.getNgayThucTe()+"')";
					
					System.out.println("_____query_2____: " + query);
					if(!db.update(query))
					{
						this.msg = "Không thể tạo ERP_KEHOACHLAYMAU_CHITIET lỗi: " + query;
						db.getConnection().rollback();
						return false;
					}
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
		
		
		String ngay = this.getDateTime();
		
		
		
		try{
			db.getConnection().setAutoCommit(false);
  
			String query = "update ERP_KEHOACHLAYMAU set ngaykehoach='"+this.ngayKeHoach+"', dvth_fk='"+this.phongBanId+"',"
					+ " sanpham_fk='"+this.sanphamId+"', diengiai='"+this.dienGiai+"', "
					+ " nguoisua='"+this.userId+"', ngaysua='"+ngay+"' \r\n"
					+ " where pk_seq='"+this.id+"'";
			
			System.out.println("_____query_1____: " + query);
			
			
			if(!db.update(query))
			{
				this.msg = "Không thể tạo ERP_KEHOACHLAYMAU lỗi: " + query;
				db.getConnection().rollback();
				return false;
			} 
			
			query = "select kehoachlaymau_fk from ERP_KEHOACHLAYMAU_CHITIET where kehoachlaymau_fk='"+this.id+"'";
			System.out.println("_____query_2____: " + query);
			
			ResultSet rs = db.get(query);
			
			if(rs != null){
				query = "delete from ERP_KEHOACHLAYMAU_CHITIET where kehoachlaymau_fk='"+this.id+"'";
				System.out.println("_____query_2____: " + query);
				if(!db.update(query))
				{
					this.msg = "Không thể xóa ERP_KEHOACHLAYMAU_CHITIET lỗi: " + query;
					db.getConnection().rollback();
					return false;
				} 
			}
			
			for(PhieuKiemDinh pkd : getDsPhieuKiemDinhs()){
				if(pkd.getNgayDanhGia()!=null && pkd.getNgayDanhGia().length() > 0){
					query = "INSERT ERP_KEHOACHLAYMAU_CHITIET(SOTT, KEHOACHLAYMAU_FK, NGAYLAYMAU, NGAYDANHGIALAI, GHICHU, NGAYTHUCTE)"
							+ " VALUES("+pkd.getSott()+", '"+this.id+"', '"+pkd.getNgayDanhGia()+"', '"+pkd.getNgayDanhGiaLai()+"', '"+pkd.getGhiChu()+"', '"+pkd.getNgayThucTe()+"')";
					
					System.out.println("_____query_2____: " + query);
					if(!db.update(query))
					{
						this.msg = "Không thể tạo ERP_KEHOACHLAYMAU_CHITIET lỗi: " + query;
						db.getConnection().rollback();
						return false;
					}
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

 
	public String getPhongBanId() {
		 
		return this.phongBanId;
	}

 
	public void setPhongBanId(String phongBanId) {
		 this.phongBanId = phongBanId;
		
	}

	 
	public void init() {
		
		String query = "select PK_SEQ, NGAYKEHOACH, DVTH_FK, SANPHAM_FK, DIENGIAI, TRANGTHAI, NGUOITAO, NGAYTAO, NGUOISUA, NGAYSUA, CONGTY_FK\r\n" + 
				"from ERP_KEHOACHLAYMAU \r\n"
				+ " where 1=1";
		
		if(this.id != null && this.id.trim().length() > 0){
			query += " AND PK_SEQ = " + this.id;
		}
		
		ResultSet rs = db.get(query);
		try{
			if(rs.next()){
				this.phongBanId = rs.getString("DVTH_FK");
				this.sanphamId = rs.getString("SANPHAM_FK");
				this.dienGiai = rs.getString("DIENGIAI");
				this.ngayKeHoach = rs.getString("NGAYKEHOACH");
				this.trangthai = rs.getString("TRANGTHAI");
				System.out.println("aaaaaaaaaaaaaaaa: " + this.trangthai);
				initChitiet(this.id);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void initChitiet(String id2){
		
		String query = "select SOTT, KEHOACHLAYMAU_FK, NGAYLAYMAU, NGAYDANHGIALAI, GHICHU, NGAYTHUCTE\r\n" + 
				"from ERP_KEHOACHLAYMAU_CHITIET \r\n"
				+ " where 1=1 \r\n";
		
		if(id2 !=null && id2.trim().length() > 0){
			query += " AND KEHOACHLAYMAU_FK=" + id2 + " \r\n";
		}
		
		query += "order by SOTT asc";
		
		ResultSet rs = db.get(query);
		PhieuKiemDinh phieuKiemDinh = null;
		
		try {
			while(rs.next()){
				phieuKiemDinh = new PhieuKiemDinh();
				phieuKiemDinh.setSott(rs.getString("SOTT"));
				phieuKiemDinh.setNgayDanhGia(rs.getString("NGAYLAYMAU"));
				phieuKiemDinh.setNgayDanhGiaLai(rs.getString("NGAYDANHGIALAI"));
				phieuKiemDinh.setNgayThucTe(rs.getString("NGAYTHUCTE"));
				phieuKiemDinh.setGhiChu(rs.getString("GHICHU"));
				getDsPhieuKiemDinhs().add(phieuKiemDinh);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
 
	}
	
	public boolean chot(String Id){
		
		dbutils db = new dbutils();
		String msg = "";
		
		try
		{
			 
			
			String query = "update ERP_KEHOACHLAYMAU set TRANGTHAI=1 where pk_seq='"+Id+"'";
			
			System.out.println("lllllllloooooollllllll: " + query);
			
			if(!db.update(query))
			{
				msg = "Khong the chot: " + query;
				 
				return false;
			}
			
		 
			
		}catch(Exception e){
		 
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean bochot(String Id){
		
		dbutils db = new dbutils();
		String msg = "";
		
		try
		{
			 
			
			String query = "update ERP_KEHOACHLAYMAU set TRANGTHAI=0 where pk_seq='"+Id+"'";
			
			System.out.println("lllllllloooooollllllll: " + query);
			
			if(!db.update(query))
			{
				msg = "Khong the bo chot: " + query;
				 
				return false;
			}
			
		 
			
		}catch(Exception e){
		 
			e.printStackTrace();
			return false;
		}
		return true;
	}
 
	public boolean xoa(String Id){
		
		dbutils db = new dbutils();
		String msg = "";
		
		try
		{
			 
			
			String query = "update ERP_KEHOACHLAYMAU set TRANGTHAI=2 where pk_seq='"+Id+"'";
			
			System.out.println("lllllllloooooollllllll: " + query);
			
			if(!db.update(query))
			{
				msg = "Khong the xoa: " + query;
				 
				return false;
			}
			
		 
			
		}catch(Exception e){
		 
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
