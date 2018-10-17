package geso.traphaco.erp.servlets.baocao;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.servlets.report.ReportAPI;
 
import geso.dms.distributor.util.Utility;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.servlets.hoadon.ErpHoaDonSvl;
import geso.traphaco.erp.servlets.lapngansach.KhoitaongansachSvl;
import geso.traphaco.center.util.Utility_Kho;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.w3c.dom.ls.LSInput;

import Z.DB;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;

import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;


public class ErpBCThuongKhachHang extends HttpServlet {
	private static final long serialVersionUID = 1L; 
    public ErpBCThuongKhachHang() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
	    IStockintransit obj = new Stockintransit();
	    Utility util = new Utility();
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    obj.setuserId(userId);
	    obj.init();
	    
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBcThuongKhachHang.jsp";
		response.sendRedirect(nextJSP);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
	    IStockintransit obj = new Stockintransit();	
	    Utility util = new Utility();
	  
	    obj.setuserId((String)session.getAttribute("userId")==null?"":
	    				(String) session.getAttribute("userId"));
	    
	    obj.setuserTen((String)session.getAttribute("userTen")==null? "":
	    					(String) session.getAttribute("userTen"));
	    
   	 	obj.setnppId(util.antiSQLInspection(request.getParameter("nppId"))==null?"":
   	 						util.antiSQLInspection(request.getParameter("nppId")));
   	 	
   	   	obj.setkenhId(util.antiSQLInspection(request.getParameter("kenhId"))==null? "":
   	 						util.antiSQLInspection(request.getParameter("kenhId")));
   	 	
	   	 obj.setdvkdId(util.antiSQLInspection(request.getParameter("dvkdId"))==null? "":
	   		 				util.antiSQLInspection(request.getParameter("dvkdId")));
	   	 
	   	 obj.setMonth(util.antiSQLInspection(request.getParameter("month"))==null? "":
	   		 				util.antiSQLInspection(request.getParameter("month")));
	   	 
	   	 obj.setYear(util.antiSQLInspection(request.getParameter("year"))==null? "":
	   		 				util.antiSQLInspection(request.getParameter("year")));	   	 
	 	 
	   	 obj.setvungId(util.antiSQLInspection(request.getParameter("vungId"))==null? "":
	   		 				util.antiSQLInspection(request.getParameter("vungId")));	   	 
	   	 
	   	 obj.setkhuvucId(util.antiSQLInspection(request.getParameter("khuvucId"))==null? "":
	   		 				util.antiSQLInspection(request.getParameter("khuvucId")));	 
	   	 	   	 
	   	
		 obj.setdvdlId(util.antiSQLInspection(request.getParameter("dvdlId"))==null? "":
			 				util.antiSQLInspection(request.getParameter("dvdlId")));		 
		
		 obj.setDdkd(util.antiSQLInspection(request.getParameter("ddkdId"))==null? "":
			 				util.antiSQLInspection(request.getParameter("ddkdId")));
		 
		 
		 String []fieldsHien = request.getParameterValues("fieldsHien");
		 obj.setFieldShow(fieldsHien);		 
	 
		 
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBcThuongKhachHang.jsp";		 
		try{
			String action=util.antiSQLInspection(request.getParameter("action"));
			if(action.equals("Taomoi")){
				response.setContentType("application/xlsm");
		        response.setHeader("Content-Disposition", 
		        		"attachment; filename=ThuongThucDatKhachHang.xlsm");
		        OutputStream out = response.getOutputStream();
		        String query = setQuery(obj);
		        CreatePivotTable(out,obj,query);
			}else if(action.equals("thuchientinh")){
				System.out.println("come here :"+action); 
				 
				CheckChuyenKho_Nhan();
				obj.init();	    
				session.setAttribute("obj", obj);
				session.setAttribute("userId", obj.getuserId());		
				response.sendRedirect(nextJSP);
			}
		}catch(Exception ex){
			obj.setMsg(ex.getMessage());
			obj.init();	    
			session.setAttribute("obj", obj);
			session.setAttribute("userId", obj.getuserId());		
			response.sendRedirect(nextJSP);
			
		}
		
	}
	
	
	public static void main ( String args [  ]  )   {
		  try{
			  System.out.println("thuong ");
			  dbutils db =new dbutils();
			//b1 check chuyen kho kho chuyen là 3 kho 100000,100001,100002
			 CheckChuyenKho_TrongLSX();
			 
			  System.out.println("khong laf duoc ");
			  String query="select ma from sanpham where ma=''";
			  ResultSet rscheck=db.get(query);
			  if(rscheck.next()){
				  
			  }
			  rscheck.close();
			  // check kho nhận là 3 kho 100000,100001,100002
			 // CheckChuyenKho_Nhan();
			 
		  }catch(Exception err){
			  err.printStackTrace();
		  }
			
	  }
	
	
	private static void CheckChuyenKho_Nhan() {
		// TODO Auto-generated method stub
		try{
			 dbutils db =new dbutils();
			 
			 String query="select ddh.pk_seq 	from  	  ERP_DONDATHANG ddh " +
			 		" 	where ddh.TRANGTHAI <> 7  and ddh.MakeToStock='0' and ddh.TRANGTHAI=1 and ddh.NGAYDAT like '2015-01%'";
			 
			 ResultSet rs=db.get(query);
			 while(rs.next()){
				 String ddhid=rs.getString("pk_seq");
				 CheckBookedDonHang(db,ddhid);
			 }
			 rs.close();
		 
			 
		 }catch(Exception er){
			er.printStackTrace();
		}
	}

	
	private static boolean CheckBookedDonHang(dbutils db, String ddhid) {
		// TODO Auto-generated method stub
		try{
			db.getConnection().setAutoCommit(false);

			String	query =     " 	SELECT b.MA + '-' +b.ten  as ten , A.KHOTT_FK,A.SANPHAM_FK  , "+
					    "	CASE WHEN A.DVDL_FK != B.DVDL_FK AND CAST(D.SOLUONG2 AS FLOAT) >0 "+ 
						"	THEN A.SOLUONG * ISNULL(D.SOLUONG1, 1) / ISNULL(D.SOLUONG2, 1)    ELSE A.SOLUONG END AS SOLUONG "+ 
						"	FROM ERP_DONDATHANG_SP A  "+
						"	INNER JOIN ERP_SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ "+   
						"	INNER JOIN DONVIDOLUONG E ON B.DVDL_FK = E.PK_SEQ   "+
						"	INNER JOIN ERP_DONDATHANG C ON C.PK_SEQ = A.DONDATHANG_FK "+  
						"	LEFT JOIN QUYCACH D ON B.PK_SEQ = D.SANPHAM_FK  AND A.DVDL_FK = D.DVDL2_FK  AND D.DVDL1_FK=B.DVDL_FK "+
						"	WHERE A.DONDATHANG_FK ="+ddhid+" and  MakeToStock='0'    ";
		 
		   ResultSet  rskho=db.get(query);
		    while (rskho.next()){
		    	
		    	
			if(  rskho.getDouble("soluong")<=0 ){
				db.getConnection().rollback();
			System.out.println("Quy cách của sản phẩm "+rskho.getString("ten")+" chưa xác định,vui lòng thiết lập trước vì đây là đơn hàng không chọn  Sản xuất theo đơn đặt hàng  " );
				return false;
			}
			query = " select available from ERP_KHOTT_SANPHAM " +
			        " where khott_fk = '" + rskho.getString("KHOTT_FK")  + "' and sanpham_fk = '" + rskho.getString("sanpham_fk") + "'  ";
			 
			ResultSet rsCheck = db.get(query);
			double avai = 0;
			
			if(rsCheck != null)
			{
				if(rsCheck.next())
				{
					avai = rsCheck.getDouble("available");
				}
				rsCheck.close();
			}
	
			if(avai < rskho.getDouble("soluong"))
			{
				db.getConnection().rollback();
				 System.out.println("Không đủ tồn kho "+rskho.getString("sanpham_fk"));
				return false;
			}
	
			
			query=  " update erp_khott_sanpham set booked=booked+ "+rskho.getDouble("soluong")+" ,available=available- "+rskho.getDouble("soluong")+"  " +
					" where sanpham_fk ="+rskho.getString("sanpham_fk")+"  and khott_fk= "+rskho.getString("khott_fk");
			if(!db.update(query))
			{
				db.getConnection().rollback();
			 
				return false;
			}
		}
		    rskho.close();
		
		    query="update erp_dondathang set MakeToStock =1 where pk_seq="+ddhid;
		    if(!db.update(query))
			{
				db.getConnection().rollback();
				return false;
			}
		    
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		}catch(Exception er){
			db.update("rollback");
			return false;
			
		} 
	}

	private static String createChuyenKho_Nhan(String ckid, String kHONHAN_FK,
			String khoxuat_new, dbutils db) {

		try{
		
			String query=" select * from erp_chuyenkho_sanpham where sanpham_fk in " +
						 " (select sanpham_fk from kho_nl where khott_fk ="+khoxuat_new+" ) and  chuyenkho_fk ="+ckid;
			
			ResultSet rssp= db.get(query);
			
			boolean bien=false;
			String spid_str="0";
			while(rssp.next()){
				spid_str+=","+rssp.getString("sanpham_fk");
				bien=true;
			}
			rssp.close();
			
				if(bien){
					
				 query = " insert ERP_CHUYENKHO( noidungxuat_fk, ngaychuyen, ngaynhan, ngaychot, lydo, trangthai, khoxuat_fk, khonhan_fk, trangthaisp, ngaytao, nguoitao, ngaysua, nguoisua, NCC_CHUYEN_FK, NCC_NHAN_FK,YCKD_FK ,ISHANGDIDUONG) " +
						 " select noidungxuat_fk, ngaychuyen, ngaynhan, ngaychot, lydo, trangthai,khoxuat_fk, "+khoxuat_new+", trangthaisp, ngaytao, nguoitao, ngaysua, nguoisua, NCC_CHUYEN_FK, NCC_NHAN_FK,YCKD_FK ,ISHANGDIDUONG from erp_chuyenkho where pk_seq= "+ckid;
						if(!db.update(query))
						{
							return  "Không thể tạo phiếu chuyển kho từ kho tạm sang kho tổng: " + query;
						}
						String ckId_new = "";
						query = "select IDENT_CURRENT('ERP_CHUYENKHO') as ckId";
						ResultSet rsCk = db.get(query);
						if (rsCk.next())
						{
							ckId_new = rsCk.getString("ckId");
						} else {
							return "Không thể tạo phiếu chuyển kho từ kho tạm sang kho tổng: " + query;
						 
						}
						rsCk.close();
						
						// tạo bảng chi tiết
						query = " insert ERP_CHUYENKHO_SANPHAM(chuyenkho_fk, SANPHAM_FK, SOLUONGYEUCAU,SOLUONGXUAT,SOLUONGNHAN) " +
						"  select "+ckId_new+", SANPHAM_FK, SOLUONGYEUCAU,SOLUONGXUAT,SOLUONGNHAN from ERP_CHUYENKHO_SANPHAM where sanpham_fk in ("+spid_str+") and chuyenkho_fk= "+ckid;
				
						if(!db.update(query))
						{
							return  "Khong the tao moi ERP_CHUYENKHO_SANPHAM: " + query;
						}
						query=" INSERT INTO ERP_CHUYENKHO_SP_XUATHANG (CHUYENKHO_FK,SANPHAM_FK,SOLO,KHU,NGAYBATDAU,SOLUONG ) " +
						  " select  "+ckId_new+",SANPHAM_FK,SOLO,KHU,NGAYBATDAU,SOLUONG  from  ERP_CHUYENKHO_SP_XUATHANG where sanpham_fk in ("+spid_str+") and chuyenkho_fk= "+ckid;
						if(db.updateReturnInt(query)< 1)
						{
							return "Không thể cập nhật : " + query;
						}
						
						// 
						query=" INSERT INTO ERP_CHUYENKHO_SP_NHANHANG (CHUYENKHO_FK,CK_SP_XH_FK,SANPHAM_FK,SOLO,KHU,NGAYBATDAU,SOLUONG ) " +
						  " select "+ckId_new+",CK_SP_XH_FK,SANPHAM_FK,SOLO,KHU,NGAYBATDAU,SOLUONG from   ERP_CHUYENKHO_SP_NHANHANG where sanpham_fk in ("+spid_str+") and chuyenkho_fk= "+ckid;
		
						if(db.updateReturnInt(query) != 1)
						{
							return "Không thể cập nhật : " + query;
						}
				 
				}
			 
				
				
		}catch(Exception err){
			err.printStackTrace();
		}
		return "";
 
	
		
	}

	private static void CheckChuyenKho_TrongLSX() {
		// TODO Auto-generated method stub
		try{
			 dbutils db =new dbutils();
			 String query=  " SELECT PK_SEQ,khoxuat_fk ,trangthai " +
			 				" FROM ERP_CHUYENKHO WHERE KHOXUAT_FK IN (100000,100001,100002)   ";
			 
			 ResultSet rs=db.get(query);
			 while(rs.next()){
				 String Ckid=rs.getString("pk_seq");
				 String khoxuat_fk=rs.getString("khoxuat_fk");
				 String[] khoid_array=new String[]{"100000","100001","100002"};
				 for(int i=0;i<khoid_array.length;i++){
					 if(!khoid_array[i].equals(khoxuat_fk)){
						 createChuyenKhoThem(Ckid,khoxuat_fk,khoid_array[i],db);
					 }
				 }
				 
				 query="delete erp_chuyenkho_sanpham where chuyenkho_fk ="+Ckid+" and  sanpham_fk not in (select sanpham_fk from kho_nl where khott_fk ="+khoxuat_fk+")";
				 if(!db.update(query)){
					 System.out.println("Không thành cong : "+query);
				 }
			 	 query="delete erp_chuyenkho_sp_nhanhang where chuyenkho_fk ="+Ckid+" and  sanpham_fk not in (select sanpham_fk from kho_nl where khott_fk ="+khoxuat_fk+")";
				 if(!db.update(query)){
					 System.out.println("Không thành cong : "+query);
				 }
			     query="delete erp_chuyenkho_sp_xuathang where chuyenkho_fk ="+Ckid+" and  sanpham_fk not in (select sanpham_fk from kho_nl where khott_fk ="+khoxuat_fk+")";
				 if(!db.update(query)){
					 System.out.println("Không thành cong : "+query);
				 }
		 
			 }rs.close();
			 
			 
			 
		}catch(Exception er){
			er.printStackTrace();
		}
	}

	private static String createChuyenKhoThem(String ckid, String khoxuat_fk,
			String khoxuat_new, dbutils db) {
		try{
		
			String query=" select * from erp_chuyenkho_sanpham where sanpham_fk in " +
						 " (select sanpham_fk from kho_nl where khott_fk ="+khoxuat_new+" ) and  chuyenkho_fk ="+ckid;
			
			ResultSet rssp= db.get(query);
			
			boolean bien=false;
			String spid_str="0";
			while(rssp.next()){
				spid_str+=","+rssp.getString("sanpham_fk");
				bien=true;
			}
			rssp.close();
			
				if(bien){
					
				 query = " insert ERP_CHUYENKHO( noidungxuat_fk, ngaychuyen, ngaynhan, ngaychot, lydo, trangthai, khoxuat_fk, khonhan_fk, trangthaisp, ngaytao, nguoitao, ngaysua, nguoisua, NCC_CHUYEN_FK, NCC_NHAN_FK,YCKD_FK ,ISHANGDIDUONG) " +
						 " select noidungxuat_fk, ngaychuyen, ngaynhan, ngaychot, lydo, trangthai, "+khoxuat_new+", khonhan_fk, trangthaisp, ngaytao, nguoitao, ngaysua, nguoisua, NCC_CHUYEN_FK, NCC_NHAN_FK,YCKD_FK ,ISHANGDIDUONG from erp_chuyenkho where pk_seq= "+ckid;
						if(!db.update(query))
						{
							return  "Không thể tạo phiếu chuyển kho từ kho tạm sang kho tổng: " + query;
						}
						String ckId_new = "";
						query = "select IDENT_CURRENT('ERP_CHUYENKHO') as ckId";
						ResultSet rsCk = db.get(query);
						if (rsCk.next())
						{
							ckId_new = rsCk.getString("ckId");
						} else {
							return "Không thể tạo phiếu chuyển kho từ kho tạm sang kho tổng: " + query;
						 
						}
						rsCk.close();
						
						// tạo bảng chi tiết
						query = " insert ERP_CHUYENKHO_SANPHAM(chuyenkho_fk, SANPHAM_FK, SOLUONGYEUCAU,SOLUONGXUAT,SOLUONGNHAN) " +
						"  select "+ckId_new+", SANPHAM_FK, SOLUONGYEUCAU,SOLUONGXUAT,SOLUONGNHAN from ERP_CHUYENKHO_SANPHAM where sanpham_fk in ("+spid_str+") and chuyenkho_fk= "+ckid;
				
						if(!db.update(query))
						{
							return  "Khong the tao moi ERP_CHUYENKHO_SANPHAM: " + query;
						}
						query=" INSERT INTO ERP_CHUYENKHO_SP_XUATHANG (CHUYENKHO_FK,SANPHAM_FK,SOLO,KHU,NGAYBATDAU,SOLUONG ) " +
						  " select  "+ckId_new+",SANPHAM_FK,SOLO,KHU,NGAYBATDAU,SOLUONG  from  ERP_CHUYENKHO_SP_XUATHANG where sanpham_fk in ("+spid_str+") and chuyenkho_fk= "+ckid;
						if(db.updateReturnInt(query)< 1)
						{
							return "Không thể cập nhật : " + query;
						}
						
						// 
						query=" INSERT INTO ERP_CHUYENKHO_SP_NHANHANG (CHUYENKHO_FK,CK_SP_XH_FK,SANPHAM_FK,SOLO,KHU,NGAYBATDAU,SOLUONG ) " +
						  " select "+ckId_new+",CK_SP_XH_FK,SANPHAM_FK,SOLO,KHU,NGAYBATDAU,SOLUONG from   ERP_CHUYENKHO_SP_NHANHANG where sanpham_fk in ("+spid_str+") and chuyenkho_fk= "+ckid;
		
						if(db.updateReturnInt(query) != 1)
						{
							return "Không thể cập nhật : " + query;
						}
				 
				}
			 
				
				
		}catch(Exception err){
			err.printStackTrace();
		}
		return "";
 
	}

	private void CapNhatLenhanxuat_dakichhoat() {
		// TODO Auto-generated method stub
		try{
			dbutils db=new dbutils();
			Utility_Kho util_kho=new Utility_Kho();
			//lấy những lệnh đã yêu cầu nguyên liệu và ở trạng thái = 1
			String query=" SELECT PK_SEQ FROM ERP_LENHSANXUAT_GIAY LSX WHERE LSX.NGAYBATDAU LIKE '2015-01%' AND LSX.TRANGTHAI > 1 and  LSX.TRANGTHAI < 7 ";
			ResultSet rslsx=db.get(query);
			while (rslsx.next()){
				String lsxId=rslsx.getString("PK_SEQ");
				
				  query = 		  " SELECT LENHSANXUAT_FK,VATTU_FK ,KHOTT_FK ,CONTHIEU,AVAILABLE FROM ( "+  
								  " SELECT BOM.*, SP.TEN, BOM.SOLUONG  - ISNULL(BOM.BOOKED_LSX,0)   "+
								  " AS CONTHIEU,ISNULL(KHO.AVAILABLE,0) AS AVAILABLE    "+
								  " FROM ERP_LENHSANXUAT_BOM_GIAY BOM    "+
								  " INNER JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ=BOM.LENHSANXUAT_FK "+  
								  " LEFT JOIN ERP_KHOTT_SANPHAM KHO ON KHO.KHOTT_FK = BOM.KHOTT_FK     "+
								  " AND BOM.VATTU_FK = KHO.SANPHAM_FK INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = BOM.VATTU_FK "+  
								  " WHERE   BOM.LENHSANXUAT_FK =     "+lsxId+
								  " ) DATA WHERE DATA.CONTHIEU >0 ";
				  
				  ResultSet rs=db.get(query);
				  while (rs.next()){
					  double soluongthieu=rs.getDouble("conthieu") ;
					  double soluongkho=rs.getDouble("available");
					  double soluongbooked_new=0;
					  double soluongyeucauthem=0;
					  if(soluongthieu < soluongkho){
						  soluongbooked_new=soluongthieu;
					  }else{
						  soluongbooked_new =soluongkho;
						  soluongyeucauthem= soluongthieu-soluongkho;
						  
					  }
					  
					    String khott_fk=rs.getString("KHOTT_FK");
						String spId=rs.getString("VATTU_FK");
						double available=(-1)*soluongbooked_new;
						double booked=soluongbooked_new;
						double soluong=0;
						
						
						query=" Update ERP_KHOTT_SANPHAM set booked=isnull(booked,0)+"+booked+" , soluong =ISNULL(soluong,0) + " + soluong + ", " +
						" AVAILABLE = ISNULL(AVAILABLE,0) + " + available + " "+
						" where khott_fk =" + khott_fk + " and sanpham_fk = " +   spId + "";
						if(!db.update(query)){
							System.out.println("Không thành công :"+ query);
						}
						
						query=" update ERP_LENHSANXUAT_BOM_GIAY set BOOKED_LSX=isnull(BOOKED_LSX,0)+"+soluongbooked_new+" where   vattu_fk="+spId+" and lenhsanxuat_fk= "+ lsxId;
						if(!db.update(query)){
							System.out.println("Không thành công :"+ query);
						}
						if(soluongyeucauthem >0){
							query="insert into LSX_SANPHAM_THIEU(LSX_FK,SANPHAM_FK,KHOTT_FK,SOLUONGTHIEU) values("+lsxId+","+spId+","+khott_fk+","+soluongyeucauthem+")";
							if(!db.update(query)){
								System.out.println("Không thành công :"+ query);
								
							}
						}
						
				  }
				  rs.close();
				  

			}
			rslsx.close();
			db.shutDown();
			
		}catch(Exception er){
			er.printStackTrace();
		}
	}
	
	
	
	private void CapNhatLenhanxuat() {
		// TODO Auto-generated method stub
		try{
			dbutils db=new dbutils();
			Utility_Kho util_kho=new Utility_Kho();
			//lấy những lệnh đã yêu cầu nguyên liệu và ở trạng thái = 1
			String query=" SELECT PK_SEQ FROM ERP_LENHSANXUAT_GIAY LSX WHERE LSX.NGAYBATDAU LIKE '2015-01%' AND LSX.TRANGTHAI =1 ";
			ResultSet rslsx=db.get(query);
			while (rslsx.next()){
				String lsxId=rslsx.getString("PK_SEQ");
				
				  query = 		" SELECT LENHSANXUAT_FK,VATTU_FK ,KHOTT_FK ,CONTHIEU,AVAILABLE FROM ( "+
							  " SELECT BOM.*, SP.TEN, BOM.SOLUONG  - ISNULL(BOM.BOOKED_LSX,0)  - "+
							  " ISNULL((	 SELECT SUM(CKSP.SOLUONGYEUCAU) AS SOLUONGNHAN "+ 
							  " FROM ERP_YEUCAUCHUYENKHO CK INNER JOIN "+
							  " ERP_YEUCAUCHUYENKHO_SANPHAM CKSP ON CK.PK_SEQ=CKSP.YEUCAUCHUYENKHO_FK "+
							  " WHERE     CK.TRANGTHAI IN (0,1,2,3) AND CK.LENHSANXUAT_FK=LSX.PK_SEQ AND CKSP.SANPHAM_FK=BOM.VATTU_FK "+
							  " ) ,0) AS CONTHIEU,ISNULL(KHO.AVAILABLE,0) AS AVAILABLE "+   
							  " FROM ERP_LENHSANXUAT_BOM_GIAY BOM  "+
							  " INNER JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ=BOM.LENHSANXUAT_FK "+   
							  " LEFT JOIN ERP_KHOTT_SANPHAM KHO ON KHO.KHOTT_FK = BOM.KHOTT_FK   "+
							  " AND BOM.VATTU_FK = KHO.SANPHAM_FK INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = BOM.VATTU_FK "+  
							  " WHERE   BOM.LENHSANXUAT_FK =   "+lsxId+
							  " ) DATA WHERE DATA.CONTHIEU >0 ";
				  
				  ResultSet rs=db.get(query);
				  while (rs.next()){
					  double soluongthieu=rs.getDouble("conthieu") ;
					  double soluongkho=rs.getDouble("available");
					  double soluongbooked_new=0;
					  double soluongyeucauthem=0;
					  if(soluongthieu < soluongkho){
						  soluongbooked_new=soluongthieu;
					  }else{
						  soluongbooked_new =soluongkho;
						  soluongyeucauthem= soluongthieu-soluongkho;
						  
					  }
					  
					    String khott_fk=rs.getString("KHOTT_FK");
						String spId=rs.getString("VATTU_FK");
						double available=(-1)*soluongbooked_new;
						double booked=soluongbooked_new;
						double soluong=0;
						
						
						query=" Update ERP_KHOTT_SANPHAM set booked=isnull(booked,0)+"+booked+" , soluong =ISNULL(soluong,0) + " + soluong + ", " +
						" AVAILABLE = ISNULL(AVAILABLE,0) + " + available + " "+
						" where khott_fk =" + khott_fk + " and sanpham_fk = " +   spId + "";
						if(!db.update(query)){
							System.out.println("Không thành công :"+ query);
						}
						
						query=" update ERP_LENHSANXUAT_BOM_GIAY set BOOKED_LSX=isnull(BOOKED_LSX,0)+"+soluongbooked_new+" where   vattu_fk="+spId+" and lenhsanxuat_fk= "+ lsxId;
						if(!db.update(query)){
							System.out.println("Không thành công :"+ query);
						}
						if(soluongyeucauthem >0){
							query="insert into LSX_SANPHAM_THIEU(LSX_FK,SANPHAM_FK,KHOTT_FK,SOLUONGTHIEU) values("+lsxId+","+spId+","+khott_fk+","+soluongyeucauthem+")";
							if(!db.update(query)){
								System.out.println("Không thành công :"+ query);
								
							}
						}
						
				  }
				  rs.close();
				  

			}
			rslsx.close();
			db.shutDown();
			
		}catch(Exception er){
			er.printStackTrace();
		}
	}

	
	private String setQuery( IStockintransit obj) {
		
		String fromYear = obj.getYear();
		String fromMonth = obj.getMonth();
		
		String fromDate=fromYear+'-'+fromMonth;
		String query="";
	 
		 //dung file show de luu chuoi;
	 
		  if(obj.getkenhId().length() > 0)
				query += " and kbh.pk_seq='"+obj.getkenhId()+"'";
			if(obj.getnppId().length() >0)
				query += " and npp.pk_seq = '"+obj.getnppId()+"'";
			if(obj.getvungId().length() > 0)
				query += " and vung.pk_seq = '"+obj.getvungId()+"'";
			 
			if(obj.getkhuvucId().length() > 0)
				query += " and kv.pk_seq = '"+obj.getkhuvucId()+"'";
			
			
		String sql=" select    KENH,   VUNG,  KHUVUC,   MAKH,   TENKH ,data.CHITIEU,data.THUCDAT ,data.PHANTRAM, " +  
		   "  isnull((  " +  
		   "  SELECT   CASE WHEN TC_MUC.DONVI='0' THEN DATA.CHITIEU *TC_MUC.CHIETKHAU/100  ELSE TC_MUC.CHIETKHAU END   FROM TIEUCHITHUONGTL TC  " +  
		   "  INNER JOIN TIEUCHITHUONGTL_TIEUCHI TC_MUC ON TC.PK_SEQ=TC_MUC.THUONGTL_FK " +
		   "  INNER JOIN TIEUCHITHUONGTL_NPP TC_NPP ON TC.PK_SEQ=TC_NPP.THUONGTL_FK " +  
		   "  WHERE TC_NPP.NPP_FK=DATA.KHID and  TC.THANG="+obj.getMonth()+" AND TC.NAM="+obj.getYear()+" AND TC.KBH_FK=DATA.KBH_FK AND DATA.PHANTRAM >=TC_MUC.TUMUC AND DATA.PHANTRAM <=TC_MUC.DENMUC " +  
		   "  ),0) as THUONG " +  
		   "   " +  
		   "  from ( " +  
		   "   " +  
		   "  SELECT  KBH.PK_SEQ as kbh_FK,KBH.TEN AS KENH, VUNG.TEN AS VUNG,KV.TEN AS KHUVUC,KH.pk_seq as KHID , KH.MA AS MAKH, KH.TEN AS TENKH      " +  
		   "  ,ISNULL(DSNHOM.THANHTIEN,0) AS THUCDAT, ISNULL(CTNHOM.CHITIEU,0) AS CHITIEU,ISNULL(DSNHOM.THANHTIEN,0)*100/ISNULL(CTNHOM.CHITIEU,0)  as PHANTRAM " +  
		   "   " +  
		   "  FROM     " +  
		   "  (     " +  
		   "  SELECT   CT.KBH_FK ,CTNSP.KHACHHANG_FK  , SUM(CTNSP.CHITIET) AS CHITIEU     " +  
		   "  FROM ERP_CHITIEU_KHACHHANG_NHOMSP CTNSP INNER JOIN CHITIEUTHANG CT ON     " +  
		   "  CT.PK_SEQ=CTNSP.CHITIEUTHANG_FK       " +  
		   "  WHERE CT.THANG ="+obj.getMonth()+" AND CT.NAM="+obj.getYear()+" AND TRANGTHAI<>'2'     " +  
		   "  GROUP BY  CT.KBH_FK ,CTNSP.KHACHHANG_FK  " +  
		   "  ) AS CTNHOM     " +  
		   "  INNER JOIN ERP_KHACHHANG KH ON KH.PK_SEQ=CTNHOM.KHACHHANG_FK    " +  
		   "  INNER JOIN KHUVUC KV ON KV.PK_SEQ=KH.KHUVUC_FK     " +  
		   "  INNER JOIN VUNG  ON VUNG.PK_SEQ=KV.VUNG_FK     " +  
		   "  INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ=CTNHOM.KBH_FK     " +  
		   "  LEFT JOIN    " +  
		   "  (    " +  
		   "  SELECT  HD.KBH_FK ,HD.KHACHHANG_FK   ,SUM(HDSP.SOLUONG* HDSP.DONGIA)  AS THANHTIEN FROM ERP_HOADON HD      " +  
		   "  INNER JOIN ERP_HOADON_SP HDSP ON HD.PK_SEQ=HDSP.HOADON_FK    " +  
		   "  INNER JOIN NHOMSANPHAM_SANPHAM NSP_SP ON NSP_SP.SP_FK=HDSP.SANPHAM_FK    " +  
		   "  WHERE HD.NGAYXUATHD LIKE '"+fromDate+"%'    " +  
		   "  GROUP BY HD.KBH_FK ,HD.KHACHHANG_FK   " +  
		   "  ) AS DSNHOM ON DSNHOM.KHACHHANG_FK=CTNHOM.KHACHHANG_FK  " +
		   "  where 1=1 " +query +  
		   "  ) as data " ;  
		   
		 
		 System.out.println("1.Query DAILY CUS  : " + sql);
		return sql;
	}

			private void CreatePivotTable(OutputStream out,IStockintransit obj,String query) throws Exception
		    {   
				try{
					String chuoi=getServletContext().getInitParameter("path") + "\\ThuongThucDatKH.xlsm";
					FileInputStream fstream = new FileInputStream(chuoi);
					Workbook workbook = new Workbook();
					workbook.open(fstream);
					workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
					
					CreateStaticHeader(workbook,obj); 
					FillData(workbook,obj.getFieldShow(), query, obj); 
					workbook.save(out);
					fstream.close();
			     }catch(Exception ex){
			    	 ex.printStackTrace();
			    	 throw new Exception(ex.getMessage());
			     }	    
		   }
			private Hashtable< Integer, String > htbValueCell (){
				Hashtable<Integer, String> htb=new Hashtable<Integer, String>();
				htb.put(1,"DA");htb.put(2,"DB");htb.put(3,"DC");htb.put(4,"DD");htb.put(5,"DE");
				htb.put(6,"DF");htb.put(7,"DG");htb.put(8,"DH");htb.put(9,"DI");htb.put(10,"DJ");
				htb.put(11,"DK");htb.put(12,"DL");htb.put(13,"DM");htb.put(14,"DN");htb.put(15,"DO");
				htb.put(16,"DP");htb.put(17,"DQ");htb.put(18,"DR");htb.put(19,"DS");htb.put(20,"DT");
				htb.put(21,"DU");htb.put(22,"DV");htb.put(23,"DW");htb.put(24,"DX");htb.put(25,"DY");
				htb.put(26,"DZ");htb.put(27,"EA");htb.put(28,"EB");htb.put(29,"EC");htb.put(30,"ED");
				htb.put(31,"EE");htb.put(32,"EF");htb.put(33,"EG");htb.put(34,"EH");htb.put(35,"EI");
				htb.put(36,"EJ");htb.put(37,"EK");htb.put(38,"EL");htb.put(39,"EM");htb.put(40,"EN");
				htb.put(41,"EO");htb.put(42,"EP");htb.put(43,"EQ");htb.put(44,"ER");htb.put(45,"ES");
				htb.put(46,"ET");htb.put(47,"EU");htb.put(48,"EV");htb.put(49,"EW");htb.put(50,"EX");
				htb.put(51,"EY");htb.put(52,"EZ");htb.put(53,"FA");htb.put(54,"FB");htb.put(55,"FC");
				htb.put(56,"FD");htb.put(57,"FE");htb.put(58,"FF");htb.put(59,"FG");htb.put(60,"FH");
				htb.put(61,"FI");htb.put(62,"FJ");htb.put(63,"FK");htb.put(64,"FL");htb.put(65,"FM");
				return htb; 
			}
	private void CreateStaticHeader(Workbook workbook, IStockintransit obj) 
	{
		Hashtable<Integer, String> htb=this.htbValueCell();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		Cells cells = worksheet.getCells();

		Style style;		
		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
		cell.setValue("BÁO CÁO THƯỞNG KHÁCH HÀNG");

		style = cell.getStyle();

		Font font2 = new Font();
		font2.setColor(Color.RED);// mau chu
		font2.setSize(14);// size chu
		font2.setBold(true);
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu
		cell.setStyle(style);
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("A3");
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Tháng : " + obj.getMonth() + "" );
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("B3"); 
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Năm : " + obj.getYear() + "" );
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A4");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A5");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());
	    cell = cells.getCell(htb.get(1)+"1"); cell.setValue("KenhBanHang");
 
	    cell = cells.getCell(htb.get(2)+"1"); cell.setValue("ChiNhanh");
	    cell = cells.getCell(htb.get(3)+"1"); cell.setValue("KhuVuc");
	    cell = cells.getCell(htb.get(4)+"1"); cell.setValue("MaNhaPhanPhoi");	
	    cell = cells.getCell(htb.get(5)+"1");cell.setValue("NhaPhanPhoi");  	    
   
				 cell = cells.getCell(htb.get(6)+"1"); cell.setValue("TongChiTieu");
				 
				 cell = cells.getCell(htb.get(7)+"1"); cell.setValue("TongThucDat");
				  
				 cell = cells.getCell(htb.get(8)+"1"); cell.setValue("PhanTram");
				 cell = cells.getCell(htb.get(9)+"1"); cell.setValue("Thuong");
				 
				 
	   
	}

	private void FillData(Workbook workbook,String[] fieldShow, String query, IStockintransit obj)throws Exception 
	{
		
		Hashtable<Integer, String> htb=this.htbValueCell();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
		
	    cells.setColumnWidth(0, 10.0f);
		cells.setColumnWidth(1, 15.0f);
		cells.setColumnWidth(2, 15.0f);
		cells.setColumnWidth(3, 15.0f);
		cells.setColumnWidth(4, 15.0f);
		cells.setColumnWidth(5, 15.0f);
		cells.setColumnWidth(6, 15.0f);
		cells.setColumnWidth(7, 15.0f);
		cells.setColumnWidth(8, 15.0f);
		cells.setColumnWidth(9, 15.0f);
		cells.setColumnWidth(10, 15.0f);
		cells.setColumnWidth(11, 15.0f);
		cells.setColumnWidth(12, 15.0f);
		cells.setColumnWidth(13, 15.0f);
		cells.setColumnWidth(14, 15.0f);
		cells.setColumnWidth(15, 15.0f);
		cells.setColumnWidth(16, 15.0f);
		dbutils db = new dbutils();		
		ResultSet rs = db.get(query);	
		int indexRow = 2;
		try 
			{				
				Cell cell = null;
				 
				while(rs.next())
				{ 				
				    cell = cells.getCell(htb.get(1) + Integer.toString(indexRow)); cell.setValue(rs.getString("KENH"));
 
					cell = cells.getCell(htb.get(2) + Integer.toString(indexRow)); cell.setValue(rs.getString("VUNG"));
					cell = cells.getCell(htb.get(3) + Integer.toString(indexRow)); cell.setValue(rs.getString("KHUVUC"));					
					cell = cells.getCell(htb.get(4) + Integer.toString(indexRow));cell.setValue(rs.getString("MAKH"));				
					cell = cells.getCell(htb.get(5) + Integer.toString(indexRow));  cell.setValue(rs.getString("TENKH"));					
					
					 
					cell = cells.getCell(htb.get(6) + Integer.toString(indexRow));  cell.setValue(rs.getDouble("CHITIEU"));					
					 
					cell = cells.getCell(htb.get(7)+ Integer.toString(indexRow)); cell.setValue(rs.getDouble("THUCDAT"));
					 
					double SumphantramMTD =0;
					if(rs.getDouble("CHITIEU") >0){
						SumphantramMTD=rs.getDouble("THUCDAT")*100/rs.getDouble("CHITIEU");
					}
					cell = cells.getCell(htb.get(8)+ Integer.toString(indexRow)); cell.setValue(SumphantramMTD);
					cell = cells.getCell(htb.get(9)+ Integer.toString(indexRow)); cell.setValue(rs.getDouble("THUONG"));
					 
					indexRow++;
				}
				if(rs != null) rs.close();
				if(db!=null){
					db.shutDown();
				}
				
				if(indexRow==2){
					throw new Exception("Không có dữ liệu lấy báo cáo,vui lòng chọn lại thời điểm lấy báo cáo");
				}
				
			}catch(java.sql.SQLException err){
				err.printStackTrace();
				System.out.println(err.toString());
				throw new Exception("Khong the tao duoc bao cao trong thoi gian nay. Error :"+err.toString());
			}
		
		
	}
	
	private String GetExcelColumnName(int columnNumber)
	 {
	     int dividend = columnNumber;
	     String columnName = "";
	     int modulo;

	     while (dividend > 0)
	     {
	         modulo = (dividend - 1) % 26;
	         columnName = (char)(65 + modulo) + columnName;
	         dividend = (int)((dividend - modulo) / 26);
	     } 

	     return columnName;
	 }
	
}
