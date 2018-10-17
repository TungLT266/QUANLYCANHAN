package geso.traphaco.erp.servlets.baocao;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.beans.baocao.IBaocao;
import geso.traphaco.erp.beans.baocao.imp.Baocao;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

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
import com.aspose.cells.HorizontalAlignmentType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.itextpdf.text.log.SysoCounter;

public class ErpBCHangnhapSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErpBCHangnhapSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IBaocao obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();

		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		obj = new Baocao();
		obj.setUserId(userId);
		obj.createRs();
		session.setAttribute("obj", obj);

		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCHangNhapKho.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		OutputStream out = response.getOutputStream();
		IBaocao obj;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();

		String userTen = (String) session.getAttribute("userTen");
		String userId = request.getParameter("userId");

		obj = new Baocao();
		obj.setUserId(userId);
		obj.setUserTen(userTen);
		
		String loaibaocao = request.getParameter("loaibaocao");
		if(loaibaocao == null)
			loaibaocao = "1";
		obj.setLoaibaocao(loaibaocao);

		String tungay = request.getParameter("tungay");
		if (tungay == null)
			tungay = "";
		obj.setTuNgay(tungay);

		String denngay = request.getParameter("denngay");
		if (denngay == null)
			denngay = "";
		obj.setDenNgay(denngay);

		String loaisp = request.getParameter("loaisanpham");
		if (loaisp == null)
			loaisp = "";
		obj.setLoaiSanPhamIds(loaisp);


		String[] khoId = request.getParameterValues("khoids");
		String chuoikho="";
		if (khoId != null) {
			for(int i =0 ;i <khoId.length ;i++){
				chuoikho =chuoikho+"," + khoId[i];
			}
			if(chuoikho.length() >0){
				chuoikho = chuoikho.substring(1, chuoikho.length());
			}
		}
		obj.setKhoIds(chuoikho);

		System.out.println(" kho ne :" + obj.getKhoIds());


		String getpivot = request.getParameter("getpivot");
		if(getpivot == null)
			getpivot = "0";
		obj.setPivot(getpivot);
		String khoTen = request.getParameter("khoTen");
		if (khoTen == null)
			khoTen = "";
		obj.setKhoTen(khoTen);


		String flag = request.getParameter("flag");
		if (flag == null)
			flag = "0";
		obj.setFlag(flag);

		String[] spIds = request.getParameterValues("sanpham");
		String spId = "";
		if (spIds != null)
		{
			for (int i = 0; i < spIds.length; i++)
				spId += spIds[i] + ",";
			if (spId.length() > 0)
				spId = spId.substring(0, spId.length() - 1);
			obj.setSanPhamIds( spId);
		}
		System.out.println(" sanpham : "+ spId);





		String[] clIds = request.getParameterValues("clIds");
		String clId = "";
		if (clIds != null)
		{
			for (int i = 0; i < clIds.length; i++)
				clId += clIds[i] + ",";
			if (clId.length() > 0)
				clId = clId.substring(0, clId.length() - 1);
			obj.setChungloaiIds(clId);
		}

		String[] ndnIds = request.getParameterValues("ndnIds");
		String ndnId = "";
		if (ndnIds != null)
		{
			for (int i = 0; i < ndnIds.length; i++)
				ndnId += ndnIds[i] + ",";
			if (ndnId.length() > 0)
				ndnId = ndnId.substring(0, ndnId.length() - 1);
			obj.setNdnhapIds(ndnId);
		}

		String action = request.getParameter("action");
		System.out.println("Action nhan duoc: " + action); 


		String view_chitiet = request.getParameter("view_chitiet");
		if(view_chitiet==null){
			view_chitiet ="";
		}
		obj.set_view_chitiet(view_chitiet);


		if (action.equals("filter"))
		{
			obj.createRs();
			session.setAttribute("obj", obj);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCHangNhapKho.jsp";
			response.sendRedirect(nextJSP);
		}
		else if (action.equals("web"))
		{
			obj.createRs();
			List<HangNhapKhoObj> list = BaoCaoHangNhapKho(obj);
			session.setAttribute("ListXNT", list);
			session.setAttribute("ListXNT_check", "1");
			session.setAttribute("obj", obj);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCHangNhapKho.jsp";
			response.sendRedirect(nextJSP);
		}
		else if (action.equals("submit"))
		{
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=ErpBCHangnhapkho.xlsm");
			obj.createRs();
			boolean isTrue = false;
			try
			{
				isTrue = CreatePivotTable(out, obj, "");
			} catch (Exception e)
			{
				obj.setMsg(e.getMessage());
				e.printStackTrace();
				isTrue = false;
			}

			if (isTrue==false)
			{
				obj.createRs();
				session.setAttribute("obj", obj);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCHangNhapKho.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}


	private List<HangNhapKhoObj> BaoCaoHangNhapKho(IBaocao obj){

		Utility util = new Utility();
		dbutils db = new dbutils();
		List<HangNhapKhoObj> list = new ArrayList<HangNhapKhoObj>();

		String query ="\n SELECT ROW_NUMBER()  OVER (  ORDER BY MAKHO DESC) AS STT, MAKHO,TENKHO,LOAISANPHAM,MASP,TENSP,DONVI,  ROUND( SUM(SOLUONG),3)  AS SOLUONG,ROUND(  DONGIA, 3) AS DONGIA, ROUND(  (SUM(SOLUONG) * DONGIA),0 ) as THANHTIEN ,  kho_fk,sanpham_fk , maloaisp "+    
		"\n  FROM		  "+    
		"\n  (SELECT makho as MAKHO,  kho_fk as kho_fk, tenkho as tenkho,maloaisp_pkseq as maloaisp_pkseq,  maloaisp as maloaisp, loaisanpham as loaisanpham,sanpham_fk as sanpham_fk,  spma as masp, spten as tensp, donvi as donvi, nhap as soluong, isnull( dongia,0)  as dongia, NOIDUNGNHAN_FK as NOIDUNGNHAN_FK  "+    
		"\n  FROM [UFN_NHAPKHO_HO_TRONGKY]('"+obj.getTuNgay()+ "','" +obj.getDenNgay()+ " ') WHERE 1=1";

		if(obj.getKhoIds().length()>0){
			query	+="\n  AND  kho_fk IN (" +obj.getKhoIds()+" ) ";
		}

		if(obj.getLoaiSanPhamIds().length()>0){
			query	+="\n  AND  maloaisp_pkseq IN (" +obj.getLoaiSanPhamIds()+" ) ";
		}

		if(obj.getSanPhamIds().length()>0){
			query	+="\n  AND  sanpham_fk IN (" +obj.getSanPhamIds()+" ) ";
		}

		if(obj.getNdnhapIds().length()>0){
			query	+="\n  AND  NOIDUNGNHAN_FK IN (" +obj.getNdnhapIds()+" ) ";
		}


		query	+=	 ")  DATA     \n  Group by MAKHO,TENKHO,LOAISANPHAM,MASP,TENSP,DONGIA ,DONVI,  kho_fk,sanpham_fk , maloaisp ";			 

		System.out.println(" qr web: "+ query);
		ResultSet  chiTietNXT = db.get(query);

		try 
		{
			if(chiTietNXT != null)
			{
				while(chiTietNXT.next())
				{
					String makho= chiTietNXT.getString("makho");
					String tenkho = chiTietNXT.getString("tenkho");
					String loaisp = chiTietNXT.getString("LOAISANPHAM");
					String masp = chiTietNXT.getString("MASP");
					String tensp = chiTietNXT.getString("TENSP");
					String donvi = chiTietNXT.getString("DONVI");
					double soluong = chiTietNXT.getDouble("SOLUONG");
					double dongia = chiTietNXT.getDouble("DONGIA");
					double thanhtien = chiTietNXT.getDouble("THANHTIEN");

					HangNhapKhoObj temp = new HangNhapKhoObj();
					temp.setMakho(makho);
					temp.setTenkho(tenkho);
					temp.setLoaisp(loaisp);
					temp.setMasp(masp);
					temp.setTensp(tensp);
					temp.setDonvi(donvi);
					temp.setSoluong(soluong);
					temp.setDongia(dongia);
					temp.setThanhtien(thanhtien);
					list.add(temp);
				}
				chiTietNXT.close();
			}
			return list;
		}
		catch (Exception e) 
		{ 
			e.printStackTrace();
			return list;
		} finally{
			db.shutDown();
		}
	}




	private boolean CreatePivotTable(OutputStream out, IBaocao obj, String condition) throws Exception
	{
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		

		if(obj.getLoaibaocao().equals("1"))
		{
			
			fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ErpBCHangnhapkhoTongQuat.xlsm");
		}
		else
			if(obj.getLoaibaocao().equals("2"))
		{
			fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ErpBCHangnhapkhoChiTiet.xlsm");
			
		}

		//fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ErpBCHangnhapkho.xlsm");

		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		boolean isTrue =false;
		
		if(obj.getLoaibaocao().equals("2"))
		{
			//chi tiet
			isTrue = CreateExcel_BChangnhapkho_ChiTiet(workbook, obj, condition);
		}
		else
			if(obj.getLoaibaocao().equals("1"))
		{
				//tong quat
			isTrue = CreateExcel_BChangnhapkho(workbook, obj, condition);
		}
		
		//isTrue = CreateExcel_BChangnhapkho(workbook, obj, condition);

		if (!isTrue)
		{
			obj.setMsg("Lỗi tạo dữ liệu báo cáo");
			return false;
		} 

		workbook.save(out);

		fstream.close();
		return true;
	}


	public static void main(String[] arg)
	{

	}


	private boolean CreateExcel_BChangnhapkho_ChiTiet(Workbook workbook, IBaocao obj, String condition) throws Exception
	{
		dbutils db = new dbutils();
		try{

			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			Cells cells = worksheet.getCells();
			Cell cell = null;
			worksheet.setName("Sheet1");
			Style style;
			DecimalFormat formatter = new DecimalFormat("###,###,###");
			DecimalFormat formatter1 = new DecimalFormat("###,###.###");
			//------------------------ header -----------------------------
			Font font = new Font();


			cell = cells.getCell("C2");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 9,  obj.getTuNgay());

			cell = cells.getCell("E2");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 9,  obj.getDenNgay());


			cell = cells.getCell("C3");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, ReportAPI.NOW("yyyy-MM-dd"));

			cell = cells.getCell("C4");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 9,  obj.getUserTen());



			//--------------------  cau lay du lieu  --------------------------
			String query ="\n SELECT ROW_NUMBER()  OVER (  ORDER BY MAKHO,loaichungtu,machungtu,ngaychungtu DESC) AS STT, sophieu,  MAKHO+'-'+TENKHO as TENKHO,loaichungtu,machungtu,ngaychungtu,lenhsanxuat_fk,thanhphamsp,lydo,MASP,TENSP,DONVI,  ROUND( SUM(SOLUONG),3)  AS SOLUONG,ROUND(  DONGIA, 3) AS DONGIA, ROUND(  (SUM(SOLUONG) * DONGIA),0 ) as THANHTIEN ,'' as ghichu"+    
			"\n  FROM		  "+    
			"\n  (SELECT makho as MAKHO,  kho_fk as kho_fk, tenkho as tenkho,maloaisp_pkseq as maloaisp_pkseq,  maloaisp as maloaisp, loaisanpham as loaisanpham,sanpham_fk as sanpham_fk,  spma as masp, spten as tensp, donvi as donvi, nhap as soluong, isnull( dongia,0)  as dongia,loaichungtu,machungtu,ngaychungtu,lenhsanxuat_fk,thanhphamsp,lydo, NOIDUNGNHAN_FK as NOIDUNGNHAN_FK , sophieu "+    
			"\n  FROM [UFN_NHAPKHO_HO_TRONGKY]('"+obj.getTuNgay()+ "','" +obj.getDenNgay()+ " ') WHERE 1=1";

			if(obj.getKhoIds().length()>0){
				query	+="\n  AND  kho_fk IN (" +obj.getKhoIds()+" ) ";
			}

			if(obj.getLoaiSanPhamIds().length()>0){
				query	+="\n  AND  maloaisp_pkseq IN (" +obj.getLoaiSanPhamIds()+" ) ";
			}

			if(obj.getSanPhamIds().length()>0){
				query	+="\n  AND  sanpham_fk IN (" +obj.getSanPhamIds()+" ) ";
			}

			if(obj.getNdnhapIds().length()>0){
				query	+="\n  AND  NOIDUNGNHAN_FK IN (" +obj.getNdnhapIds()+" ) ";
			}


			query	+=	 ")  DATA     \n  Group by MAKHO,TENKHO,LOAISANPHAM,MASP,TENSP,DONGIA ,DONVI,  kho_fk,sanpham_fk , maloaisp,loaichungtu,machungtu,ngaychungtu,lenhsanxuat_fk,thanhphamsp,lydo, sophieu ";			 

			System.out.println("hang nhap kho chi tiet: "+ query);
			System.out.println("danh sach sp: "+ obj.getSanPhamIds());
			ResultSet rs=db.get(query);
			ResultSetMetaData rsmd=rs.getMetaData();;
			int socottrongSql=rsmd.getColumnCount();
			System.out.println("so cot "+ socottrongSql);
			int countRow = 6;
			int column = 0;
			cell = null;
			style = null;
			double tongtien=0;
			boolean isSame = false;
			String sophieu = "";
			String loaichungtu = "";
			String machungtu = "";
			String ngaychungtu = "";
			

			while (rs.next()) {
				/********************************/
				isSame = false;
				String sophieuNew = rs.getString("sophieu");
				String loaichungtuNew = rs.getString("loaichungtu");
				String machungtuNew = rs.getString("machungtu");
				String ngaychungtuNew = rs.getString("ngaychungtu");
				if(sophieu.equals(sophieuNew) && loaichungtu.equals(loaichungtuNew)
						&& machungtu.equals(machungtuNew) && ngaychungtu.equals(ngaychungtuNew)){
					isSame = true;
				}
				sophieu = sophieuNew;
				loaichungtu = loaichungtuNew;
				machungtu = machungtuNew;
				ngaychungtu = ngaychungtuNew;
				/********************************/
				// chi lay 10 dong dau
				for(int i =1;i <=socottrongSql ; i ++)
				{
					cell = cells.getCell(countRow,column + i-1 );

					if( i==1 || i==13 || i == 14 || i == 15){
						System.out.println("ELEMENT :"+ rs.getString(i));
						cell.setValue(rs.getDouble(i));
						
						if(i==13 || i==14){
							cell.setValue(formatter1.format(rs.getDouble(i))  );
						}
						
						if(i==15){
							cell.setValue(formatter.format(rs.getDouble(i)) );
						}
					}else if (i==10 || i == 11 || i == 12){
						cell.setValue(rs.getString(i));
					}
					else{
						cell.setValue( isSame ?"":rs.getString(i));
					}


					if(i==1){
						setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED,false);
					} else{
						setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,false);
					}
					if(i==15){
						tongtien+=rs.getDouble(i);
					}

				}
				countRow++;
			}

			// chen dong tong cong +thanh tien
			cell = cells.getCell(countRow,0);
			cells.merge(countRow, 0, countRow, 11);
			cell.setValue("Tổng cộng");
			setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED,true);

			cell = cells.getCell(countRow,12);
			cells.merge(countRow, 12, countRow, 13);
			cell.setValue(formatter.format(tongtien) );
			setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED,true);
			
			cell = cells.getCell(countRow,14);
			cell.setValue("");
			setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED,true);

			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		} finally {
			db.shutDown();
		}
	}

	private boolean CreateExcel_BChangnhapkho(Workbook workbook, IBaocao obj, String condition) throws Exception
	{
		dbutils db = new dbutils();
		try{

			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			Cells cells = worksheet.getCells();
			Cell cell = null;
			worksheet.setName("Sheet1");
			Style style;
			DecimalFormat formatter = new DecimalFormat("###,###,###");
			DecimalFormat formatter1 = new DecimalFormat("###,###.###");
			//------------------------ header -----------------------------
			Font font = new Font();


			cell = cells.getCell("C2");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 9,  obj.getTuNgay());

			cell = cells.getCell("E2");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 9,  obj.getDenNgay());


			cell = cells.getCell("C3");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, ReportAPI.NOW("yyyy-MM-dd"));

			cell = cells.getCell("C4");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 9,  obj.getUserTen());



			//--------------------  cau lay du lieu  --------------------------
			String query ="\n SELECT ROW_NUMBER()  OVER (  ORDER BY MAKHO DESC) AS STT, MAKHO,TENKHO,MASP,TENSP,DONVI,  ROUND( SUM(SOLUONG),3)  AS SOLUONG,ROUND(  DONGIA, 3) AS DONGIA, ROUND(  (SUM(SOLUONG) * DONGIA),0 ) as THANHTIEN, solo  "+    
			"\n  FROM		  "+    
			"\n  (SELECT makho as MAKHO,  kho_fk as kho_fk, tenkho as tenkho,maloaisp_pkseq as maloaisp_pkseq,  maloaisp as maloaisp, loaisanpham as loaisanpham,sanpham_fk as sanpham_fk,  spma as masp, spten as tensp, donvi as donvi, nhap as soluong, isnull( dongia,0)  as dongia, NOIDUNGNHAN_FK as NOIDUNGNHAN_FK, solo   "+    
			"\n  FROM [UFN_NHAPKHO_HO_TRONGKY]('"+obj.getTuNgay()+ "','" +obj.getDenNgay()+ " ') WHERE 1=1";

			if(obj.getKhoIds().length()>0){
				query	+="\n  AND  kho_fk IN (" +obj.getKhoIds()+" ) ";
			}

			if(obj.getLoaiSanPhamIds().length()>0){
				query	+="\n  AND  maloaisp_pkseq IN (" +obj.getLoaiSanPhamIds()+" ) ";
			}

			if(obj.getSanPhamIds().length()>0){
				query	+="\n  AND  sanpham_fk IN (" +obj.getSanPhamIds()+" ) ";
			}

			if(obj.getNdnhapIds().length()>0){
				query	+="\n  AND  NOIDUNGNHAN_FK IN (" +obj.getNdnhapIds()+" ) ";
			}


			query	+=	 ")  DATA     \n  Group by MAKHO,TENKHO,LOAISANPHAM,MASP,TENSP,DONGIA ,DONVI,  kho_fk,sanpham_fk , maloaisp ";			 

			System.out.println("hang nhap kho: "+ query);
			System.out.println("danh sach sp: "+ obj.getSanPhamIds());
			ResultSet rs=db.get(query);
			ResultSetMetaData rsmd=rs.getMetaData();;
			int socottrongSql=rsmd.getColumnCount();
			System.out.println("so cot "+ socottrongSql);
			int countRow = 6;
			int column = 0;
			cell = null;
			style = null;
			double tongtien=0;
			

			while (rs.next()) {
				// chi lay 10 dong dau
				for(int i =1;i <=socottrongSql ; i ++)
				{
					cell = cells.getCell(countRow,column + i-1 );

					if( i==1 || (i>=7 && i <= 9)){

						cell.setValue(rs.getDouble(i));
						
						if(i==7 || i==7){
							cell.setValue(formatter1.format(rs.getDouble(i))  );
						}
						
						if(i==9){
							cell.setValue(formatter.format(rs.getDouble(i)) );
						}
					}
					else{
						cell.setValue(rs.getString(i));
					}


					if(i==1){
						setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED,false);
					} else{
						setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,false);
					}
					if(i==9){
						tongtien+=rs.getDouble(i);
					}

				}
				countRow++;
			}

			// chen dong tong cong +thanh tien
			cell = cells.getCell(countRow,0);
			cells.merge(countRow, 0, countRow, 7);
			cell.setValue("Tổng cộng");
			setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED,true);

			cell = cells.getCell(countRow,8);
			cell.setValue(formatter.format(tongtien) );
			setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED,true);

			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		} finally {
			db.shutDown();
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
			font2.setColor(Color.BLACK);
			font2.setSize(11);
			style.setFont(font2);
			style.setColor(Color.SILVER);
		}
		else
			style.setColor(Color.WHITE);

		cell.setStyle(style);
	}



	/*
	private boolean CreateStaticData_1(Workbook workbook, IBaocao obj, String condition) throws Exception
	{
		try{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();

		String query ="";

		String tenkho= "";

		query=" select ten from ERP_KHOTT where pk_seq="+obj.getKhoIds();

		ResultSet kho = db.get(query);

		if(kho!=null){
			while(kho.next()){
				tenkho = kho.getString("ten");
			}kho.close();
		}


		query  =  
				 		 "  select isnull(cl.ten,'') as chungloai ,isnull(lsp.ten,'') as loaisanpham " +
				 		 "  ,sp.MA, sp.TEN, dvdl.DIENGIAI, TONG_NHAP.tong_soluong, TONG_NHAP.spId from    \n"+
						 "  (     select A.spId, SUM(A.soluong) as tong_soluong from (  \n"+
						 "		select nhsp.SANPHAM_FK as spId, SUM(isnull(nhsp.SOLUONGNHAN, 0)) as soluong  \n"+
						 "		from ERP_NHANHANG_SANPHAM nhsp  \n"+
						 "		inner join ERP_NHANHANG nh on nh.PK_SEQ=nhsp.NHANHANG_FK  \n"+
						 "		  WHERE NH.NGAYNHAN >= '"+obj.getTuNgay()+"' AND NH.NGAYNHAN <='"+obj.getDenNgay()+"'  " +
						 "      AND NH.TRANGTHAI IN (1, 2) AND NHSP.KHONHAN =  "+obj.getKhoIds()+ 
						 " 		  AND NHSP.DANHAN=1 "+
						 "		group by nhsp.SANPHAM_FK  \n"+
						 "		union all  \n"+
						 "		select nhsp.SANPHAM_FK, SUM(isnull(nhsp.SOLUONGNHAN, 0)) as soluong  \n"+
						 "		from ERP_NHANHANG_SANPHAM nhsp  \n"+
						 "		inner join ERP_NHANHANG nh on nh.PK_SEQ=nhsp.NHANHANG_FK  \n"+
						 "		where NH.NGAYNHAN >= "+obj.getTuNgay()+" AND NH.NGAYNHAN <='"+obj.getDenNgay()+"' AND NH.TRANGTHAI IN (1, 2) AND NH.KHOCHOXULY_FK ="+obj.getKhoIds()+
						 " 		 AND NHSP.DANHAN=0 "+
						 "		group by nhsp.SANPHAM_FK  \n"+
						 "		union all  \n"+
						 "		select nksp.SANPHAM_FK, SUM(isnull(nksp.SOLUONGNHAP, 0))  \n"+
						 "		from ERP_NHAPKHO_SANPHAM nksp  \n"+
						 "		inner join ERP_NHAPKHO nk on nk.PK_SEQ=nksp.SONHAPKHO_FK  \n"+
						 "		inner join DONVIDOLUONG dvdl on dvdl.PK_SEQ= nksp.DVDL_FK  \n"+
						 "		where nk.TRANGTHAI=1 and nk.NGAYNHAPKHO>='"+obj.getTuNgay()+"' and nk.NGAYNHAPKHO <='"+obj.getDenNgay()+"'  \n"+
						 "		and nk.KHONHAP="+obj.getKhoIds()+"  \n"+
						 "		group by nksp.SANPHAM_FK   \n"+
						 "		union all  \n"+
						 "		select ndqc.SANPHAM_FK, SUM( isnull(ndqc.SOLUONG, 0))  \n"+
						 "		from ERP_NHANDOIQUYCACH ndqc  \n"+
						 "		inner join ERP_XUATDOIQUYCACH xdqc on xdqc.PK_SEQ= ndqc.DOIQUYCACH_FK  \n"+
						 "		where  xdqc.TRANGTHAI=1 and xdqc.NGAY>='"+obj.getTuNgay()+"' and xdqc.NGAY<='"+obj.getDenNgay()+"' and xdqc.KHO_FK="+obj.getKhoIds()+"  \n"+
						 "		group by ndqc.SANPHAM_FK  \n"+
						 "		union all  \n"+
						 "		select cksp.SANPHAM_FK, SUM(isnull(cksp.SOLUONGNHAN, 0))  \n"+
						 "		from ERP_CHUYENKHO_SANPHAM cksp  \n"+
						 "		inner join ERP_CHUYENKHO ck on ck.PK_SEQ=cksp.CHUYENKHO_FK  \n"+
						 "		where ck.TRANGTHAI=3 and ck.NGAYCHUYEN>='"+obj.getTuNgay()+"' and ck.NGAYCHUYEN<='"+obj.getDenNgay()+"' and ck.KhoNhan_FK="+obj.getKhoIds()+"  \n"+
						 "		group by cksp.SANPHAM_FK  \n"+
						 "		union all  \n"+
						 "		select dcsp.SanPham_FK, SUM(isnull(dcsp.SoLuongDieuChinh,0))  \n"+
						 "		from ERP_DIEUCHINHTONKHOTT_SANPHAM dcsp  \n"+
						 "		inner join ERP_DIEUCHINHTONKHOTT dc on dc.pk_seq=dcsp.DieuChinhTonKhoTT_FK  \n"+
						 "		where dc.trangthai=1 and dc.ngaydieuchinh >='"+obj.getTuNgay()+"' and dc.ngaydieuchinh<='"+obj.getDenNgay()+"' and dc.khott_fk="+obj.getKhoIds()+"  \n"+
						 "		group by dcsp.SanPham_FK  \n"+
						 "		union all	  \n"+
						 "		select kksp.SANPHAM_FK, SUM(isnull(kksp.SOLUONGDIEUCHINH,0))  \n"+
						 "		from ERP_KIEMKHO_SANPHAM kksp  \n"+
						 "		inner join ERP_KIEMKHO kk on kk.PK_SEQ = kksp.KIEMKHO_FK  \n"+
						 "		where kk.TRANGTHAI=1 and kk.NGAYKIEM>='"+obj.getTuNgay()+"' and kk.NGAYKIEM<='"+obj.getDenNgay()+"' and kk.KHOTT_FK="+obj.getKhoIds()+"  \n"+
						 "		group by kksp.SANPHAM_FK   \n"+
						 ") A group by A.spId " +
						 " ) TONG_NHAP  \n"+
						 " inner join ERP_SANPHAM sp on  TONG_NHAP.spId= sp.PK_SEQ  \n"+
						 " left join DONVIDOLUONG dvdl on dvdl.PK_SEQ= sp.DVDL_FK   " +
						 " left join erp_loaisanpham lsp on lsp.pk_seq=sp.loaisanpham_fk " +
						 " left join chungloai cl on cl.pk_seq=sp.chungloai_fk ";


		 ResultSet rs = db.get(query);

		Cell cell = null;

		 int i=7;
		 if(rs!=null){
			 while(rs.next()){
					cell = cells.getCell("A" + Integer.toString(i));cell.setValue(tenkho);
					cell = cells.getCell("B" + Integer.toString(i));cell.setValue(rs.getString("loaisanpham"));
					cell = cells.getCell("C" + Integer.toString(i));cell.setValue(rs.getString("chungloai"));
					cell = cells.getCell("D" + Integer.toString(i));cell.setValue(rs.getString("ma"));
					cell = cells.getCell("E" + Integer.toString(i));cell.setValue(rs.getString("ten"));
					cell = cells.getCell("F" + Integer.toString(i));cell.setValue(rs.getString("diengiai"));
					cell = cells.getCell("G" + Integer.toString(i));cell.setValue(rs.getDouble("tong_soluong"));

					i++;
			 }rs.close();
			 db.shutDown();
		 }

		return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}

	}
	 */
	/*
	private boolean CreateStaticData(Workbook workbook, IBaocao obj, String condition) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();

		String[] param = new String[8];
		param[0] = obj.getTuNgay().equals("") ? null : obj.getTuNgay();
		param[1] = obj.getDenNgay().equals("") ? null : obj.getDenNgay();
		param[2] = obj.getUserId().equals("") ? null : obj.getUserId();
		param[3] = obj.getChungloaiIds().equals("") ? null : "" + obj.getChungloaiIds().substring(0,obj.getChungloaiIds().length() ) + "" ;
		param[4] = obj.getLoaiSanPhamIds().equals("") ? null : "" + obj.getLoaiSanPhamIds().substring(0,obj.getLoaiSanPhamIds().length() ) + "";
		param[5] = obj.getSanPhamIds().equals("") ? null : "" + obj.getSanPhamIds().substring(0,obj.getSanPhamIds().length() ) + "";
		param[6] = obj.getKhoIds().equals("") ? null : "" + obj.getKhoIds().substring(0,obj.getKhoIds().length() ) + "";;
		param[7] = obj.getNdnhapIds().equals("") ? null : "" + obj.getNdnhapIds().substring(0,obj.getNdnhapIds().length() ) + "";;


		System.out.println(" param :  " + param[0]  +" ,"+ param [1]+","+ param[6]);

		ResultSet rs = db.getRsByPro("sp_Hang_Nhap_Kho_TT", param);
		int i = 2;
		if(obj.getPivot().trim().equals("0")){
			i=7;
		}

		if (rs != null)
		{

			try
			{
				cells.setColumnWidth(0, 15.0f);
				cells.setColumnWidth(1, 15.0f);
				cells.setColumnWidth(2, 15.0f);
				cells.setColumnWidth(3, 15.0f);
				cells.setColumnWidth(4, 15.0f);
				cells.setColumnWidth(5, 15.0f);
				cells.setColumnWidth(6, 15.0f);
				cells.setColumnWidth(7, 15.0f);
				cells.setColumnWidth(8, 15.0f);

				Cell cell = null;
				while (rs.next())
				{

							if(obj.getPivot().trim().equals("0")){

								cell = cells.getCell("A" + Integer.toString(i));cell.setValue(rs.getString("Kho"));
								cell = cells.getCell("B" + Integer.toString(i));cell.setValue(rs.getString("Type"));
								cell = cells.getCell("C" + Integer.toString(i));cell.setValue(rs.getString("SoChungTu"));
								cell = cells.getCell("D" + Integer.toString(i));cell.setValue(rs.getString("NgayChungTu"));
								cell = cells.getCell("E" + Integer.toString(i));cell.setValue(rs.getString("ChungLoai")==null?" ":rs.getString("ChungLoai"));
								cell = cells.getCell("F" + Integer.toString(i));cell.setValue(rs.getString("LoaiSanPham")==null?" ":rs.getString("LoaiSanPham") );
								cell = cells.getCell("G" + Integer.toString(i));cell.setValue(rs.getString("SpId"));
								cell = cells.getCell("H" + Integer.toString(i));cell.setValue(rs.getString("spMa"));
								cell = cells.getCell("I" + Integer.toString(i));cell.setValue(rs.getString("spTen"));
								cell = cells.getCell("J" + Integer.toString(i));cell.setValue(rs.getString("Dvdl")==null?" " :rs.getString("Dvdl"));
								cell = cells.getCell("K" + Integer.toString(i));cell.setValue(rs.getDouble("SoKg"));
								cell = cells.getCell("L" + Integer.toString(i));cell.setValue(rs.getDouble("SoLuong"));

								cell = cells.getCell("M" + Integer.toString(i));cell.setValue(rs.getDouble("THANHTIEN"));

								//MANCC,TENNCC,SOHOADON,NGAYHOADON
								cell = cells.getCell("N" + Integer.toString(i));cell.setValue(rs.getString("MANCC"));
								cell = cells.getCell("O" + Integer.toString(i));cell.setValue(rs.getString("TENNCC"));
								cell = cells.getCell("P" + Integer.toString(i));cell.setValue(rs.getString("SOHOADON"));
								cell = cells.getCell("Q" + Integer.toString(i));cell.setValue(rs.getString("NGAYHOADON"));

							}else{
								cell = cells.getCell("FA" + Integer.toString(i));cell.setValue(rs.getString("Kho"));
								cell = cells.getCell("FB" + Integer.toString(i));cell.setValue(rs.getString("Type"));
								cell = cells.getCell("FC" + Integer.toString(i));cell.setValue(rs.getString("SoChungTu"));
								cell = cells.getCell("FD" + Integer.toString(i));cell.setValue(rs.getString("NgayChungTu"));
								cell = cells.getCell("FE" + Integer.toString(i));cell.setValue(rs.getString("ChungLoai")==null?" ":rs.getString("ChungLoai"));
								cell = cells.getCell("FF" + Integer.toString(i));cell.setValue(rs.getString("LoaiSanPham")==null?" ":rs.getString("LoaiSanPham") );
								cell = cells.getCell("FG" + Integer.toString(i));cell.setValue(rs.getString("SpId"));
								cell = cells.getCell("FH" + Integer.toString(i));cell.setValue(rs.getString("spMa"));
								cell = cells.getCell("FI" + Integer.toString(i));cell.setValue(rs.getString("MA"));
								cell = cells.getCell("FJ" + Integer.toString(i));cell.setValue(rs.getString("spTen"));
								cell = cells.getCell("FK" + Integer.toString(i));cell.setValue(rs.getString("QuyCach")==null?" " :rs.getString("QuyCach"));
								cell = cells.getCell("FL" + Integer.toString(i));cell.setValue(rs.getString("Dvdl")==null?" " :rs.getString("Dvdl"));
								cell = cells.getCell("FM" + Integer.toString(i));cell.setValue(rs.getDouble("SoKg"));
								cell = cells.getCell("FN" + Integer.toString(i));cell.setValue(rs.getDouble("SoLuong"));
								//MANCC,TENNCC,SOHOADON,NGAYHOADON
								cell = cells.getCell("FO" + Integer.toString(i));cell.setValue(rs.getString("MANCC"));
								cell = cells.getCell("FP" + Integer.toString(i));cell.setValue(rs.getString("TENNCC"));
								cell = cells.getCell("FQ" + Integer.toString(i));cell.setValue(rs.getString("SOHOADON"));
								cell = cells.getCell("FR" + Integer.toString(i));cell.setValue(rs.getString("NGAYHOADON"));
								cell = cells.getCell("FS" + Integer.toString(i));cell.setValue(rs.getString("NGUONGOCHH"));
							}

					i++;
				}
				if (rs != null)
					rs.close();
				if (db != null)
					db.shutDown();
				if (i == 2)
				{
					throw new Exception("Khong co bao cao trong thoi gian nay...");
				}

			} catch (Exception e)
			{
				System.out.println("115.Exception: " + e.getMessage());
				e.printStackTrace();
				throw new Exception(e.getMessage());
			}
			return true;
		} else{

			System.out.println(" bi null");
			if (db != null){
				db.shutDown();
			}	
			return false;		
		}
	}

	 */







}

