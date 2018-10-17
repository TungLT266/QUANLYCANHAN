<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.sohoadon.ISohoadon" %>
<%@ page  import = "geso.traphaco.erp.beans.sohoadon.ISohoadonList" %>
<%@ page  import = "geso.traphaco.erp.db.sql.dbutils" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%
	ISohoadonList obj = (ISohoadonList)session.getAttribute("obj"); %>
<% 	
	ResultSet sohoadonList = (ResultSet)obj.getSohoadonList();
	ResultSet kholist = (ResultSet)obj.getKhoList() ;
	int[] quyen = new  int[5];
	quyen = util.Getquyen("Erp_SohoadonSvl","&loaiNPP=0&isKH=0",userId);
	NumberFormat formatter = new DecimalFormat("#,###,###"); 
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
	 document.sohoadonForm.action.value = "new";
   	 document.forms["sohoadonForm"].submit();
}

function searchform()
{
	 document.sohoadonForm.action.value = "search";
   	 document.forms["sohoadonForm"].submit();
}

function clearform()
{
	document.sohoadonForm.khoId.selectedIndex = 0;
    document.sohoadonForm.trangthai.selectedIndex = 0;
    document.sohoadonForm.loai.selectedIndex = 0;
    document.sohoadonForm.action.value = "search";
    document.forms["sohoadonForm"].submit();
}

</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="sohoadonForm" method="post" action="../../Erp_SohoadonSvl">
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
						  		<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kế toán  &gt; Số hóa đơn</TD> 
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
									<TD width="19%" class="plainlabel">Kho vùng </TD>
  								  	<TD class="plainlabel" >
										<TABLE cellpadding="0" cellspacing="0" border="0">
											<TR>
										  		<TD><SELECT  name="khoId" onChange="searchform();">												
													<option value=""  ></option>
												
												<% 	if(kholist != null){
														try{
															while (kholist.next()){%>
																
																<%	if(kholist.getString("KHOID").equals(obj.getKhoId())){ %>											
																		<option value= <%=kholist.getString("KHOID")%> selected><%= kholist.getString("TEN") %></option>															
																<%}else{%>
																		<option value= <%=kholist.getString("KHOID")%> ><%= kholist.getString("TEN") %></option>																																		
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
									<TD class="plainlabel">Loại </TD>
								  	<TD  class="plainlabel"><SELECT name = "loai"  onChange = "searchform();">
								  	<% if (obj.getLoai().equals("1")){ %>
								  		<option value=""  ></option>
								    	<option value="1" SELECTED >Hóa đơn</option>
								    	<option value="2" >Xuất kho</option>
								    	
									<%}else if (obj.getLoai().equals("2")){ %>							
								  		<option value=""  ></option>
								    	<option value="1" >Hóa đơn</option>
								    	<option value="2" SELECTED >Xuất kho</option>			    	

									<%}else{ %>
										<option value=""  ></option>
								    	<option value="1" >Hóa đơn</option>
								    	<option value="2" >Xuất kho</option>

									<%} %>

										</SELECT></TD>
									
								<TR>

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
					<LEGEND class="legendtitle">&nbsp;Số hóa đơn&nbsp;&nbsp;&nbsp;
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
									<TH width="10%" >Từ số </TH>
									<TH width="10%" >Đến số </TH>
									<TH width="10%" >Ký hiệu </TH>
									<TH width="10%">Trạng thái </TH>
									<TH width="25%">Kho vùng</TH>
									<TH width="10%">Loại</TH>
									<TH width="20%">Người tạo</TH>
									<TH width="7%">Ngày tạo</TH>
									
									<TH width="15%">Tác vụ</TH>
								</TR>
						<%  
						int m = 0;
						String lightrow = "tblightrow";
						String darkrow = "tbdarkrow";

						if(sohoadonList != null){
							while(sohoadonList.next()){ 
								if(m % 2 != 0) {%>
									<TR class = <%=lightrow%> >
							<%	} else {%>
									<TR class = <%=darkrow%> >
							<%	}%>
										<TD><%= sohoadonList.getString("TUSO") %>
										<TD><%= sohoadonList.getString("DENSO") %>
								    											
								    <% if (sohoadonList.getString("TRANGTHAI").equals("1")){ %>								    
									
										<TD align = "center">Hoạt động</TD>
									
									<%}else{ %>
									
										<TD align = "center">Ngưng hoạt động</TD>
									<%} %>
									<TD align = "center"><%= sohoadonList.getString("KYHIEUHD") %>
									<TD align = "left"><%= sohoadonList.getString("KHOVUNG") %>
									<TD align = "center"><%= sohoadonList.getString("LOAI") %>
									<TD align = "left"><%= sohoadonList.getString("NGUOITAO") %>
									<TD align = "center"><%= sohoadonList.getString("NGAYTAO") %>
									<TD align = "center">
										<TABLE border = 0 cellpadding="0" cellspacing="0">
											<TR>
												<TD>
										<% if(obj.isNotInUse(sohoadonList.getString("SOHOADONID"))){ %>
												<%if(quyen[2]!=0){ %>
													<A href = "../../Erp_SohoadonUpdateSvl?userid=<%=userId%>&update=<%=sohoadonList.getString("SOHOADONID")%>"><img title="Cập nhật" src="../images/Edit20.png" alt="Chinh sua" width="20" height="20" longdesc="Chinh sua" border = 0></A>&nbsp;
												<%} %>
												</TD>
												
												<TD>
												<%if(quyen[1]!=0){ %>
													<A href = ../../Erp_SohoadonSvl?userid=<%=userId%>&delete=<%=sohoadonList.getString("SOHOADONID")%>><img title="Xóa" src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa Số hóa đơn này ?')) return false;"></A>&nbsp;
												<%}%>
												</TD>
										<%} %>
												<TD>&nbsp;</TD>
												<TD>&nbsp;</TD>
												<TD>
												<!-- QUYEN VIEW DLN -->
													<A href = "../../Erp_SohoadonUpdateSvl?userid=<%=userId%>&display=<%=sohoadonList.getString("SOHOADONID")%>"><img src="../images/Display20.png" title="Hiển thị" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
												<!-- END QUYEN VIEW DLN -->
												</TD>
									 	
											</TR>
										</TABLE>				
								</TR>
									
									<% 	m++;
									
							}
							 
						}%>
							
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
 	if( sohoadonList != null) sohoadonList.close(); 
 	if( kholist != null) kholist.close();
 	session.setAttribute("obj",null);
 	obj.DBClose();
	} %>
	