package geso.traphaco.erp.beans.baocaochiphiphongban.imp;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.baocaochiphiphongban.IBaoCaoChiPhiPhongBan;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;



public class BaoCaoChiPhiPhongBan  implements IBaoCaoChiPhiPhongBan  {
	String userId;
	String ctyId;	
	String ctyTen;
	String tungay;
	String denngay;
	ResultSet rsBaoCaoTongHop;
	ResultSet rsBaoCaoChiTiet;
	String dvthId;
	ResultSet dvthRs;
	String msg;
	dbutils db;
	String dvthTen;
	String[] phongBanIds;
	String QueryTH;
	String QueryCT;
	public BaoCaoChiPhiPhongBan () {
		this.userId = "";
		this.ctyId = "";
		this.ctyTen = "";
		this.dvthId="";
		this.msg = "";
		this.db = new dbutils();
		this.tungay = "";
		this.denngay = "";
		this.dvthTen = "";
		this.QueryTH = "";
		this.QueryCT= "";
	}

	public String getQueryTH() {
		return QueryTH;
	}

	public void setQueryTH(String queryTH) {
		QueryTH = queryTH;
	}

	public String getQueryCT() {
		return QueryCT;
	}

	public void setQueryCT(String queryCT) {
		QueryCT = queryCT;
	}

	public String[] getPhongBanIds() {
		return phongBanIds;
	}



	public void setPhongBanIds(String[] phongBanIds) {
		this.phongBanIds = phongBanIds;
	}



	public String getDvthTen() {
		return dvthTen;
	}



	public void setDvthTen(String dvthTen) {
		this.dvthTen = dvthTen;
	}



	public String getUserId() {
		return userId;
	}



	public void setUserId(String userId) {
		this.userId = userId;
	}



	public String getCtyId() {
		return ctyId;
	}



	public void setCtyId(String ctyId) {
		this.ctyId = ctyId;
	}



	public ResultSet getRsBaoCaoTongHop() {
		return rsBaoCaoTongHop;
	}



	public void setRsBaoCaoTongHop(ResultSet rsBaoCaoTongHop) {
		this.rsBaoCaoTongHop = rsBaoCaoTongHop;
	}



	public ResultSet getRsBaoCaoChiTiet() {
		return rsBaoCaoChiTiet;
	}



	public void setRsBaoCaoChiTiet(ResultSet rsBaoCaoChiTiet) {
		this.rsBaoCaoChiTiet = rsBaoCaoChiTiet;
	}



	public String getCtyTen() {
		return ctyTen;
	}



	public void setCtyTen(String ctyTen) {
		this.ctyTen = ctyTen;
	}



	public String getTungay() {
		return tungay;
	}



	public void setTungay(String tungay) {
		this.tungay = tungay;
	}



	public String getDenngay() {
		return denngay;
	}



	public void setDenngay(String denngay) {
		this.denngay = denngay;
	}


	public String getDvthId() {
		return dvthId;
	}



	public void setDvthId(String dvthId) {
		this.dvthId = dvthId;
	}



	public ResultSet getDvthRs() {
		
		String query = 
						"SELECT TOP 1  MAX( ISNULL(HIENTHIALL,'0') ) AS HIENTHIALL \n"+
						" FROM NHOMQUYEN  A INNER JOIN PHANQUYEN B ON A.DMQ_FK = B.DMQ_FK  \n"+
						" INNER JOIN UNGDUNG UD ON UD.PK_SEQ=A.UNGDUNG_FK \n"+
						"  WHERE B.NHANVIEN_FK="+this.userId+" AND UD.SERVLET='BaoCaoChiPhiPhongBanSvl'\n";
		int count =0;
		try {
			ResultSet rs = db.get(query);
			while(rs.next())
			{
				count = rs.getInt("HIENTHIALL");
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			this.msg="Lỗi trong quá trình lấy quyền";
		}
		query="";
		if(count ==1)
			query+= " select 0 as PK_SEQ, 'ALL' as TEN UNION ALL \n";
		query += "select PK_SEQ, MA+'-'+TEN as TEN from ERP_DONVITHUCHIEN  \n";
		if(count ==0)
				query+= " WHERE PK_SEQ IN (SELECT PHONGBAN_FK FROM NHANVIEN WHERE PK_SEQ = "+this.userId+")";
		System.out.println("Query phong ban:"+ query);
		this.dvthRs = db.get(query);
		return dvthRs;
	}



	public void setDvthRs(ResultSet dvthRs) {
		this.dvthRs = dvthRs;
	}



	public String getMsg() {
		return msg;
	}



	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void init()
	{
		String query = "";
		try {
			if(this.tungay.trim().length()>0 && this.denngay.trim().length()>0)
			{
				if(this.ctyId.trim().length()>0)
				{
					query = "SELECT TEN FROM ERP_CONGTY WHERE PK_SEQ =  "+this.ctyId;
					ResultSet rscongty = db.get(query);
					while(rscongty.next())
					{
						this.ctyTen = rscongty.getString("TEN");
					}
					rscongty.close();
				}
			/*	 String[] param = new String[4];	
				 param[0] =this.ctyId;
		 	     param[1] =(this.dvthId.trim().length() >0?this.dvthId:"0");
		 	     param[2] =this.tungay; 
		 	     param[3]=this.denngay;
		 	     query = "EXEC REPORT_CHIPHI_PHONGBAN "+this.ctyId+",";
		 	     rsBaoCaoTongHop = db.getRsByPro("REPORT_CHIPHI_PHONGBAN", param);
		 	    rsBaoCaoChiTiet = db.getRsByPro("REPORT_CHIPHI_PHONGBAN_CHITIET", param);
		 	    */
				
				
				if(this.phongBanIds != null){
					String tmp = "";
					for(int i = 0; i < this.phongBanIds.length; i++){
						tmp += this.phongBanIds[i] + ",";
					}
					this.dvthId = tmp.substring(0, tmp.length() - 1);
						
					System.out.println("Phòng ban ID : " + this.dvthId);
				}
		 	  /* query = "EXEC REPORT_CHIPHI_PHONGBAN "+this.ctyId+","+this.dvthId+",'"+this.tungay+"','"+this.denngay+"'";
		 	 System.out.println("REPORT_CHIPHI_PHONGBAN: "+query);
		 	   rsBaoCaoTongHop = db.get(query);
		 	 query = "EXEC REPORT_CHIPHI_PHONGBAN_CHITIET "+this.ctyId+","+this.dvthId+",'"+this.tungay+"','"+this.denngay+"'";
		 	 System.out.println("REPORT_CHIPHI_PHONGBAN_CHITIET: "+query);
		 	 rsBaoCaoChiTiet = db.get(query);*/
		 	   
				query = "DELETE FROM ERP_BAOCAO_CHIPHI_PHONGBAN";
				db.update(query);
				 query ="  INSERT INTO ERP_BAOCAO_CHIPHI_PHONGBAN (MA ,PHONGBAN,PK_SEQ,TENNGUOIDENGHI,LOAICHUNGTU ,SOCHUNGTU,TONGTIEN) \n"+
						" SELECT PB.MA,PB.TEN AS PHONGBAN,PB.PK_SEQ,NV.TEN AS TENNGUOIDENGHI,N'Tạm ứng' AS LOAICHUNGTU, \n"+
						" CONVERT(NVARCHAR,TU.PK_SEQ) AS SOCHUNGTU, ROUND(TU.SOTIENTAMUNGNT * TU.TIGIA,0) AS TONGTIEN FROM ERP_TAMUNG TU \n"+
						" INNER JOIN ERP_NHANVIEN NV ON NV.PK_SEQ = TU.NHANVIEN_FK \n"+
						" INNER JOIN ERP_DONVITHUCHIEN PB ON PB.PK_SEQ =NV.DVTH_FK \n"+
						" WHERE TU.TRANGTHAI =1 AND TU.NHANVIEN_FK IS NOT NULL AND TU.CONGTY_FK = "+this.ctyId+" AND TU.NGAYTAMUNG >='"+this.tungay+"' AND TU.NGAYTAMUNG <='"+this.denngay+"'  \n";
						if(!this.dvthId.equals("0"))
						query+=	" AND(PB.PK_SEQ  IN ( "+this.dvthId+")) \n";
								query+=	" UNION ALL \n"+
						" SELECT ISNULL(PB.MA,PB1.MA) AS MA,ISNULL(PB.TEN,PB1.TEN) AS PHONGBAN,ISNULL(PB.PK_SEQ,PB1.PK_SEQ) AS PK_SEQ,ISNULL(NV.TEN,NV1.TEN) AS TENNGUOIDENGHI,N'Đề nghị thanh toán' AS LOAICHUNGTU, \n"+
						" MH.soChungTu_Chu +MH.soChungTu_So AS SOCHUNGTU, ROUND(MH.TONGTIENAVAT * MH.TyGiaQuyDoi,0) AS TONGTIEN  \n"+
						" FROM ERP_MUAHANG MH \n"+
						" LEFT JOIN ERP_NHANVIEN NV ON NV.PK_SEQ = MH.NguoiDeNghi \n"+
						"  LEFT JOIN ERP_NHANVIEN NV1 ON NV1.PK_SEQ = MH.NHANVIEN_FK  \n "+
						" LEFT JOIN ERP_DONVITHUCHIEN PB ON PB.PK_SEQ =NV.DVTH_FK \n"+
						" LEFT JOIN ERP_DONVITHUCHIEN PB1 ON PB1.PK_SEQ =NV1.DVTH_FK \n"+
						"  WHERE MH.ISDNTT =1 AND ISDACHOT =1 AND MH.TRANGTHAI =1 AND (MH.ISKTV =1 OR MH.ISKTT =1) AND MH.quanlycongno =1 \n"+
						" AND MH.CONGTY_FK = "+this.ctyId+" AND MH.NGAYMUA >='"+this.tungay+"' AND MH.NGAYMUA <='"+this.denngay+"'  \n";
						if(!this.dvthId.equals("0"))
							query+=	" AND(PB.PK_SEQ  IN ( "+this.dvthId+")  OR PB1.PK_SEQ  IN ( "+this.dvthId+") ) \n";
						query+=	" UNION ALL \n"+
								" SELECT ISNULL(PB.MA,PB1.MA) AS MA,ISNULL(PB.TEN,PB1.TEN) AS PHONGBAN,ISNULL(PB.PK_SEQ,PB1.PK_SEQ) AS PK_SEQ,ISNULL(NV.TEN,NV1.TEN) AS TENNGUOIDENGHI,N'Đề nghị thanh toán' AS LOAICHUNGTU, \n"+
								" MH.soChungTu_Chu +MH.soChungTu_So AS SOCHUNGTU, ROUND(MH.TONGTIENAVAT * MH.TyGiaQuyDoi,0) AS TONGTIEN  \n"+
								" FROM ERP_MUAHANG MH \n"+
								" LEFT JOIN ERP_NHANVIEN NV ON NV.PK_SEQ = MH.NguoiDeNghi \n"+
								"  LEFT JOIN ERP_NHANVIEN NV1 ON NV1.PK_SEQ = MH.NHANVIEN_FK  \n "+
								" LEFT JOIN ERP_DONVITHUCHIEN PB ON PB.PK_SEQ =NV.DVTH_FK \n"+
								" LEFT JOIN ERP_DONVITHUCHIEN PB1 ON PB1.PK_SEQ =NV1.DVTH_FK \n"+
								"   WHERE MH.ISDNTT =1 AND ISDACHOT =1 AND MH.TRANGTHAI =1 AND (MH.ISKTV =1 OR MH.ISKTT =1) " +
								"	AND MH.quanlycongno =0" +
								"    AND MH.quanlycongno =0 "+
								"    AND  \n"+
								"    (		\n"+
								"		( 	\n" +
								"			MH.TONGTIENCONLAI >0 and MH.PK_SEQ IN (SELECT DNTT_FK FROM ERP_THANHTOANHOADON WHERE DNTT_FK = MH.PK_SEQ AND TRANGTHAI =1) \n"+
								"		) " +
								
								"		OR MH.TONGTIENCONLAI =0 \n"+
								"	) \n"+
								" AND MH.CONGTY_FK = "+this.ctyId+" AND MH.NGAYMUA >='"+this.tungay+"' AND MH.NGAYMUA <='"+this.denngay+"'  \n";
								if(!this.dvthId.equals("0"))
									query+=	" AND(PB.PK_SEQ  IN ( "+this.dvthId+") OR PB1.PK_SEQ  IN ( "+this.dvthId+")) \n";
						System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa \n"+query);
				db.update(query);
			// lấy báo cáo tổng hợp
			query = "SELECT ROW_NUMBER() OVER(ORDER BY A.MA ASC)  AS SOTT,A.MA,A.LOAICHUNGTU,SUM(A.TONGTIEN) AS TONGTIEN FROM  \n "+
					" ERP_BAOCAO_CHIPHI_PHONGBAN A  \n"+
					" GROUP BY A.MA,A.LOAICHUNGTU \n";
			
			System.out.println("REPORT_CHIPHI_PHONGBAN: "+query);
			this.QueryTH = query;
			  rsBaoCaoTongHop = db.get(query);
			  
			// lấy báo cáo chi tiết
			  query ="";
			  query = "SELECT * FROM ERP_BAOCAO_CHIPHI_PHONGBAN  A \n "+
						" ORDER BY A.PK_SEQ \n";
			  System.out.println("REPORT_CHIPHI_PHONGBAN_CHITIET: "+query);
			  this.QueryCT = "SELECT * FROM ERP_BAOCAO_CHIPHI_PHONGBAN";
			  rsBaoCaoChiTiet = db.get(query);
			  
			  
		 	   if(this.dvthId.trim().length()>0)
		 	    {
		 		   String tmp1 = "";
		 	    	query = "SELECT MA FROM ERP_DONVITHUCHIEN WHERE PK_SEQ in ( "+this.dvthId+")";
		 	    	System.out.println("Query: "+ query);
					ResultSet rsdv = db.get(query);
					while(rsdv.next())
					{
						if(rsdv.getString("MA")!=null)
						{
							tmp1 += rsdv.getString("MA")+ ",";
						}
						
					}
					
					rsdv.close();
					if(tmp1.trim().length()>0)
						this.dvthTen = tmp1.substring(0, tmp1.length() - 1);
		 	    }
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public void DBClose(){
		
			try {
				if(rsBaoCaoChiTiet !=null)rsBaoCaoChiTiet.close();
				if(rsBaoCaoTongHop !=null)rsBaoCaoTongHop.close();
				if(dvthRs !=null) dvthRs.close();
				if(db != null) db.shutDown();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	

}
