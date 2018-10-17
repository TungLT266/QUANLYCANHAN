package geso.traphaco.erp.beans.parktrongnuoc;

import java.sql.ResultSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface IErpParkTrongNuoc 
{
	public String getUserId();
	public void setUserId(String userId);
	
	public String getCtyId();
	public void setCtyId(String CtyId);	
	
	public String getId();
	public void setId(String id);	
	
	public ResultSet getNccRs();
	public void setNccRs(ResultSet nccRs);

	public void setNccId(String nccId); 
	public String getNccId(); 
	
	public void setLoaihang(String loaihang);
	public String getLoaihang();
	
	public String getNgayghinhan();
	public void setNgayghinhan(String ngayghinhan);
	
	public ResultSet getLoaihangList();
	public void setLoaihangList(ResultSet loaihanglist);
	
	public List<IErpHoadon> getHdList();
	public void setHdList(List<IErpHoadon> hdList);
	
	public List<IErpHoadonSp> getPnkList();
	public void setPnkList(List<IErpHoadonSp> pnkList);
	
	public String getTrangthai();
	public void setTrangthai(String trangthai);
	
	public ResultSet getTienteRs(); 
	public void setTienteRs(ResultSet ttRs); 
	public ResultSet getTienteRs_Display();
	
	public String getTtId(); 
	public void setTtId(String ttId); 
	
	public String getTiente();
	public void setTiente(String tiente); 
	
	public String getMsg();
	public void setMsg(String msg);
	public void createRsLocNcc() ;
	
	public ResultSet getPoNkRs();
	
	public void setPoNkRs(ResultSet poNKRs);

	public void setPoNk_SPRs(ResultSet poNK_SPRs);

	public ResultSet getPoNk_SPRs();

	public void setPoNk_SP_ChonRs(ResultSet poNK_SP_ChonRs);

	public ResultSet getPoNk_SP_ChonRs(String Id);	

	public void setPoNkIds(String nccId); 

	public String getPoNkIdsId(); 
	
	public void createRs();
	public void init();
	public void initDisplay();
	public boolean createPark();
	public boolean updatePark();
	public boolean updatePark_Duyet();
	
	public boolean Duyet();
	public void close();
	
	public String getTinhThueNhapKhau();
	public void setTinhThueNhapKhau(String TinhThueNhapKhau);
	
	public void setRequest(HttpServletRequest request);
	
	public void clearHdlist();
	
	public String getTongsoluong();
	public void setTongsoluong(String tongsoluong);
	
	public String getTigia(); 
	public void setTigia(String tigia); 
	
	public void setPoRs(ResultSet poRs);
	public ResultSet getPoRs();
	
	public void setPoId(String poId); 
	public String getPoId(); 
	
	public void setChitietPoRs(ResultSet ChitietPoRs);
	public ResultSet getChitietPoRs();
		
	public String getThuesuat() ;
	public void setThuesuat(String thuesuat) ;
	
	public void setNCCThayTheTen(String NCCThayTheTen);
	public String getNCCThayTheTen();
	
	public void setNCCThayTheMST(String NCCThayTheMST);
	public String getNCCThayTheMST();
	
	public void setNCCThayTheDiaChi(String NCCThayTheDiaChi);
	public String getNCCThayTheDiaChi();
	
	public void setNCCThayTheId(String NCCThayTheId);
	public String getNCCThayTheId();
	
	public String getnppdangnhap();
	public void setnppdangnhap(String nppdangnhap);
	
	public void setLoaidonmhRs(ResultSet LoaidonmhRs);
	public ResultSet getLoaidonmhRs();
	
	public String getLoaidonmh();
	public void setLoaidonmh(String loaidonmh);
	public String getTrangthaiHD();
	public String getcophieuchi();
}
