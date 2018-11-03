package app;

import geso.traphaco.center.db.sql.dbutils;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CapnhatTooltip {
	
	public static void main(String[] args) {
		dbutils db = new dbutils();
		String query = "select a.pk_seq, a.NgayDonHang, a.NgayDeNghi, a.HOPDONG_FK, a.ghichu,isnull( a.sotienGIAM, 0 ) as sotienGIAM, a.ngaysua, b.ten as nguoisua "
				+ "from ERP_DONDATHANGNPP a inner join nhanvien b on a.nguoisua = b.pk_seq "
				+ "where a.pk_seq  > 0 --and a.tooltip is null";

		try
		{
			db.getConnection().setAutoCommit(false);
			
			ResultSet rs = db.get(query);
			NumberFormat formatter = new DecimalFormat("#,###,###");
			while(rs.next())
			{
				String tooltip = "NULL";
				String table = "";
				String dhid = rs.getString("pk_seq");
				String ngaydonhang = rs.getString("NgayDonHang");
				String ngaydenghi = rs.getString("NgayDeNghi");
				String ghichu = rs.getString("ghichu")==null?"":rs.getString("ghichu");
				String hopdong = rs.getString("HOPDONG_FK")==null?"":rs.getString("HOPDONG_FK");
				Double sotiengiam = rs.getDouble("sotiengiam");
				String ngaysua = rs.getString("Ngaysua");
				String nguoisua = rs.getString("Nguoisua");
				
				tooltip = "Số Đơn hàng: "+ dhid +"  </br> Ngày đặt hàng: " + ngaydonhang +
						" </br> Ngày đề nghị: " + ngaydenghi +"</br> Số hợp đồng: " + hopdong+
						"</br>Ghi chú: "+ghichu;
				table = "</br><table><tr><th style=width: 10% >Mã SP</th><th style=width: 10% >Tên SP</th><th style=width: 10% >Số lượng</th><th style=width: 10% >Đơn giá</th><th style=width: 10% >Scheme</th></tr>";
			
				query = "select b.MA, isnull(a.sanphamTEN, b.TEN) as TEN, DV.donvi, isnull(a.soluong, 0) as soluong, isnull(a.dongia, 0) as dongia, a.dongiagoc, "
						+ "isnull(a.chietkhau, 0) as chietkhau, isnull(a.thueVAT, 0) as thueVAT "
						+ "from ERP_DONDATHANGNPP_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK "
						+ "where a.DONDATHANG_FK = '"+dhid+"' ";
				
				double tongtien = 0;
				double tongtienck = 0;
				double tongvat = 0;
				double doanhso = 0;
				double tongsauck = 0;
				double tongsauvat = 0;
				ResultSet rsSP = db.get(query);
				while(rsSP.next())
				{
					String ma = rsSP.getString("ma");
					String ten = rsSP.getString("ten");
					String dongia = rsSP.getString("dongia");
					String dongiagoc = rsSP.getString("dongiagoc");
					String soluong = rsSP.getString("soluong");
					String ck = rsSP.getString("chietkhau");
					String vat = rsSP.getString("thueVAT");
					
					tongtien += Double.parseDouble(dongia) * Double.parseDouble(soluong);
					doanhso += (Double.parseDouble(dongiagoc) * Double.parseDouble(soluong) * (1 + Double.parseDouble(vat) / 100)); 
					tongtienck += Double.parseDouble(ck);
					tongvat += (Double.parseDouble(dongia) * Double.parseDouble(soluong) - Double.parseDouble(ck)) * (Double.parseDouble(vat) / 100); 
					
					table += "<tr><td>"+ma.trim()+"</td><td>"+ten.trim()+"</td><td>"+soluong.trim()+"</td><td>"+dongia.trim()+"</td><td></td></tr>";
				}
				tongsauck = tongtien - tongtienck;
				tongsauvat = tongsauck + tongvat;
				tongsauck = tongtien - tongtienck;
				tongsauvat = tongsauck + tongvat;
				tooltip += "</br> Tổng thành tiền: " + formatter.format(tongtien);
				tooltip += "</br> Tổng tiền CK: " + formatter.format(tongtienck);
				tooltip += "</br> Tổng tiền sau CK: " + formatter.format(tongsauck);
				tooltip += "</br> Tổng tiền sau VAT: " + formatter.format(tongsauvat);
				tooltip += "</br> Doanh số: " + formatter.format(doanhso);
				tooltip += "</br> Chiết khấu: 0";
				tooltip += "</br> Tổng thanh toán: " + formatter.format(tongsauvat - sotiengiam);
				tooltip += "</br> Ngày sửa: "+ngaysua;
				tooltip += "</br> Người sửa: "+nguoisua;
				
				
				query = "select b.SCHEME, a.SPMA, a.SOLUONG, c.ten from ERP_DONDATHANGNPP_CTKM_TRAKM a inner join CTKHUYENMAI b on a.CTKMID = b.PK_SEQ "+
						"left join sanpham c on a.SPMA = c.MA where DONDATHANGID = '"+dhid+"' ";
				
				ResultSet rsKM = db.get(query);
				while(rsKM.next())
				{
					String ma = rsKM.getString("spma")==null?"":rsKM.getString("spma");
					String ten = rsKM.getString("ten")==null?"":rsKM.getString("ten");
					String soluong = rsKM.getString("soluong")==null?"":rsKM.getString("soluong");
					String scheme = rsKM.getString("scheme");
					
					table += "<tr><td>"+ma.trim()+"</td><td>"+ten.trim()+"</td><td>"+soluong.trim()+"</td><td></td><td>"+scheme+"</td></tr>";
				}
				tooltip += table;
				
				query = "update ERP_DONDATHANGNPP set tooltip = N'" + tooltip + "' where pk_seq = " + dhid;
				System.out.println(tooltip);
				if(!db.update(query))
				{
					System.out.println("::: Loi cap nhat tooltip: " + query);
					db.getConnection().rollback();
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
