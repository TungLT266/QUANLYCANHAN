package geso.traphaco.erp.beans.danhgianhacc.imp;
import geso.traphaco.erp.beans.danhgianhacc.INhacungcap;
import geso.traphaco.center.db.sql.*;

import java.sql.ResultSet;

public class Nhacungcap implements INhacungcap
{
	private static final long serialVersionUID = -9217977546733610214L;
	String id;
	String ma;
	String ten;
	String nguoilienhe;
	String diachi;
	String dienthoai;
	String trangthai;
	String ngaytao;
	String ngaysua;
	String nguoitao;
	String nguoisua;
	String msg;
	String sopo;
	String dotincay;
	String chatluong;
	String khanangcn;
	String giaca;
	String ptthanhtoan;
	String tggiaohang;
	String haumai;
	String tongluong;
	String chon;
	dbutils db;
	
	public Nhacungcap()
	{
		this.id = "";
		this.ma = "";
		this.ten = "";
		this.diachi = "";
		this.trangthai = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.nguoilienhe = "";
		this.dienthoai = "";
		this.dotincay = "";
		this.chatluong = "";
		this.khanangcn = "";
		this.giaca = "";
		this.ptthanhtoan = "";
		this.tggiaohang = "";
		this.haumai = "";
		this.sopo = "";
		this.tongluong = "";
		this.chon = "0";
		this.msg = "";
		this.db = new dbutils();
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
	
	public String getDiachi()
	{
		return this.diachi;
	}
	
	public void setDiachi(String diachi)
	{
		this.diachi = diachi;
	}

	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	public String getNgaytao()
	{
		return this.ngaytao;
		
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
		this.ngaysua = ngaysua;
	}
	
	public String getNguoitao()
	{
		return this.nguoitao;
	}
	
	public void setNguoitao(String nguoitao)
	{
		this.nguoitao = nguoitao;
	}

	public String getNguoisua()
	{
		return this.nguoisua;
	}

	public void setNguoisua(String nguoisua)
	{
		this.nguoisua = nguoisua;
	}
	
	public String getMessage()
	{
		return this.msg;
	}

	public void setMessage(String msg)
	{
		this.msg = msg;
	}

	@Override
	public String getNguoilienhe() {
		// TODO Auto-generated method stub
		return this.nguoilienhe;
	}

	@Override
	public void setNguoilienhe(String nglienhe) {
		// TODO Auto-generated method stub
		this.nguoilienhe = nglienhe;
	}

	@Override
	public String getDienthoai() {
		// TODO Auto-generated method stub
		return this.dienthoai;
	}

	@Override
	public void setDienthoai(String dienthoai) {
		// TODO Auto-generated method stub
		this.dienthoai = dienthoai;
	}

	@Override
	public String getDotincay() {
		// TODO Auto-generated method stub
		return this.dotincay;
	}

	@Override
	public void setDotincay(String dotincay) {
		// TODO Auto-generated method stub
		this.dotincay = dotincay;
	}

	@Override
	public String getChatluong() {
		// TODO Auto-generated method stub
		return this.chatluong;
	}

	@Override
	public void setChatluong(String chatluong) {
		// TODO Auto-generated method stub
		this.chatluong = chatluong;
	}

	@Override
	public String getKhanangcn() {
		// TODO Auto-generated method stub
		return this.khanangcn;
	}

	@Override
	public void setKhanangcn(String khanangcn) {
		// TODO Auto-generated method stub
		this.khanangcn = khanangcn;
	}

	@Override
	public String getGiaca() {
		// TODO Auto-generated method stub
		return this.giaca;
	}

	@Override
	public void setGiaca(String giaca) {
		// TODO Auto-generated method stub
		this.giaca = giaca;
	}

	@Override
	public String getPtthanhtoan() {
		// TODO Auto-generated method stub
		return this.ptthanhtoan;
	}

	@Override
	public void setPtthanhtoan(String ptthanhtoan) {
		// TODO Auto-generated method stub
		this.ptthanhtoan = ptthanhtoan;
	}

	@Override
	public String getTggiaohang() {
		// TODO Auto-generated method stub
		return this.tggiaohang;
	}

	@Override
	public void setTggiaohang(String tggiaohang) {
		// TODO Auto-generated method stub
		this.tggiaohang = tggiaohang;
	}

	@Override
	public String getHaumai() {
		// TODO Auto-generated method stub
		return this.haumai;
	}

	@Override
	public void setHaumai(String haumai) {
		// TODO Auto-generated method stub
		this.haumai = haumai;
	}

	@Override
	public String getSopo() {
		// TODO Auto-generated method stub
		return this.sopo;
	}

	@Override
	public void setSopo(String sopo) {
		// TODO Auto-generated method stub
		this.sopo = sopo;
	}

	@Override
	public String getTongluong() {
		// TODO Auto-generated method stub
		return this.tongluong;
	}

	@Override
	public void setTongluong(String tongluong) {
		// TODO Auto-generated method stub
		this.tongluong = tongluong;
	}

	@Override
	public String getChon() {
		// TODO Auto-generated method stub
		return this.chon;
	}

	@Override
	public void setChon(String chon) {
		// TODO Auto-generated method stub
		this.chon = chon;
	}

	
	
}
