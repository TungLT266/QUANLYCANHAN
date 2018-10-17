package geso.traphaco.erp.beans.khoasothang.imp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.traphaco.erp.beans.khoasothang.IErpNuocda;
import geso.traphaco.erp.beans.khoasothang.IErptiendien;
import geso.traphaco.erp.beans.khoasothang.IErptiennuoc;
import geso.traphaco.erp.beans.khoasothang.IErptinhgiadongluc;
import geso.traphaco.erp.db.sql.dbutils;

public class Erptinhgiadongluc implements IErptinhgiadongluc 
{
	
	private String Id;
	private String msg;
	private String UserId;
	private int thang;
	private int nam;
	private String trangthai ;
	
	private double tongtiendien;
	private List<IErptiendien> listtiendien;
	
	private double tongtiennuoc;
	private List<IErptiennuoc> listtiennuoc;
	private List<IErpNuocda > listnuocda;
	dbutils db=new dbutils();
	
	public 	Erptinhgiadongluc(){
		this.Id="";
		this.thang=0;
		this.nam=0;
		this.msg="";
		this.trangthai="";
	}
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return this.Id;
	}

	@Override
	public void setId(String _Id) {
		// TODO Auto-generated method stub
		this.Id=_Id;
	}

	@Override
	public int getThang() {
		// TODO Auto-generated method stub
		return this.thang;
	}

	@Override
	public void setThang(int _thang) {
		// TODO Auto-generated method stub
		this.thang=_thang;
	}

	@Override
	public int getNam() {
		// TODO Auto-generated method stub
		return this.nam;
	}

	@Override
	public void setNam(int _nam) {
		// TODO Auto-generated method stub
		this.nam=_nam;
	}

	@Override
	public double getTongTienDien() {
		// TODO Auto-generated method stub
		return this.tongtiendien;
	}

	@Override
	public void setTongTienDien(double tongtiendien) {
		// TODO Auto-generated method stub
		this.tongtiendien=tongtiendien;
	}

	@Override
	public List<IErptiendien> GetListiendien() {
		// TODO Auto-generated method stub
		return this.listtiendien;
	}

	@Override
	public void setListiendien(List<IErptiendien> listtiendien) {
		// TODO Auto-generated method stub
		 this.listtiendien=listtiendien;
	}

	@Override
	public void Init() {
		// TODO Auto-generated method stub
		try{
			String query="";
			if(this.Id.length() >0){
				query="SELECT THANG,NAM,TONGTIENDIEN,TONGTIENNUOC,TRANGTHAI FROM ERP_TINHGIADONGLUC WHERE PK_SEQ="+this.Id;
				
				ResultSet rs=db.get(query);
				if(rs.next()){
					this.thang=rs.getInt("thang");
					this.nam=rs.getInt("nam");
					this.tongtiendien=rs.getDouble("tongtiendien");
					this.tongtiennuoc=rs.getDouble("TONGTIENNUOC");
					this.trangthai=rs.getString("trangthai");
				}
				rs.close();
			}
			
			
			this.tongtiendien=10000000;
			this.tongtiennuoc=3000000;
			
			if(this.listtiendien==null ||this.listtiendien.size() ==0){
			 query=" SELECT PK_SEQ,MA,TEN, ISNULL(GIADIEN.DONGIA,0) AS DONGIA,ISNULL(GIADIEN.SOLUONG,0) AS SOLUONG ," +
						 " ISNULL(GIADIEN.THANHTIEN,0) AS THANHTIEN "+
						 " FROM ERP_DONGHODIEN DHD "+
						 " LEFT JOIN ERP_TINHGIATHANHDIEN GIADIEN ON GIADIEN.DONGHODIEN_FK=DHD.PK_SEQ " +
						 " AND GIADIEN.TINHGIADONGLUC_FK="+(this.Id.length() >0?this.Id:"0" ) ;
			ResultSet rs=db.get(query);
			List<IErptiendien> listtd= new ArrayList<IErptiendien>();
			while(rs.next()){
					IErptiendien tiendien=new ErpTiendien();
					tiendien.setDongia(rs.getDouble("dongia"));
					tiendien.setIdDongHoDien(rs.getString("PK_SEQ"));
					tiendien.setTenDongHoDien(rs.getString("TEN"));
					tiendien.setSoLuong(rs.getDouble("soluong"));
					tiendien.setThanhtien(rs.getDouble("THANHTIEN"));
					listtd.add(tiendien);
				}
			rs.close();
			this.listtiendien= listtd;
			}
			if(this.listtiennuoc==null ||this.listtiennuoc.size() ==0){
				 query=		 " SELECT PK_SEQ,MA,TEN, ISNULL(GIADIEN.DONGIA,0) AS DONGIA,ISNULL(GIADIEN.SOLUONG,0) AS SOLUONG ," +
							 " ISNULL(GIADIEN.THANHTIEN,0) AS THANHTIEN "+
							 " FROM ERP_DONGHONUOC DHD "+
							 " LEFT JOIN ERP_TINHGIATHANHNUOC GIADIEN ON GIADIEN.DONGHONUOC_FK=DHD.PK_SEQ AND GIADIEN.TINHGIADONGLUC_FK="+(this.Id.length() >0?this.Id:"0" ) ;
				ResultSet rs=db.get(query);
				List<IErptiennuoc> listtd= new ArrayList<IErptiennuoc>();
				while(rs.next()){
					IErptiennuoc tiendien=new ErpTiennuoc();
						tiendien.setDongia(rs.getDouble("dongia"));
						tiendien.setIdDongHoNuoc(rs.getString("PK_SEQ"));
						tiendien.setTenDongHoNuoc(rs.getString("TEN"));
						tiendien.setSoLuong(rs.getDouble("soluong"));
						tiendien.setThanhtien(rs.getDouble("THANHTIEN"));
						listtd.add(tiendien);
					}
				rs.close();
				this.listtiennuoc=listtd;
				}		
			
			if(this.listnuocda==null ||this.listnuocda.size() ==0){
				 query=		 " SELECT DHD.PK_SEQ,MANHAMAY AS MA,TENNHAMAY AS TEN, ISNULL(GIADIEN.DONGIA,350) AS DONGIA, " +
				 			 " ISNULL(GIADIEN.SOLUONG,0) AS SOLUONG ," +
							 " ISNULL(GIADIEN.THANHTIEN,0) AS THANHTIEN "+
							 " FROM ERP_NHAMAY DHD "+
							 " LEFT JOIN ERP_TINHGIADONGLUC_NHAPNUOCDA GIADIEN ON GIADIEN.NHAMAY_FK=DHD.PK_SEQ AND GIADIEN.TINHGIADONGLUC_FK="+(this.Id.length() >0?this.Id:"0" ) ;
				ResultSet rs=db.get(query);
				List<IErpNuocda> listnd= new ArrayList<IErpNuocda>();
				while(rs.next()){
					IErpNuocda tiendien=new ErpNuocda();
						tiendien.setDongia(rs.getDouble("dongia"));
						tiendien.setIdNhamay(rs.getString("PK_SEQ"));
						tiendien.setTenNhamay(rs.getString("TEN"));
						tiendien.setSoLuong(rs.getDouble("soluong"));
						tiendien.setThanhtien(rs.getDouble("THANHTIEN"));
						listnd.add(tiendien);
					}
				rs.close();
				this.listnuocda=listnd;
				}		
			
			
		}catch(Exception er){
			er.printStackTrace();
		}
	}

	@Override
	public String getUserId() {
		// TODO Auto-generated method stub
		return this.UserId;
	}

	@Override
	public void setUserId(String UserId) {
		// TODO Auto-generated method stub
		this.UserId=UserId;
	}

	@Override
	public String getMsg() {
		// TODO Auto-generated method stub
		return  this.msg;
	}

	@Override
	public void setMsg(String msg) {
		// TODO Auto-generated method stub
		this.msg=msg;
	}

	@Override
	public void DbClose() {
		// TODO Auto-generated method stub
		try{
			db.shutDown();
		}catch(Exception er){
			er.printStackTrace();
		}
	}
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	@Override
	
	public boolean Save() {
		// TODO Auto-generated method stub
		try{
			
			// kiểm tra thông tin có hợp lệ chưa
			if(!this.kiemtrahople()){
				return false;
			}
			db.getConnection().setAutoCommit(false);
			
			String query="insert into ERP_TINHGIADONGLUC(THANG,NAM  ,	NGUOITAO  ,	NGAYTAO  ,	NGUOISUA  ,	NGAYSUA ,	TONGTIENDIEN ,TRANGTHAI)  " +
					" values ("+this.thang+","+this.nam+","+this.UserId+",'"+this.getDateTime()+"',"+this.UserId+",'"+this.getDateTime()+"',"+this.tongtiendien+",'0')";
			
			if(!db.update(query)){
				this.msg="Không thể cập nhật dữ liệu, lỗi dòng lệnh :"+query;
				db.update("rollback");
				return false;
			}
			query = "select SCOPE_IDENTITY() as idcr";
			ResultSet rsDh = db.get(query);	
			
			rsDh.next();
			String idcr = rsDh.getString("idcr");
		    rsDh.close();
		    double tongsoluongdien=0;
		    
		    int bien=0;
		    for(int i=0;i<this.listtiendien.size();i++){
				IErptiendien tiendien=listtiendien.get(i);
				if(tiendien.getSoLuong() >0){
					tongsoluongdien=tongsoluongdien + tiendien.getSoLuong();
					bien=i;
				}
				
			}
		    
		    
		    NumberFormat formatter = new DecimalFormat("#######.##");
		 
		    double dongiabinhquan= Double.parseDouble(formatter.format(this.tongtiendien/tongsoluongdien));

		    
		    double totalthanhtien=0;
			for(int i=0;i<this.listtiendien.size();i++){
				IErptiendien tiendien=listtiendien.get(i);
				double thanhtien= Math.round(tiendien.getSoLuong()* dongiabinhquan);
				if(i==bien){
					thanhtien =this.tongtiendien-totalthanhtien;
				}else{
					totalthanhtien=totalthanhtien+thanhtien;
				}
			
				query=  " INSERT INTO ERP_TINHGIATHANHDIEN (TINHGIADONGLUC_FK,DONGHODIEN_FK,SOLUONG,DONGIA,THANHTIEN) VALUES" +
						" ("+idcr+","+tiendien.getIdDongHoDien()+","+tiendien.getSoLuong()+","+dongiabinhquan+","+thanhtien+")";

				if(!db.update(query)){
					this.msg="Không thể cập nhật dữ liệu, lỗi dòng lệnh :"+query;
					db.update("rollback");
					return false;
				}
			}
		    
			
			// tính giá của nước
			
			double tongsoluongnuoc=0;
		    
		     bien=0;
		    for(int i=0;i<this.listtiennuoc.size();i++){
				IErptiennuoc tiendien=listtiennuoc.get(i);
				if(tiendien.getSoLuong() >0){
					tongsoluongnuoc=tongsoluongnuoc + tiendien.getSoLuong();
					bien=i;
				}
				
			}
		    dongiabinhquan= Double.parseDouble(formatter.format(this.tongtiennuoc/tongsoluongnuoc));
 		    totalthanhtien=0;
			for(int i=0;i<this.listtiennuoc.size();i++){
				IErptiennuoc tiendien=listtiennuoc.get(i);
				double thanhtien= Math.round(tiendien.getSoLuong()* dongiabinhquan);
				if(i==bien){
					thanhtien =this.tongtiennuoc-totalthanhtien;
				}else{
					totalthanhtien=totalthanhtien+thanhtien;
				}
			
				query=  " INSERT INTO ERP_TINHGIATHANHNUOC (TINHGIADONGLUC_FK,DONGHONUOC_FK,SOLUONG,DONGIA,THANHTIEN) VALUES" +
						" ("+idcr+","+tiendien.getIdDongHoNuoc()+","+tiendien.getSoLuong()+","+dongiabinhquan+","+thanhtien+")";

				if(!db.update(query)){
					this.msg="Không thể cập nhật dữ liệu, lỗi dòng lệnh :"+query;
					db.update("rollback");
					return false;
				}
				
				
			}
		    
			
			for(int i=0;i<this.listnuocda.size();i++){
				IErpNuocda nuocda=listnuocda.get(i);
				double thanhtien= Math.round(nuocda.getSoLuong()* nuocda.getDongia());
				 
			
				query=  " INSERT INTO ERP_TINHGIADONGLUC_NHAPNUOCDA(TINHGIADONGLUC_FK,NHAMAY_FK,SOLUONG,DONGIA,THANHTIEN)  VALUES" +
						" ("+idcr+","+nuocda.getIdNhamay()+","+nuocda.getSoLuong()+","+nuocda.getDongia()+","+thanhtien+")";

				if(!db.update(query)){
					this.msg="Không thể cập nhật dữ liệu, lỗi dòng lệnh :"+query;
					db.update("rollback");
					return false;
				}
				
				
			}
		    
			
			
			db.getConnection().setAutoCommit(false);
			db.getConnection().commit();
			
		}catch(Exception err){
			db.update("rollback");
			err.printStackTrace();
			return false;
			
		}
		return true;
	}
	private boolean kiemtrahople() throws Exception {
		// TODO Auto-generated method stub
		if(this.thang < 1){
			this.msg="Vui lòng chọn tháng";
			return false;
		}
		if(this.nam < 1){
			this.msg="Vui lòng chọn năm";
			return false;
		}
		if(this.tongtiendien <= 0){
			this.msg="Chưa có tổng số tiền điện trong tháng.Vui lòng kiểm tra lại";
			return false;
		}
		 double tongsoluongdien=0;
		   
		    for(int i=0;i<this.listtiendien.size();i++){
				IErptiendien tiendien=listtiendien.get(i);
				if(tiendien.getSoLuong() >0){
					tongsoluongdien=tongsoluongdien + tiendien.getSoLuong();
					 
				}
				
			}
		    if(tongsoluongdien <=0){
		    	this.msg=" Chưa có tổng số lượng điện trong tháng của các đồng hồ điện. Vui lòng kiểm tra lại";
				return false;
		    }
		    double tongsoluongnuoc=0;
		    for(int i=0;i<this.listtiennuoc.size();i++){
				IErptiennuoc tiendien=listtiennuoc.get(i);
				if(tiendien.getSoLuong() >0){
					tongsoluongnuoc=tongsoluongnuoc + tiendien.getSoLuong();
				
				}
				
			}
		    
		    if(tongsoluongnuoc <=0){
		    	this.msg=" Chưa có tổng số lượng nước trong tháng của các đồng hồ nước. Vui lòng kiểm tra lại";
				return false;
		    }
		    
		String query=" SELECT PK_SEQ FROM ERP_TINHGIADONGLUC WHERE THANG="+this.thang+" AND NAM ="+this.nam+" AND TRANGTHAI <>2 ";
		if(this.Id.length() > 0){
			query+=" and pk_seq <> "+this.Id;
		}
		ResultSet rs=db.get(query);
		if(rs.next()){
			rs.close();
			this.msg="Động lực đã được tính trong thời gian này,vui lòng hủy động lực cũ để tạo lại ";
			return false;
		}
		rs.close();
		
		return true;
	}
	@Override
	public String getTrangthai() {
		// TODO Auto-generated method stub
		return this.trangthai;
	}
	@Override
	public void setTrangthai(String Trangthai) {
		// TODO Auto-generated method stub
		this.trangthai=Trangthai;
	}
	@Override
	public double getTongTienNuoc() {
		// TODO Auto-generated method stub
		return this.tongtiennuoc;
	}
	@Override
	public void setTongTienNuoc(double tongtiennuoc) {
		// TODO Auto-generated method stub
		this.tongtiennuoc=tongtiennuoc;
	}
	@Override
	public List<IErptiennuoc> GetListiennuoc() {
		// TODO Auto-generated method stub
		return this.listtiennuoc;
	}
	@Override
	public void setListiennuoc(List<IErptiennuoc> listtiennuoc) {
		// TODO Auto-generated method stub
		this.listtiennuoc= listtiennuoc;
	}
	@Override
	public boolean Edit() {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		try{
			
			// kiểm tra thông tin có hợp lệ chưa
			if(!this.kiemtrahople()){
				return false;
			}
			db.getConnection().setAutoCommit(false);
			
			String query="  update ERP_TINHGIADONGLUC set THANG ="+this.thang+",NAM ="+this.nam+" ,	 " +
						 "	NGUOISUA ="+this.UserId+" ,	NGAYSUA='"+this.getDateTime()+"' ," +
						 "	TONGTIENDIEN="+this.tongtiendien+" , " +
						 "  TONGTIENNUOC="+this.tongtiennuoc+" where pk_seq="+this.Id;
			
			
			if(!db.update(query)){
				this.msg="Không thể cập nhật dữ liệu, lỗi dòng lệnh :"+query;
				db.update("rollback");
				return false;
			}
			
			query="delete ERP_TINHGIATHANHDIEN where TINHGIADONGLUC_FK ="+this.Id;
			if(!db.update(query)){
				this.msg="Không thể cập nhật dữ liệu, lỗi dòng lệnh :"+query;
				db.update("rollback");
				return false;
			}
			query="delete ERP_TINHGIATHANHNUOC where TINHGIADONGLUC_FK ="+this.Id;
			if(!db.update(query)){
				this.msg="Không thể cập nhật dữ liệu, lỗi dòng lệnh :"+query;
				db.update("rollback");
				return false;
			}
			
			query="delete ERP_TINHGIADONGLUC_NHAPNUOCDA  where TINHGIADONGLUC_FK ="+this.Id;
			if(!db.update(query)){
				this.msg="Không thể cập nhật dữ liệu, lỗi dòng lệnh :"+query;
				db.update("rollback");
				return false;
			}
			
			
		    double tongsoluongdien=0;
		    
		    int bien=0;
		    for(int i=0;i<this.listtiendien.size();i++){
				IErptiendien tiendien=listtiendien.get(i);
				if(tiendien.getSoLuong() >0){
					tongsoluongdien=tongsoluongdien + tiendien.getSoLuong();
					bien=i;
				}
				
			}
		    
		    
		    NumberFormat formatter = new DecimalFormat("#######.##");
		 
		    double dongiabinhquan= Double.parseDouble(formatter.format(this.tongtiendien/tongsoluongdien));

		    
		    double totalthanhtien=0;
			for(int i=0;i<this.listtiendien.size();i++){
				IErptiendien tiendien=listtiendien.get(i);
				double thanhtien= Math.round(tiendien.getSoLuong()* dongiabinhquan);
				if(i==bien){
					thanhtien =this.tongtiendien-totalthanhtien;
				}else{
					totalthanhtien=totalthanhtien+thanhtien;
				}
			
				query=  " INSERT INTO ERP_TINHGIATHANHDIEN (TINHGIADONGLUC_FK,DONGHODIEN_FK,SOLUONG,DONGIA,THANHTIEN) VALUES" +
						" ("+this.Id+","+tiendien.getIdDongHoDien()+","+tiendien.getSoLuong()+","+dongiabinhquan+","+thanhtien+")";

				if(!db.update(query)){
					this.msg="Không thể cập nhật dữ liệu, lỗi dòng lệnh :"+query;
					db.update("rollback");
					return false;
				}
			}
		    
			
			// tính giá của nước
			
			double tongsoluongnuoc=0;
		    
		     bien=0;
		    for(int i=0;i<this.listtiennuoc.size();i++){
				IErptiennuoc tiendien=listtiennuoc.get(i);
				if(tiendien.getSoLuong() >0){
					tongsoluongnuoc=tongsoluongnuoc + tiendien.getSoLuong();
					bien=i;
				}
				
			}
		    dongiabinhquan= Double.parseDouble(formatter.format(this.tongtiennuoc/tongsoluongnuoc));
 		    totalthanhtien=0;
			for(int i=0;i<this.listtiennuoc.size();i++){
				IErptiennuoc tiendien=listtiennuoc.get(i);
				double thanhtien= Math.round(tiendien.getSoLuong()* dongiabinhquan);
				if(i==bien){
					thanhtien =this.tongtiennuoc-totalthanhtien;
				}else{
					totalthanhtien=totalthanhtien+thanhtien;
				}
			
				query=  " INSERT INTO ERP_TINHGIATHANHNUOC (TINHGIADONGLUC_FK,DONGHONUOC_FK,SOLUONG,DONGIA,THANHTIEN) VALUES" +
						" ("+this.Id+","+tiendien.getIdDongHoNuoc()+","+tiendien.getSoLuong()+","+dongiabinhquan+","+thanhtien+")";

				if(!db.update(query)){
					this.msg="Không thể cập nhật dữ liệu, lỗi dòng lệnh :"+query;
					db.update("rollback");
					return false;
				}
			}
		    
			
			for(int i=0;i<this.listnuocda.size();i++){
				IErpNuocda nuocda=listnuocda.get(i);
				double thanhtien= Math.round(nuocda.getSoLuong()* nuocda.getDongia());
				 
			
				query=  " INSERT INTO ERP_TINHGIADONGLUC_NHAPNUOCDA(TINHGIADONGLUC_FK,NHAMAY_FK,SOLUONG,DONGIA,THANHTIEN)  VALUES" +
						" ("+this.Id+","+nuocda.getIdNhamay()+","+nuocda.getSoLuong()+","+nuocda.getDongia()+","+thanhtien+")";

				if(!db.update(query)){
					this.msg="Không thể cập nhật dữ liệu, lỗi dòng lệnh :"+query;
					db.update("rollback");
					return false;
				}
				
				
			}
		    
			db.getConnection().setAutoCommit(false);
			db.getConnection().commit();
			
		}catch(Exception err){
			db.update("rollback");
			err.printStackTrace();
			return false;
			
		}
		return true;
	
	}
	@Override
	public List<IErpNuocda> GetLisnuocda() {
		// TODO Auto-generated method stub
		return this.listnuocda;
	}
	@Override
	public void setListnuocda(List<IErpNuocda> listnuocda) {
		// TODO Auto-generated method stub
		this.listnuocda= listnuocda;
	}
}
