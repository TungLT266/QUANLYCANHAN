package geso.traphaco.erp.beans.danhgialaichiphitratruoc.imp;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;
import geso.dms.center.util.Utility;
import geso.dms.db.sql.dbutils;

import geso.traphaco.erp.beans.danhgialaichiphitratruoc.IErp_DanhGiaLaiCPTT;
import geso.traphaco.erp.beans.taisancodinh.IKhauHaoDuKien;
import geso.traphaco.erp.beans.taisancodinh.imp.KhauHaoDuKien;
import geso.traphaco.erp.beans.park.IErpPhieunhapkho;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
public class Erp_DanhGiaLaiCPTT extends Phan_Trang implements IErp_DanhGiaLaiCPTT
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	dbutils db;
	String Id , Diengiai , Ma , LtsId, NtsId , ctyId , SothangKH , Dvt , ThangbatdauKH , PpKH , Msg , Nguoitao ,
	Nguoisua , Ngaytao , Ngaysua , Trangthai , userTen , userId , SoLuong , DonGia , ThanhTien;

	String[] cdtsIds;

	String[] khoanmucIds;
	
	List<IKhauHaoDuKien> khauhaodukienList = new ArrayList<IKhauHaoDuKien>();
	
	String cdId;
	String kmId;
	

	ResultSet TsList;


	String cpttId;
	String loaiCPTTId;
	String loaiCPTTId_New;
	ResultSet cpttRs;
	ResultSet loaiCPTTRs;
	ResultSet loaiCPTT_NEWRs;
	String sochungtu;
	String sodieuchuyen;
	String tungay;
	String denngay;
	String ngaychungtu= getDateTime();
	String maTSDD = "";
	
	String nguyengia;
	String nguyengiamoi;
	String dieuchinhnguyengia;
	
	String sothangkh;
	String sothangkhmoi;
	String dieuchinhsothangkh;
	String dienGiaiCT;
	private int num;
	private int[] listPages;
	private int currentPages;
	
	
	public Erp_DanhGiaLaiCPTT( )
	{
		this.Id = "";

		this.sodieuchuyen="";
		this.tungay="";
		this.denngay="";

		this.SothangKH = "";
		this.ThangbatdauKH = "";
		this.Msg = "";
		this.Nguoitao = "";
		this.Nguoisua = "";
		this.sochungtu="";
		this.Ngaytao = "";
		this.Ngaysua = "";
		this.nguyengia="";
		this.nguyengiamoi="";
		this.dieuchinhnguyengia="";
	    this.sothangkh="";
	    this.sothangkhmoi="";
	    this.dieuchinhsothangkh="";
		this.Trangthai = "1";
		this.userId = "";
		this.userTen = "";
		this.ngaychungtu="";
		this.cpttId="";
		this.dienGiaiCT = "";
		this.loaiCPTTId="";
		currentPages = 1;
		num = 1;

		
		this.db = new dbutils();
		
	}
	
	public Erp_DanhGiaLaiCPTT( String id )
	{
		this.Id = id;
		this.sodieuchuyen="";
		this.tungay="";
		this.nguyengia="";
		this.nguyengiamoi="";
		this.dieuchinhnguyengia="";
	    this.sothangkh="";
	    this.sothangkhmoi="";
	    this.dieuchinhsothangkh="";
		this.denngay="";
		this.cpttId="";
		this.SothangKH = "";
		this.ThangbatdauKH = "";
		this.Msg = "";
		this.Nguoitao = "";
		this.Nguoisua = "";
		this.Ngaytao = "";
		this.Ngaysua = "";
		this.Trangthai = "1";
		this.userId = "";
		this.userTen = "";
		this.cpttId="";
		currentPages = 1;
		this.dienGiaiCT ="";
		this.loaiCPTTId="";
		num = 1;
		this.db = new dbutils();
		this.Msg = "";
		this.sochungtu="";
		
		
	}
	
	public String getId()
	{
		return Id;
	}
	
	public String getMa()
	{
		return Ma;
	}
	
	public String getDiengiai()
	{
		return this.Diengiai;
	}
	

	public String getMsg()
	{
		return Msg;
	}
	
	public void setId(String id)
	{
		this.Id = id;
	}
	
	public void setMa(String ma)
	{
		this.Ma = ma;
	}
	
	public void setDiengiai(String diengiai)
	{
		this.Diengiai = diengiai;
	}
	
	public void setMsg(String Msg)
	{
		this.Msg = Msg;
	}
	
	public ResultSet getRsts()
	{
		return TsList;
	}
	
	public void setRsts(ResultSet Rsts)
	{
		this.TsList = Rsts;
	}
	

	public void setUserid(String userId)
	{
		this.userId = userId;
	}
	
	public String getUserid()
	{
		return userId;
	}
	
	public void setUserTen(String userTen)
	{
		this.userTen = userTen;
	}
	
	public String getUserTen()
	{
		return userTen;
	}

	public void setLtsId(String ltsId)
	{
		this.LtsId = ltsId;
	}
	
	public String getLtsId()
	{
		return this.LtsId;
	}

	public void setCdtsIds(String[] cdtsIds)
	{

		this.cdtsIds = cdtsIds;
	}

	public String[] getCdtsIds()
	{

		return cdtsIds;
	}
	
	public String getNgaytao()
	{
		return Ngaytao;
	}
	
	public String getNguoitao()
	{
		return Nguoitao;
	}
	
	public String getNgaysua()
	{
		return Ngaysua;
	}
	
	public String getNguoisua()
	{
		return Nguoisua;
	}
	
	public void setNgaytao(String ngaytao)
	{
		this.Ngaytao = ngaytao;
	}
	
	public void setNguoitao(String nguoitao)
	{
		this.Nguoitao = nguoitao;
	}
	
	public void setNgaysua(String ngaysua)
	{
		this.Ngaysua = ngaysua;
	}
	
	public void setNguoisua(String nguoisua)
	{
		this.Nguoisua = nguoisua;
	}
	
	public String getTrangthai()
	{
		return Trangthai;
	}
	
	public void setTrangthai(String trangthai)
	{
		this.Trangthai = trangthai;
	}
	
	public String getNtsId()
	{
		return NtsId;
	}
		
	
	public String getDvt()
	{
		return Dvt;
	}
	
	public String getSothangKH()
	{
		return SothangKH;
	}
	
	public String getThangbatdauKH()
	{
		return ThangbatdauKH;
	}
	
	public String getPpKH()
	{
		return PpKH;
	}
	
	public void setNtsId(String ntsId)
	{
		this.NtsId = ntsId;
	}
	
	public void setCtyId(String ctyId)
	{
		this.ctyId = ctyId;
	}
	

	public String getCtyId()
	{
		return this.ctyId;
	}

	public void setDvt(String dvt)
	{
		this.Dvt = dvt;
	}
	
	public void setSothangKH(String sothangKh)
	{
		this.SothangKH = sothangKh;
	}
	
	public void setThangbatdauKH(String thangbatdauKh)
	{
		this.ThangbatdauKH = thangbatdauKh;
	}
	
	public void setPpKH(String ppKh)
	{
		this.PpKH = ppKh;
	}
	
	public String getSoLuong()
	{
		return this.SoLuong;
	}
	
	public void setSoLuong(String soluong)
	{
		this.SoLuong = soluong;
	}
	
	public String getDonGia()
	{
		return this.DonGia;
	}
	
	public void setDonGia(String dongia)
	{
		this.DonGia = dongia;
	}
	
	public void setThanhTien(String thanhtien)
	{
		this.ThanhTien = thanhtien;
	}
	
	public String getThanhTien()
	{
		return this.ThanhTien;
	}
	
	
		
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	

	public boolean themmoiMa(HttpServletRequest request)
	{
		Utility util = new Utility();
		int thang = 0;
		int nam = 0;
		try {
			
			this.db.getConnection().setAutoCommit(false);
	
			if(this.cpttId.length()<=0)
			{
				this.Msg= "Vui lòng chọn chi phí trả trước!";
			}
	
			
			if(this.ngaychungtu.length() == 10)
			{
				thang = Integer.valueOf(this.ngaychungtu.substring(5, 7))+1;
				nam = Integer.valueOf(this.ngaychungtu.substring(0, 4));
			}
			if(this.loaiCPTTId_New.length()<=0)
			{
				this.loaiCPTTId_New=this.loaiCPTTId;
			}
			String query="INSERT INTO ERP_DANHGIALAICHIPHITRATRUOC (CCDC_FK,LOAICCDC_FK_OLD,LOAICCDC_FK_NEW,NGAYCHUNGTU,SOCHUNGTU,TRANGTHAI,THANG,NAM,NGUOITAO,NGUOISUA,NGAYTAO,NGAYSUA,SOTHANGKHAUHAO,SOTHANGKHAUHAOMOI,DIEUCHINHSOTHANGKHAUHAO,NGUYENGIA,NGUYENGIAMOI,DIEUCHINHNGUYENGIA,DIENGIAI) " +
						"VALUES ('"+this.cpttId+"',"+this.loaiCPTTId+","+this.loaiCPTTId_New+",'"+this.ngaychungtu+"','"+this.sochungtu+"','0','"+thang+"','"+nam+"','"+this.userId+"','"+this.userId+"','"+this.getDateTime()+"','"+this.getDateTime()+"',"+this.sothangkh+","+this.sothangkhmoi+","+this.dieuchinhsothangkh+"," +
								""+this.nguyengia.replaceAll(",", "")+","+this.nguyengiamoi.replaceAll(",", "")+","+this.dieuchinhnguyengia.replaceAll(",", "")+",N'"+this.dienGiaiCT+"') ";
				System.out.println("QUERY TẠO MỚI "+query);
				if(!this.db.update(query))
				{
					this.setMsg("Không thể tạo mới đánh giá tài sản");
					return false;
				}
			this.db.getConnection().commit();
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}
		return true;
	}
	
	public boolean UpdateMa(HttpServletRequest request)
	{
		Utility util = new Utility();
		int thang = 0;
		int nam = 0;
		try {
			
			this.db.getConnection().setAutoCommit(false);
	
			if(this.cpttId.length()<=0)
			{
				this.Msg= "Vui lòng chọn chi phí trả trước!";
			}
		
			if(this.ngaychungtu.length() == 10)
			{
				thang = Integer.valueOf(this.ngaychungtu.substring(5, 7));
				nam = Integer.valueOf(this.ngaychungtu.substring(0, 4));
			}
			
			if(this.loaiCPTTId_New.length()<=0)
			{
				this.loaiCPTTId_New=this.loaiCPTTId;
			}
	
			String query="Update ERP_DANHGIALAICHIPHITRATRUOC SET CCDC_FK='"+this.cpttId+"', SOCHUNGTU='"+this.sochungtu+"',NGAYCHUNGTU='"+this.ngaychungtu+"',NGAYSUA='"+getDateTime()+"',NGUOISUA="+this.userId+" " +
					" ,SOTHANGKHAUHAO = "+this.sothangkh+", SOTHANGKHAUHAOMOI="+this.sothangkhmoi+", DIEUCHINHSOTHANGKHAUHAO = "+this.dieuchinhsothangkh+" , NGUYENGIA ="+this.nguyengia.replaceAll(",","")+", NGUYENGIAMOI = "+this.nguyengiamoi.replaceAll(",", "")+"" +
					" , DIEUCHINHNGUYENGIA = "+this.dieuchinhnguyengia.replaceAll(",", "")+",DIENGIAI=N'"+this.dienGiaiCT+"',LOAICCDC_FK_NEW="+this.loaiCPTTId_New+"WHERE PK_SEQ = "+this.Id;
			if(!this.db.update(query))
			{
				this.setMsg("Không thể cập nhật đánh giá tài sản");
				this.db.getConnection().rollback();
				return false;
			}
			
		
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}
		return true;
		
	}
	public void Delete(String id) 
	{		
		
	

			try
			{
					
					 
					String query = "UPDATE ERP_DANHGIALAICHIPHITRATRUOC set trangthai=2 WHERE PK_SEQ = " + id + "";
					System.out.println(query);
					
					this.db.update(query);
					
							
				
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	public void init(String sql)
	{
		NumberFormat formatter = new DecimalFormat("#,###,###.##"); 
		try {
		String query="SELECT CCDC_FK,LOAICCDC_FK_OLD,LOAICCDC_FK_NEW,NGAYCHUNGTU,SOCHUNGTU,TRANGTHAI,THANG,NAM,SOTHANGKHAUHAO,SOTHANGKHAUHAOMOI," +
				" DIEUCHINHSOTHANGKHAUHAO,NGUYENGIA,NGUYENGIAMOI,DIEUCHINHNGUYENGIA,ISNULL(DIENGIAI,'') AS DIENGIAI " +
				"FROM ERP_DANHGIALAICHIPHITRATRUOC WHERE PK_SEQ ="+this.Id ;
		ResultSet dcRs= db.get(query);
		System.out.println("QUERY :"+query);
		
			while(dcRs.next())
			{
				this.cpttId=dcRs.getString("CCDC_FK");
				this.loaiCPTTId=dcRs.getString("LOAICCDC_FK_OLD");
				this.loaiCPTTId_New=dcRs.getString("LOAICCDC_FK_NEW");
				this.ngaychungtu=dcRs.getString("NGAYCHUNGTU");
				this.sochungtu=dcRs.getString("SOCHUNGTU");
				this.sothangkh=dcRs.getString("SOTHANGKHAUHAO");
				this.sothangkhmoi=dcRs.getString("SOTHANGKHAUHAOMOI");
				this.dieuchinhsothangkh=dcRs.getString("DIEUCHINHSOTHANGKHAUHAO");
				this.nguyengia=formatter.format(dcRs.getDouble("NGUYENGIA"));
				this.nguyengiamoi=formatter.format(dcRs.getDouble("NGUYENGIAMOI"));
				this.dieuchinhnguyengia=formatter.format(dcRs.getDouble("DIEUCHINHNGUYENGIA"));
				this.dienGiaiCT = dcRs.getString("DIENGIAI");
			}
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		
		

	}
	
	public void createRs()
	{
		try {
		NumberFormat formatter = new DecimalFormat("#,###,###.##"); 
		String query="";	
		
		query=" SELECT PK_SEQ,MA + '-' + DIENGIAI as TEN FROM ERP_CONGCUDUNGCU WHERE TRANGTHAI=1";
		this.cpttRs=this.db.get(query);
		
		query=" SELECT PK_SEQ,MA + '-' + DIENGIAI as TEN FROM Erp_LOAICCDC WHERE TRANGTHAI=1";
		this.loaiCPTTRs=this.db.get(query);
		

		query=" SELECT PK_SEQ,MA + '-' + DIENGIAI as TEN FROM Erp_LOAICCDC WHERE TRANGTHAI=1";
		this.loaiCPTT_NEWRs=this.db.get(query);
		
	
		if(this.cpttId.length()>0)
		{
			
			query=" select sothangkh, thanhtien,loaiccdc_fk from ERP_CONGCUDUNGCU where pk_seq = "+this.cpttId ;
			ResultSet rs= db.get(query);
			
				if(rs.next())
				{
					this.sothangkh= rs.getString("sothangkh");
					this.nguyengia= formatter.format(rs.getDouble("thanhtien"));
					this.loaiCPTTId=rs.getString("loaiccdc_fk");
				}
			    rs.close();
		 
						
		
			 if(!this.dieuchinhnguyengia.equals("")||!this.dieuchinhsothangkh.equals(""))
			 {
				 if(this.dieuchinhnguyengia.equals(""))
				 {
				    this.nguyengiamoi= this.nguyengia;
				    this.dieuchinhnguyengia="0";
				 }
				    if(this.dieuchinhsothangkh.equals(""))
				 {
				    this.sothangkhmoi=this.sothangkh;
				    this.dieuchinhsothangkh="0";
				 }
				    query = 	" SELECT CCDC.THANHTIEN AS THANHTIEN , ISNULL(CCDC.SOTHANGKH,0) - ISNULL(CCDC.SOTHANGDAKHAUHAO,0)  SOTHANGKH , ISNULL(THANGDAKHAUHAO.THANGTHU, 0)  AS THANGDAKHAUHAO, \n" +
			        " (ISNULL(CCDC.SOTHANGKH,0) - ISNULL(CCDC.SOTHANGDAKHAUHAO,0) - CONVERT(INT, ISNULL(THANGDAKHAUHAO.THANGTHU, 0)) ) AS THANGCONKH, \n" +
			        " ISNULL(THANGDAKHAUHAO.THANGTHU, 0) + ISNULL(CCDC.SOTHANGDAKHAUHAO,0) AS SOTHANGDAKHAUHAO, \n" +
			        " ISNULL(TIENDAKHAUHAO.TIENDAKHAUHAO,0) as TIENDAKHAUHAO \n" +
					" FROM ERP_CONGCUDUNGCU CCDC \n" +
					" LEFT JOIN \n" +
					"( \n" +
					"	SELECT TOP 1 THANGTHU, CCDC_FK \n" +
					"	FROM ERP_KHAUHAOCCDC \n" +
					"	WHERE KHAUHAO > 0 \n" +
					"	AND CCDC_FK = '" + this.cpttId + "'  \n" +
					"   ORDER BY THANG DESC, NAM DESC \n" +
					")THANGDAKHAUHAO ON THANGDAKHAUHAO.CCDC_FK = CCDC.PK_SEQ \n" +
					"LEFT JOIN \n" +
					"( \n" +
					"	SELECT CCDC_FK, COUNT(KHAUHAO) AS SOTHANGDAKHAUHAO \n" +
					"	FROM ERP_KHAUHAOCCDC \n" +
					"	WHERE CCDC_FK = '" +  this.cpttId + "' AND KHAUHAO > 0 \n" +
					"	GROUP BY CCDC_FK \n" +
					")SOTHANGDAKHAUHAO ON SOTHANGDAKHAUHAO.CCDC_FK =  CCDC.PK_SEQ  \n" +

					"LEFT JOIN \n" +
					"( \n" +
					"	SELECT TOP 1 CCDC_FK, KHAUHAOLUYKE AS TIENDAKHAUHAO \n" +
					"	FROM ERP_KHAUHAOCCDC \n" +
					"	WHERE CCDC_FK = '" +  this.cpttId + "' AND KHAUHAO > 0 \n" +
					"	ORDER BY THANG DESC,NAM DESC \n" +
					")TIENDAKHAUHAO ON TIENDAKHAUHAO.CCDC_FK =  CCDC.PK_SEQ  \n" +

					"WHERE CCDC.PK_SEQ = '" +  this.cpttId + "' ";
			   rs = this.db.get(query);
			   System.out.println("aaaaaaaaaaaaaaa"+query);
			   if(rs != null){
					
			   rs.next();
			   double thanhtien = Double.parseDouble(rs.getString("THANHTIEN")) + Double.parseDouble(this.dieuchinhnguyengia.replaceAll(",", ""));
			   double tiendaKH = Double.parseDouble(rs.getString("TIENDAKHAUHAO"));
			   int thangdaKH = rs.getInt("THANGDAKHAUHAO"); 
			   int sothangKH = rs.getInt("sothangKH") + Integer.parseInt(this.dieuchinhsothangkh);
			   int sothangdaKH =rs.getInt("SOTHANGDAKHAUHAO");
							
			   if(thanhtien > 0 & sothangKH >0 ){ 											
				   this.Tinhkhauhao_Update(sothangKH,thangdaKH,sothangdaKH,thanhtien,tiendaKH);
										
									
			   }
			   }
					rs.close();
			 
		   }
		}
							
				    
					
		
		
		
	
		} catch (Exception e) {
			e.printStackTrace();
			this.Msg="Tài sản này đã được khấu hao hết!";
		}

	}
	
//	public ResultSet Laykhauhao(){
//		System.out.println("'" + this.Id + "'");
//		return this.db.get("'" + this.tscdId + "'"); 
//	}
	private boolean Tinhkhauhao_Update(int sothangKH, int thangdaKH,  int sothangdaKH, double thanhtien, double tiendaKH){
		NumberFormat formatter = new DecimalFormat("#,###,###.##"); 
		double sotienconlai = thanhtien;
		double luykebandau = tiendaKH;
		double conlaibandau = thanhtien - tiendaKH;
		int n = sothangKH;
		double[] khdkVal = new double[n];
		double[] lkdkVal = new double[n];
		double[] gtdkVal = new double[n];
		String query="";
		

		try{
			// LẤY RA THÁNG KHẤU HAO THỰC THẾ TRÊN HỆ THỐNG ĐỂ KIỂM TRA SỐ THÁNG CÒN CHƯA KHẤU HAO CHO TỚI LÚC THAY ĐỔI NGUYÊN GIÁ/ SỐ THÁNG KH
			query="Select thangthu + isnull(ts.sothangdakhauhao,0) as thang,kh.KHAUHAO, kh.KHAUHAOLUYKE,kh.GIATRICONLAI FROM ERP_KHAUHAOTAISAN kh  inner join erp_taisancodinh ts on " +
				  " ts.pk_seq = kh.taisan_fk where TAISAN_FK ="+this.cpttId ;
			ResultSet rs=db.get(query);
			while(rs.next())
			{
				IKhauHaoDuKien khauhao = new KhauHaoDuKien();
				khauhao.setTaisanid(this.cpttId);
				khauhao.setThang(formatter.format(rs.getDouble("thang")));
				khauhao.setKhauhaodukien(formatter.format(rs.getDouble("KHAUHAO")));
				khauhao.setKhauhaothucte(formatter.format(rs.getDouble("KHAUHAO")));
				khauhao.setKhauhaoluykedukien(formatter.format(rs.getDouble("KHAUHAOLUYKE")));
				khauhao.setKhauhaoluykethucte(formatter.format(rs.getDouble("KHAUHAOLUYKE")));
				khauhao.setGiatriconlaidukien(formatter.format(rs.getDouble("GIATRICONLAI")));
				khauhao.setGiatriconlaithucte(formatter.format(rs.getDouble("GIATRICONLAI")));
				khauhaodukienList.add(khauhao);
			}
			rs.close();
			int m = n - thangdaKH;
			int month = sothangdaKH + 1;
			int sothangconlai = n - sothangdaKH;
			//System.out.println("m: " + m);
			
			for(int i = 0 ; i < m; i++)
			{
										
				if(i == (m - 1))  // thang cuoi cung
				{		
					if(i > 0)
						khdkVal[i] = thanhtien - lkdkVal[i-1];
					else
						khdkVal[i] = sotienconlai;
					
					if(i > 0) 
						lkdkVal[i] = khdkVal[i] + lkdkVal[i-1];
					else
						lkdkVal[i] = luykebandau + khdkVal[i] ;

					gtdkVal[i] = thanhtien - lkdkVal[i];
				}
				else
				{
					khdkVal[i] = Math.round(conlaibandau/(n - thangdaKH ));
					
					if(i > 0) 
						lkdkVal[i] = khdkVal[i] + lkdkVal[i-1];
					else
						lkdkVal[i] = luykebandau + khdkVal[i] ;
					
					gtdkVal[i] = thanhtien - lkdkVal[i];
				}					
			 
				if(i != (m-1)){		
					IKhauHaoDuKien khauhao = new KhauHaoDuKien();
					khauhao.setTaisanid(this.cpttId);
					khauhao.setThang(formatter.format(month));
					khauhao.setKhauhaodukien(formatter.format(khdkVal[i]));
					khauhao.setKhauhaothucte("0");
					khauhao.setKhauhaoluykedukien(formatter.format(lkdkVal[i]));
					khauhao.setKhauhaoluykethucte(formatter.format(luykebandau));
					khauhao.setGiatriconlaidukien(formatter.format(gtdkVal[i]));
					khauhao.setGiatriconlaithucte(formatter.format(conlaibandau));
					khauhaodukienList.add(khauhao);
				}else{
					IKhauHaoDuKien khauhao = new KhauHaoDuKien();
					khauhao.setTaisanid(this.cpttId);
					khauhao.setThang(formatter.format(month));
					khauhao.setKhauhaodukien(formatter.format(khdkVal[i]));
					khauhao.setKhauhaothucte("0");
					khauhao.setKhauhaoluykedukien(formatter.format(lkdkVal[i]));
					khauhao.setKhauhaoluykethucte(formatter.format(khdkVal[i]));
					khauhao.setGiatriconlaidukien(formatter.format(gtdkVal[i]));
					khauhao.setGiatriconlaithucte(formatter.format(conlaibandau));
					khauhaodukienList.add(khauhao);
				}
				//System.out.println("khauhao_update " + month + ": " + query);
				month++;
				}
								
		}catch(Exception e){
			System.out.println("khauhao_update: " + e.toString());
			return false;
		}
		return true;
	}
	
	public void DBClose()
	{
		try
		{
			if (TsList != null)
			{
				TsList.close();
			}	
		}
		catch (Exception er)
		{
			er.printStackTrace();
		}
		finally
		{
			if (db != null)
			{
				db.shutDown();
			}
		}
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
		this.listPages= listPages;
		
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

	

	public String getNgaychungtu() {
		return ngaychungtu;
	}

	public void setNgaychungtu(String ngaychungtu) {
		this.ngaychungtu = ngaychungtu;
	}


	public String getSochungtu() {
		return sochungtu;
	}

	public void setSochungtu(String sochungtu) {
		this.sochungtu = sochungtu;
	}


	public String[] getKhoanmucIds() {
		return khoanmucIds;
	}

	public void setKhoanmucIds(String[] khoanmucIds) {
		this.khoanmucIds = khoanmucIds;
	}

	public String getSodieuchuyen() {
		return sodieuchuyen;
	}

	public void setSodieuchuyen(String sodieuchuyen) {
		this.sodieuchuyen = sodieuchuyen;
	}

	public String getTungay() {
		return tungay;
	}

	public void setTungay(String tungay) {
		this.tungay = tungay;
	}

	public String getDenngay() {
		return denngay;
	}

	public void setDenngay(String denngay) {
		this.denngay = denngay;
	}

	public String getNguyengia() {
		return nguyengia;
	}

	public void setNguyengia(String nguyengia) {
		this.nguyengia = nguyengia;
	}

	public String getNguyengiamoi() {
		return nguyengiamoi;
	}

	public void setNguyengiamoi(String nguyengiamoi) {
		this.nguyengiamoi = nguyengiamoi;
	}

	public String getDieuchinhnguyengia() {
		return dieuchinhnguyengia;
	}

	public void setDieuchinhnguyengia(String dieuchinhnguyengia) {
		this.dieuchinhnguyengia = dieuchinhnguyengia;
	}

	public String getSothangkh() {
		return sothangkh;
	}

	public void setSothangkh(String sothangkh) {
		this.sothangkh = sothangkh;
	}

	public String getSothangkhmoi() {
		return sothangkhmoi;
	}

	public void setSothangkhmoi(String sothangkhmoi) {
		this.sothangkhmoi = sothangkhmoi;
	}

	public String getDieuchinhsothangkh() {
		return dieuchinhsothangkh;
	}

	public void setDieuchinhsothangkh(String dieuchinhsothangkh) {
		this.dieuchinhsothangkh = dieuchinhsothangkh;
	}

	public String getCdId() {
		return cdId;
	}

	public void setCdId(String cdId) {
		this.cdId = cdId;
	}

	public String getKmId() {
		return kmId;
	}

	public void setKmId(String kmId) {
		this.kmId = kmId;
	}

	public List<IKhauHaoDuKien> getKhauhaodukienList() {
		return khauhaodukienList;
	}

	public void setKhauhaodukienList(List<IKhauHaoDuKien> khauhaodukienList) {
		this.khauhaodukienList = khauhaodukienList;
	}
	public String getDienGiaiCT(){
		return this.dienGiaiCT;
	}
	public void setDienGiaiCT(String dienGiaiCT){
		this.dienGiaiCT = dienGiaiCT;
	}

	public String getCpttId() {
		return cpttId;
	}

	public void setCpttId(String cpttId) {
		this.cpttId = cpttId;
	}

	public ResultSet getLoaiCPTTRs() {
		return loaiCPTTRs;
	}

	public void setLoaiCPTTRs(ResultSet loaiCPTTRs) {
		this.loaiCPTTRs = loaiCPTTRs;
	}

	public ResultSet getCpttRs() {
		return cpttRs;
	}

	public void setCpttRs(ResultSet cpttRs) {
		this.cpttRs = cpttRs;
	}

	public String getLoaiCPTTId() {
		return loaiCPTTId;
	}

	public void setLoaiCPTTId(String loaiCPTTId) {
		this.loaiCPTTId = loaiCPTTId;
	}

	public String getLoaiCPTTId_New() {
		return loaiCPTTId_New;
	}

	public void setLoaiCPTTId_New(String loaiCPTTId_New) {
		this.loaiCPTTId_New = loaiCPTTId_New;
	}

	public ResultSet getLoaiCPTT_NEWRs() {
		return loaiCPTT_NEWRs;
	}

	public void setLoaiCPTT_NEWRs(ResultSet loaiCPTT_NEWRs) {
		this.loaiCPTT_NEWRs = loaiCPTT_NEWRs;
	}
	
}
