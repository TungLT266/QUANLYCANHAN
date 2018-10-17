<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.loaitaisan.IErp_loaiCCDC" %>
<%@ page  import = "geso.traphaco.erp.beans.loaitaisan.imp.Erp_loaiCCDC" %>
<%@ page  import = "java.sql.ResultSet"%>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "geso.traphaco.center.util.*" %>

<%
	IErp_loaiCCDC lccdcBean =(Erp_loaiCCDC)session.getAttribute("lccdcBean");
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  
	ResultSet tkList = lccdcBean.getRsTk();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>TraphacoERP - Project</TITLE>  
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<link rel="stylesheet" href="../css/main.css" type="text/css">
<link rel="stylesheet" href="../css/calendar.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>

<script type="text/javascript">
function msg()
{
alert("Lưu dữ liệu thành công!");
}

function submitform(){
	var mats = document.forms['tsForm'].Ma.value;
	if(mats == "")                        
	{
		document.forms['tsForm'].dataerror.value = "Vui lòng nhập mã Loại chi phí trả trước.";
		return;              
	}
	
	var tents = document.forms['tsForm'].Ten.value;
	if(tents == "")
	{
		document.forms['tsForm'].dataerror.value = "Vui lòng nhập tên Loại chi phí trả trước.";
		return;
	}
	
	var tk = document.forms['tsForm'].tk.value;
	if(tk == "")
	{
		document.forms['tsForm'].dataerror.value = "Vui lòng nhập tài khoản kế toán.";
		return;
	}
	document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
	document.forms['tsForm'].action.value='save';
	document.forms["tsForm"].submit();
	
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

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="tsForm" method="post" action="../../Erp_LoaiCCDCUpdateSvl" charset ="UTF-8">
<input type="hidden" name="userId" value='<%=userId %>'>
<input type="hidden" name="userTen" value='<%=userTen %>'>
<INPUT name="action" type="hidden" value="">
<input type = "hidden" value = "<%=lccdcBean.getId() %>" name = "id">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">

					   	<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">Quản lý công cụ dụng cụ > Loại chi phí trả trước > Cập nhật </TD>
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
									<TD width="30" align="left"> <A href="/TraphacoHYERP/Erp_LoaiCCDCSvl" > <img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"> </A></TD>
								    <TD width="2" align="left" ></TD>
								    <TD width="30" align="left" >
								    <div id="btnSave">
								    <A href="javascript: submitform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A>
								    </div>
								    </TD>
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
			    				<textarea name="dataerror" style="width: 100%" readonly="readonly" rows="1" ><%=lccdcBean.getMsg()%></textarea>
								
								</FIELDSET>
						   </TD>
							</tr>	
							<tr>
						   <TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle">Loại chi phí trả trước
								</LEGEND>
								<TABLE  width="100%" cellspacing="0" cellpadding="6">		
									<TR>
									  <TD width="15%" class="plainlabel">Mã <FONT class="erroralert">*</FONT></TD>
									  <TD  class="plainlabel" ><INPUT type = "text" name="Ma" id = "ma" size="40"
										type="text" value="<%=lccdcBean.getMa() %>" >  
										
										</TD>
										
										
								    </TR>
	
									<tr>
									<td width = "15%" class = "plainlabel">Tên <font class = "erroralert">*</font> </td> 
									<td class = "plainlabel"> <input type = "text" name = "Ten" id = "ten" value="<%=lccdcBean.getTen() %>"> </td>
									</tr>
									
									<TR>
											  <TD class="plainlabel">Tài khoản chi phí trả trước<FONT class="erroralert"> *</FONT></TD>
											  <TD colspan="3" class="plainlabel">
											  <select name="tk" style="width: 300px" class="select2" >
												<option value="" ></option>
												<% try
												{
													while(tkList.next()){
							  						if (lccdcBean.getTkId().equals(tkList.getString("sohieutaikhoan")) || lccdcBean.getTkId().equals(tkList.getString("pk_seq"))){%>								  								
					  									<option value='<%= tkList.getString("pk_seq")%>' selected ><%= tkList.getString("SoHieuTaiKhoan") %> - <%= tkList.getString("tentaikhoan") %></option>
												<%	}
					  								else{ %>
					  									<option value='<%= tkList.getString("pk_seq")%>'><%= tkList.getString("SoHieuTaiKhoan") %> - <%= tkList.getString("tentaikhoan") %></option>
					  							<%	}}
													tkList.close();
						  						}catch (java.sql.SQLException e){} %>
											  </select></TD>
										  </TR>

									<TR>
									<TD class = "plainlabel">
									Hoạt động
									</TD>
									<TD class = "plainlabel">
									<%
									   if(lccdcBean.getTrangthai().equals("1")){ %>
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
<% lccdcBean.DBClose(); %>