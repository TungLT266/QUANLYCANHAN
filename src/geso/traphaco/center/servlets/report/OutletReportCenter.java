package geso.traphaco.center.servlets.report;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;


import geso.traphaco.center.beans.stockintransit.IStockintransit;
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


public class OutletReportCenter extends HttpServlet {
	private static final long serialVersionUID = 1L;
     
	
	String query="";
   
    public OutletReportCenter() {
        super();
        }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		 Utility util = new Utility();
		 String querystring = request.getQueryString();
		 String  userId = util.getUserId(querystring);
		 IStockintransit obj = new Stockintransit();
	    obj.setuserId(userId);
	    obj.init();	    
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Center/OutletReportCenter.jsp";
		response.sendRedirect(nextJSP);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    HttpSession session = request.getSession();
		String userTen = (String)session.getAttribute("userTen");
		String userId = (String)session.getAttribute("userId");

	    IStockintransit obj = new Stockintransit();
		try{
			obj.settungay(request.getParameter("Sdays"));
			obj.setdenngay(request.getParameter("Edays"));
			obj.setkenhId(request.getParameter("kenhs"));
			obj.setvungId(request.getParameter("vungs"));
			obj.setkhuvucId(request.getParameter("khuvucs"));
			obj.setgsbhId(request.getParameter("gsbhs"));
			obj.setnppId(request.getParameter("npps"));
			obj.setDdkd(request.getParameter("ddkds"));
			String action = request.getParameter("action");
			obj.setuserId(userId);
			obj.setuserTen(userTen);

			Utility util= new Utility();
			if (action.equals("create")) {
				response.setContentType("application/xlsm");
		        response.setHeader("Content-Disposition", "attachment; filename=BCDanhGiaCuaHieu_"+util.setTieuDe(obj)+".xlsm");
		        OutputStream out = response.getOutputStream();
				setQuery(obj);
				ExportToExcel(out,obj);
			}	
			
		}catch(Exception ex){
			session.setAttribute("errors", ex.getMessage());
		}
		obj.init();
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Center/OutletReportCenter.jsp";
		response.sendRedirect(nextJSP);
	}
	private void ExportToExcel(OutputStream out,IStockintransit obj)throws Exception{
		try{ 
			
			
			String chuoi=getServletContext().getInitParameter("path") + "\\BAOCAODOANHSOCUAHIEUTT.xlsm";
			
			FileInputStream fstream = new FileInputStream(chuoi);
			
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			
			CreateHeader(workbook,obj);
			FillData(workbook,obj);
			
			workbook.save(out);	
			fstream.close();
		}catch(Exception ex){
			throw new Exception(ex.getMessage());
		}
	}

	private void CreateHeader(Workbook workbook,IStockintransit obj) throws Exception {
		try {
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");

			Cells cells = worksheet.getCells();
			cells.setRowHeight(0, 50.0f);
			Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 16,
					"DOANH SỐ THEO CỬA HIỆU");
			cell = cells.getCell("A2");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10,
					"Từ ngày : " + obj.gettungay() + " Tới ngày: " + obj.getdenngay());
			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Ngày tạo : "
					+ obj.getDateTime());
			cell = cells.getCell("A4");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10,
					"Người tạo : " + obj.getuserTen());
			
			cell = cells.getCell("FA1");		cell.setValue("Kenh");
			cell = cells.getCell("FB1");		cell.setValue("Vung");
			cell = cells.getCell("FC1");		cell.setValue("KhuVuc");
			cell = cells.getCell("FD1");		cell.setValue("GiamSatBanHang");
			cell = cells.getCell("FE1");		cell.setValue("ChiNhanh/DoiTac");
			cell = cells.getCell("FF1");		cell.setValue("DaiDienKinhDoanh");
			cell = cells.getCell("FG1");		cell.setValue("KhachHang");
			cell = cells.getCell("FH1");		cell.setValue("DoanhSoCaoNhat");
			cell = cells.getCell("FI1");		cell.setValue("NgayBatDauMuaHang");
			cell = cells.getCell("FJ1");		cell.setValue("NgayCuoiCungMuaHang");
			cell = cells.getCell("FK1");		cell.setValue("DoanhSoTb3ThangGanNhat");
			cell = cells.getCell("FL1");		cell.setValue("DoanhSoThucDat");
			cell = cells.getCell("FM1");		cell.setValue("SoDonHang");
			cell = cells.getCell("FN1");		cell.setValue("TinhThanh");
			cell = cells.getCell("FO1");		cell.setValue("MaCN/DT");
			cell = cells.getCell("FP1");		cell.setValue("MaKhachHang");
			cell = cells.getCell("FQ1");		cell.setValue("MaGiamSatBanHang");
			cell = cells.getCell("FR1");		cell.setValue("MaDaiDienKinhDoanh");
			

		} catch (Exception ex) {
			System.out.println(ex.toString());
			throw new Exception("Khong the tao duoc Header cho bao cao.!!!");
		}
	}	
	private void FillData(Workbook workbook,IStockintransit obj)throws Exception{
		try{
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setGridlinesVisible(false);
			Cells cells = worksheet.getCells();
			dbutils db = new dbutils();
			ResultSet rs = db.get(getQuery());
			Cell cell = null;
			int countRow = 2;
			while(rs.next()){
				cell = cells.getCell("FA" + String.valueOf(countRow));		cell.setValue(rs.getString("Channel"));
				cell = cells.getCell("FB" + String.valueOf(countRow));		cell.setValue(rs.getString("Region"));
				cell = cells.getCell("FC" + String.valueOf(countRow));		cell.setValue(rs.getString("Area"));
				cell = cells.getCell("FD" + String.valueOf(countRow));		cell.setValue(rs.getString("Sales_Sup"));
				cell = cells.getCell("FE" + String.valueOf(countRow));		cell.setValue(rs.getString("Distributor"));
				cell = cells.getCell("FF" + String.valueOf(countRow));		cell.setValue(rs.getString("Sales_Rep"));
				cell = cells.getCell("FG" + String.valueOf(countRow));		cell.setValue(rs.getString("Customer"));
				cell = cells.getCell("FH" + String.valueOf(countRow));		cell.setValue(rs.getDouble("Highest_ever_Volume"));
				cell = cells.getCell("FI" + String.valueOf(countRow));		cell.setValue(rs.getString("First_Buy_date"));
				cell = cells.getCell("FJ" + String.valueOf(countRow));		cell.setValue(rs.getString("Last_buy_date"));
				cell = cells.getCell("FK" + String.valueOf(countRow));		cell.setValue(rs.getDouble("Monthly_Avg_second_sales"));
				cell = cells.getCell("FL" + String.valueOf(countRow));		cell.setValue(rs.getDouble("Monthly_archive_second_sales"));
				cell = cells.getCell("FM" + String.valueOf(countRow));		cell.setValue(rs.getDouble("#Order"));
				cell = cells.getCell("FN" + String.valueOf(countRow));		cell.setValue(rs.getString("Province"));
				cell = cells.getCell("FO" + String.valueOf(countRow));		cell.setValue(rs.getString("Distributor_code"));
				cell = cells.getCell("FP" + String.valueOf(countRow));		cell.setValue(rs.getString("SMARTID"));
				cell = cells.getCell("FQ" + String.valueOf(countRow));		cell.setValue(rs.getString("gsbhMa"));
				cell = cells.getCell("FR" + String.valueOf(countRow));		cell.setValue(rs.getString("ddkdMa"));
			

				++countRow;
			}
			if(rs!=null)rs.close();
			if(db!=null){
				db.shutDown();
			}
			ReportAPI.setHidden(workbook,20);
	
		}catch(Exception ex){
			
			System.out.println("Errrorr : "+ex.toString());
			throw new Exception("Qua trinh dien du lieu vao file Excel va tao PivotTable bi loi.!!!");
		}
	}
	private void setQuery(IStockintransit obj)
	{
		Utility Ult =new Utility();
		query = 
				" select kbh.ten as Channel,v.ten as Region,kv.ten as Area,  " +
				" 	ddkd.MaNhanVien as ddkdMa,gsbh.manhanvien as gsbhMa,gsbh.ten as Sales_Sup,npp.ten as Distributor,ddkd.ten as Sales_Rep,  " +
				" 	kh.PK_SEQ as khId,kh.smartid as makh,kh.ten + '__'+ kh.diachi  + '__' + isnull(kh.dienthoai,'') as Customer, " +
				" 	kh.SMARTID AS SMARTID,npp.convsitecode as  Distributor_code,kh.smartid as  custkey, tt.ten as Province,qh.ten as Town,hch.diengiai as Outlettype, lch.diengiai as outletpostion,  " +
				" 	(	select min(a.ngaynhap)  " +
				" 		from donhang a  " +
				" 		where a.khachhang_fk = dh.khachhang_fk  " +
				" 		and a.NGAYNHAP >='"+obj.gettungay()+"' and a.NGAYNHAP <='"+obj.getdenngay()+"' and a.trangthai=1  " +
				" 	)as First_Buy_date,  " +
				" 	( " +
				" 		select max(mat.ngaynhap)  " +
				" 		from donhang mat where  " +
				" 		mat.ddkd_fk = dh.ddkd_fk and mat.khachhang_fk = dh.khachhang_fk and mat.gsbh_fk = dh.gsbh_fk  " +
				" 		and mat.NGAYNHAP >='"+obj.gettungay()+"' and mat.NGAYNHAP <='"+obj.getdenngay()+"'  and mat.trangthai=1 " +
				" 	)as Last_buy_date,  " +
				" 	(	 " +
				" 		select top(1) sum(dhsp.soluong*dhsp.giamua)  " +
				"  		from donhang_sanpham dhsp  " +
				"  			inner join donhang dhs on dhs.pk_seq = dhsp.donhang_fk  " +
				" 		where dh.ddkd_fk = dhs.ddkd_fk	 and dh.gsbh_fk=dhs.gsbh_fk  " +
				" 			and dh.khachhang_fk=dhs.khachhang_fk and dh.kbh_fk =dhs.kbh_fk	  " +
				" 			and dh.npp_fk =dhs.npp_fk  " +
				" 			and dhs.NGAYNHAP >='"+obj.gettungay()+"' and dhs.NGAYNHAP <='"+obj.getdenngay()+"' and	dhs.trangthai=1  " +
				" 		group by dhsp.DONHANG_FK,dhs.KHACHHANG_FK " +
				" 		order by sum(dhsp.soluong*dhsp.giamua)  desc				 " +
				" 	)as  Highest_ever_Volume,  " +
				" 	( " +
				" 		select sum(dhsp1.soluong*dhsp1.giamua)/3  " +
				"  		from donhang_sanpham dhsp1  " +
				" 			inner join donhang dhs on dhs.pk_seq = dhsp1.donhang_fk  " +
				" 		where dh.ddkd_fk = dhs.ddkd_fk	and dh.gsbh_fk=dhs.gsbh_fk	and dh.khachhang_fk=dhs.khachhang_fk  " +
				" 			and dh.kbh_fk =dhs.kbh_fk and dh.npp_fk =dhs.npp_fk  " +
				" 			and dhs.ngaynhap >(SELECT CONVERT(VARCHAR(10),DATEADD(day,-12*7-2,GETDATE()),120))  " +
				" 			and dhs.ngaynhap <=(SELECT CONVERT(VARCHAR(10),DATEADD(day,-2,GETDATE()),120)) and  dhs.trangthai=1 " +
				" 	 )as Monthly_Avg_second_sales,  " +
				" 	(	 " +
				" 		select sum(dhsp2.soluong*dhsp2.giamua)  " +
				" 		from donhang dhg inner join donhang_sanpham dhsp2 on dhg.pk_seq = dhsp2.donhang_fk  " +
				" 		where dh.ddkd_fk = dhg.ddkd_fk and dh.gsbh_fk=dhg.gsbh_fk and dh.khachhang_fk=dhg.khachhang_fk  " +
				" 			and dh.kbh_fk =dhg.kbh_fk and dh.npp_fk =dhg.npp_fk  " +
				" 			and dhg.NGAYNHAP >='"+obj.gettungay()+"' and dhg.NGAYNHAP <='"+obj.getdenngay()+"' 	and	dhg.trangthai=1			  " +
				" 	 ) as Monthly_archive_second_sales,  " +
				" 	(	 " +
				" 		select count(dhd.pk_seq) from donhang dhd  " +
				" 		where dh.ddkd_fk = dhd.ddkd_fk	and dh.gsbh_fk=dhd.gsbh_fk		and dh.khachhang_fk=dhd.khachhang_fk  " +
				" 			and dh.kbh_fk =dhd.kbh_fk	and dh.npp_fk =dhd.npp_fk	 " +
				" 			and dhd.ngaynhap >='"+obj.gettungay()+"'  " +
				" 			and dhd.ngaynhap <='"+obj.getdenngay()+"'  and dhd.trangthai=1" +
				" 	)as #Order  " ;
		if(obj.getnppId().length()>0)
				query += "    from (select distinct npp_fk,khachhang_fk,ddkd_fk,kbh_fk,gsbh_fk  from donhang where ngaynhap >='" + obj.gettungay() +"' and ngaynhap <='" + obj.getdenngay() +"' and npp_fk ='"+ obj.getnppId() +"' and trangthai=1 ) dh ";
		else
			query += "    from (select distinct npp_fk,khachhang_fk,ddkd_fk,kbh_fk,gsbh_fk from donhang where ngaynhap >='" + obj.gettungay() +"' and ngaynhap <='" + obj.getdenngay() +"' and trangthai=1 ) dh ";
			query +="			inner join kenhbanhang kbh on kbh.pk_seq = dh.kbh_fk" +
				"			inner join nhaphanphoi npp on npp.pk_seq = dh.npp_fk		" +
				"			left join khuvuc kv on kv.pk_seq = npp.khuvuc_fk" +
				"			left join vung v on v.pk_seq = kv.vung_fk" +
				"			left join giamsatbanhang gsbh on gsbh.pk_seq = dh.gsbh_fk" +
				"		   inner join daidienkinhdoanh ddkd on ddkd.pk_seq = dh.ddkd_fk" +
				"		   inner join khachhang kh on kh.pk_seq = dh.khachhang_fk" +
				"		   left join tinhthanh tt on tt.pk_seq = kh.tinhthanh_fk" +
				"		   left join quanhuyen qh on qh.pk_seq = kh.quanhuyen_fk" +
				"		   left join hangcuahang hch on hch.pk_seq = kh.hch_fk" +
				"		   left join loaicuahang lch on lch.pk_seq = kh.lch_fk " +
			    "			where npp.pk_seq in " + Ult.quyen_npp(obj.getuserId()) +" and kbh.pk_seq in " + Ult.quyen_kenh(obj.getuserId());
			        if(obj.getkenhId().length()>0)
			        	query +=" and kbh.pk_seq ='"+ obj.getkenhId() +"'";
			        if(obj.getvungId().length() >0)
			        	query +=" and v.pk_seq ='"+ obj.getvungId() +"'";
			        if(obj.getkhuvucId().length()>0)
			        	query +=" and kv.pk_seq ='"+ obj.getkhuvucId() +"'";
			        if(obj.getgsbhId().length()>0    		)
			        	query +=" and gsbh.pk_seq ='"+ obj.getgsbhId() +"'";
			        if(obj.getDdkd().length()>0)
			        	query +=" and ddkd.pk_seq ='"+ obj.getDdkd() +"'";
				System.out.println("qoedjdl : "+query);
	}
	private String getQuery()
	{
		return query;
	}

}
