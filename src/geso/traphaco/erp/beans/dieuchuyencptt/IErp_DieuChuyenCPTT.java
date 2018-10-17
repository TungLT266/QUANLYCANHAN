package geso.traphaco.erp.beans.dieuchuyencptt;
import geso.dms.center.util.IPhan_Trang;
import geso.traphaco.erp.beans.taisancodinh.IPhanBo;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface IErp_DieuChuyenCPTT  extends Serializable, IPhan_Trang
{
	public String getId();
	
	public String getMa();
	
	public String getDiengiai();
	
	public String getNtsId();
	
	public String getDvt();
	
	public String getSothangKH();
	
	public String getThangbatdauKH();
	
	public String getPpKH();
	
	public String getMsg();
	
	public String getNgaytao();
	public String getNguoitao();
	public String getNgaysua();
	public String getNguoisua();
	
	public void setPhanloai(String phanloai);
	public String getPhanloai();
	public void init_convert();
	public int getCurrentPage();
	public void setCurrentPage(int current);	
	public int[] getListPages();
	public void setListPages(int[] listPages);
	
	public String getTrangthai();
	
	public String getSoLuong();
	
	public void setSoLuong(String soluong);
	
	public String getDonGia();
	
	public void setDonGia(String dongia);
	
	public void setThanhTien(String thanhtien);
	
	public String getThanhTien();
	
	public void setId(String id);
	
	public void setMa(String ma);
	
	public void setDiengiai(String diengiai);
	
	public void setMsg(String Msg);
	
	public void setNgaytao(String ngaytao);
	
	public void setNguoitao(String nguoitao);
	
	public void setNgaysua(String ngaysua);
	
	public void setNguoisua(String nguoisua);
	
	public void setTrangthai(String trangthai);
	
	public void setNtsId(String ntsId);
	
	public void setCtyId(String ctyId);
	
	public String getCtyId();
	
	public void setDvt(String dvt);
	
	public void setLtsId(String ltsId);
	
	public String getLtsId();
	
	public void setSothangKH(String sothangKh);
	
	public void setThangbatdauKH(String thangbatdauKh);
	
	public void setPpKH(String ppKh);
	
	public ResultSet getRsts();
	
	public void setRsts(ResultSet rsTs);
	
	public ResultSet getRsLoaitaisan();
	
	public void setRsLoaitaisan(ResultSet LtsList);
	
	public ResultSet getRsNts();
	
	public void setRsNts(ResultSet rsNts);
	
	public ResultSet getRscd();
	
	public ResultSet getRsdvt();
	
	public void setPPKHrS(ResultSet ppkh);
	
	public ResultSet getPPKHrS();

	public void setRsdvt(ResultSet rsDvt);
	
	public void setUserid(String userId);
	
	public String getUserid();
	
	public void setUserTen(String userTen);
	
	public String getUserTen();
		
	boolean themmoiMa(HttpServletRequest request);
	
	boolean UpdateMa(HttpServletRequest request);
	
	public void init(String sql);
	
	public void createRs();
	
	
	public boolean getEnable();
	
	
	public ResultSet getRsTtcp();

	public void setRsTtcp(ResultSet ttcpRs);

	public String[] getKhoanmucIds() ;

	public void setKhoanmucIds(String[] khoanmucIds) ;

	public ResultSet getRsCdts();

	public void setRsCdts(ResultSet cdtsRs);

	public void setCdtsIds(String[] cdtsIds);

	public String[] getCdtsIds();
	
	public ResultSet getChoncongdung();
	
	public ResultSet getChoncongdungthem();
	
	public ResultSet getChonTTCP();
	
	public ResultSet getChonTTCPThem();

	public String getCdIdsList();
	
	public String getTtcpIdsList();
	
	public String getTtcp();
	public void setTtcp(String Ttcp);
	
	public String getLoaitaisan();
	
	
	public String getLapngansachId();
	public void setLapngansachId(String lnsId);
		
	public void Delete(String id);
	public boolean isKhauhao(String tsId);
	void DBClose();
	public String getNgaychungtu();

	public void setNgaychungtu(String ngaychungtu);
	
	public String getTscdId() ;

	public void setTscdId(String tscdId) ;

	public ResultSet getTscdRs() ;

	public void setTscdRs(ResultSet tscdRs);
	
	public String getSochungtu();

	public void setSochungtu(String sochungtu) ;
	
	public String getPbId() ;

	public void setPbId(String pbId) ;
	public ResultSet getPbRs() ;

	public void setPbRs(ResultSet pbRs);
	

	public ResultSet getRskhoanmuc() ;

	public void setRskhoanmuc(ResultSet rskhoanmuc) ;
	
	public ResultSet getRsDc() ;

	public void setRsDc(ResultSet rsDc) ;
	public String getSodieuchuyen() ;

	public void setSodieuchuyen(String sodieuchuyen) ;

	public String getTungay() ;

	public void setTungay(String tungay) ;

	public String getDenngay() ;
	public void setDenngay(String denngay) ;
	public String getPhongban();
	public void setPhongban(String phongban) ;
	
	
	
	public String getCdId() ;

	public void setCdId(String cdId) ;

	public String getKmId() ;

	public void setKmId(String kmId) ;
	
	
	public String getPbCu() ;

	public void setPbCu(String pbCu);
	
	public String getPbCuId() ;

	public void setPbCuId(String pbCuId) ;
	public ResultSet getTtcpRs() ;

	public void setTtcpRs(ResultSet ttcpRs) ;
	
	
	public String getTtcpCu() ;

	public void setTtcpCu(String ttcpCu) ;

	public String getTtcpCuId() ;

	public void setTtcpCuId(String ttcpCuId) ;
	

	public String getTtcpId();

	public void setTtcpId(String ttcpId) ;
	
	public String getDienGiaiCT();
	
	public void setDienGiaiCT(String dienGiaiCT);
	
	
	public List<IPhanBo> getPhanBoList();

	public void setPhanBoList(List<IPhanBo> phanBoList);
	
	
	public String[] getPhanTramKhauHao();

	public void setPhanTramKhauHao(String[] phanTramKhauHao);
	

}
