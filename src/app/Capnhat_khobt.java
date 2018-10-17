package app;

import geso.traphaco.center.db.sql.dbutils;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Capnhat_khobt {
	
	public static void main(String[] args) 
	{
		try
		{
			dbutils db = new dbutils();
			db.getConnection().setAutoCommit(false);
			
			ResultSet rs;
			String query = "";
			
			String[] param = new String[4];
			
			param[0] = "100000";		//kho
			param[1] = "2015-07-01";	//tungay
			param[2] = "2015-08-31";	//denngay
			param[3] = "106313";		//npp
			
			// cap nhat kho tong nhapp_kho_chitiet
			
			// cap nhat soluong
			rs = db.getRsByPro("REPORT_XUATNHAPTON_CHITIET_COLUMN", param);
			while(rs.next())
			{
				double cuoiky = rs.getDouble("CUỐI KỲ");
				if(cuoiky < 0)
					cuoiky = 0;
				String nhomkenh = rs.getString("NHÓM KÊNH");
				String solo = rs.getString("SỐ LÔ");
				String sanpham = rs.getString("SANPHAMID");
				query = "select COUNT(*) as kt from NHAPP_KHO_CHITIET where KHO_FK = '"+param[0]+"' and npp_fk = '"+param[3]+"' and nhomkenh_fk = '"+nhomkenh+"' and SANPHAM_FK = '"+sanpham+"' and SOLO = '"+solo+"'";
				ResultSet rskt = db.get(query);
				rskt.next();
				String kt = rskt.getString("kt");
				if(!kt.trim().equals("0"))
				{
					query = "update nhapp_kho_chitiet set soluong = '"+cuoiky+"' where kho_fk = '"+param[0]+"' and npp_fk = '"+param[3]+"' "
							+ "and nhomkenh_fk = '"+nhomkenh+"' and solo = '"+solo+"' and sanpham_fk = '"+sanpham+"'";
					System.out.println(query);
					if (!db.update(query)) {
						System.out.println("loi update nhapp_kho_chitiet: "+query);
						db.getConnection().rollback();
					}
				}
				else
				{
					query = "insert into nhapp_kho_chitiet(kho_fk, npp_fk, sanpham_fk, nhomkenh_fk, solo, ngayhethan, soluong, booked, available) "
							+ "select "+param[0]+", "+param[3]+", "+sanpham+", "+nhomkenh+", '"+solo+"', '', "+cuoiky+", 0, "+cuoiky+" ";
					System.out.println(query);
					if (!db.update(query)) {
						System.out.println("loi insert nhapp_kho_chitiet: "+query);
						db.getConnection().rollback();
					}
				}
			}
			
			// cap nhat booked
			
			query = "select sanpham_fk, nhomkenh_fk, solo, booked from NHAPP_KHO_CHITIET where KHO_FK = "+param[0]+" and NPP_FK = "+param[3];
			rs = db.get(query);
			while(rs.next())
			{
				double bookedban = 0;
				String nhomkenh = rs.getString("NHOMKENH_FK");
				String solo = rs.getString("solo");
				String sanpham = rs.getString("sanpham_fk");
				
				query = "SELECT 'PHIEUGIAOHANG' AS TYPE,A.NPP_FK,  A.KHO_FK, B.SANPHAM_FK, A.nhomkenh_fk, B.solo, B.SOLUONG, B.DVDL_FK "
						+ "FROM ERP_DONDATHANGNPP A "
						+ "inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET B on A.PK_SEQ = B.dondathang_fk "
						+ "WHERE  A.CS_DUYET = '1' and A.Kho_FK = "+param[0]+" and A.NPP_FK = " + param[3] + " and B.sanpham_fk = " + sanpham + " and solo = '" + solo + "' "
						+ "and A.PK_SEQ in (select ddh_fk from ERP_YCXUATKHONPP where TRANGTHAI = 0)";
				System.out.println("query " + query);
				ResultSet rsbookbanhang = db.get(query);
				if(rsbookbanhang!=null)
				{
					while(rsbookbanhang.next())
					{
						String dvdl = rsbookbanhang.getString("dvdl_fk")==null?"":rsbookbanhang.getString("dvdl_fk");
						
						query = "select dvdl1_fk, dvdl2_fk, soluong1, soluong2 from quycach where sanpham_fk = " + sanpham;
						ResultSet rsquycach = db.get(query);
						double soluong = rsbookbanhang.getDouble("soluong");
						while(rsquycach.next())
						{
							String dvdl1 = rsquycach.getString("dvdl1_fk")==null?"":rsquycach.getString("dvdl1_fk");
							String dvdl2 = rsquycach.getString("dvdl2_fk")==null?"":rsquycach.getString("dvdl2_fk");
							double soluong1 = rsquycach.getDouble("soluong1");
							double soluong2 = rsquycach.getDouble("soluong2");
							if(!dvdl.trim().equals(dvdl1.trim()))
							{
								if(dvdl.trim().equals(dvdl2.trim()))
								{
									soluong = soluong * soluong1 / soluong2;
								}
							}
							else
								break;
						}
						
						bookedban = bookedban + soluong;
					}
				}
				query = "update nhapp_kho_chitiet set booked = '"+bookedban+"' where kho_fk = '"+param[0]+"' and npp_fk = '"+param[3]+"' "
						+ "and nhomkenh_fk = '"+nhomkenh+"' and solo = '"+solo+"' and sanpham_fk = '"+sanpham+"'";
				System.out.println(query);
				if (!db.update(query)) {
					System.out.println("loi update nhapp_kho_chitiet: "+query);
					db.getConnection().rollback();
				}
			}
			
			query = "SELECT NPP_FK, KhoXuat_FK, sanpham_fk, NHOMKENH_FK, solo, SUM(soluong) as booked FROM "
					+ " ("
					+ "	SELECT 'CHUYENKHO' AS TYPE ,CK.NPP_FK,CK.KhoXuat_FK,CKSP.SANPHAM_FK, CK.NHOMKENH_FK,solo,CKSP.soluong "
					+ " FROM ERP_CHUYENKHO_SP_XUATHANG CKSP "
					+ " INNER JOIN ERP_CHUYENKHO CK ON CK.PK_SEQ=CKSP.CHUYENKHO_FK"
					+ " WHERE CK.TRANGTHAI =0 AND ISNULL(CK.LOAICHUYENKHO,'0') = '0' and ck.NgayChuyen >= '"+param[1]+"' and ck.NgayChuyen <= '"+param[2]+"'"
					+ "	UNION ALL"
					+ " SELECT 'CHUYENKHO_TDV' AS TYPE ,CK.NPP_FK,CK.KhoXuat_FK,CKSP.SANPHAM_FK, CKSP.nhomkenh_fk, solo,CKSP.soluong"
					+ " FROM ERP_CHUYENKHO_SANPHAM_CHITIET CKSP"
					+ " INNER JOIN ERP_CHUYENKHO CK ON CK.PK_SEQ=CKSP.CHUYENKHO_FK"
					+ " WHERE CK.TRANGTHAI =0 AND ISNULL(CK.LOAICHUYENKHO,'0') ='1' and ck.NgayChuyen >= '"+param[1]+"' and ck.NgayChuyen <= '"+param[2]+"'"
					+ " UNION ALL"
					+ " SELECT 'TRAHANG' AS TYPE, NPP_FK,  A.KHO_FK, B.SANPHAM_FK, B.nhomkenh_fk, B.solo, B.SOLUONG AS TONGXUAT"
					+ " FROM DONTRAHANG A"
					+ " INNER JOIN DONTRAHANG_SP B ON A.PK_SEQ = B.DONTRAHANG_FK"
					+ " WHERE A.TRANGTHAI=0 and A.NGAYTRA >= '"+param[1]+"' and A.NGAYTRA <= '"+param[2]+"'"
					+ " UNION ALL"
					+ " SELECT 'BAOCAODAILY' AS TYPE, A.NPP_FK AS NPP_FK,A.KHO_FK, B.SANPHAM_FK, B.NHOMKENH_FK, B.solo, B.SOLUONG AS TONGXUAT"
					+ " FROM DONHANG A"
					+ " INNER JOIN DONHANG_SANPHAM_CHITIET B ON A.PK_SEQ = B.DONHANG_FK"
					+ " WHERE A.TRANGTHAI=0 and A.NGAYNHAP >= '"+param[1]+"' and A.NGAYNHAP <= '"+param[2]+"'"
					+ " ) AS DATA"
					+ " WHERE DATA.KhoXuat_FK='"+param[0]+"' AND NPP_FK='"+param[3]+"' "
					+ " group by NPP_FK, KhoXuat_FK, sanpham_fk, NHOMKENH_FK, solo";
			System.out.println("query booked " + query);
			rs = db.get(query);
			while(rs.next())
			{
				double booked = rs.getDouble("booked");
				String nhomkenh = rs.getString("NHOMKENH_FK");
				String solo = rs.getString("solo");
				String sanpham = rs.getString("sanpham_fk");
				
				query = "update nhapp_kho_chitiet set booked = isnull(booked,0)+ '"+booked+"' where kho_fk = '"+param[0]+"' and npp_fk = '"+param[3]+"' "
						+ "and nhomkenh_fk = '"+nhomkenh+"' and solo = '"+solo+"' and sanpham_fk = '"+sanpham+"'";
				System.out.println(query);
				if (!db.update(query)) {
					System.out.println("loi update nhapp_kho_chitiet: "+query);
					db.getConnection().rollback();
				}
				
			}
			
			// cap nhat available
			query = "select sanpham_fk, nhomkenh_fk, solo, soluong, booked from NHAPP_KHO_CHITIET WHERE Kho_FK='"+param[0]+"' AND NPP_FK='"+param[3]+"' ";
			
			rs = db.get(query);
			while(rs.next())
			{
				double soluong = rs.getDouble("soluong");
				double booked = rs.getDouble("booked");
				double available = soluong - booked;
				if(available < 0)
					available = 0;
				String nhomkenh = rs.getString("NHOMKENH_FK");
				String solo = rs.getString("solo");
				String sanpham = rs.getString("sanpham_fk");
				query = "update nhapp_kho_chitiet set available = '"+available+"' where kho_fk = '"+param[0]+"' and npp_fk = '"+param[3]+"' "
						+ "and nhomkenh_fk = '"+nhomkenh+"' and solo = '"+solo+"' and sanpham_fk = '"+sanpham+"'";
				System.out.println(query);
				if (!db.update(query)) {
					System.out.println("loi update nhapp_kho_chitiet: "+query);
					db.getConnection().rollback();
				}
				
			}
			
			//------------------------------------------------------------------------------------------------------------------------------------------------
			// cap nhat kho tong nhapp_kho
			
			query = "select KHO_FK, nhomkenh_fk, NPP_FK, SANPHAM_FK, SUM(soluong) as soluong, sum(booked) as booked, SUM(available) as available "
					+ "from NHAPP_KHO_CHITIET "
					+ "where KHO_FK = "+param[0]+" and NPP_FK = "+param[3]+" "
					+ "group by KHO_FK, nhomkenh_fk, NPP_FK, SANPHAM_FK";
			rs = db.get(query);
			while(rs.next())
			{
				double soluong = rs.getDouble("soluong");
				double booked = rs.getDouble("booked");
				double available = rs.getDouble("available");
				String kho = rs.getString("KHO_FK");
				String npp = rs.getString("NPP_FK");
				String nhomkenh = rs.getString("nhomkenh_fk");
				String sanpham = rs.getString("SANPHAM_FK");
				query = "select COUNT(*) as kt from NHAPP_KHO where KHO_FK = '"+param[0]+"' and npp_fk = '"+param[3]+"' and nhomkenh_fk = '"+nhomkenh+"' and SANPHAM_FK = '"+sanpham+"'";
				ResultSet rskt = db.get(query);
				rskt.next();
				String kt = rskt.getString("kt");
				if(!kt.trim().equals("0"))
				{
					query = "update nhapp_kho set soluong = '"+soluong+"', booked = "+booked+", available = "+available+" where kho_fk = '"+kho+"' and npp_fk = '"+npp+"' "
							+ "and nhomkenh_fk = '"+nhomkenh+"' and sanpham_fk = '"+sanpham+"'";
					System.out.println(query);
					if (!db.update(query)) {
						System.out.println("loi update nhapp_kho: "+query);
						db.getConnection().rollback();
					}
				}
				else
				{
					query = "insert into nhapp_kho(kho_fk, npp_fk, sanpham_fk, nhomkenh_fk, soluong, booked, available) "
							+ "select "+param[0]+", "+param[3]+", "+sanpham+", "+nhomkenh+", "+soluong+", "+booked+", "+available+" ";
					System.out.println(query);
					if (!db.update(query)) {
						System.out.println("loi insert nhapp_kho: "+query);
						db.getConnection().rollback();
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(false);
			System.out.println("Hoan tat!");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
	}
}
