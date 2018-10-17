package geso.traphaco.erp.servlets.bcthekho;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.bcthekho.IBaocao;
import geso.traphaco.erp.beans.bcthekho.imp.Baocao;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.aspose.cells.Font;
import com.aspose.cells.HorizontalAlignmentType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class ErpBCThekhoSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpBCThekhoSvl() {
        super();
    }
    
    NumberFormat formatter = new DecimalFormat("#,###,###");
    NumberFormat formatter3 = new DecimalFormat("#,###,###.###");
    NumberFormat formatter2 = new DecimalFormat("#,###,###.00");
    
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
	    obj.createRsBCKHO();
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCTheKho.jsp";
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

		String userId = request.getParameter("userId");

		obj = new Baocao();
		obj.setUserId(userId);

		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTuNgay(tungay);

		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenNgay(denngay);


		String khoId = request.getParameter("khoId");
		if(khoId == null)
			khoId = "";
		obj.setKhoIds(khoId);

		String khoTen = request.getParameter("khoTen");
		if(khoTen == null)
			khoTen = "";
		obj.setKhoTen(khoTen);

		String check_laysolieucophatsinh = request.getParameter("check_laysolieucophatsinh");
		if(check_laysolieucophatsinh == null)
			check_laysolieucophatsinh = "";
		obj.setCheck_SpCoPhatSinh(check_laysolieucophatsinh);

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

		String dvkdId= request.getParameter("dvkdId");
		if(dvkdId==null){
			dvkdId="";
		}
		obj.setDvkdIds(dvkdId);

		String spId = request.getParameter("sanpham");
		if(spId==null){
			spId="";
		}
		obj.setspId(spId);

		String lspIds = request.getParameter("loaisanpham");
		if(lspIds==null){
			lspIds="";
		}	
		obj.setLoaiSanPhamIds(lspIds);

		String solo = request.getParameter("solo");
		if(solo == null)
			solo = "";
		obj.setSolo(solo);

		String action = request.getParameter("action");
		System.out.println("Action nhan duoc: " + action + " -- LOAI SP: " + obj.getLoaiSanPhamIds() );

		if(action.equals("search"))
		{
			obj.createRsBCKHO();
			session.setAttribute("obj", obj);

			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCTheKho.jsp";
			response.sendRedirect(nextJSP);
		}
		else if(action.equals("web"))
		{
	    	obj.createRsBCKHO();
	    	List<Double> tondau = new ArrayList<Double>();
	    	List<TheKhoObj> list = BaoCaoChiTietNXT( obj, tondau);
	    	
	    	session.setAttribute("ListTheKho", list);
	    	session.setAttribute("ListTheKho_check", "1");
	    	session.setAttribute("ListTheKho_tondau", tondau.get(0));
	    	session.setAttribute("obj", obj);
			
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCTheKho.jsp";
			response.sendRedirect(nextJSP);
	    }
		else
		{
			try 
			{	
				if(!(action.equals("view")))
				{
					if( action.equals("thugon")){
						response.setContentType("application/xlsm");
						response.setHeader("Content-Disposition", "attachment; filename=ErpBCTheKhoThuGon.xlsm");
						String query = "";
						System.out.println(" vao excel Thu Gon");
						ChiTietNXTThuGon(out, obj, query);
						obj.close();
					}
					else
					{
						response.setContentType("application/xlsm");
						response.setHeader("Content-Disposition", "attachment; filename=ErpBCTheKho.xlsm");
						String query = "";
						System.out.println(" vao excel");
						ChiTietNXT(out, obj, query);
						obj.close();
					}
				}
				else
				{
					obj.initview();
					obj.initview_BOOKED(khoId, spId, tungay, denngay);
					obj.createRsBCKHO();
					session.setAttribute("obj", obj);
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCTheKho.jsp";
					response.sendRedirect(nextJSP);
				}
			} 
			catch (Exception e) 
			{ 
				e.printStackTrace();
				System.out.println("Exception: " + e.getMessage()); 
			}
		}
	}
	
	private void ChiTietNXTThuGon(OutputStream out, IBaocao obj, String query) throws Exception
    {   
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ErpTheKhoThuGon.xlsm");

		workbook.open(fstream);
		System.out.println(" vao dc excel 2");
		BaoCaoChiTietNXTThuGon(workbook, obj );	     
 
	    workbook.save(out);
			
		fstream.close();
    }
	
	private void BaoCaoChiTietNXTThuGon(Workbook workbook, IBaocao obj ) 
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		worksheet.setName("Sheet1");

		Cells cells = worksheet.getCells();
		dbutils db = new dbutils(); 

		double soluongdau = 0;
		double dongiadau = 0;
		double thanhtiendau = 0;

		String condition = "";
		if( obj.getSolo().trim().length() > 0 )
			condition += " and solo = '" + obj.getSolo() + "' ";
		
		String query = "select ( select MA + ' - ' + TEN as TEN from ERP_SANPHAM where pk_seq = '" + obj.getspId() + "' ) tenSP, " +
					   "	   ( select ten from ERP_KHOTT where pk_seq = '" + obj.getKhoIds() + "' )  as tenKHO,	" +
					   "	  ISNULL( ( select sum(dauky) from dbo.ufn_thekho_chitiet_dauky( '" + obj.getTuNgay() + "', " + obj.getKhoIds() + ", " + obj.getspId() + " ) ), 0 ) as tondau		" ;
		System.out.println(":::: INFO TON DAU THU GON: " + query);
		ResultSet rsInfo = db.get(query);
		String tenSP = "";
		String tenKHO = "";
		if( rsInfo != null )
		{
			try 
			{
				if( rsInfo.next() )
				{
					tenSP = rsInfo.getString("tenSP");
					tenKHO = rsInfo.getString("tenKHO");
					soluongdau = rsInfo.getDouble("tondau");
				}
				rsInfo.close();
			} 
			catch (Exception e) { e.printStackTrace(); }
		}
		
		//System.out.println();
		Style style;
		Font font = new Font();
		font.setName("Times New Roman");
		font.setColor(Color.RED); //mau chu
		font.setSize(16);// size chu
		font.setBold(true);

		Cell cell = cells.getCell("A1");
		String tieude = "";
		
		cell = cells.getCell("A3");
		tieude = "Của " + tenSP;
		ReportAPI.getCellStyle(cell, Color.BLACK, false, 11, tieude);
		//cells.merge(5, 4, 0, 10);

		cell = cells.getCell("A4");
		tieude = "Từ ngày " + obj.getTuNgay() + " đến ngày " + obj.getDenNgay();
		ReportAPI.getCellStyle(cell, Color.BLACK, false, 11, tieude);
		//cells.merge(6, 4, 0, 10);
/*
		cell = cells.getCell("A6");
		tieude = "Của " + tenSP;
		ReportAPI.getCellStyle(cell, Color.BLACK, false, 11, tieude);
		//cells.merge(5, 4, 0, 10);

		cell = cells.getCell("A7");
		tieude = "Từ ngày " + obj.getTuNgay() + " đến ngày " + obj.getDenNgay();
		ReportAPI.getCellStyle(cell, Color.BLACK, false, 11, tieude);
		//cells.merge(6, 4, 0, 10);

		cell = cells.getCell("L9");
		tieude = "";
		ReportAPI.getCellStyle(cell, Color.BLACK, false, 10, tieude);
		//cells.merge(8, 11, 0, 2);

		cells.setRowHeight(10, 40.0f);*/

		//VE THEO TỪNG LÔ
		query = " select distinct isnull(solo, '') as solo " + 
				" from ERP_KHOTT_SP_CHITIET where khott_fk = '" + obj.getKhoIds() + "' and sanpham_fk = '" + obj.getspId() + "' ";
		
		System.out.println(":::: ALL LO: " + query);
		ResultSet rsLO = db.get(query);
		if( rsLO != null )
		{
			try 
			{
				int index = 8;
				double tonDauKy = 0.0;
				while( rsLO.next() )
				{
					soluongdau = 0;
					dongiadau = 0;
					thanhtiendau = 0;

					query = "  select sum( dauky ) as tondau from dbo.ufn_thekho_chitiet_dauky( '" + obj.getTuNgay() + "', " + obj.getKhoIds() + ", " + obj.getspId() + " ) " + 
							"  where solo = '" + rsLO.getString("solo") + "'		" ;
					System.out.println(":::: INFO TON DAU: " + query);
					rsInfo = db.get(query);
					if( rsInfo != null )
					{
						if( rsInfo.next() )
						{
							soluongdau = rsInfo.getDouble("tondau");
						}
						rsInfo.close();
					}
					tonDauKy += soluongdau;
				}
				rsLO.close();
					index++;
					
					
					
					query = "select soId, loaichungtu, machungtu, ngaychungtu, ngaychot,ngayhethan, diengiai,maphieu, solo, sum(nhap) as nhap, sum(xuat) as xuat " + 
							"from ufn_thekho_chitiet( '" + obj.getTuNgay() + "', '" + obj.getDenNgay() + "', '" + obj.getKhoIds() + "', '" + obj.getspId() + "' ) " ; 
							//" where solo = '" + rsLO.getString("solo") + "' ";
					
					query += " group by soId, loaichungtu, machungtu, ngaychungtu, ngaychot,ngayhethan, diengiai,maphieu, solo";
					query += " order by ngaychungtu asc ";
					System.out.println(":::: LAY BAO CAO THU GON: " + query);
					ResultSet  chiTietNXT = db.get(query);
					
					double totalSoluongN = 0;
					double totalThanhtienN = 0;
					double totalSoluongX = 0;
					double totalThanhtienX = 0;

					double tongluongDauKy = soluongdau;
					double thanhtienDauKy = thanhtiendau;
					
					double totalSoluongTON = soluongdau;
					double totalThanhtienTON = thanhtiendau;

					
					cell = cells.getCell("D8"); 	cell.setValue(obj.getTuNgay());
					this.setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,
							false);
					this.setCellBold(cell);
					cell = cells.getCell("E8" ); 	
					cell.setValue("Tồn đầu kỳ : "+ formatter2.format(tonDauKy));
					this.setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,
							false);
					this.setCellBold(cell);
					
					if(chiTietNXT != null)
					{
						while(chiTietNXT.next())
						{
							String stt = Integer.toString(index - 8);
							String soId = chiTietNXT.getString("soId");
							String machungtu = chiTietNXT.getString("machungtu");
							String loaichungtu = chiTietNXT.getString("loaichungtu");
							String ngaychungtu = chiTietNXT.getString("ngaychungtu");
							String ngaychot = chiTietNXT.getString("ngaychot");
							String diengiai = chiTietNXT.getString("diengiai");
							String maphieu = chiTietNXT.getString("maphieu");
							String handung = chiTietNXT.getString("ngayhethan");
							
							
							
							
							double dongia = 0;
							
							double nhap = chiTietNXT.getDouble("nhap");
							totalSoluongN += nhap;
							totalThanhtienN += 0;

							double xuat = chiTietNXT.getDouble("xuat");
							totalSoluongX += xuat;
							totalThanhtienX += 0;

							
							totalThanhtienTON += 0;

							cell = cells.getCell("A" + Integer.toString(index)); 	cell.setValue(soId);
							this.setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,
									false);
							cell = cells.getCell("B" + Integer.toString(index)); 	cell.setValue(machungtu);
							this.setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,
									false);
							cell = cells.getCell("C" + Integer.toString(index)); 	cell.setValue(loaichungtu);
							this.setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,
									false);
							cell = cells.getCell("D" + Integer.toString(index)); 	cell.setValue(ngaychungtu);
							this.setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,
									false);
							
							cell = cells.getCell("E" + Integer.toString(index)); 	cell.setValue(diengiai);
							this.setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,
									false);
							cell = cells.getCell("F" + Integer.toString(index)); 	cell.setValue(maphieu);
							this.setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,
									false);
							cell = cells.getCell("G" + Integer.toString(index)); 	cell.setValue(handung); 
							this.setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,
									false);
							cell = cells.getCell("H" + Integer.toString(index)); 	cell.setValue(nhap); 
							this.setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,
									false);
							cell = cells.getCell("I" + Integer.toString(index)); 	cell.setValue(xuat); 
							this.setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,
									false);
							tonDauKy = tonDauKy+nhap-xuat;
							cell = cells.getCell("J" + Integer.toString(index)); 	cell.setValue(tonDauKy); 
							this.setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,
									false);

							index++;
						}
						chiTietNXT.close();
						
						cell = cells.getCell("A" + Integer.toString(index)); 	cell.setValue("");
						this.setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,
								false);
						cell = cells.getCell("B" + Integer.toString(index)); 	cell.setValue("");
						this.setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,
								false);
						cell = cells.getCell("C" + Integer.toString(index)); 	cell.setValue("");
						this.setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,
								false);
						cell = cells.getCell("D"+Integer.toString(index)); 	cell.setValue(obj.getDenNgay());
						this.setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,
								false);
						this.setCellBold(cell);
						cell = cells.getCell("E"+Integer.toString(index) ); 	
						cell.setValue("Tồn cuối kỳ : "+ formatter2.format(tonDauKy));
						this.setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,
								false);
						this.setCellBold(cell);
						cell = cells.getCell("F" + Integer.toString(index)); 	cell.setValue("");
						this.setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,
								false);
						cell = cells.getCell("G" + Integer.toString(index)); 	cell.setValue(""); 
						this.setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,
								false);
						cell = cells.getCell("H"+Integer.toString(index)); 	cell.setValue(totalSoluongN);
						this.setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,
								false);
						this.setCellBold(cell);
						cell = cells.getCell("I"+Integer.toString(index)); 	cell.setValue(totalSoluongX);
						this.setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,
								false);
						this.setCellBold(cell);
						cell = cells.getCell("J"+Integer.toString(index)); 	cell.setValue(tonDauKy);
						this.setCellBorderStyle(cell, HorizontalAlignmentType.LEFT,
								false);
						this.setCellBold(cell);
					}
					
					
/*
					cell = cells.getCell("A" + Integer.toString(index)); 
					ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Tổng cộng ( " + rsLO.getString("SOLO") + " )");
					
					cell = cells.getCell("R" + Integer.toString(index)); 	cell.setValue(totalSoluongN);
					cell = cells.getCell("S" + Integer.toString(index));    cell.setValue(0);
					cell = cells.getCell("T" + Integer.toString(index)); 	cell.setValue(totalSoluongN * 0);

					cell = cells.getCell("U" + Integer.toString(index)); 	cell.setValue(totalSoluongX);
					cell = cells.getCell("V" + Integer.toString(index));    cell.setValue(0);
					cell = cells.getCell("W" + Integer.toString(index)); 	cell.setValue(totalSoluongX * 0);

					cell = cells.getCell("X" + Integer.toString(index)); 	cell.setValue(totalSoluongTON);
					cell = cells.getCell("Y" + Integer.toString(index)); 	cell.setValue(0);
					cell = cells.getCell("Z" + Integer.toString(index)); 	cell.setValue(totalSoluongTON * 0);*/
					
					//index ++;
				
				
				
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		

	}
	
	
	private void ChiTietNXT(OutputStream out, IBaocao obj, String query) throws Exception
    {   
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ErpTheKho.xlsm");

		workbook.open(fstream);
		System.out.println(" vao dc excel 2");
	    BaoCaoChiTietNXT(workbook, obj );	     
 
	    workbook.save(out);
			
		fstream.close();
   }
	
	private List<TheKhoObj> BaoCaoChiTietNXT(IBaocao obj, List<Double> tondauR) 
	{
		dbutils db = new dbutils(); 
		
		List<TheKhoObj> list = new ArrayList<TheKhoObj>();
		double soluongdau = 0;
		double dongiadau = 0;
		double thanhtiendau = 0;
		String condition = "";
		if( obj.getSolo().trim().length() > 0 )
			condition += " and solo = '" + obj.getSolo() + "' ";
		
		String query = "select ( select MA + ' - ' + TEN as TEN from ERP_SANPHAM where pk_seq = '" + obj.getspId() + "' ) tenSP, " +
					   "	   ( select ten from ERP_KHOTT where pk_seq = '" + obj.getKhoIds() + "' )  as tenKHO,	" +
					   "	  ISNULL( ( select sum(dauky) from dbo.ufn_thekho_chitiet_dauky( '" + obj.getTuNgay() + "', " + obj.getKhoIds() + ", " + obj.getspId() + " ) ), 0 ) as tondau		" ;
		
		System.out.println(":::: INFO TON DAU: " + query);
		ResultSet rsInfo = db.get(query);
		String tenSP = "";
		String tenKHO = "";
		if( rsInfo != null )
		{
			try 
			{
				if( rsInfo.next() )
				{
					tenSP = rsInfo.getString("tenSP");
					tenKHO = rsInfo.getString("tenKHO");
					soluongdau = rsInfo.getDouble("tondau");
				}
				rsInfo.close();
			} 
			catch (Exception e) { e.printStackTrace();  }
		}
		// ghi nhan ton dau ra web
		tondauR.add(soluongdau);

		query =   " select loaichungtu, machungtu, ngaychungtu, ngaychot, solo, ngayhethan,ngaynhapkho, mame, mathung, "
				+ " vitri, maphieu, phieudt, phieuEO, marq, hamam, hamluong, sum(nhap) as nhap, sum(xuat) as xuat,NSX " + 
				  " from ufn_thekho_chitiet( '" + obj.getTuNgay() + "', '" + obj.getDenNgay() + "', '" 
				  + obj.getKhoIds() + "', '" + obj.getspId() + "' ) " + 
				  " where 1 = 1 " + condition;
		
		query += " group by loaichungtu, machungtu, ngaychungtu, ngaychot, solo, ngayhethan,ngaynhapkho, mame, mathung, vitri, maphieu,"
			  + " phieudt, phieuEO, marq, hamam, hamluong,NSX  ";
		
		query += " order by ngaychungtu, NGAYHETHAN, SOLO, MAPHIEU, dbo.ftConvertToNumber(MAME), dbo.ftConvertToNumber(MATHUNG) ";
		System.out.println(":::: LAY BAO CAO: " + query);
		ResultSet  chiTietNXT = db.get(query);
		
		double totalSoluongN = 0;
		double totalThanhtienN = 0;
		double totalSoluongX = 0;
		double totalThanhtienX = 0;

		double tongluongDauKy = soluongdau;
		double thanhtienDauKy = thanhtiendau;
		
		double totalSoluongTON = soluongdau;
		double totalThanhtienTON = thanhtiendau;

		try 
		{
			int index = 13;
			if(chiTietNXT != null)
			{
				while(chiTietNXT.next())
				{
					TheKhoObj temp = new TheKhoObj();
					
					String machungtu = chiTietNXT.getString("machungtu");
					String loaichungtu = chiTietNXT.getString("loaichungtu");
					String ngaychungtu = chiTietNXT.getString("ngaychungtu");
					String ngaychot = chiTietNXT.getString("ngaychot");
					
					String solo = chiTietNXT.getString("solo");
					String ngayhethan = chiTietNXT.getString("ngayhethan");
					String ngaynhapkho = chiTietNXT.getString("ngaynhapkho");
					String mame = chiTietNXT.getString("mame");
					String mathung = chiTietNXT.getString("mathung");
					String vitri = chiTietNXT.getString("vitri");
					
					String maphieu = chiTietNXT.getString("maphieu");
					String phieuDT = chiTietNXT.getString("phieudt");
					String phieuEO = chiTietNXT.getString("phieuEO");
					
					String marq = chiTietNXT.getString("marq");
					String hamam = chiTietNXT.getString("hamam");
					String hamluong = chiTietNXT.getString("hamluong");
					String NSX = chiTietNXT.getString("NSX");
					
					double dongia = 0;
					double nhap = chiTietNXT.getDouble("nhap");
					double xuat = chiTietNXT.getDouble("xuat");
					totalSoluongTON += ( nhap - xuat );
					
					temp.setMachungtu(machungtu);
					temp.setLoaichungtu(loaichungtu);
					temp.setNgaychungtu(ngaychungtu);
					temp.setNgaychot(ngaychot);
					temp.setSolo(solo);
					temp.setNgayhethan(ngayhethan);
					temp.setNgaynhapkho(ngaynhapkho);
					temp.setMathung(mathung);
					temp.setMame(mame);
					temp.setVitri(vitri);
					temp.setMaphieu(maphieu);
					temp.setPhieuDT(phieuDT);
					temp.setPhieuEO(phieuEO);
					temp.setMarq(marq);
					temp.setHamam(hamam);
					temp.setHamluong(hamluong);
					temp.setNhap(nhap);
					temp.setXuat(xuat);
					temp.setTon(totalSoluongTON);
					temp.setNsx(NSX);
					list.add(temp);
					index++;
				}
				chiTietNXT.close();
			}
			return list;
		}
		catch (Exception e) 
		{ 
			e.printStackTrace();
			return null;
		} finally{
			db.shutDown();
		}
	}
	

	private void BaoCaoChiTietNXT(Workbook workbook, IBaocao obj ) 
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		worksheet.setName("Sheet1");

		Cells cells = worksheet.getCells();
		dbutils db = new dbutils(); 

		String condition = "";
		if( obj.getSolo().trim().length() > 0 )
			condition += " and solo = '" + obj.getSolo() + "' ";
		
		/*String query = "select ( select MA + ' - ' + TEN as TEN from ERP_SANPHAM where pk_seq = '" + obj.getspId() + "' ) tenSP, " +
					   "	   ( select ten from ERP_KHOTT where pk_seq = '" + obj.getKhoIds() + "' )  as tenKHO,	" +
					   "	  ISNULL( ( select sum(dauky) from dbo.ufn_thekho_chitiet_dauky( '" + obj.getTuNgay() + "', " + obj.getKhoIds() + ", " + obj.getspId() + " ) where 1 = 1 " + condition + " ), 0 ) as tondau		" ;*/
		
		String query = "select ( select MA + ' - ' + TEN as TEN from ERP_SANPHAM where pk_seq = '" + obj.getspId() + "' ) tenSP, " +
				   "	   ( select ten from ERP_KHOTT where pk_seq = '" + obj.getKhoIds() + "' )  as tenKHO,	" +
				   "	  0 as tondau		" ;
		
		System.out.println(":::: INFO TON DAU: " + query);
		ResultSet rsInfo = db.get(query);
		String tenSP = "";
		String tenKHO = "";
		if( rsInfo != null )
		{
			try 
			{
				if( rsInfo.next() )
				{
					tenSP = rsInfo.getString("tenSP");
					tenKHO = rsInfo.getString("tenKHO");
					//soluongdau = rsInfo.getDouble("tondau");
				}
				rsInfo.close();
			} 
			catch (Exception e) { e.printStackTrace(); }
		}
		
		//System.out.println();
		Style style;
		Font font = new Font();
		font.setName("Times New Roman");
		font.setColor(Color.RED); //mau chu
		font.setSize(16);// size chu
		font.setBold(true);

		Cell cell = cells.getCell("A1");
		style = cell.getStyle();
		style.setFont(font); 
		style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu 	  

		String tieude = "Tên DN: Tổng công ty Traphaco";
		ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		cells.merge(0, 0, 0, 8);

		cell = cells.getCell("A2");
		tieude = "Địa chỉ: 75 Phố Yên Ninh, Phường Quán Thánh, Quận Ba Đình, TP Hà Nội, Việt Nam";
		ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		cells.merge(1, 0, 0, 8);

		cell = cells.getCell("A3");
		tieude = "MST: 0 1 0 0 1 0 8 6 5 6 ";
		ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		cells.merge(2, 0, 0, 8);

		cell = cells.getCell("A4");
		tieude = "Kho: " + tenKHO;
		ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		cells.merge(2, 0, 0, 8);

		cells.setRowHeight(4, 30.0f);
		cell = cells.getCell("A5");
		tieude = "BÁO CÁO THẺ KHO";
		ReportAPI.getCellStyle(cell, Color.BLACK, true, 18, tieude);
		//cells.merge(4, 3, 0, 10);

		cell = cells.getCell("A6");
		tieude = "Của " + tenSP;
		ReportAPI.getCellStyle(cell, Color.BLACK, false, 11, tieude);
		//cells.merge(5, 4, 0, 10);

		cell = cells.getCell("A7");
		tieude = "Từ ngày " + obj.getTuNgay() + " đến ngày " + obj.getDenNgay();
		ReportAPI.getCellStyle(cell, Color.BLACK, false, 11, tieude);
		//cells.merge(6, 4, 0, 10);

		cell = cells.getCell("L9");
		tieude = "";
		ReportAPI.getCellStyle(cell, Color.BLACK, false, 10, tieude);
		//cells.merge(8, 11, 0, 2);

		cells.setRowHeight(10, 40.0f);

		//VE THEO TỪNG LÔ ==> sử dụng bảng TEMP để select lại nhiều lần
		query = "select * into #TEMP_THEKHO   "+
				"from "+
				"( "+
				"	select N'Đầu kỳ' loaichungtu, '' machungtu, '' ngaychungtu, '' ngaychot, '' diengiai, solo, '' ngayhethan,'' ngaynhapkho, '' mame, '' mathung,  "+
				"		'' vitri, '' maphieu, '' phieudt, '' phieuEO, '' marq, '' hamam, '' hamluong, sum( dauky ) as nhap, 0 as xuat, 1 as stt,'' NSX  "+
				"	from dbo.ufn_thekho_chitiet_dauky( '" + obj.getTuNgay() + "', '" + obj.getKhoIds() + "', '" + obj.getspId() + "' )   "+
				"	where 1 = 1	" + condition +
				"	group by solo "+
				"union all "+
				"	select loaichungtu, machungtu, ngaychungtu, ngaychot, diengiai, solo, ngayhethan,ngaynhapkho, mame, mathung, isnull(vitri, ''), maphieu, phieudt, phieuEO, marq, hamam, hamluong, sum(nhap) as nhap, sum(xuat) as xuat, 2 as stt,NSX  "+
				"	from ufn_thekho_chitiet( '" + obj.getTuNgay() + "', '" + obj.getDenNgay() + "', '" + obj.getKhoIds() + "', '" + obj.getspId() + "' )   "+
				"	where 1 = 1	" + condition +
				"	group by loaichungtu, machungtu, ngaychungtu, ngaychot, diengiai, solo, ngayhethan,ngaynhapkho, mame, mathung, isnull(vitri, ''), maphieu, phieudt, phieuEO, marq, hamam, hamluong,NSX"+
				") "+
				"DT  "+
				"select * from "+
				"( "+
				"	select *  "+
				"	from #TEMP_THEKHO " + 
				" 	where solo in ( select solo from #TEMP_THEKHO where nhap != 0 or xuat != 0   )  "+
				"union all "+  //Những lô không có đầu kỳ, nhưng có nhập xuất, thì khởi tạo đầu kỳ = 0 để vẽ báo cáo
				"	select distinct N'Đầu kỳ' loaichungtu, '' machungtu, '' ngaychungtu, '' ngaychot, '' diengiai, solo, '' ngayhethan,'' ngaynhapkho, '' mame, '' mathung,  "+
				"			'' vitri, '' maphieu, '' phieudt, '' phieuEO, '' marq, '' hamam, '' hamluong, 0 as nhap, 0 as xuat, 1 as stt,'' NSX   "+
				"	from #TEMP_THEKHO where loaichungtu != N'Đầu kỳ' and solo not in ( select solo from #TEMP_THEKHO where loaichungtu = N'Đầu kỳ' ) "+
				") "+
				"DT2 order by SOLO asc, stt asc, ngaychungtu asc, nhap desc  ";
		
		System.out.println(":::: THE KHO ALL LO: " + query);
		ResultSet rsLO = db.get(query);
		if( rsLO != null )
		{
			try 
			{
				int index = 11;
				int stt = 1;
				double soluongdau = 0;
				double totalSoluongN = 0;
				double totalSoluongX = 0;
				double totalSoluongTON = 0;
				String soloBK = "";
				
				while( rsLO.next() )
				{
					if( rsLO.getString("loaichungtu").equals("Đầu kỳ") )
					{
						//Nếu không phải dòng đầu tiên, thì vẽ TOTAL chỗ này trước
						if( stt > 1 )
						{
							cell = cells.getCell("A" + Integer.toString(index)); 
							ReportAPI.getCellStyle(cell, Color.RED, true, 10, "Tồn cuối kỳ: ");
							
							cell = cells.getCell("G" + index); ReportAPI.getCellStyle(cell, Color.RED, true, 10, soloBK);
							
							cell = cells.getCell("T" + Integer.toString(index));	cell.setValue(totalSoluongN);
							cell = cells.getCell("U" + Integer.toString(index)); 	cell.setValue(totalSoluongX);
							cell = cells.getCell("V" + Integer.toString(index)); 	cell.setValue(totalSoluongTON);
							
							index ++;
						}
						
						//Vẽ dòng đầu kỳ
						soluongdau = rsLO.getDouble("nhap");
						soloBK = rsLO.getString("solo");
						totalSoluongN = 0;
						totalSoluongX = 0;
						totalSoluongTON = rsLO.getDouble("nhap");

						cell = cells.getCell("A" + index);
						tieude = "Tồn đầu kỳ: ";
						ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);

						cell = cells.getCell("G" + index);
						ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, rsLO.getString("solo"));
						
						cell = cells.getCell("V" + index);
						cell.setValue(soluongdau);

						index ++;
					}
					else  //Vẽ dòng nhập xuất
					{
						String machungtu = rsLO.getString("machungtu");
						String loaichungtu = rsLO.getString("loaichungtu");
						String ngaychungtu = rsLO.getString("ngaychungtu");
						String ngaychot = rsLO.getString("ngaychot");
						String diengiai = rsLO.getString("diengiai");
						
						String solo = rsLO.getString("solo");
						String ngayhethan = rsLO.getString("ngayhethan");
						String ngaynhapkho = rsLO.getString("ngaynhapkho");
						String mame = rsLO.getString("mame");
						String mathung = rsLO.getString("mathung");
						String vitri = rsLO.getString("vitri");
						
						String maphieu = rsLO.getString("maphieu");
						String phieuDT = rsLO.getString("phieudt");
						String phieuEO = rsLO.getString("phieuEO");
						
						String marq = rsLO.getString("marq");
						String hamam = rsLO.getString("hamam");
						String hamluong = rsLO.getString("hamluong");
						String NSX = rsLO.getString("NSX");
						double nhap = rsLO.getDouble("nhap");
						totalSoluongN += nhap;

						double xuat = rsLO.getDouble("xuat");
						totalSoluongX += xuat;

						totalSoluongTON += ( nhap - xuat );

						cell = cells.getCell("A" + Integer.toString(index)); 	cell.setValue( stt++ );
						
						cell = cells.getCell("B" + Integer.toString(index)); 	cell.setValue(machungtu);
						cell = cells.getCell("C" + Integer.toString(index)); 	cell.setValue(loaichungtu);
						cell = cells.getCell("D" + Integer.toString(index)); 	cell.setValue(ngaychungtu);
						cell = cells.getCell("E" + Integer.toString(index)); 	cell.setValue(ngaychot);
						cell = cells.getCell("F" + Integer.toString(index)); 	cell.setValue(diengiai);
						
						cell = cells.getCell("G" + Integer.toString(index)); 	cell.setValue(solo);
						cell = cells.getCell("H" + Integer.toString(index)); 	cell.setValue(ngayhethan); 
						cell = cells.getCell("I" + Integer.toString(index)); 	cell.setValue(ngaynhapkho); 
						cell = cells.getCell("J" + Integer.toString(index)); 	cell.setValue(mame);
						cell = cells.getCell("K" + Integer.toString(index)); 	cell.setValue(mathung);
						cell = cells.getCell("L" + Integer.toString(index)); 	cell.setValue(vitri);
						
						cell = cells.getCell("M" + Integer.toString(index)); 	cell.setValue(maphieu); 
						cell = cells.getCell("N" + Integer.toString(index)); 	cell.setValue(phieuDT); 
						cell = cells.getCell("O" + Integer.toString(index)); 	cell.setValue(phieuEO);
						
						cell = cells.getCell("P" + Integer.toString(index)); 	cell.setValue(marq); 
						cell = cells.getCell("Q" + Integer.toString(index)); 	cell.setValue(hamam);
						cell = cells.getCell("R" + Integer.toString(index)); 	cell.setValue(hamluong); 
						cell = cells.getCell("S" + Integer.toString(index)); 	cell.setValue(NSX); 
						cell = cells.getCell("T" + Integer.toString(index)); 	cell.setValue(nhap); 
						cell = cells.getCell("U" + Integer.toString(index)); 	cell.setValue(xuat); 
						cell = cells.getCell("V" + Integer.toString(index)); 	cell.setValue(totalSoluongTON); 

						index++;
					}	
				}
				rsLO.close();
				
				if( stt > 1 )
				{
					//TOTAL DÒNG CUỐI CÙNG
					cell = cells.getCell("A" + Integer.toString(index)); 
					ReportAPI.getCellStyle(cell, Color.RED, true, 10, "Tồn cuối kỳ: ");
					
					cell = cells.getCell("G" + index); ReportAPI.getCellStyle(cell, Color.RED, true, 10, soloBK);
					
					cell = cells.getCell("T" + Integer.toString(index)); 	cell.setValue(totalSoluongN);
					cell = cells.getCell("U" + Integer.toString(index)); 	cell.setValue(totalSoluongX);
					cell = cells.getCell("V" + Integer.toString(index)); 	cell.setValue(totalSoluongTON);
				}
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		db.shutDown();
	}
	
	//KIỂU NÀY CHẠY QUÁ CHẬM
	private void BaoCaoChiTietNXT_BK(Workbook workbook, IBaocao obj ) 
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		worksheet.setName("Sheet1");

		Cells cells = worksheet.getCells();
		dbutils db = new dbutils(); 

		//double soluongdau = 0;

		String condition = "";
		if( obj.getSolo().trim().length() > 0 )
			condition += " and solo = '" + obj.getSolo() + "' ";
		
		/*String query = "select ( select MA + ' - ' + TEN as TEN from ERP_SANPHAM where pk_seq = '" + obj.getspId() + "' ) tenSP, " +
					   "	   ( select ten from ERP_KHOTT where pk_seq = '" + obj.getKhoIds() + "' )  as tenKHO,	" +
					   "	  ISNULL( ( select sum(dauky) from dbo.ufn_thekho_chitiet_dauky( '" + obj.getTuNgay() + "', " + obj.getKhoIds() + ", " + obj.getspId() + " ) where 1 = 1 " + condition + " ), 0 ) as tondau		" ;*/
		
		String query = "select ( select MA + ' - ' + TEN as TEN from ERP_SANPHAM where pk_seq = '" + obj.getspId() + "' ) tenSP, " +
				   "	   ( select ten from ERP_KHOTT where pk_seq = '" + obj.getKhoIds() + "' )  as tenKHO,	" +
				   "	  0 as tondau		" ;
		
		System.out.println(":::: INFO TON DAU: " + query);
		ResultSet rsInfo = db.get(query);
		String tenSP = "";
		String tenKHO = "";
		if( rsInfo != null )
		{
			try 
			{
				if( rsInfo.next() )
				{
					tenSP = rsInfo.getString("tenSP");
					tenKHO = rsInfo.getString("tenKHO");
					//soluongdau = rsInfo.getDouble("tondau");
				}
				rsInfo.close();
			} 
			catch (Exception e) { e.printStackTrace(); }
		}
		
		//System.out.println();
		Style style;
		Font font = new Font();
		font.setName("Times New Roman");
		font.setColor(Color.RED); //mau chu
		font.setSize(16);// size chu
		font.setBold(true);

		Cell cell = cells.getCell("A1");
		style = cell.getStyle();
		style.setFont(font); 
		style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu 	  

		String tieude = "Tên DN: Tổng công ty Traphaco";
		ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		cells.merge(0, 0, 0, 8);

		cell = cells.getCell("A2");
		tieude = "Địa chỉ: 75 Phố Yên Ninh, Phường Quán Thánh, Quận Ba Đình, TP Hà Nội, Việt Nam";
		ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		cells.merge(1, 0, 0, 8);

		cell = cells.getCell("A3");
		tieude = "MST: 0 1 0 0 1 0 8 6 5 6 ";
		ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		cells.merge(2, 0, 0, 8);

		cell = cells.getCell("A4");
		tieude = "Kho: " + tenKHO;
		ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
		cells.merge(2, 0, 0, 8);

		cells.setRowHeight(4, 30.0f);
		cell = cells.getCell("A5");
		tieude = "BÁO CÁO THẺ KHO";
		ReportAPI.getCellStyle(cell, Color.BLACK, true, 18, tieude);
		//cells.merge(4, 3, 0, 10);

		cell = cells.getCell("A6");
		tieude = "Của " + tenSP;
		ReportAPI.getCellStyle(cell, Color.BLACK, false, 11, tieude);
		//cells.merge(5, 4, 0, 10);

		cell = cells.getCell("A7");
		tieude = "Từ ngày " + obj.getTuNgay() + " đến ngày " + obj.getDenNgay();
		ReportAPI.getCellStyle(cell, Color.BLACK, false, 11, tieude);
		//cells.merge(6, 4, 0, 10);

		cell = cells.getCell("L9");
		tieude = "";
		ReportAPI.getCellStyle(cell, Color.BLACK, false, 10, tieude);
		//cells.merge(8, 11, 0, 2);

		cells.setRowHeight(10, 40.0f);

		//VE THEO TỪNG LÔ
		query = " select distinct isnull(solo, '') as solo " + 
				" from ERP_KHOTT_SP_CHITIET where khott_fk = '" + obj.getKhoIds() + "' and sanpham_fk = '" + obj.getspId() + "' " + condition;
		
		System.out.println(":::: ALL LO: " + query);
		ResultSet rsLO = db.get(query);
		if( rsLO != null )
		{
			try 
			{
				int index = 11;
				while( rsLO.next() )
				{
					double soluongdau = 0;

					query = "  select sum( dauky ) as tondau from dbo.ufn_thekho_chitiet_dauky_solo( '" + obj.getTuNgay() + "', " + obj.getKhoIds() + ", " + obj.getspId() + ", '" + rsLO.getString("solo") + "' ) " + 
							"  where 1 = 1  " ;
					System.out.println(":::: INFO TON DAU: " + query);
					rsInfo = db.get(query);
					if( rsInfo != null )
					{
						if( rsInfo.next() )
						{
							soluongdau = rsInfo.getDouble("tondau");
						}
						rsInfo.close();
					}
					
					cell = cells.getCell("A" + index);
					tieude = "Tồn đầu kỳ: ";
					ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);

					cell = cells.getCell("G" + index);
					ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, rsLO.getString("solo"));
					
					cell = cells.getCell("T" + index);
					cell.setValue(soluongdau);

					index ++;
					
					query = "select loaichungtu, machungtu, ngaychungtu, ngaychot, diengiai, solo, ngayhethan, mame, mathung, vitri, maphieu, phieudt, phieuEO, marq, hamam, hamluong, sum(nhap) as nhap, sum(xuat) as xuat " + 
							"from ufn_thekho_chitiet_solo( '" + obj.getTuNgay() + "', '" + obj.getDenNgay() + "', '" + obj.getKhoIds() + "', '" + obj.getspId() + "', '" + rsLO.getString("solo") + "' ) " + 
							" where 1 = 1 ";
					
					query += " group by loaichungtu, machungtu, ngaychungtu, ngaychot, diengiai, solo, ngayhethan, mame, mathung, vitri, maphieu, phieudt, phieuEO, marq, hamam, hamluong ";
					query += " order by ngaychungtu asc ";
					System.out.println(":::: LAY BAO CAO: " + query);
					ResultSet  chiTietNXT = db.get(query);
					
					double totalSoluongN = 0;
					double totalSoluongX = 0;
					//double tongluongDauKy = soluongdau;
					double totalSoluongTON = soluongdau;

					boolean coNHAPXUAT = false;
					if(chiTietNXT != null)
					{
						while(chiTietNXT.next())
						{
							String stt = Integer.toString(index - 12);
							String machungtu = chiTietNXT.getString("machungtu");
							String loaichungtu = chiTietNXT.getString("loaichungtu");
							String ngaychungtu = chiTietNXT.getString("ngaychungtu");
							String ngaychot = chiTietNXT.getString("ngaychot");
							String diengiai = chiTietNXT.getString("diengiai");
							
							String solo = chiTietNXT.getString("solo");
							String ngayhethan = chiTietNXT.getString("ngayhethan");
							
							String mame = chiTietNXT.getString("mame");
							String mathung = chiTietNXT.getString("mathung");
							String vitri = chiTietNXT.getString("vitri");
							
							String maphieu = chiTietNXT.getString("maphieu");
							String phieuDT = chiTietNXT.getString("phieudt");
							String phieuEO = chiTietNXT.getString("phieuEO");
							
							String marq = chiTietNXT.getString("marq");
							String hamam = chiTietNXT.getString("hamam");
							String hamluong = chiTietNXT.getString("hamluong");
							
							double nhap = chiTietNXT.getDouble("nhap");
							totalSoluongN += nhap;

							double xuat = chiTietNXT.getDouble("xuat");
							totalSoluongX += xuat;

							totalSoluongTON += ( nhap - xuat );

							cell = cells.getCell("A" + Integer.toString(index)); 	cell.setValue(stt);
							
							cell = cells.getCell("B" + Integer.toString(index)); 	cell.setValue(machungtu);
							cell = cells.getCell("C" + Integer.toString(index)); 	cell.setValue(loaichungtu);
							cell = cells.getCell("D" + Integer.toString(index)); 	cell.setValue(ngaychungtu);
							cell = cells.getCell("E" + Integer.toString(index)); 	cell.setValue(ngaychot);
							cell = cells.getCell("F" + Integer.toString(index)); 	cell.setValue(diengiai);
							
							cell = cells.getCell("G" + Integer.toString(index)); 	cell.setValue(solo);
							cell = cells.getCell("H" + Integer.toString(index)); 	cell.setValue(ngayhethan); 
							
							cell = cells.getCell("I" + Integer.toString(index)); 	cell.setValue(mame);
							cell = cells.getCell("J" + Integer.toString(index)); 	cell.setValue(mathung);
							cell = cells.getCell("K" + Integer.toString(index)); 	cell.setValue(vitri);
							
							cell = cells.getCell("L" + Integer.toString(index)); 	cell.setValue(maphieu); 
							cell = cells.getCell("M" + Integer.toString(index)); 	cell.setValue(phieuDT); 
							cell = cells.getCell("N" + Integer.toString(index)); 	cell.setValue(phieuEO);
							
							cell = cells.getCell("O" + Integer.toString(index)); 	cell.setValue(marq); 
							cell = cells.getCell("P" + Integer.toString(index)); 	cell.setValue(hamam);
							cell = cells.getCell("Q" + Integer.toString(index)); 	cell.setValue(hamluong); 
							
							cell = cells.getCell("R" + Integer.toString(index)); 	cell.setValue(nhap); 
							cell = cells.getCell("S" + Integer.toString(index)); 	cell.setValue(xuat); 
							cell = cells.getCell("T" + Integer.toString(index)); 	cell.setValue(totalSoluongTON); 

							index++;
							coNHAPXUAT = true;
						}
						chiTietNXT.close();
					}

					if( coNHAPXUAT )
					{
						cell = cells.getCell("A" + Integer.toString(index)); 
						ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Tồn cuối kỳ: ");
						
						cell = cells.getCell("G" + index); ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, rsLO.getString("solo"));
						
						cell = cells.getCell("R" + Integer.toString(index)); 	cell.setValue(totalSoluongN);
						cell = cells.getCell("S" + Integer.toString(index)); 	cell.setValue(totalSoluongX);
						cell = cells.getCell("T" + Integer.toString(index)); 	cell.setValue(totalSoluongTON);
						
						index ++;
					}
				}
				rsLO.close();
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

	}
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	private void setCellBold(Cell cell){
		Style style = cell.getStyle();
		com.aspose.cells.Font font = new com.aspose.cells.Font();
		font.setBold(true);
		style.setFont(font);
		cell.setStyle(style);
	}
	private void setCellBorderStyle(Cell cell, short align, boolean kt) {
		Style style = cell.getStyle();
		style.setHAlignment(align);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		style.setTextWrapped(true);
		if (kt) {
			com.aspose.cells.Font font2 = new com.aspose.cells.Font();
			font2.setName("Calibri");
			font2.setColor(Color.BLACK);
			font2.setSize(11);
			style.setFont(font2);
			style.setColor(Color.SILVER);
		} else
			style.setColor(Color.WHITE);

		cell.setStyle(style);
	}
 
}
