package geso.traphaco.distributor.beans.chuyenkhonoibo.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.distributor.beans.chuyenkhonoibo.IErpChuyenKhonoiboList;

import java.sql.ResultSet;

public class ErpChuyenKhonoiboList extends Phan_Trang implements IErpChuyenKhonoiboList
{
	private static final long serialVersionUID = 1L;
	String userId;
	String tungay;
	String denngay;
	String trangthai;

	String sophieu;
	String lydo;
	String msg;
	
	ResultSet nhapkhoRs;
	ResultSet khRs;
	String khId;
	
	String nppId;
	String nppTen;
	String sitecode;
	String sochungtu;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	
	public ErpChuyenKhonoiboList()
	{
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.sophieu="";
		this.sochungtu="";
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
        
		if(search.length() > 0)
			query = search;
		else
		{
			query = 
					"select a.PK_SEQ, a.trangthai,isnull( a.sochungtu,'')as SOCHUNGTU ,a.ngaychuyen, a.ghichu as lydo, NV.TEN as nguoitao, b.ten as khoxuat, isnull(c.ten,'') as nppTEN, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua,isnull(c.ten,'') as tentdv   " +
					"from ERP_CHUYENKHO a inner join KHO b on a.khoxuat_fk = b.pk_seq left join daidienkinhdoanh c on a.ddkd_fk = c.pk_seq  " +
					"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
					"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.tructhuoc_fk = '" + this.nppId + "' and a.loaichuyenkho = '1' ";
			} 
			
		System.out.println("___CHUYEN KHO: " + query);
		
		this.nhapkhoRs = createSplittingDataNew(this.db, 50, 10, "ngaychuyen desc, SOCHUNGTU desc, trangthai asc ", query);
		this.khRs = db.get("select b.PK_SEQ, b.TEN from Daidienkinhdoanh_Npp a,Daidienkinhdoanh b where a.ddkd_fk = b.pk_seq and b.trangthai = '1' and a.npp_fk = '" + this.nppId + "'");
		System.out.println("Dai dien kinh doanh: "+"select b.PK_SEQ, b.TEN from Daidienkinhdoanh_Npp a,Daidienkinhdoanh b where a.ddkd_fk = b.pk_seq and b.trangthai = '1' and a.npp_fk = '" + this.nppId + "'");
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
	
	public String getNppId() 
	{
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
	}
	
	public String getNppTen() 
	{
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
		
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		String sql = "";
		try
		{
			sql = "select phanloai,LOAI from nhanvien where pk_seq=" + this.userId;
			ResultSet rs = this.db.get(sql);
			if (rs != null)
			{
				if (rs.next())
				{
					String phanloai = rs.getString("phanloai");
					String loaiNv= rs.getString("LOAI")==null?"":rs.getString("LOAI");
					if (rs.getString("phanloai").equals("1")||( phanloai.equals("2")   && loaiNv.equals("3")   )  )
					{
						this.nppId=util.getIdNhapp(this.userId);
						this.nppTen=util.getTenNhaPP();
						//this.dangnhap = util.getDangNhap();
						this.sitecode=util.getSitecode();
					}else  this.nppId ="1";
					rs.close();
					System.out.println("NPPID: "+this.nppId +" USERID: "+this.userId);					
				}
			}
		} catch (Exception er)
		{

		}
			
	}

	public String getSochungtu() {
		return this.sochungtu;
	}

	public void setSochungtu(String sochungtu) {
		this.sochungtu=sochungtu;
	}

	public ResultSet getKhRs() {
		return this.khRs;
	}

	public void setKhRs(ResultSet khrs) {
		this.khRs=khrs;
		
	}

	public String getKhId() {
		return this.khId;
	}

	public void setKhId(String KhId) {
		this.khId=KhId;
		
	}

	String type;
	@Override
  public String getType()
  {
	  return type;
  }

	@Override
  public void setType(String type)
  {
	  this.type=type;
  }

	
}
