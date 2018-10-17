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

public class ErpTonkhotrungtamSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public ErpTonkhotrungtamSvl()
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
		obj.createRsBCKHO();
		session.setAttribute("obj", obj);

		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCTonKhoTrungTam.jsp";
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

		String tungay = request.getParameter("tungay");
		if (tungay == null)
			tungay = "";
		obj.setTuNgay(tungay);

		String denngay = request.getParameter("denngay");
		if (denngay == null)
			denngay = "";
		obj.setDenNgay(denngay);

		String khoId = request.getParameter("khoId");
		if (khoId == null)
			khoId = "";
		obj.setKhoIds(khoId);

		String khoTen = request.getParameter("khoTen");
		if (khoTen == null)
			khoTen = "";
		obj.setKhoTen(khoTen);

		String flag = request.getParameter("flag");
		if (flag == null)
			flag = "0";
		obj.setFlag(flag);

		
		String khotong = request.getParameter("khotong");
		if (khotong == null)
			khotong = "0";
		obj.setKhotong(khotong);
		
		
		String pivot = request.getParameter("pivot");
		if (pivot == null)
			pivot = "0";
		obj.setPivot(pivot);
		
		String xemtheolo = request.getParameter("xemtheolo");
		if (xemtheolo == null)
			xemtheolo = "0";
		obj.setXemtheolo(xemtheolo);

		String[] lspIds = request.getParameterValues("lspIds");
		String lspId = "";
		if (lspIds != null)
		{
			for (int i = 0; i < lspIds.length; i++)
				lspId += lspIds[i] + ",";
			if (lspId.length() > 0)
				lspId = lspId.substring(0, lspId.length() - 1);
			obj.setLoaiSanPhamIds(lspId);
		}

		String[] maspIds = request.getParameterValues("maspIds");
		String maspId = "";
		if (maspIds != null)
		{
			for (int i = 0; i < maspIds.length; i++)
				maspId += "'" + maspIds[i] + "',";
			if (maspId.length() > 0)
				maspId = maspId.substring(0, maspId.length() - 1);
			obj.setMaSanPhamIds(maspId);
		}

		String[] spIds = request.getParameterValues("spIds");
		String spId = "";
		if (spIds != null)
		{
			for (int i = 0; i < spIds.length; i++)
				spId += spIds[i] + ",";
			if (spId.length() > 0)
				spId = spId.substring(0, spId.length() - 1);
			obj.setSanPhamIds(spId);
		}

		
		String action = request.getParameter("action");
		System.out.println("Action nhan duoc: " + action);

		if (action.equals("search"))
		{
			obj.createRsBCKHO();
			session.setAttribute("obj", obj);

			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCTonKhoTrungTam.jsp";
			response.sendRedirect(nextJSP);
		} 
		else 
		{
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=ErpTonHienTaiTT.xlsm");
			
			boolean isTrue = false;
			try
			{
				isTrue = CreatePivotTable(out, obj, "");
			} 
			catch (Exception e)
			{
				isTrue = false;
				e.printStackTrace();
			}

			if (!isTrue)
			{
				obj.createRsBCKHO();
				session.setAttribute("obj", obj);
				obj.setMsg("Không thể tạo báo cáo");
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCTonKhoTrungTam.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}

	private boolean CreatePivotTable(OutputStream out, IBaocao obj, String condition) throws Exception
	{
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();

		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ErpTonHienTaiTT.xlsm");

		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		
		CreateStaticHeader(workbook, obj.getTuNgay(), obj.getDenNgay(), obj.getUserTen(), obj.getPivot().equals("1"), obj.getXemtheolo());
		boolean isTrue = CreateStaticData(workbook, obj, condition, obj.getPivot().equals("1"));
		if (!isTrue)
		{
			return false;
		}
		workbook.save(out);

		fstream.close();
		return true;
	}

	private void CreateStaticHeader(Workbook workbook, String dateFrom, String dateTo, String UserName, boolean pivot, String xemtheolo)
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		
		worksheet.setName("Sheet1");

		Cells cells = worksheet.getCells();

		Style style;
		Font font = new Font();
		font.setColor(Color.RED);// mau chu
		font.setSize(16);// size chu
		font.setBold(true);

		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
		style = cell.getStyle();
		style.setFont(font);
		style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu

		String tieude = "BÁO CÁO TỒN KHO HIỆN TẠI";
		ReportAPI.getCellStyle(cell, Color.RED, true, 14, tieude);

		cells.setRowHeight(4, 18.0f);
		cell = cells.getCell("A3");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));

		cells.setRowHeight(5, 18.0f);
		cell = cells.getCell("A4");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + UserName);


		cell = cells.getCell("BA1"); cell.setValue("Kho"); ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BB1"); cell.setValue("KhuVuc"); ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BC1"); cell.setValue("ViTri"); ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("BD1"); cell.setValue("LoaiSanPham"); ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BE1"); cell.setValue("MaSanPham"); ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BF1"); cell.setValue("TenSanPham"); ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BG1"); cell.setValue("DonVi"); ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("BH1"); cell.setValue("SoLo"); ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BI1"); cell.setValue("NgayHetHan"); ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BJ1"); cell.setValue("NgayNhapKho"); ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("BK1"); cell.setValue("MaMe"); ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BL1"); cell.setValue("MaThung"); ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BM1"); cell.setValue("MaPhieu"); ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BN1"); cell.setValue("PhieuDinhTinh"); ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BO1"); cell.setValue("PhieuEO"); ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("BP1"); cell.setValue("MARQUETTE"); ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BQ1"); cell.setValue("HamAm"); ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BR1"); cell.setValue("HamLuong"); ReportAPI.setCellHeader(cell);
		
		cell = cells.getCell("BS1"); cell.setValue("SoLuong"); ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BT1"); cell.setValue("Booked"); ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BU1"); cell.setValue("HienHuu"); ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BV1"); cell.setValue("NhaSanXuat"); ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BW1"
				+ ""); cell.setValue("MaDoiTuong"); ReportAPI.setCellHeader(cell);
		cell = cells.getCell("BX1"); cell.setValue("TendoiTuong"); ReportAPI.setCellHeader(cell);
	}

	private boolean CreateStaticData(Workbook workbook, IBaocao obj, String condition, boolean pivot) throws Exception
	{
		Utility util = new Utility();
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		String query = "";

		if(obj.getKhotong().trim().equals("1"))
		{
			query="SELECT B.MA + ', ' + B.TEN AS KHOTEN, LSP.TEN AS LSP, '' AS khuvuc, '' AS vitri, e.MA AS SPMA, E.TEN AS SPTEN, dv.DonVi,'' SOLO, '' NGAYHETHAN, '' NGAYNHAPKHO,  "
				 +"\n'' as MARQ,''as hamluong, '' as hamam, '' as mame, '' as mathung, '' as maphieu,'' as phieudt, '' as phieueo,  A.SOLUONG, A.BOOKED, A.AVAILABLE  "
				 +"\nFROM ERP_KHOTT_SANPHAM A  "
				 +"\nINNER JOIN ERP_KHOTT B ON A.KHOTT_FK = B.PK_SEQ   "
				 +"\nINNER JOIN ERP_SANPHAM E ON A.SANPHAM_FK = E.PK_SEQ  "
				 +"\nleft JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = E.LOAISANPHAM_FK  "
				 +"\nleft JOIN DONVIDOLUONG dv on E.dvdl_fk = dv.pk_seq  "
				 +"\n  WHERE 1=1 and B.pk_seq in " + util.quyen_khott(obj.getUserId());
		}
		else
		{
		query = "  SELECT B.MA + ', ' + B.TEN AS KHOTEN, LSP.TEN AS LSP, isnull(D.DIENGIAI, '') AS khuvuc, isnull(C.TEN, '') AS vitri,  \n" + 
				"  	  e.MA AS SPMA, E.TEN AS SPTEN, dv.DonVi, A.SOLO, A.NGAYHETHAN, A.NGAYNHAPKHO,  \n" + 
				"  	  ISNULL(A.marq, '') as MARQ, ISNULL( A.HAMLUONG, '100' ) as hamluong, ISNULL(a.hamam, '0') as hamam, \n" + 
				"    	  isnull(mame, '') as mame, isnull( mathung, '') as mathung, isnull(maphieu, '') as maphieu, isnull(MAPHIEUDINHTINH, '') as phieudt, isnull(phieueo, '') as phieueo, \n" + 
				"    	  A.SOLUONG, A.BOOKED, A.AVAILABLE ,ISNULL(nsx.ma +' - '+nsx.ten,'') as NSXTEN ,\n" + 
				"		  isnull(DOITUONG.TEN,'') as tendoituong, isnull(DOITUONG.MA,0) as madoituong\n" + 
				"  FROM ERP_KHOTT_SP_CHITIET A  \n" + 
				"  	INNER JOIN ERP_KHOTT B ON A.KHOTT_FK = B.PK_SEQ  \n" + 
				"  	INNER JOIN ERP_KHOTT_SANPHAM KHO ON  KHO.KHOTT_FK = A.KHOTT_FK AND KHO.SANPHAM_FK=A.SANPHAM_FK  \n" + 
				"  	LEFT JOIN ERP_BIN C ON A.BIN_FK = C.PK_SEQ  \n" + 
				"	LEFT JOIN (\n" + 
				"		SELECT 7 AS LOAI, PK_SEQ,  MA  , TEN FROM ERP_NHACUNGCAP\n" + 
				"		UNION \n" + 
				"		SELECT 8 AS LOAI,PK_SEQ,  MA  , TEN FROM ERP_KHACHHANG\n" + 
				"		UNION \n" + 
				"		SELECT 9 AS LOAI,PK_SEQ, MA  , TEN FROM ERP_KHACHHANG\n" + 
				"		UNION \n" + 
				"		SELECT 13 AS LOAI,PK_SEQ, MA  , TEN FROM ERP_KHACHHANG\n" + 
				"		UNION \n" + 
				"		SELECT 77 AS LOAI,PK_SEQ, MA  , TEN FROM ERP_DONVITHUCHIEN\n" + 
				"		UNION \n" + 
				"		SELECT 14 AS LOAI,PK_SEQ, MA  , TEN FROM ERP_NHANVIEN\n" + 
				"		UNION \n" + 
				"		SELECT 10 AS LOAI,PK_SEQ, MA  , TEN FROM NHAPHANPHOI\n" + 
				"		UNION \n" + 
				"		SELECT 5 AS LOAI,PK_SEQ, cast(PK_SEQ  as nvarchar) , TEN_DT FROM ERP_LENHSANXUAT_CONGDOAN_GIAY\n" + 
				"	) DOITUONG ON A.loaidoituong =DOITUONG.LOAI AND A.doituongId = DOITUONG.PK_SEQ\n" + 
				"  	LEFT JOIN ERP_KHUVUCKHO D ON C.KHUVUC_FK = D.PK_SEQ  \n" + 
				"  	INNER JOIN ERP_SANPHAM E ON A.SANPHAM_FK = E.PK_SEQ  \n" + 
				"  	left JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = E.LOAISANPHAM_FK  \n" + 
				"  	left JOIN DONVIDOLUONG dv on E.dvdl_fk = dv.pk_seq  \n" + 
				"  	left join MARQUETTE MQ on A.IDMARQUETTE = MQ.PK_SEQ	 \n" + 
				" 	left join ERP_NHASANXUAT nsx on A.nsx_fk = nsx.pk_seq\n" + 
				"  WHERE 1=1 and B.pk_seq in " + util.quyen_khott(obj.getUserId());
		}
		
		if (obj.getKhoIds().length() > 0)
			query += "\n and a.KhoTT_fk = '" + obj.getKhoIds() + "' ";

		if (obj.getMaSanPhamIds().length() > 0)
			query += "\n and e.MA in (" + obj.getMaSanPhamIds() + ") ";

		if (obj.getLoaiSanPhamIds().length() > 0)
			query += "\n and e.loaisanpham_fk in(" + obj.getLoaiSanPhamIds() + ") ";

		if (obj.getSanPhamIds().length() > 0)
			query += "\n and e.pk_seq in (" + obj.getSanPhamIds() + ") ";

		if (obj.getFlag().equals("1"))
			query += "\n and a.soluong > 0 ";
		
		System.out.println("::: LAY BAO CAO: " + query);
		int index = 2;
		ResultSet rs = db.get(query);
		if (rs != null)
		{
			try
			{
				Cell cell = null;
				while (rs.next())
				{
					String kho = rs.getString("khoten");
					
					String khuvuc = rs.getString("khuvuc");
					String vitri = rs.getString("vitri");
					
					String loaisanpham = rs.getString("LSP");
					String ma = rs.getString("spma");
					String ten = rs.getString("spten");
					String donvi = rs.getString("donvi");
					
					String solo = rs.getString("solo");
					String ngayhethan = rs.getString("ngayhethan");
					String ngaynhapkho = rs.getString("ngaynhapkho");
					
					String mame = rs.getString("mame");
					//String mathung = rs.getString("mathung");
					
					String maphieu = rs.getString("maphieu");
					String phieuDT = rs.getString("phieudt");
					String phieuEO = rs.getString("phieuEO");
					
					String marq = rs.getString("marq");
					String hamam = rs.getString("hamam");
					String hamluong = rs.getString("hamluong");
					String NSXTEN = rs.getString("NSXTEN");
					double soluong = rs.getDouble("soluong");
					double booked = rs.getDouble("booked");
					double available = rs.getDouble("available");
					String madoituong = rs.getString("madoituong");
					String tendoituong = rs.getString("tendoituong");

					cell = cells.getCell("BA" + Integer.toString(index)); 	cell.setValue(kho);
					cell = cells.getCell("BB" + Integer.toString(index)); 	cell.setValue(khuvuc);
					cell = cells.getCell("BC" + Integer.toString(index)); 	cell.setValue(vitri);
					
					cell = cells.getCell("BD" + Integer.toString(index)); 	cell.setValue(loaisanpham);
					cell = cells.getCell("BE" + Integer.toString(index)); 	cell.setValue(ma);
					cell = cells.getCell("BF" + Integer.toString(index)); 	cell.setValue(ten);
					cell = cells.getCell("BG" + Integer.toString(index)); 	cell.setValue(donvi);
					
					cell = cells.getCell("BH" + Integer.toString(index)); 	cell.setValue(solo);
					cell = cells.getCell("BI" + Integer.toString(index)); 	cell.setValue(ngayhethan); 
					cell = cells.getCell("BJ" + Integer.toString(index)); 	cell.setValue(ngaynhapkho); 
					
					cell = cells.getCell("BK" + Integer.toString(index)); 	cell.setValue(mame);
					
					if(isNumeric(rs.getString("mathung"))){
					
					cell = cells.getCell("BL" + Integer.toString(index)); 	cell.setValue(rs.getDouble("mathung"));
					}
					
					else{
						cell = cells.getCell("BL" + Integer.toString(index)); 	cell.setValue(rs.getString("mathung"));
					}
					
					cell = cells.getCell("BM" + Integer.toString(index)); 	cell.setValue(maphieu); 
					cell = cells.getCell("BN" + Integer.toString(index)); 	cell.setValue(phieuDT); 
					cell = cells.getCell("BO" + Integer.toString(index)); 	cell.setValue(phieuEO); 
					
					cell = cells.getCell("BP" + Integer.toString(index)); 	cell.setValue(marq);
					cell = cells.getCell("BQ" + Integer.toString(index)); 	cell.setValue(hamam);
					cell = cells.getCell("BR" + Integer.toString(index)); 	cell.setValue(hamluong); 
					
					cell = cells.getCell("BS" + Integer.toString(index)); 	cell.setValue(soluong); 
					cell = cells.getCell("BT" + Integer.toString(index)); 	cell.setValue(booked); 
					cell = cells.getCell("BU" + Integer.toString(index)); 	cell.setValue(available); 
					cell = cells.getCell("BV" + Integer.toString(index)); 	cell.setValue(NSXTEN);
					cell = cells.getCell("BW" + Integer.toString(index)); 	cell.setValue(madoituong); 
					cell = cells.getCell("BX" + Integer.toString(index)); 	cell.setValue(tendoituong); 
					index++;
				}
				
				if (rs != null)
					rs.close();
				if (db != null)
					db.shutDown();
				if ( index <= 2 )
				{
					throw new Exception("Khong co bao cao trong thoi gian nay...");
				}
			} 
			catch (Exception e)
			{
				e.printStackTrace();
				throw new Exception(e.getMessage());
			}
		} 
		else
		{
			if (db != null)
				db.shutDown();
			return false;
		}
		
		return true;

	}
	
	public static boolean isNumeric(String str)  
	{  
	  try  
	  {  
	    double d = Double.parseDouble(str);  
	  }  
	  catch(NumberFormatException nfe)  
	  {  
	    return false;  
	  }  
	  return true;  
	}

}
