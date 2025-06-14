package hp.urlshortener.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hp.urlshortener.base.ApiResponse;
import hp.urlshortener.entity.ShortUrl;
import hp.urlshortener.repository.ShortUrlRepository;
import hp.urlshortener.service.ShortUrlService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ShortUrlController {

	private final ShortUrlService shortUrlService;
	private final ShortUrlRepository shortUrlRepository;

	@PostMapping
	public ResponseEntity<ApiResponse<?>> createShortUrl(@RequestParam String originalUrl, HttpServletRequest request) {
		try {
			Optional<ShortUrl> existing = shortUrlRepository.findByOriginalUrl(originalUrl);

			String baseUrl = request.getRequestURL().toString().replace(request.getRequestURI(), "");
			String shortCode;
			String fullShortUrl;

			if (existing.isPresent()) {
				shortCode = existing.get().getShortCode();
				fullShortUrl = baseUrl + "/" + shortCode;

				return ResponseEntity.ok(new ApiResponse<>("success", "Already short_url :-", fullShortUrl));
			}
			shortCode = shortUrlService.creatShortUrl(originalUrl);
			fullShortUrl = baseUrl + "/" + shortCode;

			return ResponseEntity.ok(new ApiResponse<>("success", "Created short_url :-", fullShortUrl));
		} catch (Exception exp) {
			exp.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse<>("error", "Internal Server Error ❌", null));
		}
	}

	@GetMapping("/{short_code}")
	public ResponseEntity<ApiResponse<?>> getShortCode(@PathVariable("short_code") String short_code,
			HttpServletRequest request) {
		try {
			Optional<ShortUrl> shortUrlOpt = shortUrlService.getShortCode(short_code);
			if (shortUrlOpt.isPresent()) {
				String baseUrl = request.getRequestURL().toString().replace(request.getRequestURI(), "");
				String fullUrl = baseUrl + "/" + shortUrlOpt.get().getShortCode();
				return ResponseEntity.status(HttpStatus.FOUND)
						.body(new ApiResponse<>("error", "Redirect :- ", fullUrl));
			} else {

				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(new ApiResponse<>("error", "not found short_url :- ", null));
			}

		} catch (Exception exp) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse<>("error", "Internal Server Error ??", null));
		}
	}

	@GetMapping("/analytics/{short_code}")
	public ResponseEntity<ApiResponse<?>> analytics(@PathVariable("short_code") String short_code) {
		try {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ApiResponse<>("success", "Total Visited :- ", shortUrlService.analytics(short_code)));
		} catch (Exception exp) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse<>("error", "Internal Server Error ??", null));
		}
	}

	@GetMapping("/all")
	public ResponseEntity<ApiResponse<?>> getList() {
		try {

			return ResponseEntity.status(HttpStatus.OK)
					.body(new ApiResponse<>("success", "Result :", shortUrlService.getList()));
		} catch (Exception exp) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse<>("error", "Internal Server Error ??", null));
		}
	}

}
