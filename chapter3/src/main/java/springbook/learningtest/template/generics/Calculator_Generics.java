package springbook.learningtest.template.generics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 제너릭과 라인 콜백을 사용한 계산기
 */
public class Calculator_Generics {

    /**
     * 3-43 문자열 연결 기능 콜백을 이용해 만든 concatenate() 메소드
     */
    public String concatenate(String filepath) throws IOException {
        LineCallback<String> concatenateCallback =
            new LineCallback<String>() {
                public String doSomethingWithLine(String line, String value) {
                    return value + line;
                }};
        return lineReadTemplate(filepath, concatenateCallback, "");
    }

    /**
     * 3-42 타입 파라미터를 추가해서 제네릭 메소드로 만든 lineReadTemplate()
     */
    public <T> T lineReadTemplate(String filepath, LineCallback<T> callback, T initVal) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filepath));
            T res = initVal;
            String line = null;
            while ((line = br.readLine()) != null) {
                res = callback.doSomethingWithLine(line, res);
            }
            return res;
        } catch (IOException e) {
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


}
