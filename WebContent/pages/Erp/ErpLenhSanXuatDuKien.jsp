<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import="geso.traphaco.erp.beans.lapkehoach.IErpLenhsanxuatdkList"%>
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
	IErpLenhsanxuatdkList obj =(IErpLenhsanxuatdkList)session.getAttribute("lsxdklist");
	ResultSet dnsxRs = obj.getLenhsanxuatdkRs();
	String thang = obj.getThang();
	String nam = obj.getNam();
	ResultSet nmRs  = (ResultSet) obj.getNhamayRS();
	ResultSet spRs  = (ResultSet) obj.getSanphamRS();
	

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
	    document.forms['lsxdk'].nam.value= "";
	    document.forms['lsxdk'].thang.value= "";
	    document.forms['lsxdk'].spId.value = "";
	    document.forms['lsxdk'].chungloai.value = "";
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
		var Id = PlId;
	
		var k = document.getElementById('KbsxId' + PlId);
		var kbsxId = k.options[k.selectedIndex].value;
		
//		alert(kbsxId);
		
		if(soluong == ''){
			alert('Vui lòng nhập số lượng sản xuất');
			return;
		}
		
		document.forms["lsxdk"].soluongsx.value = soluong;
		document.forms["lsxdk"].Id.value = Id;
		document.forms["lsxdk"].kbsxId.value = kbsxId;
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
	 function exportExcelKeHoachSanXuat()
	 {
		 document.forms['lsxdk'].action.value='exportExcelKHSX';
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
<form name="lsxdk" method="post" action="../../ErpLenhsanxuatdukienSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >

<input type="hidden" name="kbsxId" value="" >
<input type="hidden" name="Id" value="" >
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
                            <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý cung ứng > Lập kế hoạch > Đề nghị sản xuất </TD>  
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
          					 <TR>
                    				<TD class="plainlabel" valign="middle">Phân xưởng</TD>
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
                			
          					 
                			
                			<TR >
		                    	<TD class="plainlabel" >Loại hàng hóa</TD>
		                    	<TD class="plainlabel">
		                        	<SELECT name = "loaihanghoa"  style="width: 250px;" onChange = "submitform();">
		                        		<OPTION value = "" selected> </OPTION>
		                        		
										<!-- 0 : MUA BÊN NGOÀI,1-- SẢN XUẤT--2 GIA CÔNG-->
										<% String[] key1 = {"1","2"} ;
										   String[] value1 = {"Sản xuất","Gia công"};
										
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
				  	  			
				  	  			&nbsp;&nbsp;&nbsp;
				  	  	  			<a href="javascript:exportExcelKeHoachSanXuat();" class="button"> <img alt="" src="../images/button.png" style="top: -4px;"> Xuất kế hoạch sản xuất và mua hàng</a>
				  	  	  			&nbsp;&nbsp;&nbsp;
				  	  			
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
                            <TABLE  width="100%" border="0" cellspacing="2" cellpadding="4">
                                <TR class="tbheader">
                                	<TH width="5%" align = "center" rowspan = 2 > ID</TH>
									<TH width="20%" align = "center" rowspan = 2 >Sản phẩm</TH>
									<TH align = "center" width="8%"  colspan = 4 >Lệnh sản xuất </TH>
									<TH align = "center" width="10%" colspan = 4 >Đề nghị sản xuất </TH>
									<TH align = "center" width="7%" rowspan = 2 >Số lô </TH>
									<TH align = "center" width="8%" rowspan = 2 >Nhà máy </TH>
									<TH align = "center" width="14%" rowspan = 2  >Chuyển sang LSX</TH>
									
								</TR>
								<TR class="tbheader">
									<TH align = "center" width="7%" >Tuần 1</TH>
									<TH align = "center" width="7%" >Tuần 2</TH>
									<TH align = "center" width="7%" >Tuần 3</TH>
									<TH align = "center" width="7%" >Tuần 4</TH>

									<TH align = "center" width="7%" >Tuần 1</TH>
									<TH align = "center" width="7%" >Tuần 2</TH>
									<TH align = "center" width="7%" >Tuần 3</TH>
									<TH align = "center" width="7%" >Tuần 4</TH>
								</TR>
                                <% 
                                String nam_thang = obj.getNam() + "-" + (obj.getThang().length() == 1?"0" + obj.getThang():obj.getThang()) + "-";
                                int count = 0; 
                                String lightrow = "tblightrow";
                                String darkrow = "tbdarkrow";
								String spId = "", spId_bk = "";
								
								if(dnsxRs != null){
                                    while ( dnsxRs.next()){
									if(count % 2 == 0){ %>
								<TR class = <%= lightrow %> >
					<%				}else{ %>	
								<TR class = <%= darkrow %> >		
					<% 				}			                                    
									
									spId = 	dnsxRs.getString("SPID");
									if(!spId.equals(spId_bk))
									{
	                                	String tmp[] = obj.getSX_Tuan(nam_thang, spId); %>
	                                 	
										<TD align = "left" colspan = "2" ><B><%= dnsxRs.getString("MA") + " - " + dnsxRs.getString("TEN") + " - " + dnsxRs.getString("DONVI") %></B></TD>
	                                	
										<TD align = "right" ><%= formatter.format(Double.parseDouble(tmp[0])) %></TD>
										<TD align = "right" ><%= formatter.format(Double.parseDouble(tmp[1])) %></TD>
										<TD align = "right" ><%= formatter.format(Double.parseDouble(tmp[2]))  %></TD>
										<TD align = "right" ><%= formatter.format(Double.parseDouble(tmp[2])) %></TD>
										
										<TD align = "right" ></TD>
										<TD align = "right" ></TD>
										<TD align = "right" ></TD>
										<TD align = "right" ></TD>
										<TD align = "right" ></TD>
										<TD align = "right" ></TD>
										
										<TD align = "left" >&nbsp;</TD>
									
					<%					spId_bk = spId;
									}
									count ++; 

									if(count % 2 == 0){ %>
								<TR class = <%= lightrow %> >
					<%				}else{ %>	
								<TR class = <%= darkrow %> >		
					<% 				}%>			                                    
 
                                    <TD align = "center"><%= dnsxRs.getString("ID") %></TD>
									
									<TD align = "left"></TD>
									
									<TD align = "left"></TD>

									<TD align = "left"></TD>
									
									<TD align = "left"></TD>
																		
									<TD align = "left"></TD>
									
									<TD align = "right"><%= formatter.format(dnsxRs.getDouble("TUAN_1")) %></TD>
									
									<TD align = "right"><%= formatter.format(dnsxRs.getDouble("TUAN_2"))  %></TD>
									
									<TD align = "right"><%= formatter.format(dnsxRs.getDouble("TUAN_3")) %></TD>
									
									<TD align = "right"><%= formatter.format(dnsxRs.getDouble("TUAN_4")) %></TD>
                                    
									<TD align = "center" ><%= dnsxRs.getString("SOLO") %></TD>
									<TD align = "center" ><%= dnsxRs.getString("NHAMAY") %></TD>
                                    
    								<TD align="center">           
							                  		
							  <% if(dnsxRs.getDouble("SANXUAT") < dnsxRs.getDouble("TUAN_1") ||
									dnsxRs.getDouble("SANXUAT") < dnsxRs.getDouble("TUAN_2") ||
									dnsxRs.getDouble("SANXUAT") < dnsxRs.getDouble("TUAN_3") ||
									dnsxRs.getDouble("SANXUAT") < dnsxRs.getDouble("TUAN_4")
							  	  ){
					                String query =  "SELECT PK_SEQ AS KBSXID, CAST(PK_SEQ  AS NVARCHAR(10)) + ', ' + DIENGIAI AS KBSXTEN " +
					                				"FROM ERP_KICHBANSANXUAT_GIAY " +
					                				"WHERE SANPHAM_FK = '" + dnsxRs.getString("SPID") + "' " +
					                				"AND TRANGTHAI = '1'";
							                   			
					
							                   			
					                ResultSet rsKbsx = db.get(query);
					                if(rsKbsx != null)
					                {
					                                		%>              		
										<a href="" id="PlnId<%= count %>" rel="subcontentPlnId<%= count %>"> 
										<img alt="Chuyển sang lệnh sản xuất"  title="Chuyển sang lệnh sản xuất" src="../images/Next20.png"></a>
					           	 		
									    <DIV id="subcontentPlnId<%= count %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
										 background-color: white; width: 720px; max-height:250px; overflow:auto; padding: 4px; z-index: 100000000; ">
										<table width="98%" align="center" cellpadding="6px" cellspacing="6px">
											<tr>
											   <td width="120px" style="font-size: 12px">Sản phẩm</td>
											   <td width="120px" style="font-size: 12px">
											    <%= dnsxRs.getString("TEN") %>
											   </td>
											</tr>
											<tr>
												<td width="120px" style="font-size: 12px">Kịch bản</td>
											   	<td width="600px" style="font-size: 12px">
											      <select style="width: 100%" id="KbsxId<%= dnsxRs.getString("ID") %>">
											   
											<%
											  		while(rsKbsx.next())
											   		{ %>
											                            			
														<option value="<%= rsKbsx.getString("KBSXID") %>" > <%= rsKbsx.getString("KBSXTEN") %> </option>
											<%  	}  %>
												 </select>
												</td>
											</tr>
											<tr>
											    <td  style="font-size: 12px">Số lượng SX</td>
											    <td style="font-size: 12px">
												                            
											        <input  id="soluong<%= dnsxRs.getString("ID") %>" type="text" style="text-align: right;" value = <%= formatter.format(dnsxRs.getDouble("TUAN_1") + dnsxRs.getDouble("TUAN_2") +dnsxRs.getDouble("TUAN_3") + dnsxRs.getDouble("TUAN_4") - dnsxRs.getDouble("SANXUAT"))%> onkeypress="keypress(event);"> 
												</td>
											</tr>
										</table>
										    <div align="right">     
										       	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										      	<a href="javascript:XacNhanChuyen('<%= dnsxRs.getString("ID") %>');" > <b>Chuyển thành LSX</b> </a>
										      	&nbsp;|&nbsp; 
										       	<a href="javascript:dropdowncontent.hidediv('subcontentPlnId<%= count %>')" > <b>Đóng lại</b> </a>
										    </div>
										    </DIV> 
					                                		
					                   <%  } 
					                		
					                		
										}else{ %>
								
										<%= formatter.format(dnsxRs.getDouble("SANXUAT")) %>			
							 <% 			}
                                    }
							  %>							
    								
    								</TD>
                            
							 </TR>
					<%		}
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
		if(dnsxRs != null)
			dnsxRs.close();
		if(nmRs != null)
			nmRs.close();
	 
		if(spRs != null)
			spRs.close();
		
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