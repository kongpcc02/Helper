package P107;

import Connect.Connector;

/**
 *
 * @author siridet_suk
 */
public class P107Service {

    public StringBuffer update(String d1, String d2, int line) {
        StringBuffer str = new StringBuffer();
        Connector c = new Connect.Connector("172.20.1.9", "rvauser", "userrva_", "etanetdb", "1521");
        String lineString = String.valueOf(line);
        if (line == 99) {
            lineString = "C";
        }

        try {
            c.connect();
            String sql = " update RVA_TRX_ETC_TRX set VAT=0,EXVAT=INVAT where SERVICE_ID='2' "
                    + " and SHIFT_ID in (select SHIFT_ID from RVA_TRX_ETC_MASTER mas left join RVA_MST_ETC_POS pos on mas.POS_ID=POS.POS_ID"
                    //+ " where SUBSTR(pos.STATION_CODE,0,1)='" + lineString + "' and TRX_DATE BETWEEN  to_Date('" + d1 + "','dd/mm/yyyy') and  to_Date('" + d2 + "','dd/mm/yyyy')) and vat <> 0";
                    + " where  TRX_DATE BETWEEN  to_Date('" + d1 + "','dd/mm/yyyy') and  to_Date('" + d2 + "','dd/mm/yyyy')) and vat <> 0";
            //System.out.println(sql);
            c.executeUpdate(sql);
            str.append("=====update complete=====");
        } catch (Exception ex) {
            str.append("ERROR==").append(ex);
        } finally {
            c.close();
            return str;
        }
    }

    public static void main(String[] args) {
        P107Service s = new P107Service();
        s.update("31/05/2018", "31/05/2018", 3);

    }
}
