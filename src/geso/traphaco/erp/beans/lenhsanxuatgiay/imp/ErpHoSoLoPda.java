package geso.traphaco.erp.beans.lenhsanxuatgiay.imp;

import java.sql.ResultSet;

import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.erp.beans.lenhsanxuatgiay.IErpHoSoLoPda;
import geso.traphaco.erp.beans.lenhsanxuatgiay.IErpLenhsanxuatList;
import geso.traphaco.erp.db.sql.dbutils;

public  class ErpHoSoLoPda  extends Phan_Trang implements IErpHoSoLoPda {

	private static final long serialVersionUID = 1L;
	String congtyId;
	String nppId;
	String userId;
	String lenhsanxuat;
	String ngayin;
	String ca;
	String congdoan;
	String smg;
	dbutils db;
	ResultSet congdoanRs=null;
	ResultSet lsxRs=null;
	String hoso_fk="";
	

	

	public ErpHoSoLoPda()
	{
		this.congtyId="";
		this.userId="";
		this.lenhsanxuat="";
		this.ngayin="";
		this.ca="";
		this.congdoan="";
		this.nppId="";
		this.smg="";
		this.db= new dbutils();
	}
	
	public ErpHoSoLoPda(String cty, String user, String lsx, String ngay,  String ca, String congdoan, String smg, String npp)
	{
		this.congtyId=cty;
		this.userId=user;
		this.lenhsanxuat=lsx;
		this.ngayin=ngay;
		this.ca=ca;
		this.congdoan=congdoan;
		this.nppId=npp;
		this.smg=smg;
		
	}
	
	
	public void createRs() {
		String query="SELECT PK_SEQ , CAST(PK_SEQ as nvarchar) +' - '+ DIENGIAI AS TEN FROM ERP_LENHSANXUAT_GIAY WHERE CONGTY_FK='" + this.congtyId + "' AND NPP_FK='" + this.nppId + "'";
		System.out.println("lay lsx:"+ query);
		this.lsxRs= this.db.get(query);
		
		if( this.lenhsanxuat != null && this.lenhsanxuat.trim().length()>0 ){
		query="select  d.PK_SEQ as congdoanid,d.DienGiai as tencongdoan, "
						 + "\n ISNULL( loaimauinSxId,'0') as  loaimauinSxId, ISNULL(loaiin.maloai,'') as maloaiin, ISNULL(loaiin.MAUTEMPLATE,'') as MAUTEMPLATE   from ERP_LENHSANXUAT_GIAY a "
                         + "\n inner join Erp_KichBanSanXuat_Giay b on a.KICHBANSANXUAT_FK=b.PK_SEQ"
                         + "\n inner join Erp_KichBanSanXuat_CongDoanSanXuat_Giay c on c.KichBanSanXuat_FK=b.PK_SEQ"
                         + "\n inner join Erp_CongDoanSanXuat_Giay d on d.PK_SEQ=c.CongDoanSanXuat_FK"
                         +" \n left join LOAIMAUIN_SANXUAT loaiin on loaiin.pk_Seq= d.loaimauinSxId "
                         + "\n where a.PK_SEQ='" + this.lenhsanxuat + "' and a.CONGTY_FK='" + this.congtyId + "'  and d.TRANGTHAI=1 and b.TrangThai=1 and b.CongTy_FK='" + this.congtyId + "' order by ThuTu";
		System.out.println("lay cong doan:"+ query);
		this.congdoanRs= this.db.get(query);
		}
	
	}
	
	public String getSmg() {
		return smg;
	}

	public void setSms(String smg) {
		this.smg = smg;
	}
	
	public void DBclose() 
	{
		try{
			if(!(this.db == null)){
				this.db.shutDown();
			}
		}
		catch(Exception err){
			
		}
	}
	
	public String getNppId() {
		return nppId;
	}

	public void setNppId(String nppId) {
		this.nppId = nppId;
	}
	
	public String getCongtyId() {
		return congtyId;
	}
	public void setCongtyId(String congtyId) {
		this.congtyId = congtyId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getLenhsanxuat() {
		return lenhsanxuat;
	}
	public void setLenhsanxuat(String lenhsanxuat) {
		this.lenhsanxuat = lenhsanxuat;
	}
	public String getNgayin() {
		return ngayin;
	}
	public void setNgayin(String ngayin) {
		this.ngayin = ngayin;
	}
	public String getCa() {
		return ca;
	}
	public void setCa(String ca) {
		this.ca = ca;
	}
	public String getCongdoan() {
		return congdoan;
	}
	public void setCongdoan(String congdoan) {
		this.congdoan = congdoan;
	}
	public ResultSet getCongdoanRs() {
		return congdoanRs;
	}

	public void setCongdoanRs(ResultSet congdoanRs) {
		this.congdoanRs = congdoanRs;
	}

	public ResultSet getLsxRs() {
		return lsxRs;
	}

	@Override
	public void setLsxRs(ResultSet lsxRs) {
		// TODO Auto-generated method stub
		
	}
	
	public String getHoso_fk() {
		return hoso_fk;
	}

	public void setHoso_fk(String hoso_fk) {
		this.hoso_fk = hoso_fk;
	}
}
