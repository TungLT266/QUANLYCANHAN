package geso.traphaco.erp.beans.huychungtu.imp;

import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.erp.beans.huychungtu.IErpHuylenhsanxuatList;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
 

public class ErpHuylenhsanxuatList extends Phan_Trang implements IErpHuylenhsanxuatList 
{
	private static final long serialVersionUID = 1L;
	String congtyId;
	String userId;
	String tungay;
	String denngay;
	String trangthai;
	String msg;
	String nguoitao;
	String soCT;
	
	ResultSet hctmhRs;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	
	public ErpHuylenhsanxuatList()
	{
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.msg = "";
		this.nguoitao="";
		this.soCT="";
		
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
	
	public String getNguoitao()
	{
		return this.nguoitao;
	}

	public void setNguoitao(String nguoitao) 
	{
		this.nguoitao = nguoitao;
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
		ResultSet rs = db.get("select count(*) as c from ERP_HUYCHUNGTUMUAHANG");
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

	public ResultSet getHctMhRs() 
	{
		return this.hctmhRs;
	}

	public void setHctMhRs(ResultSet hctmhRs) 
	{
		this.hctmhRs = hctmhRs;
	}

	public void init(String search)
	{
		String query = "";
		
		if(search.length() > 0)
			query = search;
		else
			query = " select  CASE A.LOAICHUNGTU WHEN 'LSX' THEN  N'LỆNH SẢN XUẤT' WHEN 'THNL' THEN N'TIÊU HAO NGUYÊN LIỆU' WHEN 'NK' THEN N'NHẬP KHO'  WHEN 'NKM' THEN  N'NHẬP KHO MỚI' WHEN 'KDCLM' THEN N'KIỂM ĐỊNH CHẤT LƯỢNG MỚI'" +
					" WHEN 'YEUCAU' THEN N'YÊU CẦU NGUYÊN LIỆU' WHEN 'CHUYENKHO' THEN N'CHUYỂN KHO'  WHEN 'KDCL' THEN N'KIỂM ĐỊNH CHẤT LƯỢNG'  END AS LOAICHUNGTU , a.PK_SEQ as SOPHIEU, a.LENHSANXUAT_FK AS  SOCHUNGTU, isnull(a.SOCHUNGTUPHATSINH, '') as SOCHUNGTUPHATSINH, a.TRANGTHAI, a.NGAYTAO, a.NGAYSUA, b.TEN as NGUOITAO, c.TEN as NGUOISUA " +
					" from ERP_HUYLENHSANXUAT a inner join NHANVIEN b on a.nguoitao = b.pk_seq inner join NHANVIEN c on a.nguoisua = c.pk_seq " +
					" where a.congty_fk = '" + this.congtyId + "' ";
		
		this.hctmhRs = createSplittingDataNew(this.db, 50, 10, "SOPHIEU desc ", query);
	}
	
	public void DBclose() 
	{
		try 
		{
			if(this.hctmhRs != null)
			this.hctmhRs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			if(this.db!=null)
			this.db.shutDown();
		}
	}

	public String getCongtyId()
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}


	public String getsochungtu() {

		return this.soCT;
	}


	public void setsochungtu(String sochungtu) {
		this.soCT=sochungtu;
	}
	
}
