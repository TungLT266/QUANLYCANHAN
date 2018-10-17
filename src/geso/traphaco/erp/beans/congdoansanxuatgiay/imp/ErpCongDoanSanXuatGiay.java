package geso.traphaco.erp.beans.congdoansanxuatgiay.imp;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.congdoansanxuatgiay.IErpCongDoanSanXuatGiay;
import geso.traphaco.erp.beans.congdoansanxuatgiay.ITaisan;
import geso.traphaco.erp.beans.congdoansanxuatgiay.ITieuchikiemdinh;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ErpCongDoanSanXuatGiay implements  IErpCongDoanSanXuatGiay
{
	String ctyId;
	String userId;
	String id;
	
	String ma;
	String diengiai;
	String sonhancong;
	private String yckn;
	private String lhsknList;
	
	ResultSet nhamayRs;
	String nhamayIds;
	
	ResultSet tbRs;
	String tbIds;
	
	String trangthai;
	String msg;
	String SanXuat;
	String [] tieuchi_dinhtinh;
	String kiemdinhchatluong,dinhluong,dinhtinh,sanPhamId,dmvtId,sanphamMa,SpSelected;
	List<ITieuchikiemdinh> lstKdinh;
	ResultSet rsSp,rsDmvt;
	dbutils db;
	List<ITaisan> lts;
	String[] sttpb;
	String[] phongbanId;
	ResultSet phongbanRs;
	String[] giaidoanId;
	ResultSet giaidoanRs;
	ResultSet loaimauinSxRs;
	String loaimauinSxId;
	private ResultSet LhsknRs;
	
	public ErpCongDoanSanXuatGiay()
	{
		this.ctyId = "";
		this.userId = "";
		this.id = "";
		this.ma = "";
		this.diengiai = "";
		this.yckn = "";
		this.lhsknList = "";
		this.sonhancong = "";
		this.tbIds = "";
		this.nhamayIds = "";
		this.trangthai = "1";
		this.msg = "";
		this.sanPhamId="";
		this.kiemdinhchatluong="";
		this.dinhluong="0";
		this.dinhtinh="";
		this.SanXuat="";
		this.sanphamMa="";
		this.dmvtId="";
		this.SpSelected="";
		this.loaimauinSxId = "";
		this.db = new dbutils();
		lstKdinh=new ArrayList<ITieuchikiemdinh>();
		lts = new ArrayList<ITaisan>();
	}
	
	public ErpCongDoanSanXuatGiay(String id)
	{
		this.id = id;
		this.ma = "";
		this.diengiai = "";
		this.yckn = "";
		this.lhsknList = "";
		this.sonhancong = "";
		this.tbIds = "";
		this.nhamayIds = "";
		this.trangthai = "1";
		this.msg = "";
		this.sanPhamId="";
		this.kiemdinhchatluong="";
		this.dinhluong="0";
		this.dinhtinh="";
		this.SanXuat="";
		this.dmvtId="";
		this.sanphamMa="";
		this.SpSelected="";
		this.loaimauinSxId = "";
		lstKdinh=new ArrayList<ITieuchikiemdinh>();
		lts = new ArrayList<ITaisan>();
		this.db = new dbutils();
		
	}
	
	public ResultSet getLoaimauinSxRs() {
		return loaimauinSxRs;
	}

	public void setLoaimauinSxRs(ResultSet loaimauinSxRs) {
		this.loaimauinSxRs = loaimauinSxRs;
	}

	public String getLoaimauinSxId() {
		return loaimauinSxId;
	}

	public void setLoaimauinSxId(String loaimauinSxId) {
		this.loaimauinSxId = loaimauinSxId;
	}
	
	public String getCtyId() 
	{
		return this.ctyId;
	}

	public void setCtyId(String ctyId) 
	{
		this.ctyId = ctyId;	
	}
	
	public String getId() 
	{
		return this.id;
	}

	public void setId(String Id) 
	{
		this.id = Id;
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;	
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public void init() 
	{
		String query = "select loaimauinSxId,PK_SEQ,isnull(SanPham_FK,'-1')as SanPham_FK,MaSanPham ,isnull(dinhtinh,0) as dinhtinh ,isnull(dinhluong,0) as dinhluong, DienGiai,NhaMay_FK,CongTy_FK,SoNhanCong,TrangThai,SanXuat,isnull(isYCKN,0) as isYCKN from Erp_CongDoanSanXuat_Giay where pk_seq='"+this.id+"'";
		System.out.println( " init : "+ query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.sanPhamId=rs.getString("SanPham_fK");
					this.sanphamMa=rs.getString("MaSanPham");
					this.SpSelected=rs.getString("SanPham_fK")+"_"+rs.getString("MaSanPham");
					this.dinhtinh=rs.getString("dinhtinh");
					this.dinhluong=rs.getString("dinhluong");
					this.SanXuat=rs.getString("SanXuat");
					this.diengiai = rs.getString("diengiai");
					this.sonhancong = rs.getString("sonhancong");
					this.nhamayIds = rs.getString("nhamay_fk");
					this.trangthai = rs.getString("trangthai");
					this.loaimauinSxId= rs.getString("loaimauinSxId");
					this.yckn= rs.getString("isYCKN");
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("__Exception: " + e.getMessage());
			}
		}
		System.out.println("INIT CongDoan__"+query);
		
		query = "select LOAIMAUKN_FK from ERP_CONGDOANSANXUAT_HOSOKN where CONGDOANSX_FK=" + this.id;
		rs = this.db.get(query);
		
		try {
			while(rs.next()){
				this.lhsknList += rs.getString("LOAIMAUKN_FK") + ",";
			}
			if(this.lhsknList.length() > 0)
				this.lhsknList = this.lhsknList.substring(0, this.lhsknList.length() - 1);
			
			rs.close();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		String STT = "", PHONGBAN = "", GIAIDOAN = "";
		query = "select stt, phongban_fk, giaidoan_fk from Erp_CongDoanSanXuat_GIAIDOAN_Giay where CongDoanSanXuat_FK = '" + this.id + "' order by stt";
		rs = db.get(query);
		try 
		{
			while(rs.next())
			{
				STT += rs.getString("stt") + "__";
				PHONGBAN += rs.getString("phongban_fk") + "__";
				GIAIDOAN += rs.getString("giaidoan_fk") + "__";
			}
			rs.close();
			
			STT = STT.substring(0, STT.length() - 2);
			this.sttpb = STT.split("__");
			
			PHONGBAN = PHONGBAN.substring(0, PHONGBAN.length() - 2);
			this.phongbanId = PHONGBAN.split("__");
			
			GIAIDOAN = GIAIDOAN.substring(0, GIAIDOAN.length() - 2);
			this.giaidoanId = GIAIDOAN.split("__");
		} 
		catch (Exception e) 
		{
			System.out.println("__Exception 2: " + e.getMessage());
		}
		
		//create Ids
		query = "select thietbi_fk from Erp_CongDoanSanXuat_ThietBi_Giay where CongDoanSanXuat_FK = '" + this.id + "'";
		rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				String str = "";
				while(rs.next())
				{
					str += rs.getString("thietbi_fk") + ",";
				}
				rs.close();
				
				if(str.trim().length() > 0)
				{
					str = str.substring(0, str.length() - 1);
					this.tbIds = str;
				}
			} 
			catch (Exception e) 
			{
				System.out.println("__Exception 2: " + e.getMessage());
			}
		}
		
	
		int numb=0;
		query="select count(*)  from Erp_CongDoanSanXuat_TieuChiKiemDinh_Giay where CongDoanSanXuat_FK= '"+this.id+"'  and DinhTinh=1 ";
		rs=this.db.get(query);
		if(rs!=null)
		try
		{
			while(rs.next())
			numb=rs.getInt(1);
			rs.close();
		} catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		this.tieuchi_dinhtinh=new String[numb];
		System.out.println("So dong "+numb);
		query="select CongDoanSanXuat_FK,TieuChi from Erp_CongDoanSanXuat_TieuChiKiemDinh_Giay where CongDoanSanXuat_FK= '"+this.id+"'  and DinhTinh=1 ";
		rs=this.db.get(query);
		System.out.println("___DinhTinh___"+query);
		int i=0;
		if(rs!=null)
		{
			try
			{
				while(rs.next())
				{
						this.tieuchi_dinhtinh[i]=rs.getString("TieuChi");
						i++;
				}rs.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		
		query="select * from Erp_CongDoanSanXuat_TieuChiKiemDinh_Giay where CongDoanSanXuat_FK= '"+this.id+"'  and DinhLuong = 1 ";
		rs=this.db.get(query);
		System.out.println("___DinhLuong___"+query);
		this.lstKdinh = new ArrayList<ITieuchikiemdinh>();
		if(rs!=null)
		{
			try
			{
				while(rs.next())
				{
					ITieuchikiemdinh kd = new Tieuchikiemdinh();
					kd.setTieuchi(rs.getString("TieuChi"));
					kd.setToantu(rs.getString("PhepToan"));
					kd.setGiatrichuan(rs.getString("GiaTriChuan"));
					this.lstKdinh.add(kd);
				}
				rs.close();
			} 
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		
		query="select pk_seq,ma,ten from ERP_CONGDOANSANXUAT_TAISAN, ERP_TAISANCODINH WHERE TAISAN_FK = pk_seq and CONGDOAN_FK= '"+this.id+"'";
		rs=this.db.get(query);
		System.out.println("___Taisan___"+query);
		this.lts = new ArrayList<ITaisan>();
		if(rs!=null)
		{
			try
			{
				while(rs.next())
				{
					ITaisan ts = new Taisan();
					ts.setPk_seq(rs.getString("pk_seq"));
					ts.setMa(rs.getString("ma"));
					ts.setTen(rs.getString("ten"));
					this.lts.add(ts);
				}
				rs.close();
			} 
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		
	}
	


	public boolean createCumsanxuat()
	{	
		try 
		{
			if(!valid())
			{
				return false;
			}
			db.getConnection().setAutoCommit(false);
			
			String  query="insert Erp_CongDoanSanXuat_Giay(SanPham_FK, DinhLuong, DinhTinh , DienGiai, isYCKN, NhaMay_FK, CongTy_FK, SoNhanCong, TrangThai,SanXuat, ngaytao, nguoitao, ngaysua, nguoisua, LOAIMAUINSX_FK) " +
					"values("+this.sanPhamId+",'"+this.dinhluong+"','"+this.dinhtinh+"',N'"+this.diengiai+"', "+this.yckn+",'"+this.nhamayIds+"','"+this.ctyId+"','"+this.sonhancong+"','" + this.trangthai + "',"
					+ "'"+this.SanXuat+"', '" + this.getDateTime() + "', '" + this.userId + "', '" + this.getDateTime() + "', '" + this.userId + "','" + this.loaimauinSxId +"')";
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới Erp_CongDoanSanXuat_Giay " + query;
				db.getConnection().rollback();
				return false;
			}
			query="select scope_identity()";
			ResultSet rsId=this.db.get(query);
			if(rsId!=null)
			{
				while(rsId.next())
					this.id=rsId.getString(1);
				rsId.close();
			}
			
			if(this.yckn.equals("1")){
				if(this.lhsknList.length() > 0){
					String[] chon = this.lhsknList.split(",");
					for(int i = 0; i < chon.length; i++){
						query = "insert into ERP_CONGDOANSANXUAT_HOSOKN(CONGDOANSX_FK,LOAIMAUKN_FK) values('"+this.id+"','"+chon[i]+"')";
						System.out.println(query);
						if(!db.update(query)) {
							this.msg = "Không thể tạo mới ERP_CONGDOANSANXUAT_HOSOKN: " + query;
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			
			/*query = "insert Erp_CongDoanSanXuat_ThietBi_Giay(CongDoanSanXuat_FK, thietbi_fk) " +
					"select '"+this.id+"' as CongDoanSanXuat_Fk, pk_seq from ERP_TAISANCODINH where pk_seq in ( " + this.tbIds + " )";
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới Erp_CongDoanSanXuat_Giay_ThietBi " + query;
				db.getConnection().rollback();
				return false;
			}*/
			if(this.tieuchi_dinhtinh!=null && this.tieuchi_dinhtinh.length>0)
			{
				for(int i=0;i<this.tieuchi_dinhtinh.length;i++)
				{
					if(this.tieuchi_dinhtinh[i]!=null && this.tieuchi_dinhtinh[i].trim().length()>0)
					{
						query="insert into Erp_CongDoanSanXuat_TieuChiKiemDinh_Giay(CongDoanSanXuat_FK,TieuChi,DinhTinh)" +
							"values('"+this.id+"',N'"+this.tieuchi_dinhtinh[i]+"',1)";
						if(!this.db.update(query))
						{
							this.msg = "Không thể tạo mới Erp_CongDoanSanXuat_TieuChiKiemDinh_Giay " + query;
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			if(lstKdinh != null && lstKdinh.size() > 0){
				for(int i=0; i< lstKdinh.size(); i++){
					ITieuchikiemdinh tckd= lstKdinh.get(i);
					
					query="insert into Erp_CongDoanSanXuat_TieuChiKiemDinh_Giay(CongDoanSanXuat_FK, TieuChi, Pheptoan, GiaTriChuan, DinhLuong)" +
					"values('"+this.id+"', N'"+tckd.getTieuchi()+"', '"+tckd.getToantu()+"', "+tckd.getGiatrichuan()+", 1)";
					if(!this.db.update(query))
					{
						this.msg = "Không thể tạo mới Erp_CongDoanSanXuat_TieuChiKiemDinh_Giay " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			if(lts != null && lts.size() > 0){
				for(int i=0; i< lts.size(); i++){
					ITaisan tscd= lts.get(i);
					
					query="insert into ERP_CONGDOANSANXUAT_TAISAN(CONGDOAN_FK, TAISAN_FK)" +
					"values('"+this.id+"','"+tscd.getPk_seq()+"')";
					if(!this.db.update(query))
					{
						this.msg = "Không thể tạo mới ERP_CONGDOANSANXUAT_TAISAN " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			for(int i = 0; i < phongbanId.length; i++){
				
				if(phongbanId[i].trim().length() > 2 && giaidoanId[i].trim().length() > 2)
				{
					query = "insert into ERP_CONGDOANSANXUAT_GIAIDOAN_GIAY(CONGDOANSANXUAT_FK, PHONGBAN_FK, GIAIDOAN_FK, STT)" +
							"values('" + this.id + "', '" + phongbanId[i] + "', '" + giaidoanId[i] + "', '" + sttpb[i] + "')";
					if(!this.db.update(query))
					{
						this.msg = "Không thể tạo mới ERP_CONGDOANSANXUAT_GIAIDOAN_GIAY " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			e.printStackTrace();
			this.msg=e.toString();
			return false;
		}
		
		return true;
	}
	
	public boolean updateCumsanxuat() 
	{
		try 
		{
			if(!valid())
			{
				return false;
			}
			db.getConnection().setAutoCommit(false);
			
			String query = "update Erp_CongDoanSanXuat_Giay set SanPham_FK="+this.sanPhamId+" ,MaSanPham=nullif('"+this.sanphamMa.trim()+"',''),dinhluong =" + this.dinhluong + ", DinhTinh='"+this.dinhtinh+
					"',DienGiai=N'"+this.diengiai+"',isYCKN="+this.yckn+",NhaMay_FK ='"+this.nhamayIds+"',SoNhanCong='"+this.sonhancong+"' ,TrangThai='"
					+this.trangthai+"', ngaysua='"+this.getDateTime()+"', nguoisua='"+this.userId+"',SanXuat='"+this.SanXuat+"', loaimauinSxId='"+ this.loaimauinSxId +"'" +
					" where pk_Seq='"+this.id+"' ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật Erp_CongDoanSanXuat_Giay " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_CONGDOANSANXUAT_HOSOKN where CONGDOANSX_FK = " + this.id;
			if(!db.update(query)){
				this.msg = "Không thể xóa ERP_CONGDOANSANXUAT_HOSOKN " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query="delete from Erp_CongDoanSanXuat_TieuChiKiemDinh_Giay where congdoansanxuat_fk ='"+this.id+"'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật Erp_CongDoanSanXuat_TieuChiKiemDinh_Giay " + query;
				db.getConnection().rollback();
				return false;
			}
			query="delete from ERP_CONGDOANSANXUAT_TAISAN where CONGDOAN_FK ='"+this.id+"'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_CONGDOANSANXUAT_TAISAN " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete from ERP_CONGDOANSANXUAT_GIAIDOAN_GIAY where CONGDOANSANXUAT_FK ='"+this.id+"'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_CONGDOANSANXUAT_TAISAN " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if(this.yckn.equals("1")){
				if(this.lhsknList.length() > 0){
					String[] chon = this.lhsknList.split(",");
					for(int i = 0; i < chon.length; i++){
						query = "insert into ERP_CONGDOANSANXUAT_HOSOKN(CONGDOANSX_FK,LOAIMAUKN_FK) values('"+this.id+"','"+chon[i]+"')";
						System.out.println(query);
						if(!db.update(query)) {
							this.msg = "Không thể tạo mới ERP_CONGDOANSANXUAT_HOSOKN: " + query;
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			
		/*	query = "delete Erp_CongDoanSanXuat_ThietBi_Giay where CongDoanSanXuat_FK = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật Erp_CongDoanSanXuat_ThietBi_Giay " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "insert Erp_CongDoanSanXuat_ThietBi_Giay(CongDoanSanXuat_FK, thietbi_fk) " +
					"select '"+this.id+"' as CongDoanSanXuat_FK , pk_seq from ERP_TAISANCODINH where pk_seq in ( " + this.tbIds + " )";
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới Erp_CongDoanSanXuat_ThietBi_Giay " + query;
				db.getConnection().rollback();
				return false;
			}*/
			
			if(this.tieuchi_dinhtinh!=null && this.tieuchi_dinhtinh.length>0)
			{
				for(int i=0;i<this.tieuchi_dinhtinh.length;i++)
				{
					if(this.tieuchi_dinhtinh[i]!=null && this.tieuchi_dinhtinh[i].trim().length()>0)
					{
						query="insert into Erp_CongDoanSanXuat_TieuChiKiemDinh_Giay(CongDoanSanXuat_FK,TieuChi,DinhTinh)" +
							"values('"+this.id+"',N'"+this.tieuchi_dinhtinh[i]+"',1)";
						if(!this.db.update(query))
						{
							this.msg = "Không thể tạo mới Erp_CongDoanSanXuat_TieuChiKiemDinh_Giay " + query;
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			if(lstKdinh != null && lstKdinh.size() > 0){
				for(int i=0; i< lstKdinh.size(); i++){
					ITieuchikiemdinh tckd= lstKdinh.get(i);
					
					query="insert into Erp_CongDoanSanXuat_TieuChiKiemDinh_Giay(CongDoanSanXuat_FK, TieuChi, Pheptoan, GiaTriChuan, DinhLuong)" +
					" values('"+this.id+"', N'"+tckd.getTieuchi()+"', '"+tckd.getToantu()+"', "+tckd.getGiatrichuan()+", 1)";
					if(!this.db.update(query))
					{
						this.msg = "Không thể tạo mới Erp_CongDoanSanXuat_TieuChiKiemDinh_Giay " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			if(lts != null && lts.size() > 0){
				for(int i=0; i< lts.size(); i++){
					ITaisan tscd= lts.get(i);
					
					query="insert into ERP_CONGDOANSANXUAT_TAISAN(CONGDOAN_FK, TAISAN_FK)" +
					"values('"+this.id+"','"+tscd.getPk_seq()+"')";
					if(!this.db.update(query))
					{
						this.msg = "Không thể tạo mới ERP_CONGDOANSANXUAT_TAISAN " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			for(int i = 0; i < phongbanId.length; i++){
				
				if(phongbanId[i].trim().length() > 2 && giaidoanId[i].trim().length() > 2)
				{
					query = "insert into ERP_CONGDOANSANXUAT_GIAIDOAN_GIAY(CONGDOANSANXUAT_FK, PHONGBAN_FK, GIAIDOAN_FK, STT)" +
							"values('" + this.id + "', '" + phongbanId[i] + "', '" + giaidoanId[i] + "', '" + sttpb[i] + "')";
					if(!this.db.update(query))
					{
						this.msg = "Không thể tạo mới ERP_CONGDOANSANXUAT_GIAIDOAN_GIAY " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			e.printStackTrace();
			this.msg=e.toString();
			return false;
		}
		
		return true;
	}

	public void createRs() 
	{
		String query="";
		/*Nha May*/
		query="select pk_seq, manhamay + ', ' + tennhamay as nhamayTen from ERP_NHAMAY where trangthai = 1 and congty_fk = " + this.ctyId + " ";
		this.nhamayRs = db.get(query);
		/*Thiet bi may moc */
		query="select pk_seq, ma, ten from ERP_TAISANCODINH where congdung_fk = '9000000' and congty_fk = " + this.ctyId + " ";
		this.tbRs = db.get(query);
		//Chi hien thi ra nhung san pham thuoc loai thanh pham va ban thanh pham
		query="select pk_seq,ma, Ten + ' ( '  + QuyCach +')' as Ten,QuyCach from erp_sanpham where loaisanpham_fk in(100005,100011) and congty_fk='"+this.ctyId+"' and trangthai='1' and pk_seq in (select sanpham_fk from erp_danhmucvattu where trangthai='1')  "+
		"			union all "+
		" select distinct '-1' as pk_seq, ma,Ten,'' as quycach from erp_sanpham where loaisanpham_fk in(100005,100011) and congty_fk='"+this.ctyId+"' and trangthai='1' and pk_seq in (select sanpham_fk from erp_danhmucvattu where trangthai='1')  "+
		"order by ma,quycach,Ten ";
		System.out.println("__San pham la ____"+query);
		if(this.SanXuat!=null &&this.SanXuat.equals("1"))
		{
			this.rsSp=db.get(query);
			
			if(sanPhamId!=null)
			{
				query="select * from erp_Danhmucvattu where trangthai='1' and sanpham_fk='"+this.sanPhamId+"' and Congty_fk='"+this.ctyId+"'";
				this.rsDmvt=db.get(query);
			}
			System.out.println("__Danh muc vat tu la ____"+query);
		}
		
		query = "select pk_seq, (ma + ' - ' + ten) as ten from erp_phongbansx where trangthai = 1 and congty_fk = " + this.ctyId;
		this.phongbanRs = db.getScrol(query);
		
		query = "select pk_seq, (ma + ' - ' + ten) as ten from erp_giaidoansx where trangthai = 1 and congty_fk = " + this.ctyId;
		this.giaidoanRs = db.getScrol(query);
		
		
		query = "select pk_seq,maloai + ' - ' + tenloai as Ten from LOAIMAUIN_SANXUAT where trangthai = 1 ";
		this.loaimauinSxRs = db.getScrol(query);
		
		query = "select PK_SEQ, MA + ' - ' + TEN as ten from ERP_LOAIMAUKIEMNGHIEM where TRANGTHAI = 1";
		this.LhsknRs = this.db.get(query);
	}
	
	
	public String getDiengiai()
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai)
	{
		this.diengiai = diengiai;
	}

	
	public void DbClose() 
	{
		try 
		{
			if (this.lstKdinh != null)
				this.lstKdinh.clear();
			if (this.lts != null)
				this.lts.clear();
			if(this.tbRs != null)
				this.tbRs.close();
			
			if(this.rsDmvt!=null)
				this.rsDmvt.close();
			
			if(this.rsSp!=null)
				this.rsSp.close();
			
			this.db.shutDown();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<ITaisan> getLts() {
		return lts;
	}

	public void setLts(List<ITaisan> lts) {
		this.lts = lts;
	}

	public String getMa() 
	{
		return this.ma;
	}

	public void setMa(String ma) 
	{
		this.ma = ma;
	}

	public ResultSet getThietbiRs() 
	{
		return this.tbRs;
	}

	public void setThietbiRs(ResultSet tbRs)
	{
		this.tbRs = tbRs;
	}

	public String getTbIds() 
	{
		return this.tbIds;
	}

	public void setTbIds(String tbIds) 
	{
		this.tbIds = tbIds;
	}

	public String getSonhancong() 
	{
		return this.sonhancong;
	}

	public void setSonhancong(String sonhancong) 
	{
		this.sonhancong = sonhancong;
	}

	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public ResultSet getNhamayRs() 
	{
		return this.nhamayRs;
	}

	public void setNhamayRs(ResultSet nmRs) 
	{
		this.nhamayRs = nmRs;
	}

	public String getNhamayIds() 
	{
		return this.nhamayIds;
	}

	public void setNhamayIds(String nmIds)
	{
		this.nhamayIds = nmIds;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	
	public String getSanXuat()
	{
		
		return this.SanXuat;
	}

	
	public void setSanXuat(String SanXuat)
	{
		this.SanXuat=SanXuat;
	}

	
	public void setdinhluong(String dinhluong)
	{
		
		this.dinhluong=dinhluong;
	}

	
	public String getdinhluong()
	{
		
		return this.dinhluong;
	}

	
	public void setTieuchikiemdinhList(List<ITieuchikiemdinh> list)
	{
		
		this.lstKdinh=list;
	}

	
	public List<ITieuchikiemdinh> getTieuchikiemdinhList()
	{
		
		return this.lstKdinh;
	}

	public String getSanPhamId()
	{
		return this.sanPhamId;
	}

	
	public void setSanPhamId(String sanPhamId)
	{
		this.sanPhamId=sanPhamId;	
	}

	
	public String getDmvtId()
	{	
		return this.dmvtId;
	}

	public void setDmvtId(String dmvtId)
	{
		this.dmvtId=dmvtId;
		
	}

	
	public ResultSet getRsSp()
	{
	
		return this.rsSp;
	}

	
	public void settRsSp(ResultSet rsSp)
	{
		this.rsSp=rsSp;
		
	}

	
	public ResultSet getRsDmvt()
	{
	
		return this.rsDmvt;
	}

	
	public void setRsDmvt(ResultSet rsDmvt)
	{
	
		this.rsDmvt=rsDmvt;
	}

	public boolean valid()
	{
		if(this.sonhancong.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập số nhân công cho công đoạn sản xuất ! ";
			return false;
		}
		
	/*	if(this.tbIds.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn thiết bị cho công đoạn sản xuất !";
			return false;
		}*/
		
		if(this.diengiai.trim().length()<=0)
		{
			this.msg="Vui lòng nhập diễn giải !";
			return false;
		}
		if(this.nhamayIds.trim().length()<=0)
		{
			this.msg="Vui lòng chọn nhà máy cho công đoạn sản xuất !";
			return false;
		}
		if(this.SanXuat.equals("1"))
		{
			if(this.sanPhamId!=null&&this.dmvtId!=null&&this.sanPhamId.trim().length()<=0&&this.dmvtId.trim().length()<=0)
			{	
				this.msg="Vui lòng chọn sản phẩm/danh mục vật tư";
				return false;
			}
		}
		if(this.dinhluong.equals("1"))
		{
			if(this.lstKdinh.size()<=0)
			{
				this.msg="Vui lòng chọn tiêu chí kiểm định ";
				return false;
			}
		}
		/*try
		{
			//kiem tra khoan muc phi cua giai doan co nam trong trung tam chi phi cua phan xuong ko
			for(int i = 0; i < giaidoanId.length; i++){
				if(!giaidoanId[i].equals("0")) {
					String q = "select ncp.pk_seq " +
							"from ERP_NHAMAY px " +
							"inner join ERP_TRUNGTAMCHIPHI ttcp on px.TTCP_FK = ttcp.PK_SEQ " +
							"inner join ERP_NHOMCHIPHI ncp on ttcp.PK_SEQ = ncp.TTCHIPHI_FK " +
							"where px.congty_fk = '" + this.getCtyId() +"' and px.pk_seq = '"+this.nhamayIds+"' "
									+ " and ncp.pk_seq in (";
					q += "select  ncp.pk_seq " +
							"from erp_giaidoansx gdsx " +
							"inner join ERP_GIAIDOANSX_THIETBI gdsxtb on  gdsx.pk_seq = gdsxtb.GIAIDOAN_FK " +
							"inner join ERP_TAISANCODINH tscd on gdsxtb.thietbi = tscd.pk_seq " +
							"inner join erp_taisancodinh_khoanmucchiphi kmp on tscd.pk_seq = kmp.TAISANCODINH_FK " +
							"inner join erp_nhomchiphi ncp on kmp.khoanmucchiphi_fk = ncp.pk_seq " +
							"where gdsxtb.loai = 0 and gdsx.congty_fk = '"+this.getCtyId()+"' " +
									 "and gdsx.pk_seq = '" + giaidoanId[i] + "' " +
							"group by ncp.pk_seq " +
							"union " +
							"select  ncp.pk_seq " +
							"from erp_giaidoansx gdsx " +
							"inner join ERP_GIAIDOANSX_THIETBI gdsxtb on  gdsx.pk_seq = gdsxtb.GIAIDOAN_FK " +
							"inner join ERP_CONGCUDUNGCU cptt on gdsxtb.thietbi = cptt.pk_seq " +
							"inner join Erp_CongCuDungCu_KhoanMucChiPhi kmp on cptt.pk_seq = kmp.ccdc_fk " +
							"inner join erp_nhomchiphi ncp on kmp.nhomchiphi_fk = ncp.pk_seq " +
							"where gdsxtb.loai = 1 and gdsx.congty_fk = '"+this.getCtyId()+"' " +
									 "and gdsx.pk_seq = '" + giaidoanId[i] + "' " +
							"group by ncp.pk_seq " + 
							"union ";
					q = q.substring(0, q.length() - 6);
					q += ")";
					System.out.println("Check khoan muc phi: " + q);
					ResultSet check_kmp = this.db.get(q);
					if(!check_kmp.next()) {
						this.msg = "TSCD - CPTT được chọn trong giai đoạn không có khoản mục phí nằm trong trung tâm chi phí";
						return false;
					}
				}
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			this.msg = "Lỗi: " + e.getMessage();
			return false;
		}*/
		return true;
	}

	
	public String[] getToanTu()
	{
		String []toanTu={">","<","=",">=","<="};
		return toanTu;
	}

	
	public String getSanPhamMa()
	{
	
		return this.sanphamMa;
	}

	
	public void setSanPhamMa(String sanphamMa)
	{
	
		this.sanphamMa=sanphamMa;
	}

	
	
	
	public String getSpSelected()
	{

		return this.SpSelected;
	}

	
	public void setSpSelected(String SpSelected)
	{

		this.SpSelected=SpSelected;
	}

	
	public void setDinhluong(String dinhluong)
	{
		this.dinhluong=dinhluong;
		
	}

	
	public String getDinhluong()
	{
	
		return this.dinhluong;
	}

	
	public void setDinhtinh(String dinhtinh)
	{
		this.dinhtinh=dinhtinh;
		
	}

	
	public String getDinhtinh()
	{
	
		return this.dinhtinh;
	}

	
	public String getKiemdinhchatluong()
	{

		return this.kiemdinhchatluong;
	}

	
	public void setKiemdinhchatluong(String kiemdinhchatluong)
	{
		this.kiemdinhchatluong=kiemdinhchatluong;
		
	}

	
	public String[] getTieuchi_Dinhtinh()
	{
		return this.tieuchi_dinhtinh;
	}

	
	public void setTieuchi_Dinhtinh(String[] tieuchi_dinhtinh)
	{
		this.tieuchi_dinhtinh=tieuchi_dinhtinh;
	}

	@Override
	public void setDaytruyenSXId(String str) {
		
	}

	public ArrayList<ITaisan> gettaisan(String mats){
		String q="select pk_seq,ma,ten  from ERP_TAISANCODINH a where nhomtaisan_fk in (select nhomtaisan_fk "
				+ "from ERP_NHOMTAISAN_CONGDUNG b inner join ERP_CONGDUNG c on b.congdung_fk=c.pk_seq"
				+ " where b.congdung_fk= 100000 and c.congty_fk='"+this.ctyId+"') "
				+ " and a.congty_fk="+this.ctyId;
		if(mats.length()>0){
			q+=" and ma like '%"+mats+"%'";
		}
		ArrayList<ITaisan> rs = new ArrayList<ITaisan>();
		ResultSet taisan;
		try{
			taisan=db.get(q);
			while(taisan.next()){
				ITaisan ts = new Taisan();
				ts.setPk_seq(taisan.getString("pk_seq"));
				ts.setMa(taisan.getString("ma"));
				ts.setTen(taisan.getString("ten"));
				rs.add(ts);
			}
			taisan.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		db.shutDown();
		return rs;
	}

	@Override
	public ResultSet getPhongbanRs() {
		return phongbanRs;
	}

	@Override
	public void getPhongbanRs(ResultSet phongbanRs) {
		this.phongbanRs = phongbanRs;
	}

	@Override
	public String[] getPhongbanId() {
		return this.phongbanId;
	}

	@Override
	public void setPhongbanId(String[] phongbanId) {
		this.phongbanId = phongbanId;
	}

	@Override
	public ResultSet getGiaidoanRs() {
		return this.giaidoanRs;
	}

	@Override
	public void getGiaidoanRs(ResultSet giaidoanRs) {
		this.giaidoanRs = giaidoanRs;
	}

	@Override
	public String[] getGiaidoanId() {
		return this.giaidoanId;
	}

	@Override
	public void setGiaidoanId(String[] giaidoanId) {
		this.giaidoanId = giaidoanId;
	}

	@Override
	public String[] getSttpb() {
		return this.sttpb;
	}

	@Override
	public void setSttpb(String[] sttpb) {
		this.sttpb = sttpb;
	}

	public String getYckn() {
		return yckn;
	}

	public void setYckn(String yckn) {
		this.yckn = yckn;
	}

	public ResultSet getLhsknRs() {
		return LhsknRs;
	}

	public void setLhsknRs(ResultSet lhsknRs) {
		LhsknRs = lhsknRs;
	}

	public String getLhsknList() {
		return lhsknList;
	}

	public void setLhsknList(String lhsknList) {
		this.lhsknList = lhsknList;
	}
	
}
