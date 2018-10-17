<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.baocaochiphi.*"%>
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
// 	if (!util.check(userId, userTen, sum)) {
// 		response.sendRedirect("/TraphacoHYERP/index.jsp");
// 	} else {
	{
%>
<%
	IBaoCaoChiPhiList obj = (IBaoCaoChiPhiList) session.getAttribute("obj");
	NumberFormat formatter = new DecimalFormat("#,###,###.##");
	String[] phongBanIds = obj.getPhongBanIds();
	ResultSet rsNhomChiPhi = obj.getRsNhomChiPhi();
%>
<%
int[] quyen = new  int[5];
quyen = util.Getquyen("BaoCaoChiPhiSvl","&view=TT",userId);
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
<SCRIPT language="JavaScript" type="text/javascript">
	function submitform(check) {
		//Có kiểm tra điều kiện lọc
		if (check == "1")
		{
			var soHieuTaiKhoanNo = document.getElementById("soHieuTaiKhoanNo").value;
			if (soHieuTaiKhoanNo == "")
			{
				alert("Vui lòng chọn tài khoản");
				return;
			}
			
// 			var loaiDoiTuongNoId = document.getElementById("loaiDoiTuongNoId").value;
// 			console.log("loaiDoiTuongNoId: " + loaiDoiTuongNoId);
// 			if (loaiDoiTuongNoId == "")
// 			{
// 				alert("Vui lòng chọn Loại đối tượng");
// 				return;
// 			}
			
// 			var doiTuongNoId = document.getElementById("doiTuongNoId").value;
// 			console.log("doiTuongNoId: " + doiTuongNoId);
// 			if (doiTuongNoId == "")
// 			{
// 				alert("Vui lòng chọn đối tượng");
// 				return;
// 			}
		}
		document.xdForm.action.value = 'search';
		document.forms["xdForm"].submit();
	}

	function clearform() {
		document.xdForm.nam.value = "";
		document.xdForm.thangBatDau.selectedIndex = 0;
		document.xdForm.thangKetThuc.selectedIndex = 0;
		document.xdForm.phongBanId.selectedIndex = 0;
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
	function kiemTra(){
		var monthStart = document.getElementById("thangBatDau").value;
		var monthEnd = document.getElementById("thangKetThuc").value;
		var year = document.getElementById("nam").value;
		if((monthStart < 10 && year <=2016) || (monthEnd < 10 && year <=2016)){
			alert("Ngày DKSD : 30-09-2016 . Vui lòng nhập thời gian lấy báo cáo từ ngày 01-10-2016");
			return false; 
		}
		var phongBan = document.getElementById("phongBanId").value;
		if(phongBan.length == 0){
			alert("Vui lòng nhập phòng ban");
			return 
		}
		return true;
	}
	
	function thongbao() {
		console.log("thong bao");
		var tt = document.forms['xdForm'].msg.value;
		if (tt.length > 0)
			alert(document.forms['xdForm'].msg.value);
}
</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="xdForm" method="post" action="../../BaoCaoChiPhiSvl">
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
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Báo cáo quản trị &gt; Kế toán - tài chính &gt; Báo cáo chi phí theo khoản mục</TD>
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
													<TR>
														<TD class="plainlabel">Năm</TD>
														<TD class="plainlabel" colspan="3">
															<select name="nam" id="nam" style="width: 100px;">
																<%for (int i = 2016; i < 2030; i++)
																	if (obj.getNam().equals(i))
																	{
																%>
																	<option value="<%=i %>" selected><%=i %></option>
																<%
																	} else {
																%>
																	<option value="<%=i %>"><%=i %></option>
																<%
																	}
																%>
															</select>
														</TD>
													</TR>
													
													<TR>
														<TD width="19%" class="plainlabel">Từ tháng</TD>
														<TD class="plainlabel">
															<select name="thangBatDau" id="thangBatDau" style="width: 100px;">
																<%for (int i = 1; i <= 12; i++)
																	if (obj.getThangBatDau().equals("" + i))
																	{
																%>
																	<option value="<%=i %>" selected><%=i %></option>
																<%
																	} else {
																%>
																	<option value="<%=i %>"><%=i %></option>
																<%
																	}
																%>
															</select>
														</TD>
													
														<TD width="19%" class="plainlabel">Đến tháng</TD>
														<TD class="plainlabel">
															<select name="thangKetThuc" id="thangKetThuc" style="width: 100px;">
																<%
																System.out.print("obj.getThangKeThuc(: '" + obj.getThangKeThuc() + "'");
																for (int i = 1; i <= 12; i++)
																	if (obj.getThangKeThuc().equals("" + i))
																	{
																%>
																	<option value="<%=i %>" selected><%=i %></option>
																<%
																	} else {
																%>
																	<option value="<%=i %>"><%=i %></option>
																<%
																	}
																%>
															</select>
														</TD>
													</TR>
													
													<TR>
														<TD class="plainlabel">Phòng ban</TD>
														<TD class="plainlabel" colspan="3">
															<select name="phongBanId" id="phongBanId" style="width: 500px; height: 300px;" multiple>									
																<%
																	for (Erp_Item item: obj.getPhongBanList()){
																		int kiemTra = 0 ;	
																		if(phongBanIds != null)
																			for(int i = 0 ; i<=phongBanIds.length;i++){
																																					
																				if (phongBanIds[i].indexOf(item.getValue()) >= 0){
																				kiemTra ++;
																	%>
																		<option value="<%=item.getValue() %>" selected><%=item.getName() %></option>
																	<%
																			}}
																		if(kiemTra == 0){
				 													%>
																		<option value="<%=item.getValue() %>"><%=item.getName() %></option>
																	<%
																	}}
																%>
															</select>
														</TD>
														
													</TR>
                                                    <TR>
                                                        <TD class="plainlabel">Nhóm chi phí</TD>
                                                        <TD class="plainlabel" colspan="3">
                                                          <select name="nhomChiPhiId" id="nhomChiPhiId" style="width: 500px; height: 300px;">
                                                               <option value="" ></option>                  
                                                            <%
                                                                   if(rsNhomChiPhi != null){
                                                                        while(rsNhomChiPhi.next()){
                                                                          if(rsNhomChiPhi.getString("PKSEQ").equals(obj.getNhomChiPhiId())){
                                                              %>
                                                                    <option value="<%=rsNhomChiPhi.getString("PKSEQ")%>" selected><%=rsNhomChiPhi.getString("TEN") %></option>
                                                                  <%
                                                                      }else{
                                                                  %>
                                                                    <option value="<%=rsNhomChiPhi.getString("PKSEQ")%>"><%=rsNhomChiPhi.getString("TEN") %></option>
                                                                  <%
                                                                  }}}
                                                                %>
                                                          </select>
                                                        </TD>
                                                        
                                                      </TR>
													
													<TR>
														<TD class="plainlabel" colspan="4">
															<a class="button2" href="javascript:clearform()"> 
																<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại
														</a>&nbsp;&nbsp;&nbsp;&nbsp; 
<!-- 														<a class="button2" href="javascript:submitform(1);"">  -->
<!-- 															<img style="top: -4px;" src="../images/button.png" alt="">Tìm kiếm -->
<!-- 														</a>&nbsp;&nbsp;&nbsp;&nbsp;  -->
														
														<a class="button3" href="javascript:exportExcel()"><img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a>
														
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
  if(rsNhomChiPhi != null) rsNhomChiPhi.close();
  obj.DBClose();
  }
%>