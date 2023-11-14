package hifit.be.user.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class SarcopeniaService {

    public void post() {
        RestTemplate restTemplate = new RestTemplate();

        // 데이터 설정 (예: JSON 데이터)
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("age", 59.0);
        requestBody.put("sex", 2.0);
        requestBody.put("BO1", 4.0);
        requestBody.put("BE5_2", 1.0);
        requestBody.put("BE5_1", 1.0);
        requestBody.put("BE3_11", 1.0);
        requestBody.put("BE3_21", 1.0);
        requestBody.put("BE3_31", 8.0);
        requestBody.put("HE_PLS_1", 60.0);
        requestBody.put("HE_sbp", 131.0);
        requestBody.put("HE_dbp", 85.0);
        requestBody.put("HE_ht", 139.9);
        requestBody.put("HE_wt", 42.9);
        requestBody.put("HE_wc", 85.4);
        requestBody.put("HE_BMI", 21.919);
        requestBody.put("sm_presnt", 0.0);
        requestBody.put("HE_obe", 2.0);

        // 헤더 설정 (예: JSON 형식으로 전송)
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");

        // 요청 엔터티 생성
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        // POST 요청 전송
        ResponseEntity<String> response = restTemplate.exchange("http://127.0.0.1:5001/predict", HttpMethod.POST, entity, String.class);

        String body = response.getBody();
    }
}
