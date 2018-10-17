package geso.traphaco.erp.beans.nhapkho.giay.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.beans.nhapkho.giay.IErpNhapKhoLsx;
import geso.traphaco.erp.beans.nhapkho.giay.IErpNhapkho;
import geso.traphaco.erp.beans.nhapkho.giay.ISanpham;
import geso.traphaco.erp.db.sql.dbutils;

public class ErpNhapKhoLsx implements IErpNhapKhoLsx
{

	String lsxId, congdoanId, congtyId, userId, khoId, nhamayId, trangthai,msg,ndnId,IsNhapBTP_Loi="0";
	List<IErpNhapkho> lstNhapKho;
	String Ngaynhapkho="";
	dbutils db;
	private String IsQLKhuvuc;
	String LoaisanphamId;
	String IsLsxCongNghe;
	ResultSet RsLoaiSp;
	String KoCokiemdinh;
	String soluongsanxuat;
	String donvitinh;
	ResultSet RsBTP;
	ResultSet RsThupham;
	String BtpID="";
	String ThuphamID="";
	String BomID;
	ResultSet RsKhoNhanTP;
	String KhonhanTP;
	
	public ErpNhapKhoLsx()
	{
		this.lsxId = "";
		this.congtyId = "";
		this.congdoanId = "";
		this.userId = "";
		this.nhamayId = "";
		this.trangthai = "";
		this.msg="";
		BtpID="";
		this.BomID="";
		this.khoId="";
		this.ndnId="";
		this.donvitinh="";
		this.soluongsanxuat="";
		this.KoCokiemdinh="";
		this.Ngaynhapkho="";
		this.IsNhapBTP_Loi="0";
		this.IsQLKhuvuc = "0";
		ThuphamID="";
		this.LoaisanphamId="";
		
		this.IsLsxCongNghe="";
		
		db = new dbutils();
		lstNhapKho = new ArrayList<IErpNhapkho>();
	}

	public String getLsxId()
	{

		return this.lsxId;
	}

	public void setLsxId(String lsxId)
	{
		this.lsxId = lsxId;
	}

	public String getCongDoanId()
	{

		return this.congdoanId;
	}

	public void setCongDoanId(String congdoanId)
	{
		this.congdoanId = congdoanId;
	}

	public String getCongTyId()
	{

		return this.congtyId;
	}

	public void setCongTyId(String congtyId)
	{
		this.congtyId = congtyId;
	}

	public String getUserId()
	{

		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getNhaMayId()
	{

		return this.nhamayId;
	}

	public void setNhaMayId(String nhamayId)
	{
		this.nhamayId = nhamayId;

	}

	public List<IErpNhapkho> getListNhapKho()
	{

		return this.lstNhapKho;
	}

	public void setListNhapKho(List<IErpNhapkho> lstNhapkho)
	{
		this.lstNhapKho = lstNhapkho;
	}

	public String getTrangThai()
	{

		return this.trangthai;
	}
	
	public void setTrangThai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	public void initNhapKhoLsx()
	{
		
		
		// Lay ra kho san xuat cua lenh san xuat de tru dung kho
		String query =  "  select khosanxuat_fk,kho.QUANLYBIN, lsx.soluong,ISNULL(ISLSXCONGNGHE,0) AS ISLSXCONGNGHE " +
						"  from Erp_LenhSanXuat_Giay lsx inner join erp_khott kho on kho.pk_seq=lsx.khosanxuat_fk where lsx.PK_SEQ='" + this.lsxId + "' " ;
						 
			ResultSet rs = db.get(query);
	 
				try
				{
					if (rs.next())
					{
						this.khoId = rs.getString("khosanxuat_fk");
					this.IsQLKhuvuc = rs.getString("QUANLYBIN");
					this.IsLsxCongNghe=rs.getString("ISLSXCONGNGHE");
					this.soluongsanxuat=rs.getString("soluong");
					if(this.IsQLKhuvuc.trim().equals("1")){
						query = "select * from ERP_KHUVUCKHO where KHOTT_FK = '"+this.khoId+"' and TRANGTHAI = 1";
						rs = db.get(query);
						if(rs.next()){
							
						}
						else{
							this.IsQLKhuvuc = "0";
							this.msg = "Kho nhập có quản lý khu vực, nhưng không có khu vực nào hoạt động";
						}
					}
				}
				rs.close();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			//LAY DVTINH 
			query=  " select DVDL.DIENGIAI as dvt from ERP_DANHMUCVATTU BOM "+
					" inner join DONVIDOLUONG DVDL on BOM.DVDL_FK = DVDL.PK_SEQ "+
					" WHERE BOM.PK_SEQ ="+this.BomID ;
			System.out.println("LAY DVT NHAPKHO: "+query);
			rs=db.get(query);
			try
			{
				if (rs.next())
				{
					this.donvitinh=rs.getString("dvt");
				}rs.close();
			} catch (Exception e)
			{
				e.printStackTrace();
			}
				
				
			query=" SELECT distinct BTP.MA + ' - '+BTP.TEN as ten,BTP.PK_SEQ FROM ERP_SANPHAM SP "+
			" INNER JOIN ERP_SANPHAM_BTP SPBTP ON  SP.PK_SEQ=SPBTP.SP_FK "+
			" INNER JOIN ERP_SANPHAM BTP ON BTP.PK_SEQ=SPBTP.BTP_FK "+
			" WHERE SP.PK_SEQ in (select sanpham_fk from ERP_LENHSANXUAT_SANPHAM where lenhsanxuat_fk= "+this.lsxId+") " ;
			this.RsBTP=db.get(query);
		 // lay DON VI KINH DOANH CUA LENH SAN XUAT
			query=" SELECT DVKD_FK FROM ERP_SANPHAM SP INNER JOIN ERP_LENHSANXUAT_SANPHAM LSXSP ON LSXSP.SANPHAM_FK=SP.PK_SEQ "+
				  " WHERE LSXSP.LENHSANXUAT_FK="+this.lsxId ;
			rs=db.get(query);
			String dvkdid="";
			try
			{
				if (rs.next())
				{
					dvkdid=rs.getString("DVKD_FK");
				}rs.close();
			} catch (Exception e1)
			{
				e1.printStackTrace();
			}
	 
		// Kiem tra xem cong doan san xuat nay da hoan tat hay chua
		// New da hoan tat thi cho nhap kho khong cho phep chinh sua thong tin
		int hoantat = 0;
		query = "select TINHTRANG From Erp_LenhSanXuat_CongDoan_Giay where CongDoan_FK='" + this.congdoanId +
			"' and LenhSanXuat_FK='" + this.lsxId + "'";
		rs = this.db.get(query);
		 
			try
			{
				while (rs.next())
				{
					hoantat = rs.getInt("TINHTRANG");
				}rs.close();
			} catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		 
		// Hoan tat
		if (hoantat == 1)
			this.trangthai = "1";
		
		boolean IscdCuoi=false;
		query=" select * from ERP_LENHSANXUAT_CONGDOAN_GIAY where   LENHSANXUAT_FK='"+this.lsxId+"' and congdoan_fk="+this.congdoanId+"  and THUTU=( select MAX(thutu) from ERP_LENHSANXUAT_CONGDOAN_GIAY where LENHSANXUAT_FK='"+this.lsxId+"' )  ";
		//System.out.println("Cong Doan Cuôi : "+query);
		ResultSet rscheck1=db.get(query);
		try{
		if(rscheck1.next()){
				IscdCuoi=true;
		}
		rscheck1.close();
		}catch(Exception er){
			
		}
		
		
		query=" SELECT SOLENHSANXUAT,TRANGTHAI ,CONGDOAN_FK "+
			  " FROM ERP_NHAPKHO "+
			  " WHERE TRANGTHAI=0 AND SOLENHSANXUAT='"+this.lsxId+"'and CongDoan_FK='"+this.congdoanId+"' "+
			  " GROUP BY SOLENHSANXUAT,TRANGTHAI,CONGDOAN_FK "+
		      " HAVING COUNT(distinct TrangThai) > 0";
		//System.out.println("___Kiem tra tinh trang cua cong doan da hoa tat hay chua____"+query);
		ResultSet rsCheck=this.db.get(query);
 
		query = " select pk_Seq  as NhapKhoId, TrangThai from " + " erp_nhapkho where SOLENHSANXUAT='" + this.lsxId +"' and CONGDOAN_FK='" + this.congdoanId + "' " + " and TRANGTHAI <2";
		try
		{
			//NẾU công đoạn không hoàn tất và đã có nhập kho hay chưa
			if(hoantat!=1 && ! rsCheck.next())
				query+=" union all " +
						" select null  as NhapKhoId, 0 as TrangThai";
		} catch (Exception e2)
		{
			e2.printStackTrace();
		}
		
		rs = this.db.getScrol(query);
	 
		try
		{
				rs.beforeFirst();
				while (rs.next())
				{
					IErpNhapkho e = null;
					//System.out.println("NhapkhoId___"+rs.getString("NhapKhoId"));
					e = new ErpNhapkho();
					e.setId(rs.getString("NhapKhoId")== null ? "" : rs.getString("NhapKhoId"));
					e.setCongtyId(this.congtyId);
					e.setKhoId(this.khoId);
					e.setCongDoanId(this.congdoanId);
					e.setSoLenhsx(this.lsxId);
					e.setUserId(this.userId);
					e.setNdnId("100047");
					e.setTrangthai(rs.getString("TrangThai"));
					if(rs.getString("NhapKhoId")!=null){
						 
								 
									query=	"	SELECT a.PK_SEQ as LsxId,e.PK_SEQ as NhapKhoId,b.CONGDOAN_FK as CongDoanId,d.PK_SEQ as SanPhamId,d.MA as MaSanPham,d.TEN  as TenSanPham,ISNULL(f.SOLO,'')as SoLo, ISNULL(F.KHUVUCKHO_FK,0)AS KHUVUCKHO_FK," +
											"	ISNULL(f.SOLUONGNHAN,'0')as SoLuongNhan,isnull(SOLUONGLAYMAU_TRUOCQD,0) as soluonglaymau ,isnull(f.SOLUONGTRUOCQUYDOI,0) as SoLuongNhap,c.SOLUONG as SoLuongSX,isnull(f.NgaySanXuat,'')as NgaySanXuat, " +
											" 	isnull(f.NgayNhapKho,'') NgayNhapKho,f.ngayhethan ,'100000' as TienTe_FK ,f.DVDL_FK, dvdl.donvi ,dvdl.pk_seq as dvdl_mau_fk  " +
											"	FROM " +
											"	ERP_LENHSANXUAT_GIAY a inner join ERP_LENHSANXUAT_CONGDOAN_GIAY b on b.LENHSANXUAT_FK=a.PK_SEQ" +
											"	inner join ERP_LenhSanXuat_SanPham c on c.LenhSanXuat_FK=a.PK_SEQ" +
											"	inner join ERP_NHAPKHO e on e.SOLENHSANXUAT=a.PK_SEQ" +
											"	inner join ERP_NHAPKHO_SANPHAM f on f.SONHAPKHO_FK=e.PK_SEQ  " +
											"	inner join ERP_SANPHAM d on   f.sanpham_fk=d.pk_seq " +
											"   left join donvidoluong dvdl on dvdl.pk_seq=f.dvdl_mau_fk    " +
											"   where a.pk_Seq='" +this.lsxId +"' and b.Congdoan_fk='" +this.congdoanId +"' and  e.PK_SEQ='"+rs.getString("NhapKhoId")+"' " ;
								 
						 
					}
					
					//Neu nhap kho  nay da chot het nhung cond doan chua hoan tat
						if(hoantat!=1 && rs.getString("NhapKhoId")==null)
						{
									if(LoaisanphamId.equals("")){
										LoaisanphamId="0";
									}
							
										if(LoaisanphamId.equals("0")){
											query=  "   select a.PK_SEQ as LsxId,'' as NhapKhoId,b.CongDoan_FK as CongDoanId,d.PK_SEQ as SanPhamId,d.MA as MaSanPham,d.TEN  as TenSanPham,ISNULL(A.SOLO,'')  as SoLo, 0 AS KHUVUCKHO_FK, " +
													"	C.SOLUONG as SoLuongNhan,0 as soluonglaymau, C.SOLUONG as SoLuongNhap,c.SOLUONG as SoLuongSX,'' as NgaySanXuat,'' as NgayNhapKho,'' as ngayhethan,'100000' as TienTe_FK ,dmvt.DVDL_FK , " +
													"   dvdl.donvi,dvdl.pk_seq as dvdl_mau_fk " +
													"	FROM " +
													"	ERP_LENHSANXUAT_GIAY a inner join ERP_LENHSANXUAT_CONGDOAN_GIAY b on b.LENHSANXUAT_FK=a.PK_SEQ" +
													"	inner join ERP_LenhSanXuat_SanPham c on c.LenhSanXuat_FK=a.PK_SEQ" +
													"	inner join ERP_SANPHAM d on d.PK_SEQ=c.SanPham_FK  " +
													"   inner join erp_danhmucvattu dmvt on dmvt.pk_seq=c.DanhMucVT_FK  " +
													"   inner join donvidoluong dvdl on dvdl.pk_seq=dmvt.dvdl_fk  " +
													"   where a.pk_Seq='" +this.lsxId +"' and b.Congdoan_fk='" +this.congdoanId +"' " +
													"   and b.THUTU=( select MAX(thutu) from ERP_LENHSANXUAT_CONGDOAN_GIAY where LENHSANXUAT_FK='"+this.lsxId+"' )  " ;
											
										}else if(LoaisanphamId.equals("1")){
											query=  "  SELECT A.PK_SEQ AS LSXID,'' AS NHAPKHOID,B.CONGDOAN_FK AS CONGDOANID,BTP.PK_SEQ AS SANPHAMID,BTP.MA AS MASANPHAM,BTP.TEN  AS TENSANPHAM,ISNULL(A.SOLO,'') AS SOLO, 0 AS KHUVUCKHO_FK, "+  
													"  C.SOLUONG AS SOLUONGNHAN,0 as soluonglaymau, C.SOLUONG AS SOLUONGNHAP,C.SOLUONG AS SOLUONGSX,'' AS NGAYSANXUAT,'' AS NGAYNHAPKHO,'' as ngayhethan,'100000' AS TIENTE_FK ,BTP.DVDL_FK ," +
													"  dvdl.donvi ,dvdl.pk_seq as dvdl_mau_fk "+ 
													"  FROM   "+
													"  ERP_LENHSANXUAT_GIAY A INNER JOIN ERP_LENHSANXUAT_CONGDOAN_GIAY B ON B.LENHSANXUAT_FK=A.PK_SEQ "+ 
													"  INNER JOIN ERP_LENHSANXUAT_SANPHAM C ON C.LENHSANXUAT_FK=A.PK_SEQ "+ 
													"  INNER JOIN ERP_SANPHAM BTP ON BTP.PK_SEQ= "+this.BtpID+ 
													"  inner join donvidoluong dvdl on dvdl.pk_seq=btp.dvdl_fk   " +
													"WHERE A.PK_SEQ="+this.lsxId+" AND B.CONGDOAN_FK="+this.congdoanId+" "+
													"  AND B.THUTU=( SELECT MAX(THUTU)S FROM ERP_LENHSANXUAT_CONGDOAN_GIAY WHERE LENHSANXUAT_FK="+this.lsxId+" ) " ;
										}else if(LoaisanphamId.equals("2")) {
											query=  "   select a.PK_SEQ as LsxId,'' as NhapKhoId,b.CongDoan_FK as CongDoanId,PP.PK_SEQ as SanPhamId,PP.MA as MaSanPham,PP.TEN  as TenSanPham,ISNULL(A.SOLO,'') as SoLo, 0 AS KHUVUCKHO_FK, " +
													"	C.SOLUONG as SoLuongNhan,0 as soluonglaymau, C.SOLUONG as SoLuongNhap,c.SOLUONG as SoLuongSX,'' as NgaySanXuat,'' as NgayNhapKho,'' as ngayhethan,'100000' as TienTe_FK ,pp.DVDL_FK ," +
													"  dvdl.donvi ,dvdl.pk_seq as dvdl_mau_fk" +
													"	FROM " +
													"	ERP_LENHSANXUAT_GIAY a inner join ERP_LENHSANXUAT_CONGDOAN_GIAY b on b.LENHSANXUAT_FK=a.PK_SEQ" +
													"	inner join ERP_LenhSanXuat_SanPham c on c.LenhSanXuat_FK=a.PK_SEQ" +
													"	inner join ERP_SANPHAM d on d.PK_SEQ=c.SanPham_FK  " +
													"   inner join erp_SANPHAM PP ON PP.PK_SEQ= D.PHEPHAM_FK " +
													"   left join donvidoluong dvdl on dvdl.pk_seq=pp.dvdl_fk   " +
													"   where a.pk_Seq='" +this.lsxId +"' and b.Congdoan_fk='" +this.congdoanId +"' " +
													"   and b.THUTU=( select MAX(thutu) from ERP_LENHSANXUAT_CONGDOAN_GIAY where LENHSANXUAT_FK='"+this.lsxId+"' )  " ;

										}else if(LoaisanphamId.equals("3")){
											query=  "  SELECT A.PK_SEQ AS LSXID,'' AS NHAPKHOID,B.CONGDOAN_FK AS CONGDOANID,thupham.PK_SEQ AS SANPHAMID,thupham.MA AS MASANPHAM,thupham.TEN  AS TENSANPHAM, ISNULL(A.SOLO,'') AS SOLO, 0 AS KHUVUCKHO_FK, "+  
													"  C.SOLUONG AS SOLUONGNHAN,0 as soluonglaymau, C.SOLUONG AS SOLUONGNHAP,C.SOLUONG AS SOLUONGSX,'' AS NGAYSANXUAT,'' AS NGAYNHAPKHO,'' as ngayhethan,'100000' AS TIENTE_FK ,thupham.DVDL_FK ," +
													"  dvdl.donvi ,dvdl.pk_seq as dvdl_mau_fk "+ 
													"  FROM   "+
													"  ERP_LENHSANXUAT_GIAY A INNER JOIN ERP_LENHSANXUAT_CONGDOAN_GIAY B ON B.LENHSANXUAT_FK=A.PK_SEQ "+ 
													"  INNER JOIN ERP_LENHSANXUAT_SANPHAM C ON C.LENHSANXUAT_FK=A.PK_SEQ "+ 
													"  INNER JOIN ERP_SANPHAM thupham ON thupham.PK_SEQ= "+this.ThuphamID+ 
													"  inner join donvidoluong dvdl on dvdl.pk_seq=thupham.dvdl_fk   " +
													"  WHERE A.PK_SEQ="+this.lsxId+" AND B.CONGDOAN_FK="+this.congdoanId+" "+
													"  AND B.THUTU=( SELECT MAX(THUTU)S FROM ERP_LENHSANXUAT_CONGDOAN_GIAY WHERE LENHSANXUAT_FK="+this.lsxId+" ) " ;
										}
						 
						}
					ResultSet rsSanPham = this.db.get(query);
					//System.out.println("San pham cua nhap kho 2___"+query);
					List<ISanpham> spList = new ArrayList<ISanpham>();
					 
					try
					{
						while (rsSanPham.next())
						{
							ISanpham s = null;
							s = new Sanpham();
							s.setId(rsSanPham.getString("SanPhamId"));
							s.setMa(rsSanPham.getString("MaSanPham"));
							s.setDiengiai(rsSanPham.getString("TenSanPham"));
							s.setSolo(rsSanPham.getString("SOLO"));
							s.setSoluongnhapkho(rsSanPham.getString("soluongnhap"));
							s.setSoluonglaymau(rsSanPham.getString("soluonglaymau"));
							s.setDvdl_Mau(rsSanPham.getString("donvi"));
							s.setDvdl_Mau_Id(rsSanPham.getString("dvdl_mau_fk"));
							s.setSoluongSx(rsSanPham.getString("SoluongSx"));
							s.setNgaySanXuat(rsSanPham.getString("ngaysanxuat"));
							s.setNgayNhapKho(rsSanPham.getString("ngaynhapkho"));
							s.setNgayhethan(rsSanPham.getString("ngayhethan"));
							
							s.setTiente(rsSanPham.getString("TienTe_FK"));
							s.setKhuvucId(rsSanPham.getString("KHUVUCKHO_FK"));
							s.setDvdlId((rsSanPham.getString("dvdl_fk")==null)?"":rsSanPham.getString("dvdl_fk") );
							s.setKhuvucRs(this.db.get("select * from ERP_KHUVUCKHO where KHOTT_FK = '"+this.khoId+"' and TRANGTHAI = 1"));
							query=" SELECT DV.PK_SEQ,DV.DIENGIAI,DV.DONVI FROM DONVIDOLUONG DV "+
								  " WHERE DV.PK_SEQ IN (SELECT DVDL_FK FROM ERP_SANPHAM SP  WHERE SP.PK_SEQ=  "+rsSanPham.getString("SanPhamId")+
								  " UNION SELECT DVDL2_FK FROM QUYCACH WHERE SANPHAM_FK= "+rsSanPham.getString("SanPhamId")+")";
							ResultSet rsdvdl=this.db.get(query);
							s.setRsDvld(rsdvdl);
							spList.add(s);
						}
						rsSanPham.close();
					} catch (SQLException e1)
					{
						e1.printStackTrace();
					}
					
					e.setSpList(spList);
					this.lstNhapKho.add(e);
				 
				
				}
				rs.close();
			 
		}
		 catch (Exception e)
		{
			e.printStackTrace();
		}
		 this.CreateRs();
	}

	
	public String getMsg()
	{
	
		return this.msg;
	}

	
	public void setMsg(String msg)
	{
	
		this.msg=msg;
	}

	
	public String getKhoId()
	{
		
		return this.khoId;
	}

	
	public void setKhoId(String khoId)
	{
		this.khoId=khoId;
		
	}

	
	public boolean createNhapKhoLSX()
	{
		
		for(int i=0;i<this.lstNhapKho.size();i++)
		{
			IErpNhapkho e = this.lstNhapKho.get(i);
			
			e.setIsQLKV(this.IsQLKhuvuc);
			e.setKhoNhanTP(this.KhonhanTP);
			if(e.getId()!= null && e.getId().trim().length()<=0)
			{
				
				if(!e.createNhapKhoLSX())
				{
					e.setId("");
					this.setMsg(e.getmsg());
					return false;
				}
			}
		}
		
		return true;
	}

	
	public String getNdnId()
	{
		
		return this.ndnId;
	}

	
	public void setNdnId(String ndnId)
	{
		this.ndnId=ndnId;
		
	}

	
	public String getNgaynhapkho() {
	
		return this.Ngaynhapkho;
	}

	
	public void setNgaynhapkho(String ngaynhapkho) {
	
		 this.Ngaynhapkho=ngaynhapkho;
	}

	
	public String getIsNhapBTP_Loi() {
	
		return this.IsNhapBTP_Loi;
	}

	
	public void setIsNhapBTP_Loi(String IsNhapBTP_loi) {
	
		this.IsNhapBTP_Loi=IsNhapBTP_loi;
	}

	
	public String getIsQLKV() {
		return this.IsQLKhuvuc;
	}

	
	public void setIsQLKV(String value) {
		this.IsQLKhuvuc = value;
	}

	
	public ResultSet getQuery(String string) {
		return this.db.get(string);
	}

	
	public void shutdown() {
		this.db.shutDown();
	}

	
	public String getLoaisanpham() {
	
		return this.LoaisanphamId;
	}

	
	public void setLoaisanpham(String loaisanpham) {
	
		this.LoaisanphamId=loaisanpham;
	}

	
	public ResultSet getRsLoaisanpham() {
	
		return this.RsLoaiSp;
	}

	
	public void setRsLoaisanpham(ResultSet rs) {
	
		this.RsLoaiSp=rs;
	}

	
	public ResultSet getRsBTP() {
	
		return this.RsBTP;
	}

	
	public void setRsBTP(ResultSet rs) {
	
		this.RsBTP=rs;
	}

	
	public String getBTPId() {
	
		return this.BtpID;
	}

	
	public void setBTPId(String BTPId) {
	
		this.BtpID=BTPId;
	}

	
	public String getIsLsxCongNghe() {
	
		return this.IsLsxCongNghe;
	}

	
	public void setIsLsxCongNghe(String IsLsxCongNghe) {
	
		this.IsLsxCongNghe=IsLsxCongNghe;
	}

	
	public String getKhongkiemdinh() {
	
		return this.KoCokiemdinh;
	}

	
	public void setKhongkiemdinh(String khongkiemdinh) {
	
		this.KoCokiemdinh=khongkiemdinh;
	}

	
	public String getSoLuongSanXuat() {
	
		return this.soluongsanxuat;
	}

	
	public void setSoLuongSanXuat(String soluongsanxuat) {
	
		this.soluongsanxuat=soluongsanxuat;
	}

	
	public String getDonViTinh() {
	
		return this.donvitinh;
	}

	
	public void setDonViTinh(String donvitinh) {
	
		this.donvitinh=donvitinh;
	}

	public String getBomID() {
		return this.BomID;
	}


	public void setBomID(String bomid) {
		this.BomID=bomid;
		
	}

	@Override
	public ResultSet getRsdvdl(String spid) {
		// TODO Auto-generated method stub
		String query=" SELECT DV.PK_SEQ,DV.DIENGIAI,DV.DONVI FROM DONVIDOLUONG DV "+
		  " WHERE DV.PK_SEQ IN (SELECT DVDL_FK FROM ERP_SANPHAM SP  WHERE SP.PK_SEQ=  "+spid+
		  " UNION SELECT DVDL2_FK FROM QUYCACH WHERE SANPHAM_FK= "+spid+")";

		return db.get(query);
	}

	@Override
	public void CreateRs() {
		// TODO Auto-generated method stub
		String query=	" SELECT BTP.MA + ' - '+BTP.TEN as ten,BTP.PK_SEQ FROM ERP_SANPHAM SP "+
						" INNER JOIN ERP_SANPHAM_BTP SPBTP ON  SP.PK_SEQ=SPBTP.SP_FK "+
						" INNER JOIN ERP_SANPHAM BTP ON BTP.PK_SEQ=SPBTP.BTP_FK "+
						" WHERE SP.PK_SEQ in (select sanpham_fk from ERP_LENHSANXUAT_SANPHAM where lenhsanxuat_fk= "+this.lsxId+") " ;
		this.RsBTP=db.get(query);
			query=" SELECT SP.MA + ' - '+SP.TEN AS TEN,SP.PK_SEQ FROM ERP_SANPHAM SP  WHERE SP.LOAISANPHAM_FK=100048";
		 this.RsThupham=db.get(query);
		 
	 	query= " select PK_SEQ,TEN from ERP_KHOTT where   ISKHONHANTP_DAT='1'  ";
		this.RsKhoNhanTP=db.get(query);
		 
	}

	@Override
	public ResultSet getRsThupham() {
		// TODO Auto-generated method stub
		return this.RsThupham;
	}

	@Override
	public void setRsThupham(ResultSet rs) {
		// TODO Auto-generated method stub
		this.RsThupham=rs;
	}

	@Override
	public String getThuphamId() {
		// TODO Auto-generated method stub
		return this.ThuphamID;
	}

	@Override
	public void setThuphamId(String thuphamId) {
		// TODO Auto-generated method stub
		this.ThuphamID=thuphamId;
	}

	@Override
	public void setrsKhoNhanTP(ResultSet rskho) {
		// TODO Auto-generated method stub
		this.RsKhoNhanTP=rskho;
	}

	@Override
	public ResultSet getrsKhoNhanTP() {
		// TODO Auto-generated method stub
		return this.RsKhoNhanTP;
	}

	@Override
	public String getKhoNhanTP() {
		// TODO Auto-generated method stub
		return this.KhonhanTP;
	}

	@Override
	public void setKhoNhanTP(String KhoNhanTP) {
		// TODO Auto-generated method stub
		this.KhonhanTP=KhoNhanTP;
	}
}
