package geso.traphaco.erp.beans.lenhsanxuatgiay.imp;

import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.Utility_Kho;
import geso.traphaco.erp.beans.danhmucvattu.IDanhmucvattu;
import geso.traphaco.erp.beans.danhmucvattu.IDanhmucvattu_SP;
import geso.traphaco.erp.beans.danhmucvattu.imp.Danhmucvattu;
import geso.traphaco.erp.beans.danhmucvattu.imp.Danhmucvattu_SP;
import geso.traphaco.erp.beans.lapkehoach.IErpBom;
import geso.traphaco.erp.beans.lapkehoach.IErpDinhmuc;
import geso.traphaco.erp.beans.lapkehoach.imp.ErpBom;
import geso.traphaco.erp.beans.lapkehoach.imp.ErpDinhmuc;
import geso.traphaco.erp.beans.lenhsanxuatgiay.IErpLenhsanxuat;
import geso.traphaco.erp.beans.lenhsanxuatgiay.IErpTieuhaonguyenlieu;
import geso.traphaco.erp.beans.lenhsanxuatgiay.ILenhSXCongDoan;
import geso.traphaco.erp.beans.lenhsanxuatgiay.ISanPhamSanXuat;
import geso.traphaco.erp.beans.lenhsanxuatgiay.ISpSanxuatChitiet;
import geso.traphaco.erp.beans.phieuxuatkho.ISpDetail;
import geso.traphaco.erp.beans.phieuxuatkho.imp.SpDetail;
import geso.traphaco.erp.beans.sohoadon.imp.Sohoadon;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.Lenhsanxuat;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.util.Kho_Lib;
import geso.traphaco.erp.util.Library;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.xml.soap.DetailEntry;

import com.itextpdf.text.log.SysoCounter;

public class ErpTieuhaonguyenlieu implements IErpTieuhaonguyenlieu
{
	private static final long serialVersionUID = 1L;
	String congtyId;
	String userId;
	String id;
	String ngaytao;
	String ngaydukien;
	String loaisanphamTH;
	String Ghichu;
	String soluongsx;
	String dvtBOM;
	String PO;
	String msg;
	String trangthai;
	String quatrinh1;
	String quatrinh2;
	String dang1;
	String dang2;
	String dang3;
	String SKN1;
	String SKN2;
	String SKN3;
	String khoId;
	String SoLSX;
	ResultSet khoRs;
	boolean is_init=false;
	ResultSet spRs;
	ResultSet RsKhoTp;
	ResultSet loaisanphamRs;
	ResultSet RsKhoLayNL;
	ResultSet RsKhoSX;
	
	String spId;
	String spMa;
	String soPoKH;
	String nppId;
	
	ResultSet lohoiRs;
	String lohoiId;
	
	ResultSet kbsxRs;
	String kbsxId;
	
	String IdNhapkho;

	String congdoanCurrent;
	String tencongdoanCurrent;
	ResultSet nhamayRs;
	ResultSet RsCongDoan;
	ResultSet RsDvkd;
	ResultSet rsNhanHangSPMoi;
	ResultSet rsTieuhaoSPMoi;
	ResultSet rsKiemDinhSPMoi;
	ResultSet rsYeuCauChuyenKho;
	
	ResultSet rsBOM;
	String bomId;
 
	String CoKiemDinhCL_SPmoi="";
	
	String nhamayId;
	
	String viewBom;
	String soluongchuan;
	String IsLsxCongNghe;
	String cpTT;
	boolean VuotDungSai;
	boolean IsChangeBom=false;
	
	String ngaytieuhao;
	String solenhsanxuat;
	String KhosxId="";
	
	String lsxIds="";
	ResultSet lsxRs;
	
	String CdsxId="";
	ResultSet CdsxRs;
	
	
	public String getSolenhsanxuat() {
		return solenhsanxuat;
	}

	public void setSolenhsanxuat(String solenhsanxuat) {
		this.solenhsanxuat = solenhsanxuat;
	}

	List<IDanhmucvattu_SP> dmvtList;
	List<IDanhmucvattu_SP> dmvtThieuList;
	
	List<IDanhmucvattu_SP> ListVattuThem;
	
	List<IDanhmucvattu_SP> ListBanThanhPham;
	
	List<ILenhSXCongDoan> ListCongdoan;
	List<ISanPhamSanXuat> listSpSx;
	
	List<IDanhmucvattu_SP> ListVattuDeNghi;
	
	List<IErpDinhmuc> dinhmucList;
	
	List<IErpBom> BomList;
	
	
	ResultSet chitietNlRs;
	String Trangthaixemtieuhao;
	ResultSet rsnhapkho;
	String IdTieuHao;
	float DinhLuong;
	String Dvkdid;
	dbutils db;
	String KhGiaCongId="";
	String IsLSXGiacong="";
	
	Utility_Kho util_kho=new Utility_Kho();
	
	NumberFormat formatter = new DecimalFormat("#######.###");
	
	String[] mangcheck=new String[]{"1.Lệnh sản xuất","2.Phiếu giao nhận vật tư SX","3.Hồ sơ sản xuất","4.Phiếu giao nhận vật tư đóng gói","5.Hồ sơ lô đóng gói",
			"6.Nhãn trong quá trình sản xuất","7.Phiếu kiểm tra trước khi SX_ĐG","Trước khi lĩnh nguyên liệu","Trước khi pha chế + ĐG sơ cấp", 
			"Trước khi đóng gói thức cấp", "11. Biên bản xửa lý dư phẩm,phế phẩm","12. Phiếu thanh toán vật tư"};
	String[] mangcheck_value=new String[]{"1","2","3","4","5",
			"6","7","7.1","7.2","7.3", "11","12"};
	
	String[] mangcheck_tkl=new String[]{
	"1. Sự thay đổi","2. Sự cố kỹ thuật","3. Sản phẩm không phù hợp loại 3"
	};
	String[] mangvalue_tkl=new String[]{"1","2","3"};
		
	String[] view_check=new String[]{"1","0","1","1","1","1","1","1" 
			,"0","1","1",
			"0","1","1","1",
			"0",
			"1","1","1",
			"0","1","1",
			"0","1","1","1","1"};
	
	String[] mangdanhgia =new String[]{};
	
	String[] mangdanhgia_values =new String[]{"9", "10"};
	
	String Strchon;
	String StrTongket;
	
	String HD,NSX,Dmvt,Sohoso;
	String SOLUONGNHAPKHO,SOLUONGLAYMAU,HIEUSUAT_SX ,HIEUSUAT_DG  ,	TOANCHANG  ,YKIENKHAC ,NDHL ,PHACHE ;
	
	String soLenhSanXuat,SDK,canCuLamLenh,QTSX,TCKT,HOSOLOGOC,QUYETDINHLUUHANH;


	String NGAYBATDAUSX,NGAYHOANTHANH,SONGAYCHAMSOVOILENH ,	GHICHU_TIENDO;
	String[]  SOTT=new String[]{"1","1","2","3","4"} ;
	String[] NOIDUNG =new String[]{"BTP.........","BTP.........","MT không khí","Nước cất","Thành phẩm"} ;
	String[] SOPKN=new String[]{"","","","",""} ;
	String[] NGAY=new String[]{"","","","",""} ;
	String[] KETQUA=new String[]{"","","","",""} ;
	String[] GHICHU=new String[]{"","","","",""} ;
	
	ResultSet khoanmucchiphiRs;
	String khoanmucchiphi;

	 
	String NgayYCThenNL="";
	
	
	public String getNgayYCThenNL() {
		return NgayYCThenNL;
	}

	public void setNgayYCThenNL(String ngayYCThenNL) {
		NgayYCThenNL = ngayYCThenNL;
	}


	public ErpTieuhaonguyenlieu()
	{		
			HIEUSUAT_SX ="";HIEUSUAT_DG  ="";	TOANCHANG ="";YKIENKHAC="";NDHL="";PHACHE ="";	GHICHU_TIENDO="";
			NGAYBATDAUSX="";NGAYHOANTHANH="";SONGAYCHAMSOVOILENH="";SOLUONGNHAPKHO ="";SOLUONGLAYMAU="";
			QTSX="";TCKT="";HOSOLOGOC="";QUYETDINHLUUHANH="";soLenhSanXuat="";SDK="";canCuLamLenh="";
		this.StrTongket="";
		 HD="";NSX="";Dmvt="";Sohoso="";
		 this.Strchon="";
		this.IdNhapkho="";
		this.VuotDungSai=false;
		this.DinhLuong=0;
		this.id = "";
		this.ngaytieuhao="";
		this.ngaytao = "";
		this.ngaydukien = "";
		this.Dvkdid="";
		this.soluongsx = "";
		this.dvtBOM="";
		this.msg = "";
		this.khoId = "";
		this.Ghichu="";
		this.PO = "";
		this.Trangthaixemtieuhao="1";
		this.spId = "";
		this.kbsxId = "";
		this.nhamayId = "";
		this.soPoKH="";
		this.IsLsxCongNghe="";
		this.bomId="";
		this.viewBom = "0";
		this.soluongchuan = "";
		this.cpTT = "0";
		this.trangthai = "0";
		this.IdTieuHao="";
		this.spMa="";
		this.lohoiId = "";
		this.loaisanphamTH ="";
		this.dang1="";
		this.dang2="";
		this.dang3="";
		this.SKN1="";
		this.SKN2="";
		this.SKN3="";
		this.quatrinh1="";
		this.quatrinh2="";
		this.SoLSX="";
		this.dinhmucList = new ArrayList<IErpDinhmuc>();
		
		this.dmvtList = new ArrayList<IDanhmucvattu_SP>();
		
		this.dmvtThieuList=new ArrayList<IDanhmucvattu_SP>();
		
		this.ListBanThanhPham=new ArrayList<IDanhmucvattu_SP>();
		
		this.ListVattuThem=new ArrayList<IDanhmucvattu_SP>();
		
		this.ListCongdoan=new ArrayList<ILenhSXCongDoan>();
		this.ListVattuDeNghi=new ArrayList<IDanhmucvattu_SP>();
		listSpSx =new ArrayList<ISanPhamSanXuat>();
		this.db = new dbutils();
		IsChangeBom=false;
		this.khoanmucchiphi = "";
	
		
	}
	
	public ErpTieuhaonguyenlieu(String id)
	{
		
	 
		
		
		HIEUSUAT_SX ="";HIEUSUAT_DG  ="";	TOANCHANG ="";YKIENKHAC="";NDHL="";PHACHE ="";	
		GHICHU_TIENDO="";soLenhSanXuat="";SDK="";canCuLamLenh="";
		NGAYBATDAUSX="";NGAYHOANTHANH="";SONGAYCHAMSOVOILENH="";SOLUONGNHAPKHO ="";SOLUONGLAYMAU="";
		QTSX="";TCKT="";HOSOLOGOC="";QUYETDINHLUUHANH="";
		this.Strchon="";
		this.StrTongket="";
		 HD="";NSX="";Dmvt="";Sohoso="";
		this.id = id;
		this.VuotDungSai=false;
		this.ngaytao = "";
		this.ngaydukien = "";
		this.Dvkdid="";
		this.soluongsx = "";
		this.dvtBOM="";
		this.msg = "";
		this.khoId = "";
		this.PO = "";
		this.loaisanphamTH ="";
		this.Trangthaixemtieuhao="1";
		this.spId = "";
		this.kbsxId = "";
		this.nhamayId = "";
		this.IdTieuHao="";
		this.viewBom = "0";
		this.soluongchuan = "";
		this.cpTT = "0";
		this.trangthai = "0";
		this.ngaytieuhao="";
		this.IdNhapkho="";
		this.soPoKH="";
		this.lohoiId = "";
		this.IsLsxCongNghe="";
		this.dinhmucList = new ArrayList<IErpDinhmuc>();
		this.bomId="";
		this.dang1="";
		this.dang2="";
		this.dang3="";
		this.SKN1="";
		this.SKN2="";
		this.SKN3="";
		this.quatrinh1="";
		this.quatrinh2="";
		this.SoLSX="";
		this.ListCongdoan=new ArrayList<ILenhSXCongDoan>();
		this.dmvtList = new ArrayList<IDanhmucvattu_SP>();
		this.dmvtThieuList=new ArrayList<IDanhmucvattu_SP>();
		this.ListVattuThem=new ArrayList<IDanhmucvattu_SP>();
		this.ListBanThanhPham=new ArrayList<IDanhmucvattu_SP>();
		this.ListVattuDeNghi=new ArrayList<IDanhmucvattu_SP>();
		listSpSx =new ArrayList<ISanPhamSanXuat>();
		this.db = new dbutils();
		this.khoanmucchiphi = "";
	}

	

	public String getKhoanmucchiphi() {
		return khoanmucchiphi;
	}

	public void setKhoanmucchiphi(String khoanmucchiphi) {
		this.khoanmucchiphi = khoanmucchiphi;
	}

	public ResultSet getKhoanmucchiphiRs() {
		return khoanmucchiphiRs;
	}

	public void setKhoanmucchiphiRs(ResultSet khoanmucchiphiRs) {
		this.khoanmucchiphiRs = khoanmucchiphiRs;
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

	public String getNgaytao() 
	{
		return this.ngaytao;
	}

	public void setNgaytao(String ngaytao) 
	{
		this.ngaytao = ngaytao;
	}

	public String getNgaydukien() 
	{
		return this.ngaydukien;
	}

	public void setNgaydukien(String ngaydk)
	{
		this.ngaydukien = ngaydk;
	}

	public String getSoluong() 
	{
		return this.soluongsx;
	}

	public void setSoluong(String soluong)
	{
		this.soluongsx  =soluong;
	}

	public String getKhottId()
	{
		return this.khoId;
	}

	public void setKhottId(String khott) 
	{
		this.khoId = khott;
	}

	public ResultSet getKhoTTRs()
	{
		return this.khoRs;
	}

	public void setKhoTTRs(ResultSet khottRs)
	{
		this.khoRs = khottRs;
	}

	public String getmsg() 
	{
		return this.msg;
	}

	public void setmsg(String msg) 
	{
		this.msg = msg;	
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private String getTime()
	{
		Date date = new Date();
	    SimpleDateFormat simpDate;

	    //format 24h
	    simpDate = new SimpleDateFormat("kk:mm:ss");
	    
	    return simpDate.format(date);
	}
	
	public boolean createLsx() 
	{
		if(this.ngaytao.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ngày bắt đầu lệnh sản xuất";
			return false;
		}
		
		if( this.spId.length() == 0){
			this.msg="Vui lòng chọn sản phẩm để sản xuất";
			return false;
		}
		
		if(  this.kbsxId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kịch bản sản xuất";
			return false;
		}
		
		if(this.soluongsx.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập số lượng sản xuất";
			return false;
		}
		
	 
		 
		try 
		{
			db.getConnection().setAutoCommit(false);
		 
		 
			
			// CAP NHAT SO PO, MA TU DONG TANG
						String nam = this.ngaytao.substring(0, 4);
						String thang = this.ngaytao.substring(5, 7);
						String ngay  = this.ngaytao.substring(8, 10);
						
					String	query = " SELECT (select isnull(DUNG_KYHIEU_SX,'0')  from erp_congty where pk_seq ="+this.congtyId+" )   as DUNG_KYHIEU_SX, "
								+ " (select KYHIEU_LSX from erp_congty where pk_seq ="+this.congtyId+" ) as kyhieu ,ISNULL( MAX(SOTUTANG_THEONAM), 0) AS MAXSTT  "
									 
								+ " FROM ERP_lenhsanxuat_giay   DMH WHERE   SUBSTRING(NGAYBATDAU, 0, 5) = " + nam
								+ " and  dondathang_fk is null ";
						//System.out.println("Du lieu po sai  :" + query);
						String soPO = "";
						int sotutang_theonam = 0;
						ResultSet rsPO = db.get(query);
						if (rsPO.next()) {
							sotutang_theonam = (rsPO.getInt("maxSTT") + 1);
						
							String kyhieu= rsPO.getString("kyhieu");
							 
							
							String chuoiso = ("00000" + Integer.toString(sotutang_theonam)).substring(
									("00000" + Integer.toString(sotutang_theonam)).length() - 5,
									("00000" + Integer.toString(sotutang_theonam)).length());
							
							//System.out.println(rsPO.getString("DUNG_KYHIEU_SX"));
							
							if(  rsPO.getString("DUNG_KYHIEU_SX").equals("1") ){
								soPO = kyhieu + "-" +ngay+ "/" + thang + "/" + nam+"-"+ chuoiso;
							} 

						}
						rsPO.close();
						
						
			
			if(this.lohoiId == null || this.lohoiId.length() == 0)
				this.lohoiId = "NULL";
			
			query =" insert into ERP_LENHSANXUAT_GIAY(SANPHAM_FK,SOTUTANG_THEONAM,SOlenhsanxuat,IsLsxCongNghe,diengiai, ngaybatdau, ngaydukienht, kichbansanxuat_fk, congty_fk, soluong, nhamay_fk, trangthai, ngaytao, nguoitao, ngaysua, nguoisua,khosanxuat_fk, soPoKH, LOHOI_FK,NPP_FK) " +
					" values("+this.spId+","+sotutang_theonam+",'"+soPO+"','"+this.IsLsxCongNghe+"',N'"+this.Ghichu+"','"+this.ngaytao+"','"+this.ngaydukien+"'," + (this.kbsxId.length() >0? this.kbsxId:"null") + ", '" + this.congtyId + "',  '" +  this.soluongsx+ "' " +
							", " + this.nhamayId + ", '0', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "',"+this.KhosxId+", N'"+this.soPoKH+"', "+this.lohoiId+","+this.nppId+") "; 
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới lệnh sản xuất: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "select SCOPE_IDENTITY()  as clId";
			ResultSet rs = this.db.get(query);
			rs.next();
			String id_curent = rs.getString("clId");
			rs.close();
  	 
			
			if(this.ListCongdoan.size() >0){
				

				for(int i = 0; i < this.ListCongdoan.size(); i++)
				{
					ILenhSXCongDoan congdoan=this.ListCongdoan.get(i);
					String trangthaicd="";
					if(congdoan.getActive().equals("1")){
						trangthaicd="0";
					}else{
						trangthaicd="2";
					}
					
					query=  " insert into ERP_LENHSANXUAT_CONGDOAN_GIAY (lenhsanxuat_fk,kichban_fk,congdoan_fk,tinhtrang,soluong,THUTU,SANPHAM_FK,DANHMUCVATTU_FK,KIEMDINHCHATLUONG,MASANPHAM,MAYSUDUNG) values " +
							" ( '"+id_curent+"','"+this.kbsxId+"','"+congdoan.getCongDoanId()+"','"+trangthaicd+"',"+congdoan.getSoLuong()+","+congdoan.getThutu()+","+congdoan.getSpid()+","+congdoan.getBomId()+",'"+congdoan.getKiemDinhCL()+"','"+congdoan.getMaSp()+"',N'"+congdoan.getMaySuDung()+"')";
				 
					
					if(!db.update(query))
					{
						this.msg = "Không thể tạo mới Kichbansanxuat: " + query;
						db.getConnection().rollback();
						return false;
					}
					
					// thực hiện save danh sách nguyên liệu trong công đoạn
					
					String msg1=this.Save_nguyenLieu_Congdoan(congdoan,db,id_curent);
					if(msg1.length() >0){
						this.msg=msg1;
						return false;
					}
					 
					  msg1= this.Save_NhapXuat_Congdoan(congdoan,db,id_curent);
					  if(msg1.length() >0){
							this.msg=msg1;
							return false;
					 }
					  
				}
			}
			/*****END	*  ***/
 
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			return true;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			this.msg = "Không thể tạo mới lệnh sản xuất: " + e.getMessage();
			return false;
		}
		
	}

	private String Save_NhapXuat_Congdoan(ILenhSXCongDoan congdoan,
			dbutils db2, String id_curent) {
		// TODO Auto-generated method stub
		try{
			
			ArrayList<String> soluongNhap = congdoan.getSoluongNhap();
			ArrayList<String> soluongXuat = congdoan.getSoluongXuat();
			ArrayList<String> soluongDuPham = congdoan.getSoluongDuPham();
			ArrayList<String> soluongPhePham = congdoan.getSoluongPhePham();
			String query="delete ERP_LENHSANXUAT_CONGDOAN_NHAPXUAT where congdoan_fk="+congdoan.getCongDoanId()+" and lenhsanxuat_fk ="+id_curent;
			
			if(!db.update(query))
			{
				
				db.getConnection().rollback();
				return "Không thể xóa công đoạn nhập xuất: " + query;
				 
			}
		 
			//Loop theo số lần nhập mà lưu vào bảng công đoạn nhập xuất
			for(int j = 0 ; j < soluongNhap.size() ; j++ ){
				int lannhap = j +1 ;
			//Lưu thêm công đoạn nhập xuất
			 	query=  " INSERT INTO ERP_LENHSANXUAT_CONGDOAN_NHAPXUAT ([LENHSANXUAT_FK],[CONGDOAN_FK],"
						+ "[NHAP],[XUAT],[CHUYENDUPHAM],[CHUYENPHEPHAM],[LANNHAP]) VALUES " +
						" ( '"+id_curent+"','"+congdoan.getCongDoanId()+"',";
				query += soluongNhap.get(j) +"," + soluongXuat.get(j) +",";
				query += soluongDuPham.get(j) +"," + soluongPhePham.get(j) +",";
				query += lannhap +")";
				//Bổ sung các giá trị nhập xuất của mỗi lần nhập
				int test = j +1 ;
				////System.out.println("query lan (" + test + ") la : \n" + query);
				if(!db.update(query))
				{
					
					db.getConnection().rollback();
					return "Không thể tạo mới công đoạn nhập xuất: " + query;
					 
				}
				
			}
			
			
		}catch(Exception er){
			db.update("rollback");
			
			er.printStackTrace();
			return er.getMessage();
		}
		return "";
	}

	private String Save_nguyenLieu_Congdoan(ILenhSXCongDoan congdoan,
			dbutils db2, String id_curent) {
		// TODO Auto-generated method stub
		try{
			int sott=0;
			
			dmvtList =congdoan.getListDanhMuc(); 
			 
			if(!this.check_congdoan_chua_YCNL(congdoan.getCongDoanId())){
				db.getConnection().rollback();
				return   "Không thể cập nhật công đoạn, vì công đoạn này đã yêu cầu nguyên liệu, bạn chỉ có thể yêu cầu nguyên liệu thêm"; 
			}
			 
			String query="delete ERP_LENHSANXUAT_BOM_GIAY where lenhsanxuat_fk="+id_curent +" and congdoan_fk="+congdoan.getCongDoanId();
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return   "Không thể tạo mới ERP_LENHSANXUAT_BOM_GIAY: " + query; 
			}
			
			query="delete ERP_LENHSANXUAT_BOM_GIAY_CHITIET where lenhsanxuat_fk="+id_curent +" and congdoan_fk="+congdoan.getCongDoanId();
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return   "Không thể tạo mới ERP_LENHSANXUAT_BOM_GIAY_CHITIET: " + query; 
			}
			
			
			
			
			for(int i = 0; i <  dmvtList.size(); i++)
			    {
					IDanhmucvattu_SP vattu = this.dmvtList.get(i);
					
					//loại =1 là để phân biết các sản phẩm được yêu cầu nguyên liệu
					//  2 : sản phẩm lấy  từ kho sản xuất
					//3 bán thành phẩm
					String loai="1";
					String checkkho="1";
					  
					  query =    " insert into ERP_LENHSANXUAT_BOM_GIAY(LOAI,ISNL_TIEUHAO,lenhsanxuat_fk,congdoan_fk,khott_fk, VATTU_FK,SOLUONGCHUAN ,CHECKKHO,SOTT, DVT,isTINHHAMAM,isTINHHAMLUONG,HAMAM,HAMLUONG,DUTONKHO)  " +
							   " values ('"+loai+"','"+vattu.getIsNLTieuHao()+"',"+id_curent+","+vattu.getCongDoanId()+",'"+vattu.getkhoid()+"','"+vattu.getIdVT()+"' " +
							   "  ,"+vattu.getSoLuongChuan()+" ,'"+checkkho+"',"+sott+", N'"+vattu.getDvtVT()+"' ,'"+vattu.getIsTinhHA()+"', '"+vattu.getIsTinhHL()+"',"+vattu.getHamam()+","+vattu.getHamluong()+",'"+vattu.getChon()+"')";
					
					
					if(!db.update(query))
					{
						 
						db.getConnection().rollback();
						return   "Không thể tạo mới ERP_LENHSANXUAT_BOM_GIAY: " + query; 
					}
					
					
					List<ISpSanxuatChitiet> listct = vattu.getListCTkho();
					for(int k=0;k<listct.size();k++){
						ISpSanxuatChitiet sp=listct.get(k);
						double soluongct=0;
						try{
							soluongct=Double.parseDouble(sp.getSoluong().replaceAll(",", ""));
						}catch(Exception er){}
						if(soluongct>0){
							double soluong_ton=0;
							try{
								soluong_ton=Double.parseDouble(sp.getSoluongton().replaceAll(",", ""));
							}catch(Exception er){}
							
							if(soluongct>soluong_ton){
								 
								db.getConnection().rollback();
								return   "Vui lòng kiểm tra số lượng > tồn của SP :"+sp.getMaSp()+" Số lô :"+ sp.getSolo() ;
							}
							

							//-------- Luu lai dinh muc cua nguyen lieu thay the 
							double soluongdinhmuc=0.0;
							double dinhmuc_thaythe=0.0;
							double soluongbom=0.0;
							String sql_slbom="select SOLUONGCHUAN from ERP_DANHMUCVATTU where pk_seq="+ congdoan.getBomId() ;
							////System.out.println(" sluong bom : "+ sql_slbom);
							ResultSet rs_slbom= db.get(sql_slbom);
							if(rs_slbom!=null){
								try {
									while (rs_slbom.next()){
										soluongbom= rs_slbom.getDouble("SOLUONGCHUAN");
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
								rs_slbom.close();
							}
							
							String sql_dinhmucthaythe="select SOLUONG from ERP_DANHMUCVATTU_VATTU_THAYTHE where DANHMUCVT_FK="+congdoan.getBomId()+"  and VATTU_FK="+ vattu.getIdVT() +" AND VATTUTT_FK=" +sp.getIdSp();
							ResultSet rs_dinhmucthaythe= db.get(sql_dinhmucthaythe);
							if(rs_dinhmucthaythe!=null){
								try {
									while (rs_dinhmucthaythe.next()){
										dinhmuc_thaythe= rs_dinhmucthaythe.getDouble("SOLUONG");
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
								rs_dinhmucthaythe.close();
							}
							NumberFormat formatter_3 = new DecimalFormat("#######.#########");
							soluongdinhmuc =Double.parseDouble(formatter_3.format( ( Double.parseDouble( this.soluongsx.replaceAll(",", "")) /soluongbom  )*dinhmuc_thaythe));
							
							
							
							if(soluongct>0){
								double soluongquyveBom=this.QuyvechuanBom(vattu,sp,soluongct);
								query=   " INSERT INTO ERP_LENHSANXUAT_BOM_GIAY_CHITIET ( CONGDOAN_FK ,LENHSANXUAT_FK ,	VATTU_FK,	SANPHAM_FK ,	KHOTT_FK ,	SOLO , " +
										 " KHUVUCKHO_FK,	MARQUETTE_FK ,MARQ,	MATHUNG,	MAME ,	NGAYNHAPKHO,	HAMLUONG ,	HAMAM,	NGAYHETHAN,	SOLUONG ,SOLUONGCHUAN,BIN_FK,MAPHIEU,PHIEUEO,MAPHIEUDINHTINH ,SOLUONGDINHMUC)  " +
										 " VALUES ("+congdoan.getCongDoanId()+","+id_curent+","+vattu.getIdVT()+","+sp.getIdSp()+","+sp.getKhoId()+",'"+sp.getSolo()+"', " +
										 " "+(sp.getkhuvuckhoId().length()==0?"NULL":sp.getkhuvuckhoId())+","+ (sp.getMARQUETTE_FK().length()>0?sp.getMARQUETTE_FK():"NULL" ) +",'"+sp.getMARQUETTE()+"','"+sp.getMATHUNG()+"','"+sp.getMAME()+"' " +
										 " ,'"+sp.getNGAYNHAPKHO()+"','"+sp.getHAMLUONG()+"',"+sp.getHAMAM()+",'"+sp.getNGAYHETHAN()+"',"+soluongct+","+soluongquyveBom+","+(sp.getBinId().length()>0?sp.getBinId():"NULL")+" ,'"+sp.getMAPHIEU()+"','"+sp.getMAPHIEU_EO()+"','"+sp.getMAPHIEU_TINHTINH()+"',"+soluongdinhmuc+")";
								
								if(!db.update(query))
								{
									db.getConnection().rollback();
									return   "Không thể tạo mới ERP_LENHSANXUAT_BOM_GIAY: " + query; 
								}
							}
						}
					}
					
					
					sott++;
					 
			
			}
		 
		}catch(Exception er){
			db.update("rollback");
			return er.getMessage();
		}
		return "";
	}


	private String Save_nguyenLieu_Them(ILenhSXCongDoan congdoan,
			dbutils db2, String id_curent) {
		// TODO Auto-generated method stub
		try{
			int sott=0;
			
			dmvtList =congdoan.getListDanhMuc(); 
			  
			boolean check_conlyc=false;
			
			for(int i = 0; i <  dmvtList.size(); i++)
			    {
					IDanhmucvattu_SP vattu = this.dmvtList.get(i);
					
					//loại =1 là để phân biết các sản phẩm được yêu cầu nguyên liệu
					//  2 : sản phẩm lấy  từ kho sản xuất
					//3 bán thành phẩm
					String loai="1";
					String checkkho="1";
					  
					 
					
					List<ISpSanxuatChitiet> listct = vattu.getListCTkho();
					for(int k=0;k<listct.size();k++){
						ISpSanxuatChitiet sp=listct.get(k);
						double soluongct=0;
						try{
							soluongct=Double.parseDouble(sp.getSoluong().replaceAll(",", ""));
						}catch(Exception er){}
						if(soluongct>0){
							double soluong_ton=0;
							try{
								soluong_ton=Double.parseDouble(sp.getSoluongton().replaceAll(",", ""));
							}catch(Exception er){}
							
							if(soluongct>soluong_ton){
								 
								db.getConnection().rollback();
								return   "Vui lòng kiểm tra số lượng > tồn của SP :"+sp.getMaSp()+" Số lô :"+ sp.getSolo() ;
							}
							

							//-------- Luu lai dinh muc cua nguyen lieu thay the 
							double soluongdinhmuc=0.0;
							double dinhmuc_thaythe=0.0;
							double soluongbom=0.0;
							String sql_slbom="select SOLUONGCHUAN from ERP_DANHMUCVATTU where pk_seq="+ congdoan.getBomId() ;
							////System.out.println(" sluong bom : "+ sql_slbom);
							ResultSet rs_slbom= db.get(sql_slbom);
							if(rs_slbom!=null){
								try {
									while (rs_slbom.next()){
										soluongbom= rs_slbom.getDouble("SOLUONGCHUAN");
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
								rs_slbom.close();
							}
							
							String sql_dinhmucthaythe="select SOLUONG from ERP_DANHMUCVATTU_VATTU_THAYTHE where DANHMUCVT_FK="+congdoan.getBomId()+"  and VATTU_FK="+ vattu.getIdVT() +" AND VATTUTT_FK=" +sp.getIdSp();
							ResultSet rs_dinhmucthaythe= db.get(sql_dinhmucthaythe);
							if(rs_dinhmucthaythe!=null){
								try {
									while (rs_dinhmucthaythe.next()){
										dinhmuc_thaythe= rs_dinhmucthaythe.getDouble("SOLUONG");
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
								rs_dinhmucthaythe.close();
							}
							NumberFormat formatter_3 = new DecimalFormat("#######.#########");
							soluongdinhmuc =Double.parseDouble(formatter_3.format( ( Double.parseDouble( this.soluongsx.replaceAll(",", "")) /soluongbom  )*dinhmuc_thaythe));
							
							
							
							if(soluongct>0){
								double soluongquyveBom=this.QuyvechuanBom(vattu,sp,soluongct);
								String query=   " INSERT INTO ERP_LENHSANXUAT_BOM_GIAY_CHITIET ( CONGDOAN_FK ,LENHSANXUAT_FK ,	VATTU_FK,	SANPHAM_FK ,	KHOTT_FK ,	SOLO , " +
										 " KHUVUCKHO_FK,	MARQUETTE_FK ,MARQ,	MATHUNG,	MAME ,	NGAYNHAPKHO,	HAMLUONG ,	HAMAM,	NGAYHETHAN,	SOLUONG ,SOLUONGCHUAN,BIN_FK,MAPHIEU,PHIEUEO,MAPHIEUDINHTINH ,SOLUONGDINHMUC)  " +
										 " VALUES ("+congdoan.getCongDoanId()+","+id_curent+","+vattu.getIdVT()+","+sp.getIdSp()+","+sp.getKhoId()+",'"+sp.getSolo()+"', " +
										 " "+(sp.getkhuvuckhoId().length()==0?"NULL":sp.getkhuvuckhoId())+","+ (sp.getMARQUETTE_FK().length()>0?sp.getMARQUETTE_FK():"NULL" ) +",'"+sp.getMARQUETTE()+"','"+sp.getMATHUNG()+"','"+sp.getMAME()+"' " +
										 " ,'"+sp.getNGAYNHAPKHO()+"','"+sp.getHAMLUONG()+"',"+sp.getHAMAM()+",'"+sp.getNGAYHETHAN()+"',"+soluongct+","+soluongquyveBom+","+(sp.getBinId().length()>0?sp.getBinId():"NULL")+" ,'"+sp.getMAPHIEU()+"','"+sp.getMAPHIEU_EO()+"','"+sp.getMAPHIEU_TINHTINH()+"',"+soluongdinhmuc+")";
								
								if(!db.update(query))
								{
									db.getConnection().rollback();
									return   "Không thể tạo mới ERP_LENHSANXUAT_BOM_GIAY: " + query; 
								}
								check_conlyc=true;
							}
						}
					}
					
					
					sott++;
					 
			
			}
			if(!check_conlyc){
				db.getConnection().rollback();
				return "Không thể tạo yêu cầu nguyên liệu thêm, vì không có chi tiết lô nguyên liệu nào được chọn";
			}
		 
		}catch(Exception er){
			db.update("rollback");
			return er.getMessage();
		}
		return "";
	}

	
	public boolean updateLsx() 
	{
		
		if(this.ngaytao.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập ngày bắt đầu lệnh sản xuất";
			return false;
		}
		 
		/*********END KIEM TRA *****************************************/
		if(this.kbsxId.trim().length() <= 0   )
		{
			this.msg = "Vui lòng chọn kịch bản sản xuất";
			return false;
		}
 
		if(this.nhamayId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn nhà máy sản xuất";
			return false;
		}
		
		if(this.soluongsx.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập số lượng sản xuất";
			return false;
		}
		
	 
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			
			String query ="";
			
			 	
		 
			
			
			int sott=0;
			
			query="select trangthai from erp_lenhsanxuat_giay where pk_Seq="+this.id;
			ResultSet rstt=db.get(query);
			if (rstt != null)
			{
				if(rstt.next()){
					this.trangthai= rstt.getString("trangthai");
				}
				rstt.close();
			}
			
			if(this.Check_lsx_dayc_nl()){ 
				
				
				if(this.lohoiId == null || this.lohoiId.length() == 0)
					this.lohoiId = "NULL";
				
				String nam = this.ngaytao.substring(0, 4);
				String thang = this.ngaytao.substring(5, 7);
				String ngay  = this.ngaytao.substring(8, 10);
				int sotutang_theonam = 0;	
				boolean cothaydoi_dvth = false;

				query = " select  *   from erp_lenhsanxuat_giay  mh " + " where mh.pk_seq=" + this.id
						+ " and (  SUBSTRING(NGAYBATDAU, 0, 5) <> " + nam + " and isnull(ISGIACONG,0)  <> 1 ) ";
				ResultSet rscheckdv = db.get(query);
				String soPO = "";
				
				if (rscheckdv.next()) {
					cothaydoi_dvth = true;
					// Có thay đổi đơn vị thực hiện.phải thay đổi lại số PO và số
					// thứ tự,hoặc năm bị thay đổi
					
					query = " SELECT (select isnull(DUNG_KYHIEU_SX,'0')  from erp_congty where pk_seq ="+this.congtyId+" )   as DUNG_KYHIEU_SX, "
							+ " (select KYHIEU_LSX from erp_congty where pk_seq ="+this.congtyId+" ) as kyhieu ,ISNULL( MAX(SOTUTANG_THEONAM), 0) AS MAXSTT  "
								 
							+ " FROM ERP_lenhsanxuat_giay   DMH WHERE dmh.npp_fk="+this.nppId+" and SUBSTRING(NGAYBATDAU, 0, 5) = " + nam
							+ " and  dondathang_fk is null ";
					//System.out.println("Du lieu po sai  :" + query);
					
					
					ResultSet rsPO = db.get(query);
					if (rsPO.next()) {
						sotutang_theonam = (rsPO.getInt("maxSTT") + 1);
						 
						String kyhieu= rsPO.getString("kyhieu");
						 
						
						String chuoiso = ("00000" + Integer.toString(sotutang_theonam)).substring(
								("00000" + Integer.toString(sotutang_theonam)).length() - 5,
								("00000" + Integer.toString(sotutang_theonam)).length());
						
						//System.out.println(rsPO.getString("DUNG_KYHIEU_SX"));
						
						if(  rsPO.getString("DUNG_KYHIEU_SX").equals("1") ){
							soPO = kyhieu + "-" +ngay+ "/" + thang + "/" + nam+"-"+ chuoiso;
						} 

					}
					rsPO.close();
					
					
					// //System.out.println("---SO PO: " + soPO);

				}
				rscheckdv.close();
				
				
				
				query =  " Update ERP_LENHSANXUAT_GIAY set  diengiai= N'"+this.Ghichu+"' , sanpham_fk="+this.spId+", "+ (cothaydoi_dvth?"solenhsanxuat='"+soPO+"',SOTUTANG_THEONAM ='" + sotutang_theonam + "',":"") +" ISLSXCONGNGHE='"+this.IsLsxCongNghe+"', kichbansanxuat_fk = " + (this.kbsxId.length() >0?this.kbsxId:"null" ) + ", ngaybatdau = '" + this.ngaytao + "', ngaydukienHT = '" + this.ngaydukien + "', " +
						 " soluong = '" + this.soluongsx + "', nhamay_fk = '" + this.nhamayId + "', ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "', khosanxuat_fk="+this.KhosxId+", LOHOI_FK = "+this.lohoiId+" where pk_seq = '" + this.id + "' ";
					 			
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật lệnh sản xuất: " + query;
					db.getConnection().rollback();
					return false;
				}
 
			 
				query = "delete ERP_LENHSANXUAT_CONGDOAN_GIAY where lenhsanxuat_fk = '" + this.id + "'";
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật Kichbansanxuat: " + query;
					db.getConnection().rollback();
					return false;
				}
				query = "delete ERP_LENHSANXUAT_CONGDOAN_NHAPXUAT where lenhsanxuat_fk = '" + this.id + "'";
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật Kichbansanxuat(không xoá được ERP_LENHSANXUAT_CONGDOAN_NHAPXUAT): " + query;
					db.getConnection().rollback();
					return false;
				} 
				 
				 
				if(this.ListCongdoan.size() >0){
					 
					for(int i = 0; i < this.ListCongdoan.size(); i++)
					{
						ILenhSXCongDoan congdoan=this.ListCongdoan.get(i);
						String trangthaicd="";
						if(congdoan.getActive().equals("1")){
							trangthaicd="0";
						}else{
							trangthaicd="2";
						}
						query="insert into ERP_LENHSANXUAT_CONGDOAN_GIAY (lenhsanxuat_fk,kichban_fk,congdoan_fk,tinhtrang,soluong,THUTU,SANPHAM_FK,DANHMUCVATTU_FK,KIEMDINHCHATLUONG,MASANPHAM,MAYSUDUNG) values " +
						" ( '"+this.id+"','"+this.kbsxId+"','"+congdoan.getCongDoanId()+"','"+trangthaicd+"',"+congdoan.getSoLuong()+","+congdoan.getThutu()+","+congdoan.getSpid()+","+congdoan.getBomId()+",'"+congdoan.getKiemDinhCL()+"','"+congdoan.getMaSp()+"',N'"+congdoan.getMaySuDung()+"')";
						
						//////System.out.println("Các Cong Doanh");
						if(!db.update(query))
						{
							this.msg = "Không thể tạo mới Kichbansanxuat: " + query;
							db.getConnection().rollback();
							return false;
						}
						 
						
					}
				}
				/*****END			 *  ***/
			 
	 
				}
			 
		  
			
				 
			query=" update ERP_LENHSANXUAT_GIAY set diengiai= N'"+this.Ghichu+"'  where pk_seq="+this.id;
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật lệnh sản xuất: " + query;
				db.getConnection().rollback();
				return false;
			}
		 
			 
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			return true;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			this.msg = "Không thể tạo mới lệnh sản xuất: " + e.getMessage();
			return false;
		}
	}

	
	private boolean Check_lsx_dayc_nl() {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		try{
				String id_=this.id;
				if(this.id==null|| this.id.equals("")){
					id_="0";
				}
				String query="select count(congdoan_fk) as count from ERP_LENHSANXUAT_BOM_GIAY_CHITIET "
						   + " where LENHSANXUAT_FK= "+id_+"  AND CHUYENKHO_FK IS NOT NULL ";
				ResultSet rs=db.get(query);
				int bien=0;
				if(rs.next()){
					bien=rs.getInt("count");
				}
				rs.close();
				if(bien==0){
					return true;
				}else{
					return false;
				}
		}catch(Exception er){
			
		}
		return false;
	
	}

	private double QuyvechuanBom(IDanhmucvattu_SP vattu, ISpSanxuatChitiet sp,
			double soluongct1) {
		// TODO Auto-generated method stub
		double soluongct=soluongct1;
		
		double hamam_=0;
		try{
			hamam_= Double.parseDouble(vattu.getHamam().replaceAll(",", ""));
		}catch(Exception er){}
		double hamluong_=0;
		
		try{
			hamluong_=Double.parseDouble(vattu.getHamluong().replaceAll(",", ""));
		}catch(Exception er){}
		double hamam_bom=0;
		try{
		    hamam_bom= Double.parseDouble(sp.getHAMAM().replaceAll(",", "")) ;
		}catch(Exception er){
			
		}
		double hamluong_bom=0;
		try{
		  hamluong_bom= Double.parseDouble(sp.getHAMLUONG().replaceAll(",", "")) ;
		}catch(Exception er){
			
		}
		
		//quy về tịnh 
		 
		 if(vattu.getIsTinhHA().equals("1")){
			 soluongct=soluongct *(100- hamam_bom)/100 ;
			 
		 }
		 if(vattu.getIsTinhHL().equals("1")){
			 soluongct =soluongct* hamluong_bom/100;
		 }
	
		 
		 
		 
		 // quy ra lại số lượng của hàm ẩm và hàm lượng trong BOM
		 
		 if(vattu.getIsTinhHA().equals("1")){
			 soluongct=soluongct/(100-hamam_) * 100 ;
			 
		 }
		  
		 if(vattu.getIsTinhHL().equals("1")){
			 soluongct=soluongct*100/ hamluong_;
		 }
		 NumberFormat formatter = new DecimalFormat("#,###,###.###"); 
		
		return  Double.parseDouble(formatter.format(soluongct).replaceAll(",", ""));
	}

	private boolean KiemTraCoSanPhamChon() {
		
		for(int i = 0; i < this.listSpSx.size(); i++)
		{
			ISanPhamSanXuat spsx = this.listSpSx.get(i);
			double soluong = 0;
			try{
				soluong = Double.parseDouble(spsx.getSoluong());
			}catch(Exception er){
			}
			if(( spsx.getBomId().length() >0 || this.Dvkdid.trim().equals("100005") ) && soluong >0  ){
				return true;
			}
		}
		return false;
	}

	public void createRs()
	{
		
		if(this.ngaydukien.equals("")){
			this.ngaydukien=this.getDateTime();
		}
		if(this.ngaytao.equals("")){
			this.ngaytao=this.getDateTime();
		}
		String query ="select pk_seq, manhamay + ', ' + tennhamay as ten from ERP_NHAMAY where trangthai = 1 and congty_fk="+this.congtyId;
		this.nhamayRs = db.get(query);
		query="SELECT PK_SEQ,DONVIKINHDOANH as ten FROM DONVIKINHDOANH where congty_fk="+this.congtyId;
		this.RsDvkd=db.get(query);
		 
		
		query=" select   sp.PK_SEQ, sp.ma, sp.ten +'-' + isnull(dvdl.donvi,'NA')  as ten from ERP_SanPham sp \n"+
			" left join donvidoluong dvdl on dvdl.pk_seq=sp.dvdl_fk  " +
 			" where sp.trangthai = '1' and exists (select pk_seq from  erp_danhmucvattu a " +
 			//" inner join erp_Danhmucvattu_nhamay nm on nm.danhmucvattu_fk= a.pk_Seq " +
 			"  where a.masanpham=sp.ma   ) and sp.congty_fk="+this.congtyId;
		
		 
		////System.out.println("SAN PHAM  : \n"+sql+"\n============================");
		this.spRs = db.get(query);
		if(this.spId==null){
			this.spId="";
		}
		
		
		
		if(this.spId.trim().length() > 0)
		{
			
			query="select pk_seq, diengiai ,SOLUONGCHUAN from Erp_KichBanSanXuat_Giay " +
					" where trangthai = '1' and sanpham_fk = '" + this.spId + "' and congty_fk="+this.congtyId;
			////System.out.println("KBSX :"+sql);
			this.kbsxRs = db.get(query);
		 
			
		}
	 
		if(this.kbsxId.length() >0){
				
			query=" select b.pk_seq,b.diengiai from Erp_KichBanSanXuat_CongDoanSanXuat_Giay a inner join Erp_CongDoanSanXuat_Giay b  "+
					" on a.congdoansanxuat_fk=b.pk_Seq  where a.kichbansanxuat_fk="+this.kbsxId;
				this.RsCongDoan=db.getScrol(query);
		}
		 
		if(this.congtyId.length() >0 && this.nhamayId.length() >0){
		
			query=  " select PK_SEQ, khonhannguyenlieu_fk , MA+ ' - ' +TEN AS TEN  from ERP_KHOTT where TrangThai = '1'" +
					" and loai in ('10') and congty_fk = '"+this.congtyId+"' and NHAMAY_FK="+ this.nhamayId;
			this.RsKhoSX=db.get(query) ; 
			
			if(this.KhosxId==null ||  this.KhosxId.equals("")){
				
				ResultSet rskho=db.get(query);
				 
					String khoxuat="";
					
					if(rskho!=null){
						try{
							while (rskho.next()){
									khoxuat = rskho.getString("pk_seq");
							}
							rskho.close();
						}catch(Exception er){
							er.printStackTrace();
						}
					}
					this.KhosxId=khoxuat;
			}
	 
		}
		
	}
	
	
	private int getInt_FormSql(String sql) {
		// TODO Auto-generated method stub
		int dem=0;
		try{
			////System.out.println("getInt_FormSql :"+sql);
			ResultSet rs=db.get(sql);
			if(rs.next()){
				dem=rs.getInt("count");
			}
			rs.close();
		}catch(Exception er){
			
		}
		
	
		return dem;
	}
 
	public void init()
	{
		
		if(this.NgayYCThenNL==null){
			this.NgayYCThenNL="";
		}
		
		this.dinhmucList = new ArrayList<IErpDinhmuc>();
		
		this.dmvtList = new ArrayList<IDanhmucvattu_SP>();
	 
		this.ListCongdoan=new ArrayList<ILenhSXCongDoan>();
		 
		
		String sql =    " SELECT DISTINCT ISNULL( A.solenhsanxuat,'') solenhsanxuat,  ddh.khachhang_fk  as khachhang_fk,    ISNULL(ISGIACONG,0) AS ISGIACONG  ,A.KHOSANXUAT_FK , isnull(ISLSXCONGNGHE,'0') as ISLSXCONGNGHE  ,   isnull(a.diengiai,'') as diengiai, a.congty_fk ,  ISNULL(KB.SANPHAM_FK,0) AS SANPHAM_FK ,SP.MA AS MASANPHAM ,NGAYDUKIENHT, A.NGAYBATDAU ,A.TRANGTHAI, A.LOHOI_FK, "+
						" ISNULL(LENHSANXUATDUKIEN_FK, '-1') AS PO, KICHBANSANXUAT_FK , A.SOLUONG, A.NHAMAY_FK, A.soPoKH,  SP.DVKD_FK   "+
						" FROM ERP_LENHSANXUAT_GIAY A " +
						"  left join erp_dondathang ddh on ddh.pk_seq=a.dondathang_fk  "+
						"  "+
						" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=A.SANPHAM_FK " +
						" LEFT JOIN ERP_KICHBANSANXUAT_GIAY KB ON KB.PK_SEQ=KICHBANSANXUAT_FK WHERE A.PK_SEQ = "+this.id;
		////System.out.println("LENHSANXUAT: "+sql);
		String khoxuat="";
		ResultSet rs = db.get(sql);
	 
			try 
			{
				if(rs.next())
				{
					if(!rs.getString("PO").equals("-1"))
					{
						this.PO = "Pln" + rs.getString("PO");
					}
					this.kbsxId = rs.getString("kichbansanxuat_fk");
					if(this.kbsxId==null){
						this.kbsxId="";
					}
					this.KhGiaCongId=rs.getString("khachhang_fk");
					if(this.KhGiaCongId==null){
						this.KhGiaCongId="";
					}
					this.IsLSXGiacong=rs.getString("ISGIACONG");
					
					this.congtyId=rs.getString("congty_fk");
					this.spId = rs.getString("sanpham_fk");
					this.spMa=rs.getString("masanpham");
					this.nhamayId = rs.getString("nhamay_fk");
					this.ngaytao = rs.getString("ngaybatdau");
					this.ngaydukien = rs.getString("ngaydukienHT");
					this.soluongsx = rs.getString("soluong");
					this.trangthai = rs.getString("trangthai");
					this.Ghichu=rs.getString("diengiai");
					this.soPoKH= rs.getString("soPoKH");
					 
					this.Dvkdid = rs.getString("DVKD_FK");
					this.IsLsxCongNghe=rs.getString("ISLSXCONGNGHE");
					this.solenhsanxuat=rs.getString("solenhsanxuat");
					
					 
					khoxuat=rs.getString("KHOSANXUAT_FK");
				}
				rs.close();
				
				String query=" select LENHSANXUAT_FK ,SOLENHSANXUAT,SDK,CANCULAMLENH,SOHOSO , dmvt   ,NSX , HD ,isnull(QTSX,'') as QTSX,isnull(TCKT,'') as TCKT,isnull(quyetdinhluuhanh,'') as quyetdinhluuhanh  from ERP_LENHSANXUAT_HOSOLO where lenhsanxuat_fk="+this.id;
				ResultSet rshoso=db.get(query);
				if(rshoso.next()){
					this.Sohoso=rshoso.getString("SOHOSO");
					this.Dmvt=rshoso.getString("dmvt");
					this.NSX=rshoso.getString("NSX");
					this.HD=rshoso.getString("HD");
					this.QUYETDINHLUUHANH=rshoso.getString("quyetdinhluuhanh");
					this.QTSX=rshoso.getString("QTSX");
					this.TCKT=rshoso.getString("TCKT");
					this.soLenhSanXuat=rshoso.getString("SOLENHSANXUAT");
					this.SDK=rshoso.getString("SDK");
					this.canCuLamLenh=rshoso.getString("CANCULAMLENH");
				}
				rshoso.close();
				query="SELECT SOLUONGNHAPKHO,SOLUONGLAYMAU,HIEUSUAT_SX ,HIEUSUAT_DG   ,TOANCHANG  ,YKIENKHAC ,NDHL ,PHACHE ,	GHICHU_TIENDO,CHECKCHON FROM LENHSANXUAT_TONGKETLO WHERE LENHSANXUAT_FK="+this.id;
				 rshoso=db.get(query);
					if(rshoso.next()){
						this.HIEUSUAT_SX =rshoso.getString("HIEUSUAT_SX");
						this.HIEUSUAT_DG=rshoso.getString("HIEUSUAT_DG");
						this.TOANCHANG=rshoso.getString("TOANCHANG");
						this.NDHL=rshoso.getString("NDHL");
						this.PHACHE=rshoso.getString("PHACHE");
						this.StrTongket=rshoso.getString("CHECKCHON");
						this.YKIENKHAC=rshoso.getString("YKIENKHAC");
						this.SOLUONGNHAPKHO=rshoso.getString("SOLUONGNHAPKHO");
						this.SOLUONGLAYMAU=rshoso.getString("SOLUONGLAYMAU");
						 
					}rshoso.close();
					
					query="SELECT SOTT,	NOIDUNG ,	SOPKN ,	NGAY ,	KETQUA  ,	GHICHU FROM   LENHSANXUAT_PHIEUKIEMNGHIEM WHERE  LENHSANXUAT_FK="+this.id+" order by SOTT";
					 rshoso=db.get(query);
					 int t=0;
					 while(rshoso.next()){
						 SOTT[t]=""+rshoso.getInt("SOTT");
						 NOIDUNG[t]=rshoso.getString("NOIDUNG");
						 SOPKN[t] =rshoso.getString("SOPKN");
						 NGAY[t]=rshoso.getString("NGAY");
						 KETQUA[t]=rshoso.getString("KETQUA");
						GHICHU[t]=rshoso.getString("GHICHU");
						
						t++;
					 }
					 rshoso.close();
					 
					 query=" SELECT NGAYBATDAUSX ,NGAYHOANTHANH ,SONGAYCHAMSOVOILENH,GHICHUTIENDO  FROM ERP_LENHSANXUAT_TONGKETLO_TIENDO WHERE  LENHSANXUAT_FK="+this.id+" ";
					 ResultSet rsTienDo=db.get(query);
					 while(rsTienDo.next()){
						 NGAYBATDAUSX=rsTienDo.getString("NGAYBATDAUSX");
						 NGAYHOANTHANH=rsTienDo.getString("NGAYHOANTHANH");
						 SONGAYCHAMSOVOILENH =rsTienDo.getString("SONGAYCHAMSOVOILENH");
						 GHICHU_TIENDO=rsTienDo.getString("GHICHUTIENDO");
					 }
					 rsTienDo.close();
			} 
			catch (Exception err) 
			{
				this.msg=err.toString();
				err.printStackTrace();
				 
			}
			
		
			//LAY CAC CONG DOAN TRONG LENH SAN XUAT
				if(this.kbsxId.length() >0){
					
					sql=" select b.pk_seq, b.diengiai from Erp_KichBanSanXuat_CongDoanSanXuat_Giay a inner join Erp_CongDoanSanXuat_Giay b  "+
						" on a.congdoansanxuat_fk=b.pk_Seq  where a.kichbansanxuat_fk="+this.kbsxId;
					this.RsCongDoan = db.getScrol(sql);
				}
				 String  query="select * from ERP_LENHSANXUAT_BOM_GIAY_CHITIET where LENHSANXUAT_FK= "+(this.id.length() >0?this.id:0) ;
			     rs=db.get(query);
			   boolean bien_check=false;
			   try {
				if(rs.next()){
					   bien_check=true; 
				   }
				 rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		/****  BEGIN lay thong tin cac cong doan ****/
			
				sql="  select  dmvt.diengiai as diengiaibom ,  isnull( a.maysudung,'') as maysudung ,  isnull(a.KIEMDINHCHATLUONG ,'0')  as  KIEMDINHCHATLUONG, a.tinhtrang,a.soluong as soluongcd, "+ 
					"  nm.pk_seq as nhamayid, a.thutu,  a.danhmucvattu_fk ,isnull(kb.sanpham_fk,0) as spid , b.kiemdinhchatluong, "+ 
					"  b.pk_seq,b.diengiai,a.kichban_fk kichbansanxuat_fk,  a.thutu,b.nhamay_fk,nm.manhamay,nm.tennhamay , "+
					"  isnull(a.sanpham_fk,0) as sanpham_fk  ,isnull(sp.ma,'') as masp,isnull(sp.ten,'') as tensp  " +
					"   " +
					"  from ERP_LENHSANXUAT_CONGDOAN_GIAY a "+
					"  inner join Erp_CongDoanSanXuat_Giay b  on a.congdoan_fk=b.pk_Seq "+   
					"  inner join erp_nhamay nm on nm.pk_seq=b.nhamay_fk "+ 
					"  LEFT join erp_sanpham sp on SP.ma=a.masanpham and sp.congty_fk=b.congty_fk "+
					"  inner join  erp_kichbansanxuat_giay kb on kb.pk_seq= a.kichban_fk  "
					+ " left join erp_danhmucvattu dmvt on dmvt.pk_seq= a.danhmucvattu_fk"+ 
					"  where a.lenhsanxuat_fk= "+this.id+" order by a.thutu  ";
				
				
				///Viết thêm câu truy vấn SQL này thêm các công đoạn đã lưu ở bảng 
				//ERP_LENHSANXUAT_CONGDOAN_NHAPXUAT
				//PhatNguyen
					
			////System.out.println("get Cong doan : \n"+sql);
			rs=db.get(sql);
			try{
				while (rs.next()){
					ILenhSXCongDoan lsxcd=new LenhSXCongDoan();
					lsxcd.setLenhSXId(this.id);
					lsxcd.setCongDoanId(rs.getString("pk_seq"))	;
					lsxcd.setDiengiai(rs.getString("diengiai"));
					lsxcd.Setkichbansanxuat(rs.getString("kichbansanxuat_fk"));
					lsxcd.setTrangthai(rs.getString("tinhtrang"));
					if(rs.getString("tinhtrang").equals("2")){
						lsxcd.setActive("0");
					}else{
						lsxcd.setActive("1");
					}
					lsxcd.setNhaMayId(rs.getString("nhamay_fk"));
					lsxcd.setBomId(rs.getString("danhmucvattu_fk"));
					lsxcd.setThuTu(rs.getFloat("thutu"));
					lsxcd.setPhanXuong(rs.getString("manhamay"));
					lsxcd.setSanPham(rs.getString("masp") +"-"+rs.getString("tensp"));
					lsxcd.setMaSp(rs.getString("masp"));
					lsxcd.setMaySuDung(rs.getString("maysudung"));
					
					lsxcd.setBomTen(rs.getString("diengiaibom"));
					
					lsxcd.setSpId(rs.getString("sanpham_fk"));
					 
					 
				//	this.nhamayId=rs.getString("nhamayid");
					
					
					lsxcd.SetKiemDinhCL(rs.getString("KIEMDINHCHATLUONG"));
	
					lsxcd.setSoLuong(rs.getString("soluongcd"));
					
					 
					lsxcd.initCongDoanNhapXuat();
					lsxcd.initArrayNhapXuat();
					
					ListCongdoan.add(lsxcd);
					
					
				}
				
				rs.close();
			
			} 
			catch (Exception err) 
			{
				this.msg=err.toString();
				err.printStackTrace();
				 
			}
			
			
		 
			 
	/*
				sql=" SELECT  isnull(A.SOLUONG,0) as SOLUONG , ISNULL(DUTONKHO,0) AS DUTONKHO ,ISNULL(ISNL_TIEUHAO,'0') AS ISNL_TIEUHAO  , CD.DIENGIAI AS TENCONGDOAN,  \n" +
					" LENHSANXUAT_FK, CONGDOAN_FK, SP.MA AS MAVATTU, SP.TEN AS TENVATTU, A.VATTU_FK AS IDVATTU, A.KHOTT_FK, \n" +  
					" isnull( A.SOLUONG,0) as SOLUONG , isnull( A.SOLUONGCHUAN,0) AS SOLUONGCHUAN, DVT , \n" +
					" isnull(a.isTINHHAMAM,'0') as isTINHHAMAM  ,isnull(a.isTINHHAMLUONG,'0') as isTINHHAMLUONG ,ISNULL(A.HAMAM,0) AS HAMAM \n" +
					",ISNULL(A.HAMLUONG,0) AS HAMLUONG   FROM \n" +	  
					" ERP_LENHSANXUAT_BOM_GIAY A  INNER JOIN   \n" +
					" ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ=A.LENHSANXUAT_FK   \n" +
					" INNER JOIN ERP_CONGDOANSANXUAT_GIAY CD ON A.CONGDOAN_FK=CD.PK_SEQ \n" +  
					" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = A.VATTU_FK    \n" + 
					" WHERE A.LENHSANXUAT_FK = "+this.id +" ORDER BY A.SOTT \n";
				System.out.println("lay dmvt list:\n" + sql + "\n========================================="); 
				try{
					rs=db.get(sql);
					while (rs.next()){
						IDanhmucvattu_SP dmvt=new Danhmucvattu_SP();
						dmvt.setCongDoanId(rs.getString("CONGDOAN_FK"));
						dmvt.setMaVatTu(rs.getString("mavattu"));
						dmvt.setTenVatTu(rs.getString("tenvattu"));
						dmvt.setDvtVT(rs.getString("dvt"));
						dmvt.setSoLuong(rs.getString("soluong"));
						dmvt.setSoLuongChuan(rs.getString("soluongchuan"));
						dmvt.setTenCongDoan(rs.getString("tencongdoan"));
						String Idvattu=  rs.getString("Idvattu");
						dmvt.setIdVT(Idvattu);
						dmvt.setChon(rs.getString("DUTONKHO"));
						dmvt.setSoLuong(rs.getString("SOLUONG"));
						
						dmvt.SetKhoid(rs.getString("KHOTT_FK"));
						dmvt.setIsNLTieuHao(rs.getString("ISNL_TIEUHAO"));
						dmvt.setIsTinhHA(rs.getString("isTINHHAMAM"));
						dmvt.setIsTinhHL(rs.getString("isTINHHAMLUONG"));
						dmvt.setHamam(rs.getString("hamam"));
						dmvt.setHamluong(rs.getString("HAMLUONG"));
						
						double soluongthieu=( rs.getDouble("soluongchuan")-rs.getDouble("soluong")<0?0:  rs.getDouble("soluongchuan")-rs.getDouble("soluong"));
						
						List<ISpSanxuatChitiet> listct =new ArrayList<ISpSanxuatChitiet>();
						double tonkho=0;
						if(Double.parseDouble(this.trangthai)<=1){
							 tonkho=  this.getListSpSxCT(rs.getString("isTINHHAMAM"),rs.getString("isTINHHAMLUONG"),
							   			( !bien_check ? rs.getDouble("soluongchuan"): 0),Idvattu,khoxuat,rs.getString("hamam"),rs.getString("HAMLUONG"),listct,Idvattu,this.id);

						}else{
						 	  tonkho=  this.getListSpSxCT(rs.getString("isTINHHAMAM"),rs.getString("isTINHHAMLUONG"),
						 			 soluongthieu,Idvattu,khoxuat,rs.getString("hamam"),rs.getString("HAMLUONG"),listct,Idvattu,this.id);
						}
						double soluongconlai=0;
 
						if(tonkho> soluongthieu) {
							soluongconlai=0;
						}else{
							soluongconlai=soluongthieu-tonkho;
						}
						
					   		 
						   query=  " select VATTUTT_FK,SP.MA,SP.TEN,DV.DONVI,TT.SOLUONG   FROM ERP_DANHMUCVATTU_VATTU_THAYTHE  TT  " +
								  		" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=TT.VATTUTT_FK " +
								  		" LEFT JOIN DONVIDOLUONG DV ON DV.PK_SEQ=SP.DVDL_FK " +
								  		"  WHERE vattu_fk="+Idvattu+" and  DANHMUCVT_FK="+this.bomId;
						  ResultSet rssptt=db.get(query);
						  
						  while (  rssptt.next()){
							  		String vattutt_fk= rssptt.getString("VATTUTT_FK");
							  		// ADD THÊM CÁC SP CÒN THIẾU
								    tonkho = this.getListSpSxCT(rs.getString("isTINHHAMAM"),rs.getString("isTINHHAMLUONG"),soluongconlai,vattutt_fk 
										  ,khoxuat,rs.getString("hamam"),rs.getString("HAMLUONG"),listct,Idvattu,this.id);
								   
								    if(tonkho> soluongconlai) {
										soluongconlai=0;
									}else{
										soluongconlai=soluongconlai-tonkho;
										
									}
							 
						  }
						  rssptt.close();
						  if(soluongconlai==0){
							  dmvt.setChon("1");
						  }
						  
						  
						  listct =this.SapsepList(listct);
						  dmvt.setListCTkho(listct);
						  
						  List<ISpSanxuatChitiet>  list_dayc=this.getList_Dayeucau_ct(dmvt);
						  dmvt.setListCT__DaYCCK(list_dayc);
					 ////System.out.println(dmvt+"\n");
						this.dmvtList.add(dmvt);
					}
				rs.close();	
				}catch(Exception err){
					err.printStackTrace();
				}
				
				*/
				
			/*	sql="SELECT cp.*, dm.PK_SEQ, dm.TEN, dm.DONVITINH from ERP_LENHSANXUAT_BOM_CHIPHI cp inner join ERP_DINHMUCCHIPHI dm on dm.PK_SEQ = cp.DINHMUCCHIPHI_FK where cp.LENHSANXUAT_FK = "+this.id;
				 
				this.dinhmucList = new ArrayList<IErpDinhmuc>();
				try{
					rs = db.get(sql);
					while (rs.next()){
						IErpDinhmuc dm = new ErpDinhmuc();
						dm.setId(rs.getString("DINHMUCCHIPHI_FK"));
						dm.setTen(rs.getString("TEN"));
						dm.setDVT(rs.getString("DONVITINH"));
						dm.setSoluong(Double.parseDouble(rs.getString("CHIPHI")));
						this.dinhmucList.add(dm);
					}
				rs.close();	
				}catch(Exception err){
					err.printStackTrace();
				}
				*/
			/*	if(this.Dvkdid.trim().equals("100005")){
					this.CreateRs_spmoi();
					
				}*/
		this.createRs();
		//this.Kiemtranguyenlieu();
	}

	
	private List<ISpSanxuatChitiet> SapsepList(List<ISpSanxuatChitiet> listct) {
		// TODO Auto-generated method stub
		List<ISpSanxuatChitiet> list_new =new ArrayList<ISpSanxuatChitiet>();
		if(listct==null || listct.size() ==0){
			return listct;
		}
		try{
		 String query="";
		 for(int i=0;i<listct.size();i++){
				ISpSanxuatChitiet sp=listct.get(i);
				
			 query+=(query.length() >0? " UNION ALL  ":"") + 
				 	"  SELECT '"+sp.getMAPHIEU()+"' as MAPHIEU ,'"+sp.getMAPHIEU_TINHTINH()+"' MAPHIEUDINHTINH , " +
					" '"+sp.getMAPHIEU_EO()+"' PHIEUEO ,'"+sp.getBin()+"' as  BIN , " +
					"'"+sp.getBinId()+"' as  BIN_FK,'"+sp.getHAMAM()+"' as  HAMAM , " +
					
					"'"+sp.getkhuvuckhoId()+"' as  KVID,'"+sp.getkhuvuckhoTen()+"' as  KHUVUC, " +
					
					"  '"+sp.getHAMLUONG()+"' as  HAMLUONG,'"+sp.getIdSp()+"' as  SANPHAM_FK, " +
					"'"+sp.getMaSp()+"' as  MA,'"+sp.getKhoId()+"' as  KHOTT_FK, " +
					"'"+sp.getKhoTen()+"' as  makho,N'"+sp.getTenSp()+"' as  TENSP, " +
					"'"+sp.getMAME()+"' as  MAME,'"+sp.getMATHUNG()+"' as  MATHUNG, " +
					"'"+sp.getMARQUETTE()+"' as  MQ_MA,'"+sp.getMARQUETTE_FK()+"' as  MQ_ID, " +
					"'"+sp.getNGAYHETHAN()+"' as  NGAYHETHAN,'"+sp.getNGAYNHAPKHO()+"' as  NGAYNHAPKHO, " +
					"'0"+sp.getSoluongton().replaceAll(",", "")+"' as  soluongton,'"+sp.getIdSpThayThe()+"' as  idspThayThe, " +
					"'0"+sp.getSoluongtonthute().replaceAll(",", "")+"' as  soluongtonthute,'"+sp.getSoluong().replaceAll(",", "") +"' as  SOLUONGDEXUAT, " +
					"'"+sp.getSolo()+"' as  SOLO,'"+sp.getDoituongId()+"' as doituongid,'"+sp.getloaidoituong()+"' as loaidoituong   ";
				
			 
			 
		 }
		  
		 query="SELECT * FROM ( "+query+" )  AS A order by  MAPHIEU,  SOLO, CAST( MAME AS FLOAT),CAST( MATHUNG  AS FLOAT),SOLUONGDEXUAT DESC  ";
		   ////System.out.println("du lieu: "+query);
		ResultSet rs =db.get(query);
	  
		while(rs.next()){
	
		ISpSanxuatChitiet sp=new SpSanxuatChitiet();
		sp.setMAPHIEU(rs.getString("MAPHIEU"));
		sp.setMAPHIEU_DINHTINH(rs.getString("MAPHIEUDINHTINH"));
		sp.setMAPHIEU_EO(rs.getString("PHIEUEO"));
		sp.setBin(rs.getString("BIN"));
		sp.setBinId(rs.getString("BIN_FK"));
		sp.setHAMAM(rs.getString("HAMAM"));
		sp.setHAMLUONG(rs.getString("HAMLUONG"));
		sp.setIdSp(rs.getString("SANPHAM_FK"));
		sp.setMaSp(rs.getString("MA"));
		sp.setKhoId(rs.getString("KHOTT_FK"));
		sp.setKhoTen(rs.getString("makho"));
		sp.setTenSp(rs.getString("TENSP"));
		
		sp.setkhuvuckhoId(rs.getString("KVID"));
		sp.setkhuvuckhoTen(rs.getString("KHUVUC"));
 
		sp.setMAME(rs.getString("MAME"));
		sp.setMATHUNG(rs.getString("MATHUNG"));
		sp.setMARQUETTE(rs.getString("MQ_MA"));
		sp.setMARQUETTE_FK(rs.getString("MQ_ID"));
		
		sp.setNGAYHETHAN(rs.getString("NGAYHETHAN"));
		sp.setNGAYNHAPKHO(rs.getString("NGAYNHAPKHO"));
		sp.setloaidoituong(rs.getString("loaidoituong"));
		sp.setDoituongId(rs.getString("doituongid"));
		sp.setSolo(rs.getString("SOLO"));
		sp.setSoluongton(formatter.format(rs.getDouble("soluongton")));
		
		//idspThayThe là sp được thay thế
		String idspThayThe=rs.getString("idspThayThe");
		sp.setIdSpThayThe(idspThayThe);
		 
		 double soluongtonthucte=rs.getDouble("soluongtonthute");
		 
		 sp.setSoluongtonthute(formatter.format(soluongtonthucte));
		 
		 // nếu chính là IDMQ 
		 double soluong_dexuat=rs.getDouble("SOLUONGDEXUAT");
		 
	 
		 sp.setSoluong(formatter.format(soluong_dexuat)); 
		 
		 list_new.add(sp);
		}
		}catch(Exception er){
			er.printStackTrace();
		}
		 return list_new;
		
	}

	private List<ISpSanxuatChitiet> getList_Dayeucau_ct(IDanhmucvattu_SP dmvt2,String congdoanid) {
		
		// TODO Auto-generated method stub
		List<ISpSanxuatChitiet> list_dayc =new ArrayList<ISpSanxuatChitiet>();
		try{
			  
			String query= "SELECT ISNULL(A.DOITUONGID,0)DOITUONGID, ISNULL(A.LOAIDOITUONG,'') LOAIDOITUONG,    \n" + 
					  " ISNULL(CAST(A.BIN_FK AS VARCHAR(12)),'')  AS BIN_FK  ,ISNULL(BIN.MA,'') AS BIN  ,   \n" + 
					  " ISNULL(A.PHIEUEO,'') AS PHIEUEO  , ISNULL(A.MAPHIEUDINHTINH,'') AS  MAPHIEUDINHTINH     \n" + 
					  " , ISNULL(A.MAPHIEU,'') AS  MAPHIEU      \n" + 
					  " , A.SANPHAM_FK,A.VATTU_FK ,A.KHOTT_FK , KHO.MA AS MAKHO, A.SOLUONG AS SOLUONG      \n" + 
					  " ,ISNULL(A.HAMLUONG,0) AS HAMLUONG ,ISNULL(A.HAMAM,0) AS HAMAM      \n" + 
					  " ,SP.MA,SP.TEN AS TENSP , ISNULL(A.MARQ,'')  AS MQ_MA    \n" + 
					  " ,A.MAME,A.MATHUNG,A.NGAYNHAPKHO   ,  \n" + 
					  " A.SOLO,A.NGAYHETHAN     \n" + 
					  " FROM  ERP_LENHSANXUAT_BOM_GIAY_CHITIET A     \n" + 
					  " INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=A.SANPHAM_FK    \n" + 
					  " INNER  JOIN ERP_KHOTT KHO ON KHO.PK_SEQ=A.KHOTT_FK    \n" + 
					  " LEFT JOIN ERP_BIN  BIN ON BIN.PK_SEQ=A.BIN_FK     \n" +    
						  " WHERE A.LENHSANXUAT_FK="+(this.id==null||this.id.length()==0 ? "0":this.id)+"  "
						  		+ " and congdoan_fk="+congdoanid+" AND A.VATTU_FK="+dmvt2.getIdVT();
			
			System.out.println("so luong da yeu cau " + query);
			
			ResultSet rs=db.get(query);
			while(rs.next()){
				ISpSanxuatChitiet sp=new SpSanxuatChitiet();
				sp.setMAPHIEU(rs.getString("MAPHIEU"));
				sp.setMAPHIEU_DINHTINH(rs.getString("MAPHIEUDINHTINH"));
				sp.setMAPHIEU_EO(rs.getString("PHIEUEO"));
				sp.setBin(rs.getString("BIN"));
				sp.setBinId(rs.getString("BIN_FK"));
				sp.setHAMAM(rs.getString("HAMAM"));
				sp.setHAMLUONG(rs.getString("HAMLUONG"));
				sp.setIdSp(rs.getString("SANPHAM_FK"));
				sp.setMaSp(rs.getString("MA"));
				sp.setKhoId(rs.getString("KHOTT_FK"));
				sp.setKhoTen(rs.getString("makho"));
				sp.setTenSp(rs.getString("TENSP"));
				
				sp.setMAME(rs.getString("MAME"));
				sp.setMATHUNG(rs.getString("MATHUNG"));
				sp.setMARQUETTE(rs.getString("MQ_MA"));
				sp.setNGAYHETHAN(rs.getString("NGAYHETHAN"));
				sp.setNGAYNHAPKHO(rs.getString("NGAYNHAPKHO"));
				sp.setSolo(rs.getString("SOLO"));
				sp.setDoituongId(rs.getString("DOITUONGID").equals("0")?"":rs.getString("DOITUONGID"));
				sp.setloaidoituong(rs.getString("LOAIDOITUONG"));
				sp.setSoluong(formatter.format(rs.getDouble("soluong")));
				
				list_dayc.add(sp);
				
			}
			
		}catch(Exception er){
			
		}
		return list_dayc;
		
	}

	private void CreateRs_spmoi() {
		
	String	sql=" select nk.pk_seq as chungtu,nk.ngaynhapkho ,sp.solo, SUM(SOLUONGNHAP) as soluong "+
			"  from ERP_NHAPKHO nk inner join ERP_NHAPKHO_SANPHAM SP  ON SONHAPKHO_FK= "+
			"  NK.PK_SEQ WHERE SOLENHSANXUAT="+this.id+
			" AND NK.TRANGTHAI!=2 "+
			" GROUP BY  nk.pk_seq,nk.ngaynhapkho ,sp.solo";
		this.rsNhanHangSPMoi=db.get(sql);
		sql=" select THNL.PK_SEQ as chungtu,sp.ma,sp.ten  as ten,b.soluong "+ 
		" from ERP_TIEUHAONGUYENLIEU THNL  "+
		" INNER JOIN  erp_lenhsanxuat_tieuhao b ON THNL.PK_SEQ=B.TIEUHAONGUYENLIEU_FK "+
		" inner join erp_sanpham sp on sp.pk_seq= b.sanpham_fk   "+
		" where THNL.trangthai='1'  and THNL.lenhsanxuat_fk="+this.id;
 
	    this.rsTieuhaoSPMoi=db.get(sql);
		sql=" select THNL.PK_SEQ as chungtu,sp.ma,sp.ten  as ten,b.soluong "+ 
		" from ERP_TIEUHAONGUYENLIEU THNL  "+
		" INNER JOIN  erp_lenhsanxuat_tieuhao b ON THNL.PK_SEQ=B.TIEUHAONGUYENLIEU_FK "+
		" inner join erp_sanpham sp on sp.pk_seq= b.sanpham_fk   "+
		" where THNL.trangthai='1' and   THNL.lenhsanxuat_fk="+this.id;
	
		//////System.out.println( "Get Sql : "+sql);	
		
		this.rsKiemDinhSPMoi=db.get(sql);
		
		sql=" select a.pk_seq   from " +
		    " Erp_YeuCauKiemDinh a  where lenhsanxuat_Fk="+this.id;
		try{
			ResultSet rs=db.get(sql);
			if(rs.next()){
				this.CoKiemDinhCL_SPmoi="1";
			}else{
				this.CoKiemDinhCL_SPmoi="";
			}
			rs.close();
		}catch(Exception er){
			er.printStackTrace();
		}
	}

	private String getSoLuongTuCongDoan(String congDoanId) {
		
		String soluong="0";
		for(int i=0;i<ListCongdoan.size();i++){
			ILenhSXCongDoan cd=ListCongdoan.get(i);
			if(cd.getCongDoanId().equals(congDoanId)){
				return cd.getSoLuong();
			}
		}
		
		return soluong;
	}

	public void DBclose()
	{
		try 
		{
			if (this.ListVattuThem != null)
				this.ListVattuThem.clear(); 
			if (this.ListBanThanhPham != null)
				this.ListBanThanhPham.clear(); 
			if (this.ListVattuDeNghi != null)
				this.ListVattuDeNghi.clear(); 
			if (this.dinhmucList != null)
				this.dinhmucList.clear(); 
			if(this.spRs != null)
				this.spRs.close();
			if(this.nhamayRs != null)
				this.nhamayRs.close();
			if(this.kbsxRs != null)
				this.kbsxRs.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}finally{
			this.db.shutDown();
		}
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg=msg;
	}

	
	public String getSpId() 
	{
		return this.spId;
	}

	public void setSpId(String spId) 
	{
		this.spId = spId;
	}

	public ResultSet getSpRs()
	{
		return this.spRs;
	}

	public void setSpRs(ResultSet spRs)
	{
		this.spRs = spRs;
	}

	public String getKbsxId() 
	{
		return this.kbsxId;
	}

	public void setKbsxId(String kbsxId)
	{
		this.kbsxId = kbsxId;
	}

	public ResultSet getKbsxRs() 
	{
		return this.kbsxRs;
	}

	public void setKbsxRs(ResultSet kbsxRs) 
	{
		this.kbsxRs = kbsxRs;
	}

	
	public String getNhamayId()
	{
		return this.nhamayId;
	}

	public void setNhamayId(String nhamayId) 
	{
		this.nhamayId = nhamayId;
	}

	public ResultSet getNhamayRs()
	{
		return this.nhamayRs;
	}

	public void setNhamayRs(ResultSet nhamayRs) 
	{
		this.nhamayRs = nhamayRs;
	}

	public String getPlaintOrder() 
	{
		return this.PO;
	}

	public void setPlaintOrder(String PO) 
	{
		this.PO = PO;
	}

	public ResultSet getChitietNLRs() 
	{
		return this.chitietNlRs;
	}

	public void setChitietNLRs(ResultSet nlRs) 
	{
		this.chitietNlRs = nlRs;
	}

	public String getTuan(String ngay) 
	{
		String[] ngayArr = ngay.split("-");
		
		int ngayTT = Integer.parseInt(ngayArr[2]);
		
		if( 1 <= ngayTT && ngayTT <= 7)
		{
			return "Tuần 1";
		}
		
		if( 8 <= ngayTT && ngayTT <= 14)
		{
			return "Tuần 2";
		}
		
		if( 15 <= ngayTT && ngayTT <= 21)
		{
			return "Tuần 3";
		}
		
		return "Tuần 4";
	}

	public void setListDanhMuc(List<IDanhmucvattu_SP> list) 
	{
		this.dmvtList = list;
	}

	public List<IDanhmucvattu_SP> getListDanhMuc() 
	{
		return this.dmvtList;
	}

	public String getSoluongchuan() 
	{
		return this.soluongchuan;
	}

	public void setSoluongchuan(String slgchuan) 
	{
		this.soluongchuan = slgchuan;
	}

	public String getChophepTT() 
	{
		return this.cpTT;
	}

	public void setChophepTT(String chophepTT) 
	{
		this.cpTT = chophepTT;
	}

	public void Kiemtranguyenlieu() 
	{  
		try{
			//kiem tra bom het han
			/*String checkExpire=CheckBomExpire();
			if(!checkExpire.equals("")){
				this.msg="Vui lòng kiểm tra các Bom hết hạn hoặc chưa tới thời hạn sử dụng : " +checkExpire ;
				return;
			}*/
			dmvtList.clear();
 
			List<ISanPhamSanXuat> listSpSx1 = this.CopyListSpSx();
			////System.out.println("List 1 lan 1: "+listSpSx1.size());
			this.listSpSx = CongDonSanPhamSXCungMa_KhacKh();
			////System.out.println(this.listSpSx.size());
			 
		  
			this.listSpSx = listSpSx1;
		 
			
		}catch(Exception er){
			this.msg=er.toString();
			er.printStackTrace();
		}
	}
 
	private List<ISanPhamSanXuat> CopyListSpSx() {
		List<ISanPhamSanXuat> listSpSx1= new ArrayList<ISanPhamSanXuat>();
		for(int i=0;i<this.listSpSx.size();i++){
			ISanPhamSanXuat sp=this.listSpSx.get(i);
			listSpSx1.add(sp);
		}
		return listSpSx1;
	}

	private List<ISanPhamSanXuat> CongDonSanPhamSXCungMa_KhacKh() {
		
		List<ISanPhamSanXuat> listSpSx1=this.listSpSx;
		
		 for (int i=0;i<listSpSx1.size()-1;i++){
			 ISanPhamSanXuat vattu= listSpSx1.get(i);
			 for(int z =i+1; z< listSpSx1.size();z++ )
			 {													
				 ISanPhamSanXuat vt=listSpSx1.get(z); 
				// ////System.out.println("vt : "+vt.getIdSp());
				// ////System.out.println("vattu : "+vattu.getIdSp());
				 
				 if(vt.getIdSp().trim().equals(vattu.getIdSp().trim())){
					 //////System.out.println("vao trong vt : "+vt.getIdSp());
					// ////System.out.println("vao trong vattu : "+vattu.getIdSp());
					 double soluongcu=Double.parseDouble( vattu.getSoluong());
					 vattu.setSoluong(formatter.format((soluongcu+Double.parseDouble(vt.getSoluong()))));
					 listSpSx1.remove(z);
					 z=z-1;
				 }
			 }
		 }
		// ////System.out.println("listSpSx1 size: "+listSpSx1.size());
		 return listSpSx1;
	}
/*
	private void KiemTraNguyenLieu_SPMoi() {
		
	try{	
			String query = "";
			int j= ListCongdoan.size()-1;
			////System.out.println( "size cong doan : "+ListCongdoan.size());
			while ( j>=0) {
				
				ILenhSXCongDoan congdoan = ListCongdoan.get(j);
			  {
				 
				// công đoạn cuối 	
				if(j == ListCongdoan.size()-1){
					
					// THỰC HIỆN LẤY KHO SẢN XUÂT TRƯỚC -- 
					query=  " select PK_SEQ  from ERP_KHOTT where TrangThai = '1'\n" +
							" and loai in ('10') and congty_fk = '"+this.congtyId+"' and NHAMAY_FK="+ nhamayId + "\n";
					////System.out.println("query get kho xuat:\n" + query + "\n====================================================");
					ResultSet rs=db.get(query);
					String khosanxuat="";
					if(rs!=null){
						while (rs.next()){
							khosanxuat = rs.getString("pk_seq");
						}
						rs.close();
					}
				 
					
					if (khosanxuat.trim().length() == 0)
					{
						msg = "Không có kho sản xuất";						
						return;
					}
					
					
				
					double soluongtong=0;
					for(int k=0;k<listSpSx.size();k++){
						 ISanPhamSanXuat spsx=listSpSx.get(k);
						 double soluongspsx = 0;
						 double soluongchuan = 0;
						 	try{
								soluongspsx= Double.parseDouble(spsx.getSoluong());
								
							}catch(Exception er){
								
							}
							 
							if(soluongspsx >0 && spsx.getBomId().length() > 0)	{
								
								
								congdoan.setSanPham(spsx.getMaSp()+"-"+ spsx.getTenSp());
								soluongtong=soluongtong+ soluongspsx;
							 
								
							 
						query= "  SELECT ISNULL(ISNGUYENLIEUTIEUHAO,0) AS ISNL_TIEUHAO, DANHMUC.PK_SEQ AS BOMID  \n" +  
							   "  , SP1.LOAISANPHAM_FK , SP1.PK_SEQ AS IDSP, SP1.MA AS VTMA,  \n" +  
							   "  SP1.TEN   AS VTTEN, ISNULL(DVDL1.DONVI,'') AS VTDONVI,  \n" +  
							   "  VATTU.SOLUONG  AS SOLUONG,   \n" +  
							   "  DANHMUC.SOLUONGCHUAN  , isnull(VATTU.isTINHHAMAM,0) as isTINHHAMAM, " +
							   "  ISNULL(VATTU.HAMLUONG,100) AS HAMLUONG, ISNULL(VATTU.HAMAM,0) AS HAMAM " +
							   ", isnull(VATTU.isTINHHAMLUONG,0) as isTINHHAMLUONG    \n" +  
							   "  FROM ERP_DANHMUCVATTU DANHMUC     \n" +  
							   "  INNER JOIN ERP_DANHMUCVATTU_VATTU VATTU ON VATTU.DANHMUCVT_FK = DANHMUC.PK_SEQ   \n" +  
							   "  INNER JOIN ERP_SANPHAM SP1 ON VATTU.VATTU_FK = SP1.PK_SEQ     \n" +  
							   "  LEFT JOIN DONVIDOLUONG DVDL1 ON SP1.DVDL_FK = DVDL1.PK_SEQ    \n" +  
							   "  WHERE     DANHMUC.PK_SEQ= " +spsx.getBomId() + " and DANHMUC.CONGTY_FK="+this.congtyId+" order by  VATTU.SOTT ";  
						     
							rs=db.get(query);
							
							NumberFormat formatter_3 = new DecimalFormat("#######.###"); 
							IDanhmucvattu_SP vt = null;
							if(rs != null)
								while(rs.next())
								{ 
									soluongchuan = rs.getDouble("SOLUONGCHUAN");
									double soluongsx=0;
									if( rs.getDouble("SOLUONGCHUAN") * rs.getDouble("SOLUONG") <=0){
											this.msg="Vui Lòng kiểm tra lại quy cách và kiểm tra số lượng tỉ lệ tạo BOM của sản phẩm này" +spsx.getIdSp();
									}else{
											soluongsx =Double.parseDouble(formatter_3.format(soluongspsx /rs.getDouble("SOLUONGCHUAN")*rs.getDouble("SOLUONG")));
									}
									
									vt = new Danhmucvattu_SP();
									String dmvt_fk =rs.getString("bomid");
									vt.setBomId(dmvt_fk);
									String Idsp=rs.getString("IDSP");
									
									vt.setIdVT(Idsp);
									vt.setMaVatTu(rs.getString("vtMa"));
									vt.setTenVatTu(rs.getString("vtTen"));
									vt.setDvtVT(rs.getString("vtDonvi"));
									vt.setCongDoanId(congdoan.getCongDoanId());
									vt.setTenCongDoan(congdoan.getDiengiai());
									vt.setSoLuong("0");
									vt.setSoLuongChuan(rs.getString("SOLUONG"));
									 
									String isTINHHAMAM=rs.getString("isTINHHAMAM");
									String isTINHHAMLUONG=rs.getString("isTINHHAMLUONG");
									
									String  HAMAM=rs.getString("HAMAM");
									String  HAMLUONG=rs.getString("HAMLUONG");
									vt.setHamam(HAMAM);
									vt.setHamluong(HAMLUONG);
									
									vt.setIsTinhHA(isTINHHAMAM);
									vt.setIsTinhHL(isTINHHAMLUONG);
										
									vt.setIsNLTieuHao(rs.getString("isNl_Tieuhao"));
									vt.SetKhoid(khosanxuat);
									vt.setSoLuongChuan(""+soluongsx);
									
									List<ISpSanxuatChitiet> listct =new ArrayList<ISpSanxuatChitiet>();
									
								   	double tonkho=  this.getListSpSxCT(isTINHHAMAM,isTINHHAMLUONG,soluongsx,Idsp,khosanxuat,HAMAM,HAMLUONG,listct,Idsp,"");
									
									 
								   		double soluongconlai=0;
								   		
										if(tonkho> soluongsx) {
											soluongconlai=0;
										}else{
											soluongconlai=soluongsx-tonkho;
											
										}
										
										
									  query=" select VATTUTT_FK,SP.MA,SP.TEN,DV.DONVI,TT.SOLUONG   FROM ERP_DANHMUCVATTU_VATTU_THAYTHE  TT  " +
									  		" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=TT.VATTUTT_FK " +
									  		" LEFT JOIN DONVIDOLUONG DV ON DV.PK_SEQ=SP.DVDL_FK " +
									  		"  WHERE vattu_fk="+Idsp+" and  DANHMUCVT_FK="+dmvt_fk;
									  ResultSet rssptt=db.get(query);
									  
									  while (  rssptt.next()){
										  String vattutt_fk= rssptt.getString("VATTUTT_FK");
										  // ADD THÊM CÁC SP CÒN THIẾU
											  tonkho = this.getListSpSxCT(isTINHHAMAM,isTINHHAMLUONG,soluongconlai,vattutt_fk,khosanxuat,HAMAM,HAMLUONG,listct,Idsp,"");
											  if(tonkho> soluongconlai) {
													soluongconlai=0;
												}else{
													soluongconlai=soluongconlai-tonkho;
													
												}
										 
									  }
									  rssptt.close();
									  
									  // bước cuối này là phân bổ số lượng SP vào trong list SP chi tiết, và biết được sp đủ nguyên liệu hay không?
									  listct =this.SapsepList(listct);
									  
									  vt.setListCTkho(listct);
									  
									  ////System.out.println("size: "+listct.size());
									  if(soluongconlai==0){
										  vt.setChon("1");
										  
									  }
									  this.dmvtList.add(vt);
									  
										  
								}
							////System.out.println("Size du lieu :"+this.dmvtList.size());
							query = "select cp.TEN, cp.DONVITINH, vt.* from ERP_DANHMUCVATTU_DINHMUCCHIPHI vt inner join ERP_DINHMUCCHIPHI cp on cp.PK_SEQ = vt.DINHMUCCHIPHI_FK where DANHMUCVT_FK = '"+this.bomId+"'";
							rs = db.get(query);
							this.dinhmucList = new ArrayList<IErpDinhmuc>();
							if(rs != null){
								while(rs.next()){
									IErpDinhmuc dm = new ErpDinhmuc();
									dm.setTen(rs.getString("TEN"));
									dm.setDVT(rs.getString("DONVITINH"));
									dm.setId(rs.getString("DINHMUCCHIPHI_FK"));
									dm.setSoluong(soluongspsx / soluongchuan * rs.getFloat("CHIPHI"));
									this.dinhmucList.add(dm);
								}
								rs.close();
							}
							}
						}
					
					 
					congdoan.setSoLuong(""+soluongtong);
					
	
				}
				
				}
				j--;
			}
		}catch(Exception err) {
			err.printStackTrace();
			this.msg=err.toString();
		}
	}
	*/
	 
	 

	private double getListSpSxCT(String isTINHHAMAM,
			String isTINHHAMLUONG, double soluongsx2, String SpId,
			String Khosxid, String HAMAM, String HAMLUONG ,List<ISpSanxuatChitiet> list, String idspThayThe, String Lenhsanxuat_fk ,String congdoanid,boolean is_reload) {
	 
		try{
			if(Lenhsanxuat_fk==null || Lenhsanxuat_fk.length()==0){
				Lenhsanxuat_fk="0";
			}
		
			
			if(this.IsLSXGiacong.equals("1") && this.id.length() > 0){
				String query	="SELECT DISTINCT  ddh.khachhang_fk  as khachhang_fk "+ 
							" FROM ERP_LENHSANXUAT_GIAY A   "+
							" left join erp_dondathang ddh on ddh.pk_seq= a.dondathang_fk "+   
							" where  A.PK_SEQ = isnull ( ( select B.LSXBTP_LSXID from ERP_LENHSANXUAT_GIAY B where B.PK_SEQ="+this.id+" ),"+this.id+" )";
				
				////System.out.println("IsLSXGiacong query : "+query);
				ResultSet rs=db.get(query);
				if(rs.next()){
					this.KhGiaCongId =rs.getString("khachhang_fk");
				}
				rs.close();
			}
			
			   String query="select * from ERP_LENHSANXUAT_BOM_GIAY_CHITIET where congdoan_fk="+congdoanid+" and LENHSANXUAT_FK= "+(this.id.length() >0?this.id:0) ;
			   ResultSet rs=db.get(query);
			   boolean bien_check=false;
			   if(rs.next()){
				   bien_check=true; 
			   }
			   rs.close();
			   
			
			
		// TODO Auto-generated method stub
			NumberFormat formatter = new DecimalFormat("#,###,###.###"); 
			
			
			//lấy ra marq đang có tồn kho lơn nhất
			 String Mq_max=this.getMQ_max(isTINHHAMAM, isTINHHAMLUONG, soluongsx2, SpId, Khosxid, HAMAM, HAMLUONG);
			
			 
			 
			// hàm lấy ra các IDMQ có tồn kho trong hệ thống
			  query= " SELECT    ISNULL(KHO_SP.MARQ ,'')  AS IDMQ  \n" + 
						  " FROM ERP_KHOTT_SP_CHITIET  KHO_SP   \n" + 
						  " INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=KHO_SP.SANPHAM_FK \n" + 
						  " LEFT JOIN ERP_KHUVUCKHO KV ON KV.PK_SEQ=KHO_SP.KHUVUCKHO_FK \n" + 
						  " INNER JOIN ERP_KHOTT KHO ON KHO.PK_SEQ=KHO_SP.KHOTT_FK  \n" + 
						  " WHERE KHO_SP.NPP_FK="+this.nppId+" and KHO.PK_SEQ  IN ( \n" +
						  		"SELECT KHOTT_NL_FK FROM ERP_KHOSX_KHONHANNL  WHERE KHOTT_SX_FK ="+Khosxid+"   \n" + 
						  "   )   \n" + 
						  " AND KHO_SP.SANPHAM_FK = "+SpId+" and KHO.CONGTY_FK= "+this.congtyId ;
						  if(this.KhGiaCongId.length() > 0){
							 query += " AND CASE WHEN  KHO.LOAI='13' THEN  KHO_SP.DOITUONGID  ELSE "+ (this.KhGiaCongId.length() >0?this.KhGiaCongId:"0")+"  END  ="+(this.KhGiaCongId.length() >0?this.KhGiaCongId:"0")+"   \n"; 
							   
						  }else{
							  query += " and KHO.loai <> 13";
						  }
						  query +=" AND KHO_SP.AVAILABLE >0 AND NOT (isnull( SP.BATBUOCKIEMDINH,0)  =1 AND KHO_SP.MAPHIEU = '')   AND KHO_SP.NGAYNHAPKHO <='"+(this.NgayYCThenNL.length()>0?this.NgayYCThenNL: this.ngaytao)+"'  \n" +
						  " group by   KHO_SP.MARQ   ";
			
			  //System.out.println("tồn kho 1 : "+query);
			  
			 ResultSet rsmq=db.get(query);
			 double soluongmax=0;
		
			// String mq_="";
			 
			 
			 double soluongchitiet=soluongsx2;
			 double soluongtong_ct=0;
				
			 
			while (rsmq.next()){
					    String idMq=rsmq.getString("IDMQ");
					 	 
						double HAMAM_= 0 ;
						try{
						HAMAM_ =Double.parseDouble(HAMAM);
						}catch(Exception er){
							
						}
						 
						double HAMLUONG_= 0 ;
						try{
							HAMLUONG_ =Double.parseDouble(HAMLUONG);
						}catch(Exception er){
							
						}
	 
		
				query= 	  " SELECT     isnull( cast(KHO_SP.DOITUONGID as nvarchar(18)) ,'') as  DOITUONGID,ISNULL(KHO_SP.LOAIDOITUONG,'') AS loaidoituong " +
						  " , ISNULL(cast(BIN_FK as varchar(12)),'')  as BIN_FK  ,ISNULL(BIN.MA,'') AS BIN  , " +
						  " isnull(PHIEUEO,'') as PHIEUEO  , isnull(MAPHIEUDINHTINH,'') as  MAPHIEUDINHTINH " +
						  " , isnull(MAPHIEU,'') as  MAPHIEU , ISNULL(CAST(KHO_SP.IDMARQUETTE AS NVARCHAR(10)),'')  AS  MQ_ID  " +
						  "  , KHO_SP.SANPHAM_FK,KHO_SP.KHOTT_FK , KHO.MA AS makho, KHO_SP.AVAILABLE as soluong   " + 
						  " ,ISNULL(HAMLUONG,0) AS HAMLUONG ,ISNULL(HAMAM,0) AS HAMAM   " + 
						  " ,SP.MA,SP.TEN as TENSP , ISNULL(KHO_SP.MARQ,'')  AS MQ_MA " + 
						  " , isnull(KHO_SP.MAME,'') as MAME , ISNULL(KHO_SP.MATHUNG,'') AS MATHUNG ,KHO_SP.NGAYNHAPKHO ,ISNULL(KV.MA,'')  AS KHUVUC,KV.PK_SEQ AS KVID, " + 
						  " KHO_SP.SOLO,KHO_SP.NGAYHETHAN   ,KHO_SP.PK_SEQ AS IDKHOCT  " + 
						  " FROM ERP_KHOTT_SP_CHITIET  KHO_SP   " + 
						  " INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=KHO_SP.SANPHAM_FK " + 
						  " LEFT JOIN ERP_KHUVUCKHO KV ON KV.PK_SEQ=KHO_SP.KHUVUCKHO_FK " + 
						  " INNER JOIN ERP_KHOTT KHO ON KHO.PK_SEQ=KHO_SP.KHOTT_FK " +
						  " LEFT JOIN ERP_BIN  BIN ON BIN.PK_SEQ=KHO_SP.BIN_FK  " + 
						  " WHERE  KHO_SP.NPP_FK="+this.nppId+" AND KHO.PK_SEQ  IN " +
						  		" (  " +
						  		" SELECT KHOTT_NL_FK FROM ERP_KHOSX_KHONHANNL  WHERE KHOTT_SX_FK ="+Khosxid+"  " + 
						  "     )   " + 
						  " AND KHO_SP.SANPHAM_FK = "+SpId+" \n" ;
						if(this.KhGiaCongId.length() > 0){
						  query +=" AND CASE WHEN  KHO.LOAI='13' THEN  KHO_SP.DOITUONGID  ELSE "+(this.KhGiaCongId.length() >0?this.KhGiaCongId:"0")+"  END  ="+(this.KhGiaCongId.length() >0?this.KhGiaCongId:"0")+"   \n"; 
						}else{
							query+=" and KHO.LOAI <> '13' ";
						}
						query += " AND NOT ( ISNULL(SP.BATBUOCKIEMDINH,'0') ='1' AND ISNULL(KHO_SP.MAPHIEU,'')  = '') " +
						  "  AND KHO_SP.AVAILABLE >0 AND ISNULL(KHO_SP.MARQ,'')= '"+(idMq.length()==0?"":idMq)+"'  " +
						  "  AND KHO_SP.NGAYNHAPKHO <='"+(this.NgayYCThenNL.length()>0?this.NgayYCThenNL: this.ngaytao)+"' AND KHO.CONGTY_FK= "+this.congtyId +
						  "  ORDER BY KHO_SP.NGAYHETHAN, KHO_SP.MAPHIEU, KHO_SP.SOLO, CAST(KHO_SP.MAME AS FLOAT),CAST(KHO_SP.MATHUNG  AS FLOAT)  " ;
						  
						  //"  KHO_SP.MAPHIEU, KHO_SP.SOLO, CAST(KHO_SP.MAME AS FLOAT),CAST(KHO_SP.MATHUNG  AS FLOAT) " ;
				
				// MAPHIEU , SOLO, cast(DT.MAME as float), cast(DT.MATHUNG as float) asc
				
				//System.out.println("tồn kho "+query);
				  rs =db.get(query);
				
				while(rs.next()){
					
					ISpSanxuatChitiet sp=new SpSanxuatChitiet();
					sp.setMAPHIEU(rs.getString("MAPHIEU"));
					sp.setMAPHIEU_DINHTINH(rs.getString("MAPHIEUDINHTINH"));
					sp.setMAPHIEU_EO(rs.getString("PHIEUEO"));
					sp.setBin(rs.getString("BIN"));
					sp.setBinId(rs.getString("BIN_FK"));
					sp.setHAMAM(rs.getString("HAMAM"));
					sp.setHAMLUONG(rs.getString("HAMLUONG"));
					sp.setIdSp(rs.getString("SANPHAM_FK"));
					sp.setMaSp(rs.getString("MA"));
					sp.setKhoId(rs.getString("KHOTT_FK"));
					sp.setKhoTen(rs.getString("makho"));
					sp.setTenSp(rs.getString("TENSP"));
					sp.setkhuvuckhoId(rs.getString("KVID"));
					sp.setkhuvuckhoTen(rs.getString("KHUVUC"));
					sp.setkhuvuckhoId(rs.getString("KVID"));
					sp.setMAME(rs.getString("MAME"));
					sp.setMATHUNG(rs.getString("MATHUNG"));
					sp.setMARQUETTE(rs.getString("MQ_MA"));
					sp.setMARQUETTE_FK(rs.getString("MQ_ID"));
					sp.setNGAYHETHAN(rs.getString("NGAYHETHAN"));
					sp.setNGAYNHAPKHO(rs.getString("NGAYNHAPKHO"));
					sp.setSolo(rs.getString("SOLO"));
					sp.setSoluongton(formatter.format(rs.getDouble("soluong")));
					sp.setloaidoituong(rs.getString("LOAIDOITUONG"));
					sp.setDoituongId(rs.getString("DOITUONGID"));
					
					////System.out.println("Doi tuong : "+rs.getString("DOITUONGID"));
					
					//idspThayThe là sp được thay thế
					sp.setIdSpThayThe(idspThayThe);
					 double soluongct=rs.getDouble("soluong");
					 soluongct =this.getSoluongquydoi_HAMHAM_HAMLUONG(isTINHHAMLUONG,isTINHHAMAM,soluongct,rs.getDouble("HAMAM"),HAMAM_,rs.getDouble("HAMLUONG"),HAMLUONG_) ;
					 
					 
					/* // quy về chuẩn 	
					 */
				 
					 
					
					 // quy về hàm ẩm hàm lượng định mức
					 
					 sp.setSoluongtonthute(formatter.format(soluongct));
					  
					 double soluong_dexuat=0;
					 // nếu chính là IDMQ 
					 //////System.out.println("Mq_3333333max: "+Mq_max+"+ idMq: "+idMq);
					 if(Mq_max.equals(idMq)){
						
						 if(soluongct>soluongchitiet){
							 soluong_dexuat=soluongchitiet;
							 soluongchitiet=0;
							
						 }else{
							 //bằng số lượng tồn,đồng thời số lương chi tiết giảm đi một số=  soluongct (tồn)
							 soluong_dexuat= soluongct;
							 soluongchitiet=soluongchitiet-soluongct;
						 }
						 
						 soluongtong_ct+=soluong_dexuat;
					 }
					 //////System.out.println("soluong_dexuat truoc   : "+soluong_dexuat);
					 if(soluong_dexuat>0){
						
						 // quy ra lại số lượng của hàm ẩm và hàm lượng của kho
						 
						 // quy ra lại số lượng của hàm ẩm và hàm lượng cua kho   
						  
						 soluong_dexuat =this.getSoluongquydoi_HAMHAM_HAMLUONG(isTINHHAMLUONG,isTINHHAMAM, soluong_dexuat,HAMAM_,rs.getDouble("HAMAM"),HAMLUONG_ , rs.getDouble("HAMLUONG")) ;

						 //////System.out.println("soluong_dexuat truoc 4 : "+soluong_dexuat);
					 }
					double _Trangthai=0;
					 try{
						 _Trangthai=Double.parseDouble(this.trangthai);
					 }catch(Exception er){}
					 
					 // bien_check trong trường hợp init thì = true  
					 // không phải reload mà là view 
					 
					 if(Lenhsanxuat_fk.length() >0     && bien_check && !is_reload){
						 soluong_dexuat=this.getsoluongdexuat(Lenhsanxuat_fk,sp,idspThayThe,congdoanid);
					 }
					 
					 
					 ////System.out.println("Soluonglonnhat ad vao day : "+soluong_dexuat);
					 sp.setSoluong(formatter.format(soluong_dexuat)); 
					 list.add(sp);
					 
				}
				 
			}
			return    soluongtong_ct ;
			
			
		}catch(Exception er){
			er.printStackTrace();
		}
		return 0;
		 
	 }

	private double getsoluongdexuat(String lenhsanxuat_fk, ISpSanxuatChitiet sp,String SpId,String congdoanid) {
		// TODO Auto-generated method stub
		String query=	" SELECT SOLUONG  FROM ERP_LENHSANXUAT_BOM_GIAY_CHITIET WHERE  ISNULL(MAPHIEU,'')='"+sp.getMAPHIEU()+"' and isnull(PHIEUEO,'') ='"+sp.getMAPHIEU_EO()+"' " +
						" and  isnull(MAPHIEUDINHTINH,'')='"+sp.getMAPHIEU_TINHTINH()+"'  AND ISNULL(ISYCCK,'0')='0' and congdoan_fk="+congdoanid+" and LENHSANXUAT_FK= "+lenhsanxuat_fk 
						+" AND VATTU_FK="+SpId+" AND SANPHAM_FK= "+sp.getIdSp()+" AND SOLO='"+sp.getSolo()+"' " +
						" AND KHOTT_FK= "+sp.getKhoId()+"" +
						" And  ISNULL(MARQ,'') = '"+sp.getMARQUETTE()+"' AND MATHUNG='"+sp.getMATHUNG()+"' " +
						" AND MAME='"+sp.getMAME()+"' and  hamam='"+sp.getHAMAM()+"' and hamluong='"+sp.getHAMLUONG()+"'" +
						" AND NGAYNHAPKHO='"+sp.getNGAYNHAPKHO()+"' AND ngayhethan='"+sp.getNGAYHETHAN()+"' " +
						"  "+(sp.getBinId().length()>0? " and ISNULL(BIN_FK,0)="+sp.getBinId():"  AND ISNULL(  BIN_FK,0)=0  ");
		
		if(sp.getDoituongId().length()>0){
			query+=" and DOITUONGID ="+ sp.getDoituongId();
		}
		//////System.out.println("Get ton kho : "+query);
		ResultSet rs=db.get(query);
		double soluong=0;
		try{
			if(rs.next()){
				soluong=rs.getDouble("SOLUONG");
			}
			rs.close();
		}
		catch(Exception er){
			
		}
		return soluong;
	}

	private String get_sqlTonkhoct(IDanhmucvattu_SP vt_tt,String khosanxuat) {
		// TODO Auto-generated method stub
		 String query =   " SELECT KHO.MA + ' ' +KHO.TEN AS TENKHO ,KHO_SP.SOLO,MARQ , sum(KHO_SP.AVAILABLE)  as soluong " +
		 				  " ,ISNULL(HAMAM,0)AS HAMAM, ISNULL(HAMLUONG,0)AS HAMLUONG  "+
						  " FROM ERP_KHOTT_SP_CHITIET KHO_SP  "+
						  " INNER JOIN ERP_KHOTT KHO ON KHO.PK_SEQ=KHO_SP.KHOTT_FK "+
						  " WHERE KHO.PK_SEQ  IN (SELECT KHOTT_NL_FK FROM ERP_KHOSX_KHONHANNL  " +
						  " WHERE KHOTT_SX_FK ="+khosanxuat+" UNION ALL  SELECT "+khosanxuat+"  ) "+
						  " AND SANPHAM_FK ="+vt_tt.getIdVT()+
						  " AND KHO_SP.AVAILABLE >0 " +
						  " group by KHO.MA , KHO.TEN ,KHO_SP.SOLO ,MARQ ,  HAMAM   ,  HAMLUONG  "; 
		 
		////System.out.println("query : "+query);
		return query;
	}

	private double getSoLuongTon(String isTINHHAMAM, String isTINHHAMLUONG,
			double soluongsx, String SpId,String Khosxid,String HAMAM,String HAMLUONG) {
		try{
		// TODO Auto-generated method stub
			
			double HAMAM_= 0 ;
			try{
			HAMAM_ =Double.parseDouble(HAMAM);
			}catch(Exception er){
				
			}
			 
			double HAMLUONG_= 0 ;
			try{
				HAMLUONG_ =Double.parseDouble(HAMLUONG);
			}catch(Exception er){
				
			} 
			
		String	query=" SELECT  DISTINCT  isnull(KHO_SP.MARQ,'') as IDMARQUETTE  \n"+
				  " FROM ERP_KHOTT_SP_CHITIET  KHO_SP  \n"+
				  " INNER JOIN ERP_KHOTT KHO ON KHO.PK_SEQ=KHO_SP.KHOTT_FK \n"+
				  " INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ= KHO_SP.SANPHAM_FK  "+
				  " WHERE KHO.PK_SEQ  IN (SELECT KHOTT_NL_FK FROM ERP_KHOSX_KHONHANNL " +
				  " WHERE KHO_SP.NPP_FK="+this.nppId+" AND  KHOTT_SX_FK ="+Khosxid+" UNION ALL  SELECT "+Khosxid+"  ) \n"+
				  " AND SANPHAM_FK ="+SpId+ "\n" +
				  " AND KHO_SP.AVAILABLE >0  and  KHO_SP.NGAYNHAPKHO <='"+(this.NgayYCThenNL.length()>0?this.NgayYCThenNL: this.ngaytao)+"' AND  " +
				  "  NOT ( ISNULL(SP.BATBUOCKIEMDINH,'0') ='1' AND ISNULL(KHO_SP.MAPHIEU,'')  = '')   \n";
		System.out.println(" check ton du kho: "+query );
		ResultSet rsmq=db.get(query);
		// có nhiều Marquete nên nên chạy vòng lặp tính tồn kho của từng marquete ,số tồn lớn nhất là trên từng maquest  
		
		double soluongmax=0;
		 double total_sl=0;
		while(rsmq.next()){
			String IDMARQUETTE=rsmq.getString("IDMARQUETTE");
			
		  query =	  " SELECT KHO_SP.KHOTT_FK  , KHO_SP.AVAILABLE as soluong " +
					  " ,ISNULL(HAMLUONG,0) AS HAMLUONG ,ISNULL(HAMAM,0) AS HAMAM \n"+
					  " FROM ERP_KHOTT_SP_CHITIET  KHO_SP  \n"+
					  " INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ= KHO_SP.SANPHAM_FK  "+
					  " INNER JOIN ERP_KHOTT KHO ON KHO.PK_SEQ=KHO_SP.KHOTT_FK \n"+
					  " WHERE  KHO_SP.NPP_FK="+this.nppId+" and KHO.PK_SEQ  IN (SELECT KHOTT_NL_FK FROM ERP_KHOSX_KHONHANNL  WHERE KHOTT_SX_FK ="+Khosxid+" UNION ALL  SELECT "+Khosxid+"  ) \n"+
					  " AND SANPHAM_FK ="+SpId+ "\n" +
					  " AND KHO_SP.AVAILABLE >0  AND  NOT ( ISNULL(SP.BATBUOCKIEMDINH,'0') ='1' AND ISNULL(KHO_SP.MAPHIEU,'')  = '') " +
					  "  and isnull(KHO_SP.MARQ,'')='"+IDMARQUETTE+"'   and  KHO_SP.NGAYNHAPKHO <='"+(this.NgayYCThenNL.length()>0?this.NgayYCThenNL: this.ngaytao)+"' \n";
		 System.out.println(" check ton du kho: "+query );
		 ResultSet rs =db.get(query);
		 total_sl=0;
		 while(rs.next()){
			 double soluongct=rs.getDouble("soluong");
			 // quy về chuẩn 	
			 if(isTINHHAMAM.equals("1")){
				 soluongct=soluongct- soluongct* rs.getDouble("HAMAM") /100 ;
				 
			 }
			 
			 if(isTINHHAMLUONG.equals("1")){
				 soluongct =soluongct* rs.getDouble("HAMLUONG")/100;
			 }
			 
			 // quy ra lại số lượng của hàm ẩm và hàm lượng trong BOM
			 
			 if(isTINHHAMAM.equals("1")){
				 soluongct=soluongct* 100/(100- HAMAM_) ;
				 
			 }
			  
			 if(isTINHHAMLUONG.equals("1")){
				 soluongct=soluongct*100/ HAMLUONG_;
			 }
			 
			 
			  
			 total_sl +=soluongct;
			 
		 }
		 if(total_sl>soluongmax){
			 soluongmax=total_sl;
		 }
		}
		return soluongmax;
		
		
		}catch(Exception er){
			er.printStackTrace();
		}
		return 0 ;
	}

	
	
	private String  getMQ_max(String isTINHHAMAM, String isTINHHAMLUONG,
			double soluongsx, String SpId,String Khosxid,String HAMAM,String HAMLUONG) {
		 String Mqmax="";
		 
		try{
		// TODO Auto-generated method stub
			
			double HAMAM_= 0 ;
			try{
			HAMAM_ =Double.parseDouble(HAMAM);
			}catch(Exception er){
				
			}
			 
			double HAMLUONG_= 0 ;
			try{
				HAMLUONG_ =Double.parseDouble(HAMLUONG);
			}catch(Exception er){
				
			}
			 
			
			// lấy danh sách marq ,và ngày hết hạn
	String	query=" SELECT  DISTINCT    ISNULL(KHO_SP.MARQ ,'')  as IDMARQUETTE ,min (ngayhethan) as ngayhethan   \n"+
				  " FROM ERP_KHOTT_SP_CHITIET  KHO_SP  \n"+
				  " INNER JOIN ERP_KHOTT KHO ON KHO.PK_SEQ=KHO_SP.KHOTT_FK  " +
				  "  INNER   JOIN ERP_SANPHAM SP ON SP.PK_SEQ= KHO_SP.SANPHAM_FK  \n"+
				  " WHERE KHO_SP.NPP_FK="+this.nppId+//" and KHO.PK_SEQ  IN (SELECT KHOTT_NL_FK FROM ERP_KHOSX_KHONHANNL  WHERE KHOTT_SX_FK ="+Khosxid+" ) \n"+
				  " AND SANPHAM_FK ="+SpId+ "\n" +
				  " AND KHO_SP.AVAILABLE >0  AND  NOT ( ISNULL(SP.BATBUOCKIEMDINH,'0') ='1' AND ISNULL(KHO_SP.MAPHIEU,'')  = '')  " +
				  " AND KHO_SP.NGAYNHAPKHO <='"+(this.NgayYCThenNL.length()>0?this.NgayYCThenNL: this.ngaytao)+"' " +
				  " AND CASE WHEN  KHO.LOAI='13' THEN  KHO_SP.DOITUONGID  ELSE "+(this.KhGiaCongId.length() >0?this.KhGiaCongId:"0")+"  END  ="+(this.KhGiaCongId.length() >0?this.KhGiaCongId:"0")+"   " + 
				  	 " GROUP BY   ISNULL(KHO_SP.MARQ ,'')  " +
				  	 " ORDER BY NGAYHETHAN   " +
				  "   \n";
	////System.out.println("MARQ QEURY :"+query);
		ResultSet rsmq=db.get(query);
		// có nhiều Marquete nên nên chạy vòng lặp tính tồn kho của từng marquete ,số tồn lớn nhất là trên từng maquest  
		
		double soluongmax=0;
		 double total_sl=0;
		
		 
		 
		while(rsmq.next()){
			
			
			String IDMARQUETTE=rsmq.getString("IDMARQUETTE");
			
		  query =	  " SELECT KHO_SP.KHOTT_FK  , KHO_SP.AVAILABLE as soluong " +
					  " ,ISNULL(HAMLUONG,0) AS HAMLUONG ,ISNULL(HAMAM,0) AS HAMAM \n"+
					  " FROM ERP_KHOTT_SP_CHITIET  KHO_SP  \n"+
					  "  INNER   JOIN ERP_SANPHAM SP ON SP.PK_SEQ= KHO_SP.SANPHAM_FK  \n"+
					  " INNER JOIN ERP_KHOTT KHO ON KHO.PK_SEQ=KHO_SP.KHOTT_FK \n"+
					  " WHERE   KHO_SP.NPP_FK="+this.nppId+" and KHO.PK_SEQ  IN (SELECT KHOTT_NL_FK FROM ERP_KHOSX_KHONHANNL  WHERE KHOTT_SX_FK ="+Khosxid+"   ) \n"+
					  " AND SANPHAM_FK ="+SpId+ "\n" +
					  " AND KHO_SP.AVAILABLE >0  AND  NOT ( ISNULL(SP.BATBUOCKIEMDINH,'0') ='1' AND ISNULL(KHO_SP.MAPHIEU,'')  = '')  " +
					  " AND CASE WHEN  KHO.LOAI='13' THEN  KHO_SP.DOITUONGID  ELSE "+(this.KhGiaCongId.length() >0?this.KhGiaCongId:"0")+"  END  ="+(this.KhGiaCongId.length() >0?this.KhGiaCongId:"0")+"   " + 
					  " AND KHO_SP.NGAYNHAPKHO <='"+(this.NgayYCThenNL.length()>0?this.NgayYCThenNL: this.ngaytao)+"'  " +
					  " and ISNULL(KHO_SP.MARQ ,'') ='"+IDMARQUETTE+"' \n";
		  
					 ResultSet rs =db.get(query);
					 total_sl=0;
					 while(rs.next()){
						 double soluongct=rs.getDouble("soluong");
						 soluongct =this.getSoluongquydoi_HAMHAM_HAMLUONG(isTINHHAMLUONG,isTINHHAMAM,soluongct,rs.getDouble("HAMAM"),HAMAM_,rs.getDouble("HAMLUONG"),HAMLUONG_) ;
						 total_sl +=soluongct;
					 }
					 
				 if(total_sl>soluongmax){
					 soluongmax=total_sl;
					 Mqmax=IDMARQUETTE;
					 // nếu mà số lượng thỏa thì return luôn, để lấy được ngày hết hạn nhỏ nhất, vì đang lọc ngày nhỏ nhất trước, ngày nhỏ nhất đủ thì return luôn 
					 if(total_sl>= soluongsx ){
						 return Mqmax;
					 }
					// nếu không thì cuối cùng vẫn trả về Mqmax
				 }
		}
		return Mqmax;
		
		
		}catch(Exception er){
			er.printStackTrace();
		}
		return Mqmax ;
	}
	
		public double getSoluongquydoi_HAMHAM_HAMLUONG(  String isTINHHAMLUONG, String isTINHHAMAM, double A,   double a2,   double a1, double b2, double b1){
				
				double soluongqd=A;
					if(isTINHHAMLUONG.equals("1") && isTINHHAMAM.equals("1")  ){
					
					if((100-a1)* b1 !=0 ) {
						soluongqd =(A* (100-a2)*b2)/((100-a1)* b1);
					}else{
						soluongqd=A ;
					}
				 }else if(isTINHHAMLUONG.equals("1")){
					 	if( b1 !=0 ) {
							soluongqd =( A * b2)/(  b1);
						}else{
							soluongqd=A ;
						}
				 }else if(isTINHHAMAM.equals("1")){
					 	if( 100-a1 !=0 ) {
							soluongqd =(   A * (100-a2) )/(  100-a1);
						}else{
							soluongqd=A ;
						}
				 } 
				return soluongqd;
			}


	private String CongDoanHoatDong() {
	String congdoanhoatdong="1";
		if(this.ListCongdoan!=null){
			for(int i=0;i<this.ListCongdoan.size();i++){
				ILenhSXCongDoan lenh=this.ListCongdoan.get(i);
				if(lenh.getActive().equals("1")){
					congdoanhoatdong=congdoanhoatdong+","+lenh.getCongDoanId();
				}
			}
		}
		return congdoanhoatdong;
	}
	
	private boolean checkNgayTieuHao(){
		if(this.IdNhapkho.length() >0){
		String queryNhanHang = "select NGAYNHAN from ERP_NHANHANG where  "
				+ " substring(ngaynhan,1,7) <>'"+this.getNgaytieuhao().substring(0,7)+"' and LENHSANXUAT_FK = "+this.lsxIds +" and pk_seq in ("+this.IdNhapkho+")";
		ResultSet rs = this.db.get(queryNhanHang);
		String ngaynhan = "";
		
		try {
			if (rs.next()){
				ngaynhan = rs.getString("NGAYNHAN");
				rs.close();
				return false;
			}
			rs.close();
		} catch (SQLException e) {
			this.msg = " Lỗi "+ e.getMessage();
			e.printStackTrace();
			return false;
		}
		}
		return true;
	}
	// return true if ngayTH <= ngayNhan
	@SuppressWarnings("deprecation")
	private boolean compare2Dates(String ngayNhan, String ngayTH){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date1 = sdf.parse(ngayNhan);
			Date date2 = sdf.parse(ngayTH);
			if (date2.compareTo(date1) < 0) {
				this.msg = "Bạn đang chọn ngày chứng từ tiêu hao nhỏ hơn ngày nhập thành phẩm. Vui lòng chọn lại. ";
				return false;
			}
			if(date1.getMonth() != date2.getMonth()){
				this.msg = "Bạn đang chọn tháng chứng từ tiêu hao không cùng tháng nhập thành phẩm. Vui lòng chọn lại. ";
				return false;
			}
		} catch (ParseException e) {
			
			e.printStackTrace();
			return false;
		}
		return true;
	}
 

	public boolean tieuhaoLsx() 
	{
			if(this.msg.trim().length() > 0)
			{
				this.msg = "Vui lòng kiểm tra lại các thông tin: \n" + this.msg;
				return false;
			}
			if(this.khoanmucchiphi.trim().length() <= 0){
				this.msg = "Vui lòng kiểm tra lại nhóm chi phí: \n";
				return false;
			}
			
			if(this.checkNgayTieuHao() == false){
				this.msg="Vui lòng chọn ngày tiêu hao cùng tháng với nhận hàng";
				return false;
			}
  
			Utility util= new Utility();
			
		/*	msg= util.checkNgayHopLe(this.db, this.getNgaytieuhao(),this.congtyId);
			if(this.msg.trim().length() > 0)
			{
				this.msg = "Vui lòng kiểm tra lại các thông tin: \n" + this.msg;
				return false;
			}*/
			
			//Check vattu List
			if(this.dmvtList.size() <= 0)
			{
				this.msg = "Không có danh mục vật tư nào để tiêu hao.";
				return false;
			}
		
			for(int i = 0; i < this.dmvtList.size(); i++)
			{
				IDanhmucvattu_SP vattu = this.dmvtList.get(i);
				List<ISpDetail> spDetail = vattu.getSpDetailList();
				double soluongtong=0;
				for(int j = 0; j < spDetail.size(); j++)
				{
					ISpDetail detail = spDetail.get(j);
					soluongtong=soluongtong+Double.parseDouble(detail.getSoluong());
				}
				 
				if(Double.parseDouble( formatter.format( Double.parseDouble(vattu.getSoLuongTHThucTe()))) -Double.parseDouble(formatter.format(soluongtong)) >0 ){
					this.msg = "Vui lòng kiểm tra lại Bean / Lô tiêu hao của vật tư: " + vattu.getMaVatTu() + ", " + vattu.getTenVatTu();
					return false;
				}
			}
			
			String query =	  
				" select lsx.pk_seq as doituongid ,KHO.pk_seq \n" +
				"from ERP_KHOTT KHO \n" +
				" inner join erp_lenhsanxuat_congdoan_giay lsx on lsx.KHOSANXUAT_FK=KHO.PK_sEQ \n" +
				" where    lsx.lenhsanxuat_fk ="+this.lsxIds +" and lsx.congdoan_fk="+this.CdsxId;

			String khoTieuhao_fk="";
			String doituongid="";
			ResultSet rsKho = db.get(query);
			if(rsKho != null)
			{
				try 
				{
					if(rsKho.next())
					{
						khoTieuhao_fk = rsKho.getString("pk_seq");
						doituongid= rsKho.getString("doituongid");
					}
					rsKho.close();
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(khoTieuhao_fk==null  || khoTieuhao_fk.equals("")){
				this.msg = "Không xác định được kho sản xuất ";
				return false;
			}
			boolean IsQlkhuvuc_khoTieuhao=false;
			
			if(util_kho.getIsQuanLyKhuVuc(khoTieuhao_fk,db).equals("1")){
				IsQlkhuvuc_khoTieuhao=true;
			}
			String nam = this.getNgaytieuhao().substring(0, 4);
			String thang = this.getNgaytieuhao().substring(5, 7);
			
			try 
			{
			db.getConnection().setAutoCommit(false);
  
		 
				query=" INSERT INTO ERP_TIEUHAONGUYENLIEU ( LENHSANXUAT_FK,CONGDOAN_FK,TRANGTHAI,NGUOITAO,NGAYTAO,NGUOISUA,NGAYSUA,NGAYTIEUHAO,NGAYHETHONG,GIOCHOT,LOAISANPHAM,nhomchiphi_fk) "+
				" VALUES ( "+this.lsxIds+","+this.CdsxId+",0,"+this.userId+",'"+this.getDateTime()+"',"+this.userId+",'"+this.getDateTime()+"','"+this.getNgaytieuhao()+"',GETDATE(),'"+util.getTime()+"','"
				+this.loaisanphamTH+"','"+this.khoanmucchiphi+"')"; 
		  	////System.out.println("insert tieu hao "+query);
			if(!db.update(query))
			{
				this.msg = "1.Khong the cap nhat ERP_TIEUHAONGUYENLIEU: " + query;
				db.getConnection().rollback();
				return false;
			}
			query = "select SCOPE_IDENTITY()   as clId";
			ResultSet rs = this.db.get(query);
			String chungtuid = "";
			if (rs != null)
			{
				if (rs.next())
					chungtuid = rs.getString("clId");
				rs.close();
			}
			String [] mangnhapkhoid=this.IdNhapkho.split(",");
			for(int i=0;i < mangnhapkhoid.length;i++){
				if(mangnhapkhoid[i].length()>2){
					query="insert into ERP_LSXTIEUHAO_NHAPKHO (TIEUHAO_FK,NHAPKHO_FK) values ("+chungtuid+","+mangnhapkhoid[i]+")";
					if(!db.update(query))
					{
						this.msg = "1.Khong the cap nhat ERP_LSXTIEUHAO_NHAPKHO: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			 	
			
			for(int i = 0; i < this.dmvtList.size(); i++)
			{
				IDanhmucvattu_SP vattu = this.dmvtList.get(i);
				if(Double.parseDouble(vattu.getSoLuongTHThucTe().replaceAll(",", "")) >0 ){
				 
					query = " Insert ERP_LENHSANXUAT_TIEUHAO ( TIEUHAONGUYENLIEU_FK,KHOTT_FK, sanpham_fk, soluong, dongia, thanhtien,loai  ,SOLUONGDINHMUC) " +
							" select  "+chungtuid+","+khoTieuhao_fk+", pk_seq,  " + vattu.getSoLuongTHThucTe().replaceAll(",", "") + " ,0,0 "+
							" ,'"+vattu.getLoai()+"',"+vattu.getSoLuong()+"   from ERP_SanPham where pk_seq = '" + vattu.getIdVT()+"'";
					 
					if(!db.update(query))
					{
						this.msg = "1.Không thể cập nhật : " + query;
						db.getConnection().rollback();
						return false;
					}
				 
					if(vattu.getSoLuongTHThucTe().trim().length() > 0)
					{
						List<ISpDetail> spDetail = vattu.getSpDetailList();
						for(int j = 0; j < spDetail.size(); j++)
						{
							ISpDetail detail = spDetail.get(j);
							String solo = detail.getSolo();
							double soluongct =0;
							try{
								soluongct =Double.parseDouble(detail.getSoluong());
							}catch(Exception er){
							}
							if(IsQlkhuvuc_khoTieuhao){
								 if( detail.getKhuId()==null || detail.getKhuId().equals("")){
									 this.msg = "Vui lòng kiểm tra lại.Kho tiêu hao đang được cấu hình có xác định khu vực. Nhưng sản phẩm chi tiết không được chọn khu vực";
										db.getConnection().rollback();
										return false;
								 }
							}
							 
							query = " INSERT ERP_LENHSANXUAT_TIEUHAO_CHITIET ( DOITUONGID,TIEUHAONGUYENLIEU_FK, KHOTT_FK, SANPHAM_FK, SOLO, NGAYHETHAN, SOLUONG,LOAI,KHUVUCKHO_FK,NGAYBATDAU   ,MATHUNG,MAME,NGAYNHAPKHO,MARQ,BIN_FK,HAMAM,HAMLUONG,MAPHIEU,PHIEUEO,MAPHIEUDINHTINH ,nsx_fk) " +
									" values ( "+doituongid+","+chungtuid+","+khoTieuhao_fk+","+vattu.getIdVT()+", '" + detail.getSolo() + "', '"+detail.getNgayhethan()+"', " + detail.getSoluong() + " " +
									" ,'"+vattu.getLoai()+"',"+(detail.getKhuId().length() >0?detail.getKhuId():"0")+",'"+detail.getNgaybatdau()+"','"+detail.getMathung()+"','"+detail.getMame()+"' " +
											",'"+detail.getNgaynhapkho()+"','"+detail.getMarq()+"',"+(detail.getVitriId().length()>0?detail.getVitriId():"NULL")+", '"+detail.getHamam()+"','"+detail.getHamluong()+"','"+detail.getMaphieu()+"','"+detail.getPHIEUEO()+"','"+detail.getMAPHIEUDINHTINH()+"',"+detail.getNSX_FK()+")";
 
							if(db.updateReturnInt(query)!=1)
							{
								this.msg = "1.Không thể cập nhật ERP_LENHSANXUAT_TIEUHAO_CHITIET: " + query;
								db.getConnection().rollback();
								return false;
							}
							Kho_Lib kholib=new Kho_Lib();
							 
							kholib.setLoaichungtu("erplenhsanxuat.java 3223 :  ERP_TIEUHAONGUYENLIEU"+ chungtuid);
							
							kholib.setNgaychungtu(this.getNgaytieuhao());
							 
							kholib.setKhottId(khoTieuhao_fk);
							
							kholib.setBinId(detail.getVitriId());
						 
							
							kholib.setSolo(solo);
							String spid= vattu.getIdVT();
							kholib.setSanphamId(spid);
							
							
							kholib.setMame(detail.getMame());
							kholib.setMathung(detail.getMathung());
							kholib.setMaphieu(detail.getMaphieu());
							
							kholib.setMaphieudinhtinh(detail.getMAPHIEUDINHTINH());
							kholib.setPhieuEo(detail.getPHIEUEO());
							
							kholib.setNgayhethan(detail.getNgayhethan());
							kholib.setNgaysanxuat("");
							
							kholib.setNgaynhapkho(detail.getNgaynhapkho());
							 
							kholib.setMARQ(detail.getMarq());
							kholib.setDoituongId(doituongid);
							kholib.setLoaidoituong("5");
							 
							
							kholib.setHamluong(detail.getHamluong());
							kholib.setHamam(detail.getHamam());
							 
					    	kholib.setBooked(soluongct);
							kholib.setSoluong(0);
							kholib.setAvailable((-1)*soluongct);
						    kholib.setNppid(this.nppId);
						    kholib.setNsxId(detail.getNSX_FK());
							
							String msg1= util_kho.Update_Kho_Sp_Tra(db,kholib);
						    if( msg1.length() >0)
							{
								// this.msg = msg1;
						    	this.msg = msg1;
								db.getConnection().rollback();
								return false;
								
							}
						 
							 
						}
						
					}
				}
				 
			}
			
			 
	 
			/*query=" UPDATE ERP_TIEUHAONGUYENLIEU SET TRANGTHAI=1 WHERE PK_SEQ="+chungtuid;
			 if(db.updateReturnInt(query)!=1)
				{
					this.msg = "4.Không thể cập nhật ERP_KHOTT_SANPHAM: " + query;
					db.getConnection().rollback();
					return false;
			}
			 Library lib=new Library();
			 
			 String msg1=lib.capnhatketoan_Xuat_Tieuhaolsx(this.userId, db, chungtuid, false, this.congtyId);
			 
			 if(msg1.length() >0){
			 	this.msg = msg1;
				db.getConnection().rollback();
				return false;
			 }*/
			 
			 
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			e.printStackTrace();
			this.msg = "Ecception: " + e.getMessage();
			return false;
		}

		return true;
	}

	public boolean checkTieuHaoLsx() 
	{ 
		is_init=true; 
		if(this.IdTieuHao.length()==0){
			// kiem tra xem có tieu hao chưa?
			String sql="select pk_seq from erp_tieuhaonguyenlieu where trangthai=1 and pk_seq="+this.IdTieuHao;
			ResultSet rs= db.get(sql);
			try{
			if(rs!=null){
				if(rs.next()){
					this.IdTieuHao=rs.getString("pk_seq");
					
				}
			}
			}catch(Exception er){
				
			}
			
		}
		
		if(this.IdTieuHao.length() > 0) //Da tieu hao
		{
			this.initLsx_TieuHao();
			return true;
		}
		else
		{
			//Chua tieu hao, co the thay doi danh muc vat tu, phai luu lai truoc
			
			
			this.initTieuHao();
			return false;
		}
		
		
	}

	public  void initTieuHao() 
	{					
		
				if(this.ngaytieuhao==null || this.ngaytieuhao.length() ==0){
					this.ngaytieuhao=this.getDateTime();
					
				}
				if(this.lsxIds!=null && this.CdsxId!=null && this.lsxIds.length() >0 && this.CdsxId.length() >0){
				
				try{
		
					 String  query=" "; 
				
					 String doituong="";
					 String loaidoituong="5";
					 String khoTieuhao_fk="";
					 String dvdl_fk="";
				  query=     " select  dmvt.dvdl_fk , dvkd_fk,lsxcd.pk_seq as doituong, isnull(solenhsanxuat,'') as solenhsanxuat, lsxcd.khosanxuat_fk, a.trangthai, ISNULL(lenhsanxuatdukien_fk, '-1') as PO, kichbansanxuat_fk, "+
							 " ngaybatdau, ngaydukienHT, lsxcd.soluong, cd.nhamay_fk,cd.diengiai "+
							"	, isnull((select cast(nk.PK_SEQ as nvarchar(50)) + ',' as [text()]\n" + 
							"	from ERP_NHANHANG nk \n" +
							"	where nk.LENHSANXUAT_FK = a.PK_SEQ and TRANGTHAI = 1\n" +
							"	for XML PATH('') ), '') as nhapKhoId			\n" +						 
							 " from ERP_LENHSANXUAT_Giay   a "+
							 " inner join erp_lenhsanxuat_congdoan_giay lsxcd on lsxcd.lenhsanxuat_fk=a.pk_seq "+
							 " inner join erp_congdoansanxuat_giay cd on cd.pk_seq=lsxcd.congdoan_fk  "
							 + " left join  ERP_DANHMUCVATTU dmvt on dmvt.pk_seq= lsxcd.danhmucvattu_fk "+
							 " where   a.pk_seq ="+this.lsxIds +" and lsxcd.congdoan_fk="+this.CdsxId; 
			 
						//////System.out.println("Du lieu lấy  :"+query);
			            ResultSet    rs = db.get(query);
						double soluongsxtrongcd=0;
						 
						if(rs.next())
						{
							if(!rs.getString("PO").equals("-1"))
							{
								this.PO = "Pln" + rs.getString("PO");
							}
							this.kbsxId = rs.getString("kichbansanxuat_fk");
							this.nhamayId = rs.getString("nhamay_fk");
							this.ngaytao = rs.getString("ngaybatdau");
							this.ngaydukien = rs.getString("ngaydukienHT");
							this.soluongsx = rs.getString("soluong");
							soluongsxtrongcd=rs.getDouble("soluong");
							this.trangthai = rs.getString("trangthai");
							this.tencongdoanCurrent=rs.getString("diengiai");
							this.SoLSX=rs.getString("solenhsanxuat");
							khoTieuhao_fk = rs.getString("khosanxuat_fk");
							this.Dvkdid=rs.getString("dvkd_fk");
							doituong=rs.getString("doituong");
							dvdl_fk=rs.getString("dvdl_fk");
							
						}
						rs.close();
					  
			 
			 
			 
						boolean IsQlkhuvuc_khoTieuhao=false;
						
						if(util_kho.getIsQuanLyKhuVuc(khoTieuhao_fk,db).equals("1")){
							IsQlkhuvuc_khoTieuhao=true;
						}
						
						dmvtList.clear();
						
				 
						 
						double soluongnhanthucte=0;
						String nhapkhoid_[]=IdNhapkho.split(",");
						for(int i=0 ;i< nhapkhoid_.length;i++ ){
							//lấy số lượng quy đổi giống với số lượng trong BOM của LSX,theo từng nhập kho
							
				
							query=  " SELECT A.SOLUONG AS SL, CASE WHEN A.DVDL_FK = "+dvdl_fk+" THEN A.SOLUONG ELSE A.SOLUONG* QC.SOLUONG2/QC.SOLUONG1  END AS SOLUONG \n"+
							" FROM ( \n"+
							  
							" SELECT NKSP.SANPHAM_FK,SP.DVDL_FK,    \n"+
							" SUM( NKSP.SOLUONGNHAN  ) AS SOLUONG     FROM ERP_NHANHANG NK   \n"+   
							" INNER JOIN ERP_NHANHANG_SANPHAM NKSP ON NKSP.NHANHANG_FK=NK.PK_SEQ  \n"+ 
							" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ= NKSP.SANPHAM_FK    \n"+
							" WHERE NK.PK_SEQ   IN ("+nhapkhoid_[i]+")   \n"+
							" GROUP BY SP.DVDL_FK   ,NKSP.SANPHAM_FK  \n"+
							" ) AS A  \n"+
							" LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK=A.SANPHAM_FK AND QC.DVDL2_FK= "+dvdl_fk;
							
							System.out.println("lenh lay nl:\n" + query + "\n============================");
							ResultSet rsnhapkho=db.get(query);
							if (rsnhapkho != null)
							{
								 
									if(rsnhapkho.next()){
										soluongnhanthucte= soluongnhanthucte+ ( rsnhapkho.getDouble("SOLUONG") >0 ?rsnhapkho.getDouble("SOLUONG") :rsnhapkho.getDouble("SL"));
									}
								 
							}				
						}
		 
				 if(soluongnhanthucte > 0) {
					 
					 
					  
					 //dang test
					 query=  " SELECT  DISTINCT   ISNULL(CT.SANPHAM_FK,A.VATTU_FK) as SANPHAM_FK ,ISNULL(A.LOAI,'1' ) AS LOAI,  B.PK_SEQ AS IDVT, B.MA AS VTMA, "+  
							 " B.TEN +   ' ' + B.MA AS VTTEN,  D.DONVI AS VTDONVI,    "+
							 " CASE WHEN A.VATTU_FK <> CT.SANPHAM_FK  THEN round(CT.SOLUONGDINHMUC,3)   "+
							 " ELSE A.SOLUONGCHUAN END SOLUONGCHUAN  FROM ERP_LENHSANXUAT_BOM_GIAY_CHITIET CT   "+
							 " FULL OUTER JOIN ERP_LENHSANXUAT_BOM_GIAY A     ON a.congdoan_fk=ct.congdoan_fk  and  A.LENHSANXUAT_FK=CT.LENHSANXUAT_FK  AND CT.VATTU_FK=A.VATTU_FK "+   
							 " INNER JOIN ERP_SANPHAM B ON ISNULL(CT.SANPHAM_FK,A.VATTU_FK) = B.PK_SEQ "+ 
							 " INNER JOIN DONVIDOLUONG D ON B.DVDL_FK = D.PK_SEQ   "+
							 " WHERE   ISNULL (CT.congdoan_fk, A.congdoan_fk)= "+this.CdsxId+"  and  ISNULL (CT.LENHSANXUAT_FK, A.LENHSANXUAT_FK) ="+this.lsxIds;
					 System.out.println("cau lenh lay danh muc vat tu: \n" + query + "\n=======================================");
					rs = db.get(query);
					List<IDanhmucvattu_SP> dmvtList = new ArrayList<IDanhmucvattu_SP>();
					IDanhmucvattu_SP vt = null;
					while(rs.next())
					{
							vt = new Danhmucvattu_SP();
							vt.setIdVT(rs.getString("idvt"));
							vt.setMaVatTu(rs.getString("vtMa"));
							vt.setTenVatTu(rs.getString("vtTen"));
							vt.setDvtVT(rs.getString("vtDonvi"));
							double soluongth=rs.getDouble("SOLUONGCHUAN");
							vt.setSoLuong(formatter.format(soluongth));
							 
							vt.setSoLuongChuan(formatter.format(rs.getDouble("SOLUONGCHUAN")));
							
							vt.setLoai(rs.getString("loai"));
							List<ISpDetail> spDetail = new ArrayList<ISpDetail>();
							// chỉ lấy những lô có khai trong lSX
							 
							//dang test											
							query=" SELECT isnull(nsx.pk_Seq,0) as nsx_fk, isnull(nsx.ten,'') as NHASANXUAT  ,ISNULL(A.MAPHIEU,'') AS MAPHIEU , ISNULL(A.PHIEUEO,'') AS PHIEUEO , ISNULL (A.MAPHIEUDINHTINH,'') AS MAPHIEUDINHTINH  ,    \n" + 
									  " A.HAMLUONG,A.HAMAM ,isnull(BIN.MA,'')  AS BIN ,ISNULL(CAST(BIN.PK_SEQ AS NVARCHAR(12)),'0')  AS BINID ,  A.NGAYBATDAU ,     \n" + 
									  " ISNULL(CAST(KV.PK_SEQ AS VARCHAR(12)),'')   AS KHUID, ISNULL(KV.TEN,'') AS KHUTEN, DV.DONVI,A.SANPHAM_FK,  ISNULL(AVAILABLE, 0) AS AVAILABLE, SOLUONGtieuhao , "+
									  " a.SOLO ,isnull(A.MATHUNG,'') as MATHUNG,ISNULL(A.MAME,'') AS MAME ,A.NGAYHETHAN ,A.NGAYNHAPKHO,ISNULL(A.MARQ,'') AS MARQ       \n" + 
									  " FROM  (  "
									  + "   select a.*,ct.SOLUONGtieuhao   from  ERP_KHOTT_SP_CHITIET a "
									  + " left join (" +
									  "		        SELECT  isnull(nsx_fk,0) as nsx_fk ,ISNULL(B.DOITUONGID,0) AS DOITUONGID  , BIN.PK_SEQ as BIN_FK,B.SANPHAM_FK , ISNULL(B.SOLO,'') AS SOLO , B.NGAYHETHAN, B.NGAYNHAPKHO, B.MAME, B.MATHUNG ,      \n" + 
									  "				B.MAPHIEU, B.MAPHIEUDINHTINH , B.PHIEUEO,  B.MARQ, ISNULL(B.HAMAM, '0') AS HAMAM, ISNULL(B.HAMLUONG, '100') AS HAMLUONG ,  (B.SOLUONG) AS SOLUONGtieuhao        \n" + 
									  "				FROM ERP_TIEUHAONGUYENLIEU  A INNER JOIN ERP_LENHSANXUAT_TIEUHAO_CHITIET  B ON A.PK_SEQ = B.TIEUHAONGUYENLIEU_FK   \n" + 
									  "				LEFT JOIN ERP_BIN BIN ON B.BIN_FK = BIN.PK_SEQ      \n" + 
									  "				inner join ERP_KHOTT khonhan on khonhan.PK_SEQ= B.KHOTT_FK    \n" + 
									  "				WHERE A.TRANGTHAI = '0'	AND A.PK_SEQ=  "+(this.IdTieuHao!=null && this.IdTieuHao.length()>0? this.IdTieuHao:"0")+    
									  " )     CT  ON      CT.SANPHAM_FK=A.SANPHAM_FK    AND   A.SOLO=CT.SOLO AND  isnull( A.MAME,'')=isnull(CT.MAME,'')     \n" + 
									  " AND  isnull(A.MATHUNG,'')=isnull(CT.MATHUNG,'')      \n" + 
									  " AND ISNULL(CT.MARQ,'') = ISNULL(A.MARQ,'')         \n" + 
									  " AND ISNULL(CT.MAPHIEU,'')= ISNULL(A.MAPHIEU,'')      \n" + 
									  " AND ISNULL(CT.MAPHIEUDINHTINH,'')= ISNULL(A.MAPHIEUDINHTINH,'')      \n" + 
									  " AND ISNULL(CT.PHIEUEO,'')= ISNULL(A.PHIEUEO,'')     \n" + 
									  " AND ISNULL(CT.HAMAM,'0')= ISNULL(A.HAMAM,'0')     \n" + 
									  " AND ISNULL(CT.HAMLUONG,'100')= ISNULL(A.HAMLUONG,'100')     \n" + 
									  " AND ISNULL(CT.ngaynhapkho,'')= ISNULL(A.ngaynhapkho,'')     \n" + 
									  " AND ISNULL(CT.ngayhethan,'')= ISNULL(A.ngayhethan,'')  "
									  + " and ISNULL(CT.BIN_FK,0)=ISNULL(A.BIN_FK,0)    and ISNULL(CT.nsx_fk,0)=ISNULL(A.nsx_fk,0)    and ISNULL(CT.doituongid,0)=ISNULL(A.doituongid,0)  "
									  + "  where   a.khott_fk = "+khoTieuhao_fk+" and  "
									  + " a.sanpham_fk =   "+vt.getIdVT()+"  and AVAILABLE + isnull(ct.SOLUONGtieuhao,0 )  > 0    and A.NGAYNHAPKHO<='"+this.ngaytieuhao+"' and a.DOITUONGID="+doituong+" and a.loaidoituong="+loaidoituong+" "
									   + " ) A    \n" + 
									  " INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=A.SANPHAM_FK     \n" + 
									  " LEFT JOIN DONVIDOLUONG DV ON DV.PK_SEQ=SP.DVDL_FK   LEFT JOIN ERP_KHUVUCKHO KV ON KV.PK_SEQ= A.KHUVUCKHO_FK      \n" + 
									  " LEFT JOIN ERP_BIN BIN ON BIN.PK_SEQ=A.BIN_FK  "+
								      " left join erp_nhasanxuat nsx on nsx.pk_seq=a.nsx_fk  \n" + 
									  "   \n" + 
									  " order by a.ngaynhapkho asc, a.AVAILABLE asc";
							System.out.println("cau lenh lay theo so lo:\n" + query + "\n============================================================");
							
							double tongsoluongth=soluongth;
							ResultSet rsSpDetail = db.get(query);
							if(rsSpDetail != null)
							{
								try 
								{
									while(rsSpDetail.next())
									{
										double avaiD = rsSpDetail.getDouble("AVAILABLE");
										String maspD = rsSpDetail.getString("SANPHAM_FK");
										String soloD = rsSpDetail.getString("SOLO");
										String donvi=rsSpDetail.getString("donvi");
										
										double soluongth_ct=0;
										
										if(tongsoluongth <  avaiD )
										{
											soluongth_ct=tongsoluongth;
											tongsoluongth=0;
										}else{
											soluongth_ct= avaiD;
											tongsoluongth=tongsoluongth-avaiD;
										}
										
										
										ISpDetail spDetail2 = new SpDetail(maspD, soloD, formatter.format(soluongth_ct),donvi);
										spDetail2.setSoluongton(""+(rsSpDetail.getDouble("AVAILABLE")+rsSpDetail.getDouble("soluongtieuhao")));
										//spDetail2.setSoluongton(""+avaiD);
										spDetail2.setKhu(rsSpDetail.getString("khuten"));
										spDetail2.setKhuId(rsSpDetail.getString("khuid"));
										
										if(this.is_init){
											spDetail2.setSoluong(formatter.format(rsSpDetail.getDouble("soluongtieuhao")));
										}else{
											spDetail2.setSoluong(formatter.format(0));
										}
										// ,A.MATHUNG,A.MAME,A.NGAYNHAPKHO,A.MARQ
										
										spDetail2.setMarq(rsSpDetail.getString("MARQ"));
										
										
										spDetail2.setMathung(rsSpDetail.getString("MATHUNG"));
										spDetail2.setMame(rsSpDetail.getString("MAME"));
										spDetail2.setNgaynhapkho(rsSpDetail.getString("NGAYNHAPKHO"));
										spDetail2.setNgayhethan(rsSpDetail.getString("NGAYHETHAN"));
										spDetail2.setVitri(rsSpDetail.getString("BIN"));
										spDetail2.setVitriId(rsSpDetail.getString("BINID"));
										spDetail2.setHamluong(rsSpDetail.getString("hamluong"));
										spDetail2.setHamam(rsSpDetail.getString("hamam"));
										spDetail2.setMaphieu(rsSpDetail.getString("maphieu"));
										spDetail2.setMAPHIEUDINHTINH(rsSpDetail.getString("MAPHIEUDINHTINH"));
										spDetail2.setPHIEUEO(rsSpDetail.getString("PHIEUEO"));
										spDetail2.setNSX_FK(rsSpDetail.getString("nsx_fk"));
										spDetail2.setMaNSX(rsSpDetail.getString("nhasanxuat"));
										spDetail.add(spDetail2);
										 
									}
									rsSpDetail.close();
								} 
								catch (Exception e) 
								{
									e.printStackTrace();
								}
								
							}
							
							vt.setSpDetailList(spDetail);
							
							dmvtList.add(vt);
						
					}
					rs.close();
					
					this.dmvtList = dmvtList;
					 
				}
				 
			}catch(Exception er){
				er.printStackTrace();
			}
		 }
		 
	}
	public void createLoaisanphamRs()
	{
		String query=" select pk_seq,ma, ten from erp_loaisanpham where isnull( is_sanxuat ,'0') ='1' ";
		////System.out.println("lay loai san pham: \n" + query + "\n=======================================");
		this.loaisanphamRs= db.get(query);
	}
	private void initLsx_TieuHao() 
	{
		
		this.createLoaisanphamRs();
		this.createKhoanMucChiPhiRs();
		  String query= " select  thnl.congdoan_fk,(select isnull(LSX.SOLenhsanxuat,'') from ERP_LENHSANXUAT_GIAY LSX where lsx.PK_SEQ=THNL.LENHSANXUAT_FK) as solenhsanxuat,"
		  				+ " THNL.nhomchiphi_fk, THNL.LOAISANPHAM,THNL.ngaytieuhao , THNL.lenhsanxuat_fk, THNL.trangthai,  ISNULL(lenhsanxuatdukien_fk, '-1') as PO,  ISNULL(kichbansanxuat_fk,0) AS kichbansanxuat_fk , "+
						" ngaybatdau, ngaydukienHT, ISNULL(lsxcd.soluong,0) AS SOLUONG ,ISNULL( cd.nhamay_fk,0) AS nhamay_fk "
						+ " , ISNULL(cd.diengiai,'') AS diengiai , "
						+ " ( SELECT  SUM(NKSP.SOLUONGNHAN) AS SOLUONG FROM    ERP_NHANHANG_SANPHAM NKSP  "
						+ "WHERE     NHANHANG_FK    IN  (  	SELECT B.NHAPKHO_FK  	FROM  ERP_LSXTIEUHAO_NHAPKHO B  	WHERE B.TIEUHAO_FK=THNL.PK_SEQ   ) ) AS SOLUONGSX  "+
						" from  ERP_TIEUHAONGUYENLIEU THNL inner join  " +
						"  ERP_LENHSANXUAT_Giay   a  ON a.PK_SEQ=THNL.LENHSANXUAT_FK"+
						"  left join erp_lenhsanxuat_congdoan_giay lsxcd on lsxcd.lenhsanxuat_fk=a.pk_seq  and lsxcd.CONGDOAN_FK= thnl.CONGDOAN_FK "+
						" left join erp_congdoansanxuat_giay cd on cd.pk_seq=lsxcd.congdoan_fk "+
						" where THNL.PK_SEQ="+this.IdTieuHao;
		  ////System.out.println("query th:"+query);
			ResultSet rs = db.get(query);
			System.out.println("QUEY INIII: " + query);
			if(rs != null)
			{
				try 
				{
					if(rs.next())
					{
						if(!rs.getString("PO").equals("-1"))
						{
							this.PO = "Pln" + rs.getString("PO");
						}
						this.kbsxId = rs.getString("kichbansanxuat_fk");
						this.id=rs.getString("lenhsanxuat_fk");
						this.nhamayId = rs.getString("nhamay_fk");
						this.ngaytao = rs.getString("ngaybatdau");
						this.ngaydukien = rs.getString("ngaydukienHT");
						this.soluongsx = rs.getString("SOLUONGSX");
						this.trangthai = rs.getString("trangthai");
						this.loaisanphamTH=rs.getString("LOAISANPHAM");
						this.tencongdoanCurrent=rs.getString("diengiai");
						this.ngaytieuhao=rs.getString("ngaytieuhao");
						this.SoLSX=rs.getString("solenhsanxuat");
						this.khoanmucchiphi = rs.getString("nhomchiphi_fk");
						this.lsxIds= rs.getString("lenhsanxuat_fk");
						this.CdsxId=rs.getString("congdoan_fk");
						
					
					}
				rs.close();
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
				}
				query="  "+
					" SELECT B.NHAPKHO_FK   "+
					" FROM  ERP_LSXTIEUHAO_NHAPKHO B "+  
					" WHERE B.TIEUHAO_FK="+this.IdTieuHao+" "+   
					"  ";
				ResultSet  rsnk=db.get(query);
				String str="";
				try{
				while (rsnk.next()){
					
					str+=(str.equals("")?"":"," ) +rsnk.getString("NHAPKHO_FK");
					
				}
				}catch (Exception er){}
			this.IdNhapkho=str;
			
			
			System.out.println("cau lenh lay tieu hao huy "+ query);
			
			if(this.trangthai.equals("0")){
				this.initTieuHao();
			}else{
				this.init_sp_tieuhao();
			}
			
	
		
	}

	private void init_sp_tieuhao() {
		// TODO Auto-generated method stub
		String query=	" SELECT B.PK_SEQ AS SPID, B.MA AS VTMA, B.TEN AS VTTEN, D.DONVI AS   VTDONVI,  ISNULL(LSXBOM.SOLUONG,0)  AS SOLUONG, "+
				" A.SOLUONG AS TIEUHAO  FROM ERP_LENHSANXUAT_TIEUHAO  A    "+
				" INNER JOIN ERP_TIEUHAONGUYENLIEU THNL ON A.TIEUHAONGUYENLIEU_FK=THNL.PK_SEQ   "+
				" LEFT JOIN ERP_LENHSANXUAT_BOM_GIAY LSXBOM ON ISNULL(LSXBOM.CONGDOAN_FK,0)= ISNULL(THNL.CONGDOAN_FK ,0)   "+
				" AND LSXBOM.LENHSANXUAT_FK=THNL.LENHSANXUAT_FK AND ISNULL(LSXBOM.VATTUTT_FK ,LSXBOM.VATTU_FK)=A.SANPHAM_FK "+   
				" INNER JOIN ERP_SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ    "+
				" LEFT JOIN DONVIDOLUONG D ON B.DVDL_FK = D.PK_SEQ   WHERE "+ 
				" A.TIEUHAONGUYENLIEU_FK="+this.IdTieuHao;
 
		System.out.println("cau lenh lay vat tu tieu hao lsx:\n" + query + "\n=================================================s");
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			List<IDanhmucvattu_SP> dmvtList = new ArrayList<IDanhmucvattu_SP>();
			
			try 
			{
				IDanhmucvattu_SP vt = null;
				while(rs.next())
				{
					vt = new Danhmucvattu_SP();
					
					vt.setMaVatTu(rs.getString("vtMa"));
					vt.setTenVatTu(rs.getString("vtTen"));
					vt.setDvtVT(rs.getString("vtDonvi"));
					vt.setSoLuong(rs.getString("SOLUONG"));
					vt.setSoLuongTHThucTe(formatter.format(rs.getDouble("tieuhao")));
 
					//Them chi tiet da tieu hao
					query = " select a.SOLO, a.SOLUONG,isnull(dv.donvi ,'') as donvi ,a.MATHUNG,a.MAME,a.MAPHIEU, "
							+ " a.MAPHIEUDINHTINH,a.MARQ,a.PHIEUEO,a.NGAYNHAPKHO,a.NGAYHETHAN,a.HAMAM,a.HAMLUONG, isnull(bin.TEN,'')  as vitri"+
							"	from ERP_LENHSANXUAT_TIEUHAO_CHITIET a    "
							+ " left join ERP_BIN bin on bin.PK_SEQ=a.BIN_FK "+
							"	inner join erp_sanpham sp on sp.pk_seq=a.sanpham_fk "+
							"	left join donvidoluong dv on dv.pk_seq=sp.dvdl_fk "+
							"   where  a.soluong >0   and a.tieuhaonguyenlieu_fk = '" + this.IdTieuHao + "' AND A.SANPHAM_FK="+rs.getString("spId");
					 
					ResultSet rsSpDetail = db.get(query);
					//System.out.println("query chi tiet :"+query);
					List<ISpDetail> spConList = new ArrayList<ISpDetail>();
					ISpDetail spCon = null;
					if(rsSpDetail != null)
					{
						while(rsSpDetail.next())
						{
							String idhangmua = rs.getString("spId");
							String solo = rsSpDetail.getString("solo");
							String slg = rsSpDetail.getString("soluong");
							String donvi= rsSpDetail.getString("donvi");
							spCon = new SpDetail(idhangmua, solo, slg,donvi);
							spCon.setMathung(rsSpDetail.getString("MATHUNG"));
							spCon.setMame(rsSpDetail.getString("MAME"));
							spCon.setMaphieu(rsSpDetail.getString("MAPHIEU"));
							spCon.setMAPHIEUDINHTINH(rsSpDetail.getString("MAPHIEUDINHTINH"));
							spCon.setMarq(rsSpDetail.getString("MARQ"));
							spCon.setPHIEUEO(rsSpDetail.getString("PHIEUEO"));
							spCon.setNgaynhapkho(rsSpDetail.getString("NGAYNHAPKHO"));
							spCon.setNgayhethan(rsSpDetail.getString("NGAYHETHAN"));
							spCon.setHamam(rsSpDetail.getString("hamam"));
							spCon.setHamluong(rsSpDetail.getString("hamluong"));
							spCon.setVitri(rsSpDetail.getString("vitri"));
							 
							spConList.add(spCon);
						}
						rsSpDetail.close();
					}
					
					vt.setSpDetailList(spConList);	
					
					dmvtList.add(vt);
					
				}
				rs.close();
				
				this.dmvtList = dmvtList;
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}


	public void initDisplay() 
	{
		
	}

	public String getViewBom() 
	{
		return this.viewBom;
	}

	public void setViewBom(String viewBom) 
	{
		this.viewBom = viewBom;
	}

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	
	public String getSpma() {
		
		return this.spMa;
	}

	
	public void setSpma(String spma) {
		
		this.spMa=spma;
	}

	
	public void SetListCongDoan(List<ILenhSXCongDoan> listlenhsxcd) {
		
		this.ListCongdoan=listlenhsxcd;
	}

	
	public List<ILenhSXCongDoan> getListCongDoan() {
		
		return this.ListCongdoan;
	}

	
	public void SetListSanPhamSx(List<ISanPhamSanXuat> listspsx) {
		
		this.listSpSx=listspsx;
	}

	
	public List<ISanPhamSanXuat> getListSanPhamSx() {
		
		return this.listSpSx;
	}

	
	public void sethtpsp(Hashtable<String, String> htb) {
	}

	
	public void setListDanhMucThieu(List<IDanhmucvattu_SP> list) {
		
		this.dmvtThieuList=list;
	}

	
	public List<IDanhmucvattu_SP> getListDanhMucThieu() {
		
		return this.dmvtThieuList;
	}

	
	public void ChangeSpOrKichBan() {
		
		 if(!this.Check_lsx_dayc_nl()){
			 // 
			 this.msg="Bạn không thể thay đổi kịch bản hay số lượng sản xuất, vì lệnh sản xuất đã được yêu cầu nguyên liệu.Vui lòng hủy yêu cầu nguyên liệu hết để làm tiếp nghiệp vụ";
			 // khởi tạo lại
			 this.init();
			 return;
			 
		 }
		
		ListCongdoan.clear();
		listSpSx.clear();
	 
		
		String query="";
		 
		//kiem tra trạng thái <2 thì mới load lại
		if(  this.kbsxId.length() >0){
			
			// lấy số lượng chuẩn bên kịch bản ra nếu số lượng trên giao diện không được nhập 
			
		/*	query=" select b.pk_seq,b.diengiai from Erp_KichBanSanXuat_CongDoanSanXuat_Giay a inner join Erp_CongDoanSanXuat_Giay b  "+
				"\n on a.congdoansanxuat_fk=b.pk_Seq  where a.kichbansanxuat_fk="+this.kbsxId; 
			
			this.RsCongDoan=db.getScrol(query);
			
			*/
			double soluongsx_=0;
			try{
				soluongsx_=Double.parseDouble(this.soluongsx.replaceAll(",",""));
			}catch(Exception er){}
			//nếu số lượng sx =0 thì lấy mạc định từ kịch bản
			if(soluongsx_==0){
				soluongsx_=this.getsoluongchuan_tukichban(this.kbsxId );
				this.soluongsx=soluongsx_+"";
			}
			
			query = "\n select dmvt.diengiai as diengiaibom , ISNULL( kb.SOLUONGCHUAN,0)  AS  SOLUONGCHUAN ,  isnull(SOLUONGDM,0) as SOLUONGDM ,sp.dvkd_fk , nm.pk_seq as nhamayid, a.thutu, a.danhmucvattu_fk ,0 as spid , "+ 
					"\n A.DINHLUONG_FK,isnull(B.DINHTINH,'0') as dinhtinh , " +
					"\n case when sp1.kiemtradinhluong ='1' and  a.danhmucvattu_fk IS   null then 1  when sp1.kiemtradinhtinh =1 and  a.danhmucvattu_fk IS   null then 1 else isnull(a.kiemdinhchatluong,'0')end  as kiemdinhchatluong  " +
					"\n ,b.pk_seq,b.diengiai,a.kichbansanxuat_fk,  "+
					"\n a.thutu,kb.nhamay_fk,nm.manhamay,nm.tennhamay ,isnull( a.sanpham_fk,0) as sanpham_fk ,isnull(sp.ma,'') as masp,isnull(sp.ten,'') as tensp "+ 
					"\n from Erp_KichBanSanXuat_CongDoanSanXuat_Giay a  "+
					"\n inner join erp_kichbansanxuat_giay kb on kb.pk_seq= a.kichbansanxuat_fk "+ 
					"\n inner join Erp_CongDoanSanXuat_Giay b on a.congdoansanxuat_fk=b.pk_Seq "+ 
					"\n inner join erp_nhamay nm on nm.pk_seq=kb.nhamay_fk  " +
					"\n left join  erp_danhmucvattu dmvt on dmvt.pk_seq=a.danhmucvattu_fk		 "+
					"\n LEFT join  erp_sanpham sp on sp.pk_seq=dmvt.sanpham_fk "+
					"\n left join erp_sanpham sp1 on sp1.pk_seq=kb.sanpham_fk "+
					"\n where kichbansanxuat_fk='"+this.kbsxId+"'   "+
					"\n order by a.thutu ";
			
			////System.out.println("ChangeSpOrKichBan nek: "+sql);
			ResultSet rs=db.get(query);
			try{
				while (rs.next()){
					ILenhSXCongDoan lsxcd=new LenhSXCongDoan();
					if(rs.getString("dvkd_fk")!=null){
						this.Dvkdid=rs.getString("dvkd_fk");
						////System.out.println(rs.getString("dvkd_fk"));
					}
					lsxcd.setCongDoanId(rs.getString("pk_seq"))	;
					lsxcd.setDiengiai(rs.getString("diengiai"));
					lsxcd.Setkichbansanxuat(rs.getString("kichbansanxuat_fk"));
					lsxcd.setTrangthai("0");
					lsxcd.setActive("1");
					
					lsxcd.setNhaMayId(rs.getString("nhamay_fk"));
					lsxcd.setBomId(rs.getString("danhmucvattu_fk"));
					lsxcd.setBomTen(rs.getString("diengiaibom"));
					lsxcd.setThuTu(rs.getFloat("thutu"));
					lsxcd.setPhanXuong(rs.getString("manhamay"));
					lsxcd.setSanPham(rs.getString("masp") +"-"+rs.getString("tensp"));
					lsxcd.setMaSp(rs.getString("masp"));
					
					lsxcd.setSpId(rs.getString("sanpham_fk"));
				 
					lsxcd.SetKiemDinhCL(rs.getString("kiemdinhchatluong"));
					double SOLUONGDM=0;
					if(rs.getDouble("SOLUONGCHUAN") >0){
						SOLUONGDM=  rs.getDouble("SOLUONGDM")* soluongsx_/rs.getDouble("SOLUONGCHUAN") ;
					}
					  
					
					lsxcd.setSoLuong(""+SOLUONGDM);
					
					
					lsxcd.initCongDoanNhapXuat();
					lsxcd.initArrayNhapXuat();
					ListCongdoan.add(lsxcd);
					
				}
				rs.close();
	 
			}catch(Exception er){
				er.printStackTrace();
				 
			}
			
			
		}
		
	}

	
	private double getsoluongchuan_tukichban(String kbsxId2) {
		// TODO Auto-generated method stub
		try{
			String query="select SoLuongChuan  from Erp_KichBanSanXuat_Giay where PK_SEQ="+kbsxId2 ;
			ResultSet rs=db.get(query);
			double soluong=0;
			if(rs.next()){
				soluong=rs.getDouble("soluongchuan");
			}
			rs.close();
			return soluong;
			
		}catch(Exception er){
			er.printStackTrace();
		}
		return 0;
	}

	public ResultSet getRsBomIdsp(String masp,String trongluong) {
		try{
		String sql=" select isnull(sp.trongluong,0) as trongluong,  ma,isnull(chuannen,'0') as chuannen ,dvkd_fk ,isnull(chungloai_fk,0) as chungloai_fk " +
				"  from erp_sanpham sp where pk_seq='"+masp+"' and sp.congty_fk="+this.congtyId;
		ResultSet rs=db.get(sql);
		rs.next();
		
		 sql=" select pk_seq,diengiai from ERP_DANHMUCVATTU   where trangthai=1 and mavattu='"+rs.getString("ma")+"' and congty_fk="+this.congtyId;
		
		if(rs.getString("dvkd_fk").equals("100004")){
			if(rs.getString("chungloai_fk").equals("100040")){
				//neu la ong cone thi co them trong luong
				 sql=" select pk_seq,diengiai from ERP_DANHMUCVATTU  " +
				 		"  where trangthai=1 and trongluong="+rs.getString("trongluong")+"  and mavattu='"+rs.getString("ma")+"' and congty_fk="+this.congtyId;
			}else if(rs.getString("chungloai_fk").equals("100031")){
				//core
				sql=" select pk_seq,diengiai from ERP_DANHMUCVATTU  a  where trangthai=1  " +
						" and congty_fk="+this.congtyId +" and a.loaichungloai=1 and CHUANNEN=N'"+rs.getString("chuannen")+"'" ;
			}else{
				// tat ca nhung cai con lai
				sql=" select pk_seq,diengiai from ERP_DANHMUCVATTU  a  where trangthai=1  " +
				" and congty_fk="+this.congtyId +" and a.loaichungloai=2  and CHUANNEN=N'"+rs.getString("chuannen")+"'" ;
			}
		}
		
		//////System.out.println("Tao Bom Cho CONE : "+sql);
		rs.close();
		 
		 return db.get(sql);
		 
		}catch(Exception er){
			er.printStackTrace();
			return null;
		}
	}

	
	public ResultSet getRsSpThayThe(IDanhmucvattu_SP spvt) {
		
		// sản phẩm thiếu.
		String soluong=this.getSoLuongTuCongDoan(spvt.getCongDoanId());
		
		String sql=" select sp.pk_seq,sp.ma,sp.ten,"+soluong+ "/dmvt.soluongchuan * a.soluong as soluong,KHO.AVAILABLE "+ 
		" ,a.DANHMUCVT_FK ,dvdl1.donvi  from ERP_DANHMUCVATTU_VATTU_THAYTHE a inner join  "+
		" erp_sanpham sp on sp.pk_seq=a.VatTuTT_FK  "+
		" LEFT JOIN ERP_KHOTT_SANPHAM KHO ON KHO.KHOTT_FK="+spvt.getkhoid()+" AND KHO.SANPHAM_FK=sp.pk_seq "+
		" inner join ERP_DANHMUCVATTU dmvt on dmvt.pk_seq=a.danhmucvt_fk " +
		" INNER JOIN DONVIDOLUONG DVDL1 ON SP.DVDL_FK = DVDL1.PK_SEQ   "+
		" where a.DANHMUCVT_FK="+spvt.getBomId()+ " and a.VatTu_FK="+spvt.getIdVT() +" and "+soluong+ "/dmvt.soluongchuan * a.soluong < KHO.AVAILABLE ";
		//////System.out.println("Get San pham thieu :"+sql);
		return db.get(sql);
	}

	
	public String getCongDoanCurrent() {
		
		return this.congdoanCurrent;
	}

	
	public void setCongDoanCurrent(String congdoanid) {
		
		this.congdoanCurrent=congdoanid;
	}

	
	public String getTenCongDoanCurrent() {
		
		return this.tencongdoanCurrent;
	}

	
	public void setTenCongDoanCurrent(String congdoanten) {
		
		this.tencongdoanCurrent=congdoanten;
		
	}

	
	public String CheckBomExpire() {
		
		String chuoi="";
		if( this.bomId.length() > 0    )
		{
			String sql=  	" select  BOM.SOLUONGCHUAN , BOM.DIENGIAI, DVDL.DIENGIAI as DVDL "+
					" from ERP_DANHMUCVATTU BOM "+
					" inner join DONVIDOLUONG DVDL on BOM.DVDL_FK = DVDL.PK_SEQ "+
					" where BOM.PK_SEQ = '" + this.bomId + "' and BOM.CONGTY_FK="+this.congtyId;
			ResultSet rsDvt=db.get(sql); 
				
			if(rsDvt!=null){
				try{
					while (rsDvt.next()){
							this.dvtBOM = rsDvt.getString("DVDL");
							
							double soluong=0;
							try{soluong=Double.parseDouble(this.soluongsx.replace(",", "")); }catch(Exception er){}
							if(soluong==0){
								this.soluongsx=rsDvt.getString("SOLUONGCHUAN");
							}
					}
					rsDvt.close();
				}catch(Exception er){
					er.printStackTrace();
				}
			}
		}
		
		
		//////System.out.println("Come Here " + this.spMa);
		if(this.kbsxId.length() > 0 && this.spMa.length() > 0) {
			String sql="select b.diengiai as congdoan,dm.hieuluctu,dm.hieulucden ,dm.diengiai from Erp_KichBanSanXuat_CongDoanSanXuat_Giay a "+
			" inner join Erp_CongDoanSanXuat_Giay b   "+
			" on a.congdoansanxuat_fk=b.pk_Seq  "+
			" inner join  erp_kichbansanxuat_giay kb  on kb.pk_seq= kichbansanxuat_fk "+ 
			" inner join erp_danhmucvattu dm on dm.pk_seq=b.danhmucvattu_fk "+
			" where kichbansanxuat_fk="+this.kbsxId+" and kb.MaSanPham='"+this.spMa+"' "+
			" and ( dm.hieuluctu >='"+this.ngaytao+"' or dm.hieulucden <='"+this.ngaytao+"') ";
			////System.out.println("Kiem Tra Bom Expire : "+sql);
			ResultSet rs=db.get(sql);
			try{
				while(rs.next()){
					
					chuoi=chuoi + "\n Công đoạn: ["+rs.getString("congdoan")+"]  ,  Bom ["+rs.getString("diengiai")+"] ,  Ngày hiệu lực :"+rs.getString("hieuluctu")+" , Ngày hết hiệu lực :  " +rs.getString("hieulucden");
					
				}
				rs.close();
			}catch(Exception er){
				//////System.out.println("Error  : "+er.toString());
				return er.toString();
				
			}
		}
		return chuoi;
	}

	
	public boolean HoantatCongDoan(String ischeckdungsai) {
		
		try{
			
			//////System.out.println("ischeckdungsai : " +ischeckdungsai);
			
			if(ischeckdungsai.equals("1")){
				if(!this.check_VuotDungSai()){
					return false;
					
				}
			}
			boolean iscongdoancuoi=false;
			String kiemdinhchatluong="";
			String sanpham="";
			String sql="select kiemdinhchatluong,isnull(sanpham_fk,0) as idsp from erp_lenhsanxuat_congdoan_giay where lenhsanxuat_fk="+this.id+" and congdoan_fk="+this.congdoanCurrent;
			ResultSet rs=db.get(sql);
			if (rs.next()){
				kiemdinhchatluong=rs.getString("kiemdinhchatluong");
				sanpham=rs.getString("idsp");
			}
			rs.close();
			
			sql="select * from erp_lenhsanxuat_congdoan_giay where congdoan_fk="+this.congdoanCurrent+" and lenhsanxuat_fk= "+this.id +
					" and thutu=(select max(thutu) from  erp_lenhsanxuat_congdoan_giay  where lenhsanxuat_fk="+this.id+" )"; 
			rs=db.get(sql);
			if (rs.next()){
				iscongdoancuoi=true;
			}
			rs.close();
			
			//neu công đoạn có sản phẩm thì phải kiểm tra đã nhập hàng và tiêu hao nguyên liệu chưa?
			if(!sanpham.equals("0") || iscongdoancuoi){
				sql=" select trangthai from erp_nhapkho  where   solenhsanxuat="+this.id+" and congdoan_fk="+this.congdoanCurrent;
				 rs=db.get(sql);
				int i=0;
				while (rs.next()){
					if(rs.getString("trangthai").equals("0")){
						this.msg="Vui Lòng Kiểm Tra Lại,Công Đoạn Này Chưa Chốt Phiếu Nhập Hàng.";
						return false;
					}
					i++;
				}
				rs.close();
				if(i==0){
					this.msg="Vui Lòng Kiểm Tra Lại,Công Đoạn Này Chưa Có Phiếu Nhập Hàng.";
					return false;
				}
				
				///BEGIN Check Xem đã tiêu hao nguyên liệu chưa?
			/*	 sql = "select COUNT(*) as sodong from ERP_TIEUHAONGUYENLIEU where  trangthai='1'  and  lenhsanxuat_fk = '" + this.id + "' and congdoan_fk="+this.congdoanCurrent;
				//////System.out.println("Lay "+sql);
				ResultSet rsCheck = db.get(sql);
				
				int sodong = 0;
				if(rsCheck != null)
				{
					try 
					{
						if(rsCheck.next())
						{
							sodong = rsCheck.getInt("sodong");
						}
						rsCheck.close();
					} 
					catch (Exception e) 
					{ 
						sodong = 0; 
						this.msg = "114.Exception: " + e.getMessage();
						return false;
					}
				}
				if(sodong == 0){
					this.msg="Vui Lòng Kiểm Tra Lại,Công Đoạn Này Chưa Tiêu Hao Nguyên Liệu.";
					return false;
				}*/
				
				///END
				
				
				
			}
			
			//neu công đoạn có kiểm tra nguyên liệu thì phải  kiểm tra nguyên liệu chuaa?
			if(kiemdinhchatluong.equals("1")){
				   sql=" select trangthai from erp_yeucaukiemdinh  where  lenhsanxuat_fk="+this.id+" and congdoan_fk ="+this.congdoanCurrent;
				   rs=db.get(sql);
					int i=0;
					while (rs.next()){
						if(rs.getString("trangthai").equals("0")){
							this.msg="Vui Lòng Kiểm Tra Lại,Công Đoạn Này Chưa Chốt Phiếu Kiểm Định.";
							return false;
						}
						i++;
					}
					rs.close();
					if(i==0){
						this.msg="Vui Lòng Kiểm Tra Lại,Công Đoạn Này Chưa Có Kiểm Định Chất Lượng.";
						return false;
					}
			}
			sql="update erp_lenhsanxuat_congdoan_giay set tinhtrang=1 where lenhsanxuat_fk='"+this.id+"' and congdoan_fk='"+this.congdoanCurrent+"'";
			
			if(!db.update(sql)){
				
				this.msg="Không thể cập nhật dữ liệu,Vui lòng thử lại";
				return false;
				
			}
			
			
			
		}catch(Exception er){
			this.msg=er.toString();
			return false;
		}
		return true;
	}

	private boolean check_VuotDungSai() {
		
		try{
			String sql=  " select dmvt.sanpham_fk, a.sanpham_fk, a.soluong+ (a.soluong* isnull(dmvt.dungsai,0)/100) as nguongtren,  " + 
						 " a.soluong- (a.soluong* isnull(dmvt.dungsai,0)/100) as nguongduoi,nk.soluongnhap from erp_lenhsanxuat_congdoan_giay a " + 
						 " inner join erp_danhmucvattu dmvt on dmvt.pk_seq=a.danhmucvattu_fk  " + 
						 " inner join  " + 
						 " ( " + 
						 " 	select sum(soluongnhap) as soluongnhap,sanpham_fk  " + 
						 " 	from erp_nhapkho  nk inner join  " + 
						 " 	erp_nhapkho_sanpham nksp on nk.pk_seq=nksp.sonhapkho_fk " + 
						 " 	where solenhsanxuat="+this.id+" and congdoan_fk="+this.congdoanCurrent+" and trangthai=1 " + 
						 " 	group by sanpham_fk " + 
						 "  " + 
						 " ) nk on nk.sanpham_fk=a.sanpham_fk " + 
						 " where lenhsanxuat_fk="+this.id+" and congdoan_fk="+this.congdoanCurrent;
			
			//////System.out.println("get Lénh dung sai :"+sql);
			ResultSet rs=db.get(sql);
			
			if(rs.next()){
				if(  rs.getDouble("soluongnhap") >rs.getDouble("nguongtren")){
					
					sql=" INSERT INTO THONGBAOVUOTDUNGSAI (LENHSANXUAT_FK,CONDOAN_FK,NGUOITAO,SOLUONGNHAP,SOLUONGCHOPHEP)"+
					" VALUES('"+this.id+"','"+this.congdoanCurrent+"',"+this.userId+","+ rs.getDouble("soluongnhap")+","+rs.getDouble("nguongtren")+")";
					db.update(sql);
					
					this.msg="Tổng số lượng nhập đã vượt quá dung sai cho phép.Tổng số lượng nhập công đoạn này là : "+rs.getDouble("soluongnhap")+".Dung sai cho phép vượt là : " +rs.getDouble("nguongtren");
					this.VuotDungSai=true;
					return false;
				}
				
				if(  rs.getDouble("soluongnhap") <rs.getDouble("nguongduoi")){
					
					sql=" INSERT INTO THONGBAOVUOTDUNGSAI (LENHSANXUAT_FK,CONDOAN_FK,NGUOITAO,SOLUONGNHAP,SOLUONGCHOPHEP)"+
					" VALUES('"+this.id+"','"+this.congdoanCurrent+"',"+this.userId+","+ rs.getDouble("soluongnhap")+","+rs.getDouble("nguongtren")+")";
					db.update(sql);
					
					
					this.msg="Tổng số lượng nhập nhỏ hơn dung sai cho phép.Tổng số lượng nhập công đoạn này là : "+rs.getDouble("soluongnhap")+".Dung sai cho phép là : " +rs.getDouble("nguongduoi");
					this.VuotDungSai=true;
					return false;
				}			
			}
		}catch(Exception er){
			this.msg="Lỗi khi kiểm tra dung sai hàng nhập : "+er.toString();
		}
		return true;
	}

	
	public void SetTrangthaixemtieuhao(String tt) {
		
		this.Trangthaixemtieuhao=tt;
	}

	
	public String GetTrangthaixemtieuhao() {
		
		return this.Trangthaixemtieuhao;
	}

	
	public void setListVattuThem(List<IDanhmucvattu_SP> list) {
		
		this.ListVattuThem=list;
	}

	
	public List<IDanhmucvattu_SP> getListVattuThem() {
		
		return this.ListVattuThem;
	}

	
	public void SetRsCongDoan(ResultSet rscd) {
		
		this.RsCongDoan=rscd;
	}

	
	public ResultSet getRsCongDoan() {
		
		return this.RsCongDoan;
	}

	
	public void KhoiTaoListCongDoan() {
		try{
		//Cap Nhat Trang Thai Cua Cong Doan.
				ListCongdoan.clear();
				String sql="  select  distinct  isnull(a.KIEMDINHCHATLUONG ,'0')  as  KIEMDINHCHATLUONG, a.tinhtrang,a.soluong as soluongcd, "+ 
				"  nm.pk_seq as nhamayid, a.thutu,  a.danhmucvattu_fk ,isnull(kb.sanpham_fk,0) as spid , b.kiemdinhchatluong, "+ 
				" b.pk_seq,b.diengiai,a.kichban_fk kichbansanxuat_fk,  a.thutu,kb.nhamay_fk,nm.manhamay,nm.tennhamay , "+
				" isnull(a.sanpham_fk,0) as sanpham_fk  ,isnull(sp.ma,'') as masp,isnull(sp.ten,'') as tensp  from ERP_LENHSANXUAT_CONGDOAN_GIAY a "+
				" inner join Erp_CongDoanSanXuat_Giay b  on a.congdoan_fk=b.pk_Seq "+   
				" inner join erp_nhamay nm on nm.pk_seq=b.nhamay_fk "+ 
				" LEFT join erp_sanpham sp on SP.ma=a.masanpham  and sp.congty_fk= b.congty_fk"+
				" inner join  erp_kichbansanxuat_giay kb on kb.pk_seq= a.kichban_fk "+ 
				" where a.lenhsanxuat_fk= "+this.id+" order by a.thutu  ";
				
				ResultSet	rs=db.get(sql);
				while (rs.next()){
					ILenhSXCongDoan lsxcd=new LenhSXCongDoan();
					lsxcd.setCongDoanId(rs.getString("pk_seq"))	;
					lsxcd.setDiengiai(rs.getString("diengiai"));
					lsxcd.Setkichbansanxuat(rs.getString("kichbansanxuat_fk"));
					lsxcd.setTrangthai(rs.getString("tinhtrang"));
					
					if(rs.getString("tinhtrang").equals("2")){
						lsxcd.setActive("0");
					}else{
						lsxcd.setActive("1");
					}
					lsxcd.setNhaMayId(rs.getString("nhamay_fk"));
					lsxcd.setBomId(rs.getString("danhmucvattu_fk"));
					lsxcd.setThuTu(rs.getFloat("thutu"));
					lsxcd.setPhanXuong(rs.getString("manhamay"));
					lsxcd.setSanPham(rs.getString("masp") +"-"+rs.getString("tensp"));
					lsxcd.setMaSp(rs.getString("masp"));
					lsxcd.setSpId(rs.getString("sanpham_fk"));
					this.spId=rs.getString("spid");
					this.nhamayId=rs.getString("nhamayid");
				
					lsxcd.SetKiemDinhCL(rs.getString("kiemdinhchatluong"));
					lsxcd.setSoLuong(rs.getString("soluongcd"));
					sql=" select nk.pk_seq as chungtu,nk.ngaynhapkho ,sp.solo, SUM(SOLUONGNHAP) as soluong "+
						"  from ERP_NHAPKHO nk inner join ERP_NHAPKHO_SANPHAM SP  ON SONHAPKHO_FK= "+
						"  NK.PK_SEQ WHERE SOLENHSANXUAT="+this.id+" AND CONGDOAN_FK= "+lsxcd.getCongDoanId()+
						 " AND NK.TRANGTHAI!=2 "+
						 " GROUP BY  nk.pk_seq,nk.ngaynhapkho ,sp.solo";
					lsxcd.SetRsHangDaNhan(db.get(sql));				
					
					sql="select pk_seq as chungtu,nhapkho_fk,solo,soluong,CASE WHEN DATCHATLUONG=1 THEN N'ĐẠT' WHEN DATCHATLUONG IS NULL THEN N'CHƯA DUYỆT'  ELSE N'KHÔNG ĐẠT' END as TINHTRANG "+
						" from ERP_YeuCauKiemDinh where trangthai=1 and  lenhsanxuat_fk="+this.id+" and congdoan_fk="+lsxcd.getCongDoanId();
					
					lsxcd.SetRsHangDaKD(db.get(sql));
				
					sql=" select THNL.PK_SEQ as chungtu,sp.ma,sp.ten   as ten,b.soluong "+ 
						" from ERP_TIEUHAONGUYENLIEU THNL  "+
						" INNER JOIN  erp_lenhsanxuat_tieuhao b ON THNL.PK_SEQ=B.TIEUHAONGUYENLIEU_FK "+
						" inner join erp_sanpham sp on sp.pk_seq= b.sanpham_fk   "+
						" where THNL.trangthai='1' and THNL.congdoan_fk="+lsxcd.getCongDoanId()+" and THNL.lenhsanxuat_fk="+this.id;
					
					lsxcd.SetRsDaTieuHao(db.get(sql));
					ListCongdoan.add(lsxcd);
				
				
				}
				rs.close();
			/**********END*********/
		
		}catch(Exception er){
			//////System.out.println("KhoiTaoListCongDoan :2818 : " + er.toString());
		}
	}

	
	public void setTieuHaoId(String id) {
		
		this.IdTieuHao=id;
	}

	
	public String getTieuHaoId() {
		
		return this.IdTieuHao;
	}

	
	public void setListBanThanhPham(List<IDanhmucvattu_SP> list) {
		
		this.ListBanThanhPham=list;
	}

	
	public List<IDanhmucvattu_SP> getListBanThanhPham() {
		
		return this.ListBanThanhPham;
	}

	
	public boolean HuyBookedNL() {
		
		try{
			
			db.getConnection().setAutoCommit(false);
			String query ="";
			
	 
			query=	"  select b.khosanxuat_fk as khott_fk  ,isnull(a.vattutt_fk,a.vattu_fk) as sanpham_fk, "+  
			"  sum( case when a.vattutt_fk is null then a.soluong else a.soluongtt end ) as soluong "+
			"  from	  ERP_LENHSANXUAT_BOM_GIAY a  inner join erp_lenhsanxuat_giay b on a.lenhsanxuat_fk=b.pk_seq  "+
			"  where checkkho='1' and lenhsanxuat_fk="+ this.id +" group by b.khosanxuat_fk ,isnull(a.vattutt_fk,a.vattu_fk) ";
	
			////System.out.println("Cap Nhat Lai Kho:    "+query);
			ResultSet rs=db.get(query);
			if(rs!=null){
				while(rs.next()){	
					query="update erp_khott_sanpham set available=available+ '"+rs.getString("soluong")+"'  " +
							",booked=booked -'"+rs.getString("soluong")+"' where khott_fk='"+rs.getString("khott_fk") 
							+"' and sanpham_fk= '"+rs.getString("sanpham_fk")+"' " ;
					if(db.updateReturnInt(query)!=1)
					{
						this.msg = "Không thể cập nhật kho: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			 query = "update ERP_LENHSANXUAT_BOM_GIAY set checkkho='0'  where lenhsanxuat_fk = '" + this.id + "'";
			 
			
			if(!db.update(query))
			{
				msg = "2.Cập nhật trạng thái của sản phẩm sản xuất. Vui lòng thử lại";
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);	
			return true;
			
		}catch(Exception er){
			 
			this.msg="Lỗi Khi cập nhật kho :" +er.toString();
			db.update("rollback");
			return false;
		}
	}

	
	public boolean getVuotDungSai() {
		 
		return this.VuotDungSai;
	}

	
	public String getNgaytieuhao() {
		 
		return this.ngaytieuhao;
	}

	
	public void setNgaytieuhao(String _ngaytieuhao) {
		 
		this.ngaytieuhao=_ngaytieuhao;
	}

	
	public void setListVattuDeNghi(List<IDanhmucvattu_SP> list) {
		 
		this.ListVattuDeNghi=list;
		
	}

	
	public List<IDanhmucvattu_SP> getListVattuDeNghi() {
		
		return this.ListVattuDeNghi;
	}

	
	public String getDvkdId() {
		 
		return this.Dvkdid;
	}

	
	public void setDvkdId(String DvkdId) {
		 
		this.Dvkdid=DvkdId;
	}

	
	public ResultSet getRsNhapKho() {
		
		return this.rsnhapkho;
	}

	
	public void setRsNhapKho(ResultSet Rs) {
		
		this.rsnhapkho=Rs;
	}

	
	public String getNhapKhoId() {
		
		return this.IdNhapkho;
	}

	
	public void setNhapkhoId(String nhapkhoId) {
		
		this.IdNhapkho=nhapkhoId;
	}

	
	public void init_tieuhao_lsx() {
		
		this.initTieuHao();
	}

	
	public ResultSet getRsDvkd() {
		
		return RsDvkd;
	}

	
	public void setRsDvkd(ResultSet rs) {
		
		this.RsDvkd=rs;
	}

	
	public ResultSet getRsHangDaNhan_SPMoi() {
		
		return this.rsNhanHangSPMoi;
	}

	
	public ResultSet getRsDaTieuHao_SPMoi() {
		
		return this.rsTieuhaoSPMoi;
	}
	
	public ResultSet getRsHangDaKD_SpMoi() {
		
		return this.rsTieuhaoSPMoi;
	}

	
	public String getCoKiemDinhChatLuong() {
		
		return CoKiemDinhCL_SPmoi;
	}

	
	public void CreateRs_tieuhao() {
		
		
		 
	   String query = "select A.PK_SEQ, cast(A.PK_SEQ as varchar(10)) +' - ' +  "+    
			 "   cast ((select ten from erp_sanpham where pk_seq =a.sanpham_fk)  AS NVARCHAR(MAX)) +  "+    
			 "      ' - ' + A.diengiai as diengiai from  ERP_LENHSANXUAT_GIAY A where TRANGTHAI != 7 ";
			
			
			System.out.println(" lsxRS : "+ query);
			this.lsxRs = db.get(query);
			
		if(this.lsxIds!=null && this.lsxIds.length() >0){
			query="SELECT PK_SEQ,DIENGIAI FROM ERP_CONGDOANSANXUAT_GIAY CD WHERE CD.PK_SEQ IN (SELECT CONGDOAN_FK FROM ERP_LENHSANXUAT_CONGDOAN_GIAY where LENHSANXUAT_FK= "+this.lsxIds+") ";
			this.CdsxRs=db.get(query);
			}
				
				
				
		 
		if(loaisanphamTH==""||loaisanphamTH==null)
			loaisanphamTH="0";
		if(this.lsxIds!=null && this.CdsxId!=null && this.lsxIds.length() >0 && this.CdsxId.length() > 0 ) {
			query=  " SELECT NK.PK_SEQ,NK.NGAYNHAN AS NGAYNHAPKHO ,SUM(NKSP.SOLUONGNHAN) AS SOLUONG, dvdl.donvi  \n"+
					" FROM ERP_NHANHANG NK   \n"+
					" INNER JOIN ERP_NHANHANG_SANPHAM NKSP ON NKSP.NHANHANG_FK=NK.PK_SEQ   \n"+
					" INNER JOIN  ERP_SANPHAM SP ON NKSP.SANPHAM_FK=SP.PK_SEQ  \n"+
					" inner join donvidoluong dvdl on dvdl.pk_seq=NKSP.DONVI \n"+
					 " WHERE nk.trangthai ='1'  and SP.LOAISANPHAM_FK   IN ("+loaisanphamTH+" ) AND NK.PK_SEQ NOT IN  \n"+
					 " ( \n"+
					 " 	SELECT B.NHAPKHO_FK FROM ERP_LENHSANXUAT_GIAY A    \n"+
					 " 	INNER JOIN ERP_TIEUHAONGUYENLIEU TH ON TH.LENHSANXUAT_FK=A.PK_SEQ \n"+
					 " 	INNER JOIN  ERP_LSXTIEUHAO_NHAPKHO B ON TH.PK_SEQ=B.TIEUHAO_FK \n"+
					
					 " 	WHERE A.PK_SEQ="+this.lsxIds+"  AND TH.TRANGTHAI =1 \n"+
					 " ) AND LENHSANXUAT_FK ="+this.lsxIds + " and CONGDOAN_FK="+this.CdsxId+" \n" +
					 " GROUP BY NK.PK_SEQ,NK.NGAYNHAN ,dvdl.donvi \n";
			
			 this.rsnhapkho=db.get(query);
			 query=" select dvkd_fk from Erp_sanpham sp inner join erp_lenhsanxuat_congdoan_giay lsxsp on lsxsp.SANPHAM_FK=sp.PK_SEQ "+
					 " where lsxsp.congdoan_fk="+this.CdsxId+" and  lsxsp.LENHSANXUAT_FK="+this.lsxIds;
			ResultSet rs = db.get(query);
			try{
				rs.next();
				this.Dvkdid=rs.getString("dvkd_fk");
				rs.close();
			}catch(Exception er){
				er.printStackTrace();
			}
		
		}
		 System.out.println("LAY RA NHAP KHO \n" + query + "\n====================================");
		 
		 this.createKhoanMucChiPhiRs();
		 this.createLoaisanphamRs();
 
	}

	private void createKhoanMucChiPhiRs(){
		String query = " select ncp.PK_SEQ, ncp.TEN,ncp.DIENGIAI from    "
				+ "     ERP_NHOMCHIPHI  NCP " +
				"where CAST( NCP.TAIKHOAN_FK AS NVARCHAR(20))  like '621%' and ncp.trangthai = 1   and ncp.congty_fk="+this.congtyId;
		
		System.out.println("khoan muc chi phi " + query);
		this.khoanmucchiphiRs = db.get(query);
	}
	
	public ResultSet getRsYeuCauChuyenKho() {
		
		return rsYeuCauChuyenKho;
	}

	
	public boolean CheckYeuCauNL() {
		
		try{
 
			//lấy kho sản xuất 
			String query=   " select PK_SEQ,khonhannguyenlieu_fk from ERP_KHOTT where TrangThai = '1' and loai in ('1') " +
							" and congty_fk = '"+this.congtyId+"' and NHAMAY_FK= (select nhamay_fk from erp_lenhsanxuat_giay where pk_seq="+this.id+")";
			ResultSet rs=db.get(query);
			String khoxuat="";
			if(rs!=null){
				while (rs.next()){
						
						khoxuat=rs.getString("pk_seq");
				}
			}
			rs.close();
			
			 query ="select * from ERP_LENHSANXUAT_BOM_GIAY where  LENHSANXUAT_FK ="+this.id;
			  rs=db.get(query);
			
			if(!rs.next()){
				this.msg="Vui lòng chọn nguyên liệu cho lệnh sản xuất";
				return false;
			}
			rs.close();
			
			
		query=  " SELECT distinct B.KHOTT_FK  "+
				" FROM ERP_LENHSANXUAT_GIAY A  "+
				" INNER JOIN ERP_LENHSANXUAT_BOM_GIAY B ON A.PK_SEQ = B.LENHSANXUAT_FK "+   
				" WHERE  B.LOAI='1' and b.khott_fk not in ("+khoxuat+")  AND A.PK_SEQ = "+this.id;
		
		
		  rs=db.get(query);
		if(rs.next()){
			rs.close();
			
			return false;
		}else{
			query="update ERP_LENHSANXUAT_GIAY set trangthai=2 where pk_Seq="+this.id;
			db.update(query);
			rs.close();
			return true;
		}
		}catch(Exception er){
			return false;
		}
	 
	}

	
	public String getghichu() {
		
		return this.Ghichu;
	}

	
	public void setghichu(String ghichu) {
		
		this.Ghichu=ghichu;
	}
	      public static void main ( String args [  ]  )   { }


		public String getsoPoKH() {

			return this.soPoKH;
		}

		
		public void setsoPoKH(String soPoKH) {
			this.soPoKH= soPoKH;
			
		}

		
		public ResultSet getBom() {
			return this.rsBOM;
		}

		
		public String getBomId() {
			return this.bomId;
		}

		
		public void setBomId(String bomId) {
			this.bomId = bomId;
		}

		
		public List<IErpDinhmuc> getDinhmucList() {
			return this.dinhmucList;
		}

		
		public void setDinhmucList(List<IErpDinhmuc> value) {
			this.dinhmucList = value;
		}

		
		public ResultSet getLohoiRs() {
			return this.lohoiRs;
		}

		
		public void setLohoiId(String lohoiId) {
			this.lohoiId = lohoiId;
		}

		
		public String getLohoiId() {
			return this.lohoiId;
		}

		
		public ResultSet getRskhoTp() {
			
			return RsKhoTp;
		}

		
		public ResultSet getRsKhoLayNL() {
			
			return this.RsKhoLayNL;
		}

		
		public String getIsLsxCongNghe() {
			
			return this.IsLsxCongNghe;
		}

		
		public void setIsLsxCongNghe(String IsLsxCongNghe) {
			
			this.IsLsxCongNghe=IsLsxCongNghe;
		}

		
		public String getDvtBOM() {
			
			return this.dvtBOM;
		}

		
		public void setDvtBOM(String dvtBOM) {
			
			this.dvtBOM=dvtBOM;
		}

		public String getLoaisanphamTH() {
			return loaisanphamTH;
		}

		public void setLoaisanphamTH(String loaisanphamTH) {
			this.loaisanphamTH = loaisanphamTH;
		}

		public ResultSet getLoaisanphamRs() {
			return loaisanphamRs;
		}

		public void setLoaisanphamRs(ResultSet loaisanphamRs) {
			this.loaisanphamRs = loaisanphamRs;
		}

		@Override
		public List<IErpBom> getBomList() {
			// TODO Auto-generated method stub
			return BomList;
		}

		@Override
		public void setBomList(List<IErpBom> list) {
			// TODO Auto-generated method stub
			this.BomList=list;
		}

		@Override
		public String getSohoso() {
			// TODO Auto-generated method stub
			return this.Sohoso;
		}

		@Override
		public void setSohoso(String Sohoso) {
			// TODO Auto-generated method stub
			this.Sohoso=Sohoso;
		}

		@Override
		public String getDmvt() {
			// TODO Auto-generated method stub
			return this.Dmvt;
		}

		@Override
		public void setDmvt(String Dmvt) {
			// TODO Auto-generated method stub
			this.Dmvt=Dmvt;
		}

		@Override
		public String getNSX() {
			// TODO Auto-generated method stub
			return this.NSX;
		}

		@Override
		public void setNSX(String NSX) {
			// TODO Auto-generated method stub
			this.NSX=NSX;
		}

		@Override
		public String getHD() {
			// TODO Auto-generated method stub
			return this.HD;
		}

		@Override
		public void setHD(String HD) {
			// TODO Auto-generated method stub
			this.HD=HD;
		}

		@Override
		public String[] getMangCheck() {
			// TODO Auto-generated method stub
			return this.mangcheck;
		}

		@Override
		public void MangCheck(String[] HD) {
			// TODO Auto-generated method stub
			this.mangcheck=HD;
		}

		@Override
		public String[] getMangCheck_Value() {
			// TODO Auto-generated method stub
			return this.mangcheck_value;
		}

		@Override
		public void MangCheck_Value(String[] HD) {
			// TODO Auto-generated method stub
			this.mangcheck_value=HD;
		}

		@Override
		public String getStrCheck_Value() {
			// TODO Auto-generated method stub
			return Strchon;
		}

		@Override
		public void setStrCheck_Value(String str) {
			// TODO Auto-generated method stub
			Strchon=str;
		}

		@Override
		public String[] getMangCheck_TKL() {
			// TODO Auto-generated method stub
			return this.mangcheck_tkl;
		}

		@Override
		public void MangCheck_TKL(String[] HD) {
			// TODO Auto-generated method stub
			this.mangcheck_tkl=HD;
		}

		@Override
		public String[] getMangCheck_ValueTKL() {
			// TODO Auto-generated method stub
			return this.mangvalue_tkl;
		}

		@Override
		public void setMangCheck_ValueTKL(String[] HD) {
			// TODO Auto-generated method stub
			this.mangvalue_tkl=HD;
		}

		@Override
		public String[] getMangView_CheckTKL() {
			// TODO Auto-generated method stub
			return this.view_check;
		}

		@Override
		public void setMangView_CheckTKL(String[] HD) {
			// TODO Auto-generated method stub
			 this.view_check=HD;
		}

		@Override
		public String getStrCheck_TKL() {
			// TODO Auto-generated method stub
			return this.StrTongket;
		}

		@Override
		public void setStrCheck_ValueTKL(String str) {
			// TODO Auto-generated method stub
			 this.StrTongket=str;
		}

		@Override
		public String getNDHL() {
			// TODO Auto-generated method stub
			return this.NDHL;
		}

		@Override
		public void setNDHL(String NDHL) {
			// TODO Auto-generated method stub
			this.NDHL=NDHL;
		}

		@Override
		public String getGHICHU_TIENDO() {
			// TODO Auto-generated method stub
			return this.GHICHU_TIENDO;
		}

		@Override
		public void setGHICHU_TIENDO(String GHICHU_TIENDO) {
			// TODO Auto-generated method stub
			this.GHICHU_TIENDO = GHICHU_TIENDO;
		}

		@Override
		public String getYKIENKHAC() {
			// TODO Auto-generated method stub
			return this.YKIENKHAC;
		}

		@Override
		public void setYKIENKHAC(String YKIENKHAC) {
			// TODO Auto-generated method stub
			 this.YKIENKHAC=YKIENKHAC;
		}

		@Override
		public String getPHACHE() {
			// TODO Auto-generated method stub
			return this.PHACHE;
		}

		@Override
		public void setPHACHE(String PHACHE) {
			// TODO Auto-generated method stub
			this.PHACHE=PHACHE;
		}

		@Override
		public String getHIEUSUAT_SX() {
			// TODO Auto-generated method stub
			return this.HIEUSUAT_SX;
		}

		@Override
		public void setHIEUSUAT_SX(String HIEUSUAT_SX) {
			// TODO Auto-generated method stub
			 this.HIEUSUAT_SX=HIEUSUAT_SX;
		}

		@Override
		public String getHIEUSUAT_DG() {
			// TODO Auto-generated method stub
			return this.HIEUSUAT_DG;
		}

		@Override
		public void setHIEUSUAT_DG(String HIEUSUAT_DG) {
			// TODO Auto-generated method stub
			this.HIEUSUAT_DG=HIEUSUAT_DG;
		}

		@Override
		public String getTOANCHANG() {
			// TODO Auto-generated method stub
			return this.TOANCHANG;
		}

		@Override
		public void setTOANCHANG(String TOANCHANG) {
			// TODO Auto-generated method stub
			this.TOANCHANG=TOANCHANG;
		}

		@Override
		public String[] getSOTT() {
			// TODO Auto-generated method stub
			return this.SOTT;
		}

		@Override
		public void setSOTT(String[] SOTT) {
			// TODO Auto-generated method stub
			this.SOTT=SOTT;
		}

		@Override
		public String[] getNOIDUNG() {
			// TODO Auto-generated method stub
			return this.NOIDUNG;
		}

		@Override
		public void setNOIDUNG(String[] NOIDUNG) {
			// TODO Auto-generated method stub
			this.NOIDUNG=NOIDUNG;
		}

		@Override
		public String[] getSOPKN() {
			// TODO Auto-generated method stub
			return this.SOPKN;
		}

		@Override
		public void setSOPKN(String[] SOPKN) {
			// TODO Auto-generated method stub
			this.SOPKN= SOPKN;
		}

		@Override
		public String[] getNGAY() {
			// TODO Auto-generated method stub
			return this.NGAY;
		}

		@Override
		public void setNGAY(String[] NGAY) {
			// TODO Auto-generated method stub
			this.NGAY=NGAY;
		}

		@Override
		public String[] getKETQUA() {
			// TODO Auto-generated method stub
			return this.KETQUA;
		}

		@Override
		public void setKETQUA(String[] KETQUA) {
			// TODO Auto-generated method stub
			this.KETQUA=KETQUA;
		}

		@Override
		public String[] getGHICHU() {
			// TODO Auto-generated method stub
			return this.GHICHU;
		}

		@Override
		public void setGHICHU(String[] GHICHU) {
			// TODO Auto-generated method stub
			this.GHICHU=GHICHU;
		}

		public String getQuatrinh1() {
			return quatrinh1;
		}

		public void setQuatrinh1(String quatrinh1) {
			this.quatrinh1 = quatrinh1;
		}

		public String getQuatrinh2() {
			return quatrinh2;
		}

		public void setQuatrinh2(String quatrinh2) {
			this.quatrinh2 = quatrinh2;
		}

		public String getDang1() {
			return dang1;
		}

		public void setDang1(String dang1) {
			this.dang1 = dang1;
		}

		public String getDang2() {
			return dang2;
		}

		public void setDang2(String dang2) {
			this.dang2 = dang2;
		}

		public String getDang3() {
			return dang3;
		}

		public void setDang3(String dang3) {
			this.dang3 = dang3;
		}

		public String getSKN1() {
			return SKN1;
		}

		public void setSKN1(String sKN1) {
			SKN1 = sKN1;
		}

		public String getSKN2() {
			return SKN2;
		}

		public void setSKN2(String sKN2) {
			SKN2 = sKN2;
		}

		public String getSKN3() {
			return SKN3;
		}

		public void setSKN3(String sKN3) {
			SKN3 = sKN3;
		}
		
		public boolean is_ChuaTao_LSX_BTP(){
			String query = "Select PK_SEQ from ERP_LENHSANXUAT_GIAY where trangthai <> 7 and LsxBTP_LsxId = "+this.id;
			 //System.out.println("du lieu query : 0 "+query);
			ResultSet lsxbtp=db.get(query);
			boolean isTrue = true;
			try {
				if(lsxbtp.next()){
					isTrue = false;
				}
				lsxbtp.close();
			} catch (SQLException e) {
				isTrue = false;
				e.printStackTrace();
			}
			return isTrue;
		}
		
		public String taoLsxBtp(){

			try
			{
				if( is_ChuaTao_LSX_BTP() )
				{
					this.db.getConnection().setAutoCommit(false);
					String	query =  " select a.congty_fk ,A.NPP_FK,  khachhang_fk ,ngaydukienht  as  ngaydat  " +
							" from ERP_LENHSANXUAT_GIAY a where a.pk_seq = '" + this.id + "'  ";
					//System.out.println("du lieu query : "+query);
					String ngaydat="";
					String khachhang_fk="";
					ResultSet rs = db.get(query);
					String npp_fk="";
					String congtyId="";
					//String loaidonhang="";
					if(rs.next())
					{ 	
						ngaydat=rs.getString("ngaydat");
						khachhang_fk=rs.getString("khachhang_fk");
						npp_fk= rs.getString("NPP_FK");
						congtyId=rs.getString("congty_fk");
						//loaidonhang=rs.getString("loaidonhang");
					}
					rs.close();
					/*if(!loaidonhang.equals("6")){
						return "";
					}*/
					String khonguyenlieu="";

					query="SELECT PK_SEQ FROM ERP_KHOTT WHERE LOAI='13' AND TRANGTHAI=1 AND CONGTY_FK="+congtyId;
					ResultSet khonl=db.get(query);
					
					if(khonl.next()){
						khonguyenlieu=khonl.getString("PK_SEQ");
					}else{
						this.db.getConnection().rollback();
						return "Không xác định được kho nguyên liệu gia công";
					}
					khonl.close();


					// đơn hàng gia công  =2,thì tạo ra lệnh sản xuất mặc định
					query=  " SELECT  LSX.DONDATHANG_FK, SP.DVKD_FK ,SP.PK_SEQ AS SPID ,SUM(SOLUONGCHUAN) AS SOLUONG " +
							"FROM ERP_LENHSANXUAT_BOM_GIAY LSX_BOM "+
							" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=LSX_BOM.VATTU_FK  " +
							" INNER JOIN ERP_LOAISANPHAM LSP ON SP.LOAISANPHAM_FK = LSP.PK_SEQ " +
							" INNER JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ = LSX_BOM.LENHSANXUAT_FK"+
							" WHERE LSX_BOM.LENHSANXUAT_FK= "+this.id+" and    LSP.IS_BTP=1 "+
							" GROUP BY LSX.DONDATHANG_FK, SP.PK_SEQ, SP.DVKD_FK  ";
					
					 //System.out.println("du lieu query kho: 1.1 "+ query);
					 
					 
					ResultSet rssp=db.get(query);
					while(rssp.next() ){
						////System.out.println("du lieu query : 1");
						String masp=rssp.getString("SPID");
						String dondathang_fk = rssp.getString("DONDATHANG_FK");
						double sum_soluong=rssp.getDouble("soluong");
						String nhamayid="";
						query="select PK_SEQ,nhamay_fk from Erp_KichBanSanXuat_Giay where sanpham_fk ='"+masp+"' and TrangThai=1";
						ResultSet rskichban=db.get(query);
						////System.out.println("du lieu query kho: 0.1 "+ query);
						String kichbansxid="";
						if(!rskichban.next()){
							db.getConnection().rollback();

							return "Sản phẩm của đơn hàng gia công này chưa khai báo đủ thông tin để sản xuất gia công, vui lòng tạo kịch bản sản xuất cho sản phẩm này";

						}else{

							nhamayid=rskichban.getString("nhamay_fk");
							kichbansxid=rskichban.getString("PK_SEQ");
						}
						rskichban.close();
						////System.out.println("du lieu query : 2");

						// lấy kho sản xuất
						query=  " select PK_SEQ  from ERP_KHOTT where TrangThai = '1'\n" +
								" and loai in ('10') and congty_fk = '"+congtyId+"' and NHAMAY_FK="+ nhamayid + "\n";

						ResultSet rskho = db.get(query);
						String khoxuat = "";

						if (rskho.next()) {
							khoxuat = rskho.getString("pk_seq");
							// kho nhận gia công
						}
						rskho.close();
						////System.out.println("du lieu query : 3");

						query = " insert into ERP_LENHSANXUAT_GIAY(NPP_FK,khachhang_fk, ISGIACONG ,diengiai ,ngaybatdau,ngaydukienht ,kichbansanxuat_fk,congty_fk , soluong, nhamay_fk, trangthai, ngaytao, nguoitao, ngaysua, nguoisua,khosanxuat_fk, soPoKH,LsxBTP_LsxId, dondathang_fk) "
								+ " values("+npp_fk+","+khachhang_fk+",'1','LSX BTP LSXID:"+this.id+"','"+ngaydat+"','"+ngaydat+"',"+kichbansxid+","+congtyId+","+sum_soluong+","+nhamayid+",0,'"+getDateTime()+"',"+this.userId+",'"+this.getDateTime()+"',"+this.userId+","+khoxuat+",'',"+this.id+","+dondathang_fk+") ";
						
						if(!db.update(query))
						{
							db.getConnection().rollback();
							return "Không thể tạo mới lệnh sản xuất , vui lòng kiểm tra lại câu lệnh: "+query;
						}

						query = "select IDENT_CURRENT('ERP_LENHSANXUAT_GIAY') as clId";
						rs =  db.get(query);
						rs.next();
						String id = rs.getString("clId");
						rs.close();

						query =   " select distinct sp.dvkd_fk , nm.pk_seq as nhamayid, a.thutu, a.danhmucvattu_fk ,0 as spid , "
								+ " A.DINHLUONG_FK,isnull(B.DINHTINH,'0') as dinhtinh , "
								+ " case when sp1.kiemtradinhluong ='1' and  a.danhmucvattu_fk IS   null then 1  when sp1.kiemtradinhtinh =1 and  a.danhmucvattu_fk IS   null then 1 else isnull(a.kiemdinhchatluong,'0')end  as kiemdinhchatluong  "
								+ ", b.pk_seq,b.diengiai,a.kichbansanxuat_fk,  "
								+ " a.thutu,b.nhamay_fk,nm.manhamay,nm.tennhamay ,isnull( a.sanpham_fk,0) as sanpham_fk ,isnull(sp.ma,'') as masp,isnull(sp.ten,'') as tensp "
								+ " from Erp_KichBanSanXuat_CongDoanSanXuat_Giay a  "
								+ " inner join erp_kichbansanxuat_giay kb on kb.pk_seq= a.kichbansanxuat_fk "
								+ " inner join Erp_CongDoanSanXuat_Giay b on a.congdoansanxuat_fk=b.pk_Seq "
								+ " inner join erp_nhamay nm on nm.pk_seq=b.nhamay_fk  "
								+ " left join  erp_danhmucvattu dmvt on dmvt.pk_seq=a.danhmucvattu_fk		 "
								+ " LEFT join  erp_sanpham sp on sp.ma=dmvt.mavattu  and sp.CONGTY_FK = "+congtyId
								+ " left join erp_sanpham sp1 on sp1.pk_seq=kb.sanpham_fk "
								+ " where kichbansanxuat_fk='" +kichbansxid+"' and kb.sanpham_fk = '"+masp+"' "
								+ " order by a.thutu ";
						////System.out.println("cong doan : "+query);
						rs = db.get(query);
						List<ILenhSXCongDoan> ListCongdoan  = new ArrayList<ILenhSXCongDoan>();
						while (rs.next()) {
							////System.out.println("du lieu query : 4");
							ILenhSXCongDoan lsxcd = new LenhSXCongDoan();
							lsxcd.setCongDoanId(rs.getString("pk_seq"));
							lsxcd.setDiengiai(rs.getString("diengiai"));
							lsxcd.Setkichbansanxuat(rs.getString("kichbansanxuat_fk"));
							lsxcd.setTrangthai("0");
							lsxcd.setActive("1");
							lsxcd.setNhaMayId(rs.getString("nhamay_fk"));
							lsxcd.setBomId(rs.getString("danhmucvattu_fk"));
							lsxcd.setThuTu(rs.getFloat("thutu"));
							lsxcd.setPhanXuong(rs.getString("manhamay"));
							lsxcd.setSanPham(rs.getString("masp")+"-"+ rs.getString("tensp"));
							lsxcd.setMaSp(rs.getString("masp"));
							lsxcd.setSpId(rs.getString("sanpham_fk"));
							lsxcd.SetKiemDinhCL(rs.getString("kiemdinhchatluong"));
							lsxcd.setSoLuong("0");
							ListCongdoan.add(lsxcd);
						}
						rs.close();
						String congdoan_cuoi="";
						for (int i = 0; i <  ListCongdoan.size(); i++) {
							ILenhSXCongDoan congdoan =  ListCongdoan.get(i);
							String trangthaicd = "";
							if (congdoan.getActive().equals("1")) {
								trangthaicd = "0";
							} else {
								trangthaicd = "2";
							}
							if(i==ListCongdoan.size()-1){
								congdoan_cuoi= congdoan.getCongDoanId();
								congdoan.setSoLuong(""+sum_soluong);
							}

							query = "insert into ERP_LENHSANXUAT_CONGDOAN_GIAY (lenhsanxuat_fk,kichban_fk,congdoan_fk,tinhtrang,soluong,THUTU,SANPHAM_FK,DANHMUCVATTU_FK,KIEMDINHCHATLUONG,MASANPHAM,MAYSUDUNG) values "
									+ " ( '"+id+ "','"+kichbansxid+ "','"+ congdoan.getCongDoanId()+ "','"+ trangthaicd	+ "',"+ congdoan.getSoLuong()+ ","+ congdoan.getThutu()+ ","+congdoan.getSpid()+ ","+congdoan.getBomId()+ ",'"+ congdoan.getKiemDinhCL()+ "','"+ congdoan.getMaSp()+ "',N'"+ congdoan.getMaySuDung() + "')";

							if(!db.update(query))
							{
								db.getConnection().rollback();

								return "Không thể tạo mới lệnh sản xuất , vui lòng kiểm tra lại câu lệnh: "+query;
							}
						}


						//cac thanh pham sản xuất ra
						query=	" SELECT SP.PK_SEQ as sanpham_fk,LSX_BOM.soluongchuan SOLUONG ,ISNULL(( SELECT TOP 1 PK_SEQ FROM ERP_DANHMUCVATTU A WHERE A.SANPHAM_FK=SP.PK_SEQ AND A.TRANGTHAI=1),0)  AS DMVT_FK "+
								" FROM ERP_LENHSANXUAT_BOM_GIAY LSX_BOM  "+
								" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ= LSX_BOM.VATTU_FK "+
								" WHERE LSX_BOM.LENHSANXUAT_FK="+this.id+" AND SP.PK_SEQ='"+masp+"'";
						rs=db.get(query);
						while(rs.next()){
							////System.out.println("du lieu query : 5");

							if(rs.getString("DMVT_FK").equals("0")){
								db.getConnection().rollback();
								return "Không thể chốt đơn hàng gia công, BOM của sản phẩm nhận gia công chưa xác định, vui lòng thiết lập dữ liệu nền BOM cho sản phẩm nhận gia công này ";
							}
							query=" insert into ERP_LENHSANXUAT_SANPHAM (LENHSANXUAT_FK,SANPHAM_FK,SOLUONG,DanhMucVT_FK)  " +
									" values ("+id+","+rs.getString("sanpham_fk")+","+rs.getDouble("soluong")+","+rs.getString("DMVT_FK")+" )"; 
							if(!db.update(query))
							{
								db.getConnection().rollback();

								return "Không thể tạo mới lệnh sản xuất , vui lòng kiểm tra lại câu lệnh: "+query;
							}

							query=  " SELECT SP.PK_SEQ AS SPID ,B.PK_SEQ AS DANHMUCVATTU_FK , C.VATTU_FK,   "+rs.getDouble("soluong")+" * C.SOLUONG /  B.SOLUONGCHUAN  AS SOLUONGDENGHI, SP.MA,SP.TEN AS TENSP,dvdl.donvi  "+ 
									" FROM  ERP_DANHMUCVATTU B     "+
									" INNER JOIN ERP_DANHMUCVATTU_VATTU C ON B.PK_SEQ = C.DANHMUCVT_FK  "+ 
									" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=C.VATTU_FK "+
									" inner join donvidoluong dvdl on dvdl.pk_seq=sp.dvdl_fk "+
									" WHERE   B.TRANGTHAI = '1'  and b.pk_seq ="+rs.getString("DMVT_FK") +  
									"  AND B.SANPHAM_FK= "+rs.getString("sanpham_fk");	
							ResultSet rs1=db.get(query);
							int sott=0;
							while (rs1.next()){
								////System.out.println("du lieu query : 6");

								query="INSERT INTO ERP_LENHSANXUAT_BOM_GIAY(LOAI,ISVATTUTHEM,LENHSANXUAT_FK,CONGDOAN_FK,KHOTT_FK, VATTU_FK, SOLUONGCHUAN,CHECKKHO,SOTT,DVT) VALUES" +
										" (1,1,"+id+","+congdoan_cuoi+","+khonguyenlieu+","+rs1.getString("VATTU_FK") +","+rs1.getString("SOLUONGDENGHI")+",0,"+sott+",N'"+rs1.getString("donvi") +"')";
								if(!db.update(query))
								{
									db.getConnection().rollback();

									return "Không thể tạo mới lệnh sản xuất , vui lòng kiểm tra lại câu lệnh: "+query;
								}
								sott++;
							}
							rs1.close();

						}
						rs.close();
						// cập nhật các nguyên liệu cần dùng

					}
					rssp.close();
					
					//this.db.get(" update ERP_LENHSANXUAT_GIAY set IS_DATAO_LSX_BTP = '1' where pk_seq = "+ this.id);
					this.db.getConnection().commit();
					this.db.getConnection().setAutoCommit(true);
					return "";
				}else{
					return "Lệnh sản xuất đã tạo LSX BTP. ";
				}
				
				
			}catch(Exception er){
				er.printStackTrace();
				return "Lỗi : "+ er.getMessage();

			}
		
		}

		@Override
		public boolean yeucauNguyenlieuthem() { 
			return false;
		}

		private String Capnhat_SoluongDinhMuc(dbutils db, String lsxId,String cdid) {
			// TODO Auto-generated method stub
			try{
				String query=	" UPDATE BOM SET BOM.SOLUONG=ISNULL(DATA.SOLUONGCHUAN,0) FROM ( "+
								" SELECT congdoan_fk,LENHSANXUAT_FK,VATTU_FK,SUM(SOLUONGCHUAN) AS SOLUONGCHUAN "+
								" FROM ERP_LENHSANXUAT_BOM_GIAY_CHITIET   where congdoan_fk="+cdid+" and  LENHSANXUAT_FK= "+lsxId+" and ISYCCK='1' "+
								" GROUP BY LENHSANXUAT_FK,VATTU_FK ,congdoan_fk "+
								" ) DATA "+
								" LEFT JOIN ERP_LENHSANXUAT_BOM_GIAY BOM ON BOM.LENHSANXUAT_FK=DATA.LENHSANXUAT_FK AND BOM.VATTU_FK=DATA.VATTU_FK and bom.congdoan_fk= data.congdoan_fk "+
								" where data.congdoan_fk="+cdid+"  and   DATA.LENHSANXUAT_FK="+lsxId;
				if(!db.update(query)){
					return "Không thể cập nhật nhật số lượng định mức của LSX: "+query;
					
				}
				
			}catch(Exception er){
				er.printStackTrace();
				return er.getMessage();
			}
			return "";
		}

		private String createChuyenKho(dbutils db, String lsxId, String userId,
				String khoid,String khonhan_fk, String  congdoanid) {
			try{
				
				Utility_Kho  util_kho=new Utility_Kho();
				
				String ngaychuyen= this.ngaytao;
				
				if(!this.NgayYCThenNL.equals("")){
					ngaychuyen =this.NgayYCThenNL;
				}
				
				 String query=" SELECT  ISNULL(DDH.KHACHHANG_FK,0) as KHACHHANG_FK ,ISNULL(LSX.ISGIACONG,0)  AS ISGIACONG ,NGAYBATDAU , SP.TEN +N'- Số Lô : '+ ISNULL(LSX.DIENGIAI,'') AS SOLO  " +
						 " FROM ERP_LENHSANXUAT_SANPHAM LSXSP  " +
						 " INNER JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ=LSXSP.LENHSANXUAT_FK " +
						 " LEFT JOIN ERP_DONDATHANG DDH ON DDH.PK_SEQ=LSX.DONDATHANG_FK  "+  
						 " INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ= LSXSP.SANPHAM_FK WHERE LENHSANXUAT_FK="+lsxId;
						 
						 //System.out.println("QUERY INIT TU DONG CHUYEN KHO :"+query);
							ResultSet rs=db.get(query);
							String solo="";
							 
							String isgiacong="";
							String KH_XUAT_FK="",DOITUONG="",LOAIDOITUONG="";
							if(rs.next()){
								solo=rs.getString("SOLO");
								isgiacong=rs.getString("ISGIACONG");
								KH_XUAT_FK= rs.getString("KHACHHANG_FK");
								// kho gia công 
								if(Library.getLoaiKho(khoid,db).equals("13")){
									 	
										DOITUONG=KH_XUAT_FK;
										LOAIDOITUONG="1";	
										
								}else{
									KH_XUAT_FK="NULL";
									DOITUONG="";
									LOAIDOITUONG="";
								}
								
								
							}
							rs.close();
							
							
				
				  query = 	" insert ERP_CHUYENKHO(LENHSANXUAT_FK,CONGDOAN_FK,LENHSANXUAT_CD_FK, IsChuyenHangSX,  noidungxuat_fk, NgayChuyen, lydo, ghichu," +
						" trangthai, khoxuat_fk, khonhan_fk,  ngaytao, nguoitao, ngaysua, nguoisua,congty_fk, sochungtu, doituong_fk,LOAIDOITUONG) " +
	   			" values("+lsxId+","+congdoanid+", (SELECT PK_SEQ FROM ERP_LENHSANXUAT_CONGDOAN_GIAY WHERE LENHSANXUAT_FK="+lsxId+" AND CONGDOAN_FK="+congdoanid+" ), 1,100066 , '" + ngaychuyen+ "',    N'Chuyển cho lệnh SX:"+lsxId+" "+solo +
	   			"', N'', '0', '" + khoid + "', " + khonhan_fk + ",  '" + getDateTime() + "', '" + userId + 
	   			"', '" + getDateTime() + "', '" + userId+ "','"+this.congtyId+"', "+lsxId+" ,"+(DOITUONG.length()>0?DOITUONG:"NULL")+", "+(LOAIDOITUONG.length()>0?LOAIDOITUONG:"NULL")+"  ) ";

				 
				if(!db.update(query))
				{
					return "Không thể tạo mới ERP_CHUYENKHO " + query;
					 
				}
				
				String ycnlCurrent = "";
				query = "select SCOPE_IDENTITY() as ckId";
				
				ResultSet rsPxk = db.get(query);						
				if(rsPxk.next())
				{
					ycnlCurrent = rsPxk.getString("ckId");
				 
				}
				
				rsPxk.close();
				
				query =" SELECT  A.DOITUONGID  DOITUONGID , A.LOAIDOITUONG LOAIDOITUONG,A.BIN_FK ,SP.MA , A.LENHSANXUAT_FK ,	VATTU_FK,	A.SANPHAM_FK ,	A.KHOTT_FK , "+
					" A.SOLO , ISNULL(CAST(A.KHUVUCKHO_FK AS NVARCHAR(12)),'')  AS  KHUVUCKHO_FK   , A.MARQUETTE_FK "+   
					" MARQUETTE_FK, ISNULL(A.MARQ,'') AS MARQ  ,A.MATHUNG,	A.MAME "+  	
					" ,A.NGAYNHAPKHO,	A.HAMLUONG ,	A.HAMAM,	 "+
					" A.NGAYHETHAN, 	A.SOLUONG ,  A.HAMLUONG,A.HAMAM, ISNULL(A.MAPHIEU,'') AS MAPHIEU , "+
					" ISNULL(A.MAPHIEUDINHTINH,'') AS MAPHIEUDINHTINH, ISNULL(A.PHIEUEO,'') AS PHIEUEO ,   "+
					" ( SELECT LSX.NPP_FK FROM ERP_LENHSANXUAT_GIAY LSX WHERE LSX.PK_SEQ= "+lsxId+" ) AS NPP_FK "+ 
					" FROM ERP_LENHSANXUAT_BOM_GIAY_CHITIET A   INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ= A.SANPHAM_FK  "+
						" where ISNULL(ISYCCK,'0')='0'  AND congdoan_fk="+congdoanid+" and   lenhsanxuat_fk="+lsxId+"  AND A.KHOTT_FK="+khoid ;
				
				ResultSet rsct=db.get(query);
				//System.out.println("kiem tra kho "+ query);
				while(rsct.next()){
					
					query = " insert ERP_CHUYENKHO_SANPHAM_CHITIET( chuyenkho_fk, SANPHAM_FK,  solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, MAPHIEU, MARQ, HAMLUONG, HAMAM, soluong,BIN_FK,PHIEUEO,MAPHIEUDINHTINH,PHIEUDT ) " +
					" select '" + ycnlCurrent + "', pk_seq,  N'" + rsct.getString("SOLO") + "', '" + rsct.getString("ngayhethan") + 
					" ', '" +rsct.getString("ngaynhapkho") + "', '" + rsct.getString("MAME") + "', '" + rsct.getString("MATHUNG")  + "', '" +rsct.getString("MAPHIEU") + "' " +
					" ,'"+rsct.getString("MARQ")+"' , " +
					" '" +rsct.getString("HAMLUONG") + "', '" + rsct.getString("HAMAM") + "', '" +rsct.getString("soluong")+ "',"+rsct.getString("BIN_FK")+" ,'"+rsct.getString("PHIEUEO")+"','"+rsct.getString("MAPHIEUDINHTINH")+"','"+rsct.getString("MAPHIEUDINHTINH")+"'  " +
					" from ERP_SANPHAM where PK_SEQ = '" + rsct.getString("sanpham_fk") + "' ";
					
					
					 
					if(!db.update(query))
					{
						return "Không thể tạo mới ERP_CHUYENKHO " + query;
					} 
					 
					//booked tổng với chi tiết
					String vitri=rsct.getString("BIN_FK") == null ? "" : rsct.getString("BIN_FK");
					String spId=rsct.getString("sanpham_fk");
					Kho_Lib kholib=new Kho_Lib();
					kholib.setLoaichungtu("erplenhsanxuat.java 5314 chuyenkhotudong: "+ycnlCurrent);
					kholib.setNgaychungtu(ngaychuyen);
					  
					kholib.setKhottId(khoid);
					kholib.setBinId(vitri);
					kholib.setSolo(rsct.getString("solo"));
					kholib.setSanphamId(spId);
					
					kholib.setMame(rsct.getString("MAME"));
					kholib.setMathung(rsct.getString("MATHUNG"));
					kholib.setMaphieu(rsct.getString("MAPHIEU"));
					kholib.setMaphieudinhtinh(rsct.getString("MAPHIEUDINHTINH"));
					kholib.setPhieuEo(rsct.getString("PHIEUEO"));
					kholib.setNgayhethan(rsct.getString("NGAYHETHAN"));
					kholib.setNgaysanxuat("");
					kholib.setNgaynhapkho(rsct.getString("ngaynhapkho") );
					kholib.setBooked( rsct.getDouble("soluong"));
					
					kholib.setSoluong(0);
					kholib.setAvailable(-1*rsct.getDouble("soluong"));
					
					kholib.setMARQ(rsct.getString("MARQ"));
					kholib.setDoituongId(rsct.getString("DOITUONGID")== null ?"":rsct.getString("DOITUONGID"));
					kholib.setLoaidoituong(rsct.getString("LOAIDOITUONG")== null ?"":rsct.getString("LOAIDOITUONG"));
					 
					kholib.setHamluong(rsct.getString("HAMLUONG"));
					kholib.setHamam(rsct.getString("HAMAM") );
					kholib.setNppid(rsct.getString("NPP_FK"));
					
					String msg1= util_kho.Update_Kho_Sp_Tra(db,kholib);
				    if( msg1.length() >0)
					{
						return  msg1;
					}
					 
				}
				rsct.close();
				query = " insert ERP_CHUYENKHO_SANPHAM(chuyenkho_fk, SANPHAM_FK,  soluongyeucau, soluongxuat) " +
						" select chuyenkho_fk, SANPHAM_FK, sum(soluong) as soluongyeucau  , sum(soluong) as soluongxuat   from  " +
						"  ERP_CHUYENKHO_SANPHAM_CHITIET where chuyenkho_fk= "+ycnlCurrent +" group by   chuyenkho_fk, SANPHAM_FK  " ;

				if(!db.update(query))
				{
					return "Không thể tạo mới ERP_CHUYENKHO " + query;
				} 
				
				// CẬP NHẬT ĐÃ YÊU CẦU NGUYÊN LIỆU =1 
				query ="  UPDATE  ERP_LENHSANXUAT_BOM_GIAY_CHITIET  SET CHUYENKHO_FK="+ycnlCurrent+",ISYCCK='1' "+
					   "  where ISNULL(ISYCCK,'0')='0' and congdoan_fk="+congdoanid+" AND  lenhsanxuat_fk="+lsxId+"  AND  KHOTT_FK="+khoid ;
			 
				if(!db.update(query))
				{
					return "Không thể tạo mới ERP_CHUYENKHO " + query;
				} 
				
				
				
			}catch(Exception er){
				er.printStackTrace();
				return er.getMessage();
			}
			// TODO Auto-generated method stub
			return "";
		}
		public String getNGAYBATDAUSX() {
			return NGAYBATDAUSX;
		}

		public void setNGAYBATDAUSX(String nGAYBATDAUSX) {
			NGAYBATDAUSX = nGAYBATDAUSX;
		}

		public String getNGAYHOANTHANH() {
			return NGAYHOANTHANH;
		}

		public void setNGAYHOANTHANH(String nGAYHOANTHANH) {
			NGAYHOANTHANH = nGAYHOANTHANH;
		}

		public String getSONGAYCHAMSOVOILENH() {
			return SONGAYCHAMSOVOILENH;
		}

		public void setSONGAYCHAMSOVOILENH(String sONGAYCHAMSOVOILENH) {
			SONGAYCHAMSOVOILENH = sONGAYCHAMSOVOILENH;
		}

		public String getSOLUONGNHAPKHO() {
			return SOLUONGNHAPKHO;
		}

		public void setSOLUONGNHAPKHO(String sOLUONGNHAPKHO) {
			SOLUONGNHAPKHO = sOLUONGNHAPKHO;
		}

		public String getSOLUONGLAYMAU() {
			return SOLUONGLAYMAU;
		}

		public void setSOLUONGLAYMAU(String sOLUONGLAYMAU) {
			SOLUONGLAYMAU = sOLUONGLAYMAU;
		}

		public String getQTSX() {
			return QTSX;
		}

		public void setQTSX(String qTSX) {
			QTSX = qTSX;
		}

		public String getTCKT() {
			return TCKT;
		}

		public void setTCKT(String tCKT) {
			TCKT = tCKT;
		}

		public String getHOSOLOGOC() {
			return HOSOLOGOC;
		}

		public void setHOSOLOGOC(String hOSOLOGOC) {
			HOSOLOGOC = hOSOLOGOC;
		}

		public String getQUYETDINHLUUHANH() {
			return QUYETDINHLUUHANH;
		}

		public void setQUYETDINHLUUHANH(String qUYETDINHLUUHANH) {
			QUYETDINHLUUHANH = qUYETDINHLUUHANH;
		}

		public String getSoLenhSanXuat() {
			return soLenhSanXuat;
		}

		public void setSoLenhSanXuat(String soLenhSanXuat) {
			this.soLenhSanXuat = soLenhSanXuat;
		}

		public String getSDK() {
			return SDK;
		}

		public void setSDK(String sDK) {
			SDK = sDK;
		}

		public String getCanCuLamLenh() {
			return canCuLamLenh;
		}

		public void setCanCuLamLenh(String canCuLamLenh) {
			this.canCuLamLenh = canCuLamLenh;
		}

		public String getNppId() {
			return nppId;
		}
		public void setNppId(String nppId) {
			this.nppId = nppId;
		}

		@Override
		public String getIsLsxGiacong() {
			// TODO Auto-generated method stub
			return IsLSXGiacong;
		}

		@Override
		public void setIsLsxGiacong(String islsxGc) {
			// TODO Auto-generated method stub
			IsLSXGiacong=islsxGc;
		}

		@Override
		public String getIsGiacong() {
			// TODO Auto-generated method stub
				try{
				
				String isgiacong="";
				if(this.id.length()>0){
				String query="SELECT  ISNULL(ISGIACONG,'0') AS ISGIACONG ,khachhang_fk  FROM ERP_LENHSANXUAT_GIAY WHERE PK_SEQ="+this.id;
				ResultSet rs=db.get(query);
			
				if(rs.next()){
					isgiacong=rs.getString("ISGIACONG");
					this.IsLSXGiacong=rs.getString("ISGIACONG");
					this.KhGiaCongId=rs.getString("khachhang_fk");
					
					if(this.KhGiaCongId==null){
						this.KhGiaCongId="";
					}
					
				}
				rs.close();
				}
				return isgiacong;
				
			}catch(Exception er){
				er.printStackTrace();
			}
			return "";
		}

		@Override
		public boolean LuuCongDoanNhapXuat() {
		 
			
			try 
			{
				db.getConnection().setAutoCommit(false);
				String query ="";
				
				  
					query = "delete ERP_LENHSANXUAT_CONGDOAN_NHAPXUAT where lenhsanxuat_fk = '" + this.id + "'";
					if(!db.update(query))
					{
						this.msg = "Không thể cập nhật Kichbansanxuat(không xoá được ERP_LENHSANXUAT_CONGDOAN_NHAPXUAT): " + query;
						db.getConnection().rollback();
						return false;
					} 
					 
				 ////System.out.println("size  :  23232  "+this.ListCongdoan.size());
				if(this.ListCongdoan.size() >0){
					 
					for(int i = 0; i < this.ListCongdoan.size(); i++)
					{
						ILenhSXCongDoan congdoan=this.ListCongdoan.get(i);
					 
						// PhatNguyen thêm công đoạn nhập xuất
						ArrayList<String> soluongNhap = congdoan.getSoluongNhap();
						ArrayList<String> soluongXuat = congdoan.getSoluongXuat();
						ArrayList<String> soluongDuPham = congdoan.getSoluongDuPham();
						ArrayList<String> soluongPhePham = congdoan.getSoluongPhePham();
						
						//Loop theo số lần nhập mà lưu vào bảng công đoạn nhập xuất
						for(int j = 0 ; j < soluongNhap.size() ; j++ ){
							int lannhap = j +1 ;
						//Lưu thêm công đoạn nhập xuất
							query=  " INSERT INTO ERP_LENHSANXUAT_CONGDOAN_NHAPXUAT ([LENHSANXUAT_FK],[CONGDOAN_FK],"
									+ "[NHAP],[XUAT],[CHUYENDUPHAM],[CHUYENPHEPHAM],[LANNHAP]) VALUES " +
									" ( '"+this.id+"','"+congdoan.getCongDoanId()+"',";
							query += soluongNhap.get(j) +"," + soluongXuat.get(j) +",";
							query += soluongDuPham.get(j) +"," + soluongPhePham.get(j) +",";
							query += lannhap +")";
							int test = j + 1 ;
							////System.out.println("query lan (" + test + ") la : \n" + query);
							//Bổ sung các giá trị nhập xuất của mỗi lần nhập
							if(!db.update(query))
							{
								this.msg = "Không thể tạo mới công đoạn nhập xuất: " + query;
								db.getConnection().rollback();
								return false;
							}
							
						}
						
					}
				}
				/*****END			 *  ***/
				
					
		  
				 
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
				
				return true;
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				db.update("rollback");
				this.msg = "Không thể tạo mới lệnh sản xuất: " + e.getMessage();
				return false;
			}
		}
		
		public boolean isLsxBTP(){
			String query = " select * from erp_lenhsanxuat_giay where lsxbtp_lsxid is not null and pk_seq = "+this.id;
			ResultSet rs = this.db.get(query);
			try {
				while(rs.next()){
					rs.close();
					return true;
				}
				rs.close();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
				return false;
			}
			return false;
		}

		@Override
		public boolean getIsChangeBom() {
			// TODO Auto-generated method stub
			return this.IsChangeBom;
		}

		@Override
		public void setIsChangeBom(boolean IsChangeBom) {
			// TODO Auto-generated method stub
			 this.IsChangeBom=IsChangeBom;
		}

		public String getSoLSX() {
			return SoLSX;
		}

		public void setSoLSX(String soLSX) {
			SoLSX = soLSX;
		}

		@Override
		public String getKhoSXId() {
			// TODO Auto-generated method stub
			return this.KhosxId;
		}

		@Override
		public void setKhoSXId(String KhoSXId) {
			// TODO Auto-generated method stub
			this.KhosxId=KhoSXId; 
		}

		@Override
		public ResultSet getRsKhoSX() {
			// TODO Auto-generated method stub
			return this.RsKhoSX;
		}

		@Override
		public void setRsKhoSX(ResultSet RsKhoSX) {
			// TODO Auto-generated method stub
			this.RsKhoSX= RsKhoSX;
		}

		@Override
		public void ReLoad_CongDoan(boolean is_reload) {
			// TODO Auto-generated method stub
			try{
					int i=this.get_cd_hientai();
					
					System.out.println("vao day rôi: "+i);
					if(i==-1){
						this.msg="Không xác định được công đoạn hiện tại";
						return;
					}
					
					//
					this.KiemTraNguyenLieu_Theo_Congdoan(i,is_reload);
					
					
				
			}catch(Exception er){
				er.printStackTrace();
			}
		}

		private int get_cd_hientai() {
			// TODO Auto-generated method stub
			/// 
			try{
				for(int i = 0; i < this.ListCongdoan.size(); i++)
				{
					ILenhSXCongDoan congdoan=this.ListCongdoan.get(i);
					if(congdoan.getCongDoanId().equals(this.congdoanCurrent)){
						return i;
					}
				}
			
			}catch(Exception er){
				this.msg=er.getMessage();
			}
			return -1 ;
		}
		
		private void KiemTraNguyenLieu_Theo_Congdoan( int j ,boolean is_reload  ) {
			
			try{	
					String query = "";
				 
								 ILenhSXCongDoan congdoan = ListCongdoan.get(j);
								 List<IDanhmucvattu_SP> dmvtList_cd = new ArrayList<IDanhmucvattu_SP>();
									 String cdId=congdoan.getCongDoanId();
									  
						
									if (this.KhosxId.trim().length() == 0)
									{
										msg = "Không có kho sản xuất, vui lòng kiểm tra nhà máy đã chọn kho chưa hoặc, kho phải hoạt động ";						
										return;
									}
										
							 
							 
									 double soluongspsx = 0;
									 
									 
								 	try{
										soluongspsx= Double.parseDouble(congdoan.getSoLuong());
										
									}catch(Exception er){
										
									}
								 	if(soluongspsx==0){
								 		msg = "Số lượng sản xuất của công đoạn không xác định ";						
										return;
								 	}
								 	if(congdoan.getBomId().length()==0){
								 		msg = "BOM của công đoạn không xác định ";						
										return;
								 	}
									 
									if(soluongspsx >0 && congdoan.getBomId().length() > 0)	{
											
										
										// kiểm tra nếu đã lưu BOM, và BOM chưa reloa
										
										// hiển thị BOM 
										// nếu công đoạn chưa lưu bom, mà gọi hiển thị thì load từ BOM, 
										// còn nếu đã lưu thì load từ dữ liệu đã lưu, 
										// 3 nếu đã lưu rồi, mà chưa yêu cầu nguyên liệu 
										if(this.check_congdoan_chua_LuuBom(cdId) || ( is_reload==true && this.check_congdoan_chua_YCNL(cdId) )){
											
											query=     "  SELECT  DANHMUC.PK_SEQ AS BOMID  \n" +  
													   "  , SP1.LOAISANPHAM_FK , SP1.PK_SEQ AS IDSP, SP1.MA AS VTMA,  \n" +  
													   "  SP1.TEN   AS VTTEN, ISNULL(DVDL1.DONVI,'') AS VTDONVI,  \n" +  
													   "  VATTU.SOLUONG  AS SOLUONG,   \n" +  
													   "  DANHMUC.SOLUONGCHUAN ,0 as SOLUONG , isnull(VATTU.isTINHHAMAM,0) as isTINHHAMAM, " +
													   "  ISNULL(VATTU.HAMLUONG,100) AS HAMLUONG, ISNULL(VATTU.HAMAM,0) AS HAMAM " +
													   ", isnull(VATTU.isTINHHAMLUONG,0) as isTINHHAMLUONG ,case when tpdaura.sanpham_fk is not null then 1 else 0 end as is_tpdaura   \n" +  
													   "  FROM ERP_DANHMUCVATTU DANHMUC     \n" +  
													   "  INNER JOIN ERP_DANHMUCVATTU_VATTU VATTU ON VATTU.DANHMUCVT_FK = DANHMUC.PK_SEQ   \n" +  
													   "  INNER JOIN ERP_SANPHAM SP1 ON VATTU.VATTU_FK = SP1.PK_SEQ     \n" +  
													   "  LEFT JOIN DONVIDOLUONG DVDL1 ON SP1.DVDL_FK = DVDL1.PK_SEQ  "
													   + " left join "
													   + "(SELECT DISTINCT  DMVT.SANPHAM_FK  FROM ERP_KICHBANSANXUAT_CONGDOANSANXUAT_GIAY A   "
													   + "INNER JOIN ERP_DANHMUCVATTU DMVT ON DMVT.PK_SEQ= A.DANHMUCVATTU_FK "
													   + "  WHERE KICHBANSANXUAT_FK= "+this.kbsxId+") as tpdaura on tpdaura.sanpham_fk = SP1.PK_SEQ     \n" +  
													   "  WHERE     DANHMUC.PK_SEQ= " +congdoan.getBomId() + " "
													 	+ " and DANHMUC.CONGTY_FK="+this.congtyId+" order by  VATTU.SOTT  ";  
											
											
											
										}else {
										
										
												  query=" SELECT ( select DANHMUCVATTU_FK  from ERP_LENHSANXUAT_CONGDOAN_GIAY "
												  		+ " where LENHSANXUAT_FK= A.LENHSANXUAT_FK and CONGDOAN_FK= a.CONGDOAN_FK ) AS  bomid "
												  		+ " ,  isnull(A.SOLUONG,0) as SOLUONG , ISNULL(DUTONKHO,0) AS DUTONKHO  , CD.DIENGIAI AS TENCONGDOAN,  \n" +
														" LENHSANXUAT_FK, CONGDOAN_FK, SP.MA AS VTMA, SP.TEN AS VTTEN, A.VATTU_FK AS IDSP, A.KHOTT_FK, \n" +  
														" isnull( A.SOLUONG,0) as soluongdayc , isnull( A.SOLUONGCHUAN,0) AS SOLUONGCHUAN, DVT as VTDONVI , \n" +
														" isnull(a.isTINHHAMAM,'0') as isTINHHAMAM  ,isnull(a.isTINHHAMLUONG,'0') as isTINHHAMLUONG ,ISNULL(A.HAMAM,0) AS HAMAM \n" +
														",ISNULL(A.HAMLUONG,0) AS HAMLUONG  ,case when tpdaura.sanpham_fk is not null then 1 else 0 end as is_tpdaura  FROM \n" +	  
														" ERP_LENHSANXUAT_BOM_GIAY A  INNER JOIN   \n" +
														" ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ=A.LENHSANXUAT_FK   \n" +
														" INNER JOIN ERP_CONGDOANSANXUAT_GIAY CD ON A.CONGDOAN_FK=CD.PK_SEQ \n" +  
														" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = A.VATTU_FK    \n"  
														   + " left join "
														   + "(SELECT DISTINCT  DMVT.SANPHAM_FK  FROM ERP_KICHBANSANXUAT_CONGDOANSANXUAT_GIAY A   "
														   + "INNER JOIN ERP_DANHMUCVATTU DMVT ON DMVT.PK_SEQ= A.DANHMUCVATTU_FK "
														   + "  WHERE KICHBANSANXUAT_FK= "+this.kbsxId+") as tpdaura on tpdaura.sanpham_fk = SP.PK_SEQ     \n" +  
														" WHERE A.LENHSANXUAT_FK = "+this.id +" and a.congdoan_fk="+congdoan.getCongDoanId()+"  "
													  + " ORDER BY A.SOTT \n";
										}
									 
									
								     
												ResultSet rs=db.get(query);
												
												NumberFormat formatter_3 = new DecimalFormat("#######.###"); 
												IDanhmucvattu_SP vt = null;
														if(rs == null){
															msg = "Lỗi trong quá trình lấy dữ liệu thất bại, vui lòng báo admin để trợ giúp :lỗi dòng lệnh : "+query;						
															return;
														}
													
													while(rs.next())
													{ 
														
														vt = new Danhmucvattu_SP();
														String Idsp=rs.getString("IDSP");
														vt.setIdVT(Idsp);
														
														 List<ISpSanxuatChitiet>  list_dayc=this.getList_Dayeucau_ct(vt,congdoan.getCongDoanId());
														 vt.setListCT__DaYCCK(list_dayc);
													 
														double soluongsx=0;
														if(this.check_congdoan_chua_LuuBom(cdId) || ( is_reload==true && this.check_congdoan_chua_YCNL(cdId) )){
														
															if( rs.getDouble("SOLUONGCHUAN") * rs.getDouble("SOLUONG") <=0){
																	this.msg="Vui Lòng kiểm tra lại quy cách và kiểm tra số lượng tỉ lệ tạo BOM của sản phẩm này" +congdoan.getSanPham();
																	return; 
															}else{
																	soluongsx =Double.parseDouble(formatter_3.format(soluongspsx /rs.getDouble("SOLUONGCHUAN")*rs.getDouble("SOLUONG")));
															}
															// số lượng chuẩn chính là số lượng tính ra được quy chuẩn
															System.out.println(soluongsx);
															vt.setSoLuongChuan(soluongsx+"");
															vt.setSoluongDaYC("0");
														}else{
															// nếu đã yêu cầu nguyên liệu, thì chỉ cần yêu cầu lượng thêm 
															vt.setSoLuongChuan(rs.getString("SOLUONGCHUAN"));// đã lưu trong database
															soluongsx = ( rs.getDouble("soluongchuan")-rs.getDouble("soluongdayc")<0?0:  rs.getDouble("soluongchuan")-rs.getDouble("soluongdayc"));
															vt.setSoluongDaYC(rs.getString("soluongdayc"));
															
														}
														
													
														String dmvt_fk =rs.getString("bomid");
														vt.setBomId(dmvt_fk);
														
														vt.setMaVatTu(rs.getString("vtMa"));
														vt.setTenVatTu(rs.getString("vtTen"));
														vt.setDvtVT(rs.getString("vtDonvi"));
														vt.setCongDoanId(congdoan.getCongDoanId());
														vt.setTenCongDoan(congdoan.getDiengiai());
													 
														
														 
														String isTINHHAMAM=rs.getString("isTINHHAMAM");
														String isTINHHAMLUONG=rs.getString("isTINHHAMLUONG");
														
														String  HAMAM=rs.getString("HAMAM");
														String  HAMLUONG=rs.getString("HAMLUONG");
														vt.setHamam(HAMAM);
														vt.setHamluong(HAMLUONG);
														
														vt.setIsTinhHA(isTINHHAMAM);
														vt.setIsTinhHL(isTINHHAMLUONG);
														 
														vt.SetKhoid(this.KhosxId);
														vt.setSoLuong(""+soluongsx);
														
														List<ISpSanxuatChitiet> listct =new ArrayList<ISpSanxuatChitiet>();
														 // sản phẩm đầu ra của các công đoạn thì không cần yêu cầu,sản xuất ra thì tiêu hao BTP
														if(!rs.getString("is_tpdaura").equals("1")){
													   	double tonkho=  this.getListSpSxCT(isTINHHAMAM,isTINHHAMLUONG,soluongsx,Idsp,this.KhosxId,HAMAM,HAMLUONG,listct,Idsp,this.id,congdoan.getCongDoanId(),is_reload);
														
														 
												   		double soluongconlai=0;
												   		
														if(tonkho> soluongsx) {
															soluongconlai=0;
														}else{
															soluongconlai=soluongsx-tonkho;
															
														}
															
															
														  query=" select VATTUTT_FK,SP.MA,SP.TEN,DV.DONVI,TT.SOLUONG   FROM ERP_DANHMUCVATTU_VATTU_THAYTHE  TT  " +
														  		" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=TT.VATTUTT_FK " +
														  		" LEFT JOIN DONVIDOLUONG DV ON DV.PK_SEQ=SP.DVDL_FK " +
														  		"  WHERE vattu_fk="+Idsp+" and  DANHMUCVT_FK="+dmvt_fk;
														  ResultSet rssptt=db.get(query);
														  
														  while (  rssptt.next()){
																  String vattutt_fk= rssptt.getString("VATTUTT_FK");
																  // ADD THÊM CÁC SP CÒN THIẾU
																tonkho = this.getListSpSxCT(isTINHHAMAM,isTINHHAMLUONG,soluongconlai,vattutt_fk,this.KhosxId,HAMAM,HAMLUONG,listct,Idsp,this.id,congdoan.getCongDoanId(),is_reload);
															    if(tonkho> soluongconlai) {
																	soluongconlai=0;
																}else{
																	soluongconlai=soluongconlai-tonkho;
																	
																}
															 
														  }
														  rssptt.close();
														  
														  // bước cuối này là phân bổ số lượng SP vào trong list SP chi tiết, và biết được sp đủ nguyên liệu hay không?
														  listct =this.SapsepList(listct);
														  
														  vt.setListCTkho(listct);
														  if(soluongconlai==0){
															  vt.setChon("1");
															  
														  }
														}
														 
														  dmvtList_cd.add(vt);
														  
															  
													}
												 
												} 
			 
					 congdoan.setListDanhMuc(dmvtList_cd);
					 
					 
				}catch(Exception err) {
					err.printStackTrace();
					this.msg=err.toString();
				}
			}

		private boolean check_congdoan_chua_LuuBom(String cdId) {
			// TODO Auto-generated method stub
			try{
				String id_=this.id;
				if(this.id==null|| this.id.equals("")){
					id_="0";
				}
				String query="select count(congdoan_fk) as count from ERP_LENHSANXUAT_BOM_GIAY where LENHSANXUAT_FK= "+id_+" and CONGDOAN_FK="+cdId;
				ResultSet rs=db.get(query);
				int bien=0;
				if(rs.next()){
					bien=rs.getInt("count");
				}
				rs.close();
				if(bien==0){
					return true;
				}else{
					return false;
				}
			}catch(Exception er){
				
			}
			return false;
		}
		// đã yêu cầu nguyên liệu thì không được reload BOM 
		private boolean check_congdoan_chua_YCNL(String cdId) {
			// TODO Auto-generated method stub
			try{
					String id_=this.id;
					if(this.id==null|| this.id.equals("")){
						id_="0";
					}
					String query="select count(congdoan_fk) as count from ERP_LENHSANXUAT_BOM_GIAY_CHITIET "
							   + " where LENHSANXUAT_FK= "+id_+" and congdoan_fk="+cdId+" AND CHUYENKHO_FK IS NOT NULL ";
					ResultSet rs=db.get(query);
					int bien=0;
					if(rs.next()){
						bien=rs.getInt("count");
					}
					rs.close();
					if(bien==0){
						return true;
					}else{
						return false;
					}
			}catch(Exception er){
				
			}
			return false;
		}

		@Override
		public boolean update_CongDoan() {
			// TODO Auto-generated method stub
			try{
					int i=this.get_cd_hientai();
					
					System.out.println("vao day rôi: "+i);
					if(i==-1){
						this.msg="Không xác định được công đoạn hiện tại";
						return false;
					}
				
					 ILenhSXCongDoan congdoan = ListCongdoan.get(i);
					 
					 if(congdoan.getListDanhMuc().size()==0){
						 this.msg="Không xác định được danh mục vật tư của công đoạn";
							return false;
					 }
					 
					 db.getConnection().setAutoCommit(false);
				 
				 	String msg1=this.Save_nguyenLieu_Congdoan(congdoan,db,this.id);
					if(msg1.length() >0){
						this.msg=msg1;
						return false;
					}
					db.getConnection().commit();
					db.getConnection().setAutoCommit(true);
				
			}catch(Exception er){
				this.msg=er.getMessage();
				db.update("rollback");
				return false;
			}
			return false;
		}

		@Override
		public boolean yeucauNguyenlieu_TheoCongdoan() {
			// TODO Auto-generated method stub
			try{
				int i=this.get_cd_hientai();
				
				System.out.println("vao day rôi: "+i);
				if(i==-1){
					this.msg="Không xác định được công đoạn hiện tại";
					return false;
				}
				db.getConnection().setAutoCommit(false);
				
				
				ILenhSXCongDoan congdoan = ListCongdoan.get(i);
				if( this.check_congdoan_chua_YCNL(congdoan.getCongDoanId())){
					String msg1=this.Save_nguyenLieu_Congdoan(congdoan,db,this.id);
					if(msg1.length() >0){
						db.getConnection().rollback();
						this.msg=msg1;
						return false;
					}
				}else{
					// yêu cầu rồi chỉ save thêm những nguyên khác 
					String msg1=this.Save_nguyenLieu_Them(congdoan,db,this.id);
					if(msg1.length() >0){
						db.getConnection().rollback();
						this.msg=msg1;
						return false;
					}
				}
				 // tạo yêu cầu nguyên liệu
				
				
				String query=	" select  DISTINCT	a.KHOTT_FK , " +
						"( select lsx.khosanxuat_fk from ERP_LENHSANXUAT_GIAY lsx where lsx.PK_SEQ= "+this.id+" ) as khonhanid    "+
						" from ERP_LENHSANXUAT_BOM_GIAY_CHITIET a  "+
						" where  ISNULL(ISYCCK,'0')='0'  AND  lenhsanxuat_fk="+this.id +" and congdoan_fk="+congdoan.getCongDoanId() ;
				 
				 	ResultSet rskho=db.get(query);
				 	
					while(rskho.next()){
						String khoid=rskho.getString("KHOTT_FK");
						////System.out.println("Kho xuat: "+khoid);
						String khonhanid=rskho.getString("khonhanid");
						String msg1=this.createChuyenKho(db, this.id, this.userId,khoid,khonhanid,congdoan.getCongDoanId());
						if(msg1.length()>0){
							
							db.getConnection().rollback();
							this.msg=msg1;
							return false;
						}
					}
					rskho.close();
					String msg1=	 this.Capnhat_SoluongDinhMuc(db,this.id,congdoan.getCongDoanId());
					if(msg1.length()>0){
						db.getConnection().rollback();
						this.msg= msg1;
						return false;
					}
					
					
					db.getConnection().commit();
					db.getConnection().setAutoCommit(true);
					
			}catch(Exception er){
				this.msg="Lỗi trong quá trình cập nhật, vui lòng báo Admin để được trợ giúp . "+er.getMessage();
						
				db.update("rollback");
				er.printStackTrace();
				return false;
			}
			this.msg="Bạn đã tạo xong yêu cầu nguyên liệu cho công đoạn";
			return true;
		}

		@Override
		public String getLsxIds() {
			// TODO Auto-generated method stub
			return this.lsxIds;
		}

		@Override
		public void setLsxIds(String lsxIds) {
			// TODO Auto-generated method stub
			this.lsxIds=lsxIds;
		}

		@Override
		public ResultSet getLsxRs() {
			// TODO Auto-generated method stub
			return this.lsxRs;
		}

		@Override
		public void setLsxRs(ResultSet lsxRs) {
			// TODO Auto-generated method stub
			this.lsxRs=lsxRs;
		}

		@Override
		public String getCDSXId() {
			// TODO Auto-generated method stub
			return this.CdsxId;
		}

		@Override
		public void setCDSXId(String CDSXId) {
			// TODO Auto-generated method stub
			this.CdsxId=CDSXId;
		}

		@Override
		public ResultSet getCDSXRs() {
			// TODO Auto-generated method stub
			return CdsxRs;
		}

		@Override
		public void setCDSXRs(ResultSet CDSXRs) {
			// TODO Auto-generated method stub
			this.CdsxRs=CDSXRs; 
		}

		@Override
		public boolean update_tieuhao() {
			if(this.msg.trim().length() > 0)
			{
				this.msg = "Vui lòng kiểm tra lại các thông tin: \n" + this.msg;
				return false;
			}
			if(this.khoanmucchiphi.trim().length() <= 0){
				this.msg = "Vui lòng kiểm tra lại nhóm chi phí: \n";
				return false;
			}
			
			if(this.checkNgayTieuHao() == false){
				this.msg="Vui lòng chọn ngày tiêu hao cùng tháng với nhận hàng";
				return false;
			}
  
			Utility util= new Utility();
			/*
			msg= util.checkNgayHopLe(this.db, this.getNgaytieuhao(),this.congtyId);
			if(this.msg.trim().length() > 0)
			{
				this.msg = "Vui lòng kiểm tra lại các thông tin: \n" + this.msg;
				return false;
			}*/
			
			//Check vattu List
			if(this.dmvtList.size() <= 0)
			{
				this.msg = "Không có danh mục vật tư nào để tiêu hao.";
				return false;
			}
		
			for(int i = 0; i < this.dmvtList.size(); i++)
			{
				IDanhmucvattu_SP vattu = this.dmvtList.get(i);
				List<ISpDetail> spDetail = vattu.getSpDetailList();
				double soluongtong=0;
				for(int j = 0; j < spDetail.size(); j++)
				{
					ISpDetail detail = spDetail.get(j);
					soluongtong=soluongtong+Double.parseDouble(detail.getSoluong());
				}
				 
				if(Double.parseDouble( formatter.format( Double.parseDouble(vattu.getSoLuongTHThucTe()))) -Double.parseDouble(formatter.format(soluongtong)) >0 ){
					this.msg = "Vui lòng kiểm tra lại Bean / Lô tiêu hao của vật tư: " + vattu.getMaVatTu() + ", " + vattu.getTenVatTu();
					return false;
				}
			}
			
			String query =	  
				" select lsx.pk_seq as doituongid ,KHO.pk_seq \n" +
				"from ERP_KHOTT KHO \n" +
				" inner join erp_lenhsanxuat_congdoan_giay lsx on lsx.KHOSANXUAT_FK=KHO.PK_sEQ \n" +
				" where    lsx.lenhsanxuat_fk ="+this.lsxIds +" and lsx.congdoan_fk="+this.CdsxId;

			String khoTieuhao_fk="";
			String doituongid="";
			ResultSet rsKho = db.get(query);
			if(rsKho != null)
			{
				try 
				{
					if(rsKho.next())
					{
						khoTieuhao_fk = rsKho.getString("pk_seq");
						doituongid= rsKho.getString("doituongid");
					}
					rsKho.close();
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(khoTieuhao_fk==null  || khoTieuhao_fk.equals("")){
				this.msg = "Không xác định được kho sản xuất ";
				return false;
			}
			boolean IsQlkhuvuc_khoTieuhao=false;
			
			if(util_kho.getIsQuanLyKhuVuc(khoTieuhao_fk,db).equals("1")){
				IsQlkhuvuc_khoTieuhao=true;
			}
			String nam = this.getNgaytieuhao().substring(0, 4);
			String thang = this.getNgaytieuhao().substring(5, 7);
			
			try 
			{
			db.getConnection().setAutoCommit(false);
  
			boolean bien_=this.revert_kho_tieuhao(this.IdTieuHao);
			if(!bien_){
				db.getConnection().rollback();
				return false;
			}
			
			
				query="update  ERP_TIEUHAONGUYENLIEU  "+
				"  set  LENHSANXUAT_FK="+this.lsxIds+",CONGDOAN_FK="+this.CdsxId+" "
						+ " ,NGUOISUA="+this.userId+",NGAYSUA= '"+this.getDateTime()+"' , "
								+ "NGAYTIEUHAO= '"+this.getNgaytieuhao()+"' ,LOAISANPHAM= '"
				+this.loaisanphamTH+"',nhomchiphi_fk ='"+this.khoanmucchiphi+"' where pk_seq="+this.IdTieuHao+ " and trangthai =0"; 
		  	 
			if(db.updateReturnInt(query)!=1 )
			{
				this.msg = "1.Khong the cap nhat ERP_TIEUHAONGUYENLIEU: " + query;
				db.getConnection().rollback();
				return false;
			}
			String chungtuid=this.IdTieuHao;
			String [] mangnhapkhoid=this.IdNhapkho.split(",");
			for(int i=0;i < mangnhapkhoid.length;i++){
				if(mangnhapkhoid[i].length()>2){
					query="insert into ERP_LSXTIEUHAO_NHAPKHO (TIEUHAO_FK,NHAPKHO_FK) values ("+chungtuid+","+mangnhapkhoid[i]+")";
					if(!db.update(query))
					{
						this.msg = "1.Khong the cap nhat ERP_LSXTIEUHAO_NHAPKHO: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			 	
			query="delete ERP_LENHSANXUAT_TIEUHAO where TIEUHAONGUYENLIEU_FK="+chungtuid;
			if(!db.update(query))
			{
				this.msg = "1.Khong the cap nhat ERP_LSXTIEUHAO_NHAPKHO: " + query;
				db.getConnection().rollback();
				return false;
			}
			query="delete ERP_LENHSANXUAT_TIEUHAO_CHITIET where TIEUHAONGUYENLIEU_FK="+chungtuid;
			if(!db.update(query))
			{
				this.msg = "1.Khong the cap nhat ERP_LSXTIEUHAO_NHAPKHO: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			for(int i = 0; i < this.dmvtList.size(); i++)
			{
				IDanhmucvattu_SP vattu = this.dmvtList.get(i);
				if(Double.parseDouble(vattu.getSoLuongTHThucTe().replaceAll(",", "")) >0 ){
				 
					query = " Insert ERP_LENHSANXUAT_TIEUHAO ( TIEUHAONGUYENLIEU_FK,KHOTT_FK, sanpham_fk, soluong, dongia, thanhtien,loai  ,SOLUONGDINHMUC) " +
							" select  "+chungtuid+","+khoTieuhao_fk+", pk_seq,  " + vattu.getSoLuongTHThucTe().replaceAll(",", "") + " ,0,0 "+
							" ,'"+vattu.getLoai()+"',"+vattu.getSoLuong()+"   from ERP_SanPham where pk_seq = '" + vattu.getIdVT()+"'";
					 
					if(!db.update(query))
					{
						this.msg = "1.Không thể cập nhật : " + query;
						db.getConnection().rollback();
						return false;
					}
				 
					if(vattu.getSoLuongTHThucTe().trim().length() > 0)
					{
						List<ISpDetail> spDetail = vattu.getSpDetailList();
						for(int j = 0; j < spDetail.size(); j++)
						{
							ISpDetail detail = spDetail.get(j);
							String solo = detail.getSolo();
							double soluongct =0;
							try{
								soluongct =Double.parseDouble(detail.getSoluong());
							}catch(Exception er){
							}
							if(IsQlkhuvuc_khoTieuhao){
								 if( detail.getKhuId()==null || detail.getKhuId().equals("")){
									 this.msg = "Vui lòng kiểm tra lại.Kho tiêu hao đang được cấu hình có xác định khu vực. Nhưng sản phẩm chi tiết không được chọn khu vực";
										db.getConnection().rollback();
										return false;
								 }
							}
							 
							query = " INSERT ERP_LENHSANXUAT_TIEUHAO_CHITIET (  TIEUHAONGUYENLIEU_FK, KHOTT_FK, SANPHAM_FK, SOLO, NGAYHETHAN, SOLUONG,LOAI,KHUVUCKHO_FK,NGAYBATDAU  "
									+ "  ,MATHUNG,MAME,NGAYNHAPKHO,MARQ,BIN_FK,HAMAM,HAMLUONG,MAPHIEU,PHIEUEO,MAPHIEUDINHTINH ,DOITUONGID,loaidoituong,nsx_fk) " +
									" values ( "+chungtuid+","+khoTieuhao_fk+","+vattu.getIdVT()+", '" + detail.getSolo() + "', '"+detail.getNgayhethan()+"', " + detail.getSoluong() + " " +
									" ,'"+vattu.getLoai()+"',"+(detail.getKhuId().length() >0?detail.getKhuId():"0")+",'"+detail.getNgaybatdau()+"','"+detail.getMathung()+"','"+detail.getMame()+"' " +
									 ",'"+detail.getNgaynhapkho()+"','"+detail.getMarq()+"',"+(detail.getVitriId().length()>0?detail.getVitriId():"NULL")+", '"+detail.getHamam()+"' "
									+ " ,'"+detail.getHamluong()+"','"+detail.getMaphieu()+"','"+detail.getPHIEUEO()+"','"+detail.getMAPHIEUDINHTINH()+"',"+doituongid+",5,"+detail.getNSX_FK()+")";
 
							if(db.updateReturnInt(query)!=1)
							{
								this.msg = "1.Không thể cập nhật ERP_LENHSANXUAT_TIEUHAO_CHITIET: " + query;
								db.getConnection().rollback();
								return false;
							}
							Kho_Lib kholib=new Kho_Lib();
							 
							kholib.setLoaichungtu("erplenhsanxuat.java 3223 :  ERP_TIEUHAONGUYENLIEU"+ chungtuid);
							
							kholib.setNgaychungtu(this.getNgaytieuhao());
							 
							kholib.setKhottId(khoTieuhao_fk);
							
							kholib.setBinId(detail.getVitriId());
						 
							
							kholib.setSolo(solo);
							String spid= vattu.getIdVT();
							kholib.setSanphamId(spid);
							
							
							kholib.setMame(detail.getMame());
							kholib.setMathung(detail.getMathung());
							kholib.setMaphieu(detail.getMaphieu());
							
							kholib.setMaphieudinhtinh(detail.getMAPHIEUDINHTINH());
							kholib.setPhieuEo(detail.getPHIEUEO());
							
							kholib.setNgayhethan(detail.getNgayhethan());
							kholib.setNgaysanxuat("");
							
							kholib.setNgaynhapkho(detail.getNgaynhapkho());
							 
							kholib.setMARQ(detail.getMarq());
							kholib.setDoituongId(doituongid);
							kholib.setLoaidoituong("5");
							 
							
							kholib.setHamluong(detail.getHamluong());
							kholib.setHamam(detail.getHamam());
							 
					    	kholib.setBooked(soluongct);
							kholib.setSoluong(0);
							kholib.setAvailable((-1)*soluongct);
						    kholib.setNppid(this.nppId);
							kholib.setNsxId(detail.getNSX_FK());
							
							String msg1= util_kho.Update_Kho_Sp_Tra(db,kholib);
						    if( msg1.length() >0)
							{
								// this.msg = msg1;
						    	this.msg = msg1;
								db.getConnection().rollback();
								return false;
								
							}
						 
							 
						}
						
					}
				}
				 
			}
			
			 
	 
			/*query=" UPDATE ERP_TIEUHAONGUYENLIEU SET TRANGTHAI=1 WHERE PK_SEQ="+chungtuid;
			 if(db.updateReturnInt(query)!=1)
				{
					this.msg = "4.Không thể cập nhật ERP_KHOTT_SANPHAM: " + query;
					db.getConnection().rollback();
					return false;
			}
			 Library lib=new Library();
			 
			 String msg1=lib.capnhatketoan_Xuat_Tieuhaolsx(this.userId, db, chungtuid, false, this.congtyId);
			 
			 if(msg1.length() >0){
			 	this.msg = msg1;
				db.getConnection().rollback();
				return false;
			 }*/
			 
			 
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			e.printStackTrace();
			this.msg = "Ecception: " + e.getMessage();
			return false;
		}

		return true;
	}

		private boolean revert_kho_tieuhao(String sochungtu) {
			// TODO Auto-generated method stub
			try{
				String query=" select b.nsx_fk ,b.doituongid   ,isnull( bin.PK_SEQ ,0)  as bin_fk  ,a.NGAYTIEUHAO ,b.KHOTT_FK as kho_fk, b.SANPHAM_FK, N'Tiêu hao lệnh sản xuất ' as loaichungtu, cast(a.PK_SEQ as varchar(10)) as machungtu, a.NGAYTIEUHAO as ngaychungtu, a.NGAYTIEUHAO, "+
						 " isnull(bin.ma,'') as vitri, b.solo, b.ngayhethan, b.ngaynhapkho, ISNULL(b.mathung,'') as mathung, ISNULL(b.mame,'') as mame,    " +
						 " isnull(b.maphieu,'') as maphieu, isnull(b.maphieudinhtinh,'')  as phieudt, isnull(b.phieueo,'') as phieueo , " +
						 " isnull(b.MArq,'')  as MARQ, b.hamam as hamam, b.hamluong as hamluong, 0 as NHAP ,	b.SOLUONG as XUAT "+
						 " from ERP_TIEUHAONGUYENLIEU a "
						 + " INNER JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ=A.LENHSANXUAT_FK   "+
						 " inner join  ERP_LENHSANXUAT_TIEUHAO_CHITIET b on a.pk_seq = b.TIEUHAONGUYENLIEU_FK "+
						 " inner join ERP_SANPHAM c on b.SANPHAM_FK = c.PK_SEQ "+ 
						 " left join ERP_BIN bin on b.bin_fk = bin.pk_seq  " +
						 " where  a.trangthai = '0' and a.pk_seq="+sochungtu;
			ResultSet rs=db.get(query);
			while(rs.next()){
				
				Kho_Lib kholib=new Kho_Lib();
				 
				kholib.setLoaichungtu("erpHuylenhsanxuat.java 1091 :  ERP_TIEUHAONGUYENLIEU"+ sochungtu);
				
				kholib.setNgaychungtu(rs.getString("NGAYTIEUHAO"));
				 
				kholib.setKhottId(rs.getString("kho_fk"));
				
				kholib.setBinId( rs.getString("bin_fk") );
			 
				
				kholib.setSolo( rs.getString("solo"));
				String spid=  rs.getString("SANPHAM_FK");
				kholib.setSanphamId(spid);
				
				
				kholib.setMame( rs.getString("mame"));
				kholib.setMathung(rs.getString("mathung"));
				kholib.setMaphieu(rs.getString("maphieu"));
				
				kholib.setMaphieudinhtinh(rs.getString("phieudt"));
				kholib.setPhieuEo(rs.getString("phieueo"));
				
				kholib.setNgayhethan(rs.getString("ngayhethan") );
				kholib.setNgaysanxuat("");
				
				
				kholib.setNgaynhapkho(rs.getString("ngaynhapkho"));
				 
				kholib.setMARQ(rs.getString("MARQ"));
				kholib.setDoituongId(rs.getString("doituongid"));
				kholib.setLoaidoituong("5");
		 
				
				kholib.setHamluong(rs.getString("hamluong"));
				kholib.setHamam(rs.getString("hamam"));
				 kholib.setNsxId(rs.getString("nsx_fk"));
				double soluongct= rs.getDouble("XUAT");
				
		    	kholib.setBooked((-1)*soluongct);
				kholib.setSoluong(0);
				kholib.setAvailable(soluongct);
				String msg1= util_kho.Update_Kho_Sp_Tra(db,kholib);
			    if( msg1.length() >0)
				{
					// this.msg = msg1;
			    	this.msg = msg1;
					db.getConnection().rollback();
					return false;
					
				}
			}
			rs.close();
			}catch(Exception er){
				this.msg=er.getMessage();
				return false;
			}
			return true;
		}
			
			 
		
}
