package geso.traphaco.distributor.beans.donhang.imp;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.distributor.beans.donhang.IChotdonhang;
import geso.traphaco.distributor.db.sql.dbutils;

public class Chotdonhang extends Phan_Trang implements IChotdonhang, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String ngaygiao;
	String msg;

	String nppId;
	String nppTen;
	String sitecode;

	ResultSet nvbhlist;
	String nvbhId;
	ResultSet nvgnlist;
	String nvgnId;

	ResultSet pxklist;
	String[] pxkIds;
	ResultSet dhlist;
	String[] dhIds;

	dbutils db;
	private int num;
	private int[] listPages;
	private int currentPages;
	private int theLastPage;

	private String ngayksgn;
	private HttpServletRequest request;

	public Chotdonhang()
	{
		this.nvbhId = "";
		this.nvgnId = "";
		this.ngaygiao = "";
		this.msg = "";
		currentPages = 1;
		num = 1;
		this.ngayksgn = "";
		this.db = new dbutils();
		// this.init();
	}

	public HttpServletRequest getRequestObj()
	{
		return this.request;
	}

	public void setRequestObj(HttpServletRequest request)
	{
		this.request = request;
	}

	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getNgaygiao()
	{
		return this.ngaygiao;
	}

	public void setNgaygiao(String ngaygiao)
	{
		this.ngaygiao = ngaygiao;
	}

	public String getNppId()
	{
		return this.nppId;
	}

	public void setNppId(String nppId)
	{
		this.nppId = nppId;
	}

	public String getNppTen()
	{
		return this.nppTen;
	}

	public void setNppTen(String nppTen)
	{
		this.nppTen = nppTen;
	}

	public String getSitecode()
	{
		return this.sitecode;
	}

	public void setSitecode(String sitecode)
	{
		this.sitecode = sitecode;
	}

	private void getNppInfo()
	{
		/*
		 * try { ResultSet rs = db.get(
		 * "select a.pk_seq, a.ten, a.sitecode from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='"
		 * + this.userId + "'"); if( rs != null) { rs.next(); this.nppId =
		 * rs.getString("pk_seq"); this.nppTen = rs.getString("ten");
		 * this.sitecode = rs.getString("sitecode"); } else { this.nppId = "";
		 * this.nppTen = ""; this.sitecode = ""; } } catch(Exception e) {}
		 */
		// Phien ban moi
		geso.traphaco.distributor.util.Utility util = new geso.traphaco.distributor.util.Utility();
		this.nppId = util.getIdNhapp(this.userId);
		this.nppTen = util.getTenNhaPP();
		this.sitecode = util.getSitecode();
	}

	public ResultSet getNvbhList()
	{
		return this.nvbhlist;
	}

	public void setNvbhList(ResultSet nvbhlist)
	{
		this.nvbhlist = nvbhlist;
	}

	public String getNvbhId()
	{
		return this.nvbhId;
	}

	public void setNvbhId(String nvbhId)
	{
		this.nvbhId = nvbhId;
	}

	public ResultSet getNvgnList()
	{
		return this.nvgnlist;
	}

	public void setNvgnList(ResultSet nvgnlist)
	{
		this.nvgnlist = nvgnlist;
	}

	public String getNvgnId()
	{
		return this.nvgnId;
	}

	public void setNvgnId(String nvgnId)
	{
		this.nvgnId = nvgnId;
	}

	public ResultSet getPxkList()
	{
		return this.pxklist;
	}

	public void setPxkList(ResultSet pxklist)
	{
		this.pxklist = pxklist;
	}

	public Hashtable<Integer, String> getPxkIds()
	{
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		if (this.pxkIds != null)
		{
			int size = (this.pxkIds).length;
			int m = 0;
			while (m < size)
			{
				selected.put(new Integer(m), this.pxkIds[m]);
				m++;
			}
		} else
		{
			selected.put(new Integer(0), "null");
		}
		return selected;
	}

	public void setPxkIds(String[] pxkIds)
	{
		this.pxkIds = pxkIds;
	}

	public ResultSet getDhList()
	{
		return this.dhlist;
	}

	public void setDhList(ResultSet dhlist)
	{
		this.dhlist = dhlist;
	}

	public Hashtable<Integer, String> getDhIds()
	{
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		if (this.dhIds != null)
		{
			int size = (this.dhIds).length;
			int m = 0;
			while (m < size)
			{
				String dhId = this.dhIds[m].substring(0, this.dhIds[m].indexOf(","));
				selected.put(new Integer(m), dhId);
				m++;
			}
		} else
		{
			selected.put(new Integer(0), "null");
		}
		return selected;
	}

	public void setDhIds(String[] dhIds)
	{
		this.dhIds = dhIds;
	}

	public void init()
	{
		db = new dbutils();
		this.getNppInfo();
		this.createNvbhRs();
		this.createNvgnRs();
		this.createDhRs();
	}

	private void createNvbhRs()
	{
		String sql = "select pk_seq as nvbhId, ten as nvbhTen from daidienkinhdoanh where pk_seq in (select ddkd_fk from daidienkinhdoanh_npp where npp_fk='"+this.nppId+"') and pk_seq in ";
		sql = sql +
			"(select distinct b.ddkd_fk from khachhang_tuyenbh a inner join tuyenbanhang b on b.pk_seq = a.tbh_fk inner join nvgn_kh c on a.khachhang_fk = c.khachhang_fk where b.npp_fk = '" +
			this.nppId + "' ";
		if (this.nvgnId.length() > 0)
			sql = sql + "and c.nvgn_fk = '" + this.nvgnId + "'";
		sql = sql + " )";
		System.out.println("chuoi in:" + sql);
		this.nvbhlist = db.get(sql);
	}

	private void createNvgnRs()
	{
		String sql = "select pk_seq as nvgnId, ten as nvgnTen from nhanviengiaonhan ";
		sql = sql + "where npp_fk = '" + this.nppId + "' and trangthai='1' ";
		this.nvgnlist = db.get(sql);
	}

	private String getQuery()
	{
		Utility util = new Utility();
		this.ngayksgn = util.ngaykhoaso(this.nppId);

		String query = 
			"select a.pk_seq as dhId,a.npp_fk as NppId, a.ngaynhap, a.tonggiatri,b.smartid, b.pk_seq as khId, b.ten as khTen, d.pk_seq as pxkId, d.nvgn_fk as nvgnId,dk.dktrungbay_fk as DangKyTrungBayId " +
			" from donhang a inner join khachhang b on a.khachhang_fk = b.pk_seq " +
			" inner join phieuxuatkho_donhang c on a.pk_seq = c.donhang_fk inner join phieuxuatkho d on c.pxk_fk = d.pk_seq " +
			" left join dktrungbay_donhang dk on dk.donhang_fk=a.pk_seq " +
			" where a.npp_fk = '" +this.nppId +"' and a.trangthai = '3' and a.ngaynhap > '" + this.ngayksgn + "' and a.ngaynhap <= '" + TangNgayKs() + "' ";
		if (this.nvbhId.length() > 0)
			query = query + " and a.ddkd_fk = '" + this.nvbhId + "'";
		if (this.nvgnId.length() > 0)
			query = query + " and d.nvgn_fk = '" + this.nvgnId + "'";
		if (this.ngaygiao.length() > 0)
			query = query + " and d.ngaylapphieu = '" + convertDate(this.ngaygiao) + "'";

		System.out.println("Query la: " + query + "\n");

		return query;
	}

	private String TangNgayKs()
	{
		String ngayks = this.ngayksgn;

		if (ngayks.equals(""))
			ngayks = this.getDateTime();

		String[] ngay = ngayks.split("-");

		Calendar c2 = Calendar.getInstance();

		// trong java thang bat dau bang 0 (thang rieng)
		c2.set(Integer.parseInt(ngay[0]), Integer.parseInt(ngay[1]) - 1, Integer.parseInt(ngay[2]));
		c2.add(Calendar.DATE, 1);

		String month = Integer.toString(c2.get(Calendar.MONTH) + 1);
		if ((c2.get(Calendar.MONTH) + 1) < 10)
		{
			month = "0" + Integer.toString(c2.get(Calendar.MONTH) + 1);
		}
		String date = Integer.toString(c2.get(Calendar.DATE));
		if ((c2.get(Calendar.DATE)) < 10)
		{
			date = "0" + Integer.toString(c2.get(Calendar.DATE));
		}

		System.out.println("ngay chon:" + Integer.toString(c2.get(Calendar.YEAR)) + "-" + month + "-" + date);
		return Integer.toString(c2.get(Calendar.YEAR)) + "-" + month + "-" + date;
	}

	private void createDhRs()
	{
		String query = getQuery();

		// query = "select top(50) * from (" + query + ") as List";
		// if (currentPages > 0)
		// {
		// int pos = (currentPages - 1) * num;
		// query = query + " where stt > '" + Integer.toString(pos) + "'";
		// }

		this.dhlist = createSplittingData(50, 10, "dhId desc", query);// createSplittingData(request,
																		// "dhId desc",
																		// query);//db.get(query);

	}

	public boolean chotDonhang()
	{
		if (this.dhIds != null)
		{
			try
			{
				int sodong = 0;
				// Kiem tra xem con don hang nao thoa CTTB ma chua cap nhat san
				// pham tra trung bay hay khong!
				String query = "select COUNT(*) as numb " +
					"from donhang a inner join phieuxuatkho_donhang c on a.pk_seq = c.donhang_fk " +
					"inner join phieuxuatkho d on c.pxk_fk = d.pk_seq " +
					"inner join dktrungbay_donhang dk on dk.donhang_fk=a.pk_seq  " + "where a.npp_fk = '" + this.nppId +
					"' and a.trangthai = '3' and a.ngaynhap > '" + this.ngayksgn + "' and a.ngaynhap <= '" + TangNgayKs() +
					"' and a.pk_seq not in(select donhang_fk from donhang_cttb_tratb) and dk.donhang_fk not in " +
					"(   \n "+
					"	select donhang_fk      \n "+
					"	from DKTRUNGBAY_DONHANG      \n "+
					"	where DKTRUNGBAY_FK       \n "+
					"	in     \n "+
					"	(     \n "+
					"		select pk_Seq from DANGKYTRUNGBAY      \n "+
					"		where  CTTRUNGBAY_FK in      \n "+
					"		(      \n "+
					"			select  CTTRUNGBAY_FK      \n "+
					"			from CTTB_TRATB      \n "+
					"			where TRATRUNGBAY_FK in (select PK_SEQ from TRATRUNGBAY where LOAI=1)     \n "+
					"		)     \n "+
					"	)     \n "+
					")";
				ResultSet rs = this.db.get(query);
				System.out.println("Check don hang chua cap nhat san pham tra trung bay"+query);
				if (rs != null)
				{
					while (rs.next())
					{
						sodong = rs.getInt(1);
					}
					rs.close();
				}
				if (sodong > 0)
				{
					this.msg = "Con " + sodong +
						" Don hang chua cap nhat tra trung bay!Ban phai cap nhat tra trung bay truoc khi chot don hang!";
					return false;
				}
				db.getConnection().setAutoCommit(false);

				for (int i = 0; i < this.dhIds.length; i++)
				{
					// thu tu qui uoc ben trang chotdonhang.jsp dhId > khId >
					// tonggiatri > nvgnId > pxkId > ngaynhap
					// this.msg = this.msg + this.dhIds[i] + " \\\\\\ ";

					String[] arr = this.dhIds[i].split(",");

					query = "update donhang set trangthai='1',chuyen ='1' where pk_seq = '" + arr[0] + "'";
					if (!db.update(query))
					{
						db.getConnection().rollback();
						this.msg = "Loi khi cap nhat bang 'donhang': " + query;
						return false;
					}

					/*if (this.nppId.equals("102961"))
					{*/
						query = "update nvgn_congno set datra = datra + '" + arr[2] + "', ngaytra= '" + arr[5] +
							"' where nvgn_fk = '" + arr[3] + "' and pxk_fk='" + arr[4] + "'";
						System.out.println("UPDATE NVGN_CONGNO : "+query);
						if (!db.update(query))
						{
							db.getConnection().rollback();
							this.msg = "Loi khi cap nhat bang 'nvgn_congno': " + query;
							return false;
						}

						String sql = "delete khachhang_congno where khachhang_fk='" + arr[1].trim() + "' and donhang_fk = '" +arr[0].trim() + "'";
						System.out.println("delete : "+sql);
						if (!db.update(sql))
						{
							db.getConnection().rollback();
							this.msg = "Loi khi xoa bang 'khachhang_congno': " + sql + " i:= " + Integer.toString(i);
							return false;
						}
						
						sql = 
							"Insert into khachhang_congno(khachhang_fk, sotienno, ngayno, diengiai, donhang_fk) " +
							"values('" +arr[1].trim() + "', '" + arr[2].trim() + "', '" + arr[5] + "', 'No Don Hang', '" + arr[0].trim() + "')";
						System.out.println("insert : "+sql);
						if (!db.update(sql))
						{
							db.getConnection().rollback();
							this.msg = "Loi khi cap nhat bang 'khachhang_congno': " + sql + " i:= " + Integer.toString(i);
							return false;
						}
					//}
				}
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
			} catch (Exception e)
			{
				db.update("rollback");
				this.msg = "Loi khi cap ,loi : " + e.toString();
				return false;
			}
		}
		return true;
	}

	public String getMessege()
	{
		return this.msg;
	}

	public void setMessege(String msg)
	{
		this.msg = msg;
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	private String convertDate(String date)
	{
		// chuyen dinh dang dd-MM-yyyy sang dinh dang yyyy-MM-dd
		if (!date.contains("-"))
			return getDateTime();
		String[] arr = date.split("-");
		if (arr[0].length() < arr[2].length())
			return arr[2] + "-" + arr[1] + "-" + arr[0];
		return date;
	}

	public int getNum()
	{
		return this.num;
	}

	public void setNum(int num)
	{
		this.num = num;
		listPages = PhanTrang.getListPages(num);
	}

	public int getCurrentPage()
	{
		return this.currentPages;
	}

	public void setCurrentPage(int current)
	{
		this.currentPages = current;
	}

	public int[] getListPages()
	{
		return this.listPages;
	}

	public void setListPages(int[] listPages)
	{
		this.listPages = listPages;
	}

	public int getLastPage()
	{
		String q = "select count(stt) as c from (" + getQuery() + ") dh";
		db = new dbutils();
		ResultSet rs = db.get(q);
		return PhanTrang.getLastPage(rs);
	}

	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage)
	{
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}

	@Override
	public void DBclose()
	{
		// TODO Auto-generated method stub
		try
		{
			if (this.dhlist != null)
				this.dhlist.close();
			if (this.nvbhlist != null)
				this.nvbhlist.close();
			if (this.nvgnlist != null)
				this.nvgnlist.close();
			if (pxklist != null)
			{
				pxklist.close();
			}
			pxkIds = null;
			if (this.db != null)
				this.db.getConnection().close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public boolean TraTrungBay(HttpServletRequest request)
	{
		String query = "";
		String[] DonHangId = request.getParameterValues("DonHangId");
		String[] TongTra = request.getParameterValues("TongTra");
		String[] TraTrungBayId = request.getParameterValues("TraTrungBayId");
		String[] CTTrungBayId = request.getParameterValues("CTTrungBayId");
		String[] SoXuat = request.getParameterValues("SoXuat");
		System.out.println("So Don hang la "+DonHangId.length);
		int SoDong=DonHangId.length;
		for (int i=0;i< SoDong; i++)
		{
			System.out.println("[DonHangId]"+DonHangId[i]);
			try
			{
				this.db.getConnection().setAutoCommit(false);
				String[] SanPhamId = request.getParameterValues("SanPham_" + DonHangId[i]);
				String[]GiaBan=request.getParameterValues("GiaBan_" + DonHangId[i]);
				String[] SoLuong = request.getParameterValues("SoLuong_" + DonHangId[i]);
				String[] TonHienTai = request.getParameterValues("TonHienTai_" + DonHangId[i]);
				int TongLg = Integer.parseInt(TongTra[i]);
				int total = 0;
				query = "UPDATE NHAPP_KHO SET SOLUONG=KHO.SOLUONG+DHTRA.SOLUONG ,AVAILABLE=AVAILABLE+DHTRA.SOLUONG  " +
					"FROM NHAPP_KHO KHO INNER JOIN DONHANG DH ON DH.NPP_FK=KHO.NPP_FK AND DH.KBH_FK=KHO.KBH_FK AND KHO.KHO_FK=DH.KHO_FK " +
					"INNER JOIN DONHANG_CTTB_TRATB DHTRA ON DHTRA.DONHANG_FK=DH.PK_SEQ AND KHO.SANPHAM_FK=DHTRA.SANPHAM_FK " +
					" WHERE KHO.NPP_FK='" + this.getNppId() + "'  AND DH.PK_SEQ='" + DonHangId[i] + "'";
				if (!this.db.update(query))
				{
					this.db.getConnection().rollback();
					this.msg = "Loi cap nhat kho  " + query;
					return false;
				}
				System.out.println("___Cap nhat lai kho" + query);
				query = "DELETE FROM DONHANG_CTTB_TRATB WHERE DONHANG_FK='" + DonHangId[i] + "'";
				if (!this.db.update(query))
				{
					this.db.getConnection().rollback();
					this.msg = "Loi xoa thong tin trong don hang  " + query;
					return false;
				}
				System.out.println("___Xoa thong tin______" + query);
				int soSp = SanPhamId.length;
				for (int j=0;j<soSp;j++)
				{
					int Sl = 0;
					if (SoLuong[j].trim().length() > 0)
						Sl = Integer.parseInt(SoLuong[j]);
					if (Sl > 0)
					{
						Float GiaBanSp=Float.parseFloat(GiaBan[j]);
						int Avail = Integer.parseInt(TonHienTai[j]);
						Float TienTb=GiaBanSp*Sl;
						System.out.println("[SoLuong]" + Sl + "[TonKho]" + Avail);
						// System.out.println("[SanPham]"+SanPhamId[j]+"[Soluong]"+SoLuong[j]+"[SoXuat]"+SoXuat[i]+"[TongTra]"+TongTra[j]+"[TonKho]"+Avail);
						if (Sl > Avail)
						{
							this.db.getConnection().rollback();
							this.msg = "Khong du ton kho";
							return false;
						}
						total += Sl;
						if (total > TongLg)
						{
							this.db.getConnection().rollback();
							this.msg = "Ban da nhap qua so luong cho phep";
							return false;
						}
						query = "INSERT INTO DONHANG_CTTB_TRATB(CTTB_FK,TTB_FK,DONHANG_FK,SANPHAM_FK,SOLUONG,SOXUAT,TONGGIATRI)" +
							"VALUES('" + CTTrungBayId[i] + "','" + TraTrungBayId[i] + "','" + DonHangId[i] + "','" +
							SanPhamId[j] + "','" + SoLuong[j] + "','" + SoXuat[i] + "','"+TienTb+"' ) ";
						if (!this.db.update(query))
						{
							this.db.getConnection().rollback();
							this.msg = "Loi chot don hang " + query;
							return false;
						}
						System.out.println("___Chen vao bang DONHANG_CTTB_TRATB___"+query);
						query = "UPDATE NHAPP_KHO SET SOLUONG=SOLUONG-'" + SoLuong[j] + "',AVAILABLE=AVAILABLE-'" +
							SoLuong[j] + "' " + " FROM NHAPP_KHO KHO INNER JOIN DONHANG DH ON DH.NPP_FK=KHO.NPP_FK" +
							" AND DH.KBH_FK=KHO.KBH_FK AND KHO.KHO_FK=DH.KHO_FK " + " WHERE KHO.NPP_FK='" + this.getNppId() +
							"' AND KHO.SANPHAM_FK='" + SanPhamId[j] + "' AND DH.PK_SEQ='" + DonHangId[i] + "'";
						if (!this.db.update(query))
						{
							this.msg = "Loi chot don hang " + query;
							this.db.getConnection().rollback();
							return false;
						}
						System.out.println("___Giam Kho___"+query);
					}
				}
				this.db.getConnection().commit();
				this.db.getConnection().setAutoCommit(true);
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		return true;
	}
}
