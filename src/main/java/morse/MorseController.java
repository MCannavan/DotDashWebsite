package morse;

import dev.mcannavan.dotdash.IMorseTiming;
import dev.mcannavan.dotdash.MorsePlayer;
import dev.mcannavan.dotdash.MorseTimingFactory;
import dev.mcannavan.dotdash.MorseTranslator;
import org.bouncycastle.util.encoders.Translator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/morse")
public class MorseController {

    @GetMapping("/generate")
    public ResponseEntity<byte[]> playMorse(
            @RequestParam(required = false) Float wpm,
            @RequestParam(required = false) Float fwpm,
            @RequestParam(required = false) Float ms,
            @RequestParam(required = false) Float fms,
            @RequestParam(defaultValue = "100") int volume,
            @RequestParam(defaultValue = "700") float frequency,
            @RequestParam String morse
            ) {
        MorsePlayer.MorsePlayerBuilder morsePlayerBuilder = new MorsePlayer.MorsePlayerBuilder();
        IMorseTiming timing;
        if (wpm != null) {
            if(fwpm != null) {
                timing = MorseTimingFactory.createFarnsworthTimingFromWpm(fwpm,wpm);
            } else {
                timing = MorseTimingFactory.createParisTimingFromWpm(wpm);
            }
        } else if (ms != null) {
            if(fms != null) {
                timing = MorseTimingFactory.createFarnsworthTimingFromMs(fms, ms);
            } else {
                timing = MorseTimingFactory.createParisTimingFromMs(ms);
            }
        } else {
            timing = MorseTimingFactory.createParisTimingFromWpm(20);
        }
        morsePlayerBuilder.withTiming(timing);
        morsePlayerBuilder.withFrequency(frequency);
        MorsePlayer player;

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            player = morsePlayerBuilder.build();
            outputStream = player.generateWavFileData(player.generateMorseAudio(morse,volume));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=morse_audio.wav");
        headers.add(HttpHeaders.CONTENT_TYPE, "audio/wav");

        byte[] audioData = outputStream.toByteArray();

        return new ResponseEntity<>(audioData, headers, HttpStatus.OK);
    }
}
