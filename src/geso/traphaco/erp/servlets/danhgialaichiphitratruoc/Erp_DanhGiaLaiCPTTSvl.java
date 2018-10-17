package geso.traphaco.erp.servlets.danhgialaichiphitratruoc;

import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.UtilityKeToan;

import geso.traphaco.erp.beans.danhgialaichiphitratruoc.IErp_DanhGiaLaiCPTT;
import geso.traphaco.erp.beans.danhgialaichiphitratruoc.IErp_DanhGiaLaiCPTTList;
import geso.traphaco.erp.beans.danhgialaichiphitratruoc.imp.Erp_DanhGiaLaiCPTT;
import geso.traphaco.erp.beans.danhgialaichiphitratruoc.imp.Erp_DanhGiaLaiCPTTList;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Erp_DanhGiaLaiCPTTSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public Erp_DanhGiaLaiCPTTSvl() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{		
		HttpSession session = request.getSession();	   	    
	    Utility util = new Utility();	   
	    session.setAttribute("util", util);
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();	   
	    out = response.getWriter();
	    
	    String userId;
	 	userId= (String)session.getAttribute("userId");
		String querystring = request.getQueryString();
		userId = util.getUserId(querystring);
	    
	    IErp_DanhGiaLaiCPTTList obj = new Erp_DanhGiaLaiCPTTList();
	    obj.setCtyId((String) session.getAttribute("congtyId"));
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    out.println("userId la :"+ userId);
	   
	    String id = util.getId(querystring);
    	if (id == null)
    		id = "";
	    
    	String action = util.getAction(querystring);
    	if (action == null)
    		action = "";
    	
    	if (action.equals("chot"))
		{
    		String msg=chot(id);
    		if(msg.length()>0)
    		{
    			obj.setMsg(msg);
    		}
		}

		if (action.equals("delete"))
		{
			dbutils db= new dbutils();
    		String query= "Update ERP_DANHGIALAICHIPHITRATRUOC set trangthai =2 where pk_seq= "+id ;
    		db.update(query);
    		db.shutDown();
		}
		
    	obj.createRs();
		obj.init("");		
		
		session.setAttribute("obj", obj);
		response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_DanhGiaLaiCPTT.jsp");	
	}
	


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		Utility util = new Utility();
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();
	    
	   
//	    obj.setCtyId((String) session.getAttribute("congtyId"));
	    String userId;
	    userId = util.antiSQLInspection(request.getParameter("userId"));
//
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }    
	    out.println(action); 	 
	    
	    if(action.equals("new"))
	    {	IErp_DanhGiaLaiCPTT dcbean;	    
	    	dcbean = new Erp_DanhGiaLaiCPTT();	
	       	
	    	//obj.init("");
	    	dcbean.createRs();
	    	
	    	session.setAttribute("dcBean", dcbean);
	    	String nextJSP = "pages/Erp/Erp_DanhGiaLaiCPTT_New.jsp";	 
	    	response.sendRedirect(nextJSP);
	    }
	    else if(action.equals("view") || action.equals("next") || action.equals("prev"))
    	{
	    	IErp_DanhGiaLaiCPTTList obj;	    
	    	obj = new Erp_DanhGiaLaiCPTTList();	
    		System.out.println("toi day");
    		obj.setCtyId((String)session.getAttribute("congtyId"));
    		
	    	String search = getSearchQuery(request, obj);
	    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
	    	/*obj.setUserId(userId);*/
	    	obj.init(search);
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	session.setAttribute("obj", obj);
	    	response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_DanhGiaLaiCPTT.jsp");	
	    }
	    
	    else
	    {	IErp_DanhGiaLaiCPTTList obj;	    
    		obj = new Erp_DanhGiaLaiCPTTList();	    	   
	    	String search = getSearchQuery(request, obj);   	
	    	obj.init(search);	    	
	    	session.setAttribute("obj", obj);	    		
	    	String nextJSP = "pages/Erp/Erp_DanhGiaLaiCPTT.jsp";	    	
		    response.sendRedirect(nextJSP);
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErp_DanhGiaLaiCPTTList obj)
	{	
		Utility util = new Utility();	
    	String query="SELECT dc.pk_seq,dc.ngaychungtu,dc.Sochungtu,dc.trangthai,ts.diengiai as TAISAN FROM Erp_DanhGiaLaiTaiSan dc inner join erp_taisancodinh ts on dc.taisan_fk=ts.pk_seq" ;
    	
    	
    	String tungay = util.antiSQLInspection(request.getParameter("tungay"));
		if(tungay == null) tungay = "";	
		obj.setTungay(tungay);
		
	 	String denngay = util.antiSQLInspection(request.getParameter("denngay"));
		if(denngay == null) denngay = "";	
		obj.setDenngay(denngay);
	
		
		String sochungtu = util.antiSQLInspection(request.getParameter("sochungtu"));
		if(sochungtu == null) sochungtu = "";	
		obj.setSochungtu(sochungtu);
		
		
		String sodieuchuyen = util.antiSQLInspection(request.getParameter("sodieuchuyen"));
		if(sodieuchuyen == null) sodieuchuyen = "";	
		obj.setSodieuchuyen(sodieuchuyen);
		
		
		String phongban = util.antiSQLInspection(request.getParameter("phongban"));
		if(phongban == null) phongban = "";	
		obj.setPbId(phongban);
		
		
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if(trangthai == null) trangthai = "";	
		obj.setTrangthai(trangthai);
		
		
		if(trangthai.length()>0)
		{
			query+=" and dc.trangthai ="+trangthai ;
		}
		if(phongban.length() >0)
		{
			query+=" and dvth.pk_seq ="+phongban ;
		}
		if(sodieuchuyen.length()>0)
		{
			query+=" and dc.pk_seq ="+sodieuchuyen ;
		}
		if(sochungtu.length()>0)
		{
			query+=" and dc.sochungtu =N'"+sochungtu +"' ";
		}
		if(tungay.length()>0)
		{
			query+=" and dc.ngaychungtu >='" +tungay + "' ";
		}
		if(denngay.length()>0)
		{
			query+=" and dc.ngaychungtu <='" +denngay + "' ";
		}
		
    	System.out.println("query search la: " + query);
    	return query;
	}	
	
	public String chot(String id) {
		dbutils db = new dbutils();
		Utility util = new Utility();
		UtilityKeToan ulkt=new UtilityKeToan();
		try {
			db.getConnection().setAutoCommit(false);
			String query = null;

			//Update trạng thái xuất dùng
			query = "update ERP_DANHGIALAICHIPHITRATRUOC set TRANGTHAI = 1 WHERE PK_SEQ = " + id ;
			int num = db.updateReturnInt(query);
			if (num == 0)
			{
				db.getConnection().rollback();
				return "Không thể cập nhật trạng thái phiếu đánh giá";
			}
			query="SELECT ngaychungtu FROM ERP_DANHGIALAICHIPHITRATRUOC WHERE PK_SEQ = "+id ;
			String ngaychungtu="";
			ResultSet rs= db.get(query);
			if(rs.next())
				ngaychungtu=rs.getString("ngaychungtu");
			rs.close();
				query="SELECT DIEUCHINHSOTHANGKHAUHAO,DIEUCHINHNGUYENGIA FROM ERP_DANHGIALAICHIPHITRATRUOC WHERE PK_SEQ ="+id ;
				ResultSet dcRs= db.get(query);
				if(dcRs.next())
				{
					query = 
						"  insert into ERP_CONGCUDUNGCU_DIEUCHINH (GIATRI,SOTHANG, CCDC_FK, LOAICHUNGTU \n"+
						" , SOCHUNGTU, BANGTHAMCHIEU, NGAYDIEUCHINH,LOAICCDC_FK_NEW) (Select dieuchinhnguyengia, DIEUCHINHSOTHANGKHAUHAO,CCDC_FK," +
						"'1',PK_SEQ,'ERP_DANHGIALAICHIPHITRATRUOC',NGAYCHUNGTU,LOAICCDC_FK_NEW from ERP_DANHGIALAICHIPHITRATRUOC where PK_SEQ= "+id +") ";
					System.out.println("deu chinh tai san:\n" + query + "\n----------------------------------------");
					num = db.updateReturnInt(query);
					if (num == 0)
					{
						
						db.getConnection().rollback();
						db.shutDown();
						return "Không thể điều chỉnh chi phí trả trước";
					}
					
				}
				else 
				{	db.getConnection().rollback();
					db.shutDown();
					return "Vui lòng kiểm tra giá trị điều chỉnh, số tháng điều chỉnh";
				}
				
		
				String thang= ngaychungtu.substring(5,7);
				String nam=ngaychungtu.substring(0,4);
				String msg="";
				query=	" select dc.pk_seq as machungtu,N'Công cụ dụng cụ' as doituong,lccdc.taikhoan_fk,ccdc.pk_seq as madoituong,N'chi phí trả trước' as doituong,dc.dieuchinhnguyengia as dieuchinh,dc.LOAICCDC_FK_NEW,dc.LOAICCDC_FK_OLD," +
						"(select taikhoan_fk from erp_loaiccdc where pk_seq=dc.LOAICCDC_FK_NEW) as taikhoanNo_LoaiCCDC_New, \n" +
						"(select taikhoan_fk from erp_loaiccdc where pk_seq=dc.LOAICCDC_FK_OLD) as taikhoanCo_LoaiCCDC_Old, \n" +
						"(select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN like '71180000' and npp_fk=1) as taiKhoanCoDoanhThu, \n"+
						"(select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN like '81180000' and npp_fk=1) as taiKhoanNoDoanhThu, \n"+
						" ROUND(ISNULL(CCDC.THANHTIEN,0)+ISNULL(THAYDOICUOIKY.giatri,0),0) -  ROUND(ISNULL(CCDC.GIATRIDAKHAUHAO,0)+ISNULL(GIATRIKHAUHAO.giatrikhauhao,0),0) - ISNULL(THAYDOIDIEUCHINH.giatri,0) AS GIATRI \n" +  
						" FROM ERP_DANHGIALAICHIPHITRATRUOC dc \n" +
						" inner join ERP_CONGCUDUNGCU ccdc  on ccdc.pk_seq= dc.CCDC_FK \n" +
						" inner join ERP_LOAICCDC lccdc on lccdc.pk_seq=ccdc.LOAICCDC_FK  \n" +
//--------------------------------------------------------------------------------------------------------------------------------------
						//Lấy giá trị của tất cà các phiếu trong bảng điều chỉnh
						" LEFT JOIN  \n " 
						+ " ( \n "
						+ " 	SELECT CCDC_FK AS CCDC_FK, SUM(SOTHANG) AS SOTHANG, SUM(GIATRI) AS GIATRI \n "
						+ " 	FROM ERP_CONGCUDUNGCU_DIEUCHINH  \n " + " 	WHERE 1=1 \n " 
						+ "AND YEAR(NGAYDIEUCHINH)<'"+nam+"' or (YEAR(NGAYDIEUCHINH)<='"+nam+"' AND MONTH(NGAYDIEUCHINH)<='"+thang+"')"
				 		+ " 	GROUP BY CCDC_FK \n "
				 		+"	)THAYDOICUOIKY ON THAYDOICUOIKY.CCDC_FK= CCDC.PK_SEQ \n "
//---------------------------------------------------------------------------------------------------------------------------------------				 		
				 		//Lấy ra giá trị phiếu hiện tại
				 		+" LEFT JOIN  \n " 
				 		+	"( \n "
						+ " 	SELECT CCDC_FK AS CCDC_FK, SUM(SOTHANG) AS SOTHANG, SUM(GIATRI) AS GIATRI \n "
						+ " 	FROM ERP_CONGCUDUNGCU_DIEUCHINH  \n " + " 	WHERE 1=1 \n " 
						+ "		AND YEAR(NGAYDIEUCHINH)<'"+nam+"' or (YEAR(NGAYDIEUCHINH)='"+nam+"' AND MONTH(NGAYDIEUCHINH)<='"+thang+"')" +
								" AND SOCHUNGTU='"+id+"'"
					 	+ " 	GROUP BY CCDC_FK \n " 
					 	+ "  )THAYDOIDIEUCHINH on  THAYDOIDIEUCHINH.CCDC_FK = CCDC.PK_SEQ \n "
//----------------------------------------------------------------------------------------------------------------------------------------
					 	//Lấy ra giá trị đã khấu hao
						+ " LEFT JOIN \n"
						+ "( \n" 
						+ "select sum(kh.KHAUHAO ) as giatrikhauhao,kh.ccdc_fk\n"
						+ "from ERP_KHAUHAOCCDC kh \n" 
						+ "where ((kh.THANG<"+thang+" and kh.NAM<="+nam+") or (kh.NAM<"+nam+"))\n"
						+ "group by kh.CCDC_FK" 
						+ ") GIATRIKHAUHAO ON GIATRIKHAUHAO.CCDC_FK=CCDC.PK_SEQ\n"
						+ " WHERE DC.PK_SEQ =" +id ;
//------------------------------------------------------------------------------------------------------------------------------------------				
				rs=db.get(query);
				System.out.println("bbbbbbbbb"+query);
				String taikhoanno="";
				String taikhoanco="";
				if(rs!=null)
				{
				while(rs.next())
					{
					String loaiccdc_fk_new=rs.getString("LOAICCDC_FK_NEW");
					String loaiccdc_fk_old=rs.getString("LOAICCDC_FK_OLD");
					String taikhoanCCDC=rs.getString("taikhoanNo_LoaiCCDC_New");
					String taikhoanDoanhCoThuKhac=rs.getString("taiKhoanCoDoanhThu");
					String taikhoanDoanhNoThuKhac=rs.getString("taiKhoanNoDoanhThu");
					String doituong=rs.getString("doituong");
					double giatri=rs.getDouble("giatri");
					int dieuchinh =rs.getInt("dieuchinh");
					if(dieuchinh!=0)
					{
						if(dieuchinh>0)
						{
							taikhoanno=taikhoanCCDC;
							taikhoanco=taikhoanDoanhCoThuKhac;
							ulkt=new UtilityKeToan("Đánh giá lại chi phí trả trước", id, ngaychungtu, ngaychungtu, taikhoanno, taikhoanco, Integer.toString(dieuchinh), Integer.toString(dieuchinh), 
									"1", "100000",thang, nam,doituong, rs.getString("madoituong"), "Chi phí trả trước",  rs.getString("madoituong"), rs.getString("machungtu"), "");
							 msg=ulkt.Update_TaiKhoanByTaiKhoan_FK(db);
							if(msg.length()>0)
						{
								db.getConnection().rollback();
								return "Không thể ghi nhận kế toán";
						}
						}else
						{
							taikhoanno=taikhoanDoanhNoThuKhac;
							taikhoanco=taikhoanCCDC;
							dieuchinh=(-1)*dieuchinh;

							ulkt=new UtilityKeToan("Đánh giá lại chi phí trả trước", id, ngaychungtu, ngaychungtu, taikhoanno, taikhoanco, Integer.toString(dieuchinh), Integer.toString(dieuchinh), 
									"1", "100000",thang, nam,doituong, rs.getString("madoituong"), "Chi phí trả trước",  rs.getString("madoituong"), rs.getString("machungtu"), "");
							 msg=ulkt.Update_TaiKhoanByTaiKhoan_FK(db);
							if(msg.length()>0)
							{
								db.getConnection().rollback();
								return "Không thể ghi nhận kế toán";
							}
						}
					
			
					}
					
					if(!loaiccdc_fk_new.equals(loaiccdc_fk_old) && giatri>0)
						{
					
						ulkt=new UtilityKeToan("Điều chỉnh chi phí trả trước", id, ngaychungtu, ngaychungtu, rs.getString("taikhoanNo_LoaiCCDC_New"), 
								rs.getString("taikhoanCo_LoaiCCDC_Old"), Double.toString(giatri),  rs.getString("GIATRI"), "1", "100000", thang, nam, 
								"Chi phí trả trước", rs.getString("madoituong"),"Chi phí trả trước" , rs.getString("madoituong"), rs.getString("machungtu"), "");
						ulkt.setKhoanMucNoCo("Nguyên giá điều chỉnh");
						 msg=ulkt.Update_TaiKhoanByTaiKhoan_FK(db);
							if (msg.trim().length() > 0) 
							{
								rs.close();
								db.getConnection().rollback();
						
							}
						}

				
						}
				}
				
		} catch (Exception e) {
			e.printStackTrace();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return "Gặp lỗi khi chốt";
		}
		finally
		{
			try {
				db.getConnection().setAutoCommit(true);
				db.shutDown();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	

}



