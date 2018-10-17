<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.khachhang.IKhachhang" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "geso.traphaco.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<% IKhachhang khBean = (IKhachhang)session.getAttribute("khBean"); %>
<% ResultSet tp = (ResultSet)khBean.getTp() ;  %>
<% ResultSet qh = (ResultSet)khBean.getQh() ;  %>
<% ResultSet hch = (ResultSet)khBean.getHangcuahang(); %>
<% ResultSet kbh = (ResultSet)khBean.getKenhbanhang();  %>
<% ResultSet bgst = (ResultSet)khBean.getBgsieuthi();  %>
<% ResultSet vtch = (ResultSet)khBean.getVtcuahang();  %>
<% ResultSet lch = (ResultSet)khBean.getLoaicuahang(); %>
<% ResultSet nch = (ResultSet)khBean.getNhomcuahang();  %>
<% ResultSet mck = (ResultSet)khBean.getMucchietkhau();  %>
<% ResultSet nvgn = (ResultSet)khBean.getNvgnRs();  %>
<% ResultSet diadiemRs = (ResultSet)khBean.getDiadiemRs();  %>
<% ResultSet khoRs = (ResultSet)khBean.getKhoRs();%>

<% ResultSet nkh_khList = (ResultSet)khBean.getNkh_khList();  %>
<% Hashtable<Integer, String> nkh_khIds = (Hashtable<Integer, String>)khBean.getNkh_KhIds(); %>

<% ResultSet tbhRs = (ResultSet)khBean.getTbhRs()  ; %>
<% ResultSet ddkdRs = (ResultSet)khBean.getDdkdRs()  ; %>

<% ResultSet dtRs = (ResultSet)khBean.getDtRs()  ; %>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>TraphacoERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">

	<LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
    <script language="javascript" src="../scripts/datepicker.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>

	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
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
   

<SCRIPT language="javascript" type="text/javascript">

function submitform()
 {   
    document.forms["khForm"].submit();
 }

 function saveform()
 { 
	 
	 var khoId = document.getElementById("khoId");
	 if(khoId.value =='')
	 {
		 alert ('Vui lòng chọn KHO khách hàng ');
		 return;
	 }
	 
	 
	 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
		
 	 document.forms['khForm'].action.value='save';
     document.forms['khForm'].submit();
 }
 function showElement(id)
 {
 	var element = document.getElementById(id);
 	element.style.display = "";
 }

 function hideElement(id)
 {
 	var element = document.getElementById(id);
 	element.style.display = "none";
 }

 function SubmitAgain(){
	 var type= document.forms['khForm'].type.value;
	 if(type=="1")
	 {
		 if(!confirm('Địa chỉ khách hàng này đã bị trùng,Bạn muốn tiếp tục lưu khách hàng này?')) 
		 {
			 document.forms['khForm'].type.value="0";
			 return false;
		 }else
		 {
		 	saveform();
		 }
	 }
 }
 
</SCRIPT>
<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { 
    		$("select:not(.notuseselect2)").select2({ width: 'resolve' });     
    	});
    </script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="khForm" method="post" action="../../KhachhangUpdateTTSvl">
		<input type="hidden" name="userId" value='<%=userId %>'> 
		<input type="hidden" name="nppId" value='<%= khBean.getNppId() %>'>
		<input type="hidden" name="action" value='1'> 
		<input type="hidden" name="type" value='<%=khBean.gettype()%>'>
		<input type="hidden" name="id" value='<%=khBean.getId()%>'>

		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="2">
						<TR>
							<TD align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Thiết lập dữ liệu nền &gt; Dữ liệu nền kinh doanh &gt; Khách hàng &gt; Cập nhật
										<TD colspan="2" align="right" class="tbnavigation">Chào
											mừng <%= khBean.getNppTen() %>&nbsp;
									</tr>
								</table></TD>
						</TR>
					</TABLE>
					<TABLE width="100%" cellpadding="0" cellspacing="2">
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR class="tbdarkrow">
										<TD width="30" align="left"><A
											href="../../KhachhangSvl?userId=<%=userId %>"><img
												src="../images/Back30.png" alt="Quay ve" title="Quay ve"
												width="30" height="30" border="1" longdesc="Quay ve"
												style="border-style: outset">
										</A>
										</TD>
										<!-- <TD width="2" align="left"></TD>
										<TD width="30" align="left">
											<div id="btnSave">
												<A href="javascript:saveform()"><IMG
													src="../images/Save30.png" title="Luu lai" alt="Luu lai"
													border="1" style="border-style: outset">
												</A>
											</div></TD>
										<TD align="left">&nbsp;</TD> -->
									</TR>
								</TABLE></TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
									<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
									<textarea name="dataerror"
										style="width: 100%; color: #F00; font-weight: bold"
										style="width:100%" rows="1"><%= khBean.getMessage() %></textarea>
									<%khBean.setMessage(""); %>
								</FIELDSET></TD>
						</tr>

						<TR>
							<TD height="100%" width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle" style="color: black">Thông
										tin khách hàng</LEGEND>
									<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
										<TR>
											<TD width="130" class="plainlabel">Tên khách hàng<FONT
												class="erroralert"> *</FONT>
											</TD>
											<TD width="250" class="plainlabel"><INPUT type="text"
												name="khTen" id="khTen" value="<%= khBean.getTen() %>">
											</TD>
											<TD width="12%" class="plainlabel">Mã FAST<FONT
												class="erroralert"> *</FONT>
											</TD>
											<TD width="250" class="plainlabel"><INPUT type="text"
												name="maFAST" value="<%= khBean.getMaFAST() %>"></TD>
											<TD colspan="2" class="plainlabel">Hoạt động <%  if (khBean.getTrangthai().equals("1")){%>
												<input name="trangthai" type="checkbox" value="1"
												checked="checked"> <%} else {%> <input
												name="trangthai" type="checkbox" value="0"> <%} %>
											</TD>
										</TR>
										<TR>
											<TD class="plainlabel">Người mua hàng</TD>
											<TD class="plainlabel"><INPUT type="text"
												name="chucuahieu" id="chucuahieu"
												value="<%= khBean.getChucuahieu() %>" size="40">
											</TD>
											<TD class="plainlabel">Địa chỉ xuất HĐ<FONT
												class="erroralert"> *</FONT>
											</TD>
											<TD  class="plainlabel" ><INPUT type="text"
												name="diachi" id="diachi" value="<%= khBean.getDiachi() %>"
												size="40"> 
											</TD>
										
											
											<td class="plainlabel">
											
											<SELECT  name="cokhuyenmai" id="TP">
													<option value=""></option>
													<%  
								    				if(khBean.getCokhuyenmai().equals("1")){ %>
													<option value="1" selected> Có khuyến mại </option>
													<option value="0" > Không có khuyến mại </option>
													<%}else{ %>
													<option value="0" selected>Không có khuyến mại</option>
													<option value="1" >Có khuyến mại</option>
													<%} %>
											</SELECT>
											
											</td>
										</TR>
										<TR>
											<TD class="plainlabel">Tỉnh /Thành phố<FONT
												class="erroralert"> *</FONT>
											</TD>
											<TD class="plainlabel"><SELECT name="tpId" id="TP"
												onChange="submitform();">
													<option value=""></option>
													<% try{while(tp.next()){ 
								    	if(tp.getString("tpId").equals(khBean.getTpId())){ %>
													<option value='<%=tp.getString("tpId")%>' selected><%=tp.getString("tpTen") %></option>
													<%}else{ %>
													<option value='<%=tp.getString("tpId")%>'><%=tp.getString("tpTen") %></option>
													<%}}}catch(java.sql.SQLException e){} %>
											</SELECT></TD>

											<TD class="plainlabel">Quận/Huyện <FONT
												class="erroralert"> *</FONT>
											</TD>
											<TD colspan="3" class="plainlabel"><SELECT name="qhId"
												id="QH">
													<option value=""></option>
													<%if(qh != null){ 
								      		try{while(qh.next()){ 
								    			if(qh.getString("qhId").equals(khBean.getQhId())){ %>
													<option value='<%=qh.getString("qhId")%>' selected><%=qh.getString("qhTen") %></option>
													<%}else{ %>
													<option value='<%=qh.getString("qhId")%>'><%=qh.getString("qhTen") %></option>
													<%}}}catch(java.sql.SQLException e){} 
								     		
								      }	%>

											</SELECT></TD>

										</TR>


										<TR>
											<TD class="plainlabel">Điện thoại</TD>
											<TD class="plainlabel"><INPUT type="text"
												name="dienthoai" value="<%= khBean.getSodienthoai() %>"
												size="15">
											</TD>

											<TD class="plainlabel">Mã số thuế</TD>
											<TD colspan="3" class="plainlabel"><INPUT type="text"
												name="masothue" value="<%= khBean.getMasothue() %>"
												size="15">
											</TD>


										</TR>

										<TR>
											<TD class="plainlabel">Kênh bán hàng<FONT
												class="erroralert">*</FONT>
											</TD>
											<TD class="plainlabel"><SELECT name="kbhTen" id="kbhTen"
												onChange="submitform();">

													<% try{while(kbh.next()){ 
								    	if(kbh.getString("kbhId").equals(khBean.getKbhId())){ %>
													<option value='<%= kbh.getString("kbhId") %>' selected><%=kbh.getString("kbhTen") %></option>
													<%}else{ %>
													<option value='<%= kbh.getString("kbhId") %>'><%= kbh.getString("kbhTen") %></option>
													<%}}}catch(java.sql.SQLException e){} 
								     %>
											</SELECT>
											</TD>

											<TD class="plainlabel">Thanh toán Tháng</TD>
											<TD class="plainlabel" colspan="2"><SELECT
												name="thanhtoan" id="thanhtoan">
													<option value=""></option>
													<%	if(khBean.getThanhtoan().equals("0") ){ %>
													<option value='0' selected="selected">Tiền mặt</option>
													<option value='1'>Hóa đơn</option>
													<% }else { %>
													<option value='0'>Tiền mặt</option>
													<option value='1' selected="selected">Hóa đơn</option>
													<% } %>
											</SELECT> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Thanh toán Quý
												&nbsp;&nbsp; <SELECT name="thanhtoanQUY" id="thanhtoanQUY">
													<option value=""></option>
													<%	if(khBean.getThanhtoanQuy().equals("0") ){ %>
													<option value='0' selected="selected">Tiền mặt</option>
													<option value='1'>Hóa đơn</option>
													<% }else { %>
													<option value='0'>Tiền mặt</option>
													<option value='1' selected="selected">Hóa đơn</option>
													<% } %>
											</SELECT></TD>

										</TR>

										<TR>
											<TD class="plainlabel">Loại khách hàng</TD>
											<TD class="plainlabel"><SELECT name="xuatkhau">
													<option value=""></option>
													<% if(khBean.getXuatkhau().equals("0") ) { %>
													<option value="0" selected="selected">Bán lẻ</option>
													<% } else { %>
													<option value="0">Bán lẻ</option>
													<% } %>
													<% if(khBean.getXuatkhau().equals("1") ) { %>
													<option value="1" selected="selected">Bán buôn</option>
													<% } else { %>
													<option value="1">Bán buôn</option>
													<% } %>
													<% if(khBean.getXuatkhau().equals("2") ) { %>
													<option value="2" selected="selected">Bán buôn và
														bán lẻ</option>
													<% } else { %>
													<option value="2">Bán buôn và bán lẻ</option>
													<% } %>
													<%-- <% if(khBean.getXuatkhau().equals("3") ) { %>
													<option value="3" selected="selected">Bán lẻ và
														bán buôn</option>
													<% } else { %>
													<option value="3">Bán lẻ và bán buôn</option>
													<% } %> --%>

											</SELECT></TD>

									
											<TD class="plainlabel">Hợp đồng</TD>
											<TD class="plainlabel">
												<INPUT type="text" name="hopdong" value="<%= khBean.getHopdong() %>" size="15">

											</TD>
 											<TD class="plainlabel" > &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Ngày ký HĐ
												<INPUT type="text" name="ngaykyhd" value="<%= khBean.getNgaykyHd() %>"  class="days">
											</TD>


										</TR>
										
										<TR>
											<TD class="plainlabel">Nhân viên giao nhận<FONT
												class="erroralert">*</FONT>
											</TD>
											<TD class="plainlabel"><SELECT name="nvgnTen" id="nvgnTen">
													<OPTION value="" ></OPTION>
													<% try
													{
														while(nvgn.next())
														{ 
									    					if(khBean.getNvgnId().indexOf(nvgn.getString("nvgnId"))>=0){ %>
													<option value='<%= nvgn.getString("nvgnId") %>' selected><%=nvgn.getString("nvgnTen") %></option>
													<%}else{ %>
													<option value='<%= nvgn.getString("nvgnId") %>'><%= nvgn.getString("nvgnTen") %></option>
													<%}} nvgn.close(); }catch(java.sql.SQLException e){e.printStackTrace();} 
									     %>
											</SELECT></TD>
											
											<TD width="15%" class="plainlabel">Phần trăm chiết khấu</TD>
											<TD class="plainlabel" colspan="2"><input type="text"
												name="ptCHIETKHAU" value="<%= khBean.getPT_Chietkhau() %>"
												style="text-align: right;"> %
												&nbsp;&nbsp;&nbsp;&nbsp;
												Kho
												<SELECT name="khoId" id ="khoId" >
													<OPTION value="" selected></OPTION>
													<% try{while(khoRs.next()){ 
									    	if(khoRs.getString("PK_SEQ").equals(khBean.getkhoId())){ %>
													<option value='<%= khoRs.getString("PK_SEQ") %>' selected><%=khoRs.getString("Ten") %></option>
													<%}else{ %>
													<option value='<%= khoRs.getString("PK_SEQ") %>'><%= khoRs.getString("Ten") %></option>
													<%}} khoRs.close(); }catch(java.sql.SQLException e){} 
									     %>
											</SELECT>
												</TD>
										</TR>

										<TR>
											<TD class="plainlabel">Chi nhánh/ĐT</TD>
											<TD class="plainlabel">
											<SELECT name="dtId"  onChange="submitform();" >
													<OPTION value="" selected></OPTION>
													<% try{while(dtRs.next()){ 
									    	if(dtRs.getString("PK_SEQ").equals(khBean.getDtId()  )){ %>
													<option value='<%= dtRs.getString("PK_SEQ") %>' selected><%=dtRs.getString("Ten") %></option>
													<%}else{ %>
													<option value='<%= dtRs.getString("PK_SEQ") %>'><%= dtRs.getString("Ten") %></option>
													<%}} dtRs.close(); }catch(java.sql.SQLException e){} 
									     %>
											</SELECT></TD>
											

											<TD class="plainlabel">Trình dược viên</TD>
											<TD class="plainlabel" colspan="2">
											<SELECT  name="ddkdId" onChange="submitform();" multiple="multiple">
													<option value=""></option>
													<% try{ while(ddkdRs.next()){ 
									    			if( khBean.getDdkdId().indexOf(ddkdRs.getString("ddkdId")) >=0){ %>
													<option value='<%=ddkdRs.getString("ddkdId")%>' selected>
														<%=ddkdRs.getString("ddkdTen") %></option>
													<%}else{ %>
													<option value='<%=ddkdRs.getString("ddkdId")%>'>
														<%=ddkdRs.getString("ddkdTen") %></option>
													<%}}}catch(java.sql.SQLException e){} %>
											</SELECT>
											</TD>
										</TR>
										
										
										<TR>
											<TD class="plainlabel">Mã Số Thuế Cá Nhân</TD>
											<TD class="plainlabel" >
												<INPUT type="text" name="mst_canhan" value="<%= khBean.getMst() %>" size="15">
											</TD>


											<TD class="plainlabel">Ngày Sinh</TD>
											<TD class="plainlabel" colspan="2">
											<INPUT type="text" class="days" name="ngaysinh" value="<%= khBean.getNgaysinh() %>" >

											</TD>

										</TR>


										<tr>
											<TD class="plainlabel">Tên ký hợp đồng:	</TD>
											<TD class="plainlabel"><INPUT type="text"
												name="tenkyhd" id="tenkyhd" value="<%= khBean.getTenKyHd() %>"
												size="40">
											</TD>	
											<TD class="plainlabel">Hạng khách hàng</TD>
											<TD class="plainlabel"><SELECT name="hangcuahangId">
													<option value=""></OPTION>
													<% if(hch!=null) 
													while(hch.next())
													{
														if(khBean.getHchId()!=null)
														{
															if(khBean.getHchId().indexOf(hch.getString("hchId")) >= 0)
															{
																%>
																<option value="<%= hch.getString("hchId") %>" selected><%= hch.getString("hchTen") %></option>														
																<%
															}
															else
															{
																%>
																<option value="<%= hch.getString("hchId") %>"><%= hch.getString("hchTen") %></option>														
																<%
															}
														}
														else
														{
															%>
															<option value="<%= hch.getString("hchId") %>"><%= hch.getString("hchTen") %></option>														
															<%
														}

													}
													
													%>
											</SELECT></TD>
											<TD class="plainlabel" colspan="6"></TD>							

										</TR>
										<TR>
																					<% if(khBean.getKbhId().equals("100052")) {%>
											<TD class="plainlabel">Mẫu hóa đơn</TD>
											<TD class="plainlabel" colspan="10"><SELECT
												name="mauhoadon" id="mauhoadon">
													<% if (khBean.getmauhd().equals("1")){%>
													<option value="1" selected>Mẫu 1(CN)</option>
													<option value="2">Mẫu 2(HO)</option>
													<%}else if(khBean.getmauhd().equals("2")) {%>
													<option value="1" >Mẫu 1(CN)</option>
													<option value="2" selected>Mẫu 2(HO)</option>
													<%} else {%>
													<option value="1" >Mẫu 1(CN)</option>
													<option value="2">Mẫu 2(HO)</option>
													<%} %>
											</SELECT></TD>
											<%} else {%>
											<TD class="plainlabel" colspan="10"></TD>
											<%} %>
										</TR>
										
										<TR>
											<TD class="plainlabel">CMND</TD>
											<TD class="plainlabel"><INPUT type="text"
												name="cmnd" value="<%=khBean.getCmnd() %>"
												size="15">
											</TD>

											<TD class="plainlabel">Thời hạn nợ</TD>
											<TD class="plainlabel" >
												<INPUT type="text" name="thoihanno" value="<%=khBean.getThoihanno() %>" size="15">
											</TD>
											
										
											<TD class="plainlabel" >
											
												<INPUT type="checkbox" name="dungmau" value="1" size="15" <%if(khBean.getDungmau().equals("1")){ %> checked="checked" <%} %>  > Dùng mẫu HO
											</TD>
										</TR>
										
										<TR>
											<TD class="plainlabel">Tài khoản kế toán</TD>
											<TD class="plainlabel" colspan="4">
												<select name="soHieuTaiKhoan" id="soHieuTaiKhoan" style="width: 500px;">
													<option value=""></option>
													<% if (khBean.getTaiKhoanIdList() != null)
													{
														for (Erp_Item item : khBean.getTaiKhoanIdList())
														{
															if (item.getValue().equals(khBean.getSoHieuTaiKhoan()))
															{
															%>
																<option value="<%=item.getValue()%>" selected="selected"><%=item.getName()%></option>
															<% } else { %>
																<option value="<%=item.getValue()%>"><%=item.getName()%></option>
													<% } }} %>
												</select>
											</TD>
										</TR>
										
									</TABLE>
								</FIELDSET></TD>
						</TR>



					</TABLE>


					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR>
							<TD colspan="6" width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle">Tuyến bán hàng </LEGEND>
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="6">
										<TR class="tbheader">
											<TH width="10%">Số thứ tự</TH>
											<TH width="60%">Tuyến</TH>
											<TH width="20%">Tần số</TH>
											<TH width="10%">Chọn</TH>
										</TR>

										<%
								int i = 0;
								String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								if(tbhRs != null){
								try{while(tbhRs.next()){ 
									if (i % 2 != 0) {%>
										<TR class=<%=lightrow%>>
											<%} else {%>
										
										<TR class=<%= darkrow%>>
											<%}%>
											<TD align="center"><input name='sott' type="text"
												value="<%=tbhRs.getString("sott") %>" readonly="readonly"
												style="width: 100%">
											</TD>
											<TD align="center"><div align="left"><%= tbhRs.getString("tbhTen") %>
												</div>
											</TD>

											<TD align="center">
												<% 
								  		String[] tenloaicuahang = new  String[] {"F1-1","F1-2","F1-3","F1-4","F2-1","F2-2"} ; 
								  			String[] idloaicuahang = new  String[] {"F1-1","F1-2","F1-3","F1-4","F2-1","F2-2"}  ;
								  %> <select style="width: 130px" name="tanso">
													<option value=""></option>
													<% for( int j=0;j<idloaicuahang.length;j++) { 
								    			if(idloaicuahang[j].equals(tbhRs.getString("tanso" ))){ %>
													<option value='<%=idloaicuahang[j]%>' selected><%=tenloaicuahang[j] %></option>
													<%}else{ %>
													<option value='<%=idloaicuahang[j]%>'><%=tenloaicuahang[j] %></option>
													<%} 
								      		 }
								       	%>
											</select></TD>

											<% if (khBean.getTbhId()!=null&&khBean.getTbhId().contains(tbhRs.getString("tbhId"))){ %>
											<TD align="center"><input name="tbhId_<%=i %>"
												type="checkbox" value="<%= tbhRs.getString("tbhId") %>"
												checked>
											</TD>
											<%}else{%>
											<TD align="center"><input name="tbhId_<%=i %>"
												type="checkbox" value="<%= tbhRs.getString("tbhId") %>">
											</TD>
											<%}%>

										</TR>
										<%i++;}}catch(java.sql.SQLException e){e.printStackTrace();}
								}
							  %>
										<tr class="tbfooter">
											<td colspan="4">&nbsp;</td>
										</tr>
									</TABLE>

								</FIELDSET></TD>
						</TR>
					</TABLE>



					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR>
							<TD colspan="6" width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle">Phân nhóm </LEGEND>
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="6">
										<TR class="tbheader">
											<TH width="20%">Nhóm khách hàng</TH>
											<TH width="10%">Chọn</TH>
										</TR>

										<%
								 i = 0;
								 lightrow = "tblightrow";
								 darkrow = "tbdarkrow";
								if(nkh_khList != null)
								{
								try{while(nkh_khList.next())
								{ 
									if (i % 2 != 0) {%>
										<TR class=<%=lightrow%>>
											<%} else {%>
										
										<TR class=<%= darkrow%>>
											<%}%>
											<TD align="center"><div align="left"><%= nkh_khList.getString("nkhTen") %>
												</div>
											</TD>
											<% if (nkh_khIds.contains(nkh_khList.getString("nkhId"))){ %>
											<TD align="center"><input name="nkh_khList"
												type="checkbox" value="<%= nkh_khList.getString("nkhId") %>"
												checked>
											</TD>
											<%}else{%>
											<TD align="center"><input name="nkh_khList"
												type="checkbox" value="<%= nkh_khList.getString("nkhId") %>">
											</TD>
											<%}%>
										</TR>
										<%i++;}}catch(java.sql.SQLException e){}
								}
							  %>
										<tr class="tbfooter">
											<td colspan="4">&nbsp;</td>
										</tr>
									</TABLE>

								</FIELDSET></TD>
						</TR>
					</TABLE></TD>



			</TR>
		</TABLE>
	</form>
</BODY>
</HTML>

<% 	

if(khBean != null){
	khBean.DBclose();
	khBean = null;
}

	try{
	if(bgst != null)
		bgst.close();
	if(hch != null)
		hch.close();
	if(kbh != null)
		kbh.close();
	if(lch != null)
		lch.close();
	if(mck != null)
		mck.close();
	if(nkh_khList!= null)
		nkh_khList.close();
	if(tp != null)
		tp.close();
	if(qh != null)
		qh.close();
	if(vtch != null)
		vtch.close();
	if(nch!=null){
		nch.close();
	}
	if(nvgn!=null){
		nvgn.close();
	}
	if(nkh_khList!=null){
		nkh_khList.close();
	}
	if(nkh_khIds!=null){
		nkh_khIds.clear();
	}
	
	session.setAttribute("khBean",null);
	}
	catch (SQLException e) {}
	
%>
<%}%>
