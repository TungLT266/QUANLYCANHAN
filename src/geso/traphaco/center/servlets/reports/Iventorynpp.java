package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.report.IBckho;
import geso.traphaco.center.beans.report.imp.Bckho;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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


public class Iventorynpp extends HttpServlet {	

  private static final long serialVersionUID = 1L;

	public Iventorynpp() {
        super();
    }
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			String ctyId = (String)session.getAttribute("congtyId");
			IBckho obj = new Bckho();

			Utility util=new Utility();
			obj.setUserId(userId);
			obj.setUserTen(userTen);
			obj.createRs();
			obj.setMsg("");
			
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			session.setAttribute("userTen", userTen);
			session.setAttribute("util", util);
			session.setAttribute("congtyId", ctyId);
			String nextJSP = "/TraphacoHYERP/pages/Center/Inventorynpp.jsp";
			response.sendRedirect(nextJSP);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		OutputStream out = response.getOutputStream(); 
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IBckho obj = new Bckho();
		Utility util = new Utility();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  
		String manpp=util.getIdNhapp(userId);
		String nextJSP = "/TraphacoHYERP/pages/Center/Inventorynpp.jsp";
		
		obj.setUserTen((String) session.getAttribute("userTen")!=null?(String) session.getAttribute("userTen"):"");

		obj.setUserId(util.antiSQLInspection(request.getParameter("userId"))!=null? util.antiSQLInspection(request.getParameter("userId")):"");
		String avaliable = util.antiSQLInspection(request.getParameter("avail"));

		obj.setNhomkenhId(util.antiSQLInspection(request.getParameter("kenhId"))!=null?util.antiSQLInspection(request.getParameter("kenhId")):"");

		String laysolo = util.antiSQLInspection(request.getParameter("laysolo"));
		if(laysolo == null)
			laysolo="0";
		obj.setLaysolo(laysolo);
		
		String loaikho = util.antiSQLInspection(request.getParameter("loaikho"));
		if(loaikho == null)
			loaikho = "";
		obj.setLoaikho(loaikho);
		
		String doituongId = util.antiSQLInspection(request.getParameter("doituongId"));
		if(doituongId == null)
			doituongId = "";
		obj.setDoituongId(doituongId);
		
		String[] khoId = request.getParameterValues("khoId"); 
		String str = "";
		if (khoId != null)
		{
			for(int i = 0; i < khoId.length; i++)
				str += khoId[i] + ",";
			if(str.length() > 0)
				str = str.substring(0, str.length() - 1);
		}
		System.out.println("khoID " + str);
		obj.setKhoId(str);
		
		String ctyId = (String)session.getAttribute("congtyId");
		String action = util.antiSQLInspection(request.getParameter("action"));
		
		if(action.equals("submit"))
		{
			obj.createRs();
			
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			session.setAttribute("userTen", userTen);
			
			response.sendRedirect(nextJSP);
		}
		else
		{
			try 
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=Iventorynpp.xlsm");
				//dbutils db=new dbutils();
				
				/*String sql="select * from banggiamuanpp_npp bgmnpp where bgmnpp.npp_fk ='"+manpp+"'";
				ResultSet rscheck=db.get(sql);
	
				if(rscheck!=null)
					if(!rscheck.next()){
						obj.setMsg("Trên trung tâm chưa chọn bảng giá mua cho nhà phân phối,vui lòng liên hệ với admin trung tâm để xử lý tình huống này");
						session.setAttribute("obj", obj);
						session.setAttribute("userId", userId);
						session.setAttribute("userTen", userTen);
						session.setAttribute("util", util);
						response.sendRedirect(nextJSP);
	
					}else{*/
	
						boolean isTrue = CreatePivotTable(out, obj,avaliable, ctyId);
						if(!isTrue){
							PrintWriter writer = new PrintWriter(out);
							writer.write("Khong co bao cao trong thoi gian nay....!!!");
							writer.close();
						}
					/*}
				rscheck.close();*/
			} 
			catch (Exception ex) 
			{
				obj.createRs();
				obj.setMsg(ex.getMessage());
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
				session.setAttribute("userTen", userTen);
				session.setAttribute("util", util);
				session.setAttribute("congtyId", ctyId);
				response.sendRedirect(nextJSP);
				System.out.println("Loi  khong lay duoc bao cao : "+ex.toString());
			}
		}
	}
	
	private boolean CreatePivotTable(OutputStream out,IBckho obj, String avaliable, String ctyId) throws Exception
    {   
		try{
		String chuoi=getServletContext().getInitParameter("path") + "\\Iventory(NPP).xlsm";

		if(obj.getLaysolo().trim().equals("1")){
			chuoi=getServletContext().getInitParameter("path") + "\\Iventory(NPP_LO).xlsm";
		}
		FileInputStream fstream;
		fstream = new FileInputStream(chuoi);

		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);


		//Buoc2 tao khung
		//ham tao khu du lieu
		CreateStaticHeader(obj,workbook, obj.getDateTime(), obj.getDateTime(), obj.getUserTen() );
		
		boolean isTrue =  CreateStaticData(workbook,obj,avaliable, ctyId);
		if(!isTrue)
			return false;
		workbook.save(out);
		fstream.close();
		}catch(Exception er){
			er.printStackTrace();
			return false;
			
		}
		return true;
	    
   }
	
	private void CreateStaticHeader(IBckho obj, Workbook workbook, String dateFrom, String dateTo, String UserName) throws Exception 
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();

		Style style;
		//cells.setColumnWidth(0, 200.0f);
		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");  
		cell.setValue("TỒN HIỆN TẠI");   	

		style = cell.getStyle();

		Font font2 = new Font();
		font2.setColor(Color.RED);//mau chu
		font2.setSize(14);// size chu
		style.setFont(font2); 
		style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu       
		cell.setStyle(style);
		cell = cells.getCell("A2"); getCellStyle(workbook,"A2",Color.NAVY,true,9);
		cell.setValue("Từ ngày  " + dateFrom + "      Đến ngày " + dateTo);    
		cell = cells.getCell("A3");getCellStyle(workbook,"A3",Color.NAVY,true,9);
		cell.setValue("Ngày Tạo : " + this.getDateTime());
		cell = cells.getCell("A4");getCellStyle(workbook,"A4",Color.NAVY,true,9);
		cell.setValue("Tạo Bởi:  " + UserName);

		//tieu de, hang dau tien cua bang du lieu, cell la toa do cua exel
		cell = cells.getCell("AA1"); cell.setValue("Kenh Ban Hang");
		cell = cells.getCell("AB1"); cell.setValue("Ten San Pham");
		cell = cells.getCell("AC1"); cell.setValue("So Luong ");	  
		cell = cells.getCell("AD1"); cell.setValue("Kho");
		cell = cells.getCell("AE1"); cell.setValue("Ma Nha Phan Phoi");
		cell = cells.getCell("AF1"); cell.setValue("Ma San Pham");
		cell = cells.getCell("AG1"); cell.setValue("So Luong Kien");
		cell = cells.getCell("AH1"); cell.setValue("Don Vi Kinh Doanh");
		cell = cells.getCell("AI1"); cell.setValue("Chung Loai");
		cell = cells.getCell("AJ1"); cell.setValue("Nhan Hang");
		cell = cells.getCell("AK1"); cell.setValue("So Tien");
		cell = cells.getCell("AL1"); cell.setValue("Booked");
		cell = cells.getCell("AM1"); cell.setValue("TonThucTe");
		cell = cells.getCell("AN1"); cell.setValue("DoiTuong");
		if(obj.getLaysolo().trim().equals("1"))
		{
			cell = cells.getCell("AO1"); 	cell.setValue("SoLo");  ReportAPI.setCellHeader(cell);
			cell = cells.getCell("AP1"); 	cell.setValue("NgayHetHan");  ReportAPI.setCellHeader(cell);
		}
		worksheet.setName("Sheet1");
	}
	
	private boolean CreateStaticData(Workbook workbook,IBckho obj, String avaliable, String ctyId) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		Utility utl = new  Utility();

		String manpp = "";
		//manpp = utl.getIdNhapp(obj.getUserId());
		String query = "select pk_seq from NHAPHANPHOI where congty_fk = "+ctyId+" and TRANGTHAI = 1 ";
		System.out.println("[congty]" + query);
		ResultSet rs = db.get(query);
		try
		{
			if(rs.next())
			{
				manpp = rs.getString("pk_seq");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		String sql1 = "";
		if (obj.getNhomkenhId().length() > 0)
			sql1 = sql1 + " and kbh.pk_seq ='" + obj.getNhomkenhId() + "' ";

		String condition = "";
		condition = condition + sql1;
		if(avaliable.equals("1")){
			condition += " and tkn.AVAILABLE > 0";
		}

		String bang = "";
		String condition2 = "";
		if(obj.getLoaikho().trim().length() > 0)
		{
			condition2 += " and kho_fk in ( select PK_SEQ from KHO where loai = '" + obj.getLoaikho() + "'";
			if(obj.getKhoId().trim().length() > 0)
				condition2 += " and pk_seq in ("+obj.getKhoId()+") ) ";
			else
				condition2 += " and pk_seq in (" + utl.quyen_kho( obj.getUserId()  ) +") ) ";
		}
		else
		{
			if(obj.getKhoId().trim().length() > 0)
				condition2 += " and kho_fk in ( select PK_SEQ from KHO where pk_seq in ("+obj.getKhoId()+") ) ";
			else
				condition2 += " and kho_fk in ( select PK_SEQ from KHO where pk_seq in (" + utl.quyen_kho( obj.getUserId()  ) +") ) ";
		}
			
		String tableNAME = "";
		String tableNAME2 = "";

		String sql = "";
		if(obj.getLoaikho().trim().length() <= 0 || obj.getLoaikho().equals("1") ||obj.getLoaikho().equals("3")|| obj.getLoaikho().equals("4") || obj.getLoaikho().equals("10"))
		{
			tableNAME = " nhapp_kho ";
			tableNAME2 = " nhapp_kho_chitiet ";

			query = "select dungchungkenh from NHAPHANPHOI where pk_seq = '" + manpp + "'";
			String dunghcungKENH = "";
			rs = db.get(query);
			if(rs.next())
			{
				dunghcungKENH = rs.getString("dungchungkenh");
				rs.close();
			}

			String condition3 = "";
			if(dunghcungKENH.equals("1"))
				condition3 += " and nhomkenh_fk = '100000' ";

			bang =  "  select kho_fk, nhomkenh_fk as kbh_fk, sanpham_fk, npp_fk, sum(soluong) as soluong, sum(AVAILABLE) as AVAILABLE, sum(booked) as booked,'' solo,'' as ngayhethan " + 
					"  from  " + tableNAME + 
					"  where npp_fk = '" + manpp + "' " + condition2 + condition3 +
					"  group by kho_fk, nhomkenh_fk, sanpham_fk, npp_fk ";

			if(obj.getLaysolo().trim().equals("1"))
			{
				bang =  "  select kho_fk, nhomkenh_fk as kbh_fk, sanpham_fk, npp_fk, sum(soluong) as soluong, sum(AVAILABLE) as AVAILABLE, sum(booked) as booked, solo, ngayhethan " + 
						"  from  " + tableNAME2 + 
						"  where npp_fk = '" + manpp + "' " + condition2 + 
						"  group by kho_fk, nhomkenh_fk, sanpham_fk, npp_fk, solo, ngayhethan ";
			}

			sql = 	"  select '' AS DOITUONG  ,tkn.SoLuong,solo, ngayhethan, case when " + dunghcungKENH + " = 0 then kbh.ten else N'Phanam' end as Channel ,sp.ma as Sku_code,sp.ten as SKU,tkn.AVAILABLE as Piece,k.ten as Warehouse, "+
					"  tkn.npp_fk as Distributor_code,"+
					"  nh.ten as Brans, isnull(cast(tkn.AVAILABLE/cast(qc.soluong1 as float) as float), 0) as Quantily,"+
					"  dvkd.donvikinhdoanh as Business_unit,cl.ten as Category, "+
					"  tkn.AVAILABLE , "+
					"  ISNULL ( ( select dongia from BANGGIABANLENPP_SANPHAM where sanpham_fk = sp.pk_seq and BANGGIABLNPP_FK in ( select pk_seq from BANGGIABANLENPP where DVKD_FK = '100000' and pk_seq in ( select BANGGIABLNPP_FK from BANGGIABANLENPP_NPP where NPP_FK = " + manpp +  " ) ) ), 0) AS DonGia, booked "+  
					"  from ( " + bang + ") tkn "+
					"  left join NHOMKENH kbh on kbh.pk_seq = tkn.kbh_fk "+
					"  inner join sanpham sp on sp.pk_seq = tkn.sanpham_fk "+
					"  left join donvikinhdoanh dvkd on dvkd.pk_seq = sp.dvkd_fk "+
					"  inner join kho k on k.pk_seq = tkn.kho_fk "+
					"  left join quycach qc on  qc.sanpham_fk = sp.pk_seq and sp.dvdl_fk =qc.dvdl1_fk  " +
					"  and qc.dvdl2_fk= 100018"+
					"  left join donvidoluong dvdl on dvdl.pk_seq = sp.dvdl_fk "+
					"  left join chungloai cl on cl.pk_seq = sp.chungloai_fk "+
					"  left join nhanhang nh on nh.pk_seq = sp.nhanhang_fk "+
					"  where 1=1 " + condition;

		}
		else if(obj.getLoaikho().equals("8")|| obj.getLoaikho().equals("9") || obj.getLoaikho().equals("5"))
		{
			tableNAME = " NHAPP_KHO_KYGUI ";
			tableNAME2 = " NHAPP_KHO_KYGUI_CHITIET ";

			String condition3 = "";
			if(obj.getDoituongId().trim().length() > 0)
				condition3 += " and KHACHHANG_FK = '" + obj.getDoituongId() + "' ";
			
			bang =  "  select KHACHHANG_FK,kho_fk, nhomkenh_fk as kbh_fk, sanpham_fk, npp_fk, sum(soluong) as soluong, sum(AVAILABLE) as AVAILABLE, sum(booked) as booked,'' solo,'' as ngayhethan " + 
					"  from  " + tableNAME + 
					"  where npp_fk = '" + manpp + "' " + condition2 + condition3 +
					"  group by KHACHHANG_FK,kho_fk, nhomkenh_fk, sanpham_fk, npp_fk ";

			if(obj.getLaysolo().trim().equals("1"))
			{
				bang =  "  select KHACHHANG_FK, kho_fk, nhomkenh_fk as kbh_fk, sanpham_fk, npp_fk, sum(soluong) as soluong, sum(AVAILABLE) as AVAILABLE, sum(booked) as booked, solo, ngayhethan " + 
						"  from  " + tableNAME2 + 
						"  where npp_fk = '" + manpp + "' " + condition2 + condition3 +
						"  group by kho_fk, nhomkenh_fk, sanpham_fk, npp_fk, solo, ngayhethan, KHACHHANG_FK ";
			}

			sql = 	"  select KH.TEN AS DOITUONG  ,tkn.SoLuong,solo, ngayhethan,kbh.ten as Channel,sp.ma as Sku_code,sp.ten as SKU,tkn.AVAILABLE as Piece,k.ten as Warehouse, "+
					"  tkn.npp_fk as Distributor_code,"+
					"  nh.ten as Brans, isnull(cast(tkn.AVAILABLE/cast(qc.soluong1 as float) as float), 0) as Quantily,"+
					"  dvkd.donvikinhdoanh as Business_unit,cl.ten as Category, "+
					"  tkn.AVAILABLE , "+
					"  ISNULL ( ( select dongia from BANGGIABANLENPP_SANPHAM where sanpham_fk = sp.pk_seq and BANGGIABLNPP_FK in ( select pk_seq from BANGGIABANLENPP where DVKD_FK = '100000' and pk_seq in ( select BANGGIABLNPP_FK from BANGGIABANLENPP_NPP where NPP_FK = " + manpp +  " ) ) ), 0) AS DonGia, booked "+  
					"  from ( " + bang + ") tkn "+
					"  left join NHOMKENH kbh on kbh.pk_seq = tkn.kbh_fk "+
					"  inner join sanpham sp on sp.pk_seq = tkn.sanpham_fk "+
					"  left join donvikinhdoanh dvkd on dvkd.pk_seq = sp.dvkd_fk "+
					"  inner join kho k on k.pk_seq = tkn.kho_fk "+
					"  left join quycach qc on  qc.sanpham_fk = sp.pk_seq and sp.dvdl_fk =qc.dvdl1_fk  " +
					"  and qc.dvdl2_fk= 100018"+
					"  left join donvidoluong dvdl on dvdl.pk_seq = sp.dvdl_fk "+
					"  left join chungloai cl on cl.pk_seq = sp.chungloai_fk "+
					"  left join nhanhang nh on nh.pk_seq = sp.nhanhang_fk  " +
					"  LEFT JOIN KHACHHANG KH ON KH.PK_SEQ= TKN.KHACHHANG_FK "+
					"  where 1=1 " + condition;

		}
		else if(obj.getLoaikho().equals("6"))
		{
			tableNAME = " NHAPP_KHO_DDKD ";
			tableNAME2 = " NHAPP_KHO_DDKD_CHITIET ";
			bang =  "  select ddkd_fk ,kho_fk, nhomkenh_fk as kbh_fk, sanpham_fk, npp_fk, sum(soluong) as soluong, sum(AVAILABLE) as AVAILABLE, sum(booked) as booked,'' solo,'' as ngayhethan " + 
					"  from  " + tableNAME + 
					"  where ddkd_fk > 0 and npp_fk = '" + manpp + "' " + condition2 +
					"  group by ddkd_fk, kho_fk, nhomkenh_fk, sanpham_fk, npp_fk ";

			if(obj.getLaysolo().trim().equals("1"))
			{
				bang =  "  select ddkd_fk, kho_fk, nhomkenh_fk as kbh_fk, sanpham_fk, npp_fk, sum(soluong) as soluong, sum(AVAILABLE) as AVAILABLE, sum(booked) as booked, solo, ngayhethan " + 
						"  from  " + tableNAME2 + 
						"  where ddkd_fk > 0 and npp_fk = '" + manpp + "' " + condition2 + 
						"  group by ddkd_fk, kho_fk, nhomkenh_fk, sanpham_fk, npp_fk, solo, ngayhethan ";
			}

			sql = 	"  select NCC.TEN AS DOITUONG  ,tkn.SoLuong,solo, ngayhethan,kbh.ten as Channel,sp.ma as Sku_code,sp.ten as SKU,tkn.AVAILABLE as Piece,k.ten as Warehouse, "+
					"  tkn.npp_fk as Distributor_code,"+
					"  nh.ten as Brans, isnull(cast(tkn.AVAILABLE/cast(qc.soluong1 as float) as float), 0) as Quantily,"+
					"  dvkd.donvikinhdoanh as Business_unit,cl.ten as Category, "+
					"  tkn.AVAILABLE , "+
					"  ISNULL ( ( select dongia from BANGGIABANLENPP_SANPHAM where sanpham_fk = sp.pk_seq and BANGGIABLNPP_FK in ( select pk_seq from BANGGIABANLENPP where DVKD_FK = '100000' and pk_seq in ( select BANGGIABLNPP_FK from BANGGIABANLENPP_NPP where NPP_FK = " + manpp +  " ) ) ), 0) AS DonGia, booked "+  
					"  from ( " + bang + ") tkn "+
					"  left join NHOMKENH kbh on kbh.pk_seq = tkn.kbh_fk "+
					"  inner join sanpham sp on sp.pk_seq = tkn.sanpham_fk "+
					"  left join donvikinhdoanh dvkd on dvkd.pk_seq = sp.dvkd_fk "+
					"  inner join kho k on k.pk_seq = tkn.kho_fk "+
					"  left join quycach qc on  qc.sanpham_fk = sp.pk_seq and sp.dvdl_fk =qc.dvdl1_fk  " +
					"  and qc.dvdl2_fk= 100018"+
					"  left join donvidoluong dvdl on dvdl.pk_seq = sp.dvdl_fk "+
					"  left join chungloai cl on cl.pk_seq = sp.chungloai_fk "+
					"  left join nhanhang nh on nh.pk_seq = sp.nhanhang_fk  " +
					"  LEFT JOIN DAIDIENKINHDOANH NCC ON NCC.PK_SEQ= TKN.DDKD_FK "+
					"  where 1=1 " + condition;
		}else {


			tableNAME = " NHAPP_KHO_NCC ";
			tableNAME2 = " NHAPP_KHO_CHITIET_NCC ";

			bang =  "  select ncc_fk,kho_fk, nhomkenh_fk as kbh_fk, sanpham_fk, npp_fk, sum(soluong) as soluong, sum(AVAILABLE) as AVAILABLE, sum(booked) as booked,'' solo,'' as ngayhethan " + 
					"  from  " + tableNAME + 
					"  where npp_fk = '" + manpp + "' " + condition2 +
					"  group by ncc_fk, kho_fk, nhomkenh_fk, sanpham_fk, npp_fk ,ncc_fk";

			if(obj.getLaysolo().trim().equals("1"))
			{
				bang =  "  select ncc_fk, kho_fk, nhomkenh_fk as kbh_fk, sanpham_fk, npp_fk, sum(soluong) as soluong, sum(AVAILABLE) as AVAILABLE, sum(booked) as booked, solo, ngayhethan " + 
						"  from  " + tableNAME2 + 
						"  where npp_fk = '" + manpp + "' " + condition2 + 
						"  group by kho_fk, nhomkenh_fk, sanpham_fk, npp_fk, solo, ngayhethan ,ncc_fk ";
			}

			sql = 	"  select NCC.TEN AS DOITUONG  ,tkn.SoLuong,solo, ngayhethan,kbh.ten as Channel,sp.ma as Sku_code,sp.ten as SKU,tkn.AVAILABLE as Piece,k.ten as Warehouse, "+
					"  tkn.npp_fk as Distributor_code,"+
					"  nh.ten as Brans, isnull(cast(tkn.AVAILABLE/cast(qc.soluong1 as float) as float), 0) as Quantily,"+
					"  dvkd.donvikinhdoanh as Business_unit,cl.ten as Category, "+
					"  tkn.AVAILABLE , "+
					"  ISNULL ( ( select dongia from BANGGIABANLENPP_SANPHAM where sanpham_fk = sp.pk_seq and BANGGIABLNPP_FK in ( select pk_seq from BANGGIABANLENPP where DVKD_FK = '100000' and pk_seq in ( select BANGGIABLNPP_FK from BANGGIABANLENPP_NPP where NPP_FK = " + manpp +  " ) ) ), 0) AS DonGia, booked "+  
					"  from ( " + bang + ") tkn "+
					"  left join NHOMKENH kbh on kbh.pk_seq = tkn.kbh_fk "+
					"  inner join sanpham sp on sp.pk_seq = tkn.sanpham_fk "+
					"  left join donvikinhdoanh dvkd on dvkd.pk_seq = sp.dvkd_fk "+
					"  inner join kho k on k.pk_seq = tkn.kho_fk "+
					"  left join quycach qc on  qc.sanpham_fk = sp.pk_seq and sp.dvdl_fk =qc.dvdl1_fk  " +
					"  and qc.dvdl2_fk= 100018"+
					"  left join donvidoluong dvdl on dvdl.pk_seq = sp.dvdl_fk "+
					"  left join chungloai cl on cl.pk_seq = sp.chungloai_fk "+
					"  left join nhanhang nh on nh.pk_seq = sp.nhanhang_fk  " +
					"  LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ= TKN.NCC_FK "+
					"  where 1=1 " + condition;
		}
			
		System.out.println("GET DU LIEU : "+sql);
		rs = db.get(sql);

		int i = 2;
		if(rs != null)
		{
			try 
			{
				// se do rong cho cac cot se dung
				cells.setColumnWidth(0, 25.0f);
				cells.setColumnWidth(1, 25.0f);
				cells.setColumnWidth(2, 30.0f);
				cells.setColumnWidth(3, 15.0f);
				cells.setColumnWidth(4, 15.0f);			
				cells.setColumnWidth(5, 15.0f);	
				cells.setColumnWidth(6, 15.0f);	
				cells.setColumnWidth(7, 15.0f);

				Cell cell = null;
				while(rs.next())// lap den cuoi bang du lieu
				{
					//lay tu co so du lieu, gan bien
					String Channel = rs.getString("Channel");
					String SKU =rs.getString("SKU");
					String Piece =rs.getString("Piece");			

					String Warehouse = rs.getString("Warehouse");
					String DistributorCode =rs.getString("Distributor_code");
					String SkuCode = rs.getString("SKU_code");		
					double Quantily = rs.getDouble("Quantily");
					String BusinessUnit = rs.getString("Business_unit");
					String Category = rs.getString("Category");
					String Brands = rs.getString("Brans");
					double DonGia = rs.getDouble("DonGia");
					double Amount = rs.getDouble("AVAILABLE")*DonGia;
					cell = cells.getCell("AA" + Integer.toString(i)); cell.setValue(Channel);
					cell = cells.getCell("AB" + Integer.toString(i)); cell.setValue(SKU);
					cell = cells.getCell("AC" + Integer.toString(i)); cell.setValue(rs.getDouble("AVAILABLE"));
					cell = cells.getCell("AD" + Integer.toString(i)); cell.setValue(Warehouse);				
					cell = cells.getCell("AE" + Integer.toString(i)); cell.setValue(DistributorCode);
					cell = cells.getCell("AF" + Integer.toString(i)); cell.setValue(SkuCode);
					cell = cells.getCell("AG" + Integer.toString(i)); cell.setValue(Quantily);
					cell = cells.getCell("AH" + Integer.toString(i)); cell.setValue(BusinessUnit);
					cell = cells.getCell("AI" + Integer.toString(i)); cell.setValue(Category);
					cell = cells.getCell("AJ" + Integer.toString(i)); cell.setValue(Brands);
					cell = cells.getCell("AK" + Integer.toString(i)); cell.setValue(Amount);
					cell = cells.getCell("AL" + Integer.toString(i)); cell.setValue( rs.getDouble("BOOKED"));
					cell = cells.getCell("AM" + Integer.toString(i)); cell.setValue( rs.getDouble("SoLuong"));
					cell = cells.getCell("AN" + Integer.toString(i)); cell.setValue( rs.getString("doituong"));
					
					if(obj.getLaysolo().trim().equals("1"))
					{
						cell = cells.getCell("AO"+  Integer.toString(i)); 	cell.setValue(rs.getString("solo"));  ReportAPI.setCellHeader(cell);
						cell = cells.getCell("AP"+  Integer.toString(i)); 	cell.setValue(rs.getString("ngayhethan"));  ReportAPI.setCellHeader(cell);
					}

					i++;
				}

				if(i==2)
					throw new Exception("Xin loi.Khong co bao cao trong thoi gian nay...!");
				//create pivot
				getAn(workbook,49);

			}catch(Exception ex)
			{
				ex.printStackTrace();
				System.out.println(ex.toString());
				throw new Exception(ex.getMessage());

			}
			finally{
				if(rs!=null)
					rs.close();
				if(db != null)
					db.shutDown();

			}
		}	    
		return true;
	}
	
	private void getCellStyle(Workbook workbook, String a, Color mau, Boolean dam, int size)
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();
		Style style;
		Cell cell = cells.getCell(a); 
		style = cell.getStyle();
		Font font1 = new Font();
		font1.setColor(mau);
		font1.setBold(dam);
		font1.setSize(size);
		style.setFont(font1);
		cell.setStyle(style);
	}
	
	private void getAn(Workbook workbook,int i)
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	    for(int j = 26; j <= i; j++)
	    { 
	    	cells.hideColumn(j);
	    }
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}
