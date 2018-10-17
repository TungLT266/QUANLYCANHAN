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
	ResultSet rsLoaisanpham = bean.getRsLoaisanpham();
	ResultSet rsMalonsanpham = bean.getRsMalonsanpham();
	
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


<script language="javascript" src="../scripts/datepicker.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
<script type="text/javascript" src="../scripts/phanTrang.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>

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
        }); 		
		
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


	 function ExportExcel()
	 { 
		 var kho=document.getElementById("khott_fk");
			 if (kho.value == "") {
				kho.focus();
				alert( 'Vui lòng chọn kho để in ');
				return;
			}
		 
				 document.forms['StockForm'].action.value='excel';
			     document.forms['StockForm'].submit();	
	 }
	
	 function ExportPdf()
	 {  var kho=document.getElementById("khott_fk");
		 if (kho.value == "") {
				kho.focus();
				alert( 'Vui lòng chọn kho để in ');
				return;
			}
				 document.forms['StockForm'].action.value='ExportPdf';
			     document.forms['StockForm'].submit();	
	 }
	 
	 function Loadsanpham()
	 {
	     document.forms['StockForm'].submit();	
	 }
	
	 
	
</script>

	</SCRIPT>
	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
	     $(document).ready(function() { $("select").select2(); });
	     
	 </script>

</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" ">
	<form name="StockForm" method="post" action="../../ErpKiemKhoExportSvl" >
		<input type="hidden" name="userId" value='<%=userId%>'> <input
			type="hidden" name="action" value='1'> <input type="hidden"
			name="id" value='<%=bean.getID()%>'>
		<div id="main" style="width: 100%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 60%; padding: 5px; float: left"
					class="tbnavigation"> Chuỗi  cung ứng > Quản lý tồn kho > In bảng kê tồn kho</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn <%=userTen%></div>
			</div>
			<div align="left" id="button"
				style="width: 100%; height: 32px; padding: 0px; float: none"
				class="tbdarkrow">
				
				<span > <A href="javascript:ExportPdf()"> <IMG
						src="../images/Pdf30.jpg" title="Xuất PDF" alt="Xuất PDF"
						border="1px" style="border-style: outset"></A>
				</span>
				<span > <A href="javascript:ExportExcel()"> <IMG width="30px" height="30px"
						src="../images/excel.gif" title="Xuất Excel" alt="Xuất Excel"
						border="1px" style="border-style: outset"></A>
				</span>
			</div>
			<br>
			
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Thông báo</legend>
					<textarea name="dataerror" rows="1" id="msg" style="width: 100%;color: red"><%=bean.getMessage()%></textarea>
				</fieldset>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle">Thông tin kiểm kho </legend>
					<div style="float: none; width: 100%" align="left">
						<table width="100%" cellpadding="4" cellspacing="0">
							
					 <TR>
                        <TD class="plainlabel" >Từ ngày </TD>
                        <TD class="plainlabel" >
                            <input type="text" class="days" 
                                   id="tungay" name="tungay" value="<%= bean.getTungay()%>" maxlength="10" />
                        </TD>
                      </TR>
                      <TR>  
                         <TD class="plainlabel"  >Đến ngày </TD>
                        <TD class="plainlabel" >
                            <input type="text" class="days" 
                                   id="denngay" name="denngay" value="<%= bean.getDenngay() %>" maxlength="10" />
                        </TD>
                    	</TR>
							
							
							
							<tr>
								<td width="20%" class="plainlabel" valign="middle">Kho kiểm</td>
								<td width="80%" align="left" class="plainlabel">
								<select style="width: 200px"
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
								<td width="20%" class="plainlabel" valign="middle">Loại sản phẩm</td>
								<td width="80%" align="left" class="plainlabel">
								<select style="width: 200px"
									name="loaisanpham" onchange="Loadsanpham()" id="loaisanpham">
										<option value=""></option>
										<%
											if (rsLoaisanpham != null)
												while (rsLoaisanpham.next())
												{
													if (rsLoaisanpham.getString("PK_SEQ").equals(bean.getLoaisanpham()))
													{
													%>
													<option value="<%=rsLoaisanpham.getString("PK_SEQ")%>"
														selected="selected"><%=rsLoaisanpham.getString("Ten")%></option>
													<%
													}
													else
													{
													%>
													<option value="<%=rsLoaisanpham.getString("PK_SEQ")%>"><%=rsLoaisanpham.getString("Ten")%></option>
													<%
													}
												}
										%>
								</select></td>
							</tr>
							
						<%-- 	<tr>
								<td width="20%" class="plainlabel" valign="middle">Mã lớn sản phẩm</td>
								<td width="80%" align="left" class="plainlabel">
								<select style="width: 200px"
									name="malonsanpham" onchange="Loadsanpham()" id="malonsanpham">
										<option value=""></option>
										<%
											if (rsMalonsanpham != null)
												while (rsMalonsanpham.next())
												{
													if (rsMalonsanpham.getString("MA").equals(bean.getMalonsanpham()))
													{
													%>
													<option value="<%=rsMalonsanpham.getString("MA")%>"
														selected="selected"><%=rsMalonsanpham.getString("MA")%></option>
													<%
													}
													else
													{
													%>
													<option value="<%=rsMalonsanpham.getString("MA")%>"><%=rsMalonsanpham.getString("MA")%></option>
													<%
													}
												}
										%>
								</select></td>
							</tr> --%>
							<%-- 
							<tr>
							<td width="20%" class="plainlabel" valign="middle">Lấy số lượng lớn hơn 0</td>
							<td width="80%" align="left" class="plainlabel">
							<% if(bean.getTicksoluong().equals("1")) { %>
								 <input type="checkbox" id="ticksoluong" name="ticksoluong" value="0" checked="checked" onchange="changeType();" > 
							<% } else { %>
								  <input type="checkbox" id="ticksoluong" name="ticksoluong" value="0" onchange="changeType();" /> 
							<% } %>
							</td> 
							</tr>	 --%>
								
						</table>
					</div>
				</fieldset>
			</div>
			
		
		</div>

	</form>
<%
try{
	
	rsKho.close();
	rsLoaisanpham.close();
	rsMalonsanpham.close();
	
}catch(Exception er){
	er.printStackTrace();	
}finally{
	bean.close();
	session.setAttribute("bean", null) ; 
}
}
%>
</BODY>
</html>