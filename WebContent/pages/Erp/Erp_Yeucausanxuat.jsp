<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import="geso.traphaco.erp.beans.lapkehoach.IErp_Yeucausanxuat"%>
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
	IErp_Yeucausanxuat obj =(IErp_Yeucausanxuat)session.getAttribute("ycsx");
	ResultSet ycsxRs = obj.getYeucausanxuatRs();
	String thang = obj.getThang();
	String nam = obj.getNam();
	ResultSet clRs  = (ResultSet) obj.getChungloaiRS();
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

	NumberFormat formatter = new DecimalFormat("#,###,###");
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
	    document.forms['lsxdk'].dvkdId.value= "";
	    document.forms['lsxdk'].nam.value= "";
	    document.forms['lsxdk'].thanglsx.value= "";
	    document.forms['lsxdk'].trangthai.value = "";
		document.forms['lsxdk'].submit();
	}

	function submitform()
	{
		document.forms['lsxdk'].action.value= 'search';
		document.forms['lsxdk'].submit();
	}

	function newform()
	{
		document.forms['lsxdk'].action.value= 'new';
		document.forms['lsxdk'].submit();
	}
	
	function XacNhanChuyen(PlId)
	 {
		var soluong = document.getElementById('soluong' + PlId).value;
		
		var sanphamid = document.getElementById('sanphamid' + PlId).value;
		
		var t = document.getElementById('thang' + PlId);
		var thang = t.options[t.selectedIndex].value;
		
		
		var k = document.getElementById('KbsxId' + PlId);
		var kbsxId = k.options[k.selectedIndex].value;
		
		alert(kbsxId);
		if(thang == ''){
			alert('Vui lòng chọn tháng sản xuất');
			return;
		}
		
		if(soluong == ''){
			alert('Vui lòng nhập số lượng sản xuất');
			return;
		}
		if(sanphamid == '')
		{
			alert('Vui lòng chọn sản phẩm sản xuất');
			return;
		}

		
		document.forms["lsxdk"].thanglsx.value = thang;
		document.forms["lsxdk"].soluongsx.value = soluong;
		
		document.forms["lsxdk"].kbsxId.value = kbsxId;
		document.forms["lsxdk"].sanphamId.value = sanphamid;
		document.forms["lsxdk"].action.value = "chuyenthanhLSX";
		document.forms["lsxdk"].submit();
		
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
			{//Phím Delete và Phím Back va dau cham, phim Tab
				return;
			}
			return false;
		}
	}
	
	 function readExcel()
	 {
		 document.forms['lsxdk'].action.value='readExcel';
		 document.forms['lsxdk'].setAttribute('enctype', "multipart/form-data", 0);
	     document.forms['lsxdk'].submit();
	 }
	 
	 function exportExcel()
	 {
		 document.forms['lsxdk'].action.value='exportExcel';
	     document.forms['lsxdk'].submit();
	 }
	
	function THONGBAO()
	{
		if(document.getElementById("msg").value != '' )
		{
			alert(document.getElementById("msg").value);
		}
	}
	
	</SCRIPT>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="lsxdk" method="post" action="../../Erp_YeucausanxuatSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="thanglsx" value="" >
<input type="hidden" name="kbsxId" value="" >
<input type="hidden" name="sanphamId" value="" >
<input type="hidden" name="soluongsx" value="" >
<input type="hidden" name="msg" id="msg" value="<%= obj.getMsg() %>" >
<script type="text/javascript">THONGBAO();</script>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%"> 
    <TR>
        <TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
        <TABLE width="100%" cellpadding="0" cellspacing="2">
            <TR>
                <TD align="left" class="tbnavigation">
                    <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                        <TR height="22">
                            <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý cung ứng > Lập kế hoạch > Yêu cầu sản xuất </TD>  
                            <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD>
                        </tr>
                    </TABLE>
                </TD>
            </TR>
        </TABLE>
        <TABLE width="100%" cellpadding="0" cellspacing="1">
            <TR>
                <TD>
                    <TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
                        <TR>
                            <TD width="100%" align="center" >
                            <FIELDSET>
                              <LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm &nbsp;</LEGEND>

                            <TABLE  width="100%" cellpadding="6" cellspacing="0">
                               
               				<TR style = "display:none">
		                    	<TD class="plainlabel" width="15%">Xưởng sản xuất</TD>
		                    	<TD class="plainlabel">
		                        	<SELECT name = "khoId"  style="width: 250px;" onChange = "submitform();">
		                        			<OPTION value = "" selected>Tổng cộng </OPTION>
		                        		
		                        		<% if(obj.getKhoId().equals("100005")){ %>		                        		
			                        		<OPTION value = "100005" selected>Xưởng sản xuất 1 </OPTION>
			                        		<OPTION value = "100006" >Xưởng sản xuất 6 </OPTION>
		                        				                        			
		                        		<% }else if(obj.getKhoId().equals("100006")){ %>
			                        		<OPTION value = "100005" >Xưởng sản xuất 1  </OPTION>
			                        		<OPTION value = "100006" selected>Xưởng sản xuất 6 </OPTION>
		                        			                        			
										<% }else{ %>
			                        		<OPTION value = "100005" >Xưởng sản xuất 1  </OPTION>
			                        		<OPTION value = "100006" >Xưởng sản xuất 6 </OPTION>
										
										<%}%>
								
		                        	</SELECT>
		                        	
		                    	</TD>
                   			</TR>	                            
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
		                        <%	int t = 0;
		                        
		                        	if(obj.getThang().length() > 0 ) 
		                        		t = Integer.parseInt(obj.getThang());
		                        			                        		                        	
		                        	for(int m = 1; m <= 12; m++){
		                        		if(t == m){ %>
		                        			<option value="<%= m %>" selected="selected"><%="Tháng " + m %></option>
		                       <% 		}else{ %>
		                					<option value="<%= m %>" ><%="Tháng " + m %></option>
		                       <% 		}
		                        	}
		                        %>
		                        
		                        	</SELECT>
                        		
                        		</TD>

							</TR> 
          					 <TR style = "display:none">
                    				<TD class="plainlabel" valign="middle">Chủng loại</TD>
                     				<TD class="plainlabel" valign="middle" colspan = 3>
                        				<select  id="chungloai" name="chungloai" style="width: 250px;" onChange = "submitform();">
                          					<option value=""></option>
                          			<%
	                      		if(clRs  != null)
	                       		{
	                       			while(clRs.next())
	                       			{  
		                       			if( clRs.getString("pk_seq").equals(obj.getClId())){ %>
	                        				<option value="<%= clRs .getString("pk_seq") %>" selected="selected" ><%= clRs.getString("ten") %></option>
	                        			<%}else 
		                        			{ %>
		                        			<option value="<%= clRs .getString("pk_seq") %>" ><%= clRs.getString("ten") %></option>
		                        		 <% } 
	                        		} 
	                       			clRs .close();
	                        	}
	                        	%>
                            			</select>
                      				</TD>                        
                			</TR>   
                			
                			<TR >
		                    	<TD class="plainlabel" >Loại hàng hóa</TD>
		                    	<TD class="plainlabel">
		                        	<SELECT name = "loaihanghoa"  style="width: 250px;" onChange = "submitform();">
		                        		<OPTION value = "" selected> </OPTION>
		                        		
										<!-- 0 : MUA BÊN NGOÀI,1-- SẢN XUẤT--2 GIA CÔNG-->
										<% String[] key1 = {"0","1","2"} ;
										   String[] value1 = {"Mua bên ngoài","Sản xuất","Gia công"};
										
											for(int i=0; i<key1.length; i++){
												if(obj.getLoaihh().equals(key1[i])){
													%>
													<option value="<%= key1[i] %>" selected> <%= value1[i] %> </option>
											<%	}else { %>
													<option value="<%= key1[i] %>" > <%= value1[i] %> </option>
										<% 		}
											}
										%>
								
		                        	</SELECT>
		                        	
		                    	</TD>
                   			</TR>	              			
 
                             <tr class="plainlabel"> 
                             	<td colspan="2" > 
                           			<a class="button2" href="javascript:clearform()">
										<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;	
                             	</td> 
                             </tr>
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
                            <LEGEND class="legendtitle">&nbsp;Đề nghị sản xuất &nbsp;&nbsp;	      
                            </LEGEND>
    
                            <TABLE class="" >
                        <TR>
                            <TD >
                            <TABLE  width="150%" border="0" cellspacing="2" cellpadding="4">
                                <TR class="tbheader">
									<TH width="5%" align = "center" rowspan = 2 >Mã</TH>
									<TH width="10%" align = "center" rowspan = 2 >Tên sản phẩm</TH>
									<TH width="5%" align = "center" rowspan = 2 >Đơn vị</TH>
									<TH align = "center" width="3%" rowspan = 2 >Tồn hiện tại </TH>
									<TH align = "center" width="3%" rowspan = 2 >Tồn an toàn </TH>
									<TH align = "center" width="3%" rowspan = 2 >Số lô </TH>
									<TH align = "center" width="12%" colspan = 5><%= "T." + t1 + "." + n1 %></TH>
									<TH align = "center" width="12%" colspan = 5><%= "T." + t2 + "." + n2 %></TH>
									<TH align = "center" width="%" colspan = 5><%= "T." + t3 + "." + n3 %></TH>
									
								</TR>
								<TR class="tbheader">
									<TH align = "center" width="3%">Dự báo bán hàng</TH>
									<TH align = "center" width="3%">Yêu cầu cung ứng</TH>
									<TH align = "center" width="3%">Lệnh SX</TH>
									<TH align = "center" width="3%">Yêu cầu SX</TH>
									<TH align = "center" width="3%">Đề nghị SX</TH>
									
									<TH align = "center" width="3%">Dự báo bán hàng</TH>
									<TH align = "center" width="3%">Yêu cầu cung ứng</TH>
									<TH align = "center" width="3%">Lệnh SX</TH>
									<TH align = "center" width="3%">Yêu cầu SX</TH>
									<TH align = "center" width="3%">Đề nghị SX</TH>
				           			
									<TH align = "center" width="3%">Dự báo bán hàng</TH>
									<TH align = "center" width="3%">Yêu cầu cung ứng</TH>
									<TH align = "center" width="3%">Lệnh SX</TH>
									<TH align = "center" width="3%">Yêu cầu SX</TH>
									<TH align = "center" width="3%">Đề nghị SX</TH>
									                             
								 </TR>
                                <% 
                                   
                                
                                int count = 0; 
                                    
                                String lightrow = "tblightrow";
                                String darkrow = "tbdarkrow";
                                int i = 0;
                                if(ycsxRs != null){
                                    while ( ycsxRs.next()){
									if(count % 2 == 0){ %>
								<TR class = <%= lightrow %> >
					<%				}else{ %>	
								<TR class = <%= darkrow %> >		
					<% 				}%>			                                    
                                    <TD align = "left"><%= ycsxRs.getString("MA") %></TD>
									
									<TD align = "left"><%= ycsxRs.getString("TEN") %></TD>
									<TD align = "left"><%= ycsxRs.getString("DONVI") %></TD>
									
									<TD align = "right"><%= formatter.format(ycsxRs.getDouble("TONHIENTAI")) %></TD>
									
									<TD align = "right"><%= formatter.format(ycsxRs.getDouble("TONANTOAN")) %></TD>
									
									<TD align = "right"><%= ycsxRs.getString("SOLO") %></TD>
									
									<TD align = "right"><%= formatter.format(ycsxRs.getDouble("DUBAOBAN_1")) %></TD>
																											
									<TD align = "right"><%= formatter.format(ycsxRs.getDouble("YCCU_THANG_1"))  %></TD>
									
									<TD align = "right"><%= formatter.format(ycsxRs.getDouble("SX_THANG_1"))  %></TD>
									
									<TD align = "right"><%= formatter.format(ycsxRs.getDouble("YCSX_THANG_1")) %></TD>
									
									<TD align = "right"><%= formatter.format(ycsxRs.getDouble("DNSX_THANG_1")) %></TD>
									
                                    
									<TD align = "right"><%= formatter.format(ycsxRs.getDouble("DUBAOBAN_2")) %></TD>
									
									<TD align = "right"><%= formatter.format(ycsxRs.getDouble("YCCU_THANG_2"))  %></TD>
									
									<TD align = "right"><%= formatter.format(ycsxRs.getDouble("SX_THANG_2"))  %></TD>
									
									<TD align = "right"><%= formatter.format(ycsxRs.getDouble("YCSX_THANG_2")) %></TD>
									
									<TD align = "right"><%= formatter.format(ycsxRs.getDouble("DNSX_THANG_2")) %></TD>


									<TD align = "right"><%= formatter.format(ycsxRs.getDouble("DUBAOBAN_3")) %></TD>
									
									<TD align = "right"><%= formatter.format(ycsxRs.getDouble("YCCU_THANG_3"))  %></TD>
									
									<TD align = "right"><%= formatter.format(ycsxRs.getDouble("SX_THANG_3"))  %></TD>
									
									<TD align = "right"><%= formatter.format(ycsxRs.getDouble("YCSX_THANG_3")) %></TD>

									<TD align = "right"><%= formatter.format(ycsxRs.getDouble("DNSX_THANG_3")) %></TD>
                           
							 </TR>
					<%		
                                    count++;
									}
                                }
                          %>
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
 <% for(int j = 0; j <= count; j++) { %>
		dropdowncontent.init('PlnId<%= j %>', "left-top", 300, "click");
 <% } %>
</script>

</BODY>
</html>
<% 	
	try
    {
		if(ycsxRs != null)
			ycsxRs.close();
		if(clRs != null)
			clRs.close();
	 
		if(db != null)
			db.shutDown();
		
		if(obj != null)
		{
			obj.DbClose();
			obj = null;
		}
		session.setAttribute("obj", null);
		session.setAttribute("lsxdklist", null) ;  ;  ; ;
		
		
	}
	catch (SQLException e) {} %>
<%}%>