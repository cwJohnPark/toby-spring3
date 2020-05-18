package springbook.learningtest.template.linecallback;

import springbook.learningtest.template.templatecallback.BufferedReaderCallback;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 라인 콜백을 사용하는 계산기
 */
public class Calculator_LineCallback {

    /**
     * 3-39 LineCallback 을 사용하는 템플릿
     */
    public Integer lineReadTemplate(String filepath, LineCallback lineCallback, int initVal) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filepath));
            Integer res = initVal;
            String line = null;
            // 파일의 각 라인을 루프를 돌며 가져오는 것도 템플릿이 담당한다.
            while ((line = br.readLine()) != null) {
                // 각 라인의 내용을 가지고 계산하는 작업만 콜백에게 맡긴다.
                // 콜백이 계산한 값을 저장해뒀다가 다음 라인 계산에 다시 사요앟ㄴ다.
                res = lineCallback.doSomethingWithLine(line, res);
            }
            return res;
        } catch (IOException e) {
            throw e;
        } finally {
            if(br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

    }

    public Integer calcSum(String filePath) throws IOException {
        LineCallback sumCallback = new LineCallback() {
            public Integer doSomethingWithLine(String line, Integer value) {
                return value + Integer.valueOf(line);
            }
        };
        return lineReadTemplate(filePath, sumCallback, 0);
    }

    public Integer calcMultiply(String filePath) throws IOException {
        LineCallback multiplyCallback = new LineCallback() {
            public Integer doSomethingWithLine(String line, Integer value) {
                return value * Integer.valueOf(line);
            }
        };
        return lineReadTemplate(filePath, multiplyCallback, 1);
    }

}
