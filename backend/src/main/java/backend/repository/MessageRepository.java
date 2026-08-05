package backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.Model.Message;


@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderIdAndRecipientIdOrRecipientIdAndSenderId(
            Long senderId, Long recipientId, Long recipientId2, Long senderId2);

    // Messages sent BY otherUserId TO currentUserId — used to mark a thread read.
    List<Message> findBySenderIdAndRecipientId(Long senderId, Long recipientId);
}
