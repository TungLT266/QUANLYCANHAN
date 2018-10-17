<%@page import="java.util.Hashtable"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.banggiablcnpp.imp.*" %>
<%@ page  import = "geso.traphaco.center.beans.banggiablcnpp.*" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page import="java.sql.SQLException" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<%
IBanggiablcNpp obj =(IBanggiablcNpp)session.getAttribute("bgBean");
	ResultSet dvkdList = obj.getDvkdRs();
	ResultSet bgList = obj.getBanggiaRs();
	ResultSet nppRs = obj.getNppRs();
	ResultSet sanphamRs = obj.getSanPhamList();
	Hashtable<String, String> spChietkhau = obj.getSanphamCK();
	Hashtable<String, String> spDG_SauCK = obj.getSanphamDG_SauCK();
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

<script type="text/javascript" src="../scripts/ajax.js"></script>
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>

<script>

$(function() {
 
 	$("ul.tabs").tabs("div.panes > div");
});
</script>

<SCRIPT language="JavaScript" type="text/javascript">

	function submitform()
	{
	    document.forms["khtt"].submit();
	}
	
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
	
	function Dinhdang(element)
	{
		element.value=DinhDangTien(element.value);
		if(element.value== '' ||element.value=='0' )
		{
			element.value= '';
		}
	}
	
	function FormatNumber(e)
	{
		e.value = DinhDangTien(e.value);
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
	
	function save()
	{
	  document.forms["khtt"].action.value = "save";
	  document.forms["khtt"].submit(); 
    }
	
	function submitFrom()
	{
	  document.forms["khtt"].action.value = "submit";
	  document.forms["khtt"].submit(); 
    }
	
	function changeKhachHang()
	{
		document.forms["khtt"].action.value = "changeKhachHang";
		document.forms["khtt"].submit(); 
	}
	
	function checkedAll()
	{
		 var chkAll = document.getElementById("checkAll");
		 var spIds = document.getElementsByName("nppIds");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = false;
			 }
		 }
	}
	
	function CapNhatDonGia(stt)
	{
		var dongiaGOC = document.getElementsByName("dongiaGOC");
		var ptchietkhau = document.getElementsByName("ptchietkhau");
		var dongiaSAUCK = document.getElementsByName("dongiaSAUCK");
		
		if(ptchietkhau.item(stt).value != '')
		{
			var sauCK = parseFloat(dongiaGOC.item(stt).value) * ( 1 - ( parseFloat(ptchietkhau.item(stt).value) / 100 ) );
			dongiaSAUCK.item(stt).value = sauCK;
			
			var sole = '';
			if(dongiaSAUCK.item(stt).value.indexOf(".") >= 0)
			{
				sole = dongiaSAUCK.item(stt).value.substring(dongiaSAUCK.item(stt).value.indexOf('.'), parseInt(dongiaSAUCK.item(stt).value.indexOf('.')) + 5 );
			}
			var dg = dongiaSAUCK.item(stt).value.split(".");
			dongiaSAUCK.item(stt).value = dg[0] + sole;
		}
		else
			dongiaSAUCK.item(stt).value = dongiaGOC.item(stt).value;
		
	}
	
	function CapNhatChietKhau(stt)
	{
		var dongiaGOC = document.getElementsByName("dongiaGOC");
		var ptchietkhau = document.getElementsByName("ptchietkhau");
		var dongiaSAUCK = document.getElementsByName("dongiaSAUCK");
		
		if(dongiaSAUCK.item(stt).value != '')
		{
			var ptCK = 100.0 * ( 1 - ( parseFloat(dongiaSAUCK.item(stt).value) / parseFloat(dongiaGOC.item(stt).value) ) );
			ptchietkhau.item(stt).value = ptCK;
		}
		else
			ptCK.item(stt).value = '';
		
	}
	
</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="khtt" method="post" action="../../BanggiablcNppUpdateSvl" >
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="action" value="0">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%" >
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Sản phẩm > Áp bảng giá cho KH > Tạo mới</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=  obj.getUserTen() %></TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../BanggiablcNppSvl?userId=<%= userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
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
						<LEGEND class="legendtitle">Thông báo </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= obj.getMessage() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thông tin bảng giá </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
                          <TR>
                          		<TD class="plainlabel" valign="middle" width="120px" >Tên bảng giá </TD>   
		                        <TD class="plainlabel" valign="middle" width="260px" ><input type="text" name="ten" value="<%= obj.getTen() %>"  > </TD>          
		                 		<TD class="plainlabel" valign="middle" width="120px" >Đơn vị kinh doanh </TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<select name="dvkdId" id="dvkdId"  >
										<!-- <option value=""></option> -->
										<% if (dvkdList != null)
										{
											while (dvkdList.next())
											{
												if (dvkdList.getString("pk_seq").equals(obj.getDvkdId()))
												{
												%>
													<option value="<%=dvkdList.getString("pk_seq")%>" selected="selected"><%=dvkdList.getString("diengiai")%></option>
												<% } else { %>
													<option value="<%=dvkdList.getString("pk_seq")%>"><%=dvkdList.getString("diengiai")%></option>
										<% } } dvkdList.close(); } %>
									</select>
		                        </TD>          
		                  </TR> 
		                  
		                   <TR>
		                   		<TD class="plainlabel" valign="middle" >Bảng giá</TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<select name="bgId" id="bgId" onchange="submitFrom()" >
										<option value=""></option>
										<% if (bgList != null)
										{
											while (bgList.next())
											{
												if (bgList.getString("pk_seq").equals(obj.getBanggiaId()))
												{
												%>
													<option value="<%=bgList.getString("pk_seq")%>" selected="selected"><%=bgList.getString("TEN")%></option>
												<% } else { %>
													<option value="<%=bgList.getString("pk_seq")%>"><%=bgList.getString("TEN")%></option>
										<% } } bgList.close(); } %>
									</select>
		                        </TD>
                          		<TD class="plainlabel" valign="middle"  >Từ ngày </TD>   
		                        <TD class="plainlabel" valign="middle"  ><input type="text" class="days"  name="tungay" value="<%= obj.getTungay() %>"  >  </TD>          
		                 		        
		                  </TR> 
		                  
		                   <TR>
                          		<TD class="plainlabel" valign="middle" width="120px" >Chiết khấu </TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<input type="text" name="chietkhau" value="<%= obj.getCk() %>"  maxlength="10" onkeypress="return keypress(event);"> 
		                        </TD>  
		                        <TD class="plainlabel" valign="middle" >Trạng thái </TD>   
		                        <TD class="plainlabel" valign="middle">
		                            <% if(obj.getTrangthai().equals("1")) { %>
		                            	<input type="checkbox" name = "trangthai" value="1" checked="checked" ><i> Hoạt động</i>
		                            <% } else { %>
		                            	<input type="checkbox" name = "trangthai" value="1" ><i> Hoạt động</i>
		                            <% } %>
		                        </TD>            
		                  </TR> 
		                  
		                  <TR>
		                  		<td colspan="4">
		                  		
		                  			<ul class="tabs">
			                  			<li><a href="#">Đối tác / Chi nhánh</a></li>
			                  			<li><a href="#">Chọn sản phẩm</a></li>
			                  		</ul>
		                  			
		                  			<div class="panes">
										<div>
											
											<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
												<TR class="tbheader">
													<TH width="10%">Mã đối tác</TH>
													<TH width="30%">Tên</TH>								
													<TH width="10%">Điện thoại</TH>
													<TH width="40%">Địa chỉ </TH>
													<TH width="10%">Chọn
														<input type="checkbox" id="checkAll" onchange="checkedAll();" >
													</TH>
												</TR>
												
												<% if(nppRs != null) { 
													while(nppRs.next())
													{ %>
														<tr class="tbdarkrow" >
															<td><%= nppRs.getString("maFAST") %> </td>
															<td><%= nppRs.getString("TEN") %> </td>
															<td><%= nppRs.getString("dienthoai") %> </td>
															<td><%= nppRs.getString("diachi") %> </td>
															<td align="center" >
																<% if( obj.getNppIds().contains(nppRs.getString("pk_seq")) ) { %>
																	<input type="checkbox" name="nppIds" value="<%= nppRs.getString("pk_seq") %>" checked="checked" >
																<% } else {  %> 
																	<input type="checkbox" name="nppIds" value="<%= nppRs.getString("pk_seq") %>"  >
																<% } %>
															</td>
														</tr>
														
													<%  } nppRs.close();
												} %>
												
											</TABLE>
											
										</div>
										
										<div>
			                  			
				                  			<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
												<TR class="tbheader">
												<TH width="10%">Mã sản phẩm </TH>
													<TH width="10%">Mã sản phẩm cũ</TH>
													<TH width="40%">Tên sản phẩm</TH>								
													<TH width="10%">Đơn vị</TH>
													<TH width="10%">Giá bán lẻ</TH>
													<TH width="10%">Chiết khấu</TH>
													<TH width="10%">Giá bán</TH>
												</TR>
												
												<% if(sanphamRs != null) { 
													int stt = 0;
													while(sanphamRs.next())
													{ %>
														<tr class="tbdarkrow" >
															<td>
																<%= sanphamRs.getString("ma") %> 
															</td>
																<td>
																<%= sanphamRs.getString("ma_fast") %> 
															</td>
															<td><%= sanphamRs.getString("TEN") %> </td>
															<td><%= sanphamRs.getString("donvi") %> </td>
															<td style="text-align: right;"><%= sanphamRs.getString("gblc") %></td>
															<td>
																<input type="hidden" name="spIds" value="<%= sanphamRs.getString("pk_seq") %>" > 
																<input type="hidden" name="dongiaGOC" value="<%= sanphamRs.getString("gblc") %>" > 
																<% if(spChietkhau.get(sanphamRs.getString("pk_seq")) != null ) { %>
																	<input type="text" name="ptchietkhau" style="width: 100%; text-align: right;" value="<%= spChietkhau.get(sanphamRs.getString("pk_seq")) %>" onkeypress="return keypress(event);" onkeyup="CapNhatDonGia(<%= stt %>);" > 
																<% } else { %>
																	<input type="text" name="ptchietkhau" style="width: 100%; text-align: right;" value="" onkeypress="return keypress(event);" onkeyup="CapNhatDonGia(<%= stt %>);" > 
																<% } %>
															</td>
															<td>
																<%-- <input type="text" name="dongiaSAUCK" style="width: 100%; text-align: right;" value="<%= sanphamRs.getString("giasauCK") %>" onkeyup="CapNhatChietKhau(<%= stt %>);" >  --%>
																<% if(spDG_SauCK.get(sanphamRs.getString("pk_seq")) != null ) { %>
																	<input type="text" name="dongiaSAUCK" style="width: 100%; text-align: right;" value="<%= spDG_SauCK.get(sanphamRs.getString("pk_seq")) %>" onkeypress="return keypress(event);" onkeyup="CapNhatChietKhau(<%= stt %>);" > 
																<% } else { %>
																	<input type="text" name="dongiaSAUCK" style="width: 100%; text-align: right;" value="<%= sanphamRs.getString("gblc") %>" onkeypress="return keypress(event);" onkeyup="CapNhatChietKhau(<%= stt %>);" > 
																<% } %>
															</td>
														</tr>
														
													<% stt++; } sanphamRs.close();
												} %>
												
											</TABLE>
										</div>
										
									</div>
		                  		</td>
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
