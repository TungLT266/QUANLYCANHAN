package geso.traphaco.center.servlets.nhomfocus;

import geso.traphaco.center.beans.nhomfocus.INhomfocus;
import geso.traphaco.center.beans.nhomfocus.INhomfocusList;
import geso.traphaco.center.beans.nhomfocus.imp.Nhomfocus;
import geso.traphaco.center.beans.nhomfocus.imp.NhomfocusList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;


public class NhomFocusSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PrintWriter out;

    public NhomFocusSvl() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
			INhomfocusList obj;
			request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    HttpSession session = request.getSession();	    
		    Utility util = new Utility();	    
		    String querystring = request.getQueryString();
		    String userId = util.getUserId(querystring);
		    
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId"));
		    
		    String action = util.getAction(querystring);
		    
		    String nhId = util.getId(querystring);
		    
		    obj = new NhomfocusList();
		    if (action.equals("delete"))
		    {	
		    	String msg = Delete(nhId,userId);
		    	if(msg.length() > 0)
		    		obj.setMsg(msg);
		    } else if(action.equals("chot"))
	    	{
	    		String kq=ChotChuyenkho(nhId,userId);
	    		if(kq.length() > 0)
	    		{
	    			obj.setMsg("Chốt không thành công, lỗi: "+ kq);
	    		}
	    	}
		    else  if(action.equals("huychot"))
		    {
		    	String kq=HuyChot(nhId,userId);
	    		if(kq.length() > 0)
	    		{
	    			obj.setMsg("Chốt không thành công, lỗi: "+ kq);
	    		}
		    }
		    
		    obj.setUserId(userId);
		    obj.init(""); 
		    session.setAttribute("obj", obj);
		    String nextJSP = "/TraphacoHYERP/pages/Center/NhomSkufocus.jsp";
    		response.sendRedirect(nextJSP);	
	    }
	
	private String HuyChot(String nhId, String userId)
	{
		dbutils db = new dbutils();
		try 
		{
			db.getConnection().setAutoCommit(false);
			String query="update nhomfocus set trangthai='0',NguoiHuy='"+userId+"',DateHuy=getdate() where pk_seq='"+ nhId +"' AND TRANGTHAI=1 ";
			System.out.println("[nhomfocus]"+query);
			if(db.updateReturnInt(query)!=1)
			{
				db.getConnection().rollback();
				return "Lỗi cập nhật "+query;
			}
			query=
					"INSERT INTO NHOMFOCUS_LOG(NHOMFOCUS_FK,NGUOISUA,TRANGTHAI) " +
					 " select '"+nhId+"','"+userId+"','0'  ";
			
			System.out.println("[NHOMFOCUS_LOG]"+query);
				if(!db.update(query))
				{
					db.update("rollback");
					return "Lỗi cập nhật "+query;
				}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return "";
		} 
		catch (Exception e)
		{ 
			try
			{
				db.getConnection().rollback();
			} catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			e.printStackTrace();
			return "Không thể xóa! Lỗi: " + e.getMessage(); 
		}
		finally
		{
			db.shutDown();
		}
	}


	private String getSearchQuery(HttpServletRequest request, INhomfocusList obj)
	{
		String query="select a.pk_seq,a.diengiai, a.trangthai,a.thang,a.nam,a.dvkd_fk,b.diengiai as tendvkd,c.diengiai as tenkbh,a.kbh_fk,a.doituong,NV.TEN as TENNV,a.NGAYSUA,a.NGAYTAO,NV.PK_SEQ as MANV,NV2.TEN as TENNVS,NV2.PK_SEQ as MANVS" +
 		" from nhomfocus a  inner join donvikinhdoanh b on a.dvkd_fk=b.pk_seq inner join kenhbanhang c on a.kbh_fk=c.pk_seq" +
 		" inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where 1=1 " ;
		
		
		if(!obj.getThang().equals("0"))
			query += " and a.thang = '" + obj.getThang() + "'";
		
		if(!obj.getNam().equals("0"))
			query += " and a.nam = '" + obj.getNam() + "'";
		
		if(obj.getDvkd().length() > 0)
			query += " and a.dvkd_fk ='" + obj.getDvkd() + "'";
		
		if(obj.getKenhbanhang().length() > 0)
			query += " and a.kbh_fk = '" + obj.getKenhbanhang() + "'";
		
		if(obj.getDoituong().length() > 0)
			query += " and a.doituong = '" + obj.getDoituong() + "'";
		
		if(obj.getTrangthai().length() > 0)
			query += " and a.trangthai ='" + obj.getTrangthai() + "'";
		System.out.println("cau query loc la "+query);
		return query;
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		INhomfocusList obj ;
		obj = new NhomfocusList();		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    String action = request.getParameter("action");
	    if (action == null)
	    {
	    	action = "";
	    }
	    
	    Utility util = new Utility();
	    String thang= request.getParameter("thang");
		if(thang == null)
			thang = "";
		obj.setThang(thang);
		
		String nam = request.getParameter("nam");
		if(nam == null)
			nam = "";
		obj.setNam(nam);
		
		String donvikinhdoanh = request.getParameter("donvikinhdoanh");
		if(donvikinhdoanh == null)
			donvikinhdoanh = "";
		obj.setDvkd(donvikinhdoanh);
		
		String kenhbanhang = request.getParameter("kenhbanhang");
		if(kenhbanhang == null)
			kenhbanhang = "";
		obj.setKenhbanhang(kenhbanhang);
		
		String doituong = request.getParameter("doituong");
		if(doituong == null)
			doituong = "";
		obj.setDoituong(doituong);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    
	    if(action.equals("Tao moi"))
	    {
	    	INhomfocus ckBean = new Nhomfocus();
	    	ckBean.setUserId(userId);
	    	ckBean.createRs();
	    	session.setAttribute("ckBean", ckBean);
	    	String nextJSP = "/TraphacoHYERP/pages/Center/NhomfocusNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
		    	String search = getSearchQuery(request, obj);
		    	System.out.println("nxtApprSplitting "+Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setUserId(userId);
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	String nextJSP = "/TraphacoHYERP/pages/Center/NhomSkufocus.jsp";
	    		response.sendRedirect(nextJSP);	
		    }
	    	else if (action.equals("toExcel"))
			{
				try
				{
					response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition", "attachment; filename=NhomThuong(TT).xlsm");
					OutputStream out = response.getOutputStream();
					ExportToExcel(out, obj);
					String nextJSP = "/TraphacoHYERP/pages/Center/NhomSkufocus.jsp";
					response.sendRedirect(nextJSP);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
	    	else
	    	{
		    	    	
		    	String search = getSearchQuery(request, obj);
		    	obj.init(search);
				obj.setUserId(userId);				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
	    		String nextJSP = "/TraphacoHYERP/pages/Center/NhomSkufocus.jsp";
	    		response.sendRedirect(nextJSP);	
	    	}
	    }
	}
	
	private String Delete(String nhId,String userId)
	{
		dbutils db = new dbutils();
		try 
		{
			db.getConnection().setAutoCommit(false);
			String query="delete from nhomfocus_vung where nhomfocus_fk='"+nhId+"'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Lỗi cập nhật "+query;
			}
			query=" delete from nhomfocus_sp where nhomfocus_fk='"+nhId+"' ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Lỗi cập nhật "+query;
			}
			query=" delete from nhomfocus where pk_seq='"+nhId+"' and trangthai=0 ";
			if(db.updateReturnInt(query)!=1)
			{
				db.getConnection().rollback();
				return "Lỗi cập nhật "+query;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return "";
		} 
		catch (Exception e)
		{ 
			db.update("rollback");
			e.printStackTrace();
			return "Không thể xóa! Lỗi: " + e.getMessage(); 
		}
		finally
		{
			db.shutDown();
		}
		
	}
	public String ChotChuyenkho(String nhId,String userId) 
	{
		dbutils db = new dbutils();
		try 
		{
			db.getConnection().setAutoCommit(false);
			String query="update nhomfocus set trangthai='1',NguoiSua='"+userId+"',NguoiDuyet='"+userId+"',NgaySua='"+getDateTime()+"' where pk_seq='"+ nhId +"' and trangthai=0 ";
			System.out.println("[nhomfocus]"+query);
			if(db.updateReturnInt(query)!=1)
			{
				db.update("rollback");
				return "Lỗi cập nhật "+query;
			}
			query=
				 "INSERT INTO NHOMFOCUS_LOG(NHOMFOCUS_FK,NGUOISUA,TRANGTHAI) " +
				 " select '"+nhId+"','"+userId+"','1'  ";
			System.out.println("[NHOMFOCUS_LOG]"+query);
			if(!db.update(query))
			{
				db.update("rollback");
				return "Lỗi cập nhật "+query;
			}
					
			if(!db.update(query))
			{
				db.update("rollback");
				return "Lỗi cập nhật "+query;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return "";
		} 
		catch (Exception e)
		{ 
			db.update("rollback");
			e.printStackTrace();
			return "Không thể xóa! Lỗi: " + e.getMessage(); 
		}
		finally
		{
			db.shutDown();
		}
	}
	
	
	private void ExportToExcel(OutputStream out, INhomfocusList obj) throws Exception
	{
		try
		{
			String chuoi = getServletContext().getInitParameter("path") + "\\NhomThuong(TT).xlsm";
			FileInputStream fstream = new FileInputStream(chuoi);
			Workbook workbook = new Workbook();
			workbook.open(fstream);
		    workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		    Worksheets worksheets = workbook.getWorksheets();
		    Worksheet worksheet = worksheets.getSheet("NhomThuong");		
			CreateHeader(workbook, obj);
			FillData(workbook, obj);
			workbook.save(out);
			fstream.close();
		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}

	}
	
	private void CreateHeader(Workbook workbook, INhomfocusList obj) throws Exception
	{
		try
		{
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			

			Cells cells = worksheet.getCells();
			cells.setRowHeight(0, 50.0f);
			Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 16, "NHÓM THƯỞNG");

			cell = cells.getCell("A3");ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Ngày tạo : " + getDateTime()  );
			cell = cells.getCell("A4");ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Người tạo : " +getDateTime()  );

			cell = cells.getCell("EA1");cell.setValue("DonViKinhDoanh");
			cell = cells.getCell("EB1");cell.setValue("KenhBanHang");
			cell = cells.getCell("EC1");cell.setValue("KhuVuc");
			cell = cells.getCell("ED1");cell.setValue("DoiTuong");
			cell = cells.getCell("EE1");cell.setValue("Thang");
			cell = cells.getCell("EF1");cell.setValue("Nam");
			cell = cells.getCell("EG1");cell.setValue("TenNhom");
			cell = cells.getCell("EH1");cell.setValue("TienThuong");
			
			cell = cells.getCell("EI1");cell.setValue("NganhHang");
			cell = cells.getCell("EJ1");cell.setValue("NhanHang");
			cell = cells.getCell("EK1");cell.setValue("ChungLoai");
			cell = cells.getCell("EL1");cell.setValue("MaSanPham");
			cell = cells.getCell("EM1");cell.setValue("TenSanPham");
			
		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("Khong the tao duoc Header cho bao cao.!!!");
		}
	}
	private void FillData(Workbook workbook, INhomfocusList obj) throws Exception
	{
		try
		{
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setGridlinesVisible(false);
			Cells cells = worksheet.getCells();
			dbutils db = new dbutils();

			String query =
			"select a.THANG,a.NAM,a.diengiai as NhomThuong , a.DoiTuong,a.TIENTHUONG ,b.DONVIKINHDOANH,c.DIENGIAI as Kenh, f.TEN as KhuVuc,h.TEN as NHANHANG, " +
			" 	i.TEN as CHUNGLOAI,k.ten as NGANHHANG,g.MA as spMa,g.TEN as spTen  " +
			" from nhomfocus a " +
			" 	inner join DONVIKINHDOANH b on b.PK_SEQ =a.DVKD_FK " +
			" 	inner join KENHBANHANG c on c.PK_SEQ= a.KBH_FK " +
			" 	inner join nhomfocus_vung d on d.NHOMFOCUS_FK =a.PK_SEQ " +
			" 	inner join nhomfocus_sp e on e.NHOMFOCUS_FK = a.PK_SEQ " +
			" 	inner join KHUVUC f on f.PK_SEQ= d.Vung_fk " +
			" 	inner join SANPHAM g on g.PK_SEQ = e.SANPHAM_FK " +
			" 	left join NHANHANG  h on h.PK_SEQ = g.NHANHANG_FK " +
			" 	left join CHUNGLOAI i on i.PK_SEQ = g.CHUNGLOAI_FK " +
			" 	left join NGANHHANG k on k.PK_SEQ = g.NGANHHANG_FK " +
			" where 1=1  " ;
			
			if(!obj.getThang().equals("0")  )
			{
				query += " and a.thang ='"+obj.getThang()+"' "; 
			}
			if(!obj.getNam().equals("0")  )
			{
				query += " and a.nam ='"+obj.getNam()+"' "; 
			}
			if(obj.getKenhbanhang() .length()>0  )
			{
				query += " and a.kbh_fk ='"+obj.getKenhbanhang()+"' "; 
			}
			if(obj.getTrangthai() .length()>0  )
			{
				query += " and a.trangthai ='"+obj.getTrangthai()+"' "; 
			}

			
			System.out.println("[NhomThuong]"+query);
			
			ResultSet rs = db.get(query);
			Cell cell = null;
			int countRow = 2;
			while (rs.next())
			{				
				cell = cells.getCell("EA" + String.valueOf(countRow));cell.setValue(rs.getString("DonViKinhDoanh"));
				cell = cells.getCell("EB" + String.valueOf(countRow));cell.setValue(rs.getString("Kenh"));
				cell = cells.getCell("EC" + String.valueOf(countRow));cell.setValue(rs.getString("KhuVuc"));
				
				
				String doituong ="";
				if(rs.getString("DoiTuong").equals("0"))
				{
					doituong ="SR";
				}
				else if(rs.getString("DoiTuong").equals("1"))
				{
					doituong ="SS";
				}else  if(rs.getString("DoiTuong").equals("2"))
				{
					doituong ="ASM";
				} 
				else  if(rs.getString("DoiTuong").equals("3"))
					{
						doituong ="RSM";
					} 
				cell = cells.getCell("ED" + String.valueOf(countRow));cell.setValue( doituong  );;
				cell = cells.getCell("EE" + String.valueOf(countRow));cell.setValue(rs.getString("Thang"));				
				cell = cells.getCell("EF" + String.valueOf(countRow));cell.setValue(rs.getString("Nam"));
				cell = cells.getCell("EG" + String.valueOf(countRow));cell.setValue(rs.getString("NhomThuong"));
				cell = cells.getCell("EH" + String.valueOf(countRow));cell.setValue(rs.getDouble("TienThuong"));
				cell = cells.getCell("EI" + String.valueOf(countRow));cell.setValue(rs.getString("NganhHang"));
				cell = cells.getCell("EJ" + String.valueOf(countRow));cell.setValue(rs.getString("NhanHang"));
				cell = cells.getCell("EK" + String.valueOf(countRow));cell.setValue(rs.getString("ChungLoai"));
				cell = cells.getCell("EL" + String.valueOf(countRow));cell.setValue(rs.getString("spMa"));
				cell = cells.getCell("EM" + String.valueOf(countRow));cell.setValue(rs.getString("spTen"));
				++countRow;
			}
			if (rs != null)
				rs.close();
			if (db != null)
			{
				db.shutDown();
			}
		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("Qua trinh dien du lieu vao file Excel va tao PivotTable bi loi.!!!");
		}
	}
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		return dateFormat.format(date);
	}

	
}
