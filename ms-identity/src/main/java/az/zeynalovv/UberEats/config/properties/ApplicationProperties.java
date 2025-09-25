package az.zeynalovv.UberEats.config.properties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.security.PrivateKey;
import java.security.PublicKey;


@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {

    private final Security security = new Security();

    @Getter
    @Setter
    public static class Security{
        private final Authentication authentication = new Authentication();

        @Getter
        @Setter
        public static class Authentication{
            private String publicKey;
            private String privateKey;
        }
    }
}
