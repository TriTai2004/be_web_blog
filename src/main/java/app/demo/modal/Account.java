package app.demo.modal;

import java.util.List;
import java.util.UUID;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data 
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String fullname;
    private String avatar;
    private String email;
    private String password;
    private String role;

    @OneToMany(mappedBy = "account")
    private List<Category> categories;

    @OneToMany(mappedBy = "author")
    private List<Like> likes;

    @OneToMany(mappedBy = "author")
    private List<Article> articles;

    @OneToMany(mappedBy = "author")
    private List<Comment> comments;

    @OneToMany(mappedBy = "author")
    private List<LikeComment> likeComments;

}
