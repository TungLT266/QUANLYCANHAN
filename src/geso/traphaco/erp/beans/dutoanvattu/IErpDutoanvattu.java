package geso.traphaco.erp.beans.dutoanvattu;

import geso.traphaco.erp.beans.dutoanvattu.ITiente;

import java.sql.ResultSet;
import java.util.List;

public interface IErpDutoanvattu
{
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getUserId();
	public void setUserId(String userId);


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

	public String getmaDMH();
	public void setmaDMH(String maDMH);
	
	// Them 28-08-2012

	public String getId();
	public void setId(String id);

	public String getNgaydutoan();
	public void setNgaydutoan(String ngaydutoan);

	public String getDvthId();
	public void setDvthId(String dvthid);
	public ResultSet getDvthList();
	public void setDvthList(ResultSet dvthlist);
	
	public String getDnmhId();
	public void setDnmhId(String dnmhId);
	public ResultSet getDmhList();
	public void setDmhList(ResultSet dmhList);
	
	public String getTimNCCId();
	public void setTimNCCId(String timNCCId);
	public ResultSet getTimNCCList();
	public void setTimNCCList(ResultSet timNCCList);

	public String getTrangthai();
	public void setTrangthai(String trangthai);

	public String getLoaispId();
	public void setLoaispId(String loaispid);

	public ResultSet getLoaiList();
	public void setLoaiList(ResultSet loaihhlist);

	public List<ISanpham> getSpList();
	public void setSpList(List<ISanpham> spList);

	public List<IDonvi> getDvList();
	public void setDvList(List<IDonvi> dvList);

	/*public ResultSet getTienteList();
	public void setTienteList(ResultSet ttList);*/
	
	public List<INhacungcap> getNhacungcapList();
	public void setNhacungcapList(List<INhacungcap> nccList);
	
	public List<ISanpham> getSanphamList();
	public void setSanphamList(List<ISanpham> spList);

	public String getMsg();
	public void setMsg(String msg);
	
	public String getLoaihanghoa();
	public void setLoaihanghoa(String loaihh);

	public void createRs(boolean po);
	public void init(boolean po);
	public boolean createDmh();
	public boolean updateDmh();
	public boolean createPO();
	
	
	public Long getTiGiaNguyenTe() ;
	public void setTiGiaNguyenTe(Long tiGiaNguyenTe) ;
	
	public List<ITiente> getTienteList() ;

	public void setTienteList(List<ITiente> tienteList);
	
	
	public void close();
	


}
