package geso.traphaco.erp.beans.dieuchuyentien.imp;

import geso.traphaco.center.util.DinhKhoanKeToan;
import geso.traphaco.center.util.IDinhKhoanKeToan;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.IThongTinHienThi;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.ThongTinHienThi;
import geso.traphaco.erp.beans.dieuchuyentien.IErpDieuchuyentienList;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ErpDieuchuyentienList extends Phan_Trang  implements IErpDieuchuyentienList 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String congtyId;
	String userId;
	String sochungtu;
	String ngaychungtu;
	
	
	
	String ttId;
	ResultSet ttRs;
	
	String nhchuyenId;
	ResultSet nhchuyenRs;
	
	String nhnhanId;
	ResultSet nhnhanRs;
	
	String sotien;
	ResultSet dieutien;
	
	List<IThongTinHienThi> hienthiList;
	
	String trangthai;
	String msg;
	dbutils db;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	public ErpDieuchuyentienList()
	{
		this.congtyId = "";
		this.userId = "";
		this.sochungtu = "";
		this.ngaychungtu = "";
		this.ttId = "";
		this.nhchuyenId = "";
		this.nhnhanId = "";
		this.sotien = "";
		this.trangthai = "";
		this.msg = "";
		currentPages = 1;
		num = 1;
		this.hienthiList = new ArrayList<IThongTinHienThi>();
		
		this.db = new dbutils();
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
		ResultSet rs = db.get("select count(*) as c from ERP_DIEUCHUYENTIEN");
		return PhanTrang.getLastPage(rs);
	}

	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage)
	{
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}
	
	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}

	public String getSochungtu(){
		return this.sochungtu;
	}
	
	public void setSochungtu(String sochungtu){
		this.sochungtu = sochungtu;
	}

	public String getNgaychungtu(){
		return this.ngaychungtu;
	}
	
	public void setNgaychungtu(String ngaychungtu){
		this.ngaychungtu = ngaychungtu;
	}

	public String getTtId(){
		return this.ttId;
	}
	
	public void setTtId(String ttId){
		this.ttId = ttId;
	}

	public String getNhchuyenId(){
		return this.nhchuyenId;
	}
	
	public void setNhchuyenId(String nhchuyenId){
		this.nhchuyenId = nhchuyenId;
	}

	public String getNhnhanId(){
		return this.nhnhanId;
	}
	
	public void setNhnhanId(String nhnhanId){
		this.nhnhanId = nhnhanId;
	}

	public String getSotien(){
		return this.sotien;
	}
	
	public void setSotien(String sotien){
		this.sotien = sotien;
	}

	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	
	public ResultSet getTienteRs(){
		return this.ttRs;
	}

	public void setTienteRs(ResultSet ttRs){
		this.ttRs = ttRs;
	}

	public ResultSet getNHChuyenRs(){
		return this.nhchuyenRs;
	}

	public void setNHChuyenRs(ResultSet nhchuyenRs){
		this.nhchuyenRs = nhchuyenRs;
	}

	public ResultSet getNHNhanRs(){
		return this.nhnhanRs;
	}

	public void setNHNhanRs(ResultSet nhnhanRs){
		this.nhnhanRs = nhnhanRs;
	}

	public ResultSet getDieutienRs(){
		return this.dieutien;
	}

	public void setDieutienRs(ResultSet dieutien){
		this.dieutien = dieutien;
	}
	
	private String LayDuLieu(String id) {
		String query=
					" select dct.NGAYCHUNGTU, ISNULL(dct.LOAI,0) as loaidc, dct.SOTIENVND,dct.PHIVND, dct.VATVND, \n" +
					"  (select NGANHANG_FK from ERP_NGANHANG_CONGTY where PK_SEQ = dct.NGANHANGCHUYEN_FK) as NHCHUYENID, \n" +
					"  (select NGANHANG_FK from ERP_NGANHANG_CONGTY where PK_SEQ = dct.NGANHANGNHAN_FK)as NHNHANID,\n" +
					"  (select TEN from ERP_DOITUONGKYQUY where PK_SEQ = dct.NHKYQUY_FK)as NHKYQUYID,\n" +
					"  (select NGANHANG_FK from ERP_NGANHANG_CONGTY where PK_SEQ = dct.NHTRICHPHI_FK)as NHTRICHPHIID,\n" +
					"  (select TaiKhoan_FK from ERP_NGANHANG_CONGTY where PK_SEQ=dct.NGANHANGCHUYEN_FK) as taikhoan_NHChuyen, \n"+
					"  (select TaiKhoan_FK from ERP_NGANHANG_CONGTY where PK_SEQ=dct.NGANHANGNHAN_FK) as taikhoan_NHNhan, \n"+
					"  (select TaiKhoanKT_FK from ERP_DOITUONGKYQUY where PK_SEQ=dct.NHKYQUY_FK) as taikhoan_NHKyquy, \n"+
					"  (select TaiKhoan_FK from ERP_NGANHANG_CONGTY where PK_SEQ=dct.NHTRICHPHI_FK) as taikhoanCO_NHTP, \n"+
					"  (select PK_SEQ from ERP_TAIKHOANKT where SOHIEUTAIKHOAN= '64250000' and congTy_FK = " + this.congtyId + ") as taikhoanNO_PHI, \n"+
					"  (select PK_SEQ from ERP_TAIKHOANKT where SOHIEUTAIKHOAN= '13311000' and congTy_FK = " + this.congtyId + ") as taikhoanNO_VAT \n"+
					" from ERP_DIEUCHUYENTIEN dct \n"+
					" where PK_SEQ = "+ id +" \n";

		System.out.println("CAU QUERY KT \n" + query + "\n======================================");
		
		String laytk= "";
		
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
//					  
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
									"       "+sotien+" SOTIEN, (SELECT MA +' - '+ TEN from ERP_DOITUONGKYQUY where PK_SEQ = dc.NHKYQUY_FK) DOITUONG	, " +
									"		'' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP \n"+
									"   FROM ERP_DIEUCHUYENTIEN dc \n"+
									"   WHERE dc.PK_SEQ = '"+id+"' \n"+
					
									"   UNION ALL \n"+
					
									"   SELECT N'CÓ' NO_CO,  "+id+" ID,(SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = '"+taikhoanCO_fk+"') SOHIEUTAIKHOAN, \n"+
									"   "+sotien+" SOTIEN, " +
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
									"       "+sotien+" SOTIEN, ( SELECT (MA + ' - ' + TEN) FROM ERP_NGANHANG  where PK_SEQ in ( SELECT NganHang_FK from ERP_NGANHANG_CONGTY where PK_SEQ = dc.NGANHANGNHAN_FK)) DOITUONG, " +
									"		'' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP \n"+
									"   FROM ERP_DIEUCHUYENTIEN dc \n"+
									"   WHERE dc.PK_SEQ = '"+id+"' \n"+
					
									"   UNION ALL \n"+
					
									"   SELECT N'CÓ' NO_CO,  "+id+" ID, (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = '"+taikhoanCO_fk+"') SOHIEUTAIKHOAN, \n"+
									"   "+sotien+" SOTIEN, " +
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
								"   "+phi+" SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 2 STT, 1 SAPXEP \n"+
								"   FROM ERP_DIEUCHUYENTIEN dc \n"+
								"   WHERE dc.PK_SEQ = '"+id+"' \n"+
				
								"   UNION ALL \n"+
				
								"   SELECT N'CÓ' NO_CO,  "+id+" ID, (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = '"+taikhoanCO_fk+"') SOHIEUTAIKHOAN, \n"+
								"   "+phi+" SOTIEN, " +
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
								"   "+vat+" SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 3 STT, 1 SAPXEP \n"+
								"   FROM ERP_DIEUCHUYENTIEN dc \n"+
								"   WHERE dc.PK_SEQ = '"+id+"' \n"+
				
								"   UNION ALL \n"+
				
								"   SELECT N'CÓ' NO_CO,  "+id+" ID, (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = '"+taikhoanCO_fk+"') SOHIEUTAIKHOAN, \n"+
								"   "+vat+" SOTIEN, " +
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
		
		if(laytk.trim().length()<=0){
			laytk += " SELECT '' NO_CO, '' ID, '' SOHIEUTAIKHOAN, '' SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP " +
					 " FROM ERP_DIEUCHUYENTIEN " +
					 " WHERE PK_SEQ ='"+id+"'";
		}

		return laytk;
	}
	public void init(String str){
		
		String query; 
		if(str.length() == 0){
			query = 	"SELECT DC.PK_SEQ AS DCID, DC.SOCHUNGTU, DC.NGAYCHUNGTU, TT.PK_SEQ AS TTID, TT.MA AS TIENTE, " + 
						"NH1.MA + ' - ' + CN1.MA AS NHCHUYEN, " +
						" ISNULL((NH2.MA + ' - ' + CN2.MA),'') AS NHNHAN, " +
						"DC.SOTIENNT, DC.SOTIENVND, DC.TRANGTHAI, NV1.TEN AS NGUOISUA, DC.NGAYSUA " +

						"FROM ERP_DIEUCHUYENTIEN DC " +
						"INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = DC.TIENTE_FK " +
						"LEFT JOIN ERP_NGANHANG_CONGTY NH_CTY1 ON NH_CTY1.PK_SEQ = DC.NGANHANGCHUYEN_FK " +
						"LEFT JOIN ERP_NGANHANG NH1 ON NH1.PK_SEQ = NH_CTY1.NGANHANG_FK " +
						"LEFT JOIN ERP_CHINHANH CN1 ON CN1.PK_SEQ = NH_CTY1.CHINHANH_FK " +

						"LEFT JOIN ERP_NGANHANG_CONGTY NH_CTY2 ON NH_CTY2.PK_SEQ = DC.NGANHANGNHAN_FK " +
						"LEFT JOIN ERP_NGANHANG NH2 ON NH2.PK_SEQ = NH_CTY2.NGANHANG_FK " +
						"LEFT JOIN ERP_CHINHANH CN2 ON CN2.PK_SEQ = NH_CTY2.CHINHANH_FK " +
						"LEFT JOIN NHANVIEN NV1 ON NV1.PK_SEQ = DC.NGUOISUA " +
						"WHERE DC.CONGTY_FK = " + this.congtyId + "";
		
		}else{
			query = str;
		}
				
		String query_init = createSplittingData_ListNew(this.db, 30, 10, " NGAYCHUNGTU desc, TRANGTHAI asc, DCID desc ", query);
		
		System.out.println(" init "+query_init);
		
		ResultSet rs = null;
		if (query_init != null && query_init.trim().length() > 0 )
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
					String sql = LayDuLieu(rs.getString("DCID"));
					ResultSet rsKT = db.get(sql);
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
					ht.setId(rs.getString("DCID"));
					ht.setSochungtu(rs.getString("SOCHUNGTU"));
					ht.setNgaychungtu(rs.getString("NGAYCHUNGTU"));
					ht.setTTID(rs.getString("TTID"));
					ht.setTiente(rs.getString("TIENTE"));
					ht.setNHCHUYEN(rs.getString("NHCHUYEN"));
					ht.setNHNHAN(rs.getString("NHNHAN"));
					ht.setSOTIENVND(rs.getString("SOTIENVND"));
					ht.setSOTIENNT(rs.getString("SOTIENNT"));
					ht.setTrangthai(rs.getString("trangthai"));
					ht.setNgaysua(rs.getString("ngaysua"));
					ht.setNguoisua(rs.getString("nguoisua"));
					
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
		
		this.createRs();
	}
	
	public void createRs(){
		String query;
		
		query = "SELECT PK_SEQ AS TTID, MA + ' - ' + TEN AS TIENTE " + 
				"FROM ERP_TIENTE ";	
		System.out.println(query);
		
		this.ttRs = this.db.get(query);
		
		query = "SELECT NH_CTY.PK_SEQ AS NHCTYID, isnull(NH_CTY.sotaikhoan, '') + ' - ' + NH.MA + ' - ' + CN.TEN + ' [' + TT.MA + ']' AS NGANHANG " +
				"FROM ERP_NGANHANG_CONGTY NH_CTY " +
				"INNER JOIN ERP_NGANHANG NH ON NH.PK_SEQ = NH_CTY.NGANHANG_FK " +
				"INNER JOIN ERP_CHINHANH CN ON CN.PK_SEQ = NH_CTY.CHINHANH_FK " +
				"INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = NH_CTY.TIENTE_FK " +
				"WHERE NH_CTY.TRANGTHAI = 1 ";
		System.out.println(query);
		this.nhchuyenRs = this.db.get(query);
		
		query = "SELECT NH_CTY.PK_SEQ AS NHCTYID, isnull(NH_CTY.sotaikhoan, '') + ' - ' + NH.MA + ' - ' + CN.TEN + ' [' + TT.MA + ']' AS NGANHANG " +
				"FROM ERP_NGANHANG_CONGTY NH_CTY " +
				"INNER JOIN ERP_NGANHANG NH ON NH.PK_SEQ = NH_CTY.NGANHANG_FK " +
				"INNER JOIN ERP_CHINHANH CN ON CN.PK_SEQ = NH_CTY.CHINHANH_FK " +
				"INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = NH_CTY.TIENTE_FK " +
				"WHERE NH_CTY.TRANGTHAI = 1 " ;
		
		if(this.nhchuyenId.length() > 0){
			query += "AND NH_CTY.PK_SEQ <> " + this.nhchuyenId + "";
		}
		
		this.nhnhanRs = this.db.get(query);
			
	}
	
	public void DBclose() 
	{
		try{
			if (this.hienthiList != null) this.hienthiList.clear();
			if(ttRs != null) this.ttRs.close();
			if(nhchuyenRs != null) this.nhchuyenRs.close();
			if(nhnhanRs != null) this.nhnhanRs.close();
			if(dieutien != null) this.dieutien.close();			
		}catch(java.sql.SQLException e){
			e.printStackTrace();
		}
		finally
		{
			if(this.db!=null)
			this.db.shutDown();
		}
	}
	
	public List<IThongTinHienThi> getHienthiList() 
	{
		return this.hienthiList;
	}

	public void setHienthiList(List<IThongTinHienThi> hienthiList) 
	{
		this.hienthiList = hienthiList;
	}
}