package geso.traphaco.erp.servlets.lapkehoach;

import geso.traphaco.erp.beans.lapkehoach.*;
import geso.traphaco.erp.beans.lapkehoach.imp.*;
import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.oreilly.servlet.MultipartRequest;

public class ErpLenhmuahangdukienSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	private static final String UPLOAD_DIRECTORY = "C:\\upload\\excel\\";
	
    public ErpLenhmuahangdukienSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    String task = request.getParameter("task");
	    if(task == null)
	    	task = "";
	    
	    
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    IErpLenhmuahangdkList lmhdk = new ErpLenhmuahangdkList();
	    lmhdk.setCtyId(ctyId);
	    lmhdk.setUserId(userId);

	    String action = util.getAction(querystring);
	    String id = util.getId(querystring);
	    
	    if(action.equals("delete")){
	    	lmhdk.delete(id);
	    }
	
	    lmhdk.init();
		session.setAttribute("lmhdk", lmhdk);
	    
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLenhMuaHangDuKien.jsp";
		response.sendRedirect(nextJSP);
	    
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		String contentType = request.getContentType();
	    
	    HttpSession session = request.getSession();	
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));     
	    IErpLenhmuahangdkList lmhdk;
	    
		String action = request.getParameter("action");
		if (action == null){
			action = "";
		}
		lmhdk = new ErpLenhmuahangdkList();

		String nam = util.antiSQLInspection(request.getParameter("nam"));
		if(nam == null)
			nam = "";
		lmhdk.setNam(nam);
		
		String thang = util.antiSQLInspection(request.getParameter("thang"));
		if(thang == null)
			thang = "";
		lmhdk.setThang(thang);

		String loai = util.antiSQLInspection(request.getParameter("loai"));
		if(loai == null)
			loai = "";
		lmhdk.setLoai(loai);

		String spId = util.antiSQLInspection(request.getParameter("spId"));
		if(spId == null)
			spId = "";
		lmhdk.setSpId(spId);

		String nmId = util.antiSQLInspection(request.getParameter("nmId")); 
		if(nmId == null) nmId = "";
		lmhdk.setNhamayId(nmId);

		String msg = "";
		if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
		{
			MultipartRequest multi = new MultipartRequest(request, UPLOAD_DIRECTORY, 20000000, "UTF-8");
			action = util.antiSQLInspection(multi.getParameter("action"));
			if (action == null)
				action = "";
				
			System.out.println("Action Request encrypt: " + action);
			lmhdk = new ErpLenhmuahangdkList();

			lmhdk.setCtyId(ctyId);
			lmhdk.setUserId(userId);
				
			// HÀM NÀY LẤY CÁC GIÁ TRỊ TỪ BROWSER
			thang = util.antiSQLInspection(multi.getParameter("thang"));
			if(thang == null) thang = "";
			lmhdk.setThang(thang);
				
			nam = util.antiSQLInspection(multi.getParameter("nam"));
			if(nam == null) nam = "";
			lmhdk.setNam(nam);

			Enumeration files = multi.getFileNames();
			String filename = "";

			while (files.hasMoreElements())
			{
				String name = (String) files.nextElement();
				filename = multi.getFilesystemName(name);
			}
				
			System.out.println("1____READ EXCEL TOI DAY, FILE NAME......" + filename);
			if (filename != null && filename.length() > 0 )
			{
				System.out.println("___READ EXCEL FILE: ");
				this.readExcel(lmhdk, UPLOAD_DIRECTORY + filename, response);				
					
			}
				
			lmhdk.init();
			session.setAttribute("lmhdk", lmhdk);		    
		    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpLenhMuaHangDuKien.jsp";
			response.sendRedirect(nextJSP);
		} 
		else
		{
			try
			{
				if(action.equals("chuyenthanhPO"))
				{
					String Id = util.antiSQLInspection(request.getParameter("Id"));
					
					if(!lmhdk.getLoai().equals("100013"))
					{
						int soluong = Integer.parseInt(request.getParameter("soluongMua"));
						String spTTId = "";
						int soluongTT = 0;
						try
						{
							spTTId = util.antiSQLInspection(request.getParameter("SPTT" + Id));
							if(spTTId == null) spTTId = "";

							soluongTT = Integer.parseInt(request.getParameter("soluongTT" + Id));

							System.out.println("So luong mua: " + soluong);
						}
						catch(Exception er){ }

						msg = ChuyenThanhPO(spTTId, soluongTT, soluong, Id, userId, ctyId);
					}
					else //Bao bì, có đặt luôn maket
					{
						String soluongMua = request.getParameter("soluongMua");
						String maketMua = request.getParameter("maketMua");
						
						msg = ChuyenThanhPO_BaoBi(soluongMua, maketMua, Id, userId, ctyId);
					}
				}
				else if(action.equals("exportExcel")){
					response.setContentType("application/xlsm");

					response.setHeader("Content-Disposition", "attachment; filename=DE_NGHI_MUA_NGUYEN_LIEU_THANG.xlsx");

					lmhdk = new ErpLenhmuahangdkList();

					lmhdk.setCtyId(ctyId);
					lmhdk.setUserId(userId);

					// HÀM NÀY LẤY CÁC GIÁ TRỊ TỪ BROWSER
					thang = util.antiSQLInspection(request.getParameter("thang"));
					if(thang == null) thang = "";
					lmhdk.setThang(thang);

					nam = util.antiSQLInspection(request.getParameter("nam"));
					if(nam == null) nam = "";
					lmhdk.setNam(nam);

					System.out.println("Nam:" + nam + ", thang:" + thang);

					OutputStream out = response.getOutputStream();
					lmhdk.init();

					if(!CreatePivotTable(out, response, request, lmhdk))
					{
						response.setContentType("text/html");
						PrintWriter writer = new PrintWriter(out);
						writer.print("Không có dữ liệu trong thời gian này");
						writer.close();
					}

				}else if(action.equals("save")){
					dbutils db = new dbutils();

					String[] Ids = request.getParameterValues("dmhId");
					for (int i = 0; i < Ids.length; i++){
						String spTTID = util.antiSQLInspection(request.getParameter("SPTT" + Ids[i]));
						String soluongTT = util.antiSQLInspection(request.getParameter("soluongTT" + Ids[i]));

						if(spTTID != null & soluongTT != null){
							String query =  "UPDATE ERP_DONMUAHANGDUKIEN SET SANPHAMTT_FK = " + spTTID + ", " +
									"soluongTT = " + soluongTT + " WHERE PK_SEQ = " + Ids[i] + "";
							System.out.println(query);
							db.update(query);
						}
					}
					db.shutDown();
				}

				lmhdk.setCtyId(ctyId);
				lmhdk.setUserId(userId);
				lmhdk.init();
				lmhdk.setMsg(msg);

				session.setAttribute("lmhdk", lmhdk);  	
				session.setAttribute("userId", userId);

				response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpLenhMuaHangDuKien.jsp");
			}catch(Exception e){
				System.out.println(e.toString());
			}
		}		
	}
	

	private String ChuyenThanhPO(String vtttId, int soluongTT, int soluong, String Id, String userId, String ctyId) 
	{
		//Lay kich ban
		String msg = "";
		
		dbutils db = new dbutils();
		try 
		{
			db.getConnection().setAutoCommit(false);
			String ngaygiao = "";
			String ngaydat = "";
			String spId = "";
			
			String sql = "select NGAYDATHANG, NGAYNHANHANG, SANPHAM_FK AS SPID from ERP_DONMUAHANGDUKIEN where PK_SEQ = '" + Id + "'";
			ResultSet rs = db.get(sql);
			if(rs.next())
			{
				ngaydat = rs.getString("NGAYDATHANG");
				ngaygiao = rs.getString("NGAYNHANHANG");
				spId = rs.getString("SPID");
			}
			rs.close();
			
			//TẠO SỐ PO THEO QUY TẮC
			String nam = ngaygiao.substring(0, 4);
			String thang = ngaygiao.substring(5, 7);

			String query = " SELECT ISNULL( MAX(SOTUTANG_THEONAM), 0) AS MAXSTT, (SELECT PREFIX FROM ERP_DONVITHUCHIEN  "
					+ " WHERE PK_SEQ = 100092 ) AS PREFIX   "
					+ " FROM ERP_MUAHANG  DMH WHERE SUBSTRING(NGAYMUA, 0, 5) = " + nam
					+ " and     DMH.DONVITHUCHIEN_FK= 100092";
			System.out.println("Du lieu po sai  :" + query);
			String soPO = "";
			int sotutang_theonam = 0;
			ResultSet rsPO = db.get(query);
			if (rsPO.next()) 
			{
				sotutang_theonam = (rsPO.getInt("maxSTT") + 1);
				String prefix = rsPO.getString("PREFIX");
				String namPO = ngaygiao.substring(2, 4);
				String chuoiso = ("0000" + Integer.toString(sotutang_theonam)).substring(
						("0000" + Integer.toString(sotutang_theonam)).length() - 4,
						("0000" + Integer.toString(sotutang_theonam)).length());

				soPO = prefix + "-" + chuoiso + "/" + thang + "/" + namPO;

			}
			rsPO.close();
			
			// DONVITHUCHIEN_FK: 100092 - Phòng Kế Hoạch Cung Ứng
			query = "insert ERP_MUAHANG(NGAYMUA, SOPO, SOTUTANG_THEONAM, DONVITHUCHIEN_FK, NHACUNGCAP_FK, LOAIHANGHOA_FK, LOAISANPHAM_FK, TRANGTHAI, NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, TYPE, LOAI, CONGTY_FK, TONGTIENBVAT, VAT, TONGTIENAVAT, NGUONGOCHH, TIENTE_FK, TyGiaQuyDoi, DungSai, NOTE, SOTHAMCHIEU )  " +
					"select '" + ngaygiao + "', '" + soPO + "', '" + sotutang_theonam + "', '100092', null, '0', LOAISANPHAM_FK, 0, '" + getDateTime() + "', '" + userId + "', '" + getDateTime() + "', " +
					"'" + userId + "', '0', '0', '" + ctyId + "', '0', '0', '0', 'TN', '100000', '1', '0', " +
					"N'Được chuyển từ đề nghị mua nguyên liệu ( " + Id + " ) ', " + Id + "  " +
					"from ERP_SANPHAM where PK_SEQ = '" + spId + "'";
			System.out.println("___Tao mua hang: " + query);
			if(!db.update(query))
			{
				msg = "Không thể tạo mới ERP_MUAHANG: " + query;
				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}
			
			query = "SELECT SCOPE_IDENTITY() AS ID ";
			rs = db.get(query);
			rs.next();
			String dmhId = rs.getString("ID");
			rs.close();

			query = "insert ERP_MUAHANG_SP(MUAHANG_FK, SANPHAM_FK, DIENGIAI, SOLUONG, NGAYNHAN, DONVI, DONGIA, DONGIAVIET, THANHTIENVIET)  " +
					"select " + dmhId + ", a.pk_seq, a.ten, '" + soluong + "', '" + ngaygiao + "', isnull(b.Donvi, ''), '0', '0', '0'   " +
					"from ERP_SANPHAM a left join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq " +
					"where a.PK_SEQ = '" + spId + "'";
			System.out.println("___Tao mua hang - SP: " + query);
			if(!db.update(query))
			{
				msg = "Không thể tạo mới ERP_MUAHANG_SP: " + query;
				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}
			
			if(vtttId != null && vtttId.trim().length() > 0 )
			{
				query = "insert ERP_MUAHANG_SP(MUAHANG_FK, SANPHAM_FK, DIENGIAI, SOLUONG, NGAYNHAN, DONVI, DONGIA, DONGIAVIET, THANHTIENVIET)  " +
						"select " + dmhId + ", a.pk_seq, a.ten, '" + soluongTT + "', '" + ngaygiao + "', isnull(b.Donvi, ''), '0', '0', '0'   " +
						"from ERP_SANPHAM a left join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq " +
						"where a.PK_SEQ = '" + vtttId + "'";

				System.out.println("___Tao mua hang - SP: " + query);
				if(!db.update(query))
				{
					msg = "Không thể tạo mới ERP_MUAHANG_SP: " + query;
					db.getConnection().rollback();
					db.shutDown();
					return msg;
				}
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			
			return msg;
		} 
		catch (Exception e) 
		{
			msg = "Không thể tạo mới mua hàng: " + e.getMessage();
			db.update("rollback");
			db.shutDown();
			return msg;
		}

	}

	private String ChuyenThanhPO_BaoBi(String soluongMua, String maketMua, String Id, String userId, String ctyId) 
	{
		//Lay kich ban
		String msg = "";
		
		dbutils db = new dbutils();
		try 
		{
			db.getConnection().setAutoCommit(false);
			String ngaygiao = "";
			String ngaydat = "";
			String spId = "";
			
			String sql = "select NGAYDATHANG, NGAYNHANHANG, SANPHAM_FK AS SPID from ERP_DONMUAHANGDUKIEN where PK_SEQ = '" + Id + "'";
			ResultSet rs = db.get(sql);
			if(rs.next())
			{
				ngaydat = rs.getString("NGAYDATHANG");
				ngaygiao = rs.getString("NGAYNHANHANG");
				spId = rs.getString("SPID");
			}
			rs.close();
			
			
			//TẠO SỐ PO THEO QUY TẮC
			String nam = ngaygiao.substring(0, 4);
			String thang = ngaygiao.substring(5, 7);

			String query = " SELECT ISNULL( MAX(SOTUTANG_THEONAM), 0) AS MAXSTT, (SELECT PREFIX FROM ERP_DONVITHUCHIEN  "
					+ " WHERE PK_SEQ = 100092 ) AS PREFIX   "
					+ " FROM ERP_MUAHANG  DMH WHERE SUBSTRING(NGAYMUA, 0, 5) = " + nam
					+ " and     DMH.DONVITHUCHIEN_FK= 100092";
			System.out.println("Du lieu po sai  :" + query);
			String soPO = "";
			int sotutang_theonam = 0;
			ResultSet rsPO = db.get(query);
			if (rsPO.next()) {
				sotutang_theonam = (rsPO.getInt("maxSTT") + 1);
				String prefix = rsPO.getString("PREFIX");
				String namPO = ngaygiao.substring(2, 4);
				String chuoiso = ("0000" + Integer.toString(sotutang_theonam)).substring(
						("0000" + Integer.toString(sotutang_theonam)).length() - 4,
						("0000" + Integer.toString(sotutang_theonam)).length());

				soPO = prefix + "-" + chuoiso + "/" + thang + "/" + namPO;

			}
			rsPO.close();
			
			// DONVITHUCHIEN_FK: 100092 - Phòng Kế Hoạch Cung Ứng
			query = "insert ERP_MUAHANG(NGAYMUA, soPO, sotutang_theonam, DONVITHUCHIEN_FK, NHACUNGCAP_FK, LOAIHANGHOA_FK, LOAISANPHAM_FK, TRANGTHAI, NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, TYPE, LOAI, CONGTY_FK, TONGTIENBVAT, VAT, TONGTIENAVAT, NGUONGOCHH, TIENTE_FK, TyGiaQuyDoi, DungSai, NOTE, SOTHAMCHIEU )  " +
					"select '" + ngaygiao + "', '" + soPO + "', '" + sotutang_theonam + "', '100092', null, '0', LOAISANPHAM_FK, 0, '" + getDateTime() + "', '" + userId + "', '" + getDateTime() + "', " +
					"'" + userId + "', '0', '0', '" + ctyId + "', '0', '0', '0', 'TN', '100000', '1', '0', " +
					"N'Được chuyển từ đề nghị mua nguyên liệu ( " + Id + " ) ', " + Id + "  " +
					"from ERP_SANPHAM where PK_SEQ = '" + spId + "'";
			System.out.println("___Tao mua hang: " + query);
			if(!db.update(query))
			{
				msg = "Không thể tạo mới ERP_MUAHANG: " + query;
				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}
		
			query = "SELECT SCOPE_IDENTITY() AS ID ";
			rs = db.get(query);
			rs.next();
			String dmhId = rs.getString("ID");
			rs.close();

			/*query = 
				"SELECT DISTINCT MK.PK_SEQ AS MKID, MK.MA + ' - ' + MK.DIENGIAI  AS MAKET, MK.TUNGAY, MK.DENNGAY \n " +
				
				"FROM ERP_SANPHAM SP 	 \n " +
				"INNER JOIN MARQUETTE MK ON MK.SANPHAM_FK = SP.PK_SEQ \n " +
				
				"WHERE SP.PK_SEQ = " + spId + " AND MK.TUNGAY <= (SELECT NGAYDATHANG FROM ERP_DONMUAHANGDUKIEN WHERE PK_SEQ = " + Id + ") \n " +
				"AND MK.DENNGAY >= (SELECT NGAYDATHANG FROM ERP_DONMUAHANGDUKIEN WHERE PK_SEQ = " + Id + ") " ;

			rs = db.get(query);
			int i = 0;
			while(rs.next()){
				if(i == 0){
					query = "insert ERP_MUAHANG_SP(MUAHANG_FK, SANPHAM_FK, DIENGIAI, SOLUONG, SOLUONG_NEW, NGAYNHAN, DONVI, DONGIA, DONGIAVIET, THANHTIENVIET, IDMARQUETTE)  " +
							"select " + dmhId + ", a.pk_seq, a.ten, '" + soluong + "', '" + soluong + "', '" + ngaygiao + "', isnull(b.Donvi, ''), '0', '0', '0', " + rs.getString("MKID") + " " + 
							"from ERP_SANPHAM a left join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq " +
							"where a.PK_SEQ = '" + spId + "'";
					System.out.println("___Tao mua hang - SP: " + query);
					if(!db.update(query))
					{
						msg = "Không thể tạo mới ERP_MUAHANG_SP: " + query;
						db.getConnection().rollback();
						db.shutDown();
						return msg;
					}
				}else{
					query = "insert ERP_MUAHANG_SP(MUAHANG_FK, SANPHAM_FK, DIENGIAI, SOLUONG, SOLUONG_NEW, NGAYNHAN, DONVI, DONGIA, DONGIAVIET, THANHTIENVIET, IDMARQUETTE)  " +
							"select " + dmhId + ", a.pk_seq, a.ten, '0', '0', '" + ngaygiao + "', isnull(b.Donvi, ''), '0', '0', '0', " + rs.getString("MKID") + " " + 
							"from ERP_SANPHAM a left join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq " +
							"where a.PK_SEQ = '" + spId + "'";
					System.out.println("___Tao mua hang - SP: " + query);
					if(!db.update(query))
					{
						msg = "Không thể tạo mới ERP_MUAHANG_SP: " + query;
						db.getConnection().rollback();
						db.shutDown();
						return msg;
					}
							
				}
				i++;
			}*/
			
			//ANH HẢI YÊU CẦU CHỖ NÀY LẠI: 3. đề nghị mua nguyên liệu - chọn loại bao bì -> hiện ra maket rồi, nhưng phải cho nhập số lượng mua theo mmaket chứ ko phải số lượng nằm ngoaài như hiện nay 
			String[] soluong = soluongMua.split(";");
			String[] maket = maketMua.split(";");
			for( int i = 0; i < soluong.length; i++ )
			{
				if( soluong[i].trim().length() > 0 )
				{
					query = "insert ERP_MUAHANG_SP(MUAHANG_FK, SANPHAM_FK, DIENGIAI, SOLUONG, SOLUONG_NEW, NGAYNHAN, DONVI, DONGIA, DONGIAVIET, THANHTIENVIET, IDMARQUETTE)  " +
							"select " + dmhId + ", a.pk_seq, a.ten, '" + soluong[i].replaceAll(",", "") + "', '" + soluong[i].replaceAll(",", "") + "', '" + ngaygiao + "', isnull(b.Donvi, ''), '0', '0', '0', " + maket[i] + " " + 
							"from ERP_SANPHAM a left join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq " +
							"where a.PK_SEQ = '" + spId + "'";
					System.out.println("___Tao mua hang - SP: " + query);
					if(!db.update(query))
					{
						msg = "Không thể tạo mới ERP_MUAHANG_SP: " + query;
						db.getConnection().rollback();
						db.shutDown();
						return msg;
					}
				}
			}
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			
			return msg;
		} 
		catch (Exception e) 
		{
			msg = "Không thể tạo mới mua hàng: " + e.getMessage();
			db.update("rollback");
			db.shutDown();
			return msg;
		}

	}
	
	private String getNgayBatDau(String ngayhoanthanh, float sogio) 
	{
		//ngay lam 8h, bat dau tinh tu 8h AM
		int ngay = Math.round( sogio / 8 );
		Calendar c1 = Calendar.getInstance();
		
		String[] arr = ngayhoanthanh.split("-");
		c1.set(Integer.parseInt( arr[0]), Integer.parseInt( arr[1]) - 1, Integer.parseInt( arr[2]) );
		
		c1.add(Calendar.DATE, (-1) * ngay);
		

		Calendar c2 = Calendar.getInstance();
		c2.set(Integer.parseInt( arr[0]), Integer.parseInt( arr[1]) - 1, Integer.parseInt( arr[2]) );
	
		while( c2.getTime().compareTo(c1.getTime()) > 0 )
		{
			//neu la ngay chu nhat thi phai tang 1 len 1 ngay
			if(c2.get(Calendar.DAY_OF_WEEK) == 8 || c2.get(Calendar.DAY_OF_WEEK) == 1)
			{
				c1.add(Calendar.DATE, -1);
			}
			
			c2.add(Calendar.DATE, -1);
		}
		
		String ngaykt = Integer.toString(c1.get(Calendar.DATE));
		if(ngaykt.trim().length() < 2)
			ngaykt = "0" + ngaykt;
		
		String thangkt = Integer.toString(c1.get(Calendar.MONTH) + 1);
		if(thangkt.trim().length() < 2)
			thangkt = "0" + thangkt;
		
		//System.out.println("___Date ngay bat dau: " + c1.get(Calendar.DAY_OF_WEEK) );
		System.out.println("1.Ngay bat dau tinh duoc: " + Integer.toString(c1.get(Calendar.YEAR)) + "-" + thangkt + "-" + ngaykt);
		
		return Integer.toString(c1.get(Calendar.YEAR)) + "-" + thangkt + "-" + ngaykt;
	}
	

	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	void getRequestEcrypt(Utility util, IErpLenhmuahangdkList lsxdklist, MultipartRequest multirequest, HttpServletRequest request, int i)
	{
	
		String thang = "";
		thang = util.antiSQLInspection(multirequest.getParameter("thang"));
		if(thang == null) thang = "";
		lsxdklist.setThang(thang);
		System.out.println("Thang: " + thang);
		
		String nam = "";
		nam = util.antiSQLInspection(multirequest.getParameter("nam"));
		if(nam == null) nam = "";
		lsxdklist.setNam(nam);
		System.out.println("Nam: " + nam);
		
		String filename = "" ;
		if(i == 0){
			filename = request.getParameter("filename");
		}else{
			filename = multirequest.getParameter("filename");
		}
		if (filename == null)
			filename = "";
	}

	public void readExcel(IErpLenhmuahangdkList lmhdklist, String fileName, HttpServletResponse response) throws IOException
	{
		dbutils db = new dbutils();
		
		OutputStream out = response.getOutputStream();
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		
		fstream = new FileInputStream(fileName);
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007);
		
		Worksheets worksheets = workbook.getWorksheets();		

		String nam = lmhdklist.getNam();
		String thang = lmhdklist.getThang();
		
		//DÒNG VÀ CỘT TÍNH TỪ 0
		
		System.out.println("BẮT ĐẦU ĐỌC FILE");
		
		//LẤY THÁNG VÀ NĂM		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		Calendar c = Calendar.getInstance();
		
		//FILE BẮT ĐẦU CHỨA DỮ LIỆU TỪ DÒNG SỐ 9 - CÓ 3 SHEET
		try
		{
			db.getConnection().setAutoCommit(false);
			String sql = "";
			
			Worksheet readSheet = worksheets.getSheet("KH_NL");
				
			//LẤY SỐ DÒNG CỦA SHEET ĐANG ĐỌC. TÍNH TỪ 0.			
			int readSheetRow = readSheet.getLastRowIndex() + 1;
			
			for(int i = 6; i < readSheetRow;i++)
			{
					//CỘT 1 - MÃ SẢN PHẨM
					//CỘT 4 - ĐƠN GIÁ
					//CỘT 8 - SỐ LƯỢNg
				String ID =  readSheet.getCell(i, 1).getStringValue();	
				String maSP =  readSheet.getCell(i, 2).getStringValue();				
					
				if(maSP.length()>0)
				{
					String hamluong =  readSheet.getCell(i, 4).getStringValue().replaceAll(",", "");
					if(hamluong.length() == 0) 
						hamluong = "100";
					else 
						hamluong.replaceAll(",", "");
					
					String hamam =  readSheet.getCell(i, 5).getStringValue().replaceAll(",", "");
					if(hamam.length() == 0) 
						hamam = "0";
					else 
						hamam.replaceAll(",", "");
					
					String soluong =  readSheet.getCell(i, 6).getStringValue().replaceAll(",", "");
					if(soluong.length() == 0) 
						soluong = "0";
					else
						soluong.replaceAll(",", "");
					
					String soluongTT =  readSheet.getCell(i, 9).getStringValue().replaceAll(",", "");
					if(soluong.length() == 0) 
						soluong = "0";
					else
						soluong.replaceAll(",", "");

					String ngaydat =  readSheet.getCell(i, 10).getStringValue().trim();
					ngaydat = ngaydat.substring(6, 10) + "-" + ngaydat.substring(3, 5) + "-" + ngaydat.substring(0, 2);
					
					String ngaynhan =  readSheet.getCell(i, 11).getStringValue();
					ngaynhan = ngaynhan.substring(6, 10) + "-" + ngaynhan.substring(3, 5) + "-" + ngaynhan.substring(0, 2);
					
					sql += 	" UPDATE ERP_DONMUAHANGDUKIEN SET HAMAM = " + hamam + ", HAMLUONG = " + hamluong + ", SOLUONG = " + soluong + ", " +
							" SOLUONGTT = " + soluongTT + ", NGAYDATHANG = '" + ngaydat + "', NGAYNHANHANG = '" + ngaynhan + "' " +
							" WHERE PK_SEQ = " + ID + " ";

					System.out.println(sql);
				}
			}
			
			sql += "UPDATE ERP_DONMUAHANGDUKIEN SET SOLUONGTT = 0 WHERE SANPHAMTT_FK IS NULL ";
			
			db.update(sql);

			System.out.println("KẾT THÚC ĐỌC SHEET: "+readSheet.getName());
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch(SQLException e)
		{
			
		}
			
		db.shutDown();
	
		System.out.println("KẾT THÚC ĐỌC FILE");
		
	}
	
	private boolean CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IErpLenhmuahangdkList obj) throws Exception 
	{
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\DE_NGHI_MUA_NGUYEN_LIEU_THANG.xlsx");
		System.out.println("I am in the Createpivot");
		
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007);
		
		FillData(workbook, obj, "KH NL");

		workbook.save(out);
		fstream.close();
		return true;	
	}

	private void FillData(Workbook workbook, IErpLenhmuahangdkList obj, String sheet) {
		System.out.println("I am in the Filldata");
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet;
		Cell cell;
		String query;
		String t1 = "", t2 = "", t3 = "", n1 = "", n2 = "", n3 = "";
		
		
		worksheet = worksheets.getSheet("KH_NL");
		
		Cells cells = worksheet.getCells();
		cell = cells.getCell("D4");
		cell.setValue("Tháng " + (t1.length() == 1? "0" + t1:t1) + " năm " + n1 );
		
		ResultSet rs = obj.getLenhmuahangdkRs();
		try{
			if(rs != null){
				int row = 7;
				Style style;
				Font font;
				//worksheet = worksheets.getSheet(sheet);
				cells = worksheet.getCells();
				int i = 1;
				//System.out.println("OK:" + row);
				while(rs.next()){

					cell = cells.getCell("A" + row );
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.CENTER);
					cell.setStyle(style);
					ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, "" + i , 0);

					cell = cells.getCell("B" + row );
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.CENTER);
					cell.setStyle(style);
					ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, rs.getString("DMHID"), 0);
					//System.out.println("OK:" + row);
					
					cell = cells.getCell("C" + row);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, rs.getString("MA"), 0);
					//System.out.println("OK:" + row);

					cell = cells.getCell("D" + row );
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					ReportAPI.getCellStyle(cell, Color.BLACK, true, 11, rs.getString("TEN"), 0);
					
					cell = cells.getCell("E" + row);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.RIGHT);							
					style.setNumber(3);
					font = style.getFont();
					font.setName("Times New Roman");
					font.setSize(11);
					style.setFont(font);
					cell.setStyle(style);
//					cell.setValue(rs.getDouble("TONHIENTAI"));
					
										
					cell = cells.getCell("F" + row);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.RIGHT);							
					style.setNumber(3);
					font = style.getFont();
					font.setName("Times New Roman");
					font.setSize(11);
					style.setFont(font);
					cell.setStyle(style);
//					cell.setValue(rs.getDouble("TONANTOAN"));

					cell = cells.getCell("G" + row );
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.RIGHT);							
					style.setNumber(3);
					font = style.getFont();
					font.setName("Times New Roman");
					font.setSize(11);
					style.setFont(font);
					cell.setStyle(style);
					cell.setValue(rs.getDouble("SOLUONG"));
					
					cell = cells.getCell("H" + row);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.RIGHT);							
					style.setNumber(3);
					font = style.getFont();
					font.setName("Times New Roman");
					font.setSize(11);
					style.setFont(font);
					cell.setStyle(style);
					cell.setValue(rs.getString("MATT"));

					cell = cells.getCell("I" + row );
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.RIGHT);							
					style.setNumber(3);
					font = style.getFont();
					font.setName("Times New Roman");
					font.setSize(11);
					style.setFont(font);
					cell.setStyle(style);
					cell.setValue(rs.getString("TENTT"));

					cell = cells.getCell("J" + row);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.RIGHT);							
					style.setNumber(3);
					font = style.getFont();
					font.setName("Times New Roman");
					font.setSize(11);
					style.setFont(font);
					cell.setStyle(style);
					cell.setValue(rs.getDouble("SOLUONGTT"));

					cell = cells.getCell("K" + row);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.RIGHT);	
					style.setTextWrapped(true);
					font = style.getFont();
					font.setName("Times New Roman");
					font.setSize(11);
					style.setFont(font);
					cell.setStyle(style);
					cell.setValue(rs.getString("NGAYDATHANG").substring(8, 10) + "/" + rs.getString("NGAYDATHANG").substring(5, 7) + "/" + rs.getString("NGAYDATHANG").substring(0, 4));
					
					cell = cells.getCell("L" + row);
					style = cell.getStyle();
					style.setHAlignment(TextAlignmentType.RIGHT);	
					style.setTextWrapped(true);
					font = style.getFont();
					font.setName("Times New Roman");
					font.setSize(11);
					style.setFont(font);
					cell.setStyle(style);
					cell.setValue(rs.getString("NGAYNHANHANG").substring(8, 10) + "/" + rs.getString("NGAYNHANHANG").substring(5, 7) + "/" + rs.getString("NGAYNHANHANG").substring(0, 4));
					
					row++;
					i++;
				}
				rs.close();
			}
				
			
		}catch(java.sql.SQLException e){
				System.out.println(e.toString());
		}
			
		
		db.shutDown();
	}

	private void setCategoryStyle(Cells cells ,Cell cell)
	{
		Cell cell1 = cells.getCell("AZ1");
		Style style;	
		style = cell1.getStyle();
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
        cell.setStyle(style);
	}
	
}
