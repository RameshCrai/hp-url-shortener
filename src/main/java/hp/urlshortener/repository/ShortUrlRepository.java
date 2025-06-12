package hp.urlshortener.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import hp.urlshortener.entity.ShortUrl;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, UUID> {

}
