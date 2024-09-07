package com.eatsleep.promotion.comment.infrastructure.outputadapters.db;

import com.eatsleep.promotion.comment.domain.Comment;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comment")
@Getter
@Setter
@NoArgsConstructor
public class CommentDbEntity {
    @Id
    @Column(name = "id_comment", columnDefinition = "CHAR(36)")
    private String idComment;

    @Column(name = "date_comment")
    private LocalDate dateComment;

    @Column(name = "description", length = 200)
    private String description;
    
    @Column(name = "type", length = 50)
    private String type;

    @Column(name = "rate")
    private Double rate;

    @Column(name = "id_product", nullable = false, columnDefinition = "CHAR(36)")
    private String idProduct;

    @Column(name = "user_id_user", nullable = false, columnDefinition = "CHAR(36)")
    private String user;

    public Comment toDomainModel() {
        return Comment.builder()
                .idComment(UUID.fromString(this.getIdComment()))
                .dateComment(this.getDateComment())
                .description(this.getDescription())
                .type(this.getType())
                .rate(this.getRate())
                .idProduct(UUID.fromString(this.idProduct))
                .user(UUID.fromString(this.getUser()))  // UUID se maneja como String en la entidad
                .build();
    }

    public static CommentDbEntity from(Comment comment) {
        CommentDbEntity commentDbEntity = new CommentDbEntity();
        commentDbEntity.setIdComment(comment.getIdComment() != null ? comment.getIdComment().toString() : UUID.randomUUID().toString());
        commentDbEntity.setDateComment(comment.getDateComment());
        commentDbEntity.setDescription(comment.getDescription());
        commentDbEntity.setType(comment.getType());
        commentDbEntity.setRate(comment.getRate());
        commentDbEntity.setIdProduct(comment.getIdProduct().toString());
        commentDbEntity.setUser(comment.getUser().toString());

        return commentDbEntity;
    }
}
