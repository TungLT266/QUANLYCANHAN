package geso.traphaco.erp.servlets.lenhsanxuat;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.util.Utility_Kho;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.lenhsanxuat.IErpChuyenkhoSX;
import geso.traphaco.erp.beans.lenhsanxuat.IErpChuyenkhoSXList;
import geso.traphaco.erp.beans.lenhsanxuat.imp.ErpChuyenkhoSX;
import geso.traphaco.erp.beans.lenhsanxuat.imp.ErpChuyenkhoSXList;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpChuyenkhoSXSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpChuyenkhoSXSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpChuyenkhoSXList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
		String  task= util.antiSQLInspection(request.getParameter("task"));
		
		if(task==null){
			task="chuyenNL";
		}
		
		String  isnhanHang= util.antiSQLInspection(request.getParameter("isnhanHang"));
		
		if(isnhanHang==null){
			isnhanHang="";
		}
		 
	    String action = util.getAction(querystring);
	    
	    String lsxId = util.getId(querystring);
	    obj = new ErpChuyenkhoSXList();
	    obj.settask(task);
	    obj.setIsnhanHang(isnhanHang);
	    
	    System.out.println("action chuyen kho post: " + action);
	     
	    if (action.equals("delete"))
	    {	
	    	String msg = this.DeleteChuyenKho(lsxId);
			obj.setMsg(msg);
	    }
	    else
	    {
	    	if(action.equals("chot"))
	    	{	
	    		String msg=this.ChotPhieuChuyenKho(lsxId);
	    		obj.setMsg(msg);
	    	}
	    	else
	    	{
	    		if(action.equals("hoantat"))
	    		{
	    			String msg = this.HoanTatChuyenKho(lsxId);
	    			obj.setMsg(msg);
	    			System.out.println("___thong bao loi: " + msg);
	    		}
	    	}
	    }
	     
	    obj.setIsnhanHang(isnhanHang); 
	    obj.setUserId(userId);
	    obj.init("");
	   
		session.setAttribute("obj", obj);
			
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKhoSanXuat.jsp";
		if(isnhanHang.equals("1"))
		{
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKho_NhanHangList.jsp";
		}
		else
		{
			if(isnhanHang.equals("2"))
			{
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKho_XuatHangList.jsp";
			}
		}
		
		response.sendRedirect(nextJSP);
	}

	private String ChotPhieuChuyenKho(String id) {
		// TODO Auto-generated method stub
		dbutils db = new dbutils();
		try{
			System.out.println("chot phieu chuyen kho");
			db.getConnection().setAutoCommit(false);
 
			Utility_Kho util_kho =new Utility_Kho(); 
			  // cập nhật phiên bản mới,nếu là hàng đi đường thì giảm kho xuất ngay khi chốt.hoàn tất phiếu sẽ phải kiểm tra nếu ishangdiduong=1 thì ko có giảm kho tiếp
			
			//giảm kho xuất
		 	String query=	" SELECT  CK.NGAYCHUYEN ,CK.NCC_CHUYEN_FK,CK.KHOXUAT_FK ,CKXH.CHUYENKHO_FK,CKXH.SANPHAM_FK,CKXH.SOLO,CKXH.VITRI,CKXH.KHU,CKXH.NGAYBATDAU,CKXH.SOLUONG, "+
			 		" ISNULL(CK.TRANGTHAISP,'0') AS TRANGTHAISP    ,ISNULL(CKSP.DONGIA,0) AS DONGIAKHO,ISNULL(CKXH.DONGIA,0) AS DONGIALO "+
			 		" FROM ERP_CHUYENKHO CK   "+
			 		" INNER JOIN ERP_CHUYENKHO_SANPHAM CKSP ON CKSP.CHUYENKHO_FK=CK.PK_SEQ "+
			 		" INNER JOIN   ERP_CHUYENKHO_SP_XUATHANG  CKXH ON CK.PK_SEQ=CKSP.CHUYENKHO_FK  AND CKSP.SANPHAM_FK=CKXH.sanpham_fk  AND CKSP.CHUYENKHO_FK=CKXH.chuyenkho_fk"+
			 		" WHERE CK.trangthai='0' and CK.PK_SEQ="+id;
			 	
			 		//System.out.println(" kho ne :"+ query);
					ResultSet rs=db.get(query);
					while(rs.next()){
						// giam booked_tang_avai
						// cập nhật kho tổng
						String NGAYCHUYEN=rs.getString("NGAYCHUYEN");
						double soluongct=rs.getDouble("SOLUONG");
						String sanpham_fk=rs.getString("sanpham_fk");
						String solo=rs.getString("SOLO");
						String KHU=rs.getString("KHU");
						String khoXuatId=rs.getString("KHOXUAT_FK");
						String trangthaisp=rs.getString("TRANGTHAISP");
						if(KHU==null){
							KHU="";
						}
						String NGAYBATDAU=rs.getString("NGAYBATDAU");
						String VITRI=rs.getString("VITRI");
						double dongiachuyenkho=rs.getDouble("DONGIAKHO"); 
						double dongiatheolo=rs.getDouble("DONGIALO");
						// giảm số lượng ,giảm bookd,avai giữ nguyên
						
						String msg1= util_kho.Update_Kho_Sp_Check_TonKhoNgay( db,khoXuatId,sanpham_fk, (-1)*soluongct,(-1)*soluongct,0,dongiachuyenkho,NGAYCHUYEN);
						if(msg1.length() >0){
							db.getConnection().rollback();
							return "CPCK1.1 " + msg1;
						}
						
						msg1=util_kho.Update_Kho_Sp_Chitiet(db,khoXuatId,sanpham_fk, (-1)*soluongct,(-1)*soluongct,0,dongiatheolo,solo,VITRI,KHU,NGAYBATDAU);
						if(msg1.length() >0){
							db.getConnection().rollback();
							return "CPCK1.2 " + msg1;
						}
						
						if(util_kho.IsKhoQuanLyTrangThai( khoXuatId, db)){
							//neu la kho quan ly trang thai thi them kho trang thai
							msg1=util_kho.Update_Kho_Sp_Chitiet_TrangThai(db,khoXuatId,sanpham_fk,(-1)*soluongct,(-1)*soluongct,0 ,dongiatheolo,solo,VITRI,KHU,NGAYBATDAU, trangthaisp);
							if(msg1.length() >0){
								db.getConnection().rollback();
								return "CPCK1.3 " + msg1;
							}
							
						}
 
						if(rs.getString("NCC_CHUYEN_FK")!=null){
							
							msg1=util_kho.Update_Kho_Sp_Chitiet_NCC(db,khoXuatId,sanpham_fk, (-1)*soluongct,(-1)*soluongct,0,dongiatheolo,solo,VITRI,KHU,NGAYBATDAU,rs.getString("NCC_CHUYEN_FK"));
							if(msg1.length() >0){
								db.getConnection().rollback();
								return "CPCK1.4 " + msg1;
							}
				
						}
						
					}
			
		
			query=" SELECT PK_SEQ FROM ERP_CHUYENKHO WHERE khonhan_fk is null and PK_SEQ="+id;
			rs=db.get(query);
			if(rs.next()){
				// trường hợp không có kho nhận thì chốt coi như hoàn tất phiếu luôn
				query=" UPDATE ERP_CHUYENKHO SET TRANGTHAI='3' WHERE PK_SEQ= "+id;
				if(!db.update(query)){
					db.getConnection().rollback();
					return "CPCK1.5 " + "Lỗi dòng lệnh :"+query;
				}
			}
				
				query=  " SELECT SANPHAM_FK,SOLUONGYEUCAU, "+  
						" (   "+
						" SELECT SUM(SOLUONGXUAT) FROM ERP_CHUYENKHO CK   "+
						" INNER JOIN  ERP_CHUYENKHO_SANPHAM A ON A.CHUYENKHO_FK=CK.PK_SEQ "+
						" AND CK.YEUCAUCHUYENKHO_FK=YCCK.PK_SEQ  "+
						" AND CK.TRANGTHAI <>4  and A.SANPHAM_FK=CK_SP.SANPHAM_FK "+
						" ) AS A  "+
						" FROM ERP_YEUCAUCHUYENKHO YCCK  "+
						" INNER JOIN ERP_YEUCAUCHUYENKHO_SANPHAM CK_SP "+  
						" ON YCCK.PK_SEQ=CK_SP.YEUCAUCHUYENKHO_FK "+ 
						" WHERE YCCK.PK_SEQ= (  "+
						" SELECT YEUCAUCHUYENKHO_FK FROM ERP_CHUYENKHO WHERE PK_SEQ= "+id+"  ) "+ 
						" AND SOLUONGYEUCAU > (   "+
						" SELECT SUM(SOLUONGXUAT) FROM ERP_CHUYENKHO CK   "+
						" INNER JOIN  ERP_CHUYENKHO_SANPHAM A ON A.CHUYENKHO_FK=CK.PK_SEQ   "+
						" WHERE CK.YEUCAUCHUYENKHO_FK=YCCK.PK_SEQ  AND A.SANPHAM_FK=CK_SP.SANPHAM_FK "+
						" AND CK.TRANGTHAI <> 4  )  ";
				
				ResultSet rscheck=db.get(query);
				if(rscheck.next()){
					
				}else{
					//hoàn tất phiếu yêu cầu 
					query="Update ERP_YEUCAUCHUYENKHO set trangthai=3  where pk_seq = (select yeucauchuyenkho_fk from erp_chuyenkho where pk_Seq="+id+") ";
					if(!db.update(query))
					{
						db.getConnection().rollback();
						rscheck.close();
						return "CPCK1.6 " + "Không thể cập nhật erp_yeucauchuyenkho " + query;
					}
				}
			 
			rs.close();
			query= " update ERP_CHUYENKHO set trangthai = '1' , ISHANGDIDUONG ='1' where pk_seq = '" + id + "'" ;
			if(!db.update(query)){
				db.getConnection().rollback();
				return "CPCK1.7 " + "Lỗi dòng lệnh :"+query;
			}
			
			 db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
	 
	    	
		}catch(Exception er){
			db.update("rollback");
			er.printStackTrace();
		
			return "CPCK1.8 " + er.getMessage();
		}
		finally
		{
			db.shutDown();
		}
		return "";
	}

	private String DeleteChuyenKho(String id)
	{
		dbutils db = new dbutils();
		Utility_Kho util_kho= new Utility_Kho();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			String	query=" SELECT CK.NCC_CHUYEN_FK,CK.KHOXUAT_FK ,CHUYENKHO_FK,SANPHAM_FK,SOLO,VITRI,KHU,NGAYBATDAU,SOLUONG, " +
						  " ISNULL(CK.TRANGTHAISP,'0') AS TRANGTHAISP " +
						  " FROM ERP_CHUYENKHO CK "+ 
						  " INNER JOIN   ERP_CHUYENKHO_SP_XUATHANG  CKSP ON CK.PK_SEQ=CKSP.CHUYENKHO_FK "+
						  " WHERE CK.PK_SEQ="+id;
			ResultSet rs=db.get(query);
			
			while(rs.next()){
				// giam booked_tang_avai
				// cập nhật kho tổng
				String khoXuatId=rs.getString("KHOXUAT_FK");
				double soluongct=rs.getDouble("SOLUONG");
				String sanpham_fk=rs.getString("sanpham_fk");
				String solo=rs.getString("SOLO");
				String KHU=rs.getString("KHU");
				String NGAYBATDAU=rs.getString("NGAYBATDAU");
				String VITRI=rs.getString("VITRI");
				
				String trangthaisp=rs.getString("TRANGTHAISP");
				
				String msg1= util_kho.Update_Kho_Sp ( db,khoXuatId,sanpham_fk,0, (-1)*soluongct,soluongct,0);
				if(msg1.length() >0){
					db.getConnection().rollback();
					return msg1;
				}
				
				msg1=util_kho.Update_Kho_Sp_Chitiet( db,khoXuatId,sanpham_fk,0, (-1)*soluongct,soluongct,0,solo,VITRI,KHU,NGAYBATDAU);
				if(msg1.length() >0){
					db.getConnection().rollback();
					return msg1;
				}
				
				if(util_kho.IsKhoQuanLyTrangThai( khoXuatId, db)){
					//neu la kho quan ly trang thai thi them kho trang thai
					msg1=util_kho.Update_Kho_Sp_Chitiet_TrangThai( db,khoXuatId,sanpham_fk,0, (-1)*soluongct, soluongct,0,solo,VITRI,KHU,NGAYBATDAU, trangthaisp);
					if(msg1.length() >0){
					 
						db.getConnection().rollback();
						return msg1;
					}
					
				}
				 
				if(rs.getString("NCC_CHUYEN_FK")!=null){
					msg1=util_kho.Update_Kho_Sp_Chitiet_NCC( db,khoXuatId,sanpham_fk,0, (-1)*soluongct,soluongct,0,solo,VITRI,KHU,NGAYBATDAU,rs.getString("NCC_CHUYEN_FK"));
					if(msg1.length() >0){
						db.getConnection().rollback();
						return msg1;
					}
 
				}
		
	}
			
			query = "update ERP_CHUYENKHO set trangthai = '4' where pk_seq = '" + id + "'";
			if(!db.update(query))
			{
				msg = "3.Không thể cập nhật ERP_KHOTT_SP_CHITIET_TRANGTHAI: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query=" select ck.yeucauchuyenkho_fk from ERP_CHUYENKHO ck  where ck.yeucauchuyenkho_fk =(select yeucauchuyenkho_fk from ERP_CHUYENKHO ck where PK_SEQ ="+id+" ) and ck.trangthai <>4 and yeucauchuyenkho_fk is not null";
			 rs=db.get(query);
			 
			if(!rs.next()){
				query=" update ERP_YEUCAUCHUYENKHO set TRANGTHAI='1' where PK_SEQ =(select yeucauchuyenkho_fk from ERP_CHUYENKHO ck where PK_SEQ ="+id+" )";
				if(!db.update(query))
				{
					msg = "3.Không thể cập nhật ERP_KHOTT_SP_CHITIET_TRANGTHAI: " + query;
					db.getConnection().rollback();
					return msg;
				}
			}
			rs.close();
			
			db.getConnection().commit();
			db.shutDown();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		
		return "";
		
	}

	private String HoanTatChuyenKho(String id)
	{
		dbutils db = new dbutils();
		  Utility util=new Utility();
		  Utility_Kho util_kho =new Utility_Kho(); 
		try 
		{
			db.getConnection().setAutoCommit(false);
			String ISHANGDIDUONG="";
			//Lay noi dung xuat
			String query = "select noidungxuat_fk, ngaychot,isnull(ISHANGDIDUONG,'0') as ISHANGDIDUONG  from ERP_CHUYENKHO where pk_seq = '" + id + "' and trangthai <> '3' ";
			String noidungxuat_fk = "";
			String ngaychot = "";
			ResultSet rsChuyenKho = db.get(query);
			if(rsChuyenKho.next())
			{
				noidungxuat_fk = rsChuyenKho.getString("noidungxuat_fk");
				ngaychot = rsChuyenKho.getString("ngaychot");
				ISHANGDIDUONG=rsChuyenKho.getString("ISHANGDIDUONG");
			}else{
				db.update("rollback");
				return "HTCK1.1 Không xác định được nội dung xuất của hệ thống ";
			}
			rsChuyenKho.close();
			
			if(!ISHANGDIDUONG.equals("1")){
				// nếu không phải hàng đi đường thì vẫn giảm booked,giảm số lượng cho kho xuất 
				//giảm kho xuất
			 	query=	" SELECT CK.NGAYCHUYEN , CK.NCC_CHUYEN_FK,CK.KHOXUAT_FK ,CKXH.CHUYENKHO_FK,CKXH.SANPHAM_FK,CKXH.SOLO,CKXH.VITRI,CKXH.KHU,CKXH.NGAYBATDAU,CKXH.SOLUONG, "+
				 		" ISNULL(CK.TRANGTHAISP,'0') AS TRANGTHAISP    ,ISNULL(CKSP.DONGIA,0) AS DONGIAKHO,ISNULL(CKXH.DONGIA,0) AS DONGIALO "+
				 		" FROM ERP_CHUYENKHO CK   "+
				 		" INNER JOIN ERP_CHUYENKHO_SANPHAM CKSP ON CKSP.CHUYENKHO_FK=CK.PK_SEQ "+
				 		" INNER JOIN   ERP_CHUYENKHO_SP_XUATHANG  CKXH ON CK.PK_SEQ=CKSP.CHUYENKHO_FK  AND CKSP.SANPHAM_FK=CKXH.sanpham_fk  AND CKSP.CHUYENKHO_FK=CKXH.chuyenkho_fk"+
				 		" WHERE isnull(CK.ISHANGDIDUONG,'0') !='1' and  CK.PK_SEQ="+id;
						ResultSet rs=db.get(query);
						while(rs.next()){
							// giam booked_tang_avai
							// cập nhật kho tổng
							double soluongct=rs.getDouble("SOLUONG");
							String sanpham_fk=rs.getString("sanpham_fk");
							String solo=rs.getString("SOLO");
							String KHU=rs.getString("KHU");
							String khoXuatId=rs.getString("KHOXUAT_FK");
							String trangthaisp=rs.getString("TRANGTHAISP");
							String NGAYCHUYEN=rs.getString("NGAYCHUYEN");
							if(KHU==null){
								KHU="";
							}
							String NGAYBATDAU=rs.getString("NGAYBATDAU");
							String VITRI=rs.getString("VITRI");
							double dongiachuyenkho=rs.getDouble("DONGIAKHO"); 
							double dongiatheolo=rs.getDouble("DONGIALO");
							// giảm số lượng ,giảm bookd,avai giữ nguyên
							
							String msg1= util_kho.Update_Kho_Sp_Check_TonKhoNgay( db,khoXuatId,sanpham_fk, (-1)*soluongct,(-1)*soluongct,0,dongiachuyenkho,NGAYCHUYEN);
							if(msg1.length() >0){
								db.getConnection().rollback();
								return msg1;
							}
							
							msg1=util_kho.Update_Kho_Sp_Chitiet(db,khoXuatId,sanpham_fk, (-1)*soluongct,(-1)*soluongct,0,dongiatheolo,solo,VITRI,KHU,NGAYBATDAU);
							if(msg1.length() >0){
								db.getConnection().rollback();
								return "HTCK1.2 " + msg1;
							}
							
							if(util_kho.IsKhoQuanLyTrangThai( khoXuatId, db)){
								//neu la kho quan ly trang thai thi them kho trang thai
								msg1=util_kho.Update_Kho_Sp_Chitiet_TrangThai(db,khoXuatId,sanpham_fk,(-1)*soluongct,(-1)*soluongct,0 ,dongiatheolo,solo,VITRI,KHU,NGAYBATDAU, trangthaisp);
								if(msg1.length() >0){
									db.getConnection().rollback();
									return "HTCK1.3 " + msg1;
								}
								
							}
	 
							if(rs.getString("NCC_CHUYEN_FK")!=null){
								
								msg1=util_kho.Update_Kho_Sp_Chitiet_NCC(db,khoXuatId,sanpham_fk, (-1)*soluongct,(-1)*soluongct,0,dongiatheolo,solo,VITRI,KHU,NGAYBATDAU,rs.getString("NCC_CHUYEN_FK"));
								if(msg1.length() >0){
									db.getConnection().rollback();
									return "HTCK1.4 " + msg1;
								}
					
							}
							
						}
					
				
			}
			
			ResultSet rs;
			if(!noidungxuat_fk.equals("100030")){
				//tăng kho nhận ,cập nhật giá kho
				query=	" SELECT SP.DVDL_FK, qc.SOLUONG1,qc.SOLUONG2 , CKNH.PK_SEQ AS IDNHANHANG ,SP.MA AS MASP, CK.KHONHAN_FK  , \n" +
						" CK.KHOXUAT_FK , CKNH.PK_SEQ,CKNH.SANPHAM_FK,CKNH.SOLO, CKNH.KHU,CKXH.KHU AS KHUKHOXUAT,CKXH.NGAYBATDAU AS NGAYBATDAU_KHOXUAT \n"+
						" ,CKNH.SOLUONG AS SOLUONGNHAN  ,ISNULL(CK.TRANGTHAISP,'0') AS TRANGTHAISP \n"+  
						" FROM ERP_CHUYENKHO_SP_NHANHANG CKNH   \n"+
						" INNER JOIN ERP_CHUYENKHO_SP_XUATHANG CKXH ON CKXH.PK_SEQ=CKNH.CK_SP_XH_FK \n"+  
						" INNER JOIN ERP_CHUYENKHO CK ON CK.PK_SEQ=CKNH.CHUYENKHO_FK   \n"+
						" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = CKNH.SANPHAM_FK    \n"+
						" left join QUYCACH qc on qc.SANPHAM_FK=SP.PK_SEQ  and qc.DVDL2_FK=100003 \n"+
						" WHERE CKNH.CHUYENKHO_FK="+id;
				System.out.println("cau lenh tang kho nhan,cap nhat gia kho\n" + query + "\n========================================");
				 
				rs=db.get(query);
				
				while (rs.next()){
					
					String IDNHANHANG=rs.getString("IDNHANHANG");
					double soluongct=rs.getDouble("SOLUONGNHAN");
					String sanpham_fk=rs.getString("sanpham_fk");
					String solo=rs.getString("SOLO");
					String KHU=rs.getString("KHU");
					String khukhoxuat=rs.getString("KHUKHOXUAT");
					if(khukhoxuat==null){
						khukhoxuat="";
					}
					String khoXuatId=rs.getString("KHOXUAT_FK");
					String KHONHAN_FK=rs.getString("KHONHAN_FK");
					String trangthaisp=rs.getString("TRANGTHAISP");
					if(KHU==null){
						KHU="";
					}
					String NGAYBATDAU=rs.getString("NGAYBATDAU_KHOXUAT");
					
					double dongiaton=0;
					query="SELECT isnull(GIATON,0) as GIATON  FROM ERP_KHOTT_SANPHAM WHERE KHOTT_FK= "+ khoXuatId +" AND SANPHAM_FK="+sanpham_fk ;
					ResultSet rskho=db.get(query);
					if(rskho.next()){
						dongiaton=rskho.getDouble("GIATON");
					}else{
						db.getConnection().rollback();
						return "Không xác định được giá tồn kho của sản phẩm  :"+rs.getString("SPMA");
					}
					rskho.close();
					
					query=" UPDATE ERP_CHUYENKHO_SANPHAM SET DONGIA="+dongiaton+" WHERE CHUYENKHO_FK="+id+" AND SANPHAM_FK= "+sanpham_fk;
						
					 if(!db.update(query)){
						 	db.getConnection().rollback();
							return "HTCK1.5 Không thể cập nhật :"+query;
					 }
					 
					
					// tính được giá trị cấp đông,chi phí lưu kho
					query=" SELECT NGAYBATDAU,NGAYSANXUAT,NGAYHETHAN,ISNULL(CPCAPDONG,0) AS CPCAPDONG,"+ 
						  " ISNULL(CPLUUKHO,0) AS CPLUUKHO  , "+
						  " ISNULL(CPNHANHANG,0) AS CPNHANHANG , "+
						  " ISNULL(THUENHAPKHAU,0) AS THUENHAPKHAU, "+ 
						  " ISNULL(DONGIAMUA,0) AS DONGIAMUA "+
					 	  " FROM ERP_KHOTT_SP_CHITIET WHERE KHOTT_FK="+khoXuatId+" AND SANPHAM_FK="+sanpham_fk+" AND SOLO='"+solo+"'  ";
					if(util_kho.getIsQuanLyKhuVuc(khoXuatId, db).equals("1")){
						query=query + " AND  KHUVUCKHO_FK ="+ khukhoxuat;
					}
					  rskho=db.get(query);
					 double CphiCapDong=0;
					 double CphiLuuKho=0;
					 double CphiNhapHang=0;
					 double ThueNhapkhau=0;
					 double dongiamua =0;
					 String ngaysanxuat="";
					 String ngayhethan="";
 
					if(rskho.next()){
						
						   CphiCapDong=rskho.getDouble("CPCAPDONG");
						   CphiLuuKho=rskho.getDouble("CPLUUKHO");
						   CphiNhapHang=rskho.getDouble("CPNHANHANG");
						   ThueNhapkhau=rskho.getDouble("THUENHAPKHAU");
						   dongiamua =rskho.getDouble("DONGIAMUA");
						   ngaysanxuat=rskho.getString("NGAYSANXUAT");
						   ngayhethan=rskho.getString("NGAYHETHAN");
						
					}else{
						db.getConnection().rollback();
						return "HTCK1.6 Lỗi không lấy được lô "+solo+" kho xuất để tính giá trị xuất,vui lòng kiểm tra lại";
					}
					double chiphicapdongthem=util_kho.getChiPhiCapdong(khoXuatId,db);
					// phải chia lại đơn vị nếu đơn vị chuẩn khác KG,vì đơn vị đang tính cấp đông và lưu kho là giá/KG
					/*if(chiphicapdongthem >0){
						if(!rs.getString("dvdl_fk").equals("100003")){
							try{
								if(rs.getDouble("soluong2") >0 && rs.getDouble("soluong1")  > 0){
									chiphicapdongthem=chiphicapdongthem * rs.getDouble("soluong2")/ rs.getDouble("soluong1");	
								}else{
									db.getConnection().rollback();
									return "1.Lỗi:Cần phải xác định quy đổi ra Kg để tính chi phí cấp đông (giá cấp đông đang là 1200/Kg)";
								}
							}catch(Exception err){
								db.getConnection().rollback();
								return "1.Lỗi:Cần phải xác định quy đổi ra Kg để tính chi phí cấp đông (giá cấp đông đang là 1200/Kg)";
							}
							
						}
					}*/
					
					if(chiphicapdongthem<0){
						db.getConnection().rollback();
						return "HTCK1.7 Không xác định được giá chi phí cấp đông thêm";
					}
					CphiCapDong=CphiCapDong+ chiphicapdongthem;
					
					//ngày chốt là ngày ra kho
					double chiphiLuuKhoThem=util_kho.getChiPhiLuukho(khoXuatId,NGAYBATDAU,ngaychot,db);
					
					/*if(chiphiLuuKhoThem >0){
						if(!rs.getString("dvdl_fk").equals("100003")){
							try{
								if(rs.getDouble("soluong2") >0 && rs.getDouble("soluong1")  > 0){
									chiphiLuuKhoThem=chiphiLuuKhoThem * rs.getDouble("soluong2")/ rs.getDouble("soluong1");	
								}else{
									db.getConnection().rollback();
									return "1.Lỗi:Cần phải xác định quy đổi ra Kg để tính chi phí lưu kho (giá lưu kho : 630/Kg/Ngày)";
								}
							}catch(Exception err){
								db.getConnection().rollback();
								return "1.Lỗi:Cần phải xác định quy đổi ra Kg để tính chi phí lưu kho (giá lưu kho : 630/Kg/Ngày)";
							}
							
						}
					}*/
					
					// tạm bỏ ra,vì giờ ngày chuyển của người dùng không đúng, có thể ngày chueyen nhỏ hơn ngày 
					/*if(chiphiLuuKhoThem<0){
						db.getConnection().rollback();
						return "Không xác định được giá chi phí lưu kho";
					}*/
					if(chiphiLuuKhoThem<0){
						chiphiLuuKhoThem=0;
					}
					CphiLuuKho=CphiLuuKho+chiphiLuuKhoThem;
					
					// cập nhật vào kho nhận
					
					 String msg1= util_kho.Update_Kho_Sp( db, KHONHAN_FK,sanpham_fk,soluongct,0,soluongct,dongiaton);
					 if(msg1.length() >0){
						 db.getConnection().rollback();
							return "HTCK1.8 " + msg1;
					 }
				 
					 System.out.println("khu vuc kho: " + KHU);
					 msg1= util_kho.Update_Kho_Sp_Chitiet( db,KHONHAN_FK,sanpham_fk,soluongct,0,soluongct,dongiamua,solo,"",KHU,ngaychot,ngaychot,ngaysanxuat,ngayhethan,CphiCapDong,CphiLuuKho,CphiNhapHang,ThueNhapkhau);
					 if(msg1.length() >0){
						 db.getConnection().rollback();
						 System.out.println("HTCK1.9 " + msg1);
							return "HTCK1.9 " + msg1;
					 }
					 
					 // số lượng đạt
					 //trangthai=0 là hàng đạt
					 if(util_kho.IsKhoQuanLyTrangThai(KHONHAN_FK, db)) {
						 msg1= util_kho.Update_Kho_Sp_Chitiet_TrangThai(db,KHONHAN_FK,sanpham_fk,soluongct,0,soluongct,dongiamua,solo,"",KHU,ngaychot,ngaychot,ngaysanxuat,ngayhethan,CphiCapDong,CphiLuuKho,CphiNhapHang,ThueNhapkhau,trangthaisp);
					 	if(msg1.length() >0){
					 		db.getConnection().rollback();
							return "HTCK1.10 " + msg1;
					 	}
					 }
					 // cập nhật số chi phí,đơn giá theo lô vào bảng erp_chuyenkho_sp_nhanhang
					 query=" update ERP_CHUYENKHO_SP_NHANHANG set DONGIA="+dongiamua+",CPCAPDONG="+CphiCapDong+",CPLUUKHO="+CphiLuuKho+" ,THUENHAPKHAU="+ThueNhapkhau+",CPNHANHANG="+CphiNhapHang+" where PK_SEQ="+IDNHANHANG;
					if(!db.update(query)){
						db.getConnection().rollback();
						return "HTCK1.11 Không thể cập nhật đơn giá,vui lòng kiểm tra lại"+query;
					}
					 
					// trường hợp kho gia công 
				 
					
				}
				rs.close();
			} 
					
			
			//CAP NHAT KE TOAN
			if( noidungxuat_fk.equals("100015") || noidungxuat_fk.equals("100009") || noidungxuat_fk.equals("100021") ||  noidungxuat_fk.equals("100022") ||  noidungxuat_fk.equals("100030"))
			{
				String nam = ngaychot.substring(0, 4);
				String thang = ngaychot.substring(5, 7);
				
				//KE TOAN TU DONG PHAT SINH
				 
				int namOLD = Integer.parseInt(nam);
				int thangOLD = Integer.parseInt(thang);
				
				if(thangOLD == 1)
				{
					thangOLD = 12;
					namOLD = namOLD - 1;
				}
				else
				{
					thangOLD = thangOLD - 1;
				}
				
				String taikhoanktCo_SoHieu = "";
				String taikhoanktNo_SoHieu = "";
				
				
				query = "select a.SOLUONGNHAN, a.SOLUONGXUAT, b.LOAISANPHAM_FK, a.SANPHAM_FK, b.DVKD_FK, " +
						"	isnull( ( select AVG(GIATON) from ERP_TONKHOTHANG where THANG = " + thangOLD + " and NAM = " + namOLD + " and SANPHAM_FK = a.SANPHAM_FK ), 0 ) as GiaTon,  " +
						"	( select sohieutaikhoan from ERP_TAIKHOANKT where PK_SEQ in ( select TAIKHOANKT_FK from ERP_LOAISANPHAM where PK_SEQ = b.LOAISANPHAM_FK ) ) as TaiKhoanCO  " +
						"from ERP_CHUYENKHO_SANPHAM a inner join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ " +
						"where a.chuyenkho_fk = '" + id + "' ";
				
				System.out.println("-----LAY TAI KHOAN: " + query);
				ResultSet rsCK = db.get(query);
				if(rsCK != null)
				{
					while(rsCK.next())
					{
//						String dvkd = rsCK.getString("DVKD_FK");
						String loaisp = rsCK.getString("LOAISANPHAM_FK");
						String sanpham_fk = rsCK.getString("SANPHAM_FK");
						String tiente_fk = "100000";
						double soluong = rsCK.getDouble("SOLUONGNHAN");
						double giaTonTH = rsCK.getDouble("GiaTon");
						
						if(noidungxuat_fk.equals("100015")) //Xuất hủy phế liệu
						{
							taikhoanktNo_SoHieu = "811000";
							taikhoanktCo_SoHieu = rsCK.getString("TaiKhoanCO");
							soluong = rsCK.getDouble("SOLUONGXUAT");
						}
						else  //Xuất tiêu thụ trong SX
						{
							if( noidungxuat_fk.equals("100009") ) //Xuat tieu thu NHOM
							{
								if(loaisp.equals("100013")) //Paper
								{
									taikhoanktNo_SoHieu = "621110";
									taikhoanktCo_SoHieu = rsCK.getString("TaiKhoanCO");
								}
								else
								{
									if(loaisp.equals("100014")) //FOIL
									{
										taikhoanktNo_SoHieu = "621120";
										taikhoanktCo_SoHieu = rsCK.getString("TaiKhoanCO");
									}
									else
									{
										if(loaisp.equals("100015")) //GLUE
										{
											taikhoanktNo_SoHieu = "621130";
											taikhoanktCo_SoHieu = rsCK.getString("TaiKhoanCO");
										}
										else
										{
											if(loaisp.equals("100016"))  //LACQUE
											{
												taikhoanktNo_SoHieu = "621140";
												taikhoanktCo_SoHieu = rsCK.getString("TaiKhoanCO");
											}
											else
											{
												if(loaisp.equals("100017")) //Sub-Materials
												{
													taikhoanktNo_SoHieu = "621150";
													taikhoanktCo_SoHieu = rsCK.getString("TaiKhoanCO");
												}
												else
												{
													if(loaisp.equals("100000")) //VẬT TƯ
													{
														taikhoanktNo_SoHieu = "627119";
														taikhoanktCo_SoHieu = rsCK.getString("TaiKhoanCO");
													}
													else //LOẠI SẢN PHẨM KHÁC
													{
														taikhoanktNo_SoHieu = "632000";
														taikhoanktCo_SoHieu = rsCK.getString("TaiKhoanCO");
													}
												}
											}
										}
									}
								}
							}
							else
							{
								if( noidungxuat_fk.equals("100021") ) //XUAT TIEU THU LOI
								{
									if(loaisp.equals("100013")) //Paper
									{
										taikhoanktNo_SoHieu = "621210";
										taikhoanktCo_SoHieu = rsCK.getString("TaiKhoanCO");
									}
									else
									{
										if(loaisp.equals("100014")) //FOIL
										{
											taikhoanktNo_SoHieu = "621220";
											taikhoanktCo_SoHieu = rsCK.getString("TaiKhoanCO");
										}
										else
										{
											if(loaisp.equals("100015")) //GLUE
											{
												taikhoanktNo_SoHieu = "621230";
												taikhoanktCo_SoHieu = rsCK.getString("TaiKhoanCO");
											}
											else
											{
												if(loaisp.equals("100016"))  //LACQUE
												{
													taikhoanktNo_SoHieu = "621240";
													taikhoanktCo_SoHieu = rsCK.getString("TaiKhoanCO");
												}
												else
												{
													if(loaisp.equals("100017")) //Sub-Materials
													{
														taikhoanktNo_SoHieu = "621250";
														taikhoanktCo_SoHieu = rsCK.getString("TaiKhoanCO");
													}
													else
													{
														if(loaisp.equals("100000")) //VẬT TƯ
														{
															taikhoanktNo_SoHieu = "627219";
															taikhoanktCo_SoHieu = rsCK.getString("TaiKhoanCO");
														}
														else //LOẠI SẢN PHẨM KHÁC
														{
															taikhoanktNo_SoHieu = "632000";
															taikhoanktCo_SoHieu = rsCK.getString("TaiKhoanCO");
														}
													}
												}
											}
										}
									}
								}
								else
								{
									if( noidungxuat_fk.equals("100022") ) //Xuất tiêu thụ PX Sản phẩm mới
									{
										if(loaisp.equals("100013")) //Paper
										{
											taikhoanktNo_SoHieu = "621110";
											taikhoanktCo_SoHieu = rsCK.getString("TaiKhoanCO");
										}
										else
										{
											if(loaisp.equals("100014")) //FOIL
											{
												taikhoanktNo_SoHieu = "621120";
												taikhoanktCo_SoHieu = rsCK.getString("TaiKhoanCO");
											}
											else
											{
												if(loaisp.equals("100015")) //GLUE
												{
													taikhoanktNo_SoHieu = "621130";
													taikhoanktCo_SoHieu = rsCK.getString("TaiKhoanCO");
												}
												else
												{
													if(loaisp.equals("100016"))  //LACQUE
													{
														taikhoanktNo_SoHieu = "621140";
														taikhoanktCo_SoHieu = rsCK.getString("TaiKhoanCO");
													}
													else
													{
														if(loaisp.equals("100017")) //Sub-Materials
														{
															taikhoanktNo_SoHieu = "621150";
															taikhoanktCo_SoHieu = rsCK.getString("TaiKhoanCO");
														}
														else
														{
															if(loaisp.equals("100000")) //VẬT TƯ
															{
																taikhoanktNo_SoHieu = "627119";
																taikhoanktCo_SoHieu = rsCK.getString("TaiKhoanCO");
															}
															else //LOẠI SẢN PHẨM KHÁC
															{
																taikhoanktNo_SoHieu = "632000";
																taikhoanktCo_SoHieu = rsCK.getString("TaiKhoanCO");
															}
														}
													}
												}
											}
										}
									}else if( noidungxuat_fk.equals("100030") )  //Xuất vật tư cho tài sản xây dựng dở dang
									{
										
										query = "SELECT TK.SOHIEUTAIKHOAN AS TAIKHOANNO \n " +
												"FROM ERP_YEUCAUCHUYENKHO YCCK \n " +
												"INNER JOIN ERP_TAISANCODINH TSCD ON TSCD.PK_SEQ = YCCK.TSDD_FK \n " +
												"INNER JOIN Erp_LOAITAISAN LTS ON LTS.PK_SEQ = TSCD.LOAITAISAN_FK \n " +
												"INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = LTS.TAIKHOAN_FK \n " +
												"WHERE YCCK.PK_SEQ IN (SELECT YEUCAUCHUYENKHO_FK FROM ERP_CHUYENKHO WHERE PK_SEQ = " + id+ ")  \n " ;
										System.out.println(query);
										
										ResultSet rs2 = db.get(query);
										if(rs2 != null){
											rs2.next();
											taikhoanktNo_SoHieu = rs2.getString("TAIKHOANNO");
											rs2.close();
										}
										
										taikhoanktCo_SoHieu = rsCK.getString("TaiKhoanCO");
									}
									
								}
							}	
						}
						
						//TAI KHOAN KE TOAN
						if(taikhoanktCo_SoHieu.trim().length() > 0 || taikhoanktNo_SoHieu.trim().length() > 0 )
						{
							double tonggiatri = soluong * giaTonTH;
							String msg = "";
							
							if( noidungxuat_fk.equals("100030")){ //Xuất vật tư cho tài sản xây dựng dở dang
								System.out.println("" + thang + ", " + nam + ", Chuyển kho" + ", " + taikhoanktNo_SoHieu + ", " + taikhoanktCo_SoHieu
										+ ", " + Double.toString(tonggiatri) + "Chuyển kho - Xuất vật tư cho tài sản xây dựng dở dang" );
								msg = 	util.Update_TaiKhoan_TheoSoHieu_CAN( db, thang, nam, ngaychot, ngaychot, "Chuyển kho", id, taikhoanktNo_SoHieu, taikhoanktCo_SoHieu, noidungxuat_fk, 
										Double.toString(tonggiatri), Double.toString(tonggiatri), "Vật tư", sanpham_fk, "0", "", "", tiente_fk, "", "1", Double.toString(tonggiatri), Double.toString(tonggiatri), "Chuyển kho - Xuất vật tư cho tài sản" );
								
							}else{

								msg = util.Update_TaiKhoan_TheoSoHieu_CAN( db, thang, nam, ngaychot, ngaychot, "Chuyển kho", id, taikhoanktNo_SoHieu, taikhoanktCo_SoHieu, noidungxuat_fk, 
											Double.toString(tonggiatri), Double.toString(tonggiatri), "Sản phẩm", sanpham_fk, "0", "", "", tiente_fk, "", "1", Double.toString(tonggiatri), Double.toString(tonggiatri), "Chuyển kho" );
							}
							if(msg.trim().length() > 0)
							{
								rsCK.close();
								db.getConnection().rollback();
								System.out.println(msg);
								return "HTCK1.4 " + msg;
							}
						}
						
					}
					rsCK.close();
				}
				
			}
			
			//HOÀN TẤT CÁC PHIẾU YÊU CẦU CHUYỂN KHO KHI HOÀN TẤT
			
			query=  " SELECT SANPHAM_FK,SOLUONGYEUCAU, "+  
					" (   "+
					" SELECT SUM(SOLUONGXUAT) FROM ERP_CHUYENKHO CK   "+
					" INNER JOIN  ERP_CHUYENKHO_SANPHAM A ON A.CHUYENKHO_FK=CK.PK_SEQ "+
					" AND CK.YEUCAUCHUYENKHO_FK=YCCK.PK_SEQ  "+
					" AND CK.TRANGTHAI <>4  and A.SANPHAM_FK=CK_SP.SANPHAM_FK "+
					" ) AS A  "+
					" FROM ERP_YEUCAUCHUYENKHO YCCK  "+
					" INNER JOIN ERP_YEUCAUCHUYENKHO_SANPHAM CK_SP "+  
					" ON YCCK.PK_SEQ=CK_SP.YEUCAUCHUYENKHO_FK "+ 
					" WHERE YCCK.PK_SEQ= (  "+
					" SELECT YEUCAUCHUYENKHO_FK FROM ERP_CHUYENKHO WHERE PK_SEQ= "+id+"  ) "+ 
					" AND SOLUONGYEUCAU > (   "+
					" SELECT SUM(SOLUONGXUAT) FROM ERP_CHUYENKHO CK   "+
					" INNER JOIN  ERP_CHUYENKHO_SANPHAM A ON A.CHUYENKHO_FK=CK.PK_SEQ   "+
					" WHERE CK.YEUCAUCHUYENKHO_FK=YCCK.PK_SEQ  AND A.SANPHAM_FK=CK_SP.SANPHAM_FK "+
					" AND CK.TRANGTHAI <> 4  )  ";
			
			System.out.println("Hoàn tác " + query);
			ResultSet rscheck=db.get(query);
			if(rscheck.next()){
				
			}else{
				//hoàn tất phiếu 
				query="Update ERP_YEUCAUCHUYENKHO set trangthai=3  where pk_seq = (select yeucauchuyenkho_fk from erp_chuyenkho where pk_Seq="+id+") ";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					rscheck.close();
					return "HTCK1.15 Không thể cập nhật erp_yeucauchuyenkho " + query;
				}
			}
			query = "Update ERP_CHUYENKHO set trangthai = '3' where pk_seq = '" + id+ "' ";
			
			System.out.println("Set trang thai: " + query);
			
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "HTCK1.16  1.Không thể hoàn tất chuyển kho: " + query;
			}
			
			if(!noidungxuat_fk.equals("100030")){
			// cập nhật booked cho lệnh sản xuất của phiếu chuyển kho này
			 query=  " select lsx.KHOTT_FK as KhoNhan_FK,lsx.VATTU_FK as sanpham_fk,lsx.LENHSANXUAT_FK, ( lsx.SOLUONG - isnull(lsx.booked_lsx,0) ) as chuabooked , "
			 		+" kho.AVAILABLE from ERP_LENHSANXUAT_BOM_GIAY lsx "+ 
					 " inner join ERP_KHOTT_SANPHAM kho on kho.KHOTT_FK= lsx.KHOTT_FK and lsx.VATTU_FK=kho.SANPHAM_FK "+
					 " where lsx.LENHSANXUAT_FK = (select lenhsanxuat_fk from ERP_CHUYENKHO ck where ck.PK_SEQ ="+id+") " +
					 " and ( lsx.SOLUONG - isnull(lsx.booked_lsx,0) ) >0  " ;
			 
			 
			 rs=db.get(query);
			 while (rs.next()){
				 String spId=rs.getString("SANPHAM_FK");
				 String LsxId=rs.getString("LENHSANXUAT_FK");
				 double SOLUONGNHAN=rs.getDouble("chuabooked");
				 double soluongconbooked=rs.getDouble("AVAILABLE"); 
				 String khott_fk=rs.getString("KhoNhan_FK"); 
				 double soluongbooked=0;
				 if(SOLUONGNHAN  >= soluongconbooked){
					 soluongbooked= soluongconbooked;
				 }else{
					 soluongbooked = SOLUONGNHAN;
				 }
				 double soluong=0;
				 // booked kho,giảm avai,số lượng giữ nguyên
				String	msg1= util_kho.Update_Kho_Sp(db, khott_fk, spId, soluong, soluongbooked,(-1)*soluongbooked, 0);
				
				if(msg1.length() >0){
					db.getConnection().rollback();
					return "HTCK1.17 " + msg1;
				}
				 // cập nhật lại booked trong kho
				 query="update ERP_LENHSANXUAT_BOM_GIAY set BOOKED_LSX =isnull(BOOKED_LSX,0)+"+soluongbooked+"  where lenhsanxuat_fk="+LsxId+" and vattu_fk="+spId;
					if(!db.update(query))
					{
						db.getConnection().rollback();
						return "HTCK1.18  1.Không thể thực hiện dòng lệnh : " + query;
					}
			 
			 }
			 rs.close();
			
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			db.shutDown();
			return "HTCK1.19  Không thể hoàn tất chuyển kho: " + e.getMessage();
		}
		
		return "";
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    String action = request.getParameter("action");
	    if (action == null)
	    {
	    	action = "";
	    }
	    
		IErpChuyenkhoSXList obj = new ErpChuyenkhoSXList();
	    String task = request.getParameter("task");
		if(task == null)
		  task = "chuyenNL";
		obj.settask(task);
	    
		String isnhanHang = request.getParameter("isnhanHang");
		if(isnhanHang == null)
			isnhanHang = "";
		obj.setIsnhanHang(isnhanHang);
		
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    //System.out.println("USEDID: "+userId);
	    if(action.equals("Tao moi"))
	    {
	    	IErpChuyenkhoSX lsxBean = new ErpChuyenkhoSX();
	    	
	    	lsxBean.setUserId(userId);
	    	lsxBean.settask(task);
	    	lsxBean.createRs();
	    	lsxBean.setIsnhanHang(isnhanHang);
	    	obj.DBclose();
	    	session.setAttribute("lsxBean", lsxBean);
	    	session.setAttribute("khochuyenIds", "");
			session.setAttribute("trangthaisp", "");
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKhoSanXuatNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj.setUserId(userId);
		    	String search = getSearchQuery(request, obj);
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	
		    	String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKhoSanXuat.jsp";
				if(isnhanHang.equals("1"))
				{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKho_NhanHangList.jsp";
				}
				else
				{
					if(isnhanHang.equals("2"))
					{
						nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKho_XuatHangList.jsp";
					}
				}
				response.sendRedirect(nextJSP);
		    }
	    	else
	    	{
	    		obj.setUserId(userId);
		    	String search = getSearchQuery(request, obj);
		    	
		    	obj.init(search);
				
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKhoSanXuat.jsp";
	    		if(isnhanHang.equals("1"))
	    		{
	    			nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKho_NhanHangList.jsp";
	    		}
	    		else
	    		{
	    			if(isnhanHang.equals("2"))
	    			{
	    				nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKho_XuatHangList.jsp";
	    			}
	    		}
	    		response.sendRedirect(nextJSP);
	    		
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpChuyenkhoSXList obj)
	{
		Utility util = new Utility();
		String query = " select  isnull(Yeucauchuyenkho_fk,0) as yeucauchuyenkhoid , a.PK_SEQ, a.trangthai,  " +
				" isnull(KHOTT.TEN,'') as khonhan, a.ngaychuyen, a.noidungxuat_fk as ndxId, b.ma + ', ' + b.ten as noidungxuat, a.lydo, NV.TEN as nguoitao, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua,   \n" +
				"isnull(is_doiquycach, 0) as is_doiquycach, isnull( ( select  LenhSanXuat_FK  from ERP_YeuCauKiemDinh c where  c.pk_seq=a.yckd_fk) , isnull(a.lenhsanxuat_fk,0)) as solenhsx,     \n"+
				
				" isnull((select lsx.NGAYBATDAU  \n"+
				" from ERP_NHAPKHO nk  \n"+
				" inner join ERP_LENHSANXUAT_GIAY lsx on nk.SOLENHSANXUAT= lsx.PK_SEQ and  nk.PK_SEQ=a.nhapkho_fk ), '') as ngaysanxuat \n"+
				" from ERP_CHUYENKHO a inner join ERP_NOIDUNGNHAP b on a.noidungxuat_fk = b.pk_seq  \n" +
				" left join ERP_KHOTT KHOTT on a.khonhan_fk = KHOTT.PK_SEQ    \n" +
				" inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ    \n" +
				" inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ " +
				" where a.pk_seq > 0  \n" ;
				//"  and ( a.khonhan_fk in "+util.quyen_khott(obj.getUserId()) +" or a.khoxuat_fk  in "+util.quyen_khott(obj.getUserId())+" ) ";
		
	
		
		String tungaySX = request.getParameter("tungaySX");
		if(tungaySX == null)
			tungaySX = "";
		obj.setTungayTao(tungaySX);
		
		String denngaySX = request.getParameter("denngaySX");
		if(denngaySX == null)
			denngaySX = "";
		obj.setDenngayTao(denngaySX);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String sophieu = request.getParameter("sophieu");
		if(sophieu == null)
			sophieu = "";
		obj.setSophieu(sophieu);
		
		String khonhanid = request.getParameter("khonhanId");
		if(khonhanid == null)
			khonhanid = "";
		obj.setKhonhanid(khonhanid);
		
		String khochuyenid = request.getParameter("khochuyenId");
		if(khochuyenid == null)
			khochuyenid = "";
		obj.setKhochuyenId(khochuyenid);
		
		String lsxid = request.getParameter("solenhsx");
		if(lsxid == null)
			lsxid = "";
		obj.setLsxId(lsxid);
		
		System.out.println("lsx 1 :"+ lsxid);
		
		String lsxid1 = request.getParameter("solenhsx1");
		if(lsxid1 == null)
			lsxid1 = "";
		if(lsxid.length() <=0){
			obj.setLsxId(lsxid1);
		}
		System.out.println("lsx 2 :"+ lsxid1);
		
		
		
		String sophieuyeucau = request.getParameter("sophieuyeucau");
		if(sophieuyeucau == null)
			sophieuyeucau = "";
		obj.setsochungtu(sophieuyeucau);
		
		
		String ndxuat = request.getParameter("ndxuat");
		if(ndxuat == null)
			ndxuat = "";
		obj.setNdxuat(ndxuat);
		
		String lydo = request.getParameter("lydo");
		if(lydo == null)
			lydo = "";
		obj.setLydo(lydo);
		
		
		String nguoitao = request.getParameter("nguoitao");
		if(nguoitao == null)
			nguoitao = "";
		obj.setNguoitao(nguoitao);
		
		String nguoisua = request.getParameter("nguoisua");
		if(nguoisua == null)
			nguoisua = "";
		obj.setNguoisua(nguoisua);
		

		
		
		if(tungaySX.length() > 0)
			query += " and a.ngaychuyen >= '" + tungaySX + "'";
		
		if(denngaySX.length() > 0)
			query += " and a.ngaychuyen <= '" + denngaySX + "'";
	
		if(trangthai.length() > 0)
			query += " and a.TrangThai = '" + trangthai + "'";
		
		if(sophieu.length() > 0){
			query += " and  cast( a.pk_seq as nvarchar(10))  like '%" + sophieu + "%'";
		}
		
		if(khonhanid.length() > 0){
			query += " and  cast( a.khonhan_fk as nvarchar(10))  like '%" + khonhanid + "%'";
		}
		
		if(khochuyenid.length() > 0){
			query += " and  cast( a.khoxuat_fk as nvarchar(10))  like '%" + khochuyenid + "%'";
		}
		
		 
		
		if(lsxid1.length() > 0){
			query += " and (  a.yckd_fk in  (select pk_seq  from ERP_YeuCauKiemDinh where  cast(isnull(LenhSanXuat_FK ,0) as nvarchar(10)) " +
					" like '%"+lsxid1+"%') or cast(isnull(a.lenhsanxuat_fk,0) as nvarchar(10)) like  '%"+lsxid1+"%'  )";
		}
		
		if(sophieuyeucau.length() > 0){
			query += " and  cast(  ISNULL(YEUCAUCHUYENKHO_FK,0) as nvarchar(10))  like '%" + sophieuyeucau + "%'";
		}
		
		if(lydo.length() > 0){
			query += " and a.lydo like N'%" +lydo+ "%'";
		}
		
		if(ndxuat.length() > 0){
			query += " and a.noidungxuat_fk = " +ndxuat+ " ";
		}
		if(nguoitao.length() > 0){
			query += " and dbo.ftBoDau(NV.PK_SEQ) like N'%" +util.replaceAEIOU(nguoitao)+"%'";
		}
		if(nguoisua.length() > 0){
			query += " and dbo.ftBoDau(NV2.PK_SEQ) like N'%" +util.replaceAEIOU(nguoisua)+"%'";
		}
		
		/*if(obj.gettask().equals("LSX")){
			query+=" and a.lenhsanxuat_fk is not null ";
		}else if(obj.gettask().equals("chuyenNL")){
			query+=" and a.lenhsanxuat_fk is null ";
		}
		*/
		
		System.out.println("Câu TK: "+query);
		return query;
	}
		
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
}
