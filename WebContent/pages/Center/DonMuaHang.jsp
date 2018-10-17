<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.donmuahang.IDonmuahangList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	System.out.println(userId);
	System.out.println(userTen);
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/SalesUp/index.jsp");
	}else{ %>

<% IDonmuahangList obj = (IDonmuahangList)session.getAttribute("obj"); %>
<% ResultSet kvlist = (ResultSet)obj.getKvList(); %>
<% ResultSet dhlist = (ResultSet)obj.getDhList(); %>
<% obj.setNextSplittings(); 
int[] quyen = new  int[5];
quyen = util.Getquyen("36",userId);
ResultSet npp=(ResultSet)obj.getNppRs();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>


<link rel="stylesheet" type="text/css" href="../css/autocomplete.css" />
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

<script type="text/javascript" src="../scripts/jquery-1.4.2.min.js"></script>
<script src="../scripts/jquery.autocomplete.js"></script>

<script type="text/javascript" src="../scripts/jquery.js"></script>
<script type="text/javascript" src="../scripts/phanTrang.js"></script>

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
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
$(document).ready(function()
{
	$("#nppId").select2();
});
</script>

<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">


<SCRIPT language="javascript" type="text/javascript">

function submitForm(arg){
	
	document.forms["ddhForm"].action.value = arg;
	document.forms["ddhForm"].submit();
	
}
function submitform()
{   
   document.forms["ddhForm"].submit();
}

function clearform()
{
    document.forms["ddhForm"].sku.value = "";
    document.forms["ddhForm"].kvId.value = "";
    document.forms["ddhForm"].tungay.value = "";
    document.forms["ddhForm"].denngay.value = "";
    document.forms["ddhForm"].trangthai.value = "";
    document.forms["ddhForm"].so.value = "";
    document.forms["ddhForm"].nppId.value = "";
    document.forms["ddhForm"].submit();
}

function  newform(){
	document.forms["ddhForm"].action.value ="new";
	  document.forms["ddhForm"].submit();
}
function processing(id,chuoi)
{
	 document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif' width = '20' height = '20' title='cho thuc hien..' border='0' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>"; 		  
	 document.getElementById(id).href=chuoi;
}

function thongbao()
{
	 var tt = document.forms["ddhForm"].msg.value;
	 if(tt.length>0)
    	alert(document.forms["ddhForm"].msg.value);
 }
 
function xuatExcel()
{
	document.forms['ddhForm'].action.value= 'toExcel';
	document.forms['ddhForm'].submit();
}

</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form id="ddhForm" name="ddhForm" method="post"
		action="../../DonmuahangSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> 
		<input type="hidden" name="userTen" value='<%=userTen%>'> 
		<input type="hidden" name="action" value="1"> <input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>">
		<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>">
	<input type="hidden" name="msg" value='<%=obj.getMsg() %>'>
	<script language="javascript" type="text/javascript">
    	thongbao();
	</script> 
	<%obj.SetMsg(""); %>
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%" bgcolor="#FFFFFF">
			<TR>
				<TD colspan="4" align='left' valign='top'>
					<!--begin body Dossier--> <!--begin common dossier info---> <!--End common dossier info--->


					<TABLE width="100%" cellspacing="0" cellpadding="0">
						<TR>
							<TD>
								<TABLE width="100%" cellspacing="1" cellpadding="0">
									<TR>
										<TD align="left" class="tbnavigation">
											<table width="100%" border="0" cellpadding="0"
												cellspacing="0">
												<tr height="22">
													<TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý bán hàng
														&gt; &nbsp;Tạo đơn hàng</TD>

													<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=obj.getTen() %> &nbsp;</TD>
												</tr>
											</table></TD>
									</TR>
								</TABLE>

								<TABLE border="0" width="100%" cellspacing=0 cellpadding=0>

									<TR>
										<TD width="100%" align="left">
											<FIELDSET>
												<LEGEND class="legendtitle">&nbsp;Tiêu chí hiển thị
													&nbsp;</LEGEND>

												<TABLE width="100%" cellpadding="4" cellspacing="0">
													<TR>
														<TD width="19%" class="plainlabel">Chứa SKU</TD>
														<TD width="81%" class="plainlabel"><input type="text"
															id="sku" name="sku" value="<%= obj.getSKU() %>" size=40 />

														</TD>
													</TR>
													<TR>
														<TD class="plainlabel">Từ ngày</TD>
														<TD class="plainlabel">
															<TABLE cellpadding="0" cellspacing="0">
																<TR>
																	<TD><input class="days" type="text" name="tungay"
																		value="<%=obj.getTungay() %>" size="20"></TD>

																</TR>
															</TABLE></TD>
													</TR>
													<TR>
														<TD class="plainlabel">Đến ngày</TD>
														<TD class="plainlabel">
															<TABLE cellpadding="0" cellspacing="0">
																<TR>
																	<TD><input class="days" type="text" name="denngay"
																		value="<%=obj.getDenngay() %>" size="20"></TD>

																</TR>
															</TABLE></TD>

													</TR>
													<TR>
														<TD class="plainlabel">Trạng thái</TD>
														<TD class="plainlabel"><select id="trangthai" name="trangthai" onChange="submitform();">
																<%if (obj.getTrangthai().equals("1")){ %>
																<option value=""></option>
																<option value="1" selected>Chờ xử lý</option>
																<option value="2" >TT duyệt đơn hàng</option>
																<option value="3">ERP duyệt đơn hàng</option>
																<option value="4">Ðã xuất HĐ </option>
																<option value="6">Đã hủy đơn ĐH</option>
																<%}else{ 							
								  						if (obj.getTrangthai().equals("2")){ %>
																<option value=""></option>
																<option value="1">Chờ xử lý</option>
																<option value="2" selected>TT duyệt đơn hàng</option>
																<option value="3">ERP duyệt đơn hàng</option>
																<option value="4">Ðã xuất HĐ </option>
																<option value="6">Đã hủy đơn ĐH</option>
																<%}else{ 
														if (obj.getTrangthai().equals("3")){ %>
																<option value=""></option>
																<option value="1">Chờ xử lý</option>
																<option value="2" >TT duyệt đơn hàng</option>
																<option value="3" selected>ERP duyệt đơn hàng</option>
																<option value="4">Ðã xuất HĐ </option>
																<option value="6">Đã hủy đơn ĐH</option>
													<%} else{ 																	   	 
														if (obj.getTrangthai().equals("4")){ %>
																<option value=""></option>
																<option value="1">Chờ xử lý</option>
																<option value="2" >TT duyệt đơn hàng</option>
																<option value="3">ERP duyệt đơn hàng</option>
																<option value="4" selected>Ðã xuất HĐ </option>
																<option value="6">Đã hủy đơn ĐH</option>

																<%	} else { if(obj.getTrangthai().equals("5")) { %>
																	<option value=""></option>
																	<option value="1">Chờ xử lý</option>
																	<option value="2" >TT duyệt đơn hàng</option>
																	<option value="3">ERP duyệt đơn hàng</option>
																	<option value="4">Ðã xuất HĐ </option>
																	<option value="6" selected>Đã hủy đơn ĐH</option>
																	
																 <%} else { if(obj.getTrangthai().equals("6")){ %> 
																 	<option value=""></option>
																	<option value="1">Chờ xử lý</option>
																	<option value="2" selected>TT duyệt đơn hàng</option>
																	<option value="3">ERP duyệt đơn hàng</option>
																	<option value="4">Ðã xuất HĐ </option>
																	<option value="6" selected>Đã hủy đơn ĐH</option>
																	
																  <%} else { %>
																	<option value=""></option>
																	<option value="1">Chờ xử lý</option>
																	<option value="2" >TT duyệt đơn hàng</option>
																	<option value="3">ERP duyệt đơn hàng</option>
																	<option value="4">Ðã xuất HĐ </option>
																	<option value="6">Đã hủy đơn ĐH</option>
																<%  } } } } } } %>
														</select>
														</TD>
													</TR>
													<TR>
														<TD class="plainlabel">Khu vực</TD>
														<TD width="81%" class="plainlabel"><SELECT
															name="kvId" onChange="submitform();">
																<option value=""></option>
																<%  try{
								  		while(kvlist.next()){								  			
								  			if (obj.getKvId().equals(kvlist.getString("kvId"))){ %>
																<option value="<%= kvlist.getString("kvId")%>" selected><%= kvlist.getString("kv")%></option>
																<%}else{ %>
																<option value="<%= kvlist.getString("kvId")%>"><%= kvlist.getString("kv")%></option>
																<%		}
								  		}	
								  	}catch (java.sql.SQLException e){} %>
														</SELECT>
														</TD>
													</TR>
														<TR>
														<TD width="19%" class="plainlabel">Số SO</TD>
														<TD width="81%" class="plainlabel"><input type="text"
															id="so" name="so" value="<%=obj.getSO()%>" size=40 onchange="submitform();" />

														</TD>
													</TR>
													
													<TR>													
												<td width="9%" class="plainlabel">Chi nhánh / Đối tác</td>
									<td width="33%" class="plainlabel">
									<SELECT name="nppId" id="nppId" style="width:350px" >
									<option value="">All </option>
									<% if(npp != null){
										  try
										  { 
											  String optionGroup = "";
											  String optionName = "";
											  int i = 0;
											  
											  while(npp.next())
											  { 
												 if(i == 0)
												 {
													 optionGroup = npp.getString("kvTen");
													 optionName = npp.getString("kvTen");
													 
													 %>
													 
													 <optgroup label="<%= optionName %>" >
												 <% }
												 
												 optionGroup = npp.getString("kvTen");
												 if(optionGroup.trim().equals(optionName.trim()))
												 { %>
													 <% if(npp.getString("nppId").equals(obj.getNppId())) {%>
													 	<option value="<%= npp.getString("nppId") %>" selected="selected" ><%= npp.getString("nppTen") %></option>
													 <%} else { %>
													 	<option value="<%= npp.getString("nppId") %>"><%= npp.getString("nppTen") %></option>
													 <%} %>
												 <% }
												 else
												 {
													 %>
													</optgroup>
													<% optionName = optionGroup; %>
													<optgroup label="<%= optionName %>" >
													<% if(npp.getString("nppId").equals(obj.getNppId())) {%>
													 	<option value="<%= npp.getString("nppId") %>" selected="selected" ><%= npp.getString("nppTen") %></option>
													 <%} else { %>
													 	<option value="<%= npp.getString("nppId") %>"><%= npp.getString("nppTen") %></option>
													 <%} %>
												 <% }
												 i++;
								     	 	  } 
											  %>
											  	</optgroup>
											  <%npp.close(); 
								     	 }catch(java.sql.SQLException e){}}%>	  
                                	</SELECT>
								</td>
													</TR>
													<TR>

														<TD class="plainlabel" align="left" colspan=2>
														<a  class="button1" href="javascript:submitForm('Hien thi')"> <img style="top: -4px;" src="../images/Search30.png"alt="">Tìm kiếm </a>
															&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a class="button2"
															href="javascript:clearform()"> <img
																style="top: -4px;" src="../images/button.png" alt="">Nhập
																lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
																 

        &nbsp;&nbsp;&nbsp;&nbsp;
							<a class="button2" href="javascript:xuatExcel()"> <img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a>&nbsp;&nbsp;&nbsp;&nbsp;

														</TD>

													</TR>
												</TABLE>
											</FIELDSET></TD>
									</TR>

								</TABLE></TD>
						</TR>
					</TABLE>
					<TABLE width="100%" cellspacing=0 cellpadding=0>
						<TR>
							<TD>
								<FIELDSET>
									<LEGEND class="legendtitle">
										&nbsp;&nbsp;&nbsp;Đơn đặt hàng&nbsp;&nbsp;&nbsp;&nbsp; <a
											class="button3" href="javascript:newform()"> <img
											style="top: -4px;" src="../images/New.png" alt="">Tạo
											mới </a>
									</LEGEND>

									<TABLE width="100%">
										<TR>
											<TD>
												<TABLE width="100%" border="0" cellspacing="1"
													cellpadding="4">
													<TR class="tbheader">
														<TH width="9%">Ngày đặt hàng</TH>
														<TH width="9%">Số PO</TH>
														<TH width="9%">Số SO</TH>
														<TH width="17%">Chi nhánh / Đối tác</TH>
														<TH width="10%">Người tạo</TH>
														<TH width="9%">T.tiền Đ.hàng</TH>
														<TH width="10%">Trạng thái</TH>
														<TH width="9%">Ngày hóa đơn</TH>
														<TH width="9%">Ngày nhận hàng</TH>
														<TH width="9%">T.tiền h.đơn</TH>
														<TH width="8%" align="center">&nbsp;Tác vụ</TH>
													</TR>

													<% 
                            NumberFormat formatter = new DecimalFormat("#,###,###");
                            int m = 0;
                            String lightrow = "tblightrow";
                            String darkrow = "tbdarkrow";
							if(dhlist != null){
								try{								
                                    while (dhlist.next()){
                                        	
                                      	String chuoi="";
                                    	String ngaydat=dhlist.getString("ngaydat");
										  if( dhlist.getString("loaidonhang").equals("1")){
											  
											  chuoi="color: red;";
											  ngaydat=dhlist.getString("ngaydat")+"("+dhlist.getString("nam")+"-"+dhlist.getString("thang")+")";
										  }
										  
										  
                                       	if (m % 2 != 0) {
                                       	
                                     
											  
											  %>
											  
													<TR style="<%=chuoi %>"  class=<%=lightrow%> >
														<%} else {%>
													
													<TR style="<%=chuoi %>"  class=<%= darkrow%>>
														<%}%>
														<TD align="left"><div align="left"><%= ngaydat%></div>
														</TD>
														<TD align="left"><div align="left"><%= dhlist.getString("soid")%></div>
														</TD>
														
														<TD>
														<%
                                                  		 if (dhlist.getString("trangthai").equals("7")){ %>
															<span style="color: red;">
																<%=  dhlist.getString("chungtu") %>
															</span>
														<%} else{ %>
															<span >
																<%=  dhlist.getString("chungtu") %>
															</span>
														<%} %>
														</TD>
														<TD><div align="left"><%= dhlist.getString("nppTen") %></div>
														</TD>
														<TD><div align="center"><%= dhlist.getString("nguoitao") %></div>
														</TD>
														<TD><% String thd="0";
														   if(dhlist.getString("sotienavat").length()!=0)
															{
																thd=dhlist.getString("sotienavat");

															}
														
														%><div align="right"><%=formatter.format(Double.parseDouble(thd)) %></div>
														</TD>

														<%
                                                  		 if (dhlist.getString("trangthai").equals("1")){ %>
														<TD><div align="center">Chờ xử lý</div>
														</TD>
														<%}else  if (dhlist.getString("trangthai").equals("2")) {%>
														<TD><div align="center"> TT duyệt đơn hàng</div>
														</TD>
														
														<% } else if  (dhlist.getString("trangthai").equals("3")){ %> 
															<TD><div align="center">ERP duyệt đơn hàng</div></TD>	
														<% } else if (dhlist.getString("trangthai").equals("4")) { %>
														<TD><div align="center">Ðã xuất HĐ</div></TD>													
														<% } else if (dhlist.getString("trangthai").equals("6")) { %>
															<TD><div align="center">Đã hủy </div></TD>		
														<% } else if (dhlist.getString("trangthai").equals("7")) { %>
															<TD><div align="center">NPP Hủy HĐ </div></TD>
														 <%}else { %>
														<TD><div align="center">Không xác định </div></TD>
														<%} %>
														<% if (dhlist.getString("ngayhd").equals("0")){ %>
														<TD align="center">&nbsp;</TD>
														<%}else{ %>
														<TD align="center"><%=dhlist.getString("ngayhd") %></TD>
														<%} %>
														<TD align="center"><%=dhlist.getString("ngaynhan") %></TD>
														<% if (dhlist.getString("sotienavat").equals("0")){ %>
														<TD align="center">&nbsp;</TD>
														<%}else
														{ %>
														<% String thd2="0";
														   if(dhlist.getString("tienhd").length()!=0)
															{
																thd2 =dhlist.getString("tienhd");

															}
														
														%>
														<TD align="right"><%=formatter.format(Double.parseDouble(thd2)) %></TD>
														

														<%} %>

														<TD align="center">
															<TABLE border=0 cellpadding="1" cellspacing="0">
																<TR>
																	<% 
                                	           						  int t	= Integer.valueOf(dhlist.getString("trangthai")).intValue();%>
																	<TD width=25>
																	<%if(quyen[3]!=0){ %>
																	<A
																	   href="../../ERP_DonDatHangUpdateSvl?userId=<%=userId%>&display=<%=dhlist.getString("chungtu") %>"><img
																	   src="../images/Display20.png" alt="Hiển thị"
																	   width="20" height="20" title="Hiển thị" border="0"   >
																	</A>
																	<%} %>
																	</TD>
																	
																	<% if (t == 1){ %>
																	
																	<TD width=25>
																	
																	<% 
																	 if( dhlist.getString("loaidonhang").equals("0")){
																	if(quyen[2]!=0) {%>
																	<A id='suaphieu<%=dhlist.getString("chungtu")%>'
																	   href=""><img
																	   src="../images/Edit.png" alt="Sua Don Hang"
																	   width="20" height="20" title="Sửa Đơn Hàng"
																	   border="0" onclick="processing('<%="suaphieu"+dhlist.getString("chungtu")%>' , '../../ERP_DonDatHangUpdateSvl?userId=<%=userId%>&edit=<%=dhlist.getString("chungtu") %>')" >
																	</A>
																	<%} }%>
																	</TD>
																	
																	<TD width=25>
																	<% 
																	if(quyen[4]!=0)
																	{
																		if(dhlist.getDouble("cktm")>0 &&  dhlist.getString("isduyetcktm").equals("1")  )
																		{
																	%>
																		<A id='chotphieu<%=dhlist.getString("chungtu")%>' href=""><img src="../images/Chot.png" alt="Chốt đơn hàng" width="20" height="20" title="Chốt đơn hàng" border="0" onclick="if(!confirm('Bạn có muốn chốt đơn hàng này?')) {return false ;}else{ processing('<%="chotphieu"+dhlist.getString("chungtu")%>' , '../../DonmuahangSvl?userId=<%=userId%>&chot=<%=dhlist.getString("chungtu") %>');}"  ></A>
																	<%}  else if(dhlist.getDouble("cktm") <=0 ){  %>
																		<A id='chotphieu<%=dhlist.getString("chungtu")%>' href=""><img src="../images/Chot.png" alt="Chốt đơn hàng" width="20" height="20" title="Chốt đơn hàng" border="0" onclick="if(!confirm('Bạn có muốn chốt đơn hàng này?')) {return false ;}else{ processing('<%="chotphieu"+dhlist.getString("chungtu")%>' , '../../DonmuahangSvl?userId=<%=userId%>&chot=<%=dhlist.getString("chungtu") %>');}"  ></A>																		
																	<%}
																	}
																	%>
																	</TD>
																	<TD width=25>
																	<%if(quyen[1]!=0){ %>
																	<A id='deletephieu<%=dhlist.getString("chungtu")%>'
																	   href=""><img
																	   src="../images/Delete.png" alt="Hủy đơn hàng"
																	   width="20" height="20" title="Hủy đơn hàng" 
																	   border="0" onclick="if(!confirm('Bạn có muốn hủy đơn hàng này?')) {return false ;}else{ processing('<%="deletephieu"+dhlist.getString("chungtu")%>' , '../../DonmuahangSvl?userId=<%=userId%>&delete=<%=dhlist.getString("chungtu") %>');}"  >
																	</A>
																	<%} %>
																	</TD>
																	<% }
                         	                   	 					%>
                         	                   	 					<% if (t == 2 && dhlist.getString("kbh_fk").equals("100025") ){
																	 if( dhlist.getString("loaidonhang").equals("0")){
																			if(quyen[4]!=0){ %>
																			<TD width=25>
																			<A id='huychotphieu<%=dhlist.getString("chungtu")%>'
																			   href=""><img
																			   src="../images/unChot.png" alt="Hủy Chốt đơn hàng"
																			   width="20" height="20" title="Hủy Chốt đơn hàng" 
																			   border="0" onclick="if(!confirm('Bạn có muốn hủy chốt đơn hàng này?')) {return false ;}else{ processing('<%="huychotphieu"+dhlist.getString("chungtu")%>' , '../../DonmuahangSvl?userId=<%=userId%>&huychot=<%=dhlist.getString("chungtu") %>');}"  >
																			</A>
																			</TD>		
																			<%} %>
																	<% }}%>
																	
																	<TD>
																		<A href="../../DonMuaHangExcel?userId=<%=userId%>&excel=<%=dhlist.getString("chungtu") %>">
																		<img src="../images/excel.gif" alt="In đơn hàng chi tiết Excel" width="20" height="20" longdesc="In đơn hàng chi tiết Excel" border=0> </A>
																	</TD>
																	
																</TR>
															</TABLE></TD>
													</TR>
													<% m++; } 
                                    
                                        }catch(Exception  e){
                                        	e.printStackTrace();
                                        }
                               		}%>

													<tr class="tbfooter">
														<TD align="center" valign="middle" colspan="13"
															class="tbfooter">
															<%if(obj.getNxtApprSplitting() >1) {%> <img alt="Trang Dau"
															src="../images/first.gif" style="cursor: pointer;"
															onclick="View('ddhForm', 1, 'view')"> &nbsp; <%}else {%>
															<img alt="Trang Dau" src="../images/first.gif">
															&nbsp; <%} %> <% if(obj.getNxtApprSplitting() > 1){ %> <img
															alt="Trang Truoc" src="../images/prev.gif"
															style="cursor: pointer;"
															onclick="View('ddhForm', eval(ddhForm.nxtApprSplitting.value) -1, 'view')">
															&nbsp; <%}else{ %> <img alt="Trang Truoc"
															src="../images/prev_d.gif"> &nbsp; <%} %> <%
													int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%> <% 
												
												if(listPage[i] == obj.getNxtApprSplitting()){ %> <a
															style="color: white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
															<%}else{ %> <a
															href="javascript:View('ddhForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
															<%} %> <input type="hidden" name="list"
															value="<%= listPage[i] %>" /> &nbsp; <%} %> <% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
															&nbsp; <img alt="Trang Tiep" src="../images/next.gif"
															style="cursor: pointer;"
															onclick="View('ddhForm', eval(ddhForm.nxtApprSplitting.value) +1, 'view')">
															&nbsp; <%}else{ %> &nbsp; <img alt="Trang Tiep"
															src="../images/next_d.gif"> &nbsp; <%} %> <%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
															<img alt="Trang Cuoi" src="../images/last.gif">
															&nbsp; <%}else{ %> <img alt="Trang Cuoi"
															src="../images/last.gif" style="cursor: pointer;"
															onclick="View('ddhForm', -1, 'view')"> &nbsp; <%} %>
														</TD>
													</tr>


												</TABLE></TD>
										</TR>
									</TABLE>
								</FIELDSET></TD>
						</TR>
					</TABLE></TD>
			</TR>
		</TABLE>
	</form>
	<p id="result"></p>

</BODY>
</HTML>

<% if (!( dhlist == null)) dhlist.close(); %>
<% if (!( kvlist == null)) kvlist.close(); %>
<% obj.DBclose(); obj=null; %>
<%}%>