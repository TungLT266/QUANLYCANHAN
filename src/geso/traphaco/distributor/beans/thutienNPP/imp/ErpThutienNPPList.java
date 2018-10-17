package geso.traphaco.distributor.beans.thutienNPP.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.thutienNPP.IErpThutienNPPList;

public class ErpThutienNPPList  extends Phan_Trang  implements IErpThutienNPPList
{
	private static final long serialVersionUID = 1L;
	String userId;
	String tungay;
	String denngay;
	
	String nccId;
	ResultSet nppRs;
	String htttId;
	ResultSet htttRs;   
	String ctyId;
	String trangthai;
	String msg;
	String nppId;
	
	String nvgnId;
	ResultSet nvgnList;
	
	String tdvId;
	ResultSet tdvList;
	String sotienConlai;
	
	String maphieu;
	String sohoadon;
	String sophieunop;
	
	ResultSet tthdRs;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	Utility util;
	
	public ErpThutienNPPList()
	{
		this.tungay = "";
		this.denngay = "";
		this.nccId = "";
		this.htttId = "";
		this.trangthai = "";
		this.msg = "";
		this.nppId="";
		this.ctyId = "";
		this.maphieu= "";
		this.sohoadon="";
		this.sophieunop = "";
		currentPages = 1;
		num = 1;
		util=new Utility();
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
	
	public String getNppId()
	{
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
	}

	public String getCtyId()
	{
		return this.ctyId;
	}

	public void setCtyId(String ctyId) 
	{
		this.ctyId = ctyId;
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

	public String getNccId() 
	{
		return this.nccId;
	}

	public void setNccId(String nccid) 
	{
		this.nccId = nccid;
	}

	public ResultSet getNccList() 
	{
		return this.nppRs;
	}

	public void setNccList(ResultSet ncclist) 
	{
		this.nppRs = ncclist;
	}

	public String getHtttId() 
	{
		return this.htttId;
	}

	public void setHtttId(String htttid) 
	{
		this.htttId = htttid;
	}

	public ResultSet getHtttList()
	{
		return this.htttRs;
	}

	public void setHtttList(ResultSet htttlist)
	{
		this.htttRs = htttlist;	
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	public void setmsg(String msg)
	{
		this.msg = msg;
	}

	public String getmsg() 
	{
		return this.msg;
	}

	public ResultSet getTThoadonList() 
	{
		return this.tthdRs;
	}

	public void setTThoadonList(ResultSet tthdlist) 
	{
		this.tthdRs = tthdlist;	
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
		ResultSet rs = db.get("select count(*) as c from ERP_thutien");
		return PhanTrang.getLastPage(rs);
	}

	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage)
	{
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}
	
	private void getNppInfo()
	{		
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
	}
	
	public void init(String search)
	{
		this.getNppInfo();
		String query = "";
		if(search.length() <= 0)
		{			
			query =  
		     /*" select a.pk_seq as tthdId, a.trangthai, a.ngaychungtu,  a.ngaytao, a.ngaysua, isnull(noptienids, '') as noptienids,   \n"+
		     "        d.ten as nguoitao, e.ten as nguoisua,  isnull(a.SOTIENTHU,0) AS THUCTHU, a.CONLAI as SOTIENCONLAI , isnull(tennguoinop, '') as tennguoinop " +
		     " from ERP_THUTIENNPP a  inner join NHANVIEN d on a.nguoitao = d.pk_seq   \n"+
		     "      inner join NHANVIEN e on a.nguoisua = e.pk_seq \n"+
		     " where a.NPP_FK= '"+ this.nppId +"' and a.trangthai != 2 ";*/
			
			 " select distinct a.pk_seq as tthdId, a.trangthai, a.ngaychungtu,  a.ngaytao, a.ngaysua, isnull(noptienids, '') as noptienids, " + 
			 " d.ten as nguoitao, e.ten as nguoisua,  isnull(a.SOTIENTHU,0) AS THUCTHU, a.CONLAI AS SOTIENCONLAI, isnull(tennguoinop, '') as tennguoinop," +
			 "        ISNULL (  (Select distinct KH.MAFAST + ' - ' + KH.TEN  + ',' AS [text()]  "+
		  	 "           From ERP_THUTIENNPP_HOADON K1 inner join KHACHHANG KH on K1.KHACHHANG_FK = Kh.PK_SEQ "+
		  	 "           Where K1.THUTIENNPP_FK = a.PK_SEQ  and isnull(a.NOPTIENIDS,'') = '' "+
		  	 "           For XML PATH ('') ) , '' ) as khachhangs  "+ 
			 " from ERP_THUTIENNPP a "  +
			 " inner join NHANVIEN d on a.nguoitao = d.pk_seq " + 
			 " inner join NHANVIEN e on a.nguoisua = e.pk_seq " +
			 " left join ERP_THUTIENNPP_HOADON f on f.THUTIENNPP_FK=a.PK_SEQ "+
			 " left join HOADON g on g.PK_SEQ=f.HOADONNPP_FK and f.KHACHHANG_FK in (select pk_seq from KHACHHANG where kbh_fk='100025') " +
			 " left join ERP_HOADONNPP h on h.PK_SEQ=f.HOADONNPP_FK and f.KHACHHANG_FK in (select pk_seq from KHACHHANG where kbh_fk='100052') " +
			 " where a.NPP_FK= '"+ this.nppId +"' and a.trangthai != 2 ";		
		}
		else
			query = search + " and  a.NPP_FK= '"+ this.nppId +"'" ;
		
		System.out.println("Query init1: " + query);
		
		this.tthdRs = createSplittingDataNew(this.db, 50, 10, "ngaychungtu desc, trangthai asc, tthdId desc ", query);    
		this.nppRs = db.get("select pk_seq, isnull(maFAST,'') + '-' + ten as TEN from KHACHHANG where trangthai = '1' and npp_fk = '"+ this.nppId +"' ");
		this.tdvList = db.get( " select B.PK_SEQ , CAST(B.pk_seq as nvarchar(20)) + ' - ' + B.ten as Ten " +
							   " from DAIDIENKINHDOANH_NPP A INNER JOIN DAIDIENKINHDOANH B ON A.DDKD_FK= B.PK_SEQ " +
							   " where  A.npp_fk ='"+ this.nppId +"' AND B.TRANGTHAI = '1' ");
		this.nvgnList = db.get("select pk_seq, ten  from NHANVIENGIAONHAN where trangthai = '1' and npp_fk = '"+ this.nppId +"' ");
	
	}

	public void DBclose() 
	{
		
			try {
				if(this.nppRs != null)
					this.nppRs.close();
				
				if(this.htttRs != null)
					this.htttRs.close();
				
				if(this.tthdRs != null)
					this.tthdRs.close();
				this.db.shutDown();		
				
			} catch (SQLException e) {e.printStackTrace();
}
		
		
	}


	public String getMaphieu() 
	{
		return this.maphieu;
	}

	public void setMaphieu(String maphieu) 
	{
	  this.maphieu = maphieu;
		
	}

	public String getNvgnId() 
	{
		return this.nvgnId;
	}

	public void setNvgnId(String nvgnId) 
	{
		this.nvgnId = nvgnId;
		
	}

	public ResultSet getNvgnList() 
	{
		return this.nvgnList;
	}

	public void setNvgnList(ResultSet nvgnList) 
	{
		this.nvgnList = nvgnList;
		
	}

	public String getTdvId()
	{
		return this.tdvId;
	}

	public void setTdvId(String tdvId) 
	{
		this.tdvId = tdvId;
		
	}


	public ResultSet getTdvList() 
	{
		return this.tdvList;
	}



	public void setTdvList(ResultSet tdvList) 
	{
		this.tdvList = tdvList;
		
	}

	public void setSotienConlai(String sotienConlai) 
	{
		this.sotienConlai = sotienConlai;
		
	}

	public String getSotienConlai() 
	{
		return this.sotienConlai;
	}

	
	public String getSoHoaDon() {
		
		return this.sohoadon;
	}

	
	public void setSoHoaDon(String sohoadon) {
		
		this.sohoadon=sohoadon;
	}

	public String getSophieunop() 
	{
		return this.sophieunop;
	}

	public void setSophieunop(String sophieunop) 
	{
		this.sophieunop = sophieunop;
	}

	
}
