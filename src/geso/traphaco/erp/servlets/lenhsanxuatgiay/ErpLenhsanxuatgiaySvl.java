package geso.traphaco.erp.servlets.lenhsanxuatgiay;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.Utility_Kho;
import geso.traphaco.erp.beans.danhmucvattu.IDanhmucvattu_SP;
import geso.traphaco.erp.beans.danhmucvattu.imp.Danhmucvattu_SP;
import geso.traphaco.erp.beans.lenhsanxuatgiay.IErpLenhsanxuat;
import geso.traphaco.erp.beans.lenhsanxuatgiay.IErpLenhsanxuatList;
import geso.traphaco.erp.beans.lenhsanxuatgiay.imp.ErpLenhsanxuat;
import geso.traphaco.erp.beans.lenhsanxuatgiay.imp.ErpLenhsanxuatList;
import geso.traphaco.erp.beans.yeucauchuyenkho.IYeucau;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.Yeucau;
import geso.traphaco.erp.util.Kho_Lib;
import geso.traphaco.erp.util.Library;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class ErpLenhsanxuatgiaySvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpLenhsanxuatgiaySvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpLenhsanxuatList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    
	    String lsxId = util.getId(querystring);
	    obj = new ErpLenhsanxuatList();
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    obj.setNppId((String)session.getAttribute("nppId"));
	    obj.setLSXId(lsxId);
	    
	    if (action.equals("delete"))
	    {	
	    	obj.setMsg(DeleteLsx(lsxId));
	    } 
	     
	    if (action.equals("kichhoat"))
	    {	
	    	obj.setMsg(Kichhoat(lsxId, obj.getCongtyId(),userId));
	    } 
	    else
	    {
	    	if(action.equals("hoantat"))
	    	{
	    		String msg =HoanTatLenhSanXuat(lsxId,userId);
	    		obj.setMsg(msg);
	    	}
	    }
	    
	    
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
		session.setAttribute("tensudung", "2");
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLenhSanXuatGiay.jsp";
		response.sendRedirect(nextJSP);
	}


	private String Kichhoat(String lsxId, String ctyId, String UserId) {
		dbutils db = new dbutils();
	
		try {
			
			  
				String query =  " select pk_seq  " +
								"  from erp_chuyenkho where trangthai   in (1,2) and  lenhsanxuat_fk= "+lsxId ;
				ResultSet rsck=db.get(query);
				String chuoick="";
				String khonhanid="";
				while (rsck.next()){
					chuoick=  chuoick +"," +rsck.getString("pk_seq");
					 
				}
				rsck.close();
				if(chuoick.length() >0){
					 
					return "LSX số "+lsxId+".có phát sinh phiếu nhận hàng số :"+chuoick+". Chưa hoàn tất, xin vui lòng kiểm tra lại.";
					
				}
				
				
				query="SELECT SOLUONGME FROM ERP_LENHSANXUAT_GIAY A WHERE A.PK_SEQ="+lsxId;
				ResultSet rs=db.get(query);
				int soluongme=0;
				if(rs.next()){
					soluongme= rs.getInt("SOLUONGME");
						
					
				}
				rs.close();
				if(soluongme<=0){
					return "Vui lòng nhập số mẻ cho LSX số "+lsxId;
				}
					
				
				query=	" SELECT SP.MA FROM ERP_LENHSANXUAT_BOM_GIAY_CHITIET A  " +
						" INNER JOIN ERP_LENHSANXUAT_GIAY B ON A.LENHSANXUAT_FK=B.PK_SEQ " +
						" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ =A.VATTU_FK "+ 
						" WHERE A.LENHSANXUAT_FK= "+lsxId;
				System.out.println("query ERP_LENHSANXUAT_BOM_GIAY_CHITIET : "+query);
				    rs=db.get(query);
				if(!rs.next()){
					rs.close();
					return  " Không thể kích hoạt lệnh sản xuất này,chưa mở lệnh để lưu chi tiết nguyên liệu cần chuyển cho sản xuất ";
				}
				rs.close();
				
	 
				String msg1="";
				db.getConnection().setAutoCommit(false);
				
				query=	" select  DISTINCT	a.congdoan_fk ,a.KHOTT_FK , " +
						"( select lsx.khosanxuat_fk from ERP_LENHSANXUAT_Congdoan_GIAY lsx where lsx.lenhsanxuat_fk= "+lsxId+" and congdoan_fk=a.congdoan_fk ) as khonhanid     "+
						 
						" from ERP_LENHSANXUAT_BOM_GIAY_CHITIET a  "+
						" where  ISNULL(ISYCCK,'0')='0'  AND  lenhsanxuat_fk="+lsxId ;
				System.out.println("query 1.1: "+query);
				ResultSet rskho=db.get(query);
			 	String NPP_FK="";
				while(rskho.next()){
					String khoid=rskho.getString("KHOTT_FK");
					System.out.println("Kho xuat: "+khoid);
					khonhanid=rskho.getString("khonhanid");
					 
					String cdid=rskho.getString("congdoan_fk");
					if(khonhanid==null || khonhanid.equals("")){
						db.getConnection().rollback();
						 
						return "Không xác định được kho nhận của công đoạn sản xuất ";
					}
					  msg1=this.createChuyenKho(db, lsxId, UserId,khoid,khonhanid,NPP_FK,ctyId,cdid);
					  System.out.println("msg1 1.1: "+msg1);
						if(msg1.length()>0){
							
							db.getConnection().rollback();
							db.shutDown();
							return msg1;
						}
						
					  msg1=	 this.Capnhat_SoluongDinhMuc(db,lsxId,cdid);
						if(msg1.length()>0){
							db.getConnection().rollback();
							return msg1;
						}
						
				}
				rskho.close();
					 
				query = "update ERP_LENHSANXUAT_GIAY set TRANGTHAI = 2 where PK_SEQ = '"+lsxId+"' ";
				if(!db.update(query))
				{
					String msg = "Không cập nhật được trạng thái lệnh sản xuất, vui lòng kiểm tra lại.";
					db.getConnection().rollback();
					db.shutDown();
					return msg;
				}
				
				
				
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
				db.shutDown();
			// booked kho với những sản phẩm chưa booked chỗ này
			
			// TÍNH SỐ LƯỢNG QUY ĐỔI VỀ ĐỊNH MỨC BOM DÃ YÊU CẦU
				
			
		} catch (Exception e) {
			e.printStackTrace();
			db.update("rollback");
			db.shutDown();
			return "Lỗi khi kích hoạt.";
		}
		return "Đã kích hoạt lệnh thành công.";
	}

	
	private String Check_DuNl_Sx(dbutils db, String lsxId) {
		String str="";
		try{
			
			String query=   " SELECT BOM.SOLUONGCHUAN,DATA.SOLUONGCHUAN as SOLUONGYC,SP.MA "+
							" FROM  ERP_LENHSANXUAT_BOM_GIAY BOM LEFT JOIN   "+
							" ( "+
							" SELECT LENHSANXUAT_FK,VATTU_FK,SUM(SOLUONGCHUAN) AS SOLUONGCHUAN "+
							" FROM ERP_LENHSANXUAT_BOM_GIAY_CHITIET   where LENHSANXUAT_FK=  "+lsxId +  
							" GROUP BY LENHSANXUAT_FK,VATTU_FK "+
							" ) DATA "+
							" ON BOM.LENHSANXUAT_FK=DATA.LENHSANXUAT_FK AND BOM.VATTU_FK=DATA.VATTU_FK "+
							" inner join ERP_SANPHAM sp on sp.PK_SEQ= BOM.VATTU_FK "+
							" inner join ERP_LOAISANPHAM lsp on lsp.PK_SEQ=sp.LOAISANPHAM_FK "+
							" where BOM.LENHSANXUAT_FK = "+lsxId+ 
							" AND ISNULL(lsp.isNguyenlieusanxuat,'0') = '1' "+
							" AND  CAST( round(BOM.SOLUONGCHUAN,3) AS NUMERIC(18,0) ) > CAST(round(ISNULL(DATA.SOLUONGCHUAN,0),3)  AS NUMERIC(18,0) )";
			
			ResultSet rs=db.get(query);
			while(rs.next()){
				str+=rs.getString("MA")+ "- Số lượng định mức BOM: "+rs.getString("SOLUONGCHUAN")+", Số lượng yêu cầu: "+rs.getString("SOLUONGYC")+" \n";
				
			}
			rs.close();
		 
		}catch(Exception er){
			return er.getMessage();
		}
		return str ;
	}

	private String Capnhat_SoluongDinhMuc(dbutils db, String lsxId,String cdid) {
		// TODO Auto-generated method stub
		try{
			String query=	" UPDATE BOM SET BOM.SOLUONG=ISNULL(DATA.SOLUONGCHUAN,0) FROM ( "+
							" SELECT congdoan_fk,LENHSANXUAT_FK,VATTU_FK,SUM(SOLUONGCHUAN) AS SOLUONGCHUAN "+
							" FROM ERP_LENHSANXUAT_BOM_GIAY_CHITIET   where congdoan_fk="+cdid+" and  LENHSANXUAT_FK= "+lsxId+" and ISYCCK='1' "+
							" GROUP BY LENHSANXUAT_FK,VATTU_FK ,congdoan_fk "+
							" ) DATA "+
							" LEFT JOIN ERP_LENHSANXUAT_BOM_GIAY BOM ON BOM.LENHSANXUAT_FK=DATA.LENHSANXUAT_FK AND BOM.VATTU_FK=DATA.VATTU_FK and bom.congdoan_fk= data.congdoan_fk "+
							" where data.congdoan_fk="+cdid+"  and   DATA.LENHSANXUAT_FK="+lsxId;
			if(!db.update(query)){
				return "Không thể cập nhật nhật số lượng định mức của LSX: "+query;
				
			}
			
		}catch(Exception er){
			er.printStackTrace();
			return er.getMessage();
		}
		return "";
	}

	private String createChuyenKho(dbutils db, String lsxId, String userId,
			String khoid,String khonhan_fk,String Nppid1,String congtyId,String congdoanid) {
		try{
			 Utility_Kho  util_kho=new Utility_Kho();
			 String query=" SELECT  ISNULL(DDH.NPP_FK ,0) as KHACHHANG_FK ,ISNULL(LSX.ISGIACONG,0)  AS ISGIACONG ,NGAYBATDAU , SP.TEN +N'- Số Lô : '+ ISNULL(LSX.DIENGIAI,'') AS SOLO  " +
					 " FROM ERP_LENHSANXUAT_CONGDOAN_GIAY  LSXSP  " +
					 " INNER JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ=LSXSP.LENHSANXUAT_FK " +
					 " LEFT JOIN ERP_DONDATHANG DDH ON DDH.PK_SEQ=LSX.DONDATHANG_FK  "+  
					 " INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ= LSXSP.SANPHAM_FK "
					 + " WHERE LENHSANXUAT_FK="+lsxId+" AND CONGDOAN_FK="+congdoanid;
			 
			 System.out.println("QUERY INIT TU DONG CHUYEN KHO :"+query);
				ResultSet rs=db.get(query);
				String solo="";
				String ngaychuyen="";
				String isgiacong="";
				String KH_XUAT_FK="",DOITUONG="",LOAIDOITUONG="";
				if(rs.next()){
					solo=rs.getString("SOLO");
					ngaychuyen=rs.getString("NGAYBATDAU");
					isgiacong=rs.getString("ISGIACONG");
					KH_XUAT_FK= rs.getString("KHACHHANG_FK");
					// kho gia công 
					if(Library.getLoaiKho(khoid,db).equals("13")){
						 	
							DOITUONG=KH_XUAT_FK;
							LOAIDOITUONG="1";	
							
					}else{
						KH_XUAT_FK="NULL";
						DOITUONG="";
						LOAIDOITUONG="";
					}
					
					
				}
				rs.close();
				 
			
				  query = 	" insert ERP_CHUYENKHO(LENHSANXUAT_FK,CONGDOAN_FK,LENHSANXUAT_CD_FK, IsChuyenHangSX,  noidungxuat_fk, NgayChuyen, lydo, ghichu," +
							" trangthai, khoxuat_fk, khonhan_fk,  ngaytao, nguoitao, ngaysua, nguoisua,congty_fk, sochungtu, doituong_fk,LOAIDOITUONG,loaidoituongNHAN,	DOITUONGNHAN_FK) " +
		   			" values("+lsxId+","+congdoanid+", (SELECT PK_SEQ FROM ERP_LENHSANXUAT_CONGDOAN_GIAY WHERE LENHSANXUAT_FK="+lsxId+" AND CONGDOAN_FK="+congdoanid+" ), 1,100066 , '" + ngaychuyen+ "',    N'Chuyển cho lệnh SX:"+lsxId+" "+solo +
		   			"', N'', '0', '" + khoid + "', " + khonhan_fk + ",  '" + getDateTime() + "', '" + userId + 
		   			"', '" + getDateTime() + "', '" + userId+ "','"+ congtyId+"',"+lsxId+" ,"+(DOITUONG.length()>0?DOITUONG:"NULL")+", "+(LOAIDOITUONG.length()>0?LOAIDOITUONG:"NULL")+"  "
		   					+ " ,5,  (SELECT PK_SEQ FROM ERP_LENHSANXUAT_CONGDOAN_GIAY WHERE LENHSANXUAT_FK="+lsxId+" AND CONGDOAN_FK="+congdoanid+" )) ";

					 
					if(!db.update(query))
					{
						return "Không thể tạo mới ERP_CHUYENKHO " + query;
						 
					}
					
			String ycnlCurrent = "";
			query = "select SCOPE_IDENTITY() as ckId";
			
			ResultSet rsPxk = db.get(query);						
			if(rsPxk.next())
			{
				ycnlCurrent = rsPxk.getString("ckId");
			}
			
			rsPxk.close();
			String msg1=check_double_sanpham_trongkho(db,lsxId,khoid);
			
			if( msg1.length() >0){
				return msg1;
			}
			
			query =" SELECT  A.DOITUONGID  DOITUONGID , A.LOAIDOITUONG LOAIDOITUONG,A.BIN_FK ,SP.MA , A.LENHSANXUAT_FK ,	VATTU_FK,	A.SANPHAM_FK ,	A.KHOTT_FK , "+
					" A.SOLO , ISNULL(CAST(A.KHUVUCKHO_FK AS NVARCHAR(12)),'')  AS  KHUVUCKHO_FK   , A.MARQUETTE_FK "+   
					" MARQUETTE_FK, ISNULL(A.MARQ,'') AS MARQ  ,A.MATHUNG,	A.MAME "+  	
					" ,A.NGAYNHAPKHO,	A.HAMLUONG ,	A.HAMAM,	 "+
					" A.NGAYHETHAN, 	A.SOLUONG ,  A.HAMLUONG,A.HAMAM, ISNULL(A.MAPHIEU,'') AS MAPHIEU , "+
					" ISNULL(A.MAPHIEUDINHTINH,'') AS MAPHIEUDINHTINH, ISNULL(A.PHIEUEO,'') AS PHIEUEO , NSX_FK   "+
				 
					" FROM ERP_LENHSANXUAT_BOM_GIAY_CHITIET A   INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ= A.SANPHAM_FK  "+
						" where ISNULL(ISYCCK,'0')='0'  AND congdoan_fk="+congdoanid+" and   lenhsanxuat_fk="+lsxId+"  AND A.KHOTT_FK="+khoid ;
				
				ResultSet rsct=db.get(query);
				//System.out.println("kiem tra kho "+ query);
				while(rsct.next()){
					
					query = " insert ERP_CHUYENKHO_SANPHAM_CHITIET( chuyenkho_fk, SANPHAM_FK,  solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, MAPHIEU, MARQ, HAMLUONG, HAMAM, soluong,BIN_FK,PHIEUEO,MAPHIEUDINHTINH,PHIEUDT,NSX_FK ) " +
					" select '" + ycnlCurrent + "', pk_seq,  N'" + rsct.getString("SOLO") + "', '" + rsct.getString("ngayhethan") + 
					" ', '" +rsct.getString("ngaynhapkho") + "', '" + rsct.getString("MAME") + "', '" + rsct.getString("MATHUNG")  + "', '" +rsct.getString("MAPHIEU") + "' " +
					" ,'"+rsct.getString("MARQ")+"' , " +
					" '" +rsct.getString("HAMLUONG") + "', '" + rsct.getString("HAMAM") + "', '" +rsct.getString("soluong")+ "',"+rsct.getString("BIN_FK")+" ,'"+rsct.getString("PHIEUEO")+"' "
							+ ",'"+rsct.getString("MAPHIEUDINHTINH")+"','"+rsct.getString("MAPHIEUDINHTINH")+"' ,"+rsct.getString("NSX_FK")+" " +
					" from ERP_SANPHAM where PK_SEQ = '" + rsct.getString("sanpham_fk") + "' ";
					
					
					 
					if(!db.update(query))
					{
						return "Không thể tạo mới ERP_CHUYENKHO " + query;
					} 
					 
					//booked tổng với chi tiết
					String vitri=rsct.getString("BIN_FK") == null ? "" : rsct.getString("BIN_FK");
					String spId=rsct.getString("sanpham_fk");
					Kho_Lib kholib=new Kho_Lib();
					kholib.setLoaichungtu("erplenhsanxuat.java 5314 chuyenkhotudong: "+ycnlCurrent);
					kholib.setNgaychungtu(ngaychuyen);
					  
					kholib.setKhottId(khoid);
					kholib.setBinId(vitri);
					kholib.setSolo(rsct.getString("solo"));
					kholib.setSanphamId(spId);
					
					kholib.setMame(rsct.getString("MAME"));
					kholib.setMathung(rsct.getString("MATHUNG"));
					kholib.setMaphieu(rsct.getString("MAPHIEU"));
					kholib.setMaphieudinhtinh(rsct.getString("MAPHIEUDINHTINH"));
					kholib.setPhieuEo(rsct.getString("PHIEUEO"));
					kholib.setNgayhethan(rsct.getString("NGAYHETHAN"));
					kholib.setNgaysanxuat("");
					kholib.setNgaynhapkho(rsct.getString("ngaynhapkho") );
					kholib.setBooked( rsct.getDouble("soluong"));
					
					kholib.setSoluong(0);
					kholib.setAvailable(-1*rsct.getDouble("soluong"));
					
					kholib.setMARQ(rsct.getString("MARQ"));
					kholib.setDoituongId(rsct.getString("DOITUONGID")== null ?"":rsct.getString("DOITUONGID"));
					kholib.setLoaidoituong(rsct.getString("LOAIDOITUONG")== null ?"":rsct.getString("LOAIDOITUONG"));
					 
					kholib.setHamluong(rsct.getString("HAMLUONG"));
					kholib.setHamam(rsct.getString("HAMAM") );
				 
					kholib.setNsxId(rsct.getString("nsx_fk"));
					
					  msg1= util_kho.Update_Kho_Sp_Tra(db,kholib);
				    if( msg1.length() >0)
					{
						return  msg1;
					}
					 
				}
				rsct.close();
				query = " insert ERP_CHUYENKHO_SANPHAM(chuyenkho_fk, SANPHAM_FK,  soluongyeucau, soluongxuat) " +
						" select chuyenkho_fk, SANPHAM_FK, sum(soluong) as soluongyeucau  , sum(soluong) as soluongxuat   from  " +
						"  ERP_CHUYENKHO_SANPHAM_CHITIET where chuyenkho_fk= "+ycnlCurrent +" group by   chuyenkho_fk, SANPHAM_FK  " ;

				if(!db.update(query))
				{
					return "Không thể tạo mới ERP_CHUYENKHO " + query;
				} 
				
				// CẬP NHẬT ĐÃ YÊU CẦU NGUYÊN LIỆU =1 
				query ="  UPDATE  ERP_LENHSANXUAT_BOM_GIAY_CHITIET  SET CHUYENKHO_FK="+ycnlCurrent+",ISYCCK='1' "+
					   "  where ISNULL(ISYCCK,'0')='0' and congdoan_fk="+congdoanid+" AND  lenhsanxuat_fk="+lsxId+"  AND  KHOTT_FK="+khoid ;
			 
				if(!db.update(query))
				{
					return "Không thể tạo mới ERP_CHUYENKHO " + query;
				} 
			
				db.execProceduce2("CapNhatTooltip_CK", new String[] { ycnlCurrent } );	
			
		}catch(Exception er){
			er.printStackTrace();
			return er.getMessage();
		}
		return "";
	}
  
	private String check_double_sanpham_trongkho(dbutils db, String lsxId,
			String khoid) {
		try{
		 
			
			String query= " select  sp.MA, a.LENHSANXUAT_FK,a.VATTU_FK,a.SANPHAM_FK,a.KHOTT_FK,a.SOLO,a.BIN_FK,a.MATHUNG,    " + 
						  " a.MAME,a.NGAYNHAPKHO,  a.NGAYHETHAN,a.MAPHIEU,a.PHIEUEO,a.MAPHIEUDINHTINH ,a.nsx_fk   " + 
						  " ,COUNT(a.NGAYNHAPKHO) AS COUNT   " + 
						  " from ERP_LENHSANXUAT_BOM_GIAY_CHITIET a   " +
						  "  INNER JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ=A.LENHSANXUAT_FK   " + 
						  " inner join erp_sanpham sp on sp.PK_SEQ= a.SANPHAM_FK    " + 
						  " left join ERP_KHOTT_SP_CHITIET kho   " + 
						  " on "
						  //+ " KHO.NPP_FK=LSX.NPP_FK AND "
						  + " kho.SANPHAM_FK= a.SANPHAM_FK and a.KHOTT_FK=kho.KHOTT_FK     " + 
						  " and a.SOLO=kho.SOLO  and a.NGAYNHAPKHO= kho.NGAYNHAPKHO     " + 
						  " and isnull(a.MARQ,'')=isnull(kho.MARQ,'')  " + 
						  " and isnull(a.MAME,'')=isnull(kho.MAME,'') and isnull(a.MATHUNG,'')= isnull(kho.MATHUNG,'')     " + 
						  " AND ISNULL(A.BIN_FK,0)= ISNULL(KHO.BIN_FK ,0)     " + 
						  " and ISNULL(a.MAPHIEU,'')= ISNULL(kho.MAPHIEU,'') and ISNULL(a.MAPHIEUDINHTINH,'')=ISNULL(kho.MAPHIEUDINHTINH,'')     " + 
						  " and ISNULL(a.PHIEUEO,'')= ISNULL(kho.PHIEUEO,'')     " + 
						  " and rtrim(ltrim(kho.NGAYHETHAN))= rtrim(ltrim(a.NGAYHETHAN))   " + 
						  " and ISNULL(A.HAMAM,'')=ISNULL(KHO.HAMAM,'') AND   ISNULL(A.HAMLUONG,'')=ISNULL(KHO.HAMLUONG,'') and isnull(kho.nsx_fk,0)= isnull(a.nsx_fk,0)    " + 
						  " where   lenhsanxuat_fk= "+lsxId+" AND   A.KHOTT_FK="+khoid +
						  " group by sp.MA, a.LENHSANXUAT_FK,a.VATTU_FK,a.SANPHAM_FK,a.KHOTT_FK,a.SOLO,a.BIN_FK,a.MATHUNG,    " + 
						  " a.MAME,a.NGAYNHAPKHO,  a.NGAYHETHAN,a.MAPHIEU,a.PHIEUEO,a.MAPHIEUDINHTINH,a.nsx_fk  " + 
						  " having COUNT(a.PK_SEQ) >1";
			
			ResultSet rs=db.get(query);
			String msg1="";
			while (rs.next()){
				msg1=rs.getString("MA");
			}
			rs.close();
			
			if(msg1.length()>0){
				return "Sản phẩm bị double sản phẩm trong kho, vui lòng báo Admin để được trợ giúp :"+ msg1;
				
			}
			
		}catch(Exception err){
			err.printStackTrace();
			return err.getMessage();
		}
		return "";
	}

	private String HoanTatLenhSanXuat(String lsxId, String userId) {
		  String msg = "";
			dbutils db = new dbutils();
			try 
			{
				db.getConnection().setAutoCommit(false);
				String query = "update ERP_LENHSANXUAT_GIAY set trangthai = '6',HOANTATNHANHANG = '1' where pk_seq = '" + lsxId + "'";
				if(!db.update(query))
				{
					msg = "1.Không thể kích hoạt lệnh sản xuất. Vui lòng thử lại";
					db.getConnection().rollback();
					db.shutDown();
					return msg;
				}
				 
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
				db.shutDown();
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				 db.update("rollback");
				msg = "3.Không thể kích hoạt lệnh sản xuất. Vui lòng thử lại " + e.getMessage();
				db.shutDown();
			}

			return msg;
	}

 
	private String DeleteLsx(String lsxId) 
	{
		String msg = "";
		Utility_Kho util_kho=new Utility_Kho();
		dbutils db = new dbutils();
		try 
		{
			db.getConnection().setAutoCommit(false);
			String query="select PK_SEQ from erp_nhapkho where TRANGTHAI='1' AND  SOLENHSANXUAT="+lsxId;
			ResultSet rsnk=db.get(query);
			 if(rsnk.next()){
						db.getConnection().rollback();
						return "Lệnh sản xuất đã nhận hàng, không thể xóa ";
				 
			 }rsnk.close();
			 
				
					query="select PK_SEQ,TRANGTHAI from ERP_CHUYENKHO where TRANGTHAI <>4 AND LENHSANXUAT_FK= "+lsxId;
					ResultSet rsck=db.get(query);
					if(rsck.next()){
						db.getConnection().rollback();
						return "Lệnh sản xuất đã yêu cầu nguyên liệu  , không thể xóa ";
					}
					
					query = " update ERP_LENHSANXUAT_GIAY set trangthai = '7' where pk_seq = '" + lsxId + "'";
			
			 
				if(!db.update(query))
				{
					msg = "2.Không thể xóa lệnh sản xuất. Vui lòng thử lại";
					db.getConnection().rollback();
					db.shutDown();
					return msg;
				}
				
				query = " update ERP_LENHSANXUATDUKIEN set SANXUAT = ISNULL(SANXUAT, 0) - (SELECT ISNULL(soluong,0) FROM ERP_LENHSANXUAT_GIAY  WHERE PK_SEQ =  '" + lsxId + "') where pk_seq IN ( SELECT LENHSANXUATDUKIEN_FK FROM ERP_LENHSANXUAT_GIAY WHERE PK_SEQ =  '" + lsxId + "')"
						+ " \n AND ISNULL(SANXUAT, 0) - (SELECT ISNULL(soluong,0) FROM ERP_LENHSANXUAT_GIAY  WHERE PK_SEQ =  '" + lsxId + "') >=0 ";
				
				 System.out.println("query: "+query);
				if(!db.update(query))
				{
					msg = "2.Không thể xóa lệnh sản xuất. Vui lòng thử lại";
					db.getConnection().rollback();
					db.shutDown();
					return msg;
				}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);		
			
			db.shutDown();
		} 
		catch (Exception e) 
		{
			
			System.out.println(" 3.Không thể xóa lệnh sản xuất. Vui lòng thử lại"+e.toString());
			msg = "3.Không thể xóa lệnh sản xuất. Vui lòng thử lại";
	 
			try
			{
				db.getConnection().rollback();
			} 
			catch (Exception e1) {}
			
			db.shutDown();
		}

		return msg;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpLenhsanxuatList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    OutputStream out = response.getOutputStream();
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    
	    System.out.println("___Action la: " + action);
	    if(action.equals("Tao moi"))
	    {
	    	IErpLenhsanxuat lsxBean = new ErpLenhsanxuat();
	    	lsxBean.setCongtyId((String)session.getAttribute("congtyId"));
	    	lsxBean.setNppId((String)session.getAttribute("nppId"));
	    	lsxBean.createRs();
    		
	    	session.setAttribute("lsxBean", lsxBean);
	    	session.setAttribute("tensudung", "2");
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLenhSanXuatGiayNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		System.out.println("toi day");
	    		obj = new ErpLenhsanxuatList();
	    		obj.setCongtyId((String)session.getAttribute("congtyId"));
	    		obj.setNppId((String)session.getAttribute("nppId"));
		    	String search = getSearchQuery(request, obj);
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setUserId(userId);
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpLenhSanXuatGiay.jsp");	
		    }
	    	else
	    	{
	    		String msg = "";
	    		if(action.equals("kichhoat"))
	    		{
	    			String kbsxId = request.getParameter("kbsxId");
	    			String lsxId = request.getParameter("lxsId");
	    			
	    			msg = KichHoatLSX(kbsxId, lsxId, userId);
		      		
	    			obj = new ErpLenhsanxuatList();
	    			obj.setCongtyId((String)session.getAttribute("congtyId"));
	    			obj.setNppId((String)session.getAttribute("nppId"));
	    			obj.setUserId(userId);
	    		    obj.init("");
	    		    obj.setMsg(msg);
	    		    
	    			session.setAttribute("obj", obj);

	    			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLenhSanXuatGiay.jsp";
	    			response.sendRedirect(nextJSP);
	    			
	    		}
	    		else
	    		{
			    	obj = new ErpLenhsanxuatList();
			    	obj.setCongtyId((String)session.getAttribute("congtyId"));
			    	obj.setNppId((String)session.getAttribute("nppId"));
			    	if(action.equals("Excel"))
	    			{
	    				try
	    			    {
	    					response.setContentType("application/xlsm");
	    					response.setHeader("Content-Disposition", "attachment; filename=DanhSachLenhSanXuat.xlsm");
	    			        
	    					FileInputStream fstream = null;
	    					Workbook workbook = new Workbook();		
	    					 
	    					fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\DSLenhSanXuat.xlsx");
	    					 
	    					workbook.open(fstream);
	    					workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
	    			    	
	    				 /*    CreateStaticHeader(workbook, "");*/
	    				     CreateStaticData(workbook, getSearchQuery2(request, obj));
	    				
	    				     //Saving the Excel file
	    				     workbook.save(out);
	    				     
	    				     fstream.close();
	    			    }
	    			    catch (Exception ex)
	    			    {
	    			    	ex.printStackTrace();
	    			        obj.setMsg("Khong the tao báo cáo được.");
	    			    }
	    			}
			    	
			    	obj.setUserId(userId);
			    	String search = getSearchQuery(request, obj);
			    	obj.init(search);
					
					
			    	session.setAttribute("obj", obj);  	
		    		session.setAttribute("userId", userId);
			
		    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpLenhSanXuatGiay.jsp");
	    		}
	    	}
	    }
	}
	
	private String KichHoatLSX(String kbsxId, String lsxId, String userId) 
	{
		//Lay kich ban
		String msg = "";
		
		dbutils db = new dbutils();
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query =  "select * from " +
						"(  " +
							"select a.pk_seq as kbsx_fk, c.NHAMAY_FK as nhamayId, '1' as type, " +
								"b.thoigian + ( select isnull(SUM(thoigian), 0) from ERP_ChiPhiThoiGian where nhamay_fk = '100000' )  as tongTG   " +
							"from ERP_KICHBANSANXUAT a inner join ERP_DAYCHUYENSANXUAT b on a.daychuyen_fk = b.pk_seq     " +
								"inner join ERP_CUMSANXUAT c on b.cumsanxuat_fk = c.PK_SEQ    " +
							"where a.pk_seq = '" + kbsxId + "'   " +
						")  " +
						"kichban inner join  " +
						"( " +
							"select NGAYBATDAU, NGAYDUKIENHT, SOLUONG, '1' as type from ERP_LENHSANXUAT where PK_SEQ = '" + lsxId + "' " +
						")  " +
						"lenhsanxuat on kichban.type = lenhsanxuat.type";
			
			System.out.println("___Khoi tao: " + query);
			ResultSet rsKb = db.get(query);
			
			String nhamay_fk = "";
			String ngaydukienHT = "";
			String ngaybatdau = "";
			String soluong = "";
			
			if(rsKb.next())
			{
				nhamay_fk = rsKb.getString("nhamayId");
				ngaydukienHT = rsKb.getString("NGAYDUKIENHT");
				soluong = rsKb.getString("SOLUONG");
				
				ngaybatdau = getNgayBatDau(ngaydukienHT, rsKb.getFloat("tongTG"));
			}
			rsKb.close();
			
			query = "Update ERP_LENHSANXUAT set Kichbansanxuat_fk = '" + kbsxId + "', NgayBatDau = '" + ngaybatdau + "', NgayDuKienHT = '" + ngaydukienHT + "', " +
							"NhaMay_fk = '" + nhamay_fk + "', TrangThai = '1', NgaySua = '" + getDateTime() + "', NguoiSua = '" + userId + "'  " +
					"where pk_seq = '" + lsxId + "' ";
			
			System.out.println("___cap nhat LSX: " + query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				msg = "Không thể cập nhật ERP_LENHSANXUAT: " + query;
				return msg;
			}
			
			query = "insert ERP_LENHSANXUAT_BOM(LENHSANXUAT_FK, SOLUONGCHUAN, CHOPHEPTHAYTHE, VATTU_FK, SOLUONG, VATTUTT_FK, SOLUONGTT, TILE)  " +
					"select '" + lsxId + "', a.SOLUONGCHUAN, a.CHOPHEPTT, b.VATTU_FK, b.SOLUONG * " + soluong + " / a.SOLUONGCHUAN, b.VATTUTT_FK, b.SOLUONGTT, b.TILE   " +
					"from ERP_DANHMUCVATTU a inner join ERP_DANHMUCVATTU_VATTU b on a.PK_SEQ = b.DANHMUCVT_FK  " +
					"where a.PK_SEQ = ( select bom_fk from ERP_KICHBANSANXUAT where pk_seq = '" + kbsxId + "' ) ";
			
			System.out.println("___Chen BOM: " + query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				msg = "Không thể tạo mới ERP_LENHSANXUAT_BOM: " + query;
				return msg;
			}
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			
			return msg;
		} 
		catch (Exception e) 
		{
			msg = "Không thể kích hoạt lệnh sản xuất: " + e.getMessage();
			db.update("rollback");
			db.shutDown();
			return msg;
		}

	}

	private String getSearchQuery(HttpServletRequest request, IErpLenhsanxuatList obj)
	{
		Utility util= new Utility();
		
		 
		String query = 	" SELECT    isnull(solsx,'') as solenhsanxuat ,  ISNULL(A.DUYET,'0') AS DUYET, A.NGAYBATDAU ,A.PK_SEQ , isnull(A.diengiai,'') as diengiai   ,A.TRANGTHAI, SP.MA AS SPID, SP.TEN AS SPTEN, NV.TEN AS NGUOITAO, \n"+
				" A.NGAYSUA, A.NGAYTAO, NV2.TEN AS NGUOISUA, ISNULL(A.DONDATHANG_FK, '-1') AS DONDATHANG_FK, \n"+
				" A.KICHBANSANXUAT_FK , CASE WHEN SP.DVKD_FK=100005 THEN '' ELSE  ISNULL(( \n"+
				" SELECT TOP 1 D.DIENGIAI FROM ERP_LENHSANXUAT_CONGDOAN_GIAY C INNER JOIN ERP_CONGDOANSANXUAT_GIAY  D ON C.CONGDOAN_FK=D.PK_SEQ \n"+ 
				" WHERE TINHTRANG=0 AND  \n"+
				" LENHSANXUAT_FK=A.PK_SEQ ORDER BY THUTU),N'HOÀN TẤT CÁC CÔNG DOẠN') END AS CONGDOAN\n"+
				" FROM ERP_LENHSANXUAT_GIAY  A \n"+ 
				" LEFT JOIN ERP_KICHBANSANXUAT_GIAY KB ON KB.PK_SEQ=KICHBANSANXUAT_FK \n"+
				"  \n"+
				" left JOIN ERP_SANPHAM SP ON SP.PK_SEQ= a.SANPHAM_FK \n"+   
				" INNER JOIN NHANVIEN NV ON A.NGUOITAO = NV.PK_SEQ    \n"+
				" INNER JOIN NHANVIEN NV2 ON A.NGUOISUA = NV2.PK_SEQ  WHERE A.CONGTY_FK = "+obj.getCongtyId()+"";
		
		
		
		/*String tungaySX = request.getParameter("tungaySX");
		if(tungaySX == null)
			tungaySX = "";
		obj.setTungayTao(tungaySX);
				
		String tungayDk = request.getParameter("tungayDk");
		if(tungayDk == null)
			tungayDk = "";
		obj.setTungayDk(tungayDk);*/
		
		String tungayBD = request.getParameter("tungayBD");
		if(tungayBD == null)
			tungayBD = "";
		obj.setTungayBD(tungayBD);
		
		String denngayBD = request.getParameter("denngayBD");
		if(denngayBD == null)
			denngayBD = "";
		obj.setDenngayBD(denngayBD);
		
		String tungayKT = request.getParameter("tungayKT");
		if(tungayKT == null)
			tungayKT = "";
		obj.setTungayKT(tungayKT);
		
		String denngayKT = request.getParameter("denngayKT");
		if(denngayKT == null)
			denngayKT = "";
		obj.setDenngayKT(denngayKT);		
		 
		String dvkdid = request.getParameter("dvkdid");
		if(dvkdid == null)
			dvkdid = "";
		obj.setIddvkd(dvkdid);
		 
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String tensanpham = request.getParameter("tensanpham");
		if(tensanpham == null)
			tensanpham = "";
		obj.setTenSp(tensanpham);
		
		String nguoitaoid = request.getParameter("nguoitao");
		if(nguoitaoid == null)
			nguoitaoid = "";
		obj.setNguoitaoId(nguoitaoid);
		
		String nhamayid = request.getParameter("nhamay");
		if(nhamayid == null)
			nhamayid = "";
		obj.setNhamayId(nhamayid);

		String lsxid=request.getParameter("solsx");
		
		obj.setLSXId(lsxid);
		
		String soLSX1 = request.getParameter("soLSX1");
		if(soLSX1 == null)
			soLSX1 = "";
		obj.setSoLSX(soLSX1);
		
		
		
		String ghichu = request.getParameter("ghichu");
		if(ghichu == null)
			ghichu = "";
		obj.setGhichu(ghichu);
		
		if(lsxid.length()  >0){
			query=query+ " and cast( a.pk_seq as nvarchar(10)) like '%"+lsxid+"%' ";
		}
		
		if(dvkdid.length()  >0){
			query=query+ " and sp.dvkd_fk= '"+dvkdid+"' ";
		}
		
		if(tungayBD.length() > 0)
			query += " and a.Ngaybatdau >='" + tungayBD + "'";
		
		if(denngayBD.length() > 0)
			query += " and a.Ngaybatdau <= '" + denngayBD + "'";

		if(tungayKT.length() > 0)
			query += " and a.Ngaydukienht >='" + tungayKT + "'";
		
		if(denngayKT.length() > 0)
			query += " and a.Ngaydukienht <= '" + denngayKT + "'";
		
		if(trangthai.length() > 0)
			query += " and a.TrangThai = '" + trangthai + "'";
		
		if(tensanpham.length() > 0)
			query += " and dbo.ftBoDau(sp.ten) like N'%" + util.replaceAEIOU(tensanpham.trim()) + "%' ";
		
		if(ghichu.length() > 0)
			query += " and dbo.ftBoDau(a.diengiai) like N'%" + util.replaceAEIOU(ghichu.trim()) + "%' ";
		
		if(nguoitaoid.length() > 0)
			query += " and NV.PK_SEQ ='" + nguoitaoid + "'";
		
		if(nhamayid.length() > 0)
			query += " and a.nhamay_fk = '" + nhamayid + "'";
		
		if(soLSX1.length() > 0)
			query += " and isnull( a.solsx,'')  like N'%" + soLSX1 + "%'";
		

		return query;
	}
		
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private String getNgayBatDau(String ngayhoanthanh, float sogio) 
	{
		//ngay lam 8h, bat dau tinh tu 8h AM
		int ngay = Math.round( sogio / 8 );
		Calendar c1 = Calendar.getInstance();
		
		String[] arr = ngayhoanthanh.split("-");
		c1.set(Integer.parseInt( arr[0]), Integer.parseInt( arr[1]) - 1, Integer.parseInt( arr[2]) );
		
		c1.add(Calendar.DATE, (-1) * ngay);
		

		Calendar c2 = Calendar.getInstance();
		c2.set(Integer.parseInt( arr[0]), Integer.parseInt( arr[1]) - 1, Integer.parseInt( arr[2]) );
	
		while( c2.getTime().compareTo(c1.getTime()) > 0 )
		{
			//neu la ngay chu nhat thi phai tang 1 len 1 ngay
			if(c2.get(Calendar.DAY_OF_WEEK) == 8 || c2.get(Calendar.DAY_OF_WEEK) == 1)
			{
				c1.add(Calendar.DATE, -1);
			}
			
			c2.add(Calendar.DATE, -1);
		}
		
		String ngaykt = Integer.toString(c1.get(Calendar.DATE));
		if(ngaykt.trim().length() < 2)
			ngaykt = "0" + ngaykt;
		
		String thangkt = Integer.toString(c1.get(Calendar.MONTH) + 1);
		if(thangkt.trim().length() < 2)
			thangkt = "0" + thangkt;
		
		//System.out.println("___Date ngay bat dau: " + c1.get(Calendar.DAY_OF_WEEK) );
		System.out.println("1.Ngay bat dau tinh duoc: " + Integer.toString(c1.get(Calendar.YEAR)) + "-" + thangkt + "-" + ngaykt);
		
		return Integer.toString(c1.get(Calendar.YEAR)) + "-" + thangkt + "-" + ngaykt;
	}
	
	
	
	/***********************EXcel***********************/
	private void CreateStaticHeader(Workbook workbook, String UserName) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	    cells.setRowHeight(0, 20.0f);
	    
	    Cell cell = cells.getCell("A2"); 
	    ReportAPI.getCellStyle(cell,Color.RED, true, 18, "Danh sách lệnh sản xuất");
	   
	    //tieu de
	    cell = cells.getCell("A5");
	    cell.setValue("Mã lệnh");
	    ReportAPI.setCellHeader(cell);
	    
	    cell = cells.getCell("B5");
	    cell.setValue("Ngày bắt đầu");
	    ReportAPI.setCellHeader(cell);
	    
	    cell = cells.getCell("C5");
	    cell.setValue("Ngày dự kiến HT");
	    ReportAPI.setCellHeader(cell);
	    
	    cell = cells.getCell("D5");
	    cell.setValue("Mã sản phẩm");
	    ReportAPI.setCellHeader(cell);
	    
	    cell = cells.getCell("E5");
	    cell.setValue("Tên sản phẩm");
	    ReportAPI.setCellHeader(cell);
	    
	    cell = cells.getCell("F5");
	    cell.setValue("Đơn vị đo lường");
	    ReportAPI.setCellHeader(cell);
	    
	    cell = cells.getCell("G5");
	    cell.setValue("Số lượng");
	    ReportAPI.setCellHeader(cell);
	    
	    
	    //worksheet.setName("Danh sách lệnh sản xuất");
	}
	
	private void CreateStaticData(Workbook workbook, String query) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    
		dbutils db = new dbutils();
		ResultSet rs = db.get(query);
		int i = 4;
		if(rs != null)
		{
			try 
			{
				cells.setColumnWidth(0, 15.0f);
				cells.setColumnWidth(1, 15.0f);
				cells.setColumnWidth(2, 15.0f);
				cells.setColumnWidth(3, 20.0f);
				cells.setColumnWidth(4, 45.0f);
				cells.setColumnWidth(5, 15.0f);
				cells.setColumnWidth(6, 15.0f);
	
				for(int j = 0; j < 6; j++)
					cells.setRowHeight(j, 13.0f);
				
				Cell cell = null;
				while(rs.next())
				{
					cell = cells.getCell("A" + Integer.toString(i));
					cell.setValue(rs.getString("ngaysanxuat"));
					cell = cells.getCell("B" + Integer.toString(i));
					cell.setValue(rs.getString("tenxuong"));
					cell = cells.getCell("C" + Integer.toString(i));
					cell.setValue(rs.getString("solenhSX"));
					cell = cells.getCell("D" + Integer.toString(i));
					cell.setValue(rs.getString("spMa"));
					cell = cells.getCell("E" + Integer.toString(i));
					cell.setValue(rs.getString("spTen"));
					cell = cells.getCell("F" + Integer.toString(i));
					cell.setValue(rs.getDouble("soluongSX"));
					
					cell = cells.getCell("G" + Integer.toString(i));
					cell.setValue(rs.getString("dvdlten"));
					
					
					cell = cells.getCell("L" + Integer.toString(i));
					cell.setValue(rs.getString("lspTen"));
					
					
					cell = cells.getCell("M" + Integer.toString(i));
					cell.setValue(rs.getDouble("soluongnhap"));
					
					cell = cells.getCell("N" + Integer.toString(i));
					cell.setValue(rs.getString("dvdl_nhapkho"));
					
					cell = cells.getCell("O" + Integer.toString(i));
					cell.setValue(rs.getDouble("soluongdat"));
					
					
					cell = cells.getCell("P" + Integer.toString(i));
					cell.setValue(rs.getDouble("soluongnhankhoTP"));
					
					
					
					
					
					i++;
				}
				rs.close();
			}
			catch (Exception e){ e.printStackTrace(); }
		}
		
	}
	
	private String getSearchQuery2(HttpServletRequest request, IErpLenhsanxuatList obj)
	{
		String query =  " select lsx.PK_SEQ as solenhSX, lsx.NGAYBATDAU as ngaysanxuat, lsx.NGAYDUKIENHT as ngayketthuc, isnull( cast(nk.PK_SEQ as varchar), '') as nkId, isnull(nk.NGAYNHAPKHO, '') as ngaynhap,       \n"+
						" nhamay.tennhamay as tenxuong, sp.MA as spma,  sp.TEN as spten, lsx_sp.SOLUONG as soluongSX, dvdl.DIENGIAI as dvdlten, isnull(nk_sp.SOLUONGNHAP, 0) as soluongnhap,       \n"+
						" isnull(dvdl1.DIENGIAI, '') as  dvdl_nhapkho, ISNULL(kd.soluongDat, 0) as soluongdat,      \n"+
						" isnull((case      \n"+
						" 	when isnull(nk.KHONGKIEMDINH, 0)=0       \n"+
						" 		then (select sum(ck_sp.SOLUONGNHAN)       \n"+
						" 			  from ERP_CHUYENKHO ck       \n"+
						" 			  inner join ERP_CHUYENKHO_SANPHAM ck_sp on ck.PK_SEQ= ck_sp.CHUYENKHO_FK      \n"+
						" 			  where  ck_sp.SANPHAM_FK= nk_sp.SANPHAM_FK and ck.YCKD_FK= kd.pk_seq )      \n"+
						" 	when  isnull(nk.KHONGKIEMDINH, 0)=1       \n"+
						" 		then (select sum(ck_sp.SOLUONGNHAN)       \n"+
						" 			  from ERP_CHUYENKHO ck       \n"+
						" 			  inner join ERP_CHUYENKHO_SANPHAM ck_sp on ck.PK_SEQ= ck_sp.CHUYENKHO_FK      \n"+
						" 			  where  ck.LENHSANXUAT_FK=nk.SOLENHSANXUAT and ck_sp.SANPHAM_FK= nk_sp.SANPHAM_FK and ck.LENHSANXUAT_FK is not null )      \n"+
						" end      \n"+
						" ), 0) as soluongnhankhoTP, lsp.ten as lspTen      \n"+
						" from ERP_LENHSANXUAT_GIAY lsx      \n"+
						" inner join ERP_LENHSANXUAT_SANPHAM lsx_sp on lsx.PK_SEQ=lsx_sp.LENHSANXUAT_FK      \n"+
						" inner join ERP_NHAMAY nhamay on nhamay.pk_seq=lsx.NHAMAY_FK      \n"+
						" inner join ERP_SANPHAM sp on lsx_sp.SANPHAM_FK= sp.PK_SEQ      \n"+
						" left join DONVIDOLUONG dvdl on dvdl.PK_SEQ= sp.DVDL_FK      \n"+
						" left join ERP_NHAPKHO nk on  nk.SOLENHSANXUAT= lsx.PK_SEQ  and nk.TRANGTHAI <>2         \n"+
						" left join ERP_NHAPKHO_SANPHAM nk_sp on nk.PK_SEQ=nk_sp.SONHAPKHO_FK      \n"+
						" left join DONVIDOLUONG dvdl1 on dvdl1.PK_SEQ= nk_sp.DVDL_FK       \n"+
						" left join ERP_YeuCauKiemDinh kd on kd.LenhSanXuat_FK=nk.LENHSANXUAT_FK and kd.sanpham_fk= nk_sp.SANPHAM_FK      \n"+ 
						" left join erp_loaisanpham lsp on sp.loaisanpham_fk= lsp.pk_seq      \n"+ 
						" where lsx.NPP_FK= "+obj.getNppId();
		
		
		String tungayBD =   request.getParameter("tungayBD");
		if(tungayBD==null){
			tungayBD="";
		}
		
		
		String denngayBD =  request.getParameter("denngayBD");
		if(denngayBD==null){
			denngayBD="";
		}
		

		
		
		if(tungayBD.length() > 0){
			query += " and lsx.ngaybatdau >='"+tungayBD+"' ";
		}
		
		
		if(denngayBD.length() >0){
			query += " and lsx.ngaybatdau <='"+denngayBD+"' ";
		}
		
		
		
		String tungayDK = request.getParameter("tungayKT");
		if(tungayDK == null)
			tungayDK = "";
		obj.setTungayDk(tungayDK);
		
		String denngayDK = request.getParameter("denngayKT");
		if(denngayDK == null)
			denngayDK = "";
		obj.setDenngayDk(denngayDK);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String lsxid=request.getParameter("solsx");
		
		obj.setLSXId(lsxid);
		
		
		if(lsxid.length()  >0){
			query=query+ " and cast( lsx.pk_seq as nvarchar(10)) like '%"+lsxid+"%' ";
		}
		
	
		
		if(tungayDK.length() > 0)
			query += " and lsx.NgayDuKienHT >= '" + tungayDK + "'";
		
		if(denngayDK.length() > 0)
			query += " and lsx.NgayDuKienHT <= '" + denngayDK + "'";
		
		if(trangthai.length() > 0)
			query += " and lsx.TrangThai = '" + trangthai + "'";
		
		
		System.out.println(" in excel : " + query);
		return query;
	}
	
	public static void main ( String args [  ]  )   {
		dbutils db=new dbutils();
		try{
			
			  String chuoi="2018-01-01";
			  System.out.println(chuoi.substring(1,7));
			
			 /*System.out.println("2323233333 ");
			 	db.getConnection().setAutoCommit(false);
			 
			 	String msg1= Revert_NhapKho("100338", db);
				if(msg1.length()> 0){
					db.getConnection().rollback();
					 
				}
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);*/
			//String[] mang=new String[]{"0","1","2","3","4","5","6","7"};
			
			/*String[] mang= new String[]{"101274","101275"};
			for(int i=0;i<mang.length;i++){
				HuyLenhSanxuat(mang[i]);
			}*/
				//HuyMuahang_Giacong("103451",db);
		/*	103010
		 * 
			103011
			103012
			103013
			103014*/
			/*HuyMuahang_Giacong("103012",db);
			
			HuyMuahang_Giacong("103019",db);*/
			
			
			
			 
			// HuyMuahang_Giacong("102989",db);
	 
			//101088,101089,101090,101092,101091 
			 
 
		//	HuyMuahang_Giacong("102990",db);
			
		 
			 /*103378
			 103194
			 102842
			 102844*/
		 	 
			 
			 
		}catch(Exception err){
			err.printStackTrace();
		}
	 
   }
 
 
	
}
