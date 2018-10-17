package geso.traphaco.distributor.beans.noptien.imp;

import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.distributor.beans.noptien.INoptienList;
import geso.traphaco.distributor.db.sql.*;

import java.io.Serializable;
import java.sql.ResultSet;

public class NoptienList extends Phan_Trang implements INoptienList, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId; //nppId
	String ten;
	String trangthai;
	String msg;
	String ngaynop;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	//List<IKhachhang> khlist;
	ResultSet khlist;
	
	ResultSet NvbhRS;
	String nvbhId;
	
	ResultSet NvgnRs;
	String nvgnId;
	
	ResultSet KhRs;
	String khId;
	
	String sochungtu;
	String tungay;
	String denngay;
	
	
	ResultSet nhomcuahang;
	String nchId;
	
	dbutils db;
	
	int currentPages;
	int[] listPages;
	
	String diachi, maFAST;
	
	public NoptienList()
	{
		this.ten = "";
		this.trangthai = "";
		this.khId = "";
		this.nvgnId = "";
		this.nvbhId = "";
		this.ngaynop = "";
		this.tungay = "";
		this.denngay = "";
		this.sochungtu = "";
		this.msg = "";
		
		
		currentPages = 1;
		listPages = new int[]{1, 2 , 3 , 4, 5, 6, 7, 8, 9, 10};
		
		this.db = new dbutils();
		
	}
	
		
	public String getDiachi() {
		return diachi;
	}

	public void setDiachi(String diachi) {
		this.diachi = diachi;
	}


	public String getMaFAST() {
		return maFAST;
	}


	public void setMaFAST(String maFAST) {
		this.maFAST = maFAST;
	}


	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getTen() 
	{
		return this.ten;
	}

	public void setTen(String ten) 
	{
		this.ten = ten;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public ResultSet getNvgnRs() 
	{
		return this.NvgnRs;
	}

	public void setNvgnRs(ResultSet nvgnRs)
	{
		this.NvgnRs = nvgnRs;
	}

	public ResultSet getNvbhRs() 
	{
		return this.NvbhRS;
	}

	public void setNvbhRs(ResultSet nvbhRs)
	{
		this.NvbhRS = nvbhRs;
	}

	public String getNvgnId() 
	{
		return this.nvgnId;
	}

	public void setNvgnId(String nvgnId) 
	{
		this.nvgnId = nvgnId;
	}

	public String getNvbhId() 
	{
		return this.nvbhId;
	}

	public void setNvbhId(String nvbhId) 
	{
		this.nvbhId = nvbhId;
	}




	public String getNchId() 
	{
		return this.nchId;
	}

	public void setNchId(String nchId) 
	{
		this.nchId = nchId;
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
	
	public void createNvgnRS()
	{
		this.NvgnRs =  this.db.get("select pk_seq, CAST(pk_seq as nvarchar(20)) + '-' + ten as Ten from NHANVIENGIAONHAN where trangthai = '1' and npp_fk ='"+ this.nppId +"' ");
	System.out.println("NPP "+this.nppId);
	}
	
	public void createNvbhRS()
	{
		this.NvbhRS =  this.db.get("select pk_seq, CAST(pk_seq as nvarchar(20)) + '-' + ten as Ten from DAIDIENKINHDOANH where trangthai = '1' and npp_fk ='"+ this.nppId +"' ");
	}
	
	public void createKhRS()
	{
		this.KhRs =  this.db.get( "select pk_seq, isnull(maFAST,'') + '-' + ten as Ten from KHACHHANG where trangthai = '1' and npp_fk ='"+ this.nppId +"' ");
	}
	
	
	public void createRS()
	{
		this.getNppInfo();
		this.createNvgnRS();
		this.createNvbhRS();
		this.createKhRS();
		
		
	}
	
	
	
	private void getNppInfo()
	{
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		this.sitecode=util.getSitecode();
	}
	public void init(String search) 
	{
		this.getNppInfo();
		String query;	
		if (search.length() == 0)
		{		
			query = "select A.PK_SEQ , a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua, A.NGAYNOP , ISNULL(A.SOIN, '') AS SOIN ,  " 
					+ " A.SOTIEN ,CASE WHEN A.NVBH_FK IS NOT NULL THEN  E.TEN + ' - ' + cast(E.PK_SEQ as nvarchar(100) )  " +
					  "                WHEN A.NVGN_FK IS NOT NULL THEN  D.TEN + ' - ' + cast(D.PK_SEQ as nvarchar(100) ) " +
					  "                WHEN A.NPP_DAT_FK IS NOT NULL THEN  G.TEN + ' - ' + cast(G.PK_SEQ as nvarchar(100) ) " +
					  "                ELSE F.TEN + ' - ' + F.MAFAST  END AS DOITUONG"
					+ " from NOPTIEN a inner join nhanvien b on a.nguoitao = b.pk_seq inner join nhanvien c on a.nguoisua = c.pk_seq  "
					+ " left join NHANVIENGIAONHAN D on a.NVGN_FK = D.PK_SEQ " +
					  " left join DAIDIENKINHDOANH  E on a.NVBH_FK = E.PK_SEQ " +
					  " left join KHACHHANG F on a.KHACHHANG_FK = F.PK_SEQ " +
					  " left join NHAPHANPHOI G on a.NPP_DAT_FK = G.PK_SEQ "+
					" where a.npp_fk='"+ this.nppId +"' ";
		}
		else
		{
			query = search + " and a.npp_fk='"+ this.nppId +"' " ;
		}
		
		System.out.println("chuoi:"+query );
	
		this.khlist =  super.createSplittingData(super.getItems(), super.getSplittings(), " PK_SEQ desc", query);
		this.createRS();
	}
	
	public void khChuaPhanTuyen(String dk)
	{}

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
		ResultSet rs = db.get("select count(*) as skh from NOPTIEN ");
		try 
		{
			rs.next();
			int count = Integer.parseInt(rs.getString("skh"));
			rs.close();
			return count;
		}
		catch(Exception e) {}
		finally{try {
			if(rs != null)
				rs.close();
		} catch (Exception e2) {
			
		}}
		
		return 0;
	}

	@Override
	public void DBclose() {
		
		try {
			if(this.NvbhRS != null)
				this.NvbhRS.close();
			if(this.NvgnRs != null)
				this.NvgnRs.close();
			if(this.KhRs != null)
				this.KhRs.close();
			if(this.db != null)
				db.shutDown();
			if(this.nhomcuahang!=null){
				nhomcuahang.close();
			}
		
			
		} catch (Exception e) {
			
		}
		
	}

	@Override
	public void setKhList(ResultSet khlist) {
		this.khlist = khlist;
		
	}

	@Override
	public ResultSet getKhList() {		
		return khlist;
	}

	@Override
	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	@Override
	public String getMsg() 
	{	
		return msg;
	}




	@Override
	public String getNgaynop() {
		// TODO Auto-generated method stub
		return this.ngaynop;
	}


	@Override
	public void setNgaynop(String ngaynop) {
		this.ngaynop= ngaynop;
		
	}


	@Override
	public String getKhId() {
		// TODO Auto-generated method stub
		return this.khId;
	}



	public void setKhId(String KhId) 
	{
		this.khId= KhId;
		
	}



	public ResultSet getKhRs() 
	{
		return this.KhRs;
	}



	public void setKhRs(ResultSet KhRs) 
	{
		this.KhRs= KhRs;
		
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


	public String getSochungtu() 
	{
		return this.sochungtu;
	}


	public void setSochungtu(String sochungtu) 
	{
		this.sochungtu = sochungtu;
	}
}

