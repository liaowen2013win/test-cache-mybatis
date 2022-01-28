package org.example.test.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * todo 填写描述信息
 *
 * @author liaowen
 * @date 2022/1/27 18:25
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserEntity extends Model<UserEntity> implements Serializable {

    private static final long serialVersionUID = 6184875766403113480L;

    @TableId(type = IdType.AUTO)
    private Long userId;

    private String userName;

    private Long orgId;
}
