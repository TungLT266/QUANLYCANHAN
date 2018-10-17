package geso.traphaco.center.beans.chitieunpp.imp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import geso.traphaco.center.beans.chitieunpp.IChiTieuNhaPP;
import geso.traphaco.center.beans.chitieunpp.imp.ChiTieuDDKD;
import geso.traphaco.center.beans.chitieunpp.imp.ChiTieuNhaPP;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;

public class ChiTieuNhaPP  extends Phan_Trang implements IChiTieuNhaPP, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String vungId,khuvucId,nppId;
	ResultSet vungRs,khuvucRs,nppRs;
	
	double Chitiet=0;
	String UserID;
	double Id;
	int Thang;
	int Nam;
	String NgayTao;
	String NguoiTao;
	String NguoiSua;
	String NgaySua;
	String NgayKetThuc;
	String KhuVuc;
	String DienGiai;
	String TenKhuVuc;
	String Message;
	double ChiTieu;
	String NhappTen;
	String NhaPhanPhoiId;
	String TenDVKD;
	String DVKDId;
	String SoNgayLamViec;
	String KenhId;
	String TrangThai;
	ResultSet rsChiTieu;
	ResultSet rsChiTieu_ddkd;
	String[] NhomSP;
	String[] NhomSPId;
	List<ChiTieuDDKD> listchitieuddkd=new ArrayList<ChiTieuDDKD>();
	 List<ChiTieuNhaPP> listchitieu =new ArrayList<ChiTieuNhaPP>();
	dbutils db;
	ResultSet rsdvkd;
	@Override
	public ResultSet getrsdvkd() {
		
		return this.rsdvkd;
	}

	@Override
	public void setrsdvkd(ResultSet rsdvkd) {
		
		this.rsdvkd=rsdvkd;
	}
	 public ChiTieuNhaPP(){
		 this.NgayKetThuc="";
		 this.Message="";
		 this.DienGiai="";
		this.SoNgayLamViec="";
		this.TrangThai="";
		this.khuvucId="";
		this.nppId="";
		this.vungId="";
		this.db = new dbutils();
	 }
	 public ChiTieuNhaPP(String id){	 
		 if(db == null)
			 this.db = new dbutils();

			String  sql_getdata="SELECT    C.Kenh_fk,c.trangthai, C.PK_SEQ, C.THANG, C.NAM, C.NHAPP_FK, K.TEN AS TENNPP,C.SONGAYLAMVIEC, C.CHITIEU,C.DIENGIAI, C.NGAYKETTHUC,C.DVKD_FK,d.donvikinhdoanh, C.NGAYTAO, C.NGAYSUA,NT.TEN AS NGUOITAO, "+
			   " NS.TEN AS NGUOISUA FROM dbo.CHITIEUNPP AS C INNER JOIN dbo.NHAPHANPHOI AS K ON C.NHAPP_FK = K.PK_SEQ INNER JOIN  "+
			   " dbo.NHANVIEN AS NT ON C.NGUOITAO = NT.PK_SEQ INNER JOIN dbo.NHANVIEN AS NS ON C.NGUOISUA = NS.PK_SEQ inner join donvikinhdoanh d on d.pk_seq=C.DVKD_FK where c.trangthai!='2' "+
			   " and c.pk_seq="+ id;
	
			  System.out.println("SQL : "+ sql_getdata);
			ResultSet rs_chitieunpp = null;
			ResultSet rsChiTieu = null;
			try{
				rsChiTieu =	db.get(sql_getdata);
				if(rsChiTieu!=null){
					while(rsChiTieu.next()){
					
						this.setID(rsChiTieu.getDouble("PK_SEQ"));
						this.setThang(rsChiTieu.getInt("THANG"));
						this.setNam(rsChiTieu.getInt("NAM"));
						this.setChitieu(rsChiTieu.getDouble("CHITIEU"));
						this.setNgayKetThuc(rsChiTieu.getString("NGAYKETTHUC"));
						this.setNgayTao(rsChiTieu.getString("NGAYTAO"));
						this.setNgaySua(rsChiTieu.getString("NGAYSUA"));
						this.setDienGiai(rsChiTieu.getString("DIENGIAI"));
						this.setNhappId(rsChiTieu.getString("NHAPP_FK"));
						this.setNguoiTao(rsChiTieu.getString("NGUOITAO"));
						this.setNguoiSua(rsChiTieu.getString("NGUOISUA"));
					    this.setDVDKId(rsChiTieu.getString("dvkd_fk"));
					    this.setTenDVKD(rsChiTieu.getString("donvikinhdoanh"));
					    this.setSoNgayLamViec(rsChiTieu.getString("songaylamviec"));
					    this.setKenhId(rsChiTieu.getString("kenh_fk"));
					    this.setTrangThai(rsChiTieu.getString("trangthai"));
						this.setMessage("");
						this.NhappTen= rsChiTieu.getString("TENNPP");
						
				    
					}
				}
				String sql=" select nsp.PK_SEQ,a.CHITIEU,nsp.TEN  "+
					" from CHITIEUNPP_NHOMSP a   "+
					" inner join NHOMSANPHAM nsp on nsp.PK_SEQ=nhomsp_fk "+
					" where nhomsp_fk <200000 AND  CHITIEUNPP_FK="+this.Id + 
					" UNION ALL  select nsp.PK_SEQ,a.CHITIEU,nsp.DIENGIAI AS TEN  "+
					" from CHITIEUNPP_NHOMSP a   "+
					" inner join NHOMFOCUS nsp on nsp.PK_SEQ=nhomsp_fk "+
					" where nhomsp_fk > 200000 AND  CHITIEUNPP_FK="+this.Id ;
				
				
				ResultSet rs=this.db.get(sql);
				String[] nhom =new String[10];
				String[] nhomid =new String[10];
				
				int i=0;
				String strselect="";
				String strngoac="[0]";
				String chuoikhong="";
				String chuoiselectsum="";
				
				if(rs!=null){
					while (rs.next()){
						nhomid[i]=rs.getString("pk_seq");
						nhom[i]=rs.getString("ten")+"("+rs.getString("chitieu")+")";
						if(i==0){
							chuoiselectsum=", sum(["+rs.getString("pk_seq")+"]) as CT"+rs.getString("pk_seq")+"";
							strselect=" , isnull(["+rs.getString("pk_seq")+"],0) as  ["+rs.getString("pk_seq")+"]";
							strngoac="[0] ,["+rs.getString("pk_seq")+"]";
							chuoikhong=",0";
						}else{
							chuoiselectsum=chuoiselectsum+ ", sum(["+rs.getString("pk_seq")+"]) as CT"+rs.getString("pk_seq")+"";

							strselect=strselect+" , isnull(["+rs.getString("pk_seq")+"],0) as  ["+rs.getString("pk_seq")+"]";
							strngoac=strngoac+ ",["+rs.getString("pk_seq")+"]";
							chuoikhong=chuoikhong+",0";
						}
						i++;
					}
				}
				this.NhomSP=nhom;
				this.NhomSPId=nhomid;
				
				
				//set list chi tieu nha pp
				String sql_chitieunpp="  select ddkd_fk,ten,SUM(chitieu) as chitieu,SUM(sodonhang) as sodonhang,SUM(sku) as sku "+chuoiselectsum+"  from   "+  
									  "  (     "+  
									  "  select b.ddkd_fk,chitieu,ten,sodonhang,sku "+strselect+" from chitieunpp_ddkd b  "+  
									  "  left join"+  
									  "  ( "+  
									  "  select A.CHITIEUNPP_FK,a.NHOMSANPHAM_FK as nhomsp_fk,a.DDKD_FK,CHITIEU from CHITIEUNPP_DDKD_NHOMSP a where a.CHITIEUNPP_FK="+this.Id  +
									  "  ) P"+  
									  "  PIVOT "+  
									  "  ( "+  
									  "  SUM(CHITIEU) "+  
									  "  FOR nhomsp_fk IN "+  
									  "  ("+strngoac+") "+  
									  "  ) AS CTNHOM ON b.CHITIEUNPP_FK=CTNHOM.CHITIEUNPP_FK AND b.DDKD_FK=CTNHOM.DDKD_FK"+  
									  "  "+  
									  "  inner join daidienkinhdoanh a on a.pk_seq=b.DDKD_FK where b.CHITIEUNPP_FK=  "+this.Id +"  "+  
									  "  union all     	 select PK_SEQ,0,a.ten,0,0"+chuoikhong+" from daidienkinhdoanh  a     "+  
									  "  where NPP_FK= "+this.getNhappId()+" and a.TRANGTHAI='1'   "+  
									  "  )as b     group by ddkd_fk,ten ";
				
				System.out.println("sql_chitieunpp : "+sql_chitieunpp);
				this.rsChiTieu_ddkd=db.get(sql_chitieunpp);
				
				/*rs_chitieunpp=db.get(sql_chitieunpp);
				if(rs_chitieunpp!=null){
					while(rs_chitieunpp.next()){
						ChiTieuDDKD ct=new ChiTieuDDKD();
					
						ct.setDDKDId(rs_chitieunpp.getString("ddkd_fk"));
						ct.setDDKDTen(rs_chitieunpp.getString("ten"));
						ct.setChiTieu(rs_chitieunpp.getDouble("chitieu"));
						ct.setSoDonHang(rs_chitieunpp.getString("sodonhang"));
						ct.setSoSku(rs_chitieunpp.getString("sku"));
						this.listchitieuddkd.add(ct);
					}
				}*/
				
			}catch(Exception er){
				er.printStackTrace();
				this.setMessage("Error :" + er.toString());
				//System.out.println("Error Class name : CHITIEUNPP.JAVA- LINE 216 :STRING SQL" + er.toString());
			}
			finally{try {
				if(rs_chitieunpp != null)
					rs_chitieunpp.close();
				if(rsChiTieu != null)
					rsChiTieu.close();
			} catch (Exception e) {
				// TODO: handle exception
			}}
	 }
	 

	public void setChitieu(double chitieu) {
		
	 this.ChiTieu=chitieu;
	}


	public double getChitieu() {
		
		return this.ChiTieu;
	}


	public void setID(double id) {
		
	 this.Id=id;	
	}


	public double getID() {
		
		return Id;
	}


	public void setThang(int thang) {
		
		this.Thang=thang;
	}


	public int getThang() {
		
		return this.Thang;
	}


	public void setNam(int nam) {
		
		this.Nam=nam;
	}


	public int getNam() {
		
		return this.Nam;
	}


	public void setKhuVucID(String khuvucid) {
		
		this.KhuVuc=khuvucid;
	}


	public String getKhuVucID() {
		
		return this.KhuVuc;
	}


	public void setNgayKetThuc(String ngayketthuc) {
		
		this.NgayKetThuc=ngayketthuc;
	}


	public String getNgayKetThuc() {
		
		return this.NgayKetThuc;
	}


	public void setNgayTao(String ngaytao) {
		
		this.NgayTao =ngaytao;
	}


	public String getNgayTao() {
		
		return this.NgayTao;
	}

	public void setNgaySua(String ngaysua) {
		
		this.NgaySua=ngaysua;
	}

	public String getNgaySua() {
		
		return this.NgaySua;
	}

	public void setDienGiai(String diengiai) {
		
		this.DienGiai=diengiai;
	}

	public String getDienGiai() {
		
		return this.DienGiai;
	}

	public boolean EditChiTieu(HttpServletRequest request) {
		
		try{
		
			db.getConnection().setAutoCommit(false);
			
			
			String sql="update CHITIEUNPP set NGAYSUA='"+this.NgaySua+"',NGUOISUA='"+this.NguoiSua+"' where pk_seq="+ this.Id;
		
			if(!db.update(sql)){
				
				db.update("rollback");
				return false;
			}
		    sql="delete chitieunpp_ddkd where chitieunpp_fk="+ this.Id;
			if(!db.update(sql)){
				db.update("rollback");
				return false;
			}
			sql="delete chitieunpp_ddkd_nhomsp where chitieunpp_fk=  "+this.Id;
			if(!db.update(sql)){
				db.update("rollback");
				return false;
			}
	/*		 sql=" select nsp.PK_SEQ,a.CHITIEU,nsp.TEN  "+
			" from CHITIEUNPP_NHOMSP a   "+
			" inner join NHOMSANPHAM nsp on nsp.PK_SEQ=nhomsp_fk "+
			" where CHITIEUNPP_FK="+this.Id; */
			 
			 
			   sql=" select nsp.PK_SEQ,a.CHITIEU,nsp.TEN  "+
				" from CHITIEUNPP_NHOMSP a   "+
				" inner join NHOMSANPHAM nsp on nsp.PK_SEQ=nhomsp_fk "+
				" where nhomsp_fk <200000 AND  CHITIEUNPP_FK="+this.Id + 
				" UNION ALL  select nsp.PK_SEQ,a.CHITIEU,nsp.DIENGIAI AS TEN  "+
				" from CHITIEUNPP_NHOMSP a   "+
				" inner join NHOMFOCUS nsp on nsp.PK_SEQ=nhomsp_fk "+
				" where nhomsp_fk > 200000 AND  CHITIEUNPP_FK="+this.Id ;
			 
			 
			ResultSet rs=this.db.get(sql);
			String[] nhom =new String[20];
			String[] nhomid =new String[20];
			
			int i=0;
			
			if(rs!=null){
			while (rs.next()){
				nhomid[i]=rs.getString("pk_seq");
				nhom[i]=rs.getString("ten") +"("+rs.getString("chitieu")+")";
				
				i++;
			}
			rs.close();
		}
		this.NhomSP=nhom;
		this.NhomSPId=nhomid;
		rs.close();
		int count=0;
		if(this.listchitieuddkd!=null){
			while (count <this.listchitieuddkd.size()){
				ChiTieuDDKD ddkd=new ChiTieuDDKD();
				ddkd=this.listchitieuddkd.get(count);
				String sql_insertdetail="insert into chitieunpp_ddkd(chitieunpp_fk,ddkd_fk,chitieu,sodonhang,sku) values("+this.getID()+","+ddkd.getDDKDId()+","+ddkd.getChiTieu()+",'"+ddkd.getSoDonHang()+"','"+ddkd.getSoSku()+"')";
				if(!db.update(sql_insertdetail)){
					db.update("rollback");
					return false;
				}
				
				for(int j=0;j<nhomid.length;j++){
					double chitieu=0;
					
					if(nhomid[j]!=null){
						
						try{
							
					
							chitieu=Double.parseDouble(request.getParameter(ddkd.getDDKDId().trim()+ nhomid[j].trim()));
						}catch(Exception er){
							er.printStackTrace();
						}
						sql="insert into chitieunpp_ddkd_nhomsp (chitieunpp_fk,ddkd_fk,nhomsanpham_fk,chitieu) " +
								" values ("+this.Id+","+ddkd.getDDKDId().trim()+","+nhomid[j]+","+chitieu+") ";
						
						if(!db.update(sql)){
							db.update("rollback");
							return false;
						}
					}
				}
				
				
				
				
				
				count++;
			}
		}
		
		sql=   " select * from ( "+ 
			   " select SUM(chitieu) as ct1 ,nhomsanpham_fk ,(select CHITIEU from CHITIEUNPP_NHOMSP a "+  
			   " where CHITIEUNPP_FK="+this.Id+" and b.NHOMSANPHAM_FK=a.NHOMSP_FK) as ct2 "+
			   " from CHITIEUNPP_DDKD_NHOMSP b where CHITIEUNPP_FK= "+this.Id + 
			   " group by nhomsanpham_fk "+  
			   " ) c where c.ct1<> c.ct2";
		
		 rs=db.get(sql);
		if(rs.next()) {
			db.update("rollback");
			this.Message="vui lòng nhập lại chỉ tiêu nhóm,tổng chi tiêu nhóm của các Trình dược viên phải bằng chỉ tiêu nhóm của nhà phân phối";
			return false;
		}
		rs.close();
		
		sql=   " select pk_seq from chitieunpp b  where PK_SEQ="+this.Id +  
				" and  CHITIEU  - (select SUM(chitieu) from CHITIEUNPP_DDKD  a  where  a.CHITIEUNPP_FK =b.PK_SEQ  ) !=0 " ; 
	
			rs=db.get(sql);
		if(rs.next()) {
			db.update("rollback");
			this.Message="vui lòng nhập lại chỉ tiêu nhóm,tổng chi tiêu nhóm của các Trình dược viên phải bằng chỉ tiêu nhóm của nhà phân phối";
			return false;
		}
		rs.close();
		
		
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			this.Message=er.toString();
			db.update("rollback");
			return false;
		}
		return true;
	}
 

	
	public void setUserId(String userid) {
		
		this.UserID=userid;
		 
	}


	public String getUserId() {
		
		return this.UserID;
	}


	
	
	public void setListChiTieu(String sql) {	
		Utility util=new Utility();
		
				
		String  sql_getdata=   "  SELECT    C.KENH_FK,kbh.ten as kenh,C.TRANGTHAI, C.PK_SEQ, C.THANG, C.NAM, C.NHAPP_FK, K.TEN AS TENNPP, C.CHITIEU,C.DIENGIAI, C.NGAYKETTHUC,C.DVKD_FK,d.donvikinhdoanh,C.SONGAYLAMVIEC, C.NGAYTAO, C.NGAYSUA,NT.TEN AS NGUOITAO, "+
							   " NS.TEN AS NGUOISUA FROM dbo.CHITIEUNPP AS C " +
							   " INNER JOIN dbo.NHAPHANPHOI AS K ON C.NHAPP_FK = K.PK_SEQ INNER JOIN  "+
							   " dbo.NHANVIEN AS NT ON C.NGUOITAO = NT.PK_SEQ  " +
							   " INNER JOIN dbo.NHANVIEN AS NS ON C.NGUOISUA = NS.PK_SEQ " +
							   " inner join donvikinhdoanh d on d.pk_seq=C.DVKD_FK " +
							   "  inner join kenhbanhang kbh on kbh.pk_seq=c.kenh_fk where c.trangthai!='2' and k.trangthai=1 "+
							   " and NHAPP_FK in "+util.quyen_npp(this.UserID);
		
			if(sql!="")
			{ 
				sql_getdata=sql;
			}  
			  rsChiTieu=	createSplittingData(50, 10, "PK_SEQ desc", sql_getdata); 
			  
			  String  query="select distinct kv.pk_Seq as kvid, kv.ten as  kv from phamvihoatdong inner join " +
						 " nhaphanphoi npp on npp.pk_seq=npp_fk  "+ 
						 " inner join khuvuc kv on kv.pk_seq=npp.khuvuc_fk " +
						 " where nhanvien_fk= '"+ this.UserID +"'";
					this.khuvucRs = this.db.get(query);
			  
			   query=
						"SELECT A.PK_SEQ AS NPPID, A.TEN AS NPPTEN,A.MA AS NPPMA, B.TEN AS KVTEN "+ 
						"FROM  "+
						" NHAPHANPHOI A INNER JOIN KHUVUC B ON A.KHUVUC_FK = B.PK_SEQ "+ 
						"WHERE A.TRANGTHAI = '1' AND A.SITECODE = A.CONVSITECODE ";
					if(this.khuvucId.length()>0)
					{
						query += " and A.khuvuc_fk ='"+this.khuvucId+"' ";
					}
					query+= "order by b.ten,a.ten asc ";
					this.nppRs=this.db.get(query);
					query="select PK_SEQ,TEN from vung ";
		this.vungRs=this.db.get(query);
	}

	public void setTenKhuVuc(String tenkhuvuc) {
		
		this.TenKhuVuc=tenkhuvuc;
	}

	public String getTenKhuVuc() {
		
		return this.TenKhuVuc;
	}

	public void setNguoiTao(String nguoitao) {
		
		this.NguoiTao=nguoitao;		
	}

	public String getNguoiTao() {
		
		return this.NguoiTao;
	}

	public void setNguoiSua(String nguoisua) {
		
		this.NguoiSua=nguoisua;
	}

	public String getNguoiSua() {
		
		return this.NguoiSua;
	}

	public void setMessage(String strmessage) {
		
		this.Message=strmessage;
	}

	public String getMessage() {
		
		return this.Message;
	}

	public void setNhappId(String NhappId) {
		
		this.NhaPhanPhoiId=NhappId;
	}
	public String getNhappId() {
		
		return this.NhaPhanPhoiId;
	}
	public ResultSet getListChiTieu() {
		
		return this.rsChiTieu;
	}
	@Override
	public List<ChiTieuDDKD> getListChiTieuDDKD() {
		
		return this.listchitieuddkd;
	}
	@Override
	public void setListChiTieuDDKD(List<ChiTieuDDKD> list) {
		
		this.listchitieuddkd=list;
	}
	@Override
	public String getDVKDId() {
		
		return this.DVKDId;
	}
	@Override
	public void setDVDKId(String dvkdid) {
		
		this.DVKDId=dvkdid;
	}
	@Override
	public String getTenDVKD() {
		
		return this.TenDVKD;
	}
	@Override
	public void setTenDVKD(String tendvkd) {
		
		this.TenDVKD=tendvkd;
	}

	public void setSoNgayLamViec(String songaylamviec) {
		
		this.SoNgayLamViec=songaylamviec;
	}
	public String getSoNgayLamViec() {
		
		return this.SoNgayLamViec;
	}
	@Override
	public void setKenhId(String kenhid) {
		
		this.KenhId=kenhid;
	}
	@Override
	public String getKenhId() {
		
		return this.KenhId;
	}
	@Override
	public ResultSet getRsKenh() {
		
		String sql="select pk_Seq,ten from  kenhbanhang where trangthai='1'";
		return  db.get(sql);
	}
	 
	 
	@Override
	public void setTrangThai(String trangthai) {
		
		this.TrangThai=trangthai;
	}
	@Override
	public String getTrangThai() {
		
		return this.TrangThai;
	}
	@Override
	public boolean ChotChiTieu() {
		
		try{
		db.getConnection().setAutoCommit(false);
		
		String sql="select  chitieu,(select sum(chitieu) from chitieunpp_ddkd  where CHITIEUNPP_FK=pk_seq ) as chitieuddkd  from CHITIEUNPP where pk_seq="+this.Id;
		
		ResultSet rscheck=db.get(sql);
		if(rscheck.next()){
			if(rscheck.getDouble("chitieu") != rscheck.getDouble("chitieuddkd")){
				this.Message="Bạn phải chia lại chỉ tiêu cho các nhân viên";
				System.out.println("ERP : "+this.Message);
				rscheck.close(); 
				db.update("rollback");
				return false;
			}
		}
		rscheck.close();
		
		 sql="update CHITIEUNPP set trangthai='1' where pk_seq="+ this.Id;
		if(!db.update(sql)){//khong xoa duoc
		  db.update("rollback");
		  return false;
		}
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			er.printStackTrace();
			db.update("rollback");
			this.setMessage("Ban khong the xoa chi tieu moi nay, loi : " + er.toString());
			return false;
		}
        return true;
	}

	@Override
	public void DBclose() {
		
		try {
			this.rsChiTieu.close();
			this.rsdvkd.close();
			if(this.db != null)
				this.db.shutDown();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void init() {
		
		String sql_getdvkd="select pk_seq,donvikinhdoanh from donvikinhdoanh where trangthai=1";
		this.rsdvkd=db.get(sql_getdvkd);
		
	}

	@Override
	public String[] getNhomSp() {
		
		return this.NhomSP;
	}

	@Override
	public String[] getNhomSpId() {
		
		return this.NhomSPId;
	}

	@Override
	public ResultSet getChitieuDDKd() {
		
		return this.rsChiTieu_ddkd;
	}

	@Override
	public void setTenNPP(String TenNPP) {
		
		NhappTen=TenNPP;
	}

	@Override
	public String getTenNPP() {
		
		return this.NhappTen;
	}
	public String getVungId()
	{
		return vungId;
	}

	public void setVungId(String vungId)
	{
		this.vungId = vungId;
	}

	public String getKhuvucId()
	{
		return khuvucId;
	}

	public void setKhuvucId(String khuvucId)
	{
		this.khuvucId = khuvucId;
	}

	public String getNppId()
	{
		return nppId;
	}

	public void setNppId(String nppId)
	{
		this.nppId = nppId;
	}

	public ResultSet getVungRs()
	{
		return vungRs;
	}

	public void setVungRs(ResultSet vungRs)
	{
		this.vungRs = vungRs;
	}

	public ResultSet getKhuvucRs()
	{
		return khuvucRs;
	}

	public void setKhuvucRs(ResultSet khuvucRs)
	{
		this.khuvucRs = khuvucRs;
	}

	public ResultSet getNppRs()
	{
		return nppRs;
	}

	public void setNppRs(ResultSet nppRs)
	{
		this.nppRs = nppRs;
	}

 
	
}
