package morse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/morse")
public class MorseController {

    private final MorseService morseService;

    public MorseController(MorseService morseService) {
        this.morseService = morseService;
    }

    @GetMapping("/generate")
    public ResponseEntity<byte[]> generate(
            @RequestParam(required = false) Float wpm,
            @RequestParam(required = false) Float fwpm,
            @RequestParam(required = false) Float ms,
            @RequestParam(required = false) Float fms,
            @RequestParam(defaultValue = "100") int volume,
            @RequestParam(defaultValue = "700") float frequency,
            @RequestParam String morse
            ) {
        
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=morse_audio.wav");
        headers.add(HttpHeaders.CONTENT_TYPE, "audio/wav");

        byte[] audioData = morseService.generate(wpm,fwpm,ms,fms,volume,frequency,morse);

        return new ResponseEntity<>(audioData, headers, HttpStatus.OK);
    }

    @GetMapping("/translate")
    public ResponseEntity<String> translate() {
        return ResponseEntity.status(404).build();
    }
}
