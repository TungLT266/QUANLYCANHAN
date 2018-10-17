<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="geso.traphaco.erp.beans.tinhgiathanh.IErp_Tinhgiathanh"%>
<%@page import="geso.traphaco.erp.beans.kiemkho.imp.ErpKiemKho_SanPham"%>
<%@page import="geso.traphaco.erp.beans.kiemkho.IErpKiemKho_SanPham"%>
<%@page import="geso.traphaco.erp.beans.kiemkho.IErpKiemKho"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.ResultSetMetaData"%>
<%@page import="java.sql.Types"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.*"%>
<%
	IErp_Tinhgiathanh bean = (IErp_Tinhgiathanh) session.getAttribute("obj");
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
 
	ResultSet rsnhamay = bean.getRsNhaMay();

	NumberFormat formatter = new DecimalFormat("#,###,###");
	NumberFormat formatter3 = new DecimalFormat("#,###,###.###");
	
	ResultSet giathanhTPRs = bean.getGiathanhTP();
	ResultSet giathanhLSXRs = bean.getGiathanhLSX();
	ResultSet chitietRs = bean.getChitietRs();

	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/Canfoco/index.jsp");
	}else{
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
<TITLE>Newtoyo - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
<script type="text/javascript">
jQuery(function($){ 
	 $('.addspeech').speechbubble();})
</script>
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
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
<script>
//perform JavaScript after the document is scriptable.
$(document).ready(function() {

    //When page loads...
    $(".tab_content").hide(); //Hide all content
    var index = $("ul.tabs li.current").show().index(); 
    $(".tab_content").eq(0).show();

    //On Click Event
    $("ul.tabs li").click(function() {
    	
        $("ul.tabs li").removeClass("current"); //Remove any "active" class
        $(this).addClass("current"); //Add "active" class to selected tab
        $(".tab_content").hide(); //Hide all tab content  
        var activeTab = $(this).find("a").attr("href"); //Find the href attribute value to identify the active tab + content  
        $(activeTab).show(); //Fade in the active ID content
        return false;
    });

});
</script>

<script type="text/javascript">
$( function() {
	
	function groupTable($rows, startIndex, total)
	{
		if (total === 0)
		{
			return;
		}
		var i , currentIndex = startIndex, count=1, lst=[];
		var tds = $rows.find('td:eq('+ currentIndex +')');
		var ctrl = $(tds[0]);
		lst.push($rows[0]);
		for (i=1;i<=tds.length;i++){
		if (ctrl.text() ==  $(tds[i]).text()){
		count++;
		$(tds[i]).addClass('deleted');
		lst.push($rows[i]);
		}
		else{
			if (count>1){
			ctrl.attr('rowspan',count);
			groupTable($(lst),startIndex+1,total-1)
			}
			count=1;
			lst = [];
			ctrl=$(tds[i]);
			lst.push($rows[i]);
		}
		}
	}
	var rowCount = $('#tableb_tab2 tr').length;

	groupTable($('#tableb_tab2 tr:has(td)'),0,rowCount);
	$('#tableb_tab2 .deleted').remove();
	});
    </script>
    
    <script type="text/javascript">
$( function() {
	
	function groupTable($rows, startIndex, total)
	{
		if (total === 0)
		{
			return;
		}
		var i , currentIndex = startIndex, count=1, lst=[];
		var tds = $rows.find('td:eq('+ currentIndex +')');
		var ctrl = $(tds[0]);
		lst.push($rows[0]);
		for (i=1;i<=tds.length;i++){
		if (ctrl.text() ==  $(tds[i]).text()){
		count++;
		$(tds[i]).addClass('deleted');
		lst.push($rows[i]);
		}
		else{
			if (count>1){
			ctrl.attr('rowspan',count);
			groupTable($(lst),startIndex+1,total-1)
			}
			count=1;
			lst = [];
			ctrl=$(tds[i]);
			lst.push($rows[i]);
		}
		}
	}
	var rowCount = $('#table_tab512 tr').length;

	groupTable($('#table_tab512 tr:has(td)'),0,3);
	$('#table_tab512.deleted').remove();
	});
    </script>
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/erp-SpListChuyenKho.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>

<script type="text/javascript">

	 function TinhGiaThanh()
	 { 
		 document.forms['StockForm'].action.value='tinhgiathanh';
	     document.forms['StockForm'].submit();	
	 }
	 
	 function XuatExcel()
	 { 
		 document.forms['StockForm'].action.value='xuatexcel';
	     document.forms['StockForm'].submit();	
	 }
	 function Xem()
	 { 
		 document.forms['StockForm'].action.value='xem';
	     document.forms['StockForm'].submit();	
	 }
	 
	 function submitview()
	 { 
		 document.forms['StockForm'].action.value='submitview';
	     document.forms['StockForm'].submit();	
	 }
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" >
	<form name="StockForm" method="post" action="../../ErpTinhGiaThanhSvl" >
		<input type="hidden" name="userId" value='<%=userId%>'>
		<input type="hidden" name="action" value=''>
		<div id="main" style="width: 100%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 60%; padding: 5px; float: left"
					class="tbnavigation"> Quản lý tồn kho > Khóa sổ > Tính giá thành</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn <%=userTen%></div>
			</div>
			
			<br>
			
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Thông báo</legend>
					<textarea name="dataerror" rows="1" id="msg" style="width: 100%;color: red"><%=bean.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset style="float: none;height:100% ; width: 100%"  >
					<legend class="legendtitle">Tính giá thành </legend>
					<div style="float: none;height:100% ; width: 100%" align="left" >
						<table width="100%" cellpadding="4" cellspacing="0">
							
						<tr class="plainlabel">
								<td width="120px;" class="plainlabel" valign="middle">Năm </td>
								<td>
								<select name="nam" id="nam" onchange="submitview()"  style="width :70px" >
									<option value=0> </option>  
									<%
									  
									 Calendar c2 = Calendar.getInstance();
										int t=c2.get(Calendar.YEAR) + 5;
  										int n;
  										for(n=2016;n<t;n++){
  										if(bean.getNam().equals(""+n)){
  									%>
									<option value=<%=n%> selected="selected" > <%=n%></option> 
									<%
 										}else{
 											%>
 											<option value=<%=n%> > <%=n%></option> 
 											<%
 										}
 									 }
 									%>
 									</select>
								</td>
							</tr>
							<tr class="plainlabel">
								<td class="plainlabel" valign="middle"> Tháng </td>
								<td>
								<select name="thang"  onchange="submitview()" id="thang"  style="width :70px" >
									<option value=0> </option>  
									<%
  										int k=1;
  									for(k=1;k<=12;k++){
  										
  									  if(bean.getthang().equals(k+"")){
  									%>
									<option value=<%=k%> selected="selected" > <%=k%></option> 
									<%
 										}else{
 											%>
 											<option value=<%=k%> > <%=k%></option> 
 											<%
 										}
 									  }
 									%>
									</select>
								</td>
								</tr>
								
								<tr class="plainlabel">
								<td class="plainlabel" valign="middle">Phân xưởng </td>
								<td>
								<select name="nhamayid"  onchange="submitview()" id="nhamayid"  style="width :300px" >
									<option value=""> </option>  
									<%
									if(rsnhamay!=null){
	  										while (rsnhamay.next()){
	  										  if(rsnhamay.getString("pk_seq").trim().equals(bean.getNhaMayId().trim())){
			  									%>
												<option value=<%=rsnhamay.getString("pk_seq").trim()%> selected="selected" > <%=rsnhamay.getString("tennhamay").trim()%></option> 
												<%
	 											}else{
	 											%>
	 											<option value=<%=rsnhamay.getString("pk_seq").trim()%> > <%=rsnhamay.getString("tennhamay").trim()%></option>
	 											<%
	 											}
	 									  }
									}
 									%>
									</select>
								</td>
								</tr>
							
								<tr class="plainlabel">
								  	 <td colspan="2">
								  	 
								  		&nbsp;&nbsp;&nbsp;&nbsp; <a class="button2" href="javascript:TinhGiaThanh()">
										<img style="top: -4px;" src="../images/button.png" alt="">Tính giá thành</a>
										
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										 <a class="button2" href="javascript:Xem()">
										<img style="top: -4px;" src="../images/button.png" alt="">Xem Online</a>
										
										<!-- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										 <a class="button2" href="javascript:XuatExcel()">
										<img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel</a> -->
										
																
								  	</td>
						 	 	</tr>
						 	 	 
								<!-- Xem excel  -->
								 <% if(bean.getIsView() == 1){ 	%>
								<tr>
								<td colspan="2"> 
								 	<ul  class="tabs" id="tabnew">		
										<li class="current" > <a href="#sheet1">Theo mã sản phẩm</a></li>	 
										<li > <a href="#sheet2">Theo lệnh SX</a> 
									 
										<li > <a href="#sheet3">Chi tiết chi phí </a></li>
									 
						 			</ul>    
						 			  
				                  	<div class="panes">
				                  
									<!-- sheet1 excel -->
				                    <div id="sheet1" class ="tab_content" >
										 <TABLE  id="table_s1"  border="0" cellpadding="4"  cellspacing="2" width="100%">
											<TR class="tbheader" >
												<TH width="5%" align = "center" >STT </TH>
												<TH width="10%" align = "center" >Mã SP </TH>															
												<TH width="30%" align = "center" >Tên sản phẩm</TH>
												<TH width="10%" align = "center" >Đơn vị</TH>
												<TH width="10%" align = "center" >Số lượng SX </TH>
												<TH width="10%" align = "center" >Giá thành</TH>
												<TH width="15%" align = "center" >Tổng giá trị</TH>
											</TR>
											
											<% 
											String lightrow = "tblightrow";
											String darkrow = "tbdarkrow";
											if( giathanhTPRs != null ) { 
												int i = 0;
												String _class = "";
												while( giathanhTPRs.next() ) { 
												
												if( i % 2 == 0 )
													_class = " class = '" + lightrow + "' ";
												else
													_class = " class = '" + darkrow + "' ";
												%> 
												
												<Tr <%= _class %> >
													<td><%= i %></td>
													<td><%= giathanhTPRs.getString("ma") %></td>
													<td><%= giathanhTPRs.getString("ten") %></td>
													<td><%= giathanhTPRs.getString("donvi") %></td>
													<td style="text-align: right;" ><%= formatter3.format( giathanhTPRs.getDouble("soluong") ) %></td>
													<td style="text-align: right;"><%= formatter3.format( giathanhTPRs.getDouble("dongia") ) %></td>
													<td style="text-align: right;" ><%= formatter.format( giathanhTPRs.getDouble("tonggiatri") ) %></td>
												</Tr>
											
											<% i++; } giathanhTPRs.close();  } %>
											
										</TABLE>
								</div>
		
									<!-- sheet4 excel -->
				                    <div id="sheet2" class ="tab_content" >
										<TABLE  border="0" cellpadding="4"  cellspacing="2" width="100%">
										
											<TR class="tbheader" >
												<TH width="10%" align = "center" >Số lệnh </TH>
												<TH width="30%" align = "center" >Sản phẩm </TH>															
												<TH width="10%" align = "center" >Số lượng SX</TH>
												<TH width="10%" align = "center" >CP nguyên liệu</TH>
												<TH width="10%" align = "center" >CP nhân công</TH>
												<TH width="10%" align = "center" >CP khấu hao</TH>
												<TH width="10%" align = "center" >CP SX chung</TH>
												<TH width="10%" align = "center" >Tổng chi phí</TH>
											</TR>
										
											<% 
											if( giathanhLSXRs != null ) { 
												int i = 0;
												String _class = "";
												while( giathanhLSXRs.next() ) { 
												
												if( i % 2 == 0 )
													_class = " class = '" + lightrow + "' ";
												else
													_class = " class = '" + darkrow + "' ";
												%> 
												
												<Tr <%= _class %> >
													<td><%= giathanhLSXRs.getString("lenhsanxuat_fk") %></td>
													<td><%= giathanhLSXRs.getString("spTen") %></td>
													<td style="text-align: right;" ><%= formatter3.format( giathanhLSXRs.getDouble("soluong") ) %></td>
													<td style="text-align: right;"><%= formatter3.format( giathanhLSXRs.getDouble("chiphi_nguyenlieu") ) %></td>
													<td style="text-align: right;" ><%= formatter.format( giathanhLSXRs.getDouble("chiphi_nhancong") ) %></td>
													<td style="text-align: right;" ><%= formatter.format( giathanhLSXRs.getDouble("chiphi_khauhao") ) %></td>
													<td style="text-align: right;" ><%= formatter.format( giathanhLSXRs.getDouble("chiphi_sanxuatchung") ) %></td>
													<td style="text-align: right;" ><%= formatter.format( giathanhLSXRs.getDouble("tongchiphi") ) %></td>
												</Tr>
											
											<% i++; } giathanhLSXRs.close();  } %>
										
										</TABLE>
									</div>
								
								 	<!-- sheet16 excel -->
				                    <div id="sheet3" class ="tab_content"  >
										<TABLE  id="table_s1" border="0" cellpadding="4"  cellspacing="2" width="100%">
											
											<TR class="tbheader" >
												<TH width="10%" align = "center" rowspan="2" >Số lệnh </TH>
												<TH width="21%" align = "center" rowspan="2" >Sản phẩm </TH>															
												<TH width="10%" align = "center" rowspan="2"  >CP nguyên liệu</TH>
												<TH width="21%" align = "center" colspan="3" >CP nhân công</TH>
												<TH width="22%" align = "center" colspan="3" >CP khấu hao</TH>
												<TH width="22%" align = "center" colspan="3" >CP SX chung</TH>
											</TR>
											<TR class="tbheader" >
												<TH width="7%" align = "center" >Lương</TH>
												<TH width="7%" align = "center" >Thưởng</TH>
												<TH width="7%" align = "center" >Khác</TH>
												
												<TH width="8%" align = "center" >SX trực tiếp</TH>
												<TH width="7%" align = "center" >Phân xưởng</TH>
												<TH width="7%" align = "center" >Chung</TH>
												
												<TH width="8%" align = "center" >SX trực tiếp</TH>
												<TH width="7%" align = "center" >Phân xưởng</TH>
												<TH width="7%" align = "center" >Chung</TH>
											</TR>
											
											<% 
											if( chitietRs != null ) { 
												int i = 0;
												String _class = "";
												while( chitietRs.next() ) { 
												
												if( i % 2 == 0 )
													_class = " class = '" + lightrow + "' ";
												else
													_class = " class = '" + darkrow + "' ";
												%> 
												
												<Tr <%= _class %> >
													<td><%= chitietRs.getString("lenhsanxuat_fk") %></td>
													<td><%= chitietRs.getString("spTen") %></td>
				
													<td style="text-align: right;"><%= formatter3.format( chitietRs.getDouble("cp_nguyenlieu") ) %></td>
													
													<td style="text-align: right;" ><%= formatter3.format( chitietRs.getDouble("cp_nhancong_luong") ) %></td>
													<td style="text-align: right;" ><%= formatter3.format( chitietRs.getDouble("cp_nhancong_thuong") ) %></td>
													<td style="text-align: right;" ><%= formatter3.format( chitietRs.getDouble("cp_nhancong_khac") ) %></td>
													
													<td style="text-align: right;" ><%= formatter3.format( chitietRs.getDouble("cp_khauhao_tructiep") ) %></td>
													<td style="text-align: right;" ><%= formatter3.format( chitietRs.getDouble("cp_khauhao_phanxuong") ) %></td>
													<td style="text-align: right;" ><%= formatter3.format( chitietRs.getDouble("cp_khauhao_chung") ) %></td>
													
													<td style="text-align: right;" ><%= formatter3.format( chitietRs.getDouble("cp_sx_tructiep") ) %></td>
													<td style="text-align: right;" ><%= formatter3.format( chitietRs.getDouble("cp_sx_phanxuong") ) %></td>
													<td style="text-align: right;" ><%= formatter3.format( chitietRs.getDouble("cp_sx_chung") ) %></td>
													
												</Tr>
											
											<% i++; } chitietRs.close();  } %>
										
										</TABLE>
								</div>
								 
									</div>
							 </td>
							</tr>
							<%} %>
						</table>
					</div>
				</fieldset>
			</div>
			
		
		</div>

	</form>
	<%
	try{
		if(giathanhTPRs!=null){
			giathanhTPRs.close();
		}
		 
		if(rsnhamay!=null){
			rsnhamay.close();
		}
		 
		if(giathanhLSXRs != null){
			giathanhLSXRs.close();
		}
		
		if(chitietRs != null){
			chitietRs.close();
		}
		
		bean.DbClose();
		
	}catch(Exception er){
		
	}
	}
	%>
</BODY>
</html>