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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class ErpXuatnhaptonTTSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpXuatnhaptonTTSvl() {
        super();
    } 
    
    NumberFormat formatter = new DecimalFormat("#,###,###");
    NumberFormat formatter2 = new DecimalFormat("#,###,###.#######");
    
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
	    obj.setXemtheolo("3"); //mac dinh la bao cao theo hoa don
	    obj.createRsBCKHO();
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCNhapXuatTon.jsp";
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
	    
	    String doituongId = request.getParameter("doituongId");
	    if(doituongId == null)
	    	doituongId = "";
	    obj.setDoituongId(doituongId);
	    
	    String check_laysolieucophatsinh = request.getParameter("check_laysolieucophatsinh");
	    if(check_laysolieucophatsinh == null)
	    	check_laysolieucophatsinh = "";
	    obj.setCheck_SpCoPhatSinh(check_laysolieucophatsinh);
	    
	    String[] lspIds = request.getParameterValues("loaisanpham");
	    String lspId = "";
	    if(lspIds != null)
	    {
	    	for(int i = 0; i < lspIds.length; i++)
	    		lspId += lspIds[i] + ",";
	    	if(lspId.length() > 0)
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

		String[] dvkdIds = request.getParameterValues("dvkdIds");
		String dvkdId = "";
		if (dvkdIds != null)
		{
			for (int i = 0; i < dvkdIds.length; i++)
				dvkdId +=  dvkdIds[i] + ",";
			if (dvkdId.length() > 0)
				dvkdId = dvkdId.substring(0, dvkdId.length() - 1);
			obj.setDvkdIds(dvkdId);
		}
		
	    String[] spIds = request.getParameterValues("spIds");
	    String spId = "";
	    if(spIds != null)
	    {
	    	for(int i = 0; i < spIds.length; i++)
	    		spId += spIds[i] + ",";
	    	if(spId.length() > 0)
	    		spId = spId.substring(0, spId.length() - 1);
	    	obj.setSanPhamIds(spId);
	    }
	    
	    String dinhdang = request.getParameter("dinhdang");
	    if(dinhdang == null)
	    	dinhdang = "0";
	    obj.setXemtheolo(dinhdang);
	    
	    String action = request.getParameter("action");
	    //System.out.println("Action nhan duoc: " + action + " -- LOAI SP: " + obj.getLoaiSanPhamIds() );
	    
	    if(action.equals("search"))
	    {
	    	obj.createRsBCKHO();
	    	session.setAttribute("obj", obj);
			
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCNhapXuatTon.jsp";
			response.sendRedirect(nextJSP);
	    } 
	    else if(action.equals("web"))
	    {
	    	obj.createRsBCKHO();
	    	List<XuatNhapTonObj> list = BaoCaoTongHopNXT(obj);
	    	
	    	session.setAttribute("ListXNT", list);
	    	session.setAttribute("ListXNT_check", "1");
	    	session.setAttribute("obj", obj);
			
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCNhapXuatTon.jsp";
			response.sendRedirect(nextJSP);
	    	
	    }
	    else
	    {
	    	try 
	    	{	
    			response.setContentType("application/xlsm");
	    		response.setHeader("Content-Disposition", "attachment; filename=ErpTongHopNhapXuatTonTT.xlsm");

    			TongHopNXT(out, obj);
    			obj.close();
			} 
	    	catch (Exception e) 
	    	{ 
	    		e.printStackTrace();
	    		System.out.println("Exception: " + e.getMessage()); 
	    	}
	    }
	}
	
	private void TongHopNXT(OutputStream out, IBaocao obj) throws Exception
    {   
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();		
		
		if( obj.getXemtheolo().equals("0") || obj.getXemtheolo().equals("3") ) //Tổng quát hoặc theo hóa đơn
		{
			System.out.println("xemtheolo="+obj.getXemtheolo());
			String path = getServletContext().getInitParameter("path") + "\\ErpNhapXuatTon_TongQuat.xlsm";
			if(obj.getXemtheolo().equals("0"))
			{
				if( obj.getKhoIds().equals("100024") )
					path = getServletContext().getInitParameter("path") + "\\ErpNhapXuatTon_TongQuat_ChiNhanh.xlsm";
			}
			else
			{
				path = getServletContext().getInitParameter("path") + "\\ErpNhapXuatTon_TongQuatHD.xlsm";
			}
			fstream = new FileInputStream(path);
		}
		else if( obj.getXemtheolo().equals("1") ) //Theo lô
			fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ErpNhapXuatTon_TheoLo.xlsm");
		else if( obj.getXemtheolo().equals("2") )  //Chi tiết thùng, mẻ...
		{
			fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ErpNhapXuatTon.xlsm");
			if( obj.getKhoIds().equals("100024") )
			{
				fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ErpNhapXuatTon_ChiNhanh.xlsm");
			}
		}
		else if( obj.getXemtheolo().equals("4") )  //Chi tiết thùng, mẻ...
			fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ErpNhapXuatTon_TheoMaPhieu.xlsm");
		
		workbook.open(fstream);
		
	    BaoCaoTongHopNXT(workbook, obj);	     
	     
	    workbook.save(out);
			
		fstream.close();
		
    }

	private List<XuatNhapTonObj> BaoCaoTongHopNXT(IBaocao obj){
	    
	    Utility util = new Utility();
	    dbutils db = new dbutils();
	    
	    List<XuatNhapTonObj> list = new ArrayList<XuatNhapTonObj>();
	    
	    String condition = " and kho_fk in  " + util.quyen_khott(obj.getUserId());
	    if( obj.getKhoIds().trim().length() > 0 )
	    	condition += " and kho_fk in ( " + obj.getKhoIds() + " ) ";
	    
	    if( obj.getLoaiSanPhamIds().trim().length() > 0 )
	    	condition += " and sanpham_fk in ( select PK_SEQ from ERP_SANPHAM where LOAISANPHAM_FK in ( " + obj.getLoaiSanPhamIds() + " ) ) ";
	    
	    if( obj.getSanPhamIds().trim().length() > 0 )
	    	condition += " and sanpham_fk in ( " + obj.getSanPhamIds() + " ) ";
	    
	    String query = "";
	    
	    if( obj.getXemtheolo().equals("0") ) //Tổng quát
	    {
	    	if( !obj.getKhoIds().equals("100024") )
	    	{
		    	query = "select ma, ten, donvi, sum(dauky) as dauky, sum(nhap) as nhap, sum(xuat) as xuat " + 
					    "	from UFN_NXT_HO_FULL ( '" + obj.getTuNgay() + "', '" + obj.getDenNgay() + "' ) " + 
					    " where 1 = 1 " + condition;
				
			    query += " group by ma, ten, donvi ";
			    query += " order by ma ";
	    	}
	    	else
	    	{
	    		query = "select machinhanh, tenchinhanh, masanpham, tensanpham, donvi, toncuoi, dongia, thanhtien " + 
	    				"from UFN_NXT_HO_FULL_CHINHANH( '" + obj.getTuNgay() + "', '" + obj.getDenNgay() + "' )";
				if(obj.getDoituongId().trim().length() > 0)
					query += " where nppId = " + obj.getDoituongId();
			    query += " order by machinhanh, tensanpham, donvi ";
	    	}
	    }
		else if( obj.getXemtheolo().equals("1") ) //Theo lô
		{
			query = "select ma, ten, donvi, solo, ngayhethan, sum(dauky) as dauky, sum(nhap) as nhap, sum(xuat) as xuat " + 
				    "	from UFN_NXT_HO_FULL ( '" + obj.getTuNgay() + "', '" + obj.getDenNgay() + "' ) " + 
				    " where 1 = 1 " + condition;
			
		    query += " group by ma, ten, donvi, solo, ngayhethan ";
		    query += " order by ma, NGAYHETHAN, SOLO ";
		}
	    
		else if( obj.getXemtheolo().equals("4") ) //Theo ma phieu
		{
			query = "select ma, ten, donvi, solo, ngayhethan,  dauky,  nhap,xuat, maphieu from (   select ma, ten, donvi, solo, ngayhethan, sum(dauky) as dauky, sum(nhap) as nhap, sum(xuat) as xuat, maphieu " + 
				    "	from UFN_NXT_HO_FULL ( '" + obj.getTuNgay() + "', '" + obj.getDenNgay() + "' ) " + 
				    " where 1 = 1 " + condition;
			
		    query += " group by ma, ten, donvi, solo, ngayhethan, maphieu ";
		    query += ")  data where (dauky<>0) or (nhap<>0) or (xuat <>0)   order by ma, NGAYHETHAN, SOLO ";
		}
		else if( obj.getXemtheolo().equals("2") )  //Chi tiết thùng, mẻ...
		{
			query = "select kho, A.ma, A.ten, donvi, vitri, solo, ngayhethan, mame, mathung, maphieu, phieudt, phieueo, MARQ, hamam, hamluong, sum(dauky) as dauky, sum(nhap) as nhap, sum(xuat) as xuat,ISNULL(NSX.MA,'') AS NSX " + 
				    "	from UFN_NXT_HO_FULL ( '" + obj.getTuNgay() + "', '" + obj.getDenNgay() + "' ) A \n"
				    		+ "	LEFT JOIN ERP_NHASANXUAT NSX ON NSX.PK_SEQ = A.NSX_FK \n" + 
				    " where 1 = 1 " + condition;
			
		    query += " group by kho, A.ma, A.ten, donvi, vitri, solo, ngayhethan, mame, mathung, maphieu, phieudt, phieueo, MARQ, hamam, hamluong,ISNULL(NSX.MA,'') ";
		    query += " order by ma, NGAYHETHAN, SOLO, MAPHIEU, dbo.ftConvertToNumber(MAME), dbo.ftConvertToNumber(MATHUNG) ";
		}
		else if( obj.getXemtheolo().equals("3") ) //Tổng quát theo hóa đơn nhập
	    {
	    	query = "select ma, ten, donvi,CAST ( sum(dauky) as numeric(18,4)) as dauky, sum(nhap) as nhap, sum(xuat) as xuat " + 
				    "	from UFN_NXT_HO_THEO_HOADON ( '" + obj.getTuNgay() + "', '" + obj.getDenNgay() + "' ) " + 
				    " where 1 = 1 " + condition;
			
		    query += " group by ma, ten, donvi ";
		    query += " order by ma ";
	    }
	    
	    
		System.out.println(":::: LAY BAO CAO: " + query);
		ResultSet  chiTietNXT = db.get(query);
		
		try 
		{
			int index = 11;
			double totalDAUKY = 0;
			double totalNHAP = 0;
			double totalXUAT = 0;
			double totalTONCUOI = 0;
			
			if( obj.getXemtheolo().equals("0") || obj.getXemtheolo().equals("3") ) //Tổng quát
			{
				if(chiTietNXT != null)
				{
					while(chiTietNXT.next())
					{

						String ma = chiTietNXT.getString("ma");
						String ten = chiTietNXT.getString("ten");
						String donvi = chiTietNXT.getString("donvi");
						
						double dauky = chiTietNXT.getDouble("dauky");
						totalDAUKY += dauky;
						
						double nhap = chiTietNXT.getDouble("nhap");
						totalNHAP += nhap;
						
						double xuat = chiTietNXT.getDouble("xuat");
						totalXUAT += xuat;
						
						double toncuoi = dauky + nhap - xuat;	
						if( Math.abs(toncuoi)  <0.001)
							toncuoi=0;
						totalTONCUOI += toncuoi;
						
						XuatNhapTonObj temp = new XuatNhapTonObj();
						temp.setMa(ma);
						temp.setTen(ten);
						temp.setDonvi(donvi);
						temp.setDauky(dauky);
						temp.setNhap(nhap);
						temp.setToncuoi(toncuoi);
						temp.setXuat(xuat);
						
						list.add(temp);
						
						index++;
					}
					chiTietNXT.close();
				}
			}
			else if( obj.getXemtheolo().equals("1") ) //XEM THEO LÔ
			{
				if(chiTietNXT != null)
				{
					while(chiTietNXT.next())
					{
						String stt = Integer.toString(index - 10);
						
						String ma = chiTietNXT.getString("ma");
						String ten = chiTietNXT.getString("ten");
						String donvi = chiTietNXT.getString("donvi");
						
						String solo = chiTietNXT.getString("solo");
						String ngayhethan = chiTietNXT.getString("ngayhethan");

						double dauky = chiTietNXT.getDouble("dauky");
						totalDAUKY += dauky;
						
						double nhap = chiTietNXT.getDouble("nhap");
						totalNHAP += nhap;
						
						double xuat = chiTietNXT.getDouble("xuat");
						totalXUAT += xuat;
						
						double toncuoi = dauky + nhap - xuat;
						if( Math.abs(toncuoi)  <0.001)
							toncuoi=0;
						totalTONCUOI += toncuoi;
	
						index++;
						XuatNhapTonObj temp = new XuatNhapTonObj();
						temp.setMa(ma);
						temp.setTen(ten);
						temp.setDonvi(donvi);
						
						temp.setSolo(solo);
						temp.setNgayhethan(ngayhethan);
						
						temp.setDauky(dauky);
						temp.setNhap(nhap);
						temp.setToncuoi(toncuoi);
						temp.setXuat(xuat);
						
						list.add(temp);
						
					}
					chiTietNXT.close();
				}
			}
			else if( obj.getXemtheolo().equals("4") ) //XEM THEO MA PHIEU
			{
				if(chiTietNXT != null)
				{
					while(chiTietNXT.next())
					{
						String stt = Integer.toString(index - 10);
						
						String ma = chiTietNXT.getString("ma");
						String ten = chiTietNXT.getString("ten");
						String donvi = chiTietNXT.getString("donvi");
						String maphieu = chiTietNXT.getString("maphieu");
						String solo = chiTietNXT.getString("solo");
						String ngayhethan = chiTietNXT.getString("ngayhethan");

						double dauky = chiTietNXT.getDouble("dauky");
						totalDAUKY += dauky;
						
						double nhap = chiTietNXT.getDouble("nhap");
						totalNHAP += nhap;
						
						double xuat = chiTietNXT.getDouble("xuat");
						totalXUAT += xuat;
						
						double toncuoi = dauky + nhap - xuat;	
						if( Math.abs(toncuoi)  <0.001)
							toncuoi=0;
						totalTONCUOI += toncuoi;
						
						System.out.println("dau ky: "+dauky +" nhap: "+ nhap +" xuat: "+ xuat );
						
	
						index++;
						XuatNhapTonObj temp = new XuatNhapTonObj();
						temp.setMaphieu(maphieu);
						temp.setMa(ma);
						temp.setTen(ten);
						temp.setDonvi(donvi);
						
						temp.setSolo(solo);
						temp.setNgayhethan(ngayhethan);
						
						temp.setDauky(dauky);
						temp.setNhap(nhap);
						temp.setToncuoi(toncuoi);
						temp.setXuat(xuat);
						
						list.add(temp);
						
					}
					chiTietNXT.close();
				}
			}
			else //Xem theo tất cả thông tin: thùng, mẻ, hàm lượng, hàm ẩm....
			{
				index = 10;
				if(chiTietNXT != null)
				{
					while(chiTietNXT.next())
					{
						String stt = Integer.toString(index - 9);
						
						String kho = chiTietNXT.getString("kho");
						String ma = chiTietNXT.getString("ma");
						String ten = chiTietNXT.getString("ten");
						String donvi = chiTietNXT.getString("donvi");
						
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
						String NSX = chiTietNXT.getString("NSX");
						double dongia = 0;
						
						
						double dauky = chiTietNXT.getDouble("dauky");
						totalDAUKY += dauky;
						
						double nhap = chiTietNXT.getDouble("nhap");
						totalNHAP += nhap;
						
						double xuat = chiTietNXT.getDouble("xuat");
						totalXUAT += xuat;
						
						double toncuoi = dauky + nhap - xuat;	
						if( Math.abs(toncuoi)  <0.001)
							toncuoi=0;
						totalTONCUOI += toncuoi;
	
						index++;
						
						XuatNhapTonObj temp = new XuatNhapTonObj();
						temp.setMa(ma);
						temp.setTen(ten);
						temp.setDonvi(donvi);
						
						temp.setSolo(solo);
						temp.setNgayhethan(ngayhethan);
						
						temp.setMame(mame);
						temp.setMathung(mathung);
						temp.setVitri(vitri);
						temp.setMaphieu(maphieu);
						temp.setPhieuDT(phieuDT);
						temp.setPhieuEO(phieuEO);
						temp.setMarq(marq);
						temp.setHamam(hamam);
						temp.setHamluong(hamluong);
						
						temp.setDauky(dauky);
						temp.setNhap(nhap);
						temp.setToncuoi(toncuoi);
						temp.setXuat(xuat);
						temp.setNsx(NSX);
						list.add(temp);
						
					}
					chiTietNXT.close();
				}
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
	
	private void BaoCaoTongHopNXT(Workbook workbook, IBaocao obj) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    worksheet.setName("Sheet1");
	    
	    Cells cells = worksheet.getCells();
		
	    Style style;
	    Font font = new Font();
	    font.setName("Times New Roman");
	    font.setColor(Color.RED);//mau chu
	    font.setSize(16);// size chu
	   	font.setBold(true);

	    Cell cell = cells.getCell("A1");
	    style = cell.getStyle();
	    style.setFont(font); 
	    style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu 	  
	    
	    String tieude = "Tên DN: Công ty TNHH Traphaco Hưng Yên ";
	    ReportAPI.getCellStyle(cell, Color.BLACK, false, 10, tieude);
	    cells.merge(0, 0, 0, 8);
	    
	    cell = cells.getCell("A2");
	    tieude = "Địa chỉ: Thôn Bình Lương, Xã Tân Quang, Huyện Văn Lâm, Hưng Yên ";
	    ReportAPI.getCellStyle(cell, Color.BLACK, false, 10, tieude);
	    cells.merge(1, 0, 0, 8);
	    
	    cell = cells.getCell("A3");
	    tieude = "Điện thoại: 02213791125  -  Fax: 02213791128";
	    ReportAPI.getCellStyle(cell, Color.BLACK, false, 10, tieude);
	    cells.merge(2, 0, 0, 8);
	    
	    //create data
	    dbutils db = new dbutils();

	    cells.setRowHeight(4, 30.0f);
	    cell = cells.getCell("A5");
	    tieude = "BÁO CÁO NHẬP XUẤT TỒN";
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 18, tieude);
	    
	    String tenKHO = "";
	    if(obj.getKhoIds().trim().length() > 0 && obj.getKhoIds().trim().length() <= 10 )
	    {
	    	String query = "select ma + ' - ' + ten as ten from ERP_KHOTT where pk_seq = '" + obj.getKhoIds() + "' ";
	    	ResultSet rs = db.get(query);
	    	if( rs != null )
	    	{
	    		try 
	    		{
					if( rs.next() )
					{
						tenKHO = rs.getString("ten");
					}
					rs.close();
				} 
	    		catch (Exception e) { }
	    	}
	    }
	    cell = cells.getCell("A6");
	    tieude = "Kho: " + tenKHO;
	    ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, tieude);
	    cells.merge(2, 0, 0, 8);
	    
	    cell = cells.getCell("A7");
	    tieude = "Từ ngày " + obj.getTuNgay() + " đến ngày " + obj.getDenNgay();
	    ReportAPI.getCellStyle(cell, Color.BLACK, false, 10, tieude);
	    
	    
	    Utility util = new Utility();
	    String condition = " and kho_fk in  " + util.quyen_khott(obj.getUserId());
	    if( obj.getKhoIds().trim().length() > 0 )
	    	condition += " and kho_fk in ( " + obj.getKhoIds() + " ) ";
	    
	    if( obj.getLoaiSanPhamIds().trim().length() > 0 )
	    	condition += " and a.sanpham_fk in ( select PK_SEQ from ERP_SANPHAM where LOAISANPHAM_FK in ( " + obj.getLoaiSanPhamIds() + " ) ) ";
	    
	    if( obj.getSanPhamIds().trim().length() > 0 )
	    	condition += " and a.sanpham_fk in ( " + obj.getSanPhamIds() + " ) ";
	    
	    String query = "";
	    
	    if( obj.getXemtheolo().equals("0") ) //Tổng quát
	    {
		    if( !obj.getKhoIds().equals("100024") )
	    	{
		    	query = " select  ma, ten, donvi,gia.DONGIA, sum(dauky) as dauky, sum(nhap) as nhap, sum(xuat) as xuat, round( ( (sum(dauky) + sum(nhap) - sum(xuat)) * gia.DONGIA ) + 0.0000001, 0) thanhtien " + 
					    " from UFN_NXT_HO_FULL ( '" + obj.getTuNgay() + "', '" + obj.getDenNgay() + "' ) a   " +
			    		" left join ERP_BANGGIA_THANHPHAM_CUOIKY gia on gia.sanpham_fk= a.sanpham_fk " +
			    		" and gia.nam="+obj.getDenNgay().substring(0, 4)+" and gia.thang= " + obj.getDenNgay().substring(5, 7)+
					    " where 1 = 1 " + condition;

			    query += " group by a.ma, a.ten, a.donvi,gia.dongia ";
			    query += " order by ma ";
	    	}
	    	else
	    	{
	    		query = "select machinhanh, tenchinhanh, masanpham, tensanpham, donvi, toncuoi, dongia, thanhtien " + 
	    				"from UFN_NXT_HO_FULL_CHINHANH( '" + obj.getTuNgay() + "', '" + obj.getDenNgay() + "' ) a";
	    		if(obj.getDoituongId().trim().length() > 0)
					query += " where nppId = " + obj.getDoituongId();
			    query += " order by machinhanh, tensanpham, donvi ";
	    	}
	    }
		else if( obj.getXemtheolo().equals("1") ) //Theo lô
		{
			query = "select ma, ten, donvi, solo, ngayhethan, sum(dauky) as dauky, sum(nhap) as nhap, sum(xuat) as xuat " + 
				    "	from UFN_NXT_HO_FULL ( '" + obj.getTuNgay() + "', '" + obj.getDenNgay() + "' ) a " + 
				    " where 1 = 1 " + condition;
			
		    query += " group by ma, ten, donvi, solo, ngayhethan ";
		    query += " order by ma, NGAYHETHAN, SOLO ";
		}
		else if( obj.getXemtheolo().equals("4") ) //Theo ma phieu
		{
			query = "  select ma, ten, donvi,vitri, solo, ngayhethan,  dauky,  nhap,xuat, maphieu from (  select ma, ten, donvi,vitri, solo, ngayhethan, sum(dauky) as dauky, sum(nhap) as nhap, sum(xuat) as xuat,  maphieu " + 
				    "\n	from UFN_NXT_HO_FULL ( '" + obj.getTuNgay() + "', '" + obj.getDenNgay() + "' ) a" + 
				    " where 1 = 1  " + condition  ;
			
		    query += " group by ma, ten, donvi, vitri, solo, ngayhethan,  maphieu ";
		    query += " )  data where (dauky<>0) or (nhap<>0) or (xuat <>0)  order by ma, NGAYHETHAN, SOLO ";
		}
		else if( obj.getXemtheolo().equals("2") )  //Chi tiết thùng, mẻ...
		{
			if( !obj.getKhoIds().equals("100024") )
	    	{
				query = "select kho, a.ma, a.ten, donvi, vitri, solo, ngayhethan, mame, mathung, maphieu, phieudt, phieueo, MARQ, hamam, hamluong, isnull(gia,0) as dongia, sum(dauky) as dauky, sum(nhap) as nhap, sum(xuat) as xuat, round( ( (sum(dauky) + sum(nhap) - sum(xuat)) * isnull(gia,0) ) + 0.0000001, 0) thanhtien,ISNULL(NSX.MA,'') AS NSX " + 
					    "	from UFN_NXT_HO_FULL ( '" + obj.getTuNgay() + "', '" + obj.getDenNgay() + "' ) a	\n"
					    		+ "	LEFT JOIN ERP_NHASANXUAT NSX ON NSX.PK_SEQ = a.nsx_fk " + 
					    " where 1 = 1 " + condition;
			    query += " group by kho, a.ma, a.ten, donvi, vitri, solo, ngayhethan, mame, mathung, maphieu, phieudt, phieueo, MARQ, hamam, hamluong, gia,ISNULL(NSX.MA,'')  \n";
			    query += " order by ma, NGAYHETHAN, SOLO, MAPHIEU, dbo.ftConvertToNumber(MAME), dbo.ftConvertToNumber(MATHUNG) \n";
	    	} 
			else 
			{
				query = "select machinhanh, tenchinhanh, masanpham, tensanpham, donvi, solo, ngayhethan, toncuoi, dongia, thanhtien " + 
	    				"from UFN_NXT_HO_FULL_CHINHANH_CHITIET( '" + obj.getTuNgay() + "', '" + obj.getDenNgay() + "' ) a";
				if(obj.getDoituongId().trim().length() > 0)
					query += " where nppId = " + obj.getDoituongId();
			    query += " order by machinhanh, tensanpham, donvi ";
	    	}
		}
		else if( obj.getXemtheolo().equals("3") ) //Tổng quát theo hóa đơn nhập
	    {
	    	query = "select ma, ten, donvi, CAST ( sum(dauky) as numeric(18,4)) as dauky, sum(nhap) as nhap, sum(xuat) as xuat " + 
				    "	from UFN_NXT_HO_THEO_HOADON ( '" + obj.getTuNgay() + "', '" + obj.getDenNgay() + "' ) a " + 
				    " where 1 = 1 " + condition;
			
		    query += " group by ma, ten, donvi ";
		    query += " order by ma ";
	    }
	    
		System.out.println(":::: LAY BAO CAO: " + query);
		ResultSet  chiTietNXT = db.get(query);
		
		try 
		{
			int index = 11;
			double totalDAUKY = 0;
			double totalNHAP = 0;
			double totalXUAT = 0;
			double totalTONCUOI = 0;
			DecimalFormat formatter = new DecimalFormat("#,###,###.####");

			if( obj.getXemtheolo().equals("0") || obj.getXemtheolo().equals("3") ) //Tổng quát
			{
				if( !obj.getKhoIds().equals("100024") )
				{
					if(chiTietNXT != null)
					{
						while(chiTietNXT.next())
						{
							String stt = Integer.toString(index - 10);
	
							String ma = chiTietNXT.getString("ma");
							String ten = chiTietNXT.getString("ten");
							String donvi = chiTietNXT.getString("donvi");
							
							double dauky = chiTietNXT.getDouble("dauky");
							totalDAUKY += dauky;
							
							double nhap = chiTietNXT.getDouble("nhap");
							totalNHAP += nhap;
							
							double xuat = chiTietNXT.getDouble("xuat");
							totalXUAT += xuat;
							
							double toncuoi = dauky + nhap - xuat;
							if( Math.abs(toncuoi)  <0.001)
							{
								toncuoi=0;
							}
							totalTONCUOI += toncuoi;
							
							cell = cells.getCell("A" + Integer.toString(index)); 	cell.setValue(stt);
							
							cell = cells.getCell("B" + Integer.toString(index)); 	cell.setValue(ma);
							cell = cells.getCell("C" + Integer.toString(index)); 	cell.setValue(ten);
							cell = cells.getCell("D" + Integer.toString(index)); 	cell.setValue(donvi);
							
							cell = cells.getCell("E" + Integer.toString(index)); 	cell.setValue(dauky); 
							cell = cells.getCell("F" + Integer.toString(index)); 	cell.setValue(nhap); 
							cell = cells.getCell("G" + Integer.toString(index)); 	cell.setValue(xuat); 
							cell = cells.getCell("H" + Integer.toString(index)); 	cell.setValue(toncuoi); 
							
						  	if( obj.getXemtheolo().equals("0") )
						  	{
								double dongia = chiTietNXT.getDouble("dongia");
								double tonggiatri = chiTietNXT.getDouble("thanhtien");
								cell = cells.getCell("I" + Integer.toString(index)); 	cell.setValue(dongia);
								cell = cells.getCell("J" + Integer.toString(index)); 	cell.setValue(tonggiatri);
							}
							
							index++;
						}
						chiTietNXT.close();
					}
				}
				else
				{
					if(chiTietNXT != null)
					{
						while(chiTietNXT.next())
						{
							String stt = Integer.toString(index - 10);
							String donvi = chiTietNXT.getString("donvi");
							
							cell = cells.getCell("A" + Integer.toString(index)); 	cell.setValue(stt);
							if( obj.getXemtheolo().equals("0") )
							{
								String machinhanh = chiTietNXT.getString("machinhanh");
								String tenchinhanh = chiTietNXT.getString("tenchinhanh");
								String masanpham = chiTietNXT.getString("masanpham");
								String tensanpham = chiTietNXT.getString("tensanpham");
								double toncuoi = chiTietNXT.getDouble("toncuoi");
								totalTONCUOI += toncuoi;
								
								double dongia = chiTietNXT.getDouble("dongia");
								double thanhtien = chiTietNXT.getDouble("thanhtien");
								cell = cells.getCell("B" + Integer.toString(index)); 	cell.setValue(machinhanh);
								cell = cells.getCell("C" + Integer.toString(index)); 	cell.setValue(tenchinhanh);
								
								cell = cells.getCell("D" + Integer.toString(index)); 	cell.setValue(masanpham);
								cell = cells.getCell("E" + Integer.toString(index)); 	cell.setValue(tensanpham); 
								cell = cells.getCell("F" + Integer.toString(index)); 	cell.setValue(donvi); 
								
								cell = cells.getCell("G" + Integer.toString(index)); 	cell.setValue(toncuoi); 
								cell = cells.getCell("H" + Integer.toString(index)); 	cell.setValue(dongia); 
								cell = cells.getCell("I" + Integer.toString(index)); 	cell.setValue(thanhtien);
							}
							else
							{	
								String ma = chiTietNXT.getString("ma");
								String ten = chiTietNXT.getString("ten");
								double dauky = chiTietNXT.getDouble("dauky");
								totalDAUKY += dauky;
								
								double nhap = chiTietNXT.getDouble("nhap");
								totalNHAP += nhap;
								
								double xuat = chiTietNXT.getDouble("xuat");
								totalXUAT += xuat;
								
								double toncuoi = dauky + nhap - xuat;
								totalTONCUOI += toncuoi;
								cell = cells.getCell("B" + Integer.toString(index)); 	cell.setValue(ma);
								cell = cells.getCell("C" + Integer.toString(index)); 	cell.setValue(ten); 
								cell = cells.getCell("D" + Integer.toString(index)); 	cell.setValue(donvi); 
								cell = cells.getCell("E" + Integer.toString(index)); 	cell.setValue(dauky); 
								cell = cells.getCell("F" + Integer.toString(index)); 	cell.setValue(nhap); 
								cell = cells.getCell("G" + Integer.toString(index)); 	cell.setValue(xuat); 
								cell = cells.getCell("H" + Integer.toString(index)); 	cell.setValue(toncuoi); 
							}

							index++;
						}
						chiTietNXT.close();
					}
				}
			}
			else if( obj.getXemtheolo().equals("1") ) //XEM THEO LÔ
			{
				if(chiTietNXT != null)
				{
					while(chiTietNXT.next())
					{
						String stt = Integer.toString(index - 10);
						
						String ma = chiTietNXT.getString("ma");
						String ten = chiTietNXT.getString("ten");
						String donvi = chiTietNXT.getString("donvi");
						
						String solo = chiTietNXT.getString("solo");
						String ngayhethan = chiTietNXT.getString("ngayhethan");

						double dauky = chiTietNXT.getDouble("dauky");
						totalDAUKY += dauky;
						
						double nhap = chiTietNXT.getDouble("nhap");
						totalNHAP += nhap;
						
						double xuat = chiTietNXT.getDouble("xuat");
						totalXUAT += xuat;
						
						double toncuoi = dauky + nhap - xuat;	
						
						if( Math.abs(toncuoi) < 0.001)
						{
							toncuoi=0;
						}
						totalTONCUOI += toncuoi;
	
						cell = cells.getCell("A" + Integer.toString(index)); 	cell.setValue(stt);
						
						cell = cells.getCell("B" + Integer.toString(index)); 	cell.setValue(ma);
						cell = cells.getCell("C" + Integer.toString(index)); 	cell.setValue(ten);
						cell = cells.getCell("D" + Integer.toString(index)); 	cell.setValue(donvi);
						
						cell = cells.getCell("E" + Integer.toString(index)); 	cell.setValue(solo);
						cell = cells.getCell("F" + Integer.toString(index)); 	cell.setValue(ngayhethan); 
						
						cell = cells.getCell("G" + Integer.toString(index)); 	cell.setValue(dauky); 
						cell = cells.getCell("H" + Integer.toString(index)); 	cell.setValue(nhap); 
						cell = cells.getCell("I" + Integer.toString(index)); 	cell.setValue(xuat); 
						cell = cells.getCell("J" + Integer.toString(index)); 	cell.setValue(toncuoi); 
	
						index++;
					}
					chiTietNXT.close();
				}
			}
			else if( obj.getXemtheolo().equals("4") ) //XEM THEO ma phiee
			{
				System.out.println("VAODAY");
				if(chiTietNXT != null)
				{
					while(chiTietNXT.next())
					{
						String stt = Integer.toString(index - 10);
						
						String ma = chiTietNXT.getString("ma");
						String ten = chiTietNXT.getString("ten");
						String donvi = chiTietNXT.getString("donvi");
						String maphieu=chiTietNXT.getString("maphieu");
						String vitri = chiTietNXT.getString("vitri");
						String solo = chiTietNXT.getString("solo");
						String ngayhethan = chiTietNXT.getString("ngayhethan");

						double dauky = chiTietNXT.getDouble("dauky");
						totalDAUKY += dauky;
						
						double nhap = chiTietNXT.getDouble("nhap");
						totalNHAP += nhap;
						
						double xuat = chiTietNXT.getDouble("xuat");
						totalXUAT += xuat;
						
						double toncuoi = dauky + nhap - xuat;	
						if( Math.abs(toncuoi)  <0.001)
						{
							toncuoi=0;
						}
						totalTONCUOI += toncuoi;
	
						cell = cells.getCell("A" + Integer.toString(index)); 	cell.setValue(stt);
						
						cell = cells.getCell("B" + Integer.toString(index)); 	cell.setValue(ma);
						cell = cells.getCell("C" + Integer.toString(index)); 	cell.setValue(ten);
						cell = cells.getCell("D" + Integer.toString(index)); 	cell.setValue(donvi);
						cell = cells.getCell("E" + Integer.toString(index)); 	cell.setValue(maphieu);
												
						cell = cells.getCell("F" + Integer.toString(index)); 	cell.setValue(vitri);
						cell = cells.getCell("G" + Integer.toString(index)); 	cell.setValue(solo);
						cell = cells.getCell("H" + Integer.toString(index)); 	cell.setValue(ngayhethan); 
						
						cell = cells.getCell("I" + Integer.toString(index)); 	cell.setValue(dauky); 
						cell = cells.getCell("J" + Integer.toString(index)); 	cell.setValue(nhap); 
						cell = cells.getCell("K" + Integer.toString(index)); 	cell.setValue(xuat); 
						cell = cells.getCell("L" + Integer.toString(index)); 	cell.setValue(toncuoi); 
	
						index++;
					}
					chiTietNXT.close();
				}
			}
			else //Xem theo tất cả thông tin: thùng, mẻ, hàm lượng, hàm ẩm....
			{
				if(!obj.getKhoIds().equals("100024"))
				{
					index = 10;
					if(chiTietNXT != null)
					{
						while(chiTietNXT.next())
						{
							String stt = Integer.toString(index - 9);
							
							String kho = chiTietNXT.getString("kho");
							String ma = chiTietNXT.getString("ma");
							String ten = chiTietNXT.getString("ten");
							String donvi = chiTietNXT.getString("donvi");
							
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
							String NSX = chiTietNXT.getString("NSX");
							//double dongia = chiTietNXT.getDouble("dongia");
							
							double dauky = chiTietNXT.getDouble("dauky");
							totalDAUKY += dauky;
							
							double nhap = chiTietNXT.getDouble("nhap");
							totalNHAP += nhap;
							
							double xuat = chiTietNXT.getDouble("xuat");
							totalXUAT += xuat;
							
							double toncuoi = dauky + nhap - xuat;	
							if( Math.abs(toncuoi) < 0.001)
							{
								toncuoi=0;
							}
							totalTONCUOI += toncuoi;
		
							double dongia = chiTietNXT.getDouble("dongia");
							double thanhtien = chiTietNXT.getDouble("THANHTIEN");
							
							cell = cells.getCell("A" + Integer.toString(index)); 	cell.setValue(stt);
							
							cell = cells.getCell("B" + Integer.toString(index)); 	cell.setValue(kho);
							cell = cells.getCell("C" + Integer.toString(index)); 	cell.setValue(ma);
							cell = cells.getCell("D" + Integer.toString(index)); 	cell.setValue(ten);
							cell = cells.getCell("E" + Integer.toString(index)); 	cell.setValue(donvi);
							
							cell = cells.getCell("F" + Integer.toString(index)); 	cell.setValue(solo);
							cell = cells.getCell("G" + Integer.toString(index)); 	cell.setValue(ngayhethan); 
							
							cell = cells.getCell("H" + Integer.toString(index)); 	cell.setValue(mame);
							cell = cells.getCell("I" + Integer.toString(index)); 	cell.setValue(mathung);
							cell = cells.getCell("J" + Integer.toString(index)); 	cell.setValue(vitri);
							
							cell = cells.getCell("K" + Integer.toString(index)); 	cell.setValue(maphieu); 
							cell = cells.getCell("L" + Integer.toString(index)); 	cell.setValue(phieuDT); 
							cell = cells.getCell("M" + Integer.toString(index)); 	cell.setValue(phieuEO); 
							
							cell = cells.getCell("N" + Integer.toString(index)); 	cell.setValue(marq);
							cell = cells.getCell("O" + Integer.toString(index)); 	cell.setValue(hamam);
							cell = cells.getCell("P" + Integer.toString(index)); 	cell.setValue(hamluong); 
							cell = cells.getCell("Q" + Integer.toString(index)); 	cell.setValue(NSX); 
							cell = cells.getCell("R" + Integer.toString(index)); 	cell.setValue(dauky); 
							cell = cells.getCell("S" + Integer.toString(index)); 	cell.setValue(dongia); 
							cell = cells.getCell("T" + Integer.toString(index)); 	cell.setValue(Math.round( dauky * dongia) ); 
							
							cell = cells.getCell("U" + Integer.toString(index)); 	cell.setValue(nhap); 
							cell = cells.getCell("V" + Integer.toString(index)); 	cell.setValue(dongia); 
							cell = cells.getCell("W" + Integer.toString(index)); 	cell.setValue(Math.round( nhap * dongia )); 
							
							cell = cells.getCell("X" + Integer.toString(index)); 	cell.setValue(xuat); 
							cell = cells.getCell("Y" + Integer.toString(index)); 	cell.setValue(dongia); 
							cell = cells.getCell("Z" + Integer.toString(index)); 	cell.setValue(Math.round( nhap * dongia )); 
							
							cell = cells.getCell("AA" + Integer.toString(index)); 	cell.setValue(toncuoi); 
							cell = cells.getCell("AB" + Integer.toString(index)); 	cell.setValue(dongia); 
							cell = cells.getCell("AC" + Integer.toString(index)); 	cell.setValue(thanhtien); 
		
							index++;
						}
						chiTietNXT.close();
					}
		
					cell = cells.getCell("A" + Integer.toString(index)); 
					ReportAPI.getCellStyle(cell, Color.BLACK, true, 10, "Tổng cộng");
					
					cell = cells.getCell("R" + Integer.toString(index)); 	cell.setValue(totalDAUKY);
					cell = cells.getCell("S" + Integer.toString(index));    cell.setValue(0);
					cell = cells.getCell("T" + Integer.toString(index)); 	cell.setValue(totalDAUKY * 0);
					
					cell = cells.getCell("U" + Integer.toString(index)); 	cell.setValue(totalNHAP);
					cell = cells.getCell("V" + Integer.toString(index));    cell.setValue(0);
					cell = cells.getCell("W" + Integer.toString(index)); 	cell.setValue(totalNHAP * 0);
		
					cell = cells.getCell("X" + Integer.toString(index)); 	cell.setValue(totalXUAT);
					cell = cells.getCell("Y" + Integer.toString(index));    cell.setValue(0);
					cell = cells.getCell("Z" + Integer.toString(index)); 	cell.setValue(totalXUAT * 0);
		
					cell = cells.getCell("AA" + Integer.toString(index)); 	cell.setValue(totalTONCUOI);
					cell = cells.getCell("AB" + Integer.toString(index)); 	cell.setValue(0);
					cell = cells.getCell("AC" + Integer.toString(index)); 	cell.setValue(totalTONCUOI * 0);
				}
				else
				{
					if(chiTietNXT != null)
					{
						while(chiTietNXT.next())
						{
							String stt = Integer.toString(index - 10);

							String machinhanh = chiTietNXT.getString("machinhanh");
							String tenchinhanh = chiTietNXT.getString("tenchinhanh");
							String masanpham = chiTietNXT.getString("masanpham");
							String tensanpham = chiTietNXT.getString("tensanpham");
							String donvi = chiTietNXT.getString("donvi");
							String solo = chiTietNXT.getString("solo");
							String ngayhethan = chiTietNXT.getString("ngayhethan");
							double toncuoi = chiTietNXT.getDouble("toncuoi");
							
							double dongia = chiTietNXT.getDouble("dongia");
							double thanhtien = chiTietNXT.getDouble("THANHTIEN");
							
							totalTONCUOI += toncuoi;
							
							cell = cells.getCell("A" + Integer.toString(index)); 	cell.setValue(stt);
							
							cell = cells.getCell("B" + Integer.toString(index)); 	cell.setValue(machinhanh);
							cell = cells.getCell("C" + Integer.toString(index)); 	cell.setValue(tenchinhanh);
							
							cell = cells.getCell("D" + Integer.toString(index)); 	cell.setValue(masanpham);
							cell = cells.getCell("E" + Integer.toString(index)); 	cell.setValue(tensanpham); 
							cell = cells.getCell("F" + Integer.toString(index)); 	cell.setValue(donvi);
							cell = cells.getCell("G" + Integer.toString(index)); 	cell.setValue(solo); 
							cell = cells.getCell("H" + Integer.toString(index)); 	cell.setValue(ngayhethan); 
							
							cell = cells.getCell("I" + Integer.toString(index)); 	cell.setValue(toncuoi); 
							cell = cells.getCell("J" + Integer.toString(index)); 	cell.setValue(dongia); 
							cell = cells.getCell("K" + Integer.toString(index)); 	cell.setValue(thanhtien); 

							index++;
						}
						chiTietNXT.close();
					}
				}
			}
		}
		catch (Exception e) 
		{ 
			e.printStackTrace();
		}
	    
	    db.shutDown();	    
	} 
}
