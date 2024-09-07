package com.eatsleep.promotion.promotion.application.getrelatedpromotiontocreateusecase;

import com.eatsleep.promotion.comment.infrastructure.outputadapters.db.CommentDbOutputAdapter;
import com.eatsleep.promotion.comment.infrastructure.outputadapters.restapi.CommentRestApiOutputAdapter;
import com.eatsleep.promotion.common.UseCase;
import com.eatsleep.promotion.promotion.infrastructure.inputadapters.restapi.response.RelatedPromotionToCreateResponse;
import com.eatsleep.promotion.promotion.infrastructure.inputports.GetRelatedPromotionsToCreateInputPort;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@UseCase
@Transactional
public class GetRelatedPromotionsToCreateUseCase implements GetRelatedPromotionsToCreateInputPort{
    
    private CommentDbOutputAdapter commentOutputAdapter;
    private CommentRestApiOutputAdapter commentRestApiOutputAdapter;

    @Autowired
    public GetRelatedPromotionsToCreateUseCase(CommentDbOutputAdapter commentOutputAdapter, CommentRestApiOutputAdapter commentRestApiOutputAdapter) {
        this.commentOutputAdapter = commentOutputAdapter;
        this.commentRestApiOutputAdapter = commentRestApiOutputAdapter;
    }

    @Override
    public List<RelatedPromotionToCreateResponse> getRelatedPromotionsToCreateInputPort() {
        // Crear la lista a devolver
        List<RelatedPromotionToCreateResponse> relatedPromotions = new ArrayList<>();
        
        // Porcentaje de descuento por habitacion mas popular (comentario)
        // Traer las 3 habitaciones mas populares y los hoteles
        List<TopRatedCommentResponse> topRooms = this.commentOutputAdapter.getTopRatedRoomComments();
        
        List<TopRatedCommentResponse> top5Rooms = topRooms.size() > 5 ? topRooms.subList(0, 5) : topRooms;
        
        for (TopRatedCommentResponse top5Room : top5Rooms) {
            RoomPromotionResponse roomResponse = this.commentRestApiOutputAdapter.getRoomInformation(top5Room.getIdProduct());
            String nameProduct = "Cuarto No." + roomResponse.getNumber() + " , con precio" + roomResponse.getUnitPrice() + " ,del hotel con id" + roomResponse.getIdHotel();
            RelatedPromotionToCreateResponse relatedPromotion = 
                    new RelatedPromotionToCreateResponse(
                            "room"
                            ,"Recomendado: disminuir un porcentaje del valor del cuarto"
                            , "all"
                            , roomResponse.getId()
                            , nameProduct
                            , String.valueOf(top5Room.getMaxRate()));
            relatedPromotions.add(relatedPromotion);
        }
        
        // Porcentaje de descuento por platillo mas popular (comentario)
        // Traer los 3 platillos mas populares y sus restaurantes
        List<TopRatedCommentResponse> topDish = this.commentOutputAdapter.getTopRatedDishComments();
        
        List<TopRatedCommentResponse> top5Dishes = topDish.size() > 5 ? topDish.subList(0, 5) : topDish;
        
        for (TopRatedCommentResponse top5Dish : top5Dishes) {
            DishPromotionResponse dishResponse = this.commentRestApiOutputAdapter.getDishInformation(top5Dish.getIdProduct());
            String nameProduct = "Platillo con nombre: " 
                    + dishResponse.getName() 
                    + "del restaurante con id: " 
                    + dishResponse.getIdRestaurant()
                    + ", con descripcion: " 
                    + dishResponse.getDescription();
            RelatedPromotionToCreateResponse relatedPromotion = 
                    new RelatedPromotionToCreateResponse(
                            "dish"
                            ,"Recomendado: disminuir un porcentaje del valor del platillo"
                            , "all"
                            , dishResponse.getId()
                            , nameProduct
                            , String.valueOf(top5Dish.getMaxRate()));
            relatedPromotions.add(relatedPromotion);
        }
        
        // Porcentaje de descuento por cliente con mas gasto
        // Traer los 3 clientes con mas gasto
        List<TopSpendingClientResponse> top5clients = this.commentRestApiOutputAdapter.findToSpendingClients();
        for (TopSpendingClientResponse topclient : top5clients) {
            String nameProduct = "El cliente con id:" + topclient.getUserId() + " puede ser merecedor de un descuento, porque a gastado " + topclient.getTotalSpent() ;
            RelatedPromotionToCreateResponse relatedPromotion = 
                    new RelatedPromotionToCreateResponse(
                            "client"
                            ,"Recomendado: Dar un descuento al cliente en especifico"
                            , "top5"
                            , topclient.getUserId()
                            , nameProduct
                            , String.valueOf(topclient.getTotalSpent()));
            relatedPromotions.add(relatedPromotion);
        }
        
        return relatedPromotions;
    }
    

}
