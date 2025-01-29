package morse;

import dev.mcannavan.dotdash.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MorseService {
    private MorsePlayer morsePlayer;

    public void setMorsePlayer(MorsePlayer morsePlayer) {
        this.morsePlayer = morsePlayer;
    }

    public MorsePlayer getMorsePlayer() {
        return morsePlayer;
    }

}
