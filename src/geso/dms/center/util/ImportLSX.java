package geso.dms.center.util;

import geso.traphaco.erp.beans.danhmucvattu.IDanhmucvattu_SP;
import geso.traphaco.erp.beans.danhmucvattu.imp.Danhmucvattu_SP;
import geso.traphaco.erp.beans.lenhsanxuatgiay.IErpLenhsanxuat;
import geso.traphaco.erp.beans.lenhsanxuatgiay.ILenhSXCongDoan;
import geso.traphaco.erp.beans.lenhsanxuatgiay.ISanPhamSanXuat;
import geso.traphaco.erp.beans.lenhsanxuatgiay.imp.ErpLenhsanxuat;
import geso.traphaco.erp.beans.lenhsanxuatgiay.imp.LenhSXCongDoan;
import geso.traphaco.erp.beans.lenhsanxuatgiay.imp.SanPhamSanXuat;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
 

public class ImportLSX extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
   
    public ImportLSX() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		doPost(request, response);
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
//		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("application/xlsm");
		response.setHeader("Content-Disposition", "attachment; filename=PO_GR_IV_01.2014.xlsm");
				
	 

	}
	
 
	public  static void main ( String args [  ]  )   {
		System.out.println("Begin... : ");

		try{
			String isTINHHAMAM ="1";
			String isTINHHAMLUONG="1";
			double soluongsx =400;
			
			String SpId="108779";
			String Khosxid="100051";
		// TODO Auto-generated method stub
		String query=" SELECT KHO_SP.KHOTT_FK  , KHO_SP.AVAILABLE as soluong " +
		  " ,ISNULL(HAMLUONG,0) AS HAMLUONG ,ISNULL(HAMAM,0) AS HAMAM \n"+
		  " FROM ERP_KHOTT_SP_CHITIET  KHO_SP  \n"+
		  " INNER JOIN ERP_KHOTT KHO ON KHO.PK_SEQ=KHO_SP.KHOTT_FK \n"+
		  " WHERE KHO.PK_SEQ  IN (SELECT KHOTT_NL_FK FROM ERP_KHOSX_KHONHANNL  WHERE KHOTT_SX_FK ="+Khosxid+" UNION ALL  SELECT "+Khosxid+"  ) \n"+
		  " AND SANPHAM_FK ="+SpId+ "\n" +
		  " AND KHO_SP.AVAILABLE >0  \n";
		dbutils db= new dbutils();
		
		 ResultSet rs =db.get(query);
		 double total_sl=0;
		 while(rs.next()){
			 double soluongct=rs.getDouble("soluong");
			 if(isTINHHAMAM.equals("1")){
				 soluongct=soluongct- soluongct* rs.getDouble("HAMAM") /100 ;
				 
				
			 }
			 
			 if(isTINHHAMLUONG.equals("1")){
				 soluongct =soluongct* rs.getDouble("HAMLUONG")/100;
			 }
			 total_sl +=soluongct;
			 
		 }
		 System.out.println("total_sl : "+total_sl);
		
		}catch(Exception er){
			er.printStackTrace();
		}
	 
	
		
	/*	
		Update_quycach_DTY();*/
		// CapNhatKho(); 
		/*String nhId ="";
		 String query="";
		 ResultSet ds;
		 dbutils db = new dbutils();
		 
		 // lấy DS to khai
		 query= "select pk_seq from ERP_NHANHANG where SoToKhai_fk is not null";
		 ds = db.get(query);
		 
		 try{
			while (ds.next())
			{
					nhId= ds.getString("PK_SEQ");
					CheckTokhai(db,nhId);
				 	if(CheckTokhai(db,nhId))
				 	{
						query=" UPDATE ERP_THUENHAPKHAU SET CHOTTOKHAI=1 WHERE PK_SEQ=(SELECT  SOTOKHAI_FK FROM ERP_NHANHANG WHERE PK_SEQ='"+nhId+"') ";
						if(!db.update(query))
						{
							System.out.println("-- Lỗi update--" + query);
						}
						System.out.println("-- chốt tờ khai--" + nhId);
						
				 	}
			 }
			ds.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	 
		*/
		
		System.out.println("Xong Come Here : ");
	}
	private static void CapNhatNhapKho_Parent(){
		try{
			dbutils db=new dbutils();
			String query="select pk_seq from erp_lenhsanxuat_giay where trangthai <>8";
			ResultSet rs=db.get(query);
			while (rs.next()){
				query=" SELECT TH.LENHSANXUAT_FK,TH.CONGDOAN_FK,THSP.SOLUONG FROM ERP_TIEUHAONGUYENLIEU TH "+ 
						 " INNER JOIN ERP_LENHSANXUAT_TIEUHAO THSP ON  TH.PK_SEQ=THSP.TIEUHAONGUYENLIEU_FK "+
						 " INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=THSP.SANPHAM_FK "+
						 " WHERE TH.LENHSANXUAT_FK=100968 AND TH.TRANGTHAI=1 AND SP.LOAISANPHAM_FK=100011";
				ResultSet rssl_th=db.get(query);
			 
				if(rssl_th.next()){
						
				}
				
			}
			rs.close();
			
			
		}catch(Exception er){
			er.printStackTrace();
		}
	}
	private static void CapNhatThueNhapKhau(){
		dbutils db=new dbutils();
		try{
			NumberFormat formatter = new DecimalFormat("#,###,###");
			String query="select * from erp_thuenhapkhau ";
			ResultSet rs1=db.get(query);
			while (rs1.next()){
				
				
			String thueCurrent=rs1.getString("pk_Seq");
			
			query="delete ERP_THUENHAPKHAU_CHITIET where THUENHAPKHAU_FK = "+thueCurrent ;
			
			if(!db.update(query))//Luu vào bảng ERP_THUENHAPKHAU_HOADONNCC
			{
				 System.out.println("Khong the cap nhat " +query);
			}
				int dem=0;
				query=" SELECT SUM(SOLUONG) AS TONGSOLUONG,count(distinct SANPHAM_FK) as dem "+
					" FROM "+
					" ( "+
					" SELECT MUAHANG_FK,SANPHAM_FK,SOLUONG FROM ERP_HOADONNCC_DONMUAHANG HD_MH "+
					" INNER JOIN ERP_HOADONNCC HD ON HD.pk_seq=HD_MH.HOADONNCC_FK "+
					" WHERE HOADONNCC_FK IN (SELECT HOADONNCC_FK FROM ERP_THUENHAPKHAU_HOADONNCC WHERE THUENHAPKHAU_FK="+thueCurrent  +
					" ) "+
					" UNION ALL "+
					" SELECT NH.MUAHANG_FK,NHSP.SANPHAM_FK,NHSP.SOLUONGNHAN  FROM ERP_HOADONNCC_PHIEUNHAP HD_PN "+ 
					" INNER JOIN ERP_NHANHANG NH ON NH.PK_SEQ=HD_PN.phieunhan_fk  "+
					" INNER  JOIN ERP_NHANHANG_SANPHAM NHSP ON NH.PK_SEQ=NHSP.NHANHANG_FK "+
					" WHERE HOADONNCC_FK IN (SELECT HOADONNCC_FK FROM ERP_THUENHAPKHAU_HOADONNCC WHERE THUENHAPKHAU_FK="+thueCurrent +
					" ) "+
					" )   DATA ";
				 ResultSet rssum=db.get(query);
				 double tongsoluong=0;
				 if(rssum.next()){
					 tongsoluong=rssum.getDouble("TONGSOLUONG");
					 dem=rssum.getInt("dem");
				 }
				 rssum.close();
				
				query=" SELECT SANPHAM_FK,SUM(SOLUONG) AS SOLUONG  "+
				" FROM "+
				" ( "+
				" SELECT MUAHANG_FK,SANPHAM_FK,SOLUONG FROM ERP_HOADONNCC_DONMUAHANG HD_MH "+
				" INNER JOIN ERP_HOADONNCC HD ON HD.pk_seq=HD_MH.HOADONNCC_FK "+
				" WHERE HOADONNCC_FK IN (SELECT HOADONNCC_FK FROM ERP_THUENHAPKHAU_HOADONNCC WHERE THUENHAPKHAU_FK="+ thueCurrent  +
				" ) "+
				" UNION ALL "+
				" SELECT NH.MUAHANG_FK,NHSP.SANPHAM_FK,NHSP.SOLUONGNHAN  FROM ERP_HOADONNCC_PHIEUNHAP HD_PN "+ 
				" INNER JOIN ERP_NHANHANG NH ON NH.PK_SEQ=HD_PN.phieunhan_fk  "+
				" INNER  JOIN ERP_NHANHANG_SANPHAM NHSP ON NH.PK_SEQ=NHSP.NHANHANG_FK "+
				" WHERE HOADONNCC_FK IN (SELECT HOADONNCC_FK FROM ERP_THUENHAPKHAU_HOADONNCC WHERE THUENHAPKHAU_FK="+ thueCurrent  +
				" ) "+
				" )   DATA "+
				" GROUP BY SANPHAM_FK ";
				
				//Phan bổ tiền 
				ResultSet rsdata=db.get(query);
				int bien=0;
				long tongcong=0;
				long ThueNK=rs1.getLong("thuenk");
				String ptThueNK=rs1.getString("ptThueNK");
				
				while (rsdata.next()){
					bien++;
					double soluongsp=rsdata.getDouble("SOLUONG");
					double phantramdonggop= soluongsp*100/tongsoluong;
					
					long tienthuenk=Math.round(phantramdonggop * ThueNK /100);
					System.out.println(phantramdonggop);
					System.out.println(phantramdonggop);
					 
					
					
					if(dem== bien ){
						tienthuenk= ThueNK -tongcong;
					} 
					
					tongcong=tongcong+ tienthuenk;
					
					 query=" INSERT INTO ERP_THUENHAPKHAU_CHITIET (THUENHAPKHAU_FK,SANPHAM_FK,SOLUONG,THUESUAT,THUENHAPKHAU) "+
					 	   " VALUES ("+ thueCurrent +","+rsdata.getString("SANPHAM_FK")+","+soluongsp+","+ ptThueNK.replace(",", "")+","+tienthuenk+")";
					 	if(!db.update(query))//Luu vào bảng ERP_THUENHAPKHAU_HOADONNCC
						{
							 System.out.println("Khong the cap nhat " +query);
						}
					 
				}
				rsdata.close();
			}
			
		}catch(Exception er){
			er.printStackTrace();
		}
	}

	private static void CapNhatKho(){
		dbutils db=new dbutils();
 
		 try{
			 	NumberFormat formatter = new DecimalFormat("#######.###"); 
			 	
			 	String query = "delete sanpham_tmp ";
	    	    db.update(query);
 
				query="SELECT PK_SEQ as khoid FROM ERP_KHOTT where pk_seq in (100018) ";
	    	   
				 ResultSet rs=db.get(query);
				 while (rs.next()){
				 
			    	 	//Cập nhật booked  trước  
					 		CapnhatBooked(rs.getString("khoid"),db);
				/*	 query= 	 " insert into sanpham_tmp  SELECT SANPHAM_FK FROM ERP_KHOTT_SANPHAM KHO " +
							     " INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=KHO.SANPHAM_FK   " +
							     " where     khott_fk="+rs.getString("khoid") + "   AND SP.LOAISANPHAM_FK= ";*/
					 		
		 		query=  " insert into sanpham_tmp "+
						" SELECT SANPHAM_FK FROM ERP_KHOTT_SANPHAM KHO   "+
						" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=KHO.SANPHAM_FK "+    
						" where     khott_fk  =100018  ";
		 		 
		 		 db.update(query);
    	    
			     String[] param = new  String[3];
			     param[0] =rs.getString("khoid");
			     param[1] ="2015-01-01";
			     param[2] ="2015-04-31";
						     
			      ResultSet   tongHopNXT = db.getRsByPro("REPORT_XUATNHAPTON_TT", param);
				  while (tongHopNXT.next()){
						     double tondau = tongHopNXT.getDouble("TONDAUKY");
						     double soluongnhap = tongHopNXT.getDouble("TONGNHAP");
						     double soluongxuat = tongHopNXT.getDouble("TONGXUAT");
						     double soluongton = tondau + soluongnhap - soluongxuat;
						      
						     double soluongkho=0;
						  
 
						    	// System.out.println("So Luong chenh lenh sanpham: "+tongHopNXT.getString("PK_SEQ")+"_  kho : "+rs.getString("khoid")+"  soluongton"+soluongton+",soluongkho "+ soluongkho);
					 
						    	 if(soluongton > 0){
						    		  
						    		 query=" update erp_khott_sanpham set soluong="+formatter.format(soluongton)+",available="+soluongton+" - booked  where sanpham_fk="+tongHopNXT.getString("PK_SEQ")+"  and khott_fk= "+rs.getString("khoid");
						    		 if(!db.update(query)){
						    			 System.out.println("Không thành công : "+query);
						    		 }
						    		 query="	select * from erp_khott_sp_chitiet where sanpham_fk="+tongHopNXT.getString("PK_SEQ")+"  and khott_fk= "+rs.getString("khoid");
						    		 ResultSet rs1=db.get(query);
						    		 
						    		 if(rs1.next()){
						    			 	//lấy số lượng tổng
						    			 
						    			 	query="select sum(soluong ) as soluong from erp_khott_sp_chitiet where sanpham_fk="+tongHopNXT.getString("PK_SEQ")+"  and khott_fk= "+rs.getString("khoid");
						    			 	ResultSet rssum=db.get(query);
						    			 	rssum.next();
						    			 	if(rssum.getDouble("soluong") > soluongton ) {
						    			 		// 
						    			 		double soluongiam=rssum.getDouble("soluong")- soluongton;
						    			 		query=" select *, (SOLUONG-BOOKED ) AS SOLUONGCOTHEGIAM from erp_khott_sp_chitiet " +
						    			 			  " where soluong > 0  and  sanpham_fk="+tongHopNXT.getString("PK_SEQ")+"  and khott_fk= "+rs.getString("khoid");
						    			 		
						    			 		ResultSet rskhoct=db.get(query);
						    			 		
						    			 		while (soluongiam > 0 && rskhoct.next()){
						    			 			double soluongtru=0;
						    			 			if(rskhoct.getDouble("SOLUONGCOTHEGIAM") >soluongiam ){
						    			 				soluongtru=soluongiam;
						    			 				soluongiam =0;
						    			 			}else{
						    			 				soluongtru=rskhoct.getDouble("SOLUONGCOTHEGIAM");
						    			 				soluongiam=soluongiam-rskhoct.getDouble("SOLUONGCOTHEGIAM");
						    			 			}
						    			 			 query= " update erp_khott_sp_chitiet set soluong=soluong-"+ soluongtru+" " +
						    			 			 		" where solo='"+rskhoct.getString("solo")+"' and  sanpham_fk="+tongHopNXT.getString("PK_SEQ")+"  and khott_fk= "+rs.getString("khoid");
						    			 			
						    			 			 if(!db.update(query)){
										    			 System.out.println("Không thành công : "+query);
										    		 }
						    			 			 
						    			 			 query= " update erp_khott_sp_chitiet set AVAILABLE=soluong- BOOKED " +
					    			 			 			" where solo='"+rskhoct.getString("solo")+"' and  sanpham_fk="+tongHopNXT.getString("PK_SEQ")+"  and khott_fk= "+rs.getString("khoid");
						    			 			
						    			 			 if(!db.update(query)){
										    			 System.out.println("Không thành công : "+query);
										    		 }
						    			 			 
						    			 		}
						    			 		rskhoct.close();
						    			 		
						    			 		
						    			 	}else if( Double.parseDouble(formatter.format(rssum.getDouble("soluong"))) <Double.parseDouble(formatter.format( soluongton) )  ){
						    			 		double soluonthem=soluongton-rssum.getDouble("soluong");
						    			 		// insert thêm lo 0 đúng bằng số lượng cần thêm
						    			 		 query= " insert into erp_khott_sp_chitiet (khott_fk,sanpham_fk,soluong,booked,available,solo,bin,ngaysanxuat,ngayhethan,ngaynhapkho,giachiphinl,giachiphikhac,giatheolo) "+
						    			 		 		" values ("+rs.getString("khoid")+"	,"+tongHopNXT.getString("PK_SEQ")+",	"+formatter.format(soluonthem)+",	0.000,	"+formatter.format(soluonthem)+"	,'Lo_2014-09-01',	100000,'2013-12-01','2016-12-01','2014-01-01',	0	,0	,0)";
						    			 		 if(!db.update(query)){
									    		       System.out.println("Không thành công : "+query);
									    		 }
						    			 	}
						    			 	
						    			 	
						    			 
						    		 }else{
						    			 // không có bảng chi tiết thì insert vào kho chi tiết
						    			 query=" insert into erp_khott_sp_chitiet (khott_fk,sanpham_fk,soluong,booked,available,solo,bin,ngaysanxuat,ngayhethan,ngaynhapkho,giachiphinl,giachiphikhac,giatheolo) "+
						    				   " values ("+rs.getString("khoid")+"	,"+tongHopNXT.getString("PK_SEQ")+",	"+formatter.format(soluongton)+",	0.000,	"+formatter.format(soluongton)+"	,'Lo_2014-09-01',	100000,'2013-12-01','2016-12-01','2014-01-01',	0	,0	,0)";
						    			 if(!db.update(query)){
							    			 System.out.println("Không thành công : "+query);
							    		 }
						    			 
						    		 }
						    		 
						    	 }else{
						    		 
						    		 query=" update erp_khott_sanpham set soluong=0 ,available=0 - booked  where sanpham_fk="+tongHopNXT.getString("PK_SEQ")+"  and khott_fk= "+rs.getString("khoid");
						    		 
						    		 if(!db.update(query)){
						    			 System.out.println("Không thành công : "+query);
						    		 }
						    		 query=" update erp_khott_sp_chitiet set soluong=0 ,available=0 - booked  where sanpham_fk="+tongHopNXT.getString("PK_SEQ")+"  and khott_fk= "+rs.getString("khoid");
						    		 if(!db.update(query)){
						    			 System.out.println("Không thành công : "+query);
						    		 }

						    	 }
						      
						     
				     }
				     
				     
				 }
			rs.close();
			
			// CapNhatLechKhoTong_KhoChiTiet(db);
			
		 }catch(Exception er){
			 er.printStackTrace();
			 
		 }
	}
	

	private static void CapnhatBooked(String khoid_,dbutils db) {
		// TODO Auto-generated method stub
		try{
			String tungay="2014-08-01";
			String sql=" update khoTONG set khoTONG.BOOKED = ISNULL(tong.BOOKED,0),AVAILABLE=KHOTONG.SOLUONG- ISNULL(tong.BOOKED,0) " +  
			   "   from ERP_KHOTT_SANPHAM khoTONG full outer join " +  
			   "  ( " +  
			   "  	select KHOTT_FK, spId, MA, TEN, SUM(total.SOLUONG) as BOOKED  " +  
			   "  	from  " +  
			   "  	( " +  
			   "  		  " +  
			   "  		SELECT  c.KHOTT_FK, a.DONDATHANG_FK as SoChungTu, b.PK_SEQ as spId, b.MA, b.TEN,SUM( CASE WHEN A.DVDL_FK != B.DVDL_FK AND CAST(D.SOLUONG2 AS FLOAT) >0   " +  
			   "  		THEN A.SOLUONG * ISNULL(D.SOLUONG1, 1) / ISNULL(D.SOLUONG2, 1)    ELSE A.SOLUONG END)  AS SOLUONG  , N'Don Dat Hang' as LoaiChungTu  " +  
			   "  		FROM ERP_DONDATHANG_SP A   " +  
			   "  		INNER JOIN ERP_SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ     " +  
			   "  		INNER JOIN DONVIDOLUONG E ON B.DVDL_FK = E.PK_SEQ    " +  
			   "  		INNER JOIN ERP_DONDATHANG C ON C.PK_SEQ = A.DONDATHANG_FK    " +  
			   "  		LEFT JOIN QUYCACH D ON B.PK_SEQ = D.SANPHAM_FK  AND A.DVDL_FK = D.DVDL2_FK  AND D.DVDL1_FK=B.DVDL_FK  " +  
			   "  		where c.TRANGTHAI in ( 1, 2, 3 )  and maketoSTOCK = '1'  and    C.NGAYDAT >='"+tungay+"' " +  
			   "  		group by c.KHOTT_FK, a.DONDATHANG_FK  , b.PK_SEQ , b.MA, b.TEN " +  
			   "  		 " +  
			   "  	union all " +  
			   "  		  " +  
			   "  		select c.KHO_FK as KHOTT_FK, a.XUATKHO_FK as SoChungTu, b.PK_SEQ as spId, b.MA, b.TEN, sum(a.SOLUONG) as SOLUONG, N'Don Dat Hang' as LoaiChungTu  " +  
			   "  		from ERP_XUATKHO_SANPHAM a inner join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ " +  
			   "  		inner join ERP_XUATKHO c on a.XUATKHO_FK = c.PK_SEQ " +  
			   "  		where c.TRANGTHAI = '0' and c.DONDATHANG_FK in ( select PK_SEQ from ERP_DonDatHang where MakeToStock = '0' ) " +  
			   "  		and c.NGAYCHOT  >='"+tungay+"' " +  
			   "  		group by c.KHO_FK, a.XUATKHO_FK, b.PK_SEQ, b.MA, b.TEN " +  
			   "  	union all " +  
			   "  		  " +  
			   "  		select c.KhoXuat_FK as KHOTT_FK, a.CHUYENKHO_FK as SoChungTu, b.PK_SEQ as spId, b.MA, b.TEN, sum(a.SOLUONGXUAT) as SOLUONG, N'Chuyen kho' as LoaiChungTu  " +  
			   "  		from ERP_CHUYENKHO_SANPHAM a inner join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ " +  
			   "  				inner join ERP_CHUYENKHO c on a.CHUYENKHO_FK = c.PK_SEQ " +  
			   "  		where a.CHUYENKHO_FK in ( select PK_SEQ from ERP_CHUYENKHO where TRANGTHAI in ( 0, 1, 2 ) ) " +  
			   "  		and c.NGAYCHUYEN >='"+tungay+"' " +  
			   "  		group by c.KhoXuat_FK, a.CHUYENKHO_FK, b.PK_SEQ, b.MA, b.TEN " +  
			   "  	union all " +  
			   "  		  " +  
			   "  		select a.KHOTT_FK, a.MUAHANG_FK as SoChungTu, b.PK_SEQ as spId, b.MA, b.TEN, sum(a.SOLUONG) as SOLUONG, N'Cong Ty Tra Hang' as LoaiChungTu  " +  
			   "  		from ERP_MUAHANG_SP a inner join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ " +  
			   "  		where a.MUAHANG_FK in ( select PK_SEQ from ERP_MUAHANG mh where TRANGTHAI in ( 0, 1 ) and TYPE = '1'  and mh.NGAYMUA >='"+tungay+"') " +  
			   "  		 " +  
			   "  		group by a.KHOTT_FK, a.MUAHANG_FK, b.PK_SEQ, b.MA, b.TEN " +  
			   "  	union all " +  
			   "  		  " +  
			   "  		select a.KHO_FK as khoTT_FK, a.PK_SEQ as SoChungTu, a.SANPHAM_FK as spId, b.MA, b.TEN, sum(a.SOLUONG) as SOLUONG, N'Xuat doi quy cach' as LoaiChungTu  " +  
			   "  		from ERP_XUATDOIQUYCACH a inner join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ " +  
			   "  		where a.TRANGTHAI = 0 and a.NGAY >= '"+tungay+"' " +  
			   "  		group by a.KHO_FK, a.PK_SEQ, a.SANPHAM_FK, b.MA, b.TEN " +  
			   "  	union all " +  
			   "  		 " +  
			   "  		select a.KHO_FK as khoTT_FK, a.PK_SEQ as SoChungTu, a.SANPHAM_FK as spId, b.MA, b.TEN, sum(a.SOLUONG) as SOLUONG, N'Bo san pham' as LoaiChungTu  " +  
			   "  		from ERP_BOSANPHAM a inner join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ " +  
			   "  		where a.TRANGTHAI = 0 and a.NGAY >= '"+tungay+"'  " +  
			   "  		group by a.KHO_FK, a.PK_SEQ, a.SANPHAM_FK, b.MA, b.TEN " +  
			   "  	union ALL " +  
			   "  		  " +  
			   "  		SELECT B.KHOSANXUAT_FK AS KHOTT_FK ,0 ,ISNULL(A.VATTUTT_FK,A.VATTU_FK) AS SANPHAM_FK,SP.MA,SP.TEN , " +  
			   "  		SUM( CASE WHEN A.VATTUTT_FK IS NULL THEN A.SOLUONG ELSE A.SOLUONGTT END ) -ISNULL( SUM(GIAM_BOOKED),0) AS SOLUONG , N'LỆNH SẢN XUẤT ' AS NGHIEPVU " +  
			   "  		FROM	  ERP_LENHSANXUAT_BOM_GIAY A  INNER JOIN ERP_LENHSANXUAT_GIAY B ON A.LENHSANXUAT_FK=B.PK_SEQ   " +  
			   "  		INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=ISNULL(A.VATTUTT_FK,A.VATTU_FK) " +  
			   "  		WHERE CHECKKHO='1' AND B.TRANGTHAI <7 and  B.NGAYBATDAU >='"+tungay+"' " +  
			   "  		GROUP BY B.KHOSANXUAT_FK ,ISNULL(A.VATTUTT_FK,A.VATTU_FK),SP.MA,SP.TEN " +  
			   "  		HAVING SUM( CASE WHEN A.VATTUTT_FK IS NULL THEN A.SOLUONG ELSE A.SOLUONGTT END ) -ISNULL( SUM(GIAM_BOOKED),0)    >0 " +  
			   "  		 " +  
			   "  	UNION ALL " +  
			   "   " +  
			   "  	 " +  
			   "  		SELECT B.KHOXUAT_FK,0,A.SANPHAM_FK,SP.MA,SP.TEN, A.SOLUONGNHAN  ,N'YÊU CẦU NGUYÊN LIỆU' AS NGHIEPVU " +  
			   "  		FROM ERP_YEUCAUNGUYENLIEU_SANPHAM A    " +  
			   "  		INNER JOIN ERP_YEUCAUNGUYENLIEU B ON A.YEUCAUNGUYENLIEU_FK=B.PK_SEQ  " +  
			   "  		INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=A.SANPHAM_FK " +  
			   "  		WHERE B.TRANGTHAI= 1 AND A.SOLUONGNHAN >0	and B.NGAYYEUCAU >='"+tungay+"' " +  
			   "  			 " +  
			   "  	)  " +  
			   "  	total   " +  
			   "  	where KHOTT_FK is not null " +  
			   "  	group by KHOTT_FK, spId, MA, TEN " +  
			   "  ) " +  
			   "  tong  on  tong.KHOTT_FK = khoTONG.KHOTT_FK  " +  
			   "  and tong.spId = khoTONG.SANPHAM_FK  " +  
			   "  WHERE  khoTONG.KHOTT_FK = "+khoid_+"  " +  
			   "  and   ABS( ISNULL(khoTONG.BOOKED,0) - ISNULL(tong.BOOKED,0)) >0.01 ";
			if(!db.update(sql)){
				System.out.println("không thanh công");
			}
			
			//cập nhật kho chi tiết theo kho
			
			sql="update ERP_KHOTT_SP_CHITIET set BOOKED =0 where KHOTT_FK="+khoid_;
			if(!db.update(sql)){
				System.out.println("không thanh công");
			}
			// cho nay anh vua sua them tí nưa 
			sql="  SELECT KHOTT_FK ,SPID,SOLO ,SUM(SOLUONG) AS SOLUONG FROM ( " +  
					   "  	SELECT C.KHOXUAT_FK AS KHOTT_FK , B.PK_SEQ AS SPID,  " +  
					   "  	SUM(A.SOLUONGXUAT) AS SOLUONG, A.SOLO   " +  
					   "  	FROM ERP_CHUYENKHO_SANPHAM A INNER JOIN ERP_SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ   " +  
					   "  	INNER JOIN ERP_CHUYENKHO C ON A.CHUYENKHO_FK = C.PK_SEQ   " +  
					   "  	WHERE A.CHUYENKHO_FK IN ( SELECT PK_SEQ FROM ERP_CHUYENKHO     " +  
					   "  	WHERE TRANGTHAI IN ( 0, 1, 2 ) ) AND C.NGAYCHUYEN >= '2014-08-01' AND C.KHOXUAT_FK =   " +  khoid_ +  
					   "  	GROUP BY C.KHOXUAT_FK,  B.PK_SEQ,  A.SOLO   " +  
					   "  	UNION ALL  " +  
					   "  	SELECT  DQC.KHO_FK,  DQC_CT.SANPHAM_FK, DQC_CT.SOLUONG, DQC_CT.SOLO  " +  
					   "  	FROM ERP_XUATDOIQUYCACH_CHITIET DQC_CT  " +  
					   "  	INNER JOIN ERP_XUATDOIQUYCACH DQC ON DQC.PK_SEQ = DQC_CT.DOIQUYCACH_FK  " +  
					   "  	WHERE DQC.TRANGTHAI=0 AND DQC.NGAY >='2014-08-01' AND DQC.KHO_FK= " +khoid_ +  
					   "  ) AS A  GROUP BY KHOTT_FK ,SPID,SOLO " ; 
			ResultSet rs=db.get(sql);
			
			while (rs.next()){
				//update kho chi tiết 
				sql= " UPDATE  ERP_KHOTT_Sp_chitiet SET SOLUONG=SOLUONG+"+rs.getDouble("SOLUONG") +", BOOKED = "+rs.getDouble("SOLUONG")+"" +
				   " WHERE KHOTT_FK ="+rs.getString("KHOTT_FK")+" AND SANPHAM_FK = "+rs.getString("spId") +" AND RTRIM(LTRIM(SOLO))='"+rs.getString("SOLO").trim()+"'";
				if(!db.update(sql)){
					System.out.println("không thanh công");
				}
				
			}
			
		}catch(Exception er){
			er.printStackTrace();
		}
	}


	/*private static void CapnhatBooked(String khoid_,dbutils db) {
		// TODO Auto-generated method stub
		try{
			String tungay="2014-08-01";
			String sql=" update khoTONG set khoTONG.BOOKED = ISNULL(tong.BOOKED,0),AVAILABLE=KHOTONG.SOLUONG- ISNULL(tong.BOOKED,0) " +  
			   "   from ERP_KHOTT_SANPHAM khoTONG full outer join " +  
			   "  ( " +  
			   "  	select KHOTT_FK, spId, MA, TEN, SUM(total.SOLUONG) as BOOKED  " +  
			   "  	from  " +  
			   "  	( " +  
			   "  		  " +  
			   "  		SELECT  c.KHOTT_FK, a.DONDATHANG_FK as SoChungTu, b.PK_SEQ as spId, b.MA, b.TEN,SUM( CASE WHEN A.DVDL_FK != B.DVDL_FK AND CAST(D.SOLUONG2 AS FLOAT) >0   " +  
			   "  		THEN A.SOLUONG * ISNULL(D.SOLUONG1, 1) / ISNULL(D.SOLUONG2, 1)    ELSE A.SOLUONG END)  AS SOLUONG  , N'Don Dat Hang' as LoaiChungTu  " +  
			   "  		FROM ERP_DONDATHANG_SP A   " +  
			   "  		INNER JOIN ERP_SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ     " +  
			   "  		INNER JOIN DONVIDOLUONG E ON B.DVDL_FK = E.PK_SEQ    " +  
			   "  		INNER JOIN ERP_DONDATHANG C ON C.PK_SEQ = A.DONDATHANG_FK    " +  
			   "  		LEFT JOIN QUYCACH D ON B.PK_SEQ = D.SANPHAM_FK  AND A.DVDL_FK = D.DVDL2_FK  AND D.DVDL1_FK=B.DVDL_FK  " +  
			   "  		where c.TRANGTHAI in ( 1, 2, 3 )  and maketoSTOCK = '1'  and    C.NGAYDAT >='"+tungay+"' " +  
			   "  		group by c.KHOTT_FK, a.DONDATHANG_FK  , b.PK_SEQ , b.MA, b.TEN " +  
			   "  		 " +  
			   "  	union all " +  
			   "  		  " +  
			   "  		select c.KHO_FK as KHOTT_FK, a.XUATKHO_FK as SoChungTu, b.PK_SEQ as spId, b.MA, b.TEN, sum(a.SOLUONG) as SOLUONG, N'Don Dat Hang' as LoaiChungTu  " +  
			   "  		from ERP_XUATKHO_SANPHAM a inner join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ " +  
			   "  		inner join ERP_XUATKHO c on a.XUATKHO_FK = c.PK_SEQ " +  
			   "  		where c.TRANGTHAI = '0' and c.DONDATHANG_FK in ( select PK_SEQ from ERP_DonDatHang where MakeToStock = '0' ) " +  
			   "  		and c.NGAYCHOT  >='"+tungay+"' " +  
			   "  		group by c.KHO_FK, a.XUATKHO_FK, b.PK_SEQ, b.MA, b.TEN " +  
			   "  	union all " +  
			   "  		  " +  
			   "  		select c.KhoXuat_FK as KHOTT_FK, a.CHUYENKHO_FK as SoChungTu, b.PK_SEQ as spId, b.MA, b.TEN, sum(a.SOLUONGXUAT) as SOLUONG, N'Chuyen kho' as LoaiChungTu  " +  
			   "  		from ERP_CHUYENKHO_SANPHAM a inner join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ " +  
			   "  				inner join ERP_CHUYENKHO c on a.CHUYENKHO_FK = c.PK_SEQ " +  
			   "  		where a.CHUYENKHO_FK in ( select PK_SEQ from ERP_CHUYENKHO where TRANGTHAI in ( 0, 1, 2 ) ) " +  
			   "  		and c.NGAYCHUYEN >'"+tungay+"' " +  
			   "  		group by c.KhoXuat_FK, a.CHUYENKHO_FK, b.PK_SEQ, b.MA, b.TEN " +  
			   "  	union all " +  
			   "  		  " +  
			   "  		select a.KHOTT_FK, a.MUAHANG_FK as SoChungTu, b.PK_SEQ as spId, b.MA, b.TEN, sum(a.SOLUONG) as SOLUONG, N'Cong Ty Tra Hang' as LoaiChungTu  " +  
			   "  		from ERP_MUAHANG_SP a inner join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ " +  
			   "  		where a.MUAHANG_FK in ( select PK_SEQ from ERP_MUAHANG mh where TRANGTHAI in ( 0, 1 ) and TYPE = '1'  and mh.NGAYMUA >='"+tungay+"') " +  
			   "  		 " +  
			   "  		group by a.KHOTT_FK, a.MUAHANG_FK, b.PK_SEQ, b.MA, b.TEN " +  
			   "  	union all " +  
			   "  		  " +  
			   "  		select a.KHO_FK as khoTT_FK, a.PK_SEQ as SoChungTu, a.SANPHAM_FK as spId, b.MA, b.TEN, sum(a.SOLUONG) as SOLUONG, N'Xuat doi quy cach' as LoaiChungTu  " +  
			   "  		from ERP_XUATDOIQUYCACH a inner join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ " +  
			   "  		where a.TRANGTHAI = 0 and a.NGAY >= '"+tungay+"' " +  
			   "  		group by a.KHO_FK, a.PK_SEQ, a.SANPHAM_FK, b.MA, b.TEN " +  
			   "  	union all " +  
			   "  		 " +  
			   "  		select a.KHO_FK as khoTT_FK, a.PK_SEQ as SoChungTu, a.SANPHAM_FK as spId, b.MA, b.TEN, sum(a.SOLUONG) as SOLUONG, N'Bo san pham' as LoaiChungTu  " +  
			   "  		from ERP_BOSANPHAM a inner join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ " +  
			   "  		where a.TRANGTHAI = 0 and a.NGAY >= '"+tungay+"'  " +  
			   "  		group by a.KHO_FK, a.PK_SEQ, a.SANPHAM_FK, b.MA, b.TEN " +  
			   "  	union ALL " +  
			   "  		  " +  
			   "  		SELECT B.KHOSANXUAT_FK AS KHOTT_FK ,0 ,ISNULL(A.VATTUTT_FK,A.VATTU_FK) AS SANPHAM_FK,SP.MA,SP.TEN , " +  
			   "  		SUM( CASE WHEN A.VATTUTT_FK IS NULL THEN A.SOLUONG ELSE A.SOLUONGTT END ) -ISNULL( SUM(GIAM_BOOKED),0) AS SOLUONG , N'LỆNH SẢN XUẤT ' AS NGHIEPVU " +  
			   "  		FROM	  ERP_LENHSANXUAT_BOM_GIAY A  INNER JOIN ERP_LENHSANXUAT_GIAY B ON A.LENHSANXUAT_FK=B.PK_SEQ   " +  
			   "  		INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=ISNULL(A.VATTUTT_FK,A.VATTU_FK) " +  
			   "  		WHERE CHECKKHO='1' AND B.TRANGTHAI <7 and  B.NGAYBATDAU >='"+tungay+"' " +  
			   "  		GROUP BY B.KHOSANXUAT_FK ,ISNULL(A.VATTUTT_FK,A.VATTU_FK),SP.MA,SP.TEN " +  
			   "  		HAVING SUM( CASE WHEN A.VATTUTT_FK IS NULL THEN A.SOLUONG ELSE A.SOLUONGTT END ) -ISNULL( SUM(GIAM_BOOKED),0)    >0 " +  
			   "  		 " +  
			   "  	UNION ALL " +  
			   "   " +  
			   "  	 " +  
			   "  		SELECT B.KHOXUAT_FK,0,A.SANPHAM_FK,SP.MA,SP.TEN, A.SOLUONGNHAN  ,N'YÊU CẦU NGUYÊN LIỆU' AS NGHIEPVU " +  
			   "  		FROM ERP_YEUCAUNGUYENLIEU_SANPHAM A    " +  
			   "  		INNER JOIN ERP_YEUCAUNGUYENLIEU B ON A.YEUCAUNGUYENLIEU_FK=B.PK_SEQ  " +  
			   "  		INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=A.SANPHAM_FK " +  
			   "  		WHERE B.TRANGTHAI= 1 AND A.SOLUONGNHAN >0	and B.NGAYYEUCAU >='"+tungay+"' " +  
			   "  			 " +  
			   "  	)  " +  
			   "  	total   " +  
			   "  	where KHOTT_FK is not null " +  
			   "  	group by KHOTT_FK, spId, MA, TEN " +  
			   "  ) " +  
			   "  tong  on  tong.KHOTT_FK = khoTONG.KHOTT_FK  " +  
			   "  and tong.spId = khoTONG.SANPHAM_FK  " +  
			   "  WHERE  khoTONG.KHOTT_FK = "+khoid_+"  " +  
			   "  and   ABS( ISNULL(khoTONG.BOOKED,0) - ISNULL(tong.BOOKED,0)) >0.01 ";
			if(!db.update(sql)){
				System.out.println("không thanh công");
			}
			
			//cập nhật kho chi tiết theo kho
			
			sql="update ERP_KHOTT_SP_CHITIET set BOOKED =0 where KHOTT_FK="+khoid_;
			if(!db.update(sql)){
				System.out.println("không thanh công");
			}
			sql="  SELECT KHOTT_FK ,SPID,SOLO ,SUM(SOLUONG) AS SOLUONG FROM ( " +  
			   "  	SELECT C.KHOXUAT_FK AS KHOTT_FK , B.PK_SEQ AS SPID,  " +  
			   "  	SUM(A.SOLUONGXUAT) AS SOLUONG, A.SOLO   " +  
			   "  	FROM ERP_CHUYENKHO_SANPHAM A INNER JOIN ERP_SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ   " +  
			   "  	INNER JOIN ERP_CHUYENKHO C ON A.CHUYENKHO_FK = C.PK_SEQ   " +  
			   "  	WHERE A.CHUYENKHO_FK IN ( SELECT PK_SEQ FROM ERP_CHUYENKHO     " +  
			   "  	WHERE TRANGTHAI IN ( 0, 1, 2 ) ) AND C.NGAYCHUYEN >= '2014-08-01' AND C.KHOXUAT_FK =   " +  khoid_ +  
			   "  	GROUP BY C.KHOXUAT_FK,  B.PK_SEQ,  A.SOLO   " +  
			   "  	UNION ALL  " +  
			   "  	SELECT  DQC.KHO_FK,  DQC_CT.SANPHAM_FK, DQC_CT.SOLUONG, DQC_CT.SOLO  " +  
			   "  	FROM ERP_XUATDOIQUYCACH_CHITIET DQC_CT  " +  
			   "  	INNER JOIN ERP_XUATDOIQUYCACH DQC ON DQC.PK_SEQ = DQC_CT.DOIQUYCACH_FK  " +  
			   "  	WHERE DQC.TRANGTHAI=0 AND DQC.NGAY >='2014-08-01' AND DQC.KHO_FK= " +khoid_ +  
			   "  ) AS A " +  
			   "  GROUP BY KHOTT_FK ,SPID,SOLO";
			ResultSet rs=db.get(sql);
			
			while (rs.next()){
				//update kho chi tiết 
				sql= " UPDATE  ERP_KHOTT_Sp_chitiet SET SOLUONG=SOLUONG+"+rs.getDouble("SOLUONG") +", BOOKED = "+rs.getDouble("SOLUONG")+"" +
				   " WHERE KHOTT_FK ="+rs.getString("KHOTT_FK")+" AND SANPHAM_FK = "+rs.getString("spId") +" AND RTRIM(LTRIM(SOLO))='"+rs.getString("SOLO").trim()+"'";
				if(!db.update(sql)){
					System.out.println("không thanh công");
				}
				
			}
			
		}catch(Exception er){
			er.printStackTrace();
		}
	}*/


	private static void CapNhatLechKhoTong_KhoChiTiet(dbutils db) {
		try{
			NumberFormat formatter = new DecimalFormat("#######.###");
			
			String query=	" select khott_fk,sanpham_fk,soluong , isnull((select sum(soluong) from erp_khott_sp_chitiet b  "+
							" where a.sanpham_fk =b.sanpham_fk and a.khott_fk=b.khott_fk ),0) as tongsoluongct  from erp_khott_sanpham a where soluong  <> "+ 
							" isnull((select sum(soluong) from erp_khott_sp_chitiet b  "+
							" where a.sanpham_fk =b.sanpham_fk and a.khott_fk=b.khott_fk ),0) ";
			
			ResultSet rs1=db.get(query);
			while (rs1.next()){
				
				double soluong=rs1.getDouble("soluong");
				double soluongkhoct=rs1.getDouble("tongsoluongct");
				
				if(soluong >soluongkhoct ){
					
					double soluongthem=soluong-soluongkhoct;
					// thêm 1 số lô nữa cho kho chi tiết
					query=" SELECT * FROM erp_khott_sp_chitiet WHERE SOLO='0' AND  SANPHAM_FK= "+rs1.getString("sanpham_fk")+" AND KHOTT_FK= "+rs1.getString("khott_fk") +" order by BOOKED";
					
					ResultSet rscheck=db.get(query);
						if(rscheck.next()){
								 query=  " update erp_khott_sp_chitiet set soluong=soluong + "+formatter.format(soluongthem)+" ,available =available +"+formatter.format(soluongthem)+" " +
										 "  WHERE SOLO='0' AND  SANPHAM_FK= "+rs1.getString("sanpham_fk")+" AND KHOTT_FK= "+rs1.getString("khott_fk");

    						}else{
    							 query=  " insert into erp_khott_sp_chitiet (khott_fk,sanpham_fk,soluong,booked,available,solo,bin,ngaysanxuat,ngayhethan,ngaynhapkho,giachiphinl,giachiphikhac,giatheolo) "+
		   				 	 			 " values ("+rs1.getString("khott_fk")+"	,"+rs1.getString("sanpham_fk")+",	"+formatter.format(soluongthem)+",	0.000,	"+formatter.format(soluongthem)+"	,'0',	100000,'2013-12-01','2016-12-01','2014-01-01',	0	,0	,0)";

							}
						 if(!db.update(query)){
			    			 System.out.println("Không thành công : "+query);
			    		 }
					 
				}else{
					// phải trừ đi bớt kho chi tiết  nhưng phải tính đến việc booked của kho chi tiết.
					query=" SELECT * FROM erp_khott_sp_chitiet WHERE SOLUONG >0   AND  SANPHAM_FK= "+rs1.getString("sanpham_fk")+" AND KHOTT_FK= "+rs1.getString("khott_fk");

					ResultSet rskhoct=db.get(query);
					double soluongiam=soluongkhoct-soluong;
					
					while (soluongiam > 0 && rskhoct.next()){
			 			double soluongtru=0;
			 			if(rskhoct.getDouble("available") >soluongiam ){
			 				soluongtru=soluongiam;
			 				soluongiam =0;
			 			}else{
			 				soluongtru=rskhoct.getDouble("available");
			 				soluongiam=soluongiam-rskhoct.getDouble("available");
			 				
			 			}
			 			 query= " update erp_khott_sp_chitiet set soluong=soluong-"+ formatter.format(soluongtru)+",available= available-"+formatter.format(soluongtru)+"   " +
			 			 		" where solo='"+rskhoct.getString("solo")+"' AND  SANPHAM_FK= "+rs1.getString("sanpham_fk")+" AND KHOTT_FK= "+rs1.getString("khott_fk");
			 			 
			 			 if(!db.update(query)){
			    			 System.out.println("Không thành công : "+query);
			    		 }
			 			 
			 		}
			 		rskhoct.close();

				}
				
			}
			rs1.close();
			
			
		}catch(Exception er){
			er.printStackTrace();
		}
		
	}

	private static void ImportData_NLTieuHao( ) {
		// TODO Auto-generated method stub
		dbutils db = new dbutils();
		try{ 
 
			FileInputStream fstream = null;
			Workbook workbook = new Workbook();
			
			fstream = new FileInputStream("E:\\newtoyo_excel\\LenhSanXuat\\LSXTHANG32014_PHUONGLAN_NEW.xlsx");
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet("Dinh Muc");
	
			Cells cells = worksheet.getCells();
			
			String userId = "100307"; // User geso
			String cpId ;
			String ncpTen;
			String tongtienAVAT;
			Cell cell;
			String Id;
			String SOPO="";
			String SOPO_BK="";
			int i=0;
			List<IDanhmucvattu_SP> spList = new ArrayList<IDanhmucvattu_SP>();
			 
			
			 String masp="";
			 IErpLenhsanxuat lsx =new ErpLenhsanxuat();
			 IDanhmucvattu_SP vattu=new Danhmucvattu_SP();
			 double soluongtong=0;
			 float SOLUONGBTP_DAUKY =0;
			 String chuoima="";
			 Hashtable<String, Float> htb=new Hashtable<String, Float>();
			 ///for (int row = 12; row <=570 ; row ++){
			 for (int row = 149; row <=150 ; row ++){
				  cell = cells.getCell("A" + row);
				  //System.out.println("So Po : "+SOPO);
				  	if(i==0){
					  SOPO_BK=cell.getStringValue();;
					  lsx =new ErpLenhsanxuat();
					  
					 }
				    SOPO = cell.getStringValue();
				    
				    
				    lsx.setCongtyId("100005");
				    lsx.setNgaydukien("2014-03-01");
				    lsx.setNgaytao("2014-03-01");
				    lsx.setDvkdId("100000");
				    lsx.setNhamayId("100000");
				    lsx.setUserId("100307");
				    
					 	if(!SOPO.equals(SOPO_BK)){
					 		lsx.setghichu("PO__"+SOPO_BK);
					 		String query=" SELECT TOP 1 PK_SEQ ,CONGDOAN_FK,LSX.khosanxuat_fk FROM ERP_LENHSANXUAT_GIAY LSX "+ 
					 		 			  " INNER JOIN ERP_LENHSANXUAT_CONGDOAN_GIAY CD ON LSX.PK_SEQ=CD.LENHSANXUAT_FK "+
					 		 			  " WHERE  DIENGIAI='"+"PO__"+SOPO_BK+"'  ORDER BY THUTU ";
					 		 
					 		 ResultSet rs=db.get(query);
					 		 if(rs.next()){
					 			 lsx =new ErpLenhsanxuat();
					 			 lsx.setCongDoanCurrent(rs.getString("CONGDOAN_FK"));
					 			 lsx.setKhottId(rs.getString("khosanxuat_fk"));
					 			 lsx.setId(rs.getString("PK_SEQ"));
					 			 lsx.setListVattuThem(spList);
					 			 if(spList.size() >0){
					 				 Create_DMVT_BOM(lsx,db);
					 			 }else{
					 				 System.out.println(SOPO +" chưa có dữ liệu tiêu hao ");
					 			 }
					 		 }
					 		SOPO_BK=SOPO;
					 		soluongtong=0;
					 		SOLUONGBTP_DAUKY =0;
					 		spList.clear();
					 		
					 	}
					  
					 	 cell = cells.getCell("G" + row);
					 	 float SoluongCuon=0;
					 	 try{
					 		   SoluongCuon= Float.parseFloat(cell.getStringValue().replace(",",""));
					 		   
					 	 }catch (Exception er){
					 		 //er.printStackTrace();
					 	 }
					 	   
					 	 if(SoluongCuon >0  ){
					 		 
					 	 
					 		cell = cells.getCell("BM" + row);
						 	  String MA=cell.getStringValue();
						 	  
					 		cell = cells.getCell("E" + row);
						 	  String nguongoc=cell.getStringValue();
					 		 cell = cells.getCell("C" + row);
						 	  String loainl=cell.getStringValue();
					 		  cell = cells.getCell("D" + row);
						 	   masp=cell.getStringValue();
						 	  lsx.setSpma(masp);
						 	//  System.out.println("row: "+row+"Ma sp  : "+masp);
						  
						 	  
						 	 cell = cells.getCell("K" + row);
						 	 
							 float dai=0; 
						 	 try{
						 		dai=Float.parseFloat(cell.getStringValue().replace(",",""));
						 	 }catch(Exception er){}
						 	 
						 	 
						 	 cell = cells.getCell("J" + row);
						 	
						 	 float rong=0; 
						 	 try{
						 	  rong=Float.parseFloat(cell.getStringValue().replace(",",""));
						 	 }catch(Exception er){}
						 	 
						 	 
						 	 cell = cells.getCell("I" + row);
							 float dinhluong=0; 
						 	 try{
						 		dinhluong=Float.parseFloat(cell.getStringValue().replace(",",""));
						 	 }catch(Exception er){}
						 	 
						 	String query=""; 
						 	 if(loainl.trim().equals("Hồ")){
						 	   query="select sp.pk_seq from erp_Sanpham  sp left join donvidoluong dvdl on dvdl.pk_seq=sp.dvdl_fk " +
						 	   		" where ma='"+masp+"' " ;
						 	 }else{
						 		 if(MA.length() > 0){
						 			 query="select  pk_seq from erp_Sanpham  where MA='"+MA+"'";
						 			 
						 		 }else{
						 		 query=		" select sp.pk_seq,NguonGoc,MA from erp_Sanpham  sp left join donvidoluong dvdl on dvdl.pk_seq=sp.dvdl_fk " +
						 	   				" where ma='"+masp+"' and dai="+dai+" and rong="+rong+" and dinhluong="+dinhluong +" " +
						 	   				" \n and ( nguongoc like N'%"+nguongoc+"%' or MA  like N'%"+nguongoc+"%')  " ;
						 		 }
						 		 
						 	 }
						 	 ResultSet  rs=db.get(query);
						 	 int k=0;
						 	 String Idsp="";
						 	 while (rs.next()){
						 		   Idsp=rs.getString("pk_seq");
						 		   k++;
						 	 }
						 	 if(k>1){
						 		System.out.println("bi double nhieu san pham tren  row : "+row+ " ----k : "+k +"   -------------- Sp Id :"+Idsp +" sql : " +query);
						 		
						 	 }
						 	 
						 	 rs.close();
						 	 if(k== 0){
						 		System.out.println("row  : "+row +" chưa xác định được quy cách  --"+masp + ("--" +rong)+ ("--" +dai)+ ("--" +dinhluong) + "--" + nguongoc);
						 		
					 		 }
						 	 if(k==1){/*
						 		 
						 		 if(htb.containsKey(Idsp)){
						 			 float soluongcu=htb.get(Idsp);
						 			 htb.put(Idsp, SoluongCuon+soluongcu );
						 			 //System.out.println("Trùng SP  row  : "+row +" KHông đủ tồn kho --- "+Idsp+" --"+masp + ("--" +rong)+ ("--" +dai)+ ("--" +dinhluong) + "--" + nguongoc  +"  So Luong : "+    "--" +SoluongCuon );
 
						 		 }else{
						 			 
						 		 
						 		String[] param = new String[3];
							    param[0] ="100001";
							    param[1] ="2014-01-01";
							    param[2] ="2014-03-31";
							    query = " delete sanpham_tmp ";
							    
							    db.update(query);
							    
							    query = " insert into sanpham_tmp select pk_seq from erp_sanpham where pk_seq= "+Idsp;
							    db.update(query);
							    
							    ResultSet   tongHopNXT = db.getRsByPro("REPORT_XUATNHAPTON_TT", param);
							    
							    tongHopNXT.next();
							    double tondau = tongHopNXT.getDouble("TONDAUKY");
								 
								double soluongnhap = tongHopNXT.getDouble("TONGNHAP");
								 
								double soluongxuat = tongHopNXT.getDouble("TONGXUAT");
								 
								double soluongton = tondau + soluongnhap - soluongxuat;
								
								if(soluongton <SoluongCuon){
									
									//System.out.println( "Không đủ số lượng tồn kho  ");
									//System.out.println("row  : "+row +" KHông đủ tồn kho --- "+Idsp+" --"+masp + ("--" +rong)+ ("--" +dai)+ ("--" +dinhluong) + "--" + nguongoc  +"  So Luong : "+soluongton + "--" +SoluongCuon );
									htb.put(Idsp, SoluongCuon);
								}
								}						 		 
						 		 
								
						 	 */}
						 	 
						 	 if(Idsp.length() >0){
						 		 		 
						 		vattu=new Danhmucvattu_SP();
						 		vattu.setIdVT(Idsp);	
						 		vattu.setSoLuong(""+SoluongCuon);
						 		spList.add(vattu);
							 	   
						 	 }
						 	 
					 	 }
					 	
					  
					 	
					 	i++;
			}
			 	lsx.setghichu("PO__"+SOPO_BK);
		 		String query=" SELECT TOP 1 PK_SEQ ,CONGDOAN_FK,LSX.khosanxuat_fk FROM ERP_LENHSANXUAT_GIAY LSX "+ 
		 		 			  " INNER JOIN ERP_LENHSANXUAT_CONGDOAN_GIAY CD ON LSX.PK_SEQ=CD.LENHSANXUAT_FK "+
		 		 			  " WHERE  DIENGIAI='"+"PO__"+SOPO_BK+"'  ORDER BY THUTU ";
		 		 
		 		 ResultSet rs=db.get(query);
		 		 if(rs.next()){
		 			 lsx =new ErpLenhsanxuat();
		 			 lsx.setCongDoanCurrent(rs.getString("CONGDOAN_FK"));
		 			 lsx.setKhottId(rs.getString("khosanxuat_fk"));
		 			 lsx.setId(rs.getString("PK_SEQ"));
		 			 lsx.setListVattuThem(spList);
		 			 if(spList.size() >0){
		 				 Create_DMVT_BOM(lsx,db);
		 			 }else{
		 				 System.out.println(SOPO +" chưa có dữ liệu tiêu hao ");
		 			 }
		 		 }
			
			
			System.out.println(chuoima);
			fstream.close();
			}catch(Exception er){
				er.printStackTrace();
			}
	}

	private static void PhanBoTuDongKeo( ) {
		try{
			String sanphamid="102215";
			double soluongnl=4200;
			dbutils db = new dbutils();
			String query=" SELECT SUM(B.SOLUONG) as tong FROM ERP_LENHSANXUAT_GIAY A INNER JOIN ERP_LENHSANXUAT_BOM_GIAY B ON A.PK_SEQ=B.LENHSANXUAT_FK "+ 
						 " WHERE PK_SEQ >=102009 AND PK_SEQ< =102153 AND B.VATTU_FK NOT IN  (  "+sanphamid+") AND LENHSANXUAT_FK IN  "+
						 " ( "+
						 " SELECT A.PK_SEQ FROM ERP_LENHSANXUAT_GIAY A INNER JOIN ERP_LENHSANXUAT_BOM_GIAY B ON A.PK_SEQ=B.LENHSANXUAT_FK  "+
						 " WHERE PK_SEQ >=102009 AND PK_SEQ< =102153 AND B.VATTU_FK IN (  "+sanphamid+")  "+
						 " )  ";
			ResultSet rs=db.get(query);
			double soluongtong=0;
			if(rs.next()){
				soluongtong=rs.getDouble("tong");
			}
			rs.close();
			
			query=" SELECT SUM(B.SOLUONG) AS TONG,PK_SEQ FROM ERP_LENHSANXUAT_GIAY A INNER JOIN ERP_LENHSANXUAT_BOM_GIAY B ON A.PK_SEQ=B.LENHSANXUAT_FK "+ 
				" WHERE PK_SEQ >=102009 AND PK_SEQ< =102153 AND B.VATTU_FK NOT IN  (  "+sanphamid+") AND LENHSANXUAT_FK IN "+ 
				" ( "+
				" SELECT A.PK_SEQ FROM ERP_LENHSANXUAT_GIAY A INNER JOIN ERP_LENHSANXUAT_BOM_GIAY B ON A.PK_SEQ=B.LENHSANXUAT_FK "+ 
				" WHERE PK_SEQ >=102009 AND PK_SEQ< =102153 AND B.VATTU_FK IN (  "+sanphamid+") "+ 
				" ) "+
				" GROUP BY PK_SEQ  ";
			
			NumberFormat formatter = new DecimalFormat("#######.###");
			rs=db.get(query);
			String lsxid="";
			while (rs.next()){
				lsxid=rs.getString("PK_SEQ");
				double phantram= rs.getDouble("TONG") /soluongtong;
				 double soluong_ = phantram*soluongnl;
				/*query="update  ERP_LENHSANXUAT_BOM_GIAY set soluong="+formatter.format(soluong_)+" where lenhsanxuat_fk="+lsxid+" and vattu_fk="+sanphamid;
				System.out.println(" không thể cập nhật : "+query);
				if(!db.update(query)){
					System.out.println(" không thể cập nhật : "+query);
				}
				
				// Cập nhật lại tiêu hao
*/				 
				 query=	" update b "+
						" set b.soluong= "+formatter.format(soluong_) +
						" from ERP_LENHSANXUAT_TIEUHAO b  "+
						" inner join ERP_TIEUHAONGUYENLIEU a  on a.pk_seq=b.tieuhaonguyenlieu_fk "+
						" where sanpham_fk="+sanphamid+" and a.lenhsanxuat_fk   = "+lsxid;
				 if(db.updateReturnInt(query) !=1){
					System.out.println(" Cap Nhat Khac So 1 : "+query);
				}
				
				 
			}
			rs.close();
			 
			
			
		}catch(Exception er){
			er.printStackTrace();
		}
	}
	
	
	
	private static void PhanBoTuDongKeo_T2( ) {
		try{
			String sanphamid="105055";
			double soluongnl=     800    ;
			dbutils db = new dbutils();
			String query=" SELECT SUM(B.SOLUONG) as tong FROM ERP_LENHSANXUAT_GIAY A INNER JOIN ERP_LENHSANXUAT_BOM_GIAY B ON A.PK_SEQ=B.LENHSANXUAT_FK "+ 
						 " WHERE ngaybatdau like '2014-02%'and nhamay_fk=100000 AND B.VATTU_FK NOT IN  (  "+sanphamid+") AND LENHSANXUAT_FK IN  "+
						 " ( "+
						 " SELECT A.PK_SEQ FROM ERP_LENHSANXUAT_GIAY A INNER JOIN ERP_LENHSANXUAT_BOM_GIAY B ON A.PK_SEQ=B.LENHSANXUAT_FK  "+
						 " WHERE ngaybatdau like '2014-02%'and nhamay_fk=100000 AND B.VATTU_FK IN (  "+sanphamid+")  "+
						 " )  ";
			ResultSet rs=db.get(query);
			double soluongtong=0;
			if(rs.next()){
				soluongtong=rs.getDouble("tong");
			}
			rs.close();
			
			query=" SELECT SUM(B.SOLUONG) AS TONG,PK_SEQ FROM ERP_LENHSANXUAT_GIAY A INNER JOIN ERP_LENHSANXUAT_BOM_GIAY B ON A.PK_SEQ=B.LENHSANXUAT_FK "+ 
				" WHERE ngaybatdau like '2014-02%'and nhamay_fk=100000 AND B.VATTU_FK NOT IN  (  "+sanphamid+") AND LENHSANXUAT_FK IN "+ 
				" ( "+
				" SELECT A.PK_SEQ FROM ERP_LENHSANXUAT_GIAY A INNER JOIN ERP_LENHSANXUAT_BOM_GIAY B ON A.PK_SEQ=B.LENHSANXUAT_FK "+ 
				" WHERE ngaybatdau like '2014-02%'and nhamay_fk=100000 AND B.VATTU_FK IN (  "+sanphamid+") "+ 
				" ) "+
				" GROUP BY PK_SEQ  ";
			System.out.println(query);
			NumberFormat formatter = new DecimalFormat("#######.###");
			rs=db.get(query);
			String lsxid="";
			while (rs.next()){
				lsxid=rs.getString("PK_SEQ");
				 double phantram= rs.getDouble("TONG") /soluongtong;
				 double soluong_ = phantram*soluongnl;
				 // ta ra tieu hao voi số lượng phân bổ
				 
				 IErpLenhsanxuat lsx=new ErpLenhsanxuat();
				 lsx.setId(lsxid);
				 lsx.setCongDoanCurrent("100001");
				 lsx.setKhottId("100001");
				 List<IDanhmucvattu_SP> spList = new ArrayList<IDanhmucvattu_SP>();
				 IDanhmucvattu_SP vattu=new Danhmucvattu_SP();
				
				 vattu.setIdVT(sanphamid);
				 vattu.setSoLuong(soluong_+"");
				 spList.add(vattu);
				 lsx.setListVattuThem(spList);
				 Create_DMVT_BOM_T2(lsx,db);
				 
				 
			}
			rs.close();
			 
			
			
		}catch(Exception er){
			er.printStackTrace();
		}
	}
	

	
	private static void ImportData( ) {
		
		
		// TODO Auto-generated method stub
		dbutils db = new dbutils();
		try{ 
 
			FileInputStream fstream = null;
			Workbook workbook = new Workbook();
			
			fstream = new FileInputStream("E:\\newtoyo_excel\\LenhSanXuat\\LSXTHANG32014_PHUONGLAN_NEW.xlsx");
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet("Chi tiet NVL");
	
			Cells cells = worksheet.getCells();
			
			String userId = "100307"; // User geso
			String cpId ;
			String ncpTen;
			String tongtienAVAT;
			Cell cell;
			String Id;
			String SOPO="";
			String SOPO_BK="";
			int i=0;
			List<IDanhmucvattu_SP> spList = new ArrayList<IDanhmucvattu_SP>();
			List<ISanPhamSanXuat> listSpSx = new ArrayList<ISanPhamSanXuat>();
			
			List<ISanPhamSanXuat> listSpSx_BTP = new ArrayList<ISanPhamSanXuat>();
			
			ISanPhamSanXuat spsx;
			 String masp="";
			 IErpLenhsanxuat lsx =new ErpLenhsanxuat();
			 double soluongtong=0;
			 float SOLUONGBTP_DAUKY =0;
			 String chuoima="";
			// for (int row = 107; row <=755 ; row ++){
			for (int row = 266; row <=267 ; row ++){
				  cell = cells.getCell("A" + row);
				 // System.out.println("So Po : "+SOPO);
				  	if(i==0){
					  SOPO_BK=cell.getStringValue();;
					  lsx =new ErpLenhsanxuat();
					 }
				    SOPO = cell.getStringValue();
			 
				    lsx.setCongtyId("100005");
				    lsx.setNgaydukien("2014-03-01");
				    lsx.setNgaytao("2014-03-01");
				    lsx.setDvkdId("100000");
				    lsx.setNhamayId("100000");
				    lsx.setUserId("100307");
				    
				    
					 	if(!SOPO.equals(SOPO_BK)){
					 		
					 		//tạo lệnh sản xuất
					 		 lsx.setghichu("PO__"+SOPO_BK);
					 		String query=" select PK_SEQ from Erp_KichBanSanXuat_Giay where MASANPHAM='"+lsx.getSpma()+"' and TrangThai=1";
					 		ResultSet rsgetkichban=db.get(query);
					 		String kichbanId="NULL";
					 		int t=0;
					 		while (rsgetkichban.next()){
					 			kichbanId=rsgetkichban.getString("PK_SEQ");
					 			// System.out.println("row  : "+(row-1) +"   xac đinh được kich ban " +kichbanId);
					 			t++;
						 	 } 
					 		rsgetkichban.close();
					 		if(t==0){
					 			 System.out.println("row  : "+(row-1) +" không xac đinh được kich ban " +query);
					 			
					 		}else if(t>1){
					 			 System.out.println("row  : "+(row-1) +" Cos nhieu kich  ban " +query);
					 			 chuoima=chuoima+",'"+lsx.getSpma()+"'";
					 		}
					 		
					 	/*	query="SELECT COUNT(KichBanSanXuat_FK) as count FROM Erp_KichBanSanXuat_CongDoanSanXuat_Giay WHERE KichBanSanXuat_FK= "+kichbanId;
					 		ResultSet rscount=db.get(query);
					 		if(rscount.next()){
					 			if(rscount.getInt("count") !=2){
					 				 System.out.println(rscount.getInt("count") + "row  : "+(row-1) +" kich ban nay co nhieu cong doan :  " +query );
					 			}
					 		}
					 		rscount.close();*/
					 		
					 		lsx.setKbsxId(kichbanId);
					 		lsx.setSoluong(soluongtong+"");
					 		
					 		 boolean bien= createLsx( db,lsx,listSpSx,listSpSx_BTP,SOLUONGBTP_DAUKY );
					 		 if(!bien){
					 			 System.out.println("Khong The Tao Lenh San Xuat Cho Po : " +SOPO_BK);
					 		 } 
					 		 
					 		 
					 		lsx =new ErpLenhsanxuat();
					 		SOPO_BK=SOPO;
					 		listSpSx_BTP.clear();
					 		listSpSx.clear();
					 		soluongtong=0;
					 		SOLUONGBTP_DAUKY =0;
					 		
					 		
					 	}
					 
					 	
					 	 
					 	 cell = cells.getCell("G" + row);
					 	 String mau=cell.getStringValue();
					 	 
					 	 cell = cells.getCell("L" + row);
					 	 float SoluongCuon=0;
					 	 try{
					 		   SoluongCuon= Float.parseFloat(cell.getStringValue().replace(",",""));
					 		   
					 	 }catch (Exception er){
					 		 //er.printStackTrace();
					 	 }
					 	cell = cells.getCell("M" + row);
					 	float trongluong =0;
					 	 try{
					 		trongluong= Float.parseFloat(cell.getStringValue().replace(",",""));
					 		   
					 	 }catch (Exception er){
					 		 //er.printStackTrace();
					 	 }
					 	//System.out.println("Trong luong : "+trongluong);
					 	 
					 	cell = cells.getCell("U" + row);
					 	
					 	 try{
					 		SOLUONGBTP_DAUKY=SOLUONGBTP_DAUKY+ Float.parseFloat(cell.getStringValue().replace(",",""));
					 		   
					 	 }catch (Exception er){
					 		 //er.printStackTrace();
					 	 }
					 	 
					 	 
					 	 
					 	 if(SoluongCuon >0 ||trongluong >0 ){
					 		 
					 		 
					 		cell = cells.getCell("C" + row);
					 		  String   donvi=cell.getStringValue();
					 		  
					 		  
					 		  cell = cells.getCell("H" + row);
					 		  String   kind_H=cell.getStringValue();
					 		 
					 		  cell = cells.getCell("B" + row);
						 	   masp=cell.getStringValue();
						 	  lsx.setSpma(masp);
						 	//  System.out.println("row: "+row+"Ma sp  : "+masp);
						 	 cell = cells.getCell("C" + row);
						 	 String dvt=cell.getStringValue();
						 	  
						 	  
						 	 cell = cells.getCell("D" + row);
						 	 
							 float dai=0; 
						 	 try{
						 		dai=Float.parseFloat(cell.getStringValue());
						 	 }catch(Exception er){}
						 	 
						 	 
						 	 cell = cells.getCell("E" + row);
						 	
						 	 float rong=0; 
						 	 try{
						 	  rong=Float.parseFloat(cell.getStringValue());
						 	 }catch(Exception er){}
						 	 
						 	 
						 	 cell = cells.getCell("F" + row);
							 float dinhluong=0; 
						 	 try{
						 		dinhluong=Float.parseFloat(cell.getStringValue());
						 	 }catch(Exception er){}
						 	 String query="";
						 	 if(kind_H.equals("TP")){
						 	   query="select sp.pk_seq from erp_Sanpham  sp INNER join donvidoluong dvdl on dvdl.pk_seq=sp.dvdl_fk " +
						 	   		" where ma='"+masp+"' and dai="+dai+" and rong="+rong+" and dinhluong="+dinhluong +" and mau=N'"+mau+"' and dvdl.donvi like N'"+donvi+"'" ;
						 	 }else{
						 		query= "select PK_SEQ from erp_sanpham where ma= (select 'S'+ma from erp_maketoan where pk_seq in   (select MAKETOAN_FK from ERP_SANPHAM where MA='"+masp+"' ))";
						 	 }
						 	 ResultSet rs=db.get(query);
						 	 int k=0;
						 	 String Idsp="";
						 	 while (rs.next()){
						 		   Idsp=rs.getString("pk_seq");
						 		   k++;
						 	 }
						 	 if(k>1){
						 		 System.out.println("bi double nhieu san pham tren  row : "+row+ " ----k : "+k +"   -------------- Sp Id :"+Idsp);
						 	 }
						 	 
						 	 rs.close();
						 	 if(	k== 0){
						 		 System.out.println("row  : "+row +" không xac đinh được san pham " +query);
					 		 }
						 	 
						 	 if(Idsp.length() >0){
						 		 spsx =new  	 SanPhamSanXuat(); 
						 		//System.out.println("Don vi tinh nek "+dvt.trim());
						 		 if(dvt.toUpperCase().trim().equals("KG")){
						 			spsx.setSoluong(""+trongluong);
						 			//System.out.println("Vao trong luong");
						 		 }else{
						 			//System.out.println("Vao so luong");
						 			 spsx.setSoluong(""+SoluongCuon);
						 		 }
						 		 spsx.setIdSp(Idsp);
							 	 query="select PK_SEQ from ERP_DANHMUCVATTU where MAVATTU='"+masp+"' and CHOPHEPSUA=0 ";
							 	 String	BomId="NULL";
							 	 ResultSet rsgetbom=db.get(query);
							 	 if(rsgetbom.next()){
							 		 BomId=rsgetbom.getString("PK_SEQ");
							 	 }else{
							 		 System.out.println("row  : "+row +" không xac đinh được BOM ");
							 	 }
							 	 rsgetbom.close();
							 	 spsx.setBom(BomId);
							 	 if(kind_H.equals("TP")){
							 		listSpSx.add(spsx);
 
							 		 if(dvt.trim().equals("kg")){
							 			 	soluongtong=soluongtong+ trongluong;
								 		 }else{
								 			soluongtong=soluongtong+ SoluongCuon;
								 		 }
							 	 }else{
							 		listSpSx_BTP.add(spsx);
							 		//System.out.println(" Trong Luong nek  : " + trongluong);
							 		//System.out.println(" So luong : " + spsx.getSoluong());
							 	 }
							 	  
						 	 }
						 	 
					 	 }
					 	
					  
					 	
					 	i++;
			}
			
			

				 lsx.setghichu("PO__"+SOPO);
	 		//tạo lệnh sản xuất
	 		
	 		String query="select PK_SEQ from Erp_KichBanSanXuat_Giay where MASANPHAM='"+lsx.getSpma()+"' and TrangThai=1";
	 		ResultSet rsgetkichban=db.get(query);
	 		String kichbanId="NULL";
	 		int t=0;
	 		while (rsgetkichban.next()){
	 			kichbanId=rsgetkichban.getString("PK_SEQ");
	 			// System.out.println("row  : "+(row-1) +"   xac đinh được kich ban " +kichbanId);
	 			t++;
		 	 } 
	 		rsgetkichban.close();
	 		lsx.setKbsxId(kichbanId);
	 		lsx.setSoluong(soluongtong+"");
	 		
	 		 boolean bien= createLsx( db,lsx,listSpSx,listSpSx_BTP,SOLUONGBTP_DAUKY );
	 		 if(!bien){
	 			 System.out.println("Khong The Tao Lenh San Xuat Cho Po : " +SOPO);
	 		 } 
	 		  
			System.out.println(chuoima);
			fstream.close();
			}catch(Exception er){
				er.printStackTrace();
			}
 
	}

	private static void ImportData_TieuHaoThang2( ) {
		// TODO Auto-generated method stub
		dbutils db = new dbutils();
		try{ 
 
			FileInputStream fstream = null;
			Workbook workbook = new Workbook();
			
			fstream = new FileInputStream("E:\\newtoyo_excel\\LenhSanXuat\\Sheet tieu hao nguyen lieu LSX thang 2 - Anh Tuong.xlsx");
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet("Sheet1");
			Cells cells = worksheet.getCells();
			
			String userId = "100307"; // User geso
			String cpId ;
			String ncpTen;
			String tongtienAVAT;
			Cell cell;
			String Id;
			String SOPO="";
			String SOPO_BK="";
			int i=0;
			List<IDanhmucvattu_SP> spList = new ArrayList<IDanhmucvattu_SP>();
			 
			
			 String masp="";
			 IErpLenhsanxuat lsx =new ErpLenhsanxuat();
			 IDanhmucvattu_SP vattu=new Danhmucvattu_SP();
			 double soluongtong=0;
			 float SOLUONGBTP_DAUKY =0;
			 String chuoima="";
			 Hashtable<String, Float> htb=new Hashtable<String, Float>();
			 ///for (int row = 12; row <=570 ; row ++){
			 for (int row = 5; row <=567 ; row ++){
				  cell = cells.getCell("B" + row);
				 // System.out.println("So Po : "+SOPO);
				  	if(i==0){
					  SOPO_BK=cell.getStringValue();;
					  lsx =new ErpLenhsanxuat();
					  
					 }
				    SOPO = cell.getStringValue();
				    
				    cell = cells.getCell("AM" + row);
				 	 float SoluongCuon=0;
				 	 try{
				 		   SoluongCuon= Float.parseFloat(cell.getStringValue().replace(",",""));
				 		   
				 	 }catch (Exception er){
				 		 //er.printStackTrace();
				 	 }
				 	 
				    
				    lsx.setCongtyId("100005");
				    lsx.setNgaydukien("2014-03-01");
				    lsx.setNgaytao("2014-03-01");
				    lsx.setDvkdId("100000");
				    lsx.setNhamayId("100000");
				    lsx.setUserId("100307");
				    if(SoluongCuon>0){
				    	
				    		
				    	//cell = cells.getCell("I" + row);
						  
						   
					//	String  SOPO_cu =cell.getStringValue();
						
						String query="";
						/*query="select pk_seq from erp_lenhsanxuat_giay where pk_seq="+SOPO +" and diengiai like N'%"+SOPO_cu+"%'";
						
						 ResultSet rs1=db.get(query);
				 		 if(rs1.next()){
				 			 
				 		 }else{
				 			System.out.println("Lenh san xuat khong xác dinh   row : "+row +"  ---- "+query);
				 		 }
							 */
						  	
				    	
					 	if(!SOPO.equals(SOPO_BK)){
					 		lsx.setghichu("PO__"+SOPO_BK);
					 		  query=" SELECT TOP 1 PK_SEQ ,CONGDOAN_FK,LSX.khosanxuat_fk FROM ERP_LENHSANXUAT_GIAY LSX "+ 
		 		 			  " INNER JOIN ERP_LENHSANXUAT_CONGDOAN_GIAY CD ON LSX.PK_SEQ=CD.LENHSANXUAT_FK "+
		 		 			  " WHERE  pk_seq="+SOPO_BK+"  ORDER BY THUTU ";
		 		 
					 		 ResultSet rs=db.get(query);
					 		 if(rs.next()){
					 			 lsx =new ErpLenhsanxuat();
					 			 lsx.setCongDoanCurrent(rs.getString("CONGDOAN_FK"));
					 			 lsx.setKhottId(rs.getString("khosanxuat_fk"));
					 			 lsx.setId(rs.getString("PK_SEQ"));
					 			 lsx.setListVattuThem(spList);
					 			 if(spList.size() >0){
					 				Create_DMVT_BOM_T2(lsx,db);
					 			 }else{
					 				 System.out.println(SOPO +" chưa có dữ liệu tiêu hao ");
					 			 }
					 		 }
					 		 
					 		 
					 		spList.clear();
					 		SOPO_BK=SOPO;
					 		soluongtong=0;
					 		SOLUONGBTP_DAUKY =0;
					 		spList.clear();
					 		
					 	}
					  
					 	
					 	   
					 	 if(SoluongCuon >0  ){
					 		 
					 	 
					 		cell = cells.getCell("C" + row);
						 	  String MA=cell.getStringValue();
						 	  
					 		cell = cells.getCell("E" + row);
						 	  String nguongoc=cell.getStringValue();
					 		 
						   
						 	 
						 	//  System.out.println("row: "+row+"Ma sp  : "+masp);
						  
						 	  
						 	 cell = cells.getCell("H" + row);
						 	 
							 float dai=0; 
						 	 try{
						 		dai=Float.parseFloat(cell.getStringValue().replace(",",""));
						 	 }catch(Exception er){}
						 	 
						 	 
						 	 cell = cells.getCell("G" + row);
						 	
						 	 float rong=0; 
						 	 try{
						 	  rong=Float.parseFloat(cell.getStringValue().replace(",",""));
						 	 }catch(Exception er){}
						 	 
						 	 
						 	 cell = cells.getCell("F" + row);
							 float dinhluong=0; 
						 	 try{
						 		dinhluong=Float.parseFloat(cell.getStringValue().replace(",",""));
						 	 }catch(Exception er){}
						 	 
						 	  query=""; 
						 	 
						 		 query=		" select sp.pk_seq,NguonGoc,MA from erp_Sanpham  sp left join donvidoluong dvdl on dvdl.pk_seq=sp.dvdl_fk " +
						 	   				" where MA='"+MA+"' " ;
						 	   						//"and dai="+dai+" and rong="+rong+" and dinhluong="+dinhluong ;
						 	   			//	" \n and ( nguongoc like N'%"+nguongoc+"%' or MA  like N'%"+nguongoc+"%')  " ;
						 	 
						 	 ResultSet  rs=db.get(query);
						 	 int k=0;
						 	 String Idsp="";
						 	 while (rs.next()){
						 		   Idsp=rs.getString("pk_seq");
						 		   k++;
						 	 }
						 	 if(k>1){
						 		System.out.println("bi double nhieu san pham tren  row : "+row+ " ----k : "+k +"   -------------- Sp Id :"+Idsp +" sql : " +query);
						 	 }
						 	 
						 	 rs.close();
						 	 if(k== 0){
						 		System.out.println("row  : "+row +" chưa xác định được quy cách  --"+masp + ("--" +rong)+ ("--" +dai)+ ("--" +dinhluong) + "--" + nguongoc);
						 		
					 		 }
						 	 if(k==1){/*
						 		 
						 		 if(htb.containsKey(Idsp)){
						 			 float soluongcu=htb.get(Idsp);
						 			 htb.put(Idsp, SoluongCuon+soluongcu );
						 			 //System.out.println("Trùng SP  row  : "+row +" KHông đủ tồn kho --- "+Idsp+" --"+masp + ("--" +rong)+ ("--" +dai)+ ("--" +dinhluong) + "--" + nguongoc  +"  So Luong : "+    "--" +SoluongCuon );
 
						 		 }else{
						 			 
						 		 
						 		String[] param = new String[3];
							    param[0] ="100001";
							    param[1] ="2014-01-01";
							    param[2] ="2014-02-28";
							    query = " delete sanpham_tmp ";
							    
							    db.update(query);
							    
							    query = " insert into sanpham_tmp select pk_seq from erp_sanpham where pk_seq= "+Idsp;
							    db.update(query);
							    
							    ResultSet   tongHopNXT = db.getRsByPro("REPORT_XUATNHAPTON_TT", param);
							    
							    tongHopNXT.next();
							    double tondau = tongHopNXT.getDouble("TONDAUKY");
								 
								double soluongnhap = tongHopNXT.getDouble("TONGNHAP");
								 
								double soluongxuat = tongHopNXT.getDouble("TONGXUAT");
								 
								double soluongton = tondau + soluongnhap - soluongxuat;
								
								if(SoluongCuon- soluongton >0.01 ){
									
									//System.out.println( "Không đủ số lượng tồn kho  ");
									System.out.println("row  : "+row +" KHông đủ tồn kho --- "+Idsp+" --"+MA + ("--" +rong)+ ("--" +dai)+ ("--" +dinhluong) + "--" + nguongoc  +"  So Luong : "+soluongton + "--" +SoluongCuon );
									htb.put(Idsp, SoluongCuon);
								}
								}						 		 
						 		 
								
						 	 */}
						 	 
						 	 if(Idsp.length() >0){
						 		 		 
						 		vattu=new Danhmucvattu_SP();
						 		vattu.setIdVT(Idsp);	
						 		vattu.setSoLuong(""+SoluongCuon);
						 		spList.add(vattu);
						 	 }
					 	 }

				    }
					 	i++;
			}
			 lsx.setghichu("PO__"+SOPO_BK);
	 		  String query=" SELECT TOP 1 PK_SEQ ,CONGDOAN_FK,LSX.khosanxuat_fk FROM ERP_LENHSANXUAT_GIAY LSX "+ 
 			  " INNER JOIN ERP_LENHSANXUAT_CONGDOAN_GIAY CD ON LSX.PK_SEQ=CD.LENHSANXUAT_FK "+
 			  " WHERE  pk_seq="+SOPO_BK+"  ORDER BY THUTU ";
 
	 		 ResultSet rs=db.get(query);
	 		 if(rs.next()){
	 			 lsx =new ErpLenhsanxuat();
	 			 lsx.setCongDoanCurrent(rs.getString("CONGDOAN_FK"));
	 			 lsx.setKhottId(rs.getString("khosanxuat_fk"));
	 			 lsx.setId(rs.getString("PK_SEQ"));
	 			 lsx.setListVattuThem(spList);
	 			 if(spList.size() >0){
	 				  Create_DMVT_BOM_T2(lsx,db);
	 			 }else{
	 				 System.out.println(SOPO +" chưa có dữ liệu tiêu hao ");
	 			 }
	 		 }
			
			
			System.out.println(chuoima);
			fstream.close();
			}catch(Exception er){
				er.printStackTrace();
			}
	}
	

	private static boolean Create_DMVT_BOM_T2(IErpLenhsanxuat lsx, dbutils db) {
		// TODO Auto-generated method stub
		try{
			db.getConnection().setAutoCommit(false);
			List <IDanhmucvattu_SP> ListVattuThem=lsx.getListVattuThem();
			String	query=" INSERT INTO ERP_TIEUHAONGUYENLIEU (LENHSANXUAT_FK,CONGDOAN_FK,TRANGTHAI,NGUOITAO,NGAYTAO,NGUOISUA,NGAYSUA,NGAYTIEUHAO) "+ 
						  " VALUES("+lsx.getId()+"	,"+lsx.getCongDoanCurrent()+",1,	100380,	'2014-05-27'	,100380,	'2014-05-27'	,'2014-02-28')";
			System.out.println("query :" +query);
			
			if(!db.update(query))
			{
				System.out.println("Khong The Tao ERP_TIEUHAONGUYENLIEU :" +query);
				db.getConnection().rollback();
				return false;
			}
		   query = "select IDENT_CURRENT('ERP_TIEUHAONGUYENLIEU') as clId";
		   ResultSet rs =  db.get(query);
		   rs.next();
		   String tieuhaoid = rs.getString("clId");
		   rs.close();
		    
			for(int i = 0; i <  ListVattuThem.size(); i++)
			{
					IDanhmucvattu_SP vattu = ListVattuThem.get(i);
				    
					query= " INSERT INTO ERP_LENHSANXUAT_TIEUHAO (TIEUHAONGUYENLIEU_FK,SANPHAM_FK,SOLUONG,DONGIA,THANHTIEN,KHOTT_FK,LOAI)"+
			   		  " VALUES("+tieuhaoid+",	"+vattu.getIdVT()+","+vattu.getSoLuong()+"	,0.00,	0.00	,"+lsx.getKhottId()+"	,1)";
			
					System.out.println("query :" +query);
					if(!db.update(query))
					{
						System.out.println("Khong The Tao ERP_LENHSANXUAT_BOM_GIAY :" +query);
						db.getConnection().rollback();
						return false;
					}
			}
			//tạo tiêu hao nguyên liệu 
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
			
		}catch(Exception er){
			db.update("rollback");
			er.printStackTrace();
			return false;
		}
		
	}

	private static boolean Create_DMVT_BOM(IErpLenhsanxuat lsx, dbutils db) {
		// TODO Auto-generated method stub
		try{
			db.getConnection().setAutoCommit(false);
			List <IDanhmucvattu_SP> ListVattuThem=lsx.getListVattuThem();
			String	query=" INSERT INTO ERP_TIEUHAONGUYENLIEU (LENHSANXUAT_FK,CONGDOAN_FK,TRANGTHAI,NGUOITAO,NGAYTAO,NGUOISUA,NGAYSUA,NGAYTIEUHAO) "+ 
						  " VALUES("+lsx.getId()+"	,"+lsx.getCongDoanCurrent()+",1,	100380,	'2014-01-12'	,100380,	'2014-01-12'	,'2014-03-30')";
			if(!db.update(query))
			{
				System.out.println("Khong The Tao ERP_TIEUHAONGUYENLIEU :" +query);
				db.getConnection().rollback();
				return false;
			}
		   query = "select IDENT_CURRENT('ERP_TIEUHAONGUYENLIEU') as clId";
		   ResultSet rs =  db.get(query);
		   rs.next();
		   String tieuhaoid = rs.getString("clId");
		   rs.close();
		    
			for(int i = 0; i <  ListVattuThem.size(); i++)
			{
					IDanhmucvattu_SP vattu = ListVattuThem.get(i);
				     query = "insert into ERP_LENHSANXUAT_BOM_GIAY(loai,ISVATTUTHEM,lenhsanxuat_fk,congdoan_fk,khott_fk, VATTU_FK, SOLUONG,CHECKKHO)  " +
								"values ('1','1',"+lsx.getId()+","+lsx.getCongDoanCurrent()+",'"+lsx.getKhottId()+"','"+vattu.getIdVT()+"','"+vattu.getSoLuong()+"','1')";
							
					if(!db.update(query))
					{
						System.out.println("Không thể tạo mới ERP_LENHSANXUAT_BOM_GIAY: " + query);
						db.getConnection().rollback();
						return false;
					}
					
					query= " INSERT INTO ERP_LENHSANXUAT_TIEUHAO (TIEUHAONGUYENLIEU_FK,SANPHAM_FK,SOLUONG,DONGIA,THANHTIEN,KHOTT_FK,LOAI)"+
			   		  " VALUES("+tieuhaoid+",	"+vattu.getIdVT()+","+vattu.getSoLuong()+"	,0.00,	0.00	,"+lsx.getKhottId()+"	,1)";
					if(!db.update(query))
					{
						System.out.println("Khong The Tao ERP_LENHSANXUAT_BOM_GIAY :" +query);
						db.getConnection().rollback();
						return false;
					}
			}
			//tạo tiêu hao nguyên liệu 
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
			
		}catch(Exception er){
			db.update("rollback");
			er.printStackTrace();
			return false;
		}
		
	}

	private static boolean createLsx(dbutils db, IErpLenhsanxuat lsx,
			List<ISanPhamSanXuat> listSpSx, List<ISanPhamSanXuat> listSpSx_BTP, float sOLUONGBTP_DAUKY) {
		// TODO Auto-generated method stub
		try{
			
			List<ILenhSXCongDoan> ListCongdoan;
			ListCongdoan=new ArrayList<ILenhSXCongDoan>();
			db.getConnection().setAutoCommit(false);
			if(lsx.getKbsxId().length()>0){
					String sql=" select distinct sp.dvkd_fk , nm.pk_seq as nhamayid, a.thutu, a.danhmucvattu_fk ,0 as spid , "+ 
				" A.DINHLUONG_FK,isnull(B.DINHTINH,'0') as dinhtinh , " +
				" case when sp1.kiemtradinhluong ='1' and  a.danhmucvattu_fk IS   null then 1  when sp1.kiemtradinhtinh =1 and  a.danhmucvattu_fk IS   null then 1 else isnull(a.kiemdinhchatluong,'0')end  as kiemdinhchatluong  " +
				", b.pk_seq,b.diengiai,a.kichbansanxuat_fk,  "+
				" a.thutu,b.nhamay_fk,nm.manhamay,nm.tennhamay ,isnull( a.sanpham_fk,0) as sanpham_fk ,isnull(sp.ma,'') as masp,isnull(sp.ten,'') as tensp "+ 
				" from Erp_KichBanSanXuat_CongDoanSanXuat_Giay a  "+
				" inner join erp_kichbansanxuat_giay kb on kb.pk_seq= a.kichbansanxuat_fk "+ 
				" inner join Erp_CongDoanSanXuat_Giay b on a.congdoansanxuat_fk=b.pk_Seq "+ 
				" inner join erp_nhamay nm on nm.pk_seq=b.nhamay_fk  " +
				" left join  erp_danhmucvattu dmvt on dmvt.pk_seq=a.danhmucvattu_fk		 "+
				" LEFT join  erp_sanpham sp on sp.ma=dmvt.mavattu "+
				" left join erp_sanpham sp1 on sp1.ma=kb.MaSanPham "+
				" where kichbansanxuat_fk="+lsx.getKbsxId()+" and kb.MaSanPham = '"+lsx.getSpma()+"' "+
				" order by a.thutu ";
			 
			ResultSet rs=db.get(sql);
		 
			while (rs.next()){
				ILenhSXCongDoan lsxcd=new LenhSXCongDoan();
				lsxcd.setCongDoanId(rs.getString("pk_seq"))	;
				lsxcd.setDiengiai(rs.getString("diengiai"));
				lsxcd.Setkichbansanxuat(rs.getString("kichbansanxuat_fk"));
				lsxcd.setTrangthai("0");
				lsxcd.setActive("1");
				lsxcd.setNhaMayId(rs.getString("nhamay_fk"));
				lsxcd.setBomId(rs.getString("danhmucvattu_fk"));
				lsxcd.setThuTu(rs.getFloat("thutu"));
				lsxcd.setPhanXuong(rs.getString("manhamay"));
				lsxcd.setSanPham(rs.getString("masp") +"-"+rs.getString("tensp"));
				lsxcd.setMaSp(rs.getString("masp"));
				lsxcd.setSpId(rs.getString("sanpham_fk"));
				lsxcd.SetKiemDinhCL(rs.getString("kiemdinhchatluong"));
				lsxcd.setSoLuong("0");
				ListCongdoan.add(lsxcd);
				
			}
				rs.close();
				if(ListCongdoan.size()==0){
					System.out.println("KHonog lay duoc cai gi: "+ lsx.getghichu()+ ". Sql :  " +sql);
				}
			}
			
			//lấy kho sản xuất 
			String query= " select PK_SEQ,khonhannguyenlieu_fk from ERP_KHOTT where TrangThai = '1' and loai in ('1') and congty_fk = '"+lsx.getCongtyId()+"' and NHAMAY_FK="+lsx.getNhamayId();
			ResultSet rs=db.get(query);
			String khonguyenlieu="";
			// chỉ booked kho xuất,
			String khoxuat="";
			if(rs!=null){
				while (rs.next()){
						khoxuat=rs.getString("pk_seq");
						khonguyenlieu=rs.getString("khonhannguyenlieu_fk");
					
				}
			}
			rs.close();
			
			
				 query =" insert into ERP_LENHSANXUAT_GIAY(diengiai ,ngaybatdau,ngaydukienht ,kichbansanxuat_fk,congty_fk ,   soluong, nhamay_fk, trangthai, ngaytao, nguoitao, ngaysua, nguoisua,khosanxuat_fk) " +
						" values(N'"+lsx.getghichu()+"','"+lsx.getNgaydukien()+"','"+lsx.getNgaydukien()+"'," + (lsx.getKbsxId().length() >0? lsx.getKbsxId():"null") + ", '" + lsx.getCongtyId() + "',  " +lsx.getSoluong() +   ", " + lsx.getNhamayId() + ", '7', '" + lsx.getNgaytao() + "', '" + lsx.getUserId()+ "', '" +  lsx.getNgaytao() + "', '" +  lsx.getUserId()+ "',"+khoxuat+") "; 
				if(!db.update(query))
				{
					System.out.println( "Không thể tạo mới Kichbansanxuat: " + query);
					db.getConnection().rollback();
					return false;
				}
				query = "select IDENT_CURRENT('ERP_LENHSANXUAT_GIAY') as clId";
				rs =  db.get(query);
				rs.next();
				String lsxid  = rs.getString("clId");
				rs.close();
				String congdoan_fk_1="";
				String congdoan_fk="";
				if(ListCongdoan.size() >0){
					for(int i = 0; i <  ListCongdoan.size(); i++)
					{
						
						ILenhSXCongDoan congdoan= ListCongdoan.get(i);
						String trangthaicd="";
						if(congdoan.getActive().equals("1")){
							trangthaicd="0";
						}else{
							trangthaicd="2";
						}
						query="insert into ERP_LENHSANXUAT_CONGDOAN_GIAY (lenhsanxuat_fk,kichban_fk,congdoan_fk,tinhtrang,soluong,THUTU,SANPHAM_FK,DANHMUCVATTU_FK,KIEMDINHCHATLUONG,MASANPHAM,MAYSUDUNG) values " +
								" ( '"+lsxid+"','"+lsx.getKbsxId()+"','"+congdoan.getCongDoanId()+"','"+trangthaicd+"',"+congdoan.getSoLuong()+","+congdoan.getThutu()+","+congdoan.getSpid()+","+congdoan.getBomId()+",'"+congdoan.getKiemDinhCL()+"','"+congdoan.getMaSp()+"',N'"+congdoan.getMaySuDung()+"')";
						
						//System.out.println("Các Cong Doanh");
						if(!db.update(query))
						{
							System.out.println( "Không thể tạo mới Kichbansanxuat: " + query);
							db.getConnection().rollback();
							return false;
						}
						if(i==0){
							congdoan_fk_1= congdoan.getCongDoanId();
						}
						congdoan_fk=congdoan.getCongDoanId();
					}
				}
				
				for(int i = 0; i <  listSpSx.size(); i++)
				{
					ISanPhamSanXuat spsx= listSpSx.get(i);
					
					query="insert into ERP_LENHSANXUAT_SANPHAM (LENHSANXUAT_FK,SANPHAM_FK,SOLUONG,DanhMucVT_FK,COCHONGBUI,KHACHHANG_FK,CODEMAU)  values " +
							" ( '"+lsxid+"','"+spsx.getIdSp()+"','"+spsx.getSoluong()+"',"+(spsx.getBomId().length() >0?spsx.getBomId():"null")+",'"+spsx.getChongbui()+"',"+(spsx.getIdkhachhang().trim().length()==0?"null":spsx.getIdkhachhang() )+",N'"+spsx.getcodemau()+"')";
						if(!db.update(query))
						{
							System.out.println( "Không thể tạo mới Kichbansanxuat: " + query);
							db.getConnection().rollback();
							return false;
						}
					
				}
				// INSERT BÁN THÀNH PHẨM ĐẦU KỲ ERP_LENHSANXUAT_BOM_GIAY
				
				if(sOLUONGBTP_DAUKY >0){
					 	 query= "select PK_SEQ from erp_sanpham where ma= (select 'S'+ma from erp_maketoan where pk_seq in   (select MAKETOAN_FK from ERP_SANPHAM where MA='"+lsx.getSpma()+"' ))";
					 	 rs=db.get(query);
					 	 String Idsp="";
					 	 if (rs.next()){
					 		 Idsp=rs.getString("pk_seq");
					 		 query="insert into ERP_LENHSANXUAT_BOM_GIAY (LENHSANXUAT_FK,CONGDOAN_FK,CHOPHEPTHAYTHE,VATTU_FK,SOLUONG,KHOTT_FK,LOAI,ISVATTUTHEM)VALUES " +
					 		 		"("+lsxid+",100001,0,"+Idsp+","+sOLUONGBTP_DAUKY+","+khoxuat+",1,1)";
					 		if(!db.update(query))
							{
								System.out.println( "Không thể tạo mới Kichbansanxuat: " + query);
								db.getConnection().rollback();
								return false;
							}
					 	 }
				}
				
			
				// tạo nhập kho thành phẩm ,tự động tao ra bán thành phẩm và tiêu hao bán thành phẩm tự động
				query=  " INSERT INTO ERP_NHAPKHO (NGAYNHAPKHO,NGAYCHOT,SOLENHSANXUAT,NOIDUNGNHAP,KHONHAP,TRANGTHAI,NGUOITAO,NGAYTAO,NGUOISUA,NGAYSUA,congty_fk,congdoan_fk) "+ 
				" VALUES ('2014-03-30',	'2014-03-30',"+lsxid+"	,100005	,"+khoxuat+",	1,	"+lsx.getUserId()+",	'"+lsx.getNgaytao()+"',	"+lsx.getUserId()+",	'"+lsx.getNgaytao()+"'	,100005,"+congdoan_fk+") ";
		
				if(!db.update(query))
				{
					System.out.println( "Không thể tạo mới  : " + query);
					db.getConnection().rollback();
					return false;
				}
				
				query = "select IDENT_CURRENT('ERP_NHAPKHO') as clId";
				  rs =  db.get(query);
				  rs.next();
				   String nhapkho_id = rs.getString("clId");
				rs.close();
				
				for(int i = 0; i <  listSpSx.size(); i++)
				{
					ISanPhamSanXuat spsx= listSpSx.get(i);
					query="INSERT INTO ERP_NHAPKHO_SANPHAM (SONHAPKHO_FK,SANPHAM_FK,SOLUONGNHAN,SOLUONGNHAP,SOLO,NGAYHETHAN,NGAYSANXUAT,NGAYNHAPKHO)VALUES "+
					" ("+nhapkho_id+",	"+spsx.getIdSp()+",	"+spsx.getSoluong()+"	,"+spsx.getSoluong()+"	,"+lsxid+",	'2015-01-28',	'2014-03-30'	,'2014-03-30')";
					if(!db.update(query))
					{
						System.out.println( "Không thể tạo mới  : " + query);
						db.getConnection().rollback();
						return false;
					}
					
				}
				if(!TaoNhapKhoBanThanhPham(db,nhapkho_id,lsxid,lsx,khoxuat)){
					System.out.println( "Không thể tạo mới  : TaoNhapKhoBanThanhPham");
					return false;
				}
				 // tạo nhập kho bán thành phẩm cuối kỳ
				
				if(listSpSx_BTP.size() >0){
					
					
					
					query=  " INSERT INTO ERP_NHAPKHO (NGAYNHAPKHO,NGAYCHOT,SOLENHSANXUAT,NOIDUNGNHAP,KHONHAP,TRANGTHAI,NGUOITAO,NGAYTAO,NGUOISUA,NGAYSUA,congty_fk,congdoan_fk) "+ 
					" VALUES ('2014-03-30',	'2014-03-30',"+lsxid+"	,100005	,"+khoxuat+",	1,	"+lsx.getUserId()+",	'"+lsx.getNgaytao()+"',	"+lsx.getUserId()+",	'"+lsx.getNgaytao()+"'	,100005,"+congdoan_fk_1+") ";
					
					if(!db.update(query))
					{
						System.out.println( "Không thể tạo mới  : " + query);
						db.getConnection().rollback();
						return false;
					}
					  query = "select IDENT_CURRENT('ERP_NHAPKHO') as clId";
				 
					  rs =  db.get(query);
					  rs.next();
					  String   nhapkhobtp_id = rs.getString("clId");
					  rs.close();
					
					for(int i = 0; i <  listSpSx_BTP.size(); i++)
					{
						ISanPhamSanXuat spsx= listSpSx_BTP.get(i);
						query="INSERT INTO ERP_NHAPKHO_SANPHAM (SONHAPKHO_FK,SANPHAM_FK,SOLUONGNHAN,SOLUONGNHAP,SOLO,NGAYHETHAN,NGAYSANXUAT,NGAYNHAPKHO)VALUES "+
						" ("+nhapkhobtp_id+",	"+spsx.getIdSp()+",	"+spsx.getSoluong()+"	,"+spsx.getSoluong()+"	,"+lsxid+",	'2015-01-28',	'2014-03-30'	,'2014-03-30')";
						if(!db.update(query))
						{
							System.out.println( "Không thể tạo mới  : " + query);
							db.getConnection().rollback();
							return false;
						}
					}
					
					
				}
				
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
				
			return true;
		}catch(Exception err){
			err.printStackTrace();
			db.update("rollback");
			
		}
		return false;
	}

	private static boolean TaoNhapKhoBanThanhPham(dbutils db, String nhapkho_id,
			String lsxid, IErpLenhsanxuat lsx, String khoxuat) {
		// TODO Auto-generated method stub
		try{
			NumberFormat formatter = new DecimalFormat("#######.###");
			 
			 String query=  	   "    select nk.ngaynhapkho,nk.ngaychot,nk.giochot,nk.solenhsanxuat,nk.noidungnhap,nk.khonhap ,  "  +  
					       " 	nk.congty_fk,nk.congdoan_fk,  a.SANPHAM_FK, a.SOLO, a.SOLUONGNHAP, a.NGAYSANXUAT,TIENTE_FK , "  +  
						   "    a.NGAYHETHAN, a.SOLUONGNHAN AS SOLUONG, "  +  
						   "    100000 AS VITRI  from ERP_NHAPKHO_SANPHAM a   "  +  
						   "    inner join erp_nhapkho nk on nk.pk_seq=a.sonhapkho_fk "  +  
						   "    inner join erp_sanpham sp on sp.pk_seq= a.SANPHAM_FK "  +  
						   "    where a.SONHAPKHO_FK =  "  +nhapkho_id  +
						   "    and sp.loaisanpham_fk=100005 and sp.dvkd_fk=100000  ";
								
			 ResultSet  rs=db.getScrol(query);
			 String ngaynhapkho="2014-03-30";
			String NhapkhobtpId="";
			if(rs.next()) {
				 // lấy được công đoạn tạo ra bán thành phẩm của công đoạn này
				 
				String congdoan2= "100001";
				
				
				 
				// nếu là thành phẩm thì tạo ra nhập kho cho thành phẩm này 
				query =	" insert ERP_NHAPKHO (ngaynhapkho, ngaychot, SOLENHSANXUAT,CongDoan_FK, NOIDUNGNHAP, KHONHAP, TRANGTHAI, ngaytao, ngaysua, nguoitao, nguoisua, congty_fk,NHAPKHO_PARENT_FK ) " +
						" values('" +  ngaynhapkho + "', '" + ngaynhapkho + "', '" +  lsxid + "', '"+congdoan2+"',100005 ,'" +khoxuat + "', " +
						" '1', '" +lsx.getNgaytao() + "', '" + lsx.getNgaytao() + "', '" + lsx.getUserId() + "', '" + lsx.getUserId() + "', 100005,"+nhapkho_id+")";

					if(!db.update(query))
					{
						 
						db.getConnection().rollback();
						return false;
					}
					
					query = "select IDENT_CURRENT('Erp_NHAPKHO') as nkId";
					ResultSet rsDmh = db.get(query);
					if (rsDmh.next())
					{
						NhapkhobtpId = rsDmh.getString("nkId");
						rsDmh.close();
					}
			}
			
				query = "select top(1) NAM as namMax, THANGKS as thangMax from ERP_KHOASOTHANG order by NAM desc, THANGKS desc ";
				ResultSet rsthangnam = db.get(query);
				String thangKsMax = "";
				String namKsMax = "";
				while(rsthangnam.next())
				{
					thangKsMax = rsthangnam.getString("thangMax");
					namKsMax = rsthangnam.getString("namMax"); 
					
				}
				rsthangnam.close();
		 
				rs.beforeFirst();
			
				double soluongbtp =0;
				String sanphambtpid="";
				String ngaysanxuat="";
				String Ngayhethan="";
				String tiente="";
				 while (rs.next()){
					 
						  ngaysanxuat=rs.getString("ngaysanxuat");
						  Ngayhethan=rs.getString("Ngayhethan");
						  tiente=rs.getString("TIENTE_FK");
						
						String sanphamid=rs.getString("SANPHAM_FK");
						query="  select dmvt.vattu_fk,danhmuc.soluongchuan /(  "  +  
							   " SELECT TOP 1 CASE WHEN SP.DVDL_FK=100003 THEN 1 ELSE   "  +  
							   "  ISNULL(QC.SOLUONG2/ CAST(QC.SOLUONG1 AS FLOAT),0) END  "  +  
							   " FROM ERP_SANPHAM SP LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK=SP.PK_SEQ   "  +  
							   " AND QC.DVDL1_FK=SP.DVDL_FK AND QC.DVDL2_FK=100003 WHERE SP.PK_SEQ="  + sanphamid+ 
							   " ) as soluongchuan ,  "  + 
							   " dmvt.soluong / ( "  +  
							   " SELECT TOP 1 CASE WHEN SP.DVDL_FK=100003 THEN 1 ELSE   "  +  
							   "  ISNULL(QC.SOLUONG2/ CAST(QC.SOLUONG1 AS FLOAT),0) END  "  +  
							   " FROM ERP_SANPHAM SP LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK=SP.PK_SEQ   "  +  
							   " AND QC.DVDL1_FK=SP.DVDL_FK AND QC.DVDL2_FK=100003 WHERE SP.PK_SEQ=dmvt.vattu_fk "  +  
							   "  "  +  
							   " )  as soluong,vattu_fk from erp_lenhsanxuat_sanpham a "  +  
							   " inner join erp_danhmucvattu danhmuc on danhmuc.pk_seq=a.danhmucvt_fk "  +  
							   " inner join erp_danhmucvattu_vattu dmvt on dmvt.danhmucvt_fk=danhmuc.pk_seq "  +  
							   " where a.lenhsanxuat_fk="+lsxid+" and a.sanpham_fk= "+sanphamid;
						//System.out.println("querry : " +query);
							ResultSet rs_getdmvt=db.get(query);
							if(rs_getdmvt.next()){
								sanphambtpid=rs_getdmvt.getString("vattu_fk");
								
								//System.out.println( "SOLUONGNHAP : "+ rs.getDouble("SOLUONGNHAP"));
								//System.out.println( "soluong : "+ rs_getdmvt.getDouble("soluong"));
								//System.out.println( "soluongchuan : "+ rs_getdmvt.getDouble("soluongchuan"));
								 
								try{
									soluongbtp= soluongbtp+   Double.parseDouble(formatter.format( rs.getDouble("SOLUONGNHAP")* rs_getdmvt.getDouble("soluong")/rs_getdmvt.getDouble("soluongchuan")));
								}catch(Exception err){
								
								}
							}else{
								db.update("rollback");
								System.out.println( "Không thể xác định được BOM cho thành phẩm : " +rs.getString("sanpham_fk") );
								return false;
							}		
						
				 }
				 if(sanphambtpid.length() >0){	 
						 
						 
					query = "select giaton from erp_tonkhothang where thang = '" + thangKsMax + "' and nam = '" + namKsMax + "'" +
					" and sanpham_fk =  "+ sanphambtpid;
					//System.out.println("1__Lay gia ton: " + query);
					String giaTon = "0";
					ResultSet rsGia = db.get(query);
					if(rsGia != null)
					{
						if(rsGia.next())
						{
							giaTon = rsGia.getString("giaton");
						}
						rsGia.close();
					}
					soluongbtp=   Double.parseDouble(formatter.format(soluongbtp));
					 
					//query=" SELECT AVAILABLE FROM ERP_KHOTT_SANPHAM WHERE khott_fk="+this.khoId+"  and SANPHAM_FK= "+sanphambtpid;
					// lấy số lượng yêu cầu trong BOM
					//1. Không yêu cầu thì tạo ra số lượng bán thành phẩm đủ để tiêu hao.	
				    //2. Có yêu cầu BTP.Kho có đủ :
						query=" select SOLUONG  from ERP_LENHSANXUAT_BOM_GIAY where LENHSANXUAT_FK="+lsxid+" and VATTU_FK="+sanphambtpid+" and ISVATTUTHEM='1'";
						
					//System.out.println("Du Lieu Trong KHo : "+query);
						
						ResultSet rsbtpyeucau =db.get(query);
						double soluongyeucau=0;
						if( rsbtpyeucau.next()){
							soluongyeucau =rsbtpyeucau.getDouble("SOLUONG");
						} 
					    double soluongbtp_them=0;
						soluongbtp_them=soluongbtp-soluongyeucau ;
						 if(soluongbtp_them<0){
							 soluongbtp_them=0;
						 }
						 
						 //kiểm tra kho còn đủ để tiêu hao không
						 
						  
						double thanhtien=Double.parseDouble(giaTon)*soluongbtp_them;
					 
						query = " insert ERP_NHAPKHO_SANPHAM(SONHAPKHO_FK, SANPHAM_FK, SOLUONGNHAN, SOLUONGNHAP, SOLO, NGAYSANXUAT, NGAYHETHAN,NgayNhapKho,DONGIA, THANHTIEN, DONGIAVIET, THANHTIENVIET, TIENTE_FK) " +
								" values (" + NhapkhobtpId + ", '"+sanphambtpid+"', '" + formatter.format(soluongbtp_them)   + "', " + formatter.format(soluongbtp_them)  + ", 'BTP', '" +  ngaysanxuat + "','"+ Ngayhethan 
							   +"','"+ ngaynhapkho+"', " + giaTon + ", " + thanhtien+ ", " +
							    giaTon + ", " + thanhtien  + " , 100000 ) " ;
							
						if(!db.update(query))
						{
							System.out.println( "1.Khong the tao moi ERP_NHAPKHO: " + query);
							db.getConnection().rollback();
							return false;
						}
					}
			if(rs!=null){
				rs.close();
			}
			if(NhapkhobtpId.length() >0){
				if(!TieuHaoTuDongBanThanhPham(db,nhapkho_id,lsx)){
					System.out.println( "1.Khong the tao moi TieuHaoTuDongBanThanhPham: ");
					db.getConnection().rollback();
					return false;
				}
			}
			
			
		}catch(Exception er){
			db.update("rollback");
			er.printStackTrace();
			 
			return false;
		}
		return true;
		
	}

	private static boolean TieuHaoTuDongBanThanhPham(dbutils db,
			String idnhapkhotp, IErpLenhsanxuat lsx) {
		 
		try{
			NumberFormat formatter = new DecimalFormat("#######.###");
			String khoTieuhao_fk = "";
			String chungtutieuhaoid ="";
 
			String query=  "    select nk.ngaynhapkho,nk.ngaychot,nk.giochot,nk.solenhsanxuat,nk.noidungnhap,nk.khonhap ,  "  +  
					       " 	nk.congty_fk,nk.congdoan_fk,  a.SANPHAM_FK, a.SOLO, a.SOLUONGNHAP, a.NGAYSANXUAT,TIENTE_FK , "  +  
						   "    a.NGAYHETHAN, a.SOLUONGNHAN AS SOLUONG, "  +  
						   "    100000 AS VITRI  from ERP_NHAPKHO_SANPHAM a   "  +  
						   "    inner join erp_nhapkho nk on nk.pk_seq=a.sonhapkho_fk "  +  
						   "    inner join erp_sanpham sp on sp.pk_seq= a.SANPHAM_FK "  +  
						   "    where a.SONHAPKHO_FK =  "  +idnhapkhotp  +
						   "    and sp.loaisanpham_fk=100005  ";
			ResultSet rsphieuxk=db.getScrol(query);
			String solenhsanxuat="";
			String ngaynhapkho="";
			if(rsphieuxk.next()){
			
				  ngaynhapkho=rsphieuxk.getString("ngaynhapkho");
				String congdoan=rsphieuxk.getString("congdoan_fk");
				  solenhsanxuat=rsphieuxk.getString("solenhsanxuat");
			 
				query = " select ERP_KHOTT.pk_seq from ERP_KHOTT inner join erp_lenhsanxuat_giay lsx on lsx.congty_fk=ERP_KHOTT.congty_fk and lsx.nhamay_fk=erp_khott.nhamay_fk " +
					"  where ERP_KHOTT.TrangThai = '1' and ERP_KHOTT.LOAI = '1' and lsx.congty_fk = '"+lsx.getCongtyId()+"' and lsx.pk_seq="+solenhsanxuat ;
				
					ResultSet rsKho = db.get(query);
			
					if(rsKho.next())
					{
						khoTieuhao_fk = rsKho.getString("pk_seq");
					}
					rsKho.close();
				
					query=" INSERT INTO ERP_TIEUHAONGUYENLIEU (LENHSANXUAT_FK,CONGDOAN_FK,TRANGTHAI,NGUOITAO,NGAYTAO,NGUOISUA,NGAYSUA,NGAYTIEUHAO,NHAPKHO_PARENT_FK) "+
						" VALUES ("+solenhsanxuat+","+congdoan+",1,"+lsx.getUserId()+",'"+lsx.getNgaytao()+"',"+lsx.getUserId()+",'"+lsx.getNgaytao()+"','"+ngaynhapkho+"',"+idnhapkhotp+")"; 
						//System.out.println("1.Insert ERP_LENHSANXUAT_TIEUHAO: " + sql);
					if(!db.update(query))
					{
						db.update("rollback");
						return false;
					}
					query = "select IDENT_CURRENT('ERP_TIEUHAONGUYENLIEU') as clId";
						ResultSet rs =  db.get(query);
						rs.next();
						chungtutieuhaoid = rs.getString("clId");
						rs.close();
			}else{
				System.out.println("Không Xác định được nhập kho bán thành phẩm : " +query);
				db.update("rollback");
				return false;
			}
			
			query=" insert into ERP_LSXTIEUHAO_NHAPKHO (TIEUHAO_FK,NHAPKHO_FK) values ("+chungtutieuhaoid+","+idnhapkhotp+")";
			
			if(!db.update(query))
			{
				System.out.println("Không Xác định được nhập kho bán thành phẩm : " +query);
				db.update("rollback");
				return false;
			}
			rsphieuxk.beforeFirst();
			while (rsphieuxk.next()){
				String sanphamid=rsphieuxk.getString("SANPHAM_FK");
 
		     query=" select dmvt.vattu_fk,danhmuc.soluongchuan /(  "  +  
				   " SELECT TOP 1 CASE WHEN SP.DVDL_FK=100003 THEN 1 ELSE   "  +  
				   "  ISNULL(QC.SOLUONG2/ CAST(QC.SOLUONG1 AS FLOAT),0) END  "  +  
				   " FROM ERP_SANPHAM SP LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK=SP.PK_SEQ   "  +  
				   " AND QC.DVDL1_FK=SP.DVDL_FK AND QC.DVDL2_FK=100003 WHERE SP.PK_SEQ="  + sanphamid+ 
				   " ) as soluongchuan ,  "  + 
				   " dmvt.soluong / ( "  +  
				   " SELECT TOP 1 CASE WHEN SP.DVDL_FK=100003 THEN 1 ELSE   "  +  
				   "  ISNULL(QC.SOLUONG2/ CAST(QC.SOLUONG1 AS FLOAT),0) END  "  +  
				   " FROM ERP_SANPHAM SP LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK=SP.PK_SEQ   "  +  
				   " AND QC.DVDL1_FK=SP.DVDL_FK AND QC.DVDL2_FK=100003 WHERE SP.PK_SEQ=dmvt.vattu_fk "  +  
				   "  "  +  
				   " )  as soluong,vattu_fk from erp_lenhsanxuat_sanpham a "  +  
				   " inner join erp_danhmucvattu danhmuc on danhmuc.pk_seq=a.danhmucvt_fk "  +  
				   " inner join erp_danhmucvattu_vattu dmvt on dmvt.danhmucvt_fk=danhmuc.pk_seq "  +  
				   " where a.lenhsanxuat_fk="+solenhsanxuat+" and a.sanpham_fk= "+sanphamid;
		 	//System.out.println(query);
			ResultSet rs_getdmvt=db.get(query);
			if(rs_getdmvt.next()){
				 
				String sanphambtpid=rs_getdmvt.getString("vattu_fk");
				
					query = " select giaton from erp_tonkhothang where thang = '" + ngaynhapkho.substring(5,7) + "' and nam = '" + ngaynhapkho.substring(0,4) + "'" +
							" and sanpham_fk =  "+ sanphambtpid;
	
					String giaTon = "0";
					ResultSet rsGia = db.get(query);
					if(rsGia != null)
					{
						if(rsGia.next())
						{
							giaTon = rsGia.getString("giaton");
						}
						rsGia.close();
					}	
					
				//	System.out.println("SOLUONGNHAP : "+rsphieuxk.getDouble("SOLUONGNHAP"));
					//System.out.println("soluong : "+rs_getdmvt.getDouble("soluong"));
				//	System.out.println("soluongchuan : "+rs_getdmvt.getDouble("soluongchuan"));
					
					double soluongbtp= rsphieuxk.getDouble("SOLUONGNHAP")* rs_getdmvt.getDouble("soluong")/rs_getdmvt.getDouble("soluongchuan");
					
					soluongbtp=   Double.parseDouble(formatter.format(soluongbtp));
					double thanhtien=Double.parseDouble(giaTon)*soluongbtp;
					
					query = " Insert ERP_LENHSANXUAT_TIEUHAO ( TIEUHAONGUYENLIEU_FK,KHOTT_FK, sanpham_fk, soluong, dongia, thanhtien,loai) " +
					"values("+chungtutieuhaoid+","+khoTieuhao_fk+", "+sanphambtpid+", " +   formatter.format(soluongbtp) + ", " + giaTon + ", " + thanhtien + ",3)";
					
					 
					if(!db.update(query))
					{
						
						db.getConnection().rollback();
						return false;
					}
					  
				}else{
					db.update("rollback");
					System.out.println("Không thể xác định được BOM cho thành phẩm : " +sanphamid );
					return false;
				}		
				
			}
			return true;
		}catch(Exception er){
			er.printStackTrace();
			db.update("rollback");
			return false;	
		}
		
	}

	private static String getDateTime() {
		// TODO Auto-generated method stub
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			return dateFormat.format(date);
		 
	}
	
	public static boolean CheckDahoantat(dbutils db, String idnhanhang ){
		
		String query=" SELECT DATA.SANPHAM_FK,DATA.SOLUONG,DATA.SOLUONGDANHAN FROM ( "+ 
			 " SELECT MHSP.SANPHAM_FK,MHSP.SOLUONG ,(SELECT SUM( NHSP.SOLUONGNHAN ) FROM ERP_NHANHANG NH  "+ 
			 " INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NH.PK_SEQ=NHSP.NHANHANG_FK "+ 
			 " WHERE NH.MUAHANG_FK=MH.PK_SEQ AND NHSP.SANPHAM_FK=MHSP.SANPHAM_FK AND NH.TRANGTHAI  IN (1,2) )  AS SOLUONGDANHAN "+ 
			 " FROM ERP_MUAHANG MH  "+ 
			 " INNER JOIN ERP_MUAHANG_SP MHSP ON MH.PK_SEQ=MHSP.MUAHANG_FK "+ 
			 " WHERE MH.PK_SEQ= (select MUAHANG_FK from ERP_NHANHANG where PK_SEQ= "+idnhanhang+")  "+ 
			 " ) DATA WHERE DATA.SOLUONG-DATA.SOLUONGDANHAN >0";
		
		
		ResultSet  kt = db.get(query);
		try{
			 
			if(kt.next()){
				//dang con du lieu co the nhan
				kt.close();
				return false;
			}
			kt.close();
			
		}catch(Exception e){
			e.printStackTrace();
			return false;
			
		}
		return true;
	}
	
	public static boolean CheckTokhai(dbutils db, String idnhanhang ){
		
		
		String tokhai ="SELECT SOTOKHAI_FK FROM ERP_NHANHANG WHERE PK_SEQ='"+idnhanhang+"' ";
		String query="";
		ResultSet  tokhaiHQ= db.get(tokhai);
		try{
			if(tokhaiHQ.next()){
				query =   "	SELECT NHANHANG.SOTOKHAI_FK, NHANHANG.SANPHAM_FK, NHANHANG.SLNHAN, TOKHAIHQ.SOLUONG  "+
						 "  FROM "+
						 "  (SELECT  NH.SOTOKHAI_FK, NHSP.SANPHAM_FK, SUM(NHSP.SOLUONGNHAN) AS SLNHAN "+
						 "  FROM ERP_NHANHANG NH "+
						 "  INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NH.PK_SEQ= NHSP.NHANHANG_FK "+
						 "  INNER JOIN ERP_THUENHAPKHAU TNK ON NH.SOTOKHAI_FK= TNK.PK_SEQ "+
						 "  WHERE NH.TRANGTHAI IN (1,2)  AND NH.SOTOKHAI_FK=(SELECT SOTOKHAI_FK FROM ERP_NHANHANG WHERE PK_SEQ='"+idnhanhang+"') "+
						 "  GROUP BY NH.SOTOKHAI_FK, NHSP.SANPHAM_FK "+
						 "  HAVING SUM(NHSP.SOLUONGNHAN) >0 ) NHANHANG "+
						 "  INNER JOIN  "+
						 "   ( "+
						 "  	SELECT  A.SANPHAM_FK ,A.SOLUONG, D.SOTOKHAI_FK "+
						 "  	FROM ERP_MUAHANG_SP A INNER JOIN ERP_MUAHANG MH ON A.MUAHANG_FK = MH.PK_SEQ     "+
						 "  	LEFT JOIN ERP_SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ "+
						 "  	INNER JOIN ERP_MUAHANG C  ON A.MUAHANG_FK= C.PK_SEQ "+
						 "  	INNER JOIN ERP_NHANHANG D ON C.PK_SEQ= D.MUAHANG_FK    "+
						 "  	WHERE A.MUAHANG_FK IN "+
						 "  		(SELECT DISTINCT A.PK_SEQ  "+
						 "  		FROM ERP_MUAHANG A   "+
						 "  		LEFT JOIN ERP_HOADONNCC_DONMUAHANG B ON B.MUAHANG_FK = A.PK_SEQ   "+
						 "  		LEFT JOIN ERP_THUENHAPKHAU_HOADONNCC C ON C.HOADONNCC_FK = B.HOADONNCC_FK   "+
						 "  		WHERE C.THUENHAPKHAU_FK =(SELECT SOTOKHAI_FK FROM ERP_NHANHANG WHERE PK_SEQ='"+idnhanhang+"'))   "+
						 "  	AND A.SANPHAM_FK IN  "+
						 "  		(SELECT DISTINCT AA.SANPHAM_FK  "+
						 "  		FROM ERP_MUAHANG A   "+
						 "  		LEFT JOIN ERP_MUAHANG_SP AA ON AA.MUAHANG_FK = A.PK_SEQ   "+
						 "  		LEFT JOIN ERP_HOADONNCC_DONMUAHANG B ON B.MUAHANG_FK = A.PK_SEQ AND B.SANPHAM_FK = AA.SANPHAM_FK   "+
						 "  		LEFT JOIN ERP_THUENHAPKHAU_HOADONNCC C ON C.HOADONNCC_FK = B.HOADONNCC_FK   "+
						 "  		WHERE C.THUENHAPKHAU_FK =(SELECT SOTOKHAI_FK FROM ERP_NHANHANG WHERE PK_SEQ='"+idnhanhang+"')) "+
						 "  ) TOKHAIHQ "+
						 " ON NHANHANG.SOTOKHAI_FK= TOKHAIHQ.SOTOKHAI_FK AND NHANHANG.SANPHAM_FK=TOKHAIHQ.SANPHAM_FK "+
						 " WHERE (NHANHANG.SLNHAN-TOKHAIHQ.SOLUONG ) >0 ";
				ResultSet  kt = db.get(query);
				if(kt.next()){
					//dang con du lieu co the nhan
					kt.close();
					return false;
				}
				kt.close();
			}
			tokhaiHQ.close();
		
		}catch(Exception e){
			e.printStackTrace();
			return false;
		
		}
		return true;
	}	
	
 
	public static  void Update_quycach_DTY (){
		
		dbutils db = new dbutils();
		NumberFormat formatter2 = new DecimalFormat("#######.###");
		String query= "";
		String quycach= "";
		query= 		" select SP.pk_seq, SP.TEN ,  isnull(chungloai_fk, 0) as chungloai_fk , SP.MA,   " +  
					   "  ISNULL(SP.DAI, 0) AS DAI, ISNULL(SP.RONG, 0) AS RONG, ISNULL(SP.DINHLUONG, 0) AS DINHLUONG,  " +  
					   "  ISNULL(SP.TRONGLUONG, 0) AS TRONGLUONG, ISNULL(SP.DUONGKINHTRONG, 0) AS DUONGKINHTRONG,  " +  
					   "  ISNULL(SP.DODAY, 0) AS DODAY, ISNULL(SP.DAULON, 0) AS DAULON, ISNULL(SP.DAUNHO, 0) AS DAUNHO,  " +  
					   "  ISNULL(SP.DAIDAY, 0) AS DAIDAY,      ISNULL(SP.DVDL_DAI, '') AS DVDL_DAI, ISNULL(SP.DVDL_RONG, '') AS DVDL_RONG,  " +  
					   "  ISNULL(SP.DVDL_DINHLUONG, '') AS DVDL_DINHLUONG, ISNULL(SP.DVDL_TRONGLUONG, '') AS DVDL_TRONGLUONG,  " +  
					   "  ISNULL(SP.DVDL_DKTRONG, '') AS DVDL_DKTRONG, ISNULL(SP.DVDL_DODAY, '') AS DVDL_DODAY,  " +  
					   "  ISNULL(SP.DVDL_DAULON, '') AS DVDL_DAULON, ISNULL(SP.DVDL_DAUNHO, '') AS DVDL_DAUNHO,  " +  
					   "  ISNULL(SP.DVDL_DAIDAY, '') AS DVDL_DAIDAY,  ISNULL(SP.MAUIN, '') AS MAUIN, ISNULL(SP.MAU, '') AS MAU, " +  
					   "  ISNULL(SP.CHUANNEN, '') AS CHUANNEN, ISNULL(SP.LOGO,'') AS LOGO   " +  
					   "  from erp_sanpham SP where SP.LOAISANPHAM_FK=100005 AND CHUNGLOAI_FK IN (100031) " +  
					   "  AND DVKD_FK=100004  ";
		
		
		
		//*** khai báo biến lấy quy cách***//
		
		double dai=0;
		double rong=0;
		double trongluong=0;
		double dinhluong=0;
		double duongkinhtrong=0;
		double doday=0;
		double daulon=0;
		double daunho=0;
		double daiday=0;
		String mau="";
		String mauin="";
		String logo="";
		String chuannen="";
		String spma="";
		String clId;
		String spten="";
		ResultSet kt = db.get(query);
		ResultSet thuchien;
		String chuoi="";
		String command="";
		try {
		
			if(kt!=null){	
				while(kt.next()){
					
					spma =kt.getString("pk_seq");
					spten =kt.getString("ten");
					clId =kt.getString("chungloai_fk");
					
					
					if( clId.equals("100031"))  //ONG CORE     
					{
						
						duongkinhtrong = Double.parseDouble(kt.getString("DUONGKINHTRONG"));
						dai = Double.parseDouble(kt.getString("DAI"));
						doday= Double.parseDouble(kt.getString("DODAY"));
						mau= kt.getString("MAU");
						if(mau.trim().equals("Không màu") || mau.trim().equals("")){
							mau="";
						}
						
						mauin = kt.getString("MAUIN");
					
						quycach =  (duongkinhtrong >0 ? " x " + formatter2.format(duongkinhtrong)  + kt.getString("DVDL_DKTRONG") :"");
						quycach += (dai >0 ? " x " + formatter2.format(dai)  + kt.getString("DVDL_DAI") :"");
						quycach += (doday >0 ? " x " + formatter2.format(doday)  + kt.getString("DVDL_DODAY") :"");
						//quycach += (mau.length() >0 ? " x " + mau :"");
						logo = kt.getString("LOGO");
						quycach += (logo.length() >0 ? " x " + logo :"");
						//quycach += (mauin.length() >0 ? " x " + mauin :"");
						chuannen = kt.getString("CHUANNEN");
						
						quycach += (chuannen.length() >0 ? " x " + chuannen :"");
						
						
						System.out.println("quy cach  core/dty:"+ spma  + spten + quycach);
						
						
						
						query = " update erp_sanpham set QUYCACH=N'"+quycach+"' where pk_seq="+spma+"  ";
						db.update(query);
						
						chuoi=" upper(dbo.ftBoDau(isnull(ten,'')))  +'-'+ upper(dbo.ftBoDau(isnull(quycach,''))) ";
						
						
						 command= " UPDATE ERP_SANPHAM SET TIMKIEM = CAST(PK_SEQ AS NVARCHAR(10))+ ' '+ "+chuoi+" where pk_seq='"+spma+"'";
						db.update(command);
						
						
					
					}else if(clId.equals("100030") || clId.equals("100032")||clId.equals("100033")){
					
						duongkinhtrong = Double.parseDouble(kt.getString("DUONGKINHTRONG"));
						dai = Double.parseDouble(kt.getString("DAI"));
						doday= Double.parseDouble(kt.getString("DODAY"));
						mau= kt.getString("MAU");
						if(mau.trim().equals("Không màu") || mau.trim().equals("")){
							mau="";
						}
						
						
						/*logo = kt.getString("LOGO");
						if(logo.trim().equals("KHONG") || logo.trim().equals("")){
							logo="";
						}*/
						
						mauin = kt.getString("MAUIN");
					
						chuannen = kt.getString("CHUANNEN");
						
						/*if(mauin.trim().equals("Không") || mauin.trim().equals("")){
							mauin="";
						}
						
						*/
						
						quycach =  (duongkinhtrong >0 ? " x " + formatter2.format(duongkinhtrong)  + kt.getString("DVDL_DKTRONG") :"");
						quycach += (dai >0 ? " x " + formatter2.format(dai)  + kt.getString("DVDL_DAI") :"");
						quycach += (doday >0 ? " x " + formatter2.format(doday)  + kt.getString("DVDL_DODAY") :"");
						quycach += (mau.length() >0 ? " x " + mau :"");
						quycach += (mauin.length() >0 ? " x " + mauin :"");
						quycach += (chuannen.length() >0 ? " x " + chuannen :"");
						
						
						System.out.println("quy cach  core/dty:"+ spma  + spten + quycach);
						
						
						
						query = " update erp_sanpham set QUYCACH=N'"+quycach+"' where pk_seq="+spma+"  ";
						db.update(query);
						
						chuoi=" upper(dbo.ftBoDau(isnull(ten,'')))  +'-'+ upper(dbo.ftBoDau(isnull(quycach,''))) ";
						
						
						 command= " UPDATE ERP_SANPHAM SET TIMKIEM = CAST(PK_SEQ AS NVARCHAR(10))+ ' '+ "+chuoi+" where pk_seq='"+spma+"'";
						db.update(command);
							
					}
					else if(clId.equals("100040"))  //ONG CONE
						{
						
							daulon=  Double.parseDouble(kt.getString("DAULON"));
							dai = Double.parseDouble(kt.getString("DAI"));
							daunho=  Double.parseDouble(kt.getString("DAUNHO"));
							trongluong =  Double.parseDouble(kt.getString("TRONGLUONG"));
							
							
							mau= kt.getString("MAU");
							if(mau.trim().equals("Không màu") || mau.trim().equals("")){
								mau="";
							}
							
							mauin = kt.getString("MAUIN");
							
							/*
							if(mauin.trim().equals("Không") || mauin.trim().equals("")){
								mauin="";
							}*/
							
							chuannen = kt.getString("CHUANNEN");

							quycach =  (daulon >0 ? " x " + formatter2.format(daulon)  + kt.getString("DVDL_DAULON") :"");
							quycach += (dai >0 ? " x " + formatter2.format(dai)  + kt.getString("DVDL_DAI") :"");
							quycach += (daunho >0 ? " x " + formatter2.format(daunho)  + kt.getString("DVDL_DAUNHO") :"");
							quycach += (trongluong >0 ? " x " + formatter2.format(trongluong)  + kt.getString("DVDL_TRONGLUONG") :"");
							
							quycach += (mauin.length() >0 ? " x " + mauin :"");
							
							quycach += (mau.length() >0 ? " x " + mau :"");
							
							
							quycach += (chuannen.length() >0 ? " x " + chuannen :"");
							
							System.out.println("quy cach cone :" + spma + spten + quycach);
						

							
							query = " update erp_sanpham set QUYCACH=N'"+quycach+"' where pk_seq="+spma+"  ";
							db.update(query);
							
							chuoi=" upper(dbo.ftBoDau(isnull(ten,'')))  +'-'+ upper(dbo.ftBoDau(isnull(quycach,''))) ";
							
							
							 command= " UPDATE ERP_SANPHAM SET TIMKIEM = CAST(PK_SEQ AS NVARCHAR(10))+ ' '+ "+chuoi+" where pk_seq='"+spma+"'";
							db.update(command);
							
					
		
				} 
					
				} 
				
			}}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
	