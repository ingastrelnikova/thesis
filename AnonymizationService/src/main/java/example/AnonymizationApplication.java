package example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AnonymizationApplication {
    public static void main(String[] args) {
        SpringApplication.run(AnonymizationApplication.class, args);
    }
}
