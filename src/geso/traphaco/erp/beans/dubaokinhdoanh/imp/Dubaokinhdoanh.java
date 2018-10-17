package geso.traphaco.erp.beans.dubaokinhdoanh.imp;

import geso.dms.db.sql.dbutils;
import geso.traphaco.erp.beans.dubaokinhdoanh.IDubaokinhdoanh;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class Dubaokinhdoanh implements IDubaokinhdoanh
{
	String CTYID, USERID,ID,MSG,KHO,DIENGIAI,TRANGTHAI,NGAYDUBAO,SOTHANGDUBAO,PHUONGPHAP,LOAISOVOI,TENSANPHAM,NHANHANG,CHUNGLOAI;
	ResultSet rsKho,rsPhuongphap,rsSovoi,rsSanpham,rsNhanhang,rsChungloai;
	String maspstr;
	String[][] data;
	String[] spIds;
	String[] selectedSpIds;
	String[] ngaythang;
	String ngaytonkho;
	ResultSet khRS;
	int count;
	dbutils db;
	public Dubaokinhdoanh()
	{
		this.db=new dbutils();
		this.CTYID = "";
		this.USERID="";
		this.ID="";
		this.KHO="";
		this.NGAYDUBAO= this.getDateTime();
		this.LOAISOVOI="";
		this.SOTHANGDUBAO="6";
		this.TENSANPHAM="";
		this.PHUONGPHAP="";
		this.DIENGIAI="";
		this.TRANGTHAI="";
		this.NHANHANG="";
		this.CHUNGLOAI="";
		this.ngaytonkho = "";
		this.MSG="";
		this.maspstr = "";
	
	}
	public Dubaokinhdoanh(String id)
	{
		this.db=new dbutils();
		this.CTYID = "";
		this.ID=id;
		this.USERID="";
		this.DIENGIAI="";
		this.ID=id;
		this.KHO="";
		this.NGAYDUBAO= this.getDateTime();
		this.SOTHANGDUBAO="6";
		this.PHUONGPHAP="";
		this.NHANHANG="";
		this.CHUNGLOAI="";
		this.MSG="";
		this.maspstr = "";
		this.ngaytonkho = "";
	}
	
	public String getCtyId() 
	{
		
		return this.CTYID;
	}

	
	public void setCtyId(String ctyId) 
	{
		
		this.CTYID = ctyId;
	}
	
	
	public String getUserId() {
		
		return this.USERID;
	}

	
	public void setUserId(String userId) {
		
		this.USERID=userId;
	}

	
	public String getId() {
		
		return this.ID;
	}

	
	public void setId(String id) 
	{
		
		this.ID=id;
	}

	public int getCount() {
		
		return this.count;
	}

	
	public void setCount(int count) 
	{
		
		this.count = count;
	}
	
	public String getNgaytonkho() {
		
		return this.ngaytonkho;
	}

	
	public void setNgaytonkho(String ngaytonkho) 
	{
		
		this.ngaytonkho = ngaytonkho;
	}

	public String getMsg() {
		
		return this.MSG;
	}

	
	public void setMsg(String msg) 
	{
		this.MSG=msg;	
	}

	
	public String getNgaydubao() {
		
		return this.NGAYDUBAO;
	}

	
	public void setNgaydubao(String ngaydubao) {
		
		this.NGAYDUBAO=ngaydubao;
	}


	public String[] getNgayThang() {
		
		return this.ngaythang;
	}

	public String getTrangthai() {
		
		return this.TRANGTHAI;
	}

	
	public void setTrangthai(String trangthai) {
		
		this.TRANGTHAI=trangthai;
	}

	
	public String getDiengiai() {
		
		return this.DIENGIAI;
	}

	
	public void setDiengiai(String diengiai) {
		
		this.DIENGIAI=diengiai;
	}

	
	public String getKho() {
		
		return this.KHO;
	}

	
	public void setKho(String kho) {
		
		this.KHO=kho;
	}

	
	public String getSothangdubao() {
		
		return this.SOTHANGDUBAO;
	}

	
	public void setSothangdubao(String sothangdubao) {
		
		this.SOTHANGDUBAO=sothangdubao;
	}

	
	public String getPhuongphap() {
		
		return this.PHUONGPHAP;
	}

	
	public void setPhuongphap(String phuongphap) {
		
		this.PHUONGPHAP=phuongphap;
	}

	public ResultSet getKhoList() {
		
		return this.rsKho;
	}


	
	public void setKhoList(ResultSet kholist) {
		
		this.rsKho=kholist;
	}


	
	public ResultSet getPhuongphapList() {
		
		return this.rsPhuongphap;
	}


	
	public void setPhuongphapList(ResultSet phuongphaplist) {
		
		this.rsPhuongphap=phuongphaplist;
	}
	
	public String[][] getData() {
		return this.data;
	}
	
	public void setData(String[][] data) {
		this.data = data;
	}

	public ResultSet getSovoi() {
		return this.rsSovoi;
	}

	public void setSovoi(ResultSet sovoi) {
		this.rsSovoi=sovoi;
	}

	public ResultSet getSanPhamRs() {
		return this.rsSanpham;
	}

	public void setSanPhamRs(ResultSet sanpham) {
		this.rsSanpham=sanpham;
		
	}

	public String[] getSanPhamIds() {

		return this.spIds;
	}
	
	public void setSanPhamIds(String[] spIds) {
		this.spIds = spIds;
	}
	
	public String[] getSelectedSpIds() {

		return this.selectedSpIds ;
	}
	
	public void setSelectedSpIds(String[] selectedSpIds) {
		this.selectedSpIds = selectedSpIds;
	}

	public String getTenPhamIds() {
		return this.TENSANPHAM;
	}
	
	public void setTenPhamIds(String rensanpham) {
		
		this.TENSANPHAM=rensanpham;
	}
	
	public String getNhanhang() {
		
		return this.NHANHANG;
	}
	
	public void setNhanhang(String nhanhang) {
		
		this.NHANHANG=nhanhang;
	}
	
	public String getChungloai() {
		
		return this.CHUNGLOAI;
	}
	
	public void setChungloai(String Chungloai) {
		
		this.CHUNGLOAI=Chungloai;
	}
	
	public ResultSet getNhanhangList() {
		
		return this.rsNhanhang;
	}
	
	public void setNhanhangList(ResultSet nhanhanglist) {
		
		this.rsNhanhang = nhanhanglist;
	}
	
	public ResultSet getKhRS() {
		
		return this.khRS;
	}
	
	public void setKhRS(ResultSet khRS) {
		
		this.khRS = khRS;
	}

	public ResultSet getChungloaiList() {
		
		return this.rsChungloai;
	}
	
	public void setChungloaiList(ResultSet chungloailist) {
		
		this.rsChungloai=chungloailist;
	}
	
	public String getMaspstr() 
	{
		return this.maspstr;
	}
	
	public void setMaspstr(String maspstr) 
	{
		this.maspstr = maspstr;
	}

	

	public void createRs() 
	{
		this.rsKho=this.db.get("select PK_SEQ,TEN,DIACHI  from ERP_KHOTT WHERE TRANGTHAI = '1' AND LOAI = 3 AND CONGTY_FK = " + this.CTYID );
				
		this.rsSovoi=this.db.getScrol("select PK_SEQ,TEN from ERP_LOAISOVOI WHERE TRANGTHAI = '1'" );

		this.rsNhanhang=this.db.get("select PK_SEQ,TEN from NHANHANG WHERE TRANGTHAI = '1' AND CONGTY_FK = " + this.CTYID  );
		
		this.rsChungloai=this.db.get("select PK_SEQ,TEN from CHUNGLOAI WHERE TRANGTHAI = '1' AND CONGTY_FK = " + this.CTYID );
		
//		this.rsPhuongphap=this.db.get("select PK_SEQ,TENPHUONGPHAP  from ERP_PHUONGPHAPDUBAO" );			
		
		String query = "select pk_seq, ma, ten from erp_sanpham where trangthai = '1' AND CONGTY_FK = " + this.CTYID ;
		if(NHANHANG.length()>0)
			query = "select pk_seq, ma, ten from erp_sanpham where trangthai = '1' and nhanhang_fk='"+this.NHANHANG+"'";
		
		if(CHUNGLOAI.length()>0)
			query = "select pk_seq, ma, ten from erp_sanpham where trangthai = '1' and chungloai_fk='"+this.CHUNGLOAI+"'";
		
		if(CHUNGLOAI.length()>0 && NHANHANG.length()>0)
			query = "select pk_seq, ma, ten from erp_sanpham where trangthai = '1' and chungloai_fk='"+this.CHUNGLOAI+"' and NHANHANG_FK='"+this.NHANHANG+"'";
			
		this.rsSanpham = db.get(query);
		
		query = "SELECT PK_SEQ AS KHID, TEN FROM ERP_KHACHHANG WHERE CONGTY_FK = 100001 AND MAKETOSTOCK = 0"; 
		this.khRS = this.db.get(query);
		
		int th=0;
       	this.ngaythang= this.getNgaydubao().split("-");
       	
       	if(this.ngaythang.length!=1){
       		
        	th = Integer.parseInt(this.ngaythang[1]);
        	
        	if(th == 12) {
        		th = 1;        		
        		this.ngaythang[1] = "1";        		
        		this.ngaythang[0] = "" +  (Integer.parseInt(this.ngaythang[0]) + 1);
        	}else{
        		th = th + 1;
        		this.ngaythang[1] = "" +  (Integer.parseInt(this.ngaythang[1]) + 1);
        	}

       	}
       	
		System.out.println("So thang du bao: "+this.SOTHANGDUBAO);
		query = "SELECT	COUNT(SP.PK_SEQ) AS NUM " +
				"FROM ERP_DUBAO DUBAO " +
				"INNER JOIN ERP_DUBAOSANPHAM DUBAOSP on DUBAO.PK_SEQ = DUBAOSP.DUBAO_FK " +
				"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = DUBAOSP.SANPHAM_FK  " + 
				"WHERE DUBAO.PK_SEQ = '" + this.ID + "' AND DUBAOSP.THANG = '" + th + "' " +
				"AND DUBAOSP.NAM = '" + ngaythang[0] + "' " ;
		
		if(this.CHUNGLOAI.length() > 0){
			query = query + " AND SP.CHUNGLOAI_FK = '" + this.CHUNGLOAI + "' "; 
		}
		
		if(this.NHANHANG.length() > 0){
			query = query + " AND SP.NHANHANG_FK = '" + this.NHANHANG + "' ";
		}
		
		if(this.selectedSpIds != null){
			String tmp = "(";
			for (int i = 0; i < this.selectedSpIds.length; i++){
				tmp = tmp + this.selectedSpIds[i] + ", ";
			}
			tmp = tmp + ")";
			query = query + " AND SP.PK_SEQ IN " + tmp;
		}
		
		System.out.println("Cau query : " + query);
		
		ResultSet rs = this.db.get(query);
		int count = 0;
		try{
			rs.next();
			count = rs.getInt("NUM");
			this.count = count*9+3;
			
			rs.close();
			
			String[][] data = new String[count*9 + 3][Integer.parseInt(this.SOTHANGDUBAO)];
			
			for(int i=0; i < Integer.parseInt(this.SOTHANGDUBAO); i++)
			{
			
				if(this.ID.length() > 0)
				{
					query =	"SELECT	DUBAOSP.NAM, DUBAOSP.THANG, DUBAO.PK_SEQ, SP.PK_SEQ AS SPID, SP.MA AS MASANPHAM, " +
							"SP.TEN AS TENSANPHAM, ISNULL(DUBAOSP.TONDAU, '0') AS TONDAU, ISNULL(DUBAOSP.TANGTRUONG, '0') AS TANGTRUONG, ISNULL(DUBAOSP.SOVOI_FK, '0') AS SOVOI, " +
							"ISNULL(DUBAOSP.TONKHOANTOAN, '0') AS TONKHOANTOAN, ISNULL(DUBAOSP.SANXUAT, '0') AS SANXUAT , ISNULL(DUBAOSP.TONCUOI, '0') AS TONCUOI, ISNULL(DUBAOSP.DUKIENBAN, '0') AS DUKIENBAN, " +
							"ISNULL(DUBAOSP.SONGAYBANHANG, '24')  AS SONGAYBANHANG, " +
							"DUBAOSP.AVG3M, DUBAOSP.AVG6M, DUBAOSP.LASTYEAR " + 
							"FROM ERP_DUBAO DUBAO " +
							"INNER JOIN ERP_DUBAOSANPHAM DUBAOSP on DUBAO.PK_SEQ = DUBAOSP.DUBAO_FK " +
							"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = DUBAOSP.SANPHAM_FK " +   
							"WHERE DUBAO.PK_SEQ = '" + this.ID + "' AND DUBAOSP.THANG = '" + th + "' AND DUBAOSP.NAM = '" + ngaythang[0] + "' ";
									 
					if(this.CHUNGLOAI.length() > 0){
						query = query + " AND SP.CHUNGLOAI_FK = '" + this.CHUNGLOAI + "' "; 
					}
					
					if(this.NHANHANG.length() > 0){
						query = query + " AND SP.NHANHANG_FK = '" + this.NHANHANG + "' ";
					}
					
					if(this.selectedSpIds != null){
						String tmp = "(";
						for (int k = 0; k < this.selectedSpIds.length; k++){
							tmp = tmp + this.selectedSpIds[k] + ", ";
						}
						tmp = tmp + ")";
						query = query + " AND SP.PK_SEQ IN " + tmp;
					}

					System.out.println(query);
					rs = this.db.get(query);
					count = 0;
					while(rs.next()){
						if(count == 0) {
							data[count++][i] = rs.getString("SONGAYBANHANG");
							data[count++][i] = rs.getString("TANGTRUONG");
							data[count++][i] = rs.getString("SOVOI");
						}

						data[count++][i] = rs.getString("SPID")+ ";" + rs.getString("MASANPHAM") + " - " + rs.getString("TENSANPHAM");
						data[count++][i] = rs.getString("TONDAU") ;
						data[count++][i] = rs.getString("DUKIENBAN") ;
						data[count++][i] = rs.getString("TONKHOANTOAN") ;
						data[count++][i] = rs.getString("SANXUAT") ;
						data[count++][i] = rs.getString("TONCUOI") ;
						data[count++][i] = rs.getString("AVG3M") ;
						data[count++][i] = rs.getString("AVG6M") ;
						data[count++][i] = rs.getString("LASTYEAR") ;

						//Tao danh sach cac san pham, chi dung 1 thang dau de tao danh sach
						if(i == 0){
							if (this.maspstr.length()==0){
								this.maspstr = "'" + rs.getString("SPID") + "'";
							}else{
								this.maspstr = this.maspstr + ",'" + rs.getString("SPID") + "'";
							}				
						}
					}
					System.out.println("Kiem tra count:" + count);
					rs.close();
				}		
				
				if(th==12)
				{
					th=1;
					ngaythang[0]=(Integer.parseInt(ngaythang[0])+1)+"";
				}
				else
 				{
// 					if(i>=1)
 					th +=1;
 				}

			}
			this.data = data;
			
		}catch(java.sql.SQLException e){}
	}

	public void createRs_New() 
	{
		String query = "SELECT PK_SEQ AS KHID, TEN FROM ERP_KHACHHANG WHERE CONGTY_FK = 100001 AND MAKETOSTOCK = 0"; 
		this.khRS = this.db.get(query);

		this.rsKho=this.db.get("select PK_SEQ,TEN,DIACHI  from ERP_KHOTT WHERE TRANGTHAI = '1' and CONGTY_FK = " + this.CTYID + " and LOAI = 3");
	}

	public void init() 
	{
		String query = "select *  from ERP_DUBAO where pk_seq = '" + this.ID + "'";
		System.out.println("Cau query la "+query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ID = rs.getString("PK_SEQ");
					this.DIENGIAI=rs.getString("DIENGIAI");
//					this.PHUONGPHAP = rs.getString("PHUONGPHAP_FK");
					this.NGAYDUBAO = rs.getString("NGAYDUBAO");
					
					if(rs.getString("NGAYTONKHOANTOAN") == null || rs.getString("NGAYTONKHOANTOAN").length() == 0)
						this.ngaytonkho = "0";
					else
						this.ngaytonkho = rs.getString("NGAYTONKHOANTOAN");
					this.KHO = rs.getString("KHO_FK");
				}
				rs.close();
			} 
			catch (SQLException e) 
			{
				System.out.println("Loi roi" + e.toString());
			}
		}
		//Lay chi tiet du bao
		
		this.createRs();	
	}


	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public boolean CreateDubao(HttpServletRequest request)  throws ServletException, IOException
	{
		System.out.println("vao day ............insert");
		String ngaytao = this.getDateTime();
		
		try 
		{
			this.db.getConnection().setAutoCommit(false);
			System.out.print("---------------Kho------------ "+this.getKho());
			String query = 	"INSERT ERP_DUBAO (CONGTY_FK, KHO_FK, NGAYDUBAO, DIENGIAI, TRANGTHAI, NGUOITAO, NGAYTAO, NGUOISUA, NGAYSUA, NGAYTONKHOANTOAN) " +
							"VALUES(" + this.CTYID + ", '"+this.KHO +"','"+this.NGAYDUBAO+"',N'"+this.DIENGIAI+"','"+"0"+"','"+USERID+"', " +
							"'"+ngaytao+"','"+USERID+"','"+ngaytao+"', '" + this.ngaytonkho + "')";
			System.out.println(query);
			if(!db.update(query))
			{
				this.MSG = "Không thể tạo dự báo kinh doanh lỗi : " + query;
				db.getConnection().rollback();
				return false;
			}

			query = "select SCOPE_IDENTITY() as dubaoId";
			
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				if(rs.next())
				{
					this.ID = rs.getString("dubaoId");
					rs.close();
				}
			}
			else
			{
				this.MSG = "Lổi trong lúc lấy PK dự báo kinh doanh : " + query;
				db.getConnection().rollback();
				return false;
			}
			System.out.println("id"+this.ID);
			this.CreateDubaoSP(request);
			
		}catch(java.sql.SQLException e){
			return false;
		}
		
		return true;
	}	
	
	private boolean CreateDubaoSP(HttpServletRequest request)  throws ServletException, IOException{
		String ngaytao = this.getDateTime();
		try{
			this.db.getConnection().setAutoCommit(false);
			String query = "SELECT DATEADD(d,-1, DATEADD(mm, DATEDIFF(mm, 0 ,'"+ ngaytao + "'), 0)) AS NGAY";
		
			ResultSet rs = this.db.get(query);
						
			rs.next();
		
			String  ngaytd = rs.getString("NGAY").substring(0, 10);
		
			rs.close();
		
			query = "SELECT DATEADD(d,-1, DATEADD(mm, DATEDIFF(mm, 0 ,'"+ ngaytd +"')-3, 0)) AS NGAY";
		
			rs = this.db.get(query);
		
			rs.next();
		
			String  ngaydsd3M = rs.getString("NGAY").substring(0, 10);
		
			rs.close();
		
			query = "SELECT DATEADD(d,-1, DATEADD(mm, DATEDIFF(mm, 0 ,'"+ ngaytd +"')- 6, 0)) AS NGAY";
		
			rs = this.db.get(query);
		
			rs.next();
		
			String  ngaydsd6M = rs.getString("NGAY").substring(0, 10);
		
			rs.close();

			String tonkhoantoan = request.getParameter("ngaytonkho");
			int thang = Integer.valueOf(ngaytao.substring(5, 7)).intValue();
			int nam   = Integer.valueOf(ngaytao.substring(0, 4)).intValue();
			
			if(thang == 12){
				thang = 1;
				nam = nam + 1;
			}else{
				thang = thang + 1;
			}
			

			String lastmonth;
			for (int i = 0; i < 12; i++){
				int lastyear = nam - 1;
				
				if(thang < 10)
					lastmonth = lastyear + "-0" + thang;
				else
					lastmonth = lastyear + "-" + thang;

				query = "SELECT	PK_SEQ AS SPID, ISNULL(TONDAU.SOLUONG, 0) AS TONDAU, " + 
						"ISNULL(AVG3M.AVG3M_PRI, 0) AS AVG3M_PRI, " +
						"ISNULL(AVG6M.AVG6M_PRI, 0) AS AVG6M_PRI, " +
						"ISNULL(LASTYEAR.LASTYEAR_PRI, 0) AS LASTYEAR_PRI " +
						"FROM ERP_SANPHAM SP " +
						"LEFT JOIN " +
						"( " +
						"	SELECT SANPHAM_FK AS SPID, SOLUONG " + 
						"	FROM ERP_TONKHONGAY  " +
						"	WHERE KHO_FK = '" + this.KHO + "' AND NGAY = '" + ngaytd + "'" +
						")TONDAU ON TONDAU.SPID = SP.PK_SEQ " +
						"LEFT JOIN " +
						"( " +
						"	SELECT	SP.PK_SEQ AS SPID ,  SUM (CONVERT(FLOAT, SOLUONG) )/3 AS AVG3M_PRI " +  
						"	FROM NHAPHANG NH  " +
						"	INNER JOIN NHAPHANG_SP NHSP ON NH.PK_SEQ = NHSP.NHAPHANG_FK " +  
						"	INNER JOIN ERP_SANPHAM SP ON SP.MA = NHSP.SANPHAM_FK  " +	
						"	WHERE NH.TRANGTHAI <> 2 AND NH.NGAYCHUNGTU >= '"+ ngaydsd3M +"' AND NH.NGAYCHUNGTU<='" + ngaytd + "'" +
						"	AND NH.KHO_FK = '" + this.KHO + "' GROUP BY SP.PK_SEQ " +
						")AVG3M ON AVG3M.SPID = SP.PK_SEQ " +
						"LEFT JOIN " +
						"( " +
						"	SELECT	SP.PK_SEQ AS SPID,  SUM (CONVERT(FLOAT, SOLUONG) )/6 AS AVG6M_PRI " +  
						"	FROM NHAPHANG NH  " +
						"	INNER JOIN NHAPHANG_SP NHSP ON NH.PK_SEQ = NHSP.NHAPHANG_FK " +  
						"	INNER JOIN ERP_SANPHAM SP ON SP.MA = NHSP.SANPHAM_FK  " +
						"	WHERE NH.TRANGTHAI <> 2 AND NH.NGAYCHUNGTU >= '"+ ngaydsd6M +"' AND NH.NGAYCHUNGTU<='" + ngaytd + "'" +
						"	AND NH.KHO_FK = '" + this.KHO + "'" +
						"	GROUP BY SP.PK_SEQ " +
						")AVG6M ON AVG6M.SPID = SP.PK_SEQ " +
						"LEFT JOIN " +
						"( " +
						"	SELECT	SP.PK_SEQ AS SPID,  SUM (CONVERT(FLOAT, SOLUONG) )/6 AS LASTYEAR_PRI " +  
						"	FROM NHAPHANG NH  " +
						"	INNER JOIN NHAPHANG_SP NHSP ON NH.PK_SEQ = NHSP.NHAPHANG_FK " +  
						"	INNER JOIN ERP_SANPHAM SP ON SP.MA = NHSP.SANPHAM_FK  " +	
						"	WHERE NH.TRANGTHAI <> 2 AND SUBSTRING(NH.NGAYCHUNGTU, 1, 7) = '" + lastmonth + "'" +
						"	AND NH.KHO_FK = '" + this.KHO + "'" +
						"	GROUP BY SP.PK_SEQ " + 
						")LASTYEAR ON LASTYEAR.SPID = SP.PK_SEQ  " +
						"WHERE SP.TRANGTHAI = '1' AND SP.CONGTY_FK = " + this.CTYID ;

						System.out.println(query);
						rs = this.db.get(query);
						String tmp;
						while(rs.next()){
							tmp =	"INSERT INTO ERP_DUBAOSANPHAM (SANPHAM_FK, SONGAYBANHANG, SOVOI_FK, TANGTRUONG, TONDAU, DUKIENBAN, TONKHOANTOAN," +
									"SANXUAT, TONCUOI, DUBAO_FK, NAM, THANG, AVG3M, AVG6M, LASTYEAR) " +
									"VALUES('" + rs.getString("SPID") + "', '0', null , '0', " + rs.getString("TONDAU") + ", '0' ,  " + tonkhoantoan + ", " +
									" '0' ,  '" + rs.getString("TONDAU") + "' , " + this.ID + ", '" + nam + "', '" + thang + "', '" + rs.getString("AVG3M_PRI") + "', " +
									"'" + rs.getString("AVG6M_PRI") + "', '" + rs.getString("LASTYEAR_PRI") + "' )";

									System.out.println(tmp);
									if(!db.update(tmp))
									{
										this.MSG = "Không thể tạo dự báo kinh doanh lỗi : " + tmp;
										db.getConnection().rollback();
										return false;
									}

						}
						rs.close();
						if(thang == 12) {
							thang = 1;
							nam = nam + 1;
						}else{
							thang = thang + 1;
						}
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			this.MSG="Đã có lỗi xảy ra không thể thêm ( "+e.getMessage()+" )";
			return false;
		}
	}


	public boolean update(HttpServletRequest request)  throws ServletException, IOException
	{
		
		request.setCharacterEncoding("UTF-8");
		System.out.println("Vao update");
		String ngaytao = this.getDateTime();
		try 
		{
			db.getConnection().setAutoCommit(false);
			if(this.KHO.length() == 0) this.KHO = null;
			
			String query =  "UPDATE ERP_DUBAO set KHO_FK = '" + this.KHO + "', NGAYDUBAO= '" + this.NGAYDUBAO + "', DIENGIAI = N'" + this.DIENGIAI + "'," +
							"NGAYTONKHOANTOAN = '" + this.ngaytonkho + "', " +
							"NGUOISUA='" + this.USERID +"',NGAYSUA='"+ngaytao+"' where pk_seq="+this.ID;	
			System.out.println("Update 1: " + query);
			
			if(!db.update(query))
			{
				this.MSG = "Không thể cập nhật dự báo kinh doanh lỗi : " + query;
				db.getConnection().rollback();
				return false;
			}

			String songayban = "songayban_";
			String tangtruong = "tangtruong_";
			String sovoi = "sovoi_";			
			
			String tondau = "tondau_";			
			String dukienban = "dukienban_";
			String tonkhoantoan = "tonkhoantoan_";
			String sanxuat = "sanxuat_";
			String toncuoi = "toncuoi_";
			
			String[] ngaydubao = this.NGAYDUBAO.split("-"); //chua nam, thang, ngay
			int thang;
			int nam;

			if(ngaydubao[1].equals("12")){
				ngaydubao[1] = "01";
				ngaydubao[0] = "" + (Integer.valueOf(ngaydubao[0]) + 1) ;
			}else{
				ngaydubao[1] = "" + (Integer.valueOf(ngaydubao[1]) + 1) ;
			}

			for(int j = 0; j < this.spIds.length ; j++){
				thang 	= Integer.parseInt(ngaydubao[1]);
				nam  	= Integer.parseInt(ngaydubao[0]);
					
				for(int i = 0; i < Integer.valueOf(this.SOTHANGDUBAO ).intValue(); i++){

					System.out.println("thang: " + thang + "; nam: " + nam + "; sanpham: " +  this.spIds[j]);

					songayban 	= request.getParameter("songayban_" + i);
					tangtruong 	= request.getParameter("tangtruong_" + i);
					sovoi 		= request.getParameter("sovoi_" + i);
					if(sovoi.equals("0")) sovoi = null;
					
					tondau 			= request.getParameter("tondau_" + this.spIds[j]  + "_" +  i);
					dukienban		= request.getParameter("dukienban_" + this.spIds[j]  + "_" +  i);
					tonkhoantoan	= request.getParameter("tonkhoantoan_" + this.spIds[j]  + "_" +  i);
					sanxuat			= request.getParameter("sanxuat_" + this.spIds[j]  + "_" +  i);
					toncuoi			= request.getParameter("toncuoi_" + this.spIds[j]  + "_" +  i);
					
       				query =	"UPDATE ERP_DUBAOSANPHAM set SONGAYBANHANG= " + songayban + ", " +
       						"SOVOI_FK = " + sovoi + ", TANGTRUONG = '" + tangtruong + "', " +
       						"TONDAU = " + tondau + ", DUKIENBAN = " + dukienban + ", TONKHOANTOAN = " + tonkhoantoan + ", " +
       						"SANXUAT = " + sanxuat + ", TONCUOI = " + toncuoi + " " +
       						"WHERE DUBAO_FK = '" + this.ID +"' AND THANG = '" + thang + "' AND NAM = '" + nam + "' " +
       						"AND SANPHAM_FK = '" + this.spIds[j] + "' ";
       				
           			System.out.println("Update Du Bao: " + query);
           			if(!db.update(query))
           			{
           				this.MSG = "Không thể tạo dự báo kinh doanh lỗi : " + query;
           				this.db.getConnection().rollback();
                		return false;
                	}
           			
					if(thang == 12) {
						thang = 1;
						nam = nam + 1;
					}else{
						thang = thang + 1;
					}

				}
			
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		 
		}catch (Exception e) 
		{
			db.update("rollback");
			this.MSG="Đã có lỗi xảy ra không thể cập nhật ( "+e.getMessage()+" )";
			e.printStackTrace();
			return false;
		}
	}
	

	public void close() 
	
	{
		this.db.shutDown();
		try 
		{
			if(rsChungloai!=null){
				rsChungloai.close();
			}
			if(rsNhanhang!=null){
				rsNhanhang.close();
			}
			if(rsSanpham!=null){
				rsSanpham.close();
			}
			if(rsSovoi!=null){
				rsSovoi.close();
			}
			
			
			if(this.rsKho != null)
				this.rsKho.close();
			if(this.rsPhuongphap != null)
				this.rsPhuongphap.close();
		}
		catch (SQLException e) 
		{
			System.out.println(e.toString());
		}	
		
	}


	
	private ResultSet GetTondau(String khoId, String ngay)
	{
		String tondau = "0";
		String ngaytd = "";
		dbutils db = new dbutils();
		String[] tmp;
		
		String query = "SELECT DATEADD(d,-1, DATEADD(mm, DATEDIFF(mm, 0 ,'"+ ngay + "'), 0)) AS NGAY";
		ResultSet rs = this.db.get(query);
		
		try{
			
			rs.next();
			ngaytd = rs.getString("NGAY").substring(0, 10);
			rs.close();
			query = "SELECT SANPHAM_FK, SOLUONG FROM ERP_TONKHONGAY WHERE KHO_FK = '" + khoId + "' AND NGAY = '" + ngay + "'";
			return this.db.get(query);
			
		}catch(java.sql.SQLException e){
			return null;
		}
		
	}
	
	
	private ResultSet GetDSTB3M(String kho, String ngaydsc)
	{

		String query = "SELECT DATEADD(d,-1, DATEADD(mm, DATEDIFF(mm, 0 ,'"+ ngaydsc +"')-3, 0)) AS NGAY";
		ResultSet rs = this.db.get(query);
		String ngaydsd;
		try{
			
			rs.next();
			ngaydsd = rs.getString("NGAY").substring(0, 10);

			rs.close();
			query = "SELECT	SP.PK_SEQ,  SUM (CONVERT(FLOAT, SOLUONG) )/3 AS AVG3M_PRI " +  
					"FROM NHAPHANG NH  " +
					"INNER JOIN NHAPHANG_SP NHSP ON NH.PK_SEQ = NHSP.NHAPHANG_FK " +  
					"INNER JOIN ERP_SANPHAM SP ON SP.MA = NHSP.SANPHAM_FK  	" +
					"WHERE NH.TRANGTHAI <> 2 AND NH.NGAYCHUNGTU >= '"+ ngaydsd +"' AND NH.NGAYCHUNGTU<='"+ ngaydsc +"'" +
					"AND NH.KHO_FK = '" + kho + "' AND SP.CONGTY_FK = " + this.CTYID + " " + 
					"GROUP BY SP.PK_SEQ " ;

			return this.db.get(query);
			
		}catch(java.sql.SQLException e){
			return null;
		}
		
	}	

	
	}
