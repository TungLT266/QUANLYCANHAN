<%@page import="geso.traphaco.erp.beans.cophieu.IErpCoPhieuList"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "geso.traphaco.erp.beans.cophieu.imp.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%
	IErpCoPhieuList cpList = (IErpCoPhieuList)session.getAttribute("nhList");
	ResultSet CpList = (ResultSet)cpList.getCpList();
	 int[] quyen = new  int[5];
		quyen = util.Getquyen("ErpCoPhieuSvl",userId);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
<title>Best - Co Phieu</title>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<meta http-equiv="Content-Style-Type" content="text/css">
<link rel="stylesheet" href="../css/main.css" type="text/css">
<link rel="stylesheet" href="../css/calendar.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
      $(document).ready(function() {            
            $( ".days" ).datepicker({                     
                        changeMonth: true,
                        changeYear: true                    
            });            
      });   
</script>

<script language="javascript" type="text/javascript">

function xoaform()
{
    document.nhListForm.MA.value = "";
    document.nhListForm.TEN.value = "";
    submitform();
}

function submitform()
{
	document.forms['nhListForm'].action.value= 'search';
	document.forms['nhListForm'].submit();
}

function newform()
{
	document.forms['nhListForm'].action.value= 'new';
	document.forms['nhListForm'].submit();
}

function thongbao()
{
	 var tt = document.forms["nhListForm"].msg.value;
	 if(tt.length>0)
    	alert(document.forms["nhListForm"].msg.value);
	}

</script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		$(".select2").select2();
	});
</script>

</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="nhListForm" method="post" action="../../ErpCoPhieuSvl">
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="userTen" value='<%=userTen%>'> 
<input type="hidden" name="action" value='1'>
<input type="hidden" name="msg" value='<%= cpList.getMsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 
<table width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	
	<tr>
		<td colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<table width="100%" cellpadding="0" cellspacing="1">		
				<tr>
					<td align="left">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
						   <td align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kế toán &gt; Danh mục cổ phiếu
						   </TD>
						   <td align = "right" colspan="2" class="tbnavigation">Chào mừng bạn <%=userTen %> &nbsp;</td>
						  </tr>
 					  </table>
					</td>
				</tr>
				<tr>
					<td>
					<table border="0" width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td width="100%" align="center" >
							<fieldset>
							  <legend class="legendtitle">Tiêu chí tìm kiếm &nbsp;</legend>
							<table width="100%" cellpadding="6" cellspacing="0">
								<tr>
								  	<td class="plainlabel" style="width: 150px">Mã cổ phiếu</td>
								    <TD class="plainlabel" style="width: 300px"><input type="text" name="MA" value="<%=cpList.getMA() %>" onchange="submitform()"></td>
								    <td class="plainlabel" style="width: 200px" >Công ty phát hành cổ phiếu</td>
								    <TD class="plainlabel"><input type="text" name="congtyphathanh" value="<%=cpList.getCongtyphathanh() %>" onchange="submitform()"></td>
								   
						      	</tr>
						      									
								<tr>
								 	<td class="plainlabel" style="width: 150px">Tên cổ phiếu</td>
								    <TD class="plainlabel" colspan="5"><input type="text" name="TEN" value="<%=cpList.getTEN() %>" onchange="submitform()"></td>
								  	
						      	</tr>
						      	
							    <tr>
									<td colspan="6" class="plainlabel">
									<a class="button2" href="javascript:xoaform()">
									<img style="top: -4px;" src="../images/button.png" alt="">Nhập Lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
									</td>									
								</tr>
								
							</table>

							</fieldset>
						</td>
						</tr>
					</table>
					</td>
				</tr>

				<tr>
					<td width="100%">
						<fieldset>
						<legend class="legendtitle">&nbsp;Danh mục cổ phiếu&nbsp;&nbsp;&nbsp;
						<%if(quyen[0]!=0){ %>
						<a class="button3" href="javascript:newform()">
    					<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>      
    					<%} %>                      
					 	</legend>
	
		   				<table width="100%" border="0" cellpadding="0" cellspacing="0">
			  				<tr>
			  	   				<td align="left" colspan="4" class="legendtitle">
									<table width="100%" border="0" cellspacing="1" cellpadding="4">
										<tr class="tbheader">
											<th width="6%">Mã</th>
											<th width="20%">Tên</th>	
											<th width ="25%">Tên cty phát hành</th>	
											<th width="9%">Trạng thái</th>										
											<th width="5%">Ngày tạo</th>
											<th width="9%">Người tạo</th>
											<th width ="5%">Ngày sửa</th>											
											<th width ="9%">Người sửa</th>			
											<th width="4%" align="center">&nbsp;Tác vụ</th>					
										</tr>
				
									<%
									int m = 0;
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									
									if(CpList != null)
									while (CpList.next())
									{
										if(m % 2 != 0) {%>
										<tr class = <%=lightrow%> >
									<%} else {%>
										<tr class = <%=darkrow%> >
									<%}%>
									
										<td align="center"><%=CpList.getString("MA")%> </td>
										<td align="left"><%=CpList.getString("TEN")%> </td>
										<td align="left"><%=CpList.getString("CONGTYPHATHANH")%> </td>
										<% 
										if(CpList.getString("trangthai").equals("1")){
										%>	
											<td align="center">Hoạt Động </td>
											<%
										}else{
											%>
											<td align="center">Ngưng Hoạt Động </td>
											<% 
										}
										%>								
										<td align="center"><%=CpList.getString("NGAYTAO")%> </td>
										<td align="center"><%=CpList.getString("NGUOITAO")%> </td>
										<td align="center"><%=CpList.getString("NGAYSUA")%> </td>										
										<td align="center"><%=CpList.getString("NGUOISUA")%> </td>
										
										
										<td align = "center">
											<table border = 0 cellpadding="0" cellspacing="0">
												<tr>
												<td>
												<%if(quyen[2]!=0){ %>
												<a href = "../../ErpCoPhieuUpdateSvl?userid=<%=userId%>&update=<%=CpList.getString("PK_SEQ")%>"><img src="../images/Edit20.png" title="Cập nhật" alt="Chinh sua" width="20" height="20" longdesc="Chinh sua" border = 0></A>&nbsp;
												<%} %>
												</td>
												<td>
												<%if(quyen[1]!=0){ %>
												<a href = "../../ErpCoPhieuSvl?userid=<%=userId%>&delete=<%=CpList.getString("PK_SEQ")%>"><img src="../images/Delete20.png" title="Xóa" alt="Xoa" width="20" height="20" longdesc="Xoa" border="0" onclick="if(!confirm('Bạn có muốn xóa ngân hàng này ?')) return false; "></a>&nbsp;
												<%} %>
												</td>
												<td>
												<!-- QUYEN VIEW DLN -->
												<a href = "../../ErpCoPhieuUpdateSvl?userid=<%=userId%>&display=<%=CpList.getString("PK_SEQ")%>"><img src="../images/Display20.png" title="Hiển thị" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
												<!-- END QUYEN VIEW DLN --> 
												</td>
												</tr>
											</table>				
										</tr>
									
									<% 	m++;
									}%>									
									
										<tr>
											<td height="26" colspan = "11" align="center" class="tbfooter">&nbsp;	</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>

					</fieldset>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</form>
</body>
</html>
<%
if(CpList != null) CpList.close();
cpList.DBClose();

session.setAttribute("nhList",null);
}%>