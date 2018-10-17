<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.nhanhang.INhanhang" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<% INhanhang nhBean = (INhanhang)session.getAttribute("nhanhangBean");%>
<% ResultSet dvkdlist = (ResultSet)nhBean.getDvkdList(); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<SCRIPT language="javascript" type="text/javascript">
</SCRIPT>
<SCRIPT language="JavaScript" type="text/javascript">
<!--HPB_SCRIPT_CODE_40
//-->
function submitform()
{
    document.forms["nhanhangForm"].submit();
}

function focusDvkd(){
	alert("Không thể thay đổi vì đã có sản phẩm thuộc Nhãn và DVDK này");	
	submitform();
}

</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="nhanhangForm" method="post" action="../../NhanhangUpdateSvl" charset="utf-8">
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
<INPUT name="nhId" type="hidden" value='<%=nhBean.getId() %>' size="30">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">

		<TABLE width="100%" cellpadding="0" cellspacing="1">
			
				<TR>
					<TD align="left">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
						   <TD align="left" colspan="2" class="tbnavigation">&nbsp;Thiết lập dữ liệu nền &gt; Dữ liệu nền sản phẩm &gt; Nhãn hàng> Cập nhật </TD>
   
						   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn<%=userTen %>&nbsp;  </TD>
						  </tr>
 					  </table>
					</TD>
				</TR>
		</TABLE>		
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR ><TD >
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR class = "tbdarkrow">
									<TD width="30" align="left"><A href="../../NhanhangSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
								    <TD width="2" align="left" ></TD>
								    <TD width="30" align="left" ><A href="javascript: submitform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
									<TD >&nbsp; </TD>						
								</TR>
						</TABLE>
				</TD></TR>
		</TABLE>
					
		<TABLE width = 100% cellpadding = "4" cellspacing = "0" border = "0">
		  	<tr>
				<TD align="left" colspan="4" class="legendtitle">
					<FIELDSET>
					<LEGEND class="legendtitle">Báo lỗi nhập liệu</LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%=nhBean.getMessage()%></textarea>
						<% nhBean.setMessage(""); %>
						</FIELDSET>
				   </TD>
			</tr>			
			<TR>
				<TD width="100%" align="left" >
					<FIELDSET>
					<LEGEND class="legendtitle">&nbsp;Nhãn hàng &nbsp;mới</LEGEND>

					<TABLE  width="100%" cellpadding="6" cellspacing="0">
						<TR>
							<TD width="20%" class="plainlabel">Nhãn hàng <FONT class="erroralert">*</FONT></TD>
							<TD width="80%" class="plainlabel"><INPUT name="nhanhang" value='<%=nhBean.getNhanhang()%>' type="text" size="40"/></TD>
						</TR>
				      	<TR>
							<TD width="19%" class="plainlabel">Đơn vị kinh doanh </TD>
							  	<TD class="plainlabel" >
								<TABLE cellpadding="0" cellspacing="0" border="0">
									<TR>
								  		<TD>
								  		<% if (nhBean.AllowtoChangeDvkd()) {%>
								  			<SELECT  name="dvkdId">
								  		<%}else{ %>
								  			<SELECT  name="dvkdId" onFocus="focusDvkd();">
										<% }
									
											try{%>
												<option value="0"  ></option>
											<% 	while (dvkdlist.next()){%>
													
													<%	if(dvkdlist.getString("pk_seq").equals(nhBean.getDvkdId())){ %>											
															
															<option value= <%=dvkdlist.getString("pk_seq")%> selected><%= dvkdlist.getString("donvikinhdoanh") %></option>															
														<%}else{%>
															<option value= <%=dvkdlist.getString("pk_seq")%> ><%= dvkdlist.getString("donvikinhdoanh") %></option>																																		
														<%	}
														}
															
													}catch(java.sql.SQLException e){
														
													}%>														                                           
                                      			</SELECT>
                                   			</TD>
										</TR>
									</TABLE>									
								</TD>

						</TR>

						<TR>
							<% if(nhBean.getTrangthai().equals("1")) {%>						
						  		<TD class="plainlabel"><input name="trangthai" type="checkbox" value="1" checked>
						  	<%}else{ %>
						  		<TD class="plainlabel"><input name="trangthai" type="checkbox" value="0" >
						  	<%} %>
							      Hoạt động </TD>
							<TD class="plainlabel">&nbsp;</TD>
						</TR>
								
				</TABLE>

				</FIELDSET>
			</TD>
		</TR>
	</TABLE>
	</TD></TR>
</TABLE>
</form>
</BODY>
</HTML>
<% if(dvkdlist != null) 
	dvkdlist.close(); 
 
	nhBean.DBClose();
}%>