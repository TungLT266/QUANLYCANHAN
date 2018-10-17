package geso.traphaco.center.beans.baocao.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.beans.baocao.ILichSuGdKho;

public class LichSuGdKho implements ILichSuGdKho
{
	String userId;
	String userTen;

	ResultSet loaiSpRs, loaiCtRs;
	ResultSet spRs;
	ResultSet khoRs;
	String loaisanphamIds;
	String spIds;
	String khoIds;
	String khoTen;
	String LoaiCtIds;
	String tungay;
	String denngay,Msg;

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

		String query = "select pk_seq, ma, ten from sanpham where trangthai = '1' ";
		if (this.loaisanphamIds.length() > 0)
		{
			query += "and loaisanpham_fk In( " + this.loaisanphamIds + ")";
		}

		this.spRs = db.get(query);

		query = " select pk_seq, ten + ', ' + diachi as khoTen from erp_khott ";
		this.khoRs = db.get(query);

		query = " Select PK_SEQ,TEN FROM ERP_NOIDUNGNHAP WHERE TRANGTHAI=1 " + " UNION ALL "
			+ " SELECT '200000',N'Kiểm kho' ";

		this.loaiCtRs = this.db.get(query);
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
			if(this.khoRs!=null)
				this.khoRs.close();

		} catch (SQLException e)
		{
			e.printStackTrace();
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
String 
			query = 
			"SELECT	B.PREFIX + CAST(B.PK_SEQ AS VARCHAR(20)) AS SOCT,ND.TEN AS LOAICT, B.NGAYCHUYEN AS NGAYCT, B.NGAYCHOT, SP.MA AS MASP,SP.TEN AS TENSP, \n"+
			"	KHOXUAT.TEN AS MADT,KHOXUAT.TEN+'-'+KHOXUAT.DIACHI  AS TENDT,(-1)*ISNULL(A.SOLUONGNHAN,0) AS SOLUONG,KHONHAN.TEN+'-'+KHONHAN.DIACHI  AS KHO,NV.TEN AS NGUOITAO, \n"+
			"	CASE \n" +
			"		WHEN B.TRANGTHAI =0 THEN N'Chưa chốt' \n"+
			"		WHEN B.TRANGTHAI=1 THEN N'Đã chốt' \n"+
			"		WHEN B.TRANGTHAI=2 THEN N'Đã hủy' \n"+
			"		WHEN B.TRANGTHAI=3 THEN N'Hoàn tất' \n"+
			"	END TRANGTHAI \n"+
			"FROM ERP_CHUYENKHO_SANPHAM A INNER JOIN ERP_CHUYENKHO B ON A.CHUYENKHO_FK = B.PK_SEQ \n"+  
			"	INNER JOIN( SELECT  PK_SEQ,TEN FROM ERP_NOIDUNGNHAP WHERE 1=1 \n";
			if(this.LoaiCtIds.length()>0)
				query+=" AND PK_SEQ IN ( " +this.LoaiCtIds+" ) \n";
			query+=
			" ) ND ON ND.PK_SEQ=B.NOIDUNGXUAT_FK \n"+
			" 	INNER JOIN ( SELECT PK_SEQ,MA,TEN FROM SANPHAM  WHERE 1=1 \n" ;
			if(this.loaisanphamIds.length()>0)
				query+=" AND  LOAISANPHAM_FK IN ( "+this.loaisanphamIds+" ) \n" ;
			if(this.spIds.length()>0)
				query+=" AND PK_SEQ IN ( " +this.spIds+"  ) \n";
			query+=" ) SP ON SP.PK_SEQ=A.SANPHAM_FK \n"+
			" 	INNER JOIN ERP_KHOTT KHOXUAT ON KHOXUAT.PK_SEQ=B.KHOXUAT_FK \n"+
			" 	INNER JOIN NHANVIEN NV ON NV.PK_SEQ=B.NGUOITAO \n"+
			" 	INNER JOIN ERP_KHOTT KHONHAN ON KHONHAN.PK_SEQ=B.KHONHAN_FK \n" +
			"WHERE  B.NGAYCHOT>='"+this.tungay+"' AND B.NGAYCHOT<='"+this.denngay+"' \n"  ;
			if(this.khoIds.length()>0 )
				query+=" AND B.KHOXUAT_FK ="+this.khoIds+" ";
			query+=
			" UNION ALL  \n"+
					" SELECT B.PREFIX + CAST(B.PK_SEQ AS VARCHAR(20)) AS SOCT,C.TEN LOAICT,B.NGAYXUAT AS NGAYCT,ISNULL( B.NGAYCHOT,'')AS NGAYCHOT,\n"+
				" D.MA AS MASP ,D.TEN TENSP, \n"+ 
				" CASE \n"+
				"	WHEN B.TRAHANGNCC_FK IS NOT NULL THEN NCC.MA \n"+
				"	WHEN B.DONDATHANG_FK IS NOT NULL THEN NPP.MA \n"+
				" END MADT, \n"+
				" CASE \n"+
				"	WHEN B.TRAHANGNCC_FK IS NOT NULL THEN NCC.TEN \n"+
				"	WHEN B.DONDATHANG_FK IS NOT NULL THEN NPP.TEN \n"+
				" END TENDT,(-1)*A.SOLUONG,KHO.TEN+'-'+KHO.DIACHI AS KHO,NV.TEN AS NGUOITAO, \n"+
				" CASE \n"+ 
				"	WHEN B.TRANGTHAI=0 THEN N'Chưa chốt' \n"+
				"	WHEN B.TRANGTHAI=1 THEN N'Đã chốt' \n"+
				"	WHEN B.TRANGTHAI=2 THEN N'Đã xoá' \n"+
				"	WHEN B.TRANGTHAI=3 THEN N'Đã hủy' \n"+
				"	END AS TRANGTHAI \n"+
			" FROM ERP_XUATKHO_SANPHAM A \n"+ 
			"	INNER JOIN ERP_XUATKHO B ON A.XUATKHO_FK = B.PK_SEQ \n"+    
			"	INNER JOIN ( SELECT  PK_SEQ,TEN FROM ERP_NOIDUNGNHAP WHERE 1=1 \n" ;
			if(this.LoaiCtIds.length()>0)
				query+=" AND PK_SEQ IN ( " +this.LoaiCtIds+" ) \n";
			query+=
			") C ON C.PK_SEQ=B.NOIDUNGXUAT \n"+
			"	INNER JOIN ( SELECT PK_SEQ,MA,TEN FROM SANPHAM  WHERE 1=1 \n" ;
			if(this.loaisanphamIds.length()>0)
				query+=" AND  LOAISANPHAM_FK IN ( "+this.loaisanphamIds+" ) \n" ;
			if(this.spIds.length()>0)
				query+=" AND PK_SEQ IN ( " +this.spIds+"  ) \n";
			query+=	
			" ) D ON D.PK_SEQ=A.SANPHAM_FK \n"+
			"	LEFT JOIN ERP_MUAHANG MH ON MH.PK_SEQ=B.TRAHANGNCC_FK \n"+
			"	LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ=MH.NHACUNGCAP_FK \n" +
			"	LEFT JOIN DONDATHANG DDH ON DDH.PK_SEQ=B.DONDATHANG_FK \n"+
			"	LEFT JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=DDH.NPP_FK \n"+
			"	INNER JOIN ERP_KHOTT KHO ON KHO.PK_SEQ=B.KHO_FK \n"+
			" 	INNER JOIN NHANVIEN NV ON NV.PK_SEQ=B.NGUOITAO \n" +
			" WHERE  B.NGAYCHOT>='"+this.tungay+"' AND B.NGAYCHOT<='"+this.denngay+"' \n" ;
			if(this.khoIds.length()>0 )
				query+=" AND B.KHO_FK ="+this.khoIds+" \n";
			query+=
		" UNION ALL \n"+
		"	SELECT A.PREFIX + CAST (A.PK_SEQ AS VARCHAR(20)) AS SOCT,B.TEN AS LOAICT,A.NGAYNHAPKHO AS NGAYCT,ISNULL(A.NGAYCHOT, '') AS NGAYCHOT, \n"+
		"		J.MA AS MASP,J.TEN AS TENSP, \n"+
		"	CASE \n"+
		"	 WHEN A.SODONTRAHANG IS NOT NULL THEN NPP.MA \n"+ 
		"	 WHEN A.SOPHIEUNHAPHANG IS NOT NULL THEN NCC.MA \n"+
		"	 END MADT, \n"+
		"	CASE \n"+
		"	 WHEN A.SODONTRAHANG IS NOT NULL THEN NPP.TEN \n"+ 
		"	 WHEN A.SOPHIEUNHAPHANG IS NOT NULL THEN NCC.TEN  \n"+
		"	 END TENDT, \n"+
		"	I.SOLUONGNHAP AS SOLUONG,KHO.TEN +'-'+ KHO.DIACHI KHO,NV.TEN NGUOITAO , \n"+
		"	CASE \n"+ 
		"		WHEN  A.TRANGTHAI=0 THEN N'Chưa chốt' \n"+
		"		WHEN A.TRANGTHAI=1 THEN N'Đã chốt' \n"+
		"		WHEN A.TRANGTHAI=2 THEN N'Hoàn tất' \n"+
		"		WHEN A.TRANGTHAI=3 THEN N'Đã xóa' \n"+
		"		WHEN A.TRANGTHAI=4 THEN N'Đã hủy' \n"+
		"		END AS TRANGTHAI \n"+
		"	FROM ERP_NHAPKHO A INNER JOIN ( SELECT  PK_SEQ,TEN FROM ERP_NOIDUNGNHAP WHERE 1=1  \n" ;
		if(this.LoaiCtIds.length()>0)
			query+=" AND PK_SEQ IN ( " +this.LoaiCtIds+" ) \n";
		query+=
		" ) B ON A.NOIDUNGNHAP = B.PK_SEQ INNER JOIN ERP_KHOTT C ON A.KHONHAP = C.PK_SEQ \n"+  
		"		LEFT JOIN ERP_NHANHANG F ON A.SOPHIEUNHAPHANG = F.PK_SEQ  \n"+ 
		"		LEFT JOIN ERP_DONVITHUCHIEN G ON F.DONVITHUCHIEN_FK = G.PK_SEQ \n"+  
		" 		LEFT JOIN DONTRAHANG H ON A.SODONTRAHANG = H.PK_SEQ \n"+ 
		"		LEFT JOIN ERP_LENHSANXUAT K ON A.SOLENHSANXUAT = K.PK_SEQ \n"+  
		"		INNER JOIN ERP_NHAPKHO_SANPHAM I ON I.SONHAPKHO_FK=A.PK_SEQ \n"+
		"		INNER JOIN ( SELECT PK_SEQ,MA,TEN FROM SANPHAM  WHERE 1=1 \n" ;
		if(this.loaisanphamIds.length()>0)
			query+=" AND  LOAISANPHAM_FK IN ( "+this.loaisanphamIds+" ) \n" ;
		if(this.spIds.length()>0)
			query+=" AND PK_SEQ IN ( " +this.spIds+"  )  \n";
		query+=	
		" ) J ON J.PK_SEQ=I.SANPHAM_FK \n"+
		"		INNER JOIN ERP_KHOTT KHO ON KHO.PK_SEQ=A.KHONHAP \n"+
		"		LEFT JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=H.NPP_FK \n"+
		"		LEFT JOIN ERP_MUAHANG MH ON MH.PK_SEQ=F.MUAHANG_FK \n"+ 
		"		LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ=MH.NHACUNGCAP_FK \n"+
		"		INNER JOIN NHANVIEN NV ON NV.PK_SEQ=A.NGUOITAO \n" +
		" 	WHERE A.NGAYCHOT>='"+this.tungay+"' AND A.NGAYCHOT<='"+this.denngay+"'	\n" ;
		if(this.khoIds.length()>0 )
			query+=" AND A.KHONHAP="+this.khoIds+" ";
		if(this.LoaiCtIds.indexOf("200000")>=0||this.LoaiCtIds.trim().length()==0) 
		{
		query+=
		" UNION ALL \n"+ 
		"		SELECT A.PREFIX + CAST(A.PK_SEQ AS VARCHAR(20)) AS SOCT, N'Kiểm kho' AS LOAICT, \n"+
		"		A.NGAYDIEUCHINH AS NGAYCT, A.NGAYCHOT,SP.MA AS MASP,SP.TEN TENSP,'KHO' AS MADT,'KHO' AS TENDT, \n"+
		"		ISNULL(B.SOLUONGDIEUCHINH, '0') AS SOLUONG,KHO.TEN+'-'+KHO.DIACHI AS KHO,NV.TEN AS NGUOITAO, \n"+
		"		CASE \n"+
		"			WHEN A.TRANGTHAI=0 THEN N'Chưa chốt' \n"+
		"			WHEN A.TRANGTHAI=1 THEN N'Đã chốt' \n"+
		"		END  AS TRANGTHAI \n"+
		"		FROM ERP_DIEUCHINHTONKHOTT A \n"+ 
		"		INNER JOIN ERP_DIEUCHINHTONKHOTT_SANPHAM B ON A.PK_SEQ = B.DIEUCHINHTONKHOTT_FK \n"+
		"		INNER JOIN ( SELECT PK_SEQ,MA,TEN FROM SANPHAM  WHERE 1=1 \n" ;
		if(this.loaisanphamIds.length()>0)
			query+=" AND  LOAISANPHAM_FK IN ( "+this.loaisanphamIds+" ) \n" ;
		if(this.spIds.length()>0)
			query+=" AND PK_SEQ IN ( " +this.spIds+"  ) \n";
		query+=	
		" ) SP ON SP.PK_SEQ=B.SANPHAM_FK \n"+
		"		INNER JOIN ERP_KHOTT KHO ON KHO.PK_SEQ=A.KHOTT_FK \n"+
		"		INNER JOIN NHANVIEN NV ON NV.PK_SEQ=A.NGUOITAO \n" +
		" WHERE  A.NGAYCHOT>='"+this.tungay+"' AND A.NGAYCHOT<='"+this.denngay+"' \n";
		if(this.khoIds.length()>0 )
			query+=" AND A.KHOTT_FK ="+this.khoIds+" \n";
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
