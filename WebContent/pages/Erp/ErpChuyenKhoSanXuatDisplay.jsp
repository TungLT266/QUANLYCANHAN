<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.traphaco.erp.beans.lenhsanxuat.*" %>
<%@ page  import = "geso.traphaco.erp.beans.lenhsanxuat.imp.*" %>
<%@ page  import = "geso.traphaco.erp.beans.phieuxuatkho.*" %>
<%@ page  import = "geso.traphaco.erp.beans.nhapkho.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>

<% IErpChuyenkhoSX lsxBean = (IErpChuyenkhoSX)session.getAttribute("lsxBean"); %>
<% ResultSet khoxuatRs = lsxBean.getKhoXuatRs(); %>
<% ResultSet khonhapRs = lsxBean.getKhoNhapRs(); %>
<% ResultSet lsxRs = lsxBean.getLsxRs(); %>
<% List<IYeucau> spList = (List<IYeucau>)lsxBean.getSpList(); %>
<% List<IKhu_Vitri> khuList = lsxBean.getKhuList(); %>
<% List<IKhu_Vitri> vitriList = lsxBean.getVitriList(); %>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen"); 
	String sum = (String) session.getAttribute("sum");
	  NumberFormat formatter3 = new DecimalFormat("#,###,###.######");
	  Double tongsoluong = 0.0;
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>TraphacoHYERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});	
</script>

	
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="hctmhForm" method="post" action="">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
    	<%if(lsxBean.getIsnhanHang().equals("1")){ %>
        	Quản lý cung ứng > Sản xuất > Nhận nguyên liệu > Hiển thị
        <% }else {%>
        	Quản lý cung ứng > Quản lý tồn kho > Chuyển nguyên liệu > Hiển thị
        <%} %>
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
  	
    	<A href= "../../ErpChuyenkhoSXSvl?userId=<%= userId %>&task=<%=lsxBean.gettask()%>&isnhanHang=<%=lsxBean.getIsnhanHang()%>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
    	
    	<%if(lsxBean.getnoiDungXuat().equals("100025")) {%>   
       <%-- <A href="../../ErpPhieuxuatchuyennoiboPdfSvl?userId=<%=userId %>&print=<%=lsxBean.getId() %>" >
       		<img src="../images/Printer30.png" alt="Print"  title="Print"  border ="1px" longdesc="Print" style="border-style:outset"></A>										 --%>   
		<%} else {%>
	       <A href= "../../ErpPhieuchuyenkhoPdfSvl?userId=<%=userId%>&print=<%= lsxBean.getId() %>" >
        	<img src="../images/Printer30.png" alt="In"  title="In" border="1px" longdesc="In" style="border-style:outset"></A>
        <%} %>
    </div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  rows="1" readonly="readonly" style ="width:100%"><%= lsxBean.getMsg() %></textarea>
		         <% lsxBean.setMsg(""); %>
    	</fieldset> 
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle">Chuyển kho </legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0">							
                <TR>
                    <TD width="20%" class="plainlabel" valign="top">Ngày nhận </TD>
                    <TD colspan="4" class="plainlabel" valign="top">
                    	<input type="text" readonly="readonly"  name="ngaychuyen" value="<%= lsxBean.getNgayyeucau() %>"  /></TD>
                </TR>
                <TR>
                    <TD class="plainlabel" valign="top">Lý do </TD>
                    <TD colspan="4" class="plainlabel" valign="top">
                    	<input type="text"  name="lydo" value="<%= lsxBean.getLydoyeucau() %>" readonly="readonly" />
                    </TD>
                </TR>
                <TR>
                    <TD class="plainlabel" valign="top">Người nhận </TD>
                    <TD colspan="4"  class="plainlabel" valign="top">
                    	<input type="text"  name="nguoinhan" value="<%= lsxBean.getNguoinhan() %>"/>
                    </TD>
                </TR>
                
               <%--  <TR>
                    <TD class="plainlabel" valign="top">Số lượng nhận </TD>
                    <TD colspan="4"  class="plainlabel" valign="top">
                    	<input type="text"  name="tongslnhan" value="<%= formatter3.format(Double.parseDouble(lsxBean.getTongSLnhan()))  %>"/>
                    </TD>
                </TR> --%>
                <%
                for(int i = 0; i < spList.size(); i++)
        		{
        			IYeucau yeucau = spList.get(i);
        			System.out.println();
        			if(lsxBean.getIsnhanHang().equals("1"))
        				tongsoluong+=Double.parseDouble(yeucau.getSoluongNhan().replaceAll(",", ""));
        			else
        				tongsoluong+=Double.parseDouble(yeucau.getSoluongchuyen().replaceAll(",", ""));
        		}
                if(lsxBean.getIsnhanHang().equals("1")){ %>
                <TR>
	               	 <TD class="plainlabel">Tổng số lượng nhận</TD>
                    	<TD class="plainlabel" >
                    		<input type="text"  name="tongsoluong" value="<%= formatter3.format(tongsoluong)%>"/></TD>
		        </TR>
		        <% }else {%>
		       	<TR>
		        		<TD class="plainlabel">Tổng số lượng chuyển</TD>
                    	<TD class="plainlabel" >
                    		<input type="text"  name="tongsoluong" value="<%= formatter3.format(tongsoluong)%>"/></TD>
                 </TR>
		        <%} %>
                 <!-- CHUYỂN KHO BÊN NGOÀI -->
                <%if( lsxBean.getNdxId().equals("100025") ){ %>
                <TR>
                	<TD class="plainlabel">Ký hiệu</TD>
                    	<TD class="plainlabel" colspan="4" >
                    	<input type="text"  name="kyhieu" value="<%=lsxBean.getKyHieu() %>"/></TD>
                </TR>
                <TR>
                	<TD class="plainlabel">Số chứng từ</TD>
                    	<TD class="plainlabel" colspan="4" >
                    	<input type="text"  name="sochungtu" value="<%= lsxBean.getSochungtu()%>"/></TD>
                </TR>
                <TR>
                	<TD class="plainlabel">Lệnh điều động số</TD>
                    	<TD class="plainlabel">
                    	<input type="text"  name="lenhdieudong" value="<%=lsxBean.getLenhdieudong() %>"/></TD>
                    <TD  class="plainlabel" valign="top">Ngày  </TD>
                   		 <TD  class="plainlabel" valign="top" >
                    	<input  type="text" class="days" name="ngaydieudong" value="<%=lsxBean.getNgaydieudong() %>"/></TD>
                </TR>
                <TR>
                	<TD class="plainlabel">Của</TD>
                    	<TD class="plainlabel" colspan="4" >
                    	<input type="text"  name="nguoidieudong" value="<%=lsxBean.getNguoidieudong() %>"/></TD>
                </TR>
                <TR>
                	<TD class="plainlabel">Về việc</TD>
                    	<TD class="plainlabel" colspan="4" >
                    	<input type="text"  name="veviec" value="<%=lsxBean.getVeviec() %>"/></TD>
                </TR>
                <TR>
                	<TD class="plainlabel">Người vận chuyển</TD>
                    	<TD class="plainlabel" colspan="4" >
                    	<input type="text"  name="nguoivanchuyen" value="<%=lsxBean.getNguoivanchuyen() %>"/></TD>
                </TR>
                <TR>
                	<TD class="plainlabel">Phương tiện vận chuyển</TD>
                    	<TD class="plainlabel" >
                    	<input type="text"  name="phuongtien" value="<%=lsxBean.getPhuongtien() %>"/></TD>
                    <TD class="plainlabel">Hợp đồng</TD>
                    	<TD class="plainlabel" >
                    	<input type="text"  name="hopdong" value="<%=lsxBean.getHopdong() %>"/></TD>
                </TR>
                <%} %>
                <TR>
                    <TD class="plainlabel" valign="top">Kho chuyển </TD>
                    <TD colspan="4" class="plainlabel" valign="top">
                    	<input type="hidden" name = "khoxuatId" value="<%= lsxBean.getKhoXuatId() %>" >
                    	<select disabled="disabled" >
                    		<option value=""> </option>
                        	<%
                        		if(khoxuatRs != null)
                        		{
                        			try
                        			{
                        			while(khoxuatRs.next())
                        			{  
                        			if( khoxuatRs.getString("pk_seq").equals(lsxBean.getKhoXuatId())){ %>
                        				<option value="<%= khoxuatRs.getString("pk_seq") %>" selected="selected" ><%= khoxuatRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= khoxuatRs.getString("pk_seq") %>" ><%= khoxuatRs.getString("ten") %></option>
                        		 <% } } khoxuatRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                    	</select>
                    </TD>
                </TR>     
                <%if(lsxBean.getKhoNhapId()!= null &&  lsxBean.getKhoNhapId().trim().length()>0) {%>
                <TR>
                    <TD class="plainlabel" valign="top">Kho nhận </TD>
                    <TD colspan="4" class="plainlabel" valign="top">
                    	<input type="hidden" name = "khonhapId" value="<%= lsxBean.getKhoNhapId() %>" >
                    	<select disabled="disabled" >
                    		<option value=""> </option>
                        	<%
                        		if(khonhapRs != null)
                        		{
                        			try
                        			{
                        			while(khonhapRs.next())
                        			{  
                        			if( khonhapRs.getString("pk_seq").equals(lsxBean.getKhoNhapId())){ %>
                        				<option value="<%= khonhapRs.getString("pk_seq") %>" selected="selected" ><%= khonhapRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= khonhapRs.getString("pk_seq") %>" ><%= khonhapRs.getString("ten") %></option>
                        		 <% } } khonhapRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                    	</select>
                    </TD>
                </TR>
                <%} %>
                
                
            </TABLE>
			<hr />
			
			<table cellpadding="0px" cellspacing="1px" width="100%">
			<tr class="tbheader">
				<th align="center" width="5%"  >STT</th>
				<th align="center" width="15%"  >Mã sản phẩm</th>
				<th align="center" width="25%">Tên sản phẩm</th>
				<th align="center" width="10%">Đơn vị tính </th>
				<th align="center" width="7%">Số lượng chuyển</th>
				
				<th align="center" width="7%">Vị trí xuất</th>
				<%if(lsxBean.getKhoNhapId()!= null &&  lsxBean.getKhoNhapId().trim().length()>0) {%>
					<th align="center" width="10%">Số lượng nhận</th>
					<th align="center" width="10%">Vị trí nhận</th>
					<th align="center" width="9%">Ghi chú</th>
				<%} %>
			</tr>
			
			<% 
			if(spList.size() > 0) 
			{  
				for(int i = 0; i < spList.size(); i++)
				{ 
					IYeucau yeucau = spList.get(i); %>
					
					<tr>
						<td style="font-size: 9pt;" align="center"> <%=i+1 %></td>
						<td  > <input type="text" name="masp" value="<%= yeucau.getMa() %>" style="width: 100%"  readonly="readonly"  > </td>
						<td> <input type="text" name="tensp" value="<%= yeucau.getTen() %>" style="width: 100%" readonly="readonly"> </td>
						<td> <input type="text" name="tensp" value="<%= yeucau.getDonViTinh() %>" style="width: 100%" readonly="readonly"> </td>
						<td> <input type="text" name="soluongchuyen" value="<%= yeucau.getSoluongchuyen() %>" style="width: 100%; text-align: right;" readonly="readonly" > </td>
						
						<td align="center">
						<a href="" id="<%= yeucau.getId() + "pobup"%>" rel="subcontent<%= i %>">
		           	 							<img alt="Vi tri luu kho" src="../images/vitriluu.png"></a>
           	 					<DIV  id="subcontent<%= i %>" style=" z-index=10; position:absolute; visibility: hidden; border: 9px solid #80CB9B;
			                             background-color: white; max-height :500px;width: 500px; padding: 1px; overflow: auto; ">
			                    <table width="100%" align="center">
			                        <tr>
			                        	
			                            <%if(lsxBean.getIsQuanlykhuvuc_khoxuat().equals("1")){ %>
				                             	<th width="10%">Khu vực kho</th>
				                             <%} %>
			                             <th width="20%">Số lô</th>
			                             <th width="20%">Ngày nhập kho  </th>
			                        
 										 <th width="20%" >Số lượng xuất</th>
			                        </tr>
			                        <%
			                        	List<ISpDetail> spConList = yeucau.getSpDetailList();
			                        	int stt = 1; 
			                        	if(spConList.size() > 0)
			                        	{
			                        		for(int sd = 0; sd < spConList.size(); sd ++)
			                        		{
			                        			ISpDetail spCon = spConList.get(sd);
			                        			
			                        			double soluongct= 0;
			                        			try{
			                        			 soluongct= Double.parseDouble(spCon.getSoluong().replaceAll(",",""));
			                        			}catch(Exception er){}
			                        			
			                        		%>
			                        			<tr>
			                        			<%if(lsxBean.getIsQuanlykhuvuc_khoxuat().equals("1")){ %>
			                        			<td> 
			                        				
			                        				<input name="<%= yeucau.getId() + "khuvuc_id" %>" 
						                            	type="hidden" style="width: 100%" value="<%= spCon.getKhuId() %>" readonly="readonly" /> 
						                            	
						                            	<input name="<%= yeucau.getId() + "khuvuc_ten" %>" 
						                            	type="text" style="width: 100%" value="<%= spCon.getKhu() %>" readonly="readonly" /> 
						                            </td>
						                            <%} %>
						                            <td> 
						                            	<input name="<%= yeucau.getId() + "solo" %>" 
						                            	type="text" style="width: 100%" value="<%= spCon.getSolo() %>" readonly="readonly" /> 
						                            </td>
						                            <td> 
						                            	<input name="<%= yeucau.getId() + "ngaynhapkho" %>" 
						                            	type="text" style="width: 100%" value="<%= spCon.getNgaybatdau() %>" readonly="readonly" /> 
						                            </td>
						                           
						                             
						                            	<td>
						                            	<input name="<%= yeucau.getId() + "soluongxuat" %>" 
						                            		type="text" style="width: 100%" value="<%=formatter3.format(soluongct)%>" onkeypress="return keypress(event);" /> 
						                            	</td>
						                            	
						                           
						                        </tr>
			                        		<%  } 
			                        	}
			                        %>
			                         
			                    </table>
			                     <div align="right"><a href="javascript:hoantat('<%=i %>','<%= yeucau.getId()%>')">Hoàn tất</a></div>
			                </DIV>
						</td>
						<%if(lsxBean.getKhoNhapId()!= null &&  lsxBean.getKhoNhapId().trim().length()>0) { 
							double soluongnhan= 0;
                			try{
                				soluongnhan= Double.parseDouble(yeucau.getSoluongNhan().replaceAll(",",""));
                			}catch(Exception er){}
                			
						%>
						<td> <input type="text" name="soluongnhan" value="<%= formatter3.format(soluongnhan) %>" style="width: 100%; text-align: right;" readonly="readonly"> </td>
						
						<td align="center"> 
							 <a href="" id="<%= yeucau.getId() + "pobup2"%>" rel="subcontentnhap<%= i %>">
		           	 							<img alt="Vi tri luu kho" src="../images/vitriluu.png"></a>
           	 					<DIV  id="subcontentnhap<%= i %>" style=" z-index=10; position:absolute; visibility: hidden; border: 9px solid #80CB9B;
			                             background-color: white; max-height :500px;width: 500px; padding: 1px; overflow: auto; ">
			                    <table width="100%" align="center">
			                        <tr>
			                            <%if(lsxBean.getIsQuanlykhuvuc_khonhap().equals("1")){ %>
				                         <th width="10%">Khu vực kho</th>
				                             <%} %>
			                             <th width="20%">Số lô</th>
			                             <th width="20%">Ngày nhập kho  </th>
 										 <th width="20%" >Số lượng xuất</th>
			                        </tr>
			                        <%
			                        spConList.clear();
			                        //	list kho nhận
			                        	  spConList = yeucau.getSpDetail2List();
			                        	  stt = 1; 
			                        	if(spConList.size() > 0)
			                        	{
			                        		for(int sd = 0; sd < spConList.size(); sd ++)
			                        		{
			                        			ISpDetail spCon = spConList.get(sd);
			                        			double soluongct= 0;
			                        			try{
			                        			 soluongct= Double.parseDouble(spCon.getSoluong().replaceAll(",",""));
			                        			}catch(Exception er){}
			                        			
			                        		%>
			                        			<tr>
			                        			<% 
			                        		 
			                        			if(lsxBean.getIsQuanlykhuvuc_khonhap().equals("1")){ %>
			                        			<td> 
			                        				
			                        				<input name="<%= yeucau.getId() + "khuvuc_id" %>" 
						                            	type="hidden" style="width: 100%" value="<%= spCon.getKhuId() %>" readonly="readonly" /> 
						                            	
						                            	<input name="<%= yeucau.getId() + "khuvuc_ten" %>" 
						                            	type="text" style="width: 100%" value="<%= spCon.getKhu() %>" readonly="readonly" /> 
						                            </td>
						                            <%} %>
						                            <td> 
						                            	<input name="<%= yeucau.getId() + "solo" %>" 
						                            	type="text" style="width: 100%" value="<%= spCon.getSolo() %>" readonly="readonly" /> 
						                            </td>
						                            <td> 
						                            	<input name="<%= yeucau.getId() + "ngaynhapkho" %>" 
						                            	type="text" style="width: 100%" value="<%= spCon.getNgaybatdau() %>" readonly="readonly" /> 
						                            </td>
						                             
						                            	<td>
						                            	<input name="<%= yeucau.getId() + "soluongxuat" %>" 
						                            		type="text" style="width: 100%" value="<%=formatter3.format(soluongct)%>" onkeypress="return keypress(event);" /> 
						                            	</td>
						                         		 
						                        </tr>
			                        		<%  } 
			                        	}
			                        %>
			                         
			                    </table>
			                     <div align="right"><a href="javascript:hoantat('<%=i %>','<%=yeucau.getId()+ "pobup2"%>')">Hoàn tất</a></div>
			                </DIV>
			        
						</td>
						 <td> <input type="text" name="ghichu" value="" style="text-align: right;"  > </td>
					  <%} %>
					</tr>
			<% } } %>
			</table>
				
            </div>
     </fieldset>	
    </div>
</div>
<script type="text/javascript">

	<% 
		for(int i = 0; i < spList.size(); i++)
		{
			IYeucau yeucau = spList.get(i);
	%>
		dropdowncontent.init('<%=yeucau.getId()+ "pobup"%>', "left-top", 500, "click");
		dropdowncontent.init('<%=yeucau.getId()+ "pobup2"%>', "left-top", 500, "click");
	<%} %>


</script>

<%  
	try{
		khuList.clear();
		spList.clear();
		vitriList.clear();
		lsxBean.DBclose(); 
		
	}catch(Exception er){
		
	}
	} %>

</form>
</BODY>
</html>