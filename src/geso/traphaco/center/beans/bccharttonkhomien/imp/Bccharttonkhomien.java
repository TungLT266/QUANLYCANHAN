package geso.traphaco.center.beans.bccharttonkhomien.imp;

import geso.traphaco.center.beans.bccharttonkhomien.IBccharttonkhomien;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Phan_Trang;
import java.sql.ResultSet;

public class Bccharttonkhomien extends Phan_Trang implements IBccharttonkhomien{

	private static final long serialVersionUID = 1L;
	String userId;
	String id;
	String mangtenmien[];	
	String mangidmien[];
	String trangthai;
	ResultSet rs;
	String msg;

	dbutils db;

	public Bccharttonkhomien()
	{
		this.userId = "";
		this.id = "";
		this.msg = "";
		this.db = new dbutils();
		this.trangthai="0";
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

	public void initTonKho()
	{
		String query="";
		
		// LẤY ARR MIỀN CÓ TỒN KHO
		query=" SELECT distinct v.PK_SEQ,v.TEN "+
				"	FROM NHAPP_KHO nppkho inner join NHAPHANPHOI npp on nppkho.NPP_FK= npp.PK_SEQ inner join "+
				"		KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK join VUNG v on kv.VUNG_FK = v.PK_SEQ "+
				"	WHERE npp.TRANGTHAI = 1 and nppkho.SOLUONG>0 ";
			
		rs=this.db.get(query);		
		
		mangidmien=new String[64];
		mangtenmien=new String[64];
		
		try{
			int i = 0;
			while (rs.next())
			{
				mangidmien[i]=rs.getString("pk_seq");
				mangtenmien[i]=rs.getString("ten");
				i++;
			}
			rs.close();
		}catch (Exception e) {
			this.msg="Không tìm thấy miền nào";
		}
		
		// LẤY BÁO CÁO
		query="declare @cols varchar(max), @sql nvarchar(max) "+
				"	set @cols = '[' + REPLACE( "+
				"			(SELECT distinct v.PK_SEQ as [data()] "+
				"							FROM NHAPP_KHO nppkho inner join NHAPHANPHOI npp on nppkho.NPP_FK= npp.PK_SEQ inner join "+
				"							 KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK join VUNG v on kv.VUNG_FK = v.PK_SEQ "+
				"							 WHERE npp.TRANGTHAI = 1 and nppkho.AVAILABLE>0 "+
				"			FOR XML PATH('') "+
				"				),' ','],[') + ']' ; "+
				"	set @sql = ' "+
				"		SELECT N''Ton kho'' as type, ' + @cols + ' "+
				"		FROM( "+
				"			SELECT cast(sum(nppkho.AVAILABLE) as numeric(18,0)) as TonKho,v.pk_seq "+
				"			FROM NHAPP_KHO nppkho inner join NHAPHANPHOI npp on nppkho.NPP_FK= npp.PK_SEQ inner join "+
				"			KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK join VUNG v on kv.VUNG_FK = v.PK_SEQ "+
				"			WHERE npp.TRANGTHAI =1 and nppkho.AVAILABLE >0 "+
				"						and npp.pk_seq in ( select npp_fk from phamvihoatdong where nhanvien_fk =''"+this.getUserId()+"'') "+
				"			Group by v.pk_seq "+
				"			) a "+
				"		 PIVOT "+
				"		( "+
				"			SUM(a.TonKho) FOR a.PK_SEQ  IN ('+@cols +') "+
				"		) AS PIVOTTable ' "+
				"	exec (@sql)"; 
		System.out.println("Lấy báo cáo tồn kho miền: "+query);
		this.rs=this.db.get(query);
	}
	public void initXuatNhapTon() 
	{
		//VẪN CHƯA LÀM XONG CÂU QUERY -- ĐANG LÀM XEM TỒN KHO
		String query="";
		
		// LẤY ARR ĐỊA BÀN CÓ TỒN KHO
		query=" SELECT distinct v.PK_SEQ,v.TEN "+
				"	FROM NHAPP_KHO nppkho inner join NHAPHANPHOI npp on nppkho.NPP_FK= npp.PK_SEQ inner join "+
				"		KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK join VUNG v on kv.VUNG_FK = v.PK_SEQ "+
				"	WHERE npp.TRANGTHAI = 1 and nppkho.SOLUONG>0 ";
			
		rs=this.db.get(query);		
		
		mangidmien=new String[64];
		mangtenmien=new String[64];
		
		try{
			int i = 0;
			while (rs.next())
			{
				mangidmien[i]=rs.getString("pk_seq");
				mangtenmien[i]=rs.getString("ten");
				i++;
			}
			rs.close();
		}catch (Exception e) {
			this.msg="Không tìm thấy địa bàn nào";
		}
		// LẤY BÁO CÁO
		query="declare @cols varchar(max), @sql nvarchar(max) "+
				"	set @cols = '[' + REPLACE( "+
				"			(SELECT distinct v.PK_SEQ as [data()] "+
				"							FROM NHAPP_KHO nppkho inner join NHAPHANPHOI npp on nppkho.NPP_FK= npp.PK_SEQ inner join "+
				"							 KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK join VUNG v on kv.VUNG_FK = v.PK_SEQ "+
				"							 WHERE npp.TRANGTHAI = 1 and nppkho.SOLUONG>0 "+
				"			FOR XML PATH('') "+
				"				),' ','],[') + ']' ; "+
				"	set @sql = ' "+
				"		SELECT N''Ton kho'' as type, ' + @cols + ' "+
				"		FROM( "+
				"			SELECT sum(nppkho.SOLUONG) as TonKho,v.pk_seq "+
				"			FROM NHAPP_KHO nppkho inner join NHAPHANPHOI npp on nppkho.NPP_FK= npp.PK_SEQ inner join "+
				"			KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK join VUNG v on kv.VUNG_FK = v.PK_SEQ "+
				"			WHERE npp.TRANGTHAI =1 and nppkho.SOLUONG >0 "+
				"			Group by v.pk_seq "+
				"			) a "+
				"		 PIVOT "+
				"		( "+
				"			SUM(a.TonKho) FOR a.PK_SEQ  IN ('+@cols +') "+
				"		) AS PIVOTTable ' "+
				"	exec (@sql)"; 
		System.out.println(query);
		this.rs=this.db.get(query);
		
	}

	public void createRs()
	{

	}

	public void DbClose()
	{
		try
		{
			this.db.shutDown();
		}
		catch (Exception e) {}
	}
	
	public String[] getArrTenMien() {
		return this.mangtenmien;
	}

	public String[] getArrIDMien() {
		return this.mangidmien;
	}

	public ResultSet getRs() {
		return this.rs;
	}

	public String getTrangThai() {
		return this.trangthai;
	}
	public void setTrangThai(String trangthai)
	{
		this.trangthai=trangthai;
	}


	



}
