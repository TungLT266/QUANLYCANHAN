<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import="geso.traphaco.erp.beans.lapkehoach.IErp_YeucaumuaNL"%>
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
	IErp_YeucaumuaNL obj =(IErp_YeucaumuaNL)session.getAttribute("ycNL");
	ResultSet ycNLRs = obj.getYeucaumuaNLRs();
	String thang = obj.getThang();
	String nam = obj.getNam();
	ResultSet nmRs  = (ResultSet) obj.getNhamayRS();

	String t1 = "", t2 = "", t3 = "", t4 = "", n1 = "", n2 = "", n3 = "", n4 = "";
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

	if(t3.equals("12")){
		t4 = "1";
		n4 = "" + (Integer.parseInt(n3) + 1);
	}else{
		t4 = "" + (Integer.parseInt(t3) + 1);
		n4 = n3;
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
	    document.forms['ycNL'].dvkdId.value= "";
	    document.forms['ycNL'].nam.value= "";
	    document.forms['ycNL'].thanglsx.value= "";
	    document.forms['ycNL'].trangthai.value = "";
		document.forms['ycNL'].submit();
	}

	function submitform()
	{
		document.forms['ycNL'].action.value= 'search';
		document.forms['ycNL'].submit();
	}

	function newform()
	{
		document.forms['ycNL'].action.value= 'new';
		document.forms['ycNL'].submit();
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
		 document.forms['ycNL'].action.value='readExcel';
		 document.forms['ycNL'].setAttribute('enctype', "multipart/form-data", 0);
	     document.forms['ycNL'].submit();
	 }
	 
	 function exportExcel()
	 {
		 document.forms['ycNL'].action.value='exportExcel';
	     document.forms['ycNL'].submit();
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
<form name="ycNL" method="post" action="../../Erp_YeucaumuaNLSvl">
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
                            <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý cung ứng > Lập kế hoạch > Yêu cầu mua nguyên liệu </TD>  
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
                            <LEGEND class="legendtitle">&nbsp;Yêu cầu mua nguyên liệu &nbsp;&nbsp;	      
                            </LEGEND>
    
                            <TABLE class="" >
                        <TR>
                            <TD >
                            <TABLE  width="180%" border="0" cellspacing="2" cellpadding="4">
                                <TR class="tbheader">
									<TH width="3%" align = "center" rowspan = 2 >Mã</TH>
									<TH width="8%" align = "center" rowspan = 2 >Tên sản phẩm</TH>
									<TH align = "center" width="3%" rowspan = 2 >Tồn hiện tại </TH>
									<TH align = "center" width="3%" rowspan = 2 >Tồn an toàn </TH>
									<TH align = "center" width="12%" colspan = 4><%= "T." + t1 + "." + n1 %></TH>
									<TH align = "center" width="12%" colspan = 4><%= "T." + t2 + "." + n2 %></TH>
									<TH align = "center" width="%" colspan = 4><%= "T." + t3 + "." + n3 %></TH>
									<TH align = "center" width="%" colspan = 4><%= "T." + t4 + "." + n4 %></TH>
									
								</TR>
								<TR class="tbheader">
									<TH align = "center" width="3%">Yêu cầu NL</TH>									
									<TH align = "center" width="3%">Cần bổ sung</TH>
									<TH align = "center" width="3%">Đơn mua hàng</TH>
									<TH align = "center" width="3%">Đề nghị mua</TH>
									
									<TH align = "center" width="3%">Yêu cầu NL</TH>
									<TH align = "center" width="3%">Cần bổ sung</TH>
									<TH align = "center" width="3%">Đơn mua hàng</TH>
									<TH align = "center" width="3%">Đề nghị mua</TH>
				           			
									<TH align = "center" width="3%">Yêu cầu NL</TH>
									<TH align = "center" width="3%">Cần bổ sung</TH>
									<TH align = "center" width="3%">Đơn mua hàng</TH>
									<TH align = "center" width="3%">Đề nghị mua</TH>

									<TH align = "center" width="3%">Yêu cầu NL</TH>
									<TH align = "center" width="3%">Cần bổ sung</TH>
									<TH align = "center" width="3%">Đơn mua hàng</TH>
									<TH align = "center" width="3%">Đề nghị mua</TH>
									                             
								 </TR>
                                <% 
                                   
                                
                                int count = 0; 
                                    
                                String lightrow = "tblightrow";
                                String darkrow = "tbdarkrow";
                                int i = 0;
                                if(ycNLRs != null){
                                    while ( ycNLRs.next()){
									if(count % 2 == 0){ %>
								<TR class = <%= lightrow %> >
					<%				}else{ %>	
								<TR class = <%= darkrow %> >		
					<% 				}%>			                                    
                                    <TD align = "left"><%= ycNLRs.getString("MA") %></TD>
									
									<TD align = "left"><%= ycNLRs.getString("TEN") %></TD>
									
									<TD align = "right"><%= formatter.format(ycNLRs.getDouble("TONHIENTAI")) %></TD>
									
									<TD align = "right"><%= formatter.format(ycNLRs.getDouble("TONANTOAN")) %></TD>
									
									<TD align = "right"><%= formatter.format(ycNLRs.getDouble("YCNL_THANG_1")) %></TD>
									
									<TD align = "right"><%= formatter.format(ycNLRs.getDouble("YCCU_THANG_1")) %></TD>
									
									<TD align = "right"><%= formatter.format(ycNLRs.getDouble("MH_THANG_1")) %></TD>
									
									<TD align = "right"><%= formatter.format(ycNLRs.getDouble("DNMH_THANG_1"))  %></TD>
									
	                                
									<TD align = "right"><%= formatter.format(ycNLRs.getDouble("YCNL_THANG_2")) %></TD>
									
									<TD align = "right"><%= formatter.format(ycNLRs.getDouble("YCCU_THANG_2")) %></TD>
									
									<TD align = "right"><%= formatter.format(ycNLRs.getDouble("MH_THANG_2")) %></TD>
									
									<TD align = "right"><%= formatter.format(ycNLRs.getDouble("DNMH_THANG_2"))  %></TD>
									
											
									<TD align = "right"><%= formatter.format(ycNLRs.getDouble("YCNL_THANG_3")) %></TD>
									
									<TD align = "right"><%= formatter.format(ycNLRs.getDouble("YCCU_THANG_3")) %></TD>									
									
									<TD align = "right"><%= formatter.format(ycNLRs.getDouble("MH_THANG_3")) %></TD>
									
									<TD align = "right"><%= formatter.format(ycNLRs.getDouble("DNMH_THANG_3"))  %></TD>
									
																		
									<TD align = "right"><%= formatter.format(ycNLRs.getDouble("YCNL_THANG_4")) %></TD>
									
									<TD align = "right"><%= formatter.format(ycNLRs.getDouble("YCCU_THANG_4")) %></TD>									
									
									<TD align = "right"><%= formatter.format(ycNLRs.getDouble("MH_THANG_4")) %></TD>
									
									<TD align = "right"><%= formatter.format(ycNLRs.getDouble("DNMH_THANG_4"))  %></TD>
                           
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
		if(ycNLRs != null)
			ycNLRs.close();
		if(nmRs != null)
			nmRs.close();
	 
		if(db != null)
			db.shutDown();
		
		if(obj != null)
		{
			obj.DbClose();
			obj = null;
		}
		session.setAttribute("obj", null);
		session.setAttribute("ycNL", null);
		
		
	}
	catch (SQLException e) {} %>
<%}%>