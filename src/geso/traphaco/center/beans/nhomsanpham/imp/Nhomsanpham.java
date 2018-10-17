package geso.traphaco.center.beans.nhomsanpham.imp;

import geso.traphaco.center.beans.nhomsanpham.INhomsanpham;
import geso.traphaco.center.db.sql.dbutils;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

public class Nhomsanpham implements INhomsanpham, Serializable
{
	private static final long serialVersionUID = -9217977546733690415L;
	String id;
	String parent;
	String ten;
	String diengiai;
	String thanhvien;
	String lnhom;
	String trangthai;
	String ngaytao;
	String ngaysua;
	String nguoitao;
	String nguoisua;
	String msg;
	ResultSet sanpham_mienbac;
	ResultSet sanpham_miennam;
	ResultSet sanpham_mientrung;
	ResultSet nspList;
	ResultSet nspSelected;
	ResultSet spList;
	ResultSet spSelected;

	ResultSet dvkdList;
	ResultSet nhList;
	ResultSet clList;
	String dvkdId;
	String nhId;
	String clId;
	String[] nhom;
	String[] sanpham;
	boolean search = false;
	dbutils db;

	public Nhomsanpham(String[] param)
	{
		this.id = param[0];
		this.ten = param[1];
		this.diengiai = param[2];
		this.thanhvien = param[3];
		this.trangthai = param[4];
		this.ngaytao = param[5];
		this.ngaysua = param[6];
		this.nguoitao = param[7];
		this.nguoisua = param[8];
		this.parent = param[9];
		this.lnhom = param[10];
		this.msg = "";
		this.dvkdId = "0";
		this.nhId = "0";
		this.clId = "0";
		this.db = new dbutils();
	}

	public Nhomsanpham()
	{
		this.id = "";
		this.ten = "";
		this.diengiai = "";
		this.thanhvien = "2";
		this.lnhom = "1";
		this.trangthai = "";
		this.ngaytao = "";
		this.ngaysua = "";
		this.nguoitao = "";
		this.nguoisua = "";
		this.parent = "";
		this.msg = "";
		this.dvkdId = "0";
		this.nhId = "0";
		this.clId = "0";
		this.db = new dbutils();
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getParent()
	{
		return this.parent;
	}

	public void setParent(String parent)
	{
		this.parent = parent;
	}

	public String getTen()
	{
		return this.ten;
	}

	public void setTen(String ten)
	{
		this.ten = ten;
	}

	public String getDiengiai()
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai)
	{
		this.diengiai = diengiai;
	}

	public String getThanhvien()
	{
		return this.thanhvien;
	}

	public void setThanhvien(String thanhvien)
	{
		this.thanhvien = thanhvien;
	}

	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	public String getNgaytao()
	{
		return this.ngaytao;

	}

	public void setNgaytao(String ngaytao)
	{
		this.ngaytao = ngaytao;
	}

	public String getNgaysua()
	{
		return this.ngaysua;

	}

	public void setNgaysua(String ngaysua)
	{
		this.ngaysua = ngaysua;
	}

	public String getNguoitao()
	{
		return this.nguoitao;
	}

	public void setNguoitao(String nguoitao)
	{
		this.nguoitao = nguoitao;
	}

	public String getNguoisua()
	{
		return this.nguoisua;
	}

	public void setNguoisua(String nguoisua)
	{
		this.nguoisua = nguoisua;
	}

	public String getMessage()
	{
		return this.msg;
	}

	public void setMessage(String msg)
	{
		this.msg = msg;
	}

	public ResultSet getNspList()
	{
		return this.nspList;
	}

	public void setNspList(ResultSet nspList)
	{
		this.nspList = nspList;
	}

	public ResultSet getSpList()
	{
		return this.spList;
	}

	public void setSpList(ResultSet spList)
	{
		this.spList = spList;
	}

	public ResultSet getSpSelected()
	{
		return this.spSelected;
	}

	public void setSpSelected(ResultSet spSelected)
	{
		this.spSelected = spSelected;
	}


	public ResultSet getDvkdList()
	{
		return this.dvkdList;
	}

	public void setDvkdList(ResultSet dvkdList)
	{
		this.dvkdList = dvkdList;
	}

	public ResultSet getNhList()
	{
		return this.nhList;
	}

	public void setNhList(ResultSet nhList)
	{
		this.nhList = nhList;
	}

	public ResultSet getCLList()
	{
		return this.clList;
	}

	public void setClList(ResultSet clList)
	{
		this.clList = clList;
	}

	public String getDvkdId()
	{
		return this.dvkdId;
	}

	public void setDvkdId(String dvkdId)
	{
		this.dvkdId = dvkdId;
	}

	public String getNhId()
	{
		return this.nhId;
	}

	public void setNhId(String nhId)
	{
		this.nhId = nhId;
	}

	public String getClId()
	{
		return this.clId;
	}

	public void setClId(String clId)
	{
		this.clId = clId;
	}


	public String[] getNhomsp()
	{
		return this.nhom;
	}

	public void setNhomsp(String[] nhom)
	{
		this.nhom = nhom;
	}

	public String[] getSanpham()
	{
		return this.sanpham;
	}

	public void setSanpham(String[] sanpham)
	{
		this.sanpham = sanpham;
	}

	public ResultSet getSanpham_mienbac()
	{
		return sanpham_mienbac;
	}

	public void setSanpham_mienbac(ResultSet sanpham_mienbac)
	{
		this.sanpham_mienbac = sanpham_mienbac;
	}

	public ResultSet getSanpham_miennam()
	{
		return sanpham_miennam;
	}

	public void setSanpham_miennam(ResultSet sanpham_miennam)
	{
		this.sanpham_miennam = sanpham_miennam;
	}

	public ResultSet getSanpham_mientrung()
	{
		return sanpham_mientrung;
	}

	public void setSanpham_mientrung(ResultSet sanpham_mientrung)
	{
		this.sanpham_mientrung = sanpham_mientrung;
	}

	public boolean saveNewNsp()
	{
		String command = "";

		try
		{
			this.db.getConnection().setAutoCommit(false);

			if (this.thanhvien.equals("1"))
			{
				command = "insert into NHOMSANPHAM(diengiai, nsp_parent_fk, loaithanhvien, ngaytao, ngaysua, nguoitao, nguoisua, trangthai, ten, type, loainhom) "
						+ " values(N'"
						+ this.diengiai
						+ "', '0', '1', '"
						+ this.ngaytao
						+ "', '"
						+ this.ngaysua
						+ "', '"
						+ this.nguoitao
						+ "', '"
						+ this.nguoisua
						+ "', '1', '"
						+ this.ten + "','0', '0')";
				System.out.println("1.Insert Nhom sanpham: " + command);
				if (!this.db.update(command))
				{
					this.msg = "Khong the tao moi nhomsanpham: " + command;
					db.getConnection().rollback();
					return false;
				}

				command = "select IDENT_CURRENT('NHOMSANPHAM') as nspId";
				ResultSet rs = this.db.get(command);
				rs.next();
				this.id = rs.getString("nspId");

				if (this.nhom != null)
				{
					String[] nhomList = this.nhom;
					int size = (this.nhom).length;
					int m = 0;
					while (m < size)
					{
						command = "update NHOMSANPHAM set nsp_parent_fk ='" + this.id + "' where pk_seq = '" + nhomList[m] + "'";
						System.out.println("2.Insert nhomsanpham_sanpham: " + command);

						if (!this.db.update(command))
						{
							this.msg = "Khong the tao moi NHOMSANPHAM: " + command;
							db.getConnection().rollback();
							return false;
						}
						m++;
					}
				}
			} 
			else
			{
				command = "insert into NHOMSANPHAM(diengiai, nsp_parent_fk, loaithanhvien, ngaytao, ngaysua, nguoitao, nguoisua, trangthai, ten, type, loainhom) "
						+ " values(N'"
						+ this.diengiai
						+ "', '0', '2', '"
						+ this.ngaytao
						+ "', '"
						+ this.ngaysua
						+ "', '"
						+ this.nguoitao
						+ "', '"
						+ this.nguoisua
						+ "', '1', N'"
						+ this.ten + "','0', '" + this.lnhom + "')";

				System.out.println("1.Insert Nhom sanpham: " + command);
				if (!this.db.update(command))
				{
					this.msg = "Khong the tao moi nhomsanpham: " + command;
					db.getConnection().rollback();
					return false;
				}

				command = "select IDENT_CURRENT('NHOMSANPHAM') as nspId";
				ResultSet rs = this.db.get(command);
				rs.next();
				this.id = rs.getString("nspId");

				if (this.sanpham != null)
				{
					String[] sanphamList = this.sanpham;
					int size = (this.sanpham).length;
					int m = 0;

					while (m < size)
					{
						command = "insert into nhomsanpham_sanpham(sp_fk, nsp_fk) values('" + sanphamList[m] + "', '" + this.id + "')";
						System.out.println("2.Insert nhomsanpham_sanpham: " + command);

						if (!this.db.update(command))
						{
							this.msg = "Khong the tao moi nhomsanpham_sanpham: " + command;
							System.out.println("11.Eroor: " + this.msg);
							db.getConnection().rollback();
							return false;
						}
						m++;
					}
				 }

			}

			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e1)
		{
			System.out.println("Exception: " + e1.getMessage());
			e1.printStackTrace();
			db.update("rollback");
		}

		return true;
	}

	public boolean updateNsp()
	{

		String command;

		command = "update nhomsanpham set ten =N'" + this.ten
				+ "', diengiai = N'" + this.diengiai + "',ngaysua ='"
				+ this.ngaysua + "', trangthai ='" + this.trangthai
				+ "',nguoisua ='" + this.nguoisua + "',loainhom =" + this.lnhom
				+ " where pk_seq = '" + this.id + "'";
		System.out.println("update : " + command);
		this.db.update(command);

		if (this.thanhvien.equals("1"))
		{
			command = "select pk_seq from nhomsanpham where nsp_parent_fk ='" + this.id + "'";
			ResultSet rs = this.db.get(command);
			try
			{
				while (rs.next())
				{
					String pk_seq = rs.getString("pk_seq");
					command = "update nhomsanpham set nsp_parent_fk = '0' where pk_seq ='" + pk_seq + "'";
					this.db.update(command);
				}
			} 
			catch (Exception e)
			{

			}

			if (this.nhom != null)
			{
				String[] nhomList = this.nhom;
				int size = (this.nhom).length;
				int m = 0;
				while (m < size)
				{
					if (nhomList[m] != null)
					{
						command = "update nhomsanpham set nsp_parent_fk ='" + this.id + "' where pk_seq = '" + nhomList[m] + "'";
						System.out.println("2.Insert nhomsanpham_sanpham: " + command);
						this.db.update(command);

					}
					m++;
				}

			}
		} 
		else
		{
			command = "delete from nhomsanpham_sanpham where nsp_fk = '" + this.id + "'";
			this.db.update(command);

			if (this.sanpham != null)
			{
				String[] sanphamList = this.sanpham;
				int size = (this.sanpham).length;
				int m = 0;

				while (m < size)
				{
					command = "insert into nhomsanpham_sanpham(sp_fk, nsp_fk) values('" + sanphamList[m] + "','" + this.id + "')";
					this.db.update(command);
					m++;
				}
			}
		}
		return true;
	}

	private ResultSet createDvkdRS()
	{

		// ResultSet dvkdRS =
		// this.db.get("select pk_seq, diengiai from donvikinhdoanh where trangthai='1' ");
		ResultSet dvkdRS = this.db
				.get("select a.pk_seq, a.diengiai from donvikinhdoanh a,Nhacungcap_dvkd b where a.pk_seq = b.dvkd_fk and a.trangthai='1' and b.checked ='1'");

		return dvkdRS;
	}

	private ResultSet createNhRS()
	{
		ResultSet nhRS;
		if (!this.dvkdId.equals("0"))
		{
			nhRS = this.db
					.get("select pk_seq, ten from nhanhang where trangthai='1' and dvkd_fk='"
							+ this.dvkdId + "'");
		} else
		{
			nhRS = null;
		}

		return nhRS;

	}

	private ResultSet createClRS()
	{
		ResultSet clRS;

		if (!this.nhId.equals("0"))
		{
			clRS = this.db
					.get("select distinct a.pk_seq, a.ten from chungloai a, nhanhang_chungloai b where trangthai='1' and a.pk_seq = b.cl_fk and b.nh_fk = '"
							+ this.nhId + "'");
		} else
		{
			clRS = null;
		}
		return clRS;

	}

	private void createNspRS()
	{
		String sql = "";
		if (this.id.length() > 0)
		{
			sql = "select pk_seq, ten, diengiai, nsp_parent_fk from nhomsanpham where nsp_parent_fk ='"
					+ this.id
					+ "' and trangthai = '1' union select pk_seq, ten, diengiai, nsp_parent_fk from nhomsanpham where nsp_parent_fk = '0' and trangthai = '1' and pk_seq <> '"
					+ this.id
					+ "' and pk_seq not in (select nsp_parent_fk from nhomsanpham where nsp_parent_fk <>0) order by ten";
			this.nspList = this.db.get(sql);

	   	} else
		{
			sql = "select * from nhomsanpham where nsp_parent_fk = '0' and trangthai = '1' and pk_seq not in (select nsp_parent_fk from nhomsanpham where nsp_parent_fk <>0)  order by ten";
			this.nspList = this.db.get(sql);
		}
		System.out.println("NSP List : " + sql);
	}

	private void createSpRS()
	{
		String query;
		String temp = "";
		if (this.id.length() > 0)
		{
			if (this.sanpham != null)
			{
				query = "select pk_seq, ma, ten from ERP_SANPHAM where LOAISANPHAM_FK in ( 100002, 100003, 100004, 100005, 100006, 100007,  100013, 100000 ) ";
				temp = "select pk_seq from ERP_SANPHAM where LOAISANPHAM_FK in ( 100002, 100003, 100004, 100005, 100006, 100007,  100013, 100000 ) ";
				
				String spIds = "";
				for (int i = 0; i < this.sanpham.length; i++)
				{
					spIds += this.sanpham[i];
					if( i < this.sanpham.length - 1 )
						spIds += ",";
				}
				
				if( spIds.trim().length() > 0 )
				{
					query += " and pk_seq in ( " + spIds + " ) ";
					temp += " and pk_seq in ( " + spIds + " ) ";
				}
			} 
			else
			{
				query = " select a.pk_seq, a.ma, a.ten from ERP_SANPHAM a, nhomsanpham_sanpham b " + 
						" where a.pk_seq = b.sp_fk and b.nsp_fk = '" + this.id + "' and a.LOAISANPHAM_FK in ( 100002, 100003, 100004, 100005, 100006, 100007,  100013, 100000 ) ";

			}

			System.out.print(query);
			this.spSelected = this.db.get(query);
		} 
		else
		{
			if (this.sanpham != null)
			{
				query = "select pk_seq, ma, ten from ERP_SANPHAM where LOAISANPHAM_FK in ( 100002, 100003, 100004, 100005, 100006, 100007,  100013, 100000 ) ";
				temp = "select pk_seq from ERP_SANPHAM where LOAISANPHAM_FK in ( 100002, 100003, 100004, 100005, 100006, 100007,  100013, 100000 ) ";
				
				String spIds = "";
				for (int i = 0; i < this.sanpham.length; i++)
				{
					spIds += this.sanpham[i];
					if( i < this.sanpham.length - 1 )
						spIds += ",";
				}
				
				if( spIds.trim().length() > 0 )
				{
					query += " and pk_seq in ( " + spIds + " ) ";
					temp += " and pk_seq in ( " + spIds + " ) ";
				}

				this.spSelected = this.db.get(query);
			}
		}

		if (this.id.length() > 0)
		{
			query = "select pk_seq, ma, ten from ERP_SANPHAM " + 
					" where LOAISANPHAM_FK in ( 100002, 100003, 100004, 100005, 100006, 100007,  100013, 100000 ) and trangthai = '1' " + 
					"	 and pk_seq not in (select sp_fk from nhomsanpham_sanpham where nsp_fk = '" + this.id + "') " + 
					"	 and pk_seq not in (select sp_fk from nhomsanpham_sanpham where nsp_fk in ( select pk_seq from NHOMSANPHAM  where loainhom = '" + this.lnhom + "' and pk_seq != '" + this.id + "' )   )	";
		} 
		else
		{
			query = "select pk_seq, ma, ten from ERP_SANPHAM " + 
					" where LOAISANPHAM_FK in ( 100002, 100003, 100004, 100005, 100006, 100007,  100013, 100000 ) and trangthai = '1'" + 
					"	 and pk_seq not in (select sp_fk from nhomsanpham_sanpham where nsp_fk in ( select pk_seq from NHOMSANPHAM  where loainhom = '" + this.lnhom + "'  )   )	";
		}
		
		if (!this.dvkdId.equals("0"))
		{
			query = query + " and dvkd_fk ='" + this.dvkdId + "'";

		}

		if (this.sanpham != null)
		{
			query = query + " and pk_seq not in (" + temp + ")";
		}
		
		query = query + " order by ten ";
		this.spList = this.db.get(query);

	}

	public void UpdateRS()
	{
		this.dvkdList = createDvkdRS();
		this.nhList = createNhRS();
		this.clList = createClRS();

		if (this.thanhvien.equals("1"))
		{
			createNspRS();
		} 
		else
		{
			createSpRS();
		}
	}

	public String getLoainhom()
	{

		return lnhom;
	}

	public void setLoainhom(String lnhom)
	{

		this.lnhom = lnhom;
	}



	@Override
	public boolean saveNhomSP(HttpServletRequest request)
	{

		try
		{
			db.getConnection().setAutoCommit(false);

			String query="";
			if(Integer.parseInt(this.id)<=100004)
			{
			 query = "update nhomsanpham set ten =N'" + this.ten
					+ "', diengiai = N'" + this.diengiai + "',ngaysua ='"
					+ this.ngaysua + "', trangthai ='" + this.trangthai
					+ "',nguoisua ='" + this.nguoisua + "',loainhom =" + this.lnhom
					+ " where pk_seq = '" + this.id + "'";
					if(!db.update(query))
					{
						db.getConnection().rollback();
						this.msg = "Lỗi xảy ra khi cập nhật thông tin";
						return false;
					}
			}
			else
			{
				 query = "update NHOMSANPHAM set ten =N'" + this.ten
							+ "', diengiai = N'" + this.diengiai + "',ngaysua ='"
							+ this.ngaysua + "', trangthai ='" + this.trangthai
							+ "',nguoisua ='" + this.nguoisua + "',loainhom =" + this.lnhom
							+ " where pk_seq = '" + this.id + "'";
							if(!db.update(query))
							{
								db.getConnection().rollback();
								this.msg = "Lỗi xảy ra khi cập nhật thông tin";
								return false;
							}
			}
			String[] nhomB = request.getParameterValues("Bcb");
			String[] nhomN = request.getParameterValues("Ncb");
			String[] nhomT = request.getParameterValues("Tcb");
			if(Integer.parseInt( this.id)<=100004)
			{
				query="insert into nhomsanpham_log(VUNG,NHOM,SANPHAM_FKs,nguoisua) "+
							"select 100001 as VUNG_FK ,a.PK_SEQ,(select	REPLACE((sELECT distinct xx.SP_FK as [data()] "+   
							"		FROM NHOMSANPHAM_SANPHAM xx  "+
							"		where xx.NSP_FK=a.PK_SEQ "+
							"		FOR XML PATH('') ) ,' ',',')) as spNhom,"+this.nguoisua+
							" from NHOMSANPHAM a 		 "+
							" where a.pk_seq="+this.id +
							"union  "+
							"select 100002 as VUNG_FK ,a.PK_SEQ,(select	REPLACE((sELECT distinct xx.SP_FK as [data()] "+   
							"		FROM NHOMSANPHAM_MIENNAM_SANPHAM xx  "+
							"		where xx.NSP_FK=a.PK_SEQ "+
							"		FOR XML PATH('') ) ,' ',','))as spNhom ,"+this.nguoisua+
							"  from NHOMSANPHAM a "+
							" where a.pk_seq="+this.id +
							" union  "+
							"select 100003 as VUNG_FK ,a.PK_SEQ,(select	REPLACE((sELECT distinct xx.SP_FK as [data()] "+  
							"		FROM NHOMSANPHAM_MIENTRUNG_SANPHAM xx  "+
							"		where xx.NSP_FK=a.PK_SEQ "+
							"		FOR XML PATH('') ) ,' ',',')) as spNhom ,"+this.nguoisua+
							" from NHOMSANPHAM a"+
							" where a.pk_seq="+this.id ;
			}
			else
			{
				query="insert into nhomsanpham_log(VUNG,NHOM,SANPHAM_FKs,nguoisua) "+
						"select 100001 as VUNG_FK ,a.PK_SEQ,(select	REPLACE((sELECT distinct xx.SP_FK as [data()] "+   
						"		FROM NHOMSANPHAM_MIENBAC xx  "+
						"		where xx.NSP_FK=a.PK_SEQ "+
						"		FOR XML PATH('') ) ,' ',',')) as spNhom,"+this.nguoisua+
						" from NHOMSANPHAM a 		 "+
						" where a.pk_seq="+this.id +
						"union  "+
						"select 100002 as VUNG_FK ,a.PK_SEQ,(select	REPLACE((sELECT distinct xx.SP_FK as [data()] "+   
						"		FROM NHOMSANPHAM_MIENNAM xx  "+
						"		where xx.NSP_FK=a.PK_SEQ "+
						"		FOR XML PATH('') ) ,' ',','))as spNhom ,"+this.nguoisua+
						"  from NHOMSANPHAM a "+
						" where a.pk_seq="+this.id +
						" union  "+
						"select 100003 as VUNG_FK ,a.PK_SEQ,(select	REPLACE((sELECT distinct xx.SP_FK as [data()] "+  
						"		FROM NHOMSANPHAM_MIENTRUNG xx  "+
						"		where xx.NSP_FK=a.PK_SEQ "+
						"		FOR XML PATH('') ) ,' ',',')) as spNhom ,"+this.nguoisua+
						" from NHOMSANPHAM a"+
						" where a.pk_seq="+this.id ;
			}
			
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = "Lỗi xảy ra khi cập nhật thông tin";
				return false;
			}
			if(Integer.parseInt(this.id)<=100004)
			{
			 query = "delete from NhomSanPHAM_SANPHAM WHERE NSP_FK='" + this.id + "'";
			}
			else
			{
				 query = "delete from NHOMSANPHAM_MIENBAC WHERE NSP_FK='" + this.id + "'";
			}
			if (!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = "Lỗi xảy ra khi cập nhật thông tin";
				return false;
			}

			if (nhomB != null)
			{
				for (int i = 0; i < nhomB.length; i++)
				{
					if(Integer.parseInt(this.id)<=100004)
					{
						query = "insert into NhomSanPHAM_SANPHAM(nsp_fk,sp_Fk)  select '" + this.id + "','" + nhomB[i] + "'";
					}
					else
					{
						query = "insert into NHOMSANPHAM_MIENBAC(nsp_fk,sp_Fk)  select '" + this.id + "','" + nhomB[i] + "'";
					}
					if (!db.update(query))
					{
						System.out.println("_________query " + query);
						db.getConnection().rollback();
						this.msg = "Lỗi xảy ra khi cập nhật thông tin";
						return false;
					}
				}
			}
			if(Integer.parseInt(this.id)<=100004)
			{
				query = "delete from NHOMSANPHAM_MIENTRUNG_SANPHAM WHERE NSP_FK='" + this.id + "'";
			}
			else
			{
				query = "delete from NHOMSANPHAM_MIENTRUNG WHERE NSP_FK='" + this.id + "'";
			}
			if (!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = "Lỗi xảy ra khi cập nhật thông tin";
				return false;
			}

			if (nhomT != null)
			{
				for (int i = 0; i < nhomT.length; i++)
				{
					if(Integer.parseInt(this.id)<=100004)
					{
					query = "insert into NHOMSANPHAM_MIENTRUNG_SANPHAM(nsp_fk,sp_Fk)  select '" + this.id + "','" + nhomT[i] + "'";
					}
					else
					{
						query = "insert into NHOMSANPHAM_MIENTRUNG(nsp_fk,sp_Fk)  select '" + this.id + "','" + nhomT[i] + "'";
						
					}
					if (!db.update(query))
					{
						db.getConnection().rollback();
						this.msg = "Lỗi xảy ra khi cập nhật thông tin";
						return false;
					}
				}
			}
			if(Integer.parseInt(this.id)<=100004)
			{
				query = "delete from NHOMSANPHAM_MIENNAM_SANPHAM WHERE NSP_FK='" + this.id + "'";
			}
			else
			{
				query = "delete from NHOMSANPHAM_MIENNAM WHERE NSP_FK='" + this.id + "'";

			}
			if (!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = "Lỗi xảy ra khi cập nhật thông tin";
				return false;
			}
			if (nhomN != null)
			{
				for (int i = 0; i < nhomN.length; i++)
				{
					if(Integer.parseInt(this.id)<=100004)
					{
					query = "insert into NHOMSANPHAM_MIENNAM_SANPHAM(nsp_fk,sp_Fk)  select '" + this.id + "','" + nhomN[i] + "'";
					}
					else
					{
						query = "insert into NHOMSANPHAM_MIENNAM(nsp_fk,sp_Fk)  select '" + this.id + "','" + nhomN[i] + "'";

					}
					if (!db.update(query))
					{
						db.getConnection().rollback();
						this.msg = "Lỗi xảy ra khi cập nhật thông tin";
						return false;
					}
				}
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (db != null)
				db.shutDown();
		}
		return true;
	}

}

