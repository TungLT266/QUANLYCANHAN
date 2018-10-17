package geso.traphaco.center.beans.thuongdauthung.imp;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import java.util.List;

import geso.traphaco.center.beans.thuongdauthung.imp.Sanpham;
import geso.traphaco.center.beans.thuongdauthung.ISanpham;
import geso.traphaco.center.beans.thuongdauthung.IThuongdauthung;
import geso.traphaco.distributor.db.sql.dbutils;

public class Thuongdauthung implements IThuongdauthung {



	String display = "0";

	String UserID;
	double Id;
	int Thang;
	int Nam;
	String tungay = "";
	String denngay = "";
	String nsp_fk = "";
	ResultSet nspRs ;
	double soluong = 0;
	String loaict = "0";
	String isSalesIn  = "0";
	String thangdt;


	String namdt;

	String NgayTao;
	String NguoiTao;
	String NguoiSua;
	String NgaySua;
	String DienGiai;
	String Message;
	String TrangThai;
	ResultSet uploadluongcobanRs ;
	ResultSet uploadluongcobanChiTietRs ;
	dbutils db;
	String loaidk = "0";
	String tieuchilay="1";
	public String getTieuchilay() {
		return tieuchilay;
	}



	public void setTieuchilay(String tieuchilay) {
		this.tieuchilay = tieuchilay;
	}



	public Thuongdauthung(){

		this.display = "0";
		this.NguoiTao = "";
		this.NguoiSua = "";
		this.NgayTao ="";
		this.NgaySua ="";
		this.Message="";
		this.DienGiai="";
		this.TrangThai = "0";

		this.soluong = 0;
		this.loaict = "0";
		this.isSalesIn  = "0";

		this.nsp_fk = "";
		this.tungay = "";
		this.denngay = "";
		this.thangdt="";
		this.namdt="";

		this.loaidk = "0";
		this.loai="0";
		this.tieuchilay="1";
		db=new dbutils();

		spList= new ArrayList<ISanpham>();
		this.nspRs = db.getScrol("select PK_SEQ, TEN from NHOMSANPHAM where TRANGTHAI = '1'");
	}



	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}


	public Thuongdauthung(String id)
	{
		db=new dbutils();
		String  query="";

		this.loaidk = "0";

		query=" SELECT   c.tieuchilay,c.trangthai,C.PK_SEQ,isnull( C.thang,'')as thang, isnull(C.nam,'') as nam, C.DIENGIAI,  C.NGAYTAO, C.NGAYSUA,NT.TEN AS NGUOITAO,NS.TEN AS NGUOISUA" +
				"	, c.nsp_fk ,c.Loai "+ 
				" FROM thuongdauthung AS C " +
				" INNER JOIN "+
				" dbo.NHANVIEN AS NT ON C.NGUOITAO = NT.PK_SEQ INNER JOIN dbo.NHANVIEN AS NS ON C.NGUOISUA = NS.PK_SEQ where 1=1 and c.pk_seq="+ id;
		try
		{
			ResultSet rsUploadLuongCoBan=	db.get(query);
			if(rsUploadLuongCoBan!=null)
			{
				while(rsUploadLuongCoBan.next())
				{
					this.setID(rsUploadLuongCoBan.getDouble("PK_SEQ"));
					this.setNgayTao(rsUploadLuongCoBan.getString("NGAYTAO"));
					this.setNgaySua(rsUploadLuongCoBan.getString("NGAYSUA"));
					this.setDienGiai(rsUploadLuongCoBan.getString("DIENGIAI"));
					this.setNguoiTao(rsUploadLuongCoBan.getString("NGUOITAO"));
					this.setNguoiSua(rsUploadLuongCoBan.getString("NGUOISUA"));
					this.setTrangThai(rsUploadLuongCoBan.getString("trangthai"));					
					this.nsp_fk = rsUploadLuongCoBan.getString("nsp_fk");
					this.loai = rsUploadLuongCoBan.getString("loai")==null?"0":rsUploadLuongCoBan.getString("loai");
					this.setTieuchilay(rsUploadLuongCoBan.getString("tieuchilay"));
					this.thangdt=rsUploadLuongCoBan.getString("thang");
					this.namdt=rsUploadLuongCoBan.getString("nam");
				}
			}
			rsUploadLuongCoBan.close();	

			query="select nsp.sanpham_fk,sp.MA as spMa,sp.ten as spTen ,nsp.thuong_fk,TrongSo " +
					" from ThuongDauThung_Sp nsp inner join SANPHAM sp on sp.PK_SEQ=nsp.sanpham_fk where nsp.thuong_fk='"+id+"'";
			ResultSet	rs =db.get(query);
			List<ISanpham> spList= new ArrayList<ISanpham>();
			while(rs.next())
			{
				ISanpham sp= new Sanpham();
				sp.setId(rs.getString("sanpham_fk"));
				sp.setMa(rs.getString("spMa"));
				sp.setTen(rs.getString("spTen"));
				sp.setTrongso(rs.getString("TrongSo")==null?"":rs.getString("TrongSo"));
				spList.add(sp);
			}
			this.setSpList(spList);


			this.nspRs = db.getScrol("select PK_SEQ, TEN from NHOMSANPHAM where TRANGTHAI = '1' and TYPE = '4'");

			NumberFormat format = new DecimalFormat("#,###,###");
			query = "select TuMuc,DenMuc,thuong_fk,DonVi,ThuongSR,ThuongSS,ThuongASM,ThuongBM " +
					" from ThuongDauThung_TieuChi where thuong_fk = '" + this.Id + "'";

			String _tumuc ="";
			String _denmuc="";
			String donvi_thuong="";
			String _thuongSR="";
			String _thuongSS="";
			String _thuongASM="";
			String _thuongBM="";
			rs=db.get(query);
			while(rs.next())
			{
				/*_tumuc+=rs.getString("TuMuc").trim().length() > 0?"" + "__": " __";
				_denmuc+=rs.getString("DenMuc").trim().length() > 0?"" + "__": " __";
				*/_thuongSR+=rs.getString("thuongSR").trim().length() > 0?format.format(rs.getDouble("thuongSR")) + "__": " __";
				_thuongSS+=rs.getString("thuongSS").trim().length() > 0?format.format(rs.getDouble("thuongSS")) + "__": " __";
				_thuongASM+=rs.getString("thuongASM").trim().length() > 0?format.format(rs.getDouble("thuongASM")) + "__": " __";
				_thuongBM+=rs.getString("thuongBM").trim().length() > 0?format.format(rs.getDouble("thuongBM")) + "__": " __";
				//donvi_thuong+=rs.getString("DonVi").trim().length() > 0?"" + "__": " __";
			}
			rs.close();
			if(_thuongSR.trim().length() > 0 || _thuongSS.trim().length() > 0 || _thuongASM.trim().length() > 0 || _thuongBM.trim().length() > 0)
			{
				//_tumuc = _tumuc.substring(0, _tumuc.length() - 2); this.tumuc = _tumuc.split("__");
				//_denmuc = _denmuc.substring(0, _denmuc.length() - 2); this.denmuc = _denmuc.split("__");
				_thuongSR = _thuongSR.substring(0, _thuongSR.length() - 2); this.thuongSR = _thuongSR.split("__");
				_thuongSS = _thuongSS.substring(0, _thuongSS.length() - 2); this.thuongSS = _thuongSS.split("__");
				_thuongASM = _thuongASM.substring(0, _thuongASM.length() - 2); this.thuongASM = _thuongASM.split("__");
				_thuongBM = _thuongBM.substring(0, _thuongBM.length() - 2); this.thuongBM = _thuongBM.split("__");
				//donvi_thuong = donvi_thuong.substring(0, donvi_thuong.length() - 2); this.donvi_thuong = donvi_thuong.split("__");
			}
		}catch(Exception er)
		{
			er.printStackTrace();
			this.Message="Error rsUploadLuongCoBan.java - line : 88- detail error :"+ er.toString();
			System.out.println("Error Class name : rsUploadLuongCoBan.JAVA- LINE 216 :STRING SQL" + er.toString());
		}
		this.Message="";
	}

	@Override
	public void setID(double id) 
	{
		this.Id=id;	
	}

	@Override
	public double getID() 
	{
		return Id;
	}

	@Override
	public void setThang(int thang) 
	{
		this.Thang=thang;
	}

	@Override
	public int getThang() 
	{
		return this.Thang;
	}

	@Override
	public void setNam(int nam) {
		this.Nam=nam;
	}

	@Override
	public int getNam() {

		return this.Nam;
	}



	@Override
	public void setNgayTao(String ngaytao) {

		this.NgayTao =ngaytao;
	}

	@Override
	public String getNgayTao() {

		return this.NgayTao;
	}

	@Override
	public void setNgaySua(String ngaysua) {

		this.NgaySua=ngaysua;
	}

	@Override
	public String getNgaySua() {

		return this.NgaySua;
	}

	@Override
	public void setDienGiai(String diengiai) {

		this.DienGiai=diengiai;
	}

	@Override
	public String getDienGiai() {

		return this.DienGiai;
	}
	public boolean KiemTraHopLe()
	{
		if(this.NguoiTao.toString().equals("null") || this.NguoiTao.equals(""))
		{
			this.Message="Ten Nguoi Dung Khong Hop Le,Vui Long Dang Xuat De Thu Lai!";
			return false;
		}
		if(this.thangdt.equals("")|| this.namdt.equals(""))
		{

			this.Message="Chưa chọn thời gian";			
			db.update("rollback");
			return false;
		}	
		return true;
	}

	public void setUserId(String userid) 
	{
		this.UserID=userid;
	}

	public String getUserId() 
	{

		return this.UserID;
	}

	@Override
	public void setNguoiTao(String nguoitao) {

		this.NguoiTao=nguoitao;		
	}

	@Override
	public String getNguoiTao() {

		return this.NguoiTao;
	}

	@Override
	public void setNguoiSua(String nguoisua) {

		this.NguoiSua=nguoisua;
	}

	@Override
	public String getNguoiSua() {

		return this.NguoiSua;
	}

	@Override
	public void setMessage(String strmessage) {

		this.Message=strmessage;
	}

	@Override
	public String getMessage() {

		return this.Message;
	}


	@Override
	public void setTrangThai(String trangthai) {

		this.TrangThai=trangthai;
	}
	@Override
	public String getTrangThai() {

		return this.TrangThai;
	}

	public boolean CreateUploadLuongCoBan() 
	{
		try
		{
			if(!KiemTraHopLe())
			{
				return false;
			}

			db.getConnection().setAutoCommit(false);
			String sql = "";
			sql=
					"insert into thuongdauthung(diengiai,thang,nam,NGAYTAO,NGUOITAO,NGAYSUA,NGUOISUA,TRANGTHAI,Loai,tieuchilay) " +
							"values (N'"+this.DienGiai+"','"+this.thangdt+"','"+this.namdt+"','"+this.NgayTao+"','"+this.NguoiTao+"','"+this.NgaySua+"','"+this.NguoiSua+"','0','"+this.loai+"',"+this.tieuchilay+")";
			if(!db.update(sql))
			{
				this.Message="Loi :"+sql;		
				db.update("rollback");
				return false;
			}		

			sql = "select scope_identity() as Id";
			ResultSet	rs = db.get(sql);	
			try
			{
				rs.next();
				this.Id=rs.getDouble("Id");
				rs.close();
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}


			if(thuongSR!=null || thuongSS!=null || thuongASM!=null || thuongBM !=null)
			{
				for(int i=0;i<1;i++)
				{
					if((thuongSR[i]!=null&&thuongSR[i].length()>0)||(thuongSS[i]!=null&&thuongSS[i].length()>0)||(thuongBM[i]!=null&&thuongBM[i].length()>0)||(thuongASM[i]!=null&&thuongASM[i].length()>0))
					{
						sql="insert into ThuongDauThung_TieuChi(Thuong_fk,ThuongSR,ThuongSS,ThuongASM,ThuongBM) " +
								" select '"+this.Id+"','"+thuongSR[i].replaceAll(",", "")+"','"+thuongSS[i].replaceAll(",", "")+"','"+thuongASM[i].replaceAll(",", "")+"','"+thuongBM[i].replaceAll(",", "")+"'  ";
						if(!this.db.update(sql))
						{				
							this.db.getConnection().rollback();
							this.Message="Vui lòng liên hệ Admin để sửa lỗi "+sql;
							return false;
						}
					}
				}
			}

			if(this.spList != null)
			{
				int size = spList.size();
				int m = 0;
				while(m < size)
				{
					ISanpham sp = spList.get(m);
					String trongso=sp.getTrongso().length()<=0?"NULL":sp.getTrongso();
					sql = "insert into ThuongDauThung_Sp(sanpham_fk, Thuong_fk,TrongSo) " +
							" select '" + sp.getId() + "', '"+ this.Id + "',"+trongso+" where '" + sp.getId() + "' not in (select sanpham_fk from ThuongDauThung_Sp where thuong_fk='"+this.Id+"')";
					m++ ;
					if(!this.db.update(sql))
					{				
						this.db.getConnection().rollback();
						this.Message="Vui lòng liên hệ Admin để sửa lỗi "+sql;
						return false;
					}
				}
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}catch(Exception er){	
			er.printStackTrace();
			db.update("rollback");
			this.setMessage("Ban khong the them chi tieu moi nay, loi : " + er.toString());
			return false;			
		}
		return true;
	}

	public boolean UpdateUploadLuongCoBan()
	{
		try
		{
			if(!KiemTraHopLe()){
				return false;
			}
			db.getConnection().setAutoCommit(false);

			String sql = "";

			sql="update thuongdauthung set diengiai = N'"+this.DienGiai+"',nam ='"+this.namdt+"'" +
					",thang = '"+this.thangdt+"',NGAYSUA = '"+this.NgaySua+"'" +
					",NGUOISUA='"+this.NguoiSua+"' ,tieuchilay="+this.tieuchilay+" WHERE PK_SEQ='"+this.Id+"' " ;
			System.out.println("save  thuongdauthung " +sql);
			if(!db.update(sql))
			{
				this.Message="Loi :"+sql;		
				db.update("rollback");
				return false;
			}					

			sql="delete ThuongDauThung_TieuChi where Thuong_fk='"+this.Id+"'";
			if(!this.db.update(sql))
			{				
				this.db.getConnection().rollback();
				this.Message="Vui lòng liên hệ Admin để sửa lỗi "+sql;
				return false;
			}

			if(thuongSR!=null || thuongSS!=null || thuongASM!=null || thuongBM !=null)
			{
				for(int i=0;i<1;i++)
				{
					if((thuongSR[i]!=null&&thuongSR[i].length()>0)||(thuongSS[i]!=null&&thuongSS[i].length()>0)||(thuongBM[i]!=null&&thuongBM[i].length()>0)||(thuongASM[i]!=null&&thuongASM[i].length()>0))
					{
						sql="insert into ThuongDauThung_TieuChi(Thuong_fk,ThuongSR,ThuongSS,ThuongASM,ThuongBM) " +
								" select '"+this.Id+"','"+thuongSR[i].replaceAll(",", "")+"','"+thuongSS[i].replaceAll(",", "")+"','"+thuongASM[i].replaceAll(",", "")+"','"+thuongBM[i].replaceAll(",", "")+"'  ";
					System.out.println("::::::::::::::::::::::"+sql);
						if(!this.db.update(sql))
						{				
							this.db.getConnection().rollback();
							this.Message="Vui lòng liên hệ Admin để sửa lỗi "+sql;
							return false;
						}
					}
				}
			}


			sql=" delete from ThuongDauThung_Sp where Thuong_fk="+this.Id+" ";
			if(this.spList != null)
			{
				int size = spList.size();
				int m = 0;
				while(m < size)
				{
					ISanpham sp = spList.get(m);
					String trongso=sp.getTrongso().length()<=0?"NULL":sp.getTrongso();
					sql = "insert into ThuongDauThung_Sp(sanpham_fk, Thuong_fk,TrongSo) " +
							" select '" + sp.getId() + "', '"+ this.Id + "',"+trongso+" where '" + sp.getId() + "' not in (select sanpham_fk from ThuongDauThung_Sp where thuong_fk='"+this.Id+"')";
					m++ ;
					if(!this.db.update(sql))
					{				
						this.db.getConnection().rollback();
						this.Message="Vui lòng liên hệ Admin để sửa lỗi "+sql;
						return false;
					}
				}
			}

			


			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}catch(Exception er){	
			er.printStackTrace();
			db.update("rollback");
			this.setMessage("Ban khong the them chi tieu moi nay, loi : " + er.toString());
			return false;			
		}
		return true;
	}
	public boolean tinhthuongdauthung(dbutils db)
	{
		return true;

	}

	public boolean chotUploadLuongCoBan()
	{
		try		
		{
			db.getConnection().setAutoCommit(false);

			String  query ="";


			query = "update thuongdauthung set trangthai =1 where  pk_seq = '"+this.Id+"' and trangthai = 0 ";
			if(db.updateReturnInt(query) <=0)
			{
				this.setMessage("Khong the chot thuong :" + query);
			}
			if(!this.tinhthuongdauthung(db))
			{
				db.update("rollback");
				return false;			
			}			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return  true ;
		}
		catch (Exception e) 
		{
			db.update("rollback");
			this.setMessage("Ban khong the chot Thuong dau thung, loi : " + e.toString());
			return false;			
		}

	}

	public boolean UnchotUploadLuongCoBan()
	{
		String  query = "update thuongdauthung set trangthai =0 where  pk_seq = '"+this.Id+"'";		
		return db.updateReturnInt(query) > 0 ? true  : false;
	}
	public boolean DeleteUploadLuongCoBan()
	{
		String  query = "update thuongdauthung set trangthai =2 where  pk_seq = '"+this.Id+"' and trangthai = 0";		
		return db.updateReturnInt(query) > 0 ? true  : false;
	}

	public void closeDB() {
		try{
			this.uploadluongcobanRs.close();
			if(this.db!=null){
				db.shutDown();
			}
		}catch(Exception er){

		}
	}

	//this.soluong = 0;
	public double getSoluong() {
		return soluong;
	}
	public void setSoluong(double soluong) {
		this.soluong = soluong;
	}
	//this.isThung = "0";
	public String getLoaict() {
		return loaict;
	}
	public void setLoaict(String isThung) {
		this.loaict = isThung;
	}

	public String getIsSalesIn() {
		return isSalesIn;
	}
	public void setIsSalesIn(String isSalesIn) {
		this.isSalesIn = isSalesIn;
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
	//this.tyledoanhsoSR = 0;
	public void setNsp_fk(String nsp_fk) {
		this.nsp_fk = nsp_fk;
	}
	public String getNsp_fk() {
		return nsp_fk;
	}
	public ResultSet getNspRs() {
		return nspRs;
	}

	public String getLoaidk() {
		return loaidk;
	}
	public void setLoaidk(String loaidk) 
	{
		this.loaidk = loaidk;
	}


	List<ISanpham> spList;

	@Override
	public List<ISanpham> getSpList()
	{
		return this.spList;
	}

	@Override
	public void setSpList(List<ISanpham> spList)
	{
		this.spList = spList;	
	}
	String[] sanpham;
	public String[] getSanpham()
	{
		return this.sanpham;
	}

	public void setSanpham(String[] sanpham)
	{
		this.sanpham = sanpham;
	}


	public void createSpList()
	{
		String query = "select b.pk_seq, b.MA, b.TEN from NHOMSANPHAM_SANPHAM a inner join SANPHAM b on a.SP_FK = b.PK_SEQ where a.NSP_FK = '" + this.nsp_fk + "'";	
		ResultSet rs = db.get(query);
		List<ISanpham> splist = new ArrayList<ISanpham>();
		if(rs != null)
		{
			try 
			{
				ISanpham sp = null;
				while(rs.next())
				{
					String spId = rs.getString("pk_seq");
					String spMa = rs.getString("MA");
					String spTen = rs.getString("TEN");
					sp = new Sanpham(spId, spMa, spTen);
					splist.add(sp);
				}
			} 
			catch(Exception e) {}
		}
		this.spList = splist;
	}

	ResultSet dataRs;

	public ResultSet getDataRs()
	{
		return dataRs;
	}



	public void setDataRs(ResultSet dataRs)
	{
		this.dataRs = dataRs;
	}
	String loai;

	public String getLoai()
	{
		return loai;
	}



	public void setLoai(String loai)
	{
		this.loai = loai;
	}


	String[] tumuc;
	String[] denmuc,thuongSR,thuongSS,thuongASM,thuongBM,donvi_thuong;
	public String[] getTumuc()
	{
		return this.tumuc;
	}

	public String[] getDonvi_thuong()
	{
		return donvi_thuong;
	}

	public void setDonvi_thuong(String[] donvi_thuong)
	{
		this.donvi_thuong = donvi_thuong;
	}

	public void setTumuc(String[] tumuc)
	{

		this.tumuc = tumuc;
	}

	public String[] getDenmuc()
	{

		return this.denmuc;
	}

	public void setDenmuc(String[] denmuc)
	{

		this.denmuc = denmuc;
	}

	public String[] getThuongSR()
	{
		return thuongSR;
	}



	public void setThuongSR(String[] thuongSR)
	{
		this.thuongSR = thuongSR;
	}



	public String[] getThuongSS()
	{
		return thuongSS;
	}


	public void setThuongSS(String[] thuongSS)
	{
		this.thuongSS = thuongSS;
	}


	public String[] getThuongASM()
	{
		return thuongASM;
	}

	public void setThuongASM(String[] thuongASM)
	{
		this.thuongASM = thuongASM;
	}

	public String[] getThuongBM()
	{
		return thuongBM;
	}

	public void setThuongBM(String[] thuongBM)
	{
		this.thuongBM = thuongBM;
	}

	@Override
	public void setLuongkhacRs(String uploadluongcobanRs)
	{
		String query=	"SELECT    c.trangthai,C.PK_SEQ, isnull(C.thang,'') as thang, isnull(C.nam,'') as nam, C.DIENGIAI,  C.NGAYTAO, C.NGAYSUA,NT.TEN AS NGUOITAO "+ 
				",NS.TEN AS NGUOISUA,c.LOAI FROM thuongdauthung AS C INNER JOIN "+
				"dbo.NHANVIEN AS NT ON C.NGUOITAO = NT.PK_SEQ INNER JOIN dbo.NHANVIEN AS NS ON C.NGUOISUA = NS.PK_SEQ " +
				"where 1=1 and c.Loai="+loai+" ";
		this.uploadluongcobanRs= this.db.get(query);
	}

	@Override
	public ResultSet getLuongkhacRs()
	{
		return this.uploadluongcobanRs;
	}
	public String getThangdt() {
		return thangdt;
	}



	public void setThangdt(String thangdt) {
		this.thangdt = thangdt;
	}



	public String getNamdt() {
		return namdt;
	}



	public void setNamdt(String namdt) {
		this.namdt = namdt;
	}


}
