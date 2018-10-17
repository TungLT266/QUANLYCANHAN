package app;

import geso.traphaco.center.db.sql.dbutils;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class CapnhatTooltipHD {
	public static void main(String[] args) {
		dbutils db = new dbutils();
		String query = "select a.pk_seq, a.ngayxuatHD, a.sohoadon, a.nhomkenh_fk, b.ddh_fk from ERP_HOADONNPP a inner join ERP_HOADONNPP_DDH b on "
				+ "a.pk_seq = b.hoadonnpp_fk where a.pk_seq > 0 and a.tooltip is null --and a.pk_seq = 153316";

		try
		{
			db.getConnection().setAutoCommit(false);
			
			ResultSet rs = db.get(query);
			NumberFormat formatter = new DecimalFormat("#,###,###.##");
			while(rs.next())
			{
				String tooltip = "NULL";
				String table = "";
				String id = rs.getString("pk_seq");
				String ngayxuatHD = rs.getString("ngayxuatHD");
				String sohoadon = rs.getString("sohoadon");
				String kbhId = rs.getString("nhomkenh_fk");
				String dhid = rs.getString("ddh_fk");
				query = "select ten from nhomkenh where pk_seq = " + kbhId;
				ResultSet rskkbh = db.get(query);
				String kbh = "";
				if(rskkbh != null)
				{
					while(rskkbh.next())
					{
						kbh = rskkbh.getString("ten");
					}
				}
				
				tooltip = "Mã số: " + id + "</br> Ngày hóa đơn: " + ngayxuatHD +
						"</br> Số hóa đơn: " + sohoadon + "</br> Kênh bán hàng: " + kbh;
				table = "</br><table><tr><th style=width: 10% >Mã SP</th><th style=width: 10% >Tên SP</th><th style=width: 10% >Số lượng</th><th style=width: 10% >Đơn giá</th></tr>";
			
				query = "select b.MA, isnull(a.sanphamTEN, b.TEN) as TEN, DV.donvi, isnull(a.soluong, 0) as soluong, isnull(a.dongia, 0) as dongia, "
						+ "isnull(a.chietkhau, 0) as chietkhau, isnull(a.thueVAT, 0) as thueVAT "
						+ "from ERP_DONDATHANGNPP_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK "
						+ "where a.DONDATHANG_FK = '"+dhid+"' ";
				
				double tongtien = 0;
				double tongtienck = 0;
				double tongvat = 0;
				double tongsauck = 0;
				double tongsauvat = 0;
				ResultSet rsSP = db.get(query);
				while(rsSP.next())
				{
					String ma = rsSP.getString("ma");
					String ten = rsSP.getString("ten");
					String dongia = rsSP.getString("dongia");
					String soluong = rsSP.getString("soluong");
					String ck = rsSP.getString("chietkhau");
					String vat = rsSP.getString("thueVAT");
					
					tongtien += Double.parseDouble(dongia) * Double.parseDouble(soluong);
					tongtienck += Double.parseDouble(ck);
					tongvat += (Double.parseDouble(dongia) * Double.parseDouble(soluong) - Double.parseDouble(ck)) * (Double.parseDouble(vat) / 100); 
					
					table += "<tr><td>"+ma.trim()+"</td><td>"+ten.trim()+"</td><td>"+soluong.trim()+"</td><td>"+dongia.trim()+"</td></tr>";
				}
				table += "</table>";
				tongsauck = tongtien - tongtienck;
				tongsauvat = tongsauck + tongvat;
				tongsauck = tongtien - tongtienck;
				tongsauvat = tongsauck + tongvat;
				tooltip += "</br> Tổng thành tiền: " + formatter.format(tongtien);
				tooltip += "</br> Tổng tiền CK: " + formatter.format(tongtienck);
				tooltip += "</br> Tổng tiền sau CK: " + formatter.format(tongsauck);
				tooltip += "</br> Tổng tiền sau VAT: " + formatter.format(tongsauvat);
				tooltip += table;
				
				query = "update ERP_HOADONNPP set tooltip = N'" + tooltip + "' where pk_seq = " + id;
				System.out.println(tooltip);
				if(!db.update(query))
				{
					System.out.println("::: Loi cap nhat tooltip: " + query);
					db.getConnection().rollback();
					return;
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
