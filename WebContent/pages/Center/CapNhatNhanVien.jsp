<%@page import="geso.traphaco.center.beans.capnhatnhanvien.ICapnhatnhanvienList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.nhacungcap.INhacungcap" %>
<%@ page  import = "geso.traphaco.center.beans.nhacungcap.INhacungcapList" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{
		int[] quyen = new  int[6];
		quyen = util.Getquyen("CapnhatnhanvienSvl","",userId);

		%>

<% ICapnhatnhanvienList obj = (ICapnhatnhanvienList)session.getAttribute("obj"); 
obj.setNextSplittings();
%>
<% ResultSet nhomquyenRs = (ResultSet)obj.getNhomquyenRs(); %>
<% ResultSet phongbanRs = (ResultSet)obj.getPhongbanRs(); %>
<% ResultSet DSNV = (ResultSet)obj.getDSNV(); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
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
	
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>

<SCRIPT language="javascript" type="text/javascript">

function clearform()
{  document.forms['nccForm'].action.value= 'xoa';
     document.forms['nccForm'].submit();
}

function submitform()
{
	document.forms['nccForm'].action.value= 'search';
	document.forms['nccForm'].submit();
}

function newform()
{
	document.forms['nccForm'].action.value= 'new';
	document.forms['nccForm'].submit();
	document.forms['nccForm'].submit();
}
function deleteform()
{
	document.forms['nccForm'].action.value= 'delete';
	document.forms['nccForm'].submit();
}

function xuatExcel()
{
	document.forms['nccForm'].action.value= 'toExcel';
	document.forms['nccForm'].submit();
	document.forms['nccForm'].action.value= '';
}

</SCRIPT>

<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
		$(document).ready(function()
		{
			$(".select2").select2();
		});
	</script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="nccForm" method="post" action="../../CapnhatnhanvienSvl">
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="action" value='1'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" >
					   <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">
							   		&nbsp;Quản trị hệ thống &gt; Cập nhật nhân viên
							   </TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %> &nbsp;</td> 
						    </tr>
						</table>
					</TD>
				</TR>
				<TR>
					<TD>
						<TABLE border="0" width="100%" >
							<TR>
								<TD width="100%" align="left"><FIELDSET>
									<LEGEND class="legendtitle">Tiêu chí tìm kiếm  </LEGEND>

									<TABLE class="tblight" width="100%" cellpadding="6" cellspacing = "0">
										<TR>
											<TD width="19%" class="plainlabel" >Tên / đăng nhập </TD>
										  <TD width="81%" class="plainlabel" ><INPUT name="ten" type="text" value="<%=obj.getTen() %>" onChange="submitform();" ></TD>
										</TR>
										<TR>
											<TD class="plainlabel">Phân loại</TD>
											
											<TD class="plainlabel"><select name="phanloai" onChange="submitform();" class="select2" style="width: 200px" >
											   <option value =""></option>
									     	 <% if(obj.getPhanloai().equals("1")) { %>
										         <option value="1" selected>Chi nhánh / Đối tác</option>
				                                 <option value="2">Trung tâm</option>
				                                 <%} else  if(obj.getPhanloai().equals("2")){ %>
					                                  <option value="2" selected>Tổng công ty</option>
					                                  <option value="1">Công ty MTV</option>
				                                 <%} else {%>
				                                      <option value="2">Tổng công ty</option>
					                                  <option value="1">Công ty MTV</option>
											 <%} %>
											
										    </select></TD>
										</TR>
										<TR style="display: none;" >							
											<TD class="plainlabel" >Từ ngày </TD>
											<TD class="plainlabel" colspan="3">
												<TABLE cellpadding="0" cellspacing="0">
											<TR><TD>
										<input class="days" type="text" name="tungay" value='<%=obj.getTungay() %>'  size="20" onchange=submitform(); >
												</TD>
												
		   										</TR>
												</TABLE>
																						
		  									</TD>
										</TR>
										<TR style="display: none;" >
                                          <TD class="plainlabel" >Đến ngày </TD>
										  <TD class="plainlabel" colspan="3">
										  		<TABLE cellpadding="0" cellspacing="0"><TR><TD>
										 <input class="days" type="text" name="denngay" value='<%=obj.getDenngay() %>' size="20" onchange=submitform(); >
										  		</TD>
										

											  </TR>
											 </TABLE>
									  	</TR>
									  	<TR>
											<TD class="plainlabel" >Tên quyền </TD>
										    
										    <%-- <TD class="plainlabel" ><INPUT name="tenQuyen" type="text"  value="<%=obj.getTenquyen() %>" onChange="submitform();" ></TD> --%>
										
											<TD  class="plainlabel" >
										
											<select name="nhomquyenId" class="select2" style="width: 200px" onchange="submitform();">
												<option value="">ALL</option>
													<%if( nhomquyenRs != null)
														{ while (nhomquyenRs.next()){ %>
														<option value="<%=nhomquyenRs.getString("pk_seq")%>" <%if(nhomquyenRs.getString("pk_Seq").equals(obj.getNhomquyenId())) { %> selected="selected" <%} %>><%= nhomquyenRs.getString("ten") %></option>
													<% } nhomquyenRs.close(); } %>
											</select>
											
											</TD>
											
										</TR>
										
										<TR>
											<TD class="plainlabel" >Phòng ban </TD>
										
											<TD  class="plainlabel" >
										
											<select name="phongbanId" class="select2" style="width: 200px" onchange="submitform();">
												<option value="">ALL</option>
													<%if( phongbanRs != null)
														{ while (phongbanRs.next()){ %>
														<option value="<%=phongbanRs.getString("pk_seq")%>" <%if(phongbanRs.getString("pk_Seq").equals(obj.getPhongbanId())) { %> 
															selected="selected" <%} %>><%= phongbanRs.getString("ten") %></option>
													<% } phongbanRs.close(); } %>
											</select>
											
											</TD>
											
										</TR>
										
										
										<TR>
											<TD class="plainlabel">Trạng thái</TD>
											
											<TD class="plainlabel"><select name="trangthai" onChange="submitform();">
											<option value =""></option>
									     	 <% if(obj.getTrangthai().equals("1")) { %>
										         <option value="1" selected>Hoạt động</option>
				                                 <option value="0">Kết thúc</option>
				                                 <%} else if(obj.getTrangthai().equals("0")){ %>
					                                  <option value="0"  selected>Kết thúc</option>
					                                  <option value="1">Hoạt động</option>
				                                 <%} else {%>
				                                  <option value="1">Hoạt động</option>
				                                  <option value="0">Kết thúc</option>
				                                
											        <% } %>
											
										    </select></TD>
										</TR>
										<TR>
										    <TD class="plainlabel" colspan=4>
										    <a class="button2" href="javascript:clearform()">
	<img style="top: -4px;" src="../images/button.png" alt="" onClick="clearform();">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
		<a class="button2" href="javascript:xuatExcel()"> <img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a>
                                             </TD>
										</TR>
										
									</TABLE>
									</FIELDSET>
								</TD>	
							</TR>
						</TABLE>
					</TD>
				</TR>	
				
			    <TR>
					<TD align="left" >
						<FIELDSET>
							<LEGEND class="legendtitle">&nbsp;Nhân viên &nbsp;&nbsp;&nbsp;&nbsp;   
							<%if(quyen[Utility.THEM]!=0){ %>
							<a class="button3" onClick="newform();">
    	<img style="top: -4px;" src="../images/New.png"  >Tạo mới  </a>                            
							<%} %>
							
						</LEGEND>
								<TABLE class="" width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="98%">
										<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
											<TR class="tbheader">
											<TH width="15%">Họ tên</TH>
											<TH width="10%">Đăng nhập</TH>
											<TH width="15%">Phòng ban</TH>
											<TH width="10%">Quyền</TH>
											<TH width="10%">Điện thoại</TH>
											<TH width="10%">Trạng thái</TH>
											<TH width="10%">Phân loại</TH>
											<!-- <TH width="8%">Người tạo</TH>
											<TH width="8%">Ngày tạo</TH> -->
											<TH width="8%">Người sửa</TH>
											<TH width="8%">Ngày sửa</TH>
											<TH width="8%">Tác vụ</TH>
										</TR>
								     <%
								        String lightrow = "tblightrow";
	                                    String darkrow = "tbdarkrow";
	                                   int i =0; 
	                                   if(DSNV != null)
								     while(DSNV.next()) 
								     {
								    	 if (i % 2 != 0) {%>                     
                                         <TR align="center" align = "left" class=<%=lightrow%> >
                                     <%} else { %>
                                         <TR align="center" align = "left" class=<%= darkrow%> >
                                     <%}%>
								     		<TD width="20%" align = "left" ><%=DSNV.getString("Ten") %></TD>
											<TD width="5%"><%=DSNV.getString("dangnhap") %></TD>
											<TD width="10%"><%=DSNV.getString("tenphongban") %></TD>
											<TD width="10%"><%=DSNV.getString("quyenIds") == null ? "" : DSNV.getString("quyenIds")  %></TD>
											<TD width="10%"><%=DSNV.getString("dienthoai") %></TD>
										<% if(DSNV.getString("trangthai").equals("1")) {%>
										    <TD width="10%">Hoạt động</TD>
										<%} else { %>
										    <TD width="10%">Ngưng hoạt động</TD>
										<%}%>
										
										<% if(DSNV.getString("phanloai").equals("1")) { %>
										   	<TD width="10%">Chi nhánh / Đối tác</TD>
										<%} else { %>
										   	<TD width="10%">Trung tâm</TD>
										<%} %>
										
											<TD width="6%"><%=DSNV.getString("nguoisua1") %></TD>
											<TD width="6%"><%=DSNV.getString("ngaysua") %></TD>
											<TD align="center">
                                                <TABLE border = 0 cellpadding="0" cellspacing="0">
                                                    <TR><TD>
                                                    <%if(quyen[Utility.SUA]!=0){ %>
                                                     	<A href = "../../CapnhatnhanviennewSvl?userId=<%=userId%>&update=<%=DSNV.getString("pk_seq") %>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>
                                                   	<%} %>
                                                    </TD>
                                                    <TD>&nbsp;</TD>
                                                    <TD>
                                                     <%if(quyen[Utility.XOA]!=0){ %>
                                                        <A href="../../CapnhatnhanviennewSvl?userId=<%=userId%>&delete=<%=DSNV.getString("pk_seq") %>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa nhân viên này?')) return false;"></A>
                                                    <%} %>
                                                    </TD>
                                                    </TR></TABLE>
                                                </TD>
								            </TR>
							          <% i++;
							          } %>
							          
								<TR>
									<TD align="center" colspan="11" class="tbfooter">&nbsp;</TD>
								</TR>
								
							</TABLE>
							
							</TD>
						</TR>
						<tr class="tbfooter" > 
						 <TD align="center" valign="middle" colspan="13" class="tbfooter">
						 	 <% obj.setNextSplittings(); %>
												 <script type="text/javascript" src="../scripts/phanTrang.js"></script>
												<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
												<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >

											 	<%if(obj.getNxtApprSplitting() >1) {%>
													<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, 1, 'view')"> &nbsp;
												<%}else {%>
													<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
													<%} %>
												<% if(obj.getNxtApprSplitting() > 1){ %>
													<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) -1, 'view')"> &nbsp;
												<%}else{ %>
													<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
												<%} %>
												
												<%
													int[] listPage = obj.getNextSplittings();
													for( i = 0; i < listPage.length; i++){
												%>
												
												<% 
												
												System.out.println("Current page:" + obj.getNxtApprSplitting());
												System.out.println("List page:" + listPage[i]);
												
												if(listPage[i] == obj.getNxtApprSplitting()){ %>
												
													<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
												<%}else{ %>
													<a href="javascript:View(document.forms[0].name, <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
												<%} %>
													<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
												<%} %>
												
												<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) +1, 'view')"> &nbsp;
												<%}else{ %>
													&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
												<%} %>
												<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
											   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
										   		<%}else{ %>
										   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, -1, 'view')"> &nbsp;
										   		<%} %>
						</TD>
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
<%
try{
	if(DSNV!=null){
		DSNV.close();
	}
	obj.DbClose();
}catch(Exception er){
	
}


}%>