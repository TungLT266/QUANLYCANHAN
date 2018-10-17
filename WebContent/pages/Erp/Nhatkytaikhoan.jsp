<%@page import="geso.traphaco.center.util.Erp_Item"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.erp.beans.bangchitiettaikhoan.INhatkytaikhoan"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page import="java.util.List"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	  


	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	INhatkytaikhoan obj = (INhatkytaikhoan) session.getAttribute("obj");
	ResultSet tk = obj.getTaikhoan();
	ResultSet rs = obj.getData();
	String view = (String) session.getAttribute("view");  
	ResultSet ctyRs = obj.getCtyRs();
	String [] ctyIds = obj.getCtyIds();
	List<Erp_Item> nhomTaiKhoanList = obj.getNhomTaiKhoanList();
	List<Erp_Item> loaiNghiepVuList = obj.getLoaiNghiepVuList();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>TraphacoERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery-1.js"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

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
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button1").hover(function(){
                $(".button1 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        });
		
    </script>

<script type="text/javascript" src="../scripts/report-js/action-report.js"></script>
<script language="javascript" type="text/javascript">	
	function seach()
	{
		if(!kiemTra()){
			return false;
		}
		document.forms['frm'].action.value= 'seach';
		document.forms["frm"].submit();
	}

	function Change()
	{   
		if(!kiemTra()){
			return false;
		}
   		document.forms["frm"].action.value="change";
   		document.forms["frm"].submit();
	}

	function submitform() {
		if(!kiemTra()){
			return false;
		}
		document.forms['frm'].action.value= 'tao';
		document.forms["frm"].submit();
	}

	function setErrors(errorMsg) {
		var errors = document.getElementById("errors");
		errors.value = errorMsg;
	}
	
	function xuatExcel()
	{
		if(!kiemTra()){
			return false;
		}
		document.forms['frm'].action.value= 'excel';
		document.forms['frm'].submit();
	}
	
	function TinhTongTien()
	{		
	    var sumno = document.getElementById("sumno").value;
	    var sumco = document.getElementById("sumco").value;
		if(sumno == ''){
			 sumno = 0;
			 document.getElementById("sumno").value = '0';
		}
		else{
			document.getElementById("sumno").value = DinhDangTien(sumno);
		}
		
		if(sumco == ''){
			 sumco = 0;
			 document.getElementById("sumco").value = '';
		}
		else{
			document.getElementById("sumco").value = DinhDangTien(sumco);
		}
		
		document.getElementById("sumcoht").value = DinhDangTien(sumco);
		document.getElementById("sumnoht").value = DinhDangTien(sumno);
	}
	
	function DinhDangTien(num) 
	 {
	    num = num.toString().replace(/\$|\,/g,'');
	    if(isNaN(num))
	    num = "0";
	    sign = (num == (num = Math.abs(num)));
	    num = Math.floor(num*100+0.50000000001);
	    num = Math.floor(num/100).toString();
	    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	    num = num.substring(0,num.length-(4*i+3))+','+
	    num.substring(num.length-(4*i+3));
	    return (((sign)?'':'-') + num);
	}
	function kiemTra(){
		var tuNgay = document.getElementById("tungay");
		var denNgay = document.getElementById("denngay");
		if (tuNgay.value.trim().length ==0 || denNgay.value.trim().length == 0){
			alert ('Vui lòng nhập ngày bắt đầu và ngày kết thúc lấy báo cáo .');
			return false;
		}else if(tuNgay.value.trim() <= '2016-09-30' || denNgay.value.trim() <='2016-09-30'){
			alert ( 'Ngày DKSD : 30-09-2016 . Vui lòng nhập thời gian lấy báo cáo từ ngày 01-10-2016');
			return false;
		}
		return true;
	}
</script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2(); });
     
</script>

</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="frm" method="post" action="../../NhatkytaikhoanSvl">
		<input type="hidden" name="action" value='1'> 
		<input type="hidden" name="userId" value='<%=userId%>'>
		<input type="hidden" name="view" value="<%= obj.getView() %>" >
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 60%; padding: 5px; float: left"
					class="tbnavigation">Báo cáo quản trị &gt; Kế toán - Tài chính &gt; Nhật ký tài khoản</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng Bạn <%=userTen%> &nbsp;</div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Thông báo</legend>
					<textarea id="errors" name="errors" rows="1" style="width: 100%"><%=obj.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset><legend class="legendtitle">Nhật ký tài khoản</legend></fieldset>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"	class="plainlabel">

							<TABLE width="100%" cellpadding="4" cellspacing="0">
								
          						<% if(obj.getView().equals("TT")){ %>							                          
                    <TR>
                        <TD class="plainlabel" width="15%">Chọn công ty </TD>
                        <TD class="plainlabel" >
                            <SELECT NAME = "ctyIds"  style = "width:300px;size:5" onChange = "Change();" id = "ctyIds">
                            	<OPTION VALUE = "">&nbsp;</OPTION>
                         
					<%
					if(ctyRs != null){ 
							while(ctyRs.next()){
								int dem = 0;
									if(ctyIds != null){
								for ( int i = 0 ; i<ctyIds.length; i ++){
									if(ctyIds[i]!=null){
								if(ctyIds[i].trim().equals(ctyRs.getString("PK_SEQ"))){
									dem ++;
							%>
								
								<OPTION VALUE = "<%= ctyRs.getString("PK_SEQ")%>" SELECTED ><%= ctyRs.getString("TEN")%></OPTION>
					<%			}}}}
								if (dem <1){ %>
					
								<OPTION VALUE = "<%= ctyRs.getString("PK_SEQ")%>" ><%= ctyRs.getString("TEN")%></OPTION>
								
					<%			}

							} 
						}%>
                            </SELECT>
                        </TD>
                    </TR>
			<%} %>	
								
								<TR>
			                        <TD class="plainlabel" width="100px">Từ ngày</TD>
			                        <TD class="plainlabel" width="250px" >
			                            <input type="text" class="days" name="tungay" id ="tungay" value="<%= obj.getTungay() %>" maxlength="10"  />
			                        </TD>
			                    
			                        <TD class="plainlabel" width="100px">Đến ngày</TD>
			                        <TD class="plainlabel" colspan="4">
			                            <input type="text" class="days" name="denngay" id="denngay" value="<%= obj.getDenngay() %>" maxlength="10"  />
			                        </TD>
                    			</TR>
                    
								
								<TR>	
									<TD class="plainlabel">Chọn tài khoản</TD>
									<TD class="plainlabel" colspan="3">
										<SELECT name = "tkId" style="width: 300px" onChange = "submitform();">
											<option value = "" ></option>
								<%	if(tk != null){ 
										while(tk.next()){ 
											if(tk.getString("TKID").equals(obj.getTkId())){ %>
												<option value = "<%= tk.getString("TKID")%>" selected><%= tk.getString("TAIKHOAN")%></option>
								<%			}else{ %>
												<option value = "<%= tk.getString("TKID")%>" ><%= tk.getString("TAIKHOAN")%></option>
								<%			} %>
								<%			}
									}
								
								%>
										</SELECT></TD>
								</TR>
								
<!-- 								Nhóm tài khoản -->
								<TR>	
									<TD class="plainlabel">Chọn nhóm tài khoản</TD>
									<TD class="plainlabel" colspan="3">
										<SELECT name = "nhomTaiKhoanId" style="width: 300px" onChange = "submitform();">
											<option value = "" ></option>
											<% if (nhomTaiKhoanList != null)
											{
												System.out.println("nhomTaiKhoanList: " + nhomTaiKhoanList.size());
												for (Erp_Item item : nhomTaiKhoanList)
												{
													if (item.getValue().equals(obj.getNhomTaiKhoanId()))
													{
													%>
														<option value="<%=item.getValue()%>" selected="selected"><%=item.getName()%></option>
													<% } else { %>
														<option value="<%=item.getValue()%>"><%=item.getName()%></option>
											<% } }} System.out.println("nhomTaiKhoanList: nullsss");%>
										</SELECT>
									</TD>
								</TR>

<!-- 								Loại nghiệp vụ  -->
								<TR>	
									<TD class="plainlabel">Loại nghiệp vụ</TD>
									<TD class="plainlabel" colspan="3">
										<SELECT name = "loaiNghiepVu" style="width: 300px" onChange = "submitform();">
											<option value = "" ></option>
											<% if (loaiNghiepVuList != null)
											{
												for (Erp_Item item : loaiNghiepVuList)
												{
													if (item.getValue().equals(obj.getLoaiNghiepVu()))
													{
													%>
														<option value="<%=item.getValue()%>" selected="selected"><%=item.getName()%></option>
													<% } else { %>
														<option value="<%=item.getValue()%>"><%=item.getName()%></option>
											<% } }} %>
										</SELECT>
									</TD>
								</TR>

								<tr>
		                        	<td  class="plainlabel" colspan="4">
		                            	<a class="button" href="javascript:submitform()">
		                            	       <img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>	&nbsp;     &nbsp; &nbsp;
		                            	       
		                            	 <a class="button2" href="javascript:clearform()">
		                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;    &nbsp; &nbsp;
		                                
		                                <a class="button3" href="javascript:xuatExcel()"> 
		                                <img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a>
		                                                  
		                        	</td>
		                        	
                    			</tr> 
                    
								<TR>
									
								</TR>
									
							</TABLE>
						</div>
						
						
						<div style="width: 100%; float: none" align="left">
							<TABLE width="100%" cellpadding="4" cellspacing="1">
								
                                <TR class="tbheader">
                                    <TH width="8%" align="center">Ngày</TH>
                                    
                      <% if(obj.getView().equals("TT")){ %>	
                                    <TH width="12%" align="center">Công ty</TH>
                      <% } %>
                                    <TH width="12%" align="center">Số chứng từ</TH>
                                    <TH width="15%" align="center">Loại chứng từ</TH>
                                    <TH width="15%" align="center">Diễn giải chứng từ</TH>
                                    <TH width="10%" align="center">Tài khoản</TH>
                                    <TH width="10%" align="center">TK đối ứng</TH>
                                    <TH width="8%" align="center">Số tiền Nợ (VNĐ)</TH>
                                    <TH width="8%" align="center">Số tiền Có (VNĐ)</TH>
                                    <TH width="11%" align="center">Đối tượng</TH>
                                    <TH width="10%" align="center">Số hóa đơn</TH>
                                </TR>
                                <TR class= "tblightrow">
                                	<TD align = "center"> </TD>
                                	<TD align = "center"> </TD>
                                	<TD align = "center"> </TD>
                                	<TD align = "center"> </TD>
                                	<TD align = "center"> </TD>
                                	<TD align = "center"> </TD>
                                	<TD align = "center"> </TD>  
                                	<TD align = "right"><input type="text" style = "border-width: 0px; text-align:right;width:120px"  id="sumnoht" name="sumnoht" value="" readonly="readonly"> </TD>
                                	<TD align = "right"><input type="text"  style = "border-width: 0px; text-align:right;width:120px"  id="sumcoht" name="sumcoht" value="" readonly="readonly" > </TD>
                                	<TD align = "center"> </TD>                                	
                                	<TD align = "center"> </TD>
                                </TR>
                     <%	
						double SUMNO=0;
                     	double SUMCO=0;
                     	if(rs != null){
                    	 	NumberFormat formatter = new DecimalFormat("#,###,###"); 
                        	int m = 0;                        	
                            while(rs.next()){
                            	/* double NO = Math.round(rs.getDouble("NO"));
                            	double CO =  Math.round(rs.getDouble("CO")); */
                            	double NO = rs.getDouble("NO");
                            	double CO = rs.getDouble("CO");
                            	
                            	SUMNO = SUMNO + NO;
                            	SUMCO = SUMCO + CO;
	                          if (m % 2 != 0){	%>                     
                                <TR class= "tblightrow" >
                     <%		  } else {	%>
                                <TR class= "tbdarkrow" >
                     <%		  }%>
                      		 	   		
                          	 		<TD align = "center"><%= rs.getString("NGAYCHUNGTU") %></TD>
                  	 
                  	 <% if(obj.getView().equals("TT")){ %>
                          	 		<TD align = "left"><%= rs.getString("CONGTY") %></TD>
                     <% } %>     	 		
                      
                              		<TD align = "center">
                              		<% if(!rs.getString("SOCHUNGTU").equals("0")){ %>
                              			<%= rs.getString("SOCHUNGTU")   %>
                              		<%}else{ %>
                              			&nbsp;
                              		<%} %>
                              		</TD>
                              		<TD align = "left"><%= rs.getString("NOIDUNG")   %></TD>
                              		<TD align = "left"><%= rs.getString("DIENGIAI")   %></TD>
                              		<TD align = "center"><%= rs.getString("TAIKHOAN")   %></TD>
                              		<TD align = "center"><%= rs.getString("DOIUNG")   %></TD>
                              		<TD align = "right"><%= formatter.format(NO)   %></TD>
                              		<TD align = "right"><%= formatter.format(CO)   %></TD>
                              		<TD align = "left"><%= rs.getString("DOITUONG") == null? "" : rs.getString("DOITUONG") %></TD>
                              		<TD align = "left"><%= rs.getString("SOHOADON")%></TD>
                              		
                    <%		m++;
                    		} 
                     }
                    %>
               		<input type="hidden" name="sumno" id="sumno" value="<%=SUMNO  %>" >
               		<input type="hidden" name="sumco" id="sumco" value="<%=SUMCO  %>" >
								</TR>
							</TABLE>
							<%-- <TABLE width="100%" cellpadding="4" cellspacing="1">
								<%     	NumberFormat formatter = new DecimalFormat("#,###,###");  %>
								<TR class= "tbheader" >
                        			<TD width="10%" align="center">Tổng</TD>  
                        			<TD width="10%" align="center"></TD> 
                        			<TD width="20%" align="center"></TD> 
                        			<TD width="10%" align="center"></TD> 
                        			<TD width="10%" align="center"></TD> 
                        			<TD width="10%" align="right" ><%= formatter.format(Double.parseDouble(obj.getTongNO()))%></TD> 
                        			<TD width="10%" align="right"><%= formatter.format(Double.parseDouble(obj.getTongCO())) %></TD> 
                        			<TD width="20%" align="center"></TD> 
                        		</TR>
							</TABLE> --%>
						</div>
					</div>
					</div>
			</div>
<script type="text/javascript">
	  TinhTongTien();
</script>
	</form>
</body>
</html>
<%
	if(rs != null) rs.close();
	if(tk != null) tk.close();	
	if(ctyRs != null) ctyRs.close();
	obj.DBClose();
%>