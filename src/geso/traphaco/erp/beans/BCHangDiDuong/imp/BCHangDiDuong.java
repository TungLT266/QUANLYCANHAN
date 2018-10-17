
package geso.traphaco.erp.beans.BCHangDiDuong.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.BCHangDiDuong.IBCHangDiDuong;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BCHangDiDuong implements IBCHangDiDuong
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
	
	String laygiaGoc;
	String chuyenSale;
	String laykhuyenMai;
	
	public BCHangDiDuong()
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

		this.kenh = db.get(" select pk_seq,ten,diengiai from kenhbanhang ");
		this.nhomkenh = db.get(" select pk_seq,ten,diengiai from NHOMKENH ");

		this.vung = db.get("select pk_seq,ten,diengiai from vung ");

		this.dvkd = db.get("select pk_seq,diengiai from donvikinhdoanh ");

		this.tinhthanh = db.get("select pk_seq,ten from tinhthanh");
		this.Khors=db.get("select pk_seq,ten from kho");

		if (this.vungId.length() > 0)
		{
			this.khuvuc = db.get("select pk_seq,ten from khuvuc where vung_fk ='" + this.vungId + "'");
		} else
		{
			String query_khuvuc=" select PK_SEQ, TEN from KHUVUC "
				+ "	where PK_SEQ in (select KHUVUC_Fk from NHAPHANPHOI "
				+ "	where pk_seq in "+ Ult.quyen_npp(userId)+")"; 			
			this.khuvuc= db.get(query_khuvuc);		
		}

		Utility  util = new Utility();

		sql = "select pk_seq,ten from nhaphanphoi where trangthai ='1'   ";

		if(this.ttId.length()>0)
		{
			sql+=" and TinhThanh_fk='"+this.ttId+"'";
		}

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

		sql=" select distinct a.PK_SEQ, a.scheme from CTKHUYENMAI a  " +
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

		this.sanpham = db.get(st);
		this.SpRs = db.get(st);

		sql = "select pk_seq,donvi from donvidoluong ";

		this.dvdl = db.get(sql);
		this.kho = db.get("select * from kho ");

		// Dai dien kinh doanh
		if ( this.nppId!=null&& this.nppId.length() > 0)
		{
			this.setRsddkd(db.get("SELECT pk_seq,ten FROM DAIDIENKINHDOANH d where d.trangthai=1 and d.PK_SEQ in (Select ddkd_fk from DAIDIENKINHDOANH_NPP where npp_fk='"+this.nppId+"' )  "));
		} else
		{
			String query="SELECT pk_seq,ten FROM DAIDIENKINHDOANH d where trangthai=1 ";
			if(this.phanloai.equals("2")&& !loaiNv.equals("3"))
			{
				query+= " and pk_Seq in " + util.Quyen_Ddkd(userId)+"";
			}
			if(this.ttId.length()>0)
			{
				query+=" and pk_seq in (select ddkd_fk from daidienkinhdoanh_npp where npp_fk in (Select pk_Seq from nhaphanphoi where tinhthanh_fk='"+this.ttId+"')) ";
			}
			System.out.println("_________TDV"+query);
			this.setRsddkd(db.get(query));
		}

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

		String query ="  select distinct  kh.PK_SEQ, kh.MaFast + ' - '+ kh.Ten as Ten" +
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
		this.khrs =  this.db.get( query);
		
		//GET nha cung cap

		query = "select distinct ncc.pk_seq, ncc.ten from erp_nhacungcap ncc" +
			  "  where 1=1 and ncc.TrangThai ='1' and duyet = '1'";
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
			e.printStackTrace();
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

	public ResultSet getNhomspRs()
	{

		return this.nhomRs;
	}


	public void setNhomspRs(ResultSet nspRs)
	{

		this.nhomRs = nspRs;
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

}
