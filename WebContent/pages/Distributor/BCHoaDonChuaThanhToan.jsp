<%@page import="geso.traphaco.distributor.beans.reports.IBCHoaDonChuaThanhToan"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "geso.traphaco.distributor.beans.reports.IBCHoaDonChuaThanhToan" %>
<%@ page  import = "geso.traphaco.distributor.beans.reports.imp.BCHoaDonChuaThanhToan" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<% 	
	IBCHoaDonChuaThanhToan bccn = (IBCHoaDonChuaThanhToan)session.getAttribute("bccn"); 
    ResultSet nvbhRs = bccn.getNvbhRs();
    ResultSet nvgnRs = bccn.getNvgnRs();
    ResultSet khRs = bccn.getKhRs();
    ResultSet dtRs = bccn.getDtRs();

    String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	String loi=(String)session.getAttribute("loi");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />

<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});	
</script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2(); });
     
</script>

<script type="text/javascript">
function submitform()
{
	if (document.forms["rpForm"]["tuNgay"].value.length == 0) {
		setErrors("Vui lòng chọn ngày bắt đầu");
		return;
	}
	if (document.forms["rpForm"]["denNgay"].value.length == 0) {
		setErrors("Vui lòng chọn ngày kết thúc");
		return;
	}			
	var fieldShow = document.getElementsByName("fieldsHien");		
	for ( var i = 0; i < fieldShow.length; ++i) {
		fieldShow.item(i).checked = true;
	}		
	
	document.forms['rpForm'].action.value="excel";
	document.forms['rpForm'].submit();
	reset();
}

function setErrors(errorMsg) {
	var errors = document.getElementById("errors");
	errors.value = errorMsg;
}

function reset() {
	var fieldShow = document.getElementsByName("fieldsHien");		
	for ( var i = 0; i < fieldShow.length; ++i) {
		fieldShow.item(i).checked = false;
	}		
};
</SCRIPT>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="rpForm" method="post" action="../../BCHoaDonChuaThanhToanSvl">
<input type="hidden" name="userId" value= <%= userId %> >
<input type="hidden" name="action" value='1'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" cellpadding="0" cellspacing="1">		
				<TR>
					<TD width="100%" align="left">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
						   <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý công nợ &gt; Báo cáo &gt; Hóa đơn chưa thanh toán </TD>
   
						   <TD colspan="2" align="right" class="tbnavigation">Chào mừng Bạn <%=userTen %>&nbsp;</TD>
						  </tr>
 					  </table>					</TD>
				</TR>
				<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
	    				<textarea id="errors" readonly="readonly" name="errors" rows="1" style="width: 100% ; color:#F00 ; font-weight:bold">
						<%= bccn.getMsg() %>
					</textarea>
						</FIELDSET>
				   </TD>
				</tr>	
				<TR>
					<TD>
					<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="100%" align="center" >
							<FIELDSET>
							  <LEGEND class="legendtitle">Thời gian xuất báo cáo</LEGEND>
							<TABLE  width="100%" cellpadding="6" cellspacing="0">
								<TR>
								  	<TD width="19%" class="plainlabel" >Từ ngày</TD>
								  	<TD class="plainlabel" >
										<TABLE cellpadding="0" cellspacing="0">
											<TR><TD>
												<input type="text" class="days" name="tuNgay" size="20" value = "<%=bccn.getTuNgay()%>" >
												</TD>
												<TD>		   										
												</TD>
                                    		</TR>
										</TABLE>									</TD>
								<TR>
									<TD class="plainlabel" >Đến ngày </TD>
								  	<TD class="plainlabel" >
							  			<TABLE cellpadding="0" cellspacing="0">
							  				<TR>
							  					<TD>
													<input type="text"  class="days" name="denNgay" size="20" value = "<%=bccn.getDenNgay()%>" >												</TD>
												<TD>                                        		</TD>
                                     		</TR>
										</TABLE>									</TD>
								</TR>
							    <TR>
							     <TR>
                      <TD class="plainlabel">Tìm trình dược viên</TD>
                  		<TD class="plainlabel" colspan="3">                        
                  		<select name="nvbhId" id="nvbhId" style="width: 500px" multiple="multiple"  >
                            <option value=""> </option>
                        	<%
                        		if(nvbhRs != null)
                        		{
                        			try
                        			{
                        			while(nvbhRs.next())
                        			{  
                        			if( bccn.getNvbhIds().contains(nvbhRs.getString("pk_seq")) ){ %>
                        				<option value="<%= nvbhRs.getString("pk_seq") %>" selected="selected" ><%= nvbhRs.getString("Ten") %></option>
                        			<%}else { %>
                        				<option value="<%= nvbhRs.getString("pk_seq") %>" ><%= nvbhRs.getString("Ten") %></option>
                        		 <% } } nvbhRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD>                     
                 </TR>   
                  <TR>
                      <TD class="plainlabel">Tìm nhân viên giao nhận</TD>
                  		<TD class="plainlabel" colspan="3">                        
                  		<select name="nvgnId" id="nvgnId" style="width: 500px" multiple="multiple"  >
                            
                        	<%
                        		if(nvgnRs != null)
                        		{
  			    			try
                        			{
                        			while(nvgnRs.next())
                        			{  
                        			if( bccn.getNvgnIds().contains(nvgnRs.getString("pk_seq")) ){ %>
                        				<option value="<%= nvgnRs.getString("pk_seq") %>" selected="selected" ><%= nvgnRs.getString("Ten") %></option>
                        			<%}else { %>
                        				<option value="<%= nvgnRs.getString("pk_seq") %>" ><%= nvgnRs.getString("Ten") %></option>
                        		 <% } } nvgnRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD>                     
                 </TR>
                
                 <TR>
                    <TD class="plainlabel" >Tìm đối tác</TD>
                  		<TD class="plainlabel" colspan="3">                        
                  		<select name="dtId" style="width: 500px" multiple="multiple" >
                           <option value=""> </option>
                           
                        	<%
                        		if(dtRs != null)
                        		{
                        			try
                        			{
                        			while(dtRs.next())
                        			{  
                        			if( bccn.getDtIds().contains(dtRs.getString("pk_seq")) ){ %>
                        				<option value="<%= dtRs.getString("pk_seq") %>" selected="selected" ><%= dtRs.getString("nppTen") %></option>
                        			<%}else { %>
                        				<option value="<%= dtRs.getString("pk_seq") %>" ><%= dtRs.getString("nppTen") %></option>
                        		 <% } } dtRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD>
                  </TR>
                  <TR>    
                       <TD class="plainlabel">Tìm khách hàng</TD>
                  		<TD class="plainlabel" colspan="3">                        
                  		<select name="khId" style="width: 500px" multiple="multiple" >
                           <option value=""> </option>
                        	<%
                        		if(khRs != null)
                        		{
                        			try
                        			{
                        			while(khRs.next())
                        			{  
                        			if( bccn.getKhIds().contains(khRs.getString("pk_seq")) ){ %>
                        				<option value="<%= khRs.getString("pk_seq") %>" selected="selected" ><%= khRs.getString("khTen") %></option>
                        			<%}else { %>
                        				<option value="<%= khRs.getString("pk_seq") %>" ><%= khRs.getString("khTen") %></option>
                        		 <% } } khRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD>
                    </TR>
									<TD colspan="2" class="plainlabel">
									<a class="button2" href="javascript:submitform()" >
									<img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel</a>&nbsp;&nbsp;&nbsp;&nbsp;
	
									
	                                  </TD>
								</TR>
							</TABLE>
							</FIELDSET>						</TD>
						</TR>
					</TABLE>					</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
</TABLE>
</form>
</BODY>
</HTML>
<%

if(bccn!=null)
{
	bccn.dbClose();
}

	}%>