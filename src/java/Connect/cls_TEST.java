/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Connect;

import java.io.*;
import java.util.*;

public class cls_TEST {

    public static void main(String[] args){
        char   input;        int runNumber1 = 0;         int runNumber2 = 0;
                    String sql;
                    String text;
                    String fName = "IndividualTransactionsExitSCW3.csv";
                    //String fContenType = request.getParameter("fContenType");
                    String fSize = "00000";
                    String filepath = "c:\\a\\"+fName; //**


                    ArrayList        al = new ArrayList();
                    ArrayList     check = new ArrayList();               //Check Error;
                    ArrayList       cut = new ArrayList();
                    StringBuffer Output = new StringBuffer();
                    ConNection     cont = new ConNection();
                    cont.Con_Oracle();
///////////////////////////////// DELETE TABLE ////////////////////////////////
                    sql = "DELETE FROM G_ETC";
                    cont.doExecute(sql);
/*/////////////////////////////////////////////////////////////////////////////// **/
                 try{
                    FileReader gFile    = new FileReader(filepath);
                    BufferedReader   in = new BufferedReader(gFile);

                    int nLIne = 0;

                    while ((text = in.readLine()) != null) {

                        nLIne ++;

                         for (int i = 0; i < text.length(); i++) {
                            input = text.charAt(i);//get ค่าทีละตัว
                             if ((input != ',') && (i != (text.length() - 1))) {
                                if (input != '"') {
                                    Output.append(input);
                              } else {
                                i++;
                                    while (text.charAt(i) != '"') {
                                        Output.append(text.charAt(i));
                                        i++;
                                        if (i == text.length()) {
                                            break;
                                        }
                                    }
                                    i++;
                                    al.add(Output.toString()); //check null;
                                    Output.delete(0, Output.length());
                                }
                              } else { //check null
                                  if ((input != ',') && (input != '"')) {
                                    Output.append(input);
                                }  //check null คือ length()>0
                                al.add(Output.toString());
                                Output.delete(0, Output.length());
                           }
                                 }
                        }

                        System.out.println("Read file lines :" + nLIne);
                 }catch(Exception e){
                    System.out.println("File read error : " + e.getMessage());
                 }
                    int    field   = 0; //Value To Check Error Field
                    int    buff    = 0; //Value To Check Error Row
                    double toTal   = 0; //Value To Sum
                    double num1    = 0; //Value To Fare
                    double num2    = 0; //Value To cD
                    String date = "";
                    String time = "";

                    System.out.println("Row count : " + al.size());
                    for (int j = 0; j < al.size(); j = j + 15) {                  //row = จำนวนทั้งหมด (size)/จำนวน columm
     // out.print(runNumber2+" : "+ al.get(runNumber2)+"<BR>"); runNumber2++;   //นำเสนอเป็นชุดของข้อมูล
                        String Plaza      = al.get(j + 0).toString();             //EXIT
                        String Lan        = al.get(j + 1).toString();
                        String StaffNo    = al.get(j + 2).toString();             //EMPID
                        String ExitDate   = al.get(j + 3).toString();
                        String EntryPlaza = al.get(j + 4).toString();             //ENTRY
                        String EntryLan   = al.get(j + 5).toString();
                        String Entrydate  = al.get(j + 6).toString();             //SHIFT_DATE (FULL)
                        String cT         = al.get(j + 7).toString();
                        String cD         = al.get(j + 8).toString();             //COUNT
                        String Fare       = al.get(j + 9).toString();             //FARE
                        String Payment    = al.get(j + 10).toString();
                        String Ntrx       = al.get(j + 11).toString();
                        String PanId      = al.get(j + 12).toString();
                        String ObeId      = al.get(j + 13).toString();
                        String SignalCode = al.get(j + 14).toString();
                        String splitString[] = ExitDate.split(" "); //split date : SHIFT_DATE
//**************************CALCILATE FARE x CD = TOTAL*************************
                        try {
                            if(Fare != "")
                                num1 = Double.parseDouble(Fare);
                            else
                                num1 = 0;
                            if(cD != "")
                                num2 = Double.parseDouble(cD);
                            else
                                num2 = 0;
                            toTal = (num1 * num2);
                   } catch (Exception n) {
                           System.out.print(n + "error total" + "..");
                        }
                  // out.print(num1 + "...." + num2 + "...."+ "result :" + toTal +"<BR>");
//**************************                           *************************
                       buff = (j/15)+1;
//if(j>250){
                       try {

                       sql = "INSERT INTO G_ETC (IDs,SHIFT_DATE,EXITs,EMPID,ENTRYs,FARE,COUNT,AMOUNT,JOB) VALUES ('" + buff + "','" + splitString[0] + "','" + Plaza + "','" + StaffNo + "','" + EntryPlaza + "','" + num1 + "','" + num2 + "','" + toTal + "','"+ 38 + "')";
                       if (! cont.doExecute(sql)){
                           System.out.println("Error : " + cont.gs_error);
                           break;
                       }

                      System.out.println(sql);

                      /*
                           sql = "INSERT INTO JOBS(JOB_ID,JOB_TITLE,MIN_SALARY,MAX_SALARY) VALUES ('" + buff + "','" + Plaza + "','" + 38 + "','" + 40 + "')";
                         cont.doExecute(sql);
 * */
                       //out.print(buff +sql + "<BR>");
                      //out.print(sql + "<BR>");
                     //}
                       }catch (Exception e){
                         System.out.println(e.getMessage());
                       }
}

    }

}
