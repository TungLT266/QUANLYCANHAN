package geso.traphaco.erp.beans.NhomDTKhac;
import java.sql.ResultSet;
public interface IErpNhomDTKhac {

	
	public String getCtyId();
	public void setCtyId(String ctyId);
	
	public String getCtyTen();
	public void setCtyTen(String ctyTen);
	
	public String getId();

	public void setId(String id);
		
	public String getDiengiai();
	
	public void setDiengiai(String diengiai);

	public String getTrangthai();
	
	public void setTrangthai(String trangthai);
	
	public String getNgaytao();
	
	public void setNgaytao(String ngaytao);
	
	public String getNgaysua();
	
	public void setNgaysua(String ngaysua);
	
	public String getNguoitao();
	
	public void setNguoitao(String nguoitao);
	
	public String getNguoisua();
	
	public void setNguoisua(String nguoisua);

	public String getMessage();
	
	public void setMessage(String msg);
		
	public ResultSet geNccList();

	public void setNccList(ResultSet nccList);

	public ResultSet getNccSelected();

	public void setNccSelected(ResultSet nccSelected);

	public ResultSet getVungList();

	public void setVungList(ResultSet vungList);
	
	public ResultSet getKvList();

	public void setKvList(ResultSet kvList);
	
	public ResultSet getNppList();

	public void setNppList(ResultSet nppList);
	
	
	public String getVungId();

	public void setVungId(String vungId);

	public String getKvId();

	public void setKvId(String kvId);

	public String getNppId();

	public void setNppId(String nppId);
	
	public void UpdateRS();

	public boolean saveNewNNcc();
	
	public boolean updateNNcc();   
	
	public void DBClose();
	
	public String[] getNcc();
	public void setNcc(String[] ncc);
	public boolean CheckTKKT();
	
	public String getTen() ;

	public void setTen(String ten) ;


}
