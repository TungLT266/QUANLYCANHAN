<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.lapkehoach.imp.*" %>
<%@ page import="geso.traphaco.erp.beans.lapkehoach.*" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@page import="java.sql.SQLException" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.text.DecimalFormat" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<%
 	IErpXemkehoach obj =(IErpXemkehoach)session.getAttribute("xkhBean");
	ResultSet ttppRs = obj.getKhoTTRs();
	ResultSet spRs = obj.getSpRs();
	
	ResultSet thangRs = obj.getThangRs();
	ResultSet namRs = obj.getNamRs();
	
	ResultSet nhanhangRs = obj.getNhanhangRs();
	ResultSet chungloaiRs = obj.getChungloaiRs();

	NumberFormat formatter = new DecimalFormat("#,###,###"); 
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>

<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>

	
<script>

	$(function() {
	 // setup ul.tabs to work as tabs for each div directly under div.panes
	 	$("ul.tabs").tabs("div.panes > div");

	});
	
</script>



<SCRIPT language="JavaScript" type="text/javascript">
	function submitform()
	{
	    document.forms["khtt"].submit();
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
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="khtt" method="post" action="../../ErpXemkehoachUpdateSvl" >
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="id" value='<%= obj.getId() %>' >
<input type="hidden" name="action" value="0">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						  <TR height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý cung ứng > Lập kế hoạch > Xem kế hoạch</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD></TR>
						</TABLE>
			 		</TD>
				</TR>
			</TABLE>
			
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<TR>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Thông báo </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; color:#F00 ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</TR>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Xem kế hoạch </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
                           <TR>
		                        <TD class="plainlabel" valign="middle" >Tháng </TD>
		                        <TD class="plainlabel" valign="middle" width="220px" >
		                            <select name="thang" onchange="submitform();" >
		                            	<option value=""></option>
		                            	<%
			                        		if(thangRs != null)
			                        		{
			                        			while(thangRs.next())
			                        			{  
			                        			if( thangRs.getString("thang").equals(obj.getThang())){ %>
			                        				<option value="<%= thangRs.getString("thang") %>" selected="selected" ><%= thangRs.getString("thangTen") %></option>
			                        			<%}else { %>
			                        				<option value="<%= thangRs.getString("thang") %>" ><%= thangRs.getString("thangTen") %></option>
			                        		 <% } } thangRs.close();
			                        		}
			                        	%>
		                            </select>
		                        </TD>   
		                        <TD class="plainlabel" valign="middle" width="100px" >Năm </TD>   
		                        <TD class="plainlabel" valign="middle" >
		                        	<select name="nam" onchange="submitform();" >
		                            	<option value=""></option>
		                            	<%
			                        		if(namRs != null)
			                        		{
			                        			while(namRs.next())
			                        			{  
			                        			if( namRs.getString("nam").equals(obj.getNam())){ %>
			                        				<option value="<%= namRs.getString("nam") %>" selected="selected" ><%= namRs.getString("namTen") %></option>
			                        			<%}else { %>
			                        				<option value="<%= namRs.getString("nam") %>" ><%= namRs.getString("namTen") %></option>
			                        		 <% } } namRs.close();
			                        		}
			                        	%>
		                            </select> 
		                        </TD>                
		                  </TR>
                           <TR>
		                        <TD class="plainlabel" valign="middle" width="150px" >Trung tâm phân phối</TD>
		                        <TD class="plainlabel" valign="middle" colspan="3" >
		                            <select name="khoId" onchange="submitform();" >
		                            	<%
			                        		if(ttppRs != null)
			                        		{
			                        			while(ttppRs.next())
			                        			{  
			                        			if( ttppRs.getString("pk_seq").equals(obj.getKhoId())){ %>
			                        				<option value="<%= ttppRs.getString("pk_seq") %>" selected="selected" ><%= ttppRs.getString("ten") %></option>
			                        			<%}else { %>
			                        				<option value="<%= ttppRs.getString("pk_seq") %>" ><%= ttppRs.getString("ten") %></option>
			                        		 <% } } ttppRs.close();
			                        		}
			                        	%>
		                            </select>
		                        </TD>                        
		                  </TR> 
		                  <TR>
		                        <TD class="plainlabel" valign="middle" >Nhãn hàng </TD>
		                        <TD class="plainlabel" valign="middle" width="220px" >
		                            <select name="nhId" onchange="submitform();" >
		                            	<option value=""></option>
		                            	<%
			                        		if(nhanhangRs != null)
			                        		{
			                        			while(nhanhangRs.next())
			                        			{  
			                        			if( nhanhangRs.getString("pk_seq").equals(obj.getNhId())){ %>
			                        				<option value="<%= nhanhangRs.getString("pk_seq") %>" selected="selected" ><%= nhanhangRs.getString("ten") %></option>
			                        			<%}else { %>
			                        				<option value="<%= nhanhangRs.getString("pk_seq") %>" ><%= nhanhangRs.getString("ten") %></option>
			                        		 <% } } nhanhangRs.close();
			                        		}
			                        	%>
		                            </select>
		                        </TD>   
		                        <TD class="plainlabel" valign="middle" width="100px" >Chủng loại </TD>   
		                        <TD class="plainlabel" valign="middle" >
		                        	<select name="clId" onchange="submitform();" >
		                            	<option value=""></option>
		                            	<%
			                        		if(chungloaiRs != null)
			                        		{
			                        			while(chungloaiRs.next())
			                        			{  
			                        			if( chungloaiRs.getString("pk_seq").equals(obj.getClId())){ %>
			                        				<option value="<%= chungloaiRs.getString("pk_seq") %>" selected="selected" ><%= chungloaiRs.getString("ten") %></option>
			                        			<%}else { %>
			                        				<option value="<%= chungloaiRs.getString("pk_seq") %>" ><%= chungloaiRs.getString("ten") %></option>
			                        		 <% } } chungloaiRs.close();
			                        		}
			                        	%>
		                            </select> 
		                        </TD>                
		                  </TR>
		                  <TR>
		                        <TD class="plainlabel" valign="middle" >Sản phẩm </TD>
		                        <TD class="plainlabel" valign="middle" width="220px" >
		                            <select name="spId" onchange="submitform();" >
		                            	<option value=""></option>
		                            	<%
			                        		if(spRs != null)
			                        		{
			                        			while(spRs.next())
			                        			{  
			                        			if( spRs.getString("spId").equals(obj.getSpId())){ %>
			                        				<option value="<%= spRs.getString("spId") %>" selected="selected" ><%= spRs.getString("ten") %></option>
			                        			<%}else { %>
			                        				<option value="<%= spRs.getString("spId") %>" ><%= spRs.getString("ten") %></option>
			                        		 <% } } spRs.close();
			                        		}
			                        	%>
		                            </select>
		                        </TD>   
		                        <TD class="plainlabel" valign="middle" width="100px" >Đơn vị </TD>   
		                        <TD class="plainlabel" valign="middle" >
		                        	<input type="text" name="donvi" value="<%= obj.getDonvi() %>" readonly="readonly" > 
		                        </TD>                
		                  </TR> 
		                </TABLE>
		                  
						<TABLE width="100%" align="center" cellpadding="6" cellspacing="1px">
							<TR class="tbheader">
								<TH width="10%" align="center" rowspan = 2 >Ngày</TH>
								<TH width="10%" align="center" rowspan = 2 >Tồn</TH>
								<TH width="10%" align="center" rowspan = 2 >Tồn an toàn</TH>
								<TH width="20%" align="center" colspan = 2 >Lệnh sản xuất</TH>
								<TH width="10%" align="center" rowspan = 2>Yêu cầu</TH>
								<TH width="10%" align="center" rowspan = 2>Đơn hàng bán</TH>
								<TH width="30%" align="center" rowspan = 2>Ghi chú</TH>
							</TR>
													
							<TR class="tbheader">
								<TH width="10%" align="center">Dự kiến</TH>
								<TH width="10%" align="center">Thực tế</TH>
							</TR>
					<%
               			int m = 0;
           				if((m % 2 ) == 0) {%>
                         	<TR class='tbdarkrow'>
                        <%}else{ %>
                          	<TR class='tblightrow'>
                        <%} 
     if(obj.getSpId().length() > 0 & obj.getKhoId().length() > 0){
       	long tondau = Long.parseLong(obj.getTondau());
       	ResultSet rs;
       	long yeucau;
                        %>
								<TD align = "center"><%= obj.getNgayhientai() %></TD>
								<TD align = "right"><%= formatter.format(tondau) %>	</TD>
								
								<TD>&nbsp;</TD>				
								<TD>&nbsp;</TD>
								<TD>&nbsp;</TD>
								<TD>&nbsp;</TD>
								<TD>&nbsp;</TD>
								<TD>&nbsp;</TD>
							</TR>
			<%	m ++;
				int thang = Integer.parseInt(obj.getThang());
				int nam = Integer.parseInt(obj.getNam());
           					
           		try{
           			String ngayhientai = obj.getNgayhientai();
           			
           			int namhientai = Integer.parseInt(ngayhientai.substring(0, 4));
           			int thanghientai = Integer.parseInt(ngayhientai.substring(5, 7));
           			String ngaycuoithang = namhientai + "-" + thanghientai + "-" + obj.getNgaycuoithang("" + thanghientai, "" + namhientai);
           			
           			String tungay = ngayhientai;   
           			String denngay = ngaycuoithang;
        		
           			// Lay PRO va PLO tu ngay hien tai den cuoi thang hien tai
					rs = obj.getLenhSanXuat(tungay, denngay);           		
					
                    if(rs != null){
						while(rs.next()){
	           				if((m % 2 ) == 0) { %>
		                         	
		                    <TR class='tbdarkrow'>
		                        	
	                   	<%  }else{ %>
		                          	
		                    <TR class='tblightrow'>
		                        	
	                   	<%  } 

							if(rs.getString("ID").contains("PLO")){ 
								tondau = tondau + Math.round(Float.parseFloat(rs.getString("QTY")));
									
							%> 
								
								<TD align = "center"><%= rs.getString("NGAY")%></TD>
								<TD align = "right"><%= formatter.format(tondau) %></TD>
								<TD align = "right">&nbsp;</TD>								
								<TD align = "right"><%= formatter.format(Math.round(Float.parseFloat(rs.getString("QTY")))) %> </TD>
								<TD>&nbsp;</TD>
								<TD>&nbsp;</TD>
								<TD>&nbsp;</TD>
								<TD><%= rs.getString("ID") %></TD>
			
						<%	}else
								
							if(rs.getString("ID").contains("PRO")){ 
								tondau = tondau + Math.round(Float.parseFloat(rs.getString("QTY"))); %>
									
								<TD align = "center"><%= rs.getString("NGAY")%></TD>
								<TD align = "right"><%= formatter.format(tondau) %></TD>
								<TD align = "right">&nbsp;</TD>								
								<TD>&nbsp;</TD>
								<TD align = "right"><%= formatter.format(Math.round(Float.parseFloat(rs.getString("QTY")))) %> </TD>								
								<TD>&nbsp;</TD>
								<TD>&nbsp;</TD>
								<TD><%= rs.getString("ID") %></TD>
									 										
						<%	}else{ %>	
								<TD>&nbsp;</TD>
								<TD>&nbsp;</TD>
								<TD>&nbsp;</TD>												
								<TD>&nbsp;</TD>
								<TD>&nbsp;</TD>
								<TD>&nbsp;</TD>
								<TD>&nbsp;</TD>
								<TD>&nbsp;</TD>
								
								
						<%	} 	
							m++; %>
							</TR>
				<%		}
						rs.close();
					}
                	int thangdau ;
           			if(nam == (namhientai + 1)){
           				thangdau = 1;
           			}else{
           				thangdau = thanghientai + 1;
           			}
           		
           			for(int i =  thangdau; i <= thang; i++){
					rs = obj.getYeuCau(i, nam);	
							
					if(rs != null){
           				if((m % 2 ) == 0) { %>
                         	
                         	<TR class='tbdarkrow'>
                        	
                      <%}else{ %>
                          	
                          	<TR class='tblightrow'>
                        	
                      <%}

						rs.next(); 
           				yeucau = Math.round(Float.parseFloat(rs.getString("TONKHOANTOAN")));
           				ngaycuoithang = nam + "-0" + i + "-" + obj.getNgaycuoithang("" + i, "" + nam);
           				tondau = tondau - yeucau;
           				%>
           				
           						
								<TD align = "center"><%= ngaycuoithang %></TD>
								<TD align = "right"><%= formatter.format(tondau) %></TD>

								<TD align = "right"><%= formatter.format((-1)*yeucau) %></TD>
								<TD align = "right">&nbsp;</TD>
								<TD>&nbsp;</TD>
								<TD>&nbsp;</TD>
								<TD>&nbsp;</TD>
								<TD>Tồn an toàn cho tháng  <%= i %>/ <%= nam %></TD>
							</TR>
						<%		
						 
						m++;
						
						if((m % 2 ) == 0) { %>

                         	<TR class='tbdarkrow'>
                        	
                     <% }else{ %>
                          	
                          	<TR class='tblightrow'>
                        	
                     <% }
						yeucau = Math.round(Float.parseFloat(rs.getString("YEUCAU")));
						tondau = tondau - yeucau; 
						%>

           						
								<TD align = "center"><%= ngaycuoithang %></TD>
								<TD align = "right"><%= formatter.format(tondau) %></TD>

								<TD align = "right">&nbsp;</TD>
								<TD>&nbsp;</TD>
								<TD align = "right">&nbsp;</TD>
								<TD align = "right"><%= formatter.format((-1)*yeucau) %></TD>
								<TD>&nbsp;</TD>
								<TD>Yêu cầu từ dự báo cho tháng  <%= i %>/ <%= nam %></TD>
							</TR>	
				<%		
						rs.close();
						m++;
					
					// Hien du lieu thang da chon
					tungay = nam + "-0" + i + "-01";
					denngay = nam + "-0" + i + "-" + obj.getNgaycuoithang(""+i, ""+nam);
					
					System.out.println("Tu ngay: " + tungay + "; den ngay: " + denngay);
					rs = obj.getLenhSanXuat(tungay, denngay);
				           		
			        if(rs != null){
						while(rs.next()){
				    		if((m % 2 ) == 0) { %>
					                         	
					        <TR class='tbdarkrow'>
					                       	
				        <%  }else{ %>
					                          	
					        <TR class='tblightrow'>
					                        	
				        <%  } 

							if(rs.getString("ID").contains("PLO")){ 
								tondau = tondau + Math.round(Float.parseFloat(rs.getString("QTY")));
												
						%> 
											
								<TD align = "center"><%= rs.getString("NGAY")%></TD>
								<TD align = "right"><%= formatter.format(tondau) %></TD>
								<TD align = "right">&nbsp;</TD>								
								<TD align = "right"><%= formatter.format(Math.round(Float.parseFloat(rs.getString("QTY")))) %> </TD>
								<TD>&nbsp;</TD>
								<TD>&nbsp;</TD>
								<TD>&nbsp;</TD>
								<TD><%= rs.getString("ID") %></TD>
						
						<%	}else
											
							if(rs.getString("ID").contains("PRO")){ 
								tondau = tondau + Math.round(Float.parseFloat(rs.getString("QTY"))); %>
												
								<TD align = "center"><%= rs.getString("NGAY")%></TD>
								<TD align = "right"><%= formatter.format(tondau) %></TD>
								<TD align = "right">&nbsp;</TD>								
								<TD>&nbsp;</TD>
								<TD align = "right"><%= formatter.format(Math.round(Float.parseFloat(rs.getString("QTY")))) %> </TD>								
								<TD>&nbsp;</TD>
								<TD>&nbsp;</TD>
								<TD><%= rs.getString("ID") %></TD>
												 										
						<%	}else{ %>	
								<TD>&nbsp;</TD>
								<TD>&nbsp;</TD>
								<TD>&nbsp;</TD>												
								<TD>&nbsp;</TD>
								<TD>&nbsp;</TD>
								<TD>&nbsp;</TD>
								<TD>&nbsp;</TD>
								<TD>&nbsp;</TD>
						<%	} 	
							m++; %>
						</TR>
				<%		}
						rs.close();
					} 
				}
           	}
           
          }catch(java.sql.SQLException e){}
	}else{ %>
								<TD align = "center">&nbsp;</TD>
								<TD align = "right">&nbsp;</TD>
								<TD>&nbsp;</TD>				
								<TD>&nbsp;</TD>
								<TD>&nbsp;</TD>
								<TD>&nbsp;</TD>
								<TD>&nbsp;</TD>
								<TD>&nbsp;</TD>
								
<%  } %>						
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
	dropdowncontent.init('spId', "right-bottom", 300, "click");
</script>
</BODY>
</HTML>
<%}%>
