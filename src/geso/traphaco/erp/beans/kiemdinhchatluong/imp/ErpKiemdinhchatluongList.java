package geso.traphaco.erp.beans.kiemdinhchatluong.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.erp.beans.kiemdinhchatluong.*;
import geso.traphaco.center.util.*
import geso.traphaco.center.util.*
import geso.traphaco.center.util.*
import geso.traphaco.erp.db.sql.dbutils;

public class ErpKiemdinhchatluongList extends Phan_Trang implements IErpKiemdinhchatluongList 
{
	String userId;
	String tungay;
	String denngay;
	String trangthai; 
	String ma;
	String sanpham;
	String diengiai;
	String msg;
	
	ResultSet kdclRs;
	ResultSet kdcluongRs;
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
			sql = "select a.pk_seq, b.PREFIX + CAST(a.nhapkho_fk as varchar(10)) as sonhapkho, c.ten as spTen, " +
					"a.soluong, a.solo, a.ngaytao, isnull(a.ngaysua, '') as ngaysua, a.trangthai, d.TEN as nguoitao, isnull(e.TEN, '') as nguoisua   " +
				  "from ERP_YeuCauKiemDinh a inner join ERP_NHAPKHO b on a.nhapkho_fk = b.PK_SEQ  " +
				  	"inner join ERP_SANPHAM c on a.sanpham_fk = c.PK_SEQ left join NHANVIEN d on a.nguoitao = d.PK_SEQ " +
				  	"left join NHANVIEN e on a.nguoisua = e.PK_SEQ  ";
				/*  "order by a.pk_seq desc ";
*/		}
		this.kdclRs = createSplittingData(50, 10, " pk_seq desc ", sql);
		/*System.out.println("__Yeucaukiemdinh : " + sql);
		this.kdclRs = db.get(sql);*/
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

	public int getNum()
	{
		return this.num;
	}
	
	public void setNum(int num)
	{
		this.num = num;
		listPages = PhanTrang.getListPages(num);
	}


	public int getCurrentPage() {
	
		return this.currentPages;
	}

	@Override
	public void setCurrentPage(int current) {
		this.currentPages = current;
		
	}


	public int[] getListPages() {
		return this.listPages;
	}

	
	public void setListPages(int[] listPages) {
		this.listPages = listPages;
		
	}


}
