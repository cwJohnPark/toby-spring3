package springbook.learningtest.template.nopattern;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 3-32 try/catch/finally를 적용한 calcSum() 메소드
 */
public class Calculator_HandlingException {

    public Integer calcSum(String filepath) throws IOException {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(filepath));
            Integer sum = 0;
            String line = null;
            while((line = br.readLine()) != null) {
                sum += Integer.valueOf(line);
            }

            return sum;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (br != null) {
                try { br.close(); }
                catch(IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
