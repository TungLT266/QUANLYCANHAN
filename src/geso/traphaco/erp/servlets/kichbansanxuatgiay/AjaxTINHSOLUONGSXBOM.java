package geso.traphaco.erp.servlets.kichbansanxuatgiay;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.kichbansanxuatgiay.imp.ErpChiTietBom;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AjaxTINHSOLUONGSXBOM")
public class AjaxTINHSOLUONGSXBOM extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public AjaxTINHSOLUONGSXBOM()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Utility util = new Utility();
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    PrintWriter out = response.getWriter();	
		String type = request.getParameter("action") == null ? "" : request.getParameter("action");
		String spId = util.antiSQLInspection(request.getParameter("spId")==null?"":request.getParameter("spId"));
		String soluongchuan = util.antiSQLInspection(request.getParameter("soluongchuan")==null?"":request.getParameter("soluongchuan"));
		String param = util.antiSQLInspection(request.getParameter("param")==null?"":request.getParameter("param"));
		
		if(type.equals("tinhsoluong") && param.length()>0)
		{
			String query = "", kq = "";
			ResultSet rs;
			try
			{
				dbutils db = new dbutils();
				String[] param1 = param.split("--");
				String _thutu = "";
				String _bom = "";
				for(int i = param1.length - 1; i >= 0; i--)
				{
					if(param1[i].split("-")[0].trim().length() > 0)
					{
						_thutu += param1[i].split("-")[0] + "__";
						_bom += param1[i].split("-")[1] + "__";
					}
				}
				String[] thutu;
				String[] bom;
				_thutu = _thutu.substring(0, _thutu.length() - 2);
				thutu = _thutu.split("__");
				_bom = _bom.substring(0, _bom.length() - 2);
				bom = _bom.split("__");
				String _sanpham = "";
				//System.out.println("_thutu = " + _thutu + "; _bom = " + _bom);
				String sanpham = spId;
				double slchuan = Double.parseDouble(soluongchuan);
				List<ErpChiTietBom> list = new ArrayList<ErpChiTietBom>();
				for (int i = 0; i < thutu.length; i++) 
				{
					//kq += thutu[i] + "-" + sanpham + "-" + slchuan + "][";
					query = "select count(*) num from erp_danhmucvattu where pk_seq = " + bom[i] + " and sanpham_fk = " + sanpham;
					System.out.println("1." + i + " " + query);
					rs = db.get(query);
					rs.next();
					if(rs.getInt("num") > 0)
					{
						query = "select dbo.LayQuyCach_DVBan(b.pk_seq, b.dvdl_fk, a.DVDL_FK) quycach, a.soluongchuan \n" +
								"from erp_danhmucvattu a inner join erp_sanpham b on a.sanpham_fk = b.pk_seq \n" +
								"where a.pk_seq = " + bom[i];
						System.out.println("2." + i + " " + query);
						rs = db.get(query);
						rs.next();
						double quycach = rs.getDouble("quycach");
						double _soluongchuan = rs.getDouble("soluongchuan");
						rs.close();
						_sanpham += sanpham + "__";
						ErpChiTietBom chitiet = new ErpChiTietBom(thutu[i], sanpham, slchuan * quycach);
						list.add(chitiet);
						if(i + 1 < thutu.length)
						{
							double tyle = slchuan * quycach / _soluongchuan;	
							query = "select sanpham_fk from erp_danhmucvattu where pk_seq = " + bom[i + 1];
							System.out.println("3." + i + " " + query);
							rs = db.get(query);
							rs.next();
							String nguyenlieu = rs.getString("sanpham_fk");
							rs.close();
							query = "select vattu_fk, soluong from erp_danhmucvattu_vattu where danhmucvt_fk = " + bom[i];
							System.out.println("4." + i + " " + query);
							rs = db.get(query);
							int kt = 0;
							while(rs.next())
							{
								System.out.println("tyle = " + tyle + "; sl = " + rs.getDouble("soluong"));
								if(rs.getString("vattu_fk").trim().equals(nguyenlieu))
								{
									kt++;
									sanpham = nguyenlieu;
									slchuan = rs.getDouble("soluong") * tyle; 
								}
								else
								{
									ErpChiTietBom ct = new ErpChiTietBom(thutu[i], rs.getString("vattu_fk"), rs.getDouble("soluong") * tyle);
									list.add(ct);
								}
							}
							rs.close();
							if(kt == 0)
							{
								kq = "ERROR--Sản phẩm của BOM không phải nguyên liệu cần thiết. Vui lòng chọn lại!";
								break;
							}
						}
					}
					else
					{
						kq = "ERROR--Sản phẩm của BOM không phải nguyên liệu cần thiết. Vui lòng chọn lại!";
						break;
					}
					rs.close();
				}	

				if(kq.trim().length() <= 0)
				{
					_sanpham = _sanpham.substring(0, _sanpham.length() - 2);
					System.out.println("_sanpham: " + _sanpham);
					String[] sp = _sanpham.split("__");
					for (int i = 0; i < sp.length; i++) 
					{
						ErpChiTietBom ct = new ErpChiTietBom(sp[i]);
						for (ErpChiTietBom ob : list) {
							if(ob.equals(ct))
								ob.setTinh(true);
						}
					}
					
					List<ErpChiTietBom> list2 = new ArrayList<ErpChiTietBom>();
					for (ErpChiTietBom ct : list) {
						System.out.println("thutu = " + ct.getStt() + "; sp = " + ct.getSanpham() + "; sl = " + ct.getSoluong() + "; tinh = " + ct.getTinh() + "; containt = " + list2.contains(ct));
						if(ct.getTinh())
						{
							if(!list2.contains(ct))
								list2.add(ct);
							else
							{
								int index = list2.indexOf(ct);
								ErpChiTietBom ctnew = list2.get(index);
								double soluong = ctnew.getSoluong();
								ctnew.setSoluong(soluong + ct.getSoluong());
								if(Integer.parseInt(ct.getStt()) < Integer.parseInt(ctnew.getStt()))
									ctnew.setStt(ct.getStt());
							}
						}
					}
					for (ErpChiTietBom ct : list2) {
						kq += ct.getStt() + "-" + ct.getSanpham() + "-" + ct.getSoluong() + "][";
					}
				}
				
				db.shutDown();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
    		out.write(kq);
		}
	}
}
