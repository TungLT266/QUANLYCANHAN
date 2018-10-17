<%-- <%@page import="com.aspose.cells.a.a.a.i"%> --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.baocaocandoiphatsinh.*"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.traphaco.center.util.*"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%
	System.out.println("");
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if (!util.check(userId, userTen, sum)) {
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	} else {
%>
<%
	IErp_BaoCaoCanDoiPhatSinhList obj = (IErp_BaoCaoCanDoiPhatSinhList) session.getAttribute("obj");
	List<PhatSinhKeToanItem> viewList = obj.getViewList();
	List<String> colunmNameList = obj.getColunmNameList();
	NumberFormat formatter = new DecimalFormat("#,###,###.##");
	ResultSet cdpsRs = obj.getCdpsRs();
%>
<%
int[] quyen = new  int[5];
quyen = util.Getquyen("Erp_BaoCaoCanDoiPhatSinhSvl","",userId);
%>
<% obj.setNextSplittings(); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Traphaco - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
<!-- <script type="text/javascript" src="../scripts/ajax_erp_loadspvattu.js"></script> -->
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<script type="text/javascript" src="../scripts/ajax.js"></script>
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});	
</script>

<style type="text/css">
	#mainContainer{
		width:660px;
		margin:0 auto;
		text-align:left;
		height:100%;
		border-left:3px double #000;
		border-right:3px double #000;
	}
	#formContent{
		padding:5px;
	}
	/* END CSS ONLY NEEDED IN DEMO */
		
	/* Big box with list of options */
	#ajax_listOfOptions{
		position:absolute;	/* Never change this one */
		width:auto;	/* Width of box */
		height:200px;	/* Height of box */
		overflow:auto;	/* Scrolling features */
		border:1px solid #317082;	/* Dark green border */
		background-color:#C5E8CD;	/* White background color */
    	color: black;
		text-align:left;
		font-size:1.0em;
		z-index:100;
	}
	#ajax_listOfOptions div{	/* General rule for both .optionDiv and .optionDivSelected */
		margin:1px;		
		padding:1px;
		cursor:pointer;
		font-size:1.0em;
	}
	#ajax_listOfOptions .optionDiv{	/* Div for each item in list */
		
	}
	#ajax_listOfOptions .optionDivSelected{ /* Selected item in the list */
		background-color:#317082; /*mau khi di chuyen */
		color:#FFF;
	}
	#ajax_listOfOptions_iframe{
		background-color:#F00;
		position:absolute;
		z-index:5;
	}
	form{
		display:inline;
	}
	#dhtmltooltip
	{
		position: absolute;
		left: -300px;
		width: 150px;
		border: 1px solid black;
		padding: 2px;
		background-color: lightyellow;
		visibility: hidden;
		z-index: 100;
		/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
		filter: progid:DXImageTransform.Microsoft.Shadow(color=gray,direction=135);
	}	
	#dhtmlpointer
	{
		position:absolute;
		left: -300px;
		z-index: 101;
		visibility: hidden;
	}
	
</style>
<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />

<script>
//perform JavaScript after the document is scriptable.
$(function() {
 // setup ul.tabs to work as tabs for each div directly under div.panes
 	$("ul.tabs").tabs("div.panes > div");
});
</script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2(); });
</script>




<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
	<style type="text/css">
		#dhtmltooltip
		{
			position: absolute;
			left: -300px;
			width: 150px;
			border: 1px solid black;
			padding: 2px;
			background-color: lightyellow;
			visibility: hidden;
			z-index: 100;
			float:left;
			/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
			filter: progid:DXImageTransform.Microsoft.Shadow(color=gray,direction=135);
		}	
		#dhtmlpointer
		{
			position:absolute;
			left: -300px;
			z-index: 101;
			visibility: hidden;
		}
		th {
		cursor: pointer;
		}	
		
		.fixed {position:fixed; top:0; left:10; z-index:999999; width:99%;}
	
		
  	</style>
  	
  	
  	<script type="text/javascript">
  		
  	$(window).scroll(function(){
        if ($(this).scrollTop() > 135) {
            $('#task_flyout').addClass('fixed');
        } else {
            $('#task_flyout').removeClass('fixed');
        }
    });
  	
  	</script>
  	
  	


<SCRIPT language="JavaScript" type="text/javascript">
	function submitform(check) {
		if(!kiemTra()){
			return false;
		}
		document.xdForm.action.value = 'search';
		document.forms["xdForm"].submit();
	}

	function clearform() {
		document.xdForm.maCCDC.value = "";
		document.xdForm.soChungTu.value = "";
		document.xdForm.trangThai.selectedIndex = 0;
		document.xdForm.ngayBatDau.value = "";
		document.xdForm.ngayKetThuc.value = "";
		submitform(0);
	}

	function newform() {
		document.forms['xdForm'].action.value = 'new';
		document.forms['xdForm'].submit();
	}
	
	function exportExcel()
	{
		if(!kiemTra()){
			return false;
		}
		document.forms['xdForm'].action.value= 'exportExcel';
		document.forms['xdForm'].submit();
	}
	
	function khaiBaoMau()
	{
		document.forms['xdForm'].action.value= 'khaiBaoMau';
		document.forms['xdForm'].submit();
	}
	
	function thongbao() {
		console.log("thong bao");
		var tt = document.forms['xdForm'].msg.value;
		if (tt.length > 0)
			alert(document.forms['xdForm'].msg.value);
	}
	function kiemTra(){
		var tuNgay = document.getElementById("tuNgay");
		var denNgay = document.getElementById("denNgay");
		if (tuNgay.value.trim().length ==0 || denNgay.value.trim().length == 0){
			alert ('Vui lòng nhập ngày bắt đầu và ngày kết thúc lấy báo cáo .');
			return false;
		}else if(tuNgay.value.trim() <= '2016-09-30' || denNgay.value.trim() <='2016-09-30'){
			alert ( 'Ngày DKSD : 30-09-2016 . Vui lòng nhập thời gian lấy báo cáo từ ngày 01-10-2016');
			return false;
		}
		return true;
	}
</SCRIPT>




</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="xdForm" method="post" action="../../Erp_BaoCaoCanDoiPhatSinhSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> 
		<%System.out.println("obj.getMsg(): " + obj.getMsg()); %>
		<input type="hidden" name="action" value='1'> <input type="hidden" id="msg" name="msg" value='<%=obj.getMsg()%>'> 

		<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
		<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >

		<script language="JavaScript" type="text/javascript">
    	thongbao();
		</script>
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left" class="tbnavigation">
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý kế toán > Báo cáo > Bảng tổng hợp tài khoản</TD>
										<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen%>&nbsp;
										</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" cellpadding="0" cellspacing="1">
						<TR>
							<TD>
								<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
									<TR>
										<TD width="100%" align="left">
											<FIELDSET>
												<LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm &nbsp;</LEGEND>
												<TABLE class="tblight" width="100%" cellpadding="6" cellspacing="0">
													<TR style="display: none;">
														<TD width="19%" class="plainlabel">Mã chi phí trả trước</TD>
														<TD class="plainlabel" colspan=5><INPUT name="maCCDC" type="text" size="30" value='<%=""%>' onChange="submitform();"></TD>
														
														<TD width="19%" class="plainlabel">Số chứng từ</TD>
														<TD class="plainlabel" colspan=5><INPUT name="soChungTu" type="text" size="30" value='<%=""%>' onChange="submitform();"></TD>
													</TR>
													<TR>
														<TD width="19%" class="plainlabel">Từ ngày</TD>
														<TD class="plainlabel" colspan=5><INPUT name="tuNgay" id="tuNgay" class="days" type="text" size="30" value='<%=obj.getDieuKienLoc().getTuNgay()%>' readonly="readonly"></TD>
														<TD colspan="6" class="plainlabel"></TD>
													</TR>
													<TR>
														<TD width="19%" class="plainlabel">Đến ngày</TD>
														<TD class="plainlabel" colspan=5><INPUT name="denNgay" id="denNgay" class="days" type="text" size="30" value='<%=obj.getDieuKienLoc().getDenNgay()%>' readonly="readonly"></TD>
														<TD colspan="6" class="plainlabel"></TD>
													</TR>
													
													<TR style="display: none;">
														<TD class="plainlabel">Tài khoản công nợ</TD>
														<TD class="plainlabel">
															<select name="soHieuTaiKhoanNo" id="soHieuTaiKhoanNo">
																<option value=""></option>
																<%
																	for (Erp_Item item: obj.getDieuKienLoc().getTaiKhoanList())
																		if (obj.getDieuKienLoc().getSoHieuTaiKhoanNo().equals(item.getValue())){
																%>
																	<option value="<%=item.getValue() %>" selected><%=item.getName() %></option>
																<%
																		} else {
																%>
																	<option value="<%=item.getValue() %>"><%=item.getName() %></option>
																<%
																	}
																%>
														</select></TD>
														<TD colspan="6" class="plainlabel"></TD>
													</TR>
													
													<TR style="display: none;">
														<TD class="plainlabel">Loại đối tượng</TD>
														<TD class="plainlabel">
															<select name="loaiDoiTuongNoId" id="loaiDoiTuongNoId" onchange="submitform(0);">
																<option value=""></option>
																<%
																	for (Erp_Item item: obj.getDieuKienLoc().getLoaiDoiTuongList())
																		if (obj.getDieuKienLoc().getLoaiDoiTuongNoId().trim().equals(item.getValue())){
																%>
																	<option value="<%=item.getValue() %>" selected><%=item.getName() %></option>
																<%
																		} else {
																%>
																	<option value="<%=item.getValue() %>"><%=item.getName() %></option>
																<%
																	}
																%>
														</select></TD>
														<TD colspan="6" class="plainlabel"></TD>
													</TR>
													
													<TR style="display: none;">
														<TD class="plainlabel">Tên đối tượng</TD>
														<TD class="plainlabel">
															<select name="doiTuongNoId" id="doiTuongNoId">
																<option value=""></option>
																<%
																	for (Erp_Item item: obj.getDieuKienLoc().getDoiTuongNoList())
																		if (obj.getDieuKienLoc().getDoiTuongNoId().trim().equals(item.getValue())){
																%>
																	<option value="<%=item.getValue() %>" selected><%=item.getName() %></option>
																<%
																		} else {
																%>
																	<option value="<%=item.getValue() %>"><%=item.getName() %></option>
																<%
																	}
																%>
															</select>
														</TD>
														<TD colspan="6" class="plainlabel"></TD>
													</TR>
													
													<TR>
														<TD class="plainlabel">Công ty</TD>
														<TD class="plainlabel">
															<select name="congTyLId" style="width: 300px;">
																<option value="0" selected>All</option>
																<%
																	for (Erp_Item item: obj.getDieuKienLoc().getCongTyList())
																		if (obj.getDieuKienLoc().getCongTyId().trim().equals(item.getValue())){
																%>
																	<option value="<%=item.getValue() %>" selected><%=item.getName() %></option>
																<%
																		} else {
																%>
																	<option value="<%=item.getValue() %>"><%=item.getName() %></option>
																<%
																	}
																%>
															</select>
														</TD>
														<TD colspan="6" class="plainlabel"></TD>
													</TR>
													
													<TR>
														<TD class="plainlabel" colspan="2"><a class="button2" href="javascript:clearform()"> <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại
														</a>&nbsp;&nbsp;&nbsp;&nbsp; <a class="button2" href="javascript:submitform(1);""> <img style="top: -4px;" src="../images/button.png" alt="">Tìm kiếm
														</a>
														&nbsp;&nbsp;&nbsp;&nbsp;
														<a class="button3" href="javascript:exportExcel()"><img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a>
														&nbsp;&nbsp;&nbsp;&nbsp;
<!-- 														<a class="button3" href="javascript:khaiBaoMau();"><img style="top: -4px;" src="../images/button.png" alt="">Khai báo mẫu </a> -->
														</TD>
														<TD colspan="6" class="plainlabel"></TD>
													</TR>
												</TABLE>
											</FIELDSET>
										</TD>
									</TR>
								</TABLE>
								<TABLE width="100%" cellpadding="0" cellspacing="1">
									<TR>
										<TD width="100%">
											<FIELDSET>
												<LEGEND class="legendtitle">
													&nbsp;Bảng tổng hợp tài khoản&nbsp;&nbsp; 
												</LEGEND>
												<TABLE width="100%">
													<TR>
														<TD width="100%">
															<TABLE  width="100%" border="0" cellspacing="1" cellpadding="2">
																<TR class="tbheader" id=task_flyout>
																	<TH width="5%">STT</TH>
																	<%
																	for (String colunmName : colunmNameList){ System.out.println("colunmName: " + colunmName);%>
																	<TH width="10%"><%=colunmName %></TH>
																	<%} %>
																</TR>
																

																<%
																	if (cdpsRs != null) {
																			try {
																				int m = 0;
																				String lightrow = "tblightrow";
																				String darkrow = "tbdarkrow";
																				while (cdpsRs.next()) 
																				{
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
																	<TD align="center" width="5%"><%= m + 1%></TD>
																	<TD align="left"  width="10%"><%= cdpsRs.getString("SOHIEUTAIKHOAN") %></TD>
																	<TD align="left"  width="10%"><%= cdpsRs.getString("TENTAIKHOAN") %></TD>
																	<TD align="right"  width="10%"><%= formatter.format(cdpsRs.getDouble("DAUKYNOVND"))%></TD>
																	<TD align="right"  width="10%"><%= formatter.format(cdpsRs.getDouble("DAUKYCOVND"))%></TD>
																	<TD align="right"  width="10%"><%= formatter.format(cdpsRs.getDouble("PHATSINHNOVND"))%></TD>
																	<TD align="right"  width="10%"><%= formatter.format(cdpsRs.getDouble("PHATSINHCOVND"))%></TD>
																	<TD align="right"  width="10%"><%= formatter.format(cdpsRs.getDouble("CUOIKYNO"))%></TD>
																	<TD align="right"  width="10%"><%= formatter.format(cdpsRs.getDouble("CUOIKYCO"))%></TD>
																	<TD align="center"  width="10%">
																	<%String baoCaoSvl = "Erp_BaoCaoSoCaiTaiKhoanSvl";
/* 																	if (item.getTaiKhoan().trim().equals("1"))
																		baoCaoSvl = "Erp_BaoCaoCongNoSvl";
 */																	%>
																		<A href="../../<%=baoCaoSvl %>?userId=<%=userId%>&display=1&soHieuTaiKhoan=<%=cdpsRs.getString("SOHIEUTAIKHOAN")%>&tuNgay=<%=obj.getDieuKienLoc().getTuNgay()%>&denNgay=<%=obj.getDieuKienLoc().getDenNgay()%>&congTyId=<%=obj.getDieuKienLoc().getCongTyId()%>">
																			<img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border = 0>
																		</A>&nbsp;
																	</TD>
																</TR>
																<%
																	m++;
																				}
																			} catch (Exception e) {
																				e.printStackTrace();
																			}
																		}
																%>
																<tr class="tbfooter" > 
																	 <TD align="center" valign="middle" colspan="13" class="tbfooter">
																	 	<%if(obj.getNxtApprSplitting() >1) {%>
																			<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('xdForm', 1, 'view')"> &nbsp;
																		<%}else {%>
																			<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
																			<%} %>
																		<% if(obj.getNxtApprSplitting() > 1){ %>
																			<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="Prev('xdForm', 'prev')"> &nbsp;
																		<%}else{ %>
																			<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
																		<%} %>
																		
																		<%
																			int[] listPage = obj.getNextSplittings();
																			if (listPage != null)
																			for(int i = 0; i < listPage.length; i++){
																		%>
																		
																		<% 							
																	
																		if(listPage[i] == obj.getNxtApprSplitting()){ %>
																		
																			<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
																		<%}else{ %>
																			<a href="javascript:View('xdForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
																		<%} %>
																			<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
																		<%} %>
																		
																		<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
																			&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="Next('xdForm', 'next')"> &nbsp;
																		<%}else{ %>
																			&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
																		<%} %>
																		<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
																	   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
																   		<%}else{ %>
																   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View('xdForm', -1, 'view')"> &nbsp;
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
				</TD>
			</TR>
		</TABLE>
	</form>
</BODY>
</HTML>
<%
	if(cdpsRs != null) cdpsRs.close();
	viewList.clear();
	colunmNameList.clear();
	session.removeAttribute("obj");
	obj.DBClose();
	obj = null;
}
%>
