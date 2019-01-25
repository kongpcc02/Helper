<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="data.EVM_MST_CHART_OF_ACCOUNT" %>
<%@page import="bin.MATH" %>
<%@include file="../../config.jsp" %>
<%
//-----รับค่า POST-----//
String VAR_ACC_NO = request.getParameter("txtAccNo");
String VAR_ACC_NAME = request.getParameter("txtAccName");
//-----ตรวจสอบค่า-----//
if(VAR_ACC_NO.equals("")){
    out.println(-1);
    out.close();
}// end if
//-----INSTANCE CLASS MATH-----//
MATH CLS_MATH = new MATH();
if(CLS_MATH.is_number(VAR_ACC_NO)==false ){
    out.println(-2);
    out.close();
}

if(VAR_ACC_NAME.equals("")){
    out.println(-3);
    out.close();
}

//-----INSTANCE CLASS EVM_MST_CHART_OF_ACCOUNT-----//
EVM_MST_CHART_OF_ACCOUNT CLS_ACCOUNT = new EVM_MST_CHART_OF_ACCOUNT();
//-----ติดต่อฐานข้อมูล-----//
CLS_ACCOUNT.connect(CONFIG_HOST, CONFIG_SID, CONFIG_USERNAME, CONFIG_PASSWORD);
//-----เพิ่มข้อมูล-----//
int INSERT_VAL = CLS_ACCOUNT.insert(VAR_ACC_NO, VAR_ACC_NAME, "", "");
if(INSERT_VAL == 1){
    out.println(1);
}else{
    out.println(CLS_ACCOUNT.SQL);
}

%>