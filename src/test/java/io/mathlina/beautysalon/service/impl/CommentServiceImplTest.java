package io.mathlina.beautysalon.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.mathlina.beautysalon.domain.Comment;
import io.mathlina.beautysalon.domain.Master;
import io.mathlina.beautysalon.domain.User;
import io.mathlina.beautysalon.exception.UserNotFound;
import io.mathlina.beautysalon.repos.CommentRepo;
import io.mathlina.beautysalon.repos.UserRepo;
import io.mathlina.beautysalon.service.CommentService;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = CommentService.class)
@ExtendWith({SpringExtension.class})
class CommentServiceImplTest {

  @Mock
  CommentRepo commentRepo;

  @Mock
  UserRepo userRepo;

  @InjectMocks
  CommentServiceImpl commentService;


  @Test
  void getCommentsShouldReturnCommentsPage() {
    User client = new User();
    Master master = new Master();
    Comment comment = Comment.builder().client(client).master(master).build();
    PageImpl<Comment> expected = new PageImpl<>(List.of(comment));

    Mockito.when(commentRepo.findAllByMaster(master, Pageable.unpaged())).thenReturn(expected);

    Page<Comment> actual = commentService.getComments(master, Pageable.unpaged());

    assertEquals(expected, actual);

    Mockito.verify(commentRepo).findAllByMaster(master, Pageable.unpaged());
    Mockito.verifyNoMoreInteractions(commentRepo, userRepo);
  }

  @Test
  void getCommentShouldReturnUserComment() {
    String username = "Username";
    User client = User.builder().username(username).build();
    Master master = new Master();
    Comment expected = Comment.builder().client(client).master(master).build();

    Mockito.when(userRepo.findByUsername(username)).thenReturn(Optional.of(client));
    Mockito.when(commentRepo.findByMasterAndClient(master, client))
        .thenReturn(Optional.of(expected));

    Comment actual = commentService.getComment(master, client);

    assertEquals(expected, actual);

    Mockito.verify(userRepo).findByUsername(username);
    Mockito.verify(commentRepo).findByMasterAndClient(master, client);
    Mockito.verifyNoMoreInteractions(commentRepo, userRepo);
  }

  @Test
  void getCommentShouldThrowExceptionWhenUserNotFound() {
    String username = "Username";
    User client = User.builder().username(username).build();
    Master master = new Master();

    Mockito.when(userRepo.findByUsername(username)).thenReturn(Optional.empty());

    assertThrows(UserNotFound.class, () -> commentService.getComment(master, client));

    Mockito.verify(userRepo).findByUsername(username);
    Mockito.verifyNoMoreInteractions(commentRepo, userRepo);
  }

  @Test
  void updateCommentShouldThrowExceptionWhenUserNotFound() {
    String username = "Username";
    byte grade = 5;
    String commentText = "comment text";
    User client = User.builder().username(username).build();
    Master master = new Master();

    Mockito.when(userRepo.findByUsername(username)).thenReturn(Optional.empty());

    assertThrows(UserNotFound.class,
        () -> commentService.updateComment(client, master, grade, commentText));

    Mockito.verify(userRepo).findByUsername(username);
    Mockito.verifyNoMoreInteractions(commentRepo, userRepo);
  }

  @Test
  void updateCommentShouldNotUpdateCommentIfItNotChanged() {
    String username = "Username";
    byte grade = 5;
    String commentText = "comment text";
    User client = User.builder().username(username).build();
    Master master = new Master();
    Comment comment = Comment.builder()
        .client(client)
        .master(master)
        .grade(grade)
        .text(commentText)
        .build();

    Mockito.when(userRepo.findByUsername(username)).thenReturn(Optional.of(client));
    Mockito.when(commentRepo.findByMasterAndClient(master, client))
        .thenReturn(Optional.of(comment));

    commentService.updateComment(client, master, grade, commentText);

    Mockito.verify(userRepo).findByUsername(username);
    Mockito.verify(commentRepo).findByMasterAndClient(master, client);
    Mockito.verifyNoMoreInteractions(commentRepo, userRepo);
  }

  @Test
  void updateCommentShouldUpdateComment() {
    String username = "Username";
    byte grade = 5;
    String commentText = "Comment text";
    User client = User.builder().username(username).build();
    Master master = new Master();
    Comment oldComment = Comment.builder().client(client).master(master).build();
    Comment newComment = Comment.builder()
        .client(client)
        .master(master)
        .grade(grade)
        .text(commentText)
        .build();

    Mockito.when(userRepo.findByUsername(username)).thenReturn(Optional.of(client));
    Mockito.when(commentRepo.findByMasterAndClient(master, client))
        .thenReturn(Optional.of(oldComment));

    commentService.updateComment(client, master, grade, commentText);

    Mockito.verify(userRepo).findByUsername(username);
    Mockito.verify(commentRepo).findByMasterAndClient(master, client);
    Mockito.verify(commentRepo).save(newComment);
    Mockito.verifyNoMoreInteractions(commentRepo, userRepo);
  }
}