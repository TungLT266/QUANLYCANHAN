package geso.traphaco.erp.beans.huychungtuketoan.imp;

import geso.traphaco.center.util.DinhKhoanKeToan;
import geso.traphaco.center.util.IDinhKhoanKeToan;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.IThongTinHienThi;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.ThongTinHienThi;
import geso.traphaco.erp.beans.huychungtuketoan.IErpHuychungtuketoanList;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ErpHuychungtuketoanList extends Phan_Trang implements IErpHuychungtuketoanList
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
	
	List<IThongTinHienThi> hienthiList;
	
	ResultSet hctmhRs;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	
	public ErpHuychungtuketoanList()
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
		this.hienthiList = new ArrayList<IThongTinHienThi>();
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
	
	private String LayDuLieu(String id, String loaict) {
		String laytk= "";
		if(loaict.equals("1"))
		{
		String query=
					" select dct.NGAYCHUNGTU, ISNULL(dct.LOAI,0) as loaidc, dct.SOTIENVND,dct.PHIVND, dct.VATVND, " +
					"  (select NGANHANG_FK from ERP_NGANHANG_CONGTY where PK_SEQ = dct.NGANHANGCHUYEN_FK) as NHCHUYENID, " +
					"  (select NGANHANG_FK from ERP_NGANHANG_CONGTY where PK_SEQ = dct.NGANHANGNHAN_FK)as NHNHANID," +
					"  (select TEN from ERP_DOITUONGKYQUY where PK_SEQ = dct.NHKYQUY_FK)as NHKYQUYID," +
					"  (select NGANHANG_FK from ERP_NGANHANG_CONGTY where PK_SEQ = dct.NHTRICHPHI_FK)as NHTRICHPHIID," +
					"  (select TaiKhoan_FK from ERP_NGANHANG_CONGTY where PK_SEQ=dct.NGANHANGCHUYEN_FK) as taikhoan_NHChuyen, "+
					"  (select TaiKhoan_FK from ERP_NGANHANG_CONGTY where PK_SEQ=dct.NGANHANGNHAN_FK) as taikhoan_NHNhan, "+
					"  (select TaiKhoanKT_FK from ERP_DOITUONGKYQUY where PK_SEQ=dct.NHKYQUY_FK) as taikhoan_NHKyquy, "+
					"  (select TaiKhoan_FK from ERP_NGANHANG_CONGTY where PK_SEQ=dct.NHTRICHPHI_FK) as taikhoanCO_NHTP, "+
					"  (select PK_SEQ from ERP_TAIKHOANKT where SOHIEUTAIKHOAN= '64250000') as taikhoanNO_PHI, "+
					"  (select PK_SEQ from ERP_TAIKHOANKT where SOHIEUTAIKHOAN= '13311000') as taikhoanNO_VAT "+
					" from ERP_DIEUCHUYENTIEN dct "+
					" where PK_SEQ = "+ id +" ";

		System.out.println("CAU QUERY KT "+query);
		
		
		
		ResultSet rs = db.get(query);
		
		try{
			if(rs!= null)
			{
//				String NHChuyenId= "";
//				String NHNhanId = "";
				String NHKyquyId = "";
//				String NHTrichphi= "";
				String taikhoanNO_fk= "";
				String taikhoanCO_fk= "";
				
//				String nhkyquyId = "";
				
				while(rs.next())
				{
//					  NHChuyenId= rs.getString("NHCHUYENID")== null ? "":rs.getString("NHCHUYENID") ;
//				      NHNhanId= rs.getString("NHNHANID")== null ? "": rs.getString("NHNHANID");
				      NHKyquyId= rs.getString("NHKYQUYID")== null ? "": rs.getString("NHKYQUYID");
//				      NHTrichphi = rs.getString("NHTRICHPHIID")== null ? "":  rs.getString("NHTRICHPHIID");
				      
//					  String nam = rs.getString("NGAYCHUNGTU").substring(0, 4);
//					  String thang = rs.getString("NGAYCHUNGTU").substring(5, 7);
					  
//					  String madoituongno = NHNhanId;
					  					  
					  //GHI NHAN SO TIEN DIEU CHUYEN
				      double sotien= rs.getDouble("SOTIENVND");
		    		  
				      if(sotien > 0)
				      {
				    	  if(laytk.trim().length()>0) laytk += " UNION ALL \n";				    	  
				    	  
				    	  taikhoanCO_fk = rs.getString("taikhoan_NHChuyen")== null ? "":rs.getString("taikhoan_NHChuyen");
				    	  
				    	  if(NHKyquyId.trim().length() > 0)
				    	  {
				    		  taikhoanNO_fk= rs.getString("taikhoan_NHKyquy")== null ? "":rs.getString("taikhoan_NHKyquy") ;
//				    		  madoituongno = nhkyquyId;	
				    		  
				    		  laytk +=
									"	SELECT N'NỢ' NO_CO, "+id+" ID, (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = '"+taikhoanNO_fk+"') SOHIEUTAIKHOAN, \n"+
									"       "+(-1)*sotien+" SOTIEN, (SELECT MA +' - '+ TEN from ERP_DOITUONGKYQUY where PK_SEQ = dc.NHKYQUY_FK) DOITUONG	, " +
									"		'' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP \n"+
									"   FROM ERP_DIEUCHUYENTIEN dc \n"+
									"   WHERE dc.PK_SEQ = '"+id+"' \n"+
					
									"   UNION ALL \n"+
					
									"   SELECT N'CÓ' NO_CO,  "+id+" ID,(SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = '"+taikhoanCO_fk+"') SOHIEUTAIKHOAN, \n"+
									"   "+(-1)*sotien+" SOTIEN, " +
									"   ( SELECT (MA + ' - ' + TEN) FROM ERP_NGANHANG  where PK_SEQ in ( SELECT NganHang_FK from ERP_NGANHANG_CONGTY where PK_SEQ = dc.NGANHANGCHUYEN_FK)) \n"+
									"	DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 2 SAPXEP \n"+
									" 	FROM ERP_DIEUCHUYENTIEN dc \n"+
									"   WHERE dc.PK_SEQ = '"+id+"' \n";	
				    		  System.out.println(laytk);
				    	  }
				    	  else
				    	  {
				    		  taikhoanNO_fk= rs.getString("taikhoan_NHNhan")== null ? "":rs.getString("taikhoan_NHNhan") ;
				    		  
				    		  if(laytk.trim().length()>0) laytk += " UNION ALL \n";		
				    		  
				    		  laytk +=
									"	SELECT N'NỢ' NO_CO,   "+id+" ID, (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = '"+taikhoanNO_fk+"') SOHIEUTAIKHOAN, \n"+
									"       "+(-1)*sotien+" SOTIEN, ( SELECT (MA + ' - ' + TEN) FROM ERP_NGANHANG  where PK_SEQ in ( SELECT NganHang_FK from ERP_NGANHANG_CONGTY where PK_SEQ = dc.NGANHANGNHAN_FK)) DOITUONG, " +
									"		'' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP \n"+
									"   FROM ERP_DIEUCHUYENTIEN dc \n"+
									"   WHERE dc.PK_SEQ = '"+id+"' \n"+
					
									"   UNION ALL \n"+
					
									"   SELECT N'CÓ' NO_CO,  "+id+" ID, (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = '"+taikhoanCO_fk+"') SOHIEUTAIKHOAN, \n"+
									"   "+(-1)*sotien+" SOTIEN, " +
									"   ( SELECT (MA + ' - ' + TEN) FROM ERP_NGANHANG  where PK_SEQ in ( SELECT NganHang_FK from ERP_NGANHANG_CONGTY where PK_SEQ = dc.NGANHANGCHUYEN_FK)) \n"+
									"	DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 2 SAPXEP \n"+
									" 	FROM ERP_DIEUCHUYENTIEN dc \n"+
									"   WHERE dc.PK_SEQ = '"+id+"' \n";	
				    	  }
				    	  
			    		 
				      }
				      
				      double phi= rs.getDouble("PHIVND");
				      if(phi > 0)
				      {
				    	  taikhoanNO_fk= rs.getString("taikhoanNO_PHI");
				    	  taikhoanCO_fk = rs.getString("taikhoanCO_NHTP");
				    	  
				    	  if(laytk.trim().length()>0) laytk += " UNION ALL \n";		
				    	  
				    	  laytk +=
								"	SELECT N'NỢ' NO_CO,   "+id+" ID, '64250000' SOHIEUTAIKHOAN, \n"+
								"   "+(-1)*phi+" SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 2 STT, 1 SAPXEP \n"+
								"   FROM ERP_DIEUCHUYENTIEN dc \n"+
								"   WHERE dc.PK_SEQ = '"+id+"' \n"+
				
								"   UNION ALL \n"+
				
								"   SELECT N'CÓ' NO_CO,  "+id+" ID, (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = '"+taikhoanCO_fk+"') SOHIEUTAIKHOAN, \n"+
								"   "+(-1)*phi+" SOTIEN, " +
								"   ( SELECT (MA + ' - ' + TEN) FROM ERP_NGANHANG  where PK_SEQ in ( SELECT NganHang_FK from ERP_NGANHANG_CONGTY where PK_SEQ = dc.NHTRICHPHI_FK)) \n"+
								"	DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 2 STT, 2 SAPXEP \n"+
								" 	FROM ERP_DIEUCHUYENTIEN dc \n"+
								"   WHERE dc.PK_SEQ = '"+id+"' \n";	
				      }
				      
				      //GHI NHAN VAT
				      double vat= rs.getDouble("VATVND");
				      if(vat > 0)
				      {
				    	  taikhoanNO_fk= rs.getString("taikhoanNO_VAT");
				    	  taikhoanCO_fk = rs.getString("taikhoanCO_NHTP");
				    	  
				    	  if(laytk.trim().length()>0) laytk += " UNION ALL \n";		
				    	  
				    	  laytk +=
								"	SELECT N'NỢ' NO_CO,   "+id+" ID, '13311000' SOHIEUTAIKHOAN, \n"+
								"   "+(-1)*vat+" SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 3 STT, 1 SAPXEP \n"+
								"   FROM ERP_DIEUCHUYENTIEN dc \n"+
								"   WHERE dc.PK_SEQ = '"+id+"' \n"+
				
								"   UNION ALL \n"+
				
								"   SELECT N'CÓ' NO_CO,  "+id+" ID, (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = '"+taikhoanCO_fk+"') SOHIEUTAIKHOAN, \n"+
								"   "+(-1)*vat+" SOTIEN, " +
								"   ( SELECT (MA + ' - ' + TEN) FROM ERP_NGANHANG  where PK_SEQ in ( SELECT NganHang_FK from ERP_NGANHANG_CONGTY where PK_SEQ = dc.NHTRICHPHI_FK)) \n"+
								"	DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 3 STT, 2 SAPXEP \n"+
								" 	FROM ERP_DIEUCHUYENTIEN dc \n"+
								"   WHERE dc.PK_SEQ = '"+id+"' \n";	
				      }
				}
				rs.close();
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		if(laytk.trim().length()>0) laytk += " ORDER BY ID, STT, SAPXEP \n";
		}
		/// KẾT THÚC ĐIỀU CHUYỂN TIỀN
		if(laytk.trim().length()<=0){
			laytk += " SELECT '' NO_CO, '' ID, '' SOHIEUTAIKHOAN, '' SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP " +
					 " FROM ERP_DIEUCHUYENTIEN " +
					 " WHERE PK_SEQ ='"+id+"'";
		}
		
		return laytk;
		
	}

	public void init(String search)
	{
		String query = "";
		
		
		if(search.length() > 0)
			query = search;
		else
			query = "	SELECT a.PK_SEQ as SOPHIEU,dc.sochungtu as SOCHUNGTU,a.SOCHUNGTU as SOPHIEUDC, case a.LOAICHUNGTU when 1 then N'Điều chuyển' else N'Giải ngân' end as LOAICHUNGTU ,a.TRANGTHAI, a.NGAYTAO, a.NGAYSUA, b.TEN as NGUOITAO, c.TEN as NGUOISUA, a.LOAICHUNGTU LOAI \n " +
			   		"	FROM ERP_HUYCHUNGTUDIEUCHUYEN a inner join NHANVIEN b on a.nguoitao = b.pk_seq inner join NHANVIEN c on a.nguoisua = c.pk_seq \n " +
			   		"   inner join ERP_DIEUCHUYENTIEN dc on dc.pk_seq= a.sochungtu" +
			   		"	WHERE a.congty_fk = '" + congtyId + "' and a.LOAICHUNGTU ='1' ";
		
		String query_init = createSplittingData_ListNew(this.db, 50, 10, "SOPHIEU desc ", query);
		
		System.out.println(" INIT___ : "+ query);
		
		ResultSet rs = null;
		if (query != null && query.trim().length() > 0)
			rs = db.get(query_init);
		
		List<IThongTinHienThi> htList = new ArrayList<IThongTinHienThi>();
		
		try
		{
			if(rs!= null)
			{
				IThongTinHienThi ht = null;
				while(rs.next())
				{					
					//LAY DINH KHOAN KE TOAN
					String dk = LayDuLieu(rs.getString("SOPHIEUDC"), rs.getString("LOAI"));
					System.out.println("Dinh khoan "+dk);
					ResultSet rsKT = db.get(dk);
					List<IDinhKhoanKeToan> ktList = new ArrayList<IDinhKhoanKeToan>();
					if(rsKT!= null)
					{
						IDinhKhoanKeToan kt = null;
						while(rsKT.next())
						{
							kt = new DinhKhoanKeToan(rsKT.getString("ID"), rsKT.getString("NO_CO"),rsKT.getString("SOHIEUTAIKHOAN"),rsKT.getString("SOTIEN"),rsKT.getString("DOITUONG")== null ? "": rsKT.getString("DOITUONG"),
									 rsKT.getString("TRUNGTAMCHIPHI"),rsKT.getString("TRUNGTAMDOANHTHU"), "");
							ktList.add(kt);
						}
						rsKT.close();
					}
												
					// INIT
					
						ht = new ThongTinHienThi();		
						
						ht.setId(rs.getString("SOPHIEU"));
						ht.setSOCHUNGTU(rs.getString("SOCHUNGTU"));
						ht.setloaichungtu(rs.getString("LOAI"));
						ht.setTRANGTHAI(rs.getString("trangthai"));
						ht.setNgaytao(rs.getString("NGAYTAO"));
						ht.setNgaysua(rs.getString("NGAYSUA"));
						ht.setNguoitao(rs.getString("NGUOITAO"));
						ht.setNgaysua(rs.getString("NGAYSUA"));
						ht.setNguoisua(rs.getString("NGUOISUA"));						
						
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
		System.out.println("Size:"+this.hienthiList.size());
	}
	
	public void DBclose() 
	{
		try 
		{
			if (this.hienthiList != null)
				this.hienthiList.clear(); 
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
		this.soCT= sochungtu;
		
	}

	
	public List<IThongTinHienThi> getHienthiList() {
		
		return this.hienthiList;
	}

	
	public void setHienthiList(List<IThongTinHienThi> hienthiList) {
		
		this.hienthiList = hienthiList;
	}


	
	
}
