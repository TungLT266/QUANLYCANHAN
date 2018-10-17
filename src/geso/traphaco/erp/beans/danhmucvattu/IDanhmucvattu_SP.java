package geso.traphaco.erp.beans.danhmucvattu;

import geso.traphaco.erp.beans.lenhsanxuatgiay.ISpSanxuatChitiet;
import geso.traphaco.erp.beans.phieuxuatkho.ISpDetail;

import java.sql.ResultSet;
import java.util.List;

public interface IDanhmucvattu_SP 
{
	public String getIdVT();
	public void setIdVT(String idvt);
	
	public String getkhoten();
	public void setKhoten(String khoten);
	
	public String getMA();
	public void setMA(String MA);
	
	public String getMaVatTu();
	public void setMaVatTu(String mavattu);
	
	public String getTenVatTu();
	public void setTenVatTu(String tenvattu);
	
	public String getDvtVT();
	public void setDvtVT(String dvtvt);
	
	public String getSoLuong();
	public void setSoLuong(String soluong);
	
	public String getSoLuongChuan();
	public void setSoLuongChuan(String soluongchuan);
	
	public String getIdVatTuThayThe();
	public void setIdVatTuThayThe(String idvttt);
	
	
	public String getMaVatTuThayThe();
	public void setMaVatTuThayThe(String mavattuTT);
	
	public String getTenVatTuThayThe();
	public void setTenVatTuThayThe(String tenvattuTT);
	
	public String getDvtThayThe();
	public void setDvtThayThe(String dvtTT);
	
	public String getTile();
	public void setTile(String tile);
	
	public double getHaoHut();
	public void setHaoHut(double haohut);
	
	public String getIsNLTieuHao();
	public void setIsNLTieuHao(String nltieuhao);
	
	public String getSoluongTonTT();
	public void setSoluongTonTT(String SoluongTonTT);
	
	public String getSoLuongTHThucTe();
	public void setSoLuongTHThucTe(String soluong);
	
	public String getSoLuongXuat();
	public void setSoLuongXuat(String soluongxuat);
	
	
	public List<ISpDetail> getSpDetailList();
	public void setSpDetailList(List<ISpDetail> spDetailList);
 
	public String getSoLuongDaTieuHao();
	public void setSoLuongDaTieuHao(String soluongdatieuhao);
	
	
	
	
	public String getCongDoanId();
	public void setCongDoanId(String CongDoanId);
	
	public String getBomId();
	public void setBomId(String BomId);
	
	public String getDoRong();
	public void setDoRong(String Rong);
	
	public String getTenCongDoan();
	public void setTenCongDoan(String TenCongDoan);
 
	
	public String getSoluongTon();
	public void setSoLuongTon(String soluongton);
	
	public ResultSet getRsSpThayThe();
	public void setRsSpThayThe(ResultSet rs);
	
	public void SetKhoid(String khoid);
	public String getkhoid();
	
	public List<IDanhmucvattu_SP> getListThayThe();
	public void setListThayThe(List<IDanhmucvattu_SP> spList);
 
	
	public void setLoai(String loai);
	
	public String getLoai();
	

	public void setChon(String chon);
	
	public String getChon();
	
	public void SetTrongLuong(double trongluong);
	public void SetSoi(double soi);

	
	public double getTrongLuong();
	public double getSoi();
	public double getDinhLuong();
	public void SetDinhluong(double dinhluong);
	public double getSo_gsm_le();
	public void SetSo_gsm_le(double So_gsm_le);
	
	public String getIsChongBui();
	public void SetIsChongBui(String IsChongBui);
	
	public String getSoluongTonKhoSx();
	public void setSoluongTonKhoSx(String SoluongTonKhoSx);
	
	public ResultSet getRsTonKho();
	public void setRsTonkho(ResultSet rs);
	
	public void DBClose();
	public String getHamam();
	public void setHamam(String hamam);
	public String getIsTinhHA();
	public void setIsTinhHA(String tinhHA);
	public String getHamluong();
	public void setHamluong(String hamluong);
	public String getIsTinhHL();
	public void setIsTinhHL(String tinhHL);
	public String getMaVatTuTT();
	public void setMaVatTuTT(String mavattuTT);
	
	public String getDongdu();
	public void setDongdu(String Dongdu);
	
	public String getGhichu();
	public void setGhichu(String ghichu);

	public String[] getMaNLTT();

	public void setMaNLTT(String[] maNLTT);

	public String[] getSoluongNLTT() ;

	public void setSoluongNLTT(String[] soluongTT);
	
	

	public List<ISpSanxuatChitiet> getListCTkho();
	public void setListCTkho(List<ISpSanxuatChitiet> listCt);
 
	public List<ISpSanxuatChitiet> getListCT_DaYCCK();
	public void setListCT__DaYCCK(List<ISpSanxuatChitiet> listCt);
	public void setSoluongDaYC(String soluongdayc);
	public String getSoluongDaYC();
	
	public String getDungsai();
	public void setDungsai(String dungsai);
}
