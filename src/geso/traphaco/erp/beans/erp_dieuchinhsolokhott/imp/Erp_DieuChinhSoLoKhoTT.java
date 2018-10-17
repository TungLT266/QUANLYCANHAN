package geso.traphaco.erp.beans.erp_dieuchinhsolokhott.imp;


import geso.traphaco.erp.beans.erp_dieuchinhsolokhott.IErp_DieuChinhSoLoKhoTT;
import geso.traphaco.erp.beans.erp_dieuchinhsolokhott.IErp_DieuChinhSoLoKhoTT_SanPham;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Erp_DieuChinhSoLoKhoTT implements IErp_DieuChinhSoLoKhoTT
{
	String ID, NgayDieuChinh, LyDoDieuChinh, KhoTT_FK, NgayTao, NgaySua, NguoiSua, NguoiTao, Msg, NgayChot, Khu, SPID, LOAISPID;
	int TrangThai;
	ResultSet rsKhoTT, rsSanPham, rsViTriKho, rsBin, rsKhu, rsSanpham, rsLoaiSanPham;
	List<IErp_DieuChinhSoLoKhoTT_SanPham> sanphamList;
	dbutils db;
	
	public Erp_DieuChinhSoLoKhoTT()
	{
		this.ID = "";
		this.NgayDieuChinh = "";
		this.LyDoDieuChinh = "";
		this.KhoTT_FK = "";
		this.NgayTao = "";
		this.NgaySua = "";
		this.NguoiTao = "";
		this.NguoiSua = "";
		this.Msg = "";
		this.TrangThai = 0;
		this.NgayChot = "";
		this.Khu = "";
		this.SPID = "";
		this.LOAISPID = "";
		sanphamList = new ArrayList<IErp_DieuChinhSoLoKhoTT_SanPham>();
		db = new dbutils();
	}
	
	public Erp_DieuChinhSoLoKhoTT(String id)
	{
		this.ID = id;
		this.NgayDieuChinh = "";
		this.LyDoDieuChinh = "";
		this.KhoTT_FK = "";
		this.NgayTao = "";
		this.NgaySua = "";
		this.NguoiTao = "";
		this.NguoiSua = "";
		this.Msg = "";
		this.TrangThai = 0;
		this.NgayChot = "";
		this.Khu = "";
		this.SPID = "";
		this.LOAISPID = "";
		sanphamList = new ArrayList<IErp_DieuChinhSoLoKhoTT_SanPham>();
		db = new dbutils();
	}
	
	public String getID()
	{
		return this.ID;
	}
	
	public void setID(String id)
	{
		this.ID = id;
	}
	
	public String getNgayDieuChinh()
	{
		return this.NgayDieuChinh;
	}
	
	public void setNgayDieuChinh(String ngayDieuChinh)
	{
		this.NgayDieuChinh = ngayDieuChinh;
	}
	
	public String getLyDoDieuChinh()
	{
		return this.LyDoDieuChinh;
	}
	
	public void setLyDoDieuChinh(String lyDoDieuChinh)
	{
		this.LyDoDieuChinh = lyDoDieuChinh;
	}
	
	public String getKhoTT_FK()
	{
		return this.KhoTT_FK;
	}
	
	public void setKhoTT_FK(String khoTT_FK)
	{
		this.KhoTT_FK = khoTT_FK;
	}
	
	public int getTrangThai()
	{
		return this.TrangThai;
	}
	
	public void setTrangThai(int trangThai)
	{
		this.TrangThai = trangThai;
	}
	
	public String getNguoiTao()
	{
		return this.NguoiTao;
	}
	
	public void setNguoiTao(String nguoiTao)
	{
		this.NguoiTao = nguoiTao;
	}
	
	public String getNguaSua()
	{
		return this.NguoiSua;
	}
	
	public void setNguoiSua(String nguoiSua)
	{
		this.NguoiSua = nguoiSua;
	}
	
	public String getSPID()
	{
		return this.SPID;
	}
	
	public void setSPID(String spId)
	{
		this.SPID = spId;
	}

	public ResultSet getRsKhoTT()
	{
		return this.rsKhoTT;
	}
	
	public void setRsKhoTT(ResultSet rsKhoTT)
	{
		this.rsKhoTT = rsKhoTT;
	}
	
	public ResultSet getRsSanPham()
	{
		return this.rsSanPham;
	}
	
	public void setRsSanPham(ResultSet rsSanPham)
	{
		this.rsSanPham = rsSanPham;
	}
	
	public ResultSet getRsLoaiSanPham()
	{
		return this.rsLoaiSanPham;
	}
	
	public void setRsLoaiSanPham(ResultSet rsLoaiSanPham)
	{
		this.rsLoaiSanPham = rsLoaiSanPham;
	}

	public String getLOAISPID()
	{
		return this.LOAISPID;
	}
	
	public void setLOAISPID(String loaispId)
	{
		this.LOAISPID = loaispId;
	}
	
	public void CreateRs()
	{
		
		this.rsLoaiSanPham = this.db.get("SELECT PK_SEQ AS LOAISPID, TEN FROM ERP_LOAISANPHAM WHERE TRANGTHAI = 1");
		
		String query = "SELECT PK_SEQ AS SPID, MA + ' - ' + TEN AS TEN FROM ERP_SANPHAM WHERE TRANGTHAI = 1 " ;
		if(this.LOAISPID.trim().length() > 0){
			query += " AND LOAISANPHAM_FK = " + this.LOAISPID + "";
		}
				
		query += "ORDER BY MA + ' - ' + TEN ";

		this.rsSanPham = this.db.get(query);
		
		
	}
	
	public void rsSanPhamByKhoForUpdate()
	{
		String query = "";
		if (this.SPID.length() > 0 && this.ID.length() == 0)
		{
			query = " SELECT DISTINCT A.SANPHAM_FK, E.MA AS MASP, E.TEN AS TENSP, E.LOAISANPHAM_FK, A.solo as SOLOCU,  " +
					" '' as solomoi, ISNULL(A.NGAYHETHAN, '') as ngayhethan, '' AS NGAYHETHANMOI  " +
					" FROM ERP_KHOTT_SP_CHITIET A " +
					" LEFT JOIN ERP_SANPHAM E ON E.PK_SEQ = A.SANPHAM_FK  " +
					" LEFT JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = E.LOAISANPHAM_FK " +
					" WHERE A.SANPHAM_FK = '" + this.SPID + "' AND A.AVAILABLE > 0 " +
					"ORDER BY E.MA ";
		}
		else if (this.ID.length() > 0)
		{
			query = "SELECT DISTINCT A.SANPHAM_FK,F.MA AS MASP,F.TEN AS TENSP, F.LOAISANPHAM_FK, A.SOLOCU AS SOLOCU, isnull(A.SOLOMOI,'') as solomoi, " +
					"isnull(A.NGAYHETHAN,'') as ngayhethan , " +
					
					"ISNULL(A.NGAYHETHANMOI, '') as NGAYHETHANMOI " +
					"FROM ERP_DIEUCHINHSOLOKHOTT_SANPHAM A  " +
					"INNER JOIN ERP_KHOTT_SP_CHITIET B ON A.SANPHAM_FK = B.SANPHAM_FK   " +
					"INNER JOIN ERP_SANPHAM F ON F.PK_SEQ = A.SANPHAM_FK   " +
					"LEFT JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = F.LOAISANPHAM_FK " +
					"WHERE a.dieuchinhsolokhoTT_fk = '" + this.ID + "'  " +
					"ORDER BY F.MA ";
			
		}
		
		System.out.println("1.Lay SP dieu chinh: " + query);
		
		ResultSet rsSanPham = db.get(query);
		sanphamList.clear();
		if (rsSanPham != null)
			try
			{
				while (rsSanPham.next())
				{
					IErp_DieuChinhSoLoKhoTT_SanPham s = new Erp_DieuChinhSoLoKhoTT_SanPham();
					this.SPID = rsSanPham.getString("SanPham_FK");
					s.setSanPham_FK(rsSanPham.getString("SanPham_FK"));
					s.setMaSanPham(rsSanPham.getString("MaSP"));
					s.setSoLo(rsSanPham.getString("SoLoCu"));
					s.setSoLoMoi(rsSanPham.getString("SoLoMoi"));
					s.setNgayHetHan(rsSanPham.getString("ngayhethan"));
					s.setNgayHetHanMoi(rsSanPham.getString("ngayhethanmoi"));
					s.setTenSanPham(rsSanPham.getString("TenSP"));
					this.LOAISPID = rsSanPham.getString("LOAISANPHAM_FK");
					sanphamList.add(s);
				}
				rsSanPham.close();
			}
			catch (SQLException e)
			{
				System.out.println("ex "+query);
				e.printStackTrace();
			}
	}
	
	public List<IErp_DieuChinhSoLoKhoTT_SanPham> getSanPhamKhoList()
	{
		return this.sanphamList;
	}
	
	public void setSanPhamKho(List<IErp_DieuChinhSoLoKhoTT_SanPham> SanPhamKhoList)
	{
		this.sanphamList = SanPhamKhoList;
	}
	
	public static void main(String[] agr)
	{
		String numb = "d.";
		if (numb.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+"))
			System.out.println("So ne");
		else
			System.out.println(" K phai so");
	}
	
	public boolean Update()
	{
		int number_sp = this.sanphamList.size();
		System.out.println("Update---So San Pham " + number_sp);
		String query = "";
		try
		{
			if (this.NgayDieuChinh.length() == 0)
			{
				this.Msg = "Vui lòng chọn ngày điều chỉnh !";
				System.out.print("----------" + this.NgayDieuChinh);
				return false;
			}
			if (this.LyDoDieuChinh.length() == 0)
			{
				this.Msg = "Vui lòng chọn lý do điều chỉnh!";
				return false;
			}
			if (this.KhoTT_FK.length() == 0)
			{
				this.Msg = "Vui lòng chọn kho điều chỉnh!";
				return false;
			}
			if (this.NgayChot.length() == 0)
			{
				this.Msg = "Vui lòng thêm ngày chốt điều chỉnh tồn kho!";
				return false;
			}
			if (number_sp > 0)
			{
				query =
				"Update ERP_DIEUCHINHSOLOKHOTT set NgaySua='" + getDateTime() + "',LyDoDieuChinh=N'" +
				this.LyDoDieuChinh + "',NguoiSua='" + this.NguoiSua + "',NgayChot='" + this.NgayChot + "', KhoTT_FK='" +
				this.KhoTT_FK + "',Khu_FK='" + this.Khu + "' Where PK_SEQ='" + this.ID + "'";
				db.getConnection().setAutoCommit(false);
				if (!db.update(query))
				{
					this.setMessage("Không thể cập nhật kho " + query);
					db.getConnection().rollback();
					return false;
				}
				for (int i = 0; i < number_sp; i++)
				{
					IErp_DieuChinhSoLoKhoTT_SanPham s = this.sanphamList.get(i);
					if (s.getSoLoMoi().length() >0)
					{
						query =
						" DECLARE @I INT	" + "UPDATE ERP_DIEUCHINHSOLOKHOTT_SANPHAM " + " SET  SOLOMOI='"+s.getSoLoMoi()+"',ngayhethan='"+s.getNgayHetHan()+"'  WHERE  SanPham_FK='" +s.getSanPham_FK() +
						"'" +" AND  SOLOCU='" +s.getSoLo() +"' AND Bin='" +s.getBin_FK() +"' AND dieuchinhsolokhoTT_fk='" +this.ID +
						"'" +" SELECT @I=@@ROWCOUNT " +" IF @I=0 " +
						"	BEGIN " +
						"Insert into ERP_DIEUCHINHSOLOKHOTT_SANPHAM(dieuchinhsolokhoTT_fk,SanPham_FK,Bin,SoLoCu,TonCu,TonHienTai,SoLoMoi,ngayhethan) " +
						" values('" + this.ID + "','" + s.getSanPham_FK() + "','" + s.getBin_FK() + "','" + s.getSoLo() + "','" +
						s.getTonKhoCu() + "','" + s.getTonHienTai() + "','" +s.getSoLoMoi()+ "','"+s.getNgayHetHan()+"') END ";
						System.out.println(query);
					}
					
					db.getConnection().setAutoCommit(false);
					if (!db.update(query))
					{
						db.update("rollback");
						System.out.println("Loi trong luc cap nhat: " + query);
						this.Msg = "Loi trong luc cap nhat: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			else
			{
				this.Msg = "Chưa có sản phẩm nào được cập nhật số lượng tồn!";
				return false;
			}
			this.db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (SQLException e)
		{
			this.setMessage("Exception" + query);
			db.update("rollback");
			System.out.println("Error Create New Erp_DieuChinhTonKho " + e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/* Huy dieu chinh ton kho */
	public boolean Cancel()
	{
		
		String query = " Delete From ERP_DIEUCHINHSOLOKHOTT_SANPHAM Where dieuchinhsolokhoTT_fk='" + this.ID + "'";
		query += " Delete From ERP_DIEUCHINHSOLOKHOTT Where pk_seq=" + this.ID + " And TrangThai='0' ";
		if (db.update(query))
			return true;
		else
		{
			System.out.println("Huy Dieu Chinh -Cancel-" + query);
			this.Msg = "Lỗi: " + query;
			return false;
		}
	}
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	// Tao moi dieu chinh ton kho trung tam
	public boolean SaveNew()
	{
		String ngaytao = getDateTime();
		int number_sp = this.sanphamList.size();
		System.out.println("So San Pham " + number_sp);
		String query = "";
		try
		{
			if (this.NgayDieuChinh.length() == 0)
			{
				this.Msg = "Vui lòng chọn ngày điều chỉnh !";
				System.out.print("----------" + this.NgayDieuChinh);
				return false;
			}

			if (this.LyDoDieuChinh.length() == 0)
			{
				this.Msg = "Vui lòng chọn lý do điều chỉnh!";
				return false;
			}

			if (number_sp > 0)
			{
				query = "Insert into Erp_DieuChinhSoLoKhoTT(NgayDieuChinh,LyDoDieuChinh, TrangThai,NgayTao,NguoiTao," +
						"NguoiSua,NgaySua,NgayChot) " +
						" values('" +this.NgayDieuChinh +"',N'" +this.LyDoDieuChinh +"', 0, '" + ngaytao + "',  " + this.NguoiTao + "," + this.NguoiTao + ",'" + ngaytao + "','" + this.NgayDieuChinh + "')";
				
				db.getConnection().setAutoCommit(false);
				if (db.update(query) != true)
				{
					this.Msg = "Lỗi tạo mới :" + query;
					db.getConnection().rollback();
					System.out.println("Khong the Insert" + query);
					return false;
				}
				else
				{
					String dcID = "";
					query = "select IDENT_CURRENT('Erp_DieuChinhSoLoKhoTT')  as dcID";
					ResultSet rsNh = db.get(query);
					if (rsNh != null)
					{
						while (rsNh.next())
						{
							dcID = rsNh.getString("dcID");
						}
						rsNh.close();
					}
					else
					{
						this.Msg = "Lỗi đổi số lô : " + query;
						db.getConnection().rollback();
						return false;
					}
					for (int i = 0; i < number_sp; i++)
					{
						IErp_DieuChinhSoLoKhoTT_SanPham s = this.sanphamList.get(i);
						if(s.getSoLoMoi().trim().length() > 0 || s.getNgayHetHanMoi().length() > 0){
							query = "Insert Erp_DieuChinhSoLoKhoTT_SanPham(DieuChinhSoLoKhoTT_FK,SanPham_FK, SoLoCu, solomoi, ngayhethan, ngayhethanmoi) " +
									"values('" + dcID + "','" + s.getSanPham_FK() + "', '" + s.getSoLo() + "', '" + s.getSoLoMoi() + "', '" + s.getNgayHetHan() + "', '" + s.getNgayHetHanMoi() + "' )";
						
							System.out.println(query);
							boolean isSuccess = db.update(query);
							db.getConnection().setAutoCommit(false);
							if (isSuccess != true)
							{
								db.update("rollback");
								this.Msg = "1.Không thể tạo mới ERP_DIEUCHINHSOLOKHOTT_SANPHAM: " + query;
								db.getConnection().rollback();
								return false;
							}
						}
					}

				}
				this.db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
				
			}
			else
			{
				db.update("rollback");
				this.Msg = "Chưa có sản phẩm nào được cập nhật tồn kho!";
				return false;
			}
		}
		catch (SQLException e)
		{
			db.update("rollback");
			this.Msg = "Lỗi tạo mới  : " + query;
			System.out.println("Error Create New Erp_DieuChinhTonKho " + e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	public String getMessage()
	{
		return this.Msg;
	}
	
	public void setMessage(String msg)
	{
		this.Msg = msg;
	}
	
	public void beanDctktt()
	{
		String query = " Select PK_SEQ, NgayDieuChinh, LyDoDieuChinh, NgayChot From ERP_DIEUCHINHSOLOKHOTT " +
					   "Where PK_SEQ = " + this.ID + " ";
		ResultSet rsDieuChinhTonKhoTT = db.get(query);
		try
		{
			while (rsDieuChinhTonKhoTT.next())
			{
				this.ID = rsDieuChinhTonKhoTT.getString("PK_SEQ");

				this.LyDoDieuChinh = rsDieuChinhTonKhoTT.getString("LyDoDieuChinh");
				this.NgayDieuChinh = rsDieuChinhTonKhoTT.getString("NgayDieuChinh");
				this.NgayChot = rsDieuChinhTonKhoTT.getString("NgayChot");
				
			}
		}
		catch (SQLException e)
		{
			System.out.println("beanDctktt Error!");
			e.printStackTrace();
		}finally{
			try {
				rsDieuChinhTonKhoTT.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}
	
	public void rsSanPhamKhoDisplay()
	{
		NumberFormat format = new DecimalFormat("##,###,###");
		
		String query =  " SELECT A.SANPHAM_FK,F.MA AS MASP,F.TEN AS TENSP,C.TEN AS BIN,C.PK_SEQ  AS BIN_FK, A.SOLOCU ,A.SOLOMOI,A.NGAYHETHAN , '' as TRANGTHAI_FK, N'Đạt chất lượng' AS TRANGTHAI,   " +
						" D.PK_SEQ KHU_FK,  D.TEN VITRIKHO, SOLUONGDIEUCHINH, A.TONCU   " +
					"FROM ERP_DIEUCHINHSOLOKHOTT_SANPHAM A     " +
						"INNER JOIN ERP_VITRIKHO C ON C.PK_SEQ = A.BIN    INNER JOIN ERP_KHUVUCKHO D ON D.PK_SEQ = C.KHU_FK and D.pk_seq = '" + this.Khu + "'     " +
						"INNER JOIN ERP_SANPHAM F ON F.PK_SEQ = A.SANPHAM_FK    INNER JOIN DONVIDOLUONG  G ON G.PK_SEQ=F.DVDL_FK    " +
					"WHERE  a.dieuchinhsolokhoTT_fk = '" + this.ID + "' and  isnull(solomoi ,'') <> '' " +
					"ORDER BY SANPHAM_FK, TONHIENTAI ";
		
		System.out.println("1.Hien thi: " + query);
		this.rsSanPham = db.get(query);
		sanphamList.clear();
		if (rsSanPham != null)
			try
			{
				while (rsSanPham.next())
				{
					IErp_DieuChinhSoLoKhoTT_SanPham s = new Erp_DieuChinhSoLoKhoTT_SanPham();
					s.setSanPham_FK(rsSanPham.getString("SanPham_FK"));
					s.setMaSanPham(rsSanPham.getString("MaSP"));
					s.setBin(rsSanPham.getString("Bin"));
					s.setBin_FK(rsSanPham.getString("Bin_FK"));
					s.setSoLo(rsSanPham.getString("SoLoCu"));
					
					s.setSoLoMoi(rsSanPham.getString("SoLoMoi"));
					s.setNgayHetHan(rsSanPham.getString("ngayhethan"));
					
					
					s.setKhu_FK(rsSanPham.getString("Khu_FK"));
				

					String tonkhoCu = rsSanPham.getString("TonCu") == null ? "" : rsSanPham.getString("TonCu");
					if(tonkhoCu.trim().length() > 0)
					{
						s.setTonKhoCu(format.format(Double.parseDouble(tonkhoCu)));
					}
					
					s.setTenSanPham(rsSanPham.getString("TenSP"));
					sanphamList.add(s);
				}
				rsSanPham.close();
			}
			catch (SQLException e)
			{
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
	}
	
	public void ViewToUpdate()
	{
		
		beanDctktt();
		rsSanPhamByKhoForUpdate();
		CreateRs();
	}
	
	public void display()
	{
		beanDctktt();
		rsSanPhamKhoDisplay();
		CreateRs();
	}
	
	// Chot
	public boolean Approve()
	{
		String query = "";
		try
		{
			beanDctktt();
			rsSanPhamByKhoForUpdate();
			int number_sp = this.sanphamList.size();
			String ngaychot = this.NgayChot;
			int sodong = 0;
			int thang = Integer.valueOf(ngaychot.substring(5, 7));
			int nam = Integer.valueOf(ngaychot.substring(0, 4));
			System.out.println("Thang chot la " + thang);
			System.out.println("Nam chot la " + nam);
			query = " Select count(PK_SEQ) as sodong From Erp_KhoaSoThang Where " + " " + thang +
			" IN ( Select ThangKS From Erp_KhoaSoThang Where Nam=" + nam + ")";
			ResultSet rs = this.db.get(query);
			if (rs != null)
			{
				while (rs.next())
				{
					sodong = rs.getInt("sodong");
				}
				rs.close();
			}
			if (sodong > 0)
			{
				System.out.println("So dong " + sodong);
				this.setMessage("Không thể chốt điều chỉnh tồn kho vì tháng " + thang + " đã khóa sổ rồi!");
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			for (int i = 0; i < number_sp; i++)
			{
				IErp_DieuChinhSoLoKhoTT_SanPham s = this.sanphamList.get(i);
				// Kiem tra Booked va so luong available
				
		
				if (s.getSoLoMoi().length() > 0)
				{
														
					query = "UPDATE ERP_KHOTT_SP_CHITIET SET SOLO = '" + s.getSoLoMoi() + "', NGAYHETHAN = '" + s.getNgayHetHanMoi() + "' " +
							"WHERE SANPHAM_FK = '" + s.getSanPham_FK() + "' AND SOLO = '" + s.getSoLo() + "' "	;
					
					System.out.println(query);					
					if (!this.db.update(query))
					{
						db.update("rollback");
						this.Msg = "Không thể chốt " + query;
						System.out.println("Không thể chốt " + query);
						return false;
					}

				}// End While rsSanPham
			}
			query =
				"Update ERP_DIEUCHINHSOLOKHOTT set NgaySua='" + getDateTime() + "',NguoiSua='" + this.NguoiSua +
				"'" + ", TrangThai=1 Where PK_SEQ='" + this.ID + "'";
				db.getConnection().setAutoCommit(false);
				if (!db.update(query))
				{
					this.setMessage("Không thể cập nhật kho " + query);
					db.getConnection().rollback();
					return false;
				}
				
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}
		catch (Exception e)
		{
			db.update("rollback");
			System.out.println("Exception Update Trang thai Chot" + query);
			System.out.println(e.getMessage());
			this.Msg = "Lỗi :" + e.getMessage();
			e.printStackTrace();
			return false;
		}
		return true;
	}// End Method
	
	public boolean CheckNumerOrNot(String number)
	{
		if (number.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+"))
			return true;
		return false;
	}
	
	public boolean CheckValid(IErp_DieuChinhSoLoKhoTT_SanPham o)
	{
		float Booked = 0.0f;
		float TonThucTe = 0;
		
		if (CheckNumerOrNot(o.GetTonThucTe()))
		{
			String query =
			"Select Booked From Erp_KhoTT_SP_ChiTiet Where SanPham_FK='" + o.getSanPham_FK() + "' AND " +
			" Bin='" + o.getBin_FK() + "'  AND  SOLO='" + o.getSoLo() +
			"'";
			ResultSet rs = this.db.get(query);
			if (rs != null)
			{
				try
				{
					while (rs.next())
					{
						Booked = rs.getFloat("Booked");
					}
					rs.close();
					
					TonThucTe = Float.parseFloat(o.GetTonThucTe());
					if (Booked > TonThucTe)
					{
						this.Msg =
						"Không thể chốt vì Booked > Tồn thực tế (" + Booked + " >+ " + TonThucTe + ") của sản phẩm " +
						o.getMaSanPham() + " ở Lô '"+o.getSoLo()+"' ,Bin '"+o.getBin()+"' ";
						return false;
					}
					
				}
				
				catch (SQLException e)
				{
					this.db.shutDown();
					e.printStackTrace();
					return false;
				}
			}
			System.out.println("Booked hien tai " + Booked);
		}
		return true;
	}
	
	public String getNgayChot()
	{
		return this.NgayChot;
	}
	
	public void setNgayChot(String ngaychot)
	{
		this.NgayChot = ngaychot;
	}
	
	public void close()
	{
		try
		{
			if (this.rsKhoTT != null)
				rsKhoTT.close();
			if (this.rsSanPham != null)
				rsSanPham.close();
			if (this.rsKhu != null)
				this.rsKhu.close();
		}
		catch (SQLException e)
		{
			this.db.shutDown();
			e.printStackTrace();
		}
	}
	
	public ResultSet getViTriKhoRs()
	{
		
		return this.rsViTriKho;
	}
	
	public void setViTriKhoRs(ResultSet vitrikho)
	{
		this.rsViTriKho = vitrikho;
	}
	
	public ResultSet getBinRs()
	{
		
		return this.rsBin;
	}
	
	public void setBinRs(ResultSet binRs)
	{
		this.rsBin = binRs;
	}
	
	public void PDF()
	{
		
	}
	
	public void SetKhu_FK(String Khu_FK)
	{
		this.Khu = Khu_FK;
	}
	
	public ResultSet getKhuRs()
	{
		
		return this.rsKhu;
	}
	
	public void setKhuRs(ResultSet KhuRs)
	{
		this.rsKhu = KhuRs;
	}
	
	public String getKhu_FK()
	{
		
		return this.Khu;
	}
}
