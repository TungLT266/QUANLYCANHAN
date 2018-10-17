package app;

import geso.traphaco.center.db.sql.dbutils;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaoNhaphangtudong {
	public static String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	public static void main(String[] args) {
		try
		{
			dbutils db = new dbutils();
			db.getConnection().setAutoCommit(false);
			//TU DONG HOAN TAT CAC DON DAT HANG TU CU TOI MOI
			String
			query = "select a.PK_SEQ, b.ddh_fk from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_DDH b on a.PK_SEQ = b.ycxk_fk where NPP_DAT_FK = 106344 order by ddh_fk asc";
			ResultSet rsDDH = db.get(query);
			
			while(rsDDH.next())
			{
				String Id = rsDDH.getString("PK_SEQ");
				query = " insert NHAPHANG(NGAYNHAN, NGAYCHUNGTU, NPP_FK, SOCHUNGTU, NCC_FK, GSBH_FK, ASM_FK, BM_FK, DVKD_FK, NHOMKENH_FK, YCXKNPP_FK, TRANGTHAI, NGUOITAO, NGAYTAO, NGUOISUA, NGAYSUA) " +
						" select distinct NgayYeuCau, NgayYeuCau, NPP_DAT_FK,  " +
						" 			( select sohoadon from ERP_YCXUATKHONPP where PK_SEQ = a.pk_seq ), " +
						" 			( select top(1) NCC_FK from NHACUNGCAP_DVKD where PK_SEQ in ( select NCC_DVKD_FK from NHAPP_NHACC_DONVIKD where NPP_FK = a.NPP_DAT_FK ) ), " +
						"			( select top(1) GSBH_FK from NHAPP_GIAMSATBH where NPP_FK = a.NPP_DAT_FK ), " +
						"			( select top(1) ASM_FK from ASM_KHUVUC where KHUVUC_FK in ( select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = a.NPP_DAT_FK )), " +
						"			( select top(1) BM_FK from BM_CHINHANH where VUNG_FK in ( select VUNG_FK from KHUVUC where PK_SEQ in (  select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = a.NPP_DAT_FK ) ) ), " +
						" 	   '100001' as DVKD_FK, a.NHOMKENH_FK, '" + Id + "', '0', '100336', '" + getDateTime() + "', ' 100336', '" + getDateTime() + "' " +
						" from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM b on a.PK_SEQ = b.ycxk_fk " +
						" where a.PK_SEQ = '" + Id + "' ";
				
				System.out.println("---INSERT NHAN HANG: " + query );
				if(!db.update(query))
				{
					System.out.println("Không tạo mới NHAPHANG " + query);
					db.getConnection().rollback();
				}
				
				query = " insert NHAPHANG_SP(NHAPHANG_FK, SANPHAM_FK, SOLUONG, soluongNHAN, DONGIA, CHIETKHAU, DVDL_FK, LOAI, SCHEME, SOLO, NGAYHETHAN) " +
						" select ( select pk_seq from NHAPHANG where YCXKNPP_FK = a.PK_SEQ  ),  " +
						" 		b.sanpham_fk, b.soluong, NULL, b.dongia, 0 as chietkhau, c.DVDL_FK, b.LOAI, b.SCHEME, b.solo, b.ngayhethan " +
						" from ERP_YCXUATKHONPP a inner join ERP_YCXUATKHONPP_SANPHAM_THUCGIAO_CHITIET b on a.PK_SEQ = b.ycxk_fk " +
						" 		inner join SANPHAM c on b.sanpham_fk = c.PK_SEQ   " +
						" where a.PK_SEQ = '" + Id + "' and b.soluong > 0 ";
				System.out.println("---INSERT NHAN HANG - SP: " + query );
				if(db.updateReturnInt(query) < 1 )
				{
					System.out.println("Không tạo mới NHAPHANG_SP " + query);
					db.getConnection().rollback();
				}
				
				query = "insert NHAPHANG_DDH(nhaphang_fk, ddh_fk)  " +
						"select ( select PK_SEQ from NHAPHANG where YCXKNPP_FK = '" + Id + "' ) as nhID, ddh_fk  " +
						"from ERP_YCXUATKHONPP_DDH where ycxk_fk = '" + Id + "'";
				if(!db.update(query))
				{
					System.out.println("Không tạo mới NHAPHANG_DDH " + query);
					db.getConnection().rollback();
				}
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(false);
			System.out.println("hoan tat");
		}
		catch(Exception ex)
		{
			System.out.println("Vui Long Kiem Tra Lai Du Lieu .Xay Ra Loi Sau :" + ex.toString());
			ex.printStackTrace();
		}
	}
}
