<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.distributor.beans.noptien.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ 
		int[] quyen = new  int[6];
		quyen = util.Getquyen("NoptienSvl","",userId);
	%>

<% INoptienList obj = (INoptienList)session.getAttribute("obj"); %>
<% ResultSet khlist = (ResultSet) obj.getKhList(); %>
<% ResultSet khRs = (ResultSet) obj.getKhRs(); %>
<% ResultSet nvgn = (ResultSet)obj.getNvgnRs(); %>
<% ResultSet nvbh = (ResultSet)obj.getNvbhRs();  %>
<%	NumberFormat formatter = new DecimalFormat("#,###,###"); %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});	
</script>
<SCRIPT language="javascript" type="text/javascript">
function clearform()
{
	document.khForm.ngaynop.value = "";
	document.khForm.nvbhId.value = "";
	document.khForm.nvgnId.value = "";
	document.khForm.khId.value = "";
	document.khForm.sochungtu.value = "";
	document.khForm.tungay.value = "";
	document.khForm.denngay.value = "";
	submitform();
}

function submitform()
{
	document.forms['khForm'].action.value= 'search';
	document.forms['khForm'].submit();
}

function newform()
{
	document.forms['khForm'].action.value= 'new';
	document.forms['khForm'].submit();
}

function thongbao()
{
	if(document.getElementById("msg").value != '')
		alert(document.getElementById("msg").value);
}

</SCRIPT>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2(); });
     
</script>
</HEAD>

<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="khForm" method="post" action="../../NoptienSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="nppId" value="<%= obj.getNppId() %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="currentPage" value="<%= obj.getCurrentPage() %>" >
<input type="hidden" name="msg" id="msg" value='<%= obj.getMsg() %>'>
<script type="text/javascript">
	thongbao();
</script>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" 	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
				<TR>
					<TD align="left" class="tbnavigation">
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  	<tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý công nợ > Nộp tiền</TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng  <%= userTen %> &nbsp;</TD>
							</tr>
						</table>
					</TD>
		  		</TR>	
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
				<TR>
					<TD >
						<FIELDSET><LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm&nbsp;</LEGEND>
							<TABLE class="tblight" width="100%" cellspacing="0" cellpadding="6">
								<TR>
									<TD class="plainlabel">Từ ngày</TD>
								    <TD class="plainlabel">
								    	<INPUT name="tungay" class="days" type="text" value="<%= obj.getTungay() %>" size="40" onChange = "submitform();"> 
								    </TD>
								    
								    <TD class="plainlabel">Đến ngày</TD>
								    <TD class="plainlabel">
								    	<INPUT name="denngay" class="days" type="text" value="<%= obj.getDenngay() %>" size="40" onChange = "submitform();"> 
								    </TD>
							    </TR>
								
								<TR>
									<TD class="plainlabel">Ngày nộp</TD>
								    <TD class="plainlabel">
								    	<INPUT name="ngaynop" class="days" type="text" value="<%= obj.getNgaynop() %>" size="40" onChange = "submitform();"> 
								    </TD>
								  	<TD class="plainlabel">Nhân viên giao nhận</TD>
								  	<TD class="plainlabel"><SELECT name="nvgnId" onChange = "submitform();" style="width: 200px">
								    	<option value=""> </option>
									    <% try{ while(nvgn.next()){ 
								    			if(nvgn.getString("pk_seq").equals(obj.getNvgnId())){ %>
								      				<option value='<%=nvgn.getString("pk_seq")%>' selected><%=nvgn.getString("ten") %></option>
								      			<%}else{ %>
								     				<option value='<%=nvgn.getString("pk_seq")%>'><%=nvgn.getString("ten") %></option>
								     			<%}}}catch(java.sql.SQLException e){} %>
									  	</SELECT>
									</TD>
		
								</TR>
								
								
								<TR>
								  	<TD class="plainlabel">Trình dược viên</TD>
								  	<TD class="plainlabel"><SELECT name="nvbhId" onChange = "submitform();" style="width: 200px">
								    	<option value=""> </option>
									    <% try{ while(nvbh.next()){ 
								    			if(nvbh.getString("pk_seq").equals(obj.getNvbhId())){ %>
								      				<option value='<%=nvbh.getString("pk_seq")%>' selected><%=nvbh.getString("ten") %></option>
								      			<%}else{ %>
								     				<option value='<%=nvbh.getString("pk_seq")%>'><%=nvbh.getString("ten") %></option>
								     			<%}}}catch(java.sql.SQLException e){} %>
									  	</SELECT>
									</TD>
									<TD class="plainlabel">Khách hàng</TD>
								  	<TD  class="plainlabel"><SELECT name="khId" onChange = "submitform();" style="width: 200px">
								    	<option value=""> </option>
									    <% try{ while(khRs.next()){ 
								    			if(khRs.getString("pk_seq").equals(obj.getKhId())){ %>
								      				<option value='<%=khRs.getString("pk_seq")%>' selected><%=khRs.getString("ten") %></option>
								      			<%}else{ %>
								     				<option value='<%=khRs.getString("pk_seq")%>'><%=khRs.getString("ten") %></option>
								     			<%}}}catch(java.sql.SQLException e){} %>
									  	</SELECT>
									</TD>
								  </TR>
			
								  <TR>					  
								 	<TD class="plainlabel">Số chứng từ </TD>
								    <TD class="plainlabel" colspan="3">
								    	<INPUT name="sochungtu" type="text" value="<%= obj.getSochungtu() %>" size="40" onChange = "submitform();"> 
								    </TD> 
								  </TR>
								

								
								<TR>
								    <TD class="plainlabel" colspan="4">
								    	<a class="button2" href="javascript:clearform()">
										<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	    
								    <!--  
                                      <INPUT name="reinitialiser" type="button" value="Nhap lai" onClick="clearform();"> 
                                     --> </TD>
								</TR>
							</TABLE>
					  </FIELDSET>
					</TD>	
				</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellspacing="0" cellpadding="2">
				<TR>
					<TD width="100%">
					<FIELDSET>
					<LEGEND class="legendtitle">&nbsp;Nộp tiền &nbsp;&nbsp;&nbsp;
					<%if(quyen[Utility.THEM]!=0){ %>
					<a class="button3" href="javascript:newform()">
    				<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
    				<%} %>                            
					
					<!-- <INPUT name="new" type="button" value="Tao moi" onClick="newform();"> -->
					</LEGEND>
					<TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
									<TH width="7%">Số chứng từ</TH>
									<TH width="7%">Số phiếu thu</TH>
									<TH width="17%">NVGN/TDV/Khách hàng</TH>
									<TH width="8%">Ngày nộp</TH>
									<TH width="10%">Số tiền</TH>
									<TH width="10%">Trạng thái</TH>
									<TH width="8%">Ngày tạo</TH>
									<TH width="8%">Người tạo</TH>
									<TH width="8%">Ngày sửa</TH>
									<TH width="8%">Người sửa</TH> 
									<TH width="10%">Tác vụ</TH>
								</TR>
								
						<%  														
	                        int m = 0;
	                        String lightrow = "tblightrow";
	                        String darkrow = "tbdarkrow";
							if(khlist!=null)
							{%>
							<% try{								
                                while (khlist.next()){
                                    	
                                   	if (m % 2 != 0) {%>                     
                                    	<TR class= <%=lightrow%> >
                                    <%} else {%>
                                       	<TR class= <%= darkrow%> >
                                    	<%}
                                    	%>
											<TD align="left"><div align="center"><%=khlist.getString("PK_SEQ") %></div></TD> 
											<TD align="left"><div align="center"><%=khlist.getString("SOIN") %></div></TD>     
											<TD align="left"><div align="left"><%=khlist.getString("doituong") %></div></TD>                                                   
											<TD align="center"><div align="left"><%= khlist.getString("ngaynop")%></div></TD>
											<TD align="center"><div align="right"><%= formatter.format(khlist.getDouble("sotien"))%></div></TD>
											<%
											if (khlist.getString("trangthai").equals("0")){ %>
												<TD align="center">Chưa xác nhận</TD>
											<%}else{
											    if(khlist.getString("trangthai").equals("1")){%>
												 <TD align="center">Đã xác nhận</TD>
												 <%}else{ %>
												 <TD align="center">Đã hủy</TD>
											<%}}%>
											
											<TD align="left"><%=khlist.getString("ngaytao")%></TD>
											<TD align="left"><%=khlist.getString("nguoitao")%></TD>
											<TD align="center"><%=khlist.getString("ngaysua")%></TD>
											<TD align="center"><%=khlist.getString("nguoisua")%></TD>
											<TD align="center">
											<TABLE border = 0 cellpadding="0" cellspacing="0">
												<TR>
												 <%if(khlist.getString("trangthai").equals("0")){ %>
													<TD>
														<%if(quyen[Utility.SUA]!=0){ %>
														<A href = "../../NoptienUpdateSvl?userId=<%=userId%>&update=<%=khlist.getString("pk_seq") %>"><img src="../images/Edit20.png" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border = 0></A>
														<%} %>
													</TD>
													<TD>&nbsp;</TD>
													<TD>
														<%if(quyen[Utility.CHOT]!=0){ %>
														<A href = "../../NoptienSvl?userId=<%=userId%>&chot=<%=khlist.getString("pk_seq") %>"><img src="../images/Chot.png" alt="Chot" width="20" height="20" longdesc="Chot" border=0 onclick="if(!confirm('Ban Muon Chot Phieu Nay?')) return false;"></A>
														<%} %>
													</TD>
													<TD>&nbsp;</TD>
													<TD>
														<%if(quyen[Utility.XOA]!=0){ %>
														<A href = "../../NoptienSvl?userId=<%=userId%>&delete=<%=khlist.getString("pk_seq") %>"><img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Ban Muon Xoa Phieu Nay?')) return false;"></A>
														<%} %>
													</TD>
												<%}else if (khlist.getString("trangthai").equals("1")) { %>
													<TD>
														<%if(quyen[Utility.XEM]!=0){ %>
														<A href = "../../NoptienUpdateSvl?userId=<%=userId%>&display=<%=khlist.getString("pk_seq") %>"><img src="../images/Display20.png" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
														<%} %>
													</TD>
													<TD>&nbsp;</TD>
													<TD>
														<%if(quyen[Utility.XOA]!=0){ %>
														<A href = "../../NoptienSvl?userId=<%=userId%>&delete=<%=khlist.getString("pk_seq") %>"><img src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Ban Muon Xoa Phieu Nay?')) return false;"></A>
														<%} %>
													</TD>
												<%}else{ %>
													<TD>
														<%if(quyen[Utility.XEM]!=0){ %>
														<A href = "../../NoptienUpdateSvl?userId=<%=userId%>&display=<%=khlist.getString("pk_seq") %>"><img src="../images/Display20.png" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
														<%} %>
													</TD>
												<%} %>
												</TR></TABLE>
											</TD>
										</TR>
								<%m++; }}catch(java.sql.SQLException e){}
							}%>
								
										 <tr class="tbfooter" > 
											 <TD align="center" valign="middle" colspan="13" class="tbfooter">
											 <% obj.setNextSplittings(); %>
												 <script type="text/javascript" src="../scripts/phanTrang.js"></script>
												<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
												<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >

											 	<%if(obj.getNxtApprSplitting() >1) {%>
													<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, 1, 'view')"> &nbsp;
												<%}else {%>
													<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
													<%} %>
												<% if(obj.getNxtApprSplitting() > 1){ %>
													<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) -1, 'view')"> &nbsp;
												<%}else{ %>
													<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
												<%} %>
												
												<%
													int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%>
												
												<% 
												
												if(listPage[i] == obj.getNxtApprSplitting()){ %>
												
													<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
												<%}else{ %>
													<a href="javascript:View(document.forms[0].name, <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
												<%} %>
													<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
												<%} %>
												
												<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) +1, 'view')"> &nbsp;
												<%}else{ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
												<%} %>
												<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
											   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
										   		<%}else{ %>
										   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, -1, 'view')"> &nbsp;
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
						</TABLE>
					</TD>
				</TR>
			</TABLE>
		</form>
	</BODY>
</HTML>
<% 	

	try{
		
		if(khlist != null)
			khlist.close();
		if(nvbh != null)
			nvbh.close();
		if(nvgn != null)
			nvgn.close();

		if(obj != null){
			obj.DBclose();
			obj = null;
		}		
		
		session.setAttribute("obj",null);
	
	}
	catch (SQLException e) {}
	

%>
<%}%>