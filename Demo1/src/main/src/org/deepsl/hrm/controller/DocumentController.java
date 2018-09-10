package org.deepsl.hrm.controller;

import java.io.*;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.deepsl.hrm.domain.Document;
import org.deepsl.hrm.domain.User;
import org.deepsl.hrm.service.DocumentService;
import org.deepsl.hrm.service.HrmService;
import org.deepsl.hrm.util.common.HrmConstants;
import org.deepsl.hrm.util.tag.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**   
 * @Description: 处理上传下载文件请求控制器
 * @version V1.0   
 */

@Controller
@RequestMapping("/document")
public class DocumentController {
    @Autowired
    @Qualifier("DocumentService")
    DocumentService documentService;
    @RequestMapping("/selectDocument")
    public ModelAndView selectDocument(Document document,Integer pageIndex ,HttpSession session,PageModel pageModel,ModelAndView mv){
        List<Document> documents = documentService.findDocument(document, pageModel);
        mv.addObject("documents",documents);
        mv.setViewName("/document/document");
       return mv;
    }

    @RequestMapping("/addDocument")
    public ModelAndView addDocument(Document document, HttpServletRequest request,Integer flag, ModelAndView mv) throws IOException {
        if (flag==1){
            mv.setViewName("/document/showAddDocument");
            return mv;
        }
        /**
         * 文件上传
         */
        if (flag==2){
            Date date = new Date();
            document.setCreateDate(date);

            /**
             * 要解决session问题，同时要解决乱码问题
             */
            //User user = (User) request.getSession().getAttribute(HrmConstants.USER_SESSION);
            User user =new User(10,"11","11","11",1,date);
            document.setUser(user);

            //保存图片的位置
            String saveDir=request.getServletContext().getRealPath("/myFiles");
            //原来的文件名
            String originalFilename = document.getFile().getOriginalFilename();
            document.setFileName(originalFilename);
            File destFile = new File(saveDir, originalFilename);
            document.getFile().transferTo(destFile);
            document.setFilepath("myFiles/"+originalFilename);
            documentService.addDocument(document);
            mv.setViewName("/document/document");
            return mv;
        }
        return null;
    }

    /**
     *文件下载
     */
    @RequestMapping("/downLoad")
    public void downLoad(Integer id, ModelAndView mv,HttpServletRequest request, HttpServletResponse response) throws IOException {

        Document document = documentService.findDocumentById(id);

        String realPath = request.getServletContext().getRealPath("");
        //获取下载文件路径 ctxPath+"/uploadFile/"+ storeName;
        String downLoadPath = realPath+document.getFilepath();

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        //获取文件的长度
        //long fileLength = new File(downLoadPath).length();

        bis = new BufferedInputStream(new FileInputStream(downLoadPath));
        //输出流
        bos = new BufferedOutputStream(response.getOutputStream());
        //bos = new BufferedOutputStream(new FileOutputStream(document.getFileName()));
        byte[] buff = new byte[2048];
        int bytesRead;
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
            bos.write(buff, 0, bytesRead);
        }
        //关闭流
        bis.close();
        bos.close();
        //mv.setViewName("/document/document");
        //return mv;
    }

 
}
