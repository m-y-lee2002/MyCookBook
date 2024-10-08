package com.backend.backend.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@Table(name="Dish")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Dish {

    @EmbeddedId
    @EqualsAndHashCode.Include
    @Column(name = "did", nullable = false, unique = true)
    private DishId did;

    @Column(name = "img", nullable = true)
    private String img;

    @Column(name = "foodType", nullable = false)
    private String foodType;

    @Column(name = "totalTime", nullable = true)
    private Integer totalTime;

    @Column(name = "calories", nullable = true)
    private Integer calories;

    @Column(name = "protein", nullable = true)
    private Integer protein;

    @Column(name = "carbs", nullable = true)
    private Integer carbs;

    @Column(name = "fibers", nullable = true)
    private Integer fibers;

    public Dish(String email, String name, String img, String foodType, Integer totalTime, Integer calories, Integer protein, Integer carbs, Integer fibers) {
        this.did = new DishId(email, name);
        this.img = img;
        String caseUniformity = foodType.toUpperCase();
        if(caseUniformity.equals("B")){
            this.foodType = "BREAKFAST";
        }else if(caseUniformity.equals("L")){
            this.foodType = "LUNCH";
        }else if(caseUniformity.equals("D1")) {
            this.foodType = "DINNER";
        }else if(caseUniformity.equals("D2")) {
            this.foodType = "DESSERT";
        }else if(caseUniformity.equals("D3")) {
            this.foodType = "DRINKS";
        }else{
            this.foodType = "N/A";
        }
        this.totalTime = totalTime;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fibers = fibers;
    }

    public void changeFoodType(String foodType){
        String caseUniformity = foodType.toUpperCase();
        if(caseUniformity.equals("B")){
            this.foodType = "BREAKFAST";
        }else if(caseUniformity.equals("L")){
            this.foodType = "LUNCH";
        }else if(caseUniformity.equals("D1")) {
            this.foodType = "DINNER";
        }else if(caseUniformity.equals("D2")) {
            this.foodType = "DESSERT";
        }else if(caseUniformity.equals("D3")) {
            this.foodType = "DRINKS";
        }else{
            this.foodType = "N/A";
        }
    }

}