package io.mathlina.beautysalon.service.impl;

import io.mathlina.beautysalon.domain.Master;
import io.mathlina.beautysalon.domain.User;
import io.mathlina.beautysalon.exception.UserNotFound;
import io.mathlina.beautysalon.model.CommentModel;
import io.mathlina.beautysalon.repos.CommentRepository;
import io.mathlina.beautysalon.repos.UserRepo;
import io.mathlina.beautysalon.service.CommentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = CommentService.class)
@ExtendWith({SpringExtension.class})
class CommentServiceImplTest {

  @Mock
  CommentRepository commentRepository;

  @Mock
  UserRepo userRepo;

  @InjectMocks
  CommentServiceImpl commentService;


  @Test
  void getCommentsShouldReturnCommentsPage() {
    User client = new User();
    Master master = new Master();
    CommentModel comment = CommentModel.builder().clientId(client.getId()).masterId(master.getId()).build();
    PageImpl<CommentModel> expected = new PageImpl<>(List.of(comment));

    Mockito.when(commentRepository.findAllByMaster(master, Pageable.unpaged())).thenReturn(expected);

    Page<CommentModel> actual = commentService.getComments(master, Pageable.unpaged());

    assertEquals(expected, actual);

    Mockito.verify(commentRepository).findAllByMaster(master, Pageable.unpaged());
    Mockito.verifyNoMoreInteractions(commentRepository, userRepo);
  }

  @Test
  void getCommentShouldReturnUserComment() {
    String username = "Username";
    User client = User.builder().username(username).build();
    Master master = new Master();
    CommentModel expected = CommentModel.builder().clientId(client.getId()).masterId(master.getId()).build();

    Mockito.when(userRepo.findByUsername(username)).thenReturn(Optional.of(client));
    Mockito.when(commentRepository.findByMasterAndClient(master, client))
        .thenReturn(Optional.of(expected));

    CommentModel actual = commentService.getComment(master, client);

    assertEquals(expected, actual);

    Mockito.verify(userRepo).findByUsername(username);
    Mockito.verify(commentRepository).findByMasterAndClient(master, client);
    Mockito.verifyNoMoreInteractions(commentRepository, userRepo);
  }

  @Test
  void getCommentShouldThrowExceptionWhenUserNotFound() {
    String username = "Username";
    User client = User.builder().username(username).build();
    Master master = new Master();

    Mockito.when(userRepo.findByUsername(username)).thenReturn(Optional.empty());

    assertThrows(UserNotFound.class, () -> commentService.getComment(master, client));

    Mockito.verify(userRepo).findByUsername(username);
    Mockito.verifyNoMoreInteractions(commentRepository, userRepo);
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
    Mockito.verifyNoMoreInteractions(commentRepository, userRepo);
  }

  @Test
  void updateCommentShouldNotUpdateCommentIfItNotChanged() {
    String username = "Username";
    byte grade = 5;
    String commentText = "comment text";
    User client = User.builder().username(username).build();
    Master master = new Master();
    CommentModel comment = CommentModel.builder()
        .clientId(client.getId())
        .masterId(master.getId())
        .grade(grade)
        .text(commentText)
        .build();

    Mockito.when(userRepo.findByUsername(username)).thenReturn(Optional.of(client));
    Mockito.when(commentRepository.findByMasterAndClient(master, client))
        .thenReturn(Optional.of(comment));

    commentService.updateComment(client, master, grade, commentText);

    Mockito.verify(userRepo).findByUsername(username);
    Mockito.verify(commentRepository).findByMasterAndClient(master, client);
    Mockito.verifyNoMoreInteractions(commentRepository, userRepo);
  }

  @Test
  void updateCommentShouldUpdateComment() {
    String username = "Username";
    byte grade = 5;
    String commentText = "Comment text";
    User client = User.builder().username(username).build();
    Master master = new Master();
    CommentModel oldComment = CommentModel.builder().clientId(client.getId()).masterId(master.getId()).build();
    CommentModel newComment = CommentModel.builder()
        .clientId(client.getId())
        .masterId(master.getId())
        .grade(grade)
        .text(commentText)
        .build();

    Mockito.when(userRepo.findByUsername(username)).thenReturn(Optional.of(client));
    Mockito.when(commentRepository.findByMasterAndClient(master, client))
        .thenReturn(Optional.of(oldComment));

    commentService.updateComment(client, master, grade, commentText);

    Mockito.verify(userRepo).findByUsername(username);
    Mockito.verify(commentRepository).findByMasterAndClient(master, client);
    Mockito.verify(commentRepository).save(newComment);
    Mockito.verifyNoMoreInteractions(commentRepository, userRepo);
  }
}