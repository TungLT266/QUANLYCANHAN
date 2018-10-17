package geso.traphaco.center.beans.nhaphanphoi.imp;
import geso.traphaco.center.beans.nhaphanphoi.INhaphanphoi;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Erp_Item;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class Nhaphanphoi implements INhaphanphoi
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId;
	String id;
	String ten;
	String diachi;//dia chi nhan hang
	String diachixhd;
	String IdKhoTT;
	String masothue;
	String tpId;
	String qhId;
	String sodienthoai;
	String trangthai;
	String ma;
	String msg;
	String prisec;
	ResultSet khuvucList;
	String kvId;
	String tuvanchuyen;
	String sbhId;
	String tenkyhd;
	ResultSet tp;
	ResultSet qh;
	
	String kyhieuHD;
	String SoHDTu;
	String SoHDDen;
	
	ResultSet gsbhList;
	String gsbhIds;
	
	ResultSet nvbhList;
	String nvbhIds;

	ResultSet rs_khott;
	ResultSet rs_tinhthanh;
	ResultSet rs_quanhuyen;
	
	String tennguoidaidien;
	String fax;
	String email;
	String hinhthucthanhtoan;
	String nganhang;
	String sotk;
	String ghichu;
	String ngaybatdau;
	String ngayketthuc;
	String dvkdId;
	String chietkhau;
	
	String loaiNPP;
	ResultSet tructhuocRs;
	String tructhuocId;
	String isKH;
	
	String xuattaikho;
	String tuxuatETC;
	String tutaohoadonOTC;
	String dungchungkenh;
	
	ResultSet loaicnRs;
	String loaicn;
	
	String Hanmucno;
	String Songayno;
	String CMTND;
	String thukho;
	
	private String taiKhoanCongNo;
	private List<Erp_Item> taiKhoanCongNoList;
	
	private String taiKhoanNoiBo;
	private List<Erp_Item> taiKhoanNoiBoList;
	
	dbutils db;
	
	public Nhaphanphoi(String[] param)
	{
		this.id = param[0];
		this.ten = param[1];
		this.diachi = param[2];
		this.sodienthoai = param[3];
		this.trangthai = param[4];
		this.kvId = param[11];
		this.ma = param[13];
		this.msg = "";
		this.db = new dbutils();
		this.tuvanchuyen="";
		this.dvkdId="";
		this.chietkhau="0";
		this.loaiNPP = "";
		this.tructhuocId = "";
		this.gsbhIds = "";
		this.nvbhIds = "";
		this.isKH = "";
		this.kyhieuHD="";
		this.SoHDTu= "";
		this.SoHDDen="";
		this.xuattaikho ="";
		this.tuxuatETC = "0";
		this.tutaohoadonOTC = "0";
		this.dungchungkenh = "0";
		this.loaicn="1";
		this.makho="";
		this.manx="";
		this.tenkyhd="";
		this.Hanmucno = "0";
		this.Songayno  = "0";
		this.thukho = "";
		this.tpId = "";
		
		this.taiKhoanCongNo = "";
		this.taiKhoanCongNoList = new ArrayList<Erp_Item>();
		
		this.taiKhoanNoiBo = "";
		this.taiKhoanNoiBoList = new ArrayList<Erp_Item>();
	}
	
	public Nhaphanphoi(String id, String isKH)
	{
		this.id = id;
		this.ten = "";
		this.diachi = "";
		this.masothue = "";
		this.diachixhd = "";
		this.IdKhoTT = "";
		this.tpId = "";
		this.qhId = "";
		this.sodienthoai = "";
		this.trangthai = "2";	
		this.kvId = "";
		this.msg = "";
		this.ma = "";
		this.prisec = "";
		this.tennguoidaidien = "";
		this.ngaybatdau = "";
		this.ngayketthuc = "";
		this.nganhang = "";
		this.sotk = "";
		this.email = "";
		this.fax = "";
		this.ghichu = "";
		this.hinhthucthanhtoan = "";
		this.tuvanchuyen = "";
		this.dvkdId = "";
		this.chietkhau = "0";
		this.loaiNPP = "";
		this.tructhuocId = "";
		this.gsbhIds = "";
		this.nvbhIds = "";
		this.isKH = isKH;
		this.kyhieuHD="";
		this.SoHDTu= "";
		this.SoHDDen="";
		this.xuattaikho ="";
		this.tuxuatETC = "0";
		this.tutaohoadonOTC = "0";
		this.dungchungkenh = "0";
		this.makho="";
		this.manx="";
		this.loaicn="1";
		this.tenkyhd="";
		this.Hanmucno = "0";
		this.Songayno  = "0";
		this.CMTND="";
		this.thukho = "";
		
		this.taiKhoanCongNo = "";
		this.taiKhoanCongNoList = new ArrayList<Erp_Item>();
		
		this.taiKhoanNoiBo = "";
		this.taiKhoanNoiBoList = new ArrayList<Erp_Item>();
		
		this.db = new dbutils();
		if(id.length() > 0)
			this.init();
		else
			this.createRS();
		this.db = new dbutils();
	}	
	
	
	public boolean CreateNpp(HttpServletRequest request) 
	{
		try
		{
			this.db.getConnection().setAutoCommit(false);
			

			String maNPP = this.ma;
			/*ResultSet rsMANPP = db.get("select max( cast( ma as numeric(18, 0) ) + 1 ) as maNPP from NHAPHANPHOI where isKHACHHANG = '" + this.isKH + "' ");
			if(rsMANPP.next())
			{
				maNPP = rsMANPP.getString("maNPP");
			}
			rsMANPP.close();*/
			
			String tructhuoc_fk = "";
			String loaiNPP = "";
			String nvbh_fk = "";
			String sitecode = "";
			String CMND="";
			if(this.isKH.equals("0"))
			{
				tructhuoc_fk = this.tructhuocId;
				loaiNPP = this.loaiNPP;
				nvbh_fk = "NULL";
				sitecode = maNPP;
				CMND="";
			}
			else
			{
				tructhuoc_fk = "1";
				loaiNPP = "6";
				nvbh_fk = this.nvbhIds;
				sitecode = "";
				CMND=this.CMTND;
			}
			
			String ck = this.chietkhau.trim().length() <= 0 ? "0" : this.chietkhau;
			
			String query="";
			if(this.loaicn.trim().length()<=0)
				this.loaicn = "NULL";	
				
			query= "insert into nhaphanphoi(MaKho,MaNX,kyhieuhoadon, sohoadontu\n" +
					", sohoadonden, TongThau_FK, tuvanchuyen, ten\n" +
					", ngaytao, ngaysua, nguoitao, nguoisua\n" +
					", dienthoai, diachi, khuvuc_fk, trangthai\n" +
					", ma, MaFAST, sitecode, convsitecode\n" +
					", npptn_fk, priandsecond,tinhthanh_fk,quanhuyen_fk\n" +
					",masothue,diachixhd,khosap,TENNGUOIDAIDIEN\n" +
					",FAX,EMAIL,HINHTHUCTHANHTOAN,NGANHANG,SOTAIKHOAN\n" +
					",GHICHU,ngaybatdau,ngayketthuc,ChietKhau\n" +
					", GiaVanChuyen, loaiNPP, TRUCTHUOC_FK, DDKD_FK\n" +
					", isKHACHHANG , XUATTAIKHO, tuxuatETC,TUTAOHOADON\n" +
					", DUNGCHUNGKENH, TenKyHd,cmnd,HanMucDoanhThu\n" +
					", thukho, taiKhoan_FK, taiKhoanKHNB_FK,THOIHANNO,HANMUCNO)" +
				    " values('"+this.makho+"','"+this.manx+"','"+ this.kyhieuHD +"', '"+ this.SoHDTu +"'\n" +
			   		",'"+ this.SoHDDen+"' ,'0', '0', N'" + this.ten + "'\n" +
	   				", '" + this.getDateTime() + "', '" + this.getDateTime() + "', '" + this.userId + "', '" + this.userId + "'\n" +
					", '" + this.sodienthoai + "', N'" + this.diachi + "', '" + this.kvId + " ', '" + this.trangthai + "'\n" +
					", '" + maNPP + "', '" + this.ma + "', '" + sitecode + "', '" + sitecode + "'\n" +
					", NULL,'" + this.prisec + "', "+ this.tpId + ", " + this.qhId + "\n" +
					", '" + this.masothue + "', N'" + this.diachixhd + "', '" + this.IdKhoTT + "', N'" + this.tennguoidaidien + "'\n" +
					", '" + this.fax + "','" + this.email + "', N'" + this.hinhthucthanhtoan + "', N'" + this.nganhang + "'\n" +
					",'" + this.sotk + "', N'" + this.ghichu + "', '" + this.getDateTime() + "','2200-02-02'\n" +
					", '0', '" + ck + "', '" + loaiNPP + "', '" + tructhuoc_fk + "'\n" +
					", " + nvbh_fk + ", '" + this.isKH + "' , N'" + this.xuattaikho +  "', '" + this.tuxuatETC + "'\n" +
					", '"+ this.tutaohoadonOTC +"', '" + this.dungchungkenh + "', N'"+this.tenkyhd+"','"+CMND+"',"+(this.hanmucdoanhthu.length()<=0?"NULL":this.hanmucdoanhthu)+"\n" +
					",N'"+this.thukho+"', " + (this.taiKhoanCongNo.trim().length() == 0 ? "null" : this.taiKhoanCongNo) + ", " + (this.taiKhoanNoiBo.trim().length() == 0 ? "null" : this.taiKhoanNoiBo) + ""
							+ ", "+(this.Songayno.trim().length() == 0 ? "0" : this.Songayno)+", "+(this.Hanmucno.trim().length() == 0 ?"0":this.Hanmucno)+") ";
			
			System.out.println("1. Tao nha phan phoi:" +query ) ;
			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg = "Khong the Tao Moi nhaphanphoi: " + query;
				return false; 
			}
		
			//
			query = "select scope_identity() as nppId";
			ResultSet rs = this.db.get(query);
			rs.next();
			this.id = rs.getString("nppId");
			rs.close();
			
			query = "insert nhapp_nhacc_donvikd(NPP_FK, NCC_DVKD_FK) " +
					"select '" + this.id + "', PK_SEQ from NHACUNGCAP_DVKD";
			//System.out.println("2. Tao NPP_NCC_DVKD : " +query ) ; 
			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg = "Khong the cap nhat bang nhapp_nhacc_donvikd: " + query;
				return false; 
			}
	
			
			query = "insert into nhapp_kbh(npp_fk, kbh_fk) " +
					"select distinct '" + this.id + "', pk_seq " +
					"from KENHBANHANG where trangthai = '1'  ";
			//System.out.println("3. Tao NPP: " + query ) ; 
			if(!this.db.update(query))
			{   
				this.db.getConnection().rollback();
				this.msg = "Khong the cap nhat bang nhapp_kbh: " + query;
				return false;
			}
			
			if(this.loaiNPP.equals("0")||this.loaiNPP.equals("4")){
				
				if(this.Hanmucno.length() <=0 ){
					this.Hanmucno = "";
				}
				else {
					this.Hanmucno = this.Hanmucno.trim().replaceAll(",", "");
				}
				
				if(this.Songayno.length() <=0) {
					this.Songayno = "";
				}
				else{
					this.Songayno = this.Songayno.trim().replaceAll(",", "");
				}
				
				if(this.Hanmucno.length() > 0 || this.Songayno.length() >0){
					query = " INSERT CONGNO_NPP (NPP_FK,HANMUCNO, SONGAYNO, LOAICN) VALUES ("+this.id+","+(this.Hanmucno.length() <=0 ? "0": this.Hanmucno)+","+(this.Songayno.length() <=0 ? "0": this.Songayno)+", 1 )";
					
					if(!this.db.update(query)){
						this.db.getConnection().rollback();
						this.msg = "Khong the tao CONGNO_NPP : " + query;
						return false;
					}					
				}		
				
			}
				
			//Tao nhan vien --> KHong tu tao, qua phan tao nhan vien tao
			if(this.isKH.equals("0"))
			{
			 	/*query = "insert into nhanvien(ten, dangnhap, matkhau, trangthai, ngaytao, ngaysua, nguoitao, nguoisua, phanloai, sessionid) " +
			 			"values('" + this.ten + "', '" + this.ma + "', pwdencrypt('" + this.ma + "'), '1', '" + this.getDateTime() + "', '"+ this.getDateTime() +"', '"+ this.userId+"', '" + this.userId + "', '2', '2012-01-01')";
			 	if(!this.db.update(query))
				{	
			 		this.db.getConnection().rollback();
					this.msg = "Khong the cap nhat nhapp_kbh: " + query;
					return false; 
				}
			 
				query = "insert into phamvihoatdong(nhanvien_fk, npp_fk) " +
						"select distinct nhanvien_fk, '" + this.id + "' " +
						"from phamvihoatdong pv inner join nhaphanphoi npp on npp_fk = npp.pk_seq " +
						"where khuvuc_fk = " + this.kvId + " and Nhanvien_fk not in( select Nhanvien_fk from PHAMVIHOATDONG where Npp_fk = '" + this.id + "')";
			    if(!this.db.update(query))
			    {
			    	this.db.getConnection().rollback();
				    this.setMessage("Khong the cap nhat loi :" + query);
				    return false;
			    }*/
			}
			
			query = " UPDATE NHAPHANPHOI SET TIMKIEM =UPPER(DBO.FTBODAU(TEN))+' '+UPPER(DBO.FTBODAU(ISNULL(DIACHI,'')))+' '+UPPER(DBO.FTBODAU(ISNULL(MA,''))) +' '+UPPER(DBO.FTBODAU(ISNULL(EMAIL,''))) + ' '+ISNULL(DIENTHOAI,'') WHERE PK_SEQ='"+this.id+"' "; 
			//System.out.println("4. Tao NPP: " + query ) ; 
			if(!(this.db.update(query)))
			{
				this.msg="Lỗi hệ thống ! Vui lòng liên hệt trung tâm để giải quyết !"+ query;
				this.db.update("rollback");
				return false;
			}
			
			if(this.isKH.equals("0"))
			{
				query = " insert into BANGGIAMUANPP_NPP(BANGGIAMUANPP_FK, NPP_FK) "+
						" select  TOP(1) bgM.PK_SEQ,a.PK_SEQ "+
						" from BANGGIAMUANPP bgM, NHAPHANPHOI a inner join NHAPP_KBH b  on b.NPP_FK=a.PK_SEQ "+
						"	inner join NHAPP_NHACC_DONVIKD c on c.NPP_FK=b.NPP_FK "+
						" 	inner join NHACUNGCAP_DVKD d on d.PK_SEQ=c.NCC_DVKD_FK "+
						" where d.CHECKED = 1  "+
						" and not exists "+
						" (  "+
						"	select * from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP "+ 
						"		bgnpp on bgnpp.BANGGIAMUANPP_FK=bg.PK_SEQ "+
						"	where bg.KENH_FK=b.KBH_FK and bg.DVKD_FK=d.DVKD_FK  "+
						"	and bgnpp.NPP_FK=a.PK_SEQ	and bgM.PK_SEQ=bg.PK_SEQ "+
						" ) AND A.PK_sEQ = " + this.id + "" +
						" ORDER BY BGM.TUNGAY DESC " ;
				//System.out.println("5. Tao NPP: " + query ) ; 
				if(!(this.db.update(query)))
				{
					this.msg="Lỗi hệ thống ! Vui lòng liên hệt trung tâm để giải quyết !"+ query;
					this.db.getConnection().rollback();
					return false;
				}
				
				//CHEN BANG GIA BAN LE CHUAN
				query = "insert BANGGIABANLENPP(TEN, DVKD_FK, NPP_FK, BANGGIABANLECHUAN_FK, NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA) " +
						"select N'Bảng giá bán lẻ chuẩn TraphacoDMS', DVKD_FK, '" + this.id + "', pk_seq, '" + getDateTime() + "', '" + this.userId + "', '" + this.getDateTime() + "', '" + this.userId + "' from BANGGIABANLECHUAN";
				//System.out.println("5. Tao NPP: " + query ) ; 
				if(!(this.db.update(query)))
				{
					this.msg = "Lỗi hệ thống ! Vui lòng liên hệt trung tâm để giải quyết !"+ query;
					this.db.getConnection().rollback();
					return false;
				}
				
				query = "insert BGBANLENPP_SANPHAM(BGBANLENPP_FK, SANPHAM_FK, GIABANLENPP, GIABANLECHUAN) " +
						"select ident_current('BANGGIABANLENPP'), sanpham_fk, giabanlechuan, giabanlechuan " +
						"from BANGGIABLC_SANPHAM";
				if(!(this.db.update(query)))
				{
					this.msg = "Lỗi hệ thống ! Vui lòng liên hệt trung tâm để giải quyết !"+ query;
					this.db.getConnection().rollback();
					return false;
				}
				
				// * Chen nhung san pham chua co trong kho.
				// * Theo don vi kinh doanh va kenh ban hang cua npp			 
				query = "insert into nhapp_kho(npp_fk,kbh_fk,KHO_FK,SANPHAM_FK,SOLUONG,BOOKED,AVAILABLE)   "+
						"select  npp.PK_SEQ, kenh.PK_SEQ as kbhId ,kho.PK_SEQ as khoId,sp.PK_SEQ as spId,0 as SoLuong,0 as Booked,0 as avail  from KHO kho,SANPHAM sp,KENHBANHANG kenh ,NHAPHANPHOI npp "+  
						"where not exists "+  
						"	( 	 "+
						"		select * from  NHAPP_KHO a 	 "+
						"		where a.KHO_FK=kho.PK_SEQ and a.KBH_FK=kenh.PK_SEQ 	 "+
						"		and a.SANPHAM_FK=sp.PK_SEQ and a.npp_fk =npp.pk_Seq   "+
						"	) and kenh.PK_SEQ in (select kbh_fk from NHAPP_KBH where NPP_FK="+this.id+")   and sp.DVKD_FK  in (select b.DVKD_FK from NHAPP_NHACC_DONVIKD a inner join NHACUNGCAP_DVKD b on a.NCC_DVKD_FK=b.PK_SEQ and a.NPP_FK="+this.id+" ) "+ 
						"and npp.pk_Seq="+this.id+" ";
				//System.out.println("6. Tao NPP: " + query ) ; 
				if(!db.update(query))
				{
					this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query;
					this.db.getConnection().rollback();
					return false;
				}
				
				query = "	insert into nhapp_kho_ChiTiet(npp_fk,kbh_fk,KHO_FK,SANPHAM_FK,SOLUONG,BOOKED,AVAILABLE,SOLO,NgayHetHan)   "+
						"	select  npp.PK_SEQ, kenh.PK_SEQ as kbhId ,kho.PK_SEQ as khoId,sp.PK_SEQ as spId,0 as SoLuong,0 as Booked,0 as avail,'NA','2030-12-31'  from KHO kho,SANPHAM sp,KENHBANHANG kenh ,NHAPHANPHOI npp "+  
						"	where not exists "+  
						"	( " +
						"		select * from  nhapp_kho_ChiTiet a 	 "+
						"		where a.KHO_FK=kho.PK_SEQ and a.KBH_FK=kenh.PK_SEQ 	 "+
						"		and a.SANPHAM_FK=sp.PK_SEQ and a.npp_fk =npp.pk_Seq  and a.SOLO='NA'  "+
						"	) and kenh.PK_SEQ in (select kbh_fk from NHAPP_KBH where NPP_FK="+this.id+")   and sp.DVKD_FK  in (select b.DVKD_FK from NHAPP_NHACC_DONVIKD a inner join NHACUNGCAP_DVKD b on a.NCC_DVKD_FK=b.PK_SEQ and a.NPP_FK="+this.id+" ) "+ 
						"	and npp.pk_Seq="+this.id+" ";
				//System.out.println("7. Tao NPP: " + query ) ; 
				if(!db.update(query))
				{
					this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query;
					this.db.getConnection().rollback();
					return false;
				}
			}
			
			
			/*********************Tự động chèn đại diện,tạo tuyến đối với Loại nhà phân phối =2 (Quầy bán buôn)**************************/
						
			if(this.loaiNPP.equals("2") || this.loaiNPP.equals("3") )
			{
				query=
					"	insert into DAIDIENKINHDOANH(TEN,DIACHI,NPP_FK,TRANGTHAI,NGUOITAO,NGUOISUA,NGAYTAO,NGAYSUA,SMARTID,MANHANVIEN,tructhuoc_fk)   "+
					"	SELECT 'TDV_'+TEN AS TEN,'NA' AS DIACHI,PK_SEQ AS NPPID,1 AS TRA,'"+this.userId+"' AS NTAO,'"+this.userId+"' NSUA,'"+this.getDateTime()+"','"+this.getDateTime()+"',PK_SEQ AS SMARTID,PK_SEQ AS MANV,  "+
					"		PK_SEQ AS TRUCTHUC   "+
					"	FROM NHAPHANPHOI   "+
					"	WHERE PK_SEQ ='" + this.id + "'  ";
				//System.out.println("8. Tao NPP: " + query ) ; 
				if(!db.update(query))
				{
					this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query;
					this.db.getConnection().rollback();
					return false;
				}	
				query=
				" insert into TUYENBANHANG(DIENGIAI,NGAYLAMVIEC,NGAYTAO,NGAYSUA,NGUOITAO,NGUOISUA,DDKD_FK,NPP_FK,NGAYID)  "+
				" select 'T2_'+a.TEN,'Thu hai','"+this.getDateTime()+"','"+this.getDateTime()+"','"+this.userId+"','"+this.userId+"',a.PK_SEQ,a.NPP_FK,2   "+
				" from DAIDIENKINHDOANH a inner join NHAPHANPHOI b on b.PK_SEQ=a.NPP_FK   "+
				" where b.pk_Seq = '" + this.id + "'  ";
				//System.out.println("9. Tao NPP: " + query ) ; 
				if(!db.update(query))
				{
					this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query;
					this.db.getConnection().rollback();
					return false;
				}	
				 
				 /*********************Tự động chèn đại diện,tạo tuyến đối với Loại nhà phân phối =2 (Quầy bán buôn)**************************/	
			}
			
				query=
				"	insert into phamvihoatdong(nhanvien_fk, npp_fk)  "+ 
				"	select PK_SEQ,'"+this.id+"' "+
				"	from NHANVIEN  "+
				"	where PHANLOAI=2 and LOAI in (1,2,5)  ";
				System.out.println("5. Tao NPP: " + query ) ; 
				
				if(!db.update(query))
				{
					this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query;
					this.db.getConnection().rollback();
					return false;
				}	
			
				query=
						"	insert PHAMVIHOATDONG (Nhanvien_fk,Npp_fk) "+
								"	select nv.PK_SEQ,npp.PK_SEQ "+
								"	from NHAPHANPHOI npp,NHANVIEN nv "+
								"	where nv.IsAdmin=1 and not exists "+
								"	( "+
								"		select 1 from PHAMVIHOATDONG hd where hd.Nhanvien_fk=nv.PK_SEQ "+
								"		and Npp_fk=npp.PK_SEQ "+
								"	) ";

				if(!this.db.update(query))
				{
					this.db.getConnection().rollback();
					this.msg = "Khong the tao : " + query;
					return false;
				}
				query=	" insert NHANVIEN_SANPHAM (Nhanvien_fk,Sanpham_fk) "+
						" select nv.PK_SEQ,npp.PK_SEQ "+
						" from SANPHAM npp,NHANVIEN nv "+
						" where nv.IsAdmin=1 and not exists "+
						" ( "+
						"	select 1 from NHANVIEN_SANPHAM hd where hd.Nhanvien_fk=nv.PK_SEQ "+
						"	and Sanpham_fk=npp.PK_SEQ "+
						" )";
				if(!this.db.update(query))
				{
					this.db.getConnection().rollback();
					this.msg = "Khong the tao : " + query;
					return false;
				}
				query= "insert NHANVIEN_KENH (Nhanvien_fk,Kenh_fk) "+
						"select nv.PK_SEQ,npp.PK_SEQ "+
						"from KENHBANHANG npp,NHANVIEN nv "+
						"where nv.IsAdmin=1 and not exists "+
						"( "+
						"	select 1 from NHANVIEN_KENH hd where hd.Nhanvien_fk=nv.PK_SEQ "+
						"	and Kenh_fk=npp.PK_SEQ "+
						") ";

				if(!this.db.update(query))
				{
					this.db.getConnection().rollback();
					this.msg = "Khong the tao : " + query;
					return false;
				}
				query="insert nhanvien_kho (Nhanvien_fk,kho_fk) "+
					  "	select nv.PK_SEQ,npp.PK_SEQ "+
					  "	from KHO npp,NHANVIEN nv "+
					  "	where nv.IsAdmin=1 and not exists "+
					  "	( "+
					  "		select 1 from nhanvien_kho hd where hd.Nhanvien_fk=nv.PK_SEQ "+
					  "		and kho_fk=npp.PK_SEQ "+
					  "	)";
				if(!this.db.update(query))
				{
					this.db.getConnection().rollback();
					this.msg = "Khong the tao : " + query;
					return false;
				}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}
		catch(Exception e)
		{
			this.msg = "Vui long kiem tra lai du lieu Ban da nhap"+ e.toString();		
			this.db.update("rollback");
			e.printStackTrace();
			return false;						
		}			
	
		return true;
	}
	
	public boolean UpdateNpp(HttpServletRequest request) 
	{
		try
		{
			this.db.getConnection().setAutoCommit(false);
			
			String tructhuoc_fk = "";
			String loaiNPP = "";
			String nvbh_fk = "";
			String CMND="";
			System.out.println("---IS KH: " + this.isKH);
			if(this.isKH.equals("0"))
			{
				tructhuoc_fk = this.tructhuocId;
				loaiNPP = this.loaiNPP;
				nvbh_fk = "NULL";
				CMND="";
			}
			else
			{
				tructhuoc_fk = "1";
				loaiNPP = "6";
				nvbh_fk = this.nvbhIds;
				CMND=this.CMTND;
				System.out.println("CMNd ___1 ____" + CMND);
			}
			
			if(this.loaicn.trim().length()<=0)
				this.loaicn="NULL";
			
			String query = "";
			String ck = this.chietkhau.trim().length() <= 0 ? "0" : this.chietkhau;
			query = "update nhaphanphoi set MaKho='"+this.makho+"',MaNx='"+this.manx+"',kyhieuhoadon ='"+ this.kyhieuHD +"', sohoadontu='"+this.SoHDTu +"', sohoadonden='"+this.SoHDDen+"', khosap='" + this.IdKhoTT + "', masothue='"+this.masothue+"', diachixhd=N'"+this.getDiaChiXuatHoaDon()+"', " +
					"ten=N'" + this.ten + "', dienthoai='" + this.sodienthoai + "', diachi=N'"+ this.diachi + "', khuvuc_fk = '" + this.kvId + "',  " +
					"ngaysua = '" + this.getDateTime() + "',  nguoisua = '" + this.userId + "', trangthai = '" + this.trangthai + "', " +
					"maFAST='"+ this.ma + "',  priandsecond ='" + this.prisec + "', tinhthanh_fk='" + this.tpId + "', " +
					"quanhuyen_fk='" + this.qhId + "', tennguoidaidien=N'"+this.tennguoidaidien+"',fax ='"+this.fax+"',email='"+this.email+"', " +
					" hinhthucthanhtoan=N'"+this.hinhthucthanhtoan+"',nganhang=N'"+this.nganhang+"',sotaikhoan='"+this.sotk+"',ghichu=N'"+this.ghichu+"',ngaybatdau='"+this.ngaybatdau+"', " +
					" ngayketthuc='"+this.ngayketthuc+"',ChietKhau='" + ck + "', GiaVanChuyen = '0', loaiNPP = '" + loaiNPP + "', TRUCTHUOC_FK = '" + tructhuoc_fk + "', DDKD_FK = " + nvbh_fk + ", XUATTAIKHO = N'" + this.xuattaikho + "', tuxuatETC = '" + this.tuxuatETC + "', TUTAOHOADON = '"+ this.tutaohoadonOTC +"', TenKyHd= N'"+this.tenkyhd+"',cmnd='"+this.CMTND+"',HanMucDoanhThu="+(this.hanmucdoanhthu.length()<=0?"NULL":this.hanmucdoanhthu)+", thukho = N'"+this.thukho+"' \n" +
					", taiKhoan_FK = " + (this.taiKhoanCongNo.trim().length() == 0 ? "null" : this.taiKhoanCongNo) + ", taiKhoanKHNB_FK = '" + (this.taiKhoanNoiBo.trim().length() == 0 ? "null" : this.taiKhoanNoiBo) + "'\n" +
					", THOIHANNO = " + (this.Songayno.trim().length() == 0 ? "0" : this.Songayno) + 
					", HANMUCNO = " + (this.Hanmucno.trim().length() == 0 ? "0" : this.Hanmucno) +
					" where pk_seq = '" + this.id + "'\n";
			
			System.out.println("Query : "+ query);
			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg = "Khong the cap nhat nhapp_kbh";
				return false; 
			}		

				
			query = "delete from nhapp_kbh where npp_fk = '" + this.id + "'";
			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg = "Khong the cap nhat nhapp_kbh";
				return false; 
			}		
		

			
			query = " delete from nhapp_nhacc_donvikd where npp_fk = '"+this.id+"' ";
			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg = "Khong the cap nhat nhapp_kbh";
				return false; 
			}		
				
			query = "insert nhapp_nhacc_donvikd(NPP_FK, NCC_DVKD_FK) " +
					"select '" + this.id + "', PK_SEQ from NHACUNGCAP_DVKD";
			System.out.println("2. Tao NPP_NCC_DVKD : " +query ) ; 
			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg = "Khong the cap nhat bang nhapp_nhacc_donvikd: " + query;
				return false; 
			}
		
			
			query = "insert into nhapp_kbh(npp_fk, kbh_fk) " +
					"select distinct'" + this.id + "', pk_seq " +
					"from KENHBANHANG where trangthai = '1'  ";
			if(!this.db.update(query))
			{   
				this.db.getConnection().rollback();
				this.msg = "Khong the cap nhat bang nhapp_kbh: " + query;
				return false;
			}
				
			query = "UPDATE NHAPHANPHOI SET TIMKIEM = UPPER(DBO.FTBODAU(TEN))+' '+UPPER(DBO.FTBODAU(ISNULL(DIACHI,'')))+' '+UPPER(DBO.FTBODAU(ISNULL(MA,''))) +' '+UPPER(DBO.FTBODAU(ISNULL(EMAIL,''))) + ' '+ISNULL(DIENTHOAI,'') WHERE PK_SEQ='"+this.id+"' "; 
			if(!(this.db.update(query)))
			{
				this.msg="Lỗi hệ thống ! Vui lòng liên hệt trung tâm để giải quyết !"+ query;
				this.db.getConnection().rollback();
				return false;
			}
			
			if(this.loaiNPP.equals("0")){				
				query = "";
			}
			if(this.isKH.equals("0"))
			{
				query = "	insert into nhapp_kho(npp_fk,kbh_fk,KHO_FK,SANPHAM_FK,SOLUONG,BOOKED,AVAILABLE)   "+
						"	select  npp.PK_SEQ, kenh.PK_SEQ as kbhId ,kho.PK_SEQ as khoId,sp.PK_SEQ as spId,0 as SoLuong,0 as Booked,0 as avail  from KHO kho,SANPHAM sp,KENHBANHANG kenh ,NHAPHANPHOI npp "+  
						"	where not exists "+  
						"	( 	 "+
						"		select * from  NHAPP_KHO a 	 "+
						"		where a.KHO_FK=kho.PK_SEQ and a.KBH_FK=kenh.PK_SEQ 	 "+
						"		and a.SANPHAM_FK=sp.PK_SEQ and a.npp_fk =npp.pk_Seq   "+
						"	) and kenh.PK_SEQ in (select kbh_fk from NHAPP_KBH where NPP_FK="+this.id+")   and sp.DVKD_FK  in (select b.DVKD_FK from NHAPP_NHACC_DONVIKD a inner join NHACUNGCAP_DVKD b on a.NCC_DVKD_FK=b.PK_SEQ and a.NPP_FK="+this.id+" ) "+ 
						"	and npp.pk_Seq="+this.id+" ";
				if(!db.update(query))
				{
					this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query;
					this.db.getConnection().rollback();
					return false;
				}
					
				query = "	insert into nhapp_kho_ChiTiet(npp_fk,kbh_fk,KHO_FK,SANPHAM_FK,SOLUONG,BOOKED,AVAILABLE,SOLO,NgayHetHan)   "+
						"	select  npp.PK_SEQ, kenh.PK_SEQ as kbhId ,kho.PK_SEQ as khoId,sp.PK_SEQ as spId,0 as SoLuong,0 as Booked,0 as avail,'NA','2030-12-31'  from KHO kho,SANPHAM sp,KENHBANHANG kenh ,NHAPHANPHOI npp "+  
						"	where not exists "+  
						"	( 	 "+
						"		select 1 from  nhapp_kho_ChiTiet a 	 "+
						"		where a.KHO_FK=kho.PK_SEQ and a.KBH_FK=kenh.PK_SEQ 	 "+
						"		and a.SANPHAM_FK=sp.PK_SEQ and a.npp_fk =npp.pk_Seq and a.SoLo='NA'  "+
						"	) and kenh.PK_SEQ in (select kbh_fk from NHAPP_KBH where NPP_FK="+this.id+")   and sp.DVKD_FK  in (select b.DVKD_FK from NHAPP_NHACC_DONVIKD a inner join NHACUNGCAP_DVKD b on a.NCC_DVKD_FK=b.PK_SEQ and a.NPP_FK="+this.id+" ) "+ 
						"	and npp.pk_Seq="+this.id+" ";
				if(!db.update(query))
				{
					this.msg = "Có lỗi phát sinh trong quá trình cập nhật "+query;
					this.db.getConnection().rollback();
					return false;
				}
				 
				query = " insert into BANGGIAMUANPP_NPP(BANGGIAMUANPP_FK,NPP_FK) "+
						" select  TOP(1) bgM.PK_SEQ,a.PK_SEQ "+
						" from BANGGIAMUANPP bgM , NHAPHANPHOI a inner join NHAPP_KBH b  on b.NPP_FK=a.PK_SEQ "+
						"	inner join NHAPP_NHACC_DONVIKD c on c.NPP_FK=b.NPP_FK "+
						" 	inner join NHACUNGCAP_DVKD d on d.PK_SEQ=c.NCC_DVKD_FK "+
						"  where d.CHECKED=1  "+
						" and not exists "+
						" (  "+
						"	select * from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP "+ 
						"		bgnpp on bgnpp.BANGGIAMUANPP_FK=bg.PK_SEQ "+
						"	where bg.KENH_FK=b.KBH_FK and bg.DVKD_FK=d.DVKD_FK  "+
						"	and bgnpp.NPP_FK=a.PK_SEQ	and bgM.PK_SEQ=bg.PK_SEQ "+
						" ) AND A.PK_sEQ = " + this.id + " and bgM.pk_seq not in ( select banggiamuaNPP_FK from BANGGIAMUANPP_NPP where NPP_FK = '" + this.id + "' ) " +
						" ORDER BY BGM.TUNGAY DESC " ;
				if(!(this.db.update(query)))
				{
					this.msg="2.Lỗi hệ thống ! Vui lòng liên hệt trung tâm để giải quyết !"+ query;
					this.db.getConnection().rollback();
					return false;
				}
			}
			
			if(this.loaiNPP.equals("0")||this.loaiNPP.equals("4")){
				
				if(this.Hanmucno.length() <=0 ) this.Hanmucno = "";
				
				if(this.Songayno.length() <=0) this.Songayno = "";
				
				if(this.Hanmucno.length() > 0 || this.Songayno.length() >0){
					query = " SELECT COUNT (PK_SEQ) sodong FROM CONGNO_NPP WHERE NPP_FK ='"+this.id+"'";
					
					System.out.println(query);
					int count = 0;
					ResultSet dem = db.get(query);
					if(dem!=null){
						while(dem.next()){
							count = dem.getInt("sodong");
						}
						dem.close();
					}
					
					if(count<=0){					
						query = " INSERT CONGNO_NPP (NPP_FK,HANMUCNO, SONGAYNO, LOAICN) VALUES ("+this.id+","+(this.Hanmucno.length() <=0 ? "0": this.Hanmucno.trim().replaceAll(",", ""))+","+(this.Songayno.length() <=0 ? "0": this.Songayno.trim().replaceAll(",", ""))+", 1 )";
						System.out.println(query);
						if(!this.db.update(query)){
							this.db.getConnection().rollback();
							this.msg = "Khong the tao CONGNO_NPP : " + query;
							return false;
						}
					}
					else {
						query = "UPDATE CONGNO_NPP SET HANMUCNO ="+(this.Hanmucno.length() <=0 ? "0": this.Hanmucno.trim().replaceAll(",", ""))+", SONGAYNO ="+(this.Songayno.length() <=0 ? "0": this.Songayno.trim().replaceAll(",", ""))+" WHERE NPP_FK ='"+this.id+"'";
						System.out.println(query);
						if(!this.db.update(query)){
							this.db.getConnection().rollback();
							this.msg = "Khong the cap nhat : " + query;
							return false;
						}
					}
				}		
			}
			else{
				
				query = "DELETE CONGNO_NPP WHERE NPP_FK ='"+this.id+"'";
				
				System.out.println(query);
				if(!this.db.update(query)){
					this.db.getConnection().rollback();
					this.msg = "Khong the tao : " + query;
					return false;
				}
			}
			
					
			query=	" insert NHANVIEN_SANPHAM (Nhanvien_fk,Sanpham_fk) "+
					" select nv.PK_SEQ,npp.PK_SEQ "+
					" from SANPHAM npp,NHANVIEN nv "+
					" where nv.IsAdmin=1 and not exists "+
					" ( "+
					"	select 1 from NHANVIEN_SANPHAM hd where hd.Nhanvien_fk=nv.PK_SEQ "+
					"	and Sanpham_fk=npp.PK_SEQ "+
					" )";
			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg = "Khong the tao : " + query;
				return false;
			}
			query= "insert NHANVIEN_KENH (Nhanvien_fk,Kenh_fk) "+
					"select nv.PK_SEQ,npp.PK_SEQ "+
					"from KENHBANHANG npp,NHANVIEN nv "+
					"where nv.IsAdmin=1 and not exists "+
					"( "+
					"	select 1 from NHANVIEN_KENH hd where hd.Nhanvien_fk=nv.PK_SEQ "+
					"	and Kenh_fk=npp.PK_SEQ "+
					") ";

			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg = "Khong the tao : " + query;
				return false;
			}
			query="insert nhanvien_kho (Nhanvien_fk,kho_fk) "+
				  "	select nv.PK_SEQ,npp.PK_SEQ "+
				  "	from KHO npp,NHANVIEN nv "+
				  "	where nv.IsAdmin=1 and not exists "+
				  "	( "+
				  "		select 1 from nhanvien_kho hd where hd.Nhanvien_fk=nv.PK_SEQ "+
				  "		and kho_fk=npp.PK_SEQ "+
				  "	)";
			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg = "Khong the tao : " + query;
				return false;
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} 
		catch(Exception e)
		{
			this.msg = "Vui long kiem tra lai du lieu Ban da nhap"+ e.toString();		
			this.db.update("rollback");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public ResultSet createTpRS()
	{  	
		ResultSet tpRS = this.db.get("select ten as tpTen, pk_seq as tpId from tinhthanh order by ten");
		return tpRS;
	}
	
	public ResultSet createQhRS()
	{  	
		ResultSet qhRS = null;
		
		if (this.tpId.length() > 0){
			qhRS = this.db.get("select ten as qhTen, pk_seq as qhId from quanhuyen where tinhthanh_fk='"+ this.tpId +"' order by ten");
		}
		else
		{
			qhRS = this.db.get("select ten as qhTen, pk_seq as qhId from quanhuyen order by ten");
		}
		
		return qhRS;
	}

	public ResultSet createKvRS()
	{  	
		ResultSet kvRS = this.db.get("select ten as kvTen, pk_seq as kvId from khuvuc order by ten");
		return kvRS;
	}
	
	public void createRS()
	{
		this.createTaiKhoanCongNoList();
		this.createTaiKhoanNoiBoList();
		
		this.tp = this.createTpRS();		
		this.qh = createQhRS();
		this.khuvucList = this.createKvRS();
		this.gsbhList = db.get("select pk_seq, ten from GIAMSATBANHANG where trangthai = '1'");
		this.rs_khott = db.get("select pk_seq, ten from ERP_KHOTT where trangthai = '1'");
		this.nvbhList = db.get("select pk_seq as ID, TEN from DAIDIENKINHDOANH ");
		this.loaicnRs = db.get("Select pk_seq, DienGiai from CongNo");
		
		System.out.println("Select pk_seq, DienGiai from CongNo");
		
	    String query = " ";
	    
	    query = "select 1 as ID, N'TraphacoDMS' as TEN, 1 as STT ";
	    if(this.loaiNPP.equals("0")) //Chi nhánh cấp 1  -> TRUC THUOC TraphacoDMS hoac CHI NHANH CAP 1 KHAC
	    {
	    	//query += " union select pk_seq as ID, TEN, 2 as STT from NHAPHANPHOI where trangthai = '1' and loaiNPP in ( 0 )  ";
	    }
	    else if(this.loaiNPP.equals("1")) //Chi nhánh cấp 2  -> TRUC THUOC TraphacoDMS hoac CHI NHANH CAP 1 KHAC
	    {
	    	query += " union select pk_seq as ID, TEN, 2 as STT from NHAPHANPHOI where trangthai = '1' and loaiNPP in ( 0 )  ";
	    }
	    else if(this.loaiNPP.equals("2")) //Quầy bán buôn  -> TRUC THUOC TraphacoDMS hoac CHI NHANH CAP 1, 2 KHAC
	    {
	    	query += " union select pk_seq as ID, TEN, 2 as STT from NHAPHANPHOI where trangthai = '1' and loaiNPP in ( 0, 1 )  ";
	    }
	    else if(this.loaiNPP.equals("3")) //Quầy TraphacoDMS  -> TRUC THUOC DOI TAC
	    {
	    	query = " select pk_seq as ID, TEN, 2 as STT from NHAPHANPHOI where trangthai = '1' and loaiNPP in ( 4 )  ";
	    }
	    else if(this.loaiNPP.equals("4")) //Đối tác  -> TRUC THUOC TraphacoDMS va CHI NHANH CAP 1
	    {
	    	query += " union select pk_seq as ID, TEN, 2 as STT from NHAPHANPHOI where trangthai = '1' and loaiNPP in ( 0 )  ";
	    }
	    else if(this.loaiNPP.equals("5")) //Chi nhánh đối tác  -> TRUC THUOC DOI TAC
	    {
	    	query = " select pk_seq as ID, TEN, 2 as STT from NHAPHANPHOI where trangthai = '1' and loaiNPP in ( 4 )  ";
	    }
	    
	    query += " order by STT asc, TEN asc ";
	    
	    //System.out.println("--LAY TRUC THUOC: " + query );
	    this.tructhuocRs = db.get(query);
	} 
	
	private void createTaiKhoanCongNoList() {
		String query = "select soHieuTaiKhoan as pk_seq, soHieuTaiKhoan + ' - ' + tentaiKhoan as ten from erp_taiKhoanKT where trangThai = 1 and npp_fk = 1 and soHieuTaiKhoan like '13%'";
		Erp_Item.getListFromQuery(db, query, this.taiKhoanCongNoList);
	}

	private void createTaiKhoanNoiBoList() {
		String query = "select soHieuTaiKhoan as pk_seq, soHieuTaiKhoan + ' - ' + tentaiKhoan as ten from erp_taiKhoanKT where trangThai = 1 and npp_fk = 1 and soHieuTaiKhoan like '1361%'";
		Erp_Item.getListFromQuery(db, query, this.taiKhoanNoiBoList);
	}
	
	private void init() 
	{
		String query =  
						"	select isnull(a.thoihanno,0)thoihanno , isnull(a.hanmucno,0)hanmucno,   a.HanMucDoanhThu, isnull(a.xuattaikho,'') as xuattaikho , a.tongthau_fk, isnull(a.tuvanchuyen,'') as tuvanchuyen , a.khosap as khott,isnull(a.TenKyHd,'') TenKyHd ," +
						"   isnull(a.kyhieuhoadon,'') as kyhieuhoadon ,isnull(a.sohoadontu,'') as sohoadontu, isnull(a.sohoadonden,'') as sohoadonden, "+
						"		a.masothue,a.diachixhd,a.pk_seq as id, a.ten as nppten, a.dienthoai, a.diachi, "+ 
						"		a.trangthai,  a.ma, a.pass, a.ngaytao, b.nguoitao, a.ngaysua, c.nguoisua, "+
						"		isnull(d.ten,'') as kvten, d.pk_seq as kvid, a.priandsecond as  prisec, isNull(convert(nvarchar, a.tinhthanh_fk), '') as tpid, "+
						"		a.quanhuyen_fk as qhid, a.tennguoidaidien as tennguoidaidien, isnull(a.fax,'') as fax, isnull(a.email,'') as email,  "+
						"		isnull(a.hinhthucthanhtoan,'') as hinhthucthanhtoan, isnull(a.nganhang,'') as nganhang, isnull(a.sotaikhoan,'') as sotaikhoan, "+  
						"		a.ghichu as ghichu, isnull(a.ngaybatdau,'') as ngaybatdau, isnull(a.ngayketthuc,'') as ngayketthuc,  "+
						"		isnull(chietkhau, 0) as chietkhau, isnull(giavanchuyen, 0) as giavanchuyen ,isnull(sitecode_conv.tennpptn,'') as tennpptn,a.npptn_fk, a.loaiNPP, a.TRUCTHUOC_FK, a.DDKD_FK, a.isKHACHHANG, a.MaFAST, tuxuatETC, isnull(tutaohoadon, 0) as tutaohoadon, DUNGCHUNGKENH "+
						" ,a.MaKho,a.MaNX,a.cmnd, isnull(a.thukho,'') thukho \n" +
						", isNull(convert(nvarchar, a.TaiKhoan_FK), '') as taiKhoanCongNo, isNull(convert(nvarchar, a.TaikhoanKhNB_fk), '') taiKhoanNoiBo\n"+
						"	from  nhaphanphoi a inner join nhanvien b on b.pk_seq=a.nguoitao \n"+ 
						"	     inner join  nhanvien c on c.pk_seq=a.nguoisua   \n"+
						"	     left join sitecode_conv on sitecode_conv.convsitecode=a.sitecode \n"+
						"		 left join  khuvuc d  on a.khuvuc_fk=d.pk_seq \n"+  	
						"	 where  a.pk_seq = '"+this.id+"' \n";
		
		ResultSet rs =  this.db.get(query);
		System.out.println("1/Load lên: \n" + query);

        try{
            while(rs.next())
            {            	
            	this.taiKhoanCongNo = rs.getString("taiKhoanCongNo");
            	this.taiKhoanNoiBo = rs.getString("taiKhoanNoiBo");
	            this.masothue=rs.getString("masothue");
	            this.diachixhd=rs.getString("diachixhd");
	        	this.id = rs.getString("id");
	        	this.ten = rs.getString("nppTen");
	        	this.sodienthoai = rs.getString("dienthoai")==null?"": rs.getString("dienthoai");
	        	this.diachi = rs.getString("diachi")==null?"":rs.getString("diachi");
	        	this.tpId = rs.getString("tpId");
	        	this.qhId = rs.getString("qhId");
	        	this.trangthai = rs.getString("trangthai");
	        	this.ma 	   = rs.getString("MaFAST");
	        	this.tuvanchuyen=rs.getString("tuvanchuyen");      	
	        	this.kvId = rs.getString("kvId");
	        	this.tennguoidaidien = rs.getString("tennguoidaidien")==null?"":rs.getString("tennguoidaidien");
	        	this.fax = rs.getString("fax")==null?"":rs.getString("fax");
	        	this.email = rs.getString("email")==null?"":rs.getString("email");
	        	this.hinhthucthanhtoan = rs.getString("hinhthucthanhtoan");
	        	this.nganhang = rs.getString("nganhang");
	        	this.sotk = rs.getString("sotaikhoan")==null?"":rs.getString("sotaikhoan");
	        	this.ngaybatdau = rs.getString("ngaybatdau");
	        	this.ngayketthuc = rs.getString("ngayketthuc");
	        	this.ghichu = rs.getString("ghichu");
	        	String st = rs.getString("prisec");
	        	this.chietkhau = rs.getString("chietkhau");
	        	this.Hanmucno = rs.getString("hanmucno");
	        	this.Songayno = rs.getString("thoihanno");
	        	this.kyhieuHD =  rs.getString("kyhieuhoadon");
	        	this.tenkyhd = rs.getString("TenKyHd");
	        	this.SoHDTu=  rs.getString("sohoadontu");
	        	this.SoHDDen =  rs.getString("sohoadonden");
	        	
	        	this.loaiNPP = rs.getString("loaiNPP");
	        	if(rs.getString("TRUCTHUOC_FK") != null)
	        		this.tructhuocId = rs.getString("TRUCTHUOC_FK");
	        	if(rs.getString("DDKD_FK") != null)
	        		this.nvbhIds = rs.getString("DDKD_FK");
	        	
	        	this.isKH = rs.getString("isKHACHHANG");
	        	this.IdKhoTT=rs.getString("khott");
	        	if(st==null)
	        	   st = "0";
	        	this.prisec=st;
	        	this.xuattaikho = rs.getString("xuattaikho");
	        	this.tuxuatETC = rs.getString("tuxuatETC");
	        	this.tutaohoadonOTC= rs.getString("tutaohoadon");
	        	this.dungchungkenh= rs.getString("dungchungkenh");
	        	this.makho = rs.getString("makho") ==null?"": rs.getString("makho");
	        	this.manx = rs.getString("manx") ==null?"": rs.getString("manx");
	        	this.CMTND=rs.getString("cmnd") ==null?"": rs.getString("cmnd");;
	        	DecimalFormat df2 = new DecimalFormat( "#,###,###" );
	        	
	        	this.hanmucdoanhthu = rs.getString("hanmucdoanhthu")==null?"":df2.format(rs.getDouble("hanmucdoanhthu"));
	        	this.thukho = rs.getString("thukho");
	        	
            }
            rs.close();      
            
            //INIT GSBH
            query = "select gsbh_fk from nhapp_giamsatbh where NPP_FK = '" + this.id + "'";
            rs = db.get(query);
            if(rs.next())
            	this.gsbhIds = rs.getString("gsbh_fk");
            rs.close();
            
            //HAN MUC CONG NO
            
//            if(this.loaiNPP.equals("4")||this.loaiNPP.equals("0")){
//            	query = " SELECT NPP_FK, LOAICN, HANMUCNO, SONGAYNO FROM CONGNO_NPP WHERE NPP_FK ='"+this.id+"'";
//            	System.out.println(query);
//            	rs = db.get(query);
//            	if(rs.next()){
//            		this.loaicn = rs.getString("LOAICN");
//            		this.Hanmucno = rs.getString("HANMUCNO");
//            		this.Songayno = rs.getString("SONGAYNO");
//            	}
//            	rs.close();	
//            }
        }
        catch(Exception e){
        	e.printStackTrace();
    	}
       	
        createRS(); 
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	
	public void DBclose()
	{
		try
		{		
			if(khuvucList!=null){
				khuvucList.close();
			}
			
			if(tp!=null){
				tp.close();
			}
			
			if(qh!=null){
				qh.close();
			}
			
			if(rs_khott!=null){
				rs_khott.close();
			}
			if(rs_tinhthanh!=null){
				rs_tinhthanh.close();
			}
			
			if(rs_quanhuyen!=null){
				rs_quanhuyen.close();
			}
			
		if(!(this.db == null)){
			
			this.db.shutDown();
		}
		}catch(Exception er){
			er.printStackTrace();
		}
	}

	
	public void setDiaChiXuatHoaDon(String _diachixhd) {
		
		this.diachixhd=_diachixhd;
	}

	
	public String getDiaChiXuatHoaDon() {
		
		return this.diachixhd;
	}

	
	public void setMaSoThue(String _masothue) {
		
		this.masothue=_masothue;
	}

	
	public String getmaSoThue() {
		
		return this.masothue;
	}

	
	public void setKhoTT(String khott) {
		
		this.IdKhoTT=khott;
	}

	
	public String getKhoTT() {
		
		return this.IdKhoTT;
	}

	public String getTuvanchuyen() {

		return this.tuvanchuyen;
	}

	public void setTuvanchuyen(String tuvanchuyen) {

		this.tuvanchuyen=tuvanchuyen;
	}
	
	public String getDvkdId()
	{
		return dvkdId;
	}

	public void setDvkdId(String dvkdId)
	{
		this.dvkdId = dvkdId;
	}
	public String getChietkhau()
	{
		return chietkhau;
	}

	public void setChietkhau(String chietkhau)
	{
		this.chietkhau = chietkhau;
	}

	public void setLoaiNPP(String loaiNPP) {
		
		this.loaiNPP = loaiNPP;
	}

	public String getLoaiNPP() {
		
		return this.loaiNPP;
	}

	public ResultSet getTructhuoc() {
		
		return this.tructhuocRs;
	}

	public void setTructhuoc(ResultSet tructhuocRs) {
		
		this.tructhuocRs = tructhuocRs;
	}

	public void setTructhuocId(String tructhuoc) {
		
		this.tructhuocId = tructhuoc;
	}

	public String getTructhuocId() {
		
		return this.tructhuocId;
	}

	public ResultSet getrs_gsbh() {

		return this.gsbhList;
	}

	public String getGsbhId() {

		return this.gsbhIds;
	}

	public void setGsbhId(String gbbhId) {

		this.gsbhIds = gbbhId;
	}

	public ResultSet getrs_khott() {
		
		return this.rs_khott;
	}

	public void setIsKhachhang(String isKH) {

		this.isKH = isKH;
	}

	public String getIsKhachhang() {

		return this.isKH;
	}

	public ResultSet getrs_nvbh() {

		return this.nvbhList;
	}

	public String getNvbhId() {

		return this.nvbhIds;
	}

	public void setNvbhId(String nvbhId) {

		this.nvbhIds = nvbhId;
	}

	public void setKyhieuHD(String kyhieuHD) {
		this.kyhieuHD = kyhieuHD;
	}
	public String getKyhieuHD() {
		return kyhieuHD;
	}
	
	public void setSoHDTu(String soHDTu) {
		SoHDTu = soHDTu;
	}
	public String getSoHDTu() {
		return SoHDTu;
	}
	
	public void setSoHDDen(String soHDDen) {
		SoHDDen = soHDDen;
	}
	public String getSoHDDen() {
		return SoHDDen;
	}

	public String getTuxuatkhoETC() {

		return this.tuxuatETC;
	}

	public void setTuxuatkhoETC(String tuxuatETC) {

		this.tuxuatETC = tuxuatETC;
	}

	
	public String getTutaohoadonOTC() {
		return this.tutaohoadonOTC;
	}

	
	public void setTutaohoadonOTC(String tutaohoadonOTC) {
		this.tutaohoadonOTC= tutaohoadonOTC;
		
	}

	
	public String getDungchungkenh() {
		
		return this.dungchungkenh;
	}

	
	public void setDungchungkenh(String dungchungkenh) {
		
		this.dungchungkenh = dungchungkenh;
	}
	
	String makho,manx;
	
	public String getMaKho()
	{
		return this.makho;
	}
	public void setMaKho(String makho)
	{
		this.makho =makho;
	}
	
	public String getMaNX()
	{
		return this.manx;
	}
	public void setMaNX(String manx)
	{
		this.manx= manx;
	}

	
	public String getLoaiCN() {
		
		return this.loaicn;
	}

	
	public void setLoaiCN(String loaicn) {
		
		this.loaicn=loaicn;
	}

	
	public ResultSet getLoaiCNRs() {
		
		return this.loaicnRs;
	}

	
	public void setLoaiCNRs(ResultSet loaicnRs) {
		
		this.loaicnRs=loaicnRs;
	}

	
	public String getTenKyHd() {
		
		return this.tenkyhd;
	}

	
	public void setTenKyHd(String TenKyHd) {
		
		this.tenkyhd=TenKyHd;
	}


	public void setHanmucno(String hanmucno) {
	
		this.Hanmucno = hanmucno;
	}


	public String getHanmucno() {
	
		return this.Hanmucno;
	}


	public void setSongayno(String songayno) {
	
		this.Songayno = songayno;
	}


	public String getSongayno() {
	
		return this.Songayno;
	}
	public String hanmucdoanhthu;

	public String getHanmucdoanhthu()
  {
  	return hanmucdoanhthu;
  }

	public void setHanmucdoanhthu(String hanmucdoanhthu)
  {
  	this.hanmucdoanhthu = hanmucdoanhthu;
  }


	public String getThuKho() {
		
		return this.thukho;
	}


	public void setThuKho(String thukho) {
		
		this.thukho = thukho;
	}

	public String getCMTND() {
		return CMTND;
	}

	public void setCMTND(String cMTND) {
		CMTND = cMTND;
	}
	
	public String getXuattaikho() {
		return xuattaikho;
	}

	public void setXuattaikho(String xuattaikho) {
		this.xuattaikho = xuattaikho;
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
	
	public void setId(String id) 
	{
		this.id = id;
	}

	public String getTen() 
	{
		return this.ten;
	}

	public void setTen(String ten) 
	{
		this.ten = ten;
	}
	
	public String getDiachi() 
	{
		return this.diachi;
	}

	public void setDiachi(String diachi) 
	{
		this.diachi = diachi;
	}
	
	public String getTpId() 
	{
		return this.tpId;
	}

	public void setTpId(String tpId) 
	{
		this.tpId = tpId;
	}

	public String getQhId() 
	{
		return this.qhId;
	}

	public void setQhId(String qhId) 
	{
		this.qhId = qhId;
	}

	public String getSodienthoai() 
	{
		return this.sodienthoai;
	}

	public void setSodienthoai(String sodienthoai) 
	{
		this.sodienthoai = sodienthoai;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setMaSAP(String ma) 
	{
		this.ma = ma;
	}

	public String getMaSAP() 
	{
		return this.ma;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getMessage() 
	{
		return this.msg;
	}
	
	public void setMessage(String msg) 
	{
		this.msg = msg;
	}

	public ResultSet getTp() 
	{
		return this.tp;
	}

	public void setTp(ResultSet tp) 
	{
		this.tp = tp;
	}

	public ResultSet getQh() 
	{
		return this.qh;
	}

	public void setQh(ResultSet qh) 
	{
		this.qh = qh;
	}
	
	public ResultSet getKhuvuc() 
	{
		return this.khuvucList;
	}

	public void setKhuvuc(ResultSet khuvucList) 
	{
		this.khuvucList = khuvucList;
	}
		
	public String getKvId()
	{
		return this.kvId;
	}
	
	public void setKvId(String kvId)
	{
		this.kvId = kvId;
	}

	public String getPriSec() 
	{
		if(this.prisec==null){
			return "";
		}else{
			return this.prisec;
		}
	}

	public void setPriSec(String prisec) 
	{
		this.prisec = prisec;
	}

	public void setTenNguoiDaiDien(String nguoidaidien)
	{
		this.tennguoidaidien = nguoidaidien;
	}
	
	public String getTenNguoiDaiDien()
	{
		return this.tennguoidaidien;
	}
	
	public void setFAX(String fax)
	{
		this.fax = fax;
	}
	
	public String getFAX()
	{
		return this.fax;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public String getEmail()
	{
		return this.email;
	}
	
	public void setHinhThucThanhToan(String httt)
	{
		this.hinhthucthanhtoan = httt;
	}
	
	public String getHinhThucThanhToan()
	{
		return this.hinhthucthanhtoan;
	}
	
	public void setNganHang(String nganhang)
	{
		this.nganhang = nganhang;
	}
	
	public String getNganHang()
	{
		return this.nganhang;
	}
	
	public void setSoTK(String sotk)
	{
		this.sotk = sotk;
	}
	
	public String getSoTK()
	{
		return this.sotk;
	}
	
	public void setGhichu(String ghichu)
	{
		this.ghichu = ghichu;
	}
	
	public String getGhichu()
	{
		return this.ghichu;
	}
	public void setNgaybatdau(String ngaybatdau)
	{
		this.ngaybatdau=ngaybatdau;
	}
	public String getNgaybatdau()
	{
		return this.ngaybatdau;
	}
	public void setNgayketthuc(String ngayketthuc)
	{
		this.ngayketthuc=ngayketthuc;
	}
	public String getNgayketthuc()
	{
		return this.ngayketthuc;
	}
	
	public String getTaiKhoanCongNo() {
		return taiKhoanCongNo;
	}

	public void setTaiKhoanCongNo(String taiKhoanCongNo) {
		this.taiKhoanCongNo = taiKhoanCongNo;
	}

	public List<Erp_Item> getTaiKhoanCongNoList() {
		return taiKhoanCongNoList;
	}

	public void setTaiKhoanCongNoList(List<Erp_Item> taiKhoanCongNoList) {
		this.taiKhoanCongNoList = taiKhoanCongNoList;
	}

	public String getTaiKhoanNoiBo() {
		return taiKhoanNoiBo;
	}

	public void setTaiKhoanNoiBo(String taiKhoanNoiBo) {
		this.taiKhoanNoiBo = taiKhoanNoiBo;
	}

	public List<Erp_Item> getTaiKhoanNoiBoList() {
		return taiKhoanNoiBoList;
	}

	public void setTaiKhoanNoiBoList(List<Erp_Item> taiKhoanNoiBoList) {
		this.taiKhoanNoiBoList = taiKhoanNoiBoList;
	}
}