<%@page import="geso.traphaco.erp.beans.kho.IErp_KhoTT"%>
<%@page import="geso.traphaco.erp.beans.kho.IErp_KhoTTList"%>
<%@page import="geso.traphaco.erp.beans.kho.imp.Erp_KhoTT"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.erp.beans.xuatdungccdc.Erp_Item"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

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

<%	IErp_KhoTT ktt =(IErp_KhoTT)session.getAttribute("ktt"); %>
<%	ResultSet rsNhamay = ktt.getNhamayRs(); %>
<%	ResultSet rsKhott = ktt.getKhoTTRs(); %>
<%	ResultSet rsLoaisp = ktt.getLoaiSpRs(); %>
<%	ResultSet rsSanpham = ktt.getSanphamRs(); %>
<%	String msg = (String)session.getAttribute("msgKhoTT"); 
	List<Erp_Item> loaiKhoList = ktt.getLoaiKhoList();
%>

<script type="text/javascript">
	function submitform()
	{
		document.forms['erpKhoForm'].action.value = 'submit';
		document.forms['erpKhoForm'].submit();
	}
	
	function saveform()
	{
	  	document.forms["erpKhoForm"].action.value = "save";
	  	document.forms["erpKhoForm"].submit(); 
	}
	
	
	function sellectAll()
	 {
		 var chkAll = document.getElementById("chkAll");
		 var spIds = document.getElementsByName("spIds");
		 
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
	 
	 function sellectAll2()
	 {
		 var chkAll = document.getElementById("chkAll2");
		 var spIds = document.getElementsByName("lspIds");
		 
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
	
</script>
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="erpKhoForm" method="post" action="../../ErpKhoTTUpdateSvl">
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
<input type="hidden" value="0" name="action">

<table width="100%" border="0" cellspacing="0" cellpadding="0"> 
	<tr>
		<td colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<table width="100%" cellpadding="0" cellspacing="1">
				<tr>
					<td align="left" class="tbnavigation">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr height="22">
						<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kho vận &gt; Khai báo kho &gt; Tạo mới </TD>
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
						<td width="30" align="left"> <a href="../../ErpKhoTTSvl?action=reView" > <img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"> </a></td>
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
			    			<textarea name="dataerror" style=" background-color:white; width: 100%;color: red" readonly="readonly" rows="1"  ><%=ktt.getMsg()%></textarea>
						</fieldset>
						</td>
					</tr>	
					
					
					<tr>
						<TD align="left" colspan="4" class="legendtitle">
						<fieldset>
						<LEGEND class="legendtitle">Thông tin kho </LEGEND>
						<TABLE  width="100%" cellspacing="0" cellpadding="6">
						<tr>	
							<td class = "plainlabel" width="150px" >Mã kho<font class="erroralert">*</font></td>
							<td class="plainlabel"><input name="txtMakho" value="<%=ktt.getMa() %>" type="text"/></td>
						</tr>
						<tr>
							<td class="plainlabel">Tên kho<font class="erroralert">*</font></td>
							<td class="plainlabel"><input name="txtTenkho" value="<%=ktt.getTen() %>" type="text"/></td>
						</tr>
						<tr>
							<td class="plainlabel">Địa chỉ</td>
							<td class="plainlabel"><input name="txtDiachi" value="<%=ktt.getDiachi() %>" type="text"/></td>
						</tr>

						<tr>
							<td class="plainlabel">Mô hình quản lý</td>
							<td class="plainlabel">
						  	<%if(ktt.getQuanlybin().equals("1")){%>
								<input name="mohinh"  type="checkbox" value="1" checked="checked" />Phân khu vực
						  <%}else{ %>
								<input name="mohinh" type="checkbox" value="1" />Phân khu vực
						  <%} %>
							</td>
						</tr>
						
						<tr>
							<td class="plainlabel">Loại</td>
							<td class="plainlabel">
								<select name = "loai" onchange="submitform();" >
									<option value = '0'>&nbsp;</option>
									<%  
// 									String[] mang=new String[]{ "1","2","3","4"};
// 									String[] mangten=new String[]{"Kho sản xuất","Kho nguyên liệu","Kho vật tư"
// 											,"Kho thành phẩm"};
									 for(Erp_Item item : loaiKhoList){
										 if(ktt.getLoai().equals(item.getValue())){
										 %>
										 <option selected="selected" value="<%=item.getValue()%>"> <%=item.getName() %> </option>
										 <%
										 }else{
										 %>
										 <option value="<%=item.getValue()%>"> <%=item.getName() %> </option>
										 <% 
										 }
									 }
									%>
							 						
								</select>
							</td>
						</tr>
						
						<% if(ktt.getLoai().equals("10")|| ktt.getLoai().equals("7")){ %>
						
							<tr>
								<td class="plainlabel">Nhà máy</td>
								<td class="plainlabel">
									<select name="nhamayId" >
		                            	<%
			                        		if(rsNhamay != null)
			                        		{
			                        			while(rsNhamay.next())
			                        			{  
			                        			if( rsNhamay.getString("pk_seq").equals(ktt.getNhamayId())){ %>
			                        				<option value="<%= rsNhamay.getString("pk_seq") %>" selected="selected" ><%= rsNhamay.getString("nhamayTen") %></option>
			                        			<%}else { %>
			                        				<option value="<%= rsNhamay.getString("pk_seq") %>" ><%= rsNhamay.getString("nhamayTen") %></option>
			                        		 	<% } } rsNhamay.close(); 
			                        		}
			                        	%>
		                            </select>
								</td>
							</tr>
							<tr>
								 <td class="plainlabel">Nhận nguyên liệu từ kho</td>
								<td class="plainlabel">
								  
		                            
		                            <a href="" id="hienthikho" rel="subcontentkho">
	           	 							&nbsp; <img alt="Thông tin loại sản phẩm" src="../images/vitriluu.png"></a>
	           	 							
								<DIV id="subcontentkho" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
				                             background-color: white; width: 590px; max-height:300px; overflow-y:scroll; padding: 4px;">
				                    <table width="90%" align="center">
				                        <tr>
				                            
				                            <th width="350px">Tên </th>
				                            <th width="100px" align="center">Chọn hết <input type="checkbox" onchange="sellectAll2()" id="chkAll2" ></th>
				                        </tr>
		                            	<%
			                        		if(rsKhott != null)
			                        		{
			                        			while(rsKhott.next())
			                        			{  %>
			                        			
			                        			<tr>
			                        				 
			                        				<td><input style="width: 100%" value="<%= rsKhott.getString("khoTen") %>" readonly="readonly"  ></td>
			                        				<td align="center">
			                        				<% if(ktt.getKhoTTIds().indexOf(rsKhott.getString("pk_seq")) >= 0 ){ %>
			                        					<input type="checkbox" name="khottId" checked="checked" value="<%= rsKhott.getString("pk_seq") %>">
			                        				<%}else{ %>
			                        					<input type="checkbox" name="khottId" value="<%= rsKhott.getString("pk_seq") %>">
			                        				<%} %>
			                        				</td>
			                        			</tr>
			                        			
			                        		 <% } rsKhott.close(); } %>
				                    </table>
				                     <div align="right">
				                     	<label style="color: red" ></label>
				                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                     	<a href="javascript:dropdowncontent.hidediv('subcontentCl')" onclick="submitform();" >Hoàn tất</a>
				                     </div>
				            	</DIV>   
				            	
								</td>
							</tr>
							
						<% } %>
						
						<tr>
							<td class="plainlabel">Loại sản phẩm</td>
							<td class="plainlabel">
								<a href="" id="lspId" rel="subcontentCl">
	           	 							&nbsp; <img alt="Thông tin loại sản phẩm" src="../images/vitriluu.png"></a>
	           	 							
								<DIV id="subcontentCl" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
				                             background-color: white; width: 590px; max-height:300px; overflow-y:scroll; padding: 4px;">
				                    <table width="90%" align="center">
				                        <tr>
				                            <th width="100px">Mã loại</th>
				                            <th width="350px">Tên </th>
				                            <th width="100px" align="center">Chọn hết <input type="checkbox" onchange="sellectAll2()" id="chkAll2" ></th>
				                        </tr>
		                            	<%
			                        		if(rsLoaisp != null)
			                        		{
			                        			while(rsLoaisp.next())
			                        			{  %>
			                        			
			                        			<tr>
			                        				<td><input style="width: 100%" value="<%= rsLoaisp.getString("pk_seq") %>" readonly="readonly"  ></td>
			                        				<td><input style="width: 100%" value="<%= rsLoaisp.getString("lspTen") %>" readonly="readonly"  ></td>
			                        				<td align="center">
			                        				<% if(ktt.getLoaispIds().indexOf(rsLoaisp.getString("pk_seq")) >= 0 ){ %>
			                        					<input type="checkbox" name="lspIds" checked="checked" value="<%= rsLoaisp.getString("pk_seq") %>">
			                        				<%}else{ %>
			                        					<input type="checkbox" name="lspIds" value="<%= rsLoaisp.getString("pk_seq") %>">
			                        				<%} %>
			                        				</td>
			                        			</tr>
			                        			
			                        		 <% } rsLoaisp.close(); } %>
				                    </table>
				                     <div align="right">
				                     	<label style="color: red" ></label>
				                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                     	<a href="javascript:dropdowncontent.hidediv('subcontentCl')" onclick="submitform();" >Hoàn tất</a>
				                     </div>
				            	</DIV>   
							</td>
						</tr>
						
						<% if(ktt.getLoaispIds().trim().length() > 0){ %>
							
						<tr>
							<td class="plainlabel">Sản phẩm</td>
							<td class="plainlabel">
								<a href="" id="spId" rel="subcontentSp">
	           	 							&nbsp; <img alt="Thông tin loại sản phẩm" src="../images/vitriluu.png"></a>
	           	 							
								<DIV id="subcontentSp" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
				                             background-color: white; width: 590px; max-height:300px; overflow-y:scroll; padding: 4px;">
				                    <table width="90%" align="center">
				                        <tr>
				                            <th width="100px">Mã sản phẩm</th>
				                            <th width="350px">Tên sản phẩm</th>
				                            <th width="100px" align="center">Chọn hết <input type="checkbox" onchange="sellectAll()" id="chkAll" ></th>
				                        </tr>
		                            	<%
			                        		if(rsSanpham != null)
			                        		{
			                        			rsSanpham.beforeFirst();
			                        			while(rsSanpham.next())
			                        			{  %>
			                        			
			                        			<tr>
			                        				<td><input style="width: 100%" value="<%= rsSanpham.getString("ma") %>" readonly="readonly" ></td>
			                        				<td><input style="width: 100%" value="<%= rsSanpham.getString("ten") %>" readonly="readonly"  ></td>
			                        				<td align="center">
			                        				<% if(ktt.getSpIds().indexOf(rsSanpham.getString("pk_seq")) >= 0 ){ %>
			                        					<input type="checkbox" name="spIds" checked="checked" value="<%= rsSanpham.getString("pk_seq") %>">
			                        				<%}else{ %>
			                        					<input type="checkbox" name="spIds" value="<%= rsSanpham.getString("pk_seq") %>">
			                        				<%} %>
			                        				</td>
			                        			</tr>
			                        			
			                        		 <% } rsSanpham.close(); } %>
				                    </table>
				                     <div align="right">
				                     	<label style="color: red" ></label>
				                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                     	<a href="javascript:dropdowncontent.hidediv('subcontentSp')">Hoàn tất</a>
				                     </div>
				            	</DIV>
							</td>
						</tr>
						
						<% } %>
						
						<tr>
							<td class="plainlabel">Trạng thái</td>
							<td class="plainlabel">
								<input name="hoatdong" checked="checked" type="checkbox" value="1" />Hoạt động
							</td>
						</tr>
					</table>
				</fieldset>
				</td>
			</tr>
</table>				
</td></tr>
</table></form>

<script type="text/javascript">
	dropdowncontent.init('spId', "right-top", 300, "click");
	dropdowncontent.init('lspId', "right-top", 300, "click");
	dropdowncontent.init('hienthikho', "right-top", 300, "click");
</script>

</body>
</html>

<%
 	if (rsKhott != null) 
 		rsKhott.close();
	if (rsNhamay != null) 
		rsNhamay.close();
	if (rsLoaisp != null) 
		rsLoaisp.close();
	if (rsSanpham != null) 
		rsSanpham.close();
 	ktt.DBClose();
 	session.setAttribute("ktt", null) ; session.setAttribute("msgKhoTT", null) ; 
 	
	}
%>