package hp.urlshortener;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class UrlshortenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlshortenerApplication.class, args);
	}

	@Value("${spring.profiles.active:default}")
	private String[] activeProfile;

	@PostConstruct
	public void showProfile() {
		if (activeProfile == null || activeProfile.length == 0) {
			System.out.println("No Spring profile set");
		} else {
			System.out.println("Active Spring Profile(s): ");
			for (String profile : activeProfile) {
				System.out.println(" - " + profile);
			}
		}
	}

}
