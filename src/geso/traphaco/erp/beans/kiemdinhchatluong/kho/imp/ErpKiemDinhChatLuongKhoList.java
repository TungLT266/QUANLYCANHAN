package geso.traphaco.erp.beans.kiemdinhchatluong.kho.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.kiemdinhchatluong.kho.IErpKiemDinhChatLuongKhoList;

public class ErpKiemDinhChatLuongKhoList extends Phan_Trang implements IErpKiemDinhChatLuongKhoList{
	String userId;
	String tungay;
	String denngay;
	String tungayNH;
	String denngayNH;
	String trangthai; 
	String ma;
	String sanpham;
	String diengiai;
	String sonhanhang = "";
	String sochungtu = "";
	String msg;
	String ngaynhan;
	// them truong loai mua hang de phan biet mua hang trong nuoc va nhap khau
	String loaimh;
	ResultSet kdclRs;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	
	public ErpKiemDinhChatLuongKhoList()
	{
		this.userId = "";
		this.tungay = "";
		this.denngay = "";
		this.tungayNH = "";
		this.denngayNH = "";
		this.ma = "";
		this.sanpham = "";
		this.trangthai = "";
		this.diengiai = "";
		this.msg = "";
		this.sochungtu="";
		this.ngaynhan = "";
		currentPages = 1;
		num = 1;
		
		this.db = new dbutils();
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;	
	}

	public String getMa() 
	{
		return this.ma;
	}

	public void setMa(String ma) 
	{
		this.ma = ma;	
	}

	public String getSanpham() 
	{
		return this.sanpham;
	}

	public void setSanpham(String sanpham) 
	{
		this.sanpham = sanpham;	
	}

	public String getTungay()
	{
		return this.tungay;
	}

	public void setTungay(String tungay) 
	{
		this.tungay = tungay;
	}

	public String getDenngay()
	{
		return this.denngay;
	}

	public void setDenngay(String denngay)
	{
		this.denngay = denngay;
	}
	
	
	public String getTungayNH()
	{
		return this.tungayNH;
	}

	public void setTungayNH(String tungayNH) 
	{
		this.tungayNH = tungayNH;
	}

	public String getDenngayNH()
	{
		return this.denngayNH;
	}

	public void setDenngayNH(String denngayNH)
	{
		this.denngayNH = denngayNH;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getDiengiai() 
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public void init(String query) 
	{
		String sql = "";
		
		if(query.length() > 0)
			sql = query;
		else
		{	
			
			sql =   " select ISNULL(THIEUHOSO,'0')  AS THIEUHOSO, a.pk_seq, b.PREFIX + CAST(a.nhanhang_fk as varchar(10)) as sonhapkho, c.ten as spTen, " +
					" 		 a.soluong, isnull( (select sum(SOLUONGDUYET-SOLUONGHONG-SOLUONGMAU)  as soluongdat from ERP_KIEMDINHCHATLUONG_LANDUYET kd_ld " +
					"       where kd_ld.YEUCAUKIEMDINH_FK=A.pk_seq) , 0) as soluongDat, a.solo, a.ngaytao, isnull(a.ngaysua, '') as ngaysua, " +
					"     b.ngaynhan as ngaynhan, a.trangthai, d.TEN as nguoitao, isnull(e.TEN, '') as nguoisua " +
					" ,isnull(b.trahang_fk,0) as trahang_fk,isnull(b.muahang_fk,0) as muahang_fk, a.KHONHAN_FK  " +
				    " from 	 ERP_YeuCauKiemDinh a inner join ERP_NHANHANG b on a.nhanhang_fk = b.PK_SEQ  " +
				  	" 		 inner join ERP_SANPHAM c on a.sanpham_fk = c.PK_SEQ left join NHANVIEN d on a.nguoitao = d.PK_SEQ " +
				  	" 		 left join NHANVIEN e on a.nguoisua = e.PK_SEQ " +
					" where  a.nhanhang_fk is not null and a.loaimuahang="+loaimh+" ";
					/*" order by a.pk_seq desc ";*/
		}
		
		Utility util = new Utility();
		// Phân quyền nhân viên theo phòng ban, mặc định admin được nhìn thấy
		if( !this.userId.equals("100002")){
			/*sql += " and d.PHONGBAN_FK in ( select  " +
					" distinct PHONGBAN_FK from NHANVIEN where PK_SEQ ="+this.userId+" )";*/
			
			sql += " and ((a.KHOCHOXULY_FK in "+util.quyen_khott(this.userId) +") " +
			" or(a.KHONHAN_FK in "+util.quyen_khott(this.userId) +"))";
		}
		
		System.out.println("---- 1313. erpkiemdinhchatluongList - init sql: " + sql);
		this.kdclRs = createSplittingData(50, 10, " trangthai asc, pk_seq desc", sql);
		System.out.println("__Yeucaukiemdinh : " + sql);
		/*this.kdclRs = db.get(sql);*/
	}

	public void DbClose() 
	{
		try 
		{
			if(this.kdclRs != null)
				this.kdclRs.close();
			this.db.shutDown();
		} 
		catch (SQLException e) {}
	}

	
	public ResultSet getKdclRs() 
	{
		return this.kdclRs;
	}

	public void setKdclRs(ResultSet kdclRs) 
	{
		this.kdclRs = kdclRs;
	}

	
	public String getSonhanhang() {
		return this.sonhanhang;
	}

	
	public void setSonhanhang(String sonhanhang) {
		this.sonhanhang = sonhanhang;
	}

	
	public String getsochungtu() {
		
		return this.sochungtu;
	}

	
	public void setsochungtu(String _sochungtu) {
		
		this.sochungtu=_sochungtu;
	}


	public int getCurrentPage() {
	
		return this.currentPages;
	}

	
	public void setCurrentPage(int current) {
		this.currentPages= current;
		
	}


	public int[] getListPages() {
		
		return this.listPages;
	}


	public void setListPages(int[] listPages) {
		this.listPages= listPages;
		
	}
	public int getNum()
	{
		return this.num;
	}
	
	public void setNum(int num)
	{
		this.num = num;
		listPages = PhanTrang.getListPages(num);
	}

	
	public String getNgayNhan() {
		
		return this.ngaynhan;
	}

	
	public void setNgayNhan(String ngaynhan) {
		
		this.ngaynhan = ngaynhan;
	}

	@Override
	public String getloaimuahang() {
		// TODO Auto-generated method stub
		return this.loaimh;
	}

	@Override
	public void setloaimuahang(String loaimh) {
		// TODO Auto-generated method stub
		this.loaimh=loaimh;
	}
}
