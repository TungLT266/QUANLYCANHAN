<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "geso.traphaco.center.beans.phuongxa.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ int[] quyen = new  int[5];
		quyen = util.Getquyen("PhuongxaSvl","14",userId);
		
		System.out.println(quyen[0]);
		System.out.println(quyen[1]);
		System.out.println(quyen[2]);
		System.out.println(quyen[3]);	
		System.out.println(quyen[4]);
		
		
		
		%>

<% IPhuongxaList obj = (IPhuongxaList)session.getAttribute("obj");

obj.setNextSplittings(); 

%>
<% ResultSet ttRs = (ResultSet)obj.getTinhthanhRs(); %>
<% ResultSet qhRs = (ResultSet)obj.getQuanhuyenRs(); %>
<% ResultSet pxRs = (ResultSet)obj.getPhuongxaRs(); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>TTC - Project</TITLE>

<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" src="../scripts/phanTrang.js"></script>
<SCRIPT language="javascript" type="text/javascript">
function clearform()
{
	
	//document.kvForm.VungMien.selectedIndex = 0;
    document.kvForm.tinhthanh.value = "";  
    document.kvForm.quanhuyen.value = ""; 
    document.kvForm.TrangThai.selectedIndex = 2;
    submitform();
}

function submitform()
{
	//alert("hello");
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
<form name="kvForm" method="post" action="../../PhuongxaSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="msg" value='<%=obj.getMsg() %>'>
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >
<input type="hidden" name="currentPage" value="<%= obj.getCurrentPage() %>" >

<script language="javascript" type="text/javascript">
    thongbao();
</script> 
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
		<TABLE width="100%" cellpadding="0" cellspacing="1">
			
				<TR>
					<TD align="left">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
						   <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Kinh doanh > Phường xã </TD>
   
						   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD>
						  </tr>
 					  </table>
					</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" cellpadding="0" cellspacing="0">			
				<TR>
					<TD>
					<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="100%" align="center" >
							<FIELDSET>
							
							  <LEGEND class="legendtitle">Tiêu chí tìm kiếm &nbsp;</LEGEND>
						    
							<TABLE  width="100%" cellpadding="6" cellspacing="0">
								<TR>
								    <TD width="20%" class="plainlabel">Tỉnh thành</TD>
								      <TD width="80%" class="plainlabel"><SELECT name="tinhthanh" onChange = "submitform();">
								      	<option value='' ></option>
								        <% try{ while(ttRs.next()){ 
								    	if(ttRs.getString("pk_seq").equals(obj.getTinhthanhId())){ %>
								      		<option value='<%= ttRs.getString("pk_seq") %>' selected> <%= ttRs.getString("ten") %></option>
								      	<%}else{ %>
								     		<option value='<%= ttRs.getString("pk_seq") %>'> <%= ttRs.getString("ten") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>
								      </SELECT></TD>
								      
							      
								</TR>
								
								<TR>
									<TD width="20%" class="plainlabel">Quận huyện</TD>
								      <TD width="80%" class="plainlabel"><SELECT name="quanhuyen" onChange = "submitform();">
								      	<option value='' ></option>
								        <% try{ while(qhRs.next()){ 
								    	if(qhRs.getString("pk_seq").equals(obj.getQuanhuyenId())){ %>
								      		<option value='<%= qhRs.getString("pk_seq") %>' selected> <%= qhRs.getString("ten") %></option>
								      	<%}else{ %>
								     		<option value='<%= qhRs.getString("pk_seq") %>'> <%= qhRs.getString("ten") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>
								      </SELECT></TD>
								</TR>
								
								  <TR>
								    <TD class="plainlabel">Trạng thái </TD>
								    <TD class="plainlabel"><SELECT name="TrangThai" onChange = "submitform();" >
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
											
                                    </SELECT></TD>
						      </TR>
							    <TR>
									
									<TD colspan="2" class="plainlabel">
									<a class="button2" href="javascript:clearform()"> 
									<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
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
			<TABLE width="100%" cellpadding="0" cellspacing="0">			
				<TR>
					<TD width="100%">
						<FIELDSET>
							<LEGEND class="legendtitle">&nbsp;Phường xã &nbsp;&nbsp;
							<%if(quyen[0]!=0){ %>
									<a class="button3" href="javascript:newform()">
    	<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>                            
				<%} %>
						</LEGEND>
	
						    <TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
									<TH width="13%">Diễn giải </TH>
									<TH width="13%">Tỉnh thành </TH>
									<TH width="13%">Quận huyện </TH>
									<TH width="8%">Trạng thái </TH>
									<TH width="6%">Ngày tạo </TH>
									<TH width="10%">Người tạo </TH>
									<TH width="6%">Ngày sửa</TH>
									<TH width="10%">Người sửa </TH>
									<TH width="6%" align="center">&nbsp;Tác vụ</TH>
								</TR>
							
								<% 
								if(pxRs != null)
								{
								
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									while (pxRs.next()){
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
												<TD align="center"><div align="center"><%= pxRs.getString("TENPX") %></div></TD>
											    <TD align="center"><div align="center"><%= pxRs.getString("TENTT") %></div></TD>
											    <TD align="center"><div align="center"><%= pxRs.getString("TENQH") %></div></TD>
											                             
												<% if (pxRs.getString("TRANGTHAI").equals("1")){ %>
													<TD align="center">Hoạt động </TD>
												<%}else{%>
													<TD align="center">Ngưng hoạt động</TD>
												<%}%>
												<TD align="center"><%= pxRs.getString("NGAYTAO") %></TD>
												<TD align="center"><%= pxRs.getString("NGUOITAO") %></TD>
												<TD align="center"><%= pxRs.getString("NGAYSUA") %></TD>
												<TD align="center"><%= pxRs.getString("NGUOISUA") %></TD>
										 	
												<TD align="center">
												<TABLE border = 0 cellpadding="0" cellspacing="0">
													<TR><TD>
													<%if(quyen[2]!=0){ %>
														<A href = "../../PhuongxaUpdateSvl?userId=<%=userId%>&update=<%=pxRs.getString("PK_SEQ")%>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>
													<%} %>
													</TD>
													<TD>&nbsp;</TD>
													<TD>
													<%if(quyen[1]!=0){ %>
														<A href = "../../PhuongxaSvl?userId=<%=userId%>&delete=<%=pxRs.getString("PK_SEQ")%>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa thông tin phường xã này ?')) return false;"></A>
														<%} %>
														</TD>
													</TR></TABLE>
												</TD>
											</TR>
								<% m++; }} %>		
								
								<tr class="tbfooter">
														<TD align="center" valign="middle" colspan="13"
															class="tbfooter">
															<%if(obj.getNxtApprSplitting() >1) {%> <img alt="Trang Dau"
															src="../images/first.gif" style="cursor: pointer;"
															onclick="View('kvForm', 1, 'view')"> &nbsp; <%}else {%>
															<img alt="Trang Dau" src="../images/first.gif">
															&nbsp; <%} %> <% if(obj.getNxtApprSplitting() > 1){ %> <img
															alt="Trang Truoc" src="../images/prev.gif"
															style="cursor: pointer;"
															onclick="View('kvForm', eval(kvForm.nxtApprSplitting.value) -1, 'view')">
															&nbsp; <%}else{ %> <img alt="Trang Truoc"
															src="../images/prev_d.gif"> &nbsp; <%} %> <%
													int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%> <% 
												
												if(listPage[i] == obj.getNxtApprSplitting()){ %> <a
															style="color: white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
															<%}else{ %> <a
															href="javascript:View('kvForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
															<%} %> <input type="hidden" name="list"
															value="<%= listPage[i] %>" /> &nbsp; <%} %> <% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
															&nbsp; <img alt="Trang Tiep" src="../images/next.gif"
															style="cursor: pointer;"
															onclick="View('kvForm', eval(kvForm.nxtApprSplitting.value) +1, 'view')">
															&nbsp; <%}else{ %> &nbsp; <img alt="Trang Tiep"
															src="../images/next_d.gif"> &nbsp; <%} %> <%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
															<img alt="Trang Cuoi" src="../images/last.gif">
															&nbsp; <%}else{ %> <img alt="Trang Cuoi"
															src="../images/last.gif" style="cursor: pointer;"
															onclick="View('kvForm', -1, 'view')"> &nbsp; <%} %>
														</TD>
													</tr>
								
								
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
<%}%>