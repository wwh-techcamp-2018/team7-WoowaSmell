package com.woowahan.smell.bazzangee.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@ToString(exclude = "review")
public class Good {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_good_user"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_good_review"))
    private Review review;

    public boolean matchUser(User user) {
        return this.user.equals(user);
    }

    public Good(User user, Review review) {
        this.user = user;
        this.review = review;
    }

    public void resetReview() {
        this.review = null;
    }
}
