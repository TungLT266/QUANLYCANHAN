package geso.traphaco.center.servlets.thongtinsanpham;

import geso.traphaco.center.beans.thongtinsanpham.IThongtinsanpham;
import geso.traphaco.center.beans.thongtinsanpham.IThongtinsanphamList;
import geso.traphaco.center.beans.thongtinsanpham.imp.Thongtinsanpham;
import geso.traphaco.center.beans.thongtinsanpham.imp.ThongtinsanphamList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ThongtinsanphamUpdateSvl extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet
{
	static final long serialVersionUID = 1L;
	private PrintWriter out;
	private int items = 50;
	private int splittings = 10;

	public ThongtinsanphamUpdateSvl()
	{
		super();
	}

	private void settingPage(IThongtinsanphamList obj)
	{
		Utility util = new Utility();
		if (getServletContext().getInitParameter("items") != null)
		{
			String i = getServletContext().getInitParameter("items").trim();
			if (util.isNumeric(i))
				items = Integer.parseInt(i);
		}

		if (getServletContext().getInitParameter("splittings") != null)
		{
			String i = getServletContext().getInitParameter("splittings").trim();
			if (util.isNumeric(i))
				splittings = Integer.parseInt(i);
		}

		obj.setItems(items);
		obj.setSplittings(splittings);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();

		this.out = response.getWriter();
		Utility util = new Utility();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		out.println(userId);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		String id = util.getId(querystring);

		IThongtinsanpham spBean = new Thongtinsanpham();
		spBean.setId(id);
		spBean.setUserId(userId);
		if (querystring.equals("display"))
			spBean.init2();
		else
			spBean.init();
		session.setAttribute("spBean", spBean);
		String nextJSP = "/TraphacoHYERP/pages/Center/ThongTinSanPhamUpdate.jsp";
		if (querystring.indexOf("display") > 0)
			nextJSP = "/TraphacoHYERP/pages/Center/ThongTinSanPhamDisplay.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();

		// PrintWriter out = response.getWriter();
		Utility util = new Utility();
		String id = util.antiSQLInspection(request.getParameter("id"));
		dbutils db = new dbutils();
		IThongtinsanpham spBean = new Thongtinsanpham();
		if (id != null)
			spBean.setId(id);

		String userId = util.antiSQLInspection(request.getParameter("userId"));
		spBean.setUserId(userId);

		String maspcu = util.antiSQLInspection(request.getParameter("maspcu"));
		if (maspcu == null)
			maspcu = "";
		spBean.setMaspcu(maspcu);

		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null)
			trangthai = "0";
		else
			trangthai = "1";
		spBean.setTrangthai(trangthai);
		
		String trangthaiDL = util.antiSQLInspection(request.getParameter("trangthaiDL"));
		if (trangthaiDL == null)
			trangthaiDL = "0";
		else
			trangthaiDL = "1";
		spBean.setTrangthaiDaily(trangthaiDL);

		String tensp = util.antiSQLInspection(request.getParameter("tensp"));
		if (tensp == null)
			tensp = "";
		spBean.setTen(tensp);

		String hamluong = util.antiSQLInspection(request.getParameter("hamluong"));
		if (hamluong == null)
			hamluong = "";
		spBean.setHamluong(hamluong);
		
		String giakk = util.antiSQLInspection(request.getParameter("giakk"));
		if (giakk == null)
			giakk = "";
		spBean.setGiakktheodvt(giakk);
		
		String stttheott40 = util.antiSQLInspection(request.getParameter("stttheott40"));
		if (stttheott40 == null)
			stttheott40 = "";
		spBean.setStttheoTT40(stttheott40);
		
		String nhomsphtotc = util.antiSQLInspection(request.getParameter("nhomsphtotc"));
		if (nhomsphtotc == null)
			nhomsphtotc = "";
		spBean.setNhomsanphamHtotc(nhomsphtotc);
		
		String nguongoc = util.antiSQLInspection(request.getParameter("nguongoc"));
		if (nguongoc == null)
			nguongoc = "";
		spBean.setNguongoc(nguongoc);

		
		
		String hethanvisa = util.antiSQLInspection(request.getParameter("hethanvisa"));
		if (hethanvisa == null)
			hethanvisa = "";
		spBean.setHethanvisa(hethanvisa);
		
		String tenviettat = util.antiSQLInspection(request.getParameter("tenviettat"));
		System.out.println("Ten viet tat "+tenviettat);
		if (tenviettat == null)
			tenviettat = "";
		spBean.setTenviettat(tenviettat);
		
		String[] tenxuathoadonArr = request.getParameterValues("tenxuathoadon");
		spBean.setTenxuathoadonArr(tenxuathoadonArr);
		
		String[] nguongocnhanhapkhauArr = request.getParameterValues("nguongocnhanhapkhau");
		spBean.setNguongocnhapkhauArr(nguongocnhanhapkhauArr);
		String[] ngayArr = request.getParameterValues("ngay");
		spBean.setNgayArr(ngayArr);
		String[] thongtinArr = request.getParameterValues("thongtin");
		spBean.setThongtinArr(thongtinArr);
		String[] ghichuArr= request.getParameterValues("ghichu");
		spBean.setGhichuArr(ghichuArr);
		
		String loaispId = util.antiSQLInspection(request.getParameter("loaispId"));
		if (loaispId == null)
			loaispId = "";
		spBean.setLoaisp(loaispId);
		String visa = util.antiSQLInspection(request.getParameter("visa"));
		if (visa == null)
			visa = "";
		spBean.setVisa(visa);
		String ngaycap = util.antiSQLInspection(request.getParameter("ngaycap"));
		if (ngaycap == null)
			ngaycap = "";
		spBean.setNgaycap(ngaycap);
		String kkg = util.antiSQLInspection(request.getParameter("kkg"));
		if (kkg == null)
			kkg = "";
		spBean.setKkg(kkg);
		String nsx = util.antiSQLInspection(request.getParameter("nsx"));
		if (nsx == null)
			nsx = "";
		spBean.setNsx(nsx);
		String nkk = util.antiSQLInspection(request.getParameter("nkk"));
		if (nkk == null)
			nkk = "";
		spBean.setNkk(nkk);
		String nxb = util.antiSQLInspection(request.getParameter("nxb"));
		if (nxb == null)
			nxb = "";
		spBean.setNxb(nxb);
		
		String dvdlId = util.antiSQLInspection(request.getParameter("dvdlId"));
		if (dvdlId == null)
			dvdlId = "";
		spBean.setDvdlId(dvdlId);

		String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
		if (dvkdId == null)
			dvkdId = "";
		spBean.setDvkdId(dvkdId);
		// System.out.print(dvkdId +"dvkdId;");

		String nhId = util.antiSQLInspection(request.getParameter("nhId"));
		if (nhId == null)
			nhId = "";
		spBean.setNhId(nhId);

		String clId = util.antiSQLInspection(request.getParameter("clId"));
		if (clId == null)
			clId = "";
		spBean.setClId(clId);
		
		String spchuluc = util.antiSQLInspection(request.getParameter("spchuluc"));
		if (spchuluc == null)
			spchuluc = "0";
		else 
			spchuluc="1";
		spBean.setSpChuLuc(spchuluc);
		
		String spmoi = util.antiSQLInspection(request.getParameter("spmoi"));
		if (spmoi == null)
			spmoi = "";
		else 
			spmoi="1";
		spBean.setSpMoi(spmoi);

		String giablc = util.antiSQLInspection(request.getParameter("giablc").replaceAll(",", ""));
		if (giablc == null)
			giablc = "";
		spBean.setGiablc(giablc);

		String kl = util.antiSQLInspection(request.getParameter("kl"));
		if (kl == null)
			kl = "";
		spBean.setKL(kl);

		String packsizeid = util.antiSQLInspection(request.getParameter("packsizeid"));
		if (packsizeid == null)
			packsizeid = "";
		spBean.setPacksizeId(packsizeid);

		String machuan = util.antiSQLInspection(request.getParameter("machuan"));
		if (machuan == null)
			machuan = "";
		spBean.setMachuan(machuan);

		String tenchuan = util.antiSQLInspection(request.getParameter("tenchuan"));
		if (tenchuan == null)
			tenchuan = "";
		spBean.setTenchuan(tenchuan);

		String nganhhangid = util.antiSQLInspection(request.getParameter("nganhhangid"));
		if (nganhhangid == null)
			nganhhangid = "";
		spBean.setNganhhangid(nganhhangid);
		String thuexuat = util.antiSQLInspection(request.getParameter("thuexuat"));
		if (thuexuat == null)
			thuexuat = "";
		System.out.println("thue xuat "+thuexuat);
		if(nganhhangid.length() > 0 && thuexuat.length() <= 0)
		{
			String query = "select isnull(thuexuat,'0') as thue from nganhhang where  pk_seq ="+nganhhangid;
			ResultSet rs = db.get(query);
			try 
			{
				rs.next();
				thuexuat = rs.getString("thue");
			}
			catch (SQLException e)
			{
				
				e.printStackTrace();
			}
		}
		spBean.setThuetxuat(thuexuat);
		String tenhoatchat = util.antiSQLInspection(request.getParameter("tenhoatchat"));
		if (tenhoatchat == null)
			tenhoatchat = "";
		spBean.setTenhoatchat(tenhoatchat);
		
		String dangtrinhbay = util.antiSQLInspection(request.getParameter("dangtrinhbay"));
		if (dangtrinhbay == null)
			dangtrinhbay = "";
		spBean.setDangtrinhbay(dangtrinhbay);
		
		String quycachdonggoi = util.antiSQLInspection(request.getParameter("quycachdonggoi"));
		if (quycachdonggoi == null)
			quycachdonggoi = "";
		spBean.setQuycachdonggoi(quycachdonggoi);
		
		String tieuchuanchatluong = util.antiSQLInspection(request.getParameter("tieuchuanchatluong"));
		if (tieuchuanchatluong == null)
			tieuchuanchatluong = "";
		spBean.setTieuchuanchatluong(tieuchuanchatluong);
		
		String nguongocnguyenlieu = util.antiSQLInspection(request.getParameter("nguongocnguyenlieu"));
		if (nguongocnguyenlieu == null)
			nguongocnguyenlieu = "";
		spBean.setNguongocnguyenlieu(nguongocnguyenlieu);
		
		String tieuchuannsx = util.antiSQLInspection(request.getParameter("tieuchuannsx"));
		if (tieuchuannsx == null)
			tieuchuannsx = "";
		spBean.setTieuchuannsx(tieuchuannsx);
		
		String nuocsx = util.antiSQLInspection(request.getParameter("nuocsx"));
		if (nuocsx == null)
			nuocsx = "";
		spBean.setNuocsx(nuocsx);
		

		String giabancothue = util.antiSQLInspection(request.getParameter("giabancothue"));
		if (giabancothue == null)
			giabancothue = "";
		spBean.setGiabancothue(giabancothue);
		
		long quydoithuong = 1;
		try
		{
			quydoithuong = Math.round(Double.parseDouble(util.antiSQLInspection(request.getParameter("quydoithuong"))));
		} 
		catch (Exception er) { }

		String hansudung = util.antiSQLInspection(request.getParameter("hansudung"));
		if (hansudung == null)
			hansudung = "";
		spBean.setHansudung(hansudung);

		spBean.setquydoithuong(quydoithuong + "");

		String tt = util.antiSQLInspection(request.getParameter("tt"));
		if (tt == null)
			tt = "";
		spBean.setTT(tt);
		
		String dvdlETCId = util.antiSQLInspection(request.getParameter("dvdlETCId"));
		if (dvdlETCId == null)
			dvdlETCId = "";
		spBean.setDvdlETCId(dvdlETCId);

		String ngaysua = getDateTime();
		spBean.setNgaysua(ngaysua);

		String nguoisua = userId;
		spBean.setNguoisua(nguoisua);

		String[] nspIds = request.getParameterValues("nspIds");
		if (!(nspIds == null))
			spBean.setNspIds(nspIds);

		String[] sl1 = request.getParameterValues("sl1");

		String[] dvdl1 = request.getParameterValues("dvdl1");

		String[] sl2 = request.getParameterValues("sl2");

		String[] dvdl2 = request.getParameterValues("dvdl2");
		
	


		
		if (!(sl1 == null))
			spBean.setSl1(sl1);

		if (!(dvdl1 == null))
			spBean.setDvdl1(dvdl1);

		if (!(sl2 == null))
			spBean.setSl2(sl2);

		if (!(dvdl2 == null))
			spBean.setDvdl2(dvdl2);

		String type = util.antiSQLInspection(request.getParameter("type"));
		if (type == null)
			type = "0";
		else
			type = "1";
		spBean.setType(type);

		String[] spIds = request.getParameterValues("spIds");
		if (spIds != null)
		{
			for(int i=0;i<spIds.length;i++)
			{
				String values=spIds[i];
				String soluong=request.getParameter("spSoluong_"+spIds[i]);
				if(soluong != "")
  					values += "-" + soluong;
				spIds[i]=values;
			}
			spBean.setSpIds(spIds);
			
			System.out.println("So san pham la: " + spIds.length + "\n");
		}
		String nhanhangId = "";
		String[] nhanhangIds = request.getParameterValues("nhanhangIds");

		if (nhanhangIds != null)
		{
			for (int i = 0; i < nhanhangIds.length; i++)
			{
				nhanhangId += nhanhangIds[i] + ",";
			}
			if (nhanhangId.length() > 0)
				nhanhangId = nhanhangId.substring(0, nhanhangId.length() - 1);
		}
		spBean.setNhanhangIds(nhanhangId);

		String chungloaiId = "";
		String[] chungloaiIds = request.getParameterValues("chungloaiIds");
		if (chungloaiIds != null)
		{
			for (int i = 0; i < chungloaiIds.length; i++)
			{
				chungloaiId += chungloaiIds[i] + ",";
			}
			if (chungloaiId.length() > 0)
				chungloaiId = chungloaiId.substring(0, chungloaiId.length() - 1);
		}
		spBean.setChungloaiIds(chungloaiId);

		String nhomhangId = "";
		String[] nhomhangIds = request.getParameterValues("nhomhangId");

		if (nhomhangIds != null)
		{
			for (int i = 0; i < nhomhangIds.length; i++)
			{
				nhomhangId += nhomhangIds[i] + ",";
			}
			System.out.println("_________"+nhomhangId);
			
			if (nhomhangId.length() > 0)
				nhomhangId = nhomhangId.substring(0, nhomhangId.length() - 1);
		}
		spBean.setNhomHangId(nhomhangId);
		
		boolean error = false;

		/*if (giablc.trim().length() == 0)
		{
			giablc = "0";
		}*/

/*		if (!type.equals("1"))
		{
			if (clId.trim().length() == 0)
			{
				spBean.setMessage("Vui lòng nhập chủng loại cho sản phẩm ");
				error = true;
			}
		}
*/
		/*if (nhId.trim().length() == 0)
		{
			spBean.setMessage("Vui lòng nhập nhãn hàng của sản phẩm ");
			error = true;
		}
*/
//		if (dvkdId.trim().length() == 0)
//		{
//			spBean.setMessage("Vui lòng nhập  đơn vị kinh doanh của sản phẩm");
//			error = true;
//		}
		if (sl1 == null || dvdl1 == null || sl2 == null || dvdl2 == null)
		{
				spBean.setMessage("Vui lòng nhập quy cách cho sản phẩm ");
				error = true;
	
		}
		else
		{
			for(int i = 0; i < sl1.length ; i++)
				if(sl1[i] == null  || sl2[i] == null || dvdl1[i] == null || dvdl2[i] == null)
					error = true;
			System.out.println("do dai sl1 "+sl1.length);
			System.out.println("do dai sl1 "+sl2.length);
			System.out.println("do dai sl1 "+dvdl1.length);
			System.out.println("do dai sl1 "+dvdl2.length);
		}
		if (dvdlId.trim().length() == 0)
		{
			spBean.setMessage("Vui lòng nhập vào đơn vị đo lường của sản phẩm");
			error = true;
		}

		if (tensp.trim().length() == 0)
		{
			spBean.setMessage("Vui lòng nhập tên của sản phẩm");
			error = true;
		}

//		if (masp.trim().length() == 0)
//		{
//			spBean.setMessage("Vui lòng nhập vào mã của sản phẩm");
//			error = true;
//		}
		String action = request.getParameter("action");
		if (error)
		{
			System.out.print("error = true");
		}
		spBean.CreateRS();
		spBean.setUserId(userId);
		session.setAttribute("userId", userId);
		session.setAttribute("spBean", spBean);

		String nextJSP;
		if (action.equals("save") && !error)
		{
			if (!(spBean.CreateSp()))
			{
				System.out.println("KET QUA TAO MOI SP LA FALSE... " + spBean.getMessage());
				nextJSP = "/TraphacoHYERP/pages/Center/ThongTinSanPhamNew.jsp";
				response.sendRedirect(nextJSP);
			} 
			else
			{
				IThongtinsanphamList obj = new ThongtinsanphamList();
				obj.setUserId(userId);
				settingPage(obj);
				obj.init();
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
				response.sendRedirect("/TraphacoHYERP/pages/Center/ThongTinSanPham.jsp");
			}
		} 
		else if (action.equals("update") && !error)
		{
			if (!(spBean.UpdateSp()))
			{
				nextJSP = "/TraphacoHYERP/pages/Center/ThongTinSanPhamUpdate.jsp";
				response.sendRedirect(nextJSP);
			} 
			else
			{
				IThongtinsanphamList obj = new ThongtinsanphamList();
				obj.setUserId(userId);
				settingPage(obj);
				obj.init();
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
				response.sendRedirect("/TraphacoHYERP/pages/Center/ThongTinSanPham.jsp");
			}
		} 
		else if (id == null)
		{
			spBean.CreateRS();
			session.setAttribute("obj", spBean);
			nextJSP = "/TraphacoHYERP/pages/Center/ThongTinSanPhamNew.jsp";
			response.sendRedirect(nextJSP);
		} 
		else
		{
			spBean.CreateRS();
			nextJSP = "/TraphacoHYERP/pages/Center/ThongTinSanPhamUpdate.jsp";
			response.sendRedirect(nextJSP);
		}
		
		System.out.println("action" + action);
	}
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

}