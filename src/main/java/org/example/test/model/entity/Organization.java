package org.example.test.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.test.annotations.CacheField;
import org.example.test.annotations.CacheKey;

import java.io.Serializable;

/**
 * todo 填写描述信息
 *
 * @author liaowen
 * @date 2022/1/27 18:37
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@CacheKey(name = "orgId")
@CacheField(name = "orgId")
public class Organization extends Model<Organization> implements Serializable {

    private static final long serialVersionUID = -7048106921649081203L;

    @TableId(type = IdType.AUTO)
    private Long orgId;

    private String orgName;

}
