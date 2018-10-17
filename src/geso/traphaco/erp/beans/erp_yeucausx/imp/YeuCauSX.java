package geso.traphaco.erp.beans.erp_yeucausx.imp;

import geso.dms.db.sql.dbutils;
import geso.traphaco.erp.beans.dubaokinhdoanh.IDubao;
import geso.traphaco.erp.beans.dubaokinhdoanh.imp.Dubao;
import geso.traphaco.erp.beans.erp_yeucausx.IYeuCauSX;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class YeuCauSX implements IYeuCauSX {
	private String pk_seq,sanpham_fk,sanxuat,thang,ngaydubao,diengiai,tuan1,tuan2,tuan3,tuan4,SOTHANGDUBAO,nam,ngaysua,nguoisua,ngaytao,nguoitao,msg,dubao_fk,yc_fk;
	private int cacthangcodubao,sothang,phantuj;
	
	private String[] MANGCACTHANGDERATAB,MangThang;
	private List<IDubao> dbList;
	private ArrayList<IDubao>[] THANG;
	private ResultSet rsYCSX,rsDuBaoKinhDoanh,rsThangDB,rsSanPhamSX;
	
	private dbutils db;
	
	public void setYc_fk(String yc_fk)
	{
		this.yc_fk=yc_fk;
	}
	public String getYc_fk()
	{
		return this.yc_fk;
	}
	public void setDiengiai(String diengiai)
	{
		this.diengiai=diengiai;
	}
	
	public String getDiengiai()
	{
		return this.diengiai;
	}
	
	public String[] getMangThang()
	{
		return this.MangThang;
	}
	public void setMangThang(String[] mangthang)
	{
		this.MangThang=mangthang;
	}
	
	public void setphantuj(int phantuj)
	{
		this.phantuj=phantuj;
	}
	public int getphantuj()
	{
		return this.phantuj;
	}
	public void setsothang(int sothang)
	{
		this.sothang=sothang;
	}
	public int getsothang()
	{
		return this.sothang;
	}
	public void setSOTHANGDUBAO(String sothangdubao)
	{
		this.SOTHANGDUBAO=sothangdubao;
	}
	
	public String getSOTHANGDUBAO()
	{
		return this.SOTHANGDUBAO;
	}
	public void setRsSanphamSX(ResultSet rs)
	{
		this.rsSanPhamSX=rs;
	}
	public ResultSet getRsSanPhamSX()
	{
		return this.rsSanPhamSX;
	}
	
	public void setTHANG(String thang)
	{
		this.thang=thang;
	}
	public String getTHANG()
	{
		return this.thang;
	}
	
	public void setNGAYDUBAO(String ngaydubao)
	{
		this.ngaydubao=ngaydubao;
	}
	public String getNGAYDUBAO()
	{
		return this.ngaydubao;
	}
	
	public void setRSTHANGDB(ResultSet rs)
	{
		this.rsThangDB=rs;
	}
	public ResultSet getRSTHANGDB()
	{
		return this.rsThangDB;
	}
	
	public void setRSDuBaoKinhDoanh(ResultSet rs)
	{
		this.rsDuBaoKinhDoanh=rs;
	}
	
	public ResultSet getRSDuBaoKinhDoanh()
	{
		return this.rsDuBaoKinhDoanh;
	}
	
	public void setDUBAO_FK(String dubao_fk)
	{
		this.dubao_fk=dubao_fk;
	}
	public String getDUBAO_FK()
	{
		return this.dubao_fk;
	}
	public YeuCauSX()
	{
		this.pk_seq="";
		this.sanpham_fk="";
		this.sanxuat="";
		this.tuan1="";
		this.tuan2="";
		this.tuan3="";
		this.tuan4="";
		this.dubao_fk="";
		this.SOTHANGDUBAO="0";
		this.phantuj=0;
		this.sothang=0;
		this.thang="";
		this.nam="";
		this.ngaysua="";
		this.ngaydubao="";
		this.nguoisua="";
		this.ngaytao="";
		this.nguoitao="";
		this.diengiai="";
		this.msg="";
		this.db=new dbutils();
		THANG = new ArrayList[Integer.parseInt(this.SOTHANGDUBAO)];
		for(int i=0;i<THANG.length;i++) 
			THANG[i] = new ArrayList<IDubao>();
		this.dbList = new ArrayList<IDubao>();
	}
	public YeuCauSX(String pk_seq)
	{
		this.pk_seq= pk_seq;
		this.sanpham_fk="";
		this.sanxuat="";
		this.diengiai="";
		this.tuan1="";
		this.tuan2="";
		this.tuan3="";
		this.tuan4="";
		this.dubao_fk="";
		this.phantuj=0;
		this.sothang=0;
		this.thang="";
		this.ngaydubao="";
		this.SOTHANGDUBAO="0";
		this.nam="";
		this.ngaysua="";
		this.nguoisua="";
		this.ngaytao="";
		this.nguoitao="";
		this.msg="";
		this.db=new dbutils();
		THANG = new ArrayList[Integer.parseInt(this.SOTHANGDUBAO)];
		for(int i=0;i<THANG.length;i++) 
			THANG[i] = new ArrayList<IDubao>();
		this.dbList = new ArrayList<IDubao>();
	}
	public void setPK_SEQ(String pk_seq) {
		this.pk_seq=pk_seq;
	}

	public String getPK_SEQ() {
		return this.pk_seq;
	}

	public void setSANPHAM_FK(String sanpham_fk) {
		this.sanpham_fk=sanpham_fk;
	}

	public String getSANPHAM_FK() {
		return this.sanpham_fk;
	}

	public void setNGAYTAO(String ngaytao) {
		this.ngaytao=ngaytao;
	}

	public String getNGAYTAO() {
		return this.ngaytao;
	}
	
	public void setNGUOITAO(String nguoitao) {
		this.nguoitao=nguoitao;
	}

	public String getNGUOITAO() {
		return this.nguoitao;
	}

	public void setNGAYSUA(String ngaysua) {
		this.ngaysua=ngaysua;
	}

	public String getNGAYSUA() {
		return this.ngaysua;
	}

	public void setNGUOISUA(String nguoisua) {
		this.nguoisua=nguoisua;
	}

	public String getNGUOISUA() {
		return this.nguoisua;
	}

	public void setSANXUAT(String sanxuat) {
		this.sanxuat=sanxuat;
	}

	public String getSANXUAT() {
		return this.sanxuat;
	}

	public void setTUAN1(String tuan) {
		this.tuan1=tuan;
	}

	public String getTUAN1() {
		return this.tuan1;
	}

	public void setTUAN2(String tuan) {
		this.tuan2=tuan;
	}

	public String getTUAN2() {
		return this.tuan2;
	}

	public void setTUAN3(String tuan) {
		this.tuan3=tuan;
	}

	public String getTUAN3() {
		return this.tuan3;
	}

	public void setTUAN4(String tuan) {
		this.tuan4=tuan;
	}

	public String getTUAN4() {
		return this.tuan4;
	}

	public boolean Create() 
	{
			try
			{
				String curr="";
				String[] tenthang=this.getMangThang();
				/////////////////////////////////////////////////////////////////////////////////////////
				/////////////////////////////////////////////////////////////////////////////////////////
				String insertYC="INSERT INTO yc(ngaytao,dubao_fk,diengiai,nguoitao) VALUES('"+this.ngaydubao+"','"+this.dubao_fk+"',N'"+this.diengiai+"','"+this.nguoitao+"')";
				System.out.println("Insert dòng mới trong bảng Yêu Cầu: "+insertYC);
				
				this.db.getConnection().setAutoCommit(false);
				if(!this.db.update(insertYC))
				{
					this.msg="Không thể insert vào bảng yêu cầu "+insertYC;
					return false;
				}
				String selectIDENT_CURRENT="select IDENT_CURRENT('yc') as ycId";
				//System.out.println("lấy số dòng : "+selectIDENT_CURRENT);
				ResultSet rs=this.db.get(selectIDENT_CURRENT);
				if(rs!=null)
				{
					if(rs.next())
					{
						curr = rs.getString("ycId");
						rs.close();
					}
				}
				else
				{
					this.msg = "Lổi trong lúc lấy PK  yêu cầu : " + insertYC;
					db.getConnection().rollback();
					return false;
				}
				/////////////////////////////////////////////////////////////////////////////////////////////
				/////////////////////////////////////////////////////////////////////////////////////////////
				//System.out.println("số tháng "+this.getsothang());
				for(int i=0;i<this.getsothang();i++)
				{
					
					//System.out.println("có chạy ròi đó for i "+i);
					//System.out.println(this.getphantuj());
					for(int j=0;j<this.getphantuj();j++)
					{

						Dubao sp;//=new Dubao();
						 sp = (Dubao)this.THANG[i].get(j);

						 
						String InsertTempYCSX="INSERT INTO ERP_TEMPYEUCAUSANXUAT(SANPHAM_FK,THANG,SANXUAT,Tuan1,Tuan2,Tuan3," +
								"Tuan4,ngaytao,nguoitao,ngaysua,nguoisua,dubao_FK,nam,solan,yc_fk)" +
								"VALUES('"+sp.getIDsp()+"','"+tenthang[i]+"','"+sp.getSanxuat()+"','"+sp.getTuan1()+"'," +
										"'"+sp.getTuan2()+"','"+sp.getTuan3()+"','"+sp.getTuan4()+"'," +
										"'"+getDateTime()+"','"+this.nguoitao+"','"+getDateTime()+"','"+this.nguoitao+"',"+this.dubao_fk+",'"+sp.getNam().trim()+"',(SELECT COUNT (*) FROM ERP_TEMPYEUCAUSANXUAT WHERE SANPHAM_FK="+sp.getIDsp()+" AND THANG='"+tenthang[i]+"' AND dubao_FK='"+this.getDUBAO_FK()+"' AND nam='"+sp.getNam().trim()+"'),"+curr+")";
						//System.out.println("Insert dòng mới trong bảng tempYCSX: "+InsertTempYCSX);
						String deleteBangChinh="DELETE ERP_YEUCAUSANXUAT WHERE SANPHAM_FK='"+sp.getIDsp()+"' AND THANG='"+tenthang[i]+"' AND DUBAO_FK='"+this.dubao_fk+"' AND nam='"+sp.getNam().trim()+"'";
						//System.out.println("Xóa bảng chính : "+deleteBangChinh);
						String InsertYCSX="INSERT INTO ERP_YEUCAUSANXUAT(SANPHAM_FK,THANG,SANXUAT,Tuan1,Tuan2,Tuan3," +
								"Tuan4,ngaytao,nguoitao,ngaysua,nguoisua,dubao_FK,nam,yc_fk)" +
								"VALUES('"+sp.getIDsp()+"','"+tenthang[i]+"','"+sp.getSanxuat()+"','"+sp.getTuan1()+"'," +
										"'"+sp.getTuan2()+"','"+sp.getTuan3()+"','"+sp.getTuan4()+"'," +
										"'"+getDateTime()+"','"+this.nguoitao+"','"+getDateTime()+"','"+this.nguoitao+"',"+this.dubao_fk+",'"+sp.getNam().trim()+"',"+ curr+")";
						//System.out.println("Insert dòng mới trong bảng tempYCSX: " +InsertYCSX);
						
						
						
							
							if (!this.db.update(InsertTempYCSX))
							{
								this.db.update("rollback");
								this.msg="Không thể thêm yêu cầu sản xuất vào bảng log";
								return false;
							}
							if (!this.db.update(deleteBangChinh))
							{
								this.db.update("rollback");
								this.msg="Không thể xóa yêu cầu sản xuất trong bảng chính";
								return false;
							}
							if (!this.db.update(InsertYCSX))
							{
								this.db.update("rollback");
								this.msg="Không thể thêm yêu cầu sản xuất trong bảng chính";
								return false;
							}
							this.db.getConnection().commit();
							this.db.getConnection().setAutoCommit(false);
					}
				}
			}
		catch (Exception e) 
		{
			this.db.update("rollback");
			return false;
		}
			return true;
	}

	public boolean Edit() {
		return false;
	}

	public boolean Delete() {
		return false;
	}

	public void init() {

	}
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date=new Date();
		return dateFormat.format(date);
	}

	public void setMsg(String msg) {
		this.msg=msg;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setNAM(String nam) {
		this.nam=nam;
	}

	public String getNAM() {
		return this.nam;
	}
	public List<IDubao>[] getThang() {
		return this.THANG;
	}
	public void setThang(List<IDubao>[] kholist) {
		this.THANG=(ArrayList<IDubao>[]) kholist;	
	}

	public void CreateRS() 
	{
		String query="SELECT * FROM erp_dubao";
		this.rsDuBaoKinhDoanh=this.db.get(query);
		if(this.dubao_fk.length()!=0)
		{
			String queryThangSX="select distinct b.nam, b.thang AS thang,a.ngaydubao AS ngaydubao from erp_dubao a inner join ERP_DUBAOSANPHAM b ON a.PK_SEQ=b.DUBAO_FK WHERE a.PK_SEQ="+ this.dubao_fk +" order by b.nam,b.thang ";
			System.out.println(queryThangSX);
			this.rsThangDB=this.db.get(queryThangSX);
			try
			{ 
				int i=0;
				while(rsThangDB.next())
				{
					//this.ngaydubao=rsThangDB.getString("ngaydubao");
					
					i++;
				}
				this.rsThangDB=this.db.get(queryThangSX);
				this.cacthangcodubao=i;
				
			}
			catch (Exception e) 
			{
				
			}
		}
	}

	public void CreateRSerp_dubaosanpham()
	{

		String queryNgaydubao="SELECT NGAYDUBAO,SOTHANGDUBAO FROM ERP_DUBAO WHERE PK_SEQ='"+this.dubao_fk+"'";
		System.out.println(queryNgaydubao);
		ResultSet rsNDB=this.db.get(queryNgaydubao);
		if (rsNDB != null)
		{
			try{
				while(rsNDB.next())
				{
					//this.ngaydubao=rsNDB.getString("NGAYDUBAO");
					this.SOTHANGDUBAO=rsNDB.getString("SOTHANGDUBAO");
				}
				System.out.println("Init "+queryNgaydubao);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}finally{
				try {
					rsNDB.close();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
		}
		
        int sothang=0;
		String[] mangcacthang;
		String a=this.getTHANG();
		if(a.equals("13"))
		{
			mangcacthang=new String[Integer.parseInt(this.SOTHANGDUBAO.trim())];
			sothang=Integer.parseInt(this.SOTHANGDUBAO.trim());
			String queryThangSX="select distinct b.nam,b.thang AS thang,a.ngaydubao AS ngaydubao from erp_dubao a inner join ERP_DUBAOSANPHAM b ON a.PK_SEQ=b.DUBAO_FK WHERE a.PK_SEQ="+ this.dubao_fk +" order by b.nam,b.thang ASC";
		
			ResultSet rsThangDB1=this.db.get(queryThangSX);
		
			int i=0;
			if (rsThangDB1 != null)
			{
				try{
					while(rsThangDB1.next())
					{
						mangcacthang[i] = rsThangDB1.getString("thang");
						
						i++;
					}
				}
				catch(Exception er){
					er.printStackTrace();
				}finally{
					try {
						rsThangDB1.close();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
			}
		}
		else
		{
			mangcacthang=new String[1];
			sothang=1;
			mangcacthang[0]=this.getTHANG();
		}
		

			THANG = new ArrayList[sothang];
			for(int i=0;i<THANG.length;i++) THANG[i] = new ArrayList<IDubao>();

		
		if(this.thang.length()>0){
			
			
		for(int i=0;i<this.THANG.length;i++)
		{
			
			String th1 = mangcacthang[i]	;
			
			List<IDubao> dbList = new ArrayList<IDubao>();
			if(this.dubao_fk.length() > 0 && this.dbList.size() <= 0)
			{
				String query=" select a.pk_seq,sp.MA " +
				 		" as MASANPHAM,sp.TEN as TENSANPHAM,b.THANG AS THANG,b.NAM AS NAM,b.sanpham_fk AS sanpham_fk, " +
				 		" b.SANXUAT AS sanxuat " +
				 		" from ERP_DUBAO a inner join ERP_DUBAOSANPHAM b on a.pk_seq = b.DUBAO_FK" +
				 		" inner join sanpham sp on sp.pk_seq=b.sanpham_fk   Where a.pk_seq='"+this.dubao_fk+"' and b.THANG='"+th1+"'";
				 System.out.println(query);
				 ResultSet rsdb=db.get(query);
				if(rsdb!=null)
				{
					
					try {
						while(rsdb.next())
						{  
							IDubao dbDetail = null;
							dbDetail=new  Dubao();
							dbDetail.setIDsp(rsdb.getString("sanpham_fk"));
							dbDetail.setMasp(rsdb.getString("MASANPHAM"));
							dbDetail.setTensp(rsdb.getString("TENSANPHAM"));
							dbDetail.setSanxuat(rsdb.getString("SANXUAT"));
							dbDetail.setThang(rsdb.getString("THANG"));
							dbDetail.setNam(rsdb.getString("NAM"));
							dbList.add(dbDetail);
						}
					} 
					catch (Exception e) 
					{
						System.out.println("Loi roi" + e.toString());
					}finally{
						try {
							rsdb.close();
						} catch (Exception e2) {
							// TODO: handle exception
							e2.printStackTrace();
						}
					}
				}
				this.THANG[i]=(ArrayList<IDubao>) dbList;
			}
		}
		}	
	}

	@Override
	public int getCacThangCoDuBao() {
		// TODO Auto-generated method stub
		return cacthangcodubao;
	}

	@Override
	public String[] getMANGCACTHANGDERATAB() {
		// TODO Auto-generated method stub
		return this.MANGCACTHANGDERATAB;
	}
	
	public void DBClose()
	{
		try {
			if (this.dbList != null)
				this.dbList.clear(); 
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			if (this.db != null)
				this.db.shutDown();
		}
	}
}
