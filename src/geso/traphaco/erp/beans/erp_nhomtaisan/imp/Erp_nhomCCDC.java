package geso.traphaco.erp.beans.erp_nhomtaisan.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.erp_nhomtaisan.IErp_nhomCCDC;

public class Erp_nhomCCDC extends Phan_Trang implements IErp_nhomCCDC
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -796740377599462235L;
	dbutils db;
	String Id, Ten, Ma, Msg, Nguoitao, Nguoisua, Ngaytao, Ngaysua, Trangthai, userTen, userId, ctyId;
	String ltsId;
	String[] cdtsIds;
	String[] ttcpIds;
	ResultSet TsList, ltsRs, ttcpRs, cdtsRs;

	public Erp_nhomCCDC()
	{
		this.Id = "";
		this.ctyId = "";
		this.Ma = "";
		this.Ten = "";
		this.ltsId = "";
		this.Msg = "";
		this.Nguoitao = "";
		this.Nguoisua = "";
		this.Ngaytao = "";
		this.Ngaysua = "";
		this.Trangthai = "1";
		this.userId = "";
		this.userTen = "";
		db = new dbutils();
	}

	public Erp_nhomCCDC(String id)
	{
		this.Id=id;
		this.ctyId = "";
		this.Ma = "";
		this.Ten = "";
		this.Msg = "";
		this.Nguoitao = "";
		this.Nguoisua = "";
		this.Ngaytao = "";
		this.Ngaysua = "";
		this.Trangthai = "1";
		this.userId = "";
		this.userTen = "";
		db = new dbutils();
	}

	public String getId()
	{

		return Id;
	}

	public String getCtyId()
	{

		return ctyId;
	}

	public String getMa()
	{

		return Ma;
	}

	public String getTen()
	{

		return Ten;
	}


	public String getMsg()
	{

		return Msg;
	}

	public void setId(String id)
	{

		this.Id = id;
	}

	public void setCtyId(String ctyId)
	{

		this.ctyId = ctyId;
	}

	public void setMa(String ma)
	{

		this.Ma = ma;
	}

	public void setTen(String ten)
	{

		this.Ten = ten;
	}

	public void setMsg(String Msg)
	{

		this.Msg = Msg;
	}

	public ResultSet getRsts()
	{

		return TsList;
	}

	public void setRsts(ResultSet Rsts)
	{

		this.TsList = Rsts;
	}


	public ResultSet getRslts()
	{

		return this.ltsRs;
	}

	public void setRslts(ResultSet ltsRs)
	{

		this.ltsRs = ltsRs;
	}


	public void setLtsId(String ltsId)
	{

		this.ltsId = ltsId;
	}

	public String getLtsId()
	{

		return ltsId;
	}
	
	
	public ResultSet getRsCdts()
	{

		return this.cdtsRs;
	}

	public void setRsCdts(ResultSet cdtsRs)
	{

		this.cdtsRs = cdtsRs;
	}


	public void setCdtsIds(String[] cdtsIds)
	{

		this.cdtsIds = cdtsIds;
	}

	public String[] getCdtsIds()
	{

		return cdtsIds;
	}
	
	public ResultSet getRsTtcp()
	{

		return this.ttcpRs;
	}

	public void setRsTtcp(ResultSet ttcpRs)
	{

		this.ttcpRs = ttcpRs;
	}


	public void setTtcpIds(String[] ttcpIds)
	{

		this.ttcpIds = ttcpIds;
	}

	public String[] getTtcpIds()
	{

		return ttcpIds;
	}
	
	public void setUserid(String userId)
	{

		this.userId = userId;
	}

	public String getUserid()
	{

		return userId;
	}

	public void setUserTen(String userTen)
	{

		this.userTen = userTen;
	}

	public String getUserTen()
	{

		return userTen;
	}

	public String getCdIdsList(){
		String tmp = "";
		if(this.cdtsIds != null){
			for(int i = 0; i < this.cdtsIds.length; i++){
				tmp = tmp +  this.cdtsIds[i] + ";" ;
			}
		}
		return tmp;
	}


	public String getTtcpIdsList(){
		String tmp = "";
		if(this.ttcpIds != null){
			for(int i = 0; i < this.ttcpIds.length; i++){
				tmp = tmp +  this.ttcpIds[i] + ";" ;
			}
		}
		return tmp;
	}
	
	public boolean ThemMoiMa()
	{
		try{
			this.db.getConnection().setAutoCommit(false);
			if (!CheckUnique())
			{
				this.Msg = "TMM1.1 Mã nhóm tài khoản này đã có,vui lòng chọn mã khác";
				return false;
			}
			String query = "INSERT erp_nhomCCDC(ma, diengiai,  trangthai, nguoitao, nguoisua, ngaytao, ngaysua, loaiCCDC_fk, congty_fk) VALUES(N'"
							+ this.Ma+ "', N'"+ this.Ten+ "', '"+ this.Trangthai+ "','"+ this.Nguoitao
							+ "','" + this.Nguoisua + "','" + this.Ngaytao + "','" + this.Ngaysua + "', " + this.ltsId + ", " + this.ctyId + " )";
			
			System.out.println("them moi: " + query);
			
		
			
			if (db.update(query))
			{
				try{
					query = "SELECT SCOPE_IDENTITY() AS ID";
				
					ResultSet rs = this.db.get(query);
					rs.next();
				
					this.Id = rs.getString("ID");
				
					if(this.cdtsIds != null){
						for(int i = 0; i < this.cdtsIds.length; i++){
							query = "INSERT INTO ERP_NHOMCCDC_CONGDUNG(NHOMCCDC_FK, CONGDUNG_FK) VALUES (" + this.Id + ", " + this.cdtsIds[i] + " )";
						
							System.out.println("Cong dung: " + query);
	
							if (!this.db.update(query))
							{
								this.Msg = "TMM1.2 Không thể cập nhật";
								this.db.getConnection().rollback();
								return false;
							}
						}
					}else{
						this.Msg = "TMM1.3 Vui lòng chọn Công Dụng Tài Sản";
						this.db.getConnection().rollback();
						return false;
					}
	
				
					boolean coTTCP = false;
					if(this.ttcpIds != null)
					{
						for(int i = 0; i < this.ttcpIds.length; i++){
							if (this.ttcpIds[i] != null)
							{
								coTTCP = true;
								query = "INSERT INTO ERP_NHOMCCDC_PHANBOKHAUHAO(NHOMCCDC_FK, TTCP_FK) VALUES (" + this.Id + ", " + this.ttcpIds[i] + " )";
							
								System.out.println("Phan bo khau hao: " + query);
						
								if (!this.db.update(query))
								{
									this.Msg = "TMM1.3 Không thể tạo mới nhóm chi phí";
									this.db.getConnection().rollback();
									return false;
								}
							}
						}
					}
					
					if (coTTCP == false)
					{
						this.Msg = "TMM1.3.1 Không thể tạo mới nhóm chi phí: không có trung tâm chi phí để phân bổ khấu hao được chọn";
						this.db.getConnection().rollback();
						return false;
					}
					
					if (rs != null)
						rs.close();
				}catch(java.sql.SQLException e){
					e.printStackTrace();
					this.db.getConnection().rollback();
					return false;
				}
			} else
			{
				this.Msg = "TMM1.4 Khong the them moi nhom tai san "+query;
				this.db.getConnection().rollback();
				return false;
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}catch (Exception e) {
			e.printStackTrace();
			this.db.update("rollback");
			return false;
		}
		return true;
	}
	

	public boolean UpdateMa()
	{
		try{
			this.db.getConnection().setAutoCommit(false);
			if (!CheckUnique())
			{
				this.Msg = "UM1.1 Mã nhóm tài khoản này đã có,vui lòng chọn mã khác";
				this.db.update("rollback");
				return false;
			}
			
			String query = 	"UPDATE erp_nhomCCDC SET ma = N'" + this.Ma + "', diengiai = N'" + this.Ten + "', " + 
							"nguoisua = '" + this.Nguoisua + "',ngaysua = '" + this.Ngaysua + "', " + 
							"trangthai = '" + this.Trangthai + "', loaiCCDC_fk = " + this.ltsId + "  WHERE pk_seq = '" + this.Id + "'";
	
			System.out.println("update: " + query);
			
			if (db.update(query))
			{
				
				if(this.cdtsIds != null){
					query = "DELETE FROM ERP_NHOMCCDC_CONGDUNG WHERE NHOMCCDC_FK = " + this.Id + "";
					this.db.update(query);
	
					for(int i = 0; i < this.cdtsIds.length; i++){
						query = "INSERT INTO ERP_NHOMCCDC_CONGDUNG(NHOMCCDC_FK, CONGDUNG_FK) VALUES (" + this.Id + ", " + this.cdtsIds[i] + " )";
						
						System.out.println("Cong dung: " + query);
	
						if (!this.db.update(query))
						{
							this.Msg = "UM1.2 Không thể cập nhật nhóm chi phí";
							this.db.getConnection().rollback();
							return false;
						}
					}
				}else{
					this.Msg = "UM1.3 Vui lòng chọn Công Dụng Tài Sản";
					this.db.update("rollback");
					return false;
				}
	
				query = "DELETE FROM ERP_NHOMCCDC_PHANBOKHAUHAO WHERE NHOMCCDC_FK = " + this.Id + "";
				if (!this.db.update(query))
				{
					this.Msg = "UM1.4 Không thể cập nhật nhóm chi phí";
					this.db.getConnection().rollback();
					return false;
				}
				
				boolean coTTCP = false;
				if(this.ttcpIds != null){
					for(int i = 0; i < this.ttcpIds.length; i++){
						if (this.ttcpIds[i] != null)
						{
							coTTCP = true;
							query = "INSERT INTO ERP_NHOMCCDC_PHANBOKHAUHAO(NHOMCCDC_FK, TTCP_FK) VALUES (" + this.Id + ", " + this.ttcpIds[i] + " )";
							
							System.out.println("Phan bo khau hao: " + query);
							
							if (!this.db.update(query))
							{
								this.Msg = "UM1.5 Không thể cập nhật nhóm chi phí";
								this.db.getConnection().rollback();
								return false;
							}
						}
					}
				}
				
				if (coTTCP == false)
				{
					this.Msg = "UM1.5.1 Không thể cập nhật nhóm chi phí: không có trung tâm chi phí để phân bổ khấu hao được chọn";
					this.db.getConnection().rollback();
					return false;
				}
			} else
			{
				this.Msg = "UM1.6 Khong the cap nhat  nhom tai san "+query;
				this.db.update("rollback");
				return false;
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}catch (Exception e) {
			this.Msg = "UM1.7 Không thể cập nhật nhóm chi phí";
			e.printStackTrace();
			this.db.update("rollback");
			return false;
		}
		return true;
	}

	public ResultSet getChoncongdung(){
		String query = "";
		if(this.Id.length() > 0){
		
			query = 	"SELECT CD.PK_SEQ AS CDTSID, CD.TEN AS DIENGIAI \n" +
						"FROM ERP_NHOMCCDC_CONGDUNG NTS_CD \n" +
						"INNER JOIN Erp_CongDungCCDC CD ON CD.PK_SEQ = NTS_CD.CONGDUNG_FK \n" +
						"WHERE NHOMCCDC_FK = " + this.Id + " ORDER BY CD.PK_SEQ \n";
			System.out.println("lay cong dung: \n" + query + "\n=============================");
			return this.db.get(query);
		}
		
		System.out.println(query);
		
		return null;
	}
	
	public ResultSet getChoncongdungthem(){
		String query = "";
		if(this.Id.length() > 0){
		
			query = 	"SELECT PK_SEQ AS CDTSID, TEN AS DIENGIAI " +
						"FROM ERP_CONGDUNGCCDC WHERE PK_SEQ NOT IN (" +
						"SELECT CD.PK_SEQ " +
						"FROM ERP_NHOMCCDC_CONGDUNG NTS_CD " +
						"INNER JOIN ERP_CONGDUNGCCDC CD ON CD.PK_SEQ = NTS_CD.CONGDUNG_FK " +
						"WHERE NHOMCCDC_FK = " + this.Id + ") ORDER BY PK_SEQ ";
			
			return this.db.get(query);

		}else{
			query = 	"SELECT PK_SEQ AS CDTSID, TEN AS DIENGIAI " +
						"FROM ERP_CONGDUNGCCDC ORDER BY PK_SEQ ";
			
			return this.db.get(query);
		}
				
	}

	public ResultSet getChonTTCP(){
		String query = "";
		if(this.Id.length() > 0){
		
			query = 	"SELECT TTCP.PK_SEQ AS TTCPID, TTCP.MA, TTCP.DIENGIAI " +
						"FROM ERP_NHOMCCDC_PHANBOKHAUHAO PB " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = PB.TTCP_FK " +
						"WHERE NHOMCCDC_FK = " + this.Id + " ORDER BY TTCP.PK_SEQ ";
			
			return this.db.get(query);

		}
		
		System.out.println(query);
		
		return null;
	}

	public ResultSet getChonTTCPThem(){
		String query = "";
		if(this.Id.length() > 0){
		
			query = 	"SELECT PK_SEQ AS TTCPID, MA, DIENGIAI "+
						"FROM ERP_TRUNGTAMCHIPHI WHERE PK_SEQ NOT IN (" +
						"SELECT TTCP.PK_SEQ  " +
						"FROM ERP_NHOMCCDC_PHANBOKHAUHAO PB " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = PB.TTCP_FK " +
						"WHERE NHOMCCDC_FK = " + this.Id + " ) ORDER BY PK_SEQ ";
			
			return this.db.get(query);

		}else{
			query = 	"SELECT PK_SEQ AS TTCPID, MA, DIENGIAI "+
						"FROM ERP_TRUNGTAMCHIPHI ORDER BY PK_SEQ";
			
			return this.db.get(query);
		}
		
	}

	public void DBClose()
	{

		try
		{
			if (this.cdtsRs != null)
			{
				this.cdtsRs.close();
			}
			if (TsList != null)
			{
				TsList.close();
			}

			if (this.ltsRs != null)
			{
				ltsRs.close();
			}

			
			if (this.ttcpRs != null)
			{
				ttcpRs.close();
			}
			
			if (db != null)
			{
				db.shutDown();
			}

		} catch (Exception er)
		{
			er.printStackTrace();
		}
	}

	public void init()
	{
		db = new dbutils();
		this.Msg = "";
		String query = 	"SELECT ma, diengiai, nguoitao, nguoisua, ngaytao, ngaysua, trangthai, isnull(loaiCCDC_fk, 0) as ltsId " +												
						"FROM erp_nhomCCDC WHERE pk_seq = '" + this.Id + "' ";
		
		System.out.println(query);
		
		ResultSet obj = db.get(query);
		try
		{
			if (obj.next())
			{
				this.Ma = obj.getString("ma");
				this.Ten = obj.getString("diengiai");
				this.Nguoitao = obj.getString("nguoitao");
				this.Nguoisua = obj.getString("nguoisua");
				this.Ngaytao = obj.getString("ngaytao");
				this.Ngaysua = obj.getString("ngaysua");
				this.Trangthai = obj.getString("trangthai");
				this.ltsId = obj.getString("ltsId");
			}

			if (obj != null)
			{
				obj.close();
			}

		} catch (Exception er)
		{
			er.printStackTrace();
			this.Msg = "Loi" + er.toString();
		}
		
		query = "SELECT PK_SEQ AS LTSID, DIENGIAI FROM ERP_LOAICCDC WHERE TrangThai = 1 and congty_fk = " + this.ctyId;
		System.out.println("cau lenh lay ccdc: \n" + query + "\n==========================");
		this.ltsRs = this.db.get(query);
		
		query = "SELECT PK_SEQ AS TTCPID, MA, DIENGIAI FROM ERP_TRUNGTAMCHIPHI WHERE TRANGTHAI = 1";
		
		this.ttcpRs = this.db.get(query);
		
		query = "SELECT PK_SEQ AS CDTSID, TEN AS DIENGIAI FROM ERP_CONGDUNGCCDC WHERE TrangThai = 1";
		
		this.cdtsRs = this.db.get(query);
		
	}

	public String getNgaytao()
	{

		return Ngaytao;
	}

	public String getNguoitao()
	{

		return Nguoitao;
	}

	public String getNgaysua()
	{

		return Ngaysua;
	}

	public String getNguoisua()
	{

		return Nguoisua;
	}

	public void setNgaytao(String ngaytao)
	{

		this.Ngaytao = ngaytao;
	}

	public void setNguoitao(String nguoitao)
	{

		this.Nguoitao = nguoitao;
	}

	public void setNgaysua(String ngaysua)
	{

		this.Ngaysua = ngaysua;
	}

	public void setNguoisua(String nguoisua)
	{

		this.Nguoisua = nguoisua;
	}

	public String getTrangthai()
	{

		return Trangthai;
	}

	public boolean DeleteNhts()
	{

/*		if (!CheckReferences("NhomCCDC_FK", "Erp_CONGCUDUNGCU"))
		{
			this.Msg = " Nhóm tài sản này đã có tài sản,bạn phải xóa tài sản trước ";
			return false;
		}*/
		String query = "DELETE Erp_NhomCCDC WHERE PK_SEQ='" + this.Id + "'";
		if (!this.db.update(query))
		{
			this.Msg = "Không thể xóa nhóm tài sản " + query;
			return false;
		}
		return true;
	}

	public void setTrangthai(String trangthai)
	{

		this.Trangthai = trangthai;
	}

	public boolean CheckUnique()
	{
		String query = "";
		if (this.Id.length() > 0)
			query = "Select count(*) as count From Erp_NhomCCDC Where MA=N'" + this.Ma + "' AND PK_SEQ !='" + this.Id
					+ " '";
		else
			query = "Select count(*) as count From Erp_NhomCCDC Where MA=N'" + this.Ma + "' ";
		int count = 0;
		ResultSet rs = this.db.get(query);
		if (rs != null)
			try
			{
				while (rs.next())
				{
					count = rs.getInt("count");
				}
				rs.close();
				if (count > 0)
					return false;
			} catch (SQLException e)
			{
				return false;
			}
		return true;
	}

	public boolean CheckReferences(String column, String table)
	{
		String query = "SELECT count(" + column + ") AS NUM  FROM " + table + " WHERE " + column + " =" + this.Id + "";
		System.out.println("CheckReferences " + query);
		ResultSet rs = db.get(query);
		try
		{// kiem tra ben san pham
			if (rs != null)
			{
				while (rs.next())
				{
					if (rs.getString("num").equals("0"))
						return true;
				}
				rs.close();
			}
		} catch (SQLException e)
		{

			e.printStackTrace();
		}
		return false;
	}

	public void createRs(){
		String query = "SELECT PK_SEQ AS LTSID, DIENGIAI FROM ERP_LOAICCDC WHERE TrangThai = 1 and congty_fk = " + this.ctyId;
		
		this.ltsRs = this.db.get(query);
		
		query = "SELECT PK_SEQ AS TTCPID, TEN AS DIENGIAI FROM ERP_TRUNGTAMCHIPHI WHERE TrangThai = 1 and CONGTY_FK = " + this.ctyId;
		
		this.ttcpRs = this.db.get(query);

		query = "SELECT PK_SEQ AS CDTSID, TEN AS DIENGIAI FROM ERP_CONGDUNG WHERE TrangThai = 1 and congty_fk = " + this.ctyId;
		
		this.cdtsRs = this.db.get(query);
	}
}