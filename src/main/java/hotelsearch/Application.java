package hotelsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by vsushil on 9/14/2017.
 */

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "hotelsearch")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}