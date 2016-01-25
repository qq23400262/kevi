package cc.wdcy.web.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import cc.wdcy.domain.user.User;

/**
 * @author Shengzhao Li
 */
@Component
public class UserValidator implements Validator {
   
    @Override
    public boolean supports(Class<?> clazz) {
        return UserValidator.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        //validateUsernameExsit(clientDetailsDto, errors);
        validateValues(user, errors);
    }

    private void validateValues(User user, Errors errors) {
        final String grantTypes = user.username();
        if (StringUtils.isEmpty(grantTypes)) {
            errors.rejectValue("authorizedGrantTypes", null, "grant_type(s) is required");
            return;
        }

        if ("refresh_token".equalsIgnoreCase(grantTypes)) {
            errors.rejectValue("authorizedGrantTypes", null, "grant_type(s) 不能只是[refresh_token]");
        }
    }

}