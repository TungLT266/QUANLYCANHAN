<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.lapngansach.ILNSBanggiaban_kh" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% ILNSBanggiaban_kh bgbanBean = (ILNSBanggiaban_kh)session.getAttribute("assign"); %>
<% ResultSet dvkd = (ResultSet)bgbanBean.getDvkdIds(); %>
<% ResultSet kenh = (ResultSet)bgbanBean.getKenhIds(); %>
<% ResultSet khSelected = (ResultSet)bgbanBean.getKHSelected(); %>
<% ResultSet khList = (ResultSet)bgbanBean.getKHList(); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>SalesUp\\ - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<SCRIPT language="javascript" type="text/javascript">
function submitform()
{   
   document.forms["bgbanForm"].submit();
}

 function saveform()
{
	document.forms['bgbanForm'].action.value='save';
    document.forms["bgbanForm"].submit();
}

</SCRIPT>
 <script type="text/javascript">
 
 function checkedAll() {
		var khIds = new Array(<%= bgbanBean.getKHString() %>);	
		for (var i =0; i < khIds.length; i++) 
	 	{
		 	var cb = "chbox" + khIds[i];
		 	var objCheckBoxes = document.forms["bgbanForm"].elements[cb];
			if (document.forms["bgbanForm"].elements["chon"].checked == false){
				objCheckBoxes.checked = false;
			}else{
				objCheckBoxes.checked = true;
			}
	 	}
 }
 </script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="bgbanForm" method="post" action="../../ErpLNSBanggiabanSvl">
<input type="hidden" name="userId" value='<%=bgbanBean.getUserId() %>'>
<input type="hidden" name="action" value='assign'>
<input type="hidden" name="id" value='<%= bgbanBean.getId() %>'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý ngân sách &gt; Dữ liệu nền &gt; Bảng giá bán &gt; Cập nhật</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../ErpLNSBanggiabanSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD width="30" align="left" ><A href="javascript: saveform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
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
				
	    			<textarea name="dataerror"  style="width:100%" rows="1"><%=bgbanBean.getMessage()%></textarea>
					<% bgbanBean.setMessage(""); %>
					</FIELDSET>
			   </TD>
			</tr>			

		 	<TR>
				<TD >
			        <FIELDSET>
			        <LEGEND class="legendtitle">&nbsp;Bảng giá bán </LEGEND>
					<TABLE width="100%"cellpadding="0" cellspacing="1">
						<TR>
							<TD>				
								<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
									<TR>
										<TD width="100%" align="center">
											<TABLE class="tblight" width="100%" cellpadding="6" cellspacing="0">
												<TR>
													<TD width="15%" class="plainlabel">Tên bảng giá <FONT class="erroralert">*</FONT></TD>
													<TD  class="plainlabel"><INPUT name="bgTen" value="<%= bgbanBean.getTen() %>"  type="text" style="width:300px" readonly="readonly"/></TD>
										
												</TR>
												<TR>
								    				<TD class="plainlabel">Đơn vị kinh doanh</TD>
								      				<TD class="plainlabel">
								      				<SELECT name="dvkdId" disabled="disabled" style="width:300px">								      
								  	 					<option value =""></option>
								  	 					<% try{ while(dvkd.next()){ 
								  	 							if(dvkd.getString("dvkdId").equals(bgbanBean.getDvkdId())){ %>
								      								<option value='<%=dvkd.getString("dvkdId")%>' selected><%=dvkd.getString("dvkd") %></option>
								      						   <%}else{ %>
								     								<option value='<%=dvkd.getString("dvkdId")%>' ><%=dvkd.getString("dvkd")  %></option>
								     							<%}}}catch(java.sql.SQLException e){} %>	
								     	
													</SELECT></TD>
												</TR>
												<TR>
								  					<TD class="plainlabel">Kênh bán hàng </TD>
								  					<TD class="plainlabel">
								      					<SELECT name="kenhId" disabled="disabled" style="width:300px">								      
								  	 						<option value =""></option>
								  	 					<% try{ while(kenh.next()){ 
								    							if((kenh.getString("kenhId").trim()).equals(bgbanBean.getKenhId().trim())){ %>
								      								<option value='<%=kenh.getString("kenhId")%>' selected><%=kenh.getString("kenh") %></option>
								      						  <%}else{ %>
								     								<option value='<%=kenh.getString("kenhId")%>'><%=kenh.getString("kenh") %></option>
								     						<%}}}catch(java.sql.SQLException e){} %>	
								     	
                                  						</SELECT></TD>
									  			</TR>

											</TABLE>								
										</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
				</TABLE>
				</FIELDSET>
						<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">							
								<TR class="tbheader">
									<TH width="28%">Mã khách hàng</TH>
									<TH width="60%">Tên khách hàng </TH>
									<TH width="12%">Chọn						
										<input type="checkbox" name="chon" onClick="checkedAll()">																	
									</TH>
								</TR>

								<% ResultSet rs = null;
								   
								   String lightrow = "tblightrow";
								   String darkrow = "tbdarkrow";
								   int m = 0;
								   
								   rs = khSelected;
									   
								   if (!(rs == null)){			
										    
									  	while (rs.next()){								   			
											if (m % 2 != 0) {%>						
												<TR class= <%=lightrow%> >
										<%  } else {%>
												<TR class= <%= darkrow%> >
										<%  } %>	
												<TD align="left" class="textfont" > <input type="hidden" name='<%="idkh" +rs.getString("khId")%>'> <%= rs.getString("khId") %></TD>
												<TD align="center"><div align="left"><%= rs.getString("khTen")%></div></TD>
												<TD align="center">
													<input type="checkbox" name='<%= "chbox" + rs.getString("khId") %>' value='<%= rs.getString("khId") %>' checked>									
												</TD>

												</TR>
													
								<% 		m++;}
									}	
			
							   	       rs = khList;
									   
									   if (!(rs == null)){			
										    
									   		while (rs.next()){								   			
												if (m % 2 != 0) {%>						
													<TR class= <%=lightrow%> >
											<%  } else {%>
													<TR class= <%= darkrow%> >
											<%  } %>	
													<TD align="left" class="textfont"><%= rs.getString("khId") %></TD>
													<TD align="center"><div align="left"><%= rs.getString("khTen")%></div></TD>
													<TD align="center">
														<input type="checkbox" name='<%= "chbox" + rs.getString("khId") %>' value='<%= rs.getString("khId") %>'>									
													</TD>

													</TR>
													
								<% 			m++;}
										}	
									   
									%>	
								   
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
try{
	 
	if( dvkd!=null){
		dvkd.close();
		}
	if( kenh!=null){
		kenh.close();
		}
	if( khSelected!=null){
		khSelected.close();
		}
	if( khList!=null){
		khList.close();
		}
	if( rs!=null){
		rs.close();
		}
	session.setAttribute("assign", null);
}catch(Exception er){
	
}
finally{
	bgbanBean.DbClose();
}
  
	}%>