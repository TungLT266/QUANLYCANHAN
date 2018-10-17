package geso.traphaco.erp.beans.lapkehoach;

import java.sql.SQLException;
import java.util.List;

public interface IErpDanhMucVatTuThanhPhan {


	public String getDanhmucvattu_fk() ;

	public void setDanhmucvattu_fk(String danhmucvattu_fk) ;

	public String getSanpham_fk() ;

	public void setSanpham_fk(String sanpham_fk) ;

	public String getSoluong() ;

	public void setSoluong(String soluong) ;

	public String getDvdl_fk() ;

	public void setDvdl_fk(String dvdl_fk) ;
	
	public String getTenVTTP() ;

	public void setTenVTTP(String tenVTTP) ;
	
	public void setTenDonVi(String tenDonVi);

	public String getTenDonVi();
	
	public void initDanhSachVatTu();
	
	public List<IErpDanhMucVatTuThanhPhan> getSptpList() ;

	public void setSptpList(List<IErpDanhMucVatTuThanhPhan> sptpList);
	
	public Boolean createDanhMucVatTuThanhPhan() throws SQLException;
	
	public Boolean updateDanhMucVatTuThanhPhan();

}
