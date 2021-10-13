package com.simbirsoftintensiv.intensiv.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;
@Service
public class OtpService {
    //TODO проверить
    //cache based on username and OPT MAX 8
    private static final Integer EXPIRE_MINS = 5;

    private LoadingCache<Long, Integer> otpCache;

    public OtpService(){
        otpCache = CacheBuilder.newBuilder().
                expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES).build(new CacheLoader<Long, Integer>() {
            public Integer load(Long key) {
                return 0;
            }
        });
    }

    //This method is used to push the opt number against Key. Rewrite the OTP if it exists
    //Using user id  as key
    public int generateOTP(Long key){

        Random random = new Random();
        int otp = 10000 + random.nextInt(90000);
        otpCache.put(key, otp);
        return otp;
    }

    //Этот метод используется для возврата номера OTP для Key-> Key values это username
    public int getOtp(Long key){
        try{
            return otpCache.get(key);
        }catch (Exception e){
            return 0;
        }
    }

    //This method is used to clear the OTP catched already
    public void clearOTP(String key){
        otpCache.invalidate(key);
    }
}
