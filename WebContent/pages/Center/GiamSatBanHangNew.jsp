<%@page import="CI.DS"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.giamsatbanhang.IGiamsatbanhang" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/Traphaco/index.jsp");
	}else{ %>


<% IGiamsatbanhang gsbhBean = (IGiamsatbanhang)session.getAttribute("gsbhBean"); %>
<% ResultSet ncc = (ResultSet)gsbhBean.getNhacungcapList(); %>
<% ResultSet dvkd = (ResultSet)gsbhBean.getDvkdList(); %>
<% ResultSet kbh = (ResultSet)gsbhBean.getKenhbanhangList(); %>
<% ResultSet vung = (ResultSet)gsbhBean.createVungRS(); %>
<% ResultSet khuvuc = (ResultSet)gsbhBean.createKhuvucRS() ; %>
<% ResultSet bmRs = (ResultSet)gsbhBean.getBmRs(); %>

<% ResultSet Dsnpp = (ResultSet) gsbhBean.getDsnpp(); %>
<% Hashtable<Integer, String> nppIds = (Hashtable<Integer, String>) gsbhBean.getnpp(); %>


<% Hashtable<Integer, String> kvIds = (Hashtable<Integer, String>)gsbhBean.getKvIds(); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">

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
    document.forms["gsbhForm"].submit();
 }

  function saveform()
 {
		 	 
 	 document.forms['gsbhForm'].action.value='save';
     document.forms["gsbhForm"].submit();
 }
  
  function ajaxOption(id) 
  {
		//var kenhId=	document.getElementById("kenhId").value;
		var str2 = '';
		var kvIds = document.getElementsByName("kvIds");
		for(var j = 0; j < kvIds.length ; j++)
		{
			if(kvIds[j].checked == true)
				str2 += kvIds[j].value + ',';
		}	
		
		var str3 = '';
		var nppIds = document.getElementsByName("npp");
		if(nppIds !== null)
		{
			for(var k = 0; k < nppIds.length ; k++)
			{
				if(nppIds[k].checked == true)
					str3 += nppIds[k].value + ',';
			}			
		}
		var xmlhttp;

		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				document.getElementById(id).innerHTML = xmlhttp.responseText;
			}
		}
		xmlhttp.open("GET", "../../ajaxnppGsbh?id=" + id + "&kvIds=" + str2 + "&nppIds=" + str3 +"&gsbhId=0" ,true);	
	xmlhttp.send();

	
}
  function sellectAll(cbId1,cbId2 )
  {
  	 var chkAll_Lct = document.getElementById(cbId1);
  	 var loaiCtIds = document.getElementsByName(cbId2);
  	 

  	 
  	 if(chkAll_Lct.checked)
  	 {
  		 for(var i = 0; i < loaiCtIds.length; i++)
  		 {
  			 loaiCtIds.item(i).checked = true;
  		 }
  	 }
  	 else
  	 {
  		 for(var i = 0; i < loaiCtIds.length; i++)
  		 {
  			 loaiCtIds.item(i).checked = false;
  		 }
  	 }
  }
  

   
</SCRIPT>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="gsbhForm" method="post" action="../../GiamsatbanhangUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'> 
<input type="hidden" name="action" value='1'>
 
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Thiết lập dữ liệu nền > Dữ liệu nền kinh doanh > Phụ Trách Tỉnh/ GĐCN Cấp 2 > Tạo mới 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
					<TR class = "tbdarkrow">
						<TD width="30" align="center"><A href="../../GiamsatbanhangSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" width="30" height="30" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
					    <TD width="2" align="left" ></TD>
					    <TD width="30" align="left" ><A href="javascript: saveform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
				    	<TD align="left" >&nbsp;</TD>
					</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="0">
			<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1" ><%=gsbhBean.getMessage()%></textarea>
						<% gsbhBean.setMessage(""); %>
						</FIELDSET>
				   </TD>
				</tr>
				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET>
						<LEGEND class="legendtitle" style="color:black">Thông tin Phụ Trách Tỉnh/ GĐCN Cấp 2</LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
							<TR>
								<TD width="150px" class="plainlabel" >Họ tên <FONT class="erroralert"> *</FONT></TD>
								<TD width="250px" class="plainlabel"><INPUT type="text" name="TenGSBH"  value="<%= gsbhBean.getTen() %>"></TD>
							
							  	<TD width="120px" class="plainlabel">Địa chỉ<FONT class="erroralert"> *</FONT></TD>
						  	  	<TD class="plainlabel"><INPUT type="text" name="DiaChi"  value="<%= gsbhBean.getDiachi() %>"></TD>

						  </TR>
							<TR>
							  	 <TD class="plainlabel">Điện thoại <FONT
									class="erroralert"> *</FONT></TD>
								 <TD class="plainlabel"><INPUT type="text" name="DienThoai"  value="<%= gsbhBean.getSodienthoai() %>"></TD>
							
							  <TD class="plainlabel">Email<FONT
									class="erroralert">*</FONT></TD>
							  <TD class="plainlabel"><INPUT type="text" name="Email"  value="<%=gsbhBean.getEmail()%>"></TD>
							  
							 
						  </TR>
							<TR>
							  <TD class="plainlabel">Nhà cung cấp <FONT class="erroralert">*</FONT></TD>
							  <TD class="plainlabel"><select name="NCC" onChange="submitform();">		
								<% try{ while(ncc.next()){ 
								   if(ncc.getString("nccId").equals(gsbhBean.getNccId())){ %>
								      	<option value='<%=ncc.getString("nccId") %>' selected><%= ncc.getString("nccTen") %></option>
								   <%}else{ %>
								     	<option value='<%=ncc.getString("nccId") %>'><%= ncc.getString("nccTen") %></option>
								   <%}}}catch(java.sql.SQLException e){} %>	
								   
							    </select>							  
							  </TD>
							  
						 
							  <TD class="plainlabel">Đơn vị kinh doanh <FONT class="erroralert">*</FONT></TD>
							  <TD class="plainlabel"><select name="dvkdId">
							  
							    <option value =""></option>  		
								<% try{ while(dvkd.next()){ 
								   if(dvkd.getString("dvkdId").equals(gsbhBean.getDvkdId())){ %>
								      	<option value='<%=dvkd.getString("dvkdId") %>' selected><%= dvkd.getString("dvkdTen") %></option>
								   <%}else{ %>
								     	<option value='<%=dvkd.getString("dvkdId") %>'><%= dvkd.getString("dvkdTen") %></option>
								   <%}}}catch(java.sql.SQLException e){} %>	
								   
							    </select>							  
							  </TD>
							  
							
						  </TR>

							<TR>
							   	 <TD class="plainlabel">Vùng Miền<FONT class="erroralert">*</FONT></TD>
								 <TD class="plainlabel"><SELECT name="vungId" onChange="submitform();">
								 
								    <option value =""></option>  		
								<% try{ while(vung.next()){ 
								   if(vung.getString("vungId").equals(gsbhBean.getVungId())){ %>
								      	<option value='<%=vung.getString("vungId") %>' selected><%=vung.getString("vung") %></option>
								   <%}else{ %>
								     	<option value='<%=vung.getString("vungId") %>'><%=vung.getString("vung") %></option>
								   <%}}}catch(java.sql.SQLException e){} %>	
								   
									</SELECT></TD>
									
								<TD  class="plainlabel" > Mã FAST </TD>
								<TD  class="plainlabel" > 
									<INPUT type="text" name="maFAST"  value="<%= gsbhBean.getMaFAST() %>" >
								</TD>
							</TR>
							
							<TR>
							   	 <TD class="plainlabel">Giám đốc miền </TD>
								 <TD colspan="3" class="plainlabel">
								 	<SELECT name="bmId" >
								    <option value =""></option>  		
									<% try
									{ while(bmRs.next())
									{ 
								   		if(bmRs.getString("pk_seq").equals(gsbhBean.getBmId()    )){ %>
								      	<option value='<%=bmRs.getString("pk_seq") %>' selected><%=bmRs.getString("ten") %></option>
								   	<%}else{ %>
								     	<option value='<%=bmRs.getString("pk_seq") %>'><%=bmRs.getString("ten") %></option>
								   <%}}}catch(java.sql.SQLException e){e.printStackTrace();} %>	
								   
									</SELECT></TD>
							</TR>
							
                          	<TR>		
								<TD class="plainlabel">Số CMND<FONT class="erroralert"></FONT></TD>
								<TD class="plainlabel"  ><input name="cmnd" maxlength="50"
									id="cmnd" type="text" value="<%=gsbhBean.getCmnd()%>"></TD>
							
								<TD class="plainlabel">Mật khẩu</TD>
								<TD class="plainlabel"  ><INPUT type="password" name="matkhau"
									value="<%=gsbhBean.getMatKhau()%>">
								</TD>
							</TR>
							
							<tr>
								<TD  class="plainlabel">Hoạt động </TD>
						        <TD colspan = "3" class="plainlabel">
									<input name="trangthai" type="checkbox" value = "1" checked>
								</TD>							
							</tr>
					</table>
				</FIELDSET>
				</TD>
			</TR>
		</TABLE>
						
				<table width="100%" cellspacing="1px" cellpadding="4px">
             	<tr class="tbheader">             	
                	<th width="20px" align="center"> Mã Khu Vực</th>
                    <th width="100px" align="left"> Tên Khu Vực</th>
                    <th width="20px" align="center">Chọn</th>
                </tr>
                <% if(khuvuc != null){
                	int n = 0;
					try{ while(khuvuc.next())
					{ %>
					<% if (n % 2 == 0){ %>
					<TR class= "tblightrow" >
					<%}else{ %>
					<TR class= "tbdarkrow" >
					<%} %>
						<TD align="center"><%= khuvuc.getString("kvId") %></TD>
						<TD align="left"><%= khuvuc.getString("khuvuc") %></TD>
									
						<% 
						for(int i=0;i<kvIds.size();i++)
						{
							System.out.println("KhuvucIds : "+kvIds.get(i)+" - kvId : "+khuvuc.getString("kvId"));
						} 
						if(kvIds.contains(khuvuc.getString("kvId"))){ %>
							   <TD align="center"><input name="kvIds" type="checkbox" onChange="ajaxOption('nppIds')" value ="<%= khuvuc.getString("kvId") %>"  checked ></TD>
						<%}else{%>
							   <TD align="center"><input name="kvIds" type="checkbox" onChange="ajaxOption('nppIds')" value ="<%= khuvuc.getString("kvId") %>" ></TD>
						<%}%> 
                    </TR> <%
                    	n++;
					} %> 
				     	<tr class="tbheader"><td colspan="4">&nbsp;</td></tr>
				   <% khuvuc.close(); }catch(java.sql.SQLException e){} 
                } %>	                                                              		  
             </table> 
            
             
             <tr>
             <td>
             	<FIELDSET>
				<LEGEND class="legendtitle">&nbsp;Chi nhánh / Đối tác </LEGEND>
					<div style="height: auto">
					<TABLE width="100%" cellspacing="0" cellpadding="6">
				<TR>
						<TD colspan="4" width="100%">
						<div id="nppIds">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="6">
							<tbody id="tb_npp">
							<TR class="tbheader">
								<TH align="center" width="15%">Mã Chi nhánh/Đối tác</TH>
								<TH align="center" width="73%">Tên Chi nhánh/Đối tác</TH>
								<TH align="center" width="1%" style="display: none;" >Ngày bắt đầu </TH>
                                <TH align="center" width="1%" style="display: none;">Ngày kết thúc </TH>   						
								 <TH align="center" width="10%"> Chọn tất cả <input type ="checkbox" id ="cbNpp" onClick="sellectAll('cbNpp','npp')"></TH>
							</TR>
							
			<% int k = 0;
			  
			  if (Dsnpp != null) 
			  {	try 
			  	{	while (Dsnpp.next()) 
			  		{	if (k % 2 == 0) 
			  			{%>
							
							<TR class='tbdarkrow'>
					  <%} else {%>													
							<TR class='tblightrow'>
					  <%}%>
								<TD align="center"><%=Dsnpp.getString("pk_seq")%></TD>
								<TD align="left"><%=Dsnpp.getString("ten")%></TD>		
								<TD align="center" style="display: none;" ><input class="days" name="ngaybatdau<%=Dsnpp.getString("pk_seq")%>" value='<%=Dsnpp.getString("ngaybatdau")%>'>  </TD>
								<TD align="center" style="display: none;"><input class="days"  name="ngayketthuc<%=Dsnpp.getString("pk_seq")%>" value='<%=Dsnpp.getString("ngayketthuc")%>'> </TD>						
						<%if (nppIds.contains(Dsnpp.getString("pk_seq"))) 
						{%>
								<TD align="center"><input type="checkbox" name="npp" id="npp" value="<%=Dsnpp.getString("pk_seq")%>" checked="checked"></TD>
					  <%}else{%>
								<TD align="center"><input type="checkbox" name="npp" id="npp" value="<%=Dsnpp.getString("pk_seq")%>"></TD>
					  <%}%>
							</TR>
					  <%k++;
					} //while
			  		Dsnpp.close();
				} catch (Exception ex) {}
			 }%>
							<TR>
								<TD align="center" colspan="10" class="plainlabel">&nbsp;</TD>
							</TR>
							</tbody>
							</TABLE>
						</div>
							
						</TABLE>
					</div>
					<!-- </div> -->
				</FIELDSET>
             </td>
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
</BODY>
</HTML>
<%}%>