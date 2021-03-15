package main.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tags")
@Data
@NoArgsConstructor(force = true) @EqualsAndHashCode(callSuper = true, of = {"name"})
@ToString(callSuper = true, of = {"name"})
public class Tags extends AbstractEntity {
    // Имя тега
    @Column(nullable = false)
    private String name;

    // Посты, отмеченные конкретным тегом
    @NotNull
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "tags")

    private final Set<Posts> posts = new HashSet<>();

    public Tags(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Set<Posts> getPosts() {
        return posts;
    }
}
