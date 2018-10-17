package geso.traphaco.distributor.servlets.reports;

import geso.traphaco.distributor.beans.khotonghop.*;
import geso.traphaco.distributor.beans.khotonghop.imp.*;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Types;

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

public class BcKhoTongHopSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public BcKhoTongHopSvl()
	{
		super();
		
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IKhotonghop obj = new Khotonghop();
		Utility util = new Utility();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		
		obj.setuserId(userId);
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Distributor/BcKhoTongHop.jsp";
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
		IKhotonghop obj = new Khotonghop();
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
		
		/*String thang = request.getParameter("thang"); // <!---
		if (thang == null)
			thang = "";
		obj.setThang(thang);
		
		String nam = request.getParameter("nam"); // <!---
		if (nam == null)
			nam = "";
		obj.setNam(nam);*/
		
		String tungay = request.getParameter("tungay"); // <!---
		if (tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = request.getParameter("denngay"); // <!---
		if (denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		if (!tungay.equals("") && !denngay.equals(""))
		{
			
			String action = request.getParameter("action");
			
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			if (action.equals("tao"))
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "Attachment; filename=BcKhoTongHop" + this.getPiVotName() + ".xlsm");
				OutputStream out = response.getOutputStream();
				try
				{
					CreatePivotTable(out, response, request, obj); 
					
				} catch (Exception ex)
				{
					obj.setMsg(ex.getMessage());

					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);
					String nextJSP = "/TraphacoHYERP/pages/Distributor/BcKhoTongHop.jsp";
					response.sendRedirect(nextJSP);
					
				}
			}else{
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
				
				String nextJSP = "/TraphacoHYERP/pages/Distributor/BcKhoTongHop.jsp";
				response.sendRedirect(nextJSP);
			}
		}
		else
		{
			obj.setMsg("Vui lòng chọn thời gian lấy báo cáo");
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			
			String nextJSP = "/TraphacoHYERP/pages/Distributor/BcKhoTongHop.jsp";
			response.sendRedirect(nextJSP);
		}
	}
	
	private void CreatePivotTable(OutputStream out, HttpServletResponse response, HttpServletRequest request, IKhotonghop obj) throws Exception
	{
		try
		{
			
			String strfstream = getServletContext().getInitParameter("path") + "\\BcKhoTongHop.xlsm";
			
			FileInputStream fstream = new FileInputStream(strfstream);
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet_tn = worksheets.getSheet("TrongNuoc");
			
	  		this.TaoBaoCao(worksheet_tn, obj, "1");
	  		
			Worksheet worksheet_nk = worksheets.getSheet("NhapKhau");
			
	  		this.TaoBaoCao(worksheet_nk, obj, "0");
				
			workbook.save(out);
			fstream.close();
		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}
	
	private String getQuery(IKhotonghop obj, String loai)
	{
		dbutils db = new dbutils();
		String query = "select distinct sp.PK_SEQ, sp.ten from sanpham sp inner join ERP_NHANHANG_SANPHAM nh_sp on sp.PK_SEQ = nh_sp.SANPHAM_FK "+
				   "inner join ERP_NHANHANG nh on nh_sp.NHANHANG_FK = nh.PK_SEQ inner join ERP_MUAHANG mh on nh.MUAHANG_FK = mh.PK_SEQ "+
				   //"WHERE month(nh.NGAYNHAN) = "+obj.getThang()+" AND year(nh.NGAYNHAN) = "+obj.getNam()+" AND NH.TRANGTHAI in ('1')";
				   "WHERE nh.NGAYNHAN >= '"+obj.getTungay()+"' AND nh.NGAYNHAN <= '"+obj.getDenngay()+"' AND NH.TRANGTHAI in ('1') and mh.LOAI = '"+loai+"'";
		System.out.println("[SP] "+query);
		ResultSet rs=db.get(query);
		String chuoi="";
		String[] arraychuoi= new String[1000];
		String chuoiselct=" '' ";
		String chuoingoac="[0]";//co dau []
		if(rs!=null){
			int i=0;
			try {
				while (rs.next()){
	
					if(i==0){
						chuoingoac="["+rs.getString("ten")+"]";
						chuoi=rs.getString("ten");
						chuoiselct="isnull(ctBan.["+rs.getString("ten")+"],0) AS ctBan"+rs.getString("ten")+",ISNULL(tdBan.["+rs.getString("ten")+"],0) AS tdBan"+rs.getString("ten");
					}else{
						chuoi=chuoi+","+rs.getString("ten");
						chuoiselct=chuoiselct+", isnull(ctBan.["+rs.getString("ten")+"],0) AS ctBan"+rs.getString("ten")+",ISNULL(DS.["+rs.getString("ten")+"],0) AS tdBan"+rs.getString("ten");
						chuoingoac=chuoingoac+",["+rs.getString("ten")+"]";
					}
					arraychuoi[i]=rs.getString("ten");
					i++;
	
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		query = "select * from "+
				"\n (		"+
				"\n	SELECT sp.ten as spten, v.DIENGIAI, NGAYNHAN, SUM(ISNULL(SOLUONGNHAN, '0')) AS SOLUONG "+
				"\n	FROM ERP_NHANHANG_SANPHAM NH_SP "+
				"\n	INNER JOIN ERP_NHANHANG NH ON NH.PK_SEQ=NH_SP.NHANHANG_FK "+
				"\n inner join ERP_MUAHANG mh on nh.MUAHANG_FK = mh.PK_SEQ "+
				"\n	inner join sanpham sp on NH_SP.SANPHAM_FK = sp.PK_SEQ "+
				"\n	inner join NHAPHANPHOI_VUNG npp_v on nh.NHAPHANPHOI_FK = npp_v.NPP_FK "+
				"\n	inner join VUNG v on npp_v.VUNG_FK = v.PK_SEQ "+
				//"\n	WHERE month(NH.NGAYNHAN) = "+obj.getThang()+" AND year(NH.NGAYNHAN) = "+obj.getNam()+" AND NH.TRANGTHAI in ('1') "+
				"\n WHERE nh.NGAYNHAN >= '"+obj.getTungay()+"' AND nh.NGAYNHAN <= '"+obj.getDenngay()+"' AND NH.TRANGTHAI in ('1') "+
				"\n	and mh.LOAI = '"+loai+"' AND NHAPHANPHOI_FK in ( "+
				"\n	select a.PK_SEQ from NHAPHANPHOI a inner join NHAPHANPHOI_VUNG b on a.PK_SEQ = b.NPP_FK "+
				"\n	inner join VUNG c on b.VUNG_FK = c.PK_SEQ where loaiNPP = 0) "+
				"\n	GROUP BY sp.ten, v.DIENGIAI, NGAYNHAN "+	
				"\n ) as t pivot  ( sum(SoLuong) for spten in ( "+chuoingoac+" ) ) as t";
		System.out.println("[query] "+query);
		return query;
	}
	
	private void TaoBaoCao(Worksheet worksheet, IKhotonghop obj, String loai) 
	{
		try{
			Cells cells = worksheet.getCells();
			   
			for(int i=0;i<30;i++){
				cells.setColumnWidth(i, 10.0f);   
			}
			cells.setColumnWidth(3, 18.0f);
			cells.setColumnWidth(5, 18.0f);
		   
			cells.setRowHeight(0, 50.0f);
			
			/*cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 10, "Tháng: " + obj.getThang());
			cell = cells.getCell("B3");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Năm: " + obj.getNam());*/
			Cell cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Tạo bởi : " + obj.getuserTen());
			
			cell = cells.getCell("P1");
			Style style1=cell.getStyle();
			
			cell = cells.getCell("O1");
			Style style2=cell.getStyle();
			
			dbutils db = new dbutils();
			
			String query = getQuery(obj, loai);
			ResultSet rs = db.get(query);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();
			int countRow = 4;

			for(int i = 2; i <= socottrongSql; i++)
			{
				cell = cells.getCell(countRow, i-2);
				Style s = cell.getStyle();
				s.setTextWrapped(true);
				s.setHAlignment(TextAlignmentType.CENTER);
				s.setVAlignment(TextAlignmentType.JUSTIFY);
				cell.setStyle(s);
				ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
				
				ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, rsmd.getColumnName(i));
			}
			countRow ++;
			
			String mien = "", miencur = "";
	   
			//NumberFormat formatter = new DecimalFormat("#,###,###");
			while(rs.next())
			{
				miencur = rs.getString(1);
				if(!mien.equals(miencur))
				{
					cell = cells.getCell(countRow, 0);
					cells.merge(countRow, 0, countRow, socottrongSql-2);
					Style s = cell.getStyle();
					s.setHAlignment(TextAlignmentType.CENTER);
					s.setVAlignment(TextAlignmentType.JUSTIFY);
					cell.setStyle(s);
					ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, miencur.toUpperCase());
					mien = miencur;
					countRow++;
				}
				for(int i = 2; i <= socottrongSql; i ++)
				{
					cell = cells.getCell(countRow, i-2);
					if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i)==Types.INTEGER )
					{
						cell.setStyle(style1);
						ReportAPI.getCellStyle_double(cell, "Arial", Color.BLACK, false, 9,  rs.getDouble(i));
						// ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
					}
					else
					{
						cell.setStyle(style2);
						ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString(i));
						// ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					}
				}
				++countRow;
			}
			
			if(rs!=null)rs.close();
			
			cell = cells.getCell("A1");
			cells.merge(0, 0, 0, socottrongSql-2);
			Style s = cell.getStyle();
			s.setHAlignment(TextAlignmentType.CENTER);
			s.setVAlignment(TextAlignmentType.JUSTIFY);
			cell.setStyle(s);
			ReportAPI.getCellStyle(cell, Color.RED, true, 14, "BẢNG THEO DÕI ĐẶT HÀNG + NHẬN HÀNG");
			db.shutDown();

		}catch(Exception ex){
			ex.printStackTrace();
		}
	}



}

