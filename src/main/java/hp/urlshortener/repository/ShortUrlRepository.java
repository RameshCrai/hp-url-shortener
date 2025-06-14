package hp.urlshortener.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hp.urlshortener.entity.ShortUrl;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, UUID> {

	Optional<ShortUrl> findByOriginalUrl(String originalUrl);

	Optional<ShortUrl> findByShortCode(String shortCode);

	@Query("SELECT s.shortCode FROM ShortUrl s")
	List<String> findAllShortCodes();

}
