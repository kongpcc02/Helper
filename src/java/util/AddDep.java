/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import Connect.Connector;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author chonpisit_klo
 */
public class AddDep {

    public static void main(String[] args) {
        Connector connect = new Connector();
        Connector sqlConnect = new Connector("172.20.1.208", "HRUser_101", "cas1@lkilogmL", "EXATDB", "");
        try {
            connect.connectEta();
            sqlConnect.connectSqlServer();
// ----------------------- UPDATE EMP DEP FROM DEPTAB -----------------------
            int count = 0;
            String sqlRvaUser = "select * from sec_user_back_up  where active_status = 'A' and user_name != 'opas_auk'";
            String sqlDepTab = "", sqlUpdate = "";
            ResultSet resultRvaUser = connect.executeQuery(sqlRvaUser);
            while (resultRvaUser.next()) {
                count++;
                sqlDepTab = "select * from per_pertab where empid = '" + resultRvaUser.getString("user_name").substring(1) + "'";
                ResultSet deptabUser = sqlConnect.executeQuery(sqlDepTab);
                if (deptabUser.next() == true) {
                    System.out.println(deptabUser.getString("empid"));
//                    continue;
                }
//                System.out.print("rva_sec  >>>  "+resultRvaUser.getString("user_name")+ "  dep_code  >  " + resultRvaUser.getString("org_code"));

//                while (deptabUser.next()) {
//                    System.out.println("deptab  >>>  " + deptabUser.getString("empid") + "  dep_code  >  " + deptabUser.getString("depcod"));
//                    sqlUpdate = "update sec_user_back_up set ";
//
//                }
            }
            System.out.println(count);

// ----------------------- END -----------------------
// ----------------------- UPDATE ORG_BACK_UP FROM DEPTAB -----------------------
// HAS SOME ERROR
//            String sqlDep = "", sqlUpdate = "", sqlInsert = "";
//            int count = 0, countInactive = 0, countActive = 0, countInsert = 0;
//            String sqlOrg = "select * from sec_org_back_up";
//            ResultSet resultDeptab, resultOrg;
//            resultOrg = connect.executeQuery(sqlOrg);
//            while (resultOrg.next()) {
//                count++;
//                sqlDep = "select * from SEC_ORG_DEPTAB where org_code = '" + resultOrg.getString("org_code") + "'";
//                resultDeptab = connect.executeQuery(sqlDep);
//                while (resultDeptab.next()) {
//                    if ("I".equals(resultDeptab.getString("active_status"))) {
//                        countInactive++;
//                        System.out.println(count + " > " + resultDeptab.getString("org_dsc") + " Inactived ");
//                        sqlUpdate = "update sec_org_back_up set active_status = 'I', end_date = to_date('09042018', 'ddMMyyyy') where org_code = '" + resultOrg.getString("org_code") + "'";
//                        System.out.println(sqlUpdate);
//                        connect.addBatch(sqlUpdate);
//                        continue;
//                    }
//                    countActive++;
//                    System.out.println(count + " > " + resultDeptab.getString("org_dsc") + " Actived ");
//                }
//            }
//            connect.executeBatch();
//            System.out.println("Count active > " + countActive);
//            System.out.println("Count inactive > " + countInactive);
//            connect.close();
//            connect.connectEta();
//            sqlDep = "select * from sec_org_deptab where active_status = 'A'";
//            resultDeptab = connect.executeQuery(sqlDep);
//            while (resultDeptab.next()) {
//                sqlOrg = "select * from sec_org_back_up where org_code = '" + resultDeptab.getString("org_code") + "'";
//                resultOrg = connect.executeQuery(sqlOrg);
//                if (resultOrg.next()) {
//                    System.out.println("Has deptab >  " + resultOrg.getString("org_code") + "   " + resultOrg.getString("active_status"));
//                    continue;
//                }
//                countInsert++;
//                sqlInsert = "insert into sec_org_back_up values( (select max(org_id)+1 from sec_org_back_up ), '" + resultDeptab.getString("org_code") + "', '" + resultDeptab.getString("org_dsc") + "', '3422', sysdate, '3422', sysdate, 'A', to_date('09042018', 'ddMMyyyy'), null )";
//                System.out.println(sqlInsert);
//                connect.addBatch(sqlInsert);
//            }
//            System.out.println(countInsert);
//            connect.executeBatch();
// ----------------------- END -----------------------
//            connect.close();
//            connect.connectEta();
//            ResultSet resultDeptab = connect.executeQuery(sqlDep);
//            System.out.println("-----------------------------------------------------------------------");
//            while (resultDeptab.next()) {
//                System.out.println(resultDeptab.getString("org_dsc"));
//            }
//            ArrayList<String> extSttCode = new ArrayList<String>();
//            extSttCode.add("701");
//            extSttCode.add("702");
//            extSttCode.add("703");
//            extSttCode.add("704");
//            extSttCode.add("706");
//            extSttCode.add("707");
//            extSttCode.add("708");
//            extSttCode.add("709");
//            extSttCode.add("710");
//            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
//            Date startDate = sdf.parse("01052019");
//            Date endDate = sdf.parse("30092019");
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(startDate);
//            while (calendar.getTime().before(endDate) || calendar.getTime().equals(endDate)) {
//                String sqlInsert = "";
//                for (String stationCode : extSttCode) {
//                    sqlInsert = "insert into RVA_TRX_TOLL_CALC_ITEM values( (select max(id)+1 from RVA_TRX_TOLL_CALC_ITEM), '" + stationCode + "', to_date('" + sdf.format(calendar.getTime()) + "', 'ddMMyyyy'), '1777777777', '1', 40, 0, 0, 1, sysdate, sysdate)";
//                    System.out.println(sqlInsert);
//                    connect.addBatch(sqlInsert);
//                }
//                calendar.add(Calendar.DATE, 1);
//            }
//            connect.executeBatch();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            connect.close();
            sqlConnect.close();
        }

    }

}
