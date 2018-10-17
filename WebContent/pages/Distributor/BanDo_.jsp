<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.distributor.beans.bando.IBando" %>
<%@ page  import = "geso.traphaco.distributor.beans.bando.imp.Bando" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% IBando bdBean = (IBando)session.getAttribute("obj"); %>
<% ResultSet ddkd = bdBean.getDdkdRs(); %>
<% ResultSet tbh = bdBean.getTbhRs(); %>
<% ResultSet khSelected = bdBean.getKhDaViengThamRs(); %>
<% ResultSet khList = bdBean.getKhChuaViengThamRs(); %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	
	<script src="//maps.google.com/maps?file=api&amp;v=2&amp;key=AIzaSyDUs-Mowbr3M3jRoVxy30B9uBAlEFgbKMQ"
            type="text/javascript"></script>
    
    <script type="text/javascript">

    //var map = new GMap2(document.getElementById("map_canvas")); 
    
    function initialize() 
    {
      if (GBrowserIsCompatible())
      {
        var map = new GMap2(document.getElementById("map_canvas"));    
        //map.addControl(new GNavLabelControl());
        
        map.setCenter(new GLatLng(10.80913414,106.66382645), 17);
        map.setUIToDefault();
        
        // Create our "tiny" marker icon
        var blueIcon = new GIcon(G_DEFAULT_ICON);
        blueIcon.image = "../images/maps_human.png";
		blueIcon.iconSize = new GSize(39, 39);
        
		// Set up our GMarkerOptions object
		markerOptions = { icon:blueIcon };
		
		 var blueIcon2 = new GIcon(G_DEFAULT_ICON);
	     blueIcon2.image = "http://gmaps-samples.googlecode.com/svn/trunk/markers/blue/blank.png";
			
		// Set up our GMarkerOptions object
		markerOptions2 = { icon:blueIcon2 };

		/*
		var center = new GLatLng(10.771317,106.689668);
	
		var markerC = new GMarker(center, markerOptions);
		markerC.value = 'Cafe trung nguyen';

      	GEvent.addListener(markerC,"click", function()
      	{
			var myHtml = "<b>" + 'Cafe Trung Nguyen' + "</b><br/>" + '82 Bui Thi  Xuan - Quan 1';
			map.openInfoWindowHtml(center, myHtml);
      	});
		
		map.addOverlay(markerC);
		*/
		<%
			if(khList != null)
			{
				while(khList.next())
				{
			%>  
		          //var latlng = new GLatLng(southWest.lat() + latSpan * Math.random(),southWest.lng() + lngSpan * Math.random());
		          var latlng = new GLatLng(parseFloat('<%= khList.getString("lat") %>'), parseFloat('<%= khList.getString("long") %>') );       
		          var marker = new GMarker(latlng, markerOptions);
		          map.addOverlay(marker);
			<%}}
		%>

		var lat = new Array();
		var mar = new Array();
		
		<%
		if(khSelected != null)
		{
			int pos = 0;
			while(khSelected.next())
			{
		%>
		
		lat[<%= pos %>] = new GLatLng(parseFloat('<%= khSelected.getString("lat") %>'), parseFloat('<%= khSelected.getString("long")%>'));      
        mar[<%= pos %>] = new GMarker(lat[<%= pos %>], markerOptions2);
        
        GEvent.addListener(mar[<%= pos %>],"click", function()
	      	{
	      		var noidung = '<b>Thời điểm: </b><%= khSelected.getString("thoidiem") %><br /><b>Số điện thoại: </b><%= khSelected.getString("dienthoai") %><br /><b>Tên nhân viên: </b><%= khSelected.getString("ddkdTen") %><br /><b>Khách hàng: </b><%= khSelected.getString("khTen") %>';
				map.openInfoWindowHtml(new GLatLng(parseFloat('<%= khSelected.getString("lat") %>'), parseFloat('<%= khSelected.getString("long") %>') ), noidung);
	      	});   
	    map.addOverlay(mar[<%= pos %>]);
	
		<% pos++; }}
	%>
		
      }
    }
    
    </script>
    
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
    
    <script language="javascript" src="../scripts/datepicker.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
  	
  	<script type="text/javascript">
	  	function submitform()
		{  
		    document.forms["bdForm"].submit();
		}
  	</script>
</head>

<body onload="initialize()" onunload="GUnload()">
<form name="bdForm" method="post" action="../../BandoSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="nppId" value='<%= bdBean.getNppId() %>'>
<input type="hidden" name="action" value='1'>

<div id="main" style="width:99.5%; padding-left:2px">

	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:55%; padding:5px; float:left" class="tbnavigation">
        	Quan ly nhan vien > tinh trang hoat dong
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chao mung nha phan phoi <%= bdBean.getNppTen() %> &nbsp;&nbsp;
        </div>
    </div>
    
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../TratrungbaySvl?userId=UserID" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
    </div>
    
    <div style="width:100%; float:none" align="left">
    <fieldset>
    	<legend class="legendtitle">Tinh trang hoat dong</legend>
        <TABLE width="100%" cellpadding="6" cellspacing="0">								   
            <TR>
                <TD class="plainlabel" width="150" >Ngay </TD>
                <TD class="plainlabel">
                    <input type="text" size="10" class="w8em format-d-m-y divider-dash highlight-days-12" 
                           id="ngay" name="ngay" value="<%= bdBean.getDate() %>" maxlength="10" />
                </TD>
            </TR>
            <TR>
                <TD class="plainlabel" >Dai dien kinh doanh</TD>
                <TD class="plainlabel">
                    <select name="ddkd" id="ddkd" onChange = "submitform();">
                            <option value="" selected></option>
                            <% if(ddkd != null){
								  try{ while(ddkd.next()){ 
					    			if(ddkd.getString("ddkdId").equals(bdBean.getDdkdId())){ %>
					      				<option value='<%= ddkd.getString("ddkdId")%>' selected><%= ddkd.getString("ddkdTen") %></option>
					      			<%}else{ %>
					     				<option value='<%= ddkd.getString("ddkdId")%>'><%= ddkd.getString("ddkdTen") %></option>
					     	<%}} ddkd.close(); }catch(java.sql.SQLException e){} }%>
                        </select>    
                </TD>
            </TR>
            <TR>
                <TD class="plainlabel" >Tuyen ban hang</TD>
                <TD class="plainlabel">
                    <select name="tbh" id="tbh" onChange = "submitform();">
                          <option value="" selected></option>
                          <% if(tbh != null){
						  try{ while(tbh.next()){ 
			    			if(tbh.getString("tbhId").equals(bdBean.getTbhId())){ %>
			      				<option value='<%= tbh.getString("tbhId")%>' selected><%= tbh.getString("tbhTen") %></option>
			      			<%}else{ %>
			     				<option value='<%= tbh.getString("tbhId")%>'><%= tbh.getString("tbhTen") %></option>
			     	<%}} tbh.close(); }catch(java.sql.SQLException e){} }%>
                      </select>    
               </TD>
            </TR>
        </TABLE>
     	<hr>
    
	<div id="map_canvas" style="width: 100%; height: 650px; position:static; margin-left:auto; margin-right:auto">
    </div>
    
    </fieldset></div>
 </div>
 </form>
</body>
</html>

<% bdBean.DBclose(); %>
<%}%>