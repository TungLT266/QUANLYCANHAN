package geso.traphaco.center.beans.donmuahang.imp;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import geso.traphaco.center.beans.donmuahang.ISanPhamTraKM;
import geso.traphaco.center.beans.donmuahang.ITaodonhangKm;
import geso.traphaco.center.db.sql.dbutils;
public class TaodonhangKm implements ITaodonhangKm{

	String thang;
	String nam;
	String userid;
	ResultSet rsctkm;
	ResultSet rssp;
	String msg="";
	dbutils db;
	ResultSet rsnpp;
	String nppchon;
	String KbhId;
	ResultSet rskenh;
	
	
	String nppchon_thanhtoantien;
	
	
	String ctkmchon="";
	List<ISanPhamTraKM> listsp;
	
	
	public TaodonhangKm(){
		db=new dbutils();	
		this.thang="";
		this.ctkmchon="";
		this.nppchon="";
		this.KbhId="";
		
		this.nam="";
		listsp=new ArrayList<ISanPhamTraKM>() ;	
	}
	public String getId() {

		return null;
	}

	@Override
	public void setId(String id) {

		
	}

	@Override
	public String getNam() {

		return this.nam;
	}

	@Override
	public void setNam(String _nam) {

		this.nam=_nam;
	}

	@Override
	public String getThang() {

		return this.thang;
	}

	@Override
	public void setThang(String _thang) {

		this.thang=_thang;
	}

	@Override
	public String getUserId() {

		return this.userid;
	}

	@Override
	public void setUserId(String userId) {

		this.userid=userId;
	}

	@Override
	public ResultSet getRsCTKM() {

		return this.rsctkm;
	}

	@Override
	public void getRsCTKM(ResultSet rs) {

		this.rsctkm=rs;
	}

	@Override
	public void Init() {

		String sql=" ";
		/*
		 * Lay tat ca CTKM tra san pham kho npp ứng
		 * 
		 */
		 sql=
		" select pk_seq,scheme,diengiai from ctkhuyenmai KM " +
		"  where "+
		" EXISTS "+
		" (  "+
		" 	SELECT  CTKMID "+
		" 	FROM DONHANG DH "+
		" 	INNER JOIN DONHANG_CTKM_TRAKM DHKM ON DH.PK_SEQ=DHKM.DONHANGID "+
		"	WHERE DH.NGAYNHAP LIKE '"+this.nam+"-"+(thang.length()>1? thang:"0"+thang)+"%'  AND DHKM.CTKMID=KM.PK_SEQ  "+
		" 	AND KM.DENNGAY >='"+this.nam+"-"+(thang.length()>1? thang:"0"+thang)+"%'  and KM.kho_fk=100000 AND DH.TRANGTHAI=1  "+
		" )  ";  
		this.rsctkm=db.get(sql);
		
		sql="select pk_seq,ma,ten from sanpham where trangthai=1";
		this.rssp=db.getScrol(sql);
		
		System.out.println("ctkmchon : "+this.ctkmchon);
		
		if(this.ctkmchon.length()>0)
		{
			sql=" SELECT distinct NPP.PK_SEQ ,NPP.TEN  ,  NPP.SITECODE "+
			" FROM CTKHUYENMAI A     "+
			" INNER JOIN CTKM_NPP ON A.PK_SEQ=CTKM_NPP.CTKM_FK AND CHON=1 "+  
			" INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=CTKM_NPP.NPP_FK   "+
			" WHERE A.PK_SEQ IN ("+this.ctkmchon+")   "+
			" AND  NOT EXISTS " +
			"( " +
			"  SELECT * FROM DONDATHANG DDH INNER JOIN DONDATHANG_SP DDH_SP  ON DDH_SP.DONDATHANG_FK=DDH.PK_SEQ  "+ 
			" WHERE LOAIDONHANG=1 AND  DDH_SP.SCHEME IS NULL AND DDH.TRANGTHAI!=6 AND DDH.THANG='"+this.thang+"' AND DDH.NAM='"+this.nam+"' "+
			" AND DDH.NPP_FK =NPP.PK_SEQ  " +
			" ) " ;
			System.out.println("sql LAY NHA PP : "+sql);
			this.rsnpp=db.get(sql);
		}
		
	}

	@Override
	public boolean ThucHien() 
	{
		dbutils db=new dbutils();
		try
		{
				
		/*
		 * Chuong Trinh Khuyen mai tra san pham kho npp ung
		 */
			
			String ctkmId="0";
			String query=
				" select pk_seq as ctkmId from ctkhuyenmai KM " +
				"  where "+
				" EXISTS "+
				" (  "+
				" 	SELECT  CTKMID "+
				" 	FROM DONHANG DH "+
				" 	INNER JOIN DONHANG_CTKM_TRAKM DHKM ON DH.PK_SEQ=DHKM.DONHANGID "+
				"	WHERE DH.NGAYNHAP LIKE '"+this.nam+"-"+(thang.length()>1? thang:"0"+thang)+"%'  AND DHKM.CTKMID=KM.PK_SEQ  "+
				" 	AND KM.DENNGAY >='"+this.nam+"-"+(thang.length()>1? thang:"0"+thang)+"%'  and KM.kho_fk=100000 AND DH.TRANGTHAI=1  "+
				" )  ";
			ResultSet rs=db.get(query);
			while(rs.next())
			{
				ctkmId+=","+rs.getString("ctkmId");
			}
			if(rs!=null)rs.close();
			db.getConnection().setAutoCommit(false);
			String[] Npparray=	this.nppchon.split(",");
	  		String sql="";					
	  		for(int t=0;t<Npparray.length;t++)
	  		{
				sql= "  SELECT SP.DVKD_FK, DH.NPP_FK,DH.KBH_FK,sp.pk_seq as spid,DVDL.DONVI ,   "+ 
					 "  QC.SOLUONG2,QC.SOLUONG1,QC.DVDL2_FK,SUM( DHSP.SOLUONG) AS SOLUONG ,  	    "+ 
					 "  isnull((select top 1  giamuanpp  FROM               		     "+ 
					 "  dbo.BGMUANPP_SANPHAM  d inner join BangGiaMuaNPP p on p.pk_seq=d.BGMuaNPP_fk  inner join BANGGIAMUANPP_NPP        "+ 
					 "  npp on npp.Banggiamuanpp_fk=p.pk_seq where p.kenh_fk=dh.kbh_fk      "+ 
					 "  and dh.npp_fk=npp.npp_fk and d.sanpham_fk=SP.pk_seq and p.trangthai=1 order by p.TuNgay DESC ),0  ) as giamua      "+ 
					 "  FROM DONHANG DH     "+ 
					 "  INNER JOIN DONHANG_CTKM_TRAKM DHSP  ON DH.PK_SEQ=DHSP.DONHANGID     "+ 
					 "  INNER JOIN SANPHAM SP 	ON DHSP.SPMA=SP.MA         "+ 
					 "  INNER JOIN CTKHUYENMAI CTKM ON CTKM.PK_SEQ=DHSP.CTKMID     "+ 
					 "  INNER JOIN QUYCACH QC ON QC.SANPHAM_FK=SP.PK_SEQ AND DVDL2_FK=100018  "+ 
					 "  INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ=SP.DVDL_FK   "+ 
					 "  WHERE DH.TRANGTHAI=1 AND dh.NgayNhap LIKE '"+this.nam+"-"+(this.thang.length()>1? this.thang:"0"+this.thang)+"%'   "+ 
					 "  AND DH.PK_SEQ NOT IN      "+ 
					 "  (     "+ 
					 "  SELECT DONHANG_FK FROM DONHANGTRAVE DHTV      "+ 
					 "  WHERE DHTV.NGAYNHAP LIKE '"+this.nam+"-"+(this.thang.length()>1? this.thang:"0"+this.thang)+"%' AND DHTV.TRANGTHAI='3'    "+ 
					 "  AND DONHANG_FK IS NOT NULL     "+ 
					 "  ) and ctkm.pk_seq in ("+ctkmId+")  and dh.npp_fk in ("+Npparray[t]+")  " +
					 " AND   NOT EXISTS " +
					 "( " +
					 " SELECT * FROM DONDATHANG DDH INNER JOIN DONDATHANG_SP DDHSP ON DDHSP.DONDATHANG_FK=DDH.PK_SEQ  "+
					 "	WHERE  DDHSP.SCHEME IS NULL AND   NPP_FK=DH.NPP_FK AND THANG='"+this.thang+"' AND NAM='"+this.nam+"' AND TRANGTHAI!=6 AND LOAIDONHANG=1 " +
					 " ) "+  
					 "  GROUP BY  DH.NPP_FK,DH.KBH_FK,   "+ 
					 "  DVDL.DONVI,QC.DVDL2_FK,SP.PK_SEQ,QC.SOLUONG2,QC.SOLUONG1,SP.DVKD_FK " +
					 " ORDER BY DH.NPP_FK ,DH.KBH_FK,SP.DVKD_FK   ";
					System.out.println("GetData : "+sql);
    				 rs=db.get(sql);
    				String chuoitaomoi="";
    				String chuoitaomoibk="";
    				String dhId="";
    				String nppid="";
    				String dvkdid="";
    				String kbhid="";
    				while(rs.next())
    				{
    					  chuoitaomoi=rs.getString("NPP_FK")+rs.getString("KBH_FK")+rs.getString("DVKD_FK");
    					  if(!chuoitaomoi.equals(chuoitaomoibk))
    					  { 
    						  if(dhId.length()>0)
    						  {
	    						sql="  UPDATE DONDATHANG SET VAT= 10 ,SOTIENAVAT =(  "+
	    							"   SELECT SUM(SOTIENAVAT) FROM DONDATHANG_SP  WHERE DONDATHANG_FK=DONDATHANG.PK_SEQ "+ 
	    							"  ),SOTIENBVAT=( "+
	    							"  	SELECT SUM(SOTIENBVAT) FROM DONDATHANG_SP  WHERE DONDATHANG_FK=DONDATHANG.PK_SEQ "+ 
	    							"  ) WHERE PK_SEQ="+dhId;
	    						 
									if(!db.update(sql)){
										this.msg="Lỗi dòng lệnh : " +sql;
										db.update("rollback");
										return false;
										
									}	
    						  }
    						  chuoitaomoibk=chuoitaomoi;
    						  nppid=rs.getString("NPP_FK");
    						  dvkdid=rs.getString("DVKD_FK");
    						  kbhid=rs.getString("KBH_FK");
    						  
    						  
    						  sql=" insert into dondathang(NGAYDAT,TRANGTHAI,NGUOITAO,NGUOISUA,NPP_FK,DVKD_FK,NCC_FK,kbh_FK,loaidonhang,iskm,tinhtrang,KHOTT_FK,thang,nam,sotienbvat,vat,sotienavat,HinhThucVanChuyen) "+
    							 " values ('"+this.getDateTime()+"','1',"+this.userid+","+this.userid+","+nppid+","+dvkdid+",100014," + kbhid+" ,'1','1','1',100001,"+thang+","+nam+",0,0,0,'CTVC')  ";
    							 
    							if(!db.update(sql))
    							{
    								this.msg="Lỗi dòng lệnh : " +sql;
    								db.update("rollback");
    								return false;
    								
    							}	
    						 
							 query = "select IDENT_CURRENT('dondathang') as dhId";
							ResultSet rsDh = db.get(query);	
							rsDh.next();
					     	dhId = rsDh.getString("dhId");
					     	rsDh.close();
    					  }
    					  
    					 
    					  double soluongdu= Math.round(rs.getDouble("soluong") % (rs.getDouble("SOLUONG1")/rs.getDouble("soluong2")));
    					 
    					  double soluong=rs.getDouble("soluong")-soluongdu;
    			 
    					  double thanhtien=soluong * rs.getDouble("giamua");
    					  if(soluong>0)
    					  {
    					  	sql="insert into dondathang_sp (sanpham_fk,dondathang_fk,soluong,soluongduyet,dongia,donvi,sotienbvat ,chietkhau,vat,sotienavat,scheme,khott,dvdl_duyet_fk,giachuan) values (" 
    							+rs.getString("spid")+","+dhId+","+soluong+",'"+soluong+"' ,"+rs.getDouble("giamua")+",N'"+rs.getString("donvi")+"','"+thanhtien+"','"+0+"','"+thanhtien*0.1+"','"+(thanhtien*1.1)+"',NULL,'100001','100018','"+rs.getDouble("giamua")+"')";
    					 
    						if(!db.update(sql)){
    						 
    							this.msg="Lỗi dòng lệnh : " +sql;
    							db.update("rollback");
    							return false;
    						}
    					  }
    						
    						if(soluongdu >0){
    							sql=" insert into DONDATHANG_CTKM (DONDATHANG_FK,SCHEME,SOTIEN,SANPHAM_FK,SOLUONG,DONGIA,VAT,TIENAVAT) " +
    									" VALUES ("+dhId+",NULL,"+soluongdu* rs.getDouble("giamua") +","+rs.getString("spid")+","+soluongdu+","+rs.getDouble("giamua")+","+(0.1* soluongdu* rs.getDouble("giamua")) +","+(1.1* soluongdu* rs.getDouble("giamua")) +")";
        						if(!db.update(sql)){
        							this.msg="Lỗi dòng lệnh : " +sql;
        							db.update("rollback");
        							return false;
        						}
        						
    						}
    					  
    				}
    				  if(dhId.length()>0)
					  {
    					sql="  UPDATE DONDATHANG SET VAT= 10 ,SOTIENAVAT =(  "+
							"   SELECT SUM(SOTIENAVAT) FROM DONDATHANG_SP  WHERE DONDATHANG_FK=DONDATHANG.PK_SEQ "+ 
							"  ),SOTIENBVAT=( "+
							"  	SELECT SUM(SOTIENBVAT) FROM DONDATHANG_SP  WHERE DONDATHANG_FK=DONDATHANG.PK_SEQ "+ 
							"  ) WHERE PK_SEQ="+dhId;
						 
							if(!db.update(sql)){
								this.msg="Lỗi dòng lệnh : " +sql;
								db.update("rollback");
								return false;
								
							}
					  }
			rs.close();
	  	}
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}catch(Exception er)
		{
			er.printStackTrace();
			this.msg=er.toString();
			db.update("rollback");
			return false;
		}
		return true;	
	}
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	@Override
	public String getMsg() {

		return this.msg;
	}
	@Override
	public void setMsg(String _msg) {

		this.msg=_msg;
		
	}
	@Override
	public void setCTKMChon(String str) {

		this.ctkmchon=str;
	}
	@Override
	public String getCTKMChon() {

		return this.ctkmchon;
	}
	@Override
	public void setListSanpham(List<ISanPhamTraKM> list) {

		listsp=list;
	}
	@Override
	public List<ISanPhamTraKM> getListSanPham() {

		return listsp;
	}
	@Override
	public ResultSet getRsSp() {

		return this.rssp;
	}
	@Override
	public void getRsSp(ResultSet rs) {

		this.rssp=rs;
	}
	@Override
	public void LoadSpKm() {

		this.listsp.clear();
		
	String	sql= "  select npp.pk_seq as nppid,npp.ten as nppten,a.pk_seq as ctkmid ,   "+ 
				 "  a.scheme,a.diengiai , sp.pk_seq ,sp.ma,sp.ten  "+ 
				 "  from ctkhuyenmai a   "+ 
				 "  inner join  ctkm_trakm b on a.pk_seq=b.ctkhuyenmai_fk   "+ 
				 "  inner join trakhuyenmai c on c.pk_seq=b.trakhuyenmai_fk    "+ 
				 "  inner join TRAKHUYENMAI_SANPHAM d on  d.TRAKHUYENMAI_FK=c.pk_seq    "+ 
				 "  inner join sanpham sp on sp.pk_seq=d.sanpham_fk    "+ 
				 "  inner join ctkm_npp on a.pk_seq=ctkm_npp.ctkm_fk and chon=1  "+ 
				 "  inner join nhaphanphoi npp on npp.pk_seq=ctkm_npp.npp_fk  "+ 
				 "  where a.pk_seq in ("+this.ctkmchon+") ";
	
		//System.out.println(sql);
		
		List<ISanPhamTraKM> listsp1	=new ArrayList<ISanPhamTraKM>() ;
		
		ResultSet rs=db.get(sql);
		try{
		if(rs!=null){
			while (rs.next()){
				ISanPhamTraKM a=new SanPhamTraKm();
				a.setNPPId(rs.getString("nppid"));
				a.setNPPTen(rs.getString("nppten"));
				a.setctkm(rs.getString("ctkmid"));
				a.setScheme(rs.getString("scheme"));
				a.setSpId(rs.getString("pk_seq"));
				a.setdiengiai(rs.getString("diengiai"));
				a.setspma(rs.getString("ma"));
				a.setspten(rs.getString("ten"));
				a.setSpIdTT(rs.getString("pk_seq"));
				a.setspmatt(rs.getString("ma"));
				a.setsptentt(rs.getString("ten"));
				a.setsoluongtt(1);
				listsp1.add(a);
			}
		}
		this.listsp=listsp1;
		}catch(Exception er){
			er.printStackTrace();
		}
	}
	@Override
	public ResultSet getRsNpp() {

		return this.rsnpp;
	}
	@Override
	public void setRsNpp(ResultSet rs) {

		this.rsnpp=rs;
	}
	@Override
	public String getNppChon() {

		return this.nppchon;
	}
	@Override
	public void setNppChon(String NppChon) {

		this.nppchon=NppChon;
	}
	@Override
	public String getNppChon_ThanhToanTien() {

		return this.nppchon_thanhtoantien;
	}
	@Override
	public void setNppChon_ThanhToanTien(String NppChon_TTT) {

		this.nppchon_thanhtoantien=NppChon_TTT;
	}
	@Override
	public ResultSet getRsKenh() {

		return this.rskenh;
	}
	@Override
	public void getRsKenh(ResultSet rs) {

		this.rskenh=rs;
	}
	@Override
	public String getKbhId() {

		return this.KbhId;
	}
	@Override
	public void setKbhId(String kbhId) {

		this.KbhId=kbhId;
	}

	
}
