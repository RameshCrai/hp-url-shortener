package hp.urlshortener.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import hp.urlshortener.dto.ShortUrlDto;
import hp.urlshortener.entity.ShortUrl;

@Mapper(componentModel = "spring")
public interface ShortUrlMapper {

	ShortUrlMapper instance = Mappers.getMapper(ShortUrlMapper.class);

	ShortUrl toEntity(ShortUrlDto dto);

	ShortUrlDto toDto(ShortUrl shortUrl);

}
