package geso.traphaco.distributor.servlets.thutienNPP;

import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.distributor.beans.thutienNPP.IErpThutienNPP;
import geso.traphaco.distributor.beans.thutienNPP.IErpThutienNPPList;
import geso.traphaco.distributor.beans.thutienNPP.imp.ErpThutienNPP;
import geso.traphaco.distributor.beans.thutienNPP.imp.ErpThutienNPPList;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class ErpThutienNPPSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpThutienNPPSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpThutienNPPList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    
	    String tthdId = util.getId(querystring);
	    
	    obj = new ErpThutienNPPList();
	    obj.setCtyId(ctyId);

	    
	    if (action.equals("delete"))
	    {	
	    	String msg = Delete(tthdId);
	    	if(msg.length() > 0)
	    		obj.setmsg(msg);
	    }
	    else
	    {
	    	if(action.equals("chot"))
	    	{
	    		IErpThutienNPP tthd = new ErpThutienNPP(tthdId);
	    		if(!tthd.chotTTHD(userId))
	    		{
	    			obj.setmsg(tthd.getMsg());
	    		}
	    	}
	    }
	    
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpThuTienNPP.jsp";
		response.sendRedirect(nextJSP);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpThutienNPPList obj;
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
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpThutienNPP tthdBean = new ErpThutienNPP();
	    	tthdBean.setCtyId(ctyId);
	    	tthdBean.setUserId(userId);
	    	tthdBean.setCtyId(ctyId);
	    	//tthdBean.createRs();
	    	tthdBean.createRsLoc();
    		
	    	session.setAttribute("tthdBean", tthdBean);

    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpThuTienNPPNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ErpThutienNPPList();
	    		obj.setCtyId(ctyId);
		    	String search = getSearchQuery(request, obj);
		    	
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setUserId(userId);
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	response.sendRedirect("/TraphacoHYERP/pages/Distributor/ErpThuTienNPP.jsp");	
		    }
	    	else if (action.equals("excel"))
	 	    {
	    		obj = (IErpThutienNPPList) new ErpThutienNPPList();	 	    	
	 		    obj.setCtyId(ctyId);	 		    
	 		   // String search = getSearchQuery(request, obj);
	 	    	
	 	    	try
	 		    {
	 		    	response.setContentType("application/vnd.ms-excel");
	 		        response.setHeader("Content-Disposition", "attachment; filename=ThuTien.xls");
	 		        
	 		        Workbook workbook = new Workbook();
	 		    	
	 			     CreateStaticHeader(workbook, (String) session.getAttribute("userTen"));
	 			     CreateStaticData(workbook, getQueryExcelThuTien(request, "", "", obj) ,  getQueryExcelThuTienHoaDon(request, "", "", obj));
	 			     
	 			
	 			     //Saving the Excel file
	 			     workbook.save(out);
	 		    }
	 		    catch (Exception ex)
	 		    {
	 		    	ex.printStackTrace();
	 		        obj.setmsg("Khong the tao pivot.");
	 		    }
	 	    	
	 			obj.setUserId(userId);
	 		//	obj.init(search);
	 			
	 	    	//session.setAttribute("obj", obj);

	     	//	session.setAttribute("userId", userId);
	 	    		
	     	//response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpThuTien.jsp");	    		
	 	    }
	    	
	    	else
	    	{
		    	obj = new ErpThutienNPPList();
		    	obj.setCtyId(ctyId);
		    	obj.setUserId(userId);
		    	String search = getSearchQuery(request, obj);
		    	obj.init(search);
								
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		response.sendRedirect("/TraphacoHYERP/pages/Distributor/ErpThuTienNPP.jsp");
	    	}
	    }
	}
	
	private void CreateStaticHeader(Workbook workbook, String UserName) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet1 = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet1.getCells();
	   	    
	    Cell cell = cells.getCell("A1"); 
	    cell.setValue("PHẢI THU KHÁCH HÀNG");
 
	    cell = cells.getCell("A6");
	    cell.setValue("Số hệ thống Pk_seq");
	    
	    cell = cells.getCell("B6");
	    cell.setValue("Số phiếu");
	    
	    cell = cells.getCell("C6");
	    cell.setValue("Ngày ghi sổ");
	    
	    cell = cells.getCell("D6");	  
	    cell.setValue("ID khách hàng");
	    
	    cell = cells.getCell("E6");
	    cell.setValue("Tên khách hàng");
	    
	    cell = cells.getCell("F6");
	    cell.setValue("Thu được");
	    
	    cell = cells.getCell("G6");
	    cell.setValue("Thu được NT");
	    
	    cell = cells.getCell("H6");
	    cell.setValue("Phí ngân hàng");
	    
	    cell = cells.getCell("I6");
	    cell.setValue("Phí ngân hàng NT");
	    
	    cell = cells.getCell("J6");
	    cell.setValue("Chênh lệch");	 
 
	    cell = cells.getCell("K6");
	    cell.setValue("Chiết khấu");
	    	  
	    worksheet1.setName("Phatsinh");
	    
	    
	    Worksheet worksheet2 = workbook.createSheet();
	   	   
	    Cells cell1s = worksheet2.getCells();
	   	    
	    Cell cell1 = cell1s.getCell("A1"); 
	    cell1.setValue("HÓA ĐƠN KHÁCH HÀNG");
 
	    cell1 = cell1s.getCell("A6");
	    cell1.setValue("Thu tiền fk");
	    
	    cell1 = cell1s.getCell("B6");
	    cell1.setValue("ID khách hàng");
	    
	    cell1 = cell1s.getCell("C6");
	    cell1.setValue("Tên khách hàng");
	    
	    cell1 = cell1s.getCell("D6");
	    cell1.setValue("Hóa đơn");
	    
	    cell1 = cell1s.getCell("E6");
	    cell1.setValue("Số hóa đơn");
	    
	    cell1 = cell1s.getCell("F6");
	    cell1.setValue("Ngày xuất hóa đơn");
	    
	    cell1 = cell1s.getCell("G6");
	    cell1.setValue("Tổng tiền VND");
	    
	    cell1 = cell1s.getCell("H6");
	    cell1.setValue("Tổng tiền USD");
	    
	    cell1 = cell1s.getCell("I6");
	    cell1.setValue("Thanh toán VND");	 
 
	    cell1 = cell1s.getCell("J6");
	    cell1.setValue("Thanh toán USD");
	    
	    cell1 = cell1s.getCell("K6");
	    cell1.setValue("Ngày TT");
	    	  
	    worksheet2.setName("Hoadon");
	}
	
	
	private void CreateStaticData(Workbook workbook, String query , String query2) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    
		dbutils db = new dbutils();
		ResultSet rs = db.get(query);
		System.out.println("Get thu tien :"+query);
		
		NumberFormat formatTienViet = new DecimalFormat("#,###,###"); 
		NumberFormat formatTienUSA = new DecimalFormat("#,###,###.##"); 
		
		int i = 7;
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
					
					cell = cells.getCell("A" + Integer.toString(i));
					cell.setValue(rs.getString("pk_seq"));
					
					cell = cells.getCell("B" + Integer.toString(i));
					cell.setValue(rs.getString("sochungtu"));
					
					cell = cells.getCell("C" + Integer.toString(i));
					cell.setValue(rs.getString("ngayghiso"));
					
					cell = cells.getCell("D" + Integer.toString(i));
					cell.setValue(rs.getString("khachhang_fk"));
					
					cell = cells.getCell("E" + Integer.toString(i));
					cell.setValue(rs.getString("ten"));
					
					cell = cells.getCell("F" + Integer.toString(i));
					cell.setValue(formatTienViet.format(rs.getDouble("thuduoc")));
					
					cell = cells.getCell("G" + Integer.toString(i));
					cell.setValue(formatTienUSA.format(rs.getDouble("thuduocnt")));
					
					cell = cells.getCell("H" + Integer.toString(i));
					cell.setValue(formatTienViet.format(rs.getDouble("phinganhang")));
					
					cell = cells.getCell("I" + Integer.toString(i));
					cell.setValue(formatTienUSA.format(rs.getDouble("phinganhangnt")));
					
					cell = cells.getCell("J" + Integer.toString(i));
					cell.setValue(formatTienViet.format(rs.getDouble("chenhlech")));
									 
					cell = cells.getCell("K" + Integer.toString(i));
					cell.setValue(formatTienViet.format(rs.getDouble("chietkhau")));
					
					i++;
				}
				rs.close();
			}
			catch (Exception e){ e.printStackTrace(); }
		}
		
		
		 Worksheet worksheet2 = worksheets.getSheet(1);
	    Cells cell1s = worksheet2.getCells();
	    
		
		rs = db.get(query2);
		System.out.println("Get thu tien hoa don :"+query2);
		
		i = 7;
		if(rs != null)
		{
			try 
			{
				cell1s.setColumnWidth(0, 25.0f);
				cell1s.setColumnWidth(1, 15.0f);
				cell1s.setColumnWidth(2, 15.0f);
				cell1s.setColumnWidth(3, 30.0f);
				cell1s.setColumnWidth(4, 45.0f);
				cell1s.setColumnWidth(5, 25.0f);
				cell1s.setColumnWidth(6, 15.0f);
				cell1s.setColumnWidth(7, 15.0f);
				cell1s.setColumnWidth(8, 15.0f);
				cell1s.setColumnWidth(9, 15.0f);
				cell1s.setColumnWidth(10, 15.0f);
				for(int j = 0; j < 11; j++)
					cell1s.setRowHeight(j, 13.0f);
				
				Cell cell = null;
				while(rs.next())
				{					
					
					cell = cell1s.getCell("A" + Integer.toString(i));
					cell.setValue(rs.getString("thutien_fk"));
					
					cell = cell1s.getCell("B" + Integer.toString(i));
					cell.setValue(rs.getString("khachhang_fk"));
					
					cell = cell1s.getCell("C" + Integer.toString(i));
					cell.setValue(rs.getString("ten"));
					
					cell = cell1s.getCell("D" + Integer.toString(i));
					cell.setValue(rs.getString("hoadon_fk"));
					
					cell = cell1s.getCell("E" + Integer.toString(i));
					cell.setValue(rs.getString("sohoadon"));
					
					cell = cell1s.getCell("F" + Integer.toString(i));
					cell.setValue(rs.getString("ngayxuathd"));
					
					cell = cell1s.getCell("G" + Integer.toString(i));
					cell.setValue(formatTienViet.format(rs.getDouble("tongtienviet")));
					
					cell = cell1s.getCell("H" + Integer.toString(i));
					cell.setValue(formatTienUSA.format(rs.getDouble("tongtienusd")));
					
					cell = cell1s.getCell("I" + Integer.toString(i));
					cell.setValue(formatTienViet.format(rs.getDouble("sotienttviet")));
					
					cell = cell1s.getCell("J" + Integer.toString(i));
					cell.setValue(formatTienUSA.format(rs.getDouble("sotienttusd")));
									 
					cell = cell1s.getCell("K" + Integer.toString(i));
					cell.setValue(rs.getString("ngayghiso"));
					
					i++;
				}
				rs.close();
			}
			catch (Exception e){ e.printStackTrace(); }
		}
	 
	}
	
	
	private String getQueryExcelThuTien(HttpServletRequest request, String pages, String soDong, IErpThutienNPPList obj)
	{    	
    	String query =  "select a.PK_SEQ, a.sochungtu , a.NGAYGHISO , a.KHACHHANG_FK , b.Ten  , isnull(a.THUDUOC, 0) as THUDUOC, isnull(a.THUDUOCNT,0) as THUDUOCNT, " +
				    	" PHINGANHANG, PHINGANHANGNT ,  CHENHLECH , isnull(CHIETKHAU , 0) as CHIETKHAU  " +
				    	" from ERP_THUTIEN a  " +
				    	" inner join ERP_KHACHHANG b on b.PK_SEQ = a.KHACHHANG_FK ";		    
    	return query;
    	
	}	
	
	private String getQueryExcelThuTienHoaDon(HttpServletRequest request, String pages, String soDong, IErpThutienNPPList obj)
	{    	
    	String query =  
			    		"	select a.THUTIEN_FK , b.KHACHHANG_FK , d.Ten , a.HOADON_FK , b.SOHOADON ,b.NGAYXUATHD , " +
						"	case when b.tiente_fk = 100000 then B.TONGTIENAVAT else 0 end as tongtienviet ,	" +
						"	case when b.tiente_fk = 100001 then B.TONGTIENAVAT else 0 end as tongtienusd , " +
						"	case when c.tiente_fk = 100000 then a.SOTIENTT else 0 end as sotienttviet ,	" +
						"	case when c.tiente_fk = 100001 then a.SOTIENTT else 0 end as sotienttusd , c.NGAYGHISO	" +
						"	from ERP_THUTIEN_HOADON a	" +
						"	inner join ERP_HOADON b on a.HOADON_FK = b.PK_SEQ	" +
						"	inner join ERP_THUTIEN c on a.THUTIEN_FK = c.PK_SEQ	" +
						"	inner join ERP_KHACHHANG d on b.KHACHHANG_FK = d.PK_SEQ	" +
						"	where a.LOAIHOADON = 0 	" +
						
						"	union all " +
						
						"	select a.THUTIEN_FK , b.KHACHHANG_FK , d.Ten , a.HOADON_FK , b.sohoadon , b.ngayhoadon , " +
						"	b.TONGTIENAVAT as tongtienviet , " +
						"	0 as tongtienusd , " +
						"	a.SOTIENTT as sotienttviet , " +
						"	0 as sotienttusd, c.NGAYCHUNGTU	" +
						"	from ERP_THUTIEN_HOADON a	" +
						"	inner join (	" +
						"	select a.pk_seq as hdid, sohoadon , ngayhoadon  , ( b.tt + (b.tt * a.vat/100) ) as TONGTIENAVAT, a.trangthai , khachhang_fk " +
						"	from ERP_hoadonphelieu a	" +
						"	inner join (select hoadonphelieu_fk , SUM(thanhtien) as tt	" +
						"	from erp_hoadonphelieu_sanpham " +
						"	group by hoadonphelieu_fk ) b on b.hoadonphelieu_fk = a.pk_seq	" +
						"	) b on b.hdid = a.HOADON_FK	" +
						"	inner join ERP_THUTIEN c on a.THUTIEN_FK = c.PK_SEQ	" +
						"	inner join ERP_KHACHHANG d on b.KHACHHANG_FK = d.PK_SEQ	" +
						"	where a.LOAIHOADON = 1		    ";		
 
    	return query;
    	
	}	
	
	
	private String getSearchQuery(HttpServletRequest request, IErpThutienNPPList obj)
	{
		Utility util = new Utility();
/*	String 	query =  
	    " select a.pk_seq as tthdId, a.trangthai, a.ngaychungtu,  a.ngaytao, a.ngaysua, isnull(noptienids, '') as noptienids,  "+
	     "  d.ten as nguoitao, e.ten as nguoisua,  isnull(a.SOTIENTHU,0) AS THUCTHU, a.CONLAI AS SOTIENCONLAI, isnull(tennguoinop, '') as tennguoinop \n"+
	     " from ERP_THUTIENNPP a  inner join NHANVIEN d on a.nguoitao = d.pk_seq   "+
	     "		 inner join NHANVIEN e on a.nguoisua = e.pk_seq " +
	     " where a.pk_seq > 0 ";*/
	    
		String query =
			 " select distinct a.pk_seq as tthdId, a.trangthai, a.ngaychungtu,  a.ngaytao, a.ngaysua, isnull(noptienids, '') as noptienids, \n" + 
			 " d.ten as nguoitao, e.ten as nguoisua,  isnull(a.SOTIENTHU,0) AS THUCTHU, a.CONLAI AS SOTIENCONLAI, isnull(tennguoinop, '') as tennguoinop, \n" +
			 "        ISNULL(   (Select distinct KH.MAFAST + ' - ' + KH.TEN  + ',' AS [text()]  \n"+
		  	 "           From ERP_THUTIENNPP_HOADON K1 inner join KHACHHANG KH on K1.KHACHHANG_FK = Kh.PK_SEQ \n"+
		  	 "           Where K1.THUTIENNPP_FK = a.PK_SEQ  and isnull(a.NOPTIENIDS,'') = '' \n"+
		  	 "           For XML PATH ('') ) , '' ) as khachhangs  \n"+ 
			 " from ERP_THUTIENNPP a \n"  +
			 " inner join NHANVIEN d on a.nguoitao = d.pk_seq \n" + 
			 " inner join NHANVIEN e on a.nguoisua = e.pk_seq \n" +
			 " left join ERP_THUTIENNPP_HOADON f on f.THUTIENNPP_FK=a.PK_SEQ \n"+
			 " left join HOADON g on g.PK_SEQ=f.HOADONNPP_FK and f.KHACHHANG_FK in (select pk_seq from KHACHHANG where kbh_fk='100025') \n" +
			 " left join ERP_HOADONNPP h on h.PK_SEQ=f.HOADONNPP_FK and f.KHACHHANG_FK in (select pk_seq from KHACHHANG where kbh_fk='100052') \n" +
			" where a.pk_seq > 0 and a.TRANGTHAI != 2 \n";

		String tungay = util.antiSQLInspection(request.getParameter("tungay"));
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = util.antiSQLInspection(request.getParameter("denngay"));
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String nccId = util.antiSQLInspection(request.getParameter("khId"));
		if(nccId == null)
			nccId = "";
		obj.setNccId(nccId);
		
		
		String maphieu = util.antiSQLInspection(request.getParameter("maphieu"));
		if(maphieu == null)
			maphieu = "";
		obj.setMaphieu(maphieu);
		
		
		String tdvId = util.antiSQLInspection(request.getParameter("tdvId"));
		if(tdvId == null)
			tdvId = "";
		obj.setTdvId(tdvId);
		
		String nvgnId = util.antiSQLInspection(request.getParameter("nvgnId"));
		if(nvgnId == null)
			nvgnId = "";
		obj.setNvgnId(nvgnId);
		
		String sohoadon = util.antiSQLInspection(request.getParameter("sohoadon"));
		if(sohoadon == null)
			sohoadon = "";
		obj.setSoHoaDon(sohoadon);
		
		String sophieunop = util.antiSQLInspection(request.getParameter("sophieunop"));
		if(sophieunop == null)
			sophieunop = "";
		obj.setSophieunop(sophieunop);
		
		if(tungay.length() > 0)
			query += " and a.ngaychungtu >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngaychungtu <= '" + denngay + "'";
		
		if(nccId.length() > 0)
			query += " and a.khachhang_fk = '" + nccId + "'";
		
		if(nvgnId.length() > 0)
			query += " and a.NVGN_FK = '" + nvgnId + "'";
		
		if(tdvId.length() > 0)
			query += " and a.NVBH_FK = '" + tdvId + "'";
		
		if(maphieu.length() > 0)
			query += " and a.pk_seq = '" + maphieu + "'  ";
		
		if(sohoadon.length() > 0)
		{
			query += " and (f.HOADONNPP_FK in (select PK_SEQ from HOADON where SOHOADON like '%"+sohoadon+"%' and TRANGTHAI not in(3,5)) " +
					" or f.HOADONNPP_FK in (select PK_SEQ from ERP_HOADONNPP where SOHOADON like '%"+sohoadon+"%' and TRANGTHAI not in(3,5))) ";
		}
		
		if(sophieunop.length() > 0)
			query += " and "+sophieunop+" in (select NOPTIEN_FK from ERP_THUTIENNPP_NOPTIEN where THUTIENNPP_FK = a.pk_seq ) ";
		
		System.out.println("Câu query tìm kiếm là: "+ query);
		return query;
	}
	
	private String Delete(String tthdId)
	{
		dbutils db = new dbutils();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			db.update("update ERP_THUTIENNPP set trangthai='2'  where PK_SEQ = '" + tthdId + "'");			
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			
			return "";
		} 
		catch (SQLException e)
		{ 
			db.shutDown(); 
			return "Khong the xoa ERP_THUTIENNPP"; 
		}
		
	}

}
