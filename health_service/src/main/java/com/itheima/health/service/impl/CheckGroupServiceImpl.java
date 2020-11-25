package com.itheima.health.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.Exception.MyException;
import com.itheima.health.dao.CheckGroupDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckGroup;
import com.itheima.health.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;
    @Override
    @Transactional
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.add(checkGroup);
        Integer checkGroupId = checkGroup.getId();
        if (null != checkitemIds) {
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.addCheckGroupCheckItem(checkGroupId,checkitemId);
            }
        }
    }

    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {
         PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
                 if(!StringUtils.isEmpty(queryPageBean.getQueryString())){
                     queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
                 }
                 Page<CheckGroup> page = checkGroupDao.findPage(queryPageBean.getQueryString());
                 long total = page.getTotal();
                 List<CheckGroup> rows = page.getResult();
                 return new PageResult<CheckGroup>(total,rows);
    }

    @Override
    @Transactional
    public void deleteById(int id) throws MyException{
        //检查这个检查组是否被套餐使用
        int count= checkGroupDao.findSetmealCountByCheckGroupId(id);
        if (count>0) {
            throw new MyException("检查组被套餐使用了...");
        }
        checkGroupDao.deleteCheckGroupCheckItem(id);
        checkGroupDao.deleteById(id);
    }

    @Override
    public CheckGroup findById(int checkGroupId) {
        return checkGroupDao.findById(checkGroupId);
    }

    @Override
    public List<Integer> findCheckItemIdByCheckGroupId(int checkGroupId) {
        return checkGroupDao.findCheckItemIdByCheckGroupId(checkGroupId);
    }

    @Override
    @Transactional
    public void update(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.update(checkGroup);

        checkGroupDao.deleteCheckGroupCheckItem(checkGroup.getId());

        if (checkitemIds!=null){
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.addCheckGroupCheckItem(checkGroup.getId(),checkitemId);
            }
        }
    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }

}
