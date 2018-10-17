package geso.traphaco.center.beans.sitecode_conv.imp;

import java.sql.ResultSet;

import geso.traphaco.center.beans.sitecode_conv.Isitecode_conv;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Phan_Trang;

public class sitcode_conv  extends Phan_Trang implements Isitecode_conv{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String Sitecode;
	private String Convsitecode;
	private String Ten;
	private String Ngaytao;
	private String Ngaysua;
	private String Trangthai;
	private String Nguoitao;
	private String Nguoisua;
	private String UserId;
	private String Smg;
	private ResultSet rs_sitecode_conv;
	private dbutils db;
	private ResultSet rs_npptn;//Nha phan phoi tien nhiem
	private ResultSet rs_khuvuc;
	private String IdNpptiennhiem="";
	private String tennpptn="";
	private String KhuvucId="";
	private String NgayKs="";
	String diachi;
	public sitcode_conv(){
		this.Trangthai="";
		this.Ten="";
		this.Sitecode="";
		this.Convsitecode="";
		this.Smg="";
		this.KhuvucId="";
		this.diachi="";
		db=new dbutils();
		
	}
	public String getDiachi() {
		return diachi;
	}
	public void setDiachi(String diachi) {
		this.diachi = diachi;
	}
	public sitcode_conv(String sitecode){
		db=new dbutils();
		String sql="select conv.sitecode,conv.ten,conv.convsitecode,npp.ten as npptiennhiem,npp.pk_seq as idnpptn, "+ 
		" conv.trangthai,conv.ngaytao,conv.ngaysua,nt.ten as nguoitao,ns.ten as nguoisua,npp.ten as tenupdate,conv.diachi from sitecode_conv conv " +
		" left join nhaphanphoi npp on npp.sitecode=conv.convsitecode"+ 
		" inner join nhanvien nt on nt.pk_seq= conv.nguoitao inner join nhanvien ns on conv.nguoisua=ns.pk_seq where conv.sitecode='"+sitecode+"'";
		
	
		
		
		ResultSet rs=db.get(sql);
		if(rs!= null)
		{
			try
			{
				 if(rs.next())
				 {
					 this.Sitecode=rs.getString("sitecode");
					 this.Convsitecode=rs.getString("convsitecode");
					 this.Ten=rs.getString("ten");
					 this.IdNpptiennhiem=rs.getString("idnpptn");
					 if(this.IdNpptiennhiem==null)
					 {
						 this.IdNpptiennhiem="";
					 }
					 this.Smg="";
					 this.diachi=rs.getString("diachi");
					 if(rs.getString("trangthai").equals("0") && rs.getString("convsitecode")!="")
					 {
						 this.tennpptn=rs.getString("tenupdate");
					 }else
					 {
						 this.tennpptn=rs.getString("npptiennhiem");
					 }
				 }
				
			}catch(Exception er)
			{
				
			}
		}
	}
	
	@Override
	public void setsitecode(String sitecode) {

		this.Sitecode=sitecode;
	}

	@Override
	public String getsitecode() {

		return this.Sitecode;
	}

	@Override
	public void setconvsitecode(String convsitecode) {

		this.Convsitecode=convsitecode;
	}

	@Override
	public String getconvsitecode() {

		return this.Convsitecode;
	}

	@Override
	public void setngaytao(String ngaytao) {

		this.Ngaytao=ngaytao;
	}

	@Override
	public String getngaytao() {

		return this.Ngaytao;
	}

	@Override
	public void setngaysua(String ngaysua) {

		this.Ngaysua=ngaysua;
	}

	@Override
	public String getngaysua() {

		return this.Ngaysua;
	}

	@Override
	public void setten(String ten) {

		this.Ten=ten;
	}

	@Override
	public String getten() {

		return this.Ten;
	}

	@Override
	public void settrangthai(String trangthai) {

		this.Trangthai=trangthai;
	}

	@Override
	public String gettrangthai() {

		return this.Trangthai;
	}

	@Override
	public void setnguoitao(String nguoitao) {

		this.Nguoitao=nguoitao;
	}

	@Override
	public String getnguoitao() {

		return this.Nguoitao;
	}

	@Override
	public void setnguoisua(String nguoisua) {

		this.Nguoisua=nguoisua;
	}

	@Override
	public String getnguoisua() {

		return this.Nguoisua;
	}

	@Override
	public void Init(String sql) 
	{
		//neu  nha phan phoi chua c� co nghia la null thi se lay ten cua nha phan phoi tien nhiem va trong trang thai la chua chot
		
		String sql_getsitecode_conv=
				"	select conv.sitecode,conv.ten,isnull (conv.convsitecode,'') as convsitecode, "+
						"		isnull(conv.tennpptn,'NA') as npptiennhiem,ISNULL(npp.pk_seq,'0') as idnpptn, "+ 
						"		conv.trangthai,conv.ngaytao,conv.ngaysua,nt.ten as nguoitao,ns.ten as nguoisua,isnull(npp.ten,'') as tenupdate , "+
						"		isnull(dms.PK_SEQ,0) as MaDms "+
						"	from sitecode_conv conv 	  "+
						"		left join nhaphanphoi npp on npp.sitecode=conv.convsitecode "+ 
						"		left join NHAPHANPHOI dms on dms.CONVSiteCode=conv.SITECODE  "+
						"		inner join nhanvien nt on nt.pk_seq= conv.nguoitao  "+
						"		inner join nhanvien ns on conv.nguoisua=ns.pk_seq  " +
						" WHERE 1=1 ";;
		if(sql!="")
		{
			sql_getsitecode_conv=sql;
		}
		rs_sitecode_conv=super.createSplittingData(super.getItems(), super.getSplittings(), "ngaytao", sql_getsitecode_conv);
	}

	@Override
	public ResultSet getsistecode_conv() {

		return rs_sitecode_conv;
	}

	@Override
	public void setUserid(String userid) {

		this.UserId=userid;
	}

	@Override
	public String getUserid() {

		return this.UserId;
	}

	@Override
	public void setMsg(String smg) {

		this.Smg=smg;
	}

	@Override
	public String getMsg() {

		return this.Smg;
	}

	@Override
	public void setRsNppTienNhiem() {

		String sql="select pk_seq,ten,sitecode from nhaphanphoi where sitecode=convsitecode and trangthai=1";//Nhung nha phan phoi  co du lieu
		if(!this.KhuvucId.equals("")){
			sql=sql+" and khuvuc_fk='"+this.KhuvucId+"'";
		}
		//System.out.println(sql);

		rs_npptn=db.get(sql);
		
	}

	@Override
	public ResultSet getRsNppTienNhiem() {

		return rs_npptn;
	}

	@Override
	public void setIdNppTienNhiem(String idnpptiennhiem) {

	this.IdNpptiennhiem=idnpptiennhiem;
	}

	@Override
	public String getIdNppTienNhiem() {

		return this.IdNpptiennhiem;
	}
	@Override
	public boolean save() 
	{
		if(this.IdNpptiennhiem.trim().length()<=0)
		{
			this.setMsg("Vui lòng chọn nhà phân phối tiền nhiệm ");
			return false;
		}
		String sql="update sitecode_conv set convsitecode= (select sitecode from nhaphanphoi where pk_seq='"+this.IdNpptiennhiem+"') where sitecode='"+this.Sitecode+"' ";
		if(!db.update(sql))
		{
			this.setMsg("Loi dong lenh :" + sql);
			return false;
		}
		return true;
	}
	@Override
	public boolean chot() 
	{
		//cap nhat trang thai va cap nhat convsitecode
		try
		{
			
			if(this.IdNpptiennhiem.length()<0)
			{
				this.setMsg("Vui lòng chọn nhà phân phối tiền nhiệm ");
				return false;
			}
			db.getConnection().setAutoCommit(false);
			//lay sitecode cua npp tien nhiem
			String sql="select sitecode,ten from nhaphanphoi where pk_seq='"+this.IdNpptiennhiem+"'";
			ResultSet rs=db.get(sql);
			if(rs.next())
			{
				this.Convsitecode=rs.getString("sitecode");
				this.tennpptn =rs.getString("ten");
			}
			//Cap nhat lai convsitecode la sitecode cua npp tien nhiem,va cap nhat ten npp tien nhiem
			 sql="update sitecode_conv set trangthai=1,convsitecode=N'"+this.Convsitecode+"',tennpptn=N'"+this.tennpptn+"' where sitecode='"+this.Sitecode+"' ";
			if(!db.update(sql)){
				this.setMsg("Loi dong lenh :" + sql);
				db.update("rollback");
				return false;
			}
			//cap nhat ten npp tien nhiem thanh ten npp moi
			sql="update nhaphanphoi set ten=N'"+this.Ten+"',DiaChi=N'"+this.diachi+"',trangthai='1',ma=N'"+this.Sitecode+"',pass='"+this.Sitecode+"' where pk_Seq='"+this.IdNpptiennhiem+"'";
			if(!db.update(sql))
			{
				this.setMsg("Loi dong lenh :" + sql);
				db.update("rollback");
				return false;
			}
			//Them user dang nhap 
			 sql = "insert into nhanvien(ten, ngaysinh, dangnhap, matkhau, email, dienthoai, trangthai, ngaytao, ngaysua, nguoitao, nguoisua, phanloai, islogin, sessionid,convsitecode)  " +
			 		"values(N'" + this.Ten + "','',N'" + this.Sitecode + "',pwdencrypt('" + this.Sitecode + "'),'','','1','" + this.Ngaysua + "','" + this.Ngaysua + "','" + this.UserId + "','" + this.UserId + "','1','0','No',N'"+this.Convsitecode+"')";
			if(!db.update(sql))
			{
				db.update("rollback");
				this.setMsg("Loi dong lenh :" + sql);
				return false; 
			}
			
			//cap nhat lai trang thai cua cac nha phan phoi ke nhiem truoc,muc dich chi de 1 nha phan phoi ke nhiem
			sql="update sitecode_conv set trangthai='2' where convsitecode=(select sitecode from nhaphanphoi where pk_Seq='"+this.IdNpptiennhiem+"') and sitecode<>'"+this.Sitecode+"'";
			if(!db.update(sql))
			{
				db.update("rollback");
				this.setMsg("Loi dong lenh :" + sql);
				return false; 
			}
			//huy cac dang nhap cua cac nha phan phoi ke nhiem truoc,de mot dang nhap cua npp ke nhiem moi
			sql="update nhanvien set trangthai='0' where dangnhap in (select sitecode from sitecode_conv where convsitecode='"+this.Convsitecode+"' and sitecode!='"+this.Sitecode+"') or dangnhap in (select sitecode from nhaphanphoi where pk_seq='"+this.IdNpptiennhiem+"')";
			if(!db.update(sql))
			{
				db.update("rollback");
				this.setMsg("Loi dong lenh :" + sql);
				return false; 
			}
			db.update("commit");
			db.getConnection().setAutoCommit(true);
			
		}catch(Exception er)
		{
			db.update("rollback");
			return false;
		}
		return true;
	}
	@Override
	public void settennpptn(String tennpptn) {

		this.tennpptn=tennpptn;
	}
	@Override
	public String gettennpptn() {

		return this.tennpptn;
	}
	@Override
	public ResultSet getRsloctheokhuvuc() {

		return this.rs_khuvuc;
	}
	@Override
	public void setRsKhuvuc() {

		String sql="select pk_seq,ten from khuvuc ";//Nhung nha phan phoi  co du lieu
		db=new dbutils();
		rs_khuvuc=db.get(sql);
	}
	@Override
	public String getKhuVucId() {

		return this.KhuvucId;
	}
	@Override
	public void setkhuvucId(String khuvucid) {

		this.KhuvucId=khuvucid;
	}
	@Override
	public boolean TaoNPPMoi() {

		try
		{
			this.db.getConnection().setAutoCommit(false);
			if(this.KhuvucId ==null ||this.KhuvucId.equals(""))
			{
				this.setMsg("Vui Long Chon Khu Vuc Cho Nha Phan Phoi Moi");
				
				return false;
			}
			if(this.getsitecode().equals(""))
			{
				this.setMsg("Vui Long Thu Lai,SiteCode Nha Phan Phoi Khong Duoc Rong.");
				return false;
			}
			
			
		String sql="insert into nhaphanphoi (ten,ngaytao,ngaysua,nguoitao,nguoisua,trangthai,sitecode,convsitecode,khuvuc_fk,KHOSAP,ma,pass,DiaChi)"+
		"values(N'"+this.getten()+"','"+this.Ngaytao+"','"+this.Ngaytao+"','"+this.UserId+"','"+this.UserId+"','0',N'"+this.getsitecode()+"',N'"+this.getsitecode()+"','"+this.KhuvucId+"','100003',N'"+this.getsitecode()+"',N'"+this.getsitecode()+"',(select diachi from sitecode_conv where sitecode=N'"+this.Sitecode+"'))";
		
		if(!db.update(sql))
		{
			this.setMsg("Khong The Chuyen Sang Nha Phan Phoi Moi,Vui Long Thu Lai,Neu Khong Duoc,Vui Long Lien He Voi Admin De Duoc Giup Do.Loi :"+sql);
			this.db.update("rollback");
			return false;
		}
		
		 sql = "insert into nhanvien(ten, ngaysinh, dangnhap, matkhau, email, dienthoai, trangthai, ngaytao, ngaysua, nguoitao, nguoisua, phanloai, islogin, sessionid,convsitecode) " +
		 		" values(N'" + this.Ten + "','',N'" + this.Sitecode + "',pwdencrypt('" + this.Sitecode + "'),'','','1','" + this.Ngaytao + "','" + this.Ngaytao + "','" + this.UserId + "','" + this.UserId + "','1','0','No',N'"+this.Sitecode+"')";
		if(!db.update(sql))
		{
			db.update("rollback");
			this.setMsg("Loi dong lenh :" + sql);
			return false; 
		}
		String  query = "select IDENT_CURRENT('nhaphanphoi') as nppId";
		ResultSet rs = this.db.get(query);
		rs.next();
		String nppId = rs.getString("nppId");
		rs.close();
		
		sql="UPDATE sitecode_conv SET TRANGTHAI=1 WHERE SITECODE='"+this.Sitecode+"' ";
		if(!db.update(sql))
		{
			db.update("rollback");
			this.setMsg("Loi dong lenh :" + sql);
			return false; 
		}
		
		sql=
		" UPDATE NHAPHANPHOI SET TIMKIEM =UPPER(DBO.FTBODAU(TEN))+' '+UPPER(DBO.FTBODAU(ISNULL(DIACHI,'')))+' '+UPPER(DBO.FTBODAU(ISNULL(MA,''))) +' '+UPPER(DBO.FTBODAU(ISNULL(EMAIL,''))) + ' '+ISNULL(DIENTHOAI,'') WHERE PK_SEQ='"+nppId+"' ";
		if(!db.update(sql))
		{
			this.setMsg("Khong The Chuyen Sang Nha Phan Phoi Moi,Vui Long Thu Lai,Neu Khong Duoc,Vui Long Lien He Voi Admin De Duoc Giup Do.Loi :"+sql);
			this.db.update("rollback");
			return false;
		}
		
		/*
		 * thuc hien cap nhat lai phan quyen
		 * 
		 */
		sql="select pk_seq from nhaphanphoi where sitecode=N'"+this.getsitecode()+"'";
		rs=db.get(sql);
		String id="";
		if(rs.next())
		{
			id=rs.getString("pk_seq");
		}
			rs.close();
		 sql="select distinct nhanvien_fk,npp.khuvuc_fk from phamvihoatdong inner join nhaphanphoi npp on npp_fk=npp.pk_seq where khuvuc_fk="+this.KhuvucId ;
		 rs=db.get(sql);
		 while (rs.next())
		 {
		   sql="insert into phamvihoatdong(nhanvien_fk,npp_fk) values("+rs.getString("nhanvien_fk")+",'"+id+"') ";
		  
		   if(!db.update(sql))
		   {
			   this.db.update("rollback");
			   this.setMsg("Khong the cap nhat loi :" + sql);
			   return false;
		   }
		 }
		 if(rs!=null)
		 {
			 rs.close();
		 }
		
		//them ngay khoa so moi
		 
		 sql=" insert into khoasongay(ngayksgannhat,ngayks,ngaytao,nguoitao,npp_fk,chon,doanhso)" +
		 		" values ('"+this.getNgaykhoaso()+"','"+this.NgayKs+"','"+this.Ngaytao+"','"+this.UserId+"','"+id+"','1','1')" ;
		 System.out.println("Get Sql Update khoa so ngay : "+sql);
		  if(!db.update(sql))
		  {
			   this.db.update("rollback");
			   this.setMsg("Khong the cap nhat loi :" + sql);
			   return false;
		   }
		
		
		this.db.update("commit");
		this.db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			this.db.update("rollback");
			this.setMsg("Khong the cap nhat loi :" + er.toString());
			   return false;
		}
	
		return true;
	}
	@Override
	public void NgayKhoaSo(String ngayks) {

		this.NgayKs=ngayks;
	}
	@Override
	public String getNgaykhoaso() {

		return this.NgayKs;
	}
	@Override
	public void DBClose() {
		try{

		if(rs_sitecode_conv!=null){
			rs_sitecode_conv.close();
		}
		if(rs_npptn!=null){
			rs_npptn.close();
		}
		if(rs_khuvuc!=null){
			rs_khuvuc.close();
		}

		if(db!=null){
			db.shutDown();
		}
		}catch(Exception er){
			
		}
	}
}
