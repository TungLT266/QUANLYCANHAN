package app;

import geso.traphaco.center.db.sql.dbutils;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class CapnhatTooltipDNMH {
	public static void main(String[] args) {
		dbutils db = new dbutils();
		String query = "select pk_seq, NgayDeNghi, LOAIHANGHOA_FK from erp_denghimuahang where tooltip is null";

		try
		{
			db.getConnection().setAutoCommit(false);
			
			ResultSet rs = db.get(query);
			NumberFormat formatter = new DecimalFormat("#,###,###.##");
			while(rs.next())
			{
				String tooltip = "NULL";
				String table = "";
				String dhid = rs.getString("pk_seq");
				String ngaydenghi = rs.getString("NgayDeNghi");
				String lhhId = rs.getString("LOAIHANGHOA_FK")== null ? "" : rs.getString("LOAIHANGHOA_FK");
				tooltip = "Số Đơn hàng: "+ dhid +
						" </br> Ngày đề nghị: " + ngaydenghi;
				table = "</br><table><tr><th style=width: 10% >Mã SP</th><th style=width: 10% >Tên SP</th><th style=width: 10% >Số lượng</th></tr>";
			
				query = "select  isnull(b.pk_seq,0) as spid,  isnull(b.ma, '' ) as spMa,  isnull(a.diengiai, b.ten )   as spTen,   "
						+ "b.ten    as spTen2, 'NA' as spNh,  isnull(tscd.pk_seq,0) as tscdid, isnull(tscd.ma, '') as tscdMa, isnull(a.diengiai, tscd.ten) as tscdTen, "
						+ "isnull(nts.ma, 'NA') as nstNh,   isnull(ccdc.pk_seq,0) as ccdcid, isnull(ccdc.ma, '') as ccdcMa, isnull(a.diengiai, ccdc.DIENGIAI) as ccdcTen,   "
						+ "isnull(ncp.pk_seq,0) as ncpid,isnull(ncp.ten, '') as ncpMa, isnull(a.diengiai, ncp.diengiai) as ncpTen, isnull(ttcp.diengiai, 'NA') as ncpNh,  "
						+ "isnull(a.donvi, '') as donvi, a.soluong, a.soluongduyet from (	select AVG(PK_SEQ) AS PK_SEQ, denghi_FK, SANPHAM_FK, CHIPHI_FK, "
						+ "TAISAN_FK, DIENGIAI, SUM(SOLUONG) AS SOLUONG, SUM(SOLUONGDUYET) AS SOLUONGDUYET, DONVI, CCDC_FK	"
						+ "from ERP_denghiMUAHANG_SP WHERE denghi_FK = '"+dhid+"' 	"
						+ "GROUP BY denghi_FK, SANPHAM_FK, CHIPHI_FK, TAISAN_FK, DIENGIAI, DONVI, CCDC_FK ) a left join  SANPHAM b on a.sanpham_fk = b.pk_seq    "
						+ "left join	erp_taisancodinh tscd on a.taisan_fk = tscd.pk_seq   left join erp_nhomtaisan nts on tscd.NhomTaiSan_fk = nts.pk_seq    "
						+ "left join	erp_congcudungcu ccdc on a.ccdc_fk = ccdc.pk_seq   "
						+ "left join erp_nhomchiphi ncp on a.chiphi_fk = ncp.pk_seq  left join  erp_trungtamchiphi ttcp on ncp.ttchiphi_fk = ttcp.pk_seq   "
						+ "where denghi_fk = '"+dhid+"' order by a.pk_seq asc ";
				
				
				ResultSet rsSP = db.get(query);
				String ma="", ten="", soluong="";
				while(rsSP.next())
				{
					if(lhhId.equals("0"))
					{
						ma = rsSP.getString("spMa");
						ten = rsSP.getString("spTen2");
					}
					else
					{
						if(lhhId.equals("1")) //Tai san co dinh
						{
							ma = rsSP.getString("tscdMa");
							ten = rsSP.getString("tscdTen");
						}
						else
						{
							if(lhhId.equals("3")) //Cong cu dung cu
							{
								ma = rsSP.getString("ccdcMa");
								ten = rsSP.getString("ccdcTen");
							}
							else
							{
								ma = rsSP.getString("ncpMa");
								ten = rsSP.getString("ncpTen");
							}
						}
					}
					
					soluong = formatter.format(rsSP.getDouble("soluong"));
					
					table += "<tr><td>"+ma.trim()+"</td><td>"+ten.trim()+"</td><td>"+soluong.trim()+"</td></tr>";
				}
				table += "</table>";
				tooltip += table;
				
				query = "update erp_denghimuahang set tooltip = N'" + tooltip + "' where pk_seq = " + dhid;
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
