
<%@page import="geso.traphaco.distributor.beans.phieuthuhoi.ISanphamSoLo"%>
<%@page import="geso.traphaco.distributor.beans.phieuthuhoi.imp.Sanphamthuhoi"%>
<%@page import="geso.traphaco.distributor.beans.phieuthuhoi.ISanphamthuhoi"%>
<%@page import="geso.traphaco.distributor.beans.donhang.imp.Sanpham"%>
<%@page import="geso.traphaco.distributor.beans.phieuthuhoi.IPhieuthuhoi"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Enumeration"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.distributor.beans.donhang.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.text.DateFormat" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.text.SimpleDateFormat" %>

<%@ page  import = "geso.traphaco.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% IPhieuthuhoi dhBean = (IPhieuthuhoi)session.getAttribute("obj");
  
%>
<% List<ISanphamthuhoi> splist = (List<ISanphamthuhoi>)dhBean.getSanphamList();
List<ISanphamthuhoi> spkmlist = (List<ISanphamthuhoi>)dhBean.getSpkmList();
int size = splist.size();
  %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
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

<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
<script type="text/javascript">
	jQuery(function($){ 
		 $('.addspeech').speechbubble();
	})
</script>

<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/ajax_bangianpp-mua.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
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
<script language="javascript" type="text/javascript">



		function saveform()
		{	 
			 document.getElementById("saveid").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>";	 
				document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
		
				 document.forms['dhForm'].action.value='Save';
		    	document.forms['dhForm'].submit();
		}
		function chotform()
		{	 
			if(!confirm('Bạn có muốn chốt phiếu thu hồi này?'))
			{
				return false;
			}
			 document.getElementById("saveid").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>";	 
				document.getElementById("btnChot").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";
				
				 document.forms['dhForm'].action.value='Chot';
		    	document.forms['dhForm'].submit();
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
				if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39)
				{//Phím Delete và Phím Back
					return;
				}
				return false;
			}
		}


</script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="dhForm" method="post" action="../../PhieuthuhoiUpdateSvl">
<input type="hidden" name="userId" value='<%=dhBean.getUserId() %>'>
<input type="hidden" name="nppId" value='<%= dhBean.getNppId() %>'>
<input type="hidden" name="action" value='new'>
<input type="hidden" name="formname" value='formnew'>
<INPUT type="hidden" name="id" value='<%= dhBean.getId() %>'>
<INPUT type="hidden" name="trangthai" id="trangthaiDh" value='<%= dhBean.getTrangthai() %>'>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
				<TABLE border =0 width = "100%" cellpadding="2" cellspacing="0">
				<TBODY>
					<TR height="22">
						<TD align="left" >
							<TABLE width="100%" cellpadding="0" cellspacing="0" style="margin:5px " >
								<TR>
									<TD align="left">
									   <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										   <TR height="22">
			 								   <TD align="left"  class="tbnavigation">&nbsp;Xử lý đơn hàng > Đơn hàng bán>Phiếu thu hồi>Cập nhật </TD>								   
			 								   <TD align="right" class="tbnavigation">Chào mừng <%= dhBean.getNppTen() %> &nbsp; </TD>
					    				 </TR>
									  </TABLE>
								  </TD>
							  </TR>	
						  	</TABLE>
							<TABLE width="100%" border="0" cellpadding="1" cellspacing="0">
								<TR ><TD >
									<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										<TR class = "tbdarkrow">
											<TD width="30" align="left"><A href = "../../PhieuthuhoiSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
										    <TD width="2" align="left" >&nbsp;</TD>
										    <TD width="30" align="left" >
										    <div id="btnSave">
										    <A href="javascript:saveform()" ><img id="saveid" src="../images/Save30.png" alt="Luu lai"  title="Luu lai" border="1" longdesc="Luu lai" style="border-style:outset"></A>
										    </div>
										    </TD>
										     <TD width="2" align="left" >&nbsp;</TD>
										    <TD width="30" align="left" >&nbsp;
							    			<div id="btnChot">
										    <A href="javascript:chotform()" ><img id="saveid" src="../images/Chot.png" alt="Luu lai"  title="Chốt" border="1" longdesc="Chốt" style="border-style:outset"></A>
										   		 </div>
				
										   		 </TD>
										   		  
								    		<TD align="left" >&nbsp;</TD>
										</TR>
									</TABLE>
								</TD></TR>
							</TABLE>												
							<TABLE border="0" width="100%" cellpadding = "0" cellspacing = "0" >
								<tr>
								<TD align="left" colspan="4" class="legendtitle">
									<FIELDSET>
									<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>			
				    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold" style="width:100%"  rows="1" readonly="readonly"  ><%= dhBean.getMessage() %> </textarea>
									</FIELDSET>
							   </TD>
								</tr>
								<TR>
									<TD  align="left">						
										<FIELDSET>
										<LEGEND class="legendtitle">&nbsp;Phiếu thu hồi </LEGEND>
										<TABLE cellpadding = "6" cellspacing = "0" width = "100%" border = 0>
											
																	
											
										
											<TR class="plainlabel">
											<TD  width="20%" class="plainlabel">Số phiếu xuất kho</TD>
												<TD width="80%" colspan="3" class="plainlabel"> 
												 <input name="pxkid" type="text" value='<%=dhBean.getPhieuxuatkho() %>' />
												</TD>
											</TR>
											
											
										</TABLE>
											
											
										</TABLE>
									</FIELDSET>
							
								  </TD>
							   </TR>	
							  
										
							
						  </TABLE>
						  <div style="margin: 5px"  class="plainlabel"> 
						   Hàng bán 
						  
						  	<TABLE width = "100%"  cellpadding="0px" cellspacing="1" style="padding:5px ">
												<TR class="tbheader" >
													<TH width="15%">Mã sản phẩm </TH>
													<TH width="28%">Tên sản phẩm </TH>
													<TH width="7%"> Đơn vị tính</TH>
													<TH width="5%">	Số lượng </TH>
													<TH width="10%"> Số lô đã xuất </TH>
													
												</TR>
									<% 
							if(splist != null){
							ISanphamthuhoi sanpham =new Sanphamthuhoi();
							
							int m = 0;
							while (m < size){
								sanpham = splist.get(m); 
								%>
									<TR class= 'tblightrow' >
									
										 <TD align="left" >
										 <input name="kbh_fk" type="hidden"  value="<%=sanpham.getKenhId()%>"  style="width:100%;" >
										 <input name="kho_fk" type="hidden"  value="<%=sanpham.getKhoId()%>"  style="width:100%;" > 
										<input name="idsp" type="hidden"  value="<%=sanpham.getSPId()%>"  style="width:100%;" > 
									<input name="masp" type="text"  value="<%=sanpham.getMasanpham()%>"  style="width:100%;" >
									</TD>
									<TD align="left" >
										<input name="tensp"  type="text" readonly value="<%=sanpham.getTensanpham()%>"  style="width:100%"></TD>
									 <TD align = "center" ><input name="donvitinh" type="text" value="<%= sanpham.getDonvitinh()%>" readonly  style="text-align:center ;width:100%"></TD>
						      
									<TD align="left" >
									<input name="soluong" type="text"  value="<%=sanpham.getSoluong()%>"  style="width:100%;"  readonly="readonly" onkeypress="return keypress(event);">
									</TD>
								   	  <TD align = "center" >
								   	   <a href="" id="yeucau<%= m %>" rel="subcontent<%= m %>">
	           	 						<img alt="Số lô - Vị trí hàng hóa xuất" src="../images/vitriluu.png"></a>
	           	 							<DIV id="subcontent<%= m %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
				                             background-color: white; width: 350px; padding: 4px;">
				                    	<table width="90%" align="center">
				                        <tr>
				                            <th width="100px">Số lô</th>
				                             <th width="100px">Ngày hết hạn</th>
				                            <th width="50px">Số lượng</th>
				                  			  <th width="50px">Số lượng xuất kho</th>
				                        </tr>
				                        <%
				                        	List<ISanphamSoLo> spDetailList = sanpham.getSPLoList();
				                        	int stt = 1; 
				                        	if(spDetailList.size() > 0)
				                        	{
				                        		for(int sd = 0; sd < spDetailList.size(); sd ++)
				                        		{
				                        			ISanphamSoLo spDetail = spDetailList.get(sd);
				                        			
				                        		%>
				                        			<tr>
							                            <td>
							                            	<input type="text" size="100px" name="<%=sanpham.getSPId()+ ".solo" %>" value="<%= spDetail.getSOLo() %>" readonly="readonly" style="width: 100%" /></td>
							                            	 <td>
							                            	<input type="text" size="100px" name="<%=sanpham.getSPId()+ ".ngayhethan" %>" value="<%= spDetail.getNgayhethan() %>" readonly="readonly" style="width: 100%" /></td>
							                            	<td>
							                            	<input type="text" size="50px"  name="<%=sanpham.getSPId()+ ".soluong" %>" value="<%= spDetail.getSoluong() %>"  onkeypress="return keypress(event);" style="width: 100%" />
							                           		 </td> 
							                           		 <td>
							                            	<input type="text" size="50px" readonly="readonly"  name="<%=sanpham.getSPId()+ ".soluongxk" %>" value="<%= spDetail.getSoluongXK() %>"  onkeypress="return keypress(event);" style="width: 100%" />
							                           		 </td> 
							                        </tr>
				                        		<%}
				                        	}
				                        %>
				                    </table>
				                     <div align="right"><a href="javascript:dropdowncontent.hidediv('subcontent<%=m %>')">Đóng lại</a></div>
				                </DIV>
								   	  
								    	</TD>
								  	 
								</TR>
								<% m++; }}%>
								
																																																																																																																						
								<TR><TD colspan="8" class="tbfooter">&nbsp;</TD></TR>	
								</TABLE>
						  </div>
						    <div style="margin: 5px" class="plainlabel"> 
						  Hàng khuyến mãi
						  
						  	<TABLE width = "100%"  cellpadding="0px" cellspacing="1" style="padding:5px ">
												<TR class="tbheader" >
													<TH width="15%">Mã sản phẩm </TH>
													<TH width="28%">Tên sản phẩm </TH>
													<TH width="7%"> Đơn vị tính</TH>
											
													<TH width="5%">	Số lượng </TH>
													<TH width="10%"> Số lô đã xuất </TH>
													
												</TR>
									<% 
							if(spkmlist != null){
							ISanphamthuhoi sanpham =new Sanphamthuhoi();
							
							int n = 0;
							int m=size;
							while (n < spkmlist.size()){
								sanpham = spkmlist.get(n); 
								%>
									<TR class= 'tblightrow' >
									
										 <TD align="left" >
										 <input name="kmkbh_fk" type="hidden"  value="<%=sanpham.getKenhId()%>"  style="width:100%;" >
										 <input name="kmkho_fk" type="hidden"  value="<%=sanpham.getKhoId()%>"  style="width:100%;" > 
										<input name="kmidsp" type="hidden"  value="<%=sanpham.getSPId()%>"  style="width:100%;" > 
									<input name="kmmasp" type="text"  value="<%=sanpham.getMasanpham()%>"  style="width:100%;" >
									</TD>
									<TD align="left" >
										<input name="kmtensp"  type="text" readonly value="<%=sanpham.getTensanpham()%>"  style="width:100%"></TD>
									 <TD align = "center" ><input name="kmdonvitinh" type="text" value="<%= sanpham.getDonvitinh()%>" readonly  style="text-align:center ;width:100%"></TD>
						      
									<TD align="left" >
									<input name="kmsoluong" type="text"  value="<%=sanpham.getSoluong()%>"  style="width:100%;"  readonly="readonly" onkeypress="return keypress(event);">
									</TD>
								   	  <TD align = "center" >
								   	   <a href="" id="yeucau<%= m %>" rel="subcontent<%= m %>">
	           	 						<img alt="Số lô - Vị trí hàng hóa xuất" src="../images/vitriluu.png"></a>
	           	 							<DIV id="subcontent<%= m %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
				                             background-color: white; width: 350px; padding: 4px;">
				                    	<table width="90%" align="center">
				                        <tr>
				                            <th width="100px">Số lô</th>
				                            <th width="100px">Ngày hết hạn</th>
				                            <th width="50px">Số lượng</th>
				                    		<th width="50px">Số lượng xuất kho</th>
				                        </tr>
				                        <%
				                        	List<ISanphamSoLo> spDetailList = sanpham.getSPLoList();
				                        	int stt = 1; 
				                        	if(spDetailList.size() > 0)
				                        	{
				                        		for(int sd = 0; sd < spDetailList.size(); sd ++)
				                        		{
				                        			ISanphamSoLo spDetail = spDetailList.get(sd);
				                        			
				                        		%>
				                        			<tr>
							                            <td>
							                            	<input type="text" size="100px" name="<%=sanpham.getSPId()+ ".kmsolo" %>" value="<%= spDetail.getSOLo() %>" readonly="readonly" style="width: 100%" /></td>
							                            	 <td>
							                            	<input type="text" size="100px" name="<%=sanpham.getSPId()+ ".kmngayhethan" %>" value="<%= spDetail.getNgayhethan() %>" readonly="readonly" style="width: 100%" /></td>
							                            <td>
							                            	<input type="text" size="50px"  name="<%=sanpham.getSPId()+ ".kmsoluong" %>" value="<%= spDetail.getSoluong() %>"  onkeypress="return keypress(event);" style="width: 100%" />
							                            </td> 
							                            	 <td>
							                            	<input type="text" size="50px" readonly="readonly"  name="<%=sanpham.getSPId()+ ".kmsoluongxk" %>" value="<%= spDetail.getSoluongXK() %>"  onkeypress="return keypress(event);" style="width: 100%" />
							                           		 </td> 
							                        </tr>
				                        		<%}
				                        	}
				                        %>
				                    </table>
				                     <div align="right"><a href="javascript:dropdowncontent.hidediv('subcontent<%=m %>')">Đóng lại</a></div>
				                </DIV>
								   	  
								    	</TD>
								  	 
								</TR>
								<% m++; n++;}}%>
								
																																																																																																																						
								<TR><TD colspan="8" class="tbfooter">&nbsp;</TD></TR>	
								</TABLE>
						  </div>
						</TD>
					</TR>				
				</TBODY>
			</TABLE>
	</td>
  </tr>
<script type="text/javascript">

dropdowncontent.init('lsx', "right-bottom", 300, "click");

 <% for(int i = 0; i < size+spkmlist.size(); i ++){ %>
	dropdowncontent.init('yeucau<%= i %>', "left-top", 300, "click");
<% } %>

</script>

</TABLE>
</form>
</BODY>
</HTML>
<% 	
try{
if(dhBean != null){
	dhBean.DBclose();
	dhBean = null;
}

if(splist!=null){
	splist.clear();
}

session.setAttribute("obj",null);
}catch(Exception er){
	
}




%>
<%}%>

