package geso.traphaco.erp.beans.park.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Erp_Item;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.IThongTinHienThi;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.park.IErpHoadon;
import geso.traphaco.erp.beans.park.IErpParkList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ErpParkList extends Phan_Trang implements IErpParkList
{

	private static final long serialVersionUID = 1L;
	String ID,NCC, NCCID,LOAIHANG,ngayghinhan,USERID,TRANGTHAI,MSG ,NGUOITAO , SOHOADON, sonhanhang, congtyId, nppdangnhap, loaihd,ngayhoadon;
	String denNgay;
	int DUYET;
	List<IErpHoadon> hdList;
	List<IThongTinHienThi> hienthiList;
	
	ResultSet nccList;
	ResultSet Loaihanglist;
	ResultSet ParkList;
	
	private String donViThucHienId;
	private List<Erp_Item> donViThucHienList;
	
	private String nguoiTaoId;
	private List<Erp_Item> nguoiTaoList;
	
	private String loaiNhapMuaId;
	private List<Erp_Item> loaiNhapMuaList;
	

	private int num;
	private int[] listPages;
	private int currentPages;
	dbutils db;
	ResultSet sanphamRs;
	String sanphamId;
	private Utility util;
	public ErpParkList()
	{
		this.db=new dbutils();
		this.ID="";
		this.NCC="";
		this.NCCID = "";
		this.LOAIHANG="";
		this.ngayghinhan="";
		this.denNgay = "";
		this.USERID="";
		this.TRANGTHAI="";
		this.MSG="";
		this.DUYET=0;
		this.SOHOADON="";
		this.sonhanhang="";
		this.NGUOITAO="";
		this.congtyId = "";
		this.nppdangnhap = "";
		this.loaihd = "";
		this.hdList = new ArrayList<IErpHoadon>();
		
		this.donViThucHienId = "";
		this.hienthiList = new ArrayList<IThongTinHienThi>();
		this.donViThucHienList = new ArrayList<Erp_Item>();
		
		this.nguoiTaoId = "";
		this.nguoiTaoList = new ArrayList<Erp_Item>();
			
		this.loaiNhapMuaId = "";
		this.loaiNhapMuaList = new ArrayList<Erp_Item>();
		this.ngayhoadon="";
		this.sanphamId="";
		 util=new Utility();
	}
		
	
	public void createRs() 
	{		
		this.Loaihanglist= this.db.get("select * from ERP_LOAISANPHAM" );
		this.sanphamRs = this.db.get(" select * from ERP_SANPHAM WHERE TRANGTHAI = 1 AND CONGTY_FK = "+this.congtyId);
		
		this.nccList = this.db.get(" SELECT * FROM ERP_NHACUNGCAP WHERE TRANGTHAI = 1 and duyet = '1' AND CONGTY_FK = "+this.congtyId);
		
		String query = "select dv.PK_SEQ, dv.MA + ' - ' + dv.TEN as ten from ERP_DONVITHUCHIEN dv where dv.TRANGTHAI = 1 order by NGAYTAO desc, PK_SEQ desc";
		Erp_Item.getListFromQuery(db, query, this.donViThucHienList);
		
		query = "select nv.PK_SEQ, nv.DANGNHAP + ' - ' + nv.TEN as ten from nhanvien nv where nv.TRANGTHAI = 1";
		Erp_Item.getListFromQuery(db, query, this.nguoiTaoList);
			
		this.loaiNhapMuaList.clear();
		this.loaiNhapMuaList.add(new Erp_Item("0", "Hóa đơn nhập khẩu"));
		this.loaiNhapMuaList.add(new Erp_Item("1", "Hóa đơn trong nước"));
		this.loaiNhapMuaList.add(new Erp_Item("2", "Hóa đơn VT/ CPDV/ TSCĐ/ CCDC"));
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
//                    
//                    String namNV = psktRs.getString("ngayghinhan").substring(0, 4);
//        			String thangNV = psktRs.getString("ngayghinhan").substring(5, 7);
        			
//        			String ncc_fk = psktRs.getString("ncc_fk");
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
                    
//                    String doituongno = "";
//                    String madoituongno = "";
//                    String doituongco = "";
//                    String madoituongco ="";
                    
//                    doituongco = "Nhà cung cấp";
//            		madoituongco = ncc_fk; 
            		
            		String NO_TENDOITUONG = "";
            		String CO_TENDOITUONG = "";
            		
            		if(sanphamId.trim().length()>0)
                	{
//                		doituongno = "Sản phẩm";
//                		madoituongno = sanphamId;
                		
                		TAIKHOANNO_TIENHANG = psktRs.getString("TAIKHOANNO_SANPHAM");
                		TAIKHOANCO_TIENHANG = taikhoanncc_fk;
                		
                		TAIKHOANNO_VAT = psktRs.getString("TAIKHOANNO_VAT");
                		TAIKHOANCO_VAT = taikhoanncc_fk;
                		
                		NO_TENDOITUONG = psktRs.getString("TENSP");
                		CO_TENDOITUONG = psktRs.getString("TENNCC");
                	}
                	
                	if(ccdcId.trim().length()>0)
                	{
//                		doituongno = "Công cụ dụng cụ";
//                		madoituongno = ccdcId;
                		
                		TAIKHOANNO_TIENHANG = psktRs.getString("TAIKHOANNO_CCDK");
                		TAIKHOANCO_TIENHANG = taikhoanncc_fk;
                		
                		TAIKHOANNO_VAT = psktRs.getString("TAIKHOANNO_VAT");
                		TAIKHOANCO_VAT = taikhoanncc_fk;
                		
                		NO_TENDOITUONG = psktRs.getString("TENCCDC");
                		CO_TENDOITUONG = psktRs.getString("TENNCC");
                	}
                	
                	if(taisanId.trim().length()>0)
                	{
//                		doituongno = "Tài sản";
//                		madoituongno = taisanId;
                		
                		TAIKHOANNO_TIENHANG = psktRs.getString("TAIKHOANNO_TAISAN");
                		TAIKHOANCO_TIENHANG = taikhoanncc_fk;
                		
                		TAIKHOANNO_VAT = psktRs.getString("TAIKHOANNO_VAT_TSCD");
                		TAIKHOANCO_VAT = taikhoanncc_fk;
                		
                		NO_TENDOITUONG = psktRs.getString("TENTS");
                		CO_TENDOITUONG = psktRs.getString("TENNCC");
                	}
                	
                	if(chiphiId.trim().length()>0)
                	{
//                		doituongno = "Chi phí";
//                		madoituongno = chiphiId;
                		
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
	@Override
	public void initBangKeHoaDon() 
	{
		String sql = "";
		this.getNppInfo();
			sql =   " SELECT DISTINCT CASE WHEN a.loaihd = 0 THEN N'Hóa đơn NCC nhập khẩu' WHEN A.LOAIHD = 1 THEN N'Hóa đơn NCC trong nước' ELSE N'Hóa đơn NCC chi phí dịch vụ' END AS LOAIHD \n"
					+ ", A.PREFIX+CONVERT(VARCHAR, A.PK_SEQ) AS PARK_NO, A.NGAYGHINHAN\r\n" + 
					" , ISNULL(HDNCC.SOHOADON,'') AS SOHOADON,  ISNULL(HDNCC.KYHIEU,'') AS KYHIEU,  ISNULL(HDNCC.NGAYHOADON,'') AS NGAYHOADON,  ISNULL(HDNCC.MAUHOADON,'') AS MAUHOADON\r\n" + 
					" , MH.SOPO, TT.MA, NCC.TEN\r\n" + 
					" , HDNCC.SOTIENBVAT, HDNCC.VAT, HDNCC.SOTIENAVAT\r\n" + 
					" , ISNULL(KHOBIETTRU.TEN, '') KHOBIETTRU, ISNULL(KHOTONTRU.TEN, '') KHOTONTRU\r\n" + 
					" , A.DIENGIAI, ISNULL(CONVERT(VARCHAR, A.SOTTDUYET), '') AS SOTTDUYET,  ISNULL(A.NGAYDUYET, '') NGAYDUYET\r\n" + 
					" , CASE WHEN ISNULL(A.TRANGTHAI, 0) IN (0, 1) THEN N'Chưa duyệt' \r\n" + 
					"		WHEN A.TRANGTHAI = 2 THEN N'Đã duyệt'\r\n" + 
					"		WHEN A.TRANGTHAI = 3 THEN N'Hoàn tất'\r\n" + 
					"		WHEN A.TRANGTHAI = 4 THEN N'Đã hủy'\r\n" + 
					"	ELSE ''\r\n" + 
					"	END AS TRANGTHAI \n"+
					
					" FROM 	 ERP_PARK A  \n " +
					" INNER JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = A.NCC_FK   	\n" +
					" INNER JOIN NHANVIEN NV ON A.NGUOITAO = NV.PK_SEQ   \n" +  
					" INNER JOIN NHANVIEN NV2 ON A.NGUOISUA = NV2.PK_SEQ	     \n" +  
					" LEFT JOIN 	(   \n" +  
					" 		 SELECT A.PARK_FK , (DEMA - ISNULL (DEMB,0)) AS SODEM \n" +
					"		 FROM 	(SELECT PARK_FK , COUNT(PARK_FK) AS DEMA  	\n" +
					"				 FROM ERP_HOADONNCC   \n" +
					"				 WHERE CONGTY_FK = "+this.congtyId +
					"		 		 GROUP BY PARK_FK) AS A  	\n" +
					"				 LEFT JOIN   	\n" +
					"				(SELECT PARK_FK , COUNT(PARK_FK) AS DEMB  	\n" +
					"				 FROM ERP_HOADONNCC  	 \n" +  
					"		 		 WHERE TRANGTHAI = 2 AND CONGTY_FK = "+this.congtyId+
					"				 GROUP BY PARK_FK) AS B ON B.PARK_FK = A.PARK_FK  \n" +  
					"				) HOANTAT ON HOANTAT.PARK_FK = A.PK_SEQ   	 \n" +  

					" LEFT JOIN 	(  \n" +  
					"   				SELECT 	DISTINCT HD.PARK_FK 		\n"+
					"   				FROM 	ERP_THANHTOANHOADON_HOADON TT		\n"+   
					"   					 	INNER JOIN ERP_HOADONNCC HD ON HD.PK_SEQ = TT.HOADON_FK AND TT.LOAIHD = 0 \n"+
					"   				WHERE 	TT.THANHTOANHD_FK IN (SELECT PK_SEQ FROM ERP_THANHTOANHOADON WHERE TRANGTHAI != 2 AND CONGTY_FK = "+this.congtyId+" )  \n"+
					"							AND HD.CONGTY_FK = "+this.congtyId +
					"  					) THANHTOAN ON THANHTOAN.PARK_FK = A.PK_SEQ " +
					" INNER JOIN ERP_HOADONNCC HDNCC ON A.PK_SEQ = HDNCC.PARK_FK \n"+   
					" LEFT JOIN ERP_HOADONNCC_DONMUAHANG HD_MH ON HD_MH.HOADONNCC_FK = HDNCC.pk_seq \n"+   
					" LEFT JOIN ERP_MUAHANG MH ON HD_MH.MUAHANG_FK = MH.PK_SEQ  "+
					" LEFT JOIN ERP_TIENTE TT ON TT.PK_SEQ = A.TIENTE_FK\r\n" + 
					" LEFT JOIN ERP_KHOTT KHOBIETTRU ON KHOBIETTRU.PK_SEQ = HDNCC.KHOBIETTRU_FK\r\n" + 
					" LEFT JOIN ERP_KHOTT KHOTONTRU ON KHOTONTRU.PK_SEQ = HDNCC.KHOTONTRU_FK "
					+ "where 1 =1 \n";   
			
			
			
			if (this.DUYET != 1) {
				sql+=" and MH.LOAI =2 ";
			}
			if(this.DUYET==1)
			{
				sql+=" and a.trangthai <> '0' ";
			}
			
			sql+= "  and  HDNCC.CONGTY_FK =" + this.congtyId + " \n " ;
				//  "AND HDNCC.PK_SEQ IN (SELECT HDNCC_FK FROM ERP_NHANHANG WHERE TRANGTHAI <> '3') ";// CHỈ HIỆN RA NHỮNG HĐ ĐÃ NHẬN HÀNG (KO TÍNH ĐÃ HỦY)
	
		
		// Phân quyền nhân viên theo phòng ban, mặc định admin được nhìn thấy
		if( !this.USERID.equals("100002") && !IsPhongKetoan()){
		sql += " and NV.PHONGBAN_FK in ( select  " +
					" distinct PHONGBAN_FK from NHANVIEN where PK_SEQ ="+this.USERID+")";
		}
		
		if( !this.USERID.equals("100002") && checkTruongPhong() == false && !IsPhongKetoan()){
			sql += " and a.NGUOITAO = "+ this.USERID;
		}
		
		if(this.ngayhoadon.length()>0)
			sql+="and a.ngayghinhan='"+this.ngayhoadon+"'";
		if(this.ngayghinhan.length() > 0)
			sql += " and a.ngayghinhan >= '" + this.ngayghinhan + "'";
		if (this.denNgay.length() >0)
			sql +=" and a.ngayghinhan <= '" + this.denNgay +"'";
		
		if(this.NCC.length() > 0)
			sql += " and a.ncc_fk = '" + this.NCC + "'";
		
		if(this.LOAIHANG.length() > 0)
			sql += " and a.LOAIHD = '" + this.LOAIHANG+ "'";
		if(this.ID.length() > 0)
			sql += " and (('170'+ cast(a.pk_seq as nvarchar(50)) like '%" + this.ID + "%' ))";
		if(this.SOHOADON.length() > 0) {
			sql += " and a.pk_seq in (select park_fk from ERP_HOADONNCC where sohoadon like N'%" + this.SOHOADON + "%') ";
		}
		System.out.println("TRANG THAI :" +TRANGTHAI);
		if(this.TRANGTHAI.length() > 0){
			if(this.TRANGTHAI.equals("0") || this.TRANGTHAI.equals("1") ){
				sql += " and a.trangthai = '" + this.TRANGTHAI + "'";
			}
			else if(this.TRANGTHAI.equals("2")) 
			{
				sql += " and a.trangthai = '" + this.TRANGTHAI + "' and hoantat.sodem != 0  and thanhtoan.park_fk is null";
			}
			else if(this.TRANGTHAI.equals("3")) 
			{
				sql += " and a.trangthai = 2 and hoantat.sodem != 0  and thanhtoan.park_fk is not null";
			}
			else if(this.TRANGTHAI.equals("4")) 
			{
				sql += " and ( (a.trangthai = 2 and hoantat.sodem = 0 ) or a.trangthai = 3 )";
			}
			else if(this.TRANGTHAI.equals("5")){
				sql += " and a.trangthai = 4 ";
			}
		}
		 
		if(this.nguoiTaoId.length() > 0) {
				sql += " and a.nguoitao = '" + this.nguoiTaoId + "' ";
		}
		if(this.donViThucHienId.length()>0 )
		{
			    sql += " and MH.DONVITHUCHIEN_FK = '" + this.donViThucHienId + "' ";
		}
		sql += " ORDER BY A.NGAYGHINHAN desc, A.PREFIX+CONVERT(VARCHAR, A.PK_SEQ)  desc";
		System.out.println("sql:\n" + sql + "\n---------------------------------------------------------");
		this.ParkList = db.get( sql);
	}
	
	
	public void init(String query) 
	{
		String sql = "";
		this.getNppInfo();
		System.out.println("query: \n" + query + "\n-----------------------------------------------");
			sql =   " SELECT DISTINCT A.PK_SEQ , A.NGAYGHINHAN, NCC.TEN AS TENNHACUNGCAP, A.TRANGTHAI ,isnull( A.tooltip,'') as tooltip, isnull(A.NOTE,'') as NOTE,     	NV.TEN AS TENNV,A.NGAYSUA,A.NGAYTAO,NV.PK_SEQ AS MANV,NV2.TEN AS \n" +  
					"  		 TENNVS,NV2.PK_SEQ AS MANVS, A.PREFIX     	, HOANTAT.SODEM  AS DAHOANTAT , CASE WHEN THANHTOAN.PARK_FK IS NULL THEN 0 ELSE 1 END AS DACOTHANHTOAN, ISNULL(HDNCC.SOHOADON,'') AS SOHOADON, HDNCC.LOAIHD	, \n" +
					"		(SELECT COUNT(PK_SEQ) FROM ERP_NHANHANG WHERE HDNCC_FK = HDNCC.PK_SEQ  AND TRANGTHAI NOT IN (3) ) DANHANHANG, NCC.PK_SEQ NCCID, HDNCC.PK_SEQ HOADONID, \n"+
					" 		( SELECT CASE WHEN HDMH1.SANPHAM_FK IS NOT NULL THEN 0 WHEN HDMH1.TAISAN_FK IS NOT NULL THEN 1 WHEN HDMH1.CCDC_FK IS NOT NULL THEN 3 ELSE 2 END LOAIHH \n"+
					" 		  FROM \n"+
					" 		 (SELECT TOP (1) * " +
					"  		  FROM ERP_HOADONNCC_DONMUAHANG HDMH " +
					"  		  WHERE HDMH.HOADONNCC_FK = HDNCC.PK_SEQ ) HDMH1 ) LOAIHH \n"+
					
					" FROM 	 ERP_PARK A  \n " +
					" INNER JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = A.NCC_FK   	\n" +
					" INNER JOIN NHANVIEN NV ON A.NGUOITAO = NV.PK_SEQ   \n" +  
					" INNER JOIN NHANVIEN NV2 ON A.NGUOISUA = NV2.PK_SEQ	     \n" +  
					" LEFT JOIN 	(   \n" +  
					" 		 SELECT A.PARK_FK , (DEMA - ISNULL (DEMB,0)) AS SODEM \n" +
					"		 FROM 	(SELECT PARK_FK , COUNT(PARK_FK) AS DEMA  	\n" +
					"				 FROM ERP_HOADONNCC   \n" +
					"				 WHERE CONGTY_FK = "+this.congtyId +
					"		 		 GROUP BY PARK_FK) AS A  	\n" +
					"				 LEFT JOIN   	\n" +
					"				(SELECT PARK_FK , COUNT(PARK_FK) AS DEMB  	\n" +
					"				 FROM ERP_HOADONNCC  	 \n" +  
					"		 		 WHERE TRANGTHAI = 2 AND CONGTY_FK = "+this.congtyId+
					"				 GROUP BY PARK_FK) AS B ON B.PARK_FK = A.PARK_FK  \n" +  
					"				) HOANTAT ON HOANTAT.PARK_FK = A.PK_SEQ   	 \n" +  

					" LEFT JOIN 	(  \n" +  
					"   				SELECT 	DISTINCT HD.PARK_FK 		\n"+
					"   				FROM 	ERP_THANHTOANHOADON_HOADON TT		\n"+   
					"   					 	INNER JOIN ERP_HOADONNCC HD ON HD.PK_SEQ = TT.HOADON_FK AND TT.LOAIHD = 0 \n"+
					"   				WHERE 	TT.THANHTOANHD_FK IN (SELECT PK_SEQ FROM ERP_THANHTOANHOADON WHERE TRANGTHAI != 2 AND CONGTY_FK = "+this.congtyId+" )  \n"+
					"							AND HD.CONGTY_FK = "+this.congtyId +
					"  					) THANHTOAN ON THANHTOAN.PARK_FK = A.PK_SEQ " +
					" INNER JOIN ERP_HOADONNCC HDNCC ON A.PK_SEQ = HDNCC.PARK_FK \n"+   
					" LEFT JOIN ERP_HOADONNCC_DONMUAHANG HD_MH ON HD_MH.HOADONNCC_FK = HDNCC.pk_seq \n"+   
					" LEFT JOIN ERP_MUAHANG MH ON HD_MH.MUAHANG_FK = MH.PK_SEQ   where 1 =1 \n ";

			
			if (this.DUYET != 1) {
				sql+=" and MH.LOAI =2 ";
			}
			if(this.DUYET==1)
			{
				sql+=" and a.trangthai <> '0' ";
			}
			
			sql+= "  and  HDNCC.CONGTY_FK =" + this.congtyId + " \n " ;
				//  "AND HDNCC.PK_SEQ IN (SELECT HDNCC_FK FROM ERP_NHANHANG WHERE TRANGTHAI <> '3') ";// CHỈ HIỆN RA NHỮNG HĐ ĐÃ NHẬN HÀNG (KO TÍNH ĐÃ HỦY)

		// Phân quyền nhân viên theo phòng ban, mặc định admin được nhìn thấy
		if( !this.USERID.equals("100002") && !IsPhongKetoan()  && util.GetquyenNew("ErpParkHoadonSvl","",USERID)[9]==0){
		sql += " and NV.PHONGBAN_FK in ( select  " +
					" distinct PHONGBAN_FK from NHANVIEN where PK_SEQ ="+this.USERID+")";
		}
		
		if( !this.USERID.equals("100002") && checkTruongPhong() == false && !IsPhongKetoan()  && util.GetquyenNew("ErpParkHoadonSvl","",USERID)[9]==0){
			sql += " and a.NGUOITAO = "+ this.USERID;
		}
		
		if(this.ngayhoadon.length()>0)
			sql+="and a.ngayghinhan='"+this.ngayhoadon+"'";
		if(this.ngayghinhan.length() > 0)
			sql += " and a.ngayghinhan >= '" + this.ngayghinhan + "'";
		if (this.denNgay.length() >0)
			sql +=" and a.ngayghinhan <= '" + this.denNgay +"'";
		
		if(this.NCC.length() > 0)
			sql += " and a.ncc_fk = '" + this.NCC + "'";
		
		if(this.LOAIHANG.length() > 0)
			sql += " and a.LOAIHD = '" + this.LOAIHANG+ "'";
		if(this.ID.length() > 0)
			sql += " and (('170'+ cast(a.pk_seq as nvarchar(50)) like '%" + this.ID + "%' ))";
		if(this.SOHOADON.length() > 0) {
			sql += " and a.pk_seq in (select park_fk from ERP_HOADONNCC where sohoadon like N'%" + this.SOHOADON + "%') ";
		}
		System.out.println("TRANG THAI :" +TRANGTHAI);
		if(this.TRANGTHAI.length() > 0){
			if(this.TRANGTHAI.equals("0") || this.TRANGTHAI.equals("1") ){
				sql += " and a.trangthai = '" + this.TRANGTHAI + "'";
			}
			else if(this.TRANGTHAI.equals("2")) 
			{
				sql += " and a.trangthai = '" + this.TRANGTHAI + "' and hoantat.sodem != 0  and thanhtoan.park_fk is null";
			}
			else if(this.TRANGTHAI.equals("3")) 
			{
				sql += " and a.trangthai = 2 and hoantat.sodem != 0  and thanhtoan.park_fk is not null";
			}
			else if(this.TRANGTHAI.equals("4")) 
			{
				sql += " and ( (a.trangthai = 2 and hoantat.sodem = 0 ) or a.trangthai = 3 )";
			}
			else if(this.TRANGTHAI.equals("5")){
				sql += " and a.trangthai = 4 ";
			}
		}
		 
		if(this.nguoiTaoId.length() > 0) {
				sql += " and a.nguoitao = '" + this.nguoiTaoId + "' ";
		}
		if(this.donViThucHienId.length()>0 )
		{
			    sql += " and MH.DONVITHUCHIEN_FK = '" + this.donViThucHienId + "' ";
		}
		if(this.sanphamId.length() > 0){
			sql += " and "+ this.sanphamId + " IN ( select distinct SANPHAM_FK FROM ERP_MUAHANG_SP mhsp where mhsp.muahang_fk = MH.PK_SEQ and mhsp.sanpham_fk = HD_MH.SANPHAM_FK )";
					
		}
		
		if( this.sonhanhang.length()>0){
			sql += "  and a.PO_NHAPKHAU like '%"+ this.sonhanhang+"%'" ;
		}
	
		
		System.out.println("sql:\n" + sql + "\n---------------------------------------------------------");
		this.ParkList = createSplittingData( 30, 10, "NGAYGHINHAN desc, PK_SEQ  desc", sql);
		this.createRs();
	}
	
	
	
	// HAM LAY LIST BEN MENU DUYET HOA DON NCC (CONG NO PHAI THU)
	public void initDuyetHDNCC(String query) 
	{
		String sql = "";
		this.getNppInfo();
		System.out.println("query: \n" + query + "\n-----------------------------------------------");
			sql =   " SELECT DISTINCT A.PK_SEQ , A.NGAYGHINHAN, NCC.TEN AS TENNHACUNGCAP, A.TRANGTHAI ,isnull( A.tooltip,'') as tooltip, isnull(A.NOTE,'') as NOTE,     	NV.TEN AS TENNV,A.NGAYSUA,A.NGAYTAO,NV.PK_SEQ AS MANV,NV2.TEN AS \n" +  
					"  		 TENNVS,NV2.PK_SEQ AS MANVS, A.PREFIX     	, HOANTAT.SODEM  AS DAHOANTAT , CASE WHEN THANHTOAN.PARK_FK IS NULL THEN 0 ELSE 1 END AS DACOTHANHTOAN, ISNULL(HDNCC.SOHOADON,'') AS SOHOADON, HDNCC.LOAIHD	, \n" +
					"		(SELECT COUNT(PK_SEQ) FROM ERP_NHANHANG WHERE HDNCC_FK = HDNCC.PK_SEQ  AND TRANGTHAI NOT IN (3) ) DANHANHANG, NCC.PK_SEQ NCCID, HDNCC.PK_SEQ HOADONID, \n"+
					" 		( SELECT CASE WHEN HDMH1.SANPHAM_FK IS NOT NULL THEN 0 WHEN HDMH1.TAISAN_FK IS NOT NULL THEN 1 WHEN HDMH1.CCDC_FK IS NOT NULL THEN 3 ELSE 2 END LOAIHH \n"+
					" 		  FROM \n"+
					" 		 (SELECT TOP (1) * " +
					"  		  FROM ERP_HOADONNCC_DONMUAHANG HDMH " +
					"  		  WHERE HDMH.HOADONNCC_FK = HDNCC.PK_SEQ ) HDMH1 ) LOAIHH \n"+
					
					" FROM 	 ERP_PARK A  \n " +
					" INNER JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = A.NCC_FK   	\n" +
					" INNER JOIN NHANVIEN NV ON A.NGUOITAO = NV.PK_SEQ   \n" +  
					" INNER JOIN NHANVIEN NV2 ON A.NGUOISUA = NV2.PK_SEQ	     \n" +  
					" LEFT JOIN 	(   \n" +  
					" 		 SELECT A.PARK_FK , (DEMA - ISNULL (DEMB,0)) AS SODEM \n" +
					"		 FROM 	(SELECT PARK_FK , COUNT(PARK_FK) AS DEMA  	\n" +
					"				 FROM ERP_HOADONNCC   \n" +
					"				 WHERE CONGTY_FK = "+this.congtyId +
					"		 		 GROUP BY PARK_FK) AS A  	\n" +
					"				 LEFT JOIN   	\n" +
					"				(SELECT PARK_FK , COUNT(PARK_FK) AS DEMB  	\n" +
					"				 FROM ERP_HOADONNCC  	 \n" +  
					"		 		 WHERE TRANGTHAI = 2 AND CONGTY_FK = "+this.congtyId+
					"				 GROUP BY PARK_FK) AS B ON B.PARK_FK = A.PARK_FK  \n" +  
					"				) HOANTAT ON HOANTAT.PARK_FK = A.PK_SEQ   	 \n" +  

					" LEFT JOIN 	(  \n" +  
					"   				SELECT 	DISTINCT HD.PARK_FK 		\n"+
					"   				FROM 	ERP_THANHTOANHOADON_HOADON TT		\n"+   
					"   					 	INNER JOIN ERP_HOADONNCC HD ON HD.PK_SEQ = TT.HOADON_FK AND TT.LOAIHD = 0 \n"+
					"   				WHERE 	TT.THANHTOANHD_FK IN (SELECT PK_SEQ FROM ERP_THANHTOANHOADON WHERE TRANGTHAI != 2 AND CONGTY_FK = "+this.congtyId+" )  \n"+
					"							AND HD.CONGTY_FK = "+this.congtyId +
					"  					) THANHTOAN ON THANHTOAN.PARK_FK = A.PK_SEQ " +
					" INNER JOIN ERP_HOADONNCC HDNCC ON A.PK_SEQ = HDNCC.PARK_FK \n"+   
					" LEFT JOIN ERP_HOADONNCC_DONMUAHANG HD_MH ON HD_MH.HOADONNCC_FK = HDNCC.pk_seq \n"+   
					" LEFT JOIN ERP_MUAHANG MH ON HD_MH.MUAHANG_FK = MH.PK_SEQ  \n" +
					" INNER JOIN (SELECT * FROM ERP_NHANHANG WHERE TRANGTHAI<>0 AND TRANGTHAI<>3 )NH ON NH.hdNCC_fk=HDNCC.pk_seq  where 1 =1 \n";   
			//MAP VS NHAN HANG: CHI LAY HOA DON RA NHAN HANG DA CHOT DEN HOAN THANH
			
			if (this.DUYET != 1) {
				sql+=" and MH.LOAI =2 ";
			}
			if(this.DUYET==1)
			{
				sql+=" and a.trangthai <> '0' ";
			}
			
			sql+= "  and  HDNCC.CONGTY_FK =" + this.congtyId + " \n " ;
				//  "AND HDNCC.PK_SEQ IN (SELECT HDNCC_FK FROM ERP_NHANHANG WHERE TRANGTHAI <> '3') ";// CHỈ HIỆN RA NHỮNG HĐ ĐÃ NHẬN HÀNG (KO TÍNH ĐÃ HỦY)
	
		
		// Phân quyền nhân viên theo phòng ban, mặc định admin được nhìn thấy
		if( !this.USERID.equals("100002") && !IsPhongKetoan()){
		sql += " and NV.PHONGBAN_FK in ( select  " +
					" distinct PHONGBAN_FK from NHANVIEN where PK_SEQ ="+this.USERID+")";
		}
		
		if( !this.USERID.equals("100002") && checkTruongPhong() == false && !IsPhongKetoan()){
			sql += " and a.NGUOITAO = "+ this.USERID;
		}
		
		if(this.ngayhoadon.length()>0)
			sql+="and a.ngayghinhan='"+this.ngayhoadon+"'";
		if(this.ngayghinhan.length() > 0)
			sql += " and a.ngayghinhan >= '" + this.ngayghinhan + "'";
		if (this.denNgay.length() >0)
			sql +=" and a.ngayghinhan <= '" + this.denNgay +"'";
		
		if(this.NCC.length() > 0)
			sql += " and a.ncc_fk = '" + this.NCC + "'";
		
		if(this.LOAIHANG.length() > 0)
			sql += " and a.LOAIHD = '" + this.LOAIHANG+ "'";
		if(this.ID.length() > 0)
			sql += " and (('170'+ cast(a.pk_seq as nvarchar(50)) like '%" + this.ID + "%' ))";
		if(this.SOHOADON.length() > 0) {
			sql += " and a.pk_seq in (select park_fk from ERP_HOADONNCC where sohoadon like N'%" + this.SOHOADON + "%') ";
		}
		System.out.println("TRANG THAI :" +TRANGTHAI);
		if(this.TRANGTHAI.length() > 0){
			if(this.TRANGTHAI.equals("0") || this.TRANGTHAI.equals("1") ){
				sql += " and a.trangthai = '" + this.TRANGTHAI + "'";
			}
			else if(this.TRANGTHAI.equals("2")) 
			{
				sql += " and a.trangthai = '" + this.TRANGTHAI + "' and hoantat.sodem != 0  and thanhtoan.park_fk is null";
			}
			else if(this.TRANGTHAI.equals("3")) 
			{
				sql += " and a.trangthai = 2 and hoantat.sodem != 0  and thanhtoan.park_fk is not null";
			}
			else if(this.TRANGTHAI.equals("4")) 
			{
				sql += " and ( (a.trangthai = 2 and hoantat.sodem = 0 ) or a.trangthai = 3 )";
			}
			else if(this.TRANGTHAI.equals("5")){
				sql += " and a.trangthai = 4 ";
			}
		}
		 
		if(this.nguoiTaoId.length() > 0) {
				sql += " and a.nguoitao = '" + this.nguoiTaoId + "' ";
		}
		if(this.donViThucHienId.length()>0 )
		{
			    sql += " and MH.DONVITHUCHIEN_FK = '" + this.donViThucHienId + "' ";
		}
		if(this.sanphamId.length() > 0){
			sql += " and "+ this.sanphamId + " IN ( select distinct SANPHAM_FK FROM ERP_MUAHANG_SP mhsp where mhsp.muahang_fk = MH.PK_SEQ and mhsp.sanpham_fk = HD_MH.SANPHAM_FK )";
					
		}
		
		// chi sau khi nhan hang xong moi cho duyet
		sql+= " \n  and A.HOANTAT_NHANHANG =1    ";
		
		System.out.println("sql:\n" + sql + "\n---------------------------------------------------------");
//		String query_init = createSplittingData_ListNew(this.db, 30, 10, "NGAYGHINHAN desc, PK_SEQ  desc", sql);
		this.ParkList = createSplittingData( 30, 10, "NGAYGHINHAN desc, PK_SEQ  desc", sql);
		
//		System.out.println("query_init: \n" + query_init + "\n-----------------------------------------------------");
		//CHỈNH SỬAs
		
//		this.ParkList = db.get(query_init);
		
		this.createRs();
	}
	
	
	private boolean IsPhongKetoan() {
		// TODO Auto-generated method stub
		try{
			String query="select  PHONGBAN_FK from NHANVIEN where PK_SEQ = "+this.USERID+" and PHONGBAN_FK=100049";
			boolean bientmp=false;
			ResultSet rs=db.get(query);
			if(rs.next()){
				bientmp=true;
			}
			rs.close();
			return bientmp;
		}catch(Exception er){
			er.printStackTrace();
		}
		return false;
	}


	// kiem tra co phai truong phong hay khong?
	private boolean checkTruongPhong(){
		try{
			String sql = " select NHANVIEN_FK from ERP_CHUCDANH where isTP =1";
			ResultSet rs = this.db.get(sql);
			List<String> list = new ArrayList<String>();
			if(rs!=null){
				while(rs.next()){
					list.add(rs.getString("NHANVIEN_FK"));
					
				}
				rs.close();
			}
			
			for(int i=0; i< list.size(); i++){
				if(this.USERID.equals(list.get(i))){
					return true;
				}
			}
			return false;
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	}

	
	public void close() 
	{
		this.db.shutDown();
		try 
		{
			if(this.Loaihanglist != null)
				this.Loaihanglist.close();
			if(this.ParkList!= null)
				this.ParkList.close();
		}
		catch (SQLException e){}
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
			db.update("update ERP_PARK set trangthai = '0', nguoisua = "+userId+" where pk_seq = '" + parkId + "' and trangthai=1");

		return msg;
	}

	
	public String checkSoHoaDon(String parkId) {
		
		String msg= "";
		
		String query = "\n 	SELECT count(hd.pk_seq) dem FROM ERP_HOADONNCC hd inner join ERP_PARK p on hd.PARK_FK = p.PK_SEQ" +
					   "\n 	WHERE hd.SOHOADON IN (SELECT A.SOHOADON FROM ERP_HOADONNCC A INNER JOIN ERP_PARK B ON A.PARK_FK = B.PK_SEQ " +
					   "\n 	WHERE A.PARK_fk = "+parkId+" AND B.TRANGTHAI NOT IN (4) )"+
					   "\n	and hd.kyhieu IN (SELECT A.kyhieu FROM ERP_HOADONNCC A INNER JOIN ERP_PARK B ON A.PARK_FK = B.PK_SEQ "+ 
					   "\n	WHERE A.PARK_fk = "+parkId+" AND B.TRANGTHAI NOT IN (4) ) "+
					   "\n	and p.ncc_fk in (SELECT b.ncc_fk FROM ERP_HOADONNCC A INNER JOIN ERP_PARK B ON A.PARK_FK = B.PK_SEQ "+ 
					   "\n	WHERE A.PARK_fk = "+parkId+" AND B.TRANGTHAI NOT IN (4) )";
		query+= 	"AND P.TRANGTHAI NOT IN (4) ";
		
		System.out.println(  "[CHECK SOHOADON] :" +query);
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

		if(dem >= 1)
			msg = "Số hóa đơn bị trùng bạn vui lòng thay đổi số hóa đơn!";
		
		return msg;
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
		
		return this.ngayghinhan;
	}

	
	public void setNgayghinhan(String ngayghinhan) 
	{
		this.ngayghinhan=ngayghinhan;
		
	}
	
	public String getDenNgay()
	{
		return this.denNgay;
	}
	public void setDenNgay(String denNgay)
	{
		this.denNgay = denNgay;
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
	public String getloaihd() {
		return this.loaihd;
	}
	
	public void setloaihd(String loaihd) {
		this.loaihd = loaihd;
	}

	public void setDonViThucHienList(List<Erp_Item> donViThucHienList) {
		this.donViThucHienList = donViThucHienList;
	}

	public List<Erp_Item> getDonViThucHienList() {
		return donViThucHienList;
	}

	public void setDonViThucHienId(String donViThucHienId) {
		this.donViThucHienId = donViThucHienId;
	}

	public String getDonViThucHienId() {
		return donViThucHienId;
	}
	
	public String getNguoiTaoId() {
		return nguoiTaoId;
	}


	public void setNguoiTaoId(String nguoiTaoId) {
		this.nguoiTaoId = nguoiTaoId;
	}


	public List<Erp_Item> getNguoiTaoList() {
		return nguoiTaoList;
	}


	public void setNguoiTaoList(List<Erp_Item> nguoiTaoList) {
		this.nguoiTaoList = nguoiTaoList;
	}


	public String getLoaiNhapMuaId() {
		return loaiNhapMuaId;
	}


	public void setLoaiNhapMuaId(String loaiNhapMuaId) {
		this.loaiNhapMuaId = loaiNhapMuaId;
	}


	public List<Erp_Item> getLoaiNhapMuaList() {
		return loaiNhapMuaList;
	}


	public void setLoaiNhapMuaList(List<Erp_Item> loaiNhapMuaList) {
		this.loaiNhapMuaList = loaiNhapMuaList;
	}

	@Override
	public int getDUYET() {
		return DUYET;
	}


	@Override
	public void setDUYET(int dUYET) {
		DUYET = dUYET;
	}


	public String getNgayhoadon() {
		return ngayhoadon;
	}


	public void setNgayhoadon(String ngayhoadon) {
		this.ngayhoadon = ngayhoadon;
	}


	public ResultSet getSanphamRs() {
		return sanphamRs;
	}


	public void setSanphamRs(ResultSet sanphamRs) {
		this.sanphamRs = sanphamRs;
	}


	public String getSanphamId() {
		return sanphamId;
	}


	public void setSanphamId(String sanphamId) {
		this.sanphamId = sanphamId;
	}
	
	
}