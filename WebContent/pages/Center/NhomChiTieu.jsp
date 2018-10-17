<%@page import="java.sql.ResultSet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.nhomsanpham.INhomsanpham" %>
<%@ page  import = "geso.traphaco.center.beans.nhomsanpham.INhomsanphamList" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% INhomsanphamList obj = (INhomsanphamList)session.getAttribute("obj"); %>
<% ResultSet nsplist = obj.getNspList();
	int[] quyen = new  int[6];
	quyen = util.Getquyen("NhomchitieuSvl", "", userId);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">


<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {		
			$( ".days" ).datepicker({			    
					changeMonth: true,
					changeYear: true				
			});            
        }); 		
		
</script>


<SCRIPT language="JavaScript" type="text/javascript">
function submitform()
{
	 document.nspForm.action.value = "new";
   	 document.forms["nspForm"].submit();
}

function searchform()
{
	 document.nspForm.action.value = "search";
   	 document.forms["nspForm"].submit();
}

function clearform()
{
	document.nspForm.diengiai.value = "";
    document.nspForm.trangthai.selectedIndex = 0;
    document.nspForm.loainhom.value = "";
	document.nspForm.tungay.value = "";
	document.nspForm.denngay.value = "";
 	document.forms["nspForm"].submit();
}

function xuatExcel()
{
 	document.forms['nspForm'].action.value= 'excel';
 	document.forms['nspForm'].submit();
 	document.forms['nspForm'].action.value= '';
}
</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="nspForm" method="post" action="../../NhomchitieuSvl">
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value="1">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
				   		<table width="100%" border="0" cellpadding="0" cellspacing="0">
					  		<tr height="22">
						  		<TD align="left" colspan="2" class="tbnavigation">
						  			&nbsp;Dữ liệu nền sản phẩm > Nhóm chỉ tiêu</TD> 
						  		<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD>
						  	</tr>
						</table></TD>
			  	</TR>
			</TABLE>
			
			<TABLE width="100%" border="0" cellpadding="0">
				<TR>
					<TD >
						<FIELDSET><LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm &nbsp;</LEGEND>
							<TABLE  width="100%" cellspacing="0" cellpadding="6">
								
								<TR>
									<TD class="plainlabel">Diễn giải </TD>
								    <TD class="plainlabel" colspan="3" >
										<INPUT name="diengiai" type="text" value='<%=obj.getDiengiai()%>' onChange ="searchform();">
									</TD>
								</TR>
								<TR>								
									<TD style="width: 120px;" class="plainlabel" >Từ ngày </TD>
									<TD style="width: 250px;" class="plainlabel" >
										<input class="days" type="text" name="tungay" value='<%=obj.getTungay() %>'  size="20" onchange = "searchform();">																			
  									</TD>
				  							 							  							 
							  	  <TD style="width: 120px;" class="plainlabel" >Đến ngày </TD>
								  <TD class="plainlabel" >
					 				  <input class="days" type="text" name="denngay" value='<%=obj.getDenngay() %>' size="20" onchange = "searchform();">
					  			  </TD>	 
							  </TR>
							  
							  <TR>
							  	  <TD class="plainlabel">Loại nhóm </TD>
								  <TD class="plainlabel">
								  	<select name="loainhom" onChange = "searchform();">
								      <%-- <% if (nspBean.getLoainhom().equals("0")){ %>                                
		                                	<OPTION value="0" selected>Nhóm bình thường</OPTION>
		                              <% } else { %>
		                              		<OPTION value="0" >Nhóm bình thường</OPTION>
		                              <% } %>   --%>
		                              <% if (obj.getLoainhom().equals("1")){ %>                                
		                                	<OPTION value="1" selected>Nhóm đặc trị</OPTION>
		                              <% } else { %>
		                              		<OPTION value="1" >Nhóm đặc trị</OPTION>
		                              <% } %>	
		                              <% if (obj.getLoainhom().equals("2")){ %>                                
		                                	<OPTION value="2" selected>KPI đặc trị</OPTION>
		                              <% } else { %>
		                              		<OPTION value="2" >KPI đặc trị</OPTION>
		                              <% } %>
		                              <% if (obj.getLoainhom().equals("3")){ %>                                
		                                	<OPTION value="3" selected>Nhóm chiến lược</OPTION>
		                              <% } else { %>
		                              		<OPTION value="3" >Nhóm chiến lược</OPTION>
		                              <% } %>	
		                              <% if (obj.getLoainhom().equals("4")){ %>                                
		                                	<OPTION value="4" selected>KPI chiến lược</OPTION>
		                              <% } else { %>
		                              		<OPTION value="4" >KPI chiến lược</OPTION>
		                              <% } %>	
								   	</select>
								  </TD>				
							  	  <TD class="plainlabel">Trạng thái </TD>
								  <TD class="plainlabel">
									  	<SELECT name = "trangthai"  onChange = "searchform();">
									  	<% if (obj.getTrangthai().equals("0")){ %>
									    	<option value=""> </option>
									    	<option value="1">Hoạt động</option>
									    	<option value="0" selected>Ngưng hoạt động</option>
										<%}else{ 							
									  		if (obj.getTrangthai().equals("1")){ %>
									    	<option value=""> </option>
									    	<option value="1" selected>Hoạt động</option>
									    	<option value="0" >Ngưng hoạt động</option>
										<%}else{ %>
									    	<option value="" selected> </option>
									    	<option value="1" >Hoạt động</option>
									    	<option value="0" >Ngưng hoạt động</option>
										<%}} %>
	
										</SELECT>
									</TD>
							  </TR>
							  		
								<TR>
									<TD class="plainlabel" colspan="4">
										<a class="button2" href="javascript:clearform()"> <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
										<!-- <a class="button2" href="javascript:xuatExcel()"> <img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a> -->
                                    </TD>
                                    												
								</TR>
								
							</TABLE>
					  </FIELDSET>
					</TD>	
				</TR>
			</TABLE>
			
			<TABLE width="100%" border = "0" cellpading = "0" cellspacing = "0">
				<TR>
					<TD width="100%">
					<FIELDSET>
					<LEGEND class="legendtitle">&nbsp;Nhóm chỉ tiêu &nbsp;&nbsp;&nbsp;
						<%if(quyen[Utility.THEM] != 0) {%>
							<a class="button3" href="javascript:submitform()" >
	    						<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>   
	    				<%} %>                         		
					</LEGEND>
					<TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
									<TH width="10%">Mã nhóm</TH>
									<TH width="30%">Tên nhóm </TH>
									<TH width="9%">Trạng thái</TH>
									<TH width="8%">Ngày tạo </TH>
									<TH width="12%">Người tạo </TH>
									<TH width="9%">Ngày sửa</TH>
									<TH width="12%">Người sửa</TH> 
									<TH width="9%">Tác vụ</TH>
								</TR>
								
								<% if( nsplist != null ) { 
									
									int m = 0;
									while( nsplist.next() )
									{
										if((m % 2 ) == 0) {%>
				                         	<TR class='tbdarkrow'>
				                        <%}else{ %>
				                          	<TR class='tblightrow'>
				                        <%} %>
										
										<TD ><%= nsplist.getString("pk_seq")  %></TD>
										<TD ><%= nsplist.getString("ten") %></TD>
										
										<% if(nsplist.getString("trangthai").equals("1")) {%>
											<TD align="center">Hoạt động</TD>
										<%}else {%>
											<TD align="center">Ngưng hoạt động</TD>
										<%} %>
										<TD ><%= nsplist.getString("ngaytao") %></TD>
										<TD ><%= nsplist.getString("nguoitao") %></TD>
										<TD ><%= nsplist.getString("ngaysua") %></TD>
										<TD ><%= nsplist.getString("nguoisua") %> </TD>
										<TD >
											
											<A href = "../../NhomchitieuUpdateSvl?userId=<%=userId%>&update=<%= nsplist.getString("pk_seq") %>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>
										
											<A href = "../../NhomchitieuSvl?userId=<%=userId%>&delete=<%= nsplist.getString("pk_seq") %>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn muốn xóa nhóm sản phẩm này?')) return false;"></A>
									
										</TD>
									</TR>	
										
									<% m++; }
									nsplist.close();
								}  %>
							
								<TR>
									<TD align="center" colspan="12" class="tbfooter">&nbsp;</TD>
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
if(obj!=null)obj.DBClose();
	
	}%>