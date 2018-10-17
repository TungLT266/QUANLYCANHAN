package geso.traphaco.distributor.servlets.nhomkhachhang;

import geso.traphaco.center.beans.nhomhang.INhomHang;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.nhomkhachhang.INhomkhachhangListNPP;
import geso.traphaco.distributor.beans.nhomkhachhang.INhomkhachhangNPP;
import geso.traphaco.distributor.beans.nhomkhachhang.imp.NhomkhachhangListNPP;
import geso.traphaco.distributor.beans.nhomkhachhang.imp.NhomkhachhangNPP;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
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

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class NhomkhachhangNPPUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PrintWriter out;
	
    public NhomkhachhangNPPUpdateSvl() {
        super();

    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		HttpSession session = request.getSession();
		//this.out = response.getWriter();
		dbutils db = new dbutils();
		Utility util = new Utility();
	
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
    
		System.out.println(userId);
    
		if (userId.length()==0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
    	    
		String id = util.getId(querystring);
      	
		String query = "select a.pk_seq, a.diengiai,a.ten, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from nhomkhachhangnpp a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ and a.pk_seq='" + id + "'";
		String nextJSP ="";
		ResultSet rs =  db.get(query);
		try{
			rs.next();
			String[] param = new String[10];
			param[0]= id;
			param[1]= rs.getString("diengiai");	
			param[2]= rs.getString("trangthai");
			
			param[3]= rs.getString("ngaytao");
			param[4]= rs.getString("ngaysua");
			param[5]= rs.getString("nguoitao");
			param[6]= rs.getString("nguoisua");
			param[7]= rs.getString("ten");
			OutputStream out = response.getOutputStream(); 
			INhomkhachhangNPP nkhBean = new NhomkhachhangNPP(param);
			nkhBean.setTen(param[7]);
			nkhBean.setCongtyId((String)session.getAttribute("congtyId"));
			nkhBean.setUserId(userId);
			nkhBean.UpdateRS();
			session.setAttribute("nkhBean", nkhBean);
			session.setAttribute("userId", userId);
			session.setAttribute("congtyId", session.getAttribute("congtyId"));
			System.out.println("querystring: "+querystring);
			if(querystring.indexOf("excel") > 0)
		    {
				System.out.println("vo day");
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "Attachment; filename=ReportNhomkhachhang.xlsm");
				 
				try
				{
					System.out.println("vo xuat excel: ");
					
					CreatePivotTable(out, response, request, nkhBean); 
					
				} catch (Exception ex)
				{
					nkhBean.setMessage(ex.getMessage());
					request.getSession().setAttribute("errors", ex.getMessage());
				 
					session.setAttribute("obj", nkhBean);
					session.setAttribute("userId", userId);
					
					nextJSP = "/TraphacoHYERP/pages/Distributor/NhomKhachHangNPPUpdate.jsp";
					
					
				}
		    	
		    }
			else nextJSP = "/TraphacoHYERP/pages/Distributor/NhomKhachHangNPPUpdate.jsp";
			response.sendRedirect(nextJSP);
   		
		}catch(Exception e){
			System.out.println(e.toString());
		}
   
	}  	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		INhomkhachhangNPP nkhBean = new NhomkhachhangNPP();
		
//		dbutils db;
		
		Utility util = new Utility();
		
//		db = new dbutils();
		//Get data from NhacungcapUpdate.jsp	
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		nkhBean.setUserId(userId);
		nkhBean.setCongtyId((String)session.getAttribute("congtyId"));
		
		String id = util.antiSQLInspection(request.getParameter("nkhId"));
		if(id == null){
			id = "";
		}
		nkhBean.setId(id);

		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		nkhBean.setDiengiai(diengiai);
		
		
		String ten = util.antiSQLInspection(request.getParameter("ten"));
		nkhBean.setTen(ten);


		String vungId = util.antiSQLInspection(request.getParameter("vungId"));
		nkhBean.setVungId(vungId);
	
		String kvId = util.antiSQLInspection(request.getParameter("kvId"));	
		nkhBean.setKvId(kvId);
		
		
		String loaiNpp = util.antiSQLInspection(request.getParameter("loaiNpp"));	
		nkhBean.setLoaiNpp(loaiNpp);


//		String nppId= util.antiSQLInspection(request.getParameter("nppId"));
//		nkhBean.setNppId(nppId);
	
		String ngaytao = getDateTime();
		nkhBean.setNgaytao(ngaytao);
	
		String ngaysua = ngaytao;
		nkhBean.setNgaysua(ngaysua);
	
		String nguoitao = userId;
		nkhBean.setNguoitao(userId);
	
		String nguoisua = nguoitao;
		nkhBean.setNguoisua(nguoisua);
	
		String trangthai;
		if(util.antiSQLInspection(request.getParameter("trangthai"))!= null)
			trangthai = "1";
		else
			trangthai = "0";
		nkhBean.setTrangthai(trangthai);
		
		boolean error = false;
		if (diengiai.trim().length()> 0)
			nkhBean.setDiengiai(diengiai);
		else{
			nkhBean.setMessage("Vui lòng nhập vào Nhóm khách hàng");
			error = true;
		}

		String[] khachhang = request.getParameterValues("khachhang");
		nkhBean.setKhachhang(khachhang);
	
		String action = request.getParameter("action");

		out.println(action);
	
		if (action.equals("filter") || error){		
			nkhBean.UpdateRS();
			nkhBean.setCongtyId((String)session.getAttribute("congtyId"));
			session.setAttribute("nkhBean", nkhBean);
			session.setAttribute("userId", userId);
			session.setAttribute("congtyId", session.getAttribute("congtyId"));
			
			String nextJSP;
			if (id.length()>0){
				nextJSP = "/TraphacoHYERP/pages/Distributor/NhomKhachHangNPPUpdate.jsp";
			}else{
				nextJSP = "/TraphacoHYERP/pages/Distributor/NhomKhachHangNPPNew.jsp";
			}
			response.sendRedirect(nextJSP);    		
		}else{
			session.setAttribute("userId", nguoitao);

			if (action.equals("new")){
				if (!nkhBean.saveNewNkh()){
					
					nkhBean.UpdateRS();				
					session.setAttribute("nkhBean", nkhBean);
					session.setAttribute("userId", userId);
					session.setAttribute("congtyId", session.getAttribute("congtyId"));
    		
					String nextJSP = "/TraphacoHYERP/pages/Distributor/NhomKhachHangNPPNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else{
					INhomkhachhangListNPP obj = new NhomkhachhangListNPP();
					obj.setCongtyId((String)session.getAttribute("congtyId"));
					List<INhomkhachhangNPP> nkhlist = new ArrayList<INhomkhachhangNPP>(); 
		    
					getNkhBeanList(nkhlist,"");
					obj.setUserId(userId);
				// 	Save data into session
					obj.setNkhList(nkhlist);
		    
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);
					session.setAttribute("congtyId", session.getAttribute("congtyId"));
					String nextJSP = "/TraphacoHYERP/pages/Distributor/NhomKhachHangNPP.jsp";
					response.sendRedirect(nextJSP);
			
				}
				
			}else if(action.equals("update")){
				if (!nkhBean.updateNkh()){
					nkhBean.setCongtyId((String)session.getAttribute("congtyId"));
					nkhBean.UpdateRS();				
					session.setAttribute("nkhBean", nkhBean);
					session.setAttribute("userId", userId);
					session.setAttribute("congtyId", session.getAttribute("congtyId"));
    							
					String nextJSP = "/TraphacoHYERP/pages/Distributor/NhomKhachHangNPPUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else{
					INhomkhachhangListNPP obj = new NhomkhachhangListNPP();
					obj.setCongtyId((String)session.getAttribute("congtyId"));
					List<INhomkhachhangNPP> nkhlist = new ArrayList<INhomkhachhangNPP>(); 
		    
					getNkhBeanList(nkhlist,"");
					obj.setUserId(userId);
				// 	Save data into session
					obj.setNkhList(nkhlist);
		    
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);
					session.setAttribute("congtyId", session.getAttribute("congtyId"));
					String nextJSP = "/TraphacoHYERP/pages/Distributor/NhomKhachHangNPP.jsp";
					response.sendRedirect(nextJSP);
			
				}
			}else{
				nkhBean.setCongtyId((String)session.getAttribute("congtyId"));
				nkhBean.UpdateRS();				
				session.setAttribute("nkhBean", nkhBean);
				session.setAttribute("userId", userId);
				session.setAttribute("congtyId", session.getAttribute("congtyId"));
				if(id.length()>0)
				{
					String nextJSP = "/TraphacoHYERP/pages/Distributor/NhomKhachHangNPPUpdate.jsp";
					response.sendRedirect(nextJSP);
				}else{
					String nextJSP = "/TraphacoHYERP/pages/Distributor/NhomKhachHangNPPNew.jsp";
					response.sendRedirect(nextJSP);
				}
				
			}
		}
	
	}   	  	    
	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}

	private void  getNkhBeanList(List<INhomkhachhangNPP> nkhlist, String search){	
		String query;
    
		dbutils db = new dbutils();
		if (search.length() > 0){
			query = search;
		}else{
			query = "select a.ten,a.pk_seq, a.diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua from nhomkhachhangnpp a, nhanvien b, nhanvien c where a.nguoitao = b.PK_SEQ and a.nguoisua = c.PK_SEQ order by diengiai";
		}
		ResultSet rs = db.get(query);
		try{	
			String[] param = new String[11];
			INhomkhachhangNPP nkhBean;
			while (rs.next()){	    			
				param[0]= rs.getString("pk_seq");
				param[1]= rs.getString("diengiai");				
				param[2]= rs.getString("trangthai");
				param[3]= rs.getString("ngaytao");
				param[4]= rs.getString("ngaysua");
				param[5]= rs.getString("nguoitao");
				param[6]= rs.getString("nguoisua");			
			
				nkhBean = new NhomkhachhangNPP(param);	
				nkhBean.setTen(rs.getString("ten"));
				nkhlist.add(nkhBean);
			}    		
			db.shutDown();
		}catch(Exception e){}
	}

	private void CreatePivotTable(OutputStream out, HttpServletResponse response, HttpServletRequest request,  INhomkhachhangNPP obj) throws Exception
	{
		try
		{
			
			String strfstream = getServletContext().getInitParameter("path") + "\\Nhomhang.xlsx";
			 
			FileInputStream fstream = new FileInputStream(strfstream);
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			Worksheets worksheets = workbook.getWorksheets();
	  		Worksheet worksheet_2 = worksheets.getSheet("sheet1");
	  		worksheet_2.setName("Nhomkhachhang");
	  		 Cells cells = worksheet_2.getCells();
			   
	  		 	Cell	cell = cells.getCell("P1");
				Style style1=cell.getStyle();
				 
				dbutils db = new dbutils();
				Utility util = new Utility();
				String query =   " SELECT ROW_NUMBER() OVER (ORDER BY ISNULL(NPP.MAFAST,KH.MAFAST)) AS 'SOTT' , ISNULL(NPP.MAFAST,KH.MAFAST) AS MAFAST,ISNULL(NPP.TEN,KH.TEN) AS TEN,ISNULL(kh.DIACHI,'') AS DIACHI FROM NHOMKHACHHANGNPP_NPP NKH_NPP LEFT JOIN "+util.prefixDMS+"NHAPHANPHOI NPP ON NPP.PK_SEQ=NKH_NPP.NPP_FK and NKH_NPP.LOAINPP in (1,2)  " +
								" LEFT JOIN "+util.prefixDMS+"KHACHHANG kh on kh.pk_seq = NKH_NPP.NPP_FK and NKH_NPP.LOAINPP=3 \n"+
								" WHERE NHOMKHACHHANGNPP_FK="+obj.getId()+" \n";
								//" order by ISNULL(NPP.MAFAST,KH.MAFAST) asc \n";
				System.out.println("query: "+ query);
				ResultSet	rs = db.get(query);
		  		worksheets = workbook.getWorksheets();
		  		 
		  		this.TaoBaoCao(db,rs,worksheet_2, obj,"",style1);
		  		 
		  	 
			workbook.save(out);
			fstream.close();
		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}
	
	private void TaoBaoCao(dbutils db,ResultSet rs,Worksheet worksheet, INhomkhachhangNPP obj, String diengiai, Style style12) 
	{
		  try{
			  
			   Cells cells = worksheet.getCells();
			   cells.setColumnWidth(0, 7.14f);
				cells.setColumnWidth(1, 16.29f);
				cells.setColumnWidth(2, 50.71f);
				cells.setColumnWidth(3, 50.71f);

				
			   
			   geso.traphaco.center.util.Utility uti = new geso.traphaco.center.util.Utility();
			    cells.setRowHeight(0, 50.0f);
			    String query = "select ISNULL(TEN,'') AS TEN from NHOMKHACHHANGNPP where PK_SEQ =  '"+obj.getId()+"' ";
			    String tennhomkhachhang = "";
			    ResultSet rscongty  = db.get(query);
			    while(rscongty.next())
			    {
			    	tennhomkhachhang = rscongty.getString("TEN");
			    }
			    rscongty.close();
			    Cell cell = cells.getCell("C4");
			    ReportAPI.getCellStyle(cell, Color.BLACK, true, 16, "BÁO CÁO NHÓM KHÁCH HÀNG HÀNG",1);
			    cell = cells.getCell("C5");
			    ReportAPI.getCellStyle(cell, Color.BLACK, true, 16,"Nhóm khách hàng: "+ tennhomkhachhang,0);
			    cell = cells.getCell("A7");
			    ReportAPI.getCellStyle(cell, Color.BLACK, true, 16, "Số TT",0);
			    cell = cells.getCell("B7");
			    ReportAPI.getCellStyle(cell, Color.BLACK, true, 16, "Mã khách hàng",0);
			    cell = cells.getCell("C7");
			    ReportAPI.getCellStyle(cell, Color.BLACK, true, 16, "Tên khách hàng",0);
			    cell = cells.getCell("D7");
			    ReportAPI.getCellStyle(cell, Color.BLACK, true, 16, "Địa chỉ",0);
			   worksheet.setGridlinesVisible(false);
			   
			 
			   
			   ResultSetMetaData rsmd = rs.getMetaData();
			   int socottrongSql = rsmd.getColumnCount();
			   
			   int countRow = 7;

			  
			   
			   NumberFormat formatter = new DecimalFormat("#,###,###");
			   while(rs.next())
			   {
				   Color color_=Color.BLACK;
				     /* if(rs.getDouble(2) <rs.getDouble(1) &&  rs.getDouble(20) != 0  ){
				    	  color_=Color.RED;
				      }*/
			    for(int i = 1; i <= socottrongSql; i ++)
			    {
			     
			     cell = cells.getCell(countRow,i-1);
			     if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i)==Types.NUMERIC || rsmd.getColumnType(i)==Types.INTEGER )
			     {
			    
			    	 //cell.setStyle(style1);
			    	 ReportAPI.getCellStyle_double(cell, "Times New Roman", color_, false, 9,  rs.getDouble(i));
			   
			     }
			     else
			     {
			    	 ReportAPI.getCellStyle(cell, "Times New Roman", color_, false, 9, rs.getString(i));
			   
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
