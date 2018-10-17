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

public class ErpDuyetLenhsanxuatgiaySvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpDuyetLenhsanxuatgiaySvl() {
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
	    String phanloai=request.getParameter("phanloai");
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    
	    String lsxId = util.getId(querystring);
	    obj = new ErpLenhsanxuatList();
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    obj.setLSXId(lsxId);
	    obj.setPhanLoai(phanloai);
	    System.out.println("PHANLOAI :"+phanloai +"  " + lsxId);
	    
	    obj.setIsduyet("1");
	    
	    if (action.equals("duyetlsx"))
	    {	
	    	obj.setMsg(DuyetLsx(lsxId, obj.getCongtyId(),userId));
	    }else if (action.equals("duyetkh"))
	    {	
	    	obj.setMsg(Duyetkh(lsxId, obj.getCongtyId(),userId));
	    }else if (action.equals("xoalsx")){
	    	obj.setMsg(DeleteLsx(lsxId));
	    }
	     
	    
	    obj.setLSXId("");
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
		session.setAttribute("tensudung", "2");
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuyetLenhSanXuat.jsp";
		response.sendRedirect(nextJSP);
	}


	private String Duyetkh(String lsxId, String congtyId, String userId) {
		// TODO Auto-generated method stub
		String msg = "";
		dbutils db = new dbutils();
		try 
		{
			db.getConnection().setAutoCommit(false);
			 
			
					
			String   query = " update ERP_LENHSANXUAT_GIAY set DUYET_KH = '1' where pk_seq = '" + lsxId + "'";
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

	private String DuyetLsx(String lsxId, String nhamayid, String userId) 
	{
		String msg = "";
		dbutils db = new dbutils();
		try 
		{
			db.getConnection().setAutoCommit(false);
			 
			
					
			String   query = " update ERP_LENHSANXUAT_GIAY set DUYET = '1' where pk_seq = '" + lsxId + "'";
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
			 
				
					
					query = " update ERP_LENHSANXUAT_GIAY set trangthai = '7' where pk_seq = '" + lsxId + "'";
			
			 
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
	    String phanloai=request.getParameter("phanloai");
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    
	    System.out.println("___Action la: " + action);
	    if(action.equals("Tao moi"))
	    {
	    	 
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		System.out.println("toi day");
	    		obj = new ErpLenhsanxuatList();
	    		obj.setCongtyId((String)session.getAttribute("congtyId"));
	    		obj.setPhanLoai(phanloai);
	    		
		    	String search = getSearchQuery(request, obj);
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setUserId(userId);
		    	obj.setIsduyet("1");
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpDuyetLenhSanXuat.jsp");	
		    }
	    	else
	    	{
	    	 
			    	obj = new ErpLenhsanxuatList();
			    	obj.setCongtyId((String)session.getAttribute("congtyId"));
			    	obj.setIsduyet("1");
			    	obj.setPhanLoai(phanloai);
		    		
			    	obj.setUserId(userId);
			    	String search = getSearchQuery(request, obj);
			    	obj.init(search);
					
					
			    	session.setAttribute("obj", obj);  	
		    		session.setAttribute("userId", userId);
			
		    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpDuyetLenhSanXuat.jsp");
	    		 
	    	}
	    }
	}
	

	private String getSearchQuery(HttpServletRequest request, IErpLenhsanxuatList obj)
	{
		Utility util= new Utility();
		
		String query = 	" SELECT DISTINCT A.NGAYBATDAU , A.PK_SEQ ,isnull(a.diengiai,'') as diengiai ,A.TRANGTHAI, SP.MA AS SPID, SP.TEN AS SPTEN, NV.TEN AS NGUOITAO, "+
						" A.NGAYSUA, A.NGAYTAO, NV2.TEN AS NGUOISUA, ISNULL(A.DONDATHANG_FK, '-1') AS DONDATHANG_FK, "+
						" A.KICHBANSANXUAT_FK , CASE WHEN SP.DVKD_FK=100005 THEN '' ELSE  ISNULL(( "+
						" SELECT TOP 1 D.DIENGIAI FROM ERP_LENHSANXUAT_CONGDOAN_GIAY C INNER JOIN ERP_CONGDOANSANXUAT_GIAY  D ON C.CONGDOAN_FK=D.PK_SEQ "+ 
						" WHERE TINHTRANG=0 AND  "+
						" LENHSANXUAT_FK=A.PK_SEQ ORDER BY THUTU),N'HOÀN TẤT CÁC CÔNG DOẠN') END AS CONGDOAN"+
						" FROM ERP_LENHSANXUAT_GIAY  A "+ 
						" LEFT JOIN ERP_KICHBANSANXUAT_GIAY KB ON KB.PK_SEQ=KICHBANSANXUAT_FK "+
						" inner join ERP_LENHSANXUAT_SANPHAM lsx_sp on lsx_sp.LENHSANXUAT_FK=A.PK_SEQ "+
						" left JOIN ERP_SANPHAM SP ON SP.PK_SEQ= lsx_sp.SANPHAM_FK "+   
						" INNER JOIN NHANVIEN NV ON A.NGUOITAO = NV.PK_SEQ    "+
						" INNER JOIN NHANVIEN NV2 ON A.NGUOISUA = NV2.PK_SEQ  WHERE 1 = 1";
		
		
	 
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
						" where 1=1 ";
		
		
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
		/*	 String query="SELECT PK_SEQ  FROM ERP_LENHSANXUAT_GIAY WHERE PK_SEQ IN (  101000,101001 ,101083)";
			 //String query="SELECT PK_SEQ  FROM ERP_LENHSANXUAT_GIAY WHERE PK_SEQ IN (   101068)";
			 ResultSet rs=db.get(query);
			 while(rs.next()){
				String lsxid=rs.getString("PK_SEQ");
				query="select PK_SEQ from erp_nhapkho where SOLENHSANXUAT="+lsxid;
				ResultSet rscheck=db.get(query);
				if(!rscheck.next()){
					
						query="select PK_SEQ,TRANGTHAI from ERP_CHUYENKHO where TRANGTHAI <>4 AND LENHSANXUAT_FK= "+lsxid;
						ResultSet rsck=db.get(query);
						while(rsck.next()){
							String ckid=rsck.getString("PK_SEQ");
							Huychungtu_Chuyenkho(ckid,rsck.getString("trangthai"));
						}
						rsck.close();
						
				}else{
					System.out.println("Không thể revert pa: "+lsxid);
				}
				
				  query = " update ERP_LENHSANXUAT_GIAY set trangthai = '7' where pk_seq = '" + lsxid + "'";
			  
					if(!db.update(query))
					{
						System.out.println("KHONOG THANH CONG DO BA");
					}
			 }
			 rs.close();*/
			
			 Huychungtu_Chuyenkho ("100447","3");
			 
			 
			 
		}catch(Exception err){
			err.printStackTrace();
		}
	 
   }

	private static void Huychungtu_Chuyenkho(String ckid, String trangthai) {
		// TODO Auto-generated method stub
		try{
			// trạng thái ,1,2,3 là đã xuất kho 
			if(trangthai.equals("0")){
				String msg1=	XoaChuyenKho(ckid);
				System.out.println(msg1);
			}else{
			String msg1=	Revert_ChuyenKho(ckid);	
				System.out.println(msg1);
			}
		}catch(Exception er){
			er.printStackTrace();
		}
	}
	
	private static String Revert_ChuyenKho(String lsxId) 
	{
		String msg = "";
		dbutils db = new dbutils();
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			
			
			//TRỪ KHO
			Utility util = new Utility();
			String query = "  select b.loaidoituong, b.DOITUONG_FK as doituongId, b.NGAYCHUYEN, b.KhoXuat_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, " + 
							" 		a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, a.phieudt, a.phieueo, SUM( a.SoLuong ) as soluong  " + 
							"  from ERP_CHUYENKHO_SANPHAM_CHITIET a inner join ERP_CHUYENKHO b on a.chuyenkho_FK = b.PK_SEQ " + 
							"  where b.PK_SEQ = '" + lsxId + "'  " + 
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

					double soluong = rs.getDouble("soluong");
					
					msg = util.Update_KhoTT(rs.getString("NGAYCHUYEN"), "Cập nhật CK - Tăng kho ngược lại trước khi xóa ", db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
							mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, soluong, 0, soluong);
					if( msg.trim().length() > 0 )
					{
						msg = "Lỗi khi xóa: " + msg;
						db.getConnection().rollback();
						db.shutDown();
						return msg;
					}
					 
				}
				rs.close();
					
	// giam  kho nhan
	
	  query =   " select b.khonhan_fk ,b.loaidoituong, b.DOITUONG_FK as doituongId, b.NGAYCHUYEN, b.KhoXuat_FK, a.SanPham_fk, " +
		  		" a.SoLo, a.NgayHetHan, a.ngaynhapkho, " + 
				" a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.binNhan_fk, 0) as bin_fk, a.phieudt, a.phieueo, SUM( a.SoLuong ) as soluong  " + 
				" from ERP_CHUYENKHO_SANPHAM_CHITIET a inner join ERP_CHUYENKHO b on a.chuyenkho_FK = b.PK_SEQ " + 
				" where b.PK_SEQ = '" + lsxId + "' and b.trangthai=3 " + 
				" group by b.khonhan_fk, b.loaidoituong, b.DOITUONG_FK, b.NGAYCHUYEN, b.KhoXuat_FK, a.SanPham_fk, " +
				" a.SoLo, a.NgayHetHan, a.ngaynhapkho, a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, " +
				" a.binNhan_fk, a.phieudt, a.phieueo ";
	
		  //System.out.println("::: CAP NHAT KHO: " + query);
		    rs = db.get(query);
			while( rs.next() )
			{
				String khoId = rs.getString("khonhan_fk");
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

				double soluong = (-1)* rs.getDouble("soluong");
				
				msg = util.Update_KhoTT(rs.getString("NGAYCHUYEN"), "Hủy CK - Trừ kho ngược lại trước khi xóa ", db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
						mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, soluong, 0, soluong);
				if( msg.trim().length() > 0 )
				{
					msg = "Lỗi khi xóa: " + msg;
					db.getConnection().rollback();
					db.shutDown();
					return msg;
				}
				 
			}
			rs.close();
			
			
		 	query = "update ERP_CHUYENKHO set trangthai = '4' where pk_seq = '" + lsxId + "' ";
			if( !db.update(query) )
			{
				msg = "Lỗi khi xóa: " + query;
				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}
					
			db.getConnection().commit();
			db.shutDown();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			
			msg = "Lỗi khi chốt: " + e.getMessage();
			db.update("rollback");
			db.shutDown();
		}
		
		return msg;
	}


	

	private static String XoaChuyenKho(String lsxId) 
	{
		String msg = "";
		dbutils db = new dbutils();
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_CHUYENKHO set trangthai = '4' where pk_seq = '" + lsxId + "' ";
			if( !db.update(query) )
			{
				msg = "Lỗi khi xóa: " + query;
				db.getConnection().rollback();
				db.shutDown();
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
						db.shutDown();
						return msg;
					}
				}
				rs.close();
			}
			
			db.getConnection().commit();
			db.shutDown();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			
			msg = "Lỗi khi chốt: " + e.getMessage();
			db.update("rollback");
			db.shutDown();
		}
		
		return msg;
	}


	private static String createLsx(String sanphamId, double soluongsp, List<IDanhmucvattu_SP> dmvtList, dbutils db) {
		// TODO Auto-generated method stub
		String  id="";
		try{
			String query=" INSERT INTO ERP_LENHSANXUAT_GIAY (CONGTY_FK,SOLUONG,NHAMAY_FK,TRANGTHAI,NGAYTAO,NGUOITAO,NGAYSUA,NGUOISUA,TINHTRANG,NGAYBATDAU,NGAYDUKIENHT,khosanxuat_fk,diengiai) VALUES "+ 
						 "	(100005,	"+soluongsp+"	,100003	,'7',	'2014-01-01'	,100211,	'2014-01-01'	,100211,	0,	'2014-02-01'	,'2014-02-28',	100018,	'LENHTAOTUDONG')";
			
			if(!db.update(query)){
				System.out.println("Khong The Tao Lenh san xuat"+query);
				return "";
			} 
			query = "select IDENT_CURRENT('ERP_LENHSANXUAT_GIAY') as clId";
			ResultSet rs =  db.get(query);
			rs.next();
			   id = rs.getString("clId");
			rs.close();
			 
					for(int i = 0; i < dmvtList.size(); i++)
				    {
						IDanhmucvattu_SP vattu =  dmvtList.get(i);
						query =     " INSERT INTO ERP_LENHSANXUAT_BOM_GIAY (LENHSANXUAT_FK,CHOPHEPTHAYTHE,VATTU_FK,SOLUONG,CHECKKHO,KHOTT_FK,ISVATTUTHEM,LOAI) VALUES "+
									" ("+id+"	,'0'	,"+vattu.getIdVT()+",	"+vattu.getSoLuong()+",	'1'	,100018	,'1'	,'1')";
						if(!db.update(query))
						{
							System.out.println("Khong The Tao ERP_LENHSANXUAT_BOM_GIAY :" +query);
							return "";
						}
				     }
				
				query="INSERT INTO ERP_LENHSANXUAT_SANPHAM (LENHSANXUAT_FK,SANPHAM_FK,SOLUONG) VALUES ("+id+",	"+sanphamId+"	,"+soluongsp+")";
			 // Tạo nhập kho
				if(!db.update(query))
				{
					System.out.println("Khong The Tao ERP_LENHSANXUAT_BOM_GIAY :" +query);
					return "";
				}
				
				query=  " INSERT INTO ERP_NHAPKHO (NGAYNHAPKHO,NGAYCHOT,SOLENHSANXUAT,NOIDUNGNHAP,KHONHAP,TRANGTHAI,NGUOITAO,NGAYTAO,NGUOISUA,NGAYSUA,congty_fk) "+ 
						" VALUES ('2014-02-28',	'2014-02-28',"+id+"	,100005	,100018,	1,	100211,	2013-04-03,	100211,	2014-03-12	,100005) ";
				
				if(!db.update(query))
				{
					System.out.println("Khong The Tao ERP_LENHSANXUAT_BOM_GIAY :" +query);
					return "";
				}
				
				query = "select IDENT_CURRENT('ERP_NHAPKHO') as clId";
				  rs =  db.get(query);
				  rs.next();
				   String nhapkho_id = rs.getString("clId");
				rs.close();
				query="INSERT INTO ERP_NHAPKHO_SANPHAM (SONHAPKHO_FK,SANPHAM_FK,SOLUONGNHAN,SOLUONGNHAP,SOLO,NGAYHETHAN,NGAYSANXUAT,NGAYNHAPKHO)VALUES "+
					" ("+nhapkho_id+",	"+sanphamId+",	"+soluongsp+"	,"+soluongsp+"	,"+id+",	'2015-01-28',	'2014-02-28'	,'2014-02-28')";
				if(!db.update(query))
				{
					System.out.println("Khong The Tao ERP_LENHSANXUAT_BOM_GIAY :" +query);
					return "";
				}
				// cập nhật tiêu hao nguyên liệu
				
				query=" INSERT INTO ERP_TIEUHAONGUYENLIEU (LENHSANXUAT_FK,TRANGTHAI,NGUOITAO,NGAYTAO,NGUOISUA,NGAYSUA,NGAYTIEUHAO) "+ 
					  " VALUES("+id+"	,1,	100380,	'2014-01-12'	,100380,	'2014-01-12'	,'2014-02-28')";
				if(!db.update(query))
				{
					System.out.println("Khong The Tao ERP_LENHSANXUAT_BOM_GIAY :" +query);
					return "";
				}
				   query = "select IDENT_CURRENT('ERP_TIEUHAONGUYENLIEU') as clId";
				   rs =  db.get(query);
				   rs.next();
				   String tieuhaoid = rs.getString("clId");
				   rs.close();
				   
				   for(int i = 0; i < dmvtList.size(); i++)
				    {
						IDanhmucvattu_SP vattu =  dmvtList.get(i);
						query= " INSERT INTO ERP_LENHSANXUAT_TIEUHAO (TIEUHAONGUYENLIEU_FK,SANPHAM_FK,SOLUONG,DONGIA,THANHTIEN,KHOTT_FK,LOAI)"+
				   		  " VALUES("+tieuhaoid+",	"+vattu.getIdVT()+","+vattu.getSoLuong()+"	,0.00,	0.00	,100018	,1)";
						if(!db.update(query))
						{
							System.out.println("Khong The Tao ERP_LENHSANXUAT_BOM_GIAY :" +query);
							return "";
						}
				     }
				
		}catch(Exception er){
			er.printStackTrace();
		}
		return null;
	}
	
	
}
