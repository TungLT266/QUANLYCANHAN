package geso.traphaco.center.servlets.chuongtrinhkhuyenmai;

import geso.traphaco.center.beans.ctkhuyenmai.*;
import geso.traphaco.center.beans.ctkhuyenmai.imp.*;

import geso.traphaco.center.beans.dieukienkhuyenmai.ISanpham;

import geso.traphaco.center.beans.dieukienkhuyenmai.imp.Sanpham;

import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.db.sql.dbutils;


import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



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


public class CtkhuyenmaiSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	PrintWriter out;

	public CtkhuyenmaiSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		this.out = response.getWriter();

		HttpSession session = request.getSession();
		ICtkhuyenmaiList obj = new CtkhuyenmaiList();

		Utility util = new Utility();
		out = response.getWriter();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		out.println(userId);

		if (userId.length() == 0)
			userId = request.getParameter("userId");

		String action = util.getAction(querystring);
		out.println(action);

		String ctkmId = util.getId(querystring);

		if (action.equals("delete"))
		{
			String msg = Delete(ctkmId);
			if (msg.length() > 0)
				obj.setMessage(msg);
		}else if(action.equals("phanbo"))
		{
			String msg = PhanBoKm(ctkmId);
			if (msg.length() > 0)
				obj.setMessage(msg);
		}
		obj.setRequestObj(request);
		obj.setUserId(userId);
		System.out.println("\nINIT CTKM");
		obj.init("");
		session.setAttribute("obj", obj);
		session.setAttribute("dkkmDien_giai", "");
		session.setAttribute("dkkmTungay", "");
		session.setAttribute("dkkmDenngay", "");

		String nextJSP = "/TraphacoHYERP/pages/Center/ChuongTrinhKhuyenMai.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		System.out.println("vao dopost ");

		ICtkhuyenmaiList obj = new CtkhuyenmaiList();
		HttpSession session = request.getSession();
		String userId = request.getParameter("userId");

		String diengiai = request.getParameter("diengiai");
		String tungay = request.getParameter("tungay");
		String denngay = request.getParameter("denngay");
		String trangthai = request.getParameter("trangthai");

		obj.setDiengiai(diengiai);
		obj.setTungay(tungay);
		obj.setDenngay(denngay);
		obj.setTrangthai(trangthai);

		String action = request.getParameter("action");
		if (action == null)
		{
			action = "";
		}
		obj.setRequestObj(request);
		System.out.println("oalalalalala111111111111111111111");
		if (action.equals("Tao moi"))
		{
			System.out.println("oalalalalala");
			ICtkhuyenmai ctkmBean = (ICtkhuyenmai) new Ctkhuyenmai("");
			
			System.out.println("in chung ::::::::;;"+ctkmBean.getInchung());
			ctkmBean.setUserId(userId);
			ctkmBean.createRS();
			ctkmBean.CreateVung();
			session.setAttribute("ctkmBean", ctkmBean);
			session.setAttribute("dkkmDien_giai", "");
			session.setAttribute("dkkmTungay", "");
			session.setAttribute("dkkmDenngay", "");
			String nextJSP = "/TraphacoHYERP/pages/Center/ChuongTrinhKhuyenMaiNew.jsp";
			response.sendRedirect(nextJSP);
		} else if (action.equals("view") || action.equals("next") || action.equals("prev"))
		{
			String search = getSearchQuery(request, obj);
			obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
			obj.init(search);
			obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
			session.setAttribute("obj", obj);
			response.sendRedirect("/TraphacoHYERP/pages/Center/ChuongTrinhKhuyenMai.jsp");
		} else if(action.equals("toExcel"))
		{
			OutputStream outstream = null;
			try
			{
				WorkbookSettings wbSettings = new WorkbookSettings();
				wbSettings.setLocale(new Locale("en", "EN"));			
				response.setContentType("application/vnd.ms-excel");
			    response.setHeader("Content-Disposition", "attachment; filename=ChuongTrinhKhuyenMai.xls");
			    outstream = response.getOutputStream();
			    WritableWorkbook workbook = jxl.Workbook.createWorkbook(response.getOutputStream());
			    
				String search = getSearchQuery(request, obj);
				dbutils db=new dbutils();
				ResultSet rs=db.get(search);
				WritableSheet sheet =null;
				int sheetIndex=0;
				ICtkhuyenmai e=new Ctkhuyenmai("");
				 e.setUserId(userId);
				System.out.println("[Excel]"+search);
				try
				{
					while(rs.next())
					{
						int rowIndex=10;
				    	e.setId(rs.getString("ctkmId")  );
				    	e.setKhuvucId("");
				    	e.initExcel();
				    	sheet = workbook.createSheet(rs.getString("scheme"), sheetIndex);
				    	workbook.setColourRGB(Colour.RED, 0xff, sheetIndex, sheetIndex);
				    	CreateHeaderCTKM( sheet,e);
				    	List<IDieukienkm> listDkkm =e.getDkkmList();
				    	List<ITrakm> listTrakm = e.getTrakmList();
				    	for(int i=0;i<listDkkm.size();i++)
				    	{
				    		IDieukienkm o=listDkkm.get(i);
				    		IDieukienDetail dkDetail=o.getDieukienDetail();
				    		List<ISanpham> sp_dkkmList=dkDetail.getSpList();
				    		this.CreateHeader(sheet, o,i);
				    		rowIndex=this.CreateContent(sheet, sp_dkkmList,i);
				    		rowIndex++;
				    	}
				    	int nextRow=rowIndex+2;
				    	for(int i=0;i<listTrakm.size();i++)
				    	{
					    	ITrakm   ITrakm=listTrakm.get(i);
				    		ITrakmDetail tkmDetail=ITrakm.getTraDetail();
				    		List<ISanpham> sp_dkkmList=tkmDetail.getSpList();
				    		CreateHeaderTraKM(sheet,ITrakm,nextRow,i);
				    		rowIndex=this.CreateContentTraKM(sheet, sp_dkkmList,nextRow+8,i);
				    		rowIndex++;
				    	}
				    	ResultSet Dsnpp =e.getDsnppSelected();
				    	CreateHeaderNPP(sheet,rowIndex+2);
				    	CreateContentNPP(sheet,Dsnpp,rowIndex+4);
				    	sheetIndex++;
					}
					if(rs!=null)
					{
						rs.close();
						e.DbClose();
					}
				} catch (SQLException ex)
				{
					ex.printStackTrace();
				}
				if(sheetIndex==0)
				{
					obj.setMessage("Không có báo cáo trong thời gian đã chọn!");
					
				}else 
				{
					workbook.write();		
					workbook.close();
				}
				return;
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				System.out.print("Exception...");
			}
			finally
		    {
		     if (outstream != null)
		    	 outstream.close();
		    }
		}else 
		{
			obj.setRequestObj(request);
			String search = getSearchQuery(request, obj);
			obj.setUserId(userId);
			System.out.println("SVL này");
			
			obj.init(search);
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			response.sendRedirect("/TraphacoHYERP/pages/Center/ChuongTrinhKhuyenMai.jsp");
		}
	}

	private String getSearchQuery(HttpServletRequest request, ICtkhuyenmaiList obj)
	{
		String diengiai = request.getParameter("diengiai");
    	if ( diengiai == null)
    		diengiai = "";
    	obj.setDiengiai(diengiai);
    	
    	String tungay = request.getParameter("tungay");
    	if (tungay == null)
    		tungay = "";    	
    	obj.setTungay(tungay);
    	
    	String denngay = request.getParameter("denngay");
    	if (denngay == null)
    		denngay = "";    	
    	obj.setDenngay(denngay);
    	
    	String trangthai = request.getParameter("trangthai");
    	if (trangthai == null)
    		trangthai = "1";    	
    	obj.setTrangthai(trangthai);
    	
    	
    	String nppId = request.getParameter("nppId");
    	if (nppId == null)
    		nppId = "";    	
    	obj.setNppId(nppId);
    	
    	String vungId = request.getParameter("vungId");
    	if (vungId == null)
    		vungId = "";    	
    	obj.setVungId(vungId);
    	
    	String khuvucId = request.getParameter("khuvucId");
    	if (khuvucId == null)
    		khuvucId = "";    	
    	obj.setKhuvucId(khuvucId);
    	
    	String phanbo = request.getParameter("phanbo");
    	if (phanbo == null)
    		phanbo = "";    	
    	obj.setPhanbo(phanbo);

    	String 
    	query =
    	"	select distinct  a.pk_seq as ctkmid, a.scheme, isnull(a.diengiai, '') as diengiai, a.tungay, a.denngay "+
    	"		, isnull(a.loaict, '1') as type, isnull(a.ngansach, '') as ngansach, a.dasudung "+
    	"		, isnull(a.ngaytao, '') as ngaytao, isnull(a.ngaysua, '') as ngaysua, b.ten as nguoitao, c.ten as nguoisua,isnull(a.LOAINGANSACH,0) as LoaiNganSach,(select COUNT(CTKM_FK) from PhanBoKhuyenMai where CTKM_FK=a.PK_SEQ)as SoNpp "+ 
    	"	from ctkhuyenmai a  "+
    	"		inner join nhanvien b on a.nguoitao = b.pk_seq  "+
    	"		inner join nhanvien c on a.nguoisua = c.pk_seq "; 
    	if(vungId.length()>0 ||khuvucId.length()>0 ||nppId.length()>0||phanbo.length()>0)
    	{
	    	query+=
	    	"		inner join "+
	    	"		( "+
	    	"			select CTKM_FK "+
	    	"			from CTKM_NPP km  "+
	    	"				inner join NHAPHANPHOI npp on npp.PK_SEQ=km.NPP_FK	 "+
	    	"			where  1=1 and isnull(chon,0)=1  " ;
	    	if(nppId.length()>0)
	    	query+=
	    	" and  km.NPP_FK in ( "+nppId+" ) ";
	    	if(khuvucId.length()>0)
	    	query+=" and npp.KHUVUC_FK ="+khuvucId+"  ";
	    	if(vungId.length()>0)
	    	query+=" and npp.KHUVUC_FK in ( select pk_seq from KHUVUC where VUNG_FK="+vungId+" ) ";
	    	if(phanbo.equals("0"))
	    	{
		    	query+=
		    	"	and km.CTKM_FK not in ( select ctkm_fk from PHANBOKHUYENMAI where 1=1 " ;
		    	if(nppId.length()>0)
		    		query+=" and npp_fk ="+nppId+"  ";
		    	query+=" ) ";
	    	}else if(phanbo.equals("1"))
	    	{
	    		query+=
		    	"	and km.CTKM_FK  in ( select ctkm_fk from PHANBOKHUYENMAI where 1=1 " ;
		    	if(nppId.length()>0)
		    		query+=" and npp_fk ="+nppId+"  ";
		    	query+=" ) ";
	    	}
	    	query+=
	    	" ) pb on pb.CTKM_FK=a.PK_SEQ ";
    	}
    	query+="	where a.pk_seq > 0 ";
		
    	if (diengiai.length()>0){
			query = query + " and upper(a.diengiai) like upper('%" + diengiai + "%') or upper(a.SCHEME) like upper('%" + diengiai + "%')";			
    	}
    	if (tungay.length()>0){
			query = query + " and a.tungay >= '" + convertDate(tungay) + " '";			
    	}
    	
    	if (denngay.length()>0){
			query = query + " and a.denngay <= '" + convertDate(denngay) + " '";		
    	}

		if(trangthai.equals("1"))
		{
			query = query + " and tungay <= (SELECT CONVERT(VARCHAR(10),DATEADD(day,0,GETDATE()),120))" +
							" and denngay >=(SELECT CONVERT(VARCHAR(10),DATEADD(day,0,GETDATE()),120))";
		}
		
		if(trangthai.equals("2"))
		{
			query = query + " and ( tungay > (SELECT CONVERT(VARCHAR(10),DATEADD(day,0,GETDATE()),120))" +
							" or denngay <( SELECT CONVERT(VARCHAR(10),DATEADD(day,0,GETDATE()),120)))";	
		}
		System.out.println("[CTKM]"+query);
    	return query;
	}

	private String convertDate(String date)
	{
		if (!date.contains("-"))
			return "";
		String[] arr = date.split("-");
		if (arr[0].length() < arr[2].length())
			return arr[2] + "-" + arr[1] + "-" + arr[0];
		return date;
	}

	private String Delete(String ctkmId)
	{
		dbutils db = new dbutils();
		ResultSet rs = db.get("select count(*) as num from donhang_ctkm_trakm where ctkmId ='" + ctkmId + "'");
		try
		{
			rs.next();
			if (!rs.getString("num").equals("0"))
			{
				rs.close();
				return "Chuong trinh khuyen mai nay da duoc su dung";
			}
			rs.close();

			// Xoa Cac Bang Con Truoc
			db.update("delete from ctkm_dkkm where ctkhuyenmai_fk='" + ctkmId + "'");
			db.update("delete from ctkm_trakm where ctkhuyenmai_fk='" + ctkmId + "'");
			db.update("delete from ctkm_npp where ctkm_fk='" + ctkmId + "'");
			db.update("delete from CTKHUYENMAI_HANGCUAHANG where CTKM_FK='" + ctkmId + "'");
			
			// Xoa Bang Cha
			db.update("delete from ctkhuyenmai where pk_seq = '" + ctkmId + "'");
			db.update("commit");

			return "";

		} catch (SQLException e)
		 {
			return "Khong the xoa chuong trinh khuyen mai nay";
		}

	}
	
	private String PhanBoKm(String ctkmId)
	{
		dbutils db = new dbutils();
		ResultSet rs = db.get("select count(*) as num from donhang_ctkm_trakm where ctkmId ='" + ctkmId + "'");
		try
		{
			rs.next();
			if (!rs.getString("num").equals("0"))
			{
				rs.close();
				return "Chuong trinh khuyen mai nay da duoc su dung";
			}
			rs.close();

			db.getConnection().setAutoCommit(false);
			
			String query="delete from phanbokhuyenmai where ctkm_fk='"+ctkmId+"'";
			if(!db.update(query))
			{
				db.update("rollback");
				return "Lỗi cập nhật "+query;
			}
			query=
			"insert into PHANBOKHUYENMAI(CTKM_FK,NPP_FK,NGANSACH,DASUDUNG,TINHTRANG,SoXuatToiDa) "+
			" select '"+ctkmId+"' as ctkmId,a.npp_fk,99999999999,0,0,c.SoXuatToiDa "+
			" from ctkm_npp a inner join nhaphanphoi b on a.npp_fk = b.pk_seq " +
			"   inner join ctkhuyenmai c on c.pk_seq=a.ctkm_fk  "+
			" where a.ctkm_fk='"+ctkmId+"' and CHON=1  and b.sitecode = b.convsitecode ";
			
			System.out.println("__PhanBo__"+query);
			
			if(!db.update(query))
			{
				db.update("rollback");
				return "Lỗi cập nhật "+query;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			if(db!=null)
				db.shutDown();
			return "";	
		} catch (Exception e)
		{
			
			try
			{
				db.getConnection().rollback();
			} catch (SQLException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
			db.shutDown();
			return "Không thể phân bổ CTKM này !"+e.getMessage();
		}
	}
	

	private void CreateHeader(WritableSheet ws, IDieukienkm dkkm,int index) throws WriteException
	{

		IDieukienDetail dkkmBean = dkkm.getDieukienDetail();
		//Co dinh 8 row dau tien
		ws.getSettings().setVerticalFreeze(8);
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
	   
	    Label title = new Label(0,8+index*10, "Điều kiện khuyến mãi", wcf);
	    ws.addCell(title);
	    
	    WritableFont font = new WritableFont(WritableFont.ARIAL, WritableFont.DEFAULT_POINT_SIZE, WritableFont.NO_BOLD, false,UnderlineStyle.NO_UNDERLINE);
	    wcf = new WritableCellFormat(font);
	    wcf.setWrap(true);
	    
	    WritableFont wf2 = new WritableFont(WritableFont.ARIAL, WritableFont.DEFAULT_POINT_SIZE , WritableFont.BOLD);
	    WritableCellFormat wcf2 = new WritableCellFormat(wf2);

	    Label l_madieukien = new Label(0, 9+index*10, "Mã điều kiện: ", wcf);
	    ws.addCell(l_madieukien);	
	    
	    Label d_madieukien = new Label(1, 9+index*10, dkkm.getId(), wcf2);
	    ws.addCell(d_madieukien);	
	    

	    Label l_diengiai = new Label(0, 10+index*10, "Diễn giải: ", wcf);
	    ws.addCell(l_diengiai);	
	    
	    Label d_diengiai = new Label(1,10+index*10, dkkmBean.getDiengiai(), wcf2);
	    ws.addCell(d_diengiai);	
	    
	    Label l_loaidieukien = new Label(0, 11+index*10, "Loại điều kiện: ", wcf);
	    ws.addCell(l_loaidieukien);
	    
	    
	    Label d_loaidieukien;
	    if(dkkmBean.getLoaidieukien().equals("1"))
	    	d_loaidieukien = new Label(1, 11+index*10, "Bắt buộc nhập số lượng từng sản phẩm" , wcf2);
	    else
	    	d_loaidieukien = new Label(1, 11+index*10, "Bất kỳ trong" , wcf2);
	    ws.addCell(d_loaidieukien);	

	    
	    Label l_hinhthuc = new Label(0, 12+index*10, "Hình thức: ", wcf);
	    ws.addCell(l_hinhthuc);		    
	    
	    Label d_hinhthuc;
	    Label d_tong;
	    if(dkkmBean.getHinhthuc().equals("1")){
	    	d_hinhthuc = new Label(1, 12+index*10, "Nhập tổng lượng" , wcf2);
	    	d_tong = new Label(2, 12+index*10, dkkmBean.getSotong() , wcf2);
		}else{
	    	d_hinhthuc = new Label(1, 12+index*10,"Nhập tổng tiền" , wcf2);
	    	d_tong = new Label(2, 12+index*10, dkkmBean.getSotong() , wcf2);
	    }
	    ws.addCell(d_hinhthuc);	
	    ws.addCell(d_tong);
	    
	    ws.addCell( new Label(0, 13+index*10, "Số lượng tính theo: ", wcf));
	    Label d_thung;
	    if(dkkm.isTheothung())
	    	d_thung = new Label(1, 13+index*10, "Thùng" , wcf2);
	    else
	    	d_thung = new Label(1, 13+index*10, "Lẻ" , wcf2);
	    ws.addCell(d_thung);	
	    
	    
	    font = new WritableFont(WritableFont.ARIAL, WritableFont.DEFAULT_POINT_SIZE, WritableFont.BOLD, false);
	    wcf = new WritableCellFormat(font);
	    wcf.setWrap(true);
	    wcf.setAlignment(Alignment.CENTRE);
	    wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
	    wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
	    	    
	    Label masp = new Label(0, 15+index*10, "Mã sản phẩm", wcf);	    
	    ws.addCell(masp);	

	    Label tensp = new Label(1, 15+index*10, "Tên sản phẩm", wcf);	    
	    ws.addCell(tensp);	
	    
	    Label soluong = new Label(2, 15+index*10, "Số lượng", wcf);	   
	    ws.addCell(soluong);	 
	    
	}
	private int CreateContent(WritableSheet ws, List<ISanpham> sp_dkkmList,int index) throws WriteException
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
			
		int m = 16;
		for(int i = 0; i < sp_dkkmList.size(); i++)
		{
			Sanpham sp = (Sanpham)sp_dkkmList.get(i);
			
			Label masp = new Label(0, m+10*index,sp.getMasanpham(), wcf_left);
			ws.addCell(masp);

			Label tensp = new Label(1, m+10*index, sp.getTensanpham(), wcf_left);
			ws.addCell(tensp);
			
			Label soluong = new Label(2, m+10*index, sp.getSoluong(), wcf_left);
			ws.addCell(soluong);
			m++;
		}
		return m+10*index;
	}
	
	private int  CreateHeaderTraKM(WritableSheet ws, ITrakm trakm,int rowIndex,int index) throws WriteException
	{
		ITrakmDetail dkkmBean = trakm.getTraDetail();
		//Co dinh 8 row dau tien
		ws.getSettings().setVerticalFreeze(8);
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
	   
	    Label title = new Label(0,rowIndex+index*10, "Trả khuyến mãi", wcf);
	    ws.addCell(title);
	    
	    WritableFont font = new WritableFont(WritableFont.ARIAL, WritableFont.DEFAULT_POINT_SIZE, WritableFont.NO_BOLD, false,UnderlineStyle.NO_UNDERLINE);
	    wcf = new WritableCellFormat(font);
	    wcf.setWrap(true);
	    
	    WritableFont wf2 = new WritableFont(WritableFont.ARIAL, WritableFont.DEFAULT_POINT_SIZE , WritableFont.BOLD);
	    WritableCellFormat wcf2 = new WritableCellFormat(wf2);

	    Label l_madieukien = new Label(0, rowIndex+1+index*10, "Mã điều kiện: ", wcf);
	    ws.addCell(l_madieukien);	
	    
	    Label d_madieukien = new Label(1, rowIndex+1+index*10, trakm.getId(), wcf2);
	    ws.addCell(d_madieukien);	
	    

	    Label l_diengiai = new Label(0, rowIndex+2+index*10, "Diễn giải: ", wcf);
	    ws.addCell(l_diengiai);	
	    
	    Label d_diengiai = new Label(1,rowIndex+2+index*10, dkkmBean.getDiengiai(), wcf2);
	    ws.addCell(d_diengiai);	
	    
	    Label l_loaidieukien = new Label(0, rowIndex+3+index*10, "Loại điều kiện: ", wcf);
	    ws.addCell(l_loaidieukien);
	    
	    
	    Label d_loaidieukien =null;
	    if(dkkmBean.getLoaitra().equals("1"))
	    	d_loaidieukien = new Label(1, rowIndex+3+index*10, "Trả tiền" , wcf2);
	    else if(dkkmBean.getLoaitra().equals("2"))
	    	d_loaidieukien = new Label(1, rowIndex+3+index*10, "Trả chiết khấu" , wcf2);
	    else if(dkkmBean.getLoaitra().equals("3"))
	    	d_loaidieukien = new Label(1, rowIndex+3+index*10, "Trả sản phẩm" , wcf2);
	    ws.addCell(d_loaidieukien);	

	    
	    Label l_hinhthuc = new Label(0, rowIndex+4+index*10, "Hình thức: ", wcf);
	    ws.addCell(l_hinhthuc);		    
	    
	    Label d_hinhthuc;
	    Label d_tong;
	    if(dkkmBean.getHinhthuc().equals("1"))
	    {
	    	d_hinhthuc = new Label(1, rowIndex+4+index*10, "Bắt buộc nhập số lượng" , wcf2);
	    	d_tong = new Label(2, rowIndex+4+index*10, dkkmBean.getSotong() , wcf2);
		}else
		{
	    	d_hinhthuc = new Label(1, rowIndex+4+index*10,"Bất kỳ trong" , wcf2);
	    	d_tong = new Label(2, rowIndex+4+index*10, dkkmBean.getSotong() , wcf2);
	    }
	    ws.addCell(d_hinhthuc);	
	    ws.addCell(d_tong);
	    
	    
	    
	    ws.addCell( new Label(0, rowIndex+5+index*10, "Tổng lượng / Tổng tiền" , wcf2));
	    ws.addCell(new Label(1, rowIndex+5+index*10, dkkmBean.getSotong()));
	    
	    ws.addCell( new Label(0, rowIndex+6+index*10, "Số lượng tính theo" , wcf2));
	    ws.addCell(new Label(1, rowIndex+6+index*10, "Lẻ" ));
	    
	    
	    font = new WritableFont(WritableFont.ARIAL, WritableFont.DEFAULT_POINT_SIZE, WritableFont.BOLD, false);
	    wcf = new WritableCellFormat(font);
	    wcf.setWrap(true);
	    wcf.setAlignment(Alignment.CENTRE);
	    wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
	    wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
	    	    
	    Label masp = new Label(0, rowIndex+7+index*10, "Mã sản phẩm", wcf);	    
	    ws.addCell(masp);	

	    Label tensp = new Label(1, rowIndex+7+index*10, "Tên sản phẩm", wcf);	    
	    ws.addCell(tensp);	
	    
	    Label soluong = new Label(2, rowIndex+7+index*10, "Số lượng", wcf);	   
	    ws.addCell(soluong);	 
	    
	    return rowIndex+7;
	    
	}
	private int CreateContentTraKM(WritableSheet ws, List<ISanpham> sp_dkkmList,int rowIndex,int index) throws WriteException
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
		
	int m = rowIndex;
		
	for(int i = 0; i < sp_dkkmList.size(); i++)
	{
		Sanpham sp = (Sanpham)sp_dkkmList.get(i);
		
		Label masp = new Label(0, m+10*index,sp.getMasanpham(), wcf_left);
		ws.addCell(masp);

		Label tensp = new Label(1, m+10*index, sp.getTensanpham(), wcf_left);
		ws.addCell(tensp);
		
		Label soluong = new Label(2, m+10*index, sp.getSoluong(), wcf_left);
		ws.addCell(soluong);
		m++;
	}
	return m+10*index;
	
}
	
	private void CreateHeaderNPP(WritableSheet ws,int rowIndex) throws WriteException
	{	
		//Co dinh 8 row dau tien
		ws.getSettings().setVerticalFreeze(8);
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
	   
	    Label title = new Label(0,rowIndex, "Danh sách NPP", wcf);
	    ws.addCell(title);
	    
	    WritableFont font = new WritableFont(WritableFont.ARIAL, WritableFont.DEFAULT_POINT_SIZE, WritableFont.NO_BOLD, false,UnderlineStyle.NO_UNDERLINE);
	    wcf = new WritableCellFormat(font);
	    wcf.setWrap(true);
	    
	    WritableFont wf2 = new WritableFont(WritableFont.ARIAL, WritableFont.DEFAULT_POINT_SIZE , WritableFont.BOLD);
	    WritableCellFormat wcf2 = new WritableCellFormat(wf2);
	    	    	    
	    font = new WritableFont(WritableFont.ARIAL, WritableFont.DEFAULT_POINT_SIZE, WritableFont.BOLD, false);
	    wcf = new WritableCellFormat(font);
	    wcf.setWrap(true);
	    wcf.setAlignment(Alignment.CENTRE);
	    wcf.setBorder(Border.ALL, BorderLineStyle.THIN);
	    wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
	    	    
	    Label masp = new Label(0, rowIndex+1, "Mã nhà phân phối", wcf);	    
	    ws.addCell(masp);	

	    Label tensp = new Label(1,  rowIndex+1, "Nhà phân phối", wcf);	    
	    ws.addCell(tensp);	
	    
	}
	private void CreateContentNPP(WritableSheet ws, ResultSet npp,int index) throws WriteException
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
			
		int m = index;
		try
		{
			while(npp.next())
			{
				ws.addCell(new Label(0, m, npp.getString("ma")   , wcf_left));
				ws.addCell(new Label(1, m, npp.getString("ten")   , wcf_left));
				m++;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	private void CreateHeaderCTKM(WritableSheet ws, ICtkhuyenmai ctkmBean ) throws WriteException, SQLException
	{
		//Co dinh 8 row dau tien
		ws.getSettings().setVerticalFreeze(8);
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
	   
	    Label title = new Label(0, 0, "Chương trình khuyến mãi", wcf);
	    ws.addCell(title);
	    
	    WritableFont font = new WritableFont(WritableFont.ARIAL, WritableFont.DEFAULT_POINT_SIZE, WritableFont.NO_BOLD, false,UnderlineStyle.NO_UNDERLINE);
	    wcf = new WritableCellFormat(font);
	    wcf.setWrap(true);
	    
	    WritableFont wf2 = new WritableFont(WritableFont.ARIAL, WritableFont.DEFAULT_POINT_SIZE , WritableFont.BOLD);
	    WritableCellFormat wcf2 = new WritableCellFormat(wf2);
	    	    
	    Label l_ngaytao = new Label(0, 1, "Scheme: ", wcf);
	    ws.addCell(l_ngaytao);	
	    
	    Label d_ngaytao = new Label(1, 1,ctkmBean.getScheme() , wcf2);
	    ws.addCell(d_ngaytao);	
	    
	    Label l_nguoitao = new Label(0, 2, "Diễn giải: ", wcf);
	    ws.addCell(l_nguoitao);	
	    
	    Label d_nguoitao = new Label(1, 2, ctkmBean.getDiengiai(), wcf2);
	    ws.addCell(d_nguoitao);		    	    		    

	    Label l_madieukien = new Label(0, 3, "Loại chương trình: ", wcf);
	    ws.addCell(l_madieukien);	
	    
	    String loai="";
	    if(ctkmBean.getType().equals("1"))
	    {
	    	loai="Bình thường";
	    }else if(ctkmBean.getType().equals("2"))
	    {
	    	loai="On Top";
	    }else  loai="Tích lũy";
	    
	    Label d_madieukien = new Label(1, 3, loai   , wcf2);
	    ws.addCell(d_madieukien);	

	    Label l_diengiai = new Label(0, 4, "Từ ngày: ", wcf);
	    ws.addCell(l_diengiai);	
	    
	    Label d_diengiai = new Label(1, 4, ctkmBean.getTungay()  , wcf2);
	    ws.addCell(d_diengiai);	
	    
	    Label l_loaidieukien = new Label(0, 5, "Đến ngày: ", wcf);
	    ws.addCell(l_loaidieukien);
	    	    
	    Label d_loaidieukien;d_loaidieukien = new Label(1, 5, ctkmBean.getDenngay() , wcf2);
	    ws.addCell(d_loaidieukien);	    	 
	    
	    ws.addCell(new Label(0, 6, "Khu vực áp dụng: " , wcf));
	    ws.addCell(new Label(1, 6, ctkmBean.getKhuvucId() , wcf2));
	
	}
		

	
}




