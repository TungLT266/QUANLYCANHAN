<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.banggiablc.IBanggiablc" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoDMS/index.jsp");
	}else{ %>

<% IBanggiablc bgblcBean = (IBanggiablc)session.getAttribute("bgblcBean"); %>
<% ResultSet dvkd = (ResultSet)bgblcBean.getDvkdIds(); %>
<% ResultSet sanpham = (ResultSet)bgblcBean.getSanPhamList();
	NumberFormat formatter = new DecimalFormat("#,###,####");
	NumberFormat formatter2 = new DecimalFormat("#,###,###.####");
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


<SCRIPT language="javascript" type="text/javascript">
function submitform()
{   
   document.forms["bgblcForm"].submit();
}

 function saveform()
{
	document.forms['bgblcForm'].action.value='save';
    document.forms["bgblcForm"].submit();
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
</SCRIPT>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="bgblcForm" method="post" action="../../BanggiabanlechuanUpdateSvl">
<input type="hidden" name="userId" value='<%=bgblcBean.getUserId() %>'>
<input type="hidden" name="id" value="<%=bgblcBean.getId() %>">
<input type="hidden" name="action" value='1'>
 
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
		
			<TABLE width="100%" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						   <tr height="22">
 							   <TD align="left" colspan="2" class="tbnavigation">
 							   		&nbsp;Dữ liệu nền &gt;Sản phẩm &gt; Bảng giá bán&gt; Cập nhật </TD>
 							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;</td>
					     </tr>
						</table>
					 </TD>
				  </TR>	
		  	</TABLE>
			<TABLE width="100%" cellpadding="0" cellspacing="1">
			<TR ><TD >
				<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
					<TR class = "tbdarkrow">
						<TD width="30" align="left"><A href="../../BanggiabanlechuanSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
					    <TD width="2" align="left" ></TD>
					    <TD width="30" align="left"  ><A href="javascript: saveform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
				    	<TD align="left" >&nbsp;</TD>
					</TR>
				</TABLE>
			</TD></TR>
			</TABLE>

		<TABLE width="100%"  border = "0" cellspacing="0" cellpadding="0">
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Bão lỗi nhập liệu </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; color:white; font-weight:bold" rows="1" style="width: 100%" readonly="readonly" ><%=bgblcBean.getMessage()%></textarea>
						<% bgblcBean.setMessage(""); %>
						</FIELDSET>
				   </TD>
				</tr>			
		 	
			<TR>
		 	
			<TR>
				<TD >
        			
				<FIELDSET>
				<LEGEND class="legendtitle">&nbsp;Bảng giá bán &nbsp;</LEGEND>
				<TABLE width="100%" cellpadding="0" cellspacing="1">
					<TR><TD>					
						<TABLE  width="100%" cellpadding="4" cellspacing="0">
							<TR>
								<TD  width="15%" class="plainlabel">Tên bảng giá <FONT class="erroralert">*</FONT></TD>
								<TD class="plainlabel">
								  <input name="bgTen" type="text" value="<%= bgblcBean.getTen() %>" style="width:300px">
							  	</TD>
							</TR>
								
							<TR>
								<TD class="plainlabel">Đơn vị kinh doanh <FONT class="erroralert">*</FONT></TD>
								    <TD class="plainlabel"><SELECT  name="dvkdId"  onChange = "submitform();" style="width:300px">
								  		<option value =""></option>
								  		
								  	 <% try{ while(dvkd.next()){ 
								    	if(dvkd.getString("dvkdId").equals(bgblcBean.getDvkdId())){ %>
								      		<option value='<%=dvkd.getString("dvkdId") %>' selected><%=dvkd.getString("dvkd") %></option>
								      	<%}else{ %>
								     		<option value='<%=dvkd.getString("dvkdId") %>'><%=dvkd.getString("dvkd") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	
                                  </select></TD>
							</TR>
							
								<TR>
								<TD  width="15%" class="plainlabel">Từ ngày <FONT class="erroralert">*</FONT></TD>
								<TD class="plainlabel">
								  <input name="tungay" type="text"  class="days"  value="<%=bgblcBean.getTungay() %>" style="width:300px">
							  	</TD>
							</TR>
							
							<TR>							
							    <TD  class="plainlabel" colspan=2 align=left>  									
							    <% if (bgblcBean.getTrangthai().equals("1")){ %>
									<input name="trangthai" type="checkbox" value = "1" checked >
									<%}else{ %>
									<input name="trangthai" type="checkbox" value = "0"  >
								<%} %>
								Hoạt động </TD>
							</TR>	
							
						</TABLE>
					</TD></TR>
				</TABLE>
				<TABLE class="tabledetail" cellpadding="0" cellspacing="0" width="100%">
						<TR id="spdvkd" bgcolor="#FFFFFF">
							<TD width="100%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="0">
								<TR class="tbheader">
									<TH width="25%">Mã sản phẩm </TH>
									<TH width="45%">Tên sản phẩm</TH>
									<TH width="10%">Đơn vị tính</TH>
									<TH width="10%">Giá bán lẻ chuẩn</TH>
									<TH width="10%">Tiền tệ</TH>
								</TR>
								
								<%
								int j = 0;
								String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								if (sanpham != null)
								{
									try{ while(sanpham.next())
									{ 
										if (j % 2 != 0) 
										{%>						
											<TR class= <%=lightrow%> >
									    <%} else 
									    {%>
											<TR class= <%= darkrow%> >
										<%}%>
												<TD align="center"><div align="left"><%= sanpham.getString("ma") %> </div></TD>
												<TD align="center"><div align="left"><%= sanpham.getString("ten") %></div></TD>
												<TD align="center"><div align="center"><%= sanpham.getString("donvi") %></div></TD>
												<TD align="center">
													<input type='text' name='dongia'  value="<%= formatter2.format(sanpham.getDouble("giabanlechuan")) %>" style="text-align: right"/>
													<input type='hidden' name='masanpham' value="<%= sanpham.getString("id") %>" />
													<input type='hidden' name='quycach' value="0" style="text-align: right"/>
												</TD>
												<TD  align="center">
													VNĐ
												</TD>
							     		<% j++;
										}}catch(Exception e){ e.printStackTrace(); } 
								}%>
							</TABLE>

							</TD>
						</TR>
					</TABLE>
					</FIELDSET>
				</td>
			</TR>
		</TABLE>
	
	</TD>
	</TR>
</Table>
</form>
</BODY>
</html>

<% if(dvkd != null) dvkd.close(); %>
<% if (sanpham != null) sanpham.close(); 
	bgblcBean.DbClose();
%>
<%}%>