package com.example.kakaologin;

import android.app.Application;
import android.content.Context;

import com.kakao.auth.ApprovalType;
import com.kakao.auth.AuthType;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.ISessionConfig;
import com.kakao.auth.KakaoAdapter;
import com.kakao.auth.KakaoSDK;

public class KakaoApplication extends Application
{
    private static volatile KakaoApplication instance = null;
    public static KakaoApplication getGlobalApplicationContext() {
        if(instance == null) {
            throw new IllegalStateException("this application does not inherit com.kakao.GlobalApplication");
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // KakaoSdk를 초기화 함
        KakaoSDK.init(new KakaoSDKAdapter());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        instance = null;
    }


    private static class KakaoSDKAdapter extends KakaoAdapter
    {
        // 로그인 설정값 설정 부분
        public ISessionConfig getSessionConfig() {
            return new ISessionConfig() {
                @Override
                public AuthType[] getAuthTypes() {
                    return new AuthType[] {AuthType.KAKAO_LOGIN_ALL};
                    // 로그인시 인증받을 타입을 지정한다.
                    //KAKAO_LOGIN_ALL: 모든 로그인방식을 사용하고 싶을때 지정.
                }

                @Override
                public boolean isUsingWebviewTimer() {
                    // SDK 로그인시 사용되는 WebView에서 pause와 resume시에 Timer를 설정하여 CPU소모를 절약한다.
                    return false;
                }

                @Override
                public boolean isSecureMode() {
                    // 로그인시 access token과 refresh token을 저장할 때 암호화 여부를 결정한다.
                    return false;
                }

                @Override
                public ApprovalType getApprovalType() {
                   // 일반 사용자가 아닌 Kakao와 제휴된 앱에서 사용되는 값으로, 값을 채워주지 않을경우 ApprovalType.INDIVIDUAL 값을 사용하게 된다.
                    return ApprovalType.INDIVIDUAL;
                }

                @Override
                public boolean isSaveFormData() {
                    return true;
                }
            };
        }

        @Override
        public IApplicationConfig getApplicationConfig() {
            return new IApplicationConfig() {
                @Override
                public Context getApplicationContext() {
                    return KakaoApplication.getGlobalApplicationContext();
                }
            };
        }
    }


}