package geso.traphaco.erp.beans.kiemdinhchatluong.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.erp.beans.kiemdinhchatluong.*;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.Utility_Kho;
import geso.traphaco.center.db.sql.dbutils;

public class ErpKiemdinhchatluongList_NhGiay extends Phan_Trang implements IErpKiemdinhchatluongList_NhGiay
{
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
	String sohoadon="";
	

	String msg;
	String ngaynhan;
	// them truong loai mua hang de phan biet mua hang trong nuoc va nhap khau
	String loaimh;
	ResultSet kdclRs;
	ResultSet spRs;
	public ResultSet getSpRs() {
		return spRs;
	}


	public void setSpRs(ResultSet spRs) {
		this.spRs = spRs;
	}

	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	
	public ErpKiemdinhchatluongList_NhGiay()
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
	
	
	public String getSohoadon() {
		return sohoadon;
	}

	public void setSohoadon(String sohoadon) {
		this.sohoadon = sohoadon;
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
			
			sql =   " select isnull(a.loaimuahang,'') as loaimuahang , isnull(hd.sohoadon,'')  as spSohoadon, ISNULL(THIEUHOSO,'0')  AS THIEUHOSO, a.pk_seq, b.PREFIX + CAST(a.nhanhang_fk as varchar(10)) as sonhapkho, c.ten as spTen, " +
					"\n 		 a.soluong, isnull( (select sum(SOLUONGDUYET-SOLUONGHONG-SOLUONGMAU)  as soluongdat from ERP_KIEMDINHCHATLUONG_LANDUYET kd_ld " +
					"\n       where kd_ld.YEUCAUKIEMDINH_FK=A.pk_seq) , 0) as soluongDat, a.solo, a.ngaytao, isnull(a.ngaysua, '') as ngaysua, " +
					"\n     b.ngaynhan as ngaynhan, a.trangthai, d.TEN as nguoitao, isnull(e.TEN, '') as nguoisua " +
					"\n ,isnull(b.trahang_fk,0) as trahang_fk,isnull(b.muahang_fk,0) as muahang_fk, a.KHONHAN_FK  " +
				    "\n from 	 ERP_YeuCauKiemDinh a inner join ERP_NHANHANG b on a.nhanhang_fk = b.PK_SEQ  " +
				  	"\n 		 inner join ERP_SANPHAM c on a.sanpham_fk = c.PK_SEQ left join NHANVIEN d on a.nguoitao = d.PK_SEQ " +
				  	"\n 		 left join NHANVIEN e on a.nguoisua = e.PK_SEQ " +
				  	"\n 		 left join ERP_HOADONNCC hd on hd.pk_seq= b.hdncc_fk " +
					"\n where  a.nhanhang_fk is not null and a.loaimuahang="+this.loaimh+" " ;
					/*"\n order by a.pk_seq desc ";*/
		}
		if(this.loaimh.length() > 0)
			sql += "\n and a.loaimuahang='"+this.loaimh +"'";
			
			if(this.tungay.length() > 0)
				sql += "\n and isnull(a.ngaysua, '') >= '" + this.tungay + "'";
			
			if(ngaynhan.length() > 0)
				sql += "\n and isnull(b.ngaynhan, '') = '" + ngaynhan + "'";
			
			if(this.denngay.length() > 0)
				sql += "\n and isnull(a.ngaysua, '') <= '" + this.denngay + "'";
			
			if(this.tungayNH.length() > 0)
				sql += "\n and isnull(b.ngaynhan, '') >= '" + this.tungayNH + "'";
			
			if(this.denngayNH.length() > 0)
				sql += "\n and isnull(b.ngaynhan, '') <= '" + this.denngayNH + "'";
			
			if(this.ma.length() > 0)
				sql += "\n and a.solo like N'%" + this.ma + "%' ";
			
			
			if(sonhanhang.length() > 0)
				sql += "\n and ( b.PREFIX + CAST(a.nhanhang_fk as varchar(10)) like N'%" + sonhanhang + "%' ) ";
			
			if(this.sochungtu.length() > 0)
				sql += "\n and ( '200' + CAST(a.pk_seq as varchar(10)) like N'%" + this.sochungtu + "%' ) ";
			
			if(this.trangthai.length() > 0)
				sql += "\n and a.trangthai = '" + this.trangthai + "' ";
			
			if(this.sohoadon.length() > 0)
				sql += "\n and hd.sohoadon = '" + this.sohoadon + "' ";
			
		
			
			if(this.sanpham.length() > 0)
			{
				//sql += " and ( dbo.ftBoDau(c.ten) like N'%" + util.replaceAEIOU(sanpham) + "%' or dbo.ftBoDau(c.ma) like N'%" + util.replaceAEIOU(sanpham) + "%' ) ";
				sql +=" \n  and c.pk_seq ="+  this.sanpham   ;
				
				
			}
		
		Utility util = new Utility();
		// Phân quyền nhân viên theo phòng ban, mặc định admin được nhìn thấy
		if( !this.userId.equals("100002")  && util.GetquyenNew("ErpKiemdinhchatluong_NhGiaySvl", "",userId)[9]==0){
			/*sql += " and d.PHONGBAN_FK in ( select  " +
					" distinct PHONGBAN_FK from NHANVIEN where PK_SEQ ="+this.userId+" )";*/
			
			sql += " and ((a.KHOCHOXULY_FK in "+util.quyen_khott(this.userId) +") " +
			" or(a.KHONHAN_FK in "+util.quyen_khott(this.userId) +"))";
		}
		
		System.out.println("---- 1313. erpkiemdinhchatluongList - init sql: " + sql);
		this.kdclRs = createSplittingData(50, 10, " trangthai asc, pk_seq desc", sql);
		System.out.println("__Yeucaukiemdinh : " + sql);
		/*this.kdclRs = db.get(sql);*/
		
		
		//get spRS
		query ="select PK_SEQ,MA, TEN from ERP_SANPHAM where trangthai ='1'";
		this.spRs = db.get(query);

		
		
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
