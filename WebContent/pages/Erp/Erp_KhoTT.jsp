<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.erp.beans.kho.IErp_KhoTTList"%>
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
		 quyen = util.Getquyen("ErpKhoTTSvl","&loaiNPP=0&isKH=0",userId);

		
		%>
<html>
<head>

<title>Best - Project</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<meta http-equiv="Content-Style-Type" content="text/css">
<link rel="stylesheet" href="../css/main.css" type="text/css">
<link rel="stylesheet" href="../css/calendar.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />

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

<%	IErp_KhoTTList khottlist=(IErp_KhoTTList)session.getAttribute("kttL");%>
<%	ResultSet rsKhoTTList=khottlist.getRSKhoList(); %>
<%	ResultSet rsCongty=khottlist.getRSCongty(); %>
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="khoForm" method="post" action="../../ErpKhoTTSvl">
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" id="msg" value="<%=khottlist.getMsg() %>">

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
						   <td align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kho vận &gt; Khai báo kho 		   
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
								  	<td width="10%" class="plainlabel">Mã kho</td>
								    <TD class="plainlabel"><input name="txtMakho" type="text" value="<%=khottlist.getMa() %>" onchange="submitform()"/></td>
						      	</tr>
						      	
						      	<tr>
								  	<td width="10%" class="plainlabel">Tên kho</td>
								    <TD class="plainlabel"><input  name="txtTenkho" type="text" value="<%=khottlist.getTen() %>" onchange="submitform()"/></td>
						      	</tr>
								
								<tr>
						      		<td width="10%" class = "plainlabel">Trạng thái</td>
									<td class = "plainlabel">
										<select name = "tt" onchange = "submitform()">
										<option  value="" selected="selected" > </option>
										<%if(khottlist.getTrangthai().equals("1")){ %>
										<option value = "1" selected="selected">Hoạt động</option>
										<option value = "0">Ngưng hoạt động</option>
										<%}else if(khottlist.getTrangthai().equals("0")){ %>
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
						<legend class="legendtitle">&nbsp;Thông tin kho &nbsp;&nbsp;&nbsp;
						<%if(quyen[0]!=0){ %>
						
 						<a class="button3" href="javascript:newform()"> 
     					<img style="top: -4px;display: none" src="../images/New.png" alt="">Tạo mới </a>
     					<%} %>
 					 	</legend>
	
		   				<table width="100%" border="0" cellpadding="0" cellspacing="0">
			  				<tr>
			  	   				<td align="left" colspan="4" >
									<table width="100%" border="0" cellspacing="1" cellpadding="4">
										<tr class="tbheader">
											<th align="center" width="5%">Mã Kho</th>
											<th >Tên Kho</th>
											<th align="center" width="5%">Phân khu</th>	
											<th align="center" width="17%">Công ty</th>	
											<th align="center" width="8%">Trạng thái</th>
											<th align="center" width="8%">Ngày tạo</th>
											<th align="center" width="10%">Người tạo</th>
											<th align="center" width ="8%">Ngày sửa</th>											
											<th align="center" width ="10%">Người sửa</th>		
											<th align="center" width="10%" align="center">Tác vụ</th>					
										</tr>
										<%try
											{
											int m = 0;
											String lightrow = "tblightrow";
											String darkrow = "tbdarkrow";
											while(rsKhoTTList.next())
											{
												if (m % 2 != 0) {%>						
												<TR class= <%=lightrow%> >
											<%} else {%>
												<TR class= <%= darkrow%> >
											<%}%>
											<td align="left"><%=rsKhoTTList.getString("MA")%></td>
											<td align="left"><%=rsKhoTTList.getString("TEN")%></td>
											<%if(rsKhoTTList.getString("QUANLYBIN").equals("1")) {%>
											<td align="center">Có</td>
											<%}else{ %>
											<td align="center">Không</td><%} %>
											<td align="left"><%=rsKhoTTList.getString("CONGTY")%></td>
											<td align="center">
												<% if( rsKhoTTList.getString("TRANGTHAI").equals("1")){ %> 
													 	Hoạt động 
												<% }else{ %>
												   		Ngưng hoạt động
												<%}  %></td>
											<td align="center"><%=rsKhoTTList.getString("NGAYTAO")%></td>
											<td align="center"><%=rsKhoTTList.getString("NGUOITAO")%></td>
											<td align="center"><%=rsKhoTTList.getString("NGAYSUA")%></td>
											<td align="center"><%=rsKhoTTList.getString("NGUOISUA")%></td>											
											<td align="center">
												<table>
													<tr>
														<td>
													<%if(quyen[2]!=0){ %>
														<A href = "../../ErpKhoTTUpdateSvl?userId=<%=userId%>&action=update&khoId=<%=rsKhoTTList.getString("PK_SEQ")%>"><img title="Cập nhật" src="../images/Edit20.png" alt="Chinh sua" width="20" height="20" longdesc="Chinh sua" border = 0></A>&nbsp;
													<%} %>
														</td>
														<td>
													<%if(quyen[1]!=0){ %>
														<A href = "../../ErpKhoTTSvl?userId=<%=userId%>&action=delete&khoId=<%=rsKhoTTList.getString("PK_SEQ")%>"><img title="Xóa" src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa kho trung tâm này không ?')) return false;"></A>&nbsp;
													<%} %>
														</td>
													<td>
													<!-- QUYEN VIEW DLN --> 
														<A href = "../../ErpKhoTTUpdateSvl?userId=<%=userId%>&action=display&khoId=<%=rsKhoTTList.getString("PK_SEQ")%>"><img src="../images/Display20.png" title="Hiển thị" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
													<!-- END QUYEN VIEW DLN --> 
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
	if (rsKhoTTList != null ) rsKhoTTList.close();
	if (rsCongty != null) rsCongty.close();
	khottlist.DBClose() ;
	session.setAttribute("kttL", null) ; 
	
	}
%>


