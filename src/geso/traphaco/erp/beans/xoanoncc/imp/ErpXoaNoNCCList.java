package geso.traphaco.erp.beans.xoanoncc.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.DinhKhoanKeToan;
import geso.traphaco.center.util.IDinhKhoanKeToan;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.IThongTinHienThi;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.ThongTinHienThi;
import geso.traphaco.erp.beans.xoanoncc.IErpXoaNoNCCList;;

public class ErpXoaNoNCCList  extends Phan_Trang  implements IErpXoaNoNCCList
{
	private static final long serialVersionUID = 1L;
	String userId;
	String tungay;
	String denngay;
	String maphieu;
	String ngaychungtu;
	String nccId;
	ResultSet nccRs;
	String nvId = "";
	ResultSet nvRs;
	String htttId;
	ResultSet htttRs;   
	
	String trangthai;
	String msg;
	
	String congtyId;
	String nppdangnhap;
	
	ResultSet tthdRs;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	List<IThongTinHienThi> hienthiList;
	
	dbutils db;
	
	public ErpXoaNoNCCList()
	{
		this.tungay = "";
		this.denngay = "";
		this.nccId = "";
		this.htttId = "";
		this.trangthai = "";
		this.msg = "";
		this.maphieu = "";
		this.ngaychungtu = "";
		this.congtyId ="";
		this.nppdangnhap = "";
		currentPages = 1;
		num = 1;
		
		this.hienthiList = new ArrayList<IThongTinHienThi>();
		
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
		return this.nccRs;
	}

	public void setNccList(ResultSet ncclist) 
	{
		this.nccRs = ncclist;
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
		ResultSet rs = db.get("select count(*) as c from ERP_xoakhachhangtt");
		return PhanTrang.getLastPage(rs);
	}

	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage)
	{
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}
	private String LayDuLieu(String id) {
		String query = "";
		
		if(query.trim().length()<=0){
			query = "SELECT '' NO_CO, '' PK_SEQ, '' NGAYHOADON, '' SOTIEN, '' SOHIEUTAIKHOAN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP \n " +
					"FROM ERP_THUENHAPKHAU \n " +
					"WHERE PK_SEQ = '"+id+"'";
		}
		return query;
	}
	
	public void init(String search)
	{
		String query = "";
//		if(search.length() <= 0)
//		{
			query = "select a.pk_seq as tthdId, a.trangthai, a.ngaychungtu, a.ngayghiso, a.ngaytao, a.ngaysua," +
					"      case when a.loaixoano = 0 then ncc.ten else nv.ten end as tendoituong, "+
					"      d.ten as nguoitao, e.ten as nguoisua " +
					"from ERP_XOANONCC a " +
					"     left join ERP_NHACUNGCAP ncc on a.NCC_FK= ncc.PK_SEQ  " +
					"     left join ERP_NHANVIEN nv on a.NHANVIEN_FK= nv.PK_SEQ  " +
					"     inner join NHANVIEN d on a.nguoitao = d.pk_seq "+
					"     inner join NHANVIEN e on a.nguoisua = e.pk_seq "+
					"where 1=1 ";
//		}
//		else
//			query = search;
			
			if(this.congtyId.trim().length() > 0){
				query += " and a.CONGTY_FK = "+this.congtyId;
			}
			
			if(this.getTungay().length() > 0)
				query += " and a.ngaychungtu >= '" + this.getTungay() + "'";
			
			if(this.getDenngay().length() > 0)
				query += " and a.ngaychungtu <= '" + this.getDenngay() + "'";
			
			if(this.getNgayChungTu().length() > 0)
				query += " and a.ngaychungtu = '" + this.getNgayChungTu() + "'";
			
			if(this.getMaPhieu().length() > 0)
				query += " and a.pk_seq like '%" + this.getMaPhieu() + "%'";
			
			if(this.getNccId().length() > 0)
				query += " and ncc.pk_seq = '" + this.getNccId() + "'";
			
			if(this.getNvId() != null && this.getNvId().length() > 0)
				query += " and nv.pk_seq = '" + this.getNvId() + "'";
			
			if(this.getTrangthai().length() > 0)
				query += " and a.trangthai = '" + this.getTrangthai() + "'";
			
			
		
		System.out.println("Query init: " + query);

		String query_init = createSplittingData_ListNew(this.db, 25, 10, "tthdId desc, trangthai asc", query);
		
		ResultSet rs = db.get(query_init);
		
		List<IThongTinHienThi> htList = new ArrayList<IThongTinHienThi>();
		
		try
		{
			if(rs!= null)
			{
				IThongTinHienThi ht = null;
				while(rs.next())
				{			
					ht = new ThongTinHienThi();
					//LAY DINH KHOAN KE TOAN
					String dk = LayDuLieu(rs.getString("tthdId"));					
					
					
					ResultSet rsKT = db.get(dk);
					List<IDinhKhoanKeToan> ktList = new ArrayList<IDinhKhoanKeToan>();
						if(rsKT!= null)
						{
							IDinhKhoanKeToan kt = null;
							while(rsKT.next())
							{
								kt = new DinhKhoanKeToan(rsKT.getString("PK_SEQ"), rsKT.getString("NO_CO"),rsKT.getString("SOHIEUTAIKHOAN"),
											rsKT.getString("SOTIEN"),rsKT.getString("DOITUONG"),
											rsKT.getString("TRUNGTAMCHIPHI"),rsKT.getString("TRUNGTAMDOANHTHU"), "");
								ktList.add(kt);
							}
							
							rsKT.close();
						}
												
					// INIT					
						
						ht.setId(rs.getString("tthdId"));
						ht.setNgaychungtu(rs.getString("ngaychungtu"));
						ht.settendoituong(rs.getString("tendoituong"));
						ht.setTrangthai(rs.getString("trangthai"));
						ht.setNGAYTAO(rs.getString("NGAYTAO"));
						ht.setNGUOITAO(rs.getString("NGUOITAO"));
						ht.setNGUOISUA(rs.getString("NGUOISUA"));
						ht.setNGAYSUA(rs.getString("NGAYSUA"));
						
						ht.setLayDinhkhoanKT(ktList);
					
					htList.add(ht);																	
				}
				rs.close();
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		this.hienthiList = htList;
		

		this.nccRs = db.get("select pk_seq, ma + ', ' + ten as nppTen from ERP_NHACUNGCAP where trangthai = '1' and duyet = '1' and CONGTY_FK = "+this.congtyId+"");
		this.htttRs = db.get("select pk_seq, ma, ten from ERP_HINHTHUCTHANHTOAN where trangthai = '1'");
		//this.nvRs = db.get("select pk_seq, ma, ten as nvTen from NHANVIEN where trangthai = '1' and CONGTY_FK = "+this.congtyId+"");
	}

	public void DBclose() 
	{
		
		try {
			if(this.nccRs != null)
				this.nccRs.close();
			
			if(this.htttRs != null)
				this.htttRs.close();
			
			if(this.tthdRs != null)
				this.tthdRs.close();
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		this.db.shutDown();		
		this.db = null;
	}
	public void NewDbUtil()
	{
		  if(this.db == null )
		  {
			   this.db  = new dbutils();
		  }
	}
	
	public List<IThongTinHienThi> getHienthiList() {
		
		return this.hienthiList;
	}


public void setHienthiList(List<IThongTinHienThi> hienthiList) {
	
	this.hienthiList = hienthiList;
}


public String getMaPhieu() {
	
	return this.maphieu;
}


public void setMaPhieu(String maphieu) {
	this.maphieu=maphieu;
	
}

public String getNgayChungTu() {
	
	return this.ngaychungtu;
}


public void setNgayChungTu(String ngaychungtu) {
	this.ngaychungtu=ngaychungtu;
	
}


	public String getNvId() 
	{
		return this.nvId;
	}
	
	
	public void setNvId(String nvid) 
	{
		this.nvId = nvid ;
	}
	
	
	public ResultSet getNvList() 
	{
		return this.nvRs ;
	}
	
	
	public void setNvList(ResultSet nvlist) 
	{
		this.nvRs = nvlist;
	}

	
	public String getCongtyId() {
		
		return this.congtyId;
	}

	
	public void setCongtyId(String congtyId) {
		
		this.congtyId = congtyId;
	}

	
	public String getnppdangnhap() {
		
		return this.nppdangnhap;
	}

	
	public void setnppdangnhap(String nppdangnhap) {
		
		this.nppdangnhap = nppdangnhap;
	}

	private void getNppInfo()
	{		
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppdangnhap=util.getIdNhapp(this.userId);
	}
}