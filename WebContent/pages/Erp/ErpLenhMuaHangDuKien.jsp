<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import="geso.traphaco.erp.beans.lapkehoach.IErpLenhmuahangdkList"%>
<%@ page import = "geso.traphaco.center.util.*" %>
<%@ page  import = "geso.traphaco.erp.db.sql.dbutils" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<% dbutils db = new dbutils(); %> 

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%	
	IErpLenhmuahangdkList obj =(IErpLenhmuahangdkList)session.getAttribute("lmhdk");
	ResultSet mhdkRs = obj.getLenhmuahangdkRs();
	ResultSet spRs  = (ResultSet) obj.getSanphamRS();
	ResultSet nmRs  = (ResultSet) obj.getNhamayRS();

	String thang = obj.getThang();
	String nam = obj.getNam();
	
	String t1 = "", t2 = "", t3 = "", n1 = "", n2 = "", n3 = "";
	t1 = thang;
	n1 = nam;
	
	if(t1.equals("12")){
		t2 = "1";
		n2 = "" + (Integer.parseInt(n1) + 1);
	}else{
		t2 = "" + (Integer.parseInt(t1) + 1);
		n2 = n1;
	}
	
	if(t2.equals("12")){
		t3 = "1";
		n3 = "" + (Integer.parseInt(n2) + 1);
	}else{
		t3 = "" + (Integer.parseInt(t2) + 1);
		n3 = n2;
	}
	NumberFormat formatter = new DecimalFormat("#,###,###.###");
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<script type="text/javascript" language="JavaScript" src="../scripts/Numberformat.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
	
	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
	
	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
	     $(document).ready(function() { $("select").select2();  });
	     
	</script>
		
	<script type="text/javascript">
		$(document).ready(function() {		
				$( ".days" ).datepicker({			    
						changeMonth: true,
						changeYear: true				
				});            
	        }); 		
			
	</script>
	
	
	<SCRIPT language="javascript" type="text/javascript">
	function clearform()
	{ 
	    document.forms['mhdk'].nam.value= "";
	    document.forms['mhdk'].thang.value= "";
	    document.forms['mhdk'].trangthai.value = "";
		document.forms['mhdk'].submit();
	}

	function submitform()
	{
		document.forms['mhdk'].action.value= 'search';
		document.forms['mhdk'].submit();
	}

	function newform()
	{
		document.forms['mhdk'].action.value= 'new';
		document.forms['mhdk'].submit();
	}
	
	function XacNhanChuyen(PlainId)
	{
		var lsp = document.getElementById('loai').value;
		
		var soluong = '';
		var maket = '';
		
		//Nếu loại bao bì thì cho đặt tới market
		if( lsp == 100013 )
		{
			var _maket = document.getElementsByName(PlainId + "_MAKET");
			var _soluongDat = document.getElementsByName(PlainId + "_soluongDAT");
			if( _soluongDat != null )
			{
				for( i = 0; i < _soluongDat.length; i++ )
				{
					if( _soluongDat.item(i).value != '' )
					{
						soluong += _soluongDat.item(i).value + ';';
						maket += _soluongDat.item(i).value + ';';
					}
				}
				
				if( soluong != '' )
				{
					soluong = soluong.substring(0, soluong.length - 1 );
					maket = maket.substring(0, maket.length - 1 );
				}
			}
		}
		else
		{
			soluong = document.getElementById('soluong' + PlainId).value;
		}
		
		if(soluong == '' ) 
		{
			alert('Vui lòng nhập số lượng mua');
			return;
		}
		
		document.forms["mhdk"].Id.value = PlainId;
		document.forms["mhdk"].soluongMua.value = soluong;
		document.forms["mhdk"].maketMua.value = maket;
		document.forms["mhdk"].action.value = "chuyenthanhPO";
		
		document.forms["mhdk"].submit();
		
	 }
	
	 function readExcel()
	 {
		 document.forms['mhdk'].action.value='readExcel';
		 document.forms['mhdk'].setAttribute('enctype', "multipart/form-data", 0);
	     document.forms['mhdk'].submit();
	 }
	 
	 function exportExcel()
	 {
		 document.forms['mhdk'].action.value='exportExcel';
	     document.forms['mhdk'].submit();
	 }
	 
	function save()
	{
		
	  document.forms["mhdk"].action.value = "save";
	  document.forms["mhdk"].submit(); 
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
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46)
			{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}

	</SCRIPT>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="mhdk" method="post" action="../../ErpLenhmuahangdukienSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="Id" value="" >
<input type="hidden" name="nccId" value="" >
<input type="hidden" name="soluongMua" value="" >
<input type="hidden" name="maketMua" value="" >

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%"> 
    <TR>
        <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
        <TABLE width="100%" cellpadding="0" cellspacing="2">
            <TR>
                <TD align="left" class="tbnavigation">
                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                        <TR height="22">
                            <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý cung ứng > Lập kế hoạch > Đề nghị mua nguyên liệu </TD>  
                            <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD>
                        </tr>
                    </TABLE>
                </TD>
            </TR>
        </TABLE>
        <TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR>
				<TD>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class="tbdarkrow">
							<TD width="30" align="left">
								<div id="btnSave">
									<A href="javascript: save()"><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border="1" style="border-style: outset"></A>
								</div>
							</TD>
							<TD>&nbsp;</TD>
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
                            <TD width="100%" align="left" >
                            <FIELDSET>
                              <LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm &nbsp;</LEGEND>

                            <TABLE  width="100%" cellpadding="6" cellspacing="0">
                    		 <TR>
		                        <TD class="plainlabel" width="15%">Năm</TD>
		                        <TD class="plainlabel">
		                        	<SELECT name = "nam"  style="width: 100px;" onChange = "submitform();">
		                        		<%
										int year =  Integer.parseInt(obj.getDateTime().substring(0, 4)) - 1;                       		
		                        			                        		
		                        		for(int i = 0; i <= 2; i++){		                        				
		                        			
		                        			if(obj.getNam().equals("" + year) ){
		                        		%>
			                        		<OPTION value = <%= year  %> selected> <%= year %></OPTION>
			                        	
			                        	<% 	}else{ %>
		                        			
		                        			<OPTION value = <%= year  %> > <%= year %></OPTION>		                        			
		                        		<%	}
		                        			year = year + 1;
		                        		}%>
		                        	</SELECT>
		                        	
		                        </TD>
                    		</TR>	
                    		<TR>
                        		<TD class="plainlabel" width="15%">Từ tháng</TD>
                        		<TD class="plainlabel">
		                        	<SELECT name="thang" style="width: 100px;" onChange = "submitform();">
										<option value = '0'>&nbsp;</option>
		                        <%	thang = "0";
		                        
		                        	if(obj.getThang().length() > 0 ) 
		                        		thang = "" + Integer.parseInt(obj.getThang());
		                        			                        		                        	
		                        	for(int m = 1; m <= 12; m++){
		                        		if(Integer.parseInt(thang) == m){ %>
		                        			<option value="<%= m %>" selected="selected"><%="Tháng " + m %></option>
		                       <% 		}else{ %>
		                					<option value="<%= m %>" ><%="Tháng " + m %></option>
		                       <% 		}
		                        	}
		                        %>
		                        
		                        	</SELECT>
                        		
                        		</TD>

							</TR> 
          					 <TR>
                    				<TD class="plainlabel" valign="middle">Nhà máy</TD>
                     				<TD class="plainlabel" valign="middle" colspan = 3>
                        				<select  id="nmId" name="nmId" style="width: 250px;" onChange = "submitform();">
                          					<option value=""></option>
                          			<%
	                      		if(nmRs  != null)
	                       		{
	                       			while(nmRs.next())
	                       			{  
		                       			if( nmRs.getString("NMID").equals(obj.getNhamayId())){ %>
	                        				<option value="<%= nmRs.getString("NMID") %>" selected="selected" ><%= nmRs.getString("TEN") %></option>
	                        			<%}else 
		                        			{ %>
		                        			<option value="<%= nmRs.getString("NMID") %>" ><%= nmRs.getString("TEN") %></option>
		                        		 <% } 
	                        		} 
	                       			nmRs.close();
	                        	}
	                        	%>
                            			</select>
                      				</TD>                        
                			</TR>
							
          					 <TR>
                    				<TD class="plainlabel" valign="middle">Loại</TD>
                     				<TD class="plainlabel" valign="middle" colspan = 3>
                        				<select  id="loai" name="loai" style="width: 250px;" onChange = "submitform();">
                          					<option value=""></option>
                          			<%
		                       			if( obj.getLoai().equals("100013")){ %>		      
		                       				<option value="100013" selected="selected" >Bao bì</option>                 			
	                        				<option value="100000" >Nguyên liệu</option>
	                        				<option value="100008"  >Vật liệu phụ</option>
	                        				<option value="100002"  >Thành phẩm</option>
	                        					                        				
	                        	<%		}else if( obj.getLoai().equals("100000")){ %>
		                       				<option value="100013"  >Bao bì</option>                 			
	                        				<option value="100000" selected="selected" >Nguyên liệu</option>
	                        				<option value="100008"  >Vật liệu phụ</option>
	                        				<option value="100002"  >Thành phẩm</option>
	                        				
		                        <% 		}else if( obj.getLoai().equals("100008")){ %>
			                   				<option value="100013"  >Bao bì</option>                 			
			                				<option value="100000"  >Nguyên liệu</option>
			                				<option value="100008" selected="selected" >Vật liệu phụ</option>
			                				<option value="100002"  >Thành phẩm</option>
			                				
			                    <% 		}else if( obj.getLoai().equals("100002")){ %>
			                   				<option value="100013"  >Bao bì</option>                 			
			                				<option value="100000"  >Nguyên liệu</option>
			                				<option value="100008"  >Vật liệu phụ</option>
			                				<option value="100002" selected="selected"  >Thành phẩm</option>
			                				
			                    <% 		}else{ %>
		                       				<option value="100013"  >Bao bì</option>                 			
	                        				<option value="100000" >Nguyên liệu</option>
	                        				<option value="100008"  >Vật liệu phụ</option>
	                        				<option value="100002"  >Thành phẩm</option>
	                        					                        	
	                        	 <% 	} %>
                            			</select>
                      				</TD>                        
                			</TR>

          					 <TR>
                    				<TD class="plainlabel" valign="middle">Sản phẩm</TD>
                     				<TD class="plainlabel" valign="middle" colspan = 3>
                        				<select  id="spId" name="spId" style="width: 250px;" onChange = "submitform();">
                          					<option value=""></option>
                          			<%
	                      		if(spRs  != null)
	                       		{
	                       			while(spRs.next())
	                       			{  
		                       			if( spRs.getString("pk_seq").equals(obj.getSpId())){ %>
	                        				<option value="<%= spRs .getString("pk_seq") %>" selected="selected" ><%= spRs.getString("ten") %></option>
	                        			<%}else 
		                        			{ %>
		                        			<option value="<%= spRs .getString("pk_seq") %>" ><%= spRs.getString("ten") %></option>
		                        		 <% } 
	                        		} 
	                       			spRs .close();
	                        	}
	                        	%>
                            			</select>
                      				</TD>                        
                			</TR>
 
 							<TR>
				 	  			<TD class="plainlabel">Xuất nhập dữ liệu</TD>
				  	  			<TD class="plainlabel" colspan = 3>
				  	  				<INPUT onchange="Upload()" type="file" name="filename" id="filename"  size="40" value=''>
				  	  	  			&nbsp;&nbsp;&nbsp;	
				  	  	  			<a href="javascript:readExcel();" class="button"> <img alt="" src="../images/button.png" style="top: -4px;"> Đọc dữ liệu</a>
				  	  	  
				  	  	  			&nbsp;&nbsp;&nbsp;
				  	  	  			<a href="javascript:exportExcel();" class="button"> <img alt="" src="../images/button.png" style="top: -4px;"> Xuất dữ liệu</a>
				  	  			</TD>
						  	
				 			</TR>
 
                            </TABLE>

                            </FIELDSET>
                            </TD>

                        </TR>
                    </TABLE>
                    </TD>
                </TR>

                <TR>
                    <TD width="100%">
                        <FIELDSET>
                            <LEGEND class="legendtitle">&nbsp;Đề nghị mua nguyên liệu &nbsp;&nbsp;	      
                            </LEGEND>
    
                            <TABLE width="100%" border="0" cellspacing="2" cellpadding="4">
                                 <TR class="tbheader">
                                	<TH align = "center" rowspan = 1 > ID</TH>
									<TH align = "center" rowspan = 1 >Nguyên liệu</TH>
									<TH align = "center" rowspan = 1   >Số lượng</TH>
							<% if(obj.getLoai().equals("100000")) {%>	
									<TH align = "center" rowspan = 1 >Hàm ẩm</TH>
									<TH align = "center" rowspan = 1 >Hàm lượng</TH>	
									<TH  align = "center" rowspan = 1 >Nguyên liệu thay thế</TH>
									<TH align = "center"  >Số lượng</TH>
							<% } else if(obj.getLoai().equals("100013")) { %>
									<TH align = "center" rowspan = 1 >Ma-ket</TH>
							<% } %>
									
									
									<TH align = "center" >Ngày đặt dự kiến</TH>
									<TH align = "center" >Ngày nhận dự kiến</TH>
									
									<!-- <TH align = "center" width="21%" colspan = 3  >Yêu cầu cung ứng</TH> -->
									<TH align = "center"    >Mã yêu cầu</TH>
									
									<TH align = "center"   >Đã đặt hàng</TH>
									<TH align = "center"    >Chuyển sang PO</TH>
								</TR>
								<!-- <TR class="tbheader">
									<TH align = "center" width="7%"  >Năm</TH>
									<TH align = "center" width="7%"  >Tháng</TH>			
									<TH align = "center" width="7%"  >Mã yêu cầu</TH> 
								 </TR> -->
                                <% 
                                   
                                    int m = 0;
                                    //int count = 0; 
                                    
                                    String lightrow = "tblightrow";
                                    String darkrow = "tbdarkrow";
                                    if(mhdkRs != null)
                                    while ( mhdkRs.next()){
                                       
                                        if (m % 2 != 0) {%>                     
                                            <TR class= <%=lightrow%> >
                                        <%} else {%>
                                            <TR class= <%= darkrow%> >
                                        <%}%>
                                                <TD align="center"><%= mhdkRs.getString("DMHID") %>
                                                <INPUT type = "hidden" NAME = "dmhId" value = "<%= mhdkRs.getString("DMHID") %>" ></TD>
                                               
												<TD align="left"><%= mhdkRs.getString("MA") + " - " + mhdkRs.getString("TEN") %></TD>
 												<TD align="right"><%= formatter.format(mhdkRs.getDouble("SOLUONG")) %></TD>

 							<% 
 								ResultSet rs = obj.getNLThaythe( mhdkRs.getString("SPID")); 
 								if( obj.getLoai().trim().equals("100000")) {%>	
 										
 												<TD align="center"><%= mhdkRs.getString("HAMAM") %></TD>
 												<TD align="center"><%= mhdkRs.getString("HAMLUONG") %></TD>
 									
												<TD align="left">
										<% 	
											if(rs != null){
												rs.beforeFirst();
												if(rs.next()){
										%>
													<select style="width:150px" name ="SPTT<%= mhdkRs.getString("DMHID") %>">
										<%			rs.beforeFirst();
													while(rs.next()){ 
														if(mhdkRs.getString("VTTTID").equals(rs.getString("VTTTID"))){
										%>
														<option value = "<%= rs.getString("VTTTID") %>" SELECTED><%= rs.getString("TENTT") %>										
										<%			
														}else{  %>														
														<option value = "<%= rs.getString("VTTTID") %>" ><%= rs.getString("TENTT") %>														
										<%				} 
													}%>	 		  													   		
											   		</select>
												
										<% 		}
											}
												%>		
												
										<%	if(rs != null){
												rs.beforeFirst();
												if(rs.next()){ %>
												
 												<TD align="right"><INPUT style = "width:70px;text-align:right" NAME = "soluongTT<%= mhdkRs.getString("DMHID") %>" value = "<%= mhdkRs.getString("SOLUONGTT") %>" onkeypress="return keypress(event);"></TD>

										<%		}
												else{ %>
												<TD align="right"></TD>			
										<%		}
												rs.close();
											 }	%>
							<%	}else if( obj.getLoai().equals("100013") ) {  
									rs = obj.getMa_ket( mhdkRs.getString("SPID"), mhdkRs.getString("NGAYDATHANG"));
									if(rs != null){
										if(rs.next()){	
											rs.beforeFirst();	%>				
    											<TD align="center" width = "10%">           							                  	
												<a href="" id="Maket<%= m %>" rel="subcontentMaket<%= m %>"> 
												<img alt="Ma-ket"  title="Ma-ket" src="../images/Search20.png"></a>
					           	 		
									    		<DIV id="subcontentMaket<%= m %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
										 		background-color: white; width: 720px; max-height:250px; overflow:auto; padding: 4px; z-index: 100000000; ">
												<table width="98%" align="center" cellpadding="6px" cellspacing="6px">
												<tr class = "tbheader">
													<th >Ma-ket</th>
													<th>Hiệu lực từ ngày</th>
													<th>Hiệu lực đến ngày</th>
													<TH>Tồn hiện tại</TH>
													<TH>Số lượng</TH>
													
												</tr>
	
							<%
											int k = 0;
											String _soluongDAT = "";
											while(rs.next()){
												if( k == 0 )
													_soluongDAT = formatter.format(mhdkRs.getDouble("SOLUONG"));
												else 
													_soluongDAT = "";
												if(k % 2 == 0){
							%>				 				
											<tr class = "tbdarkrow">
											
							<%					}else{ %>
							
											<tr class = "tblightrow">
							
							<%					} %>
												<td>
													<%= rs.getString("MAKET") %>
													<input name="<%= mhdkRs.getString("DMHID") %>_MAKET" type="hidden" style="width: 100%; text-align: right;" value="<%= rs.getString("MAKET") %>"  >
												</td>
												<td><%= rs.getString("TUNGAY") %></td>
												<td><%= rs.getString("DENNGAY") %></td>
												<td><%= rs.getString("TONHIENTAI") %></td> 
												<td><input name="<%= mhdkRs.getString("DMHID") %>_soluongDAT" type="text" style="width: 100%; text-align: right;" value="<%= _soluongDAT %>"  ></td> 
												 
											</tr>						
							<%				
												k++;
											} %>											
											</table>
										    <div align="right">     
										       	<a href="javascript:dropdowncontent.hidediv('subcontentMaket<%= m %>')" > <b>Đóng lại</b> </a>
										    </div>
										    </DIV> 
								
    									</TD>
									
								<%	
												}else{  %>
										<TD></TD>				
								<%				}							
										}
								} else {		%>
									
								<% } %>	
								
								
 												<TD align="center"><%= mhdkRs.getString("NGAYDATHANG") %></TD>
 												<TD align="center"><%= mhdkRs.getString("NGAYNHANHANG") %></TD>
 												<%-- <TD align="center"><%= mhdkRs.getString("NAM") %></TD>
 												<TD align="center"><%= mhdkRs.getString("THANG") %></TD> --%>
 												<TD align="center"><%= mhdkRs.getString("ID") %></TD>
 												<TD align="center"><%= formatter.format(mhdkRs.getDouble("DADATHANG"))%></TD>
    											<TD align="center" width = "10%">           							                  	
							  <% 
					                                		%>              		
												<a href="" id="PlnId<%= m %>" rel="subcontentPlnId<%= m %>"> 
												<img alt="Chuyển sang mua hàng"  title="Chuyển sang mua hàng" src="../images/Next20.png"></a>
					           	 		
									    		<DIV id="subcontentPlnId<%= m %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
										 		background-color: white; width: 720px; max-height:250px; overflow:auto; padding: 4px; z-index: 100000000; ">
												<table width="98%" align="center" cellpadding="6px" cellspacing="6px">
												<tr>
											   		<td width="70px" style="font-size: 12px">Tháng</td>
											   		<td width="70px" style="font-size: 12px"  >
											   		<select style="width: 30%" id="thang<%= mhdkRs.getString("DMHID") %>">
											   		
											   		
											   			<option value = "<%= t1 + "." + n1 %>"><%= t1 + "." + n1 %></option>
											   		
											   			<option value = "<%= t2 + "." + n2 %>"><%= (t2.length()==1?"0" + t2: t2) + "." + n2 %></option>
											   		
											   			<option value = "<%= t3 + "." + n3 %>"><%= (t3.length()==1?"0" + t3: t3) + "." + n3 %></option>
											   		</select>
											   		</td>
												</tr>

												<tr>
											   		<td width="70px" style="font-size: 12px">Sản phẩm</td>
											   		<td width="70px" style="font-size: 12px">
											    	<%= mhdkRs.getString("TEN") %>
												   	</td>
												   	
												</tr>
												<% if( !obj.getLoai().equals("100013") ) { %>
												<tr>
												    <td  style="font-size: 12px">Số lượng mua</td>
											    	<td style="font-size: 12px">
												                            
											       	<input  id="soluong<%= mhdkRs.getString("DMHID") %>" type="text" style="text-align: right;" onkeypress="keypress(event);"> 
													</td>

												</tr>
												<% } %>
											</table>
										    <div align="right">     
										       	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										      	<a href="javascript:XacNhanChuyen('<%= mhdkRs.getString("DMHID") %>');" > <b>Chuyển thành đơn mua hàng</b> </a>
										      	&nbsp;|&nbsp; 
										       	<a href="javascript:dropdowncontent.hidediv('subcontentPlnId<%= m %>')" > <b>Đóng lại</b> </a>
										    </div>
										    </DIV> 
								
    									</TD>
                            
                               		</TR>
                                     <% m++; 
                                     } %>  
                                          <TR class="tbfooter" >
									<TD align="center" colspan="15"> &nbsp; </TD>
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
<script type="text/javascript">
 <% for(int j = 0; j <= m ; j++) { %>
		dropdowncontent.init('PlnId<%= j %>', "left-top", 300, "click");
		dropdowncontent.init('Maket<%= j %>', "right-top", 300, "click");
 <% } %>
 
 
</script>
</BODY>
</html>
<% 	
	try
    {
		if(nmRs != null)
			nmRs.close();

		if(mhdkRs != null)
			mhdkRs.close();
		
		if(obj != null)
		{
			obj.DbClose();
			obj = null;
		}
		session.setAttribute("obj", null);
		session.setAttribute("lmhdk", null) ; ;
		db.shutDown();
	}
	catch (SQLException e) {} %>
<%}%>