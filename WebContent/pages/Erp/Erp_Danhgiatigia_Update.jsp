<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "geso.traphaco.erp.beans.danhgiatigia.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<% IDanhgiatigia dgtigiaBean = (IDanhgiatigia)session.getAttribute("dgtigiaBean"); %>

<% ResultSet kqlist = (ResultSet) dgtigiaBean.getKqRS(); %>
<% String ghidao = dgtigiaBean.getGhidao(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  

	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/SalesUp/index.jsp");
	}else{	
		 int[] quyen = new  int[5];
		 quyen = util.Getquyen("151",userId);


%>
<%  NumberFormat formatter = new DecimalFormat("#,###,###.##"); %>    

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>TraphacoHYERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
<script type="text/javascript" src="../scripts/maskedinput.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<style type="text/css">

#diengiai {

    width: 300px;
}
#tab tr:HOVER{
cursor:pointer;
background: #E2F0D9;
}
#tabc tr input:HOVER{
cursor:pointer;
background: #E2F0D9;
}
</style>


<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript" language="javascript">
	$(document).ready(function() {
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});
	


	var flag=false;
	function isNumberKey(evt)
	{
	   var charCode = (evt.which) ? evt.which : event.keyCode;
	   if (charCode > 31 && (charCode < 48 || charCode > 57) && charCode!='46')
	      return false;
	
	   return true;
	}
	
	function isNumberKey2(evt)
	{
	   var charCode = (evt.which) ? evt.which : event.keyCode;
	   if (charCode > 31 && (charCode < 48 || charCode > 57) )
	      return false;
	
	   return true;
	}
	
	 function thuchienform()
	 { 
		 document.forms['dgtigiaForm'].action.value='thuchien';
	     document.forms['dgtigiaForm'].submit();
	 }

	function submitform()
	 { 
	    document.forms['dgtigiaForm'].submit();
	 }

	function saveform()
	 { 
		document.forms['dgtigiaForm'].action.value= 'Save';
	    document.forms['dgtigiaForm'].submit();
	 }
	 
</script>
	
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" onload="toBottom()">
<form name="dgtigiaForm" method="post" action="../../Erp_danhgiatigia_UpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="Id" value='<%= dgtigiaBean.getId()%>'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">

    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">Quản lý kế toán > Đánh giá tỉ giá > Cập nhật
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
	<div>
				<table width="100%" border="0" cellpadding="3" cellspacing="0">
				<tr>
					<td>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr class = "tbdarkrow">
						<td width="30" align="left"> <a href="/TraphacoHYERP/Erp_danhgiatigiaSvl?userId=<%=userId %>" > <img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"> </a></td>
					    <td width="2" align="left" ></td>
					    <% if(dgtigiaBean.getNam().length() >0 ){ %>
						<td width="30" align="left" ><a href="javascript: chotform()" ><img src="../images/Chot.png" title="Chốt" alt="Chốt" border = "1"  style="border-style:outset"></a></td>
						<%} %>
						<td>&nbsp;</td>						
					</tr>
					</table>
					</td>
				</tr>
			</table>
   	</div>
  	<div align="left" style="width:100%%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  rows="1" readonly="readonly" style ="width:100%%"><%= dgtigiaBean.getMsg() %></textarea>
		         <% dgtigiaBean.setMsg(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle">Đánh giá tỉ giá</legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0" >
                 
                 <TR>
                        <TD class="plainlabel" valign="middle" >Năm</TD>
                        <TD class="plainlabel" valign="middle" colspan = 3>
                        	<INPUT TYPE = "TEXT" NAME = "nam" VALUE = "<%= dgtigiaBean.getNam()%>" readonly>                            	
                 </TR>

                 <TR>
                        <TD class="plainlabel" valign="middle" >Tháng</TD>
                        <TD class="plainlabel" valign="middle" colspan = 3>
                        	<INPUT TYPE = "TEXT" NAME = "thang" VALUE = "<%= dgtigiaBean.getThang() %>" readonly>
                         
                 </TR>

                 <TR>
                 	<TD class="plainlabel" valign="middle" width = "15%">
                 		Có ghi đảo vào đầu kỳ tới
                 	</TD>
                 	
                 	<TD class="plainlabel" valign="middle" >
                 		<% if(ghidao.equals("1")){ %>
                 			<INPUT type="checkbox" name="ghidao" value="1" CHECKED disabled>
                 		<%}else{ %>
                 			<INPUT type="checkbox" name="ghidao" value="1" disabled>
                 		<%} %>
                 	</TD>
                 </TR> 						

            </TABLE>
            </div>          
     </fieldset>
    </div>

 <div align="left" style="width:100%; float:none; clear:left">
	 <fieldset>
		<legend class="legendtitle">Kết quả đánh giá &nbsp;&nbsp;&nbsp;&nbsp;
		</legend>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
				<TR>
					<TD align="left" colspan="4">
						<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">

							<TR class="tbheader">
								<TH width="5%" align="center">STT</th>
								<TH width="12%" align="center">Loại chứng từ</th>
								<TH width="18%" align="center">Đối tượng</th>
								<TH width="10%" align="center">Số chứng từ</th>
								<TH width="10%" align="center">Ngày chứng từ</TH>
								<TH width="5%" align="center">Tiền</TH>
								<TH width="10%" align="center">Nguyên tệ</TH>
								<TH width="10%" align="center">Thành tiền cũ</TH>
								<TH width="10%" align="center">Thành tiền mới</TH>
								<TH width="15%" align="center">Chênh lệch</TH>
							</TR>
							
			<%try{ 
				if(kqlist != null){	
					int m = 0;
					String lightrow = "tblightrow";
					String darkrow = "tbdarkrow";
					
					while (kqlist.next()){
						if (m % 2 != 0) {%>
							<TR class=<%=lightrow%>>
					<%  } else {%>												
							<TR class=<%=darkrow%>>
				    <%  }%>
							
								<TD align = "center"><%= m + 1 %></TD>
								<TD align = "left"><%= kqlist.getString("LOAICT") %></TD>
								<TD align = "left"><%= kqlist.getString("DOITUONG") %></TD>
								<TD align = "center"><%= kqlist.getString("SOCHUNGTU") %></TD>
								<TD align = "center"><%= kqlist.getString("NGAYCHUNGTU") %></TD>
								<TD align = "center"><%= kqlist.getString("LOAITIEN") %></TD>
								<TD align = "right"><%= formatter.format(kqlist.getDouble("NGUYENTE")) %></TD>
								<TD align = "right"><%= formatter.format(Double.parseDouble(kqlist.getString("THANHTIENCU"))) %></TD>
								<TD align = "right"><%= formatter.format(Double.parseDouble(kqlist.getString("THANHTIENMOI"))) %></TD>
								<TD align = "right"><%= formatter.format(Double.parseDouble(kqlist.getString("CHENHLECH"))) %></TD>
							</TR>
			<%
						m++;
					}
				}
			}catch(java.sql.SQLException e) {}%>
					</TABLE>
				</TD>
			</TR>
		</TABLE>
   </fieldset>
</div>

</form>

</BODY>

</html>
<% 	if(kqlist != null) kqlist.close(); %>
	
<%	dgtigiaBean.DBClose();   
}%>
