<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.lapkehoach.imp.*" %>
<%@ page import="geso.traphaco.erp.beans.lapkehoach.*" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@page import="java.sql.SQLException" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.text.DecimalFormat" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<%
 	IErpLenhsanxuatdk obj =(IErpLenhsanxuatdk)session.getAttribute("lsxBean");
	ResultSet khoRs = obj.getKhoTTRs() ;
	
	NumberFormat formatter = new DecimalFormat("#,###,###"); 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
	<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	
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
	
	function isNumberKey(evt)
	{
	   var charCode = (evt.which) ? evt.which : event.keyCode;
	   if (charCode > 31 && (charCode < 48 || charCode > 57) && charCode!='46')
	      return false;
	
	   return true;
	}
	
	function save()
	{
	
	  document.forms["khtt"].action.value = "save";
	  document.forms["khtt"].submit(); 
    }
	
	function XacNhanChuyen(PlainId)
	{
		var kbsxId = document.getElementById('KbsxId' + PlainId).value;
		if(kbsxId == '-1')
		{
			alert('Sản phẩm này chưa có kịch bản sản xuất');
			return;
		}
		
		var xmlhttp;
		if (PlainId == "")
		{
		   alert('Vui lòng chọn Plain Order');
		   return;
		}
		
		if (window.XMLHttpRequest)
		{
		   xmlhttp = new XMLHttpRequest();
		}
		else
		{
		   xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange=function()
		{
		   if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
		   {
			   if( xmlhttp.responseText.length > 20 )
			   {
				   alert(xmlhttp.responseText);
			   }
			   else
			   {
				   document.getElementById("Div" + PlainId).innerHTML = '<input type="text" style="width: 100%; color:red;" value="' + PlainId + '" readonly="readonly"  >';
				   document.getElementById("Div_" + PlainId).innerHTML = xmlhttp.responseText;
			   }
		   }
		}
		
		//xmlhttp.open("POST", "../../ErpLenhsanxuatAjaxSvl?id=" + PlainId, true);
		xmlhttp.open("GET", "../../ErpLenhsanxuatdukienSvl?id=" + PlainId + "&kbsxId=" + kbsxId + "&task=chuyenthanhProc", true);
		xmlhttp.send();
		
	}
	
	

</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="khtt" method="post" action="" >
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="id" value='<%= obj.getId() %>' >
<input type="hidden" name="action" value="0">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý cung ứng > Lập kế hoạch > Lệnh sản xuất dự kiến > Hiển thị </TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="3" cellspacing="0">
				<TR>
					<TD>
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
							<TR class="tbdarkrow">
								<TD width="30" align="left"><A href="../../ErpLenhsanxuatdukienSvl?userId=<%= userId %>"><img src="../images/Back30.png" alt="Quay ve" title="Quay ve"
										border="1" longdesc="Quay ve" style="border-style: outset"></A></TD>
								<TD width="2" align="left"></TD>
							</TR>
						</TABLE>
					</TD>
				</TR>
			</TABLE>
			
		
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Thông báo </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Lệnh sản xuất dự kiến </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
						 <TR>		                  
		                        <TD class="plainlabel" valign="middle" >ID </TD>
		                        <TD class="plainlabel" valign="middle" colspan = 3>
		                        	<input type="text" name="ID" value="<%= obj.getId() %>" readonly="readonly" style="width:100px"> 
		                        </TD>                
						 </TR>

		                  <TR>
		                        <TD class="plainlabel" valign="middle" >Mã sản phẩm </TD>
		                        <TD class="plainlabel" valign="middle" >
		                        	<input type="text" name="masp" value="<%= obj.getMasp() %>" readonly="readonly" > 
		                        </TD>

		                        <TD class="plainlabel" valign="left" width = 5% >Đơn vị </TD>   
		                        <TD class="plainlabel" valign="middle" width = "60%">
		                        	<input type="text" name="donvi" value="<%= obj.getDonvi() %>" readonly="readonly"  style="width:100px"> 
		                        </TD>                
		                                        
						 </TR>
						 <TR>		                  <TR>
		                        <TD class="plainlabel" valign="middle" >Tên sản phẩm </TD>
		                        <TD class="plainlabel" valign="middle" colspan = 3>
		                        	<input type="text" name="tensp" value="<%= obj.getTensp() %>" readonly="readonly" style="width:300px"> 
		                        </TD>                
						 </TR>
						 
						 <TR>
		                  </TR> 
                          <TR>
		                        <TD class="plainlabel" valign="middle" width="150px" >Số lượng</TD>
		                        <TD class="plainlabel" valign="middle" colspan="3" >
									<INPUT type = "text" name = "soluong" value = <%= obj.getSoluong() %> style="width:100px" onkeypress="return keypress(event)">
		                        </TD>                        
		                  </TR> 

                          <TR>
		                        <TD class="plainlabel" valign="middle" width="150px" >Ngày dự kiến hoàn tất</TD>
		                        <TD class="plainlabel" valign="middle" colspan="3" >
									<input class="days" type="text" name="ngay" value="<%= obj.getNgay() %>" style="width:100px" >		                        
		                        </TD>                        
		                  </TR> 

                          <TR>
		                        <TD class="plainlabel" valign="middle" width="150px" >Trung tâm phân phối</TD>
		                        <TD class="plainlabel" valign="middle" colspan="3" >
		                            <select name="khoId" disabled=disabled>
		                            	<%
			                        		if(khoRs != null)
			                        		{
			                        			while(khoRs.next())
			                        			{  
			                        			if( khoRs.getString("id").equals(obj.getKhoId())){ %>
			                        				<option value="<%= khoRs.getString("id") %>" selected="selected" ><%= khoRs.getString("ten") %></option>
			                        			<%}else { %>
			                        				<option value="<%= khoRs.getString("id") %>" ><%= khoRs.getString("ten") %></option>
			                        		 <% } } 
			                        			khoRs.close();
			                        		}
			                        	%>
		                            </select>
		                        </TD>                        
		                  </TR> 

                          <TR>
		                        <TD class="plainlabel" valign="middle" width="150px" >Hoạt động</TD>
		                        <TD class="plainlabel" valign="middle" colspan="3" >
								<% if (obj.getTrangthai().equals("1")) {%>
									<INPUT type = "checkbox" name = "trangthai" value = "1" checked>
								<%}else{ %>
									<INPUT type = "checkbox" name = "trangthai" value = "1" >
								<%} %>
		                        </TD>                        
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
