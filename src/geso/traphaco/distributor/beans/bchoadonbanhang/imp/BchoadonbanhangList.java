package geso.traphaco.distributor.beans.bchoadonbanhang.imp;

import geso.traphaco.distributor.beans.bchoadonbanhang.IBchoadonbanhangList;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.center.util.Phan_Trang;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BchoadonbanhangList extends Phan_Trang implements IBchoadonbanhangList
{
	String userId;

	String tungay;
	String denngay;
	String makh;
	String mafast;
	String trangthai;
	String khohang;
	String trinhduocvien;

	String msg;

	ResultSet bchoadonbanhangRs;
	String bchoadonbanhangId;

	ResultSet khohangRs;
	String khohangId;

	ResultSet trinhduocvienRs;
	String trinhduocvienId;

	dbutils db;

	public BchoadonbanhangList()
	{
		this.userId = "";
		this.msg= "";

		this.tungay = "";
		this.denngay = "";
		this.makh = "";
		this.mafast = "";
		this.trangthai = "";
		this.khohang = "";
		this.trinhduocvien = "";

		this.bchoadonbanhangId = "";
		this.db = new dbutils();
	}

	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getTungay()
	{
		return this.tungay;
	}

	public void setTungay(String tungay) 
	{
		this.tungay = tungay;
	}

	public String getDenngay()
	{
		return this.denngay;
	}

	public void setDenngay(String denngay) 
	{
		this.denngay = denngay;
	}

	public String getMakh()
	{
		return this.makh;
	}

	public void setMakh(String makh) 
	{
		this.makh = makh;
	}

	public String getMafast()
	{
		return this.mafast;
	}

	public void setMafast(String mafast) 
	{
		this.mafast = mafast;
	}

	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getKhohang()
	{
		return this.khohang;
	}

	public void setKhohang(String khohang) 
	{
		this.khohang = khohang;
	}

	public String getTrinhduocvien()
	{
		return this.trinhduocvien;
	}

	public void setTrinhduocvien(String trinhduocvien) 
	{
		this.trinhduocvien = trinhduocvien;
	}

	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public void init(String query)
	{
		String sql = "";

		if(query.length() > 0)
			sql = query;
		else
		{
			sql = 	"select  donhang.NGAYNHAP, donhang.PK_SEQ, kh.maFAST, kh.TEN, donhang.Tientruocthue,   "+
					" donhang.VAT, donhang.Tongtien  "+
					" from  ( "+
					" select dh.PK_SEQ, dh.TRANGTHAI, dh.KHACHHANG_FK,dh.NGAYNHAP, dh.DDKD_FK,  dh.KHO_FK, " +
					" sum(dhsp.SOLUONG*dhsp.GIAMUA) as Tientruocthue,  sum(dhsp.SOLUONG*dhsp.GIAMUA*dhsp.THUEVAT/100) as VAT,  " + 
					" sum(dhsp.SOLUONG*dhsp.GIAMUA*(1+dhsp.THUEVAT/100)) as Tongtien   " +
					" from DONHANG dh  inner join DONHANG_SANPHAM dhsp on dh.PK_SEQ=dhsp.DONHANG_FK  " +
					" group by dh.PK_SEQ, dh.TRANGTHAI, dh.KHACHHANG_FK,dh.NGAYNHAP , dh.DDKD_FK , dh.KHO_FK ) donhang  "+
					" inner join KHACHHANG kh on donhang.KHACHHANG_FK= kh.PK_SEQ"+
					" inner join DAIDIENKINHDOANH ddkd on donhang.DDKD_FK=ddkd.PK_SEQ" +
					"  where 1=1 " ;

		}			
		System.out.println("[DS DH]: "+  sql);
		this.bchoadonbanhangRs = db.get(sql);
		this.bchoadonbanhangRs = super.createSplittingData(super.getItems(), super.getSplittings(), "pk_seq desc", sql);
		
		String ddkd="select PK_SEQ, TEN from DAIDIENKINHDOANH";
		this.trinhduocvienRs = db.get(ddkd);
	}

	public void DbClose()
	{
		try
		{
			if(this.bchoadonbanhangRs != null)
				this.bchoadonbanhangRs.close();
			this.db.shutDown();
		}
		catch (SQLException e) {}
	}

	public ResultSet getBchoadonbanhangRs()
	{
		return this.bchoadonbanhangRs;
	}

	public void setBchoadonbanhangRs(ResultSet bchoadonbanhangRs)
	{
		this.bchoadonbanhangRs = bchoadonbanhangRs;
	}

	public String getBchoadonbanhangId()
	{
		return this.bchoadonbanhangId;
	}

	public void setBchoadonbanhangId(String bchoadonbanhangId)
	{
		this.bchoadonbanhangId = bchoadonbanhangId;
	}

	public ResultSet getKhohangRs()
	{
		return this.khohangRs;
	}

	public void setKhohangRs(ResultSet khohangRs)
	{
		this.khohangRs = khohangRs;
	}

	public String getKhohangId()
	{
		return this.khohangId;
	}

	public void setKhohangId(String khohangId)
	{
		this.khohangId = khohangId;
	}

	public ResultSet getTrinhduocvienRs()
	{
		return this.trinhduocvienRs;
	}

	public void setTrinhduocvienRs(ResultSet trinhduocvienRs)
	{
		this.trinhduocvienRs = trinhduocvienRs;
	}

	public String getTrinhduocvienId()
	{
		return this.trinhduocvienId;
	}

	public void setTrinhduocvienId(String trinhduocvienId)
	{
		this.trinhduocvienId = trinhduocvienId;
	}

	public void setQuery(String searchQuery) {
	
		
	}
	


}
