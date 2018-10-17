<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.nhasanxuat.IErpNhaSanXuat"%>
<%@ page  import = "geso.traphaco.erp.beans.nhasanxuat.imp.ErpNhaSanXuat" %>
<%@ page  import = "java.sql.ResultSet"%>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoERP/index.jsp");
	}else{ %>

<%
	IErpNhaSanXuat nsxBean = (IErpNhaSanXuat) session.getAttribute("nsxBean");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Traphaco - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<script type="text/javascript">
function msg()
{
alert("Lưu dữ liệu thành công!");
}

function submitform(){
	
	var tennh = document.forms['nsxForm'].Ten.value;
	if(tennh == "")
	{
		document.forms['nsxForm'].dataerror.value = "Vui lòng nhập tên nhà sản xuất.";
		return;
	}
	
	document.forms["nsxForm"].submit();
	
}
</script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="nsxForm" method="post" action="../../ErpNhaSanXuatUpdateSvl" charset="UTF-8">
<input type="hidden" name="userId" value='<%=userId %>'>
<input type="hidden" name="userTen" value='<%=userTen %>'>
<INPUT name="action" type="hidden" value="save">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">

					   	<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kho vận &gt; Nhà sản xuất
							   &gt; Tạo mới </TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %> &nbsp; </td>
					      </tr>
   
						</TABLE>
					</TD>
				</TR>
			</TABLE>	
			<TABLE width="100%" border="0" cellpadding="3" cellspacing="0">
				<TR ><TD >
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR class = "tbdarkrow">
									<TD width="30" align="left"> <A href="../../ErpNhaSanXuatSvl" > <img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"> </A></TD>
								    <TD width="2" align="left" ></TD>
								    <TD width="30" align="left" ><A href="javascript: submitform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
									<TD>&nbsp; </TD>						
								</TR>
						</TABLE>
				</TD></TR>
			</TABLE>
								<TABLE width="100%" cellspacing="0" cellpadding="6">
										<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle">Thông báo </LEGEND>
			    				<textarea name="dataerror" style="width: 100%" readonly="readonly" rows="1" ><%=nsxBean.getMsg()%></textarea>
								
								</FIELDSET>
						   </TD>
							</tr>	
							<tr>
						   <TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle">Thông tin nhà sản xuất
								</LEGEND>
								<TABLE  width="100%" cellspacing="0" cellpadding="6">		
									<%-- <TR>
									  	<TD width="15%" class="plainlabel">Mã <FONT class="erroralert">*</FONT></TD>
									  	<TD class="plainlabel" >
									  		<INPUT type = "text" name="Ma" id = "ma" size="40" type="text" value="<%=nsxBean.getMA() %>" >
										</TD>
								    </TR> --%>
	
									<tr>
									<td width = "15%" class = "plainlabel">Tên <font class = "erroralert">*</font> </td> 
									<td class = "plainlabel"> <input type = "text" name = "Ten" id = "ten" value="<%=nsxBean.getTEN() %>"> </td>
									</tr>
									
									<TR>
									<TD class = "plainlabel">
									Hoạt động
									</TD>
									<TD class = "plainlabel">
									<%
									   if(nsxBean.getTRANGTHAI().equals("1")){ %>
										<input name="hoatdong" checked="checked" type="checkbox" value="1">
									<%}else{ %>
										<input name="hoatdong" type="checkbox" value="1" >
									<%} %>
									</TD>
									
									</TR>
								</TABLE>
								</FIELDSET>			
								</TD> </tr></TABLE>
				 </TD>
			</TR>
		</TABLE>				
</form>

</BODY>
</HTML>
<%
nsxBean.DBClose();
	}%>