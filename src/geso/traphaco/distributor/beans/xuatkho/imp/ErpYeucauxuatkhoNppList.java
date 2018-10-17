package geso.traphaco.distributor.beans.xuatkho.imp;

import java.sql.ResultSet;

import geso.traphaco.distributor.beans.xuatkho.IErpYeucauxuatkhoNppList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.Utility_Kho;

public class ErpYeucauxuatkhoNppList extends Phan_Trang implements IErpYeucauxuatkhoNppList
{
	private static final long serialVersionUID = 1L;
	String userId;
	String tungay;
	String denngay;
	String trangthai;

	String khTen;
	String msg;
	
	//ResultSet khRs;
	ResultSet DondathangRs;
	
	ResultSet rsrskhoid;
	String nguoigiao;
	String khohh;
	
	String loaidonhang;
	String phanloai;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	String maso ="";
	String nppId;
	String nppTen;
	String sitecode;
	String ctyId;
	
	String sodonhang;
	String sohoadon;
	
	String tdv_dangnhap_id;
	String npp_duocchon_id;
	
	dbutils db;
	String KbhId = "",KvId = "",SpId = "";
	

	ResultSet KbhRs,KvRs,SpRs;
	
	public ErpYeucauxuatkhoNppList()
	{
		this.tungay = "";
		this.denngay = "";
		this.khTen = "";
		this.trangthai = "";
		this.msg = "";
		this.loaidonhang = "";
		this.phanloai = "";
		currentPages = 1;
		this.ctyId = "";
		num = 1;
		this.nguoigiao="";
		this.khohh="";
		this.sodonhang = "";
		this.sohoadon = "";
		this.tdv_dangnhap_id = "";
		this.npp_duocchon_id = "";
		this.db = new dbutils();
	}
	public String getCtyId()
	{
		return this.ctyId;
	}

	public void setCtyId(String ctyId) 
	{
		this.ctyId = ctyId;
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
		this.getNppInfo();
		
		String query = "";
		Utility util = new Utility();
		if(search.length() > 0)
			query = search;
		else
		{
			query = "select a.PK_SEQ, a.machungtu, a.trangthai, a.ngayyeucau, case "+
								"  when a.loaidonhang = '0' then c.ten "
								  +" when a.loaidonhang = '1' then d.ten "
								  +" when a.loaidonhang =  '2' then d.ten "
								  +" when a.loaidonhang =  '3' then e.ten "
								 +" end  as nppTEN, b.ten as khoTEN, NV.TEN as nguoitao, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, " +
					"	 (	Select cast(YCXK1.DDH_FK as varchar(10)) + ',' AS [text()]  " +
					"		From ERP_YCXUATKHONPP_DDH YCXK1   " +
					"		Where YCXK1.ycxk_fk = a.pk_seq  " +
					"		For XML PATH ('') )  as ddhIds, hd.sohoadon     " +
					"from ERP_YCXUATKHONPP a inner join KHO b on a.kho_fk = b.pk_seq " +
					"	left join NHAPHANPHOI c on a.NPP_DAT_FK = c.pk_seq " +
					"	left join KHACHHANG d on a.khachhang_fk = d.pk_seq  " 
					+ util.getPhanQuyen_TheoNhanVien("KHACHHANG", "d", "pk_seq", this.getLoainhanvien(), this.getDoituongId() ) +
					"	left join ERP_NHANVIEN e on a.nhanvien_fk = e.pk_seq  " +
					"	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
					"	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ " + 
					"	left join ERP_HOADONNPP hd on a.hoadon_fk = hd.pk_seq " + 
					" where a.npp_fk = '" + this.nppId + "' and a.kho_fk in "+ util.quyen_kho(this.userId); 
		} 
		
		if(this.loaidonhang.trim().length() > 0)
			query += " and a.loaidonhang = '" + this.loaidonhang + "' ";
				
		System.out.println("___CHUYEN KHO: " + query);
		this.DondathangRs = createSplittingDataNew(this.db,50, 10, "ngayyeucau desc, trangthai asc ", query);
		
		/*query = "";
		if(this.phanloai.equals("0"))
		{
			query = "	select PK_SEQ,  isnull(maFAST,'') + '-' + TEN as TEN  " +
					"	from NHAPHANPHOI where TRANGTHAI = '1' and tructhuoc_fk = '" + this.nppId + "' ";
		}
		else if(this.phanloai.equals("1"))
		{
			query = "select PK_SEQ,  isnull(maFAST,'') + '-' + TEN as TEN  " +
					"	from KHACHHANG where TRANGTHAI = '1' and KBH_FK in ( select kbh_fk from NHOMKENH_KENHBANHANG where nk_fk = '100000' ) and npp_fk = '" + this.nppId + "' ";
		}
		else
		{
			query = "select PK_SEQ,  isnull(maFAST,'') + '-' + TEN as TEN  " +
					"	from KHACHHANG where TRANGTHAI = '1' and KBH_FK in ( select kbh_fk from NHOMKENH_KENHBANHANG where nk_fk = '100001' ) and npp_fk = '" + this.nppId + "' ";
		}
		
		this.khRs = db.get(query);*/
		this.rsrskhoid=db.get("select pk_seq ,ten from kho");

		this.KbhRs = db.get("select pk_seq,ten from kenhbanhang where trangthai = 1");
		this.KvRs = db.get("select pk_seq, ten from khuvuc where trangthai = 1");
		this.SpRs = db.get("select pk_seq, ten from sanpham where trangthai = 1");
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

	
	public String getKhTen() {
		
		return this.khTen;
	}

	
	public void setKhTen(String khTen) {
		
		this.khTen = khTen;
	}
	
	public String getLoaidonhang() {

		return this.loaidonhang;
	}


	public void setLoaidonhang(String loaidonhang) {
		
		this.loaidonhang = loaidonhang;
	}
	
	public String getNppId() {
		
		return this.nppId;
	}

	
	public void setNppId(String khId) {
		
		this.nppId = khId;
	}
	

	public String getNppTen() {

		return this.nppTen;
	}
	
	public void setNppTen(String nppTen) 
	{
		this.nppTen = nppTen;
	}
	
	public String getSitecode() 
	{
		return this.sitecode;
	}

	public void setSitecode(String sitecode) 
	{
		this.sitecode = sitecode;
	}
	
	private void getNppInfo()
	{		
		geso.traphaco.distributor.util.Utility util = new geso.traphaco.distributor.util.Utility();
		
		if(this.npp_duocchon_id.trim().length() <= 0)
		{
			this.nppId = util.getIdNhapp(this.userId);
			this.nppTen = util.getTenNhaPP();
			this.sitecode = util.getSitecode();
		}
		else
		{
			this.nppId = this.npp_duocchon_id;
			this.nppTen = "";
			this.sitecode = "";
		}
	}

	public String getPhanloai() 
	{
		return this.phanloai;
	}

	public void setPhanloai(String phanloai) 
	{
		this.phanloai = phanloai;
	}
	
	
	public ResultSet Laydinhkhoan(String Id){
		ResultSet rs = null;
		// BUT TOAN KE TOAN
		
		Utility_Kho util_kho=new Utility_Kho();
		String query = 	"SELECT  N'NỢ' AS NO_CO, \n " +

						"CASE WHEN LEN(ISNULL(YC_CT.SCHEME, '')) <= 0 THEN  \n " +
		
						"'63211000'  \n " +

						"ELSE  \n " +  // ĐƠN HÀNG KHUYẾN MẠI
		
						"'63220000'  \n " +
		
						"END AS SOHIEU,  \n " +
		
						"CASE WHEN CONVERT(INT, SUBSTRING(NGAYYEUCAU, 6, 2)) > 1 THEN  \n " + // TRƯỜNG HỢP KHÔNG LÀ THÁNG 1, THÌ LẤY THÁNG - 1
						"	ISNULL( ( SELECT AVG(GIATON) FROM ERP_TONKHOTHANG  \n " +
						"	          WHERE THANG = (CONVERT(INT, SUBSTRING(NGAYYEUCAU, 6, 2)) - 1) AND NAM = SUBSTRING(NGAYYEUCAU, 1, 4)  \n " +
						"	          AND SANPHAM_FK = YC_CT.SANPHAM_FK AND CONGTY_FK = (SELECT CONGTY_FK from NHAPHANPHOI where PK_SEQ = '" + nppId + "')), 0 )*SUM(YC_CT.SOLUONG)  \n " +
						"ELSE  \n " +
						"	ISNULL( ( SELECT AVG(GIATON) FROM ERP_TONKHOTHANG  \n " +
						"			  WHERE THANG = 12 and NAM = (SUBSTRING(NGAYYEUCAU, 1, 4) - 1)  \n " +
						"			  AND SANPHAM_FK = YC_CT.SANPHAM_FK AND CONGTY_FK = (SELECT CONGTY_FK from NHAPHANPHOI where PK_SEQ = '" + nppId + "')), 0 )*SUM(YC_CT.SOLUONG)  \n " +
						"END AS SOTIEN,  \n " + 

						"N'Giá vốn hàng xuất bán' AS DOITUONG,  \n " +
 		
						"'' AS TTCP, '' AS TTDT  \n " +
		
						" FROM ERP_YCXUATKHONPP YC  \n " +
						" INNER JOIN ERP_YCXUATKHONPP_SANPHAM_CHITIET YC_CT ON YC.PK_SEQ = YC_CT.YCXK_FK  \n " + 
						" INNER JOIN ERP_HOADONNPP HD ON HD.PK_SEQ = YC.HOADON_FK  \n " +
						" WHERE YCXK_FK = '" + Id + "'  \n " +
						" GROUP BY YC_CT.SCHEME, NGAYYEUCAU, YC_CT.SANPHAM_FK  \n " +
		
						"UNION ALL  \n " +
		
						"SELECT  N'CÓ' AS NO_CO,  \n " +

						" (SELECT TAIKHOANKT_FK FROM ERP_LOAISANPHAM  \n " +
						" WHERE PK_SEQ = SP.LOAISANPHAM_FK) AS SOHIEU, \n " +
							
						"CASE WHEN CONVERT(INT, SUBSTRING(NGAYYEUCAU, 6, 2)) > 1 THEN  \n " + // TRƯỜNG HỢP KHÔNG LÀ THÁNG 1, THÌ LẤY THÁNG - 1
						"	ISNULL( ( SELECT AVG(GIATON) FROM ERP_TONKHOTHANG  \n " +
						"	          WHERE THANG = (CONVERT(INT, SUBSTRING(NGAYYEUCAU, 6, 2)) - 1) AND NAM = SUBSTRING(NGAYYEUCAU, 1, 4)  \n " +
						"	          AND SANPHAM_FK = YC_CT.SANPHAM_FK AND CONGTY_FK = (SELECT CONGTY_FK from NHAPHANPHOI where PK_SEQ = '" + nppId + "')), 0 )*SUM(YC_CT.SOLUONG)  \n " +
						"ELSE  \n " +
						"	ISNULL( ( SELECT AVG(GIATON) FROM ERP_TONKHOTHANG  \n " +
						"			  WHERE THANG = 12 and NAM = (SUBSTRING(NGAYYEUCAU, 1, 4) - 1)  \n " +
						"			  AND SANPHAM_FK = YC_CT.SANPHAM_FK AND CONGTY_FK = (SELECT CONGTY_FK from NHAPHANPHOI where PK_SEQ = '" + nppId + "')), 0 )*SUM(YC_CT.SOLUONG)  \n " +
						"END AS SOTIEN,  \n " + 

						"SP.TEN AS DOITUONG,  \n " +
 		

						"'' AS TTCP, '' AS TTDT  \n " +
		
						" FROM ERP_YCXUATKHONPP YC  \n " +
						" INNER JOIN ERP_YCXUATKHONPP_SANPHAM_CHITIET YC_CT ON YC.PK_SEQ = YC_CT.YCXK_FK  \n " + 
						" INNER JOIN SANPHAM SP ON SP.PK_SEQ = YC_CT.SANPHAM_FK  \n " +
						" INNER JOIN ERP_HOADONNPP HD ON HD.PK_SEQ = YC.HOADON_FK  \n " +
						" WHERE YCXK_FK = '" + Id + "'  \n " +
						" GROUP BY YC_CT.SCHEME, NGAYYEUCAU, YC_CT.SANPHAM_FK, SP.LOAISANPHAM_FK, SP.TEN " ;
		
		//System.out.println("Định khoản: " + query);
		rs = this.db.get(query);
		return rs;
	}
	
	public String getSodonhang() {
		
		return this.sodonhang;
	}
	
	public void setSodonhang(String sodonhang) {
		
		this.sodonhang = sodonhang;
	}
	
	public String getSohoadon() {
		
		return this.sohoadon;
	}
	
	public void setSohoadon(String sohoadon) {
		
		this.sohoadon = sohoadon;
	}
	
	public String getMaso() {
	
		return this.maso;
	}
	
	public void setMaso(String Maso) {
	
		this.maso = Maso;
	}	public ResultSet getRsrskhoid() {
		return rsrskhoid;
	}
	public void setRsrskhoid(ResultSet rsrskhoid) {
		this.rsrskhoid = rsrskhoid;
	}
	public String getNguoigiao() {
		return nguoigiao;
	}
	public void setNguoigiao(String nguoigiao) {
		this.nguoigiao = nguoigiao;
	}
	public String getKhohh() {
		return khohh;
	}
	public void setKhohh(String khohh) {
		this.khohh = khohh;
	}
	
	public String getKhten() {

		return this.khTen;
	}

	public void setKhten(String khten) {

		this.khTen = khten;
	}
	public String getKbhId() {
		return KbhId;
	}

	public void setKbhId(String kbhId) {
		KbhId = kbhId;
	}

	public String getKvId() {
		return KvId;
	}

	public void setKvId(String kvId) {
		KvId = kvId;
	}

	public ResultSet getKbhRs() {
		return KbhRs;
	}

	public ResultSet getKvRs() {
		return KvRs;
	}
	public String getSpId() {
		return SpId;
	}
	public void setSpId(String spId) {
		SpId = spId;
	}
	public ResultSet getSpRs() {
		return SpRs;
	}
	
	Object loainhanvien;
	Object doituongId;
	public String getLoainhanvien() 
	{
		if( this.loainhanvien == null )
			return "";
		
		return this.loainhanvien.toString();
	}

	public void setLoainhanvien(Object loainhanvien) 
	{
		this.loainhanvien = loainhanvien;
	}
	
	public String getDoituongId() 
	{
		if( this.doituongId == null )
			return "";
		
		return this.doituongId.toString();
	}

	public void setDoituongId(Object doituongId) 
	{
		this.doituongId = doituongId;
	}
	
	public String getTdv_dangnhap_id() {
		
		return this.tdv_dangnhap_id;
	}

	
	public void setTdv_dangnhap_id(String tdv_dangnhap_id) {
		
		this.tdv_dangnhap_id = tdv_dangnhap_id;
	}
	
	public String getNpp_duocchon_id() {
		
		return this.npp_duocchon_id;
	}

	
	public void setNpp_duocchon_id(String npp_duocchon_id) {
		
		this.npp_duocchon_id = npp_duocchon_id;
	}
}
