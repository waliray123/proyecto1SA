package com.eatsleep.promotion.comment.application.retrievecommentusecase;

import com.eatsleep.promotion.common.UseCase;
import com.eatsleep.promotion.comment.domain.Comment;
import com.eatsleep.promotion.comment.infrastructure.inputports.RetrieveCommentInputPort;
import com.eatsleep.promotion.comment.infrastructure.outputadapters.db.CommentDbOutputAdapter;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class RetrieveCommentUseCase implements RetrieveCommentInputPort{
    
    private CommentDbOutputAdapter commentOutputAdapter;

    @Autowired
    public RetrieveCommentUseCase(CommentDbOutputAdapter commentOutputAdapter) {
        this.commentOutputAdapter = commentOutputAdapter;
    }

    @Override
    public Optional<Comment> getCommentById(String id) {
        // Recuperar el hotel por ID
        Optional<Comment> commentOptional = commentOutputAdapter.getCommentById(id);

        // Convertir la entidad de base de datos a entidad de dominio y luego a respuesta
        return commentOptional;
    }

    @Override
    public List<Comment> getAllComments() {
        // Recuperar todos los hoteles
        List<Comment> comments = commentOutputAdapter.getAllComments();
        
        // Convertir la lista de entidades a respuestas
        return comments;
    }

    @Override
    public List<Comment> getCommentsByIdUser(String idUser) {
        List<Comment> comments = commentOutputAdapter.getCommentByIdUser(idUser);
        return comments;
    }

}
