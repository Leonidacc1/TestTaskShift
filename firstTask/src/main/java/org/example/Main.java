import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.StandardOpenOption;

public class Main {
    /*Создаем переменные для статистики по типам */
    public static int s = 0;
    public static int f = 0;
    public static int N = 0;
    public static boolean o;
    public static boolean a;
    public static boolean p;
    public static boolean S;
    public static boolean F;

    public static String datafaile;
    public static String mode;
    public static String path = "";


    public static boolean isNumeric(String str) {/*Создаем переменная для проверки содержит ли строка цифры*/

        return str.matches("[+-]?\\d+(\\.\\d+)?([Ee][+-]?\\d+)?");
    }
    public static void  main(String[] args) {
        String Prefresult = "";
        Scanner in = new Scanner(System.in);
        System.out.print("Ввидте режим работы программы пример :-s -a -p sample- int1.txt in2.txt \n");
        mode = in.nextLine();
        o = mode.contains("-o");
        if (o == true) {
            {
                Pattern pattern1 = Pattern.compile("-o(.*?) ");
                Matcher matcher1 = pattern1.matcher(mode);

                if (matcher1.find()) {
                    path = matcher1.group(1);
                }
            }
        }
        p = mode.contains("-p");
        if (p == true) {
            {
                Pattern pattern2 = Pattern.compile("-p(.*?)- ");
                Matcher matcher2 = pattern2.matcher(mode);

                if (matcher2.find()) {
                    Prefresult = matcher2.group(1);
                }
            }
        }
        a = mode.contains("-a");
        S = mode.contains("-s");
        F = mode.contains("-f");
        Pattern pattern3 = Pattern.compile(" (.*?).txt ");
        Matcher matcher3 = pattern3.matcher(mode);
        if (matcher3.find()) {
            datafaile = matcher3.group(1);
        }
        System.out.println("s=" + S + ";f=" + F + ";a=" + a + ";p=" + p + ";o=" + o + ";datafaile=" + datafaile);
        try {/*Считываем построчно файл с данными */
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Набиль\\Desktop\\Java\\in1.txt"));
            String line = reader.readLine();
            if (a == false) {
                Files.deleteIfExists(Path.of(path + Prefresult + "_floats.txt"));
                Files.deleteIfExists(Path.of(path + Prefresult + "_integers.txt"));
                Files.deleteIfExists(Path.of(path + Prefresult + "_string.txt"));
            }
            while (line != null) {/* Пока строка не пустая считываем построчно файл и выводим строку*/
                if (isNumeric(line) == true) {/*Провереаем условия на то что строка содержит цифры */
                    if (line.matches("^[+-]?[0-9]+[.][0-9]*([e][+-]?[0-9]+)?$") == true)/*Создаем условия для того чтобы различит цифры целые и с вещественные */ {
                        /*Если строка с вещественным числом говорим тип строки и добовлаем в стастику типа один */
                        f = f + 1;
                        try {
                            Files.writeString(Path.of(path + Prefresult + "_floats.txt"), line + System.lineSeparator(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
                        } catch (IOException e) {
                            System.out.println("Ошибка записи в файл: " + e.getMessage());

                        }
                    } else {
                        N = N + 1;
                        try {
                            Files.writeString(Path.of(path + Prefresult + "_integers.txt"), line + System.lineSeparator(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
                        } catch (IOException e) {
                            System.out.println("Ошибка записи в файл: " + e.getMessage());
                        }
                    }
                } else {
                    s = s + 1;
                    try {
                        Files.writeString(Path.of(path + Prefresult + "_string.txt"), line + System.lineSeparator(), StandardOpenOption.APPEND, StandardOpenOption.CREATE);
                    } catch (IOException e) {
                        System.out.println("Ошибка записи в файл: " + e.getMessage());
                    }
                }
                line = reader.readLine();
            }reader.close();
            if (S == true) {
                System.out.printf("string=%d; integers=%d;floats=%d \n", s, N, f);
            }
            if (F == true) {
                try {
                    Scanner scanner = new Scanner(new FileReader(path + Prefresult + "_floats.txt"));
                    ArrayList<Float> list = new ArrayList<>();
                    float sum = 0;
                    while (scanner.hasNextLine()) {
                        list.add(Float.parseFloat(scanner.nextLine()));
                    }
                    int Size = list.size();
                    for ( int i = 0; i < Size; i++) {
                        sum += list.get(i);
                    }
                    float AVR= sum/Size;
                    Collections.sort(list);
                    System.out.println(list);
                    System.out.printf("avr=%f; max=%f;min=%f \n",AVR, list.getFirst(), list.get(Size-1));
                    scanner.close();
                }
                catch (IOException e) {
                    System.out.println("Ошибка записи в файл: " + e.getMessage());
                }try {
                    Scanner scanner = new Scanner(new FileReader(path + Prefresult + "_integers.txt"));
                    ArrayList<Integer> list = new ArrayList<>();
                    int sum = 0;
                    while (scanner.hasNextLine()) {
                        list.add(Integer.parseInt(scanner.nextLine()));
                    }
                    int Size = list.size();
                    for ( int i = 0; i < Size; i++) {
                        sum += list.get(i);
                    }
                    int AVR= sum/Size;
                    Collections.sort(list);
                    System.out.println(list);
                    System.out.printf("Avr=%d; max=%d;min=%d \n",AVR, list.getFirst(), list.get(Size-1));
                    scanner.close();
                }
                catch (IOException e) {System.out.println("Ошибка в открытии файла: " + e.getMessage());}
            }
        }catch (IOException e) {System.out.println("Ошибка в открытии файла: " + e.getMessage());}
    }
}
