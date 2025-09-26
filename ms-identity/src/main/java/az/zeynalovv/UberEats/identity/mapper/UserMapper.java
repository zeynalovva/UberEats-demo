package az.zeynalovv.UberEats.identity.mapper;

import az.zeynalovv.UberEats.identity.dto.UserDto;
import az.zeynalovv.UberEats.identity.dto.request.UserRegisterRequest;
import az.zeynalovv.UberEats.identity.entity.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface UserMapper extends EntityMapper<UserDto, User> {

  @Mapping(target = "userRole", expression = "java(az.zeynalovv.UberEats.identity.entity.enums.UserRole.USER)")
  @Mapping(target = "userStatus", expression = "java(az.zeynalovv.UberEats.identity.entity.enums.UserStatus.ACTIVE)")
  User toEntity(UserRegisterRequest userRegisterRequest);
}
