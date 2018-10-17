package geso.traphaco.distributor.servlets.phieuxuatkho;

import geso.traphaco.distributor.beans.phieuxuatkho.IPhieuxuatkho;
import geso.traphaco.distributor.beans.phieuxuatkho.IPhieuxuatkhoList;
import geso.traphaco.distributor.beans.phieuxuatkho.imp.Phieuxuatkho;
import geso.traphaco.distributor.beans.phieuxuatkho.imp.PhieuxuatkhoList;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class PhieuxuatkhoSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
    public PhieuxuatkhoSvl() 
    {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IPhieuxuatkhoList obj;
		PrintWriter out = response.getWriter();; 
		String userId;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();

	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    userId = util.getUserId(querystring);
	    //out.println(userId);
	    
	    if (userId.length() == 0 )
	    	userId = request.getParameter("userId");
	    
	    String action = util.getAction(querystring);
	    out.println(action);
	    
	    String pxkId = util.getId(querystring);

	    String msg = "";
	    if(action.equals("chotphieu"))
	    {
    		String nppId = request.getParameter("nppId");
    		String ngaylap = request.getParameter("ngaylap");
    		
			msg = this.Chotphieuxuat(pxkId, nppId, ngaylap);
			if(msg.length() > 0)
			{
				out.println("Khong the chot phieu xuat...\n");
				System.out.println("Error Mesege: " + msg + "\n");
			}
	    }
	    else
	    {
	    	if(action.equals("delete"))
	    	{
	    		DeletePxk(pxkId);
	    	}
	    }
	   	    
	    obj = new PhieuxuatkhoList();
	    obj.setRequestObj(request);
	    obj.setUserId(userId);
	    
	    obj.init("");
	    obj.setMsg(msg);
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Distributor/PhieuXuatKho.jsp";
		response.sendRedirect(nextJSP);
	}

	private String DeletePxk(String pxkId) 
	{
		dbutils db = new dbutils();
		//khi moi tao phieuxuatkho chua lam gi het, nen co the xoa thang
		//String query = "update phieuxuatkho set trangthai = '2' where pxk_fk = '" + pxkId + "'";
		//db.update(query);
		try{
			db.getConnection().setAutoCommit(false);
			String query="delete phieuxuatkho_tienkm where pxk_fk = '" + pxkId + "'";
			if(!db.update(query)){
				db.getConnection().rollback();
				return query;
			}
					
		  //System.out.println("delete phieuxuatkho_tienkm where pxk_fk = '" + pxkId + "'");
			query="delete phieuxuatkho_sanpham where pxk_fk = '" + pxkId + "'";
			System.out.println(query);
			if(! db.update(query)){
				db.getConnection().rollback();
				return query;
			}
		 //System.out.println("delete phieuxuatkho_sanpham where pxk_fk = '" + pxkId + "'");
			query="delete phieuxuatkho_spkm where pxk_fk = '" + pxkId + "'";
			System.out.println(query);
			if(! db.update(query)){
				db.getConnection().rollback();
				return query;
			}
			query="delete PHIEUXUATKHO_DONHANG where pxk_fk='"+pxkId+"'";
			System.out.println(query);
			if(! db.update(query)){
				db.getConnection().rollback();
				return query;
			}
			//System.out.println("delete phieuxuatkho_spkm where pxk_fk = '" + pxkId + "'");
			query="delete phieuxuatkho where pk_seq = '" + pxkId + "'";
			System.out.println(query);
			if(! db.update(query)){
				db.getConnection().rollback();
				return query;
			}
			//System.out.println("delete phieuxuatkho where pk_seq = '" + pxkId + "'");
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
		}catch(Exception er){
			System.out.println(er);
		}
		
		return "";
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IPhieuxuatkhoList obj = new PhieuxuatkhoList();
		PrintWriter out = response.getWriter();; 
		String userId;
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
		HttpSession session = request.getSession();
	    userId = request.getParameter("userId");
	    
	    String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    out.println(action); 
	    obj.setRequestObj(request);
	    if (action.equals("Tao moi"))
	    {
	    	// Empty Bean for distributor
	    	IPhieuxuatkho pxkBean = (IPhieuxuatkho) new Phieuxuatkho("");
	    	pxkBean.setUserId(userId);
	    	pxkBean.createRS();
	    	
	    	// Save Data into session
	    	session.setAttribute("pxkBean", pxkBean);
    		
    		String nextJSP = "/TraphacoHYERP/pages/Distributor/PhieuXuatKhoNew.jsp";
    		response.sendRedirect(nextJSP);
    		
	    }
	    else if(action.equals("clear"))
	    {
	    	obj.setUserId(userId);
	    	obj.init("");
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
    		response.sendRedirect("/TraphacoHYERP/pages/Distributor/PhieuXuatKho.jsp");	 
	    }
	    
	    else if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    {
	    	String search = getSearchQuery(request, obj);
	    	
	    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
	    	obj.setUserId(userId);
	    	obj.init(search);
	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	session.setAttribute("obj", obj);
	    	response.sendRedirect("/TraphacoHYERP/pages/Distributor/PhieuXuatKho.jsp");	
	    }
	    
	    else
	    {
	    	obj = new PhieuxuatkhoList();
	    	obj.setRequestObj(request);
	    	
	    	
	    	obj.setUserId(userId);
	    	String search = getSearchQuery(request, obj);
	    	
	    	
	    	obj.init(search);
				
	    	session.setAttribute("obj", obj);  	
    		session.setAttribute("userId", userId);
    		session.setAttribute("abc", search);
		
    		response.sendRedirect("/TraphacoHYERP/pages/Distributor/PhieuXuatKho.jsp");	    		    	
	    }
	    
	}
	
	private String getSearchQuery(HttpServletRequest request, IPhieuxuatkhoList obj) 
	{	
		String nppId = request.getParameter("nppId");
		geso.traphaco.center.util.Utility utilCenter = new geso.traphaco.center.util.Utility();
    	if ( nppId == null)
    		nppId = "";
    	obj.setNppId(nppId);
    	
		String nvgnId = request.getParameter("nvgnTen");
    	if ( nvgnId == null)
    		nvgnId = "";
    	obj.setNvgnId(nvgnId);
    	
    	String trangthai = request.getParameter("trangthai");
    	if ( trangthai == null)
    		trangthai = "";
    	obj.setTrangthai(trangthai);
    	
    	String tungay = request.getParameter("tungay");
    	if (tungay == null)
    		tungay = "";    	
    	obj.setTungay(tungay);
    	
    	String denngay = request.getParameter("denngay");
    	if (denngay == null)
    		denngay = "";
    	obj.setDenngay(denngay);
    	
    	String mapxk = request.getParameter("mapxk");
    	if (mapxk == null)
    		mapxk = "";
    	obj.setmaPXK(mapxk);
    	
    	String maFast = request.getParameter("mafast");
    	if(maFast == null)
    		maFast="";
    	obj.setmaFast(maFast);
    	
    	String maDonhang = request.getParameter("madonhang");
    	if(maDonhang == null)
    		maDonhang="";
    	obj.setmaDonhang(maDonhang);
    	
    	String khachhang = request.getParameter("khId");
    	if(khachhang == null)
    		khachhang="";
    	obj.setkhachHang(khachhang);
    	
    	
    	String query = "";
    	query = "select ROW_NUMBER() OVER(ORDER BY pxk.ngaylapphieu DESC) AS 'stt', pxk.pk_seq as pxkId, nvgn.pk_seq as nvgnId, nvgn.ten as nvgnTen, pxk.trangthai, pxk.ngaylapphieu, pxk.ngaytao, pxk.ngaysua, nv1.ten as nguoitao, nv2.ten as nguoisua, isnull(c.maFAST,'' )as mafast, isnull(c.TEN, '') as khachhang, isnull(c.PK_SEQ, '0') makh ";
    	query += "from phieuxuatkho pxk inner join nhanviengiaonhan nvgn on pxk.nvgn_fk = nvgn.pk_seq inner join nhanvien nv1 on pxk.nguoitao = nv1.pk_seq inner join nhanvien nv2 on pxk.nguoisua = nv2.pk_seq left join PHIEUXUATKHO_DONHANG a on pxk.PK_SEQ = a.PXK_FK left join DONHANG b on a.DONHANG_FK =b.PK_SEQ left join KHACHHANG c on b.KHACHHANG_FK = c.PK_SEQ	"
    			+ " where pxk.npp_fk = '" + nppId + "' and b.kho_fk in "+utilCenter.quyen_kho(obj.getUserId());
    		
    	if (nvgnId.length() > 0)
    	{
			query = query + " and nvgn.pk_seq='" + nvgnId + "'";			
    	}
    	
    	if (trangthai.length() > 0)
    	{
			query = query + " and pxk.trangthai='" + trangthai + "'";			
    	}
    	
    	if (tungay.length() > 0)
    	{
    		query = query + " and pxk.ngaytao >= '" + tungay + "'"; 
    	}
    	
    	if (denngay.length() > 0)
    	{
    		query = query + " and pxk.ngaytao <= '" + denngay + "'"; 
    	}
    	
    	if (mapxk.length() > 0)
    	{
    		query = query + " and pxk.pk_seq like'%" + mapxk + "%'"; 
    	}
    	
    	if(maFast.length() >0){
    		query = query + " and c.maFAST like '%"+ maFast + "%'";
    	}
    	if(maDonhang.length() >0){
    		query = query + " and b.pk_seq like '%"+ maDonhang + "%'";
    	}
    	
    	if(khachhang.length() >0){
    		Utility util = new Utility();
    		query = query + " and [dbo].[fuConvertToUnsign1](lower(c.TEN)) like N'%"+ util.replaceAEIOU(khachhang)  + "%' or c.PK_SEQ like N'%"+ util.replaceAEIOU(khachhang) +"%'";
    	}
    	
    	
    	
    	System.out.print("\n Search Query la: " + query + "\n");
    	
    	return query;
	}
	
	private String Chotphieuxuat(String pxkId, String nppId, String ngaylap) 
	{	
		dbutils db = new dbutils();
		
		String msg = "";
		String ngaytiep = "";
		int songay = 0;
		
		//check ton kho (HANG SU DUNG TRONG KHO NPP)
		String query =  "select pxk_sp.spMa, isnull(kho_npp.SOLUONG,0) as tonkho, pxk_sp.soluong " + 
						"from " +
						"(" +
							"select khoId, kbhId, spId, spMa, sum(soluong) as soluong " +
							"from( " +
								"select c.kho_fk as khoId, c.kbh_fk as kbhId, b.pk_seq as spId, b.ma as spMa, sum(a.soluong) as soluong " +
								"from donhang_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq inner join donhang c on a.donhang_fk = c.pk_seq " +
								"where c.trangthai != 2 and a.donhang_fk in (select donhang_fk from phieuxuatkho_donhang where pxk_fk = " + pxkId + ") and a.khoNVBH = '0' " + 
								"group by c.kho_fk, c.kbh_fk, b.pk_seq, b.ma " +
							"union all " +
								"select b.kho_fk as khoId, e.kbh_fk as kbhId, d.pk_seq as spId, a.spMa, sum(a.soluong) as soluong " +
								"from donhang_ctkm_trakm a inner join ctkhuyenmai b on a.ctkmid = b.pk_seq " +
								"	inner join sanpham d on a.spMa = d.ma inner join donhang e on a.donhangId = e.pk_seq " +
								"where e.trangthai != 2 and a.spMa is not null and a.donhangId in (select donhang_fk from phieuxuatkho_donhang where pxk_fk = " + pxkId + ") and a.khoNVBH = '0' " +
								"group by b.kho_fk, e.kbh_fk, a.ctkmId, a.spMa, d.pk_seq " +
							" ) a " +
							" group by khoId, kbhId, spId, spMa " +
						") " +
						"pxk_sp left join " +
						"( " +
						"	select kho_fk, npp_fk, sanpham_fk, kbh_fk, AVAILABLE " +
						"	from nhapp_kho where npp_fk = '" + nppId + "' " +
						") " +
						"kho_npp on pxk_sp.khoId = kho_npp.kho_fk and pxk_sp.kbhId = kho_npp.kbh_fk and pxk_sp.spId = kho_npp.sanpham_fk ";
						//"where (isnull(kho_npp.SOLUONG,0) - pxk_sp.soluong) < 0";
		
		System.out.println("Query check chot phieu xuat kho: " + query + "\n");
		ResultSet sosp = db.get(query);
		String spMa = "";
		if(sosp != null)
		{
			try 
			{
				while(sosp.next())
				{
					if( sosp.getDouble("tonkho") <  sosp.getDouble("soluong") )
						spMa += sosp.getString("spMa") + ", ";
				}
				sosp.close();
			} 
			catch(Exception e1) { return "Loi: " + e1.getMessage(); }
		}
		
		if(spMa.length() > 0)
		{
			msg = "Các mã sản phẩm sau: " + spMa + " không đủ số lượng trong kho NHÀ PHÂN PHỐI \nVui lòng kiểm tra lại số lượng trước khi chốt phiếu xuất kho";
			return msg;
		}
		
		//THANG CHOT PHIEU XUAT KHO BUOC PHAI SAU THANG KS + 1
		/*query = "select top(1) NAM as namMax, THANGKS as thangMax, " +
				"	( select ngaylapphieu from PHIEUXUATKHO where pk_seq = '" + pxkId + "' ) as ngaylapphieu " +
				"from KHOASOTHANG where NPP_FK = '" + nppId + "' " +
				"order by NAM desc, THANGKS desc ";
		System.out.println("1.Khoi tao thang: " + query);
		ResultSet rs = db.get(query);
		
		try
		{
			if(rs != null)
			{
				while(rs.next())
				{
					String thangHL = "";
					String namHL = "";
					
					String thangKs = rs.getString("thangMax");
					String namKs = rs.getString("namMax"); 
			
					String nam = rs.getString("ngaylapphieu").substring(0, 4);
					String thang = rs.getString("ngaylapphieu").substring(5, 7);
					
					if(thangKs.equals("12"))
					{
						thangHL = "1";
						namHL = Integer.toString(Integer.parseInt(namKs) + 1);
					}
					else
					{
						thangHL = Integer.toString(Integer.parseInt(thangKs) + 1);
						namHL = namKs;
					}
					
					if(thangHL.trim().length() < 2)
						thangHL = "0" + thangHL;
					
					if(	!thangHL.equals(thang) || !namHL.equals(nam) )
					{
						msg = "Bạn chỉ được phép chốt phiếu xuất kho sau tháng khóa sổ 1 tháng";
						rs.close();
						return msg;
					}
					
				}
				rs.close();
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return "Lỗi khi chốt xuất kho " + e.getMessage();
		}
		*/
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			//DU TON KHO, CAP NHAT KHO TONG
			query = "delete from phieuxuatkho_donhang " +
					"where pxk_fk = '" + pxkId + "' and donhang_fk in (select donhang_fk from phieuxuatkho_donhang a inner join donhang b on a.donhang_fk = b.pk_seq where a.pxk_fk = '" + pxkId + "' and b.trangthai = '2')";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "1.Lỗi khi chốt xuất kho: " + query;
			}

			query = " update a set a.tonggiatri = ISNULL( ( ( select sum( (soluong * giamua) - chietkhau ) from donhang_sanpham  where donhang_fk = a.DONHANG_FK  group by donhang_fk )   " +
					" 								- isnull( ( select sum(tonggiatri) as giatriKM from donhang_ctkm_trakm where donhangId = a.DONHANG_FK and SPMA is null ), 0 ) ), 0 )  " +
					" from phieuxuatkho_donhang a  " +
					" where a.pxk_fk = '" + pxkId + "' ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "2.Lỗi khi chốt xuất kho: " + query;
			}
			
			query = "delete phieuxuatkho_sanpham where pxk_fk = '" + pxkId + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "3.Lỗi khi chốt xuất kho: " + query;
			}
			
			query = "delete PHIEUXUATKHO_SANPHAM_CHITIET where pxk_fk = '" + pxkId + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "3.Lỗi khi chốt xuất kho: " + query;
			}
			
			//INSERT SAN PHAM
			query = " Insert into phieuxuatkho_sanpham(pxk_fk, sanpham_fk, kho_fk, kbh_fk, soluong, khoNVBH, NVBH_FK) " +
					" select '" + pxkId + "', b.pk_seq as spId, c.kho_fk as khoId, c.kbh_fk as kbhId, sum(a.soluong) as soluong, khoNVBH, case when khoNVBH = 0 then NULL else c.ddkd_fk end as ddkd_fk  " +
					" from donhang_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq inner join donhang c on a.donhang_fk = c.pk_seq   " +
					" where c.trangthai != 2 and a.donhang_fk in ( select donhang_fk from phieuxuatkho_donhang where pxk_fk = '" + pxkId + "' )  " +
					" group by c.kho_fk, c.kbh_fk, b.pk_seq, khoNVBH, ( case when khoNVBH = 0 then NULL else c.ddkd_fk end ) ";
			System.out.println("---4.CHEN SP: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				db.getConnection().rollback();
				return "4.Lỗi khi chốt xuất kho: " + query;
			}
			
			
			//B1. CAP NHAT KHO TONG
			query = " select ( case when khoNVBH = 0 then NULL else c.ddkd_fk end ) as DDKD_FK, c.kho_fk as khoId, c.kbh_fk as kbhId, b.pk_seq as spId, b.ten as spTEN, khoNVBH, sum(a.soluong) as soluong  " +
					" from donhang_sanpham a inner join sanpham b on a.sanpham_fk = b.pk_seq inner join donhang c on a.donhang_fk = c.pk_seq   " +
					" where c.trangthai != 2 and a.donhang_fk in ( select donhang_fk from phieuxuatkho_donhang where pxk_fk = '" + pxkId + "' )  " +
					" group by ( case when khoNVBH = 0 then NULL else c.ddkd_fk end ), c.kho_fk, c.kbh_fk, b.pk_seq, b.ten, khoNVBH ";
			System.out.println("---5.CHEN SP: " + query);
			ResultSet rsKHO = db.get(query);
			if(rsKHO != null)
			{
				while(rsKHO.next())
				{
					String nvbhId = rsKHO.getString("DDKD_FK");
					String khoId = rsKHO.getString("khoId");
					String kbhId = rsKHO.getString("kbhId");
					String spId = rsKHO.getString("spId");
					//String spTEN = rsKHO.getString("spTEN");
					String khoNVBH = rsKHO.getString("khoNVBH");
					double soluong = rsKHO.getDouble("soluong");
					
					
					query = "update nhapp_kho set BOOKED = BOOKED - '" + soluong + "', SOLUONG = SOLUONG - '" + soluong + "' " +
							" where sanpham_fk = '" + spId + "' and npp_fk = '" + nppId + "' and kho_fk = '" + khoId + "' and kbh_fk = '" + kbhId + "' ";
					
					//System.out.println("---UPDATE KHO TONG: " + query);
					
					if(db.updateReturnInt(query) != 1 )
					{
						db.getConnection().rollback();
						return "5.Lỗi khi chốt xuất kho: " + query;
					}
					
					
					//TU DE XUAT LO --> KHO CHI TIET THI VAN TRU SO LUONG + AVAI
					if(khoNVBH.equals("0"))
					{
						query = "select AVAILABLE, SOLO, NGAYHETHAN  " +
								"from NHAPP_KHO_CHITIET " +
								"where AVAILABLE > 0 and NPP_FK = '" + nppId + "' and KBH_FK = '" + kbhId + "' and KHO_FK = '" + khoId + "' and SANPHAM_FK = '" + spId + "' " +
								"order by NGAYHETHAN asc, AVAILABLE asc";
					}
					else
					{
						query = "select AVAILABLE, SOLO, NGAYHETHAN  " +
								"from NVBH_KHO_CHITIET " +
								"where AVAILABLE > 0 and NVBH_FK = '" + nvbhId + "' and NPP_FK = '" + nppId + "' and KBH_FK = '" + kbhId + "' and KHO_FK = '" + khoId + "' and SANPHAM_FK = '" + spId + "' " +
								"order by NGAYHETHAN asc, AVAILABLE asc";
					}
					
					System.out.println("--TU DE XUAT: " + query);
					ResultSet rs = db.get(query);
					
					double tongluongxuatCT = 0;  //PHAI BAT BUOC TONG LUONG XUAT O KHO CHI TIET PHAI BANG TONG LUONG XUAT O KHO TONG
					if(rs != null)
					{
						double totalLUONG = 0;
						boolean exit = false;
						while(rs.next() && !exit)
						{
							
							String NgayHetHan= rs.getString("NGAYHETHAN");
							
							totalLUONG += rs.getDouble("AVAILABLE");
							double soluongXUAT = 0;
							
							if(totalLUONG <= soluong)
							{
								soluongXUAT = rs.getDouble("AVAILABLE");
							}
							else
							{
								soluongXUAT = soluong - ( totalLUONG - rs.getDouble("AVAILABLE") );
								exit = true;
							}
							
							//CAP NHAT KHO CHI TIET
							if(khoNVBH.equals("0"))
							{
								query = "update nhapp_kho_chitiet set AVAILABLE = AVAILABLE - '" + soluongXUAT + "', SOLUONG = SOLUONG - '" + soluongXUAT + "' " +
										" where sanpham_fk = '" + spId + "' and npp_fk = '" + nppId + "' and kho_fk = '" + khoId + "' and kbh_fk = '" + kbhId + "' and SOLO = '" + rs.getString("SOLO") + "' and NgayHetHan='"+NgayHetHan+"' ";
							}
							else
							{
								query = "update NVBH_KHO_CHITIET set BOOKED = BOOKED - '" + soluongXUAT + "', SOLUONG = SOLUONG - '" + soluongXUAT + "' " +
										" where nvbh_fk = '" + nvbhId + "' and sanpham_fk = '" + spId + "' and npp_fk = '" + nppId + "' and kho_fk = '" + khoId + "' and kbh_fk = '" + kbhId + "' and SOLO = '" + rs.getString("SOLO") + "' ";
							}
							
							//System.out.println("---UPDATE KHO CHI TIET: " + query);
							if(db.updateReturnInt(query)!=1)
							{
								db.getConnection().rollback();
								return "6.Lỗi khi chốt xuất kho: " + query;
							}
							
							//INSERT PXK - CHI TIET
							query = "insert into PHIEUXUATKHO_SANPHAM_CHITIET (PXK_FK, KBH_FK, KHO_FK, SANPHAM_FK, SOLUONG, SOLO, NGAYHETHAN, khoNVBH, NVBH_FK) " +
									"values( '" + pxkId + "', '" + kbhId + "', '" + khoId + "', '" + spId + "', '" + soluongXUAT +  "', '" + rs.getString("SOLO") + "', '" + rs.getString("NGAYHETHAN") + "', '" + khoNVBH + "', " + ( khoNVBH.equals("1") ? nvbhId : "NULL" ) + " )";
							//System.out.println("---CHEN PXK CHI TIET: " + query);
							if(db.updateReturnInt(query) <= 0 )
							{
								db.getConnection().rollback();
								return "7.Lỗi khi chốt xuất kho: " + query;
							}
							
							tongluongxuatCT += soluongXUAT;
							if(exit)  //DA XUAT DU
							{
								//rs.close();
								break;
							}
						}
						rs.close();
					}
					
					if(tongluongxuatCT != soluong)
					{
						db.getConnection().rollback();
						rsKHO.close();
						return "1.Lỗi hệ thống ( tổng xuất theo lô đề xuất đang bị sai ). Vui lòng liên hệ trung tâm để được hỗ trợ xử lý.";
					}
					
				}
				rsKHO.close();
			}
			
			//CHECK TONG KHO CHI TIET PHAI BANG TONG TRONG KHO TONG
			query = "select count(*) as soDONG  " +
					"from PHIEUXUATKHO_SANPHAM tong left join  " +
					"	( " +
					"		select sanpham_fk, kbh_fk, kho_fk, sum(soluong) as soluong  " +
					"		from PHIEUXUATKHO_SANPHAM_CHITIET " +
					"		where  PXK_FK = '" + pxkId + "' " +
					"		group by sanpham_fk, kbh_fk, kho_fk " +
					"	) " +
					"	CT on tong.sanpham_fk = CT.sanpham_fk and tong.kbh_fk = CT.kbh_fk and tong.kho_fk = CT.kho_fk " +
					"where PXK_FK = '" + pxkId + "' and tong.soluong != isnull(CT.soluong, 0) ";
			ResultSet rsCHECK = db.get(query);
			int soDONG = 0;
			if(rsCHECK != null )
			{
				if( rsCHECK.next() )
				{
					soDONG = rsCHECK.getInt("soDONG");
				}
				rsCHECK.close();
			}
			
			if(soDONG > 0)
			{
				db.getConnection().rollback();
				return "11.Lỗi hệ thống ( tổng xuất theo lô đề xuất đang bị sai ). Vui lòng liên hệ trung tâm để được hỗ trợ xử lý.";
			}
			
			query = "delete phieuxuatkho_spkm where pxk_fk = '" + pxkId + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "4.Lỗi khi chốt xuất kho: " + query;
			}
			
			query = "delete PHIEUXUATKHO_SPKM_CHITIET where pxk_fk = '" + pxkId + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "4.Lỗi khi chốt xuất kho: " + query;
			}
			
			//INSERT SPKM
			query = " Insert into phieuxuatkho_spkm(pxk_fk, sanpham_fk, kho_fk, kbh_fk, scheme, soluong, khoNVBH, NVBH_FK)  " +
					" select '" + pxkId + "', d.pk_seq as spId,  b.kho_fk as khoId, e.kbh_fk as kbhId, a.ctkmId, sum(a.soluong) as soluong, a.khoNVBH, ( case when khoNVBH = 0 then NULL else e.ddkd_fk end )  " +
					" from donhang_ctkm_trakm a inner join ctkhuyenmai b on a.ctkmid = b.pk_seq inner join sanpham d on a.spMa = d.ma inner join donhang e on a.donhangId = e.pk_seq  " +
					" where e.trangthai != 2 and a.spMa is not null and a.donhangId in (select donhang_fk from phieuxuatkho_donhang where pxk_fk = '" + pxkId + "')  " +
					" group by b.kho_fk, e.kbh_fk, a.ctkmId, d.pk_seq, khoNVBH, ( case when khoNVBH = 0 then NULL else e.ddkd_fk end )  ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "4.Lỗi khi chốt xuất kho: " + query;
			}
			
			
			//B1. CAP NHAT KHO TONG HANG KHUYEN MAI
			query = " select '" + pxkId + "', d.pk_seq as spId,  b.kho_fk as khoId, e.kbh_fk as kbhId, a.ctkmId, sum(a.soluong) as soluong, a.khoNVBH, ( case when khoNVBH = 0 then NULL else e.ddkd_fk end ) as DDKD_FK  " +
					" from donhang_ctkm_trakm a inner join ctkhuyenmai b on a.ctkmid = b.pk_seq inner join sanpham d on a.spMa = d.ma inner join donhang e on a.donhangId = e.pk_seq  " +
					" where e.trangthai != 2 and a.spMa is not null and a.donhangId in (select donhang_fk from phieuxuatkho_donhang where pxk_fk = '" + pxkId + "')  " +
					" group by b.kho_fk, e.kbh_fk, a.ctkmId, d.pk_seq, khoNVBH, ( case when khoNVBH = 0 then NULL else e.ddkd_fk end )  ";
			//System.out.println("---5.1.CHEN SPKM: " + query);
			rsKHO = db.get(query);
			if(rsKHO != null)
			{
				while(rsKHO.next())
				{
					String nvbhId = rsKHO.getString("DDKD_FK");
					String khoId = rsKHO.getString("khoId");
					String kbhId = rsKHO.getString("kbhId");
					String spId = rsKHO.getString("spId");
					String ctkmId = rsKHO.getString("ctkmId");
					String khoNVBH = rsKHO.getString("khoNVBH");
					double soluong = rsKHO.getDouble("soluong");
					
					query = "update nhapp_kho set BOOKED = BOOKED - '" + soluong + "', SOLUONG = SOLUONG - '" + soluong + "' " +
							" where sanpham_fk = '" + spId + "' and npp_fk = '" + nppId + "' and kho_fk = '" + khoId + "' and kbh_fk = '" + kbhId + "' ";
					
					//System.out.println("---UPDATE KHO TONG: " + query);
					
					if(db.updateReturnInt(query)!=1)
					{
						db.getConnection().rollback();
						return "5.Lỗi khi chốt xuất kho: " + query;
					}
					
					
					//TU DE XUAT LO
					if(khoNVBH.equals("0"))
					{
						query = "select AVAILABLE, SOLO, NGAYHETHAN  " +
								"from NHAPP_KHO_CHITIET " +
								"where AVAILABLE > 0 and NPP_FK = '" + nppId + "' and KBH_FK = '" + kbhId + "' and KHO_FK = '" + khoId + "' and SANPHAM_FK = '" + spId + "' " +
								"order by NGAYHETHAN asc, AVAILABLE asc";
					}
					else
					{
						query = "select AVAILABLE, SOLO, NGAYHETHAN  " +
								"from NVBH_KHO_CHITIET " +
								"where AVAILABLE > 0 and NVBH_FK = '" + nvbhId + "' and NPP_FK = '" + nppId + "' and KBH_FK = '" + kbhId + "' and KHO_FK = '" + khoId + "' and SANPHAM_FK = '" + spId + "' " +
								"order by NGAYHETHAN asc, AVAILABLE asc";
					}
					
					//System.out.println("--TU DE XUAT: " + query);
					ResultSet rs = db.get(query);
					double tongluongxuatCT = 0;  //PHAI BAT BUOC TONG LUONG XUAT O KHO CHI TIET PHAI BANG TONG LUONG XUAT O KHO TONG
					if(rs != null)
					{
						double totalLUONG = 0;
						boolean exit = false;
						while(rs.next() && !exit)
						{
							String NgayHetHan= rs.getString("NGAYHETHAN");
							
							totalLUONG += rs.getDouble("AVAILABLE");
							double soluongXUAT = 0;
							
							if(totalLUONG <= soluong)
							{
								soluongXUAT = rs.getDouble("AVAILABLE");
							}
							else
							{
								soluongXUAT = soluong - ( totalLUONG - rs.getDouble("AVAILABLE") );
								exit = true;
							}
							
							//CAP NHAT KHO CHI TIET
							if(khoNVBH.equals("0"))
							{
								query = "update nhapp_kho_chitiet set AVAILABLE = AVAILABLE - '" + soluongXUAT + "', SOLUONG = SOLUONG - '" + soluongXUAT + "' " +
										" where sanpham_fk = '" + spId + "' and npp_fk = '" + nppId + "' and kho_fk = '" + khoId + "' and kbh_fk = '" + kbhId + "' and SOLO = '" + rs.getString("SOLO") + "' and NgayHetHan='"+NgayHetHan+"' ";
							}
							else
							{
								query = "update NVBH_KHO_CHITIET set BOOKED = BOOKED - '" + soluongXUAT + "', SOLUONG = SOLUONG - '" + soluongXUAT + "' " +
										" where nvbh_fk = '" + nvbhId + "' and sanpham_fk = '" + spId + "' and npp_fk = '" + nppId + "' and kho_fk = '" + khoId + "' and kbh_fk = '" + kbhId + "' and SOLO = '" + rs.getString("SOLO") + "' ";
							}
							
							//System.out.println("---UPDATE KHO CHI TIET: " + query);
							if(db.updateReturnInt(query)!=1)
							{
								db.getConnection().rollback();
								return "6.Lỗi khi chốt xuất kho: " + query;
							}
							
							//INSERT PXK - CHI TIET
							query = "insert into PHIEUXUATKHO_SPKM_CHITIET (PXK_FK, KBH_FK, KHO_FK, SANPHAM_FK, SCHEME, SOLUONG, SOLO, NGAYHETHAN, khoNVBH, NVBH_FK) " +
									"values( '" + pxkId + "', '" + kbhId + "', '" + khoId + "', '" + spId + "', '" + ctkmId + "', '" + soluongXUAT +  "', '" + rs.getString("SOLO") + "', '" + rs.getString("NGAYHETHAN") + "', '" + khoNVBH + "', " + ( khoNVBH.equals("1") ? nvbhId : "NULL" ) + " )";
							//System.out.println("---CHEN PXK-SPKM CHI TIET: " + query);
							if(!db.update(query))
							{
								db.getConnection().rollback();
								return "7.Lỗi khi chốt xuất kho: " + query;
							}
							
							tongluongxuatCT += soluongXUAT;
							if(exit)  //DA XUAT DU
							{
								//rs.close();
								break;
							}
						}
						rs.close();
					}
					
					if(tongluongxuatCT != soluong)
					{
						db.getConnection().rollback();
						rsKHO.close();
						return "2.Lỗi hệ thống ( tổng xuất theo lô đề xuất đang bị sai ). Vui lòng liên hệ trung tâm để được hỗ trợ xử lý.";
					}
					
				}
				rsKHO.close();
			}
			
			
			//CHECK TONG KHO CHI TIET PHAI BANG TONG TRONG KHO TONG
			query = "select count(*) as soDONG  " +
					"from PHIEUXUATKHO_SPKM tong left join  " +
					"	( " +
					"		select sanpham_fk, kbh_fk, kho_fk, sum(soluong) as soluong  " +
					"		from PHIEUXUATKHO_SPKM_CHITIET " +
					"		where  PXK_FK = '" + pxkId + "' " +
					"		group by sanpham_fk, kbh_fk, kho_fk " +
					"	) " +
					"	CT on tong.sanpham_fk = CT.sanpham_fk and tong.kbh_fk = CT.kbh_fk and tong.kho_fk = CT.kho_fk " +
					"where PXK_FK = '" + pxkId + "' and tong.soluong != isnull(CT.soluong, 0) ";
			rsCHECK = db.get(query);
			soDONG = 0;
			if(rsCHECK != null )
			{
				if( rsCHECK.next() )
				{
					soDONG = rsCHECK.getInt("soDONG");
				}
				rsCHECK.close();
			}
			
			if(soDONG > 0)
			{
				db.getConnection().rollback();
				return "22.Lỗi hệ thống ( tổng xuất theo lô đề xuất đang bị sai ). Vui lòng liên hệ trung tâm để được hỗ trợ xử lý.";
			}
			
			
			query = "delete phieuxuatkho_tienkm where pxk_fk = '" + pxkId + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "5.Lỗi khi chốt xuất kho: " + query;
			}
			
			query = " Insert into phieuxuatkho_tienkm(pxk_fk, scheme, tonggiatri) " +
					" select '" + pxkId + "', ctkmID, sum(a.tonggiatri) as tonggiatri  " +
					" from donhang_ctkm_trakm a inner join donhang b on a.donhangId = b.pk_seq  " +
					" where b.trangthai != '2' and a.spMa is null and a.donhangId in (select donhang_fk from phieuxuatkho_donhang where pxk_fk = '" + pxkId + "')  " +
					" group by ctkmID " ;
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "5.Lỗi khi chốt xuất kho: " + query;
			}
			
			query = "update phieuxuatkho set trangthai = '1' where pk_seq = '" + pxkId + "' ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "8.Lỗi khi chốt xuất kho: " + query;
			}
			
			query = "update donhang set trangthai = '1' where pk_seq in ( select donhang_fk from phieuxuatkho_donhang where pxk_fk = '" + pxkId + "' ) ";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "9.Lỗi khi chốt xuất kho: " + query;
			}
			
			db.getConnection().commit();
		} 
		catch (Exception e) {
			
			try 
			{
				db.getConnection().rollback();
			} 
			catch (Exception e1) {}
			
			return "Lỗi khi chốt PXK: " + e.getMessage();
		}
		
		return "";
	}
	
}
