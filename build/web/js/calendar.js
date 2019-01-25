//=====================================================
// Java Calendar Function
//=====================================================
var oldLink = null;

function setActiveStyleSheet(link, title) {
		 var i, a, main;
		 for(i=0; (a = document.getElementsByTagName("link")[i]); i++) {
			       if(a.getAttribute("rel").indexOf("style") != -1 && a.getAttribute("title")) {
			                 a.disabled = true;
			                 if(a.getAttribute("title") == title) a.disabled = false;
			       }
		 }
		 oldLink = link;
		 return false;
};

function selected(cal, date) {
		 cal.sel.value = date; 
		 if (cal.sel.id == "sel1" || cal.sel.id == "sel3")
		 cal.callCloseHandler();
};

function closeHandler(cal) {
		 cal.hide();                     
};

function showCalendar(id, format) {
		 var el = document.getElementById(id);
		 if (calendar != null) {
			       calendar.hide();             
		 } else {
			       var cal = new Calendar(false, null, selected, closeHandler);
			       calendar = cal;                
			       cal.setRange(1900, 2070);     
			       cal.create();
		 }
		 calendar.setDateFormat(format);    
		 calendar.parseDate(el.value); 
		 calendar.sel = el;                 
		 calendar.showAtElement(el);       
		  return false;
};
//= End of Java Cakendar Function =========================================