<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.daytruyensanxuat.imp.*" %>
<%@ page import="geso.traphaco.erp.beans.daytruyensanxuat.*" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@page import="java.sql.SQLException" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<%
 	IErpDaytruyensanxuat obj =(IErpDaytruyensanxuat)session.getAttribute("dtsxBean");
	ResultSet nmRs = obj.getNhamayRs();
	ResultSet gioRs = obj.getGioRs();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<SCRIPT language="JavaScript" type="text/javascript">
	function submitform()
	{
	    document.forms["dtsx"].submit();
	}
	
	function keypress(e) //Hàm dùng d? ngan ngu?i dùng nh?p các ký t? khác ký t? s? vào TextBox
	{    
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode; //IE
		else
			keypressed = e.which; //NON-IE, Standard
		
		if (keypressed < 48 || keypressed > 57)
		{ 
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46)
			{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}
	
	function save()
	{
	
	  document.forms["dtsx"].action.value = "save";
	  document.forms["dtsx"].submit(); 
    }
	
</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="dtsx" method="post" action="../../ErpDaytruyensanxuatUpdateSvl" >
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="id" value='<%= obj.getId() %>' >
<input type="hidden" name="action" value="0">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="1"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Sản xuất > Dây truyền sản xuất > Cập nhật</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="2" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../ErpDaytruyensanxuatSvl?userId=<%= userId%>" ><img src="../images/Back30.png" alt="Quay về"  title="Quay về" border="1" longdesc="Quay về" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD width="30" align="left" >
						    <div id="btnSave">
						    <A href="javascript: save()" ><IMG src="../images/Save30.png" title="Lưu lại" alt="Lưu lại" border = "1"  style="border-style:outset"></A>
						    </div>
						    </TD>
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
				
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="3"><%= obj.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Dây truyền sản xuất </LEGEND>
						<TABLE border="0" width="100%" cellpadding="10" cellspacing="0">
                          <TR>
                          		<TD class="plainlabel" valign="middle" width="150px" >Mã dây truyền sản xuất</TD>   
		                        <TD class="plainlabel" valign="middle" colspan = 3 >
		                        	<input type="text" name="madaytruyensanxuat" value="<%= obj.getMa() %>"  > 
		                        </TD>          
		                  </TR> 
                          <TR>
                          		<TD class="plainlabel" valign="middle" width="150px" >Diễn giải </TD>   
		                        <TD class="plainlabel" valign="middle" colspan = 3  >
		                        	<input type="text" name="diengiai" style = "width:300px" value="<%= obj.getDiengiai() %>"  > 
		                        </TD>          
		                  </TR> 
                          <TR>
                          		<TD class="plainlabel" valign="middle" >Nhà máy </TD>   
								<TD class="plainlabel" colspan = 3 >
									<SELECT name="nmId" style = "width:300px" onchange=submitform();>
													<OPTION VALUE = ""></OPTION>
									<% if(nmRs != null){
											try{
												while(nmRs.next()){
													if(nmRs.getString("NMID").equals(obj.getNhamayId())){ %>
														<OPTION VALUE = "<%= nmRs.getString("NMID") %>" SELECTED><%= nmRs.getString("TEN") %></OPTION>
									<%				}else{ 	%>
														<OPTION VALUE = "<%= nmRs.getString("NMID") %>" ><%= nmRs.getString("TEN") %></OPTION>
									<%				}
												}
											}catch(java.sql.SQLException e){}
										
										}
										
										%>	
									
										
									</SELECT>
								</TD>
		                  </TR> 
                          <TR>
                          		<TD class="plainlabel" valign="middle" >Giờ làm việc từ </TD>   
								<TD class="plainlabel" width = "5%" >
									<SELECT name="tugio" style = "width:100px" >
													<OPTION VALUE = "0"></OPTION>
									<% if(gioRs != null){
											try{
												while(gioRs.next()){
													if(gioRs.getString("ID").equals(obj.getTugio())){ %>
														<OPTION VALUE = "<%= gioRs.getString("ID") %>" SELECTED><%= gioRs.getString("GIO") %></OPTION>
									<%				}else{ 	%>
														<OPTION VALUE = "<%= gioRs.getString("ID") %>" ><%= gioRs.getString("GIO") %></OPTION>
									<%				}
												}
											}catch(java.sql.SQLException e){}
										
										}
										
										%>	
									
										
									</SELECT>
								</TD>
                          		<TD class="plainlabel" valign="left" width = "5%" >Đến </TD>   
								<TD class="plainlabel" valign="left" >
									<SELECT name="dengio" style = "width:100px" >
													<OPTION VALUE = "0"></OPTION>

									<% if(gioRs != null){
											try{
												gioRs.beforeFirst();
												while(gioRs.next()){
													if(gioRs.getString("ID").equals(obj.getDengio())){ %>
														<OPTION VALUE = "<%= gioRs.getString("ID") %>" SELECTED><%= gioRs.getString("GIO") %></OPTION>
									<%				}else{ 	%>
														<OPTION VALUE = "<%= gioRs.getString("ID") %>" ><%= gioRs.getString("GIO") %></OPTION>
									<%				}
												}
											}catch(java.sql.SQLException e){}
										
										}
										
										%>	
										
									</SELECT>
								</TD>
								
		                  </TR> 

                          <TR>
                          		<TD class="plainlabel" valign="middle" >Nghỉ trưa từ </TD>   
								<TD class="plainlabel" width = "5%" >
									<SELECT name="nghitruatu" style = "width:100px" >
													<OPTION VALUE = "0"></OPTION>
													
									<% 
										
										if(gioRs != null){
											try{
												gioRs.beforeFirst();
												while(gioRs.next()){
													if(gioRs.getString("ID").equals(obj.getNghitruaTu())){ %>
														<OPTION VALUE = "<%= gioRs.getString("ID") %>" SELECTED><%= gioRs.getString("GIO") %></OPTION>
									<%				}else{ 	%>
														<OPTION VALUE = "<%= gioRs.getString("ID") %>" ><%= gioRs.getString("GIO") %></OPTION>
									<%				}
												}
											}catch(java.sql.SQLException e){}
										
										}
										
										%>	
									
										
									</SELECT>
								</TD>
                          		<TD class="plainlabel" valign="left" width = "5%" >Đến </TD>   
								<TD class="plainlabel" valign="left" >
									<SELECT name="nghitruaden" style = "width:100px" >
													<OPTION VALUE = "0"></OPTION>

									<% if(gioRs != null){
											try{
												gioRs.beforeFirst();
												while(gioRs.next()){
													if(gioRs.getString("ID").equals(obj.getNghitruaDen())){ %>
														<OPTION VALUE = "<%= gioRs.getString("ID") %>" SELECTED><%= gioRs.getString("GIO") %></OPTION>
									<%				}else{ 	%>
														<OPTION VALUE = "<%= gioRs.getString("ID") %>" ><%= gioRs.getString("GIO") %></OPTION>
									<%				}
												}
											}catch(java.sql.SQLException e){}
										
										}
										
										%>	
										
									</SELECT>
								</TD>
								
		                  </TR> 
		                 
		                  <TR>
                          		<TD class="plainlabel" valign="middle" >Trạng thái </TD>   
		                        <TD class="plainlabel" valign="middle" colspan = 3 >
		                            <% if(obj.getTrangthai().equals("1")) { %>
		                            	<input type="checkbox" name = "trangthai" value="1" checked="checked" ><i> Hoạt động</i>
		                            <% } else { %>
		                            	<input type="checkbox" name = "trangthai" value="1" ><i> Hoạt động</i>
		                            <% } %>
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
try{
	if(gioRs != null) gioRs.close();
	if(nmRs != null) nmRs.close();
	session.setAttribute("dtsxBean", null) ; 
	obj.DbClose();
}catch (Exception ex)
{
	ex.printStackTrace();
}%>
<%}%>
