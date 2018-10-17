package geso.traphaco.erp.beans.kiemdinhchatluong.giay.imp;

import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.erp.beans.kiemdinhchatluong.giay.IErpKiemdinhchatluongList;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ErpKiemdinhchatluongList extends Phan_Trang implements IErpKiemdinhchatluongList 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String userId;
	String tungay;
	String denngay;
	String trangthai; 
	String ma;
	String sanpham;
	String diengiai;
	String msg;
	String ctyId;
	String nhamayId;
	String soLSX;
	String Solo;
	String nguoitao;
	String ngaysanxuat;
	String Sochungtu;
	
	ResultSet kdclRs;
	ResultSet nhamayRs;
	
	dbutils db;
	private int num;
	private int[] listPages;
	private int currentPages;
	
	
	public ErpKiemdinhchatluongList()
	{
		this.userId = "";
		this.tungay = "";
		this.denngay = "";
		this.ma = "";
		this.sanpham = "";
		this.trangthai = "";
		this.diengiai = "";
		this.msg = "";
		this.ngaysanxuat = "";
		this.nguoitao = "";
		this.nhamayId = "";
		this.ctyId="";
		this.soLSX= "";
		this.Solo="";
		this.Sochungtu="";
		this.db = new dbutils();
		currentPages = 1;
		num = 1;
		
	}
	
	public String getSoLSX() 
	{
		return soLSX;
	}
	public void setSoLSX(String soLSX) 
	{
		this.soLSX = soLSX;
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
			sql =   "   SELECT 	a.pk_seq, isnull(lsx.diengiai,'') as diengiai , a.nhapkho_fk  as sonhapkho, c.ten   as spTen, a.soluong, \n"+
					"			a.solo, a.ngaytao, isnull(a.ngaysua, '') as ngaysua, a.trangthai, d.TEN as nguoitao, \n"+ 
					"			isnull(e.TEN, '') as nguoisua,a.LENHSANXUAT_FK AS LenhSanXuatId,a.CONGDOAN_FK AS CONGDOANID ,A.NGAYKIEM,nk_sp.ngaysanxuat as ngaysanxuat \n"+  
					"   FROM 	ERP_YeuCauKiemDinh a inner join ERP_SANPHAM c on a.sanpham_fk = c.PK_SEQ \n" +
					" 			INNER JOIN ERP_NHAPKHO_sanpham nk_sp on a.nhapkho_fk = nk_sp.SONHAPKHO_FK and  a.sanpham_fk = nk_sp.SANPHAM_FK \n"+
					"   		INNER JOIN erp_lenhsanxuat_giay lsx on lsx.pk_seq= a.LENHSANXUAT_FK  \n"+ 
					"   		LEFT JOIN NHANVIEN d on a.nguoitao = d.PK_SEQ left join NHANVIEN e on a.nguoisua = e.PK_SEQ \n"+ 
					"   WHERE 	a.congty_fk='"+this.ctyId+"' and a.LENHSANXUAT_FK is not null \n" ;
					
			
		}
		this.kdclRs = createSplittingData(50, 10, "  trangthai asc, pk_seq desc ", sql);
		
		this.nhamayRs = db.get("SELECT PK_SEQ, tennhamay FROM ERP_NHAMAY WHERE TRANGTHAI = 1");
		
	//	this.kdclRs = db.get(sql);//createSplittingData(50, 10, " pk_seq ,LenhSanXuatId, CONGDOANID ", sql);
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

	
	public String getCongtyId()
	{

		return this.ctyId;
	}

	
	public void setCongtyId(String congtyId)
	{
		this.ctyId=congtyId;
		
	}

	
	public String getChungtu() {
		
		return this.Sochungtu;
	}

	
	public void setChungtu(String Chungtu) {
		
		this.Sochungtu=Chungtu;
	
	}

	
	public String getSolo() {
		
		return this.Solo;
	}
	
	
	public void setSolo(String solo) {
		
		this.Solo=solo;
	}

	
	public int getCurrentPage() {
		
		return this.currentPages;
	}

	
	public void setCurrentPage(int current) {
		
		this.currentPages=current;
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

	
	public String getNguoiTao() {
		
		return this.nguoitao;
	}

	
	public void setNguoiTao(String nguoitao) {
		
		this.nguoitao=nguoitao;
	}

	
	public String getNgaySanXuat() {
		
		return this.ngaysanxuat;
	}

	
	public void setNgaySanXuat(String ngaysanxuat) {
		
		this.ngaysanxuat=ngaysanxuat;
	}

	
	public ResultSet getNhaMayRs() {
		
		return this.nhamayRs;
	}

	
	public void setNhaMayRs(ResultSet nhamayRs) {
		
		this.nhamayRs=nhamayRs;
	}

	
	public String getNhaMayId() {
		
		return this.nhamayId;
	}

	
	public void setNhaMayId(String nhamayid) {
		
		this.nhamayId=nhamayid;
	}



}
