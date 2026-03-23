package nl.schoepping.spring_renamefiles;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "nl.schoepping.renamefiles")
@EnableAutoConfiguration
public class ApplicationTest {
}
