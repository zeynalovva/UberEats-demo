package az.zeynalovv.UberEats.identity;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class MsIdentityApplication {
  public static void main(String[] args) {
    SpringApplication.run(MsIdentityApplication.class, args);
  }
}
