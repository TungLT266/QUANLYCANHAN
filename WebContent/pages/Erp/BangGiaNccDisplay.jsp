<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.banggiancc.imp.*" %>
<%@ page import="geso.traphaco.erp.beans.banggiancc.*" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@page import="java.sql.SQLException" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<%
 	IBangGiaNcc obj =(IBangGiaNcc)session.getAttribute("csxBean");
	ResultSet dvkdList = obj.getDvkdRs();
	ResultSet kbhList = obj.getKbhRs();
	ResultSet nhomkenhRs = obj.getNhomkenhRs();
	ResultSet nccRs = obj.getNccRs();
	List<IBangGiaNcc_Sp> spList = (List<IBangGiaNcc_Sp>)obj.getSpList();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>

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

<script type="text/javascript" src="../scripts/ajax.js"></script>
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     
 </script>

<script>

$(function() {
 
 	$("ul.tabs").tabs("div.panes > div");
});
</script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		$(".select2").select2();
		
	});
</script>
<SCRIPT language="JavaScript" type="text/javascript">

	function submitform()
	{
	    document.forms["khtt"].submit();
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
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46)
			{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
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
	function DinhDangDonGia_sole(num, thapphan) 
	{
		num = num.toString().replace(/\$|\,/g,'');
		 
		var sole = '';
		if(num.indexOf(".") >= 0)
		{
			sole = num.substring(num.indexOf('.'));
		}
		
		if(isNaN(num))
		num = "0";
		sign = (num == (num = Math.abs(num)));
		num = Math.floor(num*100);
		num = Math.floor(num/100).toString();
		for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
		num = num.substring(0,num.length-(4*i+3)) + ',' + num.substring(num.length-(4*i+3));

		var kq;
		if(sole.length >= 0)
		{
			sole = sole.substring(0,thapphan+1);
			kq = (((sign)?'':'-') + num) + sole;
		}
		else
			kq = (((sign)?'':'-') + num);
		
		//alert(kq);
		return kq;
	}
	
	
 	function Dinhdang(element)
	{
		//element.value=DinhDangTien(element.value);
		element.value=DinhDangDonGia_sole(element.value,4);
		if(element.value== '' ||element.value=='0' )
		{
			element.value= '';
		}
	}
	
	function FormatNumber(e)
	{
		e.value = DinhDangTien(e.value);
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
	
	function save()
	{
	  document.forms["khtt"].action.value = "save";
	  document.forms["khtt"].submit(); 
    }
	
	function submitFrom()
	{
	  document.forms["khtt"].action.value = "submit";
	  document.forms["khtt"].submit(); 
    }
	
	function checkedAll()
	{
		 var chkAll = document.getElementById("chekAllSp");
		 var spIds = document.getElementsByName("chon");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = false;
			 }
		 }
	}
	function checkedAll2()
	{
		 var chkAll = document.getElementById("chekAllNcc");
		 var nccId = document.getElementsByName("nccId");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < nccId.length; i++)
			 {
				 nccId.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < nccId.length; i++)
			 {
				 nccId.item(i).checked = false;
			 }
		 }
	}
	
	function backButton()
	{
	  document.forms["khtt"].action.value = "back";
	  document.forms["khtt"].submit(); 
    }
	
</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="khtt" method="post" action="../../BangGiaNccUpdateSvl" >
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="action" value="0">
<input type="hidden" name="id" value="<%=obj.getId()   %>">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Mua hàng > Bảng giá mua NCC > Hiển thị</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="javascript: backButton();" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Thông báo </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thông tin bảng giá </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
                          <TR>
                          		<TD class="plainlabel" valign="middle" width="120px" >Tên bảng giá </TD>   
		                        <TD class="plainlabel" valign="middle"  ><input type="text" name="ten" value="<%= obj.getTen() %>"  > </TD>          
		                 		<TD class="plainlabel" valign="middle" width="120px" >Đơn vị kinh doanh </TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<select name="dvkdId" id="dvkdId" onchange="submitFrom();" >
										<option value=""></option>
										<% if (dvkdList != null)
										{
											while (dvkdList.next())
											{
												if (dvkdList.getString("dvkdId").equals(obj.getDvkdId()))
												{
												%>
													<option value="<%=dvkdList.getString("dvkdId")%>" selected="selected"><%=dvkdList.getString("dvkd")%></option>
												<% } else { %>
													<option value="<%=dvkdList.getString("dvkdId")%>"><%=dvkdList.getString("dvkd")%></option>
										<% } } dvkdList.close(); } %>
									</select>
		                        </TD>          
		                  </TR> 
		                  
		                   <TR>
                          		<TD class="plainlabel" valign="middle" width="120px" >Từ ngày </TD>   
		                        <TD class="plainlabel" valign="middle"  ><input type="text" class="days"  onchange="submitFrom()"  name="tungay" value="<%= obj.getTuNgay() %>"  >  </TD>          
		                 		<TD class="plainlabel" valign="middle" >Trạng thái </TD>   
		                        <TD class="plainlabel" valign="middle" >
		                            <% if(obj.getTrangthai().equals("1")) { %>
		                            	<input type="checkbox" name = "trangthai" value="1" checked="checked" ><i> Hoạt động</i>
		                            <% } else { %>
		                            	<input type="checkbox" name = "trangthai" value="1" ><i> Hoạt động</i>
		                            <% } %>
		                        </TD>     
		                  </TR>
		                  
		                  <TR>
		                  		<td colspan="4">
		                  		
		                  			<ul class="tabs">			                  			
			                  			<li><a href="#">Sản phẩm</a></li>
			                  			<li><a href="#">Nhà cung cấp</a></li>			                  			
			                  		</ul>
		                  			
		                  			<div class="panes">
										<div>
			                  			
				                  			<TABLE width="100%" border="0" cellspacing="1" cellpadding="0">
												<TR class="tbheader">
													<TH width="20%">Mã sản phẩm </TH>
													<TH width="50%">Tên sản phẩm</TH>								
													<TH width="10%">Giá mua </TH>
													<TH width="10%">Đơn vị</TH>
													<TH width="10%">Chọn 
														<input type="checkbox" id="chekAllSp" onclick="checkedAll();">
													</TH>
												</TR>
												
												<% for(int i = 0; i < spList.size(); i++ ) { 
													IBangGiaNcc_Sp sanpham = spList.get(i);	%>
													
													<TR>
														<TD>
															<input type="hidden" style="width: 100%" name="spId" value="<%= sanpham.getIdsp() %>" >
															<input type="text" style="width: 100%" name="spMa" value="<%= sanpham.getMasp() %>" readonly="readonly" >
														</TD>
														<TD>
															<input type="text" style="width: 100%" name="spTen" value="<%= sanpham.getTensp() %>" readonly="readonly" >
														</TD>
														<TD>
															<input type="text" style="width: 100%; text-align: right;"   onkeyup="Dinhdang(this)"  name="giaban" value="<%= sanpham.getGiaban() %>" > 
															<input type="hidden" style="width: 100%; text-align: right;" name="giamoi" value=""  >
														</TD>
														<TD>
															<input type="text" style="width: 100%; text-align: center;" name="donvi" value="<%= sanpham.getDonvi() %>" readonly="readonly" >
														</TD>
														<TD align="center">
															<% if(sanpham.getChonban().equals("1")) { %>
																<input type="checkbox" name="chonban" value="<%= sanpham.getIdsp() %>"  checked="checked" >
															<% } else { %>
																<input type="checkbox" name="chonban" value="<%= sanpham.getIdsp() %>"  >
															<% } %>
														</TD>
													</TR>
													
												<% } %>
												
											</TABLE>
										</div>
										
									</div>
									<div class="panes">
			                  			<div>
			                  			
				                  			<TABLE width="100%" border="0" cellspacing="1" cellpadding="0">
												<TR class="tbheader">
													<TH width="10%">Mã nhà cung cấp </TH>
													<TH width="80%">Tên nhà cung cấp</TH>		
													<TH width="10%">Chọn
														<input type="checkbox" id="chekAllNcc" onclick="checkedAll2();">
													</TH>
												</TR>
												<%
							                    	if(nccRs != null)
							                    	{
							                    		while(nccRs.next())
							                    		{
							                    			%>
				                    			
				                    			<tr>
				                    				<td><input type="text" value="<%= nccRs.getString("ma") %>" style="width: 100%;" readonly="readonly" ></td>
				                    				<td><input type="text" value="<%= nccRs.getString("ten") %>" style="width: 100%;" readonly="readonly" ></td>
				                    				<TD align="center"  >
						                        		<% if(obj.getNccId().contains(nccRs.getString("pk_seq"))) { %>
				                    						<input type="checkbox" name="nccId" value="<%= nccRs.getString("pk_seq") %>" checked="checked" >
				                    					<% } else { %>
				                    						<input type="checkbox" name="nccId" value="<%= nccRs.getString("pk_seq") %>"  >
				                    					<% } %>
						                       	 	</TD>          
												</tr>
												<%}} %>
											</TABLE>
										</div>
										
									</div>
		                  		</td>
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
	/* if(nppRs != null)
		nppRs.close(); */
	spList.clear();
	
	obj.DbClose();
%>

<%}%>
