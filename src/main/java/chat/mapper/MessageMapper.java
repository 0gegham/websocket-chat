package chat.mapper;

import chat.models.dto.MessageDto;
import chat.models.entities.Message;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    Message messageDtoToMessageEntity(MessageDto messageDTO);
    MessageDto messageEntityToMessageDto(Message messageEntity);
}
