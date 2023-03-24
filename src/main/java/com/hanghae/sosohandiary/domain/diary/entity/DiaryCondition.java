package com.hanghae.sosohandiary.domain.diary.entity;

public enum DiaryCondition {

    PUBLIC(Condition.PUBLIC),
    PRIVATE(Condition.PRIVATE);

    private final String condition;

    DiaryCondition(String condition) {
        this.condition = condition;
    }

    public static class Condition {
        public static String PUBLIC = "PUBLIC";
        public static String PRIVATE = "PRIVATE";
    }

}
