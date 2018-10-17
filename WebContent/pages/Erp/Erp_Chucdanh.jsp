<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.chucdanh.IChucdanhList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% IChucdanhList obj = (IChucdanhList)session.getAttribute("obj"); %>
<% ResultSet cdlist = (ResultSet)obj.getCdlist() ; %>
<% ResultSet nvlist = (ResultSet)obj.getNvList() ; 
int[] quyen = new  int[5];
quyen = util.Getquyen("Erp_ChucdanhSvl","",userId);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>TraphacoHYERP - Project</TITLE>
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

<SCRIPT language="javascript" type="text/javascript">


function clearform()
{
    document.cdForm.chucdanh.value = "";   
    document.cdForm.trangthai.selectedIndex = 0;
    submitform();
}

function submitform()
{
	document.forms['cdForm'].action.value= 'search';
	document.forms['cdForm'].submit();
}

function newform()
{
	document.forms['cdForm'].action.value= 'new';
	document.forms['cdForm'].submit();
}
function thongbao()
{var tt = document.forms['cdForm'].msg.value;
	if(tt.length>0)
    alert(document.forms['cdForm'].msg.value);
	}

</script>


</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="cdForm" method="post" action="../../Erp_ChucdanhSvl" charset="utf-8">
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
<input type="hidden" name="action" value='1'>
<input type="hidden" name="msg" value='<%=obj.getMsg() %>'>

<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
		<TABLE width="100%" cellpadding="0" cellspacing="1">
			
				<TR>
					<TD align="left">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
						   <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Cơ bản &gt; Chức danh </TD>
   
						   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen%>&nbsp;  </TD>
						  </tr>
 					  </table>
					</TD>
				</TR>
				<TR>
					<TD>
					<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="100%" align="center" >
							<FIELDSET>
							<LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm &nbsp;</LEGEND>

							<TABLE  width="100%" cellpadding="4" cellspacing="0">
								<TR>
									<TD width="19%" class="plainlabel" >Danh sách Chức danh</TD>
								    <TD width="81%" class="plainlabel" ><INPUT name="chucdanh" size="40" type="text" value='<%= obj.getChucdanh() %>' onChange="submitform();"></TD>
								</TR>
								
								<TR>
									<TD class="plainlabel">Trạng thái</TD>
									<TD class="plainlabel"><select name="trangthai" onChange="submitform();">
										  <option value="2"> </option>
										  <% if (obj.getTrangthai().equals("1")){ %>										  
										  		<option value="1" selected>Hoạt động</option>
										  		<option value="0">Ngưng hoạt động</option>
										  <%}else{
										  		if (obj.getTrangthai().equals("0")){ %>										  
										  			<option value="1" >Hoạt động</option>
										  			<option value="0" selected>Ngưng hoạt động</option>
										  		<%}else{%>	
										  			<option value="1">Hoạt động</option>
										  			<option value="0">Ngưng hoạt động</option>
										  		<%}%>
										   <%}%>
										  </select>
									</TD>
                                                                                                                 
							  </TR>
								<TR>
									<TD class="plainlabel">
									<a class="button2" href="javascript:clearform()">
	<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
									
                                  </TD>								
									<TD class="plainlabel">&nbsp;</TD>										
								</TR>
							</TABLE>

							</FIELDSET>
							</TD>
						</TR>
					</TABLE>
					</TD>
				</TR>

				<TR>
					<TD >
						<FIELDSET>
							<LEGEND class="legendtitle">&nbsp;Chức danh &nbsp; &nbsp; &nbsp;
							<%if(quyen[0]!=0){ %>
							<a class="button3" href="javascript:newform()">
    	<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>                            
							<%} %>
						</LEGEND>
						<TABLE width="100%" cellpadding="0" cellspacing="0">
							<TR><TD>
								<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
									<TH width="15%">Chức danh </TH>															
									<TH width="12%">Người được bổ nhiệm</TH>
									<TH width="10%">Trạng thái </TH>
									<TH width="10%" align="center">&nbsp;Tác vụ</TH>
								</TR>
								<%	 
									
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									try{
										while (cdlist.next()){
										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
										<TD align="center"><div align="left"><%=cdlist.getString("CHUCDANH") %></div></TD>
										<TD align="center"><div align="left"><%=cdlist.getString("TEN") %></div></TD>
										
									 <% if (cdlist.getString("trangthai").equals("1")){ %>
												<TD align="center">Hoạt động </TD>
									 <%}else{ %>
												<TD align="center">Ngưng hoạt động </TD>
									 <%} %>
									
									<TD align="center">
										<TABLE border = 0 cellpadding="0" cellspacing="0">
											<TR>
											<TD>
												<%if(quyen[2]!=0){ %>
												<A href = "../../Erp_ChucdanhUpdateSvl?userId=<%=userId%>&cdId=<%=cdlist.getString("CDID")%>" ><img title="Cập nhật"src="../images/Edit20.png" alt="Chinh sua" width="20" height="20" longdesc="Chinh sua" border = 0></A>&nbsp;
												<%} %>
											</TD>
											<TD>
												<%if(quyen[0]!=0){ %>
												<A href = "../../Erp_ChucdanhSvl?userId=<%=userId%>&delete=<%=cdlist.getString("CDID")%>"><img title="Xóa" src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa chức danh này?')) return false;"></A>&nbsp;
												<%} %>
											</TD>
											<TD>
												<!-- QUYEN VIEW DLN -->
												<A href = "../../Erp_ChucdanhUpdateSvl?userId=<%=userId%>&display=<%=cdlist.getString("CDID")%>" ><img src="../images/Display20.png" title="Hiển thị" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
												<!-- END QUYEN VIEW DLN -->
											</TD>
											</TR>
										</TABLE>												
									 </TD>
									</TR>
									<% 	m++; 
										}
									}catch(java.sql.SQLException e){}%>
								
								<TR>
									<TD align="center" colspan="11" class="tbfooter">&nbsp;	</TD>
								</TR>
							</TABLE>
							</TD>
						</TR>
					</TABLE>

					</FIELDSET>
					</TD>
				</TR>

		</TABLE>

	</TR>
</TABLE>
</form>
</BODY>
</HTML>
<%

	 if (cdlist != null) cdlist.close() ; 
	 if (nvlist != null) nvlist.close();
	 obj.DBClose();
	 session.setAttribute("obj",null);
	}
%>