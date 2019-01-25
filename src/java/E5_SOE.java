
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class E5_SOE {

    private static String dd;
    private static final String mm = "10";
    private static final String yyyy = "2016";

    public static void main(String[] args) throws Exception {
        try {
            DecimalFormat fmt = new DecimalFormat("0,000.00");

            Calendar cal = new GregorianCalendar(Integer.parseInt(yyyy), Integer.parseInt(mm) - 1, 1);
            int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);     // 29

            for (int i = 1; i <= days; i++) {
                StringBuilder txt = new StringBuilder();
                dd = String.valueOf(i);

                if (i < 10) {
                    dd = "0" + dd;
                }

                String path = "D:\\E5_SOE\\SAP_08_GL_E5_" + yyyy + "" + mm + "" + dd + "_3.txt";

                File file = new File(path);
                if (file.isFile()) {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String line;
                    Double total = 0.0;
                    String newLine = null;

                    while ((line = br.readLine()) != null) {
                        if (line.contains("4114000")) {
                            String[] arr = line.split("\t");

                            Double d = Double.parseDouble(arr[6]);
                            total += d;
                            newLine = arr[0] + "\t" + arr[1] + "\t" + arr[2] + "\t" + arr[3] + "\t" + arr[4] + "\t9200290\t" + total + "\t" + arr[7] + "\t" + arr[8] + "\t" + arr[9] + "\t20300000\t" + arr[11];
                        } else {
                            txt.append(line);
                            txt.append("\n");
                        }
                    }
                    txt.append(newLine);
                    br.close();
                 //   System.out.println(txt);
                    
                    E5_SOE.writeText(txt.toString(), "D:\\E5_SOE\\SAP_08_GL_E5_" + yyyy + "" + mm + "" + dd + "_5.txt");
                }
            }

        } catch (Exception e) {
            System.out.println("" + e.getMessage());

        }
    }

    public static void writeText(String content, String fName) {
        try {

            File file = new File(fName);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content.replaceAll("(?m)^[ \t]*\r?\n", ""));
            bw.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
