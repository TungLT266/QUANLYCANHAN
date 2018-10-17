<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.erp.beans.yeucaukythuat.IErpYeuCauKyThuat"%>
<%@page import="geso.traphaco.erp.beans.yeucaukythuat.imp.ErpYeuCauKyThuat_HoaChat"%>
<%@page import="geso.traphaco.center.util.*"%>
<%@page import="java.util.List"%>

<%String userId = (String) session.getAttribute("userId");  
String userTen = (String) session.getAttribute("userTen");  	
String sum = (String) session.getAttribute("sum");
Utility util = (Utility) session.getAttribute("util");
if(!util.check(userId, userTen, sum)){
	response.sendRedirect("/TraphacoHYERP/index.jsp");
} else {
	IErpYeuCauKyThuat obj = (IErpYeuCauKyThuat)session.getAttribute("obj");
	List<ErpYeuCauKyThuat_HoaChat> hoachatList = obj.getHoachatList();
	ResultSet sanphamRs = obj.getSanphamRs();
	ResultSet dvtRs = obj.getDvtRs(); %>
	
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
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		$(".select2").select2();
	});
</script>

<SCRIPT language="JavaScript" type="text/javascript">
	function keypress(e) {    
		var keypressed = null;
		if(window.event)
			keypressed = window.event.keyCode;
		else
			keypressed = e.which;
		
		if((keypressed>31 && keypressed<43) || (keypressed>43 && keypressed<45) || keypressed == 47 || keypressed>57) {
	        return false;
	    }
	    return true;
	}
	
	function save() {
		if(document.getElementById("ma").value.trim() == ""){
			document.getElementById("dataerror").value = "Bạn chưa nhập mã yêu cầu kỹ thuật.";
			return false;
		}
		
		if(document.getElementById("ten").value.trim() == ""){
			document.getElementById("dataerror").value = "Bạn chưa nhập tên yêu cầu kỹ thuật.";
			return false;
		}
		
		if(document.getElementById("thongsotu").value.trim() != "" || document.getElementById("thongsoden").value.trim() != ""){
			if(document.getElementById("thongsotu").value.trim() != "" && document.getElementById("thongsoden").value.trim() != ""){
				var thongsotu = parseFloat(document.getElementById("thongsotu").value.trim());
				if(isNaN(thongsotu)){
					document.getElementById("dataerror").value = "Thông số yêu cầu từ không hợp lệ.";
					return false;
				}
				
				var thongsoden = parseFloat(document.getElementById("thongsoden").value.trim());
				if(isNaN(thongsoden)){
					document.getElementById("dataerror").value = "Thông số yêu cầu đến không hợp lệ.";
					return false;
				}
				
				if(thongsotu > thongsoden){
					document.getElementById("dataerror").value = "Thông số yêu cầu từ phải nhỏ hơn hoặc bằng thông số yêu cầu đến.";
					return false;
				}
			} else {
				if(document.getElementById("thongsotu").value.trim() != ""){
					document.getElementById("dataerror").value = "Bạn chưa nhập thông số yêu cầu đến.";
				}else {
					document.getElementById("dataerror").value = "Bạn chưa nhập thông số yêu cầu từ.";
				}
				return false;
			}
		}
		
		document.forms["yeucaukythuat"].action.value = "save";
		document.forms["yeucaukythuat"].submit(); 
    }
</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="yeucaukythuat" method="post" action="../../ErpYeuCauKyThuatUpdateSvl" >
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="id" value='<%= obj.getId() %>' >
<input type="hidden" name="action" value="0">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
							<tr height="22">
							  	<%if(obj.getId().length()>0){%>
							  		<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Hồ sơ kiểm nghiệm > Yêu cầu kỹ thuật > Cập nhật</TD>
							  	<%} else { %>
							  		<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Hồ sơ kiểm nghiệm > Yêu cầu kỹ thuật > Tạo mới</TD>
						  		<%} %>
								<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD>
							</tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
			
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD>
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
							<TR class = "tbdarkrow">
								<TD width="30" align="left">
									<A href="../../ErpYeuCauKyThuatSvl?userId=<%=userId %>" >
										<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset">
									</A>
								</TD>
							    <TD width="2" align="left" ></TD>
						    	<TD width="30" align="left" >
						    		<div id="btnSave">
						    			<A href="javascript: save()" >
						    				<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset">
					    				</A>
						    		</div>
							    </TD>
								<TD >&nbsp; </TD>						
							</TR>
						</TABLE>
					</TD>
				</TR>
			</TABLE>
			
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
							<LEGEND class="legendtitle">Thông báo </LEGEND>
		    				<textarea name="dataerror" id="dataerror" readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  	<TD height="100%" width="100%">
						<FIELDSET>
							<LEGEND class="legendtitle" style="color:black">Yêu cầu kỹ thuật </LEGEND>
							<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
                          		<TR>
	                          		<TD class="plainlabel" valign="middle" width="200px" >Mã yêu cầu kỹ thuật <FONT class="erroralert">*</FONT></TD>   
			                        <TD class="plainlabel" valign="middle" colspan="3">
			                        	<input type="text" style="width:35%" name="ma" id="ma" value="<%=obj.getMa() %>">
			                        </TD>
                  				</TR>
                  			
	                          	<TR>
	                          		<TD class="plainlabel" valign="middle" width="200px">Tên yêu cầu kỹ thuật <FONT class="erroralert">*</FONT></TD>
			                        <TD class="plainlabel" valign="middle" colspan="3">
			                        	<input type="text" style="width:35%" name="ten" id="ten" value="<%= obj.getTen() %>"  > 
			                        </TD>          
			                  	</TR>
			                  	
			                  	<TR>
	                          		<TD class="plainlabel" valign="middle" width="200px">Mô tả/Diễn giải</TD>
			                        <TD class="plainlabel" valign="middle" colspan="3">
			                        	<textarea name="diengiai" rows="7" style="width: 60%;color: black;"><%=obj.getDiengiai() %></textarea>
			                        </TD>          
			                  	</TR>
			                  	
			                  	<TR>
	                          		<TD class="plainlabel" valign="middle" width="200px">Thông số yêu cầu từ</TD>
			                        <TD class="plainlabel" valign="middle" style="width: 200px">
			                        	<input type="text" style="width:150px;text-align:right" name="thongsotu" id="thongsotu" value="<%=obj.getThongsoTu() %>" onkeypress="return keypress(event);"> 
			                        </TD>
			                        <TD class="plainlabel" valign="middle" width="200px">Thông số yêu cầu đến</TD>
			                        <TD class="plainlabel" valign="middle">
			                        	<input type="text" style="width:150px;text-align:right" name="thongsoden" id="thongsoden" value="<%=obj.getThongsoDen() %>" onkeypress="return keypress(event);"> 
			                        </TD>
			                  	</TR>
			                  	
			                  	<TR>
	                          		<TD class="plainlabel" valign="middle" width="200px">DVT</TD>
			                        <TD class="plainlabel" valign="middle" style="width: 200px">
			                        	<select name="donvitinh" style="width: 150px" class="select2">
			                        		<option value=""></option>
			                        		<%if(dvtRs != null){
												while(dvtRs.next()){ 
													if(dvtRs.getString("pk_seq").equals(obj.getDvt())){ %>
														<option value="<%=dvtRs.getString("pk_seq") %>" selected><%=dvtRs.getString("donvi") %></option>
													<%} else { %>
														<option value="<%= dvtRs.getString("pk_seq") %>" ><%=dvtRs.getString("donvi") %></option>
													<%}
												}
											} %>
			                        	</select>
			                        </TD>
			                        <TD class="plainlabel" valign="middle" width="200px">Giới hạn cho phép/Dung sai</TD>
			                        <TD class="plainlabel" valign="middle">
			                        	<input type="text" style="width:150px;text-align:right" name="gioihan" value="<%=obj.getGioihan() %>"  > 
			                        </TD>
			                  	</TR>
		                  	
			                  	<TR>
	                          		<TD class="plainlabel" valign="middle" width="200px">Trạng thái </TD>   
			                        <TD class="plainlabel" valign="middle" colspan="3">
			                            <%if(obj.getTrangthai().equals("1")){ %>
			                            	<input type="checkbox" name="trangthai" value="1" checked><i> Hoạt động</i>
			                            <%} else { %>
			                            	<input type="checkbox" name="trangthai" value="1" ><i> Hoạt động</i>
			                            <%} %>
			                        </TD>                
			                  	</TR>
							</TABLE>
							
							<table cellpadding="0px" cellspacing="1px" width="100%">
								<tr class="tbheader">
									<th width="5%" >STT</th>
									<th width="34%" >Hóa chất</th>
									<th width="15%" >Số lượng</th>
									<th width="23%" >Mã số</th>
									<th width="23%" >Cách pha</th>
								</tr>
								
								<%ErpYeuCauKyThuat_HoaChat hoachat;
								for(int i=0; i<hoachatList.size(); i++){
									hoachat = hoachatList.get(i); %>
									<tr>
										<td style="text-align: center;"><%=i+1 %></td>
										<td>
											<select name="hoachat" style="width: 100%" class="select2">
												<option value="" ></option>
												<%if(sanphamRs != null){
													sanphamRs.beforeFirst();
													while(sanphamRs.next()){ 
														if(sanphamRs.getString("pk_seq").equals(hoachat.getHoachat())){ %>
															<option value="<%=sanphamRs.getString("pk_seq") %>" selected><%=sanphamRs.getString("ten") %></option>
														<%} else { %>
															<option value="<%= sanphamRs.getString("pk_seq") %>" ><%=sanphamRs.getString("ten") %></option>
														<%}
													}
												} %>
											</select>
										</td>
										<td><input type="text" name="soluong" style="width: 100%;text-align: right;" value="<%=hoachat.getSoluong() %>" onkeypress="return keypress(event);"></td>
										<td><input type="text" name="maso" value="<%=hoachat.getMaso() %>" style="width: 100%"></td>
										<td><input type="text" name="cachpha" value="<%=hoachat.getCachpha() %>" style="width: 100%"></td>
									</tr>
								<%} %>
								
								<%for(int j=hoachatList.size(); j<10; j++){ %>
									<tr>
										<td style="text-align: center;"><%=j+1 %></td>
										<td>
											<select name="hoachat" style="width: 100%" class="select2">
												<option value="" ></option>
												<%if(sanphamRs != null){
													sanphamRs.beforeFirst();
													while(sanphamRs.next()){ %>
														<option value="<%=sanphamRs.getString("pk_seq") %>"><%=sanphamRs.getString("ten") %></option>
													<%}
												} %>
											</select>
										</td>
										<td><input type="text" name="soluong" style="width: 100%;text-align: right;" value="" onkeypress="return keypress(event);"></td>
										<td><input type="text" name="maso" value="" style="width: 100%"></td>
										<td><input type="text" name="cachpha" value="" style="width: 100%"></td>
									</tr>
								<%} %>
							</table>
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
	if (obj != null) {
		obj.DBClose();
		obj = null;
	}
	session.removeAttribute("obj");
} %>
