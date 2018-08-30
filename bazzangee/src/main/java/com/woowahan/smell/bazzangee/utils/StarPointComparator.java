package com.woowahan.smell.bazzangee.utils;

import com.woowahan.smell.bazzangee.domain.food.OrderFood;

import java.util.Comparator;

public class StarPointComparator implements Comparator<OrderFood> {

    @Override
    public int compare(OrderFood former, OrderFood latter) {
        if (former.getReview().getStarPoint() < latter.getReview().getStarPoint()) {
            return 1;
        } else if (former.getReview().getStarPoint() > latter.getReview().getStarPoint()) {
            return -1;
        } else {
            return 0;
        }
    }
}
