<%@page import="geso.traphaco.distributor.beans.huynhaphang.IHuynhaphang"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.distributor.beans.huynhaphang.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.text.DateFormat" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.text.SimpleDateFormat" %>
<% IHuynhaphang dhtvBean = (IHuynhaphang)session.getAttribute("dhtvBean"); %>
<% List<ISanpham> splist = (List<ISanpham>)dhtvBean.getSpList(); %>

<% String userId = (String) session.getAttribute("userId");  %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
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


<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
<script type="text/javascript">
	jQuery(function($){ 
		 $('.addspeech').speechbubble();})
</script>
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/ajax-dynamic-list-sptrave.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>

<script language="javascript" type="text/javascript" >

	
	
	
	 	
</script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="dhtvForm" method="post" action="../../HuynhaphangUpdateSvl">
<input type="hidden" name="userId" value='<%=userId %>'>
<input type="hidden" name="nppId" value='<%= dhtvBean.getNppId() %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="chot_dh" value='false'>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFEE"><!--begin body Dossier-->
				<TABLE border =0 width = "100%" cellpadding="2" cellspacing="0">
				<TBODY>
					<TR height="22">
						<TD align="left" >
							<TABLE width="100%" cellpadding="0" cellspacing="0">
								<TR>
									<TD align="left">
									   <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										   <TR height="22">
			 								  <TD align="left"  class="tbnavigation">&nbsp;Quản lý tồn kho > Mua hàng từ nhà cung cấp  > Hủy nhập hàng > Hiển thị</TD>								   
			 								   <TD align="right" class="tbnavigation">Chào mừng  <%= dhtvBean.getNppTen() %> &nbsp; </TD>
					    				 </TR>
									  </TABLE>
								  </TD>
							  </TR>	
						  	</TABLE>
							<TABLE width="100%" border="0" cellpadding="1" cellspacing="0">
								<TR ><TD >
									<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										<TR class = "tbdarkrow">
											<TD width="30" align="left"><A href = "../../HuynhaphangSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
										    <TD width="2" align="left" >&nbsp;</TD>
										    <TD width="30" align="left" >
										    
										    	</TD>	
										    <TD align="left" >&nbsp;</TD>				    		
										</TR>
									</TABLE>
								</TD></TR>
							</TABLE>												
							<TABLE border="0" width="100%" cellpadding = "0" cellspacing = "0">
								<tr>
								<TD align="left" colspan="4" class="legendtitle">
									<FIELDSET>
									<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>			
				    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold"  style="width:100%" readonly="readonly" rows="1"><%= dhtvBean.getMessage() %></textarea>
									</FIELDSET>
							   </TD>
								</tr>
								<TR>
									<TD  align="left">						
										<FIELDSET>
										<LEGEND class="legendtitle">&nbsp;Đơn hàng trả về </LEGEND>
										<TABLE cellpadding = "6" cellspacing = "0" width = "100%" border = 0>
										   
                                        	
										
											
											
											<TR class="tblightrow">
											    <TD  class="plainlabel">Nhập số hóa đơn hủy</TD>	     
										        <TD colspan="3"  class="plainlabel"><input name="Sochungtu" id="Sochungtu" type="text" value="<%=dhtvBean.getSochungtu()%>" style="text-align: right;"  >  </TD>
									       	</TR>

										    <TR class="tblightrow">
											    <TD  class="plainlabel">Ngày hóa đơn</TD>	     
										        <TD colspan="3"  class="plainlabel"><input name="Ngaychungtu" id="Ngaychungtu" type="text" value="<%=dhtvBean.getNgaychungtu()%>" style="text-align: right;" readonly >  </TD>
									       	</TR>

										 																  
										</TABLE>
										
									</FIELDSET>
								  </TD>
							   </TR>	
							   <TR>
							   		<TD>
										<TABLE width = "100%"  border="0" cellpadding="0" cellspacing="1">
										<tbody id="san_pham">
												<TR class="tbheader" >
													<TH width="15%" height="20">Mã sản phẩm </TH>
													<TH width="28%">Tên sản phẩm </TH>
													<TH width="5%">Số lượng </TH>
													<TH width="10%">Số lô </TH>
													<TH width="10%">Ngày hết hạn </TH>
													<TH width="7%">DVT</TH>
													<TH width="10%">Trạng thái </TH>
												</TR>
								<% 
								
								ISanpham sanpham = null;
								int size = splist.size();
								int m = 0;
								String trangthai="Chưa xuất kho";
								while (m < size){
								sanpham = splist.get(m); 
								trangthai="Chưa xuất kho";
								
								if(sanpham.getISXUATKHO().trim().equals("1")){
									trangthai="Đã xuất kho";
								}
							
								 %>										
								    <TR class= 'tblightrow' >							
									<TD align="left" >
										<input name="masp" type="text"  value="<%=sanpham.getMasanpham()%>" readonly="readonly" style="width:100% ;text-align: left;">
									</TD>
									<TD align="left" >
										<input name="tensp" type="text" readonly value="<%=sanpham.getTensanpham()%>"  readonly="readonly" style="width:100% ;text-align: left;"></TD>							
						        	
							        	<TD align = "center" >
								        <input name="soluong" type="text" value="<%= sanpham.getSoluong() %>" readonly="readonly" style="width:100%; text-align:left;">
								        </TD>
								<TD align = "center" >
								        <input name="soluong" type="text" value="<%= sanpham.getSoLo() %>"   readonly="readonly" style="width:100%; text-align:left;">
								        </TD>
								    <TD align = "center" >
								        <input name="soluong" type="text" value="<%= sanpham.getNgayhethan() %>"   readonly="readonly" style="width:100%; text-align:left;">
								        </TD>
								    
								    <TD align = "center" ><input name="donvitinh" type="text" value="<%= sanpham.getDonvitinh() %>" readonly style="width:100% ;text-align: left;"></TD>
								       <TD align = "center" ><input name="donvitinh" type="text" value="<%=trangthai %>" readonly style="width:100% ;text-align: left;"></TD>
								   
								</TR>
								<% m++; }%>																																																																																																					
								</tbody>
								</TABLE>
							
								</TD>
							  </TR>	
							 		   
						  </TABLE>
						</TD>
					</TR>	
					
				</TBODY>
			</TABLE>
	</td>
  </tr>

</TABLE>
</form>
</BODY>

</HTML>
<%
try{

	if(dhtvBean != null){
		dhtvBean.DBclose();
		dhtvBean = null;
	}
	if(splist!=null){
		splist.clear();
	}
	session.setAttribute("dhtvBean",null);
	
	}
	catch (Exception e) {}
%>