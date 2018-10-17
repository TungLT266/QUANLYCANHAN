<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.erp.beans.khoanmucchietkhau.IKhoanmucchietkhauList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "geso.traphaco.center.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{  
		 int[] quyen = new  int[5];
		 quyen = util.Getquyen("Erp_KhoanmucchietkhauSvl","",userId);
		%>
<html>
<head>

<title>Best - Project</title>
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
   
	document.forms['khoForm'].txtMakho.value= '';
	document.forms['khoForm'].txtTenkho.value= '';
	
	document.forms['khoForm'].tt.value= '';
	
	document.forms['khoForm'].action.value= 'search';
	document.forms['khoForm'].submit();
}

function submitform()
{
	document.forms['khoForm'].action.value= 'search';
	document.forms['khoForm'].submit();
}

function newform()
{
	document.forms['khoForm'].action.value= 'new';
	document.forms['khoForm'].submit();
}
function thongbao()
{
	if(document.getElementById("msg").value != ''&& document.getElementById("msg").value!=null)
	{alert(document.getElementById("msg").value);
	}
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

<script type="text/javascript">
        $(document).ready(function(){
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button2").hover(function(){
                $(".button2 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button3").hover(function(){
                $(".button3 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
    </script>

<%	IKhoanmucchietkhauList kmckList=(IKhoanmucchietkhauList)session.getAttribute("kmckL");%>
<%	ResultSet rsKMCK=kmckList.getRsKMCK(); %>
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="khoForm" method="post" action="../../Erp_KhoanmucchietkhauSvl">
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" id="msg" value="<%=kmckList.getMsg() %>">
<input type="hidden" name="chixem" value='<%= kmckList.getChixem() %>'>

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
						   <td align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kế toán &gt; Khoản mục chiết khấu 		   
						   </td>
   
						 <td align = "right" colspan="2" class="tbnavigation">Chào mừng bạn <%=userTen %> &nbsp;</td>
						  </tr>
 					  </table>
					</td>
				</tr>
				<tr>
					<td>
					<table border="0" width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td width="100%" align="left" >
							<fieldset>
							  <legend class="legendtitle">Tiêu chí tìm kiếm &nbsp;</legend>
							<table width="100%" cellpadding="6" cellspacing="0">
								<tr>
								  	<td width="20%" class="plainlabel">Mã khoản mục chiết khấu</td>
								    <TD class="plainlabel"><input name="maCK" type="text" value="<%=kmckList.getMa() %>" onchange="submitform()"/></td>
						      	</tr>
						      	
						      	<tr>
								  	<td width="20%" class="plainlabel">Tên khoản mục chiết khấu</td>
								    <TD class="plainlabel"><input  name="tenCK" type="text" value="<%=kmckList.getTen() %>" onchange="submitform()"/></td>
						      	</tr>
								
								<tr>
						      		<td width="20%" class = "plainlabel">Trạng thái</td>
									<td class = "plainlabel">
										<select name = "tt" onchange = "submitform()" style="width:200px" class="select2">
										<option  value="" selected="selected" > </option>
										<%if(kmckList.getTrangthai().equals("1")){ %>
										<option value = "1" selected="selected">Hoạt động</option>
										<option value = "0">Ngưng hoạt động</option>
										<%}else if(kmckList.getTrangthai().equals("0")){ %>
										<option value = "1" >Hoạt động</option>
										<option value = "0" selected="selected">Ngưng hoạt động</option>
										<%}else{ %>
										<option value = "1" >Hoạt động</option>
										<option value = "0" >Ngưng hoạt động</option>
										<% }%>
										</select>
									</td>
								</tr>

							    <tr>
									<td colspan="2" class="plainlabel">
									<a class="button2" href="javascript:xoaform()">
									<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
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
						 <legend class="legendtitle">&nbsp;Khoản mục chiết khấu &nbsp;&nbsp;&nbsp;
						<%if(quyen[0]!=0 ){ %>
						
 						<a class="button3" href="javascript:newform()"> 
     					<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
     					<%} %>
 					 	</legend> 
	
		   				<table width="100%" border="0" cellpadding="0" cellspacing="0">
			  				<tr>
			  	   				<td align="left" colspan="4" >
									<table width="100%" border="0" cellspacing="1" cellpadding="4">
										<tr class="tbheader">
											<th align="center" width="5%">Mã </th>
											<th align="center" width="15%">Tên khoản mục chiết khấu</th>											
											<th align="center" width="10%">Trạng thái</th>
											<th align="center" width="10%">Ngày tạo</th>
											<th align="center" width="10%">Người tạo</th>
											<th align="center" width ="10%">Ngày sửa</th>											
											<th align="center" width ="10%">Người sửa</th>		
											<th align="center" width="5%" align="center">Tác vụ</th>					
										</tr>
										<%try
											{
											int m = 0;
											String lightrow = "tblightrow";
											String darkrow = "tbdarkrow";
											while(rsKMCK.next())
											{
												if (m % 2 != 0) {%>						
												<TR class= <%=lightrow%> >
											<%} else {%>
												<TR class= <%= darkrow%> >
											<%}%>
											<td align="center"><%=rsKMCK.getString("MA")%></td>
											<td align="center"><%=rsKMCK.getString("TEN")%></td>
											
											<td align="center">
												<% if( rsKMCK.getString("TRANGTHAI").equals("1")){ %> 
													 	Hoạt động 
												<% }else{ %>
												   		Ngưng hoạt động
												<%}  %></td>
											<td align="center"><%=rsKMCK.getString("NGAYTAO")%></td>
											<td align="center"><%=rsKMCK.getString("NGUOITAO")%></td>
											<td align="center"><%=rsKMCK.getString("NGAYSUA")%></td>
											<td align="center"><%=rsKMCK.getString("NGUOISUA")%></td>											
											<td align="center">
												<TABLE border = 0 cellpadding="0" cellspacing="0">
													<tr>
														<td>
													<%if(quyen[2]!=0 && kmckList.getChixem().equals("0" )&& rsKMCK.getString("TRANGTHAI").equals("1") ){ %>
														<A href = "../../Erp_KhoanmucchietkhauUpdateSvl?userId=<%=userId%>&action=update&mucCK=<%=rsKMCK.getString("PK_SEQ")%>"><img title="Cập nhật" src="../images/Edit20.png" alt="Chinh sua" width="20" height="20" longdesc="Chinh sua" border = 0></A>&nbsp;
													<%} %>
														</td>
														<td>
													<%if(quyen[1]!=0 && kmckList.getChixem().equals("0")&& rsKMCK.getString("TRANGTHAI").equals("1") ){ %>
														<A href = "../../Erp_KhoanmucchietkhauSvl?userId=<%=userId%>&action=delete&mucCK=<%=rsKMCK.getString("PK_SEQ")%>"><img title="Xóa" src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa khoản mục chiết khấu này không ?')) return false;"></A>&nbsp;
													<%} %>
														</td>
														<td>
													<!-- QUYEN VIEW DLN -->
														<A href = "../../Erp_KhoanmucchietkhauUpdateSvl?userId=<%=userId%>&action=display&mucCK=<%=rsKMCK.getString("PK_SEQ")%>"><img src="../images/Display20.png" title="Hiển thị" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
													<!-- QUYEN VIEW DLN -->
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<%m++;}}catch(Exception e){} %>
										<tr>
											<TD align="center" valign="middle" colspan="13" class="tbfooter">
												&nbsp;	
											</TD>
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
	if (rsKMCK != null ) rsKMCK.close();
	kmckList.DBClose() ;
	}
%>


