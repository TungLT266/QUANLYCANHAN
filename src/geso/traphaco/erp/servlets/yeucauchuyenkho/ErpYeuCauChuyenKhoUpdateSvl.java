package geso.traphaco.erp.servlets.yeucauchuyenkho;

import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpChuyenkhoSX;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpChuyenkhoSXList;
import geso.traphaco.erp.beans.yeucauchuyenkho.IYeucau;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpChuyenkhoSX;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpChuyenkhoSXList;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.Yeucau;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;

public class ErpYeuCauChuyenKhoUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
    public ErpYeuCauChuyenKhoUpdateSvl() 
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
		}
		else
		{
			session.setMaxInactiveInterval(30000);
		
			Utility util = new Utility();
			
	    	String querystring = request.getQueryString();
		    userId = util.getUserId(querystring);
		    
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId")); 
		    
		    String id = util.getId(querystring);  	
		    IErpChuyenkhoSX lsxBean = new ErpChuyenkhoSX(id);
		    lsxBean.setUserId(userId); 
		    
		    String task = request.getParameter("task");
			if(task == null)
				task = "0";
			lsxBean.settask(task);
			
	        String nextJSP = "/TraphacoHYERP/pages/Erp/ErpYeuCauChuyenKhoNew.jsp";
			if(request.getQueryString().indexOf("display") >= 0 ) 
	        {
        		//lsxBean.initView();
        		nextJSP = "/TraphacoHYERP/pages/Erp/ErpYeuCauChuyenKhoDisplay.jsp";
	        }
			else if(request.getQueryString().indexOf("print") >= 0 ) 
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=YeuCauChuyenKho.xlsm");
				ServletOutputStream outstream = response.getOutputStream();			
				
				try 
				{
					ExportToExcel(outstream, id);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
				
				return;
			}
	        
			lsxBean.init();
			if(request.getQueryString().indexOf("copy") >= 0 ) 
	        {
        		lsxBean.setId("");
	        }
			session.setAttribute("khochuyenIds", lsxBean.getKhoXuatId());
			session.setAttribute("vitriId", "");
	        session.setAttribute("lsxBean", lsxBean);
	        response.sendRedirect(nextJSP);
		}		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen"); 
		
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
			
			session.setMaxInactiveInterval(30000);
			
			this.out = response.getWriter();
			IErpChuyenkhoSX lsxBean;
			
			Utility util = new Utility();	
			String id = util.antiSQLInspection(request.getParameter("id"));
		    if(id == null)
		    {  	
		    	lsxBean = new ErpChuyenkhoSX("");
		    }
		    else
		    {
		    	lsxBean = new ErpChuyenkhoSX(id);
		    }
	
		    lsxBean.setUserId(userId);
		    
		    String task = request.getParameter("task");
			if(task == null)
				task = "0";
			lsxBean.setIsnhanHang(task);
		    
		    String ngayyeucau = util.antiSQLInspection(request.getParameter("ngaychuyen"));
		    if(ngayyeucau == null || ngayyeucau.trim().length() <= 0)
		    	ngayyeucau = getDateTime();
		    lsxBean.setNgayyeucau(ngayyeucau);
		    
		    String lydo = util.antiSQLInspection(request.getParameter("lydo"));
		    if(lydo == null)
		    	lydo = "";
		    lsxBean.setLydoyeucau(lydo);
		    
		    String IsChuyenHangSX = util.antiSQLInspection(request.getParameter("IsChuyenHangSX"));
		    if(IsChuyenHangSX == null)
		    	IsChuyenHangSX = "0";
		    lsxBean.setIsChuyenHangSX(IsChuyenHangSX);
		    
		    String IsChuyenkhongdat= util.antiSQLInspection(request.getParameter("chuyenhangkhongdat"));
		    if(IsChuyenkhongdat == null)
		    	IsChuyenkhongdat = "0";
		    lsxBean.setIsChuyenhangkhongdat(IsChuyenkhongdat);
		    
		    String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
		    if(ghichu == null)
		    	ghichu = "";
		    lsxBean.setGhichu(ghichu);
		    
		    String noidungxuat = util.antiSQLInspection(request.getParameter("noidungxuat"));
			if (noidungxuat == null)
				noidungxuat = "";	
			lsxBean.setNdxId(noidungxuat);
	    	
		    String khoxuatId = util.antiSQLInspection(request.getParameter("khoxuatId"));
			if (khoxuatId == null)
				khoxuatId = "";				
			lsxBean.setKhoXuatId(khoxuatId);
 
			String khonhapId = util.antiSQLInspection(request.getParameter("khonhapId"));
			if (khonhapId == null)
				khonhapId = "";				
			lsxBean.setKhoNhapId(khonhapId);
			
			String nguoinhan = util.antiSQLInspection(request.getParameter("nguoinhan"));
			if (nguoinhan == null)
				nguoinhan = "";				
			lsxBean.setNguoinhan(nguoinhan);
			
			String tsddId = util.antiSQLInspection(request.getParameter("tsddId"));
			if (tsddId == null)
				tsddId = "";				
			lsxBean.setTsddId(tsddId);
			
			String kyhieu = util.antiSQLInspection(request.getParameter("kyhieu"));
			if (kyhieu == null)
				kyhieu = "";				
			lsxBean.setKyHieu(kyhieu);
			
			String sochungtu = util.antiSQLInspection(request.getParameter("sochungtu"));
			if (sochungtu == null)
				sochungtu = "";				
			lsxBean.setSochungtu(sochungtu);
			
			String lenhdieudong = util.antiSQLInspection(request.getParameter("lenhdieudong"));
			if (lenhdieudong == null)
				lenhdieudong = "";				
			lsxBean.setLenhdieudong(lenhdieudong);
			
			String ngaydieudong = util.antiSQLInspection(request.getParameter("ngaydieudong"));
			if (ngaydieudong == null)
				ngaydieudong = "";				
			lsxBean.setNgaydieudong(ngaydieudong);
			
			String nguoidieudong = util.antiSQLInspection(request.getParameter("nguoidieudong"));
			if (nguoidieudong == null)
				nguoidieudong = "";				
			lsxBean.setNguoidieudong(nguoidieudong);
			
			String veviec = util.antiSQLInspection(request.getParameter("veviec"));
			if (veviec == null)
				veviec = "";				
			lsxBean.setVeviec(veviec);
			
			String nguoivanchuyen = util.antiSQLInspection(request.getParameter("nguoivanchuyen"));
			if (nguoivanchuyen == null)
				nguoivanchuyen = "";				
			lsxBean.setNguoivanchuyen(nguoivanchuyen);
			
			String phuongtien = util.antiSQLInspection(request.getParameter("phuongtien"));
			if (phuongtien == null)
				phuongtien = "";				
			lsxBean.setPhuongtien(phuongtien);
			
			String hopdong = util.antiSQLInspection(request.getParameter("hopdong"));
			if (hopdong == null)
				hopdong = "";				
			lsxBean.setHopdong(hopdong);
			
			String lsxId = util.antiSQLInspection(request.getParameter("lsxId"));
			if (lsxId == null)
				lsxId = "";				
			lsxBean.setLsxIds(lsxId);
			
			String CdsxId = util.antiSQLInspection(request.getParameter("CdsxId"));
			if (CdsxId == null)
				CdsxId = "";				
			lsxBean.setCDSXId(CdsxId);
			
			
			//BÊN CHUYỂN
			String codoituong = util.antiSQLInspection(request.getParameter("codoituong"));
			if (codoituong == null)
				codoituong = "";				
			lsxBean.setCoDoituong(codoituong);
			
			String loaidoituongId = util.antiSQLInspection(request.getParameter("loaidoituongId"));
			if (loaidoituongId == null)
				loaidoituongId = "";				
			lsxBean.setLoaidoituongId(loaidoituongId);
			
			String doituongId = util.antiSQLInspection(request.getParameter("doituongId"));
			if (doituongId == null)
				doituongId = "";				
			lsxBean.setDoituongId(doituongId);
			
			//BÊN NHẬN
			String cokhoNhan = util.antiSQLInspection(request.getParameter("cokhoNhan"));
			if (cokhoNhan == null)
				cokhoNhan = "";				
			lsxBean.setCoKhonhan(cokhoNhan);
			
			String codoituongNhan = util.antiSQLInspection(request.getParameter("codoituongNhan"));
			if (codoituongNhan == null)
				codoituongNhan = "";				
			lsxBean.setCoDoituongNhan(codoituongNhan);
			
			String loaidoituongNhanId = util.antiSQLInspection(request.getParameter("loaidoituongNhanId"));
			if (loaidoituongNhanId == null)
				loaidoituongNhanId = "";				
			lsxBean.setLoaidoituongNhanId(loaidoituongNhanId);
			
			String doituongNhanId = util.antiSQLInspection(request.getParameter("doituongNhanId"));
			if (doituongNhanId == null)
				doituongNhanId = "";				
			lsxBean.setDoituongNhanId(doituongNhanId);
			
			//CHI PHI
			String coChiphi = util.antiSQLInspection(request.getParameter("coChiphi"));
			if (coChiphi == null)
				coChiphi = "";				
			lsxBean.setCochiphi(coChiphi);
			
			String chiphiId = util.antiSQLInspection(request.getParameter("chiphiId"));
			if (chiphiId == null)
				chiphiId = "";				
			lsxBean.setChiphiId(chiphiId);
			
			String muahang_fk = util.antiSQLInspection(request.getParameter("muahang_fk"));
			if (muahang_fk == null)
				muahang_fk = "";	
			lsxBean.setMuahang_fk(muahang_fk);
			session.setAttribute("lsxId", lsxId);
			session.setAttribute("manoidungxuat",  lsxBean.getMaNDX(noidungxuat));
			
			String[] idsp = request.getParameterValues("idsp");
			String[] masp = request.getParameterValues("masp");
			String[] tensp = request.getParameterValues("tensp");
			String[] dvt = request.getParameterValues("donvi");
		 
			String[] soluong = request.getParameterValues("soluongyeucau"); 
			String[] soluongton = request.getParameterValues("soluongtonkho"); 
			String[] ghichu_ck = request.getParameterValues("ghichu_ck"); 
			
			List<IYeucau> spList = new ArrayList<IYeucau>();
			if(masp != null)
			{	
				IYeucau yeucau = null;
				for(int m = 0; m < masp.length; m++)
				{	
					if(masp[m] != "")
					{	
						yeucau = new Yeucau();
						yeucau.setId(idsp[m]);
						yeucau.setMa(masp[m]);
						yeucau.setTen(tensp[m]);
						
						yeucau.setSoluongTon(soluongton[m].replaceAll(",",""));
						yeucau.setSoluongYC(soluong[m].replaceAll(",",""));
						
						yeucau.setghichu_CK(ghichu_ck[m]);
						yeucau.setLsxId("");
						
						if(dvt != null)
							yeucau.setDonViTinh(dvt[m]);
				 
						spList.add(yeucau);
					}				
				}
				
				lsxBean.setSpList(spList);
			}	
 
		    String action = request.getParameter("action");
			if(action.equals("save"))
			{	
				if(id.length() ==0 )
				{
					if(!lsxBean.createCK())
					{
						lsxBean.createRs();
	    		    	session.setAttribute("lsxBean", lsxBean);
	    		    	lsxBean.settask(task);
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpYeuCauChuyenKhoNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpChuyenkhoSXList obj = new ErpChuyenkhoSXList();
						obj.setUserId(userId);
						obj.setIsnhanHang(task);
						
						String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
		 	    		geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
		 	    		
						obj.init("");  
						lsxBean.DBclose();
				    	session.setAttribute("obj", obj);  	
			    		session.setAttribute("userId", userId);
			    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpYeuCauChuyenKho.jsp");
					}
				}
				else
				{
					if(!lsxBean.updateCK())
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
						lsxBean.setIsnhanHang(task);
	    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpYeuCauChuyenKhoNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpChuyenkhoSXList obj = new ErpChuyenkhoSXList();

						obj.setUserId(userId);
						obj.setIsnhanHang(task);

						String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
		 	    		geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
		 	    		
					    obj.init("");
					    lsxBean.DBclose();
						session.setAttribute("obj", obj);							
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpYeuCauChuyenKho.jsp";
						response.sendRedirect(nextJSP);
					}
				}
			}
			else if(action.equals("savesohoadon"))
			{
				System.out.println("Vào đây: "+action);
				if(!lsxBean.updateSoHoaDon())
				{
					lsxBean.createRs();
					session.setAttribute("lsxBean", lsxBean);
    				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpYeuCauChuyenKhoDisplay.jsp";   				
    				lsxBean.initView();   
    				response.sendRedirect(nextJSP);
				}
				else
				{
					IErpChuyenkhoSXList obj = new ErpChuyenkhoSXList();
					obj.setUserId(userId);
					obj.setIsnhanHang(task);
					
					String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
	 	    		geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
	 	    		
				    obj.init("");
				    lsxBean.DBclose();
					session.setAttribute("obj", obj);							
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpYeuCauChuyenKho.jsp";
					response.sendRedirect(nextJSP);
				}
			}
			else
			{
				if(action.equals("changeKHO"))
				{
					lsxBean.setSpList(new ArrayList<IYeucau>());
				}
				
				session.setAttribute("khochuyenIds", khoxuatId);
				/*session.setAttribute("nccchuyenId", nccchuyenId);*/
 
				lsxBean.createRs();
				
				session.setAttribute("lsxBean", lsxBean);
				lsxBean.settask(task);
				String nextJSP = "";
				
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpYeuCauChuyenKhoNew.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}
	
	private void ExportToExcel(OutputStream out, String id ) throws Exception
	{
		try
		{ 		
			String strfstream = getServletContext().getInitParameter("path") + "\\YeuCauChuyenKho.xlsm";
			
			FileInputStream fstream = new FileInputStream(strfstream);
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

			TaoBaoCao(workbook, id, 0);
			workbook.save(out);			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}

	}
	
	private void TaoBaoCao(com.aspose.cells.Workbook workbook, String id, int sheetNum ) throws Exception
	{
		try
		{
			com.aspose.cells.Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(sheetNum);
			com.aspose.cells.Cells cells = worksheet.getCells();
		
			com.aspose.cells.Cell cell = cells.getCell("B3");
			
			dbutils db = new dbutils();

			String sql = " select SUBSTRING (a.NgayYeuCau, 9, 2 ) as ngay, SUBSTRING (a.NgayYeuCau, 6, 2) as thang, SUBSTRING (a.NgayYeuCau, 0, 5) as nam, " + 
						 " 		isnull(b.Ma, '') as ma, isnull(b.TEN, '') as ten, a.noidungxuat_fk, a.trangthai " + 
						 " from ERP_YEUCAUCHUYENKHO a left join NHAPHANPHOI b on a.doituongnhan_fk = b.PK_SEQ " + 
						 " where a.PK_SEQ = " + id;
	
			System.out.println(":: INFO: " + sql);
			ResultSet rssql=db.get(sql);
			rssql.next();
			
			cell = cells.getCell(2,0);	
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);
			System.out.println("Ngày "+rssql.getString("ngay")+" Tháng "+rssql.getString("thang")+" Năm "+rssql.getString("nam"));
			cell.setValue("Ngày "+rssql.getString("ngay")+" Tháng "+rssql.getString("thang")+" Năm "+rssql.getString("nam"));
			
			cell = cells.getCell(3,0);
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);
			cell.setValue("Đơn vị dự trù :"+rssql.getString("ma")+"-"+rssql.getString("ten"));
			
			String ndx_fk = rssql.getString("noidungxuat_fk");
			String trangthai = rssql.getString("trangthai");
			rssql.close();
			
			String query = "";
			if( ndx_fk.equals("100073") && trangthai.equals("0") )  //Xuất cho chi nhánh đặt ==> in tất cả phiếu chưa chốt
			{
				query = " select '' STT, c.MA , c.TEN, d.DONVI, sum( cast( b.soluongyeucau as numeric(18, 3) ) ) as soluong, '' as GHICHU " + 
						   " from ERP_YEUCAUCHUYENKHO a inner join ERP_YEUCAUCHUYENKHO_SANPHAM b on a.PK_SEQ = b.yeucauchuyenkho_fk " + 
						   " 	inner join ERP_SANPHAM c on c.PK_SEQ = b.sanpham_fk \n"+
						   "	inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk \n"+
						   "where a.trangthai = 0 and noidungxuat_fk = '100073' and a.doituongnhan_fk in ( select doituongnhan_fk from ERP_YEUCAUCHUYENKHO where pk_seq = '" + id + "' ) " + 
						   "group by c.MA , c.TEN, d.DONVI ";
			}
			else  //Chỉ in phiếu này
			{
				query = " select '' STT, c.MA , c.TEN, d.DONVI, cast( b.soluongyeucau as numeric(18, 3) ) as soluong, a.LYDO as GHICHU " + 
						   " from ERP_YEUCAUCHUYENKHO a inner join ERP_YEUCAUCHUYENKHO_SANPHAM b on a.PK_SEQ = b.yeucauchuyenkho_fk " + 
						   " 	inner join ERP_SANPHAM c on c.PK_SEQ = b.sanpham_fk \n"+
						   "	inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk \n"+
						   "	where a.PK_SEQ = " + id;
			}
			
			System.out.println("::: LAY SP XUAT: " + query);
			ResultSet rs = db.get(query);

			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();

			int countRow = 5;
			int column = -1;
	
			rs = db.get(query);
			int j=1;
			while(rs.next())
			{
				for(int i =1;i <=socottrongSql ; i ++)
				{
					Color c = Color.WHITE;
					cell = cells.getCell(countRow,column + i);
					
					if(rsmd.getColumnName(i).equals("STT"))
					{
						cell.setValue(j);
						ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, true, 41);
					}
					else
					{
						if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i) == Types.INTEGER || rsmd.getColumnType(i) == Types.DECIMAL )
						{
							cell.setValue(rs.getDouble(i));
							ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, true, 41);
						}
						else
						{
							cell.setValue(rs.getString(i));
							ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, true, 0);
						}
					}
					
				}
				j++;
				++countRow;
			}
			
			if(rs!=null)rs.close();
			if(db!=null){
				db.shutDown();
			}
		}
		catch(Exception ex)
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
