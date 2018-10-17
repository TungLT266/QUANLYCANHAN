package geso.traphaco.erp.beans.huythutien.imp;

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
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.huythutien.IErpHuythutienList;

public class ErpHuythutienList extends Phan_Trang implements IErpHuythutienList 
{
	private static final long serialVersionUID = 1L;
	String congtyId;
	String userId;
	String tungay;
	String denngay;
	String trangthai;
	String msg;
	String nguoitao;
	String sotien;
	String soCT;
	String nppdangnhap;
	
	ResultSet hctmhRs;
	
	List<IThongTinHienThi> hienthiList;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	dbutils db;
	Utility util;
	
	public ErpHuythutienList()
	{
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.msg = "";
		this.nguoitao="";
		this.soCT="";
		this.sotien ="";
		
		this.hienthiList = new ArrayList<IThongTinHienThi>();
		
		currentPages = 1;
		num = 1;
		this.db = new dbutils();
		this.util = new Utility();
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
	
	private String LayDuLieu(String id) {
				
		String query =	"SELECT ISNULL(NHOMKHTT_FK,0) NHOMKHTT_ID, NGAYGHISO FROM ERP_THUTIEN WHERE PK_SEQ ='"+id+"' \n ";
			
		System.out.println(query);
		String laykt = "";
			
		ResultSet NHOMKHTT= db.get(query);
		int nhomkhttId = 0;
		String ngayghiso = "";
		
		try{
			if(NHOMKHTT!=null)
			{
				while(NHOMKHTT.next())
				{
					nhomkhttId = Integer.parseInt(NHOMKHTT.getString("NHOMKHTT_ID"));
					ngayghiso = NHOMKHTT.getString("NGAYGHISO");
				}
				NHOMKHTT.close();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
			if(nhomkhttId > 0)  // THU TIỀN THEO NHÓM KH
			  {
				int sodong = 0;  
			    query= " SELECT Count(BANGA.KHACHHANG_FK) as dem" +
					   " FROM ( " +
					   "  Select hd.KHACHHANG_FK ,SUM(tthd.SOTIENTT)ST_Thanhtoan "+
	                   "  From ERP_THUTIEN tt inner join ERP_THUTIEN_HOADON tthd on tt.PK_SEQ=tthd.THUTIEN_FK "+
	                   "					  inner join ERP_HOADON hd on tthd.HOADON_FK=hd.PK_SEQ "+
	                   "  Where tt.PK_SEQ='" + id + "' "+
	                   "  Group by hd.KHACHHANG_FK "+
	                   " ) as BANGA";
					ResultSet rsD= db.get(query);
					try{
						if(rsD!=null)
						{
							while(rsD.next())
							{
								sodong= Integer.parseInt(rsD.getString("dem"));
							}
							rsD.close();
						}
					}
					catch(Exception e){
						e.printStackTrace();
					}
					
					query = 
						"SELECT tt.ngayghiso, tt.tiente_fk, TTHOADON.sotienTT, tt.tiente_fk, isnull(tt.chietkhau,0) as chietkhau, \n " +
						" 		isnull(tt.phinganhang, 0) as phinganhang, isnull(tt.chenhlech, 0) as chenhlech, \n " +
						" 		TTHOADON.khachhang_fk, tt.httt_fk, tt.nganhang_fk, tt.chinhanh_fk, kh.ma as maKH, tt.noidungtt_fk , \n" +
						" 		( select SOHIEUTAIKHOAN from ERP_TAIKHOANKT where pk_seq in ( select TaiKhoan_fk from ERP_NGANHANG_CONGTY where Sotaikhoan=tt.sotaikhoan )  ) as taikhoanNO_SoHieu, \n" +
						" 		( select SOHIEUTAIKHOAN from ERP_TAIKHOANKT where pk_seq  = kh.taikhoan_fk ) as taikhoanCO_KH_SoHieu  \n " +
						"FROM	 erp_thutien tt inner join NHOMKHACHHANGTT_KHACHHANGTT nkh on tt.nhomkhtt_fk = nkh.nkhtt_fk \n " +
						"                    inner join ERP_KHACHHANG kh on nkh.khachhang_fk = kh.pk_seq \n " +
						"                    inner join ( \n "+
						"                                 select hd.KHACHHANG_FK, SUM(tthd.SOTIENTT) as SOTIENTT \n "+
						"                                 from ERP_THUTIEN_HOADON tthd inner join ERP_HOADON hd tthd.HOADON_FK = hd.PK_SEQ \n "+
						"                                 where tthd.THUTIEN_FK = tt.PK_SEQ  \n "+
						"                                 group by hd.KHACHHANG_FK) TTHOADON on TTHOADON.KHACHHANG_FK = kh.PK_SEQ \n "+
						"where tt.pk_seq = '" + id + "' \n";
						System.out.println("Câu lấy TK : "+query); 
						
						int m=0;
						String[] khachhang_fk = new String[sodong];
						String[] hinhthuctt = new String[sodong];	
						double[] tonggiatri = new double[sodong];
						String[] tiente_fk = new String[sodong];
						String[] taikhoanCO_SoHieu = new String[sodong];
						String[] taikhoanNO_SoHieu = new String[sodong];
						double[] chietkhau_KH = new double[sodong];
						double[] phinganhang_KH = new double[sodong];
						double[] chenhlech_KH = new double[sodong];
						
						double chietkhau = 0;
						double phinganhang = 0;
						double chenhlech = 0;

						
						double chietkhauTT  = 0;
						double phiNHTT  = 0;
						double chenhlechTT  = 0;
						
						ResultSet RsRs = db.get(query);
						
						try{
						if(RsRs!= null)
						{
							while(RsRs.next())
							{
								String nam = ngayghiso.substring(0, 4);
								String thang = ngayghiso.substring(5, 7);
								
								khachhang_fk[m] = RsRs.getString("khachhang_fk");
								hinhthuctt[m] = RsRs.getString("httt_fk");
								tiente_fk[m] = RsRs.getString("tiente_fk");
								
								tonggiatri[m] = RsRs.getDouble("sotienTT");
								
								chietkhau = RsRs.getDouble("chietkhau");
								phinganhang = RsRs.getDouble("phinganhang");
								chenhlech =   RsRs.getDouble("chenhlech");
								
								chietkhau_KH[m] = 0;
								phinganhang_KH[m] = 0;
								chenhlech_KH[m] = 0;
								
								if(m == 0)
								{
									chietkhauTT = chietkhau;
									phiNHTT = phinganhang;
									chenhlechTT = chenhlech;
								}
								
								taikhoanCO_SoHieu[m] = RsRs.getString("taikhoanCO_KH_SoHieu") == null ? "":RsRs.getString("taikhoanCO_KH_SoHieu") ;
								
								if(hinhthuctt[m].equals("100001")) //thanh toan NGANHANG (CHUYEN KHOAN)
								{
									taikhoanNO_SoHieu[m] = RsRs.getString("taikhoanNO_SoHieu")== null ? "":RsRs.getString("taikhoanNO_SoHieu") ;
								}
								else
								{
									taikhoanNO_SoHieu[m] = "11110000";
								}
								
							// TIỀN HÓA ĐƠN
								if(tonggiatri[m] > 0)
								{	
									if(laykt.trim().length()>0) laykt += " UNION ALL \n";
									laykt +=
									"	SELECT N'NỢ' NO_CO,  TT.PK_SEQ, '"+taikhoanNO_SoHieu+"' SOHIEUTAIKHOAN, \n"+
									"       "+(-1)*tonggiatri[m]+" SOTIEN, (CASE TT.HTTT_FK WHEN 100001 THEN (select MA+' - '+TEN from ERP_NGANHANG where PK_SEQ = tt.NGANHANG_FK  ) " +
									" 											  ELSE '' END ) DOITUONG	, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+m+" STT, 1 SAPXEP \n"+
									"   FROM ERP_THUTIEN TT LEFT JOIN ERP_KHACHHANG KH ON TT.KHACHHANG_FK = KH.PK_SEQ  \n"+
									"   WHERE TT.PK_SEQ = '"+id+"' \n"+
								
									"   UNION ALL \n"+
								
									"   SELECT N'CÓ' NO_CO, TT.PK_SEQ, '"+taikhoanCO_SoHieu+"' SOHIEUTAIKHOAN, \n"+
									"       "+(-1)*tonggiatri[m]+" SOTIEN, KH.TEN DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, "+m+" STT, 2 SAPXEP \n"+
									" 	FROM ERP_THUTIEN TT LEFT JOIN ERP_KHACHHANG KH ON TT.KHACHHANG_FK = KH.PK_SEQ \n"+
									"   WHERE TT.PK_SEQ = '"+id+"' \n";
									 
								}
							    m ++;			
							}
							RsRs.close();
						}
						}
						catch(Exception e){
							e.printStackTrace();
						}
			  }
			else 
			{	
				query =  "	SELECT tt.ngayghiso, tt.tiente_fk, tt.sotienTT, isnull(tt.thuduoc, 0) as thuduoc, isnull(chietkhau,0) as chietkhau, \n " +
				" 		   isnull(tt.phinganhang, 0) as phinganhang, isnull(tt.chenhlech, 0) as chenhlech, tt.dinhkhoanco, tt.noidungtt_fk , \n " +
				" 		   tt.khachhang_fk, tt.httt_fk, tt.nganhang_fk, tt.chinhanh_fk, kh.ma as maKH, tt.noidungtt_fk , \n " +
				" 		   (case when tt.NCC_FK is not null then (select SOHIEUTAIKHOAN from ERP_TAIKHOANKT where PK_SEQ = ncc.TAIKHOAN_FK ) \n" +
				"       		 else (select SOHIEUTAIKHOAN from ERP_TAIKHOANKT where PK_SEQ = nv.TAIKHOAN_FK ) end ) as taikhoanCO_NCC_NV_SoHieu , \n" +
				" 			(case when tt.NCC_FK is not null then N'Nhà cung cấp' else N'Nhân viên' end )as DOITUONG_TU, \n" +
				" 			(case when tt.NCC_FK is not null then tt.NCC_FK else tt.NHANVIEN_FK  end)as NCC_NV_FK , \n" +
				" 			( select SOHIEUTAIKHOAN from ERP_TAIKHOANKT where pk_seq in ( select TaiKhoan_fk from ERP_NGANHANG_CONGTY where Sotaikhoan=tt.sotaikhoan )  ) as taikhoanNO_SoHieu, \n " +
				" 			( select SOHIEUTAIKHOAN from ERP_TAIKHOANKT where pk_seq  = kh.taikhoan_fk ) as taikhoanCO_KH_SoHieu, (isnull(kh.ma,'') + ' - ' +kh.ten) as TENKH  \n " +
				"	FROM erp_thutien tt \n " +
				"		 left join ERP_KHACHHANG kh on TT.KHACHHANG_FK = KH.PK_SEQ  \n " +
				"		 left join ERP_NHACUNGCAP ncc on tt.NCC_FK = ncc.PK_SEQ \n" +
				"		 left join ERP_NHANVIEN nv on tt.NHANVIEN_FK = nv.PK_SEQ \n " +
				"	WHERE tt.pk_seq = '" + id + "'";
						
				String dtno="";
				String dtco="";
				String tenkh="";
				
		ResultSet psktRs = db.get(query);
		
		if(psktRs != null)
		{
		  try{
			while(psktRs.next())
			{
				String khachhang_fk = psktRs.getString("khachhang_fk");
				tenkh = psktRs.getString("TENKH");
				String hinhthuctt = psktRs.getString("httt_fk");
				double tonggiatri = Math.round(psktRs.getDouble("thuduoc"));
				
				String nam = psktRs.getString("ngayghiso").substring(0, 4);
				String thang = psktRs.getString("ngayghiso").substring(5, 7);
				String tiente_fk = psktRs.getString("tiente_fk");
				String noidungthutien = psktRs.getString("noidungtt_fk");
				
				String taikhoanCO_SoHieu = psktRs.getString("taikhoanCO_KH_SoHieu") == null ? "" : psktRs.getString("taikhoanCO_KH_SoHieu") ;
				String taikhoanNO_SoHieu = "";
			
				if(hinhthuctt.equals("100001"))
				{
					taikhoanNO_SoHieu = psktRs.getString("taikhoanNO_SoHieu") == null ? "": psktRs.getString("taikhoanNO_SoHieu") ;
				}
				else
				{
					taikhoanNO_SoHieu = "11110000";
				}
			
				if( noidungthutien.equals("100000")|| noidungthutien.equals("100001")) // THU TIEN HÓA ĐƠN && KHACH HANG TRA TRUOC
				{
					//TIEN THU DUOC
					laykt =
					"	SELECT N'NỢ' NO_CO,  TT.PK_SEQ, '"+taikhoanNO_SoHieu+"' SOHIEUTAIKHOAN, \n"+
					"       "+(-1)*tonggiatri+" SOTIEN, (CASE TT.HTTT_FK WHEN 100001 THEN (select MA+' - '+TEN from ERP_NGANHANG where PK_SEQ = tt.NGANHANG_FK  ) " +
					" 											  ELSE '' END ) DOITUONG	, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP \n"+
					"   FROM ERP_THUTIEN TT LEFT JOIN ERP_KHACHHANG KH ON TT.KHACHHANG_FK = KH.PK_SEQ  \n"+
					"   WHERE TT.PK_SEQ = '"+id+"' \n"+
				
					"   UNION ALL \n"+
				
					"   SELECT N'CÓ' NO_CO, TT.PK_SEQ, '"+taikhoanCO_SoHieu+"' SOHIEUTAIKHOAN, \n"+
					"       "+(-1)*tonggiatri+" SOTIEN, KH.TEN DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 2 SAPXEP \n"+
					" 	FROM ERP_THUTIEN TT LEFT JOIN ERP_KHACHHANG KH ON TT.KHACHHANG_FK = KH.PK_SEQ \n"+
					"   WHERE TT.PK_SEQ = '"+id+"' \n";
					 
					
					double phinganhang = Math.round(psktRs.getDouble("phinganhang"));
					
					if(phinganhang > 0){
						taikhoanNO_SoHieu = "64250000";
						
						if(laykt.trim().length()>0) laykt += " UNION ALL \n";
							
						laykt +=
						"	SELECT N'NỢ' NO_CO,  TT.PK_SEQ, '"+taikhoanNO_SoHieu+"' SOHIEUTAIKHOAN, \n"+
						"       "+(-1)*phinganhang+" SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 2 STT, 1 SAPXEP \n"+
						"   FROM ERP_THUTIEN TT LEFT JOIN ERP_KHACHHANG KH ON TT.KHACHHANG_FK = KH.PK_SEQ \n"+
						"   WHERE TT.PK_SEQ = '"+id+"' \n"+
				
						"   UNION ALL \n"+
				
						"   SELECT N'CÓ' NO_CO, TT.PK_SEQ, '"+taikhoanCO_SoHieu+"' SOHIEUTAIKHOAN, \n"+
						"       "+(-1)*phinganhang+" SOTIEN, KH.TEN DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 2 STT, 2 SAPXEP \n"+
						" 	FROM ERP_THUTIEN TT LEFT JOIN ERP_KHACHHANG KH ON TT.KHACHHANG_FK = KH.PK_SEQ \n"+
						"   WHERE TT.PK_SEQ = '"+id+"' \n";
						}
					
					//GHI NHAN SO TIEN CHENH LECH 
					double chenhlech = Math.round(psktRs.getDouble("chenhlech"));
					
					if(chenhlech != 0){
						if(chenhlech > 0)
						{
							if(tiente_fk.equals("100000")) // VND
							{	
								taikhoanNO_SoHieu = "81180000";
								taikhoanCO_SoHieu = psktRs.getString("taikhoanCO_KH_SoHieu") == null ? "" : psktRs.getString("taikhoanCO_KH_SoHieu") ;
							}else{  // NGOAI TỆ
								 
								taikhoanNO_SoHieu = "63580000 ";
								taikhoanCO_SoHieu = psktRs.getString("taikhoanCO_KH_SoHieu") == null ? "" : psktRs.getString("taikhoanCO_KH_SoHieu") ;							
							}
							
							chenhlech = Math.abs(chenhlech);
							
							if(laykt.trim().length()>0) laykt += " UNION ALL \n";
							
							laykt +=
							"	SELECT N'NỢ' NO_CO,  TT.PK_SEQ, '"+taikhoanNO_SoHieu+"' SOHIEUTAIKHOAN, \n"+
							"       "+(-1)*chenhlech+" SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 3 STT, 1 SAPXEP \n"+
							"   FROM ERP_THUTIEN TT LEFT JOIN ERP_KHACHHANG KH ON TT.KHACHHANG_FK = KH.PK_SEQ \n"+
							"   WHERE TT.PK_SEQ = '"+id+"' \n"+
				
							"   UNION ALL \n"+
				
							"   SELECT N'CÓ' NO_CO, TT.PK_SEQ, '"+taikhoanCO_SoHieu+"' SOHIEUTAIKHOAN, \n"+
							"       "+(-1)*chenhlech+" SOTIEN, (KH.MA +' - '+ KH.TEN) DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 3 STT, 2 SAPXEP \n"+
							" 	FROM ERP_THUTIEN TT LEFT JOIN ERP_KHACHHANG KH ON TT.KHACHHANG_FK = KH.PK_SEQ \n"+
							"   WHERE TT.PK_SEQ = '"+id+"' \n";
							
							
						}										
						else
						{
							chenhlech= chenhlech*(-1);
							if(tiente_fk.equals("100000")) // VND
							{
								taikhoanNO_SoHieu = psktRs.getString("taikhoanCO_KH_SoHieu") == null ? "" : psktRs.getString("taikhoanCO_KH_SoHieu") ;
								taikhoanCO_SoHieu = "71180000"; 
							}else {   // NGOẠI TỆ
								
								taikhoanNO_SoHieu = psktRs.getString("taikhoanCO_KH_SoHieu") == null ? "" : psktRs.getString("taikhoanCO_KH_SoHieu") ;
								taikhoanCO_SoHieu = "51580000"; 
							}
							
							chenhlech = Math.abs(chenhlech);
							
							if(laykt.trim().length()>0) laykt += " UNION ALL \n";
							
							laykt +=
							"	SELECT N'NỢ' NO_CO,  TT.PK_SEQ, '"+taikhoanNO_SoHieu+"' SOHIEUTAIKHOAN, \n"+
							"       "+(-1)*chenhlech+" SOTIEN, (KH.MA +' - '+ KH.TEN) DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 4 STT, 1 SAPXEP \n"+
							"   FROM ERP_THUTIEN TT LEFT JOIN ERP_KHACHHANG KH ON TT.KHACHHANG_FK = KH.PK_SEQ \n"+
							"   WHERE TT.PK_SEQ = '"+id+"' \n"+
				
							"   UNION ALL \n"+
				
							"   SELECT N'CÓ' NO_CO, TT.PK_SEQ, '"+taikhoanCO_SoHieu+"' SOHIEUTAIKHOAN, \n"+
							"       "+(-1)*chenhlech+" SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 4 STT, 2 SAPXEP \n"+
							" 	FROM ERP_THUTIEN TT LEFT JOIN ERP_KHACHHANG KH ON TT.KHACHHANG_FK = KH.PK_SEQ \n"+
							"   WHERE TT.PK_SEQ = '"+id+"' \n";
							
						}
						
					}	
									
					//GHI NHAN TIEN CHIET KHAU (Dung cho Thu tien ban hang)
					double chietkhau = Math.round(psktRs.getDouble("chietkhau"));
					
					if(chietkhau > 0)
					{
						taikhoanNO_SoHieu = "64183000";
						taikhoanCO_SoHieu = psktRs.getString("taikhoanCO_KH_SoHieu") == null ? "" : psktRs.getString("taikhoanCO_KH_SoHieu") ;
						
						if(laykt.trim().length()>0) laykt += " UNION ALL \n";
						
						laykt +=
						"	SELECT N'NỢ' NO_CO,  TT.PK_SEQ, '"+taikhoanNO_SoHieu+"' SOHIEUTAIKHOAN, \n"+
						"       "+(-1)*chietkhau+" SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 5 STT, 1 SAPXEP \n"+
						"   FROM ERP_THUTIEN TT LEFT JOIN ERP_KHACHHANG KH ON TT.KHACHHANG_FK = KH.PK_SEQ \n"+
						"   WHERE TT.PK_SEQ = '"+id+"' \n"+
				
						"   UNION ALL \n"+
				
						"   SELECT N'CÓ' NO_CO, TT.PK_SEQ, '"+taikhoanCO_SoHieu+"' SOHIEUTAIKHOAN, \n"+
						"       "+(-1)*chietkhau+" SOTIEN, (KH.MA +' - '+ KH.TEN) DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 5 STT, 2 SAPXEP \n"+
						" 	FROM ERP_THUTIEN TT LEFT JOIN ERP_KHACHHANG KH ON TT.KHACHHANG_FK = KH.PK_SEQ \n"+
						"   WHERE TT.PK_SEQ = '"+id+"' \n";
						
					}
				
			}
			else if (noidungthutien.equals("100002"))// THU KHÁC
			{
				if(tonggiatri >0)
				{
					taikhoanCO_SoHieu= psktRs.getString("dinhkhoanco") == null ? "" : psktRs.getString("dinhkhoanco");
					
					if(laykt.trim().length()>0) laykt += " UNION ALL \n";
					
					laykt +=
					"	SELECT N'NỢ' NO_CO,  TT.PK_SEQ,  '"+taikhoanNO_SoHieu+"' SOHIEUTAIKHOAN, \n"+
					"       "+(-1)*tonggiatri+" SOTIEN, (CASE TT.HTTT_FK WHEN 100001 THEN (select MA+' - '+TEN from ERP_NGANHANG where PK_SEQ = tt.NGANHANG_FK  ) ELSE '' END) DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 6 STT, 1 SAPXEP \n"+
					"   FROM ERP_THUTIEN TT \n"+
					"   WHERE TT.PK_SEQ = '"+id+"' \n"+
			
					"   UNION ALL \n"+
			
					"   SELECT N'CÓ' NO_CO, TT.PK_SEQ, '"+taikhoanCO_SoHieu+"' SOHIEUTAIKHOAN, \n"+
					"       "+(-1)*tonggiatri+" SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 6 STT, 2 SAPXEP \n"+
					" 	FROM ERP_THUTIEN TT LEFT JOIN ERP_KHACHHANG KH ON TT.KHACHHANG_FK = KH.PK_SEQ \n"+
					"   WHERE TT.PK_SEQ = '"+id+"' \n";
				}
				
				//GHI NHAN SO TIEN PHI NGAN HANG
				double phinganhang = Math.round(psktRs.getDouble("phinganhang"));
				
				if(phinganhang > 0)
				{
					if(hinhthuctt.equals("100001")) //thanh toan NGANHANG (CHUYEN KHOAN)
					{
						taikhoanCO_SoHieu = psktRs.getString("taikhoanNO_SoHieu") == null ? "": psktRs.getString("taikhoanNO_SoHieu") ;
					}
					else
					{
						taikhoanCO_SoHieu = "11110000";
					}
						taikhoanNO_SoHieu = "64250000";
						
					if(laykt.trim().length()>0) laykt += " UNION ALL \n";
					
					laykt +=
					"	SELECT N'NỢ' NO_CO,  TT.PK_SEQ, '"+taikhoanNO_SoHieu+"' SOHIEUTAIKHOAN, \n"+
					"       "+(-1)*phinganhang+" SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 7 STT, 1 SAPXEP \n"+
					"   FROM ERP_THUTIEN TT LEFT JOIN ERP_KHACHHANG KH ON TT.KHACHHANG_FK = KH.PK_SEQ \n"+
					"   WHERE TT.PK_SEQ = '"+id+"' \n"+
			
					"   UNION ALL \n"+
			
					"   SELECT N'CÓ' NO_CO, TT.PK_SEQ, '"+taikhoanCO_SoHieu+"' SOHIEUTAIKHOAN, \n"+
					"       "+(-1)*phinganhang+" SOTIEN, (CASE TT.HTTT_FK WHEN 100001 THEN (select MA+' - '+TEN from ERP_NGANHANG where PK_SEQ=tt.NGANHANG_FK  ) " +
					" 											  ELSE '' END ) DOITUONG , '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 7 STT, 2 SAPXEP \n"+
					" 	FROM ERP_THUTIEN TT LEFT JOIN ERP_KHACHHANG KH ON TT.KHACHHANG_FK = KH.PK_SEQ \n"+
					"   WHERE TT.PK_SEQ = '"+id+"' \n";
				}
				
			}
			else if (noidungthutien.equals("100003")){
				
				if(hinhthuctt.equals("100001")) //thanh toan NGANHANG (CHUYEN KHOAN)
				{
					taikhoanNO_SoHieu = psktRs.getString("taikhoanNO_SoHieu") == null ? "" : psktRs.getString("taikhoanNO_SoHieu");
				}
				else
				{
					taikhoanNO_SoHieu = "11110000";
				}
				taikhoanCO_SoHieu =  psktRs.getString("taikhoanCO_NCC_NV_SoHieu") == null ? "" : psktRs.getString("taikhoanCO_NCC_NV_SoHieu");
				
				//GHI NHAN SO TIEN THU DUOC
				
				if(tonggiatri > 0)
				{
					if(laykt.trim().length()>0) laykt += " UNION ALL \n";
					
					laykt +=
					"	SELECT N'NỢ' NO_CO,  TT.PK_SEQ, '"+taikhoanNO_SoHieu+"' SOHIEUTAIKHOAN, \n"+
					"       "+(-1)*tonggiatri+" SOTIEN, (CASE TT.HTTT_FK WHEN 100001 THEN (select MA+' - '+TEN from ERP_NGANHANG where PK_SEQ=tt.NGANHANG_FK  ) " +
					" 											  ELSE '' END ) DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 8 STT, 1 SAPXEP \n"+
					"   FROM ERP_THUTIEN TT LEFT JOIN ERP_KHACHHANG KH ON TT.KHACHHANG_FK = KH.PK_SEQ \n"+
					"   WHERE TT.PK_SEQ = '"+id+"' \n"+
			
					"   UNION ALL \n"+
			
					"   SELECT N'CÓ' NO_CO, TT.PK_SEQ, '"+taikhoanCO_SoHieu+"' SOHIEUTAIKHOAN, \n"+
					"       "+(-1)*tonggiatri+" SOTIEN, (CASE WHEN TT.NCC_FK IS NOT NULL THEN (select MA+' - '+TEN from ERP_NHACUNGCAP where PK_SEQ=tt.NCC_FK  ) " +
					" 											  ELSE (select MA+' - '+TEN from ERP_NHANVIEN where PK_SEQ=tt.NHANVIEN_FK  ) END ) DOITUONG , '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 8 STT, 2 SAPXEP \n"+
					" 	FROM ERP_THUTIEN TT LEFT JOIN ERP_KHACHHANG KH ON TT.KHACHHANG_FK = KH.PK_SEQ \n"+
					"   WHERE TT.PK_SEQ = '"+id+"' \n";
				}
				
				//GHI NHAN SO TIEN PHI NGAN HANG
				double phinganhang = Math.round(psktRs.getDouble("phinganhang"));
				
				if(phinganhang > 0)
				{
					taikhoanNO_SoHieu = "64250000";
					
					if(laykt.trim().length()>0) laykt += " UNION ALL \n";
					
					laykt +=
					"	SELECT N'NỢ' NO_CO,  TT.PK_SEQ, '"+taikhoanNO_SoHieu+"' SOHIEUTAIKHOAN, \n"+
					"       "+(-1)*tonggiatri+" SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU,9 STT, 1 SAPXEP \n"+
					"   FROM ERP_THUTIEN TT LEFT JOIN ERP_KHACHHANG KH ON TT.KHACHHANG_FK = KH.PK_SEQ \n"+
					"   WHERE TT.PK_SEQ = '"+id+"' \n"+
			
					"   UNION ALL \n"+
			
					"   SELECT N'CÓ' NO_CO, TT.PK_SEQ, '"+taikhoanCO_SoHieu+"' SOHIEUTAIKHOAN, \n"+
					"       "+(-1)*tonggiatri+" SOTIEN, (CASE TT.HTTT_FK WHEN 100001 THEN (select MA+' - '+TEN from ERP_NGANHANG where PK_SEQ=tt.NGANHANG_FK  ) " +
					" 											  ELSE '' END ) DOITUONG , '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 9 STT, 2 SAPXEP \n"+
					" 	FROM ERP_THUTIEN TT LEFT JOIN ERP_KHACHHANG KH ON TT.KHACHHANG_FK = KH.PK_SEQ \n"+
					"   WHERE TT.PK_SEQ = '"+id+"' \n";
				}
				
				//GHI NHAN SO TIEN CHENH LECH
				double chenhlech = Math.round(psktRs.getDouble("chenhlech"));
				
				if(chenhlech != 0)
				{
					if(chenhlech > 0)
					{
						if(tiente_fk.equals("100000"))  // VND
						{
							taikhoanNO_SoHieu = "81180000";
							taikhoanCO_SoHieu = psktRs.getString("taikhoanCO_NCC_NV_SoHieu") == null ? "" : psktRs.getString("taikhoanCO_NCC_NV_SoHieu") ;
						}else {   // NGOẠI TỆ
						
							taikhoanNO_SoHieu = "63580000";
							taikhoanCO_SoHieu = psktRs.getString("taikhoanCO_NCC_NV_SoHieu") == null ? "" : psktRs.getString("taikhoanCO_NCC_NV_SoHieu") ;
						
						}
						
						chenhlech = Math.abs(chenhlech);
						
						if(laykt.trim().length()>0) laykt += " UNION ALL \n";
						
						laykt +=
						"	SELECT N'NỢ' NO_CO,  TT.PK_SEQ, '"+taikhoanNO_SoHieu+"' SOHIEUTAIKHOAN, \n"+
						"       "+(-1)*chenhlech+" SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 10 STT, 1 SAPXEP \n"+
						"   FROM ERP_THUTIEN TT LEFT JOIN ERP_KHACHHANG KH ON TT.KHACHHANG_FK = KH.PK_SEQ \n"+
						"   WHERE TT.PK_SEQ = '"+id+"' \n"+
			
						"   UNION ALL \n"+
			
						"   SELECT N'CÓ' NO_CO, TT.PK_SEQ, '"+taikhoanCO_SoHieu+"' SOHIEUTAIKHOAN, \n"+
						"       "+(-1)*chenhlech+" SOTIEN, (CASE WHEN TT.NCC_FK IS NOT NULL THEN (select MA+' - '+TEN from ERP_NHACUNGCAP where PK_SEQ=tt.NCC_FK  ) " +
						" 											  ELSE (select MA+' - '+TEN from ERP_NHANVIEN where PK_SEQ=tt.NHANVIEN_FK ) END ) DOITUONG , '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU,10 STT, 2 SAPXEP \n"+
						" 	FROM ERP_THUTIEN TT LEFT JOIN ERP_KHACHHANG KH ON TT.KHACHHANG_FK = KH.PK_SEQ \n"+
						"   WHERE TT.PK_SEQ = '"+id+"' \n";
						
					}
					else
					{
						chenhlech= chenhlech*(-1);						
						if(tiente_fk.equals("100000"))  // VND
						{
							taikhoanNO_SoHieu = psktRs.getString("taikhoanCO_NCC_NV_SoHieu") == null ? "" : psktRs.getString("taikhoanCO_NCC_NV_SoHieu") ;
							taikhoanCO_SoHieu = "71180000";
						}else {
							
							taikhoanNO_SoHieu = psktRs.getString("taikhoanCO_NCC_NV_SoHieu") == null ? "" : psktRs.getString("taikhoanCO_NCC_NV_SoHieu") ;
							taikhoanCO_SoHieu = "51580000";								
						}
						
						chenhlech = Math.abs(chenhlech);
						
						if(laykt.trim().length()>0) laykt += " UNION ALL \n";
						
						laykt +=
						"	SELECT N'NỢ' NO_CO,  TT.PK_SEQ, '"+taikhoanNO_SoHieu+"' SOHIEUTAIKHOAN, \n"+
						"       "+(-1)*chenhlech+" SOTIEN, (CASE WHEN TT.NCC_FK IS NOT NULL THEN (select MA+' - '+TEN from ERP_NHACUNGCAP where PK_SEQ=tt.NCC_FK  ) " +
						" 											  ELSE (select MA+' - '+TEN from ERP_NHANVIEN where PK_SEQ=tt.NHANVIEN_FK  ) END ) DOITUONG , '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 11 STT, 1 SAPXEP \n"+
						"   FROM ERP_THUTIEN TT LEFT JOIN ERP_KHACHHANG KH ON TT.KHACHHANG_FK = KH.PK_SEQ \n"+
						"   WHERE TT.PK_SEQ = '"+id+"' \n"+
			
						"   UNION ALL \n"+
			
						"   SELECT N'CÓ' NO_CO, TT.PK_SEQ, '"+taikhoanCO_SoHieu+"' SOHIEUTAIKHOAN, \n"+
						"       "+(-1)*chenhlech+" SOTIEN, '' DOITUONG , '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 11 STT, 2 SAPXEP \n"+
						" 	FROM ERP_THUTIEN TT LEFT JOIN ERP_KHACHHANG KH ON TT.KHACHHANG_FK = KH.PK_SEQ  \n"+
						"   WHERE TT.PK_SEQ = '"+id+"' \n";
						
						}
					
					}							
				}
			}
		 }
		  catch (SQLException e) {e.printStackTrace();
		}
		if(laykt.trim().length()>0) laykt += " ORDER BY PK_SEQ, STT, SAPXEP \n";
		
		if(laykt.trim().length()<=0){
		laykt += " SELECT '' NO_CO, '' PK_SEQ, '' SOHIEUTAIKHOAN, '' SOTIEN, '' DOITUONG, '' TRUNGTAMCHIPHI, '' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP " +
			 " FROM ERP_THUTIEN " +
			 " WHERE PK_SEQ ='"+id+"'";
		}
		
		System.out.println(laykt);
		}
	}
		return laykt;
}	

	public void init(String search)
	{
		this.getNppInfo();
		String query = "";
		
		//LOAICHUNGTU = 2: HỦY PHIẾU CHI
		//LOAICHUNGTU = 1: HỦY PHIẾU THU
		
//		if(search.length() > 0)
//			query = search;
//		else
		query = "select a.PK_SEQ as SOPHIEU, a.SOCHUNGTU, a.TRANGTHAI, a.NGAYTAO, a.NGAYSUA, b.TEN as NGUOITAO, c.TEN as NGUOISUA, a.SOCHUNGTUGOC \n " +
					"from ERP_HUYCHUNGTUKETOAN a inner join NHANVIEN b on a.nguoitao = b.pk_seq inner join NHANVIEN c on a.nguoisua = c.pk_seq \n " +
					"where a.congty_fk = '" + this.congtyId + "' and a.LOAICHUNGTU = 1  \n ";
			
		if(this.tungay.length() > 0)
			query += " and a.ngaytao >= '" + this.tungay + "'";
		
		if(this.denngay.length() > 0)
			query += " and a.ngaysua <= '" + this.denngay + "'";
		
		if(this.trangthai.length() > 0)
			query += " and a.trangthai = '" + this.trangthai + "'";
		
		if(this.nguoitao.length() > 0)
			query += " and b.TEN like N'%" + this.nguoitao + "%'";
		
		if(this.soCT.length() > 0)
			query += " and a.SOCHUNGTU like '%" + this.soCT + "%'";
		
		if(sotien.length()>0)
			query += " and (select sum(SOTIEN) FROM ERP_HUYCHUNGTUKETOAN_CHUNGTU WHERE HCTKT_FK = a.PK_SEQ ) = "+sotien+"";
		this.hctmhRs = db.get(query);
		
	}
	
	public void DBclose() 
	{
		
			try 
			{
				if(this.hctmhRs != null)
				this.hctmhRs.close();
			} catch (SQLException e) {}
		
			this.db.shutDown();
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

	
	public String getsotien() {
		
		return this.sotien;
	}

	
	public void setsotien(String sotien) {
		
		this.sotien = sotien;
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
