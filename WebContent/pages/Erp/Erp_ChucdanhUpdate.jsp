<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.chucdanh.IChucdanh" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<%  IChucdanh cdBean = (IChucdanh)session.getAttribute("cdBean");%>
<%  ResultSet nvlist = (ResultSet)cdBean.getNvList(); 
	ResultSet  rsnvquanly=(ResultSet)cdBean.getRsNvquanly();
	ResultSet rsDvth = (ResultSet)cdBean.getRsDvth(); 
	ResultSet rsCty = (ResultSet)cdBean.getRsCty();
	String strnvquanly=cdBean.getNvquanly();
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Phanam - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script> 
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>

<SCRIPT language="JavaScript" type="text/javascript">
<!--HPB_SCRIPT_CODE_40
//-->
function submitform()
{
	document.forms['cdForm'].action.value= 'xoa';
    document.forms["cdForm"].submit();
}
function saveform()
{
	document.forms['cdForm'].action.value= 'save';
    document.forms["cdForm"].submit();
}
function focusCty(){
	alert("Không thể thay đổi vì công ty đã dùng chức danh này");	
	//submitform();
}

function sellectAll(id1, id2)
{
	 var chkAll = document.getElementById(id1);
	 var nvIds = document.getElementsByName(id2);
	 
	 if(chkAll.checked)
	 {
		 for(i = 0; i < nvIds.length; i++)
		 {
			 nvIds.item(i).checked = true;
		 }
	 }
	 else
	 {
		 for(i = 0; i < nvIds.length; i++)
		 {
			 nvIds.item(i).checked = false;
		 }
	 }
}

</SCRIPT>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2();  });
     
</script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="cdForm" method="post" action="../../Erp_ChucdanhUpdateSvl" charset="utf-8">
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
<INPUT name="cdId" type="hidden" value='<%=cdBean.getId() %>' size="30">
<INPUT name="action" type="hidden" value='' size="30">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">

		<TABLE width="100%" cellpadding="0" cellspacing="1">
			
				<TR>
					<TD align="left">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
						   <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Cơ bản &gt; Chức danh > Cập nhật </TD>
   
						   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn<%=userTen %>&nbsp;  </TD>
						  </tr>
 					  </table>
					</TD>
				</TR>
		</TABLE>		
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR ><TD >
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR class = "tbdarkrow">
									<TD width="30" align="left"><A href="../../Erp_ChucdanhSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
								    <TD width="2" align="left" ></TD>
								    <TD width="30" align="left" ><A href="javascript: saveform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
									<TD >&nbsp; </TD>						
								</TR>
						</TABLE>
				</TD></TR>
		</TABLE>
					
		<TABLE width = 100% cellpadding = "4" cellspacing = "0" border = "0">
		  	<tr>
				<TD align="left" colspan="4" class="legendtitle">
					<FIELDSET>
					<LEGEND class="legendtitle">Thông báo</LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100%" readonly="readonly" rows="1"><%=cdBean.getMessage()%></textarea>
						<% cdBean.setMessage(""); %>
						</FIELDSET>
				   </TD>
			</tr>			
			<TR>
				<TD width="100%" align="left" >
					<FIELDSET>
					<LEGEND class="legendtitle">&nbsp;Thông tin Chức danh</LEGEND>

					<TABLE  width="100%" cellpadding="6" cellspacing="0">
						<TR>
							<TD width="20%" class="plainlabel">Chức danh <FONT class="erroralert">*</FONT></TD>
							<TD width="80%" class="plainlabel"><INPUT name="chucdanh" value='<%=cdBean.getChucdanh() %>' type="text" size="40"/></TD>
						</TR>
						
						<TR>
							<TD width="19%" class="plainlabel">Công ty </TD>
							<td class="plainlabel">
					  			<SELECT  name="ctyId" class="select2" style="width: 220px;" onchange=" submitform()" >
						
								<%try {%>
								<% 	while (rsCty.next()){%>
										
										<%	if(rsCty.getString("PK_SEQ").equals(cdBean.getCtyId())){ %>											
												
												<option value= <%=rsCty.getString("PK_SEQ")%> selected><%= rsCty.getString("TEN") %></option>															
											<%}else{%>
												<option value= <%=rsCty.getString("PK_SEQ")%> ><%= rsCty.getString("TEN") %></option>																																		
											<%	}
									 }
												
								 }catch(java.sql.SQLException e){
											
								 }%>														                                           
                               	</SELECT>
                         	</td>
						</TR>
						
				      	<TR>
							<TD width="19%" class="plainlabel">Người được bổ nhiệm <FONT class="erroralert">*</FONT></TD>
							  	<TD class="plainlabel" >
								<TABLE cellpadding="0" cellspacing="0" border="0">
									<TR>
								  		<TD>
								  			<SELECT  name="nvId"  onchange=" submitform()" class="select2" style="width: 220px;" >
									
											<%try {%>
												<option value="0"  ></option>
											<% 	while (nvlist.next()){%>
													
													<%	if(nvlist.getString("NVID").equals(cdBean.getNvId())){ %>											
															
															<option value= <%=nvlist.getString("NVID")%> selected><%= nvlist.getString("TEN") %></option>															
														<%}else{%>
															<option value= <%=nvlist.getString("NVID")%> ><%= nvlist.getString("TEN") %></option>																																		
														<%	}
												 }
															
											 }catch(java.sql.SQLException e){
														
											 }%>														                                           
                                      			</SELECT>
                                   			</TD>
										</TR>
									</TABLE>									
								</TD>

						</TR>

						
						
						<TR>
							<TD width="19%" class="plainlabel">Phòng ban </TD>
							  	<TD class="plainlabel" >
								<TABLE cellpadding="0" cellspacing="0" border="0">
									<TR>
								  		<TD>
								  			<SELECT  name="pbId" onchange="submitform()" class="select2" style="width: 220px;" >
									
											<%try {%>
												<option value="0"  ></option>
											<% 	while (rsDvth.next()){%>
													
													<%	if(rsDvth.getString("PK_SEQ").equals(cdBean.getDvthId())){ %>											
															
															<option value= <%=rsDvth.getString("PK_SEQ")%> selected><%= rsDvth.getString("TEN") %></option>															
														<%}else{%>
															<option value= <%=rsDvth.getString("PK_SEQ")%> ><%= rsDvth.getString("TEN") %></option>																																		
														<%	}
												 }
															
											 }catch(java.sql.SQLException e){
														
											 }%>														                                           
                                      			</SELECT>
                                   			</TD>
										</TR>
									</TABLE>									
								</TD>
						</TR>
						
						<TR>
							<TD width="19%" class="plainlabel">Loại </TD>
							<td class="plainlabel">
					  			<SELECT  name="loai" class="select2" style="width: 220px;">
								<%	if(cdBean.getLoai().equals("0")){ %>
									<option value="" ></option>			
									<option value="0" selected>Duyệt mua hàng nhập khẩu</option>
									<option value="1" >Duyệt mua hàng khác</option>		
									<option value ="2" >Duyệt mua hàng trong nước </option>													
								<% }else if(cdBean.getLoai().equals("1")){%>
									<option value="" ></option>			
									<option value="0" >Duyệt mua hàng nhập khẩu</option>
									<option value="1" selected>Duyệt mua hàng khác</option>
									<option value ="2" >Duyệt mua hàng trong nước </option>
								<% }else if(cdBean.getLoai().equals("2")){%>
									<option value="" ></option>			
									<option value="0" >Duyệt mua hàng nhập khẩu </option>
									<option value="1" >Duyệt mua hàng khác</option>
									<option value ="2" selected>Duyệt mua hàng trong nước </option>	
								<% }else{ %>
									<option value="" selected></option>			
									<option value="0" >Duyệt mua hàng nhập khẩu </option>
									<option value="1" >Duyệt mua hàng khác</option>
									<option value ="2" >Duyệt mua hàng trong nước </option>	
								<% } %>															                                           
                               	</SELECT>
                         	</td>
						</TR>
						
						<TR>
							<TD class="plainlabel" colspan="3">
							<% if(cdBean.getTrangthai().equals("1")) {%>						
						  		<input name="trangthai" type="checkbox" value="1" checked>
						  	<%}else{ %>
						  		<input name="trangthai" type="checkbox" value="0" >
						  	<%} %>
							      Hoạt động &nbsp;&nbsp;&nbsp;
							<% if(cdBean.getDuyetDtvt().equals("1")) {%>						
						  		<input name="duyetdtvt" type="checkbox" value="1" checked>
						  	<%}else{ %>
						  		<input name="duyetdtvt" type="checkbox" value="1" >
						  	<%} %>
							      Duyệt dự toán vật tư 
							</TD>
														
						</TR>
						
						<TR>
	                    	<TD class="plainlabel" colspan="2" >
	                    	<fieldset  >
								<legend> Chức danh ( ĐNTT/ĐNTƯ ) </legend>
								 <table id="tableDDH" width="100%">
	               					<tbody> 
		               					<TR> 
		               						<% if(cdBean.getisTP().equals("1")) {%>						
										  		<TD class="plainlabel"><input name="isTP" type="checkbox" value="1" checked>
										  	<%}else{ %>
										  		<TD class="plainlabel"><input name="isTP" type="checkbox" value="0" >
										  	<%} %>
											      Trưởng phòng </TD>
											      
											<% if(cdBean.getisKTV().equals("1")) {%>						
										  		<TD class="plainlabel"><input name="isKTV" type="checkbox" value="1" checked>
										  	<%}else{ %>
										  		<TD class="plainlabel"><input name="isKTV" type="checkbox" value="0" >
										  	<%} %>
											      Kế toán gán mã chi phí </TD>
											      
		               						<% if(cdBean.getisKTT().equals("1")) {%>						
										  		<TD class="plainlabel"><input name="isKTT" type="checkbox" value="1" checked>
										  	<%}else{ %>
										  		<TD class="plainlabel"><input name="isKTT" type="checkbox" value="0" >
										  	<%} %>
											      Kế toán trưởng </TD>
		               					    
								      	</TR>
	   								</tbody>                     								                  							
	                   			</table>
	                   		</fieldset>
	                    	</TD> 
	                    </TR>
	                    
											
						<tr>
							<TD colspan="2" class="plainlabel"    >Chọn nhân viên quản lý <FONT class=""> </FONT></TD>
							
						</tr>
						  	<TR>
								
							  	<TD colspan="2"  >
								<TABLE cellpadding="0" width="100%" cellspacing="0" border="1">
								
									<TR class="tbheader">
										<th>
											Đăng nhập
										</th>
										<th>
											Tên
										</th>
										<th>
											Chọn hết
											<input type="checkbox" id='chkAll' onchange="sellectAll('chkAll', 'Idnvchon');"   >
										</th>
									</TR>
										<% while (rsnvquanly.next()){ %>
											<tr>
												<td>
													<%=rsnvquanly.getString("DANGNHAP") %>
												</td>
												<td>
													<%=rsnvquanly.getString("TEN") %>
												</td>
												<td>
													<%if(strnvquanly.contains(rsnvquanly.getString("PK_SEQ"))){ %>
														<input type="checkbox" name="Idnvchon" checked="checked" value="<%= rsnvquanly.getString("PK_SEQ")%>"> 
													<%}else{ %>
															<input type="checkbox" name="Idnvchon"  value="<%= rsnvquanly.getString("PK_SEQ")%>">
													<%} %>
												</td>
												
											</tr>
										<% } %>
								  		 
									</TABLE>									
								</TD>

						</TR>		
				</TABLE>

				</FIELDSET>
			</TD>
		</TR>
	</TABLE>
	</TD></TR>
</TABLE>
</form>
</BODY>
</HTML>
<%  
   if(nvlist != null)   nvlist.close();
	cdBean.DBClose();
}%>
