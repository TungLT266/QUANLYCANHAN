package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import CI.IF;

import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTables;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class Distributionnpp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Distributionnpp() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Utility util = new Utility();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		// PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String userTen = (String) session.getAttribute("userTen");
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		//Ireportnpp obj = new Reports();
		IStockintransit obj = new Stockintransit();
		obj.settungay("");
		obj.setdenngay("");
		obj.setuserId(userId);
		obj.init();
		obj.setsanphamId("");
		session.setAttribute("obj", obj);
		session.setAttribute("loi", "");
		session.setAttribute("userTen", userTen);
		String nextJSP = "/TraphacoHYERP/pages/Center/Distributionnpp.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		OutputStream out = response.getOutputStream();
		IStockintransit obj = new Stockintransit();
		Utility util = new Utility();
		HttpSession session = request.getSession();
		try {
			
			
		
			obj.setuserTen((String) session.getAttribute("userTen"));
			obj.settungay(util.antiSQLInspection(request.getParameter("tungay")));
			obj.setdenngay(util.antiSQLInspection(request.getParameter("denngay")));
			obj.setsanphamId(util.antiSQLInspection(request.getParameter("skuid")));
			//obj.setuserId((String) session.getAttribute("userId"));
			obj.setuserId(util.antiSQLInspection(request.getParameter("userId")));
			//System.out.println("Nhan vien id = " + obj.getuserId());
			obj.setDdkd(request.getParameter("ddkdId")!=null? 
					request.getParameter("ddkdId"):"");
			
		
			obj.setkenhId(util.antiSQLInspection(request.getParameter("kenhId")) != null ? util.antiSQLInspection(request.getParameter("kenhId")) : "");
			
			String[] skutest = request.getParameterValues("check");
			 
			String whereSKU = "";
			dbutils db=new dbutils();
			if(skutest!=null)	
			for (int i = 0; i < skutest.length; i++) 
			{
					whereSKU += "'" + skutest[i] + "',";
					String sql="insert into test (a) values ("+skutest[i]+")";
		 			db.update(sql);
			}
			
		
	 		
	 		db.shutDown();
	 		
	 		
			boolean bfasle = false;
			session.setAttribute("checkedSKU", skutest);
			if (whereSKU == "") {
				bfasle = false;
			} else {
				whereSKU = " (" + whereSKU.substring(0, whereSKU.length() - 1)
						+ ")";
				bfasle = true;
			}
			obj.setsanphamId(whereSKU);
			
			System.out.println("___________"+whereSKU);
			
			obj.setnhanhangId(util.antiSQLInspection(request.getParameter("nhanhangId"))!=null?
			util.antiSQLInspection(request.getParameter("nhanhangId")):"");
			obj.setchungloaiId(util.antiSQLInspection(request.getParameter("chungloaiId"))!=null?
			util.antiSQLInspection(request.getParameter("chungloaiId")):"");
			obj.setdvkdId(util.antiSQLInspection(request.getParameter("dvkdId"))!=null?
			util.antiSQLInspection(request.getParameter("dvkdId")):"");
			obj.SetNhoSPId(util.antiSQLInspection(request.getParameter("nhomspid"))!=null?
					util.antiSQLInspection(request.getParameter("nhomspid")):"");
			
			 obj.settype(request.getParameter("typeid"));
			
			System.out.println("Type :"+obj.gettype());	
				
		 	String action = request.getParameter("action");
			System.out.println("Action  :"+action);	
			if(action.equals("create"))
			{
			
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "Attachment; filename=DistributionReport(NPP)"+this.getPiVotName()+".xlsm");

				CreatePivotTable(out, response, request,obj,bfasle,whereSKU);
			}
			else if (action.equals("getdata")){
				session.setAttribute("checkedSKU", "");
				session.setAttribute("obj", obj);
				session.setAttribute("loi", "");
				session.setAttribute("userId", obj.getuserId());
				session.setAttribute("userTen", obj.getuserTen());
				obj.init();
				obj.setRSBaocao(CreateRSData(obj));
				
				session.setAttribute("obj", obj);
				String nextJSP = "/TraphacoHYERP/pages/Center/Distributionnpp.jsp";
				response.sendRedirect(nextJSP);
			}
			else{
				obj.init();			
				session.setAttribute("obj", obj);
				
				session.setAttribute("userTen", obj.getuserTen());
				String nextJSP = "/TraphacoHYERP/pages/Center/Distributionnpp.jsp";
				response.sendRedirect(nextJSP);
			}
			
			
			
		} catch (Exception ex) {
			obj.init();
			obj.setMsg("Khong The Tao Duoc Bao Cao. -- "+ ex.toString());
			session.setAttribute("obj", obj);
			
			session.setAttribute("userTen", obj.getuserTen());
			String nextJSP = "/TraphacoHYERP/pages/Center/Distributionnpp.jsp";
			response.sendRedirect(nextJSP);
		}
		
	}
	private ResultSet CreateRSData(IStockintransit obj) {

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
			param[11] = "0";// LAY BAO CAO CENTER
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
				"\n			npp.ten as distributor, kh.mafast +'_'+kh.ten as custommer, "+sku+"  as sku, lch.diengiai as outlet_type, vt.diengiai as	outlet_location, hch.diengiai as outlet_class, " +
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
				"\n	inner join donvikinhdoanh dvkd on dvkd.pk_seq=sph.dvkd_fk "+
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
	private void CreatePivotTable(OutputStream out,
			HttpServletResponse response, HttpServletRequest request,IStockintransit obj,boolean bfasle,String whereSKU)
			throws IOException { // khoi tao de viet pivottable
									// buoc 1
		//khoi tao de viet pivottable
		//buoc 1
		//String strfstream="D:\\Best Stable\\Best\\WebContent\\pages\\Templates\\Distribution(NPP).xlsm";
		String chuoi=getServletContext().getInitParameter("path") + "\\Distribution(NPP).xlsm";		
		//String strfstream = getServletContext().getInitParameter("path") + "\\Distribution(NPP).xlsm";		

		FileInputStream fstream = new FileInputStream(chuoi);	
		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		
	     //Buoc2 tao khung
	    //ham tao khu du lieu
	     CreateStaticHeader(workbook,obj.gettungay(),obj.getdenngay(),obj.getuserTen());
	     //Buoc3 
	     // day du lieu vao
	     bfasle=  CreateStaticData(workbook,obj);
	     HttpSession session = request.getSession();
	     
		  obj.init();
		 session.setAttribute("obj", obj);
	     
	     if(bfasle==false){
	    	
	    	
	    	
	    	session.setAttribute("checkedSKU","");
	
	    	session.setAttribute("userId", obj.getuserId());
			session.setAttribute("userTen", obj.getuserTen());	 		
	    	obj.init();
	 		session.setAttribute("obj",obj);
	 		String nextJSP = "/TraphacoHYERP/pages/Center/Distributionnpp.jsp";
	 		response.sendRedirect(nextJSP);
	     }else{
	    	 //Saving the Excel file
	    	 workbook.save(out);
			    fstream.close();
	     }
	}

	private void CreateStaticHeader(Workbook workbook, String dateFrom,
			String dateTo, String UserName) {
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();

		Style style;
		// cells.setColumnWidth(0, 200.0f);
		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
		cell.setValue("ĐỘ PHỦ SẢN PHẨM");

		style = cell.getStyle();

		Font font2 = new Font();
		font2.setColor(Color.RED);// mau chu
		font2.setSize(16);// size chu
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu
		cell.setStyle(style);
		cell = cells.getCell("A2");
		getCellStyle(workbook, "A2", Color.NAVY, true, 10);
		cell.setValue("Từ Ngày " + dateFrom + "      Đến Ngày   " + dateTo);
		cell = cells.getCell("A3");
		getCellStyle(workbook, "A3", Color.NAVY, true, 10);
		cell.setValue("Ngày Tạo: " + this.getDateTime());
		cell = cells.getCell("A4");
		getCellStyle(workbook, "A4", Color.NAVY, true, 10);
		cell.setValue("Tạo Bởi :  " + UserName);

		cell = cells.getCell("AA1");
		cell.setValue("Kenh Ban Hang");
		
		cell = cells.getCell("AB1");
		cell.setValue("Don Vi Kinh Doanh");
		
		cell = cells.getCell("AC1");
		cell.setValue("Dai Dien Kinh Doanh");
		
		cell = cells.getCell("AD1");
		cell.setValue("Khach Hang");
		
		
		
		cell = cells.getCell("AE1");
		cell.setValue("Loai Cua Hang");
		
		cell = cells.getCell("AF1");
		cell.setValue("Vi Tri Cua Hang");
		cell = cells.getCell("AG1");
		cell.setValue("Hang Cua Hang");
		cell = cells.getCell("AH1");
		cell.setValue("Nhom Khach Hang");
		cell = cells.getCell("AI1");
		cell.setValue("Nhan Hang");
		cell = cells.getCell("AJ1");
		cell.setValue("Chung Loai");
		
		//nhom sp
		
		cell = cells.getCell("AK1");
		cell.setValue("Nhom San Pham");
		
		
		cell = cells.getCell("AL1");
		cell.setValue("San Pham");
		
		
		
		cell = cells.getCell("AM1");
		
		cell.setValue("Do Phu");
		
		cell = cells.getCell("AN1");
		
		cell.setValue("Doanh So");
		
		cell = cells.getCell("AO1");
		
		cell.setValue("Doanh So Khach Hang");
		
		cell = cells.getCell("AP1");
		
		cell.setValue("Quan Huyen");
		
		cell = cells.getCell("AQ1");
		
		cell.setValue("Tinh Thanh");
	
	}

	private boolean CreateStaticData(Workbook workbook,IStockintransit obj) {
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		
		Utility util = new Utility();
		obj.setnppId(util.getIdNhapp(obj.getuserId()));		
		
		ResultSet rs=null;
		if(!obj.gettype().equals("2"))
		{
			String[] param = new String[16];
			param[0] = obj.getnppId().equals("")?null:obj.getnppId();
			param[1] = obj.gettungay();
			param[2] = obj.getdenngay();
			param[3]=obj.getkenhId().equals("")?null:obj.getkenhId();	
			param[4]=obj.getnhanhangId().equals("")? null:obj.getnhanhangId();
			param[5]=obj.getchungloaiId().equals("")?null:obj.getchungloaiId();
			param[6]=obj.getdvkdId().equals("")?null:obj.getdvkdId();
			param[7] = obj.getTtId() == "" ? null : obj.getTtId();
			param[8]=obj.getvungId()==""?null:obj.getvungId();
			param[9]=obj.getsanphamId();
			param[10]=obj.getuserId();
			param[11]="0";//LAY BAO CAO DISTRIBUTOR
			
			param[12]=obj.gettype();//type=1 la lay bao cao theo thoi gian
		    
			param[13]=obj.GetNhoSPId()==""?null:obj.GetNhoSPId();
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
		    


		int i = 8;
		if (rs != null) {

			try {

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
				if (rs != null)
					rs.close();
			
			} catch (Exception e) 
			{
				obj.setMsg("Khong Lay Duoc Bao Cao.Loi :"+ e.toString());
				e.printStackTrace();
				return false;
			}
		} else {
			obj.setMsg("Khong Lay Duoc Bao Cao");
			return false;
		}
		return true;

	}

	private void getCellStyle(Workbook workbook, String a, Color mau,
			Boolean dam, int size) {
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

	private void getAn(Workbook workbook, int i) {
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();
		for (int j = 1; j <= i; j++) {
			cells.hideRow(j);
		}

	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
		Date date = new Date();
		return dateFormat.format(date);
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

}
