<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.erp.db.sql.dbutils"%>
<%@page import="geso.traphaco.erp.beans.congty.*"%>
<%@page import="geso.traphaco.center.util.*" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{
	
		IErpCongTyList congty = (IErpCongTyList) session.getAttribute("obj");

		ResultSet ctList = congty.getCongtyList();
	    int[] quyen = new  int[5];
		quyen = util.Getquyen("ErpCongTySvl","&loaiNPP=0&isKH=0",userId);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<link rel="stylesheet" href="../css/main.css" type="text/css">
<link rel="stylesheet" href="../css/calendar.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">


<script type="text/javascript">
	function clearform() {
		document.ctForm.ma.value = "";
		document.ctForm.diachi.value = "";
		document.ctForm.dienthoai.value = "";
		document.ctForm.fax.value = "";
		document.ctForm.trangthai.selectedIndex = 2;
		Submitform();
	}
	function Submitform() {
		document.forms["ctForm"].action.value = "search";
		document.forms["ctForm"].submit();
	}
	function TaoMoi() {
		document.forms["ctForm"].action.value = "new";
		document.forms["ctForm"].submit();
	}
	 function thongbao()
	 {
		 var tt = document.forms["ctForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["ctForm"].msg.value);
	 }
</script>
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
</head>
<body>
	<form name="ctForm" method="post" action="../../ErpCongTySvl" >
		<input type="hidden" name="action"> 
		<input type="hidden" name="msg" value='<%= congty.getMessage() %>'>
		<script language="javascript" type="text/javascript">
    thongbao();
</script> 
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top'>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Cơ bản > Công ty</TD>
										<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen%>
										</TD>
									</tr>
								</table>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD>
								<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
									<TR>
										<TD width="100%" align="left">
											<FIELDSET>
												<LEGEND class="legendtitle">Tiêu chí tìm kiếm </LEGEND>

												<TABLE class="tblight" width="100%" cellpadding="6"
													cellspacing="0">
													<TR>
														<TD width="10%" class="plainlabel">Mã</TD>
														<TD class="plainlabel"><input type="text"
															class="txtsearch" name="ma" size="20"
															value="<%=congty.getMA()%>" onchange="Submitform()"></TD>
													</TR>
													<TR>
														<TD width="10%" class="plainlabel">Địa chỉ</TD>
														<TD class="plainlabel"><input type="text"
															class="txtsearch" name="diachi" size="20"
															value="<%=congty.getDIACHI()%>" onchange="Submitform()"></TD>
													</TR>
													<TR>
														<TD width="10%" class="plainlabel">Điện thoại</TD>
														<TD class="plainlabel"><input type="text"
															class="txtsearch" name="dienthoai" size="20"
															value="<%=congty.getDIENTHOAI()%>"
															onchange="Submitform()"></TD>
													</TR>
													<TR>
														<TD width="10%" class="plainlabel">FAX</TD>
														<TD class="plainlabel"><input type="text"
															class="txtsearch" name="fax" size="20"
															value="<%=congty.getFAX()%>" onchange="Submitform()"></TD>
													</TR>
													<TR>
														<TD width="10%" class="plainlabel">Trạng thái</TD>
														<TD class="plainlabel"><select name="trangthai"
															onchange="Submitform()">
																<%
																	if (congty.getTRANGTHAI().equals("0")) {
																%>
																<option value="1" >Hoạt động</option>
																<option value="0" selected>Ngưng hoạt động</option>
																<option value="2"></option>

																<%
																	} else if (congty.getTRANGTHAI().equals("2")) {
																%>
																<option value="0" >Ngưng hoạt động</option>
																<option value="1">Hoạt động</option>
																<option value="2" selected></option>

																<%
																	} else {
																%>
																<option value="1" selected>Hoạt động</option>
																<option value="0">Ngưng hoạt động</option>
																<option value="2" ></option>
																<%
																	}
																%>
														</select></TD>
													</TR>
													<TR>
														<TD class="plainlabel" colspan=2><a class="button2"
															href="javascript:clearform()"> <img
																style="top: -4px;" src="../images/button.png" alt="">Nhập
																lại
														</a>&nbsp;&nbsp;&nbsp;&nbsp;</TD>
													</TR>
												</TABLE>
											</FIELDSET>
										</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left">
								<FIELDSET>
								
									<LEGEND class="legendtitle">
										&nbsp;Công ty&nbsp;&nbsp;&nbsp;  
											<%if(quyen[0]!=0){ %>
										 <a class="button3"
											href="javascript:TaoMoi()"> 
											<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới</a>
										
										<%} %>
									</LEGEND> 

									<TABLE class="" width="100%" border="0" cellpadding="0"
										cellspacing="0">
										<TR>
											<TD width="98%">
												<TABLE width="100%" border="0" cellspacing="1"
													cellpadding="4">
													<TR class="tbheader">
														<TH align="center" width = "5%">Mã</TH>
														<TH align="center" width = "10%">Tên</TH>
														<TH align="center" width = "15%">Địa chỉ</TH>
														<TH align="center" width = "10%">Lãnh vực</TH>
														<TH align="center" width = "10%">Giấy phép KD</TH>
														<TH align="center" width = "5%">Vốn điều lệ</TH>
														<TH align="center" width = "5%">Ngày tạo</TH>
														<TH align="center" width = "10%">Người tạo</TH>
														<TH align="center" width = "5%">Ngày sửa</TH>
														<TH align="center" width = "10%">Người sửa</TH>
														<TH align="center" width = "5%">Tác vụ</TH>
													</TR>
													<%
													if (ctList != null) {
														try {
															int m = 0;
															String lightrow = "tblightrow";
															String darkrow = "tbdarkrow";
															while (ctList.next()) {
																if (m % 2 != 0) {
													%>
													<TR class=<%=lightrow%>>
														<%
															} else {
														%>
													
													<TR class=<%=darkrow%>>
														<%
															}
														%>
														<TD align="center"><%=ctList.getString("MA")%></TD>
														<TD align="left"><%=ctList.getString("TEN")%></TD>
														<TD align="left"><%=ctList.getString("DIACHI")%></TD>
														<TD align="left"><%=ctList.getString("NGANHNGHEKINHDOANH")%></TD>
<!-- 														<TD align="center"> -->
<%-- 															<% --%>
<!-- 																String trangthai = ""; -->
<!-- 																			String tt = ctList.getString("trangthai"); -->
<!-- 																			if (tt.equals("0")) -->
<!-- 																				trangthai = "Ngưng hoạt động"; -->
<!-- 																			else { -->
<!-- 																				if (tt.equals("1")) -->
<!-- 																					trangthai = "Hoạt động"; -->
<!-- 																			} -->
<%-- 															%> <%=trangthai%> --%>
<!-- 														</TD> -->
														<TD align="left"><%=ctList.getString("GIAYPHEPKINHDOANH")%></TD>
														<TD align="center"><%=ctList.getString("VONDIEULE")%></TD>
														<TD align="center"><%=ctList.getString("NGAYTAO")%></TD>
														<TD align="center"><%=ctList.getString("TENNV")%></TD>
														<TD align="center"><%=ctList.getString("NGAYSUA")%></TD>
														<TD align="center"><%=ctList.getString("TENNVS")%></TD>
														<TD align="center">
															<TABLE border="0" cellpadding="0" cellspacing="0">
																<TR>
																	<TD>
																	<%if(quyen[2]!=0){ %>
																	<A
																		href="../../ErpCongTyUpdateSvl?task=capnhat&id=<%=ctList.getString("PK_SEQ")%>">
																			<img title="Cập nhật" src="../images/Edit20.png" alt="Cap nhat"
																			width="20" height="20" longdesc="Cap nhat" border=0>
																	</A>
																	<%} %>
																	</TD>
																	<%-- <TD></TD>
																	<TD>
																	<%if(quyen[1]!=0){ %>
																	<A href="../../ErpCongTySvl?task=delete&id=<%=ctList.getString("PK_SEQ")%>">
																			<img title="Xóa" src="../images/Delete20.png" alt="Xoa"
																			width="20" height="20" longdesc="Xoa" border=0
																			onclick="return confirm('Bạn có muốn xóa công ty này ?')">
																	</A>
																	<%} %>
																	</TD> --%>
																	<TD>
																	<!-- QUYEN VIEW DLN -->
																	<A
																		href="../../ErpCongTyUpdateSvl?task=display&id=<%=ctList.getString("PK_SEQ")%>">
																			<img src="../images/Display20.png" title="Hiển thị" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
																	<!-- END QUYEN VIEW DLN -->
																	</TD>
																</TR>
															</TABLE>
														</TD>
													</TR>
													<%
														m++;
																}
																
															} catch (SQLException ex) {
																System.out.print("Loi roi....");
															}
														}
													%>

													<TR>
														<TD height="" colspan="15" align="center" class="tbfooter">
															&nbsp;</TD>
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
</body>
</html>


<%
if(ctList != null) { 
	ctList.close();
}
session.setAttribute("obj",null);
congty.closeDB();
}%>	