package geso.traphaco.center.beans.hoadontaichinh.imp;

import geso.traphaco.center.beans.dondathang.imp.ErpThongtinkho;
import geso.traphaco.center.beans.hoadontaichinh.IErpHoadontaichinh;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.UtilityKeToan;
import geso.traphaco.center.util.WebService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import net.sf.jasperreports.engine.JRDataSource;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ErpHoadontaichinh implements IErpHoadontaichinh
{
	String userId;
	String id;
	
	String ngayxuatHD;
	String ngayghinhanCN;
	String ghichu;
	String ptchietkhau;
	String kyhieuhoadon;
	String sohoadon;
	String hinhthuctt;
	String nguoimua;
	String innguoimua;

	String loaidonhang;  //0 đơn đặt hàng, 1 đơn hàng khuyến mại ứng hàng, 3 đơn hàng khuyến mại tích lũy, 4 đơn hàng trưng bày, 5 đơn hàng khác
	String loaiXHD;
	
	String msg;
	String trangthai;
	String khoNhanId;
	ResultSet khoNhanRs;
	String nppId;
	ResultSet nppRs;

	String ddhId;
	ResultSet ddhRs;
	String phanloai;
	JRDataSource datasource ;

	public JRDataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(JRDataSource datasource) {
		this.datasource = datasource;
	}

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
	String[] spTienthue;
	
	String[] dhCk_diengiai;
	String[] dhCk_giatri;
	String[] dhCk_loai;
	
	String[] tichluy_scheme;
	String[] tichluy_tongtien;
	String[] tichluy_vat;
	String[] tichluy_tienavat;
	String[] tichluy_tienvat;
	
	String[] spIskhongthue;


	
	String bvat;
	String totalCHIETKHAU;
	String thueVAT;
	String avat;
	String mavuviec="";
	String noibo;
	
	dbutils db;
	
	public ErpHoadontaichinh()
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
		this.msg = "";
		this.trangthai = "0";
		this.ddhId = "";
		
		this.bvat = "0";
		this.totalCHIETKHAU = "0";
		this.thueVAT = "0";
		this.avat = "0";
		
		this.loaidonhang = "0";
		this.nguoimua = ""; 
		this.innguoimua = "";
		this.loaiXHD ="";
		this.ptchietkhau="0";
		this.noibo = "0";
		this.phanloai="";
		this.db = new dbutils();
	}
	
	public ErpHoadontaichinh(String id)
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
		this.msg = "";
		this.trangthai = "0";
		this.ddhId = "";
		
		this.bvat = "0";
		this.totalCHIETKHAU = "0";
		this.thueVAT = "0";
		this.avat = "0";

		this.loaidonhang = "0";
		this.nguoimua= "";
		this.innguoimua = "";
		
		this.loaiXHD ="";
		this.ptchietkhau="0";
		this.noibo = "0";
		this.phanloai="";
		this.db = new dbutils();
	}
	public String[] getSpIskhongthue() {
		return spIskhongthue;
	}

	public void setSpIskhongthue(String[] spIskhongthue) {
		this.spIskhongthue = spIskhongthue;
	}
	public String getPhanloai() {
		return phanloai;
	}

	public void setPhanloai(String phanloai) {
		this.phanloai = phanloai;
	}
	
	public String[] getTichluy_tienvat() {
		return tichluy_tienvat;
	}

	public void setTichluy_tienvat(String[] tichluy_tienvat) {
		this.tichluy_tienvat = tichluy_tienvat;
	}

	public String[] getTichluy_tienavat() {
		return tichluy_tienavat;
	}

	public void setTichluy_tienavat(String[] tichluy_tienavat) {
		this.tichluy_tienavat = tichluy_tienavat;
	}
	
	public String getInNguoimua() 
	{
		return this.innguoimua;
	}

	public void setInNguoimua(String innguoimua) 
	{
		this.innguoimua = innguoimua;
	}
	
	public String getMavuviec() {
		return mavuviec;
	}

	public void setMavuviec(String mavuviec) {
		this.mavuviec = mavuviec;
	}
	
	public String getPtchietkhau() {
		return ptchietkhau;
	}

	public void setPtchietkhau(String ptchietkhau) {
		this.ptchietkhau = ptchietkhau;
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


	public void createRs() 
	{
		String query = "select PK_SEQ, MAFAST + '-' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1'  " +
				" and pk_seq in ( select NPP_FK from ERP_DONDATHANG where pk_seq in ( " + this.ddhId + " ) )";
		this.nppRs = db.get(query);


		if(this.ddhId.length()>0)
		{
			query = "select pk_seq,ten from kenhbanhang where pk_Seq in (select kbh_fk from Erp_DonDatHang where pk_Seq in ("+this.ddhId+"))";
			this.kbhRs = this.db.get(query);
		}

		if(this.id.length() <=0 )
		{
			// TỰ TẠO SỐ HÓA ĐƠN CỦA USER
			int kbDLN =0;
			String chuoiHD= "";
			long sohoadontu= 0;
			String sohoadonden= "";
			String mau = "1";
			
			try
			{
				//KIEM TRA USER ĐÃ KHAI BAO SO HOA DON TRONG DLN CHUA
				query=  " select count(pk_seq) as dem" +
						" from NHANVIEN" +
						" where pk_seq = '"+ this.userId+"' and  isnull(sohoadontu,'') != '' and isnull(sohoadonden,'')  != ''" +
						"       and isnull(kyhieu, '') != ''  ";

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
					this.msg = "Vui lòng khai báo Số hóa đơn trong menu Cập nhật nhân viên cho user này ";
				}
				else
				{
					// LAY KY HIEU HOA DON ,SOHDTU TRONG DLN
					query= "  select kyhieu as kyhieuhoadon, sohoadontu, sohoadonden " +
							" from NHANVIEN " +
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

					// KIEM TRA SOHOADON DA DUOC DUNG CHUA
					// ETC
					query = " select count(pk_seq) as dem" +
							" from ERP_HOADON" +
							" where RTRIM(LTRIM(kyhieu)) = '"+ this.kyhieuhoadon +"' and cast((case when  sohoadon!='NA' then sohoadon else 0 end) as int) >=  "+ sohoadontu +" " +
							"       and trangthai != '3' and nguoisua= "+ this.userId +" and sohoadon != 'NA' and mauhoadon = 1  ";
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

					//OTC
					query = " select count(pk_seq) as dem" +
							" from HOADON" +
							" where RTRIM(LTRIM(kyhieu)) = '"+ this.kyhieuhoadon +"' and cast((case when  sohoadon!='NA' then sohoadon else 0 end) as numeric(18,0)) >= "+ sohoadontu +" and cast(sohoadon as numeric(18,0))<= " + Integer.parseInt(sohoadonden) + " " +
							"        and trangthai != '3'  and nguoisua= "+ this.userId +" and sohoadon != 'NA' and mauhoadon = 1 ";
					System.out.println("Câu kiểm tra SHD "+query);
					ResultSet KiemTra_OTC = db.get(query);
					int check_OTC = 0;
					if(KiemTra_OTC != null)
					{
						while(KiemTra_OTC.next())
						{
							check_OTC = KiemTra_OTC.getInt("dem");
						}
						KiemTra_OTC.close();
					}

					// LAY SOIN MAX	
					if(check <= 0 && check_OTC <= 0)
					{
						chuoiHD = ("000000"+ sohoadontu);
						chuoiHD = chuoiHD.substring(chuoiHD.length() - 7, chuoiHD.length());
					}
					else
					{
						// LAY SOIN MAX TRONG HOADON : 
						//OTC
						query = " select  MAX(cast((case when  sohoadon!='NA' then sohoadon else 0 end) as numeric)) as SOIN_MAX" +
								" from HOADON where RTRIM(LTRIM(KYHIEU)) ='"+ this.kyhieuhoadon +"' and cast((case when  sohoadon!='NA' then sohoadon else 0 end) as numeric(18,0)) >= "+ sohoadontu +" and cast(sohoadon as numeric(18,0))<= " + Integer.parseInt(sohoadonden) + " " +
								"       and trangthai != '3'  and nguoisua= "+ this.userId +" and sohoadon != 'NA' and mauhoadon = 1 ";
						System.out.println("Câu lấy shd max "+query);
						ResultSet laySOIN = db.get(query);
						long soinMAX_OTC= 0;
						if(laySOIN!= null)
						{
							while(laySOIN.next())
							{
								soinMAX_OTC = laySOIN.getLong("SOIN_MAX");
							}
							laySOIN.close();
						}

						//ETC
						query = " select  MAX(cast((case when  sohoadon!='NA' then sohoadon else 0 end) as numeric)) as SOIN_MAX" +
								" from ERP_HOADON " +
								" where RTRIM(LTRIM(KYHIEU)) ='"+ this.kyhieuhoadon +"' and cast((case when  sohoadon!='NA' then sohoadon else 0 end) as numeric(18,0)) >= "+ sohoadontu +" and cast(sohoadon as numeric(18,0))<= " + Integer.parseInt(sohoadonden) + " " +
								"       and trangthai != '3'  and nguoisua= "+ this.userId +" and sohoadon != 'NA' and mauhoadon = 1 ";
						System.out.println("Câu lấy shd max "+query);
						ResultSet laySOIN_ETC = db.get(query);
						long soinMAX_ETC= 0;
						if(laySOIN_ETC!= null)
						{
							while(laySOIN_ETC.next())
							{
								soinMAX_ETC = laySOIN_ETC.getLong("SOIN_MAX");
							}
							laySOIN_ETC.close();
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

				if(this.nppId.trim().length() > 0 )
				{	
					this.nguoimua = "";
							
					// LAY DONHANG	
					query = " select PK_SEQ , NgayDonHang  " +
							" from ERP_DONDATHANG " +
							" where  NPP_FK = '"+ this.nppId +"'   " +
							" and pk_seq not in (select a.DDH_FK " +
							"                    from  ERP_HOADON_DDH a inner join ERP_HOADON b on a.HOADON_FK=b.PK_SEQ" +
							"                    where b.TRANGTHAI in ( 1, 2, 4) )" ;
					System.out.println("Câu query "+query);		
					this.ddhRs = db.get(query);
				}
				
				String chuoi = this.ddhId;
				if(chuoi.length() > 0)
				{	
					query = "select b.PK_SEQ as SPID, b.MA, b.TEN, DV.donvi, a.dongia , "+
							"  isnull(scheme,' ') as scheme , isnull(a.thuevat,0) as vat ,   " +
							"  SUM( a.soluong)  as soluong, SUM( isnull(a.chietkhau, 0)) as chietkhau,isnull(a.IS_KHONGTHUE,0) as IS_KHONGTHUE "+
							"from ERP_DONDATHANG_SANPHAM a inner Join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ   "+  	 
							" INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK  " +
							" inner join  ERP_DONDATHANG c on a.dondathang_fk=c.pk_seq    "+
							"where a.dondathang_fk in ( "+ chuoi +" ) and a.dondathang_fk in (select pk_seq from ERP_DONDATHANG where NPP_FK="+ this.nppId +")  " +
							" group by b.PK_SEQ , b.MA, b.TEN, DV.donvi, a.dongia , isnull(scheme,' ') , isnull(a.thuevat,0),isnull(a.IS_KHONGTHUE,0)  order by b.Ma ASC ";

					System.out.println("Lấy sản phẩm: "+query);
					ResultSet rsLaySP = db.get(query);

					String spID = "";
					String spMA = "";
					String spTEN = "";
					String spDONVI = "";
					String spSOLUONG = "";
					String spGIANHAP = "";
					String spCHIETKHAU = "";
					String spSCHEME = "";
					String spVAT = "";
					String spTIENTHUE = "";
					String iskht ="";
					if(rsLaySP!= null)
					{				    	
						while(rsLaySP.next())
						{
							spID += rsLaySP.getString("SPID") + "__";
							spMA += rsLaySP.getString("MA") + "__";
							spTEN += rsLaySP.getString("TEN") + "__";
							spDONVI += rsLaySP.getString("DONVI") + "__";
							spSOLUONG += (rsLaySP.getDouble("SOLUONG")) + "__";
							spGIANHAP += (rsLaySP.getDouble("DONGIA")) + "__";
							spCHIETKHAU += (rsLaySP.getDouble("chietkhau")) + "__";
							if( rsLaySP.getString("scheme").trim().length() <= 0 )
								spSCHEME += " __";
							else
								spSCHEME += rsLaySP.getString("scheme") + "__";
							
							spVAT +=  (rsLaySP.getDouble("vat")) + "__";
							spTIENTHUE +=  Math.round( ( Math.round( rsLaySP.getDouble("SOLUONG") * rsLaySP.getDouble("DONGIA") ) - rsLaySP.getDouble("chietkhau") ) * rsLaySP.getDouble("vat") / 100 ) + "__";
							iskht += rsLaySP.getString("IS_KHONGTHUE") + "__";
						}
						rsLaySP.close();

						if(spMA.trim().length() > 0)
						{
							spID = spID.substring(0, spID.length() - 2);
							this.spId = spID.split("__");

							spMA = spMA.substring(0, spMA.length() - 2);
							this.spMa = spMA.split("__");

							spTEN = spTEN.substring(0, spTEN.length() - 2);
							this.spTen = spTEN.split("__");

							spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
							this.spDonvi = spDONVI.split("__");

							spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
							this.spSoluong = spSOLUONG.split("__");

							spGIANHAP = spGIANHAP.substring(0, spGIANHAP.length() - 2);
							this.spDongia = spGIANHAP.split("__");

							spCHIETKHAU = spCHIETKHAU.substring(0, spCHIETKHAU.length() - 2);
							this.spChietkhau = spCHIETKHAU.split("__");

							spSCHEME = spSCHEME.substring(0, spSCHEME.length() - 2);
							this.spSCheme = spSCHEME.split("__");

							spVAT = spVAT.substring(0, spVAT.length() - 2);
							this.spVat = spVAT.split("__");

							spTIENTHUE = spTIENTHUE.substring(0, spTIENTHUE.length() - 2);
							this.spTienthue = spTIENTHUE.split("__");
							
							iskht = iskht.substring(0, iskht.length() - 2);
							this.spIskhongthue = iskht.split("__");


						}
					}
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		else // ID > 0
		{
		/*	query = "select b.PK_SEQ as SPID, b.MA, isnull(a.tensp,b.TEN) as ten, a.donvitinh, a.soluong, a.dongia, isnull(a.chietkhau, 0) as chietkhau, a.scheme, a.vat," +
					"	ROUND ( isnull( TIENVAT, ( ( round( soluong * dongia, 0 ) - chietkhau ) * isnull(vat, 0) / 100 ) ), 0) as tienVAT  " +
					"from ERP_HOADON_SP a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ   " +
					"where a.hoadon_fk = "+ this.id +"   order by a.vat asc " ;		
		*/	
			query= "\n select b.PK_SEQ as SPID, b.MA, isnull(a.tensp,b.TEN) as ten, a.donvitinh, a.soluong, a.dongia, isnull(a.chietkhau, 0) as chietkhau, a.scheme,CASE WHEN isnull(a.IS_KHONGTHUE,0) =1 THEN 0 ELSE ( case when d.thuevat=0 then (select nh.THUEXUAT from nganhhang nh where pk_seq=b.nganhhang_fk) else d.thuevat end ) END vat,	"+
					"\n  ROUND ( isnull( a.TIENVAT, ( ( round( a.soluong * a.dongia, 0 ) - a.chietkhau ) * isnull(d.thueVAT, 0) / 100 ) ), 0) as tienVAT,isnull(a.IS_KHONGTHUE,0) as IS_KHONGTHUE  "+ 
					"\n  from ERP_HOADON_SP a  "+ 
					"\n  inner Join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ    "+ 
					"\n  inner join ERP_HOADON_DDH c on c.HOADON_FK=a.HOADON_FK "+
					"\n  left join ERP_DONDATHANG_SANPHAM d on d.dondathang_fk=c.DDH_FK and d.sanpham_fk=a.SANPHAM_FK "+
					"\n  where a.hoadon_fk ="+ this.id +"   order by case when d.thuevat=0 then (select nh.THUEXUAT from nganhhang nh where pk_seq=b.nganhhang_fk) else d.thuevat end ,b.ma  ";
			

			System.out.println("INIT sản phẩm: "+query);
			ResultSet rsLaySP = db.get(query);
			try 
			{
				String spID = "";
				String spMA = "";
				String spTEN = "";
				String spDONVI = "";
				String spSOLUONG = "";
				String spGIANHAP = "";
				String spCHIETKHAU = "";
				String spSCHEME = "";
				String spVAT = "";
				String spTIENTHUE = "";
				String iskht ="";
				if(rsLaySP!= null)
				{				    	
					while(rsLaySP.next())
					{
						spID += rsLaySP.getString("SPID") + "__";
						spMA += rsLaySP.getString("MA") + "__";
						spTEN += rsLaySP.getString("TEN") + "__";
						spDONVI += rsLaySP.getString("DONVITINH") + "__";
						spSOLUONG += (rsLaySP.getDouble("SOLUONG")) + "__";
						spGIANHAP += (rsLaySP.getDouble("DONGIA")) + "__";
						spCHIETKHAU += (rsLaySP.getDouble("chietkhau")) + "__";
						if( rsLaySP.getString("scheme").trim().length() <= 0 )
							spSCHEME += " __";
						else
							spSCHEME += rsLaySP.getString("scheme") + "__";
						spVAT +=  (rsLaySP.getDouble("vat")) + "__";
						spTIENTHUE +=  (rsLaySP.getDouble("tienVAT")) + "__";
						iskht += rsLaySP.getString("IS_KHONGTHUE") + "__";
					}
					rsLaySP.close();

					if(spMA.trim().length() > 0)
					{
						spID = spID.substring(0, spID.length() - 2);
						this.spId = spID.split("__");

						spMA = spMA.substring(0, spMA.length() - 2);
						this.spMa = spMA.split("__");

						spTEN = spTEN.substring(0, spTEN.length() - 2);
						this.spTen = spTEN.split("__");

						spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
						this.spDonvi = spDONVI.split("__");

						spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
						this.spSoluong = spSOLUONG.split("__");

						spGIANHAP = spGIANHAP.substring(0, spGIANHAP.length() - 2);
						this.spDongia = spGIANHAP.split("__");

						spCHIETKHAU = spCHIETKHAU.substring(0, spCHIETKHAU.length() - 2);
						this.spChietkhau = spCHIETKHAU.split("__");

						spSCHEME = spSCHEME.substring(0, spSCHEME.length() - 2);
						this.spSCheme = spSCHEME.split("__");

						spVAT = spVAT.substring(0, spVAT.length() - 2);
						this.spVat = spVAT.split("__");

						spTIENTHUE = spTIENTHUE.substring(0, spTIENTHUE.length() - 2);
						this.spTienthue = spTIENTHUE.split("__");
						iskht = iskht.substring(0, iskht.length() - 2);
						this.spIskhongthue = iskht.split("__");
					}
				}
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}

	public void init() 
	{
		NumberFormat formatter = new DecimalFormat("##,###,###");
		Utility util = new Utility();

		String query =  "  select isnull(c.phanloai,'') phanloai,isnull(c.ChietKhau,0) as ptchietkhau,a.kho_fk,dondathang_fk,( select kbh_fk from ERP_DONDATHANG where pk_seq in ( select DDH_FK from ERP_HOADON_DDH where hoadon_fk = a.pk_seq ) ) as kbhId, a.npp_fk, ngayxuatHD, ISNULL(a.ghichu, '') as ghichu,  " + 
						"  		a.npp_fk, a.trangthai, kyhieu,loaihoadon, sohoadon, hinhthuctt ,  isnull(a.chietkhau,0) as chietkhau,  (select   case   len(tennguoidaidien) when 0 then TEN else isnull ( tennguoidaidien,TEN ) end  from  nhaphanphoi  where pk_seq= a.NPP_FK )  as nguoimua, 1 as innguoimua,   " + 
						"  		isnull(tongtienbvat,0) as tongtienbvat,  isnull(tongtienavat,0) as tongtienavat,  isnull(a.vat, 0) as vat, isnull(a.chietkhau, 0) as chietkhau, isnull(loaixuatHD,0) as loaixuatHD, '' as mavv, isnull(a.noibo, 0) as noibo  " + 
						"  from ERP_HOADON a  " + 
						"     inner join ERP_HOADON_DDH b on b.HOADON_FK = a.PK_SEQ " + 
						"     inner join ERP_DONDATHANG c on c.PK_SEQ = b.DDH_FK " +
						"   where a.pk_seq = '" + this.id + "'";
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
					this.nppId = rs.getString("npp_fk");
					this.loaiXHD = rs.getString("loaixuatHD");		
					this.nguoimua = rs.getString("nguoimua");	
					this.innguoimua = rs.getString("innguoimua");
					this.trangthai = rs.getString("trangthai");
					this.kbhId=rs.getString("kbhId") == null ? "" : rs.getString("kbhId");
					this.khoNhanId=rs.getString("kho_fk");
					this.mavuviec=rs.getString("mavv");
					this.ptchietkhau=rs.getString("ptchietkhau");
					this.bvat = formatter.format(rs.getDouble("tongtienbvat"));
					this.totalCHIETKHAU = formatter.format(rs.getDouble("chietkhau"));
					this.thueVAT = formatter.format(rs.getDouble("vat"));
					this.avat = formatter.format(rs.getDouble("tongtienavat"));
					this.noibo = rs.getString("noibo");
					this.phanloai=rs.getString("phanloai");
					this.ddhId="";
					this.loaidonhang = rs.getString("loaihoadon");
					//INIT DDH
					query = "SELECT HOADON_FK, DDH_FK FROM ERP_HOADON_DDH WHERE HOADON_FK = " + this.id;
					System.out.println("---LAY DDH: " + query );
					rs = this.db.get(query);
					if(rs!=null)
					{

						String _ddhId = "";
						while(rs.next())
						{
							_ddhId = _ddhId + rs.getString("DDH_FK") + ",";
						}

						if(_ddhId.trim().length() > 0)
						{
							_ddhId = _ddhId.substring(0, _ddhId.length() - 1);
							this.ddhId = _ddhId;
						}
					}

					if(this.trangthai.equals("3") || this.trangthai.equals("5") )
					{
						query = " select B.PK_SEQ ,B.NgayDonHang   " +
								" from ERP_HOADON_DDH A INNER JOIN ERP_DONDATHANG B ON A.DDH_FK = B.PK_SEQ " +
								" where A.HOADON_FK = '"+ this.id +"'  and B.kho_fk in " + util.quyen_kho(this.userId);
					}
					else
					{
						if(this.loaiXHD.equals("1") ) // KHACHHANG
						{			
							query = " select PK_SEQ , NgayDonHang  " +
									" from ERP_DONDATHANG " +
									" where trangthai = 4 and NPP_FK="+ this.nppId + 
									" and pk_seq not in  (select a.DDH_FK from  ERP_HOADON_DDH a inner join ERP_HOADON b on a.HOADON_FK=b.PK_SEQ where b.TRANGTHAI in (2,4) and b.pk_seq != " + this.id +")   " ;

						}
						else // DOITAC
						{			
							query = " select PK_SEQ , NgayDonHang  " +
									" from ERP_DONDATHANG " +
									" where trangthai = 4 and  NPP_FK="+ this.nppId +
									" and pk_seq not in(select a.DDH_FK from  ERP_HOADON_DDH a inner join ERP_HOADON b on a.HOADON_FK=b.PK_SEQ where b.TRANGTHAI =2 and b.pk_seq != " + this.id +")  " ;

						}
					}
					System.out.println("LAY DANH SACH DDH: " + query);		
					this.ddhRs = db.get(query);
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("---LOI INIT: " + e.getMessage());
			}
		}
		
		try
		{
			//INIT SOLO
			query = "select sp.pk_seq as sanpham_fk, solo, ngayhethan,ngaynhapkho, mathung, mame,maphieu, isnull( bin.ma, '' ) as vitri,marq, isnull( nsx.ma, '' ) as nsx, isnull(scheme,' ') scheme, sum(soluong) as soluong  " +
					"from erp_hoadon_sp_chitiet ct inner join erp_sanpham sp on ct.ma = sp.ma left join ERP_BIN bin on ct.bin_fk = bin.pk_seq left join erp_nhasanxuat nsx on ct.nsx_fk = nsx.pk_seq "
					+ " where hoadon_fk = '" + this.id + "' " +
					"group by sp.pk_seq, solo, ngayhethan, mathung, mame,maphieu, isnull( bin.ma, '' ), isnull(scheme,' '),marq, isnull( nsx.ma, '' ), ngaynhapkho ";
			
			System.out.println("---LO DA XUAT: " + query);
			ResultSet rsSOLO = db.get(query);
			this.sanpham_soluong = new Hashtable<String, String>();
			if(rsSOLO != null)
			{
				while(rsSOLO.next())
				{
					String key = rsSOLO.getString("sanpham_fk") + "__" + rsSOLO.getString("scheme") + "__" + rsSOLO.getString("solo").trim() + "__" + rsSOLO.getString("ngayhethan") + "__" + rsSOLO.getString("vitri") + "__" + rsSOLO.getString("mathung") + "__" + rsSOLO.getString("mame") + "__" + rsSOLO.getString("maphieu") + "__" + rsSOLO.getString("marq") + "__" + rsSOLO.getString("nsx")+ "__" + rsSOLO.getString("ngaynhapkho");
					this.sanpham_soluong.put(key, rsSOLO.getString("soluong"));
					
					System.out.println("::: KEY BEAN: " + key);
				}
				rsSOLO.close();
			}
		}
		catch (Exception e) 
		{	
			e.printStackTrace();
		}

		this.initTichLuy(this.id);
		this.createRs();
	}

	public void DBclose() {
		
		try{
			
			if(khoNhanRs!=null){
				khoNhanRs.close();
			}
			
			this.db.shutDown();
			
		}catch(Exception er){
			er.printStackTrace();
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

	public boolean create() 
	{
		if(this.ngayxuatHD.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày xuất hóa đơn";
			return false;
		}

		if(this.ngayghinhanCN.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày ghi nợ";
			return false;
		}
		
		if(this.ddhId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn đơn đặt hàng";
			return false;
		}
				
		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm  xuất hoá đơn";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spId.length; i++)
			{
				if( spSoluong[i].trim().length() > 0 )
				{
					if(Double.parseDouble(spSoluong[i].trim().replace(",", "")) > 0)
						coSP = true;
				}
			}
			
			if(!coSP)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm  xuất hoá đơn";
				return false;
			}	
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			// LAY KY HIEU HOA DON ,SOHDTU TRONG DLN
			String chuoi = "";
			long sohoadontu = 0;
			String sohoadonden = "";
			int kbDLN = 0;
			String mau = "1";

			String query =  " select  sohoadontu, sohoadonden " +
							" from NHANVIEN" +
							" where pk_seq= '"+ this.userId +"' and  isnull(kyhieu,'')= '"+ this.kyhieuhoadon +"' ";
			
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
			if (sohoadontu == 0 || sohoadonden.trim().length() <= 0 )
			{
				this.msg = "Ký hiệu "+ this.kyhieuhoadon +" khác với ký hiệu khai báo trong dữ liệu nền/ Chưa khai báo số hóa đơn trong dữ liệu nền (Số hóa đơn)";
				db.getConnection().rollback();
				return false;
			}
			
			// Check So hoa don sua da co dung chua
		   // OTC
			query= " select  count(pk_seq) as kiemtra " +
			       " from HOADON " +
			       " where nguoisua= '"+ this.userId +"' and RTRIM(LTRIM(sohoadon)) = '"+ this.sohoadon.trim() +"' and RTRIM(LTRIM(kyhieu)) = '"+ this.kyhieuhoadon +"' " +
			       "       and trangthai != '3' and pk_seq != "+ this.id +"  and npp_fk = " + this.nppId + "  and mauhoadon = 1 ";
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
			       " from ERP_HOADON " +
			       " where nguoisua= '"+ this.userId +"' and RTRIM(LTRIM(sohoadon)) = '"+ this.sohoadon.trim() +"' and RTRIM(LTRIM(kyhieu))='"+ this.kyhieuhoadon +"' " +
			       "       and trangthai != '3' and pk_seq != "+ this.id +"  and npp_fk = " + this.nppId + " and mauhoadon = 1  ";
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
			
			if (ktra > 0 || ktra_ETC > 0 )
			{
				this.msg = "Số hóa đơn này đã được sử dụng. Vui lòng đánh số hóa đơn khác. ";
				db.getConnection().rollback();
				return false;
			}
			else if(this.sohoadon.trim().length() != 7)
			{
				this.msg = "Số hóa đơn phải đủ 7 ký tự .Vui lòng kiểm tra lại! ";
				db.getConnection().rollback();
				return false;
			}
					
			String tbThongTin = " NHAPHANPHOI ";
			String thongtinId = this.nppId;
			String ddkd_fk ="(select ddkd_fk From Erp_DonDatHang where pk_Seq='"+this.ddhId+"') ";
			String khoId ="(select KHO_FK From Erp_DonDatHang where pk_Seq='"+ddhId+"') ";
			String kbhId ="(select kbh_fk From Erp_DonDatHang where pk_Seq='"+ddhId+"') ";
		 
			
			 query = 	" insert ERP_HOADON(KHO_FK,DDKD_FK,nguoimua, innguoimua,ngayxuatHD, trangthai, ngaytao, nguoitao, ngaysua, nguoisua, kyhieu, sohoadon, hinhthuctt ," +
				       " 	chietkhau, tongtienbvat, tongtienavat, vat, ghichu, loaixuathd, npp_fk, mauhoadon,TENKHACHHANG,DIACHI,MASOTHUE,mavv ) " +
					   " select "+khoId+","+ddkd_fk+",  N'"+ this.nguoimua +"' , "+ this.innguoimua +", '" + this.ngayxuatHD + "', '1','" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "', RTRIM(LTRIM('" + this.kyhieuhoadon + "')), " +
					   "       RTRIM(LTRIM('" + this.sohoadon + "')), N'"+ this.hinhthuctt +"' , '"+ this.totalCHIETKHAU.replaceAll(",", "")  +"', '"+ this.bvat.replaceAll(",", "") +"', '" + this.avat.replaceAll(",", "")  +"'," +
					   "       '"+ this.thueVAT.replaceAll(",", "") +"', N'"+ this.ghichu +"', '"+ this.loaiXHD +"', "+ this.nppId +", '"+ mau +"'" +
					   "		 , (select ten from "+tbThongTin+" where pk_Seq =" + thongtinId + " ) as nppMua " +
					   " 		, (select ISNULL(DIACHI,'') from "+tbThongTin+" where pk_Seq =" + thongtinId + " ) as diachinpp " +
					   " 		, (select ISNULL(MASOTHUE,'') from "+tbThongTin+" where pk_Seq =" + thongtinId + " ) as mst, '"+this.mavuviec+"'" ;
			 			
			System.out.println("1.Insert ERP_HOADON: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_HOADON " + query;
				db.getConnection().rollback();
				return false;
			}
			
			String hdId = "";
			query = "select SCOPE_IDENTITY() as hdId";
			ResultSet rs1 = db.get(query);
			rs1.next();
			hdId = rs1.getString("hdId");
			rs1.close();
			
			for(int i = 0; i < spId.length; i++)
			{
				if(spId[i].trim().length() > 0 && Double.parseDouble(spSoluong[i].trim().replaceAll(",", "")) > 0 )
				{
					double thanhtien = Double.parseDouble( spSoluong[i].replaceAll(",", "") ) * Double.parseDouble(  spDongia[i].replaceAll(",", "") );
					
					query = "insert ERP_HOADON_SP( hoadon_fk, sanpham_fk, donvitinh, soluong, dongia, thanhtien, chietkhau, scheme , vat) " +
							" values( "+ hdId +", '" + spId[i] + "', N'"+ spDonvi[i] +"', '" + spSoluong[i].replaceAll(",", "") + "', '" + spDongia[i].replaceAll(",", "") + "'," +
									" "+ thanhtien +", '"+ spChietkhau[i].replaceAll(",", "") +"', N'"+ spSCheme[i].replaceAll(",", "") +"', '"+ spVat[i].replaceAll(",", "") +"' ) ";
					
					System.out.println("1.1.Insert ERP_HOADON_SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_HOADON_SP: " + query;
						db.getConnection().rollback();
						return false;
					}
			
				}
			}
			
				query=
				"		update C set SoLuong_Chuan=  "+
				"				case     when c.donvitinh = e.donvi then c.soluong  "+     
				"								  else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  ,  "+ 
				"		DonGia_Chuan=	case     when c.donvitinh = e.donvi then c.dongia      "+
				"							  else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from		DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  , "+
				"			DonVi_Chuan=e.DONVI  "+
				"		from ERP_HOADON a         "+
				"			left join KHACHHANG KH on a.KHACHHANG_FK = KH.PK_SEQ      "+
				"			inner join ERP_HOADON_SP c on a.pk_seq = c.hoadon_fk  "+ 
				"			inner join ERP_SANPHAM d on c.sanpham_fk = d.pk_seq    "+
				"			inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq  "+      
				"		where a.pk_Seq='"+hdId+"' ";
				System.out.println("1.1.UPDATE ERP_HOADON: " + query);
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới ERP_HOADON_SP " + query;
					db.getConnection().rollback();
					return false;
				}
			
			
			query = "Insert ERP_HOADON_DDH(hoadon_fk, ddh_fk) select " + hdId + ", pk_seq from ERP_DONDATHANG where pk_seq in ( " + this.ddhId + " )    ";
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_HOADON_DDH " + query;
				db.getConnection().rollback();
				return false;
			}
					
			query = "Update ERP_YCXUATKHO set NGAYYEUCAU = '" + this.ngayxuatHD + "' " +
					"where PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHO_DDH where ddh_fk in ( " + this.ddhId + " ) )";
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_YCXUATKHO " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			db.execProceduce2("CapNhatTooltip_HoaDon", new String[]{ this.id });
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
		//NEU HOA DON DA HUY THI CHI DUOC SUA KY HIEU VA SO HOA DON
		String sqlCHECK = "select isnull(a.noibo,0) as noibo,a.trangthai, b.loaiNPP from ERP_HOADON a inner join NHAPHANPHOI b on a.npp_fk = b.pk_seq " +
						  "where a.pk_seq = '" + this.id + "'";
		System.out.println("Lấy trạng thái "+sqlCHECK);
		ResultSet rsCHECK = db.get(sqlCHECK);
		String loaiNPP = "";
		int noibo=0;
		try 
		{
			if(rsCHECK.next())
			{
				this.trangthai = rsCHECK.getString("trangthai");
				loaiNPP = rsCHECK.getString("loaiNPP");
				noibo=rsCHECK.getInt("noibo");
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
		if(trangthai.equals("4"))
		{
			this.msg = "Hóa đơn đã in bạn không được chỉnh sửa trên hóa đơn này !!! ";
			return false;
		}
		else if(trangthai.equals("1")  )
		{
			if(this.ddhId.length() <= 0)
			{
				this.msg = "Vui lòng chọn đơn đặt hàng";
				return false;
			}

			if(spMa == null)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm  xuất hoá đơn tài chính";
				return false;
			}
			else
			{
				boolean coSP = false;
				System.out.println("sp id lengh"+spId.length);
				for(int i = 0; i < spId.length; i++)
				{
					System.out.println("SPla "+spId[i] +"");
					if( spSoluong[i].trim().length() > 0 )
					{
						if(Double.parseDouble(spSoluong[i].trim().replaceAll(",", "")) > 0)
							coSP = true;
					}
				}

				if(!coSP)
				{
					this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm  xuất hoá đơn tài chính";
					return false;
				}	
			}
			if(  (this.sanpham_soluong == null || this.sanpham_soluong.size() <= 0) )
			{
				this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm chi tiết xuất hoá đơn tài chính";
				return false;
			}

		}
		
		

		try
		{
			db.getConnection().setAutoCommit(false);

			// LAY KY HIEU HOA DON ,SOHDTU TRONG DLN
			String chuoi="";
			long sohoadontu=0;
			String sohoadonden="";
			int kbDLN=0;
			String mau = "1";

			String query =" select isnull(SOHOADONTU,'') as SOHOADONTU,isnull(SOHOADONDEN,'') as SOHOADONDEN,isnull(SOHOADONTU2,'') as SOHOADONTU2,isnull(SOHOADONDEN2,'') as SOHOADONDEN2 from NHANVIEN where pk_seq="+userId;
			System.out.println("AAAAA:"+ query);
			ResultSet mauHDrs = db.get(query);

			String SOHOADONTU1="";
			String SOHOADONDEN1="";
			String SOHOADONTU2="";
			String SOHOADONDEN2="";

			if(mauHDrs!=null)
			{
				while(mauHDrs.next())
				{
					SOHOADONTU1 = mauHDrs.getString("SOHOADONTU");
					SOHOADONDEN1 = mauHDrs.getString("SOHOADONDEN");
					SOHOADONTU2 = mauHDrs.getString("SOHOADONTU2");
					SOHOADONDEN2 = mauHDrs.getString("SOHOADONDEN2");

				}
				mauHDrs.close();
			}
			if(!loaiNPP.equals("4"))
			{
				String query_kyhieu = " KYHIEU ";				
				String query_sohdTU = " SOHOADONTU ";	
				String query_sohdDEN = " SOHOADONDEN ";	
				String query_mauhd = "1";
				String query_ngayhd = " NGAYHOADON  ";


				if(SOHOADONDEN1.trim().length()>0 && SOHOADONTU1.trim().length()>0)
				{
					query_kyhieu = " KYHIEU ";				
					query_sohdTU = " SOHOADONTU ";	
					query_sohdDEN = " SOHOADONDEN ";	
					query_mauhd = "1";
					query_ngayhd = " NGAYHOADON  ";
				}
				if(SOHOADONDEN2.trim().length()>0 && SOHOADONTU2.trim().length()>0)
				{
					query_kyhieu = " KYHIEU2 ";				
					query_sohdTU = " SOHOADONTU2 ";	
					query_sohdDEN = " SOHOADONDEN2 ";				
					query_mauhd = "2";
					query_ngayhd = " NGAYHOADON2 ";
				}

				mau = query_mauhd;
				query=   " select  "+query_sohdTU+" as sohoadontu , "+query_sohdDEN+" as sohoadonden  " +
						" from NHANVIEN" +
						" where pk_seq= '"+ this.userId +"' and isnull("+query_kyhieu+", '') = '"+ this.kyhieuhoadon +"' ";
				System.out.println("Câu query: "+query);
				ResultSet rsLayDL =  db.get(query);
				if(rsLayDL != null)
				{
					while(rsLayDL.next())
					{
						System.out.println ("---sdas-----------"+query_sohdTU+", "+query_sohdDEN+"");
						sohoadontu = rsLayDL.getLong("sohoadontu");
						sohoadonden = rsLayDL.getString("sohoadonden");
					}
					rsLayDL.close();
				}

				if(sohoadontu == 0 || sohoadonden.trim().length() <= 0)
				{
					this.msg = " Ký hiệu hóa đơn vừa sửa không giống với ký hiệu khai báo trong dữ liệu nền/ Chưa khai báo số hóa đơn trong dữ liệu nền (Số hóa đơn) ";
					db.getConnection().rollback();
					return false;
				}

				// ETC
				query= " select  count(pk_seq) as kiemtra " +
						" from ERP_HOADON " +
						" where nguoisua= '"+ this.userId +"' and RTRIM(LTRIM(sohoadon)) = '"+ this.sohoadon.trim() +"' and RTRIM(LTRIM(kyhieu)) ='"+ this.kyhieuhoadon +"' " +
						"       and trangthai != 3  and pk_seq != "+ this.id +"  and mauhoadon ="+mau+" ";
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


				if (ktra_ETC > 0 )
				{
					this.msg = "Số hóa đơn này đã được sử dụng. Vui lòng đánh số hóa đơn khác. ";
					db.getConnection().rollback();
					return false;
				}
				else if (this.sohoadon.trim().length() != 7 )
				{
					this.msg = "Số hóa đơn phải đủ 7 ký tự . Vui lòng kiểm tra lại. ";
					db.getConnection().rollback();
					return false;
				}
			}

			query = "";
			if(!trangthai.equals("1") )  //NEU DA DUYET THI CHI DUOC SUA CAC THONG NAY
			{
				
				query=" UPDATE ERP_PHATSINHKETOAN SET MACHUNGTU = RTRIM(LTRIM('" + this.sohoadon + "')) WHERE SOCHUNGTU="+this.id+" AND (LOAICHUNGTU=N'Hóa đơn tài chính' OR LOAICHUNGTU=N'Hóa đơn nội bộ') AND (LOAIHD=N'HDHO_NB' OR LOAIHD=N'HDHO') ";
				if(!db.update(query))
				{
					this.msg = "Khong the tao moi update ERP_HOADON_SP: " + query;
					db.getConnection().rollback();
					return false;
				}
				query = " update ERP_HOADON set  ngaysua = '" + getDateTime() + "' , nguoisua ='" + this.userId + "'," +
						" kyhieu = RTRIM(LTRIM('" + this.kyhieuhoadon + "')) , sohoadon= RTRIM(LTRIM('" + this.sohoadon + "')) " +
						" where pk_seq = '"+ this.id +"' and TrangThai!=1 " ;
				System.out.println("1.Update ERP_HOADON1: " + query);
				if(db.updateReturnInt(query)!=1)
				{
					this.msg = "Hóa đơn đã chốt,vui lòng kiểm tra lại "+query;
					db.getConnection().rollback();
					return false;
				}

				/*for(int i = 0; i < spId.length; i++)
				{
					if(spId[i].trim().length() > 0 && Double.parseDouble(spSoluong[i].trim().replaceAll(",", "")) > 0 )
					{	
						query = " update ERP_HOADON_SP set  tensp = N'" + spTen[i].replaceAll(",", "") + "' "+
								" where hoadon_fk = '"+ this.id +"' and sanpham_fk='"+spId[i]+"'" ;

						System.out.println("1.1.update ERP_HOADON_SP: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi update ERP_HOADON_SP: " + query;
							db.getConnection().rollback();
							return false;
						}
					}
				}*/
				
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
				return true ;
			}
			else
			{
				query = " update ERP_HOADON set innguoimua= "+ this.innguoimua +" , nguoimua = N'"+ this.nguoimua +"' , dondathang_fk = "+ this.ddhId + ", ngayxuatHD = '" + this.ngayxuatHD + "' , ngaysua = '" + getDateTime() + "' , nguoisua ='" + this.userId + "'," +
						" kyhieu = RTRIM(LTRIM('" + this.kyhieuhoadon + "')) , sohoadon= RTRIM(LTRIM('" + this.sohoadon + "')), hinhthuctt= N'"+ this.hinhthuctt +"' ," +
						" chietkhau =  '"+ this.totalCHIETKHAU.replaceAll(",", "")  +"' , tongtienbvat= '"+ this.bvat.replaceAll(",", "") +"', tongtienavat='" + this.avat.replaceAll(",", "") + "', vat = '"+ this.thueVAT.replaceAll(",", "") + "', ghichu= N'"+ this.ghichu +"'," +
						" loaixuathd= '"+ this.loaiXHD +"' , npp_fk = "+ this.nppId +" ,mavv='" +this.mavuviec+"'"+
						" where pk_seq = '"+ this.id +"'" ;

				System.out.println("1.Update ERP_HOADON2: " + query);
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật ERP_HOADON " + query;
					db.getConnection().rollback();
					return false;
				}

				Utility util = new Utility();
				msg= util.Check_Huy_NghiepVu_KhoaSo(db,"ERP_HOADON", id, "ngayxuatHD");
				if(msg.length()>0)
				{
					db.getConnection().rollback();
					return false;
				}

				/*query = "delete from ERP_HOADON_SP where hoadon_fk = '"+ this.id +"' " ;
				if(!db.update(query))
				{
					this.msg = "Không thể xóa HOADON_SP " + query;
					db.getConnection().rollback();
					return false;
				}

				query = "delete from ERP_HOADON_DDH where hoadon_fk = '"+ this.id +"' " ;
				if(!db.update(query))
				{
					this.msg = "Không thể xóa ERP_HOADON_DDH " + query;
					db.getConnection().rollback();
					return false;
				}*/

				/*for(int i = 0; i < spId.length; i++)
				{
					if(spId[i].trim().length() > 0 && Double.parseDouble(spSoluong[i].trim().replaceAll(",", "")) > 0 )
					{
						double thanhtien = Double.parseDouble( spSoluong[i].replaceAll(",", "") ) * Double.parseDouble(  spDongia[i].replaceAll(",", "") );

						query = "insert ERP_HOADON_SP( hoadon_fk, sanpham_fk, donvitinh, soluong, dongia, thanhtien, chietkhau, scheme, vat, TIENVAT,tensp,IS_KHONGTHUE ) values " +
								" ('"+ this.id +"', '" + spId[i] + "', N'"+ spDonvi[i] +"', '" + spSoluong[i].replaceAll(",", "") + "', '" + spDongia[i].replaceAll(",", "") + "'," +
								" "+ thanhtien +", '"+ spChietkhau[i].replaceAll(",", "") +"', N'"+ spSCheme[i].replaceAll(",", "") +"','"+ spVat[i].replaceAll(",", "") +"', '" + spTienthue[i].replaceAll(",", "") + "',N'" + spTen[i].replaceAll(",", "") + "','"+this.spIskhongthue[i]+"' )  ";

						System.out.println("1.1.Insert ERP_HOADON_SP: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_HOADON_SP: " + query;
							db.getConnection().rollback();
							return false;
						}
					}
				}*/

				//Tăng kho ngược lại trước khi xóa - trường hợp đổi số lô
				/*query =  "  select c.NPP_FK, a.Kho_FK, b.PK_SEQ as sanpham_fk, a.SOLO, a.NGAYHETHAN, a.NGAYNHAPKHO  " + 
						 "  from ERP_HOADON_SP_ChiTiet a inner join SANPHAM b on a.MA = b.MA  " + 
						 "  		inner join ERP_HOADON c on a.hoadon_fk = c.PK_SEQ " + 
						 "  where hoadon_fk = '" + this.id + "' ";*/
				
				/*String loaichungtu = "Hóa đơn tài chính"; 
				String chungtuId = this.id; 
				String transactionId = util.createTransactionId();
				
				query = "  select b.ngayxuatHD, b.Kho_FK, sp.PK_SEQ as SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, " + 
						" 		a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, a.phieudt, a.phieueo, SUM( a.SoLuong_CHUAN ) as soluong  " + 
						"  from ERP_HOADON_SP_ChiTiet a inner join ERP_HOADON b on a.hoadon_fk = b.PK_SEQ " + 
						"		inner join ERP_SANPHAM sp on a.MA = sp.MA 	" +
						"  where b.PK_SEQ = '" + this.id + "' " + 
						"  group by b.ngayxuatHD, b.Kho_FK, sp.PK_SEQ, a.SoLo, a.NgayHetHan, a.ngaynhapkho, a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, a.bin_fk, a.phieudt, a.phieueo ";
				
				System.out.println("::: CAP NHAT KHO TRUOC KHI XOA: " + query);
				ResultSet rs = db.get(query);
				if( rs != null )
				{
					while( rs.next() )
					{
						this.khoNhanId = rs.getString("Kho_FK");
						String khoId = rs.getString("Kho_FK");
						String spId = rs.getString("SanPham_fk");
						String solo = rs.getString("SoLo");
						String ngayhethan = rs.getString("NgayHetHan");
						String ngaynhapkho = rs.getString("ngaynhapkho");
						
						String loaidoituong = "";
						String doituongId = "";
						
						String mame = rs.getString("mame");
						String mathung = rs.getString("mathung");
						String bin_fk = rs.getString("bin_fk");
						
						String maphieu = rs.getString("maphieu");
						String phieudt = rs.getString("phieudt");
						String phieueo = rs.getString("phieueo");
						
						String marq = rs.getString("marq");
						String hamluong = rs.getString("hamluong");
						String hamam = rs.getString("hamam");

						double soluong = rs.getDouble("soluong");
						
						msg = util.Update_KhoTT(rs.getString("ngayxuatHD"), "Cập nhật HD - Tăng kho ngược lại trước khi xóa ", db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
								mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, 0, -1 * soluong, soluong, 
								loaichungtu, chungtuId, transactionId	);
						if( msg.trim().length() > 0 )
						{

							db.getConnection().rollback();
							return false;
						}
					}
					rs.close();
				}
				
				// cập nhật lại kho chi tiết 
				query = "delete from ERP_HOADON_SP_ChiTiet where hoadon_fk="+this.id+"";
				if(db.updateReturnInt(query)<=0)
				{
					msg = "Khong the cap nhat ERP_HOADON_SP_ChiTiet: " + query;
					db.getConnection().rollback();
					return false;
				}

				query = "		update C set SoLuong_Chuan=  "+
						"				case     when c.donvitinh = e.donvi then c.soluong  "+     
						"								  else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  ,  "+ 
						"		DonGia_Chuan=	case     when c.donvitinh = e.donvi then c.dongia      "+
						"							  else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from		DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK )   end  , "+
						"			DonVi_Chuan=e.DONVI  "+
						"		from ERP_HOADON a         "+
						"			inner join ERP_HOADON_SP c on a.pk_seq = c.hoadon_fk  "+ 
						"			inner join ERP_SANPHAM d on c.sanpham_fk = d.pk_seq    "+
						"			inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq  "+      
						"		where a.pk_Seq='"+this.id+"' ";

				System.out.println("1.1.UPDATE ERP_HOADON_SP: " + query);
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới ERP_HOADON_SP " + query;
					db.getConnection().rollback();
					return false;
				}	

				query = "Insert ERP_HOADON_DDH(hoadon_fk, ddh_fk) select '"+ this.id +"', pk_seq from ERP_DONDATHANG where pk_seq in ( " + this.ddhId + " )  ";
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới ERP_HOADON_DDH " + query;
					db.getConnection().rollback();
					return false;
				}*/

				/*query = "Update ERP_YCXUATKHO set NGAYYEUCAU = '" + this.ngayxuatHD + "' " +
						"where PK_SEQ in ( select ycxk_fk from ERP_YCXUATKHO_DDH where ddh_fk in ( " + this.ddhId + " ) )";
				if(!db.update(query))
				{
					this.msg = "Không thể tạo mới ERP_YCXUATKHO " + query;
					db.getConnection().rollback();
					return false;
				}*/

				/*query = "delete ERP_DONDATHANG_SANPHAM_CHITIET where dondathang_fk in  (" + this.ddhId + ") ";
				if(!db.update(query))
				{
					this.msg = "Lỗi khi duyệt: " + query;
					db.getConnection().rollback();
					return false;
				}

				//LUU VAO BANG CHI TIET
				for(int i = 0; i < spMa.length; i++)
				{
					if(spMa[i].trim().length() > 0 && spSoluong[i].trim().length() > 0 )
					{
						if(this.sanpham_soluong != null)
						{
							Enumeration<String> keys = this.sanpham_soluong.keys();
							double totalCT = 0;

							while(keys.hasMoreElements())
							{
								String key = keys.nextElement();

								if(key.startsWith( spMa[i]) )
								{
									String[] _sp = this.mySplit( key, "__" );

									String _soluongCT = "0"; 
									if(this.sanpham_soluong.get(key) != null)
									{
										_soluongCT = this.sanpham_soluong.get(key).replaceAll(",", "");
									}

									if(!_soluongCT.equals("0"))
									{
										//QUY VỀ SỐ LƯỢNG CHUẨN LƯU KHO
										query = " select '" + _soluongCT.replaceAll(",", "") + "' * dbo.LayQuyCach( a.pk_seq, null, ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ) ) as soluongCHUAN, " + 
												"	dbo.LayQuyCach_DVBan( a.pk_seq, null, ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ) ) as quyveDVBAN	" +
												" from ERP_SANPHAM a where ma = '" + spMa[i] + "' ";
										
										double soluongCHUAN = Double.parseDouble(_soluongCT.replaceAll(",", ""));
										double quyveDVBAN = 0;
										rs = db.get(query);
										if(rs != null )
										{
											if( rs.next() )
											{
												soluongCHUAN = rs.getDouble("soluongCHUAN");
												quyveDVBAN = rs.getDouble("quyveDVBAN");
											}
											rs.close();
										}
										
										//Bên đầu bán, lúc nhập chỉ có số lô, ngày hết hạn, vị trí, các thông số khác sẽ đề xuất chỗ này
										List<ErpThongtinkho> chitiet = util.DeXuatKho(db, this.ngayxuatHD, this.nppId, this.khoNhanId, spMa[i], _sp[1],  _sp[2], _sp[3], _sp[4], _sp[5],_sp[6], soluongCHUAN );
										if( chitiet == null )
										{

											this.msg = "Lỗi đề xuất sản phẩm, vui lòng liên hệ admin để xử lý.";
											db.getConnection().rollback();
											return false;
										}
										
										totalCT += Double.parseDouble(_soluongCT);
										
										for( int j = 0; j < chitiet.size(); j++  )
										{
											ErpThongtinkho tt = chitiet.get(j);
	
											query = "insert ERP_DONDATHANG_SANPHAM_CHITIET( dondathang_fk, SANPHAM_FK, scheme, solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, MAPHIEU, MARQ, HAMLUONG, HAMAM, bin_fk, phieudt, phieueo, soluong, soluongCHUAN ,IS_KHONGTHUE ,TIENHANG,TIENVAT,TIENHANGSAUVAT) " +
													"select '" + ddhId + "', '" + tt.spId + "', N'', N'" + tt.solo + "', '" + tt.ngayhethan + "', '" + tt.ngaynhapkho + "', '" + tt.mame + "', '" + tt.mathung + "', N'" + tt.maphieu + "', '" + tt.MARQ + "', '" + tt.hamluong + "', '" + tt.hamam + "', '" + tt.vitriId + "', '" + tt.phieudt + "', '" + tt.phieueo + "', ( " + quyveDVBAN + " * " + tt.soluong + " ), '" + tt.soluong + "','"+this.spIskhongthue[i]+"' "+
													" ,ROUND(("+ tt.soluong+" * (SELECT DISTINCT DONGIA FROM ERP_DONDATHANG_SANPHAM WHERE dondathang_fk = " + ddhId + " AND SANPHAM_FK = " + tt.spId + ")),0) AS TIENHANG, ROUND(((ROUND(("+ tt.soluong+" * (SELECT DISTINCT DONGIA FROM ERP_DONDATHANG_SANPHAM WHERE dondathang_fk = " + ddhId + " AND SANPHAM_FK = " + tt.spId + ")),0))*(SELECT DISTINCT thueVAT FROM ERP_DONDATHANG_SANPHAM WHERE dondathang_fk = " + ddhId + " AND SANPHAM_FK = " + tt.spId + ")/100),0) AS TIENVAT"+
													", (ROUND(("+ tt.soluong+" * (SELECT DISTINCT DONGIA FROM ERP_DONDATHANG_SANPHAM WHERE dondathang_fk = " + ddhId + " AND SANPHAM_FK = " + tt.spId + ")),0) +ROUND(((ROUND(("+ tt.soluong+" * (SELECT DISTINCT DONGIA FROM ERP_DONDATHANG_SANPHAM WHERE dondathang_fk = " + ddhId + " AND SANPHAM_FK = " + tt.spId + ")),0))*(SELECT DISTINCT thueVAT FROM ERP_DONDATHANG_SANPHAM WHERE dondathang_fk = " + ddhId + " AND SANPHAM_FK = " + tt.spId + ")/100),0) )AS TIENHANGSAUVAT  ";
									
											
											System.out.println("1.2.Insert ERP_DONDATHANG_SANPHAM_CHITIET: " + query);
											if(!db.update(query))
											{
											
												this.msg = "Khong the tao moi ERP_DONDATHANG_SANPHAM_CHITIET: " + query;
												db.getConnection().rollback();
												return false;
											}
											
											//CẬP NHẬT KHO
											//this.msg = util.Update_KhoTT(this.ngaydenghi, "HO / Bán đối tác", db, this.khoNhanId, "( select pk_seq from SANPHAM where ma = '" + spMa[i] + "' )", _sp[2], _sp[3], _sp[4], _sp[5], _sp[6], 0, Double.parseDouble(_soluongCT.replaceAll(",", "")), -1 * Double.parseDouble(_soluongCT.replaceAll(",", "")));
											this.msg = util.Update_KhoTT(this.ngayxuatHD, "HO / Bán đối tác", db, tt.khoId, tt.spId, tt.solo, tt.ngayhethan, tt.ngaynhapkho, 
															tt.mame, tt.mathung, tt.vitriId, tt.maphieu, tt.phieudt, tt.phieueo, tt.MARQ, tt.hamluong, tt.hamam, "", "", 0, tt.soluong, -1 * tt.soluong, 
															loaichungtu, chungtuId, transactionId	);
											if( this.msg.trim().length() > 0 )
											{

												db.getConnection().rollback();
												return false;
											}
										}	
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

				//CHECK TONG KHO CHI TIET PHAI BANG TONG TRONG KHO TONG
				query = "select count(*) as soDONG   " +
						"from ERP_DONDATHANG_SANPHAM tong left join   " +
						"	(  " +
						"		select sanpham_fk, sum(soluong) as soluong   " +
						"		from ERP_DONDATHANG_SANPHAM_CHITIET  " +
						"		where  dondathang_fk = '" + this.ddhId + "'  " +
						"		group by sanpham_fk " +
						"	)  " +
						"	CT on tong.sanpham_fk = CT.sanpham_fk " +
						"where dondathang_fk = '" + this.ddhId + "' and tong.soluong != isnull(CT.soluong, 0)  " ;

				System.out.println("[CheckTong_CT]"+query);

				rsCHECK = db.get(query);
				int soDONG = 0;
				{
					if( rsCHECK.next() )
					{
						soDONG = rsCHECK.getInt("soDONG");
					}
					rsCHECK.close();
				}

				if(soDONG > 0)
				{

					db.getConnection().rollback();
					this.msg = "11.Lỗi hệ thống ( tổng xuất theo lô đề xuất đang bị sai ). Vui lòng liên hệ trung tâm để được hỗ trợ xử lý."+query;
					return false;
				}*/

				/*query = "  insert ERP_HOADON_SP_CHITIET(hoadon_fk, donhang_fk, KBH_FK, Kho_FK, MA, TEN, DONVI, DVCHUAN, DVDATHANG, SOLUONG, SOLO, NGAYHETHAN, NGAYNHAPKHO, CHIETKHAU, THUEVAT, DONGIA,SoLuong_Chuan,DonGia_Chuan,SoLuong_DatHang)   " + 
						"  select '" + this.id + "' as hoadon_fk, a.pk_seq as donhang_fk, a.KBH_FK, a.KHO_FK, c.MA, isnull(dhsp.sanphamTEN ,c.TEN ) as TEN, (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,    " + 
						"  	b.soluong,    " + 
						"  	b.solo, b.NGAYHETHAN, NGAYNHAPKHO, dhsp.chietkhau, dhsp.thuevat,   " + 
						"  	dhsp.dongia,   " + 
						"  	b.soluong * dbo.LayQuyCach( b.SANPHAM_FK, null, b.DVDL_FK ) as SoLuong_Chuan,   " + 
						"  	dhsp.dongia * dbo.LayQuyCach_DVBan( b.SANPHAM_FK, null, b.DVDL_FK ) as DonGia_Chuan,   " + 
						"  	dhsp.soluong as SoLuong_DatHang   " + 
						"  from ERP_DONDATHANG a inner join ERP_DONDATHANG_SANPHAM_CHITIET b on a.pk_seq = b.dondathang_fk	  								   " + 
						"       inner join ERP_DONDATHANG_SANPHAM dhsp on dhsp.dondathang_fk = a.PK_SEQ and dhsp.sanpham_fk = b.sanpham_fk	   " + 
						"       inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  						   " + 
						"       inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	   " + 
						"  where a.trangthai != '3' and a.PK_SEQ = '" + this.ddhId + "' and b.soluong > 0  ";*/
				
				/*query = "  insert ERP_HOADON_SP_CHITIET(hoadon_fk, donhang_fk, KBH_FK, Kho_FK, MA, TEN, DONVI, DVCHUAN, DVDATHANG, SOLUONG, SoLuong_Chuan, SoLuong_DatHang, CHIETKHAU, THUEVAT, DONGIA, DonGia_Chuan, SOLO, NGAYHETHAN, NGAYNHAPKHO, MAME, MATHUNG, MAPHIEU, MARQ, HAMLUONG, HAMAM, bin_fk, phieudt, phieueo ,IS_KHONGTHUE,TIENHANG,TIENVAT,TIENHANGSAUVAT) " + 
						"  select '" + this.id + "', a.DonDatHang_FK, c.KBH_FK, c.Kho_FK, b.MA, b.TEN, d.DONVI, b.DVDL_FK as dvChuan, a.Dvdl_Fk as dvDathang,  " + 
						"  			a.SoLuong, a.SoLuong * dbo.LayQuyCach(a.sanpham_fk, null, e.dvdl_fk) as soluongChuan, a.SoLuong as SoLuong_DatHang,  " + 
						"  			e.chietkhau as chietkhau, e.thueVAT as THUEVAT, e.dongia as DONGIA, e.dongia * dbo.LayQuyCach_DVBan( a.SanPham_fk, null, e.Dvdl_Fk ) as DonGia_Chuan, " + 
						"			a.SoLo, a.NgayHetHan, a.ngaynhapkho, MAME, MATHUNG, MAPHIEU, MARQ, HAMLUONG, HAMAM, bin_fk, phieudt, phieueo,isnull(a.IS_KHONGTHUE,0) as IS_KHONGTHUE,ISNULL(a.TIENHANG,0) AS TIENHANG,ISNULL(a.TIENVAT,0) AS TIENVAT,ISNULL(a.TIENHANGSAUVAT,0) AS 	TIENHANGSAUVAT		" +
						"  from ERP_DONDATHANG_SANPHAM_CHITIET a inner join ERP_SANPHAM b on a.SanPham_fk = b.PK_SEQ " + 
						"  		inner join ERP_DONDATHANG c on a.DonDatHang_FK = c.PK_SEQ " + 
						"  		left join DONVIDOLUONG d on a.Dvdl_Fk = d.PK_SEQ " + 
						"  		inner join ERP_DONDATHANG_SANPHAM e on a.dondathang_fk = e.dondathang_fk and a.sanpham_fk = e.sanpham_fk " + 
						"  where a.DonDatHang_FK = '" + ddhId + "' and a.soluong > 0 ";

				if(db.updateReturnInt(query)<=0)
				{

					msg = "Khong the cap nhat ERP_HOADON_SP_CHITIET: " + query;
					rs.close();
					db.getConnection().rollback();
					return false;
				}*/						
			}				

			/*Utility util = new Utility();
			msg = util.Check_Kho_Tong_VS_KhoCT(util.getIdNhapp(userId), db);
			if(msg.length()>0)
			{
				db.getConnection().rollback();
				return false;
			}*/
			
			/*query=" update b set b.thuesuat=f.thueVAT  "+
				  "	 from erp_hoadon a inner join ERP_HOADON_SP b "+
				  "	on a.PK_SEQ=b.HOADON_FK inner join ERP_HOADON_DDH c "+
				  "	on c.HOADON_FK=a.PK_SEQ inner join ERP_DONDATHANG d "+
				  "	on d.PK_SEQ=c.DDH_FK inner join ERP_DONDATHANG_SANPHAM f "+
				  "	on f.dondathang_fk=d.PK_SEQ and f.sanpham_fk=b.SANPHAM_FK "+
				  "	where a.pk_seq= "+this.id;
				  if(db.updateReturnInt(query)<=0)
					{
						//
						msg = "Khong the cap nhat ERP_HOADON_SP_CHITIET: " + query;
						db.getConnection().rollback();
						return false;
					}	*/	
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			/*if(noibo==1)
			{
				db.execProceduce2("CapNhatTooltip_HoaDon_noibo", new String[]{ this.id  });
			}
			else
			{
				db.execProceduce2("CapNhatTooltip_HoaDon", new String[]{ this.id });
			}*/
			
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			this.msg = "Exception: " + e.getMessage();
			e.printStackTrace();
			return false;
		}

		this.msg = "Số hóa đơn bạn vừa lưu: " + this.sohoadon;
		return true;
	}
	
	private String[] mySplit(String key, String operator) 
	{
		boolean exit = false;
		if( key.endsWith(operator) )
		{
			key = key + " ";
			exit = true;
		}
		
		String[] arr = key.split(operator);
		if( arr.length > 0 && exit )
			arr[ arr.length - 1 ] = arr[ arr.length - 1 ].trim();
		
		return arr;
	}

	public boolean chot_update()
	{
		try
		{
			boolean flag = this.update();
			if( flag == false )
				return flag;
			
			flag = this.chot(this.id, this.userId );
			if( flag == false )
				return flag;
			
			
			
			//
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return true;
		
	}

	public boolean chot(String hdId, String userId) 
	{
		Utility util = new Utility();
		/*msg = util.Check_Huy_NghiepVu_KhoaSo("ERP_HOADON", hdId, "ngayxuatHD", db);
		if(msg.length()>0)
		{
			db.shutDown();
			return false;
		}*/

		try
		{
			db.getConnection().setAutoCommit(false);

			//CHECK KHOA SO THANG

			String query = "update ERP_HOADON set trangthai = '2', NgaySua='"+getDateTime()+"', thoidiemCHOT = getdate()  where pk_seq = '" + hdId + "' and trangthai in (0,1)  ";
			System.out.println("::: CHOT HOA DON: " + query);
			if(db.updateReturnInt(query)!=1)
			{
				msg = "Hóa đơn đã chuyển trạng thái ,vui lòng xem lại ";
				db.getConnection().rollback();
				return false;
			}

			 query = " select a.npp_fk, a.noibo, a.loaihoadon, dh.isKM  " +
					" from ERP_HOADON a " +
					" inner join ERP_HOADON_DDH ddh on ddh.HOADON_FK=a.PK_SEQ "+
					" inner join ERP_DONDATHANG dh on dh.PK_SEQ=ddh.DDH_FK "+
					" where a.pk_seq = "+hdId;

			ResultSet rs=db.get(query);
			String npp="";
			int noibo = 0;
			int isKM = 0;

			if(rs.next())
			{
				npp = rs.getString("npp_fk");
				noibo = rs.getInt("NOIBO");
				isKM = rs.getInt("isKM");
			}
			rs.close();

			//CHECK BANG TONG PHAI BANG BANG CHI TIET


			//CẬP NHẬT TIỀN
			db.execProceduce2("CapNhatTooltip_HoaDon", new String[]{ hdId });

			if(noibo == 0 && isKM == 0) // HÓA ĐƠN TÀI CHINH
			{
				query = "select count(*) as sodong  " +
						"from " +
						"( " +
						"	select b.pk_seq as sanpham_fk, sum(soluong) as soluong  " +
						"	from ERP_HOADON_SP a inner join ERP_SANPHAM b on a.sanpham_fk = b.pk_seq " +
						"	where a.hoadon_fk = '" + hdId + "' " +
						"	group by b.pk_seq " +
						") " +
						"dh left join " +
						"( " +
						"	select b.pk_seq as sanpham_fk, sum(soluong) as soluong  " +
						"	from ERP_HOADON_SP_CHITIET a inner join ERP_SANPHAM b on a.MA = b.MA " +
						"	where a.hoadon_fk = '" + hdId + "' " +
						"	group by b.pk_seq " +
						") " +
						"xk on dh.sanpham_fk = xk.sanpham_fk " +
						"where dh.soluong != isnull(xk.soluong, 0) ";
				System.out.println("---CHECK HOA DON: " + query);
				int soDONG = 0;
				ResultSet rsCHECK = db.get(query);
				if(rsCHECK != null)
				{
					if(rsCHECK.next())
					{
						soDONG = rsCHECK.getInt("sodong");
					}
					rsCHECK.close();
				}

				if(soDONG > 0)
				{
					msg = "3.Số lượng trong đơn hàng không khớp với hóa đơn. Vui lòng liên hệ Admin để xử lý ";
					db.getConnection().rollback();
					return false;
				}
				
				msg = GhinhanKT(hdId);
				if(msg.trim().length() > 0)
				{
					db.getConnection().rollback();
					return false;
				}
			}

			if(noibo == 1 ) // HÓA ĐƠN NỘI BỘ
			{
				util.HuyUpdate_TaiKhoan(db, hdId, "Hóa đơn nội bộ");
				// CÀI KẾ TOÁN
				query = " SELECT HD.LoaiHoaDon,LOAI_SP.PK_SEQ LOAIHH, HD.NPP_FK KHACHHANG_FK ,  \n"+
						" (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = KH.TAIKHOANKHNB_FK AND npp_fk = 1 ) TAIKHOANHO_KH, \n"+
						//							" (SELECT PK_SEQ FROM TraphacoERP.dbo.ERP_NHACUNGCAP WHERE NPP_FK = 1) NCC_FK, \n"+
						" (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = '51200000' AND npp_fk = 1 ) TAIKHOANHO_51200000, \n"+
						" (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = '33311000' AND npp_fk = 1 ) TAIKHOANHO_33311000, \n"+
						" (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = 63220000 AND npp_fk = HD.NPP_FK ) TAIKHOANCN_GIAVON, \n"+
						" (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = '13311000' AND npp_fk = HD.NPP_FK ) TAIKHOANCN_13311000, \n"+
						" (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = '33610000' AND npp_fk = HD.NPP_FK ) TAIKHOANCN_33610000, \n"+
						" (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = LOAI_SP.TaikhoanChietkhau_sh_fk AND npp_fk = 1 ) TAIKHOANHO_CHIETKHAU, \n"+
						" HD_SP.SOLUONG SOLUONG,  HD_SP.DONGIA  DONGIA, HD.VAT THUEVAT,(SELECT SUM(CHIETKHAU) FROM ERP_HOADON_CHIETKHAU WHERE HOADON_FK=HD.PK_SEQ) AS TIENCKTM, \n"+
						" HD_SP.CHIETKHAU CHIETKHAU, SP.PK_SEQ SANPHAM_FK, HD.NGAYXUATHD,'100000' AS TIENTE_FK, 1  AS TIGIA, \n"+
						" SP.MA MASP, SP.TEN TENSP, HD_SP.DONVITINH DONVI, HD.SOHOADON, HD.KBH_FK, HD.KHO_FK,  \n"+
						" CASE WHEN ISNULL(HD.KHACHHANG_FK,0) > 0 THEN 0 ELSE 1 END AS ISNPP,KH.MAFAST+'-'+HD.GHICHU AS GHICHU \n"+
						" FROM ERP_HOADON HD \n " +
						" INNER JOIN ERP_HOADON_SP HD_SP ON HD.PK_SEQ = HD_SP.HOADON_FK \n"+
						" INNER JOIN ERP_SANPHAM SP ON HD_SP.SANPHAM_FK = SP.PK_SEQ \n"+
						" LEFT JOIN ERP_LOAISANPHAM LOAI_SP ON SP.LOAISANPHAM_FK = LOAI_SP.PK_SEQ \n"+
						" LEFT JOIN NHAPHANPHOI KH ON HD.NPP_FK = KH.PK_SEQ \n"+
						" WHERE HD.PK_SEQ  = '"+hdId+"' \n";
				System.out.println(query);
				ResultSet rskt = db.get(query);

				if(rskt!=null)
				{
					while(rskt.next())
					{
						String loaiHD = "HDHO_NB";
						String loaihh_fk = rskt.getString("LOAIHH");
						String masp = rskt.getString("MASP");
						String tensp = rskt.getString("TENSP");
						String donvi = rskt.getString("DONVI"); 
						String sohoadon = rskt.getString("SOHOADON"); 
						String kbh_fk = rskt.getString("KBH_FK")== null ? "": rskt.getString("KBH_FK"); 
						String kho_fk = rskt.getString("KHO_FK"); 
						String isNPP = rskt.getString("ISNPP"); 
						//					String ncc_fk = rskt.getString("NCC_FK"); 
						String dienGiai =rskt.getString("GHICHU");
						String tiente_fk = rskt.getString("tiente_fk");
						int tygia = rskt.getInt("tigia");

						double soluong = rskt.getDouble("SOLUONG");
						double chietkhau = Math.round(rskt.getDouble("CHIETKHAU"));
						double dongia = rskt.getDouble("DONGIA");
						double thuevat = rskt.getDouble("THUEVAT");

						double tienhang = Math.round(soluong * dongia)	;
						double thueGTGT = Math.round(tienhang*thuevat/100);

						String khachhang_fk = rskt.getString("KHACHHANG_FK");
						String sanpham_fk = rskt.getString("SANPHAM_FK");

						String TAIKHOAN_NO = "";
						String TAIKHOAN_CO = "";

						String loaidoituong_no = "";
						String loaidoituong_co = "";

						String madoituong_no = "";
						String madoituong_co = "";		

						String ngayghinhan = rskt.getString("NGAYXUATHD");
						String nam = ngayghinhan.substring(0, 4);
						String thang = ngayghinhan.substring(5, 7);

						String TAIKHOANHO_KH = rskt.getString("TAIKHOANHO_KH") == null ? "": rskt.getString("TAIKHOANHO_KH");
						String TAIKHOANHO_51200000 = rskt.getString("TAIKHOANHO_51200000") == null ? "": rskt.getString("TAIKHOANHO_51200000");
						String TAIKHOANHO_33311000 = rskt.getString("TAIKHOANHO_33311000") == null ? "": rskt.getString("TAIKHOANHO_33311000");
						String TAIKHOANCN_GIAVON = rskt.getString("TAIKHOANCN_GIAVON") == null ? "": rskt.getString("TAIKHOANCN_GIAVON");
						String TAIKHOANCN_13311000 = rskt.getString("TAIKHOANCN_13311000") == null ? "": rskt.getString("TAIKHOANCN_13311000");
						String TAIKHOANCN_33610000 = rskt.getString("TAIKHOANCN_33610000") == null ? "": rskt.getString("TAIKHOANCN_33610000");
						String TAIKHOANHO_CHIETKHAU = rskt.getString("TAIKHOANHO_CHIETKHAU") == null ? "": rskt.getString("TAIKHOANHO_CHIETKHAU");
						{ // HO
							if(tienhang > 0)
							{
								TAIKHOAN_NO = TAIKHOANHO_KH;
								TAIKHOAN_CO = TAIKHOANHO_51200000;

								loaidoituong_no = "Khách hàng";
								madoituong_no = khachhang_fk;

								loaidoituong_co = "Sản phẩm";
								madoituong_co = sanpham_fk;

								if (TAIKHOAN_NO.trim().length() <= 0 || TAIKHOAN_CO.trim().length() <= 0) 
								{
									msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
									rskt.close();
									db.getConnection().rollback();
									return false;
								}




								msg = util.Update_TaiKhoan_FULL_v2( db, thang, nam, ngayghinhan, ngayghinhan, "Hóa đơn nội bộ", hdId, TAIKHOANHO_KH, TAIKHOANHO_51200000, "", 
										Double.toString(tienhang), Double.toString(tienhang), loaidoituong_no, madoituong_no, loaidoituong_co, madoituong_co, "0", 
										Double.toString(soluong), Double.toString(dongia), tiente_fk, Double.toString(dongia), Double.toString(tygia), Double.toString(tienhang), 
										Double.toString(tienhang), "Hóa đơn - Tiền hàng", Double.toString(thueGTGT) , dienGiai , sohoadon ,isNPP ,masp , tensp, donvi, kbh_fk, 
										kho_fk, "", "", Double.toString(tienhang),loaiHD);

								if(msg.trim().length()>0)
								{
									msg = "6.Lỗi khi chạy kế toán: " + msg;
									rskt.close();
									db.getConnection().rollback();
									return false;
								}

								TAIKHOAN_NO = TAIKHOANCN_GIAVON;
								TAIKHOAN_CO = TAIKHOANCN_33610000;

								loaidoituong_no = "";
								madoituong_no = "";

								loaidoituong_co = "Chi nhánh/Đối tác";
								madoituong_co = "1";

								msg = util.Update_TaiKhoan_FULL_v2( db, thang, nam, ngayghinhan, ngayghinhan, "Hóa đơn nội bộ", hdId, TAIKHOANCN_GIAVON, TAIKHOANCN_33610000, "", 
										Double.toString(tienhang), Double.toString(tienhang), loaidoituong_no, madoituong_no, loaidoituong_co,madoituong_co, "0", 
										Double.toString(soluong), Double.toString(dongia), tiente_fk, Double.toString(dongia), Double.toString(tygia), Double.toString(tienhang), 
										Double.toString(tienhang), "Hóa đơn - Tiền hàng", Double.toString(thueGTGT) , dienGiai , sohoadon ,"1" ,masp , tensp, donvi, kbh_fk, 
										kho_fk, "", "", Double.toString(tienhang),loaiHD);

								if(msg.trim().length()>0)
								{
									msg = "6.Lỗi khi chạy kế toán: " + msg;
									rskt.close();
									db.getConnection().rollback();
									return false;
								}

							}

						}


					}
					rskt.close();
				}

				query ="  SELECT HD.LoaiHoaDon, HD.NPP_FK KHACHHANG_FK , \n"+
						"   (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = KH.TAIKHOANKHNB_FK AND npp_fk = 1 ) TAIKHOANHO_KH,  \n"+
						"   (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = '51200000' AND npp_fk = 1 ) TAIKHOANHO_51200000,   \n"+
						"   (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = '33311000' AND npp_fk = 1 ) TAIKHOANHO_33311000,  \n"+
						"   (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = '33610000' AND npp_fk = HD.NPP_FK ) TAIKHOANCN_33610000, \n"+ 
						"  (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = '13311000' AND npp_fk = HD.NPP_FK ) TAIKHOANCN_13311000,   \n"+
						"   (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = '63213000' AND npp_fk = HD.NPP_FK ) TAIKHOANCN_63213000,  \n"+
						"   HD.GHICHU ,HD.VAT,HD_CHIETKHAU.CHIETKHAU AS CHIETKHAU,HD_CHIETKHAU.VATCK AS VATCK, HD.NGAYXUATHD,HD.SOHOADON,'100000' AS TIENTE_FK, 1  AS TIGIA, \n"+
						"   CASE WHEN ISNULL(HD.KHACHHANG_FK,0) > 0 THEN 0 ELSE 1 END AS ISNPP,KH.MAFAST+'-'+HD.GHICHU AS GHICHU  \n"+
						"   FROM ERP_HOADON HD  \n"+
						"   LEFT JOIN (  \n"+
						"   SELECT HOADON_FK,SUM(CHIETKHAU) AS CHIETKHAU,SUM(TIENVAT) AS VATCK FROM ERP_HOADON_CHIETKHAU \n"+
						"   WHERE HOADON_FK =" + hdId+" \n"+
						"   GROUP BY HOADON_FK \n"+
						"   ) HD_CHIETKHAU ON HD_CHIETKHAU.HOADON_FK = HD.PK_SEQ \n"+
						"   LEFT JOIN NHAPHANPHOI KH ON HD.NPP_FK = KH.PK_SEQ  \n"+
						" WHERE HD.PK_SEQ  = '"+hdId+"' \n";
				double TIENCKTM = 0;
				double VAT =0;
				System.out.println(query);
				rskt= db.get(query);

				if(rskt!=null)
				{
					while(rskt.next())
					{
						String loaiHD = "HDHO_NB";
						//					String loaihh_fk = rskt2.getString("LOAIHH");
						String sohoadon = rskt.getString("SOHOADON"); 
						String isNPP = rskt.getString("ISNPP"); 
						//					String ncc_fk = rskt.getString("NCC_FK"); 
						String dienGiai =rskt.getString("GHICHU");
						String tiente_fk = rskt.getString("tiente_fk");
						int tygia = rskt.getInt("tigia");

						double chietkhau = Math.round(rskt.getDouble("CHIETKHAU"));
						double vat_chietkhau = Math.round(rskt.getDouble("VATCK"));
						double thuevat = rskt.getDouble("VAT");

						String khachhang_fk = rskt.getString("KHACHHANG_FK");

						String TAIKHOAN_NO = "";
						String TAIKHOAN_CO = "";

						String loaidoituong_no = "";
						String loaidoituong_co = "";

						String madoituong_no = "";
						String madoituong_co = "";		

						String ngayghinhan = rskt.getString("NGAYXUATHD");
						String nam = ngayghinhan.substring(0, 4);
						String thang = ngayghinhan.substring(5, 7);

						String TAIKHOANHO_KH = rskt.getString("TAIKHOANHO_KH") == null ? "": rskt.getString("TAIKHOANHO_KH");
						String TAIKHOANHO_51200000 = rskt.getString("TAIKHOANHO_51200000") == null ? "": rskt.getString("TAIKHOANHO_51200000");
						String TAIKHOANHO_33311000 = rskt.getString("TAIKHOANHO_33311000") == null ? "": rskt.getString("TAIKHOANHO_33311000");
						String TAIKHOANCN_GIAVON = rskt.getString("TAIKHOANCN_63213000") == null ? "": rskt.getString("TAIKHOANCN_63213000");
						String TAIKHOANCN_13311000 = rskt.getString("TAIKHOANCN_13311000") == null ? "": rskt.getString("TAIKHOANCN_13311000");
						String TAIKHOANCN_33610000 = rskt.getString("TAIKHOANCN_33610000") == null ? "": rskt.getString("TAIKHOANCN_33610000");


						{ // HO
							if(thuevat > 0)
							{
								TAIKHOAN_NO = TAIKHOANHO_KH;
								TAIKHOAN_CO = TAIKHOANHO_33311000;

								loaidoituong_no = "Chi nhánh/Đối tác";
								madoituong_no = khachhang_fk;

								loaidoituong_co = "";
								madoituong_co = "";

								if (TAIKHOANHO_KH.trim().length() <= 0 || TAIKHOANHO_51200000.trim().length() <= 0) 
								{
									msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
									rskt.close();
									db.getConnection().rollback();
									return false;
								}




								msg = util.Update_TaiKhoan_FULL_v2( db, thang, nam, ngayghinhan, ngayghinhan, "Hóa đơn nội bộ", hdId, TAIKHOAN_NO, TAIKHOAN_CO, "", 
										Double.toString(thuevat), Double.toString(thuevat), loaidoituong_no, madoituong_no, loaidoituong_co, madoituong_co, "0", 
										Double.toString(0), Double.toString(0), tiente_fk, Double.toString(0), Double.toString(tygia), Double.toString(thuevat), 
										Double.toString(thuevat), "Hóa đơn - Tiền thuế", Double.toString(thuevat) , dienGiai , sohoadon ,isNPP ,"" , "", "", "", 
										"", "", "", Double.toString(thuevat),loaiHD);

								if(msg.trim().length()>0)
								{
									msg = "6.Lỗi khi chạy kế toán: " + msg;
									rskt.close();
									db.getConnection().rollback();
									return false;
								}

								TAIKHOAN_NO = TAIKHOANCN_13311000;
								TAIKHOAN_CO = TAIKHOANCN_33610000;

								loaidoituong_no = "";
								madoituong_no = "";

								loaidoituong_co = "Chi nhánh/Đối tác";
								madoituong_co = "1";



								msg = util.Update_TaiKhoan_FULL_v2( db, thang, nam, ngayghinhan, ngayghinhan, "Hóa đơn nội bộ", hdId, TAIKHOAN_NO, TAIKHOAN_CO, "", 
										Double.toString(thuevat), Double.toString(thuevat), loaidoituong_no, madoituong_no, loaidoituong_co, madoituong_co, "0", 
										Double.toString(0), Double.toString(0), tiente_fk, Double.toString(0), Double.toString(tygia), Double.toString(thuevat), 
										Double.toString(thuevat), "Hóa đơn - Tiền thuế", Double.toString(thuevat) , dienGiai , sohoadon ,"1" ,"" , "", "", "", 
										"", "", "", Double.toString(thuevat),loaiHD);

								if(msg.trim().length()>0)
								{
									msg = "6.Lỗi khi chạy kế toán: " + msg;
									rskt.close();
									db.getConnection().rollback();
									return false;
								}

							}

							if(chietkhau > 0)
							{
								String diengiai_ck = dienGiai+ " chiết khấu ";
								TAIKHOAN_NO = TAIKHOANHO_51200000;
								TAIKHOAN_CO = TAIKHOANHO_KH;

								loaidoituong_no = "";
								madoituong_no = "";

								loaidoituong_co = "Chi nhánh/Đối tác";
								madoituong_co = khachhang_fk;

								if (TAIKHOANHO_KH.trim().length() <= 0 || TAIKHOANHO_51200000.trim().length() <= 0) 
								{
									msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
									rskt.close();
									db.getConnection().rollback();
									return false;
								}




								msg = util.Update_TaiKhoan_FULL_v2( db, thang, nam, ngayghinhan, ngayghinhan, "Hóa đơn nội bộ", hdId, TAIKHOAN_NO, TAIKHOAN_CO, "", 
										Double.toString(chietkhau), Double.toString(chietkhau), loaidoituong_no, madoituong_no, loaidoituong_co, madoituong_co, "0", 
										Double.toString(0), Double.toString(0), tiente_fk, Double.toString(0), Double.toString(tygia), Double.toString(chietkhau), 
										Double.toString(chietkhau), "Hóa đơn - Chiết khấu", Double.toString(chietkhau) , diengiai_ck , sohoadon ,isNPP ,"" , "", "", "", 
										"", "", "", Double.toString(chietkhau),loaiHD);

								if(msg.trim().length()>0)
								{
									msg = "6.Lỗi khi chạy kế toán: " + msg;
									rskt.close();
									db.getConnection().rollback();
									return false;
								}

								TAIKHOAN_NO = TAIKHOANCN_33610000;
								TAIKHOAN_CO = TAIKHOANCN_GIAVON;

								loaidoituong_no = "Chi nhánh/Đối tác";
								madoituong_no = "1";

								loaidoituong_co = "";
								madoituong_co = "";



								msg = util.Update_TaiKhoan_FULL_v2( db, thang, nam, ngayghinhan, ngayghinhan, "Hóa đơn nội bộ", hdId, TAIKHOAN_NO, TAIKHOAN_CO, "", 
										Double.toString(chietkhau), Double.toString(chietkhau), loaidoituong_no, madoituong_no, loaidoituong_co, madoituong_co, "0", 
										Double.toString(0), Double.toString(0), tiente_fk, Double.toString(0), Double.toString(tygia), Double.toString(chietkhau), 
										Double.toString(chietkhau), "Hóa đơn - Tiền khấu", Double.toString(chietkhau) , dienGiai , sohoadon ,"1" ,"" , "", "", "", 
										"", "", "", Double.toString(chietkhau),loaiHD);

								if(msg.trim().length()>0)
								{
									msg = "6.Lỗi khi chạy kế toán: " + msg;
									rskt.close();
									db.getConnection().rollback();
									return false;
								}

							}
						}


					}
					rskt.close();
				}
			}
			//TAO HOA DON TU DONG
			String error = this.TaoXuatKhoTuDong();
			if(error.trim().length()>0)
			{
				msg = error;
				db.getConnection().rollback();
				return false;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			this.msg = "Exception: " + e.getMessage();
			e.printStackTrace();
			db.update("rollback");
			return false;
		}

		return true;
	}
	
	private String GhinhanKT (String hdId)
	{
		String msg = "", query = "";
		Utility util = new Utility();
		try
		{
			// CÀI KẾ TOÁN
			query =     
				"SELECT HD.LoaiHoaDon,LOAI_SP.PK_SEQ LOAIHH, DH.ISHM, HD.NPP_FK KHACHHANG_FK, HD_SP.scheme, km.codieukien, km.codangki, km.inchung, \n"+   
				"(SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = '13111000' AND npp_fk = 1 ) TAIKHOANHO_KH, \n"+
				"(SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = LOAI_SP.TaikhoanGiavon_sh_fk AND npp_fk = 1 ) TAIKHOANHO_GIAVON, \n"+
				"(SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = LOAI_SP.TaikhoanDoanhthu_sh_fk AND npp_fk = 1 ) TAIKHOANHO_DOANHTHU, \n"+
				"(SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = LOAI_SP.TaikhoanDoanhthuNB_sh_fk AND npp_fk = 1 ) TAIKHOANHO_DOANHTHUBOIBO, \n"+
				"(SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = '33311000' AND npp_fk = 1 ) TAIKHOANHO_THUE, \n"+ 
				"(SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = LOAI_SP.TaikhoanChietkhau_sh_fk AND npp_fk = 1 ) TAIKHOANHO_CHIETKHAU, \n"+
				"(SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = (select taikhoan_fk from erp_nhomchiphi where ten = 'PBC-64163001') AND NPP_FK = 1) taikhoankt_64163001, \n" +
				"(select taikhoan_fk from erp_nhomchiphi where ten = 'PBC-64163001') kmcp_64163001, \n" +
				"(SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = (select taikhoan_fk from erp_nhomchiphi where ten = 'PBC-64144001') AND NPP_FK = 1) taikhoankt_64144001, \n" +
				"(select taikhoan_fk from erp_nhomchiphi where ten = 'PBC-64144001') kmcp_64144001, \n" +
				"(SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = (select taikhoan_fk from erp_nhomchiphi where ten = 'PBC-35320001') AND NPP_FK = 1) taikhoankt_35320001, \n" +
				"(select taikhoan_fk from erp_nhomchiphi where ten = 'PBC-35320001') kmcp_35320001, \n" +
				"(SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = (select taikhoan_fk from erp_nhomchiphi where ten = 'PBC-64180001') AND NPP_FK = 1) taikhoankt_64180001, \n" +
				"(select taikhoan_fk from erp_nhomchiphi where ten = 'PBC-64180001') kmcp_64180001, \n" +
				"(SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = (select taikhoan_fk from erp_nhomchiphi where ten = 'PBC-64173001') AND NPP_FK = 1) taikhoankt_64173001, \n" +
				"(select taikhoan_fk from erp_nhomchiphi where ten = 'PBC-64173001') kmcp_64173001, \n" +
				"'' AS SOLO, HD_SP.SOLUONG SOLUONG, HD_SP.DONGIA  DONGIA, HD_SP.VAT THUEVAT, HD_SP.CHIETKHAU CHIETKHAU, \n " +
				"SP.PK_SEQ SANPHAM_FK, HD.NGAYXUATHD, isnull(HD.TIENTE_FK, 100000) as TIENTE_FK, isnull(HD.TYGIA, 1) as TIGIA, \n"+
				"HD_SP.SANPHAM_FK MASP, SP.TEN TENSP, HD_SP.DONVITINH AS DONVI, HD.SOHOADON, HD.KBH_FK, HD.KHO_FK,  \n"+
				"CASE WHEN ISNULL(HD.KHACHHANG_FK,0) > 0 THEN 0 ELSE 1 END AS ISNPP, HD.GHICHU \n"+
				"FROM ERP_HOADON HD \n " +
				"INNER JOIN ERP_HOADON_SP HD_SP ON HD.PK_SEQ = HD_SP.HOADON_FK \n"+
				"INNER JOIN ERP_SANPHAM SP ON HD_SP.SANPHAM_FK = SP.PK_SEQ \n"+
				"inner join erp_hoadon_ddh hd_dh on hd.pk_seq = hd_dh.hoadon_fk \n"+
				"inner join erp_dondathang dh on hd_dh.ddh_fk = dh.pk_seq \n"+
				"LEFT JOIN ERP_LOAISANPHAM LOAI_SP ON SP.LOAISANPHAM_FK = LOAI_SP.PK_SEQ \n"+
				"LEFT JOIN NHAPHANPHOI KH ON HD.NPP_FK = KH.PK_SEQ \n"+
				"left join ctkhuyenmai km on isnull(hd_sp.scheme, '') = km.scheme \n"+
				"WHERE HD.PK_SEQ  = '"+hdId+"' \n";

			System.out.println("::: DINH KHOAN: " + query);
			ResultSet rskt = db.get(query);
	
			if(rskt!=null)
			{
				while(rskt.next())
				{
					String loaiHD = "HDHO";
					String loaihh_fk = rskt.getString("LOAIHH");
					String scheme = rskt.getString("SCHEME");
				 	int codieukien = rskt.getInt("codieukien");
				 	int codangki = rskt.getInt("codangki");
				 	int inchung = rskt.getInt("inchung");
				 	int ishm = rskt.getInt("ishm");
				 	int loaihoadon = rskt.getInt("LoaiHoaDon");
					String solo = rskt.getString("SOLO");
					String masp = rskt.getString("MASP");
					String tensp = rskt.getString("TENSP");
					String donvi = rskt.getString("DONVI")== null ? "": rskt.getString("DONVI"); 
					String sohoadon = rskt.getString("SOHOADON"); 
					String kbh_fk = rskt.getString("KBH_FK")== null ? "": rskt.getString("KBH_FK");
					String kho_fk = rskt.getString("KHO_FK"); 
					String isNPP = rskt.getString("ISNPP");  
					String dienGiai = rskt.getString("GHICHU");
	
					String tiente_fk = rskt.getString("tiente_fk");
					int tygia = rskt.getInt("tigia");
	
					//int mauhoadon = rskt.getInt("MAUHOADON");
	
					double soluong = rskt.getDouble("SOLUONG");
					double chietkhau = Math.round(rskt.getDouble("CHIETKHAU"));
					double dongia = rskt.getDouble("DONGIA");
					double thuevat = rskt.getDouble("THUEVAT");
	
					double tienhang = Math.round(soluong * dongia);
					double thueGTGT = Math.round(tienhang * thuevat/100);
	
					String khachhang_fk = rskt.getString("KHACHHANG_FK");
					String sanpham_fk = rskt.getString("SANPHAM_FK");
	
					String TAIKHOAN_NO = "";
					String TAIKHOAN_CO = "";
	
					String loaidoituong_no = "";
					String loaidoituong_co = "";
	
					String madoituong_no = "";
					String madoituong_co = "";		
	
					String ngayghinhan = rskt.getString("NGAYXUATHD");
					String nam = ngayghinhan.substring(0, 4);
					String thang = ngayghinhan.substring(5, 7);
	
					String TAIKHOANHO_KH = rskt.getString("TAIKHOANHO_KH") == null ? "": rskt.getString("TAIKHOANHO_KH");
					String TAIKHOANHO_GIAVON = rskt.getString("TAIKHOANHO_GIAVON") == null ? "": rskt.getString("TAIKHOANHO_GIAVON");
					String TAIKHOANHO_DOANHTHU = rskt.getString("TAIKHOANHO_DOANHTHU") == null ? "": rskt.getString("TAIKHOANHO_DOANHTHU");
					String TAIKHOANHO_DOANHTHUBOIBO = rskt.getString("TAIKHOANHO_DOANHTHUBOIBO") == null ? "": rskt.getString("TAIKHOANHO_DOANHTHUBOIBO");
					String TAIKHOANHO_THUE = rskt.getString("TAIKHOANHO_THUE") == null ? "": rskt.getString("TAIKHOANHO_THUE");
					String TAIKHOANHO_CHIETKHAU = rskt.getString("TAIKHOANHO_CHIETKHAU") == null ? "": rskt.getString("TAIKHOANHO_CHIETKHAU");
					
					String kmcp = "";
					String taikhoankt_64163001 = "", taikhoankt_64144001 = "", taikhoankt_35320001 = "", taikhoankt_64180001 = "", taikhoankt_64173001 = "";
					String kmcp_64163001 = "", kmcp_64144001 = "", kmcp_35320001 = "", kmcp_64180001 = "", kmcp_64173001 = "";
					taikhoankt_64163001 = rskt.getString("taikhoankt_64163001");
					kmcp_64163001 = rskt.getString("kmcp_64163001");
					taikhoankt_64144001 = rskt.getString("taikhoankt_64144001");
					kmcp_64144001 = rskt.getString("kmcp_64144001");
					taikhoankt_35320001 = rskt.getString("taikhoankt_35320001");
					kmcp_35320001 = rskt.getString("kmcp_35320001");
					taikhoankt_64180001 = rskt.getString("taikhoankt_64180001");
					kmcp_64180001 = rskt.getString("kmcp_64180001");
					taikhoankt_64173001 = rskt.getString("taikhoankt_64173001");
					kmcp_64173001 = rskt.getString("kmcp_64173001");
					
					if(tienhang > 0 && loaihoadon == 0)
					{
						TAIKHOAN_NO = TAIKHOANHO_KH;
						TAIKHOAN_CO = TAIKHOANHO_DOANHTHU;
	
						//Khách hàng và nahf phân phối đều lưu trong bảng NHAPHANPHOI, nên loaidoituong lúc nào cũng là "Khách hàng"
						loaidoituong_no = "Khách hàng";
						madoituong_no = khachhang_fk;

						loaidoituong_co = "Sản phẩm";
						madoituong_co = sanpham_fk;
	
						if (TAIKHOAN_NO.trim().length() <= 0 || TAIKHOAN_CO.trim().length() <= 0) 
						{
							msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
							rskt.close();
							return msg;
						}
	
						msg = util.Update_TaiKhoan_FULL_v2( db, thang, nam, ngayghinhan, ngayghinhan, "Hóa đơn tài chính", hdId, TAIKHOAN_NO, TAIKHOAN_CO, "", 
								Double.toString(tienhang), Double.toString(tienhang), loaidoituong_no, madoituong_no, loaidoituong_co, madoituong_co, "0",
								Double.toString(soluong), Double.toString(dongia), tiente_fk, Double.toString(dongia), Double.toString(tygia), 
								Double.toString(tienhang), Double.toString(tienhang), "Hóa đơn - Tiền hàng", Double.toString(thueGTGT), dienGiai , 
								sohoadon ,isNPP ,masp , tensp, donvi, kbh_fk, kho_fk, solo, "", Double.toString(tienhang), loaiHD);
	
						System.out.println(":::1. LỖI ĐỊNH KHOẢN: " + msg);
						if(msg.trim().length()>0)
						{
							msg = "1.Lỗi khi chạy kế toán: " + msg;
							rskt.close();
							return msg;
						}
					}
	
					if(chietkhau > 0 && loaihoadon == 0)
					{
						TAIKHOAN_NO = TAIKHOANHO_CHIETKHAU;
						TAIKHOAN_CO = TAIKHOANHO_KH;
	
						loaidoituong_no = "Sản phẩm";
						madoituong_no = sanpham_fk;
	
						loaidoituong_co = "Khách hàng";
						madoituong_co = khachhang_fk;

						if (TAIKHOAN_NO.trim().length() <= 0 || TAIKHOAN_CO.trim().length() <= 0) 
						{
							msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
							rskt.close();
							return msg;
						}
	
						msg = util.Update_TaiKhoan_FULL_v2( db, thang, nam, ngayghinhan, ngayghinhan, "Hóa đơn tài chính", hdId, TAIKHOAN_NO, TAIKHOAN_CO, "", 
								Double.toString(chietkhau), Double.toString(chietkhau), loaidoituong_no, madoituong_no, loaidoituong_co, madoituong_co, "0", 
								Double.toString(soluong), Double.toString(dongia), tiente_fk, Double.toString(dongia), Double.toString(tygia), Double.toString(chietkhau), 
								Double.toString(chietkhau), "Hóa đơn - Chiết khấu", Double.toString(thueGTGT) , dienGiai , sohoadon ,isNPP ,masp , 
								tensp, donvi, kbh_fk, kho_fk, solo, "", Double.toString(tienhang), loaiHD);
	
						System.out.println(":::1. LỖI ĐỊNH KHOẢN: " + msg);
						if(msg.trim().length()>0)
						{
							msg = "2.Lỗi khi chạy kế toán: " + msg;
							rskt.close();
							return msg;
						}
					}

					if(loaihoadon == 0 && scheme.trim().length() > 0 && codieukien == 0)
					{
						query = "select dongia, vat from erp_hoadon_sp where hoadon_fk = '" + hdId + "' and sanpham_fk = " + sanpham_fk + " and isnull(scheme, '') = ''";
						ResultSet rsdg = db.get(query);
						if(rsdg != null && rsdg.next())
						{
							dongia = rskt.getDouble("DONGIA");
							thuevat = rskt.getDouble("THUEVAT");
							tienhang = Math.round(soluong * dongia);
							thueGTGT = Math.round(tienhang * thuevat / 100);
							rsdg.close();
						}
					}
					if(thueGTGT > 0)
					{
						TAIKHOAN_NO = TAIKHOANHO_KH;
						TAIKHOAN_CO = TAIKHOANHO_THUE;
						if(loaihoadon == 0 && scheme.trim().length() > 0 && codieukien == 0)
						{
							TAIKHOAN_NO = taikhoankt_64163001;
							kmcp = kmcp_64163001;
						}
						if(loaihoadon == 1)
						{
							if(ishm == 1)
							{
								TAIKHOAN_NO = taikhoankt_64144001;
								kmcp = kmcp_64144001;
							}
							else if(ishm == 2)
							{
								TAIKHOAN_NO = taikhoankt_35320001;
								kmcp = kmcp_35320001;
							}
							else if(ishm == 3)
							{
								TAIKHOAN_NO = taikhoankt_64180001;
								kmcp = kmcp_64180001;
							}
							else if(ishm == 4)
							{
								TAIKHOAN_NO = taikhoankt_64173001;
								kmcp = kmcp_64173001;
							}
						}
	
						loaidoituong_no = "Khách hàng";
						madoituong_no = khachhang_fk;
	
						loaidoituong_co = "Sản phẩm";
						madoituong_co = sanpham_fk;
	
						if (TAIKHOAN_NO.trim().length() <= 0 || TAIKHOAN_CO.trim().length() <= 0) 
						{
							msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
							rskt.close();
							return msg;
						}
	
						msg = util.Update_TaiKhoan_FULL_v2_kmcp( db, thang, nam, ngayghinhan, ngayghinhan, "Hóa đơn tài chính", hdId, TAIKHOAN_NO, TAIKHOAN_CO, "", 
								Double.toString(thueGTGT), Double.toString(thueGTGT), loaidoituong_no, madoituong_no, loaidoituong_co, madoituong_co, "0", 
								Double.toString(soluong), Double.toString(dongia), tiente_fk, Double.toString(dongia), Double.toString(tygia), Double.toString(thueGTGT), 
								Double.toString(thueGTGT), "Hóa đơn - Tiền thuế", Double.toString(thueGTGT) , dienGiai , sohoadon , isNPP ,masp , tensp, donvi, kbh_fk, 
								kho_fk, solo, "", Double.toString(tienhang), loaiHD, kmcp);
	
						System.out.println(":::3. LỖI ĐỊNH KHOẢN: " + msg);
						if(msg.trim().length()>0)
						{
							msg = "3.Lỗi khi chạy kế toán: " + msg;
							rskt.close();
							return msg;
						}
					}
				}
				rskt.close();

				//HÓA ĐƠN TRÊN HO KHÔNG CÓ CHIẾT KHẤU TRONG BẢNG ERP_HOADON_CHIETKHAU
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "Lỗi";
		}
		return msg;
	}
	
	public static void main(String[] arg)
	{
		dbutils db = new dbutils();
		String query=" select pk_seq,nguoisua from erp_hoadon where pk_seq = 110340 and TRANGTHAI in (2,4) ";
//		
		ResultSet rs= db.get(query);
		try
		{
			while(rs.next())
			{
				ErpHoadontaichinh hd = new ErpHoadontaichinh();
				hd.setId(rs.getString("pk_seq"));
				hd.setUserId(rs.getString("nguoisua"));
				//hd.chot(rs.getString("pk_seq"), rs.getString("nguoisua"));
				//hd.GhinhanKT(rs.getString("pk_seq"));
				hd.TaoXuatKhoTuDong();
				hd.DBclose();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		db.shutDown();
		System.out.println("::: CHAY XONG ");
	}
	
	public boolean Chaydinhkhoan(String hdId)
	{
		Utility util = new Utility();
		try
		{
			db.getConnection().setAutoCommit(false);
			String query = " select a.npp_fk, a.noibo, a.loaihoadon, dh.isKM  " +
					" from ERP_HOADON a " +
					" inner join ERP_HOADON_DDH ddh on ddh.HOADON_FK=a.PK_SEQ "+
					" inner join ERP_DONDATHANG dh on dh.PK_SEQ=ddh.DDH_FK "+
					" where a.pk_seq = "+hdId;

			ResultSet rs=db.get(query);
			String npp="";
			int noibo = 0;
			int isKM = 0;

			if(rs.next())
			{
				npp = rs.getString("npp_fk");
				noibo = rs.getInt("NOIBO");
				isKM = rs.getInt("isKM");
			}
			rs.close();


			//CẬP NHẬT TIỀN
			db.execProceduce2("CapNhatTooltip_HoaDon", new String[]{ hdId });

			if(noibo == 0 && isKM == 0) // HÓA ĐƠN TÀI CHINH
			{
				// CÀI KẾ TOÁN
				query =     
						" SELECT HD.LoaiHoaDon,LOAI_SP.PK_SEQ LOAIHH, HD.NPP_FK KHACHHANG_FK ,  \n"+   
						" (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = '13111000' AND npp_fk = 1 ) TAIKHOANHO_KH, \n"+
						" (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = LOAI_SP.TaikhoanGiavon_sh_fk AND npp_fk = 1 ) TAIKHOANHO_GIAVON, \n"+
						" (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = LOAI_SP.TaikhoanDoanhthu_sh_fk AND npp_fk = 1 ) TAIKHOANHO_DOANHTHU, \n"+
						" (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = LOAI_SP.TaikhoanDoanhthuNB_sh_fk AND npp_fk = 1 ) TAIKHOANHO_DOANHTHUBOIBO, \n"+
						" (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = '33311000' AND npp_fk = 1 ) TAIKHOANHO_THUE, \n"+ 
						" (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = LOAI_SP.TaikhoanChietkhau_sh_fk AND npp_fk = 1 ) TAIKHOANHO_CHIETKHAU, \n"+
						" '' AS SOLO, HD_SP.SOLUONG SOLUONG, HD_SP.DONGIA  DONGIA, HD_SP.VAT THUEVAT, HD_SP.CHIETKHAU CHIETKHAU, \n " +
						" SP.PK_SEQ SANPHAM_FK, HD.NGAYXUATHD, HD.TIENTE_FK, HD.TIGIA, \n"+
						" HD_SP.SANPHAM_FK MASP, SP.TEN TENSP, HD_SP.DONVITINH AS DONVI, HD.SOHOADON, HD.KBH_FK, HD.KHO_FK,  \n"+
						" CASE WHEN ISNULL(HD.KHACHHANG_FK,0) > 0 THEN 0 ELSE 1 END AS ISNPP, HD.GHICHU \n"+
						" FROM ERP_HOADON HD \n " +
						" INNER JOIN ERP_HOADON_SP HD_SP ON HD.PK_SEQ = HD_SP.HOADON_FK \n"+
						" INNER JOIN ERP_SANPHAM SP ON HD_SP.SANPHAM_FK = SP.PK_SEQ \n"+
						" LEFT JOIN ERP_LOAISANPHAM LOAI_SP ON SP.LOAISANPHAM_FK = LOAI_SP.PK_SEQ \n"+
						" LEFT JOIN NHAPHANPHOI KH ON HD.NPP_FK = KH.PK_SEQ \n"+
						" WHERE HD.PK_SEQ  = '"+hdId+"' AND HD.LoaiHoaDon = '0' \n";
/*						" GROUP BY HD.LoaiHoaDon,LOAI_SP.PK_SEQ, HD_SP.SOLO, HD.KHACHHANG_FK,  HD.NGAYXUATHD, HD.TIENTE_FK, HD.TIGIA, HD_SP.MA, \n " +
						" HD_SP.TEN , HD_SP.DONVI, HD.SOHOADON, HD.KBH_FK, HD.KHO_FK , SP.PK_SEQ, HD.NPP_FK, \n " +
						" LOAI_SP.TaikhoanGiavon_sh_fk, LOAI_SP.TaikhoanDoanhthu_sh_fk, LOAI_SP.TaikhoanDoanhthuNB_sh_fk, LOAI_SP.TaikhoanChietkhau_sh_fk,  \n" +
						" HD.GHICHU ";
*/
				System.out.println(query);
				ResultSet rskt = db.get(query);

				if(rskt!=null)
				{
					while(rskt.next())
					{
						String loaiHD = "HDHO";
						String loaihh_fk = rskt.getString("LOAIHH");
						String solo = rskt.getString("SOLO");
						String masp = rskt.getString("MASP");
						String tensp = rskt.getString("TENSP");
						String donvi = rskt.getString("DONVI")== null ? "": rskt.getString("DONVI"); 
						String sohoadon = rskt.getString("SOHOADON"); 
						String kbh_fk = rskt.getString("KBH_FK")== null ? "": rskt.getString("KBH_FK");
						String kho_fk = rskt.getString("KHO_FK"); 
						String isNPP = rskt.getString("ISNPP");  
						String dienGiai = rskt.getString("GHICHU");

						String tiente_fk = rskt.getString("tiente_fk");
						int tygia = rskt.getInt("tigia");

						//int mauhoadon = rskt.getInt("MAUHOADON");

						double soluong = rskt.getDouble("SOLUONG");
						double chietkhau = Math.round(rskt.getDouble("CHIETKHAU"));
						double dongia = rskt.getDouble("DONGIA");
						double thuevat = rskt.getDouble("THUEVAT");

						double tienhang = Math.round(soluong * dongia)	;
						double thueGTGT = Math.round(tienhang*thuevat/100);

						String khachhang_fk = rskt.getString("KHACHHANG_FK");
						String sanpham_fk = rskt.getString("SANPHAM_FK");

						String TAIKHOAN_NO = "";
						String TAIKHOAN_CO = "";

						String loaidoituong_no = "";
						String loaidoituong_co = "";

						String madoituong_no = "";
						String madoituong_co = "";		

						String ngayghinhan = rskt.getString("NGAYXUATHD");
						String nam = ngayghinhan.substring(0, 4);
						String thang = ngayghinhan.substring(5, 7);

						String TAIKHOANHO_KH = rskt.getString("TAIKHOANHO_KH") == null ? "": rskt.getString("TAIKHOANHO_KH");
						String TAIKHOANHO_GIAVON = rskt.getString("TAIKHOANHO_GIAVON") == null ? "": rskt.getString("TAIKHOANHO_GIAVON");
						String TAIKHOANHO_DOANHTHU = rskt.getString("TAIKHOANHO_DOANHTHU") == null ? "": rskt.getString("TAIKHOANHO_DOANHTHU");
						String TAIKHOANHO_DOANHTHUBOIBO = rskt.getString("TAIKHOANHO_DOANHTHUBOIBO") == null ? "": rskt.getString("TAIKHOANHO_DOANHTHUBOIBO");
						String TAIKHOANHO_THUE = rskt.getString("TAIKHOANHO_THUE") == null ? "": rskt.getString("TAIKHOANHO_THUE");
						String TAIKHOANHO_CHIETKHAU = rskt.getString("TAIKHOANHO_CHIETKHAU") == null ? "": rskt.getString("TAIKHOANHO_CHIETKHAU");
						if(tienhang > 0)
						{
							TAIKHOAN_NO = TAIKHOANHO_KH;
							TAIKHOAN_CO = TAIKHOANHO_DOANHTHU;

							loaidoituong_no = "Khách hàng";
							madoituong_no = khachhang_fk;

							loaidoituong_co = "Sản phẩm";
							madoituong_co = sanpham_fk;

							if (TAIKHOAN_NO.trim().length() <= 0 || TAIKHOAN_CO.trim().length() <= 0) {
								msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
								rskt.close();
								db.getConnection().rollback();
								return false;
							}

							msg = util.Update_TaiKhoan_FULL_v2( db, thang, nam, ngayghinhan, ngayghinhan, "Hóa đơn tài chính", hdId, TAIKHOAN_NO, TAIKHOAN_CO, "", 
									Double.toString(tienhang), Double.toString(tienhang), loaidoituong_no, madoituong_no, loaidoituong_co, madoituong_co, "0",
									Double.toString(soluong), Double.toString(dongia), tiente_fk, Double.toString(dongia), Double.toString(tygia), 
									Double.toString(tienhang), Double.toString(tienhang), "Hóa đơn - Tiền hàng", Double.toString(thueGTGT), dienGiai , 
									sohoadon ,isNPP ,masp , tensp, donvi, kbh_fk, kho_fk, solo, "", Double.toString(tienhang), loaiHD);

							if(msg.trim().length()>0)
							{
								msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
								rskt.close();
								db.getConnection().rollback();
								return false;
							}
						}

						if(chietkhau > 0)
						{
							TAIKHOAN_NO = TAIKHOANHO_CHIETKHAU;
							TAIKHOAN_CO = TAIKHOANHO_KH;

							loaidoituong_no = "Sản phẩm";
							madoituong_no = sanpham_fk;

							loaidoituong_co = "Khách hàng";
							madoituong_co = khachhang_fk;

							if (TAIKHOAN_NO.trim().length() <= 0 || TAIKHOAN_CO.trim().length() <= 0) {
								msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
								rskt.close();
								db.getConnection().rollback();
								return false;
							}

							msg = util.Update_TaiKhoan_FULL_v2( db, thang, nam, ngayghinhan, ngayghinhan, "Hóa đơn tài chính", hdId, TAIKHOAN_NO, TAIKHOAN_CO, "", 
									Double.toString(chietkhau), Double.toString(chietkhau), loaidoituong_no, madoituong_no, loaidoituong_co, madoituong_co, "0", 
									Double.toString(soluong), Double.toString(dongia), tiente_fk, Double.toString(dongia), Double.toString(tygia), Double.toString(chietkhau), 
									Double.toString(chietkhau), "Hóa đơn - Chiết khấu", Double.toString(thueGTGT) , dienGiai , sohoadon ,isNPP ,masp , 
									tensp, donvi, kbh_fk, kho_fk, solo, "", Double.toString(tienhang), loaiHD);

							if(msg.trim().length()>0)
							{
								msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
								rskt.close();
								db.getConnection().rollback();
								return false;
							}

						}

						if(thueGTGT > 0)
						{
							TAIKHOAN_NO = TAIKHOANHO_KH;
							TAIKHOAN_CO = TAIKHOANHO_THUE;

							loaidoituong_no = "Khách hàng";
							madoituong_no = khachhang_fk;

							loaidoituong_co = "Sản phẩm";
							madoituong_co = sanpham_fk;

							if (TAIKHOAN_NO.trim().length() <= 0 || TAIKHOAN_CO.trim().length() <= 0) {
								msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
								rskt.close();
								db.getConnection().rollback();
								return false;
							}

							msg = util.Update_TaiKhoan_FULL_v2( db, thang, nam, ngayghinhan, ngayghinhan, "Hóa đơn tài chính", hdId, TAIKHOAN_NO, TAIKHOAN_CO, "", 
									Double.toString(thueGTGT), Double.toString(thueGTGT), loaidoituong_no, madoituong_no, loaidoituong_co, madoituong_co, "0", 
									Double.toString(soluong), Double.toString(dongia), tiente_fk, Double.toString(dongia), Double.toString(tygia), Double.toString(thueGTGT), 
									Double.toString(thueGTGT), "Hóa đơn - Tiền thuế", Double.toString(thueGTGT) , dienGiai , sohoadon , isNPP ,masp , tensp, donvi, kbh_fk, 
									kho_fk, solo, "", Double.toString(tienhang), loaiHD);

							if(msg.trim().length()>0)
							{
								msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
								rskt.close();
								db.getConnection().rollback();
								return false;
							}

						}					

					}
					rskt.close();
					
					query =     
						"SELECT HD.LoaiHoaDon, '' LOAIHH, HD.NPP_FK KHACHHANG_FK, \n " +      
						"(SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = '13111000' AND npp_fk = 1 ) TAIKHOANHO_KH, \n " + 
						"(SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = '52112000' AND npp_fk = 1 ) TAIKHOANHO_CHIETKHAU, \n " +
						
//						"(SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = '33311000' AND npp_fk = HD.NPP_FK ) TAIKHOANCN_THUE,  \n " +
						"(SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = '33311000' AND npp_fk = 1 ) TAIKHOANHO_THUE,  \n " +

						"'' SOLO, 0 SOLUONG, 0 DONGIA, 0 THUEVAT,  \n " +
						"NULL SANPHAM_FK, HD.NGAYXUATHD, HD.TIENTE_FK, HD.TIGIA, \n " + 
						"'' MASP, '' TENSP, '' DONVI, HD.SOHOADON, HD.KBH_FK, HD.KHO_FK, \n " +  
						"CASE WHEN ISNULL(HD.KHACHHANG_FK,0) > 0 THEN 0 ELSE 1 END AS ISNPP, HD.GHICHU, \n " +
						"ISNULL(CHIETKHAU.CK, 0) AS CHIETKHAU, ISNULL(CHIETKHAU.VAT, 0) AS VAT \n " +
						"FROM ERP_HOADON HD  \n " +
						"LEFT JOIN  \n " +
						"(  \n " +
						"	SELECT HOADON_FK, SUM(ROUND(CHIETKHAU, 0)) AS CK, SUM(ROUND(ROUND(CHIETKHAU, 0)*thueVAT/100, 0)) AS VAT  " +
						"	FROM ERP_HOADON_CHIETKHAU \n " + 
						"   WHERE HIENTHI = 1 \n " +
						"	GROUP BY HOADON_FK  \n " +
						")CHIETKHAU ON CHIETKHAU.HOADON_FK = HD.PK_SEQ \n " + 

						"LEFT JOIN NHAPHANPHOI KH ON HD.NPP_FK = KH.PK_SEQ  \n " +
						"WHERE HD.PK_SEQ  = '"+hdId+"' AND HD.LoaiHoaDon = '0'  \n " +
						"GROUP BY HD.LoaiHoaDon, HD.KHACHHANG_FK,  HD.NGAYXUATHD, HD.TIENTE_FK, HD.TIGIA, \n " +
						"HD.SOHOADON, HD.KBH_FK, HD.KHO_FK , HD.NPP_FK, HD.GHICHU, CHIETKHAU.CK, CHIETKHAU.VAT  \n " ;
						
					System.out.println(query);
					rskt = db.get(query);

					while(rskt.next())
					{
						String loaiHD = "HDHO";
						String loaihh_fk = rskt.getString("LOAIHH");
						String solo = rskt.getString("SOLO");
						String masp = rskt.getString("MASP");
						String tensp = rskt.getString("TENSP");
						String donvi = rskt.getString("DONVI")== null ? "": rskt.getString("DONVI"); 
						String sohoadon = rskt.getString("SOHOADON"); 
						String kbh_fk = rskt.getString("KBH_FK")== null ? "": rskt.getString("KBH_FK");
						String kho_fk = rskt.getString("KHO_FK"); 
						String isNPP = rskt.getString("ISNPP");  
						String dienGiai = rskt.getString("GHICHU");

						String tiente_fk = rskt.getString("tiente_fk");
						int tygia = rskt.getInt("tigia");

						//int mauhoadon = rskt.getInt("MAUHOADON");

						double soluong = rskt.getDouble("SOLUONG");
						double chietkhau = Math.round(rskt.getDouble("CHIETKHAU"));
						double thueGTGT = (-1)*rskt.getDouble("VAT");

						String khachhang_fk = rskt.getString("KHACHHANG_FK");
						String sanpham_fk = rskt.getString("SANPHAM_FK");

						String TAIKHOAN_NO = "";
						String TAIKHOAN_CO = "";

						String loaidoituong_no = "";
						String loaidoituong_co = "";

						String madoituong_no = "";
						String madoituong_co = "";		

						String ngayghinhan = rskt.getString("NGAYXUATHD");
						String nam = ngayghinhan.substring(0, 4);
						String thang = ngayghinhan.substring(5, 7);

						String TAIKHOANHO_KH = rskt.getString("TAIKHOANHO_KH") == null ? "": rskt.getString("TAIKHOANHO_KH");
//						String TAIKHOANHO_DOANHTHU = rskt.getString("TAIKHOANHO_DOANHTHU") == null ? "": rskt.getString("TAIKHOANHO_DOANHTHU");

						String TAIKHOANHO_THUE = rskt.getString("TAIKHOANHO_THUE") == null ? "": rskt.getString("TAIKHOANHO_THUE");
						String TAIKHOANHO_CHIETKHAU = rskt.getString("TAIKHOANHO_CHIETKHAU") == null ? "": rskt.getString("TAIKHOANHO_CHIETKHAU");

						if(chietkhau > 0)
						{
							TAIKHOAN_NO = TAIKHOANHO_CHIETKHAU;
							TAIKHOAN_CO = TAIKHOANHO_KH;

							loaidoituong_no = "";
							madoituong_no = "";

							loaidoituong_co = "Khách hàng";
							madoituong_co = khachhang_fk;

							if (TAIKHOAN_NO.trim().length() <= 0 || TAIKHOAN_CO.trim().length() <= 0) {
								msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
								rskt.close();
								db.getConnection().rollback();
								return false;
							}

							msg = util.Update_TaiKhoan_FULL_v2( db, thang, nam, ngayghinhan, ngayghinhan, "Hóa đơn tài chính", hdId, TAIKHOAN_NO, TAIKHOAN_CO, "", 
									Double.toString(chietkhau), Double.toString(chietkhau), loaidoituong_no, madoituong_no, loaidoituong_co, madoituong_co, "0", "0", "0", tiente_fk, "0", Double.toString(tygia), Double.toString(chietkhau), 
									Double.toString(chietkhau), "Hóa đơn - Chiết khấu", Double.toString(chietkhau) , dienGiai , sohoadon ,isNPP ,masp , tensp, donvi, kbh_fk, kho_fk, solo, "", "0", loaiHD);

							if(msg.trim().length()>0)
							{
								msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
								rskt.close();
								db.getConnection().rollback();
								return false;
							}

						}

						if(thueGTGT < 0)
						{
							TAIKHOAN_NO = TAIKHOANHO_KH;
							TAIKHOAN_CO = TAIKHOANHO_THUE;

							loaidoituong_no = "Khách hàng";
							madoituong_no = khachhang_fk;

							loaidoituong_co = "";
							madoituong_co = "";

							if (TAIKHOAN_NO.trim().length() <= 0 || TAIKHOAN_CO.trim().length() <= 0) {
								msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
								rskt.close();
								db.getConnection().rollback();
								return false;
							}

							msg = util.Update_TaiKhoan_FULL_v2( db, thang, nam, ngayghinhan, ngayghinhan, "Hóa đơn tài chính", hdId, TAIKHOAN_NO, TAIKHOAN_CO, "", 
									Double.toString(thueGTGT), Double.toString(thueGTGT), loaidoituong_no, madoituong_no, loaidoituong_co, madoituong_co, "0", "0", "0", tiente_fk, "0", Double.toString(tygia), Double.toString(thueGTGT), 
									Double.toString(thueGTGT), "Hóa đơn - Tiền thuế", Double.toString(thueGTGT) , dienGiai , sohoadon , isNPP ,masp , tensp, donvi, kbh_fk, kho_fk, solo, "", "0", loaiHD);

							if(msg.trim().length()>0)
							{
								msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
								rskt.close();
								db.getConnection().rollback();
								return false;
							}

						}					

					}
					rskt.close();
					
				}

			}

			if(noibo == 1 ) // HÓA ĐƠN NỘI BỘ
			{
				// CÀI KẾ TOÁN
				query = " SELECT HD.LoaiHoaDon,LOAI_SP.PK_SEQ LOAIHH, HD.NPP_FK KHACHHANG_FK ,  \n"+
						" (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = KH.TAIKHOANKHNB_FK AND npp_fk = 1 ) TAIKHOANHO_KH, \n"+
//						" (SELECT PK_SEQ FROM TraphacoERP.dbo.ERP_NHACUNGCAP WHERE NPP_FK = 1) NCC_FK, \n"+
						" (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = '51200000' AND npp_fk = 1 ) TAIKHOANHO_51200000, \n"+
						" (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = '33311000' AND npp_fk = 1 ) TAIKHOANHO_33311000, \n"+
						" (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = LOAI_SP.TaikhoanGiavon_sh_fk AND npp_fk = HD.NPP_FK ) TAIKHOANCN_GIAVON, \n"+
						" (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = '13311000' AND npp_fk = HD.NPP_FK ) TAIKHOANCN_13311000, \n"+
						" (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = '33610000' AND npp_fk = HD.NPP_FK ) TAIKHOANCN_33610000, \n"+
						" HD_SP.SOLUONG SOLUONG,  HD_SP.DONGIA  DONGIA, HD_SP.THUEVAT THUEVAT, \n"+
						" HD_SP.CHIETKHAU CHIETKHAU, SP.PK_SEQ SANPHAM_FK, HD.NGAYXUATHD, HD.TIENTE_FK, HD.TIGIA, \n"+
						" SP.MA MASP, SP.TEN TENSP, HD_SP.DONVITINH DONVI, HD.SOHOADON, HD.KBH_FK, HD.KHO_FK,  \n"+
						" CASE WHEN ISNULL(HD.KHACHHANG_FK,0) > 0 THEN 0 ELSE 1 END AS ISNPP,HD.GHICHU \n"+
						" FROM ERP_HOADON HD \n " +
						" INNER JOIN ERP_HOADON_SP HD_SP ON HD.PK_SEQ = HD_SP.HOADON_FK \n"+
						" INNER JOIN ERP_SANPHAM SP ON HD_SP.SANPHAM_FK = SP.PK_SEQ \n"+
						" LEFT JOIN ERP_LOAISANPHAM LOAI_SP ON SP.LOAISANPHAM_FK = LOAI_SP.PK_SEQ \n"+
						" LEFT JOIN NHAPHANPHOI KH ON HD.NPP_FK = KH.PK_SEQ \n"+
						" WHERE HD.PK_SEQ  = '"+hdId+"' \n";

				System.out.println(query);
				ResultSet rskt = db.get(query);

				if(rskt!=null)
				{
					while(rskt.next())
					{
						String loaiHD = "HDHO_NB";
						String loaihh_fk = rskt.getString("LOAIHH");
						String masp = rskt.getString("MASP");
						String tensp = rskt.getString("TENSP");
						String donvi = rskt.getString("DONVI"); 
						String sohoadon = rskt.getString("SOHOADON"); 
						String kbh_fk = rskt.getString("KBH_FK")== null ? "": rskt.getString("KBH_FK"); 
						String kho_fk = rskt.getString("KHO_FK"); 
						String isNPP = rskt.getString("ISNPP"); 
	//					String ncc_fk = rskt.getString("NCC_FK"); 
						String dienGiai =rskt.getString("GHICHU");
						String tiente_fk = rskt.getString("tiente_fk");
						int tygia = rskt.getInt("tigia");

						double soluong = rskt.getDouble("SOLUONG");
						double chietkhau = Math.round(rskt.getDouble("CHIETKHAU"));
						double dongia = rskt.getDouble("DONGIA");
						double thuevat = rskt.getDouble("THUEVAT");

						double tienhang = Math.round(soluong * dongia)	;
						double thueGTGT = Math.round(tienhang*thuevat/100);

						String khachhang_fk = rskt.getString("KHACHHANG_FK");
						String sanpham_fk = rskt.getString("SANPHAM_FK");

						String TAIKHOAN_NO = "";
						String TAIKHOAN_CO = "";

						String loaidoituong_no = "";
						String loaidoituong_co = "";

						String madoituong_no = "";
						String madoituong_co = "";		

						String ngayghinhan = rskt.getString("NGAYXUATHD");
						String nam = ngayghinhan.substring(0, 4);
						String thang = ngayghinhan.substring(5, 7);

						String TAIKHOANHO_KH = rskt.getString("TAIKHOANHO_KH") == null ? "": rskt.getString("TAIKHOANHO_KH");
						String TAIKHOANHO_51200000 = rskt.getString("TAIKHOANHO_51200000") == null ? "": rskt.getString("TAIKHOANHO_51200000");
						String TAIKHOANHO_33311000 = rskt.getString("TAIKHOANHO_33311000") == null ? "": rskt.getString("TAIKHOANHO_33311000");
						String TAIKHOANCN_GIAVON = rskt.getString("TAIKHOANCN_GIAVON") == null ? "": rskt.getString("TAIKHOANCN_GIAVON");
						String TAIKHOANCN_13311000 = rskt.getString("TAIKHOANCN_13311000") == null ? "": rskt.getString("TAIKHOANCN_13311000");
						String TAIKHOANCN_33610000 = rskt.getString("TAIKHOANCN_33610000") == null ? "": rskt.getString("TAIKHOANCN_33610000");

						{ // HO
							if(tienhang > 0)
							{
								TAIKHOAN_NO = TAIKHOANHO_KH;
								TAIKHOAN_CO = TAIKHOANHO_51200000;

								loaidoituong_no = "Khách hàng";
								madoituong_no = khachhang_fk;

								loaidoituong_co = "Sản phẩm";
								madoituong_co = sanpham_fk;

								if (TAIKHOAN_NO.trim().length() <= 0 || TAIKHOAN_CO.trim().length() <= 0) {
									msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
									rskt.close();
									db.getConnection().rollback();
									return false;
								}

								msg = util.Update_TaiKhoan_FULL_v2( db, thang, nam, ngayghinhan, ngayghinhan, "Hóa đơn tài chính", hdId, TAIKHOAN_NO, TAIKHOAN_CO, "", 
										Double.toString(tienhang), Double.toString(tienhang), loaidoituong_no, madoituong_no, loaidoituong_co, madoituong_co, "0", 
										Double.toString(soluong), Double.toString(dongia), tiente_fk, Double.toString(dongia), Double.toString(tygia), Double.toString(tienhang), 
										Double.toString(tienhang), "Hóa đơn - Tiền hàng", Double.toString(thueGTGT) , dienGiai , sohoadon ,isNPP ,masp , tensp, donvi, kbh_fk, 
										kho_fk, "", "", Double.toString(tienhang),loaiHD);

								if(msg.trim().length()>0)
								{
									msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
									rskt.close();
									db.getConnection().rollback();
									return false;
								}

							}

							if(thueGTGT > 0)
							{
								TAIKHOAN_NO = TAIKHOANHO_KH;
								TAIKHOAN_CO = TAIKHOANHO_33311000;

								loaidoituong_no = "Khách hàng";
								madoituong_no = khachhang_fk;

								loaidoituong_co = "Sản phẩm";
								madoituong_co = sanpham_fk;

								if (TAIKHOAN_NO.trim().length() <= 0 || TAIKHOAN_CO.trim().length() <= 0) {
									msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.1";
									rskt.close();
									db.getConnection().rollback();
									return false;
								}

								msg = util.Update_TaiKhoan_FULL_v2( db, thang, nam, ngayghinhan, ngayghinhan, "Hóa đơn tài chính", hdId, TAIKHOAN_NO, TAIKHOAN_CO, "", 
										Double.toString(thueGTGT), Double.toString(thueGTGT), loaidoituong_no, madoituong_no, loaidoituong_co, madoituong_co, "0", 
										Double.toString(soluong), Double.toString(dongia), tiente_fk, Double.toString(dongia), Double.toString(tygia), Double.toString(thueGTGT), 
										Double.toString(thueGTGT), "Hóa đơn - Tiền thuế", Double.toString(thueGTGT) , dienGiai , sohoadon , isNPP ,masp , tensp, donvi, kbh_fk, 
										kho_fk, "", "", Double.toString(tienhang), loaiHD);

								if(msg.trim().length()>0)
								{
									msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.2";
									rskt.close();
									db.getConnection().rollback();
									return false;
								}

							}								
						}

					}
					rskt.close();
				}
			}			

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			this.msg = "Exception: " + e.getMessage();
			e.printStackTrace();
			db.update("rollback");
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

	
	public String getNguoimua() 
	{
		return this.nguoimua;
	}

	
	public void setNguoimua(String nguoimua) {
		this.nguoimua = nguoimua;
		
	}


	public String getLoaiXHD() {
		
		return this.loaiXHD;
	}

	public void setLoaiXHD(String loaiXHD) {
		this.loaiXHD= loaiXHD;
		
	}

	
	public String[] getSpVat() {
		
		return this.spVat;
	}

	
	public void setSpVat(String[] spVat) {
		 this.spVat = spVat;
		
	}

	
	public void loadContents()
	{
		String query ="";
		Utility util = new Utility();

		query = "select PK_SEQ, MA + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1'  ";
		this.nppRs = db.get(query);
		
		if(this.nppId.trim().length() > 0 )
		{			
			query =   " select PK_SEQ , NgayDonHang  " +
					" from ERP_DONDATHANG " +
					" where NPP_FK = '"+ this.nppId +"' " +
					" and pk_seq not in(select a.DDH_FK from  ERP_HOADON_DDH a inner join ERP_HOADON b on a.HOADON_FK=b.PK_SEQ where b.TRANGTHAI in ( 1, 2, 4 ) and b.pk_seq != " + ( this.id.trim().length() > 0 ? this.id : "-1" ) + " )  " +
					" order by NgayDonHang desc ";
		  	System.out.println("Câu query "+query);		
			this.ddhRs = db.get(query);
		}
		
		String chuoi = this.ddhId;
		if(chuoi.length() > 0)
		{	
			// INIT TONG TIEN VAT
		  try 
		   {
				NumberFormat formater = new DecimalFormat("##,###,###");
			    
			 query = "select b.PK_SEQ as SPID, b.MA, b.TEN, DV.donvi, a.dongia , "+
				    "  isnull(scheme,' ') as scheme , isnull(a.thuevat,0) as vat ,   " +
				    "  SUM( a.soluong)  as soluong, SUM( isnull(a.chietkhau, 0)) as chietkhau "+
				    "from ERP_DONDATHANG_SANPHAM a inner Join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ   "+  	 
				    " INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.DVDL_FK  " +
				    " inner join  ERP_DONDATHANG c on a.dondathang_fk=c.pk_seq    "+
				    "where a.dondathang_fk in ( "+ chuoi +" ) and a.dondathang_fk in (select pk_seq from ERP_DONDATHANG where NPP_FK="+ this.nppId +")  " +
				    "group by b.PK_SEQ , b.MA, b.TEN, DV.donvi, a.dongia , isnull(scheme,' ') , isnull(a.thuevat,0) order by b.ma ";
			 
		    System.out.println("Lấy sản phẩm: "+query);
		    ResultSet rsLaySP = db.get(query);
		
		    
		    	String spID = "";
				String spMA = "";
				String spTEN = "";
				String spDONVI = "";
				String spSOLUONG = "";
				String spGIANHAP = "";
				String spCHIETKHAU = "";
				String spSCHEME = "";
				String spVAT = "";
				String spTIENTHUE = "";
			
		    	
			    if(rsLaySP!= null)
			    {				    	
					while(rsLaySP.next())
					{
						spID += rsLaySP.getString("SPID") + "__";
						spMA += rsLaySP.getString("MA") + "__";
						spTEN += rsLaySP.getString("TEN") + "__";
						spDONVI += rsLaySP.getString("DONVI") + "__";
						spSOLUONG += (rsLaySP.getDouble("SOLUONG")) + "__";
						spGIANHAP += (rsLaySP.getDouble("DONGIA")) + "__";
						spCHIETKHAU += (rsLaySP.getDouble("chietkhau")) + "__";
						spSCHEME += rsLaySP.getString("scheme") + "__";
						spVAT +=  (rsLaySP.getDouble("vat")) + "__";
						
						//spTIENTHUE +=  Math.round( ( Math.round( rsLaySP.getDouble("SOLUONG") * rsLaySP.getDouble("DONGIA") ) - rsLaySP.getDouble("chietkhau") ) * rsLaySP.getDouble("vat") / 100 ) + "__";
						spTIENTHUE +=  Math.round( Math.round( rsLaySP.getDouble("SOLUONG") * rsLaySP.getDouble("DONGIA") - rsLaySP.getDouble("chietkhau")) * ( rsLaySP.getDouble("vat") / 100 ) ) + "__";
					}
					rsLaySP.close();
					
					if(spMA.trim().length() > 0)
					{
						spID = spID.substring(0, spID.length() - 2);
						this.spId = spID.split("__");
						
						spMA = spMA.substring(0, spMA.length() - 2);
						this.spMa = spMA.split("__");
						
						spTEN = spTEN.substring(0, spTEN.length() - 2);
						this.spTen = spTEN.split("__");
						
						spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
						this.spDonvi = spDONVI.split("__");
						
						spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
						this.spSoluong = spSOLUONG.split("__");
						
						spGIANHAP = spGIANHAP.substring(0, spGIANHAP.length() - 2);
						this.spDongia = spGIANHAP.split("__");
						
						spCHIETKHAU = spCHIETKHAU.substring(0, spCHIETKHAU.length() - 2);
						this.spChietkhau = spCHIETKHAU.split("__");
						
						spSCHEME = spSCHEME.substring(0, spSCHEME.length() - 2);
						this.spSCheme = spSCHEME.split("__");
						
						spVAT = spVAT.substring(0, spVAT.length() - 2);
						this.spVat = spVAT.split("__");
						
						spTIENTHUE = spTIENTHUE.substring(0, spTIENTHUE.length() - 2);
						this.spTienthue = spTIENTHUE.split("__");
						
					}
			    }	
			} 
		  catch (SQLException e) 
		  {
				e.printStackTrace();
			}
		}
	
	}
	
	public String getTongtienBVAT() {
		
		return this.bvat;
	}

	
	public void setTongtienBVAT(String bvat) {
		
		this.bvat = bvat;
	}

	
	public String getTongCK() {
		
		return this.totalCHIETKHAU;
	}

	
	public void setTongCK(String tongCK) {
		
		this.totalCHIETKHAU = tongCK;
	}

	
	public String getTongVAT() {
		
		return this.thueVAT;
	}

	
	public void setTongVAT(String vat) {
		
		this.thueVAT = vat;
	}

	
	public String getTongtienAVAT() {
		
		return this.avat;
	}

	
	public void setTongtienAVAT(String avat) {
		
		this.avat = avat;
	}

	
	public String[] getSpTienthue() {

		return this.spTienthue;
	}


	public void setSpTienthua(String[] spTienthue) {
		
		this.spTienthue = spTienthue;
	}

	Hashtable<String, String> sanpham_soluong= new Hashtable<String, String>();
	
	public Hashtable<String, String> getSanpham_Soluong() {
		
		return this.sanpham_soluong;
	}

	
	public void setSanpham_Soluong(Hashtable<String, String> sp_soluong) {
		
		this.sanpham_soluong = sp_soluong;
	}
	
	public ResultSet getSoloTheoSp(String spMa, String donvi, String tongluong)
	{
		tongluong = tongluong.replaceAll(",", "");
		//System.out.println("---TONG LUONG: " + tongluong );
		
		String soloDACHON = " select '1' as soloCHON, '' as ngayhethanCHON,'' as ngaynhapkhoCHON, '' as vitriCHON, '' mathungCHON, '' mameCHON,'' as maphieuCHON, '' marqCHON,'' as nsxCHON, 0 as soluongDACHON ";
		
		String sqlDONHANG = "";
		if( this.id.trim().length() > 0 )
			sqlDONHANG = " select SUM(soluong) from ERP_HOADON_SP_CHITIET where hoadon_fk = '" + this.id + "' and SANPHAM_FK = DT.sanpham_fk and solo = DT.solo and ngayhethan = DT.ngayhethan and mathung = DT.mathung and mame = DT.mame and maphieu = DT.maphieu and isnull( bin_fk, 0 ) = isnull( DT.bin_fk, 0 ) and marq = DT.marq and isnull( nsx_fk, 0 ) = isnull( DT.nsx_fk, 0 )   ";
		else
			sqlDONHANG = " select SUM(soluong) from ERP_HOADON_SP_CHITIET where hoadon_fk = '1' and SANPHAM_FK = DT.sanpham_fk and solo = DT.solo and ngayhethan = DT.ngayhethan and mathung = DT.mathung and mame = DT.mame and maphieu = DT.maphieu and  isnull( bin_fk, 0 ) = isnull( DT.bin_fk, 0 ) and marq = DT.marq and isnull( nsx_fk, 0 ) = isnull( DT.nsx_fk, 0 ) ";
		
		String query = "";
		
		query =  "\n select DT.SOLO, DT.NGAYHETHAN,DT.NGAYNHAPKHO, isnull(bin.MA, '') as vitri, DT.MATHUNG, DT.MAME, DT.MAPHIEU, isnull(nsx.MA, '') as nsx, DT.MARQ,  " + 
				 "\n DT.AVAILABLE + isnull( ( " + sqlDONHANG + " ), 0) - isnull(DAXUAT.soluongDACHON, 0) as AVAILABLE, '' as tudexuat  "+
				 "\n from "+
				 "\n ( "+
				 "\n 	select ct.sanpham_fk, ct.SOLO, ct.NGAYHETHAN,CT.NGAYNHAPKHO, ct.mathung, ct.mame, ct.maphieu, isnull( ct.bin_fk, 0 ) as bin_fk, ct.marq, isnull( ct.nsx_fk, 0 ) as nsx_fk, sum(ct.AVAILABLE) as AVAILABLE  "+
				 "\n 	from ERP_KHOTT_SP_CHITIET ct inner join ERP_SANPHAM sp on ct.sanpham_fk = sp.pk_seq  "+
				 "\n 	where KHOTT_FK = '" + this.khoNhanId + "' and SANPHAM_FK = '" + spMa + "'   " +
				 		"  and ngaynhapkho <= '" + this.ngayxuatHD + "' "+
				 		"  and ( isnull(sp.batbuockiemdinh, 0) = 0 or ct.KHOTT_FK in (100023, 100058) or ( sp.batbuockiemdinh = 1 and isnull( maphieu, '' ) != ''  ) )		" +
				 "\n	group by ct.sanpham_fk, ct.SOLO, ct.NGAYHETHAN, CT.NGAYNHAPKHO, ct.mathung, ct.mame,ct.maphieu,  isnull( ct.bin_fk, 0 ), ct.marq, isnull( ct.nsx_fk, 0 )	" +
				 "\n ) "+
				 "\n DT left join ERP_BIN bin on DT.bin_fk = bin.pk_seq "+
				 "\n left join ERP_NHASANXUAT nsx on DT.nsx_fk = nsx.pk_seq left join  "+
				 "\n ( "+
				 	soloDACHON +
				 "\n ) "+
				 "\n DAXUAT on DT.SOLO = DAXUAT.soloCHON and DT.NGAYHETHAN = DAXUAT.ngayhethanCHON and DT.NGAYNHAPKHO = DAXUAT.ngaynhapkhoCHON and isnull( bin.ma, '' ) = DAXUAT.vitriCHON "+
				 "\n		and DT.MATHUNG = DAXUAT.mathungCHON and DT.MAME = DAXUAT.mameCHON and DT.MAPHIEU = DAXUAT.maphieuCHON and DT.marq = DAXUAT.marqCHON and isnull( nsx.ma, '' ) = DAXUAT.nsxCHON " +
				 "\n where DT.AVAILABLE + isnull( ( " + sqlDONHANG + " ), 0) - isnull(DAXUAT.soluongDACHON, 0) > 0 "+
				 "\n order by NGAYHETHAN asc, NGAYNHAPKHO ASC  ";
		
		System.out.println("----LAY SO LO ( " + spMa + " ): " + query );
		String query2 = "";
		ResultSet rs = db.get(query);
		try 
		{
			double total = 0;
			while(rs.next())
			{
				double slg = 0;
				double avai = rs.getDouble("AVAILABLE");
				
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
					query2 += "select '" + avai + "' as AVAILABLE, '" + rs.getString("SOLO") + "' as SOLO, '" + rs.getString("NGAYHETHAN") + "' as NGAYHETHAN, '" + rs.getString("VITRI") + "' as VITRI, '" + slg + "' as tuDEXUAT,'" + rs.getString("MATHUNG") + "' as MATHUNG, '" + rs.getString("MAME") + "' as MAME, '" + rs.getString("MAPHIEU") + "' as MAPHIEU, '" + rs.getString("MARQ") + "' as MARQ, '" + rs.getString("NSX") + "' as NSX ,'" + rs.getString("NGAYNHAPKHO") + "' as NGAYNHAPKHO union ALL ";
					
				}
				else
				{
					query2 += "select '" + avai + "' as AVAILABLE, '" + rs.getString("SOLO") + "' as SOLO, '" + rs.getString("NGAYHETHAN") + "' as NGAYHETHAN, '" + rs.getString("VITRI") + "' as VITRI, '' as tuDEXUAT,'" + rs.getString("MATHUNG") + "' as MATHUNG, '" + rs.getString("MAME") + "' as MAME, '" + rs.getString("MAPHIEU") + "' as MAPHIEU, '" + rs.getString("MARQ") + "' as MARQ, '" + rs.getString("NSX") + "' as NSX ,'" + rs.getString("NGAYNHAPKHO") + "' as NGAYNHAPKHO union ALL ";
				}
			}
			rs.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("EXCEPTION INIT SOLO: " + e.getMessage());
		}
		
		/*String query2 = "";
		NodeList rs = WebService.ExecQueryFromERP(query, "geso@@tra@@90123");
		try 
		{
			double total = 0;
			for( int i = 0; i < rs.getLength(); i++ ) 
			{
				Element element = (Element) rs.item(i);
				
				double slg = 0;
				double avai = Double.parseDouble( WebService.getValues( element, "AVAILABLE" ) );
				String solo = WebService.getValues( element, "SOLO" );
				String ngayhethan = WebService.getValues( element, "NGAYHETHAN" );
				String vitri = WebService.getValues( element, "vitri" );
				String mathung = WebService.getValues( element, "MATHUNG" );
				String mame = WebService.getValues( element, "MAME" );
				
				if(this.id.trim().length() <= 0 )
				{
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
						query2 += "select '" + avai + "' as AVAILABLE, '" + solo + "' as SOLO, '" + ngayhethan + "' as NGAYHETHAN, '" + vitri + "' as VITRI, '" + mathung + "' as mathung, '" + mame + "' as mame, '" + slg + "' as tuDEXUAT union ALL ";
					}
					else
					{
						query2 += "select '" + avai + "' as AVAILABLE, '" + solo + "' as SOLO, '" + ngayhethan + "' as NGAYHETHAN, '" + vitri + "' as VITRI, '" + mathung + "' as mathung, '" + mame + "' as mame, '' as tuDEXUAT union ALL ";
					}
				}
				else //Khi vào cập nhật thì không đề xuất lại nữa
				{
					query2 += "select '" + avai + "' as AVAILABLE, '" + solo + "' as SOLO, '" + ngayhethan + "' as NGAYHETHAN, '" + vitri + "' as VITRI, '" + mathung + "' as mathung, '" + mame + "' as mame, '' as tuDEXUAT union ALL ";
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("EXCEPTION INIT SOLO: " + e.getMessage());
		}
		*/
		if(query2.trim().length() > 0)
		{
			query2 = query2.substring(0, query2.length() - 10);
			System.out.println("---TU DONG DE XUAT BIN - LO: " + query2 );
			return db.get(query2);
		}
		
		return null;
	}
	

	public ResultSet getSoloTheoSpDisplay(String spMa, String donvi, String tongluong)
	{
		String query = "select 0 as AVAILABLE, SOLO, NGAYHETHAN, ISNULL( ( select ten from ERP_BIN where pk_seq = a.bin_fk ), '' ) as VITRI, " + 
					   " 	sum( SOLUONG ) as soluong " + 
					   "from ERP_HOADON_SP_CHITIET a " + 
					   "where hoadon_fk = '" + this.id + "' and  MA = N'" + spMa + "'  " + 
					   "group by SOLO, NGAYHETHAN, BIN_FK ";
				
		System.out.println("---TU DONG DE LO: " + query );
		return db.get(query);
	}
	
	
	String kbhId;
	ResultSet kbhRs;

	public String getKbhId() {
		return kbhId;
	}

	public void setKbhId(String kbhId) {
		this.kbhId = kbhId;
	}

	public ResultSet getKbhRs() {
		return kbhRs;
	}

	public void setKbhRs(ResultSet kbhRs) {
		this.kbhRs = kbhRs;
	}
	
	String dungchungKENH;

	public String getDungchungKENH() {
		return dungchungKENH;
	}

	public void setDungchungKENH(String dungchungKENH) {
		this.dungchungKENH = dungchungKENH;
	}
	
	public String getNOIBO() {

		return this.noibo;
	}


	public void setNOIBO(String noibo) {
		
		this.noibo = noibo;
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
		String query = "";
		
		query = " select diengiai, chietkhau, thueVAT, STT, tichluyQUY,tienavat,tienvat " +
				" from ERP_HOADON_CHIETKHAU where hoadon_fk = '" + this.id + "' and HIENTHI = '1' order by STT, tichluyQUY ";
		
				
		System.out.println("---INIT TICH LUY: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			String schemeMa = "";
			String schemeVAT = "";
			String schemeTt = "";
			String schemeTvat = "";
			String schemeTavat = "";
			
			try 
			{
				NumberFormat format = new DecimalFormat("#,###,###.###");
				while(rs.next())
				{
					schemeMa += rs.getString("diengiai") + "__";
					schemeVAT += rs.getString("thueVAT") + "__";
					schemeTt += format.format( rs.getDouble("chietkhau")) + "__";
					schemeTvat += format.format( rs.getDouble("tienvat")) + "__";
					schemeTavat += format.format( rs.getDouble("tienavat")) + "__";
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
					
					
					schemeTavat = schemeTavat.substring(0, schemeTavat.length() - 2);
					this.tichluy_tienavat = schemeTavat.split("__");
					
					schemeTvat = schemeTvat.substring(0, schemeTvat.length() - 2);
					this.tichluy_tienvat = schemeTvat.split("__");
				}
			} 
			catch (Exception e) 
			{
				System.out.println("__EXCEPTION: " + e.getMessage());
				e.printStackTrace();
			}
		}	
	}


	public String TaoXuatKhoTuDong() 
	{
		try
		{
			//db.getConnection().setAutoCommit(false);
			
			String query = "select count(*) as sl from ERP_YCXUATKHO_DDH where hoadon_fk = " + this.id;
			ResultSet rs = db.get(query);
			rs.next();
			if(rs.getInt("sl")!=0)
			{
				this.msg = "Hóa đơn này đã tạo PXK rùi, không tạo thêm nữa";
				//db.getConnection().rollback();
				return this.msg;
			}
				
			String ddhId = "";
			query = "select ddh_fk from erp_hoadon_ddh where hoadon_fk = " + this.id;
			rs = db.get(query);
			rs.next();
			ddhId = rs.getString("ddh_fk");
			rs.close();
 
			query = " insert ERP_YCXUATKHO(NgayYeuCau, ghichu, trangthai, npp_fk, kho_fk, xuatcho, ngaytao, nguoitao, ngaysua, nguoisua) " +
						   " select top(1) ngayxuatHD, N'Xuất kho tự động của hóa đơn: ', '0', npp_fk, kho_fk, b.isKHACHHANG, " +
						   		" '" + getDateTime() + "', " + this.userId + ", '" + getDateTime() + "', " + this.userId + " " +
						   " from ERP_HOADON a inner join NHAPHANPHOI b on a.npp_fk = b.pk_seq " + 
						   " where a.pk_seq = '" + this.id + "' ";
			
			System.out.println("1.Insert ERP_YCXUATKHO: " + query);
			if( db.updateReturnInt(query) <= 0 )
			{
				this.msg = "Không thể tạo mới ERP_YCXUATKHO " + query;
				//db.getConnection().rollback();
				return this.msg;
			}
			
			//LAY ID
			ResultSet rsDDH = db.get("select IDENT_CURRENT('ERP_YCXUATKHO') as ID ");
			String xkId = "";
			if(rsDDH.next())
			{
				xkId = rsDDH.getString("ID");
			}
			rsDDH.close();

			query = "Insert ERP_YCXUATKHO_DDH(ycxk_fk, hoadon_fk,DDH_FK) " +
					"select '" + xkId + "', HD.pk_seq,DDH.DDH_FK from ERP_HOADON  HD INNER JOIN ERP_HOADON_DDH DDH ON DDH.HOADON_FK = HD.PK_SEQ where HD.pk_seq in ( " + this.id + " )  ";
			System.out.println("2.chen ERP_YCXUATKHO: " + query);
			if( db.updateReturnInt(query) <= 0 )
			{
				this.msg = "Không thể tạo mới ERP_YCXUATKHO_DDH " + query;
				//db.getConnection().rollback();
				return this.msg;
			}

			query = "insert ERP_YCXUATKHO_SANPHAM( ycxk_fk, sanpham_fk, soluongDAT, tonkho, daxuat, soluongXUAT, LOAI, SCHEME, soluong_dvban, kho_fk ) " +
					//"select '" + xkId + "', sanpham_fk, soluong_chuan as soluongDAT, 0 tonkho, 0 daxuat, soluong as soluongXUAT, 0 LOAI, ' ' SCHEME, soluong " +
					//"from ERP_HOADON_SP where hoadon_fk = '" + this.id + "' ";
					"select '" + xkId + "', sanpham_fk, soluong as soluongDAT, 0 tonkho, 0 daxuat, soluong as soluongXUAT, 0 LOAI, ' ' SCHEME, soluong, kho_fk " + 
					"from erp_dondathang_sanpham where dondathang_fk = '" + ddhId + "' " +
					"union all " +
					"select '" + xkId + "', b.pk_seq, soluong as soluongDAT, 0 tonkho, 0 daxuat, soluong as soluongXUAT, 1 LOAI, c.SCHEME, soluong, a.kho_fk " + 
					"from erp_dondathang_ctkm_trakm a inner join erp_sanpham b on a.spma = b.ma " +
					"inner join ctkhuyenmai c on a.ctkmid = c.pk_seq " +
					"where dondathangid = '" + ddhId + "' and isnull(spma, '') <> ''";
			
			System.out.println("1.1.Insert YCXK - SP: " + query);
			if( db.updateReturnInt(query) <= 0 )
			{
				this.msg = "Khong the tao moi ERP_YCXUATKHO_SANPHAM: " + query;
				//db.getConnection().rollback();
				return this.msg;
			}
				
			query = "insert ERP_YCXUATKHO_SANPHAM_CHITIET( ycxk_fk, SANPHAM_FK, scheme, solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, MAPHIEU, MARQ, HAMLUONG, HAMAM, " +
					"bin_fk, nsx_fk, phieudt, phieueo, soluong, soluong_dvban, kho_fk ) "+
					"select '" + xkId + "' ycxk_fk, a.SANPHAM_FK, isnull(scheme, ' ') scheme, solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, MAPHIEU, MARQ, HAMLUONG, " + 
					"HAMAM, bin_fk, nsx_fk, phieudt, phieueo, soluong, soluong, kho_fk " + 
					"from erp_dondathang_sanpham_chitiet a " +  
					"where a.dondathang_fk in ( " + ddhId + " )";
			
			System.out.println("1.2.Insert YCXK - SP - CT: " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the tao moi ERP_YCXUATKHO_SANPHAM_CHITIET: " + query;
				//db.getConnection().rollback();
				return this.msg;
			}
								
			//db.getConnection().commit();
			//db.getConnection().setAutoCommit(true);
			
			//CẬP NHẬT TOOLTIP
			db.execProceduce2("CapNhatTooltip_XuatKho", new String[]{ xkId } );
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			this.msg = "Exception: " + e.getMessage();
			return e.getMessage();
		}
		
		return "";
		
	}
	
	
	public String TaoLaiXuatKho( dbutils db, String hdId, String userId ) 
	{
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = " insert ERP_YCXUATKHO(NgayYeuCau, ghichu, trangthai, npp_fk, kho_fk, xuatcho, ngaytao, nguoitao, ngaysua, nguoisua) " +
						   " select top(1) ngayxuatHD, N'Xuất kho tự động của hóa đơn: ', '', npp_fk, kho_fk, b.isKHACHHANG, " +
						   		" '" + getDateTime() + "', '" + userId + "', '" + getDateTime() + "', '" + userId + "' " +
						   " from ERP_HOADON a inner join NHAPHANPHOI b on a.npp_fk = b.pk_seq " + 
						   " where a.pk_seq = '" + hdId + "' ";
			
			System.out.println("1.Insert ERP_YCXUATKHO: " + query);
			if( db.updateReturnInt(query) <= 0 )
			{
				this.msg = "Không thể tạo mới ERP_YCXUATKHO " + query;
				db.getConnection().rollback();
				return this.msg;
			}
			
			//LAY ID
			ResultSet rsDDH = db.get("select IDENT_CURRENT('ERP_YCXUATKHO') as ID ");
			String xkId = "";
			if(rsDDH.next())
			{
				xkId = rsDDH.getString("ID");
			}
			rsDDH.close();

			query = "Insert ERP_YCXUATKHO_DDH(ycxk_fk, hoadon_fk,DDH_FK) " +
					"select '" + xkId + "',  HD.pk_seq,DDH.DDH_FK from ERP_HOADON  HD INNER JOIN ERP_HOADON_DDH DDH ON DDH.HOADON_FK = HD.PK_SEQ where HD.pk_seq in ( " + hdId + " )  ";
			System.out.println("2.chen ERP_YCXUATKHO: " + query);
			if( db.updateReturnInt(query) <= 0 )
			{
				this.msg = "Không thể tạo mới ERP_YCXUATKHO_DDH " + query;
				db.getConnection().rollback();
				return this.msg;
			}

			query = "insert ERP_YCXUATKHO_SANPHAM( ycxk_fk, sanpham_fk, soluongDAT, tonkho, daxuat, soluongXUAT, LOAI, SCHEME, soluong_dvban ) " +
					"select '" + xkId + "', sanpham_fk, soluong_chuan as soluongDAT, 0 tonkho, 0 daxuat, soluong as soluongXUAT, 0 LOAI, ' ' SCHEME, soluong " +
					"from ERP_HOADON_SP where hoadon_fk = '" + hdId + "' ";
			
			System.out.println("1.1.Insert YCXK - SP: " + query);
			if( db.updateReturnInt(query) <= 0 )
			{
				this.msg = "Khong the tao moi ERP_YCXUATKHO_SANPHAM: " + query;
				db.getConnection().rollback();
				return this.msg;
			}
				
			query =  " insert ERP_YCXUATKHO_SANPHAM_CHITIET( ycxk_fk, SANPHAM_FK, scheme, solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, MAPHIEU, MARQ, HAMLUONG, HAMAM, bin_fk, phieudt, phieueo, soluong, soluong_dvban ) "+
					 " select '" + xkId + "' ycxk_fk, b.PK_SEQ as SANPHAM_FK, ' ' scheme, solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, MAPHIEU, MARQ, HAMLUONG, HAMAM, bin_fk, phieudt, phieueo, soluong_CHUAN, soluong  "+
					 " from ERP_HOADON_SP_CHITIET a inner join ERP_SANPHAM b on a.ma = b.MA  "+
					 " where a.hoadon_fk in ( " + hdId + " ) ";
			
			System.out.println("1.2.Insert YCXK - SP - CT: " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the tao moi ERP_YCXUATKHO_SANPHAM_CHITIET: " + query;
				db.getConnection().rollback();
				return this.msg;
			}
				
			//HOA DON CHI XUAT 1 LAN THOI
			query = " UPDATE ERP_HOADON set hoantat = '1' where pk_seq in ( select hoadon_fk from ERP_YCXUATKHO_DDH where ycxk_fk = '" + xkId + "' ) ";
			if(!db.update(query))
			{
				msg = "Không thể chốt ERP_YCXUATKHO " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			//CAP NHAT TRANG THAI DON HANG
			query = " update ERP_DONDATHANG set daxuatkho = '1' " + 
					" where pk_seq in (  select DDH_FK from ERP_HOADON_DDH where hoadon_fk in ( select hoadon_fk from ERP_YCXUATKHO_DDH where ycxk_fk = '" + xkId + "' ) )";
			if(!db.update(query))
			{
				msg = "Không tạo mới ERP_DONDATHANG " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			//CẬP NHẬT TOOLTIP
			db.execProceduce2("CapNhatTooltip_XuatKho", new String[]{ xkId } );
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			this.msg = "Exception: " + e.getMessage();
			return e.getMessage();
		}
		
		return "";
		
	}
	

}
