package com.actec.ms.controller;

import com.actec.ms.service.AbstractService;
import com.actec.ms.vo.BaseEntity;
import com.actec.ms.vo.Page;
import com.actec.ms.vo.RestResult;
import org.springframework.web.bind.annotation.*;

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
    public RestResult add(@RequestBody T pojo) {
        return service.add(pojo);
    }

    @PutMapping
    public RestResult update(@RequestBody T pojo) {
        return service.update(pojo);
    }

    @DeleteMapping("/{id}")
    public RestResult delete(@PathVariable("id") int id) {
        return service.delete(id);
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
