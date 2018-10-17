package geso.traphaco.erp.beans.huychungtu.imp;
import geso.traphaco.center.db.sql.Idbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.huychungtu.IErpHuylenhsanxuat;
import geso.traphaco.erp.db.sql.dbutils;
/*import geso.traphaco.erp.util.Kho_Lib;
import geso.traphaco.erp.util.Utility_Kho;*/

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.erp.util.Kho_Lib;
import geso.traphaco.center.util.Utility_Kho;


public class ErpHuylenhsanxuat implements IErpHuylenhsanxuat
{
	String congtyId;
	String userId;
	String id;
	String sochungtu;
	String ttnccId;
	String hdId;
	String tieuhaoId;
	String qcId;
	String pnkId;
	String pnhId;
	String dmhId;
	String kdclId_new="";
	public String getKdclId_new() {
		return kdclId_new;
	}
	public void setKdclId_new(String kdclId_new) {
		this.kdclId_new = kdclId_new;
	}
	String msg;
	String dienGiaiCT;
	String Ngayghinhan;
	String loaichungtu="";
	ResultSet sochungtuRequest;
	ResultSet sochungtuDetail;

	Utility_Kho util_kho =new Utility_Kho();

	dbutils db;
	private Utility util;
	NumberFormat formatter = new DecimalFormat("#######.######");
	public ErpHuylenhsanxuat()
	{
		this.id = "";
		this.sochungtu = "";
		this.ttnccId = "";
		this.loaichungtu="";
		this.hdId = "";

		this.tieuhaoId = "";
		this.qcId = "";
		this.pnkId = "";
		this.pnhId = "";
		this.dmhId = "";
		this.Ngayghinhan="";
		this.msg = "";
		this.dienGiaiCT = "";
		this.util=new Utility();
		this.db = new dbutils();
		this.util_kho=new Utility_Kho();
	}
	public ErpHuylenhsanxuat(String id)
	{
		this.id = id;
		this.sochungtu = "";
		this.ttnccId = "";
		this.hdId = "";
		this.tieuhaoId = "";
		this.qcId = "";
		this.pnkId = "";
		this.pnhId = "";
		this.Ngayghinhan="";
		this.dmhId = "";
		this.msg = "";
		this.dienGiaiCT= "";
		this.util=new Utility();
		this.db = new dbutils();
	}

	public String getId() 
	{
		return this.id;
	}

	public void setId(String Id) 
	{
		this.id = Id;
	}

	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getSoThanhtoan() 
	{
		return this.ttnccId;
	}

	public void setSoThanhtoan(String sothanhtoan) 
	{
		this.ttnccId = sothanhtoan;
	}

	public String getSoHoadon() 
	{
		return this.hdId;
	}

	public void setSoHoadon(String sohoadon) 
	{
		this.hdId = sohoadon;
	}

	public String getSophieunhapkho() 
	{
		return this.pnkId;
	}

	public void setSoPhieunhapkho(String sonhapkho)
	{
		this.pnkId = sonhapkho;
	}

	public String getSoPhieunhanhang() 
	{
		return this.pnhId;
	}

	public void setSoPhieunhanhang(String sonhanhang)
	{
		this.pnhId = sonhanhang;
	}

	public String getSoDonMuahang() 
	{
		return this.dmhId;
	}

	public void setSoDonmuahang(String somuahang) 
	{
		this.dmhId = somuahang;
	}

	public void createRs()
	{
		this.dmhId = "";
		this.pnhId = "";
		this.pnkId = "";
		this.hdId = "";
		this.ttnccId = "";

		if( this.sochungtu.length() > 0 )
		{ 
			//this.createRsLsx();

			if(this.loaichungtu.equals("LSX")){
				this.createRs_LSX();
			}
			else if(this.loaichungtu.equals("NK")){
				this.createRs_NK();

			}
			else if(this.loaichungtu.equals("CHUYENKHO")){
				this.createRs_CK();
			}

			else if(this.loaichungtu.equals("KDCL")){

				this.createRs_KDCL();
			} else if(this.loaichungtu.equals("THNL")){

				this.createRs_THNL();
			}	
			 else if(this.loaichungtu.equals("NKM")){ // nhan hang moi
	
				this.createRs_NKM();
			}	
			 else if(this.loaichungtu.equals("KDCLM")){  // kiem dinh chat luong moi
				 this.kdclId_new= this.sochungtu.substring(3, this.sochungtu.length());
				 System.out.println(" so chung tu : " +  this.sochungtu);
				 
				 this.createRs_KDCLM();
			}	
		}	

	}


	private void createRs_THNL() {
		// TODO Auto-generated method stub
		String requestMh= 	" 	SELECT A.PK_SEQ,   CAST(A.PK_SEQ AS VARCHAR(20)) AS SOCHUNGTU, A.NGAYTIEUHAO AS NGAYCHUNGTU, "+   
							" CASE A.TRANGTHAI WHEN 0 THEN N'Chờ kiểm' WHEN 1 THEN N'Đã Tiêu hao' ELSE N'Đã hủy' END AS TRANGTHAI, "+   
							" N'Tiêu hao nguyên liệu' AS LOAICHUNGTU, A.NGAYTAO,'THNL' as type , 1 AS STT "+    
							" FROM ERP_TIEUHAONGUYENLIEU A "+    
							" WHERE A.PK_SEQ =  "+this.sochungtu + 
							" AND A.TRANGTHAI IN ('1')    " ;
					 
		
	  
		this.sochungtuRequest = db.get(requestMh);
	}
	private void createRs_LSX(){

		try{
			String requestMh = "";
			String qrlsx = "";
			String qrck = "";
			String qrnk = "";
			String qrkdcl = "";

			// yeu cau kiem dinh
			qrkdcl= " 	SELECT A.PK_SEQ,   CAST(A.PK_SEQ AS VARCHAR(20)) AS SOCHUNGTU, A.NGAYKIEM AS NGAYCHUNGTU,   " + 
					"\n 	CASE A.TRANGTHAI WHEN 0 THEN N'Chờ kiểm' WHEN 1 THEN N'Đã kiểm' ELSE N'HOÀN TẤT' END AS TRANGTHAI,   " + 
					"\n 	N'YÊU CẦU KIỂM ĐỊNH' AS LOAICHUNGTU, A.NGAYTAO,'KDCL' as type , 4 AS STT   " + 
					"\n 	FROM ERP_YEUCAUKIEMDINH A   " + 
					"\n 	WHERE A.LENHSANXUAT_FK =       " + this.sochungtu + 
					"\n 	AND A.TRANGTHAI IN ('0','1' )   " ;

					// nhap kho
					qrnk ="  SELECT A.PK_SEQ,   CAST(A.PK_SEQ AS VARCHAR(20)) AS SOCHUNGTU, A.NGAYNHAN AS NGAYCHUNGTU,   " + 
					"\n 	CASE A.TRANGTHAI WHEN 0 THEN N'CHƯA CHỐT' WHEN 1 THEN N'ĐÃ CHỐT' ELSE N'HOÀN TẤT' END AS TRANGTHAI,   " + 
					"\n 	N'NHẬP KHO' AS LOAICHUNGTU, A.NGAYTAO, 'NKM' as type , 3 AS STT  " + 
					"\n 	FROM ERP_NHANHANG A   " + 
					"\n 	WHERE A.LENHSANXUAT_FK =" +this.sochungtu + 
					"\n 	AND A.TRANGTHAI  IN ( 1,2)  AND A.CONGTY_FK = '"+this.congtyId+"'" ;
					
					

					// chuyen kho
					qrck = "SELECT  B.PK_SEQ,   CAST(B.PK_SEQ AS VARCHAR(20)) AS SOCHUNGTU, B.NGAYCHUYEN AS NGAYCHUNGTU,     " +  
					"\n  CASE B.TRANGTHAI WHEN '0' THEN N'CHƯA CHỐT'   " +  
					"\n  WHEN '1' THEN N'CHỜ NHẬN HÀNG/CHỜ XUẤT KHO' WHEN '2' THEN N'ĐÃ NHẬN HÀNG/ĐÃ XUẤT KHO'    " +  
					"\n  WHEN '3' THEN N'HOÀN TẤT' END  AS TRANGTHAI ,     " +  
					"\n  N'CHUYỂN NGUYÊN LIỆU' AS LOAICHUNGTU, B.NGAYTAO,'CHUYENKHO' as type , 2 AS STT      " +  
					"\n  FROM ERP_CHUYENKHO B    " +  
					"\n  WHERE B.LENHSANXUAT_FK= "+ this.sochungtu +" AND B.TRANGTHAI <>4 UNION AlL  " +
					" 	SELECT A.PK_SEQ,   CAST(A.PK_SEQ AS VARCHAR(20)) AS SOCHUNGTU, A.NGAYTIEUHAO AS NGAYCHUNGTU, "+   
							" CASE A.TRANGTHAI WHEN 0 THEN N'Chờ kiểm' WHEN 1 THEN N'Đã tiêu hao' ELSE N'Đã hủy' END AS TRANGTHAI, "+   
							" N'Tiêu hao nguyên liệu' AS LOAICHUNGTU, A.NGAYTAO,'THNL' as type , 5 AS STT "+    
							" FROM ERP_TIEUHAONGUYENLIEU A "+    
							" WHERE A.LENHSANXUAT_FK  =  "+this.sochungtu + 
							" AND A.TRANGTHAI IN ('1')    " ;
					
					 

	
				// lenh san xuat
				qrlsx = "SELECT  A.PK_SEQ,   CAST(A.PK_SEQ AS VARCHAR(20)) AS SOCHUNGTU, A.NGAYBATDAU AS NGAYCHUNGTU,      "+    
				"\n	CASE A.TRANGTHAI WHEN 0 THEN N'Chưa kích hoạt' WHEN 1 THEN N'Chưa kích hoạt - Đã yêu cầu chuyển kho'  "+    
				"\n	WHEN 2 THEN N'Đã kích hoạt' WHEN 3 THEN N'Đã nhận thành phẩm'  "+    
				"\n	WHEN 4 THEN N'Đã kiểm định chất lượng' WHEN 5 THEN N'Đã tiêu hao nguyên liệu'  "+    
				"\n	WHEN 6 THEN N'Hoàn tất' END AS TRANGTHAI,N'LỆNH SẢN XUẤT' AS LOAICHUNGTU, "+    
				"\n	A.NGAYTAO,'LSX' as type ,  1 AS STT   FROM  ERP_LENHSANXUAT_GIAY A     "+    
				"\n	WHERE A.PK_SEQ= "+ this.sochungtu +"  AND A.TRANGTHAI <> 7 ";



			requestMh= qrkdcl +"\n     UNION ALL  \n" +  qrnk+ "\n     UNION ALL  \n"  + qrck + "\n     UNION ALL  \n" +qrlsx + "  ORDER BY STT DESC ";

			System.out.println("1.Create LSX LIST la: " + requestMh);
			this.sochungtuRequest = db.get(requestMh);

		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("Exception : " + e.getMessage());
		}
	}


	private void createRs_CK(){

		try{
			String requestMh = "";
			String qrlsx = "";
			String qrck = "";
			String qrnk = "";
			String qrkdcl = "";

			// chuyen kho NGUYEN LIEU
			qrck = "SELECT  B.PK_SEQ,   CAST(B.PK_SEQ AS VARCHAR(20)) AS SOCHUNGTU, B.NGAYCHUYEN AS NGAYCHUNGTU,     " +  
			"\n  CASE B.TRANGTHAI WHEN '0' THEN N'CHƯA CHỐT'   " +  
			"\n  WHEN '1' THEN N'CHỜ NHẬN HÀNG/CHỜ XUẤT KHO' WHEN '2' THEN N'ĐÃ NHẬN HÀNG/ĐÃ XUẤT KHO'    " +  
			"\n  WHEN '3' THEN N'HOÀN TẤT' END  AS TRANGTHAI ,     " +  
			"\n  N'CHUYỂN NGUYÊN LIỆU' AS LOAICHUNGTU, B.NGAYTAO,'CHUYENKHO' as type , 1 AS STT      " +  
			"\n  FROM ERP_CHUYENKHO B    " +  
			"\n  WHERE B.PK_SEQ= "+ this.sochungtu +" AND B.TRANGTHAI <>4 AND LENHSANXUAT_FK IS NOT NULL ";


			//requestMh= qrkdcl +"\n     UNION ALL  \n" +  qrnk+ "\n     UNION ALL  \n"  + qrck +"  ORDER BY STT ";

			System.out.println("1.Create CHUYEN KHO LIST la: " + qrck);
			this.sochungtuRequest = db.get(qrck);

		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("Exception : " + e.getMessage());
		}
	}

	private void createRs_NK(){

		try{
			String requestMh = "";
			String qrlsx = "";
			String qrck = "";
			String qrnk = "";
			String qrkdcl = "";

			// yeu cau kiem dinh
			qrkdcl= " 	SELECT A.PK_SEQ,   CAST(A.PK_SEQ AS VARCHAR(20)) AS SOCHUNGTU, A.NGAYKIEM AS NGAYCHUNGTU,   " + 
			"\n 	CASE A.TRANGTHAI WHEN 0 THEN N'Chờ kiểm' WHEN 1 THEN N'Đã kiểm' ELSE N'HOÀN TẤT' END AS TRANGTHAI,   " + 
			"\n 	N'YÊU CẦU KIỂM ĐỊNH' AS LOAICHUNGTU, A.NGAYTAO,'KDCL' as type , 2 AS STT   " + 
			"\n 	FROM ERP_YEUCAUKIEMDINH A   " + 
			"\n 	WHERE A.nhapkho_fk =       " + this.sochungtu + 
			"\n 	AND A.TRANGTHAI IN ('0','1')   " ;

			// nhap kho
			qrnk ="  SELECT A.PK_SEQ,   CAST(A.PK_SEQ AS VARCHAR(20)) AS SOCHUNGTU, A.NGAYNHAPKHO AS NGAYCHUNGTU,   " + 
			"\n 	CASE A.TRANGTHAI WHEN 0 THEN N'CHƯA CHỐT' WHEN 1 THEN N'ĐÃ CHỐT' ELSE N'HOÀN TẤT' END AS TRANGTHAI,   " + 
			"\n 	N'NHẬP KHO' AS LOAICHUNGTU, A.NGAYTAO, 'NK' as type , 1 AS STT  " + 
			"\n 	FROM ERP_NHAPKHO A   " + 
			"\n 	WHERE A.PK_SEQ =" +this.sochungtu + 
			"\n 	AND A.TRANGTHAI = 1  AND A.CONGTY_FK = '"+this.congtyId+"'" ;



			requestMh= qrkdcl +"\n     UNION ALL  \n" +  qrnk+ "  ORDER BY STT DESC ";

			System.out.println("1.Create NHAP KHO LIST la: " + requestMh);
			this.sochungtuRequest = db.get(requestMh);

		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("Exception : " + e.getMessage());
		}
	}
	
	
	
	private void createRs_NKM(){

		try{
			String requestMh = "";
			String qrnk = "";
			String qrkdcl = "";

			//chuyen kho
			String	requestCK = "\n SELECT CK.PK_SEQ, CAST(CK.PK_SEQ AS VARCHAR(20)) AS SOCHUNGTU, CK.NGAYCHUYEN AS NGAYCHUNGTU,   "+
			"\n CASE CK.TRANGTHAI WHEN 0 THEN N'CHƯA CHỐT' WHEN 1 THEN N'ĐÃ CHỐT' WHEN 2 THEN N'ĐÃ NHẬN HÀNG'  ELSE N'HOÀN TẤT' END AS TRANGTHAI, "+   
			"\n N'CHUYỂN KHO' AS LOAICHUNGTU, CK.NGAYTAO, 'CK' as type, 3 AS STT   "+
			"\n FROM 	 ERP_CHUYENKHO CK WHERE CK.YCKD_FK in (select pk_seq from ERP_YeuCauKiemDinh where nhanhang_fk in ( " + this.sochungtu  + " )   ) AND CK.TRANGTHAI IN (0,1,2,3)  "+
			
			 " union all " +
			 "  SELECT CK.PK_SEQ, CAST(CK.PK_SEQ AS VARCHAR(20)) AS SOCHUNGTU, CK.NGAYCHUYEN AS NGAYCHUNGTU,   "+
				"\n CASE CK.TRANGTHAI WHEN 0 THEN N'CHƯA CHỐT' WHEN 1 THEN N'ĐÃ CHỐT' WHEN 2 THEN N'ĐÃ NHẬN HÀNG'  ELSE N'HOÀN TẤT' END AS TRANGTHAI, "+   
				"\n N'CHUYỂN KHO' AS LOAICHUNGTU, CK.NGAYTAO, 'CK' as type, 4 AS STT   "+
				"\n FROM 	 ERP_CHUYENKHO CK WHERE CK.NHANHANG_FK in ( " + this.sochungtu  + "   )  AND CK.TRANGTHAI IN (0,1,2,3)  ";
		
			// yeu cau kiem dinh
			qrkdcl= " 	SELECT A.PK_SEQ, '200' +  CAST(A.PK_SEQ AS VARCHAR(20)) AS SOCHUNGTU, A.NGAYKIEM AS NGAYCHUNGTU,   " + 
			"\n 	CASE A.TRANGTHAI WHEN 0 THEN N'Chờ kiểm' WHEN 1 THEN N'Đã kiểm' ELSE N'HOÀN TẤT' END AS TRANGTHAI,   " + 
			"\n 	N'YÊU CẦU KIỂM ĐỊNH' AS LOAICHUNGTU, A.NGAYTAO,'KDCLM' as type , 2 AS STT   " + 
			"\n 	FROM ERP_YEUCAUKIEMDINH A   " + 
			"\n 	WHERE A.NHANHANG_FK =       " + this.sochungtu + 
			"\n 	AND A.NHANHANG_FK IS NOT NULL AND A.TRANGTHAI IN ('0','1','2')   " ;

			// nhap kho moi ( nhan hang san xuat)
			qrnk ="  SELECT A.PK_SEQ,   CAST(A.PK_SEQ AS VARCHAR(20)) AS SOCHUNGTU, A.NGAYNHAN  AS NGAYCHUNGTU,   " + 
			"\n 	CASE A.TRANGTHAI WHEN 0 THEN N'CHƯA CHỐT' WHEN 1 THEN N'ĐÃ CHỐT' WHEN 3 THEN N'HOÀN TẤT' ELSE N'NA' END AS TRANGTHAI,   " + 
			"\n 	N'NHẬP KHO MỚI' AS LOAICHUNGTU, A.NGAYTAO, 'NKM' as type , 1 AS STT  " + 
			"\n 	FROM ERP_NHANHANG A   " + 
			"\n 	WHERE A.PK_SEQ =" +this.sochungtu + 
			"\n 	AND A.LENHSANXUAT_FK IS NOT NULL  AND  A.TRANGTHAI in (1,2)  AND A.CONGTY_FK = '"+this.congtyId+"'" ;



			requestMh= requestCK  +"\n     UNION ALL  \n" +  qrkdcl +"\n     UNION ALL  \n" +  qrnk+ "  ORDER BY STT DESC ";

			System.out.println("1.Create NHAP KHO MOI LIST la: " + requestMh);
			this.sochungtuRequest = db.get(requestMh);

		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("Exception : " + e.getMessage());
		}
	}


	private void createRs_KDCL(){

		try{
			String requestMh = "";
			String qrlsx = "";
			String qrck = "";
			String qrnk = "";
			String qrkdcl = "";

			// yeu cau kiem dinh
			qrkdcl= " 	SELECT A.PK_SEQ,   CAST(A.PK_SEQ AS VARCHAR(20)) AS SOCHUNGTU, A.NGAYKIEM AS NGAYCHUNGTU,   " + 
			"\n 	CASE A.TRANGTHAI WHEN 0 THEN N'Chờ kiểm' WHEN 1 THEN N'Đã kiểm' ELSE N'HOÀN TẤT' END AS TRANGTHAI,   " + 
			"\n 	N'YÊU CẦU KIỂM ĐỊNH' AS LOAICHUNGTU, A.NGAYTAO,'KDCL' as type , 1 AS STT   " + 
			"\n 	FROM ERP_YEUCAUKIEMDINH A   " + 
			"\n 	WHERE A.PK_SEQ =       " + this.sochungtu + 
			"\n 	AND A.TRANGTHAI IN ('1','2')   " +
			"\n union all " +
			"\n SELECT CK.PK_SEQ, CAST(CK.PK_SEQ AS VARCHAR(20)) AS SOCHUNGTU, CK.NGAYCHUYEN AS NGAYCHUNGTU,   "+
			"\n CASE CK.TRANGTHAI WHEN 0 THEN N'CHƯA CHỐT' WHEN 1 THEN N'ĐÃ CHỐT' WHEN 2 THEN N'ĐÃ NHẬN HÀNG'  ELSE N'HOÀN TẤT' END AS TRANGTHAI, "+   
			"\n N'CHUYỂN KHO' AS LOAICHUNGTU, CK.NGAYTAO,'CHUYENKHO' as type  , 2 AS STT  "+
			"\n FROM 	 ERP_CHUYENKHO CK WHERE CK.YCKD_FK in ("+this.sochungtu +" ) AND CK.TRANGTHAI IN (0,1,2,3) " +
		    "\n order by stt  DESC";
			
			
			requestMh= qrkdcl;

			System.out.println("1.Create YEU CAU KIEM DINH LIST la: " + requestMh);
			this.sochungtuRequest = db.get(requestMh);

		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("Exception : " + e.getMessage());
		}
	}



	private void createRs_KDCLM(){

		try{
			String requestMh = "";
			String qrlsx = "";
			String qrck = "";
			String qrnk = "";
			String qrkdcl = "";

			// yeu cau kiem dinh
			qrkdcl= " 	SELECT A.PK_SEQ, '200' +  CAST(A.PK_SEQ AS VARCHAR(20)) AS SOCHUNGTU, A.NGAYKIEM AS NGAYCHUNGTU,   " + 
					"\n 	CASE A.TRANGTHAI WHEN 0 THEN N'Chờ kiểm' WHEN 1 THEN N'Đã kiểm' ELSE N'HOÀN TẤT' END AS TRANGTHAI,   " + 
					"\n 	N'YÊU CẦU KIỂM ĐỊNH MỚI' AS LOAICHUNGTU, A.NGAYTAO,'KDCLM' as type , 1 AS STT   " + 
					"\n 	FROM ERP_YEUCAUKIEMDINH A   " + 
					"\n 	WHERE A.PK_SEQ =       " + this.kdclId_new + 
					"\n 	AND A.NHANHANG_FK IS NOT NULL AND A.TRANGTHAI IN ('1','2')   " +
					"\n union all " +
					"\n SELECT CK.PK_SEQ, CAST(CK.PK_SEQ AS VARCHAR(20)) AS SOCHUNGTU, CK.NGAYCHUYEN AS NGAYCHUNGTU,   "+
					"\n CASE CK.TRANGTHAI WHEN 0 THEN N'CHƯA CHỐT' WHEN 1 THEN N'ĐÃ CHỐT' WHEN 2 THEN N'ĐÃ NHẬN HÀNG'  ELSE N'HOÀN TẤT' END AS TRANGTHAI, "+   
					"\n N'CHUYỂN KHO' AS LOAICHUNGTU, CK.NGAYTAO,'CHUYENKHO' as type  , 2 AS STT  "+
					"\n FROM 	 ERP_CHUYENKHO CK WHERE CK.YCKD_FK in ("+this.kdclId_new +" ) AND CK.TRANGTHAI IN (0,1,2,3) " +
				    "\n order by stt  DESC";
			
			
			requestMh= qrkdcl;

			System.out.println("1.Create YEU CAU KIEM DINH MOI LIST la: " + requestMh);
			this.sochungtuRequest = db.get(requestMh);

		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("Exception : " + e.getMessage());
		}
	}


 
	public void init() 
	{
		String query = "SELECT LOAICHUNGTU,NGAYGHINHAN, LENHSANXUAT_FK, TRANGTHAI,NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, CONGTY_FK,DIENGIAI FROM ERP_HUYLENHSANXUAT WHERE PK_SEQ = '" + this.id + "'";

		ResultSet rs = db.get(query);
		try
		{
			if (rs != null)
			{
				while(rs.next())
				{
					this.sochungtu = rs.getString("LENHSANXUAT_FK");
					this.Ngayghinhan=rs.getString("ngayghinhan");
					this.loaichungtu=rs.getString("loaichungtu");
					this.dienGiaiCT = rs.getString("DIENGIAI");
				}

				rs.close();
			}			
			query = " SELECT  HUYLENHSANXUAT_FK, THUTU AS STT, SOCHUNGTU, NGAYCHUNGTU, TRANGTHAI, LOAICHUNGTU FROM ERP_HUYLENHSANXUAT_CHUNGTU " +
					" WHERE HUYLENHSANXUAT_FK = '" + this.id + "' ORDER BY STT  , SOCHUNGTU DESC";

			this.sochungtuRequest = db.get(query);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}


	public boolean createHct(String[] socthuy, String[] soct, String[] ngaychungtu, String[] trangthai, String[] loaichungtu, String[] ngaytao, String[] thutu,String type[], String chon[])
	{

		if(socthuy== null || socthuy.length ==0){
			this.msg = "Bạn phải chọn chứng từ hủy.";
			return false;
		}
		if( this.Ngayghinhan.trim().length() <= 0 )
		{
			this.msg = "Bạn phải chọn ngày ghi nhận.";
			return false;
		}
		String msg1=util.checkNgayHopLe(db,this.Ngayghinhan);
		if(msg1.length() >0)
		{
			this.msg = msg1;
			return false;
		}

		try
		{
			db.getConnection().setAutoCommit(false);
			
			
			//B1.KIỂM TRA XEM CHỨNG TỪ NÀY ĐÃ HỦY CHƯA
			String query =  
			" SELECT count(pk_seq) as sodong" +
	        " FROM 	 ERP_HUYLENHSANXUAT " +
			" WHERE  trangthai != '2' and LENHSANXUAT_FK = '" + this.sochungtu + "'" +
			"        and loaichungtu = '"+ this.loaichungtu +"' ";
			
			ResultSet rsCheck = db.get(query);
			
			int count = 0;
			if(rsCheck.next())
			{
				count = rsCheck.getInt("sodong");
				rsCheck.close();
			}
			
			if(count > 0)
			{
				this.msg = "Số chứng từ này đã hủy rồi, vui lòng nhập số chứng từ khác";
				System.out.println(this.msg);
				return false;
			}
			

			//khong cam ham tao moi, mac dinh tao moi xong se chot luong
			query =  " INSERT INTO ERP_HUYLENHSANXUAT ( ngayghinhan, LENHSANXUAT_FK, trangthai,ngaytao, nguoitao, ngaysua, nguoisua, congty_fk,loaichungtu,DIENGIAI ) " +
			" VALUES('" + this.Ngayghinhan + "', '" + this.sochungtu + "', '1','" + this.getDateTime() + "', '" + this.userId + "', '" + this.getDateTime() + "', '" + this.userId + "',"+this.congtyId+",'"+this.loaichungtu+"',N'"+this.dienGiaiCT+"')";

			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = "Khong the tao moi ERP_HUYLENHSANXUAT, " + query;
				return false;
			}

			String hctbhCurrent = "";
			query = "select IDENT_CURRENT('ERP_HUYLENHSANXUAT') as hctbhId";

			ResultSet rsTthd = db.get(query);	
			if (rsTthd != null)
			{
				if(rsTthd.next())
				{
					hctbhCurrent = rsTthd.getString("hctbhId");
				}
				rsTthd.close();
			}			

			this.id = hctbhCurrent;

			if(socthuy != null)
			{
				for(int i = 0; i < soct.length; i++)
				{
					 System.out.println(" chon: "+chon[i]);
					  
					 if(chon[i].equals("1")){
								query = " insert ERP_HUYLENHSANXUAT_CHUNGTU(HUYLENHSANXUAT_FK, thutu, sochungtu, ngaychungtu, trangthai, loaichungtu,TYPE) " +
								" values('" + hctbhCurrent + "', '" + thutu[i] + "',  '" + soct[i] + "', '" + ngaychungtu[i] + "', N'" + trangthai[i] + "', N'" + loaichungtu[i] + "','"+type[i]+"')";
							
								System.out.println(" insert thong tin: "+query);
								if(!db.update(query))
								{
									db.getConnection().rollback();
									this.msg = "Không thể tạo mới ERP_HUYLENHSANXUAT_CHUNGTU, " + query;
									return false;
								}
								if(!this.RevertChungtu(type[i],soct[i], db)){
									db.getConnection().rollback();
									return false;
								}

					 }
				}
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);

			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			return false;
		}
	}

	private boolean RevertChungtu(String loaichungtu, String sochungtu, dbutils db) {
		// TODO Auto-generated method stub
		try{
			System.out.println("loaichung tu là: "+loaichungtu);
			if(loaichungtu.equals("KDCL")){
				//HỦY CHUYỂN KHO CỦA YÊU CẦU KIEM DINH
				String msg1= Revert_YeucauKiemDinh(sochungtu,db,"YCKD");
				if(msg1.length()> 0){
					this.msg="Lỗi hủy  yêu cầu kiểm định:" +msg1;
					db.getConnection().rollback();
					return false;
				}
			}
			else if(loaichungtu.equals("NK")){
				//HỦY CHUYỂN KHO CỦA YÊU CẦU
				
				String msg1=Revert_NhapKho(sochungtu,db);
				System.out.println(" huy nhập kho: "+msg1);
				if(msg1.length() > 0 ){
					this.msg="Lỗi hủy nhập kho:" +msg1;
					db.getConnection().rollback();
					return false;	
				}
					
			}else if(loaichungtu.equals("CHUYENKHO")){
				//HỦY CHUYỂN KHO NGUYEN LIEU
				System.out.println("da vao revert chuyen kho ");
			    String query="select PK_SEQ,TRANGTHAI from ERP_CHUYENKHO where TRANGTHAI <>4 AND PK_SEQ= "+sochungtu;
			    System.out.println("da vao revert chuyen kho:  "+query);
				ResultSet rsck=db.get(query);
				while(rsck.next()){
					String ckid=rsck.getString("PK_SEQ");
					String msg1= Huychungtu_Chuyenkho(db,ckid,rsck.getString("trangthai"));
					if(msg1.length()>0){
						System.out.println("Huy  chuyen kho : "+msg1);
						this.msg = "Lỗi hủy chuyển kho:" + msg1;
						db.getConnection().rollback();
						return false;
					}
				}
				rsck.close();
				
				//HUY THEM NHAP KHO -- HUY KDCL
				 
				
			 }else if(loaichungtu.equals("THNL")){
				 	String msg1= Revert_TieuhaoNL(sochungtu,db);
					if(msg1.length()>0){
						this.msg = "Lỗi hủy chuyển kho:" + msg1;
						db.getConnection().rollback();
						return false;
					}
					
			 } 
			
			//HỦY NHAP KHO MOI CỦA YÊU CẦU	
			 else if(loaichungtu.equals("NKM")){								
					String msg1=Revert_Nhanhang(db, sochungtu,loaichungtu);
					System.out.println(" huy nhập kho: "+msg1);
					if(msg1.length() > 0 ){
						this.msg="Lỗi hủy nhập kho:" +msg1;
						db.getConnection().rollback();
						return false;	
					}
			 
			 }else if(loaichungtu.equals("KDCLM")){								
					String msg1=Revert_yckD(db, sochungtu,"0");
					System.out.println(" huy nhập kho: "+msg1);
					if(msg1.length() > 0 ){
						this.msg="Lỗi hủy nhập kho:" +msg1;
						db.getConnection().rollback();
						return false;	
					}
			 
			 }
			
			 else {
					
					if(loaichungtu.equals("LSX")){
	
						if(!this.revertLenhSanXuat( sochungtu)){
							return false;
						}
					}
				}

			return true;
		}catch(Exception er){
			this.msg=er.getMessage();
			er.printStackTrace();
			return false;
		}
	}

	
	//-----14/10/2016 
	private boolean revertLenhSanXuat(String lsxid) {
		try{
			db.getConnection().setAutoCommit(false);

			String query="SELECT PK_SEQ  FROM ERP_LENHSANXUAT_GIAY WHERE PK_SEQ IN  ("+lsxid+")";
			ResultSet rs=db.get(query);
			while(rs.next()){
				lsxid=rs.getString("PK_SEQ");

				// HUY NHAP KHO
				query="select PK_SEQ from erp_nhapkho where TRANGTHAI='1' AND  SOLENHSANXUAT="+lsxid;
				ResultSet rsnk=db.get(query);
				while(rsnk.next()){
					String msg1 = Revert_NhapKho(rsnk.getString("PK_SEQ"),db);
					if(msg1.length()>0){
						System.out.println("Loi nhap kho "+msg1);
						this.msg = "loi  nhap kho : " + msg1;
						db.getConnection().rollback();
						return false;
					}
				}rsnk.close();

				//HUY CHUYEN KHO	
				query="select PK_SEQ,TRANGTHAI from ERP_CHUYENKHO where TRANGTHAI <>4 AND LENHSANXUAT_FK= "+lsxid;
				ResultSet rsck=db.get(query);
				while(rsck.next()){
					String ckid=rsck.getString("PK_SEQ");
					String msg1= Huychungtu_Chuyenkho(db,ckid,rsck.getString("trangthai"));
					if(msg1.length()>0){
						System.out.println("loi  chuye kho : "+msg1);
						this.msg = "loi  chuye kho : " + msg1;
						db.getConnection().rollback();
						return false;
					}

				}
				rsck.close();
				
				// hủy tiêu hao nguyên liệu
				
				query="select LENHSANXUAT_FK,pk_seq from ERP_TIEUHAONGUYENLIEU   where TRANGTHAI =1 AND LENHSANXUAT_FK= "+lsxid;
				  rsck=db.get(query);
				while(rsck.next()){
					String ckid=rsck.getString("PK_SEQ");
					String msg1= Revert_TieuhaoNL(ckid,db);
					if(msg1.length()>0){
						System.out.println("loi  chuye kho : "+msg1);
						this.msg = "loi  chuye kho : " + msg1;
						db.getConnection().rollback();
						return false;
					}

				}
				rsck.close();

				//CAP NHAT LAI TRANG THAI DA HUY 7
				query = " update ERP_LENHSANXUAT_GIAY set trangthai = '7', duyet='0' where pk_seq = '" + lsxid + "'";
 
				if(!db.update(query))
				{
					db.getConnection().rollback();
					this.msg = "Khong the cap nhat ERP_LENHSANXUAT_GIAY: " + query;
					
					System.out.println("KHONOG THANH CONG DO BA");
					return false;
					
				}
				query=" delete  ERP_LENHSANXUAT_BOM_GIAY_CHITIET    where LENHSANXUAT_FK= "+lsxid;
				if(!db.update(query))
				{
					db.getConnection().rollback();
					this.msg = "Khong the cap nhat ERP_LENHSANXUAT_BOM_GIAY_CHITIET: " + query;
					System.out.println("KHONOG THANH CONG DO BA");
					return false;
				}

				query=" update  ERP_LENHSANXUAT_BOM_GIAY set SOLUONG ='0' where LENHSANXUAT_FK= "+lsxid;
				if(!db.update(query))
				{
					db.getConnection().rollback();
					this.msg = "Khong the cap nhat ERP_LENHSANXUAT_BOM_GIAY: " + query;
					System.out.println("KHONOG THANH CONG DO BA");
					return false;
				}
			}
			rs.close();
			//System.out.println("AC ");
//			db.getConnection().commit();
//			db.getConnection().setAutoCommit(true);
		}catch(Exception err){
			db.update("rollback");
			this.msg=err.getMessage();
			err.printStackTrace();
			return false;
		}

		return true;
	}
	

	private static String Revert_NhapKho(String sochungtu,dbutils db  )
	{
		String msg = "";
		
		try
		{
			String query="SELECT pk_seq,nhapkho_fk FROM ERP_YEUCAUKIEMDINH WHERE nhapkho_fk = "+sochungtu+" and trangthai in (1,0)";
			ResultSet rskd=db.get(query);
			while(rskd.next()){
				String yckd=rskd.getString("PK_SEQ");
				String msg1= Revert_YeucauKiemDinh(yckd,db,"Nhapkho");
				if(msg1.length()> 0){
					db.getConnection().rollback();
					return msg1;
				}
			}
			rskd.close();
			
			query="update  ERP_YEUCAUKIEMDINH set trangthai='2' WHERE nhapkho_fk = "+sochungtu+"";

			if(!db.update(query)){
				db.getConnection().rollback();
				return "Khonog the cap nhat : "+query;
			}
			
			
			
			Utility_Kho util_kho=new Utility_Kho();
			query =     " select  a.NGAYNHAPKHO AS  NGAYCHOT  ,isnull(a.KHONGKIEMDINH,'0') as KHONGKIEMDINH  " +
					", B.DVDL_FK,B.SOLUONGTRUOCQUYDOI ,b.dongia ,  a.pk_seq as ctnhapkho,a.KHONHAP, a.SOLENHSANXUAT, a.NGAYNHAPKHO,  a.NOIDUNGNHAP, " +
			" b.SANPHAM_FK, b.SOLUONGNHAP, b.DONGIA, b.DONGIAVIET, b.TienTe_FK, isnull(b.SOLO, 0) as solo, ISNULL(b.KHUVUCKHO_FK,0) AS KVKHO, " +
			" isnull(b.NgaySanXuat, '') as ngaysanxuat, isnull(b.NgayHetHan, '') as ngayhethan, " +
			" d.QUANLYLO, d.LOAISANPHAM_FK,  isnull(d.batbuockiemdinh,'0')  as KiemDinhChatLuong , " +
			" isnull(d.KiemTraDinhLuong,0) as DinhLuong,isnull(d.KiemTraDinhTinh,0)as DinhTinh ,isnull(cd.dinhtinh,'0') as dinhtinhcd, isnull(cd.dinhluong,'0') as dinhluongcd, " +
			" ISNULL( ( select top(1) GIATON from ERP_TONKHOTHANG where SANPHAM_FK = d.pk_seq order by NAM desc, THANG desc ) , 0 ) as giaTON , " +
			" isnull(b.SOLUONGLAYMAU_TRUOCQD,0) as SOLUONGLAYMAU_TRUOCQD ,isnull(b.SOLUONGLAYMAU,0) as SOLUONGLAYMAU  " +
			" from ERP_NHAPKHO a left join erp_congdoansanxuat_Giay cd on cd.pk_seq=a.congdoan_fk " +
			" inner join ERP_NHAPKHO_SANPHAM b on a.PK_SEQ = b.SONHAPKHO_FK " +
			" inner join ERP_KHOTT c on a.khonhap = c.PK_SEQ " +
			" inner join ERP_SANPHAM d on b.SANPHAM_FK = d.PK_SEQ " +
			" where a.PK_SEQ = '" + sochungtu+ "' and a.trangthai =1 ";

			   System.out.println("1.Query chot nhap kho init: " + query);

		ResultSet rs = db.get(query);
		int i=0;
		 	
		while(rs.next()){

						i++;
						String loaisanpham = rs.getString("LOAISANPHAM_FK");
						String masanpham = rs.getString("SANPHAM_FK");
						String khonhap = rs.getString("KHONHAP");
						String ngaysanxuat = rs.getString("ngaysanxuat");
						String ngayhethan = rs.getString("ngayhethan");
						double dongia=rs.getDouble("dongia");
						double dongiaViet = rs.getDouble("giaTON");
						String tiente_fk = "100000"; 
						double soluong =  rs.getDouble("SOLUONGNHAP");
						String kiemdinhCL = rs.getString("KiemDinhChatLuong");
						String dinhluong=rs.getString("dinhluong");
						String dinhtinh=rs.getString("dinhtinh");
						if(rs.getString("dinhtinhcd").equals("1")){
							dinhtinh="1";
						}
						if(rs.getString("dinhluongcd").equals("1")){
							dinhluong="1";
						}
						 
					
						String spId = rs.getString("SANPHAM_FK");
						String solo = rs.getString("SOLO").trim();
						String khuvucId = rs.getString("KVKHO").trim();
						String soluongct = rs.getString("SOLUONGNHAP");
						String vitri = "";
						
						Kho_Lib kholib=new Kho_Lib();
						kholib.setNgaychungtu(rs.getString("NGAYNHAPKHO"));
						kholib.setLoaichungtu("ErpLenhsanxuatgiaySvl 1569 ERP_YEUCAUKIEMDINH : "+sochungtu);
						kholib.setKhottId(khonhap);
						kholib.setBinId(vitri);
						kholib.setSolo(solo);
						kholib.setSanphamId(spId);
						kholib.setMame("");
						kholib.setMathung("");
						kholib.setMaphieu("");
						kholib.setMaphieudinhtinh("");
						kholib.setPhieuEo("");
						kholib.setNgayhethan(ngayhethan);
						kholib.setNgaysanxuat(ngaysanxuat);
						kholib.setNgaynhapkho(rs.getString("NGAYCHOT"));
						kholib.setBooked(0);
						kholib.setSoluong((-1)* rs.getFloat("SOLUONGNHAP"));
						kholib.setAvailable((-1)*rs.getFloat("SOLUONGNHAP"));
						kholib.setMARQ("");
						kholib.setDoituongId("");
						kholib.setLoaidoituong("");
						kholib.setBinId("0");
						kholib.setHamluong("100");
						kholib.setHamam("0");
						
						String msg1= util_kho.Update_Kho_Sp_Tra(db,kholib);
					    if( msg1.length() >0)
						{
						 
							db.getConnection().rollback();
							return msg1;
						}
									
				}
		 
		
						query="UPDATE ERP_NHAPKHO set TRANGTHAI='2' WHERE PK_SEQ="+sochungtu;
						
						if(!db.update(query)){
							db.getConnection().rollback();
							return "Khonog the cap nhat : "+query;
						}
						 
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			msg = "Lỗi khi chốt: " + e.getMessage();
			db.update("rollback");
			//db.shutDown();
		}
		
		return msg;
	}
	

	// revert cua nhap kho moi
	private   String Revert_Nhanhang(dbutils db, String nhId, String loaict_goc) {
		// TODO Auto-generated method stub
		try{
			String  query="SELECT pk_seq FROM ERP_YEUCAUKIEMDINH where trangthai in (1,2) and nhanhang_fk="+nhId;

			ResultSet rscheck=db.get(query);
			while(rscheck.next()){
				// hủy luôn yêu cầu kiểm định
				String msg1= Revert_yckD(db,rscheck.getString("pk_seq"), "2");
				
				if(msg1.length() > 0 ){
					db.getConnection().rollback();
					return msg1;
					
				}
			}
			rscheck.close();
			
			
			 query="SELECT * FROM ERP_CHUYENKHO where trangthai  <>4  and nhanhang_fk ="+nhId;

				  rscheck=db.get(query);
				while  (rscheck.next()){
					 String msg1=Huychungtu_Chuyenkho(db, rscheck.getString("pk_seq"), rscheck.getString("trangthai"));
					 if(msg1.length() >0){
						 db.getConnection().rollback();
						 return msg1;
					 }
				}
				rscheck.close();
				
				
			
			Utility_Kho util_kho=new Utility_Kho();
			
			 
			
			  query=	" select a.PK_SEQ ,isnull(bin.pk_seq,0)  AS BIN_FK ,a.KHOCHOXULY_FK as kho_fk, b.SANPHAM_FK, N'Nhận hàng ' as loaichungtu, cast(a.PK_SEQ as varchar(10)) as machungtu, a.NGAYNHAN as ngaychungtu, a.NGAYCHOT,   " + 
					  " bin.ma as vitri, b.solo, b.ngayhethan, B.NGAYNHAPKHO as ngaynhapkho, b.mame, b.mathung, '' as maphieu, '' as phieudt, '' as phieueo,   " + 
					  " ISNULL(( select top 1 e.MA from ERP_NHANHANG_SANPHAM nhsp left join MARQUETTE e on nhsp.IDMARQUETTE = e.PK_SEQ where nhsp.NHANHANG_FK=a.pk_seq and nhsp.SANPHAM_FK=b.sanpham_fk  " + 
					  "   ),'') as MARQ, 0 as hamam, 100 as hamluong,	b.soluong as NHAP, 0 as XUAT    " + 
					  " from ERP_NHANHANG a inner join  ERP_NHANHANG_SP_CHITIET b on a.pk_seq = b.NHANHANG_FK   " + 
					  " left join ERP_BIN bin on b.bin_fk = bin.pk_seq   " + 
					  " inner join ERP_SANPHAM c on b.SANPHAM_FK = c.PK_SEQ    " + 
					  " where a.trangthai = '1' and (  isnull(b.ISKIEMDINH,'0')  =0)  and A.PK_SEQ=  " +this.sochungtu  + 
					  "   " + 
					  " union all   " + 
					  " select a.PK_SEQ ,isnull(bin.pk_seq,0) AS BIN_FK , a.KHONHAN_FK as kho_fk, b.SANPHAM_FK   " + 
					  " , N'Nhận hàng ' as loaichungtu, cast(a.PK_SEQ as varchar(10)) as machungtu, a.NGAYNHAN as ngaychungtu, a.NGAYCHOT,   " + 
					  " bin.ma as vitri, b.solo, b.ngayhethan, B.NGAYNHAPKHO as ngaynhapkho, b.mame, b.mathung,   " + 
					  " '' as maphieu, '' as phieudt, '' as phieueo,   ISNULL(( select top 1 e.MA from ERP_NHANHANG_SANPHAM nhsp left join MARQUETTE e on nhsp.IDMARQUETTE = e.PK_SEQ where nhsp.NHANHANG_FK=a.pk_seq and nhsp.SANPHAM_FK=b.sanpham_fk  " + 
					  "   ),'') as MARQ , 0 as hamam, 100 as hamluong,  " + 
					  " 	b.soluong as NHAP, 0 as XUAT   " + 
					  " from ERP_NHANHANG a inner join  ERP_NHANHANG_SP_CHITIET b on a.pk_seq = b.NHANHANG_FK   " + 
					  " left join ERP_BIN bin on b.bin_fk = bin.pk_seq   " + 
					  " inner join ERP_SANPHAM c on b.SANPHAM_FK = c.PK_SEQ    " + 
					  " where a.trangthai = '1'  and (isnull(b.ISKIEMDINH,'0') = 1)  and A.PK_SEQ= "+ this.sochungtu;
			  
			
			System.out.println("du lieu  : "+query);
			ResultSet rs=db.get(query);
			while(rs.next()){
				
				
				Kho_Lib kholib=new Kho_Lib();
				String ngaychungtu=rs.getString("ngaychungtu");
				kholib.setLoaichungtu("erpnhanhangtrongnuoc_giaysvl 791 :revert ERP_NHANHANG"+ this.sochungtu);
				
				kholib.setNgaychungtu(ngaychungtu);
				String khochuyen= rs.getString("kho_fk");
				kholib.setKhottId(khochuyen);
				
				kholib.setBinId("0");
				String solo= rs.getString("solo");
				
				kholib.setSolo(solo);
				String spid= rs.getString("SANPHAM_FK");
				kholib.setSanphamId(spid);
				
				
				kholib.setMame(rs.getString("MAME"));
				kholib.setMathung(rs.getString("mathung"));
				kholib.setMaphieu(rs.getString("maPHIEU"));
				
				kholib.setMaphieudinhtinh(rs.getString("phieudt"));
				kholib.setPhieuEo(rs.getString("phieueo"));
				
				kholib.setNgayhethan(rs.getString("ngayhethan"));
				kholib.setNgaysanxuat("");
				
				kholib.setNgaynhapkho(rs.getString("ngaynhapkho"));
				 
				kholib.setMARQ(rs.getString("MARQ"));
				kholib.setDoituongId("");
				kholib.setLoaidoituong("");
				kholib.setBinId(rs.getString("BIN_FK"));
				
				kholib.setHamluong(rs.getString("hamluong"));
				kholib.setHamam(rs.getString("hamam"));
				 
		    	kholib.setBooked(0);
				kholib.setSoluong((-1)*rs.getFloat("NHAP"));
				kholib.setAvailable((-1)*rs.getFloat("NHAP"));
			     
				
				
				String msg1= util_kho.Update_Kho_Sp_Tra(db,kholib);
			    if( msg1.length() >0)
				{
					// this.msg = msg1;
					db.getConnection().rollback();
					return msg1;
					
				}

			}
			rs.close();
			
			query=" UPDATE ERP_YEUCAUKIEMDINH   SET TRANGTHAI=3  where nhanhang_fk="+  this.sochungtu;
			
			if(!db.update(query)){
				db.getConnection().rollback();
				return "không thể cập nhật "+query;
			}
			query=" UPDATE ERP_nhanhang SET TRANGTHAI= 3  where pk_seq="+ this.sochungtu;
			
			if(!db.update(query)){
				db.getConnection().rollback();
				return "không thể cập nhật "+query;
			}
	 
			
			 
			/*String loaichungtu="Nhận hàng";
			
			String  msg1= Revert_KeToan_loaichungtu(db,loaichungtu,nhId);
			 if(msg1.length()>0){
				 db.getConnection().rollback();
				 return msg1;
			 }*/
			 
			
			query=	  " update erp_lenhsanxuat_giay set  HOANTATNHANHANG ='0' , trangthai=  ( case when ( select COUNT(*)  from ERP_TIEUHAONGUYENLIEU where  LENHSANXUAT_FK in  "+
					  " ( select lenhsanxuat_fk from ERP_NHANHANG where PK_SEQ = " + nhId +  ") "+
					  " and TRANGTHAI<>2  ) >=1 then 5  "+
					  " when ( select COUNT(*)  from ERP_NHANHANG where  LENHSANXUAT_FK in "+ 
					  "	 (select lenhsanxuat_fk from ERP_NHANHANG where PK_SEQ = " + nhId +  " )   "+
					  " and TRANGTHAI In (1,0) ) >=1 then 3  "+
					  " else   2  end )  "+
					  " where PK_SEQ= ( select lenhsanxuat_fk from ERP_NHANHANG where PK_SEQ = " + nhId +  ")  ";
				
				if(!db.update(query)){
					db.getConnection().rollback();
					return "Không thể cập nhật dòng lệnh: "+query ; 
				}
					
			return "";
		}catch(Exception err){
			err.printStackTrace();
			return err.getMessage();
		}
	
 	
	}

	private   String Revert_yckD (dbutils db, String nhId, String loaict_goc) {
		// TODO Auto-generated method stub
		try{
			
			String  query="SELECT * FROM ERP_CHUYENKHO where trangthai  <>4  and YCKD_FK ="+nhId;

			ResultSet rscheck=db.get(query);
			while  (rscheck.next()){
				 String msg1=Huychungtu_Chuyenkho(db, rscheck.getString("pk_seq"), rscheck.getString("trangthai"));
				 if(msg1.length() >0){
					 return msg1;
				 }
			}
			rscheck.close();
		 
			
			Utility_Kho util_kho=new Utility_Kho();
			
	  
			  query=" select  isnull(bin.pk_seq,0) AS BIN_FK ,a.KHONHAN_FK as kho_fk, a.SANPHAM_FK,  N'Yêu cầu kiểm định' as loaichungtu, isnull(a.MAPHIEU, '') as machungtu, a.ngaykiem as ngaychungtu, a.ngaykiem as ngaychot, " + 
				  "			bin.ma as vitri, a.solo, a.ngayhethan, b.ngaynhapkho, a.mame, b.mathung, b.maphieu, '' as phieudt, '' as phieueo, isnull(a.MAMARQUETTE, '') as MARQ, isnull(b.hamam, 0) as hamam, isnull(b.hamluong, 100) as hamluong,  " + 
				  "			case when b.soluongDAT > 0 then b.soluongDAT else 0 end as NHAP,  " + 
				  "			case when b.soluongDAT < 0 then -1 * b.soluongDAT else 0 end as XUAT   " + 
				  "	from ERP_YEUCAUKIEMDINH a inner join ERP_YEUCAUKIEMDINH_THUNG_CHITIET b on a.pk_seq = b.YEUCAUKIEMDINH_FK " + 
				  "			left join ERP_BIN bin on b.bin_fk = bin.pk_seq " + 
				  "	where a.trangthai = '2' AND  A.PK_SEQ= "+nhId+ 
				  " " + 
				  "	union all " + 
				  "	select  isnull(bin.pk_seq,0) AS BIN_FK , a.KHONHAN_FK as kho_fk, a.SANPHAM_FK,  N'Yêu cầu kiểm định' as loaichungtu, isnull(a.MAPHIEU, '') as machungtu, a.ngaykiem as ngaychungtu, a.ngaykiem as ngaychot, " + 
				  "			bin.ma as vitri, a.solo, a.ngayhethan, a.ngaykiem, a.mame, b.mathung, isnull(a.MAPHIEU, '') as maphieu, '' as phieudt, '' as phieueo, isnull(a.MAMARQUETTE, '') as MERQ, isnull(b.hamam, 0) as hamam, isnull(b.hamluong, 100) as hamluong, b.soluongMAU as NHAP, 0 as XUAT   " + 
				  "	from ERP_YEUCAUKIEMDINH a inner join ERP_YEUCAUKIEMDINH_THUNG b on a.pk_seq = b.YEUCAUKIEMDINH_FK " + 
				  "			left join ERP_BIN bin on b.bin_fk = bin.pk_seq " + 
				  "	where a.trangthai = '2'  AND  A.PK_SEQ= "+nhId+ 
				  "	 " + 
				  "	union all " + 
				  "	select  isnull(bin.pk_seq,0) AS BIN_FK , a.KHONHAN_FK as kho_fk, a.SANPHAM_FK,  N'Yêu cầu kiểm định' as loaichungtu, isnull(a.MAPHIEU, '') as machungtu, a.ngaykiem as ngaychungtu, a.ngaykiem as ngaychot, " + 
				  "			bin.ma as vitri, a.solo, a.ngayhethan, a.ngaykiem, a.mame, b.mathung,'' as maphieu, '' as phieudt, '' as phieueo, isnull(a.MAMARQUETTE, '') as MERQ, isnull(b.hamam, 0) as hamam, isnull(b.hamluong, 100) as hamluong, 0 as NHAP, b.soluongMAU as XUAT   " + 
				  "	from ERP_YEUCAUKIEMDINH a inner join ERP_YEUCAUKIEMDINH_THUNG b on a.pk_seq = b.YEUCAUKIEMDINH_FK " + 
				  "			left join ERP_BIN bin on b.bin_fk = bin.pk_seq " + 
				  "	where a.trangthai = '2' AND  A.PK_SEQ= "+nhId ;
			 
			 System.out.println("du lieu  : "+query);
			ResultSet rs=db.get(query);
			while(rs.next()){
				
				
				Kho_Lib kholib=new Kho_Lib();
				String ngaychungtu=rs.getString("ngaychungtu");
				kholib.setLoaichungtu("erpnhanhangtrongnuoc_giaysvl 1160 :revert ERP_YEUCAUKIEMDINH"+nhId);
				
				kholib.setNgaychungtu(ngaychungtu);
				String khochuyen= rs.getString("kho_fk");
				kholib.setKhottId(khochuyen);
				
				kholib.setBinId("0");
				String solo= rs.getString("solo");
				
				kholib.setSolo(solo);
				String spid= rs.getString("SANPHAM_FK");
				kholib.setSanphamId(spid);
				
				
				kholib.setMame(rs.getString("MAME"));
				kholib.setMathung(rs.getString("mathung"));
				kholib.setMaphieu(rs.getString("maPHIEU"));
				
				kholib.setMaphieudinhtinh(rs.getString("phieudt"));
				kholib.setPhieuEo(rs.getString("phieueo"));
				
				kholib.setNgayhethan(rs.getString("ngayhethan"));
				kholib.setNgaysanxuat("");
				
				kholib.setNgaynhapkho(rs.getString("ngaynhapkho"));
				 
				kholib.setMARQ(rs.getString("MARQ"));
				kholib.setDoituongId("");
				kholib.setLoaidoituong("");
				kholib.setBinId(rs.getString("BIN_FK"));
				
				kholib.setHamluong(rs.getString("hamluong"));
				kholib.setHamam(rs.getString("hamam"));
				 
			     kholib.setBooked(0);
			    	
		    	if(rs.getFloat("NHAP") >0){
		    		kholib.setSoluong((-1)*rs.getFloat("NHAP"));
					kholib.setAvailable((-1)*rs.getFloat("NHAP"));
		    	}else{
		    		kholib.setSoluong( rs.getFloat("xuat"));
					kholib.setAvailable( rs.getFloat("xuat"));
					kholib.setHamluong("100");
					kholib.setHamam("0");
					 
		    	}
					
		    	String msg1= util_kho.Update_Kho_Sp_Tra(db,kholib);
			    if( msg1.length() >0)
				{
					// this.msg = msg1;
					db.getConnection().rollback();
					return msg1;
					
				}
				
			

			}
			rs.close();
			
			
			//neu loai chung tu goc huy la NHAN HANG (2) thi doi trang thai kiem dinh thanh "huy" 3
			//neu loai chung tu goc huy la PHIEU KIEM DINH (3) thi doi trang thai kiem dinh thanh "CHO KIEM DINH" 0
			// ĐÚNG RỒI ĐÓ 
			if(loaict_goc.equals("2"))
			{
				query=" UPDATE ERP_YEUCAUKIEMDINH SET TRANGTHAI=3 where pk_seq ="+nhId;
			}
			else
			{
				query=" UPDATE ERP_YEUCAUKIEMDINH SET TRANGTHAI=0 where pk_seq ="+nhId;
			}
			
			
			if(!db.update(query)){
				db.getConnection().rollback();
				return "không thể cập nhật "+query;
			}
			query="delete ERP_KIEMDINHCHATLUONG_LANDUYET WHERE YEUCAUKIEMDINH_FK="+nhId;
			
			if(!db.update(query)){
				db.getConnection().rollback();
				return "không thể cập nhật "+query;
			}
			 
			query="delete ERP_YEUCAUKIEMDINH_THUNG_CHITIET WHERE YEUCAUKIEMDINH_FK="+nhId;
			
			if(!db.update(query)){
				db.getConnection().rollback();
				return "không thể cập nhật "+query;
			}
			
		 
			return "";
		}catch(Exception err){
			err.printStackTrace();
			return err.getMessage();
		}
	 
	}

	
	
	private static String Revert_YeucauKiemDinh(String sochungtu,dbutils db,String Loaichungtugoc )
	
	{
		String msg = "";
		
		try
		{
			String query="select PK_SEQ,TRANGTHAI from ERP_CHUYENKHO where TRANGTHAI <>4 AND YCKD_FK="+sochungtu;
			ResultSet rsck=db.get(query);
			while(rsck.next()){
				String ckid=rsck.getString("PK_SEQ");
				String msg1 =Huychungtu_Chuyenkho(db,ckid,rsck.getString("trangthai"));
				if(msg1.length() >0){
					db.getConnection().rollback();
					return msg1;
				}
				
			}
			rsck.close();
		
			Utility_Kho util_kho=new Utility_Kho();
			
			 
			
			  query=  	" SELECT  A.TRANGTHAI , ISNULL(A.MAPHIEU,'') AS MAPHIEU  ,A.NGAYSANXUAT , A.NGAYHETHAN ,A.SOLO ,A.NGAYKIEM ,A.NGAYNHAPKHO ,NK.KHONHAP  " +
						" ,A.SANPHAM_FK,A.SOLUONGDAT,a.soluongmau ,A.SOLUONG , A.SOLUONG- A.SOLUONGDAT- a.soluongmau AS  SOLUONGKHONGDAT,A.SOLO  ,NKSP.KHUVUCKHO_FK "+
						" FROM ERP_YEUCAUKIEMDINH A INNER JOIN ERP_NHAPKHO NK ON A.NHAPKHO_FK=NK.PK_SEQ "+  
						" INNER JOIN ERP_NHAPKHO_SANPHAM NKSP ON NKSP.SONHAPKHO_FK=NK.PK_SEQ AND NKSP.SANPHAM_FK=A.SANPHAM_FK  "+
				        " WHERE  A.TRANGTHAI IN (0,1)    AND A.PK_SEQ="+sochungtu;
			//System.out.println(query);
			ResultSet rsdetail=db.get(query);
			
			if(rsdetail.next()){
				
				String trangthaipkd =rsdetail.getString("TRANGTHAI");
				String maphieu=rsdetail.getString("MAPHIEU");
				
				String spid=rsdetail.getString("sanpham_fk");
				
				String ngaychungtu= rsdetail.getString("NGAYKIEM");
				
				String khochuyen=rsdetail.getString("KHONHAP");
				// giảm kho không có mã phiếu,tăng kho có mã phiếu
				String solo=rsdetail.getString("solo");
				String NGAYHETHAN=rsdetail.getString("NGAYHETHAN");
				String NGAYSANXUAT=rsdetail.getString("NGAYSANXUAT");
				String NGAYNHAPKHO=rsdetail.getString("NGAYNHAPKHO");
				Kho_Lib kholib=new Kho_Lib();
				kholib.setLoaichungtu("ErpLenhsanxuatgiaySvl 1569 ERP_YEUCAUKIEMDINH : "+sochungtu);
				kholib.setNgaychungtu(ngaychungtu);
				
				kholib.setKhottId(khochuyen);
				
				kholib.setBinId("0");
				
				kholib.setSolo(solo);
				kholib.setSanphamId(spid);
				
				kholib.setMame("");
				kholib.setMathung("");
				kholib.setMaphieu("");
				
				kholib.setMaphieudinhtinh("");
				kholib.setPhieuEo("");
				
				kholib.setNgayhethan(NGAYHETHAN);
				kholib.setNgaysanxuat(NGAYSANXUAT);
				
				kholib.setNgaynhapkho(NGAYNHAPKHO);
				
				
				kholib.setAvailable(0);
				kholib.setMARQ("");
				kholib.setDoituongId("");
				kholib.setLoaidoituong("");
				kholib.setBinId("0");
				kholib.setHamluong("100");
				kholib.setHamam("0");
				 
				 if(trangthaipkd.equals("1")){
					 
					 //chỉ revert kiểm định đã chốt về trạng thái hủy
					 if(Loaichungtugoc.equals("YCKD")){
				    	kholib.setBooked(rsdetail.getFloat("SOLUONG"));
						kholib.setSoluong(rsdetail.getFloat("SOLUONG"));
						kholib.setAvailable(0);
					 }else{
							kholib.setBooked(0);
							kholib.setSoluong(rsdetail.getFloat("SOLUONG"));
							kholib.setAvailable(rsdetail.getFloat("SOLUONG"));
					 }
				 }else {
					 //có trong trường hợp revert nhận hàng, kiểm định chưa duyệt
					 	kholib.setBooked(rsdetail.getFloat("SOLUONG") * (-1));
						kholib.setSoluong(0);
						kholib.setAvailable(rsdetail.getFloat("SOLUONG"));
				 }
				
				
				String msg1= util_kho.Update_Kho_Sp_Tra(db,kholib);
			    if( msg1.length() >0)
				{
					// this.msg = msg1;
					db.getConnection().rollback();
					return msg1;
					
				}
			    if(trangthaipkd.equals("1")  ){
			    	// thêm mã phiếu vào : tăng kho lên lại,chỉ có ngày nhập kho là thay đổi
				    kholib.setMaphieu(maphieu);
				    // ngày nhập kho là ngày kiểm định
				    kholib.setNgaynhapkho(ngaychungtu);
				    kholib.setBooked(0);
					kholib.setSoluong((-1)* rsdetail.getFloat("SOLUONG"));
					kholib.setAvailable((-1)*rsdetail.getFloat("SOLUONG"));
					
					msg1= util_kho.Update_Kho_Sp_Tra(db,kholib);
				    if( msg1.length() >0)
					{
				    	db.getConnection().rollback();
						return msg1;
					}
			    }
			}
			
			
			//DUA VE TRANG THAI CHO KIEM DINH
			query=  " update ERP_YEUCAUKIEMDINH set TRANGTHAI='0' where pk_seq="+sochungtu;
			if(!db.update(query)){
				db.getConnection().rollback();
				return "Không thể cập nhật "+query;
			}
			
			 
			 
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			
			msg = "Lỗi khi chốt: " + e.getMessage();
			db.update("rollback");
			//db.shutDown();
		}
		
		return msg;
	}
	
	private String Revert_TieuhaoNL(String sochungtu,dbutils db){
		try
		{
			String query=" select isnull(B.bin_fk,0) as bin_fk  ,a.NGAYTIEUHAO ,b.KHOTT_FK as kho_fk, b.SANPHAM_FK, N'Tiêu hao lệnh sản xuất ' as loaichungtu, cast(a.PK_SEQ as varchar(10)) as machungtu, a.NGAYTIEUHAO as ngaychungtu, a.NGAYTIEUHAO, "+
						 " isnull(bin.ma,'') as vitri, b.solo, b.ngayhethan, b.ngaynhapkho, ISNULL(b.mathung,'') as mathung, ISNULL(b.mame,'') as mame,    " +
						 " isnull(b.maphieu,'') as maphieu, isnull(b.maphieudinhtinh,'')  as phieudt, isnull(b.phieueo,'') as phieueo , " +
						 " isnull(b.MArq,'')  as MARQ, b.hamam as hamam, b.hamluong as hamluong, 0 as NHAP ,	b.SOLUONG as XUAT "+
						 " from ERP_TIEUHAONGUYENLIEU a   "+
						 " inner join  ERP_LENHSANXUAT_TIEUHAO_CHITIET b on a.pk_seq = b.TIEUHAONGUYENLIEU_FK "+
						 " inner join ERP_SANPHAM c on b.SANPHAM_FK = c.PK_SEQ "+ 
						 " left join ERP_BIN bin on b.bin_fk = bin.pk_seq  " +
						 " where  a.trangthai = '1' and a.pk_seq="+sochungtu;
			ResultSet rs=db.get(query);
			while(rs.next()){
				
				Kho_Lib kholib=new Kho_Lib();
				 
				kholib.setLoaichungtu("erpHuylenhsanxuat.java 1091 :  ERP_TIEUHAONGUYENLIEU"+ sochungtu);
				
				kholib.setNgaychungtu(rs.getString("NGAYTIEUHAO"));
				 
				kholib.setKhottId(rs.getString("kho_fk"));
				
				kholib.setBinId( rs.getString("bin_fk") );
			 
				
				kholib.setSolo( rs.getString("solo"));
				String spid=  rs.getString("SANPHAM_FK");
				kholib.setSanphamId(spid);
				
				
				kholib.setMame( rs.getString("mame"));
				kholib.setMathung(rs.getString("mathung"));
				kholib.setMaphieu(rs.getString("maphieu"));
				
				kholib.setMaphieudinhtinh(rs.getString("phieudt"));
				kholib.setPhieuEo(rs.getString("phieueo"));
				
				kholib.setNgayhethan(rs.getString("ngayhethan") );
				kholib.setNgaysanxuat("");
				
				
				kholib.setNgaynhapkho(rs.getString("ngaynhapkho"));
				 
				kholib.setMARQ(rs.getString("MARQ"));
				kholib.setDoituongId("");
				kholib.setLoaidoituong("");
				 
				
				kholib.setHamluong(rs.getString("hamluong"));
				kholib.setHamam(rs.getString("hamam"));
				 
				double soluongct= rs.getDouble("XUAT");
				
		    	kholib.setBooked(0);
				kholib.setSoluong(soluongct);
				kholib.setAvailable(soluongct);
				String msg1= util_kho.Update_Kho_Sp_Tra(db,kholib);
			    if( msg1.length() >0)
				{
					// this.msg = msg1;
			    	this.msg = msg1;
					db.getConnection().rollback();
					return msg1;
					
				}
			}
			rs.close();
			
			
			query=" update  ERP_TIEUHAONGUYENLIEU set TRANGTHAI=2 where PK_SEQ = "+ sochungtu;
			if(!db.update(query)){
				db.getConnection().rollback();
				return "Không thể cập nhật dòng lệnh: "+query ; 
			}
			Utility util = new Utility();
			String msg = util.HuyUpdate_TaiKhoan(db, sochungtu, "Xuất tiêu hao lệnh sản xuất");
			if (msg.length() >0){
				db.getConnection().rollback();
				return "Không thể xóa phát sinh kế toán: "+msg ; 
			}
			query=" update erp_lenhsanxuat_giay set trangthai=  ( case when ( select COUNT(*)  from ERP_TIEUHAONGUYENLIEU where  LENHSANXUAT_FK in  "+
				  " (select LENHSANXUAT_FK from ERP_TIEUHAONGUYENLIEU where PK_SEQ= "+sochungtu+") "+
				  " and TRANGTHAI<>2 ) >=1 then 5  "+
				  " when ( select COUNT(*)  from ERP_NHANHANG where  LENHSANXUAT_FK in "+ 
				  "	 (select LENHSANXUAT_FK from ERP_TIEUHAONGUYENLIEU where PK_SEQ= "+sochungtu+") "+
				  " and TRANGTHAI In (1,0) ) >=1 then 3  "+
				  " else   2  end )  "+
				  " where PK_SEQ= (select LENHSANXUAT_FK from ERP_TIEUHAONGUYENLIEU where PK_SEQ= "+sochungtu+")";
			if(!db.update(query)){
				db.getConnection().rollback();
				return "Không thể cập nhật dòng lệnh: "+query ; 
			}
			
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
			msg = "Lỗi khi chốt: " + e.getMessage();
			db.update("rollback");
			//db.shutDown();
		}
		
		return msg;
		
	}
	
	private static String Huychungtu_Chuyenkho(dbutils db,String ckid, String trangthai) {
		// TODO Auto-generated method stub
		try{
			// trạng thái ,1,2,3 là đã xuất kho
			String msg1="";
			if(trangthai.equals("0")){
				  msg1=	XoaChuyenKho(ckid,db);
				System.out.println(msg1);
			}else{
			    msg1=	Revert_ChuyenKho(ckid,db);	
				System.out.println("xOA XONG:  "+msg1);
			}
			if(msg1.length()==0){
				msg1=Revert_SLYC_Lenhsanxuat(ckid,db);
			}
			
			return msg1;
		}catch(Exception er){
			
			er.printStackTrace();
			return er.getMessage();
		}
	}
	
	private static String Revert_SLYC_Lenhsanxuat(String ckid, dbutils db2) {
		// TODO Auto-generated method stub
		try{
			
			String query="SELECT LENHSANXUAT_FK FROM ERP_CHUYENKHO WHERE LENHSANXUAT_FK is not null and  PK_SEQ="+ckid;
			ResultSet rs=db2.get(query);
			String lsxId="";
			if(rs.next()){
				lsxId=rs.getString("LENHSANXUAT_FK");
			}
			if(lsxId.length() >0){
				
			  query=" DELETE    ERP_LENHSANXUAT_BOM_GIAY_CHITIET   WHERE CHUYENKHO_FK ="+ckid;
			  String msg1;
			  
				if( !db2.update(query) )
				{
					msg1 = "Lỗi khi xóa: " + query;
					db2.getConnection().rollback();
					return msg1;
				}
			
				query=	" UPDATE BOM SET BOM.SOLUONG=ISNULL(DATA.SOLUONGCHUAN,0)  FROM  " +
						" ERP_LENHSANXUAT_BOM_GIAY BOM  LEFT JOIN  ( "+
						" SELECT LENHSANXUAT_FK,VATTU_FK,SUM(SOLUONGCHUAN) AS SOLUONGCHUAN "+
						" FROM ERP_LENHSANXUAT_BOM_GIAY_CHITIET   where LENHSANXUAT_FK= "+lsxId+" and ISYCCK='1' "+
						" GROUP BY LENHSANXUAT_FK,VATTU_FK "+
						" ) DATA "+
						"   ON BOM.LENHSANXUAT_FK=DATA.LENHSANXUAT_FK AND BOM.VATTU_FK=DATA.VATTU_FK "+
						" where BOM.LENHSANXUAT_FK ="+lsxId;
				if(!db2.update(query)){
					msg1 = "Lỗi khi xóa: " + query;
					db2.getConnection().rollback();
					return msg1;
				}
			}

		}catch(Exception err){
			return "Lỗi : "+err.getMessage();
		}
		
		
		return "";
	}
	
	
	private static String XoaChuyenKho(String lsxId,dbutils db) 
	{
		String msg = "";
		 
		
		try
		{
			  
			String query = "update ERP_CHUYENKHO set trangthai = '4' where pk_seq = '" + lsxId + "' ";
			if( !db.update(query) )
			{
				msg = "Lỗi khi xóa: " + query;
				db.getConnection().rollback();
				 
				return msg;
			}
			
			//TRỪ KHO
			Utility util = new Utility();
			query = "  select b.loaidoituong, b.DOITUONG_FK as doituongId, b.NGAYCHUYEN, b.KhoXuat_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, " + 
					" 		a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, a.phieudt, a.phieueo, SUM( a.SoLuong ) as soluong  " + 
					"  from ERP_CHUYENKHO_SANPHAM_CHITIET a inner join ERP_CHUYENKHO b on a.chuyenkho_FK = b.PK_SEQ " + 
					"  where b.PK_SEQ = '" + lsxId + "' " + 
					"  group by b.loaidoituong, b.DOITUONG_FK, b.NGAYCHUYEN, b.KhoXuat_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, a.bin_fk, a.phieudt, a.phieueo ";
			
			System.out.println("::: CAP NHAT KHO: " + query);
			ResultSet rs = db.get(query);
			if( rs != null )
			{
				while( rs.next() )
				{
					String khoId = rs.getString("KhoXuat_FK");
					String spId = rs.getString("SanPham_fk");
					String solo = rs.getString("SoLo");
					String ngayhethan = rs.getString("NgayHetHan");
					String ngaynhapkho = rs.getString("ngaynhapkho");
					
					String loaidoituong = rs.getString("loaidoituong") == null ? "" : rs.getString("loaidoituong");
					String doituongId = rs.getString("doituongId") == null ? "" :  rs.getString("doituongId");
					
					String mame = rs.getString("mame");
					String mathung = rs.getString("mathung");
					String bin_fk = rs.getString("bin_fk");
					
					String maphieu = rs.getString("maphieu");
					String phieudt = rs.getString("phieudt");
					String phieueo = rs.getString("phieueo");
					
					String marq = rs.getString("marq");
					String hamluong = rs.getString("hamluong");
					String hamam = rs.getString("hamam");

					double soluong = rs.getDouble("soluong");
					
					msg = util.Update_KhoTT(rs.getString("NGAYCHUYEN"), "Cập nhật CK - Tăng kho ngược lại trước khi xóa ", db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
							mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, 0, -1 * soluong, soluong);
					if( msg.trim().length() > 0 )
					{
						msg = "Lỗi khi xóa: " + msg;
						db.getConnection().rollback();
						return msg;
					}
				}
				rs.close();
			}
			 
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			msg = "Lỗi khi chốt: " + e.getMessage();
			db.update("rollback");
			//db.shutDown();
		}
		
		return msg;
	}


	
	private static String Revert_ChuyenKho(String lsxId,dbutils db) 
	{
		String msg = "";
	
		
		try
		{ 
			
			
			//TRỪ KHO
			Utility util = new Utility();
			String query =  "  select b.loaidoituong, b.DOITUONG_FK as doituongId, b.NGAYCHUYEN, b.KhoXuat_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, " + 
							" 		a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, a.phieudt, a.phieueo, SUM( a.SoLuong ) as soluong  " + 
							"  from ERP_CHUYENKHO_SANPHAM_CHITIET a inner join ERP_CHUYENKHO b on a.chuyenkho_FK = b.PK_SEQ " + 
							"  where b.PK_SEQ = '" + lsxId + "'  and B.TRANGTHAI IN (1,2,3) " + 
							"  group by b.loaidoituong, b.DOITUONG_FK, b.NGAYCHUYEN, b.KhoXuat_FK, a.SanPham_fk, " +
							" a.SoLo, a.NgayHetHan, a.ngaynhapkho, a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, " +
							" a.bin_fk, a.phieudt, a.phieueo ";
			
			System.out.println("::: CAP NHAT KHO: " + query);
			ResultSet rs = db.get(query);
			 
				while( rs.next() )
				{
					String khoId = rs.getString("KhoXuat_FK");
					String spId = rs.getString("SanPham_fk");
					String solo = rs.getString("SoLo");
					String ngayhethan = rs.getString("NgayHetHan");
					String ngaynhapkho = rs.getString("ngaynhapkho");
					
					String loaidoituong = rs.getString("loaidoituong") == null ? "" : rs.getString("loaidoituong");
					String doituongId = rs.getString("doituongId") == null ? "" :  rs.getString("doituongId");
					
					String mame = rs.getString("mame");
					String mathung = rs.getString("mathung");
					String bin_fk = rs.getString("bin_fk");
					
					String maphieu = rs.getString("maphieu");
					String phieudt = rs.getString("phieudt");
					String phieueo = rs.getString("phieueo");
					
					String marq = rs.getString("marq");
					String hamluong = rs.getString("hamluong");
					String hamam = rs.getString("hamam");

					double soluong =   rs.getDouble("soluong");
					
					msg = util.Update_KhoTT(rs.getString("NGAYCHUYEN"), "Cập nhật CK - Tăng kho ngược lại trước khi xóa ", db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
							mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, soluong, 0, soluong);
					System.out.println("Lõi 1 : " +msg);
					
					if( msg.trim().length() > 0 )
					{
						msg = "Lỗi khi xóa: " + msg;
						db.getConnection().rollback();
						//db.shutDown();
						return msg;
					}
					 
				}
				rs.close();
					
	// giam  kho nhan
	
				query = "  select b.loaidoituongNhan, b.doituongnhan_fk as doituongNhanId, b.NGAYCHUYEN, b.KHONHAN_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, " + 
				" 		a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.binNhan_fk, 0) as bin_fk, a.phieudt, a.phieueo, SUM( a.SoLuong ) as soluong  " + 
				"  from ERP_CHUYENKHO_SANPHAM_CHITIET_NHANHANG a inner join ERP_CHUYENKHO b on a.chuyenkho_FK = b.PK_SEQ " + 
				"  where  b.trangthai=3  and  b.PK_SEQ = '" +lsxId + "' and a.soluong > 0 " + 
				"  group by b.loaidoituongNhan, b.doituongnhan_fk, b.NGAYCHUYEN, b.KHONHAN_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, binNhan_fk, a.phieudt, a.phieueo ";
		
		System.out.println("::: CAP NHAT KHO: " + query);
		  rs = db.get(query);
		 
			while( rs.next() )
			{
				String khoId = rs.getString("KHONHAN_FK");
				String spId = rs.getString("SanPham_fk");
				String solo = rs.getString("SoLo");
				String ngayhethan = rs.getString("NgayHetHan");
				String ngaynhapkho = rs.getString("ngaynhapkho");
				
				String loaidoituong = rs.getString("loaidoituongNhan") == null ? "" : rs.getString("loaidoituongNhan");
				String doituongId = rs.getString("doituongNhanId") == null ? "" : rs.getString("doituongNhanId");
				
				String mame = rs.getString("mame");
				String mathung = rs.getString("mathung");
				String bin_fk = rs.getString("bin_fk");
				
				String maphieu = rs.getString("maphieu");
				String phieudt = rs.getString("phieudt");
				String phieueo = rs.getString("phieueo");
				
				String marq = rs.getString("marq");
				String hamluong = rs.getString("hamluong");
				String hamam = rs.getString("hamam");
				
				double soluong =(-1)*  rs.getDouble("soluong");
				
				msg = util.Update_KhoTT(rs.getString("NGAYCHUYEN"), "Hủy chuyển kho "+lsxId, db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
						mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, soluong, 0, soluong);
				if( msg.trim().length() > 0 )
				{
					msg = "Lỗi khi xóa: " + msg;
					db.getConnection().rollback();
					//db.shutDown();
					return msg;
				}
			}
			rs.close();
			
			
		 	query = "update ERP_CHUYENKHO set trangthai = '4' where pk_seq = '" + lsxId + "' ";
			if( !db.update(query) )
			{
				msg = "Lỗi khi xóa: " + query;
				db.getConnection().rollback();
				//db.shutDown();
				return msg;
			}
					
			 
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			
			msg = "Lỗi khi chốt: " + e.getMessage();
			db.update("rollback");
			//db.shutDown();
		}
		
		return msg;
	}

	public String Update_Kho_Sp_Tra(Idbutils db,
			Kho_Lib kholib) {
		// TODO Auto-generated method stub
			NumberFormat formater_3sole = new DecimalFormat("#######.###");
			String ngayyeucau=kholib.getNgaychungtu();
			String ghichu=kholib.getLoaichungtu();
			String sochungtu=kholib.getSochungtu();
			
			String khott_fk =kholib.getKhottId();
			
			String spId= kholib.getSanphamId() ;
			String solo=kholib.getSolo(); 
			String ngayhethan= kholib.getNgayhethan();
			String ngaynhapkho= kholib.getNgaynhapkho();
			String ngaysanxuat=kholib.getNgaysanxuat();
			String MAME =kholib.getMame();
			String MATHUNG=kholib.getMathung();
			String vitri =kholib.getBinId();
			
			String MAPHIEU=kholib.getMaphieu();
			String phieudt=kholib.getMaphieudinhtinh();
			String phieueo =kholib.getPhieuEo();
			
			String MARQ =kholib.getMARQ();
			String HAMLUONG= kholib.getHamluong();
			String HAMAM= kholib.getHamam();
			
			String loaidoituong = kholib.getLoaidoituong();
			String doituongId =kholib.getDoituongId();
			
			double soluong=Double.parseDouble(formater_3sole.format(kholib.getSoluong()));
			double booked =Double.parseDouble(formater_3sole.format(kholib.getBooked()));  
			double available =Double.parseDouble(formater_3sole.format( kholib.getAvailable())); 
			float dongia=kholib.getDongialo();
			Utility util=new Utility();
		 
			String msg1= util.Update_KhoTT(ngayyeucau, ghichu, db, khott_fk, spId, solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, vitri, MAPHIEU, phieudt, phieueo, MARQ, HAMLUONG, HAMAM, loaidoituong, doituongId, soluong, booked, available, dongia, ngaysanxuat);
			return msg1;
	}
	
	
	
	public   String Revert_KeToan_loaichungtu(dbutils db,String loaichungtu,String sochungtu ) {

		try 
		{
			
		//GHI NHAN NGUOC LAI TAI KHOAN NO - CO
		String query =  " select PK_SEQ ,NGAYGHINHAN, SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NO, CO, TIENTEGOC_FK, TONGGIATRINT  " +
					    " from ERP_PHATSINHKETOAN " +
					    " where LOAICHUNGTU = N'" + loaichungtu.trim() + "' and SOCHUNGTU = '" + sochungtu + "'  ";
		 String chungtu="0";
			ResultSet rsPSKT = db.get(query);
			while(rsPSKT.next())
			{
				chungtu+=","+rsPSKT.getString("pk_seq");
				
				String taikhoan_fk = rsPSKT.getString("TAIKHOAN_FK");
				String tiente_fk = rsPSKT.getString("TIENTEGOC_FK");
				double NO = rsPSKT.getDouble("NO");
				double CO = rsPSKT.getDouble("CO");
				double TONGGIATRINT = rsPSKT.getDouble("TONGGIATRINT");
				String ngayghinhan=rsPSKT.getString("NGAYGHINHAN");
				
				String nam = ngayghinhan.substring(0, 4);
				String thang = ngayghinhan.substring(5, 7);
				//NEU LA CO THI BAY GIO GHI GIAM CO LAI
				if( NO > 0 )
				{
					query = " update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND - " + NO + ", GIATRINONGUYENTE = GIATRINONGUYENTE - " + TONGGIATRINT + "  " +
							" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
				}
				else
				{
					query = " update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND - " + CO + ", GIATRICONGUYENTE = GIATRICONGUYENTE - " + TONGGIATRINT + "  " +
							" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
				}
				
				//System.out.println("1.REVERT NO-CO: " + query);
				
				if(db.updateReturnInt(query)!=1)
				{
					return "KHÔNG THỂ REVERT KẾ TOÁN. YÊU CẦU LIÊN HỆ LẬP TRÌNH ";
					
				}
				
			}
			rsPSKT.close();
			
		 
			
			//HỦY KẾ TOÁN ĐÃ GHI NHẬN
			query = " DELETE ERP_PHATSINHKETOAN WHERE    pk_seq in ("+chungtu+") ";	
			if(!db.update(query))
			{
				return "Không thể hủy ERP_PHATSINHKETOAN " + query;
				 
			}			
			return "";
			
		} 
		catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
			
		}
	 
	
		 
	
	}


	
	//-------
	/*
	private boolean revertYeuCauChuyenKho(String SOCHUNGTU) {
		try{


			String query="update ERP_YEUCAUCHUYENKHO set trangthai = '4' where pk_seq = '" +  SOCHUNGTU + "'";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật phiếu yêu cầu ,vui lòng liên hệ admin để xử lý  : " + query;
				return false;
			}
			//cập nhật lệnh sản xuất về trạng thái chưa yêu cầu nguyên liệu
			// kiểm tra lệnh sản xuất này không có nhập kho,không còn yêu cầu nào nữa thì đưa về chưa yêu cầu
			query=	 "  SELECT A.PK_SEQ  " + 
			" 	FROM ERP_NHAPKHO A   " + 
			" 	WHERE A.SOLENHSANXUAT in  (SELECT LENHSANXUAT_FK FROM ERP_YEUCAUCHUYENKHO WHERE PK_SEQ="+ SOCHUNGTU+" ) " +
			" 	AND A.TRANGTHAI = 1  AND A.CONGTY_FK = '"+this.congtyId+"'  " +
			"  UNION " +
			"  SELECT PK_SEQ FROM ERP_YEUCAUCHUYENKHO  A "+
			"  WHERE PK_SEQ <> "+SOCHUNGTU+" AND A.LENHSANXUAT_FK IN  "+
			"  (SELECT LENHSANXUAT_FK FROM ERP_YEUCAUCHUYENKHO WHERE PK_SEQ="+ SOCHUNGTU+" ) AND TRANGTHAI <> 4  ";
			ResultSet rs=db.get(query);
			if (rs != null)
			{
				if(rs.next()){

				}else{
					//khonog con thi cap nhật trạng thái lsx về chưa yêu cầu nl
					query= " update ERP_LENHSANXUAT_GIAY set trangthai = '0' where pk_seq IN  " +
					" (SELECT LENHSANXUAT_FK FROM ERP_PHIEUYEUCAU_LSX WHERE PHIEUYEUCAU_FK="+ SOCHUNGTU+" ) ";

					if(!db.update(query))
					{
						rs.close();
						msg = "Không thể cập nhật câu lệnh " + query;
						return false;
					}
				}

				rs.close();
			}	    	
		}catch(Exception err){
			err.printStackTrace();
			this.msg=err.getMessage();
			return false;
		}
		return true;
	}

	private boolean revertChuyenKho(String chungtuid) {
		// TODO Auto-generated method stub
		try{

			String query="";

			// Nếu là đã chuyển kho trạng thái '0','1','2',thì cập nhật kho CHUYỂN lại tăng avai,giam booked
			query= " SELECT  ISNULL(IS_BOOKED,0) AS IS_BOOKED FROM ERP_YEUCAUKIEMDINH " +
			" WHERE PK_SEQ IN (SELECT YCKD_FK FROM ERP_CHUYENKHO WHERE PK_SEQ="+chungtuid+")";

			ResultSet rscheck=db.get(query);
			String IS_BOOKED="";
			if (rscheck != null)
			{
				if(rscheck.next()){
					IS_BOOKED=rscheck.getString("IS_BOOKED");
				}
				rscheck.close();
			}			 
			query=" SELECT CK.NGAYCHUYEN, isnull(ISHANGDIDUONG,'0') as ISHANGDIDUONG , CK.LENHSANXUAT_FK ,CK.NCC_CHUYEN_FK,CK.KHOXUAT_FK ,CHUYENKHO_FK,SANPHAM_FK,SOLO,VITRI,KHU,NGAYBATDAU,SOLUONG, ISNULL(CK.TRANGTHAISP,'0') AS TRANGTHAISP " +
			" FROM ERP_CHUYENKHO CK "+ 
			" INNER JOIN   ERP_CHUYENKHO_SP_XUATHANG  CKSP ON CK.PK_SEQ=CKSP.CHUYENKHO_FK "+
			" WHERE       ck.trangthai in ('0','1','2')  and   CK.PK_SEQ="+chungtuid;

			ResultSet rs=db.get(query);
			String lenhsanxuat_fk="";
			if (rs != null)
			{
				while(rs.next()){
					// giam booked_tang_avai
					// cập nhật kho tổng
					String ngaychungtu=rs.getString("NGAYCHUYEN");
					double soluongct=rs.getDouble("SOLUONG");
					String sanpham_fk=rs.getString("sanpham_fk");
					String solo=rs.getString("SOLO");
					String KHU=rs.getString("KHU");
					String trangthaisp=rs.getString("TRANGTHAISP");
					lenhsanxuat_fk=rs.getString("LENHSANXUAT_FK");

					if(KHU==null){
						KHU="";
					}
					String NGAYBATDAU=rs.getString("NGAYBATDAU");
					String VITRI=rs.getString("VITRI");
					String khoXuatId =rs.getString("KHOXUAT_FK");


					double soluong=0;
					double booked= (-1)*soluongct;
					double available=soluongct;

					if(rs.getString("ISHANGDIDUONG").equals("1")){
						//nếu là hàng đi đường thì kho xuất đã bị trừ,không phải cập nhật booked với avai,cập nhật tăng kho trở lại
						//,vì lúc chốt đơn hàng thì mới chuyển trạng thái ishangdiduong =1
						soluong=soluongct;
						booked=0;
						available= soluongct;
					}

					if(IS_BOOKED.equals("1")){
						soluong=soluongct;
						booked =soluongct;
						available= 0;
					}

					String msg1= util_kho.Update_Kho_Sp_Check_TonKhoNgay(this.db,khoXuatId,sanpham_fk,soluong,booked,available,0,ngaychungtu);
					if(msg1.length() >0){
						this.msg = msg1;
						this.db.getConnection().rollback();
						return false;
					}

					msg1=util_kho.Update_Kho_Sp_Chitiet(this.db,khoXuatId,sanpham_fk,soluong,booked,available,0,solo,VITRI,KHU,NGAYBATDAU);
					if(msg1.length() >0){
						this.msg = msg1;
						this.db.getConnection().rollback();
						return false;
					}

					if(util_kho.IsKhoQuanLyTrangThai(khoXuatId,this.db)){
						//neu la kho quan ly trang thai thi them kho trang thai
						msg1=util_kho.Update_Kho_Sp_Chitiet_TrangThai(this.db,khoXuatId,sanpham_fk,soluong,booked,available,0,solo,VITRI,KHU,NGAYBATDAU, trangthaisp);
						if(msg1.length() >0){
							this.msg = msg1;
							this.db.getConnection().rollback();
							return false;
						}

					}


					if(rs.getString("NCC_CHUYEN_FK")!=null){

						msg1=util_kho.Update_Kho_Sp_Chitiet_NCC(this.db,khoXuatId,sanpham_fk,soluong,booked,available,0,solo,VITRI,KHU,NGAYBATDAU,rs.getString("NCC_CHUYEN_FK"));
						if(msg1.length() >0){
							this.msg = msg1;
							this.db.getConnection().rollback();
							return false;
						}

					}

				}
				rs.close();
			}		 


			//nếu là đã hoàn tất thì giảm 2 kho

			//tăng kho xuất
			query=" SELECT CK.LENHSANXUAT_FK ,CK.NCC_CHUYEN_FK,CK.KHOXUAT_FK ,CHUYENKHO_FK,SANPHAM_FK,SOLO,VITRI,KHU,NGAYBATDAU,SOLUONG, ISNULL(CK.TRANGTHAISP,'0') AS TRANGTHAISP " +
			" FROM ERP_CHUYENKHO CK "+ 
			" INNER JOIN   ERP_CHUYENKHO_SP_XUATHANG  CKSP ON CK.PK_SEQ=CKSP.CHUYENKHO_FK "+
			" WHERE CK.trangthai in ('3') and CK.PK_SEQ="+chungtuid;
			rs=db.get(query);
			if (rs != null)
			{
				while(rs.next()){
					// tăng  available
					// tăng số lượng kho xuất
					double soluongct=rs.getDouble("SOLUONG");
					String sanpham_fk=rs.getString("sanpham_fk");
					String solo=rs.getString("SOLO");
					String KHU=rs.getString("KHU");
					String khoXuatId=rs.getString("KHOXUAT_FK");
					String trangthaisp=rs.getString("TRANGTHAISP");

					double soluong= soluongct;
					double booked= 0;
					double available= soluongct; 

					if(IS_BOOKED.equals("1")){
						soluong=soluongct;
						booked =soluongct;
						available= 0;
					}

					if(KHU==null){
						KHU="";
					}
					String NGAYBATDAU=rs.getString("NGAYBATDAU");
					String VITRI=rs.getString("VITRI");
					// giảm số lượng ,giảm bookd,avai giữ nguyên

					String msg1= util_kho.Update_Kho_Sp(db,khoXuatId,sanpham_fk, soluong,booked,available,0);
					if(msg1.length() >0){
						this.msg = msg1;
						this.db.getConnection().rollback();
						return false;
					}

					msg1=util_kho.Update_Kho_Sp_Chitiet(db,khoXuatId,sanpham_fk,  soluong,booked,available,0,solo,VITRI,KHU,NGAYBATDAU);
					if(msg1.length() >0){
						this.msg = msg1;
						this.db.getConnection().rollback();
						return false;
					}

					if(util_kho.IsKhoQuanLyTrangThai( khoXuatId, db)){
						//neu la kho quan ly trang thai thi them kho trang thai
						msg1=util_kho.Update_Kho_Sp_Chitiet_TrangThai(db,khoXuatId,sanpham_fk, soluong,booked,available ,0,solo,VITRI,KHU,NGAYBATDAU, trangthaisp);
						if(msg1.length() >0){
							this.msg = msg1;
							this.db.getConnection().rollback();
							return false;
						}

					}

					if(rs.getString("NCC_CHUYEN_FK")!=null){

						msg1=util_kho.Update_Kho_Sp_Chitiet_NCC(db,khoXuatId,sanpham_fk,  soluong,booked,available,0,solo,VITRI,KHU,NGAYBATDAU,rs.getString("NCC_CHUYEN_FK"));
						if(msg1.length() >0){
							this.msg = msg1;
							this.db.getConnection().rollback();
							return false;
						}

					}

				}
				rs.close();
			}


			query=	" SELECT sp.ma+ ' - ' +sp.ten as tensp , CK.LENHSANXUAT_FK ,CK.NCC_NHAN_FK,CK.KHONHAN_FK ,CHUYENKHO_FK,SANPHAM_FK , SUM( SOLUONG)  AS SOLUONG  , "+
			" (SELECT SUM(SOLUONG) FROM ERP_LENHSANXUAT_BOM_GIAY BOM WHERE BOM.LENHSANXUAT_FK=CK.LENHSANXUAT_FK AND BOM.VATTU_FK=CKSP.SANPHAM_FK) AS SOLUONGBOKED "+
			" FROM ERP_CHUYENKHO CK   "+
			" INNER JOIN   ERP_CHUYENKHO_SP_NHANHANG  CKSP ON CK.PK_SEQ=CKSP.CHUYENKHO_FK " +
			" inner join erp_sanpham sp on sp.pk_seq=cksp.sanpham_fk  "+ 
			" WHERE CK.TRANGTHAI IN ('3') AND CK.PK_SEQ= "+chungtuid +
			" GROUP BY CK.LENHSANXUAT_FK ,CK.NCC_NHAN_FK,CK.KHONHAN_FK ,CHUYENKHO_FK,SANPHAM_FK,sp.ma,sp.ten " ;

			rs=db.get(query);

			if (rs != null)
			{
				while (rs.next()){

					String tensp=rs.getString("tensp");
					lenhsanxuat_fk=rs.getString("LENHSANXUAT_FK");

					double soluongct=rs.getDouble("SOLUONG");
					double soluongbooked=rs.getDouble("soluongboked");
					double soluong_giam_book=0;
					double soluongct_=0;
					if(soluongbooked >soluongct){
						soluong_giam_book= soluongct;
						soluongct_=0;
					}else{
						soluong_giam_book=soluongbooked;
						soluongct_=soluongct-soluongbooked;
					}
					System.out.println("soluong_giam_book : "+soluong_giam_book);
					System.out.println("soluongct_ : "+soluongct_);



					String sanpham_fk=rs.getString("sanpham_fk");


					String KHONHAN_FK=rs.getString("KHONHAN_FK");



					//						double soluong=(-1)* soluongct;
					//						double booked= 0;
					//						double available=(-1)* soluongct; 
					//						double dongiamua=0;
					//với số lượng chuyển qua và booked vào kho sản xuất thì giảm booked

					if(soluong_giam_book>0){

						String msg1= util_kho.Update_Kho_Sp( db, KHONHAN_FK,sanpham_fk,(-1)* soluong_giam_book,(-1)* soluong_giam_book,0,0);
						if(msg1.length() >0){
							this.msg = msg1;
							this.db.getConnection().rollback();
							return false;
						}
						query=" update erp_lenhsanxuat_bom_giay set booked_lsx =ISNULL(booked_lsx,0)-"+soluong_giam_book+"  where lenhsanxuat_fk="+lenhsanxuat_fk+" and vattu_fk= "+sanpham_fk;
						if(!db.update(query)){
							this.msg= "Không thể cập nhật: "+query;
							this.db.getConnection().rollback();
							return false;
						}

					}

					// cập nhật vào kho nhận,giảm với số lượng không có booked
					if(soluongct_>0){

						query=" select available from erp_khott_sanpham  where sanpham_fk ="+sanpham_fk+" and khott_fk= "+KHONHAN_FK;

						ResultSet rscheckkho=db.get(query);
						double soluongtonkho=0;
						if (rscheckkho != null)
						{
							if(rscheckkho.next()){
								soluongtonkho=rscheckkho.getDouble("available");

							}
							rscheckkho.close();
						}							
						if(soluongtonkho<soluongct_){
							this.msg = "Số lượng tồn hiện tại của sản phẩm : "+tensp+" trong kho không đủ để revert ,vui lòng kiểm tra lại xuất nhập của sản phẩm này trong kho nhận";
							this.db.getConnection().rollback();
							return false;
						}


						String msg1= util_kho.Update_Kho_Sp( db, KHONHAN_FK,sanpham_fk,(-1)*soluongct_,0,(-1)*soluongct_,0);
						if(msg1.length() >0){
							this.msg = msg1;
							this.db.getConnection().rollback();
							return false;
						}
					}



				}
				rs.close();
			}

			// trừ kho chi tiết
			query=	" SELECT CK.LENHSANXUAT_FK ,CK.NCC_NHAN_FK,CK.KHONHAN_FK ,CHUYENKHO_FK,SANPHAM_FK,SOLO,VITRI,KHU,NGAYBATDAU,SOLUONG, "+
			" ISNULL(CK.TRANGTHAISP,'0') AS TRANGTHAISP   "+
			" FROM ERP_CHUYENKHO CK   "+
			" INNER JOIN   ERP_CHUYENKHO_SP_NHANHANG  CKSP ON CK.PK_SEQ=CKSP.CHUYENKHO_FK "+ 
			" WHERE CK.trangthai in ('3') and CK.PK_SEQ="+chungtuid;

			rs=db.get(query);
			if (rs != null)
			{
				while (rs.next()){

					lenhsanxuat_fk=rs.getString("LENHSANXUAT_FK");

					double soluongct=rs.getDouble("SOLUONG");


					String sanpham_fk=rs.getString("sanpham_fk");
					String solo=rs.getString("SOLO");

					String khukhonhan=rs.getString("KHU");
					if(khukhonhan==null){
						khukhonhan="";
					}

					String KHONHAN_FK=rs.getString("KHONHAN_FK");
					String trangthaisp=rs.getString("TRANGTHAISP");

					String NGAYBATDAU=rs.getString("NGAYBATDAU");
					double soluong=(-1)* soluongct;
					double booked= 0;
					double available=(-1)* soluongct; 
					double dongiamua=0;
					//với số lượng chuyển qua và booked vào kho sản xuất thì giảm booked

					String msg1= util_kho.Update_Kho_Sp_Chitiet( db,KHONHAN_FK,sanpham_fk,soluong,booked,available,dongiamua,solo,"",khukhonhan,NGAYBATDAU);
					if(msg1.length() >0){
						this.msg = msg1;
						this.db.getConnection().rollback();
						return false;
					}

					// số lượng đạt
					//trangthai=0 là hàng đạt
					if(util_kho.IsKhoQuanLyTrangThai(KHONHAN_FK, db)) {
						msg1= util_kho.Update_Kho_Sp_Chitiet_TrangThai( db,KHONHAN_FK,sanpham_fk,soluong,booked,available,dongiamua,solo,"",khukhonhan,NGAYBATDAU,trangthaisp);
						if(msg1.length() >0){
							this.msg = msg1;
							this.db.getConnection().rollback();
							return false;
						}
					}
					// trường hợp kho gia công 

				}
				rs.close();
			}

			query=" update ERP_CHUYENKHO set trangthai = '4' where pk_seq = '" + chungtuid + "'";
			if(!db.update(query))
			{
				msg = "Khong the cap nhat ERP_CHUYENKHO: " + query;

				return false;
			}
			//KIỂM TRA CÒN CHUYỂN KHO NÀO KHÔNG?
			//NẾU YÊU CẦU CHƯA CHUYỂN KHO THÌ ĐƯA YÊU CẦU CHUYỂN KHO VỀ CHƯA CHUYỂN NGUYÊN LIỆU
			query= " SELECT PK_SEQ FROM ERP_CHUYENKHO WHERE  " +
			" YCKD_FK=(SELECT yeucauchuyenkho_fk FROM ERP_CHUYENKHO WHERE PK_SEQ="+chungtuid+") AND TRANGTHAI NOT IN ('4') AND PK_SEQ <> "+chungtuid+"";
			rs=db.get(query);
			if (rs != null)
			{
				if(rs.next()){
					// đang có yêu cầu thì để về trạng thái đã chuyển kho
					query="UPDATE ERP_YEUCAUCHUYENKHO SET TRANGTHAI='2' WHERE PK_SEQ =(SELECT yeucauchuyenkho_fk FROM ERP_CHUYENKHO WHERE PK_SEQ="+chungtuid+")  ";

				}else{
					/// đưa về trạng thái đã chốt
					query="UPDATE ERP_YEUCAUCHUYENKHO SET TRANGTHAI='1' WHERE PK_SEQ =(SELECT yeucauchuyenkho_fk FROM ERP_CHUYENKHO WHERE PK_SEQ="+chungtuid+")  ";
				}
				rs.close();
			}
			if(!db.update(query))
			{
				msg = "Khong the cap nhat ERP_YEUCAUCHUYENKHO: " + query;
				return false;
			}
		}catch(Exception err){
			err.printStackTrace();
			this.msg=err.getMessage();
			return false;
		}
		return true;
	}

	private boolean revertNHAPKHO(String sonhapkho) {
		try{
			// những kiểm định chưa chốt thì hủy luôn
			String query=" SELECT * FROM ERP_YEUCAUKIEMDINH    WHERE trangthai=0  and  nhapkho_fk= "+sonhapkho;

			ResultSet rsct=db.get(query);

			if (rsct != null)
			{
				while(rsct.next()){
					String yckd_fk= rsct.getString("pk_Seq");
					query=" update  ERP_YEUCAUKIEMDINH set trangthai=3   WHERE pk_seq= "+yckd_fk;

					if(!db.update(query))
					{
						this.msg = "3.Khong the cap nhat ERP_KHOTT_SP_CHITIET: " + query;
						return false;
					}

					query  =" SELECT SANPHAM_FK, SOLUONG, SOLUONGDAT , NK.KHONHAP as KHONHAN_FK ,SOLO,NGAYKIEM   " +
					" FROM ERP_YEUCAUKIEMDINH KD INNER JOIN ERP_NHAPKHO NK ON NK.PK_SEQ=KD.NHAPKHO_FK " +
					" WHERE IS_BOOKED= '1' AND KD.PK_SEQ = "+yckd_fk;

					ResultSet  rs=db.get(query);

					if(rs.next()){

						String khonhap=rs.getString("KHONHAN_FK");
						String spId=rs.getString("sanpham_fk");
						double soluongkhongdat=rs.getDouble("SOLUONG");
						String ngaynhapkho=rs.getString("ngaykiem");
						String solo=rs.getString("solo");

						String   msg1= util_kho.Update_Kho_Sp_Check_TonKhoNgay(db, khonhap, spId, 0,  (-1) *(soluongkhongdat), soluongkhongdat,0, ngaynhapkho);

						if(msg1.length() >0)
						{ 
							this.msg= msg1;
							return false;
						}
						msg1= util_kho.Update_Kho_Sp_Chitiet(db, khonhap, spId, 0,    (-1)*(soluongkhongdat),  soluongkhongdat , 0 ,  solo, "", "", ngaynhapkho);

						if(msg1.length() >0)
						{
							this.msg= msg1;
							return false;
						}
					}
				}
				rsct.close();
			}			

			query=" SELECT LSX.LOHOI_FK,LSX.PK_SEQ AS LENHSANXUAT_FK ,SP.MA +'- '+ SP.TEN  AS TEN , " +
			" NKSP.SOLUONGNHAP as SOLUONG,NKSP.SOLO,NK.KHONHAP,NKSP.SANPHAM_FK ,NKSP.NGAYNHAPKHO,NKSP.KHUVUCKHO_FK,DONGIAVIET " + 
			" FROM ERP_NHAPKHO NK INNER JOIN ERP_NHAPKHO_SANPHAM NKSP  " +
			" ON NK.PK_SEQ=NKSP.SONHAPKHO_FK   " +
			" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=NKSP.SANPHAM_FK   " +
			" INNER JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ=NK.SOLENHSANXUAT  " +
			" WHERE NK.TRANGTHAI=1 AND NK.PK_SEQ="+sonhapkho;
			ResultSet rsck=db.get(query);
			if (rsck != null)
			{
				while(rsck.next()){
					// cập nhật lại kho
					Double soluongct=(-1)*rsck.getDouble("SOLUONG");
					String KHONHAP=rsck.getString("KHONHAP");
					String sanpham_fk= rsck.getString("SANPHAM_FK");
					String solo= rsck.getString("SOLO");
					//ngày bắt đầu
					String NgayBatDau= rsck.getString("NGAYNHAPKHO");
					String KHUVUCKHO_FK= rsck.getString("KHUVUCKHO_FK");
					double dongia=rsck.getDouble("DONGIAVIET");

					if(util_kho.getIsQuanLyKhuVuc(KHONHAP, db).equals("1")){
						if(KHUVUCKHO_FK==null || KHUVUCKHO_FK.length()> 0){
							this.msg= "Không xác định được khu vực cho kho hủy nhận hàng";
							return false;
						}
					}
					String vitri="100000";
					String msg1=util_kho.Update_Kho_Sp_Chitiet(db, KHONHAP, sanpham_fk, soluongct, 0, soluongct, dongia, solo, vitri, KHUVUCKHO_FK, NgayBatDau);

					if(msg1.length()> 0 )
					{
						this.msg = msg1;
						return false;
					}
					msg1=util_kho.Update_Kho_Sp(db, KHONHAP, sanpham_fk, soluongct, 0, soluongct, dongia);

					if(msg1.length()> 0 )
					{
						this.msg = msg1;
						return false;
					}
					// GIẢM SỐ LƯỢNG TRONG BẢNG LƯU LÒ HƠI VÀ SẢN LƯỢNG NHẬP KHO
					query=" UPDATE ERP_LENHSANXUAT_LOHOI SET SOLUONG=SOLUONG-"+rsck.getDouble("SOLUONG")+" where LENHSANXUAT_FK = "+rsck.getDouble("LENHSANXUAT_FK")+"  AND LOHOI_FK="+rsck.getDouble("LOHOI_FK");
					if(db.updateReturnInt(query)!=1)
					{
						this.msg = "3.Không thể cập nhật dữ liệu : " + query;

						return false;
					}

				}
				rsck.close();
			}

			query="UPDATE ERP_NHAPKHO SET TRANGTHAI=2 WHERE PK_SEQ="+sonhapkho;
			if(db.updateReturnInt(query)!=1)
			{
				this.msg = "3.Khong the cap nhat ERP_KHOTT_SP_CHITIET: " + query;
				return false;
			}
			// kiểm tra còn nhập hàng nào không?hết rồi thì cập nhật trạng thái của lệnh sản xuất

			query=" SELECT PK_SEQ FROM ERP_NHAPKHO WHERE SOLENHSANXUAT =(select solenhsanxuat from erp_nhapkho where pk_seq="+sonhapkho+")  AND TRANGTHAI=1";
			rsck=db.get(query);
			if (rsck != null)
			{
				if(!rsck.next()){
					query="UPDATE ERP_LENHSANXUAT_GIAY SET TRANGTHAI= 2 WHERE PK_SEQ=(select solenhsanxuat from erp_nhapkho where pk_seq="+sonhapkho+")";
					if(db.updateReturnInt(query)!=1)
					{
						this.msg = "3.Khong the cap nhat ERP_KHOTT_SP_CHITIET: " + query;
						return false;
					}
				}
				rsck.close();
			}			
		}catch(Exception er){
			er.printStackTrace();
			this.msg=er.getMessage();
		}
		return true;
	}

	private boolean revertKIEMDINH(String sochungtu2) {
		// TODO Auto-generated method stub
		try{
			// lấy những phiếu chuyển kho của yêu cầu kiểm định
			String query=		" select PK_SEQ,ngaychuyen ,case when TRANGTHAI='0' then N'Chưa duyệt' when trangthai='1' then N'Chờ nhận' "+
			" when TRANGTHAI='2' then N'Đã xác nhận' when TRANGTHAI='3' then N'Hoàn tất' end "+ 
			" as Trangthai from erp_chuyenkho where trangthai <> 4 and  YCKD_FK="+sochungtu2;


			ResultSet rschuyenkho=db.get(query);
			if (rschuyenkho != null)
			{
				while(rschuyenkho.next()){
					query = " insert ERP_HUYLENHSANXUAT_CHUNGTU(HUYLENHSANXUAT_FK, thutu, sochungtu, ngaychungtu, trangthai, loaichungtu,TYPE) " +
					" values('" + this.id + "', '3',  '" + rschuyenkho.getString("PK_SEQ")+ "', '" +rschuyenkho.getString("ngaychuyen") + "',N'"+rschuyenkho.getString("trangthai")+"', N'CHUYỂN KHO','CHUYENKHO')";
					if(!db.update(query))
					{
						rschuyenkho.close();
						this.msg = "Không thể tạo mới ERP_HUYLENHSANXUAT_CHUNGTU, " + query;
						return false;
					}

					if(!this.revertChuyenKho(rschuyenkho.getString("PK_SEQ"))){
						rschuyenkho.close();
						this.msg="Không thể hủy chuyển kho tự động :"+ rschuyenkho.getString("PK_SEQ")+" của  phiếu nhập kho : "+sochungtu ;
						return false;
					}
				}
				rschuyenkho.close();
			}
			query  =" select sanpham_fk, soluong, soluongDat,soluong- isnull(soluongDat,0) as soluongkhongdat ,KHONHAN_FK,solo,ngaykiem   " +
			" from ERP_YeuCauKiemDinh where IS_BOOKED= '1' and  pk_seq="+sochungtu2;
			ResultSet rs=db.get(query);
			if (rs != null)
			{
				if(rs.next()){
					String khonhap=rs.getString("KHONHAN_FK");
					String spId=rs.getString("sanpham_fk");
					double soluongkhongdat=rs.getDouble("soluongkhongdat");
					String ngaynhapkho=rs.getString("ngaykiem");
					String solo=rs.getString("solo");

					String   msg1= util_kho.Update_Kho_Sp_Check_TonKhoNgay(db, khonhap, spId, 0,  (soluongkhongdat),(-1)* soluongkhongdat,0, ngaynhapkho);

					if(msg1.length() >0)
					{ 
						this.msg= msg1;
						return false;
					}
					msg1= util_kho.Update_Kho_Sp_Chitiet(db, khonhap, spId, 0,  (soluongkhongdat),    (-1)*soluongkhongdat , 0 ,  solo, "", "", ngaynhapkho);

					if(msg1.length() >0)
					{
						this.msg= msg1;
						return false;
					}
				}
				rs.close();
			}

			// đưa yêu cầu kiểm định về trạng thái chưa duyệt,để duyệt lại
			query="update  ERP_YeuCauKiemDinh set trangthai=0,SOLUONGDAT=SOLUONG where pk_seq="+sochungtu2;
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_YeuCauKiemDinh, " + query;
				return false;
			}

			return true;

		}catch(Exception er){
			er.printStackTrace();
			this.msg=er.getMessage();
			return false;
		}

	}

	private boolean revertTieuhao(String sochungtu) {

		try{
			String query="  select SANPHAM_FK,KHOTT_FK,SOLUONG,SOLO ,TIEUHAONGUYENLIEU_FK,LOAI,NGAYBATDAU  " +
			"  from ERP_LENHSANXUAT_TIEUHAO_CHITIET  WHERE TIEUHAONGUYENLIEU_FK ="+sochungtu;
			ResultSet rs=db.get(query);

			while (rs.next()){
				Double soluongct=rs.getDouble("SOLUONG");
				String khoTieuhao_fk=rs.getString("KHOTT_FK");
				String sanpham_fk= rs.getString("SANPHAM_FK");
				String solo= rs.getString("SOLO");
				String Ngaybatdau=rs.getString("ngaybatdau");

				query = " update ERP_KHOTT_SP_CHITIET set   soluong = soluong + " + soluongct + ", AVAILABLE = AVAILABLE + " + soluongct + " " +
				" where  KHOTT_FK =  " +khoTieuhao_fk +
				" and SANPHAM_FK =" + sanpham_fk +
				" and SOLO = '" + solo + "' and ngaybatdau ='"+Ngaybatdau+"'";
				if(db.updateReturnInt(query)!=1)
				{
					this.msg = "3.Khong the cap nhat ERP_KHOTT_SP_CHITIET: " + query;
					db.getConnection().rollback();
					return false;
				}
				//ERP_KHOTT_SANPHAM
				// là bán thành phẩm hoặc sản phẩm thêm trực tiếp lúc tiêu hao
				if(rs.getString("LOAI").trim().equals("3") || rs.getString("LOAI").trim().equals("-1") ){ 
					query=  " update ERP_KHOTT_SANPHAM set  soluong = soluong + " + soluongct + ", available = available + " + soluongct + " " +
					" where KHOTT_FK = "+khoTieuhao_fk + 
					" and SANPHAM_FK = "+sanpham_fk;

					if(db.updateReturnInt(query)!=1)
					{
						this.msg = "4.Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;

						return false;
					}
					// 

				}else{
					//Trường hợp những sản phẩm đã booked từ kho sản xuất trong lệnh sản xuất
					//Thì giảm booked tới mức đã booked và giảm số lượng,sau đó trừ thẳng vào avai,số lượng

					query=" select isnull(GIAM_BOOKED,0) as GIAM_BOOKED from erp_lenhsanxuat_bom_giay " +
					" where checkkho='1' and vattu_fk="+sanpham_fk+" and lenhsanxuat_fk= (select lenhsanxuat_fk from ERP_TIEUHAONGUYENLIEU where pk_seq="+sochungtu+" )"  ;
					ResultSet rscheck=db.get(query);
					double Soluongconbooked=0;
					if(rscheck.next()){
						Soluongconbooked=rscheck.getDouble("GIAM_BOOKED");
					}
					if(Soluongconbooked-soluongct >=0){
						//thì giam kho sản xuất booked =booked+ soluongiam,soluong =soluong+ soluongiam		
						//giam_booked =giam_booked-soluongiam


						query =   " update ERP_KHOTT_SANPHAM set  soluong = soluong + '" + soluongct + "', booked = booked + '" + soluongct + "' " +
						" where KHOTT_FK = "+khoTieuhao_fk+ 
						" and SANPHAM_FK = "+sanpham_fk;
						if(db.updateReturnInt(query)!=1)
						{
							this.msg = "4.Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;

							return false;
						}
						// đã booked trong bảng erp_lenhsanxuat_bom_giay sẽ giam số lượng đã trừ booked
						query=  " update erp_lenhsanxuat_bom_giay set GIAM_BOOKED =isnull(GIAM_BOOKED,0)-"+soluongct+"  " +
						" where checkkho='1' and vattu_fk="+sanpham_fk+" and lenhsanxuat_fk= (select lenhsanxuat_fk from ERP_TIEUHAONGUYENLIEU where pk_seq="+sochungtu+" )"  ;
						if(!db.update(query) )
						{
							this.msg = "4.Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;

							return false;
						}

					}else{
						double soluong_du=  soluongct-Soluongconbooked ;
						//truong hop 1 thì giam kho sản xuất booked =booked+ soluongiam,soluong =soluong+ soluongiam		
						//giam_booked =giam_booked-soluongiam
						query =   " update ERP_KHOTT_SANPHAM set  soluong = soluong + '" + Soluongconbooked + "', booked = booked + '" + Soluongconbooked + "' " +
						" where KHOTT_FK = "+khoTieuhao_fk+ 
						" and SANPHAM_FK = "+sanpham_fk;
						if(db.updateReturnInt(query)!=1)
						{
							this.msg = "4.Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;

							return false;
						}
						// đã booked trong bảng erp_lenhsanxuat_bom_giay sẽ giam số lượng đã trừ booked
						query=  " update erp_lenhsanxuat_bom_giay set GIAM_BOOKED =isnull(GIAM_BOOKED,0)-"+Soluongconbooked+"  " +
						" where checkkho='1' and vattu_fk="+sanpham_fk+" and lenhsanxuat_fk= (select lenhsanxuat_fk from ERP_TIEUHAONGUYENLIEU where pk_seq="+sochungtu+" )"  ;
						if(!db.update(query) )
						{
							this.msg = "4.Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}
						// đối với số lượng con dư thì trừ thẳng vào kho :thì giam kho sản xuất soluong =soluong+ a,available =available+a						
						query =   " update ERP_KHOTT_SANPHAM set  soluong = soluong + '" +formatter.format(soluong_du) + "', available = available + '" + formatter.format(soluong_du) + "' " +
						" where KHOTT_FK = "+khoTieuhao_fk+ 
						" and SANPHAM_FK = "+sanpham_fk;
						if(db.updateReturnInt(query)!=1)
						{
							this.msg = "4.Khong the cap nhat ERP_KHOTT_SANPHAM: " + query;

							return false;
						}
					}


				}

			}
			rs.close();
			// cập nhật lại trạng thái phiếu tiêu hao
			query="UPDATE ERP_TIEUHAONGUYENLIEU SET TRANGTHAI=2 WHERE PK_SEQ="+sochungtu;
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_HUYLENHSANXUAT_CHUNGTU, " + query;
				return false;
			}	
			// kiểm tra xem còn phiếu tiêu hao nào không,để cập nhật lại trạng thái của lệnh sản xuất

			query=" SELECT PK_SEQ FROM ERP_TIEUHAONGUYENLIEU WHERE TRANGTHAI<> 2   AND NHAPKHO_PARENT_FK IS NULL  AND  LENHSANXUAT_FK= (SELECT LENHSANXUAT_FK  FROM  ERP_TIEUHAONGUYENLIEU WHERE PK_SEQ="+sochungtu+" )";
			rs=db.get(query);
			if(rs.next()){
				// vẫn giữ nguyên trạng thái của lệnh sản xuất

			}else{
				// cập nhật lại trang thái của lệnh sản xuất 
				// kiểm tra đã kiểm định chất lượng chưa?
				query="SELECT pk_seq FROM ERP_YEUCAUKIEMDINH WHERE TRANGTHAI=1  AND LENHSANXUAT_FK=(SELECT LENHSANXUAT_FK  FROM  ERP_TIEUHAONGUYENLIEU WHERE PK_SEQ="+sochungtu+" ) ";
				rs=db.get(query);
				if(rs.next()){
					// cập nhật lệnh sản xuất về trạng thái Đã kiểm định chất lượng
					query="Update erp_lenhsanxuat_giay set trangthai=5 where pk_seq= (SELECT LENHSANXUAT_FK  FROM  ERP_TIEUHAONGUYENLIEU WHERE PK_SEQ="+sochungtu+" )";
					if(!db.update(query))
					{
						this.msg = "Không thể cập nhật erp_lenhsanxuat_giay, " + query;
						return false;
					}

				}else{
					// Chắc chắn có nhận hàng

					// KIỂM TRA CÓ NHẬN HÀNG HAY KHÔNG?
					query="select pk_seq from erp_nhapkho where  TRANGTHAI=1  AND NHAPKHO_PARENT_FK IS NULL AND    SOLENHSANXUAT=(SELECT LENHSANXUAT_FK  FROM  ERP_TIEUHAONGUYENLIEU WHERE PK_SEQ="+sochungtu+" ) ";
					ResultSet rscheck1=db.get(query);
					if(rscheck1.next()){

						query="Update erp_lenhsanxuat_giay set trangthai=4 where pk_seq= (SELECT LENHSANXUAT_FK  FROM  ERP_TIEUHAONGUYENLIEU WHERE PK_SEQ="+sochungtu+" )";

						if(!db.update(query))
						{
							this.msg = "Không thể cập nhật erp_lenhsanxuat_giay, " + query;
							rscheck1.close();
							return false;
						}
					}else{

					}
				}
			}
			rs.close();
			return true;
		}catch(Exception er){
			this.msg=er.getMessage();
			er.printStackTrace();
			return false;
		}
	}

*/	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public String getSochungtu() 
	{
		return this.sochungtu;
	}

	public void setSochungtu(String sochungtu) 
	{
		this.sochungtu = sochungtu;
	}

	public ResultSet getSochungtuDetail() 
	{
		return this.sochungtuDetail;
	}

	public void setSochugntuDetail(ResultSet soctdetail) 
	{
		this.sochungtuDetail = soctdetail;
	}

	public ResultSet getSochungtuRequest() 
	{
		return this.sochungtuRequest;
	}

	public void setSochungtuRequest(ResultSet soctRequest) 
	{
		this.sochungtuRequest = soctRequest;
	}

	private String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}


	public void DbClose() {
		try{
			this.db.shutDown();
			if (this.sochungtuRequest != null)
				this.sochungtuRequest.close();
			if (this.sochungtuDetail != null)
				this.sochungtuDetail.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}


	public String getNgayghinhan() {

		return this.Ngayghinhan;
	}


	public void setNgayghinhan(String Ngayghinhan) {

		this.Ngayghinhan=Ngayghinhan;
	}


	public String getSophieutieuhao() {

		return this.tieuhaoId;
	}


	public void setSoPhieutieuhao(String sotieuhao) {

		this.tieuhaoId = sotieuhao;
	}


	public String getSophieukiemdinh() {

		return this.qcId;
	}


	public void setSoPhieukiemdinh(String sokiemdinh) {

		this.qcId = sokiemdinh;
	}

	@Override
	public String getloaichungtu() {
		// TODO Auto-generated method stub
		return this.loaichungtu;
	}

	@Override
	public void setloaichungtu(String loaichungtu) {
		// TODO Auto-generated method stub
		this.loaichungtu=loaichungtu;
	}
	@Override
	public void setDienGiaiCT(String dienGiaiCT){
		this.dienGiaiCT = dienGiaiCT;
	}
	@Override
	public String getDienGiaiCT(){
		return this.dienGiaiCT;
	}



}
