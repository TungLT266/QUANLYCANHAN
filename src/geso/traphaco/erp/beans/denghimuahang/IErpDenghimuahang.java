package geso.traphaco.erp.beans.denghimuahang;

import geso.traphaco.erp.beans.donmuahang.*;

import java.sql.ResultSet;
import java.util.List;

public interface IErpDenghimuahang
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

	public String getCongvan();
	public void setCongvan(String congvan);
	
	public String getGhiChu();
	public void setGhiChu(String ghichu);

	public String getmaDMH();
	public void setmaDMH(String maDMH);
	
	// Them 28-08-2012

	public String getId();
	public void setId(String id);

	public String getSochungtu();
	public void setSochungtu(String sochungtu);

	public String getNgaydenghi();
	public void setNgaydenghi(String ngaydenghi);

	public String getDvthId();
	public void setDvthId(String dvthid);

	public ResultSet getDvthList();
	public void setDvthList(ResultSet dvthlist);

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

	public List<ITiente> getTienteList();
	public void setTienteList(List<ITiente> ttList);

	public String getMsg();
	public void setMsg(String msg);
	
	public String getLoaihanghoa();
	public void setLoaihanghoa(String loaihh);

	public void createRs();
	public void init();
	public boolean createDmh();
	public boolean updateDmh();
	
	public String getTrangthaiDuyet();
	public ResultSet getDuyet();

	public String[] getDuyetIds();
	public void setDuyetIds(String[] duyetIds);
	
	public String getCanDuyet();
	public void setCanDuyet(String canduyet);
	
	public String[] getGhiChuArr();
	public void setGhiChuArr(String[] ghichuArr);
	
	public void close();
	
	
	public String[] getCpkDienGiai();
	public void setCpkDiengiai(String[] cpkDD);
	public String[] getCpkSoTien();
	public void setCpkSoTien(String[] cpkST);
	
	public String getCheckedNoiBo();
	public void setCheckedNoiBo(String checkedNoiBo);

	public String getDuyetdnmh();

	public void setDuyetdnmh(String duyetdnmh) ;

}
