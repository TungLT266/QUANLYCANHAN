package geso.traphaco.erp.beans.phieuthanhtoan;

import java.sql.ResultSet;
import java.util.List;

public interface IErpDonmuahang
{
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getUserId();
	public void setUserId(String userId);

	// Them 28-08-2012
	public void CreateListTrungTamChiPhi();

	public void setNguonGocHH(String nguongoc);
	public String getNguonGocHH();

	public void setMaLoaiHH(String maloaihh);
	public String getMaLoaiHH();

	public void setTienTe_FK(String tiente_fk);
	public String getTienTe_FK();

	public void setTyGiaQuyDoi(float tygiaquydoi);
	public Float GetTyGiaQuyDoi();

	public String getGhiChu();
	public void setGhiChu(String ghichu);

	public void setTrungTamChiPhi_FK(String trungtamchiphi_fk);
	public String getTrungTamChiPhi_FK();

	public List<ITrungTamChiPhi> getTrungTamCpList();
	public void setTrungTamCpList(List<ITrungTamChiPhi> trungtamchiphi);
	
	public void CreatePOfromPR(ResultSet rs, String mnlId);

	// Them 28-08-2012

	public String getId();
	public void setId(String id);

	public String getSochungtu();
	public void setSochungtu(String sochungtu);

	public String getNgaymuahang();
	public void setNgaymuahang(String ngaymuahang);

	public String getDvthId();
	public void setDvthId(String dvthid);

	public ResultSet getDvthList();
	public void setDvthList(ResultSet dvthlist);

	public String getNCC();
	public void setNCC(String ncc);

	public String getTrangthai();
	public void setTrangthai(String trangthai);

	public String getTongtienchuaVat();
	public void setTongtienchuaVat(String ttchuavat);

	public String getVat();
	public void setVat(String vat);

	public String getTongtiensauVat();
	public void setTongtiensauVat(String ttsauvat);

	public String getLoaispId();
	public void setLoaispId(String loaispid);

	public ResultSet getLoaiList();
	public void setLoaiList(ResultSet loaihhlist);

	public List<ISanpham> getSpList();
	public void setSpList(List<ISanpham> spList);

	public List<IDonvi> getDvList();
	public void setDvList(List<IDonvi> dvList);

	public List<ITiente> getTienteList();
	public void setTienteList(List<ITiente> ttList);

	public List<IKho> getKhoList();
	public void setKhoList(List<IKho> khoList);

	public String getMsg();
	public void setMsg(String msg);
	
	public String getLoaihanghoa();
	public void setLoaihanghoa(String loaihh);
	
	public String getIsdontrahang();
	public void setIsdontrahang(String dontrahang);

	public void createRs();

	public void init();

	public boolean createDmh();

	public boolean updateDmh();

	public String getDungsai();
	public void setDungsai(String dungsai);
	
	public String getTrangthaiDuyet();
	public ResultSet getDuyet();
	
	public String getMakeToStock();
	public void setMakeToStock(String maketoStock);

	public String[] getDuyetIds();

	public void setDuyetIds(String[] duyetIds);

	public void close();
}
