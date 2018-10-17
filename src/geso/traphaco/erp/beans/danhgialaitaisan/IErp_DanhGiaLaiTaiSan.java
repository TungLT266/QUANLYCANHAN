package geso.traphaco.erp.beans.danhgialaitaisan;
import geso.dms.center.util.IPhan_Trang;
import geso.traphaco.erp.beans.taisancodinh.IKhauHaoDuKien;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface IErp_DanhGiaLaiTaiSan extends Serializable, IPhan_Trang
{
	public String getId();
	

	public String getNgaytao();
	public String getNguoitao();
	public String getNgaysua();
	public String getNguoisua();
	public String getMsg();
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
	
	public void setSothangKH(String sothangKh);
	
	public void setThangbatdauKH(String thangbatdauKh);
	


	

	public void setUserid(String userId);
	
	public String getUserid();
	
	public void setUserTen(String userTen);
	
	public String getUserTen();
		
	boolean themmoiMa(HttpServletRequest request);
	
	boolean UpdateMa(HttpServletRequest request);
	
	public void init(String sql);
	
	public void createRs();
	

	public void Delete(String id);
	
	void DBClose();
	public String getNgaychungtu();

	public void setNgaychungtu(String ngaychungtu);
	
	public String getTscdId() ;

	public void setTscdId(String tscdId) ;

	public ResultSet getTscdRs() ;

	public void setTscdRs(ResultSet tscdRs);
	
	public String getSochungtu();

	public void setSochungtu(String sochungtu) ;
	
	

	public String getTungay() ;

	public void setTungay(String tungay) ;

	public String getDenngay() ;
	public void setDenngay(String denngay) ;
	
	public String getNguyengia();

	public void setNguyengia(String nguyengia) ;

	public String getNguyengiamoi();
	public void setNguyengiamoi(String nguyengiamoi);

	public String getDieuchinhnguyengia();

	public void setDieuchinhnguyengia(String dieuchinhnguyengia) ;

	public String getSothangkh();

	public void setSothangkh(String sothangkh);

	public String getSothangkhmoi() ;

	public void setSothangkhmoi(String sothangkhmoi) ;

	public String getDieuchinhsothangkh();

	public void setDieuchinhsothangkh(String dieuchinhsothangkh);
	

	
	public List<IKhauHaoDuKien> getKhauhaodukienList() ;
	
	public void setKhauhaodukienList(List<IKhauHaoDuKien> khauhaodukienList) ;
	
	public String getDienGiaiCT();
	public void setDienGiaiCT(String dienGiaiCT);
}
