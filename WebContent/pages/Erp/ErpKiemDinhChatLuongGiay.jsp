<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import="geso.traphaco.erp.beans.kiemdinhchatluong.giay.IErpKiemdinhchatluongList"%>
<%@ page import = "geso.traphaco.center.util.*" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.text.DecimalFormat" %>
 
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%	
	IErpKiemdinhchatluongList obj =(IErpKiemdinhchatluongList)session.getAttribute("obj");
	ResultSet csxRs = obj.getKdclRs();
	ResultSet nhamayRs = obj.getNhaMayRs();
	 obj.setNextSplittings();
	
	NumberFormat formatter = new DecimalFormat("#,###,###"); 
	
	 int[] quyen = new  int[5];
	 quyen = util.Getquyen("ErpKiemdinhchatluongSvl","&loaiNPP=0&isKH=0",userId);


%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	
	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
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
	function clearform()
	{ 
		document.forms['khtt'].solsx.value= "";
	    document.forms['khtt'].solo.value= "";
	    document.forms['khtt'].sanpham.value= "";
	    document.forms['khtt'].trangthai.value = "";
	    document.forms['khtt'].sochungtu.value = "";
	    document.forms['khtt'].nguoitao.value = "";
	    document.forms['khtt'].ngaysanxuat.value = "";
	    document.forms['khtt'].nhamayid.value = "";
		document.forms['khtt'].submit();
	}
	function submitform()
	{
		document.forms['khtt'].action.value= 'search';
		document.forms['khtt'].submit();
	}

	function newform()
	{
		document.forms['khtt'].action.value= 'new';
		document.forms['khtt'].submit();
	}
	
	 function thongbao()
	 {
		 var tt = document.forms["khtt"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["khtt"].msg.value);
	 }
	</SCRIPT>
	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { $("select").select2(); });
     
 	</script>	
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="khtt" method="post" action="../../ErpKiemdinhchatluongSvl">
		<input type="hidden" name="userId" value="<%= userId %>"> <input
			type="hidden" name="action" value="1"> <input type="hidden"
			name="msg" value='<%= obj.getMsg() %>'>
		<script language="javascript" type="text/javascript">
    thongbao();
</script>
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" cellpadding="0" cellspacing="2">
						<TR>
							<TD align="left" class="tbnavigation">
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR height="22">
										<TD align="left" colspan="2" class="tbnavigation">Quản lý
											cung ứng > Sản xuất > Kiểm định chất lượng</TD>
										<TD colspan="2" align="right" class="tbnavigation">Chào
											mừng bạn <%= userTen %></TD>
									</tr>
								</TABLE></TD>
						</TR>
					</TABLE>
					<TABLE width="100%" cellpadding="0" cellspacing="1">
						<TR>
							<TD>
								<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
									<TR>
										<TD width="100%" align="center">
											<FIELDSET>
												<LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm
													&nbsp;</LEGEND>

												<TABLE width="100%" cellpadding="6" cellspacing="0">

													<TR>
														<TD width="15%" class="plainlabel">Số lô</TD>
														<TD width="25%" class="plainlabel"><input type="text"
															name="solo" value="<%= obj.getSolo()%>" size="20"
															onchange=submitform();></TD>
														<TD width="15%" class="plainlabel">Số lệnh sản xuất
														</TD>
														<TD class="plainlabel"><input type="text"
															name="solsx" value="<%= obj.getSoLSX()%>" size="20"
															onchange=submitform();></TD>
													</TR>
													<TR>
														<TD class="plainlabel">Sản phẩm</TD>
														<TD class="plainlabel"><input type="text"
															name=sanpham value="<%= obj.getSanpham() %>" size="20"
															onchange=submitform();></TD>
														<TD class="plainlabel">Số phiếu kiểm định</TD>
														<TD class="plainlabel"><input type="text"
															name=sochungtu value="<%= obj.getChungtu() %>" size="20"
															onchange=submitform();></TD>
													</TR>
													<TR>
														<TD class="plainlabel">Người tạo</TD>
														<TD class="plainlabel"><input type="text"
															name=nguoitao value="<%= obj.getNguoiTao() %>" size="20"
															onchange=submitform();></TD>
														<TD class="plainlabel">Ngày sản xuất</TD>
														<TD class="plainlabel"><input type="text"
															class="days" name=ngaysanxuat
															value="<%= obj.getNgaySanXuat() %>" size="20"
															onchange=submitform();></TD>
													</TR>


													<TR>
														<TD class="plainlabel">Trạng thái</TD>
														<TD class="plainlabel"><select name="trangthai"
															onChange="submitform();" style="width: 200px">
																<option selected="selected" value=""></option>
																<%  
									String[] mang=new String[]{ "0","1","2"};
									String[] mangten=new String[]{"Chờ kiểm định","Đã duyệt" ,"Đã hủy"};
									 for(int i=0;i<mang.length;i++){
										 if(obj.getTrangthai().equals(mang[i])){
										 %>
																<option selected="selected" value="<%=mang[i]%>">
																	<%=mangten[i] %>
																</option>
																<%
										 }else{
										 %>
																<option value="<%=mang[i]%>">
																	<%=mangten[i] %>
																</option>
																<% 
										 }
									 }
									%>
														</select></TD>
														<TD class="plainlabel">Nhà máy</TD>
														<TD class="plainlabel"><select name="nhamayid"
															onChange="submitform();" style="width: 200px">
																<option value="">Tất cả</option>
																<% try
								  	 {
								  		 if(nhamayRs!=null)
								  		 {
									  		 while(nhamayRs.next())
									  		 { 
										    	if(nhamayRs.getString("pk_seq").equals(obj.getNhaMayId())){ %>
																<option value='<%=nhamayRs.getString("pk_seq") %>'
																	selected><%=nhamayRs.getString("tennhamay") %></option>
																<%}else{ %>
																<option value='<%=nhamayRs.getString("pk_seq") %>'><%=nhamayRs.getString("tennhamay") %></option>
																<%}
								    		} 
								  	 		nhamayRs.close();
								  		 }
								  	 }catch(java.sql.SQLException e){} %>
														</select></TD>
													</TR>

													<tr class="plainlabel">
														<td colspan="4"><a class="button"
															href="javascript:submitform()"> <img
																style="top: -4px;" src="../images/Search30.png" alt="">Tìm
																kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a
															class="button2" href="javascript:clearform()"> <img
																style="top: -4px;" src="../images/button.png" alt="">Nhập
																lại</a>&nbsp;&nbsp;&nbsp;&nbsp;</td>
													</tr>
												</TABLE>

											</FIELDSET></TD>

									</TR>
								</TABLE></TD>
						</TR>

						<TR>
							<TD width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle">&nbsp;Yêu cầu kiểm định
										chất lượng &nbsp;&nbsp; </LEGEND>

									<TABLE class="" width="100%">
										<TR>
											<TD width="98%">
												<TABLE width="100%" border="0" cellspacing="1"
													cellpadding="4">
													<TR class="tbheader">
														<TH width="6%" align="center">Mã YC</TH>
														<TH width="8%" align="center">Số lệnh sản xuất</TH>
														<TH width="6%" align="center">Công đoạn</TH>
														<TH width="7%" align="center">Số nhập kho</TH>
														<TH width="20%" align="center">Sản phẩm</TH>
														<TH width="7%" align="center">Số lượng</TH>
														<TH width="5%" align="center">Số lô</TH>
														<TH width="8%" align="center">Trạng thái</TH>
														<TH width="9%" align="center">Ngày kiểm định</TH>
														<TH width="9%" align="center">Ngày sản xuất</TH>
														<TH width="10%" align="center">Người kiểm</TH>
														<TH width="9%" align="center">Tác vụ</TH>
													</TR>
													<% 
                                   
                                    int m = 0;
                                    String lightrow = "tblightrow";
                                    String darkrow = "tbdarkrow";
                                    if(csxRs != null)
                                    while ( csxRs.next()){
                                       
                                    	String tt = csxRs.getString("trangthai").trim();
                                    	
                                        if (m % 2 != 0) { %>
													<TR class=<%=lightrow%>>
														<%} else {%>
													
													<TR class=<%= darkrow%>>
														<%}%>
														<TD align="center"><%= csxRs.getString("pk_seq") %></TD>
														<TD align="center"><%= csxRs.getString("lenhsanxuatId") +(csxRs.getString("diengiai").length()>0?"("+csxRs.getString("diengiai")+")" :"" )  %>
														</TD>
														<TD align="center"><%= csxRs.getString("congdoanid") %>
														</TD>
														<TD align="center"><%= csxRs.getString("sonhapkho") %>
														</TD>
														<TD align="left"><%= csxRs.getString("spTen")%></TD>
														<TD align="center"><%= csxRs.getString("soluong") %>
														</TD>
														<TD align="center"><%= csxRs.getString("solo") %></TD>
														<% if( tt.equals("0") ) { %>
														<TD align="center">Chờ kiểm định</TD>
														<% } else  if(tt.equals("1")) { %>
														<TD align="center">Đã duyệt</TD>
														<% } else  if(tt.equals("2")) { %>
														<TD align="center">Hoàn tất</TD>
														<%} else   { %>
														<TD align="center"><span style="color: red;">Đã	hủy</span></TD>
														<% } %>
														
														<TD align="center"><%= csxRs.getString("NGAYKIEM")%>
														</TD>
														<TD align="center"><%= csxRs.getString("ngaysanxuat")%>
														</TD>
														<TD align="left"><%= csxRs.getString("NGUOISUA")%></TD>
														<TD align="center">
															<% if(    tt.equals("0")) { %>  
															 <%if(quyen[2]!=0){ %> <A
																	href="../../ErpKiemdinhchatluongUpdateSvl?userId=<%=userId%>&update=<%= csxRs.getString("pk_seq") %>"><img
																src="../images/Edit20.png" alt="Cap nhat" width="20"
																height="20" longdesc="Cap nhat" border=0>
																	</A> &nbsp; <%} %> <A href="../../ErpKiemdinhchatluongUpdateSvl?userId=<%=userId%>&display=<%= csxRs.getString("pk_seq") %>"><IMG
																			src="../images/Display20.png" alt="Hien thi"
																			title="Hien thi" border=0>
																			</A>&nbsp;
																 
														 <% } else 
																{ %> <A
															href="../../ErpKiemdinhchatluongUpdateSvl?userId=<%=userId%>&display=<%= csxRs.getString("pk_seq") %>"><IMG
																src="../images/Display20.png" alt="Hien thi"
																title="Hien thi" border=0>
														</A>&nbsp; <% }  %>
														</TD>
													</TR>
													<% m++; } %>



													<TR class="tbfooter">
														<TD align="center" valign="middle" colspan="12"
															class="tbfooter">
															<% obj.setNextSplittings(); %> <script
																type="text/javascript" src="../scripts/phanTrang.js"></script>
															<input type="hidden" name="crrApprSplitting"
															value="<%= obj.getCrrApprSplitting() %>"> <input
															type="hidden" name="nxtApprSplitting"
															value="<%= obj.getNxtApprSplitting() %>"> <%if(obj.getNxtApprSplitting() >1) {%>
															<img alt="Trang Dau" src="../images/first.gif"
															style="cursor: pointer;"
															onclick="View(document.forms[0].name, 1, 'view')">
															&nbsp; <%}else {%> <img alt="Trang Dau"
															src="../images/first.gif"> &nbsp; <%} %> <% if(obj.getNxtApprSplitting() > 1){ %>
															<img alt="Trang Truoc" src="../images/prev.gif"
															style="cursor: pointer;"
															onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) -1, 'view')">
															&nbsp; <%}else{ %> <img alt="Trang Truoc"
															src="../images/prev_d.gif"> &nbsp; <%} %> <%
												int[] listPage = obj.getNextSplittings();
												
												for(int i = 0; i < listPage.length; i++){
											%> <% 
											
											System.out.println("Current page:" + obj.getNxtApprSplitting());
											System.out.println("List page:" + listPage[i]);
											
											if(listPage[i] == obj.getNxtApprSplitting()){ %> <a
															style="color: white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
															<%}else{ %> <a
															href="javascript:View(document.forms[0].name, <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
															<%} %> <input type="hidden" name="list"
															value="<%= listPage[i] %>" /> &nbsp; <%} %> <% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
															&nbsp; <img alt="Trang Tiep" src="../images/next.gif"
															style="cursor: pointer;"
															onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) +1, 'view')">
															&nbsp; <%}else{ %> &nbsp; <img alt="Trang Tiep"
															src="../images/next_d.gif"> &nbsp; <%} %> <%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
															<img alt="Trang Cuoi" src="../images/last.gif">
															&nbsp; <%}else{ %> <img alt="Trang Cuoi"
															src="../images/last.gif" style="cursor: pointer;"
															onclick="View(document.forms[0].name, -1, 'view')">
															&nbsp; <%} %>
														</TD>
													</TR>
												</TABLE></TD>
										</TR>
									</TABLE>
								</FIELDSET></TD>
						</TR>

					</TABLE></TD>
			</TR>
		</TABLE>
	</form>
</BODY>
</html>
<% 	
	try
    {
		if(csxRs != null)
			csxRs.close();
		
		if(obj != null)
		{
			obj.DbClose();
			obj = null;
		}
		session.setAttribute("obj", null);
	}
	catch (SQLException e) {} %>
<%}%>