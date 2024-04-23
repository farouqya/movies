package com.example.javaapplication.movies.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "actors")
public class Actor extends AbstractAuditable {

    private String name;
    private int age;
    private String gender;
    private String nationality;
    @Id
    @GeneratedValue(generator = "actors_generator")
    @SequenceGenerator(name = "actors_generator", sequenceName = "actors_actors_id_seq", allocationSize = 1)
    private Long id;

    private Long movieTableId;

    public Actor(String name, int age, String gender, String nationality) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.nationality = nationality;
    }

    public Actor() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMovieTableId() {
        return movieTableId;
    }

    public void setMovieTableId(Long movieTableId) {
        this.movieTableId = movieTableId;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", nationality='" + nationality + '\'' +
                '}';
    }
}
