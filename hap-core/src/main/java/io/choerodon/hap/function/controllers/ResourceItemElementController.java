package io.choerodon.hap.function.controllers;

import io.choerodon.hap.function.dto.ResourceItemElement;
import io.choerodon.hap.function.service.IResourceItemElementService;
import io.choerodon.base.annotation.Permission;
import io.choerodon.base.enums.ResourceType;
import io.choerodon.web.controller.BaseController;
import io.choerodon.web.core.IRequest;
import io.choerodon.web.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 对权限组件元素的操作.
 *
 * @author qiang.zeng@hand-china.com
 */
@RestController
@RequestMapping(value = {"/sys/resourceItemElement", "/api/sys/resourceItemElement"})
public class ResourceItemElementController extends BaseController {

    @Autowired
    private IResourceItemElementService elementService;

    @Permission(type = ResourceType.SITE)
    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData query(HttpServletRequest request, ResourceItemElement element) {
        IRequest requestContext = createRequestContext(request);
        return new ResponseData(elementService.selectByResourceItemId(requestContext, element));
    }

    @Permission(type = ResourceType.SITE)
    @PostMapping(value = "/submit")
    public ResponseData submit(HttpServletRequest request, @RequestBody List<ResourceItemElement> elements, BindingResult result) {
        getValidator().validate(elements, result);
        if (result.hasErrors()) {
            ResponseData responseData = new ResponseData(false);
            responseData.setMessage(getErrorMessage(result, request));
            return responseData;
        }
        return new ResponseData(elementService.batchUpdate(elements));
    }

    @Permission(type = ResourceType.SITE)
    @PostMapping(value = "/remove")
    public ResponseData remove(HttpServletRequest request, @RequestBody List<ResourceItemElement> elements) {
        IRequest requestContext = createRequestContext(request);
        elementService.batchDelete(requestContext, elements);
        return new ResponseData();
    }
}