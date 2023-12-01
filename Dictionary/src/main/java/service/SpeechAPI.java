package service;

import com.microsoft.cognitiveservices.speech.*;

import java.net.ConnectException;
import java.util.Scanner;

public class SpeechAPI extends API {


    private static SpeechConfig speechConfig;
    static {
        subscriptionKey = "1677a1e0e63f4bc3800e07a4186a4bbf";
        serviceRegion = "southeastasia";
        try {
            speechConfig = SpeechConfig.fromSubscription(subscriptionKey, serviceRegion);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void getSpeechFromText(String text, String language) throws Exception {
        speechConfig.setSpeechSynthesisVoiceName(getLanguageCode(language));

        SpeechSynthesizer speechSynthesizer = new SpeechSynthesizer(speechConfig);

        SpeechSynthesisResult speechSynthesisResult = speechSynthesizer.SpeakTextAsync(text).get();

        if (speechSynthesisResult.getReason() == ResultReason.SynthesizingAudioCompleted) {
            System.out.println("Speech synthesized to speaker for text [" + text + "]");
        } else if (speechSynthesisResult.getReason() == ResultReason.Canceled) {
            SpeechSynthesisCancellationDetails cancellation = SpeechSynthesisCancellationDetails
                    .fromResult(speechSynthesisResult);
            System.out.println("CANCELED: Reason=" + cancellation.getReason());

            if (cancellation.getReason() == CancellationReason.Error) {
                System.out.println("CANCELED: ErrorCode=" + cancellation.getErrorCode());
                System.out.println("CANCELED: ErrorDetails=" + cancellation.getErrorDetails());
                System.out.println("CANCELED: Did you set the speech resource key and region values?");
                if (cancellation.getErrorCode() == CancellationErrorCode.ConnectionFailure)
                    throw new ConnectException("There is no internet connection.");
                throw new Exception("There is an error, please try again.");
            }
        }

        speechSynthesizer.close();
        speechSynthesisResult.close();
    }

    private static void waitForUserInput() {
        // Đợi người dùng nhập lệnh
        // Ví dụ: Sử dụng Scanner để đọc từ bàn phím
        Scanner scanner = new Scanner(System.in);
        System.out.println("Nhập lệnh để ngắt (ví dụ: nhấn phím Enter)");
        scanner.nextLine();

        // Đóng Scanner sau khi sử dụng
        scanner.close();
    }
    private static String getLanguageCode(String language) {
        switch (language) {
            case "en":
                return "en-US-AriaNeural";
            case "vi":
                return "vi-VN-HoaiMyNeural";
            default:
                return "";
        }
    }
    public static void main(String[] args) throws Exception {
        getSpeechFromText("Hello Hello Hello Hello Hello Hello Hello Hello Hello Hello Hello Hello Hello Hello Hello Hello ", "en");
    }
}