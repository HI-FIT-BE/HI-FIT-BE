import json
import joblib
import sys  # sys 모듈을 임포트해야 합니다.

# 모델 로드
model = joblib.load("/Users/birdie/Desktop/HI-FIT-BE/src/main/java/sarcopenia/Model_Random_Forest/randomforest_sarcopenia.pkl")

# 커맨드 라인 인자로부터 JSON 데이터 읽기
input_data = json.loads(sys.argv[1])

# JSON 데이터를 리스트로 변환
input_values = list(input_data.values())

# 2차원 리스트로 변환
input_values_2d = [input_values]

# 모델에 데이터 입력하여 예측 수행
prediction = model.predict(input_values_2d)

# 결과 출력
print(prediction)
