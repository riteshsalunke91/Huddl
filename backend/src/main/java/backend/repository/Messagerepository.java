package backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.Model.Message;

public interface Messagerepository extends JpaRepository<Message, Long> {

    List<Message> findBySenderId(Long senderId);
    List<Message> findByReceiverId(Long receiverId);
    List<Message> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
}
