package geso.traphaco.erp.beans.butrucongno.imp;

import java.io.Serializable;
import java.sql.ResultSet;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.beans.butrucongno.IButrucongno;

public class Butrucongno implements IButrucongno, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String id;
	String ngaychungtu;
	String trangthai;
	String tungay;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String denngay;
	String msg;
	String dienGiaiCT;
	
	String sohoadontu = "";
	String sohoadonden = "";
	String sotientu = "";
	String sotienden = "";
		
	String sochungtu = ""; 
	
	String tongchuyenno = "";
	String tongnhanno = "";
	
	String ghichu;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	ResultSet khlist;
	String khId;
	
	String DdkdId;
	ResultSet Ddkdlist;
	
	String TienteId;
	ResultSet Tientelist;
	
	private String tenKHChuyenNo;
	String KHChuyenNoId;
	ResultSet KHChuyenNolist;
	
	private String tenKHNhanNo;
	String KHNhanNoId;
	ResultSet KHNhanNolist;

	String[] HdIds;
	String[] KhIds;
	
	String[] HdChon;
	String[] HdId;
	String[] HdTigia;
	String[] HdNgayhd;
	String[] HdLoai;
	String[] HdSohd;
	String[] HdSotiengoc;
	String[] HdSotienphaixoa;
	String[] HdSotienxoa;
	String[] HdSotienconlai;
		
	ResultSet hoadonlist;
	String[] khIds;
	
	dbutils db;
	
	String tc_duno_tc;
	String tc_duco_tc;
	
	String tc_ghi_co;
	String tc_ghi_no;
	String ckKh;
	
	String congtyId;
	String nppdangnhap;
	
	String KHChuyenNoIdGoc;
	String KHNhanNoIdGoc;
	
	String isNPPNhanNo;
	String isNPPChuyenNo;
	
	public Butrucongno(String id)
	{
		this.id = id;
		this.trangthai = "";
		this.tungay = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.denngay = "";
		this.khId = "";
		this.msg = "";
		this.ghichu= "";
		this.sohoadonden= "";
		this.sohoadontu = "";
		this.sotienden= "";
		this.sotientu = "";
		this.DdkdId="";
		this.tc_duco_tc="0";
		this.tc_duno_tc="0";
		this.tc_ghi_co="0";
		this.tc_ghi_no="0";
		this.TienteId  = "";
		this.KHChuyenNoId = "";
		this.KHNhanNoId ="";
		this.ckKh="";
		this.ngaychungtu = "";
		this.tongchuyenno = "0";
		this.tongnhanno = "0";
		this.sochungtu = "";
		this.congtyId = "";
		this.nppdangnhap = "";
		this.KHChuyenNoIdGoc = "";
		this.KHNhanNoIdGoc = "";
		this.isNPPNhanNo = "";
		this.isNPPChuyenNo = "";		
		this.dienGiaiCT = "";
		
		this.tenKHChuyenNo = "";
		this.tenKHNhanNo = "";
		
		this.db = new dbutils();
	}
	
	public Butrucongno(String[] param)
	{
		this(param[0]);
		this.id = param[0];
		this.trangthai = param[2];
		this.tungay = param[3];
		this.ngaytao = param[4];
		this.nguoitao = param[5];
		this.ngaysua = param[6];
		this.nguoisua = param[7];
		this.denngay = param[8];
	}
	
	
	public boolean CreateNvgn()
	{	
		this.ngaytao = Utility.getCurrentDay();
		this.nguoitao = this.userId;
		
		if(KHNhanNoId.trim().length()<=0)
		{
			this.msg = "VUI LÒNG CHỌN KHÁCH HÀNG NHẬN NỢ";
			return false;
		}
		
		ResultSet rsbtcn = null;
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String	query = 
					" insert into ERP_BUTRUKHACHHANG(SOCHUNGTU, TRANGTHAI, TIENTE_FK, NGAYTAO\n"
					+ ", NGAYSUA, NGUOITAO, NGUOISUA, NGAYBUTRU\n"
					+ ", KH_CHUYENNO, KH_NHANNO, TONGTIEN, TIGIA\n"
					+ ", CONGTY_FK, NPP_FK, ISNPP, isNPPNhanNo\n"
					+ ", DIENGIAI) " +
					" values('"+this.sochungtu+"', 0, N'" + this.TienteId + "','" + this.ngaytao + "'\n"
					+ ", '" + this.ngaytao + "','" + this.nguoitao +"','" + this.nguoitao +"','"+this.ngaychungtu+"'\n"
					+ ",'" + this.KHChuyenNoId + "',"+this.KHNhanNoId+","+this.tongchuyenno.replaceAll(",", "")+",1\n"
					+ ", "+this.congtyId+", "+this.nppdangnhap+", " +this.isNPPChuyenNo + ", " + this.isNPPNhanNo + "\n"
					+ ", N'"+this.dienGiaiCT+"')";
				
			System.out.print(query);
			
			if(!db.update(query))
			{
				db.update("rollback");
				this.msg = "Khong the tao moi 'ERP_BUTRUKHACHHANG' , " + query;
				return false; 
			}
			
			query = "select IDENT_CURRENT('ERP_BUTRUKHACHHANG') as btcnId";

			rsbtcn = this.db.get(query);	
			try{
				if(rsbtcn!=null)
				{
				  while(rsbtcn.next())
				  {
					  this.id = rsbtcn.getString("btcnId");
				  }
				  rsbtcn.close();
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			
			if(this.HdId.length > 0)
				{									
					for(int i = 0; i < this.HdId.length; i++)
					{	
						double Hd_sotienxoa = Double.parseDouble(HdSotienxoa[i].replaceAll(",", ""));
						if(Hd_sotienxoa != 0)
						{
							
							if(this.HdLoai[i].equals("7") || this.HdLoai[i].equals("3")) // HÓA ĐƠN HÀNG TRẢ VỀ || THU TIỀN TRẢ TRƯỚC
								Hd_sotienxoa = Math.abs(Hd_sotienxoa);
							
							String sql = "insert into ERP_BUTRUKHACHHANG_CHITIET( BTKH_FK, LOAIHD, HOADON_FK, TIGIA , TIENHD , XOANO )"+
								         " values('" + this.id + "', '"+this.HdLoai[i]+"','"+ this.HdId[i] + "', " + this.HdTigia[i] + ", " + this.HdSotiengoc[i].replaceAll(",", "") + ", "+Hd_sotienxoa +")";
							System.out.print("INSERT ERP_BUTRUKHACHHANG_CHITIET "+ sql);
							if(!db.update(sql))
							{
								db.update("rollback");
								this.msg = "Khong the cap nhat BUTRUCONGNO_HOADON, " + sql;
								return false; 
							}
						}
					}
				
					db.getConnection().commit();
					db.getConnection().setAutoCommit(true);
				}
		} 
		catch(Exception e) {
			db.update("rollback");
			e.printStackTrace();
			this.msg = "Loi khi cap nhat bang "+e.toString();
			return false; 
		}
		finally{try {
			if(rsbtcn != null)
				rsbtcn.close();
		} catch (Exception e2) {
		}}
		return true;	
	}
	
	public boolean UpdateNvgn()
	{
		this.ngaysua = Utility.getCurrentDay();
		this.nguoisua = this.userId;
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = " update ERP_BUTRUKHACHHANG set NGAYBUTRU  = '" + this.ngaychungtu + "', sochungtu = '" + this.sochungtu + "', "+
			               "      nguoisua='" + this.nguoisua + "',  ngaysua='" + this.ngaysua + "', tiente_fk = '"+this.TienteId+"', " +
			               " 	  KH_CHUYENNO = '"+this.KHChuyenNoId+"', KH_NHANNO = '"+this.KHNhanNoId+"', tongtien = "+this.tongchuyenno.replaceAll(",", "")+
			               "	,DIENGIAI =N'"+this.dienGiaiCT+"'" +
			               " where pk_seq='" + this.id + "' and trangthai=0";
			
			System.out.println("UPDATE BUTRUCONGNO ____"+ query);
			
			if(db.updateReturnInt(query)!=1)
			{
				db.getConnection().rollback();
				this.msg = "Khong the cap nhat 'ERP_BUTRUKHACHHANG' , " + query;
				return false; 
			}	
			
			query = "DELETE ERP_BUTRUKHACHHANG_CHITIET where BTKH_FK = '" + this.id + "'";
			
			System.out.println("DELETE ERP_BUTRUKHACHHANG_CHITIET ____"+ query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = query;
				return false;
			}
			
			query = "DELETE ERP_BUTRUKHACHHANG_PHANBO_CHITIET where BTKH_FK = '" + this.id + "'";
			
			System.out.println("DELETE ERP_BUTRUKHACHHANG_PHANBO_CHITIET ____"+ query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = query;
				return false;
			}
			
			if(this.HdId.length > 0)
			{									
				for(int i = 0; i < this.HdId.length; i++)
				{	
					double Hd_sotienxoa = Double.parseDouble(HdSotienxoa[i].replaceAll(",", ""));
					
					if(this.HdLoai[i].equals("7") || this.HdLoai[i].equals("3")) // HÓA ĐƠN HÀNG TRẢ VỀ || THU TIỀN TRẢ TRƯỚC
						Hd_sotienxoa = Math.abs(Hd_sotienxoa);
					
					if(Hd_sotienxoa != 0)
					{
						String sql = "insert into ERP_BUTRUKHACHHANG_CHITIET(BTKH_FK, LOAIHD, HOADON_FK, TIGIA , TIENHD , XOANO )"+
							         " values('" + this.id + "', '"+this.HdLoai[i]+"','"+ this.HdId[i] + "', " + this.HdTigia[i] + ", " + this.HdSotiengoc[i].replaceAll(",", "") + ", "+Hd_sotienxoa +")";
						System.out.print("INSERT ERP_BUTRUKHACHHANG_CHITIET "+ sql);
						if(!db.update(sql))
						{
							db.update("rollback");
							this.msg = "Khong the cap nhat BUTRUCONGNO_HOADON, " + sql;
							return false; 
						}
						
					}
				}
			
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
			}
			
		} 
		catch(Exception e) {
			e.printStackTrace();
			db.update("rollback");
			this.msg = "Loi khi cap nhat bang "+e.toString();
			return false; 
		}
		return true;
	}
	
	public void init() 
	{
		String query =  
				" select pk_seq , NGAYBUTRU , isnull(SOCHUNGTU,'') as SOCHUNGTU\n" + 
				", KH_CHUYENNO, KH_NHANNO, TONGTIEN, TIENTE_FK\n" + 
				", isnull(isNPP,0) isNPP, isnull(isNPPNhanNo,0) isNPPNhanNo ,ISNULL(DIENGIAI,'') AS DIENGIAI \n" +
				
				", (case when isNull(isNPP,0) = 1 then \n" +
				"			(select isNull(ma, '') + ' - ' + isNull(mafast, '') + ' - ' + ten \n"+
				"			from nhaPhanPhoi npp \n" +
				"			where npp.pk_seq = KH_CHUYENNO )" + 
				"		when isNull(isNPP,0) = 0 then \n" +
				"			(select isNull(mafast, '') + ' - ' + ten \n"+
				"			from  khachHang npp \n" +
				"			where npp.pk_seq = KH_CHUYENNO ) end) tenKHChuyenNo\n" +
				
				", (case when isNull(isNPP,0) = 1 then \n" +
				"			(select isNull(ma, '') + ' - ' + isNull(mafast, '') + ' - ' + ten \n"+
				"			from nhaPhanPhoi npp \n" +
				"			where npp.pk_seq = KH_NHANNO )" + 
				"		when isNull(isNPP,0) = 0 then \n" +
				"			(select isNull(mafast, '') + ' - ' + ten \n"+
				"			from  khachHang npp \n" +
				"			where npp.pk_seq = KH_NHANNO ) end) tenKHNhanNo\n" +

				" from ERP_BUTRUKHACHHANG  \n" +
		        " where pk_seq = '"+ this.id +"' \n" ;
		System.out.println(query);
		ResultSet rs =  db.get(query );
		System.out.println(query );
		try
        {
            if (rs.next())
            {
	            this.id = rs.getString("pk_seq");
				this.ngaychungtu =  rs.getString("NGAYBUTRU");
				this.sochungtu = rs.getString("SOCHUNGTU");	
				this.KHChuyenNoId = rs.getString("KH_CHUYENNO");	
				this.KHNhanNoId = rs.getString("KH_NHANNO");
				this.TienteId = rs.getString("TIENTE_FK");
				this.tongchuyenno = rs.getString("TONGTIEN");
				this.tongnhanno = rs.getString("TONGTIEN");
				this.dienGiaiCT = rs.getString("DIENGIAI");
							
				this.isNPPChuyenNo = rs.getString("isNPP");
				this.isNPPNhanNo =  rs.getString("isNPPNhanNo");
				
				this.tenKHChuyenNo = rs.getString("tenKHChuyenNo");
				this.tenKHNhanNo =  rs.getString("tenKHNhanNo");
            }						
       	}
        catch(Exception e){
        	e.printStackTrace();
        }
        finally{try {
			if(rs != null)
				rs.close();
		} catch (Exception e2) {
			e2.printStackTrace();
		}}
       
        createRS();
	}
	
	public void createRS() 
	{		        
		String query = "";
		
//		Utility util = new Utility();
		this.Tientelist = db.get("select pk_seq, ma + ', ' + ten as TEN from ERP_TIENTE ");
		
		query =
				" select PK_SEQ, ISNULL(MA, '') AS MA, isnull(Ten,'') nppTen from NHAPHANPHOI where trangthai = '1' and PK_sEQ != 1   \n";
				
		System.out.println(query);
		
		this.KHChuyenNolist = db.get(query);
		
		//PHAN QUYEN
				
		if(this.KHChuyenNoId.trim().length()>0)
		{
			query = " select  PK_SEQ, ISNULL(MA, '') AS MA,isnull(Ten,'') nppTen from NHAPHANPHOI where trangthai = '1' and PK_SEQ NOT IN ("+KHChuyenNoId+") and PK_sEQ !=1 \n";
		}
		else
			query =  " select  PK_SEQ, ISNULL(MA, '') AS MA,isnull(Ten,'') nppTen from NHAPHANPHOI where trangthai = '1'  and PK_sEQ !=1 \n";
		
		this.KHNhanNolist = db.get(query);
		
		System.out.println(query);
			   	
		String sql ="";
		if(this.KHChuyenNoId.trim().length() > 0){
			if(this.id.length()<=0){
				this.tongchuyenno = "0";
				this.tongnhanno = "0";
			}
			
			if(this.id.length() > 0)
			{
				// HÓA ĐƠN TÀI CHÍNH
				sql = 	
					"SELECT distinct 0 AS LOAIHD, ISNULL( HD.TYGIA,1) AS TIGIA  ,HD.PK_SEQ, '' AS MAHOPDONG, \n" +
					"		HD.KYHIEU AS KYHIEU, HD.SOHOADON SOHOADON, \n " + 
					"		HD.NGAYXUATHD AS NGAYHOADON, \n " +
					" 		ISNULL(HD.TONGTIENAVAT, 0) AS SOTIENGOC, \n " +
		   			"		CAST( (ISNULL(HD.TYGIA, 1)*(ISNULL(HD.TONGTIENAVAT,0) - ISNULL(DATHU.TONGTHU, '0'))) as numeric(18,0)) AS SOTIENVND, \n" +
		   			"		ISNULL(HD.TONGTIENAVAT, 0) - ISNULL(DATHU.TONGTHU, '0') AS SOTIENNT, \n" +
		   			"		BUTRU.XOANO AS SOTIENTT, ISNULL(HD.TIENTE_FK,100000) AS TTID," +
		   			"   	'' as NGAYDENHANTT, BUTRU.HOADON_FK CHON \n" +
		   			
		   			" FROM   ERP_HOADON HD \n"+
					"       INNER JOIN NHAPHANPHOI KH ON HD.NPP_FK = KH.PK_SEQ \n";
					sql+=
		   			"       LEFT JOIN ERP_THUTIEN_HOADON TT_HD ON TT_HD.HOADON_FK = HD.PK_SEQ and TT_HD.LOAIHOADON= 0   \n" +
					"       LEFT JOIN	\n" +
					"  		( 	\n" +
					"		 SELECT DATHU.HOADON_FK,SUM( DATHU.TONGTHU) TONGTHU \n"+
					"		 FROM ("+
					// THU TIỀN HÓA ĐƠN
					"			SELECT 	TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS TONGTHU \n" + 		
					"			FROM 	ERP_THUTIEN_HOADON TTHD \n" +
					"					INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n" + 		
					"			WHERE TT.TRANGTHAI != '2' AND TTHD.LOAIHOADON = 0 AND TT.BANGKE_FK IS NULL \n" + 			
					"			GROUP BY HOADON_FK \n" +
					
					"			UNION ALL \n" +
					
					// BẢNG KÊ THU TIỀN
					"			SELECT 	BKHD.HOADON_FK, SUM(BKHD.THANHTOAN) AS TONGTHU \n" + 		
					"			FROM 	ERP_BANGKETHUTIEN BK \n" +
					"					INNER JOIN ERP_BANGKETHUTIEN_HOADON BKHD ON BKHD.BANGKE_FK = BK.PK_SEQ \n" + 		
					"			WHERE   BK.TRANGTHAI != '2' \n" + 			
					"			GROUP BY HOADON_FK \n" +
					
					"			UNION ALL \n" +
					
					//XÓA KHÁCH HÀNG TRẢ TRƯỚC
					"			SELECT 	TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n" +   		
					"			FROM 	ERP_XOAKHTRATRUOC_HOADON TTHD \n" +
					"				 	INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \n" +  		
					"			WHERE 	TT.TRANGTHAI NOT IN (2) AND ISNULL(TT.LOAIXOATRATRUOC,0) = '0' \n"+
					"					AND TTHD.LOAIHD = '0'  \n" +		
					"			GROUP BY HOADON_FK \n" +
					
					"			UNION ALL \n"+
					
					//BÙ TRỪ KHÁCH HÀNG
					"			SELECT BT_KH.HOADON_FK, SUM(BT_KH.XOANO) AS SOTIENBUTRU  \n"+
					"			FROM   ERP_BUTRUKHACHHANG BT INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BT_KH ON BT.PK_SEQ =BT_KH.BTKH_FK \n " +
					"			WHERE  BT.TRANGTHAI NOT IN (2) AND BT_KH.LOAIHD = 0 AND BT.PK_SEQ NOT IN ("+this.id+") \n"+
					"			GROUP BY BT_KH.HOADON_FK \n"+					
					"		) DATHU \n"+
					"		GROUP BY DATHU.HOADON_FK \n "+
					"  )DATHU ON HD.PK_SEQ = DATHU.HOADON_FK \n" +
					" 		INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BUTRU ON HD.PK_SEQ = BUTRU.HOADON_FK \n"+
					
					"WHERE  ISNULL(HD.TIENTE_FK,100000) = " + this.TienteId + " " +
					"       AND BUTRU.BTKH_FK= '"+this.id+"' AND BUTRU.LOAIHD = 0 \n" ;
					
// HÓA ĐƠN HÀNG TRẢ VỀ
					
			sql += " UNION ALL \n";			
			
			sql +=  " select '7' AS LOAIHD, 1 AS TYGIA, b.pk_seq , '' MAHOPDONG, b.KYHIEU as kyhieu, b.sohoadon SOHOADON, b.ngayxuathd as ngayhoadon, b.tongtienAVAT*(-1) SOTIENGOC,  \n"+
		       		" (b.tongtienAVAT - isnull(thanh_toan.tongthanhtoan, '0'))*(-1) as sotienAVAT, \n"+       
		       		" ((b.tongtienAVAT - isnull(thanh_toan.tongthanhtoan, '0'))* isnull(b.TYGIA,1))*(-1) as sotienAVATVND, \n"+       
		       		" a.XOANO*(-1) as DaThanhToan,  100000 AS TTID, '' NGAYDENHANTT, a.hoadon_fk CHON \n"+
				 	
				 	"from ERP_BUTRUKHACHHANG_CHITIET a \n" +
				 	"     left join ERP_HOADON b on a.hoadon_fk = b.pk_seq \n" +
				 	"     left join	\n" +
				 	"	  ( 	\n"+
				 	"		 SELECT THU.HOADON_FK, SUM(THU.DATHANHTOAN) tongthanhtoan \n"+
				 	"		 FROM (\n"+
					// XÓA KHÁCH HÀNG TRẢ TRƯỚC
					"			select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN    \n" +
					"			from ERP_XOAKHTRATRUOC_HOADON TTHD \n" +
					"			inner join ERP_XOAKHTRATRUOC TT on TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ  \n" +
					"			where TT.TRANGTHAI != 2 AND TTHD.LOAIHD = '7' and TT.KHACHHANG_FK = "+this.KHChuyenNoId+"  "+					
					"			group by HOADON_FK  \n" +
						
					"			union all   \n" +
					// THU TIỀN HÓA ĐƠN
					"			select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN   \n" +
					"			from ERP_THUTIEN_HOADON TTHD " +
					"			inner join ERP_THUTIEN TT on TTHD.THUTIEN_FK = TT.PK_SEQ  \n" +
					"			where  TT.TRANGTHAI != 2 AND TTHD.LOAIHOADON = '7'  AND TT.BANGKE_FK IS NULL \n" +
					"			group by HOADON_FK  \n" +
					
					// THU TIỀN BẢNG KÊ
					"       	UNION ALL \n"+
					
					"			SELECT TTHD.HOADON_FK , SUM(TTHD.SOTIENTT) as DATHANHTOAN   \n" +
					"			FROM ERP_THUTIEN_HOADON TTHD " +
					"			INNER JOIN ERP_THUTIEN TT on TTHD.THUTIEN_FK = TT.PK_SEQ  \n" +
					"			where  TT.TRANGTHAI != 2 AND TTHD.LOAIHOADON = '7'  AND TT.BANGKE_FK IS NOT NULL \n" +
					"			group by HOADON_FK  \n" +
					
					// BÙ TRỪ KHÁCH HÀNG
					"			UNION ALL \n"+		
					
					"			SELECT TTHD.HOADON_FK , SUM(TTHD.XOANO) as DATHANHTOAN \n"+   
					"			FROM ERP_BUTRUKHACHHANG_CHITIET TTHD inner join ERP_BUTRUKHACHHANG TT on TTHD.BTKH_FK = TT.PK_SEQ \n"+  
					"			WHERE  TT.TRANGTHAI != 2 AND TTHD.LOAIHD = '7'  AND TT.PK_SEQ NOT IN ("+this.id+") \n"+ 
					
					"			GROUP BY HOADON_FK \n"+
					
					"  			UNION ALL \n"+
					
					// THANH TOÁN HÓA ĐƠN
					"			SELECT TT_HD.HOADON_FK, SUM(TT_HD.THANHTOAN) AS THANHTOAN  \n"+
					"			FROM   ERP_THANHTOANHOADON TT INNER JOIN ERP_THANHTOANHOADON_HOADON_PHANBO TT_HD ON TT.PK_SEQ = TT_HD.TTHD_FK \n "+
					"			WHERE  TT.TRANGTHAI NOT IN (2) AND TT_HD.LOAIHD = 8 AND TT_HD.KHACHHANG_FK = "+this.KHChuyenNoId+" \n"+
					"			GROUP BY TT_HD.HOADON_FK \n"+
					
					"			) THU \n"+
					"		 GROUP BY THU.HOADON_FK \n"+
					"	  )thanh_toan on a.hoadon_fk = thanh_toan.hoadon_fk \n" +
					
					"where a.BTKH_FK = '" + this.id + "' and a.LOAIHD= '7' and b.LOAIHOADON = 2 \n";
					
						
				
				
				// HOA DON KHÁC
				if(this.TienteId.equals("100000"))
				{
					sql += 
					 " UNION ALL \n"+
			
					 "SELECT distinct TT_HD.LOAIHOADON AS LOAIHD, '1' AS TIGIA  ,HDPL.PK_SEQ, '' AS MAHOPDONG, HDPL.kyhieuhoadon AS KYHIEU, HDPL.SOHOADON, \n"+
					 "		 HDPL.ngayhoadon , \n"+
					 "  	 ISNULL(PLSP.SOTIEN, 0)  AS SOTIENGOC, \n " +
					 "		 ISNULL(PLSP.SOTIEN, 0) - ISNULL(DATHU.TONGTHU, '0') AS SOTIENVND, \n"+
					 "		 0 AS SOTIENNT, \n"+
					 "		 BUTRU.XOANO AS SOTIENTT , '100000' AS TTID," +
					 "   	 '' as NGAYDENHANTT, BUTRU.HOADON_FK CHON \n"+
					 
					 " FROM ERP_HoaDonPheLieu HDPL ";
					 if(this.isNPPChuyenNo.equals("0"))
						 sql+= " INNER JOIN KHACHHANG KH ON HDPL.KHACHHANG_FK = KH.PK_SEQ \n";
					 else if(this.isNPPChuyenNo.equals("1"))
						 sql+= " INNER JOIN NHAPHANPHOI KH ON HDPL.KHACHHANG_FK = KH.PK_SEQ \n";
					 else if(this.isNPPChuyenNo.equals("1"))
						 sql+= " INNER JOIN ERP_NHANVIEN KH ON HDPL.KHACHHANG_FK = KH.PK_SEQ \n";
					 
					 sql+=
					 " 		LEFT JOIN ERP_THUTIEN_HOADON TT_HD ON TT_HD.HOADON_FK = HDPL.PK_SEQ and TT_HD.LOAIHOADON = 1  \n"+
					 " 		INNER JOIN (SELECT hoadonphelieu_fk, SUM(thanhtien)AS SOTIEN \n"+
					 "			 		FROM erp_hoadonphelieu_sanpham \n"+
					 "			 		GROUP BY hoadonphelieu_fk )PLSP ON HDPL.pk_seq= PLSP.hoadonphelieu_fk \n"+
					 " 		LEFT JOIN	\n"+
					 " 		( 	\n"+
					 "			SELECT DATHU.HOADON_FK,SUM( DATHU.TONGTHU) TONGTHU \n "+
					 "			FROM ( \n"+
					 // THU TIỀN
					 "				SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS TONGTHU \n"+
					 "				FROM ERP_THUTIEN_HOADON TTHD \n"+
					 "				INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n"+
					 "				WHERE TT.TRANGTHAI != '2' AND TT.BANGKE_FK IS NULL AND TTHD.LOAIHOADON = 1 \n"+
					 "				GROUP BY HOADON_FK \n"+
					 
					 "				UNION ALL \n" +
					 //XÓA NỢ KHÁCH HÀNG
					 "				SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n" +   		
					 "				FROM ERP_XOAKHTRATRUOC_HOADON TTHD \n" +
					 "					 INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \n" +  		
					 "				WHERE TT.TRANGTHAI NOT IN (2) AND ISNULL(TT.LOAIXOATRATRUOC,0) = '0' " +
					 "				AND TTHD.LOAIHD = '1' \n"+		
					 "				GROUP BY HOADON_FK \n" +
					
					 "				UNION ALL \n"+
					 //BÙ TRỪ KHÁCH HÀNG
					 "				SELECT BT_KH.HOADON_FK, SUM(BT_KH.XOANO) AS SOTIENBUTRU  \n"+
					 "				FROM   ERP_BUTRUKHACHHANG BT INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BT_KH ON BT.PK_SEQ =BT_KH.BTKH_FK \n " +
					 "				WHERE  BT.TRANGTHAI NOT IN (2) AND BT_KH.LOAIHD = 1 AND BT.PK_SEQ NOT IN( "+this.id +" ) \n"+
					 "				GROUP BY BT_KH.HOADON_FK \n"+
					 
					 "			) DATHU \n"+
					 "		GROUP BY DATHU.HOADON_FK \n	"+
					 " )DATHU ON HDPL.PK_SEQ = DATHU.HOADON_FK \n"+
					 " 		INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BUTRU ON HDPL.PK_SEQ = BUTRU.HOADON_FK \n"+
					 
					 " WHERE (100000) = " + this.TienteId + " " +
					 "       AND BUTRU.BTKH_FK= '"+this.id+"' AND BUTRU.LOAIHD = 1 \n" ;
				}
								
				// KHACHHANGTRATRUOC
				sql += 	
					" UNION ALL \n" +
					
					" SELECT  distinct '3' as LOAIHD, tt.TIGIA, tt.PK_SEQ, '' AS MAHOPDONG, '' AS KYHIEU, CAST(tt.PK_SEQ as nvarchar(50)) AS SOHOADON, tt.NGAYCHUNGTU AS NGAYHOADON, \n" +
				 	"        CASE WHEN tt.TIENTE_FK = '100000' THEN tt.THUDUOC*(-1) ELSE tt.THUDUOCNT*(-1) END as SOTIENGOC, \n" +
				 	"        (tt.THUDUOC - ISNULL(XKH.TIENTHANHTOAN,0) - ISNULL(DATHU.DATHANHTOAN,0) )*(-1)  AS SOTIENVND, \n"+
				 	"        (tt.THUDUOC - ISNULL(XKH.TIENTHANHTOAN,0) - ISNULL(DATHU.DATHANHTOAN,0) )*(-1) AS SOTIENNT, BUTRU.XOANO*(-1) AS SOTIENTT , tt.TIENTE_FK AS TTID, \n"+
				 	"        '' as NGAYDENHANTT, BUTRU.HOADON_FK CHON \n" +
				 	
				 	" FROM 	ERP_THUTIEN tt "+
					"     INNER JOIN NHAPHANPHOI KH ON TT.KHACHHANG_FK = KH.PK_SEQ \n";
				
				sql+=
				 	"     LEFT JOIN  \n" +
				 	"	 ( SELECT XKH.CTTT_FK, SUM (XKH.TIENTHANHTOAN) TIENTHANHTOAN  \n" +
				 	"	   FROM ERP_XOAKHTRATRUOC_CTTT XKH INNER JOIN ERP_XOAKHTRATRUOC TT ON XKH.xoakhtratruoc_fk = TT.PK_SEQ  \n" +
				 	" 	   WHERE TT.TRANGTHAI NOT IN (2) AND XKH.LOAICT = 0 \n" +
				 	"	  GROUP BY XKH.CTTT_FK ) XKH \n" +
				 	"	  ON TT.PK_SEQ = XKH.cttt_fk \n"+
				 	"	  LEFT JOIN \n" +
				 	"	  (" +
				 	"		SELECT DATHU.HOADON_FK,SUM( DATHU.DATHANHTOAN) DATHANHTOAN \n "+
					"		FROM ( \n" +
		 			"				SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n" +   		
					"				FROM ERP_XOAKHTRATRUOC_HOADON TTHD \n" +
					"				INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \n" +  		
					"				WHERE TT.TRANGTHAI NOT IN (2) AND ISNULL(TT.LOAIXOATRATRUOC,0) = '0' \n"+
					"				AND TTHD.LOAIHD = '3'  \n" +		
					"				GROUP BY HOADON_FK \n" +
				
					"				UNION ALL \n"+
				
					"				SELECT BT_KH.HOADON_FK, SUM(BT_KH.XOANO) AS SOTIENBUTRU  \n"+
					"				FROM   ERP_BUTRUKHACHHANG BT INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BT_KH ON BT.PK_SEQ =BT_KH.BTKH_FK \n " +
					"				WHERE  BT.TRANGTHAI NOT IN (2) AND BT_KH.LOAIHD = 3 AND BT.PK_SEQ NOT IN ( "+this.id +" ) \n"+
					"				GROUP BY BT_KH.HOADON_FK \n"+
					"			) DATHU \n"+
					"		GROUP BY DATHU.HOADON_FK \n	"+
					"	   ) DATHU ON tt.PK_SEQ = DATHU.HOADON_FK  \n"+
				 	" 	   INNER JOIN  ERP_BUTRUKHACHHANG_CHITIET BUTRU ON tt.PK_SEQ = BUTRU.HOADON_FK AND BUTRU.LOAIHD = 3 \n"+
					
				 	" WHERE tt.NOIDUNGTT_FK = '100001' AND tt.TRANGTHAI = '1' AND tt.TIENTE_FK = '"+ this.TienteId +"' \n" +
				 	"      AND tt.KHACHHANG_FK = "+ this.KHChuyenNoId +" \n"+
				 	"      AND BUTRU.BTKH_FK= '"+this.id+"' \n" ;
					
				sql+=" UNION ALL \n" ;	
				
			}
				
			
			// HOADONTAICHINH
			sql += 		" (SELECT distinct '0' AS LOAIHD, ISNULL(HOADON.TYGIA, 1) as TIGIA , HOADON.PK_SEQ, HOADON.MAHOPDONG AS MAHOPDONG, \n" +
						"        HOADON.KYHIEU, HOADON.SOHOADON, HOADON.NGAYHOADON, \n" +	
						"        HOADON.TONGTIENAVAT AS SOTIENGOC, \n" +
						"        CAST((ISNULL(HOADON.TYGIA, 1)*(ISNULL(HOADON.TONGTIENAVAT, 0) - ISNULL(DATHANHTOAN.DATHANHTOAN, '0'))) AS numeric(18,0) ) AS SOTIENVND, \n" +
						"        (HOADON.TONGTIENAVAT - ISNULL(DATHANHTOAN.DATHANHTOAN, '0')) AS SOTIENNT, \n" +
						"        0 AS SOTIENTT, HOADON.TTID, \n" +	
						"        HOADON.NGAYDENHANTT, 0 CHON \n" +
						
						" FROM ( \n" +		
						"		SELECT 	HD.PK_SEQ, '' AS MAHOPDONG, HD.KYHIEU, HD.SOHOADON SOHOADON, HD.NGAYXUATHD AS NGAYHOADON, \n" + 
						"				HD.TONGTIENAVAT, ISNULL(HD.TYGIA,1) AS TYGIA, ISNULL(HD.TIENTE_FK,100000) AS TTID," +
						"   			'' as NGAYDENHANTT \n" +		
						"		FROM 	 ERP_HOADON HD 	\n" +
						"				INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = HD.NPP_FK \n"+	
						"		WHERE 	 HD.TRANGTHAI in (2,4)  \n";						
				sql +=  " 		AND HD.NPP_FK = "+ this.KHChuyenNoId +"";
						
					
			sql += 		"				AND HD.PK_SEQ NOT IN (SELECT HOADON_FK FROM ERP_BUTRUKHACHHANG_CHITIET WHERE BTKH_FK = '" + (this.id ==""?"0":this.id )+ "') \n";
						
			sql += 		"	  ) HOADON \n" + 
						"	  LEFT JOIN ( \n" +	
						"	  SELECT HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN \n" +  	
						"	  FROM  \n" +	
						"		  ( 	\n" +	
						"			SELECT DATHU.HOADON_FK,SUM( DATHU.DATHANHTOAN) DATHANHTOAN \n "+
						"			FROM ( \n" +						
						//XÓA KHÁCH HÀNG TRẢ TRƯỚC
						"				 SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n" +   		
						"				 FROM ERP_XOAKHTRATRUOC_HOADON TTHD \n" +
						"				 INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \n" +  		
						"				 WHERE TT.TRANGTHAI NOT IN (2) AND ISNULL(TT.LOAIXOATRATRUOC,0) = '0' \n"+		
						"				 GROUP BY HOADON_FK \n" +		
		
						"				 UNION ALL \n" +
						
						// THU TIỀN						
						"				 SELECT TTHD.HOADON_FK , SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n" +   		
						"				 FROM ERP_THUTIEN_HOADON TTHD \n" +
						"				 INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n" +   
						"				 WHERE TTHD.LOAIHOADON = '0' AND TT.BANGKE_FK IS NULL AND TT.TRANGTHAI NOT IN (2)  \n"+
						"				 GROUP BY HOADON_FK \n"+	
		
						"				 UNION ALL \n"+
						
						// BÙ TRỪ KHÁCH HÀNG
						"				 SELECT BT_KH.HOADON_FK, SUM(BT_KH.XOANO) AS SOTIENBUTRU  \n"+
						"				 FROM   ERP_BUTRUKHACHHANG BT INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BT_KH ON BT.PK_SEQ =BT_KH.BTKH_FK \n " +
						"				 WHERE  BT.TRANGTHAI NOT IN (2) AND BT_KH.LOAIHD = 0 \n"+		
						" 				 AND BT.PK_SEQ NOT IN ( "+(this.id ==""?"0":this.id ) +" ) "+
						"				 GROUP BY BT_KH.HOADON_FK \n"+	
				
						"				 UNION ALL \n" +
				
						// BẢNG KÊ THU TIỀN
						"				 SELECT BKHD.HOADON_FK, SUM(BKHD.THANHTOAN) AS TONGTHU \n" + 		
						"				 FROM 	ERP_BANGKETHUTIEN BK \n" +
						"				 INNER JOIN ERP_BANGKETHUTIEN_HOADON BKHD ON BKHD.BANGKE_FK = BK.PK_SEQ \n" + 		
						"				 WHERE   BK.TRANGTHAI != '2'  \n" + 			
						"				 GROUP BY HOADON_FK \n" +
						"				) DATHU \n"+
						"			GROUP BY DATHU.HOADON_FK \n	"+
						"		 ) HOADONDATT  \n" +
						"	GROUP BY HOADON_FK " +   
						")DATHANHTOAN ON HOADON.PK_SEQ = DATHANHTOAN.HOADON_FK \n" +
						
						" WHERE HOADON.TONGTIENAVAT - ISNULL(DATHANHTOAN.DATHANHTOAN, '0') > 0 AND HOADON.TTID = " + this.TienteId + " \n" +
						
						" ) \n";
		
				// HÓA ĐƠN KHÁC
				if(this.TienteId.equals("100000")) 
				{
					sql += 	
						" UNION ALL \n" +
					
						" (SELECT distinct '1' AS LOAIHD , HDPHELIEU.TYGIA AS TIGIA,HDPHELIEU.PK_SEQ ,HDPHELIEU.MAHOPDONG, HDPHELIEU.KYHIEU, \n" +
						"  		  HDPHELIEU.SOHOADON, HDPHELIEU.NGAYHOADON, \n" +
						"   	  ISNULL(HDPHELIEU.SOTIENVND,0) AS SOTIENGOC, \n " +
						"   	  ISNULL(HDPHELIEU.SOTIENVND,0) - ISNULL(DATHANHTOAN.DATHANHTOAN,0) AS SOTIENVND, \n" +
						"  		  0 AS SOTIENNT, 0 AS SOTIENTT, '100000' AS TTID, HDPHELIEU.NGAYDENHANTT, 0 CHON \n"+
						
						"  FROM \n" +
						" 	( \n" +
						" 		SELECT '1' AS TYGIA,PL.PK_SEQ,'' AS MAHOPDONG, PL.KYHIEUHOADON AS KYHIEU, PL.SOHOADON, PL.NGAYHOADON, CAST( (PLSP.SOTIENVND + PLSP.SOTIENVND*PL.VAT/100) AS NUMERIC(18,0) ) AS SOTIENVND, \n" +
						"        		0 AS SOTIENNT,0 AS DATHANHTOAN ,'100000' AS TTID , '' as NGAYDENHANTT \n" +
						" 		FROM ERP_HOADONPHELIEU PL ";
			
						if(this.isNPPChuyenNo.equals("0"))
							sql+= "    INNER JOIN KHACHHANG KH ON PL.KHACHHANG_FK = KH.PK_SEQ \n";
						else if(this.isNPPChuyenNo.equals("1"))
							sql+= "    INNER JOIN NHAPHANPHOI KH ON PL.KHACHHANG_FK = KH.PK_SEQ \n";									
						else if(this.isNPPChuyenNo.equals("1"))
							sql+= "    INNER JOIN ERP_NHANVIEN KH ON PL.KHACHHANG_FK = KH.PK_SEQ \n";
						
				sql+=	"    		INNER JOIN \n" +
						"    		(	SELECT HOADONPHELIEU_FK, SUM(thanhtien) AS SOTIENVND \n" +
						"     			FROM ERP_HOADONPHELIEU_SANPHAM \n" +
						"     			GROUP BY HOADONPHELIEU_FK) AS PLSP  ON PL.PK_SEQ= PLSP.HOADONPHELIEU_FK \n" +
						" 			WHERE PL.KHACHHANG_FK = "+ this.KHChuyenNoId +" and PL.TRANGTHAI = '1' \n"+
				// KHÔNG LẤY NHỮNG HÓA ĐƠN ĐÃ TICK CHỌN XÓA CHÊNH LỆCH
						"				AND PL.PK_SEQ NOT IN (SELECT HOADON_FK FROM ERP_BUTRUKHACHHANG_CHITIET WHERE BTKH_FK = '" + (this.id ==""?"0":this.id )+ "') \n";
													
				sql +=	"   )HDPHELIEU \n "+
						" 	LEFT JOIN \n"+
						"	( \n" +	
						"		SELECT HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN \n" +  	
						"		FROM  \n" +	
						"		( 	\n" +	
						// XÓA NỢ KHÁCH HÀNG
						"			SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n" +   		
						"			FROM ERP_XOAKHTRATRUOC_HOADON TTHD \n" +
						"			INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \n" +  		
						"			WHERE TT.TRANGTHAI NOT IN (2) AND ISNULL(TT.LOAIXOATRATRUOC,0) = '0' " +
						"			AND TTHD.LOAIHD = '1' \n"+	
						"			GROUP BY HOADON_FK \n" +
						
						"      		UNION ALL  \n"+
						
						// THU TIỀN
						"			SELECT TTHD.HOADON_FK , SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n" +   		
						"			FROM ERP_THUTIEN_HOADON TTHD \n" +
						"			INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n" +   		
						"			WHERE TTHD.LOAIHOADON = '1' AND TT.TRANGTHAI NOT IN (2) \n"	; 			
				sql +=	" 			GROUP BY HOADON_FK \n" +
						
						// BÙ TRỪ KHÁCH HÀNG
						"       	UNION ALL                      \n"+
						
						"			SELECT BT_KH.HOADON_FK, SUM(BT_KH.XOANO) AS SOTIENBUTRU  \n"+
						"			FROM   ERP_BUTRUKHACHHANG BT INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BT_KH ON BT.PK_SEQ =BT_KH.BTKH_FK \n " +
						"			WHERE  BT.TRANGTHAI NOT IN (2) AND BT_KH.LOAIHD = 1 \n" ;
				sql +=  " 			AND BT.PK_SEQ NOT IN ( "+(this.id ==""?"0":this.id ) +" ) ";						
				sql +=  "			GROUP BY BT_KH.HOADON_FK \n"+				
						"		) HOADONDATT  \n" +
						"		GROUP BY HOADON_FK " +   
						"	)DATHANHTOAN ON HDPHELIEU.PK_SEQ = DATHANHTOAN.HOADON_FK \n" +
						
						" WHERE ISNULL(HDPHELIEU.SOTIENVND,0) - ISNULL(DATHANHTOAN.DATHANHTOAN, '0') != 0  \n" +
						")" ;
				}
		
			// LOAD PHIEU THU TIEN: KHACH HANG TRA TRUOC
				sql += 	
						" UNION ALL \n" +
			
						" SELECT distinct '3' as LOAIHD, tt.TIGIA, tt.PK_SEQ, '' AS MAHOPDONG, '' AS KYHIEU, CAST(tt.PK_SEQ as nvarchar(50)) AS SOHOADON, tt.NGAYCHUNGTU AS NGAYHOADON, \n" +
					 	"        CASE WHEN tt.TIENTE_FK = '100000' THEN tt.THUDUOC*(-1) ELSE tt.THUDUOCNT*(-1) END as SOTIENGOC, \n" +
					 	"        (TT.THUDUOC - ISNULL(DAXOANO.SOTIENVND,0))*(-1) AS SOTIENVND, \n" +
					 	"        (TT.THUDUOCNT - ISNULL(DAXOANO.SOTIENNT,0))*(-1) AS SOTIENNT, 0 AS SOTIENTT, tt.TIENTE_FK AS TTID, \n" +
					 	"        '' as NGAYDENHANTT, 0 CHON  \n" +
					 	" FROM ERP_THUTIEN tt \n";
				  sql +="    INNER JOIN NHAPHANPHOI KH ON TT.KHACHHANG_FK = KH.PK_SEQ \n";
						
						sql+=
					 	"    LEFT JOIN \n" +
					 	"     ( \n"+
					 	"      SELECT CTTT_FK, SUM(SOTIENNT) SOTIENNT, SUM (SOTIENVND) SOTIENVND \n"+
					 	"      FROM ( \n"+
					 	// XÓA KHÁCH HÀNG
					 	"		SELECT CT.CTTT_FK, SUM(CT.TIENTHANHTOAN) AS SOTIENNT ,SUM(CT.TIENTHANHTOAN*ISNULL(CT.TIGIA,0)) AS SOTIENVND  \n" +
					 	"       FROM ERP_XOAKHTRATRUOC_CTTT CT INNER JOIN ERP_XOAKHTRATRUOC XKH ON CT.XOAKHTRATRUOC_FK = XKH.PK_SEQ \n" +
					 	"       WHERE XKH.TRANGTHAI != 2 AND XKH.LOAIXOATRATRUOC = '0' \n"+
					 	"       GROUP BY CT.CTTT_FK \n" +
					 	
					 	"		UNION ALL \n"+
					 	
						// BÙ TRỪ KHÁCH HÀNG
						"		SELECT BT_KH.HOADON_FK, SUM(BT_KH.XOANO) AS SOTIENNT, SUM(BT_KH.XOANO*BT_KH.TIGIA) AS SOTIENVND \n"+
						"		FROM   ERP_BUTRUKHACHHANG BT INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BT_KH ON BT.PK_SEQ =BT_KH.BTKH_FK \n " +
						"		WHERE  BT.TRANGTHAI NOT IN (2) AND BT_KH.LOAIHD = 3 ";
						if(this.id.trim().length()>0){
							sql +=" AND BT.PK_SEQ NOT IN ( "+this.id +" ) ";
						}						
						sql += "		GROUP BY BT_KH.HOADON_FK \n"+	
						
						"     ) A GROUP BY CTTT_FK  \n"+
					 	"      ) DAXOANO ON tt.PK_SEQ = DAXOANO.CTTT_FK \n" +
								
					 	"WHERE tt.NOIDUNGTT_FK = '100001' AND tt.TRANGTHAI = '1' AND tt.TIENTE_FK = '"+ this.TienteId +"' \n" +
					 	"      and tt.KHACHHANG_FK = "+ this.KHChuyenNoId +" "+
					 	"      AND tt.PK_SEQ NOT IN (SELECT HOADON_FK FROM ERP_BUTRUKHACHHANG_CHITIET WHERE LOAIHD = '3' AND BTKH_FK =( "+(this.id ==""?"0":this.id ) +" ) ) ";
		
						//HÓA ĐƠN TRẢ HÀNG VỀ
					
						sql += " UNION ALL \n"+					
						
						"( \n" +
						"	select '7' as LOAIHD, hoadon.TYGIA TIGIA, hoadon.pk_seq, '' AS MAHOPDONG, hoadon.kyhieu, hoadon.sohoadon  , hoadon.ngayhoadon,  \n" +
						"	hoadon.sotienAVAT*(-1) SOTIENGOC, (hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0'))*(-1) as SOTIENVND, \n"+
						"   ((hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0'))* isnull(hoadon.TYGIA,0))*(-1) as SOTIENNT, " +
						"   0 AS SOTIENTT, 100000 AS TTID, '' NGAYDENHANTT, 0 CHON \n" +
						
						"	FROM ( \n" +
						
						"		select pk_seq, KYHIEU as kyhieu, sohoadon, ngayxuathd as ngayhoadon, tongtienAVAT as sotienAVAT, ISNULL(TYGIA,1) as TYGIA \n" +
						"		from ERP_HOADON \n" +
						"		where 1 = 1 and trangthai in (1) AND isnull(TIENTE_FK,100000) = " + this.TienteId + " AND LOAIHOADON = 2 and CONGTY_FK = "+this.congtyId+										
						"		and khachhang_fk = '" + this.KHChuyenNoId + "'  "+
						"		AND PK_SEQ NOT IN (SELECT HOADON_FK FROM ERP_BUTRUKHACHHANG_CHITIET WHERE BTKH_FK = '" + (this.id ==""?"0":this.id )+ "') \n"+
						" 		AND pk_seq not in (select hoadon_fk from ERP_XoakhTraTruoc_HoaDon where xoakhtratruoc_fk = '" + (this.id == "" ? "0": this.id) + "') AND TONGTIENAVAT > 0 " +
				
						"	)hoadon \n" +
						"	left join ( \n" +
						" 		select HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) as DATHANHTOAN  \n" +
						"		from  \n" +
						"		(  \n" +
						// XÓA KHÁCH HÀNG TRẢ TRƯỚC
						"			select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN    \n" +
						"			from ERP_XOAKHTRATRUOC_HOADON TTHD \n" +
						"			inner join ERP_XOAKHTRATRUOC TT on TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ  \n" +
						"			where TT.TRANGTHAI != 2 AND TTHD.LOAIHD = '7' and TT.KHACHHANG_FK = "+this.KHChuyenNoId+"  "+						
						"			group by HOADON_FK  \n" +
							
						"			union all   \n" +
						// THU TIỀN HÓA ĐƠN
						"			select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN   \n" +
						"			from ERP_THUTIEN_HOADON TTHD " +
						"			inner join ERP_THUTIEN TT on TTHD.THUTIEN_FK = TT.PK_SEQ  \n" +
						"			where  TT.TRANGTHAI != 2 AND TTHD.LOAIHOADON = '7'  AND TT.BANGKE_FK IS NULL \n" +
						"			group by HOADON_FK  \n" +
						
						// THU TIỀN BẢNG KÊ
						"       	UNION ALL \n"+
						
						"			SELECT TTHD.HOADON_FK , SUM(TTHD.SOTIENTT) as DATHANHTOAN   \n" +
						"			FROM ERP_THUTIEN_HOADON TTHD " +
						"			INNER JOIN ERP_THUTIEN TT on TTHD.THUTIEN_FK = TT.PK_SEQ  \n" +
						"			where  TT.TRANGTHAI != 2 AND TTHD.LOAIHOADON = '7'  AND TT.BANGKE_FK IS NOT NULL \n" +
						"			group by HOADON_FK  \n" +
						
						// BÙ TRỪ KHÁCH HÀNG
						"			UNION ALL \n"+		
						
						"			SELECT TTHD.HOADON_FK , SUM(TTHD.XOANO) as DATHANHTOAN \n"+   
						"			FROM ERP_BUTRUKHACHHANG_CHITIET TTHD inner join ERP_BUTRUKHACHHANG TT on TTHD.BTKH_FK = TT.PK_SEQ \n"+  
						"			WHERE  TT.TRANGTHAI != 2 AND TTHD.LOAIHD = '7'  \n";
						if(this.id.trim().length()>0){
							sql +=" AND TT.PK_SEQ NOT IN ( "+this.id +" ) ";
						}	
						
						sql+=
						"			GROUP BY HOADON_FK \n"+
						
						"  			UNION ALL \n"+
						
						// THANH TOÁN HÓA ĐƠN
						"			SELECT TT_HD.HOADON_FK, SUM(TT_HD.THANHTOAN) AS THANHTOAN  \n"+
						"			FROM   ERP_THANHTOANHOADON TT INNER JOIN ERP_THANHTOANHOADON_HOADON_PHANBO TT_HD ON TT.PK_SEQ = TT_HD.TTHD_FK \n "+
						"			WHERE  TT.TRANGTHAI NOT IN (2) AND TT_HD.LOAIHD = 8 AND TT_HD.KHACHHANG_FK = "+this.KHChuyenNoId+" \n"+
						"			GROUP BY TT_HD.HOADON_FK \n"+
						
						"		)HOADONDATT  group by HOADON_FK  \n" +								
						"	)dathanhtoan on hoadon.pk_seq = dathanhtoan.hoadon_fk \n" +
						
						"where round(hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0'), 0) > 0 \n";
						sql+= 		" ) \n";			
		
						
						sql +=" ORDER BY LOAIHD, NGAYHOADON ASC \n";
				
				System.out.println("ACSC:\n" + sql + "\n-------------------------------------------");
				
				ResultSet rs = db.get(sql);
				try 
			    {
					String hdID = "";
					String hdLOAI = "";
					String hdTIGIA = "";
					String hdNGAYHOADON = "";
					String hdSOHOADON = "";
					String hdSOTIENGOC = "";
					String hdSOTIENPHAIXOA = "";
					String hdXOANO="";
					String hdCONLAI = "";
					String hdCHON = "";
					
					double SOTIENPHAIXOA =0;
					double XOANO = 0;
					double CONLAI = 0;
					
					if(rs!= null)
				    {				    
						while(rs.next())
						{
							hdID += rs.getString("PK_SEQ") + "__";
							hdLOAI += rs.getString("LOAIHD") + "__";
							hdTIGIA += rs.getString("TIGIA") + "__";
							hdSOHOADON += rs.getString("SOHOADON") + "__";
							hdNGAYHOADON += rs.getString("NGAYHOADON") + "__";
							hdSOTIENGOC += rs.getString("SOTIENGOC") + "__";
							hdSOTIENPHAIXOA += rs.getString("SOTIENVND") + "__";
							hdXOANO += rs.getString("SOTIENTT") + "__";	
							
							SOTIENPHAIXOA = rs.getDouble("SOTIENVND");
							XOANO = 	rs.getDouble("SOTIENTT");
							
							CONLAI = SOTIENPHAIXOA - XOANO;
							System.out.println("SOTIENPHAIXOA:"+SOTIENPHAIXOA +" XOA NO: "+XOANO);
							
							hdCONLAI += CONLAI+"__";
							hdCHON += rs.getString("CHON") + "__";
						}
						if(hdID.trim().length() > 0)
						{
							hdID = hdID.substring(0, hdID.length() - 2);
							this.HdId = hdID.split("__");
							
							hdLOAI = hdLOAI.substring(0, hdLOAI.length() - 2);
							this.HdLoai = hdLOAI.split("__");
							
							hdTIGIA = hdTIGIA.substring(0, hdTIGIA.length() - 2);
							this.HdTigia = hdTIGIA.split("__");
							
							hdSOHOADON = hdSOHOADON.substring(0, hdSOHOADON.length() - 2);
							this.HdSohd= hdSOHOADON.split("__");
							
							hdNGAYHOADON = hdNGAYHOADON.substring(0, hdNGAYHOADON.length() - 2);
							this.HdNgayhd= hdNGAYHOADON.split("__");
							
							hdSOTIENGOC = hdSOTIENGOC.substring(0, hdSOTIENGOC.length() - 2);
							this.HdSotiengoc= hdSOTIENGOC.split("__");
							
							hdSOTIENPHAIXOA = hdSOTIENPHAIXOA.substring(0, hdSOTIENPHAIXOA.length() - 2);
							this.HdSotienphaixoa= hdSOTIENPHAIXOA.split("__");
							
							hdXOANO = hdXOANO.substring(0, hdXOANO.length() - 2);
							this.HdSotienxoa= hdXOANO.split("__");
							
							hdCONLAI = hdCONLAI.substring(0, hdCONLAI.length() - 2);
							this.HdSotienconlai = hdCONLAI.split("__");
								
							hdCHON = hdCHON.substring(0, hdCHON.length() - 2);
							this.HdChon= hdCHON.split("__");
						}
				    }
					
			    }
				catch(Exception e){
					e.printStackTrace();
				}
		}
	}
	
	public void DBclose()
	{
		try 
		{
			if(this.khlist != null)
				this.khlist.close();
			if(this.hoadonlist != null)
				this.hoadonlist.close();
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
		
		if (this.db != null)
		{
			this.db.shutDown();
			this.db = null;
		}
	}

	public String[] getHdId()
	{
		return this.HdId;
	}

	public void setHdId(String[] HdId) 
	{
		this.HdId = HdId;
	}

	public String[] getHdSohd() 
	{
		return this.HdSohd;
	}

	public void setHdSohd(String[] HdSohd) 
	{
		this.HdSohd = HdSohd;
	}

	public String[] getHdChon() 
	{
		return this.HdChon;
	}

	public void setHdChon(String[] hdChon) 
	{
		this.HdChon = hdChon;
	}

	public String[] getHdNgayhd() 
	{
		return this.HdNgayhd;
	}

	public void setHdNgayhd(String[] hdNgayhd) 
	{
		this.HdNgayhd = hdNgayhd;
	}

	public String getGhichu() 
	{
		return this.ghichu;
	}

	public void setGhichu(String ghichu)
	{
		this.ghichu = ghichu;
	}

	public String[] getHdIds() 
	{
		return this.HdIds;
	}


	public void setHdIds(String[] hdIds) 
	{
		this.HdIds = hdIds;
	}

	public String getSohoadontu() 
	{
		return this.sohoadontu;
	}

	public void setSohoadontu(String sohoadontu) 
	{
		this.sohoadontu = sohoadontu;
	}

	public String getSohoadonden() 
	{
		return this.sohoadonden;
	}

	public void setSohoadonden(String sohoadonden) 
	{
		this.sohoadonden = sohoadonden;
	}

	public String getSotientu() 
	{
		return this.sotientu ;
	}

	public void setSotientu(String sotientu) 
	{
		this.sotientu = sotientu;
	}

	public String getSotienden() 
	{
		return this.sotienden;
	}

	public void setSotienden(String sotienden) 
	{
		this.sotienden = sotienden;
	}

	public String getDdkdId() {
		return this.DdkdId;
	}
	
	public void setDdkdId(String ddkdId) {
		this.DdkdId=ddkdId;
	}
		
	public String getckKh() {
		return this.ckKh;
	}

	public void setckKh(String ckKh) {
		this.ckKh=ckKh;
	}

	public String[] getKhIds() {
		return this.KhIds;
	}

	public ResultSet getTienteList() {
		return this.Tientelist;
	}

	public void setTienteList(ResultSet tientelist) {
		this.Tientelist = tientelist;
	}

	public String getTienteId() {
		return this.TienteId;
	}
	
	public void setTienteId(String tienteId) {
		this.TienteId = tienteId;
	}

	public ResultSet getKHChuyenNoList() {
		return this.KHChuyenNolist;
	}

	public void setKHChuyenNoList(ResultSet KHChuyenNolist) {
		this.KHChuyenNolist = KHChuyenNolist;
	}

	public String getKHChuyenNoId() {
		return this.KHChuyenNoId;
	}

	public void setKHChuyenNoId(String KHChuyenNoId) {
		this.KHChuyenNoId = KHChuyenNoId;
	}

	public ResultSet getKHNhanNoList() {
		return this.KHNhanNolist;
	}

	public void setKHNhanNoList(ResultSet KHNhanNolist) {
		this.KHNhanNolist = KHNhanNolist;
	}

	public String getKHNhanNoId() {
		return this.KHNhanNoId;
	}
	
	public void setKHNhanNoId(String KHNhanNoId) {
		this.KHNhanNoId = KHNhanNoId;
	}
	
	public void createKHRS() {
	}

	public String getNgaychungtu() {
		return this.ngaychungtu;
	}

	public void setNgaychungtu(String ngaychungtu) {
		this.ngaychungtu = ngaychungtu;
	}

	public String getTongchuyenno() {
		return this.tongchuyenno;
	}

	public void setTongchuyenno(String tongchuyenno) {
		this.tongchuyenno = tongchuyenno;
	}

	public String getTongnhanno() {
		return this.tongnhanno;
	}

	public void setTongnhanno(String tongnhanno) {
		this.tongnhanno = tongnhanno;
	}

	public String getSochungtu() {
		return this.sochungtu;
	}
	
	public void setSochungtu(String sochungtu) {
		this.sochungtu = sochungtu;
	}

	public String[] getHdSotiengoc() {
		return this.HdSotiengoc;
	}

	public void setHdSotiengoc(String[] HdSotiengoc) {
		this.HdSotiengoc = HdSotiengoc;
	}

	public String[] getHdSotienphaixoa() {
		return this.HdSotienphaixoa;
	}

	public void setHdSotienphaixoa(String[] HdSotienphaixoa) {
		this.HdSotienphaixoa = HdSotienphaixoa;
	}
	
	public String[] getHdSotienxoa() {
		return this.HdSotienxoa;
	}

	public void setHdSotienxoa(String[] HdSotienxoa) {
		this.HdSotienxoa = HdSotienxoa;
	}
	
	public String[] getHdSotienconlai() {
		return this.HdSotienconlai;
	}
	
	public void setHdSotienconlai(String[] HdSotienconlai) {
		this.HdSotienconlai = HdSotienconlai;
	}

	public String[] getHdLoai() {
		return this.HdLoai;
	}

	public void setHdLoai(String[] HdLoai) {
		this.HdLoai = HdLoai;
	}
	
	public String[] getHdTigia() {
		return this.HdTigia;
	}

	public void setHdTigia(String[] hdTigia) {
		this.HdTigia = hdTigia;
	}
	
	public String getCongtyId() {
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) {
		this.congtyId = congtyId;
	}

	public String getnppdangnhap() {
		return this.nppdangnhap;
	}
	
	public void setnppdangnhap(String nppdangnhap) {
		this.nppdangnhap = nppdangnhap;
	}
    
	public String getKHChuyenNoIdGoc() {
		return this.KHChuyenNoIdGoc;
	}
	
	public void setKHChuyenNoIdGoc(String KHChuyenNoIdGoc) {
		this.KHChuyenNoIdGoc = KHChuyenNoIdGoc;
	}
	
	public String getKHNhanNoIdGoc() {
		return this.KHNhanNoIdGoc ;
	}
	
	public void setKHNhanNoIdGoc(String KHNhanNoIdGoc) {
		this.KHNhanNoIdGoc = KHNhanNoIdGoc;
	}

	public String getIsNPPChuyenNo() {
		return this.isNPPChuyenNo;
	}
	
	public void setIsNPPChuyenNo(String isNPPChuyenNo) {
		this.isNPPChuyenNo = isNPPChuyenNo;
	}
	
	public String getIsNPPNhanNo() {
		return this.isNPPNhanNo ;
	}
	
	public void setIsNPPNhanNo(String isNPPNhanNo) {
		this.isNPPNhanNo = isNPPNhanNo;
	}
	
	public void init_display() {
		Utility util = new Utility();
		String query =  " select pk_seq , NGAYBUTRU , isnull(SOCHUNGTU,'') as SOCHUNGTU, KH_CHUYENNO, KH_NHANNO, TONGTIEN, TIENTE_FK, isnull(isNPP,0) isNPP, isnull(isNPPNhanNo,0) isNPPNhanNo,ISNULL(DIENGIAI,'') AS DIENGIAI ";
		query = query + " from ERP_BUTRUKHACHHANG  " +
				        " where pk_seq = '"+ this.id +"' " ;
		System.out.println(query);
		ResultSet rs =  db.get(query );
		System.out.println(query );
		try
        {
            rs.next();        	
            this.id = rs.getString("pk_seq");
			this.ngaychungtu =  rs.getString("NGAYBUTRU");
			this.sochungtu = rs.getString("SOCHUNGTU");	
			this.KHChuyenNoId = rs.getString("KH_CHUYENNO");	
			this.KHNhanNoId = rs.getString("KH_NHANNO");
			this.TienteId = rs.getString("TIENTE_FK");
			this.tongchuyenno = rs.getString("TONGTIEN");
			this.tongnhanno = rs.getString("TONGTIEN");								
			this.dienGiaiCT = rs.getString("DIENGIAI");
			rs.close();			
       	}
        catch(Exception e){
        	e.printStackTrace();
        }
        finally{try {
			if(rs != null)
				rs.close();
		} catch (Exception e2) {
		
		}}
       
		this.Tientelist = db.get("select pk_seq, ma + ', ' + ten as TEN from ERP_TIENTE ");
		
		//PHAN QUYEN
//		String strQUYEN = 
				util.getPhanQuyen_TheoNhanVien("KHACHHANG", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongIddn() );

		query =   " select PK_SEQ PK_SEQ, ISNULL(MA, '') AS MA, isnull(Ten,'') nppTen from NHAPHANPHOI where trangthai = '1' and pk_seq != 1  \n";
		
		this.KHChuyenNolist = db.get(query);
		
		//PHAN QUYEN
				
		query = " select PK_SEQ PK_SEQ, ISNULL(MA, '') AS MA, isnull(Ten,'') nppTen from NHAPHANPHOI where trangthai = '1' and pk_seq != 1  \n";
		
		this.KHNhanNolist = db.get(query);
		
		System.out.println(query);
			   	
		String sql ="";
		if(this.KHChuyenNoId.trim().length() > 0){
			if(this.id.length()<=0){
				this.tongchuyenno = "0";
				this.tongnhanno = "0";
			}
			
			if(this.id.length() > 0)
			{
				// HÓA ĐƠN TÀI CHÍNH
				sql = 	
					"SELECT distinct 0 AS LOAIHD, ISNULL( HD.TYGIA,1) AS TIGIA  ,HD.PK_SEQ, '' AS MAHOPDONG, \n" +
					"		HD.KYHIEU AS KYHIEU, HD.SOHOADON SOHOADON, \n " + 
					"		HD.NGAYXUATHD AS NGAYHOADON, \n " +
					" 		ISNULL(HD.TONGTIENAVAT, 0) AS SOTIENGOC, \n " +
		   			"		CAST( (ISNULL(HD.TYGIA, 1)*(ISNULL(HD.TONGTIENAVAT,0) - ISNULL(DATHU.TONGTHU, '0'))) as numeric(18,0)) AS SOTIENVND, \n" +
		   			"		ISNULL(HD.TONGTIENAVAT, 0) - ISNULL(DATHU.TONGTHU, '0') AS SOTIENNT, \n" +
		   			"		BUTRU.XOANO AS SOTIENTT, ISNULL(HD.TIENTE_FK,100000) AS TTID," +
		   			"   	'' as NGAYDENHANTT, BUTRU.HOADON_FK CHON \n" +
		   			
		   			" FROM   ERP_HOADON HD \n"+
		   			"       LEFT JOIN ERP_THUTIEN_HOADON TT_HD ON TT_HD.HOADON_FK = HD.PK_SEQ and TT_HD.LOAIHOADON= 0   \n" +
					"       LEFT JOIN	\n" +
					"  		( 	\n" +
					"		 SELECT DATHU.HOADON_FK,SUM( DATHU.TONGTHU) TONGTHU \n"+
					"		 FROM ("+
					// THU TIỀN HÓA ĐƠN
					"			SELECT 	TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS TONGTHU \n" + 		
					"			FROM 	ERP_THUTIEN_HOADON TTHD \n" +
					"					INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n" + 		
					"			WHERE TT.TRANGTHAI != '2' AND TTHD.LOAIHOADON = 0 AND TT.BANGKE_FK IS NULL \n" + 			
					"			GROUP BY HOADON_FK \n" +
					
					"			UNION ALL \n" +
					
					// BẢNG KÊ THU TIỀN
					"			SELECT 	BKHD.HOADON_FK, SUM(BKHD.THANHTOAN) AS TONGTHU \n" + 		
					"			FROM 	ERP_BANGKETHUTIEN BK \n" +
					"					INNER JOIN ERP_BANGKETHUTIEN_HOADON BKHD ON BKHD.BANGKE_FK = BK.PK_SEQ \n" + 		
					"			WHERE   BK.TRANGTHAI != '2' \n" + 			
					"			GROUP BY HOADON_FK \n" +
					
					"			UNION ALL \n" +
					
					//XÓA KHÁCH HÀNG TRẢ TRƯỚC
					"			SELECT 	TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n" +   		
					"			FROM 	ERP_XOAKHTRATRUOC_HOADON TTHD \n" +
					"				 	INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \n" +  		
					"			WHERE 	TT.TRANGTHAI NOT IN (2) AND ISNULL(TT.LOAIXOATRATRUOC,0) = '0' \n"+
					"					AND TTHD.LOAIHD = '0'  \n" +		
					"			GROUP BY HOADON_FK \n" +
					
					"			UNION ALL \n"+
					
					//BÙ TRỪ KHÁCH HÀNG
					"			SELECT BT_KH.HOADON_FK, SUM(BT_KH.XOANO) AS SOTIENBUTRU  \n"+
					"			FROM   ERP_BUTRUKHACHHANG BT INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BT_KH ON BT.PK_SEQ =BT_KH.BTKH_FK \n " +
					"			WHERE  BT.TRANGTHAI NOT IN (2) AND BT_KH.LOAIHD = 0 AND BT.PK_SEQ NOT IN ("+this.id+") \n"+
					"			GROUP BY BT_KH.HOADON_FK \n"+					
					"		) DATHU \n"+
					"		GROUP BY DATHU.HOADON_FK \n "+
					"  )DATHU ON HD.PK_SEQ = DATHU.HOADON_FK \n" +
					" 		INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BUTRU ON HD.PK_SEQ = BUTRU.HOADON_FK \n"+
					
					"WHERE  ISNULL(HD.TIENTE_FK,100000) = " + this.TienteId + " " +
					"       AND BUTRU.BTKH_FK= '"+this.id+"' AND BUTRU.LOAIHD = 0 \n" ;
					
					// HÓA ĐƠN HÀNG TRẢ VỀ
					
			sql += " UNION ALL \n";			
			
			sql +=  " select '7' AS LOAIHD, 1 AS TYGIA, b.pk_seq , '' MAHOPDONG, b.KYHIEU as kyhieu, b.sohoadon SOHOADON, b.ngayxuathd as ngayhoadon, b.tongtienAVAT*(-1) SOTIENGOC,  \n"+
		       		" (b.tongtienAVAT - isnull(thanh_toan.tongthanhtoan, '0'))*(-1) as sotienAVAT, \n"+       
		       		" ((b.tongtienAVAT - isnull(thanh_toan.tongthanhtoan, '0'))* isnull(b.TYGIA,1))*(-1) as sotienAVATVND, \n"+       
		       		" a.XOANO*(-1) as DaThanhToan,  100000 AS TTID, '' NGAYDENHANTT, a.hoadon_fk CHON \n"+
				 	
				 	"from ERP_BUTRUKHACHHANG_CHITIET a \n" +
				 	"     left join ERP_HOADON b on a.hoadon_fk = b.pk_seq \n" +
				 	"     left join	\n" +
				 	"	  ( 	\n"+
				 	"		 SELECT THU.HOADON_FK, SUM(THU.DATHANHTOAN) tongthanhtoan \n"+
				 	"		 FROM (\n"+
					// XÓA KHÁCH HÀNG TRẢ TRƯỚC
					"			select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN    \n" +
					"			from ERP_XOAKHTRATRUOC_HOADON TTHD \n" +
					"			inner join ERP_XOAKHTRATRUOC TT on TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ  \n" +
					"			where TT.TRANGTHAI != 2 AND TTHD.LOAIHD = '7' and TT.KHACHHANG_FK = "+this.KHChuyenNoId+" \n"+	
					"			group by HOADON_FK  \n" +
						
					"			union all   \n" +
					// THU TIỀN HÓA ĐƠN
					"			select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN   \n" +
					"			from ERP_THUTIEN_HOADON TTHD " +
					"			inner join ERP_THUTIEN TT on TTHD.THUTIEN_FK = TT.PK_SEQ  \n" +
					"			where  TT.TRANGTHAI != 2 AND TTHD.LOAIHOADON = '7' AND TT.BANGKE_FK IS NULL \n" +
					"			group by HOADON_FK  \n" +
					
					// THU TIỀN BẢNG KÊ
					"       	UNION ALL \n"+
					
					"			SELECT TTHD.HOADON_FK , SUM(TTHD.SOTIENTT) as DATHANHTOAN   \n" +
					"			FROM ERP_THUTIEN_HOADON TTHD " +
					"			INNER JOIN ERP_THUTIEN TT on TTHD.THUTIEN_FK = TT.PK_SEQ  \n" +
					"			where  TT.TRANGTHAI != 2 AND TTHD.LOAIHOADON = '7'  AND TT.BANGKE_FK IS NOT NULL \n" +
					"			group by HOADON_FK  \n" +
					
					// BÙ TRỪ KHÁCH HÀNG
					"			UNION ALL \n"+		
					
					"			SELECT TTHD.HOADON_FK , SUM(TTHD.XOANO) as DATHANHTOAN \n"+   
					"			FROM ERP_BUTRUKHACHHANG_CHITIET TTHD inner join ERP_BUTRUKHACHHANG TT on TTHD.BTKH_FK = TT.PK_SEQ \n"+  
					"			WHERE  TT.TRANGTHAI != 2 AND TTHD.LOAIHD = '7'  AND TT.PK_SEQ NOT IN ("+this.id+") \n"+ 
					
					"			GROUP BY HOADON_FK \n"+
					
					"  			UNION ALL \n"+
					
					// THANH TOÁN HÓA ĐƠN
					"			SELECT TT_HD.HOADON_FK, SUM(TT_HD.THANHTOAN) AS THANHTOAN  \n"+
					"			FROM   ERP_THANHTOANHOADON TT INNER JOIN ERP_THANHTOANHOADON_HOADON_PHANBO TT_HD ON TT.PK_SEQ = TT_HD.TTHD_FK \n "+
					"			WHERE  TT.TRANGTHAI NOT IN (2) AND TT_HD.LOAIHD = 8 AND TT_HD.KHACHHANG_FK = "+this.KHChuyenNoId+" \n"+
					"			GROUP BY TT_HD.HOADON_FK \n"+
					
					"			) THU \n"+
					"		 GROUP BY THU.HOADON_FK \n"+
					"	  )thanh_toan on a.hoadon_fk = thanh_toan.hoadon_fk \n" +
					
					"where a.BTKH_FK = '" + this.id + "' and a.LOAIHD= '7' and b.LOAIHOADON = 2 \n";
				
				// HOA DON KHÁC
				if(this.TienteId.equals("100000"))
				{
					sql += 
					 " UNION ALL \n"+
			
					 "SELECT distinct TT_HD.LOAIHOADON AS LOAIHD, '1' AS TIGIA  ,HDPL.PK_SEQ, '' AS MAHOPDONG, HDPL.kyhieuhoadon AS KYHIEU, HDPL.SOHOADON, \n"+
					 "		 HDPL.ngayhoadon , \n"+
					 "  	 ISNULL(PLSP.SOTIEN, 0)  AS SOTIENGOC, \n " +
					 "		 ISNULL(PLSP.SOTIEN, 0) - ISNULL(DATHU.TONGTHU, '0') AS SOTIENVND, \n"+
					 "		 0 AS SOTIENNT, \n"+
					 "		 BUTRU.XOANO AS SOTIENTT , '100000' AS TTID," +
					 "   	'' as NGAYDENHANTT, BUTRU.HOADON_FK CHON \n"+
					 
					 " FROM ERP_HoaDonPheLieu HDPL ";
					 if(this.isNPPChuyenNo.equals("0"))
						 sql+= " INNER JOIN KHACHHANG KH ON HDPL.KHACHHANG_FK = KH.PK_SEQ \n";
					 else if(this.isNPPChuyenNo.equals("1"))
						 sql+= " INNER JOIN NHAPHANPHOI KH ON HDPL.KHACHHANG_FK = KH.PK_SEQ \n";
					 else if(this.isNPPChuyenNo.equals("1"))
						 sql+= " INNER JOIN ERP_NHANVIEN KH ON HDPL.KHACHHANG_FK = KH.PK_SEQ \n";
					 
					 sql+=
					 " 		LEFT JOIN ERP_THUTIEN_HOADON TT_HD ON TT_HD.HOADON_FK = HDPL.PK_SEQ and TT_HD.LOAIHOADON = 1  \n"+
					 " 		INNER JOIN (SELECT hoadonphelieu_fk, SUM(thanhtien)AS SOTIEN \n"+
					 "			 		FROM erp_hoadonphelieu_sanpham \n"+
					 "			 		GROUP BY hoadonphelieu_fk )PLSP ON HDPL.pk_seq= PLSP.hoadonphelieu_fk \n"+
					 " 		LEFT JOIN	\n"+
					 " 		( 	\n"+
					 "			SELECT DATHU.HOADON_FK,SUM( DATHU.TONGTHU) TONGTHU \n "+
					 "			FROM ( \n"+
					 // THU TIỀN
					 "				SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS TONGTHU \n"+
					 "				FROM ERP_THUTIEN_HOADON TTHD \n"+
					 "				INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n"+
					 "				WHERE TT.TRANGTHAI != '2' AND TT.BANGKE_FK IS NULL AND TTHD.LOAIHOADON = 1 \n"+
					 "				GROUP BY HOADON_FK \n"+
					 
					 "				UNION ALL \n" +
					 //XÓA NỢ KHÁCH HÀNG
					 "				SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n" +   		
					 "				FROM ERP_XOAKHTRATRUOC_HOADON TTHD \n" +
					 "					 INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \n" +  		
					 "				WHERE TT.TRANGTHAI NOT IN (2) AND ISNULL(TT.LOAIXOATRATRUOC,0) = '0' \n" +
					 "				AND TTHD.LOAIHD = '1' \n"+		
					 "				GROUP BY HOADON_FK \n" +
					
					 "				UNION ALL \n"+
					 //BÙ TRỪ KHÁCH HÀNG
					 "				SELECT BT_KH.HOADON_FK, SUM(BT_KH.XOANO) AS SOTIENBUTRU  \n"+
					 "				FROM   ERP_BUTRUKHACHHANG BT INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BT_KH ON BT.PK_SEQ =BT_KH.BTKH_FK \n " +
					 "				WHERE  BT.TRANGTHAI NOT IN (2) AND BT_KH.LOAIHD = 1 AND BT.PK_SEQ NOT IN( "+this.id +" ) \n"+
					 "				GROUP BY BT_KH.HOADON_FK \n"+
					 
					 "			) DATHU \n"+
					 "		GROUP BY DATHU.HOADON_FK \n	"+
					 " )DATHU ON HDPL.PK_SEQ = DATHU.HOADON_FK \n"+
					 " 		INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BUTRU ON HDPL.PK_SEQ = BUTRU.HOADON_FK \n"+
					 
					 " WHERE 100000 = " + this.TienteId + " \n" +
					 "       AND BUTRU.BTKH_FK= '"+this.id+"' AND BUTRU.LOAIHD = 1 \n" ;
				}
								
				// KHACHHANGTRATRUOC
				sql += 	
					" UNION ALL \n" +
					
					" SELECT  distinct '3' as LOAIHD, tt.TIGIA, tt.PK_SEQ, '' AS MAHOPDONG, '' AS KYHIEU, CAST(tt.PK_SEQ as nvarchar(50)) AS SOHOADON, tt.NGAYCHUNGTU AS NGAYHOADON, \n" +
				 	"        CASE WHEN tt.TIENTE_FK = '100000' THEN tt.THUDUOC*(-1) ELSE tt.THUDUOCNT*(-1) END as SOTIENGOC, \n" +
				 	"        (tt.THUDUOC - ISNULL(XKH.TIENTHANHTOAN,0) - ISNULL(DATHU.DATHANHTOAN,0) )*(-1)  AS SOTIENVND, \n"+
				 	"        (tt.THUDUOC - ISNULL(XKH.TIENTHANHTOAN,0) - ISNULL(DATHU.DATHANHTOAN,0) )*(-1) AS SOTIENNT, BUTRU.XOANO*(-1) AS SOTIENTT , tt.TIENTE_FK AS TTID, \n"+
				 	"        '' as NGAYDENHANTT, BUTRU.HOADON_FK CHON \n" +
				 	
				 	" FROM 	ERP_THUTIEN tt "+
					"     INNER JOIN NHAPHANPHOI KH ON TT.KHACHHANG_FK = KH.PK_SEQ \n";
				
				sql+=
				 	"     LEFT JOIN  \n" +
				 	"	 ( SELECT XKH.CTTT_FK, SUM (XKH.TIENTHANHTOAN) TIENTHANHTOAN  \n" +
				 	"	   FROM ERP_XOAKHTRATRUOC_CTTT XKH INNER JOIN ERP_XOAKHTRATRUOC TT ON XKH.xoakhtratruoc_fk = TT.PK_SEQ  \n" +
				 	" 	   WHERE TT.TRANGTHAI NOT IN (2) AND XKH.LOAICT = 0 \n" +
				 	"	  GROUP BY XKH.CTTT_FK ) XKH \n" +
				 	"	  ON TT.PK_SEQ = XKH.cttt_fk \n"+
				 	"	  LEFT JOIN \n" +
				 	"	  (" +
				 	"		SELECT DATHU.HOADON_FK,SUM( DATHU.DATHANHTOAN) DATHANHTOAN \n "+
					"		FROM ( \n" +
		 			"				SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n" +   		
					"				FROM ERP_XOAKHTRATRUOC_HOADON TTHD \n" +
					"				INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \n" +  		
					"				WHERE TT.TRANGTHAI NOT IN (2) AND ISNULL(TT.LOAIXOATRATRUOC,0) = '0' \n"+
					"				AND TTHD.LOAIHD = '3'  \n" +		
					"				GROUP BY HOADON_FK \n" +
				
					"				UNION ALL \n"+
				
					"				SELECT BT_KH.HOADON_FK, SUM(BT_KH.XOANO) AS SOTIENBUTRU  \n"+
					"				FROM   ERP_BUTRUKHACHHANG BT INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BT_KH ON BT.PK_SEQ =BT_KH.BTKH_FK \n " +
					"				WHERE  BT.TRANGTHAI NOT IN (2) AND BT_KH.LOAIHD = 3 AND BT.PK_SEQ NOT IN ( "+this.id +" ) \n"+
					"				GROUP BY BT_KH.HOADON_FK \n"+
					"			) DATHU \n"+
					"		GROUP BY DATHU.HOADON_FK \n	"+
					"	   ) DATHU ON tt.PK_SEQ = DATHU.HOADON_FK  \n"+
				 	" 	   INNER JOIN  ERP_BUTRUKHACHHANG_CHITIET BUTRU ON tt.PK_SEQ = BUTRU.HOADON_FK AND BUTRU.LOAIHD = 3 \n"+
					
				 	" WHERE tt.NOIDUNGTT_FK = '100001' AND tt.TRANGTHAI = '1' AND tt.TIENTE_FK = '"+ this.TienteId +"' \n" +
				 	"      AND tt.KHACHHANG_FK = "+ this.KHChuyenNoId +" \n"+
				 	"      AND BUTRU.BTKH_FK= '"+this.id+"' \n" ;
									
			}
								
			sql +=" ORDER BY LOAIHD, NGAYHOADON ASC ";
							
			System.out.println(sql);
			
				rs = db.get(sql);
				try 
			    {
					String hdID = "";
					String hdLOAI = "";
					String hdTIGIA = "";
					String hdNGAYHOADON = "";
					String hdSOHOADON = "";
					String hdSOTIENGOC = "";
					String hdSOTIENPHAIXOA = "";
					String hdXOANO="";
					String hdCONLAI = "";
					String hdCHON = "";
					
					double SOTIENPHAIXOA =0;
					double XOANO = 0;
					double CONLAI = 0;
					
					if(rs!= null)
				    {				    
						while(rs.next())
						{
							hdID += rs.getString("PK_SEQ") + "__";
							hdLOAI += rs.getString("LOAIHD") + "__";
							hdTIGIA += rs.getString("TIGIA") + "__";
							hdSOHOADON += rs.getString("SOHOADON") + "__";
							hdNGAYHOADON += rs.getString("NGAYHOADON") + "__";
							hdSOTIENGOC += rs.getString("SOTIENGOC") + "__";
							hdSOTIENPHAIXOA += rs.getString("SOTIENVND") + "__";
							hdXOANO += rs.getString("SOTIENTT") + "__";	
							
							SOTIENPHAIXOA = rs.getDouble("SOTIENVND");
							XOANO = 	rs.getDouble("SOTIENTT");
							
							CONLAI = SOTIENPHAIXOA - XOANO;
							System.out.println("SOTIENPHAIXOA:"+SOTIENPHAIXOA +" XOA NO: "+XOANO);
							
							hdCONLAI += CONLAI+"__";
							hdCHON += rs.getString("CHON") + "__";
						}
						if(hdID.trim().length() > 0)
						{
							hdID = hdID.substring(0, hdID.length() - 2);
							this.HdId = hdID.split("__");
							
							hdLOAI = hdLOAI.substring(0, hdLOAI.length() - 2);
							this.HdLoai = hdLOAI.split("__");
							
							hdTIGIA = hdTIGIA.substring(0, hdTIGIA.length() - 2);
							this.HdTigia = hdTIGIA.split("__");
							
							hdSOHOADON = hdSOHOADON.substring(0, hdSOHOADON.length() - 2);
							this.HdSohd= hdSOHOADON.split("__");
							
							hdNGAYHOADON = hdNGAYHOADON.substring(0, hdNGAYHOADON.length() - 2);
							this.HdNgayhd= hdNGAYHOADON.split("__");
							
							hdSOTIENGOC = hdSOTIENGOC.substring(0, hdSOTIENGOC.length() - 2);
							this.HdSotiengoc= hdSOTIENGOC.split("__");
							
							hdSOTIENPHAIXOA = hdSOTIENPHAIXOA.substring(0, hdSOTIENPHAIXOA.length() - 2);
							this.HdSotienphaixoa= hdSOTIENPHAIXOA.split("__");
							
							hdXOANO = hdXOANO.substring(0, hdXOANO.length() - 2);
							this.HdSotienxoa= hdXOANO.split("__");
							
							hdCONLAI = hdCONLAI.substring(0, hdCONLAI.length() - 2);
							this.HdSotienconlai = hdCONLAI.split("__");
								
							hdCHON = hdCHON.substring(0, hdCHON.length() - 2);
							this.HdChon= hdCHON.split("__");
						}
				    }
					
			    }
				catch(Exception e){
					e.printStackTrace();
				}
		}
	}
	
	Object loainhanvien;
	Object doituongIddn;
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
	
	public String getDoituongIddn() 
	{
		if( this.doituongIddn == null )
			return "";
		
		return this.doituongIddn.toString();
	}

	public void setDoituongIddn(Object doituongIddn) 
	{
		this.doituongIddn = doituongIddn;
	}
	
	public String getDienGiaiCT(){
		return this.dienGiaiCT;
	}
	
	public void setDienGiaiCT(String dienGiaiCT){
		this.dienGiaiCT = dienGiaiCT;
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
	
	public String getTrangthai()
	{
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}
	
	public String getTungay() 
	{
		return this.tungay;
	}
	
	public void setTungay(String tungay)
	{
		this.tungay = tungay;
	}
	
	public String getNgaytao()
	{
		return this.ngaytao;
	}
	
	public void setNgaytao(String ngaytao)
	{
		this.ngaytao = ngaytao;
	}
	
	public String getNguoitao()
	{
		return this.nguoitao;
	}
	
	public void setNguoitao(String nguoitao)
	{
		this.nguoitao = nguoitao;
	}
	
	public String getNgaysua()
	{
		return this.ngaysua;
	}
	
	public void setNgaysua(String ngaysua)
	{
		this.ngaysua = ngaysua;
	}
	
	public String getNguoisua()
	{
		return this.nguoisua;
	}
	
	public void setNguoisua(String nguoisua)
	{
		this.nguoisua = nguoisua;
	}
	
	public String getDenngay()
	{
		return this.denngay;
	}
	
	public void setDenngay(String denngay)
	{
		this.denngay = denngay;
	}
	
	public String getMessage() 
	{
		return this.msg;
	}
	
	public void setMessage(String msg) 
	{
		this.msg = msg;
	}
	
	public String getNppId() 
	{
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
	}
	
	public String getNppTen() 
	{
		return this.nppTen;
	}

	public void setNppTen(String nppTen) 
	{
		this.nppTen = nppTen;
	}
	
	public String getSitecode() 
	{
		return this.sitecode;
	}

	public void setSitecode(String sitecode) 
	{
		this.sitecode = sitecode;
	}

	public ResultSet getKhList() 
	{
		return this.khlist;
	}
	
	public void setKhList(ResultSet khlist) 
	{
		this.khlist = khlist;
	}
	
	public String getKhId() 
	{		
		return this.khId;
	}
	
	public void setKhId(String khId) 
	{
		this.khId = khId;		
	}

	public ResultSet getHoadonList() 
	{		
		return this.hoadonlist;
	}
	
	public void setHoadonList(ResultSet hoadonList) 
	{
		this.hoadonlist = hoadonList;		
	}

/*	public Hashtable<Integer, String> getKhIds() 
	{		
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		if(this.khIds != null){
			int size = (this.khIds).length;
			int m = 0;
			while(m < size){
				selected.put(new Integer(m), this.khIds[m]) ;
				m++;
			}
		}else{
			selected.put(new Integer(0), "null");
		}
		return selected;
	}*/
	
	public void setKhIds(String[] khIds) 
	{
		this.khIds = khIds;
	}

	public String getTenKHChuyenNo() {
		return tenKHChuyenNo;
	}

	public void setTenKHChuyenNo(String tenKHChuyenNo) {
		this.tenKHChuyenNo = tenKHChuyenNo;
	}

	public String getTenKHNhanNo() {
		return tenKHNhanNo;
	}

	public void setTenKHNhanNo(String tenKHNhanNo) {
		this.tenKHNhanNo = tenKHNhanNo;
	}
}