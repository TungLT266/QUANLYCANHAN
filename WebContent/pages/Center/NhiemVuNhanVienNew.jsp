<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.nhiemvu.*" %>
<%@ page  import = "geso.traphaco.center.beans.nhiemvu.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<% INhiemVuNhanVien nvnvBean = (INhiemVuNhanVien)session.getAttribute("nvnvBean"); %>
<% ResultSet dvkdList = (ResultSet)nvnvBean.getDvkdList(); %>
<% ResultSet kbhList = (ResultSet)nvnvBean.getKbhList(); %>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">

	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/jquery.min.js"></script>
    <script type="text/javascript" src="..scripts/jquery-1.js"></script>
    <link type="text/css" rel="stylesheet" href="../css/mybutton.css">
    
    <LINK rel="stylesheet" type="text/css" href="../css/style.css" />
	<style type="text/css">
		#mainContainer{
			width:600px;
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
    
	<script language="javascript" type="text/javascript">
	
	/* var NhiemVu = function(id, tieuchi, doituong) {
		this.id = id;
		this.tieuchi = tieuchi;
		this.doituong = doituong;
	}
	var nvList = []; */
	
	function changeDoiTuong() {
		var _dt = $("#doituongSlt").val();
		
		$("tr[type=nvct]").hide();
    	$('tr[type=nvct][dtId="'+_dt+'"]').show(200);
	}
	
	if(!String.prototype.trim) {
	  String.prototype.trim = function () {
		return this.replace(/^\s+|\s+$/g,'');
	  };
	}
	
	function submitform()
	{   
	   document.forms["nvForm"].submit();
	}
	
	function saveform()
	{
		var dvkd = $("#dvkdSlt").val().trim();
		if(dvkd === "0") {
			alert("Bạn chưa chọn đơn vị kinh doanh!");
			return;
		}
		
		var kbh = $("#kbhSlt").val().trim();
		if(kbh === "0") {
			alert("Bạn chưa chọn kênh bán hàng!");
			return;
		}
		
		document.forms["nsptbForm"].action.value = "save";
		document.forms["nsptbForm"].submit();		
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
	 function Dinhdang(element)
		{
			element.value=DinhDangTien(element.value);
			if(element.value== '' ||element.value=='0' )
			{
				element.value= '';
			}
		}
	 
	 function xuatExcel()
	 {
	 	document.forms['nvForm'].action.value= 'excel';
	 	document.forms['nvForm'].submit();
	 	document.forms['nvForm'].action.value= '';
	 }
	 
	 
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="nsptbForm" method="post" action="../../NhiemVuNhanVienUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<div id="main" style="width:99%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:55%; padding:5px; float:left" class="tbnavigation">
        	Quản lý chỉ tiêu > Nhiệm vụ nhân viên > Tạo mới
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../NhiemVuNhanVienSvl?userId=<%= userId %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <A href="javascript:saveform()" >
        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border ="1px" style="border-style:outset"></A>
    </div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
    		<textarea name="dataerror"  style="width: 100% ; font-weight:bold" readonly rows="1"><%= nvnvBean.getMessage() %></textarea>
		    <% nvnvBean.setMessage(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle"> Nhiệm Vụ Nhân Viên</legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="6" cellspacing="0">				
           	  <TR>
                    <TD width="15%" class="plainlabel" valign="top">Diễn giải </TD>
                    <TD class="plainlabel" valign="top"><textarea name="diengiai" id="diengiai" style="width:500px;" rows="1"><%= nvnvBean.getDienGiai() %></textarea></TD>
                </TR> 
                
                <TR>
                    <TD class="plainlabel">Đối tượng<FONT class="erroralert"> *</FONT></TD>
                    <TD class="plainlabel">
                        <select name="doituong" id="doituongSlt" onChange="changeDoiTuong()">
                            <option value="SR">SR</option>
                            <option value="SS">SS</option>
                            <option value="ASM">ASM</option>
							<!--<option value="RSM">RSM</option>-->
                        </select>
                        <script>
                        	var selected = "<%= nvnvBean.getDoiTuong() %>".trim();
							$("#doituongSlt option[value="+selected+"]").attr("selected", "selected");
						</script>
                     </TD>
                </TR>
                <TR>
                    <TD class="plainlabel">Tháng<FONT class="erroralert"> *</FONT></TD>
                    <TD class="plainlabel"> 
                      <select name="thang" id="thangSlt">
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                            <option value="6">6</option>
                            <option value="7">7</option>
                            <option value="8">8</option>
                            <option value="9">9</option>
                            <option value="10">10</option>
                            <option value="11">11</option>
                            <option value="12">12</option>
                        </select>
                      <script>
							//Select thang from session
							var selectedThang = "<%= nvnvBean.getThang() %>".trim();
							$("#thangSlt option[value="+selectedThang+"]").attr("selected", "selected");
						</script>
                  	</TD>
                </TR>
                <TR>
                    <TD class="plainlabel">Năm<FONT class="erroralert"> *</FONT></TD>
                    <TD class="plainlabel"> 
                      <select name="nam" id="namSlt">
                        </select>
                      <script>
							//Create years option
							var currentYear = parseInt(new Date().getYear()) + 1900;
							for(var i = currentYear; i < currentYear + 3; i++) {
								$("#namSlt").html($("#namSlt").html() + "<option value=\"" + i + "\">"+i+"</option>");
							}
							
							//Select nam from session
							var selectedNam = "<%= nvnvBean.getNam() %>".trim();
							$("#namSlt option[value="+selectedNam+"]").attr("selected", "selected");
						</script>
                  </TD>
                </TR>
                
                <TR>
                    <TD class="plainlabel">Đơn vị kinh doanh<FONT class="erroralert"> *</FONT></TD>
                    <TD class="plainlabel">
                      <select name="dvkd" id="dvkdSlt">
                        	<option value="0"  ></option>
							<% 
                                try{											
                                    while (dvkdList.next()){%>													
                                        <%	if(dvkdList.getString("pk_seq").equals(nvnvBean.getDvkdId())){ %>											
                                                <option value= <%=dvkdList.getString("pk_seq")%> selected><%= dvkdList.getString("donvikinhdoanh") %></option>															
                                            <%}else{%>
                                                <option value= <%=dvkdList.getString("pk_seq")%> ><%= dvkdList.getString("donvikinhdoanh") %></option>																																		
                                        <%	}
                                    }
                                } catch(java.sql.SQLException e){
                                    
                                }
                            %>
                        </select>
                  </TD>
                </TR>
                
                <TR>
                    <TD class="plainlabel">Kênh bán hàng<FONT class="erroralert"> *</FONT></TD>
                    <TD class="plainlabel">
                        <select name="kbh" id="kbhSlt">
                        	<option value="0"  ></option>
							<% 
                                try{											
                                    while (kbhList.next()){%>													
                                        <%	if(kbhList.getString("pk_seq").equals(nvnvBean.getKbhId())){ %>											
                                                <option value= <%=kbhList.getString("pk_seq")%> selected><%= kbhList.getString("diengiai") %></option>															
                                            <%}else{%>
                                                <option value= <%=kbhList.getString("pk_seq")%> ><%= kbhList.getString("diengiai") %></option>
                                        <%	}
                                    }
                                } catch(java.sql.SQLException e){
                                    
                                }
                            %>
                        </select>
                  </TD>
                </TR>
                
                				
            </TABLE>
            <hr>
            
        </div>
            
            <div style="width:100%; float:none; clear:none;" align="left">
           <table border="0" cellpadding="1" cellspacing="1" width="100%">
                <tbody><tr class="tbheader">                   
                 
                	<th align="center" width="50%">Nhiệm Vụ</th>
                	<th align="left" width="25%">Số lượng</th>
                	<th align="left" width="25%">Thưởng</th>
                 
                </tr>
                
                <%
               	List <INhiemVuNhanVienChiTiet> nvlist = nvnvBean.getNvList();
                for(int i = 0; i < nvlist.size(); i++) {
                	INhiemVuNhanVienChiTiet nvct = nvlist.get(i);                	
	            %>

					<tr class="tbdarkrow" type="nvct" id="nv_<%=nvct.getNvId() %>" dtId="<%= nvct.getDoiTuong().trim() %>" nvId="<%=nvct.getNvId() %>">
	                    <td align="center">
	                    	<input name="nv_<%=nvct.getNvId() %>" style="width:100%" type="text" value="<%=nvct.getNvTen() %>" readonly>
	                    </td>
	                  <td align="left">
	                    	<input value="<%= nvct.getSoluong() > 0 ? nvct.getSoluong() : "" %>"  onkeyup="Dinhdang(this)"  name="soluong_<%=nvct.getNvId() %>" style="width:100%" type="text">
	                    </td>
	                    <td align="left">
	                    	<input value="<%= nvct.getThuong() > 0 ? nvct.getThuong() : "" %>"  onkeyup="Dinhdang(this)"   name="thuong_<%=nvct.getNvId() %>" style="width:100%" type="text">
	                    </td>
	                </tr>

	                	
	            <%
	            }
                %>
                
                
	                
                	
                <tr class="tbfooter">
                    <td colspan="4">&nbsp;</td>
                </tr>
            </tbody></table> 
            
            <script>
            	var _dt = $("#doituongSlt").val().trim();
            	$("tr[type=nvct]").hide();
            	$('tr[type=nvct][dtId="'+_dt+'"]').show(200);
            </script>
          </div>    
           
     </fieldset>	
    </div>
</div>

</form>
</BODY>
</html>


<% 
	if(kbhList != null) kbhList.close();
	if(dvkdList != null) dvkdList.close();

	nvnvBean.closeDB();
%>