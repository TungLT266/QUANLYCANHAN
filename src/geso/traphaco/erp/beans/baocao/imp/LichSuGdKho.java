package geso.traphaco.erp.beans.baocao.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.baocao.ILichSuGdKho;

public class LichSuGdKho implements ILichSuGdKho
{
	String userId;
	String userTen;

	ResultSet loaiSpRs, loaiCtRs;
	ResultSet spRs;
	ResultSet khoRs;
	ResultSet malonsanphamRs;
	String loaisanphamIds;
	String spIds;
	String khoIds;
	String khoTen;
	String LoaiCtIds;
	String tungay;
	String denngay,Msg;
	String malonsanphamIds;

	dbutils db;

	public LichSuGdKho()
	{
		this.userId = "";
		this.userTen = "";
		this.loaisanphamIds = "";
		this.spIds = "";
		this.khoIds = "";
		this.khoTen = "";
		this.tungay = "";
		this.denngay = "";
		this.LoaiCtIds = "";
		this.Msg="";
		this.malonsanphamIds = "";

		this.db = new dbutils();
	}
	
	
	
	public String getMalonsanphamIds() {
		return malonsanphamIds;
	}



	public void setMalonsanphamIds(String malonsanphamIds) {
		this.malonsanphamIds = malonsanphamIds;
	}



	public ResultSet getMalonsanphamRs() {
		return malonsanphamRs;
	}

	public void setMalonsanphamRs(ResultSet malonsanphamRs) {
		this.malonsanphamRs = malonsanphamRs;
	}

	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getUserTen()
	{
		return this.userTen;
	}

	public void setUserTen(String userTen)
	{
		this.userTen = userTen;
	}

	public ResultSet getLoaiSanPhamRs()
	{
		return this.loaiSpRs;
	}

	public void setLoaiSanPhamRs(ResultSet loaisp)
	{
		this.loaiSpRs = loaisp;
	}

	public String getLoaiSanPhamIds()
	{
		return this.loaisanphamIds;
	}

	public void setLoaiSanPhamIds(String loaispIds)
	{
		this.loaisanphamIds = loaispIds;
	}

	public String getTuNgay()
	{
		return this.tungay;
	}

	public void setTuNgay(String tungay)
	{
		this.tungay = tungay;
	}

	public String getDenNgay()
	{
		return this.denngay;
	}

	public void setDenNgay(String denngay)
	{
		this.denngay = denngay;
	}

	public ResultSet getSanPhamRs()
	{
		return this.spRs;
	}

	public void setSanPhamRs(ResultSet sanpham)
	{
		this.spRs = sanpham;
	}

	public String getSanPhamIds()
	{
		return this.spIds;
	}

	public void setSanPhamIds(String spIds)
	{
		this.spIds = spIds;
	}

	public void createRs()
	{
		this.loaiSpRs = db.get("select pk_seq, ma + ', ' + ten as ten from erp_loaisanpham");

		String query = "select pk_seq, ma, ten from ERP_SanPham where trangthai = '1' ";
		if (this.loaisanphamIds.length() > 0)
		{
			query += "and loaisanpham_fk In( " + this.loaisanphamIds + ")";
		}

		this.spRs = db.get(query);

		query = " select pk_seq, ten + ', ' + diachi as khoTen from erp_khott ";
		// " UNION "+
		// " SELECT '200000',N'Tất cả'";
		this.khoRs = db.get(query);

		query = " Select PK_SEQ,TEN FROM ERP_NOIDUNGNHAP WHERE TRANGTHAI=1 " + " UNION ALL "
			+ " SELECT '200000',N'Điều chỉnh tồn kho' ";
		// if(this.LoaiCtIds.length()>0)

		this.loaiCtRs = this.db.get(query);
		
		query = " Select distinct MA from ERP_SANPHAM ";
		this.malonsanphamRs = this.db.get(query);
		
	}

	public void close()
	{
		this.db.shutDown();

		try
		{
			if (this.loaiSpRs != null)
				this.loaiSpRs.close();

			if (this.spRs != null)
				this.spRs.close();

			if (this.loaiCtRs != null)
				this.loaiCtRs.close();
			
			if (this.malonsanphamRs!= null)
				this.malonsanphamRs.close();


		} catch (SQLException e)
		{
		}

	}

	public ResultSet getKhoRs()
	{
		return this.khoRs;
	}

	public void setKhoRs(ResultSet khoRs)
	{
		this.khoRs = khoRs;
	}

	public String getKhoIds()
	{
		return this.khoIds;
	}

	public void setKhoIds(String khoId)
	{
		this.khoIds = khoId;
	}

	public String getKhoTen()
	{
		return this.khoTen;
	}

	public void setKhoTen(String khoTen)
	{
		this.khoTen = khoTen;
	}

	public ResultSet getLoaiCtRs()
	{

		return this.loaiCtRs;
	}

	public void setLoaiCtRs(ResultSet LoaiCtRs)
	{
		this.loaiCtRs = LoaiCtRs;

	}

	public String getLoaiCtIds()
	{

		return this.LoaiCtIds;
	}

	public void setLoaiCtIds(String LoaiCtIds)
	{
		this.LoaiCtIds = LoaiCtIds;
	}

	public String getQuery()
	{
		String query = 
			"SELECT	B.PREFIX + CAST(B.PK_SEQ AS VARCHAR(20)) AS SOCT,ND.TEN AS LOAICT, B.NGAYCHUYEN AS NGAYCT, B.NGAYCHOT, SP.MA AS MASP,SP.TEN AS TENSP, "+
			"	KHOXUAT.TEN AS MADT,KHOXUAT.TEN+'-'+KHOXUAT.DIACHI  AS TENDT,ISNULL(A.SOLUONGNHAN,0) AS SOLUONG,KHONHAN.TEN+'-'+KHONHAN.DIACHI  AS KHO,NV.TEN AS NGUOITAO, "+
			"	CASE " +
			"		WHEN B.TRANGTHAI =0 THEN N'Chưa chốt' "+
			"		WHEN B.TRANGTHAI=1 THEN N'Đã chốt' "+
			"		WHEN B.TRANGTHAI=2 THEN N'Đã hủy' "+
			"		WHEN B.TRANGTHAI=3 THEN N'Hoàn tất' "+
			"	END TRANGTHAI "+
			"FROM ERP_CHUYENKHO_SANPHAM A INNER JOIN ERP_CHUYENKHO B ON A.CHUYENKHO_FK = B.PK_SEQ "+  
			"	INNER JOIN( SELECT  PK_SEQ,TEN FROM ERP_NOIDUNGNHAP WHERE 1=1 ";
			if(this.LoaiCtIds.length()>0)
				query+=" AND PK_SEQ IN ( " +this.LoaiCtIds+" ) ";
			query+=
			" ) ND ON ND.PK_SEQ=B.NOIDUNGXUAT_FK "+
			" 	INNER JOIN ( SELECT PK_SEQ,MA,TEN from ERP_SanPham  WHERE 1=1 " ;
			if(this.malonsanphamIds.length()>0)
				query+=" AND  MA IN ( "+this.malonsanphamIds+" ) " ;
			if(this.loaisanphamIds.length()>0)
				query+=" AND  LOAISANPHAM_FK IN ( "+this.loaisanphamIds+" ) " ;
			if(this.spIds.length()>0)
				query+=" AND PK_SEQ IN ( " +this.spIds+"  )";
			query+=" ) SP ON SP.PK_SEQ=A.SANPHAM_FK "+
			" 	INNER JOIN ERP_KHOTT KHOXUAT ON KHOXUAT.PK_SEQ=B.KHOXUAT_FK "+
			" 	INNER JOIN NHANVIEN NV ON NV.PK_SEQ=B.NGUOITAO "+
			" 	INNER JOIN ERP_KHOTT KHONHAN ON KHONHAN.PK_SEQ=B.KHONHAN_FK " +
			"WHERE  B.NGAYCHOT>='"+this.tungay+"' AND B.NGAYCHOT<='"+this.denngay+"' "  ;
			if(this.khoIds.length()>0 )
				query+=" AND B.KHOXUAT_FK ="+this.khoIds+" ";
			query+=
			" UNION ALL  "+
					" SELECT B.PREFIX + CAST(B.PK_SEQ AS VARCHAR(20)) AS SOCT,C.TEN LOAICT,B.NGAYXUAT AS NGAYCT,ISNULL( B.NGAYCHOT,'')AS NGAYCHOT,"+
				" D.MA AS MASP ,D.TEN TENSP, "+ 
				" CASE "+
				"	WHEN B.TRAHANGNCC_FK IS NOT NULL THEN NCC.MA "+
				"	WHEN B.DONDATHANG_FK IS NOT NULL THEN NPP.MA "+
				" END MADT, "+
				" CASE "+
				"	WHEN B.TRAHANGNCC_FK IS NOT NULL THEN NCC.TEN "+
				"	WHEN B.DONDATHANG_FK IS NOT NULL THEN NPP.TEN "+
				" END TENDT,(-1)*A.SOLUONG,KHO.TEN+'-'+KHO.DIACHI AS KHO,NV.TEN AS NGUOITAO, "+
				" CASE "+ 
				"	WHEN B.TRANGTHAI=0 THEN N'Chưa chốt' "+
				"	WHEN B.TRANGTHAI=1 THEN N'Đã chốt' "+
				"	WHEN B.TRANGTHAI=2 THEN N'Đã xoá' "+
				"	WHEN B.TRANGTHAI=3 THEN N'Đã hủy' "+
				"	END AS TRANGTHAI "+
			" FROM ERP_XUATKHO_SANPHAM A "+ 
			"	INNER JOIN ERP_XUATKHO B ON A.XUATKHO_FK = B.PK_SEQ "+    
			"	INNER JOIN ( SELECT  PK_SEQ,TEN FROM ERP_NOIDUNGNHAP WHERE 1=1 " ;
			if(this.LoaiCtIds.length()>0)
				query+=" AND PK_SEQ IN ( " +this.LoaiCtIds+" ) ";
			query+=
			") C ON C.PK_SEQ=B.NOIDUNGXUAT "+
			"	INNER JOIN ( SELECT PK_SEQ,MA,TEN from ERP_SanPham  WHERE 1=1 " ;
			if(this.malonsanphamIds.length()>0)
				query+=" AND  MA IN ( "+this.malonsanphamIds+" ) " ;
			if(this.loaisanphamIds.length()>0)
				query+=" AND  LOAISANPHAM_FK IN ( "+this.loaisanphamIds+" ) " ;
			if(this.spIds.length()>0)
				query+=" AND PK_SEQ IN ( " +this.spIds+"  )";
			query+=	
			" ) D ON D.PK_SEQ=A.SANPHAM_FK "+
			"	LEFT JOIN ERP_MUAHANG MH ON MH.PK_SEQ=B.TRAHANGNCC_FK "+
			"	LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ=MH.NHACUNGCAP_FK " +
			"	LEFT JOIN ERP_DONDATHANG DDH ON DDH.PK_SEQ=B.DONDATHANG_FK "+
			"	LEFT JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=DDH.NPP_FK "+
			"	INNER JOIN ERP_KHOTT KHO ON KHO.PK_SEQ=B.KHO_FK "+
			" 	INNER JOIN NHANVIEN NV ON NV.PK_SEQ=B.NGUOITAO " +
			" WHERE  B.NGAYCHOT>='"+this.tungay+"' AND B.NGAYCHOT<='"+this.denngay+"' " ;
			if(this.khoIds.length()>0 )
				query+=" AND B.KHO_FK ="+this.khoIds+" ";
			query+=
		" UNION ALL "+
		"	SELECT A.PREFIX + CAST (A.PK_SEQ AS VARCHAR(20)) AS SOCT,B.TEN AS LOAICT,A.NGAYNHAPKHO AS NGAYCT,ISNULL(A.NGAYCHOT, '') AS NGAYCHOT, "+
		"		J.MA AS MASP,J.TEN AS TENSP, "+
		"	CASE "+
		"	 WHEN A.SODONTRAHANG IS NOT NULL THEN NPP.MA "+ 
		"	 WHEN A.SOPHIEUNHAPHANG IS NOT NULL THEN NCC.MA "+
		"	 END MADT, "+
		"	CASE "+
		"	 WHEN A.SODONTRAHANG IS NOT NULL THEN NPP.TEN "+ 
		"	 WHEN A.SOPHIEUNHAPHANG IS NOT NULL THEN NCC.TEN "+
		"	 END TENDT, "+
		"	I.SOLUONGNHAN AS SOLUONG,KHO.TEN +'-'+ KHO.DIACHI KHO,NV.TEN NGUOITAO , "+
		"	CASE "+ 
		"		WHEN  A.TRANGTHAI=0 THEN N'Chưa chốt' "+
		"		WHEN A.TRANGTHAI=1 THEN N'Đã chốt' "+
		"		WHEN A.TRANGTHAI=2 THEN N'Hoàn tất' "+
		"		WHEN A.TRANGTHAI=3 THEN N'Đã xóa' "+
		"		WHEN A.TRANGTHAI=4 THEN N'Đã hủy' "+
		"		END AS TRANGTHAI "+
		"	FROM ERP_NHAPKHO A INNER JOIN ( SELECT  PK_SEQ,TEN FROM ERP_NOIDUNGNHAP WHERE 1=1";
		if(this.LoaiCtIds.length()>0)
			query+=" AND PK_SEQ IN ( " +this.LoaiCtIds+" ) ";
		query+=
		" ) B ON A.NOIDUNGNHAP = B.PK_SEQ INNER JOIN ERP_KHOTT C ON A.KHONHAP = C.PK_SEQ "+  
		"		LEFT JOIN ERP_NHANHANG F ON A.SOPHIEUNHAPHANG = F.PK_SEQ  "+ 
		"		LEFT JOIN ERP_DONVITHUCHIEN G ON F.DONVITHUCHIEN_FK = G.PK_SEQ "+  
		" 		LEFT JOIN DONTRAHANG H ON A.SODONTRAHANG = H.PK_SEQ "+ 
		"		LEFT JOIN ERP_LENHSANXUAT K ON A.SOLENHSANXUAT = K.PK_SEQ "+  
		"		INNER JOIN ERP_NHAPKHO_SANPHAM I ON I.SONHAPKHO_FK=A.PK_SEQ "+
		"		INNER JOIN ( SELECT PK_SEQ,MA,TEN from ERP_SanPham  WHERE 1=1 " ;
		if(this.malonsanphamIds.length()>0)
			query+=" AND  MA IN ( "+this.malonsanphamIds+" ) " ;
		if(this.loaisanphamIds.length()>0)
			query+=" AND  LOAISANPHAM_FK IN ( "+this.loaisanphamIds+" ) " ;
		if(this.spIds.length()>0)
			query+=" AND PK_SEQ IN ( " +this.spIds+"  )";
		query+=	
		" ) J ON J.PK_SEQ=I.SANPHAM_FK "+
		"		INNER JOIN ERP_KHOTT KHO ON KHO.PK_SEQ=A.KHONHAP "+
		"		LEFT JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=H.NPP_FK "+
		"		LEFT JOIN ERP_MUAHANG MH ON MH.PK_SEQ=F.MUAHANG_FK "+ 
		"		LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ=MH.NHACUNGCAP_FK "+
		"		INNER JOIN NHANVIEN NV ON NV.PK_SEQ=A.NGUOITAO " +
		" 	WHERE F.NGAYNHAN>='"+this.tungay+"' AND F.NGAYNHAN<='"+this.denngay+"'	" ;
		if(this.khoIds.length()>0 )
			query+=" AND A.KHONHAP="+this.khoIds+" ";
		if(this.LoaiCtIds.indexOf("200000")>=0||this.LoaiCtIds.trim().length()==0) 
		{
		query+=
		" UNION ALL "+ 
		"		SELECT A.PREFIX + CAST(A.PK_SEQ AS VARCHAR(20)) AS SOCT, N'Điều chỉnh tồn kho' AS LOAICT, "+
		"		A.NGAYDIEUCHINH AS NGAYCT, A.NGAYCHOT,SP.MA AS MASP,SP.TEN TENSP,'KHO' AS MADT,'KHO' AS TENDT, "+
		"		ISNULL(B.SOLUONGDIEUCHINH, '0') AS SOLUONG,KHO.TEN+'-'+KHO.DIACHI AS KHO,NV.TEN AS NGUOITAO, "+
		"		CASE "+
		"			WHEN A.TRANGTHAI=0 THEN N'Chưa chốt' "+
		"			WHEN A.TRANGTHAI=1 THEN N'Đã chốt' "+
		"		END  AS TRANGTHAI "+
		"		FROM ERP_DIEUCHINHTONKHOTT A "+ 
		"		INNER JOIN ERP_DIEUCHINHTONKHOTT_SANPHAM B ON A.PK_SEQ = B.DIEUCHINHTONKHOTT_FK "+
		"		INNER JOIN ( SELECT PK_SEQ,MA,TEN from ERP_SanPham  WHERE 1=1" ;
		if(this.malonsanphamIds.length()>0)
			query+=" AND  MA IN ( "+this.malonsanphamIds+" ) " ;
		if(this.loaisanphamIds.length()>0)
			query+=" AND  LOAISANPHAM_FK IN ( "+this.loaisanphamIds+" ) " ;
		if(this.spIds.length()>0)
			query+=" AND PK_SEQ IN ( " +this.spIds+"  )";
		query+=	
		" ) SP ON SP.PK_SEQ=B.SANPHAM_FK "+
		"		INNER JOIN ERP_KHOTT KHO ON KHO.PK_SEQ=A.KHOTT_FK "+
		"		INNER JOIN NHANVIEN NV ON NV.PK_SEQ=A.NGUOITAO" +
		" WHERE  A.NGAYCHOT>='"+this.tungay+"' AND A.NGAYCHOT<='"+this.denngay+"'";
		if(this.khoIds.length()>0 )
			query+=" AND A.KHOTT_FK ="+this.khoIds+" ";
		}
		System.out.println("Bao cao tong hop "+query);
		return query;
	}

	
	public String getMsg()
	{
		return this.Msg;
	}

	
	public void setMsg(String Msg)
	{
		this.Msg=Msg;
	}
}
