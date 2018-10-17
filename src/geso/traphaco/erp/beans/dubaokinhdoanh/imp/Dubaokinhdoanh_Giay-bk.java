package geso.traphaco.erp.beans.dubaokinhdoanh.imp;

import geso.dms.db.sql.dbutils;
import geso.traphaco.erp.beans.dubaokinhdoanh.IDubaokinhdoanh_Giay;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class Dubaokinhdoanh_Giay implements IDubaokinhdoanh_Giay
{
	String CTYID, USERID,ID,MSG,KHO,DIENGIAI,TRANGTHAI,NGAYDUBAO,SOTHANGDUBAO,PHUONGPHAP,LOAISOVOI,TENSANPHAM,NHANHANG,CHUNGLOAI, DVKDID, MOHINH;
	String HIEULUC;
	ResultSet rsKho,rsPhuongphap,rsSovoi,rsSanpham,rsNhanhang,rsChungloai, rsDvkd;
	String maspstr;
	String[][] data;
	String[] spIds;
	String[] khIds;
	String[] selectedSpIds;
	String[] ngaythang;
	String ngaytonkho;
	ResultSet khRS;
	int count;
	dbutils db;

	double thieu ;
	double lotsize ;
	double tonantoan ;
	double tonkho ;
	double tondau ;
	double plnorder;
	int leadtime;
	
	public Dubaokinhdoanh_Giay()
	{
		this.db=new dbutils();
		this.CTYID = "";
		this.USERID="";
		this.ID="";
		this.MOHINH = "";
		this.DVKDID = "";
		this.KHO="";
		this.NGAYDUBAO= this.getDateTime();
		this.LOAISOVOI="";
		this.SOTHANGDUBAO="3";
		this.TENSANPHAM="";
		this.PHUONGPHAP="";
		this.DIENGIAI="";
		this.TRANGTHAI="";
		this.NHANHANG="";
		this.CHUNGLOAI="";
		this.ngaytonkho = "0";
		this.MSG="";
		this.HIEULUC = "1";
		this.maspstr = "";
	
	}
	public Dubaokinhdoanh_Giay(String id)
	{
		this.db=new dbutils();
		this.CTYID = "";
		this.ID=id;
		this.MOHINH = "";
		this.DVKDID = "";
		this.USERID="";
		this.DIENGIAI="";		
		this.KHO="";
		this.NGAYDUBAO= this.getDateTime();
		this.SOTHANGDUBAO="3";
		this.PHUONGPHAP="";
		this.NHANHANG="";
		this.CHUNGLOAI="";
		this.MSG="";
		this.maspstr = "";
		this.HIEULUC = "1";
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
	
	public String getMohinh()
	{

		return this.MOHINH;
	}

	public void setMohinh(String MOHINH)
	{

		this.MOHINH = MOHINH;
	}

	public String getDvkdId()
	{

		return this.DVKDID;
	}

	public void setDvkdId(String dvkdId)
	{

		this.DVKDID = dvkdId;
	}

	public ResultSet getDvkdRs()
	{

		return this.rsDvkd;
	}

	public void setDvkdRs(ResultSet rsDvkd)
	{

		this.rsDvkd = rsDvkd;
	}
	
	
	public String getHieuluc() 
	{
		
		return this.HIEULUC;
	}

	
	public void setHieuluc(String HIEULUC) 
	{
		
		this.HIEULUC = HIEULUC;
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
	
	public String[] getKhIds() {

		return this.khIds;
	}
	
	public void setKhIds(String[] khIds) {
		this.khIds = khIds;
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
						
		this.rsDvkd = this.db.get("SELECT PK_SEQ AS DVKDID, DONVIKINHDOANH AS DVKD " +
								  "FROM DonViKinhDoanh WHERE CONGTY_FK = " + this.CTYID + " AND TRANGTHAI = '1' ");
		
		this.rsSovoi=this.db.getScrol("select PK_SEQ,TEN from ERP_LOAISOVOI WHERE TRANGTHAI = '1'" );

		this.rsNhanhang=this.db.get("select PK_SEQ,TEN from NHANHANG WHERE TRANGTHAI = '1' AND CONGTY_FK = " + this.CTYID  );
		
		this.rsChungloai=this.db.get("select PK_SEQ,TEN from CHUNGLOAI WHERE TRANGTHAI = '1' AND CONGTY_FK = " + this.CTYID );
			
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
		query = "SELECT COUNT(SPID) AS NUM FROM (" +
				"SELECT	DISTINCT SP.PK_SEQ AS SPID, KH.PK_SEQ " +
				"FROM ERP_DUBAO DUBAO " +
				"INNER JOIN ERP_DUBAOSANPHAM DUBAOSP on DUBAO.PK_SEQ = DUBAOSP.DUBAO_FK " +
				"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = DUBAOSP.SANPHAM_FK  " +
				"INNER JOIN DonViKinhDoanh DVKD ON DVKD.PK_SEQ = SP.DVKD_FK AND DVKD.PK_SEQ = DUBAO.DVKD_FK " +				
				"INNER JOIN ERP_KHACHHANG KH ON KH.PK_SEQ = DUBAOSP.KHACHHANG_FK " +
				"WHERE DUBAO.PK_SEQ = '" + this.ID + "' AND DUBAOSP.THANG = '" + th + "' " +
				"AND DUBAOSP.NAM = '" + ngaythang[0] + "' AND DUBAO.CONGTY_FK = " + this.CTYID + " " ;
		
		if(this.DVKDID.length() > 0){
			query = query + " AND DUBAO.DVKD_FK = '" + this.DVKDID + "' "; 
		}

		
		if(this.CHUNGLOAI.length() > 0){
			query = query + " AND SP.CHUNGLOAI_FK = '" + this.CHUNGLOAI + "' "; 
		}
		
		if(this.NHANHANG.length() > 0){
			query = query + " AND SP.NHANHANG_FK = '" + this.NHANHANG + "' ";
		}
		
		query = query + " )A ";
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
			this.count = count*6+2;
			
			rs.close();
			if(count > 0){
				String[][] data = new String[count*6 + 2][Integer.parseInt(this.SOTHANGDUBAO)];
			
				for(int i=0; i < Integer.parseInt(this.SOTHANGDUBAO); i++)
				{
			
					if(this.ID.length() > 0)
					{
						query =	"SELECT	DISTINCT SP.PK_SEQ AS SPID, SP.MA + ' - ' + SP.TEN + ' ' + SP.QUYCACH AS SPTEN, " +
								"DUBAOSP.NAM, DUBAOSP.THANG, DUBAO.PK_SEQ, SP.PK_SEQ AS SPID, SP.MA AS MASANPHAM, " +
								"SP.TEN AS TENSANPHAM, ISNULL(DUBAOSP.TONDAU, '0') AS TONDAU, ISNULL(DUBAOSP.TANGTRUONG, '0') AS TANGTRUONG, ISNULL(DUBAOSP.SOVOI_FK, '0') AS SOVOI, " +
								"ISNULL(DUBAOSP.TONKHOANTOAN, '0') AS TONKHOANTOAN, ISNULL(DUBAOSP.SANXUAT, '0') AS SANXUAT , ISNULL(DUBAOSP.TONCUOI, '0') AS TONCUOI, ISNULL(DUBAOSP.DUKIENBAN, '0') AS DUKIENBAN, " +
								"ISNULL(DUBAOSP.SONGAYBANHANG, '24')  AS SONGAYBANHANG, " +
								"DUBAOSP.AVG3M, DUBAOSP.AVG6M, DUBAOSP.LASTYEAR " + 
								"FROM ERP_DUBAO DUBAO " +
								"INNER JOIN ERP_DUBAOSANPHAMMTS DUBAOSP on DUBAO.PK_SEQ = DUBAOSP.DUBAO_FK " +
								"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = DUBAOSP.SANPHAM_FK " +
								"INNER JOIN DonViKinhDoanh DVKD ON DVKD.PK_SEQ = SP.DVKD_FK AND DVKD.PK_SEQ = DUBAO.DVKD_FK " +				
								"WHERE DUBAO.PK_SEQ = '" + this.ID + "' AND DUBAOSP.THANG = '" + th + "' AND DUBAOSP.NAM = '" + ngaythang[0] + "' " +
								"AND DUBAO.CONGTY_FK = " + this.CTYID + " ";
							
						if(this.DVKDID.length() > 0){
							query = query + " AND DUBAO.DVKD_FK = '" + this.DVKDID + "' "; 
						}
					
						if(this.CHUNGLOAI.length() > 0){
							query = query + " AND SP.CHUNGLOAI_FK = '" + this.CHUNGLOAI + "' "; 
						}
					
						if(this.NHANHANG.length() > 0){
							query = query + " AND SP.NHANHANG_FK = '" + this.NHANHANG + "' ";
						}
					
						query = query + " ORDER BY KHID ";
					
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
								data[count++][i] = rs.getString("TANGTRUONG");
								data[count++][i] = rs.getString("SOVOI");
							}
							data[count++][i] = rs.getString("SPID")+ ";" + rs.getString("SPTEN") ;
							data[count++][i] = rs.getString("DUKIENBAN") ;
							data[count++][i] = rs.getString("AVG3M") ;
							data[count++][i] = rs.getString("AVG6M") ;
							data[count++][i] = rs.getString("LASTYEAR") ;

						//	Tao danh sach cac san pham, chi dung 1 thang dau de tao danh sach
							if(i == 0){
								if (this.maspstr.length()==0){
									this.maspstr = "'" + rs.getString("KHID") + '_' + rs.getString("SPID") + "'";
								}else{
									this.maspstr = this.maspstr + ",'" + rs.getString("KHID") + '_' + rs.getString("SPID") + "'";
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
			}else{
				this.data = null;
				this.db.update("DELETE FROM ERP_DUBAO WHERE PK_SEQ = " + this.ID ) ;
				this.MSG = "Vui lòng khai báo sản phẩm thuộc đơn vị kinh doanh trước khi thực hiện dự báo kinh doanh";
			}
			
		}catch(java.sql.SQLException e){}
	}

	public void createRs_New() 
	{
		String query = "SELECT PK_SEQ AS KHID, TEN FROM ERP_KHACHHANG WHERE CONGTY_FK = 100001 AND MAKETOSTOCK = 0"; 
		this.khRS = this.db.get(query);

		this.rsKho=this.db.get("select PK_SEQ,TEN,DIACHI  from ERP_KHOTT WHERE TRANGTHAI = '1' and CONGTY_FK = " + this.CTYID + " and LOAI = 3");
		
		this.rsDvkd = this.db.get("SELECT PK_SEQ AS DVKDID, DONVIKINHDOANH AS DVKD FROM DonViKinhDoanh WHERE CONGTY_FK = " + this.CTYID + " AND TRANGTHAI = '1' ");
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
					
					if(rs.getString("DVKD_FK") == null){
						this.DVKDID = "";
					}else{
						this.DVKDID = rs.getString("DVKD_FK");	
					}
					
					this.DIENGIAI=rs.getString("DIENGIAI");
					this.NGAYDUBAO = rs.getString("NGAYDUBAO");
					this.HIEULUC = rs.getString("HIEULUC");
					this.MOHINH = rs.getString("MOHINH");
					
					if(rs.getString("NGAYTONKHOANTOAN") == null || rs.getString("NGAYTONKHOANTOAN").length() == 0)
						this.ngaytonkho = "0";
					else
						this.ngaytonkho = rs.getString("NGAYTONKHOANTOAN");
					
					if(rs.getString("KHO_FK") == null){
						this.KHO = "";
					}else{
						this.KHO = rs.getString("KHO_FK");
					}
				}
				rs.close();
			} 
			catch (SQLException e) 
			{
				System.out.println("Loi roi" + e.toString());
			}
		}
		//Lay chi tiet du bao
		if(this.MOHINH.equals("1")){
			this.createRs();
		}else{
			this.createRsMTS();
		}
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

			if(this.HIEULUC.equals("1")){
				this.db.update("UPDATE ERP_DUBAO set HIEULUC = '0' WHERE CONGTY_FK = '" + this.CTYID + "' AND DVKD_FK = " + this.DVKDID );
			}
			
			String query;
			//Doi lai, du bao khong phan biet kho
			if(this.DVKDID.length() > 0){
				query = 	"INSERT ERP_DUBAO (CONGTY_FK, KHO_FK, NGAYDUBAO, DIENGIAI, TRANGTHAI, NGUOITAO, NGAYTAO, NGUOISUA, NGAYSUA, NGAYTONKHOANTOAN, HIEULUC, DVKD_FK, MOHINH) " +
							"VALUES(" + this.CTYID + ", null,'"+this.NGAYDUBAO+"',N'"+this.DIENGIAI+"','"+"0"+"','"+USERID+"', " +
							"'"+ngaytao+"','"+USERID+"','"+ngaytao+"', '0', '" + this.HIEULUC + "', " + this.DVKDID + ", " + this.MOHINH + " )";
			}else{
				query = 	"INSERT ERP_DUBAO (CONGTY_FK, KHO_FK, NGAYDUBAO, DIENGIAI, TRANGTHAI, NGUOITAO, NGAYTAO, NGUOISUA, NGAYSUA, NGAYTONKHOANTOAN, HIEULUC, MOHINH) " +
							"VALUES(" + this.CTYID + ", null,'"+this.NGAYDUBAO+"',N'"+this.DIENGIAI+"','"+"0"+"','"+USERID+"', " +
							"'"+ngaytao+"','"+USERID+"','"+ngaytao+"', '0', '" + this.HIEULUC + "', " + this.MOHINH + " )";
				
			}
			
			System.out.println(query);
			
			if(!db.update(query))
			{
				this.MSG = "Không thể tạo dự báo kinh doanh lỗi : " + query;
				db.getConnection().rollback();
				return false;
			}else{
				System.out.println(query);
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
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
			if(this.MOHINH.equals("0")){
				this.CreateDubaoSPMTS(request);
			}else{
				this.CreateDubaoSPMTO(request);
			}
					
		}
		catch(java.sql.SQLException e)
		{
			return false;
		}
		
		return true;
	}	
	
	private boolean CreateDubaoSPMTS(HttpServletRequest request)  throws ServletException, IOException{
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
							tmp =	"INSERT INTO ERP_DUBAOSANPHAMMTS (SANPHAM_FK, SONGAYBANHANG, SOVOI_FK, TANGTRUONG, TONDAU, DUKIENBAN, TONKHOANTOAN," +
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
	
	private boolean CreateDubaoSPMTO(HttpServletRequest request)  throws ServletException, IOException
	{
		String ngaytao = this.getDateTime();
		System.out.println("Vao tao moi du bao.");
		try
		{
			this.db.getConnection().setAutoCommit(false);
			
			String query = "SELECT DATEADD(d,-1, DATEADD(mm, DATEDIFF(mm, 0 ,'"+ ngaytao + "'), 0)) AS NGAY";
			ResultSet rs = this.db.get(query);		
			rs.next();
		
			String  ngaytd = rs.getString("NGAY").substring(0, 10);
		
			System.out.println("Ngay TD: " + ngaytd);
			
			rs.close();
		
			query = "SELECT DATEADD(d,-1, DATEADD(mm, DATEDIFF(mm, 0 ,'"+ ngaytd +"')-3, 0)) AS NGAY";
			rs = this.db.get(query);
			rs.next();
		
			String  ngaydsd3M = rs.getString("NGAY").substring(0, 10);
		
			System.out.println("Ngay DS TB 3 Thang: " + ngaydsd3M);
			
			rs.close();
		
			query = "SELECT DATEADD(d,-1, DATEADD(mm, DATEDIFF(mm, 0 ,'"+ ngaytd +"')- 6, 0)) AS NGAY";
			rs = this.db.get(query);
			rs.next();
		
			String  ngaydsd6M = rs.getString("NGAY").substring(0, 10);
		
			System.out.println("Ngay DS TB 6 Thang: " + ngaydsd6M);
			
			rs.close();

//			String tonkhoantoan = "0";
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

				//New TOYO ton dau chua tinh, lay bang 0, du bao khong phan biet kho
				query = "SELECT	KHSP.KHACHHANG_FK AS KHID, SP.PK_SEQ AS SPID, 0 AS TONDAU,   " +
						"	ISNULL(AVG3M.AVG3M_PRI, 0) AS AVG3M_PRI,  " +
						"	ISNULL(AVG6M.AVG6M_PRI, 0) AS AVG6M_PRI,  " +
						"	ISNULL(LASTYEAR.LASTYEAR_PRI, 0) AS LASTYEAR_PRI   " +
						"FROM ERP_SANPHAM SP   " +
						"INNER JOIN ERP_KHACHHANG_SANPHAM KHSP ON SP.PK_SEQ = KHSP.SANPHAM_FK AND KHSP.CHON = 1   " +
						"INNER JOIN ERP_KHACHHANG KH ON KH.PK_SEQ = KHSP.KHACHHANG_FK   " +
						"LEFT JOIN  " +
						"(  " +
							"SELECT	DDH.KHACHHANG_FK, SP.PK_SEQ AS SPID,  SUM (CONVERT(FLOAT, SOLUONG) )/3 AS AVG3M_PRI    " +
							"FROM ERP_XUATKHO XK " +
								"INNER JOIN ERP_XUATKHO_SANPHAM XKSP ON XK.PK_SEQ = XKSP.XUATKHO_FK    " +
								"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = XKSP.SANPHAM_FK  " +
								"INNER JOIN ERP_DONDATHANG DDH on XK.DONDATHANG_FK = DDH.PK_SEQ 	 " +
							"WHERE XK.TRANGTHAI = 1 AND XK.NGAYCHOT >= '" + ngaydsd3M + "' AND XK.NGAYCHOT <= '" + ngaytd + "'   " +
							"GROUP BY DDH.KHACHHANG_FK, SP.PK_SEQ   " +
						") " +
						"AVG3M ON AVG3M.SPID = SP.PK_SEQ AND AVG3M.KHACHHANG_FK = KH.PK_SEQ  " +
						"LEFT JOIN  " +
						"(  " +
							"SELECT	DDH.KHACHHANG_FK, SP.PK_SEQ AS SPID,  SUM (CONVERT(FLOAT, SOLUONG) )/6 AS AVG6M_PRI    " +
							"FROM ERP_XUATKHO XK  " +
								"INNER JOIN ERP_XUATKHO_SANPHAM XKSP ON XK.PK_SEQ = XKSP.XUATKHO_FK    " +
								"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = XKSP.SANPHAM_FK   " +
								"INNER JOIN ERP_DONDATHANG DDH on XK.DONDATHANG_FK = DDH.PK_SEQ 	 " +
							"WHERE XK.TRANGTHAI = 1 AND XK.NGAYCHOT >= '" + ngaydsd6M + "' AND XK.NGAYCHOT <= '" + ngaytd + "'   " +
							"GROUP BY DDH.KHACHHANG_FK, SP.PK_SEQ   " +
						")  " +
						"AVG6M ON AVG6M.SPID = SP.PK_SEQ AND AVG6M.KHACHHANG_FK = KH.PK_SEQ  " +
						"LEFT JOIN   " +
						"(  " +
							"SELECT	DDH.KHACHHANG_FK, SP.PK_SEQ AS SPID,  SUM (CONVERT(FLOAT, SOLUONG) )/6 AS LASTYEAR_PRI    " +
							"FROM ERP_XUATKHO XK  " +
								"INNER JOIN ERP_XUATKHO_SANPHAM XKSP ON XK.PK_SEQ = XKSP.XUATKHO_FK    " +
								"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = XKSP.SANPHAM_FK  " +
								"INNER JOIN ERP_DONDATHANG DDH on XK.DONDATHANG_FK = DDH.PK_SEQ 	  " +
							"WHERE XK.TRANGTHAI = 1 AND SUBSTRING(XK.NGAYCHOT, 1, 7)  = '" + lastmonth + "'  " +
						"GROUP BY DDH.KHACHHANG_FK, SP.PK_SEQ   " +
						") " +
						"LASTYEAR ON LASTYEAR.SPID = SP.PK_SEQ  AND LASTYEAR.KHACHHANG_FK = KH.PK_SEQ  " +
						"WHERE SP.TRANGTHAI = '1' AND SP.CONGTY_FK = '" + this.CTYID + "'  AND SP.DVKD_FK =  '" + this.DVKDID + "'";

						System.out.println("___Du bao San Pham: " + query);
						
						rs = this.db.get(query);
						String tmp;
						while(rs.next()){
							tmp =	"INSERT INTO ERP_DUBAOSANPHAM (KHACHHANG_FK, SANPHAM_FK, SONGAYBANHANG, SOVOI_FK, TANGTRUONG, TONDAU, DUKIENBAN, TONKHOANTOAN," +
									"SANXUAT, TONCUOI, DUBAO_FK, NAM, THANG, AVG3M, AVG6M, LASTYEAR) " +
									"VALUES('" + rs.getString("KHID") + "','" + rs.getString("SPID") + "', '0', null , '0', 0, '0' ,  0, " +
									" '0' , '0' , " + this.ID + ", '" + nam + "', '" + thang + "', '" + rs.getString("AVG3M_PRI") + "', " +
									"'" + rs.getString("AVG6M_PRI") + "', '" + rs.getString("LASTYEAR_PRI") + "' )";

									System.out.println(tmp);
									if(!this.db.update(tmp))
									{
										System.out.println("ERROR:" + tmp);
										this.MSG = "Không thể tạo dự báo kinh doanh lỗi : " + tmp;
										//db.getConnection().rollback();
//										return false;
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

	public void createRsMTS() 
	{
		this.rsKho=this.db.get("select PK_SEQ,TEN,DIACHI  from ERP_KHOTT WHERE TRANGTHAI = '1' AND LOAI = 3 AND CONGTY_FK = " + this.CTYID );
				
		this.rsSovoi=this.db.getScrol("select PK_SEQ,TEN from ERP_LOAISOVOI WHERE TRANGTHAI = '1'" );

		this.rsNhanhang=this.db.get("select PK_SEQ,TEN from NHANHANG WHERE TRANGTHAI = '1' AND CONGTY_FK = " + this.CTYID  );
		
		this.rsChungloai=this.db.get("select PK_SEQ,TEN from CHUNGLOAI WHERE TRANGTHAI = '1' AND CONGTY_FK = " + this.CTYID );
			
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
				"INNER JOIN ERP_DUBAOSANPHAMMTS DUBAOSP on DUBAO.PK_SEQ = DUBAOSP.DUBAO_FK " +
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
							"INNER JOIN ERP_DUBAOSANPHAMMTS DUBAOSP on DUBAO.PK_SEQ = DUBAOSP.DUBAO_FK " +
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

	public boolean update(HttpServletRequest request)  throws ServletException, IOException
	{
		
		request.setCharacterEncoding("UTF-8");
		System.out.println("Vao update");
		String ngaytao = this.getDateTime();
		geso.dms.center.util.Utility util = new geso.dms.center.util.Utility();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			if(this.HIEULUC.equals("1")){
				this.db.update("UPDATE ERP_DUBAO set HIEULUC = '0' WHERE CONGTY_FK = '" + this.CTYID + "' AND DVKD_FK = " + this.DVKDID );
			}
			
			String query =  "UPDATE ERP_DUBAO set NGAYDUBAO='"+this.NGAYDUBAO+"',DIENGIAI=N'"+this.DIENGIAI+"'," +
							"NGUOISUA='" + this.USERID +"',NGAYSUA='"+ngaytao+"', HIEULUC = '" + this.HIEULUC + "' where pk_seq="+this.ID;	
			//System.out.println("Update 1: " + query);
			
			if(!db.update(query))
			{
				this.MSG = "Không thể cập nhật dự báo kinh doanh lỗi : " + query;
				db.getConnection().rollback();
				return false;
			}

			String tangtruong = "tangtruong_";
			String sovoi = "sovoi_";						
			String dukienban = "dukienban_";
			
			String[] ngaydubao = this.NGAYDUBAO.split("-"); //chua nam, thang, ngay
			int thang;
			int nam;

			if(ngaydubao[1].equals("12")){
				ngaydubao[1] = "01"; //Thang du bao
				ngaydubao[0] = "" + (Integer.valueOf(ngaydubao[0]) + 1) ; //Nam du bao
			}else{
				ngaydubao[1] = "" + (Integer.valueOf(ngaydubao[1]) + 1) ; // Thang bat dau du bao
			}
			
			if(this.spIds != null){
				for(int j = 0; j < this.spIds.length ; j++){
					thang 	= Integer.parseInt(ngaydubao[1]); // Bat dau tu thang bat dau du bao
					nam  	= Integer.parseInt(ngaydubao[0]);
					
					for(int i = 0; i < Integer.valueOf(this.SOTHANGDUBAO ).intValue(); i++){

						System.out.println("thang: " + thang + "; nam: " + nam + "; sanpham: " +  this.spIds[j]);

						tangtruong 	= util.antiSQLInspection(request.getParameter("tangtruong_" + i));
						sovoi 		= util.antiSQLInspection(request.getParameter("sovoi_" + i));
						if(sovoi.equals("0")) sovoi = null;
					
						dukienban		= util.antiSQLInspection(request.getParameter("dukienban_" + this.khIds[j]+ "_" + this.spIds[j]  + "_" +  i));
									
						query =	"UPDATE ERP_DUBAOSANPHAM set SONGAYBANHANG= '0', " +
       							"SOVOI_FK = " + sovoi + ", TANGTRUONG = '" + tangtruong.replaceAll(",", "") + "', " +
       							"TONDAU = 0, DUKIENBAN = " + dukienban.replaceAll(",", "") + ", TONKHOANTOAN = 0 , " +
       							"SANXUAT = 0, TONCUOI = 0 " +
       							"WHERE DUBAO_FK = '" + this.ID +"' AND THANG = '" + thang + "' AND NAM = '" + nam + "' " +
       							"AND SANPHAM_FK = '" + this.spIds[j] + "' AND KHACHHANG_FK = '" + this.khIds[j] + "'";
       				
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
			}
			
			if(this.HIEULUC.equals("1"))
			{
				this.CapnhatYeucau();
				this.TaoLenhSanXuatDK_MTO();
				this.TaoYeuCauNguyenLieu();
				this.createLenhMuaNguyeLieuDK();
			}
			else  //Du bao ngung, huy cac de nghi san xuat, de nghi mua nguyen lieu
			{
				this.NgungHieuLuc();
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
	
	private void NgungHieuLuc() 
	{
		String[] ngaydubao = this.NGAYDUBAO.split("-"); //chua nam, thang, ngay
		int thang;
		int nam;

		if(ngaydubao[1].equals("12")){
			ngaydubao[1] = "01";									  // Thang du bao
			ngaydubao[0] = "" + (Integer.valueOf(ngaydubao[0]) + 1) ; // Nam du bao
		}else{
			ngaydubao[1] = "" + (Integer.valueOf(ngaydubao[1]) + 1) ; // Thang du bao
		}

		nam  	= Integer.parseInt(ngaydubao[0]);

		for(int j = 0; j < Integer.valueOf(this.SOTHANGDUBAO ).intValue(); j++)
		{
			thang = Integer.parseInt(ngaydubao[1]) + j;	
			
			String query = "delete ERP_LENHSANXUATDUKIEN " +
						   "WHERE SANPHAM_FK in ( select sanpham_fk from ERP_YEUCAUCUNGUNG where dubao_fk = '" + this.ID + "'  ) AND THANG = " + thang + " AND NAM = " + nam;
			
			if(!db.update(query))
			{
				System.out.println("222.Khong the cap nhat ERP_LENHSANXUATDUKIEN: " + query);
			}
			
			query = "delete ERP_MUANGUYENLIEUDUKIEN " + 
					"WHERE SANPHAM_FK in ( select sanpham_fk from ERP_YEUCAUCUNGUNG where dubao_fk = '" + this.ID + "'  ) AND THANG = " + thang + " AND NAM = " + nam;
			
			if(!db.update(query))
			{
				System.out.println("333.Khong the cap nhat ERP_LENHSANXUATDUKIEN: " + query);
			}
			
		}
		
		
		String query = "delete ERP_YEUCAUCUNGUNG where dubao_fk = '" + this.ID + "'";
		if(!db.update(query))
		{
			System.out.println("444.Loi: " + query);
		}
		
	}
	
	private void CapnhatYeucau()
	{
		/*String query = 	"SELECT DBSP.SANPHAM_FK, DBSP.NAM, DBSP.THANG, SUM(DBSP.DUKIENBAN) AS YEUCAU " +
						"FROM ERP_DUBAOSANPHAM DBSP " +
						"INNER JOIN ERP_DUBAO DUBAO ON DUBAO.PK_SEQ = DBSP.DUBAO_FK " +
						"WHERE DUBAO.PK_SEQ = " + this.ID +  " AND DUBAO.KHO_FK = 100000 AND DUBAO.CONGTY_FK = " + this.CTYID + " " +
						"GROUP BY DBSP.SANPHAM_FK, DBSP.NAM, DBSP.THANG " ;*/
		
		String query = 	"SELECT DBSP.SANPHAM_FK, DBSP.NAM, DBSP.THANG, SUM(DBSP.DUKIENBAN) AS YEUCAU " +
						"FROM ERP_DUBAOSANPHAM DBSP " +
						"INNER JOIN ERP_DUBAO DUBAO ON DUBAO.PK_SEQ = DBSP.DUBAO_FK " +
						"WHERE DUBAO.PK_SEQ = " + this.ID +  " AND DUBAO.CONGTY_FK = " + this.CTYID + " " +
						"GROUP BY DBSP.SANPHAM_FK, DBSP.NAM, DBSP.THANG " ;
		
		//System.out.println("___Cap nhat yeu cau: " + query);
		ResultSet dbkd = this.db.get(query); // rs1 chua tong du bao kinh doanh cua tung san pham theo tung thang, nam, lay tu du bao
		ResultSet yc;
		try{
			while(dbkd.next()){
				// Kiem tra xem san pham da co trong bang ERP_YEUCAUCUNGUNG chua (theo thang va nam)?
				/*query = 	"SELECT COUNT(SANPHAM_FK) AS NUM " +
							"FROM ERP_YEUCAUCUNGUNG " +
							"WHERE KHOTT_FK = 100000 AND THANG = '" + dbkd.getString("THANG") + "' " +
							"AND NAM = '" + dbkd.getString("NAM") + "' AND SANPHAM_FK = '" + dbkd.getString("SANPHAM_FK") + "'";*/
				
				query = 	"SELECT COUNT(SANPHAM_FK) AS NUM " +
							"FROM ERP_YEUCAUCUNGUNG " +
							"WHERE THANG = '" + dbkd.getString("THANG").trim() + "' " +
							"AND NAM = '" + dbkd.getString("NAM").trim() + "' AND SANPHAM_FK = '" + dbkd.getString("SANPHAM_FK") + "'";
				
				//System.out.println("Kiem tra xem da ton tai yeu cau: " + query);
				
				yc = this.db.get(query); // yc chua ket qua kiem tra xem san pham da co trong ERP_YEUCAUCUNGUNG chua?
				yc.next();

				// Neu chua co thi them vao bang ERP_YEUCAUCUNGUNG
				if(yc.getString("NUM").equals("0")){
					if(!dbkd.getString("YEUCAU").equals("0")){
						/*query = "INSERT INTO ERP_YEUCAUCUNGUNG(KHOTT_FK, SANPHAM_FK, THANG, NAM, YEUCAU, TUAN1, TUAN2, TUAN3, TUAN4, DUBAO_FK) VALUES (" +
								"'100000', '" + dbkd.getString("SANPHAM_FK") + "', '" + dbkd.getString("THANG") + "', '" + dbkd.getString("NAM") + "', " +
								"" +  dbkd.getString("YEUCAU") + ", " + Double.parseDouble(dbkd.getString("YEUCAU"))/4 + ", " + Double.parseDouble(dbkd.getString("YEUCAU"))/4 + ", " +
								"" + Double.parseDouble(dbkd.getString("YEUCAU"))/4 + ", " + Double.parseDouble(dbkd.getString("YEUCAU"))/4 + ", " + this.ID + " )";*/
						
						query = "INSERT INTO ERP_YEUCAUCUNGUNG(SANPHAM_FK, THANG, NAM, YEUCAU, TUAN1, TUAN2, TUAN3, TUAN4, DUBAO_FK) VALUES (" +
						"'" + dbkd.getString("SANPHAM_FK") + "', '" + dbkd.getString("THANG") + "', '" + dbkd.getString("NAM") + "', " +
						"" +  dbkd.getString("YEUCAU") + ", " + Double.parseDouble(dbkd.getString("YEUCAU"))/4 + ", " + Double.parseDouble(dbkd.getString("YEUCAU"))/4 + ", " +
						"" + Double.parseDouble(dbkd.getString("YEUCAU"))/4 + ", " + Double.parseDouble(dbkd.getString("YEUCAU"))/4 + ", " + this.ID + " )";
					
					
						if(!this.db.update(query))
							System.out.println(query);
					}
				}else{
					// Neu du bao kinh doanh cua san pham trong thang, nam khac 0
					if(!dbkd.getString("YEUCAU").equals("0")){
						/*query = "UPDATE ERP_YEUCAUCUNGUNG SET YEUCAU = "+  dbkd.getString("YEUCAU") + ", TUAN1 = " + Double.parseDouble(dbkd.getString("YEUCAU"))/4 + ", " +
								"TUAN2 = " + Double.parseDouble(dbkd.getString("YEUCAU"))/4 + ", TUAN3 = " + Double.parseDouble(dbkd.getString("YEUCAU"))/4 + ", " +
								"TUAN4 = " + Double.parseDouble(dbkd.getString("YEUCAU"))/4 + ", DUBAO_FK = " + this.ID + " " +
								"WHERE KHOTT_FK = 100000 AND THANG = '" + dbkd.getString("THANG") + "' " +
								"AND NAM = '" + dbkd.getString("NAM") + "' AND SANPHAM_FK = '" + dbkd.getString("SANPHAM_FK") + "'";*/
						
						query = "UPDATE ERP_YEUCAUCUNGUNG SET YEUCAU = "+  dbkd.getString("YEUCAU") + ", TUAN1 = " + Double.parseDouble(dbkd.getString("YEUCAU"))/4 + ", " +
								"TUAN2 = " + Double.parseDouble(dbkd.getString("YEUCAU"))/4 + ", TUAN3 = " + Double.parseDouble(dbkd.getString("YEUCAU"))/4 + ", " +
								"TUAN4 = " + Double.parseDouble(dbkd.getString("YEUCAU"))/4 + ", DUBAO_FK = " + this.ID + " " +
								"WHERE THANG = '" + dbkd.getString("THANG") + "' " +
								"AND NAM = '" + dbkd.getString("NAM") + "' AND SANPHAM_FK = '" + dbkd.getString("SANPHAM_FK") + "'";

						if(!this.db.update(query))
							System.out.println(query);
					}else{
						/*query = "DELETE FROM ERP_YEUCAUCUNGUNG WHERE KHOTT_FK = 100000 AND THANG = '" + dbkd.getString("THANG") + "' " +
								"AND NAM = '" + dbkd.getString("NAM") + "' AND SANPHAM_FK = '" + dbkd.getString("SANPHAM_FK") + "'";*/
						
						query = "DELETE FROM ERP_YEUCAUCUNGUNG WHERE THANG = '" + dbkd.getString("THANG") + "' " +
								"AND NAM = '" + dbkd.getString("NAM") + "' AND SANPHAM_FK = '" + dbkd.getString("SANPHAM_FK") + "'";

						if(!this.db.update(query))
							System.out.println(query);
						
					}
				}
				
				yc.close();
				
			}
			dbkd.close();
		}catch(java.sql.SQLException e){}
	}
	
	private boolean TaoLenhSanXuatDK_MTO() 
	{
		String query = "";
		ResultSet rs;
		try{
			String[] ngaydubao = this.NGAYDUBAO.split("-"); //chua nam, thang, ngay
			int thang;
			int nam;

			if(ngaydubao[1].equals("12")){
				ngaydubao[1] = "01";									  // Thang du bao
				ngaydubao[0] = "" + (Integer.valueOf(ngaydubao[0]) + 1) ; // Nam du bao
			}else{
				ngaydubao[1] = "" + (Integer.valueOf(ngaydubao[1]) + 1) ; // Thang du bao
			}

			nam  	= Integer.parseInt(ngaydubao[0]);
				
			for(int j = 0; j < Integer.valueOf(this.SOTHANGDUBAO ).intValue(); j++)
			{
			
				thang = Integer.parseInt(ngaydubao[1]) + j;				
					
				// Yeu cau chinh la du bao lay tu phien ban du bao kinh doanh co hieu luc, loai bo di nhung san pham co du bao kinh doanh bang 0
				query = "SELECT a.SANPHAM_FK, a.YEUCAU FROM ERP_YEUCAUCUNGUNG a inner join ERP_DuBao b on a.dubao_fk = b.pk_seq " +
						"WHERE a.THANG = " + thang + " AND a.NAM = " + nam + " and b.hieuluc = '1' ";
				System.out.println("1.Khoi Tao De Nghi: " + query);
				rs = this.db.get(query);
					
				while(rs.next())
				{
										
					String yeucau = rs.getString("YEUCAU");
					String spId = rs.getString("SANPHAM_FK");
					//rs.close();
						
					// Truoc khi tao lenh san xuat du kien, kiem tra xem lenh san xuat du kien da ton tai chua?
					query = "SELECT COUNT(PK_SEQ) AS NUM FROM ERP_LENHSANXUATDUKIEN WHERE SANPHAM_FK = '" + spId + "' AND THANG = " + thang + " AND NAM = " + nam + " ";
				
					//System.out.println("2.Khoi Tao De Nghi - check LSX du kien: " + query);
			
					ResultSet rsCheck = this.db.get(query);
					rsCheck.next();
				
					if(rsCheck.getString("NUM").equals("0"))  // Chua co lenh san xuat
					{ 
						if(!yeucau.equals("0"))
						{
							query =    	"INSERT INTO ERP_LENHSANXUATDUKIEN " +
										"([CONGTY_FK], [NHAMAY_FK], [SANPHAM_FK],[SOLUONG], [SANXUAT], [THANG],[NAM]," +
										"[NGAYTAO],[NGUOITAO],[NGAYSUA],[NGUOISUA],[TRANGTHAI]) "	+
										"SELECT " + this.CTYID + ", NHAMAY_FK, '" + spId + "', '" + yeucau + "', '0', " +
										"'" + thang + "', '" + nam + "', '" + this.getDateTime() + "', '" + this.USERID + "', " + 
										"'" + this.getDateTime() + "', '" + this.USERID + "', '1' " +
										"FROM ERP_SANPHAM_SANXUAT WHERE SANPHAM_FK = '" + spId + "' ";
								
							//System.out.println("3.Khoi Tao De Nghi - LSX du kien: " + query);
							
							if(!this.db.update(query))
								System.out.println("Co loi: " + query);
						}
					}
					else
					{
						if(!yeucau.equals("0"))
						{ 

							query =		"UPDATE ERP_LENHSANXUATDUKIEN SET SOLUONG =  '" + yeucau + "' " + 
										"WHERE SANPHAM_FK = '" + spId + "' AND THANG = " + thang + " AND NAM = " + nam + " ";
							
							//System.out.println("4.Khoi Tao De Nghi - Update LSX du kien:   " + query);
							
							if(!this.db.update(query))
								System.out.println("Co loi: " + query);
						}
					}
					rsCheck.close();
				}
				rs.close();
			}
		}
		catch(Exception e)
		{
			System.out.println("5.Khoi Tao De Nghi - Exception: " + e.toString());
		}
		return true; 
	}

	private void TaoYeuCauNguyenLieu()
	{
		String query;
		ResultSet SPrs;
		ResultSet NLrs;
		
		System.out.println("Bat dau thuc hien YCNL.... ");
		
		String[] ngaydubao = this.NGAYDUBAO.split("-"); //chua nam, thang, ngay
		int thang;
		int nam;

		if(ngaydubao[1].equals("12")){
			ngaydubao[1] = "01";									  // Thang du bao
			ngaydubao[0] = "" + (Integer.valueOf(ngaydubao[0]) + 1) ; // Nam du bao
		}else{
			ngaydubao[1] = "" + (Integer.valueOf(ngaydubao[1]) + 1) ; // Thang du bao
		}

		nam  	= Integer.parseInt(ngaydubao[0]);

		for(int j = 0; j < Integer.valueOf(this.SOTHANGDUBAO ).intValue(); j++)
		{
			thang = Integer.parseInt(ngaydubao[1]) + j;				
				
			query = "SELECT PK_SEQ AS ID, SANPHAM_FK, SOLUONG FROM ERP_LENHSANXUATDUKIEN WHERE THANG = " + thang + " AND NAM = " + nam + " ";
			System.out.println(query);
				
			SPrs = this.db.get(query);
				
			try{
				if(SPrs != null){
					while(SPrs.next()){
						String id = SPrs.getString("ID");
						String spId = SPrs.getString("SANPHAM_FK");
						String soluong = SPrs.getString("SOLUONG");
						
						NLrs = getDanhmucvattu(spId, soluong);    // Lay ra yeu cau nguyen lieu cho 1 lenh san xuat du kien
				
						if(NLrs != null){
							while(NLrs.next()){
								
								// Vì lệnh sản xuất dự kiến = yêu cầu của 1 tháng, nên có thể dùng NLrs.getString("SOLUONG") để tính tồn an toàn; nhưng sẽ o đúng cho MTS
								query = "INSERT INTO ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU (LENHSANXUATDUKIEN_FK, NGUYENLIEU_FK, YEUCAU, TONKHOANTOAN) " +
										"VALUES( " + id + ", " + NLrs.getString("VATTU_FK") + ", " + NLrs.getString("SOLUONG") + ", " + 1.0*Float.parseFloat(NLrs.getString("SOLUONG"))*Float.parseFloat(NLrs.getString("TONKHOANTOAN"))/26 + ")" ;
								
								System.out.println("1. Insert ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU: " + query);
								if(!this.db.update(query)){
									query = "UPDATE ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU SET YEUCAU = " + NLrs.getString("SOLUONG") + ", " +
											"TONKHOANTOAN =  " + 1.0*Float.parseFloat(NLrs.getString("SOLUONG"))*Float.parseFloat(NLrs.getString("TONKHOANTOAN"))/26 + " " +
											"WHERE LENHSANXUATDUKIEN_FK = " + id + " AND NGUYENLIEU_FK = " + NLrs.getString("VATTU_FK") + " " ;
										
									System.out.println("2.Update ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU: " + query);
									this.db.update(query);
								}
										
							}	
							NLrs.close();
						}
					}
					SPrs.close();
				}
					
			}
			catch(Exception e)
			{
				System.out.println("Loi TaoYeuCauNguyenLieu: " + e.toString());
			}
		}
		
	}
	
	private ResultSet getDanhmucvattu(String spId, String soluong){
		
		 /*String query =	"SELECT DM.PK_SEQ AS DMID, CASE WHEN NL.CHOPHEPTHAYTHE = 1 THEN NLTHAYTHE ELSE VT.VATTU_FK END AS VATTU_FK, " + 
			 			"CASE WHEN NL.CHOPHEPTHAYTHE = 1 " +
			 			"THEN NL.TILE*" + soluong + "*1.0*VT.SOLUONG/DM.SOLUONGCHUAN ELSE " + soluong + "*1.0*VT.SOLUONG/DM.SOLUONGCHUAN END AS SOLUONG,  " +
			 			"ISNULL(NL.TONKHOANTOAN, 0) AS TONKHOANTOAN " +
			 			"FROM ERP_DANHMUCVATTU DM " +
			 			"INNER JOIN ERP_DANHMUCVATTU_VATTU VT ON VT.DANHMUCVT_FK = DM.PK_SEQ " + 
			 			"LEFT  JOIN ERP_NGUYENLIEU NL ON NL.SANPHAM_FK = VT.VATTU_FK " +
			 			"WHERE DM.SANPHAM_FK = " + spId + " AND DM.SUDUNG = '1' " +
			 			"AND DM.HIEULUCTU <= '" + this.getDateTime() + "' AND DM.HIEULUCDEN >= '" + this.getDateTime() + "' ";*/
		
		 String query = " select DanhMuc.*, isnull ( ( select TONKHOANTOAN from ERP_NGUYENLIEU where sanpham_fk = DanhMuc.vattu_fk ), 0 ) as TONKHOANTOAN   " +
		 				"from   " +
		 				"(  " +
		 					"select c.pk_seq as DMID,   " +
			 					"case d.vattu_fk when - 1 then " +
			 					"	( case ( select distinct loaisanpham_fk from ERP_SanPham where ma = d.mavattu )   " +
			 					"	when 100013 then ( select pk_seq from ERP_SanPham where rong = ( select max(NO_OF_UP) from erp_no_of_slitting where FG_WIDTH = a.rong ) and ma = d.mavattu )  " +
			 					" 	else ( select pk_seq from ERP_SanPham where rong = ( select max(NO_OF_UP) - 2 from erp_no_of_slitting where FG_WIDTH = a.rong ) and ma = d.mavattu ) end )  " +
			 					"else d.VATTU_FK	end  as vattu_fk,   " +
			 					"( '" + soluong + "' * isnull(a.SOLUONGBANTP, 1) / isnull(a.SOLUONGTP, 1) ) * d.SOLUONG / c.SOLUONGCHUAN as SoLuong    " +
		 					"from ERP_SANPHAM a inner join ERP_MAKETOAN b on a.maketoan_fk = b.pk_seq    " +
		 					"inner join ERP_DANHMUCVATTU c on 'S' + b.ma = c.MaVatTu  " +
		 					"inner join ERP_DANHMUCVATTU_VATTU d on c.pk_seq = d.danhmucvt_fk    " +
		 					"where a.pk_seq = '" + spId + "' and c.hieuluctu <= '" + this.getDateTime() + "' and '" + this.getDateTime() + "' <= c.hieulucden and c.trangthai = '1' and c.sudung = 1    " +
		 				")  " +
		 				"DanhMuc where DanhMuc.vattu_fk is not null ";
		
		 /*if(spId.equals("100130"))
		 {
			 System.out.println("LAY Danh muc vat tu loi: " + query);
		 }*/
		 return this.db.get(query);
	}

	private boolean createLenhMuaNguyeLieuDK() 
	{
		//Insert LenhSanXuat		
		String ngayketthuc = "";
		int thang ;
		int nam ;
		int thanghientai;
		double ton;
		String query = "";

		String[] ngaydubao = this.NGAYDUBAO.split("-"); //chua nam, thang, ngay
		
		if(ngaydubao[1].equals("12")){
			ngaydubao[1] = "01";
			ngaydubao[0] = "" + (Integer.valueOf(ngaydubao[0]) + 1) ;
		}else{
			ngaydubao[1] = "" + (Integer.valueOf(ngaydubao[1]) + 1) ; 
		}

		nam  	= Integer.parseInt(ngaydubao[0]);
		ResultSet rs;
		String spId;
		
		try{					
			for(int j = 0; j < Integer.valueOf(this.SOTHANGDUBAO ).intValue(); j++){
				thang = Integer.parseInt(ngaydubao[1]) + j;	
				
				query = "SELECT YC.NGUYENLIEU_FK AS SPID, ISNULL(SUM(YEUCAU),0) AS YEUCAU " +
						"FROM ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU YC " +
						"INNER JOIN ERP_LENHSANXUATDUKIEN LSXDK ON LSXDK.PK_SEQ = YC.LENHSANXUATDUKIEN_FK " +
						"WHERE LSXDK.THANG = " + thang + " AND LSXDK.NAM = " + nam + " " +
						"GROUP BY YC.NGUYENLIEU_FK " ;
				
				System.out.println("***************LAY LSX DU KIEN: " + query);
				rs = this.db.get(query);
				
				while(rs.next())
				{
					spId = rs.getString("SPID");

					thanghientai = Integer.parseInt(this.getDateTime().substring(5, 7));
											
					System.out.println("San pham: " + spId + " and Thang - Nam: " + thang + " - " + nam);
				
					this.getInitData(spId, thang, nam);   // Khởi tạo tồn kho (this.tonkho), dat hang toi thieu (this.lotsize), tồn kho an toàn (this.tonantoan), thoi gian cho giao (this.leadtime)
				
					this.tondau = getTondau(nam, thang, spId); // Tính tồn đầu cho tới thời điểm cuối tháng trước. Tồn đầu = tồn hiện tại + tổng PO - tổng yêu cầu, tính đến thời điểm cuối tháng trước
				
					System.out.println("1.Ton dau MUA NLDK  sua khi goi ham getTondau: " + this.tondau + " ");
				
//					if(thang != Integer.parseInt(this.getDateTime().substring(5, 7))) // Vì giả sử rằng tháng đầu đã trừ tồn an tòan vào cuối tháng trước rồi 
					ton = this.tondau - this.tonantoan;				// Lấy tồn = tồn đầu - tồn an toàn, số lượng tồn còn lại phải đáp ứng được yêu cầu
//					else
//						ton = this.tondau;
				
					System.out.println("2.Ton MUA NLDK : " + ton + " ");				
											
					this.thieu = this.getDemand(thang, nam, spId); // thiếu được tính bằng tổng yêu cầu - tổng đặt hàng trong tháng
									
					System.out.println("3*************8.Thieu MUA NLDK : " + this.thieu);

					String tmp1 = "" + thang;			// this.thieu > 0 nghĩa là yêu cầu > lượng đã đặt hàng
					if(tmp1.length() == 1) tmp1 = "0" + tmp1 ;						
				
					if(thang != thanghientai){
						query = "SELECT DATEADD(Day, -1, '" + nam + "-" + tmp1 + "-01' ) AS NGAY";
						System.out.println(query);
				
						ResultSet rs2 = this.db.get(query);
						rs2.next();

						ngayketthuc = rs2.getString("NGAY").substring(0, 10);
						rs2.close();
					}else{
						ngayketthuc = this.getDateTime();
					}
				
					System.out.println("Ngay ket thuc:" + ngayketthuc);
					
					query = "SELECT COUNT(PK_SEQ) AS NUM FROM ERP_MUANGUYENLIEUDUKIEN " +
							"WHERE THANG = " + thang + " AND NAM = " + nam + " AND SANPHAM_FK = " + spId + " ";
			
					System.out.println("4.Check MUA NL DU KIEN: " + query);
			
					ResultSet rs2 = this.db.get(query);
					rs2.next();
					boolean exist = false;
					
					if(!rs2.getString("NUM").equals("0")){
						exist = true;
					}
					
					// Tồn đầu = tồn hiện tại + tổng PO - tổng yêu cầu, tính đến thời điểm cuối tháng trước					
					// Tồn = tồn đầu - tồn kho an toàn
					// Thiếu được tính bằng tổng yêu cầu - tổng đặt hàng trong tháng
					// Tồn - thiếu chính là số lượng tồn kho dư
					if(ton - this.thieu > 0){ // Huy dat hang
						
						double huy = ton - this.thieu;
						
						if(huy >= this.lotsize){

						// De nghi huy dat hang
							query = "INSERT INTO ERP_DIEUCHINHDATHANG(SANPHAM_FK, SOLUONG, TANGGIAM, NGAYTAO, NGUOITAO) " +
									"VALUES('" + spId + "','" + huy + "', '0', '" + this.getDateTime() + "', '" + this.USERID + "')";
							
							this.db.update(query);
							
						}
							
					}else if((ton - this.thieu )< 0){ // Dat them
						double datthem = (-1)*(ton - this.thieu);
						
						if(this.lotsize > datthem ){   //Nếu lotsize > datthem thì tạo 1 De nghi mua hang co so luong minimum order 

							System.out.println("Ton truoc: " + ton + " ; Yeu cau: " + this.thieu);
							
							if(exist){
								query = 	"UPDATE ERP_MUANGUYENLIEUDUKIEN SET SOLUONG = '" + this.lotsize + "'," +
											"NGAYSUA =  '" + this.getDateTime() + "', NGUOISUA =  '" + this.USERID + "' " +
											"WHERE CONGTY_FK = " + this.CTYID + " AND SANPHAM_FK = '" + spId + "' AND THANG = '" + thang + "' AND NAM =  '" + nam + "' ";
								
							}else{
								query =    	"INSERT INTO ERP_MUANGUYENLIEUDUKIEN " +
											"([CONGTY_FK], [SANPHAM_FK],[SOLUONG], [DADATHANG], [NGAYBATDAU],[NGAYKETTHUC],[THANG],[NAM]," +
											"[NGAYTAO],[NGUOITAO],[NGAYSUA],[NGUOISUA],[TRANGTHAI]) VALUES "	+
											"(" + this.CTYID + ", '" + spId + "', '" + this.lotsize + "', '0', " + null + ", '" +  ngayketthuc + "', " +
											"'" + thang + "', '" + nam + "', '" + this.getDateTime() + "', '" + this.USERID + "', " + 
											"'" + this.getDateTime() + "', '" + this.USERID + "', '1' )" ;
							}
								
							System.out.println("1.lotsize > datthem: " + query);
							ton = ton + this.lotsize;
							this.db.update(query);
						}else{
							if(exist){
								query = 	"UPDATE ERP_MUANGUYENLIEUDUKIEN SET SOLUONG = '" + datthem + "'," +
											"NGAYSUA =  '" + this.getDateTime() + "', NGUOISUA =  '" + this.USERID + "' " +
											"WHERE CONGTY_FK = " + this.CTYID + " AND SANPHAM_FK = '" + spId + "' AND THANG = '" + thang + "' AND NAM =  '" + nam + "' ";
								
							}else{
								query =    	"INSERT INTO ERP_MUANGUYENLIEUDUKIEN " +
											"([CONGTY_FK], [SANPHAM_FK],[SOLUONG], [DADATHANG], [NGAYBATDAU],[NGAYKETTHUC],[THANG],[NAM]," +
											"[NGAYTAO],[NGUOITAO],[NGAYSUA],[NGUOISUA],[TRANGTHAI]) VALUES "	+
											"(" + this.CTYID + ", '" + spId + "', '" + datthem + "', '0', " + null + ", '" +  ngayketthuc + "', " +
											"'" + thang + "', '" + nam + "', '" + this.getDateTime() + "', '" + this.USERID + "', " + 
											"'" + this.getDateTime() + "', '" + this.USERID + "', '1' )" ;
							}
								
							System.out.println("2.lotsize < datthem: " + query);
							ton = ton + datthem;
							this.db.update(query);
							
						}
							
						ton = ton  - this.thieu;
						System.out.println("Ton sau: " + ton + " ; Yeu cau: " + this.thieu);
							
					}
							
					
				}
		
			}
		}catch(java.sql.SQLException e){}
		return true;
	}

	private void getInitData(String spId, int thang, int nam){
		try{

			String query =	"SELECT	SUM(KHO_SP.AVAILABLE)  AS TONKHO, " + 
							"CASE WHEN NL.CHOPHEPTHAYTHE = 1 AND NLTHAYTHE <> NULL " +
							"THEN ISNULL(THAYTHE.TONKHOANTOAN, 0) " +
							"ELSE ISNULL(NL.TONKHOANTOAN, 0) " +
							"END AS TONKHOANTOAN, " +  
			
							"CASE WHEN NL.CHOPHEPTHAYTHE = 1 AND NLTHAYTHE <> NULL " +
							"THEN ISNULL(THAYTHE.LUONGDATHANGTOITHIEU, 0) " +
							"ELSE ISNULL(NL.LUONGDATHANGTOITHIEU, 0) " +
							"END AS MINLOTSIZE, " +
							
							"CASE WHEN NL.CHOPHEPTHAYTHE = 1 AND NLTHAYTHE <> NULL " +
							"THEN ISNULL(THAYTHE.THOIGIANCHOGIAO, 0) " +
							"ELSE ISNULL(NL.THOIGIANCHOGIAO, 0) " +
							"END AS LEADTIME  " +
							
							"FROM ERP_KHOTT_SANPHAM KHO_SP " +  
							"INNER JOIN ERP_KHOTT KHOTT ON KHOTT.PK_SEQ = KHO_SP.KHOTT_FK AND KHOTT.LOAI = 2 " +
							"INNER JOIN ERP_NGUYENLIEU NL ON NL.SANPHAM_FK = KHO_SP.SANPHAM_FK   " +
							"LEFT JOIN ( " +
							"	SELECT SANPHAM_FK, TONKHOANTOAN, LUONGDATHANGTOITHIEU, THOIGIANCHOGIAO " +
							"	FROM ERP_NGUYENLIEU " +
							"	WHERE SANPHAM_FK IN (SELECT NLTHAYTHE FROM ERP_NGUYENLIEU WHERE SANPHAM_FK = '" + spId + "') " +
							")THAYTHE ON THAYTHE.SANPHAM_FK = KHO_SP.SANPHAM_FK " +
							"WHERE  KHO_SP.SANPHAM_FK = '" + spId + "' " + 
							"GROUP BY NL.LUONGDATHANGTOITHIEU,THAYTHE.LUONGDATHANGTOITHIEU,THAYTHE.THOIGIANCHOGIAO,NL.THOIGIANCHOGIAO, " + 
							"NL.CHOPHEPTHAYTHE,THAYTHE.TONKHOANTOAN, NL.TONKHOANTOAN  " ;

			System.out.println("Tinh ton kho va lot size: " + query);
			ResultSet rs = this.db.get(query);

			rs.next();
			this.tonkho = Double.parseDouble(rs.getString("TONKHO"));
			this.lotsize = Double.parseDouble(rs.getString("MINLOTSIZE"));
			this.tonantoan = Double.parseDouble(rs.getString("TONKHOANTOAN"));
			this.leadtime = Integer.parseInt(rs.getString("LEADTIME"));
			
			rs.close();
			System.out.println("Ton an toan: " + this.tonantoan);
			
		}catch(java.sql.SQLException e){}
	}
	
	private double getTondau(int nam, int thang, String spId){
	try{
		double tondau = 0;
		int t = 0;
		int n = 0;
		String thanghientai = "";
		String thangtruoc = "";
		
		if(thang - 1 == 0) {
			t = 12;
			n = nam - 1;
		}else{
			t = thang -1;
			n = nam;
		}
		if(t < 10) thangtruoc = "0" + t;
		else thangtruoc = "" + t;
		
	
		if(thang < 10) thanghientai = "0" + (thang);
		else thanghientai = "" + (thang);

		/*String query = 	"SELECT " +
						"(ISNULL(SUM(KHO_SP.SOLUONG), 0)+ ISNULL(PO.PO, 0) - ISNULL(YEUCAU.YEUCAU, 0)) AS TONDAU " +  
						"FROM ERP_KHOTT_SANPHAM KHO_SP  " +
						"INNER JOIN ERP_KHOTT KHO ON KHO.PK_SEQ = KHO_SP.KHOTT_FK " +
						"LEFT JOIN(  " +
						"	SELECT SUM(SOLUONG) AS PO, SANPHAM_FK " +   
						"	FROM ERP_MUAHANG_SP  " +
						"	WHERE NGAYNHAN >= '" + this.getDateTime() + "' AND NGAYNHAN <= '"+ n + "-" + thangtruoc  + "-31' " +
						"	AND SANPHAM_FK = '" + spId + "' " +  
						"	GROUP BY SANPHAM_FK  " +
						")PO ON PO.SANPHAM_FK = KHO_SP.SANPHAM_FK " +  
						"LEFT JOIN(  " +
						"	SELECT SUM(YCNL.YEUCAU) AS YEUCAU, YCNL.NGUYENLIEU_FK AS SANPHAM_FK " +
						"	FROM ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU YCNL " +
						"	INNER JOIN ERP_LENHSANXUATDUKIEN LSXDK ON LSXDK.PK_SEQ = YCNL.LENHSANXUATDUKIEN_FK " +
						"	WHERE LSXDK.THANG >= " + Integer.parseInt(this.getDateTime().substring(5, 7)) + " AND LSXDK.THANG <= " + t + " AND LSXDK.NAM = "+ n + " " +
						"	AND YCNL.NGUYENLIEU_FK = '" + spId + "' " +   
						"	GROUP BY YCNL.NGUYENLIEU_FK  " +
						")YEUCAU ON YEUCAU.SANPHAM_FK = KHO_SP.SANPHAM_FK " +  
						"WHERE  KHO_SP.SANPHAM_FK = '" + spId + "'  AND KHO.LOAI = 2 " +
						"GROUP BY PO.PO, YEUCAU.YEUCAU ";*/
		
		//Yêu cầu = Tổng yêu cầu - Tổng đã dùng của LSX trong tháng
		
		String query = 	"SELECT " +
						"(ISNULL(SUM(KHO_SP.SOLUONG), 0)+ ISNULL(PO.PO, 0) - ISNULL(YEUCAU.YEUCAU, 0)) AS TONDAU " +  
						"FROM ERP_KHOTT_SANPHAM KHO_SP  " +
						"INNER JOIN ERP_KHOTT KHO ON KHO.PK_SEQ = KHO_SP.KHOTT_FK " +
						"LEFT JOIN(  " +
						"	SELECT SUM(SOLUONG) AS PO, SANPHAM_FK " +   
						"	FROM ERP_MUAHANG_SP  " +
						"	WHERE NGAYNHAN >= '" + this.getDateTime() + "' AND NGAYNHAN <= '"+ n + "-" + thangtruoc  + "-31' " +
						"	AND SANPHAM_FK = '" + spId + "' " +  
						"	GROUP BY SANPHAM_FK  " +
						")PO ON PO.SANPHAM_FK = KHO_SP.SANPHAM_FK " +  
						"LEFT JOIN(  " +
						"	SELECT SUM(YCNL.YEUCAU) AS YEUCAU, YCNL.NGUYENLIEU_FK AS SANPHAM_FK " +
						"	FROM ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU YCNL " +
						"	INNER JOIN ERP_LENHSANXUATDUKIEN LSXDK ON LSXDK.PK_SEQ = YCNL.LENHSANXUATDUKIEN_FK " +
						"	WHERE LSXDK.THANG >= " + Integer.parseInt(this.getDateTime().substring(5, 7)) + " AND LSXDK.THANG <= " + t + " AND LSXDK.NAM = "+ n + " " +
						"	AND YCNL.NGUYENLIEU_FK = '" + spId + "' " +   
						"	GROUP BY YCNL.NGUYENLIEU_FK  " +
						")YEUCAU ON YEUCAU.SANPHAM_FK = KHO_SP.SANPHAM_FK " +
						"LEFT JOIN( " +
						"	SELECT CASE WHEN BOM.CHOPHEPTHAYTHE = 1 THEN BOM.VATTUTT_FK  " +
						"		ELSE BOM.VATTU_FK END AS SANPHAM_FK,  " +
						"		sum (CASE WHEN BOM.CHOPHEPTHAYTHE = 1 THEN BOM.SOLUONGTT   " +
						"		ELSE BOM.SOLUONG END ) AS SoLuong   " +
						"	FROM ERP_LENHSANXUAT_GIAY LSX   " +
						"		INNER JOIN ERP_LENHSANXUAT_BOM_GIAY BOM ON BOM.LENHSANXUAT_FK = LSX.PK_SEQ   " +
						"		WHERE LSX.Trangthai <= 3 and LSX.CONGTY_FK =  '" + this.CTYID + "' AND LSX.NGAYBATDAU <= '" + n + "-" + thangtruoc + "-31'   " +
						"		AND LSX.NGAYBATDAU >= '" + n + "-" + thangtruoc + "-01' AND ( ( CASE WHEN BOM.CHOPHEPTHAYTHE = 1 THEN BOM.VATTUTT_FK  " +
						"  																		ELSE BOM.VATTU_FK END  ) = '" + spId + "' )  " +
						"	group by BOM.CHOPHEPTHAYTHE, VATTUTT_FK, BOM.VATTU_FK " +
						") SANXUAT on SANXUAT.SANPHAM_FK =  KHO_SP.SANPHAM_FK " +  
						"WHERE  KHO_SP.SANPHAM_FK = '" + spId + "'   " +  //AND KHO.LOAI = 2
						"GROUP BY PO.PO, YEUCAU.YEUCAU ";
		
		System.out.println("**********************Lay Ton dau :" + query);
		ResultSet rs = this.db.get(query) ;
		rs.next();
		tondau = Double.parseDouble(rs.getString("TONDAU"));			
		rs.close();
		return tondau;
		
	}catch(java.sql.SQLException e){
		return 0;
	}
			
}
	

	private double getDemand(int thang, int nam, String spId){
		ResultSet rs;
		String th = "" + thang;
		if(thang < 10) th  = "0" + thang;
		String query;
		query = 	"SELECT ISNULL(YEUCAU.TONKHOANTOAN, 0) AS TONKHOANTOAN, " +
					"ISNULL(YEUCAU.YEUCAU, 0) - ISNULL(PO.PO,0) AS YEUCAU " +
					"FROM ERP_KHOTT_SANPHAM KHO_SP  " +
					"INNER JOIN ERP_KHOTT KHO ON KHO.PK_SEQ = KHO_SP.KHOTT_FK " +
					"LEFT JOIN(  " +
					"	SELECT SUM(SOLUONG) AS PO, SANPHAM_FK " +   
					"	FROM ERP_MUAHANG_SP  " +
					"	WHERE SUBSTRING(NGAYNHAN, 1, 4) = " + nam + " AND SUBSTRING(NGAYNHAN, 5, 2) = " + th + " " +
					"	AND SANPHAM_FK = '"+ spId + "' " +
					"	GROUP BY SANPHAM_FK  " +
					")PO ON PO.SANPHAM_FK = KHO_SP.SANPHAM_FK " +  
					"LEFT JOIN(  " +
					"	SELECT SUM(NL.YEUCAU) AS YEUCAU, SUM(NL.TONKHOANTOAN) AS TONKHOANTOAN, NL.NGUYENLIEU_FK AS SANPHAM_FK " +
					"	FROM ERP_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU NL " +
					"	INNER JOIN ERP_LENHSANXUATDUKIEN LSXDK ON LSXDK.PK_SEQ = NL.LENHSANXUATDUKIEN_FK " +
					"	WHERE LSXDK.THANG = " + thang + " AND LSXDK.NAM = "+ nam + " " +
					"	AND NL.NGUYENLIEU_FK = '"+ spId + "' " + 
					"	GROUP BY NGUYENLIEU_FK  " +
					")YEUCAU ON YEUCAU.SANPHAM_FK = KHO_SP.SANPHAM_FK " +  
					"WHERE  KHO_SP.SANPHAM_FK = '"+ spId + "' --AND KHO.LOAI = 2 " ;
//					"GROUP BY PO.PO, YEUCAU.YEUCAU" ;

		System.out.println("____________Lay Yeu cau: " + query);
		rs =  this.db.get(query);
		try{
			rs.next();
			double tmp = Double.parseDouble(rs.getString("YEUCAU"));			
			this.tonantoan = Double.parseDouble(rs.getString("TONKHOANTOAN"));
			rs.close();
			return tmp;
		}catch(java.sql.SQLException e){ return 0;}
		
		
	}
	
	public void close() 
	
	{
		this.db.shutDown();
		try 
		{
			if(this.rsDvkd != null) this.rsDvkd.close();
			
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
			
			if(khRS != null){
				khRS.close();
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



	
	}
