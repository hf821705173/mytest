package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.Exception.MyException;
import com.itheima.health.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CheckGroupDao {
    void add(CheckGroup checkGroup);

    void addCheckGroupCheckItem(@Param("checkGroupId") Integer checkGroupId, @Param("checkitemId") Integer checkitemIds);

    Page<CheckGroup> findPage(String queryString);

    int findSetmealCountByCheckGroupId(int id);

    CheckGroup findById(int checkGroupId);

    List<Integer> findCheckItemIdByCheckGroupId(int checkGroupId);

    void update(CheckGroup checkGroup);

    void deleteCheckGroupCheckItem(Integer id);

    void deleteById(int id) ;
}
