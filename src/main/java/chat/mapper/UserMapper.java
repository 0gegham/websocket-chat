package chat.mapper;

import chat.models.User;
import chat.models.dto.UserDTO;
import chat.models.entities.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = MessageMapper.class)
public interface UserMapper {
    User userEntityToUserDetails(UserEntity userEntity);
    UserEntity userDtoToUserEntity(UserDTO userDTO);
}
