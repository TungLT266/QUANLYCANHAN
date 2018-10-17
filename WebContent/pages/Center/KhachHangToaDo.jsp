<%@page import="com.cete.dynamicpdf.text.bd"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.bandott.IBandott" %>
<%@ page  import = "geso.traphaco.center.beans.bandott.imp.Bandott" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)) {
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	} else{ %>

<% IBandott khBean = (IBandott)session.getAttribute("obj"); %>
<% ResultSet vungRs = khBean.getVungRs(); %>
<% ResultSet kvRs = khBean.getKvRs(); %>
<% ResultSet nppRs = khBean.getNppRs(); %>
<% ResultSet ddkd = khBean.getDdkdRs(); %>
<% ResultSet tbh = khBean.getTbhRs(); %>
<% ResultSet khlist = khBean.getKhChuaViengThamRs(); %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>OneOne - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
    
    <script type="text/javascript">

    
    
    </script>
    
    
    
    
    
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">

   	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
  	
  	<script type="text/javascript">
  		function xoatoado(khId) {
  			if(confirm("Bạn có chắc chắn muốn xóa tọa độ khách hàng này không ?") && typeof(khId) !== "undefined") {
	  			$("#khId").val(khId);
	  			document.forms["bdForm"].submit();
	  		}
  		}
	  	function submitform()
		{
		    document.forms["bdForm"].submit();
		}
  	</script>
  	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
    <script>
		$(document).ready(function()
		{
			$(".select2").select2();
		});
	</script>
</head>

<body>
	<form name="bdForm" method="post" action="../../BandoTTSvl">
		<input type="hidden" name="userId" value='<%= userId %>'>
		<input type="hidden" name="view" value='khachhangToado'>
		<input type="hidden" name="action" id="action" value='1'>
		<input type="hidden" name="trungtam" id="trungtam" value=''>
		<input type="hidden" name="khId" id="khId" value="">
		
		<div id="main" style="width:99.5%; padding-left:2px">
		
			<div align="left" id="header" style="width:100%; float:none">
		    	<div style="width:40%; padding:5px; float:left" class="tbnavigation">
		        	Dữ liệu nền > Kinh doanh > Tọa độ khách hàng
		        </div>
		        <div align="right" style="padding:5px" class="tbnavigation">
		        	Chào mừng bạn <%= userTen %> &nbsp;&nbsp;
		        </div>
		    </div>
		    
		    <div style="width:100%; float:none" align="left">
		    <fieldset>
		    	<legend class="legendtitle">Thông tin khách hàng</legend>
		        	<TABLE width="100%" cellpadding="4" cellspacing="0">            
			            <TR>
			                <TD class="plainlabel" width="150px" >Vùng </TD>
			                <TD class="plainlabel" width="240px">
			                    <select name="vung" id="vung" onChange = "submitform();">
			                            <option value="" selected></option>
			                            <% if(vungRs != null){
											  try{ while(vungRs.next()){ 
								    			if(vungRs.getString("pk_seq").equals(khBean.getVungId())){ %>
								      				<option value='<%= vungRs.getString("pk_seq")%>' selected><%= vungRs.getString("ten") %></option>
								      			<%}else{ %>
								     				<option value='<%= vungRs.getString("pk_seq")%>'><%= vungRs.getString("ten") %></option>
								     	<%}} vungRs.close(); }catch(java.sql.SQLException e){} }%>
			                        </select>    
			                </TD>
			                <TD class="plainlabel" width="150px">Khu vực </TD>
			                <TD class="plainlabel">
			                    <select name="khuvuc" id="khuvuc" onChange = "submitform();">
			                            <option value="" selected></option>
			                            <% if(kvRs != null){
											  try{ while(kvRs.next()){ 
								    			if(kvRs.getString("pk_seq").equals(khBean.getkvId())){ %>
								      				<option value='<%= kvRs.getString("pk_seq")%>' selected><%= kvRs.getString("ten") %></option>
								      			<%}else{ %>
								     				<option value='<%= kvRs.getString("pk_seq")%>'><%= kvRs.getString("ten") %></option>
								     	<%}} kvRs.close(); }catch(java.sql.SQLException e){} }%>
			                        </select>    
			                </TD>
			            </TR>
			            <TR>
			                <TD class="plainlabel" >Chi nhánh / Đối tác </TD>
			                <TD class="plainlabel">
			                    <select name="npp" id="npp" class="select2" style="width: 200px;" onChange = "submitform();">
			                            <option value="" selected></option>
			                            <% if(nppRs != null){
											  try{ while(nppRs.next()){ 
								    			if(nppRs.getString("pk_seq").equals(khBean.getNppId())){ %>
								      				<option value='<%= nppRs.getString("pk_seq")%>' selected><%= nppRs.getString("ten") %></option>
								      			<%}else{ %>
								     				<option value='<%= nppRs.getString("pk_seq")%>'><%= nppRs.getString("ten") %></option>
								     	<%}} nppRs.close(); }catch(java.sql.SQLException e){} }%>
			                        </select>
			                </TD>
			                <TD class="plainlabel" >Trình dược viên </TD>
			                <TD class="plainlabel">
			                    <select name="ddkd" id="ddkd" class="select2" style="width: 200px;" onChange = "submitform();">
			                            <option value="" selected></option>
			                            <% if(ddkd != null){
											  try{ while(ddkd.next()){ 
								    			if(ddkd.getString("ddkdId").equals(khBean.getDdkdId())){ %>
								      				<option value='<%= ddkd.getString("ddkdId")%>' selected><%= ddkd.getString("ddkdTen") %></option>
								      			<%}else{ %>
								     				<option value='<%= ddkd.getString("ddkdId")%>'><%= ddkd.getString("ddkdTen") %></option>
								     	<%}} ddkd.close(); }catch(java.sql.SQLException e){} }%>
			                        </select>    
			                </TD>
		            	</TR>
		            	<TR>
		               
		                <TD class="plainlabel" >Tuyến bán hàng </TD>
		                <TD class="plainlabel" >
		                    <select name="tbh" id="tbh" onChange = "submitform();">
		                          <option value="" selected></option>
		                          <% if(tbh != null){
								  try{ while(tbh.next()){ 
					    			if(tbh.getString("tbhId").equals(khBean.getTbhId())){ %>
					      				<option value='<%= tbh.getString("tbhId")%>' selected><%= tbh.getString("tbhTen") %></option>
					      			<%}else{ %>
					     				<option value='<%= tbh.getString("tbhId")%>'><%= tbh.getString("tbhTen") %></option>
					     	<%}} tbh.close(); }catch(java.sql.SQLException e){} }%>
		                      </select>    
		               </TD>
		               <TD class="plainlabel">Mã cũ</TD>
								   		<TD class="plainlabel"><INPUT name="maFAST" type="text" value="<%= khBean.getMafast() %>" size="40" onChange = "submitform();">
		               
		            </TR>
		        </TABLE>
		     	<hr>
		    
				<TABLE width="100%" border="0" cellspacing="0" cellpadding="2">
					<TR>
						<TD width="100%">
							<TABLE class="" width="100%">
								<TR>
									<TD width="98%">
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
											<TR class="tbheader">
												<TH width="4%">STT</TH>
											<!-- 	<TH width="0%" align="center">Mã khách hàng</TH> -->
												<TH width="10%" align="center">Mã cũ</TH>
												<TH width="20%" align="center">Tên đơn vị</TH>
												<!-- <TH width="0%" align="center">Điện thoại</TH> -->
												<TH width="15%" align="center">Tên khách hàng</TH>
												<TH width="20%" align="center">Địa chỉ</TH>
												<TH width="10%" align="center">Vĩ độ</TH>
												<TH width="10%" align="center">Kinh độ</TH>
												<TH width="10%" align="center">Xóa tọa độ</TH>
											</TR>
										
							<%  														
		                        int m = 0;
		                        String lightrow = "tblightrow";
		                        String darkrow = "tbdarkrow";
								if(khlist!=null)
								{%>
								<% try{								
		                               while (khlist.next()){
		                                   	
		                                  	if (m % 2 != 0) {%>                     
		                                   	<TR class= <%=lightrow%> >
		                                   <%} else {%>
		                                      	<TR class= <%= darkrow%> >
		                                   	<%}%>
		                                   		<TD align="center"><%=m+1 %></TD>
												<%-- <TD align="left"><div align="center"><%=khlist.getString("khId") %></div></TD>  --%>
												<TD align="center"><%=khlist.getString("mafast") %></TD>                                  
												<TD align="center"><%= khlist.getString("khTen")%></TD>
												<%-- <TD align="center"><%= khlist.getString("dienthoai")%></TD> --%>
												<TD align="center"><%= khlist.getString("CHUCUAHIEU")%></TD>
												<TD align="center"><%= khlist.getString("diachi")%></TD>												
												<TD align="center"><%=khlist.getString("lat")%></TD>
												<TD align="center"><%=khlist.getString("lon")%></TD>
												<TD align="center">
													<TABLE border = 0 cellpadding="0" cellspacing="0">
														<TR>
															<TD>
																<A href = "javascript:xoatoado(<%=khlist.getString("pk_seq") %>);"><img src="../images/Delete20.png" alt="Xoa toa do" title="Xóa tọa độ" width="20" height="20" longdesc="Xoa toa do" border = 0></A>
															</TD>												
														</TR>
													</TABLE>
												</TD>
											</TR>
									<%m++; }}catch(java.sql.SQLException e){}
								}%>
									
											 							
										</TABLE>
			
								</TABLE>
							</TD>
						</TR>
					</TABLE>
		    
		    	</fieldset>
		    </div>
		 </div>
	 </form>
	 <script>
		var msg = "<%= khBean.getMsg() %>".trim();
		if(msg.length > 0) {
			alert(msg);
		}
		
	</script>
</body>

</html>

<% khBean.DBclose(); %>
<%}%>