
import Connect.Connector;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author siridet_suk
 */
public class XXX {

    private static final String FILENAME = "D:\\u.txt";

  /*  public static void main(String[] args) throws IOException, Exception {
      /*  List<String> extStts = new ArrayList<String>();
        extStts.add("613");
        extStts.add("614");
        extStts.add("615");

        BufferedReader br = null;
        FileReader fr = null;
        Connector c = new Connect.Connector("172.20.1.9", "rvauser", "userrva_", "etanetdb", "1521");
        c.connect();
        c.setAutoCommit(true);

        for (String extStt : extStts) {
            fr = new FileReader(FILENAME);
            br = new BufferedReader(fr);

            String sCurrentLine;

            br = new BufferedReader(new FileReader(FILENAME));
            while ((sCurrentLine = br.readLine()) != null) {
                String col[] = sCurrentLine.split("\t");
                String exnStt = col[0];
                int w4 = Integer.parseInt(col[1]);
                int w6 = Integer.parseInt(col[2]);
                int w6_8 = Integer.parseInt(col[3]);
                int w10 = Integer.parseInt(col[4]);
                c.addBatch("INSERT INTO RVA_MST_CHRG_CLS VALUES ('" + extStt + "', '" + exnStt + "', " + w4 + ", " + w6 + ", " + w10 + ", " + w4 + ", " + w6 + ", " + w10
                        + ", 0, 0, 0, 0, TO_DATE('20180506000000', 'YYYYMMDDHH24MISS'), 1620, TO_DATE('20180517105143', 'YYYYMMDDHH24MISS'), 1620, TO_DATE('20180517105143', 'YYYYMMDDHH24MISS'), 'D', 20, NULL, "
                        + w4 + ", " + w6 + ", " + w10 + ", 'E', 0, 0, 0, 'P', " + w6 + ", " + w6 + ", 0, 0, " + w6 + ", TO_DATE('99991231000000', 'YYYYMMDDHH24MISS'))");
                // c.addBatch("INSERT INTO RVA_MST_CHRG_CLS VALUES ('613', '409', 60, 130, 195, 60, 130, 195, 0, 0, 0, 0, TO_DATE('20180506000000', 'YYYYMMDDHH24MISS'), 1620, TO_DATE('20180517105143', 'YYYYMMDDHH24MISS'), 1620, TO_DATE('20180517105143', 'YYYYMMDDHH24MISS'), 'D', 20, NULL, 60, 130, 195, 'E', 0, 0, 0, 'P', 130, 130, 0, 0, 130, TO_DATE('99991231000000', 'YYYYMMDDHH24MISS'));");
            }
        }
        System.out.println(Arrays.toString(c.executeBatch()));
        c.commit();
        c.close(); 
      
    }*/
 public static void main(String[] args) throws IOException, Exception {
 
        BufferedReader br = null;
        FileReader fr = null;
        Connector c = new Connect.Connector("172.20.1.9", "rvauser", "userrva_", "etanetdb", "1521");
        c.connect();
      //  c.setAutoCommit(false);

   
            fr = new FileReader(FILENAME);
            br = new BufferedReader(fr);

            String sCurrentLine;

            br = new BufferedReader(new FileReader(FILENAME));
            while ((sCurrentLine = br.readLine()) != null) {
                String col[] = sCurrentLine.split("\t");
              // System.out.println("update  RVA_MST_EMP set dvs_code='"+col[1]+"'  where hr_emp_code='1"+col[0]+"' and actice_status='A'");
                 c.addBatch("update  RVA_MST_sct_stt set station_dsc='"+col[2]+"'  where station_code= '"+col[0]+"' ");
              
                
            }
        
        System.out.println(Arrays.toString(c.executeBatch()));
         c.commit();
        c.close();  
    }

}
