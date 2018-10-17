<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Hashtable"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="geso.traphaco.distributor.beans.donhangtratb.*"%>
<%@page import="geso.traphaco.distributor.beans.donhangtratb.imp.*"%>
<%@page import="java.util.Calendar" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.List" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@page import="java.sql.SQLException" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<%
 	IDonhangtraTB obj =(IDonhangtraTB)session.getAttribute("tctskuBean");
	ResultSet cttbRs = obj.getCttbRs();
	ResultSet khRs = obj.getKhRs();
	ResultSet thuongRs = obj.getThuongDhdautien();
	//Hashtable<String, Double> khachhang_thanhtoan = obj.getKhachhang_thanhtoan();
	
	String[] khId = obj.getKhId();
	String[] khTen = obj.getKhTen();
	String[] spId = obj.getSpId();
	String[] spTen = obj.getSpTen();
	String[] soxuat = obj.getSoxuat();
	String[] total = obj.getTotal();
	String[] thanhtoan = obj.getThanhtoan();
	
	NumberFormat formater = new DecimalFormat("#,###,###");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
	<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
	<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
	
	<script type="text/javascript" src="../scripts/ajax.js"></script>
	<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
	<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
	
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>

	<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

	<script type="text/javascript">
		$(document).ready(function() {
			$(".days").datepicker({
				changeMonth : true,
				changeYear : true
			});
			
			//$("ul.tabs").tabs("div.panes > div");
		});
	</script> 
	
	<script>
	$(function() {
	 	$("ul.tabs").tabs("div.panes > div");
	});
	</script>

<SCRIPT language="JavaScript" type="text/javascript">
	
	function keypress(e) //Hàm dùng d? ngan ngu?i dùng nh?p các ký t? khác ký t? s? vào TextBox
	{    
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode; //IE
		else
			keypressed = e.which; //NON-IE, Standard
		
		if (keypressed < 48 || keypressed > 57)
		{ 
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46)
			{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}
	
	function save()
	{
	   document.forms["tctsku"].action.value = "save";
	   document.forms["tctsku"].submit(); 
    }
	
	function FormatNumber(e)
	{
		e.value = DinhDangTien(e.value.replace(/,/g,""));
	}
	
	function DinhDangTien(num) 
	 {
	    num = num.toString().replace(/\$|\,/g,'');
	    if(isNaN(num))
	    num = "0";
	    sign = (num == (num = Math.abs(num)));
	    num = Math.floor(num*100+0.50000000001);
	    num = Math.floor(num/100).toString();
	    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	    num = num.substring(0,num.length-(4*i+3))+','+
	    num.substring(num.length-(4*i+3));
	    return (((sign)?'':'-') + num);
	}

</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="tctsku" method="post" action="" >
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="action" value="0">
<input type="hidden" name="id" value="<%= obj.getId() %>">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý trưng bày > Đơn hàng trả trưng bày > Hiển thị</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../DonhangtraTBSvl?userId=<%=userId%>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD width="30" align="left" >
						    
						    </TD>
							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thông tin đơn hàng trả trưng bày </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
							<TR>
								<TD width="120px" class="plainlabel" >Ngày đơn hàng</TD>
								<TD class="plainlabel" colspan="3">
									<input type="text" class="days" name="ngaynhap" value="<%= obj.getNgaynhap() %>" >
								</TD>
							  </TR>
							
							  <TR>
						  	  	<TD class="plainlabel">Mã CTTB <FONT class="erroralert"> *</FONT></TD>
						  	  	<TD class="plainlabel" colspan="3">
						  	  		<select name="cttbId" onchange="submitform();" >
						  	  		<option value="" ></option>
							  	  		<% if(cttbRs != null) { 
							  	  			while(cttbRs.next())
							  	  			{
							  	  				if(obj.getCttbId().equals(cttbRs.getString("cttbId"))) { %>
							  	  					<option value="<%= cttbRs.getString("cttbId") %>" selected="selected" ><%= cttbRs.getString("scheme") %></option>
							  	  				<% } else { %>
							  	  					<option value="<%= cttbRs.getString("cttbId") %>"  ><%= cttbRs.getString("scheme") %></option>
							  	  				<%  }
							  	  			}
							  	  		} %>
						  	  		</select>
						  	  	</TD>
							  </TR>
							  <TR>
						  	  	<TD class="plainlabel">Lần thanh toán <FONT class="erroralert"> *</FONT></TD>
						  	  	<TD class="plainlabel" colspan="3">
						  	  		<select name="lanthanhtoan" onchange="submitform();"  >
						  	  		<option value="0" ></option>
							  	  		<% for(int i = 1; i <= obj.getSoLanthanhtoan(); i++) 
							  	  		{
							  	  			if( i == obj.getLanthanhtoan())
							  	  			{
							  	  				%>
							  	  				<option value="<%= i %>" selected="selected" ><%= "Lần thanh toán thứ " + i %></option>
							  	  			<% }
							  	  			else
							  	  			{
							  	  				%>
							  	  				<option value="<%= i %>" ><%= "Lần thanh toán thứ " + i %></option>
							  	  			<% } %>
							  	  		<% } %>
						  	  		</select>
						  	  	</TD>
							  </TR>
							  <TR>
						  	  	<TD class="plainlabel">Ghi chú</TD>
						  	  	<TD class="plainlabel" colspan="3">
						  	  		<input type="text" name="diengiai" id="diengiai" value="<%= obj.getDiengiai() %>"> 
						  	  	</TD>
							  </TR>

						</TABLE>
						
						<ul class="tabs">
							<li><a href="#">Thưởng khách hàng</a></li>
							<li><a href="#">Thưởng ĐH đầu tiên</a></li>
						</ul>
						
						<div class="panes">
							
							<div>
								
								<TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px">
		                    
				                    <TR class="tbheader">
				                        <td align="center" width="30%">Khách hàng</td>
				                        <td align="center" width="30%">Sản phẩm</td>
				                    	<td align="center" width="10%" >Số xuất</td>
				                    	<td align="center" width="10%" >Số lượng / Số tiền</td>
				                    	<td align="center" width="10%" >Thanh toán</td>
				                    </TR>
				                    
				                    <% if(khId != null) { 
				                    	for(int i = 0; i < khId.length; i++)
				                    	{
				                    		%>
				                    		
				                    		<TR>
				                    			<TD>
				                    				<input type="hidden" name="khId" value="<%= khId[i] %>" style="width: 100%" >
				                    				<input type="text" name="khTen" value="<%= khTen[i] %>" style="width: 100%" readonly="readonly" > 
				                    			</TD>
				                    			<TD>
				                    				<input type="hidden" name="spId" value="<%= spId[i].trim() %>" style="width: 100%" >
				                    				<input type="text" name="spTen" value="<%= spTen[i].trim() %>" style="width: 100%" readonly="readonly" > 
				                    			</TD>
				                    			<TD>
				                    				<input type="text" name="soxuat" value="<%= soxuat[i] %>" style="width: 100%; text-align: right;" readonly="readonly" > 
				                    			</TD>
				                    			<TD>
				                    				<input type="text" name="total" value="<%= total[i] %>" style="width: 100%; text-align: right;" readonly="readonly" > 
				                    			</TD>
				                    			<TD>
				                    				<input type="text" name="thanhtoan" value="<%= thanhtoan[i].trim() %>" style="width: 100%; text-align: right;" onkeyup="FormatNumber(this);" readonly="readonly" > 
				                    			</TD>
				                    		</TR>
				                    		
				                    	<%  }
				                    } %>
				                    
				                    <TR>
				                        <TD align="center" colspan="15" class="tbfooter">&nbsp;</TD>
				                    </TR>
								</TABLE>
								
							</div>
							
							<div>
								<TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px">
		                    
				                    <TR class="tbheader">
				                        <td align="center" width="50%">Khách hàng</td>
				                        <td align="center" width="20%">Đơn hàng đầu tiên</td>
				                        <td align="center" width="10%">Ngày đơn hàng</td>
				                        <td align="center" width="10%">Tổng tiền</td>
				                    	<td align="center" width="10%" >Thưởng</td>
				                    </TR>
				                    
					                    <% if(thuongRs != null) 
					                    { 
					                    	while(thuongRs.next())
					                    	{
					                    		%>
					                    		
					                    		<tr>
					                    			<td>
					                    				<input type="text" style="width: 100%" value="<%= thuongRs.getString("khTen") %>" readonly="readonly" >
					                    			</td>
					                    			<td>
					                    				<input type="text" style="width: 100%; text-align: center;" value="<%= thuongRs.getString("dhId") %>" readonly="readonly" >
					                    			</td>
					                    			<td>
					                    				<input type="text" style="width: 100%; text-align: center;" value="<%= thuongRs.getString("ngaynhap") %>" readonly="readonly" >
					                    			</td>
					                    			<td>
					                    				<input type="text" style="width: 100%; text-align: right;" value="<%= formater.format(thuongRs.getDouble("tongtien")) %>" readonly="readonly" >
					                    			</td>
					                    			<td>
					                    				<input type="text" style="width: 100%; text-align: right;" value="<%= formater.format(thuongRs.getDouble("thuong")) %>" readonly="readonly" >
					                    			</td>
					                    		</tr>
					                    		
					                    	<% } 
					                    	thuongRs.close(); 
					                    } %>
				                    
				                    <TR>
				                        <TD align="center" colspan="15" class="tbfooter">&nbsp;</TD>
				                    </TR>
								</TABLE>
							</div>
							
						</div>
						
						
						
									
						</FIELDSET>						
					</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
	</TABLE>
</form>

</BODY>
</HTML>
<%
if(obj!=null)obj.DBClose();
	}%>
