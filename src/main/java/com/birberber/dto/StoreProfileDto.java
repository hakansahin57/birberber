package com.birberber.dto;

import java.util.List;

public class StoreProfileDto {

    private double averageRating;
    private long reviewCount;
    private List<String> photoUrls;
    private List<StoreReviewDto> reviews;

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public long getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(long reviewCount) {
        this.reviewCount = reviewCount;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public List<StoreReviewDto> getReviews() {
        return reviews;
    }

    public void setReviews(List<StoreReviewDto> reviews) {
        this.reviews = reviews;
    }

    public int getAverageRatingRounded() {
        return (int) Math.round(averageRating);
    }
}
