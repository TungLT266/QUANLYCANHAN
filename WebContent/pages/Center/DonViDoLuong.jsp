<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.donvidoluong.IDonvidoluong" %>
<%@ page  import = "geso.traphaco.center.beans.donvidoluong.IDonvidoluongList" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% IDonvidoluongList obj = (IDonvidoluongList)session.getAttribute("obj"); %>
<% ResultSet donvilist = (ResultSet)obj.getDonvilist(); 
	int[] quyen = new  int[6];
	quyen = util.Getquyen("DonvidoluongSvl","",userId);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">

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

<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<SCRIPT language="JavaScript" type="text/javascript">
function clearform()
{
	document.dvdlForm.dvdl.value = "";
    document.dvdlForm.diengiai.value = "";
    document.dvdlForm.tungay.value = "";
	document.dvdlForm.denngay.value = "";       
    document.dvdlForm.trangthai.selectedIndex = 0;
    submitform();
}

function submitform()
{
	document.forms['dvdlForm'].action.value= 'search';
	document.forms['dvdlForm'].submit();
}

function newform()
{
	document.forms['dvdlForm'].action.value= 'new';
	document.forms['dvdlForm'].submit();
}
function thongbao()
{var tt = document.forms['dvdlForm'].msg.value;
	if(tt.length>0)
    alert(document.forms['dvdlForm'].msg.value);
	}


function xuatExcel()
{
 	document.forms['dvdlForm'].action.value= 'excel';
 	document.forms['dvdlForm'].submit();
 	document.forms['dvdlForm'].action.value= '';
}
</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="dvdlForm" method="post" action="../../DonvidoluongSvl">
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value="1">
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
							   <TD align="left" colspan="2" class="tbnavigation">
							   		&nbsp;Dữ liệu nền > &nbsp;Sản phẩm &gt; Đơn vị đo lường </TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD> 
						    </tr>
						</table>
					</TD>
				</TR>
			</TABLE>
			<TABLE border="0" width="100%" cellpadding="0"  cellspacing="1">
							<TR>
								<TD width="100%" align="left"><FIELDSET>
									<LEGEND class="legendtitle">Tiêu chí tìm kiếm  </LEGEND>

									<TABLE class="tblight" width="100%" cellpadding="6" cellspacing = "0">
										<TR>
											<TD class="plainlabel" >Đơn vị đo lường</TD>
										  	<TD class="plainlabel" ><INPUT name="dvdl" type="text" size="40" value="<%= obj.getDvdl() %>" onChange="submitform();"></TD>
											<TD class="plainlabel" >Từ ngày </TD>
											<TD class="plainlabel">
												<TABLE cellpadding="0" cellspacing="0">
													<TR><TD>
														<input class="days" type="text" name="tungay" value='<%=obj.getTungay() %>'  size="20" onchange="submitform();">
													</TD>
													
													</TR>
												</TABLE>																					
		  									</TD>
										</TR>

										<TR>
											<TD class="plainlabel" >Diễn giải</TD>
										  	<TD class="plainlabel" ><INPUT name="diengiai" type="text" size="40" value="<%= obj.getDiengiai()%>" onChange="submitform();"></TD>
											<TD class="plainlabel" >Đến ngày </TD>
										  	<TD class="plainlabel" >
										  		<TABLE cellpadding="0" cellspacing="0">
										  			<TR>
										  				<TD>
										 					<input class="days" type="text" name="denngay" value='<%=obj.getDenngay() %>' size="20" onchange="submitform();">
										  				</TD>
										

											  		</TR>
											 	</TABLE>
											</TD>	 
										</TR>
										
										<TR>
											<TD class="plainlabel" >Trạng thái </TD>
										    <TD class="plainlabel" >
											    <select name="trangthai" onChange="submitform();">
													<% if (obj.getTrangthai().equals("1")){%>
													  <option value="2" selected> </option>
													  <option value="1" selected>Hoạt động</option>
													  <option value="0">Ngưng hoạt động</option>
													<%}else if(obj.getTrangthai().equals("0")) {%>
													  <option value="2" > </option>
													  <option value="0" selected>Ngưng hoạt động</option>
													  <option value="1" >Hoạt động</option>
													<%}else{%>											
													  <option value="2" selected> </option>
													  <option value="1" >Hoạt động</option>
													  <option value="0" >Ngưng hoạt động</option>
													<%}%>
											    </select>
									       </TD>
									      	<TD class="plainlabel" colspan="2">
										    	<a class="button2" href="javascript:clearform()"> <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
										    	<a class="button2" href="javascript:xuatExcel()"> <img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a>&nbsp;&nbsp;&nbsp;&nbsp;
											</TD> 
										</TR>
										
										
									</TABLE>
									</FIELDSET>
								</TD>	
							</TR>
				</TABLE>
				<TABLE width = 100% cellpadding="0" cellspacing="1">
			    <TR>
					<TD align="left" >
						<FIELDSET>
							<LEGEND class="legendtitle">&nbsp;Đơn vị đo lường sản phẩm &nbsp;&nbsp;
							<%if(quyen[Utility.THEM]!=0) {%>
							<a name="new" class="button3" href="javascript:newform()">
    	<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>  
							<%} %>
							
						</LEGEND>
				
							<TABLE class="" width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="98%">
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
											<TR class="tbheader">
												<TH width="4%">STT</TH>
											    <TH width="14%">Đơn vị đo lường</TH>
											    <TH width="10%">Diễn giải</TH>
											    <TH width="16%">Tình trạng </TH>
												<TH width="9%">Ngày tạo </TH>
												<TH width="16%">Người tạo </TH>
												<TH width="9%">Ngày sủa </TH>
												<TH width="16%">Người sửa </TH>
												<TH width="10%">Tác vụ</TH>
											</TR>
										<% 
											int m = 0;
											String lightrow = "tblightrow";
											String darkrow = "tbdarkrow";
											while (donvilist.next()){
												if (m % 2 != 0) {%>						
													<TR class= <%=lightrow%> >
												<%} else {%>
													<TR class= <%= darkrow%> >
												<%}%>
												<TD align="center"><%=m+1%></TD>
												<TD><%= donvilist.getString("donvi") %></TD>
												<TD align="center"><%= donvilist.getString("diengiai") %></TD>
												<%if (donvilist.getString("trangthai").equals("1")){ %>
													<TD align="center">Hoạt động</TD>
												<%}else{ %>
													<TD align="center">Ngưng hoạt động</TD>
												<%} %>
												<TD align="center"><%= donvilist.getString("ngaytao") %></TD>
												<TD align="center"><%= donvilist.getString("nguoitao") %></TD>
												<TD align="center"><%= donvilist.getString("ngaysua") %></TD>
												<TD align="center"><%= donvilist.getString("nguoisua") %></TD>
												<TD align="center">
												<TABLE border = 0 cellpadding="0" cellspacing="0">
													<TR><TD>
													<%if(quyen[Utility.SUA]!=0) {%>
														<A href = "../../DonvidoluongUpdateSvl?userId=<%=userId%>&update=<%=donvilist.getString("pk_seq")%>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>
													<%} %>
													</TD>
													<TD>&nbsp;</TD>
													<TD>
													<%if(quyen[Utility.XOA]!=0) {%>
														<A href = "../../DonvidoluongSvl?userId=<%=userId%>&delete=<%=donvilist.getString("pk_seq")%>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa đơn vị đo lường này ?')) return false;"></A>
													<%} %>
													</TD></TR>												
												</TABLE>												
												</TD>
											</TR>
										<%m++; }%>
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
<%	if(donvilist != null) donvilist.close();
	obj.DBClose();

	}%>