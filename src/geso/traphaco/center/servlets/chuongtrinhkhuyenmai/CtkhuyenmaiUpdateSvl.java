package geso.traphaco.center.servlets.chuongtrinhkhuyenmai;

import geso.traphaco.center.beans.ctkhuyenmai.*;
import geso.traphaco.center.beans.ctkhuyenmai.imp.*;
import geso.traphaco.center.beans.dieukienkhuyenmai.ISanpham;
import geso.traphaco.center.beans.dieukienkhuyenmai.imp.Sanpham;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CtkhuyenmaiUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out; 

    public CtkhuyenmaiUpdateSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		ICtkhuyenmai ctkmBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);
	    
	    //System.out.println("id :" + id);

	    ctkmBean = new Ctkhuyenmai(id);
	    ctkmBean.setId(id);
	    ctkmBean.setUserId(userId);
	    
        ctkmBean.init();
        
        session.setAttribute("ctkmBean", ctkmBean);
        
        session.setAttribute("type", ctkmBean.getType());
    	
        String nextJSP = "/TraphacoHYERP/pages/Center/ChuongTrinhKhuyenMaiUpdate.jsp";
        String copy = request.getParameter("copy");
        System.out.println("---COPY LA: " + copy );
		if(copy != null)
		{			
			nextJSP = "/TraphacoHYERP/pages/Center/ChuongTrinhKhuyenMaiNew.jsp";
		}
		String display = request.getParameter("display");
		if(display != null)
		{			
			nextJSP = "/TraphacoHYERP/pages/Center/ChuongTrinhKhuyenMaiDisplay.jsp";
		}
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		ICtkhuyenmai ctkmBean;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
	    String id = util.antiSQLInspection(request.getParameter("id"));
	    
	    if(id == null){  	
	    	ctkmBean = new Ctkhuyenmai("");
	    }else{
	    	ctkmBean = new Ctkhuyenmai(id);
	    }
	    	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		ctkmBean.setUserId(userId);	       
    			
		String scheme = util.antiSQLInspection(request.getParameter("scheme"));
		if (scheme == null)
			scheme = "";
		ctkmBean.setScheme(scheme);
		
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		ctkmBean.setDiengiai(diengiai);
		
		String tungay = util.antiSQLInspection(request.getParameter("tungay"));
		if (tungay == null)
			tungay = "";
		ctkmBean.setTungay(tungay);
		
		String denngay = util.antiSQLInspection(request.getParameter("denngay"));
		if (denngay == null)
			denngay = "";
		ctkmBean.setDenngay(denngay);
		
		String type = util.antiSQLInspection(request.getParameter("loaiCt"));
		if (type == null)
			type = "";
		ctkmBean.setType(type);
		session.setAttribute("type", type);
		
		String loains = util.antiSQLInspection(request.getParameter("loains"));
		if (loains == null)
			loains = "1";
		ctkmBean.setLoaiNganSach(loains);
		
		String ngansach = util.antiSQLInspection(request.getParameter("ngansach"));
		if (ngansach == null)
			ngansach = "";
		ctkmBean.setNgansach(ngansach);
				
		String dasudung =  util.antiSQLInspection(request.getParameter("dasudung"));
		if (dasudung == null)
			dasudung = "";
		ctkmBean.setDasudung(dasudung);
		
		String active =  util.antiSQLInspection(request.getParameter("active"));
		if (active == null)
			active = "";
		ctkmBean.setActive(active);
		
		String khoId = util.antiSQLInspection(request.getParameter("kho"));
		if(khoId == null)
			khoId = "";
		ctkmBean.setkhoId(khoId);
		
		
		String _soXuatToiDa = util.antiSQLInspection(request.getParameter("SoXuatToiDa_in"));
		if(_soXuatToiDa == null)
		{
			_soXuatToiDa = "0";
		}
		ctkmBean.setSoXuatToiDa(_soXuatToiDa);
		
		String nhomkhnpp= util.antiSQLInspection(request.getParameter("nhomkhnpp"));
		if(nhomkhnpp ==null)
			nhomkhnpp ="";
		ctkmBean.setNhomkhnppId(nhomkhnpp);
		
		String[] vungId = request.getParameterValues("vung");
		String str = "";
		if(vungId != null)
		{
			for(int i = 0; i < vungId.length; i++)
				str += vungId[i] + ",";
			if(str.length() > 0)
				str = str.substring(0, str.length() - 1);
		}
		ctkmBean.setVungId(str);
	
		String[] khuvucId = request.getParameterValues("khuvuc");
		String str2 = "";
		if(khuvucId != null)
		{
			for(int i = 0; i < khuvucId.length; i++)
				str2 += khuvucId[i] + ",";
			if(str2.length() > 0)
				str2 = str2.substring(0, str2.length() - 1);
		}
		ctkmBean.setKhuvucId(str2);
		
		String[] kbhId = request.getParameterValues("kbhId");
		String str3 = "";
		if(kbhId != null)
		{
			for(int i = 0; i < kbhId.length; i++)
				str3 += kbhId[i] + ",";
			if(str3.length() > 0)
				str3 = str3.substring(0, str3.length() - 1);
		}
		ctkmBean.setKbhIds(str3);
		
		String[] loaikhId = request.getParameterValues("loaikhId");
		String str4 = "";
		if(loaikhId != null)
		{
			for(int i = 0; i < loaikhId.length; i++)
				str4 += loaikhId[i] + ",";
			if(str4.length() > 0)
				str4 = str4.substring(0, str4.length() - 1);
		}
		System.out.println("---LOAI KHACH HANG: " + str4);
		ctkmBean.setLoaikhIds(str4);
		
		
		
		String[] _hangCuaHangs = request.getParameterValues("_hangCuaHangs");
		String _hCHString = "";
		if(_hangCuaHangs != null)
		{			
			for(int i = 0; i < _hangCuaHangs.length; i++)
			{
				if(!_hangCuaHangs[i].equals("0"))			
				{
					_hCHString+= _hangCuaHangs[i]+ ",";
				}
					
			}
			if(_hCHString.length()>0)
				_hCHString = _hCHString.substring(0, _hCHString.length()-1);
		}
		System.out.println("---HANG CUA HANG: " + _hCHString);
		ctkmBean.setHangCuaHangIds(_hCHString);
		
		
		String[] phamvi = request.getParameterValues("phamvi");
		String str5 = "";
		if(phamvi != null)
		{
			for(int i = 0; i < phamvi.length; i++)
				str5 += phamvi[i] + ",";
			if(str5.length() > 0)
				str5 = str5.substring(0, str5.length() - 1);
		}
		ctkmBean.setPhamvi(str5);
		
		String[] loaiapdung = request.getParameterValues("loaiapdung");
		String str6 = "";
		if(loaiapdung != null)
		{
			for(int i = 0; i < loaiapdung.length; i++)
				str6 += loaiapdung[i] + ",";
			if(str6.length() > 0)
				str6 = str6.substring(0, str6.length() - 1);
		}
		ctkmBean.setLoaiapdung(str6);
		
		String ngayds = util.antiSQLInspection(request.getParameter("ngayds"));
		if(ngayds == null)
			ngayds = "";
		ctkmBean.setngayds(ngayds);
		
		String ngayktds = util.antiSQLInspection(request.getParameter("ngayktds"));
		if(ngayktds == null)
			ngayktds = "";
		ctkmBean.setngayktds(ngayktds);
		
		String loaikhuyenmai = util.antiSQLInspection(request.getParameter("loaikhuyenmai"));
		if(loaikhuyenmai == null)
			loaikhuyenmai = "0";
		ctkmBean.setLoaikhuyenmai(loaikhuyenmai);
		
		String tilevoiprimary = util.antiSQLInspection(request.getParameter("tilevoiprimary"));
		if(tilevoiprimary == null)
			tilevoiprimary = "0";
		ctkmBean.setTylevoiPrimary(tilevoiprimary);
		
		String ApDUNGCHODHLE = util.antiSQLInspection(request.getParameter("ApDUNGCHODHLE"));
		System.out.println("___Ap dung cho don hang le: " + ApDUNGCHODHLE);
		
		if(ApDUNGCHODHLE == null)
			ApDUNGCHODHLE = "0";
		ctkmBean.setApdungchoDHLe(ApDUNGCHODHLE);
		
		String PHANBOTHEODH = util.antiSQLInspection(request.getParameter("PHANBOTHEODH"));
		if(PHANBOTHEODH == null)
		{
			System.out.println("PHANBOTHEODH bang null");
			PHANBOTHEODH = "0";
		}
		ctkmBean.setPhanbotheoDH(PHANBOTHEODH);
		
		
		String trakmIds = util.antiSQLInspection(request.getParameter("trakmIds"));
		if (trakmIds == null)
			trakmIds = "";
		ctkmBean.setTrakmId(trakmIds);
		
		String load = util.antiSQLInspection(request.getParameter("load"));
		ctkmBean.setload(load);
		String ngaysua = getDateTime();
    	ctkmBean.setNgaysua(ngaysua);
    	
    	String cttbId = util.antiSQLInspection(request.getParameter("cttbId"));
		if(cttbId == null)
			cttbId = "";
		ctkmBean.setCttbId(cttbId); 
    	
		String ctkmId = util.antiSQLInspection(request.getParameter("ctkmId"));
		if(ctkmId == null)
			ctkmId = "";
		ctkmBean.setCtkmId(ctkmId);
		
		String xuatHDCoVAT = util.antiSQLInspection(request.getParameter("xuatHDCoVAT"));
		if(xuatHDCoVAT == null)
			xuatHDCoVAT = "";
		ctkmBean.setXuatHDCoVAT(xuatHDCoVAT);
		
		String inchung = util.antiSQLInspection(request.getParameter("inchung"));
		if(inchung == null)
			inchung = "0";
		ctkmBean.setInchung(inchung);
		
		
		String codangki = util.antiSQLInspection(request.getParameter("codangki"));
		if(codangki == null)
			codangki = "0";
		ctkmBean.setCodangki(codangki);
		
		String codieukien = util.antiSQLInspection(request.getParameter("codieukien"));
		if(codieukien == null)
			codieukien = "0";
		ctkmBean.setCodieukien(codieukien);
		
		System.out.println("in chung -----------"+ctkmBean.getInchung());
		
    	String[] dkkmId = request.getParameterValues("dkkmId");
		String[] dkkmDiengiai = request.getParameterValues("dkkmDiengiai");
		String[] dkkmTongluong = request.getParameterValues("dkkmTongluong");
		String[] dkkmTongtien = request.getParameterValues("dkkmTongtien");
		String[] dkkmPheptoan = request.getParameterValues("dkkmPheptoan");
		String[] dkkmThutu = request.getParameterValues("dkkmThutu");
		
		List<IDieukienkm> dkkmlist = new ArrayList<IDieukienkm>();	
		for(int i = 0; i < dkkmId.length; i++)
		{
			if(dkkmPheptoan[i] == "")
				dkkmPheptoan[i] = "2";  //or
			System.out.println("Phep toan cua DKKM: " + dkkmPheptoan[i]+"[dkkmId]"+dkkmId[i]);
			if(dkkmId[i].trim().length() > 0 && dkkmDiengiai[i].trim().length() > 0 )
			{				
				Dieukienkm dkkm = new Dieukienkm(dkkmId[i], dkkmDiengiai[i], dkkmTongluong[i], dkkmTongtien[i], dkkmPheptoan[i], dkkmThutu[i]);
				dkkmlist.add(dkkm);
			}
			else
			{
				String diengiaiD = request.getParameter("dieukienkhuyenmai" + Integer.toString(i) + ".diengiai");
				if(diengiaiD == null)
					diengiaiD = "";
				
				String loaidieukienD = request.getParameter("dieukienkhuyenmai" + Integer.toString(i) + ".loaidieukien");
				if(loaidieukienD == null)
					loaidieukienD = "";
				
				String hinhthucD = request.getParameter("dieukienkhuyenmai" + Integer.toString(i) + ".hinhthuc");
				if(hinhthucD == null)
					hinhthucD = "";
				
				String sotongD = request.getParameter("dieukienkhuyenmai" + Integer.toString(i) + ".sotong");
				if(sotongD == null)
					sotongD = "";
				
				String nhomsanphamD = request.getParameter("dieukienkhuyenmai" + Integer.toString(i) + ".nhomsanpham");
				if(nhomsanphamD == null)
					nhomsanphamD = "";
				
				if(diengiaiD.trim().length() > 0)
				{
					Dieukienkm dkkm = new Dieukienkm(dkkmId[i], dkkmDiengiai[i], dkkmTongluong[i], dkkmTongtien[i], dkkmPheptoan[i], dkkmThutu[i]);
					
					IDieukienDetail dkDetail = new DieukienDetail(diengiaiD, loaidieukienD, hinhthucD, sotongD, nhomsanphamD);
					
					String[] masanpham = request.getParameterValues("dieukienkhuyenmai" + Integer.toString(i) + ".masanpham");
					String[] tensanpham = request.getParameterValues("dieukienkhuyenmai" + Integer.toString(i) + ".tensanpham");
					String[] soluong = request.getParameterValues("dieukienkhuyenmai" + Integer.toString(i) + ".soluong");
					
					List<ISanpham> spList = new ArrayList<ISanpham>();
					if(masanpham != null)
					{
						ISanpham spDetai = null;
						for(int j = 0; j < masanpham.length; j++)
						{
							if(masanpham[j].length() > 0)
							{
								spDetai = new Sanpham();
								spDetai.setMasanpham(masanpham[j]);
								spDetai.setTensanpham(tensanpham[j]);
								spDetai.setSoluong(soluong[j]);
								
								spList.add(spDetai);
								
								dkDetail.setSpList(spList);
							}
						}
					}
					
					System.out.println("1.So san pham tao moi: " + dkDetail.getSpList().size());
					dkkm.setDieukineDetail(dkDetail);
					
					dkkmlist.add(dkkm);
				}
			}
		}
		
		System.out.println("2.So dieu kien khuyen mai: " + dkkmlist.size());
		ctkmBean.setDkkmList(dkkmlist);
		
		
		//Tra khuyen mai
		String[] trakmId = request.getParameterValues("trakmId");
		String[] trakmDiengiai = request.getParameterValues("trakmDiengiai");
		String[] trakmTongluong = request.getParameterValues("trakmTongluong");
		String[] trakmTongtien = request.getParameterValues("trakmTongtien");
		String[] trakmChietkhau = request.getParameterValues("trakmChietkhau");
		String[] trakmPheptoan = request.getParameterValues("trakmPheptoan");
		String[] trakmThutu = request.getParameterValues("trakmThutu");
		
		List<ITrakm> trakmlist = new ArrayList<ITrakm>();	
		for(int i = 0; i < dkkmId.length; i++)
		{
			if(trakmPheptoan[i] == "")
				trakmPheptoan[i] = "1";  //and
			
			if(trakmId[i].trim().length() > 0 && trakmDiengiai[i].trim().length() > 0 )
			{				
				Trakm tkm = new Trakm(trakmId[i], trakmDiengiai[i], trakmTongluong[i], trakmTongtien[i], trakmChietkhau[i], trakmPheptoan[i], trakmThutu[i]);
				trakmlist.add(tkm);
			}
			else
			{
				String diengiaiD = request.getParameter("trakhuyenmai" + Integer.toString(i) + ".diengiai");
				if(diengiaiD == null)
					diengiaiD = "";
				
				String loaidieukienD = request.getParameter("trakhuyenmai" + Integer.toString(i) + ".loaidieukien");
				if(loaidieukienD == null)
					loaidieukienD = "";
				
				String hinhthucD = request.getParameter("trakhuyenmai" + Integer.toString(i) + ".hinhthuc");
				if(hinhthucD == null)
					hinhthucD = "";
				
				String sotongD = request.getParameter("trakhuyenmai" + Integer.toString(i) + ".sotong");
				if(sotongD == null)
					sotongD = "";
				
				String nhomsanphamD = request.getParameter("trakhuyenmai" + Integer.toString(i) + ".nhomsanpham");
				if(nhomsanphamD == null)
					nhomsanphamD = "";
				
				if(diengiaiD.trim().length() > 0)
				{
					Trakm tkm = new Trakm(trakmId[i], trakmDiengiai[i], trakmTongluong[i], trakmTongtien[i], trakmChietkhau[i], trakmPheptoan[i], trakmThutu[i]);
					System.out.println("Dien giai TKM: " + trakmDiengiai[i] + "\n");
					
					ITrakmDetail traDetail = new TrakmDetail(diengiaiD, loaidieukienD, hinhthucD, sotongD, nhomsanphamD);
					
					String[] masanpham = request.getParameterValues("trakhuyenmai" + Integer.toString(i) + ".masanpham");
					String[] tensanpham = request.getParameterValues("trakhuyenmai" + Integer.toString(i) + ".tensanpham");
					String[] soluong = request.getParameterValues("trakhuyenmai" + Integer.toString(i) + ".soluong");
					
					List<ISanpham> spList = new ArrayList<ISanpham>();
					if(masanpham != null)
					{
						ISanpham spDetai = null;
						for(int j = 0; j < masanpham.length; j++)
						{
							if(masanpham[j].length() > 0)
							{
								spDetai = new Sanpham();
								spDetai.setMasanpham(masanpham[j]);
								spDetai.setTensanpham(tensanpham[j]);
								spDetai.setSoluong(soluong[j]);
								
								spList.add(spDetai);
								
								traDetail.setSpList(spList);
							}
						}
					}
					
					System.out.println("22.So san pham tra khuyen mai tao moi: " + traDetail.getSpList().size());
					tkm.setTraDetail(traDetail);
					
					trakmlist.add(tkm);
				}
			}
		}
		
		System.out.println("2.So tra khuyen mai: " + trakmlist.size());
		ctkmBean.setTrakmList(trakmlist);
		
		
		String[] npp = request.getParameterValues("npp");
		ctkmBean.setNpp(npp);

 		String action = request.getParameter("action");
 		System.out.println("action:"+ action);
 		if(action.equals("save"))
 		{    
    		if (id == null )
    		{
    			if (!ctkmBean.CreateCtkm()){
    		    	ctkmBean.setUserId(userId);
    		    	ctkmBean.createRS();
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("ctkmBean", ctkmBean);
    				System.out.println(ctkmBean.getMessage());
    				
    				String nextJSP = "/TraphacoHYERP/pages/Center/ChuongTrinhKhuyenMaiNew.jsp";
    				response.sendRedirect(nextJSP);
    			}
    			else
    			{
    				ICtkhuyenmaiList obj = new CtkhuyenmaiList();
    				obj.setUserId(userId);
    				obj.init("");
    				session.setAttribute("obj", obj);
    					
    				response.sendRedirect("/TraphacoHYERP/pages/Center/ChuongTrinhKhuyenMai.jsp");	    	
    			 }	
    		}
    		else
    		{
    			if (!(ctkmBean.UpdateCtkm()))
    			{			
    		    	ctkmBean.setUserId(userId);
    		    	ctkmBean.createRS();
    		    	
    		    	session.setAttribute("userId", userId);
    				session.setAttribute("ctkmBean", ctkmBean);
    				
    				String nextJSP = "/TraphacoHYERP/pages/Center/ChuongTrinhKhuyenMaiUpdate.jsp";
    				response.sendRedirect(nextJSP);
    			}
			else
			{
				ICtkhuyenmaiList obj = new CtkhuyenmaiList();
				obj.setUserId(userId);
				obj.init("");
			
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
	    		
				response.sendRedirect("/TraphacoHYERP/pages/Center/ChuongTrinhKhuyenMai.jsp");
			}
    		}
	    }
		else
		{
			ctkmBean.createRS();		
			session.setAttribute("userId", userId);
			session.setAttribute("ctkmBean", ctkmBean);
			
			String nextJSP;
			if (id == null){
				nextJSP = "/TraphacoHYERP/pages/Center/ChuongTrinhKhuyenMaiNew.jsp";
			}
			else{
				nextJSP = "/TraphacoHYERP/pages/Center/ChuongTrinhKhuyenMaiUpdate.jsp";   						
			}
			response.sendRedirect(nextJSP);
		}
	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
