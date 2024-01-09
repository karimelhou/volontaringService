package ma.fstt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class VolontaringServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VolontaringServiceApplication.class, args);
	}

}
