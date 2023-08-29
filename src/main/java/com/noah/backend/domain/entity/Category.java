package com.noah.backend.domain.entity;

public enum Category {
    DIGITAL("디지털/가전"),
    FURNITURE("가구/인테리어"),
    CHILD("유아용/유아도서"),
    LIFE("생활/가공식품"),
    SPORTS("스포츠/레저"),
    WOMEN_ITEM("여성잡화"),
    WOMEN_CLOTHES("여성의류"),
    MAN_ITEM("남성패션/잡화"),
    GAME("게임/취미"),
    BEAUTY("뷰티/미용"),
    PET_ITEM("반려동물용품"),
    BOOK("도서/티켓/음반"),
    PLANT("식물"),
    ETC("기타 중고물품");

    private final String categoryName;

    Category(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return this.categoryName;
    }
}