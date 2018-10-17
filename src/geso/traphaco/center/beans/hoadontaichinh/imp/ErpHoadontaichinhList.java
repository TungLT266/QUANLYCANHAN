package geso.traphaco.center.beans.hoadontaichinh.imp;

import java.sql.ResultSet;

import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.center.beans.hoadontaichinh.IErpHoadontaichinhList;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;

public class ErpHoadontaichinhList extends Phan_Trang implements IErpHoadontaichinhList
{
	private static final long serialVersionUID = 1L;
	String userId;
	String tungay;
	String denngay;
	String trangthai;
	
	String sohoadon;
	String sodonhang;

	String nppTen;
	String msg;
	
	String nppId;
	ResultSet nppRs;
	ResultSet DondathangRs;
	
	String khTen;
	ResultSet khRs;
	
	String loaidonhang;
	private int num;
	private int[] listPages;
	private int currentPages;
	
	String loaikm;
	String noibo;
	String sanphamId;
	ResultSet sanphamRs;
	dbutils db;
	
	public ErpHoadontaichinhList()
	{
		this.tungay = "";
		this.denngay = "";
		this.nppTen = "";
		this.trangthai = "";
		this.sohoadon="";
		this.sodonhang="";
		this.msg = "";
		this.loaidonhang = "";
	    this.khTen= "";
	    this.sanphamId="";
		currentPages = 1;
		num = 1;
		this.nppId ="";
		this.loaikm="";
		this.noibo = "0";
		this.db = new dbutils();
	}

	public String getnppId()
	{
		return this.nppId;
	}

	public void setnppId(String nppId) 
	{
		this.nppId = nppId;
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getLoaikm() {
		return loaikm;
	}

	public void setLoaikm(String loaikm) {
		this.loaikm = loaikm;
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
		this.getNppInfo();
		ResultSet rs = db.get("select count(*) as c from ERP_HOADON where npp_fk ='"+ this.nppId +"'");
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

	private void getNppInfo()
	{		
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
	}
	
	double tongTruoc = 0 ;
	double tongCK = 0;
	double tongSau = 0;
	
	public void getSumBySearch(String sumqr) {
		System.out.println("câu ck: "+sumqr);
		if(isSearch){
		ResultSet rs = db.get(sumqr);
			try 
			{
				rs.next();
				this.tongTruoc = rs.getDouble("DOANHSO");
				this.tongCK = rs.getDouble("THUE");
				this.tongSau = rs.getDouble("DOANHTHU");
				rs.close();
			}
			catch(Exception e) {}
		}
		else{
			System.out.println("vàafsa ");			
			this.tongTruoc = 0;
			this.tongCK = 0;
			this.tongSau = 0;
		}
	}
	
	public void init(String search)
	{
		String query = "";
        
		System.out.println("::: NOI BO: " + this.noibo + " -- LOAI KM: " + this.loaikm  );
		if(search.length() > 0)
			query = search;
		else
		{
			if( this.noibo.equals("1") )
			{
				query = "select isnull(dh.phanloai,0) as phanloai,isnull(a.loaihoadon,0) loaihoadon ,isnull(a.loaihd,0) loaihd ,a.PK_SEQ, a.trangthai, a.ngayxuatHD, a.sohoadon + a.kyhieu as sohoadon, NV.TEN as nguoitao, a.tongtienavat as tongtien,  " +
						" npp.TEN as khTEN, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua  " +
						"from ERP_HOADON a " +
						" inner join ERP_HOADON_DDH ddh on ddh.HOADON_FK=a.PK_SEQ "+
						" inner join ERP_DONDATHANG dh on dh.PK_SEQ=ddh.DDH_FK "+
						" left join NHAPHANPHOI npp on a.NPP_FK=npp.PK_SEQ  " +
						" inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
						" inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ "+
						"where  a.noibo = '1'   ";
			}
			else
			{
				if( !this.loaikm.equals("1") )
				{
					query = "select isnull(dh.phanloai,0) as phanloai,isnull(a.loaihoadon,0) loaihoadon,isnull(a.loaihd,0) loaihd ,a.PK_SEQ, a.trangthai, a.ngayxuatHD, a.sohoadon + a.kyhieu as sohoadon, NV.TEN as nguoitao, a.tongtienavat as tongtien,  " +
							" npp.TEN as khTEN, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua  " +
							"from ERP_HOADON a " +
							" inner join ERP_HOADON_DDH ddh on ddh.HOADON_FK=a.PK_SEQ "+
							" inner join ERP_DONDATHANG dh on dh.PK_SEQ=ddh.DDH_FK "+
							" left join NHAPHANPHOI npp on a.NPP_FK=npp.PK_SEQ  " +
							" inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
							" inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ "+
							"where a.noibo = '0' and loaihoadon != '1'   ";
				}
				else //loại hóa đơn = 1 là hóa đơn KM
				{
					query = "select isnull(dh.phanloai,0) as phanloai,isnull(a.loaihoadon,0) loaihoadon,isnull(a.loaihd,0) loaihd, a.PK_SEQ, a.trangthai, a.ngayxuatHD, a.sohoadon + a.kyhieu as sohoadon, NV.TEN as nguoitao, a.tongtienavat as tongtien,   " +
							"	npp.TEN as khTEN, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua  " +
							"from ERP_HOADON a " +
							" inner join ERP_HOADON_DDH ddh on ddh.HOADON_FK=a.PK_SEQ "+
							" inner join ERP_DONDATHANG dh on dh.PK_SEQ=ddh.DDH_FK "+
							" inner join NHAPHANPHOI npp on a.NPP_FK=npp.PK_SEQ  " +
							" inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
							" inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ "+
							 "where a.noibo = '0' and a.loaihoadon = '1'   ";
				}
				
			
		
			}
			
			if(sohoadon.length()>0)
				query += " and a.sohoadon LIKE '%" + sohoadon + "%'";
			
			if(tungay.length() > 0)
				query += " and a.ngayxuatHD >= '" + tungay + "'";
			
			if(denngay.length() > 0)
				query += " and a.ngayxuatHD <= '" + denngay + "'";
		
			if(trangthai.length() > 0)
			{
				if(trangthai.equals("3")||trangthai.equals("5") )
					query += " and a.TrangThai in (3,5) ";
				else
					query += " and a.TrangThai = '" + trangthai + "'";
			}
			
			
			if(this.loaidonhang.length() > 0)
				query += " and a.loaihoadon = '" + this.loaidonhang + "'";
			
			
			if(this.khTen.length() > 0)
				query += " and ( npp.pk_seq ='" + this.khTen + "' or dh.npp_fk = '" + this.khTen + "' ) ";
			
			if(sodonhang.length()>0)
				query += " and a.pk_seq in ( select HOADON_FK from Erp_HoaDon_DDH WHERE Cast(DDH_fK as varchar(20))  LIKE '%"+sodonhang+"%' ) ";
			
			if(sanphamId.length() > 0){
				query += " and a.pk_Seq in (select HOADON_FK from Erp_HoaDon_SP where sanpham_fk in ("+sanphamId+") ) ";
			}
		
		} 
		
		System.out.print("câu tìm kiếm " + query);
		this.DondathangRs = createSplittingData(50, 10, " ngayxuatHD desc,trangthai asc, sohoadon desc ", query);
		
		query = "	select cast(PK_SEQ as nvarchar(20)) as PK_SEQ,  isnull(maFAST,'') + '-' + TEN as TEN  " +
				"	from NHAPHANPHOI where TRANGTHAI = '1'  and iskhachhang=1 ";
		
		this.khRs = db.get(query);
		System.out.println("ERP_HOADON1111: " + query);
		
		query="SELECT sp.PK_SEQ, sp.TEN + ' - ' + sp.MA AS TEN \n"
				+ " FROM ERP_HOADON_SP HDSP \n"
				+ " LEFT JOIN  ERP_SANPHAM SP ON hdsp.sanpham_fk=sp.pk_Seq \n"
				+ "  group by sp.PK_SEQ, sp.TEN + ' - ' + sp.MA";
		
		System.out.println(query);
		
		sanphamRs = db.get(query);
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

	
	public String getKhTen() {
		return this.khTen;
	}

	
	public void setKhTen(String KhTen) {
		this.khTen = KhTen;
		
	}

	
	public ResultSet getKhRs() {
		return this.khRs;
	}

	
	public void setKhRs(ResultSet KhRs) {
		this.khRs = KhRs;
		
	}

	
	public String getSohoadon() {
		
		return this.sohoadon;
	}

	
	public void setSohoadon(String sohoadon) {
		
		this.sohoadon=sohoadon;
	}

	
	public String getSodonhang() {
		
		return this.sodonhang;
	}

	
	public void setSodonhang(String sodonhang) {
		
		this.sodonhang=sodonhang;
	}
	boolean isSearch = false;
	
	public boolean getIsSearch() {
		return this.isSearch;
	}

	
	public void setIsSearch(boolean search) {
		this.isSearch = search;
		
	}

	
	public double getTongTruoc() {
		return this.tongTruoc;
	}

	
	public double getTongCK() {
		return this.tongCK;
	}

	
	public double getTongSau() {
		return this.tongSau;
	}

	
	public String getNOIBO() {

		return this.noibo;
	}

	public String getSanphamId() {
		return sanphamId;
	}

	public void setSanphamId(String sanphamId) {
		this.sanphamId = sanphamId;
	}

	
	public void setNOIBO(String noibo) {
		
		this.noibo = noibo;
	}
	public ResultSet getSanphamRs() {
		return sanphamRs;
	}



	public void setSanphamRs(ResultSet sanphamRs) {
		this.sanphamRs = sanphamRs;
	}

}
