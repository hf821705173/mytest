package com.itheima.health.service;

import com.itheima.health.Exception.MyException;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {
    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    PageResult<CheckGroup> findPage(QueryPageBean queryPageBean);

    void deleteById(int id)throws MyException;

    CheckGroup findById(int checkGroupId);

    List<Integer> findCheckItemIdByCheckGroupId(int checkGroupId);

    void update(CheckGroup checkGroup, Integer[] checkitemIds);

    List<CheckGroup> findAll();
}
