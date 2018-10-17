<%@page import="geso.traphaco.distributor.beans.chinhanhthuho.imp.Chinhanhthuho"%>
<%@page import="geso.traphaco.distributor.beans.chinhanhthuho.IChinhanhthuho"%>
<%@page import="geso.traphaco.distributor.beans.chinhanhthuho.IChinhanhthuhoList"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>
<%@ page  import = "geso.traphaco.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% IChinhanhthuho thBean =(Chinhanhthuho)session.getAttribute("thBean");

   
NumberFormat formatterNT = new DecimalFormat("#,###,###.##"); 
NumberFormat formatterVND = new DecimalFormat("#,###,###"); 
NumberFormat formatter = new DecimalFormat("#,###,###.##"); 
%>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/jquery-1.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
    <script type="text/javascript">
        $(document).ready(function(){
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
</script>
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
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

<SCRIPT language="javascript" type="text/javascript">
	function confirmLogout()
	{
	   if(confirm("Ban co muon dang xuat?"))
	   {
			top.location.href = "home.jsp";
	   }
	   return
	}
// 	function submitform()
// 	{
// 		document.forms['nvgnForm'].action.value='submitForm';
// 	   	document.forms['nvgnForm'].submit();
// 	}

	
// 	function saveform()
// 	{	  				 				 
// 		document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
		 
// 		 document.forms['nvgnForm'].action.value='save';
// 	     document.forms['nvgnForm'].submit();
// 	}
	

	
	function CheckAll()
	{
		var selectAll = document.getElementById("selectAll");
		var chon = document.getElementsByName("hdids");
		if(selectAll.checked)
		{
			for(i = 0; i < chon.length; i++)
				chon.item(i).checked = true;
		}
		else
		{
			for(i = 0; i < chon.length; i++)
				chon.item(i).checked = false;
		}
	}
	
	function UnCheckedAll()
	{
		var selectAll = document.getElementById("selectAll");
		selectAll.checked = false;
	}
	
	
	function DinhDang()
	{
		var sotien = document.getElementById("sotientu").value.replace(/,/g,"");
		document.getElementById("sotientu").value= DinhDangTien(sotien);
	}
	
	function DinhDang1()
	{
		var sotien = document.getElementById("sotienden").value.replace(/,/g,"");
		document.getElementById("sotienden").value= DinhDangTien(sotien);
	}
	function goBack()
	 {
	  	window.history.back();
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

	function keypress(e) //Hàm dùng d? ngan ngu?i dùng nh?p các ký t? khác ký t? s? vào TextBox
	{    
		
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode; //IE
		else
			keypressed = e.which; //NON-IE, Standard
		
		if (keypressed < 48 || keypressed > 57)
		{ 
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46 || keypressed == 45)
		{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}
</SCRIPT>
<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
	     $(document).ready(function() { $("select").select2(); });
	     
	</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="obj" method="post" action="../../ChinhanhthuhoUpdateSvl" >
<input type="hidden" name="action" value='1'>
<input name="userId" type="hidden" value='<%=userId %>' size="30">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"	>
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">

					   	<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Cơ bản &gt; Chi nhánh thu hộ &gt; Hiển thị
							   </TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %> &nbsp;</td> 
					      </tr>
   
						</TABLE>
					</TD>
				</TR>
			</TABLE>	
			<TABLE width="100%" border="0" cellpadding="3" cellspacing="0">
				<TR ><TD >
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR class = "tbdarkrow">
									<TD width="30" align="left"> 
										<A href="javascript:goBack();" > <img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"> </A>
									</TD>
									<TD>&nbsp; </TD>						
								</TR>
						</TABLE>
					</TD>
				</TR>
									
				<TR>
						   	<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle">Thông tin chi nhánh thu hộ
								</LEGEND>
								<TABLE width="100%" cellspacing="0" cellpadding="6">
									<TR>		
									  <TD width="15%" class="plainlabel">Mã</TD>
									  <TD  class="plainlabel" ><INPUT type = "text" readonly name="Ma" size="40"
										type="text" value="<%=thBean.getMA() %>" >  
										</TD>
										
										
								    </TR>
	
									<tr>
									<td class="plainlabel">Tên</td> <td class = "plainlabel"> <input type = "text" readonly name = "Ten" value="<%=thBean.getTEN() %>"> </td>
									</tr>
									
									<tr>
									<td class="plainlabel">Số tiền</td> <td class = "plainlabel"> <input type = "text" readonly name = "sotien" value="<%= formatterVND.format(Double.parseDouble(thBean.getSotien())) %>"> </td>
									</tr>
									
										<tr>
									<td class="plainlabel">Chi nhánh</td> <td class = "plainlabel"> <input type = "text" readonly name = "chinhanh" value="<%=thBean.getChinhanh() %>"> </td>
									</tr>
									
									
								
									
									
								</TABLE>
								</FIELDSET>			
							</TD>
								
				</TR>
		</TABLE>	
		</TD>
		</TR>
		</TABLE>
</form>
</body>
</html>
<% 	


	
%>
<%}%>