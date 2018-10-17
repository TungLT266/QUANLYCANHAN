<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.erp.beans.loaihoso.IErpLoaiHoSo"%>
<%@page import="geso.traphaco.center.util.*"%>

<%String userId = (String) session.getAttribute("userId");  
String userTen = (String) session.getAttribute("userTen");  	
String sum = (String) session.getAttribute("sum");
Utility util = (Utility) session.getAttribute("util");
if(!util.check(userId, userTen, sum)){
	response.sendRedirect("/TraphacoHYERP/index.jsp");
} else {
	IErpLoaiHoSo obj = (IErpLoaiHoSo)session.getAttribute("obj");%>
	
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
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

<SCRIPT language="JavaScript" type="text/javascript">
	function download()
	{
		document.forms["loaihoso"].action.value = "download";
		document.forms['loaihoso'].submit();
	}
</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="loaihoso" method="post" action="../../ErpLoaiHoSoUpdateSvl" >
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="id" value='<%= obj.getId() %>' >
<input type="hidden" name="action" value="0">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr height="22">
							  	<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Hồ sơ kiểm nghiệm > Biểu mẫu hồ sơ > Hiển thị</TD>
							  	<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD>
							</tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
			
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD>
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
							<TR class = "tbdarkrow">
								<TD width="30" align="left">
									<A href="../../ErpLoaiHoSoSvl?userId=<%=userId %>" >
										<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset">
									</A>
								</TD>
								<TD >&nbsp; </TD>						
							</TR>
						</TABLE>
					</TD>
				</TR>
			</TABLE>
			
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<TR>
				  	<TD height="100%" width="100%">
						<FIELDSET>
							<LEGEND class="legendtitle" style="color:black">Biểu mẫu hồ sơ </LEGEND>
							<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
                          		<TR>
	                          		<TD class="plainlabel" valign="middle" width="200px" >Mã ID</TD>
			                        <TD class="plainlabel" valign="middle"  >
			                        	<input readonly="readonly" type="text" style="width:300px" name="maloaihoso"  value="<%=obj.getMaLoaihoso() %>">
			                        </TD>
                  				</TR>
                  				
                  				<TR>
	                          		<TD class="plainlabel" valign="middle" width="200px" >Mã biểu mẫu</TD>
			                        <TD class="plainlabel" valign="middle"  >
			                        	<input type="text" style="width:300px" name="mabieumau" readonly="readonly" value="<%=obj.getMaBieumau() %>">
			                        </TD>
                  				</TR>
                  			
	                          	<TR>
	                          		<TD class="plainlabel" valign="middle" width="200px">Diễn giải</TD>
			                        <TD class="plainlabel" valign="middle"  >
			                        	<input type="text" style="width:300px" name="diengiai" readonly="readonly" value="<%= obj.getDiengiai() %>"  > 
			                        </TD>          
			                  	</TR>
			                  	
			                  	<TR>
	                          		<TD class="plainlabel" valign="middle" width="200px">Ngày ban hành</TD>
			                        <TD class="plainlabel" valign="middle"  >
			                        	<input type="text" style="width:150px" name="ngaybanhanh" value="<%= obj.getNgaybanhanh() %>" readonly="readonly" > 
			                        </TD>          
			                  	</TR>
			                  	
			                  	<TR>
	                          		<TD class="plainlabel" valign="middle" width="200px">Loại mẫu in</TD>
			                        <TD class="plainlabel" valign="middle"  >
			                        	<select name="loaimauin" style="width:300px">
											<option value="0" <%if(obj.getLoaimauin().equals("0")){ %>selected<%} else { %>disabled<%} %>>Khác</option>
											<option value="1" <%if(obj.getLoaimauin().equals("1")){ %>selected<%} else { %>disabled<%} %>>Hồ sơ lấy mẫu</option>
											<option value="2" <%if(obj.getLoaimauin().equals("2")){ %>selected<%} else { %>disabled<%} %>>Hồ sơ kiểm nghiệm</option>
											<option value="3" <%if(obj.getLoaimauin().equals("3")){ %>selected<%} else { %>disabled<%} %>>Phiếu kiểm nghiệm</option>
										</select>
			                        </TD>          
			                  	</TR>
			                  	
			                  	<TR>
	                          		<TD class="plainlabel" valign="middle" width="200px">Upload biểu mẫu</TD>
			                        <TD class="plainlabel" valign="middle">
			                        	<input type="hidden" name="bieumau_path" value="<%=obj.getBieumauPath() %>">
			                        	<input type="text" style="width:225px" readonly name="bieumau_name" value="<%=obj.getBieumauName() %>">
			                        	<%if(obj.getBieumauPath().trim().length() > 0){ %>
			                        		&nbsp;&nbsp;&nbsp;&nbsp;<a class="button2"  href="javascript:download();"><img style="top: -4px;" src="../images/button.png" alt="">Download Biểu mẫu</a>
			                        	<%} %>
			                        </TD>
			                  	</TR>
		                  	
			                  	<TR>
	                          		<TD class="plainlabel" valign="middle" width="200px">Trạng thái </TD>   
			                        <TD class="plainlabel" valign="middle" >
			                            <%if(obj.getTrangthai().equals("1")){ %>
			                            	<input type="checkbox" name="trangthai" value="1" checked disabled="disabled"><i> Hoạt động</i>
			                            <%} else { %>
			                            	<input type="checkbox" name="trangthai" value="1" disabled="disabled"><i> Hoạt động</i>
			                            <%} %>
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
	if (obj != null) {
		obj.DBClose();
		obj = null;
	}
	session.removeAttribute("obj");
} %>
