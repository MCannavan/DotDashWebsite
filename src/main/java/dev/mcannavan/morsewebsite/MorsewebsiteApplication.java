package dev.mcannavan.morsewebsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("morse")
public class MorsewebsiteApplication {

    public static void main(String[] args) {
        SpringApplication.run(MorsewebsiteApplication.class, args);
    }

}
