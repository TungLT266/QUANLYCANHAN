<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.erp.beans.phuongphapthunghiem.IPhuongPhapThuNghiemList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%
		IPhuongPhapThuNghiemList obj =(IPhuongPhapThuNghiemList)session.getAttribute("obj");
		String userId=(String)session.getAttribute("userId");
		String userName=(String)session.getAttribute("userTen");
		ResultSet RsPPTNList=obj.getRsPPTNList();
		ResultSet RsLoaiTieuChi=obj.getRsLoaiTieuChi();
		ResultSet RsYeuCauKyThuat=obj.getRsYeuCauKyThuat();
		
	%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<link rel="stylesheet" href="../css/main.css" type="text/css">
<link rel="stylesheet" href="../css/calendar.css" type="text/css">
<link rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript"
	src="../scripts/jquery.tools.min.js"></script>
<script type="text/javascript" src="../scripts/ajax.js"></script>

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<link rel="stylesheet" type="text/css" href="../css/style.css" />


<link rel="stylesheet" href="../css/cdtab.css" type="text/css">
<link rel="stylesheet" type="text/css" media="screen"
	href="../css/tabs.css">
<link rel="stylesheet" type="text/css" media="screen"
	href="../css/tabs-panes.css">
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
    <script type="text/javascript">
    	function submitform(){
    		document.forms['pptnForm'].action.value="search";
    		document.forms['pptnForm'].submit();
    	}
    	function TaoMoi(){
    		document.forms['pptnForm'].action.value="luumoi";
    		document.forms['pptnForm'].submit();
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
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script type="text/javascript">
	$(document).ready(function()
	{
		$(".select2").select2();
	});
	
</script>
</head>
<body>
	<form name="pptnForm" method="post" action="../../ErpPhuongPhapThuNghiemSvl" >
		<input type="hidden" name="action"> 
		<input type="hidden" name="msg" value=''>
	
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top'>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Hồ Sơ Thử Nghiệm > Phương Pháp Thử Nghiệm</TD>
										<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userName %>
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
									<TR><%=obj.getMsg() %>
										<TD width="100%" align="left">
											<FIELDSET>
												<LEGEND class="legendtitle">Tiêu chí tìm kiếm </LEGEND>

												<TABLE class="tblight" width="100%" cellpadding="6"
													cellspacing="0">
													<TR>
														<TD width="10%" class="plainlabel">Mã</TD>
														<TD class="plainlabel"  colspan="3"><input type="text" value="<%=obj.getMaPPTN()%>" 
														class="txtsearch" name="ma" size="20" onchange="javascript:submitform()"></TD>
													</TR>
													<TR>
														<TD width="10%" class="plainlabel">Tên Viết Tắt</TD>
														<TD class="plainlabel" colspan="3"><input type="text"
															class="txtsearch" name="tenviettat" size="20" value="<%=obj.getTenVT() %>"
															 onchange="javascript:submitform()"></TD>
													</TR>
													<TR>
														<TD width="20%" class="plainlabel">Trạng thái</TD>
														<TD class="plainlabel" colspan="3">
															<select name="trangthai" onChange="submitform();" class="select2" style="width:200px">
																<option value=""></option>
																<option value="0" <%if(obj.getTrangThai().equals("0")){ %>selected<%} %>>Ngưng hoạt động</option>
																<option value="1" <%if(obj.getTrangThai().equals("1")){ %>selected<%} %>>Hoạt động</option>
																<option value="2" <%if(obj.getTrangThai().equals("2")){ %>selected<%} %>>Đã xóa</option>
															</select>
														</TD>
													</TR>
													<TR>
														<TD width="10%" class="plainlabel">Loại Tiêu Chí</TD>
														<TD class="plainlabel"><SELECT name="loaitieuchi" class="select2" style="width:200px" onchange="javascript:submitform()">
															<option></option>
														<%
															if(RsLoaiTieuChi!=null)
															{
																while(RsLoaiTieuChi.next())
																{
																	if(obj.getLoaiTieuChi().equals(RsLoaiTieuChi.getString("PK_SEQ"))){
																	
																	%>
																		<option selected value="<%=RsLoaiTieuChi.getString("PK_SEQ") %>"><%=RsLoaiTieuChi.getString("MA") %>-<%=RsLoaiTieuChi.getString("TEN") %></option>
																	<%
																	}
																	else{
																		%>
																		<option value="<%=RsLoaiTieuChi.getString("PK_SEQ") %>"><%=RsLoaiTieuChi.getString("MA") %>-<%=RsLoaiTieuChi.getString("TEN") %></option>
																		<%
																	}
																}
															}
														%>
															
															</SELECT>
														</TD>
														<TD width="10%" class="plainlabel">Yêu Cầu Kỹ Thuật</TD>
														<TD class="plainlabel"><SELECT name="yeucaukythuat" class="select2" style="width:200px" onchange="javascript:submitform()">
															<option></option>
														<%
															if(RsYeuCauKyThuat!=null)
															{
																while(RsYeuCauKyThuat.next())
																{
																	if(obj.getYeuCauKyThuat().equals(RsYeuCauKyThuat.getString("PK_SEQ"))){
																		%>
																			<option selected value="<%=RsYeuCauKyThuat.getString("PK_SEQ") %>"><%=RsYeuCauKyThuat.getString("MA") %>-<%=RsYeuCauKyThuat.getString("TEN") %></option>
																		<%
																	}
																	else{
																		%>
																			<option value="<%=RsYeuCauKyThuat.getString("PK_SEQ") %>"><%=RsYeuCauKyThuat.getString("MA") %>-<%=RsYeuCauKyThuat.getString("TEN") %></option>
																		<%
																	}
																}
															}
														%>
															
															</SELECT>
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
					<TABLE width="100%" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left">
								<FIELDSET>
								
									<LEGEND class="legendtitle">
										&nbsp;Phương Pháp Thử Nghiệm &nbsp;&nbsp;&nbsp;  
										 <a class="button3"
											href="javascript:TaoMoi()"> 
											<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới</a>
									</LEGEND> 

									<TABLE class="" width="100%" border="0" cellpadding="0" cellspacing="0">
										<TR>
											<TD width="98%">
												<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
													<TR class="tbheader">
														<TH align="center" width="5%">Mã</TH>
														<TH align="center" width= "10%">Tên</TH>
														<TH align="center" width = "15%">Diễn Giải</TH>
														<TH align="center" width = "10%">Loại Tiêu Chí</TH>
														<TH align="center" width = "10%">Yêu Cầu Kỹ Thuật</TH>
														<TH align="center" width = "10%">Trạng thái</TH>
														<TH align="center" width = "5%">Ngày tạo</TH>
														<TH align="center" width = "5%">Người tạo</TH>
														<TH align="center" width = "10%">Ngày sửa</TH>
														<TH align="center" width = "5%">Người sửa</TH>
														<TH align="center" width = "5%">Tác vụ</TH>
													</TR>
													<%
														if(RsPPTNList!=null){
															int u=0;
															while(RsPPTNList.next()){
																u++;
																String s="";
																if(u%2==0){s="tblightrow";}
																else{
																	s="tbdarkrow";
																}
																
																	
																%>
																<TR class="<%=s%>">
																	<TD align="center"><%=RsPPTNList.getString("MA") %></TD>
																	<TD align="left"><%=RsPPTNList.getString("TEN") %></TD>
																	<TD align="left"><%=RsPPTNList.getString("DIENGIAI") %></TD>
																	<TD align="left"><%=RsPPTNList.getString("TENLOAITIEUCHI") %></TD>
																	<TD align="left"><%=RsPPTNList.getString("TENYEUCAUKYTHUAT") %></TD>
																	<%if(RsPPTNList.getString("TRANGTHAI").equals("0")) { %>
																		<TD align="center" style="color: red;">Ngưng hoạt động</TD>
																	<%} else if(RsPPTNList.getString("TRANGTHAI").equals("2")){ %>
																		<TD align="center" style="color: red;">Đã xóa</TD>
																	<%} else { %>
																		<TD align="center">Hoạt động</TD>
																	<%} %>
																	<TD align="left"><%=RsPPTNList.getString("NGAYTAO") %></TD>
																	<TD align="left"><%=RsPPTNList.getString("NGAYSUA") %></TD>
																	<TD align="center"><%=RsPPTNList.getString("NGUOITAO") %></TD>
																	<TD align="center"><%=RsPPTNList.getString("NGUOISUA") %></TD>
																	
																	<TD align="center">
																		<TABLE border="0" cellpadding="0" cellspacing="0">
																		<%
																			if(RsPPTNList.getString("TRANGTHAI").equals("2"))
																			{
																				%>
																					<TR>
																						<TD>
																						
																						<A
																							href="../../ErpPhuongPhapThuNghiemSvl?userId=<%=userId%>&display=<%=RsPPTNList.getString("PK_SEQ") %>">
																								<img title="Hiển Thị" src="../images/Display20.png" alt="Hien Thi"
																								width="20" height="20" longdesc="Hien thi" border=0>
																						</A>
																						</TD>
																					</TR>
																				<%
																			}
																			else
																			{
																				%>
																					<TR>
																						<TD>
																						
																						<A
																							href="../../ErpPhuongPhapThuNghiemUpdateSvl?userId=<%=userId%>&update=<%=RsPPTNList.getString("PK_SEQ") %>">
																								<img title="Cập nhật" src="../images/Edit20.png" alt="Cap nhat"
																								width="20" height="20" longdesc="Cap nhat" border=0>
																						</A>
																						</TD>
																						<TD>
																						<TD>
																						
																						<A
																							href="../../ErpPhuongPhapThuNghiemSvl?userId=<%=userId%>&delete=<%=RsPPTNList.getString("PK_SEQ") %>">
																								<img title="Xóa" src="../images/Delete20.png" alt="Xoa"
																								width="20" height="20" longdesc="Xoa" border=0>
																						</A>
																						</TD>
																						<TD>
																						
																						<A
																							href="../../ErpPhuongPhapThuNghiemSvl?userId=<%=userId%>&display=<%=RsPPTNList.getString("PK_SEQ") %>">
																								<img title="Hiển Thị" src="../images/Display20.png" alt="Hien Thi"
																								width="20" height="20" longdesc="Hien thi" border=0>
																						</A>
																						</TD>
																					</TR>
																				<%
																			}
																		%>
																			
																		</TABLE>
																	</TD>
																</TR>
																<% }
													}%>
													
													
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

<% obj.DBClose(); %>
	