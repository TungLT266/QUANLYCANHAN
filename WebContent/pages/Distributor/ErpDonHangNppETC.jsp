<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.distributor.beans.hopdong.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<% IErpDonhangNppETCList obj = (IErpDonhangNppETCList)session.getAttribute("obj"); %>
<% ResultSet nhapkhoRs =  obj.getDondathangRs(); %>
<% ResultSet nppRs = obj.getKhRs(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<% obj.setNextSplittings();
	NumberFormat formatter = new DecimalFormat("#,###,###");

	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{
		int[] quyen = new  int[6];
		quyen = util.Getquyen("ErpDonhangNppETCSvl", "&loaidonhang=1",userId);
		
		ResultSet rsnvbanhang=obj.getRsnvbanhang();
		ResultSet kenhbanhang = obj.getKbhRs();
		ResultSet HTbanhang = obj.getHtbhRs();
		ResultSet khuvuc = obj.getKvRs();
		ResultSet rskhoid=obj.getRskhoid();
		ResultSet rscongty=obj.getRscongty();
		ResultSet rstien=obj.getRsTien();
		
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>OneOne - Project</title>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
    <script language="javascript" src="../scripts/datepicker.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
   	
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
  	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
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
   
  	<script type="text/javascript" src="..scripts/jquery-1.js"></script>
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
    </script>
    <SCRIPT language="javascript" type="text/javascript">
	 function submitform()
	 {   
	    document.forms["ckParkForm"].submit();
	 }
	 function newform()
	 {   
		document.forms["ckParkForm"].action.value = "Tao moi";
	    document.forms["ckParkForm"].submit();
	 }
	 function clearform()
	 {   
	    document.forms["ckParkForm"].tungay.value = "";
	    document.forms["ckParkForm"].denngay.value = "";
	    document.forms["ckParkForm"].trangthai.value = "";
	   // document.forms["ckParkForm"].khId.value = "";
	    document.forms["ckParkForm"].sodh.value = "";
	    document.forms["ckParkForm"].mafast.value = "";
	    document.forms["ckParkForm"].mahopdong.value = "";
	    document.forms["ckParkForm"].loaidonhang.value = "";
	    document.forms["ckParkForm"].nguoigiao.value = "";
	    document.forms["ckParkForm"].tensp.value = "";
	    document.forms["ckParkForm"].nguoitao.value = "";
	    document.forms["ckParkForm"].sodh.value = "";
	    document.forms["ckParkForm"].soHopDong.value = "";
	    document.forms["ckParkForm"].nvbanhang.value = "";
	    document.forms["ckParkForm"].maHopDong.value = "";
	    document.forms["ckParkForm"].submit();
	 }
	 function thongbao()
	 {
		 var tt = document.forms["ckParkForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["ckParkForm"].msg.value);
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
  	</style>
  	
	<!-- <script type="text/javascript" src="../scripts/jquery-latest.js"></script>  -->
   	<script type="text/javascript" src="../scripts/jquery.tablesorter.js"></script>
	
</head>
<body>
<form name="ckParkForm" method="post" action="../../ErpDonhangNppETCSvl">
<input type="hidden" name="userId" value="<%= userId %>"> 
<input type="hidden" name="action" value="1"> 
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'> 
<input type="hidden" name="nppId" value='<%= obj.getNppId() %>'> 
<%-- <input type="hidden" name="loaidonhang" value='<%= obj.getLoaidonhang() %>'> --%>
<input type="hidden" name="currentPage" value="<%= obj.getCurrentPage() %>">
<script language="javascript" type="text/javascript">
   thongbao();
</script>

		<div id="main" style="width: 100%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 55%; padding: 5px; float: left" class="tbnavigation">
					Quản lý bán hàng > Bán hàng > Đơn hàng bán
				</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng Bạn <%= userTen %>
					&nbsp;
				</div>
			</div>
			<div id="cotent" style="width: 100%; float: none">
				<div align="left"
					style="width: 100%; float: none; clear: left; font-size: 0.7em">
					<fieldset style="margin-top: 5px">
						<legend class="legendtitle"> Tiêu chí tìm kiếm</legend>
						<TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px">
							<TR>
								<TD class="plainlabel" width="100px">Từ ngày</TD>
								<TD class="plainlabel" width="250px"><input type="text"
									class="days" name="tungay" value="<%= obj.getTungay() %>"
									maxlength="10" onchange="submitform();" /></TD>

								<TD class="plainlabel" width="100px">Đến ngày</TD>
								<TD class="plainlabel"><input type="text" class="days"
									name="denngay" value="<%= obj.getDenngay() %>" maxlength="10"
									onchange="submitform();" /></TD>
									
								<TD class="plainlabel" width="100px">Ngày nợ</TD>
								<TD class="plainlabel"><input type="text" 
									name="ngayno" value="<%= obj.getNgayno() %>" maxlength="10"
									onchange="submitform();" /></TD>
									
								<TD class="plainlabel" width="100px">Ngày giao</TD>
								<TD class="plainlabel"><input type="text" class="days"
									name="ngaygiao" value="<%= obj.getNgaygiao() %>" maxlength="10"
									onchange="submitform();" /></TD>		
							</TR>
							<TR>
								
								
								<TD class="plainlabel" width="100px">Loại đơn hàng </TD>
								<TD class="plainlabel">
									<select name="loaidonhang"  id="loaidonhang" class="select2" style="width:200px" onchange="submitform();">
										<option value="">ALL</option>
										<% if(obj.getLoaidonhang().equals("0")) { %>
											<option value="0" selected="selected" >Bán cho ĐLPP</option>
										<% } else { %>
											<option value="0" >Bán cho ĐLPP</option>
										<% } %>
										<% if(obj.getLoaidonhang().equals("1")) { %>
											<option value="1" selected="selected" >ETC</option>
										<% } else { %>
											<option value="1" >ETC</option>
										<% } %>
										<% if(obj.getLoaidonhang().equals("2")) { %>
											<option value="2" selected="selected" >OTC</option>
										<% } else { %>
											<option value="2" >OTC</option>
										<% } %>
										<% if(obj.getLoaidonhang().equals("3")) { %>
											<option value="3" selected="selected" >Bán nội bộ</option>
										<% } else { %>
											<option value="3" >Bán nội bộ</option>
										<% } %>
									</select>
								</TD>

								<TD class="plainlabel" valign="middle">Trạng thái</TD>
								<TD class="plainlabel" valign="middle">
								<select name="trangthai" class="select2" style="width: 200px" onchange="submitform();">
									
										<%-- <% if (obj.getTrangthai().equals("0")){%>
										<option value="0" selected>Chưa chốt</option>
										<option value="1">Chờ duyệt</option>
										<option value="2">Đã duyệt</option>
										<option value="3">Đã hủy</option>
										<option value="4">Hoàn tất</option>
										<option value=""></option>
										<%}else if(obj.getTrangthai().equals("1")) {%>
										<option value="0">Chưa chốt</option>
										<option value="1" selected>Chờ duyệt</option>
										<option value="2">Đã duyệt</option>
										<option value="3">Đã hủy</option>
										<option value="4">Hoàn tất</option>
										<option value=""></option>
										<%}else if(obj.getTrangthai().equals("2")) {%>
										<option value="0">Chưa chốt</option>
										<option value="1">Chờ duyệt</option>
										<option value="2" selected>Đã duyệt</option>
										<option value="3">Đã hủy</option>
										<option value="4">Hoàn tất</option>
										<option value=""></option>
										<%}else if(obj.getTrangthai().equals("3")) {%>
										<option value="0">Chưa chốt</option>
										<option value="1">Chờ duyệt</option>
										<option value="2">Đã duyệt</option>
										<option value="3" selected>Đã hủy</option>
										<option value="4">Hoàn tất</option>
										<option value=""></option>
										<%}else if(obj.getTrangthai().equals("4")) {%>
										<option value="0">Chưa chốt</option>
										<option value="1">Chờ duyệt</option>
										<option value="2">Đã duyệt</option>
										<option value="3">Đã hủy</option>
										<option value="4" selected>Hoàn tất</option>
										<option value=""></option>
										<%} else  {%>
										<option value="0">Chưa chốt</option>
										<option value="1">Chờ duyệt</option>
										<option value="2">Đã duyệt</option>
										<option value="3">Đã hủy</option>
										<option value="4">Hoàn tất</option>
										<option value="" selected></option>
										<%} %> --%>
									<option value="">ALL</option>
									
									<% if( obj.getTrangthai().equals("0") ) { %>
										<option value= "0" selected="selected" >Mới tạo</option>
									<% } else { %>
										<option value= "0" >Mới tạo</option>
									<% } %>
									<% if( obj.getTrangthai().equals("3") ) { %>
										<option value= "3" selected="selected" >Chờ xử lý</option>
									<% } else { %>
										<option value= "3" >Chờ xử lý</option>
									<% } %>
									<% if( obj.getTrangthai().equals("1") ) { %>
										<option value= "1" selected="selected" >Đã duyệt SS</option>
									<% } else { %>
										<option value= "1" >Đã duyệt SS</option>
									<% } %>
									<% if( obj.getTrangthai().equals("2") ) { %>
										<option value= "2" selected="selected" >Đã duyệt CS</option>
									<% } else { %>
										<option value= "2" >Đã duyệt CS</option>
									<% } %>
									
									<%-- <% if( obj.getTrangthai().equals("6") ) { %>
										<option value= "6" selected="selected" >Chưa chốt HĐ</option>
									<% } else { %>
										<option value= "6" >Chưa chốt HĐ</option>
									<% } %> --%>
									<% if( obj.getTrangthai().equals("7") ) { %>
										<option value= "7" selected="selected" >Chưa in HĐ</option>
									<% } else { %>
										<option value= "7" >Chưa in HĐ</option>
									<% } %>
									<% if( obj.getTrangthai().equals("8") ) { %>
										<option value= "8" selected="selected" >Đã in HĐ</option>
									<% } else { %>
										<option value= "8" >Đã in HĐ</option>
									<% } %>
									
									<% if( obj.getTrangthai().equals("4") ) { %>
										<option value= "4" selected="selected" >Đã ra SXH</option>
									<% } else { %>
										<option value= "4" >Đã ra SXH</option>
									<% } %>
									
									<% if( obj.getTrangthai().equals("5") ) { %>
										<option value= "5" selected="selected" >Đã hủy</option>
									<% } else { %>
										<option value= "5" >Đã hủy</option>
									<% } %>
								</select>
							</TD>

							<TD class="plainlabel" width="100px">Người giao</TD>
															<TD class="plainlabel"><input type="text" 
																name="nguoigiao" value="<%= obj.getNguoigiao() %>" 
																onchange="submitform();" /></TD>
							<TD class="plainlabel" width="100px">Tên SP</TD>
															<TD class="plainlabel"><input type="text" 
																name="tensp" value="<%= obj.getTensp() %>" 
																onchange="submitform();" /></TD>
									
							</TR>
							<TR>
							<TD  class="plainlabel">Mã / tên đối tượng</TD>
							<TD  class="plainlabel" ><INPUT name="mafast" type="text" size="30" value = '<%= obj.getMafast()%>' onChange = "submitform();"></TD>
							<TD  class="plainlabel">Số hợp đồng</TD>
							<TD  class="plainlabel" ><INPUT name="soHopDong" type="text" size="30" value = '<%= obj.getSoHopDong()%>' onChange = "submitform();"></TD>
								
								<TD  class="plainlabel">NVBH</TD>
							<TD  class="plainlabel" >
						
							<select name="nvbanhang" class="select2" style="width: 200px" onchange="submitform();">
								<option value="">ALL</option>
									<%if(rsnvbanhang!=null)
										{ while (rsnvbanhang.next()){%>
										<option value="<%=rsnvbanhang.getString("pk_seq")%>" <%if(rsnvbanhang.getString("pk_Seq").equals(obj.getNvbanhang())){ %> selected="selected" <%} %>><%=rsnvbanhang.getString("ten") %></option>
										<%}} %>
							</select>
							
							</TD>
							
								<TD  class="plainlabel">Người tạo</TD>
							<TD  class="plainlabel" ><INPUT name="nguoitao" type="text" size="30" value = '<%=obj.getNguoitao() %>' onChange = "submitform();"></TD>
						
									
							</TR>
							<TR>													
							
							<TD  class="plainlabel">Mã chứng từ</TD>
							<TD  class="plainlabel"><INPUT name="sodh" type="text" size="30" value = '<%= obj.getSodh()%>' onChange = "submitform();"></TD>
							
							<TD  class="plainlabel">Mã hợp đồng</TD>
							<TD  class="plainlabel" ><INPUT name="maHopDong" type="text" size="30" value = '<%= obj.getMaHopDong()%>' onChange = "submitform();"></TD>
							
									<TD  class="plainlabel">Ghi chú</TD>
							<TD  class="plainlabel" ><INPUT name="ghichu" type="text" size="30" value = '<%= obj.getGhichu()%>' onChange = "submitform();"></TD>
							
								<TD  class="plainlabel">Kho hàng hóa</TD>
							<TD  class="plainlabel" >
							<select name="khohhid" class="select2"  style="width: 200px;" onchange="submitform();">
							
								<option value="">ALL</option>
								<%if(rskhoid!=null){ while (rskhoid.next()){ %>
								<option value="<%=rskhoid.getString("pk_seq")%>" <%if(rskhoid.getString("pk_seq").equals(obj.getKhohangid())){%> selected="selected" <%}%>><%=rskhoid.getString("ten") %>  </option>
								<%} }%>
								
							</select>
							
							</TD>
							</TR>
							<TR>
							
							<TD  class="plainlabel">Hệ thống bán hàng</TD>
							<TD  class="plainlabel" >
							<select name="htbhid" class="select2"  style="width: 200px;" onchange="submitform();">
							
								<option value="">ALL</option>
								<%if(HTbanhang!=null){ while (HTbanhang.next()){ %>
								<option value="<%=HTbanhang.getString("pk_seq")%>" <%if(HTbanhang.getString("pk_seq").equals(obj.getHtbhId())){%> selected="selected" <%}%>><%=HTbanhang.getString("diengiai") %>  </option>
								<%} }%>
							</select>
							</TD>
							
							
							<TD  class="plainlabel">Kênh</TD>
							<TD  class="plainlabel" >
							<select name="kbhid" class="select2"  style="width: 200px;" onchange="submitform();">
							
								<option value="">ALL</option>
								<%if(kenhbanhang!=null){ while (kenhbanhang.next()){ %>
								<option value="<%=kenhbanhang.getString("pk_seq")%>" <%if(kenhbanhang.getString("pk_seq").equals(obj.getKbhId())){%> selected="selected" <%}%>><%=kenhbanhang.getString("ten") %>  </option>
								<%} }%>
							</select>
							</TD>
							<TD  class="plainlabel">Khu vực</TD>
							<TD  class="plainlabel" colspan = "3">
							<select name="kvid" class="select2"  style="width: 200px;" onchange="submitform();">
							
								<option value="">ALL</option>
								<%if(khuvuc!=null){ while (khuvuc.next()){ %>
								<option value="<%=khuvuc.getString("pk_seq")%>" <%if(khuvuc.getString("pk_seq").equals(obj.getKvId())){%> selected="selected" <%}%>><%=khuvuc.getString("ten") %>  </option>
								<%} }%>
							</select>
							</TD>
							
							</TR>
							<TR>
								
							</TR>
							<%if(rstien!=null){ if(rstien.next()) {
								
								double doanhso = rstien.getDouble("doanhso");
								double chietkhau = rstien.getDouble("chietkhau");
								//double thuegtgt = rstien.getDouble("thuegtgt");
								//double tienthue = Math.round( ( doanhso - chietkhau ) * thuegtgt / 100.0 );
								//double doanhthu = doanhso - chietkhau + thuegtgt;
								double doanhthu = rstien.getDouble("doanhthu");
								
								%>
							<TR>

							<TD  class="plainlabel">Doanh số</TD>
							<TD  class="plainlabel" ><INPUT name="thanhtien" type="text" size="30" value = '<%=formatter.format( doanhso )%>' readonly="readonly" ></TD>
							
							<TD  class="plainlabel">Chiết khấu</TD>
							<TD  class="plainlabel" ><INPUT name="cksp" type="text" size="30" value = "<%=formatter.format( chietkhau )%>" readonly="readonly" ></TD>
								
							<%-- <TD  class="plainlabel">Thuế GTGT</TD>
							<TD  class="plainlabel" ><INPUT name="thuegtgt" type="text" size="30" value = '<%= formatter.format( thuegtgt )%>' readonly="readonly" ></TD> --%>
							
							<TD  class="plainlabel">Doanh thu</TD>
							<TD  class="plainlabel" colspan="3" ><INPUT name="tongtien" type="text" size="30" value = '<%=formatter.format( doanhthu )%>' readonly="readonly" ></TD>
						
							
							</TR>
							<%} rstien.close(); } %>
							<tr>
								<td colspan="4" class="plainlabel"><a class="button"
									href="javascript:submitform()"> <img style="top: -4px;"
										src="../images/Search30.png" alt="">Tìm kiếm
								</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a
									class="button2" href="javascript:clearform()"> 
										<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại
								</a>&nbsp;&nbsp;&nbsp;&nbsp;</td>
								<td class="plainlabel"></td>
								<td class="plainlabel"></td>
									<td class="plainlabel"></td>
									<td class="plainlabel"></td>
							</tr>
							
						</TABLE>
					</fieldset>
				</div>
				<div align="left"
					style="width: 100%; float: none; clear: left; font-size: 0.7em">
					<fieldset>
						<legend>
							<span class="legendtitle">Đơn hàng bán</span>&nbsp;&nbsp;
							
							<%if(quyen[Utility.THEM]!=0){ %>
			                	<a class="button3" href="javascript:newform()">
			                     <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
			                 <%} %>
						</legend>
						<TABLE id="table" class="tabledetail sortable" width="100%" border="0" cellspacing="1" cellpadding="4">
						<thead>
							<TR class="tbheader">
								<TH align="center" style="width: 12%">Mã chứng từ</TH>
								<TH align="center" style="width: 9%">Ngày đặt hàng</TH>
								<TH align="center" style="width: 1%; display:none; ">Ngày đề nghị</TH>
								<TH align="center" style="width: 8%">Số hợp đồng</TH>
								<TH align="center" style="width: 20%">Đối tượng</TH>
								<TH align="center" style="width: 10%">Trạng thái</TH>
								<TH align="center" style="width: 10%">Tổng thanh toán</TH>
								<TH align="center" style="width: 10%">Ngày sửa</TH>
								<TH align="center" style="width: 13%">Người sửa</TH>
								<TH align="center" style="width: 10%" class="nosort"  >Tác vụ</TH>
							</TR>
							</thead>
	                <tbody>
							<%
							List<ISanpham> lstSP = (List<ISanpham>) obj.getListSP();
							List<IDonhang> lstDH = (List<IDonhang>) obj.getListDH();
                 		if(nhapkhoRs != null)
                 		{
                 			int m = 0;
                 			while(nhapkhoRs.next())
                 			{  	
                 				String sohd = nhapkhoRs.getString("hopdong_fk").equals("-1") ? "" : nhapkhoRs.getString("hopdong_fk");
                 				String msg= nhapkhoRs.getString("tooltip")==null?"":nhapkhoRs.getString("tooltip");
                 				msg += nhapkhoRs.getString("tooltip_scheme")==null?"":nhapkhoRs.getString("tooltip_scheme") + "</table>";
                 				String chuoiFORMAT = "";
                 				
                 				//MÀU SẮC THEO CẤP ĐỘ GIAO HÀNG
								int CAPDOGIAOHANG = nhapkhoRs.getInt("CAPDOGIAOHANG");
                 				
                 				if(CAPDOGIAOHANG > 0 && CAPDOGIAOHANG <= 4)
									chuoiFORMAT = " style='color: red; font-weight: bold;'  ";
								else if(CAPDOGIAOHANG > 4 && CAPDOGIAOHANG <= 8)
									chuoiFORMAT = " style='color: orange; font-weight: bold; '  ";
								else if(CAPDOGIAOHANG > 8 && CAPDOGIAOHANG <= 24)
									chuoiFORMAT = " style='color: blue;'  ";
								
								String lydokhongduyet = nhapkhoRs.getString("lydokhongduyet") == null ? "" : nhapkhoRs.getString("lydokhongduyet");
									
                 				if((m % 2 ) == 0) {  
                 					
                 					/* if(nhapkhoRs.getString("NOTE").trim().length() > 0 && !nhapkhoRs.getString("NOTE").contains("Convert từ đề nghị") )
                 						chuoiFORMAT = " style='color: red;' onMouseover=\"ddrivetip('" + nhapkhoRs.getString("NOTE") + "', 250)\"; onMouseout='hideddrivetip()' "; */
                 				%>
							<TR class='tbdarkrow' <%= chuoiFORMAT %> >
								<%} else{ %>
							
							<TR class='tblightrow' <%= chuoiFORMAT %> >
								<%} %>
								<TD align="center" onMouseover='ddrivetip("<%= msg %>" , 700)' onMouseout="hideddrivetip()" >
									<%= nhapkhoRs.getString("machungtu") %></TD>
								<TD align="center"><%= nhapkhoRs.getString("ngaydonhang") %></TD>
								<TD style='display:none;' ><%= nhapkhoRs.getString("ngaydenghi") %></TD>
								<TD align="center"><%= nhapkhoRs.getString("hopdong_fk").equals("-1") ? "" : nhapkhoRs.getString("hopdong_fk")  %></TD>
								<TD><%= nhapkhoRs.getString("nppTEN") %></TD>
								<%-- <% if( lydokhongduyet.trim().length() > 0 ) { %>
									<TD align="center" onMouseover="ddrivetip('<%= lydokhongduyet %>' , 400)" onMouseout="hideddrivetip()" >
								<% } else { %> --%>
									<TD align="center"  >
								<%-- <% } %> --%>
									<%
									NumberFormat formater = new DecimalFormat("#,###,###");
			                    	String trangthai = "";
		                    		String tt = nhapkhoRs.getString("trangthai");
		                    		
		                    		//int daraPGH = nhapkhoRs.getInt("daraPGH");
		                    		int daraPGH = nhapkhoRs.getInt("daraSXH");
		                    		int daraHOADON = nhapkhoRs.getInt("daraHOADON");
		                    		int cs_duyet = nhapkhoRs.getInt("CS_DUYET");
		                    		int ss_duyet = nhapkhoRs.getInt("SS_DUYET");
		                    		int mohoadon = nhapkhoRs.getInt("mohoadon");
		                    		
		                    		if( tt.equals("3") )
		                    			trangthai = "Đã hủy";
		                    		else if( daraPGH > 0 )
		                    			trangthai = "Đã ra SXH";
		                    		else if( daraHOADON > 0 )
		                    		{
		                    			if( daraHOADON == 1 )
		                    				trangthai = "Chưa in hóa đơn";
		                    			else
		                    				trangthai = "Đã in hóa đơn";
		                    		}
		                    		else if ( cs_duyet > 0 )
		                    			trangthai = "Đã duyệt CS";
		                    		else if ( ss_duyet > 0 )
		                    			trangthai = "Đã duyệt SS";
		                    		else if( tt.equals("0") )
		                    		{
		                    			if( mohoadon <= 0 )
		                    				trangthai = "Mới tạo";
		                    			else
		                    				trangthai = "Chờ xử lý";
		                    		}
		                    		else
		                    			trangthai = "NA";
		                    		
	                 				/* if( lydokhongduyet.trim().length() > 0 )
	                 				{
	                 					trangthai = " <i style='color: red;' >SS không duyệt</i> ";
	                 				} */
		                    		
		                    		/* if(tt.equals("0")) //NPP TAO
		                    			trangthai = "Chưa chốt";
		                    		else
		                    		{
		                    			if(tt.equals("1"))
		                    			{
		                    				trangthai = "Chờ duyệt";
		                    			}
		                    			else if(tt.equals("2"))
		                    			{
			                    			trangthai = "Đã duyệt";
		                    			}
		                    			else if(tt.equals("3"))
		                    			{
		                    				trangthai = "Đã hủy";
	                    				}
		                    			else if(tt.equals("4"))
		                    			{
		                    				trangthai = "Hoàn tất";
	                    				}
		                    		} */
		                    	%> <%= trangthai %>
								</TD>
								<%-- <TD align="center"><%= nhapkhoRs.getString("NGAYTAO") %></TD>	
		                    	<TD align="center"><%= nhapkhoRs.getString("NGUOITAO") %></TD> --%>
		                    	
		                    	<TD align="right"><%= formater.format( nhapkhoRs.getDouble("doanhthu") ) %></TD>
								<TD align="center"><%= nhapkhoRs.getString("NGAYSUA") %></TD>
								<TD align="center"><%= nhapkhoRs.getString("NGUOISUA") %></TD>

								<TD align="center">
									<% if(tt.equals("0")){ %> 
										<%if(quyen[Utility.SUA]!=0){ %> 
											<A href="../../ErpDonhangNppETCUpdateSvl?userId=<%=userId%>&update=<%=nhapkhoRs.getString("PK_SEQ") %>">
												<IMG src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" border=0></A>&nbsp; <% } else { %>
											
											<%if(quyen[Utility.XEM]!=0){ %> 
												<A href="../../ErpDonhangNppETCUpdateSvl?userId=<%=userId%>&display=<%= nhapkhoRs.getString("PK_SEQ") %>">
													<IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A> 
											<% } %> 
										
										<% } %> 
										<%-- <%if(quyen[Utility.CHOT]!=0){ %> 
											<A href="../../ErpDonhangNppETCSvl?userId=<%=userId%>&chot=<%= nhapkhoRs.getString("PK_SEQ") %>&loaidonhang=<%= obj.getLoaidonhang() %>">
												<img src="../images/Chot.png" alt="Chốt" title="Chốt" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn chốt đơn đặt hàng này?')) return false;"></A>&nbsp; <% } %>  --%>
										
										<%if(quyen[Utility.XOA]!=0){ %> 
											<A href="../../ErpDonhangNppETCSvl?userId=<%=userId%>&delete=<%= nhapkhoRs.getString("PK_SEQ") %>&loaidonhang=<%= obj.getLoaidonhang() %>">
												<img src="../images/Delete20.png" alt="Xóa" title="Xóa" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn xóa đơn đặt hàng này?')) return false;"></A> <% } %> 
									<%} else{ %> 
										<%-- <%if(quyen[Utility.CHOT]!=0 && tt.equals("1") ){ %> 
											<A href="../../ErpDonhangNppETCSvl?userId=<%=userId%>&unchot=<%= nhapkhoRs.getString("PK_SEQ") %>&loaidonhang=<%= obj.getLoaidonhang() %>">
												<img src="../images/unChot.png" alt="Mở Chốt" title="Mở Chốt" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn mở chốt đơn đặt hàng này?')) return false;"></A>&nbsp; <% } %>  --%>
												
										<%if(quyen[Utility.XEM]!=0){ %> 
											<A href="../../ErpDonhangNppETCUpdateSvl?userId=<%=userId%>&display=<%= nhapkhoRs.getString("PK_SEQ") %>">
												<IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A> 
										<% } %> 
									<% } %>
								</TD>
							</TR>
							<% m++; } nhapkhoRs.close(); } %>
							</tbody>
                     <tfoot>
							<tr class="tbfooter">
								<TD align="center" valign="middle" colspan="13" class="tbfooter">
									<% obj.setNextSplittings(); %> <script type="text/javascript"
										src="../scripts/phanTrang.js"></script> <input type="hidden"
									name="crrApprSplitting"
									value="<%= obj.getCrrApprSplitting() %>"> <input
									type="hidden" name="nxtApprSplitting"
									value="<%= obj.getNxtApprSplitting() %>"> <%if(obj.getNxtApprSplitting() >1) {%>
									<img alt="Trang Dau" src="../images/first.gif"
									style="cursor: pointer;"
									onclick="View(document.forms[0].name, 1, 'view')"> &nbsp;
									<%}else {%> <img alt="Trang Dau" src="../images/first.gif">
									&nbsp; <%} %> <% if(obj.getNxtApprSplitting() > 1){ %> <img
									alt="Trang Truoc" src="../images/prev.gif"
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
									<img alt="Trang Cuoi" src="../images/last.gif"> &nbsp; <%}else{ %>
									<img alt="Trang Cuoi" src="../images/last.gif"
									style="cursor: pointer;"
									onclick="View(document.forms[0].name, -1, 'view')">
									&nbsp; <%} %>
								</TD>
							</tr>
</tfoot>
						</TABLE>
					</fieldset>
				</div>
			</div>
		</div>
	</form>
	<script type="text/javascript" src="../scripts/script-table-sorter.js"></script>
	<script type="text/javascript">
		var sorter = new TINY.table.sorter("sorter");
		sorter.head = "head";
		//sorter.asc = 'asc'; //ascending header class name
		sorter.desc = 'desc'; //descending header class name
		sorter.even = "tblightrow";
		sorter.odd = "tbdarkrow";
		sorter.evensel = "evenselected";
		sorter.oddsel = "oddselected";
		sorter.paginate = false;
		sorter.currentid = "currentpage";
		sorter.limitid = "pagelimit";
		sorter.init("table", 100);
	</script> 
</body>
</html><%
	if(rsnvbanhang!=null)
		rsnvbanhang.close();
if(kenhbanhang!=null)
	kenhbanhang.close();
if(rsnvbanhang!=null)
	rsnvbanhang.close();
if(khuvuc!=null)
	khuvuc.close();
	
if(rstien!=null)
	rstien.close();
	}%>