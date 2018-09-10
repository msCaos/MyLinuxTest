package org.deepsl.hrm.service.impl;

import org.deepsl.hrm.dao.DocumentDao;
import org.deepsl.hrm.dao.UserDao;
import org.deepsl.hrm.domain.Document;
import org.deepsl.hrm.service.DocumentService;
import org.deepsl.hrm.service.OtherServiceInterface;
import org.deepsl.hrm.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
@Service("DocumentService")

public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentDao documentDao;

    @Override
    public List<Document> findDocument(Document document, PageModel pageModel) {

        Map<String,Object> map = new HashMap<String, Object>();
        if (document.getTitle()!=null && !document.getTitle().isEmpty()){
            String title = document.getTitle();
            String s = "%"+title+"%";
            document.setTitle(s);
        }
        map.put("document",document);
        Integer count = documentDao.count(map);
        if (count==0){
            return null;
        }
        pageModel.setRecordCount(count);

        //用limit在数据库中重新进行查询
        int limit = pageModel.getPageSize();
        int offset=(pageModel.getPageIndex()-1)*limit;
        map.put("limit",limit);
        map.put("offset",offset);
        List<Document> documents = documentDao.selectByPage(map);
        return documents;
    }

    @Override
    public void addDocument(Document document) {
        documentDao.save(document);
    }

    @Override
    public Document findDocumentById(Integer id) {
        Document document = documentDao.selectById(id);
        return document;
    }

    @Override
    public void removeDocumentById(Integer id) {

    }

    @Override
    public void modifyDocument(Document document) {

    }
}
