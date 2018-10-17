package geso.traphaco.distributor.beans.dondathang;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

public interface IDondathang extends Serializable 
{
	public String getUserId();
	public void setUserId(String userId);
		
	public String getId();
	public void setId(String id);

	public String getSize();
	public void setSize(String size);
	
	public String getghichu();
	public void setghichu(String ghichu);
	
	
	public String getNgaydh();
	public void setNgaydh(String ngaydh);
	
	public double getThueSuat();
	public void setThueSuat(double thuesuat);

	public double getChietkhauTrucTiep();
	public void setChietkhauTrucTiep(double chietkhau);
	
	public String getNgaydenghigh();
	public void setNgaydenghigh(String ngaydenghigh);
	
	public String getSoct();
	public void setSoct(String soct);
	
	public String getNguoitao();
	public void setNguoitao(String nguoitao);
	
	public String getNguoisua();
	public void setNguoisua(String nguoisua);
	
	public String getNppTen();
	public void setNppTen(String nppTen);

	public String getNppId();	
	public void setNppId(String id);
	
	public String getNccId();
	public void setNccId(String nccId);

	public ResultSet getDvkdIds();
	public void setDvkdIds(ResultSet dvkdIds);
	
	public String getKbhId();	
	public void setKbhId(String kbhId);
	
	public ResultSet getKbhIds();
	public void setKbhIds(ResultSet kbhIds);
	
	public String getTongtienbVAT();
	public void setTongtienbVAT(String tongtienbVAT);
	
	public String getVat();
	public void setVat(String vat);
	
	public String getTongtienaVAT();
	public void setTongtienaVAT(String tongtienaVAT);

	public String getDvkdId();
	public void setDvkdId(String dvkdId);
	
	public String getGiavanchuyen();
	public void setGiavanchuyen(String Giavanchuyen);
	
	
	public ResultSet getNcc();
	public void setNcc(ResultSet ncc);
	
	public String[] getSpId();
	public void setSpId(String[] spId);

	public String[] gettSpId();
	public void settSpId(String[] tspId);
	
	public String[] getMasp();
	public void setMasp(String[] masp);
	
	public String[] getTensp();
	public void setTensp(String[] tensp);

	public String[] gettTensp();
	public void settTensp(String[] ttensp);

	public String[] getDenghi();
	public void setDenghi(String[] denghi);
	
	public String[] gettDenghi();
	public void settDenghi(String[] tdenghi);

	public String[] getTonicp();
	public void setTonicp(String[] ton);

	public String[] gettTonicp();
	public void settTonicp(String[] tton);

	public Hashtable<String, String> getThieuMasp();
	public String[] getThieuMaspArray();
	public void setThieuMasp(String[] thieu);
	
	public String[] getQuycach();
	public void setQuycach(String[] qc);

	public String[] getDv1();
	public void setDv1(String[] dv1);

	public String[] getSl();
	public void setSl(String[] sl);
	
	public String[] gettSl();
	public void settSl(String[] tsl);
		
	public String[] getSlDuyet();
	public void setSlDuyet(String[] tslduyet);
	
	public String[] gettTienbVAT();
	public void settTienbVAT(String[] ttienbVAT);

	public String[] getTienbVAT();
	public void setTienbVAT(String[] tienbVAT);

	public String getMessage(); 	
	public void setMessage(String msg); 

	public String getMaspstr();
	public void setMaspstr(String maspstr); 
	
	public String[] getDongia();
	public void setDongia(String[] dongia);

	
	public String[] getDongia_tuvc();
	public void setDongia_tuvc(String[] dongia_tuvc);
	
	public String[] getDongia_kovc();
	public void setDongia_kovc(String[] dongia_kovc);
	
	public String[] getGoivc();
	public void setGoivc(String[] Goivc);
	
	public String[] gettDongia();
	public void settDongia(String[] tdongia);

	public String[] getDonvi();	
	public void setDonvi(String[] donvi);

	public String[] gettDonvi();	
	public void settDonvi(String[] tdonvi);
	
	public String[] getKhoiluong();	
	public void setKhoiluong(String[] khoiluong);
	
	public String[] getThetich();	
	public void setThetich(String[] thetich);
	
	public String[] getChietkhau();
	public void setChietkhau(String[] chietkhau);
		
	
	
	public String getDndhId();	
	public void setDndhId(String dndhId);
	//1 la hang doi,0 la hang ban
	
	public String getDoiHang();	
	public void setDoiHang(String doihang);
	
	//thong thuong la don hang ban
	public String getLoaiDonHang();	
	public void setLoaiDonHang(String loaidonhang);
	
	public String getTuVanchuyen();	
	public void settuvanchuyen(String tuvanchuyen);
	
	
	public void init0();
	public void init1();
	public void init2();
	public void init3();
	public void initDisplay();
	public void initSelectboxData();
	public boolean CreateDdhnpp(HttpServletRequest request);	
	public boolean UpdateDdhnpp(HttpServletRequest request);
	public boolean ChotDdhnpp(HttpServletRequest request);
	public void DBclose();	
	public String getLyDoHuy();
	public void setLyDoHuy(String lydohuy);
	public void init_reload(HttpServletRequest request);
	
	public double getChietKhauThuongMai();
	public void setChietKhauThuongMai(double chietkhauthuongmai);
	}
