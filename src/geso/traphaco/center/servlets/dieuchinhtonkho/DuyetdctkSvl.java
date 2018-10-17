package geso.traphaco.center.servlets.dieuchinhtonkho;

import geso.traphaco.center.beans.dieuchinhtonkho.IDieuchinhtonkho;
import geso.traphaco.center.beans.dieuchinhtonkho.IDieuchinhtonkhoList;
import geso.traphaco.center.beans.dieuchinhtonkho.imp.Dieuchinhtonkho;
import geso.traphaco.center.beans.dieuchinhtonkho.imp.DieuchinhtonkhoList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.util.Calendar;

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

public class DuyetdctkSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

       
    public DuyetdctkSvl() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    OutputStream out = response.getOutputStream();
		IDieuchinhtonkhoList obj;
		String userId = "";	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	  
	    
	    String querystring = request.getQueryString();
	    String action = util.getAction(querystring);
//	    out.println(action);
	    
	    String dctkId = util.getId(querystring);
	    
	    if (userId.length()==0)
	    	userId = request.getParameter("userId");

	    System.out.println("action la :"+action);
	    if(action.equals("approve")){
	    	Duyet(dctkId, userId);
	    }
	    
	    userId = util.getUserId(querystring);
	    
	    obj = new DieuchinhtonkhoList();
	    obj.setUserId(userId);
	    obj.init0();
	    obj.createDctklist("");
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Center/DuyetDieuChinhTonKho.jsp";
		response.sendRedirect(nextJSP);
	}  	
	
	private void CreateStaticHeader(Workbook workbook) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	   	
	    cells.setRowHeight(0, 20.0f);
	    Cell cell = cells.getCell("A1"); 
	    ReportAPI.getCellStyle(cell, Color.RED, true, 16, "DANH SÁCH SẢN PHẨM ĐIỀU CHỈNH");
	  
	    //cell = cells.getCell("A4");
	    //cell.setValue("User:  " + UserName);
	    
	    //tieu de
	    cell = cells.getCell("A3");			 cell.setValue("Số Phiếu");
	    cell = cells.getCell("B3");			 cell.setValue("Đơn vị kinh doanh");	   
	    cell = cells.getCell("C3");			 cell.setValue("Kho");	    
	    cell = cells.getCell("D3");			 cell.setValue("Kênh");	    
	    cell = cells.getCell("E3");			 cell.setValue("Sitecode");	    
	    cell = cells.getCell("F3");		 	 cell.setValue("Nhà phân phối");	   
	    cell = cells.getCell("G3");			 cell.setValue("Mã sản phẩm");	    
	    cell = cells.getCell("H3");			 cell.setValue("Tên sản phẩm");	    
	    cell = cells.getCell("I3");			 cell.setValue("Điều chỉnh");	    
	    cell = cells.getCell("J3");			 cell.setValue("Đơn vị");	    
	    cell = cells.getCell("K3");			 cell.setValue("Giá mua");	    
	    cell = cells.getCell("L3");			 cell.setValue("Thành tiền");	   
	    cell = cells.getCell("M3");			 cell.setValue("Tồn hiện tại");	    
	    cell = cells.getCell("N3");			 cell.setValue("Tồn mới");	    	    
	    //worksheet.setName("SẢN PHẨM ĐIỀU CHỈNH");
	}
	
	private void CreateStaticData(Workbook workbook,String dctkid) 
	{
		String query="select dctk.pk_seq,kbh.diengiai as kbh, dvkd.diengiai as dvkd, kho.pk_Seq, kho.ten as kho, npp.sitecode,npp.ten as nppten "+ 
			" ,sp.ma,sp.ten spten,dctk_sp.* "+
			"  from dieuchinhtonkho dctk "+
			" inner join dieuchinhtonkho_sp dctk_sp on dctk.pk_seq=dctk_sp.dieuchinhtonkho_fk "+
			" inner join sanpham sp on sp.pk_seq=sanpham_fk "+
			" inner join kho  on kho.pk_seq=dctk.kho_fk "+
			" inner join donvikinhdoanh dvkd on  dctk.dvkd_fk=dvkd.pk_seq "+
			" inner join kenhbanhang kbh on kbh.pk_Seq=dctk.kbh_fk "+
			" inner join nhaphanphoi npp on npp.pk_seq=dctk.npp_fk "+
			" where dctk.pk_seq = " + dctkid;
		
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    
		dbutils db = new dbutils();
		ResultSet rs = db.get(query);
		System.out.println("1.Get san pham dieu chinh:"+query);
		int i = 4;
		if(rs != null)
		{
			try 
			{
				cells.setColumnWidth(0, 25.0f);
				cells.setColumnWidth(1, 15.0f);
				cells.setColumnWidth(2, 15.0f);
				cells.setColumnWidth(3, 30.0f);
				cells.setColumnWidth(4, 45.0f);
				cells.setColumnWidth(5, 25.0f);
				cells.setColumnWidth(6, 15.0f);
				cells.setColumnWidth(7, 15.0f);
				cells.setColumnWidth(8, 15.0f);
				cells.setColumnWidth(2, 15.0f);
				cells.setColumnWidth(3, 30.0f);
				cells.setColumnWidth(4, 45.0f);
				cells.setColumnWidth(5, 25.0f);
				cells.setColumnWidth(6, 15.0f);
				cells.setColumnWidth(7, 15.0f);
				cells.setColumnWidth(8, 15.0f);
				for(int j = 0; j < 9; j++)
					cells.setRowHeight(j, 13.0f);
				
				Cell cell = null;
				while(rs.next())
				{
					cell = cells.getCell("A" + Integer.toString(i));
					cell.setValue(rs.getString("pk_seq"));
					cell = cells.getCell("B" + Integer.toString(i));
					cell.setValue(rs.getString("dvkd"));
					cell = cells.getCell("C" + Integer.toString(i));
					cell.setValue(rs.getString("kho"));
					cell = cells.getCell("D" + Integer.toString(i));
					cell.setValue(rs.getString("kbh"));
					cell = cells.getCell("E" + Integer.toString(i));
					cell.setValue(rs.getString("sitecode"));
					cell = cells.getCell("F" + Integer.toString(i));
					cell.setValue(rs.getString("nppten"));
					cell = cells.getCell("G" + Integer.toString(i));
					cell.setValue(rs.getString("ma"));
					cell = cells.getCell("H" + Integer.toString(i));
					cell.setValue(rs.getString("spten"));
					cell = cells.getCell("I" + Integer.toString(i));
					cell.setValue(rs.getFloat("dieuchinh"));
					cell = cells.getCell("J" + Integer.toString(i));
					cell.setValue(rs.getString("donvi"));
					cell = cells.getCell("K" + Integer.toString(i));
					cell.setValue(rs.getFloat("giamua"));					
					cell = cells.getCell("L" + Integer.toString(i));
					cell.setValue(rs.getFloat("thanhtien"));					
					cell = cells.getCell("M" + Integer.toString(i));
					cell.setValue(rs.getFloat("tonhientai"));					
					cell = cells.getCell("N" + Integer.toString(i));
					
					float tonmoi = rs.getFloat("tonhientai") + rs.getFloat("dieuchinh");
					//cell.setValue(rs.getFloat("tonmoi"));		
					cell.setValue(tonmoi);	
					i++;
				}
				rs.close();
			}
			catch(Exception e){
				e.printStackTrace();
			System.out.println("Loi Nek :"+e.toString());	
			}
		}
	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		 OutputStream out = response.getOutputStream();
		IDieuchinhtonkhoList obj;
			
		String userId = request.getParameter("userId");
		 obj = (IDieuchinhtonkhoList) new DieuchinhtonkhoList();
	    if(userId == null){
	    	response.sendRedirect("/SalesUp");
	    }else{
	    	obj.setUserId(userId);
	    }
		    
	    String 	action = request.getParameter("action");
	   
	    
	    String	id = request.getParameter("id");
		
	    
	    if(action.equals("approve"))
	    {
	    	String loi	=	Duyet(id, userId);
	    	if(!loi.trim().equals("")){
	    		IDieuchinhtonkho dctkBean = (IDieuchinhtonkho) new Dieuchinhtonkho();
	    		dctkBean.setUserId(userId);
	    		dctkBean.setId(id);
	    		dctkBean.setMessage(loi);
	        	dctkBean.initDisplay();
	        	session.setAttribute("dctkBean", dctkBean);
	        	String nextJSP = "/TraphacoHYERP/pages/Center/DieuChinhTonKhoDisplay.jsp";
	        	response.sendRedirect(nextJSP);    	
	    	}else{
	    		  obj = new DieuchinhtonkhoList();
	    		    obj.setUserId(userId);
	    		    obj.init0();
	    		    obj.createDctklist("");
	    			session.setAttribute("obj", obj);
	    					
	    			String nextJSP = "/TraphacoHYERP/pages/Center/DuyetDieuChinhTonKho.jsp";
	    			response.sendRedirect(nextJSP);
	    	}
	    }
	    else if(action.equals("excel")){
	
	    	IDieuchinhtonkho dctkBean = (IDieuchinhtonkho) new Dieuchinhtonkho();
	    
	    	try
		    {
	    		response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=SanPhamDieuChinh.xlsm");
				
				FileInputStream fstream = null;
				Workbook workbook = new Workbook();		
				
				fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ErpDieuChinhTonKho.xlsm");

				workbook.open(fstream);
				workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		    	
			     CreateStaticHeader(workbook);
			     CreateStaticData(workbook,id);
			     
			     //Saving the Excel file
			     workbook.save(out);
		    }
		    catch (Exception ex)
		    {
		    	dctkBean.setMessage("Khong Xuat Ra Excel Duoc");
		    }
	    	
			
			dctkBean.setUserId(userId);
			dctkBean.setId(id);
			
	    	dctkBean.initDisplay();
	    	session.setAttribute("dctkBean", dctkBean);
	    	String nextJSP = "/TraphacoHYERP/pages/Center/DieuChinhTonKhoDisplay.jsp";
	    	response.sendRedirect(nextJSP);   
	    	return;
	    }
	    else{
	        
	        Utility util = new Utility();
	    	String dvkdId =  util.antiSQLInspection(request.getParameter("dvkdId"));   
	        if(dvkdId == null){  	
	        	dvkdId = "";
	        }
	        obj.setDvkdId(dvkdId);	        
	
	        String kbhId = util.antiSQLInspection(request.getParameter("kbhId"));
	        if (kbhId == null){
	        	kbhId = "";
	        }
	        obj.setKbhId(kbhId);
	        
	        String khoId = util.antiSQLInspection(request.getParameter("khoId"));
	        if (khoId == null){
	        	khoId = "";
	        }
	        
	        obj.setKhoId(khoId);
	        
	        String tungay = util.antiSQLInspection(request.getParameter("tungay"));
	        if (tungay == null){
	        	tungay = "";
	        }
	        obj.setTungay(tungay);
	        
	        String denngay = util.antiSQLInspection(request.getParameter("denngay"));
	        if (denngay == null){
	        	denngay = "";
	        }
	        obj.setDenngay(denngay);
	        obj.init0();
	        obj.createDctklist(obj.createQueryString());
	    	session.setAttribute("obj", obj);
	//    	out.print(obj.createQueryString());
	    	
	    	String nextJSP = "/TraphacoHYERP/pages/Center/DuyetDieuChinhTonKho.jsp";
	    	response.sendRedirect(nextJSP);
	        }
	}
	
	private boolean CheckNgayDuyet(String nppId, String ngayduyet)
	{
		Utility util = new Utility();
		String ngayksgn = util.ngaykhoaso(nppId);
		
		//System.out.print("\nNgay khoa so gan nhat la: " + this.ngaychungtu + "\n");
		
		if(ngayksgn.equals(""))
			return false;
		
		String[] ngayks = ngayksgn.split("-");
		String[] ngayct = ngayduyet.split("-");
		
		//System.out.print("\nNgay chung tu la: " + this.ngaychungtu + "\n");
		
		Calendar c1 = Calendar.getInstance(); //new GregorianCalendar();
		Calendar c2 = Calendar.getInstance(); //new GregorianCalendar();
	
		//NGAY KHOA SO
		
		c1.set(Integer.parseInt(ngayks[0]), Integer.parseInt(ngayks[1]) - 1, Integer.parseInt(ngayks[2]));
	
		//NGAY thuc hien tra hang
		c2.set(Integer.parseInt(ngayct[0]), Integer.parseInt(ngayct[1]) - 1, Integer.parseInt(ngayct[2]));
	
		c1.add(Calendar.DATE, 1);//ngay tra don hang bang ngay khoa so them 1 ngay
		//phep tinh ngay nhan hang - ngay khoa so
			
		long songay = ( c1.getTime().getTime() - c2.getTime().getTime()) / (24 * 3600 * 1000);
		   
		if(songay < 0) //ngay chung tu khong duoc nho hon hoac bang ngay khoa so gan nhat 
		{
			//this.msg = "Ngay Tra Don Hang Phai Lon Hon Ngay Khoa So Gan Nhat.";
			return false;
		}
		return true;
	}
	
	private String  Duyet(String dctkId, String userId)
	{		
		dbutils db = new dbutils();
		String query = "select a.npp_fk as nppId, a.kbh_fk as kbhId, a.kho_fk as khoId, b.sanpham_fk as spId, b.dieuchinh as soluong from dieuchinhtonkho a, dieuchinhtonkho_sp b where a.pk_seq = b.dieuchinhtonkho_fk and b.dieuchinhtonkho_fk='" + dctkId + "'";
		ResultSet rs = db.get(query);
		try{
			db.getConnection().setAutoCommit(false);
			String nppId="";
			while(rs.next()){
				
				 nppId = rs.getString("nppId");
				String kbhId = rs.getString("kbhId");
				String khoId = rs.getString("khoId");
				String spId = rs.getString("spId");
				String sl = rs.getString("soluong");
				long num = Long.valueOf(sl).longValue(); 
				if(num < 0){
					num = num*(-1);
					query = "update nhapp_kho set soluong=soluong-" + num + ", available = available-" + num + "  where npp_fk = '" + nppId + "' and kbh_fk = '" + kbhId + "' and kho_fk = '" + khoId + "' and sanpham_fk = '" + spId + "'";
					System.out.print("day nay 1 "+query);
				}else{
					query = "update nhapp_kho set soluong=soluong+" + num + ", available = available+" + num + " where npp_fk = '" + nppId + "' and kbh_fk = '" + kbhId + "' and kho_fk = '" + khoId + "' and sanpham_fk = '" + spId + "'";
					System.out.print("day nay 2 "+query);
				}
				
			if(!db.update(query)){
				db.update("rollback");
				return "Khong The Duyet Dieu Chinh Ton Kho,Xay Ra Loi Cap Nhat Sau :"+ query;
				
			}
			}
			
			rs.close();
			String ngayksthem1="";
			 query="select Replace(convert(char(10), DATEADD(day, 1, cast(max(ngayks) as datetime)) , 102) , '.', '-' ) as ngay from khoasongay where npp_fk=(select npp_fk from dieuchinhtonkho where pk_Seq="+dctkId+")";
			
			 System.out.println(query);
			
			 ResultSet rs1=db.get(query);
			if(rs1!=null){
				if(rs1.next()){
					ngayksthem1=rs1.getString("ngay");
					System.out.println("Ngay Khoa So Them " +ngayksthem1);
			 	}else{
			 		db.update("rollback");		
					return "I. Khong The Duyet Dieu Chinh Ton Kho,Xay Ra Loi Cap Nhat Sau :"+ query;
			 	}
			}else{
				db.update("rollback");		
				return "II.Khong The Duyet Dieu Chinh Ton Kho,Xay Ra Loi Cap Nhat Sau :"+ query;
			}
			rs1.close();
			query = "update dieuchinhtonkho set ngaydc='"+ngayksthem1+"',trangthai='1', nguoiduyet = '" +userId+ "' where pk_seq = '" + dctkId + "'";
	
			if(!db.update(query)){
				db.update("rollback");		
				return "Khong The Duyet Dieu Chinh Ton Kho,Xay Ra Loi Cap Nhat Sau :"+ query;
		
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}catch(Exception e){
			db.update("rollback");		
			return "Khong The Duyet Dieu Chinh Ton Kho,Xay Ra Loi Cap Nhat Sau :"+ e.toString();
	
		}
		db.shutDown();
		return "";
		
	}
	}
