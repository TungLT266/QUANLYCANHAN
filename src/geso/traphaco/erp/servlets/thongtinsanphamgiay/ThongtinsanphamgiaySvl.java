package geso.traphaco.erp.servlets.thongtinsanphamgiay;

import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.erp.beans.donmuahang.IErpDonmuahangList;
import geso.traphaco.erp.beans.thongtinsanphamgiay.IThongtinsanphamgiay;
import geso.traphaco.erp.beans.thongtinsanphamgiay.IThongtinsanphamgiayList;
import geso.traphaco.erp.beans.thongtinsanphamgiay.imp.Thongtinsanphamgiay;
import geso.traphaco.erp.beans.thongtinsanphamgiay.imp.ThongtinsanphamgiayList;
import geso.dms.center.util.Utility;
import geso.dms.db.sql.dbutils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.HorizontalAlignmentType;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

 public class ThongtinsanphamgiaySvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
   static final long serialVersionUID = 1L;
   PrintWriter out;
 
   
   private int items = 50;
   private int splittings = 10;
   
   public ThongtinsanphamgiaySvl() {
		super();
	}   
   
   private void settingPage(IThongtinsanphamgiayList obj) {
		Utility util = new Utility();
		if(getServletContext().getInitParameter("items") != null){
	    	String i = getServletContext().getInitParameter("items").trim();
	    	if(util.isNumeric(i))
	    		items = Integer.parseInt(i);
	    }
	    
	    if(getServletContext().getInitParameter("splittings") != null){
	    	String i = getServletContext().getInitParameter("splittings").trim();
	    	if(util.isNumeric(i))
	    		splittings = Integer.parseInt(i);
	    }
	    
	    
	    
	    
	    obj.setItems(items);
	    obj.setSplittings(splittings);
	}
	
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
   {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    
      
	    Utility util = new Utility();
	    out = response.getWriter();
	    
	    IThongtinsanphamgiayList obj = (IThongtinsanphamgiayList)new ThongtinsanphamgiayList();
	    String ctyId = (String)session.getAttribute("congtyId");
	    String ctyTen = (String)session.getAttribute("congtyTen");

	    obj.setCtyId(ctyId);
   		obj.setCtyTen(ctyTen);
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length()==0)
	    	userId = request.getParameter("userId");
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String spId = util.getId(querystring);
	    String msg="";
	    if (action.equals("delete")){	   		  	    	
	    	  msg = Delete(spId);
	    	out.print(spId);
	    	 
	    }
	   	
    	obj.setUserId(userId);
    	settingPage(obj);
		obj.init();
		obj.setMsg(msg);
		
    	session.setAttribute("obj", obj);

		session.setAttribute("userId", userId);
    		
		response.sendRedirect("/TraphacoHYERP/pages/Erp/ThongTinSanPhamGiay.jsp");
	}  	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    OutputStream out = response.getOutputStream();
	    
		HttpSession session = request.getSession();
	    String userId = request.getParameter("userId");
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	        
	    if (action.equals("new"))
	    {
	    	IThongtinsanphamgiay spBean = (IThongtinsanphamgiay) new Thongtinsanphamgiay();	    	
		    String ctyId = (String)session.getAttribute("congtyId");
		    String ctyTen = (String)session.getAttribute("congtyTen");
		    System.out.println("HANG BO = " + spBean.getHangbo());
		    spBean.setCtyId(ctyId);
		    spBean.setCtyTen(ctyTen);

	    	spBean.setUserId(userId);
	    	spBean.CreateRS();
	    	
	    	// Save Data into session
	    	session.setAttribute("userId", userId);
	    	session.setAttribute("spBean", spBean);
	    	session.setAttribute("loadLanDau", "1");
    		
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ThongTinSanPhamGiayNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    
	    IThongtinsanphamgiayList obj = (IThongtinsanphamgiayList)new ThongtinsanphamgiayList();
	    settingPage(obj);
	    if (action.equals("search"))
	    {
		    String ctyId = (String)session.getAttribute("congtyId");
		    String ctyTen = (String)session.getAttribute("congtyTen");

		    obj.setCtyId(ctyId);
	   		obj.setCtyTen(ctyTen);

	    	obj.setQuery(getSearchQuery(request, obj));
			obj.setUserId(userId);
			obj.init();
			
	    	session.setAttribute("obj", obj);

    		session.setAttribute("userId", userId);
	    		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ThongTinSanPhamGiay.jsp");	    	
	    	
	    }
	    else if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    {
		    String ctyId = (String)session.getAttribute("congtyId");
		    String ctyTen = (String)session.getAttribute("congtyTen");

		    obj.setCtyId(ctyId);
	   		obj.setCtyTen(ctyTen);

	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
	    	obj.setUserId(userId);
	    	obj.setQuery(search);
	    	obj.init();
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	session.setAttribute("obj", obj);
	    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ThongTinSanPhamGiay.jsp");
	    } 
	    else if (action.equals("excel"))
	    {
		    String ctyId = (String)session.getAttribute("congtyId");
		    String ctyTen = (String)session.getAttribute("congtyTen");
	    	obj = (IThongtinsanphamgiayList) new ThongtinsanphamgiayList();	    	
		    obj.setCtyId(ctyId);
	   		obj.setCtyTen(ctyTen);	    	
	    	obj.setQuery(getSearchQuery(request, obj));
	    	
	    	/*try
		    {
		    	response.setContentType("application/vnd.ms-excel");
		        response.setHeader("Content-Disposition", "attachment; filename=DanhSachSanPham.xls");
		        
		        Workbook workbook = new Workbook();
		    	
			     CreateStaticHeader(workbook, (String) session.getAttribute("userTen"));
			     CreateStaticData(workbook, getSearchQuery2(request, "", "", obj));
			
			     //Saving the Excel file
			     workbook.save(out);
		    }
		    catch (Exception ex)
		    {
		    	ex.printStackTrace();
		        obj.setMsg("Khong the tao pivot.");
		    }*/
	    	
	    	try {
	    		response.setContentType("application/vnd.ms-excel");
		        response.setHeader("Content-Disposition", "attachment; filename=DanhSachSanPham.xls");
	    		CreateExcel(out, getSearchQuery2(request, "", "", obj), (String) session.getAttribute("userTen")) ;
			} catch (Exception e) {
				e.printStackTrace();
				obj.setMsg("Không thể tạo báo cáo - " + e.getMessage());
			}
			obj.setUserId(userId);
			//obj.init();			
	    	//session.setAttribute("obj", obj);
    		//session.setAttribute("userId", userId);	
    		//response.sendRedirect("/TraphacoHYERP/pages/Erp/ThongTinSanPhamGiay.jsp");	    		
	    }
	    
	    
	    
	
	}   
		
	public String getSearchQuery(HttpServletRequest request, IThongtinsanphamgiayList obj)
	{
		Utility util= new Utility();
		
		if(request != null)
		{
			String masp = request.getParameter("masp");
	    	if (masp == null)
	    		masp = "";
	    	obj.setMasp(masp);
	    	
	    	String tennoibo = request.getParameter("tennoibo");
	    	if (tennoibo == null)
	    		tennoibo = "";
	    	obj.setTennoibo(tennoibo);
	    	
			String tensp = request.getParameter("tensp");
	    	if (tensp == null)
	    		tensp = "";
	    	obj.setTensp(tensp);
	    	
	    	String dvkdId = request.getParameter("dvkdId");
	    	if (dvkdId == null)
	    		dvkdId = "";    	
	    	obj.setDvkdId(dvkdId);
	    	
	    	String nhId = request.getParameter("nhId");
	    	if (nhId == null)
	    		nhId = "";    	
	    	obj.setNhId(nhId);
	    	
	    	String clId = request.getParameter("clId");
	    	if (clId == null)
	    		clId = "";    	
	    	obj.setClId(clId);
	    	
	    	String lspId = request.getParameter("lspId");
	    	if (lspId == null)
	    		lspId = "";    	
	    	obj.setLspId(lspId);
	    	
	    	String trangthai = request.getParameter("trangthai");   	
	    	if (trangthai == null)
	    		trangthai = "";    	
	    	
	    	obj.setTrangthai(trangthai);
		}
    	
     	String query =  " select ROW_NUMBER() OVER(ORDER BY a.ma DESC) AS 'stt', a.pk_seq, a.ma, a.ten1 as tennoibo, a.ten, lsp.ten as lspten, a.trongluong, a.thetich, b.donvikinhdoanh  as dvkd,b.pk_seq as dvkdId,c.ten as chungloai, e.pk_seq as nhId, d.donvi,e.ten as nhanhang,d.pk_seq as clId,a.trangthai ,"+
     					" \n a.ngaytao as ngaytao, a.ngaysua as ngaysua, isnull(nt.ten,'') as nguoitao, isnull(ns.ten,'')  as nguoisua  "+
     					"\n from erp_sanpham a left join erp_loaisanpham lsp on lsp.pk_seq = a.loaisanpham_fk left join donvikinhdoanh b on a.dvkd_fk = b.pk_seq left join chungloai c on a.chungloai_fk = c.pk_seq  " +
     					"\n left join donvidoluong d on a.dvdl_fk = d.pk_seq left join nhanhang e on a.nhanhang_fk = e.pk_seq " +
     					"\n LEFT JOIN NHANVIEN nt \n"+
    					"\n on nt.PK_SEQ=a.NGUOITAO " + " LEFT JOIN NHANVIEN ns " + " on ns.PK_SEQ=a.NGUOISUA  "+
						"\n where a.pk_seq > 0 and a.congty_fk = " + obj.getCtyId() + " ";
		  
     	
     	
    	if (obj.getMasp().length()>0){
			query = query + "\n and upper(a.ma) like upper(N'%" + obj.getMasp() + "%')";	
    	}
    	
    	if (obj.getTennoibo().length()>0){
			query = query + "\n and a.timkiem like upper(N'%"+ util.replaceAEIOU(obj.getTennoibo().trim()).trim()+"%')";	
    	}

	    if (obj.getTensp().length()>0){
			query = query + "\n and a.timkiem like upper(N'%"+ util.replaceAEIOU(obj.getTensp().trim()).trim()+"%')";	
    	}
    	
    	if (obj.getDvkdId().length()>0){
			query = query + "\n and b.pk_seq = '" + obj.getDvkdId() + "'";	
    	}

    	/*if (obj.getNhId().length()>0){
			query = query + "\n and e.pk_seq = '" + obj.getNhId() + "'";   		
    	}
    	
    	if (obj.getClId().length()>0){
			query = query + "\n and c.pk_seq = '" + obj.getClId() + "'";    		
    	}*/
    	
    	if (obj.getLspId().length()>0){
			query = query + "\n and a.loaisanpham_fk = '" + obj.getLspId() + "'";    		
    	}

    	if(obj.getTrangthai().length() > 0){
    		query = query + "\n and a.trangthai = '" + obj.getTrangthai() + "'";   		
    	}
  
    	System.out.println(" seach : "+ query );
    	return query;
    	 
	}	
	
	
	String  kiemtra(String sql,String id,dbutils db,String chuoi)
	{
		 
		String query = "select count(*) as num from " + sql + " where sanpham_fk ='"+ id +"'";
	
    	ResultSet rs = db.get(query);
		try 
		{	
			//kiem tra ben san pham
			while(rs.next())
			{ 
				if(rs.getString("num").equals("0"))
					return "";
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		str_chuoi=chuoi;
		return chuoi;
	}
	String str_chuoi="";
	private String  Delete(String id){
		dbutils db = new dbutils();
		String query="";
		try {
 
			if(kiemtra("nhaphang_sp",id,db,"Nhận hàng").length()>0 ||kiemtra("donhangtrave_sanpham",id,db,"Đơn hàng trả về").length()>0 || kiemtra("dieukienkm_sanpham",id,db,"Điều kiện khuyến mãi").length()>0 || 
					kiemtra("phieuxuatkho_spkm",id,db,"Phiếu xuất kho").length()>0 || kiemtra("bosanpham_sanpham",id,db,"Bó sản phẩm").length()>0 || kiemtra("trakhuyenmai_sanpham",id,db,"Trả khuyến mãi").length()>0||
					kiemtra("donhangthuhoi_sanpham",id,db,"Đơn hàng thu hồi").length()>0||kiemtra("phieuxuatkho_sanpham",id,db,"Phiếu xuất kho").length()>0 || kiemtra("denghidathang_sp",id,db,"Đề nghị đặt hàng").length()>0 ||
					kiemtra("phieuthuhoi_sanpham",id,db,"Phiếu thu hồi").length()>0 || kiemtra("donhang_sanpham",id,db,"Đơn hàng").length()>0 || kiemtra("dieuchinhtonkho_sp",id,db,"Điều chỉnh tồn kho").length()>0 || 
					kiemtra("dontrahang_sp",id,db,"Đơn trả hàng").length()>0 || kiemtra("bosanpham_sanpham",id,db,"Bó sản phẩm").length()>0 ||kiemtra("phieuxuatkho_spkm",id,db,"Phiếu xuất kho").length()>0||
					kiemtra("ERP_MUAHANG_SP",id,db,"Mua hàng").length()>0 ||kiemtra("ERP_DIEUCHINHTONKHOTT_SANPHAM",id,db,"Điều chỉnh tồn kho").length()>0||
					kiemtra("ERP_XUATKHO_SANPHAM",id,db,"Xuất kho").length()>0 ||  kiemtra("ERP_dondathang_sp",id,db,"Đơn hàng bán").length()>0 ||  kiemtra("ERP_XUATKHO_SP_CHITIET",id,db,"Xuất kho").length()>0  ||  kiemtra("ERP_NHAPKHO_SANPHAM",id,db,"Nhập kho").length()>0 ||
					kiemtra("ERP_HOADON_SP",id,db,"Hóa đơn").length()>0||kiemtra("ERP_CHUYENKHO_SANPHAM",id,db,"Chuyển kho").length()>0|| kiemtra("ERP_YEUCAUCHUYENKHO_SANPHAM",id,db,"Yêu cầu chuyển kho").length()>0  
					|| kiemtra("ERP_XUATDOIQUYCACH_CHITIET",id,db,"Xuất đổi quy cách").length()>0  || kiemtra("ERP_TONKHOTHANG",id,db,"Tồn kho đầu kỳ").length()>0   || kiemtra("ERP_NHANDOIQUYCACH",id,db,"Nhận đổi quy cách").length()>0  )
			{
				 
				return    "Sản phẩm này đã phát sinh dữ liệu vui lòng kiểm tra lại dữ liệu: "+str_chuoi; 		
	           
			}
			else
			{	
				
				
				query="select * from erp_khott_sanpham where ( available >0 or soluong >0 ) and sanpham_fk= "+ id;
				
				ResultSet rscheck=db.get(query);
				if(rscheck.next()){
					 
					return  "Sản phẩm đã có tồn kho vui lòng kiểm tra lại";
					
				}
				
				query="select * from ERP_DANHMUCVATTU_VATTU where VATTU_FK= "+ id;
				rscheck=db.get(query);
				if(rscheck.next()){
					 
					return  "Sản phẩm đã có trong BOM  vui lòng kiểm tra lại";
				}
				
				query="select * from ERP_DANHMUCVATTU  where MAVATTU in  (select ma from ERP_SANPHAM where PK_SEQ="+id+")";
				rscheck=db.get(query);
				if(rscheck.next()){
					 
					return  "Sản phẩm đã có trong BOM vui lòng kiểm tra lại";
				}
				rscheck.close();
				db.getConnection().setAutoCommit(false);
				
				query="delete from nhomsanpham_sanpham where sp_fk='" + id + "'";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return  "Không thể xóa dòng lệnh :" +query;
					
				 
				}
				query="delete from quycach where sanpham_fk='" + id + "'";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return  "Không thể xóa dòng lệnh :" +query; 
					 
				}
				query="delete from banggiablc_sanpham where sanpham_fk='" + id + "'";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return  "Không thể xóa dòng lệnh :" +query;
				}
				query="delete from bgbanlenpp_sanpham where sanpham_fk='" + id + "'";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return  "Không thể xóa dòng lệnh :" +query;
				}
				query="delete from bgmuanpp_sanpham where sanpham_fk='" + id + "'";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return  "Không thể xóa dòng lệnh :" +query;
				}
				query="delete from banggiast_sanpham where sanpham_fk='" + id + "'";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return  "Không thể xóa dòng lệnh :" +query;
				}
				query="delete from nhapp_kho where sanpham_fk='" + id + "'";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return  "Không thể xóa dòng lệnh :" +query;
				}
				
				query="delete from erp_khott_Sanpham where sanpham_fk='" + id + "'";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return  "Không thể xóa dòng lệnh :" +query;
				}
				query="delete from erp_khott_Sanpham where sanpham_fk='" + id + "'";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return  "Không thể xóa dòng lệnh :" +query;
				}
				
				query="delete from DONHANG_SANPHAM where SANPHAM_FK='" + id + "'";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return  "Không thể xóa dòng lệnh :" +query;
				}
				
				query="delete from NHANVIEN_SANPHAM where SANPHAM_FK='" + id + "'";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return  "Không thể xóa dòng lệnh :" +query;
				}
				
				query="delete from ERP_BGBAN_SANPHAM where sanpham_fk ='" + id + "'";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return  "Không thể xóa dòng lệnh :" +query;
				}
				query="delete from ERP_SANPHAM_NHACUNGCAP where sanpham_fk ='" + id + "'";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return  "Không thể xóa dòng lệnh :" +query;
				}
				query="delete from erp_sanpham where pk_seq='" + id + "'";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					return  "Không thể xóa dòng lệnh :" +query;
				}
				
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
		} catch (Exception e) 
		{
			db.update("rollback");
			db.shutDown();
			e.printStackTrace();
			return e.getMessage();
			
		}
		return "";
			
	}
	
	

	private void CreateExcel(OutputStream out, String query, String userTen) throws Exception {
		
		try {
			FileInputStream fstream = null;
			Workbook workbook = new Workbook();
			fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\DanhSachSanPham.xlsm");

			
			dbutils db = new dbutils();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			Font font = new Font();
			
			worksheet.setName("DanhSachSanPham");
			
			Cells cells = worksheet.getCells();
			
			//dien thong tin 
			cells.setRowHeight(3, 18.0f);
			Cell cell = cells.getCell("B3");
			ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, getDateTime() );
			
			cells.setRowHeight(3, 18.0f);
			cell = cells.getCell("B4");
			ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, userTen  );
			
			
			//--do du lieu
			System.out.println(" danh sach san pham : "+ query);
			ResultSet rs=db.get(query);
			ResultSetMetaData rsmd=rs.getMetaData();;
			int socottrongSql=rsmd.getColumnCount();
			System.out.println(" so dong : "+socottrongSql) ;
			
			int countRow = 6;
			int column = 0;			
			try {
				while (rs.next()) {
					cell = null;
					Style style = null;
					
					for(int i =1;i <=socottrongSql ; i ++)
					{

						cell = cells.getCell(countRow,column + i-1 );
						cell.setValue(rs.getString(i));
						if(i==10){
							setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED,false);
						} else{
							setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,false);
						}							
					}
					
					
					//chen quy cach
					String sql=" select qc.SOLUONG1 as sl1, ISNULL( ( select donvi from donvidoluong where PK_SEQ= qc.DVDL1_FK),'') as dv1,  "+    
					 "   qc.SOLUONG2 as sl2, ISNULL( ( select donvi from donvidoluong where PK_SEQ= qc.DVDL2_FK),'') as dv2    "+    
					 "   from QUYCACH qc  where qc.SANPHAM_FK="+ rs.getString(2);	 
					
					String quycach="";
					System.out.println(" quy cach: "+ sql);
					ResultSet rsqc=db.get(sql);
					if(rsqc!=null){
						try {
							while(rsqc.next()){
								String sl1=rsqc.getString("sl1");
								String dv1=rsqc.getString("dv1") ;
								String sl2=rsqc.getString("sl2");
								String dv2=rsqc.getString("dv2") ;
								
								quycach+= sl1 +" " + dv1 +" = "+ sl2 +" " + dv2 +" / " ;
							}
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					if(quycach.length()>0){
						quycach= quycach.substring(0,quycach.length()-2);
					}
					System.out.println(" quy cach: "+ quycach);
					cell = cells.getCell(countRow,11 );
					cell.setValue(quycach);
					setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,false);
					
					
					
					for(int i =12;i <=socottrongSql ; i ++)
					{
						cell = cells.getCell(countRow,column + i );
						cell.setValue(rs.getString(i));
						if(i==10){
							setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED,false);
						} else{
							setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,false);
						}							
					}

					countRow++;
				}
				rs.close();	
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			//---
			workbook.save(out);
			fstream.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}	
	}

	
	private void setCellBorderStyle(Cell cell, short align,boolean kt) {
		Style style = cell.getStyle();
		style.setHAlignment(align);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		if(kt)
		{
			Font font2 = new Font(); 
			font2.setName("Calibri");
			font2.setColor(Color.RED);
			style.setFont(font2);
			
		}
		
		style.setColor(Color.WHITE);
		
		cell.setStyle(style);
	}

	
	
	
	private void CreateStaticHeader(Workbook workbook, String UserName) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	   	    
	    Cell cell = cells.getCell("A1"); 
	    cell.setValue("DANH SÁCH SẢN PHẨM");
	   
	    cell = cells.getCell("A3");
	    cell.setValue("Ngày: " + this.getDateTime());
	    cell = cells.getCell("A4");
	    cell.setValue("User:  " + UserName);
 
	    cell = cells.getCell("A8");
	    cell.setValue("Đơn vị kinh doanh");
	    cell = cells.getCell("B8");
	    cell.setValue("Nhãn hàng");
	    cell = cells.getCell("C8");
	    cell.setValue("Chủng loại");
	    cell = cells.getCell("D8");
	  
	    cell.setValue("ID Sản phẩm");
	    cell = cells.getCell("E8");
	    cell.setValue("Mã sản phẩm");
	    cell = cells.getCell("F8");
	    cell.setValue("Mã chi tiết");
	    
	    cell = cells.getCell("G8");
	    cell.setValue("Tên sản phẩm");
	    cell = cells.getCell("H8");
	    cell.setValue("Tên 1");
	    cell = cells.getCell("I8");
	    cell.setValue("Tên 2");
	    
	    cell = cells.getCell("J8");
	    cell.setValue("Đơn vị đo lường");
	 
 
	    cell = cells.getCell("K8");
	    cell.setValue("Trọng lượng");
	    
	    cell = cells.getCell("L8");
	    cell.setValue("Thể tích");
	    
	    cell = cells.getCell("M8");
	    cell.setValue("Quy cách");
	    
	    cell = cells.getCell("N8");
	    cell.setValue("Dài");
	    
	    cell = cells.getCell("O8");
	    cell.setValue("DVDL Dài");
	    
	    cell = cells.getCell("P8");
	    cell.setValue("Rộng");
	    
	    cell = cells.getCell("Q8");
	    cell.setValue("DVDL Rộng");
	    
	    cell = cells.getCell("R8");
	    cell.setValue("Định lượng");
	    
	    cell = cells.getCell("S8");
	    cell.setValue("DVDL Định lượng");
	    
	    cell = cells.getCell("T8");
	    cell.setValue("Màu");
	    
	    cell = cells.getCell("U8");
	    cell.setValue("Độ dày");
	    
	    cell = cells.getCell("V8");
	    cell.setValue("DVDL Độ dày");
	    
	    cell = cells.getCell("W8");
	    cell.setValue("Đường kính trong");
	    
	    cell = cells.getCell("X8");
	    cell.setValue("DVDL Đường kính trong");
	    
	    cell = cells.getCell("Y8");
	    cell.setValue("Logo");
	    
	    cell = cells.getCell("Z8");
	    cell.setValue("Mẫu in");
	    
	    cell = cells.getCell("AA8");
	    cell.setValue("Đầu lớn");
	    
	    cell = cells.getCell("AB8");
	    cell.setValue("DVDL Đầu lớn");
	    
	    
	    cell = cells.getCell("AC8");
	    cell.setValue("Đầu nhỏ");
	    
	    cell = cells.getCell("AD8");
	    cell.setValue("DVDL Đầu nhỏ");
	    
	    cell = cells.getCell("AE8");
	    cell.setValue("Dài đáy");
	    
	    cell = cells.getCell("AF8");
	    cell.setValue("DVDL Dài đáy");
	    
	    cell = cells.getCell("AG8");
	    cell.setValue("Chuẩn nén");
	    
	    cell = cells.getCell("AH8");
	    cell.setValue("Độ dài");
 
	    
	    cell = cells.getCell("AI8");
	    cell.setValue("Trạng thái");
	  
	    worksheet.setName("Danh sách  sản phẩm");
	}
	
	private void CreateStaticData(Workbook workbook, String query) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    
		dbutils db = new dbutils();
		ResultSet rs = db.get(query);
		System.out.println("Get san pham :"+query);
		int i = 9;
		if(rs != null)
		{
			try 
			{
				cells.setColumnWidth(0, 25.0f);
				cells.setColumnWidth(1, 15.0f);
				cells.setColumnWidth(2, 15.0f);
				cells.setColumnWidth(3, 30.0f);
				cells.setColumnWidth(4, 45.0f);
				cells.setColumnWidth(5, 25.0f);
				cells.setColumnWidth(6, 15.0f);
				cells.setColumnWidth(7, 15.0f);
				cells.setColumnWidth(8, 15.0f);
				cells.setColumnWidth(9, 15.0f);
				cells.setColumnWidth(10, 15.0f);
				for(int j = 0; j < 11; j++)
					cells.setRowHeight(j, 13.0f);
				
				Cell cell = null;
				while(rs.next())
				{
					String dvkd = "";
				 
						dvkd = rs.getString("dvkd");
					
					String nhanhang = "";
					 
						nhanhang = rs.getString("nhanhang");
					
					String chungloai = "";
				 
						chungloai = rs.getString("chungloai");
						
					String masp = "";
					 
						masp = rs.getString("ma");
					
					String tensp = "";
					if(rs.getString("ten") != null)
						tensp = rs.getString("ten");
					
					String dvdl = "";
					 
						dvdl = rs.getString("donvi");
					
				 
					String quycach = "";
			 
						quycach = rs.getString("quycach");
					String trangthai="Hoạt động";
					if(rs.getString("trangthai").equals("0")){
						trangthai="Không hoạt động";
					}
					
					NumberFormat formatTheTich = new DecimalFormat("#,###,##0.00000"); 
					NumberFormat formatKhoiLuong = new DecimalFormat("#,###,##0.000"); 
					 
					String trongluong="";
					if(rs.getString("trongluong")!=null)
						trongluong=formatKhoiLuong.format((rs.getDouble("trongluong")));
						
					String thetich="";
					if(rs.getString("thetich")!=null)
						thetich=formatTheTich.format((rs.getDouble("thetich")));
				
					
					
					cell = cells.getCell("A" + Integer.toString(i));
					cell.setValue(dvkd);
					
					cell = cells.getCell("B" + Integer.toString(i));
					cell.setValue(nhanhang);
					
					cell = cells.getCell("C" + Integer.toString(i));
					cell.setValue(chungloai);
					
					cell = cells.getCell("D" + Integer.toString(i));
					cell.setValue(rs.getString("spid"));
					
					cell = cells.getCell("E" + Integer.toString(i));
					cell.setValue(masp);
					
					cell = cells.getCell("F" + Integer.toString(i));
					cell.setValue(rs.getString("MA"));
					
					cell = cells.getCell("G" + Integer.toString(i));
					cell.setValue(tensp);
					
					cell = cells.getCell("H" + Integer.toString(i));
					cell.setValue(rs.getString("ten1"));
					
					cell = cells.getCell("I" + Integer.toString(i));
					cell.setValue(rs.getString("ten2"));
					
					cell = cells.getCell("J" + Integer.toString(i));
					cell.setValue(dvdl);
					
					 
					cell = cells.getCell("K" + Integer.toString(i));
					cell.setValue(trongluong);
					//The tich
					cell = cells.getCell("L" + Integer.toString(i));
					cell.setValue(thetich);
					
					cell = cells.getCell("M" + Integer.toString(i));
					cell.setValue(quycach);
					
				
					cell = cells.getCell("N" + Integer.toString(i));
					cell.setValue(rs.getString("dai"));
					
					cell = cells.getCell("O" + Integer.toString(i));
					cell.setValue(rs.getString("dvdl_dai"));
					
					cell = cells.getCell("P" + Integer.toString(i));
					cell.setValue(rs.getString("rong"));
					
					cell = cells.getCell("Q" + Integer.toString(i));
					cell.setValue(rs.getString("dvdl_rong"));

					cell = cells.getCell("R" + Integer.toString(i));
					cell.setValue(rs.getString("dinhluong"));
					
					cell = cells.getCell("S" + Integer.toString(i));
					cell.setValue(rs.getString("dvdl_dinhluong"));
					
					cell = cells.getCell("T" + Integer.toString(i));
					cell.setValue(rs.getString("mau"));
					
					cell = cells.getCell("U" + Integer.toString(i));
					cell.setValue(rs.getString("doday"));
					
				 
					cell = cells.getCell("V" + Integer.toString(i));
					cell.setValue(rs.getString("dvdl_doday"));
					
					cell = cells.getCell("W" + Integer.toString(i));
					cell.setValue(rs.getString("duongkinhtrong"));
					
					cell = cells.getCell("X" + Integer.toString(i));
					cell.setValue(rs.getString("dvdl_dktrong"));
					
					cell = cells.getCell("Y" + Integer.toString(i));
					cell.setValue(rs.getString("logo"));
					
					cell = cells.getCell("Z" + Integer.toString(i));
					cell.setValue(rs.getString("mauin"));
					
					cell = cells.getCell("AA" + Integer.toString(i));
					cell.setValue(rs.getString("daulon"));
					
					cell = cells.getCell("AB" + Integer.toString(i));
					cell.setValue(rs.getString("dvdl_daulon"));
					
					cell = cells.getCell("AC" + Integer.toString(i));
					cell.setValue(rs.getString("daunho"));
					
					cell = cells.getCell("AD" + Integer.toString(i));
					cell.setValue(rs.getString("dvdl_daunho"));
					
					cell = cells.getCell("AE" + Integer.toString(i));
					cell.setValue(rs.getString("daiday"));
					
					cell = cells.getCell("AF" + Integer.toString(i));
					cell.setValue(rs.getString("dvdl_daiday"));
					
					cell = cells.getCell("AG" + Integer.toString(i));
					cell.setValue(rs.getString("chuannen"));
					
					cell = cells.getCell("AH" + Integer.toString(i));
					cell.setValue(rs.getString("dodai"));
				 
					cell = cells.getCell("AI" + Integer.toString(i));
					cell.setValue(trangthai);
					i++;
				}
				rs.close();
			}
			catch (Exception e){ e.printStackTrace(); }
		}
	 
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private String getSearchQuery2(HttpServletRequest request, String pages, String soDong, IThongtinsanphamgiayList obj)
	{
		Utility util= new Utility();
		
		String masp = request.getParameter("masp");
    	if (masp == null)
    		masp = "";
    	obj.setMasp(masp);

		String tensp = request.getParameter("tensp");
    	if (tensp == null)
    		tensp = "";
    	obj.setTensp(tensp);
    	
    	String dvkdId = request.getParameter("dvkdId");
    	if (dvkdId == null)
    		dvkdId = "";    	
    	obj.setDvkdId(dvkdId);
    	
    	/*String nhId = request.getParameter("nhId");
    	if (nhId == null)
    		nhId = "";    	
    	obj.setNhId(nhId);
    	
    	String clId = request.getParameter("clId");
    	if (clId == null)
    		clId = "";    	
    	obj.setClId(clId);*/
    	
    	String lspId = request.getParameter("loaispid");
    	if (lspId == null)
    		lspId = "";    	
    	obj.setloaisanphamid(lspId);
    	
    	String trangthai = request.getParameter("trangthai");   	
    	if (trangthai == null)
    		trangthai = "";    	
	
    	if (trangthai.equals("2"))
    		trangthai = "";
    	
    	obj.setTrangthai(trangthai);

     
    	
    	/*String query =  "\n 	 select  a.pk_seq as spid , isnull(a.MA,'') as MA , isnull(a.QuyCach, '') as quycach,  a.ma, isnull (a.trongluong,0 ) as trongluong  , " +
    					"\n 	 a.thetich, a.ten, isnull(a.ten1,'') as ten1 ,isnull(a.ten2,'')  as ten2 , a.trangthai, b.ten as lspTen  " + 
					 	"\n	 ,dvkd.donvikinhdoanh as dvkd,b.pk_seq as dvkdId, isnull(c.ten,'') as chungloai, e.pk_seq as nhId,isnull( d.donvi,'') as donvi, " +
					 	"\n 	 isnull( e.ten,'')  as nhanhang, d.pk_seq as clId ," +
						"\n    a.dai,a.dvdl_dai,a.rong,a.dvdl_rong,a.dinhluong,a.dvdl_dinhluong,a.mau,a.doday,a.dvdl_doday,a.duongkinhtrong, "+
						"\n    a.dvdl_dktrong,a.logo,a.mauin,a.daulon,a.dvdl_daulon,a.daunho,a.dvdl_daunho,a.daiday,a.dvdl_daiday,a.chuannen,a.dodai" + 
						"\n 	 from ERP_SANPHAM a left join ERP_LOAISANPHAM b on a.loaisanpham_fk = b.pk_seq " +
						"\n	 left join donvikinhdoanh dvkd on a.dvkd_fk = dvkd.pk_seq " +
						"\n	 left join chungloai c on a.chungloai_fk = c.pk_seq " +
						"\n	 left join donvidoluong d on a.dvdl_fk = d.pk_seq " +
						"\n	 left join nhanhang e on a.nhanhang_fk = e.pk_seq " +
						"\n 	 where a.congty_fk = '" + obj.getCtyId() + "' ";*/
    	
				    	String query = " \n select  'Traphaco' as donvikinhdoanh, a.pk_seq as spid, b.ten as loaisp,   \n "+    
				    	 "  case when a.loaihanghoa = '0' then  N'Mua bên ngoài'   \n "+     
				    	 "  when a.loaihanghoa = '1' then  N'Sản xuất'  \n "+      
				    	 "  when a.loaihanghoa = '1' then  N'Gia công' else '' end as loaihanghoa, \n "+    
				    	 "  isnull(a.ma,'') as masp, isnull(a.ten1,'')  as tennoibo,isnull(a.ten,'')  as tensp,isnull(a.TENTHUONGMAI,'')  as tenthuongmaisp, \n "+      
				    	 "  isnull (f.TEN ,'') as dangbaoche, isnull(d.donvi,'') as donvi, ISNULL (a.thuexuat,0) as thuexuat,  \n "+        
				    	 "  case when a.kiemtradinhluong = '1' then '1' else '' end as kiemtradinhluong, \n "+    
				    	 "  case when a.KIEMTRAOE = '1' then '1' else '' end as kiemtraoe, \n "+    
				    	 "  case  when a.trangthai = '1' then  N'Hoạt động' else  N'Ngưng hoạt động'  end as trangthai    \n "+     
				    	 "  from ERP_SANPHAM a left join ERP_LOAISANPHAM b on a.loaisanpham_fk = b.pk_seq   \n "+     
				    	 "  left join donvidoluong d on a.dvdl_fk = d.pk_seq   \n "+     
				    	 "  left join nhanhang e on a.nhanhang_fk = e.pk_seq   \n "+     
				    	 "  left join dangbaoche f on a.dangbaoche=CAST( f.pk_seq  as nvarchar (18) )  \n "+  
				    	 "   where a.congty_fk = '" + obj.getCtyId() + "' ";
    	
    	
		    
/*    	if (masp.length()>0){
			query = query + " and upper(a.ma) like upper('%" + masp + "%')";	
    	}

	    if (tensp.length()>0){
	    	   Utility util = new Utility();
			query = query + " and upper(a.ten) like upper(N'%"+ util.replaceAEIOU(tensp) + "%')";	
    	}
	    
	    if (masp.length()>0){
			query = query + " and upper(a.ma) like upper('%" + masp + "%')";	
    	}
	    
	    if(lspId.length() > 0)
	    {
	    	query = query + " and a.loaisanpham_fk = '" + lspId + "' ";	
	    }
    	
		if (dvkdId.length()>0)
    	{
			query = query + " and a.dvkd_fk = '" + dvkdId + "'";	
    	}

    	if(trangthai.length() > 0){
    		query = query + " and a.trangthai = '" + trangthai + "'";   		
    	}*/
    	
    	
    	if (obj.getMasp().length()>0){
			query = query + "\n and upper(a.ma) like upper(N'%" + obj.getMasp() + "%')";	
    	}
    	
    	if (obj.getTennoibo().length()>0){
			query = query + "\n and a.timkiem like upper(N'%"+ util.replaceAEIOU(obj.getTennoibo().trim()).trim()+"%')";	
    	}

	    if (obj.getTensp().length()>0){
			query = query + "\n and a.timkiem like upper(N'%"+ util.replaceAEIOU(obj.getTensp().trim()).trim()+"%')";	
    	}
    	
    	if (obj.getDvkdId().length()>0){
			query = query + "\n and b.pk_seq = '" + obj.getDvkdId() + "'";	
    	}

    
    	
    	if (obj.getLspId().length()>0){
			query = query + "\n and a.loaisanpham_fk = '" + obj.getLspId() + "'";    		
    	}

    	if(obj.getTrangthai().length() > 0){
    		query = query + "\n and a.trangthai = '" + obj.getTrangthai() + "'";   		
    	}
  

    	return query;
    	
	}	
	 
}