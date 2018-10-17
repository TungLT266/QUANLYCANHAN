

package geso.traphaco.center.beans.chitieunhanvienetc.imp;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import geso.traphaco.center.beans.chitieunhanvienetc.ICTNhanvienETC;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.center.beans.chitieunhanvienetc.imp.CTNhanvienETC_NSP;
import geso.traphaco.center.beans.chitieunhanvienetc.ICTNhanvienETC_NSP;
public class CTNhanvienETC  implements ICTNhanvienETC  {


	String Id = "";
	String nppId ="";
	String ten = "";
	
	double DoanhSoBanRa  = 0;
	double SoLuongBanRa  = 0;
	double DoanhSoMuaVao  = 0;
	double SoLuongMuaVao  = 0;
	
	double SoVTcoDonHang = 0;
	double SoKhMuaHang = 0;
	double SoDhTrongNgay  = 0;
	double GiaTriDonHang = 0;
	double GiaTriSanPham = 0;
	
	double KhachHangMoi = 0;
	double TyleKhMuahang =0;
	double ThanhToanNpp = 0;
	String loainv = "1";
	
	
	List<ICTNhanvienETC_NSP> ctNspList = new ArrayList<ICTNhanvienETC_NSP>();
	
	
	
	public CTNhanvienETC(String[] param,dbutils db,String chitieuId)
	{
		this.Id = param[0];
		this.nppId = param[1];
		this.ten  = param[2];
		
		this.DoanhSoBanRa = Double.parseDouble(param[3]);
		this.SoLuongBanRa = Double.parseDouble(param[4]);
		this.DoanhSoMuaVao = Double.parseDouble(param[5]);
		this.SoLuongMuaVao = Double.parseDouble(param[6]);
		
		this.SoVTcoDonHang = Double.parseDouble(param[7]);
		this.SoKhMuaHang = Double.parseDouble(param[8]);
		this.SoDhTrongNgay  = Double.parseDouble(param[9]);
		this.GiaTriDonHang = Double.parseDouble(param[10]);
		this.GiaTriSanPham = Double.parseDouble(param[11]);
		
		this.KhachHangMoi = Double.parseDouble(param[12]);
		this.TyleKhMuahang =Double.parseDouble(param[13]);
		this.ThanhToanNpp = Double.parseDouble(param[14]);
		this.loainv =param[15];
		
		this.initNSP(chitieuId,db);
	}
	
	public void initNSP(String chitieuId,dbutils db)
	{
		try
		{
			ctNspList = new ArrayList<ICTNhanvienETC_NSP>();			
			String query = "";
			if(loainv.equals("1"))// SR
			{
				query ="select a.loai, a.nsp_fk,b.TEN as tennsp, a.Doanhso  -- " + 
				 "\n  from ChiTieuNhanVien_SR_NSP a -- " + 
				 "\n 	inner join nhomsanphamchitieu b on a.NSP_FK = b.PK_SEQ -- " + 
				 "\n where CTNVCT_FK ='"+this.Id+"'";
			}else
			if(loainv.equals("2"))// SS
			{
				query ="select a.loai, a.nsp_fk,b.TEN as tennsp, a.Doanhso  -- " + 
				 "\n  from ChiTieuNhanVien_SS_NSP a -- " + 
				 "\n 	inner join nhomsanphamchitieu b on a.NSP_FK = b.PK_SEQ -- " + 
				 "\n where CTNVCT_FK ='"+this.Id+"'";
			}else
			if(loainv.equals("3"))// asm
			{
				query ="select a.loai, a.nsp_fk,b.TEN as tennsp, a.Doanhso  -- " + 
				 "\n  from ChiTieuNhanVien_ASM_NSP a -- " + 
				 "\n 	inner join nhomsanphamchitieu b on a.NSP_FK = b.PK_SEQ -- " + 
				 "\n where CTNVCT_FK ='"+this.Id+"'";
			}else
			if(loainv.equals("4"))// rsm
			{
				query ="select a.loai, a.nsp_fk,b.TEN as tennsp, a.Doanhso  -- " + 
				 "\n  from ChiTieuNhanVien_RSM_NSP a -- " + 
				 "\n 	inner join nhomsanphamchitieu b on a.NSP_FK = b.PK_SEQ -- " + 
				 "\n where CTNVCT_FK ='"+this.Id+"'";
			}
			
			ResultSet rs = db.get(query);
			while(rs.next())
			{
				String[] param = new String[4];
				param[0] = rs.getString("loai");
				param[1] = rs.getString("nsp_fk");
				param[2] = rs.getString("tennsp");
				param[3] = rs.getString("Doanhso");
				
				ICTNhanvienETC_NSP a = new CTNhanvienETC_NSP(param);
				ctNspList.add(a);
			}
			rs.close();
		}
		catch (Exception e) {
			 ctNspList = new ArrayList<ICTNhanvienETC_NSP>();
		}
	}
	
	public List<ICTNhanvienETC_NSP> getCtNspList() {
		return ctNspList;
	}
	public void setCtNspList(List<ICTNhanvienETC_NSP> ctNspList) {
		this.ctNspList = ctNspList;
	}
	
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public void setTen(String ten) {
		this.ten = ten;
	}
	public String getTen() {
		return ten;
	}
	public String getNppId() {
		return nppId;
	}
	public void setNppId(String manv) {
		this.nppId = manv;
	}
	
	//this.DoanhSoBanRa = Double.parseDouble(param[3]);
	public double getDoanhSoBanRa() {
		return DoanhSoBanRa;
	}
	//this.SoLuongBanRa = Double.parseDouble(param[4]);
	public double getSoLuongBanRa() {
		return SoLuongBanRa;
	}
	//this.DoanhSoMuaVao = Double.parseDouble(param[5]);
	public double getDoanhSoMuaVao() {
		return DoanhSoMuaVao;
	}	
	//this.SoLuongMuaVao = Double.parseDouble(param[6]);
	public double getSoLuongMuaVao() {
		return SoLuongMuaVao;
	}
	
	
	public double getGiaTriDonHang() {
		return GiaTriDonHang;
	}
	public double getGiaTriSanPham() {
		return GiaTriSanPham;
	}
	public double getKhachHangMoi() {
		return KhachHangMoi;
	}
	public String getLoainv() {
		return loainv;
	}
	public double getSoDhTrongNgay() {
		return SoDhTrongNgay;
	}
	public double getSoKhMuaHang() {
		return SoKhMuaHang;
	}
	public double getSoVTcoDonHang() {
		return SoVTcoDonHang;
	}
	public double getThanhToanNpp() {
		return ThanhToanNpp;
	}
	public double getTyleKhMuahang() {
		return TyleKhMuahang;
	}
	
	
}
