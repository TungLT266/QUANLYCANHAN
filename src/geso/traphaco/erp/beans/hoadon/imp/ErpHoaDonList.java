package geso.traphaco.erp.beans.hoadon.imp;

import geso.traphaco.erp.beans.hoadon.IErpHoaDonList;
import geso.traphaco.erp.beans.hoadon.IHdDetail;

import java.util.ArrayList;
import java.util.List;

public class ErpHoaDonList implements IErpHoaDonList 
{

	String id, soxuatkho, ngayxuathd, sohoadon, tongtienavat, khachhangId, khachhangTen, trangthai, hoantat, ngaytao, ngaysua, nguoitao, nguoisua ;
	
	List<IHdDetail> hdDetailList;
	
	public ErpHoaDonList()
	{
		this.id = "";
		this.soxuatkho = "";
		this.sohoadon = "";
		this.tongtienavat = "";
		this.khachhangId = "";
		this.khachhangTen = "";
		this.trangthai = "";
		this.ngaytao = "";
		this.ngaysua = "";
		this.nguoitao = "";
		this.nguoisua = "";
		this.hoantat = "";
		
		this.hdDetailList = new ArrayList<IHdDetail>();
	}
	
	public ErpHoaDonList(String id, String soxuatkho, String ngayxuathd, String sohoadon, String tongtienavat, String khachhangId,
			             String khachhangTen, String trangthai, String hoantat, String ngaytao, String ngaysua, String nguoitao, String nguoisua)
	{
		this.id = id;
		this.soxuatkho = soxuatkho;
		this.sohoadon = sohoadon;
		this.ngayxuathd = ngayxuathd;
		this.tongtienavat = tongtienavat;
		this.khachhangId = khachhangId;
		this.khachhangTen = khachhangTen;
		this.trangthai = trangthai;
		this.ngaytao = ngaytao;
		this.ngaysua = ngaysua;
		this.nguoitao = nguoitao;
		this.nguoisua = nguoisua;
		this.hoantat = hoantat;
		
		this.hdDetailList = new ArrayList<IHdDetail>();
	}
	
	public String getId() 
	{
		return this.id;
	}

	public void setId(String id) 
	{
		this.id = id;
	}

	public String getSoxuatkho() 
	{
		return this.soxuatkho;
	}

	public void setSoxuatkho(String soxuatkho) 
	{
		this.soxuatkho =soxuatkho ;
	}

	public String getNgayxuathd() 
	{
		return this.ngayxuathd;
	}

	public void setNgayxuathd(String ngayxuathd) 
	{
		this.ngayxuathd = ngayxuathd ;
	}

	public String getSohoadon() 
	{
		return this.sohoadon;
	}

	public void setSohoadon(String sohoadon) 
	{
		this.sohoadon = sohoadon;
	}

	public String getTongtienavat() 
	{
		return this.tongtienavat;
	}

	public void setTongtienavat(String tongtienavat) 
	{
		this.tongtienavat = tongtienavat;
	}

	public String getKhachhangId() 
	{
		return this.khachhangId;
	}

	public void setKhachhangId(String khachhangId) 
	{
		this.khachhangId = khachhangId;
	}

	public String getKhachhangTen() 
	{
		return this.khachhangTen;
	}

	public void setKhachhangTen(String khachhangTen) 
	{
		this.khachhangTen = khachhangTen ;
	
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai ;
	}

	public String getNgaytao() 
	{
		return this.ngaytao ;
	}

	public void setNgaytao(String ngaytao) 
	{
		this.ngaytao = ngaytao;
	}

	public String getNgaysua() 
	{
		return this.ngaysua;
	}

	public void setNgaysua(String ngaysua) 
	{
		this.ngaysua = ngaysua ;
	}

	public String getNguoitao() 
	{
		return this.nguoitao;
	}

	public void setNguoitao(String nguoitao) 
	{
		this.nguoitao = nguoitao ;
	}

	public String getNguoisua() 
	{
		return this.nguoisua;
	}

	public void setNguoisua(String nguoisua) 
	{
		this.nguoisua = nguoisua;
	}

	public List<IHdDetail> getHdDetailList() 
	{
		return this.hdDetailList;
	}

	public void setHdDetailList(List<IHdDetail> hdDetailList) 
	{
		this.hdDetailList = hdDetailList;
	}

	public String getHoantat() 
	{
		return this.hoantat;
	}

	public void setHoantat(String hoantat) 
	{
		this.hoantat = hoantat;
	}

}
