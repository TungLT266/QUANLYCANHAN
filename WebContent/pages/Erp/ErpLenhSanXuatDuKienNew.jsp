<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.lapkehoach.imp.*" %>
<%@ page import="geso.traphaco.erp.beans.lapkehoach.*" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
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
 	IErpLenhsanxuatdk obj =(IErpLenhsanxuatdk)session.getAttribute("lsxBean");
	ResultSet khoRs = obj.getKhoTTRs();
	ResultSet spRs = obj.getSpRs();
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
	
	function save()
	{
	
	  document.forms["khtt"].action.value = "save";
	  document.forms["khtt"].submit(); 
    }
	
	function sellectAll()
	 {
		 var chkAll = document.getElementById("chkAll");
		 var spIds = document.getElementsByName("spIds");
		 
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

</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="khtt" method="post" action="../../ErpLenhsanxuatdukienUpdateSvl" >
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="id" value='<%= obj.getId() %>' >
<input type="hidden" name="kehoachId" value='<%= obj.getKehoachId() %>' >
<input type="hidden" name="action" value="0">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý cung ứng > Lập kế hoạch > Kế hoạch tổng thể > Lệnh sản xuất dự kiến</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../ErpLenhsanxuatdkSvl?userId=<%= userId%>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
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
				
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thông tin lệnh sản xuất dự kiến </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
                           <TR>
		                        <TD class="plainlabel" valign="middle" width="150px" >Trung tâm phân phối</TD>
		                        <TD class="plainlabel" valign="middle" colspan="3" >
		                            <select name="khoIds" onchange="submitform();" >
		                            	<option value=""></option>
		                            	<%
			                        		if(khoRs != null)
			                        		{
			                        			while(khoRs.next())
			                        			{  
			                        			if( khoRs.getString("pk_seq").equals(obj.getKhoIds())){ %>
			                        				<option value="<%= khoRs.getString("pk_seq") %>" selected="selected" ><%= khoRs.getString("ten") %></option>
			                        			<%}else { %>
			                        				<option value="<%= khoRs.getString("pk_seq") %>" ><%= khoRs.getString("ten") %></option>
			                        		 <% } } khoRs.close();
			                        		}
			                        	%>
		                            </select>
		                        </TD>                        
		                  </TR> 
		                  <TR>
		                        <TD class="plainlabel" valign="middle" >Sản phẩm </TD>
		                        <TD class="plainlabel" valign="middle" width="220px" >
		                            <select name="spIds" onchange="submitform();" >
		                            	<option value=""></option>
		                            	<%
			                        		if(spRs != null)
			                        		{
			                        			while(spRs.next())
			                        			{  
			                        			if( spRs.getString("pk_seq").equals(obj.getSpIds())){ %>
			                        				<option value="<%= spRs.getString("pk_seq") %>" selected="selected" ><%= spRs.getString("ten") %></option>
			                        			<%}else { %>
			                        				<option value="<%= spRs.getString("pk_seq") %>" ><%= spRs.getString("ten") %></option>
			                        		 <% } } spRs.close();
			                        		}
			                        	%>
		                            </select>
		                        </TD>   
		                        <TD class="plainlabel" valign="middle" width="100px" >Đơn vị </TD>   
		                        <TD class="plainlabel" valign="middle" >
		                        	<input type="text" name="donvi" value="<%= obj.getDonvi() %>" readonly="readonly" > 
		                        </TD>                
		                  </TR> 
                          <TR>
                          		<TD class="plainlabel" valign="middle" width="100px" >Trung bình bán / ngày </TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<input type="text" name="trungbinhban" value="<%= obj.getTbbanNgay() %>" style="text-align: right;" > 
		                        </TD>  
		                        <TD class="plainlabel" valign="middle" width="100px" >Tồn kho an toàn </TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<input type="text" name="tonantoan" value="<%= obj.getTonkhoantoan() %>" style="text-align: right;" >
		                        </TD>              
		                  </TR> 
		                  <TR>
		                  		<TD class="plainlabel" colspan="4" >
		                  			<a class="button2" href="javascript:clearform()">
										<img style="top: -4px;" src="../images/button.png" alt="">Cập nhật</a>&nbsp;&nbsp;&nbsp;&nbsp;	
		                  		 </TD>
		                  </TR>
                          
						</TABLE>
						<hr />
									
						</FIELDSET>						
					</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
	</TABLE>
</form>
<script type="text/javascript">
	dropdowncontent.init('spId', "right-bottom", 300, "click");
</script>
</BODY>
</HTML>
<%}%>
