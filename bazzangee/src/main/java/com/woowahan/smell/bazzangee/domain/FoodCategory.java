package com.woowahan.smell.bazzangee.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
}
