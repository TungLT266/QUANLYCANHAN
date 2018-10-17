package geso.traphaco.erp.beans.danhgialaichiphitratruoc.imp;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;
import geso.dms.center.util.Utility;
import geso.dms.db.sql.dbutils;
import geso.traphaco.erp.beans.danhgialaichiphitratruoc.IErp_DanhGiaLaiCPTTList;
import geso.traphaco.erp.beans.danhgialaitaisan.IErp_DanhGiaLaiTaiSanList;


import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
public class Erp_DanhGiaLaiCPTTList extends Phan_Trang implements IErp_DanhGiaLaiCPTTList
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	dbutils db;

	
	ResultSet RsDc;
	
	String tscdId;
	ResultSet tscdRs;
	ResultSet pbRs;
	String pbId;
	String sochungtu;
	String sodieuchuyen;
	String tungay;
	String denngay;
	String phongban;
	String trangthai;
	String ctyId;
	String msg;
	private int num;
	private int[] listPages;
	private int currentPages;
	
	
	public Erp_DanhGiaLaiCPTTList( )
	{
		this.sochungtu="";
		this.sodieuchuyen="";
		this.tungay="";
		this.denngay="";
		this.phongban="";
		this.pbId="";
		this.ctyId="";
		this.msg="";
		this.trangthai="";
		currentPages = 1;
		num = 1;
		this.db = new dbutils();
		
	}
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	public void init(String sql)
	{
		String query = "";

			if (sql.length() > 0)
			{
				query = sql;
				this.RsDc = createSplittingDataNew(this.db, 50, 10, "pk_seq desc ", query);
				/*this.TsList = this.db.get(query);*/
			}
			else
			{
				query="SELECT dc.pk_seq,dc.ngaychungtu,dc.Sochungtu,dc.trangthai,ccdc.diengiai as TAISAN FROM ERP_DANHGIALAICHIPHITRATRUOC dc" +
						" inner join erp_congcudungcu ccdc on dc.ccdc_fk=ccdc.pk_seq ";
				System.out.println("aaaaaaaaa"+query);
				this.RsDc = createSplittingData(50, 10, "pk_seq desc ", query);
				/*this.TsList = this.db.get(query);*/
			}
	}
	public void createRs()
	{
		
		String query="";	
		
		query = "select PK_SEQ,MA +'-' +TEN  as TEN from ERP_DONVITHUCHIEN WHERE TRANGTHAI = 1 ORDER BY MA ";
		this.pbRs = db.get(query);
	}
	
	public void DBClose()
	{
		try
		{
			
			if(this.RsDc!=null)this.RsDc.close();
			if(this.pbRs!=null)this.pbRs.close();
		}
		catch (Exception er)
		{
			er.printStackTrace();
		}
		finally
		{
			if (db != null)
			{
				db.shutDown();
			}
		}
	}


	public int getCurrentPage() {
		
		return this.currentPages;
	}


	public void setCurrentPage(int current) {
		this.currentPages = current;
		
	}


	public int[] getListPages() {
		
		return this.listPages;
	}


	public void setListPages(int[] listPages) {
		this.listPages= listPages;
		
	}

	public String getSochungtu() {
		return sochungtu;
	}

	public void setSochungtu(String sochungtu) {
		this.sochungtu = sochungtu;
	}

	public String getPbId() {
		return pbId;
	}

	public void setPbId(String pbId) {
		this.pbId = pbId;
	}

	public ResultSet getPbRs() {
		return pbRs;
	}

	public void setPbRs(ResultSet pbRs) {
		this.pbRs = pbRs;
	}

	public ResultSet getRsDc() {
		return RsDc;
	}

	public void setRsDc(ResultSet rsDc) {
		RsDc = rsDc;
	}

	public String getSodieuchuyen() {
		return sodieuchuyen;
	}

	public void setSodieuchuyen(String sodieuchuyen) {
		this.sodieuchuyen = sodieuchuyen;
	}

	public String getTungay() {
		return tungay;
	}

	public void setTungay(String tungay) {
		this.tungay = tungay;
	}

	public String getDenngay() {
		return denngay;
	}

	public void setDenngay(String denngay) {
		this.denngay = denngay;
	}

	public String getPhongban() {
		return phongban;
	}

	public void setPhongban(String phongban) {
		this.phongban = phongban;
	}
	public String getCtyId() {
		return ctyId;
	}
	public void setCtyId(String ctyId) {
		this.ctyId = ctyId;
	}
	public String getTrangthai() {
		return trangthai;
	}
	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
