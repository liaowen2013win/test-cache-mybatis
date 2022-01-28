package org.example.test.controller;

import org.example.test.model.entity.Organization;
import org.example.test.model.vo.UserInfoVO;
import org.example.test.response.ApiResponse;
import org.example.test.service.OrgService;
import org.example.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * todo 填写描述信息
 *
 * @author liaowen
 * @date 2022/1/28 10:30
 */
@RestController
@RequestMapping("/org")
public class OrgController {

    @Autowired
    private OrgService orgService;

    /**
     * 更新组织名称
     *
     * @param orgId
     * @return
     */
    @PutMapping("/updateOrgById")
    public ApiResponse<Organization> updateOrgById(Long orgId, String orgName) {
        Organization organization = orgService.updateOrgById(orgId, orgName);
        return ApiResponse.ofSuccess(organization);
    }
}
