package geso.traphaco.center.servlets.tieuchithuong;

import geso.traphaco.center.beans.tieuchithuong.IDuyetsuungho;
import geso.traphaco.center.beans.tieuchithuong.IDuyetsuunghoList;
import geso.traphaco.center.beans.tieuchithuong.imp.Duyetsuungho;
import geso.traphaco.center.beans.tieuchithuong.imp.DuyetsuunghoList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Sheet;
import jxl.Workbook;

import com.oreilly.servlet.MultipartRequest;

public class DuyetsuunghoUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
	
    public DuyetsuunghoUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		IDuyetsuungho tctskuBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);
	   
	    tctskuBean = new Duyetsuungho(id);
	    tctskuBean.setId(id);
	    tctskuBean.setUserId(userId);
	    
        tctskuBean.init();
        session.setAttribute("tctskuBean", tctskuBean);
        
        String nextJSP = "/TraphacoHYERP/pages/Center/DuyetSuUngHoUpdate.jsp";
        if(querystring.indexOf("display") >= 0)
        	nextJSP = "/TraphacoHYERP/pages/Center/DuyetSuUngHoDisplay.jsp";
        
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		this.out = response.getWriter();
		Utility util = new Utility();
		
		String contentType = request.getContentType();
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			GET_DATA_FORM_EXCEL( request, response, util );
			return;
		}
		
		IDuyetsuungho tctskuBean;
	   	String id = util.antiSQLInspection(request.getParameter("id"));
	    if(id == null){  	
	    	tctskuBean = new Duyetsuungho("");
	    }else{
	    	tctskuBean = new Duyetsuungho(id);
	    }
	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		tctskuBean.setUserId(userId);
		
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		tctskuBean.setDiengiai(diengiai);
		
		String thang = util.antiSQLInspection(request.getParameter("thang"));
		if (thang == null)
			thang = Integer.toString(Integer.parseInt(getDateTime().split("-")[1]));
		tctskuBean.setThang(thang);
		
		String loaict = util.antiSQLInspection(request.getParameter("loaict"));
		if (loaict == null)
			loaict = "0";
		tctskuBean.setLoaict(loaict);
		
		String quy = util.antiSQLInspection(request.getParameter("quy"));
		if (quy == null)
			quy = getQuyHienTai();
		tctskuBean.setQuy(quy);
		
		String nam = util.antiSQLInspection(request.getParameter("nam"));
		if (nam == null)
			nam = "";
		tctskuBean.setNam(nam);
		
		String landuyet = util.antiSQLInspection(request.getParameter("landuyet"));
		if (landuyet == null)
			landuyet = "1";
		tctskuBean.setLanduyet(landuyet);
		
		String[] nppIds = request.getParameterValues("nppIds");
		if(nppIds != null)
		{
			String nppId = "";
			for(int i = 0; i < nppIds.length; i++)
			{
				nppId += nppIds[i] + ",";
			}
			
			if(nppId.trim().length() > 0)
			{
				nppId = nppId.substring(0, nppId.length() - 1);
				tctskuBean.setNppIds(nppId);
			}
		}
		
		String[] khIds = request.getParameterValues("khIds");
		if(khIds != null)
		{
			String khId = "";
			for(int i = 0; i < khIds.length; i++)
			{
				khId += khIds[i] + ",";
			}
			
			if(khId.trim().length() > 0)
			{
				khId = khId.substring(0, khId.length() - 1);
				tctskuBean.setKhIds(khId);
			}
		}
		
		//VUNG 
		String vungId = request.getParameter("vungId");
		if(vungId == null)
			vungId = "";
		tctskuBean.setVungIds(vungId);
		
		String kvId = request.getParameter("khuvucId");
		if(kvId == null)
			kvId = "";
		tctskuBean.setKhuvucIds(kvId);
		
 		String action = request.getParameter("action");
 		System.out.println("Action la: " + action);
 		
 		if(action.equals("save"))
 		{    
    		if (id == null )
    		{
    			if (!tctskuBean.createTctSKU())
    			{
    		    	tctskuBean.setUserId(userId);
    		    	
    		    	tctskuBean.createRs();
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("tctskuBean", tctskuBean);
    				
    				String nextJSP = "/TraphacoHYERP/pages/Center/DuyetSuUngHoNew.jsp";
    				response.sendRedirect(nextJSP);
    			}
    			else
    			{
    				IDuyetsuunghoList obj = new DuyetsuunghoList();
				    obj.setUserId(userId);
				    
				    obj.init("");
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Center/DuyetSuUngHo.jsp";
					response.sendRedirect(nextJSP);
    			}	
    		}
    		else
    		{
    			if (!(tctskuBean.updateTctSKU()))
    			{			
    		    	tctskuBean.setUserId(userId);
    		    	
    		    	tctskuBean.createRs();
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("tctskuBean", tctskuBean);
    				
    				String nextJSP = "/TraphacoHYERP/pages/Center/DuyetSuUngHoUpdate.jsp";
    				response.sendRedirect(nextJSP);
    			}
				else
				{
					IDuyetsuunghoList obj = new DuyetsuunghoList();
				    obj.setUserId(userId);
				    
				    obj.init("");
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Center/DuyetSuUngHo.jsp";
					response.sendRedirect(nextJSP);
				}
    		}
	    }
		else
		{		
			tctskuBean.createRs();
			session.setAttribute("userId", userId);
			session.setAttribute("tctskuBean", tctskuBean);
			
			String nextJSP;
			if (id == null){
				nextJSP = "/TraphacoHYERP/pages/Center/DuyetSuUngHoNew.jsp";
			}
			else{
				nextJSP = "/TraphacoHYERP/pages/Center/DuyetSuUngHoUpdate.jsp";   						
			}
			response.sendRedirect(nextJSP);
		}
	}
	
	private void GET_DATA_FORM_EXCEL(HttpServletRequest request, HttpServletResponse response, Utility util)
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		
		String UPLOAD_DIRECTORY = "C:\\upload\\excel\\";
		MultipartRequest multi;
		try 
		{
			multi = new MultipartRequest(request, UPLOAD_DIRECTORY, 20000000, "UTF-8");
		
			IDuyetsuungho tctskuBean;
			
			String id = util.antiSQLInspection(multi.getParameter("id"));
		    if(id == null){  	
		    	tctskuBean = new Duyetsuungho("");
		    }else{
		    	tctskuBean = new Duyetsuungho(id);
		    }
		    
			tctskuBean.setUserId(userId);
			
			String diengiai = util.antiSQLInspection(multi.getParameter("diengiai"));
			if (diengiai == null)
				diengiai = "";
			tctskuBean.setDiengiai(diengiai);
			
			String thang = util.antiSQLInspection(multi.getParameter("thang"));
			if (thang == null)
				thang = Integer.toString(Integer.parseInt(getDateTime().split("-")[1]));
			tctskuBean.setThang(thang);
			
			String loaict = util.antiSQLInspection(multi.getParameter("loaict"));
			if (loaict == null)
				loaict = "0";
			tctskuBean.setLoaict(loaict);
			
			String quy = util.antiSQLInspection(multi.getParameter("quy"));
			if (quy == null)
				quy = getQuyHienTai();
			tctskuBean.setQuy(quy);
			
			String nam = util.antiSQLInspection(multi.getParameter("nam"));
			if (nam == null)
				nam = "";
			tctskuBean.setNam(nam);
			
			String landuyet = util.antiSQLInspection(multi.getParameter("landuyet"));
			if (landuyet == null)
				landuyet = "1";
			tctskuBean.setLanduyet(landuyet);
			
			//VUNG 
			String vungId = multi.getParameter("vungId");
			if(vungId == null)
				vungId = "";
			tctskuBean.setVungIds(vungId);
			
			String kvId = multi.getParameter("khuvucId");
			if(kvId == null)
				kvId = "";
			tctskuBean.setKhuvucIds(kvId);
			
	 		String action = request.getParameter("action");
	 		System.out.println("Action la: " + action);
			
			//DOC DU LIEU TU EXCEL FILE
			Enumeration files = multi.getFileNames();
			String filename = "";
			while (files.hasMoreElements())
			{
				String name = (String) files.nextElement();
				filename = multi.getFilesystemName(name);
				System.out.println("File  " + UPLOAD_DIRECTORY + filename);
			}
			
			if (filename != null && filename.length() > 0)
			{
				this.readExcel(tctskuBean, UPLOAD_DIRECTORY + filename);
			}
			
			tctskuBean.createRs();
			session.setAttribute("userId", userId);
			session.setAttribute("tctskuBean", tctskuBean);
			
			String nextJSP;
			if (id == null){
				nextJSP = "/TraphacoHYERP/pages/Center/DuyetSuUngHoNew.jsp";
			}
			else{
				nextJSP = "/TraphacoHYERP/pages/Center/DuyetSuUngHoUpdate.jsp";   						
			}
			response.sendRedirect(nextJSP);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}

	private void readExcel(IDuyetsuungho tctskuBean, String fileName) 
	{
		File inputWorkbook = new File(fileName);
		Workbook w;
		try
		{
			w = Workbook.getWorkbook(inputWorkbook);
			Sheet sheet = w.getSheet(0);
			int sodong = sheet.getRows();
			int socot = sheet.getColumns();
			
			System.out.println("So dong " + sodong + "socot " + socot );
			String _ma = "";
			String _khIds = "";
			System.out.println("___________Manpp"+tctskuBean.getNppIds());
			String sql1=" select npp.pk_seq from NHANVIEN nv,NHAPHANPHOI npp "+
					   " where nv.CONVSITECODE=npp.SITECODE and nv.PK_SEQ="+tctskuBean.getUserId();
			dbutils db=new dbutils();
			ResultSet rs1=db.get(sql1);
			if(rs1.next())
			{
				String maNPP=rs1.getString("pk_seq");
				rs1.close();
				for (int i = 3; i < sodong; i++)
				{
					
					String khIds = sheet.getCell(1, i).getContents();
					
					if(maNPP.trim().length() > 0 && khIds.trim().length() > 0  )
					{
						System.out.println("MA npp: " +  maNPP + "  -- KH SP: " + khIds  );
						
						_ma += maNPP + ",";
						//System.out.println("ma______"+_ma);
						 sql1=" select pk_seq from khachhang "+
								   " where Mafast='"+khIds+"'";
						 ResultSet rs12=db.get(sql1);
						 if(rs12.next())
						 {
							 khIds=rs12.getString("pk_seq");
						 }
						 rs12.close();
						_khIds += khIds + ",";
						
					}
					
				}
				
				System.out.println("Khach hang doc tu file EXCEL: " + _khIds);
				if(_khIds.trim().length() > 0)
				{
					_khIds = _khIds.substring(0, _khIds.length() - 1);
					tctskuBean.setKhIds(_khIds);
					
					//INIT NPP
					
					String query = " select distinct npp_fk from KHACHHANG where pk_seq in ( " + _khIds + " ) ";
					ResultSet rs = db.get(query);
					String nppIds = "";
					while(rs.next())
					{
						nppIds += rs.getString("npp_fk") + ",";
					}
					rs.close();
					
					if(nppIds.trim().length() > 0)
					{
						nppIds = nppIds.substring(0, nppIds.length() - 1);
						tctskuBean.setNppIds(nppIds);
					}
				}
			}
			
			
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			tctskuBean.setMsg("Loi doc file Excel" + e.getMessage());
		}
		
	}

	public String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private String getQuyHienTai() 
	{
        int thang = Integer.parseInt(this.getDateTime().split("-")[1]);
        if(1 <= thang && thang <= 3)
        	return "1";
        else if(4 <= thang && thang <= 6)
        	return "2";
        else if(7 <= thang && thang <= 9)
        	return "3";
        else 
        	return "4";
	}

}
