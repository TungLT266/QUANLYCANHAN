<%@page import="geso.traphaco.erp.beans.marquette.IMarquette"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import="geso.traphaco.center.beans.banggia.IBangGiaList"%>
<%@ page import = "geso.traphaco.center.util.*" %>

<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if (!util.check(userId, userTen, sum)) {
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	} else {
%>
<%
	IMarquette obj = (IMarquette) session.getAttribute("obj");
		ResultSet mklist =obj.getThongtinmarketList();
		ResultSet dvkd = obj.getDvkd();
		ResultSet nh = obj.getNhanhangIdRs();
		ResultSet cl = obj.getChungloaiIdRs();
		ResultSet lspRs = obj.getLoaiSpRs();
		int[] quyen = new int[6];
		quyen = util.Getquyen("MarquetteSvl", "", userId);
%>
<% obj.setNextSplittings(); %>
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
	
	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
	
	<script type="text/javascript">
		var msg = "<%=obj.getMsg()%>".trim();
		$(document).ready(function() {		
			$( ".days" ).datepicker({			    
					changeMonth: true,
					changeYear: true				
			});
			
			if(msg.length > 0 && msg !== "null") {
				alert(msg);
			}
        });	
			
	</script>
		<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	
    <script type="text/javascript">
    
        $(document).ready(function(){
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button2").hover(function(){
                $(".button2 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button3").hover(function(){
                $(".button3 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
    </script>
	
	<SCRIPT language="javascript" type="text/javascript">
	function clearform()
	{ 

	    document.maketlist.mamk.value = "";
	    document.maketlist.masp.value = "";
	    document.maketlist.tensp.value = "";
	    document.maketlist.dvkdId.selectedIndex = 0;
	    document.maketlist.nhId.selectedIndex = 0;
	    document.maketlist.lspId.selectedIndex = 0;
	    document.maketlist.trangthai.selectedIndex = 0;
	    document.maketlist.clId.selectedIndex = 0;
	    submitform();
	}

	function submitform()
	{
		document.forms['maketlist'].action.value= 'search';
		document.forms['maketlist'].submit();
	}

	function newform()
	{
		document.forms['maketlist'].action.value= 'new';
		document.forms['maketlist'].submit();
	}
	</SCRIPT>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="maketlist" method="post" action="../../MarquetteSvl">
<input type="hidden" name="userId" value="<%=userId%>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >


<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%"> 
    <TR>
        <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
        <TABLE width="100%" cellpadding="0" cellspacing="2">
            <TR>
                <TD align="left" class="tbnavigation">
                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                        <TR height="22">
                            <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Sản phẩm > Marquette sản phẩm  </TD>  
                            <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen%></TD>
                        </tr>
                    </TABLE>
                </TD>
            </TR>
        </TABLE>
        <TABLE width="100%" cellpadding="0" cellspacing="1">
            <TR>
                <TD>
                    <TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
                        <TR>
                            <TD width="100%" align="center" >
                            <FIELDSET>
                              <LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm &nbsp;</LEGEND>

                            <TABLE  width="100%" cellpadding="6" cellspacing="0">
                            	 <TR>
									<TD class="plainlabel">Maket</TD>
									<TD class="plainlabel"><INPUT name="mamk" id="mamk" type="text" size="40" value='<%=obj.getMarket()%>' onChange = "submitform();"/></TD>
									<TD class="plainlabel">Đơn vị kinh doanh</TD>
									<TD class="plainlabel"><SELECT name="dvkdId" onChange="submitform();">
										<option value="">Tất cả</option>
										<%
											try {
													while (dvkd.next()) {
														if (obj.getDvkdId().equals(dvkd.getString("dvkdId"))) {
										%>
										<option value="<%=dvkd.getString("dvkdId")%>" selected><%=dvkd.getString("dvkd")%></option>
											<%
												} else {
											%>
										<option value="<%=dvkd.getString("dvkdId")%>"><%=dvkd.getString("dvkd")%></option>
										<%
											}
													}
												} catch (java.sql.SQLException e) {
												}
										%>
									</SELECT></TD>
									
								</TR>
                            	 <TR>
									<TD class="plainlabel">Mã sản phẩm</TD>
									<TD class="plainlabel"><INPUT name="masp" id="masp" type="text" size="40" value='<%=obj.getMasp()%>' onChange = "submitform();"/></TD>
									<TD class="plainlabel">Tên sản phẩm </TD>
								  	<TD class="plainlabel"><INPUT name="tensp" id="tensp" type="text" size="40" value='<%=obj.getTensp()%>' onChange = "submitform();"/> </TD>
								</TR>
								<TR>
								   <TD class="plainlabel">Loại sản phẩm</TD>
														<TD class="plainlabel"><SELECT name="lspId" onChange="submitform();">
																<option value="">Tất cả</option>
																<%
																 
																	try {
																			while (lspRs.next()) {
																				if (obj.getLoaispId().equals(lspRs.getString("pk_seq"))) {
																%>
																<option value="<%=lspRs.getString("pk_seq")%>" selected><%=lspRs.getString("ten")%></option>
																<%
																	} else {
																%>
																<option value="<%=lspRs.getString("pk_seq")%>"><%=lspRs.getString("ten")%></option>
																<%
																	}
																			}
																		} catch (java.sql.SQLException e) {
																		}
																%>
														</SELECT></TD>
									<TD class="plainlabel">Nhãn hàng</TD>
									<TD class="plainlabel"><SELECT name="nhId" id="nhId" onChange="submitform();">
										<option value="">Tất cả</option>
											<%
												try {
											while (nh.next()) {
											if (obj.getNhanhangId().equals(nh.getString("pk_seq"))) {
												%>
											<option value="<%=nh.getString("pk_seq")%>" selected><%=nh.getString("ten")%></option>
											<%
											} else {
											%>
											<option value="<%=nh.getString("pk_seq")%>"><%=nh.getString("ten")%></option>
											<%
											}
											}
											} catch (java.sql.SQLException e) {
												}
											%>
											</SELECT></TD>
									
								</TR>
								<TR>
								    <TD class="plainlabel">Chủng loại </TD>
														<TD class="plainlabel"><SELECT name="clId" id="clId" onChange="submitform();">
																<option value="">Tất cả</option>
																<%
																	try {
																			while (cl.next()) {
																				if (obj.getChungloaiId().equals(cl.getString("pk_seq"))) {
																%>
																<option value="<%=cl.getString("pk_seq")%>" selected><%=cl.getString("ten")%></option>
																<%
																	} else {
																%>
																<option value="<%=cl.getString("pk_seq")%>"><%=cl.getString("ten")%></option>
																<%
																	}
																			}
																		} catch (java.sql.SQLException e) {
																		}
																%>
														</SELECT>
														</TD>

														<TD class="plainlabel">Trạng Thái </TD>
								    <TD class="plainlabel"><select name="trangthai" id="trangthai" onChange="submitform();">
									<%
										if (obj.getTrangthai().equals("0")) {
									%>
										<option value="2">Tất cả</option>
										<option value="1">Hoạt động</option>
										<option value="0" selected>Ngưng hoạt động</option>
									<%
										} else if (obj.getTrangthai().equals("1")) {
									%>
										<option value="2" >Tất cả</option>
										<option value="1" selected>Hoạt động</option>
										<option value="0">Ngưng hoạt động</option>
									<%
										} else if (obj.getTrangthai().equals("2")) {
									%>
										<option value="2" selected>Tất cả</option>
										<option value="1">Hoạt động</option>
										<option value="0">Ngưng hoạt động</option>
									<%
										} else {
									%>
									<option value="2">Tất cả</option>
									<option value="1">Hoạt động</option>
									<option value="0">Ngưng hoạt động</option>
									<%
										}
									%>
									</select></TD>
								</TR>
								<TR>
								  
                                  <td colspan="4" class="plainlabel" > 
                           			<a class="button2" href="javascript:clearform()">
										<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
                             	</td> 
							  </TR>
							
                            </TABLE>

                            </FIELDSET>
                            </TD>

                        </TR>
                    </TABLE>
                    </TD>
                </TR>

                <TR>
                    <TD width="100%">
                        <FIELDSET>
                            <LEGEND class="legendtitle">&nbsp;Marquette&nbsp;&nbsp;	
                            	<%
	                            		if (quyen[Utility.THEM] != 0) {
	                            	%>
                            	<a class="button3" href="javascript:newform()">
                           		<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>      
                            	<%
                                  		}
                                  	%>      
                            </LEGEND>
    
                            <TABLE class="" width="100%">
                        <TR>
                            <TD width="98%">
                            <TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
                                <TR class="tbheader">
									<TH align="center" width="5%">STT</TH>
									<TH align="center" width="20%">Mã Maket </TH>
									<TH align="center"  width="45%">Diễn giải</TH>
									<TH align="center" width="10%">Trạng thái  </TH>
									<TH align="center" width="10%">Đến ngày</TH>
									<TH width="10%" align="center">&nbsp;Tác vụ</TH>
								</TR>
                                
                            <%
                                                            	int m = 0;
                                                            		String lightrow = "tblightrow";
                                                            		String darkrow = "tbdarkrow";
                                                            		try {
                                                            			while (mklist.next()) {

                                                            				if (m % 2 != 0) {
                                                            %>						
									<TR class= <%=lightrow%> >
							  <%
							  	} else {
							  %>
									<TR class= <%=darkrow%> >
							  <%
							  	}
							  %>
							  			<TD align="center"><%=m + 1%></TD>
										<TD align="left"><%=mklist.getString("ma")%></TD>
										<TD align="left"><%=mklist.getString("diengiai")%></TD>
											
									<%
										if (mklist.getString("trangthai").equals("1")) {
									%>
										<TD align="left">Hoạt động</TD>							
									<%
																	} else {
																%>
										<TD align="left">Ngưng hoạt động</TD>
									<%
										}
									%>

										<TD align="left"><%=mklist.getString("denngay")%></TD>
										<TD align="center">
											<TABLE border = 0 cellpadding="0" cellspacing="0">
																						
											<TR>											
											<TD>
												<%
													if (quyen[Utility.SUA] != 0) {
												%>
													<A href = "../../MarquetteUpdateSvl?userId=<%=userId%>&edit=<%=mklist.getString("pk_seq")%>"><img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0></A>
												<%
													}
																if (quyen[Utility.XOA] != 0) {
												%>
													<A href = "../../MarquetteSvl?userId=<%=userId%>&delete=<%=mklist.getString("pk_seq")%>"><img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa bảng giá này ?')) return false;"></A>  
												<%
  													}
  												%>
											</TD>
											</TR>
											</TABLE>
										</TD>	
									
									</TR>				
								<%
													m++;
															}

														} catch (Exception e) {
															System.out.println("Exception: " + e.getMessage());
														}
												%>
                                
                               <tr class="tbfooter" > 
						 <TD align="center" valign="middle" colspan="6" class="tbfooter">
						 	<%if(obj.getNxtApprSplitting() >1) {%>
								<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('maketlist', 1, 'view')"> &nbsp;
							<%}else {%>
								<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
								<%} %>
							<% if(obj.getNxtApprSplitting() > 1){ %>
								<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="Prev('maketlist', 'prev')"> &nbsp;
							<%}else{ %>
								<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
							<%} %>
							
							<%
								int[] listPage = obj.getNextSplittings();
								for(int i = 0; i < listPage.length; i++){
							%>
							
							<% 							
						
							if(listPage[i] == obj.getNxtApprSplitting()){ %>
							
								<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
							<%}else{ %>
								<a href="javascript:View('maketlist', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
							<%} %>
								<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
							<%} %>
							
							<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
								&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="Next('maketlist', 'next')"> &nbsp;
							<%}else{ %>
								&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
							<%} %>
							<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
						   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
					   		<%}else{ %>
					   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View('maketlist', -1, 'view')"> &nbsp;
					   		<%} %>
						</TD>
					 </tr>                                                  
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
</html>
<%
	try {
			if (mklist != null)
				mklist.close();

			if (obj != null) {
				obj.DBClose();
				obj = null;
			}
			session.setAttribute("obj", null);
		} catch (SQLException e) {
			e.printStackTrace();
		}
%>
<%
	}
%>