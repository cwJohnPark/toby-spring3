package springbook.learningtest.template.templatecallback;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 템플릿 콜백 패턴을 적용한 계산기
 */
public class Calculator {

    /**
     * 3-34 BufferdReaderCallback을 사용하는 템플릿 메소드
     */
    public Integer fileReadTemplate(String filepath, BufferedReaderCallback callback) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filepath));
            int ret = callback.doSomethingWithReader(br);
            return ret;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    // 클라이언트 코드
    public Integer calcSum(String filePath) throws IOException {
        // 콜백 객체
        BufferedReaderCallback sumCallBack = new BufferedReaderCallback() {
            public Integer doSomethingWithReader(BufferedReader br) throws IOException {
                Integer sum = 0;
                String line = null;
                while ((line = br.readLine()) != null) {
                    sum += Integer.valueOf(line);
                }
                return sum;
            }
        };
        return fileReadTemplate(filePath, sumCallBack);
    }

    /**
     * 3-37 곱을 계산하는 콜백을 가진 calcMultiply() 메소드
     */
    public Integer calcMultiply(String filepath) throws IOException {
        BufferedReaderCallback callback = new BufferedReaderCallback() {
            public Integer doSomethingWithReader(BufferedReader br) throws IOException {
                Integer multiply = 1;
                String line = null;
                while ((line = br.readLine()) != null) {
                    multiply *= Integer.valueOf(line);
                }
                return multiply;
            }
        };
        return fileReadTemplate(filepath, callback);
    }
}
