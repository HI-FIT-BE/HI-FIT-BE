package hifit.be.util;

import hifit.be.user.dto.request.HealthInfoRequest;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RandomForestUtil {

    public static int getSarcopenia(HealthInfoRequest request, Double bmi, double obesity) {
        String inputData =
                "{\"age\": " + request.getAge() + ", " +
                "\"sex\": " + request.getGender().getValue() + ", " +
                "\"HE_PLS_1\": " + request.getHeartRate() + ", " +
                "\"HE_sbp\": " + request.getSystolicBloodPressure()+ ", " +
                "\"HE_dbp\": " + request.getDiastolicBloodPressure() + ", " +
                "\"HE_ht\": " + request.getHeight() + ", " +
                "\"HE_wt\": " + request.getWeight() + ", " +
                "\"HE_wc\": " + request.getWaistSize() + ", " +
                "\"HE_BMI\": " + bmi + ", " +
                "\"HE_obe\": " + obesity + "}";

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
        try {
            executor.execute(cmdLine);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 정규 표현식을 사용하여 숫자 부분만 추출합니다.
        Pattern pattern = Pattern.compile("\\[([0-9.]+)\\]");
        Matcher matcher = pattern.matcher(outputStream.toString().trim());

        if (matcher.find()) {
            String numberStr = matcher.group(1);

            // 문자열을 실수로 파싱합니다.
            float number = Float.parseFloat(numberStr);

            // 실수를 정수로 변환합니다.
            return (int) number;
        } else {
            System.out.println("No number found in the output.");
        }
        return -1;
    }
}
