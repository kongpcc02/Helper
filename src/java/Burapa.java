
import java.io.*;
import java.text.DecimalFormat;

public class Burapa {

    static String folder = "D:\\Burapa\\";

    public static void main(String args[]) {

        try {
            File file = new File(folder);
            File[] files = file.listFiles();
            for (int fileInList = 0; fileInList < files.length; fileInList++) {
                if (files[fileInList].toString().indexOf("RMT") >= 0) {
                    createNewFile(files[fileInList].getName(),
                            readAndConvertRMT(files[fileInList].getName()));
                }
                if (files[fileInList].toString().indexOf("TRX") >= 0) {
                    createNewFile(files[fileInList].getName(),
                            readAndConvertTRX(files[fileInList].getName()));
                }
            }
        } catch (Exception e) {
            System.out.println("Main " + e);
        }
    }

    public static String readAndConvertRMT(String fileName) {
        StringBuilder r = new StringBuilder();
        try {
            FileInputStream fstream = new FileInputStream(folder + fileName);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;

            while ((strLine = br.readLine()) != null) {
                String[] sa = strLine.split("\t");
                if (sa.length == 9) {
                    r.append(sa[0]).append("\t").append(sa[1]).append("\t").append(sa[2].length() == 6 ? convertEmpID(sa[2]) : sa[2]).append("\t").append(convertSHF(sa[3])).append("\t").append(sa[4]).append("\t").append(sa[5]).append("\t").append(sa[6]).append("\t").append(sa[7]).append("\t").append(sa[8]).append("\n");
                }
            }
            in.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e);
        }
        return r.toString();
    }

    public static String readAndConvertTRX(String fileName) {
        StringBuilder r = new StringBuilder();
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        try {
            FileInputStream fstream = new FileInputStream(folder + fileName);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;

            while ((strLine = br.readLine()) != null) {
                String[] sa = strLine.split("\t");

                if (sa.length == 14) {
                    int cost = Integer.parseInt(sa[9]) % 3 == 0 ? Integer.parseInt(sa[9]) / 3 : Integer.parseInt(sa[9]);
                    Double vat = Double.valueOf(cost) * 7 / 107;
                    Double exvat = Double.valueOf(cost) - vat;

                    r.append(sa[0]).append("\t").append(sa[1]).append("\t").append(sa[2].length() == 6 ? convertEmpID(sa[2]) : sa[2]).append("\t").append(convertSHF(sa[3])).append("\t").append(sa[4]).append("\t").append(sa[5]).append("\t").append(sa[6]).append("\t").append(sa[7]).append("\t").append(sa[8]).append("\t").append(cost).append("\t").append(0).append("\t").append(cost).append("\t").append(Double.valueOf(twoDForm.format(vat))).append("\t").append(Double.valueOf(twoDForm.format(exvat))).append("\n");
                }
            }
            in.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error2: " + e);
        }
        return r.toString();
    }

    public static void createNewFile(String fileName, String str) throws IOException {
        try {
            // Create file 
            FileWriter fstream = new FileWriter(folder + "new\\" + fileName);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(str);
            out.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error3: " + e.getMessage());
        }
    }

    public static String convertEmpID(String id) {
        return id.substring(0, 1) + id.substring(2);
    }

    public static String convertSHF(String id) {
        if (id.equals("A")) {
            return "1";
        } else if (id.equals("B")) {
            return "2";
        } else if (id.equals("C")) {
            return "3";
        } else {
            return id;
        }
    }
}
