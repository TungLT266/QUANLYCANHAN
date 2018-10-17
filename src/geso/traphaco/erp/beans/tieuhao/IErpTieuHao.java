package geso.traphaco.erp.beans.tieuhao;

import geso.traphaco.erp.beans.tieuhao.ISanpham;
import geso.traphaco.erp.db.sql.dbutils;

import java.util.List;

public interface IErpTieuHao
{
	public String getCongtyId();
	public void setCongtyId(String congtyId);
	
	public String getUserId();
	public void setUserId(String userId);
	
	public String getId();
	public void setId(String id);
	
	public String getManhanhang();
	public void setManhanhang(String manhanhang);
	
	
	public String getNgayTao();
	public void setNgayTao(String ngaytao);
	
	
	public String getNgaytieuhao();
	public void setNgaytieuhao(String Ngaytieuhao);
	
	public String getNgaychot();
	public void setNgaychot(String Ngaychot);
	
	
	public String getNgaySua();
	public void setNgaySua(String ngaysua);
	
	public String getNguoiTao();
	public void setNguoiTao(String nguoitao);
	public String getNguoiSua();
	public void setNguoiSua(String nguoisua);
	
	public String getNhanHangId();
	public void setNhanHangId(String nhanhangId);
	
	public String getSanphamId();
	public void setSanphamId(String spId);
	
	public String getSanphamMa();
	public void setSanphamMa(String ma);
	
	public String getSanphamTen();
	public void setSanphamTen(String sanphamTen);
	
	public String getSoLuong();
	public void setSoLuong(String soluong);
	
	public List<ISanpham> getSpList();
	public void setSpList(List<ISanpham> spList);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public String getMsg();
	public void setMsg(String msg);
	
	public String getGhichu();
	public void setGhichu(String ghichu);
	
	public void createRs();
	public void init();
	public void initPdf(String spId);
	
	public boolean create();
	public boolean update();
	public String chot();
	
	public String getKhotieuhao();
	public void setkhotieuhao(String khotieuhao);
	public void close();
	public String getnccid();
	public void setnccid(String ncc);
	public boolean create(geso.traphaco.distributor.db.sql.dbutils db);
	public boolean CreateTieuhaoThem(String Id);
	
	public String getSoMuaHang() ;

	public void setSoMuaHang(String soMuaHang);
	public String getMuahangID() ;

	public void setMuahangID(String soMuaHang);
	
	public String getGhiChuMuaHang() ;
	public void setGhiChuMuaHang(String ghiChuMuaHang) ;
	
}
