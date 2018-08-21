package com.woowahan.smell.bazzangee.dto;

import com.woowahan.smell.bazzangee.domain.Review;
import com.woowahan.smell.bazzangee.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

@ToString
@Setter
@Getter
@NoArgsConstructor
public class ReviewRequestDto {
    private String contents;
    private MultipartFile image;
    private double starPoint;

    public ReviewRequestDto(String contents, MultipartFile image, double starPoint) {
        this.contents = contents;
        this.image = image;
        this.starPoint = starPoint;
    }

    public Review toEntity(String url, User user) {
        if(this.image == null)
            return new Review(user, contents, this.starPoint);
        return new Review(user, contents, this.starPoint, url);
    }
}
