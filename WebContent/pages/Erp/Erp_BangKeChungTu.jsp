<%@page import="geso.traphaco.center.util.Erp_Item"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.erp.beans.bangkechungtu.imp.*"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	System.out.println("");
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	Erp_BangKeChungTu obj = (Erp_BangKeChungTu) session.getAttribute("obj");
	ResultSet tk = obj.getTaikhoan();
	ResultSet rs = obj.getData();
	String view = (String) session.getAttribute("view");  
	ResultSet ctyRs = obj.getCtyRs();
	String ctyIds = obj.getErpCongtyId();
	List<Erp_Item> nhomTaiKhoanList = obj.getNhomTaiKhoanList();
	List<Erp_Item> loaiNghiepVuList = obj.getLoaiNghiepVuList();
	List<Erp_Item> loaiDoiTuongList = obj.getLoaiDoiTuongList();
	List<Erp_Item> doiTuongList = obj.getDoiTuongList();
	List<Erp_Item> taiKhoanList = obj.getTaiKhoanList();
	List<Erp_Item> congTyList = obj.getCongTyList();
	List<Erp_BangKeChungTuListItem> viewList = obj.getViewList();
	obj.setNextSplittings();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>Traphaco - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery-1.js"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
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
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button1").hover(function(){
                $(".button1 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        });
		
    </script>

<script type="text/javascript" src="../scripts/report-js/action-report.js"></script>
<script language="javascript" type="text/javascript">	
	function seach()
	{
		document.forms['frm'].action.value= 'seach';
		document.forms["frm"].submit();
	}

	function Change()
	{   

   		document.forms["frm"].action.value="change";
   		document.forms["frm"].submit();
	}

	function submitform() {
		if(!kiemTra()){
			return false;
		}
		document.forms['frm'].action.value= 'tao';
		document.forms["frm"].submit();
	}

	function clearform() {
		document.forms['frm'].tungay.value= '';
		document.forms['frm'].denngay.value= '';
		document.forms['frm'].loaiNghiepVu.value= '';
		document.forms['frm'].soChungTu.value= '';
		document.forms['frm'].loaiDoiTuong.value= '';
		document.forms['frm'].doiTuong.value= '';
		document.forms['frm'].taiKhoanNo.value= '';
		document.forms['frm'].taiKhoanCo.value= '';
		document.forms['frm'].congTy.value= '';
		document.forms["frm"].action.value="change";
		document.forms["frm"].submit();
	}
	
	function setErrors(errorMsg) {
		var errors = document.getElementById("errors");
		errors.value = errorMsg;
	}
	
	function xuatExcel()
	{
		document.forms['frm'].action.value= 'excel';
		document.forms['frm'].submit();
	}
	
	function TinhTongTien()
	{		
	    var sumno = document.getElementById("sumno").value;
	    var sumco = document.getElementById("sumco").value;
		if(sumno == ''){
			 sumno = 0;
			 document.getElementById("sumno").value = '0';
		}
		else{
			document.getElementById("sumno").value = DinhDangTien(sumno);
		}
		
		if(sumco == ''){
			 sumco = 0;
			 document.getElementById("sumco").value = '';
		}
		else{
			document.getElementById("sumco").value = DinhDangTien(sumco);
		}
		
		document.getElementById("sumcoht").value = DinhDangTien(sumco);
		document.getElementById("sumnoht").value = DinhDangTien(sumno);
	}
	
	function DinhDangTien(num) 
	 {
	    num = num.toString().replace(/\$|\,/g,'');
	    if(isNaN(num))
	    num = "0";
	    sign = (num == (num = Math.abs(num)));
	    num = Math.floor(num*100+0.50000000001);
	    num = Math.floor(num/100).toString();
	    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	    num = num.substring(0,num.length-(4*i+3))+','+
	    num.substring(num.length-(4*i+3));
	    return (((sign)?'':'-') + num);
	}
	function kiemTra(){
		var tuNgay = document.getElementById("tungay");
		var denNgay = document.getElementById("denngay");
		if (tuNgay.value.trim().length ==0 || denNgay.value.trim().length == 0){
			alert ('Vui lòng nhập ngày bắt đầu và ngày kết thúc lấy báo cáo .');
			return false;
		}else if(tuNgay.value.trim() <= '2016-09-30' || denNgay.value.trim() <='2016-09-30'){
			alert ( 'Ngày DKSD : 30-09-2016 . Vui lòng nhập thời gian lấy báo cáo từ ngày 01-10-2016');
			return false;
		}
		return true;
	}
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


<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2(); });
</script>

</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="frm" method="post" action="../../Erp_BangKeChungTuSvl">
		<input type="hidden" name="action" value='1'> 
		<input type="hidden" name="userId" value='<%=userId%>'>
		<input type="hidden" name="view" value="<%= obj.getView()%>" >
		<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
		<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 60%; padding: 5px; float: left"
					class="tbnavigation">Quản lý kế toán > Báo cáo > Báo cáo chi tiết nghiệp vụ kế toán</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng Bạn <%=userTen%> &nbsp;</div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Thông báo</legend>
					<textarea id="errors" name="errors" rows="1" style="width: 100%"><%=obj.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset><legend class="legendtitle">Báo cáo chi tiết nghiệp vụ kế toán</legend></fieldset>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"	class="plainlabel">

							<TABLE width="100%" cellpadding="4" cellspacing="0">
								<TR>
			                        <TD class="plainlabel">Từ ngày</TD>
			                        <TD class="plainlabel" style="width: 400px">
			                            <input type="text" class="days" name="tungay" id="tungay" value="<%= obj.getTungay() %>" maxlength="10" readonly="readonly"/>
			                        </TD>
			                    
			                        <TD class="plainlabel">Đến ngày</TD>
			                        <TD class="plainlabel">
			                            <input type="text" class="days" name="denngay" id="denngay" value="<%= obj.getDenngay() %>" maxlength="10" readonly="readonly"/>
			                        </TD>
                    			</TR>
                    
<!-- 								Nhóm tài khoản -->
								<TR style="display: none;">	
									<TD class="plainlabel">Chọn nhóm tài khoản</TD>
									<TD class="plainlabel" colspan="3">
										<SELECT name = "nhomTaiKhoanId" style="width: 300px" onChange = "submitform();">
											<option value = "" ></option>
											<% if (nhomTaiKhoanList != null)
											{
												System.out.println("nhomTaiKhoanList: " + nhomTaiKhoanList.size());
												for (Erp_Item item : nhomTaiKhoanList)
												{
													if (item.getValue().equals(obj.getNhomTaiKhoanId()))
													{
													%>
														<option value="<%=item.getValue()%>" selected="selected"><%=item.getName()%></option>
													<% } else { %>
														<option value="<%=item.getValue()%>"><%=item.getName()%></option>
											<% } }} System.out.println("nhomTaiKhoanList: nullsss");%>
										</SELECT>
									</TD>
								</TR>

<!-- 								Loại nghiệp vụ  -->
								<TR>	
									<TD class="plainlabel">Loại nghiệp vụ</TD>
									<TD class="plainlabel">
										<SELECT name = "loaiNghiepVu" style="width: 300px" onChange = "submitform();">
											<option value = "" ></option>
											<% if (loaiNghiepVuList != null)
											{
												for (Erp_Item item : loaiNghiepVuList)
												{
													if (item.getValue().equals(obj.getLoaiNghiepVu()))
													{
													%>
														<option value="<%=item.getValue()%>" selected="selected"><%=item.getName()%></option>
													<% } else { %>
														<option value="<%=item.getValue()%>"><%=item.getName()%></option>
											<% } }} %>
										</SELECT>
									</TD>
<!-- 								</TR> -->

<!-- 								<TR>	 -->
									<TD class="plainlabel">Số chứng từ</TD>
									<TD class="plainlabel">
										<input type="text" name="soChungTu" value="<%= obj.getSoChungTu() %>" maxlength="10"  />
									</TD>
								</TR>
								
								<TR>	
									<TD class="plainlabel">Loại đối tượng</TD>
									<TD class="plainlabel">
										<SELECT name = "loaiDoiTuong" style="width: 300px" onChange = "submitform();">
											<option value = "" ></option>
											<% if (loaiDoiTuongList != null)
											{
												for (Erp_Item item : loaiDoiTuongList)
												{
													if (item.getValue().equals(obj.getLoaiDoiTuong()))
													{
													%>
														<option value="<%=item.getValue()%>" selected="selected"><%=item.getName()%></option>
													<% } else { %>
														<option value="<%=item.getValue()%>"><%=item.getName()%></option>
											<% } }} %>
										</SELECT>
									</TD>
<!-- 								</TR> -->
								
<!-- 								<TR>	 -->
									<TD class="plainlabel">Đối tượng</TD>
									<TD class="plainlabel">
										<SELECT name = "doiTuong" style="width: 300px" onChange = "submitform();">
											<option value = "" ></option>
											<% if (doiTuongList != null)
											{
												for (Erp_Item item : doiTuongList)
												{
													if (item.getValue().equals(obj.getDoiTuong()))
													{
													%>
														<option value="<%=item.getValue()%>" selected="selected"><%=item.getName()%></option>
													<% } else { %>
														<option value="<%=item.getValue()%>"><%=item.getName()%></option>
											<% } }} %>
										</SELECT>									</TD>
								</TR>
								
								<TR>	
									<TD class="plainlabel">Tài khoản nợ</TD>
									<TD class="plainlabel">
										<SELECT name = "taiKhoanNo" style="width: 300px" onChange = "submitform();">
											<option value = "" ></option>
											<% if (taiKhoanList != null)
											{
												for (Erp_Item item : taiKhoanList)
												{
													if (item.getValue().equals(obj.getTaiKhoanNo()))
													{
													%>
														<option value="<%=item.getValue()%>" selected="selected"><%=item.getName()%></option>
													<% } else { %>
														<option value="<%=item.getValue()%>"><%=item.getName()%></option>
											<% } }} %>
										</SELECT>
									</TD>
<!-- 								</TR> -->
								
<!-- 								<TR>	 -->
									<TD class="plainlabel">Tài khoản có</TD>
									<TD class="plainlabel">
										<SELECT name = "taiKhoanCo" style="width: 300px" onChange = "submitform();">
											<option value = "" ></option>
											<% if (taiKhoanList != null)
											{
												for (Erp_Item item : taiKhoanList)
												{
													if (item.getValue().equals(obj.getTaiKhoanCo()))
													{
													%>
														<option value="<%=item.getValue()%>" selected="selected"><%=item.getName()%></option>
													<% } else { %>
														<option value="<%=item.getValue()%>"><%=item.getName()%></option>
											<% } }} %>
										</SELECT>
									</TD>
								</TR>
								
								<TR>	
									<TD class="plainlabel">Công ty</TD>
									<TD class="plainlabel" colspan="3">
										<SELECT name = "congTy" style="width: 300px" onChange = "submitform();">
											<option value = "0" >All</option>
											<% if (congTyList != null)
											{
												for (Erp_Item item : congTyList)
												{
													if (item.getValue().equals(obj.getCongTy()))
													{
													%>
														<option value="<%=item.getValue()%>" selected="selected"><%=item.getName()%></option>
													<% } else { %>
														<option value="<%=item.getValue()%>"><%=item.getName()%></option>
											<% } }} %>
										</SELECT>
									</TD>
								</TR>
								
								<tr>
		                        	<td  class="plainlabel" colspan="4">
		                            	<a class="button" href="javascript:submitform()">
		                            	       <img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>	&nbsp;     &nbsp; &nbsp;
		                            	       
		                            	 <a class="button2" href="javascript:clearform()">
		                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;    &nbsp; &nbsp;
		                                
		                                <a class="button3" href="javascript:xuatExcel()"> 
		                                <img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a>
		                                                  
		                        	</td>
		                        	
                    			</tr> 
                    
								<TR>
									
								</TR>
									
							</TABLE>
						</div>
						
						
						<div style="width: 100%; float: none" align="left">
							<TABLE width="100%" cellpadding="0" cellspacing="1">
								
                                <TR class="tbheader" id=task_flyout>
                                	<TH width="5%" align="center">STT</TH>
                                	<TH width="15%" align="left">Mã chi nhánh nợ</TH>
                                    <TH width="7%" align="left">Mã chi nhánh có</TH>
                                    <TH width="7%" align="center">Loại chứng từ</TH>
                                    <TH width="5%" align="center">Số chứng từ</TH>
                                    <TH width="7%" align="center">Ngày ghi nhận</TH>
                      				<TH width="7%" align="center">Tài khoản nợ</TH>
                      				<TH width="7%" align="center">Tài khoản có</TH>
                                    <TH width="7%" align="center">Số tiền</TH>
                                    <TH width="3%" align="center">Tỉ giá</TH>
                                    <TH width="3%" align="center">Tiền tệ gốc</TH>
                                    <TH width="7%" align="center">Tiền ngoại tệ</TH>
                                    <TH width="5%" align="center">Đối tượng nợ</TH>
                                    <TH width="5%" align="center">Tên tượng nợ</TH>
                                    <TH width="5%" align="center">Đối tượng có</TH>
                                    <TH width="5%" align="center">Tên tượng có</TH>
                                </TR>
                                <TR class= "tblightrow">
                                	<TD align = "center" width="5%"> </TD>
                                	<TD align = "center" width="15%"> </TD>
                                	<TD align = "center" width="7%"> </TD>
                                	<TD align = "center" width="7%"> </TD>  
                                	<TD align = "center" width="5%"> </TD>
                                	<TD align = "center" width="7%"> </TD>                              	
                                	<TD align = "center" width="7%"> </TD>
                                	<TD align = "right" colspan="2" width="14%" ><input type="text" style = "border-width: 0px; text-align:right;width:120px"  id="sumnoht" name="sumnoht" value="" readonly="readonly"> </TD>
                                	<TD align = "right" colspan="2" width="6%"><input type="text"  style = "border-width: 0px; text-align:right;width:120px"  id="sumcoht" name="sumcoht" value="" readonly="readonly" > </TD>
                                	 <TD width="7%" align="center"></TD>
                                    <TD width="5%" align="center"></TD>
                                    <TD width="5%" align="center"></TD>
                                    <TD width="5%" align="center"></TD>
                                    <TD width="5%" align="center"></TD>
                                	
                                </TR>
                     <%	 
						double SUMNO=0;
                     	double SUMCO=0;
                     	if(viewList != null){
                    	 	NumberFormat formatter = new DecimalFormat("#,###,###"); 
                    	 	NumberFormat formatter1 = new DecimalFormat("#,###,###.##"); 
                            for (Erp_BangKeChungTuListItem item : viewList){
                            	/* double NO = Math.round(rs.getDouble("NO"));
                            	double CO =  Math.round(rs.getDouble("CO")); */
//                             	double NO = rs.getDouble("NO");
//                             	double CO = rs.getDouble("CO");
                            	double NO = item.getSoTien();
                            	double CO = item.getSoTien();
                            	SUMNO = SUMNO + NO;
                            	SUMCO = SUMCO + CO;
	                          if (item.getStt() % 2 != 0){	%>                     
                                <TR class= "tblightrow" >
                     <%		  } else {	%>
                                <TR class= "tbdarkrow"  >
                     <%		  }%>
                      		 	   		 
                          	 		<TD align = "center" width="5%"><%= item.getStt()%></TD>
                  	 				<TD align = "left" width="15%"><%=item.getMaChiNhanhNo()%></TD>
                              		<TD align = "left" width="7%"><%=item.getMaChiNhanhCo()%></TD>
                              		<TD align = "left" width="7%"><%=item.getLoaiChungTu() %></TD>
                          	 		<TD align = "left" width="5%"><%= item.getSoChungTu()%></TD>
                          	 		<TD align = "left" width="7%"><%= item.getNgayGhiNhan()%></TD>
                              		<TD align = "center" width="7%"><%=item.getTaiKhoanNo() %></TD>
                              		<TD align = "center" width="7%"><%=item.getTaiKhoanCo()%></TD>
                              		<TD align = "right" width="7%"><%=formatter.format(item.getSoTien())%></TD>
                              		<TD align = "right" width="3%"><%=formatter1.format(item.getTiGia())%></TD>
                              		<TD align = "right" width="3%"><%=item.getTienTeGoc()%></TD>
                              		<TD align = "right" width="7%"><%=formatter1.format(item.getTienNgoaiTe())%></TD>
                              		<TD align = "left" width="5%"><%=item.getDoiTuongNo()%></TD>
                              		<TD align = "left" width="5%"><%=item.getTenDoiTuongNo()%></TD>
                              		<TD align = "left" width="5%"><%=item.getDoiTuongCo()%></TD>
                              		<TD align = "left" width="5%"><%=item.getTenDoiTuongCo()%></TD>
                    <%
                    		} 
                     }
                    %>
               		<td><input type="hidden" name="sumno" id="sumno" value="<%=SUMNO  %>" ></td>
               		<td><input type="hidden" name="sumco" id="sumco" value="<%=SUMCO  %>" ></td>
								</TR>
								 <tr class="tbfooter">
							<TD align="center" valign="middle" colspan="16" class="tbfooter">
								<%if(obj.getNxtApprSplitting() >1) {%> <img alt="Trang Dau"
								src="../images/first.gif" style="cursor: pointer;"
								onclick="View('frm', 1, 'view')"> &nbsp; <%}else {%>
								<img alt="Trang Dau" src="../images/first.gif">
								&nbsp; <%} %> <% if(obj.getNxtApprSplitting() > 1){ %> <img
								alt="Trang Truoc" src="../images/prev.gif"
								style="cursor: pointer;"
								onclick="View('frm', eval(frm.nxtApprSplitting.value) -1, 'view')">
								&nbsp; <%}else{ %> <img alt="Trang Truoc"
								src="../images/prev_d.gif"> &nbsp; <%} %> 
								<%
													int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%> <% 
												
												System.out.println("Current page:" + obj.getNxtApprSplitting());
												System.out.println("List page:" + listPage[i]);
												
												if(listPage[i] == obj.getNxtApprSplitting()){ %> <a
															style="color: white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
															<%}else{ %> <a
															href="javascript:View('frm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
															<%} %> <input type="hidden" name="list"
															value="<%= listPage[i] %>" /> &nbsp; <%} %> <% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
															&nbsp; <img alt="Trang Tiep" src="../images/next.gif"
															style="cursor: pointer;"
															onclick="View('frm', eval(frm.nxtApprSplitting.value) +1, 'view')">
															&nbsp; <%}else{ %> &nbsp; <img alt="Trang Tiep"
															src="../images/next_d.gif"> &nbsp; <%} %> <%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
															<img alt="Trang Cuoi" src="../images/last.gif">
															&nbsp; <%}else{ %> <img alt="Trang Cuoi"
															src="../images/last.gif" style="cursor: pointer;"
															onclick="View('frm', -1, 'view')"> &nbsp; <%} %>
														</TD>
								</tr>
							</TABLE>
							<%-- <TABLE width="100%" cellpadding="4" cellspacing="1">
								<%     	NumberFormat formatter = new DecimalFormat("#,###,###");  %>
								<TR class= "tbheader" >
                        			<TD width="10%" align="center">Tổng</TD>  
                        			<TD width="10%" align="center"></TD> 
                        			<TD width="20%" align="center"></TD> 
                        			<TD width="10%" align="center"></TD> 
                        			<TD width="10%" align="center"></TD> 
                        			<TD width="10%" align="right" ><%= formatter.format(Double.parseDouble(obj.getTongNO()))%></TD> 
                        			<TD width="10%" align="right"><%= formatter.format(Double.parseDouble(obj.getTongCO())) %></TD> 
                        			<TD width="20%" align="center"></TD> 
                        		</TR>
							</TABLE> --%>
						</div>
					</div>
					</div>
			</div>
<script type="text/javascript">
	  TinhTongTien();
</script>
	</form>
</body>
</html>
<%
	try{
		session.setAttribute("obj", null);
		if(tk != null) tk.close();
		if(rs != null) rs.close();
	    if(ctyRs != null) ctyRs.close();
	
		nhomTaiKhoanList.clear();
		loaiNghiepVuList.clear();
		loaiDoiTuongList.clear();
		doiTuongList.clear();
		taiKhoanList.clear();
		congTyList.clear();
		viewList.clear();
	}catch (Exception e)
	{
		e.printStackTrace();			
	}
%>
<% if(obj!=null)obj.DBClose(); 
	session.setAttribute("obj",null);
	obj=null;%>