package geso.traphaco.erp.beans.dinhtinheo.imp;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.dinhtinheo.IDinhTinhEO;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DinhTinhEO  implements IDinhTinhEO{
	
	String Id;
	String userId;
	String msg;
	String khoId;
	String sanPham;
	String loai;
	String trangThai;
	String dienGiai;
	String ngayChungTu;
	
	String nguoiTao;
	String nguoiSua;
	String ngayTao;
	String ngaySua;
	dbutils db ;
	
	ResultSet rsSanPham;
	ResultSet rsKho;
	
	List<DinhTinhEODetail> listDetail = new ArrayList<DinhTinhEODetail>();
	
	public DinhTinhEO() {
		super();
		this.userId = "";
		this.msg = "";
		this.khoId = "";
		this.sanPham = "";
		this.loai = "";
		this.trangThai = "";
		this.dienGiai = "";
		this.nguoiTao = "";
		this.nguoiSua = "";
		this.ngayTao = "";
		this.ngaySua = "";
		this.db = new dbutils();
		this.Id = "";
		this.ngayChungTu = "";
	}
	
	public DinhTinhEO(String userId, String msg, String khoId, String sanPham, String loai, String trangThai, String dienGiai, String nguoiTao,
			String nguoiSua, String ngayTao, String ngaySua, dbutils db, List<DinhTinhEODetail> listDetail) {
		super();
		this.userId = userId;
		this.msg = msg;
		this.khoId = khoId;
		this.sanPham = sanPham;
		this.loai = loai;
		this.trangThai = trangThai;
		this.dienGiai = dienGiai;
		this.nguoiTao = nguoiTao;
		this.nguoiSua = nguoiSua;
		this.ngayTao = ngayTao;
		this.ngaySua = ngaySua;
		this.db = db;
		this.listDetail = listDetail;
		this.Id = "";
		this.ngayChungTu = "";
	}
	
	public String getNgayChungTu() {
		return ngayChungTu;
	}

	public void setNgayChungTu(String ngayChungTu) {
		this.ngayChungTu = ngayChungTu;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}
	
	public ResultSet getRsSanPham() {
		return rsSanPham;
	}
	
	public void setRsSanPham(ResultSet rsSanPham) {
		this.rsSanPham = rsSanPham;
	}

	public ResultSet getRsKho() {
		return rsKho;
	}

	public void setRsKho(ResultSet rsKho) {
		this.rsKho = rsKho;
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
	public String getKhoId() {
		return khoId;
	}
	public void setKhoId(String khoId) {
		this.khoId = khoId;
	}
	public String getSanPham() {
		return sanPham;
	}
	public void setSanPham(String sanPham) {
		this.sanPham = sanPham;
	}
	public String getLoai() {
		return loai;
	}
	public void setLoai(String loai) {
		this.loai = loai;
	}
	public String getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}
	public String getDienGiai() {
		return dienGiai;
	}
	public void setDienGiai(String dienGiai) {
		this.dienGiai = dienGiai;
	}
	public String getNguoiTao() {
		return nguoiTao;
	}
	public void setNguoiTao(String nguoiTao) {
		this.nguoiTao = nguoiTao;
	}
	public String getNguoiSua() {
		return nguoiSua;
	}
	public void setNguoiSua(String nguoiSua) {
		this.nguoiSua = nguoiSua;
	}
	public String getNgayTao() {
		return ngayTao;
	}
	public void setNgayTao(String ngayTao) {
		this.ngayTao = ngayTao;
	}
	public String getNgaySua() {
		return ngaySua;
	}
	public void setNgaySua(String ngaySua) {
		this.ngaySua = ngaySua;
	}
	public dbutils getDb() {
		return db;
	}
	public void setDb(dbutils db) {
		this.db = db;
	}
	public List<DinhTinhEODetail> getListDetail() {
		return listDetail;
	}
	public void setListDetail(List<DinhTinhEODetail> listDetail) {
		this.listDetail = listDetail;
	}
	private void createRsKho(){
		String query = " select PK_SEQ, MA + ', ' + TEN as TEN from ERP_KHOTT where TRANGTHAI =1";
		this.rsKho = this.db.get(query);
		
	}
	private void createRsSanPham(){
		String query = " select PK_SEQ, MA + ', ' + TEN as TEN from ERP_SANPHAM where TRANGTHAI =1 " +
					   " and (isnull(KIEMTRAOE,0) = 1 )";
		this.rsSanPham = this.db.get(query);
	}
	
	// lấy chi tiết sản phẩm để chỉnh sửa
	private void getDetailEO()
	{
		try
		{
			if(this.Id.trim().length() == 0)
			{
				this.Id = "0";
			}
			
			String query =  "  select ct.PK_SEQ, ct.KHOTT_FK, SANPHAM_FK, sp.MA as MASP, sp.TEN as TENSP, NGAYHETHAN " +
					        " , SOLO, BIN_FK,NGAYSANXUAT, NGAYNHAPKHO, MARQ, HAMLUONG,HAMAM, MAME, MATHUNG, MAPHIEU, bin.MA as MABIN, " +
					        " isnull(PHIEUEO,'') as PHIEUEO, isnull(MAPHIEUDINHTINH,'') as MAPHIEUDINHTINH, " +
					        " ISNULL( (select MAPHIEUDINHTINHNEW from ERP_DINHTINH_EO_DETAILS " +
					        " where KHOCHITIET_FK = ct.PK_SEQ  and DINHTINH_FK = "+ this.Id +"  ),'') as phieudinhtinhnew, " +
					        " ISNULL( (select PHIEUEONEW from ERP_DINHTINH_EO_DETAILS " +
					        " where KHOCHITIET_FK = ct.PK_SEQ and DINHTINH_FK = "+ this.Id +" ),'') " +
					        " as phieueonew, " +
					        " isnull((select Ma from ERP_NHASANXUAT where PK_SEQ = ct.nsx_fk),'') as nsxma, isnull(ct.nsx_fk, 0) as nsx_fk " +
					        " from ERP_KHOTT_SP_CHITIET ct inner join ERP_SANPHAM sp on ct.SANPHAM_FK = sp.PK_SEQ " +
					        " left join ERP_BIN bin on ct.BIN_FK = bin.PK_SEQ " +
					        " where ct.AVAILABLE >0  and ct.KHOTT_FK = "+this.khoId + "and SANPHAM_FK in ("+this.sanPham+")";
			
			query += " order by SOLO, MAPHIEU, dbo.ftConvertToNumber(MAME), dbo.ftConvertToNumber(MATHUNG) ";
			
			ResultSet rs = this.db.get(query);
			System.out.println("[DINHTINH EO] detail:"+ query);
			List<DinhTinhEODetail> list = new ArrayList<DinhTinhEODetail>();
			if(rs!=null)
			{
				while(rs.next())
				{
					DinhTinhEODetail detail = new DinhTinhEODetail();
					detail.setKhott_fk( this.khoId );
					detail.setSanPham_fk(rs.getString("SANPHAM_FK"));
					detail.setNgayHetHan(rs.getString("NGAYHETHAN"));
					detail.setSoLo(rs.getString("SOLO"));
					detail.setBin_fk(rs.getString("MABIN"));
					detail.setNgaySanXuat(rs.getString("NGAYSANXUAT"));
					detail.setNgayNhapKho(rs.getString("NGAYNHAPKHO"));
					detail.setMaRQ(rs.getString("MARQ"));
					detail.setHamLuong(rs.getDouble("HAMLUONG"));
					detail.setHamAm(rs.getDouble("HAMAM"));
					detail.setMaMe(rs.getString("MAME"));
					detail.setMaThung(rs.getString("MATHUNG"));
					detail.setMaPhieu(rs.getString("MAPHIEU"));
					detail.setMaPhieuEO(rs.getString("PHIEUEO"));
					detail.setMaPhieuDinhTinh(rs.getString("MAPHIEUDINHTINH"));
					detail.setMaSanPham(rs.getString("MASP"));
					detail.setTenSanPham(rs.getString("TENSP"));
					detail.setMaPhieuDinhTinhNew(rs.getString("phieudinhtinhnew"));
					detail.setMaPhieuEONew(rs.getString("phieueonew"));
					detail.setKhoChiTiet_fk( rs.getString("PK_SEQ") == null ? "" : rs.getString("PK_SEQ") );
					
					detail.setNsxMa(rs.getString("nsxma"));
					detail.setNsx_fk(rs.getString("nsx_fk"));
					list.add(detail);
				}
			}
			// gan lai = rong
			if(this.Id.equals("0")){
				this.Id = "";
			}
			
			this.listDetail = list;
		} 
		catch( Exception ex){
			ex.printStackTrace();
		}
	}
	
	private void getDetailEODislay()
	{
		try
		{
			String query =  "  select ct.PK_SEQ, dt.KHO_FK as khott_fk, dt.SANPHAM_FK, sp.MA as MASP, sp.TEN as TENSP, a.NGAYHETHAN " +
					        " , a.SOLO, a.BIN_FK, '' NGAYSANXUAT, a.NGAYNHAPKHO, a.MARQ, a.HAMLUONG, a.HAMAM, a.MAME, a.MATHUNG, a.MAPHIEU, bin.MA as MABIN," +
					        " isnull(a.PHIEUEO, '') as PHIEUEO, isnull(a.phieudt,'') as MAPHIEUDINHTINH, " +
					        " ISNULL( a.MAPHIEUDINHTINHNEW ,'') as phieudinhtinhnew, " +
					        " ISNULL( a.PHIEUEONEW ,'') as phieueonew, " +
					        " isnull((select Ma from ERP_NHASANXUAT where PK_SEQ = a.nsx_fk),'') as nsxma, isnull(a.nsx_fk, 0) as nsx_fk " +
					        " from ERP_DINHTINH_EO_DETAILS a left join   ERP_KHOTT_SP_CHITIET ct on a.KHOCHITIET_FK= ct.PK_SEQ " +
					        " 	inner join ERP_DINHTINH_EO dt on a.dinhtinh_fk = dt.pk_seq " +
					        " 	inner join ERP_SANPHAM sp on dt.SANPHAM_FK = sp.PK_SEQ " +
					        " 	left join ERP_BIN bin on a.BIN_FK = bin.PK_SEQ " +
					        " where a.DINHTINH_FK = "+ this.Id;
			
			System.out.println("::: INIT DATA:  " + query);
			ResultSet rs = this.db.get(query);
			List<DinhTinhEODetail> list = new ArrayList<DinhTinhEODetail>();
			if(rs!=null)
			{
				while(rs.next())
				{
					DinhTinhEODetail detail = new DinhTinhEODetail();
					detail.setKhott_fk( this.khoId );
					detail.setSanPham_fk(rs.getString("SANPHAM_FK"));
					detail.setNgayHetHan(rs.getString("NGAYHETHAN"));
					detail.setSoLo(rs.getString("SOLO"));
					detail.setBin_fk(rs.getString("MABIN"));
					detail.setNgaySanXuat(rs.getString("NGAYSANXUAT"));
					detail.setNgayNhapKho(rs.getString("NGAYNHAPKHO"));
					detail.setMaRQ(rs.getString("MARQ"));
					detail.setHamLuong(rs.getDouble("HAMLUONG"));
					detail.setHamAm(rs.getDouble("HAMAM"));
					detail.setMaMe(rs.getString("MAME"));
					detail.setMaThung(rs.getString("MATHUNG"));
					detail.setMaPhieu(rs.getString("MAPHIEU"));
					detail.setMaPhieuEO(rs.getString("PHIEUEO"));
					detail.setMaPhieuDinhTinh(rs.getString("MAPHIEUDINHTINH"));
					detail.setMaSanPham(rs.getString("MASP"));
					detail.setTenSanPham(rs.getString("TENSP"));
					detail.setMaPhieuDinhTinhNew(rs.getString("phieudinhtinhnew"));
					detail.setMaPhieuEONew(rs.getString("phieueonew"));
					detail.setKhoChiTiet_fk( rs.getString("PK_SEQ") == null ? "" : rs.getString("PK_SEQ") );
					
					detail.setNsxMa(rs.getString("nsxma"));
					detail.setNsx_fk(rs.getString("nsx_fk"));
					list.add(detail);
				}
			}
			
			this.listDetail = list;
		} 
		catch( Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void createRs()
	{
		this.createRsKho();
		this.createRsSanPham();
		
		if(this.sanPham.trim().length() >0 && this.khoId.trim().length() >0){
			this.getDetailEO();
		}
	}
	
	// Phương thức dành cho việc update
	public void init(String query)
	{
		try
		{
		String sql = " select PK_SEQ, DIENGIAI, KHO_FK, SANPHAM_FK, LOAI, NGAYCHUNGTU " +
				     " from ERP_DINHTINH_EO where PK_SEQ =  "+ this.Id;
		ResultSet rs = this.db.get(sql);
		if(rs!= null){
			if(rs.next()){
				this.dienGiai = rs.getString("DIENGIAI");
				this.khoId = rs.getString("KHO_FK");
				this.sanPham = rs.getString("SANPHAM_FK");
				this.ngayChungTu = rs.getString("NGAYCHUNGTU");
				this.loai = rs.getString("LOAI");
			}
		}
		} catch(Exception ex){
			ex.printStackTrace();
		}
		// gọi phương thức khởi tạo các Rs
		this.createRs();
	}
	
	// dành cho việc hiển thị.
	public void initView()
	{
		try
		{
			String sql = " select PK_SEQ, DIENGIAI, KHO_FK, SANPHAM_FK, LOAI, NGAYCHUNGTU " +
					     " from ERP_DINHTINH_EO where PK_SEQ =  "+ this.Id;
			ResultSet rs = this.db.get(sql);
			if(rs!= null)
			{
				if(rs.next())
				{
					this.dienGiai = rs.getString("DIENGIAI");
					this.khoId = rs.getString("KHO_FK");
					this.sanPham = rs.getString("SANPHAM_FK");
					this.ngayChungTu = rs.getString("NGAYCHUNGTU");
					this.loai = rs.getString("LOAI");
				}
			}
			} catch(Exception ex){
				ex.printStackTrace();
			}
			
			// gọi phương thức khởi tạo các Rs
			this.createRsKho();
			this.createRsSanPham();
			// lấy các record được lưu.
			this.getDetailEODislay();
	}
	
	public void DBclose(){
		this.db.shutDown();
	}
	
	
	public boolean create()
	{
		try
		{
			this.nguoiTao = this.userId;
			this.nguoiSua = this.userId;
			this.trangThai = "0";
			if(this.ngayChungTu.trim().length()==0)
			{
				this.msg = "Vui lòng chọn ngày chứng từ";
				return false;
			}
			this.db.getConnection().setAutoCommit(false);
			
			// insert ERP_DINHTINH_EO
			String sql = " insert into ERP_DINHTINH_EO(DIENGIAI, KHO_FK, TRANGTHAI, NGUOITAO, NGUOISUA, NGAYTAO, NGAYSUA, SANPHAM_FK, LOAI, NGAYCHUNGTU) " + 
						 " values(N'" + this.dienGiai + "', '" + this.khoId + "', '" + this.trangThai + "', '" + this.nguoiTao + "', '" + this.nguoiSua + "', '" + this.ngayTao + "', '" + this.ngaySua + "', '" + this.sanPham + "', '" + this.loai + "', '" + this.ngayChungTu + "')";
			
			System.out.println(":: SQL: " + sql);
			if( db.updateReturnInt(sql) < 1 )
			{
				this.msg = "Cập nhật thất bại.";
				this.db.getConnection().rollback();
				return false;
			}
			
			// get id inserted
			String idCurrent = "";
			sql = "SELECT IDENT_CURRENT('ERP_DINHTINH_EO') as id";
			ResultSet rs = this.db.get(sql);
			rs.next();
			idCurrent = rs.getString("id");
			this.Id = idCurrent;
			rs.close();
			
			// insert ERP_DINHTIN_EO_DETAIL
			for(int i=0; i< this.listDetail.size(); i++)
			{
				DinhTinhEODetail detail = this.listDetail.get(i);
				
				//Check Unicode
				if( Utility.checkUnicode( new String[]{ detail.getMaPhieuDinhTinhNew().replaceAll("	", ""), detail.getMaPhieuEONew().replaceAll("	", "") } ) )
				{
					this.msg = "Thông tin bạn nhập đang có dấu. Vui lòng kiểm tra lại";
					db.getConnection().rollback();
					return false;
				}
				
				//0 dinh tinh, 1 phieu EO
				sql = " insert into ERP_DINHTINH_EO_DETAILS ( DINHTINH_FK, MAPHIEUDINHTINHNEW, PHIEUEONEW, KHOCHITIET_FK, SOLUONG ) " +
					  " select '" + this.Id + "', '" + detail.getMaPhieuDinhTinhNew().replaceAll("	", "") + "', '" + detail.getMaPhieuEONew().replaceAll("	", "") + "', '" + detail.getKhoChiTiet_fk() + "', SOLUONG " + 
					  " from ERP_KHOTT_SP_CHITIET where pk_seq = '" + detail.getKhoChiTiet_fk() + "' ";
				
				System.out.println(":: SQL: " + sql);
				if (db.updateReturnInt(sql) != 1 ) 
				{
					this.msg = "Cập nhật thất bại chi tiết.";
					this.db.getConnection().rollback();
					return false;
				}
			}
			
			//Lưu lại thông tin kho chi tiết
			sql = " update a "+ 
					 " 	set a.solo = b.solo, a.ngayhethan = b.NGAYHETHAN, a.ngaynhapkho = b.ngaynhapkho, "+ 
					 " 		a.marq = b.marq, a.hamam = b.HAMAM, a.hamluong = b.HAMLUONG, a.MAME = b.MAME, a.MATHUNG = b.MATHUNG, a.MAPHIEU = b.MAPHIEU,"+ 
					 " 		a.bin_fk = b.BIN_FK, a.phieudt = b.maphieudinhtinh, a.phieueo = b.phieueo, a.nsx_fk = b.nsx_fk "+ 
					 " from ERP_DINHTINH_EO_DETAILS a inner join ERP_KHOTT_SP_CHITIET b on a.khochitiet_fk = b.pk_seq"+ 
					 " where a.DINHTINH_FK = '" + this.Id + "'";
			
			System.out.println(":: SQL: " + sql);
			if (db.updateReturnInt(sql) < 1 ) 
			{
				this.msg = "Cập nhật thất bại chi tiết.";
				this.db.getConnection().rollback();
				return false;
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
			return true;
		
		} 
		catch(Exception ex)
		{
			ex.printStackTrace();
			try 
			{
				this.db.getConnection().rollback();
			} catch (Exception e) { }
			
			this.msg = ex.getMessage();
			return false;
		}

	}
	
	public boolean update()
	{
		PreparedStatement pre  = null;
		try
		{
			this.nguoiTao = this.userId;
			this.nguoiSua = this.userId;
			this.trangThai = "0";
			if(this.ngayChungTu.trim().length()==0)
			{
				this.msg = "Vui lòng chọn ngày chứng từ";
				return false;
			}
			this.db.getConnection().setAutoCommit(false);
			
			// insert ERP_DINHTINH_EO
			String sql = " update ERP_DINHTINH_EO set DIENGIAI = ?, KHO_FK = ?, NGUOISUA =?, NGAYSUA =?, " +
						 " SANPHAM_FK = ?, LOAI = ?, NGAYCHUNGTU =? where PK_SEQ=? ";
			pre = this.db.getConnection().prepareStatement(sql);
			pre.setString(1, this.dienGiai);
			pre.setInt(2, Integer.parseInt(this.khoId));
			pre.setInt(3, Integer.parseInt(this.nguoiSua));
			pre.setString(4, this.ngaySua);
			pre.setString(5, this.sanPham);
			pre.setString(6, this.loai);
			pre.setString(7, this.ngayChungTu);
			pre.setInt(8, Integer.parseInt(this.Id));
			int k = pre.executeUpdate();
			if( k !=1)
			{
				this.msg = "Cập nhật thất bại.";
				this.db.getConnection().rollback();
				return false;
			}

			// get id inserted
			sql = "delete from ERP_DINHTINH_EO_DETAILS where DINHTINH_FK ="+ this.Id;
			k = this.db.updateReturnInt(sql);
			if(k == 0){
				this.msg = "Cập nhật thất bại Error 1.";
				this.db.getConnection().rollback();
				return false;
			}

			for(int i=0; i< this.listDetail.size(); i++)
			{
				DinhTinhEODetail detail = this.listDetail.get(i);

				//Check Unicode
				if( Utility.checkUnicode( new String[]{ detail.getMaPhieuDinhTinhNew().replaceAll("	", ""), detail.getMaPhieuEONew().replaceAll("	", "") } ) )
				{
					this.msg = "Thông tin bạn nhập đang có dấu. Vui lòng kiểm tra lại";
					db.getConnection().rollback();
					return false;
				}
				
				//0 dinh tinh, 1 phieu EO
				sql = " insert into ERP_DINHTINH_EO_DETAILS ( DINHTINH_FK, MAPHIEUDINHTINHNEW, PHIEUEONEW, KHOCHITIET_FK, SOLUONG ) " +
					  " select '" + this.Id + "', '" + detail.getMaPhieuDinhTinhNew().replaceAll("	", "") + "', '" + detail.getMaPhieuEONew().replaceAll("	", "") + "', '" + detail.getKhoChiTiet_fk() + "', SOLUONG " + 
					  " from ERP_KHOTT_SP_CHITIET where pk_seq = '" + detail.getKhoChiTiet_fk() + "' ";
				
				if (db.updateReturnInt(sql) != 1 ) 
				{
					this.msg = "Cập nhật thất bại chi tiết.";
					this.db.getConnection().rollback();
					return false;
				}
			}

			//Lưu lại thông tin kho chi tiết
			sql = " update a "+ 
					" 	set a.solo = b.solo, a.ngayhethan = b.NGAYHETHAN, a.ngaynhapkho = b.ngaynhapkho, "+ 
					" 		a.marq = b.marq, a.hamam = b.HAMAM, a.hamluong = b.HAMLUONG, a.MAME = b.MAME, a.MATHUNG = b.MATHUNG, a.MAPHIEU = b.MAPHIEU,"+ 
					" 		a.bin_fk = b.BIN_FK, a.phieudt = b.maphieudinhtinh, a.phieueo = b.phieueo, a.nsx_fk = b.nsx_fk "+ 
					" from ERP_DINHTINH_EO_DETAILS a inner join ERP_KHOTT_SP_CHITIET b on a.khochitiet_fk = b.pk_seq"+ 
					" where a.DINHTINH_FK = '" + this.Id + "'";
			
			if (db.updateReturnInt(sql) < 1 ) 
			{
				this.msg = "Cập nhật thất bại chi tiết.";
				this.db.getConnection().rollback();
				return false;
			}

			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
			return true;

		} 
		catch(Exception ex)
		{
			ex.printStackTrace();
			try 
			{
				this.db.getConnection().rollback();
			} catch (SQLException e) { }

			this.msg = ex.getMessage();
			return false;
		}
	}
	
	public boolean Chot()
	{
		try
		{
			this.db.getConnection().setAutoCommit(false);

			// cập nhật 
			String sql = "update ERP_DINHTINH_EO set TRANGTHAI =1 where PK_SEQ = '" + this.Id + "' and trangthai=0";
			if(db.updateReturnInt(sql)!=1)
			{
				this.msg = "Cập nhật thất bại Error 1.";
				this.db.getConnection().rollback();
				return false;
			}
			
			//Delete những dòng không nhập đi
			sql = "delete ERP_DINHTINH_EO_DETAILS where dinhtinh_fk = '" + this.Id + "' and maphieudinhtinhNEW = '' and phieuEONEW = '' ";
			if(!db.update(sql))
			{
				this.msg = "Cập nhật thất bại Error 1.";
				this.db.getConnection().rollback();
				return false;
			}
			
			//Trừ kho
			//CẬP NHẬT KHO ==> mấy nữa cải tiến booked tính động, không lấy trong kho
			Utility util = new Utility();
			
			sql =  " update a set a.soluong = c.SoLuong	"+ 
					 "   from ERP_DINHTINH_EO_DETAILS a inner join ERP_DINHTINH_EO b on a.DINHTINH_FK = b.PK_SEQ   "+ 
					 "   	  inner join ERP_KHOTT_SP_CHITIET c on b.kho_fk = c.KHOTT_FK and  ISNULL(a.bin_fk, 0) = ISNULL( c.BIN_FK, 0 ) and b.sanpham_fk = c.SANPHAM_FK   "+ 
					 "   		and a.solo = c.SOLO and a.ngayhethan = c.NGAYHETHAN and a.ngaynhapkho = c.NGAYNHAPKHO  "+ 
					 "   		and isnull(a.mame, '') = isnull(c.MAME, '') and isnull(a.mathung, '') = isnull(c.MATHUNG, '')  "+ 
					 "   		and isnull(a.hamluong, 100) = isnull(c.hamluong, 100) and isnull(a.hamam, 0) = isnull(c.hamam, 0)  "+ 
					 "   		and isnull(a.maphieu, '') = isnull(c.maphieu, '') and isnull(a.marq, '') = isnull(c.marq, '')  "+ 
					 "   		and isnull(a.phieudt, '') = isnull(c.maphieudinhtinh, '') and isnull(a.phieueo, '') = isnull(c.phieueo, '')  "+ 
					 "			and isnull(a.nsx_fk, 0) = isnull(c.nsx_fk, 0) "	+
					 "   where b.PK_SEQ = '" + this.Id + "' and ( maphieudinhtinhNEW != '' or phieuEONEW != '' ) ";
			
			System.out.println("::: CAP NHAT SO LUONG: " + sql);
			if( db.updateReturnInt(sql) < 1 )
			{
				this.msg = "Lỗi cập nhật: " + sql;
				db.getConnection().rollback();
				db.shutDown();
				return false;
			}
			
			sql =    " select  b.ngaychungtu as NGAYDIEUCHINH, b.kho_fk as Khott_FK, b.SanPham_fk, ISNULL( ( select ten from ERP_SANPHAM where pk_seq = b.sanpham_fk ), '') as tensp, "+ 
					 " 		a.SoLo, a.NgayHetHan, a.ngaynhapkho,    "+ 
					 "   	a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, a.phieudt, a.phieueo, a.SoLuong,  "+ 
					 "   			c.SOLUONG as tonkho, c.BOOKED, c.AVAILABLE,  "+ 
					 " 			b.loai as loaidieuchinh, a.maphieudinhtinhNEW, a.phieuEONEW, a.KHOCHITIET_FK, isnull(a.nsx_fk, 0) as nsx_fk	"+ 
					 "   from ERP_DINHTINH_EO_DETAILS a inner join ERP_DINHTINH_EO b on a.DINHTINH_FK = b.PK_SEQ   "+ 
					 "   	  inner join ERP_KHOTT_SP_CHITIET c on b.kho_fk = c.KHOTT_FK and  ISNULL(a.bin_fk, 0) = ISNULL( c.BIN_FK, 0 ) and b.sanpham_fk = c.SANPHAM_FK   "+ 
					 "   		and a.solo = c.SOLO and a.ngayhethan = c.NGAYHETHAN and a.ngaynhapkho = c.NGAYNHAPKHO  "+ 
					 "   		and isnull(a.mame, '') = isnull(c.MAME, '') and isnull(a.mathung, '') = isnull(c.MATHUNG, '')  "+ 
					 "   		and isnull(a.hamluong, 100) = isnull(c.hamluong, 100) and isnull(a.hamam, 0) = isnull(c.hamam, 0)  "+ 
					 "   		and isnull(a.maphieu, '') = isnull(c.maphieu, '') and isnull(a.marq, '') = isnull(c.marq, '')  "+ 
					 "   		and isnull(a.phieudt, '') = isnull(c.maphieudinhtinh, '') and isnull(a.phieueo, '') = isnull(c.phieueo, '')  "+
					 "			and isnull(a.nsx_fk, 0) = isnull(c.nsx_fk, 0) " +
					 "   where b.PK_SEQ = '" + this.Id + "' and ( maphieudinhtinhNEW != '' or phieuEONEW != '' ) ";
			
			System.out.println("::: CAP NHAT KHO: " + sql);
			ResultSet rs = db.get(sql);
			if( rs != null )
			{
				while( rs.next() )
				{
					String khoId = rs.getString("Khott_FK");
					String spId = rs.getString("SanPham_fk");
					String solo = rs.getString("SoLo");
					String ngaydieuchinh = rs.getString("NGAYDIEUCHINH");
					
					String ngayhethan = rs.getString("NgayHetHan");
					
					String ngaynhapkhoGIAM = rs.getString("ngaynhapkho");
					String ngaynhapkhoTANG = ngaydieuchinh;
					
					String loaidoituong = "";
					String doituongId = "";
					
					String mame = rs.getString("mame");
					String mathung = rs.getString("mathung");
					String bin_fk = rs.getString("bin_fk");
					
					String maphieu = rs.getString("maphieu");
					String phieudt = rs.getString("phieudt");
					String phieueo = rs.getString("phieueo");
					
					String marq = rs.getString("marq");
					String hamluong = rs.getString("hamluong");
					String hamam = rs.getString("hamam");
					
					double soluong = rs.getDouble("soluong");
					double tonkho = rs.getDouble("tonkho");
					double booked = rs.getDouble("BOOKED");
					double avai = rs.getDouble("AVAILABLE");
					
					String nsx_fk = rs.getString("nsx_fk");
					
					//Tính ra số lượng cần điều chỉnh, ở đây là điều chỉnh cột số lượng tỏng kho
					if( booked != 0 || soluong != avai )
					{
						msg = "Sản phẩm ( " + rs.getString("tensp") + " ) đã phát sinh số liệu. Bạn không thể thực hiện nghiệp vụ này. ";
						db.getConnection().rollback();
						db.shutDown();
						return false;
					}

					//Giảm kho chuyển
					msg = util.Update_KhoTT_MOI(rs.getString("NGAYDIEUCHINH"), "Chốt định tính EO  ID:"+this.Id, db, khoId, spId, solo, ngayhethan, ngaynhapkhoGIAM, 
							mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, -1 * soluong, 0, -1 * soluong, nsx_fk);
					if( msg.trim().length() > 0 )
					{
						db.getConnection().rollback();
						db.shutDown();
						return false;
					}
					
					//Tăng kho nhận 0  dinh tinh, 1 EO
					String phieuDTMoi = "";
					String phieuEOMoi = "";
					
					if( rs.getString("loaidieuchinh").equals("0") ) //DINH TINH
					{
						phieuDTMoi = rs.getString("maphieudinhtinhNEW");
						
						msg = util.Update_KhoTT_MOI(rs.getString("NGAYDIEUCHINH"), "Chốt định tính / EO ID:"+this.Id, db, khoId, spId, solo, ngayhethan, ngaynhapkhoTANG, 
										mame, mathung, bin_fk, maphieu, phieuDTMoi, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, +1 * soluong, 0, +1 * soluong, nsx_fk);
					}
					else //EO
					{
						phieuEOMoi = rs.getString("phieuEONEW");
						
						msg = util.Update_KhoTT_MOI(rs.getString("NGAYDIEUCHINH"), "Chốt định tính / EO  ID:"+this.Id, db, khoId, spId, solo, ngayhethan, ngaynhapkhoTANG, 
										mame, mathung, bin_fk, maphieu, phieudt, phieuEOMoi, marq, hamluong, hamam, loaidoituong, doituongId, +1 * soluong, 0, +1 * soluong, nsx_fk);
					}
						
					if( msg.trim().length() > 0 )
					{
						this.msg = "Lỗi cập nhật: " + sql;
						db.getConnection().rollback();
						db.shutDown();
						return false;
					}
				}
				rs.close();
			}
			
			sql =  " update b  "+
					 " 	set b.ngaynhapkhoTANG = a.ngaychungtu "+
					 " from ERP_DINHTINH_EO a inner join ERP_DINHTINH_EO_DETAILS b on a.PK_SEQ = b.dinhtinh_fk "+
					 " 		left join ERP_BIN bin on b.bin_fk = bin.pk_seq "+
					 " where a.TRANGTHAI = '1' and b.dinhtinh_fk = '" + this.Id + "'  ";
			
			if( db.updateReturnInt(sql) < 1 )
			{
				this.msg = "Lỗi cập nhật: " + sql;
				db.getConnection().rollback();
				db.shutDown();
				return false;
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
			return true;
		} 
		catch(Exception ex)
		{
			ex.printStackTrace();
			try {
				this.db.getConnection().rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			this.msg = ex.getMessage();
			return false;
		}
	}
	
	public boolean MoChot()
	{
		try
		{
			this.db.getConnection().setAutoCommit(false);

			// cập nhật 
			String sql = "update ERP_DINHTINH_EO set TRANGTHAI = 0 where PK_SEQ = '" + this.Id + "' ";
			if(!db.update(sql))
			{
				this.msg = "Cập nhật thất bại Error 1.";
				this.db.getConnection().rollback();
				return false;
			}
			
			//Trừ kho
			//CẬP NHẬT KHO ==> mấy nữa cải tiến booked tính động, không lấy trong kho
			Utility util = new Utility();
			
			sql =    " select  b.ngaychungtu as NGAYDIEUCHINH, b.kho_fk as Khott_FK, b.SanPham_fk, ISNULL( ( select ten from ERP_SANPHAM where pk_seq = b.sanpham_fk ), '') as tensp, "+ 
					 " 		a.SoLo, a.NgayHetHan, a.ngaynhapkho,    "+ 
					 "   	a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, a.phieudt, a.phieueo, a.SoLuong,  "+ 
					 "   			c.SOLUONG as tonkho, c.BOOKED, c.AVAILABLE,  "+ 
					 " 			b.loai as loaidieuchinh, a.maphieudinhtinhNEW, a.phieuEONEW, a.KHOCHITIET_FK	"+ 
					 "   from ERP_DINHTINH_EO_DETAILS a inner join ERP_DINHTINH_EO b on a.DINHTINH_FK = b.PK_SEQ   "+ 
					 "   	  inner join ERP_KHOTT_SP_CHITIET c on b.kho_fk = c.KHOTT_FK and  ISNULL(a.bin_fk, 0) = ISNULL( c.BIN_FK, 0 ) and b.sanpham_fk = c.SANPHAM_FK   "+ 
					 "   		and a.solo = c.SOLO and a.ngayhethan = c.NGAYHETHAN and a.ngaynhapkho = c.NGAYNHAPKHO  "+ 
					 "   		and isnull(a.mame, '') = isnull(c.MAME, '') and isnull(a.mathung, '') = isnull(c.MATHUNG, '')  "+ 
					 "   		and isnull(a.hamluong, 100) = isnull(c.hamluong, 100) and isnull(a.hamam, 0) = isnull(c.hamam, 0)  "+ 
					 "   		and isnull(a.maphieu, '') = isnull(c.maphieu, '') and isnull(a.marq, '') = isnull(c.marq, '')  "+ 
					 "   		and isnull(a.phieudt, '') = isnull(c.maphieudinhtinh, '') and isnull(a.phieueo, '') = isnull(c.phieueo, '')  "+ 
					 "   where b.PK_SEQ = '" + this.Id + "' and ( maphieudinhtinhNEW != '' or phieuEONEW != '' ) ";
			
			System.out.println("::: CAP NHAT KHO: " + sql);
			ResultSet rs = db.get(sql);
			if( rs != null )
			{
				while( rs.next() )
				{
					String khoId = rs.getString("Khott_FK");
					String spId = rs.getString("SanPham_fk");
					String solo = rs.getString("SoLo");
					String ngaydieuchinh = rs.getString("NGAYDIEUCHINH");
					
					String ngayhethan = rs.getString("NgayHetHan");
					
					String ngaynhapkhoGIAM = rs.getString("ngaynhapkho");
					String ngaynhapkhoTANG = ngaydieuchinh;
					
					String loaidoituong = "";
					String doituongId = "";
					
					String mame = rs.getString("mame");
					String mathung = rs.getString("mathung");
					String bin_fk = rs.getString("bin_fk");
					
					String maphieu = rs.getString("maphieu");
					String phieudt = rs.getString("phieudt");
					String phieueo = rs.getString("phieueo");
					
					String marq = rs.getString("marq");
					String hamluong = rs.getString("hamluong");
					String hamam = rs.getString("hamam");
					
					double soluong = rs.getDouble("soluong");
					double tonkho = rs.getDouble("tonkho");
					double booked = rs.getDouble("BOOKED");
					double avai = rs.getDouble("AVAILABLE");
					
					//Tính ra số lượng cần điều chỉnh, ở đây là điều chỉnh cột số lượng tỏng kho
					if( booked != 0 || soluong != avai )
					{
						msg = "Sản phẩm ( " + rs.getString("tensp") + " ) đã phát sinh số liệu. Bạn không thể thực hiện nghiệp vụ này. ";
						db.getConnection().rollback();
						db.shutDown();
						return false;
					}

					//Giảm kho chuyển
					msg = util.Update_KhoTT(rs.getString("NGAYDIEUCHINH"), "Chốt định tính EO  ID:"+this.Id, db, khoId, spId, solo, ngayhethan, ngaynhapkhoGIAM, 
							mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, soluong, 0, soluong);
					if( msg.trim().length() > 0 )
					{
						db.getConnection().rollback();
						db.shutDown();
						return false;
					}
					
					//Tăng kho nhận 0  dinh tinh, 1 EO
					String phieuDTMoi = "";
					String phieuEOMoi = "";
					
					if( rs.getString("loaidieuchinh").equals("0") ) //DINH TINH
					{
						phieuDTMoi = rs.getString("maphieudinhtinhNEW");
						
						msg = util.Update_KhoTT(rs.getString("NGAYDIEUCHINH"), "Chốt định tính / EO ID:"+this.Id, db, khoId, spId, solo, ngayhethan, ngaynhapkhoTANG, 
										mame, mathung, bin_fk, maphieu, phieuDTMoi, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, -1 * soluong, 0, -1 * soluong);
					}
					else //EO
					{
						phieuEOMoi = rs.getString("phieuEONEW");
						
						msg = util.Update_KhoTT(rs.getString("NGAYDIEUCHINH"), "Chốt định tính / EO  ID:"+this.Id, db, khoId, spId, solo, ngayhethan, ngaynhapkhoTANG, 
										mame, mathung, bin_fk, maphieu, phieudt, phieuEOMoi, marq, hamluong, hamam, loaidoituong, doituongId, -1 * soluong, 0, -1 * soluong);
					}
						
					if( msg.trim().length() > 0 )
					{
						this.msg = "Lỗi cập nhật: " + sql;
						db.getConnection().rollback();
						db.shutDown();
						return false;
					}
				}
				rs.close();
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
			return true;
		} 
		catch(Exception ex)
		{
			ex.printStackTrace();
			try {
				this.db.getConnection().rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			this.msg = ex.getMessage();
			return false;
		}
	}
	
	public boolean Delete()
	{
		try
		{
			this.db.getConnection().setAutoCommit(false);
			
			String sql = "update ERP_DINHTINH_EO set trangthai ='2' where PK_SEQ = "+ this.Id +" and trangthai=0";
			
			int k = this.db.updateReturnInt(sql);
			if(k!=1)
			{
				this.db.getConnection().rollback();
				return false;
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			return true;
		} 
		catch (Exception ex)
		{
			try 
			{
				ex.printStackTrace();
				this.db.getConnection().rollback();
			} 
			catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
			
		}
	}
	
}
