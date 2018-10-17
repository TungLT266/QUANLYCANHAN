package geso.traphaco.distributor.servlets.dangkytrungbay;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.beans.dangkytrungbay.IDangkytrungbay;
import geso.traphaco.distributor.beans.dangkytrungbay.IDangkytrungbayList;
import geso.traphaco.distributor.beans.dangkytrungbay.imp.Dangkytrungbay;
import geso.traphaco.distributor.beans.dangkytrungbay.imp.DangkytrungbayList;
import geso.traphaco.distributor.util.Utility;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import com.oreilly.servlet.MultipartRequest;

public class DangkytrungbayUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
    public DangkytrungbayUpdateSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
		
		IDangkytrungbay dktbBean;
		PrintWriter out;   
		
		
		out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	   userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);  	

	    dktbBean = new Dangkytrungbay(id);
	    dktbBean.setUserId(userId);
        dktbBean.init();
        
        session.setAttribute("dktbBean", dktbBean);
        String nextJSP = "/TraphacoHYERP/pages/Distributor/DangKyTrungBayUpdate.jsp";
        
        if(querystring.contains("display"))
        	nextJSP = "/TraphacoHYERP/pages/Distributor/DangKyTrungBayDisplay.jsp";
        
        response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
		IDangkytrungbay dktbBean;    
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    String contentType = request.getContentType();
		Utility util = new Utility();
		//Up load

		boolean upload=(contentType != null) && (contentType.indexOf("multipart/form-data") >= 0);
		String id;  
		int kq=3;
    	String nppId ;
		String cttbId ;
    	String istinhds  ;
    	String sosuatphanbo;
		String[] nvbhIds  ;
		String[] khIds  ;
		String[] ddkdIds ;
		String[] soxuat ;
		String[] dangky ;
		boolean err=false;
 		String action="";
		if (upload) 
		{
			
			MultipartRequest multi = new MultipartRequest(request, "C:\\java-tomcat\\data\\", 20000000, "UTF-8"); 
			id = util.antiSQLInspection(multi.getParameter("id"));	
		    if(id == null){  	
		    	dktbBean = new Dangkytrungbay("");
		    }else{
		    	dktbBean = new Dangkytrungbay(id);
		    }
		    
		    Enumeration files = multi.getFileNames(); 
		  	String filenameu  ="";
		  	while (files.hasMoreElements())
            {
                String name = (String)files.nextElement();
                filenameu = multi.getFilesystemName(name); 
            }
		  	String filename=    "C:\\java-tomcat\\data\\"+ filenameu;
		    
		    
		    
			userId = util.antiSQLInspection(multi.getParameter("userId"));
			dktbBean.setUserId(userId);
		      	
	    	 nppId = util.antiSQLInspection(multi.getParameter("nppId"));
			if (nppId == null)
				nppId = "";
			dktbBean.setNppId(nppId);
			
			 cttbId = util.antiSQLInspection(multi.getParameter("cttbTen"));
			if (cttbId == null)
				cttbId = "";				
	    	dktbBean.setCttbId(cttbId);
	    	
	    	 istinhds = util.antiSQLInspection(multi.getParameter("istinhds"));
			if (istinhds == null)
				istinhds = "1";				
	    	dktbBean.setIstinhds(istinhds);
	    	
	    	sosuatphanbo = util.antiSQLInspection(multi.getParameter("sosuatphanbo"));
			if (sosuatphanbo == null)
				sosuatphanbo = "";	
			
			
			System.out.println("So xuat phan bo..............:"+sosuatphanbo);
			dktbBean.setSosuatphanbo(sosuatphanbo);
	    	
			 nvbhIds = multi.getParameterValues("nvbhIds");
			 dktbBean.setNvbhIds(nvbhIds);
			 dangky = multi.getParameterValues("dangky");
			 khIds = multi.getParameterValues("khIds");
			kq=this.readExcel(filename, khIds,dangky, nppId);
			 
			 System.out.println("Kqt qua :"+kq);
			 if(kq!=0)
			 {
				 err=true;
				 dktbBean.setMessage("Lỗi trong file tại dòng "+(kq+1)+" trong file upload (mã khách hàng  không tồn tại hoặc số xuất đk không đúng)");	
				 
			 }
			if(kq==-1)
			{
				err=true;
				dktbBean.setMessage("Lỗi file không xác định!");			
			}

			 soxuat = multi.getParameterValues("soxuat");
			 ddkdIds = multi.getParameterValues("ddkdIds");
	 		 action = multi.getParameter("action");
	 		 for(int i=0;i<khIds.length;i++)
	 		 {
	 			 System.out.println("Ma khach hang: "+khIds[i]);
	 		 }
	 		System.out.println("Action tren la :"+action);
		} 
		else
		{

		    id = util.antiSQLInspection(request.getParameter("id"));	
		    if(id == null){  	
		    	dktbBean = new Dangkytrungbay("");
		    }else{
		    	dktbBean = new Dangkytrungbay(id);
		    }
		    	    
			userId = util.antiSQLInspection(request.getParameter("userId"));
			dktbBean.setUserId(userId);
		      	
	    	 nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if (nppId == null)
				nppId = "";
			dktbBean.setNppId(nppId);
			
			sosuatphanbo = util.antiSQLInspection(request.getParameter("sosuatphanbo"));
			if (sosuatphanbo == null)
				sosuatphanbo = "";	
			System.out.println("So xuat phan bo..............:"+sosuatphanbo);
			dktbBean.setSosuatphanbo(sosuatphanbo);
			
			cttbId = util.antiSQLInspection(request.getParameter("cttbTen"));
			if (cttbId == null)
				cttbId = "";				
	    	dktbBean.setCttbId(cttbId);
	    	
	    	istinhds = util.antiSQLInspection(request.getParameter("istinhds"));
			if (istinhds == null)
				istinhds = "1";				
	    	dktbBean.setIstinhds(istinhds);
	    	
	    	String mucdangky = util.antiSQLInspection(request.getParameter("mucdangky"));
			if (mucdangky == null)
				mucdangky = "";				
	    	dktbBean.setMucdangky(mucdangky);
	    	
			 nvbhIds = request.getParameterValues("nvbhIds");
			 dktbBean.setNvbhIds(nvbhIds);
			
			 khIds = request.getParameterValues("khIds");
			 ddkdIds = request.getParameterValues("ddkdIds");
			 soxuat = request.getParameterValues("soxuat");
			 dangky = request.getParameterValues("dangky");
			
	 		action = request.getParameter("action");
		}
		System.out.println("Action__"+action);
		if(action.equals("save"))
		{
			if ( id == null)
			{
				System.out.println("___Create CTTB___");
				if (!(dktbBean.CreateDktb(khIds, ddkdIds, soxuat, dangky))){	
					dktbBean.createRS();
					dktbBean.createKhRs();
					session.setAttribute("dktbBean", dktbBean);			
					String nextJSP = "/TraphacoHYERP/pages/Distributor/DangKyTrungBayNew.jsp";
					response.sendRedirect(nextJSP);
				}else{
					IDangkytrungbayList obj = new DangkytrungbayList();
					obj.setUserId(userId);
					obj.init("");
					session.setAttribute("obj", obj);
					String nextJSP = "/TraphacoHYERP/pages/Distributor/DangKyTrungBay.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
				
			}else
			{
				
				if(err)
				{
					dktbBean.init();
					dktbBean.setNvbhIds(nvbhIds);
					dktbBean.createKhRs();
					session.setAttribute("dktbBean", dktbBean);
					String nextJSP = "/TraphacoHYERP/pages/Distributor/DangKyTrungBayUpdate.jsp";
					response.sendRedirect(nextJSP);
					return;
				}
				if (!(dktbBean.UpdateDktb(khIds, ddkdIds, soxuat, dangky)))
				{
					dktbBean.init();
					dktbBean.setNvbhIds(nvbhIds);
					dktbBean.createKhRs();
					session.setAttribute("dktbBean", dktbBean);
					String nextJSP = "/TraphacoHYERP/pages/Distributor/DangKyTrungBayUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IDangkytrungbayList obj = new DangkytrungbayList();
					obj.setUserId(userId);
					obj.init("");
					session.setAttribute("obj", obj);
						
					String nextJSP = "/TraphacoHYERP/pages/Distributor/DangKyTrungBay.jsp";
					response.sendRedirect(nextJSP);			    			    									
				}
			}
		}
		else
		{
			String nextJSP;
			if (id == null)
			{
				dktbBean.createRS();
				
				if(cttbId.length() > 0)
				{
					int flag = dktbBean.checkTgDangky();
					if(flag == 0) //thoi gian hop le					 
					{}
					else
					{
						if(flag == 1)
							dktbBean.setMessage("Thoi gian dang ky chuong trinh nay phai trong khoang tg ket thuc tinh doanh so - tg ket thuc trung bay");
						//else
							//dktbBean.setMessage("Thoi gian dang ky chuong trinh nay phai trong khoang tg ket thuc tinh doanh so - tg bat dau trung bay");
					}
				}
				
				session.setAttribute("dktbBean", dktbBean);
				nextJSP = "/TraphacoHYERP/pages/Distributor/DangKyTrungBayNew.jsp";
			}
			else
			{				
				dktbBean.init();
				dktbBean.setNvbhIds(nvbhIds);
				dktbBean.createRS();
				dktbBean.createKhRs();
				
				session.setAttribute("dktbBean", dktbBean);
				nextJSP = "/TraphacoHYERP/pages/Distributor/DangKyTrungBayUpdate.jsp";   						
			}
			response.sendRedirect(nextJSP);
		}}
	}
	
	private int readExcel(String filename,String[] khIds,String dangky[],String npp) throws ServletException, IOException
	{

		File file = new File(filename);
		Workbook workbook;
		dbutils db = new dbutils();		
		try 
		{
			db.getConnection().setAutoCommit(false);
			System.out.println(file);
			workbook = Workbook.getWorkbook(file);
			Sheet sheet = workbook.getSheet(0);	
			boolean flat=false;
			int sodong=sheet.getRows();

			for(int i= 1; i < sodong;++i)
			{
				Cell[]  cells = sheet.getRow(i);
				if(cells[0].getContents().equals("Total"))
				{					
					flat = true;
				}
				if(flat)
					break;
				String readmakh=cells[0].getContents() ;
				String readdangky =cells[3].getContents() ;
				System.out.println("So xuat la:"+readdangky);
				String queryidsp = "select PK_SEQ from KHACHHANG where SMARTID='"+ readmakh +"' and npp_fk='"+npp+"'";
				String pk_seq="";
				ResultSet rskh = db.get(queryidsp);
				if(rskh!=null)
				{
					if(rskh.next())
					{
						pk_seq = rskh.getString("PK_SEQ");
						
						rskh.close();
					}
					else
					{
						db.getConnection().rollback();
						return i;
					}
				}
				else
				{
					db.getConnection().rollback();
					return i;
				}
				if(!this.CheckNumerOrNot(readdangky.trim()))
				{
					db.getConnection().rollback();
					return i;
				}
				else
				{
					for(int j=0;j<khIds.length;j++)
					{
						if(khIds[j].equals(pk_seq.trim()))
							dangky[j]=readdangky;
					}
				}
				

		    }	    	
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);	
			return 0;
		}
		catch (Exception ex) 
		{	
			db.update("rollback");
			System.out.println("Loi doc file : "+ex.toString());
			return -1;
		}
		finally
		{
			db.shutDown();
		}
	}
	

	public boolean CheckNumerOrNot(String number)
	 {
	  if (number.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+"))
	   return true;
	  return false;
	 }

}
