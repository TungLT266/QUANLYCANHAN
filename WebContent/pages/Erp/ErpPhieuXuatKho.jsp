 
<%@page import="geso.traphaco.center.util.IDinhKhoanKeToan"%>
<%@page import="geso.traphaco.erp.beans.phieuxuatkhoTH.IErpPhieuxuatkhoList"%>
<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="geso.traphaco.center.util.IThongTinHienThi"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
 
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<% IErpPhieuxuatkhoList  obj = (IErpPhieuxuatkhoList)session.getAttribute("obj"); %>
<% ResultSet pxkList = (ResultSet)obj.getPxkList(); %>
<% ResultSet ndList = (ResultSet)obj.getNoidungxuatRs(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen"); 
List<IThongTinHienThi> htList = (List<IThongTinHienThi>)obj.getHienthiList();
int[] quyen = new  int[5];
Utility util=new Utility();
quyen = util.Getquyen("ErpPhieuxuatkhoTHSvl","",userId);
NumberFormat formatter = new DecimalFormat("#,###,###.##"); 

NumberFormat formatterXK = new DecimalFormat("#,###,###"); 

ResultSet khoxuatRs= obj.get_khoxuatRs();

ResultSet RsDTH_daduyet=obj.getrsDonhang_daduyet();


%>
<% obj.setNextSplittings(); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>TraphacoERP - Project</title>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
    
    <script language="javascript" src="../scripts/datepicker.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
  	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
   
	<script type="text/javascript" src="../scripts/jquery.min.js"></script>
	<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
	<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
	<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>

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
   
  	<script type="text/javascript" src="..scripts/jquery-1.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
    <script type="text/javascript">
        $(document).ready(function(){
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button2").hover(function(){
                $(".button2 img")getDDH
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button3").hover(function(){
                $(".button3 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
    </script>
    <SCRIPT language="javascript" type="text/javascript">
	 function submitform()
	 {   
	    document.forms["erpPxkForm"].submit();
	 }
	 function newform()
	 {   
		document.forms["erpPxkForm"].action.value = "Tao moi";
	    document.forms["erpPxkForm"].submit();
	 }
	 function clearform()
	 {   
		document.forms["erpPxkForm"].xuatkhoId.value = "";
	    document.forms["erpPxkForm"].sochungtu.value = "";
	    document.forms["erpPxkForm"].tungay.value = "";
	    document.forms["erpPxkForm"].denngay.value = "";
	    document.forms["erpPxkForm"].trangthai.value = "";
	    document.forms["erpPxkForm"].ndId.value = ""; 
	    document.forms["erpPxkForm"].sohoadon.value = "";
	    document.forms["erpPxkForm"].submit();
	 }
	 function thongbao()
	 {
		 var tt = document.forms["erpPxkForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpPxkForm"].msg.value);
	 }
	 
	 function processing(id,chuoi)
	 {
		 document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif' width = '20' height = '20' title='cho thuc hien..' border='0' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>"; 		  
		 document.getElementById(id).href=chuoi;
	 }
	</SCRIPT>
	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { $("select").select2(); });
     
 	</script>	
</head>
<body>
<form name="erpPxkForm" method="post" action="../../ErpPhieuxuatkhoTHSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >

<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	Quản lý mua hàng > Nghiệp vụ khác > Phiếu xuất kho
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng Bạn <%= userTen %> &nbsp;
        </div>
    </div>
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset style="margin-top:5px" >
            	<legend class="legendtitle"> Tiêu chí tìm kiếm</legend>
                	<TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px " >								                          
                    	<TR>
                        	<TD class="plainlabel" width="15%">Từ ngày </TD>
                        	<TD class="plainlabel" width="25%">
                            	<input type="text" class="days" id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" onchange="submitform()" />
                        	</TD>
                    	
	                        <TD class="plainlabel" width="15%">Đến ngày </TD>
	                        <TD class="plainlabel">
	                            <input type="text" class="days" 
	                                   id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" onchange="submitform()" />
	                        </TD>
	                    </TR>
	                    
                       <TR>
	                        <TD class="plainlabel" >Số xuất kho </TD>
	                        <TD class="plainlabel">
	                            <input type="text"  
	                                   id="xuatkhoId" name="xuatkhoId" value="<%= obj.getXuatKhoId() %>" onchange="submitform()" />
	                        </TD>
                      
                        <TD class="plainlabel" >Số Đơn hàng/Trả hàng </TD>
                        <TD class="plainlabel">
                            <input type="text"  
                                   id="sochungtu" name="sochungtu" value="<%= obj.getSoChungTu()%>"  onchange="submitform()" />
                        </TD>
                     </TR>
                                      
                     <TR>
                        <TD class="plainlabel" valign="middle">Trạng thái </TD>
                        <TD class="plainlabel" valign="middle">
                        
                           <select name="trangthai" onChange="submitform();" style ="width:200px">
                           		<option value=""> </option>
                           		 <% 
						  		 String[] mangid=new String[]{"0","1","2","3","4"};
             		  						   String[] mangten=new String[]{"Chưa chốt","Đã chốt","Đã xóa","Đã xuất HDTC","Hoàn tất"};
             		  
						  		 for(int k=0;k < mangid.length;k ++ ){
							   
							   		if(obj.getTrangthai().equals(mangid[k])) {
								   %>
								    	<option value="<%=mangid[k] %>" selected="selected"><%=mangten[k] %> </option>
								   <%
							  		 }else{
								   %>
							    	<option value="<%=mangid[k] %>" ><%=mangten[k] %> </option>
							  		 <%  
							   }
						   }
							 %> 
                           </select>
                        </TD>     
                        
                        <TD class="plainlabel" valign="middle">Kho xuất </TD>
                        <td class="plainlabel">
                        	<select name="khoxuatId" onchange="submitform();" style ="width:200px">
                        		<option value=""></option>
                        		<%if(khoxuatRs!=null){
                        			try{
                        				while(khoxuatRs.next()){ 
                        				
                        					if(khoxuatRs.getString("pk_seq").equals(obj.get_khoxuatId())){%>
                        					<option value="<%= khoxuatRs.getString("pk_seq") %>" selected="selected"><%= khoxuatRs.getString("ten") %></option>	
                        			<%		}else{ %>
                        					<option value="<%= khoxuatRs.getString("pk_seq") %>"><%= khoxuatRs.getString("ten") %></option>
                        			<% 	}}}catch(Exception e){e.printStackTrace();}}%>
                        	</select>
                        </td>         
                    </TR>   
                    
                    <TR>
                    <TD class="plainlabel" valign="middle">Nội dung xuất </TD>
                        <td class="plainlabel" >
                        	<select name="ndId" onchange="submitform();" style ="width:200px">
                        		<option value="">Tất cả</option>
                        		<%if(ndList!=null){
                        			try{
                        				while(ndList.next()){ 
                        				
                        					if(ndList.getString("pk_seq").equals(obj.getnoidungxuatId())){%>
                        					<option value="<%= ndList.getString("pk_seq") %>" selected="selected"><%= ndList.getString("TEN") %></option>	
                        			<%		}else{ %>
                        					<option value="<%= ndList.getString("pk_seq") %>"><%= ndList.getString("TEN") %></option>
                        			<% 	}}}catch(Exception e){e.printStackTrace();}}%>
                        	</select>
                        </td>  
                        
                        <TD class="plainlabel" >Số hóa đơn </TD>
                        <TD class="plainlabel">
                            <input type="text"  
                                   id="sohoadon" name="sohoadon" value="<%= obj.getSohoadon()%>"  onchange="submitform()" />
                        </TD>
                    </TR> 
                    <tr>
                        <td colspan="4" class="plainlabel">
                            <a class="button" href="javascript:submitform()">
                                <img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="button2" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
                        </td>
                    </tr>        					
                </TABLE>                      
        </fieldset>                      
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle"> Phiếu xuất kho </span>&nbsp;&nbsp;
            			<%if(quyen[0]!=0){ %>
                		<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
                           <%} %>
                </legend>
                
                	<TABLE width="100%" border="0" cellspacing="1"
													cellpadding="4">
													<TR class="tbheader">
														<TH >Ngày đơn hàng</TH>
														<TH >Ngày kế toán duyệt</TH>
														<TH> Số hóa đơn</TH>
														<TH> Số SO</TH>
														<TH> Khách hàng</TH>
														<TH>T.tiền Đ.hàng</TH>
														<TH>Người tạo</TH>
														<TH>Người duyệt</TH>
														<TH>Trạng thái</TH>
														<TH align="center">&nbsp;Tác vụ</TH>
													</TR>

													<% 
                          
                            int m = 0;
                            String lightrow = "tblightrow";
                            String darkrow = "tbdarkrow";
                        	if(RsDTH_daduyet != null){
								try{		
									String chuoitrs="";
                                    while (RsDTH_daduyet.next()){
                                        	
                                    	                                      	  
                                      			 		if (m % 2 != 0) {%>
															<TR class=<%=lightrow%>  onMouseover="ddrivetip(  '<%=chuoitrs %>' ,700)"; onMouseout="hideddrivetip();">
														<%} else {%>
													
															<TR class=<%= darkrow%>  onMouseover="ddrivetip( '<%=chuoitrs %>' ,700)"; onMouseout="hideddrivetip();">
														<%}%>
														<TD align="left"><div align="center"><%= RsDTH_daduyet.getString("ngayDAT")%></div>
														</TD>
														<TD align="left"><div align="center"><%= RsDTH_daduyet.getString("NGAYKTDUYET")%></div>
														</TD>
														<TD align="left"><div align="center"><%= RsDTH_daduyet.getString("sohoadon1")%></div>
														</TD>
														<TD>
														<%
                                                  		 if (RsDTH_daduyet.getString("trangthai").equals("7")){ %>
															<span style="color: red;">
																<%=   RsDTH_daduyet.getString("SOHOADON") %>
															</span>
														<%} else{ %>
															<span >
																<%=  RsDTH_daduyet.getString("SOHOADON") %>
															</span>
														<%} %>
														</TD>
														<TD><div align="center"><%= RsDTH_daduyet.getString("TENKH") %></div>
														</TD>
														 
														<TD><% String thd="0";
														   if(RsDTH_daduyet.getString("sotienavat").length()!=0)
															{
																thd=RsDTH_daduyet.getString("sotienavat");

															}
														
														%><div align="right"><%=formatter.format(Double.parseDouble(thd)) %></div>
														</TD>
														<TD><div align="center"><%= RsDTH_daduyet.getString("nguoitao") %></div>
														</TD>
														<TD><div align="center"><%= RsDTH_daduyet.getString("nguoiduyet") %></div>
														</TD>
														<%
														System.out.println("duyet day a;"+RsDTH_daduyet.getString("trangthai"));
                                                  		 if (RsDTH_daduyet.getString("trangthai").equals("1")){ %>
														<TD><div align="center"> Đã duyệt </div>
														</TD>
														<%}else  if (RsDTH_daduyet.getString("trangthai").equals("2")){ %>
															<TD><div align="center"> Đã xuất kho </div>
															
														</TD>
														
														<%} else  if (RsDTH_daduyet.getString("trangthai").equals("3")){ %>
														<TD><div align="center"> Đã xuất hóa đơn tài chính </div>
														</TD>
														<%}
                                                  		 else  {%>
														<TD><div align="center"> Đã xuất kho(chưa hoàn tất) </div>
														</TD>
														 <%} %>
														  
														<TD align="center">
														<%if(quyen[2]!=0){ %>
															<A href="../../ErpPhieuxuatkhoTHUpdateSvl?userId=<%=userId%>&create_nhanhang=<%= RsDTH_daduyet.getString("pk_Seq")%>&ndxid=<%= RsDTH_daduyet.getString("ndxid") %>">
															<IMG src="../images/add.png" alt="Tạo mới" title="Tạo mới" border=0></A> 
														<%} %>
														 </TD>
													</TR>
													<% m++; } 

                                    
                                        }catch(java.sql.SQLException e){
                                        	System.out.println("Error : "+ e.toString());
                                        }
                               		}%>
									</TABLE>
                
                
            	<TABLE width="100%" border="0" cellspacing="0" cellpadding="4">
					<TR class="tbheader">
					 	<TH align="center">Mã chứng từ</TH>
					 	<TH align="center">Số hóa đơn</TH>
	                    <TH align="center">Mã phiếu</TH>
	                    <TH align="center">Ngày lập phiếu</TH>
	                    
	                   
	                    <TH align="center">Đơn bán hàng</TH>
	                    <TH align="center">Nội dung xuất</TH>
	                    <TH align="center">Trạng thái</TH>
	                    <TH align="center">Ngày tạo</TH>
	                    <TH align="center"> Người tạo </TH>
	                    <TH align="center"> Ngày sửa </TH>
	                    <TH align="center"> Người sửa </TH>
	                    <TH align="center" >Tác vụ</TH>
	                </TR>
	               <%
                 			m = 0;
                 			String tmp="" ;
                 			for(int i =0; i < htList.size(); i ++)
							{ 	
                 				IThongTinHienThi htt = htList.get(i);
                 				if(htt.getNoidungxuat().equals("100007")){ 
                 					//tmp = obj.getDDH(pxkList.getString("SOCHUNGTU")== null ? "":pxkList.getString("SOCHUNGTU"));
                 				}
                 				if(tmp.length() >= 0){
                 					if((m % 2 ) == 0) {%>
		                         	<TR class='tbdarkrow'>
		                        <%	}else{ %>
		                          	<TR class='tblightrow'>
		                        <%	} %>
		                        <TD align="center" <% if( htt.getTrangthai().equals("2")){ %> style="color: red" <%} %>><%= htt.getmachungtu() %></TD>
					                    
					              <TD align="center" <% if( htt.getTrangthai().equals("2")){ %> style="color: red" <%} %>><%= htt.getSohoadon() %></TD>
					                    <TD align="center">
		                    	<%if(htt.getTrangthai().equals("2")){ %>
		                    	<span style="color: red;"><%= htt.getSoxuatkho() %></span>
		                    	<%}else{ %>
		                    	<span><%= htt.getSoxuatkho() %></span>
			                    <%} %>
		        			            </TD>
		                    			<TD align="center" <% if( htt.getTrangthai().equals("2")){ %> style="color: red" <%} %>><%= htt.getNgayxuat() %></TD>
		                    			<%-- <%if( htt.getNoidungxuat().equals("100007")){ %>
		                    			   <TD align="center"><%=htt.getSochungtu()%></TD>	
		                    			<%}else{ %> --%>
		                    				<TD align="center" <% if( htt.getTrangthai().equals("2")){ %> style="color: red" <%} %>><%= htt.getSohoadon() %></TD>
		                    			 
		                    			<%-- <%} %> --%>
		                    			<TD align="left" <% if( htt.getTrangthai().equals("2")){ %> style="color: red" <%} %>><%= htt.getndxTen() %></TD>	
		                    			<TD align="center" <% if( htt.getTrangthai().equals("2")){ %> style="color: red" <%} %>>
		                    	<%
		                    			String trangthai = "";
		                    			String tt = htt.getTrangthai();
		                    			if(tt.equals("0"))
		                    				trangthai = "Chưa chốt";
		                    			else
		                    			{
		                    				if(tt.equals("1"))
			                    				trangthai = "Đã chốt";
			                    			else
			                    			{
		    	                				if(tt.equals("2"))
		        	            					trangthai = "Đã xóa";
		            	        				else if (tt.equals("3"))
		                	    					trangthai = "Đã xuất HDTC";
		            	        				else if(tt.equals("4"))
		            	        					trangthai = "Hoàn tất";
		                    				}
		                    			}
		                    	%>
		                    	<%= 	trangthai %>
		                    			</TD>									                                    
		                    			<TD align="center" <% if( htt.getTrangthai().equals("2")){ %> style="color: red" <%} %>><%= htt.getNgaytao() %></TD>
		                    			<TD align="left" <% if( htt.getTrangthai().equals("2")){ %> style="color: red" <%} %>><%= htt.getNguoitao() %></TD>
		                    			<TD align="center" <% if( htt.getTrangthai().equals("2")){ %> style="color: red" <%} %>><%= htt.getNgaysua() %></TD>	
		                    			<TD align="left" <% if( htt.getTrangthai().equals("2")){ %> style="color: red" <%} %>><%= htt.getNguoisua() %></TD>				
		                    			<TD align="center" <% if( htt.getTrangthai().equals("2")){ %> style="color: red" <%} %>> 
		                    <% 	if(tt.equals("0")){ %>
                                	<A id='capnhat<%=htt.getId()%>' href = "">
                                	<%-- href = "../../ErpPhieuxuatkhoTHUpdateSvl?userId=<%=userId%>&update=<%= pxkList.getString("pxkId") %>"> --%>
                                		<IMG src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" border="0" onclick="processing('<%="capnhat"+htt.getId()%>' , '../../ErpPhieuxuatkhoTHUpdateSvl?userId=<%=userId%>&update=<%= htt.getId() %>')">
                                	</A>&nbsp;
                             <%	if(quyen[3]!=0){ %>
                                	<A id='chotphieu<%= htt.getId() %>' href = "">  <%-- "../../ErpPhieuxuatkhoTHSvl?userId=<%=userId%>&chot=<%= pxkList.getString("pxkId") %>"> --%>
                                		<img src="../images/Chot.png" alt="Chốt phiếu" title="Chốt phiếu" width="20" height="20" 
                                			border=0 onclick="if(!confirm('Bạn có chắc chốt phiếu xuất kho này?')) {return false;} else{ processing('<%="chotphieu"+ htt.getId()%>' , '../../ErpPhieuxuatkhoTHSvl?userId=<%=userId%>&chot=<%= htt.getId() %>');}"></A>&nbsp;
                                	
		                             <%	} %>
		                                <%if(quyen[1]!=0){ %>
		                                <A id='xoaphieu<%= htt.getId() %>' href = "">  <%-- "../../ErpPhieuxuatkhoTHSvl?userId=<%=userId%>&delete=<%= pxkList.getString("pxkId") %>"> --%>
		                                	<img src="../images/Delete20.png" alt="Xóa phiếu" title="Xóa phiếu" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn xóa phiếu xuất kho này?')) {return false;} else{ processing('<%="xoaphieu"+ htt.getId()%>' , '../../ErpPhieuxuatkhoTHSvl?userId=<%=userId%>&delete=<%= htt.getId() %>');}"></A>
		                                <%} %>								
				                    			                    
		                    <A href="" id="ktlist<%=m %>" rel="subcontentKT<%=m %>">&nbsp; <img alt="Định khoản kế toán" src="../images/vitriluu.png"> </A> &nbsp;
                       	 
                       	 <DIV id="subcontentKT<%=m %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;  background-color: white; width: 750px; max-height:250px; overflow-y:scroll; padding: 4px;">
               				<table width="100%" align="center">
					                  <tr>
				                           	<th width="10%">Nợ/Có</th>
				                            <th width="12%">Số hiệu TK</th>
				                            <th width="15%">Số tiền</th>
				                            <th width="30%">Đối tượng</th>
				                            <th width="15%">Trung tâm CP</th>	
				                            <th width="15%">Trung tâm DT</th>										                       
						              </tr>
                   
			                            <% 		List<IDinhKhoanKeToan> ktList = htt.getLayDinhkhoanKT();							                       	 	
					                        		for(int sd = 0; sd < ktList.size(); sd++)
				                        			{
					                        			IDinhKhoanKeToan kt = ktList.get(sd);
						                        		%>
						                        			<tr>
						                        				<td>
						                        					<input type="text" style="width:100%;text-align:center" readonly="readonly" name="no_co" value="<%= kt.getNO_CO() %>" />
						                        				</td>
									                            <td>											                            	
									                            	<input type="text" style="width: 100%" readonly="readonly" name="sohieutk" value="<%= kt.getSoHieu() %>" />
									                            </td>
									                            <td>
									                            	<%if(kt.getSotien().trim().length()>0){ %>
									                            		<input type="text" style="width: 100%; text-align: right;" readonly="readonly" name="sotien" value="<%= formatterXK.format(Double.parseDouble(kt.getSotien())) %>" />
									                            	<%} else {%>
									                            		<input type="text" style="width: 100%; text-align: right;" readonly="readonly" name="sotien" value="<%= kt.getSotien() %>"  />
									                            	<%} %>
									                            <td>
									                            	<input type="text"  style="width: 100%" name="doituong" value="<%= kt.getDoiTuong() %>" />
									                            </td>
									                            <td>
									                            	<input type="text"  style="width: 100%" name="trungtamcp" value="<%= kt.getTrungtamCP()  %>" />
									                            </td>
									                            <td>
									                            	<input type="text"  style="width: 100%" name="trungtamdt" value="<%= kt.getTrungtamDT()  %>" />
									                            </td>
									                        </tr>
						                        <%  }								        
						                     %>
	
               							</table>
						                     <div align="right">
						                     	<label style="color: red" ></label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						                     	<a href="javascript:dropdowncontent.hidediv('subcontentKT<%=m %>')">Hoàn tất</a>
						                     </div>
           					</DIV>
           				<%} else if (tt.equals("1")||tt.equals("3")||tt.equals("4")) { %>
           				
           				 <A href = "../../ErpPhieuxuatkhoTHUpdateSvl?userId=<%=userId%>&display=<%= htt.getId() %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;
           				 
           				 <A href="" id="ktlist<%=m %>" rel="subcontentKT<%=m %>">&nbsp; <img alt="Định khoản kế toán" src="../images/vitriluu.png"> </A> &nbsp;                       	 
                       	 <DIV id="subcontentKT<%=m %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;  background-color: white; width: 750px; max-height:250px; overflow-y:scroll; padding: 4px;">
               				<table width="90%" align="center">
					                  <tr>
				                           	<th width="10%">Nợ/Có</th>
				                            <th width="12%">Số hiệu TK</th>
				                            <th width="15%">Số tiền</th>
				                            <th width="30%">Đối tượng</th>
				                            <th width="15%">Trung tâm CP</th>	
				                            <th width="15%">Trung tâm DT</th>										                       
						              </tr>
                   
			                            <% 		List<IDinhKhoanKeToan> ktList = htt.getLayDinhkhoanKT();							                       	 	
					                        		for(int sd = 0; sd < ktList.size(); sd++)
				                        			{
					                        			IDinhKhoanKeToan kt = ktList.get(sd);
						                        		%>
						                        			<tr>
						                        				<td>
						                        					<input type="text" style="width: 100%;text-align:center" readonly="readonly" name="no_co" value="<%= kt.getNO_CO() %>" />
						                        				</td>
									                            <td>											                            	
									                            	<input type="text" style="width: 100%" readonly="readonly" name="sohieutk" value="<%= kt.getSoHieu() %>" />
									                            </td>
									                            <td>
									                            	<%if(kt.getSotien().trim().length()>0){ %>
									                            		<input type="text" style="width: 100%; text-align: right;" readonly="readonly" name="sotien" value="<%= formatterXK.format(Double.parseDouble(kt.getSotien())) %>"  />
									                            	<%} else {%>
									                            		<input type="text" style="width: 100%; text-align: right;" readonly="readonly" name="sotien" value="<%= kt.getSotien() %>" style="text-align: left" />
									                            	<%} %>
									                            <td>
									                            	<input type="text"  style="width: 100%" name="doituong" value="<%= kt.getDoiTuong() %>" />
									                            </td>
									                            <td>
									                            	<input type="text"  style="width: 100%" name="trungtamcp" value="<%= kt.getTrungtamCP()  %>" />
									                            </td>
									                            <td>
									                            	<input type="text"  style="width: 100%" name="trungtamdt" value="<%= kt.getTrungtamDT()  %>" />
									                            </td>
									                        </tr>
						                        <%  }								        
						                     %>
	
               							</table>
						                     <div align="right">
						                     	<label style="color: red" ></label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						                     	<a href="javascript:dropdowncontent.hidediv('subcontentKT<%=m %>')">Hoàn tất</a>
						                     </div>
           					</DIV>
		                  <%  }else{ %>
				                   	<A href = "../../ErpPhieuxuatkhoTHUpdateSvl?userId=<%=userId%>&display=<%= htt.getId() %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;
				                    
				            <%} %>
				                    </TD>
		                		</TR>
		                     	<% 
		                     		m++; 
		                 			}
		                 		}  %>
							<tr class="tbfooter" > 
						 	<TD align="center" valign="middle" colspan="13" class="tbfooter">
							 	<%if(obj.getNxtApprSplitting() >1) {%>
									<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('erpPxkForm', 1, 'view')"> &nbsp;
								<%}else {%>
									<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
									<%} %>
								<% if(obj.getNxtApprSplitting() > 1){ %>
									<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="Prev('erpPxkForm', 'prev')"> &nbsp;
								<%}else{ %>
									<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
								<%} %>
								
								<%
									int[] listPage = obj.getNextSplittings();
									for(int i = 0; i < listPage.length; i++){
								%>							
								<% 	
									if(listPage[i] == obj.getNxtApprSplitting()){ %>							
									<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
									<%}else{ %>
									<a href="javascript:View('erpPxkForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
								<%} %>
									<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
								<%} %>
							
								<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
									&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="Next('erpPxkForm', 'next')"> &nbsp;
								<%}else{ %>
									&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
								<%} %>
								<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
							   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
						   		<%}else{ %>
						   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View('erpPxkForm', -1, 'view')"> &nbsp;
						   		<%} %>
						</TD>
					 </tr> 
				</TABLE>
            </fieldset>
        </div>
    </div>  
</div>
<script type="text/javascript">	
	
	 <%for(int k=0; k < m; k++) {%>
	   
		dropdowncontent.init("ktlist<%=k%>", "left-bottom", 250, "click");
	   
	 <%}%>
	  
</script>

</form>
<script type="text/javascript" src="../scripts/loadingv2.js"></script>
</body>
</html>

<% 
	if(pxkList != null) pxkList.close();
	obj.DBclose();
%>