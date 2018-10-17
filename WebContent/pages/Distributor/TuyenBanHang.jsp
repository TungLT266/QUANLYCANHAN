<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.distributor.beans.tuyenbanhang.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ 

	int[] quyen = new  int[6];
		quyen = util.Getquyen("TuyenbanhangSvl","",userId);
	%>

<% ITuyenbanhangList obj = (ITuyenbanhangList)session.getAttribute("obj"); %>
<% ResultSet tbhlist = (ResultSet) obj.getTbhRs(); %>
<% ResultSet ddkd = (ResultSet)obj.getDdkd();  %>

<% obj.setNextSplittings(); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" src="../scripts/phanTrang.js"></script>
<SCRIPT language="javascript" type="text/javascript">
function clearform()
{
    document.tbhForm.tbhTen.value = "";
    document.tbhForm.mafast.value = "";
    document.tbhForm.ddkdTen.selectedIndex = 0;
    submitform();
}

function submitform()
{
	document.tbhForm.action.value= 'search';
	document.forms['tbhForm'].submit();
}

function newform()
{
	document.tbhForm.action.value= 'new';
	document.forms['tbhForm'].submit();
}
</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="tbhForm" method="post" action="../../TuyenbanhangSvl">
<input type="hidden" name="userId" value='<%=userId%>'>

<input type="hidden" name="action" value="1" >
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >

<input type="hidden" name="nppId" value='<%= obj.getNppId() %>'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
				<TR>
					<TD align="left" class="tbnavigation">
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							<TD align="left" colspan="2" class="tbnavigation">&nbsp;Thiết lập dữ liệu nền > Dữ liệu nền kinh doanh > Tuyến bán hàng
   							<TD colspan="2" align="right" class="tbnavigation">Chào mừng  <%= obj.getNppTen() %>&nbsp;&nbsp;</TD>
						  </tr>
						</table>
		  		</TR>
			</TABLE>

			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
				<TR>
					<TD >
						<FIELDSET><LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm&nbsp;</LEGEND>
						<TABLE  width="100%" cellspacing="0" cellpadding = "6">
							<TR>
								<TD width="10%" class="plainlabel">Tuyến bán hàng </TD>
								<TD width="20%" valign="middle" class="plainlabel">
		
												<input name="tbhTen" type="text" value="<%= obj.getTuyenbh() %>" size="40" onChange = "submitform();">

							  </TD>
							  <TD width="12%" class="plainlabel">Trình dược viên </TD>
								<TD class="plainlabel"><SELECT name="ddkdTen" onChange = "submitform();" >
								  <option value=""></option>
								  <% 
								  try{ while(ddkd.next()){ 
								    	if(ddkd.getString("ddkdId").equals(obj.getDdkdId())){ %>
								      		<option value='<%=ddkd.getString("ddkdId") %>' selected><%=ddkd.getString("ddkdTen") %></option>
								      	<%}else{ %>
								     		<option value='<%=ddkd.getString("ddkdId") %>'><%=ddkd.getString("ddkdTen") %></option>
								     	<%}}}catch(java.sql.SQLException e){}
								   %>
							    </SELECT></TD>
							</TR>
							<TR>
								
							    <TD class="plainlabel">Mã FAST </TD>
								<TD valign="middle" class="plainlabel">
									<input name="mafast" type="text" value="<%= obj.getMafast() %>" size="40" onChange = "submitform();">
								</TD>
							  	<TD class="plainlabel">Mã / Tên khách hàng</TD>
								<TD valign="middle" class="plainlabel">
		
												<input name="makh" type="text" value="<%= obj.getMakh() %>" size="40" onChange = "submitform();">

							  </TD>
							</TR>
							<TR>
							    <TD class="plainlabel" colspan="4" >
							    
                               <a class="button2" href="javascript:clearform()">
	<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
                               

                                </TD>
							</TR>
						</TABLE>
						</FIELDSET>
					</TD>	
				</TR>
			</TABLE>
			<TABLE width = "100%" cellpadding="0" cellspacing="0">
				<TR>
					<TD width="100%">
						<FIELDSET>
						<LEGEND class="legendtitle">&nbsp;Tuyến bán hàng &nbsp;&nbsp;&nbsp;
						<%if(quyen[Utility.THEM]!=0){ %>
							<a class="button3" href="javascript:newform()">
    						<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
    					<%} %>	                            
						</LEGEND>
						<TABLE class="" width="100%" cellpadding="0" cellspacing="0">
							<TR>
								<TD width="98%">
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="2">
										<TR class="tbheader">
											<TH width="">Tuyến bán hàng </TH>
											<TH width="">Trình dược viên</TH>
											<TH width="">Ngày tạo</TH>
											<TH width="">Người tạo</TH>
											<TH width="">Ngày sửa </TH>
											<TH width="">Người sửa </TH>
											<TH width="">Tác vụ</TH>
										</TR>
	
								<% 
									ITuyenbanhang tbh = null;
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									if(tbhlist!=null)
									while (tbhlist.next())
									{
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
												<TD align="left"><div align="left"><%= tbhlist.getString("tbhTen") %></div></TD>                                   
												<TD><div align="center"><%=  tbhlist.getString("ddkdTen") %></div></TD>
												<TD align="center"><%= tbhlist.getString("ngaytao")%></TD>
												<TD align="center"><%= tbhlist.getString("nguoitao") %></TD>
												<TD align="center"><%= tbhlist.getString("ngaysua")%></TD>
												<TD align="center"><%= tbhlist.getString("nguoisua") %></TD>
												<TD align="center">
												<TABLE border = 0 cellpadding="0" cellspacing="0">
													<TR>
													<TD>
													<%if(quyen[Utility.THEM]!=0){ %>
                      										<A href = "../../TuyenbanhangUpdateSvl?userId=<%=userId%>&copy=<%=  tbhlist.getString("tbhId") %>"><IMG src="../images/copy20.png" alt="Copy" title="Copy" border="0"></A>
                      								<%} %>
                      								</TD>
													
													<TD>
													<%if(quyen[Utility.SUA]!=0){ %>
													
														<A href = "../../TuyenbanhangUpdateSvl?userId=<%=userId%>&update=<%=  tbhlist.getString("tbhId") %>"><img src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A>
													<%} %>
													</TD>
													
													<%if(quyen[Utility.SUA]!=0){ %>
													
														<TD>&nbsp;<A href="../../TuyenbanhangMoveSvl?userId=<%=userId%>&move=<%=  tbhlist.getString("tbhId") %>" title="Chuyen Tuyen"><img src="../images/convert.gif" alt="Chuyen Tuyen" width="20" height="20" longdesc="Chuyen Tuyen" border = 0></A></TD>
													<%} %>
													<TD>&nbsp;
													<%if(quyen[Utility.XOA]!=0){ %>
														<A href = "../../TuyenbanhangSvl?userId=<%=userId%>&delete=<%=  tbhlist.getString("tbhId") %>"><img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn muốn xóa tuyến bán hàng này?')) return false;"></A>
													<%} %>
													</TD>
													</TR></TABLE>
												</TD>
											</TR>
								<%m++; }%>
							
<tr class="tbfooter" > 
											 <TD align="center" valign="middle" colspan="13" class="tbfooter">
											 	<%if(obj.getNxtApprSplitting() >1) {%>
													<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('tbhForm', 1, 'view')"> &nbsp;
												<%}else {%>
													<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
													<%} %>
												<% if(obj.getNxtApprSplitting() > 1){ %>
													<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View('tbhForm', eval(tbhForm.nxtApprSplitting.value) -1, 'view')"> &nbsp;
												<%}else{ %>
													<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
												<%} %>
												
												<%
													int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%>
												
												<% 
												
												System.out.println("Current page:" + obj.getNxtApprSplitting());
												System.out.println("List page:" + listPage[i]);
												
												if(listPage[i] == obj.getNxtApprSplitting()){ %>
												
													<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
												<%}else{ %>
													<a href="javascript:View('tbhForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
												<%} %>
													<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
												<%} %>
												
												<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View('tbhForm', eval(tbhForm.nxtApprSplitting.value) + 1, 'view')"> &nbsp;
												<%}else{ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
												<%} %>
												<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
											   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
										   		<%}else{ %>
										   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View('tbhForm', -1, 'view')"> &nbsp;
										   		<%} %>
											</TD>
										 </tr>  


							</TABLE>
							</TD>
						</TR>
					</TABLE>
					</FIELDSET>
					</TD>
				</TR>
			</TBODY>
		</TABLE>

	</TR>
</TABLE>
</form>
</BODY>
</HTML>
<% 

	try{
	
		if(ddkd != null)
			ddkd.close();
		if(obj != null){
			obj.DBclose();
			obj = null;}	
		if(tbhlist!=null){
			tbhlist.close();
		}
		session.setAttribute("obj",null);
	}
	catch (Exception e) {}
	
%>
<%}%>