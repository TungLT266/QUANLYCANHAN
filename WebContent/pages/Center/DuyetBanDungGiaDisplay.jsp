<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="geso.traphaco.center.beans.tieuchithuong.imp.*" %>
<%@page import="geso.traphaco.center.beans.tieuchithuong.*" %>
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
 	IDuyetbandunggia obj =(IDuyetbandunggia)session.getAttribute("tctskuBean");
	ResultSet vungRs = obj.getVungRs();
	ResultSet kvRs = obj.getKhuvucRs();
	ResultSet nppRs = obj.getNppRs();
	ResultSet khRs = obj.getKhRs();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
	<TITLE>OneOne - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
	<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
	
	<script type="text/javascript" src="../scripts/ajax.js"></script>
	<!-- <LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
	<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css"> -->
	
	
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
	
	function SelectALL()
	{
		var checkAll = document.getElementById("checkAll");
		var spIds = document.getElementsByName("khIds");
		
		if(checkAll.checked == true)
		{
			for(i = 0; i < spIds.length; i++)
				spIds.item(i).checked = true;
		}
		else
		{
			for(i = 0; i < spIds.length; i++)
				spIds.item(i).checked = false;
		}
		
	}

</SCRIPT>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		$(".select2").select2();
		
	});
</script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="tctsku" method="post" action="../../DuyetbandunggiaUpdateSvl" >
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
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý khuyến mại > Tích lũy > Duyệt bán đúng giá > Hiển thị</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../DuyetbandunggiaSvl?userId=<%=userId%>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    
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
						<LEGEND class="legendtitle" style="color:black">Duyệt bán đúng giá </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
							<TR>
								<TD class="plainlabel" width="120px" >Tháng<FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel" colspan="3">
									<select name="thang" class="select2" style="width: 200px;"  >
										<%
											for(int i = 1; i <= 12; i++)
											{
												if(i == Integer.parseInt(obj.getThang()) )
												{
													%>
													<option value="<%= i %>" selected="selected" ><%= i %></option>
												<% } else { %>
													<option value="<%= i %>" ><%= i %></option>
												<% }
											}
										%>
									</select>
								</TD>
							</TR>
							<TR>
						  	  	<TD class="plainlabel" >Năm<FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel" colspan="3">
									<input type="text" name="nam" id="nam" value="<%= obj.getNam() %>" readonly="readonly" >
								</TD>
						  </TR>
						  <TR>
								<TD class="plainlabel" >Vùng</TD>
								<TD class="plainlabel" width="230px" >
									<select name="vungId" style="width: 200px;" onchange="submitform();"  class="select2" >
										<option value="" ></option>
										<%
											if(vungRs != null)
											{
												while(vungRs.next())
												{
													if(obj.getVungIds().indexOf(vungRs.getString("pk_seq")) >= 0 )
													{
														%>
														<option value="<%= vungRs.getString("pk_seq") %>" selected="selected"  ><%= vungRs.getString("ten") %></option>
													<% } else { %>
														<option value="<%= vungRs.getString("pk_seq") %>"  ><%= vungRs.getString("ten") %></option>
													<%}
												}
												vungRs.close();
											}
										%>							
									</select>
								</TD>
								<TD class="plainlabel" width="90px" >Khu vực</TD>
								<TD class="plainlabel" >
									<select name="khuvucId" style="width: 200px;" onchange="submitform();" class="select2" >
										<option value="" ></option>
										<%
											if(kvRs != null)
											{
												while(kvRs.next())
												{
													if(obj.getKhuvucIds().indexOf(kvRs.getString("pk_seq")) >= 0 )
													{
														%>
														<option value="<%= kvRs.getString("pk_seq") %>" selected="selected"  ><%= kvRs.getString("ten") %></option>
													<% } else { %>
														<option value="<%= kvRs.getString("pk_seq") %>"  ><%= kvRs.getString("ten") %></option>
													<%}
												}
												kvRs.close();
											}
										%>							
									</select>
								</TD>
						  </TR>
						  
						  <TR>
								<TD class="plainlabel" >Chi nhánh / Đối tác</TD>
								<TD class="plainlabel" colspan="3" >
									<select name="nppIds" style="width: 600px;" onchange="submitform();" class="select2" multiple="multiple" >
										<option value="" ></option>
										<%
											if(nppRs != null)
											{
												while(nppRs.next())
												{
													if(obj.getNppIds().indexOf(nppRs.getString("pk_seq")) >= 0 )
													{
														%>
														<option value="<%= nppRs.getString("pk_seq") %>" selected="selected"  ><%= nppRs.getString("ten") %></option>
													<% } else { %>
														<option value="<%= nppRs.getString("pk_seq") %>"  ><%= nppRs.getString("ten") %></option>
													<%}
												}
												nppRs.close();
											}
										%>							
									</select>
								</TD>
						 </TR>
						 <TR>
						  	  	<TD class="plainlabel">Diễn giải</TD>
						  	  	<TD class="plainlabel" colspan="3">
						  	  		<input type="text" name="diengiai" id="diengiai" value="<%= obj.getDiengiai() %>" style="width: 600px;" > 
						  	  	</TD>
						  </TR>
						</TABLE>
						
						<hr />
			  			<TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px">
	                    
		                    <TR class="tbheader">
		                        <td align="center" width="10%">Mã khách hàng</td>
		                    	<td align="center" width="20%">Họ tên</td>
		                    	<td align="center" width="20%">Điện thoại</td>
		                    	<td align="center" width="40%">Địa chỉ</td>
		                    	<td align="center" width="10%">Chọn <input type="checkbox" name="checkAll" id="checkAll" onchange="SelectALL();" > </td>
		                    </TR>
		                    
		                    <%
		                    	if(khRs != null)
		                    	{
		                    		while(khRs.next())
		                    		{
		                    			%>
		                    			
		                    			<tr>
		                    				
		                    				<td><input type="text" value="<%= khRs.getString("pk_seq") %>" style="width: 100%;" readonly="readonly" ></td>
		                    				<td><input type="text" value="<%= khRs.getString("ten") %>" style="width: 100%;" readonly="readonly" ></td>
		                    				<td><input type="text" value="<%= khRs.getString("dienthoai") %>" style="width: 100%;" readonly="readonly" ></td>
		                    				<td><input type="text" value="<%= khRs.getString("diachi") %>" style="width: 100%;" readonly="readonly" ></td>
		                    				<td align="center">
		                    					<% if(obj.getKhIds().contains(khRs.getString("pk_seq"))) { %>
		                    						<input type="checkbox" name="khIds" value="<%= khRs.getString("pk_seq") %>" checked="checked" >
		                    					<% } else { %>
		                    						<input type="checkbox" name="khIds" value="<%= khRs.getString("pk_seq") %>"  >
		                    					<% } %>
		                    				</td>
		                    				
		                    			</tr>
		                    			
		                    		<% }
		                    		khRs.close();
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

<%
	if(nppRs != null)
		nppRs.close();
	if(khRs != null)
		khRs.close();
	if(vungRs != null)
		vungRs.close();
	if(kvRs != null)
		kvRs.close();
%>

</HTML>
<%}%>
