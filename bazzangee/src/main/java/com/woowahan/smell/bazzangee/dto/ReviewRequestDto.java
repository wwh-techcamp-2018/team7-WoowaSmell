package com.woowahan.smell.bazzangee.dto;

import com.woowahan.smell.bazzangee.domain.OrderFood;
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
    private Long orderFoodId;
    private String contents;
    private MultipartFile image;
    private double starPoint;

    public ReviewRequestDto(Long orderFoodId, String contents, MultipartFile image, double starPoint) {
        this.orderFoodId = orderFoodId;
        this.contents = contents;
        this.image = image;
        this.starPoint = starPoint;
    }

    public Review toEntity(OrderFood orderFood, String url, User user) {
        if(this.image == null)
            return new Review(orderFood, user, contents, this.starPoint);
        return new Review(orderFood, user, contents, this.starPoint, url, this.image.getOriginalFilename());
    }
}
