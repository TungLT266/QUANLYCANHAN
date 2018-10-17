<%@page import="geso.traphaco.erp.beans.khoanmucchietkhau.*"%>
<%@page import="java.sql.ResultSet"%>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@page import="java.sql.SQLException" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<html>
<head>
<title>Best - Project</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<link rel="stylesheet" href="../css/main.css" type="text/css">

<%	IKhoanmucchietkhau kmck =(IKhoanmucchietkhau)session.getAttribute("kmck"); 
	ResultSet rsTaiKhoan = kmck.getTaikhoanRs();
	ResultSet rsKenhBanHang = kmck.getKenhBanHangRs();
	List<IKhoanmucCK> chiTietList = (List<IKhoanmucCK>)kmck.getChiTietList();
%>


<script type="text/javascript">
	function submitform()
	{
		document.forms['erpKhoForm'].action.value = 'submit';
		document.forms['erpKhoForm'].submit();
	}
	
	function saveform()
	{	 
		 var error = document.getElementById("Msg");
		 var maKMCK = document.getElementById("maKMCK");
		 var tenKMCK = document.getElementById("tenKMCK");
		 console.log("maKMCK" + maKMCK.value);
		 if(maKMCK.value == ""){
			 error.value="Vui lòng nhập mã khoản mục chiết khấu ";
			 return;
		 }
		 if(tenKMCK.value == ""){
			 error.value ="Vui lòng nhập tên khoản mục chiết khấu ";
			 return;
		 }
		 // Kiểm tra kênh bán hàng
		 var kenhBanHang = document.getElementsByName("kenhBanHang");
		 var taiKhoan = document.getElementsByName("taiKhoan");
		 var countKenhBanHang= 0;
		 var countTaiKhoan = 0;
		 for(var i=0;i<kenhBanHang.length;i++ ){
			 if(kenhBanHang.item(i).value.trim().length>0){
				 countKenhBanHang++;
			 }
		 }
		 
		 for(var i=0;i<taiKhoan.length;i++ ){
			 if(taiKhoan.item(i).value.trim().length>0){
				 countTaiKhoan++;
			 }
		 }
		 if(countKenhBanHang != countTaiKhoan){
			error.value="Vui lòng nhập lại kênh bán hàng";
			return; 
		 }
		 //End kiểm tra kênh bán hàng 
		
	  	document.forms["erpKhoForm"].action.value = "save";
	  	document.forms["erpKhoForm"].submit(); 
	}
	function goBack(){
		window.history.back();
	}
</script>
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="erpKhoForm" method="post" action="../../Erp_KhoanmucchietkhauUpdateSvl">
<INPUT name="userId" type="hidden" value='<%=userId %>' >

<input type="hidden" value="0" name="action">

<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	<tr>
		<td colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<table width="100%" cellpadding="0" cellspacing="1">
				<tr>
					<td align="left" class="tbnavigation">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr height="22">
						<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kế toán &gt; Khoản mục chiết khấu &gt; Tạo mới </TD>
						<td colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %> &nbsp;</td> 
					</tr>
					</table>
					</td>
				</tr>
			</table>
				
			<table width="100%" border="0" cellpadding="3" cellspacing="0">
				<tr>
					<td>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr class = "tbdarkrow">
						<td width="30" align="left"> <a href="javascript:goBack()" > <img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"> </a></td>
					    <td width="2" align="left" ></td>
						<td width="30" align="left" ><a href="javascript: saveform()" ><img src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></a></td>
						<td>&nbsp;</td>						
					</tr>
					</table>
					</td>
				</tr>
			</table>								  			
			<table  width="100%" cellspacing="0" cellpadding="4">
					<tr>
						<td align="left" colspan="4" class="legendtitle">
						<fieldset>
							<legend class="legendtitle">Thông báo </legend>
			    			<textarea name="dataerror" id="Msg" style="width: 100%;color: white" readonly="readonly" rows="1"  ><%=kmck.getMsg()%></textarea>
						</fieldset>
						</td>
					</tr>	
					
					
					<tr>
						<TD align="left" colspan="4" class="legendtitle">
						<fieldset>
						<LEGEND class="legendtitle">Thông tin khoản mục chiết khấu </LEGEND>
						<TABLE  width="100%" cellspacing="0" cellpadding="6">
						<tr>	
							<td class = "plainlabel" width="250px" >Mã khoản mục chiết khấu<font class="erroralert">*</font></td>
							<td class="plainlabel"><input name="maKMCK" id="maKMCK" value="<%=kmck.getMa()%>" type="text"/></td>
						</tr>
						<tr>
							<td class="plainlabel">Tên khoản mục chiết khấu<font class="erroralert">*</font></td>
							<td class="plainlabel"><input name="tenKMCK" id="tenKMCK" value="<%=kmck.getTen()%>" type="text"/></td>
						</tr>
						<tr>
							<td class="plainlabel">Kênh bán hàng</td>
							<td class="plainlabel">
								<a href="" id="kbhList" rel="relKBHList">
	           	 							&nbsp; <img alt="Thông tin khoản mục chiết khấu" src="../images/vitriluu.png"></a>
	           	 							
								<DIV id="relKBHList" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
				                             background-color: white; width: 430px; max-height:230px; overflow-y:scroll; padding: 4px;">
				                    <table width="90%" align="center">
				                        <tr>
				                            <th width="200px">Kênh</th>
				                            <th width="200px">Số hiệu tài khoản </th>				                            
				                        </tr>
		                            	<%
		                            		int m=0;
			                        		for (int i = 0; i < chiTietList.size(); i++ )
			                        		{
			                        			IKhoanmucCK chiTietKMCK = chiTietList.get(i);	
			                        			 %>
			                        			
			                        			<tr>	        
			                        				<td>
			                        				<input name = "chiTietId" id = "chiTietId" type="hidden" value="<%= chiTietKMCK.getKenhbhId() %>" >
			                        				<select name="kenhBanHang" id="kenhBanHang">															
																<% try {
																	rsKenhBanHang.beforeFirst();
																if (rsKenhBanHang != null)
																	{
																		while (rsKenhBanHang.next()){
																			if(!(chiTietKMCK.getKenhbhId()==null)){
																				if(chiTietKMCK.getKenhbhId().equals(rsKenhBanHang.getString("pk_seq"))){ %>
																					<option value="<%= rsKenhBanHang.getString("pk_seq") %>" selected><%= rsKenhBanHang.getString("ten")%> </option>
																			<% 	}else{ %>
																					<option value="<%= rsKenhBanHang.getString("pk_seq") %>" ><%= rsKenhBanHang.getString("ten")%> </option>
																			<% 	}
																			}
																			else{ %>
																				<option value="<%= rsKenhBanHang.getString("pk_seq") %>" ><%=rsKenhBanHang.getString("ten")%> </option>
																		<%	}
																		} 
																	} 
																} catch (java.sql.SQLException e){}
																%>
															</select>
			                        				</td>
			                        				<td>
			                        					
			                        					<select name="taiKhoan" id="taiKhoan">															
																<% try {
																	rsTaiKhoan.beforeFirst();
																if (rsTaiKhoan != null)
																	{
																		while (rsTaiKhoan.next()){
																			if(!(chiTietKMCK.getTaikhoanId()==null)){
																				if(chiTietKMCK.getTaikhoanId().equals(rsTaiKhoan.getString("pk_seq"))){ %>
																					<option value="<%= rsTaiKhoan.getString("pk_seq") %>" selected><%= rsTaiKhoan.getString("sohieutaikhoan")%> </option>
																			<% 	}else{ %>
																					<option value="<%= rsTaiKhoan.getString("pk_seq") %>" ><%= rsTaiKhoan.getString("sohieutaikhoan")%> </option>
																			<% 	}
																			}
																			else{ %>
																				<option value="<%= rsTaiKhoan.getString("pk_seq") %>" ><%=rsTaiKhoan.getString("sohieutaikhoan")%> </option>
																		<%	}
																		} 
																	} 
																} catch (java.sql.SQLException e){}
																%>
															</select>
			                        				</td>			                        				
			                        			</tr>
			                        			
			                        		 <%  m++;} while ( m <5){
				                 					
					                        		%>
					                        		<tr>
					                        					<td>
					                        					<input name = "chiTietId" id = "chiTietId" type="hidden" value="" >
					                        					<select name="kenhBanHang" id="kenhBanHang">	
					                        							<option value=""></option>														
																		<%try {
																			rsKenhBanHang.beforeFirst();
																		if (rsKenhBanHang != null)
																			{
																				while (rsKenhBanHang.next()){%>
																							<option value="<%= rsKenhBanHang.getString("pk_seq") %>"><%= rsKenhBanHang.getString("ten")%> </option>
																				<% 
																			}}} catch (java.sql.SQLException e){}%>
			
																	</select>
					                        				</td>		
					                        				<td>
					                        					
					                        					<select name="taiKhoan" id="taiKhoan">	
					                        							<option value=""></option>																
																		<%try {
																			rsTaiKhoan.beforeFirst();
																		if (rsTaiKhoan != null)
																			{
																				while (rsTaiKhoan.next()){%>
																							<option value="<%= rsTaiKhoan.getString("pk_seq") %>"><%= rsTaiKhoan.getString("sohieutaikhoan")%>-<%= rsTaiKhoan.getString("TENTAIKHOAN") %> </option>
																				<% 
																			}}} catch (java.sql.SQLException e){}%>
			
																	</select>
					                        				</td>			                 
					                        		</tr>
					                        		<%m++;} %>
				                    </table>
				                     <div align="right">
				                     	<label style="color: red" ></label>
				                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                     	<a href="javascript:dropdowncontent.hidediv('subcontentCl')" >Hoàn tất</a>
				                     </div>
				            	</DIV>   
							</td>
						</tr>
					</table>
				</fieldset>
				</td>
			</tr>
</table>				
</td></tr>
</table></form>
<script type= "text/javascript">
	dropdowncontent.init('kbhList',"right-bottom",200,"click")
</script>

</body>
</html>

<%
	if(rsTaiKhoan != null) 
		rsTaiKhoan.close();
	if(rsKenhBanHang != null)
		rsKenhBanHang.close();
	if(chiTietList != null)
		chiTietList.clear();
	
 	kmck.DBClose();
	}
%>