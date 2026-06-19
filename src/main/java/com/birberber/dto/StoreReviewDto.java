package com.birberber.dto;

import com.birberber.domain.store.StoreReview;

import java.text.SimpleDateFormat;

public class StoreReviewDto {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    private String customerName;
    private int rating;
    private String comment;
    private String dateLabel;

    public static StoreReviewDto from(StoreReview review) {
        StoreReviewDto dto = new StoreReviewDto();
        if (review.getCustomer() != null) {
            String last = review.getCustomer().getLastName();
            String initial = (last != null && !last.isEmpty()) ? " " + last.charAt(0) + "." : "";
            dto.customerName = review.getCustomer().getName() + initial;
        } else {
            dto.customerName = "Anonim";
        }
        dto.rating = review.getRating();
        dto.comment = review.getComment();
        if (review.getCreationTime() != null) {
            dto.dateLabel = DATE_FORMAT.format(review.getCreationTime());
        }
        return dto;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public String getDateLabel() {
        return dateLabel;
    }
}
