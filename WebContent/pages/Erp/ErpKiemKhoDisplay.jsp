<%@page import="geso.traphaco.erp.beans.kiemkho.imp.ErpKiemKho_SanPham"%>
<%@page import="geso.traphaco.erp.beans.kiemkho.IErpKiemKho_SanPham"%>
<%@page import="geso.traphaco.erp.beans.kiemkho.IErpKiemKho"%>
<%@page import="geso.traphaco.center.util.Utility"%>

<%@page import="java.sql.ResultSet"%>

<%@page import="java.util.*"%>
<%@page import="java.sql.ResultSet"%>

<%@page import="java.util.*"%>
<%
	IErpKiemKho bean = (IErpKiemKho) session.getAttribute("bean");
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	ResultSet rsKho = bean.getRsKhoTT();
	List<IErpKiemKho_SanPham> sanPhamKhoList = bean.getSanPhamKhoList();
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<head>
<TITLE>Diageo - Project</TITLE>
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
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/erp-SpListChuyenKho.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<script type="text/javascript">
	 
	function upload(){// nhan nut cap nhat
	
	   if(document.forms["StockForm"].filename.value==""){
		   
		   document.forms["StockForm"].dataerror.value="Chưa tìm file upload lên. Vui lòng chọn file cần upload.";
	   }else{
		 document.forms["StockForm"].setAttribute('enctype', "multipart/form-data", 0);
		 document.forms["StockForm"].submit();	
	   }
	
	}
	
	function Loadsanpham()
	{
		 document.forms['StockForm'].action.value='loadsanphamkho';
	     document.forms['StockForm'].submit();
	}
		 
	 
	 
	 
	 function SaveForm()
	 { 
		
			var msg = document.getElementById("msg");
			msg.value=CheckValid();
				if(msg.value==''){
				 document.forms['StockForm'].action.value='Update';
			     document.forms['StockForm'].submit();
				}else
				{
				return;
				}
	 }
	
	
	 function keypress(e) //Hàm dùng d? ngan ngu?i dùng nh?p các ký t? khác ký t? s? vào TextBox
		{    
			var keypressed = null;
			if (window.event)
				keypressed = window.event.keyCode; //IE
			else
				keypressed = e.which; //NON-IE, Standard
			
			if (keypressed < 48 || keypressed > 57)
			{ 
				if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46 || keypressed==45)
				{//Phím Delete và Phím Back
					return;
				}
				return false;
			}
		}
	 
	 function CheckValid() {
			
			

			var lydo = document.getElementById("lydodieuchinh");
			var kho=document.getElementById("khott_fk");
 
			return '';
		}
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" ">
	<form name="StockForm" method="post" action="" >
		<input type="hidden" name="userId" value='<%=userId%>'> <input
			type="hidden" name="action" value='1'> <input type="hidden"
			name="id" value='<%=bean.getID()%>'>
		<div id="main" style="width: 100%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 60%; padding: 5px; float: left"
					class="tbnavigation"> Chuỗi  cung ứng > Quản lý tồn kho > Kiểm kho > Hiển thị</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn <%=userTen%></div>
			</div>
			<div align="left" id="button"
				style="width: 100%; height: 32px; padding: 0px; float: none"
				class="tbdarkrow">
				<A href="../../ErpKiemKhoSvl?userId=<%=userId%>"> <img
					src="../images/Back30.png" alt="Quay về" title="Quay về"
					border="1px" longdesc="Quay ve" style="border-style: outset"></A>
				 
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Thông báo</legend>
					<textarea name="dataerror" rows="1" id="msg" style="width: 100%;color: red"><%=bean.getMessage()%></textarea>
				</fieldset>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle">Thông tin điều chỉnh tồn kho </legend>
					<div style="float: none; width: 100%" align="left">
						<table width="100%" cellpadding="4" cellspacing="0">
							
							 
						
							<tr>
								<td width="20%" class="plainlabel" valign="middle">Kho điều chỉnh</td>
								<td width="80%" align="left" class="plainlabel"><select
									name="khott_fk" onchange="Loadsanpham()" id="khott_fk">
										<option value=""></option>
										<%
											if (rsKho != null)
												while (rsKho.next())
												{
													if (rsKho.getString("PK_SEQ").equals(bean.getKhoTT_FK()))
													{
										%>
										<option value="<%=rsKho.getString("PK_SEQ")%>"
											selected="selected"><%=rsKho.getString("Ten")%></option>
										<%
											}
													else
													{
										%>
										<option value="<%=rsKho.getString("PK_SEQ")%>"><%=rsKho.getString("Ten")%></option>
										<%
											}
												}
										%>
								</select></td>
							</tr>
								<tr>
								<td width="20%" class="plainlabel" valign="middle">Diễn giải</td>
								<td width="80%" align="left" class="plainlabel"><input
									type="text" name="lydodieuchinh"
									value="<%=bean.getLyDoDieuChinh()%>"  id="lydodieuchinh" /></td>
							</tr>
							
							<tr class="plainlabel">
								<td width="20%" class="plainlabel" valign="middle">Ngày điều chỉnh </td>
								<td>
							 	<input class="days" onchange="Loadsanpham()" 	type="text" value="<%=bean.getNgayDieuChinh()%>" name="ngaydieuchinh"  id="ngaydieuchinh" />	
								</td>
							</tr>
							<tr class="plainlabel">
						  
						  	  <td colspan="2">
						  	  <INPUT type="file" name="filename" size="40" value=''> 
						  	  </td> 
						  	</tr>
						  	 
						</table>
					</div>
				</fieldset>
			</div>
			<!-- End div input Dieu chinh-->
			<div align="left" style="width: 100%; float: none; clear: none;">
				<TABLE class="tabledetail" width="100%" border="0" cellspacing="1">
					<TR class="tbheader">
						<TH align="center" width="5%">STT</TH>
						<TH align="center" width="15%">Mã sản phẩm</TH>
						<TH align="center" width="25%">Tên sản phẩm</TH>
						
						<TH align="center" width="15%"> Tồn hiện tại</TH>
						<TH align="center" width="15%"> Số lượng kiểm kho</TH>
						
					</TR>
					<%
						if (sanPhamKhoList.size() > 0 && sanPhamKhoList != null)
						{
							int size = sanPhamKhoList.size();
							int stt=0;
							for (int i = 0; i < size; i++)
							{
								IErpKiemKho_SanPham s = (ErpKiemKho_SanPham) sanPhamKhoList.get(i);
					%>
						<tr>
						<td>
							<input type="text" value="<%=++stt%>" name="stt" style="text-align: center" /> 
						<td>
							<input type="hidden" value="<%=s.getSanPham_FK()%>" name="SanPham_FK" />
							<input type="text" name="MaSP" value="<%=s.getMaSanPham()%>" readonly style="text-align: left" />
						</td>
						<td>
							<input type="text" name="TenSP" value="<%=s.getTenSanPham()%>" readonly style="text-align: left" />
						</td>
						<td>
							<input type="text" name="Tonkhocu" value="<%=s.getTonKhoCu()%>" readonly style="text-align: right" />
						</td>
						<td >
							<input type="text" name="Tonkhomoi" onkeypress="return keypress(event);"value="<%=s.getTonKhoMoi()%>"  style="text-align: right" />
						</td>
					</tr>
					<%
						}
						}
					%>
					<tr class="tbfooter">
						<td colspan="12">&nbsp;</td>
					</tr>
				</TABLE>
			</div>
			
		</div>

	</form>
	<script type="text/javascript" src="../scripts/loadingv2.js"></script>
	<%
	try{
		sanPhamKhoList.clear();
		rsKho.close();
	}catch(Exception er){
		er.printStackTrace();		
	}finally{
		session.setAttribute("bean", null) ;
		bean.close();
	}
}
	%>
</BODY>
</html>