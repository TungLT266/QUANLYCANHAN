package geso.traphaco.distributor.servlets.nhanviengiaonhan;

import geso.traphaco.distributor.db.sql.dbutils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ajaxNhanVienGN extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       

    public ajaxNhanVienGN() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
		PrintWriter out = response.getWriter();
		String ddkdIds = request.getParameter("ddkdIds");
		String tbhIds = request.getParameter("tbhIds");
		
		System.out.println("Tuyen ban hang luu lai duoc la: " + tbhIds + "\n");
		
		String id = request.getParameter("id");
		String nppId = request.getParameter("nppId");
		String update = request.getParameter("update");
		String nvgnId=request.getParameter("nvgnId").trim();
		
		String ttId=request.getParameter("ttId");
		String qhId=request.getParameter("qhId");
		
		System.out.println("______"+ttId);
		
		dbutils db = new dbutils();
		if(id.equals("tbhIds")) //loc dai dien kinh doanh
		{
			String str = "";
			if(ddkdIds.length() > 0)
			{
				ddkdIds = ddkdIds.substring(0, ddkdIds.length() - 1);
				String query = "select tuyenbanhang.pk_seq as tbhId,tuyenbanhang.diengiai as tbhTen, ddkd.ten, ngaylamviec " +
								" from tuyenbanhang inner join daidienkinhdoanh ddkd on ddkd.pk_seq =tuyenbanhang.ddkd_fk  "+
								" where tuyenbanhang.npp_fk = '" + nppId + "' and tuyenbanhang.ddkd_fk in (" + ddkdIds + ") ";
				
				System.out.println("Query lay tuyen ban hang la: " + query + "\n");
				ResultSet rs = db.get(query);
				
				if(rs != null)
				{
					try 
					{

						str = "<option value=''></option>";
						while(rs.next())
						{
							if(tbhIds.indexOf(rs.getString("tbhId")) >= 0)
							{
								str += "<option style=\"padding: 2px; width: 90%\" value='" + rs.getString("tbhId") + "' selected>" + rs.getString("ten") + " - " + rs.getString("tbhTen") + " - " + rs.getString("ngaylamviec") + "</option>";
								System.out.println("Id nhay vo day...\n");
							}
							else
								str += "<option style=\"padding: 2px; width: 90%\" value='" + rs.getString("tbhId") + "'>" + rs.getString("ten") + " - " + rs.getString("tbhTen") + " - " + rs.getString("ngaylamviec") + "</option>";
						}
						rs.close();
					} 
					catch(Exception e) {}
				}
			}
			System.out.println("Ket qua la: " + str + "\n");
			out.write(str);
		}
		else if(id.equals("qhId"))
		{
			String str = "";
			if(ttId.length()>0)
			{
				ttId = ttId.substring(0, ttId.length() - 1);
				String query = "select * from quanhuyen where tinhthanh_fk in ("+ttId+") ";
				
				System.out.println("Query lay tuyen ban hang la: " + query + "\n");
				ResultSet rs = db.get(query);
				
				if(rs != null)
				{
					try 
					{
	
						str = "<option value=''></option>";
						while(rs.next())
						{
							if(tbhIds.indexOf(rs.getString("pk_seq")) >= 0)
							{
								str += "<option style=\"padding: 2px; width: 90%\" value='" + rs.getString("pk_seq") + "' selected>" + rs.getString("ten") + "</option>";
								System.out.println("Id nhay vo day...\n");
							}
							else
								str += "<option style=\"padding: 2px; width: 90%\" value='" + rs.getString("pk_seq") + "'>" + rs.getString("ten") + "  " + "</option>";
						}
						rs.close();
					} 
					catch(Exception e) {}
				}
			System.out.println("Ket qua la: " + str + "\n");
			out.write(str);			
			}
		}
		
		else
		{
				String sql = "select distinct a.pk_seq as khId, a.ten as khTen, a.diachi, isnull(a.dienthoai, 'na') as dienthoai from khachhang a  where a.npp_fk='" + nppId + "' and trangthai=1 ";
				
				if(tbhIds.length() > 0)
				{
					tbhIds = tbhIds.substring(0, tbhIds.length() - 1);
					sql += " and a.pk_seq in (select khachhang_fk from khachhang_tuyenbh where tbh_fk in ("+tbhIds+")  ) ";
				}
				if(ddkdIds.length() > 0)
				{
					ddkdIds = ddkdIds.substring(0, ddkdIds.length() - 1);
					sql += " and a.pk_seq in (select khachhang_fk from khachhang_tuyenbh where tbh_fk in (select pk_Seq from tuyenbanhang where ddkd_fk in ("+ddkdIds+") and npp_fk='"+nppId+"'))  ";
				}
				
				if(ttId.length()>0)
				{
					ttId = ttId.substring(0, ttId.length() - 1);
				}
				
				if(qhId.length()>0)
				{
					qhId = qhId.substring(0, qhId.length() - 1);
				}
				
				if(ttId.length()>0)
				{
					sql+=" and a.tinhthanh_fk in ("+ttId+")";
				}
				
				if(qhId.length()>0)
				{
					sql+=" and a.quanhuyen_fK in ("+qhId+")";
				}
				
			
				System.out.println("Query lay khach hang: " + sql + "\n");
				
				String str = "";
				ResultSet khList = db.get(sql);
				if(khList != null)
				{
					try 
					{
						int i = 0;
						str+="<table width=\"100%\" border=\"0\" cellspacing=\"1\" cellpadding=\"3px\">"+
			                    "<tr class=\"tbheader\">"+
			                    "    <th width=\"150px\" align=\"center\">Mã khách hàng </th>"+
			                    "   <th width=\"200px\" align=\"left\"> Họ tên </th>"+
			                    "    <th width=\"400px\" align=\"left\">địa chỉ </th>"+
			                    "   <th width=\"150px\" align=\"left\">Điện thoại </th>"+
			                    "    <th align=\"center\" >Chọn <input type=\"checkbox\" id=\"selectAll\" onChange=\"CheckAll()\"/></th>"+
			                    "</tr>";
						while(khList.next())
						{
							if (i % 2 == 0)
								str += "<TR class= \"tblightrow\" > ";
							else
								str +=	"<TR class= \"tbdarkrow\" > ";
	
							str += "<TD width=\"150px\" align=\"center\">" + khList.getString("khId") + "</TD>";
							str += "<TD width=\"200px\" align='left'>" + khList.getString("khTen") + "</TD>";
							str += "<TD width=\"400px\" align='left'>" + khList.getString("diachi") + "</TD>";
							str += "<TD width=\"150px\" align='left'>" + khList.getString("dienthoai") + "</TD>";									
							
						    str += "<TD align='center'>";
						   	str += "<input name=\"khIds\" type=\"checkbox\" value =" + khList.getString("khId") + " checked onChange=\"UnCheckedAll()\"></TD></TR>"; 
						   	i++;
						   	
						}
						khList.close();
						str += "<tr><td class=\"plainlabel\" colspan=\"6\">&nbsp;</td></tr>";
					} 
					catch(Exception e) {e.printStackTrace();}
				}
				str += "</table>";
				System.out.println("Ket qua khachh hang la: " + str + "\n");
				out.write(str);
				if(db!=null)db.shutDown();
			}
		}		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
	}

}
