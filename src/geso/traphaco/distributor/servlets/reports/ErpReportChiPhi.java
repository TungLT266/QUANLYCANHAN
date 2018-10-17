package geso.traphaco.distributor.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Types;

import Z.DB;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

import java.util.*;

public class ErpReportChiPhi extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public ErpReportChiPhi()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		Utility util = new Utility();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		
		obj.setuserId(userId);
		obj.setdiscount("1");
		obj.setvat("0");
		obj.setdvdlId("");
		obj.settype("1");
		obj.init();
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpReportChiPhi.jsp";
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
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		
		Utility util = new Utility();
		if (userId == null)
			userId = "";
		obj.setuserId(userId);
		geso.traphaco.distributor.util.Utility Ult = new geso.traphaco.distributor.util.Utility();
		
		String nppId = request.getParameter("nppId");
		nppId = Ult.getIdNhapp(userId);
		obj.setnppId(nppId);
		
		String nppTen = Ult.getTenNhaPP();
		obj.setuserTen(userTen);
		
		String khId = request.getParameter("khId"); // <!---
		if (khId == null)
			khId = "";
		obj.setkhId(khId);
		
		String nccId = request.getParameter("nccId"); // <!---
		if (nccId == null)
			nccId = "";
		
		
		String kenhId = request.getParameter("kenhId"); // <!---
		if (kenhId == null)
			kenhId = "";
		obj.setkenhId(kenhId);
		
		String dvkdId = request.getParameter("dvkdId"); // <!---
		if (dvkdId == null)
			dvkdId = "";
		obj.setdvkdId(dvkdId);
		
		String nhanhangId = request.getParameter("nhanhangId"); // <!---
		if (nhanhangId == null)
			nhanhangId = "";
		obj.setnhanhangId(nhanhangId);
		
		String chungloaiId = request.getParameter("chungloaiId");// <!---
		if (chungloaiId == null)
			chungloaiId = "";
		obj.setchungloaiId(chungloaiId);
		
		String tungay = request.getParameter("Sdays"); // <!---
		if (tungay == null)
			tungay = "";
		obj.settungay(tungay);
		
		String denngay = request.getParameter("Edays");// <!---
		if (denngay == null)
			denngay = "";
		obj.setdenngay(denngay);
		
		String khoId = request.getParameter("khoId"); // <!---
		if (khoId == null)
			khoId = "";
		obj.setkhoId(khoId);
		
		String vat = request.getParameter("vat");
		obj.setvat(vat);
		
		String discount = request.getParameter("discount");
		obj.setdiscount(discount);
		
		String instransit = request.getParameter("instransit");
		obj.setHangDiDuong(instransit);
		
		obj.setsanphamId(util.antiSQLInspection(request.getParameter("sanphamId")) != null ? util.antiSQLInspection(request.getParameter("sanphamId")) : "");
		obj.setnhanhangId(util.antiSQLInspection(request.getParameter("nganhhangId")) != null ? util.antiSQLInspection(request.getParameter("nganhhangId")) : "");
		obj.setHoaDonKmDb(util.antiSQLInspection(request.getParameter("hdkmdb")) != null ? util.antiSQLInspection(request.getParameter("hdkmdb")) : "");
		obj.setHangDiDuong(util.antiSQLInspection(request.getParameter("instransit")) != null ? util.antiSQLInspection(request.getParameter("instransit")) : "");
		
		obj.settype(util.antiSQLInspection(request.getParameter("type")) != null ? util.antiSQLInspection(request.getParameter("type")) : "");	
		obj.setLaytheo(util.antiSQLInspection(request.getParameter("laysolo")) != null ? util.antiSQLInspection(request.getParameter("laysolo")) : "");
		
		obj.setChiTietXNT_Lo(util.antiSQLInspection(request.getParameter("layctnxsolo")) != null ? util.antiSQLInspection(request.getParameter("layctnxsolo")) : "");
		
		
		
		
		String[] fieldsHien = request.getParameterValues("fieldsHien");
		obj.setFieldShow(fieldsHien);
		
		String[] fieldsAn = request.getParameterValues("fieldsAn");
		obj.setFieldHidden(fieldsAn);
		if (!tungay.equals("") && !denngay.equals(""))
		{
			
			String action = request.getParameter("action");
			
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			if (action.equals("tao"))
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "Attachment; filename=ErpReportChiPhi(NPP)" + this.getPiVotName() + ".xlsm");
				OutputStream out = response.getOutputStream();
				try
				{
					CreatePivotTable(out, response, request, fieldsHien, obj); 
					
				} catch (Exception ex)
				{
					obj.setMsg(ex.getMessage());
					request.getSession().setAttribute("errors", ex.getMessage());
					obj.init();
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);
					
					String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpReportChiPhi.jsp";
					response.sendRedirect(nextJSP);
					
				}
			}
		}else{
			obj.init();
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			
			String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpReportChiPhi.jsp";
			response.sendRedirect(nextJSP);
		}
		
	
		
	}
	
	private void CreatePivotTable(OutputStream out, HttpServletResponse response, HttpServletRequest request, String[] fieldsHien, IStockintransit obj) throws Exception
	{
		try
		{
			dbutils db=new dbutils();
			
			
			String strfstream = getServletContext().getInitParameter("path") + "\\ReportChiPhi.xlsm";
			 
			FileInputStream fstream = new FileInputStream(strfstream);
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			Worksheets worksheets = workbook.getWorksheets();
			
			Worksheet worksheet_2 = worksheets.getSheet("sheet1");
	  		worksheet_2.setName("REPORT_CHITIEN");
	  		 
				 
			 
	  		this.Dodulieu_excel(worksheet_2,obj,db);
				  
	  		Worksheet worksheet_REPORT_CHITIEN_NHOMKENH = worksheets.addSheet("REPORT_CHITIEN_NHOMKENH");
	  		this.Dodulieu_excel_Theo_NHOMKENH(worksheet_REPORT_CHITIEN_NHOMKENH,obj,db);
	  		
	  		
		  		/*worksheets = workbook.getWorksheets();
		  		Worksheet worksheet_REPORT_HOADON = worksheets.addSheet("REPORT_HOADON");*/
		  		 
		  		//this.TaoBaoCao(db,rs,worksheet_REPORT_HOADON, obj,"REPORT_HOADON",style1);
		  	 
			workbook.save(out);
			fstream.close();
		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}
		
	 private void Dodulieu_excel_Theo_NHOMKENH(
			Worksheet worksheet_2, IStockintransit obj,
			dbutils db) {
		 try{
			 

				try{ }catch(Exception er){
					
					er.printStackTrace();
					}
		
	
				
		 }catch(Exception er){
			 er.printStackTrace();
		 }
		// TODO Auto-generated method stub
		
	}

	private void Dodulieu_excel(Worksheet worksheet_2, IStockintransit obj,
			dbutils db) {
				try{
		
			  		 Cells cells = worksheet_2.getCells();
			  			cells.setRowHeight(0, 50.0f);
					    Cell cell = cells.getCell("A1");
					    ReportAPI.getCellStyle(cell, Color.RED, true, 14, "BÁO CÁO THEO DÕI CHI PHÍ");
					    
					    cell = cells.getCell("A3");
						ReportAPI.getCellStyle(cell, Color.NAVY, true, 10, "Từ ngày : " + obj.gettungay() + "   Đến ngày: " + obj.getdenngay());
						cell = cells.getCell("A4");
						ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày tạo: " + obj.getDateTime());
						
						cell = cells.getCell("B4");
						ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Tạo bởi : " + obj.getuserTen());
						
						
			  		 
			  		String query=" SELECT PK_SEQ,MA FROM ERP_CONGTY WHERE PK_SEQ <> 100000  ORDER BY SOTT ";
			  		
			  		ResultSet rs=db.get(query);
			  		
			  		String chuoingoac= "[0]";
			  		String chuoiselect ="";
			  		String macty="";
			  		while (rs.next()){
			  			
			  			String id=rs.getString("PK_SEQ");
			  			String ma=rs.getString("MA");
			  			macty= macty + (macty.length() >0?";"+id: id);
			  			
			  			chuoingoac=chuoingoac+", ["+id+"]"; 
			  			chuoiselect=chuoiselect+ " , ISNULL(CHIPHI.["+id+"],0) AS    'CHIPHI"+id+"'";
			  			
			  		}
			  		rs.close();
			  		
			  		String[] mangcty=macty.split(";");
			  		
			  		 /* query=" SELECT ISNULL(KM.TEN,'') AS NHOMCP ,CP.DIENGIAI  \n"+chuoiselect + 
				  			" FROM  ERP_NHOMCHIPHI CP   \n"+
				  			" left JOIN ERP_NHOMKHOANMUCCHIPHI KM ON KM.PK_SEQ=CP.NHOMKHOANMUCCHIPHI_FK \n"+
				  			" LEFT JOIN  \n"+
				  			" ( \n"+
				  			" SELECT  *  FROM \n"+
				  			" (  \n"+
				  			" SELECT CP.PK_SEQ AS CHIPHIID,CTY.PK_SEQ    , CP.DIENGIAI, \n"+
				  			" (D.SOLUONG* D.DONGIA)    AS TONGTIEN      \n"+
				  			" FROM ERP_MUAHANG A     \n"+
				  			" LEFT JOIN ERP_NHANVIEN NV ON NV.PK_SEQ = A.NHANVIEN_FK       \n"+
				  			" LEFT JOIN ERP_NHACUNGCAP NCC ON A.NHACUNGCAP_FK = NCC.PK_SEQ   \n"+   
				  			" INNER JOIN ERP_MUAHANG_SP D ON A.PK_SEQ = D.MUAHANG_FK  \n"+    
				  			" INNER JOIN ERP_NHOMCHIPHI CP ON D.CHIPHI_FK = CP.PK_SEQ  \n"+ 
				  			" INNER JOIN ERP_CONGTY CTY ON CTY.PK_SEQ=A.CONGTY_FK \n"+
				  			" WHERE    A.LOAIHANGHOA_FK='2'   AND A.TYPE = '1' AND A.ISDNTT = 1 \n"+
				  			" AND A.NGAYMUA >='"+obj.gettungay()+"' and A.NGAYMUA <='"+obj.getdenngay()+"' \n"+
				  			" ) P  \n"+
				  			" PIVOT  \n"+
				  			" (  \n"+
				  			" SUM(TONGTIEN) \n"+
				  			" FOR PK_SEQ IN   \n"+
				  			" ( "+chuoingoac+")  \n"+
				  			" ) AS CHIPHI \n"+
				  			" ) CHIPHI ON CHIPHI.CHIPHIID=CP.PK_SEQ \n"+
				  			" ORDER BY KM.TEN ";*/
			  			query=	 " SELECT ISNULL(CP.TEN,'') AS NHOMCP ,CP.DIENGIAI  "+ chuoiselect+
								 "   FROM  "+ 
								 " (   "+ 
								 " SELECT DISTINCT KM.TEN,CP.DIENGIAI FROM "+ 
								 "  ERP_NHOMCHIPHI CP   "+ 
								 " left JOIN ERP_NHOMKHOANMUCCHIPHI KM ON KM.PK_SEQ=CP.NHOMKHOANMUCCHIPHI_FK "+ 
								 " ) AS CP"+ 
								 " LEFT JOIN  "+ 
								 " ( "+ 
								 "  SELECT  *  FROM "+ 
								 "  (  "+ 
								 "  SELECT  CTY.PK_SEQ    , CP.DIENGIAI, "+ 
								 "  (D.SOLUONG* D.DONGIA)    AS TONGTIEN      "+ 
								 "  FROM ERP_MUAHANG A     "+ 
								 "  LEFT JOIN ERP_NHANVIEN NV ON NV.PK_SEQ = A.NHANVIEN_FK       "+ 
								 "  LEFT JOIN ERP_NHACUNGCAP NCC ON A.NHACUNGCAP_FK = NCC.PK_SEQ   "+ 
								 "  INNER JOIN ERP_MUAHANG_SP D ON A.PK_SEQ = D.MUAHANG_FK  "+ 
								 "  INNER JOIN ERP_NHOMCHIPHI CP ON D.CHIPHI_FK = CP.PK_SEQ  "+ 
								 "  INNER JOIN ERP_CONGTY CTY ON CTY.PK_SEQ=A.CONGTY_FK "+ 
								 "  WHERE    A.LOAIHANGHOA_FK='2'   AND A.TYPE = '1' AND A.ISDNTT = 1 "+ 
								 "  AND A.NGAYMUA >='"+obj.gettungay()+"' and A.NGAYMUA <='"+obj.getdenngay()+"' "+ 
								 "  ) P  "+ 
								 "  PIVOT  "+ 
								 "  (  "+ 
								 "  SUM(TONGTIEN) "+ 
								 "  FOR PK_SEQ IN   "+ 
								 "  ( "+chuoingoac+")   "+ 
								 "  ) AS CHIPHI "+ 
								 " ) CHIPHI ON CHIPHI.DIENGIAI=CP.DIENGIAI "+ 
								 " ORDER BY CP.TEN ";
			  				
			  		  		 System.out.println(query);
			  		  		
							String[] param = new String[2];
					  		param[0] = obj.gettungay().equals("") ? null : obj.gettungay();
							param[1] = obj.getdenngay().equals("") ? null : obj.getdenngay();
					 
							
			  		
			  	
					   
			  		 	 	cell = cells.getCell("AV1");
						Style style1=cell.getStyle();
						
					 	 cell = cells.getCell("AY1");
						 Style style_xanhduong=cell.getStyle();
						 cell = cells.getCell("AX1");
						 Style style_vang=cell.getStyle();
						 cell = cells.getCell("AW1");
						 
						 Style style_datroi=cell.getStyle();
					 
						 ResultSet	rsds = db.getRsByPro("GET_DOANHSO_THUAN", param);
						 int cot=3;
						 int rowindex=7;
						 // lưu doanh số này vào 1 bảng hashtable
						 
						 Hashtable<String, Double> htp_ds_cty = new Hashtable<String, Double>(); 
						 
						 double tongcong=0;
						 
						 String mang_cac_thutu_tong="";
						 
						 
						 while(rsds.next()){
							 
							
							  double doanhso=rsds.getDouble("THANHTIEN");
							  tongcong=tongcong+doanhso;
							  
							    macty=rsds.getString("MA");
							    String idcty=rsds.getString("PK_SEQ");
							  htp_ds_cty.put(idcty, doanhso);
							  
							  
							  String vitri_cot=GetExcelColumnName(cot);
							  cell = cells.getCell(vitri_cot+""+rowindex);
							  cell.setStyle(style_xanhduong);
							  cell.setValue(macty)	;
							  //vẽ %
							  cell = cells.getCell(vitri_cot+""+(rowindex+1));
							  cell.setStyle(style_xanhduong);
							  cell.setValue("%") ;
							  
							  cot++;
							  
							  vitri_cot=GetExcelColumnName(cot);
							  cell = cells.getCell(vitri_cot+""+rowindex);
							  cell.setStyle(style_xanhduong);
							  cell.setValue(doanhso)	;
							   
							//vẽ tiền
							  cell = cells.getCell(vitri_cot+""+(rowindex+1));
							  cell.setStyle(style_xanhduong);
							  cell.setValue("Tiền")	;
							  cot++;
							 
							  
						 }
						 rsds.close();
						 
						  String vitri_cot=GetExcelColumnName(cot);
						  cell = cells.getCell(vitri_cot+""+rowindex);
						  cell.setStyle(style_vang);
						  cell.setValue("TỔNG")	;
						  //vẽ %
						  cell = cells.getCell(vitri_cot+""+(rowindex+1));
						  cell.setStyle(style_vang);
						  cell.setValue("%") ;
						  
						  cot++;
						    vitri_cot=GetExcelColumnName(cot);
						  cell = cells.getCell(vitri_cot+""+rowindex);
						  cell.setStyle(style_vang);
						  cell.setValue(tongcong);
						  
						  cell = cells.getCell(vitri_cot+""+(rowindex+1));
						  cell.setStyle(style_vang);
						  cell.setValue("Tiền")	;
						  
						 
						 // THỰC HIỆN VẼ DOANH SỐ TRƯỚC
						 rowindex=9;
						 rs=db.get(query);
						 int Sott=1; 
						   cot=3;
						 
						 String manhom_chiphi="";
						 int vitri_dau_bk=9;
						  int vitrinhom=1;
						  Hashtable<String, String> htb_solama=this.getSoLaMa();
						 while (rs.next()){
							 
							 if(rowindex==9){
								 manhom_chiphi=rs.getString("NHOMCP");
								  cell = cells.getCell("A"+(rowindex));
								  cell.setStyle(style_datroi);
								  
								  cell.setValue(htb_solama.get(vitrinhom+""));
								  
								  cell = cells.getCell("B"+(rowindex));
								  cell.setStyle(style_datroi);
								  cell.setValue(rs.getString("NHOMCP"))	;
								  
								  vitri_dau_bk=rowindex;
								  
								  mang_cac_thutu_tong =mang_cac_thutu_tong+ (mang_cac_thutu_tong.length()>0? ";"+vitri_dau_bk:vitri_dau_bk) ;
								  
								  rowindex++;
								  vitrinhom++;
							 }
							 if(!manhom_chiphi.equals(rs.getString("NHOMCP"))){
								 
								  cell = cells.getCell("A"+(rowindex));
								  cell.setStyle(style_datroi);
								   
								  
								  cell.setValue(htb_solama.get(vitrinhom+""));
								  
								  
								  cell = cells.getCell("B"+(rowindex));
								  cell.setStyle(style_datroi);
								  cell.setValue(rs.getString("NHOMCP"))	;
								 
								  cot=3;
								  for(int i=0;i<mangcty.length;i++){
									  
									  	  vitri_cot=GetExcelColumnName(cot);
										  Cell cell_pt =  cells.getCell(vitri_cot+ ""+vitri_dau_bk);
										  cell_pt.setStyle(style_datroi);
										  
										  
										  cot++;
										  
										  vitri_cot=GetExcelColumnName(cot);
										  cell =  cells.getCell(vitri_cot+ ""+vitri_dau_bk);
										  cell.setStyle(style_datroi);
										  cell.setFormula("=SUM("+vitri_cot+""+(vitri_dau_bk+1)+":"+vitri_cot+"" +  String.valueOf(rowindex-1) + ")");
										   
										  macty=mangcty[i];
										  Double doanhso=htp_ds_cty.get(macty);
										  double phantram=0;
										  
											/*  if(doanhso>0){
												    phantram=giatri*100/doanhso;  
											  }*/
										  
										  if(doanhso>0){
										  cell_pt.setFormula("="+vitri_cot+""+(vitri_dau_bk)+"*100/"+ (doanhso) );
										  }else{
											  cell_pt.setValue(0);
										  }
										  
										  cot++;
								  
								  }
								  // thực hiện làm sum cho 2 dòng cuối 
								  
								  vitri_cot=GetExcelColumnName(cot);
								  Cell cell_pt =  cells.getCell(vitri_cot+ ""+vitri_dau_bk);
								  cell_pt.setStyle(style_datroi);
								  
								  
								  cot++;
								  
								  vitri_cot=GetExcelColumnName(cot);
								  cell =  cells.getCell(vitri_cot+ ""+vitri_dau_bk);
								  cell.setStyle(style_datroi);
								  cell.setFormula("=SUM("+vitri_cot+""+(vitri_dau_bk+1)+":"+vitri_cot+"" +  String.valueOf(rowindex-1) + ")");
								  
								  if(tongcong>0){
									  cell_pt.setFormula("="+vitri_cot+""+(vitri_dau_bk)+"*100/"+ (tongcong) );
									  }else{
										  cell_pt.setValue(0);
									  }
								  
								  
								  // end
								  
								  
								  
								  vitri_dau_bk=rowindex;
								  mang_cac_thutu_tong =mang_cac_thutu_tong+ (mang_cac_thutu_tong.length()>0? ";"+vitri_dau_bk:vitri_dau_bk) ;
								  
								  
								  manhom_chiphi= rs.getString("NHOMCP");
								  
								  rowindex++;
								  vitrinhom++;
								  Sott=1;
								  
								  
							 }
							  cell = cells.getCell("A"+(rowindex));
							  cell.setValue(Sott)	;
							  
							  cell = cells.getCell("B"+(rowindex));
							  cell.setValue(rs.getString("DIENGIAI"))	;
							  cot=3;
							  double tongchiphi_theohang=0;
							  
							  for(int i=0;i<mangcty.length;i++){
								  macty=mangcty[i];
								   
								  double doanhso=htp_ds_cty.get(macty);
								  
								  double chiphicty=rs.getDouble("CHIPHI"+macty);
								  tongchiphi_theohang=tongchiphi_theohang+ chiphicty;
								  
								  double phantram=0;
								   
								  if(doanhso>0){
									    phantram=chiphicty*100/doanhso;  
								  }
								  
								  vitri_cot=GetExcelColumnName(cot);
								  cell = cells.getCell(vitri_cot+""+(rowindex));
								  cell.setStyle(style1);
								  cell.setValue(phantram) ;
								  
								  cot++;
								  vitri_cot=GetExcelColumnName(cot);
								  cell = cells.getCell(vitri_cot+""+(rowindex));
								  cell.setStyle(style1);
								  cell.setValue(chiphicty) ;
								  cot++;
								  
							  }
							  // total cột cuối 
							  vitri_cot=GetExcelColumnName(cot);
							  cell = cells.getCell(vitri_cot+""+(rowindex));
							  cell.setStyle(style1);
							  cell.setValue(tongchiphi_theohang*100/tongcong) ;
							  
							  cot++;
							  vitri_cot=GetExcelColumnName(cot);
							  cell = cells.getCell(vitri_cot+""+(rowindex));
							  cell.setStyle(style1);
							  cell.setValue(tongchiphi_theohang) ;
							   
							  
							  Sott++;
							  rowindex++;
						 }
						 rs.close();
						 // thực hiện dòng cuối cùng 
						 
						 
						 
						  cot=3;
						  for(int i=0;i<mangcty.length;i++){
							  
							  	  vitri_cot=GetExcelColumnName(cot);
								  Cell cell_pt =  cells.getCell(vitri_cot+ ""+vitri_dau_bk);
								  cell_pt.setStyle(style_datroi);
								  
								  
								  cot++;
								  
								  vitri_cot=GetExcelColumnName(cot);
								  cell =  cells.getCell(vitri_cot+ ""+vitri_dau_bk);
								  cell.setStyle(style_datroi);
								  cell.setFormula("=SUM("+vitri_cot+""+(vitri_dau_bk+1)+":"+vitri_cot+"" +  String.valueOf(rowindex-1) + ")");
								   
								  macty=mangcty[i];
								  Double doanhso=htp_ds_cty.get(macty);
								  double phantram=0;
								  
									/*  if(doanhso>0){
										    phantram=giatri*100/doanhso;  
									  }*/
								  
								  if(doanhso>0){
								  cell_pt.setFormula("="+vitri_cot+""+(vitri_dau_bk)+"*100/"+ (doanhso) );
								  }else{
									  cell_pt.setValue(0);
								  }
								  
								  cot++;
								  
						  }
						  // thực hiện làm sum cho 2 dòng cuối 
						  
						  vitri_cot=GetExcelColumnName(cot);
						  Cell cell_pt =  cells.getCell(vitri_cot+ ""+vitri_dau_bk);
						  cell_pt.setStyle(style_datroi);
						  
						  
						  cot++;
						  
						  vitri_cot=GetExcelColumnName(cot);
						  cell =  cells.getCell(vitri_cot+ ""+vitri_dau_bk);
						  cell.setStyle(style_datroi);
						  cell.setFormula("=SUM("+vitri_cot+""+(vitri_dau_bk+1)+":"+vitri_cot+"" +  String.valueOf(rowindex-1) + ")");
						  
						  if(tongcong>0){
							  cell_pt.setFormula("="+vitri_cot+""+(vitri_dau_bk)+"*100/"+ (tongcong) );
							  }else{
								  cell_pt.setValue(0);
							  }
						  
						  
						  // end
						  // Thực hiện tính dòng tổng ở dưới cùng 
						  
						  
						  cell = cells.getCell("A"+(rowindex));
						  cell.setStyle(style_datroi);
						  cell.setValue("")	;
						  cell = cells.getCell("B"+(rowindex));
						  cell.setStyle(style_datroi);
						  cell.setValue("Tổng cộng");
						  
						  
						  System.out.println(mang_cac_thutu_tong);
						  
						  String[] mangcacvitri= mang_cac_thutu_tong.split(";");
						 
						  cot=3;
						  
						  for(int i=0;i<mangcty.length;i++){
					 
							  vitri_cot=GetExcelColumnName(cot);
							  cell =  cells.getCell(vitri_cot+ ""+rowindex);
							  cell.setStyle(style_datroi);
							  
							  String chuoi="";
							  		for(int k=0;k<mangcacvitri.length;k++){
										  if(k==0){
										    chuoi=vitri_cot+""+mangcacvitri[k];
										  }else{
											  chuoi=chuoi+" + " +vitri_cot+""+mangcacvitri[k];
											  
										  }
							  		}
							  		 cell.setFormula("= "+chuoi);
							  		 
							  		 
							  	  cot++;
								  vitri_cot=GetExcelColumnName(cot);
								
								  chuoi="";
							  		for( int k=0;k<mangcacvitri.length;k++){
									  if(k==0){
									    chuoi=vitri_cot+""+mangcacvitri[k];
									  }else{
										  chuoi=chuoi+" + " +vitri_cot+""+mangcacvitri[k];
									  }
								  }
								  
							  		  vitri_cot=GetExcelColumnName(cot);
									  cell =  cells.getCell(vitri_cot+ ""+rowindex);
									  cell.setStyle(style_datroi);
									  cell.setFormula("= "+chuoi);
								 
									  cot++;
								  
							  }
							  
							  // 2 cột cuối cùng
						  
						  vitri_cot=GetExcelColumnName(cot);
						  cell =  cells.getCell(vitri_cot+ ""+rowindex);
						  cell.setStyle(style_datroi);
						  
						  String chuoi="";
						  		for(int k=0;k<mangcacvitri.length;k++){
									  if(k==0){
									    chuoi=vitri_cot+""+mangcacvitri[k];
									  }else{
										  chuoi=chuoi+" + " +vitri_cot+""+mangcacvitri[k];
										  
									  }
						  		}
						  		 cell.setFormula("= "+chuoi);
						  		 
						  		 
						  	  cot++;
							  vitri_cot=GetExcelColumnName(cot);
							
							  chuoi="";
						  		for( int k=0;k<mangcacvitri.length;k++){
									  if(k==0){
									    chuoi=vitri_cot+""+mangcacvitri[k];
									  }else{
										  chuoi=chuoi+" + " +vitri_cot+""+mangcacvitri[k];
									  }
						  		 }
							  
					  		  vitri_cot=GetExcelColumnName(cot);
							  cell =  cells.getCell(vitri_cot+ ""+rowindex);
							  cell.setStyle(style_datroi);
							  cell.setFormula("= "+chuoi);
				}catch(Exception er){
					
					er.printStackTrace();
					}
		
	}

	private Hashtable<String, String> getSoLaMa(){
		  Hashtable nh = new Hashtable<String, String>();
		  
		 
				  nh.put("1", "I");nh.put("2", "II");nh.put("3", "III");nh.put("4", "IV");
				  nh.put("5", "V");nh.put("6", "VI");nh.put("7", "VII");nh.put("8", "VIII");
				  
				  nh.put("9", "IX");nh.put("10", "X");nh.put("11", "XI");nh.put("12", "XII");
				  
				  nh.put("13", "XIII");nh.put("14", "XIV");nh.put("15", "XV");nh.put("16", "XVI");
				  
				   return nh;
				  
		  
	  }
	
	public static String GetExcelColumnName(int columnNumber)
	 {
	  int dividend = columnNumber;
	  String columnName = "";
	  int modulo;

	  while (dividend > 0)
	  {
	   modulo = (dividend - 1) % 26;
	   columnName = (char)(65 + modulo) + columnName;
	   dividend = (int)((dividend - modulo) / 26);
	  } 

	  return columnName;
	 }
	
	private void TaoBaoCao(dbutils db,ResultSet rs,Worksheet worksheet, IStockintransit obj, String diengiai, Style style12) 
	{
		  try{
			  
			   Cells cells = worksheet.getCells();
			   
			   for(int i=0;i<30;i++){
				   cells.setColumnWidth(i, 20.0f);   
			   }
			
			    cells.setRowHeight(0, 50.0f);
			    Cell cell = cells.getCell("A1");
			    ReportAPI.getCellStyle(cell, Color.RED, true, 14, diengiai);
			    
			    cell = cells.getCell("A3");
				ReportAPI.getCellStyle(cell, Color.NAVY, true, 10, "Từ ngày : " + obj.gettungay() + "   Đến ngày: " + obj.getdenngay());
				cell = cells.getCell("A4");
				ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày tạo: " + obj.getDateTime());
				
				cell = cells.getCell("B4");
				ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Tạo bởi : " + obj.getuserTen());
				
				 
				

			   worksheet.setGridlinesVisible(false);
			   
			 
			   
			   ResultSetMetaData rsmd = rs.getMetaData();
			   int socottrongSql = rsmd.getColumnCount();
			   
			   int countRow = 4;

			   for( int i =1 ; i <=socottrongSql ; i ++  )
			   {
			    cell = cells.getCell(countRow,i-1 );
			    
		    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
		    	
		    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, rsmd.getColumnName(i));
		    	
			   }
			   countRow ++;
			   
			  
			   while(rs.next())
			   {
			    for(int i = 1; i <= socottrongSql; i ++)
			    {
			     cell = cells.getCell(countRow,i-1 );
			     if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i)==Types.NUMERIC || rsmd.getColumnType(i)==Types.INTEGER )
			     {
			    	 cell.setStyle(style12);
			    	 ReportAPI.getCellStyle_double(cell, "Arial", Color.BLACK, false, 9,  rs.getDouble(i));
			     // ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
			     }
			     else
			     {
			    	 ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString(i));
			    //  ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
			     }
			    }
			    ++countRow;
			   }
			   
			   if(rs!=null)rs.close();
			    

			 
			  }catch(Exception ex){
				  ex.printStackTrace();
				    
			  }
	}
 
}

