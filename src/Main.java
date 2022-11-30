import java.io.*;

public class Main {
    static int[] arr1, arr2;

    public static void main(String[] args) {
        int[] max = new int[] {51,101,501,1001,901,901,901,901};
        int[] k = new int[] {301,301,301,301,9,90,201,301};
        int[] l = new int[] {1,1,1,1,1,10,100,1};
        String err = "noErr";
        File file;
        long x1, x2;
        int p;
        int countErr = 0;
        int countP = 0;
        try (FileWriter writer = new FileWriter("s3.txt", false)) {
            writer.write("");
            writer.flush();
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        for (int i = 0; i < 8; i++) {
            arr1 = fillArr(max[i],k[i],l[i]); //заполняем массив
            try (FileWriter writer = new FileWriter("s1.txt", false)) {
                for (int j : arr1) {
                    writer.write(j + "\n");
                }
                writer.flush();
            }
            catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            serialize(arr1);
            arr2 = deserialize();
            for (int j = 0; j < max[i]; j++) {
                if (arr1[j] != arr2[j]) {
                    err = "err";
                    break;
                }
            }
            file = new File("s1.txt");
            x1 = file.length();
            file = new File("s2.txt");
            x2 = file.length();
            p = (int) (100 * (x1 - x2) / x1);
            countP = p;
            try (FileWriter writer = new FileWriter("s3.txt", true)) {
                writer.write("Тест №" + (i+1) +"; ");
                writer.write("Размер до: " + x1 +"; ");
                writer.write("Размер после: " + x2 +"; ");
                writer.write("Относительное уменьшение:" + p +"%; ");
                writer.write("Наличие ошибок:" + err +".\n");
                err = "noErr";
                writer.flush();
            }
            catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        for (int i = 0; i < 100; i++) {
            int maxR = (int) (Math.random()*1001 + 1);
            arr1 = fillArr(maxR,300,1);
            try (FileWriter writer = new FileWriter("s1.txt", false)) {
                for (int j : arr1) {
                    writer.write(j + "\n");
                }
                writer.flush();
            }
            catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
            serialize(arr1);
            arr2 = deserialize();
            for (int j = 0; j < maxR; j++) {
                if (arr1[j] != arr2[j]) {
                    countErr++;
                    break;
                }
            }
            file = new File("s1.txt");
            x1 = file.length();
            file = new File("s2.txt");
            x2 = file.length();
            p = (int) (100 * (x1 - x2) / x1);
            countP = (countP + p)/2;
        }
        try (FileWriter writer = new FileWriter("s3.txt", true)) {
            writer.write("Всего ошибок:" + countErr +"; ");
            writer.write("Среднее относительное уменьшение:" + countP +"%.");
            writer.flush();
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    static int[] fillArr(int max, int k, int l) {
        int[] n = new int[max];
        for (int i = 0; i < max; i++) {
            n[i] = (int) (Math.random()*k + l);
        }
        return n;
    }
    static String intArrToStr(int[] arr) {
        StringBuilder s;
        if ((arr1.length*9+3)%8 != 0) {
            s = new StringBuilder(String.format("%3s", Integer.toBinaryString(8 - (arr.length * 9 + 3) % 8)).replaceAll(" ", "0")
                    + String.format("%" + (8 - (arr.length * 9 + 3) % 8) + "s", 0).replaceAll(" ", "0"));
        }
        else {
            s = new StringBuilder("000");
        }
        for (int i : arr) {
            s.append(String.format("%9s", Integer.toBinaryString(i)).replaceAll(" ", "0"));
        }
        StringBuilder buf = new StringBuilder();
        String ss = s.toString();
        for (int j = 0; j < ss.length(); j += 8) {
            buf.append((char) Integer.parseInt(ss.substring(j,j+8),2));
        }
        return buf.toString();
    }
    static int[] strToIntArr(String s) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            buf.append(String.format("%8s", Integer.toBinaryString(s.charAt(i))).replaceAll(" ", "0"));
        }
        s = buf.toString();
        s = s.substring(Integer.parseInt(s.substring(0,3),2) + 3);
        int[] arr = new int[s.length()/9];
        for (int i = 0; i < s.length(); i += 9) {
            arr[i/9] = Integer.parseInt(s.substring(i,i+9),2);
        }
        return arr;
    }
    static void serialize (int[] arr) {
        try(FileWriter writer = new FileWriter("s2.txt", false)) {
            writer.write(intArrToStr(arr));
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
    static int[] deserialize() {
        StringBuilder s = new StringBuilder();
        try(FileReader reader = new FileReader("s2.txt")) {
            int c;
            while((c=reader.read())!=-1) {
                s.append((char) c);
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
        return strToIntArr(s.toString());
    }
}