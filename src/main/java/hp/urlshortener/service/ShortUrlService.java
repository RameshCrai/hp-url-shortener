package hp.urlshortener.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import hp.urlshortener.entity.ShortUrl;
import hp.urlshortener.repository.ShortUrlRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ShortUrlService {

	private final ShortUrlRepository shortUrlRepository;

	public String creatShortUrl(String originalUrl) {
		Optional<ShortUrl> isExisting = shortUrlRepository.findByOriginalUrl(originalUrl);
		if (isExisting.isPresent()) {
			return isExisting.get().getShortCode();
		}

		ShortUrl shortUrl = ShortUrl.builder().originalUrl(originalUrl).shortCode(generateShortCode()).build();

		shortUrlRepository.save(shortUrl);

		return shortUrl.getShortCode();
	}

	private String generateShortCode() {
		return UUID.randomUUID().toString().substring(0, 6);
	}

	public Optional<ShortUrl> getShortCode(String shortCode) {
		Optional<ShortUrl> optional = shortUrlRepository.findByShortCode(shortCode);

		optional.ifPresent(shortUrl -> {
			shortUrl.setVisitCount(shortUrl.getVisitCount() + 1);
			shortUrlRepository.save(shortUrl);
		});

		return optional;
	}

	public int analytics(String shortCode) {
		return shortUrlRepository.findByShortCode(shortCode).map(ShortUrl::getVisitCount).orElse(0);
	}

	public List<ShortUrl> getList() {
		return shortUrlRepository.findAll();
	}

}
