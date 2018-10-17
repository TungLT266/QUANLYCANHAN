<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "geso.traphaco.erp.beans.dubaokinhdoanh.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<% 	IDubaokinhdoanh_Giay dbkdBean = (IDubaokinhdoanh_Giay)session.getAttribute("dbkdBean"); %>
<% 	ResultSet spRs = (ResultSet)dbkdBean.getSanPhamRs(); %>
<% 	ResultSet nhRs = (ResultSet)dbkdBean.getNhanhangList(); %>
<% 	ResultSet clRs = (ResultSet)dbkdBean.getChungloaiList(); %>
<% 	String userId = (String) session.getAttribute("userId");  %>
<% 	String userTen = (String) session.getAttribute("userTen");  %>
<% 
	ResultSet dubaoRs_1 = dbkdBean.getDubaoRS_1() ;
	ResultSet dubaoRs_2 = dbkdBean.getDubaoRS_2() ;
	ResultSet dubaoRs_3 = dbkdBean.getDubaoRS_3() ;
	ResultSet dubaoRs_4 = dbkdBean.getDubaoRS_4() ;
%>
<% NumberFormat formatter = new DecimalFormat("#,###,###"); %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>TraphacoHYERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
<script type="text/javascript" src="../scripts/maskedinput.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<style type="text/css">

#diengiai {

    width: 300px;
}
#tab tr:HOVER{
cursor:pointer;
background: #E2F0D9;
}
#tabc tr input:HOVER{
cursor:pointer;
background: #E2F0D9;
}
</style>


<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<LINK rel="stylesheet" href="../css/cdtab.css" type="text/css">

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2();  });
     
</script>


<script type="text/javascript" language="javascript">
	$(document).ready(function() {
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});
	



	function Upload()
	{
		var ngaydubao = document.getElementById("danhsachsp");
		var capnhapbut = document.getElementById("capnhapbut");
		ngaydubao.style.display='none';
		capnhapbut.style.display='none';
	}

	var flag=false;
	function isNumberKey(evt)
	{
	   var charCode = (evt.which) ? evt.which : event.keyCode;
	   if (charCode > 31 && (charCode < 48 || charCode > 57) && charCode!='46')
	      return false;
	
	   return true;
	}
	function isNumberKey2(evt)
	{
	   var charCode = (evt.which) ? evt.which : event.keyCode;
	   if (charCode > 31 && (charCode < 48 || charCode > 57) )
	      return false;
	
	   return true;
	}
	
	 function saveform()
	 { 
		 document.forms['ckForm'].action.value='save';
	     document.forms['ckForm'].submit();
	 }

	 function submitform()
	 { 
		 document.forms['ckForm'].action.value='';
	     document.forms['ckForm'].submit();
	 }
	 
	 function sellectAll()
	 {
		 var chkAll = document.getElementById("chkAll");
		 var spIds = document.getElementsByName("spIds");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = false;
			 }
		 }
	 }
	 
	 function DinhDangTien(num) 
	 {
	    num = num.toString().replace(/\$|\,/g,'');
	    if(isNaN(num))
	    num = "0";
	    sign = (num == (num = Math.abs(num)));
	    num = Math.floor(num*100);
	    num = Math.floor(num/100).toString();
	    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	    num = num.substring(0,num.length-(4*i+3))+','+
	    num.substring(num.length-(4*i+3));
	    return (((sign)?'':'-') + num);
	 }
	 function Dinhdang(element)
	 {
	 
	 	element.value=DinhDangTien(element.value);
	 	if(element.value== '' )
	 	{
	 		element.value= '';
	 	}
	 	
	 }

	 function toBottom()
	 {
//		 window.scrollTo(0, document.body.scrollHeight);
		 window.scrollTo(0, document.body.scrollTop );
	 }
	 function readExcel()
	 {
		 document.forms['ckForm'].action.value='readExcel';
		 document.forms['ckForm'].setAttribute('enctype', "multipart/form-data", 0);
	     document.forms['ckForm'].submit();
	 }
	 
	 function exportExcel()
	 {
		 document.forms['ckForm'].action.value='exportExcel';
	     document.forms['ckForm'].submit();
	 }
	 
</script>
<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});
		//$(".tabContents").hide();
        //$(".tabContents:first").show(); 

        $(".titleTab a").click(function () { 

            var activeTab = $(this).attr("href"); 
 			//var activeTab =
            $(".titleTab a").removeClass("active"); 
            $(this).addClass("active");
            $(".tabContents").hide();
            $(activeTab).fadeIn();

        });
        $("select").select2();
        
	});	
</script> 
	
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" onload="toBottom()">
<form name="ckForm" method="post" action="../../DuBao_Giay_UpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="id" value='<%= dbkdBean.getId() %>' >
<input type="hidden" name="action" value='1'>


<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">&nbsp;Quản lý cung ứng > Lập kế hoạch > Dự báo kinh doanh > Cập nhật
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="">
    	<A href= "../../DuBao_GiaySvl?userId=<%= userId %>" >
        	<img src="../images/Back30.png" alt="Quay về"  title="Quay về" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <span id="btnSave">
	        <A href="javascript:saveform()" >
	        	<IMG src="../images/Save30.png" title="Lưu lại" alt="Lưu lại" border ="1px" style="border-style:outset"></A>
        </span>
    </div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  rows="2" readonly="readonly" style ="width:100%%"><%= dbkdBean.getMsg() %></textarea>
		         <% dbkdBean.setMsg(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle">Thông tin dự báo kinh doanh</legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0">

                <TR>
                    <TD width="20%"  class="plainlabel" valign="middle">Diễn giải<FONT class="erroralert">*</FONT></TD>
                    <TD width="40%"  class="plainlabel" >
                    	<input type="text"  id="diengiai" autocomplete="off" name="diengiai" value="<%= dbkdBean.getDiengiai() %>" maxlength="200" width="300px" /></TD>
                    	
					<TD class="plainlabel" >Hiệu lực</TD>
					<TD class="plainlabel" width = "35%">
					<%
						if (dbkdBean.getHieuluc().equals("1")) {
						%> <input type="checkbox" name="hieuluc" value="<%=dbkdBean.getHieuluc()%>" checked="checked"> <%
 						} else {
 						%> <input type="checkbox" name="hieuluc" value="<%=dbkdBean.getHieuluc()%>"> <%
 						}
 						%>
					</TD>
                    	
                </TR>  

                <TR>
                   <TD width="20%"  class="plainlabel" valign="middle">Ngày tạo dự báo<FONT class="erroralert">*</FONT></TD>
                   <TD width="80%"  align="left" class="plainlabel" colspan = 3>
                   	<input type="text"  autocomplete="off" id="ngaydubao" readonly="readonly" name="ngaydubao" value="<%= dbkdBean.getNgaydubao()%>" 
                   		maxlength="10" ></TD>
                </TR>  

           <% if(dbkdBean.getLoai().equals("2")){ %>
               <TR>
                    <TD class="plainlabel" valign="middle" >Chọn tháng</TD>
                    <TD class="plainlabel" valign="middle" colspan = "3">
                            <select id="thang"  name="thang"  style="width: 100px;" onChange="submitform();">
                            	<option value=""></option>
                     <% 
                     	String M = "", Y = "";
                     	String ngaydubao = dbkdBean.getNgaydubao();
	                 	String nam = ngaydubao.substring(0, 4);
	                 	String thang = ngaydubao.substring(5, 7);
	                 	
                     	for(int j = 1; j <= 12; j++){
							if(Integer.parseInt(thang) + j >  12){
								M = "" + (Integer.parseInt(thang) + j - 12 );	
								Y = "" + (Integer.parseInt(nam) + 1);
							}else{
								M = "" + (Integer.parseInt(thang) + j);
								Y = "" + (Integer.parseInt(nam));
							} 
							if (dbkdBean.getThang().equals(M + "." + Y)){ %>
							<option value="<%= M + "." + Y %>" selected><%= M.length() == 1? "T.0" + M + "." + Y:"T." + M + "." + Y %></option>	
							
					<%		}else{ %>
					
							<option value="<%= M + "." + Y %>" ><%=  M.length() == 1? "T.0" + M + "." + Y:"T." + M + "." + Y %></option>	
					<%		}
                     	}
					
					%>
 
                            </select>
                    </TD>                          
                </TR>         
                  						                  						
          <% } %>
                <TR>
                     <TD class="plainlabel" valign="middle">Chủng loại</TD>
                     <TD class="plainlabel" valign="middle" colspan = 3>
                        <select  id="chungloai" name="chungloai" style="width: 250px;" onChange = "submitform();">
                          	<option value=""></option>
                          	<%
	                      		if(clRs  != null)
	                       		{
	                       			while(clRs.next())
	                       			{  
		                       			if( clRs.getString("pk_seq").equals(dbkdBean.getChungloai())){ %>
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
				 <TR>
				 	<TD colspan = 4>
						<div id="tabContaier">
							<div style="width:100%; float:left;background:#ddd">
												          
								<div class="titleTab"><a class="active" href="#tab1">MIỀN BẮC</a></div>
								<div class="titleTab"><a href="#tab2">MIỀN TRUNG</a></div>
								<div class="titleTab"><a href="#tab3">MIỀN NAM</a></div>											          
								<div class="titleTab"><a href="#tab4">CÔNG TY</a></div>					          
												          
							</div>
														
							<DIV class="tabDetails">			
	   	        				<DIV id="tab1" class="tabContents" style="display:block">
	   	        											
									<TABLE  border="0" cellpadding="4"  cellspacing="2" width="100%">
										<TR class="tbheader" >
											<TH width="5%" align = "center" rowspan = 2>STT </TH>
											<TH width="10%" align = "center" rowspan = 2>Mã sản phẩm </TH>															
											<TH width="20%" align = "center" rowspan = 2>Tên sản phẩm</TH>
											<TH width="5%" align = "center" rowspan = 2>ĐVT </TH>
											<TH width="10%" align = "center" rowspan = 2>Giá bán </TH>
											<TH width="20%" align = "center" colspan = 4>Dự báo 4 tháng</TH>
											<TH width="10%" align = "center" colspan = 2>Tổng cộng</TH>
										</TR>
			                        	<TR class="tbheader" >
			                 <%	String ngaydubao = dbkdBean.getNgaydubao();
			                 	String nam = ngaydubao.substring(0, 4);
			                 	String thang = ngaydubao.substring(5, 7);

			                 	String M = "", Y = "";
			                 	for(int j = 1; j <= 4; j++){
									if(Integer.parseInt(thang) + j >  12){
										M = "" + (Integer.parseInt(thang) + j - 12 );	
										Y = "" + (Integer.parseInt(nam) + 1);
									}else{
										M = "" + (Integer.parseInt(thang) + j);
										Y = "" + (Integer.parseInt(nam));
									} %>
															
											<TH align = "center" ><%= "T." + M + "/" + Y%></TH>
			                 <% }   %>		
			       							<TH align = "center" >Số lượng</TH>
			                        		<TH align = "center" >Doanh số</TH>
			                       		</TR>								
			                    <% 
			                    int i = 1;
			                    String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
			                    if(dubaoRs_1 != null){
			                    	while(dubaoRs_1.next()){ 
			                    		if(i % 2 == 0){
			                    	
			                    	%>
			                       		<TR class= <%=lightrow%> >
			                    <%		}else{ %>
			                       		<TR class= <%=darkrow%> >
			                    <%		} %>
			                       			<TD align = "center"><%= i %></TD>
			                       			<TD align = "left"><%= dubaoRs_1.getString("MA") %>   </TD>
			                       			<TD><%= dubaoRs_1.getString("TEN") %>   </TD>
			                       			<TD align = "center"><%= dubaoRs_1.getString("DONVI") %>   </TD>
			                       			<TD align = "right"><%= formatter.format(dubaoRs_1.getDouble("GIA")) %>   </TD>
			                       			<TD align = "right"><%= formatter.format(dubaoRs_1.getDouble("T1")) %>   </TD>
			                       			<TD align = "right"><%= formatter.format(dubaoRs_1.getDouble("T2")) %>   </TD>
			                       			<TD align = "right"><%= formatter.format(dubaoRs_1.getDouble("T3")) %>   </TD>
			                       			
			                       			<TD align = "right"><%= formatter.format(dubaoRs_1.getDouble("T4")) %>   </TD>

			                       			<TD align = "right"><%= formatter.format(dubaoRs_1.getDouble("T1") + dubaoRs_1.getDouble("T2") + dubaoRs_1.getDouble("T3")+ dubaoRs_1.getDouble("T4"))  %></TD>
			                       			<TD align = "right"><%= formatter.format(dubaoRs_1.getDouble("T1") + dubaoRs_1.getDouble("T2") + dubaoRs_1.getDouble("T3")+dubaoRs_1.getDouble("T4"))  %>   </TD>
			                       		</TR>
			                       		
			                    <% 		i++;
			                    	} 
			                    	dubaoRs_1.close();
			                    }
			                    
			                    %>
            						</TABLE>
	                     							
       							</DIV>
	   	        				<DIV id="tab2" class="tabContents" style="display:none">
	   	        											
									<TABLE  border="0" cellpadding="4"  cellspacing="2" width="100%">
										<TR class="tbheader" >
											<TH width="5%" align = "center" rowspan = 2>STT </TH>
											<TH width="10%" align = "center" rowspan = 2>Mã sản phẩm </TH>															
											<TH width="20%" align = "center" rowspan = 2>Tên sản phẩm</TH>
											<TH width="5%" align = "center" rowspan = 2>ĐVT </TH>
											<TH width="10%" align = "center" rowspan = 2>Giá bán </TH>
											<TH width="20%" align = "center" colspan = 4>Dự báo 4 tháng</TH>
											<TH width="10%" align = "center" colspan = 2>Tổng cộng</TH>
										</TR>
			                        	<TR class="tbheader" >
			                 <%	
			                 	ngaydubao = dbkdBean.getNgaydubao();
			                 	nam = ngaydubao.substring(0, 4);
			                 	thang = ngaydubao.substring(5, 7);
			                 	M = "";
			                 	Y = "";
			                 	for(int j = 1; j <= 4; j++){
									if(Integer.parseInt(thang) + j >  12){
										M = "" + (Integer.parseInt(thang) + j - 12 );	
										Y = "" + (Integer.parseInt(nam) + 1);
									}else{
										M = "" + (Integer.parseInt(thang) + j);
										Y = "" + (Integer.parseInt(nam));
									} %>
															
											<TH align = "center" ><%= "T." + M + "/" + Y%></TH>
			                 <%}   %>		
			                        		
			                        		
			                        		<TH align = "center" >Số lượng</TH>
			                        		<TH align = "center" >Doanh số</TH>
			                       		</TR>								
			                    <% 
			                    i = 1;
			                    if(dubaoRs_2 != null){
			                    	while(dubaoRs_2.next()){ 
			                    		if(i % 2 == 0){
			                    	
			                    	%>
			                       		<TR class= <%=lightrow%> >
			                    <%		}else{ %>
			                       		<TR class= <%=darkrow%> >
			                    <%		} %>
			                       			<TD align = "center"><%= i %></TD>
			                       			<TD align = "left"><%= dubaoRs_2.getString("MA") %>   </TD>
			                       			<TD><%= dubaoRs_2.getString("TEN") %>   </TD>
			                       			<TD align = "center"><%= dubaoRs_2.getString("DONVI") %>   </TD>
			                       			<TD align = "right"><%= formatter.format(dubaoRs_2.getDouble("GIA")) %>   </TD>
			                       			<TD align = "right"><%= formatter.format(dubaoRs_2.getDouble("T1")) %>   </TD>
			                       			<TD align = "right"><%= formatter.format(dubaoRs_2.getDouble("T2")) %>   </TD>
			                       			<TD align = "right"><%= formatter.format(dubaoRs_2.getDouble("T3")) %>   </TD>
			                       			
			                       			<TD align = "right"><%= formatter.format(dubaoRs_2.getDouble("T4")) %>   </TD>
			                       			<TD align = "right"><%= formatter.format(dubaoRs_2.getDouble("T1") + dubaoRs_2.getDouble("T2") + dubaoRs_2.getDouble("T3")+ dubaoRs_2.getDouble("T4"))  %></TD>
			                       			<TD align = "right"><%= formatter.format(dubaoRs_2.getDouble("T1") + dubaoRs_2.getDouble("T2") + dubaoRs_2.getDouble("T3")+dubaoRs_2.getDouble("T4"))  %>   </TD>
			                       			
			                       		</TR>
			                       		
			                    <% 		i++;
			                    	} 
			                    	dubaoRs_2.close();
			                    }
			                    
			                    %>
			                        									
            						</TABLE>
	                     							
       							</DIV>  
	   	        				<DIV id="tab3" class="tabContents" style="display:none">
	   	        											
									<TABLE  border="0" cellpadding="4"  cellspacing="2" width="100%">
										<TR class="tbheader" >
											<TH width="5%" align = "center" rowspan = 2>STT </TH>
											<TH width="10%" align = "center" rowspan = 2>Mã sản phẩm </TH>															
											<TH width="20%" align = "center" rowspan = 2>Tên sản phẩm</TH>
											<TH width="5%" align = "center" rowspan = 2>ĐVT </TH>
											<TH width="10%" align = "center" rowspan = 2>Giá bán </TH>
											<TH width="20%" align = "center" colspan = 4>Dự báo 4 tháng</TH>
											<TH width="10%" align = "center" colspan = 2>Tổng cộng</TH>
										</TR>
			                        	<TR class="tbheader" >
			                 <%	
			                 	M = ""; 
			                 	Y = "";
			                 	for(int j = 1; j <= 4; j++){
									if(Integer.parseInt(thang) + j >  12){
										M = "" + (Integer.parseInt(thang) + j - 12 );	
										Y = "" + (Integer.parseInt(nam) + 1);
									}else{
										M = "" + (Integer.parseInt(thang) + j);
										Y = "" + (Integer.parseInt(nam));
									} %>
															
											<TH align = "center" ><%= "T." + M + "/" + Y%></TH>
			                 <%}   %>		
			                        		
			                        		
			                        		<TH align = "center" >Số lượng</TH>
			                        		<TH align = "center" >Doanh số</TH>
			                       		</TR>								
			                    <% 
			                    i = 1;
			                    if(dubaoRs_3 != null){
			                    	while(dubaoRs_3.next()){ 
			                    		if(i % 2 == 0){
			                    	
			                    	%>
			                       		<TR class= <%=lightrow%> >
			                    <%		}else{ %>
			                       		<TR class= <%=darkrow%> >
			                    <%		} %>
			                       			<TD align = "center"><%= i %></TD>
			                       			<TD align = "left"><%= dubaoRs_3.getString("MA") %>   </TD>
			                       			<TD><%= dubaoRs_3.getString("TEN") %>   </TD>
			                       			<TD align = "center"><%= dubaoRs_3.getString("DONVI") %>   </TD>
			                       			<TD align = "right"><%= formatter.format(dubaoRs_3.getDouble("GIA")) %>   </TD>
			                       			<TD align = "right"><%= formatter.format(dubaoRs_3.getDouble("T1")) %>   </TD>
			                       			<TD align = "right"><%= formatter.format(dubaoRs_3.getDouble("T2")) %>   </TD>
			                       			<TD align = "right"><%= formatter.format(dubaoRs_3.getDouble("T3")) %>   </TD>
			                       			
			                       			<TD align = "right"><%= formatter.format(dubaoRs_3.getDouble("T4")) %>   </TD>

			                       			<TD align = "right"><%= formatter.format(dubaoRs_3.getDouble("T1") + dubaoRs_3.getDouble("T2") + dubaoRs_3.getDouble("T3")+ dubaoRs_3.getDouble("T4"))   %></TD>
			                       			<TD align = "right"><%= formatter.format(dubaoRs_3.getDouble("T1") + dubaoRs_3.getDouble("T2") + dubaoRs_3.getDouble("T3")+dubaoRs_3.getDouble("T4"))  %>   </TD>
			                       			
			                       		</TR>
			                       		
			                    <% 		i++;
			                    	} 
			                    	dubaoRs_3.close();
			                    }
			                    
			                    %>
			                        									
            						</TABLE>
	                     							
       							</DIV>  
	   	        				<DIV id="tab4" class="tabContents" style="display:none">
	   	        											
									<TABLE  border="0" cellpadding="4"  cellspacing="2" width="100%">
										<TR class="tbheader" >
											<TH width="5%" align = "center" rowspan = 2>STT </TH>
											<TH width="10%" align = "center" rowspan = 2>Mã sản phẩm </TH>															
											<TH width="20%" align = "center" rowspan = 2>Tên sản phẩm</TH>
											<TH width="5%" align = "center" rowspan = 2>ĐVT </TH>
											<TH width="10%" align = "center" rowspan = 2>Giá bán </TH>
											<TH width="20%" align = "center" colspan = 4>Dự báo 4 tháng</TH>
											<TH width="10%" align = "center" colspan = 2>Tổng cộng</TH>
										</TR>
			                        	<TR class="tbheader" >
			                 <%	
			                 	M = ""; 
			                 	Y = "";
			                 	for(int j = 1; j <= 4; j++){
									if(Integer.parseInt(thang) + j >  12){
										M = "" + (Integer.parseInt(thang) + j - 12 );	
										Y = "" + (Integer.parseInt(nam) + 1);
									}else{
										M = "" + (Integer.parseInt(thang) + j);
										Y = "" + (Integer.parseInt(nam));
									} %>
															
											<TH align = "center" ><%= "T." + M + "/" + Y%></TH>
			                 <%}   %>		
			                        		
			                        		
			                        		<TH align = "center" >Số lượng</TH>
			                        		<TH align = "center" >Doanh số</TH>
			                       		</TR>								
			                    <% 
			                    i = 1;
			                    if(dubaoRs_4 != null){
			                    	while(dubaoRs_4.next()){ 
			                    		if(i % 2 == 0){
			                    	
			                    	%>
			                       		<TR class= <%=lightrow%> >
			                    <%		}else{ %>
			                       		<TR class= <%=darkrow%> >
			                    <%		} %>
			                       			<TD align = "center"><%= i %></TD>
			                       			<TD align = "left"><%= dubaoRs_4.getString("MA") %>   </TD>
			                       			<TD><%= dubaoRs_4.getString("TEN") %>   </TD>
			                       			<TD align = "center"><%= dubaoRs_4.getString("DONVI") %>   </TD>
			                       			<TD align = "right"><%= formatter.format(dubaoRs_4.getDouble("GIA")) %>   </TD>
			                       			<TD align = "right"><%= formatter.format(dubaoRs_4.getDouble("T1")) %>   </TD>
			                       			<TD align = "right"><%= formatter.format(dubaoRs_4.getDouble("T2")) %>   </TD>
			                       			<TD align = "right"><%= formatter.format(dubaoRs_4.getDouble("T3")) %>   </TD>
			                       			
			                       			<TD align = "right"><%= formatter.format(dubaoRs_4.getDouble("T4")) %>   </TD>

			                       			<TD align = "right"><%= formatter.format(dubaoRs_4.getDouble("T1") + dubaoRs_4.getDouble("T2") + dubaoRs_4.getDouble("T3")+ dubaoRs_4.getDouble("T4"))   %></TD>
			                       			<TD align = "right"><%= formatter.format(dubaoRs_4.getDouble("T1") + dubaoRs_4.getDouble("T2") + dubaoRs_4.getDouble("T3")+dubaoRs_4.getDouble("T4"))  %>   </TD>
			                       		</TR>
			                       		
			                    <% 		i++;
			                    	} 
			                    	 dubaoRs_4.close();
			                    }
			                   
			       		%>
			                        									
            						</TABLE>
	                     							
       							</DIV>  
       							  
       						</DIV>                      
				 	
				 	
				 	</TD>
				 </TR> 		
            </TABLE>
            
     
  
</form>

</BODY>

</html>

<% 
 	if(spRs != null) spRs.close(); %>
<% 	if(nhRs != null) nhRs.close(); %>
<% 	if(clRs != null) clRs.close(); %>
<% 	
	dbkdBean.close(); 
	session.setAttribute("dbkdBean", null) ; 
%>
