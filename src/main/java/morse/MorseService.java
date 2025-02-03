package morse;

import dev.mcannavan.dotdash.*;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class MorseService {

    public byte[] generate(Float wpm, Float fwpm, Float ms, Float fms, int volume, Float frequency, String morse) {
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
        return outputStream.toByteArray();
    }

}
