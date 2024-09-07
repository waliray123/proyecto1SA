package com.eatsleep.promotion.comment.infrastructure.outputadapters.db;

import com.eatsleep.promotion.common.OutputAdapter;
import com.eatsleep.promotion.comment.infrastructure.outputports.db.CreateCommentOutputPort;
import com.eatsleep.promotion.comment.infrastructure.outputports.db.RetrieveCommentOutputPort;
import com.eatsleep.promotion.comment.infrastructure.outputports.db.UpdateCommentOutputPort;
import com.eatsleep.promotion.comment.domain.Comment;
import com.eatsleep.promotion.comment.infrastructure.outputadapters.db.CommentDbEntity;
import com.eatsleep.promotion.comment.infrastructure.outputports.db.TopRatedCommentOutputPort;
import com.eatsleep.promotion.promotion.application.getrelatedpromotiontocreateusecase.TopRatedCommentResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@OutputAdapter
public class CommentDbOutputAdapter implements CreateCommentOutputPort,UpdateCommentOutputPort,RetrieveCommentOutputPort, TopRatedCommentOutputPort{
    
    private CommentDbEntityRepository commentDbEntityRepository;

    public CommentDbOutputAdapter(CommentDbEntityRepository commentDbEntityRepository) {
        this.commentDbEntityRepository = commentDbEntityRepository;
    }

    @Override
    public Optional<Comment> updateComment(String id, Comment updatedComment) {

        if (commentDbEntityRepository.existsById(id)) {
            CommentDbEntity commentDbEntity = CommentDbEntity.from(updatedComment);

            CommentDbEntity savedEntity = commentDbEntityRepository.save(commentDbEntity);
            return Optional.of(savedEntity.toDomainModel());
        }

        return Optional.empty();
    }


    @Override
    public Comment createComment(Comment comment) {
        CommentDbEntity commentEntity = CommentDbEntity.from(comment);
        commentEntity = commentDbEntityRepository.save(commentEntity);
        return commentEntity.toDomainModel();
    }

    @Override
    public Optional<Comment> getCommentById(String id) {
        Optional<CommentDbEntity> commentDbEntityOptional = commentDbEntityRepository.findById(id);

        // Mapeo de CommentDbEntity a Comment
        return commentDbEntityOptional.map(CommentDbEntity::toDomainModel);
    }
    
    @Override
    public List<Comment> getCommentByIdUser(String idUser) {
        List<CommentDbEntity> commentsDbEntity = commentDbEntityRepository.findByUser(idUser);

        // Mapeo de List<CommentDbEntity> a List<Comment>
        return commentsDbEntity.stream()
                .map(CommentDbEntity::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Comment> getAllComments() {
        // Obtener todas las entidades CommentDbEntity
        List<CommentDbEntity> commentDbEntities = commentDbEntityRepository.findAll();

        // Convertir cada CommentDbEntity a Comment utilizando el método toDomainModel
        return commentDbEntities.stream()
                .map(CommentDbEntity::toDomainModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<TopRatedCommentResponse> getTopRatedRoomComments() {
        List<Object[]> results = this.commentDbEntityRepository.findTopRatedRoomComments();
        List<TopRatedCommentResponse> responseList = new ArrayList<>();
        
        for (Object[] result : results) {
            String idProduct = (String) result[0];
            double maxRate = (Double) result[1];
            responseList.add(new TopRatedCommentResponse(idProduct, maxRate));
        }
        
        return responseList;
    }

    @Override
    public List<TopRatedCommentResponse> getTopRatedDishComments() {
        List<Object[]> results = this.commentDbEntityRepository.findTopRatedRoomComments();
        List<TopRatedCommentResponse> responseList = new ArrayList<>();
        
        for (Object[] result : results) {
            String idProduct = (String) result[0];
            double maxRate = (Double) result[1];
            responseList.add(new TopRatedCommentResponse(idProduct, maxRate));
        }
        
        return responseList;
    }

}
