package com.example.common;

import com.example.common.group.GroupConst;
import com.example.common.group.GroupSaveRequest;
import com.example.common.group.GroupUpdateRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

public class GroupValidatorUnitTest {

    private Validator createValidator(){
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        return localValidatorFactoryBean;
    }

    @Test
    void saveValidatorCheck(){
        GroupSaveRequest groupSaveRequest = new GroupSaveRequest();
        groupSaveRequest.setName("");
        Validator validator = createValidator();
        Set<ConstraintViolation<GroupSaveRequest>> validate = validator.validate(groupSaveRequest);
        ConstraintViolation<GroupSaveRequest> next = validate.iterator().next();
        Assertions.assertEquals(next.getMessage(), GroupConst.BlankNameMsg);
    }

    @Test
    void updateValidatorCheck(){
        GroupUpdateRequest groupUpdateRequest = new GroupUpdateRequest();
        groupUpdateRequest.setName("");
        Validator validator = createValidator();
        Set<ConstraintViolation<GroupUpdateRequest>> validate = validator.validate(groupUpdateRequest);
        ConstraintViolation<GroupUpdateRequest> next = validate.iterator().next();
        Assertions.assertEquals(next.getMessage(), GroupConst.BlankNameMsg);
    }


}
