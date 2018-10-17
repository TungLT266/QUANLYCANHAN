package geso.traphaco.erp.beans.parktrongnuoc;

import geso.traphaco.erp.beans.donmuahangtrongnuoc.IDonvi;
import geso.traphaco.erp.beans.shiphang.ISanpham;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpChuyenkho;

import java.sql.ResultSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface IErpPark 
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getCtyId();
	public void setCtyId(String CtyId);	
	
	public String getId();
	public void setId(String id);	
	
	public ResultSet getNccRs();
	public void setNccRs(ResultSet nccRs);

	public void setNccId(String nccId); 
	public String getNccId(); 
	
	public void setLoaihang(String loaihang);
	public String getLoaihang();
	
	public String getNgayghinhan();
	public void setNgayghinhan(String ngayghinhan);
	
	public ResultSet getLoaihangList();
	public void setLoaihangList(ResultSet loaihanglist);
	
	public List<IErpHoadon> getHdList();
	public void setHdList(List<IErpHoadon> hdList);
	
	public List<IErpHoadonSp> getPnkList();
	public void setPnkList(List<IErpHoadonSp> pnkList);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public ResultSet getTienteRs(); 
	public void setTienteRs(ResultSet ttRs); 
	public ResultSet getTienteRs_Display();
	
	public String getTtId(); 
	public void setTtId(String ttId); 
	
	public String getTiente();
	public void setTiente(String tiente); 
	
	public String getMsg();
	public void setMsg(String msg);
	public void createRsLocNcc() ;
	
	public ResultSet getPoNkRs();
	
	public void setPoNkRs(ResultSet poNKRs);

	public void setPoNk_SPRs(ResultSet poNK_SPRs);

	public ResultSet getPoNk_SPRs();

	public void setPoNk_SP_ChonRs(ResultSet poNK_SP_ChonRs);

	public ResultSet getPoNk_SP_ChonRs(String Id);	

	public void setPoNkIds(String nccId); 

	public String getPoNkIdsId(); 
	
	public void createRs();
	public void createRs_update();
	public void init();
	public void init_Update();
	public void initDisplay();
	public boolean createPark();
	public boolean updatePark();
	public boolean updatePark_Duyet();
	
	public boolean Duyet();
	public void close();
	
	public String getTinhThueNhapKhau();
	public void setTinhThueNhapKhau(String TinhThueNhapKhau);
	
	public void setRequest(HttpServletRequest request);
	
	public void clearHdlist();
	
	public String getTongsoluong();
	public void setTongsoluong(String tongsoluong);
	
	public String getTigia(); 
	public void setTigia(String tigia); 
	
	public void setPoRs(ResultSet poRs);
	public ResultSet getPoRs();
	
	public void setPoId(String poId); 
	public String getPoId(); 
	
	public void setChitietPoRs(ResultSet ChitietPoRs);
	public ResultSet getChitietPoRs();
		
	public String getThuesuat() ;
	public void setThuesuat(String thuesuat) ;
	
	public void setNCCThayTheTen(String NCCThayTheTen);
	public String getNCCThayTheTen();
	
	public void setNCCThayTheMST(String NCCThayTheMST);
	public String getNCCThayTheMST();
	
	public void setNCCThayTheDiaChi(String NCCThayTheDiaChi);
	public String getNCCThayTheDiaChi();
	
	public void setNCCThayTheId(String NCCThayTheId);
	public String getNCCThayTheId();
	
	public String getnppdangnhap();
	public void setnppdangnhap(String nppdangnhap);
	
	public void setLoaidonmhRs(ResultSet LoaidonmhRs);
	public ResultSet getLoaidonmhRs();
	
	public String getLoaidonmh();
	public void setLoaidonmh(String loaidonmh);
	
	public String getcophieuchi();
	
	public String getTrangthaiHD();
	
	public String getKyHieuHoaDon();
	public void setKyHieuHoaDon(String kyHieuHoaDon);
	public String getNgayHoaDon();
	public void setNgayHoaDon(String ngayHoaDon);
	public String getSoTienChuaThue();
	public void setSoTienChuaThue(String soTienChuaThue);
	public String getThuegtgt() ;
	public void setThuegtgt(String thuegtgt);
	public double getTong();
	public void setTong(double tong);
	public List<ISanpham> getSpList() ;
	public void setSpList(List<ISanpham> spList) ;
	public String getSoHoaDon();
	public void setSoHoaDon(String soHoaDon);
	public String getHoadonNCC();
	public void setHoadonNCC(String hoadonNCC);

	public String getMuahang_fk();
	public void setMuahang_fk(String muahang_fk) ;

	public ResultSet getRsKhoBietTru();
	public void setRsKhoBietTru(ResultSet rsKhoBietTru);
	public ResultSet getRsKhoTonTru();
	public void setRsKhoTonTru(ResultSet rsKhoTonTru);
	public String getIdKhoBietTru();
	public void setIdKhoBietTru(String idKhoBietTru);
	public String getIdKhoTonTru() ;
	public void setIdKhoTonTru(String idKhoTonTru) ;
	
	public int getDungsai() ;
	public void setDungsai(int dungsai);
	
	public List<IDonvi> getListDonvi();
	public void setListDonvi(List<IDonvi> listDonvi) ;
	
	public String getDienGiaiCT();
	public void setDienGiaiCT(String dienGiaiCT);
	String getMauSoHoaDon();
	void setMauSoHoaDon(String mauSoHoaDon);
	List<IErpChuyenkho> getPhieuCKList();
	void setPhieuCKList(List<IErpChuyenkho> phieuCKList);
	int getDuyet();
	void setDuyet(int duyet);
	int getIsDisplay();
	void setIsDisplay(int isDisplay);
	String[] initPNK_StringArray();
	List<ISanpham> initPNK_HD_SP();
	public ResultSet getParkList();

	public void setParkList(ResultSet parkList);
	
	public String getChenhlechHd() ;

	public void setChenhlechHd(String chenhlechHd) ;
	
}