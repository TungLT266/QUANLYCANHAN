package geso.traphaco.erp.beans.parktrongnuoc.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.DinhKhoanKeToan;
import geso.traphaco.center.util.IDinhKhoanKeToan;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.IThongTinHienThi;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.ThongTinHienThi;
import geso.traphaco.erp.beans.parktrongnuoc.IErpHoadon;
import geso.traphaco.erp.beans.parktrongnuoc.IErpParkTrongNuocList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ErpParkTrongNuocList extends Phan_Trang implements IErpParkTrongNuocList
{

	private static final long serialVersionUID = 1L;
	String ID,NCC, NCCID,LOAIHANG,NGAYGHINHAN,USERID,TRANGTHAI,MSG ,NGUOITAO , SOHOADON, sonhanhang, congtyId, nppdangnhap;
	int DUYET;
	List<IErpHoadon> hdList;
	List<IThongTinHienThi> hienthiList;
	
	ResultSet nccList;
	ResultSet Loaihanglist;
	ResultSet ParkList;
	private int num;
	private int[] listPages;
	private int currentPages;
	dbutils db;
	public ErpParkTrongNuocList()
	{
		this.db=new dbutils();
		this.ID="";
		this.NCC="";
		this.NCCID = "";
		this.LOAIHANG="";
		this.NGAYGHINHAN="";
		this.USERID="";
		this.TRANGTHAI="";
		this.MSG="";
		this.DUYET=0;
		this.SOHOADON="";
		this.sonhanhang="";
		this.NGUOITAO="";
		this.congtyId = "";
		this.nppdangnhap = "";
		this.hdList = new ArrayList<IErpHoadon>();
		
		this.hienthiList = new ArrayList<IThongTinHienThi>();
	}
	
	
	
	public String getNGUOITAO() {
		return NGUOITAO;
	}



	public void setNGUOITAO(String nGUOITAO) {
		NGUOITAO = nGUOITAO;
	}



	public String getSOHOADON() {
		return SOHOADON;
	}



	public void setSOHOADON(String sOHOADON) {
		SOHOADON = sOHOADON;
	}

	public String getSonhanhang() 
	{
		return sonhanhang;
	}

	public void setSonhanhang(String sonhanhang) 
	{
		this.sonhanhang = sonhanhang;
	}

	public String getUserId() 
	{
		return this.USERID;
	}

	
	public void setUserId(String userId) 
	{
		this.USERID=userId;
		
	}

	
	public String getId() 
	{
		
		return this.ID;
	}

	
	public void setId(String id) 
	{
		this.ID=id;
		
	}

	
	public String getNgayghinhan() 
	{
		
		return this.NGAYGHINHAN;
	}

	
	public void setNgayghinhan(String ngayghinhan) 
	{
		this.NGAYGHINHAN=ngayghinhan;
		
	}

	
	public void setNcc(String ncc) 
	{
		
		this.NCC=ncc;
	}

	
	public String getNcc()
	{
		
		return this.NCC;
	}

	
	public void setLoaihang(String loaihang) 
	{
		
		this.LOAIHANG=loaihang;
	}

	
	public String getLoaihang() 
	{
		
		return this.LOAIHANG;
	}

	
	public String getNccId() 
	{	
		return this.NCCID;
	}

	
	public void setNccId(String NCCID) 
	{
		
		this.NCCID = NCCID;
	}

	
	public ResultSet getLoaihangList()
	{
		
		return this.Loaihanglist;
	}

	
	public String getMsg() 
	{
		
		return this.MSG;
	}

	
	public void setMsg(String msg) 
	{
		this.MSG=msg;
		
	}

	
	public void setLoaihangList(ResultSet loaihanglist) 
	{
		this.Loaihanglist=loaihanglist;
		
	}

	
	public List<IErpHoadon> getHdList() 
	{
		
		return this.hdList;
	}

	
	public void setHdList(List<IErpHoadon> hdList) 
	{
		
		this.hdList=hdList;
	}

	
	public String getTrangthai() 
	{
		
		return this.TRANGTHAI;
	}

	
	public void setTrangthai(String trangthai) 
	{
		this.TRANGTHAI=trangthai;
		
	}

	public int getNum(){
		return this.num;
	}
	public void setNum(int num){
		this.num = num;
		listPages = PhanTrang.getListPages(num);

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
		this.listPages = listPages;
	}

	
	public int getLastPage() {
		ResultSet rs = db.get("select count(*) as c from ERP_PARK where");
		return PhanTrang.getLastPage(rs);
	}

	
	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage) {
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}

	
	public void createRs() 
	{		
		this.Loaihanglist= this.db.get("select * from ERP_LOAISANPHAM" );
		
		this.nccList = this.db.get(" SELECT * FROM ERP_NHACUNGCAP WHERE TRANGTHAI = 1 and duyet = '1' AND CONGTY_FK = "+this.congtyId);
	}

	public ResultSet getParkList() 
	{
		return this.ParkList;
	}

	
	public void setParkList(ResultSet parklist) 
	{
		this.ParkList=parklist;
		
	}

	private String LayDuLieu(String id) {
		
		String query = "";
		
		String laytk = "";
		
		try 
		{
			
			query = " select  a.thuesuat,a.pk_seq, a.sohoadon, b.ngayghinhan, b.ncc_fk,  \n" +
			"    		d.SANPHAM_FK, d.TAISAN_FK, d.CCDC_FK, d.SOLUONG, d.DONGIA , d.CHIPHI_FK,   \n" +
			" 			isnull(a.sotienchietkhau, 0) as tienCK_HoaDon,  \n" +
			" 			a.VAT as VAT_HOADON, a.park_fk,  isnull(b.tinhthuenhapkhau, 0) as tinhthueNK,   \n" +
			" 			0 as loaihanghoa_fk, c.taikhoan_fk as taikhoanNCC ,  \n" +
			" 			ISNULL(b.tiente_fk, '100000') as tiente_fk,  \n"+
			" 			ISNULL(b.tigia,1 ) as tigia, \n" +
			"			(SELECT distinct B.TAIKHOANKT_FK FROM SANPHAM A INNER JOIN ERP_LOAISANPHAM B ON A.LOAISANPHAM_FK = B.PK_SEQ WHERE A.PK_SEQ = d.SANPHAM_FK ) TAIKHOANNO_SANPHAM, \n"+ 
			"			(select distinct B.taikhoan_fk from ERP_TAISANCODINH A INNER JOIN Erp_LOAITAISAN B ON A.LOAITAISAN_FK = B.pk_seq WHERE A.PK_SEQ = d.TAISAN_FK AND B.CONGTY_FK ="+this.congtyId+" ) TAIKHOANNO_TAISAN, \n"+ 
			"			( select distinct B.taikhoan_fk from ERP_CONGCUDUNGCU A  INNER JOIN Erp_LOAICCDC B ON A.LOAICCDC_FK = B.pk_seq WHERE A.PK_SEQ = d.CCDC_FK AND B.CONGTY_FK = "+this.congtyId+"  ) TAIKHOANNO_CCDK, \n"+
			"			(  select distinct TAIKHOAN_FK from ERP_NHOMCHIPHI WHERE PK_SEQ =  d.CHIPHI_FK AND CONGTY_FK = "+this.congtyId+" ) TAIKHOANNO_CHIPHI, \n"+
			"			( select  PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '13310000' AND CONGTY_FK = "+this.congtyId+") TAIKHOANNO_VAT, \n"+
			"			( select PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '13320000' AND CONGTY_FK = "+this.congtyId+") TAIKHOANNO_VAT_TSCD, \n"+
			"			( select MA from SANPHAM WHERE PK_SEQ = d.SANPHAM_FK ) TENSP, (select MA FROM ERP_TAISANCODINH WHERE PK_SEQ = d.TAISAN_FK) TENTS, \n"+
			"			( select MA from ERP_CONGCUDUNGCU WHERE PK_SEQ = d.CCDC_FK ) TENCCDC, (select TEN FROM ERP_NHOMCHIPHI WHERE PK_SEQ = d.CHIPHI_FK ) TENCHIPHI, \n"+
			"			( select MA from ERP_NHACUNGCAP WHERE PK_SEQ = b.ncc_fk ) TENNCC \n"+
			" 	from 	ERP_HOADONNCC a inner join ERP_PARK b on a.park_fk = b.pk_seq   \n" +
			" 			inner join ERP_NHACUNGCAP c on b.ncc_fk = c.pk_seq   \n"
			+ " 		inner join ERP_HOADONNCC_DONMUAHANG d on d.HOADONNCC_FK = a.PK_SEQ \n" +
			" where b.pk_seq = '" + id + "'  ";
			
			//System.out.println(query);
			
			ResultSet psktRs = db.get(query);
			
			String TAIKHOANNO_TIENHANG = "";
	        String TAIKHOANCO_TIENHANG = "";
	        
	        String TAIKHOANNO_VAT = "";
	        String TAIKHOANCO_VAT = "";
		 
			if(psktRs != null)
	        {
				int  i = 0;
				while(psktRs.next())
	              {
//					String hoadonncc_fk = psktRs.getString("pk_seq");
                    
//                    String namNV = psktRs.getString("ngayghinhan").substring(0, 4);
//        			String thangNV = psktRs.getString("ngayghinhan").substring(5, 7);
        			
        			String ncc_fk = psktRs.getString("ncc_fk");
        			String taikhoanncc_fk = psktRs.getString("taikhoanNCC");
                    
//        			String tiente_fk = psktRs.getString("tiente_fk");
//        			double tygia = (psktRs.getDouble("tigia"));
//        			if(tiente_fk.equals("100000"))
//        				tygia = 1;
	        			
        			String sanphamId= "";
        			String taisanId = "";
        			String ccdcId = "";
        			String chiphiId = "";
        			
        			double soluong = 0;
        			double dongia =  0;       			
        		    double sotienBVAT = 0;
        		    double thuesuat = 0;
        		    
    				sanphamId = psktRs.getString("sanpham_fk") == null ? "":psktRs.getString("sanpham_fk")  ;
    				taisanId = psktRs.getString("taisan_fk") == null ? "":psktRs.getString("taisan_fk");
    				ccdcId = psktRs.getString("ccdc_fk") == null ? "":psktRs.getString("ccdc_fk");
    				chiphiId = psktRs.getString("CHIPHI_FK") == null ? "":psktRs.getString("CHIPHI_FK");
    				
        			soluong = psktRs.getDouble("SOLUONG");
        			dongia =  psktRs.getDouble("DONGIA");        			
        		    sotienBVAT = Math.round(soluong*dongia);
        		    thuesuat = psktRs.getDouble("thuesuat");   
        			
        		    double VAT = soluong*dongia*thuesuat/100 ;        
                    
                    String doituongno = "";
                    String madoituongno = "";
                    String doituongco = "";
                    String madoituongco ="";
                    
                    doituongco = "Nhà cung cấp";
            		madoituongco = ncc_fk; 
            		
            		String NO_TENDOITUONG = "";
            		String CO_TENDOITUONG = "";
            		
            		if(sanphamId.trim().length()>0)
                	{
                		doituongno = "Sản phẩm";
                		madoituongno = sanphamId;
                		
                		TAIKHOANNO_TIENHANG = psktRs.getString("TAIKHOANNO_SANPHAM");
                		TAIKHOANCO_TIENHANG = taikhoanncc_fk;
                		
                		TAIKHOANNO_VAT = psktRs.getString("TAIKHOANNO_VAT");
                		TAIKHOANCO_VAT = taikhoanncc_fk;
                		
                		NO_TENDOITUONG = psktRs.getString("TENSP");
                		CO_TENDOITUONG = psktRs.getString("TENNCC");
                	}
                	
                	if(ccdcId.trim().length()>0)
                	{
                		doituongno = "Công cụ dụng cụ";
                		madoituongno = ccdcId;
                		
                		TAIKHOANNO_TIENHANG = psktRs.getString("TAIKHOANNO_CCDK");
                		TAIKHOANCO_TIENHANG = taikhoanncc_fk;
                		
                		TAIKHOANNO_VAT = psktRs.getString("TAIKHOANNO_VAT");
                		TAIKHOANCO_VAT = taikhoanncc_fk;
                		
                		NO_TENDOITUONG = psktRs.getString("TENCCDC");
                		CO_TENDOITUONG = psktRs.getString("TENNCC");
                	}
                	
                	if(taisanId.trim().length()>0)
                	{
                		doituongno = "Tài sản";
                		madoituongno = taisanId;
                		
                		TAIKHOANNO_TIENHANG = psktRs.getString("TAIKHOANNO_TAISAN");
                		TAIKHOANCO_TIENHANG = taikhoanncc_fk;
                		
                		TAIKHOANNO_VAT = psktRs.getString("TAIKHOANNO_VAT_TSCD");
                		TAIKHOANCO_VAT = taikhoanncc_fk;
                		
                		NO_TENDOITUONG = psktRs.getString("TENTS");
                		CO_TENDOITUONG = psktRs.getString("TENNCC");
                	}
                	
                	if(chiphiId.trim().length()>0)
                	{
                		doituongno = "Chi phí";
                		madoituongno = chiphiId;
                		
                		TAIKHOANNO_TIENHANG = psktRs.getString("TAIKHOANNO_CHIPHI");
                		TAIKHOANCO_TIENHANG = taikhoanncc_fk;
                		
                		TAIKHOANNO_VAT = psktRs.getString("TAIKHOANNO_VAT");
                		TAIKHOANCO_VAT = taikhoanncc_fk;
                		
                		NO_TENDOITUONG = psktRs.getString("TENCHIPHI");
                		CO_TENDOITUONG = psktRs.getString("TENNCC");
                	}
                	
                	if(sotienBVAT>0)
                	{
                		if(laytk.trim().length()>0) laytk+= " UNION ALL ";
                		laytk +=
                   		 " SELECT distinct N'NỢ' NO_CO, '" + id + "' PK_SEQ, '"+TAIKHOANNO_TIENHANG+"' SOHIEUTAIKHOAN, "+sotienBVAT+" SOTIEN, '"+NO_TENDOITUONG+"' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP \n"+
                   		 " from ERP_PARK a  \n"+
                   		 " WHERE a.PK_SEQ = '"+id+"'" +
                   		 
                   		 " UNION ALL \n"+
                   		 
                   		 " SELECT distinct N'CÓ' NO_CO, '" + id + "' PK_SEQ, (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = '"+TAIKHOANCO_TIENHANG+"' ) SOHIEUTAIKHOAN, "+sotienBVAT+" SOTIEN, '"+CO_TENDOITUONG+"' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP \n"+
                   		 " from ERP_PARK a \n"+
                   		 " WHERE a.PK_SEQ = '"+id+"'" ;
                		i++;
                	}
                	
                	if(VAT>0)
                	{
                		
                		if(laytk.trim().length()>0) laytk+= " UNION ALL ";
                		laytk +=
                   		 " SELECT distinct N'NỢ' NO_CO, '" + id + "' PK_SEQ, (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ ='"+TAIKHOANNO_VAT+"' ) SOHIEUTAIKHOAN, "+VAT+" SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 1 SAPXEP \n"+
                   		 " from ERP_PARK a  \n"+
                   		 " WHERE a.PK_SEQ = '"+id+"'" +
                   		 
                   		 " UNION ALL \n"+
                   		 
                   		 " SELECT distinct N'CÓ' NO_CO, '" + id + "' PK_SEQ, (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = '"+TAIKHOANCO_VAT+"' ) SOHIEUTAIKHOAN, "+VAT+" SOTIEN, '"+CO_TENDOITUONG+"' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+i+" STT, 2 SAPXEP \n"+
                   		 " from ERP_PARK a \n"+
                   		 " WHERE a.PK_SEQ = '"+id+"'" ;
                		i++;
                	}
	            }
	        }
			
			if(laytk.trim().length()> 0) laytk += " ORDER BY PK_SEQ, STT, SAPXEP \n";
			
	
			if(laytk.trim().length()<=0)
				{laytk +=
					 " SELECT N'' NO_CO, '' PK_SEQ, '' SOHIEUTAIKHOAN, '' SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 2 STT, 1 SAPXEP \n"+
					 " FROM ERP_HOADONNCC a inner join ERP_PARK b on a.park_fk = b.pk_seq  " +
					 "	WHERE b.PK_SEQ = '"+id+"'" ;
				}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	
		//System.out.println(laytk);
		
		return laytk;
	}
		
	private void getNppInfo()
	{		
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppdangnhap=util.getIdNhapp(this.USERID);
	}

	
	public void init(String query) 
	{
		String sql = "";
		this.getNppInfo();
		
		if(query.length() <= 0)
		{
			
			sql =   " SELECT A.PK_SEQ , A.NGAYGHINHAN, NCC.TEN AS TENNHACUNGCAP, A.TRANGTHAI ,     	NV.TEN AS TENNV,A.NGAYSUA,A.NGAYTAO,NV.PK_SEQ AS MANV,NV2.TEN AS \n" +  
					"  		 TENNVS,NV2.PK_SEQ AS MANVS, A.PREFIX     	, HOANTAT.SODEM  AS DAHOANTAT , CASE WHEN THANHTOAN.PARK_FK IS NULL THEN 0 ELSE 1 END AS DACOTHANHTOAN, ISNULL(HDNCC.SOHOADON,'') AS SOHOADON, NCC.PK_SEQ NCCID,	 \n" +
					"        HDNCC.PK_SEQ HDNCCID, \n"+
					"		 (SELECT COUNT(PK_SEQ) FROM ERP_NHANHANG WHERE HDNCC_FK = HDNCC.PK_SEQ  AND TRANGTHAI NOT IN (3) ) DANHANHANG, A.LOAIHD, "+
					" ( SELECT CASE WHEN HDMH1.SANPHAM_FK IS NOT NULL THEN 0 WHEN HDMH1.TAISAN_FK IS NOT NULL THEN 1 WHEN HDMH1.CCDC_FK IS NOT NULL THEN 3 ELSE 2 END LOAIHH \n"+
					" FROM \n"+
					" (SELECT TOP (1) * " +
					"  FROM ERP_HOADONNCC_DONMUAHANG HDMH " +
					"  WHERE HDMH.HOADONNCC_FK = HDNCC.PK_SEQ ) HDMH1 ) LOAIHH \n"+
					
					" FROM 	 ERP_PARK A   	INNER JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = A.NCC_FK   	\n" +
					"		 INNER JOIN NHANVIEN NV ON A.NGUOITAO = NV.PK_SEQ   \n" +  
					"  		 INNER JOIN NHANVIEN NV2 ON A.NGUOISUA = NV2.PK_SEQ	     \n" +  
					"  		 LEFT JOIN 	(   \n" +  
					"   				 SELECT A.PARK_FK , (DEMA - ISNULL (DEMB,0)) AS SODEM \n" +
					"					 FROM 	(SELECT PARK_FK , COUNT(PARK_FK) AS DEMA  	\n" +
					"							 FROM ERP_HOADONNCC   \n" +
					"							 WHERE CONGTY_FK = "+this.congtyId +
					"   				 		 GROUP BY PARK_FK) AS A  	\n" +
					"							 LEFT JOIN   	\n" +
					"							(SELECT PARK_FK , COUNT(PARK_FK) AS DEMB  	\n" +
					"							 FROM ERP_HOADONNCC  	 \n" +  
					"   				 		 WHERE TRANGTHAI = 2 AND CONGTY_FK = "+this.congtyId+
					"							 GROUP BY PARK_FK) AS B ON B.PARK_FK = A.PARK_FK  \n" +  
					"  					) HOANTAT ON HOANTAT.PARK_FK = A.PK_SEQ   	 \n" +  
					"  		 LEFT JOIN 	(  \n" +  
					"   				SELECT 	DISTINCT HD.PARK_FK 		\n"+
					"   				FROM 	ERP_THANHTOANHOADON_HOADON TT		\n"+   
					"   					 	INNER JOIN ERP_HOADONNCC HD ON HD.PK_SEQ = TT.HOADON_FK AND TT.LOAIHD = 0 \n"+
					"   				WHERE 	TT.THANHTOANHD_FK IN (SELECT PK_SEQ FROM ERP_THANHTOANHOADON WHERE TRANGTHAI != 2 AND CONGTY_FK = "+this.congtyId+" and NPP_FK = "+this.nppdangnhap+" )  \n"+
					"							AND HD.CONGTY_FK = "+this.congtyId +
					"  					) THANHTOAN ON THANHTOAN.PARK_FK = A.PK_SEQ " +
					" INNER JOIN ERP_HOADONNCC HDNCC ON A.PK_SEQ=HDNCC.PARK_FK ";
			 
			if(this.DUYET==1)
			{
				sql+=" and a.trangthai <> '0' ";
			}
			
			sql+= " WHERE HDNCC.CONGTY_FK ="+this.congtyId+"  AND A.LOAIHD IN (1) ";
		}
		else
			sql = query;		
		
		System.out.println(sql);
		String query_init = createSplittingData_ListNew(this.db, 30, 10, "NGAYGHINHAN desc, PK_SEQ  desc", sql);
		
		//CHỈNH SỬA
		
		ResultSet rs = db.get(query_init);
		List<IThongTinHienThi> htList = new ArrayList<IThongTinHienThi>();
		
		try
		{
			if(rs!= null)
			{
				IThongTinHienThi ht = null;
				while(rs.next())
				{
					
					//LAY DINH KHOAN KE TOAN
					String dk = LayDuLieu(rs.getString("PK_SEQ"));
					
					ResultSet rsKT = db.get(dk);
					List<IDinhKhoanKeToan> ktList = new ArrayList<IDinhKhoanKeToan>();
						if(rsKT!= null)
						{
							IDinhKhoanKeToan kt = null;
							while(rsKT.next())
							{
								kt = new DinhKhoanKeToan(rsKT.getString("PK_SEQ"), rsKT.getString("NO_CO"),rsKT.getString("SOHIEUTAIKHOAN"),rsKT.getString("SOTIEN"),rsKT.getString("DOITUONG"),
										 rsKT.getString("TRUNGTAMCHIPHI"),rsKT.getString("TRUNGTAMDOANHTHU"), "");
								ktList.add(kt);
							}
							rsKT.close();
						}
												
					// INIT
					
						ht = new ThongTinHienThi();
						ht.setId(rs.getString("PK_SEQ"));
						ht.setPREFIX(rs.getString("PREFIX"));
						ht.setSohoadon(rs.getString("SOHOADON"));
						ht.setNgayghinhan(rs.getString("NGAYGHINHAN"));
						ht.setTenNCC(rs.getString("TENNHACUNGCAP"));
						ht.setTrangthai(rs.getString("trangthai"));
						ht.setDahoantat(rs.getString("dahoantat"));
						ht.setDathanhtoan(rs.getString("dacothanhtoan"));
						ht.setNgaytao(rs.getString("NGAYTAO"));
						ht.setNgaysua(rs.getString("NGAYSUA"));
						ht.setNguoitao(rs.getString("TENNV"));
						ht.setNguoisua(rs.getString("TENNVS"));
						ht.setNCCId(rs.getString("nccId"));
						ht.setHoadonId(rs.getString("HDNCCID"));
						ht.setDaNhanHang(rs.getString("DANHANHANG"));
						ht.setLoaiHd( rs.getString("LOAIHD"));
						
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

	
	public void close() 
	{
		try 
		{
			if(this.Loaihanglist != null)
				this.Loaihanglist.close();
			if(this.ParkList!= null)
				this.ParkList.close();
		}
		catch (SQLException e){
			e.printStackTrace();
		}
		this.db.shutDown();
	}
	public int getDuyet() 
	{
		return this.DUYET;
	}
	public void setDuyet(int duyet) 
	{
		this.DUYET=duyet;
	}
	
	public void setNhacungcapList(ResultSet nhacungcapList) {
		this.nccList = nhacungcapList;
	}
	
	public ResultSet getNhacungcapList() {
		return this.nccList;
	}

	public List<IThongTinHienThi> getHienthiList() 
	{
		return this.hienthiList;
	}

	public void setHienthiList(List<IThongTinHienThi> hienthiList) 
	{
		this.hienthiList = hienthiList;
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

	
	public String Unchot(String parkId, String userId) {
		
		String msg = "";
		String query = "SELECT count(PK_SEQ) dem from ERP_NHANHANG " +
		   			   "WHERE hdNCC_fk IN (SELECT PK_SEQ FROM ERP_HOADONNCC WHERE park_fk = "+parkId+") AND TRANGTHAI NOT IN (3)";

		System.out.println(query);
		ResultSet ktra = db.get(query);
		
		int dem = 0;
		if(ktra!=null)
		{
			try {
				while (ktra.next())
				{
					dem = ktra.getInt("dem");
				}
				ktra.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}			
		}

		if(dem>0)
			msg = "Hóa đơn này đã có nhận hàng. Bạn không thể bỏ chốt!";
		else
			db.update("update ERP_PARK set trangthai = '0', nguoisua = "+userId+" where pk_seq = '" + parkId + "'");

		return msg;
	}

	
	public String checkSoHoaDon(String parkId) {
		
		String msg= "";
		
		String query = "\n SELECT count(pk_seq) dem FROM ERP_HOADONNCC " +
					   "\n WHERE SOHOADON IN (SELECT A.SOHOADON FROM ERP_HOADONNCC A INNER JOIN ERP_PARK B ON A.PARK_FK = B.PK_SEQ " +
					   "\n 					WHERE PARK_FK = "+parkId+" AND B.TRANGTHAI NOT IN (4) )";
		
		System.out.println(query);
		int dem = 0;
		ResultSet ktra = db.get(query);
		if(ktra!=null)
		{
			try {
				while (ktra.next())
				{
					dem = ktra.getInt("dem");
				}
				ktra.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}			
		}

		if(dem >= 2)
			msg = "Số hóa đơn bị trùng bạn vui lòng thay đổi số hóa đơn!";
		
		return msg;
	}


}
