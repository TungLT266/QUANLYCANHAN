<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "geso.traphaco.distributor.beans.reports.IBKTienThuTrongNgay" %>
<%@ page  import = "geso.traphaco.distributor.beans.reports.imp.BKTienThuTrongNgay" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<% 	IBKTienThuTrongNgay bctt = (IBKTienThuTrongNgay)session.getAttribute("bc"); 
    String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	String loi=(String)session.getAttribute("loi");

	Utility util = (Utility) session.getAttribute("util");
	
    ResultSet nvbhRs = bctt.getNvbhRs();
    ResultSet nvgnRs = bctt.getNvgnRs();
    ResultSet khRs = bctt.getKhRs();
    ResultSet phieunoptienRs= bctt.getPhieuNopTienRs();
    
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
		$(document).ready(function() {		
			$( ".days" ).datepicker({			    
					changeMonth: true,
					changeYear: true				
			});
           
        }); 
		
		
    </script>

<script type="text/javascript">

function submitform1()
{	
	document.forms['rpForm'].action.value="excel";
	document.forms['rpForm'].submit();
}


function submitform()
{
	document.forms['rpForm'].dataerror.value="";
	document.forms['rpForm'].submit();
}

</SCRIPT>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2(); });
     
</script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="rpForm" method="post" action="../../BKTienThuTrongNgaySvl">
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
						   <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý công nợ > Báo cáo > Bảng kê tiền thu trong ngày </TD>
   
						   <TD colspan="2" align="right" class="tbnavigation">Chào mừng Bạn <%=userTen %>&nbsp;</TD>
						  </tr>
 					  </table>					</TD>
				</TR>
				<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
	    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%=loi%></textarea>
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
												<input type="text" class="days" name="tuNgay" size="20" value = "<%=bctt.getTuNgay()%>" >
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
													<input type="text"  class="days" name="denNgay" size="20" value = "<%=bctt.getDenNgay()%>" >												</TD>
												<TD>                                        		</TD>
                                     		</TR>
										</TABLE>									</TD>
								</TR>
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
		                        			if( bctt.getNvbhIds().contains(nvbhRs.getString("pk_seq")) ){ %>
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
			                        			if( bctt.getNvgnIds().contains(nvgnRs.getString("pk_seq")) ){ %>
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
			                       <TD class="plainlabel">Tìm khách hàng</TD>
			                  		<TD class="plainlabel" colspan="3">                        
			                  		<select name="khId" style="width: 500px" multiple="multiple" onChange = "submitform();" >
			                           <option value=""> </option>
			                        	<%
			                        		if(khRs != null)
			                        		{
			                        			try
			                        			{
			                        			while(khRs.next())
			                        			{  
			                        			if( bctt.getKhIds().contains(khRs.getString("pk_seq")) ){ %>
			                        				<option value="<%= khRs.getString("pk_seq") %>" selected="selected" ><%= khRs.getString("khTen") %></option>
			                        			<%}else { %>
			                        				<option value="<%= khRs.getString("pk_seq") %>" ><%= khRs.getString("khTen") %></option>
			                        		 <% } } khRs.close();} catch(SQLException ex){}
			                        		}
			                        	%>
			                        </select>
			                     </TD>
			                    </TR>
			                    
			                    <TR>    
			                       <TD class="plainlabel">Tìm phiếu nộp tiền</TD>
			                  		<TD class="plainlabel" colspan="3">                        
			                  		<select name="phieunoptienId" style="width: 500px" multiple="multiple" >
			                           <option value=""> </option>
			                        	<%
			                        		if(phieunoptienRs != null)
			                        		{
			                        			try
			                        			{
			                        			while(phieunoptienRs.next())
			                        			{  
			                        			if( bctt.getPhieuNopTienIds().contains(phieunoptienRs.getString("pk_seq")) ){ %>
			                        				<option value="<%= phieunoptienRs.getString("pk_seq") %>" selected="selected" ><%= phieunoptienRs.getString("pk_seq") %></option>
			                        			<%}else { %>
			                        				<option value="<%= phieunoptienRs.getString("pk_seq") %>" ><%= phieunoptienRs.getString("pk_seq") %></option>
			                        		 <% } } khRs.close();} catch(SQLException ex){}
			                        		}
			                        	%>
			                        </select>
			                     </TD>
			                    </TR>
			                    <TR>
			          			<TD class="plainlabel" colspan="2" >
									<a class="button2" href="javascript:submitform1()" >
										<img style="top: -4px;" src="../images/button.png" alt="">Xuất excel</a>&nbsp;&nbsp;&nbsp;&nbsp;                                    
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

if(bctt!=null)
{
	bctt.DBclose();
}
	}%>