package com.ycrash.springboot.buggy.app;

import com.fasterxml.jackson.databind.Module;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@OpenAPIDefinition(servers = {@Server(url = "/")},
        info = @Info(title = "SpringBoot Buggy API Service", version = "1.0.0", description = "Documentation for SpringBoot Buggy Api Service")
)
@SpringBootApplication
@ComponentScan(basePackages = {
        "com.ycrash.springboot.buggy.app",
        "com.ycrash.springboot.buggy.app.controller",
        "com.ycrash.springboot.buggy.app.service.blockedapp",
        "com.ycrash.springboot.buggy.app.service.cpuspike",
        "com.ycrash.springboot.buggy.app.service.deadlock",
        "com.ycrash.springboot.buggy.app.service.memoryleak",
        "com.ycrash.springboot.buggy.app.service.metaspaceleak",
        "com.ycrash.springboot.buggy.app.service.oomcrash",
        "com.ycrash.springboot.buggy.app.service.stackoverflow",
        "com.ycrash.springboot.buggy.app.service.threadleak",
        "com.ycrash.springboot.buggy.app.service.webclient",
        "com.ycrash.springboot.buggy.app.service.resttemplate",
        "com.ycrash.springboot.buggy.app.service.books",
		"com.ycrash.springboot.buggy.app.service.concurrency",
		"com.ycrash.springboot.buggy.app.service.network"

})
public class SpringBootBuggyApp implements CommandLineRunner {

    public static void main(String args[]) throws Exception {
        new SpringApplication(SpringBootBuggyApp.class).run(args);
        System.out.println("--> http://localhost:8090/swagger-ui/index.html");
    }

    @Override
    public void run(String... arg0) throws Exception {
        if (arg0.length > 0 && arg0[0].equals("exitcode")) {
            throw new ExitException();
        }
    }

    @Bean
    public WebMvcConfigurer webConfigurer() {
        return new WebMvcConfigurer() {
        };
    }

    @Bean
    public Module jsonNullableModule() {
        return new JsonNullableModule();
    }

    static class ExitException extends RuntimeException implements ExitCodeGenerator {
        private static final long serialVersionUID = 1L;

        @Override
        public int getExitCode() {
            return 10;
        }

    }
}
