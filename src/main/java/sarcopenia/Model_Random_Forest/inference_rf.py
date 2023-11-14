# package import
import pandas as pd
import base64
import joblib

# load model    
model_name = "randomforest_sarcopenia.pkl"
model = joblib.load(model_name)

def handler(event, context):
    print('event: ', event)
    
    body = event['body']
    isBase64Encoded = event['isBase64Encoded']
    print('isBase64Encoded: ', isBase64Encoded)

    if isBase64Encoded: 
        print('Base64 decoding is required')
        body_bytes = base64.b64decode(body)
        body_dec = body_bytes.decode('ascii')        
        print('body_dec: ', body_dec)

        values = pd.read_json(body_dec)        
    else:
        print('Base64 decoding is not required')
        print('body: ', body)
        values = pd.read_json(body)        
        
    print('values: ',values)
        
    # inference
    results = model.predict(values)
    print('result:', results)

    return {
        'statusCode': 200,
        'body': results.tolist()
    }