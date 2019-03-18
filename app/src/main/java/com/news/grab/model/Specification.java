package com.news.grab.model;


import com.news.grab.network.NewsApi;

import java.util.Locale;

public class Specification {

    private String category;
    private String country = Locale.getDefault().getCountry().toLowerCase();
    private String language = null;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(NewsApi.Category category) {
        this.category = category.name();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
