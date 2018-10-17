package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderType;
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

public class DistributionTT_Svl extends HttpServlet
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DistributionTT_Svl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String userTen = (String) session.getAttribute("userTen");
		Utility util = new Utility();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		IStockintransit obj = new Stockintransit();
		obj.settungay("");
		obj.setdenngay("");
		obj.setdvdlId("");
		obj.setuserId(userId);
		obj.init();
		session.setAttribute("checkedSKU", "");
		session.setAttribute("obj", obj);
		session.setAttribute("loi", "");
		session.setAttribute("userId", userId);
		session.setAttribute("userTen", userTen);
		String nextJSP = "/TraphacoHYERP/pages/Center/DistributionTT.jsp";
		response.sendRedirect(nextJSP);
	}

	private String getPiVotName()
	{
		String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		String name = sdf.format(cal.getTime());
		name = name.replaceAll("-", "");
		name = name.replaceAll(" ", "_");
		name = name.replaceAll(":", "");
		return "_" + name;

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		OutputStream out = response.getOutputStream();

		IStockintransit obj = new Stockintransit();
		boolean bfasle = true;
		Utility util = new Utility();
		try
		{
			HttpSession session = request.getSession();
			String userTen = (String) session.getAttribute("userTen");
			if (userTen == null)
				userTen = "";
			obj.setuserTen(userTen);
			String tungay = util.antiSQLInspection(request.getParameter("tungay"));
			if (tungay == null)
				tungay = "";
			obj.settungay(tungay);

			String userId = (String) session.getAttribute("userId");
			obj.setuserId(userId);
			String denngay = util.antiSQLInspection(request.getParameter("denngay"));
			if (denngay == null)
				denngay = "";
			obj.setdenngay(denngay);

			String skuId = util.antiSQLInspection(request.getParameter("skuid"));
			if (skuId == null)
				skuId = "";
			obj.setsanphamId(skuId);

			String manpp = util.antiSQLInspection(request.getParameter("nppId"));
			if (manpp == null)
				manpp = "";
			obj.setnppId(manpp);

			obj.setvungId(util.antiSQLInspection(request.getParameter("vungId")) != null ? util.antiSQLInspection(request.getParameter("vungId")) : "");
			obj.setkhuvucId(util.antiSQLInspection(request.getParameter("khuvucId")) != null ? util.antiSQLInspection(request.getParameter("khuvucId")) : "");
			obj.setkenhId(util.antiSQLInspection(request.getParameter("kenhId")) != null ? util.antiSQLInspection(request.getParameter("kenhId")) : "");
			obj.setTtId(util.antiSQLInspection(request.getParameter("ttId")) != null ? util.antiSQLInspection(request.getParameter("ttId")) : "");
			obj.setnhanhangId(util.antiSQLInspection(request.getParameter("nhanhangId")) != null ? util.antiSQLInspection(request.getParameter("nhanhangId")) : "");
			obj.setchungloaiId(util.antiSQLInspection(request.getParameter("chungloaiId")) != null ? util.antiSQLInspection(request.getParameter("chungloaiId")) : "");
			obj.setdvkdId(util.antiSQLInspection(request.getParameter("dvkdId")) != null ? util.antiSQLInspection(request.getParameter("dvkdId")) : "");
			obj.setDdkd(util.antiSQLInspection(request.getParameter("ddkdId")) != null ? util.antiSQLInspection(request.getParameter("ddkdId")) : "");
			obj.setLaytheo(request.getParameter("laytheo"));

			obj.SetNhoSPId(util.antiSQLInspection(request.getParameter("nhomspid")) != null ? util.antiSQLInspection(request.getParameter("nhomspid")) : "");

			obj.settype(request.getParameter("typeid"));

			obj.setdvdlId("");
			String action = request.getParameter("action");
			String[] skutest = request.getParameterValues("spId");
			String whereSKU = "";
			String chuoichuyen = "";
			dbutils db = new dbutils();
			if (skutest != null)
			{
				for (int i = 0; i < skutest.length; i++)
				{

					whereSKU += skutest[i] + ",";
					chuoichuyen = chuoichuyen + "," + skutest[i];
					String sql = "insert into test (a) values (" + skutest[i] + ")";
					db.update(sql);
				}
			}
			
			 bfasle = false;
			session.setAttribute("checkedSKU", skutest);
			if (whereSKU == "") {
				bfasle = false;
			} else {
				whereSKU = " (" + whereSKU.substring(0, whereSKU.length() - 1)
						+ ")";
				bfasle = true;
			}
			obj.setsanphamId(whereSKU);
			
			// System.out.println("chuoichuyen  "+ chuoichuyen);
			db.shutDown();

			if (action.equals("create"))
			{

				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "Attachment; filename=BaoCaoDoPhu(TT)_" + util.setTieuDe(obj) + ".xlsm");
				if(obj.getLaytheo().equals("1"))
					CreatePivotTable(out, response, request, obj, bfasle);
				else
					CreatePivotTableTQ(out, response, request, obj, bfasle);
			} 
			else if (action.equals("getdata")){
				session.setAttribute("checkedSKU", "");
				session.setAttribute("obj", obj);
				session.setAttribute("loi", "");
				session.setAttribute("userId", obj.getuserId());
				session.setAttribute("userTen", obj.getuserTen());
				obj.init();
				if(obj.getLaytheo().equals("1"))
					obj.setRSBaocao(CreateRSData(obj));
				else
					obj.setRSBaocao(CreateRSDataTQ(obj));
				/*ResultSet r = obj.getRSBaocao();
				while(r.next())
					System.out.println("loop");*/
				session.setAttribute("obj", obj);
				String nextJSP = "/TraphacoHYERP/pages/Center/DistributionTT.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				session.setAttribute("checkedSKU", "");
				session.setAttribute("obj", obj);
				session.setAttribute("loi", "");
				session.setAttribute("userId", obj.getuserId());
				session.setAttribute("userTen", obj.getuserTen());
				obj.init();
				session.setAttribute("obj", obj);
				String nextJSP = "/TraphacoHYERP/pages/Center/DistributionTT.jsp";
				response.sendRedirect(nextJSP);
			}
		} catch (Exception ex)
		{
			ex.printStackTrace();

			// say sorry
			response.setContentType("text/html");
			PrintWriter writer = new PrintWriter(out);

			writer.println("<html>");
			writer.println("<head>");
			writer.println("<title>sorry</title>");
			writer.println("</head>");
			writer.println("<body>");
			writer.println("<h1>Xin loi, khong the tao pivot table...</h1>");
			ex.printStackTrace(writer);
			writer.println("</body>");
			writer.println("</html>");
			writer.close();
		}
	}

	private void CreatePivotTableTQ(OutputStream out,
			HttpServletResponse response, HttpServletRequest request,
			IStockintransit obj, boolean bfasle) throws IOException {


		String chuoi = getServletContext().getInitParameter("path") + "\\Distribution(TQ).xlsm";

		FileInputStream fstream = new FileInputStream(chuoi);

		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateStaticHeader(workbook, obj.gettungay(), obj.getdenngay(), obj.getuserTen());

		CreateStaticDataTQ(workbook, obj, bfasle);
		HttpSession session = request.getSession();

		obj.init();
		session.setAttribute("obj", obj);

		System.out.println("___"+bfasle);
		
		workbook.save(out);
		fstream.close();
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Center/DistributionTT.jsp";
		response.sendRedirect(nextJSP);
		
	
	}

	private void CreatePivotTable(OutputStream out, HttpServletResponse response, HttpServletRequest request, IStockintransit obj, boolean bfasle) throws IOException
	{

		String chuoi = getServletContext().getInitParameter("path") + "\\Distribution(TT).xlsm";

		FileInputStream fstream = new FileInputStream(chuoi);

		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateStaticHeader(workbook, obj.gettungay(), obj.getdenngay(), obj.getuserTen());

		CreateStaticData(workbook, obj, bfasle);
		HttpSession session = request.getSession();

		obj.init();
		session.setAttribute("obj", obj);

		System.out.println("___"+bfasle);
		
		workbook.save(out);
		fstream.close();
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Center/DistributionTT.jsp";
		response.sendRedirect(nextJSP);
		
	}

	private void CreateStaticHeader(Workbook workbook, String dateFrom, String dateTo, String UserName)
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		//worksheet.setName("Sheet1");
		Cells cells = worksheet.getCells();

		Style style;
		// cells.setColumnWidth(0, 200.0f);
		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
		cell.setValue("BÁO CÁO ĐỘ PHỦ SẢN PHẨM");

		style = cell.getStyle();

		Font font2 = new Font();
		font2.setColor(Color.RED);// mau chu
		font2.setSize(14);// size chu
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu
		cell.setStyle(style);
		cell = cells.getCell("A2");
		getCellStyle(workbook, "A2", Color.NAVY, true, 10);
		cell.setValue("Từ ngày " + dateFrom);
		cell = cells.getCell("B2");
		getCellStyle(workbook, "B2", Color.NAVY, true, 10);
		cell.setValue("Tới Ngày " + dateTo);

		cell = cells.getCell("A3");
		getCellStyle(workbook, "A3", Color.NAVY, true, 10);
		cell.setValue("Ngày báo cáo: " + this.getDateTime());
		cell = cells.getCell("A4");
		getCellStyle(workbook, "A4", Color.NAVY, true, 10);
		cell.setValue("Được tạo bởi:  " + UserName);
	}

	private void CreateStaticData(Workbook workbook, IStockintransit obj, boolean bfasle)
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		ResultSet rs=null;
		if(!obj.gettype().equals("2"))
		{
			String[] param = new String[16];
			param[0] = obj.getnppId().equals("") ? null : obj.getnppId();
			param[1] = obj.gettungay();
			param[2] = obj.getdenngay();
			param[3] = obj.getkenhId().equals("") ? null : obj.getkenhId();
			param[4] = obj.getnhanhangId().equals("") ? null : obj.getnhanhangId();
			param[5] = obj.getchungloaiId().equals("") ? null : obj.getchungloaiId();
			param[6] = obj.getdvkdId().equals("") ? null : obj.getdvkdId();

			param[7] = obj.getTtId() == "" ? null : obj.getTtId();
			param[8] = obj.getvungId() == "" ? null : obj.getvungId();
			param[9] = obj.getsanphamId() == "" ? null : obj.getsanphamId();
			param[10] = obj.getuserId();
			param[11] = "1";// LAY BAO CAO CENTER
			param[12] = obj.gettype();// type=1 la lay bao cao theo thoi gia
			param[13] = obj.GetNhoSPId() == "" ? null : obj.GetNhoSPId();
			param[14] = obj.getDdkd() == "" ? null : obj.getDdkd();
			param[15] = obj.getSpId() == "" ? null : obj.getSpId();
			 rs = db.getRsByPro("REPORT_DOPHUSANPHAM", param);
		}
		else 
		{
			
			String sku="''";
			
			if(obj.getsanphamId().length()>0)
			{
				sku=  " STUFF  "+      
						  " (     "+
							"	  (   "+   
							"		select DISTINCT TOP 100 PERCENT ' , ' + sp.ten  "+
							"		from sanpham sp  "+
							"		where sp.PK_SEQ in "+obj.getsanphamId()+"  "+
							"		ORDER BY ' , '  +sp.ten  "+
							"		FOR XML PATH('')       "+
							"	 ), 1, 2, ''    "+
							" )    ";
			}
			
			
			String query=
		"\n	select ROW_NUMBER() OVER (PARTITION BY ("+sku+"), npp.pk_seq order by ("+sku+")) as STT," +
		"	kbh.ten as channel, v.ten as region,dvkd.diengiai as unit, kv.ten as Area, npp.pk_seq as idnpp,npp.sitecode ,   "+
		"\n			npp.ten as distributor,kh.mafast +'_'+kh.ten as custommer, "+sku+"  as sku, lch.diengiai as outlet_type, vt.diengiai as	outlet_location, hch.diengiai as outlet_class, " +
		"\n		sph.outlet, SUM(sph.volume) AS volume, sph.baophu, sph.volume as tongkh , SUM(sph.SOLUONG) AS SOLUONG, "+ 
		"\n		 isnull(qh.ten,'na') as quanhuyen, isnull(tt.ten,'na') as tinhthanh," +
		"\n	COUNT(KH.MAFAST +'_'+KH.TEN) OVER (PARTITION BY ("+sku+"), npp.pk_seq) AS SOKH, " +
		"\n	SUM(sum(SPH.SOLUONG)) OVER (PARTITION BY ("+sku+"), npp.pk_seq) AS TONGSOLUONG, " +
		"\n	SUM(SUM(SPH.VOLUME)) OVER (PARTITION BY ("+sku+"), npp.pk_seq) AS TONGDOANHSO  "+
		"\n	from  "+
		"\n	(	  "+
		"\n		select  sp.dvkd_fk,dh.npp_fk,dh.khachhang_fk,1 as outlet ,   "+
		"\n			isnull (sum(soluong* giamua),0) as volume, sum(soluong) as soluong  , 1 as baophu  "+ 
		"\n			,sum(sp.trongluong*dhsp.soluong) as sanluong  "+
		"\n		from donhang_sanpham dhsp inner join donhang dh on dh.pk_seq = dhsp.donhang_fk  "+
		"\n		inner join sanpham sp on sp.pk_seq=dhsp.sanpham_fk   "+
		"\n		 where dh.trangthai =1    "+
		"\n		and dh.tonggiatri > 0 and dh.ngaynhap  >= '"+obj.gettungay()+"' and dh.ngaynhap <='"+obj.getdenngay()+"'   ";
		if(obj.getDdkd().length()>0)
		{
			query+=" and dh.DDKD_DK ="+obj.getDdkd()+ " ";
		}
		if(obj.getsanphamId().length()>0)
		{
				query+=" and dhsp.sanpham_fk in  "+obj.getsanphamId()+" " ;
		}
		
		query+=
		"\n		 group by dh.npp_fk,dh.khachhang_fk,sp.dvkd_fk   "+
		"\n		 union all  "+
		"\n		select sp.dvkd_fk,dh.npp_fk,dh.khachhang_fk ,0 as outlet  ,     "+
		"\n		-(1)* sum( isnull(dh_sp.giamua, dh_sp1.giamua)* isnull(dh_sp.soluong, dh_sp1.soluong))  as volume,  "+
		"\n		(-1)*sum( isnull(dh_sp.soluong,   "+
		"\n			dh_sp1.soluong))  as soluong, 1 as baophu   ,(-1)*sum(sp.trongluong*isnull(dh_sp.soluong,dh_sp1.soluong)) as sanluong  "+
		"\n		from  donhangtrave dh    "+
		"\n		left outer join  donhangtrave_sanpham dh_sp on dh_sp.donhangtrave_fk = dh.pk_seq   	 "+
		"\n		left outer join  donhang_sanpham dh_sp1 on  dh.donhang_fk = dh_sp1.donhang_fk    "+
		"\n		left join sanpham sp on sp.pk_seq=isnull(dh_sp.sanpham_fk,dh_sp1.sanpham_fk) "+
		"\n		where dh.trangthai = 3 "+
		"\n		and dh.ngaynhap >='"+obj.gettungay()+"' and dh.ngaynhap <= '"+obj.getdenngay()+"' ";
		if(obj.getDdkd().length()>0)
		{
			query+=" and dh.DDKD_DK ="+obj.getDdkd()+ " ";
		}
	
	query+=
		"\n		group by dh.npp_fk,dh.khachhang_fk,sp.dvkd_fk "+
		"\n	)sph 	 "+
		"\n	 inner join khachhang kh on kh.pk_seq = sph.khachhang_fk and kh.npp_fk = sph.npp_fk "+
		"\n	inner join donvikinhdoanh dvkd on dvkd.pk_seq	=sph.dvkd_fk "+
		"\n	inner join nhaphanphoi npp on npp.pk_seq = sph.npp_fk   "+
		"\n	inner join khuvuc kv on kv.pk_seq = npp.khuvuc_fk  "+
		"\n	inner join vung v on v.pk_seq = kv.vung_fk   "+
		"\n	left join loaicuahang lch on lch.pk_seq = kh.lch_fk  "+
		"\n	left join hangcuahang hch on hch.pk_seq = kh.hch_fk "+
		"\n	left join vitricuahang vt on vt.pk_seq = kh.vtch_fk   "+
		"\n	left join kenhbanhang kbh on kbh.pk_seq = kh.kbh_fk  "+
		"\n	left join "+ 
		"\n	( "+
		"\n		select distinct a.kh_fk,b.diengiai from nhomkhachhang_khachhang a  "+
		"\n		inner join nhomkhachhang b on b.pk_seq= a.nkh_fk "+
		"\n	) nhomkh on nhomkh.kh_fk = kh.pk_seq  "+
		"\n	left join tinhthanh tt on kh.tinhthanh_fk = tt.pk_seq  "+
		"\n	left join quanhuyen qh on kh.quanhuyen_fk = qh.pk_seq "+
		"\n	where 1=1 ";
			
			if(obj.getkenhId().length()>0)
			{
				query+=" and kbh.pk_seq ="+obj.getkenhId()+ " ";
			}
			if(obj.getnppId().length()>0)
			{
				query+=" and npp.pk_seq ="+obj.getnppId()+ " ";
			}
			
			if(obj.getvungId().length()>0)
			{
				query+=" and v.pk_seq ="+obj.getvungId()+ " ";
			}
			if(obj.getTtId().length()>0)
			{
				query+=" and tt.pk_seq ="+obj.getTtId()+ " ";
			}

			query += "\n GROUP BY kbh.ten, v.ten, dvkd.diengiai, kv.ten, npp.pk_seq, npp.sitecode,   "+
				"\n	npp.ten, kh.mafast +'_'+kh.ten, lch.diengiai, vt.diengiai, hch.diengiai, " +
				"\n	sph.outlet, sph.baophu, sph.volume, "+ 
				"\n	 isnull(qh.ten,'na'), isnull(tt.ten,'na')";
			System.out.println("__________"+query);
			rs=db.get(query);
	}

		int i = 12;


			try
			{
				cells.setColumnWidth(0, 7.0f);
				cells.setColumnWidth(1, 15.0f);
				cells.setColumnWidth(2, 15.0f);
				cells.setColumnWidth(3, 35.0f);
				cells.setColumnWidth(4, 35.0f);
				cells.setColumnWidth(5, 50.0f);
				cells.setColumnWidth(6, 10.0f);
				cells.setColumnWidth(7, 20.0f);

				Cell cell = null;
				
				int stt = 1;
				
				if (rs != null)
				{
					while (rs.next())// lap den cuoi bang du lieu
					{
						//String Channel = rs.getString("Channel");
	
						//String SaleRep = rs.getString("Sale_rep");
						int sott = rs.getInt("STT");
						String Region = rs.getString("Region");
						String tinhthanh = rs.getString("tinhthanh");
						//String Area = rs.getString("Area");
						String Distributor = rs.getString("Distributor");
						String Customer = rs.getString("custommer");
						String SKU = rs.getString("SKU");
						int sokh = rs.getInt("SOKH");
						int tongsl = rs.getInt("TONGSOLUONG");
						double tongds = rs.getDouble("TONGDOANHSO");
						
						int sl = rs.getInt("SOLUONG");
						double ds = rs.getDouble("VOLUME");
						//String OutletType = rs.getString("outlet_type");
						//String OutletLocation = rs.getString("outlet_location");
						//String OutletClass = rs.getString("outlet_class");
						//String GroupCustomer = "";//rs.getString("group_customer");
						//String Brand = "";//rs.getString("Brand");
						//String Catogery = "";//rs.getString("catogery");
						//String NhomSP = "";//rs.getString("NHOMSP");
	
						//String SumOutlet = rs.getString("outlet");
						//double VOLUME = rs.getDouble("VOLUME");
						//float tongkh=rs.getFloat("tongkh");
						//String quanhuyen = rs.getString("quanhuyen");
						
						if(sott == 1){
							cell = cells.getCell("A" + Integer.toString(i)); setCellBackground(cell, 1, true, 0);
							cell.setValue("");
							cell = cells.getCell("B" + Integer.toString(i)); setCellBackground(cell, 1, true, 0);
							cell.setValue(Region);
							cell = cells.getCell("C" + Integer.toString(i)); setCellBackground(cell, 1, true, 0);
							cell.setValue(tinhthanh);
							cell = cells.getCell("D" + Integer.toString(i)); setCellBackground(cell, 1, true, 0);
							cell.setValue(Distributor);
							cell = cells.getCell("E" + Integer.toString(i)); setCellBackground(cell, 1, true, 0);
							cell.setValue(SKU);
							cell = cells.getCell("F" + Integer.toString(i)); setCellBackground(cell, 1, true, 0);
							cell.setValue(sokh);
							cell = cells.getCell("G" + Integer.toString(i)); setCellBackground(cell, 1, true, 0);
							cell.setValue(tongsl);
							cell = cells.getCell("H" + Integer.toString(i)); setCellBackground(cell, 1, true, 0);
							cell.setValue(tongds);
							
							i++;
							cell = cells.getCell("A" + Integer.toString(i));
							cell.setValue(stt);
							/*cell = cells.getCell("B" + Integer.toString(i));
							cell.setValue(Region);
							cell = cells.getCell("C" + Integer.toString(i));
							cell.setValue(tinhthanh);
							cell = cells.getCell("D" + Integer.toString(i));
							cell.setValue(Distributor);*/
							cell = cells.getCell("E" + Integer.toString(i));
							cell.setValue(SKU);
							cell = cells.getCell("F" + Integer.toString(i));
							cell.setValue(Customer);
							cell = cells.getCell("G" + Integer.toString(i));
							cell.setValue(sl);
							cell = cells.getCell("H" + Integer.toString(i));
							cell.setValue(ds);
						}
						else{
							cell = cells.getCell("A" + Integer.toString(i));
							cell.setValue(stt);
							/*cell = cells.getCell("B" + Integer.toString(i));
							cell.setValue(Region);
							cell = cells.getCell("C" + Integer.toString(i));
							cell.setValue(tinhthanh);
							cell = cells.getCell("D" + Integer.toString(i));
							cell.setValue(Distributor);*/
							cell = cells.getCell("E" + Integer.toString(i));
							cell.setValue(SKU);
							cell = cells.getCell("F" + Integer.toString(i));
							cell.setValue(Customer);
							cell = cells.getCell("G" + Integer.toString(i));
							cell.setValue(sl);
							cell = cells.getCell("H" + Integer.toString(i));
							cell.setValue(ds);
						}
						stt++;
						i++;
					}
				}
				 System.out.println("so dong:"+i);
				if (rs != null)
					rs.close();
				
				
				//getAn(workbook, 27);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				db.shutDown();
			}
	}
	
	private ResultSet CreateRSData(IStockintransit obj)
	{
		dbutils db = new dbutils();
		ResultSet rs=null;
		if(!obj.gettype().equals("2"))
		{
			String[] param = new String[16];
			param[0] = obj.getnppId().equals("") ? null : obj.getnppId();
			param[1] = obj.gettungay();
			param[2] = obj.getdenngay();
			param[3] = obj.getkenhId().equals("") ? null : obj.getkenhId();
			param[4] = obj.getnhanhangId().equals("") ? null : obj.getnhanhangId();
			param[5] = obj.getchungloaiId().equals("") ? null : obj.getchungloaiId();
			param[6] = obj.getdvkdId().equals("") ? null : obj.getdvkdId();

			param[7] = obj.getTtId() == "" ? null : obj.getTtId();
			param[8] = obj.getvungId() == "" ? null : obj.getvungId();
			param[9] = obj.getsanphamId() == "" ? null : obj.getsanphamId();
			param[10] = obj.getuserId();
			param[11] = "1";// LAY BAO CAO CENTER
			param[12] = obj.gettype();// type=1 la lay bao cao theo thoi gia
			param[13] = obj.GetNhoSPId() == "" ? null : obj.GetNhoSPId();
			param[14] = obj.getDdkd() == "" ? null : obj.getDdkd();
			param[15] = obj.getSpId() == "" ? null : obj.getSpId();
			rs = db.getRsByPro("REPORT_DOPHUSANPHAM", param);
		}
		else 
		{
			
			String sku="''";
			
			if(obj.getsanphamId().length()>0)
			{
				sku=  " STUFF  "+      
						  " (     "+
							"	  (   "+   
							"		select DISTINCT TOP 100 PERCENT ' , ' + sp.ten  "+
							"		from sanpham sp  "+
							"		where sp.PK_SEQ in "+obj.getsanphamId()+"  "+
							"		ORDER BY ' , '  +sp.ten  "+
							"		FOR XML PATH('')       "+
							"	 ), 1, 2, ''    "+
							" )    ";
			}
			
			
			String query=
				"\n	select ROW_NUMBER() OVER (PARTITION BY ("+sku+"), npp.pk_seq order by ("+sku+")) as STT," +
				"	kbh.ten as channel, v.ten as region,dvkd.diengiai as unit, kv.ten as Area, npp.pk_seq as idnpp,npp.sitecode ,   "+
				"\n			npp.ten as distributor,kh.mafast +'_'+kh.ten as custommer, "+sku+"  as sku, lch.diengiai as outlet_type, vt.diengiai as	outlet_location, hch.diengiai as outlet_class, " +
				"\n		sph.outlet, SUM(sph.volume) AS volume, sph.baophu, sph.volume as tongkh , SUM(sph.SOLUONG) AS SOLUONG, "+ 
				"\n		 isnull(qh.ten,'na') as quanhuyen, isnull(tt.ten,'na') as tinhthanh," +
				"\n	COUNT(KH.MAFAST +'_'+KH.TEN) OVER (PARTITION BY ("+sku+"), npp.pk_seq) AS SOKH, " +
				"\n	SUM(sum(SPH.SOLUONG)) OVER (PARTITION BY ("+sku+"), npp.pk_seq) AS TONGSOLUONG, " +
				"\n	SUM(SUM(SPH.VOLUME)) OVER (PARTITION BY ("+sku+"), npp.pk_seq) AS TONGDOANHSO  "+
				"\n	from  "+
				"\n	(	  "+
				"\n		select  sp.dvkd_fk,dh.npp_fk,dh.khachhang_fk,1 as outlet ,   "+
				"\n			isnull (sum(soluong* giamua),0) as volume, sum(soluong) as soluong  , 1 as baophu  "+ 
				"\n			,sum(sp.trongluong*dhsp.soluong) as sanluong  "+
				"\n		from donhang_sanpham dhsp inner join donhang dh on dh.pk_seq = dhsp.donhang_fk  "+
				"\n		inner join sanpham sp on sp.pk_seq=dhsp.sanpham_fk   "+
				"\n		 where dh.trangthai =1    "+
				"\n		and dh.tonggiatri > 0 and dh.ngaynhap  >= '"+obj.gettungay()+"' and dh.ngaynhap <='"+obj.getdenngay()+"'   ";
				
				if(obj.getsanphamId().length()>0)
				{
						query+=" and dhsp.sanpham_fk in  "+obj.getsanphamId()+" " ;
				}
				if(obj.getDdkd().length()>0)
				{
					query+=" and dh.DDKD_DK ="+obj.getDdkd()+ " ";
				}
				query+=
				"\n		 group by dh.npp_fk,dh.khachhang_fk,sp.dvkd_fk   "+
				"\n		 union all  "+
				"\n		select sp.dvkd_fk,dh.npp_fk,dh.khachhang_fk ,0 as outlet  ,     "+
				"\n		-(1)* sum( isnull(dh_sp.giamua, dh_sp1.giamua)* isnull(dh_sp.soluong, dh_sp1.soluong))  as volume,  "+
				"\n		(-1)*sum( isnull(dh_sp.soluong,   "+
				"\n			dh_sp1.soluong))  as soluong, 1 as baophu   ,(-1)*sum(sp.trongluong*isnull(dh_sp.soluong,dh_sp1.soluong)) as sanluong  "+
				"\n		from  donhangtrave dh    "+
				"\n		left outer join  donhangtrave_sanpham dh_sp on dh_sp.donhangtrave_fk = dh.pk_seq   	 "+
				"\n		left outer join  donhang_sanpham dh_sp1 on  dh.donhang_fk = dh_sp1.donhang_fk    "+
				"\n		left join sanpham sp on sp.pk_seq=isnull(dh_sp.sanpham_fk,dh_sp1.sanpham_fk) "+
				"\n		where dh.trangthai = 3 "+
				"\n		and dh.ngaynhap >='"+obj.gettungay()+"' and dh.ngaynhap <= '"+obj.getdenngay()+"' ";
				if(obj.getDdkd().length()>0)
				{
					query+=" and dh.DDKD_DK ="+obj.getDdkd()+ " ";
				}
			
			query+=
				"\n		group by dh.npp_fk,dh.khachhang_fk,sp.dvkd_fk "+
				"\n	)sph 	 "+
				"\n	 inner join khachhang kh on kh.pk_seq = sph.khachhang_fk and kh.npp_fk = sph.npp_fk "+
				"\n	inner join donvikinhdoanh dvkd on dvkd.pk_seq	=sph.dvkd_fk "+
				"\n	inner join nhaphanphoi npp on npp.pk_seq = sph.npp_fk   "+
				"\n	inner join khuvuc kv on kv.pk_seq = npp.khuvuc_fk  "+
				"\n	inner join vung v on v.pk_seq = kv.vung_fk   "+
				"\n	left join loaicuahang lch on lch.pk_seq = kh.lch_fk  "+
				"\n	left join hangcuahang hch on hch.pk_seq = kh.hch_fk "+
				"\n	left join vitricuahang vt on vt.pk_seq = kh.vtch_fk   "+
				"\n	left join kenhbanhang kbh on kbh.pk_seq = kh.kbh_fk  "+
				"\n	left join "+ 
				"\n	( "+
				"\n		select distinct a.kh_fk,b.diengiai from nhomkhachhang_khachhang a  "+
				"\n		inner join nhomkhachhang b on b.pk_seq= a.nkh_fk "+
				"\n	) nhomkh on nhomkh.kh_fk = kh.pk_seq  "+
				"\n	left join tinhthanh tt on kh.tinhthanh_fk = tt.pk_seq  "+
				"\n	left join quanhuyen qh on kh.quanhuyen_fk = qh.pk_seq "+
				"\n	where 1=1 ";
					
					if(obj.getkenhId().length()>0)
					{
						query+=" and kbh.pk_seq ="+obj.getkenhId()+ " ";
					}
					if(obj.getnppId().length()>0)
					{
						query+=" and npp.pk_seq ="+obj.getnppId()+ " ";
					}
					if(obj.getvungId().length()>0)
					{
						query+=" and v.pk_seq ="+obj.getvungId()+ " ";
					}
					if(obj.getTtId().length()>0)
					{
						query+=" and tt.pk_seq ="+obj.getTtId()+ " ";
					}

					query += "\n GROUP BY kbh.ten, v.ten, dvkd.diengiai, kv.ten, npp.pk_seq, npp.sitecode,   "+
						"\n	npp.ten, kh.mafast +'_'+kh.ten, lch.diengiai, vt.diengiai, hch.diengiai, " +
						"\n	sph.outlet, sph.baophu, sph.volume, "+ 
						"\n	 isnull(qh.ten,'na'), isnull(tt.ten,'na')";
					System.out.println("__________"+query);
					rs=db.get(query);
			
		}
		//db.shutDown();
		return rs;
	} 


	private void CreateStaticDataTQ(Workbook workbook, IStockintransit obj,
			boolean bfasle) {

		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		ResultSet rs=null;
		String query = "";
		NumberFormat formatter2 = new DecimalFormat("#,###,###.##");
		if(!obj.gettype().equals("2"))
		{
			
			query = "\nSELECT ROW_NUMBER() OVER (PARTITION BY (SP.MA+' _ '+SP.TEN) order by (SP.MA+' _ '+SP.TEN)) as STT, " + 
				"\n			SP.MA+' _ '+SP.TEN AS SKU, V.TEN AS REGION, isnull(tt.TEN,'NA') as tinhthanh,   " + 
				"\n			NPP.PK_SEQ AS IDNPP, NPP.TEN AS DISTRIBUTOR, " + 
				"\n			SUM(SPH.VOLUME) AS VOLUME, SUM(SPH.SOLUONG) AS SOLUONG, " + 
				"\n			sum(SPH.SOKH) AS SOKH, " +
				"\n			sum(sum(SPH.SOKH)) OVER (PARTITION BY (SP.MA+' _ '+SP.TEN)) AS TONGSOKH, " + 
				"\n			sum(sum(SPH.SOLUONG)) OVER (PARTITION BY (SP.MA+' _ '+SP.TEN)) as TONGSOLUONG, " +
				"\n			sum(SUM(SPH.VOLUME)) OVER (PARTITION BY (SP.MA+' _ '+SP.TEN)) AS TONGDOANHSO, " +
				"\n			a.SOHD, " +
				"\n			sum(SUM(a.SOHD)) OVER (PARTITION BY (SP.MA+' _ '+SP.TEN)) AS TONGKHHD" + 
				"\n		FROM(  " + 
				"\n		SELECT  DH.NPP_FK, DHSP.SANPHAM_FK, COUNT(DISTINCT DH.KHACHHANG_FK) AS SOKH ,ISNULL (SUM((SOLUONG * GIAMUA) + (SOLUONG * GIAMUA * THUEVAT/100)),0) AS VOLUME, SUM(DHSP.SOLUONG) AS SOLUONG " + 
				"\n			FROM DONHANG_SANPHAM DHSP INNER JOIN DONHANG DH ON DH.PK_SEQ = DHSP.DONHANG_FK WHERE DH.TRANGTHAI =1  " + 
				"\n			AND DH.TONGGIATRI > 0 AND DH.NGAYNHAP  >= '"+obj.gettungay()+"' AND DH.NGAYNHAP <='"+obj.getdenngay()+"' ";
			if(obj.getnppId().length() > 0)	
				query += 
			"\n			AND DH.NPP_FK = '"+obj.getnppId()+"' ";
			if(obj.getDdkd().length() > 0)
				query +=
			"\nAND DH.DDKD_FK = '"+obj.getDdkd()+"' ";
			query +=
				"\n			GROUP BY DH.NPP_FK, DHSP.SANPHAM_FK " + 
				"\n			 UNION ALL " + 
				"\n			SELECT DH.NPP_FK,ISNULL(DH_SP.SANPHAM_FK, DH_SP1.SANPHAM_FK) AS SANPHAM_FK, 0 AS SOKH  ,    " + 
				"\n			-(1)* sum( ISNULL(DH_SP.GIAMUA, DH_SP1.GIAMUA)* ISNULL(DH_SP.SOLUONG, DH_SP1.SOLUONG))  AS VOLUME, (-1)* SUM(DH_SP.SOLUONG) AS SOLUONG  " + 
				"\n			FROM  DONHANGTRAVE DH   " + 
				"\n			LEFT OUTER JOIN  DONHANGTRAVE_SANPHAM DH_SP " + 
				"\n			 ON DH_SP.DONHANGTRAVE_FK = DH.PK_SEQ   	 " + 
				"\n			LEFT OUTER JOIN  DONHANG_SANPHAM DH_SP1 ON   " + 
				"\n			DH.DONHANG_FK = DH_SP1.DONHANG_FK    " + 
				"			WHERE DH.TRANGTHAI = 3 " + 
				"			AND DH.NGAYNHAP >='"+obj.gettungay()+"' AND DH.NGAYNHAP <= '"+obj.getdenngay()+"' ";
			if(obj.getnppId().length() > 0)	
				query += 
			"\n			AND DH.NPP_FK = '"+obj.getnppId()+"' ";
			if(obj.getDdkd().length() > 0)
				query +=
			"\nAND DH.DDKD_FK = '"+obj.getDdkd()+"' ";
			query +=	"			GROUP BY DH.NPP_FK, ISNULL(DH_SP.SANPHAM_FK, DH_SP1.SANPHAM_FK) " + 
				"\n			)SPH  " + 
				"\nINNER JOIN SANPHAM SP ON SP.PK_SEQ = SPH.SANPHAM_FK " + 
				"\nINNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = SPH.NPP_FK   " + 
				"\nINNER JOIN KHUVUC KV ON KV.PK_SEQ = NPP.KHUVUC_FK  " + 
				"\nINNER JOIN VUNG V ON V.PK_SEQ = KV.VUNG_FK   " + 
				"\nLEFT join TINHTHANH tt on tt.PK_SEQ = NPP.TINHTHANH_FK " + 
				"\n " + 
				"\nLEFT JOIN ( SELECT SP_FK, MAX(NSP_FK) AS NHOMSP  " + 
				"\nFROM NHOMSANPHAM_SANPHAM  GROUP BY SP_FK   " + 
				"\n) NSPSP ON NSPSP.SP_FK = SP.PK_SEQ  " + 
				"\nLEFT JOIN NHOMSANPHAM NSP ON NSP.PK_SEQ = NSPSP.NHOMSP   " + 
				"\nleft join (select COUNT(PK_SEQ) AS SOHD, NPP_FK from KHACHHANG where LEN(RTRIM(LTRIM(MaHD))) != 0 GROUP BY NPP_FK) a " + 
				"\non a.NPP_FK = SPH.NPP_FK " + 
				"\nWHERE 1=1  AND NPP.PK_SEQ IN  ( SELECT NPP_FK FROM PHAMVIHOATDONG WHERE " +
				"\nNHANVIEN_FK ='"+obj.getuserId()+"'" +
				")  ";
			if(obj.getvungId().length() > 0)
				query += "\nAND V.PK_SEQ='"+obj.getvungId()+"' ";
			if(obj.getTtId().length() > 0)
				query += "\nAND tt.PK_SEQ = '"+obj.getTtId()+"' ";
			if(obj.getsanphamId().length() > 0)
				query += "\nAND SP.PK_SEQ IN "+obj.getsanphamId()+" ";
			query += 
				"\nGROUP BY SP.MA+' _ '+SP.TEN, V.TEN, NPP.TEN, NPP.PK_SEQ, isnull(tt.TEN,'NA'), a.SOHD ";
		}
		else 
		{
			String sku="''";			
			if(obj.getsanphamId().length()>0)
			{
				sku=  " STUFF  "+      
						  " (     "+
							"	  (   "+   
							"		select DISTINCT TOP 100 PERCENT ' , ' + sp.ten  "+
							"		from sanpham sp  "+
							"		where sp.PK_SEQ in "+obj.getsanphamId()+"  "+
							"		ORDER BY ' , '  +sp.ten  "+
							"		FOR XML PATH('')       "+
							"	 ), 1, 2, ''    "+
							" )    ";
			}
			
			query = "\nSELECT ROW_NUMBER() OVER (PARTITION BY ("+sku+") order by ("+sku+")) as STT, " + 
			"\n			"+sku+" AS SKU, V.TEN AS REGION, isnull(tt.TEN,'NA') as tinhthanh,   " + 
			"\n			NPP.PK_SEQ AS IDNPP, NPP.TEN AS DISTRIBUTOR, " + 
			"\n			SUM(SPH.VOLUME) AS VOLUME, SUM(SPH.SOLUONG) AS SOLUONG, " + 
			"\n			sum(SPH.SOKH) AS SOKH, " +
			"\n			sum(sum(SPH.SOKH)) OVER (PARTITION BY ("+sku+")) AS TONGSOKH, " + 
			"\n			sum(sum(SPH.SOLUONG)) OVER (PARTITION BY ("+sku+")) as TONGSOLUONG, " +
			"\n			sum(SUM(SPH.VOLUME)) OVER (PARTITION BY ("+sku+")) AS TONGDOANHSO, " +
			"\n			a.SOHD, " +
			"\n			sum(SUM(a.SOHD)) OVER (PARTITION BY ("+sku+")) AS TONGKHHD" + 
			"\n		FROM(  " + 
			"\n		SELECT  DH.NPP_FK, DHSP.SANPHAM_FK, COUNT(DISTINCT DH.KHACHHANG_FK) AS SOKH ,ISNULL (SUM((SOLUONG * GIAMUA) + (SOLUONG * GIAMUA * THUEVAT/100)),0) AS VOLUME, SUM(DHSP.SOLUONG) AS SOLUONG " + 
			"\n			FROM DONHANG_SANPHAM DHSP INNER JOIN DONHANG DH ON DH.PK_SEQ = DHSP.DONHANG_FK WHERE DH.TRANGTHAI =1  " + 
			"\n			AND DH.TONGGIATRI > 0 AND DH.NGAYNHAP  >= '"+obj.gettungay()+"' AND DH.NGAYNHAP <='"+obj.getdenngay()+"' ";
			if(obj.getnppId().length() > 0)	
				query += 
			"\n			AND DH.NPP_FK = '"+obj.getnppId()+"' ";
			if(obj.getDdkd().length() > 0)
				query +=
			"\nAND DH.DDKD_FK = '"+obj.getDdkd()+"' ";
			query +=
				"\n			GROUP BY DH.NPP_FK, DHSP.SANPHAM_FK " + 
				"\n			 UNION ALL " + 
				"\n			SELECT DH.NPP_FK,ISNULL(DH_SP.SANPHAM_FK, DH_SP1.SANPHAM_FK) AS SANPHAM_FK, 0 AS SOKH  ,    " + 
				"\n			-(1)* sum( ISNULL(DH_SP.GIAMUA, DH_SP1.GIAMUA)* ISNULL(DH_SP.SOLUONG, DH_SP1.SOLUONG))  AS VOLUME, (-1)* SUM(DH_SP.SOLUONG) AS SOLUONG  " + 
				"\n			FROM  DONHANGTRAVE DH   " + 
				"\n			LEFT OUTER JOIN  DONHANGTRAVE_SANPHAM DH_SP " + 
				"\n			 ON DH_SP.DONHANGTRAVE_FK = DH.PK_SEQ   	 " + 
				"\n			LEFT OUTER JOIN  DONHANG_SANPHAM DH_SP1 ON   " + 
				"\n			DH.DONHANG_FK = DH_SP1.DONHANG_FK    " + 
				"			WHERE DH.TRANGTHAI = 3 " + 
				"			AND DH.NGAYNHAP >='"+obj.gettungay()+"' AND DH.NGAYNHAP <= '"+obj.getdenngay()+"' ";
			if(obj.getnppId().length() > 0)	
				query += 
			"\n			AND DH.NPP_FK = '"+obj.getnppId()+"' ";
			if(obj.getDdkd().length() > 0)
				query +=
			"\nAND DH.DDKD_FK = '"+obj.getDdkd()+"' ";
			query +=	"			GROUP BY DH.NPP_FK, ISNULL(DH_SP.SANPHAM_FK, DH_SP1.SANPHAM_FK) " + 
				"\n			)SPH  " + 
				"\nINNER JOIN SANPHAM SP ON SP.PK_SEQ = SPH.SANPHAM_FK " + 
				"\nINNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = SPH.NPP_FK   " + 
				"\nINNER JOIN KHUVUC KV ON KV.PK_SEQ = NPP.KHUVUC_FK  " + 
				"\nINNER JOIN VUNG V ON V.PK_SEQ = KV.VUNG_FK   " + 
				"\nLEFT join TINHTHANH tt on tt.PK_SEQ = NPP.TINHTHANH_FK " + 
				"\n " + 
				"\nLEFT JOIN ( SELECT SP_FK, MAX(NSP_FK) AS NHOMSP  " + 
				"\nFROM NHOMSANPHAM_SANPHAM  GROUP BY SP_FK   " + 
				"\n) NSPSP ON NSPSP.SP_FK = SP.PK_SEQ  " + 
				"\nLEFT JOIN NHOMSANPHAM NSP ON NSP.PK_SEQ = NSPSP.NHOMSP   " + 
				"\nleft join (select COUNT(PK_SEQ) AS SOHD, NPP_FK from KHACHHANG where LEN(RTRIM(LTRIM(MaHD))) != 0 GROUP BY NPP_FK) a " + 
				"\non a.NPP_FK = SPH.NPP_FK " + 
				"\nWHERE 1=1  AND NPP.PK_SEQ IN  ( SELECT NPP_FK FROM PHAMVIHOATDONG WHERE " +
				"\nNHANVIEN_FK ='"+obj.getuserId()+"'" +
				")  ";
			if(obj.getvungId().length() > 0)
				query += "\nAND V.PK_SEQ='"+obj.getvungId()+"' ";
			if(obj.getTtId().length() > 0)
				query += "\nAND tt.PK_SEQ = '"+obj.getTtId()+"' ";
			if(obj.getsanphamId().length() > 0)
				query += "\nAND SP.PK_SEQ IN "+obj.getsanphamId()+" ";
			query += 
				"\nGROUP BY V.TEN, NPP.TEN, NPP.PK_SEQ, isnull(tt.TEN,'NA'), a.SOHD ";
				
				System.out.println("__________"+query);
			
		}
		rs=db.get(query);
		int i = 9;


			try
			{// se do rong cho cac cot se dung
				cells.setColumnWidth(0, 7.0f);
				cells.setColumnWidth(1, 15.0f);
				cells.setColumnWidth(2, 15.0f);
				cells.setColumnWidth(3, 35.0f);
				cells.setColumnWidth(4, 35.0f);
				cells.setColumnWidth(5, 20.0f);
				cells.setColumnWidth(6, 20.0f);
				cells.setColumnWidth(7, 15.0f);
				cells.setColumnWidth(8, 15.0f);
				cells.setColumnWidth(9, 15.0f);

				Cell cell = null;
				
				int stt = 1;
				
				if (rs != null)
				{
					while (rs.next())// lap den cuoi bang du lieu
					{
						int sott = rs.getInt("STT");
						String Region = rs.getString("Region");
						String tinhthanh = rs.getString("tinhthanh");
						String Distributor = rs.getString("Distributor");
						String SKU = rs.getString("SKU");
						int sokh = rs.getInt("SOKH");
						int tongsokh = rs.getInt("TONGSOKH");
						int sokhhd = rs.getInt("SOHD");
						int tongsokhhd = rs.getInt("TONGKHHD");
						int sl = rs.getInt("SOLUONG");
						int tongsl = rs.getInt("TONGSOLUONG");
						double ds = rs.getDouble("VOLUME");
						double tongds = rs.getDouble("TONGDOANHSO");
						
						
						if(sott == 1){
							cell = cells.getCell("A" + Integer.toString(i)); setCellBackground(cell, 1, true, 0);
							cell = cells.getCell("B" + Integer.toString(i));
							cell.setValue("Tổng");
							cell = cells.getCell("E" + Integer.toString(i)); setCellBackground(cell, 1, true, 0);
							cell.setValue(SKU);
							cell = cells.getCell("F" + Integer.toString(i)); setCellBackground(cell, 1, true, 0);
							cell.setValue(tongsokhhd);
							cell = cells.getCell("G" + Integer.toString(i)); setCellBackground(cell, 1, true, 0);
							cell.setValue(tongsokh);
							cell = cells.getCell("H" + Integer.toString(i)); setCellBackground(cell, 1, true, 0);
							cell.setValue(formatter2.format(((float)tongsokh/(float)tongsokhhd * 100)) + "%");
							cell = cells.getCell("I" + Integer.toString(i)); setCellBackground(cell, 1, true, 0);
							cell.setValue(tongsl);
							cell = cells.getCell("J" + Integer.toString(i)); setCellBackground(cell, 1, true, 0);
							cell.setValue(tongds);
							
							i++;
							cell = cells.getCell("A" + Integer.toString(i)); setCellBackground(cell, 1, true, 0);
							cell.setValue(stt);
							cell = cells.getCell("B" + Integer.toString(i)); setCellBackground(cell, 1, true, 0);
							cell.setValue(Region);
							cell = cells.getCell("C" + Integer.toString(i)); setCellBackground(cell, 1, true, 0);
							cell.setValue(tinhthanh);
							cell = cells.getCell("D" + Integer.toString(i)); setCellBackground(cell, 1, true, 0);
							cell.setValue(Distributor);
							cell = cells.getCell("E" + Integer.toString(i)); setCellBackground(cell, 1, true, 0);
							cell.setValue(SKU);
							cell = cells.getCell("F" + Integer.toString(i)); setCellBackground(cell, 1, true, 0);
							cell.setValue(sokhhd);
							cell = cells.getCell("G" + Integer.toString(i)); setCellBackground(cell, 1, true, 0);
							cell.setValue(sokh);
							cell = cells.getCell("H" + Integer.toString(i)); setCellBackground(cell, 1, true, 0);
							cell.setValue(formatter2.format(((float)sokh/(float)sokhhd * 100)) + "%");
							cell = cells.getCell("I" + Integer.toString(i)); setCellBackground(cell, 1, true, 0);
							cell.setValue(sl);
							cell = cells.getCell("J" + Integer.toString(i)); setCellBackground(cell, 1, true, 0);
							cell.setValue(ds);
						}
						else{
							cell = cells.getCell("A" + Integer.toString(i)); setCellBackground(cell, 1, true, 0);
							cell.setValue(stt);
							cell = cells.getCell("B" + Integer.toString(i)); setCellBackground(cell, 1, true, 0);
							cell.setValue(Region);
							cell = cells.getCell("C" + Integer.toString(i)); setCellBackground(cell, 1, true, 0);
							cell.setValue(tinhthanh);
							cell = cells.getCell("D" + Integer.toString(i)); setCellBackground(cell, 1, true, 0);
							cell.setValue(Distributor);
							cell = cells.getCell("E" + Integer.toString(i)); setCellBackground(cell, 1, true, 0);
							cell.setValue(SKU);
							cell = cells.getCell("F" + Integer.toString(i)); setCellBackground(cell, 1, true, 0);
							cell.setValue(sokhhd);
							cell = cells.getCell("G" + Integer.toString(i)); setCellBackground(cell, 1, true, 0);
							cell.setValue(sokh);
							cell = cells.getCell("H" + Integer.toString(i)); setCellBackground(cell, 1, true, 0);
							cell.setValue((formatter2.format((float)sokh/(float)sokhhd * 100)) + "%");
							cell = cells.getCell("I" + Integer.toString(i)); setCellBackground(cell, 1, true, 0);
							cell.setValue(sl);
							cell = cells.getCell("J" + Integer.toString(i)); setCellBackground(cell, 1, true, 0);
							cell.setValue(ds);
						}
						stt++;
						i++;
					}
				}
				 System.out.println("so dong:"+i);
				if (rs != null)
					rs.close();
				
				
				//getAn(workbook, 27);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				db.shutDown();
			}
	
	}
	
	private ResultSet CreateRSDataTQ(IStockintransit obj) {

		dbutils db = new dbutils();
		String query = "";
		if(!obj.gettype().equals("2"))
		{
			
			query = "SELECT ROW_NUMBER() OVER (PARTITION BY (SP.MA+' _ '+SP.TEN) order by (SP.MA+' _ '+SP.TEN)) as STT, " + 
				"			SP.MA+' _ '+SP.TEN AS SKU, V.TEN AS REGION, isnull(tt.TEN,'NA') as tinhthanh,   " + 
				"			NPP.PK_SEQ AS IDNPP, NPP.TEN AS DISTRIBUTOR, " + 
				"			SUM(SPH.VOLUME) AS VOLUME, SUM(SPH.SOLUONG) AS SOLUONG, " + 
				"			sum(SPH.SOKH) AS SOKH, " +
				"			sum(sum(SPH.SOKH)) OVER (PARTITION BY (SP.MA+' _ '+SP.TEN)) AS TONGSOKH, " + 
				"			sum(sum(SPH.SOLUONG)) OVER (PARTITION BY (SP.MA+' _ '+SP.TEN)) as TONGSOLUONG, " +
				"			sum(SUM(SPH.VOLUME)) OVER (PARTITION BY (SP.MA+' _ '+SP.TEN)) AS TONGDOANHSO, " +
				"			a.SOHD, " +
				"			sum(SUM(a.SOHD)) OVER (PARTITION BY (SP.MA+' _ '+SP.TEN)) AS TONGKHHD" + 
				"		FROM(  " + 
				"		SELECT  DH.NPP_FK, DHSP.SANPHAM_FK, COUNT(DISTINCT DH.KHACHHANG_FK) AS SOKH ,ISNULL (SUM((SOLUONG * GIAMUA) + (SOLUONG * GIAMUA * THUEVAT/100)),0) AS VOLUME, SUM(DHSP.SOLUONG) AS SOLUONG " + 
				"			FROM DONHANG_SANPHAM DHSP INNER JOIN DONHANG DH ON DH.PK_SEQ = DHSP.DONHANG_FK WHERE DH.TRANGTHAI =1  " + 
				"			AND DH.TONGGIATRI > 0 AND DH.NGAYNHAP  >= '"+obj.gettungay()+"' AND DH.NGAYNHAP <='"+obj.getdenngay()+"' ";
			if(obj.getnppId().length() > 0)	
				query += 
			"			AND DH.NPP_FK = '"+obj.getnppId()+"' ";
			if(obj.getDdkd().length() > 0)
				query +=
			"AND DH.DDKD_FK = '"+obj.getDdkd()+"' ";
			query +=
				"			GROUP BY DH.NPP_FK, DHSP.SANPHAM_FK " + 
				"			 UNION ALL " + 
				"			SELECT DH.NPP_FK,ISNULL(DH_SP.SANPHAM_FK, DH_SP1.SANPHAM_FK) AS SANPHAM_FK, 0 AS SOKH  ,    " + 
				"			-(1)* sum( ISNULL(DH_SP.GIAMUA, DH_SP1.GIAMUA)* ISNULL(DH_SP.SOLUONG, DH_SP1.SOLUONG))  AS VOLUME, (-1)* SUM(DH_SP.SOLUONG) AS SOLUONG  " + 
				"			FROM  DONHANGTRAVE DH   " + 
				"			LEFT OUTER JOIN  DONHANGTRAVE_SANPHAM DH_SP " + 
				"			 ON DH_SP.DONHANGTRAVE_FK = DH.PK_SEQ   	 " + 
				"			LEFT OUTER JOIN  DONHANG_SANPHAM DH_SP1 ON   " + 
				"			DH.DONHANG_FK = DH_SP1.DONHANG_FK    " + 
				"			WHERE DH.TRANGTHAI = 3 " + 
				"			AND DH.NGAYNHAP >='"+obj.gettungay()+"' AND DH.NGAYNHAP <= '"+obj.getdenngay()+"' ";
			if(obj.getnppId().length() > 0)	
				query += 
			"			AND DH.NPP_FK = '"+obj.getnppId()+"' ";
			if(obj.getDdkd().length() > 0)
				query +=
			"AND DH.DDKD_FK = '"+obj.getDdkd()+"' ";
			query +=	"			GROUP BY DH.NPP_FK, ISNULL(DH_SP.SANPHAM_FK, DH_SP1.SANPHAM_FK) " + 
				"			)SPH  " + 
				"INNER JOIN SANPHAM SP ON SP.PK_SEQ = SPH.SANPHAM_FK " + 
				"INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = SPH.NPP_FK   " + 
				"INNER JOIN KHUVUC KV ON KV.PK_SEQ = NPP.KHUVUC_FK  " + 
				"INNER JOIN VUNG V ON V.PK_SEQ = KV.VUNG_FK   " + 
				"LEFT join TINHTHANH tt on tt.PK_SEQ = NPP.TINHTHANH_FK " + 
				" " + 
				"LEFT JOIN ( SELECT SP_FK, MAX(NSP_FK) AS NHOMSP  " + 
				"FROM NHOMSANPHAM_SANPHAM  GROUP BY SP_FK   " + 
				") NSPSP ON NSPSP.SP_FK = SP.PK_SEQ  " + 
				"LEFT JOIN NHOMSANPHAM NSP ON NSP.PK_SEQ = NSPSP.NHOMSP   " + 
				"left join (select COUNT(PK_SEQ) AS SOHD, NPP_FK from KHACHHANG where LEN(RTRIM(LTRIM(MaHD))) != 0 GROUP BY NPP_FK) a " + 
				"on a.NPP_FK = SPH.NPP_FK " + 
				"WHERE 1=1  AND NPP.PK_SEQ IN  ( SELECT NPP_FK FROM PHAMVIHOATDONG WHERE " +
				"NHANVIEN_FK ='100002'" +
				")  ";
			if(obj.getvungId().length() > 0)
				query +=
				"AND V.PK_SEQ='"+obj.getvungId()+"' ";
			if(obj.getTtId().length() > 0)
				query +=
				"AND tt.PK_SEQ = '"+obj.getTtId()+"' ";
			if(obj.getsanphamId().length() > 0)
				query += "\nAND SP.PK_SEQ IN "+obj.getsanphamId()+" ";
			query += 
				"GROUP BY SP.MA+' _ '+SP.TEN, V.TEN, NPP.TEN, NPP.PK_SEQ, isnull(tt.TEN,'NA'), a.SOHD ";
		}
		else 
		{
			String sku="''";			
			if(obj.getsanphamId().length()>0)
			{
				sku=  " STUFF  "+      
						  " (     "+
							"	  (   "+   
							"		select DISTINCT TOP 100 PERCENT ' , ' + sp.ten  "+
							"		from sanpham sp  "+
							"		where sp.PK_SEQ in "+obj.getsanphamId()+"  "+
							"		ORDER BY ' , '  +sp.ten  "+
							"		FOR XML PATH('')       "+
							"	 ), 1, 2, ''    "+
							" )    ";
			}
			
			query = "\nSELECT ROW_NUMBER() OVER (PARTITION BY ("+sku+") order by ("+sku+")) as STT, " + 
			"\n			"+sku+" AS SKU, V.TEN AS REGION, isnull(tt.TEN,'NA') as tinhthanh,   " + 
			"\n			NPP.PK_SEQ AS IDNPP, NPP.TEN AS DISTRIBUTOR, " + 
			"\n			SUM(SPH.VOLUME) AS VOLUME, SUM(SPH.SOLUONG) AS SOLUONG, " + 
			"\n			sum(SPH.SOKH) AS SOKH, " +
			"\n			sum(sum(SPH.SOKH)) OVER (PARTITION BY ("+sku+")) AS TONGSOKH, " + 
			"\n			sum(sum(SPH.SOLUONG)) OVER (PARTITION BY ("+sku+")) as TONGSOLUONG, " +
			"\n			sum(SUM(SPH.VOLUME)) OVER (PARTITION BY ("+sku+")) AS TONGDOANHSO, " +
			"\n			a.SOHD, " +
			"\n			sum(SUM(a.SOHD)) OVER (PARTITION BY ("+sku+")) AS TONGKHHD" + 
			"\n		FROM(  " + 
			"\n		SELECT  DH.NPP_FK, DHSP.SANPHAM_FK, COUNT(DISTINCT DH.KHACHHANG_FK) AS SOKH ,ISNULL (SUM((SOLUONG * GIAMUA) + (SOLUONG * GIAMUA * THUEVAT/100)),0) AS VOLUME, SUM(DHSP.SOLUONG) AS SOLUONG " + 
			"\n			FROM DONHANG_SANPHAM DHSP INNER JOIN DONHANG DH ON DH.PK_SEQ = DHSP.DONHANG_FK WHERE DH.TRANGTHAI =1  " + 
			"\n			AND DH.TONGGIATRI > 0 AND DH.NGAYNHAP  >= '"+obj.gettungay()+"' AND DH.NGAYNHAP <='"+obj.getdenngay()+"' ";
			if(obj.getnppId().length() > 0)	
				query += 
			"\n			AND DH.NPP_FK = '"+obj.getnppId()+"' ";
			if(obj.getDdkd().length() > 0)
				query +=
			"\nAND DH.DDKD_FK = '"+obj.getDdkd()+"' ";
			query +=
				"\n			GROUP BY DH.NPP_FK, DHSP.SANPHAM_FK " + 
				"\n			 UNION ALL " + 
				"\n			SELECT DH.NPP_FK,ISNULL(DH_SP.SANPHAM_FK, DH_SP1.SANPHAM_FK) AS SANPHAM_FK, 0 AS SOKH  ,    " + 
				"\n			-(1)* sum( ISNULL(DH_SP.GIAMUA, DH_SP1.GIAMUA)* ISNULL(DH_SP.SOLUONG, DH_SP1.SOLUONG))  AS VOLUME, (-1)* SUM(DH_SP.SOLUONG) AS SOLUONG  " + 
				"\n			FROM  DONHANGTRAVE DH   " + 
				"\n			LEFT OUTER JOIN  DONHANGTRAVE_SANPHAM DH_SP " + 
				"\n			 ON DH_SP.DONHANGTRAVE_FK = DH.PK_SEQ   	 " + 
				"\n			LEFT OUTER JOIN  DONHANG_SANPHAM DH_SP1 ON   " + 
				"\n			DH.DONHANG_FK = DH_SP1.DONHANG_FK    " + 
				"			WHERE DH.TRANGTHAI = 3 " + 
				"			AND DH.NGAYNHAP >='"+obj.gettungay()+"' AND DH.NGAYNHAP <= '"+obj.getdenngay()+"' ";
			if(obj.getnppId().length() > 0)	
				query += 
			"\n			AND DH.NPP_FK = '"+obj.getnppId()+"' ";
			if(obj.getDdkd().length() > 0)
				query +=
			"\nAND DH.DDKD_FK = '"+obj.getDdkd()+"' ";
			query +=	"			GROUP BY DH.NPP_FK, ISNULL(DH_SP.SANPHAM_FK, DH_SP1.SANPHAM_FK) " + 
				"\n			)SPH  " + 
				"\nINNER JOIN SANPHAM SP ON SP.PK_SEQ = SPH.SANPHAM_FK " + 
				"\nINNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = SPH.NPP_FK   " + 
				"\nINNER JOIN KHUVUC KV ON KV.PK_SEQ = NPP.KHUVUC_FK  " + 
				"\nINNER JOIN VUNG V ON V.PK_SEQ = KV.VUNG_FK   " + 
				"\nLEFT join TINHTHANH tt on tt.PK_SEQ = NPP.TINHTHANH_FK " + 
				"\n " + 
				"\nLEFT JOIN ( SELECT SP_FK, MAX(NSP_FK) AS NHOMSP  " + 
				"\nFROM NHOMSANPHAM_SANPHAM  GROUP BY SP_FK   " + 
				"\n) NSPSP ON NSPSP.SP_FK = SP.PK_SEQ  " + 
				"\nLEFT JOIN NHOMSANPHAM NSP ON NSP.PK_SEQ = NSPSP.NHOMSP   " + 
				"\nleft join (select COUNT(PK_SEQ) AS SOHD, NPP_FK from KHACHHANG where LEN(RTRIM(LTRIM(MaHD))) != 0 GROUP BY NPP_FK) a " + 
				"\non a.NPP_FK = SPH.NPP_FK " + 
				"\nWHERE 1=1  AND NPP.PK_SEQ IN  ( SELECT NPP_FK FROM PHAMVIHOATDONG WHERE " +
				"\nNHANVIEN_FK ='"+obj.getuserId()+"'" +
				")  ";
			if(obj.getvungId().length() > 0)
				query += "\nAND V.PK_SEQ='"+obj.getvungId()+"' ";
			if(obj.getTtId().length() > 0)
				query += "\nAND tt.PK_SEQ = '"+obj.getTtId()+"' ";
			if(obj.getsanphamId().length() > 0)
				query += "\nAND SP.PK_SEQ IN "+obj.getsanphamId()+" ";
			query += 
				"\nGROUP BY V.TEN, NPP.TEN, NPP.PK_SEQ, isnull(tt.TEN,'NA'), a.SOHD ";
				
				System.out.println("__________"+query);
			
		}
		System.out.println(":::::::::::::::::::::::::"+query);
		return db.get(query);
	
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

	private void getAn(Workbook workbook, int i)
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();
		for (int j = 1000; j <= i; j++)
		{
			cells.hideColumn(j);
		}

	}
	private static void setCellBackground(Cell cell,int borderLineType, boolean bold, int decimal){
		Style style = cell.getStyle();
		style.setBorderLine(BorderType.BOTTOM, borderLineType);
		style.setBorderLine(BorderType.LEFT, borderLineType);
		style.setBorderLine(BorderType.TOP, borderLineType);
		style.setBorderLine(BorderType.RIGHT, borderLineType);
		//style.setNumber(decimal);
		//Font font = new Font();
		//font.setName("Times New Roman");
		//font.setColor(Color.BLACK);
		//font.setBold(bold);
		//style.setFont(font);
		cell.setStyle(style);		
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
