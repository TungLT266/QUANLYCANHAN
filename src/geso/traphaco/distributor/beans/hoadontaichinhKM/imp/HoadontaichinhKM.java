package geso.traphaco.distributor.beans.hoadontaichinhKM.imp;

import geso.traphaco.distributor.beans.donhang.ISanpham;
import geso.traphaco.distributor.beans.donhang.imp.Sanpham;
import geso.traphaco.distributor.beans.hoadontaichinhKM.IHoadontaichinhKM;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

public class HoadontaichinhKM implements IHoadontaichinhKM
{
	String userId;
	String id;
	
	String[] tichluy_scheme;
	String[] tichluy_tongtien;
	String[] tichluy_vat;
	
	String ngayxuatHD;
	
	String ngayghinhanCN;
	String ghichu;
	
	String kyhieuhoadon;
	String sohoadon;
	String hinhthuctt;
	String nguoimua;
	String innguoimua;
	
	boolean aplaikm;
	boolean cokm;
	float ttCKKM;
	
	///trakhuyen mai
	Hashtable<String, Float> scheme_tongtien = new Hashtable<String, Float>();
	Hashtable<String, Float> scheme_chietkhau = new Hashtable<String, Float>();
	List<ISanpham> scheme_sanpham = new ArrayList<ISanpham>();
	
	String chietkhau;

	String loaidonhang;  //0 đơn đặt hàng, 1 đơn hàng khuyến mại ứng hàng, 3 đơn hàng khuyến mại tích lũy, 4 đơn hàng trưng bày, 5 đơn hàng khác
	
	String msg;
	String trangthai;
	String khoNhanId;
	ResultSet khoNhanRs;
	
	String nppId;
	ResultSet nppRs;
	
	String khId;
	ResultSet khRs;
	
	String ddhId;
	ResultSet ddhRs;
	
	String[] DonDatHang;
	
	double tienck;
	double tienSauCK;
	double tienVAT;
	double tienKM;
	double tienSauKM;
	float tongtiensauVAT;
	
	Hashtable<String, String> sanpham_soluong;

	String[] spId;
	String[] spMa;
	String[] spTen;
	String[] spDonvi;
	String[] spDongia;
	String[] spChietkhau;
	String[] spSoluong;
	String[] spLoai;
	String[] spSCheme;
	String[] spVat;
	String[] spThue;
	
	
	String[] dhCk_diengiai;
	String[] dhCk_giatri;
	String[] dhCk_loai;
	
	dbutils db;
	
	public HoadontaichinhKM()
	{
		this.id = "";
		this.ngayxuatHD = getDateTime();
		this.ngayghinhanCN = getDateTime();
		this.ghichu = "";
		this.kyhieuhoadon = "";
		this.hinhthuctt = "";
		this.sohoadon= "";
		this.khoNhanId = "";
		this.nppId = "";
		this.chietkhau="";
		this.khId ="";
		this.msg = "";
		this.trangthai = "0";
		this.ddhId = "";
		this.tienck = 0;
		this.tienSauCK = 0;
		this.tienVAT = 0;
		this.tienKM = 0;
		this.tienSauKM = 0;
		this.tongtiensauVAT=0;
		
		this.ttCKKM = 0;
		this.aplaikm = false;
		this.cokm = false;
		
		this.loaidonhang = "0";
		this.nguoimua = "";
		this.innguoimua = "";
		
		this.sanpham_soluong = new Hashtable<String, String>();
		this.DonDatHang=  new String[0];
		this.donhangkhac="";
		this.db = new dbutils();
	}
	
	public HoadontaichinhKM(String id)
	{
		this.id = id;
		this.ngayxuatHD = getDateTime();
		this.ngayghinhanCN = getDateTime();
		this.ghichu = "";
		this.kyhieuhoadon = "";
		this.sohoadon = "";
		this.khoNhanId = "";
		this.hinhthuctt = "";
		this.nppId = "";
		this.chietkhau="";
		this.msg = "";
		this.trangthai = "0";
		this.khId ="";
		this.ddhId = "";
		this.tienck = 0;
		this.tienSauCK = 0;
		this.tienVAT = 0;
		this.tienKM = 0;
		this.tienSauKM = 0;
		this.tongtiensauVAT=0;

		this.loaidonhang = "0";
		this.aplaikm = false;
		this.cokm = false;
		this.nguoimua= "";
		
		this.innguoimua = "";
		
		this.sanpham_soluong = new Hashtable<String, String>();
		this.donhangkhac="";
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

	public String getId() 
	{
		return this.id;
	}

	public void setId(String Id) 
	{
		this.id = Id;
	}

	public String getNgayxuatHD() 
	{
		return this.ngayxuatHD;
	}

	public void setNgayxuatHD(String ngayxuatHD) 
	{
		this.ngayxuatHD = ngayxuatHD;
	}

	public String getKhoNhapId()
	{
		return this.khoNhanId;
	}

	public void setKhoNhapId(String khonhaptt) 
	{
		this.khoNhanId = khonhaptt;
	}

	public ResultSet getKhoNhapRs() 
	{
		return this.khoNhanRs;
	}

	public void setKhoNHapRs(ResultSet khonhapRs) 
	{
		this.khoNhanRs = khonhapRs;
	}
	
	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}
	private void getNppInfo()
	{		
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
	}
	
	public void createRs() 
	{
		System.out.println("Uset "+this.userId);
		this.getNppInfo();
		String query = "  select PK_SEQ, MAFAST + '-' + TEN as TEN" +
				       "  from KHACHHANG" +
				       "  where TRANGTHAI = '1' and NPP_FK= "+ this.nppId +" and KBH_FK=100025 " +
				       "        and pk_seq in (select KHACHHANG_FK from DONHANG where trangthai in (1,4)) ";
		this.khRs = db.get(query);
		
	 		
	if(this.id.length() <=0)
	{
	  // TỰ TẠO SỐ HÓA ĐƠN CỦA USER
			
			int kbDLN =0;
			String chuoiHD= "";
			long sohoadontu= 0;
			String sohoadonden= "";
			
			try
			{
				
				// Kiểm tra npp nếu là Đối tác thì không dùng Số hóa đơn của hệ thống
				query = " select loaiNPP " +
						" from NHAPHANPHOI " +
						" where pk_seq = '" + nppId + "' ";	
				ResultSet rsNpp = db.get(query);
				String loainpp= "";
				if(rsNpp!= null)
				{
					while(rsNpp.next())
					{
						loainpp = rsNpp.getString("loaiNPP");
					}
					rsNpp.close();
				}
					
				if(loainpp.equals("4") || loainpp.equals("5"))	// DOI TAC VA CHI NHANH CUA DOI TAC
				{
					kyhieuhoadon = "NA";
					sohoadon = "NA";
				}
			    else
				{
			    	if(this.nppId.equals("100002")) // CN HÀ NỘI DÙNG MẪU 2(HO)
			    	{
						//KIEM TRA USER ĐÃ KHAI BAO SO HOA DON TRONG DLN CHUA
						query= " select count(pk_seq) as dem" +
							   " from NHANVIEN" +
							   " where pk_seq= '"+ this.userId+"' and  sohoadontu2 != '' and sohoadonden2 != ''" +
							   "       and isnull(kyhieu2,'') != ''  ";
						ResultSet KTDLN = db.get(query);
						if(KTDLN!= null)
						{
							while(KTDLN.next())
							{
								kbDLN = KTDLN.getInt("dem");
							}
							KTDLN.close();
						}
						
						if(kbDLN <= 0 )
						{
							this.msg = "Vui lòng khai báo Số hóa đơn trong Thiết lập dữ liệu nền > Số hóa đơn cho user này ";
							
						}
						else
						{
							// LAY KY HIEU HOA DON ,SOHDTU TRONG DLN
							query=  " select kyhieu2 as kyhieuhoadon, sohoadontu2 as sohoadontu, sohoadonden2 as sohoadonden " +
									" from NHANVIEN" +
									" where pk_seq= '"+ this.userId +"'";
							ResultSet rsLayDL =  db.get(query);
							if(rsLayDL != null)
							{
								while(rsLayDL.next())
								{
									this.kyhieuhoadon= rsLayDL.getString("kyhieuhoadon");
									sohoadontu = rsLayDL.getLong("sohoadontu");
									sohoadonden = rsLayDL.getString("sohoadonden");
								}
								rsLayDL.close();
							}
				
							
							// KIEM TRA KYHIEUHOADON DA DUOC DUNG CHUA
							 //OTC
							query ="  select count(pk_seq) as dem" +
									" from HOADON where RTRIM(LTRIM(kyhieu)) = '"+ this.kyhieuhoadon +"' and cast(sohoadon as numeric(18,0)) >= "+ sohoadontu +" and cast(sohoadon as numeric(18,0))<= " + Integer.parseInt(sohoadonden) + " " +
									"      and trangthai != 3  and nguoisua= "+ this.userId +"  and mauhoadon = 2 ";
							System.out.println("Câu kiểm tra SHD "+query);
							ResultSet KiemTra = db.get(query);
							int check = 0;
							if(KiemTra != null)
							{
								while(KiemTra.next())
								{
									check = KiemTra.getInt("dem");
								}
								KiemTra.close();
							}
							
							//ETC 
							
							query = " select count(pk_seq) as dem" +
									" from ERP_HOADONNPP " +
									" where RTRIM(LTRIM(kyhieu)) = '"+ this.kyhieuhoadon +"' and cast(sohoadon as numeric(18,0)) >= "+ sohoadontu +" and cast(sohoadon as numeric(18,0))<= " + Integer.parseInt(sohoadonden) + " " +
									"        and trangthai != '3' and nguoisua= "+ this.userId +" and mauhoadon = 2 ";
							
							System.out.println("Câu kiểm tra SHD "+query);
							ResultSet KiemTraETC = db.get(query);
							int check_ETC = 0;
							if(KiemTraETC != null)
							{
								while(KiemTraETC.next())
								{
									check_ETC = KiemTraETC.getInt("dem");
								}
								KiemTraETC.close();
							}
				
							// LAY SOIN MAX	
							if(check <= 0 && check_ETC <= 0)
							{
								chuoiHD = ("000000"+ sohoadontu);
								chuoiHD = chuoiHD.substring(chuoiHD.length() - 7, chuoiHD.length());
							}
							else
							{// LAY SOIN MAX TRONG HOADON : 
								  //OTC
								query = " select  MAX(cast(SOHOADON as numeric)) as SOIN_MAX" +
										" from HOADON" +
										" where RTRIM(LTRIM(KYHIEU)) ='"+ this.kyhieuhoadon +"' and cast(sohoadon as numeric(18,0)) >= "+ sohoadontu +" and cast(sohoadon as numeric(18,0))<= " + Integer.parseInt(sohoadonden) + " " +
										"       and trangthai != 3  and nguoisua= "+ this.userId +" and mauhoadon = 2 ";
								System.out.println("Câu lấy shd max "+query);
								ResultSet laySOIN = db.get(query);
							    long soinMAX_OTC= 0;
								if(laySOIN!= null)
								{
									while(laySOIN.next())
									{
										soinMAX_OTC = laySOIN.getLong("SOIN_MAX");
										
									}laySOIN.close();
								}
								
								
								 //ETC
								query = " select  MAX(cast(SOHOADON as numeric)) as SOIN_MAX" +
										" from ERP_HOADONNPP " +
										" where RTRIM(LTRIM(KYHIEU)) ='"+ this.kyhieuhoadon +"' and cast(sohoadon as numeric(18,0)) >= "+ sohoadontu +" and cast(sohoadon as numeric(18,0))<= " + Integer.parseInt(sohoadonden) + " " +
										"       and trangthai != '3'  and nguoisua= "+ this.userId +" and mauhoadon = 2 ";
								System.out.println("Câu lấy shd max "+query);
								ResultSet laySOIN_ETC = db.get(query);
							    long soinMAX_ETC= 0;
								if(laySOIN_ETC!= null)
								{
									while(laySOIN_ETC.next())
									{
										soinMAX_ETC = laySOIN_ETC.getLong("SOIN_MAX");
										
									}laySOIN_ETC.close();
								}
								
								if(soinMAX_OTC > soinMAX_ETC) 
								{
									chuoiHD = ("000000"+ (soinMAX_OTC >0 ? (soinMAX_OTC +1) :"1"));
								}else
								{
									chuoiHD = ("000000"+ (soinMAX_ETC >0 ? (soinMAX_ETC +1) :"1"));
								}
								
								chuoiHD = chuoiHD.substring(chuoiHD.length() - 7, chuoiHD.length());
								
							}
							
							this.sohoadon =  chuoiHD;
							
							if(Integer.parseInt(this.sohoadon) > Integer.parseInt(sohoadonden))
							{ 
								this.msg = "Số hóa đơn đã vượt quá Số hóa đơn đến trong dữ liệu nền. Vui lòng khai báo ký hiệu hóa đơn mới ! ";
							}
						 }
					   
			    	}
			    	else  // CÒN LẠI DÙNG MẪU 1 (CN)
			    	{			    		
					//KIEM TRA USER ĐÃ KHAI BAO SO HOA DON TRONG DLN CHUA
					query= " select count(pk_seq) as dem" +
						   " from NHANVIEN" +
						   " where pk_seq= '"+ this.userId+"' and  sohoadontu!= '' and sohoadonden != ''" +
						   "       and isnull(kyhieu,'') != ''  ";
					ResultSet KTDLN = db.get(query);
					if(KTDLN!= null)
					{
						while(KTDLN.next())
						{
							kbDLN = KTDLN.getInt("dem");
						}
						KTDLN.close();
					}
					
					if(kbDLN <= 0 )
					{
						this.msg = "Vui lòng khai báo Số hóa đơn trong Thiết lập dữ liệu nền > Số hóa đơn cho user này ";
						
					}
					else
					{
						// LAY KY HIEU HOA DON ,SOHDTU TRONG DLN
						query=  " select kyhieu as kyhieuhoadon, sohoadontu, sohoadonden " +
								" from NHANVIEN" +
								" where pk_seq= '"+ this.userId +"'";
						ResultSet rsLayDL =  db.get(query);
						if(rsLayDL != null)
						{
							while(rsLayDL.next())
							{
								this.kyhieuhoadon= rsLayDL.getString("kyhieuhoadon");
								sohoadontu = rsLayDL.getLong("sohoadontu");
								sohoadonden = rsLayDL.getString("sohoadonden");
							}
							rsLayDL.close();
						}
			
						
						// KIEM TRA KYHIEUHOADON DA DUOC DUNG CHUA
						 //OTC
						query ="  select count(pk_seq) as dem" +
								" from HOADON where RTRIM(LTRIM(kyhieu)) = '"+ this.kyhieuhoadon +"' and cast(sohoadon as numeric(18,0)) >= "+ sohoadontu +" and cast(sohoadon as numeric(18,0))<= " + Integer.parseInt(sohoadonden) + " " +
								"      and trangthai != 3  and nguoisua= "+ this.userId +"  and mauhoadon = 1 ";
						System.out.println("Câu kiểm tra SHD "+query);
						ResultSet KiemTra = db.get(query);
						int check = 0;
						if(KiemTra != null)
						{
							while(KiemTra.next())
							{
								check = KiemTra.getInt("dem");
							}
							KiemTra.close();
						}
						
						//ETC 
						
						query = " select count(pk_seq) as dem" +
								" from ERP_HOADONNPP " +
								" where RTRIM(LTRIM(kyhieu)) = '"+ this.kyhieuhoadon +"' and cast(sohoadon as numeric(18,0)) >= "+ sohoadontu +" and cast(sohoadon as numeric(18,0))<= " + Integer.parseInt(sohoadonden) + " " +
								"        and trangthai != '3' and nguoisua= "+ this.userId +" and mauhoadon = 1 ";
						
						System.out.println("Câu kiểm tra SHD "+query);
						ResultSet KiemTraETC = db.get(query);
						int check_ETC = 0;
						if(KiemTraETC != null)
						{
							while(KiemTraETC.next())
							{
								check_ETC = KiemTraETC.getInt("dem");
							}
							KiemTraETC.close();
						}
			
						// LAY SOIN MAX	
						if(check <= 0 && check_ETC <= 0)
						{
							chuoiHD = ("000000"+ sohoadontu);
							chuoiHD = chuoiHD.substring(chuoiHD.length() - 7, chuoiHD.length());
						}
						else
						{// LAY SOIN MAX TRONG HOADON : 
							  //OTC
							query = " select  MAX(cast(SOHOADON as numeric)) as SOIN_MAX" +
									" from HOADON" +
									" where RTRIM(LTRIM(KYHIEU)) ='"+ this.kyhieuhoadon +"' and cast(sohoadon as numeric(18,0)) >= "+ sohoadontu +" and cast(sohoadon as numeric(18,0))<= " + Integer.parseInt(sohoadonden) + " " +
									"       and trangthai != 3  and nguoisua= "+ this.userId +" and mauhoadon = 1 ";
							System.out.println("Câu lấy shd max "+query);
							ResultSet laySOIN = db.get(query);
						    long soinMAX_OTC= 0;
							if(laySOIN!= null)
							{
								while(laySOIN.next())
								{
									soinMAX_OTC = laySOIN.getLong("SOIN_MAX");
									
								}laySOIN.close();
							}
							
							
							 //ETC
							query = " select  MAX(cast(SOHOADON as numeric)) as SOIN_MAX" +
									" from ERP_HOADONNPP " +
									" where RTRIM(LTRIM(KYHIEU)) ='"+ this.kyhieuhoadon +"' and cast(sohoadon as numeric(18,0)) >= "+ sohoadontu +" and cast(sohoadon as numeric(18,0))<= " + Integer.parseInt(sohoadonden) + " " +
									"       and trangthai != '3'  and nguoisua= "+ this.userId +" and mauhoadon = 1 ";
							System.out.println("Câu lấy shd max "+query);
							ResultSet laySOIN_ETC = db.get(query);
						    long soinMAX_ETC= 0;
							if(laySOIN_ETC!= null)
							{
								while(laySOIN_ETC.next())
								{
									soinMAX_ETC = laySOIN_ETC.getLong("SOIN_MAX");
									
								}laySOIN_ETC.close();
							}
							
							if(soinMAX_OTC > soinMAX_ETC) 
							{
								chuoiHD = ("000000"+ (soinMAX_OTC >0 ? (soinMAX_OTC +1) :"1"));
							}else
							{
								chuoiHD = ("000000"+ (soinMAX_ETC >0 ? (soinMAX_ETC +1) :"1"));
							}
							
							chuoiHD = chuoiHD.substring(chuoiHD.length() - 7, chuoiHD.length());
							
						}
						
						this.sohoadon =  chuoiHD;
						
						if(Integer.parseInt(this.sohoadon) > Integer.parseInt(sohoadonden))
						{ 
							this.msg = "Số hóa đơn đã vượt quá Số hóa đơn đến trong dữ liệu nền. Vui lòng khai báo ký hiệu hóa đơn mới ! ";
						}
					 }
				    }
				   }
			}catch(Exception e)
			{
				this.msg = "Lỗi xảy ra trong quá trình lấy Số hóa đơn";
				e.printStackTrace();
			}
		
		if(this.khId.trim().length() > 0 )
		{		
			// LAY TEN NGUOI MUA TRONG DLN
			  query =" select isnull(chucuahieu,'') as nguoimua from KHACHHANG where pk_seq= '"+ this.khId +"' ";
				ResultSet rsLayTT = db.get(query);
				if(rsLayTT!= null)
				{
					try
					{
						while(rsLayTT.next())
						{
							this.nguoimua = rsLayTT.getString("nguoimua");
						}
						rsLayTT.close();
					}catch (Exception e) { e.printStackTrace(); }
				}	
				
			  query = 
				" select PK_SEQ , NgayNhap as NgayDonHang  " +
				" from DONHANG " +
				" where  trangthai not in (0,2) and   NPP_FK= "+ this.nppId +" AND  KHACHHANG_FK = '" + this.khId + "' " +
				"       and ( import= '1' or pk_seq in (select donhang_fk " +
				"                      from PHIEUXUATKHO_DONHANG a inner join PHIEUXUATKHO b on a.pxk_fk= b.pk_seq " +
				"                      where b.trangthai=1 and b.NPP_FK= "+ this.nppId +" ))  " +
				"       and ( pk_seq in (select donhangid " +
				"                      from Donhang_ctkm_trakm  a inner join trakhuyenmai b on a.trakmid=b.pk_seq " +
				"                      where b.loai = '3' )" +
				"              or isnull(donhangkhac,0) = 1 ) " +
				" and pk_seq not in (Select a.ddh_fk from HoaDon_ddh a inner join hoadon b on a.hoadon_fk=b.pk_seq where b.trangthai in (1,2,4) and loaihoadon ='1') " ;
				System.out.println("Câu query "+query);		
				this.ddhRs = db.get(query);
				
		}
		
		String chuoi="";
		int i=0;
		if(this.DonDatHang!=null)
		{
			while(i<this.DonDatHang.length)
			 {
				if(i==0){
					chuoi=DonDatHang[i];
				}
				else{
					chuoi=chuoi +"," +DonDatHang[i];
				}
				i=i+1;
			}
		}
		if(chuoi.length() > 0)
		{									
			 this.getTrakhuyenmai(chuoi);
		}
		
	  }
	  else // ID > 0
	  {
			String chuoi="";
			int i=0;
			if(this.DonDatHang!=null)
			{
				while(i<this.DonDatHang.length)
				 {
					if(i==0){
						chuoi=DonDatHang[i];
					}
					else{
						chuoi=chuoi +"," +DonDatHang[i];
					}
					i=i+1;
				}
			}
		  
			List<ISanpham> scheme_sp =  new ArrayList<ISanpham>();
		    query= " SELECT A.SANPHAM_FK, A.SANPHAMMA, B.TEN AS TENSP, A.SOLUONG, A.CTKM, A.DONVI, ISNULL(A.DONGIA,0) AS DONGIA, A.VAT " +
		    	   " FROM HOADON_CTKM_TRAKM A INNER JOIN SANPHAM B ON A.SANPHAM_FK= B.PK_SEQ " +
		    	   " WHERE hoadonId = '"+ this.id +"'";
		    System.out.println("Câu lấy spkm "+query);
		   ResultSet rs = db.get(query);
			
		  try
		  {
		   if(rs!= null)
		   {
			String[] param = new String[8];
			ISanpham sp = null;	

				while(rs.next())
				{
					param[0] = rs.getString("SANPHAM_FK");
					param[1] = rs.getString("SANPHAMMA");
					param[2] = rs.getString("TENSP");
					param[3] = rs.getString("SOLUONG");
					param[4] = rs.getString("DONVI");
					param[5] = rs.getString("DONGIA");
					param[6] = rs.getString("CTKM");
					param[7] = "0";
	
					sp = new Sanpham(param);
					
					sp.setVat(rs.getString("VAT"));
					scheme_sp.add(sp);

				}
				rs.close();
		   }
		  }catch(Exception e){e.printStackTrace();}
			
		  
		  this.scheme_sanpham= scheme_sp;
			
		}
	
				try
				{
					
					//INIT SOLO
					query =
							"	select a.kho_fk, a.kbh_fk, NPP_FK, SANPHAM_FK, SOLO, SUM(soluong) as soluong ,NgayHetHan  "+
							"	from	HOADON_CTKM_TRAKM_CHITIET a inner join HOADON b on b.PK_SEQ=a.hoadonID  "+
							"	where hoadonId='"+this.id+"'  "+  
							"	group by a.kho_fk, a.kbh_fk, NPP_FK, SANPHAM_FK, SOLO,NgayHetHan ";
					
					
					System.out.println("---LO DA XUAT: " + query);
					ResultSet rsSOLO = db.get(query);
					this.sanpham_soluong = new Hashtable<String, String>();
					if(rsSOLO != null)
					{
						while(rsSOLO.next())
						{
							String key = rsSOLO.getString("sanpham_fk") + "__" + rsSOLO.getString("solo") + "__" + rsSOLO.getString("ngayhethan");
							this.sanpham_soluong.put(key, rsSOLO.getString("soluong"));
						}
						rsSOLO.close();
					}
				}
				catch (Exception e) 
				{	
					e.printStackTrace();
				}
	 
	  
	}
		

								

	public void init() 
	{
		this.getNppInfo();
		String query =  "select dondathang_fk, ngayxuatHD, ISNULL(ghichu, '') as ghichu, kyhieu, sohoadon, hinhthuctt ,khachhang_fk, trangthai, isnull(nguoimua,'') as nguoimua, isnull(innguoimua,1) as innguoimua,  " +
				        " isnull(chietkhau,0) as chietkhau, isnull(tongtienbvat,0)as tongtienbvat, npp_fk, " +
				        " isnull(tongtienavat,0)as tongtienavat, isnull(vat, 0) as vat, isnull(tongtienkm, 0) as tongtienkm , " +
				        "  ISNULL( ( select DonHangKhac from DONHANG aa inner join HoaDon_DDH bb on bb.ddh_fk=aa.pk_Seq where bb.hoadon_fk=HOADON.pk_Seq ), 0) as DonHangKhac ," +
				        " (	select pxk_fk from PHIEUXUATKHO a inner join PHIEUXUATKHO_DONHANG b on b.PXK_FK=a.PK_SEQ inner join HOADON_DDH c on c.DDH_FK=b.DONHANG_FK where a.TRANGTHAI!=2 and c.HOADON_FK=HOADON.PK_SEQ) as pxkId "+
						"from HOADON  where pk_seq = '" + this.id + "'";
		System.out.println("____INIT HOADON: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.ngayxuatHD = rs.getString("ngayxuatHD");
					this.ghichu = rs.getString("ghichu");
					this.hinhthuctt = rs.getString("hinhthuctt");
					this.kyhieuhoadon = rs.getString("kyhieu");
					this.sohoadon = rs.getString("sohoadon");
					this.khId = rs.getString("khachhang_fk");
					this.nppId = rs.getString("npp_fk");
					this.trangthai = rs.getString("trangthai");
					this.nguoimua = rs.getString("nguoimua");
					this.innguoimua = rs.getString("innguoimua");
					this.donhangkhac=rs.getString("DonHangKhac");
					this.pxkId=rs.getString("pxkId");
					
				}
				rs.close();
				
				//INIT DDH
				query = "SELECT HOADON_FK, DDH_FK FROM HOADON_DDH WHERE HOADON_FK = " + this.id;
				
				rs = this.db.get(query);
				if(rs!=null)
				{
					this.DonDatHang=new String[20];
					int i= 0;
					while(rs.next())
					{
						this.DonDatHang[i] =rs.getString("DDH_FK");
						i=i+1;
					}
				}
				
				//INIT DDH
				query = "SELECT DDH_FK FROM HOADON_DDH WHERE HOADON_FK = " + this.id;
				rs = this.db.get(query);
				
				String ddh = "";
				if(rs!=null)
				{
					while(rs.next())
					{
						ddh += rs.getString("DDH_FK") + ",";
					}
					rs.close();
					
					if(ddh.trim().length() > 0)
						this.ddhId = ddh.substring(0, ddh.length() - 1);
				}
				
				
				if(this.trangthai.equals("3") || this.trangthai.equals("5") )
				{
					query = " select B.PK_SEQ ,B.NGAYNHAP AS NgayDonHang  " +
							" from HOADON_DDH A INNER JOIN DONHANG B ON A.DDH_FK = B.PK_SEQ " +
							" where A.HOADON_FK = '"+ this.id +"' AND A.HOADON_FK IN (SELECT PK_SEQ FROM HOADON WHERE LOAIHOADON= 1) ";
				
				}else
				{
					query = "select PK_SEQ ,NGAYNHAP AS NgayDonHang \n " +
							"from DONHANG \n" +
							"where  trangthai not in (0,2) and  NPP_FK= "+ this.nppId +" and  KHACHHANG_FK = '" + this.khId + "' \n" +
							"       and (import = '1' or pk_seq in (select donhang_fk \n" +
							"                      from PHIEUXUATKHO_DONHANG a inner join PHIEUXUATKHO b on a.pxk_fk= b.pk_seq \n" +
							"					   where b.trangthai=1 and b.NPP_FK= "+ this.nppId +") )  \n" +
							"       and (pk_seq in (select donhangid \n" +
							"                      from Donhang_ctkm_trakm  a inner join trakhuyenmai b on a.trakmid=b.pk_seq \n" +
							"                      where b.loai = '3' ) \n" +
							"            or isnull(donhangkhac,0) = 1 ) \n" +
							" and pk_seq not in (Select a.ddh_fk from HoaDon_ddh a inner join hoadon b on a.hoadon_fk=b.pk_seq where b.trangthai = 2 and loaihoadon = '1' and b.pk_seq!= '"+ this.id +"')  \n" +
							" union all " +
							" select PK_SEQ ,NGAYNHAP AS NgayDonHang \n"+
							" from DONHANG \n" +
							" where  trangthai not in (0,2) and  NPP_FK= "+ this.nppId +" and  KHACHHANG_FK = '" + this.khId + "' \n" +
							"        and donquatang = 1 "+
							" and pk_seq not in (Select a.ddh_fk from HoaDon_ddh a inner join hoadon b on a.hoadon_fk=b.pk_seq where b.trangthai = 2 and loaihoadon = '1' and b.pk_seq!= '"+ this.id +"')  \n";
					}
				System.out.println("Lấy đơn hàng "+query);		
					this.ddhRs = db.get(query);
			} 
			catch (Exception e) 
			{
				System.out.println("---LOI INIT: " + e.getMessage());
			}
		}

		this.createRs();
	}

	public void DBclose() 
	{
		try
		{
			if(khoNhanRs!=null)
			{
				khoNhanRs.close();
			}
			
			this.db.shutDown();
			
		}catch(Exception er){
			
		}
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getGhichu() {
		
		return this.ghichu;
	}

	public void setGhichu(String ghichu) {
		
		this.ghichu = ghichu;
	}

	public String getHinhthucTT() {
		
		return this.hinhthuctt;
	}

	public void setHinhthucTT(String hinhthucTT) {
		
		this.hinhthuctt = hinhthucTT;
	}
	
	public String[] getSpId() {
		
		return this.spId;
	}

	
	public void setSpId(String[] spId) {
		
		this.spId = spId;
	}

	
	public String[] getSpMa() {
		
		return this.spMa;
	}

	
	public void setSpMa(String[] spMa) {
		
		this.spMa = spMa;
	}

	
	public String[] getSpTen() {
		
		return this.spTen;
	}

	
	public void setSpTen(String[] spTen) {
		
		this.spTen = spTen;
	}

	
	public String[] getSpDonvi() {
		
		return this.spDonvi;
	}

	
	public void setSpDonvi(String[] spDonvi) {
		
		this.spDonvi = spDonvi;
	}

	
	public String[] getSpSoluong() {
		
		return this.spSoluong;
	}

	
	public void setSpSoluong(String[] spSoluong) {
		
		this.spSoluong = spSoluong;
	}


	public String getNppId() {
		
		return this.nppId;
	}

	
	public void setNppId(String nppId) {
		
		this.nppId = nppId;
	}

	
	public ResultSet getNppRs() {
		
		return this.nppRs;
	}

	
	public void setNppRs(ResultSet nppRs) {
		
		this.nppRs = nppRs;
	}

	
	public String getDondathangId() {
		
		return this.ddhId;
	}

	
	public void setDondathangId(String kbhId) {
		
		this.ddhId = kbhId;
	}

	
	public ResultSet getDondathangRs() {
		
		return this.ddhRs;
	}

	
	public void setDondathangRs(ResultSet ddhRs) {
		
		this.ddhRs = ddhRs;
	}

	
	public Hashtable<String, String> getSanpham_Soluong() {
		
		return this.sanpham_soluong;
	}

	
	public void setSanpham_Soluong(Hashtable<String, String> sp_soluong) {
		
		this.sanpham_soluong = sp_soluong;
	}
	
	public boolean create() 
	{
		this.getNppInfo();
		if(this.ngayxuatHD.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày xuất hóa đơn";
			return false;
		}

		if(this.DonDatHang.length <= 0)
		{
			this.msg = "Vui lòng chọn đơn đặt hàng";
			return false;
		}
				
		if(this.scheme_sanpham.size() <= 0)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm  xuất hoá đơn khuyến mãi";
			return false;
		}

		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			double tienAvat = this.tienSauCK + this.tienVAT;
			String query = "";
			String chuoi="";
			long sohoadontu=0;
			String sohoadonden="";
			int kbDLN=0;
		
			
			// Kiểm tra npp nếu là Đối tác thì không dùng Số hóa đơn của hệ thống
			query = " select loaiNPP " +
					" from NHAPHANPHOI " +
					" where pk_seq = '" + this.nppId + "' ";	
			ResultSet rsNpp = db.get(query);
			String loainpp= "";
			if(rsNpp!= null)
			{
				while(rsNpp.next())
				{
					loainpp = rsNpp.getString("loaiNPP");
				}
				rsNpp.close();
			}
				
			if(!loainpp.equals("4") && !loainpp.equals("5"))	// KHONG PHAI LA DOI TAC VA CHI NHANH CUA DOI TAC
			{
				if(this.nppId.equals("100002")) // CN HÀ NỘI DÙNG MẪU 2 (HO)
				{					
					// LAY KY HIEU HOA DON ,SOHDTU TRONG DLN
					query=  " select  sohoadontu2, sohoadonden2 " +
							" from NHANVIEN" +
							" where pk_seq= '"+ this.userId +"' " +
							"       and isnull(kyhieu2,'') = '"+ this.kyhieuhoadon +"' ";
					ResultSet rsLayDL =  db.get(query);
					if(rsLayDL != null)
					{
						while(rsLayDL.next())
						{
							sohoadontu = rsLayDL.getLong("sohoadontu2");
							sohoadonden = rsLayDL.getString("sohoadonden2");
						}
						rsLayDL.close();
					}
					if(sohoadontu == 0 || sohoadonden.trim().length() <= 0)
					{
						this.msg = " Ký hiệu "+ this.kyhieuhoadon +" không giống với ký hiệu khai báo trong dữ liệu nền/ Chưa khai báo số hóa đơn trong dữ liệu nền (Số hóa đơn) ";
						db.getConnection().rollback();
						return false;
					}
					
					// Check So hoa don sua da co dung chua
				    // OTC
					query= " select  count(pk_seq) as kiemtra " +
					       " from HOADON " +
					       " where nguoisua= '"+ this.userId +"' and RTRIM(LTRIM(sohoadon)) = '"+ this.sohoadon.trim() +"' and pk_seq != "+ this.id +" " +
					       " and trangthai != 3 and mauhoadon = 2 and RTRIM(LTRIM(isnull(kyhieu, ''))) = RTRIM(LTRIM('"+ this.kyhieuhoadon +"')) ";
					System.out.println("Cau kiem tra so hoa don "+query);
					ResultSet KtraSHD =  db.get(query);
					int ktra= 0;
					
					if(KtraSHD != null)
					{
						while(KtraSHD.next())
						{
							ktra = KtraSHD.getInt("kiemtra");
						}
						KtraSHD.close();
					}
				
					// ETC
					query= " select  count(pk_seq) as kiemtra " +
					       " from ERP_HOADONNPP " +
					       " where nguoisua= '"+ this.userId +"' and RTRIM(LTRIM(sohoadon)) = '"+ this.sohoadon.trim() +"'" +
					       "       and trangthai != 3 and pk_seq != "+ this.id +" and mauhoadon = 2 and RTRIM(LTRIM(isnull(kyhieu, ''))) = RTRIM(LTRIM('"+ this.kyhieuhoadon +"')) " +
					       "  ";
					System.out.println("Cau kiem tra so hoa don "+query);
					ResultSet KtraSHD_ETC =  db.get(query);
					int ktra_ETC= 0;
					
					if(KtraSHD_ETC != null)
					{
						while(KtraSHD_ETC.next())
						{
							ktra_ETC = KtraSHD_ETC.getInt("kiemtra");
						}
						KtraSHD_ETC.close();
					}
				
					if(Integer.parseInt(sohoadonden.trim()) <  Integer.parseInt(this.sohoadon.trim()))
					{
						this.msg = "Số hóa đơn này đã vượt quá số hóa đơn đến trong dữ liệu nền. Vui lòng thiết lập lại số hóa đơn ";
						db.getConnection().rollback();
						return false;
					}
					else if (ktra > 0 || ktra_ETC > 0 )
					{
						this.msg = "Số hóa đơn này đã được sử dụng. Vui lòng đánh số hóa đơn khác. ";
						db.getConnection().rollback();
						return false;
					}
					else if (this.sohoadon.trim().length() != 7 )
					{
						this.msg = "Số hóa đơn phải đủ 7 ký tự. Vui lòng kiểm tra lại.";
						db.getConnection().rollback();
						return false;
					}
					
				  
				}
				else // CÒN LẠI DÙNG MẪU 1 (CN)
				{
			
				// LAY KY HIEU HOA DON ,SOHDTU TRONG DLN
				query=  " select  sohoadontu, sohoadonden " +
						" from NHANVIEN" +
						" where pk_seq= '"+ this.userId +"' " +
						"       and isnull(kyhieu,'') = '"+ this.kyhieuhoadon +"' ";
				ResultSet rsLayDL =  db.get(query);
				if(rsLayDL != null)
				{
					while(rsLayDL.next())
					{
						sohoadontu = rsLayDL.getLong("sohoadontu");
						sohoadonden = rsLayDL.getString("sohoadonden");
					}
					rsLayDL.close();
				}
				if(sohoadontu == 0 || sohoadonden.trim().length() <= 0)
				{
					this.msg = " Ký hiệu "+ this.kyhieuhoadon +" không giống với ký hiệu khai báo trong dữ liệu nền/ Chưa khai báo số hóa đơn trong dữ liệu nền (Số hóa đơn) ";
					db.getConnection().rollback();
					return false;
				}
				
				// Check So hoa don sua da co dung chua
			    // OTC
				query= " select  count(pk_seq) as kiemtra " +
				       " from HOADON " +
				       " where nguoisua= '"+ this.userId +"' and RTRIM(LTRIM(sohoadon)) = '"+ this.sohoadon.trim() +"' and pk_seq != "+ this.id +" " +
				       " and trangthai != 3 and mauhoadon = 1 and RTRIM(LTRIM(isnull(kyhieu, ''))) = RTRIM(LTRIM('"+ this.kyhieuhoadon +"')) ";
				System.out.println("Cau kiem tra so hoa don "+query);
				ResultSet KtraSHD =  db.get(query);
				int ktra= 0;
				
				if(KtraSHD != null)
				{
					while(KtraSHD.next())
					{
						ktra = KtraSHD.getInt("kiemtra");
					}
					KtraSHD.close();
				}
			
				// ETC
				query= " select  count(pk_seq) as kiemtra " +
				       " from ERP_HOADONNPP " +
				       " where nguoisua= '"+ this.userId +"' and RTRIM(LTRIM(sohoadon)) = '"+ this.sohoadon.trim() +"'" +
				       "       and trangthai != 3 and pk_seq != "+ this.id +" and mauhoadon = 1 and RTRIM(LTRIM(isnull(kyhieu, ''))) = RTRIM(LTRIM('"+ this.kyhieuhoadon +"')) " +
				       "  ";
				System.out.println("Cau kiem tra so hoa don "+query);
				ResultSet KtraSHD_ETC =  db.get(query);
				int ktra_ETC= 0;
				
				if(KtraSHD_ETC != null)
				{
					while(KtraSHD_ETC.next())
					{
						ktra_ETC = KtraSHD_ETC.getInt("kiemtra");
					}
					KtraSHD_ETC.close();
				}
			
				if(Integer.parseInt(sohoadonden.trim()) <  Integer.parseInt(this.sohoadon.trim()))
				{
					this.msg = "Số hóa đơn này đã vượt quá số hóa đơn đến trong dữ liệu nền. Vui lòng thiết lập lại số hóa đơn ";
					db.getConnection().rollback();
					return false;
				}
				else if (ktra > 0 || ktra_ETC > 0 )
				{
					this.msg = "Số hóa đơn này đã được sử dụng. Vui lòng đánh số hóa đơn khác. ";
					db.getConnection().rollback();
					return false;
				}
				else if (this.sohoadon.trim().length() != 7 )
				{
					this.msg = "Số hóa đơn phải đủ 7 ký tự. Vui lòng kiểm tra lại.";
					db.getConnection().rollback();
					return false;
				}
				
			  }
			}
			
			String mauhoadon = "1";
			if(this.nppId.equals("100002")) mauhoadon = "2";
			
			 query = " insert HOADON(khachhang_fk, ngayxuatHD, trangthai, ngaytao, nguoitao, ngaysua, nguoisua, kyhieu, sohoadon, hinhthuctt ," +
					       " chietkhau, tongtienbvat, tongtienavat, vat, ghichu, tongtienkm, npp_fk, loaihoadon, nguoimua, innguoimua, mauhoadon) " +
						   " values( "+ this.khId +",'" + this.ngayxuatHD + "', '1','" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "', RTRIM(LTRIM('" + this.kyhieuhoadon + "'))," +
						   "       RTRIM(LTRIM('" + this.sohoadon + "')) , N'"+ this.hinhthuctt +"' , '"+ this.tienck  +"', '"+ this.tienSauCK +"', '"+ tienAvat  +"'," +
						   "       '"+ this.tienVAT +"', N'"+ this.ghichu +"', '"+ this.tienKM +"', "+ this.nppId +", '1', N'"+ this.nguoimua +"', "+ this.innguoimua +", '"+ mauhoadon +"')";
			 
			System.out.println("1.Insert HOADON: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới HOADON " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "select scope_identity() as pxkId";
			ResultSet	rs = db.get(query);
			rs.next();
			this.id = rs.getString("pxkId");
			rs.close();
			

			int j=0;
			if(this.DonDatHang!=null)
			{
				while(j<this.DonDatHang.length)
				 {
					query = "Insert HOADON_DDH(hoadon_fk, ddh_fk) " +
					"select '"+this.id+"',  " + this.DonDatHang[j] + "  ";
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới HOADON_DDH " + query;
						db.getConnection().rollback();
						return false;
					}
					j=j+1;
				}
			}
			
			for(int i = 0; i < scheme_sanpham.size(); i++)
			{
				ISanpham splist = this.scheme_sanpham.get(i);
				if(splist.getId().trim().length() > 0 && Float.parseFloat(splist.getSoluong().replaceAll(",", "")) > 0 )
				{
					query = "insert HOADON_CTKM_TRAKM(HoaDonId ,hoadon_fk, sanpham_fk, sanphamma, donvi, soluong, ctkm ,dongia, vat) " +
							"select '"+this.id+"','"+this.id+"', '" + splist.getId() + "','"+ splist.getMasanpham() +"' , N'"+ splist.getDonvitinh() +"', '" + splist.getSoluong().replaceAll(",", "") + "', '" + splist.getCTKM() + "' " +
							" , '"+ splist.getDongia().replaceAll(",", "") +"', '"+ splist.getVat().replaceAll(",", "") +"' " ;
					
					System.out.println("1.1.Insert HOADON_CTKM_TRAKM: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi HOADON_CTKM_TRAKM: " + query;
						db.getConnection().rollback();
						return false;
					}
			
				}
			}
			
		// CAP NHAT LAI VAT, BVAT, AVAT CHO HOA DON		
			 query = 						
				"select a.HOADON_FK as HDID,  round(SUM(a.SOLUONG*a.DONGIA) ,0) as BVAT ,  SUM( round((a.SOLUONG*a.DONGIA*a.VAT/100),0) ) as VAT, \n"+
				"     round(SUM(a.SOLUONG*a.DONGIA) ,0) + SUM( round((a.SOLUONG*a.DONGIA*a.VAT/100),0) ) as AVAT \n"+
				"from HOADON_CTKM_TRAKM  a inner join HOADON b on a.hoadonId=b.PK_SEQ  \n"+
				"where b.LOAIHOADON=1 and b.TRANGTHAI in (1,2,4) AND b.PK_SEQ='"+this.id+"'   )  \n"+
				"group by a.hoadonId  ";
			System.out.println("Câu query "+ query);
			rs = db.get(query);
			
			if(rs!= null)
			{
				while(rs.next())
				{
					String sql = " update HOADON set TONGTIENBVAT= "+ rs.getDouble("BVAT")  +" , " +
							     "        TONGTIENAVAT= "+ rs.getDouble("AVAT")  +", VAT = "+ rs.getDouble("VAT")  +" " +
							     " where pk_seq = "+ rs.getString("HDID")  +" ";
					System.out.println("Câu cập nhât: "+ sql);
					if(!db.update(sql))
					{
						msg = "Không thể cập nhật HOADON : " + rs.getString("HDID") + " ";
						db.getConnection().rollback();	
						return false;
					}
				}
				rs.close();
			}
		//END
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			this.msg = "Exception: " + e.getMessage();
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	
	public boolean update() 
	{
		this.getNppInfo();
	
		//NEU HOA DON DA HUY THI CHI DUOC SUA KY HIEU VA SO HOA DON
		String sqlCHECK = "select trangthai from HOADON where pk_seq = '" + this.id + "'";
		ResultSet rsCHECK = db.get(sqlCHECK);
		String trangthai = "";
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			if(rsCHECK.next())
			{
				trangthai = rsCHECK.getString("trangthai");
			}
			rsCHECK.close();
			
		} 
		catch (Exception e2) 
		{
			e2.printStackTrace();
		}
		
		if(this.ngayxuatHD.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày xuất hóa đơn";
			return false;
		}

		if(this.ngayghinhanCN.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày ghi nhận";
			return false;
		}
		if(trangthai.equals("1") ||trangthai.equals("2") || trangthai.equals("4") )
		{
			if(this.ddhId.length() <= 0)
			{
				this.msg = "Vui lòng chọn đơn đặt hàng";
				return false;
			}
			
			
			if(this.scheme_sanpham.size() <= 0)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm  xuất hoá đơn khuyến mãi";
				return false;
			}
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "";
	
			// Kiểm tra npp nếu là Đối tác thì không dùng Số hóa đơn của hệ thống
			query = " select loaiNPP, (  select PXK_FK from PHIEUXUATKHO_DONHANG a inner join PHIEUXUATKHO b on a.pxk_fk = b.pk_seq where b.trangthai = '1' and donhang_fk in ( " + this.ddhId + " ) ) as pxkID " +
					 ",  ISNULL( ( select DonHangKhac from DONHANG aa inner join HoaDon_DDH bb on bb.ddh_fk=aa.pk_Seq where bb.hoadon_fk='"+this.id+"' ), 0) as DonHangKhac " +
					" from NHAPHANPHOI " +
					" where pk_seq = '" + nppId + "' ";
			System.out.println("____Init___"+query);
			ResultSet rsNpp = db.get(query);
			String loainpp= "";
			String pxkID = "";
			/*if(rsNpp!= null)*/
			{
				while(rsNpp.next())
				{
					loainpp = rsNpp.getString("loaiNPP");
					pxkID = rsNpp.getString("pxkID");
					this.donhangkhac=rsNpp.getString("DonHangKhac");
				}
				rsNpp.close();
			}
		
				
			if(!loainpp.equals("4") && !loainpp.equals("5"))	// KHONG PHAI LA DOI TAC VA CHI NHANH CUA DOI TAC
			{
			
				// LAY KY HIEU HOA DON ,SOHDTU TRONG DLN
				String chuoi="";
				long sohoadontu=0;
				String sohoadonden="";
				int kbDLN=0;
			
				if(this.nppId.equals("100002")) // CN HÀ NỘI DÙNG MẪU 2(HO)
				{

					 query= " select  sohoadontu2, sohoadonden2 " +
							" from NHANVIEN" +
							" where pk_seq= '"+ this.userId +"' and " +
							"       isnull(kyhieu2, '') = '"+ this.kyhieuhoadon +"' ";
						
						ResultSet rsLayDL =  db.get(query);
						if(rsLayDL != null)
						{
							while(rsLayDL.next())
							{
								sohoadontu = rsLayDL.getLong("sohoadontu2");
								sohoadonden = rsLayDL.getString("sohoadonden2");
							}
							rsLayDL.close();
						}
					    
						if(sohoadontu == 0 || sohoadonden.trim().length() <= 0)
						{
							this.msg = " Ký hiệu "+ this.kyhieuhoadon +" không giống với ký hiệu khai báo trong dữ liệu nền/ Chưa khai báo số hóa đơn trong dữ liệu nền (Số hóa đơn) ";
							db.getConnection().rollback();
							return false;
						}
						
					// Check So hoa don sua da co dung chua
					    // OTC
						query= " select  count(pk_seq) as kiemtra " +
						       " from HOADON " +
						       " where nguoisua= '"+ this.userId +"' and RTRIM(LTRIM(sohoadon)) = '"+ this.sohoadon.trim() +"' and pk_seq != "+ this.id +" " +
						       "      and trangthai != 3   and mauhoadon = 2  and RTRIM(LTRIM(isnull(kyhieu, ''))) = RTRIM(LTRIM('"+ this.kyhieuhoadon +"')) ";
						System.out.println("Cau kiem tra so hoa don "+query);
						ResultSet KtraSHD =  db.get(query);
						int ktra= 0;
						
						if(KtraSHD != null)
						{
							while(KtraSHD.next())
							{
								ktra = KtraSHD.getInt("kiemtra");
							}
							KtraSHD.close();
						}
					
						// ETC
						query= " select  count(pk_seq) as kiemtra " +
						       " from ERP_HOADONNPP " +
						       " where nguoisua= '"+ this.userId +"' and RTRIM(LTRIM(sohoadon)) = '"+ this.sohoadon.trim() +"'" +
						       "       and trangthai != '3' and pk_seq != "+ this.id +" and mauhoadon = 2 and RTRIM(LTRIM(isnull(kyhieu, ''))) = RTRIM(LTRIM('"+ this.kyhieuhoadon +"')) " +
						       "   ";
						System.out.println("Cau kiem tra so hoa don "+query);
						ResultSet KtraSHD_ETC =  db.get(query);
						int ktra_ETC= 0;
						
						if(KtraSHD_ETC != null)
						{
							while(KtraSHD_ETC.next())
							{
								ktra_ETC = KtraSHD_ETC.getInt("kiemtra");
							}
							KtraSHD_ETC.close();
						}
					
						if(Integer.parseInt(sohoadonden.trim()) <  Integer.parseInt(this.sohoadon.trim()))
						{
							this.msg = "Số hóa đơn này đã vượt quá số hóa đơn đến trong dữ liệu nền. Vui lòng thiết lập lại số hóa đơn ";
							db.getConnection().rollback();
							return false;
						}
						else if (ktra > 0 || ktra_ETC > 0 )
						{
							this.msg = "Số hóa đơn này đã được sử dụng. Vui lòng đánh số hóa đơn khác. ";
							db.getConnection().rollback();
							return false;
						}
						else if (this.sohoadon.trim().length() != 7 )
						{
							this.msg = "Số hóa đơn phải đủ 7 ký tự. Vui lòng kiểm tra lại.";
							db.getConnection().rollback();
							return false;
						}
				   
				}
				else  // CÒN LẠI DÙNG MẪU 1(CN)
				{
				 query= " select  sohoadontu, sohoadonden " +
						" from NHANVIEN" +
						" where pk_seq= '"+ this.userId +"' and " +
						"       isnull(kyhieu, '') = '"+ this.kyhieuhoadon +"' ";
					System.out.println("jdhjfdv"+query);
					ResultSet rsLayDL =  db.get(query);
					if(rsLayDL != null)
					{
						while(rsLayDL.next())
						{
							sohoadontu = rsLayDL.getLong("sohoadontu");
							sohoadonden = rsLayDL.getString("sohoadonden");
						}
						rsLayDL.close();
					}
				    
					if(sohoadontu == 0 || sohoadonden.trim().length() <= 0)
					{
						this.msg = " Ký hiệu "+ this.kyhieuhoadon +" không giống với ký hiệu khai báo trong dữ liệu nền/ Chưa khai báo số hóa đơn trong dữ liệu nền (Số hóa đơn) ";
						return false;
					}
					
				// Check So hoa don sua da co dung chua
				    // OTC
					query= " select  count(pk_seq) as kiemtra " +
					       " from HOADON " +
					       " where nguoisua= '"+ this.userId +"' and RTRIM(LTRIM(sohoadon)) = '"+ this.sohoadon.trim() +"' and pk_seq != "+ this.id +" " +
					       "      and trangthai != 3   and mauhoadon = 1 and RTRIM(LTRIM(isnull(kyhieu, ''))) = RTRIM(LTRIM('"+ this.kyhieuhoadon +"')) ";
					System.out.println("Cau kiem tra so hoa don "+query);
					ResultSet KtraSHD =  db.get(query);
					int ktra= 0;
					
					if(KtraSHD != null)
					{
						while(KtraSHD.next())
						{
							ktra = KtraSHD.getInt("kiemtra");
						}
						KtraSHD.close();
					}
				
					// ETC
					query= " select  count(pk_seq) as kiemtra " +
					       " from ERP_HOADONNPP " +
					       " where nguoisua= '"+ this.userId +"' and RTRIM(LTRIM(sohoadon)) = '"+ this.sohoadon.trim() +"'" +
					       "       and trangthai != '3' and pk_seq != "+ this.id +" and mauhoadon = 1 and RTRIM(LTRIM(isnull(kyhieu, ''))) = RTRIM(LTRIM('"+ this.kyhieuhoadon +"')) " +
					       "   ";
					System.out.println("Cau kiem tra so hoa don "+query);
					ResultSet KtraSHD_ETC =  db.get(query);
					int ktra_ETC= 0;
					
					if(KtraSHD_ETC != null)
					{
						while(KtraSHD_ETC.next())
						{
							ktra_ETC = KtraSHD_ETC.getInt("kiemtra");
						}
						KtraSHD_ETC.close();
					}
				
					if(Integer.parseInt(sohoadonden.trim()) <  Integer.parseInt(this.sohoadon.trim()))
					{
						this.msg = "Số hóa đơn này đã vượt quá số hóa đơn đến trong dữ liệu nền. Vui lòng thiết lập lại số hóa đơn ";
						db.getConnection().rollback();
						return false;
					}
					else if (ktra > 0 || ktra_ETC > 0 )
					{
						this.msg = "Số hóa đơn này đã được sử dụng. Vui lòng đánh số hóa đơn khác. ";
						db.getConnection().rollback();
						return false;
					}
					else if (this.sohoadon.trim().length() != 7 )
					{
						this.msg = "Số hóa đơn phải đủ 7 ký tự. Vui lòng kiểm tra lại.";
						db.getConnection().rollback();
						return false;
					}
			   }
			}
			double tongtienavat = this.tienSauCK + this.tienVAT;
			
			if(!trangthai.equals("1") )
			{
				query = "Update HOADON set sohoadon = RTRIM(LTRIM('" + this.sohoadon + "')), kyhieu = RTRIM(LTRIM('" + this.kyhieuhoadon + "')) where pk_seq = '" + this.id + "' ";
				if(!db.update(query))
				{
					this.msg = "Lỗi khi cập nhật hóa đơn: " + query;
					db.getConnection().rollback();
					return false;
				}
				Utility util = new Utility();
				msg= util.Check_Huy_NghiepVu_KhoaSo("HOADON", id, "ngayxuatHD", db);
				if(msg.length()>0)
				{
					db.getConnection().rollback();
					return false;
				}
			}
			else
			{
				 query = " update HOADON set khachhang_fk= "+ this.khId +" , ngayxuatHD = '" + this.ngayxuatHD + "' , ngaysua = '" + getDateTime() + "' , nguoisua ='" + this.userId + "'," +
						       " kyhieu = RTRIM(LTRIM('" + this.kyhieuhoadon + "')) , sohoadon= RTRIM(LTRIM('" + this.sohoadon + "')), hinhthuctt= N'"+ this.hinhthuctt +"' ," +
						       " chietkhau =  '"+ this.tienck  +"' , tongtienbvat= '"+ this.tienSauCK +"', tongtienavat='"+ tongtienavat +"', vat = '"+ this.tienVAT +"', ghichu= N'"+ this.ghichu +"'," +
						       " tongtienkm= '"+ this.tienKM +"', NPP_FK= "+ this.nppId +" , loaihoadon = '1' , nguoimua = N'"+ this.nguoimua +"', innguoimua = "+ this.innguoimua +"" +
						       " where pk_seq = '"+ this.id +"'" ;
				 
				System.out.println("1.Update HOADON: " + query);
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật HOADON " + query;
					db.getConnection().rollback();
					return false;
				}
				
				Utility util = new Utility();
				msg= util.Check_Huy_NghiepVu_KhoaSo("HOADON", id, "ngayxuatHD", db);
				if(msg.length()>0)
				{
					db.getConnection().rollback();
					return false;
				}

				
				
	String		pxk_KM=
						"		select b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK, b.SOLO, SUM(b.soluong) as soluong , b.NgayHetHan" +
						"		from PHIEUXUATKHO a inner join PHIEUXUATKHO_SPKM_CHITIET b on a.PK_SEQ = b.PXK_FK " +
						"		where a.PK_SEQ = '" + pxkID + "' " +
						"		group by b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK, b.SOLO,b.NgayHetHan " ;
	
	String	pxk_BAN=
					"	union all	select b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK, b.SOLO, SUM(b.soluong) as soluong,b.NgayHetHan " +
					"		from PHIEUXUATKHO a inner join PHIEUXUATKHO_SANPHAM_CHITIET b on a.PK_SEQ = b.PXK_FK " +
					"		where a.PK_SEQ = '" + pxkID + "' " +
					"		group by b.kho_fk, b.kbh_fk, a.NPP_FK, b.SANPHAM_FK, b.SOLO,b.NgayHetHan " ;
				
				
				//XÓA PXK CŨ
				query = "update kho set kho.SOLUONG = kho.SOLUONG + xuat.soluong, " +
						"			   kho.AVAILABLE = kho.AVAILABLE + xuat.soluong	 " +
						"from NHAPP_KHO_CHITIET kho inner join " +
						"( " +
						"	select kho_fk, kbh_fk, NPP_FK, SANPHAM_FK, SOLO, SUM(soluong) as soluong,NgayHetHan " +
						"   from (	" +pxk_KM+"	" ;
						if(donhangkhac.equals("1"))
						{
							query+=pxk_BAN;
						}
				query+=
						"	)" +
						"	TX group by kho_fk, kbh_fk, NPP_FK, SANPHAM_FK, SOLO,NgayHetHan " +
						") " +
						"xuat on kho.SANPHAM_FK = xuat.SANPHAM_FK and kho.NPP_FK = xuat.NPP_FK and kho.KBH_FK = xuat.kbh_fk and kho.KHO_FK = xuat.kho_fk and kho.SOLO = xuat.SOLO  and kho.NgayHetHan = xuat.NgayHetHan  ";
				System.out.println("3.TANG KHO: " + query);
				if(!db.update(query))
				{
					this.msg = "Không thể hủy " + query;
					db.getConnection().rollback();
					return false;
				}

				if(donhangkhac.equals("1"))
				{
					query = "delete PHIEUXUATKHO_SANPHAM_CHITIET where PXK_FK = '" + pxkID + "'  ";
					if(!db.update(query))
					{
						this.msg = "Không thể hủy " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				query = "delete PHIEUXUATKHO_SPKM_CHITIET where PXK_FK = '" + pxkID + "'  ";
				if(!db.update(query))
				{
					this.msg = "Không thể hủy " + query;
					db.getConnection().rollback();
					return false;
				}
				query = " delete from HOADON_CTKM_TRAKM where hoadonId = '"+ this.id +"' " ;
				if(!db.update(query))
				{
					this.msg = "Không thể xóa HOADON_SP " + query;
					db.getConnection().rollback();
					return false;
	      }
				
				query = " delete from HOADON_CTKM_TRAKM_CHITIET where hoadonId = '"+ this.id +"' " ;
				if(!db.update(query))
				{
					this.msg = "Không thể xóa HOADON_SP " + query;
					db.getConnection().rollback();
					return false;
	      }
				
				query = " delete from HOADON_DDH where hoadon_fk = '"+ this.id +"' " ;
				if(!db.update(query))
				{
					this.msg = "Không thể xóa HOADON_DDH " + query;
					db.getConnection().rollback();
					return false;
	       }
				
				
				query = "Insert HOADON_DDH(hoadon_fk, ddh_fk) select " + this.id + " , pk_seq from DONHANG where pk_seq in ( " + this.ddhId + " ) ";
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới HOADON_DDH " + query;
					db.getConnection().rollback();
					return false;
				}
					
									
				query = "Update PHIEUXUATKHO set NGAYLAPPHIEU = '" + this.ngayxuatHD + "' " +
				"where PK_SEQ in ( select pxk_fk from PHIEUXUATKHO_DONHANG where donhang_fk in (  select ddh_fk from HOADON_DDH where hoadon_fk = '" + this.id + "' ) )  ";
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới HOADON " + query;
					db.getConnection().rollback();
					return false;
				}
				
		
				for(int i = 0; i < scheme_sanpham.size(); i++)
				{
					ISanpham splist = this.scheme_sanpham.get(i);
					if(splist.getId().length() > 0 && Float.parseFloat(splist.getSoluong().replaceAll(",", "")) > 0 )
					{
						query = "insert HOADON_CTKM_TRAKM( HoaDonId,hoadon_fk, sanpham_fk, sanphamma, donvi, soluong, ctkm, dongia ,vat) values " +
								" ( '"+this.id+"','"+ this.id +"', '" + splist.getId() + "','"+ splist.getMasanpham() +"' , N'"+ splist.getDonvitinh() +"', '" + splist.getSoluong().replaceAll(",", "") + "', N'" + splist.getCTKM() + "', " +
								" '"+ splist.getDongia().replaceAll(",", "") +"', '"+ splist.getVat().replaceAll(",", "") +"' )" ;
						System.out.println("1.1.Insert HOADON_CTKM_TRAKM: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi HOADON_CTKM_TRAKM: " + query;
							db.getConnection().rollback();
							return false;
						}
					}
				}
				
				
				//CHEN LAI PXK TRONG TRUONG HOP DOI SOLO
				for(int i = 0; i < spId.length; i++)
				{
					if(spId[i].trim().length() > 0 && Double.parseDouble(spSoluong[i].trim().replaceAll(",", "")) > 0 )
					{
						if(this.sanpham_soluong.size() > 0)
						{
							Enumeration<String> keys = this.sanpham_soluong.keys();
							double totalCT = 0;
							
							while(keys.hasMoreElements())
							{
								String key = keys.nextElement();
								System.out.println("----KEY: " + key + "  -- ID: " + spId[i]);
								
								if(key.startsWith( spId[i]) )
								{
									String[] _sp = key.split("__");
									
									String _soluongCT = "0"; 
									if(this.sanpham_soluong.get(key) != null)
									{
										_soluongCT = this.sanpham_soluong.get(key).replaceAll(",", "");
									}
									
									totalCT += Double.parseDouble(_soluongCT);
									
									//CHECK TỒN KHO CỦA LÔ MUỐN ĐỔI CÓ ĐỦ KHÔNG
									query = "select AVAILABLE from NHAPP_KHO_CHITIET " +
											"where NPP_FK = '" + this.nppId + "' and kho_fk = ( select kho_fk from DONHANG where pk_seq in (" + this.ddhId + ") ) and kbh_fk = '100025' and sanpham_fk = '" + _sp[0] + "' and SOLO = '" + _sp[1] + "' and ngayhethan = '" + _sp[2] + "' ";

									if(spSCheme[i].trim().length()>0)
										query = 
										"	select AVAILABLE from NHAPP_KHO_CHITIET " +
										" where NPP_FK = '" + this.nppId + "' and kho_fk = ( select kho_fk from CTKHUYENMAI a   where a.SCHEME='"+spSCheme[i]+"' ) and kbh_fk = '100025' and sanpham_fk = '" + _sp[0] + "' and SOLO = '" + _sp[1] + "' and ngayhethan = '" + _sp[2] + "' ";
									
									System.out.println("1.1.Check TONKHO: " + query);
									ResultSet rsCHECK_TK = db.get(query);
									double avai = 0;
									if(rsCHECK_TK.next())
									{
										avai = rsCHECK_TK.getDouble("AVAILABLE");
									}
									rsCHECK_TK.close();
									
									if(avai < Double.parseDouble(_soluongCT) )
									{
										db.getConnection().rollback();
										this.msg = "Sản phẩm ( " + spTen[i] + " ) với số lô ( " + _sp[1] + " ), ngày hết hạn ( " + _sp[2] + " ) còn tối đa ( " + avai + " ) ";
										return false;
									}
								
									if(spSCheme[i].trim().length()<=0)
									query = 
											"insert PHIEUXUATKHO_SANPHAM_CHITIET(PXK_FK, SANPHAM_FK, SOLUONG, SOLO, NGAYHETHAN, KBH_FK, KHO_FK)" +
											"select '" + pxkID + "', '" + _sp[0] + "', '" + _soluongCT + "', '" + _sp[1] + "', '" + _sp[2] + "', '100025', ( select kho_fk from DONHANG where pk_seq in (" + this.ddhId + ") ) as kho_fk " ;
									else
									query ="insert into PHIEUXUATKHO_SPKM_CHITIET(PXK_FK,SANPHAM_FK,SOLUONG,scheme,SOLO,NGAYHETHAN,KBH_FK,KHO_FK)" +
										"select '" + pxkID + "', '" + _sp[0] + "', '" + _soluongCT + "', a.pk_seq as Scheme ,'" + _sp[1] + "', '" + _sp[2] + "', '100025',a.kho_fk as kho_fk " +
									  "	from CTKHUYENMAI a  "+ 
									  " where a.SCHEME='"+spSCheme[i]+"' ";
									
									System.out.println("1.2.Insert PHIEUXUATKHO_SANPHAM_CHITIET: " + query);
									if(db.updateReturnInt(query)!=1)
									{
										this.msg = "Khong the tao moi PHIEUXUATKHO_SANPHAM_CHITIET: " + query;
										db.getConnection().rollback();
										return false;
									}
									query = "Update NHAPP_KHO_CHITIET set soluong = soluong - '" + _soluongCT + "', AVAILABLE = AVAILABLE - '" + _soluongCT + "' " +
											"where NPP_FK = '" + this.nppId + "' and kho_fk = ( select kho_fk from DONHANG where pk_seq in (" + this.ddhId + ") ) and kbh_fk = '100025' and sanpham_fk = '" + _sp[0] + "' and SOLO = '" + _sp[1] + "' and ngayhethan = '" + _sp[2] + "' ";
									System.out.println("1.2.Insert NHAPP_KHO_CHITIET: " + query);
									if(db.updateReturnInt(query)!=1)
									{
										this.msg = "Khong the tao moi NHAPP_KHO_CHITIET: " + query;
										db.getConnection().rollback();
										return false;
									}
								}
							}
							//NEU TONG SO LUONG CT MA KHONG BANG TONG LUONG XUAT THI KO CHO LUU
							if(totalCT != Double.parseDouble(spSoluong[i].replaceAll(",", "").trim()) )
							{
								this.msg = "Tổng xuất theo lô của sản phẩm ( " + spTen[i] + " ) ( " + totalCT + " ) phải bằng tổng số lượng xuất ( " + spSoluong[i] + " ) ";
								db.getConnection().rollback();
								return false;
							}
						}
					}
				}
				
				query = 
						"insert HOADON_CTKM_TRAKM_CHITIET(hoadon_fk, hoadonId, sanpham_fk, soluong, scheme, solo, ngayhethan, kbh_fk, kho_fk, dongia )  " +
						 "select case b.kho_fk when 100001 then NULL else '" + this.id + "' end as hoadon_fk, '" + this.id + "', a.sanpham_fk, a.soluong, a.scheme, a.solo, a.ngayhethan, a.kbh_fk, a.kho_fk, " +
						 "		ISNULL ( ( select giabanleNPP from BGBANLENPP_SANPHAM where sanpham_fk = a.sanpham_fk and BGBANLENPP_FK in ( select pk_seq from BANGGIABANLENPP where npp_fk = '" + nppId + "'  ) ), 0) as dongia " +
						 "from PHIEUXUATKHO_SPKM_CHITIET a inner join ctkhuyenmai b on a.SCHEME = b.pk_seq " +
						 "where PXK_FK = ( select pxk_fk from PHIEUXUATKHO_DONHANG where donhang_fk = '" + ddhId + "' and pxk_fk in ( select pk_seq from PHIEUXUATKHO where NPP_FK = '" + nppId + "' and trangthai != '2' ) )  " ;
						
						System.out.println("1.2.Insert HOADON_CTKM_TRAKM_CHITIET: " + query);
						if(!db.update(query))
						{
							this.msg = "Lỗi khi cập nhật "+query;
							db.getConnection().rollback();
							return false;
						}
			
						if(donhangkhac.equals("1"))
						{
							//TRUOGN HOP SAU NAY MUON IN SOLO
							query = 
							"insert HOADON_CTKM_TRAKM_CHITIET(HoaDonId,hoadon_fk, sanpham_fk, soluong, scheme, solo, ngayhethan, kbh_fk, kho_fk, dongia )  " +
							"select '" + this.id + "','" + this.id + "' hoadon_fk, sanpham_fk, soluong, NULL as scheme, solo, ngayhethan, kbh_fk, kho_fk, " +
							"		ISNULL ( ( select giabanleNPP from BGBANLENPP_SANPHAM where sanpham_fk = a.sanpham_fk and BGBANLENPP_FK in ( select pk_seq from BANGGIABANLENPP where npp_fk = '" + nppId + "'  ) ), 0) as dongia " +
							"from PHIEUXUATKHO_SANPHAM_CHITIET a " +
							"where PXK_FK = ( select pxk_fk from PHIEUXUATKHO_DONHANG where donhang_fk = '" + ddhId + "' and pxk_fk in ( select pk_seq from PHIEUXUATKHO where NPP_FK = '" + nppId + "' and trangthai != '2' ) ) ";
							System.out.println("1.2.Insert HOADON_CTKM_TRAKM_CHITIET: " + query);
							if(!db.update(query))
							{
								this.msg = "Lỗi khi cập nhật "+query;
								db.getConnection().rollback();
								return false;
							}				
						}
						
	   }
			
			query =   " update hd set hd.TONGTIENBVAT = gt.BVAT, hd.TONGTIENAVAT = gt.BVAT + gt.VAT, VAT = gt.VAT   " + 
				      "from HOADON hd inner join " + 
				      "( " + 
				      " select a.HOADON_FK, SUM( ROUND(a.SOLUONG * a.DONGIA, 0 ) ) as BVAT, " + 
				      "   SUM(  ROUND ( ( ROUND ( a.SOLUONG * a.DONGIA , 0 ) * a.VAT / 100.0 ), 0 )  )  as VAT " + 
				      " from HOADON_CTKM_TRAKM a  " + 
				      " where a.hoadon_fk = '" + this.id + "'  " + 
				      " group by a.HOADON_FK " + 
				      ") " + 
				      "gt on hd.pk_seq = gt.hoadon_fk " + 
				      "where hd.pk_seq = '" + this.id + "'" ;
			if(db.updateReturnInt(query) <= 0 )
			{
				msg = "Không thể cập nhật HOADON " + query;
				db.getConnection().rollback();	
				return false;
			}	
			
			// CAP NHAT LAI VAT, BVAT, AVAT CHO HOA DON		
			 /*query = 						
				"select a.hoadonId as HDID,  round(SUM(a.SOLUONG*a.DONGIA) ,0) as BVAT ,  SUM( round((a.SOLUONG*a.DONGIA*a.VAT/100),0) ) as VAT, \n"+
				"     round(SUM(a.SOLUONG*a.DONGIA) ,0) + SUM( round((a.SOLUONG*a.DONGIA*a.VAT/100),0) ) as AVAT \n"+
				"from HOADON_CTKM_TRAKM  a inner join HOADON b on a.hoadonId=b.PK_SEQ  \n"+
				"where b.LOAIHOADON=1 and b.TRANGTHAI in (1,2,4) AND b.PK_SEQ= "+ this.id +"  \n"+
				"group by a.hoadonId  ";
			System.out.println("Câu query "+ query);
			ResultSet rs = db.get(query);
			
			{
				try
				{
					while(rs.next())
					{
						String sql = " update HOADON set TONGTIENBVAT= "+ rs.getDouble("BVAT")  +" , " +
								     		 "        TONGTIENAVAT= "+ rs.getDouble("AVAT")  +", VAT = "+ rs.getDouble("VAT")  +" " +
								     		 " where pk_seq = "+ rs.getString("HDID")  +" ";
						System.out.println("Câu cập nhât: "+ sql);
						if(!db.update(sql))
						{
							msg = "Không thể cập nhật HOADON : " + rs.getString("HDID") + " ";
							db.getConnection().rollback();	
							return false;
						}
					}
					rs.close();
					
				}catch(Exception e)
				{
					try 
					{
						db.getConnection().rollback();
					} 
					catch (Exception e1) {}
					e.printStackTrace();
				}				
			}*/
			//END
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		
		return true;
	}
	
	

	
	public String[] getSpLoai() {

		return this.spLoai;
	}


	public void setSpLoai(String[] spLoai) {

		this.spLoai = spLoai;
	}

	
	public String[] getSpScheme() {
		
		return this.spSCheme;
	}

	
	public void setSpScheme(String[] spScheme) {
		
		this.spSCheme = spScheme;
	}

	public boolean chot() 
	{
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "update HOADON set trangthai = '2'  where pk_seq = '" + this.id + "' and TrangThai=1 ";
			if(db.updateReturnInt(query)!=1)
			{
				msg = "Hóa đơn đã cập nhật trạng thái hoặc phát sinh lỗi" + query;
				db.getConnection().rollback();
				return false;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			db.update("rollback");
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		return true;
	}

	public String getNgayghinhanCN() 
	{
		return this.ngayghinhanCN;
	}

	public void setNgayghinhanCN(String ngayghinhanCN) 
	{
		this.ngayghinhanCN = ngayghinhanCN;
	}

	public String getKyhieuhoadon() 
	{
		return this.kyhieuhoadon;
	}

	public void setKyhieuhoadon(String kyhieuhoadon) 
	{
		this.kyhieuhoadon = kyhieuhoadon;
		
	}

	public String getSohoadon() 
	{		
		return this.sohoadon;
	}

	public void setSohoadon(String sohoadon) 
	{
		this.sohoadon = sohoadon;
	}

	public String[] getDonDatHang() 
	{
		return this.DonDatHang;
	}

	public void setDonDatHang(String[] dondathang) 
	{
		this.DonDatHang = dondathang;
		
	}

	public Double getTienCK() 
	{
		return this.tienck;
	}

	public void setTienCK(Double tienCK) 
	{
		this.tienck=tienCK;
	}

	public Double getTienSauCK() 
	{
		return this.tienSauCK;
	}

	public void setTienSauCK(Double tienSauCK) 
	{
		this.tienSauCK= tienSauCK;
	}

	public Double getTienVAT() 
	{
		return this.tienVAT;
	}

	public void setTienVAT(Double tienVAT) 
	{
		this.tienVAT = tienVAT;
	}

	public String[] getSpDongia() 
	{
		return this.spDongia;
	}

	public void setSpDongia(String[] spDongia) 
	{
		this.spDongia = spDongia;
		
	}

	public String[] getSpChietkhau()
	{
		return this.spChietkhau;
	}

	public void setSpChietkhau(String[] spChietkhau) 
	{
		this.spChietkhau = spChietkhau;
		
	}

   public String[] getDhck_diengiai() {
		
		return this.dhCk_diengiai;
	}

	
	public void setDhck_Diengiai(String[] obj) {
		
		this.dhCk_diengiai = obj;
	}

	
	public String[] getDhck_giatri() {
		
		return this.dhCk_giatri;
	}

	
	public void setDhck_giatri(String[] obj) {
		
		this.dhCk_giatri = obj;
	}

	
	public String[] getDhck_loai() {
		
		return this.dhCk_loai;
	}

	
	public void setDhck_loai(String[] obj) {
		
		this.dhCk_loai = obj;
	}

	public String getLoaidonhang() {
		
		return this.loaidonhang;
	}

	
	public void setLoaidonhang(String loaidonhang) {
		
		this.loaidonhang = loaidonhang;
	}

	@Override
	public String getNguoimua() 
	{
		return this.nguoimua;
	}

	@Override
	public void setNguoimua(String nguoimua) {
		this.nguoimua = nguoimua;
		
	}

	@Override
	public Double getTienKM() {
		return this.tienKM;
	}

	@Override
	public void setTienKM(Double tienKM) {
		this.tienKM = tienKM;
		
	}

	@Override
	public Double getTienSauKM() {
		return this.tienSauCK;
	}

	@Override
	public void setTienSauKM(Double tienSauKM) {
		this.tienSauKM= tienSauKM;
		
	}

	@Override
	public String getKhId() {
		return this.khId;
	}

	@Override
	public void setKhId(String khId) {
		this.khId =khId;
		
	}

	@Override
	public ResultSet getKhRs() {
		return this.khRs;
	}

	@Override
	public void setKhRs(ResultSet khRs) {
		this.khRs = khRs;
		
	}

	@Override
	public String getChietkhau() {

		return this.chietkhau;
	}

	@Override
	public void setChietkhau(String chietkhau) {
		this.chietkhau= chietkhau;
		
	}
   public String[] getTichLuy_Scheme() {
		
		return this.tichluy_scheme;
	}

	
	public void setTichLuy_Scheme(String[] tichluy_scheme) {
		
		this.tichluy_scheme = tichluy_scheme;
	}

	
	public String[] getTichLuy_Tongtien() {
		
		return this.tichluy_tongtien;
	}

	
	public void setTichLuy_Tongtien(String[] tichluy_tongtien) {
		
		this.tichluy_tongtien = tichluy_tongtien;
	}


	public String[] getTichLuy_VAT() {
		return this.tichluy_vat;
	}


	public void setTichLuy_TVAT(String[] tichluy_vat) {
		this.tichluy_vat = tichluy_vat;
		
	}	
	
	public void initTichLuy(String ddh) 
	{


		
		String query = "select N'CN5' as diengiai, sum(chietkhau) as chietkhau, thueVAT, 1 as STT, 0 as tichluyQUY from DONHANG_SANPHAM  " +
		"	where donhang_fk in (" + ddh+ ") and thueVAT = '5' group by thueVAT " +
		"union  " +
		"	select N'CN10' as diengiai, sum(chietkhau) as chietkhau, thueVAT, 2 as STT, 0 as tichluyQUY  " +
		"	from DONHANG_SANPHAM  " +
		"	where donhang_fk in (" + ddh+ ") and thueVAT = '10' group by thueVAT " +
		"union " +
		"	select a.diengiai, a.thanhtoan / ( 1 + ptTHUE / 100 ) as chietkhau, a.ptTHUE as thueVAT, 3 as STT, tichluyQUY  " +
		"	from DUYETTRAKHUYENMAI_DONHANG a inner join TIEUCHITHUONGTL b on a.duyetkm_fk = b.PK_SEQ   " +
		"	where a.donhang_fk in (" + ddh+ ")  "+
		"union " +
		"	select a.maloai as diengiai, a.chietkhau, a.ptVAT as thueVAT, 4 as STT, 0 as tichluyQUY  " +
		"	from DONHANG_CHIETKHAUBOSUNG a    " +
		"	where a.donhang_fk in (" + ddh+ ")  order by STT, tichluyQUY  ";
		
		System.out.println("---INIT TICH LUY: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			String schemeMa = "";
			String schemeVAT = "";
			String schemeTt = "";
			
			try 
			{
				NumberFormat format = new DecimalFormat("#,###,###");
				while(rs.next())
				{
					schemeMa += rs.getString("diengiai") + "__";
					schemeVAT += rs.getString("thueVAT") + "__";
					schemeTt += format.format( rs.getDouble("chietkhau")) + "__";
				}
				rs.close();
				
				if(schemeMa.trim().length() > 0)
				{
					schemeMa = schemeMa.substring(0, schemeMa.length() - 2);
					this.tichluy_scheme = schemeMa.split("__");
					
					schemeVAT = schemeVAT.substring(0, schemeVAT.length() - 2);
					this.tichluy_vat = schemeVAT.split("__");
					
					schemeTt = schemeTt.substring(0, schemeTt.length() - 2);
					this.tichluy_tongtien = schemeTt.split("__");
				}
			} 
			catch (Exception e) 
			{
				System.out.println("__EXCEPTION: " + e.getMessage());
			}
		}	
	}

	@Override
	public String[] getSpVat() {

		return this.spVat;
	}

	@Override
	public void setSpVat(String[] spVat) {
		this.spVat= spVat;
		
	}

	@Override
	public String[] getSpThue() {
	
		return this.spThue;
	}

	@Override
	public void setSpThue(String[] spThue) {
		this.spThue = spThue;
		
	}
	//tra km
	public Hashtable<String, Float> getScheme_Tongtien() 
	{
		return this.scheme_tongtien;
	}

	public void setScheme_Tongtien(Hashtable<String, Float> scheme_tongtien) 
	{
		this.scheme_tongtien = scheme_tongtien;
	}

	public Hashtable<String, Float> getScheme_Chietkhau() 
	{
		return this.scheme_chietkhau;
	}

	public void setScheme_Chietkhau(Hashtable<String, Float> scheme_chietkhau) 
	{
		this.scheme_chietkhau = scheme_chietkhau;
	}
	
	public List<ISanpham> getScheme_SpList() 
	{
		return this.scheme_sanpham;
	}

	public void setScheme_SpList(List<ISanpham> splist) 
	{
		this.scheme_sanpham = splist;
	}
	
	public void getTrakhuyenmai(String ddh)
	{
		this.getNppInfo();
		List<ISanpham> scheme_sp = new ArrayList<ISanpham>();
		
		// CHECK DDON HANG CHON CO PHAI LA DON HANG KHAC
		String query = "select isnull(donhangkhac,0) as donhangkhac from DONHANG where pk_seq in ( "+ ddh +")";
		ResultSet ktraDH= db.get(query);
		int isDHK= 0;
		if(ktraDH!= null)
		{
		  try
		  {
		   while(ktraDH.next())
		   {
			   isDHK= ktraDH.getInt("donhangkhac") ;
		   }
		   ktraDH.close();
		  }catch (Exception e) {
			e.printStackTrace();
		}
		}
		
       if(isDHK == 0) // DONHANG BINH THUONG
       {
			// HÓA ĐƠN KHUYẾN MÃI CHỈ LẤY NHỮNG DON HÀNG ÁP KHUYẾN MÃI TRẢ SP (LOAI =3 )
			 query = "select a.ctkmId, a.trakmId, a.soxuat, a.soluong,donvi, a.spMa, a.tonggiatri, b.scheme, c.loai, c.hinhthuc, c.chietkhau,  c.tongluong, c.tongtien, d.ten, d.pk_seq as spId, a.khoNVBH, " +
					       " ( select THUEXUAT from NGANHHANG where pk_seq = d.nganhhang_fk ) as VAT,  ";
			query = query + " ISNULL ( ( select giabanleNPP from BGBANLENPP_SANPHAM where sanpham_fk = (select pk_seq from SANPHAM where ma = a.spma) and BGBANLENPP_FK in ( select pk_seq from BANGGIABANLENPP where npp_fk = '" + this.nppId + "'  ) ), 0) as dongia ";
			query = query + "from donhang_ctkm_trakm a inner join ctkhuyenmai b on a.ctkmid = b.pk_seq inner join trakhuyenmai c on a.trakmid = c.pk_seq left join sanpham d on a.spMa = d.ma ";
			query = query +" left join donvidoluong dv on dv.pk_seq=d.dvdl_fk ";
			query = query +	"where a.donhangId in (" + ddh + ") and c.loai= '3' ";
			
			ResultSet rs = db.get(query);
			if( rs != null)
			{
				try 
				{
					while(rs.next())
					{
						String schemeName = rs.getString("scheme");				
						String trakmId = rs.getString("trakmId");
						String soxuat = rs.getString("soxuat");	
						String soluong = rs.getString("soluong");
						String loai = rs.getString("loai");
						String hinhthuc = rs.getString("hinhthuc");
						String tongiatri = rs.getString("tonggiatri");
						String donvi=rs.getString("donvi");
						float tongtien = 0;
						float chietkhau = 0;
						String spMa = "";
						this.ttCKKM = 0;
						
						if (loai == null){
							if(rs.getString("spMa") == null)
							{
								if(rs.getString("tongtien") != null)
									tongtien = Float.parseFloat(rs.getString("tongtien"));
								this.scheme_tongtien.put(schemeName, tongtien * Float.parseFloat(soxuat));
								this.aplaikm = true; //co ctkm
								this.cokm = true;
							}
						}else
						{
								if(loai.equals("3")) //tra sp
								{				
									String[] param = new String[8];
									ISanpham sp = null;	
					
									//while(sanphamRs.next())
									//{
										param[0] = rs.getString("spId");
										param[1] = rs.getString("spMa");
										param[2] = rs.getString("ten");
										param[3] = soluong;
										param[4] = donvi;
										param[5] = rs.getString("dongia");
										param[6] = schemeName;
										param[7] = "0";
						
										sp = new Sanpham(param);
										sp.setKhoNVBH(rs.getString("khoNVBH"));
										sp.setVat(rs.getString("VAT"));
										scheme_sp.add(sp);
										this.aplaikm = true;
										this.cokm = true;
									//}														
									//sanphamRs.close();
								}
							
						}
					}
					rs.close();
				} 
				catch(Exception e) {}
			}
       }
       else // HOADON KHAC
       {
    	   query =  " select b.PK_SEQ as SPID, b.MA as spMa , b.TEN, DV.donvi, a.giamua as dongia ,' ' as scheme ,a.thuevat as VAT," +
   					"       sum( a.soluong) as soluong, '0'  as chietkhau, 0 as khoNVBH " +
   					" from Donhang_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ    " +
   					" 	  INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.DVDL_FK       " +
   					" where a.Donhang_FK in ( " + ddh + " ) " +
   					" group by  b.PK_SEQ , b.MA, b.TEN, DV.donvi, a.giamua  ,a.thuevat "; 
    	   
    	   ResultSet rs = db.get(query);
			if( rs != null)
			{
				try 
				{
					while(rs.next())
					{
						String schemeName = rs.getString("scheme");					
						String soluong = rs.getString("soluong");
						String donvi=rs.getString("donvi");


											
						String[] param = new String[8];
						ISanpham sp = null;	
		

							param[0] = rs.getString("spId");
							param[1] = rs.getString("spMa");
							param[2] = rs.getString("ten");
							param[3] = soluong;
							param[4] = donvi;
							param[5] = rs.getString("dongia");
							
							param[6] = schemeName;
							param[7] = "0";
			
							sp = new Sanpham(param);
							sp.setKhoNVBH(rs.getString("khoNVBH"));
							sp.setVat(rs.getString("VAT"));
							scheme_sp.add(sp);
							this.aplaikm = true;
							this.cokm = true;															
						
					}
					rs.close();
				} 
				catch(Exception e) {e.printStackTrace();}
			}
       }
		
		System.out.println("1.Khoi tao TKM:"+ query);
	   
		
		this.scheme_sanpham = scheme_sp;
		
	}
	
	public float getTongtienCKKM(){
		return this.ttCKKM;
	}
	public void setTongtienCKKM(float ttckkm){
		this.ttCKKM = ttckkm;
		
	}
	
	public boolean isAplaikhuyenmai() 
	{
		return this.aplaikm;
	}

	public void setAplaikhuyenmai(boolean aplaikm) 
	{
		this.aplaikm = aplaikm;
	}
	
	public boolean isCokhuyenmai() 
	{
		return this.cokm;
	}

	public void setCokhuyenmai(boolean cokm) 
	{
		this.cokm = cokm;
	}
	
	public float getTongtiensauVAT()
	{
		return tongtiensauVAT;
	}
	public void setTongtiensauVAT(float tongtiensauVAT) 
	{
		this.tongtiensauVAT = tongtiensauVAT;
	}

	@Override
	public void loadContents() 
	{
		this.getNppInfo();
		Utility util = new Utility();
		String query = "select PK_SEQ, TEN from KHACHHANG where TRANGTHAI = '1' and NPP_FK= "+ this.nppId +" and KBH_FK=100025 ";
		this.khRs = db.get(query);
		

		if(this.khId.trim().length() > 0 )
		{			
		  query = 
			" select PK_SEQ , NgayNhap as NgayDonHang  \n" +
			" from DONHANG \n" +
			" where  NPP_FK= "+ this.nppId +" AND  KHACHHANG_FK = '" + this.khId + "' and kho_fk in  " + util.quyen_kho(this.userId) + 
			"       and pk_seq in (select donhang_fk \n " +
			"                      from PHIEUXUATKHO_DONHANG a inner join PHIEUXUATKHO b on a.pxk_fk= b.pk_seq \n" +
			"                      where b.trangthai=1 and b.NPP_FK= "+ this.nppId +" )  \n" +
			"       and pk_seq in (select donhangid \n" +
			"                      from Donhang_ctkm_trakm  a inner join trakhuyenmai b on a.trakmid=b.pk_seq \n" +
			"                      where b.loai = '3' ) \n" +
			" and pk_seq not in (Select a.ddh_fk from HoaDon_ddh a inner join hoadon b on a.hoadon_fk=b.pk_seq where b.trangthai = 2 and loaihoadon= '1') \n" ;
			System.out.println("Câu query1 "+query);		
			this.ddhRs = db.get(query);
		}
		
		String chuoi="";
		int i=0;
		if(this.DonDatHang!=null)
		{
			while(i<this.DonDatHang.length)
			 {
				if(i==0){
					chuoi=DonDatHang[i];
				}
				else{
					chuoi=chuoi +"," +DonDatHang[i];
				}
				i=i+1;
			}
		}
		System.out.println("Chuoi "+chuoi);
		if(chuoi.length() > 0)
		{						
			
			 this.initTichLuy(chuoi);
			 this.getTrakhuyenmai(chuoi);
		}
		
	}

	public String getInNguoimua() 
	{
		return this.innguoimua;
	}

	public void setInNguoimua(String innguoimua) 
	{
		this.innguoimua = innguoimua;
	}
	
	
	public ResultSet getSoloTheoSp(String spMa, String donvi, String tongluong)
	{
		
		// Kiểm tra npp nếu là Đối tác thì không dùng Số hóa đơn của hệ thống
		String		query = " select loaiNPP, (  select PXK_FK from PHIEUXUATKHO_DONHANG a inner join PHIEUXUATKHO b on a.pxk_fk = b.pk_seq where b.trangthai = '1' and donhang_fk in ( " + this.ddhId + " ) ) as pxkID " +
				 ",  ISNULL( ( select DonHangKhac from DONHANG aa inner join HoaDon_DDH bb on bb.ddh_fk=aa.pk_Seq where bb.hoadon_fk='"+this.id+"' ), 0) as DonHangKhac " +
				" from NHAPHANPHOI " +
				" where pk_seq = '" + nppId + "' ";
		System.out.println("____Init___"+query);
		ResultSet rs = db.get(query);
		String loainpp= "";
		String pxkID = "";
		/*if(rsNpp!= null)*/
		{
			try
      {
	      while(rs.next())
	      {
	      	loainpp = rs.getString("loaiNPP");
	      	this.pxkId = rs.getString("pxkID");
	      	this.donhangkhac=rs.getString("DonHangKhac");
	      }
	      rs.close();
      } catch (SQLException e)
      {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
      }
			
		}
		
		tongluong = tongluong.replaceAll(",", "");
		//System.out.println("---TONG LUONG: " + tongluong );
		
		String kbh_fk = "100025";
		
		
	String	pxk_KM=
			 "   			select NPP_FK,KBH_FK,KHO_FK,SANPHAM_FK,SOLO,NGAYHETHAN,SOLUONG   \n "+      
			 "   			from PHIEUXUATKHO a inner join  PHIEUXUATKHO_SPKM_CHITIET b on b.PXK_FK=a.PK_SEQ   \n "+      
			 "   			where PXK_FK='"+pxkId+"'   \n ";   
				
	String	pxk_BAN=
			"   			union all   \n "+      
			"   			select NPP_FK,KBH_FK,KHO_FK,SANPHAM_FK,SOLO,NGAYHETHAN,SOLUONG   \n "+      
			"   			from PHIEUXUATKHO a inner join  PHIEUXUATKHO_SANPHAM_CHITIET b on b.PXK_FK=a.PK_SEQ   \n "+      
			"   			where PXK_FK='"+pxkId+"'   \n ";
		
		
		
		query = 
					 "   select AVAILABLE+   \n "+      
					 "   	isnull   \n "+      
					 "   	( (    \n "+      
					 "   		select sum(SoLuong)    \n "+      
					 "   		From 		   \n "+      
					 "   		(  "+pxk_KM+"  \n ";
						if(donhangkhac.equals("1"))
						{
							query+=pxk_BAN;
						}
					   query+=
					   "   		) as pxk   \n "+      
					   "   		where pxk.NPP_FK=a.NPP_FK and pxk.KBH_FK=a.KBH_FK and pxk.KHO_FK=a.KHO_FK and a.SANPHAM_FK=pxk.SANPHAM_FK   \n "+      
					   "   			and pxk.SOLO=a.SOLO and pxk.NGAYHETHAN=a.NGAYHETHAN   \n "+      
					   "   		group by pxk.NPP_FK,pxk.KBH_FK,pxk.KHO_FK,pxk.SANPHAM_FK,pxk.SOLO,pxk.NGAYHETHAN    \n "+      
					   "   	),0)  as AVAILABLE ,a.SOLO,a.NGAYHETHAN   \n "+      
					   "   from NHAPP_KHO_CHITIET a   \n "+      
					   "   where NPP_FK = '"+this.nppId+"' and KBH_FK = '"+kbh_fk+"'     \n "+      
					   "   and AVAILABLE +   \n "+      
					   "   isnull(   \n "+      
					   "   (   \n "+      
					   "   	select sum(SoLuong)   \n "+      
					   "   	From    \n "+      
					   "   	( "+pxk_KM+"  \n ";
						if(donhangkhac.equals("1"))
						{
							query+=pxk_BAN;
						}
					   query+=  
					   "   	) as pxk   \n "+      
					   "   	where pxk.NPP_FK=a.NPP_FK and pxk.KBH_FK=a.KBH_FK and pxk.KHO_FK=a.KHO_FK and a.SANPHAM_FK=pxk.SANPHAM_FK   \n "+      
					   "   		and pxk.SOLO=a.SOLO and pxk.NGAYHETHAN=a.NGAYHETHAN   \n "+      
					   "   	group by pxk.NPP_FK,pxk.KBH_FK,pxk.KHO_FK,pxk.SANPHAM_FK,pxk.SOLO,pxk.NGAYHETHAN    \n "+      
					   "   ),0)>0 and SANPHAM_FK='"+spMa+"' and exists "+
						 "	( "+
						 "			select KHO_FK  "+
						 "			From 		 "+   
						 "			(   "+pxk_KM+"  \n ";
						if(donhangkhac.equals("1"))
						{
							query+=pxk_BAN;
						}
						 query+=
						 "		) as pxk    "+
						 "	where pxk.NPP_FK=a.NPP_FK and pxk.KBH_FK=a.KBH_FK and pxk.KHO_FK=a.KHO_FK and a.SANPHAM_FK=pxk.SANPHAM_FK "+   
						 "		group by pxk.NPP_FK,pxk.KBH_FK,pxk.KHO_FK,pxk.SANPHAM_FK,pxk.SOLO,pxk.NGAYHETHAN  "+  
						 "	)  ";

		
		System.out.println("----LAY SO LO: " + query+"--"+this.id );
		String query2 = "";
		rs = db.get(query);
		try 
		{
			double total = 0;
			
			while(rs.next())
			{
				double slg = 0;
				double avai = Math.round(rs.getDouble("AVAILABLE") * 100.0 ) / 100.0;
				
				System.out.println("===================== AVAI: " + avai);
				total += avai;
				
				if(total < Double.parseDouble(tongluong))
				{
					slg = avai;
				}
				else
				{
					slg =  Double.parseDouble(tongluong) - ( total - avai );
				}
					
				if(slg >= 0)
				{
					query2 += "select '" + avai + "' as AVAILABLE, '" + rs.getString("NGAYHETHAN") + "' as NGAYHETHAN, '" + rs.getString("SOLO") + "' as SOLO, '" + slg + "' as tuDEXUAT union ALL ";
				}
				else
				{
					query2 += "select '" + avai + "' as AVAILABLE, '" + rs.getString("NGAYHETHAN") + "' as NGAYHETHAN, '" + rs.getString("SOLO") + "' as SOLO, '' as tuDEXUAT union ALL ";
				}
				
			}
			rs.close();
		} 
		catch (Exception e) 
		{
			System.out.println("EXCEPTION INIT SOLO: " + e.getMessage());
			e.printStackTrace();
		}
		
		if(query2.trim().length() > 0)
		{
			query2 = query2.substring(0, query2.length() - 10);
			//System.out.println("---TU DONG DE XUAT BIN - LO: " + query2 );
			return db.get(query2);
		}
		
		return null;
	}
	
	String donhangkhac;
	
	public String getDonHangKhac()
	{
		return donhangkhac;
	}
	public void setDonHangKhac(String dh)
	{
		this.donhangkhac=dh;
	}
	
	
	String pxkId;

	public String getPxkId()
  {
  	return pxkId;
  }

	public void setPxkId(String pxkId)
  {
  	this.pxkId = pxkId;
  }
}
