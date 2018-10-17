<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.nhomthuong.INhomthuong" %>
<%@ page  import = "geso.traphaco.center.beans.nhomthuong.imp.Nhomthuong" %>
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

<% INhomthuong nkmBean = (INhomthuong)session.getAttribute("nkmBean"); 
	ResultSet spList = (ResultSet)nkmBean.getSpList(); 
	ResultSet spSelected = (ResultSet)nkmBean.getSpSelected();
	ResultSet dvkdList = (ResultSet)nkmBean.getDvkdList();
	ResultSet nhList = (ResultSet)nkmBean.getNhList();
	ResultSet clList = (ResultSet)nkmBean.getCLList();
	String dvkdId = (String) nkmBean.getDvkdId();
	String nhId = (String)nkmBean.getNhId();
	String clId = (String)nkmBean.getClId();
	ResultSet kenh = (ResultSet)nkmBean.getKenh();

	  %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<SCRIPT language="JavaScript" type="text/javascript">
function submitform()
{
    document.forms["nkmForm"].submit();
}
function xuatexel()
{
	document.nkmForm.action.value='xuatexel';
    document.forms["nkmForm"].submit();
}

function filterDvkd()
{
    document.nkmForm.action.value='filter';
    document.nkmForm.nhId.value='0';
    document.nkmForm.clId.value='0';
    document.forms["nkmForm"].submit();       
}

function filterNh()
{
    document.nkmForm.action.value='filter';
    document.nkmForm.clId.value='0';
    document.forms["nkmForm"].submit();   
    
}

function filterCl()
{
    document.nkmForm.action.value='filter';
    document.forms["nkmForm"].submit();       
}

function checkedAll(chk) {
	for(i=0; i<chk.length; i++){
	 	if(document.nkmForm.chon.checked==true){
			chk[i].checked = true;
		}else{
			chk[i].checked = false;
		}
	 }
}

</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="nkmForm" method="post">
		<input type="hidden" name="userId" value='<%=userId%>'> <input
			type="hidden" name="nkmId" value='<%= nkmBean.getId()  %>'> <input
			type="hidden" name="action" value="0">

		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản
											lý chỉ tiêu &gt; Nhóm thưởng &gt; Hiển thị</TD>
										<TD colspan="2" align="right" class="tbnavigation">Chào
											mừng bạn <%=userTen %>&nbsp;</TD>
									</tr>
								</table></TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR class="tbdarkrow">
										<TD width="30" align="left"><A
											href="../../NhomthuongSvl?userId=<%=userId %>"><img
												src="../images/Back30.png" alt="Quay ve" title="Quay ve"
												border="1" longdesc="Quay ve" style="border-style: outset">
										</A>
										</TD>
										<TD width="2" align="left"></TD>
										<TD width="30" align="left"><A
											href="javascript: xuatexel()"><IMG
												src="../images/excel.gif" width="30" title="Luu lai"
												alt="Luu lai" border="1" style="border-style: outset">
										</A>
										</TD>
										<TD>&nbsp;</TD>

									</TR>
								</TABLE></TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
									<LEGEND class="legendtitle">Báo lỗi nhập liệu</LEGEND>

									<textarea name="dataerror"  style="width: 100% ; font-weight:bold" style="width: 100%"
										readonly="readonly" rows="1"><%= nkmBean.getMessage() %></textarea>
									<% nkmBean.setMessage(""); %>
								</FIELDSET></TD>
						</tr>

						<TR>
							<TD height="100%" width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle" style="color: black">Thông
										tin nhóm thưởng </LEGEND>
									<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
										<TR>
											<TD width="30%" class="plainlabel">Tên nhóm thưởng
												<FONT class="erroralert"> *</FONT>
											</TD>
											<TD class="plainlabel"><INPUT type="text" name="ten"
												size="80" style="width: 250px" value='<%= nkmBean.getTen()%>'>
											</TD>
										</TR>
										<TR>
											<TD width="30%"  class="plainlabel">Diễn giải</TD>
											<TD class="plainlabel"><INPUT type="text"
												name="diengiai" style="width: 250px" size="80"
												value='<%= nkmBean.getDiengiai() %>'>
											</TD>
										</TR>




										<TR style="display: none">


											<TD colspan="2" class="plainlabel"><label> <% if(nkmBean.getTrangthai().equals("1")){ %>
													<input name="trangthai" type="checkbox" value="1" checked>
													<%}else{ %> <input name="trangthai" type="checkbox" value="0">
													<%} %> Hoạt động</label>
											</TD>
										</TR>

									</TABLE>
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
										<TR class="tbheader">
											<TH width="28%">Mã sản phẩm</TH>
											<TH width="60%">Tên sản phẩm</TH>
											<TH width="12%">Chọn <input type="checkbox" name="chon"
												onClick="checkedAll(document.nkmForm.sanpham)"></TH>
										</TR>

										<% ResultSet rs = null;
								   
								   String lightrow = "tblightrow";
								   String darkrow = "tbdarkrow";
								   int m = 0;
						   	       rs = spSelected;
									   
									   if (!(rs == null)){			
										    
									   		while (rs.next()){								   			
												if (m % 2 != 0) {%>
										<TR class=<%=lightrow%>>
											<%  } else {%>
										
										<TR class=<%= darkrow%>>
											<%  } %>
											<TD align="left" class="textfont"><%= rs.getString("ma") %></TD>
											<TD align="center"><div align="left"><%= rs.getString("ten")%></div>
											</TD>
											<TD align="center"><input type="checkbox" name="sanpham"
												value='<%= rs.getString("pk_seq") %>' checked></TD>

										</TR>

										<% 			m++;}
										}	
									   
									%>
									<tr class="tbfooter" ><TD align="center" colspan="10"></TD></tr>
									</TABLE>
								</FIELDSET></TD>
						</TR>
					</TABLE></TD>
			</TR>
		</TABLE>
	</form>
</BODY>
</HTML>
<%  
	if(spList != null) spList.close(); 
	if(spSelected != null) spSelected.close();
	if(dvkdList != null) dvkdList.close();
	if(nhList != null) nhList.close();
	if(clList != null) clList.close() ; %>
<%}%>