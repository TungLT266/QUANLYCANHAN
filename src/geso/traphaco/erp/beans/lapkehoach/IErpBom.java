package geso.traphaco.erp.beans.lapkehoach;

import geso.traphaco.erp.beans.danhmucvattu.IDanhmucvattu_SP;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public interface IErpBom
{
	public String getUserId();

	public void setUserId(String userId);

	public String getId();

	public void setId(String Id);

	public String getDiengiai();

	public void setDiengiai(String diengiai);
	
	public String getTenBOM();

	public void setTenBOM(String tenbom);

	public String getVanBanHuongDan();

	public void setVanBanHuongDan(String vanbanhuongdan);

	public String getHieuluctu();

	public void setHieuluctu(String hieuluctu);

	public String getHieulucden();

	public void setHieulucden(String hieulucden);

	public String getDungsai();

	public void setDungsai(String dungsai);

	public String getSudung();

	public void setSudung(String sudung);

	public String getTrangthai();

	public void setTrangthai(String trangthai);

	public String getTrongluong();

	public void setTrongluong(String trongluong);
	
	public double getPTHaoHut();

	public void setPTHaoHut(double pthaohut);
	
	public ResultSet getSpRs();

	public void setSpRs(ResultSet spRs);

	public ResultSet getNLRs();
	
	public void setNLRs(ResultSet nlRs);

	public ResultSet getRsVattu();

	public void setRsVattu(ResultSet rsVattu);
	
	
	public ResultSet getrsNhamay();
	public void setrsNhamay(ResultSet rs);
	

	public String  getIdNhamay();
	public void setIdNhamay(String Idnhamay);
	
	public ResultSet getrsKichBanSX();
	public void setrsKichBanSX(ResultSet rs);
	
	public String getKichBanSXId();
	public void setKichBanSXId(String Id);
 
	public String getSpId();
	public void setSpId(String spId);

	
	public String getSpMa();
	public void setSpMa(String spMa);
	
	public String getDangbaoche();
	public void setDangbaoche(String Dangbaoche);
	public String getquycach();
	public void setquycach(String quycach);
	
	
	public String getDonViTinh();
	public void setDonViTinh(String donvitinh);

	public ResultSet getRsDvdl();
	public void setRsDvdl(ResultSet rs);
	
	public String getSoluongchuan();
	public void setSoluongchuan(String slgchuan);

	public String getChophepTT();
	public void setChophepTT(String chophepTT);

	public void setListDanhMuc(List<IDanhmucvattu_SP> list);

	public List<IDanhmucvattu_SP> getListDanhMuc();

	public String getMsg();

	public void setMsg(String msg);

	public boolean createBom(HttpServletRequest request) throws ServletException, IOException;
	public boolean dongbo();
	public boolean updateBom(HttpServletRequest request) throws ServletException, IOException;

	public void createRs();

	public void init();

	public String getCtyId();

	public void setCtyId(String ctyId);

	public ResultSet getLoaiList();
	public void setLoaiList(ResultSet loaihhlist);
	public String getLoaispId();
	public void setLoaispId(String loaispid);
	
	public void DbClose();
	public String getCoSuDungHC();
	public void setCoSuDungHC(String CoSuDungHC);
		
	
	public String getDvkdId();
	public void setDvkdId(String dvkdid);
	
	public ResultSet getRsDvkd();
	public void setRsDvkd(ResultSet RsDvkd);
	

	public String getChungloaiId();
	public void setChungloaiId(String ChungloaiId);
	
	public String getTypeId();
	public void setTypeId(String TypeId);
	
	public ResultSet getRsChungloai();
	public void setRsChungloai(ResultSet RsChungloai);
	
	public String getChuanNen();
	public void setChuanNen(String ChuanNen);
	
	public ResultSet getRsChuanNen();
	public void setRsChuanNen(ResultSet RsChuanNen);
	
	public List<IErpDinhmuc> getDinhmucList();
	public void setDinhmucList(List<IErpDinhmuc> value);
	
	public String getNgayBH();
	public void setNgayBH(String ngayBH);
	public String getLanBH();
	public void setLanBH(String lanBH); 
	public ResultSet getVattuTT(String vtId);
	public String getNoisanxuat();
	public void setNoisanxuat(String noisanxuat);

	public String getIskhongthoihan();
	public void setIskhongthoihan(String iskhongthoihan);
	public String getChecktontai();

	public void setChecktontai(String checktontai);
	
	public String getQuytrinhsx();

	public void setQuytrinhsx(String quytrinhsx) ;
	
	public String getNgaybanhanhQTSX();
	public void setNgaybanhanhQTSX(String ngaybanhanhQTSX);

	public String getDaychuyensanxuat();
	public void setDaychuyensanxuat(String daychuyensanxuat);
}
