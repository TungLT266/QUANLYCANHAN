<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.List"%>
<%@page import="geso.traphaco.erp.db.sql.dbutils"%>
<%@page import="geso.traphaco.erp.beans.congty.*"%>
<%@page import="geso.traphaco.erp.beans.congty.imp.*"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<%
	IErpCongTy congty = (IErpCongTy) session.getAttribute("ctBean");
	List<IErpNganHang> nganhang = congty.getNganHangList();
	ResultSet ctyRs = congty.getCtyRs();
	String tkctyId = congty.getTkCtyId();
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>

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

<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>


<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/ajax_erp_bgmuanpp.js"></script>

<script type="text/javascript" src="../scripts/DocTienTiengViet.js"></script>
<script type="text/javascript" src="../scripts/formattien.js"></script>


<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
<script type="text/javascript">
	jQuery(function($){ 
		 $('.addspeech').speechbubble();});
</script>



<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>

<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/ajax_erp_bgmuanpp.js"></script>

<script type="text/javascript" src="../scripts/DocTienTiengViet.js"></script>
<script type="text/javascript" src="../scripts/formattien.js"></script>

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});	
</script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
    $(document).ready(function() { 
    	$(".select2").select2();
	});
</script>

<SCRIPT language="javascript" type="text/javascript">
function submitform()
{
    document.forms['congtyForm'].submit();
}
 function saveform()
 {
	 var ctMa = document.getElementById('ma');
	 var ctTen= document.getElementById('ten');
	 var ctTentienganh = document.getElementById('tentienganh');
	 var ctDiachi = document.getElementById('diachi');
	 var ctDiachitienganh = document.getElementById('diachitienganh');
	 var ctMasothue = document.getElementById('masothue');
	 var ctNganhnghekinhdoanh = document.getElementById('nganhnghekinhdoanh');
	 var ctDienthoai = document.getElementById('dienthoai');
	 var ctGiamdoc = document.getElementById('giamdoc');
	 var ctKetoantruong = document.getElementById('ketoantruong');
	 if(ctMa.value == "")
	 {
		 alert("Vui lòng nhập vào mã Công ty!");
		 ctMa.focus();
		 return;
	 }
	 if(ctTen.value == "")
	 {
		 alert("Vui lòng nhập vào tên Công ty!");
		 ctTen.focus();
		 return;
	 }

	 document.forms['congtyForm'].submit();
 }
 </SCRIPT>
<style type="text/css" media="all">
.txtsearch {
	border: 1px solid #CCC;
	padding: 3px;
	-moz-border-radius: 5px;
	-webkit-border-radius: 5px;
	border-radius: 5px;
	background: #F0F0F0;
}
</style>
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
</head>
<body>
	<form name="congtyForm" method="post" action="../../ErpCongTyUpdateSvl">
		<input type="hidden" name="id" value="<%= congty.getID()%>">
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
					<TABLE width="100%">
						<TR>
							<TD align="left" class="tbnavigation">
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Cơ bản > Công ty > Cập nhật</TD>
										<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen%>
										</TD>
									</tr>
									<tr>
										<TD align="left" height="5" colspan="4" class=""></td>
									</tr>
								</TABLE>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="3" cellspacing="0">
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR class="tbdarkrow">
										<TD width="30" align="left"><A href="../../ErpCongTySvl?userId=1000"><img src="../images/Back30.png" alt="Quay ve" title="Quay ve"
												border="1" longdesc="Quay ve" style="border-style: outset"></A></TD>
										<TD width="2" align="left"></TD>
										<TD width="30" align="left"><A href="javascript: saveform()"><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai"
												border="1" style="border-style: outset"></A></TD>
										<TD>&nbsp;</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
					</TABLE>
					<TABLE width=100% cellpadding="3" cellspacing="0" border="0">
						<TR>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
									<LEGEND class="legendtitle">Thông báo </LEGEND>
									<textarea name="dataerror" style="width: 100%" readonly="readonly" rows="1"><%= congty.getMessage() %></textarea>
								</FIELDSET>
							</TD>
						</TR>
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
									<LEGEND class="legendtitle">Thông tin công ty </LEGEND>
									<TABLE class="tblight" width="100%" cellspacing="0" cellpadding="6">
										<TR>
											<TD width="15%" class="plainlabel">Mã<FONT class="erroralert">*</FONT></TD>
											<TD class="plainlabel"><input type="text" maxlength="100" size="20" id="ma" name="ma" value="<%= congty.getMA() %>"></TD>
											
											<TD width="15%" class="plainlabel">Mã số thuế</TD>
											<TD class="plainlabel"><input type="text" maxlength="50" size="20" id="masothue" name="masothue" value="<%= congty.getMASOTHUE() %>"></TD>
																
										</TR>
										<TR>
											<TD width="15%" class="plainlabel">Tên<FONT class="erroralert">*</FONT></TD>
											<TD class="plainlabel"><input type="text" maxlength="250" size="20" id="ten" name="ten" value="<%= congty.getTEN() %>"></TD>
											
											<TD width="15%" class="plainlabel">Tên (Tiếng Anh)</TD>
											<TD class="plainlabel"><input type="text"  size="20" id="tentienganh" name="tentienganh" value="<%= congty.getTENTIENGANH() %>"></TD>
											
										</TR>
										<TR>
											<TD width="15%" class="plainlabel">Địa chỉ</TD>
											<TD class="plainlabel"><input type="text" maxlength="250" size="20" id="diachi" name="diachi" value="<%= congty.getDIACHI() %>"></TD>
											
											<TD width="15%" class="plainlabel">Địa chỉ (Tiếng Anh)</TD>
											<TD class="plainlabel"><input type="text"   size="20" id="diachitienganh" name="diachitienganh" value="<%= congty.getDIACHITIENGANH() %>"></TD>
											
										</TR>
										<TR>
											<TD width="15%" class="plainlabel">Ngành nghề kinh doanh</TD>
											<TD class="plainlabel"><input   type="text" size="20" id="nganhnghekinhdoanh" name="nganhnghekinhdoanh"
												value="<%= congty.getNGANHNGHEKINHDOANH() %>"></TD>
												
											<TD width="15%" class="plainlabel">Điện thoại</TD>
											<TD class="plainlabel"><input   type="text" size="20" id="dienthoai" name="dienthoai"
												value="<%= congty.getDIENTHOAI() %>"></TD>
										</TR>
										<TR>
											<TD width="15%" class="plainlabel">Fax</TD>
											<TD class="plainlabel"><input maxlength="50" type="text" size="20" id="fax" name="fax" value="<%= congty.getFAX() %>"></TD>
											
											<TD width="15%" class="plainlabel">Tổng giám đốc</TD>
											<TD class="plainlabel"><input maxlength="50" type="text" size="20" id="giamdoc" name="giamdoc" value="<%= congty.getGIAMDOC() %>"></TD>
										</TR>
										
										<TR>
											<TD width="15%" class="plainlabel">Giấy phép kinh doanh</TD>
											<TD width="15%" class="plainlabel"><input type="text" name="giayphepkinhdoanh" value="<%=congty.getGiayPhepKinhDoanh()%>"></TD>
											
											<TD width="15%" class="plainlabel">Vốn điều lệ</TD>
											<TD class="plainlabel"><input name="vondieule" id="vondieule" maxlength="50" type="text" size="20" 
												value="<%=congty.getVonDieuLe()%>"></TD>
										</TR>
										
										<TR>
											<TD width="15%" class="plainlabel">Số tài khoản</TD>
											<TD width="15%" class="plainlabel"><input type="text" name="sotaikhoan" value="<%=congty.getSoTaiKhoan()%>"></TD>
											
											<TD width="15%" class="plainlabel">Giám đốc tài chính</TD>
											<TD class="plainlabel"><input name="ketoantruong" id="ketoantruong" maxlength="50" type="text" size="20" id="giamdoc" name="giamdoc"
												value="<%= congty.getKETOANTRUONG() %>"></TD>
										</TR>
										<TR>
											<TD width="15%" class="plainlabel">Tại Ngân hàng</TD>
											<TD width="15%" class="plainlabel"><Select name="nganhang">
													<option value=""></option>
													<%
														for (IErpNganHang n : nganhang)
														{
													%>
													<%
														if (n.getId().equals(congty.getNganHang_FK()))
															{
													%>
													<option value="<%=n.getId()%>" selected="selected"><%=n.getTen()%></option>
													<%
														}
															else
															{
													%>
													<option value="<%=n.getId()%>"><%=n.getTen()%></option>
													<%
														}
														}
													%>
											</Select></TD>
											<TD class="plainlabel"> Thừa hưởng hệ thống tài khoản từ</TD>
											<TD width="15%" class="plainlabel"><Select name="tkctyId">
													<option value=""></option>
											<%
												if(ctyRs != null){
													try{
													
													while(ctyRs.next()){
													if(ctyRs.getString("CTYID").equals(tkctyId)){ %>
														<option value = "<%= ctyRs.getString("CTYID") %>" selected><%= ctyRs.getString("CTY")%></option>														
											<%		}else{ %>
														<option value = "<%= ctyRs.getString("CTYID") %>" ><%= ctyRs.getString("CTY")%></option>			
											<%		}
													}
													}catch(java.sql.SQLException e){}
												}
											
											%>
											</Select></TD>
											
											
											
										</TR>
										
										<TR class="plainlabel" >
											  <TD class="plainlabel">Ngày bắt đầu tài chính </TD>
											  <TD class="plainlabel">                               
                                                 <input type="text" size="10" 
                                     			   id="ngayxuathd" name="ngaybatdautc" value="<%=congty.getngaybatdautc()%>" maxlength="10"/> 
											  </TD>
											   
											  <TD class="plainlabel">Ngày kết thúc tài chính</TD>
											  <TD class="plainlabel">                               
                                                 <input type="text" size="10" 
                                     			   id="ngaycongno" name="ngayketthuctc" value="<%=congty.getngayketthuctc()%>" maxlength="10" /> 
											  </TD>
										</TR>
										
										<TR>										
											<TD width="15%" class="plainlabel" colspan="3">
											
											<label> <%
 											if (congty.getIsTongCty().equals("1"))
 											{
 											%> <input name="isTongCty" type="checkbox"	value="1" checked> <%
 											}
 											else
 											{
 											%> <input name="isTongCty" type="checkbox" value="1"> <%
 											}
 												%> Tổng công ty
											</label>
											
											&nbsp;&nbsp;
											
											<label> <%  if (congty.getTRANGTHAI().equals("1")){%> <input name="trangthai"
													type="checkbox" value="1" checked> <%} else {%> <input name="trangthai" type="checkbox" value="1"> <%} %> Hoạt động
											</label></TD>
											<TD class="plainlabel"></TD>
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
<%
try{
	if(nganhang!=null){
		nganhang.clear();
	}
	if(ctyRs!=null){
		ctyRs.close();
	}
	congty.closeDB(); 
	session.setAttribute("ctBean", null) ; 
	
}catch(Exception er){
	
}
} 
%>