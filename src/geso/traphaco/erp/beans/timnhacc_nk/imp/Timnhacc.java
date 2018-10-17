package geso.traphaco.erp.beans.timnhacc_nk.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.timnhacc_nk.imp.Sanpham;
import geso.traphaco.erp.beans.timnhacc_nk.*;

public class Timnhacc implements ITimnhacc{
	
	String congtyId;
	String nppId;
	String userId;
	String id;
	String nccId;
	ResultSet nccRs;
	String spId;
	List<ISanpham> spList;
	String trangthai;
	String diengiai;
	String dnmhId;
	ResultSet dnmhRs;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	dbutils db;
	Utility util;
	String active;
	String msg;
	String loaihanghoa;
	String hinhthuc;
	String nccids;
	
	public Timnhacc(String[] param)
	{
		this.db = new dbutils();
		this.id = param[0];
		this.dnmhId = param[1];
		this.ngaytao = param[2];
		this.nguoitao = param[3];
		this.ngaysua = param[4];
		this.nguoisua = param[5];
		this.diengiai = param[6];
		this.trangthai = param[7];
		this.msg = "";
		this.active = "0";
		this.loaihanghoa = "0";
		this.hinhthuc = "0";
		this.nccids = "";
		this.util=new Utility();
	}
	public Timnhacc(String id) {
		this.congtyId = "";
		this.nppId = "";
		this.userId = "";
		this.id = id;
		this.nccId = "";
		this.dnmhId = "";
		this.trangthai = "0";
		this.diengiai = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.msg = "";
		this.active = "0";
		this.loaihanghoa = "0";
		this.hinhthuc = "0";
		this.nccids = "";
		this.util=new Utility();
		this.db = new dbutils();
		/*if(id.length() > 0)
			this.init();*/
	}
	
	@Override
	public String getCongtyId() {
		
		return this.congtyId;
	}
	@Override
	public void setCongtyId(String congtyId) {
		
		this.congtyId = congtyId;
	}
	@Override
	public String getUserId() {
		
		return this.userId;
	}
	@Override
	public void setUserId(String userId) {
		
		this.userId = userId;
	}
	@Override
	public String getId() {
		
		return this.id;
	}
	@Override
	public void setId(String id) {
		
		this.id = id;
	}
	
	@Override
	public String getTrangthai() {
		
		return this.trangthai;
	}
	@Override
	public void setTrangthai(String trangthai) {
		
		this.trangthai = trangthai;
	}
	@Override
	public String getDiengiai() {
		
		return this.diengiai;
	}
	@Override
	public void setDiengiai(String diengiai) {
		
		this.diengiai = diengiai;
	}
	
	public void createRs() {
		//moi sua
		String query;
		if(dnmhId.trim().length()>0){
			query = "select distinct pk_seq from ERP_MUAHANG where  pk_seq='"+this.dnmhId+"' and PK_SEQ not in (select pk_seq from ERP_TIMNCC ";
 		}else{
 			query = "select distinct pk_seq from ERP_MUAHANG where  trangthai in (0,1) and PK_SEQ not in (select pk_seq from ERP_TIMNCC    ";
  		}
		if(this.id.trim().length() > 0) {
			query += " where pk_seq != '"+ this.id +"' ";
		}
		query += " ) ";
		System.out.println("[Don mua hang RS] " + query);
		this.dnmhRs = db.get(query);
		
		query = "select distinct pk_seq, ma, ten from erp_nhacungcap where trangthai in (0,1) and loaincc = '100002' and congty_fk = '"+ this.congtyId +"'";
		System.out.println("nccRs " + query);
		this.nccRs = db.get(query);
		
		if(dnmhId.trim().length() > 0)
		{
			System.out.println("vao day roi !!!!!!!");
			
			try
			{
				query = "select distinct loaihanghoa_fk from ERP_MUAHANG where pk_seq = '"+this.dnmhId+"'";
				System.out.println("loai hang hoa " + query);
				ResultSet rs = db.get(query);
				if(rs != null)
				{
					while(rs.next())
					{
						this.loaihanghoa = rs.getString("loaihanghoa_fk");
					}
				}
				
				if(this.loaihanghoa.trim().equals("0"))
				{
					query = "select distinct a.pk_seq, a.ma, a.ten,b.THANHTIEN, b.MUAHANG_FK from ERP_SANPHAM a, ERP_MUAHANG_SP b where a.pk_seq = b.sanpham_fk and b.MUAHANG_FK = '" + this.dnmhId +"'";
				} 	
				System.out.println("erp_denghimuahang_sp " + query);
				rs = db.get(query);
				if(rs != null)
				{ 
					List<ISanpham> lstSP = new ArrayList<ISanpham>();
				
					while (rs.next())
					{
						ISanpham sp = new Sanpham();
						sp.setId(rs.getString("pk_seq"));
						sp.setMasanpham(rs.getString("ma"));
						sp.setTensanpham(rs.getString("ten"));
						if(this.loaihanghoa.trim().equals("0"))
						{
							query = " select distinct NCC.PK_SEQ,NCC.MA, NCC.TEN "+
									"from ERP_NHACUNGCAP as NCC "+
									" left join ERP_LOAINHACUNGCAP as LNCC on NCC.LOAINHACUNGCAP_FK = LNCC.PK_SEQ "+
									" left join ERP_DUYETNHACUNGCAP as DNCC on NCC.PK_SEQ = DNCC.NHACUNGCAP_FK  "+
									" left join ERP_MUAHANG_SP as MH_SP on MH_SP.SANPHAM_FK = DNCC.SANPHAM_FK  "+
									" left join ERP_MUAHANG as MH on MH.PK_SEQ = MH_SP.MUAHANG_FK  "+ 
									" where LNCC.PK_SEQ ='100002' and MH_SP.SANPHAM_FK = '"+sp.getId()+"'"
											+ " and NCC.PK_SEQ in (select a.NHACUNGCAP_FK from ERP_DUYETNHACUNGCAP as a) "
											+ "and MH.trangthai in (0,1,2) and MH.congty_fk = '"+ this.congtyId +"' ";
						}
						System.out.println("query: "+query);
						 
						if(this.id.trim().length() > 0)
						{
							System.out.println("cong ty " + this.congtyId);
							if(this.loaihanghoa.trim().equals("0"))
							{
								query = " select a.PK_SEQ, a.MA, a.TEN, a.TEN_NGUOILIENHE, a.DIACHI, a.DIENTHOAI, (select REPLACE((sELECT distinct cast ( c.DONGIA as VARCHAR(100)) as [data()] FROM ERP_MUAHANG b inner join ERP_MUAHANG_SP c on b.PK_SEQ = c.MUAHANG_FK and c.SANPHAM_FK = '"+sp.getId()+"' where b.TRANGTHAI <> 3 and b.NHACUNGCAP_FK = a.PK_SEQ FOR XML PATH('') ) ,' ',', ')) as dongia, "
										+ " (select isnull(sum(c.SOLUONG), 0) as TONGLUONG from ERP_MUAHANG b inner join ERP_MUAHANG_SP c on b.PK_SEQ = c.MUAHANG_FK and c.SANPHAM_FK = '"+sp.getId()+"' where b.TRANGTHAI <> 3 and b.NHACUNGCAP_FK = a.PK_SEQ) as tongluong, e.ncc_fk  "+ 
										" from ERP_NHACUNGCAP a left join (select top 1 * from ERP_DANHGIANCC where SP_FK = '"+sp.getId()+"' order by PK_SEQ desc) as d on a.PK_SEQ = d.NCC_FK "+ 
										" left join ERP_TIMNCC_NCC e on a.PK_SEQ = e.NCC_FK and e.sp_fk = '"+sp.getId()+"'";
							}
							else if(this.loaihanghoa.trim().equals("1"))
							{
								query = " select a.PK_SEQ, a.MA, a.TEN, a.TEN_NGUOILIENHE, a.DIACHI, a.DIENTHOAI, (select REPLACE((sELECT distinct cast ( c.DONGIA as VARCHAR(100)) as [data()] FROM ERP_MUAHANG b inner join ERP_MUAHANG_SP c on b.PK_SEQ = c.MUAHANG_FK and c.TAISAN_FK = '"+sp.getId()+"' where b.TRANGTHAI <> 3 and b.NHACUNGCAP_FK = a.PK_SEQ FOR XML PATH('') ) ,' ',', ')) as dongia, "
										+ " (select isnull(sum(c.SOLUONG), 0) as TONGLUONG from ERP_MUAHANG b inner join ERP_MUAHANG_SP c on b.PK_SEQ = c.MUAHANG_FK and c.TAISAN_FK = '"+sp.getId()+"' where b.TRANGTHAI <> 3 and b.NHACUNGCAP_FK = a.PK_SEQ) as tongluong, e.ncc_fk "+ 
										" from ERP_NHACUNGCAP a "/*left join (select top 1 * from ERP_DANHGIANCC where TAISAN_FK = '"+sp.getId()+"' order by PK_SEQ desc) as d on a.PK_SEQ = d.NCC_FK "*/+ 
										" left join ERP_TIMNCC_NCC e on a.PK_SEQ = e.NCC_FK and e.TAISAN_FK = '"+sp.getId()+"'";
							}
							else if(this.loaihanghoa.trim().equals("2"))
							{
								query = " select a.PK_SEQ, a.MA, a.TEN, a.TEN_NGUOILIENHE, a.DIACHI, a.DIENTHOAI, (select REPLACE((sELECT distinct cast ( c.DONGIA as VARCHAR(100)) as [data()] FROM ERP_MUAHANG b inner join ERP_MUAHANG_SP c on b.PK_SEQ = c.MUAHANG_FK and c.CHIPHI_FK = '"+sp.getId()+"' where b.TRANGTHAI <> 3 and b.NHACUNGCAP_FK = a.PK_SEQ FOR XML PATH('') ) ,' ',', ')) as dongia, "
										+ " (select isnull(sum(c.SOLUONG), 0) as TONGLUONG from ERP_MUAHANG b inner join ERP_MUAHANG_SP c on b.PK_SEQ = c.MUAHANG_FK and c.CHIPHI_FK = '"+sp.getId()+"' where b.TRANGTHAI <> 3 and b.NHACUNGCAP_FK = a.PK_SEQ) as tongluong, e.ncc_fk "+ 
										" from ERP_NHACUNGCAP a "/*left join (select top 1 * from ERP_DANHGIANCC where CHIPHI_FK = '"+sp.getId()+"' order by PK_SEQ desc) as d on a.PK_SEQ = d.NCC_FK "*/+ 
										" left join ERP_TIMNCC_NCC e on a.PK_SEQ = e.NCC_FK and e.CHIPHI_FK = '"+sp.getId()+"'";
							}
							else
							{
								query = " select a.PK_SEQ, a.MA, a.TEN, a.TEN_NGUOILIENHE, a.DIACHI, a.DIENTHOAI, (select REPLACE((sELECT distinct cast ( c.DONGIA as VARCHAR(100)) as [data()] FROM ERP_MUAHANG b inner join ERP_MUAHANG_SP c on b.PK_SEQ = c.MUAHANG_FK and c.CCDC_FK = '"+sp.getId()+"' where b.TRANGTHAI <> 3 and b.NHACUNGCAP_FK = a.PK_SEQ FOR XML PATH('') ) ,' ',', ')) as dongia, "
										+ " (select isnull(sum(c.SOLUONG), 0) as TONGLUONG from ERP_MUAHANG b inner join ERP_MUAHANG_SP c on b.PK_SEQ = c.MUAHANG_FK and c.CCDC_FK = '"+sp.getId()+"' where b.TRANGTHAI <> 3 and b.NHACUNGCAP_FK = a.PK_SEQ) as tongluong, e.ncc_fk "+ 
										" from ERP_NHACUNGCAP a " /*left join (select top 1 * from ERP_DANHGIANCC where CCDC_FK = '"+sp.getId()+"' order by PK_SEQ desc) as d on a.PK_SEQ = d.NCC_FK "*/+ 
										" left join ERP_TIMNCC_NCC e on a.PK_SEQ = e.NCC_FK and e.CCDC_FK = '"+sp.getId()+"'";
							}
							query += " and e.TIMNCC_FK = '"+ this.id +"' ";
						}
						NumberFormat formatter = new DecimalFormat("#,###,###");
						System.out.println("thong tin chi tiet sp ncc " + query);
						
						ResultSet rsncc = db.get(query);
						List<INhacungcap> lstncc = new ArrayList<INhacungcap>();
						if(rsncc != null)
						{
							System.out.println("abc ");
							while(rsncc.next())
							{  
								INhacungcap ncc = new Nhacungcap();
								ncc.setId(rsncc.getString("pk_seq"));
								System.out.println("A " + ncc.getId());
								ncc.setMa(rsncc.getString("MA")==null?"":rsncc.getString("MA"));
								ncc.setTen(rsncc.getString("TEN")==null?"":rsncc.getString("TEN")); 
								
								 								
								 
								/*if(this.id.trim().length() > 0)
									//ncc.setChon(ncc.getId()==null?"":ncc.getId());
								{	ncc.setChon(rsncc.getString("PK_SEQ")==null?"":rsncc.getString("PK_SEQ"));
									System.out.println("eee " + ncc.getChon());
								}else
									ncc.setChon("");
								lstncc.add(ncc);*/
								
								if(this.id.trim().length() > 0){
									ncc.setChon(rsncc.getString("ncc_fk")==null?"":rsncc.getString("ncc_fk"));
 									System.out.println("eee " + ncc.getChon());
								}
								else
									ncc.setChon("");
								lstncc.add(ncc);
							}
						} 
						sp.setNhacungcap(lstncc); 
						lstSP.add(sp);
					}
					this.spList = lstSP;
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public void init() {
		
		String query = "select distinct a.pk_seq, a.diengiai, a.trangthai, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua, a.denghimua_fk, a.hinhthuc from erp_timncc a, nhanvien b, nhanvien c ";
		query = query + " where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.pk_seq = '"+ this.id + "'"; 
		System.out.println("Cau init "+query);
		ResultSet rs =  this.db.get(query);
		try
        {
            while(rs.next())
			{
	        	this.id = rs.getString("pk_seq");
	        	this.diengiai = rs.getString("diengiai")==null?"":rs.getString("diengiai");
	        	this.trangthai = rs.getString("trangthai")==null?"0":rs.getString("trangthai");
	        	this.ngaytao = rs.getString("ngaytao");
	        	this.nguoitao = rs.getString("nguoitao");
	        	this.ngaysua = rs.getString("ngaysua");
	        	this.nguoisua = rs.getString("nguoisua");
	        	this.dnmhId = rs.getString("denghimua_fk");
	        	this.hinhthuc = rs.getString("hinhthuc");
	        	System.out.println("de nghi mua hang id " + this.dnmhId);
			}
			rs.close();
       	}
        catch (java.sql.SQLException e){}
		
		query = "select distinct ncc_fk from erp_timncc_ncc where timncc_fk = "+this.id;
		System.out.println("de nghi mua hang id " + query);
		rs =  this.db.get(query);
		try
        {
            while(rs.next())
			{
	        	this.nccids += rs.getString("ncc_fk") + ",";
	        	System.out.println("nccids " + this.nccids);
			}
			rs.close();
       	}
        catch (java.sql.SQLException e){}
		
		this.createRs();
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public boolean createTimNcc()
	{
		try
		{ 
			
			this.nppId = util.getIdNhapp(userId); 
			if(this.dnmhId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn đề nghị đặt hàng";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			//moi sua
			String query = " update ERP_MUAHANG set trangthai = '1' where pk_seq = "+ this.dnmhId;
			System.out.println("[denghimuahang]: " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the tao moi erp_timncc: " + query;
				db.getConnection().rollback();
				return false;
			}
			System.out.println("NHACUNGCAP: " + this.nccId);
			if( this.nppId == null || this.nppId.equals("null")){
				
				query = "insert into erp_timncc(trangthai, ngaytao, nguoitao, ngaysua, nguoisua, diengiai, DENGHIMUA_FK, congty_fk, npp_fk, hinhthuc) " +
						"values(0, '" + this.getDateTime() + "', '" + this.userId + "', '" + this.getDateTime() + "', '" + this.userId + "', N'" 
						+ this.diengiai + "', '" + this.dnmhId + "', '"+this.congtyId+"',null, '"+this.hinhthuc+"' )";
			} else {
				query = "insert into erp_timncc(trangthai, ngaytao, nguoitao, ngaysua, nguoisua, diengiai, DENGHIMUA_FK, congty_fk, npp_fk, hinhthuc) " +
						"values(0, '" + this.getDateTime() + "', '" + this.userId + "', '" + this.getDateTime() + "', '" + this.userId + "', N'" 
						+ this.diengiai + "', '" + this.dnmhId + "', '"+this.congtyId+"', '"+this.nppId+"', '"+this.hinhthuc+"' )";
			}
			
			
			System.out.println("1.Insert: " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the tao moi erp_timncc: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "select IDENT_CURRENT('erp_timncc') as Id";
			ResultSet rs = db.get(query);
			rs.next();
			this.id = rs.getString("Id");
			rs.close();
			
			if(this.hinhthuc.equals("0") && this.spList != null)
			{
				for (int i = 0; i < this.spList.size(); i++) {
					ISanpham sp = this.spList.get(i);
					List<INhacungcap> lstncc = sp.getNhacungcap(); 
					for (int j = 0; j < lstncc.size(); j++) {
						INhacungcap ncc = lstncc.get(j);
						if(ncc.getChon().trim().equals(ncc.getId().trim()))
						{
							if(this.loaihanghoa.trim().equals("0"))
							{
								query = "insert into erp_timncc_ncc(TIMNCC_FK, NCC_FK, sp_fk) " +
									"values('"+ this.id + "', '" + ncc.getId() + "', '" + sp.getId() + "' )";
							}/*
							else if(this.loaihanghoa.trim().equals("1"))
							{
								query = "insert into erp_timncc_ncc(TIMNCC_FK, NCC_FK, taisan_fk) " +
										"values('"+ this.id + "', '" + ncc.getId() + "', '" + sp.getId() + "' )";
							}
							else if(this.loaihanghoa.trim().equals("2"))
							{
								query = "insert into erp_timncc_ncc(TIMNCC_FK, NCC_FK, chiphi_fk) " +
										"values('"+ this.id + "', '" + ncc.getId() + "', '" + sp.getId() + "' )";
							}*/
							else
							{
								query = "insert into erp_timncc_ncc(TIMNCC_FK, NCC_FK, ccdc_fk) " +
										"values('"+ this.id + "', '" + ncc.getId() + "', '" + sp.getId() + "' )";
							}
							System.out.println("1.Insert: " + query);
							if(!db.update(query))
							{
								this.msg = "Khong the tao moi erp_timncc_ncc: " + query;
								db.getConnection().rollback();
								return false;
							}
						}
					}
				}
			}
			else
			{
				String sql = "";
				if(this.loaihanghoa.trim().equals("0"))
				{
					query = "select sanpham_fk from erp_denghimuahang_sp where denghi_fk = "+this.dnmhId;
					rs = db.get(query);
					while(rs.next())
					{
						sql = "insert into erp_timncc_ncc(TIMNCC_FK, NCC_FK, sp_fk) " +
							  "select '"+ this.id + "', pk_seq, "+rs.getString("sanpham_fk")+" from erp_nhacungcap where pk_seq in ("+this.nccids+")";
						System.out.println("1.Insert: " + sql);
						if(!db.update(sql))
						{
							this.msg = "Khong the tao moi erp_timncc_ncc: " + sql;
							db.getConnection().rollback();
							return false;
						}
					}
					rs.close();
				}
				else if(this.loaihanghoa.trim().equals("1"))
				{
					query = "select taisan_fk from erp_denghimuahang_sp where denghi_fk = "+this.dnmhId;
					rs = db.get(query);
					while(rs.next())
					{
						sql = "insert into erp_timncc_ncc(TIMNCC_FK, NCC_FK, taisan_fk) " +
							  "select '"+ this.id + "', pk_seq, "+rs.getString("taisan_fk")+" from erp_nhacungcap where pk_seq in ("+this.nccids+")";
						System.out.println("1.Insert: " + sql);
						if(!db.update(sql))
						{
							this.msg = "Khong the tao moi erp_timncc_ncc: " + sql;
							db.getConnection().rollback();
							return false;
						}
					}
					rs.close();
				}
				else if(this.loaihanghoa.trim().equals("2"))
				{
					query = "select chiphi_fk from erp_denghimuahang_sp where denghi_fk = "+this.dnmhId;
					rs = db.get(query);
					while(rs.next())
					{
						sql = "insert into erp_timncc_ncc(TIMNCC_FK, NCC_FK, chiphi_fk) " +
							  "select '"+ this.id + "', pk_seq, "+rs.getString("chiphi_fk")+" from erp_nhacungcap where pk_seq in ("+this.nccids+")";
						System.out.println("1.Insert: " + sql);
						if(!db.update(sql))
						{
							this.msg = "Khong the tao moi erp_timncc_ncc: " + sql;
							db.getConnection().rollback();
							return false;
						}
					}
					rs.close();
				}
				else
				{
					query = "select ccdc_fk from erp_denghimuahang_sp where denghi_fk = "+this.dnmhId;
					rs = db.get(query);
					while(rs.next())
					{
						sql = "insert into erp_timncc_ncc(TIMNCC_FK, NCC_FK, ccdc_fk) " +
							  "select '"+ this.id + "', pk_seq, "+rs.getString("ccdc_fk")+" from erp_nhacungcap where pk_seq in ("+this.nccids+")";
						System.out.println("1.Insert: " + sql);
						if(!db.update(sql))
						{
							this.msg = "Khong the tao moi erp_timncc_ncc: " + sql;
							db.getConnection().rollback();
							return false;
						}
					}
					rs.close();
				}
				
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e)
		{
			try 
			{
				db.getConnection().rollback();
				System.out.println("__EXCEPTION UPDATE: " + e.getMessage());
				e.printStackTrace();
				
				this.msg = "Lỗi khi tạo mới tìm nhà cung cấp: " + e.getMessage();
			} 
			catch (SQLException e1) {}
			
			return false;
		}
		return true;
	}
	
	public boolean updateTimNcc()
	{
		try
		{
			this.nppId = util.getIdNhapp(userId);
			if(this.dnmhId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn đề nghị đặt hàng";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			String query;
			if( this.nppId == null || this.nppId.equals("null")){
				query = "update erp_timncc set ngaysua = '"+this.getDateTime()+"', nguoisua = '"+this.userId+"', hinhthuc = '"+this.hinhthuc+"', "
					+ "diengiai = N'"+this.diengiai+"', DENGHIMUA_FK = '"+this.dnmhId+"',npp_fk = null where pk_seq = '"+this.id+"'";
			}else{
				query = "update erp_timncc set ngaysua = '"+this.getDateTime()+"', nguoisua = '"+this.userId+"', hinhthuc = '"+this.hinhthuc+"', "
						+ "diengiai = N'"+this.diengiai+"', DENGHIMUA_FK = '"+this.dnmhId+"', npp_fk = '"+this.nppId+"' where pk_seq = '"+this.id+"'";

			}
			System.out.println("2.Update: " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the cập nhật erp_timncc: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query="delete from erp_timncc_ncc where TIMNCC_FK="+this.id +" ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật erp_timncc_ncc: " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			if(this.hinhthuc.equals("0") && this.spList != null)
			{
				for (int i = 0; i < this.spList.size(); i++) {
					ISanpham sp = this.spList.get(i);
					List<INhacungcap> lstncc = sp.getNhacungcap(); 
					for (int j = 0; j < lstncc.size(); j++) {
						INhacungcap ncc = lstncc.get(j);
						if(ncc.getChon().trim().equals(ncc.getId().trim()))
						{
							if(this.loaihanghoa.trim().equals("0"))
							{
								query = "insert into erp_timncc_ncc(TIMNCC_FK, NCC_FK, sp_fk) " +
									"values('"+ this.id + "', '" + ncc.getId() + "', '" + sp.getId() + "' )";
							}/*
							else if(this.loaihanghoa.trim().equals("1"))
							{
								query = "insert into erp_timncc_ncc(TIMNCC_FK, NCC_FK, taisan_fk) " +
										"values('"+ this.id + "', '" + ncc.getId() + "', '" + sp.getId() + "' )";
							}
							else if(this.loaihanghoa.trim().equals("2"))
							{
								query = "insert into erp_timncc_ncc(TIMNCC_FK, NCC_FK, chiphi_fk) " +
										"values('"+ this.id + "', '" + ncc.getId() + "', '" + sp.getId() + "' )";
							}*/
							else
							{
								query = "insert into erp_timncc_ncc(TIMNCC_FK, NCC_FK, ccdc_fk) " +
										"values('"+ this.id + "', '" + ncc.getId() + "', '" + sp.getId() + "' )";
							}
							
							System.out.println("1.Insert: " + query);
							if(!db.update(query))
							{
								this.msg = "Khong the cap nhat erp_timncc_ncc: " + query;
								db.getConnection().rollback();
								return false;
							}
						}
					}
				}
			}
			else
			{
				System.out.println("aaaaaaaaaaaaaaaaa ");
				String sql = "";
				ResultSet rs;
				if(this.loaihanghoa.trim().equals("0"))
				{
					query = "select sanpham_fk from ERP_MUAHANG_SP where MUAHANG_FK = '"+this.dnmhId+"'";
					System.out.println("aaaaaaaaaaaaaaaaa "+query);
					rs = db.get(query);
					while(rs.next())
					{
						sql = "insert into erp_timncc_ncc(TIMNCC_FK, NCC_FK, sp_fk) " +
							  "select '"+ this.id + "', pk_seq, "+rs.getString("sanpham_fk")+" from erp_nhacungcap where pk_seq in ("+this.nccids+")";
						System.out.println("1.Insert: " + sql);
						if(!db.update(sql))
						{
							this.msg = "Khong the tao moi erp_timncc_ncc: " + sql;
							db.getConnection().rollback();
							return false;
						}
					}
					rs.close();
				}/*
				else if(this.loaihanghoa.trim().equals("1"))
				{
					query = "select taisan_fk from erp_denghimuahang_sp where denghi_fk = "+this.dnmhId;
					rs = db.get(query);
					while(rs.next())
					{
						sql = "insert into erp_timncc_ncc(TIMNCC_FK, NCC_FK, taisan_fk) " +
							  "select '"+ this.id + "', pk_seq, "+rs.getString("taisan_fk")+" from erp_nhacungcap where pk_seq in ("+this.nccids+")";
						System.out.println("1.Insert: " + sql);
						if(!db.update(sql))
						{
							this.msg = "Khong the tao moi erp_timncc_ncc: " + sql;
							db.getConnection().rollback();
							return false;
						}
					}
					rs.close();
				}
				else if(this.loaihanghoa.trim().equals("2"))
				{
					query = "select chiphi_fk from erp_denghimuahang_sp where denghi_fk = "+this.dnmhId;
					rs = db.get(query);
					while(rs.next())
					{
						sql = "insert into erp_timncc_ncc(TIMNCC_FK, NCC_FK, chiphi_fk) " +
							  "select '"+ this.id + "', pk_seq, "+rs.getString("chiphi_fk")+" from erp_nhacungcap where pk_seq in ("+this.nccids+")";
						System.out.println("1.Insert: " + sql);
						if(!db.update(sql))
						{
							this.msg = "Khong the tao moi erp_timncc_ncc: " + sql;
							db.getConnection().rollback();
							return false;
						}
					}
					rs.close();
				}*/
				else
				{
					query = "select ccdc_fk from erp_denghimuahang_sp where denghi_fk = "+this.dnmhId;
					rs = db.get(query);
					while(rs.next())
					{
						sql = "insert into erp_timncc_ncc(TIMNCC_FK, NCC_FK, ccdc_fk) " +
							  "select '"+ this.id + "', pk_seq, "+rs.getString("ccdc_fk")+" from erp_nhacungcap where pk_seq in ("+this.nccids+")";
						System.out.println("1.Insert: " + sql);
						if(!db.update(sql))
						{
							this.msg = "Khong the tao moi erp_timncc_ncc: " + sql;
							db.getConnection().rollback();
							return false;
						}
					}
					rs.close();
				}
				
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e)
		{
			try 
			{
				db.getConnection().rollback();
				System.out.println("__EXCEPTION UPDATE: " + e.getMessage());
				e.printStackTrace();
				
				this.msg = "Lỗi khi cập nhật tìm nhà cung cấp: " + e.getMessage();
			} 
			catch (SQLException e1) {}
			
			return false;
		}
		return true;
	}
	
	@Override
	public void close() {
		
		try{
			
			if(dnmhRs!=null){
				dnmhRs.close();
			}
			if(nccRs!=null){
				nccRs.close();
			}
			db.shutDown();
			
		}catch(Exception er){
			
		}
	}

	@Override
	public String getDnmhId() {
		
		return this.dnmhId;
	}

	@Override
	public void setDnmhId(String dnmhId) {
		
		this.dnmhId = dnmhId;
	}

	@Override
	public ResultSet getDnmhRs() {
		
		return this.dnmhRs;
	}

	@Override
	public void setDnmhRs(ResultSet dnmhRs) {
		
		this.dnmhRs = dnmhRs;
	}

	@Override
	public String getNgaytao() {
		
		return this.ngaytao;
	}

	@Override
	public void setNgaytao(String ngaytao) {
		
		this.ngaytao = ngaytao;
	}

	@Override
	public String getNgaysua() {
		
		return this.ngaysua;
	}

	@Override
	public void setNgaysua(String ngaysua) {
		
		this.ngaysua = ngaysua;
	}

	@Override
	public String getNguoitao() {
		
		return this.nguoitao;
	}

	@Override
	public void setNguoitao(String nguoitao) {
		
		this.nguoitao = nguoitao;
	}

	@Override
	public String getNguoisua() {
		
		return this.nguoisua;
	}

	@Override
	public void setNguoisua(String nguoisua) {
		
		this.nguoisua = nguoisua;
	}

	@Override
	public String getMessage() {
		
		return this.msg;
	}

	@Override
	public void setMessage(String msg) {
		
		this.msg = msg;
	}
	
	public void setActive(String active) 
	{
		this.active = active;
	}

	public String getActive()
	{
		return this.active;
	}
	@Override
	public String getSpId() {
		
		return this.spId;
	}
	@Override
	public void setSpId(String spId) {
		
		this.spId = spId;
	}
	@Override
	public List<ISanpham> getSpList() {
		
		return this.spList;
	}
	@Override
	public void setSpList(List<ISanpham> spList) {
		
		this.spList = spList;
	}
	@Override
	public void setLoaihh(String loaihh) {
		
		this.loaihanghoa = loaihh;
	}
	@Override
	public String getLoaihh() {
		
		return this.loaihanghoa;
	}
	@Override
	public void setHinhthuc(String hinhthuc) {
		this.hinhthuc = hinhthuc;
	}
	@Override
	public String getHinhthuc() {
		return this.hinhthuc;
	}
	@Override
	public void setNccIds(String nccids) {
		this.nccids = nccids;
	}
	@Override
	public String getNccIds() {
		return this.nccids;
	}
	@Override
	public ResultSet getNccRs() {
		return this.nccRs;
	}
	@Override
	public void setNccRs(ResultSet nccRs) {
		this.nccRs = nccRs;
	}
}
