package com.itheima.health.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.Exception.MyException;
import com.itheima.health.dao.CheckItemDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemDao checkItemDao;
    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }

    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {
         PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
                 if(!StringUtils.isEmpty(queryPageBean.getQueryString())){
                     queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
                 }
                 Page<CheckItem> page = checkItemDao.findByCondition(queryPageBean.getQueryString());
                 long total = page.getTotal();
                 List<CheckItem> rows = page.getResult();
                 return new PageResult<CheckItem>(total,rows);
    }

    @Override
    @Transactional
    public void deleteById(int id) throws MyException {
        int cnt = checkItemDao.findCountByCheckItemId(id);
        if(cnt>0){
            throw new MyException("检查项已被检查组使用，不能删除!");
        }
        checkItemDao.deleteById(id);
    }

    @Override
    public CheckItem findById(int id) {

        return checkItemDao.findById(id);
    }

    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }
}
