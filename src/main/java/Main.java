import java.io.*;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) throws NullPointerException {
        TreeMap<String, Integer> resultMap;
        String filePath = null;
        String temp = null;
        StringBuilder text = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Enter the path to the file, please.");
            filePath = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = new File(filePath);

        try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
            while ((temp = fileReader.readLine()) != null) {
                text.append(temp);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] words = text.toString().split("[^A-Za-zА-Яа-я]+");
        resultMap = initTreeMap(words);
        printInfo(resultMap);
        System.out.println();
        printMostCommon(resultMap);
    }

    public static TreeMap<String, Integer> initTreeMap(String[] words) {
        TreeMap<String, Integer> result = new TreeMap<>();
        boolean flag = false;
        String key = null;
        int value = 0;

        for (String word : words) {
            for (Map.Entry<String, Integer> pair : result.entrySet()) {
                if (word.equalsIgnoreCase(pair.getKey())) {
                    flag = true;
                    key = pair.getKey();
                    value = pair.getValue();
                }
            }

            if (flag) {
               result.put(key, ++value);
            }
            else {
                result.put(word, 1);
            }

            flag = false;
        }

        return result;
    }

    public static void printInfo(TreeMap <String, Integer> map) {
        System.out.println("    Information about this file");
        for (Map.Entry<String, Integer> pair : map.entrySet()) {
            System.out.format("Word:\t%-20s Count:\t%-10d\n", pair.getKey(), pair.getValue());
        }
    }

    public static void printMostCommon(TreeMap <String, Integer> map) {
        int maxValue = map.firstEntry().getValue();
        for (Integer value : map.values()) {
            if (maxValue < value) {
                maxValue = value;
            }
        }
        System.out.println("Most commons words in this files:");

        for (Map.Entry<String, Integer> pair : map.entrySet()) {
            if (pair.getValue() == maxValue) {
                System.out.format("Word:\t%-20s Count:\t%-10d\n", pair.getKey(), pair.getValue());
            }
        }
    }
}
