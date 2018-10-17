<%@page import="java.sql.ResultSet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.mucchietkhau.*" %>
<%@ page  import = "geso.traphaco.center.beans.mucchietkhau.imp.*" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	
	NumberFormat formatter = new DecimalFormat("#,###,###"); 
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% IChietkhauList obj = (IChietkhauList)session.getAttribute("obj"); %>
<% ResultSet congnoRs = obj.getCongnoList();
	int[] quyen = new  int[6];
	quyen = util.Getquyen("ChietkhauSvl","",userId);

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<TITLE>Acecook - Project</TITLE>
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<SCRIPT language="javascript" type="text/javascript">

 function clearform()
 {
     document.ckForm.sotien.value = "";
     document.ckForm.chietkhau.value = "";
     //document.ckForm.TrangThai.selectedIndex = 2;
     submitform();
 }

 function submitform()
 {
 	document.forms['ckForm'].action.value= 'search';
 	document.forms['ckForm'].submit();
 }

 function newform()
 {
 	document.forms['ckForm'].action.value= 'new';
 	document.forms['ckForm'].submit();
 }
 function thongbao()
 {var tt = document.forms['ckForm'].msg.value;
 	if(tt.length>0)
     alert(document.forms['ckForm'].msg.value);
 	}
 function DinhDangTien(num) 
 {
    num = num.toString().replace(/\$|\,/g,'');
    if(isNaN(num))
    num = "0";
    sign = (num == (num = Math.abs(num)));
    num = Math.floor(num*100+0.50000000001);
    num = Math.floor(num/100).toString();
    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
    num = num.substring(0,num.length-(4*i+3))+','+
    num.substring(num.length-(4*i+3));
    return (((sign)?'':'-') + num);
}
 function Dinhdang(element)
	{
		element.value=DinhDangTien(element.value);
		if(element.value== '' ||element.value=='0' )
		{
			element.value= '';
		}
	} 
 
 
 function xuatExcel()
 {
 	document.forms['ckForm'].action.value= 'excel';
 	document.forms['ckForm'].submit();
 	document.forms['ckForm'].action.value= '';
 }
 
</SCRIPT>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="ckForm" method="post" action="../../ChietkhauSvl">
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value='1'>

<input type="hidden" name="msg" value='<%=obj.getMsg() %>'>

<script language="javascript" type="text/javascript">
    thongbao();
</script> 
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top'>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" >
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">
							   		&nbsp;Dữ liệu nền > Kinh doanh > Công nợ đối tác</TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %>&nbsp;  </TD> 
						    </tr>
   						</table>
					</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD>
						<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
							<TR>
								<TD width="100%" align="left"><FIELDSET>
									<LEGEND class="legendtitle">Tiêu chí tìm kiếm  </LEGEND>

									<TABLE class="tblight" width="100%" cellpadding="6" cellspacing = "0">
										<TR>
										  <TD class="plainlabel" >Loại </TD>
										  <TD class="plainlabel" ><INPUT name="sotien" id="sotien" size="20" type="text" value="<%= obj.getSotien() %>" onChange="submitform();"></TD>
									  </TR>
										<TR>
											<TD width="20%" class="plainlabel" >Diễn giải </TD>
										    <TD width="80%" class="plainlabel" ><INPUT name="chietkhau" id="chietkhau" size="20" type="text" value="<%= obj.getChietkhau() %>"  onChange="submitform();"> </TD>
										</TR>
										<TR>
                                             <TD class="plainlabel" colspan=2> 
                                             <a class="button2" href="javascript:clearform()">
													<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
													
													<a class="button2" href="javascript:xuatExcel()"> <img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a>&nbsp;&nbsp;&nbsp;&nbsp;
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
			<TABLE width="100%" cellpadding="0" cellspacing="1">
			    <TR>
					<TD align="left" >
						 <FIELDSET>
							<LEGEND class="legendtitle">&nbsp;Công nợ đối tác &nbsp;&nbsp;
							<%if(quyen[0]!=0) {%>
							<a class="button3" href="javascript:newform()">
    							<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>                            
							<%} %>
						</LEGEND>
				
							<TABLE class="" width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="98%">
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
											<TR class="tbheader">
												<TH width="10%">Loại</TH>
												<TH width="30%">Diễn giải</TH>
											    <TH width="10%">Ngày tạo</TH>
											  <TH width="15%">Người tạo </TH>										
											  <TH width="10%">Ngày sửa</TH>
											  <TH width="15%">Người sửa</TH>
											  <TH width="10%">Tác vụ</TH>
										  </TR>
								<% 
									String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
									int m = 0;
									if(congnoRs!=null)
									while (congnoRs.next() ){

										if (m % 2 != 0) {%>						
											<TR class= <%=lightrow%> >
										<%} else {%>
											<TR class= <%= darkrow%> >
										<%}%>
												<TD align="center"><%= congnoRs.getString("loai") %></TD> 
												<TD align="left"><%= congnoRs.getString("diengiai") %></TD>                                   
												<TD align="left"><%= congnoRs.getString("ngaytao") %></TD>
												<TD align="left"><%= congnoRs.getString("nguoitao") %></TD>
												<TD align="left"><%= congnoRs.getString("ngaysua") %></TD>
												<TD align="left"><%= congnoRs.getString("nguoisua") %></TD>
												<TD align="center">
													
												<A href = "../../ChietkhauUpdateSvl?userId=<%=userId%>&update=<%= congnoRs.getString("pk_seq") %>">
	                                             				<img src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" width="20" height="20" longdesc="Cập nhật" border = 0></A>
												</TD>
											</TR>
										<% m++; }%>
								
									<TR>	
									<TD height="" colspan="11" align="center" class="tbfooter">
									&nbsp;</TD>
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
<% } %>