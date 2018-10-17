package geso.traphaco.erp.beans.lapngansach.imp;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.lapngansach.IDubaokinhdoanhNam_Giay;

public class DubaokinhdoanhNam_Giay implements IDubaokinhdoanhNam_Giay
{
	String CTYID, USERID,ID,MSG,KHO,DIENGIAI,TRANGTHAI,NGAYDUBAO,SOTHANGDUBAO,PHUONGPHAP,LOAISOVOI,TENSANPHAM,NHANHANG,CHUNGLOAI, DVKDID, DVKD, NGANSACH;
	String HIEULUC, LNSID;
	ResultSet rsKho,rsPhuongphap,rsSovoi,rsSanpham,rsNhanhang,rsChungloai, rsDvkd, khRS, nsList;
	String maspstr;
	String[][] data;
	String[] spIds;
	String[] khIds;
	String[] selectedSpIds;
	String[] ngaythang;
	String ngaytonkho;
	ResultSet kenhIds;
	String kbhId;
	
	int count;
	dbutils db;

	double thieu ;
	double lotsize ;
	double tonantoan ;
	double tonkho ;
	double tondau ;
	double plnorder;
	int leadtime;
	int nam;
	String KBH;
	public DubaokinhdoanhNam_Giay()
	{
		this.db=new dbutils();
		this.LNSID = "";
		this.CTYID = "";
		this.USERID="";
		this.ID="";
		this.KHO="";
		this.DVKDID = "";
		this.DVKD = "";
		this.NGANSACH = "";
		this.NGAYDUBAO= this.getDateTime();
		this.nam = Integer.parseInt(this.getDateTime().substring(0, 4)) + 1;
		this.LOAISOVOI="";
		this.SOTHANGDUBAO="12";
		this.TENSANPHAM="";
		this.PHUONGPHAP="";
		this.DIENGIAI="";
		this.TRANGTHAI="";
		this.NHANHANG="";
		this.CHUNGLOAI="";
		this.ngaytonkho = "";
		this.MSG="";
		this.HIEULUC = "1";
		this.maspstr = "";
		this.kbhId = "";
		this.KBH = "";
	}
	public DubaokinhdoanhNam_Giay(String id)
	{
		this.db=new dbutils();
		this.CTYID = "";
		this.ID=id;
		this.USERID="";
		this.DIENGIAI="";
		this.ID=id;
		this.KHO="";
		this.NGAYDUBAO= this.getDateTime();
		this.SOTHANGDUBAO="12";
		this.PHUONGPHAP="";
		this.NHANHANG="";
		this.CHUNGLOAI="";
		this.MSG="";
		this.maspstr = "";
		this.HIEULUC = "1";
		this.ngaytonkho = "";
		this.DVKDID = "";
		this.DVKD = "";
		this.NGANSACH = "";
		this.KBH = "";
	}
	
	public String getCtyId() 
	{
		
		return this.CTYID;
	}

	
	public void setCtyId(String ctyId) 
	{
		
		this.CTYID = ctyId;
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

	public String getNgansach() 
	{
		
		return this.NGANSACH;
	}

	
	public void setNgansach(String NGANSACH) 
	{
		
		this.NGANSACH = NGANSACH;
	}
	
	public String getNsId() 
	{
		
		return this.LNSID;
	}

	
	public void setNsId(String LNSId) 
	{
		
		this.LNSID = LNSId;
	}
	
	public String getDvkdId() 
	{
		
		return this.DVKDID;
	}

	
	public void setDvkdId(String dvkdId) 
	{
		
		this.DVKDID= dvkdId;
	}
	
	public ResultSet getDvkdRs() 
	{
		
		return this.rsDvkd;
	}

	
	public void setDvkdRs(ResultSet rsDvkd) 
	{
		
		this.rsDvkd= rsDvkd;
	}
	
	public String getDvkd() 
	{
		
		return this.DVKD;
	}

	
	public void setDvkd(String Dvkd) 
	{
		
		this.DVKD= Dvkd;
	}

	public String getKBH() 
	{
		
		return this.KBH;
	}

	
	public void setKBH(String KBH) 
	{
		
		this.KBH= KBH;
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


	public int getNam() {
		
		return this.nam;
	}

	
	public void setNam(int nam) 
	{
		this.nam = nam;	
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

	public ResultSet getKenhRS() {
		
		return this.kenhIds;
	}
	
	public void setKenhRS(ResultSet kenhRS) {
		
		this.kenhIds = kenhRS;
	}

	public String getKenhId() {
		
		return this.kbhId;
	}
	
	public void setKenhId(String kbhId) {
		
		this.kbhId = kbhId;
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

	public ResultSet getNsList() 
	{
		return this.nsList;
	}
	
	public void setNsList(ResultSet nslist) {
		
		this.nsList = nslist;
	}
	

	public void createRs() 
	{
		
		createKenhRS();
		
		this.nsList = this.db.get("SELECT PK_SEQ AS NSID, DIENGIAI FROM ERP_LAPNGANSACH WHERE CONGTY_FK = " + this.CTYID + " ");
		
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
		/*query = "SELECT COUNT(SPID) AS NUM FROM (" +
				"SELECT	DISTINCT SP.PK_SEQ AS SPID, KH.PK_SEQ " +
				"FROM ERP_LAPNGANSACH_DUBAO DUBAO " +
				"INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM DUBAOSP on DUBAO.PK_SEQ = DUBAOSP.DUBAO_FK " +
				"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = DUBAOSP.SANPHAM_FK  " +
				"INNER JOIN ERP_KHACHHANG KH ON KH.PK_SEQ = DUBAOSP.KHACHHANG_FK " +											
				"WHERE DUBAO.PK_SEQ = '" + this.ID + "' AND DUBAOSP.THANG = '1' " +
				"AND DUBAOSP.NAM = '" + this.nam + "' AND DUBAO.CONGTY_FK = " + this.CTYID + " " ;*/
		
		query = "SELECT COUNT(*) AS NUM FROM (" +
				"SELECT	DISTINCT DUBAOSP.SANPHAM_FK, DUBAOSP.THANG   " +
				"FROM ERP_LAPNGANSACH_DUBAO DUBAO " +
				"INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM DUBAOSP on DUBAO.PK_SEQ = DUBAOSP.DUBAO_FK " +
				"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = DUBAOSP.SANPHAM_FK  " +
				"WHERE DUBAO.PK_SEQ = '" + this.ID + "' AND DUBAOSP.THANG = '" + th + "' " +
				"AND DUBAOSP.NAM = '" + this.nam + "' AND DUBAO.CONGTY_FK = " + this.CTYID + "  " ;
		
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
		
		//System.out.println("Cau query : " + query);
		
		ResultSet rs = this.db.get(query);
		int count = 0;
		try
		{
			if( rs.next() )
			{
				count = rs.getInt("NUM");
				this.count = count*3 ;
				rs.close();
				
				String[][] data = new String[count*3][12];
				
				for(int i=0; i < 12; i++)
				{
					if(this.ID.length() > 0)
					{
												
						query =	"SELECT	DISTINCT SP.PK_SEQ AS SPID, SP.TEN AS SPTEN, DVDL.DONVI,  " +
								"DUBAOSP.NAM, DUBAOSP.THANG,  " +								
								"ISNULL(DUBAOSP.DUKIENBAN, '0') AS DUKIENBAN " +
								"FROM ERP_LAPNGANSACH_DUBAO DUBAO " +
								"INNER JOIN ERP_LAPNGANSACH_DUBAOSANPHAM DUBAOSP on DUBAO.PK_SEQ = DUBAOSP.DUBAO_FK " +	
								"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = DUBAOSP.SANPHAM_FK " +
								"INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = SP.DVDL_FK " +
								"WHERE DUBAO.PK_SEQ = '" + this.ID + "' AND DUBAOSP.THANG = '" + (i + 1) + "' AND DUBAOSP.NAM = '" + this.nam + "' " +
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
						
						query = query + " ORDER BY SP.PK_SEQ ";
						
	
						System.out.println("INIT SANPHAM THEO THANG: " + query);
						rs = this.db.get(query);
						count = 0;
						
						while(rs.next())
						{
							data[count++][i] = rs.getString("SPID")+ ";" + rs.getString("SPTEN") ;

							data[count++][i] = rs.getString("DONVI") ;
							
							data[count++][i] = rs.getString("DUKIENBAN") ;
	
						}
						rs.close();
					}		
					
				}
				this.data = data;
				System.out.println("DATA LA: " + this.data.length );
				
			}
		}
		catch(Exception e)
		{
			System.out.println("LOI ROI : " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void createRs_New() 
	{
		this.nsList = this.db.get("SELECT PK_SEQ AS NSID, DIENGIAI FROM ERP_LAPNGANSACH WHERE CONGTY_FK = " + this.CTYID + " ");
		
		String query = "SELECT PK_SEQ AS KHID, TEN FROM ERP_KHACHHANG WHERE CONGTY_FK = " + this.CTYID + "  AND MAKETOSTOCK = 0"; 
//		this.khRS = this.db.get(query);

//		this.rsKho=this.db.get("select PK_SEQ,TEN,DIACHI  from ERP_KHOTT WHERE TRANGTHAI = '1' and CONGTY_FK = " + this.CTYID + " and LOAI = 3");
		
		this.rsDvkd = this.db.get("SELECT PK_SEQ AS DVKDID, DONVIKINHDOANH AS DVKD FROM DONVIKINHDOANH WHERE CONGTY_FK = " + this.CTYID + " ");

		createKenhRS();  				
	}

	public void init() 
	{
		String query = 	"select DUBAO.*, KBH.DIENGIAI AS KBH, DVKD.DONVIKINHDOANH AS DVKD, LNS.DIENGIAI AS NGANSACH  " +
						"from ERP_LAPNGANSACH_DUBAO DUBAO " +
						"inner join ERP_LAPNGANSACH LNS ON LNS.PK_SEQ = DUBAO.LAPNGANSACH_FK " +
						"inner join DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = DUBAO.DVKD_FK " +
						"INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ = DUBAO.KBH_FK " +
						"where DUBAO.pk_seq = '" + this.ID + "'";
		
		System.out.println("**************VAO INIT Cau query la "+query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ID = rs.getString("PK_SEQ");
					this.DIENGIAI=rs.getString("DIENGIAI");
					this.NGANSACH = rs.getString("NGANSACH");
					this.DVKD = rs.getString("DVKD");
					this.DVKDID = rs.getString("DVKD_FK");
					this.KBH = rs.getString("KBH");
					this.kbhId = rs.getString("KBH_FK");
					
					this.nam = rs.getInt("NAM");
					
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


	public String getDateTime() 
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


			String query = 	"SELECT COUNT(*) AS NUM FROM ERP_LAPNGANSACH_DUBAO WHERE LAPNGANSACH_FK = " + this.LNSID + " " +
							"AND NAM = '" + this.nam + "' AND DVKD_FK = '" + this.DVKDID + "' AND KBH_FK = " + this.kbhId + " ";
			System.out.println(query);
			
			ResultSet rs = this.db.get(query);
			
			rs.next();
			
			if(rs.getString("NUM").equals("0")){
				rs.close();
				
				query = 	"INSERT ERP_LAPNGANSACH_DUBAO (CONGTY_FK, DVKD_FK, KBH_FK, NAM, DIENGIAI, TRANGTHAI, NGUOITAO, NGAYTAO, NGUOISUA, NGAYSUA, LAPNGANSACH_FK ) " +
							"VALUES(" + this.CTYID + ", " + this.DVKDID + ", '" + this.kbhId + "','"+this.nam+"',N'"+this.DIENGIAI+"', '0', '"+USERID+"', " +
							"'"+ngaytao+"','"+USERID+"','"+ngaytao+"', " + this.LNSID + " )";
			
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
			
				rs = db.get(query);
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
				this.CreateDubaoSP(request);
			}else{
				
				return false;
			}
			
		}catch(java.sql.SQLException e){
			return false;
		}
		
		return true;
	}	
	
	private boolean CreateDubaoSP(HttpServletRequest request)  throws ServletException, IOException
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
			//int thang = Integer.valueOf(ngaytao.substring(5, 7)).intValue();
			int thang = 1;
			int nam   = Integer.valueOf(ngaytao.substring(0, 4)).intValue();
			query = "SELECT NAM FROM ERP_LAPNGANSACH WHERE PK_SEQ = " + this.LNSID + " ";
			rs = this.db.get(query);
			rs.next();
			nam = rs.getInt("NAM");
			rs.close();

			int lastyear = nam - 1;

			if(thang == 12){
				thang = 1;
				nam = nam + 1;
			}else{
				thang = thang + 1;
			}
			

			String lastmonth;
			for (int i = 1; i <= 12; i++)
			{
				
				if(i < 10)
					lastmonth = lastyear + "-0" + i;
				else
					lastmonth = lastyear + "-" + i;

				query =  "SELECT DB.DUBAO_FK, DB.spID, DB.THANG, DB.NAM, DB.DUKIENBAN, DB.DONGIA, DB.THANHTIEN, " +
						 "ISNULL(AVG3M.AVG3M_PRI, 0) AS AVG3M_PRI, ISNULL(AVG6M.AVG6M_PRI, 0) AS AVG6M_PRI,  \n  " + 
						 "ISNULL(LASTYEAR.LASTYEAR_PRI, 0) AS LASTYEAR_PRI, " + this.LNSID + " AS LNSID \n   " + 
						 " FROM  \n " + 
						 " (  \n " + 
						 " 	SELECT distinct	'" + this.ID + "' as DUBAO_FK, " + 
						 " 	SP.PK_SEQ as spID,  " + 
						 " 	ISNULL(BGBAN_SP.GIABAN, 0) as DONGIA, 0 as DUKIENBAN, 0 AS THANHTIEN, " + nam + " as NAM, " + i + " as THANG " + 
						 
						 " 	FROM ERP_SANPHAM SP  " +
						 "  LEFT JOIN ERP_LNSBGBAN_SANPHAM BGBAN_SP ON BGBAN_SP.SANPHAM_FK = SP.PK_SEQ  AND BGBAN_SP.THANG = " + thang + " " +
						 "	LEFT JOIN ERP_LNSBANGGIABAN BGBAN ON BGBAN.PK_SEQ = BGBAN_SP.BGBAN_FK AND BGBAN.KENH_FK = " + this.kbhId + " " +
						 " 	WHERE SP.TRANGTHAI = '1' AND SP.CONGTY_FK = '" + this.CTYID + "'  AND SP.DVKD_FK =  '" + this.DVKDID + "'" + 
						 " )DB \n " + 
						 " LEFT JOIN  " + 
						 " (  " + 
						 " 	SELECT	SP.PK_SEQ as spID,  " + 
						 " 			SUM (CONVERT(FLOAT, SOLUONG) )/3 AS AVG3M_PRI    " + 
						 " 	FROM ERP_XUATKHO XK " + 
						 " 		INNER JOIN ERP_XUATKHO_SANPHAM XKSP ON XK.PK_SEQ = XKSP.XUATKHO_FK    " + 
						 " 		INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = XKSP.SANPHAM_FK  " + 
						 " 		INNER JOIN ERP_DONDATHANG DDH on XK.DONDATHANG_FK = DDH.PK_SEQ 	 " + 
						 " 	WHERE XK.TRANGTHAI = 1 AND XK.NGAYCHOT >= '" + ngaydsd3M + "' AND XK.NGAYCHOT <= '" + ngaytd + "' and SP.DVKD_FK = '" + this.DVKDID + "'  " +
						 "  AND DDH.KBH_FK = " + this.kbhId + " " +
						 " 	GROUP BY SP.PK_SEQ " +
						 " ) " + 
						 " AVG3M ON AVG3M.spID = DB.spID   " + 
						 " LEFT JOIN  " + 
						 " (  " + 
						 " 	SELECT	SP.PK_SEQ as spID,  " +  
						 " 			SUM (CONVERT(FLOAT, SOLUONG) )/6 AS AVG6M_PRI    " + 
						 " 	FROM ERP_XUATKHO XK " + 
						 " 		INNER JOIN ERP_XUATKHO_SANPHAM XKSP ON XK.PK_SEQ = XKSP.XUATKHO_FK    " + 
						 " 		INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = XKSP.SANPHAM_FK  " + 
						 " 		INNER JOIN ERP_DONDATHANG DDH on XK.DONDATHANG_FK = DDH.PK_SEQ 	 " + 
						 " 	WHERE XK.TRANGTHAI = 1 AND XK.NGAYCHOT >= '" + ngaydsd6M + "' AND XK.NGAYCHOT <= '" + ngaytd + "' and SP.DVKD_FK = '" + this.DVKDID + "'  " + 
						 " 	GROUP BY SP.PK_SEQ " + 
						 " )  " + 
						 " AVG6M ON AVG6M.spID = DB.spID " + 
						 " LEFT JOIN   " + 
						 " (  " + 
						 " 	SELECT	SP.PK_SEQ as spID, " +
						 " 			SUM (CONVERT(FLOAT, SOLUONG) )/12 AS LASTYEAR_PRI    " + 
						 " 	FROM ERP_XUATKHO XK " + 
						 " 		INNER JOIN ERP_XUATKHO_SANPHAM XKSP ON XK.PK_SEQ = XKSP.XUATKHO_FK    " + 
						 " 		INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = XKSP.SANPHAM_FK  " + 
						 " 		INNER JOIN ERP_DONDATHANG DDH on XK.DONDATHANG_FK = DDH.PK_SEQ 	 " + 
						 " 	WHERE XK.TRANGTHAI = 1 AND SUBSTRING(XK.NGAYCHOT, 1, 7)  = '" + lastmonth + "' and SP.DVKD_FK = '" + this.DVKDID + "'  " + 
						 "  AND DDH.KBH_FK = " + this.kbhId + " " +
						 " 	GROUP BY SP.PK_SEQ  " +
						 " ) " + 
						 " LASTYEAR ON LASTYEAR.spID = DB.spID  ";
				
					String tmp = "INSERT INTO ERP_LAPNGANSACH_DUBAOSANPHAM " +
								 "(DUBAO_FK, SANPHAM_FK, THANG, NAM, DUKIENBAN, DONGIA, THANHTIEN, AVG3M, AVG6M, LASTYEAR, LAPNGANSACH_FK) " + query;
					
					System.out.println("___Du bao San Pham: " + query);
					if(!this.db.update(tmp))
					{
						System.out.println("ERROR:" + tmp);
						this.MSG = "Không thể tạo ERP_DUBAOSANPHAMMTO: " + tmp;
						db.getConnection().rollback();
						return false;
					}
					
					if(thang == 12) {
						thang = 1;
						//nam = nam + 1;
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
			this.MSG="Lỗi kỹ thuật ( "+e.getMessage()+" )";
			return false;
		}
	}


	public boolean update(HttpServletRequest request)  throws ServletException, IOException
	{
		System.out.println("1.__________________VAO UPDATE...............");
		request.setCharacterEncoding("UTF-8");
		String ngaytao = this.getDateTime();
		geso.traphaco.center.util.Utility util = new geso.traphaco.center.util.Utility();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
/*			if(this.HIEULUC.equals("1")){
				this.db.update("UPDATE ERP_LAPNGANSACH_DUBAO set HIEULUC = '0' WHERE CONGTY_FK = '" + this.CTYID + "' ");
			}*/
			
			String query =  "UPDATE ERP_LAPNGANSACH_DUBAO set NAM='"+this.nam +"',DIENGIAI=N'"+this.DIENGIAI+"'," +
							"NGUOISUA='" + this.USERID +"',NGAYSUA='"+ngaytao+"' where pk_seq="+this.ID;	
			System.out.println("2._______________" + query);
			
			if(!db.update(query))
			{
				this.MSG = "Không thể cập nhật dự báo kinh doanh lỗi : " + query;
				db.getConnection().rollback();
				return false;
			}

			String dukienban = "dukienban_";
			
			if(this.spIds != null)
			{
				for(int j = 0; j < this.spIds.length ; j++){
					
					for(int i = 1; i <= 12; i++){

						dukienban		= util.antiSQLInspection(request.getParameter("dukienban_" + this.spIds[j]  + "_" +  (i-1)));
						dukienban = dukienban.replaceAll(",", "");
						
						if(dukienban != null)
						{
							query =	"UPDATE ERP_LAPNGANSACH_DUBAOSANPHAM set DUKIENBAN = " + dukienban.replaceAll(",", "") + ", " +
									"THANHTIEN = DONGIA*" + dukienban + " " +
									"WHERE DUBAO_FK = '" + this.ID +"' AND THANG = '" + i + "' AND NAM = '" + nam + "' " +
									"AND SANPHAM_FK = N'" + this.spIds[j].replaceAll("&", "\"") + "' ";
       				
							System.out.println("Update Du Bao: " + query);
							if(!db.update(query))
							{
								this.MSG = "Lỗi kỹ thuật : " + query;
								this.db.getConnection().rollback();
								return false;
							}
						}
						//System.out.println("3.______________________Update lap ngan sach du Bao: " + query);
						
						if(!db.update(query))
						{
							this.MSG = "Không thể tạo dự báo kinh doanh lỗi : " + query;
							this.db.getConnection().rollback();
							return false;
						}
												
					}
				}
				
			}
			
		
//			this.TaoYeuCauNguyenLieu();

			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		 
		}
		catch (Exception e) 
		{
			db.update("rollback");
			this.MSG="666.************************Đã có lỗi xảy ra không thể cập nhật ( "+e.getMessage()+" )";
			e.printStackTrace();
			return false;
		}
	}
	
	public void UpdateCopyLNS(){
		this.CapnhatYeucau();
		this.TaoLenhSanXuatDK_MTO();
		this.TaoYeuCauNguyenLieu();
		
	}
	private void CapnhatYeucau()
	{
		String query = "SELECT DBSP.MASANPHAM, DBSP.DORONG, DBSP.TRONGLUONG_CHUANNEN, DBSP.CoreOrConeBigger, DBSP.Length, DBSP.CoreThicknessORConesmaller, DBSP.MaKeToan_Or_MaLon, DBSP.NAM, DBSP.THANG,  " +
							"SUM(DBSP.DUKIENBAN) AS YEUCAU, SUM(DBSP.DONGIA * DBSP.DUKIENBAN) AS TONGTIEN  " +
						"FROM ERP_LAPNGANSACH_DUBAOSANPHAM DBSP   " +
						"INNER JOIN ERP_LAPNGANSACH_DUBAO DUBAO ON DUBAO.PK_SEQ = DBSP.DUBAO_FK   " +
						"WHERE DUBAO.PK_SEQ =  '" + this.ID + "'   AND DUBAO.CONGTY_FK =  '" + this.CTYID + "' AND DBSP.MASANPHAM is not null  " +
						"GROUP BY DBSP.MASANPHAM, DBSP.DORONG, DBSP.TRONGLUONG_CHUANNEN, DBSP.CoreOrConeBigger, DBSP.Length, DBSP.CoreThicknessORConesmaller, DBSP.MaKeToan_Or_MaLon, DBSP.NAM, DBSP.THANG";

		if(this.KHO.length() == 0) 
			this.KHO = "0";
		
		System.out.println("___Cap nhat yeu cau: " + query);
		ResultSet dbkd = this.db.get(query); // rs1 chua tong du bao kinh doanh cua tung san pham theo tung thang, nam, lay tu du bao
		ResultSet yc;
		try
		{
			while(dbkd.next())
			{
				// Kiem tra xem san pham da co trong bang ERP_YEUCAUCUNGUNG chua (theo thang, nam, dubao_fk)?
				/*query = "SELECT COUNT(MASANPHAM) AS NUM " +
						"FROM ERP_YEUCAUCUNGUNG " +
						"WHERE THANG = '" + dbkd.getString("THANG").trim() + "' AND NAM = '" + dbkd.getString("NAM").trim() + "' " +
								"AND upper(DIENGIAI) like upper(N'%" + dbkd.getString("DIENGIAI") + "%') AND DUBAO_FK = '" + this.ID + "' AND MASANPHAM = '" + dbkd.getString("MASANPHAM") + "' AND DORONG = '" + dbkd.getString("DORONG") + "' ";*/
				
				if(this.DVKDID.equals("100000"))
				{
					query = "SELECT COUNT(MASANPHAM) AS NUM " +
							"FROM ERP_LAPNGANSACH_YEUCAUCUNGUNG " +
							"WHERE THANG = '" + dbkd.getString("THANG").trim() + "' AND NAM = '" + dbkd.getString("NAM").trim() + "' " +
									" AND DUBAO_FK = '" + this.ID + "' AND MASANPHAM = '" + dbkd.getString("MASANPHAM") + "' AND DORONG = '" + dbkd.getString("DORONG") + "' ";
				}
				else
				{
					query = "SELECT COUNT(MASANPHAM) AS NUM " +
							"FROM ERP_LAPNGANSACH_YEUCAUCUNGUNG " +
							"WHERE THANG = '" + dbkd.getString("THANG").trim() + "' AND NAM = '" + dbkd.getString("NAM").trim() + "' " +
									" AND DUBAO_FK = '" + this.ID + "' AND MASANPHAM = '" + dbkd.getString("MASANPHAM") + "' AND TRONGLUONG_CHUANNEN = '" + dbkd.getString("TRONGLUONG_CHUANNEN") + "' and CoreOrConeBigger = '" + dbkd.getString("CoreOrConeBigger") + "' and Length = '" + dbkd.getString("Length") + "' and CoreThicknessORConesmaller = '" + dbkd.getString("CoreThicknessORConesmaller") + "' ";
				}
				
				//System.out.println("___Cap nhat yeu cau - CHECK: " + query);
				yc = this.db.get(query); // yc chua ket qua kiem tra xem san pham da co trong ERP_YEUCAUCUNGUNG chua?
				yc.next();

				// Neu chua co thi them vao bang ERP_YEUCAUCUNGUNG
				if(yc.getString("NUM").equals("0"))
				{
					if(!dbkd.getString("YEUCAU").equals("0")) //NHOM DU BAO THEO MA KE TOAN, KHONG CHIA DU BAO THEO DIEN GIAI CUA SP
					{
						query = "INSERT INTO ERP_LAPNGANSACH_YEUCAUCUNGUNG(MASANPHAM, DORONG, TRONGLUONG_CHUANNEN, DBSP.CoreOrConeBigger, DBSP.Length, DBSP.CoreThicknessORConesmaller, DIENGIAI, MaKeToan_Or_MaLon, THANG, NAM, YEUCAU, TONGTIEN, TUAN1, TUAN2, TUAN3, TUAN4, DUBAO_FK, KHOTT_FK) VALUES (" +
								"'" + dbkd.getString("MASANPHAM") + "', '" + dbkd.getString("DORONG") + "', '" + dbkd.getString("TRONGLUONG_CHUANNEN") + "', '" + dbkd.getString("CoreOrConeBigger") + "', '" + dbkd.getString("Length") + "', '" + dbkd.getString("CoreThicknessORConesmaller") + "', N'', '" + dbkd.getString("MaKeToan_Or_MaLon") + "', '" + dbkd.getString("THANG") + "', '" + dbkd.getString("NAM") + "', " +
								"" +  dbkd.getString("YEUCAU") + ", " + dbkd.getString("TONGTIEN") + ", " + Double.parseDouble(dbkd.getString("YEUCAU"))/4 + ", " + Double.parseDouble(dbkd.getString("YEUCAU"))/4 + ", " +
								"" + Double.parseDouble(dbkd.getString("YEUCAU"))/4 + ", " + Double.parseDouble(dbkd.getString("YEUCAU"))/4 + ", " + this.ID + ", " + this.KHO + " )";
						//System.out.println(query);
					
						if(!this.db.update(query))
							System.out.println(query);
					}
				}
				else
				{
					// Neu du bao kinh doanh cua san pham trong thang, nam khac 0
					if(!dbkd.getString("YEUCAU").equals("0"))
					{
						query = "UPDATE ERP_LAPNGANSACH_YEUCAUCUNGUNG SET YEUCAU = " +  dbkd.getString("YEUCAU") + ", TONGTIEN = " + dbkd.getString("TONGTIEN") + ", TUAN1 = " + Double.parseDouble(dbkd.getString("YEUCAU"))/4 + ", " +
								"TUAN2 = " + Double.parseDouble(dbkd.getString("YEUCAU"))/4 + ", TUAN3 = " + Double.parseDouble(dbkd.getString("YEUCAU"))/4 + ", " +
								"TUAN4 = " + Double.parseDouble(dbkd.getString("YEUCAU"))/4 + ", DUBAO_FK = " + this.ID + ", KHOTT_FK =  " + this.KHO + " " +
								"WHERE THANG = '" + dbkd.getString("THANG") + "' " +
								"AND NAM = '" + dbkd.getString("NAM") + "' AND DUBAO_FK = '" + this.ID + "' AND MASANPHAM = '" + dbkd.getString("MASANPHAM") + "' ";
						if(this.DVKDID.equals("100000"))
							query += " AND DORONG = '" + dbkd.getString("DORONG") + "' ";
						else
							query += " AND TRONGLUONG_CHUANNEN = '" + dbkd.getString("TRONGLUONG_CHUANNEN") + "' and CoreOrConeBigger = '" + dbkd.getString("CoreOrConeBigger") + "' and Length = '" + dbkd.getString("Length") + "' and CoreThicknessORConesmaller = '" + dbkd.getString("CoreThicknessORConesmaller") + "' ";
						
						//System.out.println(query);
						if(!this.db.update(query))
							System.out.println(query);
					}
					else
					{
						query = "DELETE FROM ERP_LAPNGANSACH_YEUCAUCUNGUNG WHERE THANG = '" + dbkd.getString("THANG") + "' " +
								"AND NAM = '" + dbkd.getString("NAM") + "' AND DUBAO_FK = '" + this.ID + "' AND MASANPHAM = '" + dbkd.getString("MASANPHAM") + "' ";
						if(this.DVKDID.equals("100000"))
							query += " AND DORONG = '" + dbkd.getString("DORONG") + "' ";
						else
							query += " AND TRONGLUONG_CHUANNEN = '" + dbkd.getString("TRONGLUONG_CHUANNEN") + "' and CoreOrConeBigger = '" + dbkd.getString("CoreOrConeBigger") + "' and Length = '" + dbkd.getString("Length") + "' and CoreThicknessORConesmaller = '" + dbkd.getString("CoreThicknessORConesmaller") + "' ";
						
						//System.out.println("XOA YCCU: " + query);
						if(!this.db.update(query))
						{
							System.out.println("Loi khi xoa YCCU: " + query);
						}
					
					}
				}
				
				yc.close();
				
			}
			dbkd.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("___Exception Tao YC: " + e.getMessage());
		}
		
		/*String query = 	"SELECT DBSP.SANPHAM_FK, DBSP.NAM, DBSP.THANG, SUM(DBSP.DUKIENBAN) AS YEUCAU " +
						"FROM ERP_LAPNGANSACH_DUBAOSANPHAM DBSP " +
						"INNER JOIN ERP_LAPNGANSACH_DUBAO DUBAO ON DUBAO.PK_SEQ = DBSP.DUBAO_FK " +
						"WHERE DUBAO.PK_SEQ = " + this.ID +  " AND DUBAO.KHO_FK = 100000 AND DUBAO.CONGTY_FK = " + this.CTYID + " " +
						"GROUP BY DBSP.SANPHAM_FK, DBSP.NAM, DBSP.THANG " ;
		
		System.out.println("5____________Cap nhat yeu cau: " + query);
		ResultSet dbkd = this.db.get(query); // rs1 chua tong du bao kinh doanh cua tung san pham theo tung thang, nam, lay tu du bao
		ResultSet yc;
		try{
			while(dbkd.next()){
				// Kiem tra xem san pham da co trong bang ERP_YEUCAUCUNGUNG chua (theo thang va nam)?
				query = 	"SELECT COUNT(SANPHAM_FK) AS NUM " +
							"FROM ERP_LAPNGANSACH_YEUCAUCUNGUNG " +
							"WHERE KHOTT_FK = 100000 AND THANG = '" + dbkd.getString("THANG") + "' " +
							"AND NAM = '" + dbkd.getString("NAM") + "' AND SANPHAM_FK = '" + dbkd.getString("SANPHAM_FK") + "' AND DUBAO_FK = " + this.ID + " ";
				
				System.out.println("6_____________-Kiem tra xem da ton tai yeu cau: " + query);
				
				yc = this.db.get(query); // yc chua ket qua kiem tra xem san pham da co trong ERP_YEUCAUCUNGUNG chua?
				yc.next();

				// Neu chua co thi them vao bang ERP_YEUCAUCUNGUNG
				if(yc.getString("NUM").equals("0")){
					if(!dbkd.getString("YEUCAU").equals("0")){
						query = "INSERT INTO ERP_LAPNGANSACH_YEUCAUCUNGUNG(KHOTT_FK, SANPHAM_FK, THANG, NAM, YEUCAU, TUAN1, TUAN2, TUAN3, TUAN4, DUBAO_FK) VALUES (" +
								"'100000', '" + dbkd.getString("SANPHAM_FK") + "', '" + dbkd.getString("THANG") + "', '" + dbkd.getString("NAM") + "', " +
								"" +  dbkd.getString("YEUCAU") + ", " + Double.parseDouble(dbkd.getString("YEUCAU"))/4 + ", " + Double.parseDouble(dbkd.getString("YEUCAU"))/4 + ", " +
								"" + Double.parseDouble(dbkd.getString("YEUCAU"))/4 + ", " + Double.parseDouble(dbkd.getString("YEUCAU"))/4 + ", " + this.ID + " )";
					
					
						if(!this.db.update(query))
							System.out.println(query);
					}
				}else{
					// Neu du bao kinh doanh cua san pham trong thang, nam khac 0
					if(!dbkd.getString("YEUCAU").equals("0")){
						query = "UPDATE ERP_LAPNGANSACH_YEUCAUCUNGUNG SET YEUCAU = "+  dbkd.getString("YEUCAU") + ", TUAN1 = " + Double.parseDouble(dbkd.getString("YEUCAU"))/4 + ", " +
								"TUAN2 = " + Double.parseDouble(dbkd.getString("YEUCAU"))/4 + ", TUAN3 = " + Double.parseDouble(dbkd.getString("YEUCAU"))/4 + ", " +
								"TUAN4 = " + Double.parseDouble(dbkd.getString("YEUCAU"))/4 + ", DUBAO_FK = " + this.ID + " " +
								"WHERE KHOTT_FK = 100000 AND THANG = '" + dbkd.getString("THANG") + "' " +
								"AND NAM = '" + dbkd.getString("NAM") + "' AND SANPHAM_FK = '" + dbkd.getString("SANPHAM_FK") + "' AND DUBAO_FK = " + this.ID + " ";

						if(!this.db.update(query))
							System.out.println(query);
					}else{
						query = "DELETE FROM ERP_LAPNGANSACH_YEUCAUCUNGUNG WHERE KHOTT_FK = 100000 AND THANG = '" + dbkd.getString("THANG") + "' " +
								"AND NAM = '" + dbkd.getString("NAM") + "' AND SANPHAM_FK = '" + dbkd.getString("SANPHAM_FK") + "'";

						if(!this.db.update(query))
							System.out.println(query);
						
					}
				}
				
				yc.close();
				
			}
			dbkd.close();
		}
		catch(Exception e)
		{
			System.out.println("8_________________EXCEPTION CAP nhat: " + e.getMessage());
		}*/
	}
	
	private boolean TaoLenhSanXuatDK_MTO() 
	{
		String query = "";
		ResultSet rs;
		try
		{
			int nam = this.nam;

			rs = db.get("select lapngansach_fk, dvkd_fk from ERP_LAPNGANSACH_DUBAO where pk_seq = '" + this.ID + "' ");
			System.out.println("LAY LAP NS_____: select lapngansach_fk, dvkd_fk from ERP_LAPNGANSACH_DUBAO where pk_seq = '" + this.ID + "' ");
			if(rs.next())
			{
				this.LNSID = rs.getString("lapngansach_fk");
				this.DVKDID = rs.getString("dvkd_fk");
			}
			//System.out.println("______________-LNS ID: " + this.LNSID);

			for(int j = 1; j <= 12; j++)
			{
				query = " delete ERP_LAPNGANSACH_LENHSANXUATDUKIEN where thang = '" + j + "' and nam = '" + nam + "' ";
				db.update(query);
				
				query = "INSERT INTO ERP_LAPNGANSACH_LENHSANXUATDUKIEN " +
						"([CONGTY_FK], [NHAMAY_FK], [MASANPHAM], [DORONG], [TRONGLUONG_CHUANNEN], [CoreOrConeBigger], [Length], [CoreThicknessORConesmaller], [SOLUONG], [SANXUAT], [THANG],[NAM]," +
						"[NGAYTAO],[NGUOITAO],[NGAYSUA],[NGUOISUA],[TRANGTHAI], LAPNGANSACH_FK ) "	+
						"SELECT '100005', '100000', masanpham, dorong, trongluong_chuannen, CoreOrConeBigger , Length, CoreThicknessORConesmaller, sum(yeucau) as yeucau,  " +
							"'0', '" + j + "', '" + this.nam + "', '" + this.getDateTime() + "', '" + this.USERID + "', '" + this.getDateTime() + "', '" + this.USERID + "', '1', '" + this.LNSID + "'  " +
						"FROM ERP_LAPNGANSACH_YEUCAUCUNGUNG where THANG = '" + j + "' and NAM = '" + this.nam + "' " +
						"group by masanpham, dorong, trongluong_chuannen, CoreOrConeBigger , Length, CoreThicknessORConesmaller ";
				
				System.out.println("3.Khoi Tao De Nghi - LSX du kien: " + query);
				db.update(query);
				
			}
		}
		catch(Exception e)
		{
			System.out.println("Loi tao LSX du kien:" + e.toString());
		}
		
		return true; 
	}

	private void TaoYeuCauNguyenLieu()
	{
		String query;
		ResultSet SPrs;
		ResultSet NLrs;
		
		System.out.println("Bat dau thuc hien YCNL.... ");
		
		//String[] ngaydubao = this.NGAYDUBAO.split("-"); //chua nam, thang, ngay
		int nam = 2014;

		for(int j = 1; j < 12; j++)
		{
			query = " delete ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU " +
					"where LENHSANXUATDUKIEN_FK in ( select PK_SEQ from ERP_LAPNGANSACH_LENHSANXUATDUKIEN where thang = '" + j + "' and nam = '" + nam + "' ) ";
			db.update(query);
						
			//query = "SELECT PK_SEQ AS ID, SANPHAM_FK, SOLUONG FROM ERP_LENHSANXUATDUKIEN WHERE THANG = " + thang + " AND NAM = " + nam + " and FROM_DUBAO = '1' ";
			query = "SELECT PK_SEQ AS ID, MASANPHAM, DORONG, TRONGLUONG_CHUANNEN, SOLUONG " +
					"FROM ERP_LAPNGANSACH_LENHSANXUATDUKIEN WHERE THANG = " + j + " AND NAM = " + nam + "  and FROM_DUBAO = '1' ";
			
			System.out.println("INIT LSX DU KIEN: " + query);
				
			SPrs = this.db.get(query);
				
			try
			{
				if(SPrs != null)
				{
					while(SPrs.next())
					{
						String id = SPrs.getString("ID");
						//String spId = SPrs.getString("SANPHAM_FK");
						String spId = SPrs.getString("MASANPHAM");
						String soluong = SPrs.getString("SOLUONG");
						String dorong = SPrs.getString("DORONG") == null ? "0" : SPrs.getString("DORONG");
						String trongluong_chuannen = SPrs.getString("TRONGLUONG_CHUANNEN") == null ? "" : SPrs.getString("TRONGLUONG_CHUANNEN");
						
						// Lay ra yeu cau nguyen lieu cho 1 lenh san xuat du kien
						NLrs = getDanhmucvattu(spId, soluong, dorong, trongluong_chuannen);    
				
						/*****************************Luu lai nhung SP trong BOM ma la Thanh Pham Nhom - Loi ( Tao ra ERP_LENHSANXUATDUKIEN co FROM_DUBAO = 0 )**************************/
						
						if(NLrs != null)
						{
							while(NLrs.next())
							{
								//Neu VATTU_FK la THANHPHAM NHOM or LOI thi tao ra lenh SX dong thoi, sau khi chay xong, quet lai cac ERP_LENHSANXUATDUKIEN ma FROM_DUBAO = 0
								
								if( !NLrs.getString("loaiSP").equals("100005") && !NLrs.getString("loaiSP").equals("100012") )
								{
									query = "INSERT INTO ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU (LENHSANXUATDUKIEN_FK, MANGUYENLIEU, DORONG, YEUCAU, TONKHOANTOAN) " +
											"VALUES( " + id + ", N'" + NLrs.getString("MAVATTU") + "', '" + NLrs.getString("DORONG_TOIUU") + "', " + NLrs.getString("SOLUONG") + ", '0')" ;
									
									//System.out.println("1. Insert ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU: " + query);
									if(!this.db.update(query))
									{
										query = " UPDATE ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU SET YEUCAU = " + NLrs.getString("SOLUONG") + 
												" WHERE LENHSANXUATDUKIEN_FK = " + id + " AND MANGUYENLIEU = N'" + NLrs.getString("MAVATTU") + "' AND DORONG = '" + NLrs.getString("DORONG_TOIUU") + "' " ;
											
										//System.out.println("2.Update ERP_LAPNGANSACH_LENHSANXUATDUKIEN_YEUCAUNGUYENLIEU: " + query);
										this.db.update(query);
									}
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
	
	private ResultSet getDanhmucvattu(String spId, String soluong, String dorong, String trongluong_chuannen)
	{	
		String sqlSP = "select count(*) as sodong from ERP_MAKETOAN where MA = '" + spId + "' ";
		ResultSet rsSp = db.get(sqlSP);
		String dvkd_fk = "";
		String chungloai_fk = "";
		
		try 
		{
			if(rsSp.next())
			{
				if(rsSp.getInt("sodong") > 0 ) //NHOM SU DUNG MA KE TOAN
				{
					dvkd_fk = "100000"; //NHOM...........
				}
				else
				{
					//LOI VA SP MOI LAY MA LON
					sqlSP = "select distinct CHUNGLOAI_FK, DVKD_FK from ERP_SANPHAM where MA = '" + spId + "'";
					ResultSet rsSP2 = db.get(sqlSP);
					
					if(rsSP2 != null)
					{
						if(rsSP2.next())
						{
							dvkd_fk = rsSP2.getString("DVKD_FK");
							chungloai_fk = rsSP2.getString("CHUNGLOAI_FK");
						}
						rsSP2.close();
					}
				}
			}
			rsSp.close();
		} 
		catch (Exception e) {}
		
		String query = "";
		if( dvkd_fk.equals("100000") ) //NHOM -- DUNG MA KE TOAN, TU DE XUAT DO RONG NGUYEN LIEU MUA TOI UU
		{
			query =  " select danhmuc.*," + 
					 " 	case when danhmuc.DINHLUONG <= 80 then " + 
					 " 	(  " + 
					 " 		case danhmuc.loaiSP  when 100013 then ( select max(NO_OF_UP) from ERP_NO_OF_SLITTING where FG_WIDTH = " + dorong + " )    " + 
					 " 							 when 100014 then ( select max(NO_OF_UP) - 2 from ERP_NO_OF_SLITTING where FG_WIDTH = " + dorong + " ) else 0  end    " + 
					 " 	) " + 
					 " 	else " + 
					 " 	( " + 
					 " 		case danhmuc.loaiSP  when 100013 then ( select max(NO_OF_UP) from ERP_NO_OF_SLITTING_UP80 where FG_WIDTH = " + dorong + " )    " + 
					 " 							 when 100014 then ( select max(NO_OF_UP) - 2 from ERP_NO_OF_SLITTING_UP80 where FG_WIDTH = " + dorong + " ) else 0  end " + 
					 " 	)  " + 
					 " 	end as DORONG_TOIUU" + 
					 " from" + 
					 " (" + 
					 " 	select b.DANHMUCVT_FK, case when ( ( ( select top(1) dvkd_fk from ERP_SANPHAM where MA = b.MAVATTU ) = '100000' ) and b.VATTU_FK != '-1' and ( ( select top(1) LOAISANPHAM_FK from ERP_SANPHAM where MA = b.MAVATTU ) != '100012' ) )   " + 
					 " 							then ( select top(1) MA from ERP_MAKETOAN where PK_SEQ in ( select maketoan_fk from ERP_SANPHAM where MA = b.MAVATTU ) )  " + 
					 " 							else b.MAVATTU end as MAVATTU, b.SOLUONG * " + soluong + " / a.soluongChuan as SOLUONG, " + 
					 " 			isnull((select top(1) loaisanpham_fk from ERP_SANPHAM where MA = b.MAVATTU ), -1) as loaiSP, " + 
					 " 			( select top(1) sp.DINHLUONG from ERP_SANPHAM sp inner join ERP_MAKETOAN kt on sp.MAKETOAN_FK = kt.PK_SEQ where kt.MA = '" + spId + "' and DINHLUONG is not null ) as dinhluong" + 
					 " 	from ERP_DANHMUCVATTU  a inner join ERP_DANHMUCVATTU_VATTU b on a.PK_SEQ = b.DANHMUCVT_FK " + 
					 " 	where a.MAVATTU = 'S" + spId + "' and a.sudung = 0" + 
					 " )" + 
					 " danhmuc";
			
		}
		else  //LOI + SP MOI
		{
			if(dvkd_fk.equals("100004"))
			{
				if(chungloai_fk.equals("100031"))  // CHUNG LOAI: Ống giấy (Core)
	    		{
	    			
	    		}
	    		else
	    		{
	    			if(chungloai_fk.equals("100040"))  //Lõi giấy (Cone)
	    			{
	    				
	    			}
	    			else
	    			{
	    				
	    			}
	    		}
			}
		}
		
		System.out.println("LAY Danh muc vat tu: " + query);
		if(query.trim().length() <= 0 )
			return null;
		
		return this.db.get(query);
	}

	private void createKenhRS(){  				
		
		this.kenhIds  =  this.db.get("select diengiai as kbh, pk_seq as kbhId from kenhbanhang where trangthai='1'");
	}
	
	public void close() 
	{
		try 
		{
			if(this.nsList != null)
				this.nsList.close();

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
		
			if(this.rsDvkd != null)
				this.rsDvkd.close();
		
			this.db.shutDown();
		}
		catch (SQLException e) 
		{
			System.out.println(e.toString());
		}	
		
	}


	
	}
