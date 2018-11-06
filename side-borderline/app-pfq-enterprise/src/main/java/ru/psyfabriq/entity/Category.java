package ru.psyfabriq.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class Category extends AbstractEntity {

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(name = "article_categories", joinColumns = {@JoinColumn(name = "category_id")}, inverseJoinColumns = {
            @JoinColumn(name = "article_id")})
    private List<Article> article;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "menuname", columnDefinition = "VARCHAR(40)")
    private String menuname;

    public Category() {
        super();
        this.article = new ArrayList<>();
    }

    public List<Article> getArticle() {
        return article;
    }

    public void setArticle(List<Article> article) {
        this.article = article;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMenuname() {
        return menuname;
    }

    public void setMenuname(String menuname) {
        this.menuname = menuname;
    }


}
