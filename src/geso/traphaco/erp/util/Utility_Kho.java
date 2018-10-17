package geso.traphaco.erp.util;
import geso.traphaco.center.db.sql.Idbutils;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
 
public class Utility_Kho implements Serializable
{ 
			
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	//Lấy New
	public String Update_Kho_Sp(String Nghiepvu ,String ngaychungtu ,dbutils db, String khott_fk, String spId,
			double soluong ,double booked,double available , double dongia) {
		// TODO Phương thức đưa số lượng nhập vào kho,bảng ERP_KHOTT_SANPHAM
		try{
			String query="  select k.ten as tenkho , sanpham_fk ,available,soluong , sp.ma+ ' '+ sp.ten as ten , ISNULL(KHO.GIATON,0) AS GIATON   from erp_khott_sanpham kho " +
						 "  inner join erp_sanpham sp  on kho.sanpham_fk=sp.pk_seq " +
						 " inner join erp_khott k on k.pk_seq= kho.khott_fk   " +
						 "  where khott_fk="+khott_fk+" and sanpham_fk= "+spId;
			 
			ResultSet rsCheck = db.get(query);
			if(rsCheck.next()){
					String tenkho=rsCheck.getString("tenkho");
				    double soluongton=rsCheck.getDouble("soluong");
				    double available_ton=rsCheck.getDouble("available");
				    double giaton=rsCheck.getDouble("GIATON");
				    
				    if(available < 0 && available_ton < (-1)*available ){
				    	return "Số lượng tồn hiện tại trong kho ["+tenkho+"] của sản phẩm : "+spId +" - "+rsCheck.getString("ten") + "  ["+available_ton+"] không đủ để trừ kho ["+available+"],vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				    }
				    
				    if(soluong < 0 && soluongton <(-1)*soluong ){
				    	return "Số lượng tồn  trong kho ["+tenkho+"] của sản phẩm : "+spId +" - "+rsCheck.getString("ten") + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				    }
				    
				   
					
					query = " Update ERP_KHOTT_SANPHAM set booked=isnull(booked,0)+"+booked+" , soluong =ISNULL(soluong,0) + " + soluong + ", " +
							" AVAILABLE = ISNULL(AVAILABLE,0) + " + available + ",giaton="+(giaton >0?giaton:dongia)+"  "+
							" where khott_fk =" + khott_fk + " and sanpham_fk = " +   spId + "";
			}else{
					query=  " INSERT INTO ERP_KHOTT_SANPHAM ( KHOTT_FK,SANPHAM_FK,GIATON,SOLUONG,BOOKED,AVAILABLE,THANHTIEN ) VALUES  " +
						    " ("+khott_fk+","+ spId+","+dongia+","+soluong+","+booked+","+available+","+soluong+"*"+dongia+")";
			}
			rsCheck.close();
			int resultInt = db.updateReturnInt(query);
			
			if(resultInt != 1)
			{
				return  " Không thể cập nhật ERP_KHOTT_SANPHAM " + query;
			}
		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp : "+er.getMessage();
		}
		return "";
	}
	public String Update_Kho_Sp_Doituong(String Nghiepvu ,String ngaychungtu ,dbutils db, String khott_fk, String spId,
			double soluong ,double booked,double available , double dongia,String doituongId,String Loaidoituong) {
		// TODO Phương thức đưa số lượng nhập vào kho,bảng ERP_KHOTT_SANPHAM
		try{
			String query="  select k.ten as tenkho , sanpham_fk ,available,soluong , sp.ma+ ' '+ sp.ten as ten , ISNULL(KHO.GIATON,0) AS GIATON   from erp_khott_sanpham kho " +
						 "  inner join erp_sanpham sp  on kho.sanpham_fk=sp.pk_seq " +
						 " inner join erp_khott k on k.pk_seq= kho.khott_fk   " +
						 "  where  DOITUONGID="+doituongId+" and loaidoituong='"+Loaidoituong+"' and  khott_fk="+khott_fk+" and sanpham_fk= "+spId;
			 
			ResultSet rsCheck = db.get(query);
			if(rsCheck.next()){
					String tenkho=rsCheck.getString("tenkho");
				    double soluongton=rsCheck.getDouble("soluong");
				    double available_ton=rsCheck.getDouble("available");
				    double giaton=rsCheck.getDouble("GIATON");
				    
				    if(available < 0 && available_ton < (-1)*available ){
				    	return "Số lượng tồn hiện tại trong kho ["+tenkho+"] của sản phẩm : "+spId +" - "+rsCheck.getString("ten") + "  ["+available_ton+"] không đủ để trừ kho ["+available+"],vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				    }
				    
				    if(soluong < 0 && soluongton <(-1)*soluong ){
				    	return "Số lượng tồn  trong kho ["+tenkho+"] của sản phẩm : "+spId +" - "+rsCheck.getString("ten") + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				    }
				    
				   
					
					query = " Update ERP_KHOTT_SANPHAM set booked=isnull(booked,0)+"+booked+" , soluong =ISNULL(soluong,0) + " + soluong + ", " +
							" AVAILABLE = ISNULL(AVAILABLE,0) + " + available + ",giaton="+(giaton >0?giaton:dongia)+"  "+
							" where khott_fk =" + khott_fk + " and sanpham_fk = " +   spId + " and DOITUONGID="+doituongId+" and loaidoituong='"+Loaidoituong+"'";
					
			}else{
					query=  " INSERT INTO ERP_KHOTT_SANPHAM ( KHOTT_FK,SANPHAM_FK,GIATON,SOLUONG,BOOKED,AVAILABLE,THANHTIEN,DOITUONGID,LOAIDOITUONG ) VALUES  " +
						    " ("+khott_fk+","+ spId+","+dongia+","+soluong+","+booked+","+available+","+soluong+"*"+dongia+")";
			}
			rsCheck.close();
			int resultInt = db.updateReturnInt(query);
			
			if(resultInt != 1)
			{
				return  " Không thể cập nhật ERP_KHOTT_SANPHAM " + query;
			}
		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp : "+er.getMessage();
		}
		return "";
	}

	public String Update_Kho_Sp(dbutils db, String khott_fk, String spId,
					double soluong ,double booked,double available , double dongia) {
				// TODO Phương thức đưa số lượng nhập vào kho,bảng ERP_KHOTT_SANPHAM
				try{
					String query="  select sanpham_fk ,available,soluong , sp.ma+ ' '+ sp.ten as ten , ISNULL(KHO.GIATON,0) AS GIATON   from erp_khott_sanpham kho " +
								 "  inner join erp_sanpham sp  on kho.sanpham_fk=sp.pk_seq  where khott_fk="+khott_fk+" and sanpham_fk= "+spId;
					 
					double available_ton=0;
					double giaton=0;
					 double soluongton=0;
					
					ResultSet rsCheck = db.get(query);
					if(rsCheck.next()){
						     soluongton=rsCheck.getDouble("soluong");
						     available_ton=rsCheck.getDouble("available");
						     giaton=rsCheck.getDouble("GIATON");
						    
						    if(available < 0 && available_ton < (-1)*available ){
						    	return "Số lượng tồn hiện tại trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
						    }
						    
						    if(soluong < 0 && soluongton <(-1)*soluong ){
						    	return "Số lượng tồn  trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
						    }
						    
						    if(giaton >0){
						    	if( giaton- dongia !=0) {
						    		
						    		query=" insert into log_sql_khott(ngay,khott_fk,sanpham_fk ,ghichu ) " +
						    			  " values( GETDATE(),"+khott_fk+","+spId+",N'đơn giá khác nhau giữa 2 kho :Giá cũ :"+giaton+". Giá mới :"+dongia+"' )";
						    		
						    		db.update(query);
						    		
						    	}
						    } 
							
							query = " Update ERP_KHOTT_SANPHAM set booked=isnull(booked,0)+"+booked+" , soluong =ISNULL(soluong,0) + " + soluong + ", " +
									" AVAILABLE = ISNULL(AVAILABLE,0) + " + available + ",giaton="+(giaton >0?giaton:dongia)+"  "+
									" where khott_fk =" + khott_fk + " and sanpham_fk = " +   spId + "";
					}else{
							query=  " INSERT INTO ERP_KHOTT_SANPHAM ( KHOTT_FK,SANPHAM_FK,GIATON,SOLUONG,BOOKED,AVAILABLE,THANHTIEN ) VALUES  " +
								    " ("+khott_fk+","+ spId+","+dongia+","+soluong+","+booked+","+available+","+soluong+"*"+dongia+")";
							
							if(available < 0 && available_ton < (-1)*available ){
						    	return "Số lượng tồn hiện tại trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
						    }
						    
						    if(soluong < 0 && soluongton <(-1)*soluong ){
						    	return "Số lượng tồn  trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
						    }
							
					}
					rsCheck.close();
					int resultInt = db.updateReturnInt(query);
					
					if(resultInt != 1)
					{
						return  " Không thể cập nhật ERP_KHOTT_SANPHAM " + query;
					}
				}catch(Exception er){
					er.printStackTrace();
					return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp : "+er.getMessage();
				}
				return "";
			}
	public String Update_Kho_Sp_Check_TonKhoNgay(dbutils db, String khott_fk, String spId,
			double soluong ,double booked,double available , double dongia,String ngaychungtu ) {
		// TODO Phương thức đưa số lượng nhập vào kho,bảng ERP_KHOTT_SANPHAM
		try{
			NumberFormat formatter2 = new DecimalFormat("#######.######");
			if(soluong!=0){
				// thực hiện kiểm tra ngày chứng từ có hợp lệ không?
				int thangtruoc=Integer.parseInt(ngaychungtu.substring(5, 7));
				int namtruoc=Integer.parseInt(ngaychungtu.substring(0, 4));
 
				String sql=" select TOP 1 THANGKS, NAM from ERP_KHOASOTHANG order by NAM desc, THANGKS desc ";
				 
				ResultSet rscheckngay=db.get(sql);
				if(rscheckngay.next()){
					 if(thangtruoc <=  rscheckngay.getInt("THANGKS")  &&  namtruoc <= rscheckngay.getInt("NAM")){
						 return " Vui lòng chỉ được chọn ngày ghi nhận sau tháng khóa sổ gần nhất:Tháng :"+rscheckngay.getString("THANGKS")+",năm :  "+rscheckngay.getString("Nam");
					 }
				}else{ 
						return " Vui lòng kiểm tra khóa sổ tháng,chưa có khóa sổ tháng";
				}
				rscheckngay.close();
  
			}
			
			if(soluong<0){
				//kiểm tra tồn kho ngày có còn đủ để xuất kho hay không?
				
				
				
				//chỉ check trong trường hợp giảm số lượng
				String[] param = new String[4];
			 	param[0] =khott_fk;
			    param[1] =ngaychungtu;
			    param[2] =ngaychungtu;
			    param[3] =spId;
			    ResultSet tonkhongay= db.getRsByPro("REPORT_XUATNHAPTON_THEKHO_DAUKY", param);
			    System.out.println("param: " + khott_fk + ", " 
	    		+ ngaychungtu + ", " + ngaychungtu + ", " + spId);
			    double soluongtonngay=0;
			    String SPTEN="";
			    String khoten="";
			    if(tonkhongay.next()){
			    	soluongtonngay=tonkhongay.getDouble("TONDAUKY")+ tonkhongay.getDouble("TONGNHAP")- tonkhongay.getDouble("TONGXUAT");
			    	SPTEN=tonkhongay.getString("SPMA")+" - "+tonkhongay.getString("SPTEN");
			    	khoten=tonkhongay.getString("khoten");
			    } 
			  
			  /*  if( Double.parseDouble(formatter2.format(soluongtonngay)) < (soluong *-1)){
			    	return "Không thể thực hiện xuất kho : [  "+ khoten + " ]   cho sản phẩm :"+SPTEN+". Tồn kho  tới ngày "+ngaychungtu +" chỉ còn :"+formatter2.format(soluongtonngay);
			    }*/
			    tonkhongay.close();
			    
			}
			
			String query="  select sanpham_fk ,available,soluong , sp.ma+ ' '+ sp.ten as ten , ISNULL(KHO.GIATON,0) AS GIATON   from erp_khott_sanpham kho " +
						 "  inner join erp_sanpham sp  on kho.sanpham_fk=sp.pk_seq  where khott_fk="+khott_fk+" and sanpham_fk= "+spId;
			 
			double available_ton=0;
			double giaton=0;
			 double soluongton=0;
			
			ResultSet rsCheck = db.get(query);
			
			String tensp="";
			
			if(rsCheck.next()){
				     soluongton=rsCheck.getDouble("soluong");
				     available_ton=rsCheck.getDouble("available");
				     giaton=rsCheck.getDouble("GIATON");
				     tensp=rsCheck.getString("ten");
				   
				    if(giaton >0){
				    	if( giaton- dongia !=0) {
				    		
				    		query=" insert into log_sql_khott(ngay,khott_fk,sanpham_fk ,ghichu ) " +
				    			  " values( GETDATE(),"+khott_fk+","+spId+",N'đơn giá khác nhau giữa 2 kho :Giá cũ :"+giaton+". Giá mới :"+dongia+"' )";
				    		
				    		db.update(query);
				    		
				    	}
				    } 
					
					query = " Update ERP_KHOTT_SANPHAM set booked=isnull(booked,0)+"+booked+" , soluong =ISNULL(soluong,0) + " + soluong + ", " +
							" AVAILABLE = ISNULL(AVAILABLE,0) + " + available + ",giaton="+(giaton >0?giaton:dongia)+"  "+
							" where khott_fk =" + khott_fk + " and sanpham_fk = " +   spId + "";
			}else{
					query=  " INSERT INTO ERP_KHOTT_SANPHAM ( KHOTT_FK,SANPHAM_FK,GIATON,SOLUONG,BOOKED,AVAILABLE,THANHTIEN ) VALUES  " +
						    " ("+khott_fk+","+ spId+","+dongia+","+soluong+","+booked+","+available+","+soluong+"*"+dongia+")";
					
					
					
			}
			rsCheck.close();
			
			if(available < 0 &&  Double.parseDouble(formatter2.format(available_ton))  < (-1)*Double.parseDouble(formatter2.format(available)) ){
		    	return "Số lượng tồn hiện tại trong kho của sản phẩm : "+tensp+ "  ["+available_ton+"] < ["+((-1)*Double.parseDouble(formatter2.format(available)))+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
		    }
		    
		    if(soluong < 0 &&  Double.parseDouble(formatter2.format(soluongton)) < (-1)*  Double.parseDouble(formatter2.format(soluong))  ){
		    	return "Số lượng tồn  trong kho của sản phẩm : "+tensp + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
		    }
		    
		    // thực hiện tính tồn kho tới ngày 
		    
		    
			int resultInt = db.updateReturnInt(query);
			
			if(resultInt != 1)
			{
				return  " Không thể cập nhật ERP_KHOTT_SANPHAM " + query;
			}
			
			
		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp : "+er.getMessage();
		}
		return "";
	}

	public String Update_Kho_Sp_Chitiet(dbutils db, String khott_fk,
			String SANPHAM_FK, double soluongnhap,double booked,double available , double DONGIAMUA,
			String solo, String vitri,String KHUVUCKHO_FK, String NgayBatDau,String NgayNhapKho ,String NgaySanXuat,String NGAYHETHAN,
			double CPCAPDONG, double CPLUUKHO, double CPNHANHANG,
			double THUENHAPKHAU) {
		
		//không sử dụng ngày bắt đầu nữa :
		NgayBatDau="2015-01-01";
		// TODO Auto-generated method stub
		/*
		 * Phương thức này là cập nhật số lượng nhập kho vào bảng ERP_KHOTT_SP_CHITIET ,cả đạt chất lượng và không đạt chất lượng.
		 * Hàng không đạt chất lượng chỉ có trong kho chờ xử lý
		 */
		try{
			 
//					vitri="100000";
			String	query = " select count(*) as sodong from ERP_KHOTT_SP_CHITIET " +
							" where   KHOTT_FK = '" + khott_fk + "' and SANPHAM_FK = " + SANPHAM_FK + " " +
							" and  ltrim(rtrim(SOLO)) = '" + solo.trim() + "' "+ (KHUVUCKHO_FK.length() >0?"and  khuvuckho_fk="+KHUVUCKHO_FK:"") +"  ";
			System.out.println(query);
		
			ResultSet	rsCheck = db.get(query);
			boolean flag = false;
				if(rsCheck.next())
				{
					if(rsCheck.getInt("sodong") > 0) {
						flag = true;
					}
					
				}
				rsCheck.close();
				
				if(flag)
				{
					query = " update ERP_KHOTT_SP_CHITIET set booked=isnull(booked,0) + "+booked+" ,soluong = soluong + " + soluongnhap + ", AVAILABLE = AVAILABLE + " + available + " " +
							" where   KHOTT_FK = '" + khott_fk + "' and SANPHAM_FK = " +SANPHAM_FK + " " +
							" and  ltrim(rtrim(SOLO)) = '" + solo.trim() + "' " +
//									"and BIN = '" + vitri + "' " +
							""+(KHUVUCKHO_FK.length() >0?"and  khuvuckho_fk="+KHUVUCKHO_FK:"")+" and NGAYBATDAU='"+NgayBatDau+"' ";
				}
				else
				{
					query = "  insert ERP_KHOTT_SP_CHITIET(KHOTT_FK, SANPHAM_FK, SOLUONG , BOOKED, AVAILABLE, SOLO, NGAYSANXUAT, NGAYHETHAN, NGAYNHAPKHO,NGAYBATDAU " +
							"  ,BIN,KHUVUCKHO_FK ,DONGIAMUA, CPCAPDONG ,CPLUUKHO ,CPNHANHANG,THUENHAPKHAU ) " +
							"  VALUES ( " + khott_fk + " ,  " + SANPHAM_FK + " , " +soluongnhap+ ", "+booked+", " +available + ", '" + solo.trim() + "', '"+NgaySanXuat+"', '"+NGAYHETHAN+"'," +
							"  '"  + NgayNhapKho + "','"+NgayBatDau+"', '" + vitri + "' ,"+(KHUVUCKHO_FK.length() >0?KHUVUCKHO_FK:"NULL")+", " +
							      +DONGIAMUA+","+CPCAPDONG+" ,"+CPLUUKHO+","+CPNHANHANG+","+THUENHAPKHAU+") " ;
							 
				}	
			 System.out.println("UDKSPCT 1.1 khu vuc kho: vitri: " + vitri + " KHUVUCKHO_FK: " + KHUVUCKHO_FK);
				if(db.updateReturnInt(query)!=1)
				{
					 return "UDKSPCT 1.1 Không thể cập nhật ERP_KHOTT_SP_CHITIET " + query;
				}
				return "";
		}catch(Exception er){
			er.printStackTrace();
			return  "UDKSPCT 1.2 không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp_Chitiet : "+er.getMessage();
		}
	}


	public String Update_Kho_Sp_Chitiet_Tra(Idbutils db,  Kho_Lib kho_lib
			 ) {
		
		//không sử dụng ngày bắt đầu nữa :
	 
		// TODO Auto-generated method stub
		/*
		 * Phương thức này là cập nhật số lượng nhập kho vào bảng ERP_KHOTT_SP_CHITIET ,cả đạt chất lượng và không đạt chất lượng.
		 * Hàng không đạt chất lượng chỉ có trong kho chờ xử lý
		 */
		try{/*
			 
//					vitri="100000";
			String	query = " select count(*) as sodong from ERP_KHOTT_SP_CHITIET " +
							" where   KHOTT_FK = '" + khott_fk + "' and SANPHAM_FK = " + SANPHAM_FK + " " +
							" and  ltrim(rtrim(SOLO)) = '" + solo.trim() + "' "+ (KHUVUCKHO_FK.length() >0?"and  khuvuckho_fk="+KHUVUCKHO_FK:"") +"  ";
			System.out.println(query);
		
			ResultSet	rsCheck = db.get(query);
			boolean flag = false;
				if(rsCheck.next())
				{
					if(rsCheck.getInt("sodong") > 0) {
						flag = true;
					}
					
				}
				rsCheck.close();
				
				if(flag)
				{
					query = " update ERP_KHOTT_SP_CHITIET set booked=isnull(booked,0) + "+booked+" ,soluong = soluong + " + soluongnhap + ", AVAILABLE = AVAILABLE + " + available + " " +
							" where   KHOTT_FK = '" + khott_fk + "' and SANPHAM_FK = " +SANPHAM_FK + " " +
							" and  ltrim(rtrim(SOLO)) = '" + solo.trim() + "' " +
//									"and BIN = '" + vitri + "' " +
							""+(KHUVUCKHO_FK.length() >0?"and  khuvuckho_fk="+KHUVUCKHO_FK:"")+" and NGAYBATDAU='"+NgayBatDau+"' ";
				}
				else
				{
					query = "  insert ERP_KHOTT_SP_CHITIET(KHOTT_FK, SANPHAM_FK, SOLUONG , BOOKED, AVAILABLE, SOLO, NGAYSANXUAT, NGAYHETHAN, NGAYNHAPKHO,NGAYBATDAU " +
							"  ,BIN,KHUVUCKHO_FK ,DONGIAMUA, CPCAPDONG ,CPLUUKHO ,CPNHANHANG,THUENHAPKHAU ) " +
							"  VALUES ( " + khott_fk + " ,  " + SANPHAM_FK + " , " +soluongnhap+ ", "+booked+", " +available + ", '" + solo.trim() + "', '"+NgaySanXuat+"', '"+NGAYHETHAN+"'," +
							"  '"  + NgayNhapKho + "','"+NgayBatDau+"', '" + vitri + "' ,"+(KHUVUCKHO_FK.length() >0?KHUVUCKHO_FK:"NULL")+", " +
							      +DONGIAMUA+", ) " ;
							 
				}	
			 System.out.println("UDKSPCT 1.1 khu vuc kho: vitri: " + vitri + " KHUVUCKHO_FK: " + KHUVUCKHO_FK);
				if(db.updateReturnInt(query)!=1)
				{
					 return "UDKSPCT 1.1 Không thể cập nhật ERP_KHOTT_SP_CHITIET " + query;
				}
			
		*/ 
			return "";
			}catch(Exception er){
			er.printStackTrace();
			return  "UDKSPCT 1.2 không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp_Chitiet : "+er.getMessage();
		}
	}



	public String Update_Kho_Sp_Chitiet_TrangThai(dbutils db, String khott_fk,
			String SANPHAM_FK, double soluongnhap,double Booked,double available, double DONGIAMUA,
			String solo, String vitri,String KHUVUCKHO_FK, String NgayBatDau,String NgayNhapKho ,String NgaySanXuat,String NGAYHETHAN,
			double CPCAPDONG, double CPLUUKHO, double CPNHANHANG,
			double THUENHAPKHAU,String trangthai) {
		
		NgayBatDau="2015-01-01";
		// TODO Auto-generated method stub
		/*
		 * Phương thức này là cập nhật số lượng nhập kho vào bảng ERP_KHOTT_SP_CHITIET_TRANGTHAI ,TRANGTHAI=-1 LÀ HÀNG KHÔNG ĐẠT,TRANGTHAI=0 LÀ HÀNG ĐẠT ,ĐỂ CHO CÁC BÁC VÀO CHUYỂN KHO CHỈ LẤY SỐ LƯỢNG ĐẠT THÔI
		 * Hàng không đạt chất lượng chỉ có trong kho chờ xử lý
		 */
		try{
			vitri="100000";
		String	query = " select count(*) as sodong from ERP_KHOTT_SP_CHITIET_TRANGTHAI " +
						" where   KHOTT_FK = '" + khott_fk + "' and SANPHAM_FK = " + SANPHAM_FK + " " +
						" and  ltrim(rtrim(SOLO)) = '" + solo.trim() + "'   "+(KHUVUCKHO_FK.length() >0?"and  khuvuckho_fk="+KHUVUCKHO_FK:"")+" and NGAYBATDAU='"+NgayBatDau+"' and trangthai="+trangthai+" ";
			    ResultSet	rsCheck = db.get(query);
				boolean flag = false;
				if(rsCheck.next())
				{
					if(rsCheck.getInt("sodong") > 0) {
						flag = true;
					}
					
				}
				rsCheck.close();
				System.out.println("KHUVUCKHO_FK la: " + KHUVUCKHO_FK);
				if(flag)
				{
					query = " update ERP_KHOTT_SP_CHITIET_TRANGTHAI set booked =isnull(booked,0)+ "+Booked+",soluong = soluong + " + soluongnhap + ", AVAILABLE = AVAILABLE + " + available + " " +
							" where   KHOTT_FK = '" + khott_fk + "' and SANPHAM_FK = " +SANPHAM_FK + " " +
							" and  ltrim(rtrim(SOLO)) = '" + solo.trim() + "'  "+(KHUVUCKHO_FK.length() >0?"and  khuvuckho_fk="+KHUVUCKHO_FK:"")+"  and NGAYBATDAU='"+NgayBatDau+"' AND TRANGTHAI="+trangthai+"";
				}
				else
				{
					query = "  insert ERP_KHOTT_SP_CHITIET_TRANGTHAI(KHOTT_FK, SANPHAM_FK, SOLUONG , BOOKED, AVAILABLE, SOLO, NGAYBATDAU " +
							" ,BIN,KHUVUCKHO_FK , TRANGTHAI ) " +
							"  VALUES ( " + khott_fk + " ,  " + SANPHAM_FK + " , " +soluongnhap+ ","+Booked+", " +available + ", '" + solo.trim() + "', '"+NgayBatDau+"' ," +
							"  '" + vitri + "' ,"+(KHUVUCKHO_FK.length() >0?KHUVUCKHO_FK:"NULL")+", " + trangthai+" ) " ;
				}	
				
				if(db.updateReturnInt(query)!=1)
				{
					 return "Không thể cập nhật ERP_KHOTT_SP_CHITIET " + query;
				}
				return "";
				
		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp_Chitiet_TrangThai : "+er.getMessage();
				}
			}
 

			public boolean IsKhoQuanLyTrangThai(String khoid,  dbutils db) {
				// TODO Auto-generated method stub
		try{
			if(khoid.length() >0){
			 String query=" select isnull(quanlytrangthai,'0') as trangthai from ERP_KHOTT WHERE PK_SEQ= "+khoid;
			 ResultSet rs=db.get(query);
			 if(rs.next()){
				 if(rs.getString("trangthai").equals("1")){
					 rs.close();
					 return true;
				 }
				
			 }
			 rs.close();
			 
			}
		}catch(Exception er){
			//er.printStackTrace();
			return false;
		}
		return false;
	}


	public String getIsQuanLyKhuVuc(String khoid,
			 dbutils db) {
		// TODO Auto-generated method stub
		try{
			if(khoid.length()  >0){
				 String query="select isnull(QUANLYBIN,'0') as QUANLYBIN from ERP_KHOTT WHERE PK_SEQ= "+khoid;
				 ResultSet rs=db.get(query);
				 if(rs.next()){
					 if(rs.getString("QUANLYBIN").equals("1")){
						 rs.close();
						 return "1";
					 }
					
				 }
				 rs.close();
				 
			}
		}catch(Exception er){
			//er.printStackTrace();
			return "0";
		}
		return "0";
	}


	public String Update_Kho_Sp_Chitiet(dbutils db, String khott_fk,
			String SANPHAM_FK, double soluongnhap,double Booked,double available, double DONGIAMUA,
			String solo, String vitri,String KHUVUCKHO_FK, String NgayBatDau ) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		/*
		 * Phương thức này là cập nhật số lượng nhập kho vào bảng ERP_KHOTT_SP_CHITIET ,cả đạt chất lượng và không đạt chất lượng.
		 * Hàng không đạt chất lượng chỉ có trong kho chờ xử lý
		 */
		NgayBatDau="2015-01-01";
		try{
			if(KHUVUCKHO_FK==null){
				KHUVUCKHO_FK="";
			}
			if(KHUVUCKHO_FK.equals("NULL")){
				KHUVUCKHO_FK="";
			}
				String query=" SELECT  SP.MA+ ' - '+ SP.TEN AS TENSP , KHO.SOLUONG,KHO.AVAILABLE ,khu.ten as tenkhu " +
							 " FROM ERP_KHOTT_SP_CHITIET KHO INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ= SANPHAM_FK  " +
							 " left join erp_khuvuckho khu on khu.pk_seq= kho.KHUVUCKHO_FK "+
							 " where   kho.KHOTT_FK = '" + khott_fk + "' and SANPHAM_FK = " +SANPHAM_FK + " " +
							 " and  ltrim(rtrim(SOLO)) = '" + solo.trim() + "'   "+(KHUVUCKHO_FK.length()>0? " and khuvuckho_fk="+KHUVUCKHO_FK : " and khuvuckho_fk is null " )+ " ";
				ResultSet rs=db.get(query);
					
				if(rs.next()){
					if(soluongnhap <0){
						// giảm số lượng
						if(rs.getDouble("soluong") < (soluongnhap*-1)){
							return " Số lô : "+solo.trim()+ (KHUVUCKHO_FK.length()>0? ". Khu vực kho: "+ rs.getString("tenkhu"):"" )+"Ngày bắt đầu :"+NgayBatDau +"  của sản phẩm  " + rs.getString("tensp")+" không đủ số lượng tồn kho. Tồn hiện tại : "+rs.getDouble("soluong");
						}
						
					}
					
					if(available <0){
						if(rs.getDouble("AVAILABLE") < (available*-1)){
							return " Số lô : "+solo.trim()+ (KHUVUCKHO_FK.length()>0? ". Khu vực kho: "+ rs.getString("tenkhu"):"" )+"Ngày bắt đầu :"+NgayBatDau +"  của sản phẩm  " + rs.getString("tensp") +" không đủ số lượng trong kho.Tồn hiện tại:"+rs.getDouble("AVAILABLE");
						}
					}
				}else{
					 return "Không thể xác định được : "+query;
				}
				rs.close();
				
				
				query =    " update ERP_KHOTT_SP_CHITIET set booked=isnull(booked,0) + "+Booked+" ,soluong = soluong + " + soluongnhap + ", AVAILABLE = AVAILABLE + " + available + " " +
							" where   KHOTT_FK = '" + khott_fk + "' and SANPHAM_FK = " +SANPHAM_FK + " " +
							" and  ltrim(rtrim(SOLO)) = '" + solo.trim() + "'   "+(KHUVUCKHO_FK.length()>0? "and khuvuckho_fk="+KHUVUCKHO_FK : " AND khuvuckho_fk IS NULL " )+ "   ";
 
						if(db.updateReturnInt(query)!=1)
						{
							 return "Không thể cập nhật ERP_KHOTT_SP_CHITIET " + query;
				}
				return "";
		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp_Chitiet : "+er.getMessage();
		}
	}


	public String Update_Kho_Sp_Chitiet_TrangThai(dbutils db, String khott_fk,
			String SANPHAM_FK, double soluongnhap,double Booked,double available, double DONGIAMUA,
			String solo, String vitri,String KHUVUCKHO_FK, String NgayBatDau ,String trangthai) {
		
 
		// TODO Auto-generated method stub
		/*
		 * Phương thức này là cập nhật số lượng nhập kho vào bảng ERP_KHOTT_SP_CHITIET_TRANGTHAI ,TRANGTHAI=-1 LÀ HÀNG KHÔNG ĐẠT,TRANGTHAI=0 LÀ HÀNG ĐẠT ,ĐỂ CHO CÁC BÁC VÀO CHUYỂN KHO CHỈ LẤY SỐ LƯỢNG ĐẠT THÔI
		 * Hàng không đạt chất lượng chỉ có trong kho chờ xử lý
		 */
		NgayBatDau="2015-01-01";
		try{
				
				String	query = " update ERP_KHOTT_SP_CHITIET_TRANGTHAI set booked =isnull(booked,0)+ "+Booked+",soluong = soluong + " + soluongnhap + ", AVAILABLE = AVAILABLE + " + available + " " +
							" where   KHOTT_FK = '" + khott_fk + "' and SANPHAM_FK = " +SANPHAM_FK + " " +
							" and  ltrim(rtrim(SOLO)) = '" + solo.trim() + "'  "+(KHUVUCKHO_FK.length()>0? "and khuvuckho_fk="+KHUVUCKHO_FK : "" )+"  and NGAYBATDAU='"+NgayBatDau+"' AND TRANGTHAI="+trangthai+"";
				 
				
				if(db.updateReturnInt(query)!=1)
				{
					 return "Không thể cập nhật ERP_KHOTT_SP_CHITIET " + query;
				}
				return "";
				
		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp_Chitiet_TrangThai : "+er.getMessage();
		}
	}


	public boolean IsKhoNhoGiaCong(String khoid, dbutils db) {
		// TODO Auto-generated method stub
		try{
			if(khoid.length() >0){
			 String query=" select  ISNULL(LOAI,'') as LOAI from ERP_KHOTT WHERE PK_SEQ= "+khoid;
			 ResultSet rs=db.get(query);
			 if(rs.next()){
				 if(rs.getString("LOAI").equals("5")){
					 rs.close();
					 return true;
				 }
				 rs.close();
			 }
			}
		}catch(Exception er){
			//er.printStackTrace();
			return false;
		}
		return false;
	}
	 
	 
	public String Update_Kho_Sp_Chitiet_NCC(dbutils db, String khott_fk,
			String SANPHAM_FK, double soluongnhap,double Booked,double available, double DONGIAMUA,
			String solo, String vitri,String KHUVUCKHO_FK, String NgayBatDau,String NCC_FK ) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		/*
		 * Phương thức này là cập nhật số lượng nhập kho vào bảng ERP_KHOTT_SP_CHITIET ,cả đạt chất lượng và không đạt chất lượng.
		 * Hàng không đạt chất lượng chỉ có trong kho chờ xử lý
		 */
		NgayBatDau="2015-01-01";
		try{
			
		String	 
					query = " update ERP_KHOTT_SP_CHITIET_NCC set booked=isnull(booked,0) + "+Booked+" ,soluong = soluong + " + soluongnhap + ", AVAILABLE = AVAILABLE + " + available + " " +
							" where  NCC_FK="+NCC_FK +" and  KHOTT_FK = '" + khott_fk + "' and SANPHAM_FK = " +SANPHAM_FK + " " +
							" and  ltrim(rtrim(SOLO)) = '" + solo.trim() + "'   and khuvuckho_fk="+KHUVUCKHO_FK+" and NGAYBATDAU='"+NgayBatDau+"' ";
				 
				 
			 
				if(db.updateReturnInt(query)!=1)
				{
					 return "Không thể cập nhật ERP_KHOTT_SP_CHITIET_NCC " + query;
				}
				return "";
		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp_Chitiet : "+er.getMessage();
		}
	}


	public double getChiPhiLuukho(String khott_fk, String ngaybatdau,
			String ngayketthuc, dbutils db) {
		// TODO Auto-generated method stub
/*				try{
					String query="SELECT LOAIKHO,DONGIA FROM ERP_KHO_CAUHINH_CHIPHI WHERE LOAIKHO='1' AND KHOTT_FK="+khott_fk;
					ResultSet rs=db.get(query);
					if(rs.next()){
						 
							//lấy ra được khoảng thời gian từ ngày tới ngày
							query="SELECT DATEDIFF(day, '"+ngaybatdau+"',  '"+ngayketthuc+"') as songay ";
							ResultSet rsngay=db.get(query);
							if(rsngay==null){
								//-2 không xác định từ ngày tới ngày
								return -2;
							}
							int songay=0;
							if(rsngay.next()){
								songay=rsngay.getInt("songay");
							}
							if(songay >0){
							 return songay * rs.getDouble("DONGIA");
							}else{
								return 0;
							}
					 
					}
					rs.close();
				}catch(Exception er){
					
					return -1;
				}*/
		return 0;
	}


	public double getChiPhiCapdong(String khott_fk, dbutils db) {
		// TODO Auto-generated method stub
		/*try{
			String query=" SELECT LOAIKHO,DONGIA FROM ERP_KHO_CAUHINH_CHIPHI  WHERE LOAIKHO='0' AND KHOTT_FK="+khott_fk;
			ResultSet rs=db.get(query);
			if(rs.next()){
					return rs.getDouble("DONGIA");
			}
			rs.close();
		}catch(Exception er){
			
			return -1;
		}*/
		return 0;
	}


	public ResultSet getRsKhu(String khottId, dbutils db) {
		// TODO Auto-generated method stub
		if(khottId.length()>0){
		String query="SELECT PK_SEQ,TEN FROM ERP_KHUVUCKHO  WHERE KHOTT_FK="+khottId;
		return db.getScrol(query);
		}
		return null;
	}


	public boolean getIsKhoGiaCong(dbutils db, String khottId) {
		// TODO Auto-generated method stub
		boolean bien=false;
			if(khottId.length()>0){
				// HIỆN TẠI HỆ THỐNG CHƯA CÓ KHO GIA CÔNG
				String query="SELECT PK_SEQ  FROM ERP_KHOTT  WHERE LOAI IN ('10','11') and  PK_SEQ="+khottId;
				ResultSet rs  = db.get(query);
				try{	
					if(rs.next()){
						bien=true;
					}else{
						bien=false;
					}
					rs.close();
				}catch(Exception er){
					
				}
					
			}
			return bien;
	}

	
	public String Update_NPP_Kho_Sp_Chitiet_trangthai(String ngaychungtu ,String nghiepvu ,Idbutils db, String khott_fk, String spId,String npp_fk,String kbh_fk,
			String solo, String ngaysanxuat, String ngayhethan, String ngaynhapkho,double soluong ,double booked,double available , double dongia,String trangthaisp) {
		try{
			if(ngayhethan==null || ngayhethan.length() != 10){
				return "Không xác định được ngày hết hạn của ID sản phẩm :"+spId+" và số lô : "+solo;
			}
			
			String query="  select sanpham_fk ,available,soluong , sp.ma+ ' '+ sp.ten as ten , ISNULL(KHO.GIAMUA,0) AS GIATON   " +
					"  from NHAPP_KHO_CHITIET_TRANGTHAI kho " +
					"  inner join sanpham sp  on kho.sanpham_fk=sp.pk_seq  where NHOMKENH_FK ="+kbh_fk+" AND NPP_FK="+npp_fk+" AND  " +
					"  KHO_FK="+khott_fk+" and sanpham_fk= "+spId +" and  solo = N'"+  solo +"' ";

			double available_ton=0;
			double giaton=0;
			double soluongton=0;

			ResultSet rsCheck = db.get(query);
			if(rsCheck.next()){
				soluongton=rsCheck.getDouble("soluong");
				available_ton=rsCheck.getDouble("available");
				giaton=rsCheck.getDouble("GIATON");

				if(available < 0 && available_ton < (-1)*available ){
					return "Số lượng tồn hiện tại trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

				if(soluong < 0 && soluongton <(-1)*soluong ){
					return "Số lượng tồn  trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}


				query = " Update NHAPP_KHO_CHITIET set booked=isnull(booked,0)+"+booked+" , soluong =ISNULL(soluong,0) + " + soluong + ", " +
						" AVAILABLE = ISNULL(AVAILABLE,0) + " + available + ", GIAMUA="+(giaton >0?giaton:dongia)+"  "+
						"  where NHOMKENH_FK="+kbh_fk+" AND NPP_FK="+npp_fk+" AND  " +
						"  KHO_FK="+khott_fk+" and sanpham_fk= "+spId +"  and  solo = N'"+  solo +"' ";

			}else{
				query=  " INSERT INTO NHAPP_KHO_CHITIET ( KHO_FK,SANPHAM_FK,NPP_FK,NHOMKENH_FK, SOLO, NGAYSANXUAT, NGAYHETHAN, NGAYNHAPKHO,GIAMUA,SOLUONG,BOOKED,AVAILABLE ) VALUES  " +
						" ("+khott_fk+","+ spId+","+npp_fk+","+kbh_fk+", N'"+ solo +"', '"+ ngaysanxuat +"', '"+ ngayhethan +"', '"+ ngaynhapkho +"',"+dongia+","+soluong+","+booked+","+available+")";

				if(available < 0 && available_ton < (-1)*available ){
					return "Số lượng tồn hiện tại trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+available_ton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

				if(soluong < 0 && soluongton <(-1)*soluong ){
					return "Số lượng tồn  trong kho của sản phẩm : "+rsCheck.getString("ten") + "  ["+soluongton+"] không đủ để trừ kho,vui lòng kiểm tra lại xuất nhập tồn của sản phẩm này " ;
				}

			}
			rsCheck.close();

			int resultInt = db.updateReturnInt(query);
			if(resultInt != 1)
			{
				return  " Không thể cập nhật NHAPP_KHO_CHITIET " + query;

			}

		}catch(Exception er){
			er.printStackTrace();
			return  "không thể thực hiện cập nhật kho  Util.Nhap_Kho_Sp : "+er.getMessage();
		}
		return "";
	}

	public String Update_Kho_Sp_NCC(String string, String ngayTieuHao,dbutils db, String khott_fk, String spid, double soluonct, 
			double booked, double available, int i, String ncc_fk,	String iDMARQUETTE, String KHUVUCKHO_FK,String solo, String mathung, String mame, String maphieu, String phieueo, String maphieudinhtinh, String bin_fk) {
		// TODO Auto-generated method stub
		try{
			String query =  " update ERP_KHOTT_SP_CHITIET  set booked=isnull(booked,0) + "+booked+" ,soluong = soluong + " + soluonct + ", AVAILABLE = AVAILABLE + " + available + " " +
							" where  DOITUONGID="+ncc_fk +" AND LOAIDOITUONG='0' and  KHOTT_FK = '" + khott_fk + "' and SANPHAM_FK = " +spid + " " +
							" and  ltrim(rtrim(SOLO)) = '" + solo.trim() + "' and isnull(maphieu,'')='"+maphieu+"' and isnull(phieueo,'')='"+phieueo+"' and isnull(maphieudinhdinh,'') ='"+maphieudinhtinh+"' and mathung='"+mathung+"' and mame='"+mame+"' and isnull(CAST(bin_fk as nvarchar(12)),'')='"+bin_fk+"' " ;
			 
			
			if(iDMARQUETTE!=null && iDMARQUETTE.length() >0){
				query+=" and IDMARQUETTE="+iDMARQUETTE+" ";
				}
				
	 
 
					if(db.updateReturnInt(query)!=1)
					{
						 return "Không thể cập nhật ERP_KHOTT_SP_CHITIET_NCC " + query;
				}
				return "";
		}catch(Exception er){
			//er.printStackTrace();
			return er.getMessage();
		}
		
	}
	public String getLoaikho(String khoid, dbutils db) {
		if(khoid!=null && khoid.length()>0){
			// HIỆN TẠI HỆ THỐNG CHƯA CÓ KHO GIA CÔNG
			String query="SELECT isnull(loai,'') as loai  FROM KHO  WHERE  PK_SEQ="+khoid;
			ResultSet rs  = db.get(query);
			try{	
				if(rs.next()){
					return rs.getString("loai");
				} 

			}catch(Exception er){
				er.printStackTrace();
				return "";
			}

		}
		return "";
	}
}