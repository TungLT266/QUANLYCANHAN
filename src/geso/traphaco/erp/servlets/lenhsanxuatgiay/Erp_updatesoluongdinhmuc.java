package geso.traphaco.erp.servlets.lenhsanxuatgiay;

import java.sql.ResultSet;

import geso.traphaco.erp.db.sql.dbutils;

		public class Erp_updatesoluongdinhmuc {
		
			public static void main ( String args [  ]  )   {
		  		dbutils db=new dbutils();
		  		
		  		try {
		  			Erp_updatesoluongdinhmuc dm= new Erp_updatesoluongdinhmuc();
		  		    String mss=	dm.update(db);
		  			System.out.println(" in ra: "+mss );
				} catch (Exception e) {
					e.printStackTrace();
				}
		  		
			}
			
		public  String update(dbutils db) {
			String mms=" Thanh cong";
			// update dinh muc cho vat tu thay the
			String sql="select LENHSANXUAT_FK, VATTU_FK, SANPHAM_FK from ERP_LENHSANXUAT_BOM_GIAY_CHITIET where VATTU_FK <> SANPHAM_FK ";
			ResultSet rs= db.get(sql);
			if(rs!= null){
				try {
					
					while (rs.next()) {
						String lsx_fk=rs.getString("LENHSANXUAT_FK");
						String VATTU_FK=rs.getString("VATTU_FK");
						String SANPHAM_FK=rs.getString("SANPHAM_FK");
						System.out.println("info: "+ lsx_fk + "--" +VATTU_FK +"--"+SANPHAM_FK );
						double soluongdinhmuc_thaythe=0.0;
						double soluongbom=0.0;
						double soluongsxlenh=0.0;
						double dinhmucbom_thaythe=0.0;
						String bomId="";
						// tim bom, soluong bom, soluong san xuat lsx
						sql="SELECT BOM.SOLUONGCHUAN AS SOLUONGBOM,LSX.SOLUONG AS SOLUONGSX, LSX.LENHSANXUAT_FK, LSX.DanhMucVT_FK FROM ERP_LENHSANXUAT_SANPHAM LSX  "
							+	" \n INNER JOIN ERP_DANHMUCVATTU BOM ON BOM.PK_SEQ= LSX.DanhMucVT_FK WHERE LSX.LENHSANXUAT_FK=" +lsx_fk;
						
						System.out.println(" bom :"+ sql);
						ResultSet rsbom= db.get(sql);	
						if(rsbom!= null){
							try {  
								 while (rsbom.next()){
									soluongbom=rsbom.getDouble("SOLUONGBOM");
									soluongsxlenh=rsbom.getDouble("SOLUONGSX");
									bomId=rsbom.getString("DanhMucVT_FK");
								 }
								
								
							} catch (Exception e) {
								e.printStackTrace();
							}
							rsbom.close();
						}
						// tim so luong dinh muc thay the
							sql="SELECT SOLUONG  FROM ERP_DANHMUCVATTU_VATTU_THAYTHE WHERE DANHMUCVT_FK="+bomId+" and VATTU_FK="+VATTU_FK +" AND VATTUTT_FK=" +SANPHAM_FK;
							System.out.println(" sl thay the :"+ sql);
							ResultSet rsthaythe= db.get(sql);	
							if(rsthaythe!= null){
								try {
									while (rsthaythe.next()){
									dinhmucbom_thaythe=rsthaythe.getDouble("SOLUONG");
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
								rsthaythe.close();
							}
						//quy doi
								if(soluongbom>0){
								soluongdinhmuc_thaythe= (soluongsxlenh*dinhmucbom_thaythe)/soluongbom;
								}
								System.out.println(" [soluongsxlenh]: "+ soluongsxlenh);
								System.out.println(" [dinhmucbom_thaythe]: "+ dinhmucbom_thaythe);
								System.out.println(" [soluongbom]: "+ soluongbom);
								System.out.println(" [soluongdinhmuc_thaythe]: "+ sql);
						// update cho erp_lenhsanxuat_bom_giay_chitet 
						sql="update ERP_LENHSANXUAT_BOM_GIAY_CHITIET SET SOLUONGDINHMUC="+soluongdinhmuc_thaythe +"  WHERE LENHSANXUAT_FK="+lsx_fk+" and VATTU_FK="+VATTU_FK +" AND SANPHAM_FK=" +SANPHAM_FK;
						System.out.println(" [update dinhmuc]: "+ sql);
						
						 if (! db.update(sql))
						 {
							 mms="LOI KO UPDATE DUOC";
						 }
					}
					rs.close();
					
					} catch (Exception e) {
						e.printStackTrace();
					}
			
			}
			
		
			return mms;
			
		}
	
	
}
