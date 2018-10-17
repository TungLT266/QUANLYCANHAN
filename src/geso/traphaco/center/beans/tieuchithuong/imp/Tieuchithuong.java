package geso.traphaco.center.beans.tieuchithuong.imp;
import geso.traphaco.center.beans.tieuchithuong.ITieuchithuong;
import geso.traphaco.center.db.sql.*;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tieuchithuong implements ITieuchithuong{
	public String tctId; 	
	public String diengiai;
	public ResultSet tct;
	public ResultSet nhomsp;
	
	public String tungay;
	public String denngay;
	
	public String cty;
	public ResultSet CtyRs;
	
	public ResultSet nhomtc;
	public String nhomtcId;
	
	public String tcdiengiai;
	
	public String nhomspId;

	public String loai;
	
	public ResultSet htbhRs;
	public String htbh;
	
	public String loaiDS;
	
	public String thuongnsp;
	public String dstoithieudh;
	
	public String tongthuong;
	public String tldstoithieu;
	
	public String dvkdId;
	public String kbhId;
	public String thang;
	public String nam;
	public String tc;
	public String tcfk;
	public ResultSet tieuchi;

	public String[] ctct ;
	public String[] stt ;
	public String[] tu ;
	public String[] den ;
	public String[] thuong ;
	
	public String userId;	
	public String msg;
	public String action;
	
	public String LoaiTieuChi;
	ResultSet rsKenh;
	ResultSet rsDVKD;
	
	public String toithieu;
	public String toida;
	
	dbutils db;

	public Tieuchithuong()
	{
		this.tungay = "";
		this.denngay = "";
		
		this.dvkdId = "";
		this.kbhId = "";
		this.tctId = "";
		this.thang = "";
		this.nam = "";
		
		this.cty = "";
		
		this.loai = "";
		this.htbh = "";
		this.loaiDS = "";
		this.thuongnsp = "";
		this.dstoithieudh = "";
		
		this.nhomtcId = "";
		this.tcdiengiai = "";
		
		this.tc = "";
		this.tcfk = "";
		this.action = "";
		this.diengiai="";
		this.msg = "";
		this.db = new dbutils();		
		this.tct = null;
		this.LoaiTieuChi="";
		this.toithieu = "";
		this.toida = "";
		this.nhomspId = "";
	}
	
	public void setDiengiai(String diengiai){
		this.diengiai = diengiai;
	}
	
	public String getDiengiai(){
		return this.diengiai;
	}
	
	public void setDvkd(String dvkdId){
		this.dvkdId = dvkdId;
	}
	
	public String getDvkd(){
		return this.dvkdId;
	}

	public void setKbh(String kbhId){
		this.kbhId = kbhId;
	}
	
	public String getKbh(){
		return this.kbhId;
	}
	
	public void setNam(String nam){
		this.nam = nam;
	}
	
	public String getNam(){
		return this.nam;
	}

	public void setThang(String thang){
		this.thang = thang;
	}
	
	public String getThang(){
		return this.thang;
	}

	public void setTct(ResultSet tct){
		this.tct = tct;
	}
	
	public ResultSet getTct(){
		return this.tct;
	}

	public void setTctId(String tctId){
		this.tctId = tctId;
	}
	
	public String getTctId(){
		return this.tctId;
	}
	
	public void setTieuchi(String tc){
		this.tc = tc;
	}
	
	public String getTieuchi(){
		return this.tc;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}
	
	public String getUserId(){
		return this.userId;
	}

	public void setMsg(String msg){
		this.msg = msg;
	}
	
	public String getMsg(){
		return this.msg;
	}

	public void setAction(String action){
		this.action = action;
	}
	
	public String getAction(){
		return this.action;
	}

	public void setTieuchiRS(ResultSet tieuchi){
		this.tieuchi = tieuchi;
	}
	
	public ResultSet getTieuchiRS(){
		return this.tieuchi;
	}

	public void setData(String[] ctct, String[] stt, String[] tu, String[] den, String[] thuong){
		this.ctct = ctct;
		this.stt = stt;
		this.tu = tu;
		this.den = den;
		this.thuong = thuong;
		
	}
	
	private boolean kiemtra()
	{
		try{
			String query = "SELECT COUNT(PK_SEQ) AS NUM FROM TIEUCHITINHTHUONG WHERE THANG = '" + this.thang + "' AND NAM = '" + this.nam + "' AND HETHONGBH_FK = '"+ this.htbh +"' AND DVKD_FK = '" + this.dvkdId + "' AND KBH_FK = '"+ this.kbhId + "' AND CONGTY_FK = '"+ this.cty +"' AND PK_SEQ NOT IN ('" + this.tctId + "')";
			System.out.println(query);
			ResultSet rs = db.get(query);
			rs.next();
			int count = rs.getInt("NUM");
			if(count > 0)
			{return false;}
		}catch(Exception ex){return false;}
		return true;
	}
	
	public boolean Save()
	{
		String query;
		boolean result = true;
			if(this.tctId.length() > 0)
			{
			if(kiemtra())
			{
				query = "UPDATE TIEUCHITINHTHUONG SET CONGTY_FK = '"+ this.cty +"', DIENGIAI = N'"+ this.diengiai + "', thang = '" + this.thang + "', nam = '" + this.nam + "'," +
						"DVKD_FK='" + this.dvkdId + "', KBH_FK = '"+ this.kbhId + "', NGUOISUA ='" + this.userId + "', NGAYSUA = '" + this.getDateTime() + "', tongthuong = '"+ this.tongthuong.replaceAll(",", "") +"', " +
						"TYLEDOANHSOTOITHIEU = '"+ this.tldstoithieu.replaceAll(",", "") +"' " +
						"WHERE PK_SEQ = '" + this.tctId + "'";
				
				System.out.println("115.Update TCT: " + query);
				if(!this.db.update(query)){
					result = false;
				}
			
				int num = 1;
				String chuoi = "";
				System.out.println("result : "+result);
				if(result)
				{
					
					try{
						//System.out.println("bo ay : "+this.ctct.length);
						if(this.ctct.length > 0)
						{
							for(int i = 0; i < this.ctct.length; i++)
							{
								chuoi += this.ctct[i] +","+ this.stt[i] +","+ this.tu[i].replaceAll(",", "") +","+ this.den[i].replaceAll(",", "") +","+ this.thuong[i].replaceAll(",", "") +";";
							}
						}
						
						String denngay = "";
						String tungay = "";
						if(this.denngay == "")
						{
							denngay = null;
						}
						else
						{
							denngay = "'"+ this.denngay +"'"; 
						}
						
						if(this.tungay == "")
						{
							tungay = null;
						}
						else
						{
							tungay = "'"+ this.tungay +"'"; 
						}
						
						if(chuoi!="")
						{
							
							
							query = " UPDATE TIEUCHITHUONG_CHITIET SET NOIDUNG = '" + chuoi.substring(0, chuoi.length() - 1) + "', " +
							" NGUOISUA = '" + this.userId + "', NGAYSUA = '" + this.getDateTime() + "', TOITHIEU = '"+ this.toithieu.replaceAll(",", "") +"', " +
							" TOIDA = '"+ this.toida.replaceAll(",", "") +"', NHOMSP_FK = "+ (this.nhomspId == "" ? null :  this.nhomspId) +", THUONG = '"+ this.thuongnsp.replaceAll(",", "") +"', LOAI = '"+ this.loai +"', DSTOITHIEUDH = '"+ this.dstoithieudh.replaceAll(",", "") +"', LOAIDS = '"+ this.loaiDS +"', TUNGAY = "+ tungay +", DENNGAY = "+ denngay +" "+
							" WHERE TIEUCHITINHTHUONG_FK = '" + this.tctId + "' AND PK_SEQ = '" + this.tcfk + "' ";
						}
						else
						{
							query = " UPDATE TIEUCHITHUONG_CHITIET SET NGUOISUA = '" + this.userId + "', NGAYSUA = '" + this.getDateTime() + "', TOITHIEU = '"+ this.toithieu.replaceAll(",", "") +"', " +
							" TOIDA = '"+ this.toida.replaceAll(",", "") +"', NHOMSP_FK = "+ (this.nhomspId == "" ? null :  this.nhomspId) +", THUONG = '"+ this.thuongnsp.replaceAll(",", "") +"', LOAI = '"+ this.loai +"', DSTOITHIEUDH = '"+ this.dstoithieudh.replaceAll(",", "") +"', LOAIDS = '"+ this.loaiDS +"', TUNGAY = "+ tungay +", DENNGAY = "+ denngay +" "+
							" WHERE TIEUCHITINHTHUONG_FK = '" + this.tctId + "' AND PK_SEQ = '" + this.tcfk + "' ";
						}
						
						
						
						System.out.println("116.Update TCT lan 2 : " + query);
						if(!this.db.update(query))
						{
							result = false;
						}
						
						
						/*if(chuoi!="")
						{*/
							query = "DELETE TIEUCHITHUONG_CT_MUCTHUONG WHERE TCTCT_FK IN (SELECT PK_SEQ FROM TIEUCHITHUONG_CHITIET WHERE TIEUCHITINHTHUONG_FK = '" + this.tctId + "')";
							System.out.println(query);
							if(!this.db.update(query))
							{
								result = false;
							}
							
							query = "SELECT PK_SEQ AS TCTCT_FK, NOIDUNG FROM TIEUCHITHUONG_CHITIET WHERE TIEUCHITINHTHUONG_FK = '"+ tctId +"'";
							System.out.println(query);
							ResultSet rs = db.get(query);
							while(rs.next())
							{
								String[] strNoidung = rs.getString("noidung").split(";");
								for(int i = 0; i < strNoidung.length; i++)
								{ 
									query = "INSERT INTO TIEUCHITHUONG_CT_MUCTHUONG (TCTCT_FK, STT, TU, DEN, THUONG) VALUES ('"+ rs.getString("TCTCT_FK") +"', '"+ strNoidung[i].split(",")[1] +"', '"+ strNoidung[i].split(",")[2] +"', '"+ strNoidung[i].split(",")[3] +"', '"+ strNoidung[i].split(",")[4] +"')";
									this.db.update(query);
								}
							}
							
							/*String[] chuoisplit = chuoi.split(";");
							
							for(int j = 0; j < chuoisplit.length; j++)
							{
								String []str = chuoisplit[j].split(",");
								query = "INSERT INTO TIEUCHITHUONG_CT_MUCTHUONG(TCTCT_FK, STT, TU, DEN, THUONG) VALUES ('"+ this.tcfk +"', '"+ str[1] +"', '"+ str[2] +"', '"+ str[3] +"', '"+ str[4] +"')";
								System.out.println("muc thuong : " + query);
								if(!this.db.update(query))
								{
									result = false;
								}	
								
							}*/
						//}

					}catch(Exception ex){}
				}
			}
			else
			{
				result = false;
			}
		}
		
		if(result) 
			this.msg = "Tiêu chí tính thưởng đã được cập nhật";
		else
			this.msg = "Khai báo tiêu chí trùng điều kiện. Vui lòng điều chỉnh lại thông tin !";
		return result;
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd: hh-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);	
	}	
	
	public void init(){
		CreateRs();
		String query = "";
		if(this.action.equals("copy")){
			query = "INSERT INTO TIEUCHITINHTHUONG(THANG, NAM, DVKD_FK, KBH_FK, LOAI, TONGTHUONG, TYLEDOANHSOTOITHIEU, HETHONGBH_FK, CONGTY_FK) " +
					"SELECT THANG, NAM, DVKD_FK, KBH_FK, LOAI, TONGTHUONG, TYLEDOANHSOTOITHIEU, HETHONGBH_FK, CONGTY_FK FROM TIEUCHITINHTHUONG WHERE PK_SEQ = '" + this.tctId + "'";
			this.db.update(query);
			
			query = "SELECT SCOPE_IDENTITY() as id";
			ResultSet rs = this.db.get(query);
			try{
				rs.next();
				String id = rs.getString("id");
				query = "UPDATE TIEUCHITINHTHUONG SET DIENGIAI = ' ', THANG = ' ', NGUOITAO='" + this.userId + "', NGUOISUA='" + this.userId + "'," +
						"NGAYTAO = '" + this.getDateTime() + "', NGAYSUA = '" + this.getDateTime() + "', TRANGTHAI = '0' " +
						"WHERE PK_SEQ ='" + id + "'";
				this.db.update(query);

					/*query = "INSERT INTO TIEUCHITINHTHUONG_CHITIET (TIEUCHITINHTHUONG_FK, STT, Diengiai, Tu, Den, Toantu, Thuong, Tieuchi, Nguoisua, Ngaysua) Values('" +
							"" + id + "','" + rs.getString("stt") + "', N'" + rs.getString("diengiai") + "', '" + rs.getString("tu") + "','" +
							"" + rs.getString("Den") + "','<', '" + rs.getString("thuong") + "','" + rs.getString("tieuchi") + "','" + this.userId + "','" + this.getDateTime() + "')";*/
					
					query = "INSERT INTO TIEUCHITHUONG_CHITIET (TIEUCHITINHTHUONG_FK, DIENGIAI, NOIDUNG, TIEUCHI, NGUOISUA, NGAYSUA, TOITHIEU, TOIDA, NHOMSP_FK, THUONG, DSTOITHIEUDH, LOAI, LOAIDS, LOAINVBH, TUNGAY, DENNGAY) " +
							"SELECT '"+ id +"' AS TIEUCHITINHTHUONG_FK, DIENGIAI, NOIDUNG, TIEUCHI, '"+ this.userId +"' AS NGUOISUA, '"+ this.getDateTime() +"' AS NGAYSUA, TOITHIEU, TOIDA, NHOMSP_FK, THUONG, DSTOITHIEUDH, LOAI, LOAIDS, LOAINVBH, TUNGAY, DENNGAY " +
							"FROM TIEUCHITHUONG_CHITIET WHERE TIEUCHITINHTHUONG_FK = '"+ this.tctId +"' ";
							
					//System.out.println("insert : "+query);
					this.db.update(query);
					
					query = "SELECT PK_SEQ AS TCTCT_FK, NOIDUNG FROM TIEUCHITHUONG_CHITIET WHERE TIEUCHITINHTHUONG_FK = '"+ tctId +"'";
					rs = db.get(query);
					while(rs.next())
					{
						String[] strNoidung = rs.getString("noidung").split(";");
						for(int i = 0; i < strNoidung.length; i++)
						{ 
							query = "INSERT INTO TIEUCHITHUONG_CT_MUCTHUONG (TCTCT_FK, STT, TU, DEN, THUONG) VALUES ('"+ rs.getString("TCTCT_FK") +"', '"+ strNoidung[i].split(",")[1] +"', '"+ strNoidung[i].split(",")[2] +"', '"+ strNoidung[i].split(",")[3] +"', '"+ strNoidung[i].split(",")[4] +"')";
							this.db.update(query);
						}
					}
					
					/*query = "SELECT PK_SEQ AS TCTCT_FK, NOIDUNG FROM TIEUCHITHUONG_CHITIET WHERE TIEUCHITINHTHUONG_FK = '"+ tctId +"'";
					while(rs.next)
					
					query = "INSERT INTO TIEUCHITHUONG_CT_MUCTHUONG (TCTCT_FK, STT, TU, DEN, THUONG) " +
					"SELECT '"+ id +"' AS TEUCHITINHTHUONG_FK, DIENGIAI, NOIDUNG, TIEUCHI, '"+ this.userId +"' AS NGUOISUA, '"+ this.getDateTime() +"' AS NGAYSUA, TOITHIEU, TOIDA, NHOMSP_FK, THUONG, DSTOITHIEUDH, LOAI, LOAIDS, LOAINVBH, TUNGAY, DENNGAY " +
					"FROM TIEUCHITHUONG_CHITIET WHERE TIEUCHITINHTHUONG_FK = '"+ this.tctId +"' ";*/
					
				this.tctId = id;
			}catch(Exception e){}
		}
		
		if(this.tctId.length() > 0){
			query = " SELECT TCT.PK_SEQ, TCT.DIENGIAI, KBH.PK_SEQ AS KBHID, HETHONGBH_FK, CONGTY_FK, " + 
					" DVKD.PK_SEQ AS DVKDID, TCT.THANG, TCT.NAM ,tct.loai, ISNULL(TCT.TONGTHUONG,0) AS TONGTHUONG, ISNULL(TCT.TYLEDOANHSOTOITHIEU, 0) AS TYLEDOANHSOTOITHIEU " +
					" FROM TIEUCHITINHTHUONG TCT " +
					" INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ = TCT.KBH_FK " +
					" INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = TCT.DVKD_FK " +
					" WHERE TCT.PK_SEQ = '" + this.tctId + "' ";
			System.out.println(query);
			this.tct = this.db.get(query);
			try{
				this.tct.next();
				this.diengiai = this.tct.getString("diengiai");
				this.kbhId = this.tct.getString("kbhId");
				this.dvkdId = this.tct.getString("dvkdId");
				this.thang = this.tct.getString("thang");
				this.nam = this.tct.getString("nam");
				this.LoaiTieuChi=this.tct.getString("loai");
				this.htbh = this.tct.getString("HETHONGBH_FK");
				
				this.cty = this.tct.getString("CONGTY_FK");
				System.out.println("Congty = " + this.cty);
				this.tongthuong = this.tct.getString("tongthuong");
				this.tldstoithieu = this.tct.getString("tyledoanhsotoithieu");
				
				//System.out.println("Loai nek k: "+this.tct.getString("loai"));
				
			}catch(Exception e){}
		
			/*query = "SELECT * FROM TIEUCHITINHTHUONG_CHITIET " + 
					"WHERE TIEUCHITINHTHUONG_FK = '"+ this.tctId +"' AND TIEUCHI='" + tc + "'";*/
			
			
				query = 				
				"SELECT pk_seq, tieuchitinhthuong_fk, pk_seq as tcfk, noidung, nguoisua, ngaysua, isnull(toithieu, 0) as toithieu, isnull(toida, 0) as toida, isnull(nhomsp_fk, 0) as nhomsp_fk, isnull(thuong, 0) as thuong, isnull(loai, 0) as loai, isnull(dstoithieudh, 0) as dstoithieudh,   " +
				"ISNULL(LOAIDS, 0) AS LOAIDS, ISNULL(TUNGAY, '') AS TUNGAY, ISNULL(DENNGAY, '') AS DENNGAY " +
				"FROM TIEUCHITHUONG_CHITIET " + 
				"WHERE TIEUCHITINHTHUONG_FK = '"+ this.tctId +"' AND tieuchi='" + tc + "' ";

				if(this.tcfk != null && !this.tcfk.equals(""))
				{ query += " and pk_seq = '"+ this.tcfk +"'"; }
				this.tct = this.db.getScrol(query);
			
			System.out.println("1.Query Khoi Tao: " + query);
			
			//query  = "SELECT DISTINCT TIEUCHI AS TC, DIENGIAI FROM TIEUCHITINHTHUONG_CHITIET WHERE TIEUCHITINHTHUONG_FK='"+ this.tctId  +"'";
			query  = "SELECT pk_seq AS TC, tieuchi, DIENGIAI FROM TIEUCHITHUONG_CHITIET WHERE TIEUCHITINHTHUONG_FK='"+ this.tctId  +"' order by tieuchi, pk_seq asc ";
			System.out.println("2.Get Tieu Chi: " + query);
			
			this.tieuchi = this.db.get(query);
			
			query = "SELECT * FROM TIEUCHI WHERE LOAI = '"+ this.LoaiTieuChi +"'";
			System.out.println("3.Get Loai Tieu Chi: " + query);
			this.nhomtc = this.db.get(query);
			
			query = "SELECT PK_SEQ, DIENGIAI FROM HETHONGBANHANG WHERE TRANGTHAI = '1'";
			System.out.println("hethongbanhang : " + query);
			this.htbhRs = this.db.get(query);
			
			
			query = "select PK_SEQ, TEN, CONGTY_FK " +
					"from NHAPHANPHOI where loaiNPP = '0' and TRANGTHAI = '1' " +
					"and PK_SEQ in (  select Npp_fk from PHAMVIHOATDONG where Nhanvien_fk = '"+ this.userId +"' )";
			System.out.println("Congty : " + query);
			this.CtyRs = this.db.get(query);
		}
		
		
	}
	private void CreateRs() {
		
		this.rsDVKD=db.get("select pk_Seq,donvikinhdoanh as ten from donvikinhdoanh");
		this.rsKenh=db.get("select pk_Seq,ten from kenhbanhang");
		this.nhomsp = db.get("select PK_SEQ, (CAST(PK_SEQ AS VARCHAR(50)) + ' - ' + TEN) AS TEN from NHOMSANPHAM where TRANGTHAI = '1' and tinhthuong = '1'");
	}

	public void closeDB(){
		try{
				if(rsDVKD!=null){
					rsDVKD.close();
				}
				if(rsKenh!=null){
					rsKenh.close();
				}
				if (this.db != null)
					this.db.shutDown();
		}catch(Exception er){
			
		}
	}

	
	public void SetLoaiTieuChi(String loaitieuchi) {
		
		this.LoaiTieuChi=loaitieuchi;
	}

	
	public String GetLoaiTieuChi() {
		
		return this.LoaiTieuChi;
	}

	
	public ResultSet GetRsKenh() {
		
		return this.rsKenh;
	}

	
	public ResultSet GetRsDVKD() {
		
		return this.rsDVKD;
	}

	
	public void setToithieu(String toithieu) {
		
		this.toithieu = toithieu;
	}

	
	public String getToithieu() {
		
		return this.toithieu;
	}

	
	public void setToida(String toida) {
		
		this.toida = toida;
	}

	
	public String getToida() {
		
		return this.toida;
	}

	
	public ResultSet GetRsNhomSp() {
		
		return this.nhomsp;
	}

	
	public String getNhomsp() {
		
		return this.nhomspId;
	}

	
	public void setNhomsp(String nhomsp) {
		
		this.nhomspId = nhomsp;
	}

	
	public void setLoai(String loai) {
		
		this.loai = loai;
	}

	
	public String getLoai() {
		
		return this.loai;
	}

	
	public void setThuongnsp(String thuongnsp) {
		
		this.thuongnsp = thuongnsp;
	}

	
	public String getThuongnsp() {
		
		return this.thuongnsp;
	}

	
	public void setDstoithieuDH(String dstoithieudh) {
		
		this.dstoithieudh = dstoithieudh;
	}

	
	public String getDstoithieuDH() {
		
		return this.dstoithieudh;
	}

	
	public void setTongThuong(String tongthuong) {
		
		this.tongthuong = tongthuong;
	}

	
	public String getTongThuong() {
		
		return this.tongthuong;
	}

	
	public void setTileDStoithieu(String tldstoithieu) {
		
		this.tldstoithieu = tldstoithieu;
	}

	
	public String getTileDStoithieu() {
		
		return this.tldstoithieu;
	}

	
	public void setTieuchiFK(String tcfk) {
		
		this.tcfk = tcfk;	
	}

	
	public String getTieuchiFK() {
		
		return this.tcfk;
	}

	
	public void setLoaiDS(String loaids) {
		
		this.loaiDS = loaids;
	}

	
	public String getLoaiDS() {
		
		return this.loaiDS;
	}

	
	public void setNhomtcRS(ResultSet nhomtc) {
		
		this.nhomtc = nhomtc;
	}

	
	public ResultSet getNhomtcRS() {
		
		return this.nhomtc;
	}

	
	public void setTCDiengiai(String tcdiengiai) {
		
		this.tcdiengiai = tcdiengiai;
	}

	
	public String getTCDiengiai() {
		
		return this.tcdiengiai;
	}

	
	public void setTCNhomId(String nhomtcid) {
		
		this.nhomtcId = nhomtcid;
	}

	
	public String getTCNhomId() {
		
		return this.nhomtcId;
	}

	@Override
	public boolean xoaTC() {
		
		boolean result = true;
		String query = "DELETE TIEUCHITHUONG_CHITIET WHERE PK_SEQ = '"+ this.tcfk +"' AND TIEUCHITINHTHUONG_FK = '"+ this.tctId +"'";
		System.out.println("delete tc : "+query);
		if(!this.db.update(query)){
			result = false;
		}
		if(result) 
			this.msg = "Tiêu chí đã được xóa.";
		else
			this.msg = "Lỗi trong quá trình xóa tiêu chí. Vui lòng liên hệ trung tâm để được xử lý.";
		return result;
	}

	@Override
	public String getTungay() {
		// TODO Auto-generated method stub
		return this.tungay;
	}

	@Override
	public void setTungay(String tungay) {
		// TODO Auto-generated method stub
		this.tungay = tungay;
	}

	@Override
	public String getDenngay() {
		// TODO Auto-generated method stub
		return this.denngay;
	}

	@Override
	public void setDenngay(String denngay) {
		// TODO Auto-generated method stub
		this.denngay = denngay;
	}

	@Override
	public void sethethongBH(String htbh) {
		// TODO Auto-generated method stub
		this.htbh = htbh;
	}

	@Override
	public String gethethongBH() {
		// TODO Auto-generated method stub
		return this.htbh;
	}

	@Override
	public void setHtbhRs(ResultSet htbhRs) {
		// TODO Auto-generated method stub
		this.htbhRs = htbhRs;
	}

	@Override
	public ResultSet getHtbhRs() {
		// TODO Auto-generated method stub
		return this.htbhRs;
	}

	@Override
	public void SetCongty(String cty) {
		// TODO Auto-generated method stub
		this.cty = cty;
	}

	@Override
	public String GetCongty() {
		// TODO Auto-generated method stub
		return this.cty;
	}

	@Override
	public void setCongtyRs(ResultSet ctyRs) {
		// TODO Auto-generated method stub
		this.CtyRs = ctyRs;
	}

	@Override
	public ResultSet getCongtyRs() {
		// TODO Auto-generated method stub
		return this.CtyRs;
	}
}
