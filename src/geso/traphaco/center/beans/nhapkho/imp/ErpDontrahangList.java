package geso.traphaco.center.beans.nhapkho.imp;

import java.sql.ResultSet;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.beans.nhapkho.IErpDontrahangList;

public class ErpDontrahangList extends Phan_Trang implements IErpDontrahangList
{
	private static final long serialVersionUID = 1L;
	String userId;
	String sohdId;
	String nppId;
	String tungay;
	String denngay;
	String trangthai;

	String sophieu;
	String lydo;
	String msg;
	
	ResultSet nhapkhoRs;
	ResultSet sohdRs;
	ResultSet nppRs;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	
	public ErpDontrahangList()
	{
		this.tungay = "";
		this.sohdId ="";
		this.nppId ="";
		this.denngay = "";
		this.trangthai = "";
		this.sophieu="";
		this.lydo = "";
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
	
	
	public String getnppId()
	{
		return this.nppId;
	}

	public void setnppId(String nppId) 
	{
		this.nppId = nppId;
	}
	
	public String getSohdId()
	{
		return this.sohdId;
	}

	public void setSohdId(String sohdId) 
	{
		this.sohdId = sohdId;
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
        
		if(search.length() > 0)
			query = search;
		else
		{
			query = "\n  select isnull(a.sohoadon,'') sohoadon,a.PK_SEQ, a.trangthai, a.ngaytra, a.lydo, NV.TEN as nguoitao, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua,  " + 
					"\n  			b.Ten as doituong, b.ten as tennpp, b.pk_seq as manpp    " + 
					"\n  from ERP_DONTRAHANG a left join NHAPHANPHOI b on a.doituongId = b.pk_seq    " + 
					"\n  	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ    " + 
					"\n  	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.pk_seq > 0 ";
		} 
			
		System.out.println("___CHUYEN KHO: " + query);
		this.nhapkhoRs = createSplittingData(50, 10, "PK_SEQ desc, trangthai asc ", query);
		this.nppRs = db.get("select pk_seq, ten from nhaphanphoi where trangthai = 1 and iskhachhang =1");
		
	}
	
	public void DBclose() 
	{
		this.db.shutDown();
	}

	public String getSophieu()
	{
		return sophieu;
	}

	public void setSophieu(String sophieu) 
	{
		this.sophieu = sophieu;
	}

	public String getLydo() 
	{
		return lydo;
	}

	public void setLydo(String lydo) 
	{
		this.lydo = lydo;
	}

	public String getTungayTao() 
	{
		return this.tungay;
	}

	public void setTungayTao(String tungay) 
	{
		this.tungay =tungay;	
	}

	public String getDenngayTao() 
	{
		return this.denngay;
	}

	public void setDenngayTao(String denngay) 
	{
		this.denngay = denngay;
	}

	public ResultSet getNhapkhoRs() 
	{
		return this.nhapkhoRs;
	}

	public void setNhapkhoRs(ResultSet nkRs) 
	{
		this.nhapkhoRs = nkRs;
	}
	
	public ResultSet getnppRs() 
	{
		return this.nppRs;
	}

	public void setnppRs(ResultSet nppRs) 
	{
		this.nppRs = nppRs;
	}
	
	
	public ResultSet getSohdRs() 
	{
		return this.sohdRs;
	}

	public void setSohdRs(ResultSet sohdRs) 
	{
		this.sohdRs = sohdRs;
	}

}
