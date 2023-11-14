package hifit.be.util;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RandomForestUtil {

    public static int getSarcopenia() throws IOException {
        String inputData = "{\"age\": 58.0, " +
                "\"sex\": 1.0, " +
                "\"HE_PLS_1\": 64.0, " +
                "\"HE_sbp\": 132.0, " +
                "\"HE_dbp\": 75.0, " +
                "\"HE_ht\": 173.9, " +
                "\"HE_wt\": 72.0, " +
                "\"HE_wc\": 84.2, " +
                "\"HE_BMI\": 23.8085711517, " +
                "\"HE_obe\": 2.0}";

        // Python 스크립트와 함께 입력 데이터를 전달
        CommandLine cmdLine = new CommandLine("python3");
        cmdLine.addArgument("/Users/birdie/Desktop/HI-FIT-BE/src/main/java/sarcopenia/Model_Random_Forest/predict_sarcopenia.py");
        cmdLine.addArgument(inputData, false);  // false는 인자를 그대로 사용하라는 의미입니다.

        // CommandLine 객체 출력
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PumpStreamHandler streamHandler = new PumpStreamHandler(outputStream);

        DefaultExecutor executor = new DefaultExecutor();
        executor.setStreamHandler(streamHandler);

        // 스크립트 실행
        executor.execute(cmdLine);

        // 정규 표현식을 사용하여 숫자 부분만 추출합니다.
        Pattern pattern = Pattern.compile("\\[([0-9.]+)\\]");
        Matcher matcher = pattern.matcher(outputStream.toString().trim());

        if (matcher.find()) {
            String numberStr = matcher.group(1);

            // 문자열을 실수로 파싱합니다.
            float number = Float.parseFloat(numberStr);

            // 실수를 정수로 변환합니다.
            int result = (int) number;

            return result;
        } else {
            System.out.println("No number found in the output.");
        }
        return -1;
    }
}
