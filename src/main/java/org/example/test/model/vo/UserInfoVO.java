package org.example.test.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.test.model.entity.UserEntity;

/**
 * todo 填写描述信息
 *
 * @author liaowen
 * @date 2022/1/27 18:32
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserInfoVO extends UserEntity {

    private String orgName;

}
