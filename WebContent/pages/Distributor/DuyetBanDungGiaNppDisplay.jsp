<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="geso.traphaco.distributor.beans.duyetbandunggia.imp.*" %>
<%@page import="geso.traphaco.distributor.beans.duyetbandunggia.*" %>
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
 	IDuyetbandunggiaNpp obj =(IDuyetbandunggiaNpp)session.getAttribute("tctskuBean");
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
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
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
		
		function save()
		{
		  /* var thang = document.getElementById("thang").value;
		  var nam = document.getElementById("nam").value;
	
		  if( thang == '' )
		  {
			  alert("Bạn phải nhập từ ngày");
			  return;
		  }
		  	
		  if( nam == '' )
		  {
			  alert("Bạn phải nhập đến ngày");
			  return;
		  } */
		  
		  document.forms["tctsku"].action.value = "save";
		  document.forms["tctsku"].submit(); 
	  }
	
		function submitform()
		{
			document.forms["tctsku"].action.value = "submit";
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
	
	function SelectALL2()
	{
		var checkAll = document.getElementById("checkAll2");
		var spIds = document.getElementsByName("kh_QuyIds"); 
		
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
	function SelectALL3()
	{
		var checkAll = document.getElementById("checkAll3");
		var spIds = document.getElementsByName("kh_ckuh"); 
		
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
	
	function exportEx()
	{
		document.forms["tctsku"].action.value = "export";
	  	document.forms["tctsku"].submit(); 
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
<form name ="tctsku" method="post" action="../../DuyetbandunggiaNppUpdateSvl" >
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="action" value="0">
<input type="hidden" name="nppId" value='<%= obj.getNppId() %>' >
<input type="hidden" name="id" value='<%= obj.getId() %>' >
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
							<TD width="30" align="left"><A href="../../DuyetbandunggiaNppSvl?userId=<%=userId%>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD width="30" align="left" >
						    <div id="btnSave">
						    <A href="javascript: save()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A>
						    </div>
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
						<LEGEND class="legendtitle" style="color:black">Duyệt bán đúng giá </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
							<TR>
								<TD class="plainlabel" width="120px"  >Tháng<FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel" colspan="3">
									<select name="thang" id="thang" style="width: 200px;"   >
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
						  <TR style="display: none;" >
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
						  
						  <TR style="display: none;" >
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
						  <TR>
						  	  	<TD class="plainlabel"></TD>
						  	  	<TD class="plainlabel" colspan="3">
						  	  		<%-- <% if(obj.getDuyetdunggia().equals("1")) { %>
						  	  			<input type="checkbox" name="duyetdunggia" value="1" checked="checked" ><i>Duyệt bán đúng giá</i> 
						  	  		<% } else { %>
						  	  			<input type="checkbox" name="duyetdunggia" value="1" ><i>Duyệt bán đúng giá</i> 
						  	  		<% } %>
						  	  		&nbsp;&nbsp;&nbsp; --%>
						  	  		<% if(obj.getKhongtinhtienthuve().equals("1")) { %>
						  	  			<input type="checkbox" name="khongtinhthuve" value="1" checked="checked" disabled="disabled" ><i>Không tính tiền thu về</i> 
						  	  		<% } else { %>
						  	  			<input type="checkbox" name="khongtinhthuve" value="1" ><i>Không tính tiền thu về</i> 
						  	  		<% } %>
						  	  		&nbsp;&nbsp;&nbsp;
						  	  		<% if(obj.getKhongtinhdoanhso().equals("1")) { %>
						  	  			<input type="checkbox" name="khongtinhdoanhso" value="0" disabled="disabled" ><i>Không tính doanh số</i> 
						  	  		<% } else { %>
						  	  			<input type="checkbox" name="khongtinhdoanhso"  value="0" disabled="disabled" ><i>Không tính doanh số</i> 
						  	  		<% } %>
						  	  	</TD>
						  </TR>
							  <tr class="plainlabel"> 
	                             <td colspan="4" > 
	                           		<a class="button2" href="javascript:exportEx()">
										<img style="top: -4px;" src="../images/button.png" alt="">Xuất File Khách Hàng Không Duyệt Bán Đúng Gía</a>&nbsp;&nbsp;&nbsp;&nbsp;	
	                             </td> 
	                         </tr>
						</TABLE>
						
						<hr />
			  			<TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px">
	                    
		                    <TR class="tbheader">
		                    	 <td align="center" width="10%">STT</td>
		                        <td align="center" width="10%">Mã khách hàng</td>
		                         <td align="center" width="10%">Mã hợp đồng</td>
		                    	<td align="center" width="20%">Họ tên</td>
		                    	<td align="center" width="10%">Người mua hàng</td>
		                    	<td align="center" width="20%">Địa chỉ</td>
		                    	<td align="center" width="10%">Chọn <input type="checkbox" name="checkAll" id="checkAll" onchange="SelectALL();" > </td>
		                    	<% 
		                    					String style_display1="display: none";
		                    					if(obj.getThang().equals("3")|| obj.getThang().equals("6") || obj.getThang().equals("9") || obj.getThang().equals("12")){
		                    						style_display1 ="";
		                    					}
		                    				%>
		                    	<td align="center" width="10%" id="choncheck" style="<%=style_display1%>">Hưởng CK quý 
		                    	<input type="checkbox" name="checkAll2" id="checkAll2" onchange="SelectALL2();" >
		                    	 </td>
		                    	 	<td align="center" width="10%" id="choncheck" ">CK Ủng hộ 
		                    	<input type="checkbox" name="checkAll3" id="checkAll3" onchange="SelectALL3();" >
		                    	 </td>
		                    </TR>
		                    
		                    <%
		                    	if(khRs != null)
		                    	{
		                    		int m=0;
		                    		while(khRs.next())
		                    		{
		                    			m++;
		                    			%>
		                    			
		                    			<tr>
		                    				<td><%=m %></td>
		                    				<td>
		                    					<input type="text" value="<%= khRs.getString("maFAST") %>" style="width: 100%;" readonly="readonly" >
		                    					<input type="hidden" value="<%= khRs.getString("pk_seq") %>" style="width: 100%;" readonly="readonly" >
		                    				</td>
		                    				<td>	<input type="text" value="<%= khRs.getString("mahd") %>" style="width: 100%;" readonly="readonly" ></td>
		                    		
		                    				<td><input type="text" value="<%= khRs.getString("ten") %>" style="width: 100%;" readonly="readonly" ></td>
		                    				<td><input type="text" value="<%= khRs.getString("tenkyhd") %>" style="width: 100%;" readonly="readonly" ></td>
		                    				<td><input type="text" value="<%= khRs.getString("diachi") %>" style="width: 100%;" readonly="readonly" ></td>
		                    				<td align="center">
		                    					<% if(khRs.getInt("dahuongKM") <= 0 ) { %>
			                    					<% if(obj.getKhIds().contains(khRs.getString("pk_seq"))) { %>
			                    						<input type="checkbox" name="khIds" value="<%= khRs.getString("pk_seq") %>" checked="checked" >
			                    					<% } else { %>
			                    						<input type="checkbox" name="khIds" value="<%= khRs.getString("pk_seq") %>"  >
			                    					<% } %>
		                    					<% } else { %>
		                    						<input type="checkbox" name="khIds" value="<%= khRs.getString("pk_seq") %>" checked="checked" onclick="return false;" >
		                    					<% } %>
		                    				</td>
		                    				
		                    				
		                    				
		                    				<% 
		                    					String style_display="display: none";
		                    					if(obj.getThang().equals("3")|| obj.getThang().equals("6") || obj.getThang().equals("9") || obj.getThang().equals("12")){
		                    						style_display ="";
		                    					}
		                    				%>
		                    				
		                    				
		                    				<td align="center" name="choncheckck" style="<%= style_display %>">
		                    					<% if(obj.getKh_QuyIds().contains(khRs.getString("pk_seq"))) { %>
		                    						<input type="checkbox" name="kh_QuyIds" value="<%= khRs.getString("pk_seq") %>" checked="checked" >
		                    					<% } else { %>
		                    						<input type="checkbox" name="kh_QuyIds" value="<%= khRs.getString("pk_seq") %>"  >
		                    					<% } %>
		                    				</td>
		                    				
		                    				
		                    				<td align="center" >
		                    					<% if(obj.getKh_ckuh().contains(khRs.getString("pk_seq"))) { %>
		                    						<input type="checkbox" name="kh_ckuh" value="<%= khRs.getString("pk_seq") %>" checked="checked" >
		                    					<% } else { %>
		                    						<input type="checkbox" name="kh_ckuh" value="<%= khRs.getString("pk_seq") %>"  >
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
<!-- <SCRIPT language="JavaScript" type="text/javascript">
	var e = document.getElementById("thang");
	var thangvalue = e.options[e.selectedIndex].text;
	if(thangvalue ==3 || thangvalue==6 || thangvalue==9 || thangvalue==12){
		document.getElementById("choncheck").style.display = "block";
		document.getElementsByName("choncheckck").style.display = "block";
		
 </SCRIPT> -->
 <script type="text/javascript">
 	 CK_hienthi(); 
 </script>
 
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
