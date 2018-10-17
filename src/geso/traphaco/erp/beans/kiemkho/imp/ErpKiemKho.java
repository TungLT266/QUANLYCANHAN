package geso.traphaco.erp.beans.kiemkho.imp;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.kiemkho.IErpKiemKho;
import geso.traphaco.erp.beans.kiemkho.IErpKiemKho_SanPham;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.util.Utility_Kho;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public class ErpKiemKho implements IErpKiemKho
{
	String ID, NgayDieuChinh, LyDoDieuChinh, KhoTT_FK, NgayTao, NgaySua, NguoiSua, NguoiTao, Msg, NgayChot, Khu , loaisanpham, malonsanpham, ticksoluong	;
	int TrangThai;
	ResultSet rsKhoTT, rsSanPham, rsViTriKho, rsBin, rsKhu , rsLoaisanpham , rsMalonsanpham;
	List<IErpKiemKho_SanPham> sanphamList;
	dbutils db;
	String thang,nam;
	String nhamayid;
	
	String tungay;
	String denngay;
	Utility util;
	NumberFormat format = new DecimalFormat("##,###,###.######");
	
	public ErpKiemKho()
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
		this.TrangThai =0;
		this.NgayChot = "";
		this.Khu = "";
		this.thang="";
		this.nam="";
		this.nhamayid="";
		this.loaisanpham="";
		this.malonsanpham="";
		this.ticksoluong="";
		this.denngay="";
		this.tungay="";
		
		sanphamList = new ArrayList<IErpKiemKho_SanPham>();
		db = new dbutils();
		util = new Utility();
	}
	
	public ErpKiemKho(String id)
	{
		this.ID = id;
		this.NgayDieuChinh = "";
		this.LyDoDieuChinh = "";
		this.thang="";
		this.nam="";
		this.KhoTT_FK = "";
		this.NgayTao = "";
		this.NgaySua = "";
		this.NguoiTao = "";
		this.NguoiSua = "";
		this.Msg = "";
		this.TrangThai = 0;
		this.NgayChot = "";
		this.Khu = "";
		this.nhamayid="";
		this.loaisanpham="";
		this.malonsanpham="";
		this.ticksoluong="";
		this.denngay="";
		this.tungay="";
		util = new Utility();
		
		
		sanphamList = new ArrayList<IErpKiemKho_SanPham>();
		db = new dbutils();
	}
	
	
	public String getTungay(){
		return this.tungay;
	}
	public void setTungay(String tungay){
		this.tungay = tungay;
	}
	
	
	public String getDenngay(){
		return this.denngay;
	}
	public void setDenngay(String denngay){
		this.denngay = denngay;
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
	
	
	
	public ResultSet getRsLoaisanpham() {
		return rsLoaisanpham;
	}

	public void setRsLoaisanpham(ResultSet rsLoaisanpham) {
		this.rsLoaisanpham = rsLoaisanpham;
	}

	public ResultSet getRsMalonsanpham() {
		return rsMalonsanpham;
	}

	public void setRsMalonsanpham(ResultSet rsMalonsanpham) {
		this.rsMalonsanpham = rsMalonsanpham;
	}

	public String getLoaisanpham() {
		return loaisanpham;
	}

	public void setLoaisanpham(String loaisanpham) {
		this.loaisanpham = loaisanpham;
	}

	public String getMalonsanpham() {
		return malonsanpham;
	}

	public void setMalonsanpham(String malonsanpham) {
		this.malonsanpham = malonsanpham;
	}

	public String getTicksoluong() {
		return ticksoluong;
	}

	public void setTicksoluong(String ticksoluong) {
		this.ticksoluong = ticksoluong;
	}

	public void CreateRsKho()
	{
		
		String query = "Select PK_SEQ, Ten from Erp_KhoTT where pk_seq in" + util.quyen_khott(this.NguoiTao);
		this.rsKhoTT = this.db.get(query);
	}
	
	public void CreateRsLoaiSanPham()
	{
		String query = "select PK_SEQ , MA , TEN from ERP_LOAISANPHAM";
		this.rsLoaisanpham = this.db.get(query);
	}
	
	public void CreateRsMaLonSanPham()
	{
		String query = " select distinct MA from ERP_SANPHAM where 1 = 1 ";
		if(this.KhoTT_FK.length()> 0)
		{ 
			query += " and PK_SEQ in ( select SANPHAM_FK from ERP_KHOTT_SANPHAM where KHOTT_FK = '"+KhoTT_FK+"' )";
		}
		if(this.loaisanpham.length()> 0 )
		{
			query += " and LOAISANPHAM_FK = '"+ loaisanpham +"'";
		}
		this.rsMalonsanpham = this.db.get(query);
	}
	
	public void CreateRsSanPham()
	{	
		// load lại sản phẩm để điều chỉnh tồn kho theo ngày,lấy báo cáo xuất nhập tồn theo ngày điều chỉnh
		// phương thức này chỉ gọi khi chọn lại kho và ngày điều chỉnh.
		
		if(this.KhoTT_FK.length() >0 && this.NgayDieuChinh.length() > 0){
 
		  	String[] param = new String[3];
		    param[0] =this.KhoTT_FK;
		    param[1] =this.NgayDieuChinh;
		    param[2] =this.NgayDieuChinh;
		    
		    String query = " insert into sanpham_tmp select pk_seq from erp_sanpham where 1 = 1 "+	    	    
		    			   " and pk_seq in (select sanpham_fk from erp_khott_sanpham where khott_fk="+this.KhoTT_FK+")";
    	 
    	    db.update(query);
		    ResultSet   rsSanPham = db.getRsByPro("REPORT_XUATNHAPTON_TT", param);
 
			sanphamList.clear();
			if (rsSanPham != null)
				try
				{
					while (rsSanPham.next())
					{
						IErpKiemKho_SanPham s = new ErpKiemKho_SanPham();
						s.setSanPham_FK(rsSanPham.getString("PK_SEQ"));
						s.setMaSanPham(rsSanPham.getString("SPMA"));
						double tondau = rsSanPham.getDouble("TONDAUKY");
						double soluongnhap = rsSanPham.getDouble("TONGNHAP");
						double soluongxuat = rsSanPham.getDouble("TONGXUAT");
						double soluongton = tondau + soluongnhap - soluongxuat;
						//System.out.println(rsSanPham.getString("SPMA"));
						//System.out.println(tondau+"--------"+soluongnhap+"----"+soluongxuat);
						
						s.setTonKhoCu(format.format(soluongton));
						s.setTenSanPham(rsSanPham.getString("SPTEN"));
						s.setLoaisanpham(rsSanPham.getString("LSP"));
						s.setDonvi(rsSanPham.getString("DONVI"));
						s.setTonKhoMoi(format.format(soluongton));
						
						sanphamList.add(s);
					}
					rsSanPham.close();
				}
				catch (Exception e)
				{ 
					e.printStackTrace();
					this.Msg=e.getMessage();
				}
		}
	}
	
	public List<IErpKiemKho_SanPham> getSanPhamKhoList()
	{
		return this.sanphamList;
	}
	
	public void setSanPhamKho(List<IErpKiemKho_SanPham> SanPhamKhoList)
	{
		this.sanphamList = SanPhamKhoList;
	}
	
	public static void main(String[] agr)
	{
		String numb = "242";
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
			
			
			if (this.KhoTT_FK.length() == 0)
			{
				this.Msg = "Vui lòng chọn kho điều chỉnh!";
				return false;
			}
			db.getConnection().setAutoCommit(false);
			if (number_sp > 0)
			{
				query =
				"Update ERP_KIEMKHO set NgaySua='" + getDateTime() + "',DIENGIAI=N'" +
				this.LyDoDieuChinh + "',NguoiSua='" + this.NguoiSua + "', KhoTT_FK='" +
				this.KhoTT_FK + "',ngaykiem='"+this.NgayDieuChinh+"' Where PK_SEQ='" + this.ID + "'";
				
				if (!db.update(query))
				{
					this.setMessage("Không thể cập nhật kho " + query);
					db.getConnection().rollback();
					return false;
				}
				query="delete Erp_KiemKho_SanPham where KIEMKHO_FK= "+this.ID;
				if (!db.update(query))
				{
					this.setMessage("Không thể cập nhật kho " + query);
					db.getConnection().rollback();
					return false;
				}
				
				for (int i = 0; i < number_sp; i++)
				{
					IErpKiemKho_SanPham s = this.sanphamList.get(i);
					s.setGiaTri("0");
					
					query = "INSERT INTO Erp_KiemKho_SanPham (KIEMKHO_FK,SANPHAM_FK,SOLUONGCU,SOLUONGMOI,SOLUONGDIEUCHINH,GIATRIDIEUCHINH) "+
					" VALUES (" +this.ID +"," +s.getSanPham_FK() +"," + s.getTonKhoCu() +","+s.getTonKhoMoi()+"," + s.getSoLuongDieuChinh()+ ","+s.getGiaTri()+")";

					if ( !db.update(query))
					{
						 
						this.Msg = "1.Không thể tạo mới Erp_KiemKho_SanPham: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			else
			{
				this.Msg = "Chưa có sản phẩm nào được cập nhật số lượng tồn!";
				db.getConnection().rollback();
				return false;
			}
			if(checkexistkiemkho()){
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e)
		{
			this.setMessage("Exception : " + e.getMessage());
			db.update("rollback");
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
		int number_sp = this.sanphamList.size();
		 
		String query = "";
		try
		{
			
			
			if (this.KhoTT_FK.length() == 0)
			{
				this.Msg = "Vui lòng chọn kho điều chỉnh!";
				return false;
			}
			if (number_sp > 0)
			{
					
				query="INSERT INTO ERP_KIEMKHO ( DIENGIAI,KHOTT_FK,TRANGTHAI,NGAYTAO,NGAYSUA,NGUOITAO,NGUOISUA,NGAYKIEM)VALUES "+
					  " ( N'"+this.LyDoDieuChinh+"','"+this.KhoTT_FK+"','0','"+this.getDateTime()+"','"+this.getDateTime()+"',"+this.NguoiTao+","+this.NguoiSua+",'"+this.NgayDieuChinh+"')";
				
				db.getConnection().setAutoCommit(false);
				if (db.update(query) != true)
				{
					this.Msg = "Lỗi tạo mới :" + query;
					db.getConnection().rollback();
					return false;
				}
				else
				{
					String dcID = "";
					query = "select IDENT_CURRENT('ERP_KIEMKHO')  as dcID";
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
						this.Msg = "Lỗi   dòng lệnh : " + query;
						db.getConnection().rollback();
						return false;
					}
					for (int i = 0; i < number_sp; i++)
					{
						IErpKiemKho_SanPham s = this.sanphamList.get(i);
						s.setGiaTri("0");
						
						if( Double.parseDouble(s.getTonKhoMoi()) <0){
							this.Msg = "Không thể điều chỉnh số lượng tồn kho của sản phẩm "+s.getTenSanPham()+" nhỏ hơn 0: ";
							db.getConnection().rollback();
							return false;
						}
						query = "INSERT INTO Erp_KiemKho_SanPham (KIEMKHO_FK,SANPHAM_FK,SOLUONGCU,SOLUONGMOI,SOLUONGDIEUCHINH,GIATRIDIEUCHINH) "+
							" VALUES (" +dcID +"," +s.getSanPham_FK() +"," + s.getTonKhoCu() +","+s.getTonKhoMoi()+"," + s.getSoLuongDieuChinh()+ ","+s.getGiaTri()+")";

						if ( !db.update(query))
						{
							 
							this.Msg = "1.Không thể tạo mới Erp_KiemKho_SanPham: " + query;
							db.getConnection().rollback();
							return false;
						}
					}
				}
				
				
				if(checkexistkiemkho()){
					db.getConnection().rollback();
					return false;
				}
				
				
				 db.getConnection().commit();
				 db.getConnection().setAutoCommit(true);
				
			}
			else
			{
				db.update("rollback");
				this.Msg = "Chưa có sản phẩm nào được cập nhật tồn kho!";
				return false;
			}
		}
		catch (Exception e)
		{
			db.update("rollback");
			this.Msg = "Lỗi tạo mới  : " + e.getMessage();
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	private boolean checkexistkiemkho() {
		
			try{
				String sql=" select pk_seq from erp_kiemkho where ngaykiem='"+this.NgayDieuChinh+"' " +
						   "  and khott_fk="+this.KhoTT_FK+" and trangthai <>'2' " ;
				
				sql=" SELECT  SP.MA + ' - ' +SP.TEN  AS TENSP, COUNT (KKSP.SANPHAM_FK)  FROM ERP_KIEMKHO  KK "+ 
					" INNER JOIN ERP_KIEMKHO_SANPHAM KKSP ON KKSP.KIEMKHO_FK=KK.PK_SEQ "+
					" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=KKSP.SANPHAM_FK "+
					" WHERE NGAYKIEM='"+this.NgayDieuChinh+"'    AND kk.TRANGTHAI <>'2' AND KKSP.SOLUONGDIEUCHINH <> 0 and KHOTT_FK="+this.KhoTT_FK+" "+
					" GROUP BY  SP.MA,SP.TEN  "+
					" HAVING COUNT (KKSP.SANPHAM_FK) >1 "; 
				
				
				ResultSet rs=db.get(sql);
				String msg1="";
					while (rs.next()){
						msg1=" " +rs.getString("tensp") +"\n";
					}
					rs.close();
				if(msg1.length()>0){
					this.Msg="Sản phẩm đã tồn tại kiểm kho trong ngày : "+msg1;
					return true;
				}else{
					return false;
				}
			 
			}catch(Exception er){
				return true;
			}
		
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
		try
		{
			
			
			String 	query = " Select a.pk_seq, thang,nam, a.diengiai, KhoTT_FK, nhamay_fk ,a.trangthai,isnull(a.ngaykiem,'') as ngaykiem " +
							" From ERP_KIEMKHO a " +
					   		" inner join erp_khott kho on kho.pk_seq=khott_fk " +
					   		" Where a.PK_SEQ = " + this.ID + " ";
		 
				ResultSet rs = db.get(query);
			
				while (rs.next())
				{
					this.ID = rs.getString("PK_SEQ");
					this.KhoTT_FK = rs.getString("KhoTT_FK");
					this.LyDoDieuChinh = rs.getString("diengiai");
					this.thang = rs.getString("THANG");
					this.nam = rs.getString("NAM");
					this.nhamayid=rs.getString("nhamay_fk") == null ? "" : rs.getString("nhamay_fk");
					this.TrangThai=rs.getInt("trangthai");
					this.NgayDieuChinh=rs.getString("ngaykiem");
			 
				}
				rs.close();
				Hashtable<String,Double> htb=this.gethtb_xuatnhapton(); 
			query = " SELECT LSP.TEN AS LOAISANPHAM,F.DONVI ,A.SANPHAM_FK,   E.MA   AS MASP, E.TEN    AS TENSP,  "+
					" A.SOLUONGCU,A.SOLUONGMOI , A.SOLUONGDIEUCHINH    "+
					" FROM ERP_KIEMKHO_SANPHAM A   "+
					" INNER JOIN ERP_SANPHAM E ON E.PK_SEQ = A.SANPHAM_FK    "+
					" INNER JOIN DONVIDOLUONG F ON F.PK_SEQ = E.DVDL_FK  " +
					" INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ=E.LOAISANPHAM_FK "+
					" WHERE A.KIEMKHO_FK="+this.ID +" order by A.SOTT ";
				rsSanPham=db.get(query);
				sanphamList.clear();
				if (rsSanPham != null)
				while (rsSanPham.next())
				{
					IErpKiemKho_SanPham s = new ErpKiemKho_SanPham();
					s.setSanPham_FK(rsSanPham.getString("SanPham_FK"));
					s.setMaSanPham(rsSanPham.getString("MaSP"));
					s.setTenSanPham(rsSanPham.getString("TenSP"));
					s.setLoaisanpham(rsSanPham.getString("loaisanpham"));
					s.setDonvi(rsSanPham.getString("donvi"));
					double soluongton=htb.get(rsSanPham.getString("SanPham_FK"));
					s.setTonKhoCu(format.format(soluongton));
					s.setTonKhoMoi(format.format(rsSanPham.getDouble("SOLUONGMOI")));
					sanphamList.add(s);
				}
				rsSanPham.close();
				htb.clear();	 
		}
		catch (Exception e)
		{
			e.printStackTrace();
			this.Msg=e.getMessage();
		}
	}
	
	private Hashtable<String, Double> gethtb_xuatnhapton() {
		// TODO Auto-generated method stub
		
		Hashtable<String, Double > htb=new Hashtable<String, Double>();
		try{
			
			String[] param = new String[3];
		    param[0] =this.KhoTT_FK;
		    param[1] =this.NgayDieuChinh;
		    param[2] =this.NgayDieuChinh;
		    
		    String query = " insert into sanpham_tmp select sanpham_fk from erp_kiemkho_sanpham where kiemkho_fk= "+this.ID;	    	    
		    			   
    	 
    	    db.update(query);
		    ResultSet   tongHopNXT = db.getRsByPro("REPORT_XUATNHAPTON_TT", param);
		    while(tongHopNXT.next()){
		    	double tondau = tongHopNXT.getDouble("TONDAUKY");
		    	double soluongnhap = tongHopNXT.getDouble("TONGNHAP");
		    	double soluongxuat = tongHopNXT.getDouble("TONGXUAT");
		    	htb.put(tongHopNXT.getString("pk_seq"),tondau+soluongnhap-soluongxuat);
		    }
		    tongHopNXT.close();
		    
		}catch(Exception er){
			er.printStackTrace();
		}
		
		return htb;
	}

	public void rsSanPhamKhoDisplay()
	{
		
		
		String query = "";
 
			query = 	
			" SELECT A.SANPHAM_FK, case when len(ISNULL(E.MA, '')) <= 0 then E.MA else E.MA end AS MASP, E.TEN +'('+ isnull(E.QUYCACH, '') +')'  AS TENSP,  "+
			" A.SOLUONGCU AS TONHIENTAI, A.SOLUONGDIEUCHINH ,A.SOLO   "+
			" FROM ERP_KIEMKHO_SP_CHITIET A   "+
			" INNER JOIN ERP_SANPHAM E ON E.PK_SEQ = A.SANPHAM_FK    "+
			" INNER JOIN DONVIDOLUONG F ON F.PK_SEQ = E.DVDL_FK     "+
			" WHERE A.KIEMKHO_FK="+this.ID ;
  
		ResultSet rsSanPham = db.get(query);
		sanphamList.clear();
		if (rsSanPham != null)
			try
			{
				while (rsSanPham.next())
				{
					IErpKiemKho_SanPham s = new ErpKiemKho_SanPham();
					s.setSanPham_FK(rsSanPham.getString("SanPham_FK"));
					s.setMaSanPham(rsSanPham.getString("MaSP"));
					s.setTonHienTai(rsSanPham.getString("TonHienTai"));
					s.setTenSanPham(rsSanPham.getString("TenSP"));
					s.setSoLuongDieuChinh(rsSanPham.getString("soluongdieuchinh"));
					s.setSoLo(rsSanPham.getString("SOLO")); 
					sanphamList.add(s);
				}
				rsSanPham.close();
			}
			catch (Exception e)
			{
				System.out.println("ex "+query);
				e.printStackTrace();
			}
	}
	
	public void ViewToUpdate()
	{
		CreateRsKho();
		beanDctktt();
		 
	}
	
	public void display()
	{
		CreateRsKho();
		beanDctktt();
	 
	}
	
	// Chot
	public boolean Approve()
	{
		String query = "";
		try
		{
			beanDctktt();
	 
			db.getConnection().setAutoCommit(false);
			// Xóa nếu có chi tiết
			
			
		/*	int thangtruoc=0;
			int namtruoc=0;
				//BEGIN LAY THANG KHOA SO THANG TRUOC
			 query="select top 1 NAM,THANGKS as  thang from ERP_KHOASOTHANG order by NAM desc,THANGKS desc ";
			 int thangks=0;
			 int namks=0;
			 ResultSet rs=db.get(query);
			 if(rs.next()){
				 thangks=rs.getInt("thang");
				 namks=rs.getInt("nam");
				if(rs.getFloat("thang") ==12){
					thangtruoc=1;
					namtruoc=  rs.getInt("nam")+1 ;
				}else{
					thangtruoc=  rs.getInt("thang")+1 ;
					namtruoc=rs.getInt("nam");
				}
				rs.close();
			 }
			 int thangkk = Integer.parseInt(this.NgayDieuChinh.substring(5, 7));
			 int namkk=Integer.parseInt(this.NgayDieuChinh.substring(0, 4));
			 boolean thangkskytruoc=false;
			 if((thangkk== thangtruoc &&  namtruoc ==namkk) || (thangkk== thangks &&  namtruoc ==namks)  ){
				 
				 if(thangkk== thangks &&  namtruoc ==namks){
					 //vào trường hợp tháng khóa sổ kỳ trước
					 thangkskytruoc=true;
				 }
				 // trường hợp đúng
			 }else{
				 	db.update("rollback");
					this.Msg = "Chỉ làm điều chỉnh của tháng trong kỳ ,hoặc của kỳ trước. Tháng khóa sổ hiện tại:  " +namks+"/"+thangks ;
					db.getConnection().rollback();
					return false;
			 }*/
			  
			query=" delete ERP_KIEMKHO_SP_CHITIET where kiemkho_fk="+this.ID;
			if ( !db.update(query))
			{
				db.update("rollback");
				this.Msg = "1.Không thể cập nhật : " + query;
				db.getConnection().rollback();
				return false;
			}
			//kiểm tra số lượng cập nhật có bị nhỏ hơn so với số lượng avai của tồn hiện tại ko
			
			query=" SELECT  SP.MA,SP.TEN , KKSP.SOLUONGDIEUCHINH    as SOLUONGDC,KHO.AVAILABLE ,ISNULL(KHO.GIATON,0) as GIATON  " +
				  " FROM ERP_KIEMKHO_SANPHAM KKSP "+ 
				  " INNER JOIN ERP_KIEMKHO KK ON KK.PK_SEQ=KKSP.KIEMKHO_FK "+
				  " INNER JOIN ERP_KHOTT_SANPHAM KHO ON KHO.SANPHAM_FK=KKSP.SANPHAM_FK AND KHO.KHOTT_FK=KK.KHOTT_FK "+
				  " INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=KHO.SANPHAM_FK "+
				  " WHERE KIEMKHO_FK="+this.ID+" AND ( KKSP.SOLUONGDIEUCHINH  + KHO.AVAILABLE)  < 0 ";
			
			ResultSet rscheck=db.get(query);
			String str="";
			if (rscheck != null)
			{
				while (rscheck.next()){
					str=str+ rscheck.getString("MA")+" Số lượng điều chỉnh: "+rscheck.getString("SOLUONGDC")+" Số lượng tồn hiện tại:"+rscheck.getString("AVAILABLE") + "\n";
				}
				rscheck.close();
			}
			
			if(str.length()>0 ){
				db.update("rollback");
				this.Msg = "1.Vui lòng kiểm tra lại số lượng điều chỉnh kiểm kê của các sản phẩm sau,vì số lượng tồn hiện tại không còn đủ để trừ kho:"+str  ;
				db.getConnection().rollback();
				return false;
			}
			 

			
			Utility_Kho util_kho=new Utility_Kho();
			
			query=" SELECT KHO.KHOTT_FK,KHO.SANPHAM_FK, SP.MA,SP.TEN , KKSP.SOLUONGDIEUCHINH   , KHO.AVAILABLE FROM ERP_KIEMKHO_SANPHAM KKSP "+ 
				  " INNER JOIN ERP_KIEMKHO KK ON KK.PK_SEQ=KKSP.KIEMKHO_FK "+
				  " INNER JOIN ERP_KHOTT_SANPHAM KHO ON KHO.SANPHAM_FK=KKSP.SANPHAM_FK AND KHO.KHOTT_FK=KK.KHOTT_FK "+
				  " INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=KHO.SANPHAM_FK "+
				  " WHERE KIEMKHO_FK="+this.ID +" and ISNULL(KKSP.SOLUONGDIEUCHINH,0) <> 0 " ; 
			ResultSet  rs=db.get(query);
			while(rs.next()){
				double soluongdc= rs.getDouble("SOLUONGDIEUCHINH") ;
				String khott_fk=rs.getString("KHOTT_FK");
				String spId=rs.getString("SANPHAM_FK");
				
				if(soluongdc!=0){
					//nếu có điều chỉnh mới cập nhật kho hiện tại.
					
					str= util_kho.Update_Kho_Sp_Check_TonKhoNgay(db, khott_fk, spId, soluongdc, 0, soluongdc, 0,this.NgayDieuChinh);
					if(str.length() >0){
						db.update("rollback");
						this.Msg = str;
						db.getConnection().rollback();
						return false;
					}
					
					//nếu ngày điều chỉnh rời vào tháng trước khóa sổ thì phải cập nhật lại tồn kho tháng.
					
					/*if(thangkskytruoc){
						query=  " update  ERP_TONKHOTHANG set soluong=soluong+"+soluongdc+",available=available"+soluongdc+"" +
								" where THANG="+thangks+" and NAM="+namks+" and SANPHAM_FK="+spId+" and KHOTT_FK= "+khott_fk;
						if(db.updateReturnInt(query)!=1){
							db.update("rollback");
							this.Msg = "Không thể cập nhật: "+query;
							db.getConnection().rollback();
							return false;
						}
					}*/
					//điều chỉnh kho chi tiết
					
					
					
					if(soluongdc >0){
						// điều chỉnh tăng vào lô có hạn sử dụng gần nhất
						
						query=" select c.KHOTT_FK,c.SANPHAM_FK ,C.SOLO,C.SOLUONG,c.AVAILABLE,C.NGAYHETHAN,NGAYBATDAU,c.KHUVUCKHO_FK from ERP_KHOTT_SP_CHITIET c where KHOTT_FK="+khott_fk+" and sanpham_fk="+spId+" order by ngayhethan DESC ,NGAYBATDAU DESC  ";
						ResultSet rsct=db.get(query);
						
						if(rsct.next()){
							query=" INSERT INTO ERP_KIEMKHO_SP_CHITIET(KIEMKHO_FK,SANPHAM_FK,SOLUONGDIEUCHINH,SOLO,NGAYBATDAU,KHUVUCKHO_FK ) VALUES " +
								  " ("+this.ID+","+spId+","+soluongdc+",'"+rsct.getString("solo")+"','"+rsct.getString("NGAYBATDAU")+"',"+rsct.getString("KHUVUCKHO_FK")+") ";
							if(db.updateReturnInt(query)!=1){
								db.update("rollback");
								this.Msg = "Không thể cập nhật: "+query;
								db.getConnection().rollback();
								return false;
							}
							
							
							str= util_kho.Update_Kho_Sp_Chitiet(db, khott_fk, spId, soluongdc, 0, soluongdc, 0, rsct.getString("solo"), "", rsct.getString("KHUVUCKHO_FK"), rsct.getString("NGAYBATDAU"));
							if(str.length() >0){
								db.update("rollback");
								this.Msg = str;
								db.getConnection().rollback();
								return false;
							}
						}else{
							// không có thì tạo ra 1 lô,nếu chưa có kho chi tiết.
							query="SELECT PK_SEQ FROM ERP_KHUVUCKHO WHERE KHOTT_FK="+khott_fk;
							ResultSet rskho=db.get(query);
							
							String khuvucid="";
							if (rskho != null)
							{
								if(rskho.next()){
									khuvucid=rskho.getString("pk_seq");
								}
								rskho.close();
							}
							
							str= util_kho.Update_Kho_Sp_Chitiet(db, khott_fk, spId, soluongdc, 0, soluongdc, 0, "LoKK_"+this.NgayDieuChinh, "",khuvucid, "");
							if(str.length() >0){
								db.update("rollback");
								this.Msg = str;
								db.getConnection().rollback();
								return false;
							}
							
						}
						rsct.close();
						
					}else{
						query=" select c.KHOTT_FK,c.SANPHAM_FK ,C.SOLO,C.SOLUONG,c.AVAILABLE,C.NGAYHETHAN,NGAYBATDAU, " +
							  " c.KHUVUCKHO_FK from ERP_KHOTT_SP_CHITIET c " +
							  " where KHOTT_FK="+khott_fk+" and sanpham_fk="+spId+" order by ngayhethan ASC ,NGAYBATDAU ASC ";
						 System.out.println("du lieu : "+query);
						ResultSet rsct=db.get(query);
						 //điều chỉnh giảm
						double soluongdtct=soluongdc * (-1);
							System.out.println("soluong dc: "+soluongdc);
						while( rsct.next() && soluongdtct >0){
							double soluongctdc=0;
							if(rsct.getDouble("AVAILABLE")>soluongdtct ){
								soluongctdc=soluongdtct;
								soluongdtct=0;
								 
								
							}else{
								soluongctdc=rsct.getDouble("AVAILABLE");
								soluongdtct=soluongdtct-rsct.getDouble("AVAILABLE");
							}
							System.out.println("soluongctdc : "+soluongctdc);
							
							soluongctdc=soluongctdc*(-1);
							query=" INSERT INTO ERP_KIEMKHO_SP_CHITIET(KIEMKHO_FK,SANPHAM_FK,SOLUONGDIEUCHINH,SOLO,NGAYBATDAU,KHUVUCKHO_FK ) VALUES " +
							  " ("+this.ID+","+spId+","+soluongctdc+",'"+rsct.getString("solo")+"','"+rsct.getString("NGAYBATDAU")+"',"+rsct.getString("KHUVUCKHO_FK")+") ";
							if(db.updateReturnInt(query)!=1){
								db.update("rollback");
								this.Msg = "Không thể cập nhật: "+query;
								db.getConnection().rollback();
								return false;
							}
								
							
							str= util_kho.Update_Kho_Sp_Chitiet(db, khott_fk, spId, soluongctdc, 0, soluongctdc, 0, rsct.getString("solo"), "", rsct.getString("KHUVUCKHO_FK"), rsct.getString("NGAYBATDAU"));
							if(str.length() >0){
								db.update("rollback");
								this.Msg = str;
								db.getConnection().rollback();
								return false;
							}
						}
					}
					
				} 
				
			}
			rs.close();
			
			
			
			//end THỰC HIỆN CẬP NHẬT KHO
			
			query=  " UPDATE KKSP "+
					" SET KKSP.DONGIA=KHO.GIATON "+
					" FROM ERP_KIEMKHO_SANPHAM KKSP "+ 
					" INNER JOIN ERP_KIEMKHO KK ON KK.PK_SEQ=KKSP.KIEMKHO_FK "+ 
					" INNER JOIN ERP_KHOTT_SANPHAM KHO ON KHO.SANPHAM_FK=KKSP.SANPHAM_FK AND KHO.KHOTT_FK=KK.KHOTT_FK "+ 
					" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=KHO.SANPHAM_FK  "+
					" WHERE KIEMKHO_FK="+this.ID+" AND  ISNULL(KKSP.SOLUONGDIEUCHINH,0) <> 0 ";
			
			if(!db.update(query)){
				db.update("rollback");
				this.Msg="Không cập nhật được dữ liệu : "+ query;
				return false;
			}
			
			query="update erp_kiemkho set  trangthai=1 where pk_seq="+this.ID + " and trangthai=0" ;
			if(db.updateReturnInt(query)!=1){
				db.update("rollback");
				this.Msg="Không cập nhật được dữ liệu : "+ query;
				return false;
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}
		catch (Exception e)
		{
			db.update("rollback");
			e.printStackTrace();
			this.Msg = "Lỗi :" + e.getMessage();
			return false;
		}
		return true;
	}// End Method
	
	private String getNgayCuoiThang(String _nam, String _thang) 
	{
		int songay = 0;
		
		int thang = Integer.parseInt(_nam);
		int nam = Integer.parseInt(_thang);;
		
		switch ( thang ) { 
		    case 1: 
		    case 3: 
		    case 5: 
		    case 7: 
		    case 8: 
		    case 10: 
		    case 12: 
		        songay    = 31; 
		        break; 
		    case 4: 
		    case 6: 
		    case 9: 
		    case 11: 
		    	songay    = 30; 
		        break; 
		    case 2: 
		        if( nam % 100 != 0 && nam % 4 == 0 ) { 
		            songay    = 29; 
		        } else { 
		            songay    = 28; 
		        } 
		        break; 
		    default: songay    = 0; 
		}
		
		return _nam + "-" + _thang + "-" + ( songay < 10 ? ( "0" + songay ) : songay );
	}

	public boolean CheckNumerOrNot(String number)
	{
		if (number.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+"))
			return true;
		return false;
	}
	
	public boolean CheckValid(IErpKiemKho_SanPham o)
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
			//System.out.println("Booked hien tai " + Booked);
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
			if (this.rsLoaisanpham != null)
				this.rsLoaisanpham.close();
			if (this.rsMalonsanpham != null)
				this.rsMalonsanpham.close();
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

	@Override
	public String getnam() {
		
		return this.nam;
	}

	@Override
	public void setNam(String _nam) {
		
		this.nam=_nam;
	}

	@Override
	public String getthang() {
		
		return this.thang;
	}

	@Override
	public void setthang(String _thang) {
		
		this.thang=_thang;
	}

	@Override
	public void initThangNam() 
	{
 
	}

	@Override
	public Hashtable<String, String> getSanphamKho(String spid_str) {
		
		String query="delete  sanpham_tmp ";
		db.update(query);
		  query = " insert into sanpham_tmp select pk_seq from erp_sanpham where 1 = 1  and ma in ("+spid_str+")";
		db.update(query);
		
		 String[] param = new String[3];
		    param[0] =this.KhoTT_FK;
		    param[1] =this.NgayDieuChinh;
		    param[2] =this.NgayDieuChinh;
		    NumberFormat formatter = new DecimalFormat("#,###,###.######");
		    
		    ResultSet   tongHopNXT = db.getRsByPro("REPORT_XUATNHAPTON_TT", param);
		Hashtable<String, String> htp=new Hashtable<String, String>();
	  
		double tondau = 0;
		double soluongnhap = 0;
		double soluongxuat = 0;
			try
			{
				while (tongHopNXT.next())
				{
					tondau = tongHopNXT.getDouble("TONDAUKY");
					soluongnhap = tongHopNXT.getDouble("TONGNHAP");
					soluongxuat = tongHopNXT.getDouble("TONGXUAT");
					htp.put(tongHopNXT.getString("SPMA"),formatter.format(tondau+soluongnhap-soluongxuat));
				}
				tongHopNXT.close();
			}
			catch (Exception e)
			{
				//System.out.println("ex "+query);
				e.printStackTrace();
			}
 		return htp;
	}
}
