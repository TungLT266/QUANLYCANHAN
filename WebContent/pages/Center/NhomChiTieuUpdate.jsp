<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.nhomsanpham.INhomsanpham" %>
<%@ page  import = "geso.traphaco.center.beans.nhomsanpham.imp.Nhomsanpham" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@page import="java.sql.SQLException"%>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<% 
	INhomsanpham nspBean = (INhomsanpham)session.getAttribute("nspBean"); 
	ResultSet spList = (ResultSet)nspBean.getSpList(); 
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<script type="text/javascript" src="../scripts/ajax.js"></script>
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.js"></script>

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
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

<SCRIPT language="JavaScript" type="text/javascript">

function submitform()
{
	document.nspForm.action.value='save';
    document.forms["nspForm"].submit();
    
}

function checkedAll(chk) {
	for(i=0; i<chk.length; i++){
	 	if(document.nspForm.chon.checked==true){
			chk[i].checked = true;
		}else{
			chk[i].checked = false;
		}
	 }
}

</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="nspForm" method="post" action="../../NhomchitieuUpdateSvl" >
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value="0">
<input type="hidden" name="id" value='<%= nspBean.getId() %>'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">
							 	&nbsp;Quản lý chỉ tiêu > Chức năng > Nhóm chỉ tiêu > Cập nhật</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../NhomchitieuSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD width="30" align="left" ><A href="javascript: submitform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
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
				
	    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%=nspBean.getMessage()%></textarea>
							<% nspBean.setMessage(""); %>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thông tin nhóm chỉ tiêu</LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
							<TR>
								<TD class="plainlabel" width="120px;" >Tên nhóm </TD>
								<TD class="plainlabel" colspan="3" >
									<INPUT type="text" name="tennhom" style="width:200px" value='<%= nspBean.getTen()%>'>
								</TD>
							</TR>
							<TR>
							  	<TD class="plainlabel">Diễn giải</TD>
						  	  	<TD class="plainlabel" width="250px;" >
						  	  		<INPUT type="text" name="diengiai" style="width:200px" value='<%= nspBean.getDiengiai() %>'>
						  	  	</TD>
						  		<TD colspan="2" class="plainlabel" ><label>
							  	<% if(nspBean.getTrangthai().equals("1")){ %>
							    	<input name="trangthai" type="checkbox" value="1" checked >
							    <%}else{ %>
							    	<input name="trangthai" type="checkbox" value="1" >
							    <%} %>
							   Hoạt động</label></TD>
						  	</TR>
							
						  	 <TR>	
						  	  	<TD class="plainlabel">Loại nhóm</TD>
							  	<TD class="plainlabel" colspan="3" >
							  		<SELECT name="lnhom" >
		                              <%-- <% if (nspBean.getLoainhom().equals("0")){ %>                                
		                                	<OPTION value="0" selected>Nhóm bình thường</OPTION>
		                              <% } else { %>
		                              		<OPTION value="0" >Nhóm bình thường</OPTION>
		                              <% } %>   --%>
		                              <% if (nspBean.getLoainhom().equals("1")){ %>                                
		                                	<OPTION value="1" selected>Nhóm đặc trị</OPTION>
		                              <% } else { %>
		                              		<OPTION value="1" >Nhóm đặc trị</OPTION>
		                              <% } %>	
		                              <% if (nspBean.getLoainhom().equals("2")){ %>                                
		                                	<OPTION value="2" selected>KPI đặc trị</OPTION>
		                              <% } else { %>
		                              		<OPTION value="2" >KPI đặc trị</OPTION>
		                              <% } %>
		                              <% if (nspBean.getLoainhom().equals("3")){ %>                                
		                                	<OPTION value="3" selected>Nhóm chiến lược</OPTION>
		                              <% } else { %>
		                              		<OPTION value="3" >Nhóm chiến lược</OPTION>
		                              <% } %>	
		                              <% if (nspBean.getLoainhom().equals("4")){ %>                                
		                                	<OPTION value="4" selected>KPI chiến lược</OPTION>
		                              <% } else { %>
		                              		<OPTION value="4" >KPI chiến lược</OPTION>
		                              <% } %>	
                              		</SELECT>
                              	</TD>
						  	</TR>
						  	<TR>
								<TD class="plainlabel" >Từ ngày </TD>
								<TD class="plainlabel">
									<INPUT type="text" name="tungay" style="width:200px" value='<%= nspBean.getTungay() %>' class="days" >
								</TD>
								<TD class="plainlabel" width="100px;" >Đến ngày </TD>
								<TD class="plainlabel">
									<INPUT type="text" name="denngay" style="width:200px" value='<%= nspBean.getDenngay() %>' class="days" >
								</TD>
							</TR>
						  
						</TABLE>
						
						<TABLE border="0" width="100%" cellpadding="4" cellspacing="1">
		                  		                  
		                  	<TR class="tbheader" >
		                  		<td align="left" >Mã sản phẩm</td>
		                  		<td align="left" >Tên sản phẩm</td>
		                  		<td align="center" >Chọn</td>
							</TR >	
							<% 
								if(spList != null){
								while(spList.next()){ %>	
								<TR class='tbdarkrow' >	
									<td align="left" ><%=spList.getString("MA") %></td>
			                  		<td align="left"> <%=spList.getString("TEN") %></td>
			                  		<TD align="center" >
			                  			<% if( nspBean.getSpIds().contains( spList.getString("pk_seq") ) ) { %> 
												<input type="checkbox" name="spIds" value="<%= spList.getString("PK_SEQ") %>"  checked="checked" >
										<%  } else { %> 
												<input type="checkbox" name="spIds" value="<%= spList.getString("PK_SEQ")%>" >	
										<% } %>
									</TD>
								</TR>
			                  <%} } %>
																	
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

<% 
if(nspBean!=null)nspBean.DBClose();
	if( spList != null )
	{
		spList.close();
		spList = null;
	}
} %>
	

