package geso.traphaco.center.servlets.donmuahang;

import geso.traphaco.center.beans.donmuahang.IDonmuahangList;
import geso.traphaco.center.beans.donmuahang.IERP_DonDatHang;
import geso.traphaco.center.beans.donmuahang.imp.DonmuahangList;
import geso.traphaco.center.beans.donmuahang.imp.ERP_DonDatHang;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.db.sql.dbutils_syn;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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

@WebServlet("/DonmuahangKmSvl")
public class DonmuahangKmSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    public DonmuahangKmSvl() {
        super();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IDonmuahangList obj;
		String userId;

		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    out = response.getWriter();
	    
	    String querystring = request.getQueryString();
	    String action = util.getAction(querystring);
	   
	 
	    
	    String ddhId = util.getId(querystring);
	    
	    userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    obj = new DonmuahangList();
	    if (action.equals("delete")){
	    	Delete(ddhId);
	    }else if(action.equals("SAboduyet")){
	    	//trang thai dang o mua sale admin vua duyet // trangthai=2 thi co the cap nhat ve trang thai=1
	    	SABODUYENT(ddhId);
	    }else if(action.equals("chot")){
	    	SADuyet(ddhId,userId);
	    }else if(action.equals("huychot"))
	    {
	    	obj.SetMsg(HuyChot(ddhId,userId));
	    }
	    
	    out.println("userId.........."+userId);
	   
	    obj.setUserId(userId);
	    obj.initDonHangKm("");
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Center/DonMuaHangKhuyenMai.jsp";
		response.sendRedirect(nextJSP);
	}  	
	
	private void SADuyet(String ddhId,String userid) {
		
		dbutils db = new dbutils();
		String query = "update  dondathang set trangthai='2',nguoiduyet='"+userid+"',dateduyet=getdate() where pk_Seq='" + ddhId + "'";
		System.out.println("Get SQL : "+query);
		db.update(query);
		db.shutDown();
		this.DonDatHangToERP(ddhId);
		
		
	}
	

	private void DonDatHangToERP(String idddh)
	{
		String PO="";//pk_seq của bảng dondathang
		String PO_BK="";
		dbutils db=new dbutils();	
		dbutils_syn db_syn=new dbutils_syn();
		try{
 
			 String sql= " SELECT DDH_SP.SOTT,KHO.TEN as khoten,KBH.TEN as kbhten,DDH.NGAYDAT,Replace(convert(nvarchar(10),  DATEDUYET , 102) , '.', '-' ) AS NGAYDUYET    "+ 
						 "  , DDH.PK_SEQ AS PO,DDH.DATEDUYET,NPP.MA AS MANPP, SP.MA AS MASP   "+ 
						 "  ,DDH_SP.SOLUONGDUYET/CAST(QC.SOLUONG1 AS DECIMAL(18,5) )AS SOLUONGDUYET ,   "+ 
						 "  isnull(DDH_SP.CHIETKHAU,0) as CKDH,ISNULL(DDH_SP.SCHEME,'') AS SCHEME,DDH.LOAIDONHANG, ISNULL( DDH.GiaVanChuyen,0) AS GiaVanChuyen , " +
						 "  0 AS CKTM,ISNULL( ddh.CHIETKHAU,0) AS CKTT,ISNULL(DDH.HINHTHUCVANCHUYEN,'CTVC') AS HINHTHUCVANCHUYEN,ISNULL(ddh.GhiChu,'') as GhiChu "+ 
						 "   FROM DONDATHANG DDH INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=DDH.NPP_FK  "+ 
						 "  INNER JOIN DONDATHANG_SP DDH_SP ON DDH_SP.DONDATHANG_FK=DDH.PK_SEQ   "+ 
						 "  INNER JOIN SANPHAM SP ON SP.PK_SEQ=DDH_SP.SANPHAM_FK   "+ 
						 "  INNER JOIN QUYCACH QC ON  QC.SANPHAM_FK=DDH_SP.SANPHAM_FK   "+ 
						 "  AND QC.DVDL2_FK=100018 AND QC.DVDL1_FK=SP.DVDL_FK   "+ 
						 "  LEFT JOIN KENHBANHANG KBH ON KBH.PK_SEQ=DDH.KBH_FK  "+ 
						 "  LEFT JOIN ERP_KHOTT KHO  ON KHO.PK_SEQ=DDH.KHOTT_FK  "+ 
						 "  WHERE DDH_SP.SOLUONGDUYET >0 AND DDH.TRANGTHAI='2' and   isnull( DDH.DACHUYEN,'0') <>'1'   AND DDH.PK_SEQ="+idddh +
						 "  UNION ALL " +
						 "  SELECT '0' as STT, KHO.TEN as khoten,KBH.TEN as kbhten,DDH.NGAYDAT,Replace(convert(nvarchar(10),  DATEDUYET , 102) , '.', '-' ) AS NGAYDUYET "+    
						 "  , DDH.PK_SEQ AS PO,DDH.DATEDUYET,NPP.MA AS MANPP, ''  AS MASP     ,0 SOLUONGDUYET ,    "+
						 "  0 as CKDH,'' AS SCHEME,DDH.LOAIDONHANG,ISNULL(DDH.GiaVanChuyen,0) AS GiaVanChuyen  ,ISNULL( DDH.CHIETKHAUTHUONGMAI,0)AS CHIETKHAUTHUONGMAI ,  DDH.CHIETKHAU AS CTTT,ISNULL(DDH.HINHTHUCVANCHUYEN,'CTVC') AS HINHTHUCVANCHUYEN,ISNULL(ddh.GhiChu,'') as GhiChu "+
						 "  FROM DONDATHANG DDH "+
						 "   INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=DDH.NPP_FK    "+
						 "  LEFT JOIN KENHBANHANG KBH ON KBH.PK_SEQ=DDH.KBH_FK     "+
						 "  LEFT JOIN ERP_KHOTT KHO  ON KHO.PK_SEQ=DDH.KHOTT_FK  "+
						 "  WHERE  DDH.TRANGTHAI='2'  and   isnull( DDH.DACHUYEN,'0') <>'1'    "+
						 "  AND DDH.PK_SEQ= " +idddh +    
						 "  ORDER BY DDH.PK_SEQ ";
			 
				
				ResultSet rs=db.get(sql);	 
		if(rs!=null)
		{
			while( rs.next()) 
			{
				PO=rs.getString("PO");
				String query=" INSERT INTO [TBL_DONDATHANG]([MADDH],[KENH],[MANPP],[MASP],[SOLUONG],[KHODH],[NGAYPO],[CKDONGHANG],[CKTRUCTIEP] "+
							 " ,[CKTHUONGMAI],[SCHEME],[LOAIDONHANG],[HTVANCHUYEN],[DGVanChuyen],[S1],[S3])  VALUES " +
							 "('"+PO+"','"+rs.getString("kbhten")+"','"+rs.getString("MANPP").trim()+"','"+rs.getString("MASP")+"' ,"+rs.getString("SOLUONGDUYET")+", " +
							 		"'"+rs.getString("khoten")+"','"+rs.getString("ngaydat")+"','"+rs.getString("CKDH")+"',"+rs.getDouble("CKTT")+","+rs.getDouble("CKTM")+",'"+rs.getString("SCHEME")+"'," +
							 				" '"+rs.getString("LOAIDONHANG")+"','"+rs.getString("HINHTHUCVANCHUYEN")+"','"+rs.getString("GiaVanChuyen")+"','"+rs.getString("SOTT")+"',N'"+rs.getString("GhiChu")+"')";
				
				if(!db_syn.update(query))
				{
				 	System.out.println("__khong the cap nhat ERP "+query);
				}else 
				{
					if(!PO.equals(PO_BK)){
							PO_BK=PO;
							query="UPDATE DONDATHANG SET DACHUYEN=1 WHERE PK_SEQ='"+PO+"'";
							if(!db.update(query))
							{
								System.out.println("__khong the cap nhat "+query); 
							}
					}
				}
			}
			rs.close();
			
		}
		} catch (Exception e)
		{
			e.printStackTrace();
		}finally {
			db.shutDown();
			db_syn.shutDown();
		}
		
	}
	

	

	private void SABODUYENT(String ddhId) {
		
		dbutils db = new dbutils();
		String query = "update  dondathang set trangthai='1' where pk_Seq='" + ddhId + "'";
		db.update(query);
		db.shutDown();
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    IDonmuahangList obj;
	    String userId;
	    Utility util = new Utility();
	    
	    obj = new DonmuahangList();		
		HttpSession session = request.getSession();
	    userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }    
	    
	     	    
	    
	    if(action.equals("view") || action.equals("next") || action.equals("prev")){
	    	//obj = new DonmuahangList();
	    	obj.setUserId(userId);
	    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
    		
        	obj.createDdhlist("");
        	
        	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
        		
        	session.setAttribute("obj", obj);
    	    		
        	String nextJSP = "/TraphacoHYERP/pages/Center/DonMuaHangKhuyenMai.jsp";	    	
        	
    	    response.sendRedirect(nextJSP);

	    }else if(action.equals("new")){
	    	IERP_DonDatHang objddh=new ERP_DonDatHang();
	    	objddh.Init();
	    	session.setAttribute("obj", objddh);
	    	String nextJSP = "/TraphacoHYERP/pages/Center/DonMuaHangKhuyenMaiNew.jsp";	 
	    	response.sendRedirect(nextJSP);
	    }else if(action.equals("toExcel"))
	    {
	    	request.setCharacterEncoding("utf-8");
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=DonDatHang.xlsm");
			OutputStream out = response.getOutputStream();
			String query =getSearchQuery(request, obj, userId);  
			try
			{
				if (!CreatePivotTable(out, obj, query))
				{
					response.setContentType("text/html");
					PrintWriter writer = new PrintWriter(out);
					writer.print("Xin loi khong co bao cao trong thoi gian nay");
					writer.close();
				}
			} catch (Exception e)
			{
				e.printStackTrace();
				obj.SetMsg("Khong co bao cao voi dieu kien da chon !");
			}	
	    }
	    else{
	    	    
    	String search = getSearchQuery(request, obj, userId);   	
    	obj.setUserId(userId);
    	obj.createDdhlist(search);
			
    	session.setAttribute("obj", obj);
	    		
    	String nextJSP = "/TraphacoHYERP/pages/Center/DonMuaHangKhuyenMai.jsp";	    	
    	
	    response.sendRedirect(nextJSP);
	    }
	    

	}   
	private String HuyChot(String ddhId,String userid) 
	{
		System.out.println("[Huy Chot]");
		dbutils db = new dbutils();
		try
		{
			db.getConnection().setAutoCommit(false);
			String query = "UPDATE DONDATHANG SET TRANGTHAI=1,DACHUYEN=0,NGUOIHUY='"+userid+"',DATEHUY=GETDATE(),HUYCHOT=1 WHERE TRANGTHAI=2 AND PK_SEQ='"+ddhId+"' AND LOAIDONHANG=1 and kmtt=1 ";
			if(db.updateReturnInt(query)<=0)
			{
				db.update("rollback");
				System.out.println("[Log1.1]"+query);
				return "Vui lòng kiểm tra lại đơn hàng "+query;
			}
			query="SELECT PK_SEQ as ddhId,NGAYDAT FROM DONDATHANG where pk_seq='"+ddhId+"' ";
			ResultSet rs=db.get(query);
			String ngaydat="";
			while(rs.next())
			{
				ngaydat=rs.getString("ngaydat");
			}
			if(rs!=null)rs.close();
			dbutils_syn db_syn = new dbutils_syn();
			query=" select count(*) as sodong from tbl_huydonhang where maddh='"+ddhId+"' ";
			rs=db_syn.get(query);
			int sodong=0;
			while(rs.next())
			{
				sodong=rs.getInt(1);
			}
			if(rs!=null)rs.close();
			
			if(sodong==0)
			{
				query=" insert into tbl_huydonhang(maddh,ngaydh) " +
						" select '"+ddhId+"','"+ngaydat+"' ";
				if(!db_syn.update(query))
				{
					System.out.println("[Log1.2]"+query);
					db.update("rollback");
					return "Không thể chuyển ĐH qua FAST "+query;
				}
			}else 
			{
				query="update tbl_huydonhang xstatus=null,ngaydh='"+ngaydat+"' where maddh='"+ddhId+"' and xstatus is not null ";
				if(db_syn.updateReturnInt(query)<=0)
				{
					System.out.println("[Log1.2]"+query);
					db.update("rollback");
					return "Không thể chuyển ĐH qua FAST "+query;
				}
			}
			query= "delete from tbl_dondathang where maddh='"+ddhId+"'" ;
			if(!db_syn.update(query))
			{
				System.out.println("[Log1.2]"+query);
				db.update("rollback");
				return "Không thể chuyển ĐH qua FAST "+query;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			db_syn.shutDown();
		} catch (Exception e)
		{
			e.printStackTrace();
			db.update("rollback");
			System.out.println("[Log1.3]"+e.getMessage());
			return "Có lỗi trong quá trình truyền tải "+e.getLocalizedMessage();
		}
		return "";
	}

	private String getSearchQuery(HttpServletRequest request, IDonmuahangList obj, String userId)
	{
		
		Utility util = new Utility();
		String sku = util.antiSQLInspection(request.getParameter("sku"));
    	if (sku == null)
    		sku = "";
    	
    	sku = sku.split("-")[0].trim();
    	obj.setSKU(sku);
    	
    	String tungay = util.antiSQLInspection(request.getParameter("tungay"));
    	if (tungay == null)
    		tungay = "";    	
    	obj.setTungay(tungay);

    	String denngay = util.antiSQLInspection(request.getParameter("denngay"));
    	if (denngay == null)
    		denngay = "";    	
    	obj.setDenngay(denngay);
    	   	
    	String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));   	
    	if (trangthai == null)
    		trangthai = "";    	
	
    	if (trangthai.equals("0"))
    		trangthai = "";
    	
    	obj.setTrangthai(trangthai);
    	
    	String kvId = util.antiSQLInspection(request.getParameter("kvId"));   	
    	if (kvId == null)
    		kvId = "";    	
	
    	if (kvId.equals("0"))
    		kvId = "";
    	
    	obj.setKvId(kvId);

    	String so = request.getParameter("so");
    	if(so == null)
    		so = "";
    	if(so.equals("0"))
    		so = "";
    	obj.setSO(so);
    	
    	String nppId = util.antiSQLInspection(request.getParameter("nppId"));
    	if(nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	geso.traphaco.center.util.Utility ut = new geso.traphaco.center.util.Utility();	
    	String query1 =
			"select isnull(a.loaidonhang,0) as loaidonhang,a.thang,a.nam ,a.ngaydat, a.pk_seq as chungtu,  f.ten as nppTen, e.donvikinhdoanh,  "+  
	    	"		 isnull( a.sotienavat,0) as sotienavat,  b.ten as nguoitao, c.ten as nguoisua, a.trangthai,isnull( a.soid ,'0') as soid,  "+
	    	"		 ISNULL(nh.CHUNGTU,'NA') as SoHoaDon,isnull (nh.ngaychungtu,'')as ngayhd, isnull(nh.sotienbvat,'0') as tienhd ,  "+
	    	"		 isnull(nh.ngaynhan,'') as ngaynhan,isnull(a.huychot,0) as huychot,a.kbh_fk,ISNULL(CHIETKHAUTHUONGMAI,0) AS CKTM, "+
	    	"		 ISNULL(IsDuyetCKTM,0) AS IsDuyetCKTM ,nd.TEN as NguoiDuyet,a.DATEDUYET "+
	    	"from dondathang a left join  "+
	    	"	nhanvien b on  a.nguoitao = b.pk_seq "+   
	    	"	left join  nhanvien c on a.nguoisua = c.pk_seq "+  
	    	"	left join NHANVIEN nd on nd.PK_SEQ=a.NGUOIDUYET "+
	    	"	left join donvikinhdoanh e on a.dvkd_fk = e.pk_seq   "+
	    	"	left join  nhaphanphoi  f  on a.npp_fk = f.pk_seq    "+
	    	"	left join nhaphang nh on nh.SOID=a.SOID  "+
	    	"where ( a.trangthai <>'0') and a.loaidonhang=1 and a.kmTT=1   and f.pk_seq in "+ ut.quyen_npp(userId) ;
		
		if (sku.length()>0){
		query1 = query1 + "and a.pk_seq in ( select dondathang_fk  from dondathang_sp  ddh_sp inner join  sanpham sp on  sp.pk_seq=ddh_sp.sanpham_fk " +
				" where sp.ma   like '%"+sku+"%' and ddh_sp.soluong > 0 )";
	 	}
    	
    	if (tungay.length()>0){
			query1 = query1 + " and a.ngaydat >= '" + tungay+ "'";

    	}

    	if (denngay.length()>0){
			query1 = query1 + " and a.ngaydat <= '" + denngay+ "'";
		
    		
    	}

    	if(trangthai.length() > 0){
    		query1 = query1 + " and a.trangthai = '" + trangthai + "'";
    		
    	}

    	if(kvId.length() > 0){
    		query1 = query1 + " and f.khuvuc_fk = '" + kvId + "'";
    	
    	}
    	
      	if(so.length() > 0){
    		query1 = query1 + " and a.pk_seq like '%" + so + "%'";
      	}
      	
    	if(nppId.length()>0){
      		query1 = query1 + " and f.pk_seq ='"+ nppId+"' "; 
      		 
      	}
    	
    	
    	System.out.println(query1);
    	
    	return query1;
	}	
		
	private void Delete(String ddhId)
	{
		//xoa thi =6
		dbutils db = new dbutils();
		try
		{
			db.getConnection().setAutoCommit(false);
			String query = "update  dondathang set trangthai='6' where pk_seq='" + ddhId + "'";
			
			if(!db.update(query))
			{
				db.update("rollback");
				System.out.println("[Cap nhat lai chiet khau ]"+query);
			}
			query=
			"	update ThanhToanCKTM set sudung=ISNULL(SuDung,0)-ISNULL(ChietKhauThuongMai,0) "+
			"	from  \n"+
			"	( \n"+
			"		select \n"+ 
			"		sum  \n"+
			"		(   "+
			"			(  \n"+
			"				a.soluongduyet*a.giachuan*(1-(isnull(a.chietkhau,0)/100)) \n"+ 
			"			) *(1-isnull(b.chietkhau,0)/100 )  \n"+
			"		) as doanhso,b.npp_fk,(select convert(varchar(10),dateadd(month,-1,substring(ngaydat,0,8)+'-01'),20)) as thoigian,ChietKhauThuongMai \n"+
			"		from dondathang_sp a inner join dondathang b on a.dondathang_fk=b.pk_seq   \n"+
			"		where a.dondathang_fk='"+ddhId+"'  \n"+
			"		group by b.npp_fk,ngaydat,ChietKhauThuongMai \n"+
			"	) as dh inner join ThanhToanCKTM as ck on \n"+
			"	dh.npp_fk=ck.npp_fk and ck.nam=substring(dh.thoigian,0,5) and ck.thang=substring(dh.thoigian,6,2) ";
			
			if(!db.update(query))
			{
				db.update("rollback");
				System.out.println("[Cap nhat lai chiet khau ]"+query);
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
			
		}catch(Exception er)
		{
			db.update("rollback");
			db.shutDown();
			System.out.println(er.toString());
		}
	}
	private boolean CreatePivotTable(OutputStream out, IDonmuahangList obj, String query) throws Exception
	{
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();

		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\DonDatHangTT.xlsm");
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		CreateHeader(workbook, obj);

		isFillData = FillData(workbook, query, obj);
		if (!isFillData)
		{
			if (fstream != null)
				fstream.close();
			return false;
		}
		workbook.save(out);
		fstream.close();
		return true;
	}
	
	
	private void CreateHeader(Workbook workbook, IDonmuahangList obj)
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		Cells cells = worksheet.getCells();

		
		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
		ReportAPI.getCellStyle(cell, Color.RED, true, 16, "Báo Cáo Đơn Hàng KM Nhà Phân Phối ");
		
		cell = cells.getCell("A3");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 10, "Ngày tạo : " + this.getDateTime());
		
		cell = cells.getCell("FA1");cell.setValue("NgayDatHang");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FB1");cell.setValue("TrangThai");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FC1");cell.setValue("SoPO");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FD1");cell.setValue("SoSO");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FE1");cell.setValue("TienDonHang");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FF1");cell.setValue("SoHoaDon");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FG1");cell.setValue("NgayHoaDon");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FH1");cell.setValue("NgayNhanHang");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FI1");cell.setValue("TienHoaDon");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FJ1");cell.setValue("NhaPhanPhoi");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FK1");cell.setValue("NguoiTao");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FL1");cell.setValue("NguoiDuyet");ReportAPI.setCellHeader(cell);
		cell = cells.getCell("FM1");cell.setValue("NgayDuyet");ReportAPI.setCellHeader(cell);
	
		
	}
	
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
	private boolean FillData(Workbook workbook, String query, IDonmuahangList obj) throws Exception
	{
		ResultSet rs = null;
		dbutils db = new dbutils();

		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();

		for (int i = 0; i < 4; ++i)
		{
			cells.setColumnWidth(i, 15.0f);
		}
		rs = db.get(query);
		int index = 2;
		if (rs != null)
		{
			try
			{
				Cell cell = null;
				while (rs.next())
				{
					String trangthai=rs.getString("TrangThai");
					if(trangthai.equals("1"))
						trangthai="Đang chờ duyệt";
					else if(trangthai.equals("2"))
							trangthai="TT Duyệt";
					else if(trangthai.equals("3"))
						trangthai="ERP Duyệt";
					else if(trangthai.equals("4"))
						trangthai="Đã xuất HĐ";
					else if(trangthai.equals("6"))
						trangthai="Đã hủy";
					
					cell = cells.getCell("FA" + String.valueOf(index));cell.setValue(rs.getString("NgayDat"));
					cell = cells.getCell("FB" + String.valueOf(index));cell.setValue(trangthai);
					cell = cells.getCell("FC" + String.valueOf(index));cell.setValue(rs.getString("soId"));
					cell = cells.getCell("FD" + String.valueOf(index));cell.setValue(rs.getString("ChungTu"));
					cell = cells.getCell("FE" + String.valueOf(index));cell.setValue(rs.getDouble("SoTienAVat"));
					cell = cells.getCell("FF" + String.valueOf(index));cell.setValue(rs.getString("SoHoaDon"));
					cell = cells.getCell("FG" + String.valueOf(index));cell.setValue(rs.getString("NgayHd"));
					cell = cells.getCell("FH" + String.valueOf(index));cell.setValue(rs.getString("NgayNhan"));
					cell = cells.getCell("FI" + String.valueOf(index));cell.setValue(rs.getDouble("TienHd"));
					cell = cells.getCell("FJ" + String.valueOf(index));cell.setValue(rs.getString("nppTen"));
					cell = cells.getCell("FK" + String.valueOf(index));cell.setValue(rs.getString("NguoiTao"));
					cell = cells.getCell("FL" + String.valueOf(index));cell.setValue(rs.getString("NguoiDuyet"));
					cell = cells.getCell("FM" + String.valueOf(index));cell.setValue(rs.getString("DateDuyet"));
					index++;
				}

				if (rs != null)
					rs.close();

				if (index == 2)
				{
					throw new Exception("Không có báo cáo với điều kiện đã chọn !");
				}
				if (db != null)
					db.shutDown();
			} catch (Exception ex)
			{
				throw new Exception(ex.getMessage());
			}
		} else
		{
			return false;
		}
		return true;
	}
	
	
}