package geso.traphaco.center.beans.dondathang.imp;

import java.sql.ResultSet;
import geso.traphaco.center.beans.dondathang.IErpDondathangList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;

public class ErpDondathangList extends Phan_Trang implements IErpDondathangList
{
	private static final long serialVersionUID = 1L;
	String userId;
	String tungay;
	String denngay;
	String trangthai;
	String ctId;
	String nppTen;
	String msg;
	
	ResultSet nppRs;
	ResultSet DondathangRs;
	ResultSet chungTuRs;
	
	String loaidonhang;
	String ETC;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	
	public ErpDondathangList()
	{
		this.tungay = "";
		this.denngay = "";
		this.nppTen = "";
		this.trangthai = "";
		this.msg = "";
		this.ctId ="";
		this.loaidonhang = "0";
		currentPages = 1;
		num = 1;
		this.iskm="";
		this.ETC = "";
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
	
	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
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

	public int getCurrentPage()
	{
		return this.currentPages;
	}

	public void setCurrentPage(int current) 
	{
		this.currentPages = current;
	}

	public int[] getListPages() 
	{
		return this.listPages;
	}

	public void setListPages(int[] listPages) 
	{
		this.listPages = listPages;
	}

	public int getLastPage() 
	{
		ResultSet rs = db.get("select count(*) as c from ERP_YEUCAUNGUYENLIEU");
		return PhanTrang.getLastPage(rs);
	}

	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage)
	{
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public void init(String search)
	{
		String query = "";

		Utility util = new Utility();

		
			query = "select a.ngaygiochot,a.PK_SEQ, a.trangthai, a.ngaydonhang,TT.TEN as DIABAN, c.ten as nppTEN, b.ten as khoTEN, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, isnull(a.NOTE, '') as NOTE,   " +
					" case a.KBH_FK when 100052 then N'ETC' when 100025 then N'OTC' end as KenhBanHang,isnull(a.iskm,0) as iskm,\n"+
					"	CASE WHEN (SELECT SUM(ISNULL(TIENHANGSAUVAT,0)) FROM ERP_DONDATHANG_SANPHAM_CHITIET WHERE  dondathang_fk = a.PK_SEQ) = 0 THEN \n"+
					"	((select SUM(isnull(soluong,0) * isnull(dongia,0)) from ERP_DONDATHANG_SANPHAM where dondathang_fk = a.PK_SEQ)+isnull(a.Vat,0)+isnull(a.Chietkhau, 0))  \n"+
					" ELSE ("
					+ " (( SELECT SUM(ISNULL(TIENHANGSAUVAT,0)) FROM ERP_DONDATHANG_SANPHAM_CHITIET where dondathang_fk = a.PK_SEQ)+isnull(a.Chietkhau, 0)) \n"
					+ ""
					+ " ) END as tonggiatri \n"+
					
					"from ERP_Dondathang a inner join ERP_KHOTT b on a.kho_fk = b.pk_seq inner join NHAPHANPHOI c on a.NPP_FK = c.pk_seq  " +
					"	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
					"	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ " +
					" 	inner join TINHTHANh TT on c.TINHTHANH_FK = tt.PK_SEQ "+
					"where a.pk_seq > 0 and a.NPP_DACHOT = '1' ";  
			if(iskm.length() > 0)
				query += " and a.iskm = '" + iskm + "' ";
			
			if(tungay.length() > 0)
				query += " and a.ngaydonhang >= '" + tungay + "'";
			
			if(denngay.length() > 0)
				query += " and a.ngaydonhang <= '" + denngay + "'";
			
			if(trangthai.length() > 0)
				query += " and a.TrangThai = '" + trangthai + "'";
			
			if(ctId.length() > 0)
				query += " and a.PK_SEQ like '%" + ctId + "%'";
			
			
			if(this.nppTen.length() > 0){
				query += " and a.NPP_FK= '" + nppTen + "'";
			}

		if( this.ETC.equals("1") )
			query += " and a.isETC = '1' ";
		else
			query += " and a.loaidonhang = '" + this.loaidonhang + "' and hopdong_fk is null ";
		
		if( this.ETC.equals("0") && this.loaidonhang.equals("0"))  //ĐƠN HÀNG BÁN CHO ĐỐI TÁC
			query += " and c.pk_seq in ( select pk_seq from NHAPHANPHOI where TRANGTHAI = '1' and loaiNPP = '4' and tructhuoc_fk='1' ) ";
		

		//query += " and c.pk_seq in " + util.quyen_npp(userId) + "";

		System.out.println("___CHUYEN KHO: " + query);
		this.DondathangRs = createSplittingData(50, 10, "ngaydonhang desc, trangthai asc ", query);

		if( this.ETC.equals("1") )
		{
			this.nppRs = db.get("select PK_SEQ, MA + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1' and isKHACHHANG = '1' ");
		}
		else
		{
			if(this.loaidonhang.equals("0"))  //ĐƠN HÀNG BÁN CHO ĐỐI TÁC
				this.nppRs = db.get("select PK_SEQ, MA + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1' and loaiNPP = '4' and tructhuoc_fk='1' and pk_seq in "+ util.quyen_npp(this.userId));
			else if(this.loaidonhang.equals("4") ||this.loaidonhang.equals("2"))
				this.nppRs = db.get("select PK_SEQ, MA + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1'  and isKHACHHANG = '1'  and pk_seq in "+ util.quyen_npp(this.userId));
			else if(this.loaidonhang.equals("0"))
				this.nppRs = db.get("select PK_SEQ, MA + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1' and pk_seq in "+ util.quyen_npp(this.userId));
		}
	}
	
	public void DBclose() 
	{
		this.db.shutDown();
	}

	public ResultSet getDondathangRs() 
	{
		return this.DondathangRs;
	}

	public void setDondathangRs(ResultSet nkRs) 
	{
		this.DondathangRs = nkRs;
	}

	
	public String getTungay() {
		
		return this.tungay;
	}

	
	public void setTungay(String tungay) {
		
		this.tungay = tungay;
	}

	
	public String getDenngay() {
		
		return this.denngay;
	}

	
	public void setDenngay(String denngay) {
		
		this.denngay = denngay;
	}

	
	public String getNppTen() {
		
		return this.nppTen;
	}

	
	public void setNppTen(String nppTen) {
		
		this.nppTen = nppTen;
	}

	
	public ResultSet getNppRs() {
		
		return this.nppRs;
	}

	
	public void setNppRs(ResultSet nppRs) {
		
		this.nppRs = nppRs;
	}

	
	public String getLoaidonhang() {

		return this.loaidonhang;
	}


	public void setLoaidonhang(String loaidonhang) {
		
		this.loaidonhang = loaidonhang;
	}


	public ResultSet getChungtuRs() {
	
		return this.chungTuRs;
	}


	public void setChungtuRs(ResultSet chungtuRs) {
		this.chungTuRs = chungtuRs;
	}

	
	public String getctId() {
	
		return this.ctId;
	}

	
	public void setctId(String ctId) {
		this.ctId = ctId;
		
	}
	
	String phanloai;

	public String getPhanloai()
	{
		return phanloai;
	}

	public void setPhanloai(String phanloai)
	{
		this.phanloai = phanloai;
	}

	
	String iskm;
	public String getIsKm()
	{
		return iskm;
	}

	public void setIsKm(String iskm)
	{
		this.iskm = iskm;
	}

	public String getETC()
	{
		return this.ETC;
	}

	public void setETC(String ETC) 
	{
		this.ETC = ETC;
	}


	public String getTittle(String type) 
	{
		String tittle = "";
		
		if( type.equals("0") )
		{
			if( this.loaidonhang.equals("0") )
			{
				if( this.ETC.equals("1") )
					tittle = "Bán hàng cho ETC";
				else
					tittle = "Bán hàng cho đối tác";
			}
			else if( this.loaidonhang.equals("2") )
			{
				//tittle = "Đơn hàng XNK";
				tittle = "Đơn hàng bán";
			}
			else if( this.loaidonhang.equals("3") )
				tittle = "Bán nguyên vật liệu";
		}
		else if( type.equals("1") )
		{
			if( this.getETC().equals("1") || this.loaidonhang.equals("2") || this.loaidonhang.equals("3") )
				tittle = "Khách hàng";
			else
				tittle = "Đối tác";
		}
		
		return tittle;
	}

	
}
