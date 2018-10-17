
package geso.traphaco.center.beans.stockintransit.imp;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.ServletOutputStream;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class Stockintransit implements IStockintransit
{
	String xemtheo;
	String Ctkmtichluy;
	ResultSet Rskmtl;
	String phanloai;
	String userId;
	String nppId;	
	String nppTen;
	String khId;
	String khTen;
	String kenhId;
	String dvkdId;
	String nhanhangId;
	String chungloaiId;
	String tungay;
	String denngay;
	String userTen;
	String khoId;
	String book;
	String unghang;
	ResultSet kho;
	String msg;
	ResultSet kenh;
	ResultSet nhomkenh;
	ResultSet dvkd;
	ResultSet nhanhang;
	ResultSet tinhthanh;

	String hangdiduong;
	ResultSet chungloai;
	String vungId;
	String khuvucId;
	ResultSet khuvuc;
	ResultSet vung;
	ResultSet npp;
	String vat;
	String gsbhId;
	String Ngayketthucctkm;

	ResultSet gsbh;
	String asmId;
	ResultSet asm;
	String bmId;
	ResultSet bm;
	String sanphamId;
	ResultSet sanpham;
	String dvdlId;
	String[] FieldShow;
	String[] FieldHidden;
	String ngayton;
	String promotion;
	String discount;
	ResultSet dvdl;
	String lessday;
	String moreday;
	dbutils db;
	String year;
	String month;
	String QuyCachId;
	String ddkd;
	ResultSet rsddkd;
	ResultSet khrs;
	ResultSet RsSearch;
	
	//
	String unit;
	String groupCus;
	//
	String programsId;
	ResultSet rsPrograms;

	String donviTinh;
	String HangDiDuong;



	String fromMonth;
	String toMonth;
	String type;
	String ToYear = "";
	String FromYear = "";
	String Nhospid = "";
	ResultSet RsNhomSp;
	ResultSet RsQuyCach;

	ResultSet Rshangcuahang;

	ResultSet Rsloaicuahang;
	ResultSet RsVitricuahang;
	ResultSet RsTinhthanh;
	ResultSet RsQuanhuyen;

	ResultSet nhomRs;

	String hangcuahangid;
	String loaicuahangid;
	String vitricuahangid;
	String tinhthanhid;
	String quanhuyenid;
	String muclay;
	String nppID;

	ResultSet nrs;
	String nspIds;
	ResultSet tieuchiRs;
	ResultSet nppRs;

	float CONVERT = 28.346457f; // = 1cm

	String tieuchiId;

	String nganhHangId;
	ResultSet ttRs;
	String ttId;
	ResultSet nganhHangRs;

	// chỉnh sửa
	String activeTab;
	ResultSet EtcRs;
	ResultSet HdKhacRs;
	ResultSet OtcRs;
	ResultSet kmRs;

	String nvgnId;

	String cn1;
	
	ResultSet loaikhoRs;
	String loaikho;

	String nccId;
	ResultSet nccrs;
	
	ResultSet Khors;
	
	String ChiTietXNT_Lo="";
	String ChiTietXNT_Lo_Tong ="";
	
	String laygiaGoc;
	String chuyenSale;
	String laykhuyenMai;
	ResultSet Rsdiaban;
	String diabanid;
	ResultSet khrsnew;
	String loaisalesIn;
	
	Object loainhanvien;
	Object doituongId;
	
	String isdlpp;
	String dlppIds;
	ResultSet dlppRs;
	
	// Tỉnh và loại đơn hàng
	String tinh;
	String loaidh;
	String Laygiavon;
	ResultSet tinhRs;
	ResultSet loaidhRs;
	ResultSet xntRs;
	
	String ErpCongTyId;
	
	public Stockintransit()
	{
		this.laygiaGoc = "0";
		this.chuyenSale = "0";
		this.laykhuyenMai = "0";
		
		this.cn1="0";
		this.userId = "";
		this.xemtheo = "";
		this.nppId = "";
		this.nppTen = "";
		this.kenhId = "";
		this.dvkdId = "";
		this.nhanhangId = "";
		this.chungloaiId = "";
		this.tungay = "";
		this.denngay = "";
		this.userTen = "";
		this.khoId = "";
		this.book = "";
		this.msg = "";
		this.vungId = "";
		this.khuvucId = "";
		this.vat = "";
		this.gsbhId = "";
		this.asmId = "";
		this.bmId = "";
		this.sanphamId = "";
		this.dvdlId = "";
		this.ngayton = "1";
		this.promotion = "0";
		this.Ngayketthucctkm="";
		this.discount = "0";
		this.lessday = "0";
		this.moreday = "0";
		this.ddkd = "";
		this.unit = "";
		this.groupCus = "";
		this.programsId = "";
		this.donviTinh = "";
		this.month = "";
		this.year = "";
		this.fromMonth = "";
		this.toMonth = "";
		this.unghang = "1";
		this.type = "0";
		this.phanloai = "";
		this.Ctkmtichluy = "";
		this.khId="";
		this.ttId="";
		this.coHoadon = "";
		this.spId = "";
		this.laytheo = "0";
		this.diabanid="";
		this.nspIds = "";
		this.year = getDate().substring(0, 4);
		this.FromYear = this.year;
		this.ToYear = this.year;
		this.toMonth = getDate().substring(5, 7);
		ChiTietXNT_Lo="";
		//System.out.println("11.Month la: " + this.toMonth);
		this.db = new dbutils();
		this.nccId = "";
		hangcuahangid = "";
		loaicuahangid = "";
		vitricuahangid = "";
		tinhthanhid = "";
		quanhuyenid = "";
		this.muclay = "";
		this.dvdlId = "";
		this.HangDiDuong="0";
		this.nganhHangId="";
		this.HoaDonKmDb="";
		this.tbhId="";
		this.nppID="";
		this.activeTab="0";
		this.nvgnId="";
		loaiNv="";
		this.trangthai="";
		this.tructhuoc_fk="";

		this.cndt="1";
		this.kh="1";
		this.coHoadon = "";
		this.spId = "";
		this.laytheo = "0";
		this.quy="";
		this.cn1="0";
		this.hangdiduong = "0";
		this.loaisalesIn = "0";
		ChiTietXNT_Lo_Tong ="";
		this.dlppIds = "";
		this.isdlpp = "";
		this.tinh= "";
		this.loaidh = "";
		this.khTen = "";
		this.Laygiavon="";
	}
	public String getTinh() {
		return tinh;
	}

	public void setTinh(String tinh) {
		this.tinh = tinh;
	}

	public String getLoaidh() {
		return loaidh;
	}

	public void setLoaidh(String loaidh) {
		this.loaidh = loaidh;
	}

	public ResultSet getTinhRs() {
		return tinhRs;
	}

	public void setTinhRs(ResultSet tinhRs) {
		this.tinhRs = tinhRs;
	}

	public ResultSet getLoaidhRs() {
		return loaidhRs;
	}

	public void setLoaidhRs(ResultSet loaidhRs) {
		this.loaidhRs = loaidhRs;
	}

	public ResultSet getKhors() {
		return Khors;
	}

	public void setKhors(ResultSet khors) {
		Khors = khors;
	}

	public String getCn1() {
		return cn1;
	}

	public void setCn1(String cn1) {
		this.cn1 = cn1;
	}
	String khoid;
	public String getKhoid() {
		return khoid;
	}

	public void setKhoid(String khoid) {
		this.khoid = khoid;
	}
	String tenkho;

	public String getTenkho() {
		return tenkho;
	}

	public void setTenkho(String tenkho) {
		this.tenkho = tenkho;
	}
	
	public String getTinhthanhid()
	{
		return tinhthanhid;
	}

	public void setTinhthanhid(String tinhthanhid)
	{
		this.tinhthanhid = tinhthanhid;
	}

	public void setuserId(String userId)
	{

		this.userId = userId;
	}

	public String getuserId()
	{

		return this.userId;
	}

	public void setnppId(String nppId)
	{

		this.nppId = nppId;
	}

	public String getnppId()
	{

		return this.nppId;
	}

	public void setnppTen(String nppTen)
	{

		this.nppTen = nppTen;
	}

	public String getnppTen()
	{

		return this.nppTen;
	}

	public void setkenhId(String kenhId)
	{

		this.kenhId = kenhId;
	}

	public String getkenhId()
	{

		return this.kenhId;
	}

	public void setdvkdId(String dvkdId)
	{

		this.dvkdId = dvkdId;
	}

	public String getdvkdId()
	{

		return this.dvkdId;
	}

	public void setASMId(String asmId)
	{

		this.asmId = asmId;
	}

	public String getASMId()
	{

		return this.asmId;
	}

	public void setBMId(String bmId)
	{

		this.bmId = bmId;
	}

	public String getBMId()
	{

		return this.bmId;
	}

	public void setnhanhangId(String nhanhangId)
	{

		this.nhanhangId = nhanhangId;
	}

	public String getnhanhangId()
	{

		return this.nhanhangId;
	}

	public void setchungloaiId(String chungloaiId)
	{

		this.chungloaiId = chungloaiId;
	}

	public String getchungloaiId()
	{

		return this.chungloaiId;
	}

	public void setkenh(ResultSet kenh)
	{

		this.kenh = kenh;
	}

	public ResultSet getkenh()
	{

		return this.kenh;
	}

	public void setdvkd(ResultSet dvkd)
	{

		this.dvkd = dvkd;
	}

	public ResultSet getdvkd()
	{

		return this.dvkd;
	}

	public void setASM(ResultSet asm)
	{

		this.asm = asm;
	}

	public ResultSet getASM()
	{

		return this.asm;
	}

	public void setBM(ResultSet bm)
	{

		this.bm = bm;
	}

	public ResultSet getBM()
	{

		return this.bm;
	}

	public void setnhanhang(ResultSet nhanhang)
	{

		this.nhanhang = nhanhang;
	}

	public ResultSet getnhanhang()
	{

		return this.nhanhang;
	}

	public void setchungloai(ResultSet chungloai)
	{

		this.chungloai = chungloai;
	}

	public ResultSet getchungloai()
	{

		return this.chungloai;
	}

	public void setMonth(String month)
	{

		this.month = month;
	}

	public String getMonth()
	{
		if (this.month.length() > 0)
		{
			return this.month;
		} else
		{
			return this.getDate().substring(5, 7);
		}

	}

	public void setUnghang(String unghang)
	{

		this.unghang = unghang;
	}

	public String getUnghang()
	{

		return this.unghang;
	}

	public void setYear(String year)
	{

		this.year = year;
	}

	public String getYear()
	{
		if (this.year.length() > 0)
		{
			return this.year;
		} else
		{
			return this.getDate().substring(0, 4);
		}
	}

	public void createKhRS1()
	{
		Utility ut = new Utility();
		this.nppId = ut.getIdNhapp(userId);

		//NPP
		if (this.nppId!=null)
		{
			// Dai dien kinh doanh				
			String query="SELECT pk_seq,ten FROM DAIDIENKINHDOANH d where trangthai=1 ";
			query+= " and NPP_FK='"+nppId+"'";

			if(this.phanloai.equals("2")&& !loaiNv.equals("3")){
				query+= " and pk_Seq in " + ut.Quyen_Ddkd(userId)+"";
			}
			this.setRsddkd(db.get(query));
			//GET KHACH HANG

			query ="select distinct  kh.PK_SEQ, kh.MaFast + ' - ' + kh.Ten as Ten" +
			"	from KHACHHANG kh "
			/*+ "inner join KHACHHANG_TUYENBH kh_tbh on kh.PK_SEQ=kh_tbh.KHACHHANG_FK "+
			" inner join TUYENBANHANG tbh on tbh.PK_SEQ=kh_tbh.TBH_FK"*/ +
			" where 1=1 and kh.TrangThai ='1'";

			/*if(this.ddkd.length()>0)
			{
				query += " and tbh.DDKD_FK ='"+ddkd+"'";
			}*/
			if(nppId.trim().length()>0)
			{
				query+= " and kh.NPP_FK='"+nppId+"'";
			}

			System.out.println("cau kh:" + query);
			this.khrs =  this.db.get( query);

			query="select PK_SEQ,TEN ,DIACHI from nhanviengiaonhan where 1=1  ";
			if(this.nppId.length()>0)
			{
				query+=" and npp_fk='"+nppId+"' ";
			}
			if(this.phanloai.equals("2")&& !loaiNv.equals("3"))
			{
				query+= " and npp_fk in " + ut.quyen_npp(userId)+"";
			}
			this.nvgnRs=this.db.get(query);

		} 
		else//TRUNG TAM
		{
			//GET NHA PHAN PHOI
			String query="";
			query="select pk_Seq,ten,ma from nhaphanphoi where iskhachhang=0 and trangthai=1 and pk_Seq in (" + ut.quyen_npp(userId)+")";

			if(this.ttId.length()>0)
			{
				query+=" and tinhthanh_fk='"+this.ttId+"' ";
			}

			query += " order by loaiNPP asc ";
			System.out.println("::: INIT NPP: " + query );
			this.nppRs = this.db.get(query);


			//GET DAI DIEN KINH DOANH
			query="SELECT pk_seq,ten FROM DAIDIENKINHDOANH d where trangthai=1 ";

			if(this.phanloai.equals("2")&& !loaiNv.equals("3")){
				query+= " and pk_Seq in " + ut.Quyen_Ddkd(userId)+"";
			}
			if(nppID.trim().length()>0){
				query+= " and NPP_FK='"+nppID+"'";
			}
			this.setRsddkd(db.get(query));

			//GET KHACH HANG
			query ="select distinct  kh.PK_SEQ, kh.MaFast + ' - ' +kh.Ten as Ten" +
			"	from KHACHHANG kh inner join KHACHHANG_TUYENBH kh_tbh on kh.PK_SEQ=kh_tbh.KHACHHANG_FK "+
			" inner join TUYENBANHANG tbh on tbh.PK_SEQ=kh_tbh.TBH_FK" +
			" where 1=1 and kh.TrangThai ='1'";

			if(this.ddkd.length()>0)
			{
				query += " and tbh.DDKD_FK ='"+ddkd+"'";
			}
			if(nppID.trim().length()>0)
			{
				query+= " and kh.NPP_FK='"+nppID+"'";
			}

			System.out.println("cau kh:" + query);
			this.khrs =  this.db.get( query);
		}
	}

	String loaiNv="";
	String tructhuoc_fk="";

	String cttbId= "";
	ResultSet cttbRs ;

	String loaiMenu="";
	public String getLoaiMenu() {
		return loaiMenu;
	}
	public void setLoaiMenu(String loaiMenu) {
		this.loaiMenu = loaiMenu;
	}
	
	
	public String getCttbId() {
		return cttbId;
	}
	public void setCttbId(String cttbId) {
		this.cttbId = cttbId;
	}
	public ResultSet getCttbRs() {
		this.cttbRs = db.get("select pk_seq ,diengiai from CTTRUNGBAY order by pk_seq desc");
		return cttbRs;
	}

	public String getTructhuoc_fk()
	{
		return tructhuoc_fk;
	}

	public void setTructhuoc_fk(String tructhuoc_fk)
	{
		this.tructhuoc_fk = tructhuoc_fk;
	}

	public void init()
	{
		Utility Ult = new Utility();
		String sql = "";
		System.out.println(" vao init");
		try
		{

			sql="SELECT PK_SEQ,MA,TEN  FROM ERP_DONVITHUCHIEN WHERE TRANGTHAI=1 and congty_fk="+this.ErpCongTyId +
			 " and pk_seq in (select donvithuchien_fk from nhanvien_donvithuchien where nhanvien_fk="+this.userId+")";
			
			sql = "select phanloai,LOAI from nhanvien where pk_seq=" + this.userId;
			ResultSet rs = this.db.get(sql);
			if (rs != null)
			{
				if (rs.next())
				{
					this.phanloai = rs.getString("phanloai");
					loaiNv= rs.getString("LOAI")==null?"":rs.getString("LOAI");

					if (rs.getString("phanloai").equals("1")||( this.phanloai.equals("2")   && loaiNv.equals("3")   )  )
					{
						this.nppId = Ult.getIdNhapp(this.userId);
						this.nppTen = Ult.getTenNhaPP();
						this.tructhuoc_fk=Ult.getTructhuoc_fk();
					}
					rs.close();
				}
			}
			System.out.println("sql : " + sql);
		} catch (Exception er)
		{
			er.printStackTrace();
		}

		sql = " select pk_seq,ten,diengiai from kenhbanhang where 1 = 1 ";
		sql += " and pk_seq in " + Ult.quyen_kenh( this.userId );
		this.kenh = db.get(sql);
		
		this.nhomkenh = db.get(" select pk_seq,ten,diengiai from NHOMKENH ");

		this.vung = db.get("select pk_seq,ten,diengiai from vung ");

		this.dvkd = db.get("select pk_seq,diengiai from donvikinhdoanh ");

		this.tinhthanh = db.get("select pk_seq,ten from tinhthanh");
		
		this.tinhRs =  db.get("select pk_seq,ten from tinhthanh");
		
		sql = "select pk_seq,ten,loai from kho where 1 = 1 ";
		sql += " and pk_seq in " + Ult.quyen_kho( this.userId );
		this.Khors = db.get(sql);

		if (this.vungId.length() > 0)
		{
			this.khuvuc = db.get("select pk_seq,ten from khuvuc where vung_fk ='" + this.vungId + "'");
		} else
		{
			String query_khuvuc=" select PK_SEQ, TEN from KHUVUC ";
			System.out.println("Get Khuvuc "+query_khuvuc);
			this.khuvuc= db.get(query_khuvuc);		
		}

		Utility  util = new Utility();

		sql = "select pk_seq,ten from nhaphanphoi where trangthai ='1'   ";

		if(this.ttId.length()>0)
		{
			sql+=" and TinhThanh_fk='"+this.ttId+"'";
		}
		this.Rsdiaban=db.get("select * from diaban ");
		
		/*
		 * Neu dang nhap la trung tam va phan loai #
		 * <%=obj.getLoai().equals("4") ? " selected " : ""%>>Trình dược
		 * viên</option> <option value="5" <%=obj.getLoai().equals("5") ?
		 * " selected " : ""%>>Ban Giám Đốc</option> <option value="1"
		 * <%=obj.getLoai().equals("1") ? " selected " : ""%>>Giám Đốc
		 * Miền</option> <option value="2" <%=obj.getLoai().equals("2") ?
		 * " selected " : ""%>>Phụ trách Vùng</option> <option value="3"
		 * <%=obj.getLoai().equals("3") ? " selected " : ""%>>Phụ Trách Tỉnh/
		 * GĐCN Cấp 2</option>
		 */
		if(this.phanloai.equals("2")&& !loaiNv.equals("3"))
		{
			sql+= " and pk_Seq in " + util.quyen_npp(userId)+"";
		}


		if (this.khuvucId.length() > 0)
		{
			sql = sql + " and khuvuc_fk ='" + this.khuvucId + "'";
		}
		if (this.vungId.length() > 0)
		{
			sql = sql + " and khuvuc_fk in (select pk_seq from khuvuc where vung_fk ='" + this.vungId + "')";
		}
		// System.out.println("Get NPP :"+sql);
		if (this.kenhId.length() > 0)
			sql = sql + " and pk_seq in (select npp_fk from NHAPP_KBH where kbh_fk ='" + this.kenhId + "')";

		if(this.gsbhId.length() > 0)
		{
			sql = sql + " and pk_seq in (select npp_fk from NHAPP_GIAMSATBH where gsbh_fk = '"+ this.gsbhId +"')";			
		}

		if(this.programsId.trim().length() >0 && this.nppId.trim().equals("")) 
		{
			sql = sql + 	" and pk_seq in (select npp_fk from  CTKM_NPP inner join CTKHUYENMAI ctkm on ctkm.pk_seq=CTKM_NPP.ctkm_fk where ctkm.scheme='"+this.getPrograms()+"'    ) ";
		}	
		//	System.out.println("Get NPP :" + sql);

		sql += " order by ten ";
		this.npp = db.getScrol(sql);

		sql=	" select distinct a.PK_SEQ, a.scheme from CTKHUYENMAI a  " +
				"inner join CTKM_NPP b on b.CTKM_FK = a.PK_SEQ where LOAICT = '3' ";
		if (this.nppId !=null && this.nppId.length() > 0){
			sql=sql+ " and b.NPP_FK = '" + this.nppId + "' "; 
		}
		if(this.gettungay()!=null &&this.gettungay().length() >0) {
			sql=sql+ " and a.tungay >='"+this.gettungay()+"'";
		}
		if( this.getdenngay()!=null && this.getdenngay().length() > 0){
			sql=sql+ " and a.denngay <='"+this.getdenngay()+"'";
		}
		if(this.getNgayketthucctkm()!= null&& this.getNgayketthucctkm().length() >0) {
			sql=sql+ " and a.denngay like  '"+this.getNgayketthucctkm()+"%'";
		}


		this.Rskmtl = db.get(sql);

		if (this.dvkdId.length() > 0)
			this.nhanhang = db.get("select * from nhanhang where dvkd_fk ='" + this.dvkdId + "'");
		else
			this.nhanhang = db.get("select * from nhanhang ");

		if(this.nhanhangId.length() > 0)
			sql = "select cl.pk_Seq,ten from chungloai cl inner join nhanhang_chungloai nhcl on nhcl.cl_fk = cl.pk_seq where nh_fk = '"+ this.nhanhangId +"' ";
		else
			sql = "select pk_Seq,ten  from chungloai";

		this.chungloai = db.get(sql);

		sql = 
			"		SELECT  GSBH.PK_SEQ, GSBH.TEN  "+  
			"		FROM GIAMSATBANHANG GSBH      "+
			"		WHERE GSBH.TRANGTHAI = '1' ";


		if (this.kenhId.length() > 0)
		{
			sql = sql + " AND GSBH.KBH_FK ='" + this.kenhId + "'";
		} else if (this.dvkdId.length() > 0)
		{
			sql = sql + " AND GSBH.DVKD_FK ='" + this.dvkdId + "'";
		} else if (this.khuvucId.length() > 0)
		{
			sql = sql + " AND GSBH.PK_SEQ IN ( SELECT GSBH_FK FROM GSBH_KHUVUC WHERE KHUVUC_FK ='" + this.khuvucId + "' )";
		}else if (this.nppId!=null && this.nppId.length() > 0)
		{
			sql = sql + " AND GSBH.PK_SEQ in (select GSBH_FK from NHAPP_GIAMSATBH where NPP_FK = '" + this.nppId + "') ";
		}

		if(this.vungId!=null && this.vungId.length()>0)
			sql+= " and  GSBH.VUNG_fk in ( select pk_seq from Vung where vung_fk ='"+this.vungId+"') ";

		//PHAN QUYEN GIAM SAT
		sql += util.getPhanQuyen_TheoNhanVien("GIAMSATBANHANG", "GSBH", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		
		this.gsbh = db.get(sql);

		
		sql = "SELECT ASM.PK_SEQ, ASM.TEN  " + 
				"FROM ASM ASM " + 
				"WHERE ASM.TRANGTHAI ='1'";
		if (this.kenhId.length() > 0)
		{
			sql = sql + " AND ASM.KBH_FK ='" + this.kenhId + "'";
		} else if (this.dvkdId.length() > 0)
		{
			sql = sql + " AND ASM.DVKD_FK ='" + this.dvkdId + "'";
		} else if (this.khuvucId.length() > 0)
		{
			sql = sql + " AND ASM.PK_SEQ IN ( SELECT asm_fk FROM ASM_KHUVUC WHERE khuvuc_Fk= '" + this.khuvucId + "' ) ";
		} else if (this.vungId.length() > 0)
		{
			sql = sql + " AND ASM.PK_SEQ in  ( SELECT asm_fk FROM ASM_KHUVUC WHERE khuvuc_Fk in (select pk_Seq from KhuVuc where vung_Fk='"+this.vungId+"' ) )    ";
		}

		//	System.out.println("ASM " + sql);
		this.asm = db.get(sql);

		sql = " SELECT BM.PK_SEQ, BM.TEN  " + "FROM BM BM " + "INNER JOIN BM_CHINHANH BM_CHINHANH ON BM_CHINHANH.BM_FK = BM.PK_SEQ " + "INNER JOIN VUNG VUNG ON VUNG.PK_SEQ = BM_CHINHANH.VUNG_FK " + "WHERE BM.TRANGTHAI ='1'";
		if (this.kenhId.length() > 0)
		{
			sql = sql + " AND BM.KBH_FK ='" + this.kenhId + "'";
		} else if (this.dvkdId.length() > 0)
		{
			sql = sql + " AND BM.DVKD_FK ='" + this.dvkdId + "'";
		} else if (this.vungId.length() > 0)
		{
			sql = sql + " AND VUNG.PK_SEQ = '" + this.vungId + "'";
		}

		// System.out.println("BM " + sql);
		this.bm = db.get(sql);

		String st = "select pk_seq,ma,ten from sanpham where trangthai ='1'  ";
		if (this.nhanhangId.length() > 0)
			st = st + " and nhanhang_fk ='" + this.nhanhangId + "'";
		if (this.chungloaiId.length() > 0)
			st = st + " and chungloai_fk ='" + this.chungloaiId + "'";
		if (this.dvkdId.length() > 0)
			st = st + " and dvkd_fk ='" + this.dvkdId + "'";
		if (this.dvdlId.length() > 0)
			st = st + " and dvdl_fk ='" + this.dvdlId + "'";
		if(this.nganhHangId!=null && this.nganhHangId.length()>0)
			st+=" and NGANHHANG_fk ='"+this.nganhHangId+"'";

		if (this.Nhospid.length() > 0)
			st = st + " and pk_seq in  (select sp_fk from nhomsanpham_sanpham where nsp_fk='" + this.Nhospid + "')";

		st += " and pk_seq in " + Ult.quyen_sanpham( this.userId );
		this.sanpham = db.get(st);
		this.SpRs = db.get(st);

		sql = "select pk_seq,donvi from donvidoluong ";

		this.dvdl = db.get(sql);
		
		sql = "select * from kho where 1 = 1 ";
		sql += " and pk_seq in " +  Ult.quyen_kho( this.userId );
		System.out.println(":::: KHO:  " + sql);
		this.kho = db.get(sql);

		// Dai dien kinh doanh
		String query = "";
		if ( this.nppId!=null&& this.nppId.length() > 0)
		{
			query = "SELECT pk_seq,ten FROM DAIDIENKINHDOANH d where d.trangthai=1 and d.PK_SEQ in (Select ddkd_fk from DAIDIENKINHDOANH_NPP where npp_fk='"+this.nppId+"' )  ";
			System.out.println("1. loainv: "+loaiNv);
			if(this.loaiNv.equals("4"))
				query="SELECT d.pk_seq, d.ten FROM DAIDIENKINHDOANH d inner join nhanvien a on d.pk_seq = a.ddkd_fk where d.trangthai=1 and a.pk_seq = "+this.userId;
			System.out.println("1.ddkdrs: "+query);
		} 
		else
		{
			query = "SELECT pk_seq, ten FROM DAIDIENKINHDOANH d where trangthai=1 ";
			if(this.phanloai.equals("2")&& !loaiNv.equals("3"))
			{
				query += " and pk_Seq in " + util.Quyen_Ddkd(userId)+"";
			}

			System.out.println("1. loainv: "+loaiNv);
			if(this.loaiNv.equals("4"))
				query="SELECT d.pk_seq, d.ten FROM DAIDIENKINHDOANH d inner join nhanvien a on d.pk_seq = a.ddkd_fk where d.trangthai=1 and a.pk_seq = "+this.userId;
			System.out.println("1.ddkdrs: "+query);
			if(this.ttId.length()>0)
			{
				query += " and pk_seq in (select ddkd_fk from daidienkinhdoanh_npp where npp_fk in (Select pk_Seq from nhaphanphoi where tinhthanh_fk='"+this.ttId+"')) ";
			}
		}
		
		query += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "d", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		System.out.println("_________TDV: " + query);
		this.rsddkd = db.get(query);

		// Lay chuong trinh khuyen mai


		sql=" select distinct  a.pk_seq,a.diengiai,a.scheme from CTKHUYENMAI a  " +
		"inner join CTKM_NPP b on b.CTKM_FK = a.PK_SEQ where 1=1 ";
		if (this.nppId != null && this.nppId.length() > 0 && this.getPrograms().trim().equals("") ){
			sql=sql+ " and b.NPP_FK = '" + this.nppId + "' "; 
		}
		if( this.gettungay()!=null && this.gettungay().length() >0) {
			sql=sql+ " and a.tungay >='"+this.gettungay()+"'";
		}
		if( this.getdenngay()!=null && this.getdenngay().length() > 0){
			sql=sql+ " and a.denngay <='"+this.getdenngay()+"'";
		}
		if( this.getNgayketthucctkm()!=null  && this.getNgayketthucctkm().length() >0) {
			sql=sql+ " and a.denngay like  '"+this.getNgayketthucctkm()+"%'";
		}

		this.rsPrograms = db.get(sql);


		this.RsNhomSp = db.get("select pk_seq,diengiai from nhomsanpham where trangthai = '1' and loainhom = '0' and TYPE = '0' ");
		this.RsQuyCach = db.get("select pk_seq,donvi as ten from donvidoluong where trangthai=1 ");

		this.nganhHangRs=db.get("SELECT PK_SEQ,TEN FROM NGANHHANG WHERE TRANGTHAI=1");

		// chỉnh sửa

		//GET KHACH HANG

		query ="  select distinct  kh.PK_SEQ, kh.MaFast + ' - '+ kh.Ten as Ten" +
			  "	 from KHACHHANG kh "
			  /*+ "inner join KHACHHANG_TUYENBH kh_tbh on kh.PK_SEQ=kh_tbh.KHACHHANG_FK "+
			  "  inner join TUYENBANHANG tbh on tbh.PK_SEQ=kh_tbh.TBH_FK"*/ +
			  "  where 1=1 and kh.TrangThai ='1' and kh.PK_SEQ in (select distinct KHACHHANG_fk from NHAPP_KHO_KYGUI_CHITIET)";

		/*if(this.ddkd.length()>0)
		{
			query += " and tbh.DDKD_FK ='"+ddkd+"'";
		}*/
		
		if(nppId!=null && nppId.trim().length()>0)
		{
			query+= " and kh.NPP_FK='"+nppId+"'";
		}

		/*if(this.ttId.length()>0)
		{
			query+= " and kh.TinhThanh_fk='"+ttId+"'";
		}*/

		System.out.println("cau kh:" + query);
		query += util.getPhanQuyen_TheoNhanVien("KHACHHANG", "kh", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		this.khrs =  this.db.get( query);
		if(this.nppId != null)
			if(this.nppId.length() > 0)
			{
				query = "select pk_seq , (MaFast + ' - '+ ten) as ten from khachhang a where npp_fk="+this.nppId+"  and trangthai=1";
				query += util.getPhanQuyen_TheoNhanVien("KHACHHANG", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
				this.khrsnew = db.get( query );
			}
		//GET nha cung cap

		query = "select distinct ncc.pk_seq, ncc.ten from erp_nhacungcap ncc" +
			  "  where 1=1 and ncc.TrangThai ='1'";
		System.out.println("cau ncc:" + query);
		this.nccrs =  this.db.get( query);

		query="select PK_SEQ, TEN from tinhthanh where 1=1 ";
		if(vungId.length()>0)
			query+=" and vung_fk = '"+vungId+"'";
		query+=" and pk_seq in  "+util.Quyen_TinhThanh(this.userId)+" ";

		this.ttRs= this.db.get(query);
		System.out.println("__________________:::::"+query);
		// hết chỉnh sửa
		
		query =  " select 1  as pk_seq, N'Kho hàng bán' AS DIENGIAI  UNION ALL "+
				 " select 2  as pk_seq, N'Kho hàng ký gửi tại NCC' AS DIENGIAI  UNION ALL "+
				 " select 3  as pk_seq, N'Kho hàng hỏng(kho biệt trữ)' AS DIENGIAI  UNION ALL "+
				 " select 4  as pk_seq, N'Kho vật tư' AS DIENGIAI  UNION ALL "+
				 " select 5  as pk_seq, N'Kho dữ trữ khách hàng' AS DIENGIAI  UNION ALL "+
				 " select 6  as pk_seq, N'Kho trình duyệt viên' AS DIENGIAI  UNION ALL "+
				 " select 7  as pk_seq, N'Kho của nhà cung cấp' AS DIENGIAI  UNION ALL "+
				 " select 8  as pk_seq, N'Kho hàng ký gửi tại KH' AS DIENGIAI  UNION ALL "+
				 " select 9  as pk_seq, N'Kho khách hàng ký gửi tại công ty' AS DIENGIAI  UNION ALL "+
				 " select 10  as pk_seq, N'Kho hàng thu hồi(Đổi)' AS DIENGIAI  ";
		
		this.loaikhoRs = this.db.get(query);
		
		this.dlppRs=db.get("select pk_seq, mafast, ten from nhaphanphoi where loainpp = 1 and trangthai = 1 and pk_Seq in "+util.quyen_npp(this.userId));
	}

	public String getLoaiNv()
	{
		return loaiNv;
	}

	public void setLoaiNv(String loaiNv)
	{
		this.loaiNv = loaiNv;
	}

	public void settungay(String tungay)
	{

		this.tungay = tungay;
	}

	public String gettungay()
	{
		return tungay;
	}

	public void setdenngay(String denngay)
	{

		this.denngay = denngay;
	}

	public String getdenngay()
	{

		return this.denngay;
	}

	public void setMsg(String msg)
	{

		this.msg = msg;
	}

	public String getMsg()
	{

		return this.msg;
	}

	public void setuserTen(String userTen)
	{

		this.userTen = userTen;
	}

	public String getuserTen()
	{

		return this.userTen;
	}

	public void setkhoId(String khoId)
	{

		this.khoId = khoId;
	}

	public String getkhoId()
	{

		return this.khoId;
	}

	public void setkho(ResultSet kho)
	{

		this.kho = kho;
	}

	public ResultSet getkho()
	{

		return this.kho;
	}

	public void setbook(String book)
	{

		this.book = book;
	}

	public String getbook()
	{

		return this.book;
	}

	public void setvat(String vat)
	{

		this.vat = vat;
	}

	public void DBclose()
	{
		try
		{
			if (chungloai != null)
				chungloai.close();
			if (dvdl != null)
				dvdl.close();
			if (dvkd != null)
				dvkd.close();
			if (gsbh != null)
				gsbh.close();
			if (kenh != null)
				kenh.close();
			if (kho != null)
				kho.close();
			if (khuvuc != null)
				khuvuc.close();
			if (nhanhang != null)
				nhanhang.close();
			if (npp != null)
				npp.close();
			if (rsddkd != null)
				rsddkd.close();
			if (rsPrograms != null)
				rsPrograms.close();
			if (sanpham != null)
				sanpham.close();
			if (vung != null)
				vung.close();
			if (RsNhomSp != null)
			{
				RsNhomSp.close();
			}
			if (RsQuyCach != null)
			{
				RsQuyCach.close();
			}
			if (db != null)
				db.shutDown();

			if (asm != null)
			{
				asm.close();
			}
			if (bm != null)
			{
				bm.close();
			}

			if (RsVitricuahang != null)
			{
				RsVitricuahang.close();
			}
			if (RsTinhthanh != null)
			{
				RsTinhthanh.close();
			}
			if (RsQuanhuyen != null)
			{
				RsQuanhuyen.close();
			}
			if (Rsloaicuahang != null)
			{
				Rsloaicuahang.close();
			}
			if (Rshangcuahang != null)
			{
				Rshangcuahang.close();
			}
			if (HdKhacRs != null)
			{
				HdKhacRs.close();
			}

		} catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("khong the dong ket noi");
		}
	}

	public void setvungId(String vungId)
	{

		this.vungId = vungId;
	}

	public String getvungId()
	{

		return this.vungId;
	}

	public void setvung(ResultSet vung)
	{

		this.vung = vung;
	}

	public ResultSet getvung()
	{

		return this.vung;
	}

	public void setkhuvucId(String khuvucId)
	{

		this.khuvucId = khuvucId;
	}

	public String getkhuvucId()
	{

		return this.khuvucId;
	}

	public void setkhuvuc(ResultSet khuvuc)
	{

		this.khuvuc = khuvuc;
	}

	public ResultSet getkhuvuc()
	{

		return this.khuvuc;
	}

	public void setnpp(ResultSet npp)
	{

		this.npp = npp;
	}

	public ResultSet getnpp()
	{

		return this.npp;
	}

	public void setgsbhId(String gsbhId)
	{

		this.gsbhId = gsbhId;
	}

	public String getgsbhId()
	{

		return this.gsbhId;
	}

	public void setgsbh(ResultSet gsbh)
	{

		this.gsbh = gsbh;
	}

	public ResultSet getgsbh()
	{

		return this.gsbh;
	}

	public void setsanphamId(String sanphamId)
	{
		this.sanphamId = sanphamId;
		System.out.println("spID " + this.sanphamId);
	}

	public String getsanphamId()
	{

		return this.sanphamId;
	}

	public void setsanpham(ResultSet sanpham)
	{

		this.sanpham = sanpham;
	}

	public ResultSet getsanpham()
	{

		return this.sanpham;
	}

	public void setdvdlId(String dvdlId)
	{

		this.dvdlId = dvdlId;
	}

	public String getdvdlId()
	{

		return this.dvdlId;
	}

	public void setdvdl(ResultSet dvdl)
	{

		this.dvdl = dvdl;
	}

	public ResultSet getdvdl()
	{

		return this.dvdl;
	}

	public void setFieldShow(String[] fieldShow)
	{

		this.FieldShow = fieldShow;
	}

	public String[] getFieldShow()
	{
		return this.FieldShow;

	}

	public void setFieldHidden(String[] fieldHidden)
	{

		this.FieldHidden = fieldHidden;
	}

	public String[] getFieldHidden()
	{

		return this.FieldHidden;
	}

	public void setngayton(String ngayton)
	{

		this.ngayton = ngayton;
	}

	public String getngayton()
	{

		return this.ngayton;
	}

	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public String getDate()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public String getdiscount()
	{

		return this.discount;
	}

	public String getpromotion()
	{

		return this.promotion;
	}

	public void setdiscount(String discount)
	{

		this.discount = discount;
	}

	public void setpromotion(String promotion)
	{

		this.promotion = promotion;
	}

	public String getvat()
	{

		return this.vat;
	}

	public String getlessday()
	{

		return this.lessday;
	}

	public String getmoreday()
	{

		return this.moreday;
	}

	public void setlessday(String lessday)
	{

		this.lessday = lessday;
	}

	public void setmoreday(String moreday)
	{

		this.moreday = moreday;
	}

	public void setDdkd(String ddkd)
	{
		this.ddkd = ddkd;
	}

	public String getDdkd()
	{

		return this.ddkd;
	}

	public void setRsddkd(ResultSet rs)
	{
		this.rsddkd = rs;
	}

	public ResultSet getRsddkd()
	{
		return this.rsddkd;
	}

	public void setUnit(String unit)
	{
		this.unit = unit;
	}

	public String getUnit()
	{

		return this.unit;
	}

	public void setGroupCus(String groupCus)
	{
		this.groupCus = groupCus;
	}

	public String getGroupCus()
	{

		return this.groupCus;
	}

	public void setPrograms(String programs)
	{
		this.programsId = programs;
	}

	public String getPrograms()
	{

		return this.programsId;
	}

	public void setRsPrograms(ResultSet RsPrograms)
	{
		this.rsPrograms = RsPrograms;
	}

	public ResultSet getRsPrograms()
	{

		return this.rsPrograms;
	}

	public void setDonViTinh(String donviTinh)
	{
		this.donviTinh = donviTinh;

	}

	public String getDonViTinh()
	{
		return this.donviTinh;
	}

	public void setFromMonth(String month)
	{
		this.fromMonth = month;
	}

	public String getFromMonth()
	{
		return this.fromMonth;
	}

	public void setToMonth(String month)
	{
		this.toMonth = month;
	}

	public String getToMonth()
	{
		return this.toMonth;
	}

	public void settype(String _type)
	{

		this.type = _type;
	}

	public String gettype()
	{

		return this.type;
	}

	public void setFromYear(String fromyear)
	{

		this.FromYear = fromyear;
	}

	public String getFromYear()
	{

		return this.FromYear;
	}

	public void setToYear(String toyear)
	{

		this.ToYear = toyear;
	}

	public String getToYear()
	{

		return this.ToYear;
	}

	public void SetNhoSPId(String nhomspid)
	{

		this.Nhospid = nhomspid;
	}

	public String GetNhoSPId()
	{

		return this.Nhospid;
	}

	public void setRsNhomSP(ResultSet rs)
	{

		this.RsNhomSp = rs;
	}

	public ResultSet GetRsNhomSP()
	{

		return this.RsNhomSp;
	}

	public void setRsQuyCach(ResultSet QuyCach)
	{

		this.RsQuyCach = QuyCach;
	}

	public ResultSet GetRsQuyCach()
	{

		return this.RsQuyCach;
	}

	public void SetQuyCachId(String _QuyCachId)
	{

		this.QuyCachId = _QuyCachId;
	}

	public String GetQuyCachId()
	{

		return this.QuyCachId;
	}

	public ResultSet getNhomrs()
	{
		return this.nrs;
	}

	public void setNhomrs(ResultSet nrs)
	{
		this.nrs = nrs;
	}

	public String getNspId()
	{
		return this.nspIds;
	}

	public void setNspId(String nspId)
	{
		this.nspIds = nspId;
	}


	public void CreaterRsKh()
	{

		String sql = "select pk_seq,ten from tinhthanh ";

		this.RsTinhthanh = db.get(sql);

		sql = "select pk_seq,ten from quanhuyen ";
		if (this.tinhthanhid.length() > 0)
		{
			sql = "select pk_seq,ten from quanhuyen  where tinhthanh_fk=" + this.tinhthanhid;
		}

		this.RsQuanhuyen = db.get(sql);

		sql = "select pk_seq,ten from tinhthanh  ";

		this.RsTinhthanh = db.get(sql);

		sql = "select pk_seq,diengiai as ten   from vitricuahang ";

		this.RsVitricuahang = db.get(sql);

		sql = "select pk_seq,diengiai as ten   from hangcuahang  ";

		this.Rshangcuahang = db.get(sql);

		sql = "select pk_seq,diengiai as ten   from loaicuahang ";

		this.Rsloaicuahang = db.get(sql);

	}


	public ResultSet GetRsTinhThanh()
	{

		return this.RsTinhthanh;
	}


	public ResultSet GetRsQuanHuyen()
	{

		return this.RsQuanhuyen;
	}


	public ResultSet GetRsHangCuahang()
	{

		return this.Rshangcuahang;
	}


	public ResultSet GetRsLoaicuahang()
	{

		return this.Rsloaicuahang;
	}


	public ResultSet GetRsVitriCuahang()
	{

		return this.RsVitricuahang;
	}


	public String GetIdTinhThanh()
	{

		return this.tinhthanhid;
	}


	public String GetIdQuanHuyen()
	{

		return this.quanhuyenid;
	}


	public String GetIdHangCuahang()
	{

		return this.hangcuahangid;
	}


	public String GetIdLoaicuahang()
	{

		return this.loaicuahangid;
	}


	public String GetIdVitriCuahang()
	{

		return this.vitricuahangid;
	}


	public void SetIdTinhThanh(String id)
	{

		this.tinhthanhid = id;
	}


	public void SetIdQuanHuyen(String id)
	{

		this.quanhuyenid = id;
	}


	public void SetIdHangCuahang(String id)
	{

		this.hangcuahangid = id;
	}


	public void SetIdLoaicuahang(String id)
	{

		this.loaicuahangid = id;
	}


	public void SetIdVitriCuahang(String id)
	{

		this.vitricuahangid = id;
	}


	public void setphanloai(String pl)
	{

		this.phanloai = pl;
	}


	public String getphanloai()
	{

		return this.phanloai;
	}


	public void setctkmtlId(String ctkmtlId)
	{

		this.Ctkmtichluy = ctkmtlId;
	}


	public String getctkmtlId()
	{

		return this.Ctkmtichluy;
	}


	public void setRskmtl(ResultSet rskmtl)
	{

		this.Rskmtl = rskmtl;
	}


	public ResultSet getRskmtl()
	{

		return this.Rskmtl;
	}


	public void setxemtheo(String xemtheo)
	{

		this.xemtheo = xemtheo;
	}


	public String getxemtheo()
	{

		return this.xemtheo;
	}

	public String getMuclay()
	{
		return muclay;
	}

	public void setMuclay(String muclay)
	{
		this.muclay = muclay;
	}


	public void TonKhoHangNgayPdf(Document document, ServletOutputStream outstream, IStockintransit obj, String query) throws SQLException, IOException
	{
		try
		{
			PdfWriter.getInstance(document, outstream);
			// document.setMargins(15.0f, 15.0f, 15.0f, 15.0f);

			document.setPageSize(PageSize.A4);
			document.open();

			// chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("C:\\WINDOWS\\Fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font header = new Font(bf, 14, Font.BOLD);
			Font bold = new Font(bf, 13, Font.BOLD);
			Font normal = new Font(bf, 10, Font.NORMAL);
			Font underline = new Font(bf, 12, Font.UNDERLINE);
			bold.setColor(BaseColor.RED);
			Paragraph ddh = new Paragraph("TỒN KHO HÀNG NGÀY", bold);
			ddh.setSpacingAfter(15);
			ddh.setAlignment(Element.ALIGN_CENTER);
			document.add(ddh);

			PdfPTable tableHead = new PdfPTable(4);
			tableHead.setWidthPercentage(100);
			tableHead.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableHead.setSpacingAfter(10);
			float[] with =
			{ 15.0f, 10.0f, 10f, 15.0f }; // set chieu rong cac columns
			tableHead.setWidths(with);

			PdfPCell cell1 = new PdfPCell(new Paragraph("Từ ngày: ", underline));
			PdfPCell cell2 = new PdfPCell(new Paragraph(obj.gettungay(), normal));
			PdfPCell cell3 = new PdfPCell(new Paragraph("Đến ngày: ", underline));
			PdfPCell cell4 = new PdfPCell(new Paragraph(obj.getdenngay(), normal));
			cell1.setBorder(0);
			cell2.setBorder(0);
			cell3.setBorder(0);
			cell4.setBorder(0);
			tableHead.addCell(cell1);
			tableHead.addCell(cell2);
			tableHead.addCell(cell3);
			tableHead.addCell(cell4);

			PdfPCell cell5 = new PdfPCell(new Paragraph("Ngày báo cáo: ", underline));
			PdfPCell cell6 = new PdfPCell(new Paragraph(getDateTime(), normal));
			PdfPCell cell7 = new PdfPCell(new Paragraph("Được tạo bởi: ", underline));
			PdfPCell cell8 = new PdfPCell(new Paragraph(obj.getuserTen(), normal));
			cell5.setBorder(0);
			cell6.setBorder(0);
			cell7.setBorder(0);
			cell8.setBorder(0);
			tableHead.addCell(cell5);
			tableHead.addCell(cell6);
			tableHead.addCell(cell7);
			tableHead.addCell(cell8);
			tableHead.setSpacingAfter(15);
			tableHead.setSpacingAfter(15);
			document.add(tableHead);

			// Table Content
			ResultSet rs = this.db.get(query);
			//	System.out.println("Query" + query);
			int socot = 6;
			PdfPTable table;
			table = null;
			PdfPCell cell = new PdfPCell();
			String nppPrev = "";
			String ngayPrev = "";
			double tongluong = 0;
			double tongtien = 0;
			double soluong = 0;
			double sotien = 0;
			float[] withs = new float[socot];
			for (int i = 0; i < socot; i++)
			{
				switch (i)
				{
				case 0:
					withs[i] = 10.0f;
					break;
				case 1:
					withs[i] = 15.0f;
					break;
				case 2:
					withs[i] = 15.0f;
					break;
				case 3:
					withs[i] = 35.0f;
					break;
				case 4:
					withs[i] = 20.0f;
					break;
				case 5:
					withs[i] = 20.0f;
					break;
				default:
					withs[i] = 15.0f;
					break;
				}
			}
			NumberFormat formatter = new DecimalFormat("#,###,###.###");
			int stt = 1;
			while (rs.next())
			{
				String nppCur = rs.getString("distcode");
				String ngayCur = rs.getString("date");
				if (!nppCur.equals(nppPrev))
				{
					if (table != null)
					{
						String[] cell_total =
						{"Ngày " + ngayPrev, "Tổng cộng ", formatter.format(tongluong), formatter.format(tongtien)};
						for (int i = 0; i < cell_total.length; i++)
						{
							cell = new PdfPCell(new Paragraph(cell_total[i], bold));
							if (i == 0)
							{
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setColspan(3);
							} else
							{
								cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							}
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setPadding(0.1f * CONVERT);
							table.addCell(cell);
						}
						document.add(table);
						ngayPrev="";
					}

					Paragraph p = new Paragraph("Nhà phân phối : " + nppCur + " - " + rs.getString("distributor"), normal);
					p.setSpacingBefore(0.3f * CONVERT);
					p.setSpacingAfter(0.2f * CONVERT);
					p.setAlignment(Element.ALIGN_LEFT);
					document.add(p);

					tongluong = 0;
					tongtien = 0;
					nppPrev = nppCur;
					table = new PdfPTable(withs.length);
					table.setWidths(withs);
					table.setWidthPercentage(100);

					String[] spTitles =
					{"STT", "Ngày", "Kho", "Sản phẩm", "Số lượng(THG)", "Thành tiền tồn"};
					for (int i = 0; i < spTitles.length; i++)
					{
						cell = new PdfPCell(new Paragraph(spTitles[i], bold));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setPadding(0.1f * CONVERT);
						table.addCell(cell);
					}
					//	System.out.println("[Npp 113]" + nppCur);
				}
				if (!ngayCur.equals(ngayPrev))
				{
					stt = 1;
					if (ngayPrev.length() > 0)
					{
						String[] cell_total =
						{"Ngày " + ngayPrev, "Tổng cộng ", formatter.format(tongluong), formatter.format(tongtien)};
						for (int i = 0; i < cell_total.length; i++)
						{
							cell = new PdfPCell(new Paragraph(cell_total[i], bold));
							if (i == 0)
							{
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setColspan(3);
							} else
							{
								cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							}
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setPadding(0.1f * CONVERT);
							table.addCell(cell);
						}
					}
					tongluong = 0;
					tongtien = 0;
					ngayPrev = ngayCur;
				}
				soluong = rs.getDouble("Quatity");
				sotien = rs.getDouble("amount");
				tongluong += soluong;
				tongtien += sotien;
				String[] spTitles = new String[]
				                               { Integer.valueOf(stt).toString(), ngayCur, rs.getString("WareHouse"), rs.getString("SkuCode") +"__"+ rs.getString("SKU"), formatter.format(soluong), formatter.format(sotien) };
				for (int i = 0; i < spTitles.length; i++)
				{
					cell = new PdfPCell(new Paragraph(spTitles[i], normal));
					if (i == 0 || i == 1)
					{
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					} else if (i == 2 || i == 3)
					{
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					} else
					{
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPadding(0.1f * CONVERT);
					table.addCell(cell);
				}
				stt++;
			}
			if (table != null)
			{
				String[] cell_total =
				{"Ngày " + ngayPrev, "Tổng cộng ", formatter.format(tongluong), formatter.format(tongtien)};
				for (int i = 0; i < cell_total.length; i++)
				{
					cell = new PdfPCell(new Paragraph(cell_total[i], bold));
					if (i == 0)
					{
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setColspan(3);
					} else
					{
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPadding(0.1f * CONVERT);
					table.addCell(cell);
				}
				document.add(table);
				document.close();
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void MuaHangPdf(Document document, ServletOutputStream outstream, IStockintransit obj, String query) throws SQLException, IOException
	{
		try
		{
			PdfWriter.getInstance(document, outstream);
			// document.setMargins(10.0f, 15.0f, 15.0f, 15.0f);

			document.setPageSize(PageSize.A4);
			document.open();

			// chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("C:\\WINDOWS\\Fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font header = new Font(bf, 14, Font.BOLD);
			Font bold = new Font(bf, 13, Font.BOLD);
			Font normal = new Font(bf, 10, Font.NORMAL);
			Font underline = new Font(bf, 12, Font.UNDERLINE);
			bold.setColor(BaseColor.RED);
			Paragraph ddh = new Paragraph("MUA HÀNG THEO THÁNG", bold);
			if (obj.gettungay().length() > 0)
			{
				ddh = new Paragraph("MUA HÀNG THEO NGÀY", bold);
			}
			ddh.setSpacingAfter(15);
			ddh.setAlignment(Element.ALIGN_CENTER);
			document.add(ddh);

			PdfPTable tableHead = new PdfPTable(4);
			tableHead.setWidthPercentage(100);
			tableHead.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableHead.setSpacingAfter(10);
			float[] with =
			{ 15.0f, 10.0f, 10f, 15.0f }; // set chieu rong cac columns
			tableHead.setWidths(with);

			PdfPCell cell1 = new PdfPCell(new Paragraph("Từ ngày", underline));

			PdfPCell cell2 = new PdfPCell(new Paragraph(obj.gettungay(), normal));

			PdfPCell cell3 = new PdfPCell(new Paragraph("Đến ngày ", underline));

			PdfPCell cell4 = new PdfPCell(new Paragraph(obj.getdenngay(), normal));
			if (obj.getFromMonth().length() > 0)
			{
				cell1 = new PdfPCell(new Paragraph("Từ tháng ", underline));
				cell2 = new PdfPCell(new Paragraph(obj.getFromMonth(), normal));
				cell3 = new PdfPCell(new Paragraph("Đến tháng ", underline));
				cell4 = new PdfPCell(new Paragraph(obj.getToMonth(), normal));
			}

			cell1.setBorder(0);
			cell2.setBorder(0);
			cell3.setBorder(0);
			cell4.setBorder(0);
			tableHead.addCell(cell1);
			tableHead.addCell(cell2);
			tableHead.addCell(cell3);
			tableHead.addCell(cell4);

			PdfPCell cell5 = new PdfPCell(new Paragraph("Ngày báo cáo: ", underline));
			PdfPCell cell6 = new PdfPCell(new Paragraph(getDateTime(), normal));
			PdfPCell cell7 = new PdfPCell(new Paragraph("Được tạo bởi: ", underline));
			PdfPCell cell8 = new PdfPCell(new Paragraph(obj.getuserTen(), normal));
			cell5.setBorder(0);
			cell6.setBorder(0);
			cell7.setBorder(0);
			cell8.setBorder(0);
			tableHead.addCell(cell5);
			tableHead.addCell(cell6);
			tableHead.addCell(cell7);
			tableHead.addCell(cell8);
			tableHead.setSpacingAfter(15);
			tableHead.setSpacingAfter(15);
			document.add(tableHead);

			// Table Content
			ResultSet rs = this.db.get(query);
			//		System.out.println("____[Init Pdf]___" + query);

			int socot = 7;
			PdfPTable table;
			table = null;
			PdfPCell cell = new PdfPCell();
			String nppPrev = "";
			String ngayPrev = "";
			String thoigian = "Ngày  ";
			double tongluong = 0;
			double tongtien = 0;
			double soluong = 0;
			double sotien = 0;
			float[] withs = new float[socot];
			for (int i = 0; i < socot; i++)
			{
				switch (i)
				{
				case 0:
					withs[i] = 10.0f;
					break;
				case 1:
					withs[i] = 20.0f;
					break;
				case 2:
					withs[i] = 20.0f;
					break;
				case 3:
					withs[i] = 25.0f;
					break;
				case 4:
					withs[i] = 40.0f;
					break;
				case 5:
					withs[i] = 25.0f;
					break;
				default:
					withs[i] = 30.0f;
					break;
				}
			}
			NumberFormat formatter = new DecimalFormat("#,###,###.###");
			int stt = 1;
			while (rs.next())
			{
				String nppCur = rs.getString("distributor_code");
				String ngayCur = rs.getString("ngaynhan");

				if (obj.getFromMonth().length() > 0)
				{
					ngayCur = rs.getString("ngaynhan").substring(0, rs.getString("ngaynhan").length() - 3);
					thoigian = "Tháng  ";
				}
				if (!nppCur.equals(nppPrev))
				{
					if (table != null)
					{
						String[] cell_total =
						{thoigian + ngayPrev, "Tổng cộng ", formatter.format(tongluong), formatter.format(tongtien)};
						for (int i = 0; i < cell_total.length; i++)
						{
							cell = new PdfPCell(new Paragraph(cell_total[i], bold));
							if (i == 0)
							{
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setColspan(3);
							} else
							{
								cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							}
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setPadding(0.1f * CONVERT);
							table.addCell(cell);
						}
						tongluong=0;
						tongtien=0;
						ngayPrev="";
						document.add(table);
					}

					Paragraph p = new Paragraph("Nhà phân phối : " + nppCur + " - " + rs.getString("distributor"), normal);
					p.setSpacingBefore(0.3f * CONVERT);
					p.setSpacingAfter(0.2f * CONVERT);
					p.setAlignment(Element.ALIGN_LEFT);
					document.add(p);

					tongluong = 0;
					tongtien = 0;
					nppPrev = nppCur;
					table = new PdfPTable(withs.length);
					table.setWidths(withs);
					table.setWidthPercentage(100);

					String[] spTitles =
					{"STT", "Số hóa đơn", "Ngày nhận", "Mã sản phẩm", "Tên sản phẩm", "Số lượng(THG)", "Thành tiền"};
					for (int i = 0; i < spTitles.length; i++)
					{
						cell = new PdfPCell(new Paragraph(spTitles[i], bold));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setPadding(0.1f * CONVERT);
						table.addCell(cell);
					}
					System.out.println("[Npp 113]" + nppCur);
				}
				if (!ngayCur.equals(ngayPrev))
				{
					if (ngayPrev.length() > 0)
					{
						String[] cell_total =
						{thoigian + ngayPrev, "Tổng cộng ", formatter.format(tongluong), formatter.format(tongtien)};
						for (int i = 0; i < cell_total.length; i++)
						{
							cell = new PdfPCell(new Paragraph(cell_total[i], bold));
							if (i == 0)
							{
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setColspan(4);
							} else
							{
								cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							}
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setPadding(0.1f * CONVERT);
							table.addCell(cell);
						}
					}
					stt = 1;
					tongluong=0;
					tongtien=0;
					ngayPrev = ngayCur;
				}
				soluong = rs.getDouble("peice");
				int qc1 = rs.getInt("qc1");
				int qc2 = rs.getInt("qc2");
				double slThg = soluong / qc1 / qc2;
				String trangthai = rs.getString("TrangThai");
				if (trangthai.equals("0"))
					trangthai = "Chưa nhận hàng";
				else
					trangthai = "Đã nhận hàng";

				sotien = rs.getDouble("amount");
				tongluong += slThg;
				tongtien += sotien;
				String[] spTitles = new String[]
				                               { Integer.valueOf(stt).toString(), rs.getString("docno"), rs.getString("ngaynhan"), rs.getString("SKU_code"), rs.getString("SKU"), formatter.format(slThg), formatter.format(sotien) };

				for (int i = 0; i < spTitles.length; i++)
				{
					cell = new PdfPCell(new Paragraph(spTitles[i], normal));
					if (i <= 4)
					{
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
					} else
					{
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPadding(0.1f * CONVERT);
					table.addCell(cell);
				}

				stt++;
			}

			if (table != null)
			{
				String[] cell_total =
				{thoigian + ngayPrev, "Tổng cộng ", formatter.format(tongluong), formatter.format(tongtien)};
				for (int i = 0; i < cell_total.length; i++)
				{
					cell = new PdfPCell(new Paragraph(cell_total[i], bold));
					if (i == 0)
					{
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						cell.setColspan(4);
					} else
					{
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPadding(0.1f * CONVERT);
					table.addCell(cell);
				}
				rs.close();
				document.add(table);
				document.close();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static String FILE = "D:/temp/FirstPdf.pdf";

	public static void main(String[] arg) throws DocumentException, SQLException, IOException
	{
		Stockintransit obj = new Stockintransit();
		obj.settungay("2013-07-01");
		obj.setdenngay("2013-07-16");
		obj.setuserTen("AAA");
		obj.setuserId("100345");

		Document document = new Document();
		//FileOutputStream outstream = new FileOutputStream(FILE);

		//obj.XuatNhapTonPdf(document, outstream, obj, "", "1");

	}

	public PdfPTable getTable(Date day) throws SQLException, DocumentException, IOException
	{
		PdfPTable table = new PdfPTable(new float[]
		                                          { 2, 1, 2, 5, 1 });
		table.setWidthPercentage(100f);
		table.getDefaultCell().setUseAscender(true);
		table.getDefaultCell().setUseDescender(true);
		table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
		for (int i = 0; i < 2; i++)
		{
			table.addCell("Location");
			table.addCell("Time");
			table.addCell("Run Length");
			table.addCell("Title");
			table.addCell("Year");
		}
		table.getDefaultCell().setBackgroundColor(null);
		table.setHeaderRows(2);
		table.setFooterRows(1);
		return table;
	}


	public ResultSet getNhomspRs()
	{

		return this.nhomRs;
	}


	public void setNhomspRs(ResultSet nspRs)
	{

		this.nhomRs = nspRs;
	}

	public void BanHangPdf(Document document, ServletOutputStream outstream, IStockintransit obj, String query, String level) throws SQLException, IOException
	{
		try
		{
			PdfWriter.getInstance(document, outstream);
			// document.setMargins(15.0f, 15.0f, 15.0f, 15.0f);
			document.setPageSize(PageSize.A4);
			document.open();

			// chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("C:\\WINDOWS\\Fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font header = new Font(bf, 14, Font.BOLD);
			Font bold = new Font(bf, 13, Font.BOLD);
			Font normal = new Font(bf, 10, Font.NORMAL);
			Font underline = new Font(bf, 12, Font.UNDERLINE);
			bold.setColor(BaseColor.RED);
			Paragraph ddh = new Paragraph("BÁN HÀNG THEO THÁNG", bold);
			if (obj.gettungay().length() > 0)
			{
				ddh = new Paragraph("BÁN HÀNG THEO NGÀY", bold);
			}
			ddh.setSpacingAfter(15);
			ddh.setAlignment(Element.ALIGN_CENTER);
			document.add(ddh);

			PdfPCell cell1 = new PdfPCell(new Paragraph("Từ ngày", underline));

			PdfPCell cell2 = new PdfPCell(new Paragraph(obj.gettungay(), normal));

			PdfPCell cell3 = new PdfPCell(new Paragraph("Đến ngày ", underline));

			PdfPCell cell4 = new PdfPCell(new Paragraph(obj.getdenngay(), normal));
			if (obj.getFromMonth().length() > 0)
			{
				cell1 = new PdfPCell(new Paragraph("Từ tháng ", underline));
				cell2 = new PdfPCell(new Paragraph(obj.getFromMonth(), normal));
				cell3 = new PdfPCell(new Paragraph("Đến tháng ", underline));
				cell4 = new PdfPCell(new Paragraph(obj.getToMonth(), normal));
			}

			PdfPTable tableHead = new PdfPTable(4);
			tableHead.setWidthPercentage(100);
			tableHead.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableHead.setSpacingAfter(10);
			float[] with =
			{ 15.0f, 10.0f, 10f, 15.0f }; // set chieu rong cac columns
			tableHead.setWidths(with);

			cell1.setBorder(0);
			cell2.setBorder(0);
			cell3.setBorder(0);
			cell4.setBorder(0);
			tableHead.addCell(cell1);
			tableHead.addCell(cell2);
			tableHead.addCell(cell3);
			tableHead.addCell(cell4);

			PdfPCell cell5 = new PdfPCell(new Paragraph("Ngày báo cáo: ", underline));
			PdfPCell cell6 = new PdfPCell(new Paragraph(getDateTime(), normal));
			PdfPCell cell7 = new PdfPCell(new Paragraph("Được tạo bởi: ", underline));
			PdfPCell cell8 = new PdfPCell(new Paragraph(obj.getuserTen(), normal));
			cell5.setBorder(0);
			cell6.setBorder(0);
			cell7.setBorder(0);
			cell8.setBorder(0);
			tableHead.addCell(cell5);
			tableHead.addCell(cell6);
			tableHead.addCell(cell7);
			tableHead.addCell(cell8);
			tableHead.setSpacingAfter(15);
			tableHead.setSpacingAfter(15);
			document.add(tableHead);
			ResultSet rs;
			rs = this.db.get(query);
			//	System.out.println("[Init Pdf Ban Hang] " + query);
			PdfPTable table;
			table = null;
			PdfPCell cell = new PdfPCell();
			String nppPrev = "";
			String ngayPrev = "";
			String thoigian = "Ngày  ";
			double tongluong = 0;
			double tongtien = 0;
			double soluong = 0;
			double sotien = 0;
			float[] TABLE_DISTRIBUTOR_WIDTH =
			{ 8.0f, 35.0f, 15.0f, 15.0f, 25.0f };
			float[] TABLE_SALESRES_WIDTH =
			{ 8.0f, 25.0f, 35.0f, 17.0f, 12.0f, 20.0f };
			float[] TABLE_CUSTOMER_WIDTH =
			{ 8.0f, 42.0f, 35.0f, 15.0f, 15.0f, 20.0f };

			NumberFormat formatter = new DecimalFormat("#,###,###.###");
			;
			int stt = 1;
			while (rs.next())
			{
				String nppCur = rs.getString("distcode");

				String ngayCur = rs.getString("ngay");

				if (obj.getFromMonth().length() > 0)
				{
					thoigian = "Tháng  ";
				}

				if (!nppCur.equals(nppPrev))
				{
					if (table != null)
					{
						String[] cell_total =
						{thoigian + ngayPrev, "Tổng cộng ", formatter.format(tongluong), formatter.format(tongtien)};
						for (int i = 0; i < cell_total.length; i++)
						{
							cell = new PdfPCell(new Paragraph(cell_total[i], bold));
							if (i == 0)
							{
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								if (level.equals("0"))
								{
									cell.setColspan(2);
								} else if (level.equals("1"))
									cell.setColspan(3);
								else
									cell.setColspan(3);
							} else
							{
								cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							}
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setPadding(0.1f * CONVERT);
							table.addCell(cell);
						}
						document.add(table);
					}

					Paragraph p = new Paragraph("Nhà phân phối : " + nppCur + " - " + rs.getString("distributor"), normal);
					p.setSpacingBefore(0.3f * CONVERT);
					p.setSpacingAfter(0.2f * CONVERT);
					p.setAlignment(Element.ALIGN_LEFT);
					document.add(p);

					tongluong = 0;
					tongtien = 0;
					nppPrev = nppCur;
					if (level.equals("0"))
					{
						table = new PdfPTable(TABLE_DISTRIBUTOR_WIDTH.length);
						table.setWidths(TABLE_DISTRIBUTOR_WIDTH);
						table.setWidthPercentage(100);
						String[] spTitles =
						{"STT", "Sản phẩm", thoigian, "Số lượng(THG)", "Thành tiền"};
						for (int i = 0; i < spTitles.length; i++)
						{
							cell = new PdfPCell(new Paragraph(spTitles[i], bold));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setPadding(0.1f * CONVERT);
							table.addCell(cell);
						}
					} else if (level.equals("1"))
					{
						table = new PdfPTable(TABLE_SALESRES_WIDTH.length);
						table.setWidths(TABLE_SALESRES_WIDTH);
						table.setWidthPercentage(100);
						String[] spTitles =
						{"STT", "Đại diện", "Sản phẩm", thoigian, "Số lượng(THG)", "Thành tiền"};
						for (int i = 0; i < spTitles.length; i++)
						{
							cell = new PdfPCell(new Paragraph(spTitles[i], bold));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setPadding(0.1f * CONVERT);
							table.addCell(cell);
						}
					} else
					{
						table = new PdfPTable(TABLE_CUSTOMER_WIDTH.length);
						table.setWidths(TABLE_CUSTOMER_WIDTH);
						table.setWidthPercentage(100);
						String[] spTitles =
						{"STT", "Khách hàng", "Sản phẩm", thoigian, "Số lượng(THG)", "Thành tiền"};
						for (int i = 0; i < spTitles.length; i++)
						{
							cell = new PdfPCell(new Paragraph(spTitles[i], bold));
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setPadding(0.1f * CONVERT);
							table.addCell(cell);
						}
					}
					//	System.out.println("[Npp 113]" + nppCur);
				}
				soluong = rs.getDouble("quantity");
				sotien = rs.getDouble("amount");
				tongluong += soluong;
				tongtien += sotien;
				if (!ngayCur.equals(ngayPrev))
				{
					if (ngayPrev.length() > 0)
					{
						stt = 1;
						String[] cell_total =
						{thoigian + ngayPrev, "Tổng cộng ", formatter.format(tongluong), formatter.format(tongtien)};
						for (int i = 0; i < cell_total.length; i++)
						{
							cell = new PdfPCell(new Paragraph(cell_total[i], bold));
							if (i == 0)
							{
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								if (level.equals("0"))
								{
									cell.setColspan(2);
								} else if (level.equals("1"))
									cell.setColspan(3);
								else
									cell.setColspan(3);
							} else
							{
								cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							}
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setPadding(0.1f * CONVERT);
							table.addCell(cell);
						}
						tongluong = 0;
						tongtien = 0;
					}

					ngayPrev = ngayCur;
				}

				if (level.equals("0"))
				{
					String[] spTitles = new String[]
					                               { Integer.valueOf(stt).toString(), rs.getString("SKU"), rs.getString("ngay"), formatter.format(soluong), formatter.format(sotien) };
					for (int i = 0; i < spTitles.length; i++)
					{
						cell = new PdfPCell(new Paragraph(spTitles[i], normal));
						if (i <= 2)
						{
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						} else
						{
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						}
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setPadding(0.1f * CONVERT);
						table.addCell(cell);
					}

				} else if (level.equals("1"))
				{
					String[] spTitles = new String[]
					                               { Integer.valueOf(stt).toString(), rs.getString("salesrep"), rs.getString("SKU"), rs.getString("ngay"), formatter.format(soluong), formatter.format(sotien) };
					for (int i = 0; i < spTitles.length; i++)
					{
						cell = new PdfPCell(new Paragraph(spTitles[i], normal));
						if (i <= 3)
						{
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						} else
						{
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						}
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setPadding(0.1f * CONVERT);
						table.addCell(cell);
					}

				} else if (level.equals("2"))
				{
					String[] spTitles = new String[]
					                               { Integer.valueOf(stt).toString(), rs.getString("customer"), rs.getString("SKU"), rs.getString("ngay"), formatter.format(soluong), formatter.format(sotien) };
					for (int i = 0; i < spTitles.length; i++)
					{
						cell = new PdfPCell(new Paragraph(spTitles[i], normal));
						if (i < 4)
						{
							cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						} else
						{
							cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						}
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setPadding(0.1f * CONVERT);
						table.addCell(cell);
					}
				}
				stt++;
			}
			if (table != null)
			{
				String[] cell_total =
				{thoigian + ngayPrev, "Tổng cộng ", formatter.format(tongluong), formatter.format(tongtien)};
				for (int i = 0; i < cell_total.length; i++)
				{
					cell = new PdfPCell(new Paragraph(cell_total[i], bold));
					if (i == 0)
					{
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						if (level.equals("0"))
						{
							cell.setColspan(2);
						} else if (level.equals("1"))
							cell.setColspan(3);
						else
							cell.setColspan(3);
					} else
					{
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					}
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setPadding(0.1f * CONVERT);
					table.addCell(cell);
				}
				document.add(table);
				document.close();
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void XuatNhapTonPdf(Document document, ServletOutputStream outstream, IStockintransit obj, String query, String giatinh) throws SQLException, IOException
	{
		try
		{
			PdfWriter.getInstance(document, outstream);
			//	document.setMargins(15.0f, 15.0f, 15.0f, 15.0f);
			document.setPageSize(PageSize.A2);
			document.open();

			// chi dinh BaseFont.IDENTITY_H de co the go tieng viet
			BaseFont bf = BaseFont.createFont("C:\\WINDOWS\\Fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font header = new Font(bf, 14, Font.BOLD);
			Font bold = new Font(bf, 13, Font.BOLD);
			Font normal = new Font(bf, 10, Font.NORMAL);
			Font underline = new Font(bf, 12, Font.UNDERLINE);
			bold.setColor(BaseColor.RED);
			Paragraph ddh = new Paragraph("XUẤT NHẬP TỒN", bold);
			ddh.setSpacingAfter(15);
			ddh.setAlignment(Element.ALIGN_CENTER);
			document.add(ddh);

			PdfPTable tableHead = new PdfPTable(4);
			tableHead.setWidthPercentage(100);
			tableHead.setHorizontalAlignment(Element.ALIGN_LEFT);
			tableHead.setSpacingAfter(10);
			float[] with =
			{ 15.0f, 10.0f, 10f, 15.0f }; // set chieu rong cac columns
			tableHead.setWidths(with);

			PdfPCell cell1 = new PdfPCell(new Paragraph("Từ ngày: ", underline));
			PdfPCell cell2 = new PdfPCell(new Paragraph(obj.gettungay(), normal));
			PdfPCell cell3 = new PdfPCell(new Paragraph("Đến ngày: ", underline));
			PdfPCell cell4 = new PdfPCell(new Paragraph(obj.getdenngay(), normal));
			cell1.setBorder(0);
			cell2.setBorder(0);
			cell3.setBorder(0);
			cell4.setBorder(0);
			tableHead.addCell(cell1);
			tableHead.addCell(cell2);
			tableHead.addCell(cell3);
			tableHead.addCell(cell4);

			PdfPCell cell5 = new PdfPCell(new Paragraph("Ngày báo cáo: ", underline));
			PdfPCell cell6 = new PdfPCell(new Paragraph(getDateTime(), normal));
			PdfPCell cell7 = new PdfPCell(new Paragraph("Được tạo bởi: ", underline));
			PdfPCell cell8 = new PdfPCell(new Paragraph(obj.getuserTen(), normal));
			cell5.setBorder(0);
			cell6.setBorder(0);
			cell7.setBorder(0);
			cell8.setBorder(0);
			tableHead.addCell(cell5);
			tableHead.addCell(cell6);
			tableHead.addCell(cell7);
			tableHead.addCell(cell8);
			tableHead.setSpacingAfter(15);
			tableHead.setSpacingAfter(15);
			document.add(tableHead);

			// Table Content

			String[] param = new String[13];
			param[0] = obj.getnppId().equals("") ? null : obj.getnppId();
			param[1] = obj.gettungay();
			param[2] = obj.getdenngay();
			param[3] = obj.getkenhId().equals("") ? null : obj.getkenhId();
			param[4] = obj.getnhanhangId().equals("") ? null : obj.getnhanhangId();
			param[5] = obj.getchungloaiId().equals("") ? null : obj.getchungloaiId();
			param[6] = obj.getdvkdId().equals("") ? null : obj.getdvkdId();
			param[7] = obj.getkhoId() == "" ? null : obj.getkhoId();
			param[8] = obj.getkhuvucId() == "" ? null : obj.getkhuvucId();
			param[9] = obj.getvungId() == "" ? null : obj.getvungId();
			param[10] = obj.getvat().equals("") ? null : obj.getvat();
			param[11] = obj.getuserId();
			param[12] = "1";// LAY BAO CAO CENTER

			ResultSet rs = null;

			if (giatinh.equals("1"))
			{
				rs = db.getRsByPro("REPORT_XUATNHAPTON", param);
			} else
			{
				if (giatinh.equals("2"))
				{
					rs = db.getRsByPro("REPORT_XUATNHAPTON_GIABANLECHUAN", param);
				} else
				{
					rs = db.getRsByPro("REPORT_XUATNHAPTON_GIATON", param);
				}
			}
			//	System.out.println("Query" + query);

			PdfPTable table;
			table = null;
			PdfPCell cell = new PdfPCell();
			String nppPrev = "";
			String ngayPrev = "";
			String spPrev = "";

			double tongluong = 0;
			double tongtien = 0;
			double soluong = 0;
			double sotien = 0;

			Hashtable<String, Integer> getColum = hbGetColumn();

			float[] withs =
			{ 8.0f, 15.0f, 35.0f, 20.0f, 20.0f, 20.0f, 20.0f, 20.0f, 20.0f, 20.0f, 20.0f, 20.0f, 20.0f, 20.0f, 20.0f };
			NumberFormat formatter = new DecimalFormat("#,###,###.###");
			int stt = 0;
			String[] spTitles = new String[15];

			while (rs.next())
			{

				//System.out.println(rs.getString("TYPE") + rs.getString("masp"));

				String nppCur = rs.getString("nppId");
				String ngayCur = rs.getString("TYPE");
				String spId = rs.getString("masp");
				if (!nppCur.equals(nppPrev))
				{
					if (table != null)
					{
						String[] cell_total =
						{"", "Tổng cộng ", formatter.format(tongluong), formatter.format(tongtien)};
						for (int i = 0; i < cell_total.length; i++)
						{
							cell = new PdfPCell(new Paragraph(cell_total[i], bold));
							if (i == 0)
							{
								cell.setHorizontalAlignment(Element.ALIGN_LEFT);
								cell.setColspan(0);
							} else
							{
								cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
							}
							cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
							cell.setPadding(0.1f * CONVERT);
							table.addCell(cell);
						}
						document.add(table);
					}

					Paragraph p = new Paragraph("Nhà phân phối : " + nppCur + " - " + rs.getString("npp"), normal);
					p.setSpacingBefore(0.3f * CONVERT);
					p.setSpacingAfter(0.2f * CONVERT);
					p.setAlignment(Element.ALIGN_LEFT);
					document.add(p);

					tongluong = 0;
					tongtien = 0;
					nppPrev = nppCur;
					table = new PdfPTable(withs.length);
					table.setWidths(withs);
					table.setWidthPercentage(100);

					String[] spTitles1 =
					{"STT", "Kho", "Sản phẩm", "Tồn đầu", "Nhập hàng bán", "Nhập hàng KM", "Xuất hàng bán", "Xuất hàng KM", "Hàng trả về NPP", "KH Trả hàng bán", "KH Trả hàng KM", "Mua hàng NPP", "Bán cho NPP", "Điều chỉnh", "Tồn cuối"};
					for (int i = 0; i < spTitles1.length; i++)
					{
						cell = new PdfPCell(new Paragraph(spTitles1[i], bold));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setPadding(0.1f * CONVERT);
						table.addCell(cell);
					}
					//System.out.println("[Npp 113]" + nppCur);
				}

				soluong = rs.getDouble("Quantily");
				sotien = rs.getDouble("amount");
				tongluong += soluong;
				tongtien += sotien;
				if (stt == 0)
				{
					spPrev = spId;
					stt++;
					spTitles[0] = stt + "";
					spTitles[1] = rs.getString("kho");
					spTitles[2] = rs.getString("MASP")+"_"+rs.getString("tensp");
				}

				if (stt != 0 && !spId.trim().equals(spPrev.trim()))
				{
					for (int i = 0; i < spTitles.length; i++)
					{
						cell = new PdfPCell(new Paragraph(spTitles[i], normal));
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell.setPadding(0.1f * CONVERT);
						table.addCell(cell);
					}
					spTitles = new String[15];
					spTitles[0] = stt + "";
					spTitles[1] = rs.getString("kho");
					spTitles[2] = rs.getString("MASP")+"_"+rs.getString("tensp");
					spPrev = spId;
					stt++;
				}
				if (getColum.get(ngayCur) != null)
				{
					spTitles[getColum.get(ngayCur)] = formatter.format(soluong);
				}
			}

			for (int i = 0; i < spTitles.length; i++)
			{
				cell = new PdfPCell(new Paragraph(spTitles[i], normal));
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setPadding(0.1f * CONVERT);
				table.addCell(cell);
			}
			document.add(table);
			document.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private Hashtable<String, Integer> hbGetColumn()
	{
		Hashtable<String, Integer> ht = new Hashtable<String, Integer>();
		ht.put("1.Tồn đầu", 3);
		ht.put("2.Nhập hàng bán", 4);
		ht.put("3.Nhập hàng KM", 5);
		ht.put("4.Xuất hàng bán", 6);
		ht.put("5.Xuất hàng KM", 7);
		ht.put("6.Hàng trả về NPP", 8);
		ht.put("7.1.KH Trả hàng bán", 9);
		ht.put("7.2.KH Trả hàng KM", 10);
		ht.put("7.3.Mua Hang NPP", 11);
		ht.put("7.3.Ban Cho NPP", 12);
		ht.put("8.Điều chỉnh", 13);
		ht.put("9.Tồn cuối", 14);
		return ht;
	}
	public String getTieuchiId()
	{
		return tieuchiId;
	}

	public void setTieuchiId(String tieuchiId)
	{
		this.tieuchiId = tieuchiId;
	}
	public ResultSet getTieuchiRs()
	{
		return tieuchiRs;
	}

	public void setTieuchiRs(ResultSet tieuchiRs)
	{
		this.tieuchiRs = tieuchiRs;
	}


	public String getNgayketthucctkm() {

		return Ngayketthucctkm;
	}


	public void setNgayketthucctkm(String _Ngayketthucctkm) {

		this.Ngayketthucctkm=_Ngayketthucctkm;

	}
	public String getHangDiDuong()
	{
		return HangDiDuong;
	}

	public void setHangDiDuong(String hangDiDuong)
	{
		HangDiDuong = hangDiDuong;
	}


	public String getNganhHangId() 
	{

		return this.nganhHangId;
	}


	public void SetNganhHangId(String nganhhangId) 
	{
		this.nganhHangId=nganhhangId;		
	}


	public ResultSet getNganhHangRs() 
	{

		return this.nganhHangRs;
	}


	public ResultSet setNganhHangRs(ResultSet nganhhangRs) 
	{
		return this.nganhHangRs=nganhhangRs;
	}

	String HoaDonKmDb;

	public String getHoaDonKmDb()
	{
		return HoaDonKmDb;
	}

	public void setHoaDonKmDb(String hoaDonKmDb)
	{
		HoaDonKmDb = hoaDonKmDb;
	}

	String tbhId;

	public String getTbhId()
	{
		return tbhId;
	}

	public void setTbhId(String tbhId)
	{
		this.tbhId = tbhId;
	}


	public void setkh(ResultSet kh) {
		this.khrs=kh;

	}


	public ResultSet kh() {

		return this.khrs;
	}


	public void setkhId(String khId) {
		this.khId=khId;

	}


	public String getkhId() {

		return this.khId;
	}


	public void setkhTen(String khTen) {

		this.khTen=khTen;
	}


	public String getkhTen() {

		return this.khTen;
	}


	public ResultSet getkh() {

		return this.khrs;
	}


	public String getNppId() {

		return this.nppID;
	}


	public void setNppId(String nppId) {

		this.nppID=nppId;
	}


	public ResultSet getNppRs() {

		return this.nppRs;
	}


	public void setNppRs(ResultSet nppRs) {

		this.nppRs=nppRs;
	}


	public String getActiveTab() {

		return this.activeTab;
	}


	public void setActiveTab(String active) {
		this.activeTab= active ;

	}


	public ResultSet getETCRs() {

		return this.EtcRs;
	}


	public void setETCRS(ResultSet ETCRs) {

		this.EtcRs= ETCRs;
	}


	public ResultSet getOTCRs() {		
		return this.OtcRs;
	}


	public void setOTCRS(ResultSet OTCRs) {		
		this.OtcRs = OTCRs;
	}


	public ResultSet getKMRs() {

		return this.kmRs;
	}


	public void setKMRS(ResultSet KMRs) {

		this.kmRs= KMRs;
	}

	public void searquery( String query)
	{
		
		this.RsSearch=db.get(query);
	}
	
	public void searchQuery_ETC(String searchquery) {
		String sql = "";
		System.out.println("câu query: ETC" + searchquery);
		if(searchquery.length() > 0)
			sql = searchquery;	
		this.EtcRs= db.get(sql);
	}


	public void searchQuery_OTC(String searchquery) {
		String sql = "";

		System.out.println("câu query OTC: " + searchquery);
		if(searchquery.length() > 0)
			sql = searchquery;	
		this.OtcRs= db.get(sql);
	}

	public void searchQuery_KM(String searchquery) {
		String sql = "";

		if(searchquery.length() > 0)
			sql = searchquery;	
		this.kmRs= db.get(sql);
	}


	public String getNvgnId() {

		return this.nvgnId;
	}


	public void setNvgnId(String nvgnId) {

		this.nvgnId=nvgnId;
	}

	ResultSet nvgnRs;
	public ResultSet getNvgnRs() {

		return this.nvgnRs;
	}


	public void setNvgnRs(ResultSet nvgnRs) {

		this.nvgnRs=nvgnRs;
	}


	public void setTotal_Query(String searchquery) 
	{
		System.out.println("___Total_Query__" + searchquery);
		this.totalRs= db.get(searchquery);

	}

	ResultSet totalRs;
	public ResultSet getTotalRs()
	{
		return totalRs;
	}
	public void setTotalRs(ResultSet totalRs)
	{
		this.totalRs=totalRs;
	}

	String trangthai;

	public String getTrangthai()
	{
		return trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}


	public String getTtId()
	{
		return ttId;
	}
	public void setTtId(String ttId)
	{
		this.ttId = ttId;
	}


	public ResultSet getTtRs()
	{
		return ttRs;
	}
	public void setTtRs(ResultSet ttRs)
	{
		this.ttRs = ttRs;
	}

	String cndt,kh;
	private String coHoadon;
	private String spId;
	private ResultSet SpRs;
	private ResultSet rsBaocao;
	private String laytheo;

	public String 	getMucCN_DT()
	{
		return 	this.cndt;
	}

	public void setMucCN_DT(String cndt)
	{
		this.cndt=cndt;
	}

	public String getMuc_KhachHang()
	{
		return 	this.kh;
	}

	public void setMuc_KhachHang(String cndt)
	{
		this.kh=cndt;
	}

	public ResultSet getTinhthanh()
	{
		return tinhthanh;
	}

	public void setTinhthanh(ResultSet tinhthanh)
	{
		this.tinhthanh = tinhthanh;
	}


	public String getCoHoadon() {

		return this.coHoadon;
	}


	public void setCoHoadon(String value) {
		this.coHoadon = value;
	}


	public ResultSet getSpRs() {

		return this.SpRs;
	}


	public void setSpId(String spid) {
		this.spId = spid;
	}


	public String getSpId() {
		return this.spId;
	}


	public void setRSBaocao(ResultSet rs) {
		this.rsBaocao = rs;
	}


	public ResultSet getRSBaocao() {
		return this.rsBaocao;
	}


	public String getLaytheo() {
		return this.laytheo;
	}


	public void setLaytheo(String value) {
		this.laytheo = value;
	}

	String quy;

	public String getQuy()
	{
		return quy;
	}

	public void setQuy(String quy)
	{
		this.quy = quy;
	}


	public void searchQuery_HDKhac(String searchquery) {
		String sql = "";
		System.out.println("câu query: HDKhac " + searchquery);
		if(searchquery.length() > 0)
			sql = searchquery;	
		this.HdKhacRs= db.get(sql);
	}


	public ResultSet getHdKhacRs() {

		return this.HdKhacRs;
	}


	public void setHdKhacRs(ResultSet HdKhacRs) {

		this.HdKhacRs=HdKhacRs;
	}
	
	ResultSet dataRs ;
	public void setDataRs(String query)
	{
		this.dataRs = db.get(query);
	}
	public ResultSet getDataRs()
	{
		return this.dataRs;
	}

	
	public void setNhomkenh(ResultSet nhomkenh) {
	
		this.nhomkenh = nhomkenh;
	}


	public ResultSet getNhomkenh() {

		return this.nhomkenh;
	}


	public String getLoaikho() {

		return this.loaikho;
	}


	public void setLoaikho(String loaikho) {
		
		this.loaikho = loaikho;
	}


	public void setLoaikhoRs(ResultSet loaikhoRs) {

		this.loaikhoRs = loaikhoRs;
	}


	public ResultSet getLoaikhoRs() {

		return this.loaikhoRs;
	}

	
	public void setNccId(String nccId) {
		this.nccId = nccId;
		
	}

	
	public String getNccId() {
		
		return this.nccId;
	}

	
	public void setNccRs(ResultSet nccRs) {
		
		this.nccrs = nccRs;
	}

	
	public ResultSet getNccRs() {
		
		return this.nccrs;
	}

	
	public String getChiTietXNT_Lo() {
		
		return this.ChiTietXNT_Lo;
	}

	
	public void setChiTietXNT_Lo(String ChiTietXNT_Lo) {
		
		this.ChiTietXNT_Lo=ChiTietXNT_Lo;
	}

	
	public void setDongiagoc(String dongiaGoc) {
		
		this.laygiaGoc = dongiaGoc;
	}

	
	public String getDongiagoc() {
		
		return this.laygiaGoc;
	}

	
	public void setChuyenSale(String chuyenSale) {
		
		this.chuyenSale = chuyenSale;
	}

	
	public String getChuyenSale() {
		
		return this.chuyenSale;
	}

	
	public void setKhuyenmai(String khuyenmai) {
		
		this.laykhuyenMai = khuyenmai;
	}

	
	public String getKhuyenmai() {
		
		return this.laykhuyenMai;
	}

	
	public String getHangdiduong() {
		return this.hangdiduong;
	}

	
	public void setHangdiduong(String hangdiduong) {
		this.hangdiduong = hangdiduong;
	}
	public String getDiabanid() {
		return diabanid;
	}

	public void setDiabanid(String diabanid) {
		this.diabanid = diabanid;
	}

	public ResultSet getRsdiaban() {
		return Rsdiaban;
	}

	public void setRsdiaban(ResultSet rsdiaban) {
		Rsdiaban = rsdiaban;
	}
	public ResultSet getKhrsnew() {
		return khrsnew;
	}

	public void setKhrsnew(ResultSet khrsnew) {
		this.khrsnew = khrsnew;
	}
	public ResultSet getRsSearch() {
		return RsSearch;
	}

	public void setRsSearch(ResultSet rsSearch) {
		RsSearch = rsSearch;
	}
	
	public void setLoaiSalesIn(String loaisalesIn) {

		this.loaisalesIn = loaisalesIn;
	}


	public String getLoaiSalesIn() {
		
		return this.loaisalesIn;
	}
	
	public String getChiTietXNT_Lo_Tong() {

		return this.ChiTietXNT_Lo_Tong;
	}

	public void setChiTietXNT_Lo_Tong(String ChiTietXNT_Lo_tong) {

		this.ChiTietXNT_Lo_Tong= ChiTietXNT_Lo_tong;
	}
	
	public String getLoainhanvien() 
	{
		if( this.loainhanvien == null )
			return "";
		
		return this.loainhanvien.toString();
	}

	public void setLoainhanvien(Object loainhanvien) 
	{
		this.loainhanvien = loainhanvien;
	}
	
	public String getDoituongId() 
	{
		if( this.doituongId == null )
			return "";
		
		return this.doituongId.toString();
	}

	public void setDoituongId(Object doituongId) 
	{
		this.doituongId = doituongId;
	}

	
	public void setDlppIds(String dlppId) {
		this.dlppIds = dlppId;
	}

	
	public String getDlppIds() {
		return this.dlppIds;
	}

	
	public void setDlpp(ResultSet dlpp) {
		this.dlppRs = dlpp;
	}

	
	public ResultSet getDlpp() {
		return this.dlppRs;
	}

	
	public void setIsDlpp(String isdlpp) {
		this.isdlpp = isdlpp;
	}

	
	public String getIsDlpp() {
		return this.isdlpp;
	}
	
	public void createXNTRs(String query, String[] param) {
		this.xntRs = db.getRsByPro(query, param);
	}
	
	public ResultSet getXNTRs() {
		return this.xntRs;
	}
	
	public void init_doanhso() 
	{
		Utility  util = new Utility();
		String sql = "";
		
		this.vung = db.get("select pk_seq,ten,diengiai from vung ");

		this.tinhthanh = db.get("select pk_seq,ten from tinhthanh");
		this.tinhRs =  db.get("select pk_seq,ten from tinhthanh");
		
		
		sql = "select pk_seq,ten,loai from kho where 1 = 1 ";
		sql += " and pk_seq in " + util.quyen_kho( this.userId );
		this.Khors = db.get(sql);

		if (this.vungId.length() > 0)
		{
			this.khuvuc = db.get("select pk_seq,ten from khuvuc where vung_fk ='" + this.vungId + "'");
		} 
		else
		{
			String query_khuvuc=" select PK_SEQ, TEN from KHUVUC ";
			this.khuvuc= db.get(query_khuvuc);		
		}

		sql = "select pk_seq,ten from nhaphanphoi where trangthai ='1'   ";
		if(this.ttId.length()>0)
			sql += " and TinhThanh_fk = '" + this.ttId + "'";
		this.Rsdiaban = db.get("select * from diaban ");
		
		String st = "select pk_seq, ma, ten from sanpham where trangthai = '1' and LOAISANPHAM_FK = '10045'  ";
		this.SpRs = db.get(st);

		sql = "select * from kho where 1 = 1 and pk_seq in " +  util.quyen_kho( this.userId );
		System.out.println(":::: KHO:  " + sql);
		this.kho = db.get(sql);

		// Dai dien kinh doanh
		String query = "";
		if ( this.nppId != null && this.nppId.length() > 0)
		{
			query = "SELECT pk_seq,ten FROM DAIDIENKINHDOANH d where d.trangthai=1 and d.PK_SEQ in (Select ddkd_fk from DAIDIENKINHDOANH_NPP where npp_fk='"+this.nppId+"' )  ";
			System.out.println("1. loainv: "+loaiNv);
			if(this.loaiNv.equals("4"))
				query="SELECT d.pk_seq, d.ten FROM DAIDIENKINHDOANH d inner join nhanvien a on d.pk_seq = a.ddkd_fk where d.trangthai=1 and a.pk_seq = "+this.userId;
			System.out.println("1.ddkdrs: "+query);
		} 
		else
		{
			query = "SELECT pk_seq, ten FROM DAIDIENKINHDOANH d where trangthai=1 ";
			if(this.phanloai.equals("2")&& !loaiNv.equals("3"))
			{
				query += " and pk_Seq in " + util.Quyen_Ddkd(userId)+"";
			}

			if(this.loaiNv.equals("4"))
				query="SELECT d.pk_seq, d.ten FROM DAIDIENKINHDOANH d inner join nhanvien a on d.pk_seq = a.ddkd_fk where d.trangthai=1 and a.pk_seq = "+this.userId;
			if(this.ttId.length()>0)
			{
				query += " and pk_seq in (select ddkd_fk from daidienkinhdoanh_npp where npp_fk in (Select pk_Seq from nhaphanphoi where tinhthanh_fk='"+this.ttId+"')) ";
			}
		}
		
		query += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "d", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		System.out.println("_________TDV: " + query);
		this.rsddkd = db.get(query);

		
		query = "select pk_seq , (MaFast + ' - '+ ten) as ten from khachhang a where  trangthai=1";
		if( this.nppId != null && this.nppId.trim().length() > 0 )
			query += " and npp_fk = " + this.nppId + " ";
		query += util.getPhanQuyen_TheoNhanVien("KHACHHANG", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		query += " order by ten asc ";
		this.khrsnew = db.get( query );
		
		
		//GET NHA PHAN PHOI
		Utility ut = new Utility();
		query="select pk_Seq,ten,ma from nhaphanphoi where iskhachhang=0 and trangthai=1 and pk_seq != 1 and pk_Seq in (" + ut.quyen_npp(userId)+")";

		if(this.ttId.length()>0)
		{
			query+=" and tinhthanh_fk='"+this.ttId+"' ";
		}

		query += " order by loaiNPP asc ";
		System.out.println("::: INIT NPP: " + query );
		this.nppRs = this.db.get(query);

	}

	public void init_dlpp_ctv() 
	{
		Utility util = new Utility();
		
		this.tinhthanh = db.get("select pk_seq,ten from tinhthanh");
		
		this.dlppRs=db.get("select pk_seq, mafast, ten from nhaphanphoi where loainpp = 1 and trangthai = 1 and pk_Seq in " + util.quyen_npp(this.userId));
		
		if (this.vungId.length() > 0)
		{
			this.khuvuc = db.get("select pk_seq,ten from khuvuc where vung_fk ='" + this.vungId + "'");
		} 
		else
		{
			String query_khuvuc=" select PK_SEQ, TEN from KHUVUC ";
			this.khuvuc= db.get(query_khuvuc);		
		}
	}
	@Override
	public void setLaygiavon(String laygiavon) {
		// TODO Auto-generated method stub
		this.Laygiavon=laygiavon;
	}
	@Override
	public String getLaygiavon() {
		// TODO Auto-generated method stub
		return this.Laygiavon;
	}

	public String getErpCongtyId() {
		
		return this.ErpCongTyId;
	}

	
	public void setErpCongtyId(String id) {
		
		this.ErpCongTyId=id;
	}
}
