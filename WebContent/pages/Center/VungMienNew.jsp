<%@page import="java.sql.ResultSet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.vung.IVungmien" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% IVungmien vmBean = (IVungmien)session.getAttribute("vmBean"); 
   ResultSet bms = (ResultSet)vmBean.getBms();
   ResultSet qg = (ResultSet)vmBean.getQuocgia();
   %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<SCRIPT language="javascript" type="text/javascript">
 function confirmLogout(){
    if(confirm("Bạn có muốn đăng xuất?"))
    {
		top.location.href = "home.jsp";
    }
    return
  }
 function submitform()
 {
     document.forms['vmForm'].submit();
 }
 function saveform()
 {
 	 document.forms['vmForm'].action.value= 'save';
     document.forms['vmForm'].submit();
 }
</SCRIPT>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name='vmForm' method="post" action="../../VungmienUpdateSvl">
<input type="hidden" name="action" value='1'>
<INPUT name="userId" type="hidden" value='<%= userId %>' size="30">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%">
				<TR>
					<TD align="left" class="tbnavigation">

					   	<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">
							   		Thiết lập dữ liệu nền &gt; Dữ liệu nền kinh doanh &gt; Miền &gt; Tạo mới </TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD> 
						    </tr>
   
						   	<tr>
						   		<TD align="left" height="5" colspan="4" class=""></td>
   
  							</tr>
						</TABLE>
					</TD>
				</TR>
			</TABLE>	
			<TABLE width="100%" border="0" cellpadding="3" cellspacing="0">
				<TR ><TD >
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR class = "tbdarkrow">
									<TD width="30" align="left"><A href="../../VungmienSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
								    <TD width="2" align="left" ></TD>
								    <TD width="30" align="left" ><A href="javascript: saveform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
									<TD >&nbsp; </TD>						
								</TR>
						</TABLE>
				</TD></TR>
			</TABLE>
				
			<TABLE width = 100% cellpadding = "3" cellspacing = "0" border = "0">
			  	<TR>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu</LEGEND>
				
		    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold" style="width: 100%" readonly="readonly" rows="1" ><%= vmBean.getMessage() %></textarea>
						</FIELDSET>
				   </TD>
				</TR>
				
				  	<tr>
						   <TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle">Thông tin vùng miền
								</LEGEND>
								<TABLE class="tblight" width="100%" cellspacing="0" cellpadding="6">
									<TR>
									  <TD width="15%" class="plainlabel" >Vùng miền <FONT class="erroralert">*</FONT></TD>
									  <TD  class="plainlabel" ><INPUT name="vungmien"
										type="text" value='<%= vmBean.getVungmien() %>' style="width:400px"></TD>
								  </TR>
									<TR>
									  <TD class="plainlabel" >Diễn giải </TD>
									  <TD class="plainlabel" ><INPUT name="diengiai"
										type="text" value='<%= vmBean.getDiengiai() %>' style="width:400px"></TD>
								  </TR>
								  <TR>
								  <TD class="plainlabel">Quản lý vùng</TD>
								  <TD class="plainlabel"><SELECT name="bm">
									<%  try{
								  		while(bms.next()){								  			
								  			if (bms.getString("pk_seq").equals(vmBean.getBm())){ %>								  			
								  				<option value="<%= bms.getString("pk_seq")%>" selected><%= bms.getString("ten")%></option>
								  		  <%}else{ %>		
								  		  		<option value="<%= bms.getString("pk_seq")%>" ><%= bms.getString("ten")%></option>
								  	<%		}
								  		}
								  	}catch (java.sql.SQLException e){} %>
                                  </SELECT></TD>
							  	</TR>
							  	<TR>
								  <TD class="plainlabel">Quốc gia</TD>
								  <TD class="plainlabel"><SELECT name="quocgia">
									<%  try{
								  		while(qg.next()){								  			
								  			if (qg.getString("Tenqg") == "Việt Nam"){ %>								  			
								  				<option value="<%= qg.getString("pk_seq")%>" selected><%= qg.getString("tenqg")%></option>
								  		  <%}else{ %>		
								  		  		<option value="<%= qg.getString("pk_seq")%>" ><%= qg.getString("tenqg")%></option>
								  	<%		}
								  		}
								  	}catch (java.sql.SQLException e){} %>
                                  </SELECT></TD>
							  	</TR>
									<TR>
									  <TD width="15%" class="plainlabel" ><label>
										<%  if (vmBean.getTrangthai().equals("1")){%>
										  	<input name="trangthai" type="checkbox" value ="1" checked>
										<%} else {%>
											<input name="trangthai" type="checkbox" value ="0">
										<%} %>
									   Hoạt động</label></TD>
										<TD  class="plainlabel" >&nbsp;</TD>
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
<% vmBean.closeDB(); %>
<%}%>