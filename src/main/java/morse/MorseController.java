package morse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api/morse")
public class MorseController {

    private final MorseService morseService;

    public MorseController(MorseService morseService) {
        this.morseService = morseService;
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST,
                    reason = "Missing request parameters")
    @ExceptionHandler(Exception.class)
    public void handleException() {
    }

    @GetMapping("/generate")
    public ResponseEntity<byte[]> generate(
            @RequestParam(required = false) Float wpm,
            @RequestParam(required = false) Float fwpm,
            @RequestParam(required = false) Float ms,
            @RequestParam(required = false) Float fms,
            @RequestParam(defaultValue = "100") int volume,
            @RequestParam(defaultValue = "700") float frequency,
            @RequestParam() String morse
            ) {
        if(morse == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=morse_audio.wav");
        headers.add(HttpHeaders.CONTENT_TYPE, "audio/wav");

        byte[] audioData = morseService.generate(wpm,fwpm,ms,fms,volume,frequency,morse);

        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println("REQUEST AT "+timeFormat.format(new Date(System.currentTimeMillis())));
        System.out.println("morse: "+morse);
        System.out.println(wpm+" wpm | "+fwpm+" fwpm | "+ms+" ms | "+fms+" fms | "+volume+" volume | "+frequency+" frequency ");
        System.out.println("------------------------------------------------");
        return new ResponseEntity<>(audioData, headers, HttpStatus.OK);
    }


    @GetMapping("/translate")
    public ResponseEntity<String> translate() {
        return ResponseEntity.status(404).build();
    }
}
