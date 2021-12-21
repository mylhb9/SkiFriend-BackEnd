package com.ppjt10.skifriend.certification;

import java.util.HashMap;
import java.util.Random;


import com.ppjt10.skifriend.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.springframework.stereotype.Service;

import static com.ppjt10.skifriend.properties.ApiKeyForSMS.*;


@Service
@RequiredArgsConstructor
public class MessageService {
    private final SmsCertification smsCertification;

    private String createRandomNumber() {
        Random rand = new Random();
        String randomNum = "";
        for (int i = 0; i < 4; i++) {
            String random = Integer.toString(rand.nextInt(10));
            randomNum += random;
        }

        return randomNum;
    }

    private HashMap<String, String> makeParams(String to, String randomNum) {
        HashMap<String, String> params = new HashMap<>();
        params.put("from", COOLSMS_FROM_PHONE);
        params.put("type", "SMS");
        params.put("app_version", "test app 1.2");
        params.put("to", to);
        params.put("text", randomNum);
        return params;
    }

    // 인증번호 전송하기
    public String sendSMS(String phonNumber) {
        Message coolsms = new Message(API_KEY, API_KEY_SECREAT);

        // 랜덤한 인증 번호 생성
        String randomNum = createRandomNumber();
        System.out.println(randomNum);

        // 발신 정보 설정
        HashMap<String, String> params = makeParams(phonNumber, randomNum);

        try {
            JSONObject obj = (JSONObject) coolsms.send(params);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }

        // DB에 발송한 인증번호 저장
        smsCertification.createSmsCertification(phonNumber,randomNum);

        return "문자 전송이 완료되었습니다.";
    }

    // 인증 번호 검증
    public void verifySms(UserDto.SmsCertificationDto requestDto) {
        if (isVerify(requestDto)) {
            throw new IllegalArgumentException("인증번호가 일치하지 않습니다.");
        }
        smsCertification.deleteSmsCertification(requestDto.getPhoneNumber());
    }

    private boolean isVerify(UserDto.SmsCertificationDto requestDto) {
        return !(smsCertification.hasKey(requestDto.getPhoneNumber()) &&
                smsCertification.getSmsCertification(requestDto.getPhoneNumber())
                        .equals(requestDto.getRandomNumber()));
    }
}
