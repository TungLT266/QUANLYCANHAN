package geso.traphaco.center.servlets.dieukienkhuyenmai;

import geso.traphaco.center.beans.dieukienkhuyenmai.*;
import geso.traphaco.center.beans.dieukienkhuyenmai.imp.*;

import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class DkkhuyenmaiUpdateSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out; 
	
	
    public DkkhuyenmaiUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IDieukienkhuyenmai dkkmBean;
		HttpSession session = request.getSession();
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);  	

	    dkkmBean = new Dieukienkhuyenmai(id);
	    dkkmBean.setUserId(userId);
        dkkmBean.init();
        
        session.setAttribute("dkkmBean", dkkmBean);
        String nextJSP = "/TraphacoHYERP/pages/Center/DieuKienKhuyenMaiUpdate.jsp";
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IDieukienkhuyenmai dkkmBean;

		Utility util = new Utility();
	    String id = util.antiSQLInspection(request.getParameter("id"));	
	    if(id == null){  	
	    	dkkmBean = new Dieukienkhuyenmai("");
	    }else{
	    	dkkmBean = new Dieukienkhuyenmai(id);
	    }
	    	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		dkkmBean.setUserId(userId);	       
    			
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		dkkmBean.setDiengiai(diengiai);
		
		String hinhthuc = util.antiSQLInspection(request.getParameter("option"));
		if (hinhthuc == null)
			hinhthuc = "";
		dkkmBean.setHinhthuc(hinhthuc);
		
		String tongluong = util.antiSQLInspection(request.getParameter("tongluong"));
		if (tongluong == null)
			tongluong = "";
		dkkmBean.setTongluong(tongluong);
		
		String tongtien = util.antiSQLInspection(request.getParameter("tongtien"));
		if (tongtien == null)
			tongtien = "";
		dkkmBean.setTongtien(tongtien);
		
		String type = util.antiSQLInspection(request.getParameter("type"));
		if (type == null)
			type = "";
		dkkmBean.setType(type);
		
		String trongso = util.antiSQLInspection(request.getParameter("trongso"));
		if (trongso == null)
			trongso = "0";
		dkkmBean.setTrongso(trongso);
		
		String nhomspId = util.antiSQLInspection(request.getParameter("nhomsp"));
		if (nhomspId == null)
			nhomspId = "";
		dkkmBean.setNhomspId(nhomspId);
		
		String isThung = request.getParameter("isThung");
		if(isThung == null)
			isThung = "0";
		dkkmBean.setIsThung(isThung);
		
		String ngaysua = getDateTime();
    	dkkmBean.setNgaysua(ngaysua);
    	
    	String[] masp = request.getParameterValues("masp");
		String[] tensp = request.getParameterValues("tensp");
		String[] soluong = request.getParameterValues("soluong");
		
		String[] spBatbuocIds = request.getParameterValues("spBatbuocIds");
		String spBatbuoc = "";
		if(spBatbuocIds != null)
		{
			for(int i = 0; i < spBatbuocIds.length; i++)
				spBatbuoc += spBatbuocIds[i] + " --- ";
		}
				
		List<ISanpham> spList = new ArrayList<ISanpham>();
		
		if(masp != null)
		{
			for(int i = 0; i < masp.length; i++)
			{
				if(masp[i].length() > 0)
				{
					Sanpham sp;
					if(soluong != null && soluong[i] != null )
					{
						sp = new Sanpham("", masp[i], tensp[i], soluong[i], "");
					}
					else
					{
						sp = new Sanpham("", masp[i], tensp[i], "", "");
					}
					
					if(spBatbuoc.indexOf(masp[i]) >= 0)
						sp.setBatbuoc("1");
					
					spList.add(sp);
				}
			}
		}

		dkkmBean.setSpList(spList);
	
 		String action = request.getParameter("action");
 		if(action.equals("save"))
 		{
    		if (id == null )
    		{
    			if (!dkkmBean.CreateDkkm())
    			{
    		    	dkkmBean.setUserId(userId);
    		    	dkkmBean.createRS();
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("dkkmBean", dkkmBean);
    				
    				String nextJSP = "/TraphacoHYERP/pages/Center/DieuKienKhuyenMaiNew.jsp";
    				response.sendRedirect(nextJSP);
    			}
    			else
    			{
    				IDkkhuyenmaiList obj = new DkkhuyenmaiList();
    				obj.setUserId(userId);
    				obj.init("");
    				
				
    				session.setAttribute("obj", obj);
    				session.setAttribute("userId", userId);
		    		
    				response.sendRedirect("/TraphacoHYERP/pages/Center/DieuKienKhuyenMai.jsp");	    	
    			}		
    		}
    		else
    		{
    			if (!(dkkmBean.UpdateDkkm()))
    			{			
    		    	dkkmBean.setUserId(userId);
    		    	dkkmBean.createRS();
    		    	
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("dkkmBean", dkkmBean);
    				
    				String nextJSP = "/TraphacoHYERP/pages/Center/DieuKienKhuyenMaiUpdate.jsp";
    				response.sendRedirect(nextJSP);
    			}
    			else
    			{
    				IDkkhuyenmaiList obj = new DkkhuyenmaiList();
    				obj.setUserId(userId);
    				obj.init("");
    				
    				session.setAttribute("obj", obj);
    				session.setAttribute("userId", userId);
		    		
    				response.sendRedirect("/TraphacoHYERP/pages/Center/DieuKienKhuyenMai.jsp");	    	
    			}
    		}
	    }
		else if (action.equals("excel"))
		{
			OutputStream outstream = null;
			try
			{
				WorkbookSettings wbSettings = new WorkbookSettings();
				dkkmBean.init();
				
				wbSettings.setLocale(new Locale("en", "EN"));			
				response.setContentType("application/vnd.ms-excel");
			    response.setHeader("Content-Disposition", "attachment; filename=DieukienKhuyenmai.xls");
			    
			    outstream = response.getOutputStream();
				WritableWorkbook workbook = Workbook.createWorkbook(outstream, wbSettings);
			    
				workbook.createSheet("DKKM", 0);
				WritableSheet Sheet = workbook.getSheet(0);
				
				workbook.setColourRGB(Colour.RED, 0xff, 0, 0);
				
				this.CreateHeader(Sheet, dkkmBean);
				this.CreateContent(Sheet, dkkmBean);
				
				workbook.write();		
				workbook.close();
				return;
			}
			catch(jxl.JXLException ex)
			{
				System.out.print("Exception...");
			}
			finally
		    {
		     if (outstream != null)
		    	 outstream.close();
		    }

		}
		else
		{
			dkkmBean.createRS();		
			if(id != null && nhomspId.length() == 0)
				dkkmBean.createDkkmSpList();
			session.setAttribute("userId", userId);
			session.setAttribute("dkkmBean", dkkmBean);
			String nextJSP;
			if (id == null){
				nextJSP = "/TraphacoHYERP/pages/Center/DieuKienKhuyenMaiNew.jsp";
			}
			else{
				nextJSP = "/TraphacoHYERP/pages/Center/DieuKienKhuyenMaiUpdate.jsp";   						
			}
			response.sendRedirect(nextJSP);
		}		
	}
	
	private void CreateHeader(WritableSheet ws, IDieukienkhuyenmai dkkmBean) throws WriteException
	{
		//Co dinh 8 row dau tien
		ws.getSettings().setVerticalFreeze(10);
	    ws.getSettings().setDefaultRowHeight(17*20);
	    
	    ws.setColumnView(0, 20);
	    ws.setColumnView(1, 50);
	    ws.setColumnView(2, 13);
	    ws.setColumnView(3, 13);
	    ws.setColumnView(4, 13);
	    ws.setColumnView(5, 15);
	    ws.setColumnView(7, 18);
	    ws.setColumnView(8, 18);
	    ws.setColumnView(9, 18);
	    ws.setColumnView(10, 18);
	    ws.setColumnView(11, 18);
	    
	    WritableFont wf = new WritableFont(WritableFont.ARIAL, 15 , WritableFont.BOLD);
	    wf.setColour(Colour.RED);
	    WritableCellFormat wcf = new WritableCellFormat(wf);
	    wcf.setAlignment(Alignment.LEFT);
	    wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
	   
	    Label title = new Label(0, 0, "Điều kiện khuyến mãi", wcf);
	    ws.addCell(title);
	    
	    WritableFont font = new WritableFont(WritableFont.ARIAL, WritableFont.DEFAULT_POINT_SIZE, WritableFont.NO_BOLD, false,UnderlineStyle.NO_UNDERLINE);
	    wcf = new WritableCellFormat(font);
	    wcf.setWrap(true);
	    
	    WritableFont wf2 = new WritableFont(WritableFont.ARIAL, WritableFont.DEFAULT_POINT_SIZE , WritableFont.BOLD);
	    WritableCellFormat wcf2 = new WritableCellFormat(wf2);
	    	    
	    Label l_ngaytao = new Label(0, 1, "Ngày tạo: ", wcf);
	    ws.addCell(l_ngaytao);	
	    
	    Label d_ngaytao = new Label(1, 1,dkkmBean.getNgaytao() , wcf2);
	    ws.addCell(d_ngaytao);	
	    
	    Label l_nguoitao = new Label(0, 2, "Người tạo: ", wcf);
	    ws.addCell(l_nguoitao);	
	    
	    Label d_nguoitao = new Label(1, 2, dkkmBean.getNguoitao(), wcf2);
	    ws.addCell(d_nguoitao);		    	    		    

	    Label l_madieukien = new Label(0, 4, "Mã điều kiện: ", wcf);
	    ws.addCell(l_madieukien);	
	    
	    Label d_madieukien = new Label(1, 4, dkkmBean.getId(), wcf2);
	    ws.addCell(d_madieukien);	
	    

	    Label l_diengiai = new Label(0, 6, "Diễn giải: ", wcf);
	    ws.addCell(l_diengiai);	
	    
	    Label d_diengiai = new Label(1, 6, dkkmBean.getDiengiai(), wcf2);
	    ws.addCell(d_diengiai);	
	    
	    Label l_loaidieukien = new Label(0, 7, "Loại điều kiện: ", wcf);
	    ws.addCell(l_loaidieukien);
	    
	    
	    Label d_loaidieukien;
	    if(dkkmBean.getType().equals("1"))
	    	d_loaidieukien = new Label(1, 7, "Bắt buộc nhập số lượng từng sản phẩm" , wcf2);
	    else
	    	d_loaidieukien = new Label(1, 7, "Bất kỳ trong" , wcf2);
	    ws.addCell(d_loaidieukien);	

	    
	    Label l_hinhthuc = new Label(0, 8, "Hình thức: ", wcf);
	    ws.addCell(l_hinhthuc);		    
	    
	    Label d_hinhthuc;
	    Label d_tong;
	    if(dkkmBean.getHinhthuc().equals("1")){
	    	d_hinhthuc = new Label(1, 8, "Nhập tổng lượng" , wcf2);
	    	d_tong = new Label(2, 8, dkkmBean.getTongluong() , wcf2);
		}else{
	    	d_hinhthuc = new Label(1, 8,"Nhập tổng tiền" , wcf2);
	    	d_tong = new Label(2, 8, dkkmBean.getTongtien() , wcf2);
	    }
	    ws.addCell(d_hinhthuc);	
	    ws.addCell(d_tong);	
	    
	    font = new WritableFont(WritableFont.ARIAL, WritableFont.DEFAULT_POINT_SIZE, WritableFont.BOLD, false);
	    wcf = new WritableCellFormat(font);
	    wcf.setWrap(true);
	    wcf.setAlignment(Alignment.CENTRE);
	    wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
	    wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
	    	    
	    Label masp = new Label(0, 10, "Mã sản phẩm", wcf);	    
	    ws.addCell(masp);	

	    Label tensp = new Label(1, 10, "Tên sản phẩm", wcf);	    
	    ws.addCell(tensp);	
	    
	    Label soluong = new Label(2, 10, "Số lượng", wcf);	   
	    ws.addCell(soluong);	 
	    
	}
	
	private void CreateContent(WritableSheet ws, IDieukienkhuyenmai dkkmBean) throws WriteException
	{
		WritableFont wf = new WritableFont(WritableFont.ARIAL,WritableFont.DEFAULT_POINT_SIZE, WritableFont.NO_BOLD);
	    WritableCellFormat wcf_left = new WritableCellFormat(wf);
	    wcf_left.setAlignment(Alignment.LEFT);
	    wcf_left.setBorder(Border.ALL, BorderLineStyle.THIN);
	    
	    WritableCellFormat wcf_center = new WritableCellFormat(wf);
	    wcf_center.setAlignment(Alignment.CENTRE);
	    wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN);
	    
	    WritableCellFormat wcf_right = new WritableCellFormat(wf);
	    wcf_right.setAlignment(Alignment.RIGHT);
	    wcf_right.setBorder(Border.ALL, BorderLineStyle.THIN);
	    
	    WritableCellFormat wcf_num = new WritableCellFormat(NumberFormats.THOUSANDS_INTEGER);
	    wcf_num.setAlignment(Alignment.RIGHT);
	    wcf_num.setBorder(Border.ALL, BorderLineStyle.THIN);
	    
	    WritableCellFormat cfi2 = new WritableCellFormat(NumberFormats.THOUSANDS_INTEGER);
	    cfi2.setAlignment(Alignment.CENTRE);
	    cfi2.setBorder(Border.ALL, BorderLineStyle.THIN);
	    		
		
		List<ISanpham> spSudungList = dkkmBean.getSpList();
			
		int m = 11;
			
		for(int i = 0; i < spSudungList.size(); i++)
		{
			Sanpham sp = (Sanpham)spSudungList.get(i);
			
			Label masp = new Label(0, m,sp.getMasanpham(), wcf_left);
			ws.addCell(masp);

			Label tensp = new Label(1, m, sp.getTensanpham(), wcf_left);
			ws.addCell(tensp);
			
			Label soluong = new Label(2, m, sp.getSoluong(), wcf_left);
			ws.addCell(soluong);
		    
			m++;
		}

	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
