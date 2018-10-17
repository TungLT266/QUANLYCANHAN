package geso.traphaco.erp.servlets.danhgialaitaisan;

import geso.traphaco.center.util.Utility;

import geso.traphaco.erp.beans.danhgialaitaisan.IErp_DanhGiaLaiTaiSan;
import geso.traphaco.erp.beans.danhgialaitaisan.IErp_DanhGiaLaiTaiSanList;
import geso.traphaco.erp.beans.danhgialaitaisan.imp.Erp_DanhGiaLaiTaiSan;
import geso.traphaco.erp.beans.danhgialaitaisan.imp.Erp_DanhGiaLaiTaiSanList;
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

public class Erp_DanhGiaLaiTaiSanSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public Erp_DanhGiaLaiTaiSanSvl() {
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
	    
	    IErp_DanhGiaLaiTaiSanList obj = new Erp_DanhGiaLaiTaiSanList();
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
    		String query= "Update Erp_DanhGiaLaiTaiSan set trangthai =2 where pk_seq= "+id ;
    		db.update(query);
    		db.shutDown();
		}
		
    	obj.createRs();
		obj.init("");		
		
		session.setAttribute("obj", obj);
		response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_DanhGiaLaiTaiSan.jsp");	
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
	    {	IErp_DanhGiaLaiTaiSan dcbean;	    
	    	dcbean = new Erp_DanhGiaLaiTaiSan();	
	       	
	    	//obj.init("");
	    	dcbean.createRs();
	    	
	    	session.setAttribute("dcBean", dcbean);
	    	String nextJSP = "pages/Erp/Erp_DanhGiaLaiTaiSan_New.jsp";	 
	    	response.sendRedirect(nextJSP);
	    }
	    else if(action.equals("view") || action.equals("next") || action.equals("prev"))
    	{
	    	IErp_DanhGiaLaiTaiSanList obj;	    
	    	obj = new Erp_DanhGiaLaiTaiSanList();	
    		System.out.println("toi day");
    		obj.setCtyId((String)session.getAttribute("congtyId"));
    		
	    	String search = getSearchQuery(request, obj);
	    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
	    	/*obj.setUserId(userId);*/
	    	obj.init(search);
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	session.setAttribute("obj", obj);
	    	response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_DanhGiaLaiTaiSan.jsp");	
	    }
	    
	    else
	    {	IErp_DanhGiaLaiTaiSanList obj;	    
    		obj = new Erp_DanhGiaLaiTaiSanList();	    	   
	    	String search = getSearchQuery(request, obj);   	
	    	obj.init(search);	    	
	    	session.setAttribute("obj", obj);	    		
	    	String nextJSP = "pages/Erp/Erp_DanhGiaLaiTaiSan.jsp";	    	
		    response.sendRedirect(nextJSP);
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErp_DanhGiaLaiTaiSanList obj)
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
		try {
			db.getConnection().setAutoCommit(false);
			String query = null;

			//Update trạng thái xuất dùng
			query = "update Erp_DanhGiaLaiTaiSan set TRANGTHAI = 1 WHERE PK_SEQ = " + id ;
			int num = db.updateReturnInt(query);
			if (num == 0)
			{
				db.getConnection().rollback();
				return "Không thể cập nhật trạng thái phiếu đánh giá";
			}
			query="SELECT ngaychungtu FROM Erp_DanhGiaLaiTaiSan WHERE PK_SEQ = "+id ;
			String ngaychungtu="";
			ResultSet rs= db.get(query);
			if(rs.next())
				ngaychungtu=rs.getString("ngaychungtu");
			rs.close();
				query="SELECT DIEUCHINHSOTHANGKHAUHAO,DIEUCHINHNGUYENGIA FROM Erp_DanhGiaLaiTaiSan WHERE PK_SEQ ="+id ;
				ResultSet dcRs= db.get(query);
				if(dcRs.next())
				{
					query = 
						"  insert into ERP_TAISANCODINH_DIEUCHINH (GIATRI,SOTHANG, TSCD_FK, LOAICHUNGTU \n"+
						" , SOCHUNGTU, BANGTHAMCHIEU, NGAYDIEUCHINH) (Select dieuchinhnguyengia, DIEUCHINHSOTHANGKHAUHAO,TAISAN_FK," +
						"N'Đánh giá lại tài sản',PK_SEQ,N'Erp_DanhGiaLaiTaiSan',NGAYCHUNGTU from Erp_DanhGiaLaiTaiSan where PK_SEQ= "+id +") ";
					System.out.println("deu chinh tai san:\n" + query + "\n----------------------------------------");
					num = db.updateReturnInt(query);
					if (num == 0)
					{
						
						db.getConnection().rollback();
						return "Không thể điều chỉnh tài sản cố định";
					}
					
				}
				else 
				{
					return "Vui lòng kiểm tra giá trị điều chỉnh, số tháng điều chỉnh";
				}
//				String thang= ngaychungtu.substring(5,7);
//				String nam=ngaychungtu.substring(0,4);
//				query=	" select lts.taikhoan_fk,tscd.pk_seq as madoituong,N'Tài sản' as doituong,dc.dieuchinhnguyengia as dieuchinh " +
//						" ,(select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN like '71110000') as taiKhoanDoanhThu FROM Erp_DanhGiaLaiTaiSan dc inner join ERP_TAISANCODINH tscd " +
//						" on tscd.pk_seq= dc.TAISAN_FK inner join Erp_LOAITAISAN lts on lts.pk_seq=tscd.LOAITAISAN_FK  " +
//						" WHERE DC.PK_SEQ =" +id ;
//				rs=db.get(query);
//				if(rs.next())
//				{
//					String taikhoanTSCD= rs.getString("taikhoan_fk");
//					String taikhoanDoanhThuKhac=rs.getString("taiKhoanDoanhThu");
//					String doituong=rs.getString("doituong");
//					String maDoiTuongTS= rs.getString("doituong");
//					String taikhoanno="";
//					String taikhoanco="";
//					int dieuchinh =rs.getInt("dieuchinh");
//					if(dieuchinh!=0)
//					{
//						if(dieuchinh>0)
//						{
//							taikhoanno=taikhoanTSCD;
//							taikhoanco=taikhoanDoanhThuKhac;
//
//							String msg=util.Update_TaiKhoan(db, thang, nam, ngaychungtu, ngaychungtu, "Đánh giá lại tài sản", id, taikhoanno, taikhoanco, "NULL", Integer.toString(dieuchinh), Integer.toString(dieuchinh), doituong,
//										maDoiTuongTS, "", "", "0", "0", "0", "100000", "0", "1", Integer.toString(dieuchinh), Integer.toString(dieuchinh), "");
//							if(msg.length()>0)
//							{
//								db.getConnection().rollback();
//								return "Không thể ghi nhận kế toán";
//							}
//						}else
//						{
//							taikhoanno=taikhoanDoanhThuKhac;
//							taikhoanco=taikhoanTSCD;
//							dieuchinh=(-1)*dieuchinh;
//							String msg=util.Update_TaiKhoan(db, thang, nam, ngaychungtu, ngaychungtu, "Đánh giá lại tài sản", id, taikhoanno, taikhoanco, "NULL", Integer.toString(dieuchinh), Integer.toString(dieuchinh), "",
//									"", doituong, maDoiTuongTS, "0", "0", "0", "100000", "0", "1", Integer.toString(dieuchinh), Integer.toString(dieuchinh), "");
//							if(msg.length()>0)
//							{
//								db.getConnection().rollback();
//								return "Không thể ghi nhận kế toán";
//							}
//						}
//						
//				
//					}
				
				
//			}
				
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
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	

}



