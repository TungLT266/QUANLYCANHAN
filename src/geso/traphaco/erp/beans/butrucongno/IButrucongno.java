package geso.traphaco.erp.beans.butrucongno;

import java.io.Serializable;
import java.sql.ResultSet;

public interface IButrucongno extends Serializable
{
	public String getUserId();
	public void setUserId(String userId);
	public String getId();
	public void setId(String id);	
	
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	public String getnppdangnhap();
	public void setnppdangnhap(String nppdangnhap);
	
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
	
	public String[] getKhIds();
	public void setKhIds(String[] khIds);
	
	public String[] getHdChon();
	public void setHdChon(String[] hdChon);
	public String[] getHdTigia();
	public void setHdTigia(String[] hdTigia);
	public String[] getHdId();
	public void setHdId(String[] HdId);
	public String[] getHdLoai();
	public void setHdLoai(String[] HdLoai);
	public String[] getHdNgayhd();
	public void setHdNgayhd(String[] hdNgayhd);
	public String[] getHdSohd();
	public void setHdSohd(String[] HdSohd);
	public String[] getHdSotiengoc();
	public void setHdSotiengoc(String[] HdSotiengoc);
	public String[] getHdSotienphaixoa();
	public void setHdSotienphaixoa(String[] HdSotienphaixoa);
	public String[] getHdSotienxoa();
	public void setHdSotienxoa(String[] HdSotienxoa);
	public String[] getHdSotienconlai();
	public void setHdSotienconlai(String[] HdSotienconlai);
	
	
	public ResultSet getHoadonList();
	public void setHoadonList(ResultSet hoadonList);
	
	public ResultSet getTienteList();
	public void setTienteList(ResultSet tientelist);
	public String getTienteId();
	public void setTienteId(String tienteId);
		
	public ResultSet getKHChuyenNoList();
	public void setKHChuyenNoList(ResultSet KHChuyenNolist);
	public String getKHChuyenNoId();
	public void setKHChuyenNoId(String KHChuyenNoId);
	
	public ResultSet getKHNhanNoList();
	public void setKHNhanNoList(ResultSet KHNhanNolist);
	public String getKHNhanNoId();
	public void setKHNhanNoId(String KHNhanNoId);
	
	public String getNgaychungtu();
	public void setNgaychungtu(String ngaychungtu);	
	
	public String getTongchuyenno();
	public void setTongchuyenno(String tongchuyenno);	
	
	public String getTongnhanno();
	public void setTongnhanno(String tongnhanno);	
	
	public String getSochungtu();
	public void setSochungtu(String sochungtu);	
		
	public boolean CreateNvgn();
	public boolean UpdateNvgn();
	public void createRS();
	public void init();
	public void init_display();
	public void DBclose();
	
	public String getKHChuyenNoIdGoc();
	public void setKHChuyenNoIdGoc(String KHChuyenNoIdGoc);
	
	public String getKHNhanNoIdGoc();
	public void setKHNhanNoIdGoc(String KHNhanNoIdGoc);
	
	public String getIsNPPChuyenNo();
	public void setIsNPPChuyenNo(String isNPPChuyenNo);
	
	public String getIsNPPNhanNo();
	public void setIsNPPNhanNo(String isNPPNhanNo);
	
	//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
	public String getLoainhanvien();
	
	public void setLoainhanvien(Object loainhanvien);
	
	public String getDoituongIddn();
	
	public void setDoituongIddn(Object doituongIddn);
	
	public String getDienGiaiCT();
	public void setDienGiaiCT(String dienGiaiCT);
	
	
	public String getTenKHChuyenNo();
	public void setTenKHChuyenNo(String tenKHChuyenNo);

	public String getTenKHNhanNo();
	public void setTenKHNhanNo(String tenKHNhanNo);
}