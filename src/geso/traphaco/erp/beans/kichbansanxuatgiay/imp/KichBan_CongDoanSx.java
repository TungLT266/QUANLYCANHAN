package geso.traphaco.erp.beans.kichbansanxuatgiay.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.beans.kichbansanxuatgiay.IKichBan_CongDoanSx;
import geso.traphaco.erp.db.sql.dbutils;

public class KichBan_CongDoanSx implements IKichBan_CongDoanSx {
	private String Id;
	private String thoiGian;
	private String thuTu;
	private String vattuId;
	private String nhapkho;
	private String dienGiai;
	private String chon;
	private String soluong;
	
//	private ResultSet CongdoanRs;
	dbutils db;
	
	private List<KichbanSX_CongdoanSX_ChiTiet> cdsxChitietList;

	public KichBan_CongDoanSx() {
		this.Id = "";
		this.dienGiai = "";
		this.thoiGian = "";
		this.thuTu = "";
		this.vattuId = "";
		this.soluong = "";
		this.nhapkho = "";
		this.chon = "";
		
		this.cdsxChitietList = new ArrayList<KichbanSX_CongdoanSX_ChiTiet>();
		
		this.db = new dbutils();
	}
	
	public void createRs(){
//		String query = "select cdsx_gd.GiaiDoan_FK as giaidoanId, pb.TEN as tenkhuvuc, gd.TEN as tengiaidoan, gdsx_tb.PK_SEQ as gdsxTbId,"
//				+ " case when gdsx_tb.loai=0 then tscd.diengiai when gdsx_tb.loai=1 then ccdc.DIENGIAI else '' end as tscdCptt,"
//				+ " isnull(tb.TEN,'') as tenthietbi, gdsx_tbct.PK_SEQ as gdsxTbctId, gdsx_tbct.THONGSOKYTHUAT, gdsx_tbct.YEUCAU,"
//				+ " gdsx_tbct.THONGSOTU, gdsx_tbct.THONGSODEN, isnull(dvdl.DIENGIAI,'') as dvdl, gdsx_tbct.ISCHECK"
//				+ " from Erp_CongDoanSanXuat_GIAIDOAN_Giay cdsx_gd"
//				+ " inner join ERP_GIAIDOANSX_THIETBI gdsx_tb on gdsx_tb.GIAIDOAN_FK = cdsx_gd.GiaiDoan_FK"
//				+ " inner join ERP_GIAIDOANSX_THIETBI_CHITIET gdsx_tbct on gdsx_tbct.GIAIDOAN_TB_FK = gdsx_tb.PK_SEQ"
//				+ " left join ERP_PHONGBANSX pb on pb.PK_SEQ = cdsx_gd.PhongBan_FK"
//				+ " left join ERP_GIAIDOANSX gd on gd.PK_SEQ = cdsx_gd.GiaiDoan_FK"
//				+ " left join ERP_TAISANCODINH tscd on tscd.pk_seq = gdsx_tb.TSCD_CCDC_FK and gdsx_tb.loai = 0"
//				+ " left join ERP_CONGCUDUNGCU ccdc on ccdc.PK_SEQ = gdsx_tb.TSCD_CCDC_FK and gdsx_tb.loai = 1"
//				+ " left join ERP_THIETBISX tb on tb.PK_SEQ = gdsx_tb.ThietBiSX_FK"
//				+ " left join DONVIDOLUONG dvdl on dvdl.PK_SEQ = gdsx_tbct.DVDL_FK"
//				+ " where CongDoanSanXuat_FK = "+this.Id+" order by cdsx_gd.stt, gdsx_tb.STT, gdsx_tbct.STT";
		
		String query = "select cdsx_gd.PhongBan_FK, pb.TEN as tenkhuvuc, cdsx_gd.GiaiDoan_FK, gd.TEN as tengiaidoan,"
				+ " case when gdsx_tb.loai = 0 then 'TSCD' + cast(gdsx_tb.TSCD_CCDC_FK as varchar) when gdsx_tb.loai = 1 then 'CPTT' + cast(gdsx_tb.TSCD_CCDC_FK as varchar) else '' end as taisanid,"
				+ " case when gdsx_tb.loai = 0 then tscd.diengiai when gdsx_tb.loai = 1 then ccdc.DIENGIAI else '' end as tentaisan,"
				+ " gdsx_tb.ThietBiSX_FK, isnull(tb.TEN,'') as tenthietbi, gdsx_tb.THONGSOCHUNG, gdsx_tbct.THONGSOKYTHUAT, gdsx_tbct.YEUCAU,"
				+ " gdsx_tbct.THONGSOTU, gdsx_tbct.THONGSODEN, gdsx_tbct.DVDL_FK, isnull(dvdl.DIENGIAI,'') as dvdl, gdsx_tbct.ISCHECK"
				+ " from Erp_CongDoanSanXuat_GIAIDOAN_Giay cdsx_gd"
				+ " inner join ERP_GIAIDOANSX_THIETBI gdsx_tb on gdsx_tb.GIAIDOAN_FK = cdsx_gd.GiaiDoan_FK"
				+ " inner join ERP_GIAIDOANSX_THIETBI_CHITIET gdsx_tbct on gdsx_tbct.GIAIDOAN_TB_FK = gdsx_tb.PK_SEQ"
				+ " left join ERP_PHONGBANSX pb on pb.PK_SEQ = cdsx_gd.PhongBan_FK"
				+ " left join ERP_GIAIDOANSX gd on gd.PK_SEQ = cdsx_gd.GiaiDoan_FK"
				+ " left join ERP_TAISANCODINH tscd on tscd.pk_seq = gdsx_tb.TSCD_CCDC_FK and gdsx_tb.loai = 0"
				+ " left join ERP_CONGCUDUNGCU ccdc on ccdc.PK_SEQ = gdsx_tb.TSCD_CCDC_FK and gdsx_tb.loai = 1"
				+ " left join ERP_THIETBISX tb on tb.PK_SEQ = gdsx_tb.ThietBiSX_FK"
				+ " left join DONVIDOLUONG dvdl on dvdl.PK_SEQ = gdsx_tbct.DVDL_FK"
				+ " where CongDoanSanXuat_FK = "+this.Id+" order by cdsx_gd.stt, gdsx_tb.STT, gdsx_tbct.STT";
		System.out.println(query);
		
		ResultSet rs = this.db.get(query);
		KichbanSX_CongdoanSX_ChiTiet cdsxChitiet;
		try {
			if(rs != null){
				while(rs.next()){
					cdsxChitiet = new KichbanSX_CongdoanSX_ChiTiet();
					
//					cdsxChitiet.setId(rs.getString("gdsxTbctId"));
					cdsxChitiet.setKhuvucsxId(rs.getString("PhongBan_FK"));
					cdsxChitiet.setKhuvucsx(rs.getString("tenkhuvuc"));
					cdsxChitiet.setGiaidoansxId(rs.getString("GiaiDoan_FK"));
					cdsxChitiet.setGiaidoansx(rs.getString("tengiaidoan"));
					cdsxChitiet.setTscdCpttId(rs.getString("taisanid"));
					cdsxChitiet.setTscdCptt(rs.getString("tentaisan"));
					cdsxChitiet.setThietbiId(rs.getString("ThietBiSX_FK"));
					cdsxChitiet.setThietbi(rs.getString("tenthietbi"));
					cdsxChitiet.setThongsochung(rs.getString("THONGSOCHUNG"));
					cdsxChitiet.setThongso(rs.getString("THONGSOKYTHUAT"));
					cdsxChitiet.setYeucau(rs.getString("YEUCAU"));
					cdsxChitiet.setThongsotu(rs.getString("THONGSOTU"));
					cdsxChitiet.setThongsoden(rs.getString("THONGSODEN"));
					cdsxChitiet.setDvtId(rs.getString("DVDL_FK"));
					cdsxChitiet.setDvt(rs.getString("dvdl"));
					cdsxChitiet.setDat(rs.getString("ISCHECK"));
					
					this.cdsxChitietList.add(cdsxChitiet);
				}
			}
			
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getId()
	{
		return this.Id;
	}

	public void setId(String id)
	{
		this.Id = id;
	}

	public String getDienGiai()
	{

		return this.dienGiai;
	}

	public void setDienGiai(String dienGiai)
	{
		this.dienGiai = dienGiai;
	}

	public String getThoiGian()
	{

		return this.thoiGian;
	}

	public void setThoiGian(String thoiGian)
	{
		this.thoiGian = thoiGian;
	}

	public String getThuTu()
	{

		return this.thuTu;
	}

	public void setThuTu(String thuTu)
	{
		this.thuTu = thuTu;
	}

	public String getVattuId()
	{

		return this.vattuId;
	}

	public void setVattuId(String vattuId)
	{
		this.vattuId = vattuId;
	}

	@Override
	public String getNhapkho() {
		return this.nhapkho;
	}

	@Override
	public void setNhapkho(String nhapkho) {
		this.nhapkho = nhapkho;
	}

//	public ResultSet getCongdoanRs() {
//		return CongdoanRs;
//	}
//	public void setCongdoanRs(ResultSet congdoanRs) {
//		CongdoanRs = congdoanRs;
//	}

	public String getChon() {
		return chon;
	}
	public void setChon(String chon) {
		this.chon = chon;
	}

	public String getSoluong() {
		return soluong;
	}
	public void setSoluong(String soluong) {
		this.soluong = soluong;
	}

	public List<KichbanSX_CongdoanSX_ChiTiet> getCdsxChitietList() {
		return cdsxChitietList;
	}

	public void setCdsxChitietList(List<KichbanSX_CongdoanSX_ChiTiet> cdsxChitietList) {
		this.cdsxChitietList = cdsxChitietList;
	}
}
