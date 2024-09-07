package com.eatsleep.promotion.comment.application.createcommentusecase;

import com.eatsleep.promotion.common.UseCase;
import com.eatsleep.promotion.comment.domain.Comment;
import com.eatsleep.promotion.comment.infrastructure.inputports.CreateCommentInputPort;
import com.eatsleep.promotion.comment.infrastructure.outputadapters.db.CommentDbOutputAdapter;
import com.eatsleep.promotion.comment.infrastructure.outputadapters.restapi.CommentRestApiOutputAdapter;
import jakarta.transaction.Transactional;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class CreateCommentUseCase implements CreateCommentInputPort{
    
    private CommentDbOutputAdapter commentOutputAdapter;
    private CommentRestApiOutputAdapter commentRestApiOutputAdapter;

    @Autowired
    public CreateCommentUseCase(CommentDbOutputAdapter commentOutputAdapter, CommentRestApiOutputAdapter commentRestApiOutputAdapter) {
        this.commentOutputAdapter = commentOutputAdapter;
        this.commentRestApiOutputAdapter = commentRestApiOutputAdapter;
    }

    @Override
    public Comment createComment(CreateCommentRequest commentRequest, String type) {
        // Validar la información de la habitación
        validateCreateCommentRequest(commentRequest);
        
        // Validar el tipo
        validateType(type);
        
        // Validar que existe el usuario
        validateClient(commentRequest.getIdUser());
        
        // Validar el producto
        validateProduct(commentRequest.getIdProduct(), type);
        
        // Lógica para crear el comentario
        Comment comment = Comment.builder()
                .dateComment(commentRequest.getDateComment())
                .description(commentRequest.getDescription())
                .description(type)
                .rate(commentRequest.getRate())
                .idProduct(UUID.fromString(commentRequest.getIdProduct()))
                .user(UUID.fromString(commentRequest.getIdUser()))
                .build();

        // Persistir la habitación en la base de datos usando el Output Adapter
        Comment savedComment = commentOutputAdapter.createComment(comment);
        
        // Crear y devolver la respuesta
        return savedComment;
    }
    
    public void validateCreateCommentRequest(CreateCommentRequest request) {
        if (Objects.isNull(request.getDateComment())) {
            throw new IllegalArgumentException("La fecha del comentario no puede ser nula");
        }
        if (Objects.isNull(request.getRate()) || request.getRate() < 1 || request.getRate() > 5) {
            throw new IllegalArgumentException("La calificacion debe se mayor a 1 y menor a 5");
        }
        if (Objects.isNull(request.getDescription()) || request.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripcion no puede estar en blanco");
        }
        if (request.getDescription().length() > 200) {
            throw new IllegalArgumentException("La descripcion no debe superar los 200 caracteres");
        }
        if (Objects.isNull(request.getIdProduct()) || request.getIdProduct().trim().isEmpty()) {
            throw new IllegalArgumentException("El cuarto a calificar debe ser ingresado");
        }
        if (Objects.isNull(request.getIdUser()) || request.getIdUser().trim().isEmpty()) {
            throw new IllegalArgumentException("El usuario calificando debe ser ingresado");
        }
        
        
    }
    
    private void validateType(String type){
        if (Objects.isNull(type)) {
            throw new IllegalArgumentException("El tipo del comentario no puede ser nula");
        }
        String regex = "^(hotel|room|dish|restaurant)$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(type);
        if(!matcher.matches()){
            throw new IllegalArgumentException("El tipo del comentario tiene que ser: hotel, room, dish o restaurant");
        }
    }
    
    private void validateClient(String idClient){
        if(!commentRestApiOutputAdapter.checkClientExistsOutputPort(idClient)){
            throw new IllegalArgumentException("El identificador"+ idClient +"no corresponde a un cliente");
        }
    }
    
    private void validateProduct(String idProduct, String type){
        if(type.contains("hotel")){
            if(!commentRestApiOutputAdapter.checkHotelExistsOutputPort(type)){
                throw new IllegalArgumentException("El identificador"+ idProduct +"no corresponde a un hotel");
            }
        }else if(type.contains("room")){
            if(!commentRestApiOutputAdapter.checkRoomExistsOutputPort(type)){
                throw new IllegalArgumentException("El identificador"+ idProduct +"no corresponde a un cuarto");
            }
        }else if(type.contains("restaurant")){
            if(!commentRestApiOutputAdapter.checkRestaurantExistsOutputPort(type)){
                throw new IllegalArgumentException("El identificador"+ idProduct +"no corresponde a un restaurante");
            }
        }else if(type.contains("dish")){
            if(!commentRestApiOutputAdapter.checkDishExistsOutputPort(type)){
                throw new IllegalArgumentException("El identificador"+ idProduct +"no corresponde a un platillo");
            }
        }
        else{
            throw new IllegalArgumentException("El tipo del comentario tiene que ser: hotel, room, dish o restaurant. Por favor cambiarlo");
        }
    }

}
