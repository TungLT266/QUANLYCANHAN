<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.distributor.beans.reports.imp.Reports"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%		
		Reports rpt=(Reports)session.getAttribute("report");
		String userId = rpt.getUser();
		String userTen = rpt.getTenUser();	
		String sum = (String) session.getAttribute("sum");
		String loi=rpt.getLoi();
		String tungay=rpt.getTuNgay();
		String denngay=rpt.getDenNgay();
		
		Utility util = (Utility) session.getAttribute("util");
		ResultSet rsnpp=rpt.getRsNhaPP();
		ResultSet rscttb=rpt.getRsCTTB();
		ResultSet vungRs=rpt.getRsVung();
		ResultSet kvRs=rpt.getRsKhuVuc();
		String nhappid=rpt.getNppId();
		
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


<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"type="text/javascript"></script>
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
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<SCRIPT language="javascript" type="text/javascript">


$(document).ready(function()
		{
			$("#nppId").select2();
		});		

function submitform()
{
	document.forms['rpForm'].dataerror.value="";
	document.forms['rpForm'].submit();
}

function search()
{
	document.forms['rpForm'].action.value="search";
	document.forms['rpForm'].submit();
}


</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="rpForm" method="post" action="../../Disproforcustomer">
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
						   <TD align="left" colspan="2" class="tbnavigation">&nbsp; Quản lý trưng bày &#62; Báo cáo &gt; Báo cáo trưng bày </TD>
   
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
										<input class="days" type="text" name="tungay" size="20" value = "<%=tungay%>" >
									</TD>
								</TR>
								<TR>
									<TD class="plainlabel" >Đến ngày </TD>
								  	<TD class="plainlabel" >
										<input class="days" type="text" name="denngay" size="20" value = "<%=denngay%>" >												
									</TD>
								</TR>
								<TR>
									<TD class="plainlabel" >Vùng  </TD>
									<TD class="plainlabel" >
									<select name="vungIds" multiple="multiple"  onchange="search()">
				                        <% if(vungRs != null) {
				                         while(vungRs.next()) 
				                         {
				                           if(rpt.getVungId().indexOf(vungRs.getString("vungId")) >= 0 ){ %>
				                             <option value="<%= vungRs.getString("vungId") %>" selected style="padding: 2px" ><%= vungRs.getString("vungTen") %></option>
				                         <%}else { %>
				                         	<option value="<%= vungRs.getString("vungId") %>" style="padding: 2px"><%= vungRs.getString("vungTen") %></option>
				                         <%}} vungRs.close(); }%>
                    					</select>
                    					</TD>
                    			</TR>
                    			
                    			
                    			<TR>
									<TD class="plainlabel" >Khu vực </TD>
									<TD class="plainlabel" >
									<select name="kvIds" multiple="multiple"  onchange="search()">
				                        <% if(kvRs != null) {
				                         while(kvRs.next()) 
				                         {
				                           if(rpt.getKhuVucId().indexOf(kvRs.getString("kvId")) >= 0 ){ %>
				                             <option value="<%= kvRs.getString("kvId") %>" selected style="padding: 2px" ><%= kvRs.getString("kvTen") %></option>
				                         <%}else { %>
				                         	<option value="<%= kvRs.getString("kvId") %>" style="padding: 2px"><%= kvRs.getString("kvTen") %></option>
				                         <%}} kvRs.close(); }%>
                    					</select>
                    					</TD>
                    			</TR>
								
								<TR class="plainlabel" > 
									<TD class="plainlabel" >Chi nhánh / Đối tác </TD>
								  	<TD class="plainlabel" >
							  			  <SELECT  name="nhappid" id="nppId"  style="width:400px">
								  		<option value =""></option>
								  		
								  	 <% try{ while(rsnpp.next()){ 
								    	if(rsnpp.getString("pk_seq").equals(nhappid)){ %>
								      		<option value='<%=rsnpp.getString("pk_seq") %>' selected><%=rsnpp.getString("ten") %></option>
								      	<%}else{ %>
								     		<option value='<%=rsnpp.getString("pk_seq") %>'><%=rsnpp.getString("ten") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	
                                  </select>
							  		</TD>
								</TR>
								
								<TR>
								  	<TD width="19%" class="plainlabel" ></TD>
								  	<TD class="plainlabel" >
										<input type="checkbox" name="laydoanhso" value="1"  > <i>Lấy doanh số</i>
									</TD>
								</TR>
								
								<tr class="plainlabel">
									<TD class="plainlabel" style="top:10px">Chương trình trưng bày  </TD>
								<td>
								<fieldset style="width: 410px; float: left;">
							       <legend>Chương trình trưng bày &nbsp;</legend> 
							       <select name="cttbid" multiple="multiple" style="width:400px;height:300px">
			                            <option value="" selected style="padding: 2px" >Chọn hết</option>
			                        <% 	if(rscttb != null) {
				                         while(rscttb.next()) 
				                         {
				                           if(rpt.getcttbid().indexOf(rscttb.getString("pk_seq")) >= 0 ){ %>
				                             <option value="<%= rscttb.getString("pk_seq") %>" selected style="padding: 2px" ><%= rscttb.getString("scheme")+"--"+rscttb.getString("diengiai") %></option>
				                         <%}else { %>
				                         	<option value="<%= rscttb.getString("pk_seq") %>" style="padding: 2px"><%= rscttb.getString("scheme")+"--"+rscttb.getString("diengiai")  %></option>
				                         <%} }}%>       	
                   				 </select>
                   				 </fieldset>
								</td>
								</tr>
							    <TR>
									<TD colspan="2" class="plainlabel">
									<a class="button2" href="javascript:submitform()" >
	<img style="top: -4px;" src="../images/button.png" alt="">Tạo báo cáo</a>&nbsp;&nbsp;&nbsp;&nbsp;                                    </TD>
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
	if(rsnpp!=null){rsnpp.close();}
	if(rscttb!=null){rscttb.close();}
	if(rpt!=null){rpt.DBclose();
	rpt = null;}
	session.setAttribute("report", null);
}
%>