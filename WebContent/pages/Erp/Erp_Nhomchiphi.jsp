<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.nhomchiphi.INhomchiphi" %>
<%@ page  import = "geso.traphaco.erp.beans.nhomchiphi.INhomchiphiList" %>
<%@ page  import = "geso.traphaco.center.db.sql.dbutils" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "geso.traphaco.center.util.Utility" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%
	INhomchiphiList obj = (INhomchiphiList)session.getAttribute("obj"); %>
<% 	ResultSet ncplist = (ResultSet)obj.getNcpList(); 
	ResultSet ctylist = (ResultSet)obj.getCtyList() ;
	ResultSet dvttlist = (ResultSet)obj.getDvttList() ;
	 int[] quyen = new  int[5];
	 quyen = util.Getquyen("Erp_NhomchiphiSvl", "13",userId);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>TraphacoHYERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">


<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {		
			$( ".days" ).datepicker({			    
					changeMonth: true,
					changeYear: true				
			});            
        }); 		
		
</script>


<SCRIPT language="JavaScript" type="text/javascript">
function submitform()
{
	 document.ncpForm.action.value = "new";
   	 document.forms["ncpForm"].submit();
}

function searchform()
{
	 document.ncpForm.action.value = "search";
   	 document.forms["ncpForm"].submit();
}

function clearform()
{
	document.ncpForm.diengiai.value = "";
	document.ncpForm.loai.selectedIndex = 2;
	document.ncpForm.ctyId.selectedIndex = 0;
	document.ncpForm.dvttId.selectedIndex = 0;
    document.ncpForm.trangthai.selectedIndex = 0;
    document.ncpForm.action.value = "search";
    document.forms["ncpForm"].submit();
}

</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="ncpForm" method="post" action="../../Erp_NhomchiphiSvl">
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value="1">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
				   		<table width="100%" border="0" cellpadding="0" cellspacing="0">
					  		<tr height="22">
						  		<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Tài chính  &gt; Nhóm chi phí và chi phí</TD> 
						  		<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD>
						  	</tr>
						</table></TD>
			  	</TR>
			</TABLE>
			
			<TABLE width="100%" border="0" cellpadding="0">
				<TR>
					<TD >
						<FIELDSET><LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm &nbsp;</LEGEND>
							<TABLE  width="100%" cellspacing="0" cellpadding="6">
								
								<TR>
									<TD width="19%" class="plainlabel">Tên / Diễn giải </TD>
								    <TD width="81%" class="plainlabel">
										<TABLE cellpadding="0" cellspacing="0">
											<TR><TD>
												<INPUT name="diengiai" type="text" size="40" value='<%=obj.getDiengiai()%>' onChange ="searchform();">
											</TD></TR>
										</TABLE>								
								</TR>
								<TR>
								  <TD class="plainlabel">Loại </TD>
								  <TD  class="plainlabel"><select name="loai" onChange = "searchform();">
								    <% if (obj.getLoai().equals("1")){ %>								    
								    	<option value="1" selected>Nhóm chi phí</option>
								    	<option value="2">Chi phí</option>
								    	<option value=""> </option>
								    <%}else{ 
								    	 if (obj.getLoai().equals("2")){ %>								    
								    		<option value="1">Nhóm chi phí</option>
								    		<option value="2" selected>Chi phí</option>
								    		<option value=""> </option>
								    	<%}else{ %>							    							    
								    		<option value="1" >Nhóm chi phí</option>
								    		<option value="2">Chi phí</option>
								    		<option value="" selected> </option>
								    <%} }%>
								    
								    </select>
								  </TD>
							  	</TR>
						      	<TR>
									<TD width="19%" class="plainlabel">Phòng ban </TD>
  								  	<TD class="plainlabel" >
										<TABLE cellpadding="0" cellspacing="0" border="0">
											<TR>
										  		<TD><SELECT  name="dvttId" onChange="searchform();">												
													<option value=""  ></option>
												
												<% 	if(dvttlist != null){
														try{
															while (dvttlist.next()){%>
																
																<%	if(dvttlist.getString("DVTTID").equals(obj.getDvttId())){ %>											
																		<option value= <%=dvttlist.getString("DVTTID")%> selected><%= dvttlist.getString("TEN") %></option>															
																<%}else{%>
																		<option value= <%=dvttlist.getString("DVTTID")%> ><%= dvttlist.getString("TEN") %></option>																																		
																<%	}
															}
															
														}catch(java.sql.SQLException e){}
													}
														%>														                                           
                                              			</SELECT>
                                         			</TD>
											</TR>
										</TABLE>									
									</TD>

								</TR>

								<TR>
									<TD class="plainlabel">Trạng thái </TD>
								  	<TD  class="plainlabel"><SELECT name = "trangthai"  onChange = "searchform();">
								  	<% if (obj.getTrangthai().equals("0")){ %>
								    	<option value=""> </option>
								    	<option value="1">Hoạt động</option>
								    	<option value="0" selected>Ngưng hoạt động</option>
									<%}else{ 							
								  		if (obj.getTrangthai().equals("1")){ %>
								    	<option value=""> </option>
								    	<option value="1" selected>Hoạt động</option>
								    	<option value="0" >Ngưng hoạt động</option>
									<%}else{ %>
								    	<option value="" selected> </option>
								    	<option value="1" >Hoạt động</option>
								    	<option value="0" >Ngưng hoạt động</option>
									<%}} %>

										</SELECT></TD>
									
								<TR>
									<TD class="plainlabel">
									<a class="button2" href="javascript:clearform()">
	<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;

                                    </TD>
                                    								
									<TD class="plainlabel">&nbsp;</TD>										
								</TR>
								
							</TABLE>
					  </FIELDSET>
					</TD>	
				</TR>
			</TABLE>
			
			<TABLE width="100%" border = "0" cellpading = "0" cellspacing = "0">
				<TR>
					<TD width="100%">
					<FIELDSET>
					<LEGEND class="legendtitle">&nbsp;Nhóm chi phí và chi phí&nbsp;&nbsp;&nbsp;
					<%if(quyen[0]!=0){ %>
						<a class="button3" href="javascript:submitform()">
				    	<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>     
				    	<%} %>                       		
					</LEGEND>
					<TABLE class="" width="100%">
						<TR>
							<TD width="98%">
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
								<TR class="tbheader">
									<TH width="7%" >Thuộc nhóm</TH>									
									<TH width="30%" >Tên và diễn giải</TH>
									<TH width="10%">Loại  </TH>
									<TH width="10%">Trạng thái </TH>
									<TH width="15%">Đơn vị</TH>
									<TH width="8%">Tác vụ</TH>
								</TR>
						<%=  obj.getData(userId) %> 
							
						<TR>
							<TD align="center" colspan="12" class="tbfooter">&nbsp;</TD>
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
<% 
 	if( ncplist != null) ncplist.close(); 
 	if( ctylist != null)  ctylist.close();
 	if( dvttlist != null) dvttlist.close();
 	session.setAttribute("obj",null);
 	obj.DBClose();
	} %>
	