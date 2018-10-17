package geso.traphaco.distributor.beans.cantrucongno;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Hashtable;

public interface ICantrucongno extends Serializable
{
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);	
	
	public String getNgay();
	public void setNgay(String ngay);	

	
	public String getGhichu();
	public void setGhichu(String ghichu);	
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);	
	public String getNgaytao();
	public void setNgaytao(String ngaytao);	
	public String getNguoitao();
	public void setNguoitao(String nguoitao);	
	public String getNgaysua();
	public void setNgaysua(String ngaysua);
	public String getNguoisua();
	public void setNguoisua(String nguoisua);	
	
	public String getSohoadontu();
	public void setSohoadontu(String sohoadontu);
	public String getSohoadonden();
	public void setSohoadonden(String sohoadonden);
	
	public String getSotientu();
	public void setSotientu(String sotientu);
	public String getSotienden();
	public void setSotienden(String sotienden);
	
	public String getTungay();
	public void setTungay(String tungay);
	public String getDenngay();
	public void setDenngay(String denngay);
	
	public String getMessage();
	public void setMessage(String msg);
	
	public String getNppId();
	public void setNppId(String nppId);
	public String getNppTen();
	public void setNppTen(String nppTen);
	public String getSitecode();
	public void setSitecode(String sitecode);
	
	public ResultSet getKhList();
	public void setKhList(ResultSet khlist);
	public String getKhId();
	public void setKhId(String khId);
	
	public String[] getHdIds();
	public void setHdIds(String[] hdIds);	
	
	public String[] getHdChon();
	public void setHdChon(String[] hdChon);
	public String[] getHdId();
	public void setHdId(String[] HdId);
	public String[] getHdNgayhd();
	public void setHdNgayhd(String[] hdNgayhd);
	public String[] getHdKhid();
	public void setHdKhid(String[] hdKhid);
	public String[] getHdMakh();
	public void setHdMakh(String[] HdMakh);
	public String[] getHdMadt();
	public void setHdMadt(String[] HdMadt);
	public String[] getHdKyhieu();
	public void setHdKyhieu(String[] HdKyhieu);
	public String[] getHdSohd();
	public void setHdSohd(String[] HdSohd);
	public String[] getHdSotien();
	public void setHdSotien(String[] HdSotien);
	
	public String[] getHdTienCK();
	public void setHdTienCK(String[] HdTienCK);
	
	public String[] getHdDuno();
	public void setHdDuno(String[] HdDuno);
		
	public String[] getHdDuco();
	public void setHdDuco(String[] HdDuco);
		
	public String[] getHdGhino();
	public void setHdGhino(String[] HdGhino);
	
	public String[] getHdGhico();
	public void setHdGhico(String[] HdGhico);

	public ResultSet getHoadonList();
	public void setHoadonList(ResultSet hoadonList);
	
	public String getDdkdId();
	public void setDdkdId(String ddkdId);
	
	public ResultSet getDdkdRs();
	public void setDdkdRs(ResultSet ddkdRs);
	
	public String getTc_duno();
	public void setTc_duno(String tc_duno_tc);
	
	public String getTc_duco();
	public void setTc_duco(String tc_duco_tc);
	
	public String getTc_ghico();
	public void setTc_ghico(String tc_ghico_tc);
	
	public String getTc_ghino();
	public void setTc_ghino(String tc_ghino_tc);
	
	public boolean CreateNvgn();
	public boolean UpdateNvgn();
	public void createRS();
	public void createKHRS();
	public void init();
	public void DBclose();
	
}
