package springbook.learningtest.template.nopattern;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * 처음 만든 Calculator 클래스 코드
 * 예외처리가 되어 있지 않음
 */
public class Calculator_NotHandlingException {
    public Integer calcSum(String filepath) throws IOException {
        // 한 줄씩 읽기 편하게 BufferedReader로 파일을 가져온다.
        BufferedReader br = new BufferedReader(new FileReader(filepath));
        Integer sum = 0;
        String line = null;
        // 마지막 라인까지 한 줄씩 읽어가며 숫자를 더한다.
        while((line = br.readLine()) != null) {
            sum += Integer.valueOf(line);
        }

        // 한 번 연 파일은 반드시 닫아준다.
        br.close();
        return sum;

    }

}
