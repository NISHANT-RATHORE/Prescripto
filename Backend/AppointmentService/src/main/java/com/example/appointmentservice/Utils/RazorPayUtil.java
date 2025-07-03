package com.example.appointmentservice.Utils;

import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Component
public class RazorPayUtil {
    public static boolean verifySignature(String payload, String expectedSignature, String secret) throws Exception {
        String actualSignature = calculateRFC2104HMAC(payload, secret);
        return actualSignature.equals(expectedSignature);
    }
    private static String calculateRFC2104HMAC(String data, String secret) throws Exception {
        SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return new String(Hex.encodeHex(rawHmac));
    }
}