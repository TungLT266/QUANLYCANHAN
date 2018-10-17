package geso.traphaco.erp.beans.kichbansanxuatgiay.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.traphaco.erp.beans.kichbansanxuatgiay.IErpKichBanSanXuatGiay;
import geso.traphaco.erp.beans.kichbansanxuatgiay.IKichBan_CongDoanSx;
import geso.traphaco.erp.db.sql.dbutils;

public class ErpKichBanSanXuatGiay implements IErpKichBanSanXuatGiay
{
	private String id;
	private String userId;
	private String ctyId;
	private String SpSelected;
	private String dienGiai;
	private String soLuongChuan;
	private String nhaMayId;
	private int songaysanxuat;
	private String msg;
	private String trangthai;
	private String uploadi;
	private String hosolosx;
	private String ngaybanhanhhsl;
	private String lanbanhanhhsl;
	
	dbutils db;
	
	ResultSet rsNhaMay;
	ResultSet rsSanPham;
	ResultSet rsCongdoan;
	ResultSet rsVatTu;
	
	List<IKichBan_CongDoanSx> lstCdsx;
	
	public ErpKichBanSanXuatGiay()
	{
		this.id = "";
		this.userId = "";
		this.ctyId = "";
		this.dienGiai = "";
		this.soLuongChuan = "";
		this.nhaMayId = "";
		this.msg = "";
		this.trangthai = "1";
		this.SpSelected = "";
		this.songaysanxuat = 0;
		this.uploadi = "";
		this.hosolosx = "";
		this.ngaybanhanhhsl = "";
		this.lanbanhanhhsl = "";
		
		this.lstCdsx = new ArrayList<IKichBan_CongDoanSx>();
		
		this.db = new dbutils();
	}

	public void init() {
		String query = "select HOSOLOSX,NGAYBANHANHHSL,LANBANHANHHSL,SanPham_FK,DienGiai,NhaMay_FK,SoLuongChuan,SONGAYSANXUAT,TrangThai from Erp_KichBanSanXuat_Giay where PK_SEQ="+this.id;
		ResultSet rs = this.db.get(query);
		System.out.println("select Erp_KichBanSanXuat_Giay: " + query);
		
		IKichBan_CongDoanSx kbsxThongso;
		ResultSet rsThongso;
		ResultSet rsCongdoanTs;
		if(rs != null){
			try {
				NumberFormat formatter = new DecimalFormat("#,###,###");
				
				while (rs.next()) {
					this.SpSelected = rs.getString("SanPham_fK");
					this.dienGiai = rs.getString("diengiai");
					this.nhaMayId = rs.getString("NhaMay_FK");
					this.soLuongChuan = formatter.format(rs.getDouble("SoLuongChuan"));
					this.songaysanxuat = rs.getInt("SONGAYSANXUAT");
					this.trangthai = rs.getString("TrangThai");
					this.hosolosx = rs.getString("HOSOLOSX");
					this.ngaybanhanhhsl = rs.getString("NGAYBANHANHHSL");
					this.lanbanhanhhsl = rs.getString("LANBANHANHHSL");
					
					query = "select pk_seq, CongDoanSanXuat_FK, ThoiGian, ThuTu, isnull(cast(DanhMucVattu_FK as varchar),'') as DanhMucVattu_FK, isnull(soluongdm,0) as soluongdm, isnull(nhapkho,'0') as nhapkho"
							+ " from Erp_KichBanSanXuat_CongDoanSanXuat_Giay where KichBanSanXuat_FK="+this.id + "order by ThuTu";
					
					rsThongso = this.db.get(query);
					System.out.println("select Erp_KichBanSanXuat_CongDoanSanXuat_Giay: " + query);
					
					if(rsThongso != null){
						while (rsThongso.next()) {
							kbsxThongso = new KichBan_CongDoanSx();
							
							kbsxThongso.setId(rsThongso.getString("CongDoanSanXuat_FK"));
							kbsxThongso.setThoiGian(rsThongso.getString("ThoiGian"));
							kbsxThongso.setThuTu(rsThongso.getString("ThuTu"));
							kbsxThongso.setVattuId(rsThongso.getString("DanhMucVattu_FK"));
							kbsxThongso.setSoluong(rsThongso.getString("soluongdm"));
							kbsxThongso.setNhapkho(rsThongso.getString("nhapkho"));
							
//							query = "select cdsx_gd.GiaiDoan_FK as giaidoanId, pb.TEN as tenkhuvuc, gd.TEN as tengiaidoan, gdsx_tb.PK_SEQ as gdsxTbId,"
//									+ " case when gdsx_tb.loai=0 then tscd.diengiai when gdsx_tb.loai=1 then ccdc.DIENGIAI else '' end as tscdCptt,"
//									+ " isnull(tb.TEN,'') as tenthietbi, gdsx_tbct.PK_SEQ as gdsxTbctId, gdsx_tbct.THONGSOKYTHUAT, gdsx_tbct.YEUCAU,"
//									+ " gdsx_tbct.THONGSOTU, gdsx_tbct.THONGSODEN, isnull(dvdl.DIENGIAI,'') as dvdl, gdsx_tbct.ISCHECK,"
//									+ " case when cdsx_ts.gdsx_ts_ct_fk is null then 0 else 1 end as chon, isnull(cdsx_ts.fileupload,'') as fileupload"
//									+ " from Erp_CongDoanSanXuat_GIAIDOAN_Giay cdsx_gd"
//									+ " inner join ERP_GIAIDOANSX_THIETBI gdsx_tb on gdsx_tb.GIAIDOAN_FK = cdsx_gd.GiaiDoan_FK"
//									+ " inner join ERP_GIAIDOANSX_THIETBI_CHITIET gdsx_tbct on gdsx_tbct.GIAIDOAN_TB_FK = gdsx_tb.PK_SEQ"
//									+ " left join ERP_PHONGBANSX pb on pb.PK_SEQ = cdsx_gd.PhongBan_FK"
//									+ " left join ERP_GIAIDOANSX gd on gd.PK_SEQ = cdsx_gd.GiaiDoan_FK"
//									+ " left join ERP_TAISANCODINH tscd on tscd.pk_seq = gdsx_tb.TSCD_CCDC_FK and gdsx_tb.loai = 0"
//									+ " left join ERP_CONGCUDUNGCU ccdc on ccdc.PK_SEQ = gdsx_tb.TSCD_CCDC_FK and gdsx_tb.loai = 1"
//									+ " left join ERP_THIETBISX tb on tb.PK_SEQ = gdsx_tb.ThietBiSX_FK"
//									+ " left join DONVIDOLUONG dvdl on dvdl.PK_SEQ = gdsx_tbct.DVDL_FK"
//									+ " left join erp_kichbansx_cdsx_thongso cdsx_ts on cdsx_ts.gdsx_ts_ct_fk = gdsx_tbct.PK_SEQ and cdsx_ts.kbsx_cdsx_fk = " + rsThongso.getString("pk_seq")
//									+ " where CongDoanSanXuat_FK = " + rsThongso.getString("CongDoanSanXuat_FK")
//									+ " order by cdsx_gd.stt, gdsx_tb.STT, gdsx_tbct.STT";
							
							query = "select kbsx_cdsx_ts.KHUVUCSX_FK, pbsx.TEN as tenkhuvuc, kbsx_cdsx_ts.GIAIDOANSX_FK, gd.TEN as tengiaidoan,"
									+ " case when kbsx_cdsx_ts.isTSCD = 1 then 'TSCD' + cast(kbsx_cdsx_ts.TSCD_CPTT_FK as varchar) when kbsx_cdsx_ts.isTSCD = 0 then 'CPTT' + cast(kbsx_cdsx_ts.TSCD_CPTT_FK as varchar) else null end as taisanid,"
									+ " case when kbsx_cdsx_ts.isTSCD = 1 then tscd.diengiai when kbsx_cdsx_ts.isTSCD = 0 then ccdc.DIENGIAI else '' end as tentaisan,"
									+ " kbsx_cdsx_ts.THIETBISX_FK, isnull(tb.TEN, '') as tenthietbi, kbsx_cdsx_ts.THONGSOCHUNG, kbsx_cdsx_ts.THONGSOKYTHUAT, kbsx_cdsx_ts.YEUCAU,"
									+ " kbsx_cdsx_ts.THONGSOTU, kbsx_cdsx_ts.THONGSODEN, isnull(kbsx_cdsx_ts.ISCHECK, 0) as ischeck, kbsx_cdsx_ts.DVDL_FK, isnull(dvdl.DIENGIAI,'') as dvdl, isnull(kbsx_cdsx_ts.chon, 0) as chon, isnull(kbsx_cdsx_ts.fileupload,'') as fileupload"
									+ " from Erp_kichbansx_cdsx_thongso kbsx_cdsx_ts"
									+ " left join ERP_PHONGBANSX pbsx on pbsx.PK_SEQ = kbsx_cdsx_ts.KHUVUCSX_FK"
									+ " left join ERP_GIAIDOANSX gd on gd.PK_SEQ = kbsx_cdsx_ts.GIAIDOANSX_FK"
									+ " left join ERP_TAISANCODINH tscd on tscd.pk_seq = kbsx_cdsx_ts.TSCD_CPTT_FK and kbsx_cdsx_ts.isTSCD = 1"
									+ " left join ERP_CONGCUDUNGCU ccdc on ccdc.PK_SEQ = kbsx_cdsx_ts.TSCD_CPTT_FK and kbsx_cdsx_ts.isTSCD = 0"
									+ " left join ERP_THIETBISX tb on tb.PK_SEQ = kbsx_cdsx_ts.THIETBISX_FK"
									+ " left join DONVIDOLUONG dvdl on dvdl.PK_SEQ = kbsx_cdsx_ts.DVDL_FK"
									+ " where kbsx_cdsx_ts.KBSX_CDSX_FK = " + rsThongso.getString("pk_seq") + " order by kbsx_cdsx_ts.STT";
							rsCongdoanTs = this.db.get(query);
							System.out.println(query);
							
							List<KichbanSX_CongdoanSX_ChiTiet> cdsxChitietList = new ArrayList<KichbanSX_CongdoanSX_ChiTiet>();
							KichbanSX_CongdoanSX_ChiTiet cdsxChitiet;
							try {
								if(rsCongdoanTs != null){
									while(rsCongdoanTs.next()){
										cdsxChitiet = new KichbanSX_CongdoanSX_ChiTiet();
										
//										cdsxChitiet.setId(rsCongdoanTs.getString("gdsxTbctId"));
										cdsxChitiet.setKhuvucsxId(rsCongdoanTs.getString("KHUVUCSX_FK"));
										cdsxChitiet.setKhuvucsx(rsCongdoanTs.getString("tenkhuvuc"));
										cdsxChitiet.setGiaidoansxId(rsCongdoanTs.getString("GIAIDOANSX_FK"));
										cdsxChitiet.setGiaidoansx(rsCongdoanTs.getString("tengiaidoan"));
										cdsxChitiet.setTscdCpttId(rsCongdoanTs.getString("taisanid"));
										cdsxChitiet.setTscdCptt(rsCongdoanTs.getString("tentaisan"));
										cdsxChitiet.setThietbiId(rsCongdoanTs.getString("THIETBISX_FK"));
										cdsxChitiet.setThietbi(rsCongdoanTs.getString("tenthietbi"));
										cdsxChitiet.setThongsochung(rsCongdoanTs.getString("THONGSOCHUNG"));
										cdsxChitiet.setThongso(rsCongdoanTs.getString("THONGSOKYTHUAT"));
										cdsxChitiet.setYeucau(rsCongdoanTs.getString("YEUCAU"));
										cdsxChitiet.setThongsotu(rsCongdoanTs.getString("THONGSOTU"));
										cdsxChitiet.setThongsoden(rsCongdoanTs.getString("THONGSODEN"));
										cdsxChitiet.setDvtId(rsCongdoanTs.getString("DVDL_FK"));
										cdsxChitiet.setDvt(rsCongdoanTs.getString("dvdl"));
										cdsxChitiet.setDat(rsCongdoanTs.getString("ISCHECK"));
										cdsxChitiet.setChon(rsCongdoanTs.getString("chon"));
										
										String filepath = rsCongdoanTs.getString("fileupload");
										cdsxChitiet.setUploadPath(filepath);
										
										String filename = "";
										if(filepath.length() > 0){
											filename = filepath.substring(filepath.lastIndexOf("\\")+1, filepath.length());
										}
										cdsxChitiet.setUploadName(filename);
										
										cdsxChitietList.add(cdsxChitiet);
									}
									
									rsCongdoanTs.close();
								}
							} catch(Exception ev) {}
							
							kbsxThongso.setCdsxChitietList(cdsxChitietList);
							this.lstCdsx.add(kbsxThongso);
						}
						rsThongso.close();
					}
				}
				rs.close();
			} catch (Exception e) {}
		}
		
		createRs();
	}

	public String getCtyId()
	{

		return this.ctyId;
	}

	public void setCtyId(String ctyId)
	{
		this.ctyId = ctyId;
	}

	public String getUserId()
	{

		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getId()
	{

		return this.id;
	}

	public void setId(String Id)
	{
		this.id = Id;
	}

	public String getDiengiai()
	{

		return this.dienGiai;
	}

	public void setDiengiai(String diengiai)
	{
		this.dienGiai = diengiai;
	}

	public ResultSet getSpRs()
	{

		return this.rsSanPham;
	}

	public void setSpRs(ResultSet spRs)
	{
		this.rsSanPham = spRs;
	}

	public String getSoluongchuan()
	{

		return this.soLuongChuan;
	}

	public void setSoluongchuan(String soluongchuan)
	{
		this.soLuongChuan = soluongchuan;
	}

	public String getMsg()
	{

		return this.msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public boolean save() {
		try {
			db.getConnection().setAutoCommit(false);
			String query = "INSERT INTO Erp_KichBanSanXuat_Giay(HOSOLOSX,NGAYBANHANHHSL,LANBANHANHHSL,SanPham_FK,DienGiai,NhaMay_FK,SoLuongChuan,SONGAYSANXUAT,TrangThai,NgayTao,NguoiTao,NgaySua,NguoiSua,CongTy_FK)"
					+ " values(N'"+this.hosolosx+"','"+this.ngaybanhanhhsl+"','"+this.lanbanhanhhsl+"','"+this.SpSelected+"',N'"+this.dienGiai+"',case when '"+this.nhaMayId.length()+"' > 0 then '"+this.nhaMayId+"' else null end,'"+this.soLuongChuan+"',"
					+ "'"+this.songaysanxuat+"','"+this.trangthai+"','"+getDateTime()+"','"+this.userId+"','"+getDateTime()+"','"+this.userId+"','"+this.ctyId+"')";
			if (!db.update(query)) {
				this.msg = "Không thể tạo mới Erp_KichBanSanXuat_Giay: " + query;
				db.getConnection().rollback();
				return false;
			}
			query = "select scope_identity()";
			ResultSet rsId;
			String idmoi = "";
			rsId = db.get("select SCOPE_IDENTITY()");
			
			if (rsId != null) {
				while (rsId.next())
					this.id = rsId.getString(1);
			}
			rsId.close();
			
			IKichBan_CongDoanSx kbsxThongso;
			for(int i = 0; i < this.lstCdsx.size(); i++){
				kbsxThongso = this.lstCdsx.get(i);
				
				if(kbsxThongso.getId().length() > 0){
					query = "insert into Erp_KichBanSanXuat_CongDoanSanXuat_Giay(KichBanSanXuat_FK,CongDoanSanXuat_FK,ThoiGian,ThuTu,DanhMucVattu_FK,NhapKho,soluongdm)"
							+ " values('"+this.id+"','"+kbsxThongso.getId()+"','"+kbsxThongso.getThoiGian()+"','"+kbsxThongso.getThuTu()+"',"
							+ " case when '"+kbsxThongso.getVattuId().length()+"' > 0 then '"+kbsxThongso.getVattuId()+"' else null end,'"+kbsxThongso.getNhapkho()+"','"+kbsxThongso.getSoluong()+"')";
					if (!db.update(query)) {
						this.msg = "Không thể tạo mới Erp_KichBanSanXuat_CongDoanSanXuat_Giay: " + query;
						db.getConnection().rollback();
						return false;
					}
					
					rsId = db.get("select SCOPE_IDENTITY()");
					if (rsId != null) {
						while (rsId.next())
							idmoi = rsId.getString(1);
					}
					rsId.close();
					
					List<KichbanSX_CongdoanSX_ChiTiet> cdsxChitietList = kbsxThongso.getCdsxChitietList();
					KichbanSX_CongdoanSX_ChiTiet cdsxChitiet;
					for(int j = 0; j < cdsxChitietList.size(); j++){
						cdsxChitiet = cdsxChitietList.get(j);
						
//						if(cdsxChitiet.getChon().equals("1")){
//							query = "insert into Erp_kichbansx_cdsx_thongso(kbsx_fk, kbsx_cdsx_fk, gdsx_ts_ct_fk, fileupload)"
//									+ " values('"+this.id+"','"+idmoi+"','"+cdsxChitiet.getId()+"', '"+cdsxChitiet.getUploadPath()+"')";
						
						String taisan = null;
						String idTscd = null;
						if(cdsxChitiet.getTscdCpttId() != null && cdsxChitiet.getTscdCpttId().length() > 0){
							if (cdsxChitiet.getTscdCpttId().startsWith("TSCD")){
								idTscd = "1";
								taisan = cdsxChitiet.getTscdCpttId().replace("TSCD", "");
							} else if (cdsxChitiet.getTscdCpttId().startsWith("CPTT")) {
								idTscd = "0";
								taisan = cdsxChitiet.getTscdCpttId().replace("CPTT", "");
							}
						}
						query = "insert into Erp_kichbansx_cdsx_thongso(kbsx_fk, kbsx_cdsx_fk, KHUVUCSX_FK, GIAIDOANSX_FK, TSCD_CPTT_FK, isTSCD, THIETBISX_FK,"
								+ " THONGSOCHUNG, THONGSOKYTHUAT, YEUCAU, THONGSOTU, THONGSODEN, ISCHECK, DVDL_FK, CHON, fileupload, STT)"
								+ " values('"+this.id+"','"+idmoi+"',"+cdsxChitiet.getKhuvucsxId()+","+cdsxChitiet.getGiaidoansxId()+","+taisan+","+idTscd+","+cdsxChitiet.getThietbiId()+","
								+ "N'"+cdsxChitiet.getThongsochung()+"',N'"+cdsxChitiet.getThongso()+"',N'"+cdsxChitiet.getYeucau()+"','"+cdsxChitiet.getThongsotu()+"',"
								+ "'"+cdsxChitiet.getThongsoden()+"','"+cdsxChitiet.getDat()+"',"+cdsxChitiet.getDvtId()+",'"+cdsxChitiet.getChon()+"', '"+cdsxChitiet.getUploadPath()+"', '"+(j+1)+"')";
						System.out.println(query);
						if (!db.update(query)) {
							this.msg = "Không thể tạo mới Erp_kichbansx_cdsx_thongso: " + query;
							db.getConnection().rollback();
							return false;
						}
//						}
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);

		} catch (Exception e) {
			this.msg = "Loi: " + e.getMessage();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {}
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean edit() {
		try {
			db.getConnection().setAutoCommit(false);
			String query = "update Erp_KichBanSanXuat_Giay set HOSOLOSX=N'"+this.hosolosx+"',NGAYBANHANHHSL='"+this.ngaybanhanhhsl+"',LANBANHANHHSL='"+this.lanbanhanhhsl+"',SanPham_FK='"+this.SpSelected+"',DienGiai=N'"+this.dienGiai+"',NhaMay_FK=case when '"+this.nhaMayId.length()+"' > 0 then '"+this.nhaMayId+"' else null end,"
					+ "SoLuongChuan='"+this.soLuongChuan+"',SONGAYSANXUAT='"+this.songaysanxuat+"',TrangThai='"+this.trangthai+"',NgaySua='"+getDateTime()+"',NguoiSua='"+this.userId+"' where pk_seq="+this.id;
			System.out.println(query);
			if (!db.update(query)) {
				this.msg = "Không thể cập nhật Erp_KichBanSanXuat_Giay: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete from Erp_KichBanSanXuat_CongDoanSanXuat_Giay where KichBanSanXuat_FK="+this.id;
			if (!db.update(query)) {
				this.msg = "Không thể xóa cập nhật Erp_KichBanSanXuat_CongDoanSanXuat_Giay: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete from erp_kichbansx_cdsx_thongso where kbsx_fk="+this.id;
			if (!db.update(query)) {
				this.msg = "Không thể xóa cập nhật erp_kichbansx_cdsx_thongso: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			ResultSet rsId;
			String idmoi = "";
			IKichBan_CongDoanSx kbsxThongso;
//			String[] chonThongso;
			for(int i = 0; i < this.lstCdsx.size(); i++){
				kbsxThongso = this.lstCdsx.get(i);
				
				if(kbsxThongso.getId().length() > 0){
					query = "insert into Erp_KichBanSanXuat_CongDoanSanXuat_Giay(KichBanSanXuat_FK,CongDoanSanXuat_FK,ThoiGian,ThuTu,DanhMucVattu_FK,NhapKho,soluongdm)"
							+ " values('"+this.id+"','"+kbsxThongso.getId()+"','"+kbsxThongso.getThoiGian()+"','"+kbsxThongso.getThuTu()+"',"
							+ " case when '"+kbsxThongso.getVattuId().length()+"' > 0 then '"+kbsxThongso.getVattuId()+"' else null end,'"+kbsxThongso.getNhapkho()+"','"+kbsxThongso.getSoluong()+"')";
					if (!db.update(query)) {
						this.msg = "Không thể tạo mới Erp_KichBanSanXuat_CongDoanSanXuat_Giay: " + query;
						db.getConnection().rollback();
						return false;
					}
					
					rsId = db.get("select SCOPE_IDENTITY()");
					if (rsId != null) {
						while (rsId.next())
							idmoi = rsId.getString(1);
					}
					rsId.close();
					
					List<KichbanSX_CongdoanSX_ChiTiet> cdsxChitietList = kbsxThongso.getCdsxChitietList();
					KichbanSX_CongdoanSX_ChiTiet cdsxChitiet;
					for(int j = 0; j < cdsxChitietList.size(); j++){
						cdsxChitiet = cdsxChitietList.get(j);
						
//						if(cdsxChitiet.getChon().equals("1")){
//							query = "insert into Erp_kichbansx_cdsx_thongso(kbsx_fk, kbsx_cdsx_fk, gdsx_ts_ct_fk, fileupload)"
//									+ " values('"+this.id+"','"+idmoi+"','"+cdsxChitiet.getId()+"', '"+cdsxChitiet.getUploadPath()+"')";
						
						String taisan = null;
						String idTscd = null;
						if(cdsxChitiet.getTscdCpttId() != null && cdsxChitiet.getTscdCpttId().length() > 0){
							if (cdsxChitiet.getTscdCpttId().startsWith("TSCD")){
								idTscd = "1";
								taisan = cdsxChitiet.getTscdCpttId().replace("TSCD", "");
							} else if (cdsxChitiet.getTscdCpttId().startsWith("CPTT")) {
								idTscd = "0";
								taisan = cdsxChitiet.getTscdCpttId().replace("CPTT", "");
							}
						}
						
						query = "insert into Erp_kichbansx_cdsx_thongso(kbsx_fk, kbsx_cdsx_fk, KHUVUCSX_FK, GIAIDOANSX_FK, TSCD_CPTT_FK, isTSCD, THIETBISX_FK,"
								+ " THONGSOCHUNG, THONGSOKYTHUAT, YEUCAU, THONGSOTU, THONGSODEN, ISCHECK, DVDL_FK, CHON, fileupload, STT)"
								+ " values('"+this.id+"','"+idmoi+"',"+cdsxChitiet.getKhuvucsxId()+","+cdsxChitiet.getGiaidoansxId()+","+taisan+","+idTscd+","+cdsxChitiet.getThietbiId()+","
								+ "N'"+cdsxChitiet.getThongsochung()+"',N'"+cdsxChitiet.getThongso()+"',N'"+cdsxChitiet.getYeucau()+"','"+cdsxChitiet.getThongsotu()+"',"
								+ "'"+cdsxChitiet.getThongsoden()+"','"+cdsxChitiet.getDat()+"',"+cdsxChitiet.getDvtId()+",'"+cdsxChitiet.getChon()+"', '"+cdsxChitiet.getUploadPath()+"', '"+(j+1)+"')";
						System.out.println(query);
						if (!db.update(query)) {
							this.msg = "Không thể tạo mới Erp_kichbansx_cdsx_thongso: " + query;
							db.getConnection().rollback();
							return false;
						}
//						}
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);

		} catch (Exception e) {
			this.msg="Error : "+e.toString();
		    try {
				db.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		    return false;
		}

		return true;
	}

	public void createRs() {
		String query =	"select sp.pk_seq, sp.ma +' - '+ sp.Ten as ten from erp_sanpham sp"
				+ " inner join erp_loaisanpham lsp on  sp.loaisanpham_fk = lsp.pk_seq"
				+ " where sp.congty_fk='"+this.ctyId+"' and sp.trangthai='1' and lsp.isBom = 1"
				+ " order by Ten";
		this.rsSanPham = this.db.get(query);
		
		query = "SELECT PK_SEQ, DIENGIAI FROM ERP_DANHMUCVATTU WHERE TRANGTHAI = 1 and CONGTY_FK='"+this.ctyId+"' ";
		this.rsVatTu=this.db.getScrol(query);
		
		query = "select a.pk_seq, '[' + b.tennhamay + ']' + ' [' + cast(a.pk_seq as varchar) + ']' + ' [' + a.diengiai + ']' diengiai"
				+ " from ERP_CONGDOANSANXUAT_GIAY a inner join erp_nhamay b on a.nhamay_fk = b.pk_seq"
				+ " WHERE a.TRANGTHAI = 1 AND a.CONGTY_FK = '" + this.ctyId + "' ";
		this.rsCongdoan=this.db.getScrol(query);
		
		query = "select pk_seq, manhamay + ' - ' + tennhamay as Ten from ERP_NHAMAY where trangthai = 1 and congty_fk = '" + this.ctyId + "' ";
		this.rsNhaMay = this.db.get(query);
	}
	
	public void DbClose() {
		try {
			if (this.lstCdsx != null)
				this.lstCdsx.clear();
			
			if (this.rsNhaMay != null)
				this.rsNhaMay.close();

			if (this.rsSanPham != null)
				this.rsSanPham.close();

			if(this.rsVatTu!=null)
				this.rsVatTu.close();
			
			if(this.rsCongdoan!=null)
				this.rsCongdoan.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (this.db != null)
				this.db.shutDown();
		}
	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	
	public ResultSet getRsNhaMay(){
		return this.rsNhaMay;
	}

	
	public void setRsNhaMay(ResultSet rsNhaMay){
		this.rsNhaMay=rsNhaMay;
	}

	
	public void setCongDoanSxList(List<IKichBan_CongDoanSx> lst){
		this.lstCdsx=lst;
	}

	
	public List<IKichBan_CongDoanSx> getCongdoanSxList(){
		return this.lstCdsx;
	}

	public String getNhaMayId(){
		return this.nhaMayId;
	}

	public void setNhaMayId(String NhaMayId){
		this.nhaMayId=NhaMayId;
	}

	public String getTrangThai(){
		return this.trangthai;
	}

	public void setTrangThai(String TrangThai){
		this.trangthai=TrangThai;
	}

	public String getSpSelected(){
		return this.SpSelected;
	}

	public void setSpSelected(String SpSelected){
		this.SpSelected=SpSelected;
	}

	public ResultSet getRsCongdoan(){
		return this.rsCongdoan;
	}

	public void setRsCongdoan(ResultSet rsCongdoan){
		this.rsCongdoan=rsCongdoan;
	}

	public ResultSet getRsVattu(){
		return this.rsVatTu;
	}

	public ResultSet setRsVattu(ResultSet rsVattu){
		return this.rsVatTu=rsVattu;
	}

	@Override
	public int getSongaysanxuat() {
		return this.songaysanxuat;
	}

	@Override
	public void setSongaysanxuat(int songaysanxuat) {
		this.songaysanxuat =songaysanxuat;
	}

	public String getUploadi() {
		return uploadi;
	}
	public void setUploadi(String uploadi) {
		this.uploadi = uploadi;
	}

	public String getHosolosx() {
		return hosolosx;
	}

	public void setHosolosx(String hosolosx) {
		this.hosolosx = hosolosx;
	}

	public String getNgaybanhanhhsl() {
		return ngaybanhanhhsl;
	}

	public void setNgaybanhanhhsl(String ngaybanhanhhsl) {
		this.ngaybanhanhhsl = ngaybanhanhhsl;
	}

	public String getLanbanhanhhsl() {
		return lanbanhanhhsl;
	}

	public void setLanbanhanhhsl(String lanbanhanhhsl) {
		this.lanbanhanhhsl = lanbanhanhhsl;
	}

	
}
