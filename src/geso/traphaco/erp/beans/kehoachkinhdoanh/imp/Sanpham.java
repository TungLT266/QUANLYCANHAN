package geso.traphaco.erp.beans.kehoachkinhdoanh.imp;

import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.beans.kehoachkinhdoanh.*;

public class Sanpham implements ISanpham
{
	private static final long serialVersionUID = -9217977546733610214L;
	
	String id;
	String masp;
	String tensp;
	String thang1;
	String thang2;
	String thang3;
	String thang4;
	String thang5;
	String thang6;
	String thang7;
	String thang8;
	String thang9;
	String thang10;
	String thang11;
	String thang12;

	public Sanpham()
	{
		this.id = "";
		this.masp = "";
		this.tensp = "";
		this.thang1 = "";
		this.thang2 = "";
		this.thang3 = "";
		this.thang4 = "";
		this.thang5 = "";
		this.thang6 = "";
		this.thang7 = "";
		this.thang8 = "";
		this.thang9 = "";
		this.thang10 = "";
		this.thang11 = "";
		this.thang12 = "";
	}

	public Sanpham(String[] param)
	{
		this.id = param[0];
		this.masp = param[1];
		this.tensp = param[2];
		this.thang1 = param[3];
		this.thang2 = param[4];
		this.thang3 = param[5];
		this.thang4 = param[6];
		this.thang5 = param[7];
		this.thang6 = param[8];
		this.thang7 = param[9];
		this.thang8 = param[10];
		this.thang9 = param[11];
		this.thang10 = param[12];
		this.thang11 = param[13];
		this.thang12 = param[14];
	}

	public Sanpham(String spId, String spMa, String spTen, String t1, String t2, String t3, String t4, String t5, String t6, String t7, String t8
			, String t9, String t10, String t11, String t12)
	{
		this.id = spId;
		this.masp = spMa;
		this.tensp = spTen;
		this.thang1 = t1;
		this.thang2 = t2;
		this.thang3 = t3;
		this.thang4 = t4;
		this.thang5 = t5;
		this.thang6 = t6;
		this.thang7 = t7;
		this.thang8 = t8;
		this.thang9 = t9;
		this.thang10 = t10;
		this.thang11 = t11;
		this.thang12 = t12;
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getMasanpham()
	{
		return this.masp;
	}

	public void setMasanpham(String masp)
	{
		this.masp = masp;
	}

	public String getTensanpham()
	{
		return this.tensp;
	}

	public void setTensanpham(String tensp)
	{
		this.tensp = tensp;
	}
	
	@Override
	public String getThang1() {
		// TODO Auto-generated method stub
		return this.thang1;
	}

	@Override
	public void setThang1(String thang1) {
		// TODO Auto-generated method stub
		this.thang1 = thang1;
	}

	@Override
	public String getThang2() {
		// TODO Auto-generated method stub
		return this.thang2;
	}

	@Override
	public void setThang2(String thang2) {
		// TODO Auto-generated method stub
		this.thang2 = thang2;
	}

	@Override
	public String getThang3() {
		// TODO Auto-generated method stub
		return this.thang3;
	}

	@Override
	public void setThang3(String thang3) {
		// TODO Auto-generated method stub
		this.thang3 = thang3;
	}

	@Override
	public String getThang4() {
		// TODO Auto-generated method stub
		return this.thang4;
	}

	@Override
	public void setThang4(String thang4) {
		// TODO Auto-generated method stub
		this.thang4 = thang4;
	}

	@Override
	public String getThang5() {
		// TODO Auto-generated method stub
		return this.thang5;
	}

	@Override
	public void setThang5(String thang5) {
		// TODO Auto-generated method stub
		this.thang5 = thang5;
	}

	@Override
	public String getThang6() {
		// TODO Auto-generated method stub
		return this.thang6;
	}

	@Override
	public void setThang6(String thang6) {
		// TODO Auto-generated method stub
		this.thang6 = thang6;
	}

	@Override
	public String getThang7() {
		// TODO Auto-generated method stub
		return this.thang7;
	}

	@Override
	public void setThang7(String thang7) {
		// TODO Auto-generated method stub
		this.thang7 = thang7;
	}

	@Override
	public String getThang8() {
		// TODO Auto-generated method stub
		return this.thang8;
	}

	@Override
	public void setThang8(String thang8) {
		// TODO Auto-generated method stub
		this.thang8 = thang8;
	}

	@Override
	public String getThang9() {
		// TODO Auto-generated method stub
		return this.thang9;
	}

	@Override
	public void setThang9(String thang9) {
		// TODO Auto-generated method stub
		this.thang9 = thang9;
	}

	@Override
	public String getThang10() {
		// TODO Auto-generated method stub
		return this.thang10;
	}

	@Override
	public void setThang10(String thang10) {
		// TODO Auto-generated method stub
		this.thang10 = thang10;
	}

	@Override
	public String getThang11() {
		// TODO Auto-generated method stub
		return this.thang11;
	}

	@Override
	public void setThang11(String thang11) {
		// TODO Auto-generated method stub
		this.thang11 = thang11;
	}

	@Override
	public String getThang12() {
		// TODO Auto-generated method stub
		return this.thang12;
	}

	@Override
	public void setThang12(String thang12) {
		// TODO Auto-generated method stub
		this.thang12 = thang12;
	}

}
