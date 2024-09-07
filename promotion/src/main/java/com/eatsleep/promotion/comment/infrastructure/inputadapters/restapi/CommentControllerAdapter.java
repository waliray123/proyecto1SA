package com.eatsleep.promotion.comment.infrastructure.inputadapters.restapi;

import com.eatsleep.promotion.common.WebAdapter;
import com.eatsleep.promotion.comment.application.createcommentusecase.CreateCommentRequest;
import com.eatsleep.promotion.comment.infrastructure.inputadapters.restapi.response.CreateCommentResponse;
import com.eatsleep.promotion.comment.infrastructure.inputadapters.restapi.response.RetrieveCommentResponse;
import com.eatsleep.promotion.comment.application.updatecommentusecase.UpdateCommentRequest;
import com.eatsleep.promotion.comment.domain.Comment;
import com.eatsleep.promotion.comment.infrastructure.inputadapters.restapi.response.UpdateCommentResponse;
import com.eatsleep.promotion.comment.infrastructure.inputports.CreateCommentInputPort;
import com.eatsleep.promotion.comment.infrastructure.inputports.RetrieveCommentInputPort;
import com.eatsleep.promotion.comment.infrastructure.inputports.UpdateCommentInputPort;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/comments")
@WebAdapter
public class CommentControllerAdapter {

    private CreateCommentInputPort createCommentInputPort;
    private UpdateCommentInputPort updateCommentInputPort;
    private RetrieveCommentInputPort retrieveCommentInputPort;

    @Autowired
    public CommentControllerAdapter(CreateCommentInputPort createCommentInputPort, UpdateCommentInputPort updateCommentInputPort, RetrieveCommentInputPort retrieveCommentInputPort) {
        this.createCommentInputPort = createCommentInputPort;
        this.updateCommentInputPort = updateCommentInputPort;
        this.retrieveCommentInputPort = retrieveCommentInputPort;
    }
    
    
    @PostMapping("/save/{type}")
    public ResponseEntity<CreateCommentResponse> createCommentHotel(@RequestBody CreateCommentRequest comment, @PathVariable String type) {
        Comment createdComment = createCommentInputPort.createComment(comment,type);
        return new ResponseEntity<>(new CreateCommentResponse(createdComment), HttpStatus.CREATED);
    }
    
    @GetMapping("/getcomment/{id}")
    public ResponseEntity<RetrieveCommentResponse> getCommentById(@PathVariable String id) {
        Optional<Comment> commentOptional = retrieveCommentInputPort.getCommentById(id);
        return commentOptional.map(comment -> new ResponseEntity<>(new RetrieveCommentResponse(comment), HttpStatus.OK))
                            .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/all")
    public ResponseEntity<List<RetrieveCommentResponse>> getAllComments() {
        List<Comment> comments = retrieveCommentInputPort.getAllComments();
        List<RetrieveCommentResponse> commentsResponse = comments.stream()
                .map(comment -> new RetrieveCommentResponse(comment))
                .collect(Collectors.toList());
        return new ResponseEntity<>(commentsResponse, HttpStatus.OK);
    }
    
    @PutMapping("/update/{type}/{id}")
    public ResponseEntity<UpdateCommentResponse> updateCommentHotel(@PathVariable String id,@PathVariable String type, @RequestBody UpdateCommentRequest updatedCommentDetails) {
        return updateCommentInputPort.updateComment(id, updatedCommentDetails,type)
                .map(task -> new ResponseEntity<>(new UpdateCommentResponse(task), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
