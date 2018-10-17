<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="geso.traphaco.center.beans.duyettrakhuyenmai.imp.*" %>
<%@page import="geso.traphaco.center.beans.duyettrakhuyenmai.*" %>
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
 	IDuyettrakhuyenmai obj =(IDuyettrakhuyenmai)session.getAttribute("tctskuBean");
	
	ResultSet ctkmRs = obj.getCtkmRs();
	//ResultSet khRs = obj.getKhachhangRs();
	String[] nppId = obj.getNppId();
	String[] nppTen = obj.getNppTen();
	String[] khId = obj.getKhId();
	String[] khTen = obj.getKhTen();
	String[] doanhso = obj.getDoanhso();
	String[] tongtien = obj.getTongtien();
	String[] donviThuong = obj.getDonvithuong();
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$(".days").datepicker({
			changeMonth : true,
			changeYear : true
		});
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
<form name ="tctsku" method="post" action="../../DuyettrakhuyenmaiUpdateSvl" >
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="action" value="0">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý khuyến mại > Duyệt trả khuyến mại tích lũy > Hiển thị</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../DuyettrakhuyenmaiSvl?userId=<%=userId%>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
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
						<LEGEND class="legendtitle" style="color:black">Duyệt trả khuyến mại tích lũy </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
							<TR>
								<TD width="120px" class="plainlabel" >Ngày duyệt <FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel" colspan="3">
									<input name="ngayduyet" class="days" value="<%= obj.getNgayduyet()  %>" readonly="readonly"  >
								</TD>
							</TR>
							
						  <TR>
						  	  	<TD class="plainlabel">Scheme <FONT class="erroralert"> *</FONT></TD>
						  	  	<TD class="plainlabel" colspan="3">
						  	  		<select name="ctkmId" onchange="submitform();" >
						  	  			<option value=""></option>
						  	  			<% if(ctkmRs != null) { 
						  	  				while(ctkmRs.next())
						  	  				{
						  	  					if(ctkmRs.getString("ctkmId").equals(obj.getCtkmId()))
						  	  					{
						  	  						%>
						  	  						<option value="<%= ctkmRs.getString("ctkmId") %>" selected="selected" ><%= ctkmRs.getString("Scheme") %></option>
						  	  					<% } else { %>
						  	  						<option value="<%= ctkmRs.getString("ctkmId") %>"  ><%= ctkmRs.getString("Scheme") %></option>
						  	  					<% }
						  	  				}
						  	  				ctkmRs.close();
						  	  			} %>
						  	  		</select>
						  	  	</TD>
						  </TR>
						  <TR>
						  	  	<TD class="plainlabel">Diễn giải</TD>
						  	  	<TD class="plainlabel" colspan="3">
						  	  		<input type="text" name="diengiai" id="diengiai" value="<%= obj.getDiengiai() %>"> 
						  	  	</TD>
						  </TR>
						  <TR>
						  	  	
						  </TR>
						  
						</TABLE>
						<hr />
						
						<TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px">
		                   
							<TR class="tbheader">
		                        <TH align="center" width="25%" >Chi nhánh / Đối tác</TH>
		                        <TH align="center" width="30%" >Khách hàng</TH>
		                        <TH align="center" width="15%" >Doanh số</TH>
		                        <td align="center" width="15%" >Tổng tiền / Tổng lượng</td>
		                        <TH align="center" width="25%" >Đơn vị</TH>
		                    </TR>
									                    
		                    <%
		                    	if(nppId != null)
		                    	{
		                    		String npp_Ten = "";
		                    		for(int i = 0; i < nppId.length; i++ )
		                    		{
		                    			%>
		                    			
		                    			<TR>
		                    				<TD>
		                    					<input type="hidden" name="nppId" value="<%= nppId[i] %>" style="width: 100%" >
		                    					<% if(!npp_Ten.equals(nppTen[i])) 
		                    					  {
		                    						npp_Ten = nppTen[i];
		                    						%>
		                    						<input type="text" name="nppTen" value="<%= nppTen[i] %>" style="width: 100%; background-color: yellow;" readonly="readonly"  >
		                    					<% } else { %>
		                    						<input type="text" name="nppTen" value="" style="width: 100%" readonly="readonly"  >
		                    					<% } %>
		                    				</TD>
		                    				<TD>
		                    					<input type="hidden" name="khId" value="<%= khId[i] %>" style="width: 100%" >
		                    					<input type="text" name="khTen" value="<%= khTen[i] %>" style="width: 100%" readonly="readonly" >
		                    				</TD>
		                    				<TD>
		                    					<input type="text" name="doanhso" value="<%= doanhso[i] %>" style="width: 100%; text-align: right;" readonly="readonly" >
		                    				</TD>
		                    				<TD>
		                    					<input type="text" name="tongtien" value="<%= tongtien[i] %>" style="width: 100%; text-align: right;" readonly="readonly" >
		                    				</TD>
		                    				<TD>
		                    					<select name="donviThuong" style="text-align: center;" disabled="disabled" >
		                    						<% if(donviThuong[i].equals("0")) { %>
		                    							<option value="0" selected="selected" > % </option>
		                    						<% } else { %>
		                    							<option value="0" > % </option>
		                    						<% } %>
		                    						<% if(donviThuong[i].equals("1")) { %>
		                    							<option value="1" selected="selected" > VNĐ </option>
		                    						<% } else { %>
		                    							<option value="1" > VNĐ </option>
		                    						<% } %>
		                    						<% if(donviThuong[i].equals("2")) { %>
		                    							<option value="2" selected="selected" > Sản phẩm </option>
		                    						<% } else { %>
		                    							<option value="2" > Sản phẩm </option>
		                    						<% } %>
		                    					</select>
		                    				</TD>
		                    			</TR>
		                    			
		                    		<% }
		                    	}
		                    %>
		                    
		                    <TR>
		                        <TD align="center" colspan="15" class="tbfooter">&nbsp;</TD>
		                    </TR>
						</TABLE>
									
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
<%}%>
