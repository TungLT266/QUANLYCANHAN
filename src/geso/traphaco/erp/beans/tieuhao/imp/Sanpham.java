package geso.traphaco.erp.beans.tieuhao.imp;

import java.util.List;

import geso.traphaco.erp.beans.tieuhao.ISanpham;
import geso.traphaco.erp.beans.tieuhao.ISanphamLo;

public class Sanpham implements ISanpham
{
	String stt;
	String id;
	String ma;
	String ten;
	String tieuhaoId;
	String dvt = "";
	double soluongchuan;
	double soluongthucte;
	double soluongdatieuhao;
	double soluongDmtieuhao;
	double soluongxuat;
	
	List<ISanphamLo> spList;
	
	public Sanpham()
	{
		this.stt = "";
		this.id = "";
		this.ma = "";
		this.ten = "";
		this.tieuhaoId = "";
		this.soluongchuan = 0;
		this.soluongthucte = 0;
		this.soluongdatieuhao=0;
		this.soluongDmtieuhao=0;
		
	}
	
	public String getStt()
	{
		return this.stt;
	}
	
	public void setStt(String stt)
	{
		this.stt = stt;
	}
	
	public String getId()
	{
		return this.id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getMa()
	{
		return this.ma;
	}
	
	public void setMa(String ma)
	{
		this.ma = ma;
	}
	
	public String getTen()
	{
		return this.ten;
	}
	
	public void setTen(String ten)
	{
		this.ten = ten;
	}
	
	public String getTieuHaoId()
	{
		return this.tieuhaoId;
	}
	
	public void setTieuHaoId(String tieuhaoId)
	{
		this.tieuhaoId = tieuhaoId;
	}
	
	public double getSoLuongChuan()
	{
		return this.soluongchuan;
	}
	
	public void setSoLuongChuan(double soluongchuan)
	{
		this.soluongchuan = soluongchuan;
	}
	
	public double getSoLuongThucTe()
	{
		return this.soluongthucte;
	}
	
	public void setSoLuongThucTe(double soluongthucte)
	{
		this.soluongthucte = soluongthucte;
	}

	@Override
	public List<ISanphamLo> getSpList() {
		return this.spList;
	}

	@Override
	public void setSpList(List<ISanphamLo> spList) {
		this.spList=spList ;
	}

	@Override
	public String getDonViTinh() {
		return this.dvt;
	}

	@Override
	public void setDonViTinh(String dvt) {
		this.dvt = dvt;
	}

	@Override
	public double getSoLuongDaTieuHao() {
		// TODO Auto-generated method stub
		return this.soluongdatieuhao;
	}

	@Override
	public void setSoLuongDaTieuHao(double SoLuongDaTieuHao) {
		// TODO Auto-generated method stub
		this.soluongdatieuhao=SoLuongDaTieuHao;
	}

	@Override
	public double getsoluongDMTieuhao() {
		// TODO Auto-generated method stub
		return soluongDmtieuhao;
	}

	@Override
	public void setsoluongDMTieuhao(double soluongdmtieuhao) {
		// TODO Auto-generated method stub
		soluongDmtieuhao=soluongdmtieuhao;
	}

	@Override
	public double getsoluongXuat() {
		// TODO Auto-generated method stub
		return soluongxuat;
	}

	@Override
	public void setsoluongXuat(double soluongxuat) {
		// TODO Auto-generated method stub
		this.soluongxuat=soluongxuat;
	}
}
