package geso.traphaco.erp.servlets.kiemdinhchatluong.giay;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.kiemdinhchatluong.giay.IErpKiemdinhchatluong;
import geso.traphaco.erp.beans.kiemdinhchatluong.giay.IErpKiemdinhchatluongList;
import geso.traphaco.erp.beans.kiemdinhchatluong.giay.imp.ErpKiemdinhchatluong;
import geso.traphaco.erp.beans.kiemdinhchatluong.giay.imp.ErpKiemdinhchatluongList;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpKiemdinhchatluongSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	
    public ErpKiemdinhchatluongSvl() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    IErpKiemdinhchatluong dcsxBean;
	    HttpSession session = request.getSession();	
	    
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String congtyId=((String)session.getAttribute("congtyId"));
	    IErpKiemdinhchatluongList obj = new ErpKiemdinhchatluongList();
	    obj.setUserId(userId);
	    obj.setCongtyId(congtyId);
	    String action = util.getAction(querystring);
	    String id = util.getId(querystring);
	    String msg = "";
	    
	    if(action.trim().equals("delete"))
	    {
	    	dcsxBean = new ErpKiemdinhchatluong(id);
	    	dcsxBean.setUserId(userId);
	    	dcsxBean.HuyKiemDinh();
	    	msg=dcsxBean.getMsg();
	    }else if(action.equals("hoantat")){
	    	
	    	dcsxBean = new ErpKiemdinhchatluong(id);
	    	dcsxBean.setUserId(userId);
	    	dcsxBean.Hoantat();
	    	msg=dcsxBean.getMsg();
	    	
	    }
	    obj.setMsg(msg);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuongGiay.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    
	    out = response.getWriter();
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));     
	    IErpKiemdinhchatluongList obj;
	    
		String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    if(action.equals("new"))
	    {
 
	    }
	    else if(action.equals("view") || action.equals("next") || action.equals("prev"))
    	{
    		System.out.println("toi day");
    		obj = new ErpKiemdinhchatluongList();
    		obj.setCongtyId((String)session.getAttribute("congtyId"));
    		
	    	String search = getSearchQuery(request, obj);
	    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
	    	obj.setUserId(userId);
	    	obj.init(search);
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	session.setAttribute("obj", obj);
	    	response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuongGiay.jsp");	
	    }
	    else
	    {
	    	obj = new ErpKiemdinhchatluongList();
		    obj.setUserId(userId);
		    String congtyId=((String)session.getAttribute("congtyId"));
		    obj.setCongtyId(congtyId);
		    
	    	String search = getSearchQuery(request, obj);
	    	obj.setUserId(userId);
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuongGiay.jsp");	
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpKiemdinhchatluongList obj) 
	{
		Utility util = new Utility();
		
		String solsx = request.getParameter("solsx");
		if(solsx == null)
			solsx = "";
		obj.setSoLSX(solsx);
		
		String solo = request.getParameter("solo");
		if(solo == null)
			solo = "";
		obj.setSolo(solo);
		
		String sanpham = request.getParameter("sanpham");
		if(sanpham == null)
			sanpham = "";
		obj.setSanpham(sanpham);
		
		String sochungtu = request.getParameter("sochungtu");
		if(sochungtu == null)
			sochungtu = "";
		obj.setChungtu(sochungtu);
		
		String ngaysanxuat = request.getParameter("ngaysanxuat");
		if(ngaysanxuat == null)
			ngaysanxuat = "";
		obj.setNgaySanXuat(ngaysanxuat);
		
		String nguoitao = request.getParameter("nguoitao");
		if(nguoitao == null)
			nguoitao = "";
		obj.setNguoiTao(nguoitao);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String nhamayid = request.getParameter("nhamayid");
		if(nhamayid == null)
			nhamayid = "";
		obj.setNhaMayId(nhamayid);
		
		
		String 	sql =   "   select a.pk_seq ,isnull(lsx.diengiai,'') as diengiai , a.nhapkho_fk  as sonhapkho,  c.ten  as spTen, a.soluong, "+
		"	a.solo, a.ngaytao, isnull(a.ngaysua, '') as ngaysua, a.trangthai, d.TEN as nguoitao, "+ 
		"	isnull(e.TEN, '') as nguoisua,a.LENHSANXUAT_FK AS LenhSanXuatId,a.CONGDOAN_FK AS CONGDOANID ,A.NGAYKIEM,nk_sp.ngaysanxuat as ngaysanxuat "+  
		"   from ERP_YeuCauKiemDinh a inner join ERP_SANPHAM c on a.sanpham_fk = c.PK_SEQ  " +
		" inner join ERP_NHAPKHO_sanpham nk_sp on a.nhapkho_fk = nk_sp.SONHAPKHO_FK and  a.sanpham_fk = nk_sp.SANPHAM_FK "+
		"   left join erp_lenhsanxuat_giay lsx on lsx.pk_seq= a.LENHSANXUAT_FK  "+ 
		"   left join NHANVIEN d on a.nguoitao = d.PK_SEQ left join NHANVIEN e on a.nguoisua = e.PK_SEQ "+ 
		"   where a.congty_fk='"+obj.getCongtyId()+"' and a.LENHSANXUAT_FK is not null " ;
		
		if(solsx.length() > 0)
			sql += " and   cast( a.LENHSANXUAT_FK as nvarchar(10))    like N'%" + solsx + "%' ";
		
		if(sochungtu.length() > 0)
			sql += " and  cast( a.pk_seq as nvarchar(10))    like N'%" + sochungtu + "%' ";

		if(ngaysanxuat.length() > 0)
			sql +=" and nk_sp.ngaysanxuat = '"+ngaysanxuat+"'";
		
		if(nguoitao.length() > 0)
			sql +=" and dbo.ftBoDau(d.ten) like N'%"+util.replaceAEIOU(nguoitao)+"%' OR d.pk_seq like '%"+nguoitao+"%' ";
		
		if(solo.length() > 0)
			sql += " and a.solo like N'%" + solo + "%' ";
		
		if(sanpham.length() > 0)
			sql += " and c.timkiem  like N'%" + util.replaceAEIOU(sanpham) + "%'  ";
		
		if(trangthai.length() > 0)
			sql += " and a.trangthai = '" + trangthai + "' ";
		
		if(nhamayid.length()>0)
			sql += " and lsx.nhamay_fk = "+nhamayid;
 
		return sql;
	}
	
	

}
