package geso.traphaco.erp.beans.lenhsanxuatgiay.imp;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.lenhsanxuatgiay.IChungtu;
import geso.traphaco.erp.beans.lenhsanxuatgiay.ITuan;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Tuan implements ITuan 
{
	int stt;
	long tbBan;
	int tonantoan;
	String ngaybatdau;
	long tondau;
	long maxDemand;
	long maxStock;
	long maxPlan;
	long maxProc;
	
	List<IChungtu> ctList;
	
	dbutils db;

	public Tuan()
	{
		this.stt = -1;
		this.tbBan = 0;
		this.tonantoan = 0;
		this.ngaybatdau = "";
		this.tondau = 0;
		this.maxDemand = -1;
		this.maxStock = -1;
		this.maxPlan = -1;
		this.maxProc = -1;
		
		this.ctList = new ArrayList<IChungtu>();
	}
	
	public int getSTT() 
	{	
		return this.stt;
	}

	public void setSTT(int stt) 
	{
		this.stt = stt;
	}

	public long getTBBan() 
	{
		return this.tbBan;
	}

	public void setTBBan(long tbBan) 
	{
		this.tbBan = tbBan;
	}

	public int getTonkhoantoan() 
	{
		return this.tonantoan;
	}

	public void setTonkhoantoan(int tkan) 
	{
		this.tonantoan = tkan;
	}

	public String getNgaybatdau() 
	{
		return this.ngaybatdau;
	}

	
	public void setNgaybatdau(String ngaybatdau) 
	{
		this.ngaybatdau = ngaybatdau;
	}

	public long getTondau()
	{
		return this.tondau;
	}

	public void setTondau(long tondau) 
	{
		this.tondau = tondau;
	}

	public long getMaxDemand() 
	{
		return this.maxDemand;
	}

	public void setMaxDemand(long maxDemand)
	{
		this.maxDemand = maxDemand;
	}

	public long getMaxPlan() 
	{
		return this.maxPlan;
	}

	public void setMaxPlan(long maxPlan) 
	{
		this.maxPlan = maxPlan;
	}

	public long getMaxProc() 
	{
		return this.maxProc;
	}

	public void setMaxProc(long maxProc) 
	{
		this.maxProc = maxProc;
	}
	
	public long getMaxStock() 
	{
		return this.maxStock;
	}

	public void setMaxStock(long maxStock) 
	{
		this.maxStock = maxStock;
	}

	public List<IChungtu> getAllChungTu()
	{
		return this.ctList;
	}

	public void setAllChungTu(List<IChungtu> ctList) 
	{
		this.ctList = ctList;
	}

	public void init() 
	{
		this.db = new dbutils();
		
		//Get Max Demand + Max Stock
		String query = "select Demand.maxDemand, Stock.maxStock " +
				"from " +
				"( " +
					"select isnull(max(maso), 99999) as maxDemand, '1' as lienket from ERP_LENHSANXUATDUKIEN_CHUNGTU where loai = '1' " +
				")  " +
				"Demand inner join " +
				"( " +
					"select isnull(max(maso), 99999) as maxStock, '1' as lienket from ERP_LENHSANXUATDUKIEN_CHUNGTU where loai = '2'  " +
				")  " +
				"Stock on Demand.lienket = Stock.lienket ";
		
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.maxDemand = rs.getLong("maxDemand");
					this.maxStock = rs.getLong("maxStock");
				}
				rs.close();
			} 
			catch (Exception e) {}
		}
		
		db.shutDown();
	}

	
}
