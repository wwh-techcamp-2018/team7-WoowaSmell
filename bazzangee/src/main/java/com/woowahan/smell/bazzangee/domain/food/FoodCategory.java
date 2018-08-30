package com.woowahan.smell.bazzangee.domain.food;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.woowahan.smell.bazzangee.domain.contents.Chat;
import com.woowahan.smell.bazzangee.domain.contents.Review;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Entity
public class FoodCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
    @Column
    private int priority;
    @JsonIgnore
    @OneToMany(mappedBy = "foodCategory", cascade = CascadeType.ALL)
    private List<Restaurant> restaurants;
    @JsonIgnore
    @OneToMany(mappedBy = "foodCategory", cascade = CascadeType.ALL)
    private List<Review> reviews;
    @OneToOne
    private Chat chat;
    @Column
    private String imageUrl;
    @Column
    private String creator;
    @Column
    private LocalDateTime createdTime;

    @Override
    public String toString() {
        return "FoodCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", priority=" + priority +
                ", chat=" + chat +
                ", imageUrl='" + imageUrl + '\'' +
                ", creator='" + creator + '\'' +
                ", createdTime=" + createdTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodCategory that = (FoodCategory) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }
}
