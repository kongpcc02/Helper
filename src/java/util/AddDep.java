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
        updateOrgRvaFromHr();
        updateUserOrgCompareHr();

    }

    private static void updateOrgRvaFromHr() {
        Connector rvaConnect = new Connector();
        Connector hrConnect = new Connector("172.20.1.208", "HRUser_101", "cas1@lkilogmL", "EXATDB", "");
        try {
            rvaConnect.connectEta();
            hrConnect.connectSqlServer();
            String sqlRva = "", sqlHr = "", sqlUpdate = "", sqlInsert = "";
            ResultSet resultRva, resultHr;
            sqlRva = "select * from sec_org where org_code != 'DUMMY'";
            resultRva = rvaConnect.executeQuery(sqlRva);
            while (resultRva.next()) {
                sqlHr = "select * from per_deptab where depcod = '" + resultRva.getString("org_code") + "'";
                System.out.print("sql hr >>  " + sqlHr + "   result  > ");
                resultHr = hrConnect.executeQuery(sqlHr);
                if (resultHr.next() == true) {
                    System.out.println(resultHr.getString("dflag"));
//                    dflag O = Inactive, C = Active
                    if ("O".equals(resultHr.getString("dflag"))) {
                        sqlUpdate = "update sec_org set active_status = 'I', end_date = to_date('09042018', 'ddMMyyyy'), updated_by = '3422', updated_date = sysdate where org_code = '" + resultRva.getString("org_code") + "'";
                        System.out.println(sqlUpdate);
                        rvaConnect.addBatch(sqlUpdate);
                    }
                    continue;
                } else {
                    System.out.print("Not in HR deptab >>  ");
                    sqlUpdate = "update sec_org set active_status = 'I', end_date = to_date('09042018', 'ddMMyyyy'), updated_by = '3422', updated_date = sysdate    where org_code = '" + resultRva.getString("org_code") + "'";
                    System.out.println(sqlUpdate);
                    rvaConnect.addBatch(sqlUpdate);
                }
            }
            rvaConnect.executeBatch();
            rvaConnect.close();
            rvaConnect.connectEta();
            sqlHr = "select * from per_deptab where dflag = 'C'";
            resultHr = hrConnect.executeQuery(sqlHr);
            while (resultHr.next()) {
                sqlRva = "select * from sec_org where org_code = '" + resultHr.getString("depcod") + "'";
                resultRva = rvaConnect.executeQuery(sqlRva);
                if (resultRva.next() == true) {
                    System.out.println("Skip");
                    continue;
                }
                sqlInsert = "insert into sec_org values( (select max(org_id)+1 from sec_org ), '" + resultHr.getString("depcod") + "', '" + resultHr.getString("depnam") + "', '3422', sysdate, '3422', sysdate, 'A', to_date('09042018', 'ddMMyyyy'), null )";
                rvaConnect.addBatch(sqlInsert);
                System.out.println(sqlInsert);
            }
            rvaConnect.executeBatch();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            rvaConnect.close();
            hrConnect.close();
        }
    }

    private static void updateUserOrgCompareHr() {
        Connector rvaConnect = new Connector();
        Connector hrConnect = new Connector("172.20.1.208", "HRUser_101", "cas1@lkilogmL", "EXATDB", "");
        try {
            int count = 0;
            rvaConnect.connectEta();
            hrConnect.connectSqlServer();
            String sqlRva = "", sqlHr = "", sqlUpdate = "", sqlInsert = "", empId = "";
            ResultSet resultRva, resultHr, resultRvaOrg;
            sqlRva = "select * from sec_user where active_status = 'A' and user_name != 'opas_auk'";
            resultRva = rvaConnect.executeQuery(sqlRva);
            while (resultRva.next()) {
                empId = resultRva.getString("user_name").substring(1);
                sqlHr = "select * from per_pertab where empid = '" + empId + "'";
                resultHr = hrConnect.executeQuery(sqlHr);
                if (resultHr.next() == false) {
                    continue;
                }
                sqlRva = "select * from sec_org where org_code = '" + resultHr.getString("depcod") + "'";
                resultRvaOrg = rvaConnect.executeQuery(sqlRva);
                if (resultRvaOrg.next() == false) {
                    continue;
                }
                sqlUpdate = "update sec_user set org_id = '" + resultRvaOrg.getString("org_id") + "', updated_by = '3422', updated_date = sysdate where user_name = '" + resultRva.getString("user_name") + "'";
                count++;
                System.out.println(sqlUpdate);
                rvaConnect.addBatch(sqlUpdate);
            }
            rvaConnect.executeBatch();
            System.out.println(count);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            rvaConnect.close();
            hrConnect.close();
        }

    }
}
