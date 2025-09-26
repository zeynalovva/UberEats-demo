package az.zeynalovv.UberEats.mapper;

import az.zeynalovv.UberEats.dto.UserDto;
import az.zeynalovv.UberEats.dto.request.UserRegisterRequest;
import az.zeynalovv.UberEats.entity.User;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = org.mapstruct.ReportingPolicy.IGNORE)
public interface UserMapper extends EntityMapper<UserDto, User> {

  @Mapping(target = "role", expression = "java(az.zeynalovv.UberEats.entity.enums.UserRole.USER)")
  @Mapping(target = "status", expression = "java(az.zeynalovv.UberEats.entity.enums.UserStatus.ACTIVE)")
  User toEntity(UserRegisterRequest userRegisterRequest);
}
