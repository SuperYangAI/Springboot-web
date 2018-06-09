package com.actec.ms.controller;

import com.actec.ms.service.AbstractService;
import com.actec.ms.vo.BaseEntity;
import com.actec.ms.vo.Page;
import com.actec.ms.vo.RestResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by donglin.wang on 2017/4/21.
 * @param <T>
 */
@RestController
@RequestMapping(value = "/",
                consumes = "application/json;charset=UTF-8",
                produces = "application/json;charset=UTF-8"
)
public  class AbstractController<T> {

    protected AbstractService service;

    protected void setService(AbstractService service) {
        this.service = service;
    }

    @PostMapping
    public RestResult add(HttpServletRequest request, @RequestBody T pojo) {
        return service.add(request, pojo);
    }

    @PutMapping
    public RestResult update(HttpServletRequest request, @RequestBody T pojo) {
        return service.update(request, pojo);
    }

    @DeleteMapping("/{id}")
    public RestResult delete(HttpServletRequest request, @PathVariable("id") int id) {
        return service.delete(request, id);
    }

    @GetMapping("/{id}")
    public RestResult<T> findById(@PathVariable("id") int id) {
        return service.findById(id);
    }

    @PostMapping("/page/{pageNum}/{pageSize}")
    public RestResult<Page<T>> page(@PathVariable(name = "pageNum") int pageNum,
                                    @PathVariable(name = "pageSize") int pageSize,
                                    @RequestBody Map pojo) {
        pojo.put("status", BaseEntity.STATUS_OK);
        RestResult<Page<T>> restResult = null;
        try {
            restResult =  service.page(pageNum, pageSize, pojo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restResult;
    }
}
