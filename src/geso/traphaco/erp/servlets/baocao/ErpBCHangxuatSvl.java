package geso.traphaco.erp.servlets.baocao;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
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

public class ErpBCHangxuatSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErpBCHangxuatSvl() {
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

		if (userId.length()==0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		obj = new Baocao();
		obj.setUserId(userId);
		obj.createRs_XUATKHO();
		obj.setFlag("0");
		session.setAttribute("obj", obj);

		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCHangXuatKho.jsp";
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
		if(tungay == null)
			tungay = "";
		obj.setTuNgay(tungay);

		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenNgay(denngay);

		String getpivot = request.getParameter("getpivot");
		if(getpivot == null)
			getpivot = "0";
		obj.setPivot(getpivot);

		String loaisp = request.getParameter("loaisanpham");
		if(loaisp == null)
			loaisp = "";
		obj.setLoaiSanPhamIds(loaisp);

		String khoId = request.getParameter("khoId");
		if(khoId == null)
			khoId = "";
		obj.setKhoIds(khoId);
		
		String khonhanId = request.getParameter("khonhanId");
		if(khonhanId == null)
			khonhanId = "";
		obj.setKhonhanIds(khonhanId);

		String khoTen = request.getParameter("khoTen");
		if(khoTen == null)
			khoTen = "";
		obj.setKhoTen(khoTen);

		String flag = request.getParameter("flag");
		if(flag == null)
			flag = "0";
		obj.setFlag(flag);

		String[] spIds = request.getParameterValues("sanpham");
		String spId = "";
		if(spIds != null)
		{
			for(int i = 0; i < spIds.length; i++)
				spId += spIds[i] + ",";
			if(spId.length() > 0)
				spId = spId.substring(0, spId.length() - 1);
			obj.setSanPhamIds(spId);
		}

		String[] clIds = request.getParameterValues("clIds");
		String clId = "";
		if(clIds != null)
		{
			for(int i = 0; i < clIds.length; i++)
				clId += clIds[i] + ",";
			if(clId.length() > 0)
				clId = clId.substring(0, clId.length() - 1);
			obj.setChungloaiIds(clId);
		}

		String[] ndxIds = request.getParameterValues("ndxIds");
		String ndxId = "";
		if (ndxIds != null)
		{
			for (int i = 0; i < ndxIds.length; i++)
				ndxId += ndxIds[i] + ",";
			if (ndxId.length() > 0)
				ndxId = ndxId.substring(0, ndxId.length() - 1);
			obj.setNdxuatIds(ndxId);
		}

		String action = request.getParameter("action");
		System.out.println("Action nhan duoc: " + action);

		if(action.equals("search"))
		{
			obj.createRs_XUATKHO();
			session.setAttribute("obj", obj);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCHangXuatKho.jsp";
			response.sendRedirect(nextJSP);
		}

		else if(action.equals("web"))
		{
			obj.createRs_XUATKHO();
			List<HangXuatKhoObj> list = BaoCaoHangXuatKho(obj);
			
			session.setAttribute("ListXNT", list);
			session.setAttribute("ListXNT_check", "1");
			session.setAttribute("obj", obj);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCHangXuatKho.jsp";
			response.sendRedirect(nextJSP);

		}
		else
		{
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=HangXuatKhod.xlsm");

			boolean isTrue = false;
			
			try 
			{
				
				isTrue = CreatePivotTable(out, obj, "");	
				
			} 
			catch (Exception e) 

			{
				e.printStackTrace();
				isTrue = false;
			}

			if(!isTrue)
			{
				obj.createRs_XUATKHO();
				session.setAttribute("obj", obj);
				obj.setMsg("Không thể tạo báo cáo");

				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCHangXuatKho.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}



	private List<HangXuatKhoObj> BaoCaoHangXuatKho(IBaocao obj){

		Utility util = new Utility();
		dbutils db = new dbutils();
		List<HangXuatKhoObj> list = new ArrayList<HangXuatKhoObj>();

		String query ="\n SELECT ROW_NUMBER()  OVER (  ORDER BY MAKHO DESC) AS STT, ID, MAKHO +' - ' + tenkho  as khoxuat , makhonhan + ' - '+ tenkhonhan  as khonhan ,loaichungtu,  machungtu,ngaychungtu,LENHSANXUAT_FK,  lydo, thanhphamsp,   LOAISANPHAM,MASP,TENSP,DONVI,  ROUND( SUM(SOLUONG),3)  AS SOLUONG,ROUND(  DONGIA, 3) AS DONGIA, ROUND(  (SUM(SOLUONG) * DONGIA),0 ) as THANHTIEN ,'' as ghichu,  kho_fk,sanpham_fk , maloaisp "+    
		"\n  FROM		  "+    
		"\n   (SELECT ID as ID, makho as MAKHO,  kho_fk as kho_fk, tenkho as tenkho, khonhan_fk, makhonhan, tenkhonhan ," 
		+"\n  isnull( loaichungtu,'') as  loaichungtu,isnull( machungtu,'') as machungtu ,ngaychungtu as ngaychungtu, "
		+"\n  isnull(LENHSANXUAT_FK,0) as LENHSANXUAT_FK,  isnull( lydo,'') as lydo , isnull( thanhphamsp,'') as thanhphamsp ,  maloaisp_pkseq as maloaisp_pkseq,  maloaisp as maloaisp, loaisanpham as loaisanpham,sanpham_fk as sanpham_fk,  spma as masp, spten as tensp, donvi as donvi, xuat as soluong, isnull( dongia,0)  as dongia, NOIDUNGXUAT_FK AS NOIDUNGXUAT_FK  "+    
		"\n  FROM [UFN_XUATKHO_HO_TRONGKY]('"+obj.getTuNgay()+ "','" +obj.getDenNgay()+ " ') WHERE 1=1";

		
		
		if(obj.getKhoIds().length()>0){
			query	+="\n  AND  kho_fk IN (" +obj.getKhoIds()+" ) ";
		}
		
		if(obj.getKhonhanIds().length()>0){
			query	+="\n  AND  khonhan_fk IN (" +obj.getKhonhanIds()+" ) ";
		}


		if(obj.getLoaiSanPhamIds().length()>0){
			query	+="\n  AND  maloaisp_pkseq IN (" +obj.getLoaiSanPhamIds()+" ) ";
		}

		if(obj.getSanPhamIds().length()>0){
			query	+="\n  AND  sanpham_fk IN (" +obj.getSanPhamIds()+" ) ";
		}

		if(obj.getNdxuatIds().length()>0){
			query	+="\n  AND  NOIDUNGXUAT_FK IN (" +obj.getNdxuatIds()+" ) ";
		}


		query	+=	 ")  DATA     \n  Group by MAKHO , tenkho ,makhonhan , tenkhonhan,loaichungtu,  machungtu,ngaychungtu,LENHSANXUAT_FK, lydo, thanhphamsp,   LOAISANPHAM,MASP,TENSP,DONGIA ,DONVI,  kho_fk,sanpham_fk , maloaisp , ID ";			 

	
		System.out.println(" qr web: "+ query);
		ResultSet  chiTietNXT = db.get(query);

		try 
		{
			if(chiTietNXT != null)
			{
				while(chiTietNXT.next())
				{
					String id=chiTietNXT.getString("id");
					String makhoxuat= chiTietNXT.getString("khoxuat");
					String makhonhan = chiTietNXT.getString("khonhan");
					
					String loaichungtu = chiTietNXT.getString("loaichungtu");
					String machungtu= chiTietNXT.getString("machungtu");
					String ngaychungtu= chiTietNXT.getString("ngaychungtu");
					
					String lenhsanxuat=chiTietNXT.getString("LENHSANXUAT_FK");
					String lydo= chiTietNXT.getString("lydo");
					String thanhphamsp= chiTietNXT.getString("thanhphamsp");
					
					String loaisp = chiTietNXT.getString("LOAISANPHAM");
					String masp = chiTietNXT.getString("MASP");
					String tensp = chiTietNXT.getString("TENSP");
					String donvi = chiTietNXT.getString("DONVI");
					double soluong = chiTietNXT.getDouble("SOLUONG");
					double dongia = chiTietNXT.getDouble("DONGIA");
					double thanhtien = chiTietNXT.getDouble("THANHTIEN");

					HangXuatKhoObj temp = new HangXuatKhoObj();
					temp.setId(id);
					temp.setMakhoxuat(makhoxuat);
					temp.setMakhonhan(makhonhan);
					
					temp.setLoaichungtu(loaichungtu);
					temp.setMachungtu(machungtu);
					temp.setNgaychungtu(ngaychungtu);
					
					temp.setLenhsanxuat(lenhsanxuat);
					temp.setLydo(lydo);
					temp.setThanhphamsp(thanhphamsp);
					
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
			
			fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\HangXuatKhod.xlsm");
		}
		else
			if(obj.getLoaibaocao().equals("2"))
		{
			fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\HangXuatKhod1.xlsm");
			
		}
		
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		boolean isTrue=false;
		
	
		
		if(obj.getLoaibaocao().equals("2"))
		{
			//chi tiet
			isTrue = CreateExcel_BChangxuatkhoChiTiet(workbook, obj, condition);
		}
		else
			if(obj.getLoaibaocao().equals("1"))
		{
				//tong quat
			isTrue = CreateExcel_BChangxuatkho(workbook, obj, condition);
		}
		
		
		if(!isTrue){
			return false;
		}
		workbook.save(out);

		fstream.close();
		return true;
	}


// bao cao chi tiet
	private boolean CreateExcel_BChangxuatkhoChiTiet(Workbook workbook, IBaocao obj, String condition) throws Exception
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


			cell = cells.getCell("B2");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 9,"Từ ngày: "+  obj.getTuNgay());

			cell = cells.getCell("D2");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 9,"Đến ngày: "+  obj.getDenNgay());


			cell = cells.getCell("B3");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 9,"Ngày báo cáo: "+ ReportAPI.NOW("yyyy-MM-dd"));

			cell = cells.getCell("B4");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 9,"Được tạo bởi: "+  obj.getUserTen());



			//--------------------  cau lay du lieu  --------------------------
			String query ="\n SELECT ROW_NUMBER()  OVER (  ORDER BY MAKHO,loaichungtu,machungtu,ngaychungtu DESC) AS STT, sophieu,MAKHO +' - ' + tenkho  as khoxuat , makhonhan + ' - '+ tenkhonhan  as khonhan ,loaichungtu,  machungtu,ngaychungtu,lenhsanxuat,thanhphamsp,lydo,MASP,TENSP,DONVI,  ROUND( SUM(SOLUONG),3)  AS SOLUONG,ROUND(  DONGIA, 3) AS DONGIA, ROUND(  (SUM(SOLUONG) * DONGIA),0 ) as THANHTIEN ,'' as ghichu "+    
			"\n  FROM		  "+    
			"\n   (SELECT ID as sophieu, makho as MAKHO,  kho_fk as kho_fk, tenkho as tenkho, makhonhan, tenkhonhan , isnull( loaichungtu,'') as  loaichungtu,isnull( machungtu,'') as machungtu ,ngaychungtu as ngaychungtu,isnull( lydo,'') as lydo , isnull( thanhphamsp,'') as thanhphamsp ,  maloaisp_pkseq as maloaisp_pkseq,  maloaisp as maloaisp, LENHSANXUAT_FK as lenhsanxuat,sanpham_fk as sanpham_fk,  spma as masp, spten as tensp, donvi as donvi, xuat as soluong, isnull( dongia,0)  as dongia, NOIDUNGXUAT_FK AS NOIDUNGXUAT_FK , maphieu  "+    
			"\n  FROM [UFN_XUATKHO_HO_TRONGKY]('"+obj.getTuNgay()+ "','" +obj.getDenNgay()+ " ') WHERE 1=1";

			if(obj.getKhoIds().length()>0){
				query	+="\n  AND  kho_fk IN (" +obj.getKhoIds()+" ) ";
			}

			if(obj.getKhonhanIds().length()>0){
				query	+="\n  AND  khonhan_fk IN (" +obj.getKhonhanIds()+" ) ";
			}
			
			if(obj.getLoaiSanPhamIds().length()>0){
				query	+="\n  AND  maloaisp_pkseq IN (" +obj.getLoaiSanPhamIds()+" ) ";
			}

			if(obj.getSanPhamIds().length()>0){
				query	+="\n  AND  sanpham_fk IN (" +obj.getSanPhamIds()+" ) ";
			}

			if(obj.getNdxuatIds().length()>0){
				query	+="\n  AND  NOIDUNGXUAT_FK IN (" +obj.getNdxuatIds()+" ) ";
			}


			query	+=	 ")  DATA     \n  Group by  MAKHO,sophieu, maphieu  , tenkho ,makhonhan , tenkhonhan,loaichungtu,  machungtu,ngaychungtu, lydo, thanhphamsp,   lenhsanxuat,MASP,TENSP,DONGIA ,DONVI,  kho_fk,sanpham_fk , maloaisp  ";			 

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
					System.out.println("ELEMENT "+i+" --"+rs.getString(i));
					cell = cells.getCell(countRow,column + i-1 );

					if( i==1 || i==14 ||i==15||i==16){

						if(i==14 ||i==15){
							
						cell.setValue(formatter1.format(rs.getDouble(i))  );
						}
						if (i==16){
							cell.setValue(formatter.format(rs.getDouble(i)) );
						}
						else
							cell.setValue(formatter1.format(rs.getDouble(i))  );
					}else if( i==11 || i==12 ||i==13){
						cell.setValue(rs.getString(i));
					}
					else{
						cell.setValue(isSame?"":rs.getString(i));
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
			cells.merge(countRow, 0, countRow, 12);
			cell.setValue("Tổng cộng");
			setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED,true);

			cell = cells.getCell(countRow,14);
			cells.merge(countRow, 14, countRow, 15);
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

// bao cao tong quat
	private boolean CreateExcel_BChangxuatkho(Workbook workbook, IBaocao obj, String condition) throws Exception
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


			cell = cells.getCell("B2");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 9,"Từ ngày: "+  obj.getTuNgay());

			cell = cells.getCell("D2");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 9,"Đến ngày: "+  obj.getDenNgay());


			cell = cells.getCell("B3");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 9,"Ngày báo cáo: "+ ReportAPI.NOW("yyyy-MM-dd"));

			cell = cells.getCell("B4");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 9,"Được tạo bởi: "+  obj.getUserTen());



			//--------------------  cau lay du lieu  --------------------------
			String query ="\nSELECT ROW_NUMBER()  OVER (  ORDER BY MAKHO DESC) AS STT, ID, MAKHO as MAKHO,tenkho  as khoxuat ,MASP,TENSP,DONVI,  ROUND( SUM(SOLUONG),3)  AS SOLUONG,ROUND(  DONGIA, 3) AS DONGIA, ROUND(  (SUM(SOLUONG) * DONGIA),0 ) as THANHTIEN  "+    
			"\n  FROM		  "+    
			"\n   (SELECT ID as ID, makho as MAKHO,  kho_fk as kho_fk, tenkho as tenkho,khonhan_fk, makhonhan, tenkhonhan , isnull( loaichungtu,'') as  loaichungtu,isnull( machungtu,'') as machungtu ,ngaychungtu as ngaychungtu, LENHSANXUAT_FK,  isnull( lydo,'') as lydo , isnull( thanhphamsp,'') as thanhphamsp ,  maloaisp_pkseq as maloaisp_pkseq,  maloaisp as maloaisp, loaisanpham as loaisanpham,sanpham_fk as sanpham_fk,  spma as masp, spten as tensp, donvi as donvi, xuat as soluong, isnull( dongia,0)  as dongia, NOIDUNGXUAT_FK AS NOIDUNGXUAT_FK  "+    
			"\n  FROM [UFN_XUATKHO_HO_TRONGKY]('"+obj.getTuNgay()+ "','" +obj.getDenNgay()+ " ') WHERE 1=1";

			if(obj.getKhoIds().length()>0){
				query	+="\n  AND  kho_fk IN (" +obj.getKhoIds()+" ) ";
			}
			
			if(obj.getKhonhanIds().length()>0){
				query	+="\n  AND  khonhan_fk IN (" +obj.getKhonhanIds()+" ) ";
			}


			if(obj.getLoaiSanPhamIds().length()>0){
				query	+="\n  AND  maloaisp_pkseq IN (" +obj.getLoaiSanPhamIds()+" ) ";
			}

			if(obj.getSanPhamIds().length()>0){
				query	+="\n  AND  sanpham_fk IN (" +obj.getSanPhamIds()+" ) ";
			}

			if(obj.getNdxuatIds().length()>0){
				query	+="\n  AND  NOIDUNGXUAT_FK IN (" +obj.getNdxuatIds()+" ) ";
			}


			query	+=	 ")  DATA     \n  Group by MAKHO , tenkho ,makhonhan , tenkhonhan,loaichungtu,  machungtu,ngaychungtu, lydo, thanhphamsp,LENHSANXUAT_FK,   LOAISANPHAM,MASP,TENSP,DONGIA ,DONVI,  kho_fk,sanpham_fk , maloaisp, ID  ";			 

			System.out.println("hang nhap kho tong quat: "+ query);
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
			
//
			while (rs.next()) {
				// chi lay 10 dong dau
				for(int i =1;i <=socottrongSql ; i ++)
				{
					cell = cells.getCell(countRow,column + i-1 );

					if( i==1 || i==15 ||i==16||i==17){

						if(i==15 ||i==16){
						cell.setValue(formatter1.format(rs.getDouble(i))  );
						}
						if (i==17){
							cell.setValue(formatter.format(rs.getDouble(i)) );
						}
						else
							cell.setValue(formatter1.format(rs.getDouble(i))  );
					}
					else{
						
						cell.setValue(rs.getString(i));
						
						if(i==8){

							if(rs.getString(i).equals("0"))
							{
								cell.setValue("");
							}
						}

					}


					if(i==1){
						setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED,false);
					} else{
						setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,false);
					}
					if(i==17){
						tongtien+=rs.getDouble(i);
					}

				}
				countRow++;
			}

			// chen dong tong cong +thanh tien
			cell = cells.getCell(countRow,0);
			cells.merge(countRow, 0, countRow, 14);
			cell.setValue("Tổng cộng");
			setCellBorderStyle(cell, HorizontalAlignmentType.CENTRED,true);

			cell = cells.getCell(countRow,15);
			cells.merge(countRow, 15, countRow, 16);
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




	/*	private void CreateStaticHeader(Workbook workbook,IBaocao  obj) 
	{
		 String dateFrom= obj.getTuNgay(); String dateTo =obj.getDenNgay(); String UserName=obj.getUserTen();

		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);

	    worksheet.setName("Sheet1");

	    Cells cells = worksheet.getCells();

	    Style style;
	    Font font = new Font();
	    font.setColor(Color.RED);//mau chu
	    font.setSize(16);// size chu
	   	font.setBold(true);

	    cells.setRowHeight(0, 20.0f);
	    Cell cell = cells.getCell("A1");
	    style = cell.getStyle();
	    style.setFont(font); 
	    style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu 	  

	    String tieude = "BÁO CÁO HÀNG XUẤT KHO";
	    ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);

	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A2");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Từ ngày: " + dateFrom);

	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("B2");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Đến ngày: " + dateTo);

	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A3");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));

	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A4");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + UserName);

	    System.out.println(obj.getFlag());

	    if(obj.getPivot().trim().equals("0")){
	    	cell = cells.getCell("A6");cell.setValue("KhoXuat");ReportAPI.setCellHeader(cell);
	    	cell = cells.getCell("B6");cell.setValue("KhoNhan");ReportAPI.setCellHeader(cell);
			cell = cells.getCell("C6");cell.setValue("LoaiChungTu");ReportAPI.setCellHeader(cell);
			cell = cells.getCell("D6");cell.setValue("SoChungTu");ReportAPI.setCellHeader(cell);
			cell = cells.getCell("E6");cell.setValue("NgayChungTu");ReportAPI.setCellHeader(cell);
			cell = cells.getCell("F6");cell.setValue("ChungLoai");ReportAPI.setCellHeader(cell);
			cell = cells.getCell("G6");cell.setValue("LoaiSanPham");ReportAPI.setCellHeader(cell);
			cell = cells.getCell("H6");cell.setValue("SanPhamID");ReportAPI.setCellHeader(cell);
			cell = cells.getCell("I6");cell.setValue("MaSanPham");ReportAPI.setCellHeader(cell);
			cell = cells.getCell("J6");cell.setValue("TenSanPham");ReportAPI.setCellHeader(cell);
			cell = cells.getCell("K6");cell.setValue("DonViDoLuong");ReportAPI.setCellHeader(cell);
			cell = cells.getCell("L6");cell.setValue("SoKG");ReportAPI.setCellHeader(cell);
			cell = cells.getCell("M6");cell.setValue("SoLuong");ReportAPI.setCellHeader(cell);
			cell = cells.getCell("N6");cell.setValue("DonGia");ReportAPI.setCellHeader(cell);
			cell = cells.getCell("O6");cell.setValue("ThanhTien");ReportAPI.setCellHeader(cell);
			cell = cells.getCell("P6");cell.setValue("GhiChu");ReportAPI.setCellHeader(cell);

			cell = cells.getCell("Q6");cell.setValue("TenKhachHang");ReportAPI.setCellHeader(cell);
	    }else{
			cell = cells.getCell("FA1");cell.setValue("KhoXuat");ReportAPI.setCellHeader(cell);
			cell = cells.getCell("FB1");cell.setValue("KhoNhan");ReportAPI.setCellHeader(cell);
			cell = cells.getCell("FC1");cell.setValue("LoaiChungTu");ReportAPI.setCellHeader(cell);
			cell = cells.getCell("FD1");cell.setValue("SoChungTu");ReportAPI.setCellHeader(cell);
			cell = cells.getCell("FE1");cell.setValue("NgayChungTu");ReportAPI.setCellHeader(cell);
			cell = cells.getCell("FF1");cell.setValue("ChungLoai");ReportAPI.setCellHeader(cell);
			cell = cells.getCell("FG1");cell.setValue("LoaiSanPham");ReportAPI.setCellHeader(cell);
			cell = cells.getCell("FH1");cell.setValue("SanPhamID");ReportAPI.setCellHeader(cell);
			cell = cells.getCell("FI1");cell.setValue("MaSanPham");ReportAPI.setCellHeader(cell);
			cell = cells.getCell("FJ1");cell.setValue("TenSanPham");ReportAPI.setCellHeader(cell);
			cell = cells.getCell("FK1");cell.setValue("DonViDoLuong");ReportAPI.setCellHeader(cell);
			cell = cells.getCell("FL1");cell.setValue("SoKG");ReportAPI.setCellHeader(cell);
			cell = cells.getCell("FM1");cell.setValue("SoLuong");ReportAPI.setCellHeader(cell);
			cell = cells.getCell("FN1");cell.setValue("DonGia");ReportAPI.setCellHeader(cell); 
			cell = cells.getCell("FO1");cell.setValue("ThanhTien");ReportAPI.setCellHeader(cell);
			cell = cells.getCell("FP1");cell.setValue("GhiChu");ReportAPI.setCellHeader(cell); 
			cell = cells.getCell("FQ1");cell.setValue("TenKhachHang");ReportAPI.setCellHeader(cell);
	    }	
	}

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
		param[3] = obj.getChungloaiIds().equals("") ? null : "'" + obj.getChungloaiIds().substring(0,obj.getChungloaiIds().length() ) + "'" ;
		param[4] = obj.getLoaiSanPhamIds().equals("") ? null : "'" + obj.getLoaiSanPhamIds().substring(0,obj.getLoaiSanPhamIds().length() ) + "'";
		param[5] = obj.getSanPhamIds().equals("") ? null : "'" + obj.getSanPhamIds().substring(0,obj.getSanPhamIds().length() ) + "'";;
		param[6] = obj.getKhoIds().equals("") ? null : "'" + obj.getKhoIds().substring(0,obj.getKhoIds().length() ) + "'";;
		param[7] = obj.getNdxuatIds().equals("") ? null : "'" + obj.getNdxuatIds().substring(0,obj.getNdxuatIds().length() ) + "'";;

		ResultSet rs = db.getRsByPro("sp_Hang_Xuat_Kho_TT", param);
		int i = 2;
		if(obj.getPivot().trim().equals("0")){
			i=7;
		}	

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
				if (rs != null)
				while (rs.next())
				{
				    if(obj.getPivot().trim().equals("0")){
				    	cell = cells.getCell("A" + Integer.toString(i));cell.setValue(rs.getString("Kho"));
				    	cell = cells.getCell("B" + Integer.toString(i));cell.setValue(rs.getString("KHONHAN"));

						cell = cells.getCell("C" + Integer.toString(i));cell.setValue(rs.getString("Type"));
						cell = cells.getCell("D" + Integer.toString(i));cell.setValue(rs.getString("SoChungTu"));
						cell = cells.getCell("E" + Integer.toString(i));cell.setValue(rs.getString("NgayChungTu"));
						cell = cells.getCell("F" + Integer.toString(i));cell.setValue(rs.getString("ChungLoai")==null?" ":rs.getString("ChungLoai"));
						cell = cells.getCell("G" + Integer.toString(i));cell.setValue(rs.getString("LoaiSanPham")==null?" ":rs.getString("LoaiSanPham") );
						cell = cells.getCell("H" + Integer.toString(i));cell.setValue(rs.getString("SpId"));
						cell = cells.getCell("I" + Integer.toString(i));cell.setValue(rs.getString("spMa"));
						cell = cells.getCell("J" + Integer.toString(i));cell.setValue(rs.getString("spTen"));
						cell = cells.getCell("K" + Integer.toString(i));cell.setValue(rs.getString("Dvdl")==null?" " :rs.getString("Dvdl"));
						cell = cells.getCell("L" + Integer.toString(i));cell.setValue(rs.getDouble("SoKg"));
						cell = cells.getCell("M" + Integer.toString(i));cell.setValue(rs.getDouble("SoLuong"));
						cell = cells.getCell("N" + Integer.toString(i));cell.setValue(rs.getDouble("DonGia"));
						cell = cells.getCell("O" + Integer.toString(i));cell.setValue(rs.getDouble("SoLuong")*rs.getDouble("DonGia"));

						cell = cells.getCell("P" + Integer.toString(i));cell.setValue(rs.getString("ghichu").length() <=0  ? "NA":rs.getString("ghichu"));
						cell = cells.getCell("Q" + Integer.toString(i));cell.setValue(rs.getString("tenkh").length() <=0  ? "NA":rs.getString("tenkh"));

				    }else{
						cell = cells.getCell("FA" + Integer.toString(i));cell.setValue(rs.getString("Kho"));
						cell = cells.getCell("FB" + Integer.toString(i));cell.setValue(rs.getString("KhoNHAN"));

						cell = cells.getCell("FC" + Integer.toString(i));cell.setValue(rs.getString("Type"));
						cell = cells.getCell("FD" + Integer.toString(i));cell.setValue(rs.getString("SoChungTu"));
						cell = cells.getCell("FE" + Integer.toString(i));cell.setValue(rs.getString("NgayChungTu"));
						cell = cells.getCell("FF" + Integer.toString(i));cell.setValue(rs.getString("ChungLoai")==null?" ":rs.getString("ChungLoai"));
						cell = cells.getCell("FG" + Integer.toString(i));cell.setValue(rs.getString("LoaiSanPham")==null?" ":rs.getString("LoaiSanPham") );
						cell = cells.getCell("FH" + Integer.toString(i));cell.setValue(rs.getString("SpId"));
						cell = cells.getCell("FI" + Integer.toString(i));cell.setValue(rs.getString("spMa"));
						cell = cells.getCell("FJ" + Integer.toString(i));cell.setValue(rs.getString("spTen"));
						cell = cells.getCell("FK" + Integer.toString(i));cell.setValue(rs.getString("Dvdl")==null?" " :rs.getString("Dvdl"));
						cell = cells.getCell("FL" + Integer.toString(i));cell.setValue(rs.getDouble("SoKg"));
						cell = cells.getCell("FM" + Integer.toString(i));cell.setValue(rs.getDouble("SoLuong"));
						cell = cells.getCell("FN" + Integer.toString(i));cell.setValue(rs.getDouble("DonGia"));
						cell = cells.getCell("FO" + Integer.toString(i));cell.setValue(rs.getDouble("soluong")*rs.getDouble("DonGia") );

						cell = cells.getCell("FP" + Integer.toString(i));cell.setValue(rs.getString("ghichu").length() <=0  ? "NA":rs.getString("ghichu"));
						cell = cells.getCell("FQ" + Integer.toString(i));cell.setValue(rs.getString("tenkh").length() <=0 ? "NA":rs.getString("tenkh"));
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


		if (db != null)
			db.shutDown();
		return true;	
	}

	 */
}
