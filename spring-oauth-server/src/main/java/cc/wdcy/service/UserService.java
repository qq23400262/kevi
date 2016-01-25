package cc.wdcy.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import cc.wdcy.domain.dto.UserJsonDto;
import cc.wdcy.domain.user.User;

/**
 * @author Shengzhao Li
 */
public interface UserService extends UserDetailsService {

    UserJsonDto loadCurrentUserJsonDto();
    
    User loadCurrentUser();
    
    void addUser(User user);
  
    void updateUser(User user);
    
    void addPrivilege(int userId, String privilege);
}