package geso.traphaco.center.servlets.thongbao;

import geso.traphaco.center.beans.thongbao.IThongbao;
import geso.traphaco.center.beans.thongbao.IThongbaoList;
import geso.traphaco.center.beans.thongbao.imp.Thongbao;
import geso.traphaco.center.beans.thongbao.imp.ThongbaoList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ThongbaoSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ThongbaoSvl() {
        super();
    }
    private boolean deletefile(String file)
	{
		System.out.println(file);
		  File f1 = new File(file);
		  boolean success = f1.delete();
		  if (!success)
		  {
			return false;
		  }
		  else
		  {
			 return true;
		   }
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");	    	    
	    HttpSession session = request.getSession();
	    String userId = (String) session.getAttribute("userId");
	    
	    IThongbaoList obj =  new ThongbaoList();
	    
	    String task = request.getParameter("task");
	    if(task == null)
	    	task = "";
	    obj.setTask(task);
	    System.out.println("TASK: "+task);
	    
	    String viewMode = request.getParameter("viewMode");
	    if(viewMode == null)
	    	viewMode = "1";
	    obj.setViewMode(viewMode);
	    
	    String loaivanban = request.getParameter("loaivanban");
	    if(loaivanban == null)
	    	loaivanban = "";
	    obj.setLoaithongbao(loaivanban);
	    
	    if(task.trim().length() > 0)
	    {
	    	if(task.equals("xoa"))
	    	{
	    		String pk = request.getParameter("id");	    		
	    		dbutils db = new dbutils();
	    		try 
	    		{
					db.getConnection().setAutoCommit(false);
					String query = "select filename from thongbao where pk_seq='"+pk+"'";
					System.out.println("cau select: "+query);
					ResultSet rs = db.get(query);
					String filename="";
					try 
					{
						rs.next();
						filename = rs.getString("filename");
					}
					catch (Exception e) 
					{
						db.update("rollback");
						obj.setMsg("Không thể xóa, lỗi: không thể lấy tên file");
					}
					if(!filename.equals("0"))
					{
						if(!this.deletefile("C:\\java-tomcat\\dinhkem\\"+filename))
						{
							db.update("rollback");
							obj.setMsg("Không thể xóa, lỗi: không thể lấy delete file");
						}
					}
					query = "delete thongbao_nhanvien where thongbao_fk= '" + pk+ "'";
		    		if(!db.update(query))
		    		{
		    			db.update("rollback");
		    			obj.setMsg("Không thể xóa, lỗi: "+ query);
		    		}
		    		query = "delete THONGBAO_VBHD where thongbao_fk= '" + pk+ "'";
		    		if(!db.update(query))
		    		{
		    			db.update("rollback");
		    			obj.setMsg("Không thể xóa, lỗi: "+ query);
		    		}
		    		query = "delete THONGBAO_VBCC where thongbao_fk= '" + pk+ "'";
		    		if(!db.update(query))
		    		{
		    			db.update("rollback");
		    			obj.setMsg("Không thể xóa, lỗi: "+ query);
		    		}
		    		query = "delete THONGBAO_VBTT where thongbao_fk= '" + pk+ "'";
		    		if(!db.update(query))
		    		{
		    			db.update("rollback");
		    			obj.setMsg("Không thể xóa, lỗi: "+ query);
		    		}
		    		query = "delete THONGBAO_VBSDBS where thongbao_fk= '" + pk+ "'";
		    		if(!db.update(query))
		    		{
		    			db.update("rollback");
		    			obj.setMsg("Không thể xóa, lỗi: "+ query);
		    		}
		    		query = "delete thongbao where pk_seq = '" + pk+ "'";
		    		if(!db.update(query))
		    		{
		    			db.update("rollback");
		    			obj.setMsg("Không thể xóa, lỗi: "+ query);
		    		}
		    		
		    		db.getConnection().commit();
					db.getConnection().setAutoCommit(true);
	    		} 
	    		catch (Exception e)
	    		{
	    			db.update("rollback");
	    			obj.setMsg("Không thể xóa, lỗi: "+ e.getMessage());
				}
	    	}
	    	else if(task.equals("1"))
	    	{
	    		obj =  new ThongbaoList(userId);
	    		obj.setUserId(userId);
	    		obj.setViewMode(viewMode);
	    		obj.setTask(task);
	    		//obj.setLoaithongbao(loaivanban);
	    		
	    		obj.initNhanvien("");
	    		
	    		session.setAttribute("obj", obj);
	    		String nextJSP = "/TraphacoHYERP/pages/Center/ThongBaoNhanVien.jsp";
	    		response.sendRedirect(nextJSP);
	    		return;
	    	}
	    	else if(task.equals("2"))
	    	{
	    		obj =  new ThongbaoList(userId);
	    		obj.setUserId(userId);
	    		obj.setViewMode(viewMode);
	    		obj.setLoaithongbao(loaivanban);
	    		
	    		obj.init("");
	    		
	    		session.setAttribute("obj", obj);
	    		String nextJSP = "/TraphacoHYERP/pages/Center/ThongBao.jsp";
	    		response.sendRedirect(nextJSP);
	    		return;
	    	}
	    }
	    
	    obj =  new ThongbaoList();
	    
	    obj.setUserId(userId);
	    System.out.println("___Loai van ban: " + loaivanban);
	    obj.setViewMode(viewMode);
	    System.out.println("___VIEW MODE: " + viewMode);
	    obj.setLoaithongbao(loaivanban);
	    
	    obj.init("");
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Center/ThongBao.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");	    	    
	    HttpSession session = request.getSession();	
	    String userId = (String) session.getAttribute("userId");
	    
	    IThongbaoList obj = new ThongbaoList();	  
	    
	    
	    String maso = request.getParameter("maso");
	    String trangthai = request.getParameter("trangthai");
	    String ngaybatdau = request.getParameter("ngaybatdau");
	    String ngayketthuc = request.getParameter("ngayketthuc");
	    String tieude = request.getParameter("tieude");
	    String noidung = request.getParameter("noidung");
	    
	    String viewMode = request.getParameter("viewMode");
		if(viewMode == null)
			viewMode = "1";
		obj.setViewMode(viewMode);
		
		String loaivanban = request.getParameter("loaivanban");
	    if(loaivanban == null)
	    	loaivanban = "";
	    obj.setLoaithongbao(loaivanban);
	    
	    if(ngaybatdau == null)
	    	ngaybatdau = "";
	    obj.setNgaybatdau(ngaybatdau);
	    
	    if(tieude == null)
	    	tieude = "";
	    obj.setTieude(tieude);
	    
	    if(noidung == null)
	    	noidung = "";
	    obj.setNoidung(noidung);
	    
	    if(ngayketthuc == null)
	    	ngayketthuc = "";
	    obj.setNgayketthuc(ngayketthuc);
	    
	    if(maso == null)
	    	maso = "";
	    obj.setId(maso);
	    dbutils db = new dbutils();
	    Utility util = new Utility();
	    
	    String query = "select   ct.pk_seq,ct.trangthai, ct.tinhtrang,ct.noidung,ct.tieude, ct.filename, ct.ngaybatdau, case when len(ct.ngayketthuc) <= 0 then N'Vô thời hạn' else ct.ngayketthuc end as ngayketthuc,ct.ngaytao,ct.nguoitao,ct.ngaysua,ct.nguoisua ,NV.TEN as TENNV,NV.PK_SEQ as MANV,NV2.TEN as TENNVS,NV2.PK_SEQ as MANVS, ct.nguoitao as nguoitaoTB from thongbao ct inner join NHANVIEN nv on ct.nguoitao = nv.PK_SEQ inner join NHANVIEN nv2 on ct.NGUOISUA = nv2.PK_SEQ where 1 = 1 ";
	    	query += " and ct.nguoitao = '" + userId + "' ";
	    
	    if(maso.length() > 0)
	    	query += " and cast(ct.pk_seq as nvarchar(20)) like '%" + maso + "%'";
	    if(ngaybatdau.length() > 0)
	    	query += " and ct.ngaybatdau >= '" + ngaybatdau + "'";
	    if(ngayketthuc.length() > 0)
	    	query += " and ct.ngayketthuc <= '" + ngayketthuc + "'";
	    if(tieude.length() > 0)
	    	query += " and upper(dbo.ftBoDau(ct.tieude)) like upper(N'%" + util.replaceAEIOU(tieude) + "%')";
	    if(noidung.length() > 0)
	    	query += " and upper(dbo.ftBoDau(ct.noidung)) like upper(N'%" + util.replaceAEIOU(noidung) + "%')";
	    
	    String action = request.getParameter("action");
	    //System.out.println(" serch "+query);
	    //System.out.println(" action"+action);
	    if(action.equals("search"))
	    {
		    obj.init(query);
			session.setAttribute("obj", obj);					
			String nextJSP = "/TraphacoHYERP/pages/Center/ThongBao.jsp";
			response.sendRedirect(nextJSP);
	    }
	    else if (action.equals("nhanvien"))
	    {
    	    if(trangthai == null)
    	    	trangthai = "";
    	    obj.setTrangthai(trangthai);
    	    query= "select a.pk_seq,a.tieude,a.filename,a.noidung,b.trangthai,a.ngaybatdau,a.ngayketthuc,a.ngaytao,a.nguoitao,a.ngaysua,a.nguoisua, a.loaithongbao, c.Ten as tennv " +
			 	   "from   thongbao a inner join thongbao_nhanvien b on a.pk_seq = b.thongbao_fk " +
			 	   "	   inner join nhanvien c on a.nguoitao = c.pk_seq	"+
			 	   "where b.trangthai != '2' and nhanvien_fk = '" + userId + "' ";
    	    if(maso.length() > 0)
    	    	query += " and a.pk_seq = '" + maso + "'";
    	    if(trangthai.length() > 0)
    	    	query += " and b.trangthai= '" + trangthai + "'";
    	    if(ngaybatdau.length() > 0)
    	    	query += " and a.ngaybatdau >= '" + ngaybatdau + "'";
    	    if(ngayketthuc.length() > 0)
    	    	query += " and a.ngayketthuc <= '" + ngayketthuc + "'";
    	    if(tieude.length() > 0)
    	    	query += " and a.tieude like N'%" + tieude + "%'";
    	    if(noidung.length() > 0)
    	    	query += " and a.noidung like N'%" + noidung + "%'";
    	    obj.initNhanvien(query);
			session.setAttribute("obj", obj);					
			String nextJSP = "/TraphacoHYERP/pages/Center/ThongBaoNhanVien.jsp";
			response.sendRedirect(nextJSP);
	    } 
	    else if (action.equals("backhome"))
	    {
	    	 query= "select count(a.pk_seq) as num " +
				 	   "from   thongbao a inner join thongbao_nhanvien b on a.pk_seq = b.thongbao_fk " +
				 	   "	   inner join nhanvien c on a.nguoitao = c.pk_seq	"+
				 	   "where b.trangthai = '0' and nhanvien_fk = '" + userId + "' ";
	    	 ResultSet ktrs =  	db.get(query);
	    	 try 
	    	 {
				ktrs.next();
				int num = ktrs.getInt("num");
				if(num != 0)
				{
					query= "select a.pk_seq,a.tieude,a.filename,a.noidung,b.trangthai,a.ngaybatdau,a.ngayketthuc,a.ngaytao,a.nguoitao,a.ngaysua,a.nguoisua, a.loaithongbao, c.Ten as tennv " +
						 	   "from   thongbao a inner join thongbao_nhanvien b on a.pk_seq = b.thongbao_fk " +
						 	   "	   inner join nhanvien c on a.nguoitao = c.pk_seq	"+
						 	   "where b.trangthai != '2' and nhanvien_fk = '" + userId + "' ";
					obj.setMsg("Vui lòng đọc hết thông báo mới được phép về trang chủ");
					obj.initNhanvien(query);
					session.setAttribute("obj", obj);					
					String nextJSP = "/TraphacoHYERP/pages/Center/ThongBaoNhanVien.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					response.sendRedirect("/TraphacoHYERP/Distributor/mainpage.jsp");
				}
				
			} 
	    	catch (SQLException e) 
	    	{
				
				e.printStackTrace();
			}
	    } 
	    else if(action.equals("new"))
	    {
	    	IThongbao tbBean = new Thongbao();	
	    	tbBean.createRs();
	    	tbBean.setLoaithongbao(loaivanban);
	    	obj.setViewMode(viewMode);
	    	
	    	session.setAttribute("tbBean", tbBean);		
	    	
	    	System.out.println("__Vao tao moi: --  ViewMode la: " + viewMode + "  -- Trong Bean: " + obj.getViewMode());
			String nextJSP = "/TraphacoHYERP/pages/Center/ThongBaoNew.jsp";
			if(viewMode.equals("0"))
				nextJSP = "/TraphacoHYERP/pages/Center/ThongBaoNew_NPP.jsp";
			
			response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj = new ThongbaoList();
	    		obj.setViewMode(viewMode);
	    
		    	System.out.println("nxtApprSplitting "+Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setUserId(userId);
		    	obj.init(query);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	String nextJSP = "/TraphacoHYERP/pages/Center/ThongBao.jsp";
		    	response.sendRedirect(nextJSP);
		    }
	    	else if(action.equals("viewnv") || action.equals("nextnv") || action.equals("prevnv"))
	    	{
	    		obj = new ThongbaoList();
	    		obj.setViewMode(viewMode);
	    		
	    	    if(trangthai == null)
	    	    	trangthai = "";
	    	    obj.setTrangthai(trangthai);
	    	    tieude = request.getParameter("tieude");
	    	    if(ngaybatdau == null)
	    	    	ngaybatdau = "";
	    	    if(tieude == null)
	    	    	tieude = "";
	    	    obj.setTieude(tieude);
	    	    query= "select a.ngaybatdau,a.ngayketthuc,a.pk_seq,a.tieude, a.tinhtrang, a.noidung,b.trangthai, a.nguoitao as nguoitaoTB from thongbao a inner join thongbao_nhanvien b on a.pk_seq = b.thongbao_fk where nhanvien_fk='"+userId+"' and 1=1 ";
	    	    if(maso.length() > 0)
	    	    	query += " and a.pk_seq = '" + maso + "'";
	    	    if(trangthai.length() > 0)
	    	    	query += " and b.trangthai= '" + trangthai + "'";
	    	    if(ngaybatdau.length() > 0)
	    	    	query += " and a.ngaybatdau >= '" + ngaybatdau + "'";
	    	    if(ngayketthuc.length() > 0)
	    	    	query += " and a.ngayketthuc <= '" + ngayketthuc + "'";
	    	    if(tieude.length() > 0)
	    	    	query += " and a.tieude like N'%" + tieude + "%'";
	    	    if(noidung.length() > 0)
	    	    	query += " and a.noidung like N'%" + noidung + "%'";
		    	System.out.println("nxtApprSplitting sxl"+Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setUserId(userId);
		    	obj.init(query);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	String nextJSP = "/TraphacoHYERP/pages/Center/ThongBaoNhanVien.jsp";
		    	response.sendRedirect(nextJSP);
	    	}
	    	else
	    	{
		    	obj = new ThongbaoList();
		    	obj.setViewMode(viewMode);
		    	obj.setLoaithongbao(loaivanban);
		    	obj.init(query);
				obj.setUserId(userId);				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
	    		String nextJSP = "/TraphacoHYERP/pages/Center/ThongBao.jsp";
	    		response.sendRedirect(nextJSP);
	    	}
	    }
	}

}
