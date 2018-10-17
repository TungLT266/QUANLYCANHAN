package geso.traphaco.center.beans.bm.imp;

import geso.traphaco.center.beans.bm.IBm;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import org.apache.tomcat.util.threads.ResizableExecutor;

import geso.traphaco.center.db.sql.*;

public class Bm implements IBm {
	public String Id;
	public String bmTen;
	public String dienthoai;
	public String email;
	public String diachi;
	String ngaysinh = "";
	String mact, vitri, vungtt, sonamlamviec, sodtct, ngayvaoct, loaihd, ngaykthd, ngaykyhd, sotk, ghichu, dakyhd;
	String nganhang, chinhanh, manhanvien, mathuviec;
	String sohd = "";
	public String kbhId;
	public ResultSet kbh;
	
	public String dvkdId;
	public ResultSet dvkd;
	
	public String[] vungId;
	public ResultSet vung;
	
	public String trangthai;
	public String msg;
	String tructhuoc = "";
	public String userId;
	
	public dbutils db ;
	ResultSet tructhuocrs;
	String maFAST;
	String MaNhanSu = "";	
	public String getMaNhanSu() {
		return MaNhanSu;
	}

	public void setMaNhanSu(String maNhanSu) {
		MaNhanSu = maNhanSu;
	}
	public Bm(){
		this.Id = "";
		this.bmTen = "";
		this.dienthoai = "";
		this.email = "";
		this.diachi = "";
		this.kbhId = "";
		this.dvkdId = "";
		this.trangthai = "";
		this.msg = "";
		this.userId = "";
		
		this.mact = "";
		this.vungtt = "";
		this.dakyhd = "";
		this.ghichu = "";
		this.loaihd = "";
		this.ngaykthd = "";
		this.ngaykyhd = "";
		this.ngayvaoct = "";
		this.sodtct = "";
		this.sotk = "";
		this.sonamlamviec = "";
		this.vitri = "";
		
		this.nganhang = "";
		this.chinhanh = "";
		this.manhanvien = "";
		this.mathuviec = "";
		this.maFAST = "";
		
		this.db = new dbutils();
	}
	
	public String getMaFAST() {

		return this.maFAST;
	}


	public void MaFAST(String maFAST) {
		
		this.maFAST = maFAST;
	}
	
	public String getId(){
		return this.Id;
	}
	
	public void setId(String Id){
		this.Id = Id;
	}

	public String getTen(){
		return this.bmTen;
	}
	
	public void setTen(String bmTen){
		this.bmTen = bmTen;
	}
	
	public String getDienthoai(){
		return this.dienthoai;
	}
	
	public void setDienthoai(String dienthoai){
		this.dienthoai = dienthoai;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public void setEmail(String email){
		this.email = email;
	}

	public String getDiachi(){
		return this.diachi;
	}
	
	public void setDiachi(String diachi){
		this.diachi = diachi;
	}

	public String getKbhId(){
		return this.kbhId;
	}
	
	public void setKbhId(String kbhId){
		this.kbhId = kbhId;
	}

	public ResultSet getKbh(){
		return this.kbh;
	}
	
	public void setKbh(ResultSet kbh){
		this.kbh = kbh;
	}

	public String getDvkdId(){
		return this.dvkdId;
	}
	
	public void setDvkdId(String dvkdId){
		this.dvkdId = dvkdId;
	}

	public ResultSet getDvkd(){
		return this.dvkd;
	}
	
	public void setDvkd(ResultSet dvkd){
		this.dvkd = dvkd;
	}

	public String[] getVungId(){
		return this.vungId;
	}
	
	public void setVungId(String[] vungId){
		this.vungId = vungId;
	}

	public ResultSet getVung(){
		return this.vung;
	}
	
	public void setVung(ResultSet vung){
		this.vung = vung;
	}

	public String getTrangthai(){
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai){
		this.trangthai = trangthai;
	}

	public String getMsg(){
		return this.msg;
	}
	
	public void setMsg(String msg){
		this.msg = msg;
	}
	
	public String getUserId(){
		return this.userId;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}

	public void init_New(){
		/*String sql 	= "SELECT PK_SEQ AS KBHID, TEN AS KBH FROM KENHBANHANG WHERE trangthai = '1' "; //PK_SEQ = '100000' OR PK_SEQ = '100002'";
		this.kbh  	= this.db.get(sql);*/
		
		String	sql 		= "SELECT PK_SEQ AS DVKDID, DONVIKINHDOANH AS DVKD FROM DONVIKINHDOANH ";//WHERE PK_SEQ = '100000' OR PK_SEQ = '100001'";
		this.dvkd 	= this.db.get(sql);
		sql = "select pk_seq as id,ten as ten from nhaphanphoi where loainpp = 0";
		this.tructhuocrs = db.get(sql);
		if(this.Id.length() <=0){
			sql= "SELECT PK_SEQ AS VUNGID, TEN AS VUNG,'' as ngaybatdau,'' as ngayketthuc  FROM VUNG ORDER BY TEN"; 	
		
		}else{
			sql="SELECT PK_SEQ AS VUNGID, TEN AS VUNG,b.ngaybatdau,b.ngayketthuc "+
			  " FROM VUNG inner join BM_CHINHANH b on vung.pk_seq=b.vung_fk WHERE B.BM_FK= "+ this.Id +
			 " UNION  ALL "+
			" SELECT PK_SEQ AS VUNGID, TEN AS VUNG,'' as ngaybatdau,'' as ngayketthuc  FROM VUNG  WHERE PK_SEQ NOT IN (SELECT VUNG_FK FROM BM_CHINHANH WHERE BM_FK ="+this.Id+") "+
			" ORDER BY TEN  ";
		}
		System.out.println(sql);
		this.vung 	= this.db.get(sql);
		
	}
	
	public void init_Update(){
		try{
			String sql 	=	"SELECT isnull(bm.mafast,'') as mafast,  ISNULL(BM.MACONGTY,'') AS MACONGTY, ISNULL(BM.VITRI,'') AS VITRI, ISNULL(BM.VUNGTT, '') AS VUNGTT, ISNULL(BM.SOTAIKHOAN,'') AS SOTAIKHOAN, BM.DAKYHD, ISNULL(BM.GHICHU,'') AS GHICHU, BM.LOAIHD, ISNULL(BM.NGAYKETTHUCHD,'') AS NGAYKETTHUCHD, ISNULL(BM.NGAYKYHD,'') AS NGAYKYHD, ISNULL(BM.NGAYVAOCONGTY,'') AS NGAYVAOCONGTY, ISNULL(BM.SODTCONGTY,'') AS SODTCONGTY, ISNULL(BM.SONAMLAMVIEC,'') AS SONAMLAMVIEC, " +
							"BM.PK_SEQ AS BMID, BM.TEN AS BMTEN, isnull(BM.DIACHI,'') as diachi, isnull(BM.DIENTHOAI,'') as dienthoai,isnull(BM.EMAIL,'') as email, BM.TRANGTHAI,  " + 
							"BM.NGAYTAO, BM.NGUOITAO, BM.NGAYSUA, BM.NGUOISUA,BM.Tructhuoc_fk,BM.NGAYSINH, VUNG.PK_SEQ AS VUNGID," +
							"VUNG.TEN AS VUNG, DVKD.PK_SEQ AS DVKDID, " +
							"ISNULL(BM.NGANHANG,'') as nganhang, ISNULL(BM.CHINHANH,'') as chinhanh, BM.PK_SEQ as manhanvien, ISNULL(BM.MATHUVIEC,'') as mathuviec,isnull(BM.SOHDLD,'') as SOHDLD, isnull(bm.manhansu,'') as manhansu " +
							"FROM BM BM " +
							"Left JOIN BM_CHINHANH BM_CN ON BM_CN.BM_FK = BM.PK_SEQ " +
							"Left JOIN VUNG VUNG ON VUNG.PK_SEQ = BM_CN.VUNG_FK " +
							"Left JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = BM.DVKD_FK " +
							"WHERE BM.PK_SEQ = '" + this.Id + "'";
			
			System.out.println(sql);
			ResultSet rs = this.db.get(sql);
			rs.next();
			
			this.bmTen = rs.getString("bmten");
			this.diachi = rs.getString("diachi");
			this.dienthoai = rs.getString("dienthoai");
			this.email = rs.getString("email");
			this.trangthai = rs.getString("trangthai");
			//this.kbhId = rs.getString("kbhid");
			this.dvkdId = rs.getString("dvkdid");
			this.MaNhanSu = rs.getString("manhansu");
			this.mact = rs.getString("macongty");
        	this.vitri = rs.getString("vitri");
        	this.vungtt = rs.getString("vungtt");
        	this.sotk = rs.getString("sotaikhoan");
        	this.dakyhd = rs.getString("dakyhd");
        	this.ghichu = rs.getString("ghichu");
        	this.loaihd = rs.getString("loaihd");
        	this.ngaykthd = rs.getString("ngayketthuchd");
        	this.ngaykyhd = rs.getString("ngaykyhd");
        	this.ngayvaoct = rs.getString("ngayvaocongty");
        	this.sodtct = rs.getString("sodtcongty");
        	this.sonamlamviec = rs.getString("sonamlamviec");
        	this.tructhuoc = rs.getString("tructhuoc_fk") == null ?"": rs.getString("tructhuoc_fk") ;
        	this.ngaysinh = rs.getString("ngaysinh") == null ?"": rs.getString("ngaysinh") ;
        	this.sohd = rs.getString("SOHDLD");
        	this.nganhang = rs.getString("nganhang");
        	this.chinhanh = rs.getString("chinhanh");
        	this.manhanvien = rs.getString("manhanvien");
        	this.mathuviec = rs.getString("mathuviec");
        	this.maFAST = rs.getString("mafast");
			this.loaihd = rs.getString("loaihd");
			System.out.println("Loai hd "+loaihd);
			rs.close();
			
			sql = "SELECT COUNT(*) AS NUM FROM BM_CHINHANH WHERE BM_FK = '" + this.Id + "'";
			rs = this.db.get(sql);
			rs.next();
			int num = Integer.parseInt(rs.getString("NUM"));
			System.out.println("" + num);
			
			sql = "SELECT BM_FK AS BMID, VUNG_FK AS VUNGID FROM BM_CHINHANH WHERE BM_FK = '" + this.Id + "'";
			System.out.println(sql);
			
			rs = this.db.get(sql);
			
			this.vungId = new String[num];
			
			int i = 0;
			while(rs.next()){
				System.out.println(this.vungId[i]);
				this.vungId[i] = rs.getString("VUNGID");
				i++;
			}
			
			init_New();
			
		}catch(Exception e){System.out.println("Loi : "+e.toString());}
	}
	
	public boolean Save(HttpServletRequest request){
		String sql = "";
		try{
			String  num="100018";
			 this.db.getConnection().setAutoCommit(false);
			 
			 if(userId==null||userId=="")
			 {
				this.db.update("rollback");
				this.msg = "User Dang Nhap Tam Thoi Bi Log Vi Che Do Bao Mat, Vui Long Dang Nhap Lai De Thuc Hien Chuc Nang Nay";
				return false;
			 }
			 sql = "select PK_SEQ from NHACUNGCAP";
			ResultSet	rsx = this.db.get(sql);
			if(rsx !=null)
			{
				if(rsx.next())
					num = rsx.getString("PK_SEQ");
			}
			
			sql="select count(*) from BM where manhanvien=N'"+this.manhanvien+"' ";
		 	if(this.Id.length()>0)
		 		sql+=" and pk_seq!='"+this.Id+"'";
			ResultSet rsCheck=db.get(sql);
			System.out.print(sql);
			while(rsCheck.next())
			{
				if(rsCheck.getInt(1)>0)
				{
					this.msg="Mã nhân viên này đã có vui lòng nhập mã khác !";
					return false;
				}
			}
			if(rsCheck!=null)
			{
				rsCheck.close();
			}
			
			
			 if(this.Id.length() == 0)
			 {
				 System.out.println("This.truocthuoc "+this.tructhuoc);
				 sql  = "INSERT INTO BM (TEN, DIACHI, EMAIL, DIENTHOAI, TRANGTHAI, NCC_FK,  DVKD_FK, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, macongty, vitri, vungtt, sotaikhoan, dakyhd, ghichu, loaihd, ngayketthuchd, ngaykyhd, ngayvaocongty, sodtcongty, sonamlamviec, nganhang, chinhanh,  mathuviec , mafast,NGAYSINH,TRUCTHUOC_FK,SOHDLD,manhansu) " +
				 		"VALUES(N'"+ this.bmTen + "',N'" + this.diachi + "','" + this.email + "','" + this.dienthoai + "','1','"+num+"','" + this.dvkdId + "','" + this.getDateTime() + "'," +
				 		"'" + this.getDateTime()+"','" + this.userId + "','" + this.userId + "', '"+this.mact+"', '"+this.vitri+"', '"+this.vungtt+"', '"+this.sotk+"', '"+this.dakyhd+"', '"+this.ghichu+"', '"+this.loaihd+"', '"+this.ngaykthd+"', '"+this.ngaykyhd+"', '"+this.ngayvaoct+"', '"+this.sodtct+"', '"+this.sonamlamviec+"', N'"+this.nganhang+"', N'"+this.chinhanh+"', N'"+this.mathuviec+"', N'"+this.maFAST+"',N'"+this.ngaysinh+"',"+this.tructhuoc+",'"+this.sohd+"',N'"+this.MaNhanSu+"')";
			 
				 System.out.println(sql);
			 
				 if(!this.db.update(sql))
				 {
					this.db.update("rollback");
					this.msg="Khong The Thuc Hien Luu RSM Nay,Vui Long Lien He Voi Admin De Sua Loi Nay"+sql;
					return false; 
				 }
			 
				 sql  = "SELECT SCOPE_IDENTITY() AS BMID";
			 
				 ResultSet rs = this.db.get(sql);
				 rs.next();
				 this.Id = rs.getString("bmId");
				 
			 }else
			 {
				 sql = 	" UPDATE BM SET TEN = N'" + this.bmTen + "', DIACHI = N'" + this.diachi + "', EMAIL = '" + this.email + "', DIENTHOAI = '" + this.dienthoai + "', TRANGTHAI = '" + this.trangthai + "'," +
						" macongty = '"+this.mact+"', vitri = '"+this.vitri+"', vungtt = '"+this.vungtt+"', sonamlamviec = '"+this.sonamlamviec+"', sodtcongty = '"+this.sodtct+"', " +
						" ngayvaocongty = '"+this.ngayvaoct+"', loaihd = '"+this.loaihd+"',  ngayketthuchd = '"+this.ngaykthd+"', sotaikhoan = '"+this.sotk+"', ghichu = '"+this.ghichu+"', " +
						" ngaykyhd = '"+this.ngaykyhd+"', dakyhd = '"+this.dakyhd+"'," +
						" nganhang = N'"+this.nganhang+"', chinhanh = N'"+this.chinhanh+"', mathuviec = N'"+this.mathuviec+"', " +
						" NGAYSUA = '" + this.getDateTime() + "', NGUOISUA = '" + this.userId +  "', MAFAST = N'" + this.maFAST +"',ngaysinh = '"+this.ngaysinh+"',Tructhuoc_fk = "+this.tructhuoc+", SOHDLD = '"+this.sohd+"' , manhansu = N'"+this.MaNhanSu+"' WHERE PK_SEQ = '" + this.Id + "'";
				 
				 System.out.println(sql);
				 
				 if(!this.db.update(sql))
				 {
					 this.db.update("rollback");
						this.msg="Khong The Thuc Hien Luu RSM Nay,Vui Long Lien He Voi Admin De Sua Loi Nay";

						return false;  
				 }
				 
				 
				 
			 }
			 for(int i = 0; i < this.vungId.length ; i++)
			 {
				 String ngaybatdau=request.getParameter("ngaybatdau"+this.vungId[i]);
				 String ngayketthuc=request.getParameter("ngayketthuc"+this.vungId[i]);
				 sql="select * from bm_chinhanh where bm_fk="+this.Id +" and vung_fk="+ this.vungId[i];
				 ResultSet rs_check=db.get(sql);
				 if(rs_check.next()){
					 sql="update bm_chinhanh set ngaybatdau='"+ngaybatdau+"',ngayketthuc='"+ngayketthuc+"' where bm_fk="+this.Id +" and vung_fk="+ this.vungId[i];
					 if(!this.db.update(sql))
					 {
						 this.db.update("rollback");
							this.msg="Khong The Thuc Hien Luu RSM Nay .Loi :"+sql;

							return false;  
					 }
				 }else
				 {
				 
					 sql = "INSERT INTO BM_CHINHANH (BM_FK, VUNG_FK,NGAYBATDAU,NGAYKETTHUC) VALUES('" + this.Id+"','" + this.vungId[i] + "','"+ngaybatdau+"','"+ngayketthuc+"')";
					 System.out.println(sql);
					 if(!this.db.update(sql))
					 {
						//neu insert khong thanh cong ,thi thuc hien update
						 
					 }
				 }
			 }
			 
			 this.db.getConnection().commit();
			 this.db.getConnection().setAutoCommit(true);
			 
			//CAP NHAT LAI CAC THONG TIN DLN + KBH
			db.execProceduce2("CapNhatThongTinNhanVien", new String[]{ this.Id, "1" });
			
			 
		}
		catch(Exception e)
		{
			this.db.update("rollback");
			this.msg="Khong The Thuc Hien Luu RSM Nay,Vui Long Lien He Voi Admin De Sua Loi Nay";

			return false;
			
		}
		return true;

	}
	
	public Hashtable<String, String> getHTVungId(){
		Hashtable<String, String> ht = new Hashtable<String, String>();
		ht.put("0", "0");
		if(this.vungId != null){
			for(int i = 0; i < this.vungId.length; i++){
				ht.put(this.vungId[i], this.vungId[i]);
			}
		}
		return ht;
	}

	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public void DBClose(){
		try{
			if(kbh != null) kbh.close(); 
			if(dvkd != null) dvkd.close(); 
			if(vung != null) vung.close(); 
			if(this.db != null) this.db.getConnection().close();
		}catch(Exception e){}
	}
	
	public void setMaCt(String mact) {
		
		this.mact = mact;
	}

	
	public String getMaCt() {
		
		return this.mact;
	}

	
	public void setVitri(String vt) {
		
		this.vitri = vt;
	}

	
	public String getVitri() {
		
		return this.vitri;
	}

	
	public void setVungTT(String vungtt) {
		
		this.vungtt = vungtt;
	}

	
	public String getVungTT() {
		
		return this.vungtt;
	}

	
	public void setSonamlamviec(String sonam) {
		
		this.sonamlamviec = sonam;
	}

	
	public String getSonamlamviec() {
		
		return this.sonamlamviec;
	}

	
	public void setSoDTcongty(String sdt) {
		
		this.sodtct = sdt;
	}

	
	public String getSoDTcongty() {
		
		return this.sodtct;
	}

	
	public void setNgayvaoct(String ngayvaoct) {
		
		this.ngayvaoct = ngayvaoct;
	}

	
	public String getNgayvaoct() {
		
		return this.ngayvaoct;
	}

	
	public void setLoaiHD(String loaihd) {
		
		this.loaihd = loaihd;
	}

	
	public String getLoaiHD() {
		
		return this.loaihd;
	}

	
	public void setNgayketthucHD(String ngaykt) {
		
		this.ngaykthd = ngaykt;
	}

	
	public String getNgayketthucHD() {
		
		return this.ngaykthd;
	}

	
	public void setSotk(String stk) {
		
		this.sotk = stk;
	}

	
	public String getSotk() {
		
		return this.sotk;
	}

	
	public void setGhichu(String ghichu) {
		
		this.ghichu = ghichu;
	}

	
	public String getGhichu() {
		
		return this.ghichu;
	}

	
	public void setNgaykyHD(String ngaykyhd) {
		
		this.ngaykyhd = ngaykyhd;
	}

	
	public String getNgaykyHD() {
		
		return this.ngaykyhd;
	}

	
	public void setDakyHD(String dakyhd) {
		
		this.dakyhd = dakyhd;
	}

	
	public String getDakyHD() {
		
		return this.dakyhd;
	}
	
	@Override
	public void setNganHang(String nganhang) {
		this.nganhang = nganhang;
	}

	@Override
	public String getNganHang() {
		return this.nganhang;
	}

	@Override
	public void setChiNhanh(String chinhanh) {
		this.chinhanh = chinhanh;
	}

	@Override
	public String getChiNhanh() {
		return this.chinhanh;
	}

	@Override
	public void setMaNhanVien(String manhanvien) {
		this.manhanvien = manhanvien;
	}

	@Override
	public String getMaNhanVien() {
		return this.manhanvien;
	}

	@Override
	public void setMaThuViec(String mathuviec) {
		this.mathuviec = mathuviec;
	}

	@Override
	public String getMaThuViec() {
		return this.mathuviec;
	}

	@Override
	public void setTructhuoc(String Tructhuoc) {
		this.tructhuoc = Tructhuoc;
		
	}

	@Override
	public String getTructhuoc() {
		// TODO Auto-generated method stub
		return this.tructhuoc;
	}

	@Override
	public ResultSet getTructhuocRs() {
		// TODO Auto-generated method stub
		return this.tructhuocrs;
	}

	@Override
	public void setTructhuocRs(ResultSet tructhuocrs) {
		this.tructhuocrs = tructhuocrs;
		
	}

	@Override
	public String getNgaysinh() {
		// TODO Auto-generated method stub
		return this.ngaysinh;
	}

	@Override
	public void setNgaysinh(String ngaysinh) {
		this.ngaysinh = ngaysinh;
		
	}

	@Override
	public void setSoHD(String sohd) {
		this.sohd = sohd;
		
	}

	@Override
	public String getSoHD() {
		// TODO Auto-generated method stub
		return this.sohd;
	}
}
