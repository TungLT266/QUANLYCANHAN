package geso.traphaco.erp.beans.nhapkho.giay.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.apache.jasper.tagplugins.jstl.core.ForTokens;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.nhapkho.giay.IKhu_Vitri;
import geso.traphaco.erp.beans.nhapkho.giay.ISanpham;
import geso.traphaco.erp.beans.nhapkho.giay.ISanphamCon;
import geso.traphaco.erp.beans.nhapkho.giay.imp.Sanpham;
import geso.traphaco.erp.beans.nhapkho.giay.IErpNhapkho;

public class ErpNhapkho implements IErpNhapkho
{
	String congtyId;
	String userId;
	String id;
	String ngaynhapkho;
	String ngaychotNV;
	
	String sophieunhan;
	String sodontrahang;
	String solenhsanxuat;

	String khoId;
	ResultSet khoRs;
	String ndnId;
	ResultSet ndnRs;
	String lhhId;
	ResultSet lhhRs;
	String vtlkId;
	ResultSet vtlkRs;
	String trangthai;
	String Diem;
	boolean quanlybean;
	boolean quanlylo;
	ResultSet rsLenhsanxuat,rsCongdoan;
	String CongDoanId;
	

	List<ISanpham> spList;

	List<IKhu_Vitri> khuList;
	List<IKhu_Vitri> vitriList;

	String msg;

	// pdf
	String nguoinhanhang;
	String diachinhan;
	String nhaptaikho;
	String ghichu;

	dbutils db;
	private Utility util;
	NumberFormat formatter = new DecimalFormat("#######.###");
	public ErpNhapkho()
	{
		this.userId = "";
		this.id = "";
		this.ngaynhapkho = "";
		this.sophieunhan = "";
		this.khoId = "";
		this.ndnId = "";
		this.lhhId = "0";
		this.vtlkId = "";
		this.trangthai = "";
		this.msg = "";
		this.Diem = "";
		this.quanlybean = true;
		this.quanlylo = true;
		this.spList = new ArrayList<ISanpham>();
		this.khuList = new ArrayList<IKhu_Vitri>();
		this.vitriList = new ArrayList<IKhu_Vitri>();

		this.nguoinhanhang = "";
		this.diachinhan = "";
		this.nhaptaikho = "";
		this.ghichu = "";
		this.sodontrahang = "";
		this.solenhsanxuat = "";
		this.ngaychotNV = "";
		this.util=new Utility();
		this.db = new dbutils();
	}

	public ErpNhapkho(String id)
	{
		this.userId = "";
		this.id = id;
		this.ngaynhapkho = "";
		this.sophieunhan = "";
		this.khoId = "";
		this.ndnId = "";
		this.lhhId = "0";
		this.vtlkId = "";
		this.trangthai = "";
		this.msg = "";
		this.quanlybean = true;
		this.quanlylo = true;
		this.spList = new ArrayList<ISanpham>();
		this.khuList = new ArrayList<IKhu_Vitri>();
		this.vitriList = new ArrayList<IKhu_Vitri>();

		this.nguoinhanhang = "";
		this.diachinhan = "";
		this.nhaptaikho = "";
		this.ghichu = "";
		this.sodontrahang = "";
		this.solenhsanxuat = "";
		this.ngaychotNV = "";
		this.util=new Utility();
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

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getNgaynhapkho()
	{
		return this.ngaynhapkho;
	}

	public void setNgaynhapkho(String ngaynhapkho)
	{
		this.ngaynhapkho = ngaynhapkho;
	}

	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public String getSoPnh()
	{
		return this.sophieunhan;
	}

	public void setSoPnh(String soPnh)
	{
		this.sophieunhan = soPnh;
	}

	public void setmsg(String msg)
	{
		this.msg = msg;
	}

	public String getmsg()
	{
		return this.msg;
	}

	public void setKhoId(String khoId)
	{
		this.khoId = khoId;
	}

	public String getKhoId()
	{
		return this.khoId;
	}

	public ResultSet getKhoList()
	{
		return this.khoRs;
	}

	public void setKhoList(ResultSet khoList)
	{
		this.khoRs = khoList;
	}

	public void setNdnId(String ndnId)
	{
		this.ndnId = ndnId;
	}

	public String getNdnId()
	{
		return this.ndnId;
	}

	public ResultSet getNdnList()
	{
		return this.ndnRs;
	}

	public void setNdnList(ResultSet ndnList)
	{
		this.ndnRs = ndnList;
	}

	public void setLhhId(String lhhId)
	{
		this.lhhId = lhhId;
	}

	public String getLhhId()
	{
		return this.lhhId;
	}

	public ResultSet getLhhList()
	{
		return this.lhhRs;
	}

	public void setLhhList(ResultSet lhhList)
	{
		this.lhhRs = lhhList;
	}

	public void setVtkId(String lhhId)
	{
		this.lhhId = lhhId;
	}

	public String getVtkId()
	{
		return this.vtlkId;
	}

	public ResultSet getVtkList()
	{
		return this.vtlkRs;
	}

	public void setVtkList(ResultSet vtkList)
	{
		this.vtlkRs = vtkList;
	}

	public List<ISanpham> getSpList()
	{
		return this.spList;
	}

	public void setSpList(List<ISanpham> spList)
	{
		this.spList = spList;
	}

	public List<IKhu_Vitri> getKhuList()
	{
		return this.khuList;
	}

	public void setKhuList(List<IKhu_Vitri> khuList)
	{
		this.khuList = khuList;
	}

	public List<IKhu_Vitri> getVitriList()
	{
		return this.vitriList;
	}

	public void setVitriList(List<IKhu_Vitri> vitriList)
	{
		this.vitriList = vitriList;
	}

	public boolean getQuanLyBean()
	{
		return this.quanlybean;
	}

	public void setQuanLyBean(boolean quanlybean)
	{
		this.quanlybean = quanlybean;
	}

	public void createRs()
	{
		String query="select pk_Seq  from erp_lenhsanxuat_giay where pk_seq='"+this.solenhsanxuat+"'";
		this.rsLenhsanxuat=this.db.get(query);
		query="select pk_Seq,diengiai from erp_congdoansanxuat_Giay where pk_seq='"+this.CongDoanId+"'";
		this.rsCongdoan=this.db.get(query);
		this.ndnRs = db.get("select pk_seq, MA + ' - ' + TEN as TEN from ERP_NOIDUNGNHAP where trangthai = '1' and upper(substring(ma, 0, 3)) = upper('NK') and pk_seq != '100005' ");
		this.khoRs = db.get("select PK_SEQ, TEN, DIACHI, QUANLYBIN from ERP_KHOTT where trangthai = '1' and congty_fk = '" + this.congtyId + "' and pk_seq='"+this.khoId+"'");
		this.lhhRs = db.get("select PK_SEQ, TEN from ERP_LOAISANPHAM");
	}

	public void init()
	{
		String query = "select a.PK_SEQ as nhId, a.KHONHAP, a.SOPHIEUNHAPHANG, a.SODONTRAHANG, a.SoLenhSanXuat," +
				    " a.NGAYNHAPKHO, b.pk_seq as ndnId, b.TEN as ndnTen, a.TRANGTHAI, c.QUANLYBIN,ISNULL(a.GhiChu,'')as GhiChu, " +
					" k.Prefix+cast(a.SOLENHSANXUAT as varchar(20)) as SOCHUNGTU,a.CongDoan_FK as CongDoanId, " +
					" a.NGAYNHAPKHO, isnull(a.NGAYCHOT, '') as NgayChot, a.NOIDUNGNHAP, b.TEN as ndnTen, a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO  " +
					" from ERP_NHAPKHO a inner join ERP_NOIDUNGNHAP b on a.NOIDUNGNHAP = b.PK_SEQ inner join ERP_KHOTT c on a.KHONHAP = c.PK_SEQ " +
					"  left join erp_lenhsanxuat_Giay k on a.SOLENHSANXUAT = k.pk_seq " +
					" where a.PK_SEQ = '" + this.id + "'";
		
		System.out.println("Init " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
		try 
		{
			while(rs.next())
			{
				this.ngaynhapkho = rs.getString("NGAYNHAPKHO");
				
				this.ngaychotNV = rs.getString("NgayChot");
				this.ndnId = rs.getString("ndnId");
				this.solenhsanxuat = rs.getString("SoLenhSanXuat");
				this.CongDoanId=rs.getString("CongDoanId");
				this.khoId = rs.getString("KHONHAP");
				this.trangthai = rs.getString("trangthai");
				this.ghichu=rs.getString("GhiChu");
				if(rs.getString("QUANLYBIN").equals("0"))
					this.quanlybean = false;
				else
					this.quanlybean = true;
			}
			rs.close();
		} 
		catch (SQLException e) {}
		}
		createRs();
		this.initSanPham();
	}

	public void initPdf()
	{
		String query = " select a.PK_SEQ as nhId, a.SOPHIEUNHAPHANG, a.NGAYNHAPKHO, b.Ma + '; ' + b.Ten as ndnTen, nm.tennhamay as nhamay,"
				+ "   a.TRANGTHAI, c.ten as khoNhap, (k.PREFIX + cast(a.SOLENHSANXUAT as varchar(20))) as SOCHUNGTU, a.NGAYTAO as ngaychungtu "
				+ " from ERP_NHAPKHO a "
				+ " inner join ERP_LENHSANXUAT_GIAY k on a.solenhsanxuat = k.pk_seq "
				+ " inner join ERP_NOIDUNGNHAP b on a.NOIDUNGNHAP = b.PK_SEQ " 
				+ " inner join ERP_KHOTT c on a.KHONHAP = c.PK_SEQ "
				+ " inner join Erp_KichBanSanXuat_Giay kbsx on k.KICHBANSANXUAT_FK = kbsx.PK_SEQ "
				+ " inner join ERP_NHAMAY nm on kbsx.NhaMay_FK = nm.pk_seq "
				+ " where a.pk_seq = '" + this.id + "'";
		
		System.out.println("[ErpNhapkho.initPdf] query = " + query);

		ResultSet rs = db.get(query);
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					this.ngaynhapkho = rs.getString("NGAYNHAPKHO");
					this.sophieunhan = "120" + rs.getString("SOPHIEUNHAPHANG");
					this.solenhsanxuat = rs.getString("SOCHUNGTU");
					this.ndnId = rs.getString("ndnTen");
					this.khoId = rs.getString("khoNhap");
					this.nhaptaikho = rs.getString("nhamay");
					this.ngaychotNV = rs.getString("ngaychungtu"); //Ngày chứng từ
					this.trangthai = rs.getString("trangthai");
					//this.Diem = rs.getString("Diem");
				}
				rs.close();
			} catch (SQLException e){}
		}

		this.initSanPhamPdf();
	}

	private void initSanPham()
	{
		NumberFormat formater = new DecimalFormat("#,###,###");
		
		String query = "select a.pk_seq, a.sanpham_fk, b.ma as spMa, b.TEN, a.soluongnhan, a.soluongnhap, a.solo, a.ngayhethan,a.NgaySanXuat,a.NgayNhapKho, a.dongia, a.dongiaViet, a.tiente_fk " +
						"from ERP_NHAPKHO_SANPHAM a inner join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ where a.SONHAPKHO_FK = '" + this.id + "'";
		
		System.out.println("__Khoi tao nhap kho: " + query);
		ResultSet rsSp = db.get(query);

		if (rsSp != null)
		{
			try
			{
				ISanpham sanpham;
				String[] param = new String[6];
				List<ISanpham> spList = new ArrayList<ISanpham>();
				while (rsSp.next())
				{
					param[0] = rsSp.getString("sanpham_fk");
					param[1] = rsSp.getString("spMa");
					param[2] = rsSp.getString("ten");
					param[3] = rsSp.getString("solo");
					param[4] = formater.format(rsSp.getDouble("soluongnhan"));
					param[5] = formater.format(rsSp.getDouble("soluongnhap"));
					String dongia = rsSp.getString("dongia");

					String pk_seq = rsSp.getString("pk_seq");

					String comand = "select SOLUONG, KHU, cast(KHU as nvarchar(10)) + ' - ' + cast(vitri as nvarchar(10)) as vitri from ERP_NHAPKHO_SP_CHITIET where NHAPKHO_SANPHAM_FK = '"
							+ pk_seq + "'";
					ResultSet spConRs = db.get(comand);

					List<ISanphamCon> spConList = new ArrayList<ISanphamCon>();
					ISanphamCon spCon = null;
					if (spConRs != null)
					{
						while (spConRs.next())
						{
							spCon = new SanphamCon(param[0], spConRs.getString("SOLUONG"), spConRs.getString("KHU"),
									spConRs.getString("vitri"));
							spConList.add(spCon);
						}
						spConRs.close();
					}

					sanpham = new Sanpham(param);
					sanpham.setNgayhethan(rsSp.getString("ngayhethan"));
					sanpham.setDongia(dongia);
					sanpham.setDongiaViet(rsSp.getString("dongiaViet"));
					sanpham.setTiente(rsSp.getString("tiente_fk"));
					sanpham.setNgayNhapKho(rsSp.getString("NgayNhapKho"));
					sanpham.setNgaySanXuat(rsSp.getString("NgaySanXuat"));
					sanpham.setSpConList(spConList);
					spList.add(sanpham);
				}
				rsSp.close();
				this.spList = spList;
			} 
			catch (Exception e) 
			{
				System.out.println("Exception: " + e.getMessage());
			}
		}
	}
	

	private void initSanPhamPdf()
	{
		String query = "select a.SANPHAM_FK, b.MA, b.TEN + ' ' + b.QUYCACH as TEN, c.DONVI as DVT, isnull(d.SOLUONG1, '1') as quycach, a.SOLUONGNHAP as SOLUONG, "
				+ "d.SOLUONG2, isnull(b.TRONGLUONG, '0') as TRONGLUONG, isnull(b.THETICH, '0') as THETICH, a.SOLO "
				+ "from ERP_NHAPKHO_SANPHAM a inner join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ inner join DONVIDOLUONG c on b.DVDL_FK = c.PK_SEQ "				
				+ " LEFT join QUYCACH d on b.PK_SEQ = d.SANPHAM_FK " + "where SONHAPKHO_FK = '" + this.id + "'";

		System.out.println("[ErpNhapkho.initSanPhamPdf] query: " + query);
		ResultSet rs = db.get(query);

		if (rs != null)
		{
			List<ISanpham> spList = new ArrayList<ISanpham>();

			try
			{
				ISanpham sanpham;
				while (rs.next())
				{
					sanpham = new Sanpham();
					sanpham.setId(rs.getString("SANPHAM_FK"));
					sanpham.setMa(rs.getString("MA"));
					sanpham.setDiengiai(rs.getString("TEN"));

					float soluong = rs.getFloat("SOLUONG");
					sanpham.setSoluongnhapkho(Float.toString(soluong));

					sanpham.setSolo(rs.getString("SOLO"));
					sanpham.setDVT(rs.getString("DVT"));
					int quycach =Integer.valueOf(( rs.getString("quycach") == null ? "1" : rs.getString("quycach")));
					sanpham.setQuycach(Integer.toString(quycach));

					float soluong2 = rs.getFloat("SOLUONG2");

					float thung = soluong * soluong2 / quycach;
					float le = (soluong * soluong2) % quycach;

					System.out.println("[ErpNhapkho.initSanPhamPdf] Thung: " + thung);
					System.out.println("[ErpNhapkho.initSanPhamPdf] Le: " + le);

					sanpham.setThung(Float.toString(thung));
					sanpham.setLe(Float.toString(le));
					sanpham.setTrongluong(rs.getString("TRONGLUONG"));
					sanpham.setThetich(rs.getString("THETICH"));

					spList.add(sanpham);
				}

				rs.close();
			} catch (SQLException e)
			{
				System.out.println("Exception: " + e.getMessage());
			}

			this.spList = spList;
		}
	}

	public boolean createNhapKho()
	{
		if (this.spList.size() <= 0)
		{
			this.msg = "Không có sản phẩm nào để nhập kho, vui lòng kiểm tra lại";
			return false;
		}
		
		boolean flag = false;
		for(int i = 0; i < this.spList.size(); i++)
		{
			ISanpham sp = this.spList.get(i);
			if(sp.getSoluongnhapkho().length() > 0)
			{
				if(Float.parseFloat(sp.getSoluongnhapkho()) > 0)
				{
					flag = true;
					break;
				}
			}
		}
		if(flag == false)
		{
			this.msg = "Không có sản phẩm nào được nhập số lượng để nhập kho, vui lòng kiểm tra lại";
			return false;
		}

		try
		{
			String ngaytao = getDateTime();

			db.getConnection().setAutoCommit(false);

			String query = "";
			String sonhanhang = "";
			
			if(this.ndnId.equals("100004"))
			{
				String sodontrahang = this.sodontrahang.substring(3, this.sodontrahang.length());
				
				query =	"insert ERP_NHAPKHO(ngaynhapkho, ngaychot, SODONTRAHANG, NOIDUNGNHAP, KHONHAP, TRANGTHAI, ngaytao, ngaysua, nguoitao, nguoisua,GhiChu, congty_fk) " +
						"values('" + this.ngaynhapkho + "', '" + this.ngaynhapkho + "', '" + sodontrahang + "', '" + this.ndnId + "', '" + this.khoId + "', " +
								"'0', '" + ngaytao + "', '" + ngaytao + "', '" + this.userId + "', '" + this.userId + "',N'"+this.ghichu+"', '" + this.congtyId + "')";
			}
			else
			{
				sonhanhang = this.sophieunhan.substring(5, this.sophieunhan.length());
				
				//Kiem tra ngay nhap kho co sau ngay nhan hang khong
				query = "select NGAYNHAN from erp_nhanhang where pk_seq = '" + sonhanhang + "'";
				ResultSet gnh = db.get(query);
				String ngaynhan = "";
				if(gnh.next())
				{
					ngaynhan = gnh.getString("ngaynhan");
					gnh.close();
				}

				Calendar c1 = Calendar.getInstance();

				Calendar c2 = Calendar.getInstance();
				String[] ngaynh = ngaynhan.split("-");
				c1.set(Integer.parseInt(ngaynh[0]),Integer.parseInt(ngaynh[1]), Integer.parseInt(ngaynh[2]));
				String[] ngaynk = this.ngaynhapkho.split("-");
				c2.set(Integer.parseInt(ngaynk[0]),Integer.parseInt(ngaynk[1]), Integer.parseInt(ngaynk[2]));
				boolean kt = c1.after(c2);
				boolean kt2 = c1.equals(c2);
				if(kt == true && kt2 == false)
				{
					this.msg = "Phiếu nhập  " + this.sophieunhan + " có ngày nhận hàng (" + ngaynh[0]+"-"+ngaynh[1]+"-"+ngaynh[2] + ") sau ngày nhập kho (" + ngaynk[0]+"-"+ngaynk[1]+"-"+ngaynk[2] + ")";
					db.getConnection().rollback();
					return false;
				}
				
				query =	"insert ERP_NHAPKHO(ngaynhapkho, ngaychot, SOPHIEUNHAPHANG, NOIDUNGNHAP, KHONHAP, TRANGTHAI, ngaytao, ngaysua, nguoitao, nguoisua,GhiChu) " +
						"values('" + this.ngaynhapkho + "', '" + this.ngaynhapkho + "', '" + sonhanhang + "', '" + this.ndnId + "', '" + this.khoId + "', " +
							"'0', '" + ngaytao + "', '" + ngaytao + "', '" + this.userId + "', '" + this.userId + "',N'"+this.ghichu+"')";
					
			}
		
			if (!db.update(query))
			{
				this.msg = "Khong the tao moi ERP_NHAPKHO: " + query;
				db.getConnection().rollback();
				return false;
			}

			String nkCurrent = "";
			query = "select IDENT_CURRENT('Erp_NHAPKHO') as nkId";

			ResultSet rsDmh = db.get(query);
			if (rsDmh.next())
			{
				nkCurrent = rsDmh.getString("nkId");
				rsDmh.close();
			}

			if (this.spList.size() > 0)
			{
				for (int i = 0; i < this.spList.size(); i++)
				{
					ISanpham sp = this.spList.get(i);
					
					if(sp.getSolo().trim().length() <= 0)
					{
						this.msg = "Vui lòng kiểm tra lại sản phẩm " + sp.getMa() + " -- Chưa nhập số lô.";
						db.getConnection().rollback();
						return false;
					}
					
					//
					String ngaysanxuat = "";
					String ngayhethan = "";
					if(this.ndnId.equals("100004"))
					{
						//kiem tra solo tra ve co hop le khong
						query = "select ngaysanxuat, ngayhethan from erp_nhapkho_sanpham " +
								"where solo = '" + sp.getSolo() + "' and sanpham_fk = ( select pk_seq from ERP_SanPham where ma = '" + sp.getMa() + "' ) " +
								"order by ngayhethan desc";
						
						System.out.println("111.Check SOLO: " + query);
						ResultSet rsCheck = db.get(query);
						if(rsCheck.next())
						{
							ngaysanxuat = rsCheck.getString("ngaysanxuat");
							ngayhethan = rsCheck.getString("ngayhethan");
						}
						rsCheck.close();
						
						if(ngaysanxuat.trim().length() <= 0 && ngayhethan.trim().length() <= 0)
						{
							this.db.getConnection().rollback();
							this.msg = "Số lô: " + sp.getSolo() + " của sản phẩm trả về: " + sp.getMa() + " không có trong kho. Vui lòng kiểm tra lại";
							return false;
						}
					}
					else
					{
						query = "select ngaysanxuat, ngayhethan  from erp_nhanhang_sp_chitiet " +
								"where nhanhang_fk = '" + sonhanhang + "' and SOLO = '" + sp.getSolo() + "' and sanpham_fk = ( select pk_seq from ERP_SanPham where ma = '" + sp.getMa() + "' )  ";
						
						ResultSet rsNsx = db.get(query);
						if(rsNsx != null)
						{
							if(rsNsx.next())
							{
								ngaysanxuat = rsNsx.getString("ngaysanxuat");
								ngayhethan = rsNsx.getString("ngayhethan");
							}
							rsNsx.close();
						}
					}
					
					query = "insert ERP_NHAPKHO_SANPHAM(SONHAPKHO_FK, SANPHAM_FK, SOLUONGNHAN, SOLUONGNHAP, SOLO, NGAYHETHAN, NGAYSANXUAT, DONGIA, THANHTIEN, DONGIAVIET, THANHTIENVIET, TIENTE_FK) " +
							"select '"+ nkCurrent+ "', pk_seq, '" + sp.getSoluongSx().replaceAll(",", "") + "', '" + sp.getSoluongnhapkho().replaceAll(",", "") + "', '" + sp.getSolo() + "', '" + ngayhethan + "', '" + ngaysanxuat + "', " +
									"" + sp.getDongia().replaceAll(",", "") + ", " + sp.getDongia().replaceAll(",", "") + "*" + sp.getSoluongnhapkho().replaceAll(",", "") + ", " + sp.getDongiaViet() + ", " + sp.getDongiaViet() + "*" + sp.getSoluongnhapkho().replaceAll(",", "") + " , '" + sp.getTiente() + "' " +
							"from ERP_SanPham where ma = '" + sp.getMa() + "'";

					if (!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_NHAPKHO_SANPHAM: " + query;
						db.getConnection().rollback();
						return false;
					} 
					else
					{
						if (this.quanlybean)
						{
							String current = "";
							query = "select IDENT_CURRENT('ERP_NHAPKHO_SANPHAM') as id_nk";

							ResultSet rsCurent = db.get(query);
							if (rsCurent.next())
							{
								current = rsCurent.getString("id_nk");
								rsCurent.close();
							}

							List<ISanphamCon> spCon = sp.getSpConList();
							for (int j = 0; j < spCon.size(); j++)
							{
								ISanphamCon detail = spCon.get(j);
								String vitri = detail.getVitri().substring(detail.getVitri().indexOf(" - ") + 3,
										detail.getVitri().length());

								query = "insert ERP_NHAPKHO_SP_CHITIET(NHAPKHO_SANPHAM_FK, SOLUONG, KHU, VITRI) "
										+ "values('" + current + "', '" + detail.getSoluong().replaceAll(",", "") + "', '"
										+ detail.getKhu() + "', '" + vitri + "')";

								if (!db.update(query))
								{
									this.msg = "Khong the tao moi ERP_NHAPKHO_SP_CHITIET: " + query;
									db.getConnection().rollback();
									return false;
								}
							}
						}
					}
				}
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);

			return true;
		} 
		catch (Exception e)
		{
			System.out.println("Exception : " + e.getMessage());
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			return false;
		}
	}


	public boolean updateNhapKho()
	{
		//Check sanpham
		for(int i = 0; i < this.spList.size(); i++)
		{
			if(this.spList.get(i).getSolo().trim().length() <= 0 || this.spList.get(i).getNgayhethan().trim().length() <= 0 )
			{
				this.msg = "Bạn phải nhập số lô và ngày hết hạn cho sản phẩm nhập kho";
				return false;
			}
		}
				
		String nam = this.ngaynhapkho.substring(0, 4);
		String thang = this.ngaynhapkho.substring(5, 7);
		
		String sql = "select count(pk_seq) as sodong from erp_khoasothang where nam = '" + nam + "' and thangks = '" + Integer.parseInt(thang) + "'";
		
		ResultSet rs  = db.get(sql);
		int count = 0;
		try
		{
			if(rs != null)
			{
				if(rs.next())
				{
					count = rs.getInt("sodong");
				}
				rs.close();
			}
		}
		catch (Exception e) {}
		
		if(count > 0)
		{
			this.msg = "Tháng " + thang + ", năm " + nam + " đã khóa sổ tháng, bạn không thể nhập kho vào ngày này, vui lòng chọn ngày nhập kho ở tháng sau.";
			return false;
		}
		boolean flag = false;
		for(int i = 0; i < this.spList.size(); i++)
		{
			ISanpham sp = this.spList.get(i);
			if(sp.getSoluongnhapkho().length() > 0)
			{
				if(Float.parseFloat(sp.getSoluongnhapkho()) > 0)
				{
					flag = true;
					break;
				}
			}
		}
		if(flag == false)
		{
			this.msg = "Không có sản phẩm nào được nhập số lượng để nhập kho, vui lòng kiểm tra lại";
			return false;
		}

		try
		{
			String ngaysua = getDateTime();

			db.getConnection().setAutoCommit(false);

			if(this.ngaychotNV.length() < 0)
				this.ngaychotNV = this.ngaynhapkho;
			
			String query = "";
				query =	"update ERP_NHAPKHO set ngaynhapkho = '" + this.ngaynhapkho + "', ngaychot = '" + this.ngaychotNV + "', SOLENHSANXUAT = '" + this.solenhsanxuat + "',CongDoan_FK='"+this.CongDoanId+"', SODONTRAHANG = null, SOPHIEUNHAPHANG = null, NOIDUNGNHAP = '" + this.ndnId + "', KHONHAP = '" + this.khoId + "', " +
					" ngaysua = '" + ngaysua + "', nguoisua = '" + this.userId + "',GhiChu=N'"+this.ghichu+"' where pk_seq = '" + this.id + "'";
			System.out.println("1.Update Nhap kho: " + query + "\n");

			if(!db.update(query))
			{
				this.msg = "4.Khong the cap nhat NHAPKHO: " + query;
				db.getConnection().rollback();
				return false;
			}
			

			query = "delete ERP_NHAPKHO_SP_CHITIET where NHAPKHO_SANPHAM_FK in ( select pk_seq from ERP_NHAPKHO_SANPHAM where SONHAPKHO_FK = '" + this.id + "' )";
			System.out.println("1.Delete: " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_NHAPKHO_SP_CHITIET: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_NHAPKHO_SANPHAM where SONHAPKHO_FK = '" + this.id + "'";
			System.out.println("2.Delete: " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_NHAPKHO_SANPHAM: " + query;
				db.getConnection().rollback();
				return false;
			}

			if (this.spList.size() > 0)
			{
				query = "select top(1) NAM as namMax, THANGKS as thangMax from ERP_KHOASOTHANG order by NAM desc, THANGKS desc ";
				System.out.println("1.Khoi tao thang: " + query);
				rs = db.get(query);
				
				String thangKsMax = "";
				String namKsMax = "";
				
				if(rs != null)
				{
					while(rs.next())
					{
						thangKsMax = rs.getString("thangMax");
						namKsMax = rs.getString("namMax"); 
						
					}
					rs.close();
				}
			for (int i = 0; i < this.spList.size(); i++)
			{
				ISanpham sp = this.spList.get(i);
				
				if(sp.getSolo().trim().length() <= 0)
				{
					this.msg = "Vui lòng kiểm tra lại sản phẩm " + sp.getMa() + " -- Chưa nhập số lô.";
					db.getConnection().rollback();
					return false;
				}
				query = "select giaton from erp_tonkhothang where thang = '" + thangKsMax + "' and nam = '" + namKsMax + "'" +
					" and sanpham_fk =  '"+sp.getId() + "' and khott_fk = '100000'";
			
				System.out.println("1__Lay gia ton: " + query);
				String giaTon = "0";
				ResultSet rsGia = db.get(query);
				if(rsGia != null)
				{
					if(rsGia.next())
					{
						giaTon = rsGia.getString("giaton");
					}
					rsGia.close();
				}
					
				query = "insert ERP_NHAPKHO_SANPHAM(SONHAPKHO_FK, SANPHAM_FK, SOLUONGNHAN, SOLUONGNHAP, SOLO, NGAYSANXUAT, NGAYHETHAN,NgayNhapKho,DONGIA, THANHTIEN, DONGIAVIET, THANHTIENVIET, TIENTE_FK) " +
					" select '" + this.id + "', '"+sp.getId()+"', '" + sp.getSoluongSx().replaceAll(",", "") + "', '" + sp.getSoluongnhapkho().replaceAll(",", "") + "', '" + sp.getSolo() + "', '" + sp.getNgaySanXuat() + "', convert (varchar(10), DATEADD(day, hansudung, '" + sp.getNgaySanXuat() + "'), 121),'"+sp.getNgayNhapKho()+"', " + giaTon + ", " + giaTon + "*" + sp.getSoluongnhapkho().replaceAll(",", "") + ", " +
						giaTon + ", " + giaTon + "*" + sp.getSoluongnhapkho() + " , '" + sp.getTiente() + "' " +
						"from erp_sanpham where pk_seq='"+sp.getId()+"'" ;
					if (!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_NHAPKHO_SANPHAM: " + query;
						db.getConnection().rollback();
						return false;
					} 
					else
					{
						if (this.quanlybean)
						{
							String current = "";
							query = "select IDENT_CURRENT('ERP_NHAPKHO_SANPHAM') as id_nk";

							ResultSet rsCurent = db.get(query);
							if (rsCurent.next())
							{
								current = rsCurent.getString("id_nk");
								rsCurent.close();
							}
								
							List<ISanphamCon> spCon = sp.getSpConList();
							for (int j = 0; j < spCon.size(); j++)
							{
								ISanphamCon detail = spCon.get(j);
								String vitri = detail.getVitri().substring(detail.getVitri().indexOf(" - ") + 3,
										detail.getVitri().length());

								query = " insert ERP_NHAPKHO_SP_CHITIET(NHAPKHO_SANPHAM_FK, SOLUONG, KHU, VITRI) "
										+ " values('" + current + "', '" + detail.getSoluong().replaceAll(",", "") + "', '"
										+ detail.getKhu() + "', '" + vitri + "')";

								if (!db.update(query))
								{
									this.msg = "Khong the tao moi ERP_NHAPKHO_SP_CHITIET: " + query;
									db.getConnection().rollback();
									return false;
								}
							}
						}
					}
				}
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		} 
		catch (Exception e)
		{
			 db.update("rollback");
			 
			
			System.out.println("Exception : " + e.getMessage());
			e.printStackTrace();
			return false;
		}
		
	}

	public String chotNhapKho(String userId)
	{
		try 
		{
			String ngaysua = getDateTime();
			
			//Check khoa so thang
			String sql = "select ngaychot from erp_nhapkho where pk_seq = '" + this.id + "'";
			ResultSet rs = db.get(sql);
			if(rs.next())
			{
				this.ngaychotNV = rs.getString("ngaychot");
				rs.close();
			}
			
			String nam = this.ngaychotNV.substring(0, 4);
			String thang = this.ngaychotNV.substring(5, 7);
			
			sql = "select count(pk_seq) as sodong from erp_khoasothang where nam = '" + nam + "' and thangks = '" + Integer.parseInt(thang) + "'";
			System.out.println("___Cau lenh check: " + sql);
			
			rs  = db.get(sql);
			int count = 0;
			if(rs != null)
			{
				if(rs.next())
				{
					count = rs.getInt("sodong");
				}
				rs.close();
			}
			
			if(count > 0)
			{
				this.msg = "Tháng " + thang + ", năm " + nam + " đã khóa sổ tháng, bạn không thể chốt phiếu nhập này, vui lòng chọn ngày chốt nghiệp vụ ở tháng sau.";
				return this.msg;
			}
			
			
			db.getConnection().setAutoCommit(false);
			
			String query = "select a.KHONHAP, a.SOPHIEUNHAPHANG, a.SODONTRAHANG, a.SOLENHSANXUAT, a.NGAYNHAPKHO, a.NGAYCHOT, a.NOIDUNGNHAP, a.pk_seq as ctnhapkho, " +
									"b.SANPHAM_FK, b.SOLUONGNHAP, b.DONGIA, b.DONGIAVIET, b.TienTe_FK, isnull(b.SOLO, 0) as solo, d.QUANLYLO, d.LOAISANPHAM_FK, d.nguongoc " +
							"from ERP_NHAPKHO a inner join ERP_NHAPKHO_SANPHAM b on a.PK_SEQ = b.SONHAPKHO_FK " +
									"inner join ERP_KHOTT c on a.khonhap = c.PK_SEQ " +
									"inner join ERP_SANPHAM d on b.SANPHAM_FK = d.PK_SEQ " +
							"where a.PK_SEQ = '" + this.id + "'";
			
			System.out.println("1.Query chot nhap kho init: " + query);
			
			rs = db.get(query);
			if(rs != null)
			{
				while(rs.next())
				{
					String ctnhapkho = rs.getString("ctnhapkho"); 
					String loaisanpham = rs.getString("LOAISANPHAM_FK");
					String sodontrahang = rs.getString("SODONTRAHANG");
					
					String noidungnhap = rs.getString("NOIDUNGNHAP");
					String masanpham = rs.getString("SANPHAM_FK");
					String khonhap = rs.getString("KHONHAP");
					
					double dongia = rs.getDouble("DONGIA");
					double dongiaViet = rs.getDouble("DONGIAVIET");
					String tiente_fk = rs.getString("TienTe_FK");
					
					float soluong =  rs.getFloat("SOLUONGNHAP");
					
					String nguongoc = rs.getString("nguongoc");
					
					//Luu lai gia ton cua SP truoc thoi diem cap nhat -- > sau nay tinh lai neu co chiet khau
					//Phai luu lai don gia xuat ngay tai thoi diem nay, de sau nay chay lai co can cu de cap nhat lai gia
					query = "update ERP_NHAPKHO_SANPHAM set DONGIA_SAUCK = DonGiaViet, " +
								"DONGIA_TONTRUOC = ( select GiaTon from ERP_KhoTT_SanPham where sanpham_fk = '" + masanpham + "' and khott_fk = '" + khonhap + "' ), " +
								"SOLUONG_TRONGKHO_TRUOCCHOT = ( select SoLuong from ERP_KhoTT_SanPham where sanpham_fk = '" + masanpham + "' and khott_fk = '" + khonhap + "' ) " +
							"where SONHAPKHO_FK = '" + this.id + "' and SANPHAM_FK = '" + masanpham + "' ";
					
					if(!db.update(query))
					{
						this.msg = "4.Khong the cap nhat ERP_NHAPKHO_SANPHAM: " + query;
						System.out.println(msg);
						db.getConnection().rollback();
						return this.msg;
					}
					
					if(nguongoc.equals("1"))  //San pham mua ngoai --> Binh quan thoi diem ( Cap nhat gia ton )
					{
						query = "update ERP_KHOTT_SANPHAM set soluong = soluong + " + soluong + ", available = available + " + soluong + ", " +
														"GIATON = (	" + dongiaViet + " * " + soluong + " + giaton * soluong) / (" + soluong + " + SOLUONG) , " +
														"THANHTIEN = (soluong + " + soluong + ") * ( " + dongiaViet + " * " + soluong + " + giaton * soluong) / ( " + soluong + " + SOLUONG ) " +
								" where khott_fk = '" + khonhap + "' and sanpham_fk = '" + masanpham + "'";
					}
					else  // san pham tu san xuat --> Binh quan cuoi ky
					{
						query = "update ERP_KHOTT_SANPHAM set soluong = soluong + " + soluong + ", available = available + " + soluong + ", " +
														"THANHTIEN = (soluong + " + soluong + ") * GiaTon " +
								" where khott_fk = '" + khonhap + "' and sanpham_fk = '" + masanpham + "'";
					}
					
					
					System.out.println("3.Query update kho, gia ton: " + query);
					
					if(!db.update(query))
					{
						this.msg = "Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;
						System.out.println(msg);
						rs.close();
						db.getConnection().rollback();
						return this.msg;
					}
					
					//Cac kho khac gia ton cung bang kho nhap ( GiaTon khong phan biet theo kho, cac chi phi khac tinh vao chi phi ban hang )
					query = " update ERP_KHOTT_SANPHAM " +
							" set GIATON = ( select GiaTon from ERP_KHOTT_SANPHAM where khott_fk = '" + khonhap + "' and sanpham_fk = '" + masanpham + "' ), THANHTIEN = SOLUONG * GIATON " +
							" where SANPHAM_FK = '" + masanpham + "'";
					
					if(!db.update(query))
					{
						this.msg = "Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;
						System.out.println(msg);
						rs.close();
						db.getConnection().rollback();
						return this.msg;
					}
					
					String taikhoanktCo = "";
					String taikhoanktNo = "";
					
					query = "select TAIKHOANKTCO, TAIKHOANKTNO " +
							"from ERP_CAUHINHDINHKHOANKETOAN  " +
							"where NOIDUNGNHAP_FK = '" + noidungnhap + "' and LOAISANPHAM_FK = '" + loaisanpham + "' ";
					
					System.out.println("5.Query lay tai khoan: " + query);
					
					ResultSet tkRs = db.get(query);
					if(tkRs != null)
					{
						if(tkRs.next())
						{
							taikhoanktCo = tkRs.getString("TAIKHOANKTCO");
							taikhoanktNo = tkRs.getString("TAIKHOANKTNO");
							tkRs.close();
						}
						
						if(taikhoanktCo.trim().length() <= 0 || taikhoanktNo.trim().length() <= 0)
						{
							this.msg = "Loại sản phẩm và nội dung nhập tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
							rs.close();
							db.getConnection().rollback();
							return this.msg;
						}
						
						//Kiem tra xem da cao tai khoan nay trong thang chua
						query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
								"where taikhoankt_fk = '" + taikhoanktNo + "' and nguyente_fk = '" + tiente_fk + "' and thang = '" + thang + "' and nam = '" + nam + "'";
						
						ResultSet rsTKNo = db.get(query);
						int sodong = 0;
						if(rsTKNo.next())
						{
							sodong = rsTKNo.getInt("sodong");
						}
						rsTKNo.close();
						
						
						if(sodong > 0) //daco
						{
							query = "update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND + " + dongiaViet + "*" + soluong + ", " +
																" GIATRINGUYENTE = GIATRINGUYENTE + "  + dongia + "*" + soluong + 
									" where taikhoankt_fk = '" + taikhoanktNo + "' and nguyente_fk = '" + tiente_fk + "' and thang = '" + thang + "' and nam = '" + nam + "'";
						}
						else
						{
							query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, THANG, NAM) " +
									"values('" + taikhoanktNo + "', '0', " + dongiaViet + "*" + soluong + ", '" + tiente_fk + "', " + dongia + "*" + soluong + ", '" + thang + "', '" + nam + "')";
						}
						
						//System.out.println("5.Cap nhat: + query");
						if(!db.update(query))
						{
							rs.close();
							db.getConnection().rollback();
							
							System.out.println("1.Loi: " + query);
							return "6.Không thể cập nhật ERP_TAIKHOAN_NOCO: " + query;
						}
						
						
						//Tai khoan co
						query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO  " +
								"where taikhoankt_fk = '" + taikhoanktCo + "' and nguyente_fk = '" + tiente_fk + "' and thang = '" + thang + "' and nam = '" + nam + "'";
						
						rsTKNo = db.get(query);
						
						sodong = 0;
						if(rsTKNo.next())
						{
							sodong = rsTKNo.getInt("sodong");
						}
						rsTKNo.close();
						
						if(sodong > 0) //daco
						{
							query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND + " + dongiaViet + "*" + soluong + ", " +
															" GIATRINGUYENTE = GIATRINGUYENTE + "  + dongia + "*" + soluong + 
									" where taikhoankt_fk = '" + taikhoanktCo + "' and nguyente_fk = '" + tiente_fk + "' and thang = '" + thang + "' and nam = '" + nam + "'";
						}
						else
						{
							query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, THANG, NAM) " +
									"select '" + taikhoanktCo + "', " + dongiaViet + "*" + soluong + ", '0', '" + tiente_fk + "', " + dongia + "*" + soluong + ", '" + thang + "', '" + nam + "' ";
						}
						
								
						if(!db.update(query))
						{
							rs.close();
							db.getConnection().rollback();
							
							System.out.println("2.Loi: " + query);
							return "7.Không thể cập nhật ERP_TAIKHOAN_NOCO: " + query;
						}
						
					}
					query =
						"SELECT SP.SANPHAM_FK,SP.SOLO,SP.NGAYHETHAN,SP.NGAYNHAPKHO,100000 AS VITRI,SP.SOLUONGNHAP AS SOLUONG,SP.DONGIA,SP.THANHTIEN,SP.NGAYSANXUAT,SP.NGAYNHAPKHO "+
						"FROM ERP_NHAPKHO_SANPHAM SP "+
						"	INNER JOIN ERP_NHAPKHO NK ON NK.PK_SEQ=SP.SONHAPKHO_FK "+
						" WHERE NK.PK_sEQ='" + ctnhapkho + "'";
						System.out.println("Nhap kho chi tiet: " + query);
						ResultSet beanRs = db.get(query);
						if(beanRs != null)
						{
							while(beanRs.next())
							{
								String spId = beanRs.getString("SANPHAM_FK");
								String solo = beanRs.getString("SOLO");
								String ngaynhapkho=beanRs.getString("NgayNhapKho");
								String ngaysanxuat = beanRs.getString("NGAYSANXUAT");
								String ngayhethan = beanRs.getString("NGAYHETHAN");
								String soluongct = beanRs.getString("SOLUONG");
								String vitri = beanRs.getString("VITRI");
								query = "select count(*) as sodong from ERP_KHOTT_SP_CHITIET where khott_fk = '" + khonhap + "' and sanpham_fk = '" + spId + "' and solo = '" + solo + "' and bin = '" + vitri + "'";
								ResultSet rsCheck = db.get(query);
								boolean flag = true;
								if(rsCheck != null)
								{
									if(rsCheck.next())
									{
										if(rsCheck.getString("sodong").equals("0"))
											flag = false;
										rsCheck.close();
									}
								}
								
								if (flag) // da ton tau, cap nhat booked, avail
								{
									query = "update ERP_KHOTT_SP_CHITIET set soluong = soluong + '" + soluongct
											+ "', AVAILABLE = AVAILABLE + '" + soluongct + "', NGAYSANXUAT = '" + ngaysanxuat + "', NGAYHETHAN = '" + ngayhethan + "', NGAYNHAPKHO = '" + this.ngaychotNV + "' where KHOTT_FK = '"
											+ khonhap + "' and SANPHAM_FK = '" + spId + "' and SOLO = '" + solo
											+ "' and BIN = '" + vitri + "'";
								} 
								else
								{
									query = "insert ERP_KHOTT_SP_CHITIET(KHOTT_FK, SANPHAM_FK, SOLUONG, BOOKED, AVAILABLE, SOLO, NGAYSANXUAT, NGAYHETHAN, NGAYNHAPKHO, BIN) "
											+ "values('" + khonhap + "', '" + spId + "', '" + soluongct + "', '0', '" + soluongct + "', '" + solo + "', '" + ngaysanxuat + "', '" + ngayhethan + "', '" + ngaynhapkho + "', '" + vitri + "')";
								}
								
								System.out.println("8.Cap nhat kho CHITIET: " + query);
								
								if(!db.update(query))
								{
									this.msg = "8.Khong the cap nhat ERP_KHOTT_SP_CHITIET: " + query;
									System.out.println(msg);
									db.getConnection().rollback();
									return this.msg;
								}
								
								
							}
							beanRs.close();
						}
					}
										
				}
				rs.close();
			query = "update ERP_NHAPKHO set trangthai = '1', giochot = '" + getTime() + "',  ngaysua = '" + ngaysua + "', nguoisua = '" + userId + "' where pk_seq = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_NHAPKHO: " + query;
				db.getConnection().rollback();
				return this.msg;
			}
			query=
			"INSERT INTO ERP_YeuCauKiemDinh(LENHSANXUAT_FK,CONGDOAN_FK,NHAPKHO_FK,SANPHAM_FK,SOLO,NGAYHETHAN,TRANGTHAI,NGUOITAO,NGAYTAO,NGUOISUA,NGAYSUA,SOLUONG,SOLUONGDAT,NGAYSANXUAT,NGAYKIEM,CONGTY_FK,DINHLUONG,DINHTINH) "+
			"	SELECT NK.SOLENHSANXUAT,NK.CONGDOAN_FK,NK.PK_SEQ AS NHAPKHO_FK,SP.SANPHAM_FK,SP.SOLO,SP.NGAYHETHAN,0 AS TRANGTHAI,'"+this.userId+"' AS NGUOITAO,'"+getDateTime()+"','"+this.userId+"' AS NGUOISUA,'"+getDateTime()+"',SP.SOLUONGNHAP AS SOLUONG,SP.SOLUONG,SP.NGAYSANXUAT,'"+getDateTime()+"' AS NGAYKIEM ,'"+this.congtyId+"',ERP_SANPHAM.KIEMTRADINHLUONG,ERP_SANPHAM.KIEMTRADINHTINH"+
				"FROM ERP_NHAPKHO NK INNER JOIN ERP_NHAPKHO_SANPHAM SP INNER JOIN ERP_SANPHAM ON ERP_SANPHAM.PK_SEQ=SP.SANPHAM_FK "+
			"	ON NK.PK_SEQ=SP.SONHAPKHO_FK "+ 
			"	WHERE ERP_SANPHAM.KIEMTRADINHLUONG=1 OR ERP_SANPHAM.KIEMTRADINHTINH=1 AND NK.PK_SEQ='"+this.id+"'";
			System.out.print("Tao phieu kiem dinh_____"+query);
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_YeuCauKiemDinh: " + query;
				db.getConnection().rollback();
				return this.msg;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			return this.msg;
		} 
		catch (Exception e) 
		{ 
			this.msg = "Exception : " + e.getMessage();
			e.printStackTrace();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {} 
			
			return this.msg;
		}
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	private String getSoLoTuDong()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-hhmm");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	private String getTime()
	{
		Date date = new Date();
	    SimpleDateFormat simpDate;

	    //format 24h
	    simpDate = new SimpleDateFormat("kk:mm:ss");
	    
	    return simpDate.format(date);
	}

	public void DBclose()
	{
		try{
		
			
			if(ndnRs!=null){
				ndnRs.close();
			}
			if(lhhRs!=null){
				lhhRs.close();
			}
			
			if(vtlkRs!=null){
				vtlkRs.close();
			}
			if(khoRs!=null){
				khoRs.close();
			}
			
			if(spList!=null){
				spList.clear();
			}
			if(khuList!=null){
				khuList.clear();
			}
			if(vitriList!=null){
				vitriList.clear();
			}
			
			db.shutDown();
		}catch(Exception er){
			
		}
	}

	public String getNguoigiaohang()
	{
		return this.nguoinhanhang;
	}

	public void setNguoinhanhang(String nguoinhanhang)
	{
		this.nguoinhanhang = nguoinhanhang;
	}

	public String getDiachi()
	{
		return this.diachinhan;
	}

	public void setDiachi(String diachi)
	{
		this.diachinhan = diachi;
	}

	public String getNhaptaikho()
	{
		return this.nhaptaikho;
	}

	public void setNhaptaikho(String nhaptaikho)
	{
		this.nhaptaikho = nhaptaikho;
	}

	public String getGhichu()
	{
		return this.ghichu;
	}

	public void setGhichu(String ghichu)
	{
		this.ghichu = ghichu;
	}

	public String getNgaychotNV() 
	{
		return this.ngaychotNV;
	}

	public void setNgaychotNV(String ngaychotNV) 
	{
		this.ngaychotNV = ngaychotNV;
	}

	public String getSoDontrahang()
	{
		return this.sodontrahang;
	}

	public void setSoDontrahang(String soDth)
	{
		this.sodontrahang = soDth;
	}

	public String getSoLenhsx() 
	{
		return this.solenhsanxuat;
	}

	public void setSoLenhsx(String soLenhsx)
	{
		this.solenhsanxuat = soLenhsx;
	}


	public boolean createNhapKhoLSX() 
	{
 
		boolean flag = false;
		for(int i = 0; i < this.spList.size(); i++)
		{
			ISanpham sp = this.spList.get(i);
			if(sp.getSoluongnhapkho().length() > 0)
			{
				if(Float.parseFloat(sp.getSoluongnhapkho()) > 0)
				{
					flag = true;
					break;
				}
			}
		}
		if(this.spList.size()==0){
			flag= false;
		}
		if(flag == false)
		{
			this.msg = "Không có sản phẩm nào được nhập số lượng để nhập kho, vui lòng kiểm tra lại";
			return false;
		}
 
		for(int i = 0; i < this.spList.size(); i++)
		{
			if(this.spList.get(i).getSolo().trim().length() <= 0 || this.spList.get(i).getNgayhethan().trim().length() <= 0 )
			{
				this.msg = "Bạn phải nhập số lô và ngày hết hạn cho sản phẩm nhập kho";
				return false;
			}
		}
		
 
		String nam = this.ngaynhapkho.substring(0, 4);
		String thang = this.ngaynhapkho.substring(5, 7);
 
		try 
		{
			db.getConnection().setAutoCommit(false);	
			String query =	" insert ERP_NHAPKHO (ngaynhapkho, ngaychot, SOLENHSANXUAT,CongDoan_FK, NOIDUNGNHAP, KHONHAP, TRANGTHAI, ngaytao, ngaysua, nguoitao, nguoisua, congty_fk ) " +
							" values('" + this.ngaynhapkho + "', '" + this.ngaynhapkho + "', '" + this.solenhsanxuat + "', '"+this.CongDoanId+"','" + this.ndnId + "','" + this.khoId + "', " +
							" '0', '" + getDateTime() + "', '" + getDateTime() + "', '" + this.userId + "', '" + this.userId + "', '" + this.congtyId + "')";
			
			if(!db.update(query))
			{
				this.msg = "1.Khong the tao moi ERP_NHAPKHO: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "select IDENT_CURRENT('Erp_NHAPKHO') as nkId";
			ResultSet rsDmh = db.get(query);
			if (rsDmh.next())
			{
				this.id = rsDmh.getString("nkId");
				rsDmh.close();
			}
			
			query=" update erp_lenhsanxuat_giay set trangthai=4 where trangthai < 4 and pk_seq= "+this.solenhsanxuat;
			if(!db.update(query))
			{
				this.msg = "1.Khong the cập nhật erp_lenhsanxuat_giay: " + query;
				db.getConnection().rollback();
				return false;
			}
	 
			query = " select top(1) NAM as namMax, THANGKS as thangMax from ERP_KHOASOTHANG order by NAM desc, THANGKS desc ";
			ResultSet  rs = db.get(query);
			String thangKsMax = "";
			String namKsMax = "";
			if(rs != null)
			{
				while(rs.next())
				{
					thangKsMax = rs.getString("thangMax");
					namKsMax = rs.getString("namMax"); 
					
				}
				rs.close();
			}
			
			if (this.spList.size() > 0)
			{
				for (int i = 0; i < this.spList.size(); i++)
				{
					ISanpham sp = this.spList.get(i);
					
					query =		 " select giaton from erp_tonkhothang where thang = '" + thangKsMax + "' and nam = '" + namKsMax + "'" +
								 " and sanpham_fk =  '"+sp.getId() + "' and khott_fk = '100000'";
					
			
					String giaTon = "0";
					ResultSet rsGia = db.get(query);
					if(rsGia != null)
					{
						if(rsGia.next())
						{
							giaTon = rsGia.getString("giaton");
						}
						rsGia.close();
					}
					if(Double.parseDouble(sp.getSoluongnhapkho()) >0){
						query = "insert ERP_NHAPKHO_SANPHAM(SONHAPKHO_FK, SANPHAM_FK, SOLUONGNHAN, SOLUONGNHAP, SOLO, NGAYSANXUAT, NGAYHETHAN,NgayNhapKho,DONGIA, THANHTIEN, DONGIAVIET, THANHTIENVIET, TIENTE_FK) " +
								" select '" + this.id + "', '"+sp.getId()+"', '" + sp.getSoluongSx().replaceAll(",", "") + "', '" + sp.getSoluongnhapkho().replaceAll(",", "") + "', '" + sp.getSolo() + "', '" + sp.getNgaySanXuat() + "', convert (varchar(10), DATEADD(day, hansudung, '" + sp.getNgaySanXuat() + "'), 121),'"+sp.getNgayNhapKho()+"', " + giaTon + ", " + giaTon + "*" + sp.getSoluongnhapkho().replaceAll(",", "") + ", " +
									giaTon + ", " + giaTon + "*" + sp.getSoluongnhapkho() + " , '" + sp.getTiente() + "' " +
									"from erp_sanpham where pk_seq='"+sp.getId()+"'" ;
 
						if (!db.update(query))
						{
							this.msg = "2.Khong the tao moi ERP_NHAPKHO_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						} 
					}
				}
			}
			String Idnhapkhothanhpham=this.id;
			
			if(chotNhapKhoLSX().trim().length()>0)
			{
				return false;
			}
			
			if(!TaoNhapKhoBanThanhPham()){
				return false;
			}
			
			if(!Idnhapkhothanhpham.equals(this.id)){
				
				
				if(chotNhapKhoLSX().trim().length()>0)
				{
					return false;
				}
				
				if(!this.TieuHaoTuDongBanThanhPham(Idnhapkhothanhpham)){
					return false;
				}
			}
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			this.msg=e.toString();
			db.update("rollback");
			System.out.println("1.Exception: " + e.getMessage());
		}
		
		
		return true;
	}

	

	private boolean TieuHaoTuDongBanThanhPham(String idnhapkhotp) {
		// TODO Auto-generated method stub
		try{

			String khoTieuhao_fk = "";
			String chungtutieuhaoid ="";
 
			String query=    "    select nk.ngaynhapkho,nk.ngaychot,nk.giochot,nk.solenhsanxuat,nk.noidungnhap,nk.khonhap ,  "  +  
					       " 	nk.congty_fk,nk.congdoan_fk,  a.SANPHAM_FK, a.SOLO, a.SOLUONGNHAP, a.NGAYSANXUAT,TIENTE_FK , "  +  
						   "    a.NGAYHETHAN, a.SOLUONGNHAN AS SOLUONG, "  +  
						   "    100000 AS VITRI  from ERP_NHAPKHO_SANPHAM a   "  +  
						   "    inner join erp_nhapkho nk on nk.pk_seq=a.sonhapkho_fk "  +  
						   "    inner join erp_sanpham sp on sp.pk_seq= a.SANPHAM_FK "  +  
						   "    where a.SONHAPKHO_FK =  "  +idnhapkhotp  +
						   "    and sp.loaisanpham_fk=100005  ";
			ResultSet rsphieuxk=db.getScrol(query);
			
			if(rsphieuxk.next()){
			
				String ngaynhapkho=rsphieuxk.getString("ngaynhapkho");
				String congdoan=rsphieuxk.getString("congdoan_fk");
				String lenhsanxuat=rsphieuxk.getString("solenhsanxuat");
				query = " select ERP_KHOTT.pk_seq from ERP_KHOTT inner join erp_lenhsanxuat_giay lsx on lsx.congty_fk=ERP_KHOTT.congty_fk and lsx.nhamay_fk=erp_khott.nhamay_fk " +
					"  where ERP_KHOTT.TrangThai = '1' and ERP_KHOTT.LOAI = '1' and lsx.congty_fk = '"+this.congtyId+"' and lsx.pk_seq="+lenhsanxuat ;
				
					ResultSet rsKho = db.get(query);
			
					if(rsKho.next())
					{
						khoTieuhao_fk = rsKho.getString("pk_seq");
					}
					rsKho.close();
				
					query=" INSERT INTO ERP_TIEUHAONGUYENLIEU (LENHSANXUAT_FK,CONGDOAN_FK,TRANGTHAI,NGUOITAO,NGAYTAO,NGUOISUA,NGAYSUA,NGAYTIEUHAO) "+
						" VALUES ("+lenhsanxuat+","+congdoan+",1,"+this.userId+",'"+this.getDateTime()+"',"+this.userId+",'"+this.getDateTime()+"','"+ngaynhapkho+"')"; 
						//System.out.println("1.Insert ERP_LENHSANXUAT_TIEUHAO: " + sql);
					if(!db.update(query))
					{
						this.msg = "1.Khong the cap nhat ERP_LENHSANXUAT_TIEUHAO: " +query;
						db.update("rollback");
						return false;
					}
					query = "select IDENT_CURRENT('ERP_TIEUHAONGUYENLIEU') as clId";
						ResultSet rs = this.db.get(query);
						rs.next();
						chungtutieuhaoid = rs.getString("clId");
						rs.close();
			}else{
				this.msg = "Không Xác định được nhập kho bán thành phẩm : " +query;
				db.update("rollback");
				return false;
			}
			
			query=" insert into ERP_LSXTIEUHAO_NHAPKHO (TIEUHAO_FK,NHAPKHO_FK) values ("+chungtutieuhaoid+","+idnhapkhotp+")";
			
			if(!db.update(query))
			{
				this.msg = "1.Khong the cap nhat ERP_LSXTIEUHAO_NHAPKHO: " +query;
				db.update("rollback");
				return false;
			}
			rsphieuxk.beforeFirst();
			while (rsphieuxk.next()){
				String sanphamid=rsphieuxk.getString("SANPHAM_FK");
 
		     query=" select dmvt.vattu_fk,danhmuc.soluongchuan /(  "  +  
				   " SELECT TOP 1 CASE WHEN SP.DVDL_FK=100003 THEN 1 ELSE   "  +  
				   "  ISNULL(QC.SOLUONG2/ CAST(QC.SOLUONG1 AS FLOAT),0) END  "  +  
				   " FROM ERP_SANPHAM SP LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK=SP.PK_SEQ   "  +  
				   " AND QC.DVDL1_FK=SP.DVDL_FK AND QC.DVDL2_FK=100003 WHERE SP.PK_SEQ="  + sanphamid+ 
				   " ) as soluongchuan ,  "  + 
				   " dmvt.soluong / ( "  +  
				   " SELECT TOP 1 CASE WHEN SP.DVDL_FK=100003 THEN 1 ELSE   "  +  
				   "  ISNULL(QC.SOLUONG2/ CAST(QC.SOLUONG1 AS FLOAT),0) END  "  +  
				   " FROM ERP_SANPHAM SP LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK=SP.PK_SEQ   "  +  
				   " AND QC.DVDL1_FK=SP.DVDL_FK AND QC.DVDL2_FK=100003 WHERE SP.PK_SEQ=dmvt.vattu_fk "  +  
				   "  "  +  
				   " )  as soluong,vattu_fk from erp_lenhsanxuat_sanpham a "  +  
				   " inner join erp_danhmucvattu danhmuc on danhmuc.pk_seq=a.danhmucvt_fk "  +  
				   " inner join erp_danhmucvattu_vattu dmvt on dmvt.danhmucvt_fk=danhmuc.pk_seq "  +  
				   " where a.lenhsanxuat_fk="+solenhsanxuat+" and a.sanpham_fk= "+sanphamid;
			 
			ResultSet rs_getdmvt=db.get(query);
			if(rs_getdmvt.next()){
				
				
				
				String sanphambtpid=rs_getdmvt.getString("vattu_fk");
				
					query = " select giaton from erp_tonkhothang where thang = '" + ngaynhapkho.substring(5,7) + "' and nam = '" +this.ngaynhapkho.substring(0,4) + "'" +
							" and sanpham_fk =  "+ sanphambtpid;
	
					String giaTon = "0";
					ResultSet rsGia = db.get(query);
					if(rsGia != null)
					{
						if(rsGia.next())
						{
							giaTon = rsGia.getString("giaton");
						}
						rsGia.close();
					}	
				
				
					double soluongbtp= rsphieuxk.getDouble("SOLUONGNHAP")* rs_getdmvt.getDouble("soluong")/rs_getdmvt.getDouble("soluongchuan");
					
					soluongbtp=   Double.parseDouble(formatter.format(soluongbtp));
					double thanhtien=Double.parseDouble(giaTon)*soluongbtp;
					
					query = " Insert ERP_LENHSANXUAT_TIEUHAO ( TIEUHAONGUYENLIEU_FK,KHOTT_FK, sanpham_fk, soluong, dongia, thanhtien,loai) " +
					"values("+chungtutieuhaoid+","+khoTieuhao_fk+", "+sanphambtpid+", " +   formatter.format(soluongbtp) + ", " + giaTon + ", " + thanhtien + ",1)";
					
					 
					if(!db.update(query))
					{
						this.msg = "1.Khong the cap nhat ERP_LENHSANXUAT_TIEUHAO: " + query;
						db.getConnection().rollback();
						return false;
					}
					// lấy kho trong kho 
					query="select available,solo,ngayhethan from erp_khott_sp_chitiet where sanpham_fk="+sanphambtpid +" and khott_fk="+khoTieuhao_fk +" and available >0  order by ngayhethan";
					
					ResultSet rskhochitiet =db.get(query);
					
					double soluongbtp_ct=soluongbtp;
					boolean flag=true;
					double sumsoluongbtp=0;
					while (rskhochitiet.next() && flag){
						double soluongcapnhat=0;
						
						String solo=rskhochitiet.getString("solo").trim();
						String ngayhethan=rskhochitiet.getString("ngayhethan");
						if(rskhochitiet.getDouble("available")  <soluongbtp_ct ){
							soluongbtp_ct=soluongbtp_ct-rskhochitiet.getDouble("available");
							soluongcapnhat=rskhochitiet.getDouble("available");
							
						}else{
							soluongcapnhat=soluongbtp_ct;
							flag=false;
						}
						soluongcapnhat=Double.parseDouble(formatter.format(soluongcapnhat));
						// Cập nhật kho chi tiết;
						query = " Insert ERP_LENHSANXUAT_TIEUHAO_CHITIET (TIEUHAONGUYENLIEU_FK,KHOTT_FK, sanpham_fk, solo, ngayhethan, soluong,loai) " +
						" VALUES( "+chungtutieuhaoid+","+khoTieuhao_fk+", "+sanphambtpid+", '" + solo+ "', '"+ngayhethan+"', " +formatter.format(soluongcapnhat)+ " " +
						" ,'1')";
							if(!db.update(query))
							{
								this.msg = "1.Khong the cap nhat ERP_LENHSANXUAT_TIEUHAO_CHITIET: " + query;
								db.getConnection().rollback();
								return false;
							}
							query = 		" update ERP_KHOTT_SP_CHITIET set soluong = soluong - '" + formatter.format(soluongcapnhat) + "', AVAILABLE = AVAILABLE - '" + formatter.format(soluongcapnhat) + "' " +
											" where KHOTT_FK =  " +khoTieuhao_fk +
										    " and SANPHAM_FK =" +sanphambtpid+
										   	" and LTRIM( RTRIM(SOLO))= '" + solo + "'";
							if(db.updateReturnInt(query)!=1)
							{
								this.msg = "3.Khong the cap nhat ERP_KHOTT_SP_CHITIET: " + query;
								db.getConnection().rollback();
								return false;
							}
						 
						//amd
							sumsoluongbtp=sumsoluongbtp+soluongcapnhat;
					}
					System.out.println("sumsoluongbtp : " +sumsoluongbtp);
					System.out.println("soluongbtp : " +soluongbtp);
					 //Trường hợp những sản phẩm đã booked từ kho sản xuất trong lệnh sản xuất
					// thì giảm booked tới mức đã booked và giảm số lượng,sau đó trừ thẳng vào avai,số lượng
					  query="select soluong- isnull(GIAM_BOOKED,0) as soluongcongiubook   from  erp_lenhsanxuat_bom_giay where ISVATTUTHEM='1' and  vattu_fk="+sanphambtpid+" and lenhsanxuat_fk="+this.solenhsanxuat;
					  ResultSet rscheck=db.get(query);
					  double Soluongconbooked=0;
					  if(rscheck.next()){
						  Soluongconbooked=rscheck.getDouble("soluongcongiubook");
					  }
					  
					  if(Soluongconbooked >0){
						  //là số lượng nếu tiêu hao lơn hơn đã booked trong lệnh sản xuất
						  double soluongchuabooked=0;
						  double soluongdangboked=0;
						  if( soluongbtp  >Soluongconbooked ){
							  soluongchuabooked= soluongbtp -Soluongconbooked;
							  soluongdangboked=Soluongconbooked;
						  }else{
							  soluongdangboked=soluongbtp;
						  }
						  //số lượng chua booked thì trừ thẳng vào available
						  query = " update ERP_KHOTT_SANPHAM set  soluong = soluong - '" + soluongchuabooked + "', available = available - '" + soluongchuabooked + "' " +
					 	  " where KHOTT_FK = "+khoTieuhao_fk+ 
						  " and SANPHAM_FK = "+sanphambtpid;
						  if(db.updateReturnInt(query)!=1)
							{
								this.msg = "4.Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;
								db.getConnection().rollback();
								return false;
							}
						    // số lượng đã booked thì trừ vào số lượng và giảm booked
						    
						    query = " update ERP_KHOTT_SANPHAM set  soluong = soluong - '" + soluongdangboked + "', booked = booked - '" + soluongdangboked + "' " +
						 	  " where KHOTT_FK = "+khoTieuhao_fk+ 
							  " and SANPHAM_FK = "+sanphambtpid;
						    if(db.updateReturnInt(query)!=1)
								{
									this.msg = "4.Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;
									db.getConnection().rollback();
									return false;
								}
						    // tăng số lượng cột  GIAM_BOOKED trong bảng ERP_LENHSANXUAT_BOM_GIAY
							    
							    query=  " UPDATE  erp_lenhsanxuat_bom_giay  " +
							    		" SET GIAM_BOOKED=isnull(GIAM_BOOKED,0) + "+soluongdangboked+
							    		" where  ISVATTUTHEM='1' and  vattu_fk="+sanphambtpid+" and lenhsanxuat_fk="+solenhsanxuat ;
							    if(db.updateReturnInt(query)!=1)
								{
									this.msg = "4.Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;
									db.getConnection().rollback();
									return false;
								}
				  
						  
						  
					  }else{
							    //không còn booked thì trừ thẳng vào số lượng,available
								query = " update ERP_KHOTT_SANPHAM set  soluong = soluong - '" + soluongbtp + "', available = available - '" + soluongbtp + "' " +
								 	  " where KHOTT_FK = "+khoTieuhao_fk+ 
									  " and SANPHAM_FK = "+sanphambtpid;
							  	if(db.updateReturnInt(query)!=1)
								{
									this.msg = "4.Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;
									db.getConnection().rollback();
									return false;
								}
					  }
				
					  
					// Tính số lượng booked trong lệnh sản xuất 
					
				/*	query =" update ERP_KHOTT_SANPHAM set soluong = soluong - '" + formatter.format(soluongbtp) + "', AVAILABLE = AVAILABLE - '" + formatter.format(soluongbtp) + "' " +
					" where KHOTT_FK =  " +khoTieuhao_fk +
				    " and SANPHAM_FK =" +sanphambtpid ;
						
				 
					if(db.updateReturnInt(query)!=1)
					{
						this.msg = "3.Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;
						db.getConnection().rollback();
						return false;
					}*/
					
					
					
					  sumsoluongbtp=  Double.parseDouble(formatter.format(sumsoluongbtp));
					  soluongbtp=  Double.parseDouble(formatter.format(soluongbtp));
					if(sumsoluongbtp != soluongbtp){
						this.msg="Không thể cập nhật số lượng bán thành phẩm.Số lượng trong kho chi tiết không đủ để cập nhật.";
						this.db.update("rollback");
						return false;
						
					}
					
					String nam = ngaynhapkho.substring(0, 4);
					String thang = ngaynhapkho.substring(5, 7);
					
					//KE TOAN TU DONG PHAT SINH KHI TIEU HAO
					Utility util = new Utility();
					int namOLD = Integer.parseInt(nam);
					int thangOLD = Integer.parseInt(thang);
					
					if(thangOLD == 1)
					{
						thangOLD = 12;
						namOLD = namOLD - 1;
					}
					else
					{
						thangOLD = thangOLD - 1;
					}
					
					query = "select a.SANPHAM_FK, a.SOLUONG, b.DVKD_FK, b.LOAISANPHAM_FK, " +
								"( select AVG(GIATON) from ERP_TONKHOTHANG where THANG = " + thangOLD + " and NAM = " + namOLD + " and SANPHAM_FK = b.PK_SEQ ) as GiaTieuHao, " +
								"( select sohieutaikhoan from ERP_TAIKHOANKT where PK_SEQ in ( select TAIKHOANKT_FK from ERP_LOAISANPHAM where PK_SEQ = b.LOAISANPHAM_FK ) ) as TaiKhoanCO  " +
							"from ERP_LENHSANXUAT_TIEUHAO a inner join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ   " +
							"where TIEUHAONGUYENLIEU_FK = '" + chungtutieuhaoid + "'";
				
					ResultSet rsKT = db.get(query);
					
					String taikhoanktCo_SoHieu = "";
					String taikhoanktNo_SoHieu = "";
					
					if(rsKT != null)
					{
						while(rsKT.next())
						{
							String dvkd = rsKT.getString("DVKD_FK");
							System.out.println( ". CAU LENH GET DU LIEU  : "+dvkd);
							System.out.println( ". CAU LENH GET DU LIEU  : "+rsKT.getString("SANPHAM_FK"));
							String loaisp = rsKT.getString("LOAISANPHAM_FK");
							String sanpham_fk = rsKT.getString("SANPHAM_FK");
							String tiente_fk = "100000";
							double soluong = rsKT.getDouble("SOLUONG");
							double giaTonTH = rsKT.getDouble("GiaTieuHao");
							
							if(dvkd.equals("100000")) //NHOM
							{
								if(loaisp.equals("100013")) //Paper
								{
									taikhoanktNo_SoHieu = "621110";
									taikhoanktCo_SoHieu = rsKT.getString("TaiKhoanCO");
								}
								else
								{
									if(loaisp.equals("100014")) //FOIL
									{
										taikhoanktNo_SoHieu = "621120";
										taikhoanktCo_SoHieu = rsKT.getString("TaiKhoanCO");
									}
									else
									{
										if(loaisp.equals("100015")) //GLUE
										{
											taikhoanktNo_SoHieu = "621130";
											taikhoanktCo_SoHieu = rsKT.getString("TaiKhoanCO");
										}
										else
										{
											if(loaisp.equals("100016"))  //LACQUE
											{
												taikhoanktNo_SoHieu = "621140";
												taikhoanktCo_SoHieu = rsKT.getString("TaiKhoanCO");
											}
											else
											{
												if(loaisp.equals("100017")) //Sub-Materials
												{
													taikhoanktNo_SoHieu = "621150";
													taikhoanktCo_SoHieu = rsKT.getString("TaiKhoanCO");
												}
											}
										}
									}
								}
							}
							else
							{
								if(dvkd.equals("100004")) //LOI
								{
									if(loaisp.equals("100013")) //Paper
									{
										taikhoanktNo_SoHieu = "621210";
										taikhoanktCo_SoHieu = rsKT.getString("TaiKhoanCO");
									}
									else
									{
										if(loaisp.equals("100014")) //FOIL
										{
											taikhoanktNo_SoHieu = "621220";
											taikhoanktCo_SoHieu = rsKT.getString("TaiKhoanCO");
										}
										else
										{
											if(loaisp.equals("100015")) //GLUE
											{
												taikhoanktNo_SoHieu = "621230";
												taikhoanktCo_SoHieu = rsKT.getString("TaiKhoanCO");
											}
											else
											{
												if(loaisp.equals("100016"))  //LACQUE
												{
													taikhoanktNo_SoHieu = "621240";
													taikhoanktCo_SoHieu = rsKT.getString("TaiKhoanCO");
												}
												else
												{
													if(loaisp.equals("100017")) //Sub-Materials
													{
														taikhoanktNo_SoHieu = "621250";
														taikhoanktCo_SoHieu = rsKT.getString("TaiKhoanCO");
													}
												}
											}
										}
									}
								}
							}
							
							
							if(taikhoanktCo_SoHieu.trim().length() > 0 || taikhoanktNo_SoHieu.trim().length() > 0 )
							{
								double tonggiatri = soluong * giaTonTH;
								this.msg = util.Update_TaiKhoan_TheoSoHieu( db, thang, nam, ngaynhapkho, ngaynhapkho, "Tiêu hao", chungtutieuhaoid, taikhoanktNo_SoHieu, taikhoanktCo_SoHieu, "", 
												Double.toString(tonggiatri), Double.toString(tonggiatri), "Sản phẩm", sanpham_fk, "0", "", "", tiente_fk, "", "1", Double.toString(tonggiatri), Double.toString(tonggiatri), "Tiêu hao" );
								if(this.msg.trim().length() > 0)
								{
									rsKT.close();
									db.getConnection().rollback();
									return false;
								}
							}
							
							
						}
						rsKT.close();
					}
				
				}else{
					db.update("rollback");
					this.msg="Không thể xác định được BOM cho thành phẩm : " +sanphamid ;
					return false;
				}		
				
			}
			return true;
		}catch(Exception er){
			er.printStackTrace();
			db.update("rollback");
			this.msg=er.toString();
			return false;	
		}
		
		
		
	}

	private boolean TaoNhapKhoBanThanhPham() {
		// TODO Auto-generated method stub
		try{
			
			String query="  SELECT   CASE WHEN SP.DVDL_FK=100003 THEN 1 ELSE      "+ 
			 "  ISNULL(QC.SOLUONG2/ CAST(QC.SOLUONG1 AS FLOAT),0) END  as qc,SP.MA +' - '+SP.TEN  as ten  "+ 
			 "  FROM ERP_SANPHAM SP  "+ 
			 "  LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK=SP.PK_SEQ     "+ 
			 "  AND QC.DVDL1_FK=SP.DVDL_FK AND QC.DVDL2_FK=100003    "+ 
			 "  inner join ERP_NHAPKHO_SANPHAM nksp  on nksp.SANPHAM_FK=SP.PK_SEQ  "+ 
			 "  WHERE nksp.SONHAPKHO_FK =  "+this.id+  
			 "  and ( CASE WHEN SP.DVDL_FK=100003 THEN 1 ELSE      "+ 
			 "  ISNULL(QC.SOLUONG2/ CAST(QC.SOLUONG1 AS FLOAT),0) END ) =0  ";
			
			
			ResultSet rs=db.get(query);
			if(rs.next()){
				this.msg = "1.Khong the tao moi ERP_NHAPKHO.Sản phẩm/Bán thành phẩm chưa khai báo quy cách :  " + rs.getString("ten");
				rs.close();
				db.getConnection().rollback();
				return false;
			}
			rs.close();
			
			
			  query=  	   "    select nk.ngaynhapkho,nk.ngaychot,nk.giochot,nk.solenhsanxuat,nk.noidungnhap,nk.khonhap ,  "  +  
					       " 	nk.congty_fk,nk.congdoan_fk,  a.SANPHAM_FK, a.SOLO, a.SOLUONGNHAP, a.NGAYSANXUAT,TIENTE_FK , "  +  
						   "    a.NGAYHETHAN, a.SOLUONGNHAN AS SOLUONG, "  +  
						   "    100000 AS VITRI  from ERP_NHAPKHO_SANPHAM a   "  +  
						   "    inner join erp_nhapkho nk on nk.pk_seq=a.sonhapkho_fk "  +  
						   "    inner join erp_sanpham sp on sp.pk_seq= a.SANPHAM_FK "  +  
						   "    where a.SONHAPKHO_FK =  "  +this.id  +
						   "    and sp.loaisanpham_fk=100005 and sp.dvkd_fk=100000  ";
								
			  rs=db.getScrol(query);
			
			
			if(rs.next()) {
				 // lấy được công đoạn tạo ra bán thành phẩm của công đoạn này
				query=  " select congdoan_fk  from erp_lenhsanxuat_congdoan_giay  " +
						" where lenhsanxuat_fk="+this.solenhsanxuat+" and congdoan_fk  <> "+this.CongDoanId;
				
				ResultSet rs_getcongdoan =db.get(query);
				String congdoan2= "";
				
				if(rs_getcongdoan.next()){
					congdoan2=rs_getcongdoan.getString("congdoan_fk");	
				}
				rs_getcongdoan.close();
				// nếu là thành phẩm thì tạo ra nhập kho cho thành phẩm này 
				query =	" insert ERP_NHAPKHO (ngaynhapkho, ngaychot, SOLENHSANXUAT,CongDoan_FK, NOIDUNGNHAP, KHONHAP, TRANGTHAI, ngaytao, ngaysua, nguoitao, nguoisua, congty_fk ) " +
						" values('" + this.ngaynhapkho + "', '" + this.ngaynhapkho + "', '" + this.solenhsanxuat + "', '"+congdoan2+"','" + this.ndnId + "','" + this.khoId + "', " +
						" '0', '" + getDateTime() + "', '" + getDateTime() + "', '" + this.userId + "', '" + this.userId + "', '" + this.congtyId + "')";

					if(!db.update(query))
					{
						this.msg = "1.Khong the tao moi ERP_NHAPKHO: " + query;
						db.getConnection().rollback();
						return false;
					}
					
					query = "select IDENT_CURRENT('Erp_NHAPKHO') as nkId";
					ResultSet rsDmh = db.get(query);
					if (rsDmh.next())
					{
						this.id = rsDmh.getString("nkId");
						rsDmh.close();
					}
			}
			
				query = "select top(1) NAM as namMax, THANGKS as thangMax from ERP_KHOASOTHANG order by NAM desc, THANGKS desc ";
				ResultSet rsthangnam = db.get(query);
				String thangKsMax = "";
				String namKsMax = "";
				while(rsthangnam.next())
				{
					thangKsMax = rsthangnam.getString("thangMax");
					namKsMax = rsthangnam.getString("namMax"); 
					
				}
				rsthangnam.close();
		 
				rs.beforeFirst();
			
				double soluongbtp =0;
				String sanphambtpid="";
				String ngaysanxuat="";
				String Ngayhethan="";
				String tiente="";
				 while (rs.next()){
					 
						  ngaysanxuat=rs.getString("ngaysanxuat");
						  Ngayhethan=rs.getString("Ngayhethan");
						  tiente=rs.getString("TIENTE_FK");
						
						String sanphamid=rs.getString("SANPHAM_FK");
						query="  select dmvt.vattu_fk,danhmuc.soluongchuan /(  "  +  
							   " SELECT TOP 1 CASE WHEN SP.DVDL_FK=100003 THEN 1 ELSE   "  +  
							   "  ISNULL(QC.SOLUONG2/ CAST(QC.SOLUONG1 AS FLOAT),0) END  "  +  
							   " FROM ERP_SANPHAM SP LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK=SP.PK_SEQ   "  +  
							   " AND QC.DVDL1_FK=SP.DVDL_FK AND QC.DVDL2_FK=100003 WHERE SP.PK_SEQ="  + sanphamid+ 
							   " ) as soluongchuan ,  "  + 
							   " dmvt.soluong / ( "  +  
							   " SELECT TOP 1 CASE WHEN SP.DVDL_FK=100003 THEN 1 ELSE   "  +  
							   "  ISNULL(QC.SOLUONG2/ CAST(QC.SOLUONG1 AS FLOAT),0) END  "  +  
							   " FROM ERP_SANPHAM SP LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK=SP.PK_SEQ   "  +  
							   " AND QC.DVDL1_FK=SP.DVDL_FK AND QC.DVDL2_FK=100003 WHERE SP.PK_SEQ=dmvt.vattu_fk "  +  
							   "  "  +  
							   " )  as soluong,vattu_fk from erp_lenhsanxuat_sanpham a "  +  
							   " inner join erp_danhmucvattu danhmuc on danhmuc.pk_seq=a.danhmucvt_fk "  +  
							   " inner join erp_danhmucvattu_vattu dmvt on dmvt.danhmucvt_fk=danhmuc.pk_seq "  +  
							   " where a.lenhsanxuat_fk="+solenhsanxuat+" and a.sanpham_fk= "+sanphamid;
							System.out.println("Lấy sản phẩm sản xuất : "+query);
							ResultSet rs_getdmvt=db.get(query);
							if(rs_getdmvt.next()){
								sanphambtpid=rs_getdmvt.getString("vattu_fk");
							  	soluongbtp= soluongbtp+  rs.getDouble("SOLUONGNHAP")* rs_getdmvt.getDouble("soluong")/rs_getdmvt.getDouble("soluongchuan");
							 
							}else{
								db.update("rollback");
								this.msg="Không thể xác định được BOM cho thành phẩm : " +rs.getString("sanpham_fk") ;
								return false;
							}		
						
				 }
			if(sanphambtpid.length() >0){	 
				 
				 
			query = "select giaton from erp_tonkhothang where thang = '" + thangKsMax + "' and nam = '" + namKsMax + "'" +
			" and sanpham_fk =  "+ sanphambtpid;
			//System.out.println("1__Lay gia ton: " + query);
			String giaTon = "0";
			ResultSet rsGia = db.get(query);
			if(rsGia != null)
			{
				if(rsGia.next())
				{
					giaTon = rsGia.getString("giaton");
				}
				rsGia.close();
			}
			soluongbtp=   Double.parseDouble(formatter.format(soluongbtp));
			 
			//query=" SELECT AVAILABLE FROM ERP_KHOTT_SANPHAM WHERE khott_fk="+this.khoId+"  and SANPHAM_FK= "+sanphambtpid;
			// lấy số lượng yêu cầu trong BOM
			//1. Không yêu cầu thì tạo ra số lượng bán thành phẩm đủ để tiêu hao.	
		    //2. Có yêu cầu BTP.Kho có đủ :
				query=" select SOLUONG  from ERP_LENHSANXUAT_BOM_GIAY where LENHSANXUAT_FK="+this.solenhsanxuat+" and VATTU_FK="+sanphambtpid+" and ISVATTUTHEM='1'";
				
			//System.out.println("Du Lieu Trong KHo : "+query);
				
				ResultSet rsbtpyeucau =db.get(query);
				double soluongyeucau=0;
				if( rsbtpyeucau.next()){
					soluongyeucau =rsbtpyeucau.getDouble("SOLUONG");
				} 
			    double soluongbtp_them=0;
				soluongbtp_them=soluongbtp-soluongyeucau ;
				 if(soluongbtp_them<0){
					 soluongbtp_them=0;
				 }
				 /*	
				  * System.out.println("soluongyeucau : "+soluongyeucau);
					System.out.println("soluongbtp_them : "+soluongbtp_them);
				  */
			double thanhtien=Double.parseDouble(giaTon)*soluongbtp_them;
			System.out.println("soluongbtp_them : "+soluongbtp_them);
			query = " insert ERP_NHAPKHO_SANPHAM(SONHAPKHO_FK, SANPHAM_FK, SOLUONGNHAN, SOLUONGNHAP, SOLO, NGAYSANXUAT, NGAYHETHAN,NgayNhapKho,DONGIA, THANHTIEN, DONGIAVIET, THANHTIENVIET, TIENTE_FK) " +
					" values (" + this.id + ", '"+sanphambtpid+"', '" + formatter.format(soluongbtp_them)   + "', " + formatter.format(soluongbtp_them)  + ", 'BTP" + this.getSoLoTuDong()+ "', '" +  ngaysanxuat + "','"+ Ngayhethan 
				   +"','"+ ngaynhapkho+"', " + giaTon + ", " + thanhtien+ ", " +
				    giaTon + ", " + thanhtien  + " , '" + tiente  + "') " ;
				
			if(!db.update(query))
			{
				this.msg = "1.Khong the tao moi ERP_NHAPKHO: " + query;
				db.getConnection().rollback();
				return false;
			}
			}
			if(rs!=null){
				rs.close();
			}
		}catch(Exception er){
			db.update("rollback");
			er.printStackTrace();
			this.msg=er.toString();
			return false;
		}
		return true;
	}

	public String chotNhapKhoLSX()
	{
		util=new Utility();
		try 
		{
			String ngaysua = getDateTime();
			String solenhSX="";
			String nam = this.ngaynhapkho.substring(0, 4);
			String thang = this.ngaynhapkho.substring(5, 7);

				String query=" select SANPHAM_FK,nk.KHONHAP as KHONHAN from ERP_NHAPKHO nk  " +
							 " inner join ERP_NHAPKHO_SANPHAM nksp on nk.PK_SEQ=nksp.SONHAPKHO_FK where nk.PK_SEQ= " +this.id;
							ResultSet rs=db.get(query);
							String chuoispid="";
							String ChuoiKhoId="";
						 
							int count=0;
							while(rs.next()){
							if(count==0){
								ChuoiKhoId= rs.getString("KHONHAN");
								chuoispid=  rs.getString("sanpham_fk");
							}else{
								ChuoiKhoId=ChuoiKhoId+";"+rs.getString("KHONHAN");
								chuoispid=chuoispid +";"+rs.getString("sanpham_fk");
							}
							count =count+1;
							}
						
							rs.close();
						 
							String err=util.CheckNgayGhiNhanHopLe(this.db,this.ngaynhapkho,chuoispid,ChuoiKhoId);
							if(err.length() >0){
								this.msg = err;
								db.update("rollback");
								return this.msg ;
							}
 
			query = " select  b.dongia ,  a.pk_seq as ctnhapkho,a.KHONHAP, a.SOLENHSANXUAT, a.NGAYNHAPKHO, a.NGAYCHOT, a.NOIDUNGNHAP, " +
					" b.SANPHAM_FK, b.SOLUONGNHAP, b.DONGIA, b.DONGIAVIET, b.TienTe_FK, isnull(b.SOLO, 0) as solo, " +
					" isnull(b.NgaySanXuat, '') as ngaysanxuat, isnull(b.NgayHetHan, '') as ngayhethan, " +
					" d.QUANLYLO, d.LOAISANPHAM_FK, case when d.KiemTraDinhLuong=1 or d.KiemTraDinhTinh=1 or isnull(cd.dinhtinh,'0')=1 then 1 else 0 end as KiemDinhChatLuong , " +
					" isnull(d.KiemTraDinhLuong,0) as DinhLuong,isnull(d.KiemTraDinhTinh,0)as DinhTinh ,isnull(cd.dinhtinh,'0') as dinhtinhcd, " +
					" ISNULL( ( select top(1) GIATON from ERP_TONKHOTHANG where SANPHAM_FK = d.pk_seq order by NAM desc, THANG desc ) , 0 ) as giaTON  " +
					" from ERP_NHAPKHO a inner join erp_congdoansanxuat_Giay cd on cd.pk_seq=a.congdoan_fk " +
					" inner join ERP_NHAPKHO_SANPHAM b on a.PK_SEQ = b.SONHAPKHO_FK " +
					" inner join ERP_KHOTT c on a.khonhap = c.PK_SEQ " +
					" inner join ERP_SANPHAM d on b.SANPHAM_FK = d.PK_SEQ " +
					" where a.PK_SEQ = '" + this.id + "'";
			
			System.out.println("1.Query chot nhap kho init: " + query);
			
			 rs = db.get(query);
			 
			 	int i=0;
			 	
				while(rs.next())
				{
				 i++;
					solenhSX = rs.getString("SOLENHSANXUAT"); 
					String loaisanpham = rs.getString("LOAISANPHAM_FK");
					 
					String masanpham = rs.getString("SANPHAM_FK");
					String khonhap = rs.getString("KHONHAP");
					String ngaysanxuat = rs.getString("ngaysanxuat");
					String ngayhethan = rs.getString("ngayhethan");
					double dongia=rs.getDouble("dongia");
					/*double dongiaViet = rs.getDouble("DONGIAVIET");
					String tiente_fk = rs.getString("TienTe_FK");*/
					
					double dongiaViet = rs.getDouble("giaTON");
					String tiente_fk = "100000";
					
					double soluong =  rs.getDouble("SOLUONGNHAP");
					String kiemdinhCL = rs.getString("KiemDinhChatLuong");
					String dinhluong=rs.getString("dinhluong");
					String dinhtinh=rs.getString("dinhtinh");
					if(rs.getString("dinhtinhcd").equals("1")){
						dinhtinh="1";
					}
				 
	  
				String spId = rs.getString("SANPHAM_FK");
				String solo = rs.getString("SOLO").trim();
				String soluongct = rs.getString("SOLUONGNHAP");
				String vitri = "100000";
					// TĂNG KHO SẢN XUÁT NGAY
				  query="select sanpham_fk from ERP_KHOTT_SP_CHITIET where ltrim(rtrim(solo))='"+solo+"' and sanpham_fk= "+spId+" and khott_fk="+khonhap;
				  
				  ResultSet rscheck=db.get(query);
				  
				  if(rscheck.next()) {
					    query = " update ERP_KHOTT_SP_CHITIET set soluong = soluong + '" + soluongct+ "', AVAILABLE = AVAILABLE + '" + soluongct + "', NGAYSANXUAT = '" + ngaysanxuat + "', NGAYHETHAN = '" + ngayhethan + "', NGAYNHAPKHO = '" + this.ngaynhapkho + "' where KHOTT_FK = '"+ khonhap + "' and SANPHAM_FK = '" + spId + "' and SOLO = '" + solo+ "' and BIN = '" + vitri + "'";
				  }else{
						query = " insert ERP_KHOTT_SP_CHITIET(KHOTT_FK, SANPHAM_FK, SOLUONG, BOOKED, AVAILABLE, SOLO, NGAYSANXUAT, NGAYHETHAN, NGAYNHAPKHO, BIN) "
							 +  " values('"+ khonhap+ "', '"+ spId+ "', '"+ soluongct+ "', '0', '"+ soluongct + "', '" + solo + "', '" + ngaysanxuat + "', '" + ngayhethan + "', '" + this.ngaynhapkho + "', '" + vitri + "')";

				  }
				  	 
				  if(db.updateReturnInt(query)!= 1)
				  {
						this.msg = "8.Khong the cap nhat ERP_KHOTT_SP_CHITIET: " + query;
						db.getConnection().rollback();
						return this.msg;
				  }
				  query=" select sanpham_fk from ERP_KHOTT_SANPHAM where   sanpham_fk= "+spId+" and khott_fk="+khonhap;
				  rscheck=db.get(query);
				  if(rscheck.next()){
					   query = " update ERP_KHOTT_SANPHAM set soluong = soluong + " + soluong + ", available = available + " + soluong + ", " +
						" THANHTIEN = (soluong + " + soluong + ") * GiaTon " +
						"  where khott_fk = '" + khonhap + "' and sanpham_fk = '" + masanpham + "'";
				  }else{
					  query="INSERT INTO ERP_KHOTT_SANPHAM (KHOTT_FK,SANPHAM_FK,GIATON,SOLUONG,THANHTIEN,BOOKED,AVAILABLE) VALUES " +
					  		"("+khonhap+","+spId+","+dongia+","+soluong+","+(dongia*soluong)+",0,"+soluong+")";
				  }
					if( db.updateReturnInt(query) !=1)
					{
						this.msg = "Không thể nhập kho cho sản phẩm này, Sản phẩm này chưa được chọn kho trong dữ liệu nền ";
						db.getConnection().rollback();
						return this.msg;
					}	
 			  
					/**
					 * Co kiem dinh thi luu san pham o trang thai cho kiem ---0 vao kho chitiet
					 * CÓ KIỂM ĐỊNH THÌ THÊM KIỂM ĐỊNH
					 */
					 
					else if(kiemdinhCL.equals("1"))
					{
 
								query = "insert ERP_YeuCauKiemDinh(nhapkho_fk, sanpham_fk, soluong,SoLuongDat, solo, ngaysanxuat, ngayhethan, ngaykiem, trangthai, nguoitao, ngaytao,lenhsanxuat_fk,congdoan_fk,DinhLuong,DinhTinh,CongTy_FK,NguoiSua,NgaySua) " +
										"values('" + this.id + "', '" + masanpham + "', '" + soluong + "','"+soluong+"', '" + rs.getString("SoLo") +  
										"', '" + ngaysanxuat + "', '" + ngayhethan + "', '" + ngaynhapkho + "', '0', '" + this.userId + "', '" + getDateTime() + "','"+solenhSX+"','"+this.CongDoanId+"','"+dinhluong+"','"+dinhtinh+"','"+this.congtyId+"','"+this.userId+"','"+getDateTime()+"')";
			
								if(!db.update(query))
								{
									this.msg = "Khong the cap nhat ERP_YeuCauKiemDinh: " + query;
									rs.close();
									db.getConnection().rollback();
									return this.msg;
								}
						}
					// Nếu nhập kho cho thành phẩm thì tự động nhập kho cho bán thành phẩm của thành phẩm này vào công đoạn trước.
				 
					String taikhoanktCo_SoHieu = "";
					String taikhoanktNo_SoHieu = "";
					
					if(loaisanpham.equals("100005")) //NHAP KHO THANH PHAM
					{
						taikhoanktNo_SoHieu = "632000";
						taikhoanktCo_SoHieu = "631000";
						
						double tonggiatri = dongiaViet * soluong;
						msg = util.Update_TaiKhoan_TheoSoHieu( db, thang, nam, this.ngaynhapkho, this.ngaynhapkho, "Nhập thành phẩm sản xuất", this.id, taikhoanktNo_SoHieu, taikhoanktCo_SoHieu, "", 
													Double.toString(tonggiatri), Double.toString(tonggiatri), "Sản phẩm", masanpham, "0", "", "", tiente_fk, "", "1", Double.toString(tonggiatri), Double.toString(tonggiatri), "Nhập thành phẩm sản xuất" );
						if(this.msg.trim().length() > 0)
						{
							rs.close();
							db.getConnection().rollback();
							return msg;
						}
					}
					else
					{
						if(loaisanpham.equals("100011")) //NHAP KHO BAN THANH PHAM
						{
							//LAY GIA 
							int namOLD = Integer.parseInt(nam);
							int thangOLD = Integer.parseInt(thang);
							
							if(thangOLD == 1)
							{
								thangOLD = 12;
								namOLD = namOLD - 1;
							}
							else
							{
								thangOLD = thangOLD - 1;
							}
							
							query = "select GIACHIPHI, GIANGUYENLIEU from ERP_TONKHOTHANG " +
									"where SANPHAM_FK = '" + masanpham + "' and KHOTT_FK = '" + khonhap + "' and THANG = '" + thangOLD + "' and NAM = '" + namOLD + "'";
							System.out.println("___LAY GIA BAN THANH PHAM: " + query );
							
							double giachiphi = 0;
							double gianguyenlieu = 0;
							ResultSet rsChiPhi = db.get(query);
							if(rsChiPhi.next())
							{
								giachiphi = rsChiPhi.getDouble("GIACHIPHI");
								gianguyenlieu = rsChiPhi.getDouble("GIANGUYENLIEU");
							}
							
							
							//CHI PHI NGUYEN VAT LIEU
							taikhoanktNo_SoHieu = "154100";
							taikhoanktCo_SoHieu = "631000";
							double tonggiatri = gianguyenlieu * soluong;
							msg = util.Update_TaiKhoan_TheoSoHieu( db, thang, nam, this.ngaynhapkho, this.ngaynhapkho, "Nhập bán thành phẩm", this.id, taikhoanktNo_SoHieu, taikhoanktCo_SoHieu, "", 
														Double.toString(tonggiatri), Double.toString(tonggiatri), "Sản phẩm", masanpham, "0", "", "", tiente_fk, "", "1", Double.toString(tonggiatri), Double.toString(tonggiatri), "Nhập bán thành phẩm" );
							if(this.msg.trim().length() > 0)
							{
								rs.close();
								db.getConnection().rollback();
								return msg;
							}
							
							
							//CHI PHI KHAC
							taikhoanktNo_SoHieu = "154200";
							taikhoanktCo_SoHieu = "631000";
							tonggiatri = giachiphi * soluong;
							msg = util.Update_TaiKhoan_TheoSoHieu( db, thang, nam, this.ngaynhapkho, this.ngaynhapkho, "Nhập bán thành phẩm", this.id, taikhoanktNo_SoHieu, taikhoanktCo_SoHieu, "", 
														Double.toString(tonggiatri), Double.toString(tonggiatri), "Sản phẩm", masanpham, "0", "", "", tiente_fk, "", "1", Double.toString(tonggiatri), Double.toString(tonggiatri), "Nhập bán thành phẩm" );
							if(this.msg.trim().length() > 0)
							{
								rs.close();
								db.getConnection().rollback();
								return msg;
							}
							
						}
					}
					
				}
				rs.close();
			
				if(i==0){
					this.msg = "Vui lòng chốt lại phiếu,nếu không được thì báo cho Admin để xử lý";
					db.getConnection().rollback();
					return this.msg;
				}
			
			query = "update ERP_NHAPKHO set trangthai = '1', giochot = '" + getTime() + "',  ngaysua = '" + ngaysua + "', nguoisua = '" + userId + "' where pk_seq = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_NHAPKHO: " + query;
				db.getConnection().rollback();
				return this.msg;
			
			}
		
		} 
		catch (Exception e) 
		{ 
			
			this.msg = "115.Exception : " + e.getMessage();
			e.printStackTrace();	
			db.update("rollback");
			return this.msg;
		}
		return this.msg;
	}


	

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}


	public void setCongDoanId(String ctidcurrent) {

		this.CongDoanId=ctidcurrent;
	}


	public String GetCongDoanId() {

		return this.CongDoanId;
	}

	
	public ResultSet getRsCongDoan()
	{
	
		return this.rsCongdoan;
	}

	
	public void setRsCongDoan(ResultSet rscongdoan)
	{
		this.rsCongdoan=rscongdoan;
		
	}

	
	public ResultSet getRsLenhSanXuat()
	{
	
		return this.rsLenhsanxuat;
	}

	
	public void setRsLenhSanXuat(ResultSet rsLenhsanxuat)
	{
		this.rsLenhsanxuat=rsLenhsanxuat;
		
	}

	
	public boolean HuyNhapKhoLsx()
	{
		String query=" SELECT SP.PK_SEQ FROM ERP_NHAPKHO_SANPHAM NKSP "+ 
					 " INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=NKSP.SANPHAM_FK "+
					 " WHERE NKSP.SONHAPKHO_FK="+this.id+" AND SP.LOAISANPHAM_FK= 100011";
		
		ResultSet rs = db.get(query);
		try{
			if(!rs.next()){
				this.msg="Chỉ được xóa nhập kho bán thành phẩm";
				return false;
			}
			
		}catch(Exception err){
			this.msg="Thông báo Admin để xử lý lỗi :"+err.getMessage();
			return false;
		}
		
 	    query = " select a.PK_SEQ as nhId, a.KHONHAP, a.SOPHIEUNHAPHANG, a.SODONTRAHANG, a.SoLenhSanXuat," +
			    " a.NGAYNHAPKHO, b.pk_seq as ndnId, b.TEN as ndnTen, a.TRANGTHAI, c.QUANLYBIN,ISNULL(a.GhiChu,'')as GhiChu, " +
				" k.Prefix+cast(a.SOLENHSANXUAT as varchar(20)) as SOCHUNGTU,a.CongDoan_FK as CongDoanId, " +
				" a.NGAYNHAPKHO, isnull(a.NGAYCHOT, '') as NgayChot, a.NOIDUNGNHAP, b.TEN as ndnTen, a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO  " +
				" from ERP_NHAPKHO a inner join ERP_NOIDUNGNHAP b on a.NOIDUNGNHAP = b.PK_SEQ inner join ERP_KHOTT c on a.KHONHAP = c.PK_SEQ " +
				"  left join erp_lenhsanxuat_Giay k on a.SOLENHSANXUAT = k.pk_seq " +
				" where a.PK_SEQ = '" + this.id + "'";
	
		//System.out.println("Init " + query);
		rs = db.get(query);
		try 
		{
			while(rs.next())
			{
				this.ngaynhapkho = rs.getString("NGAYNHAPKHO");
				
				this.ngaychotNV = rs.getString("NgayChot");
				this.ndnId = rs.getString("ndnId");
				this.solenhsanxuat = rs.getString("SoLenhSanXuat");
				this.CongDoanId=rs.getString("CongDoanId");
				this.khoId = rs.getString("KHONHAP");
				this.trangthai = rs.getString("trangthai");
				this.ghichu=rs.getString("GhiChu");
				if(rs.getString("QUANLYBIN").equals("0"))
					this.quanlybean = false;
				else
					this.quanlybean = true;
			}
			rs.close();
		} 
		catch (Exception e) {
			this.msg=e.toString();
			e.printStackTrace();
			return false;
			
		}
			
		
		
		if(!CheckNghiepVu())
		{
			return false;
		}
		String solenhSX="";
		String nam = this.ngaynhapkho.substring(0, 4);
		String thang = this.ngaynhapkho.substring(5, 7);
		 query = 		" select a.pk_seq as ctnhapkho,a.KHONHAP, a.SOLENHSANXUAT, a.NGAYNHAPKHO, a.NGAYCHOT, a.NOIDUNGNHAP, " +
						" b.SANPHAM_FK, b.SOLUONGNHAP, b.DONGIA, b.DONGIAVIET, b.TienTe_FK, isnull(b.SOLO, 0) as solo, " +
						" isnull(b.NgaySanXuat, '') as ngaysanxuat, isnull(b.NgayHetHan, '') as ngayhethan, " +
						" d.QUANLYLO, d.LOAISANPHAM_FK, lsxcd.kiemdinhchatluong  as KiemDinhChatLuong " +
						" ,isnull(cd.DinhLuong,0) as DinhLuong,isnull(cd.DinhTinh,0)as DinhTinh " +
						" from ERP_NHAPKHO a  " +
						" inner join erp_lenhsanxuat_congdoan_giay lsxcd   on lsxcd.lenhsanxuat_fk=a.solenhsanxuat and lsxcd.congdoan_fk=a.congdoan_fk " + 
						" inner join erp_congdoansanxuat_Giay cd on cd.pk_seq=a.congdoan_fk " +
						" inner join ERP_NHAPKHO_SANPHAM b on a.PK_SEQ = b.SONHAPKHO_FK " +
						" inner join ERP_KHOTT c on a.khonhap = c.PK_SEQ " +
						" inner join ERP_SANPHAM d on b.SANPHAM_FK = d.PK_SEQ " +
						" where a.PK_SEQ = '" + this.id + "'";
		
		 System.out.println("1.Query chot nhap kho init: " + query);
		 rs = db.get(query);
		 try{
		 if(rs != null)
		 {
			db.getConnection().setAutoCommit(false);
			while(rs.next())
			{
				String ctnhapkho = rs.getString("ctnhapkho"); 
				solenhSX = rs.getString("SOLENHSANXUAT"); 
				String loaisanpham = rs.getString("LOAISANPHAM_FK");
				String noidungnhap = rs.getString("NOIDUNGNHAP");
				String masanpham = rs.getString("SANPHAM_FK");
				String khonhap = rs.getString("KHONHAP");
				//String ngaysanxuat = rs.getString("ngaysanxuat");
				//String ngayhethan = rs.getString("ngayhethan");
				double dongia = rs.getDouble("DONGIA");
				double dongiaViet = rs.getDouble("DONGIAVIET");
				String tiente_fk = rs.getString("TienTe_FK");
				float soluong =  rs.getFloat("SOLUONGNHAP");
				String kiemdinhCL = rs.getString("KiemDinhChatLuong");
				 
				 String solo=rs.getString("solo");
				 
				if(kiemdinhCL.equals("0"))   
				{
					query = "update ERP_KHOTT_SP_CHITIET set soluong = soluong - '" + soluong+ "', AVAILABLE = AVAILABLE - '" + soluong + "' where KHOTT_FK = '"+ khonhap + "' and SANPHAM_FK = '" + masanpham + "' and SOLO = '" + solo+ "' and BIN = '100000'";
					int i= db.updateReturnInt(query);
					if(i <=0 )
					{
						
							this.msg = "8.Khong the cap nhat ERP_KHOTT_SP_CHITIET: " + query;
						
							db.getConnection().rollback();
							return false;
					
					}
					
					
					query =  " update ERP_KHOTT_SANPHAM set soluong = soluong - " + soluong + ", available = available - " + soluong + ", " +
						     " THANHTIEN = (soluong - " + soluong + ") * GiaTon " +
						     " where khott_fk = '" + khonhap + "' and sanpham_fk = '" + masanpham + "'";
					System.out.println("Cap Nhat Kho "+query);
					i= db.updateReturnInt(query);
					if(i <=0 )
					{
						
							this.msg = "8.Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;
							System.out.println(msg);
							db.getConnection().rollback();
							return false;
					
					}
				}else if(kiemdinhCL.equals("1"))
				{
					query=" SELECT KD.TRANGTHAI,KD.SOLO, SANPHAM_FK ,KD.SOLUONGDAT FROM ERP_NHAPKHO NK INNER JOIN ERP_YEUCAUKIEMDINH  KD "+
					" ON NK.PK_SEQ=KD.NHAPKHO_FK WHERE NK.PK_SEQ ="+ctnhapkho+" AND KD.TRANGTHAI=1 and  kd.SANPHAM_FK="+masanpham+" and KD.SOLO='"+solo+"' ";
					ResultSet rsdanhap =db.get(query);
					System.out.println("\n\n Truong Hop 2:  CÓ Kiểm Dịnh " +query);
					if(rsdanhap.next()) {
						//đã kiểm định và tăng kho
						double soluongkiemdinh=rsdanhap.getDouble("SOLUONGDAT");
						
						query = "update ERP_KHOTT_SP_CHITIET set soluong = soluong - '" + soluongkiemdinh+ "', AVAILABLE = AVAILABLE - '" + soluongkiemdinh + "' " +
								" where KHOTT_FK = '"+ khonhap + "' and SANPHAM_FK = '" + masanpham + "' and SOLO = '" + solo+ "' and BIN = '100000'";
						int i= db.updateReturnInt(query);
						if(i <=0 )
						{
							
								this.msg = "8.Khong the cap nhat ERP_KHOTT_SP_CHITIET: " + query;
								System.out.println(msg);
								db.getConnection().rollback();
								return false;
						
						}
						
						
						query =  " update ERP_KHOTT_SANPHAM set soluong = soluong - " + soluongkiemdinh + ", available = available - " + soluongkiemdinh + ", " +
							     " THANHTIEN = (soluong - " + soluongkiemdinh + ") * GiaTon " +
							     " where khott_fk = '" + khonhap + "' and sanpham_fk = '" + masanpham + "'";
						
						i= db.updateReturnInt(query);
						if(i <=0 )
						{
							
								this.msg = "8.Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;
								System.out.println(msg);
								db.getConnection().rollback();
								return false;
						
						}
						
					} else{
						query = 	" update ERP_KHOTT_SP_CHITIET_TRANGTHAI set soluong = soluong -'" + soluong + "', AVAILABLE = AVAILABLE - '" + soluong + "' " +
						" where KHOTT_FK = '" + khonhap + "' and SANPHAM_FK = '" + masanpham + "' and SOLO = '" + solo + "' and BIN = '100000' and trangthai = '" + trangthai + "'";
							System.out.println("Kiem Cap Nhat kho : "+query);
							int i= db.updateReturnInt(query);
							if( i<=0)
							{
									this.msg = "8.Khong the cap nhat ERP_KHOTT_SP_CHITIET: " + query;
									System.out.println(msg);
									db.getConnection().rollback();
									return false;
							
							}
					}
					rsdanhap.close();
					
					
				 
					 
					
				}
				/*String taikhoanktCo = "";
				String taikhoanktNo = "";
				
				query = "select TAIKHOANKTCO, TAIKHOANKTNO " +
						"from ERP_CAUHINHDINHKHOANKETOAN  " +
						"where NOIDUNGNHAP_FK = '" + noidungnhap + "' and LOAISANPHAM_FK = '" + loaisanpham + "' ";
				
				//System.out.println("5.Query lay tai khoan: " + query);
				
				ResultSet tkRs = db.get(query);
				if(tkRs != null)
				{
					if(tkRs.next())
					{
						taikhoanktCo = tkRs.getString("TAIKHOANKTCO");
						taikhoanktNo = tkRs.getString("TAIKHOANKTNO");
						tkRs.close();
					}
					
					if(taikhoanktCo.trim().length() <= 0 || taikhoanktNo.trim().length() <= 0)
					{
						this.msg = "Loại sản phẩm và nội dung nhập tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
						rs.close();
						db.getConnection().rollback();
						return false;
					}
					
					//Kiem tra xem da cao tai khoan nay trong thang chua
					query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO " +
							"where taikhoankt_fk = '" + taikhoanktNo + "' and nguyente_fk = '" + tiente_fk + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
					ResultSet rsTKNo = db.get(query);
					int sodong = 0;
					if(rsTKNo.next())
					{
						sodong = rsTKNo.getInt("sodong");
					}
					rsTKNo.close();
					if(sodong > 0) //daco
					{
						query = " update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND - " + dongiaViet + "*" + soluong + ", " +
								" GIATRINGUYENTE = GIATRINGUYENTE - "  + dongia + "*" + soluong + 
								" where taikhoankt_fk = '" + taikhoanktNo + "' and nguyente_fk = '" + tiente_fk + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					}
					else
					{
						query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, THANG, NAM) " +
								"values('" + taikhoanktNo + "', '0', '0', '" + tiente_fk + "', '', '" + thang + "', '" + nam + "')";
					}
					
					//System.out.println("5.Cap nhat No: " + query);
					if(!db.update(query))
					{
						rs.close();
						db.getConnection().rollback();
						
						System.out.println("1.Loi: " + query);
						return false;
					}
					
					
					//Tai khoan co
					query = "select count(*) as sodong from ERP_TAIKHOAN_NOCO  " +
							"where taikhoankt_fk = '" + taikhoanktCo + "' and nguyente_fk = '" + tiente_fk + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					
					rsTKNo = db.get(query);
					
					sodong = 0;
					if(rsTKNo.next())
					{
						sodong = rsTKNo.getInt("sodong");
					}
					rsTKNo.close();
					
					if(sodong > 0) //daco
					{
						query = "update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND - " + dongiaViet + "*" + soluong + ", " +
														" GIATRINGUYENTE = GIATRINGUYENTE - "  + dongia + "*" + soluong + 
								" where taikhoankt_fk = '" + taikhoanktCo + "' and nguyente_fk = '" + tiente_fk + "' and thang = '" + thang + "' and nam = '" + nam + "'";
					}
					else
					{
						query = "insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRICOVND, GIATRINOVND, NGUYENTE_FK, GIATRINGUYENTE, THANG, NAM) " +
								"select '" + taikhoanktCo + "', '0', '0', '" + tiente_fk + "', '0', '" + thang + "', '" + nam + "' ";
					}
					
					//System.out.println("5.Cap nhat Co: " + query);		
					if(!db.update(query))
					{
						rs.close();
						db.getConnection().rollback();
						
						System.out.println("2.Loi: " + query);
						return false;
					}
					
				}*/
			}
			rs.close();
		}
		
		query = "update ERP_NHAPKHO set trangthai = '2', giochot = '" + getTime() + "',  ngaysua = '" + getDateTime() + "', nguoisua = '" + userId + "' where pk_seq = '" + this.id + "'";
	
		if(!db.update(query))
		{
			this.msg = "Khong the cap nhat ERP_NHAPKHO: " + query;
			db.update("rollback");
			return false;
		}
		
		query = " update ERP_YeuCauKiemDinh set trangthai = '2', nguoisua = '" + this.userId + "',NguoiDuyet='"+this.userId+"', ngaysua = '" + getDateTime() + "' where pk_seq in  (select pk_Seq from erp_yeucaukiemdinh where nhapkho_Fk='"+this.id+"')";
		if(!db.update(query))
		{
			this.msg = " Không thể cập nhật ERP_YeuCauKiemDinh " + query;
			db.update("rollback");
			return false;
		}
	
		query = "update ERP_LENHSANXUAT_CONGDOAN_GIAY  set TINHTRANG = '0' WHERE LENHSANXUAT_FK='"+this.solenhsanxuat+"' and congdoan_fk='"+this.CongDoanId+"'  ";
		if(!db.update(query))
		{
			this.msg = "Không thể cập nhật erp_lenhsanxuat_congdoan_Giay " + query;
			db.update("rollback");
			return false;
		}
	
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
	} 
	catch (Exception e) 
	{ 
		this.msg = "115.Exception : " + e.getMessage();
		e.printStackTrace();
		db.update("rollback");
		
	}
		
		return true;
	}

	public boolean CheckNghiepVu()
	{
		String query=" select count(*) from erp_lenhsanxuat_congdoan_giay where lenhsanxuat_fk="+this.solenhsanxuat+"  and tinhtrang=1 "+
		" and thutu > (select thutu  from erp_lenhsanxuat_congdoan_giay where lenhsanxuat_fk="+this.solenhsanxuat+" and congdoan_fk="+this.CongDoanId+" ) ";
		ResultSet rs =this.db.get(query);
			try
			{
				if(rs!=null)
				while(rs.next())
				if(rs.getInt(1)>0)
				{
					this.msg="Đã có công đoạn sản xuất hoàn tất trong lệnh sản xuất,bạn phải hủy các nghiệp vụ của công đoạn tiếp theo trước khi xóa nhập kho !";
					return false;
				}rs.close();
				System.out.println("Check cong doan trong lenh san xuat "+query);
			
			} catch (SQLException e)
			{
				e.printStackTrace();
				return false;
			}
			return true;
	}
}
