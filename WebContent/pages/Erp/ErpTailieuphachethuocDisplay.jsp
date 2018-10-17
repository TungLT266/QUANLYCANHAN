<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.List"%>
<%@page import="geso.traphaco.erp.beans.tailieuphachethuoc.IErpTaiLieuPhaCheThuoc"%>
<%@page import="geso.traphaco.erp.beans.tailieuphachethuoc.imp.ErpTaiLieuPhaCheThuoc_ThongTin"%>
<%@page import="geso.traphaco.center.util.*"%>

<%String userId = (String) session.getAttribute("userId");  
String userTen = (String) session.getAttribute("userTen");  	
String sum = (String) session.getAttribute("sum");
Utility util = (Utility) session.getAttribute("util");
if(!util.check(userId, userTen, sum)){
	response.sendRedirect("/TraphacoHYERP/index.jsp");
} else {
	IErpTaiLieuPhaCheThuoc obj = (IErpTaiLieuPhaCheThuoc)session.getAttribute("obj");
	List<ErpTaiLieuPhaCheThuoc_ThongTin> thongtinList = obj.getThongtinList();
	ResultSet sanphamRs = obj.getSanphamRs(); %>
	
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
<form name ="tailieuphachethuoc" method="post" action="" >
<input type="hidden" name="userId" value='<%= userId %>' >
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr height="22">
						  		<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Hồ sơ kiểm nghiệm > Tài liệu pha chế thuốc > Hiển thị</TD>
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
									<A href="../../ErpTaiLieuPhaCheThuocSvl?userId=<%=userId %>" >
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
							<LEGEND class="legendtitle" style="color:black">Thông tin tài liệu pha chế thuốc </LEGEND>
							<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
                          		<TR>
	                          		<TD class="plainlabel" valign="middle" width="200px" >Mã tài liệu</TD>   
			                        <TD class="plainlabel" valign="middle">
			                        	<input type="text" style="width:350px" readonly value="<%=obj.getMa() %>">
			                        </TD>
                  				</TR>
                  			
	                          	<TR>
	                          		<TD class="plainlabel" valign="middle" width="200px">Nội dung</TD>
			                        <TD class="plainlabel" valign="middle">
			                        	<input type="text" readonly style="width:350px" value="<%= obj.getNoidung() %>"  > 
			                        </TD>          
			                  	</TR>
			                  	
			                  	<TR>
	                          		<TD class="plainlabel" valign="middle" width="200px">Thuốc thử</TD>
			                        <TD class="plainlabel" valign="middle">
			                        	<select style="width: 350px" class="select2" disabled>
			                        		<option value=""></option>
			                        		<%if(sanphamRs != null){
			                        			sanphamRs.beforeFirst();
												while(sanphamRs.next()){ 
													if(sanphamRs.getString("pk_seq").equals(obj.getThuocthu())){ %>
														<option value="<%=sanphamRs.getString("pk_seq") %>" selected><%=sanphamRs.getString("Ten") %></option>
													<%} else { %>
														<option value="<%= sanphamRs.getString("pk_seq") %>" ><%=sanphamRs.getString("Ten") %></option>
													<%}
												}
											} %>
			                        	</select>
			                        </TD>          
			                  	</TR>
			                  	
			                  	<TR>
	                          		<TD class="plainlabel" valign="middle" width="200px">Công thức lý thuyết/Cách pha</TD>
			                        <TD class="plainlabel" valign="middle" style="width: 200px">
			                        	<input type="text" readonly style="width:350px" value="<%=obj.getCongthuc() %>"> 
			                        </TD>
			                  	</TR>
			                  	
			                  	<TR>
	                          		<TD class="plainlabel" valign="middle" width="200px">Trạng thái </TD>   
			                        <TD class="plainlabel" valign="middle" colspan="3">
			                            <%if(obj.getTrangthai().equals("1")){ %>
			                            	<input type="checkbox" name="trangthai" value="1" checked disabled><i> Hoạt động</i>
			                            <%} else { %>
			                            	<input type="checkbox" name="trangthai" value="1" disabled><i> Hoạt động</i>
			                            <%} %>
			                        </TD>                
			                  	</TR>
							</TABLE>
							
							<table cellpadding="0px" cellspacing="1px" width="100%">
								<tr class="tbheader">
									<th width="70%" >Sản phẩm</th>
									<th width="30%" >Số lượng</th>
								</tr>
								<%
								ErpTaiLieuPhaCheThuoc_ThongTin thongtin;
								for(int i=0; i<thongtinList.size(); i++){
									thongtin = thongtinList.get(i);
									%>
									<tr>
										<td>
				                        	<select style="width: 100%" class="select2" disabled>
				                        		<option value=""></option>
				                        		<%if(sanphamRs != null){
				                        			sanphamRs.beforeFirst();
													while(sanphamRs.next()){ 
														if(sanphamRs.getString("pk_seq").equals(thongtin.getSanphamid())){ %>
															<option value="<%=sanphamRs.getString("pk_seq") %>" selected><%=sanphamRs.getString("Ten") %></option>
														<%} else { %>
															<option value="<%= sanphamRs.getString("pk_seq") %>" ><%=sanphamRs.getString("Ten") %></option>
														<%}
													}
												} %>
				                        	</select> 
										<td>
											<input type="text" readonly style="width:100%;text-align:right" value="<%=thongtin.getSoluong() %>"> 
										</td>
									</tr>
								<%} %>
							</table>
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
