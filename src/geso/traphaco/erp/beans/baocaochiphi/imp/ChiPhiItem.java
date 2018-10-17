package geso.traphaco.erp.beans.baocaochiphi.imp;

import geso.traphaco.center.db.sql.Idbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChiPhiItem
{
	private String taiKhoanChiPhi;
	private String phongBan;
	private String khoanMuc;
	private String nhomKhoanMuc;
	private double tongCong;
	private double binhQuan;
	private double thang1;
	private double thang2;
	private double thang3;
	private double thang4;
	private double thang5;
	private double thang6;
	private double thang7;
	private double thang8;
	private double thang9;
	private double thang10;
	private double thang11;
	private double thang12;
	
	//Dung cho bao cao chi tiet khoan muc chi phi
	private String soChungTu;
	private String loaiChungTu;
	private String ngayChungTu;
	private String taiKhoanDoiUng;
	private double soTienTang;
	private double soTienGiam;
	

	public ChiPhiItem()
	{
		this.taiKhoanChiPhi = "";
		this.phongBan = "";
		this.khoanMuc = "";
		this.nhomKhoanMuc = "";
		this.tongCong = 0;
		this.binhQuan = 0;
		this.thang1 = 0;
		this.thang2 = 0;
		this.thang3 = 0;
		this.thang4 = 0;
		this.thang5 = 0;
		this.thang6 = 0;
		this.thang7 = 0;
		this.thang8 = 0;
		this.thang9 = 0;
		this.thang10 = 0;
		this.thang11 = 0;
		this.thang12 = 0;
		//Dung cho bao cao chi tiet khoan muc chi phi
		this.soChungTu = "";
		this.loaiChungTu = "";
		this.ngayChungTu= "";
		this.taiKhoanDoiUng="";
		this.soTienTang =0;
		this.soTienGiam = 0;
	}

	public static String getListFromQuery(Idbutils db, String query, List<ChiPhiItem> list,String nhanBiet)
	{
		String msg = "";
		if(nhanBiet.equals("tonghop"))
		{
			msg = getListFQ(db, query, list);
			List<ChiPhiItem> sumList = new ArrayList<ChiPhiItem>();
			getSumList(list, sumList);
			System.out.println("list tong hop size:"+ sumList.size());
			insertSumListToList(list, sumList);		
		}
		if (nhanBiet.equals("chitiet")){
			msg= getListChiTietFQ(db, query, list);
			List<ChiPhiItem> sumList = new ArrayList<ChiPhiItem>();
			getSumListChiTiet(list,sumList);
			System.out.println("list chi tiet size:" + sumList.size());
			insertSumListToList(list, sumList);		
		}
		return msg;
	}

	private static void getSumList(List<ChiPhiItem> list, List<ChiPhiItem> sumList)
	{
		double tongCongSum = 0;
		double binhQuanSum = 0;
		double thang1Sum = 0;
		double thang2Sum = 0;
		double thang3Sum = 0;
		double thang4Sum = 0;
		double thang5Sum = 0;
		double thang6Sum = 0;
		double thang7Sum = 0;
		double thang8Sum = 0;
		double thang9Sum = 0;
		double thang10Sum = 0;
		double thang11Sum = 0;
		double thang12Sum = 0;
		
		ChiPhiItem sumItem = new ChiPhiItem();
		for (int i = 0; i < list.size(); i++)
		{
			ChiPhiItem item = list.get(i);
			if (i > 0)
			{
				String taiKhoanChiPhiPre = list.get(i - 1).getTaiKhoanChiPhi().substring(0, 3);
				if (!item.getTaiKhoanChiPhi().startsWith(taiKhoanChiPhiPre))
				{
					sumItem = new ChiPhiItem();
					sumItem.setTaiKhoanChiPhi(list.get(i - 1).getTaiKhoanChiPhi());
					sumItem.setTongCong(tongCongSum);
					sumItem.setBinhQuan(binhQuanSum);
					sumItem.setThang1(thang1Sum);
					sumItem.setThang2(thang2Sum);
					sumItem.setThang3(thang3Sum);
					sumItem.setThang4(thang4Sum);
					sumItem.setThang5(thang5Sum);
					sumItem.setThang6(thang6Sum);
					sumItem.setThang7(thang7Sum);
					sumItem.setThang8(thang8Sum);
					sumItem.setThang9(thang9Sum);
					sumItem.setThang10(thang10Sum);
					sumItem.setThang11(thang11Sum);
					sumItem.setThang12(thang12Sum);
					
					sumList.add(sumItem);
					
					tongCongSum = 0;
					binhQuanSum = 0;
					thang1Sum = 0;
					thang2Sum = 0;
					thang3Sum = 0;
					thang4Sum = 0;
					thang5Sum = 0;
					thang6Sum = 0;
					thang7Sum = 0;
					thang8Sum = 0;
					thang9Sum = 0;
					thang10Sum = 0;
					thang11Sum = 0;
					thang12Sum = 0;
				}
				
			}
			
			tongCongSum += item.getTongCong();
			binhQuanSum += item.getBinhQuan();
			thang1Sum += item.getThang1();
			thang2Sum += item.getThang2();
			thang3Sum += item.getThang3();
			thang4Sum += item.getThang4();
			thang5Sum += item.getThang5();
			thang6Sum += item.getThang6();
			thang7Sum += item.getThang7();
			thang8Sum += item.getThang8();
			thang9Sum += item.getThang9();
			thang10Sum += item.getThang10();
			thang11Sum += item.getThang11();
			thang12Sum += item.getThang12();
		}
		
		System.out.println("list.size(): " + list.size());
		if (sumItem != null && list.size() >= 1)
		{
			sumItem = new ChiPhiItem(); 
			ChiPhiItem item = list.get(list.size() - 1);
			sumItem.setTaiKhoanChiPhi(item.getTaiKhoanChiPhi());
			sumItem.setTongCong(tongCongSum);
			sumItem.setBinhQuan(binhQuanSum);
			sumItem.setThang1(thang1Sum);
			sumItem.setThang2(thang2Sum);
			sumItem.setThang3(thang3Sum);
			sumItem.setThang4(thang4Sum);
			sumItem.setThang5(thang5Sum);
			sumItem.setThang6(thang6Sum);
			sumItem.setThang7(thang7Sum);
			sumItem.setThang8(thang8Sum);
			sumItem.setThang9(thang9Sum);
			sumItem.setThang10(thang10Sum);
			sumItem.setThang11(thang11Sum);
			sumItem.setThang12(thang12Sum);
			
			sumList.add(sumItem);
		}
	}
	private static void getSumListChiTiet(List<ChiPhiItem> list, List<ChiPhiItem> sumList)
	{
		double soTienTangSum = 0;
		double soTienGiamSum = 0;
		
		ChiPhiItem sumItem = new ChiPhiItem();
		for (int i = 0; i < list.size(); i++)
		{
			ChiPhiItem item = list.get(i);
			if (i > 0)
			{
				String taiKhoanChiPhiPre = list.get(i - 1).getTaiKhoanChiPhi().substring(0, 3);
				if (!item.getTaiKhoanChiPhi().startsWith(taiKhoanChiPhiPre))
				{
					sumItem = new ChiPhiItem();
					sumItem.setTaiKhoanChiPhi(list.get(i - 1).getTaiKhoanChiPhi());
					sumItem.setSoTienTang(soTienTangSum);
					sumItem.setSoTienGiam(soTienGiamSum);
					
					sumList.add(sumItem);
					
					soTienTangSum = 0;
					soTienGiamSum = 0;
				}
				
			}
			soTienTangSum += item.getSoTienTang();
			soTienGiamSum += item.getSoTienGiam();
		}
		
		System.out.println("list.size(): " + list.size());
		if (sumItem != null && list.size() >= 1)
		{
			sumItem = new ChiPhiItem(); 
			ChiPhiItem item = list.get(list.size() - 1);
			sumItem.setTaiKhoanChiPhi(item.getTaiKhoanChiPhi());
			sumItem.setSoTienTang(soTienTangSum);
			sumItem.setSoTienGiam(soTienGiamSum);
			sumList.add(sumItem);
		}
	}

	private static void insertSumListToList(List<ChiPhiItem> list, List<ChiPhiItem> sumList)
	{
		for (int i = 0; i < sumList.size(); i++)
		{
			ChiPhiItem sumItem = sumList.get(i);
			int index = getSumItemIndexInList(list, sumItem);
			if (sumItem.getTaiKhoanChiPhi().startsWith("632"))
			{
				sumItem.setTaiKhoanChiPhi(""); 
				sumItem.setKhoanMuc("1. Giá vốn");
			}
			else if (sumItem.getTaiKhoanChiPhi().startsWith("635"))
			{
				sumItem.setTaiKhoanChiPhi(""); 
				sumItem.setKhoanMuc("2. Chi phí tài chính");
			}
			else if (sumItem.getTaiKhoanChiPhi().startsWith("641"))
			{
				sumItem.setTaiKhoanChiPhi(""); 
				sumItem.setKhoanMuc("3. Chi phí bán hàng (gồn các Khoản mục chi phí có tài khoản bên dưới là 641xxx)");
			}
			else if (sumItem.getTaiKhoanChiPhi().startsWith("642"))
			{
				sumItem.setTaiKhoanChiPhi(""); 
				sumItem.setKhoanMuc("4. Chi phí quản lý (gồn các Khoản mục chi phí có tài khoản bên dưới là 642xxx)");
			}
			
			list.add(index, sumList.get(i));
		}
	}

	private static int getSumItemIndexInList(List<ChiPhiItem> list, ChiPhiItem sumItem)
	{
		for (int i = 0; i < list.size(); i++)
		{
			if (list.get(i).getTaiKhoanChiPhi().startsWith(sumItem.getTaiKhoanChiPhi().substring(0, 3)))
				return i;
		}
		return -1;
	}
	
	private static String getListFQ(Idbutils db, String query, List<ChiPhiItem> list)
	{
		String msg = "";
		
		if (list == null)
			list = new ArrayList<ChiPhiItem>();
		else
			list.clear();	

		ResultSet rs = null;
		
		try {
			rs = db.get(query);
			
			if (rs != null)
				while (rs.next())
				{
					ChiPhiItem item = new ChiPhiItem();
					String taiKhoanChiPhi = rs.getString("SOHIEUTAIKHOAN");
					String phongBan = rs.getString("DVTHTEN");
					String khoanMuc = rs.getString("NCPTEN");
					String nhomKhoanMuc =rs.getString("NKMTEN");
					double tongCong = rs.getDouble("tongCong");
					double binhQuan = rs.getDouble("binhQuan");
					double thang1 = rs.getDouble("thang1");
					double thang2 = rs.getDouble("thang2");
					double thang3 = rs.getDouble("thang3");
					double thang4 = rs.getDouble("thang4");
					double thang5 = rs.getDouble("thang5");
					double thang6 = rs.getDouble("thang6");
					double thang7 = rs.getDouble("thang7");
					double thang8 = rs.getDouble("thang8");
					double thang9 = rs.getDouble("thang9");
					double thang10 = rs.getDouble("thang10");
					double thang11 = rs.getDouble("thang11");
					double thang12 = rs.getDouble("thang12");
					
					item.setTaiKhoanChiPhi(taiKhoanChiPhi);
					item.setPhongBan(phongBan);
					item.setKhoanMuc(khoanMuc);
					item.setNhomKhoanMuc(nhomKhoanMuc);
					item.setTongCong(tongCong);
					item.setBinhQuan(binhQuan);
					item.setThang1(thang1);
					item.setThang2(thang2);
					item.setThang3(thang3);
					item.setThang4(thang4);
					item.setThang5(thang5);
					item.setThang6(thang6);
					item.setThang7(thang7);
					item.setThang8(thang8);
					item.setThang9(thang9);
					item.setThang10(thang10);
					item.setThang11(thang11);
					item.setThang12(thang12);
					list.add(item);
				}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			msg = "GLFQ1.0 Lỗi đổ list";
		}
		finally{
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		return msg;
	}
	private static String getListChiTietFQ(Idbutils db, String query, List<ChiPhiItem> list)
	{
		String msg = "";
		
		if (list == null)
			list = new ArrayList<ChiPhiItem>();
		else
			list.clear();	

		ResultSet rs = null;
		
		try {
			rs = db.get(query);
			
			if (rs != null)
				while (rs.next())
				{
					ChiPhiItem item = new ChiPhiItem();
					String taiKhoanChiPhi = rs.getString("SOHIEUTAIKHOAN");
					String phongBan = rs.getString("DVTHTEN");
					String khoanMuc = rs.getString("NCPTEN");
					String nhomKhoanMuc = rs.getString("NKMTEN");
					String soChungTu = rs.getString("SOCHUNGTU");
					String ngayChungTu = rs.getString("NGAYCHUNGTU");
					String loaiChungTu = rs.getString("LOAICHUNGTU");
					double soTienTang = rs.getDouble("SOTIENTANG");
					double soTienGiam = rs.getDouble("SOTIENGIAM");
					String taiKhoanDoiUng = rs.getString("TAIKHOANDOIUNG");
					
					item.setTaiKhoanChiPhi(taiKhoanChiPhi);
					item.setPhongBan(phongBan);
					item.setKhoanMuc(khoanMuc);
					item.setNhomKhoanMuc(nhomKhoanMuc);
					item.setSoChungTu(soChungTu);
					item.setLoaiChungTu(loaiChungTu);
					item.setNgayChungTu(ngayChungTu);
					item.setSoTienTang(soTienTang);
					item.setSoTienGiam(soTienGiam);
					item.setTaiKhoanDoiUng(taiKhoanDoiUng);
					list.add(item);
				}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			msg = "GLFQ1.0 Lỗi đổ list";
		}
		finally{
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		return msg;
	}
	
	public double getThang(String thang)
	{
		if (thang.trim().toLowerCase().equals("thang1"))
			return this.thang1;
		if (thang.trim().toLowerCase().equals("thang2"))
			return this.thang2;
		if (thang.trim().toLowerCase().equals("thang3"))
			return this.thang3;
		if (thang.trim().toLowerCase().equals("thang4"))
			return this.thang4;
		if (thang.trim().toLowerCase().equals("thang5"))
			return this.thang5;
		if (thang.trim().toLowerCase().equals("thang6"))
			return this.thang6;
		if (thang.trim().toLowerCase().equals("thang7"))
			return this.thang7;
		if (thang.trim().toLowerCase().equals("thang8"))
			return this.thang8;
		if (thang.trim().toLowerCase().equals("thang9"))
			return this.thang9;
		if (thang.trim().toLowerCase().equals("thang10"))
			return this.thang10;
		if (thang.trim().toLowerCase().equals("thang11"))
			return this.thang11;
		if (thang.trim().toLowerCase().equals("thang12"))
			return this.thang12;
		return 0;
	}
	public String getTaiKhoanChiPhi() {
		return taiKhoanChiPhi;
	}

	public void setTaiKhoanChiPhi(String taiKhoanChiPhi) {
		this.taiKhoanChiPhi = taiKhoanChiPhi;
	}

	public String getPhongBan() {
		return phongBan;
	}

	public void setPhongBan(String phongBan) {
		this.phongBan = phongBan;
	}

	public String getKhoanMuc() {
		return khoanMuc;
	}

	public void setKhoanMuc(String khoanMuc) {
		this.khoanMuc = khoanMuc;
	}
	
	public String getNhomKhoanMuc(){
		return this.nhomKhoanMuc;
	}
	public void setNhomKhoanMuc(String nhomKhoanMuc){
		this.nhomKhoanMuc = nhomKhoanMuc;
	}

	public double getTongCong() {
		return tongCong;
	}

	public void setTongCong(double tongCong) {
		this.tongCong = tongCong;
	}

	public double getBinhQuan() {
		return binhQuan;
	}

	public void setBinhQuan(double binhQuan) {
		this.binhQuan = binhQuan;
	}

	public double getThang1() {
		return thang1;
	}

	public void setThang1(double thang1) {
		this.thang1 = thang1;
	}

	public double getThang2() {
		return thang2;
	}

	public void setThang2(double thang2) {
		this.thang2 = thang2;
	}

	public double getThang3() {
		return thang3;
	}

	public void setThang3(double thang3) {
		this.thang3 = thang3;
	}

	public double getThang4() {
		return thang4;
	}

	public void setThang4(double thang4) {
		this.thang4 = thang4;
	}

	public double getThang5() {
		return thang5;
	}

	public void setThang5(double thang5) {
		this.thang5 = thang5;
	}

	public double getThang6() {
		return thang6;
	}

	public void setThang6(double thang6) {
		this.thang6 = thang6;
	}

	public double getThang7() {
		return thang7;
	}

	public void setThang7(double thang7) {
		this.thang7 = thang7;
	}

	public double getThang8() {
		return thang8;
	}

	public void setThang8(double thang8) {
		this.thang8 = thang8;
	}

	public double getThang9() {
		return thang9;
	}

	public void setThang9(double thang9) {
		this.thang9 = thang9;
	}

	public double getThang10() {
		return thang10;
	}

	public void setThang10(double thang10) {
		this.thang10 = thang10;
	}

	public double getThang11() {
		return thang11;
	}

	public void setThang11(double thang11) {
		this.thang11 = thang11;
	}

	public double getThang12() {
		return thang12;
	}

	public void setThang12(double thang12) {
		this.thang12 = thang12;
	}
	public String getSoChungTu(){
		return this.soChungTu;
	}
	public void setSoChungTu(String soChungTu){
		this.soChungTu = soChungTu;
	}
	public String getLoaiChungTu(){
		return this.loaiChungTu;
	}
	public void setLoaiChungTu(String loaiChungTu){
		this.loaiChungTu = loaiChungTu;
	}
	public String getNgayChungTu(){
		return this.ngayChungTu;
	}
	public void setNgayChungTu(String ngayChungTu){
		this.ngayChungTu = ngayChungTu;
	}
	public String getTaiKhoanDoiUng(){
		return this.taiKhoanDoiUng;
	}
	public void setTaiKhoanDoiUng(String taiKhoanDoiUng){
		this.taiKhoanDoiUng = taiKhoanDoiUng;
	}
	public double getSoTienTang(){
		return this.soTienTang;
	}
	public void setSoTienTang(double soTienTang){
		this.soTienTang = soTienTang;
	}
	public double getSoTienGiam(){
		return this.soTienGiam;
	}
	public void setSoTienGiam(double soTienGiam){
		this.soTienGiam = soTienGiam;
	}
	
}