<%@page import="java.sql.ResultSet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.khovung.IKhoVung" %>
<%@ page  import = "geso.traphaco.erp.beans.khovung.IKhoVungList" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% IKhoVungList obj = (IKhoVungList)session.getAttribute("obj"); %>
<% ResultSet kvlist = obj.getKVRs(); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<TITLE>TraphacoHYERP - Project</TITLE>
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<SCRIPT language="javascript" type="text/javascript">
function clearform()
{
    document.kvForm.ma.value = "";
    document.kvForm.ten.value = "";      
    document.kvForm.trangthai.selectedIndex = 2;
    submitform();
}

function submitform()
{
	document.forms['kvForm'].action.value= 'search';
	document.forms['kvForm'].submit();
}

function newform()
{
	document.forms['kvForm'].action.value= 'new';
	document.forms['kvForm'].submit();
}
function thongbao()
{var tt = document.forms['kvForm'].msg.value;
	if(tt.length>0)
    alert(document.forms['kvForm'].msg.value);
	}
	

</SCRIPT>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="kvForm" method="post" action="../../KhoVungSvl">
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value='1'>

<input type="hidden" name="msg" value='<%=obj.getMsg() %>'>

<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" >
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kế toán &gt; Kho vùng </TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD> 
						    </tr>
   						</table>
					</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD>
						<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
							<TR>
								<TD width="100%" align="left"><FIELDSET>
									<LEGEND class="legendtitle">Tiêu chí tìm kiếm </LEGEND>

									<TABLE class="tblight" width="100%" cellpadding="6" cellspacing = "0">
										<TR>
										  <TD class="plainlabel" >Mã kho vùng </TD>
										  <TD class="plainlabel" ><INPUT name="ma" size="20" type="text" 
                                          					value="<%= obj.getMa()%>" onChange = "submitform();"></TD>
									  </TR>
										<TR>
											<TD width="20%" class="plainlabel" >Tên kho vùng </TD>
										    <TD width="80%" class="plainlabel" ><INPUT name="ten" size="40" type="text" 
                                            				value="<%= obj.getTen()%>" onChange = "submitform();" ></TD>
										</TR>
										<TR>
											<TD class="plainlabel" >Trạng thái</TD>
											<TD class="plainlabel" >
											  <select name="trangthai" onChange = "submitform();" >
											    <% if (obj.getTrangthai().equals("1")){%>
											  	<option value="1" selected>Hoạt động</option>
											  	<option value="0">Ngưng hoạt động</option>
											  	<option value="2"> </option>
											  
											<%}else if(obj.getTrangthai().equals("0")) {%>
											 	 <option value="0" selected>Ngưng hoạt động</option>
											  	<option value="1" >Hoạt động</option>
											 	 <option value="2" > </option>
											  
											<%}else{%>																					 	 
											  	<option value="1" >Hoạt động</option>
											  	<option value="0" >Ngưng hoạt động</option>
											  	<option value="2" selected> </option>
											<%}%>
									          </select></TD>
										</TR>
										<TR>
										    <TD class="plainlabel" colspan=4>
										    	<a class="button2" href="javascript:clearform()">
	<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại </a>&nbsp;&nbsp;&nbsp;&nbsp;	
			       </TD>
										</TR>
									</TABLE>
									</FIELDSET>
								</TD>	
							</TR>
						</TABLE>
					</TD>
				</TR>	
			</TABLE>
			<TABLE width="100%" cellpadding="0" cellspacing="1">
			    <TR>
					<TD align="left" >
						 <FIELDSET>
							<LEGEND class="legendtitle">&nbsp;Kho vùng &nbsp;&nbsp;&nbsp;
								<a class="button3" href="javascript:newform()">
    	<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>                            
						
						</LEGEND>
				
							<TABLE class="" width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="98%">
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
											<TR class="tbheader">
											  <TH width="12%">Mã </TH>
											  <TH width="22%">Tên </TH>
											  <TH width="11%">Trạng thái </TH>
											  <TH width="9%">Ngày tạo</TH>
											  <TH width="13%">Người tạo </TH>										
											  <TH width="9%">Ngày sửa </TH>
											  <TH width="14%">Người sửa</TH>
											  <TH width="10%">Tác vụ</TH>
										  </TR>
								<% 
								
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									if(kvlist!=null)
									
									while (kvlist.next()){
										
										
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
												<TD align="left"><div align="left"><%= kvlist.getString("MA") %></div></TD>                                   
												<TD><div align="left"><%= kvlist.getString("TEN") %></div></TD>
											  <% if (kvlist.getString("trangthai").equals("1")){ %>
													<TD align="left">Hoạt động</TD>
												<%}else{%>
													<TD align="left">Ngưng hoạt động</TD>
												<%}%>
												<TD align="center"><%=kvlist.getString("ngaytao")%></TD>
												<TD align="center"><%=kvlist.getString("nguoitao")%></TD>
												<TD align="center"><%=kvlist.getString("ngaysua")%></TD>
												<TD align="center"><%=kvlist.getString("nguoisua")%></TD>
												<TD align="center">
												<TABLE border = 0 cellpadding="0" cellspacing="0">
													<TR><TD>
													  <A href = "../../KhoVungUpdateSvl?userId=<%=userId%>&update=<%= kvlist.getString("id")%>">
                                               <img src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A>
													</TD>
													<TD>&nbsp;</TD>
													<TD>
													  <A href = "../../KhoVungSvl?userId=<%=userId%>&delete=<%=kvlist.getString("id")%>">
                                                <img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa kho vùng này ?')) return false;"></A></TD>
													</TR></TABLE>
												</TD>
											</TR>
								<%m++; }%>
								
									<TR>	
									<TD height="" colspan="11" align="center" class="tbfooter">
									&nbsp;</TD>
								</TR>
							</TABLE>
							</TD>
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
<%
	obj.closeDB();
	if(kvlist != null) kvlist.close();
	session.setAttribute("obj", null) ;  
	
	}%>